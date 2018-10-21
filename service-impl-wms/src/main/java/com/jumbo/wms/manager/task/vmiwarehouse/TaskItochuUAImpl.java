package com.jumbo.wms.manager.task.vmiwarehouse;

import java.util.List;
import java.util.Map;

import loxia.dao.support.BeanPropertyRowMapperExt;

import org.springframework.beans.factory.annotation.Autowired;

import com.baozun.task.annotation.SingleTaskLock;
import com.jumbo.dao.vmi.warehouse.MsgRtnInboundOrderDao;
import com.jumbo.dao.vmi.warehouse.MsgRtnReturnDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.util.SFTPUtil;
import com.jumbo.wms.Constants;
import com.jumbo.wms.daemon.TaskItochuUA;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.task.CommonConfigManager;
import com.jumbo.wms.manager.vmi.itochuData.ItochuUAManager;
import com.jumbo.wms.manager.vmi.itochuData.ItochuUAManagerProxy;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnInboundOrder;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnReturn;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationType;

public class TaskItochuUAImpl extends BaseManagerImpl implements TaskItochuUA {
    protected static final String outBoundRtn = "outBoundRtn";
    protected static final String inBoundRtn = "inBoundRtn";
    protected static final String inBoundRtn2 = "inBoundRtn2";
    protected static final String checkInventory = "checkInventory";

    private static final long serialVersionUID = 7948184208342688533L;

    @Autowired
    private CommonConfigManager configManager;
    @Autowired
    private ItochuUAManager itochuUAManager;
    @Autowired
    private ItochuUAManagerProxy itochuUAManagerProxy;
    @Autowired
    private MsgRtnInboundOrderDao msgRtnInboundOrderDao;
    @Autowired
    private StockTransApplicationDao stockTransApplicationDao;
    @Autowired
    private MsgRtnReturnDao msgRtnReturnDao;

    /**
     * 退货入库通知、出库通知
     */
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void uaInOutBoundNotify() {
        log.debug("------task-----UA uaInOutBoundNotify---------start------------");
        Map<String, String> cfg = configManager.getCommonFTPConfig(Constants.CONFIG_GROUP_UA_FTP);
        // String localFileDir = cfg.get(Constants.VMI_FTP_DOWN_LOCALPATH);
        String localUpFileDir = cfg.get(Constants.VMI_FTP_UP_LOCALPATH);

        // 1.出库通知(pass)
        try {
            itochuUAManager.uaOutBoundNotify(localUpFileDir);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("uaInOutBoundNotify Exception:", e);
            }
        }
        // 2.退货入库通知 (pass)
        try {
            itochuUAManager.uaRtnInBoundNotify(localUpFileDir);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("uaRtnInBoundNotify Exception:", e);
            }
        }
        // 3.退货入库取消通知(pass)
        try {
            itochuUAManager.rtnCancelNotify(localUpFileDir);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("rtnCancelNotify Exception:", e);
            }
        }

        log.debug("------task-----UA uaInOutBoundNotify---------end------------");
    }

    /**
     * 出库反馈，入库反馈，退货入库反馈，退仓
     */
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void uaInOutBoundRtn() {
        log.debug("----task-----UA inOutBoundRtn--start-----");
        Map<String, String> cfg = configManager.getCommonFTPConfig(Constants.CONFIG_GROUP_UA_FTP);
        String localFileDir = cfg.get(Constants.VMI_FTP_DOWN_LOCALPATH);
        String bakFileDir = cfg.get(Constants.ITOCHU_FTP_DOWN_LOCALPATH_BACKUP);
        // String localFileDir = "F:\\ua_wh\\in";
        // String bakFileDir = "F:\\ua_wh\\inbackup";

        // 文件下载
        try {
            SFTPUtil.readFile(cfg.get(Constants.VMI_FTP_URL), cfg.get(Constants.VMI_FTP_PORT), cfg.get(Constants.VMI_FTP_USERNAME), cfg.get(Constants.VMI_FTP_PASSWORD), cfg.get(Constants.VMI_FTP_DOWNPATH), localFileDir,
                    cfg.get(Constants.VMI_FTP_REMOTEBAKPATH), true);
        } catch (Exception e) {}
        // 1.UA出库反馈(pass)
        try {
            itochuUAManager.readUAOutBoundRtnData(localFileDir, bakFileDir);
            itochuUAManagerProxy.uaOutBoundRtnExecute();
        } catch (Exception e) {
            log.error(localFileDir);
            log.error(e.getMessage());
        }
        // 2.UA退货入库反馈(pass)
        try {
            itochuUAManager.readUARtnInBoundData(localFileDir, bakFileDir);
            itochuUAManagerProxy.uaInBoundRtnExecute();
        } catch (Exception e) {
            log.error(localFileDir);
            log.error(e.getMessage());
        }
        // 3.UA入库反馈(pass)
        try {
            itochuUAManager.readUAInBoundRtn(localFileDir, bakFileDir);
            List<MsgRtnInboundOrder> rtns = msgRtnInboundOrderDao.findInboundByStatus(Constants.VIM_WH_SOURCE_ITOCHU_UA, new BeanPropertyRowMapperExt<MsgRtnInboundOrder>(MsgRtnInboundOrder.class));
            for (MsgRtnInboundOrder rtn : rtns) {
                try {
                    // 入库没有sta单
                    StockTransApplication Sta = stockTransApplicationDao.findStaByCode(rtn.getStaCode());
                    if (Sta == null) {
                        StockTransApplication sta = new StockTransApplication();
                        sta.setType(StockTransApplicationType.VMI_INBOUND_CONSIGNMENT);
                        itochuUAManager.createStaForInBoundRtnExecute(sta, rtn);
                    }
                } catch (Exception e) {
                    if (log.isErrorEnabled()) {
                        log.error("readUAInBoundRtn Exception:", e);
                    }
                }

            }
        } catch (Exception e) {
            log.error(localFileDir);
            log.error(e.getMessage());
        }
        // 4.UA 退仓反馈(pass)
        try {
            itochuUAManager.readUAReturnData(localFileDir, bakFileDir);
            List<MsgRtnReturn> rtns = msgRtnReturnDao.findReturnByVmiSourceAndStatus(Constants.VIM_WH_SOURCE_ITOCHU_UA, new BeanPropertyRowMapperExt<MsgRtnReturn>(MsgRtnReturn.class));
            for (MsgRtnReturn rtn : rtns) {
                try {
                    itochuUAManager.uaReturnExecute(rtn);
                } catch (Exception e) {
                    if (log.isErrorEnabled()) {
                        log.error("readUAReturnData Exception:", e);
                    }
                }

            }
        } catch (Exception e) {
            log.error(localFileDir);
            log.error(e.getMessage());
        }
        log.debug("----task-----UA inOutBoundRtn--end-----");
    }

    /**
     * 库存盘点
     */
    public void uaSaveCheckInventory() {
        log.debug("----task-----UA uaSaveCheckInventory--start-----");
        Map<String, String> cfg = configManager.getCommonFTPConfig(Constants.CONFIG_GROUP_UA_FTP);
        String localFileDir = cfg.get(Constants.VMI_FTP_DOWN_LOCALPATH);
        String bakFileDir = cfg.get(Constants.ITOCHU_FTP_DOWN_LOCALPATH_BACKUP);
        // 库存盘点
        try {
            itochuUAManager.saveCheckInventory(localFileDir, bakFileDir);
        } catch (Exception e) {
            log.error(localFileDir);
            log.error(e.getMessage());
        }
        log.debug("----task-----UA uaSaveCheckInventory--end-----");
    }

    /**
     * 读文件数据 （UA伊藤忠库存数据）(pass)
     */
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void uaReadItochuRtnInvToDB() {
        log.debug("----task-----UA uaReadItochuRtnInvToDB--start-----");
        Map<String, String> cfg = configManager.getCommonFTPConfig(Constants.CONFIG_GROUP_UA_FTP);
        String localFileDir = cfg.get(Constants.VMI_FTP_DOWN_LOCALPATH);
        String bakFileDir = cfg.get(Constants.ITOCHU_FTP_DOWN_LOCALPATH_BACKUP);
        // download file
        try {
            // 下载文件
            SFTPUtil.readFile(cfg.get(Constants.VMI_FTP_URL), cfg.get(Constants.VMI_FTP_PORT), cfg.get(Constants.VMI_FTP_USERNAME), cfg.get(Constants.VMI_FTP_PASSWORD), cfg.get(Constants.VMI_FTP_DOWNPATH), localFileDir,
                    cfg.get(Constants.VMI_FTP_REMOTEBAKPATH), true);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("uaReadItochuRtnInvToDB Exception:", e);
            }
        }
        try {
            itochuUAManager.readItochuRtnInvToDB(localFileDir, bakFileDir);
        } catch (Exception e) {
            log.error(localFileDir);
            log.error(e.getMessage());
        }
        log.debug("----task-----UA uaReadItochuRtnInvToDB--end-----");
    }

    /**
     * 插入库存到日志表
     */
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void insertUaInventory() {
        log.debug("******************* ua inventory start ********************");
        try {
            itochuUAManager.insertUaInventoryLog();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        log.debug("******************* ua inventory end ********************");
    }
}
