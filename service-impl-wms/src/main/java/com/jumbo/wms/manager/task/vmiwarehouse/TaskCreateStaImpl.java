package com.jumbo.wms.manager.task.vmiwarehouse;


import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.util.StringUtils;

import com.baozun.task.annotation.SingleTaskLock;
import com.jumbo.dao.baseinfo.WarehouseDao;
import com.jumbo.dao.hub2wms.WmsConfirmOrderQueueDao;
import com.jumbo.dao.mail.MailTemplateDao;
import com.jumbo.dao.warehouse.QueueStaDao;
import com.jumbo.pac.manager.extsys.wms.rmi.Rmi4Wms;
import com.jumbo.pac.manager.extsys.wms.rmi.model.SoLineResponse;
import com.jumbo.pac.manager.extsys.wms.rmi.model.StaCreatedResponse;
import com.jumbo.util.MailUtil;
import com.jumbo.wms.Constants;
import com.jumbo.wms.daemon.TaskCreateSta;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.system.ChooseOptionManager;
import com.jumbo.wms.manager.task.TaskOmsOutManager;
import com.jumbo.wms.manager.warehouse.QstaManager;
import com.jumbo.wms.manager.warehouse.QueueStaManagerProxy;
import com.jumbo.wms.model.baseinfo.Warehouse;
import com.jumbo.wms.model.hub2wms.WmsConfirmOrderQueue;
import com.jumbo.wms.model.mail.MailTemplate;
import com.jumbo.wms.model.warehouse.StockTransApplicationType;

/**
 * 过仓定时器
 * 
 * @author cheng.su
 * 
 */
public class TaskCreateStaImpl extends BaseManagerImpl implements TaskCreateSta {

    /**
	 * 
	 */
    private static final long serialVersionUID = -3831049155654143947L;
    protected static final Logger log = LoggerFactory.getLogger(TaskCreateStaImpl.class);
    private final String TOWAREHOUSE_EMAIL = "TOWAREHOUSE_EMAIL";
    private final String CREATE_STA_TIMEOUT_EMAIL = "CREATE_STA_TIMEOUT_EMAIL";
    private final String TIME_OUT = "TIME_OUT";
    @Autowired
    private QueueStaManagerProxy queueStaManager;
    @Autowired
    private QstaManager qstaManager;
    @Autowired
    private QueueStaDao queueStaDao;
    @Autowired
    private WarehouseDao warehouseDao;
    @Autowired
    private ChooseOptionManager chooseOptionManager;
    @Autowired
    private MailTemplateDao mailTemplateDao;
    @Autowired
    private WmsConfirmOrderQueueDao wmsConfirmOrderQueueDao;
    @Autowired
    TaskOmsOutManager omsOutManager;
    @Autowired
    private Rmi4Wms rmi4Wms;

    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void createSta() {
        if (log.isInfoEnabled()) {
            log.info("screeningQsta start-------------------------------------------,TaskCreateSta Time:" + System.currentTimeMillis());
        }
        queueStaManager.screeningQsta();
        if (log.isInfoEnabled()) {
            log.info("screeningQsta end-------------------------------------------,TaskCreateSta Time:" + System.currentTimeMillis());
        }
    }

    public void createSta1() {
        qstaManager.screeningQsta();
    }

    /**
     * 根据仓库创建作业单
     * 
     * @param strWhId
     */
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void createStaByWh(String strWhId) {
        if (log.isInfoEnabled()) {
            log.info("createStaByWh start------------------------------,ouId:" + strWhId);
        }
        Long whid = Long.valueOf(strWhId);
        Warehouse wh = warehouseDao.getByOuId(whid);
        if (wh != null && wh.getIsSingelToWhTask() != null && wh.getIsSingelToWhTask()) {
            queueStaManager.createStaByQueue(whid);
        }
        if (log.isInfoEnabled()) {
            log.info("createStaByWh end------------------------------,ouId:" + strWhId);
        }
    }

    /**
     * 每30分钟汇总一次过仓失败的单数，发送邮件通知OMS
     */
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void emailToOmsForFailure() {
        Integer count = queueStaDao.findErrorCount(new SingleColumnRowMapper<Integer>(Integer.class));
        if (count != null && count > 0) {
            Map<String, String> option = chooseOptionManager.getOptionByCategoryCode(TOWAREHOUSE_EMAIL);
            MailTemplate template = mailTemplateDao.findTemplateByCode(TOWAREHOUSE_EMAIL);
            String mailBody = MessageFormat.format(template.getMailBody(), count);
            MailUtil.sendMail(template.getSubject(), option.get(Constants.RECEIVER_ADDRESS), option.get(Constants.CARBON_COPY), mailBody, false, null);
        }
        // 超时没有生成作业单邮件通知
        // sendCreateStaTimeOutEmail();
        newSendCreateStaTimeOutEmail();
    }


    // /**
    // * 超时没有生成作业单邮件通知
    // */
    // private void sendCreateStaTimeOutEmail() {
    // try {
    // Map<String, String> option =
    // chooseOptionManager.getOptionByCategoryCode(CREATE_STA_TIMEOUT_EMAIL);
    // String timeOut = option.get(TIME_OUT);
    // if (timeOut != null && !"".equals(timeOut)) {
    // Integer time = Integer.valueOf(timeOut);
    //
    // // 超时时间定义成 分钟，存储于数据库常量表
    // Integer count = queueStaDao.findCreateStaTimeOutCount(time, new
    // SingleColumnRowMapper<Integer>(Integer.class));
    // if (count != null && count > 0) {
    // MailTemplate template = mailTemplateDao.findTemplateByCode(CREATE_STA_TIMEOUT_EMAIL);
    // String mailBody = MessageFormat.format(template.getMailBody(), count);
    // MailUtil.sendMail(template.getSubject(), option.get(Constants.RECEIVER_ADDRESS),
    // option.get(Constants.CARBON_COPY), mailBody, false, null);
    // }
    // }
    //
    // } catch (Exception e) {
    // log.error(e.getMessage());
    // }
    // }

    /**
     * 超时没有生成作业单邮件通知优化
     */
    public void newSendCreateStaTimeOutEmail() {
        try {
            Map<String, String> option = chooseOptionManager.getOptionByCategoryCode(CREATE_STA_TIMEOUT_EMAIL);
            String timeOut = option.get(TIME_OUT);
            List<String> ordercode = new ArrayList<String>();
            if (timeOut != null && !"".equals(timeOut)) {
                Integer time = Integer.valueOf(timeOut);
                // 超时时间定义成 分钟，存储于数据库常量表
                ordercode = queueStaDao.findOrdercodeList(time, new SingleColumnRowMapper<String>(String.class));
                if (ordercode != null && ordercode.size() > 0) {
                    MailTemplate template = mailTemplateDao.findByCode(CREATE_STA_TIMEOUT_EMAIL);
                    String mailBody = MessageFormat.format(template.getMailBody(), ordercode.size(), ordercode.toString());
                    MailUtil.sendMail(template.getSubject(), option.get(Constants.RECEIVER_ADDRESS), option.get(Constants.CARBON_COPY), mailBody, true, null);
                }
            }

        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("newSendCreateStaTimeOutEmail error...", e);
            }
            log.error(e.getMessage());
        }
    }

    @Override
    public void wmsConfirmOrderService() {
        List<WmsConfirmOrderQueue> confirmOrders = wmsConfirmOrderQueueDao.getListByQueryPac("pacs", new BeanPropertyRowMapper<WmsConfirmOrderQueue>(WmsConfirmOrderQueue.class));

        for (WmsConfirmOrderQueue wmsConfirmOrder : confirmOrders) {
            StaCreatedResponse createdResponse = new StaCreatedResponse();
            List<SoLineResponse> lineResponses = new ArrayList<SoLineResponse>();
            try {
                if (wmsConfirmOrder.getOrderType() == StockTransApplicationType.OUTBOUND_SALES.getValue()) {
                    createdResponse.setType(StaCreatedResponse.BASE_RESULT_TYPE_SO);
                } else {
                    createdResponse.setType(StaCreatedResponse.BASE_RESULT_TYPE_RA);
                }
                if (wmsConfirmOrder.isStatus()) {
                    createdResponse.setStatus(StaCreatedResponse.BASE_RESULT_STATUS_SUCCESS);
                } else {
                    if (wmsConfirmOrder.getIsmeet() != null && wmsConfirmOrder.getIsmeet()) {
                        createdResponse.setStatus(5);
                    } else {
                        createdResponse.setStatus(StaCreatedResponse.BASE_RESULT_STATUS_INV_SHORTAGE);
                        if (StringUtils.hasText(wmsConfirmOrder.getMemo())) {

                            String check = "";
                            try {
                                check = wmsConfirmOrder.getMemo().substring(0, wmsConfirmOrder.getMemo().indexOf("："));
                            } catch (Exception e) {}
                            if (check.equals("库存不足")) {
                                String skuQty = wmsConfirmOrder.getMemo().substring(wmsConfirmOrder.getMemo().indexOf("[") + 1, wmsConfirmOrder.getMemo().indexOf("]"));
                                String memos[] = skuQty.split(",");
                                for (String memo : memos) {
                                    String memo1[] = memo.split("-缺");
                                    SoLineResponse lineResponse = new SoLineResponse();
                                    lineResponse.setJmSkuCode(memo1[0]);
                                    lineResponse.setQty(Long.parseLong(memo1[1].substring(memo1[1].indexOf("(") + 1, memo1[1].indexOf(")"))));
                                    lineResponses.add(lineResponse);
                                }
                            } else {
                                String memo[] = wmsConfirmOrder.getMemo().split(",");
                                for (int i = 0; i < memo.length; i++) {
                                    try {
                                        String memo1[] = memo[i].split(" ");
                                        SoLineResponse lineResponse = new SoLineResponse();
                                        lineResponse.setJmSkuCode(memo1[0]);
                                        if (!memo1[0].equals("仓库优先级发货规则未找到合适发货仓") && !memo1[0].equals("订单无法匹配合适物流商!")) {
                                            lineResponse.setQty(Long.parseLong(memo1[1].substring(3, memo1[1].length() - 1)));
                                        }
                                        lineResponses.add(lineResponse);
                                    } catch (Exception e) {
                                        // 报错不管 继续反馈
                                    }
                                }
                            }
                        }
                    }
                }
                createdResponse.setMsg(wmsConfirmOrder.getMemo());
                createdResponse.setSlipCode(wmsConfirmOrder.getOrderCode());
                createdResponse.setSoLineResponses(lineResponses);
            } catch (Exception e) {
                if (log.isErrorEnabled()) {
                    log.error("Assignment StaCreatedResponse errors:" + wmsConfirmOrder.getOrderCode(), e);
                }
                continue;
            }
            // 反馈OMS成功
            try {
                rmi4Wms.wmsCreateStaFeedback(createdResponse);
            } catch (Exception e) {
                log.error("error when connect oms to wmsCreateStaFeedback");
                log.error("orderCode:" + wmsConfirmOrder.getOrderCode(), e);
                // 接口异常
                throw new BusinessException(ErrorCode.SYSTEM_ERROR);
            }
            wmsConfirmOrder.setIspush(true);
            wmsConfirmOrderQueueDao.save(wmsConfirmOrder);
        }
    }

    @Override
    public void outboudNoticePacSystenKey() {
        omsOutManager.outboudNoticePacSystenKey("pacs");

    }
}
