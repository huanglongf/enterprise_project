package com.jumbo.webservice.outsourcingWH.manager;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.jumbo.dao.baseinfo.SkuDao;
import com.jumbo.dao.system.ChooseOptionDao;
import com.jumbo.dao.vmi.warehouse.MsgInboundOrderDao;
import com.jumbo.dao.vmi.warehouse.MsgInboundOrderLineDao;
import com.jumbo.dao.vmi.warehouse.MsgOutboundOrderCancelDao;
import com.jumbo.dao.vmi.warehouse.MsgOutboundOrderDao;
import com.jumbo.dao.vmi.warehouse.MsgOutboundOrderLineDao;
import com.jumbo.dao.vmi.warehouse.MsgRtnInboundOrderDao;
import com.jumbo.dao.vmi.warehouse.MsgRtnInboundOrderLineDao;
import com.jumbo.dao.vmi.warehouse.MsgRtnOutboundDao;
import com.jumbo.dao.vmi.warehouse.MsgSKUSyncDao;
import com.jumbo.dao.warehouse.BiChannelDao;
import com.jumbo.dao.warehouse.PackageInfoDao;
import com.jumbo.dao.warehouse.StaDeliveryInfoDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.mq.MarshallerUtil;
import com.jumbo.webservice.biaogan.manager.InOutBoundManager;
import com.jumbo.webservice.outsourcingWH.command.CoList;
import com.jumbo.webservice.outsourcingWH.command.CoList.Co;
import com.jumbo.webservice.outsourcingWH.command.CoResult;
import com.jumbo.webservice.outsourcingWH.command.OrderList;
import com.jumbo.webservice.outsourcingWH.command.OrderListConfirm;
import com.jumbo.webservice.outsourcingWH.command.OrderOutbound;
import com.jumbo.webservice.outsourcingWH.command.OrderOutbound.Order;
import com.jumbo.webservice.outsourcingWH.command.RoInbound;
import com.jumbo.webservice.outsourcingWH.command.RoList;
import com.jumbo.webservice.outsourcingWH.command.RoList.Ro;
import com.jumbo.webservice.outsourcingWH.command.RoList.Ro.RoLine;
import com.jumbo.webservice.outsourcingWH.command.SkuList;
import com.jumbo.webservice.outsourcingWH.command.SkuListConfirm;
import com.jumbo.webservice.outsourcingWH.command.StaInbound;
import com.jumbo.webservice.outsourcingWH.command.StaList;
import com.jumbo.wms.Constants;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.omsService.OmsService;
import com.jumbo.wms.manager.omsService.OmsServiceConstants;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.manager.warehouse.WareHouseManagerCancel;
import com.jumbo.wms.manager.warehouse.WareHouseManagerProxy;
import com.jumbo.wms.model.DefaultStatus;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.system.ChooseOption;
import com.jumbo.wms.model.vmi.warehouse.MsgInboundOrder;
import com.jumbo.wms.model.vmi.warehouse.MsgInboundOrderLine;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrder;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrderCancel;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrderCancelStatus;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrderLine;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnInboundOrder;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnInboundOrderLine;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnOutbound;
import com.jumbo.wms.model.vmi.warehouse.MsgSKUSync;
import com.jumbo.wms.model.warehouse.InventoryStatus;
import com.jumbo.wms.model.warehouse.PackageInfo;
import com.jumbo.wms.model.warehouse.StaDeliveryInfo;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationStatus;
import com.jumbo.wms.model.warehouse.StockTransApplicationType;


@Transactional
@Service("outsourcingWHManager")
public class OutsourcingWHManagerImpl extends BaseManagerImpl implements OutsourcingWHManager {

    @Autowired
    private OmsService omsService;
    @Autowired
    private MsgOutboundOrderDao msgOutboundOrderDao;
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private MsgRtnOutboundDao msgRtnDao;
    @Autowired
    private MsgOutboundOrderCancelDao msgOutboundOrderCancelDao;
    @Autowired
    private WareHouseManager wareHouseManager;
    @Autowired
    private WareHouseManagerCancel wareHouseManagerCancel;
    @Autowired
    private MsgInboundOrderDao msgInboundOrderDao;
    @Autowired
    private BiChannelDao companyShopDao;
    @Autowired
    private MsgOutboundOrderLineDao msgOutboundOrderLineDao;
    @Autowired
    private MsgInboundOrderLineDao msgILineDao;
    @Autowired
    private MsgRtnInboundOrderLineDao rtnOrderLineDao;
    @Autowired
    private ChooseOptionDao chooseOptionDao;
    @Autowired
    private MsgRtnInboundOrderDao rtnOrderDao;
    @Autowired
    private InOutBoundManager inoutManager;
    @Autowired
    private WareHouseManagerProxy wareHouseManagerProxy;
    @Autowired
    private SkuDao skuDao;
    @Autowired
    private PackageInfoDao packageInfoDao;
    @Autowired
    private StaDeliveryInfoDao staDeliveryInfoDao;
    @Autowired
    private MsgSKUSyncDao msgSKUSyncDao;

    private static final long serialVersionUID = -7845664201273659818L;

    /**
     * 出库单据出库
     */
    public List<MsgRtnOutbound> findoutboundOrder(String resultXml, String source) throws Exception {
        OrderOutbound orderOutbound = (OrderOutbound) MarshallerUtil.buildJaxb(OrderOutbound.class, resultXml);
        List<MsgRtnOutbound> msgoutList = new ArrayList<MsgRtnOutbound>();
        if (orderOutbound != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            for (Order order : orderOutbound.getOrder()) {
                try {
                    MsgRtnOutbound outOrder = msgRtnDao.findBySlipCode(order.getCode());
                    if (outOrder == null || order.getTrackingCode() == null || "".equals(order.getTrackingCode())) {
                        outOrder = new MsgRtnOutbound();
                        outOrder.setStatus(DefaultStatus.CREATED);
                        outOrder.setSource(source);
                        outOrder.setStaCode(order.getCode());
                        outOrder.setLpCode(order.getLogistic());
                        outOrder.setTrackingNo(order.getTrackingCode());
                        outOrder.setWeight(new BigDecimal(order.getWeight()));
                        try {
                            outOrder.setOutboundTime(sdf.parse(order.getOutboundTime()));
                            outOrder.setType(order.getType());
                        } catch (ParseException e) {
                            log.error("", e);
                        }
                        outOrder = msgRtnDao.save(outOrder);
                        msgRtnDao.flush();
                        msgoutList.add(outOrder);
                    }
                } catch (BusinessException ex) {
                    throw new BusinessException(ErrorCode.VMI_WH_RTN_OUTBOUND_MISS_MSG);
                }
            }
        }
        return msgoutList;
    }

    /**
     * 获取取消单据
     */
    public String getCancelOrderBound(String source) {
        CoList colist = new CoList();
        Long batchNo = 0l;
        // List<MsgOutboundOrderCancel> orderlist =
        // msgOutboundOrderCancelDao.findMsgOutboundOrderCancelList(source);
        // if (orderlist != null && orderlist.size() > 0) {
        // batchNo = orderlist.get(0).getBatchId();
        // } else {
        // batchNo = msgOutboundOrderCancelDao.findoutBoundCancleBatchNo(new
        // SingleColumnRowMapper<Long>(Long.class));
        // // 查询新建取消失败的单据
        // orderlist = msgOutboundOrderCancelDao.findOneVimCancelInfoByStatus(source);
        // }
        batchNo = msgOutboundOrderCancelDao.findoutBoundCancleBatchNo(new SingleColumnRowMapper<Long>(Long.class));
        // 查询新建取消失败的单据
        List<MsgOutboundOrderCancel> orderlist = msgOutboundOrderCancelDao.findOneVimCancelInfoByStatus(source);
        colist.setTotalQty(String.valueOf(orderlist.size()));
        colist.setBatchId(String.valueOf(batchNo));
        for (MsgOutboundOrderCancel order : orderlist) {
            Co co = new Co();
            co.setActionflag("D");
            co.setCode(order.getStaCode());
            colist.getCo().add(co);
            order.setBatchId(batchNo);
            order.setStatus(MsgOutboundOrderCancelStatus.SENT);
            msgOutboundOrderCancelDao.save(order);
        }
        String respXml = MarshallerUtil.buildJaxb(colist);
        return respXml;
    }

    /**
     * 获取销售单据
     */
    public synchronized String getPullSo(String source) {
        String respXml = "";
        long batchNo = 0;
        String plfCode = "";
        List<MsgOutboundOrder> list = null;
        String owner = "";
        List<ChooseOption> shopSource = chooseOptionDao.findOptionListByCategoryCode(Constants.VIM_WH_SOURCE_GQS_CQ);
        for (ChooseOption co : shopSource) {
            list = msgOutboundOrderDao.findSOByOwnerSource(source, co.getOptionKey(), DefaultStatus.SENT.getValue(), false, new BeanPropertyRowMapper<MsgOutboundOrder>(MsgOutboundOrder.class));
            if (list == null || list.size() == 0) {
                batchNo = msgOutboundOrderDao.findBatchNo(new SingleColumnRowMapper<Long>(Long.class));
                list = msgOutboundOrderDao.findSOByOwnerSource(source, co.getOptionKey(), DefaultStatus.CREATED.getValue(), false, new BeanPropertyRowMapper<MsgOutboundOrder>(MsgOutboundOrder.class));
            } else {
                batchNo = list.get(0).getBatchId();
            }
            if (list != null && list.size() > 0) {
                owner = co.getOptionKey();
                break;
            }
        }
        if (list != null && list.size() > 0) {
            // 把同步次数最少的设置成下次同步 把所有的序列更新
            chooseOptionDao.updateSortNo(Constants.VIM_WH_SOURCE_GQS_CQ);
        }
        OrderList orderList = new OrderList();
        orderList.setSource(owner);
        orderList.setBatchId("" + batchNo);
        orderList.setTotalQty(String.valueOf(list.size() > Constants.WEBSERVICE_RETURN_NUMBER ? Constants.WEBSERVICE_RETURN_NUMBER : list.size()));
        for (int j = 0; j < list.size(); j++) {
            MsgOutboundOrder o = list.get(j);
            OrderList.Order order = new OrderList.Order();
            order.setCode(o.getStaCode());
            StockTransApplication sta = staDao.findStaByCode(o.getStaCode());
            if (sta == null) {
                order.setPlfCode("");
                order.setStoreCode("");
            } else {
                if (StockTransApplicationType.OUTBOUND_RETURN_REQUEST.equals(sta.getType())) {
                    order.setPlfCode(sta.getRefSlipCode());
                } else {
                    plfCode = sta.getSlipCode2();
                }
                order.setPlfCode(plfCode == null ? " " : plfCode);
                BiChannel cs = companyShopDao.getByCode(sta.getOwner());
                order.setStoreCode(cs.getCode());
            }
            order.setType(o.getStaType() == 42 ? "EX" : "SS");
            SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
            order.setCreateTime(sf.format(new Date()));
            order.setCountry(o.getCity());
            order.setProvince(o.getProvince());
            order.setCity(o.getCity());
            order.setDistrict(o.getDistrict() == null ? "" : o.getDistrict());
            order.setAddress(o.getAddress());
            order.setZipcode(o.getZipcode());
            order.setTelephone(o.getTelePhone());
            order.setMobile(o.getMobile() == null ? "" : o.getMobile());
            order.setReceiver(o.getReceiver());
            order.setLogistic(o.getLpCode());
            order.setTotalAmt(o.getTotalActual() == null ? "" + new BigDecimal(0) : "" + o.getTotalActual());
            order.setLogisticFee("" + o.getTransferFee());
            order.setRemark(o.getRemark() == null ? " " : o.getRemark());

            List<MsgOutboundOrderLine> lines = msgOutboundOrderLineDao.findeMsgOutLintBymsgOrderId2(o.getId());
            for (MsgOutboundOrderLine line : lines) {
                OrderList.Order.OrderLine orderLine = new OrderList.Order.OrderLine();
                orderLine.setBarcode(line.getSku().getBarCode());
                orderLine.setBzskuCode(line.getSku().getCode());
                orderLine.setQuantity("" + line.getQty());
                orderLine.setSku(line.getSku().getCode());
                order.getOrderLine().add(orderLine);
            }

            orderList.getOrder().add(order);
            // 更新批次号和状态
            msgOutboundOrderDao.updateBatchNoByID(batchNo, DefaultStatus.SENT.getValue(), o.getId());
            if (Constants.WEBSERVICE_RETURN_NUMBER - 2 < j) {
                break;
            }
        }
        respXml = (String) MarshallerUtil.buildJaxb(orderList);
        return respXml;
    }

    /**
     * 确认接收单据
     */
    public void serviceCfOrder(OrderListConfirm confirm) {
        log.debug("============================================================================================DLY_ERROR:" + confirm);
        if (confirm.getBatchId() == null || "".equals(confirm.getBatchId())) {
            throw new BusinessException(ErrorCode.OUTSOURCING_RTN_BATCH_TYPE_ERROR);
        }
        if (confirm.getBatchType() == null || "".equals(confirm.getBatchType())) {
            throw new BusinessException(ErrorCode.OUTSOURCING_RTN_BATCH_TYPE_ERROR);
        }
        if ("Sale".equals(confirm.getBatchType()) || "OutBound".equals(confirm.getBatchType())) {// 销售
            // 或
            // 其他出库
            log.debug("============================================================================================DLY_TYPE:" + confirm.getBatchType());
            if ("0".equals(confirm.getStatus())) {
                // 修改接收失败的单据 状态为 0
                if (confirm.getOrder() == null || confirm.getOrder().size() == 0) {
                    msgOutboundOrderDao.updateStatusByBatchId(DefaultStatus.CANCELED.getValue(), Long.valueOf(confirm.getBatchId()));
                } else {
                    // 更新处理失败的单据
                    for (OrderListConfirm.Order o : confirm.getOrder()) {
                        msgOutboundOrderDao.updateOutBoundStatubatchNo(DefaultStatus.CANCELED.getValue(), o.getCode());
                    }
                    msgOutboundOrderDao.flush();
                    // 查找 接收成功的单据
                    List<MsgOutboundOrder> outOrderList = msgOutboundOrderDao.findeMsgOutboundOrderBybatchId(Long.valueOf(confirm.getBatchId()), DefaultStatus.SENT);
                    for (MsgOutboundOrder order : outOrderList) {
                        msgOutboundOrderDao.updateStatusByStaCode(DefaultStatus.FINISHED.getValue(), order.getStaCode());
                    }
                }
            } else if ("1".equals(confirm.getStatus())) {
                log.debug("============================================================================================DLY_STATUS:" + confirm.getStatus());
                msgOutboundOrderDao.updateStatusByBatchId(DefaultStatus.FINISHED.getValue(), Long.valueOf(confirm.getBatchId()));
                log.debug("============================================================================================DLY_BATCHID:" + confirm.getBatchId());
            }
        } else if ("Return".equals(confirm.getBatchType()) || "InBound".equals(confirm.getBatchType())) { // 退换货
            // 或
            // 其他入库
            if ("0".equals(confirm.getStatus())) {
                // 修改接收失败的单据 状态为 0
                if (confirm.getOrder() == null || confirm.getOrder().size() == 0) {
                    // 默认全部失败
                    msgInboundOrderDao.updateInboundOrderStatusByBatchId(Long.valueOf(confirm.getBatchId()), DefaultStatus.CANCELED.getValue());
                } else {
                    for (OrderListConfirm.Order o : confirm.getOrder()) {
                        msgInboundOrderDao.updateInboundOrderByStaCode(DefaultStatus.CANCELED.getValue(), o.getCode());
                    }
                    msgInboundOrderDao.flush();
                    // 查找 接收成功的单据
                    List<MsgInboundOrder> inboundOrderList = msgInboundOrderDao.findMsgInbounderListByBybatchId(Long.valueOf(confirm.getBatchId()), DefaultStatus.SENT);
                    for (MsgInboundOrder inboundOrder : inboundOrderList) {
                        msgInboundOrderDao.updateInboundOrderStatusByStaCode(inboundOrder.getStaCode(), DefaultStatus.FINISHED.getValue());
                    }
                }
            } else if ("1".equals(confirm.getStatus())) {
                msgInboundOrderDao.updateInboundOrderStatusByBatchId(Long.valueOf(confirm.getBatchId()), DefaultStatus.FINISHED.getValue());
            }
        } else {
            throw new BusinessException(ErrorCode.OUTSOURCING_RTN_BATCH_TYPE_ERROR);
        }
    }

    /**
     * 确认取消
     * 
     * @param resultXml
     */
    public void vimExecuteCreateCancelOrder(String resultXml, String source) {
        CoResult bhSyncRequest = (CoResult) MarshallerUtil.buildJaxb(CoResult.class, resultXml);
        if (bhSyncRequest != null) {
            for (CoResult.Co co : bhSyncRequest.getCo()) {
                List<MsgOutboundOrderCancel> list = msgOutboundOrderCancelDao.findByStaCode(co.getCode());
                MsgOutboundOrderCancel cancleOrder = null;
                if (list != null && list.size() > 0) {
                    cancleOrder = list.get(0);
                } else {
                    continue;
                }
                if (cancleOrder.getStatus().equals(MsgOutboundOrderCancelStatus.FINISHED)) {
                    continue;
                }
                if (bhSyncRequest.getBatchId() != null && !"".equals(bhSyncRequest.getBatchId())) {
                    cancleOrder.setBatchId(Long.parseLong(bhSyncRequest.getBatchId()));
                }
                cancleOrder.setStatus(MsgOutboundOrderCancelStatus.SENT);
                if (co.getStatus() != null && co.getStatus().equals("1")) {
                    cancleOrder.setIsCanceled(true);
                } else if (co.getStatus() != null && co.getStatus().equals("0")) {
                    cancleOrder.setIsCanceled(false);
                }
            }
        }
    }

    /**
     * 执行取消
     * 
     * @param resultXml
     * @return
     */
    public void vimExecuteCancelOrder(Long msgid) {
        MsgOutboundOrderCancel msg = msgOutboundOrderCancelDao.getByPrimaryKey(msgid);
        if (msg == null || !msg.getStatus().equals(MsgOutboundOrderCancelStatus.SENT) || msg.getIsCanceled() == null) {
            return;
        }
        StockTransApplication sta = staDao.findStaByCode(msg.getStaCode());
        if (msg.getIsCanceled()) {
            if (sta == null) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR);
            }
            wareHouseManagerCancel.cancelSalesSta(sta.getId(), null);
        }
        // 调用OMS取消接口
        msg.setUpdateTime(new Date());
        if (!StringUtils.hasText(sta.getSystemKey())) {
            int result = omsService.cancelSoReturn(sta, msg.getMsg(), msg.getIsCanceled(), OMS_CANCEL_URL);
            if (result == OmsServiceConstants.SUCCESS) {
                msg.setStatus(MsgOutboundOrderCancelStatus.FINISHED);
            } else {
                throw new BusinessException("When cancel order" + sta.getCode() + ",wms success but oms return error code" + result);
            }
        } else {
            msg.setStatus(MsgOutboundOrderCancelStatus.FINISHED);
        }
        msgOutboundOrderCancelDao.save(msg);
        // 取消中间表有重复记录
        msgOutboundOrderCancelDao.updateOrderById(msg.getId(), msg.getStatus().getValue());

    }

    /**
     * 执行销售出库
     */
    public void outOrderToSale(List<MsgRtnOutbound> msgoutList) {
        for (MsgRtnOutbound msgRtnbound : msgoutList) {
            StockTransApplication sta = staDao.findStaByCode(msgRtnbound.getStaCode());
            try {
                if (sta != null) {
                    if (msgRtnbound.getType().equals("P") || Constants.VIM_WH_SOURCE_GQS.equals(msgRtnbound.getSource())) {
                        wareHouseManagerProxy.vmiSalesCreatePageInfo(msgRtnbound.getId());
                    }
                    if (msgRtnbound.getType().equals("S") || Constants.VIM_WH_SOURCE_GQS.equals(msgRtnbound.getSource())) {
                        if (sta.getStatus().equals(StockTransApplicationStatus.INTRANSIT)) {
                            StaDeliveryInfo di = sta.getStaDeliveryInfo();
                            if (di != null) {
                                PackageInfo pg = packageInfoDao.findByTrackingNo(di.getTrackingNo());
                                if (pg != null) {
                                    pg.setTrackingNo(msgRtnbound.getTrackingNo());
                                    pg.setLpCode(msgRtnbound.getLpCode());
                                    pg.setWeight(msgRtnbound.getWeight());
                                    pg.setLastModifyTime(new Date());
                                    di.setLpCode(msgRtnbound.getLpCode());
                                    di.setTrackingNo(msgRtnbound.getTrackingNo());
                                    di.setWeight(msgRtnbound.getWeight());
                                    // 触发器移除 trg_update_lpcode_is_null
                                    ChooseOption op = chooseOptionDao.findByCategoryCodeAndKey(Constants.UPDATE_DELIVERY_LPCODE, Constants.UPDATE_LPCODE_IS_OPEN);
                                    String flag = op == null ? "false" : (op.getOptionValue() == null ? "false" : op.getOptionValue());
                                    if ("true".equals(flag)) {
                                        if (msgRtnbound.getLpCode() == null) {
                                            log.error("物流商编码不能为空!staCode=" + sta.getCode());
                                            throw new BusinessException(ErrorCode.TRANSPORTATOR_REF_TRANSPORTATOR_IS_NULL);
                                        }
                                    }
                                    staDeliveryInfoDao.save(di);
                                    packageInfoDao.save(pg);
                                }
                            }
                            wareHouseManagerProxy.salesStaOutBound(msgRtnbound.getId());
                        } else if (sta.getStatus().equals(StockTransApplicationStatus.CREATED)) {
                            wareHouseManagerProxy.callVmiSalesStaOutBound(msgRtnbound.getId());
                        }
                        if (sta.getStatus().equals(StockTransApplicationStatus.FINISHED)) {
                            wareHouseManager.updateMsgRtnOutbound(msgRtnbound.getId(), DefaultStatus.FINISHED.getValue());
                        }
                    }
                } else {
                    wareHouseManager.updateMsgRtnOutbound(msgRtnbound.getId(), DefaultStatus.CANCELED.getValue());
                }
            } catch (Exception ex) {
                if (log.isErrorEnabled()) {
                    log.error("outOrderToSale Exception:" + sta.getCode(), ex);
                }
            }
        }
    }

    /**
     * 获取退货单据
     */
    public synchronized String findinboundReturnRequest(String source) throws Exception {
        List<MsgInboundOrder> list = null;
        Long batchNo = null;
        String owner = "";
        List<ChooseOption> shopSource = chooseOptionDao.findOptionListByCategoryCode(Constants.VIM_WH_SOURCE_GQS_CQ);
        for (ChooseOption co : shopSource) {
            list = msgInboundOrderDao.findByOwner(source, co.getOptionKey(), StockTransApplicationType.INBOUND_RETURN_REQUEST, DefaultStatus.SENT);
            if (list == null || list.size() == 0) {
                batchNo = msgInboundOrderDao.findInOrderBatchNo(new SingleColumnRowMapper<Long>(Long.class));
                list = msgInboundOrderDao.findByOwner(source, co.getOptionKey(), StockTransApplicationType.INBOUND_RETURN_REQUEST, DefaultStatus.CREATED);
            } else {
                batchNo = list.get(0).getBatchId();
            }
            if (list != null && list.size() > 0) {
                owner = co.getOptionKey();
                break;
            }
        }
        if (list != null && list.size() > 0) {
            // 把同步次数最少的设置成下次同步 把所有的序列更新
            chooseOptionDao.updateSortNo(Constants.VIM_WH_SOURCE_GQS_CQ);
        }
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddhhmmss");
        RoList rolist = new RoList();
        rolist.setSource(owner);
        rolist.setTotalQty(String.valueOf(list.size() > Constants.WEBSERVICE_RETURN_NUMBER ? Constants.WEBSERVICE_RETURN_NUMBER : list.size()));
        rolist.setBatchId(String.valueOf(batchNo));
        int i = 0;
        for (MsgInboundOrder msginorder : list) {
            Ro ro = new Ro();
            String plfCode = "";
            ro.setCode(msginorder.getStaCode());
            StockTransApplication sta = staDao.findStaByCode(msginorder.getStaCode());
            List<StockTransApplication> staList = staDao.findBySlipCode1(sta.getSlipCode1());
            plfCode = staList.size() == 0 ? "" : staList.get(0).getCode();
            ro.setPlfCode(plfCode == null ? "" : plfCode);
            ro.setStoreCode(msginorder.getStaCode() == null ? "" : msginorder.getStaCode());
            ro.setLogistic(msginorder.getLpCode() == null ? "" : msginorder.getLpCode());
            ro.setTrackingNo(msginorder.getTrackingNo() == null ? "" : msginorder.getTrackingNo());
            ro.setReceiver(msginorder.getReceiver() == null ? "" : msginorder.getReceiver());
            ro.setTelephone(msginorder.getTelephone() == null ? "" : msginorder.getTelephone());
            ro.setMobile(msginorder.getMobile() == null ? "" : msginorder.getMobile());
            ro.setCreateTime(sf.format(msginorder.getCreateTime()));

            List<MsgInboundOrderLine> msgLineList = msgILineDao.fomdMsgInboundOrderLineByOId(msginorder.getId());

            for (MsgInboundOrderLine inLinecomd : msgLineList) {
                RoLine roline = new RoLine();
                roline.setSku(inLinecomd.getSku().getCode());
                roline.setBzskuCode(inLinecomd.getSku().getCode() == null ? "" : inLinecomd.getSku().getCode());
                roline.setBarcode(inLinecomd.getSku().getBarCode() == null ? "" : inLinecomd.getSku().getBarCode());
                roline.setQuantity(String.valueOf(inLinecomd.getQty()) == null ? "" : String.valueOf(inLinecomd.getQty()));
                ro.getRoLine().add(roline);
            }
            rolist.getRo().add(ro);

            msgInboundOrderDao.updateInboundOrder(batchNo, DefaultStatus.SENT.getValue(), msginorder.getId());
            // 更新批次号和状态
            if (Constants.WEBSERVICE_RETURN_NUMBER - 2 < i) {
                break;
            }
            i++;
        }
        return MarshallerUtil.buildJaxb(rolist);
    }

    /**
     * 退货单据入库request
     */
    public List<MsgRtnInboundOrder> transactionRtnInbound(String resultXml, String source) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
        List<MsgRtnInboundOrder> rtnList = new ArrayList<MsgRtnInboundOrder>();
        RoInbound colist = (RoInbound) MarshallerUtil.buildJaxb(RoInbound.class, resultXml);
        if (colist != null) {
            for (RoInbound.Ro ro : colist.getRo()) {
                try {
                    if (ro.getCode() == null || ro.getCode().equals("")) {
                        continue;
                    }
                    MsgRtnInboundOrder msgrtnInbound = new MsgRtnInboundOrder();
                    msgrtnInbound.setStaCode(ro.getCode());
                    Map<String, InventoryStatus> invoiceNumMap = inoutManager.findMsgInvStatusByStaCode(msgrtnInbound.getStaCode());
                    try {
                        msgrtnInbound.setSource(source);
                        msgrtnInbound.setInboundTime(sf.parse(ro.getInboundTime()));
                        msgrtnInbound.setCreateTime(new Date());
                        msgrtnInbound.setStatus(DefaultStatus.CREATED);
                    } catch (ParseException e) {
                        log.error("", e);
                    }
                    rtnList.add(msgrtnInbound);
                    rtnOrderDao.save(msgrtnInbound);
                    for (RoInbound.Ro.RoLine line : ro.getRoLine()) {
                        MsgRtnInboundOrderLine rtnline = new MsgRtnInboundOrderLine();
                        Sku sku = skuDao.getByCode(line.getSku());
                        if (sku != null) {
                            rtnline.setSkuId(sku.getId());
                        }
                        rtnline.setSkuCode(line.getSku());
                        rtnline.setOutStatus(line.getStatus());
                        rtnline.setBarcode(line.getBarcode());
                        rtnline.setQty(Long.valueOf(line.getQuantity().toString()));
                        if (line.getStatus() == null || line.getStatus().trim().equals("")) {
                            rtnline.setInvStatus(invoiceNumMap.get("0"));// 可销售库存
                        } else {
                            InventoryStatus status = invoiceNumMap.get(line.getStatus().trim());
                            if (status == null) {
                                rtnline.setInvStatus(invoiceNumMap.get("1"));// 可销售库存
                            } else {
                                rtnline.setInvStatus(status);
                            }
                        }
                        rtnline.setMsgRtnInOrder(msgrtnInbound);
                        rtnOrderLineDao.save(rtnline);
                    }
                } catch (BusinessException ex) {
                    throw new BusinessException(ErrorCode.VMI_WH_RTN_OUTBOUND_MISS_MSG);
                }
            }
        }
        return rtnList;
    }

    /**
     * 获取入库单据
     */
    public synchronized String pullInBound(String source) {
        long batchNo = 0L;
        // 获取 新建 以及 发送失败 的 入库单据
        List<MsgInboundOrder> list = null;
        String owner = "";
        List<ChooseOption> shopSource = chooseOptionDao.findOptionListByCategoryCode(Constants.VIM_WH_SOURCE_GQS_CQ);
        for (ChooseOption co : shopSource) {
            list = msgInboundOrderDao.findByOwner(source, co.getOptionKey(), null, DefaultStatus.SENT);
            if (list == null || list.size() == 0) {
                batchNo = msgInboundOrderDao.findInOrderBatchNo(new SingleColumnRowMapper<Long>(Long.class));
                list = msgInboundOrderDao.findByOwner(source, co.getOptionKey(), null, DefaultStatus.CREATED);
            } else {
                batchNo = list.get(0).getBatchId();
            }
            if (list != null && list.size() > 0) {
                owner = co.getOptionKey();
                break;
            }
        }
        if (list != null && list.size() > 0) {
            // 把同步次数最少的设置成下次同步 把所有的序列更新
            chooseOptionDao.updateSortNo(Constants.VIM_WH_SOURCE_GQS_CQ);
        }
        String respXml = "";
        StaList staList = new StaList();
        staList.setBatchId("" + batchNo);
        staList.setSource(owner);
        staList.setTotalQty(String.valueOf(list.size() > Constants.WEBSERVICE_RETURN_NUMBER ? Constants.WEBSERVICE_RETURN_NUMBER : list.size()));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String plfCode = "";
        for (int j = 0; j < list.size(); j++) {
            MsgInboundOrder msginorder = list.get(j);
            StaList.Sta sta = new StaList.Sta();
            sta.setCode(msginorder.getStaCode());
            sta.setCreateTime((sta.getCreateTime() != null ? sdf.format(sta.getCreateTime()) : ""));
            StockTransApplication staBean = staDao.findStaByCode(msginorder.getStaCode());
            if (staBean == null) {
                sta.setPlfCode("");
                sta.setStoreCode("");
            } else {
                plfCode = staBean.getSlipCode2();
                if (!StringUtils.hasText(plfCode)) {
                    plfCode = staBean.getSlipCode1();
                }
                BiChannel cs = companyShopDao.getByCode(staBean.getOwner());
                sta.setStoreCode(cs.getCode());
                sta.setPlfCode(plfCode == null ? (staBean.getRefSlipCode() == null ? "" : staBean.getRefSlipCode()) : plfCode);
            }
            List<MsgInboundOrderLine> msgLineList = msgILineDao.fomdMsgInboundOrderLineByOId(msginorder.getId());
            for (MsgInboundOrderLine inLinecomd : msgLineList) {
                StaList.Sta.StaLine staLine = new StaList.Sta.StaLine();
                staLine.setSku(inLinecomd.getSku().getCode());
                staLine.setBzskuCode(inLinecomd.getSku().getCode() == null ? "" : inLinecomd.getSku().getCode());
                staLine.setBarcode(inLinecomd.getSku().getBarCode() == null ? "" : inLinecomd.getSku().getBarCode());
                staLine.setQuantity(String.valueOf(inLinecomd.getQty()) == null ? "" : String.valueOf(inLinecomd.getQty()));
                sta.getStaLine().add(staLine);
            }
            staList.getSta().add(sta);
            // 更新批次号和状态
            msgInboundOrderDao.updateInboundOrder(batchNo, DefaultStatus.SENT.getValue(), msginorder.getId());
            if (Constants.WEBSERVICE_RETURN_NUMBER - 2 < j) {
                break;
            }
        }
        respXml = (String) MarshallerUtil.buildJaxb(staList);
        return respXml;
    }

    /**
     * 入库单据入库
     */
    public List<MsgRtnInboundOrder> ibInbound(String resultXml, String source) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
        List<MsgRtnInboundOrder> rtnList = new ArrayList<MsgRtnInboundOrder>();
        StaInbound inbound = (StaInbound) MarshallerUtil.buildJaxb(StaInbound.class, resultXml);
        if (inbound != null) {
            for (StaInbound.Sta sta : inbound.getSta()) {
                try {
                    if (sta.getCode() == null || sta.getCode().equals("")) {
                        continue;
                    }
                    MsgRtnInboundOrder msgrtnInbound = new MsgRtnInboundOrder();
                    msgrtnInbound.setStaCode(sta.getCode());
                    msgrtnInbound.setSource(source);
                    msgrtnInbound.setSourceWh(source);
                    msgrtnInbound.setCreateTime(new Date());
                    msgrtnInbound.setStatus(DefaultStatus.CREATED);
                    Map<String, InventoryStatus> invoiceNumMap = inoutManager.findMsgInvStatusByStaCode(msgrtnInbound.getStaCode());
                    try {
                        // 入库时间
                        msgrtnInbound.setInboundTime(sf.parse(sta.getInboundTime()));
                    } catch (ParseException e) {
                        log.error("", e);
                    }
                    rtnList.add(msgrtnInbound);
                    rtnOrderDao.save(msgrtnInbound);
                    for (StaInbound.Sta.StaLine line : sta.getStaLine()) {
                        MsgRtnInboundOrderLine rtnline = new MsgRtnInboundOrderLine();
                        Sku sku = skuDao.getByCode(line.getSku());
                        if (sku != null) {
                            rtnline.setSkuId(sku.getId());
                        }
                        rtnline.setSkuCode(line.getSku());
                        rtnline.setOutStatus(line.getStatus());
                        rtnline.setBarcode(line.getBarcode());
                        rtnline.setQty(Long.valueOf(line.getQuantity().toString()));
                        if (line.getStatus() == null || line.getStatus().trim().equals("")) {
                            rtnline.setInvStatus(invoiceNumMap.get("1"));// 可销售库存
                        } else {
                            InventoryStatus status = invoiceNumMap.get(line.getStatus().trim());
                            if (status == null) {
                                rtnline.setInvStatus(invoiceNumMap.get("1"));// 可销售库存
                            } else {
                                rtnline.setInvStatus(status);
                            }
                        }
                        rtnline.setMsgRtnInOrder(msgrtnInbound);
                        rtnOrderLineDao.save(rtnline);
                    }
                } catch (BusinessException ex) {
                    throw new BusinessException(ErrorCode.VMI_WH_RTN_OUTBOUND_MISS_MSG);
                }
            }
        }
        return rtnList;
    }

    /**
     * 获取出库单据
     */
    public synchronized String pullOutBound(String source) {

        List<MsgOutboundOrder> list = null;
        String respXml = "";
        long batchNo = 0;
        String owner = "";
        List<ChooseOption> shopSource = chooseOptionDao.findOptionListByCategoryCode(Constants.VIM_WH_SOURCE_GQS_CQ);
        for (ChooseOption co : shopSource) {
            list = msgOutboundOrderDao.findSOByOwnerSource(source, co.getOptionKey(), DefaultStatus.SENT.getValue(), true, new BeanPropertyRowMapper<MsgOutboundOrder>(MsgOutboundOrder.class));
            if (list == null || list.size() == 0) {
                batchNo = msgOutboundOrderDao.findBatchNo(new SingleColumnRowMapper<Long>(Long.class));
                list = msgOutboundOrderDao.findSOByOwnerSource(source, co.getOptionKey(), DefaultStatus.CREATED.getValue(), true, new BeanPropertyRowMapper<MsgOutboundOrder>(MsgOutboundOrder.class));
            } else {
                batchNo = list.get(0).getBatchId();
            }
            if (list != null && list.size() > 0) {
                owner = co.getOptionKey();
                break;
            }
        }
        if (list != null && list.size() > 0) {
            // 把同步次数最少的设置成下次同步 把所有的序列更新
            chooseOptionDao.updateSortNo(Constants.VIM_WH_SOURCE_GQS_CQ);
        }

        String plfCode = "";
        OrderList orderList = new OrderList();
        orderList.setSource(owner);
        orderList.setBatchId("" + batchNo);
        orderList.setTotalQty(String.valueOf(list.size() > Constants.WEBSERVICE_RETURN_NUMBER ? Constants.WEBSERVICE_RETURN_NUMBER : list.size()));
        for (int j = 0; j < list.size(); j++) {
            MsgOutboundOrder o = list.get(j);
            OrderList.Order order = new OrderList.Order();
            order.setCode(o.getStaCode());
            StockTransApplication sta = staDao.findStaByCode(o.getStaCode());
            if (sta == null) {
                order.setPlfCode("");
                order.setStoreCode("");
            } else {
                if (StockTransApplicationType.OUTBOUND_RETURN_REQUEST.equals(sta.getType())) {
                    plfCode = sta.getRefSlipCode();
                } else if (StockTransApplicationType.OUTBOUND_SALES.equals(sta.getType())) {
                    plfCode = sta.getSlipCode2();
                } else {
                    plfCode = sta.getRefSlipCode();
                }
                order.setPlfCode(plfCode == null ? " " : plfCode);
                BiChannel cs = companyShopDao.getByCode(sta.getOwner());
                order.setStoreCode(cs.getCode());
            }
            order.setType("TT");
            SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
            order.setCreateTime(sf.format(new Date()));
            order.setCountry(o.getCity());
            order.setProvince(o.getProvince());
            order.setCity(o.getCity());
            order.setDistrict(o.getDistrict() == null ? "" : o.getDistrict());
            order.setAddress(o.getAddress());
            order.setZipcode(o.getZipcode());
            order.setTelephone(o.getTelePhone());
            order.setMobile(o.getMobile() == null ? "" : o.getMobile());
            order.setReceiver(o.getReceiver());
            order.setLogistic(o.getLpCode());
            order.setTotalAmt(o.getTotalActual() == null ? "" + new BigDecimal(0) : "" + o.getTotalActual());
            order.setLogisticFee("" + o.getTransferFee());
            order.setRemark(o.getRemark() == null ? " " : o.getRemark());

            List<MsgOutboundOrderLine> lines = msgOutboundOrderLineDao.findeMsgOutLintBymsgOrderId2(o.getId());
            for (MsgOutboundOrderLine line : lines) {
                OrderList.Order.OrderLine orderLine = new OrderList.Order.OrderLine();
                orderLine.setBarcode(line.getSku().getBarCode());
                orderLine.setBzskuCode(line.getSku().getCode());
                orderLine.setQuantity("" + line.getQty());
                orderLine.setSku(line.getSku().getCode());
                order.getOrderLine().add(orderLine);
            }
            orderList.getOrder().add(order);
            // 更新批次号和状态
            msgOutboundOrderDao.updateBatchNoByID(batchNo, DefaultStatus.SENT.getValue(), o.getId());
            // if (Constants.WEBSERVICE_RETURN_NUMBER - 2 < j) {
            // break;
            // }
        }
        respXml = (String) MarshallerUtil.buildJaxb(orderList);
        return respXml;
    }

    /**
     * 获取SKU信息
     */
    public String pullSku(String source) {
        List<MsgSKUSync> skus = msgSKUSyncDao.findVmiMsgSKUSync(source, new BeanPropertyRowMapper<MsgSKUSync>(MsgSKUSync.class));
        String respXml = "";
        SkuList skuList = new SkuList();
        Long batchNo = msgSKUSyncDao.findSKUSyncBatchNo(new SingleColumnRowMapper<Long>(Long.class));
        skuList.setBatchId(batchNo.toString());
        skuList.setTotalQty(String.valueOf(skus.size()));
        for (MsgSKUSync s : skus) {
            SkuList.Sku sku = new SkuList.Sku();
            sku.setSku(s.getSkuCode());
            sku.setName(s.getSkuName());
            sku.setBzskuCode("");
            sku.setBarcode(s.getBarCode());
            skuList.getSku().add(sku);
            // 将状态修改为已发送
            msgSKUSyncDao.updateStatusById(DefaultStatus.SENT.getValue(), s.getId());
            // 更新批次
            msgSKUSyncDao.updateBatchNoByID(batchNo, s.getId());

        }
        respXml = (String) MarshallerUtil.buildJaxb(skuList);
        return respXml;
    }

    /**
     * 确认接收商品信息
     */
    public void serviceCfSku(SkuListConfirm confirm) {
        if (confirm.getSku() != null && confirm.getSku().size() > 0) {
            if ("SKU".equals(confirm.getBatchType())) {// 销售 或 其他出库
                if ("0".equals(confirm.getStatus())) {
                    List<MsgSKUSync> skus = msgSKUSyncDao.findeMsgSKUSyncBybatchId(Long.valueOf(confirm.getBatchId()), DefaultStatus.SENT);
                    // 修改接收失败的单据 状态为 0
                    for (SkuListConfirm.Sku sku : confirm.getSku()) {
                        for (MsgSKUSync m : skus) {
                            if (sku.getSku().equals(m.getSkuCode())) {
                                msgSKUSyncDao.updateStatusById(DefaultStatus.CANCELED.getValue(), m.getId());
                            }
                        }
                    }
                    msgOutboundOrderDao.flush();
                }
                // 查找 接收成功的单据
                msgSKUSyncDao.updateStatusByBatchNo(DefaultStatus.FINISHED.getValue(), Long.valueOf(confirm.getBatchId()));
            }
        }
    }
}
