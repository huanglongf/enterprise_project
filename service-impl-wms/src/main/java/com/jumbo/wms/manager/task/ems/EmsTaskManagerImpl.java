package com.jumbo.wms.manager.task.ems;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import loxia.dao.support.BeanPropertyRowMapperExt;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import cn.baozun.model.top.TopCainiaoCntmsLogisticsOrderConsignResponse;
import cn.baozun.model.top.TopCainiaoCntmsLogisticsOrderConsignResquest;
import cn.baozun.model.top.TopCainiaoCntmsLogisticsOrderConsignResquest.CnTmsLogisticsOrderDeliverRequirements;
import cn.baozun.model.top.TopCainiaoCntmsLogisticsOrderConsignResquest.CnTmsLogisticsOrderGotService;
import cn.baozun.model.top.TopCainiaoCntmsLogisticsOrderConsignResquest.CnTmsLogisticsOrderItem;
import cn.baozun.model.top.TopCainiaoCntmsLogisticsOrderConsignResquest.CnTmsLogisticsOrderReceiverInfo;
import cn.baozun.model.top.TopCainiaoCntmsLogisticsOrderConsignResquest.CnTmsLogisticsOrderSenderinfo;
import cn.baozun.rmi.top.TopRmiService;

import com.baozun.scm.primservice.logistics.manager.OrderConfirmContentManager;
import com.baozun.scm.primservice.logistics.model.OrderConfirmContent;
import com.baozun.scm.primservice.logistics.model.OrderConfirmResponse;
import com.jumbo.dao.authorization.OperationUnitDao;
import com.jumbo.dao.baseinfo.WarehouseDao;
import com.jumbo.dao.system.ChooseOptionDao;
import com.jumbo.dao.warehouse.BiChannelDao;
import com.jumbo.dao.warehouse.CNPConfirmOrderQueueDao;
import com.jumbo.dao.warehouse.EMSConfirmOrderQueueDao;
import com.jumbo.dao.warehouse.EMSConfirmOrderQueueLogDao;
import com.jumbo.dao.warehouse.OffLineTransPackageDao;
import com.jumbo.dao.warehouse.PackageInfoDao;
import com.jumbo.dao.warehouse.StaLineDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.dao.warehouse.TransEmsInfoDao;
import com.jumbo.dao.warehouse.WhTransProvideNoDao;
import com.jumbo.dao.warehouse.WmsInvoiceOrderDao;
import com.jumbo.util.StringUtil;
import com.jumbo.webservice.ems.EmsServiceClient;
import com.jumbo.webservice.ems.EmsServiceClient2;
import com.jumbo.webservice.ems.command.EmsOrderData;
import com.jumbo.webservice.ems.command.EmsOrderDatas;
import com.jumbo.webservice.ems.command.EmsOrderPostInfo;
import com.jumbo.webservice.ems.command.EmsOrderUpdate;
import com.jumbo.webservice.ems.command.EmsOrderUpdateResponse;
import com.jumbo.webservice.ems.command.New.Address;
import com.jumbo.webservice.ems.command.New.EmsSystemParam;
import com.jumbo.webservice.ems.command.New.Item;
import com.jumbo.webservice.ems.command.New.NewEmsOrderPostInfo;
import com.jumbo.webservice.ems.command.New.NewEmsOrderUpdateResponse;
import com.jumbo.webservice.ems.command.New.NewWaybill;
import com.jumbo.wms.Constants;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.expressDelivery.ChannelInsuranceManager;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.baseinfo.TransEmsInfo;
import com.jumbo.wms.model.baseinfo.Transportator;
import com.jumbo.wms.model.baseinfo.Warehouse;
import com.jumbo.wms.model.system.ChooseOption;
import com.jumbo.wms.model.warehouse.CNPConfirmOrderQueue;
import com.jumbo.wms.model.warehouse.EMSConfirmOrderQueue;
import com.jumbo.wms.model.warehouse.EMSConfirmOrderQueueLog;
import com.jumbo.wms.model.warehouse.StaDeliveryInfo;
import com.jumbo.wms.model.warehouse.StaLine;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.TransDeliveryType;
import com.jumbo.wms.model.warehouse.TransPackageCommand;
import com.jumbo.wms.model.warehouse.WhTransProvideNo;
import com.jumbo.wms.model.warehouse.WmsInvoiceOrderCommand;

@Service("emsTaskManager")
@Transactional
public class EmsTaskManagerImpl extends BaseManagerImpl implements EmsTaskManager {
    /**
     * 
     */
    private static final long serialVersionUID = -977497688058665154L;

    @Autowired
    private EMSConfirmOrderQueueLogDao emsConfirmOrderQueueLogDao;
    @Autowired
    private EMSConfirmOrderQueueDao emsConfirmOrderQueueDao;
    @Autowired
    private WhTransProvideNoDao transProvideNoDao;
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private BiChannelDao channelDao;
    @Autowired
    private ChannelInsuranceManager channelInsuranceManager;

    @Autowired
    private ChooseOptionDao chooseOptionDao;
    @Autowired
    private TransEmsInfoDao transEmsInfoDao;
    /*
     * @Autowired private TransEmsInfoNewDao transEmsInfoNewDao;
     */
    @Autowired
    private WmsInvoiceOrderDao wmsInvoiceOrderDao;
    @Autowired
    private CNPConfirmOrderQueueDao cNPConfirmOrderQueueDao;
    @Autowired
    private WarehouseDao warehouseDao;
    @Autowired
    private StaLineDao staLineDao;
    @Autowired
    private PackageInfoDao packageInfoDao;
    @Autowired
    private TopRmiService topRmiService;
    @Autowired
    OrderConfirmContentManager confirmContentManager;
    @Autowired
    private OffLineTransPackageDao offLineTransPackageDao;
    @Autowired
    private OperationUnitDao operationUnitDao;



    @Override
    public void exeEmsConfirmOrder(Long qId) {
        EmsOrderPostInfo info = new EmsOrderPostInfo();
        EmsOrderData data = new EmsOrderData();
        EMSConfirmOrderQueue q = emsConfirmOrderQueueDao.getByPrimaryKey(qId);
        if (q.getOrder_flag() != null && q.getOrder_flag() == 1) {// 1标识传递给EMS的订单类型为发票
            data.setBigAccountDataId(q.getStaCode());
            data.setBillno(q.getBillNo());
            List<WmsInvoiceOrderCommand> wioList = wmsInvoiceOrderDao.findWmsInvoiceOrderIsExist(q.getStaCode(), new BeanPropertyRowMapperExt<WmsInvoiceOrderCommand>(WmsInvoiceOrderCommand.class));
            if (wioList.size() > 0) {
                WmsInvoiceOrderCommand wioc = wioList.get(0);
                BiChannel bc = channelDao.getByCode(wioc.getOwner());
                data.setScontactor(bc.getName());
                data.setScustMobile(bc.getTelephone());
                data.setScustTelplus(bc.getTelephone());
                data.setScustPost(bc.getZipcode());
                data.setScustAddr(bc.getAddress());
                // data.setScustComp(comp.getName());
                data.setTcontactor(wioc.getReceiver());
                data.setTcustMobile("");
                data.setTcustTelplus("18800000000");// 防止信息泄漏 不提供正确的联系方式
                data.setTcustPost(wioc.getZipcode());

                data.setCargoDesc(EmsServiceClient.CARGO_DESC_BZ + "   #" + wioc.getPgIndex());
                data.setTcustAddr(wioc.getAddress());
                data.setTcustProvince(wioc.getProvince());
                data.setTcustCity(wioc.getCity());
                data.setTcustCounty(wioc.getDistrict());
                data.setBlank2("全程陆运");
                data.setBusinessType(EmsServiceClient.BUSINESS_TYPE_NORMAL);
                data.setProductCode("4310101991");
                data.setInsure("0");
                data.setFee("0");

                data.setBlank1("");
                data.setRemark("");
                data.setBigAccountDataId(wioc.getOrderCode());
                // data.setCustomerDn(sta.getRefSlipCode());
                data.setWeight(StringUtil.isEmpty(q.getWeight()) ? "0.00" : q.getWeight());
                data.setLength(StringUtil.isEmpty(q.getLength()) ? "0.00" : q.getLength());

                TransEmsInfo emsInfo = transEmsInfoDao.findByCmp(true, 0);
                if (emsInfo != null) {
                    info.setAppKey(emsInfo.getAppKey());
                }
                info.setPassWord(q.getPassWord());
                info.setSysAccount(q.getSysAccount());
                info.setPrintKind(EmsServiceClient.PRINT_KIND_RM); // 默认为热敏打印
                EmsOrderDatas datas = new EmsOrderDatas();
                info.setPrintDatas(datas);
                datas.setPrintData(new ArrayList<EmsOrderData>());
                datas.getPrintData().add(data);
            }
        } else {
            StockTransApplication sta = staDao.getByCode(q.getStaCode());
            StaDeliveryInfo sd = sta.getStaDeliveryInfo();
            BiChannel bc = channelDao.getByCode(sta.getOwner());
            if (bc.getIsEmsConfirm() != null && bc.getIsEmsConfirm()) {
                OrderConfirmContent orderConfirmContent = new OrderConfirmContent();
                orderConfirmContent.setOrdercode(sta.getCode());
                orderConfirmContent.setTradeid(sta.getRefSlipCode());
                orderConfirmContent.setWhcode(sta.getMainWarehouse().getCode());
                orderConfirmContent.setOwnercode(sta.getOwner());
                orderConfirmContent.setLpcode(sta.getStaDeliveryInfo().getLpCode());
                orderConfirmContent.setTrackingno(q.getSysAccount());
                orderConfirmContent.setType(1);
                orderConfirmContent.setExttransorderid(q.getStaCode());
                if (q.getWeight() != null && !q.getWeight().equals("")) {
                    orderConfirmContent.setWeight(new BigDecimal(q.getWeight()));
                } else {
                    orderConfirmContent.setWeight(new BigDecimal(0));
                }
                OrderConfirmResponse confirmResponse = confirmContentManager.mialOrderComfirm(orderConfirmContent, "WMS_NIKE");
                if (confirmResponse != null && confirmResponse.getStatus() == 1) {
                    EMSConfirmOrderQueueLog lg = new EMSConfirmOrderQueueLog();
                    lg.setBillNo(q.getBillNo());
                    lg.setCreateTime(q.getCreateTime());
                    lg.setFinishTime(new Date());
                    lg.setPassWord(q.getPassWord());
                    lg.setStaCode(q.getStaCode());
                    lg.setSysAccount(q.getSysAccount());
                    lg.setWeight(q.getWeight());
                    emsConfirmOrderQueueLogDao.save(lg);
                    emsConfirmOrderQueueDao.deleteByPrimaryKey(q.getId());
                    return;
                } else {
                    log.debug("EMS ERROR : " + (confirmResponse != null ? confirmResponse.getMsg() : "EMS interface return null!"));
                    EMSConfirmOrderQueue qo = emsConfirmOrderQueueDao.getByPrimaryKey(q.getId());
                    qo.setExeCount(qo.getExeCount() + 1);
                }


            } else {
                OperationUnit comp = sta.getMainWarehouse().getParentUnit().getParentUnit();
                DecimalFormat df = new DecimalFormat("0.00");

                data.setBigAccountDataId(sta.getCode());
                data.setBillno(q.getBillNo());
                data.setScontactor(bc.getName());
                data.setScustMobile(bc.getTelephone());
                data.setScustTelplus(bc.getTelephone());
                data.setScustPost(bc.getZipcode());
                data.setScustAddr(bc.getAddress());
                data.setScustComp(comp.getName());
                data.setTcontactor(sd.getReceiver());
                data.setTcustMobile("");
                data.setTcustTelplus("18800000000");// 防止信息泄漏 不提供正确的联系方式
                data.setTcustPost(sd.getZipcode());
                data.setCargoDesc(EmsServiceClient.CARGO_DESC_BZ + "   #" + sta.getIndex());
                data.setTcustAddr(sd.getAddress());
                data.setTcustProvince(sd.getProvince());
                data.setTcustCity(sd.getCity());
                data.setTcustCounty(sd.getDistrict());
                if (sta.getDeliveryType() != null && TransDeliveryType.ORDINARY.equals(sta.getDeliveryType())) {
                    data.setBlank2("全程陆运");
                }
                BigDecimal total = sta.getTotalActual() == null ? BigDecimal.ZERO : sta.getTotalActual();
                // if (trans.getIsSupportCod() != null && trans.getIsSupportCod()) {
                if (sd.getIsCod() != null && sd.getIsCod()) {
                    data.setBlank2(sta.getRefSlipCode() == null ? "" : sta.getRefSlipCode());
                    if (sd.getTransferFee() != null) {
                        total = total.add(sd.getTransferFee());
                    }
                    data.setBusinessType(EmsServiceClient.BUSINESS_TYPE_COD);
                    data.setProductCode("4310101911");
                    DecimalFormat feeDf = new DecimalFormat("0");
                    data.setFee(feeDf.format(total));
                    String feeUppercase = StringUtil.formatIntToChinaBigNumStr(Integer.parseInt(feeDf.format(total)));
                    data.setFeeUppercase(feeUppercase);
                    BigDecimal inSure = channelInsuranceManager.getInsurance(sta.getOwner(), sd.getInsuranceAmount());
                    data.setInsure(inSure == null ? df.format(new BigDecimal(0)) : df.format(inSure));

                } else {
                    data.setBusinessType(EmsServiceClient.BUSINESS_TYPE_NORMAL);
                    data.setProductCode("4310101991");
                    data.setInsure("0");
                    data.setFee("0");
                }
                data.setBlank1(sta.getTotalActual() == null ? "" : df.format(sta.getTotalActual()));
                data.setRemark(sta.getTotalActual() == null ? "" : df.format(sta.getTotalActual()));
                // String memo = sta.getMemo() == null ? "" : sta.getMemo();
                // int index = memo.length() > 30 ? 30 : memo.length();
                // memo = index < 0 ? "" : memo.substring(0, index) + (index > 30 ? "...." : "");
                // data.setRemark(sta.getRefSlipCode() + " #" + sta.getIndex() + " " + memo);
                data.setBigAccountDataId(sta.getCode());
                data.setCustomerDn(sta.getRefSlipCode());
                data.setWeight(StringUtil.isEmpty(q.getWeight()) ? "0.00" : q.getWeight());
                data.setLength(StringUtil.isEmpty(q.getLength()) ? "0.00" : q.getLength());

                TransEmsInfo emsInfo = transEmsInfoDao.findByCmp(true, 0);
                if (emsInfo != null) {
                    info.setAppKey(emsInfo.getAppKey());
                }
                info.setPassWord(q.getPassWord());
                info.setSysAccount(q.getSysAccount());
                info.setPrintKind(EmsServiceClient.PRINT_KIND_RM); // 默认为热敏打印

                EmsOrderDatas datas = new EmsOrderDatas();
                info.setPrintDatas(datas);
                datas.setPrintData(new ArrayList<EmsOrderData>());
                datas.getPrintData().add(data);
            }
            try {
                EmsOrderUpdateResponse rs = EmsServiceClient.sendEmsOrderDatas(info);
                if (rs != null && rs.getResult() != null && EmsOrderUpdateResponse.RESULT_SUCCESS.equals(rs.getResult().trim())) {
                    // sta.getStaDeliveryInfo().setExtTransOrderId(sta.getCode());
                    EMSConfirmOrderQueueLog lg = new EMSConfirmOrderQueueLog();
                    lg.setBillNo(q.getBillNo());
                    lg.setCreateTime(q.getCreateTime());
                    lg.setFinishTime(new Date());
                    lg.setPassWord(q.getPassWord());
                    lg.setStaCode(q.getStaCode());
                    lg.setSysAccount(q.getSysAccount());
                    lg.setWeight(q.getWeight());
                    emsConfirmOrderQueueLogDao.save(lg);
                    emsConfirmOrderQueueDao.deleteByPrimaryKey(q.getId());
                } else {
                    log.debug("EMS ERROR : " + (rs != null ? rs.getErrorDesc() : "EMS interface return null!"));
                    EMSConfirmOrderQueue qo = emsConfirmOrderQueueDao.getByPrimaryKey(q.getId());
                    qo.setExeCount(qo.getExeCount() + 1);
                }
            } catch (Exception e) {
                log.error("", e);
                throw new BusinessException(ErrorCode.SYSTEM_ERROR);
            }
        }
    }

    /**
     * ems 新的出库
     */
    @Override
    public void exeEmsConfirmOrderNew(Long qId) {
        TransEmsInfo emsInfo = null;
        List<Item> items = new ArrayList<Item>();
        List<NewWaybill> waybills = new ArrayList<NewWaybill>();
        NewEmsOrderPostInfo info = new NewEmsOrderPostInfo();
        NewWaybill bill = new NewWaybill();
        Item item = new Item();
        Address sender = new Address();// 寄件人信息
        Address receiver = new Address();// 收件人信息
        EMSConfirmOrderQueue q = emsConfirmOrderQueueDao.getByPrimaryKey(qId);
        if (q.getOrder_flag() != null && q.getOrder_flag() == 1) {// 1标识传递给EMS的订单类型为发票
            bill.setTxLogisticID(q.getStaCode());
            bill.setOrderNo(q.getBillNo());
            bill.setMailNum(q.getBillNo());// run 02 运单号
            List<WmsInvoiceOrderCommand> wioList = wmsInvoiceOrderDao.findWmsInvoiceOrderIsExist(q.getStaCode(), new BeanPropertyRowMapperExt<WmsInvoiceOrderCommand>(WmsInvoiceOrderCommand.class));
            if (wioList.size() > 0) {
                WmsInvoiceOrderCommand wioc = wioList.get(0);
                BiChannel bc = channelDao.getByCode(wioc.getOwner());
                // 封装寄件人
                // sender.setName(bc.getName());// 用户姓名
                // sender.setPostCode(bc.getZipcode());// 用户邮编
                // sender.setPhone(bc.getTelephone());// 用户电话，包括区号、电话号码及分机号，中间用“-”分隔；
                // sender.setMobile(bc.getTelephone());// 用户电话，包括区号、电话号码及分机号，中间用“-”分隔；
                // sender.setAddress(bc.getAddress());//
                sender.setName(bc.getName());// 用户姓名
                sender.setPostCode(bc.getZipcode());// 用户邮编
                sender.setPhone(bc.getTelephone());// 用户电话，包括区号、电话号码及分机号，中间用“-”分隔；
                sender.setMobile(bc.getTelephone());// 用户电话，包括区号、电话号码及分机号，中间用“-”分隔；
                sender.setAddress(bc.getAddress());//
                sender.setProv(bc.getAddress());// run 02 省
                sender.setCity(bc.getAddress());// run 02 市
                // 封装收件人
                // data.setCargoDesc(EmsServiceClient.CARGO_DESC_BZ + "   #" + wioc.getPgIndex());??
                receiver.setName(wioc.getReceiver());// 用户姓名
                receiver.setPostCode(wioc.getZipcode());// 用户邮编
                receiver.setPhone("");// 用户电话，包括区号、电话号码及分机号，中间用“-”分隔；
                receiver.setMobile("18800000000");// 用户电话，包括区号、电话号码及分机号，中间用“-”分隔；
                receiver.setProv(wioc.getProvince());// 用户所在省，使用国标全称
                receiver.setCity(wioc.getCity());// 用户所在市，使用国标全称
                receiver.setCounty(wioc.getDistrict());// 用户所在县（区），使用国标全称
                receiver.setAddress(wioc.getAddress());//
                // data.setBlank2("全程陆运");??
                bill.setOrderType("06");// 订单类型(1-普通订单)???
                // bill.setServiceType(4310101991L);// 产品代码，0-经济快递 1-标准快递??
                // bill.setInsuredAmount(0L);// 保价金额 ??
                // data.setFee("0");??
                // data.setBlank1("");
                // data.setRemark("");
                // data.setBigAccountDataId(wioc.getOrderCode());
                bill.setTxLogisticID(q.getStaCode());
                bill.setWeight(StringUtil.isEmpty(q.getWeight()) ? 0L : Long.valueOf((long) (Double.valueOf(q.getWeight()) * 1000)));// kg转g

                bill.setSender(sender);// 寄件人信息
                bill.setReceiver(receiver);// 收件人信息
                // 封装item
                item.setItemName("商品01");// 商品名称
                item.setNumber(0);// 数量
                item.setItemValue(0L);//
                items.add(item);
                bill.setItems(items);
                waybills.add(bill);
                info.setWaybills(waybills);
                emsInfo = transEmsInfoDao.findByAccount(false, q.getSysAccount(), 1);// 根据客户编码来获取
            }
        } else if (q.getType() != null && q.getType() == 1) {// 线下包裹定制
            boolean b = false;
            ChooseOption ch = chooseOptionDao.findByCategoryCodeAndKey("EMS_OUTBOUNT_IS", "1");
            if (ch == null || ch.getOptionValue() == null) {// guan
                b = false;
            } else {// kai
                b = true;
            }
            TransPackageCommand transPackageCommand = offLineTransPackageDao.getOneTransPackage2(q.getBillNo(), new BeanPropertyRowMapperExt<TransPackageCommand>(TransPackageCommand.class));
            if (b) {// 本地
                bill.setTxLogisticID(q.getStaCode());
                bill.setOrderNo(q.getBillNo());
                // 封装寄件人
                sender.setName(transPackageCommand.getSender());// 用户姓名
                sender.setPostCode("215000");// 用户邮编
                sender.setPhone(transPackageCommand.getSenderTel() == null ? "18800000000" : transPackageCommand.getSenderTel());
                sender.setMobile(transPackageCommand.getSenderTel() == null ? "18800000000" : transPackageCommand.getSenderTel());
                sender.setAddress(transPackageCommand.getSenderAddress());
                sender.setProv(transPackageCommand.getSenderAddress());
                sender.setCity(transPackageCommand.getSenderAddress());


                receiver.setName(transPackageCommand.getReceiver());// 用户姓名
                receiver.setPostCode("215000");// 用户邮编
                receiver.setPhone("");// 用户电话，包括区号、电话号码及分机号，中间用“-”分隔；
                receiver.setMobile("18800000000");// 用户电话，包括区号、电话号码及分机号，中间用“-”分隔；
                receiver.setProv(transPackageCommand.getReceiverProvince());// 用户所在省，使用国标全称
                receiver.setCity(transPackageCommand.getReceiverCity());// 用户所在市，使用国标全称
                receiver.setCounty(transPackageCommand.getReceiverArea());// 用户所在县（区），使用国标全称
                receiver.setAddress(transPackageCommand.getReceiverAddress());//
                bill.setOrderType("06");// 订单类型(1-普通订单)???
                bill.setTxLogisticID(q.getStaCode());
                bill.setOrderNo(q.getId().toString());
                bill.setMailNum(q.getBillNo());// 运单号

                bill.setWeight(StringUtil.isEmpty(q.getWeight()) ? 0L : Long.valueOf((long) (Double.valueOf(q.getWeight()) * 1000)));// kg转g

                bill.setSender(sender);// 寄件人信息
                bill.setReceiver(receiver);// 收件人信息
                // 封装item
                item.setItemName("商品01");// 商品名称
                item.setNumber(0);// 数量
                item.setItemValue(0L);//
                items.add(item);
                bill.setItems(items);
                waybills.add(bill);
                info.setWaybills(waybills);
                emsInfo = transEmsInfoDao.findByAccount(false, q.getSysAccount(), 1);// 根据客户编码来获取

            } else {// 快递服务
                Warehouse warehouse = warehouseDao.getByOuId(transPackageCommand.getOuId());
                OperationUnit u = operationUnitDao.getByPrimaryKey(transPackageCommand.getOuId());
                OrderConfirmContent orderConfirmContent = new OrderConfirmContent();
                orderConfirmContent.setOrdercode(transPackageCommand.getCode());
                // orderConfirmContent.setTradeid(sta.getRefSlipCode());
                orderConfirmContent.setWhcode(u.getCode());
                orderConfirmContent.setOwnercode(transPackageCommand.getCostCenterDetail());
                orderConfirmContent.setType(1);
                orderConfirmContent.setLpcode("EMS");
                orderConfirmContent.setTrackingno(q.getBillNo());
                orderConfirmContent.setExttransorderid(q.getStaCode());
                if (q.getWeight() != null && !q.getWeight().equals("")) {
                    orderConfirmContent.setWeight(new BigDecimal(q.getWeight()));
                } else {
                    orderConfirmContent.setWeight(new BigDecimal(0));
                }
                String source = "WMS3";

                OrderConfirmResponse confirmResponse = confirmContentManager.mialOrderComfirm(orderConfirmContent, source);
                log.info("", confirmResponse.getMsg() + "---" + confirmResponse.getStatus() + "---" + q.getStaCode());
                if (confirmResponse != null && confirmResponse.getStatus() == 1) {
                    EMSConfirmOrderQueueLog lg = new EMSConfirmOrderQueueLog();
                    lg.setBillNo(q.getBillNo());
                    lg.setCreateTime(q.getCreateTime());
                    lg.setFinishTime(new Date());
                    lg.setPassWord(q.getPassWord());

                    lg.setStaCode(q.getStaCode());
                    lg.setSysAccount(q.getSysAccount());
                    lg.setWeight(q.getWeight());
                    lg.setType(q.getType());
                    emsConfirmOrderQueueLogDao.save(lg);
                    emsConfirmOrderQueueDao.deleteByPrimaryKey(q.getId());
                    return;
                } else {
                    log.debug("EMS ERROR : " + (confirmResponse != null ? confirmResponse.getMsg() : "EMS interface return null!"));
                    EMSConfirmOrderQueue qo = emsConfirmOrderQueueDao.getByPrimaryKey(q.getId());
                    qo.setExeCount(qo.getExeCount() + 1);
                }
            }

        } else {
            StockTransApplication sta = staDao.getByCode(q.getStaCode());
            StaDeliveryInfo sd = sta.getStaDeliveryInfo();
            BiChannel bc = channelDao.getByCode(sta.getOwner());
            if (bc.getIsEmsConfirm() != null && bc.getIsEmsConfirm()) {
                OrderConfirmContent orderConfirmContent = new OrderConfirmContent();
                orderConfirmContent.setOrdercode(sta.getCode());
                orderConfirmContent.setTradeid(sta.getRefSlipCode());
                orderConfirmContent.setWhcode(sta.getMainWarehouse().getCode());
                orderConfirmContent.setOwnercode(sta.getOwner());
                orderConfirmContent.setType(1);
                orderConfirmContent.setLpcode(sta.getStaDeliveryInfo().getLpCode());
                orderConfirmContent.setTrackingno(q.getBillNo());
                orderConfirmContent.setExttransorderid(q.getStaCode());
                if (q.getWeight() != null && !q.getWeight().equals("")) {
                    orderConfirmContent.setWeight(new BigDecimal(q.getWeight()));
                } else {
                    orderConfirmContent.setWeight(new BigDecimal(0));
                }
                String source = "";
                if (bc.getIsPackingList() != null && bc.getIsPackingList()) {
                    source = "WMS_NIKE";
                } else {
                    source = "WMS3";
                }
                OrderConfirmResponse confirmResponse = confirmContentManager.mialOrderComfirm(orderConfirmContent, source);
                log.info("", confirmResponse.getMsg() + "---" + confirmResponse.getStatus() + "---" + q.getStaCode());
                if (confirmResponse != null && confirmResponse.getStatus() == 1) {
                    EMSConfirmOrderQueueLog lg = new EMSConfirmOrderQueueLog();
                    lg.setBillNo(q.getBillNo());
                    lg.setCreateTime(q.getCreateTime());
                    lg.setFinishTime(new Date());
                    lg.setPassWord(q.getPassWord());
                    
                    lg.setStaCode(q.getStaCode());
                    lg.setSysAccount(q.getSysAccount());
                    lg.setWeight(q.getWeight());
                    emsConfirmOrderQueueLogDao.save(lg);
                    emsConfirmOrderQueueDao.deleteByPrimaryKey(q.getId());
                    return;
                } else {
                    log.debug("EMS ERROR : " + (confirmResponse != null ? confirmResponse.getMsg() : "EMS interface return null!"));
                    EMSConfirmOrderQueue qo = emsConfirmOrderQueueDao.getByPrimaryKey(q.getId());
                    qo.setExeCount(qo.getExeCount() + 1);
                }


            } else {
                OperationUnit comp = sta.getMainWarehouse().getParentUnit().getParentUnit();
                // DecimalFormat df = new DecimalFormat("0.00");
                //
                bill.setTxLogisticID(q.getStaCode());
                bill.setOrderNo(q.getBillNo());
                // bill.setYpdjpayment(0);// 一票多件付费方式（1-集中主单计费 2-平均重量计费 3-分单免首重4-主分单单独计费） run 02


                // 封装寄件人
                sender.setName(bc.getName());// 用户姓名
                sender.setPostCode(bc.getZipcode());// 用户邮编
                sender.setPhone(bc.getTelephone());// 用户电话，包括区号、电话号码及分机号，中间用“-”分隔；
                sender.setMobile(bc.getTelephone());// 用户电话，包括区号、电话号码及分机号，中间用“-”分隔；
                sender.setAddress(bc.getAddress());//
                sender.setProv(bc.getAddress());// run 02 省
                sender.setCity(bc.getAddress());// run 02 市

                // data.setScustComp(comp.getName());??????
                // 封装收件人
                // data.setCargoDesc(EmsServiceClient.CARGO_DESC_BZ + "   #" + wioc.getPgIndex());??
                receiver.setName(sd.getReceiver());// 用户姓名
                // data.setTcustMobile("");?
                receiver.setPostCode(sd.getZipcode());// 用户邮编
                receiver.setPhone("");// 用户电话，包括区号、电话号码及分机号，中间用“-”分隔；
                receiver.setMobile("18800000000");// 用户电话，包括区号、电话号码及分机号，中间用“-”分隔；
                // data.setCargoDesc(EmsServiceClient.CARGO_DESC_BZ + "   #" + sta.getIndex());????
                receiver.setProv(sd.getProvince());// 用户所在省，使用国标全称
                receiver.setCity(sd.getCity());// 用户所在市，使用国标全称
                receiver.setCounty(sd.getDistrict());// 用户所在县（区），使用国标全称
                receiver.setAddress(sd.getAddress());//
                //
                if (sta.getDeliveryType() != null && TransDeliveryType.ORDINARY.equals(sta.getDeliveryType())) {
                    // data.setBlank2("全程陆运");???
                }
                BigDecimal total = sta.getTotalActual() == null ? BigDecimal.ZERO : sta.getTotalActual();
                if (sd.getIsCod() != null && sd.getIsCod()) {
                    // data.setBlank2(sta.getRefSlipCode() == null ? "" : sta.getRefSlipCode());??
                    if (sd.getTransferFee() != null) {
                        total = total.add(sd.getTransferFee());
                    }
                    bill.setOrderType("08");// 订单类型(1-普通订单)???
                    // bill.setServiceType(4310101991L);// 产品代码，0-经济快递 1-标准快递????????
                    bill.setReceiverPay(total.longValue() * 100);// 小写金额？？ 为什么没有小数点后
                    bill.setCollectionMoney(total.longValue() * 100);// 大写金额
                    BigDecimal inSure = channelInsuranceManager.getInsurance(sta.getOwner(), sd.getInsuranceAmount());
                    if (inSure == null) {
                        inSure = BigDecimal.ZERO;
                    }
                    bill.setInsuredAmount(inSure.longValue() * 100);// 保价金额 ?? 小数点后
                } else {
                    bill.setOrderType("06");// 订单类型(1-普通订单)???
                    // bill.setServiceType(4310101991L);// 产品代码，0-经济快递 1-标准快递???????
                    // bill.setInsuredAmount(0L);// 保价金额 ?? 小数点后
                    // bill.setReceiverPay(0L);// 小写金额？？ 为什么没有小数点后
                    // data.setInsure("0");
                    // data.setFee("0");
                }
                // data.setBlank1(sta.getTotalActual() == null ? "" :
                // df.format(sta.getTotalActual()));?
                // data.setRemark(sta.getTotalActual() == null ? "" :
                // df.format(sta.getTotalActual()));？

                bill.setTxLogisticID(q.getStaCode());
                bill.setOrderNo(sta.getRefSlipCode());
                bill.setMailNum(q.getBillNo());// run 02 运单号
                // bill.setSubMails("");// 子运单号
                bill.setWeight(StringUtil.isEmpty(q.getWeight()) ? 0L : Long.valueOf((long) (Double.valueOf(q.getWeight()) * 1000)));// kg转g
                // data.setLength(StringUtil.isEmpty(q.getLength()) ? "0.00" : q.getLength());?没有长度
                bill.setSender(sender);// 寄件人信息
                bill.setReceiver(receiver);// 收件人信息
                // 封装item
                item.setItemName("商品01");// 商品名称
                item.setNumber(0);// 数量
                item.setItemValue(0L);// ??
                items.add(item);
                bill.setItems(items);
                waybills.add(bill);
                info.setWaybills(waybills);
                emsInfo = transEmsInfoDao.findByAccount(false, q.getSysAccount(), 1);// 根据客户编码来获取
            }
        }
        try {
            Map<String, String> params = new HashMap<String, String>();
            JSONObject json = JSONObject.fromObject(info);// 将java对象转换为json对象
            String str = json.toString();// 将json对象转换为字符串
            // System.out.println(str);
            params.put("waybill", str);
            params.put("size", "1");
            EmsSystemParam en = EmsServiceClient2.initEmsSystemParam(null, null, "V3.01", "ems.inland.waybill.create.normal", null, "json", emsInfo.getKey(), "UTF-8", emsInfo.getAuthorization(), emsInfo.getSecret());
            NewEmsOrderUpdateResponse re = EmsServiceClient2.sendEmsOrderDatas("05", en, params, EMS_NEW_URL);
            if (re != null && "T".equals(re.getSuccess())) {
                EMSConfirmOrderQueueLog lg = new EMSConfirmOrderQueueLog();
                lg.setBillNo(q.getBillNo());
                lg.setCreateTime(q.getCreateTime());
                lg.setFinishTime(new Date());
                lg.setPassWord(q.getPassWord());
                lg.setStaCode(q.getStaCode());
                lg.setSysAccount(q.getSysAccount());
                lg.setWeight(q.getWeight());
                lg.setType(q.getType());
                emsConfirmOrderQueueLogDao.save(lg);
                emsConfirmOrderQueueDao.deleteByPrimaryKey(q.getId());
            } else {
                EMSConfirmOrderQueue qo = emsConfirmOrderQueueDao.getByPrimaryKey(q.getId());
                qo.setExeCount(qo.getExeCount() + 1);
            }
        } catch (BusinessException e){
            throw new BusinessException(e);
        } catch (Exception e) {
            log.error("", e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }


    }


    @Override
    public void saveEmsNo(String transNo, boolean isCod, String account) {
        WhTransProvideNo bean = new WhTransProvideNo();
        bean.setIsCod(isCod);
        bean.setLpcode(Transportator.EMS);
        bean.setTransno(transNo);
        bean.setEmsAccount(account);
        transProvideNoDao.save(bean);
    }


    public void updateEmsConfirmOrder(EMSConfirmOrderQueue q) {
        EmsOrderUpdate emsOu = new EmsOrderUpdate();
        emsOu.setBillNo(q.getBillNo());
        emsOu.setPassWord(q.getPassWord());
        emsOu.setSysAccount(q.getSysAccount());
        emsOu.setWeight(q.getWeight());
        EmsOrderUpdateResponse rs = EmsServiceClient.updateEmsOrder(emsOu);
        if (rs != null && EmsOrderUpdateResponse.RESULT_SUCCESS.equals(rs.getResult())) {
            EMSConfirmOrderQueueLog lg = new EMSConfirmOrderQueueLog();
            lg.setBillNo(q.getBillNo());
            lg.setCreateTime(q.getCreateTime());
            lg.setFinishTime(new Date());
            lg.setPassWord(q.getPassWord());
            lg.setStaCode(q.getStaCode());
            lg.setSysAccount(q.getSysAccount());
            lg.setWeight(q.getWeight());
            emsConfirmOrderQueueLogDao.save(lg);
            emsConfirmOrderQueueDao.deleteByPrimaryKey(q.getId());
        } else {
            EMSConfirmOrderQueue qo = emsConfirmOrderQueueDao.getByPrimaryKey(q.getId());
            qo.setExeCount(qo.getExeCount() + 1);
        }
    }

    /**
     * EMS反馈
     */
    public List<EMSConfirmOrderQueue> findExtOrder() {
        return emsConfirmOrderQueueDao.findExtOrder(Constants.SF_QUEUE_TYP_COUNT);
    }

    /**
     * 反馈优化
     */
    public List<Long> findExtOrderIdSeo() {
        return emsConfirmOrderQueueDao.findExtOrderIdSeo(Constants.SF_QUEUE_TYP_COUNT, new SingleColumnRowMapper<Long>(Long.class));
    }

    public List<Long> findCnpOrderIdSeo() {
        return cNPConfirmOrderQueueDao.findExtOrderIdSeo(Constants.SF_QUEUE_TYP_COUNT, new SingleColumnRowMapper<Long>(Long.class));
    }

    public void exeCnpConfirmOrder(Long qId) {
        CNPConfirmOrderQueue q = cNPConfirmOrderQueueDao.getByPrimaryKey(qId);
        try {
            StockTransApplication sta = staDao.getByPrimaryKey(q.getStaId());
            StaDeliveryInfo sd = sta.getStaDeliveryInfo();
            Warehouse wh = warehouseDao.getByOuId(sta.getMainWarehouse().getId());
            BiChannel channel = channelDao.getByCode(sta.getOwner());
            TopCainiaoCntmsLogisticsOrderConsignResquest request = new TopCainiaoCntmsLogisticsOrderConsignResquest();
            request.setOrderCode(sta.getCode());
            String tradeId = sta.getSlipCode1().replaceAll("-", "");
            request.setWmsShopId(channel.getId());
            request.setTradeId(tradeId);// 平台订单号
            request.setOrderSource(sta.getOrderSourcePlatform());// 来源渠道
            CnTmsLogisticsOrderReceiverInfo recInfo = new CnTmsLogisticsOrderReceiverInfo();
            recInfo.setReceiverProvince(sd.getProvince());
            recInfo.setReceiverCity(sd.getCity());
            recInfo.setReceiverArea(sd.getDistrict());
            recInfo.setReceiverAddress(sd.getAddress());
            recInfo.setReceiverName(sd.getReceiver());
            if (StringUtils.hasLength(sd.getTelephone())) {
                recInfo.setReceiverPhone(sd.getTelephone());
            } else {
                recInfo.setReceiverMobile((sd.getMobile()));
            }
            request.setReceiverInfo(recInfo); // 收货人信息
            CnTmsLogisticsOrderSenderinfo sendInfo = new CnTmsLogisticsOrderSenderinfo();
            sendInfo.setSenderProvince(wh.getProvince());
            sendInfo.setSenderCity(wh.getCity());
            sendInfo.setSenderArea(wh.getDistrict());
            sendInfo.setSenderAddress(wh.getAddress());
            sendInfo.setSenderName(wh.getPic());
            if (StringUtils.hasLength(wh.getPicContact())) {
                sendInfo.setSenderPhone((wh.getPicContact()));
            } else {
                sendInfo.setSenderPhone((wh.getPhone()));
            }
            request.setSenderInfo(sendInfo); // 发货人信息
            List<StaLine> staLines = staLineDao.findByStaId(sta.getId());
            List<CnTmsLogisticsOrderItem> itemList = new ArrayList<CnTmsLogisticsOrderItem>();
            for (StaLine line : staLines) {
                CnTmsLogisticsOrderItem item = new CnTmsLogisticsOrderItem();
                item.setOrderItemId(tradeId);
                item.setSubTradeId(tradeId);
                item.setQuantity(line.getQuantity());
                item.setItemName(line.getSku().getName());
                // item.setItemQty(line.getQuantity());
                BigDecimal unprice = line.getUnitPrice();
                if (unprice != null) {
                    BigDecimal b1 = new BigDecimal(100);
                    long b2 = unprice.multiply(b1).longValue();
                    item.setItemPrice(b2);
                } else {
                    item.setItemPrice(0l);
                }
                itemList.add(item);
            }
            request.setMailNo(q.getMailNo());
            request.setTmsCode("DISTRIBUTOR_474821");
            // request.setTmsCode(q.getTmsCode());
            request.setPackageNo(q.getPackageNo().longValue());
            request.setPackageCount(q.getPackageCount().longValue());
            request.setItems(itemList); // 商品信息
            request.setPickUpType(2l); // 宝尊默认传，2，菜鸟揽货
            CnTmsLogisticsOrderGotService go = new CnTmsLogisticsOrderGotService();
            go.setGotDate("20880808");// 宝尊默认传
            go.setGotRange("09:00-10:00");
            request.setTmsGotService(go); // 菜鸟上门揽货
            request.setSolutionsCode("5000000011425"); // 菜鸟提供唯一值，宝尊默认传值即可
            CnTmsLogisticsOrderDeliverRequirements deRe = new CnTmsLogisticsOrderDeliverRequirements();
            deRe.setDeliveryType("PTPS");
            deRe.setScheduleEnd("12:00");
            deRe.setScheduleStart("18:00");
            deRe.setScheduleType("1");
            deRe.setScheduleDay("2099-08-15");
            request.setDeliverRequirements(deRe); // 配送要求 默认值
            if (StringUtils.hasLength(q.getWeight())) {
                request.setPackageWeight(Long.parseLong(q.getWeight()));
            } else {
                request.setPackageWeight(100l);
            }
            TopCainiaoCntmsLogisticsOrderConsignResponse response = topRmiService.cainiaoCntmsLogisticsOrderConsign(request);
            if (response != null) {
                if (StringUtils.hasLength(response.getLogisticsOrderCode())) {
                    // 删除信息，记录日志
                    q.setLogisticsOrderCode(response.getLogisticsOrderCode());
                    cNPConfirmOrderQueueDao.save(q);
                    cNPConfirmOrderQueueDao.flush();
                    cNPConfirmOrderQueueDao.insertLogByQueue(qId);
                    cNPConfirmOrderQueueDao.delete(q);;
                    log.info(qId + " getLogisticsOrderCode" + response.getLogisticsOrderCode());
                } else {
                    q.setExeCount(q.getExeCount() + 1);
                    log.info(qId + " getLogisticsOrderCode" + response.getErrorCode() + "--" + response.getErrorMsg());
                }
            } else {
                q.setExeCount(q.getExeCount() + 1);
                log.error(qId + " response  is null ");
            }
        } catch (Exception e) {
            q.setExeCount(q.getExeCount() + 1);
            log.error(qId + "exeCnpConfirmOrder error: ", e);
        }
    }

    @Override
    public Boolean getChooseEmsHttps() {
        Boolean bEms = true;// 用老的
        ChooseOption op = chooseOptionDao.findByCategoryCodeAndKey(EmsServiceClient2.EMS_NEW_SWITCH_CODE, EmsServiceClient2.EMS_NEW_SWITCH_KEY);
        if (op != null && op.getOptionValue() != null) {
            bEms = false;// 用新的
        }
        return bEms;
    }

}
