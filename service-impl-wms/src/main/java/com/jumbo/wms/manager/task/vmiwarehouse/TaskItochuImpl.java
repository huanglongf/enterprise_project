package com.jumbo.wms.manager.task.vmiwarehouse;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import loxia.dao.support.BeanPropertyRowMapperExt;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import com.baozun.task.annotation.SingleTaskLock;
import com.jcraft.jsch.ChannelSftp;
import com.jumbo.dao.vmi.etamData.EtamRtnDataDao;
import com.jumbo.dao.vmi.itochuData.ItochuMsgInboundOrderDao;
import com.jumbo.dao.vmi.warehouse.MsgRtnInboundOrderDao;
import com.jumbo.dao.vmi.warehouse.MsgRtnOutboundDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.util.FtpUtil;
import com.jumbo.util.SFTPUtil;
import com.jumbo.wms.Constants;
import com.jumbo.wms.daemon.TaskItochu;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.task.CommonConfigManager;
import com.jumbo.wms.manager.vmi.etamData.EtamManager;
import com.jumbo.wms.manager.vmi.itochuData.ItochuManager;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.manager.warehouse.WareHouseManagerProxy;
import com.jumbo.wms.model.DefaultStatus;
import com.jumbo.wms.model.vmi.etamData.EtamRtnData;
import com.jumbo.wms.model.vmi.warehouse.ItochuMsgInboundOrder;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnInboundOrder;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnOutbound;
import com.jumbo.wms.model.warehouse.StockTransApplication;

public class TaskItochuImpl extends BaseManagerImpl implements TaskItochu {
    protected static final String outBoundRtn = "outBoundRtn";
    protected static final String inBoundRtn = "inBoundRtn";
    protected static final String inBoundRtn2 = "inBoundRtn2";
    protected static final String checkInventory = "checkInventory";
    /**
	 * 
	 */
    private static final long serialVersionUID = 7948184208342688533L;

    @Autowired
    private ItochuManager itochuManager;
    @Autowired
    private EtamManager etamManager;
    @Autowired
    private CommonConfigManager configManager;
    @Autowired
    private WareHouseManagerProxy wareHouseManagerProxy;
    @Autowired
    private MsgRtnInboundOrderDao msgRtnInboundOrderDao;
    @Autowired
    private WareHouseManager wareHouseManager;
    @Autowired
    private MsgRtnOutboundDao msgRtnOutboundDao;
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private ItochuMsgInboundOrderDao itochuMsgInboundOrderDao;
    @Autowired
    private EtamRtnDataDao etamRtnDataDao;

    /**
     * Etam 商品主档
     */
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void etamSku() {
        log.debug("------task-----Etam etamSku	---------start------------");

        Map<String, String> cfg = configManager.getCommonFTPConfig(Constants.ETAM_CONFIG_GROUP_FTP);
        String localFileDir = cfg.get(Constants.VMI_FTP_DOWN_LOCALPATH);
        String bakFileDir = cfg.get(Constants.VMI_FTP_DOWN_LOCALPATH_BACKUP);
        // download file
        try {
            log.debug("download url : {}", cfg.get(Constants.VMI_FTP_URL));
            log.debug("download port : {}", cfg.get(Constants.VMI_FTP_PORT));
            log.debug("download password : {}", cfg.get(Constants.VMI_FTP_PASSWORD));
            log.debug("download path : {}", cfg.get(Constants.VMI_FTP_DOWNPATH));
            log.debug("download loc path : {}", cfg.get(Constants.VMI_FTP_DOWN_LOCALPATH));
            FtpUtil.readFile(cfg.get(Constants.VMI_FTP_URL), cfg.get(Constants.VMI_FTP_PORT), cfg.get(Constants.VMI_FTP_USERNAME), cfg.get(Constants.VMI_FTP_PASSWORD),
            // cfg.get(Constants.ETAM_FTP_DOWNLOAD_FILE_START_STR),
                    "", cfg.get(Constants.VMI_FTP_DOWNPATH), cfg.get(Constants.VMI_FTP_DOWN_LOCALPATH));
            log.debug("---download success----");
        } catch (Exception e) {
            log.error("", e);
        }

        // 保存主档文件
        try {
            readSKUFileIntoDB(localFileDir, bakFileDir);
        } catch (Exception e) {
            log.error("", e);
        }
        log.debug("------task-----Etam etamSku	---------end------------");

    }

    /**
     * 读主档文件
     * 
     */
    /**
     * 读FTP商品文件到数据库
     */
    public boolean readSKUFileIntoDB(String localFileDir, String bakFileDir) {
        String line = null;
        boolean flag = false;
        List<String> results = new ArrayList<String>();
        BufferedReader buffRead = null;
        FileInputStream fis = null;
        InputStreamReader isr = null;
        File[] files = null;
        try {
            File fileDir = new File(localFileDir);
            files = fileDir.listFiles();
            if (files == null || files.length == 0) {
                log.debug("{} is null, has no file ============================", localFileDir);
                flag = false;
                return flag;
            }
            // 从本地读取文件写入到数据库中
            for (File file : files) {
                if (file.isDirectory() || file.getName().indexOf("SendArticle") == -1) {
                    continue;
                }
                log.debug("file  name ===================={} ", file.getName());
                // buffRead = new BufferedReader(new FileReader(file));
                fis = new FileInputStream(file);
                isr = new InputStreamReader(fis, "UTF-16");
                buffRead = new BufferedReader(isr);
                while ((line = buffRead.readLine()) != null) {
                    results.add(line);
                }
                log.debug("results: **************** {}", results.size());
                flag = etamManager.skuInsertIntoDB(results); // T_ETAM_SKU
                results.clear();
                /*
                 * if (buffRead != null) { buffRead.close(); }
                 */
                if (flag) {
                    // FileUtils.moveFileToDirectory(file, new File(bakFileDir), true);
                    FileUtils.copyFileToDirectory(file, new File(bakFileDir), true);
                    file.delete();
                }
                if (isr != null) {
                    isr.close();
                }
                if (fis != null) {
                    fis.close();
                }
            }
        } catch (FileNotFoundException e) {
            log.error("", e);
        } catch (IOException e) {
            log.error("", e);
        } catch (Exception e) {
            log.error("", e);
        } finally {
            try {
                if (buffRead != null) {
                    buffRead.close();
                }
                if (isr != null) {
                    isr.close();
                }
                if (fis != null) {
                    fis.close();
                }

            } catch (IOException e) {
                log.error("", e);
            }
        }
        return flag;
    }

    /**
     * 退货入库通知、出库通知
     */
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void inOutBoundNotify() {
        log.debug("------task-----Etam inOutBoundNotify---------start------------");
        Map<String, String> cfg = configManager.getCommonFTPConfig(Constants.CONFIG_GROUP_ITOCHU_FTP);
        // String localFileDir = cfg.get(Constants.VMI_FTP_DOWN_LOCALPATH);
        String localUpFileDir = cfg.get(Constants.VMI_FTP_UP_LOCALPATH);
        String bakFileDir = cfg.get(Constants.ITOCHU_FTP_DOWN_LOCALPATH_BACKUP);
        // 1.出库通知
        try {
            itochuManager.outBoundNotify(localUpFileDir);
        } catch (Exception e) {
            log.error("", e);
        }
        try {
            itochuManager.rtnInBoundNotify(localUpFileDir);
        } catch (Exception e) {
            log.error("", e);
        }
        // 退货入库取消通知
        try {
            itochuManager.rtnCancelNotify(localUpFileDir);
        } catch (Exception e) {
            log.error("", e);
        }
        // 上传出库通知文件到ftp
        ChannelSftp sftp = SFTPUtil.connect(cfg.get(Constants.VMI_FTP_URL), Integer.parseInt(cfg.get(Constants.VMI_FTP_PORT)), cfg.get(Constants.VMI_FTP_USERNAME), cfg.get(Constants.VMI_FTP_PASSWORD));
        try{
            String upfileDir = cfg.get(Constants.VMI_FTP_UPPATH);
            File dir = new File(localUpFileDir);
            if (dir.listFiles() != null && dir.listFiles().length != 0) {
                for (File f : dir.listFiles()) {
                    SFTPUtil.sendFile(upfileDir, f.getPath(), sftp);
                    try {
                        FileUtils.copyFileToDirectory(f, new File(bakFileDir), true);
                        f.delete();
                    } catch (IOException e) {
                        log.error(e.getMessage());
                        log.error("", e);
                    }
                }
            }
            log.debug("------task-----Etam inOutBoundNotify---------end------------");
        }finally{
        	SFTPUtil.disconnect(sftp);
        }

    }

    private void copyFile(String localFileDir, String bakFileDir) {
        File fileDir = new File(localFileDir);
        File[] files = fileDir.listFiles();
        if (files == null || files.length == 0) {
            log.debug("TaskItochu.copyFile():  {} is null, has no file ============================", localFileDir);
        } else {
            // 从本地读取文件写入到数据库中
            for (File file : files) {
                if (!file.isDirectory()) {
                    try {
                        FileUtils.copyFileToDirectory(file, new File(bakFileDir), true);
                    } catch (IOException e) {
                        log.debug("TaskItochu.copyFile()....Exception:" + e.getMessage());
                        log.error("", e);
                    }
                }
            }
        }
    }

    /**
     * 出库反馈，入库反馈，退货入库反馈，退仓
     */
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void inOutBoundRtn() {
        log.debug("----task-----Etam inOutBoundRtn--start-----");
        Map<String, String> cfg = configManager.getCommonFTPConfig(Constants.CONFIG_GROUP_ITOCHU_FTP);
        String localFileDir = cfg.get(Constants.VMI_FTP_DOWN_LOCALPATH);
        String bakFileDir = cfg.get(Constants.ITOCHU_FTP_DOWN_LOCALPATH_BACKUP);
        String bakFileDir2 = cfg.get(Constants.ITOCHU_FTP_DOWN_LOCALPATH_BACKUP2);
        // download file
        try {
            // 下载文件
            SFTPUtil.readFile(cfg.get(Constants.VMI_FTP_URL), cfg.get(Constants.VMI_FTP_PORT), cfg.get(Constants.VMI_FTP_USERNAME), cfg.get(Constants.VMI_FTP_PASSWORD), cfg.get(Constants.VMI_FTP_DOWNPATH), localFileDir, null, true);
        } catch (Exception e) {
            log.error("", e);
        }

        // copy to ilc_in_arch
        copyFile(localFileDir, bakFileDir2);

        // 1.入库反馈
        try {
            inBoundRtn(cfg);
        } catch (Exception e) {
            log.error("", e);
        }
        // 2.出库反馈
        try {
            outBoundRtn(cfg);
        } catch (Exception e) {
            log.error("", e);
        }
        // 3.退货入库反馈
        try {
            rtnrRaInbound(cfg);
        } catch (Exception e) {
            log.error("", e);
        }
        // 4.退仓
        try {
            rtnReturnWh(cfg);
        } catch (Exception e) {
            log.error("", e);
        }
        // 5.库存盘点
        try {
            itochuManager.saveCheckInventory(localFileDir, bakFileDir);
        } catch (Exception e) {
            log.error("", e);
        }
        log.debug("----task-----Etam inOutBoundRtn--end-----");
    }

    private void outBoundRtn(Map<String, String> cfg) {
        log.debug("-------------Etam outBoundRtn-start-------");
        String localFileDir = cfg.get(Constants.VMI_FTP_DOWN_LOCALPATH);
        String bakFileDir = cfg.get(Constants.ITOCHU_FTP_DOWN_LOCALPATH_BACKUP);
        itochuManager.readFileIntoDBInOutBoundRtn(localFileDir, bakFileDir, outBoundRtn, "SHIP", "shupl");
        outBoundRtn0();
        log.debug("-------------Etam outBoundRtn-end-------");
    }

    public void outBoundRtn0() {
        // 1.读反馈表
        List<MsgRtnOutbound> rtns = msgRtnOutboundDao.findAllVmiMsgOutbound(Constants.VIM_WH_SOURCE_ITOCHU, new BeanPropertyRowMapper<MsgRtnOutbound>(MsgRtnOutbound.class));
        // 2.执行出库流程
        for (MsgRtnOutbound rtn : rtns) {
            StockTransApplication sta = staDao.findStaByCode(rtn.getStaCode());
            if (sta != null) {
                int staStatus = sta.getStatus().getValue();
                if (staStatus == 10 || staStatus == 4) {
                    wareHouseManager.updateMsgRtnOutbound(rtn.getId(), DefaultStatus.FINISHED.getValue());
                    continue;
                }
                try {
                    wareHouseManagerProxy.callVmiSalesStaOutBound(rtn.getId());
                } catch (BusinessException e) {
                    log.error("outBoundRtn0 error ! OUT STA :" + e.getErrorCode());
                } catch (Exception e) {
                    log.error("", e);
                }
            } else {
                wareHouseManager.updateMsgRtnOutbound(rtn.getId(), DefaultStatus.INEXECUTION.getValue());
            }
        }
    }

    public void inBoundRtn(Map<String, String> cfg) {
        log.debug("----task-----Etam inBoundRtn--start-----");
        String localFileDir = cfg.get(Constants.VMI_FTP_DOWN_LOCALPATH);
        String bakFileDir = cfg.get(Constants.ITOCHU_FTP_DOWN_LOCALPATH_BACKUP);
        itochuManager.readFileIntoDBInOutBoundRtn(localFileDir, bakFileDir, inBoundRtn, "RecOnWay", "txt");
        // 创STA
        List<ItochuMsgInboundOrder> boxs = itochuMsgInboundOrderDao.findAllInboundOrderBoxNoByStatus(new BeanPropertyRowMapperExt<ItochuMsgInboundOrder>(ItochuMsgInboundOrder.class));
        for (ItochuMsgInboundOrder cmd : boxs) {
            try {
                List<ItochuMsgInboundOrder> tmplist = new ArrayList<ItochuMsgInboundOrder>();
                tmplist.add(cmd);
                itochuManager.createInBoundSta(tmplist);
            } catch (Exception e) {
                log.error("", e);
            }
        }
        // 创建入库单
        createInbound();
        log.debug("----task-----Etam inBoundRtn--end-----");
    }

    public void createInbound() {
        List<MsgRtnInboundOrder> rtns = msgRtnInboundOrderDao.findInboundByStatus(Constants.VIM_WH_SOURCE_ITOCHU, new BeanPropertyRowMapperExt<MsgRtnInboundOrder>(MsgRtnInboundOrder.class));
        if (rtns != null && rtns.size() > 0) {
            msgRtnInboundOrderDao.updateIlcshErrorInbound();
            wareHouseManagerProxy.inboundToBz(rtns);
        }
    }

    private void rtnrRaInbound(Map<String, String> cfg) {
        String localFileDir = cfg.get(Constants.VMI_FTP_DOWN_LOCALPATH);
        String bakFileDir = cfg.get(Constants.ITOCHU_FTP_DOWN_LOCALPATH_BACKUP);
        itochuManager.readFileIntoDBInOutBoundRtn(localFileDir, bakFileDir, inBoundRtn2, "RECV", "rcupl");
        // 创建入库单
        createInbound();
    }

    private void rtnReturnWh(Map<String, String> cfg) {
        String localFileDir = cfg.get(Constants.VMI_FTP_DOWN_LOCALPATH);
        String bakFileDir = cfg.get(Constants.ITOCHU_FTP_DOWN_LOCALPATH_BACKUP);
        itochuManager.returnToEtam(localFileDir, bakFileDir);
        List<EtamRtnData> etamRtnDatas = etamRtnDataDao.findEtamRtnByCreateStatus(EtamRtnData.CREATE_STATUS, new BeanPropertyRowMapperExt<EtamRtnData>(EtamRtnData.class));
        if (etamRtnDatas != null) {
            for (EtamRtnData date : etamRtnDatas) {
                try {
                    itochuManager.createStaFroEtamRtn(date.getId());
                } catch (Exception e) {
                    log.error("", e);
                }
            }
        }
    }

    /**
     * 读文件数据 （伊藤忠库存数据）
     * 
     */
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void readItochuRtnInvToDB() {
        log.debug("----task-----Etam readItochuRtnInvToDB--start-----");
        Map<String, String> cfg = configManager.getCommonFTPConfig(Constants.CONFIG_GROUP_ITOCHU_FTP);
        String localFileDir = cfg.get(Constants.VMI_FTP_DOWN_LOCALPATH);
        String bakFileDir = cfg.get(Constants.ITOCHU_FTP_DOWN_LOCALPATH_BACKUP);
        String bakFileDir2 = cfg.get(Constants.ITOCHU_FTP_DOWN_LOCALPATH_BACKUP2);
        // download file
        try {
            // 下载文件
            SFTPUtil.readFile(cfg.get(Constants.VMI_FTP_URL), cfg.get(Constants.VMI_FTP_PORT), cfg.get(Constants.VMI_FTP_USERNAME), cfg.get(Constants.VMI_FTP_PASSWORD), cfg.get(Constants.VMI_FTP_DOWNPATH), localFileDir, null, true);
        } catch (Exception e) {
            log.error("", e);
        }
        // copy to ilc_in_arch
        copyFile(localFileDir, bakFileDir2);
        try {
            itochuManager.readItochuRtnInvToDB(localFileDir, bakFileDir);
        } catch (Exception e) {
            log.error("", e);
        }
        log.debug("----task-----Etam readItochuRtnInvToDB--end-----");
    }

}
