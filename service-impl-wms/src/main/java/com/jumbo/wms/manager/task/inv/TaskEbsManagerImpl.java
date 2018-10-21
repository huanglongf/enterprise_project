package com.jumbo.wms.manager.task.inv;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import loxia.dao.support.BeanPropertyRowMapperExt;
import loxia.utils.DateUtil;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import cn.baozun.bh.util.JSONUtil;

import com.baozun.scm.baseservice.message.common.MessageCommond;
import com.baozun.scm.baseservice.message.rocketmq.service.server.RocketMQProducerServer;
import com.jumbo.dao.baseinfo.WarehouseDao;
import com.jumbo.dao.mail.MailTemplateDao;
import com.jumbo.dao.msg.MessageConfigDao;
import com.jumbo.dao.system.ChooseOptionDao;
import com.jumbo.dao.warehouse.DeailyInventoryDao;
import com.jumbo.dao.warehouse.EbsInventoryDao;
import com.jumbo.dao.warehouse.InventoryDao;
import com.jumbo.dao.warehouse.LogQueueDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.dao.warehouse.WholeInventorySyncToPACDao;
import com.jumbo.dao.warehouse.WholeInventorySynchroDao;
import com.jumbo.util.FormatUtil;
import com.jumbo.util.HttpClientUtil;
import com.jumbo.util.MailUtil;
import com.jumbo.util.SFTPUtil;
import com.jumbo.util.StringUtil;
import com.jumbo.util.UUIDUtil;
import com.jumbo.util.zip.AppSecretUtil;
import com.jumbo.wms.Constants;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.system.ChooseOptionManager;
import com.jumbo.wms.manager.task.CommonConfigManager;
import com.jumbo.wms.manager.util.MqStaticEntity;
import com.jumbo.wms.model.baseinfo.Warehouse;
import com.jumbo.wms.model.mail.MailTemplate;
import com.jumbo.wms.model.msg.MessageConfig;
import com.jumbo.wms.model.msg.MongoDBMessage;
import com.jumbo.wms.model.system.ChooseOption;
import com.jumbo.wms.model.warehouse.DeailyInventory;
import com.jumbo.wms.model.warehouse.EbsInventory;
import com.jumbo.wms.model.warehouse.Inventory;
import com.jumbo.wms.model.warehouse.LogQueue;
import com.jumbo.wms.model.warehouse.LogQueueCode;
import com.jumbo.wms.model.warehouse.LogQueueCommand;
import com.opensymphony.xwork2.Action;

/**
 * 
 * @author jinlong.ke
 * 
 */
@Transactional
@Service("taskEbsManager")
public class TaskEbsManagerImpl extends BaseManagerImpl implements TaskEbsManager {
    /**
     * 
     */
    private static final long serialVersionUID = 3420957175379374188L;
    protected static final Logger log = LoggerFactory.getLogger(BaseManagerImpl.class);
    private static String blank = "	";
    private static String EBS_INVENTORY = "EBS_INVENTORY_";
    private static String SALES_INVENTORY_ = "SALES_INVENTORY_";
    private static String INVENOTRY_QTY_ = "INVENOTRY_QTY_";
    private final String INVSYNC_EMAIL = "INVSYNC_EMAIL";
    @Autowired
    private EbsInventoryDao ebsInventoryDao;
    @Autowired
    private CommonConfigManager configManager;
    @Autowired
    private DeailyInventoryDao deailyInventoryDao;
    @Autowired
    private LogQueueDao logQueueDao;
    @Autowired
    private InventoryDao inventoryDao;
    @Autowired
    private ChooseOptionManager chooseOptionManager;
    @Autowired
    private MailTemplateDao mailTemplateDao;
    @Autowired
    private ChooseOptionDao chooseOptionDao;
    @Autowired
    private WholeInventorySynchroDao wholeInventorySynchroDao;
    @Autowired
    private WholeInventorySyncToPACDao wholeInventorySyncToPACDao;
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private WarehouseDao warehouseDao;
    @Autowired
    private MessageConfigDao messageConfigDao;
    @Autowired
    private RocketMQProducerServer producerServer;
    @Resource(name = "mongoTemplate")
    private MongoOperations mongoOperation;

    public void ebsInventory() {
        log.debug("EbsInventory.................start ");
        Map<String, String> cfg = configManager.getEbsFTPConfig();
        List<EbsInventory> ebs = ebsInventoryDao.queryEbsInventory();
        String localDir = cfg.get(Constants.VMI_FTP_UP_LOCALPATH) + "ebs/";

        if (ebs == null || ebs.isEmpty()) {
            log.debug("nikeStockReceives is null.................app exit ");
            return;
        } else {
            File f = new File(localDir);
            for (File toFile : f.listFiles()) {
                try {
                    FileUtils.copyFileToDirectory(toFile, new File(cfg.get(Constants.NIKE_FTP_UP_LOCALPATH_BACKUP)), true);
                    toFile.delete();
                } catch (IOException e) {
                    log.error("", e);
                }
            }
            BufferedWriter br = null;
            String fileName = EBS_INVENTORY + String.valueOf(FormatUtil.formatDate(new Date(), "yyyyMMdd_HHmmss"));
            try {

                br = new BufferedWriter(new FileWriter(new File(localDir + fileName)));
                batchWriteDataFileForInboundReceive(ebs, br);
                try {
                    SFTPUtil.sendFile(cfg.get(Constants.VMI_FTP_URL), cfg.get(Constants.VMI_FTP_PORT), cfg.get(Constants.VMI_FTP_USERNAME), cfg.get(Constants.VMI_FTP_PASSWORD), cfg.get(Constants.VMI_FTP_UPPATH) + "ebs/", localDir,
                            cfg.get(Constants.NIKE_FTP_UP_LOCALPATH_BACKUP));
                } catch (Exception e) {
                    log.error("", e);
                }
            } catch (IOException e) {
                log.error("", e);
            }
        }

    }

    @Transactional
    public void inBoundTransferInBoundWriteToFile(String localDir) {

    }

    /**
     * 入库反馈写文件 - 批量将数据写入到本地文件
     */
    private void batchWriteDataFileForInboundReceive(List<EbsInventory> ebs, BufferedWriter br) {
        log.debug("EbsInventory.................正在写入 ");
        for (EbsInventory ebsInventory : ebs) {
            try {
                br.write(buildDataForInboundReceive(ebsInventory));
                br.newLine();
            } catch (IOException e) {
                log.error("", e);
            }
        }
        try {
            br.flush();
        } catch (IOException e) {
            log.error("", e);
        } finally {
            try {
                br.close();
                br = null;
            } catch (IOException e) {
                log.error("", e);
            }
        }
    }


    private String buildDataForInboundReceive(EbsInventory ebsInventory) {
        // 数据写入dat文件
        StringBuilder line = new StringBuilder();
        //
        line.append(StringUtils.hasLength(ebsInventory.getCustomerCode()) ? ebsInventory.getCustomerCode() : "	");
        line.append(blank);
        line.append(FormatUtil.formatDate(ebsInventory.getCreateTime(), "yyyyMMddHHmmss"));
        line.append(blank);
        line.append(StringUtils.hasLength(ebsInventory.getChannelCode()) ? ebsInventory.getChannelCode() : "");
        line.append(blank);
        line.append("");
        line.append(blank);
        line.append(StringUtils.hasLength(ebsInventory.getCustomerSkuCode()) ? ebsInventory.getCustomerSkuCode() : "");
        line.append(blank);
        line.append(StringUtils.hasLength(ebsInventory.getBatchCode()) ? ebsInventory.getBatchCode() : "");
        line.append(blank);
        line.append(StringUtils.hasLength(ebsInventory.getTotalQty().toString()) ? ebsInventory.getTotalQty() : "");
        line.append(blank);
        line.append(StringUtils.hasLength(ebsInventory.getTotalQty().toString()) ? ebsInventory.getTransQty() : "");
        line.append(blank);
        return line.toString();
    }

    @Override
    public void salesInventory() {
        log.debug("SalesInventory.................start ");
        Date da = new Date();
        deailyInventoryDao.addDeailyInventory(da);
        Map<String, String> cfg = configManager.getEbsFTPConfig();
        List<Long> ouid = deailyInventoryDao.queryDeailyInventoryOuid(da, new SingleColumnRowMapper<Long>(Long.class));
        String localDir = cfg.get(Constants.VMI_FTP_UP_LOCALPATH) + "inv/";
        String fileName = SALES_INVENTORY_ + String.valueOf(FormatUtil.formatDate(new Date(), "yyyyMMdd_HHmmss"));
        String url = localDir + fileName;
        for (int i = 0; i < ouid.size(); i++) {
            List<DeailyInventory> dea = deailyInventoryDao.queryDeailyInventory(da, ouid.get(i), new BeanPropertyRowMapperExt<DeailyInventory>(DeailyInventory.class));
            batchWriteDataFileForInboundReceive1(dea, url);
        }
        try {
            SFTPUtil.sendFile(cfg.get(Constants.VMI_FTP_URL), cfg.get(Constants.VMI_FTP_PORT), cfg.get(Constants.VMI_FTP_USERNAME), cfg.get(Constants.VMI_FTP_PASSWORD), cfg.get(Constants.VMI_FTP_UPPATH) + "inv/", localDir,
                    cfg.get(Constants.NIKE_FTP_UP_LOCALPATH_BACKUP));
            deailyInventoryDao.updateDeailyInventoryLog(da);
            deailyInventoryDao.addDeailyInventoryLog();
            deailyInventoryDao.deleteDeailyInventoryLog();
        } catch (Exception e) {
            log.error("", e);
            Map<String, String> option = chooseOptionManager.getOptionByCategoryCode(INVSYNC_EMAIL);
            MailTemplate template = mailTemplateDao.findTemplateByCode(INVSYNC_EMAIL);
            String mailBody = MessageFormat.format(template.getMailBody(), "全量库存", "发送");
            MailUtil.sendMail(template.getSubject(), option.get(Constants.RECEIVER_ADDRESS), option.get(Constants.CARBON_COPY), mailBody, false, null);
            log.error("Total inv send failure........Email end");
        }

    }

    /**
     * 入库反馈写文件 - 批量将数据写入到本地文件
     */
    private void batchWriteDataFileForInboundReceive1(List<DeailyInventory> dea, String url) {
        BufferedWriter br = null;
        try {
            br = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(url, true)));
        } catch (FileNotFoundException e1) {
            throw new BusinessException(e1);
        }
        for (DeailyInventory deailyInventory : dea) {
            try {

                br.write(buildDataForInboundReceive1(deailyInventory));
                br.newLine();
            } catch (IOException e) {
                log.error("", e);
            }
        }
        try {
            br.flush();
        } catch (IOException e) {
            throw new BusinessException(e);
        } finally {
            try {
                br.close();
                br = null;
            } catch (IOException e) {
                throw new BusinessException(e);
            }
        }
    }

    private String buildDataForInboundReceive1(DeailyInventory deailyInventory) {
        // 数据写入dat文件
        StringBuilder line = new StringBuilder();
        line.append(StringUtils.hasLength(deailyInventory.getCustomerCode()) ? deailyInventory.getCustomerCode() : "");
        line.append(blank);
        line.append(FormatUtil.formatDate(deailyInventory.getCreateTime(), "yyyyMMddHHmmss"));
        line.append(blank);
        line.append(StringUtils.hasLength(deailyInventory.getChannelCode()) ? deailyInventory.getChannelCode() : "");
        line.append(blank);
        line.append(StringUtils.hasLength(deailyInventory.getWhouCode()) ? deailyInventory.getWhouCode() : "");
        line.append(blank);
        line.append(StringUtils.hasLength(deailyInventory.getCustomerSkuCode()) ? deailyInventory.getCustomerSkuCode() : "");
        line.append(blank);
        line.append(StringUtils.hasLength(deailyInventory.getSalesAvailQty().toString()) ? deailyInventory.getSalesAvailQty() : "");
        line.append(blank);
        return line.toString();
    }


    public void salesInventoryOms(String batchId) {
        List<LogQueueCode> queues = logQueueDao.queryLogQueue(batchId, new BeanPropertyRowMapperExt<LogQueueCode>(LogQueueCode.class));
        if (queues.size() > 0) {
            List<LogQueue> logQueues = new ArrayList<LogQueue>();
            List<String> details = new ArrayList<String>();
            for (LogQueueCode logQueueCode : queues) {
                LogQueueCommand commd = new LogQueueCommand();
                LogQueue logQueue = new LogQueue();
                commd.setChannelCode(logQueueCode.getChannelCode());
                if (logQueueCode.getSalesAvailQty() < 0) {
                    commd.setDirection(2);
                } else {
                    commd.setDirection(1);
                }
                commd.setOpTime(FormatUtil.formatDate(logQueueCode.getCreateTime(), "yyyyMMddHHmmss"));
                commd.setQty(Math.abs(logQueueCode.getSalesAvailQty()) + "");
                commd.setSkuCode(logQueueCode.getSkuCode());
                commd.setWhCode(logQueueCode.getWhouCode());
                String detail = JSONUtil.beanToJson(commd);
                logQueue.setId(logQueueCode.getId());
                details.add(detail);
                logQueues.add(logQueue);
            }
            // 切换RocketMQ
            // 查询Message消息配置表
            final String topic = MqStaticEntity.WMS3_INCREMENTAL_INVENTORY;
            List<MessageConfig> configs = messageConfigDao.findMessageConfigByParameter(null, topic, null, new BeanPropertyRowMapper<MessageConfig>(MessageConfig.class));
            if (configs == null || (configs != null && configs.isEmpty())) {
                throw new BusinessException(ErrorCode.MESSAGE_CONGIF_MISSING, topic);
            }
            // 开关
            if (configs.get(0).getIsOpen() == 1) {
                // 发送MQ
                MessageCommond mc = new MessageCommond();
                mc.setMsgId(batchId + "," + System.currentTimeMillis() + UUIDUtil.getUUID());
                mc.setMsgType(configs.get(0).getMsgType());
                mc.setSendTime(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
                String msgBody = details.toString();
                mc.setMsgBody(msgBody);
                mc.setIsMsgBodySend(false);
                log.debug("wms3_incremental_inventory send message begin:" + batchId);
                try {
                    producerServer.sendDataMsgConcurrently(MQ_WMS3_INCREMENTAL_INVENTORY, mc);
                } catch (Exception e) {
                    log.error("wms3_incremental_inventory error:" + batchId, e);
                    logQueueDao.updateLogQueue(batchId, queues.get(0).getErrorCount() == null ? 0 : queues.get(0).getErrorCount() + 1);
                }
                log.debug("wms3_incremental_inventory send message end:" + batchId);
                // 保存进mongodb
                MongoDBMessage mdbm = new MongoDBMessage();
                BeanUtils.copyProperties(mc, mdbm);
                mdbm.setOtherUniqueKey(batchId + "");
                mdbm.setMsgBody(msgBody);
                mdbm.setMemo(MQ_WMS3_INCREMENTAL_INVENTORY);
                mongoOperation.save(mdbm);
                log.debug("wms3_im_inv_sku_flow keep message into mongodb end:" + batchId);
                saveLog(batchId);
            } else {
                // 原http发送逻辑
                Map<String, String> params = new HashMap<String, String>();
                // 获取时间
                // String callDate = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());

                // 用户名
                params.put("username", SALES_NAME);
                // 密码
                params.put("source", SALES_SOURCE);
                // 时间
                params.put("calldate", batchId);
                // 拼接 证书 key 值
                String secretkey = AppSecretUtil.getMD5Str("bzwmsqazasdfg" + batchId);
                params.put("secretkey", secretkey);
                params.put("batchCode", batchId);
                params.put("details", details.toString());



                boolean status = false;
                int i = 0;
                do {
                    status = httpPost(params);
                    if (status) {
                        // 处理成功
                        // 记录日志
                        saveLog(batchId);
                    }
                    i++;
                } while (!status && i < 3);
                if (!status) {
                    logQueueDao.updateLogQueue(batchId, queues.get(0).getErrorCount() == null ? 0 : queues.get(0).getErrorCount() + i);
                }
            }
        }
    }

    public boolean httpPost(Map<String, String> params) {
        try {
            String rs = HttpClientUtil.httpPost(SALES_URL, params);
            Map<String, Object> rsMap = JSONUtil.jsonToMap(rs, true);
            String result = (String) rsMap.get("result");
            if (Action.SUCCESS.equals(result)) {
                return true;
            } else {
                String errorMsg = (String) rsMap.get("message");
                log.error(errorMsg + "======================" + params.get("batchCode"));
                return false;
            }
        } catch (Exception e) {
            log.error("", e);
            return false;
        }
    }

    @Transactional
    public void saveLog(String batchId) {
        logQueueDao.updateLogQueueStatus(batchId);
        logQueueDao.addLogQueue(batchId);
        logQueueDao.deleteLogQueue();
    }

    /**
     * 补充全量库存逻辑<br/>
     * 文件格式 INVENTORY_QTY_yyyyMMdd_HHmmss<br/>
     * 内容格式<br/>
     * *******************<br/>
     * WHCODE qty<br/>
     * TOTAL qty<br/>
     * *******************
     */
    @Override
    public void replenishForSalesInventory() {
        log.debug("TaskEbsManagerImpl.replenishForSalesInventory-------start");
        List<Inventory> list = inventoryDao.getNeedSendDataUnit(new BeanPropertyRowMapper<Inventory>(Inventory.class));
        Map<String, String> cfg = configManager.getCommonFTPConfig(Constants.EBS_INVENTORY);
        String localDir = cfg.get(Constants.VMI_FTP_UP_LOCALPATH) + "inv/";
        File f = new File(localDir);
        if (!f.exists()) {
            f.mkdirs();
        }
        if (list == null || list.isEmpty()) {
            log.debug("TaskEbsManagerImpl.replenishForSalesInventory.data is null----app exit");
            return;
        } else {
            BufferedWriter br = null;
            String fileName = INVENOTRY_QTY_ + String.valueOf(FormatUtil.formatDate(new Date(), "yyyyMMdd_HHmmss"));
            try {

                br = new BufferedWriter(new FileWriter(new File(localDir + fileName)));
                batchWriteDataFileForInventoryQty(list, br);
                try {
                    SFTPUtil.sendFile(cfg.get(Constants.VMI_FTP_URL), cfg.get(Constants.VMI_FTP_PORT), cfg.get(Constants.VMI_FTP_USERNAME), cfg.get(Constants.VMI_FTP_PASSWORD), cfg.get(Constants.VMI_FTP_UPPATH) + "inv/", localDir,
                            cfg.get(Constants.NIKE_FTP_UP_LOCALPATH_BACKUP));
                    log.debug("TaskEbsManagerImpl.replenishForSalesInventory-------end");
                } catch (Exception e) {
                    log.error("TaskEbsManagerImpl.replenishForSalesInventory-------Exception");
                    log.error("", e);
                    Map<String, String> option = chooseOptionManager.getOptionByCategoryCode(INVSYNC_EMAIL);
                    MailTemplate template = mailTemplateDao.findTemplateByCode(INVSYNC_EMAIL);
                    String mailBody = MessageFormat.format(template.getMailBody(), "全量库存补充", "发送");
                    MailUtil.sendMail(template.getSubject(), option.get(Constants.RECEIVER_ADDRESS), option.get(Constants.CARBON_COPY), mailBody, false, null);
                    log.error("Total inv send failure........Email end");
                }
            } catch (IOException e) {
                log.error("TaskEbsManagerImpl.replenishForSalesInventory-------IOException");
                log.error("", e);
                Map<String, String> option = chooseOptionManager.getOptionByCategoryCode(INVSYNC_EMAIL);
                MailTemplate template = mailTemplateDao.findTemplateByCode(INVSYNC_EMAIL);
                String mailBody = MessageFormat.format(template.getMailBody(), "全量库存补充", "发送");
                MailUtil.sendMail(template.getSubject(), option.get(Constants.RECEIVER_ADDRESS), option.get(Constants.CARBON_COPY), mailBody, false, null);
                log.error("Total inv send failure........Email end");
            }
        }
    }

    private void batchWriteDataFileForInventoryQty(List<Inventory> list, BufferedWriter br) {
        for (Inventory inv : list) {
            try {
                br.write(buildDataForInventoryQty(inv));
                br.newLine();
            } catch (IOException e) {
                log.error("TaskEbsManagerImpl.batchWriteDataFileForInventoryQty-------IOException1");
                log.error("", e);
            }
        }
        try {
            br.flush();
        } catch (IOException e) {
            log.error("TaskEbsManagerImpl.batchWriteDataFileForInventoryQty-------IOException2");
            log.error("", e);
        } finally {
            try {
                br.close();
                br = null;
            } catch (IOException e) {
                log.error("TaskEbsManagerImpl.batchWriteDataFileForInventoryQty-------IOException3");
                log.error("", e);
            }
        }
    }

    private String buildDataForInventoryQty(Inventory inv) {
        // 数据写入dat文件
        StringBuilder line = new StringBuilder();
        line.append(inv.getOwner());
        line.append(blank);
        line.append(inv.getQuantity());
        return line.toString();
    }

    /**
     * 将结果集备份至日志表，删除结果集 添加事物
     */
    @Transactional
    public void copyDataToLog() {
        wholeInventorySynchroDao.addTotalInventoryLog();
        wholeInventorySynchroDao.deleteTotalInventory();
    }

    /**
     * 将结果集备份到PAC全量库存日志表，删除结果集，添加事物
     */
    @Transactional
    public void copyInvDataToPACLog() {
        wholeInventorySyncToPACDao.addTotalInventoryLog();
        wholeInventorySyncToPACDao.deleteTotalInventory();
    }

    @Override
    public void salesInventoryOmsEmail() {
        log.debug("salesInventoryOmsEmail===================>start");
        List<LogQueue> list = logQueueDao.queryLogQueueEmail(new BeanPropertyRowMapperExt<LogQueue>(LogQueue.class));
        if (list.size() > 0) {
            // 查询系统常量表 收件人
            ChooseOption option = chooseOptionDao.findByCategoryCode("ERROR_NOTICE");
            if (!StringUtil.isEmpty(option.getOptionValue())) {
                // 传人邮件模板的CODE -- 查询String类型可用的模板
                MailTemplate template = mailTemplateDao.findTemplateByCode("INCREMENTINV_EMAIL");
                if (template != null) {
                    String skuCode = ""; // 用于存储失败的作业单号，以英文逗号隔开
                    for (LogQueue logQueue : list) {
                        skuCode += logQueue.getBatchId() + ",";
                    }
                    String mailBody =
                            template.getMailBody().substring(0, template.getMailBody().indexOf("{")) + skuCode.substring(0, skuCode.length() - 1) + template.getMailBody().substring(template.getMailBody().indexOf("}") + 1, template.getMailBody().length());// 邮件内容
                    String subject = template.getSubject();// 标题
                    String addressee = option.getOptionValue(); // 查询收件人
                    boolean bool = false;
                    bool = MailUtil.sendMail(subject, addressee, "", mailBody, false, null);
                    if (bool) {
                        // 通知成功修改标识
                        for (LogQueue LogQueue : list) {
                            logQueueDao.updateLogQueueEmail(LogQueue.getBatchId());
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

    @Override
    public void upgradeEmail() {
        log.debug("upgradeEmail===================>start");
        List<Long> list = staDao.queryUpgrade(new SingleColumnRowMapper<Long>(Long.class));
        if (list.size() > 0) {
            for (Long ouId : list) {
                Warehouse warehouse = warehouseDao.getByOuId(ouId);
                if (!StringUtil.isEmpty(warehouse.getNotifyTransUpgradeUser())) {
                    // 传人邮件模板的CODE -- 查询String类型可用的模板
                    MailTemplate template = mailTemplateDao.findTemplateByCode("INCREMENTINV_EMAIL");
                    if (template != null) {
                        String skuCode = "目前仓库存在未升单数据，请及时操作！"; // 用于存储失败的作业单号，以英文逗号隔开
                        String mailBody =
                                template.getMailBody().substring(0, template.getMailBody().indexOf("{")) + skuCode.substring(0, skuCode.length() - 1)
                                        + template.getMailBody().substring(template.getMailBody().indexOf("}") + 1, template.getMailBody().length());// 邮件内容
                        String subject = template.getSubject();// 标题
                        String addressee = warehouse.getNotifyTransUpgradeUser(); // 查询收件人
                        boolean bool = false;
                        bool = MailUtil.sendMail(subject, addressee, "", mailBody, false, null);
                        if (bool) {
                            // 通知成功

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
    }
}
