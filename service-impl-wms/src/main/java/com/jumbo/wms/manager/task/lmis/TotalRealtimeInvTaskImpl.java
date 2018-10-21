package com.jumbo.wms.manager.task.lmis;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Types;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import loxia.dao.Pagination;

import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;

import com.baozun.task.annotation.SingleTaskLock;
import com.jcraft.jsch.ChannelSftp;
import com.jumbo.dao.lmis.warehouse.RealTimeInventoryDao;
import com.jumbo.dao.lmis.warehouse.WarehouseChargesDao;
import com.jumbo.util.FileUtil;
import com.jumbo.util.FormatUtil;
import com.jumbo.util.SFTPUtil;
import com.jumbo.wms.Constants;
import com.jumbo.wms.daemon.TotalRealtimeInvTask;
import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.manager.task.CommonConfigManager;
import com.jumbo.wms.model.lmis.WarehouseCharges;

public class TotalRealtimeInvTaskImpl implements BaseManager, TotalRealtimeInvTask {

    private static final long serialVersionUID = -3164888303848042431L;

    protected static final Logger log = LoggerFactory.getLogger(TotalRealtimeInvTaskImpl.class);

    @Autowired
    private CommonConfigManager configManager;
    @Autowired
    private RealTimeInventoryDao realTimeInventoryDao;
    @Autowired
    private WarehouseChargesDao warehouseChargesDao;

    public String temFileName = null;// 临时文件路径
    public String backupsFileName = null;// 备份文件路径

    /**
     * 每日凌晨4点实时库存统计
     */
    @SingleTaskLock(timeout = Constants.TASK_LOCK_TIMEOUT, value = Constants.TASK_LOCK_VALUE)
    public void totalRealtimeInv() {
        log.info("totalRealtimeInv start.........!");
        /* 将实时库存写入相关表 */
        try {
            realTimeInventoryDao.executeDDL("truncate table t_wh_realtime_inv");
        } catch (Exception e) {
            log.debug("数据库执行[truncate table t_wh_realtime_inv]权限不足或异常！");
            realTimeInventoryDao.executeDDL("delete from t_wh_realtime_inv");
        }
        String aimTableName = "t_wh_realtime_inv";
        String aimTableColumn = "ou_id,inv_owner,location_id,sku_id,qty,inv_time";
        String sql =
                "select t.inv_owner, t.ou_id, t.location_id, t.sku_id, t.qty, sysdate inv_time" + "  from (select inv.ou_id," + "               inv.inv_owner," + "               inv.location_id," + "               inv.sku_id,"
                        + "               sum(inv.quantity) qty" + "          from t_wh_sku_inventory inv" + "         where inv.location_id is not null and inv.quantity != 0" + "         group by inv.ou_id, inv.inv_owner, inv.location_id, inv.sku_id) t"
                        + " order by t.inv_owner, t.ou_id, t.location_id, t.sku_id";

        int result = executeLargeDateInsertProcedure(aimTableName, aimTableColumn, sql);
        if (result == 0) {
            log.error("lmis实时库存写入中间表失败！" + new Date().toString());
            return;
        }
        /* 创建数据文件 */
        if (!saveFile()) {
            return;
        }
        /* 上传至sftp */
        if (temFileName != null) {
            boolean uploadStatus = false;
            try {
                uploadStatus = uploadRealtimeInv();
            } catch (Exception e) {
                log.error("每日实时库存文件上传异常" + new Date().toString(), e);
            }
            if (!uploadStatus) {
                log.debug("每日实时库存文件上传失败");
            } else {
                log.debug("每日实时库存文件上传成功");
                // 备份文件并删除临时文件
                if (backupsFile()) {
                    log.debug("每日实时库存文件备份成功");
                } else {
                    log.error("每日实时库存文件备份失败");
                }
            }
        }
        clear();
        /* 数据归档 */
        String tableName = "T_WH_REALTIME_INV_LOG";
        String tableColumn = "ou_id,inv_owner,location_id,sku_id,qty,inv_time,create_time";
        String sqlstr = "select ou_id,inv_owner,location_id,sku_id,qty,inv_time, sysdate create_time from t_wh_realtime_inv order by id";
        int res = executeLargeDateInsertProcedure(tableName, tableColumn, sqlstr);
        if (res == 0) {
            log.error("lmis实时库存转入归档表失败！" + new Date().toString());
        }
        log.info("totalRealtimeInv end.........!");
    }

    /**
     * 大量数据插入的存储过程
     */
    public int executeLargeDateInsertProcedure(String aimTableName, String aimTableColumn, String sql) {
        Map<String, Object> params = new HashMap<String, Object>();
        // 目标表
        params.put("ip_table_name", aimTableName);
        // 目标字段
        params.put("ip_table_column", aimTableColumn);
        // 查询语句
        params.put("ip_table_select", sql);
        SqlOutParameter s = new SqlOutParameter("return_result", Types.INTEGER);
        SqlParameter[] sqlParameters = {new SqlParameter("ip_table_name", Types.VARCHAR), new SqlParameter("ip_table_column", Types.VARCHAR), new SqlParameter("ip_table_select", Types.VARCHAR), s};
        Map<String, Object> result = realTimeInventoryDao.executeSp("sp_largedata_insert", sqlParameters, params);
        Object res = result.get("return_result");
        return Integer.parseInt(res.toString());
    }

    /**
     * 将需要上传的数据临时保存在服务器上
     */
    public boolean saveFile() {
        Map<String, String> cfg = configManager.getLmisFTPConfig(); // 查询配置信息
        String localDir = cfg.get(Constants.LMIS_LOCAL_PATH);
        File dir = new File(localDir);
        if (!dir.exists() && !dir.isDirectory()) {
            dir.mkdirs(); // 创建备份目录
        }
        String parentDir = dir.getParent();
        File tempDir = new File(parentDir + "/temporaryFile");
        if (!tempDir.exists() && !tempDir.isDirectory()) {
            tempDir.mkdirs(); // 创建临时目录
        }
        // 文件名显示昨天的日期
        Date date = DateUtils.addDays(new Date(), -1);
        String fileName = "/ZERO_DATA_INVENTORY_" + String.valueOf(FormatUtil.formatDate(date, "yyyyMMdd")) + ".txt";
        temFileName = tempDir + fileName;// 临时文件
        backupsFileName = localDir;// 备份文件路径
        File file = new File(temFileName);
        if (file.exists()) {
            file.delete();
        }
        FileWriter fileWriter = null;
        try {
            file.createNewFile();
            int pageSize = 10000;
            Pagination<WarehouseCharges> pagination = warehouseChargesDao.findWarehouseChargesByRealtimeInv(0, pageSize, new BeanPropertyRowMapper<WarehouseCharges>(WarehouseCharges.class), null);
            int totalPages = pagination.getTotalPages();
            fileWriter = new FileWriter(file, true);
            for (int i = 0; i < totalPages; i++) {
                // 不重复查询第一页
                if (i == 0) {
                    List<WarehouseCharges> list = pagination.getItems();
                    if (list.size() > 0) {
                        StringBuilder sb = new StringBuilder();
                        for (int j = 0; j < list.size(); j++) {
                            String json = net.sf.json.JSONObject.fromObject(list.get(j)).toString();
                            sb.append(json + "\r\n");
                            // fileWriter.write(list.get(j).toString() + "\r\n");
                        }
                        fileWriter.write(sb.toString());
                        fileWriter.flush();
                    }
                    continue;
                }
                // 剩余页数据
                Pagination<WarehouseCharges> temp = warehouseChargesDao.findWarehouseChargesByRealtimeInv(i * pageSize, pageSize, new BeanPropertyRowMapper<WarehouseCharges>(WarehouseCharges.class), null);
                List<WarehouseCharges> list = temp.getItems();
                if (list.size() > 0) {
                    StringBuilder sb = new StringBuilder();
                    for (int j = 0; j < list.size(); j++) {
                        String json = net.sf.json.JSONObject.fromObject(list.get(j)).toString();
                        sb.append(json + "\r\n");
                        // fileWriter.write(list.get(j).toString() + "\r\n");
                    }
                    fileWriter.write(sb.toString());
                    fileWriter.flush();
                }
            }
            return true;
        } catch (Exception e) {
            temFileName = null;
            log.error("实时库存文件生成失败", e);
            return false;
        } finally {
            if (fileWriter != null) {
                try {
                    fileWriter.flush();
                } catch (IOException e) {
                    if (log.isErrorEnabled()) {
                        log.error("totalRealTimeTask saveFile IOException", e);
                    }
                    return false;
                }
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    if (log.isErrorEnabled()) {
                        log.error("totalRealTimeTask fileWriter.close IOException", e);
                    }
                    return false;
                }
            }
        }
    }

    /**
     * 上传零点库存统计文件
     * 
     * @return
     */
    public boolean uploadRealtimeInv() {
        Map<String, String> cfg = configManager.getLmisFTPConfig();
        boolean flag = false;
        flag = sendFile(cfg.get(Constants.LMIS_FTP_URL), cfg.get(Constants.LMIS_FTP_PORT), cfg.get(Constants.LMIS_FTP_USERNAME), cfg.get(Constants.LMIS_FTP_PASSWORD), cfg.get(Constants.LMIS_FTP_UPPATH), temFileName, "GBK");
        return flag;
    }

    /**
     * Description: 向FTP服务器上传文件
     * 
     * @param path FTP服务器保存目录
     * @param localFileName 上传到FTP服务器上的文件名
     * @return 成功返回true，否则返回false
     * @throws IOException
     */
    public static boolean sendFile(String serverName, String port, String username, String password, String path, String localFileName, String ENcoding) {
        boolean success = false;
        // 得到文件名称
        if (localFileName == null || "".equals(localFileName)) {
            return success;
        }
        // 如果路径是\\转化成/
        if (localFileName.indexOf("/") == -1) {
            localFileName = localFileName.replace("\\\\", "\\");
            localFileName = localFileName.replace("\\", "/");
        }
        log.debug("remote path :" + path + ":localFileName:" + localFileName);
        String toFileName = localFileName.substring(localFileName.lastIndexOf("/") == -1 ? 0 : localFileName.lastIndexOf("/") + 1, localFileName.length());
        log.debug("remote path :" + path + ":toFileName:" + toFileName);
        // 连接SFTP
        ChannelSftp sftp = SFTPUtil.connect(serverName, Integer.parseInt(port), username, password);
        try {
            log.debug("ready to send file!");
            if (sftp != null && sftp.isConnected()) {
                // 上传文件
                boolean flag = SFTPUtil.sendFile(path, localFileName, sftp);
                log.debug("send file :" + flag);
                success = flag;
            }
        } catch (Exception e) {
            log.error("", e);
            success = false;
        } finally {
            SFTPUtil.disconnect(sftp);
        }
        return success;
    }

    /**
     * 备份文件
     * 
     * @return
     */
    public boolean backupsFile() {
        if (temFileName != null && backupsFileName != null) {
            boolean b = FileUtil.copyFile(temFileName, backupsFileName);
            FileUtil.deleteFile(temFileName);
            return b;
        }
        return false;
    }

    /**
     * 清空缓存目录
     * 
     * @return
     */
    public void clear() {
        temFileName = null;
        backupsFileName = null;
    }
}
