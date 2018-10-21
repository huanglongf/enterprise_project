package com.jumbo.wms.manager.task.invflow;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.baozun.scm.baseservice.message.common.MessageCommond;
import com.baozun.scm.baseservice.message.rocketmq.service.server.RocketMQProducerServer;
import com.baozun.task.annotation.SingleTaskLock;
import com.baozun.utilities.json.JsonUtil;
import com.jumbo.dao.invflow.WmsIMOccupiedAndReleaseDao;
import com.jumbo.dao.invflow.WmsSkuInventoryFlowDao;
import com.jumbo.dao.mail.MailTemplateDao;
import com.jumbo.dao.msg.MessageConfigDao;
import com.jumbo.dao.system.ChooseOptionDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.util.FormatUtil;
import com.jumbo.util.MailUtil;
import com.jumbo.util.StringUtil;
import com.jumbo.util.UUIDUtil;
import com.jumbo.wms.Constants;
import com.jumbo.wms.daemon.SkuInvFlowTask;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.util.MqStaticEntity;
import com.jumbo.wms.model.invflow.WmsOccupiedAndRelease;
import com.jumbo.wms.model.invflow.WmsSkuInventoryFlow;
import com.jumbo.wms.model.mail.MailTemplate;
import com.jumbo.wms.model.msg.MessageConfig;
import com.jumbo.wms.model.msg.MongoDBMessage;
import com.jumbo.wms.model.system.ChooseOption;
import com.jumbo.wms.model.warehouse.StockTransApplication;

import loxia.dao.Pagination;
import loxia.utils.DateUtil;

@Transactional
// @MsgClassAnnotation("SkuInvFlowTaskImpl")
public class SkuInvFlowTaskImpl extends BaseManagerImpl implements SkuInvFlowTask {

    private static final long serialVersionUID = 4758072495377888892L;
    protected static final Logger log = LoggerFactory.getLogger(SkuInvFlowTaskImpl.class);
    private static final String categoryCode = "SYS_EMAIL";

    @Autowired
    private ChooseOptionDao chooseOptionDao;
    @Autowired
    private WmsSkuInventoryFlowDao wmsSkuInventoryFlowDao;
    @Autowired
    private MessageConfigDao messageConfigDao;
    @Autowired
    private WmsIMOccupiedAndReleaseDao wmsIMOccupiedAndReleaseDao;
    @Autowired
    private RocketMQProducerServer producerServer;
    @Resource(name = "mongoTemplate")
    private MongoOperations mongoOperation;
    @Autowired
    private MailTemplateDao mailTemplateDao;
    @Autowired
    private StockTransApplicationDao staDao;

    @Value("${levis.owner}")
    public String owner;

    @Value("${levis.whCode}")
    public String whCode;


    @Override
    @SingleTaskLock(timeout = Constants.TASK_LOCK_TIMEOUT, value = Constants.TASK_LOCK_VALUE)
    public void transferExpireDataIntoLog() {
        log.info("SkuInvFlowTask start.........!");
        // 转移过期数据到日志表
        ChooseOption op = chooseOptionDao.findByCategoryCodeAndKey(Constants.SKU_INV_FLOW, Constants.INV_FLOW_EXP_DATE);
        String num = op == null ? "-7" : (op.getOptionValue() == null ? "-7" : op.getOptionValue());// 默认转移7天前数据
        Date date = DateUtils.addDays(new Date(), Integer.parseInt(num));
        String expDate = FormatUtil.formatDate(date, "yyyy-MM-dd");
        if (log.isDebugEnabled()) {
            log.debug("num:" + num + "--->" + expDate);
        }
        String aimTableName = "t_wh_sku_inv_flow_log";
        String aimTableColumn =
                "st_log_id,upc,customer_Code,store_Code,ec_Order_Code,odo_Type,revision_Qty,inv_Status," + "inv_Type,batch_Number,mfg_Date,exp_Date,country_Of_Origin,inv_Attr1,inv_Attr2,inv_Attr3,inv_Attr4,"
                        + "inv_Attr5,inv_TransactionType,wh_Code,create_Time,log_create_Time";
        String sql =
                "select inv.st_log_id," + "       inv.upc," + "       inv.customer_Code," + "       inv.store_Code," + "       inv.ec_Order_Code," + "       inv.odo_Type," + "       inv.revision_Qty," + "       inv.inv_Status," + "       inv.inv_Type,"
                        + "       inv.batch_Number," + "       inv.mfg_Date," + "       inv.exp_Date," + "       inv.country_Of_Origin," + "       inv.inv_Attr1," + "       inv.inv_Attr2," + "       inv.inv_Attr3," + "       inv.inv_Attr4,"
                        + "       inv.inv_Attr5," + "       inv.inv_TransactionType," + "       inv.wh_Code," + "       inv.create_Time," + "       sysdate log_create_Time" + " from t_wh_sku_inv_flow inv" + " where inv.create_time < to_date('" + expDate
                        + "','yyyy-mm-dd')" + "       and (inv.status='1' or inv.status='20' or inv.status is null)" + "     and (inv.error_count<5 or inv.error_count=99 or inv.error_count is null)" + "       order by inv.id";
        int result = executeLargeDateInsertProcedure(aimTableName, aimTableColumn, sql);
        if (result == 0) {
            log.error("库存流水转入日志表失败！" + new Date().toString());
            return;
        }
        // 删除中间表过期数据
        wmsSkuInventoryFlowDao.removeExpireData(expDate);
        log.info("SkuInvFlowTask end.........!");
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
        Map<String, Object> result = wmsSkuInventoryFlowDao.executeSp("sp_largedata_insert", sqlParameters, params);
        Object res = result.get("return_result");
        return Integer.parseInt(res.toString());
    }

    @Override
    @SingleTaskLock(timeout = Constants.TASK_LOCK_TIMEOUT, value = Constants.TASK_LOCK_VALUE)
    public void transferOcciedExpireDataIntoLog() {
        try {
            log.info("SkuInvFlowTask transferOcciedExpireDataIntoLog start.........!");
            // 转移过期数据到日志表
            ChooseOption op = chooseOptionDao.findByCategoryCodeAndKey(Constants.SKU_OCCIED_AND_RELEASE, Constants.INV_FLOW_EXP_DATE);
            String num = op == null ? "-7" : (op.getOptionValue() == null ? "-7" : op.getOptionValue());// 默认转移7天前数据
            Date date = DateUtils.addDays(new Date(), Integer.parseInt(num));
            String expDate = FormatUtil.formatDate(date, "yyyy-MM-dd");
            if (log.isDebugEnabled()) {
                log.debug("num:" + num + "--->" + expDate);
            }
            // 转移中间表数据
            wmsIMOccupiedAndReleaseDao.transferExpireData(expDate, 1);
            // 添加库内移动归档
            wmsIMOccupiedAndReleaseDao.transferExpireData(expDate, 20);
            // 删除过期数据
            wmsIMOccupiedAndReleaseDao.removeExpireData(expDate, 1);
            wmsIMOccupiedAndReleaseDao.removeExpireData(expDate, 20);
            log.info("SkuInvFlowTask transferOcciedExpireDataIntoLog end.........!");
        } catch (Exception e) {
            log.error("IM移动占用或取消释放数据到日志表异常！", e);
        }
    }

    /**
     * 库存流水推送IM
     */
    @Override
    @SingleTaskLock(timeout = Constants.TASK_LOCK_TIMEOUT, value = Constants.TASK_LOCK_VALUE)
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void skuInvFlowToImByMQ() {
        final String topic = MqStaticEntity.WMS3_IM_INV_SKU_FLOW;
        Long count = wmsSkuInventoryFlowDao.getUnpushedSkuInvFlowCount(0, new SingleColumnRowMapper<Long>(Long.class));
        log.debug("wms3_im_inv_sku_flow count=" + count);
        if (count == null || (count != null && count.intValue() == 0)) {
            return;
        }
        // 查询Message消息配置表
        List<MessageConfig> configs = messageConfigDao.findMessageConfigByParameter(null, topic, null, new BeanPropertyRowMapper<MessageConfig>(MessageConfig.class));
        if (configs == null || (configs != null && configs.isEmpty())) {
            throw new BusinessException(ErrorCode.MESSAGE_CONGIF_MISSING, topic);
        }
        // 开关
        if (configs.get(0).getIsOpen() == 0) {
            return;
        }
        // 配置单次推送数量
        ChooseOption op = chooseOptionDao.findByCategoryCodeAndKey(Constants.SKU_INV_FLOW, Constants.SKU_INV_FLOW_NUM);
        String num = op == null ? "300" : (op.getOptionValue() == null ? "300" : op.getOptionValue());// 默认300
        while (true) {
            Pagination<WmsSkuInventoryFlow> p = wmsSkuInventoryFlowDao.getWmsSkuInventoryFlowByStatus(0, Integer.parseInt(num), 0, new BeanPropertyRowMapper<WmsSkuInventoryFlow>(WmsSkuInventoryFlow.class));
            if (p != null && p.getTotalPages() > 0) {
                Pagination<WmsSkuInventoryFlow> p1 = new Pagination<WmsSkuInventoryFlow>();
                p1.setCount(p.getCount());
                p1.setCurrentPage(p.getCurrentPage());
                p1.setSize(p.getSize());
                p1.setStart(p.getStart());
                p1.setTotalPages(p.getTotalPages());
                List<WmsSkuInventoryFlow> flowList = p.getItems();
                List<WmsSkuInventoryFlow> wmsSkuInventoryFlowList = new ArrayList<WmsSkuInventoryFlow>();
                for (WmsSkuInventoryFlow flow : flowList) {
                    if(flow.getStaCode()!=null&&!"".equals(flow.getStaCode())) {
                        StockTransApplication sta=staDao.getByCode(flow.getStaCode());
                        if (sta != null && sta.getOwner() != null && !"".equals(sta.getOwner()) && owner.equals(sta.getOwner())) {
                            flow.setWhCode(whCode);
                            wmsSkuInventoryFlowList.add(flow);
                        } else {
                            wmsSkuInventoryFlowList.add(flow);
                        }
                    }else {
                        wmsSkuInventoryFlowList.add(flow);
                    }
                }
                p1.setItems(wmsSkuInventoryFlowList);
                List<Long> list = new ArrayList<Long>();
                sendMQAndKeepIntoMongoDB(topic, p1, list, configs.get(0));
            } else {
                break;
            }
        }
    }

    /**
     * 库存流水：发送MQ消息+保存进mongoDB+更新数据状态
     * 
     * @param topic
     * @param p
     * @param list
     */
    // @MsgMethodTopicAnnotation(topic = "wms3_im_inv_sku_flow", isSave = true)
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void sendMQAndKeepIntoMongoDB(final String topic, Pagination<WmsSkuInventoryFlow> p, List<Long> list, MessageConfig config) {
        List<Long> sendErrorIds = new ArrayList<Long>();
        // 发送MQ
        MessageCommond mc = new MessageCommond();
        mc.setMsgId(System.currentTimeMillis() + UUIDUtil.getUUID());
        mc.setMsgType(config.getMsgType());
        mc.setSendTime(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        String msgBody = JsonUtil.writeValue(p.getItems());
        mc.setMsgBody(msgBody);
        mc.setIsMsgBodySend(false);
        log.debug("wms3_im_inv_sku_flow send message begin:" + p.getCount());
        try {
            producerServer.sendDataMsgConcurrently(MQ_WMS3_IM_INV_SKU_FLOW, p.getItems().get(0).getStoreCode(), mc);
            for (WmsSkuInventoryFlow skuInvFlow : p.getItems()) {
                list.add(skuInvFlow.getId());
            }
        } catch (Exception e) {
            log.error("wms3_im_inv_sku_flow send message error:" + p.getCount(), e);
            for (WmsSkuInventoryFlow skuInvFlow : p.getItems()) {
                sendErrorIds.add(skuInvFlow.getId());
            }
        }
        log.debug("wms3_im_inv_sku_flow send message end:" + p.getCount());
        // 保存进mongodb
        MongoDBMessage mdbm = new MongoDBMessage();
        BeanUtils.copyProperties(mc, mdbm);
        mdbm.setOtherUniqueKey(mc.getMsgId());
        mdbm.setMsgBody(msgBody);
        mdbm.setMemo(MQ_WMS3_IM_INV_SKU_FLOW);
        mongoOperation.save(mdbm);
        log.debug("wms3_im_inv_sku_flow keep message into mongodb end:" + p.getCount());
        // 更新状态
        wmsSkuInventoryFlowDao.updateWmsSkuInvFlowStatus(list);
        // 更新错误次数
        if (sendErrorIds != null && !sendErrorIds.isEmpty()) {
            wmsSkuInventoryFlowDao.updateSkuInventoryFlowErrorCount(sendErrorIds);
        }
    }

    /**
     * 占用或取消数据推送IM
     */
    @Override
    @SingleTaskLock(timeout = Constants.TASK_LOCK_TIMEOUT, value = Constants.TASK_LOCK_VALUE)
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void occupiedAndReleaseToImByMQ() {
        final String topicOccupied = MqStaticEntity.WMS3_IM_OCCUPIED;
        final String topicRelease = MqStaticEntity.WMS3_IM_RELEASE;
        String[] strs = {topicOccupied, topicRelease};
        Long count = wmsIMOccupiedAndReleaseDao.getUnpushedOccupiedAndRelease(0, new SingleColumnRowMapper<Long>(Long.class));
        log.debug("wms3_im_occupied_release count=" + count);
        if (count == null || (count != null && count.intValue() == 0)) {
            return;
        }
        // 查询Message消息配置表
        List<MessageConfig> configsOccupied = messageConfigDao.findMessageConfigByParameter(null, topicOccupied, null, new BeanPropertyRowMapper<MessageConfig>(MessageConfig.class));
        List<MessageConfig> configsRelease = messageConfigDao.findMessageConfigByParameter(null, topicRelease, null, new BeanPropertyRowMapper<MessageConfig>(MessageConfig.class));
        if (configsOccupied == null || configsRelease == null) {
            throw new BusinessException(ErrorCode.MESSAGE_CONGIF_MISSING, topicOccupied + topicRelease);
        }
        // 配置单次推送数量
        ChooseOption op = chooseOptionDao.findByCategoryCodeAndKey(Constants.SKU_OCCIED_AND_RELEASE, Constants.SKU_OCCIED_AND_RELEASE_NUM);
        String num = op == null ? "300" : (op.getOptionValue() == null ? "300" : op.getOptionValue());// 默认300
        // 发送占用信息
        while (true) {
            Pagination<WmsOccupiedAndRelease> p = wmsIMOccupiedAndReleaseDao.getOccupiedAndReleaseByStatusAndType(0, Integer.parseInt(num), 0, 0, new BeanPropertyRowMapper<WmsOccupiedAndRelease>(WmsOccupiedAndRelease.class));
            if (p != null && p.getTotalPages() > 0) {
                List<Long> list = new ArrayList<Long>();
                sendMQAndKeepIntoMongoDBOcciAndReal(strs, p, list, configsOccupied.get(0), null);
            } else {
                break;
            }
        }
        // 发送取消占用信息
        while (true) {
            Pagination<WmsOccupiedAndRelease> p2 = wmsIMOccupiedAndReleaseDao.getOccupiedAndReleaseByStatusAndType(0, Integer.parseInt(num), 0, 1, new BeanPropertyRowMapper<WmsOccupiedAndRelease>(WmsOccupiedAndRelease.class));
            if (p2 != null && p2.getTotalPages() > 0) {
                List<Long> list = new ArrayList<Long>();
                sendMQAndKeepIntoMongoDBOcciAndReal(strs, p2, list, null, configsRelease.get(0));
            } else {
                break;
            }
        }
    }

    /**
     * 库存占用和释放：发送MQ消息+保存进mongoDB+更新数据状态
     * 
     * @param topic
     * @param p
     * @param list
     */
    public void sendMQAndKeepIntoMongoDBOcciAndReal(String[] topics, Pagination<WmsOccupiedAndRelease> p, List<Long> list, MessageConfig configsOccupied, MessageConfig configsRelease) {
        List<Long> sendErrorIds = new ArrayList<Long>();
        List<Long> sendErrorRealeaseIds = new ArrayList<Long>();
        MessageCommond mc = new MessageCommond();
        mc.setMsgId(System.currentTimeMillis() + UUIDUtil.getUUID());
        mc.setSendTime(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        String msgBody = JsonUtil.writeValue(p.getItems());
        mc.setMsgBody(msgBody);
        mc.setIsMsgBodySend(false);
        log.debug("wms3_im_occupied_release send message begin:" + p.getCount());
        if (configsOccupied != null && configsOccupied.getIsOpen() == 1) {
            // 占用发送
            mc.setMsgType(configsOccupied.getMsgType());
            try {
                producerServer.sendDataMsgConcurrently(MQ_WMS3_IM_OCCUPIED, p.getItems().get(0).getOwnerCode(), mc);
                for (WmsOccupiedAndRelease occr : p.getItems()) {
                    list.add(occr.getId());
                }
                log.debug("wms3_im_occupied send message end:" + +p.getCount());
            } catch (Exception e) {
                log.error("wms3_im_occupied send message error:" + +p.getCount(), e);
                for (WmsOccupiedAndRelease occr : p.getItems()) {
                    sendErrorIds.add(occr.getId());
                }
            }
        } else if (configsRelease != null && configsRelease.getIsOpen() == 1) {
            // 占用的取消数据发送
            mc.setMsgType(configsRelease.getMsgType());
            try {
                producerServer.sendDataMsgConcurrently(MQ_WMS3_IM_RELEASE, p.getItems().get(0).getOwnerCode(), mc);
                log.debug("wms3_im_release send message end:" + p.getCount());
                for (WmsOccupiedAndRelease release : p.getItems()) {
                    list.add(release.getId());
                }
            } catch (Exception e) {
                log.error("wms3_im_release send message error:" + p.getCount(), e);
                for (WmsOccupiedAndRelease release : p.getItems()) {
                    sendErrorRealeaseIds.add(release.getId());
                }
            }
        }
        // 保存进mongodb
        MongoDBMessage mdbm = new MongoDBMessage();
        BeanUtils.copyProperties(mc, mdbm);
        mdbm.setStaCode(mc.getMsgId());
        mdbm.setMsgBody(msgBody);
        mongoOperation.save(mdbm);
        log.debug("wms3_im_occupied_release keep message into mongodb end:" + +p.getCount());
        // 更新状态
        wmsIMOccupiedAndReleaseDao.updateOccupiedAndReleaseStatus(list);
        // 更新错误次数
        if (sendErrorIds != null && !sendErrorIds.isEmpty()) {
            wmsIMOccupiedAndReleaseDao.updateIMOccupiedAndReleaseErrorCount(sendErrorIds);
        }
        if (sendErrorRealeaseIds != null && !sendErrorRealeaseIds.isEmpty()) {
            wmsIMOccupiedAndReleaseDao.updateIMOccupiedAndReleaseErrorCount(sendErrorRealeaseIds);
        }
    }

    @Override
    @SingleTaskLock(timeout = Constants.TASK_LOCK_TIMEOUT, value = Constants.TASK_LOCK_VALUE)
    public void sendEmailWhenSendToIMError() {
        Long countFlow = wmsSkuInventoryFlowDao.getUnpushedSkuInvFlowCountError(new SingleColumnRowMapper<Long>(Long.class));
        Long countRelease = wmsIMOccupiedAndReleaseDao.getUnpushedOccupiedAndReleaseError(new SingleColumnRowMapper<Long>(Long.class));
        if ((countFlow != null && countFlow.intValue() == 0) && (countRelease != null && countRelease.intValue() == 0)) {
            return;
        } else {
            ChooseOption c = chooseOptionDao.findByCategoryCodeAndKey(categoryCode, Constants.PUSH_IM_FAIID_COUNT);
            if (!StringUtil.isEmpty(c.getOptionValue())) {
                // 传人邮件模板的CODE -- 查询String类型可用的模板
                MailTemplate template = mailTemplateDao.findTemplateByCode("PUSH_TO_IM_ERRORCOUNT");
                if (template != null) {
                    StringBuffer sb = new StringBuffer();
                    sb.append(template.getMailBody() + " \n");
                    sb.append("流水:" + countFlow + " \n");
                    sb.append("占用和取消：" + countRelease + " \n");
                    boolean bool = false;
                    bool = MailUtil.sendMail(template.getSubject(), c.getOptionValue(), "", sb.toString(), false, null);
                    if (bool) {
                        log.debug("邮件通知成功！");
                    } else {
                        log.debug("邮件通知失败,请联系系统管理员！");
                    }
                } else {
                    log.debug("邮件模板不存在或被禁用！");
                }
            } else {
                log.debug("邮件通知失败,收件人为空！");
            }
        }
    }
}
