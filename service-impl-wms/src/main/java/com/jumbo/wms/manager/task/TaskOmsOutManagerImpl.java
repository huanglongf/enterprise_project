package com.jumbo.wms.manager.task;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.jfree.util.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.baozun.bizhub.manager.brand.ad.AdReturnOrderManager;
import com.baozun.bizhub.model.ad.AdReturnOrderResult;
import com.baozun.ecp.ip.command.agv.inboundOrder.AgvInBoundOrder;
import com.baozun.ecp.ip.command.agv.inboundOrder.AgvInBoundOrderLine;
import com.baozun.ecp.ip.manager.wms3.Wms3AdapterInteractManager;
import com.baozun.utilities.json.JsonUtil;
import com.jumbo.dao.authorization.OperationUnitDao;
import com.jumbo.dao.baseinfo.BrandDao;
import com.jumbo.dao.baseinfo.MsgOmsSkuLogDao;
import com.jumbo.dao.baseinfo.SkuBarcodeDao;
import com.jumbo.dao.baseinfo.SkuDao;
import com.jumbo.dao.hub2wms.WmsConfirmOrderQueueDao;
import com.jumbo.dao.hub2wms.WmsOrderStatusOmsDao;
import com.jumbo.dao.mail.MailTemplateDao;
import com.jumbo.dao.msg.MessageProducerErrorDao;
import com.jumbo.dao.odo.OdoDao;
import com.jumbo.dao.odo.OdoLineDao;
import com.jumbo.dao.system.ChooseOptionDao;
import com.jumbo.dao.vmi.ids.IdsInventorySynchronousDao;
import com.jumbo.dao.vmi.warehouse.MsgRtnOutboundDao;
import com.jumbo.dao.warehouse.AdPackageLineDealDao;
import com.jumbo.dao.warehouse.AgvOutBoundDao;
import com.jumbo.dao.warehouse.AgvOutBoundLineDao;
import com.jumbo.dao.warehouse.AgvSkuDao;
import com.jumbo.dao.warehouse.BiChannelDao;
import com.jumbo.dao.warehouse.InboundAgvToHubDao;
import com.jumbo.dao.warehouse.InboundAgvToHubLineDao;
import com.jumbo.dao.warehouse.InventoryStatusDao;
import com.jumbo.dao.warehouse.InvetoryChangeDao;
import com.jumbo.dao.warehouse.PickingListDao;
import com.jumbo.dao.warehouse.StaDeliveryInfoDao;
import com.jumbo.dao.warehouse.StaInvoiceDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.dao.warehouse.WmsIntransitNoticeOmsDao;
import com.jumbo.pac.manager.extsys.wms.rmi.Rmi4Wms;
import com.jumbo.pac.manager.extsys.wms.rmi.model.BaseResult;
import com.jumbo.pac.manager.extsys.wms.rmi.model.SoLineResponse;
import com.jumbo.pac.manager.extsys.wms.rmi.model.StaCreatedResponse;
import com.jumbo.util.MailUtil;
import com.jumbo.util.StringUtil;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.hub2wms.CreateStaTaskManager;
import com.jumbo.wms.manager.listener.StaListenerManager;
import com.jumbo.wms.manager.pickZone.NewInventoryOccupyManager;
import com.jumbo.wms.manager.warehouse.QueueStaManagerExecute;
import com.jumbo.wms.manager.warehouse.WareHouseManagerExe;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.baseinfo.Brand;
import com.jumbo.wms.model.baseinfo.MsgOmsSkuLog;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.baseinfo.SkuBarcode;
import com.jumbo.wms.model.hub2wms.WmsConfirmOrderQueue;
import com.jumbo.wms.model.hub2wms.WmsOrderStatusOms;
import com.jumbo.wms.model.mail.MailTemplate;
import com.jumbo.wms.model.msg.MessageProducerError;
import com.jumbo.wms.model.msg.MongoAGVMessage;
import com.jumbo.wms.model.odo.Odo;
import com.jumbo.wms.model.odo.OdoLine;
import com.jumbo.wms.model.system.ChooseOption;
import com.jumbo.wms.model.vmi.ids.IdsInventorySynchronous;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnOutbound;
import com.jumbo.wms.model.warehouse.AdPackageLineDeal;
import com.jumbo.wms.model.warehouse.AdPackageLineDealDto;
import com.jumbo.wms.model.warehouse.AdTsReturnOrderConfirmationWms;
import com.jumbo.wms.model.warehouse.AgvSku;
import com.jumbo.wms.model.warehouse.InboundAgvToHub;
import com.jumbo.wms.model.warehouse.InboundAgvToHubLine;
import com.jumbo.wms.model.warehouse.InboundStoreMode;
import com.jumbo.wms.model.warehouse.InventoryStatus;
import com.jumbo.wms.model.warehouse.InvetoryChange;
import com.jumbo.wms.model.warehouse.InvetoryChangeCommand;
import com.jumbo.wms.model.warehouse.PickingList;
import com.jumbo.wms.model.warehouse.StaDeliveryInfo;
import com.jumbo.wms.model.warehouse.StaInvoice;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationType;
import com.jumbo.wms.model.warehouse.WmsIntransitNoticeOms;
import com.jumbo.wms.model.warehouse.WmsIntransitNoticeOmsCommand;
import com.jumbo.wms.model.warehouse.agv.AgvOutBound;
import com.jumbo.wms.model.warehouse.agv.AgvOutBoundDto;
import com.jumbo.wms.model.warehouse.agv.AgvOutBoundLineDto;

import loxia.dao.Pagination;
import loxia.dao.support.BeanPropertyRowMapperExt;

/**
 * 
 * @author xiaolong.fei WMS出库双十一优化
 */
@Service("taskOmsOutManager")
public class TaskOmsOutManagerImpl extends BaseManagerImpl implements TaskOmsOutManager {
    /**
     * 
     */
    private static final long serialVersionUID = 6657073643890859462L;

    private static final Logger log = LoggerFactory.getLogger(TaskOmsOutManagerImpl.class);

    @Autowired
    private MailTemplateDao mailTemplateDao;
    @Autowired
    private ChooseOptionDao chooseOptionDao;
    @Autowired
    private WmsIntransitNoticeOmsDao wmsIntransitNoticeOmsDao;
    @Autowired
    private TaskOmsOutThread taskOmsOutThread;
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private WareHouseManagerExe wareHouseManagerExe;
    @Autowired
    private MsgOmsSkuLogDao msgOmsSkuLogDao;
    @Autowired
    private BiChannelDao biChannelDao;
    @Autowired
    private SkuBarcodeDao skuBarcodeDao;
    @Autowired
    private StaListenerManager staListenerManager;
    @Autowired
    private WmsOrderStatusOmsDao wmsOrderStatusOmsDao;
    @Autowired
    private IdsInventorySynchronousDao idsInventorySynchronousDao;
    @Autowired
    private WmsConfirmOrderQueueDao wmsConfirmOrderQueueDao;
    @Autowired
    private MsgRtnOutboundDao msgRtnOutbounddao;
    @Autowired
    private MessageProducerErrorDao messageProducerErrorDao;
    @Autowired
    private Rmi4Wms rmi4Wms;
    @Autowired
    private OdoDao odoDao;
    @Autowired
    private StockTransApplicationDao stockTransApplicationDao;

    @Autowired
    private AgvSkuDao agvSkuDao;

    @Autowired
    private StaInvoiceDao staInvoiceDao;

    @Autowired
    private InboundAgvToHubDao inboundAgvToHubDao;

    @Autowired
    private NewInventoryOccupyManager newInventoryOccupyManager;

    @Autowired
    private CreateStaTaskManager createStaTaskManager;

    @Autowired
    private QueueStaManagerExecute queueStaManagerExecute;
    @Autowired
    private AgvOutBoundDao agvOutBoundDao;
    @Autowired
    private AdReturnOrderManager adReturnOrderManager;
    
    @Autowired
    private AdPackageLineDealDao adPackageLineDealDao;

    @Autowired
    private SkuDao skuDao;

    @Autowired
    private AgvOutBoundLineDao agvOutBoundLineDao;

    @Autowired
    private Wms3AdapterInteractManager wms3AdapterInteractManager;

    @Autowired
    private InboundAgvToHubLineDao inboundAgvToHubLineDao;

    @Autowired
    private BrandDao brandDao;

    @Autowired
    private InvetoryChangeDao invetoryChangeDao;

    @Resource(name = "mongoTemplate")
    private MongoOperations mongoOperation;
    @Autowired
    private StaDeliveryInfoDao staDeliveryInfoDao;
    @Autowired
    private OdoLineDao odoLineDao;
    @Autowired
    private OperationUnitDao operationUnitDao;
    @Autowired
    private InventoryStatusDao inventoryStatusDao;
    @Autowired
    private PickingListDao pickingListDao;


    /**
     * 全局变量 用于传 批次号
     */
    private String batchCode;


    public void createStaByOdOId(Long id) {
        Odo odo = odoDao.getByPrimaryKey(id);
        Integer status = null;
        if (odo != null) {
            status = odo.getStatus();
            if (status == 3) {
                status = 2;
            } else if (status == 6) {
                status = 3;
            }
        }
        List<OdoLine> odoLineList = odoLineDao.findOdOLineByOdOId(id, status, new BeanPropertyRowMapper<OdoLine>(OdoLine.class));
        if (odoLineList != null && odoLineList.size() > 0) {
            try {
                OperationUnit operationUnit = null;
                com.baozun.ecp.ip.command.inboundOrders.InboundOrders inboundOrders = new com.baozun.ecp.ip.command.inboundOrders.InboundOrders();
                inboundOrders.setSourceMarkCode("WMS3");
                com.baozun.ecp.ip.command.inboundOrders.InboundOrders.InboundOrder inboundOrder = new com.baozun.ecp.ip.command.inboundOrders.InboundOrders.InboundOrder();
                inboundOrder.setSourceOutCode(odo.getCode());
                inboundOrder.setSourcePlatformNo(odo.getCode());
                inboundOrder.setStoreCode(odo.getOwner());
                if (status == 2) {
                    operationUnit = operationUnitDao.getByPrimaryKey(odo.getTargetOuId());
                } else if (status == 3) {
                    operationUnit = operationUnitDao.getByPrimaryKey(odo.getDiffOuid());
                }
                inboundOrder.setWhCode(operationUnit.getCode());
                // inboundOrder.setPlanQuantity(odoLine.getQty().intValue());
                inboundOrder.setDataSource("WMS3");
                inboundOrder.setInboundNo(odo.getCode());
                InventoryStatus inventoryStatus = inventoryStatusDao.getByPrimaryKey(odo.getInvStatusId());
                com.baozun.ecp.ip.command.inboundOrders.InboundOrders.InboundOrder.InboundOrderLines inboundOrderLines = new com.baozun.ecp.ip.command.inboundOrders.InboundOrders.InboundOrder.InboundOrderLines();
                for (OdoLine odoLine : odoLineList) {
                    com.baozun.ecp.ip.command.inboundOrders.InboundOrders.InboundOrder.InboundOrderLines.InboundOrderLine line = new com.baozun.ecp.ip.command.inboundOrders.InboundOrders.InboundOrder.InboundOrderLines.InboundOrderLine();
                    Sku sku = skuDao.getByPrimaryKey(odoLine.getSkuId());
                    line.setBarcode(sku.getBarCode());
                    line.setUpc(sku.getCode());
                    line.setCartonNo(odoLine.getCode());
                    line.setStoreCode(odo.getOwner());
                    line.setQty(Integer.parseInt(odoLine.getQty().toString()));
                    if (null != inventoryStatus && null != inventoryStatus.getName() && !"".equals(inventoryStatus.getName())) {
                        if (inventoryStatus.getName().indexOf("良品不可售") >= 0)
                            line.setStatus("6");
                        else if (inventoryStatus.getName().indexOf("良品") >= 0)
                            line.setStatus("1");
                        else if (inventoryStatus.getName().indexOf("残次品") >= 0)
                            line.setStatus("2");
                        else if (inventoryStatus.getName().indexOf("待处理") >= 0)
                            line.setStatus("4");
                        else if (inventoryStatus.getName().indexOf("待报废") >= 0)
                            line.setStatus("3");
                        else if (inventoryStatus.getName().indexOf("临近保质期") >= 0)
                            line.setStatus("5");
                        else
                            line.setStatus("2");
                    }
                    inboundOrderLines.getInboundOrderLine().add(line);

                }
                inboundOrder.setInboundOrderLines(inboundOrderLines);
                inboundOrders.getInboundOrder().add(inboundOrder);
                com.baozun.ecp.ip.command.Response response = wms3AdapterInteractManager.inboundOdOOrderNotify(inboundOrders);
                if (response != null) {
                    if (response.getResult() == 1) {
                        if (status == 2) {
                            odo.setStatus(4);
                        } else {
                            odo.setStatus(7);
                        }
                        odoDao.save(odo);
                    } else {
                        odo.setErrorCount((odo.getErrorCount() == null ? 0 : odo.getErrorCount()) + 1);
                        log.error("createStaByOdOId" + id + " result" + response.getResult() + "errorMsg " + response.getErrorMsg());
                        odoDao.save(odo);
                    }
                } else {
                    odo.setErrorCount((odo.getErrorCount() == null ? 0 : odo.getErrorCount()) + 1);
                    log.error("createStaByOdOId1" + id);
                    odoDao.save(odo);
                }
            } catch (Exception e) {
                log.error("createStaByOdOId2" + id + " e" + e);
                odo.setErrorCount((odo.getErrorCount() == null ? 0 : odo.getErrorCount()) + 1);
                odoDao.save(odo);
            }
        }
    }

    /**
     * 查询1W单出库通知订单 pac
     * 
     * @return
     */
    public List<WmsIntransitNoticeOms> findToNoticeOrder() {
        return wmsIntransitNoticeOmsDao.findNoticeOrderList(new BeanPropertyRowMapperExt<WmsIntransitNoticeOms>(WmsIntransitNoticeOms.class));
    }

    /**
     * 查询1W单出库通知订单 oms
     * 
     * @return
     */
    public List<WmsOrderStatusOms> findToNoticeOrderOms() {
        return wmsOrderStatusOmsDao.findNoticeOrderList(new BeanPropertyRowMapperExt<WmsOrderStatusOms>(WmsOrderStatusOms.class));
    }

    /**
     * 查询1W单 直连 订单创反馈 oms
     * 
     * @return
     */
    public List<WmsConfirmOrderQueue> findWmsOrderFinishList() {
        return wmsConfirmOrderQueueDao.findWmsOrderFinishList(new BeanPropertyRowMapperExt<WmsConfirmOrderQueue>(WmsConfirmOrderQueue.class));
    }

    /**
     * 查询1W单 外包仓出库反馈 反馈执行失败或丢mq 通知
     * 
     * @return
     */
    public List<MsgRtnOutbound> wmsRtnOutBountMq() {
        return msgRtnOutbounddao.wmsRtnOutBountMq(new BeanPropertyRowMapper<MsgRtnOutbound>(MsgRtnOutbound.class));
    }

    public List<MessageProducerError> wmsCommonMessageProducerErrorMq() {
        return messageProducerErrorDao.wmsCommonMessageProducerErrorMqList(new BeanPropertyRowMapper<MessageProducerError>(MessageProducerError.class));
    }

    /**
     * 通知PAC订单出库
     * 
     * @param staid
     * @param orderid
     */
    public void outboudNoticePacById(Long staid, Long orderid) {
        staListenerManager.transferOmsOutBound(staid, orderid);
    }

    /**
     * 通知OMS订单出库
     */
    public void outboudNoticeOmsById(Long orderid) {
        staListenerManager.statusOmsOutBound(orderid);
    }

    /**
     * 通知 直连 创单 反馈
     */
    @Override
    public void wmsOrderFinishOms(Long orderid) {
        staListenerManager.wmsOrderFinishOms(orderid);
    }

    @Override
    public void wmsRtnOutBountMq(Long orderid) {
        staListenerManager.wmsRtnOutBountMq(orderid);
    }

    @Override
    public void wmsCommonMessageProducerErrorMq(Long orderid) {
        staListenerManager.wmsCommonMessageProducerErrorMq(orderid);
    }

    /**
     * 线程调用接口
     */
    public void threadOutBound() {
        // 查询上次失败的数据。 批次号不为空，且错误次数小于10的去重复的批次号
        List<String> codeList = wmsIntransitNoticeOmsDao.findIntransit(new SingleColumnRowMapper<String>(String.class));
        for (String code : codeList) {// 循环
            try {
                taskOmsOutThread.setBatchCode(code);
                Thread thread = new Thread(taskOmsOutThread);
                thread.run();
            } catch (Exception e) {
                if (log.isErrorEnabled()) {
                    log.error("taskOmsOutThread Exception:", e);
                }
            }
        }
        // 最大查询1000条数据
        List<WmsIntransitNoticeOms> partList = wmsIntransitNoticeOmsDao.findPartIntransit(new BeanPropertyRowMapperExt<WmsIntransitNoticeOms>(WmsIntransitNoticeOms.class));
        // 记录当前时间为批次号
        Date date = new Date();
        int count = 0; // 用于计数。每100条记录一个批次号
        batchCode = "";
        String StringDate = Long.toString(date.getTime());
        for (WmsIntransitNoticeOms each : partList) {
            count++;
            if (count <= 100) {
                batchCode = StringDate + "_P0";
            } else if (count > 100 && count <= 200) {
                batchCode = StringDate + "_P1";
            } else if (count > 200 && count <= 300) {
                batchCode = StringDate + "_P2";
            } else if (count > 300 && count <= 400) {
                batchCode = StringDate + "_P3";
            } else if (count > 400 && count <= 500) {
                batchCode = StringDate + "_P4";
            } else if (count > 500 && count <= 600) {
                batchCode = StringDate + "_P5";
            } else if (count > 600 && count <= 700) {
                batchCode = StringDate + "_P6";
            } else if (count > 700 && count <= 800) {
                batchCode = StringDate + "_P7";
            } else if (count > 800 && count <= 900) {
                batchCode = StringDate + "_P8";
            } else if (count > 900 && count <= 1000) {
                batchCode = StringDate + "_P9";
            }
            each.setBatchCode(batchCode);
            each.setLastModifyTime(new Date());
            wmsIntransitNoticeOmsDao.save(each);
        }
        wmsIntransitNoticeOmsDao.flush();
        if (!StringUtil.isEmpty(batchCode)) {
            // 获取batchCode最后一个字符，判断有多少批次，跑几条线程
            int size = Integer.parseInt(batchCode.substring(batchCode.length() - 1));
            for (int i = 0; i <= size; i++) {
                try {
                    batchCode = batchCode.substring(0, batchCode.length() - 1) + i;
                    taskOmsOutThread.setBatchCode(batchCode);
                    Thread thread = new Thread(taskOmsOutThread);
                    thread.run();
                } catch (Exception e) {
                    if (log.isErrorEnabled()) {
                        log.error("taskOmsOutThread Exception:", e);
                    }
                }
            }
        }
    }

    /**
     * 发送邮件
     * 
     * @param list
     */
    public void sendEmail(List<WmsIntransitNoticeOms> list) {
        // 查询系统常量表 收件人
        ChooseOption option = chooseOptionDao.findByCategoryCode("ERROR_NOTICE");
        if (!StringUtil.isEmpty(option.getOptionValue())) {
            // 传人邮件模板的CODE -- 查询String类型可用的模板
            MailTemplate template = mailTemplateDao.findTemplateByCode("ERROR_NOTICE");
            if (template != null) {
                String staCode = ""; // 用于存储失败的作业单号，以英文逗号隔开
                String slipCode = "";// 用于存储失败的作业单相关单据号，以英文逗号隔开
                String errorMsg = "";// 用于存储失败的作业单异常信息，以英文逗号隔开
                for (WmsIntransitNoticeOms item : list) {
                    staCode += "'" + item.getStaCode() + "', ";
                    StockTransApplication sta = staDao.getByCode(item.getStaCode());
                    if (sta != null) {
                        slipCode += "'" + sta.getRefSlipCode() + "', ";
                    }
                    errorMsg += sta.getRefSlipCode() + ":" + item.getReturnMsg() + "; ";
                }
                staCode = staCode.substring(0, staCode.length() - 2);
                slipCode = slipCode.substring(0, slipCode.length() - 2);
                errorMsg = errorMsg.substring(0, errorMsg.length() - 1);
                String mailBody = template.getMailBody().substring(0, template.getMailBody().indexOf("$"));// 邮件内容
                String mailHtml = template.getMailBody().substring(template.getMailBody().indexOf("$") + 1, template.getMailBody().length());// 邮件样式
                String subject = template.getSubject();// 标题
                String addressee = option.getOptionValue(); // 查询收件人
                // 替换样式内容
                String tcontent = mailHtml.replaceAll("mailBody", mailBody);
                tcontent = tcontent.replaceAll("staCode", staCode);
                tcontent = tcontent.replaceAll("slipCode", slipCode);
                tcontent = tcontent.replaceAll("errorMsg", errorMsg);
                boolean bool = false;
                bool = MailUtil.sendMail(subject, addressee, "", tcontent, true, null);
                if (bool) {
                    // 通知成功修改标识
                    for (WmsIntransitNoticeOms items : list) {
                        wmsIntransitNoticeOmsDao.updateSendById(items.getId());
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


    public List<Odo> findCreateInBoundStaList() {

        return odoDao.findCreateInBoundStaList();
    }

    /**
     * 发送邮件（LF库存调整执行失败）
     * 
     * @param list
     */
    public void sendEmailIdsInventory(IdsInventorySynchronous en) {
        // 查询系统常量表 收件人
        ChooseOption option = chooseOptionDao.findByCategoryCode("IDS_INVENTORY_EMAIL");
        if (!StringUtil.isEmpty(option.getOptionValue())) {
            // 传人邮件模板的CODE -- 查询String类型可用的模板
            MailTemplate template = mailTemplateDao.findTemplateByCode("IDS_INVENTORY_EMAIL");
            if (template != null) {
                String batchID = en.getBatchID(); // 批次号
                String source = en.getSource();// 来源
                String errorMsg = en.getReasonCode();// 原因编码
                String mailBody = template.getMailBody().substring(0, template.getMailBody().indexOf("$"));// 邮件内容
                String mailHtml = template.getMailBody().substring(template.getMailBody().indexOf("$") + 1, template.getMailBody().length());// 邮件样式
                String subject = template.getSubject();// 标题
                String addressee = option.getOptionValue(); // 查询收件人
                // 替换样式内容
                String tcontent = mailHtml.replaceAll("mailBody", mailBody);
                tcontent = tcontent.replaceAll("staCode", batchID);
                tcontent = tcontent.replaceAll("slipCode", source);
                tcontent = tcontent.replaceAll("errorMsg", errorMsg);
                boolean bool = false;
                bool = MailUtil.sendMail(subject, addressee, "", tcontent, true, null);
                if (bool) {
                    // 通知成功修改标识
                    idsInventorySynchronousDao.updateMsgStaById2(en.getId());
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


    /**
     * 所有超过6小时的通知OMS创建SKU而OMS仍未创建的邮件通知
     */
    public void emailNoticeOms(List<MsgOmsSkuLog> list) {
        // 查询系统常量表 收件人
        ChooseOption option = chooseOptionDao.findByCategoryCode("SKU_NOTICE");
        if (!StringUtil.isEmpty(option.getOptionValue())) {
            // 传人邮件模板的CODE -- 查询String类型可用的模板
            MailTemplate template = mailTemplateDao.findTemplateByCode("SKU_NOTICE");
            if (template != null) {

                String mailBody = template.getMailBody() + getMsgByList(list);// 邮件内容
                String subject = template.getSubject();// 标题
                String addressee = option.getOptionValue(); // 查询收件人

                boolean bool = false;
                bool = MailUtil.sendMail(subject, addressee, "", mailBody, false, null);
                if (bool) {
                    for (MsgOmsSkuLog each : list) {
                        msgOmsSkuLogDao.updateMsgOmsSkuLogById(each.getId());
                    }
                    log.debug("邮件通知成功！");
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

    /**
     * 将消息内容分组显示并返回字符串
     * 
     * @param list
     * @return
     */
    public String getMsgByList(List<MsgOmsSkuLog> list) {
        // 商品分组
        Map<String, StringBuffer> moslMap = new HashMap<String, StringBuffer>();
        for (MsgOmsSkuLog mosl : list) {
            if (mosl.getVmiCode() != null) {
                StringBuffer s = moslMap.get(mosl.getVmiCode());
                BiChannel bi = biChannelDao.getByVmiCode(mosl.getVmiCode());
                if (s != null) {
                    s.append(",'" + mosl.getExtCode2() + "'");
                } else {
                    StringBuffer sb = new StringBuffer();
                    sb.append("\r\n------------------------\r\n");
                    sb.append(mosl.getVmiCode());
                    if (bi != null) {
                        sb.append("(" + bi.getName() + ")");
                    }
                    sb.append("|'" + mosl.getExtCode2() + "'");
                    moslMap.put(mosl.getVmiCode(), sb);
                }
            } else {
                StringBuffer s = moslMap.get("无");
                if (s != null) {
                    s.append(",'" + mosl.getExtCode2() + "'");
                } else {
                    StringBuffer sb = new StringBuffer();
                    sb.append("\r\n------------------------\r\n");
                    sb.append("无 |");
                    sb.append("'" + mosl.getExtCode2() + "'");
                    moslMap.put("无", sb);
                }
            }
        }
        // 将消息合成一条字符串
        StringBuffer msgSb = new StringBuffer();
        Set<String> set = moslMap.keySet();
        for (String key : set) {
            msgSb.append(moslMap.get(key));
        }

        return msgSb.toString();
    }

    @Transactional
    public void outboudNoticePacSystenKey(String systemKey) {
        List<WmsOrderStatusOms> list = wmsOrderStatusOmsDao.wmsOrderConfirmPac(systemKey, new BeanPropertyRowMapperExt<WmsOrderStatusOms>(WmsOrderStatusOms.class));
        for (WmsOrderStatusOms wmsOrderStatusOms : list) {
            StockTransApplication sta = staDao.getByCode(wmsOrderStatusOms.getShippingCode());
            WmsIntransitNoticeOms notice = new WmsIntransitNoticeOms();
            notice.setStaCode(sta.getCode());
            notice.setStaId(sta.getId());
            notice.setCreateTime(new Date());
            notice.setLastModifyTime(new Date());
            notice.setWhOuId(sta.getMainWarehouse());
            notice.setOwner(sta.getOwner());
            notice.setErrorCount(0l);
            notice.setIsSend(0l);
            notice.setIsLock(wmsOrderStatusOms.getIsLock());// 是否是锁定 订单
            wmsIntransitNoticeOmsDao.save(notice);
            wmsIntransitNoticeOmsDao.flush();
            wmsOrderStatusOms.setStatus(10);
            wmsOrderStatusOmsDao.save(wmsOrderStatusOms);
            wmsOrderStatusOmsDao.flush();
        }

    }

    @Override
    public List<Long> getWmsOrderCreateTimeIsNullIds() {
        return wmsOrderStatusOmsDao.wmsOrderCreateTimeIsNull(new SingleColumnRowMapper<Long>(Long.class));
    }

    @Transactional
    public void updateWmsOrderStatusOmsCreateTime(List<Long> ids) {
        wmsOrderStatusOmsDao.updateWmsOrderCreateTime(ids);
    }

    /**
     * @author LuYingMing
     * @date 2016年8月15日 下午12:52:01
     * @see com.jumbo.wms.manager.task.TaskOmsOutManager#findOutPacsByParams(int, int,
     *      java.util.Map)
     */
    @Override
    public Pagination<WmsIntransitNoticeOmsCommand> findOutPacsByParams(int start, int pageSize, Map<String, Object> params) {
        return wmsIntransitNoticeOmsDao.findOutPacsByParams(start, pageSize, params, new BeanPropertyRowMapper<WmsIntransitNoticeOmsCommand>(WmsIntransitNoticeOmsCommand.class));
    }

    @Override
    public void resetZero(List<Long> idList) {
        wmsIntransitNoticeOmsDao.resetZero(idList);
    }

    @Override
    public void resetHundred(List<Long> idList) {
        wmsIntransitNoticeOmsDao.resetHundred(idList);

    }

    @Override
    public List<WmsOrderStatusOms> wmsOrderConfirmPac2() {
        List<WmsOrderStatusOms> list = wmsOrderStatusOmsDao.wmsOrderConfirmPac2("pacs", new BeanPropertyRowMapperExt<WmsOrderStatusOms>(WmsOrderStatusOms.class));
        return list;
    }

    @Override
    public void outboudNoticePacId(WmsOrderStatusOms wmsOrderStatusOms) {
        WmsOrderStatusOms wmsOrderStatusOms2 = wmsOrderStatusOmsDao.getByPrimaryKey(wmsOrderStatusOms.getId());
        StockTransApplication sta = staDao.getByCode(wmsOrderStatusOms2.getShippingCode());
        WmsIntransitNoticeOms notice = new WmsIntransitNoticeOms();
        notice.setStaCode(sta.getCode());
        notice.setStaId(sta.getId());
        notice.setCreateTime(new Date());
        notice.setLastModifyTime(new Date());
        notice.setWhOuId(sta.getMainWarehouse());
        notice.setOwner(sta.getOwner());
        notice.setErrorCount(0l);
        notice.setIsSend(0l);
        notice.setIsLock(wmsOrderStatusOms2.getIsLock());// 是否是锁定 订单
        wmsIntransitNoticeOmsDao.save(notice);
        wmsIntransitNoticeOmsDao.flush();
        wmsOrderStatusOms2.setStatus(10);
        wmsOrderStatusOmsDao.save(wmsOrderStatusOms2);
        wmsOrderStatusOmsDao.flush();

    }

    @Override
    public List<WmsConfirmOrderQueue> getListByQueryPac2() {
        List<WmsConfirmOrderQueue> confirmOrders = wmsConfirmOrderQueueDao.getListByQueryPac2("pacs", new BeanPropertyRowMapper<WmsConfirmOrderQueue>(WmsConfirmOrderQueue.class));
        return confirmOrders;
    }

    @Override
    public List<AgvOutBoundDto> agvOutBoundDaemon() {
        List<AgvOutBoundDto> list = agvOutBoundDao.agvOutBoundDaemon(new BeanPropertyRowMapper<AgvOutBoundDto>(AgvOutBoundDto.class));
        return list;
    }
    
    @Override
    public List<AdPackageLineDealDto> adPackageUpdate() {
        List<AdPackageLineDealDto> list = adPackageLineDealDao.adPackageUpdate(new BeanPropertyRowMapper<AdPackageLineDealDto>(AdPackageLineDealDto.class));
        return list;
    }

    @Override
    public void wmsConfirmOrderService3(WmsConfirmOrderQueue wmsConfirmOrderQueue) {
        WmsConfirmOrderQueue wmsConfirmOrder = wmsConfirmOrderQueueDao.getByPrimaryKey(wmsConfirmOrderQueue.getId());
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
        }
        BaseResult result = null;
        // 反馈OMS成功
        try {
            result = rmi4Wms.wmsCreateStaFeedback(createdResponse);
        } catch (Exception e) {
            log.error("error when connect oms to wmsCreateStaFeedback");
            log.error("orderCode:" + wmsConfirmOrder.getOrderCode(), e);
            // 接口异常
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        // 成功更新状态
        if (result != null && result.getStatus() == 1) {
            wmsConfirmOrder.setIspush(true);
        } else {
            // 失败增加错误次数
            if (wmsConfirmOrder.getErrorCount() == null) {
                wmsConfirmOrder.setErrorCount(0);
            }
            wmsConfirmOrder.setErrorCount(wmsConfirmOrder.getErrorCount() + 1);
        }
        wmsConfirmOrderQueueDao.save(wmsConfirmOrder);
    }

    @Override
    public List<StockTransApplication> getListWmsZhanYong() {
        BigDecimal num = null;
        ChooseOption ch = chooseOptionDao.findByCategoryCodeAndKey("getListWmsZhanYong_code", "1");
        if (ch != null && ch.getOptionValue() != null) {
            num = new BigDecimal(ch.getOptionValue());
        } else {
            num = new BigDecimal(1 / 24);
        }
        List<StockTransApplication> list = stockTransApplicationDao.getListWmsZhanYongLastTime(num, new BeanPropertyRowMapper<StockTransApplication>(StockTransApplication.class));
        return list;
    }


    @Override
    public List<AgvSku> agvSkuToHub() {
        return agvSkuDao.findAgvSkuByErrorCount();
    }

    @Override
    public List<InboundAgvToHub> findAllByErrorCount() {
        return inboundAgvToHubDao.findAllByErrorCount();
    }

    public void AgvInBoundToHub(InboundAgvToHub inboundAgvToHub) {
        AgvInBoundOrder agvInBoundOrder = new AgvInBoundOrder();
        StockTransApplication sta = staDao.getByPrimaryKey(inboundAgvToHub.getStaId());
        BiChannel biChannel = null;
        if (null != sta.getOwner() && !"".equals(sta.getOwner())) {
            biChannel = biChannelDao.getByCode(sta.getOwner());
        }
        agvInBoundOrder.setBillNumber(sta.getCode());
        agvInBoundOrder.setBillDate(sta.getCreateTime());
        List<AgvInBoundOrderLine> agvInBoundOrderLineList = new ArrayList<AgvInBoundOrderLine>();
        List<InboundAgvToHubLine> inboundAgvToHubLineList = inboundAgvToHubLineDao.findAllByInAgvId(inboundAgvToHub.getId());
        if (null != inboundAgvToHubLineList && inboundAgvToHubLineList.size() > 0) {
            for (InboundAgvToHubLine inboundAgvToHubLine : inboundAgvToHubLineList) {
                AgvInBoundOrderLine agvInBoundOrderLine = new AgvInBoundOrderLine();
                agvInBoundOrderLine.setBoxNo(sta.getCode());
                Sku sku = skuDao.getByPrimaryKey(inboundAgvToHubLine.getSkuId());
                agvInBoundOrderLine.setSkuCode(sku.getCode());
                agvInBoundOrderLine.setQuantity(inboundAgvToHubLine.getQty().intValue());
                if (null != inboundAgvToHubLine.getExpireDate() && !"".equals(inboundAgvToHubLine.getExpireDate())) {
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("batchNumber", inboundAgvToHubLine.getExpireDate());
                    agvInBoundOrderLine.setBatchProperties(map);
                }
                if (null != biChannel) {
                    agvInBoundOrderLine.setOwnerCode(biChannel.getName());
                }
                agvInBoundOrderLineList.add(agvInBoundOrderLine);
            }
        }

        agvInBoundOrder.setAgvInBoundOrderLineList(agvInBoundOrderLineList);
        try {
            com.baozun.ecp.ip.command.agv.Response response = wms3AdapterInteractManager.AgvInBoundToHub(agvInBoundOrder, inboundAgvToHub.getApiName());
            MongoAGVMessage mdbm = new MongoAGVMessage();
            mdbm.setStaCode(sta.getCode());
            mdbm.setOtherUniqueKey(null);
            mdbm.setMsgBody(JsonUtil.writeValue(agvInBoundOrderLineList));
            mdbm.setSendTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            mdbm.setMemo("AgvInBoundToHub" + JsonUtil.writeValue(response));
            mongoOperation.save(mdbm);
            if (response.getResult() == 1) {
                inboundAgvToHub.setStatus(10L);
                inboundAgvToHubDao.save(inboundAgvToHub);
            } else {
                if (null != response && !"".equals(response)) {
                    if (null != response.getOrderResponse() && response.getOrderResponse().size() > 0) {
                        String memo = response.getOrderResponse().get(0).getMemo();
                        if (null != memo && !"".equals(memo)) {
                            inboundAgvToHub.setErrorMsg(memo);
                        }
                    } else {
                        inboundAgvToHub.setErrorMsg(response.getErrorMsg());
                    }
                }
                inboundAgvToHub.setErrorCount(inboundAgvToHub.getErrorCount() + 1);
                inboundAgvToHubDao.save(inboundAgvToHub);
            }
        } catch (Exception e) {
            log.error("AgvInBoundToHub" + inboundAgvToHub.getId() + e);
            inboundAgvToHub.setErrorCount(inboundAgvToHub.getErrorCount() + 1);
            inboundAgvToHubDao.save(inboundAgvToHub);
        }

    }

    @Override
    public void AgvSkuToHub(AgvSku agvSku) {
        Sku sku = skuDao.getByPrimaryKey(agvSku.getSkuId());
        com.baozun.ecp.ip.command.agv.createSku.AgvSku agvSkuHub = new com.baozun.ecp.ip.command.agv.createSku.AgvSku();
        List<SkuBarcode> skuBarCodeList = skuBarcodeDao.findBySkuId(sku.getId());
        String barCode = sku.getBarCode();
        if (null != skuBarCodeList && skuBarCodeList.size() > 0) {
            StringBuffer sb = new StringBuffer();
            sb.append(barCode);
            for (SkuBarcode skuBarCode : skuBarCodeList) {
                sb.append("," + skuBarCode.getBarcode());
            }
            agvSkuHub.setSkuNumber(sb.toString());
        } else {
            agvSkuHub.setSkuNumber(barCode);
        }
        agvSkuHub.setSkuCode(sku.getCode());
        agvSkuHub.setName(sku.getName());
        agvSkuHub.setVirtual(false);
        if (InboundStoreMode.SHELF_MANAGEMENT.equals(sku.getStoremode())) {
            agvSkuHub.setEnableBatch(true);
        } else {
            agvSkuHub.setEnableBatch(false);
        }
        if (null != sku.getListPrice() && !"".equals(sku.getListPrice())) {
            agvSkuHub.setPrice(sku.getListPrice().floatValue());
        }
        if (null != sku.getLength() && !"".equals(sku.getLength())) {
            agvSkuHub.setLength(sku.getLength().divide(new BigDecimal(10)).floatValue());
        }
        if (null != sku.getWidth() && !"".equals(sku.getWidth())) {
            agvSkuHub.setWidth(sku.getWidth().divide(new BigDecimal(10)).floatValue());
        }
        if (null != sku.getHeight() && !"".equals(sku.getHeight())) {
            agvSkuHub.setHeight(sku.getHeight().divide(new BigDecimal(10)).floatValue());
        }
        if (null != sku.getGrossWeight() && !"".equals(sku.getGrossWeight())) {
            agvSkuHub.setWeight(sku.getGrossWeight().divide(new BigDecimal(1000)).floatValue());
        }
        if (null != sku.getBrand() && !"".equals(sku.getBrand())) {
            Brand b = brandDao.getByPrimaryKey(sku.getBrand().getId());
            agvSkuHub.setBrandName(b.getName());
        }

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("artno", sku.getSupplierCode());
        agvSkuHub.setSetExtendedProperties(map);
        try {
            com.baozun.ecp.ip.command.agv.Response response = wms3AdapterInteractManager.AgvSkuToHub(agvSkuHub, agvSku.getApiName());
            MongoAGVMessage mdbm = new MongoAGVMessage();
            mdbm.setStaCode(agvSku.getSkuId().toString());
            mdbm.setOtherUniqueKey(null);
            mdbm.setMsgBody(JsonUtil.writeValue(agvSkuHub));
            mdbm.setSendTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            mdbm.setMemo("AgvInBoundToHub" + JsonUtil.writeValue(response));
            mongoOperation.save(mdbm);
            if (response.getResult() == 1) {
                agvSku.setStatus(10L);
                agvSkuDao.save(agvSku);
            } else {
                agvSku.setErrorCount(agvSku.getErrorCount() + 1);
                agvSku.setErrorMsg(response.getErrorMsg());
                agvSkuDao.save(agvSku);
            }
        } catch (Exception e) {
            log.error("agvSku" + agvSku.getId() + e);
            agvSku.setErrorCount(agvSku.getErrorCount() + 1);
            agvSkuDao.save(agvSku);
        }
    }

    @Override
    public void zhanYongToMq(StockTransApplication stockTransApplication) {
        newInventoryOccupyManager.clearOccupiedInventoryByStaToMq(stockTransApplication.getId());// clear
    }


    @Override
    public void staNotCheckInvToMQByPac(Long id) {
        try {
            String mpMsg = createStaTaskManager.createStaByIdPac(id);
            if (!StringUtil.isEmpty(mpMsg)) {
                try {
                    createStaTaskManager.sendCreateOrderMQToPac(mpMsg);
                    // createStaTaskManager.sendCreateOrderMQToPac(mpMsg + "_qstaId_" + id);
                } catch (Exception e) {
                    createStaTaskManager.updateCreateOrderMQToPacStatus(mpMsg);
                    Log.info("sendCreateOrderMQToPac  " + mpMsg, e);
                }
            }
        } catch (Exception e) {
            log.error("createStaByIdPac----", e);
            // 增加错误次数
            if (log.isDebugEnabled()) {
                log.info("qstaId:" + id, e);
            }
            if (e instanceof BusinessException) {
                BusinessException be = (BusinessException) e;
                if (be.getErrorCode() == ErrorCode.OMS_ORDER_CANACEL) {
                    // 删除数据，记录日志
                    if (log.isDebugEnabled()) {
                        log.info("qstaId:{}, pac order cancel, delete queue and log queue", id);
                    }
                    queueStaManagerExecute.removeQstaAddLog(id);
                } else {
                    if (log.isErrorEnabled()) {
                        log.error("qstaId:{}, pac order cancel, delete queue and log queue not ErrorCode.OMS_ORDER_CANACEL" + id, e);
                    }
                    // 增加错误次数
                    if (log.isDebugEnabled()) {
                        log.info("qstaId:{},create sta throw exception, add error count 1 for qsta,qsta order code is:{}", id);
                    }
                    queueStaManagerExecute.addErrorCountForQsta(id, 1);
                }
            } else {
                if (log.isErrorEnabled()) {
                    log.error("qstaId:{}, pac order cancel, delete queue and log queue not other BusinessException" + id, e);
                }
                // 增加错误次数
                if (log.isDebugEnabled()) {
                    log.info("qstaId:{},create sta throw exception, add error count 1 for qsta,qsta order code is:{}", id);
                }
                queueStaManagerExecute.addErrorCountForQsta(id, 1);
            }
        }
    }



    @Override
    public void agvOutBoundDaemon(AgvOutBoundDto agvOutBoundDto) {
        AgvOutBoundDto agvOutBoundDto2 = new AgvOutBoundDto();
        AgvOutBound agvOutBound = agvOutBoundDao.getByPrimaryKey(agvOutBoundDto.getId());
        try {
            StockTransApplication sta = staDao.getByCode(agvOutBound.getStaCode());
            StaDeliveryInfo d = staDeliveryInfoDao.getByPrimaryKey(sta.getId());
            PickingList pl = pickingListDao.getByPrimaryKey(sta.getPickingList().getId());
            BiChannel ch = biChannelDao.getByCode(sta.getOwner());
            List<StaInvoice> lss = staInvoiceDao.getBySta(sta.getId());
            agvOutBoundDto2.setStaCode(agvOutBound.getStaCode());
            agvOutBoundDto2.setPlCode(agvOutBound.getPlCode());
            agvOutBoundDto2.setStaType(agvOutBound.getStaType());
            agvOutBoundDto2.setShipToName(d.getReceiver());// 收件人姓名
            agvOutBoundDto2.setShipToPhone(d.getMobile() == null ? d.getTelephone() : d.getMobile());// 收件人联系方式
            agvOutBoundDto2.setShipToAddress(d.getAddress());// 收件人地址
            agvOutBoundDto2.setShipFromPhone(ch.getTelephone());// 店铺电话
            agvOutBoundDto2.setShipFromAddress(ch.getAddress());// 店铺地址
            agvOutBoundDto2.setLogisticsCompany(d.getLpCode());// 物流服务商
            agvOutBoundDto2.setTmallbillNumber(sta.getSlipCode2() == null ? sta.getSlipCode1() : sta.getSlipCode2());// 天猫平台单号
            agvOutBoundDto2.setInvoicequantity(lss.size());// 发票数
            agvOutBoundDto2.setOwnerCode(ch.getName());// 店铺name
            agvOutBoundDto2.setPickingListCode(pl.getCode());// 配货批次号
            agvOutBoundDto2.setSourcenumber(sta.getRefSlipCode() == null ? sta.getSlipCode1() : sta.getRefSlipCode());// 上位系統單號
            List<AgvOutBoundLineDto> ls = agvOutBoundLineDao.getAgvOutLineListByAgvId(agvOutBound.getId(), new BeanPropertyRowMapper<AgvOutBoundLineDto>(AgvOutBoundLineDto.class));
            agvOutBoundDto2.setList(ls);
            // 推送适配器
            com.baozun.ecp.ip.command.agv.Response response = wms3AdapterInteractManager.agvOutBound(agvOutBoundDto2);
            MongoAGVMessage mdbm = new MongoAGVMessage();
            mdbm.setStaCode(agvOutBoundDto2.getStaCode());
            mdbm.setOtherUniqueKey(null);
            mdbm.setMsgBody(JsonUtil.writeValue(agvOutBoundDto2));
            mdbm.setSendTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            mdbm.setMemo("agv出库通知;" + JsonUtil.writeValue(response));
            mongoOperation.save(mdbm);

            if (response.getResult() == 1) {
                agvOutBound.setStatus(1);
            } else {
                agvOutBound.setStatus(0);
                agvOutBound.setErrorCount(agvOutBound.getErrorCount() + 1);
            }
        } catch (Exception e) {
            log.error("agvOutBoundDaemon_" + agvOutBound.getStaCode(), e);
            agvOutBound.setErrorCount(agvOutBound.getErrorCount() + 1);
        }
        agvOutBoundDao.save(agvOutBound);
    }

    
    @Override
    public void adPackageUpdate(AdPackageLineDealDto adPackageLineDealDto) {
        AdPackageLineDeal adPackageLineDeal= adPackageLineDealDao.getByPrimaryKey(adPackageLineDealDto.getId());
        AdTsReturnOrderConfirmationWms adTsReturnOrderConfirmationWms=new  AdTsReturnOrderConfirmationWms();
        AdTsReturnOrderConfirmationWms.return_info re=new AdTsReturnOrderConfirmationWms.return_info();
        List<AdTsReturnOrderConfirmationWms.return_info> reList=new  ArrayList<AdTsReturnOrderConfirmationWms.return_info>();
        re.setRemark(adPackageLineDeal.getRemark());
        re.setStatus(adPackageLineDeal.getAdStatus());
        re.setTs_order_id(adPackageLineDeal.getAdOrderId());
        re.setWms_order_type(adPackageLineDeal.getWmsOrderType());
        re.setWms_status(adPackageLineDeal.getWmsStatus());
        reList.add(re);
        adTsReturnOrderConfirmationWms.setReturn_info(reList);
        String str= JsonUtil.writeValue(adTsReturnOrderConfirmationWms);
        log.error("adPackageUpdate:"+str);
        AdReturnOrderResult adReturnOrderResult=null;
        try {
                adReturnOrderResult=adReturnOrderManager.getAdTsReturnOrderConfirmation2Wms("AD", str);
                if(adReturnOrderResult.getError_code() ==200){
                    AdPackageLineDeal adPackageLineDeal2= adPackageLineDealDao.getByPrimaryKey(adPackageLineDealDto.getId());
                    if(adPackageLineDeal2.getStatus()==3){
                        log.error("adPackageUpdate333");
                        return;
                    }else{
                        adPackageLineDeal.setIsSend(1);
                    }
                }else{
                    log.error("adPackageUpdate_1:"+adReturnOrderResult.getMessage()+";"+adPackageLineDealDto.getAdOrderId());
                    adPackageLineDeal.setIsSend(0);
                    if(adPackageLineDeal.getNum()==null){
                        adPackageLineDeal.setNum(1);
                       
                    }else{
                        adPackageLineDeal.setNum(adPackageLineDeal.getNum()+1);
                    }
                }
        } catch (Exception e) {
            log.error("adPackageUpdate_2:"+adPackageLineDealDto.getAdOrderId(),e);
            if(adPackageLineDeal.getNum()==null){
                adPackageLineDeal.setNum(1);
            }else{
                adPackageLineDeal.setNum(adPackageLineDeal.getNum()+1);
            }
        }
        adPackageLineDealDao.save(adPackageLineDeal);
        adPackageLineDealDao.flush();
    }
    
    @Override
    public List<InvetoryChangeCommand> findInvAllByErrorCount() {
        return invetoryChangeDao.findInvAllByErrorCount(new BeanPropertyRowMapper<InvetoryChangeCommand>(InvetoryChangeCommand.class));
    }

    @Override
    public void invChangeLogNoticePac(Long staId, Long stvId, Long id) {
        try {
            wareHouseManagerExe.invChangeLogNoticePac(staId, stvId);
            InvetoryChange invetoryChange = invetoryChangeDao.getByPrimaryKey(id);
            invetoryChange.setStatus(10L);
            invetoryChangeDao.save(invetoryChange);
        } catch (Exception e) {
            log.error("invChangeLogNoticePac" + id + " " + e);
            wareHouseManagerExe.invChangeLongAddErrorCount(id);
        }


    }


}
