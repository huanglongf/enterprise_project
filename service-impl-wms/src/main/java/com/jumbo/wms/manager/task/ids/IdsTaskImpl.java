package com.jumbo.wms.manager.task.ids;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import loxia.dao.support.BeanPropertyRowMapperExt;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.transaction.UnexpectedRollbackException;

import com.baozun.task.annotation.SingleTaskLock;
import com.jumbo.dao.baseinfo.WarehouseDao;
import com.jumbo.dao.mail.MailTemplateDao;
import com.jumbo.dao.system.ChooseOptionDao;
import com.jumbo.dao.vmi.warehouse.MsgOutboundOrderCancelDao;
import com.jumbo.dao.vmi.warehouse.MsgOutboundOrderDao;
import com.jumbo.dao.vmi.warehouse.MsgRtnAdjustmentDao;
import com.jumbo.dao.vmi.warehouse.MsgRtnInboundOrderDao;
import com.jumbo.dao.vmi.warehouse.MsgRtnReturnDao;
import com.jumbo.dao.warehouse.StaDeliveryInfoDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.util.MailUtil;
import com.jumbo.util.SFTPUtil;
import com.jumbo.util.StringUtil;
import com.jumbo.webservice.ids.manager.IdsManager;
import com.jumbo.wms.Constants;
import com.jumbo.wms.daemon.IdsTask;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.expressDelivery.TransOlManager;
import com.jumbo.wms.manager.task.CommonConfigManager;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.manager.warehouse.WareHouseManagerExecute;
import com.jumbo.wms.manager.warehouse.WareHouseManagerProxy;
import com.jumbo.wms.manager.warehouse.WareHouseManagerQuery;
import com.jumbo.wms.model.DefaultStatus;
import com.jumbo.wms.model.baseinfo.Transportator;
import com.jumbo.wms.model.baseinfo.Warehouse;
import com.jumbo.wms.model.mail.MailTemplate;
import com.jumbo.wms.model.system.ChooseOption;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrder;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrderCancel;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnAdjustment;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnInboundOrder;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnOutbound;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnReturn;
import com.jumbo.wms.model.warehouse.InventoryCheck;
import com.jumbo.wms.model.warehouse.StaDeliveryInfo;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationStatus;

public class IdsTaskImpl extends BaseManagerImpl implements IdsTask {
    /**
	 * 
	 */
    private static final long serialVersionUID = -5869227746209251221L;

    private static final int ROWNUM = 100;

    @Autowired
    private CommonConfigManager configManager;
    @Autowired
    private IdsManager idsManager;
    @Autowired
    private WareHouseManagerProxy wareHouseManagerProxy;
    @Autowired
    private WareHouseManagerQuery wareHouseManagerQuery;
    @Autowired
    private MsgRtnInboundOrderDao msgRtnInboundOrder;
    @Autowired
    private MsgRtnReturnDao msgRtnReturnDao;
    @Autowired
    private MsgRtnAdjustmentDao msgRtnAdjustmentDao;
    @Autowired
    private WareHouseManager wareHouseManager;
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private MsgOutboundOrderCancelDao msgOutboundOrderCancelDao;
    @Autowired
    private ChooseOptionDao chooseOptionDao;
    @Autowired
    private MailTemplateDao mailTemplateDao;
    @Autowired
    private TransOlManager transOlManager;
    @Autowired
    private WareHouseManagerExecute wareHouseManagerExecute;
    @Autowired
    private MsgOutboundOrderDao msgOutboundOrderDao;
    @Autowired
    private WarehouseDao warehouseDao;
    @Autowired
    private StaDeliveryInfoDao staDeliveryInfoDao;

    /**
     * 反馈信息任务
     */
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void idsFeedbackTask() {
        feedbackIds();
        // 把文件写到数据库文件信息表
        receiveIdsFeedback();
        // 将文件信息表里面数据同步到中间表
        synchronization();
        // 根据中间表更新数据ASN
        log.debug("===========================task msg inbound ids ===================== ");
        List<MsgRtnInboundOrder> msgOrderNewInfo = msgRtnInboundOrder.findInboundByStatus(Constants.VIM_WH_SOURCE_IDS, new BeanPropertyRowMapperExt<MsgRtnInboundOrder>(MsgRtnInboundOrder.class));
        if (msgOrderNewInfo.size() > 0) {
            log.debug("===========================msgOrderNewInfo size : {}  ===================== ", msgOrderNewInfo.size());
            for (MsgRtnInboundOrder msgNewInorder : msgOrderNewInfo) {
                try {
                    wareHouseManagerProxy.msgInBoundWareHouse(msgNewInorder);
                } catch (BusinessException be) {
                    log.error("idsFeedbackTask error ,error code is : {},id : {}", be.getErrorCode(), msgNewInorder.getId());
                } catch (UnexpectedRollbackException e) {
                    log.error("idsFeedbackTask error ,Transaction rolled back because it has been marked as rollback-only,id : {}", msgNewInorder.getId());
                } catch (Exception e) {
                    log.error("", e);
                }
            }
        }
        // 根据中间表更新数据SHP
        List<MsgRtnReturn> msgRtnReturnList = msgRtnReturnDao.queryByCanceledAndCreated(Constants.VIM_WH_SOURCE_IDS);
        if (msgRtnReturnList.size() > 0) {
            for (MsgRtnReturn msgRtnReturn : msgRtnReturnList) {
                try {
                    idsManager.executeVmiReturnOutBound(msgRtnReturn);
                } catch (BusinessException be) {
                    idsManager.executeError(msgRtnReturn);
                    log.error("idsFeedbackTask error ,error code is : {},id : {}", be.getErrorCode(), msgRtnReturn.getId());
                } catch (UnexpectedRollbackException e) {
                    log.error("idsFeedbackTask error ,Transaction rolled back because it has been marked as rollback-only,id : {}", msgRtnReturn.getId());
                    idsManager.executeError(msgRtnReturn);
                } catch (Exception e) {
                    log.error("", e);
                    idsManager.executeError(msgRtnReturn);
                }
            }
        }
        // 根据中间表生成库存调整
        List<MsgRtnAdjustment> msgRtnASJList = msgRtnAdjustmentDao.queryByCanceledAndCreated(Constants.VIM_WH_SOURCE_IDS);
        if (msgRtnASJList.size() > 0) {
            InventoryCheck ic = null;
            for (MsgRtnAdjustment msgRtnADJ : msgRtnASJList) {
                ic = null;
                try {
                    // 创建库存调整
                    ic = idsManager.createVmiAdjustment(msgRtnADJ);
                } catch (BusinessException e) {
                    // 创建错误的数据
                    idsManager.updateADJStatus(msgRtnADJ, DefaultStatus.ERROR);
                    log.error("", e);
                } catch (Exception e) {
                    // 创建失败的数据
                    idsManager.updateADJStatus(msgRtnADJ, DefaultStatus.CANCELED);
                    log.error("", e);
                }
                if (ic != null) {
                    try {
                        // 执行库存调整
                        idsManager.executionVmiAdjustment(ic);
                    } catch (Exception e) {
                        log.error("----------->>IDS->WMS Error ! Adjustment execution Error!");
                        log.error("", e);
                    }
                }
            }
        }
    }

    /**
     * 反馈给信息
     */
    private void feedbackIds() {
        log.debug("IDSTask.feedbackIds");
        try {
            // 反馈收货信息
            idsManager.feedbackIDSInfo();
        } catch (Exception e) {
            log.debug("IDSTask.feedbackIds Error");
            log.error("", e);
        }
    }

    // 将文件数据表里面的数据写入到中间表
    private void synchronization() {
        synchronizationREC();
        synchronizationSHP();
        synchronizationADJ();
    }

    /**
     * 反馈给信息
     */
    private void synchronizationREC() {
        log.debug("IDSTask.feedbackIds");
        try {
            // 将收货确认(REC)文件数据表里面的数据写入到中间表
            idsManager.synchronizationREC();
        } catch (Exception e) {
            log.debug("IDSTask.synchronizationREC Error");
            log.error("", e);
        }
    }

    /**
     * 反馈给信息
     */
    private void synchronizationSHP() {
        log.debug("IDSTask.feedbackIds");
        try {
            // 将收货确认(SHP)文件数据表里面的数据写入到中间表
            idsManager.synchronizationSHP();
        } catch (Exception e) {
            log.debug("IDSTask.synchronizationSHP Error");
            log.error("", e);
        }
    }

    /**
     * 反馈给信息
     */
    private void synchronizationADJ() {
        log.debug("IDSTask.feedbackIds");
        try {
            // 将库存调整(ADJ)文件数据表里面的数据写入到中间表
            idsManager.synchronizationADJ();
        } catch (Exception e) {
            log.debug("IDSTask.synchronizationADJ Error");
            log.error("", e);
        }
    }

    /**
     * 接收Ids反馈信息
     */
    private void receiveIdsFeedback() {
        Calendar c = Calendar.getInstance();// 取系统日期
        Map<String, String> config = configManager.getIDSFTPConfig();
        try {
            // 下载须要同步的文件
            download(config, c);
        } catch (Exception e) {
            log.debug("IDSTask.receiveIdsFeedback download error");
            log.error("", e);
        }
        log.debug("IDSTask.receiveIdsFeedback");
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String date = sdf.format(c.getTime());
            String path = config.get(Constants.IDS_FTP_IN + Constants.IDS_FTP_LOCAL_SAVE_PATH) + "/" + date;
            File file = new File(path);
            // 获取当前文件夹下面的文件
            File[] files = file.listFiles();
            if (files != null && files.length > 0) {
                for (int i = 0; i < files.length; i++) {
                    // 判断是否是文件 而不是目录
                    if (!files[i].isDirectory()) {
                        String name = files[i].getName().substring(0, 6);
                        if (name != null && name.length() == 6) {
                            try {
                                if (name.equals(Constants.IDS_FTP_FILE_TYPE_ADJ)) {
                                    // 接收库存调整反馈信息
                                    idsManager.receiveADJFeedback(files[i]);
                                    move(path + "/copy", files[i]);
                                } else if (name.equals(Constants.IDS_FTP_FILE_TYPE_REC)) {
                                    // 接收收货确认的反馈信息
                                    idsManager.receiveRECFeedback(files[i]);
                                    move(path + "/copy", files[i]);
                                } else if (name.equals(Constants.IDS_FTP_FILE_TYPE_SHP)) {
                                    // 接收发货确认的反馈信息
                                    idsManager.receiveSHPFeedback(files[i]);
                                    move(path + "/copy", files[i]);
                                }
                            } catch (Exception e) {
                                log.debug("IDSTask.receiveIdsFeedback Error");
                                log.error("", e);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.debug("IDSTask.receiveIdsFeedback Error");
            log.error("", e);
        }
    }

    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void vimIDSSaleOutboundRtn() {
        List<MsgRtnOutbound> rtns = wareHouseManagerQuery.findAllMsgRtnOutbound(Constants.VIM_WH_SOURCE_IDS);
        for (MsgRtnOutbound msgRtnbound : rtns) {
            StockTransApplication sta = staDao.findStaByCode(msgRtnbound.getStaCode());
            try {
                if (sta != null) {
                    if (msgRtnbound.getType().equals("P")) {
                        wareHouseManager.updateMsgRtnOutbound(msgRtnbound.getId(), DefaultStatus.FINISHED.getValue());
                    } else if (msgRtnbound.getType().equals("S")) {
                        if (sta.getStatus().equals(StockTransApplicationStatus.CREATED)) {
                            wareHouseManagerProxy.callVmiSalesStaOutBound(msgRtnbound.getId());
                        } else if (sta.getStatus().equals(StockTransApplicationStatus.FINISHED)) {
                            wareHouseManager.updateMsgRtnOutbound(msgRtnbound.getId(), DefaultStatus.FINISHED.getValue());
                        }
                    }
                } else {
                    wareHouseManager.updateMsgRtnOutbound(msgRtnbound.getId(), DefaultStatus.CANCELED.getValue());
                }
            } catch (BusinessException e) {
                log.error("vimIDSSaleOutboundRtn error ! OUT STA :" + e.getErrorCode());
            } catch (Exception ex) {
                log.error("", ex);
            }

        }
    }

    /**
     * 复制文件
     * 
     * @param copyPath
     * @param file
     * @throws IOException
     */
    private void move(String copyPath, File file) throws IOException {
        // 获取当前文件夹下面的文件
        File objFolderFile = new File(copyPath);
        if (!file.exists()) return;
        if (!objFolderFile.exists()) objFolderFile.mkdirs();
        if (file.isFile()) {
            File objFile = new File(objFolderFile.getPath() + File.separator + file.getName());
            // 复制文件到目标地
            InputStream ins = new FileInputStream(file);
            FileOutputStream outs = new FileOutputStream(objFile);
            byte[] buffer = new byte[1024 * 512];
            int length;
            while ((length = ins.read(buffer)) != -1) {
                outs.write(buffer, 0, length);
            }
            ins.close();
            outs.flush();
            outs.close();
            file.delete();
        }
    }

    /**
     * 抓取文件
     */
    public void download(Map<String, String> config, Calendar c) {
        log.debug("Start download IDS file data .................");
        if (config == null || config.size() == 0) {
            log.debug("## no task available for task batch [accountSet={}] ", new Object[] {"default"});
            return;
        }
        // 获取日期时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String date = sdf.format(c.getTime());
        String downDir = config.get(Constants.IDS_FTP_IN + Constants.IDS_FTP_LOCAL_SAVE_PATH);
        if (downDir == null || downDir.equals("")) {
            log.debug("the download path is not exist");
            return;
        }
        String localPath = downDir + "/" + date;
        File dir = new File(localPath);
        // 判断是否存在
        if (!dir.exists()) {
            dir.mkdirs();
        }
        // 下载文件
        int result =
                SFTPUtil.readFile(config.get(Constants.IDS_FTP_IN + Constants.IDS_FTP_URL), config.get(Constants.IDS_FTP_IN + Constants.IDS_FTP_PORT), config.get(Constants.IDS_FTP_IN + Constants.IDS_FTP_USERNAME),
                        config.get(Constants.IDS_FTP_IN + Constants.IDS_FTP_PASSWORD), config.get(Constants.IDS_FTP_IN + Constants.IDS_FTP_DOWNLOAD_PATH), localPath, null, true);
        if (result == 0) {
            log.debug("{}… ………download file success", new Object[] {new Date()});
        } else {
            log.debug("{}… ………download file error", new Object[] {new Date()});
        }
        log.debug("Ent download IDS file data .................");
    }


    /**
     * 定时任务 对MsgOutboundOrder解锁
     * 
     * @param sta
     */
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void msgUnLocked() {
        Boolean flag = true;
        while (flag) {
            List<Long> idList = staDao.findLockedOrder(ROWNUM, new SingleColumnRowMapper<Long>(Long.class));
            if (idList.size() < ROWNUM) {
                flag = false;
            }
            for (Long id : idList) {
                try {
                    transOlManager.matchingTransNo(id);
                } catch (BusinessException e) { // 记录获取运单失败的单据
                    wareHouseManagerExecute.failedToGetTransno(id);
                    StockTransApplication sta1 = staDao.getByPrimaryKey(id);
                    log.error(e.getErrorCode() + "-----"); //
                    // 判断顺丰可否送达，否则修改快递供应商为EMS/EMSCOD
                    MsgOutboundOrder mo = msgOutboundOrderDao.getByStaCode(sta1.getCode());
                    Warehouse wh = warehouseDao.getByOuId(sta1.getMainWarehouse().getId());
                    StaDeliveryInfo sinfo = sta1.getStaDeliveryInfo();
                    if (!Constants.VIM_WH_SOURCE_GUESS.equals(mo.getSource()) && !Constants.VIM_WH_SOURCE_GUESS_RETAIL.equals(mo.getSource())) {
                        if (sinfo.getIsCod() != null && sinfo.getIsCod()) {
                            sinfo.setLpCode(Transportator.EMS_COD);
                            mo.setLpCode(Transportator.EMS_COD);
                        } else {
                            sinfo.setLpCode(Transportator.EMS);
                            mo.setLpCode(Transportator.EMS);
                        }
                        if (wh.getIsEmsOlOrder()) {
                            mo.setIsLocked(true);
                        } else {
                            mo.setIsLocked(false);
                        }
                    }
                    msgOutboundOrderDao.save(mo);
                    staDeliveryInfoDao.save(sinfo);
                    log.error("", e);
                } catch (Exception e) {
                    log.error("", e);
                }
            }
        }
    }

    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void mailIds() {
        log.debug("salesInventoryOmsEmail===================>start");
        List<MsgOutboundOrderCancel> list = msgOutboundOrderCancelDao.findMsgOutboundOrderCancelMail();
        // List<LogQueue> list=logQueueDao.queryLogQueueEmail(new
        // BeanPropertyRowMapperExt<LogQueue>(LogQueue.class));
        if (list.size() > 0) {
            // 查询系统常量表 收件人
            ChooseOption option = chooseOptionDao.findByCategoryCode("ERROR_NOTICE");
            if (!StringUtil.isEmpty(option.getOptionValue())) {
                // 传人邮件模板的CODE -- 查询String类型可用的模板
                MailTemplate template = mailTemplateDao.findTemplateByCode("IDS_EMAIL");
                if (template != null) {
                    String skuCode = ""; // 用于存储失败的作业单号，以英文逗号隔开
                    for (MsgOutboundOrderCancel cancel : list) {
                        skuCode += cancel.getBatchId() + ",";
                    }
                    int i = template.getMailBody().indexOf("{");
                    System.out.println(i);
                    String mailBody =
                            template.getMailBody().substring(0, template.getMailBody().indexOf("{")) + skuCode.substring(0, skuCode.length() - 1) + template.getMailBody().substring(template.getMailBody().indexOf("}") + 1, template.getMailBody().length());// 邮件内容
                    String subject = template.getSubject();// 标题
                    String addressee = option.getOptionValue(); // 查询收件人
                    boolean bool = false;
                    bool = MailUtil.sendMail(subject, addressee, "", mailBody, false, null);
                    if (bool) {
                        // 通知成功修改标识
                        for (MsgOutboundOrderCancel cancel : list) {
                            cancel.setIsMail(1);
                            msgOutboundOrderCancelDao.save(cancel);
                        }
                    } else {
                        log.debug("邮件通知失败,请联系系统管理员！");
                    }
                } else {
                    log.debug("邮件模板不存在或被禁用");
                }
            } else {
                log.debug("邮件通知失败,收件人为空！");
            }
        }
    }

    /**
     * 推送推送利丰销售单和换货单失败定时任务预警邮件发送
     */
    @Override
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void salesOrderSendToLFFailedMailIds() {
        log.debug("salesOrderSendToLFFailedMailIds===================>start");
        // 利丰仓库预警source配置
        ChooseOption op = chooseOptionDao.findByCategoryCodeAndKey(Constants.VIM_WH_SOURCE_IDS, Constants.VIM_IDS_SEND_FAILED_SET);
        // 超时预警时间配置
        ChooseOption timeSets = chooseOptionDao.findByCategoryCodeAndKey(Constants.VIM_WH_SOURCE_IDS, Constants.VIM_IDS_SEND_FAILED_TIMES_SET);
        String time = timeSets == null ? "6" : (timeSets.getOptionValue() == null ? "6" : timeSets.getOptionValue());
        if (op != null && op.getOptionValue() != null) {
            String[] sources = StringUtils.split(op.getOptionValue(), ",");
            List<String> list = Arrays.asList(sources);
            if (list != null && list.size() > 0) {
                // key==>source;values==>错误数量统计
                Map<String, String> map = new HashMap<String, String>();
                for (String source : list) {
                    String sum = msgOutboundOrderDao.findSalesOrderSendToLFFailedMailIds(source, Long.parseLong(time), new SingleColumnRowMapper<String>(String.class));
                    if (!StringUtils.isBlank(sum)) {
                        map.put(source, sum);
                    }
                }
                // 收件人配置
                ChooseOption c = chooseOptionDao.findByCategoryCodeAndKey(Constants.VIM_WH_SOURCE_IDS, Constants.VIM_IDS_SEND_FAILED_RECEIVER);
                if (!StringUtil.isEmpty(c.getOptionValue())) {
                    // 传人邮件模板的CODE -- 查询String类型可用的模板
                    MailTemplate template = mailTemplateDao.findTemplateByCode("IDS_SEND_FAILED_NOTICE");
                    if (template != null) {
                        StringBuffer sb = new StringBuffer();
                        String mailBody = template.getMailBody().substring(0, template.getMailBody().indexOf("{")) + time + template.getMailBody().substring(template.getMailBody().indexOf("}") + 1, template.getMailBody().length());// 邮件内容
                        sb.append(mailBody + " \n");
                        for (Map.Entry<String, String> errors : map.entrySet()) {
                            sb.append(errors.getKey() + ": \n");
                            String[] statusAndCounts = StringUtils.split(errors.getValue(), ",");
                            Boolean flag = false;
                            if (statusAndCounts != null && statusAndCounts.length > 0) {
                                for (String sc : statusAndCounts) {
                                    String[] scs = StringUtils.split(sc, "==>");
                                    switch (Integer.parseInt(scs[0])) {
                                        case 1:
                                            if (Integer.parseInt(scs[1]) > 0) {
                                                flag = true;
                                                sb.append("未推送：" + scs[1] + "; ");
                                            }
                                            break;
                                        case 2:
                                            if (Integer.parseInt(scs[1]) > 0) {
                                                flag = true;
                                                sb.append("创批但未推送：" + scs[1] + "; ");
                                            }
                                            break;
                                        case -1:
                                            if (Integer.parseInt(scs[1]) > 0) {
                                                flag = true;
                                                sb.append("推送失败：" + scs[1] + "; ");
                                            }
                                            break;
                                        case 5:
                                            if (Integer.parseInt(scs[1]) > 0) {
                                                flag = true;
                                                sb.append("已推送但未接收到利丰创单反馈：" + scs[1] + "; ");
                                            }
                                            break;
                                        default:
                                            break;
                                    }
                                }
                            }
                            if (!flag) {
                                sb.append("暂无异常！\n");
                            }
                            sb.append("\n========================================================== \n");
                        }
                        if (!map.isEmpty()) {
                            boolean bool = false;
                            bool = MailUtil.sendMail(template.getSubject(), c.getOptionValue(), "", sb.toString(), false, null);
                            if (bool) {
                                log.debug("邮件通知成功！");
                            } else {
                                log.debug("邮件通知失败,请联系系统管理员！");
                            }
                        }
                    } else {
                        log.debug("邮件模板不存在或被禁用！");
                    }
                } else {
                    log.debug("邮件通知失败,收件人为空！");
                }
            }
        }
        log.debug("salesOrderSendToLFFailedMailIds===================>end");
    }
}
