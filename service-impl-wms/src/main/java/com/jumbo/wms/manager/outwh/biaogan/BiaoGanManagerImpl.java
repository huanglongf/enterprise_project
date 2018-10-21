package com.jumbo.wms.manager.outwh.biaogan;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import loxia.dao.support.BeanPropertyRowMapperExt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.dao.baseinfo.WarehouseDao;
import com.jumbo.dao.vmi.warehouse.MsgOutboundOrderDao;
import com.jumbo.dao.vmi.warehouse.MsgOutboundOrderLineDao;
import com.jumbo.dao.vmi.warehouse.MsgOutboundReturnDao;
import com.jumbo.dao.vmi.warehouse.MsgOutboundReturnLineDao;
import com.jumbo.dao.vmi.warehouse.MsgRtnOutboundDao;
import com.jumbo.dao.vmi.warehouse.MsgRtnOutboundLineDao;
import com.jumbo.dao.vmi.warehouse.MsgSKUSyncDao;
import com.jumbo.dao.warehouse.BiChannelDao;
import com.jumbo.mq.MarshallerUtil;
import com.jumbo.webservice.biaogan.clientNew2.PushExpressInfoPortTypeClientNew2;
import com.jumbo.webservice.biaogan.command.InOutBoundResponse;
import com.jumbo.webservice.biaogan.command.InvoiceCommand;
import com.jumbo.webservice.biaogan.command.InvoicesCommand;
import com.jumbo.webservice.biaogan.command.InvoicesInfoCommand;
import com.jumbo.webservice.biaogan.command.InvoicesResponseCommand;
import com.jumbo.webservice.biaogan.command.Item;
import com.jumbo.webservice.biaogan.command.RequestOrderInfo;
import com.jumbo.webservice.biaogan.command.RequestOrderListCommand;
import com.jumbo.webservice.biaogan.command.RtnOutBoundCommand;
import com.jumbo.webservice.biaogan.command.RtnOutBoundLineCommand;
import com.jumbo.webservice.biaogan.command.SkuSyncCommand;
import com.jumbo.webservice.biaogan.command.SkuSyncList;
import com.jumbo.wms.Constants;
import com.jumbo.wms.manager.outwh.BaseOutWarehouseManager;
import com.jumbo.wms.model.DefaultStatus;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.baseinfo.Warehouse;
import com.jumbo.wms.model.vmi.warehouse.MsgInvoice;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrderCommand;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrderLineCommand;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnOutbound;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnOutboundLine;
import com.jumbo.wms.model.vmi.warehouse.MsgSKUSync;

/**
 * 
 * @author sjk
 * 
 */
@Transactional
@Service("biaoGanManager")
public class BiaoGanManagerImpl implements BiaoGanManager {
    protected static final Logger log = LoggerFactory.getLogger(BiaoGanManagerImpl.class);
    /**
	 * 
	 */
    private static final long serialVersionUID = 6911567821851120849L;
    @Autowired
    private BiChannelDao companyShopDao;
    @Autowired
    private MsgOutboundOrderLineDao msgOutboundOrderLineDao;
    @Autowired
    private WarehouseDao warehouseDao;
    @Autowired
    private BaseOutWarehouseManager baseOutWarehouseManager;
    @Autowired
    private MsgOutboundOrderDao msgOutboundOrderDao;
    @Autowired
    private MsgSKUSyncDao msgSKUSyncDao;
    @Autowired
    private MsgRtnOutboundLineDao msgRtnOutBoundLineDao;
    @Autowired
    private MsgRtnOutboundDao msgRtnOutBoundDao;
    @Autowired
    private MsgOutboundReturnLineDao msgOutboundReturnLineDao;
    @Autowired
    private MsgOutboundReturnDao msgOutboundReturnDao;

    /**
     * 保存每天销售出库反馈未记录单据信息
     * 
     * @param order
     * @param sdf
     */
    public void outBoundToday(RtnOutBoundCommand order, SimpleDateFormat sdf) {
        MsgRtnOutbound msg = msgRtnOutBoundDao.findByStaCode(order.getOrderNo());
        if (msg == null) {
            Date date = new Date();
            msg = new MsgRtnOutbound();
            msg.setStaCode(order.getOrderNo());
            msg.setTrackName(order.getCarrierID());
            msg.setLpCode(order.getCarrierID());
            msg.setStatus(DefaultStatus.CREATED);
            msg.setCreateTime(date);
            try {
                msg.setOutboundTime(order.getShipTime() != null ? sdf.parse(order.getShipTime()) : date);
            } catch (ParseException e) {
                msg.setOutboundTime(date);
                log.error("", e);
            }
            msg.setSource(Constants.VIM_WH_SOURCE_BIAOGAN);
            msg.setTrackingNo(order.getShipNo());
            msg.setWeight(order.getWeight() != null ? new BigDecimal(order.getWeight()) : null);
            msgRtnOutBoundDao.save(msg);
            for (RtnOutBoundLineCommand line : order.getSend().getSku()) {
                MsgRtnOutboundLine msgLine = new MsgRtnOutboundLine();
                msgLine.setMsgOutbound(msg);
                msgLine.setQty(Long.valueOf(line.getSkuNum()));
                msgLine.setSkuCode(line.getSkuCode());
                msgRtnOutBoundLineDao.save(msgLine);
            }
        }
    }

    /**
     * 调用仓库webservice接口，发送SKU同步信息至仓库
     */
    public void singleSkuToWms(List<MsgSKUSync> skus) {

        List<SkuSyncCommand> skuCommandList = new ArrayList<SkuSyncCommand>();
        SkuSyncList skuList = new SkuSyncList();
        String xml = "";
        for (int i = 0; i < skus.size(); i++) {
            MsgSKUSync sku = skus.get(i);
            SkuSyncCommand s = new SkuSyncCommand();
            s.setALTERNATESKU1(sku.getBarCode());
            s.setALTERNATESKU2(sku.getBarCode2() == null ? "" : sku.getBarCode2());
            s.setDesc("");
            s.setName(sku.getSkuName());
            s.setSkucode(sku.getSkuCode());
            s.setBrand(sku.getBrandName());
            skuCommandList.add(s);
        }
        skuList.setSkus(skuCommandList);
        xml = MarshallerUtil.buildJaxb(skuList);
        // 发送商品信息到仓库
        InOutBoundResponse resp = PushExpressInfoPortTypeClientNew2.singleSkuToWms(xml);
        if ("true".equals(resp.getSuccess())) {
            for (int i = 0; i < skus.size(); i++) {
                msgSKUSyncDao.updateStatusById(DefaultStatus.FINISHED.getValue(), skus.get(i).getId());
            }
        } else if ("false".equals(resp.getSuccess())) {
            for (int i = 0; i < skus.size(); i++) {
                msgSKUSyncDao.updateStatusById(DefaultStatus.CANCELED.getValue(), skus.get(i).getId());
            }
        }
    }

    /**
     * 发送发票信息
     * 
     * @param invoices
     * @param source
     * @param sourceWh
     */
    public void sendInvoice(Map<String, List<MsgInvoice>> invoices, String source, String sourceWh) {
        Warehouse wh = warehouseDao.getBySource(source, sourceWh);
        if (wh == null) {
            return;
        }
        String cmpName = wh.getOu().getParentUnit().getParentUnit().getName();
        InvoicesInfoCommand invoicesInfo = new InvoicesInfoCommand();
        List<Long> msgIds = new ArrayList<Long>();// 发票消息表ID
        for (Entry<String, List<MsgInvoice>> ent : invoices.entrySet()) {
            InvoicesCommand invsCmd = new InvoicesCommand();
            invsCmd.setRemark1(cmpName);
            invsCmd.setSoreference(ent.getKey());
            for (MsgInvoice msg : ent.getValue()) {
                msgIds.add(msg.getId());
                InvoiceCommand cmd = new InvoiceCommand();
                cmd.setInvoicedate(msg.getInvoiceTime());
                cmd.setPaycompany(msg.getPayer());
                cmd.setPayee(msg.getPayee());
                cmd.setInvoicewho(msg.getDrawer());
                cmd.setNum1(msg.getQty1());
                cmd.setNum2(msg.getQty2());
                cmd.setNum3(msg.getQty3());
                cmd.setPrice1(msg.getUnitPrice1());
                cmd.setPrice2(msg.getUnitPrice2());
                cmd.setPrice3(msg.getUnitPrice3());
                cmd.setProject1(msg.getItem1());
                cmd.setProject2(msg.getItem2() == null ? "" : msg.getItem2());
                cmd.setProject3(msg.getItem3() == null ? "" : msg.getItem3());
                cmd.setRemark(msg.getRemark());
                cmd.setSum1(msg.getAmt1());
                cmd.setSum2(msg.getAmt2());
                cmd.setSum3(msg.getAmt3());
                invsCmd.getList().add(cmd);
            }
            invoicesInfo.getList().add(invsCmd);
        }
        String xml = MarshallerUtil.buildJaxbWithEncoiding(invoicesInfo, "UTF-8");
        InvoicesResponseCommand res = PushExpressInfoPortTypeClientNew2.sendInvoices(xml);
        if (res.getIsSuccess() != null && res.getIsSuccess()) {
            // 发送成功修改状态
            for (Long id : msgIds) {
                baseOutWarehouseManager.updateMsgInvoiceStatus(id, DefaultStatus.FINISHED);
            }
        }
    }

    /**
     * 调用仓库webservice，发送出库通知单至仓库。
     */
    public String outBoundOrder(List<Long> ids) {
        RequestOrderListCommand list = new RequestOrderListCommand();
        List<Object> requestlist = new ArrayList<Object>();
        List<String> staCodes = new ArrayList<String>();

        for (long outOrderId : ids) {
            // MsgOutboundOrder msg = msgOutboundOrderDao.getByPrimaryKey(outOrderId);
            // StockTransApplication sta = staDao.findStaByCode(msg.getStaCode());
            MsgOutboundOrderCommand msg = msgOutboundOrderDao.findMsgOutboundOrderByMsgId(outOrderId, new BeanPropertyRowMapperExt<MsgOutboundOrderCommand>(MsgOutboundOrderCommand.class));
            if (msg == null) continue;
            RequestOrderInfo rList = new RequestOrderInfo();
            /*
             * CompanyShop cs = msgOutboundOrderDao.findShopByOwner(sta.getOwner(), new
             * BeanPropertyRowMapper<CompanyShop>(CompanyShop.class));
             */
            BiChannel cs = companyShopDao.getByCode(msg.getStaOwner());

            if (msg != null) {
                rList.setWarehouseid("BML_KSWH");
                rList.setCustomerId("BZ_ALL");
                rList.setOrderCode(msg.getStaRefSlipCode() == null ? "" : msg.getStaRefSlipCode());
                rList.setSystemId(msg.getStaCode());
                rList.setOrderType("CM");
                rList.setShipping((msg.getLpCode() == null || "".equals(msg.getLpCode())) ? "" : msg.getLpCode());
                rList.setIssuePartyId(cs.getShopCode() == null ? cs.getCode() : cs.getShopCode());
                rList.setIssuePartyName(cs.getName());
                rList.setCustomerName(msg.getReceiver() == null || msg.getReceiver().equals("") ? "" : msg.getReceiver());
                rList.setPayment("");
                SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                rList.setOrderTime(sf.format(msg.getStaCreateTime()));

                rList.setWebsite("");
                rList.setFreight(msg.getTransferFee() == null ? new BigDecimal(0) : msg.getTransferFee());
                rList.setServiceCharge(new BigDecimal(0));

                rList.setIsCashsale(msg.getIsCashsale());

                rList.setName(msg.getReceiver());
                rList.setPostcode(msg.getZipcode() == null ? "200000" : msg.getZipcode());
                rList.setPhone(msg.getTelePhone() == null ? "" : msg.getTelePhone().replaceAll("'", ""));
                rList.setMobile(msg.getMobile() == null ? "" : msg.getMobile().replaceAll("'", ""));
                rList.setProv(msg.getProvince());
                rList.setCity(msg.getCity());
                rList.setDistrict(msg.getDistrict() == null ? "" : msg.getDistrict());
                rList.setAddress(msg.getAddress());

                rList.setItemsValue(msg.getStaTotalActual() == null ? new BigDecimal(0) : msg.getStaTotalActual());
                List<Object> cmdlist = new ArrayList<Object>();
                List<MsgOutboundOrderLineCommand> msgLine = msgOutboundOrderLineDao.findeMsgOutLintBymsgOrderId(msg.getId(), new BeanPropertyRowMapperExt<MsgOutboundOrderLineCommand>(MsgOutboundOrderLineCommand.class));
                for (MsgOutboundOrderLineCommand line : msgLine) {
                    // Sku sku = skuDao.getByPrimaryKey(line.getSku().getId());
                    Item item = new Item();
                    item.setSpuCode(line.getSkuCode() == null ? "" : line.getSkuCode());
                    item.setItemName(line.getSkuName() == null ? "" : line.getSkuName());
                    item.setItemCount(line.getQty());
                    if (line.getUnitPrice() == null) {
                        item.setItemValue(new BigDecimal(0));
                    } else {
                        item.setItemValue(line.getUnitPrice());
                    }
                    cmdlist.add(item);
                }
                rList.setItems(cmdlist);
                rList.setRemark(msg.getRemark() == null ? "" : msg.getRemark());
                requestlist.add(rList);
                staCodes.add(msg.getStaCode());
            }
        }
        list.setObj(requestlist);
        String msgStr = MarshallerUtil.buildJaxb(list);
        msgStr = msgStr.split("</RequestOrderList>")[0] + "</RequestOrderList>";
        InOutBoundResponse resp = PushExpressInfoPortTypeClientNew2.soToWms(msgStr);
        StringBuilder sb = new StringBuilder();
        sb.append(resp.getSuccess()).append("|").append(resp.getCode()).append("|").append(resp.getDesc());
        log.debug("====>BiaoGan outbound order notify result is [{}], all the  staCode are [{}]", sb.toString(), staCodes.toString());
        if ("true".equals(resp.getSuccess()) || resp.getDesc().contains("重复推送")) {
            for (long outOrderId : ids) {
                msgOutboundOrderDao.updateStatusById(DefaultStatus.FINISHED.getValue(), outOrderId);
            }
        } else if ("false".equals(resp.getSuccess())) {
            for (long outOrderId : ids) {
                msgOutboundOrderDao.updateStatusById(DefaultStatus.CANCELED.getValue(), outOrderId);
            }
        }
        return "";
    }

    /**
     * 其他出库指令通知
     * 
     * @param ids
     */
    public void outBoundReturn(List<Long> ids) {
        RequestOrderListCommand list = new RequestOrderListCommand();
        List<Long> resultIDs = new ArrayList<Long>();
        List<Object> requestlist = new ArrayList<Object>();
        for (Long id : ids) {
            try {
                MsgOutboundOrderCommand msg = msgOutboundReturnDao.findMsgOutboundReturnByMsgId(id, new BeanPropertyRowMapperExt<MsgOutboundOrderCommand>(MsgOutboundOrderCommand.class));
                if (msg != null) {
                    RequestOrderInfo rList = new RequestOrderInfo();
                    BiChannel cs = companyShopDao.getByCode(msg.getStaOwner());
                    rList.setWarehouseid("BML_KSWH");
                    rList.setCustomerId("BZ_ALL");
                    rList.setOrderCode(msg.getStaRefSlipCode() == null ? "" : msg.getStaRefSlipCode());
                    rList.setSystemId(msg.getStaCode());
                    rList.setOrderType("TD");
                    rList.setShipping((msg.getLpCode() == null || msg.getLpCode().equals("")) ? "" : msg.getLpCode());
                    rList.setIssuePartyId(cs.getShopCode() == null ? cs.getCode() : cs.getShopCode());
                    rList.setIssuePartyName(cs.getName());
                    rList.setCustomerName(msg.getReceiver() == null || msg.getReceiver().equals("") ? "" : msg.getReceiver());
                    rList.setPayment("");
                    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    rList.setOrderTime(sf.format(msg.getStaCreateTime()));
                    rList.setWebsite("");
                    rList.setFreight(msg.getTransferFee() == null ? new BigDecimal(0) : msg.getTransferFee());
                    rList.setServiceCharge(new BigDecimal(0));
                    rList.setIsCashsale(msg.getIsCashsale());
                    rList.setName(msg.getReceiver() == null ? "" : msg.getReceiver());
                    rList.setPostcode(msg.getZipcode() == null ? "200000" : msg.getZipcode());
                    rList.setPhone(msg.getTelePhone() == null ? "" : msg.getTelePhone());
                    rList.setMobile(msg.getMobile() == null ? "" : msg.getMobile());
                    rList.setProv(msg.getProvince() == null ? "" : msg.getProvince());
                    rList.setCity(msg.getCity() == null ? "" : msg.getCity());
                    rList.setDistrict(msg.getDistrict() == null ? "" : msg.getDistrict());
                    rList.setAddress(msg.getAddress() == null ? "" : msg.getAddress());

                    rList.setItemsValue(msg.getStaTotalActual() == null ? new BigDecimal(0) : msg.getStaTotalActual());
                    List<Object> cmdlist = new ArrayList<Object>();
                    List<MsgOutboundOrderLineCommand> msgLine = msgOutboundReturnLineDao.findeMsgOutLintBymsgReturnId(msg.getId(), new BeanPropertyRowMapperExt<MsgOutboundOrderLineCommand>(MsgOutboundOrderLineCommand.class));
                    for (MsgOutboundOrderLineCommand line : msgLine) {
                        Item item = new Item();
                        item.setSpuCode(line.getSkuCode() == null ? "" : line.getSkuCode());
                        item.setItemName(line.getSkuName() == null ? "" : line.getSkuName());
                        item.setItemCount(line.getQty());
                        if (line.getUnitPrice() == null) {
                            item.setItemValue(new BigDecimal(0));
                        } else {
                            item.setItemValue(line.getUnitPrice());
                        }
                        cmdlist.add(item);
                    }
                    rList.setItems(cmdlist);
                    rList.setRemark(msg.getRemark() == null ? "" : msg.getRemark());
                    requestlist.add(rList);
                    resultIDs.add(id);
                }
            } catch (Exception e) {
                msgOutboundOrderDao.updateStatusById(DefaultStatus.CANCELED.getValue(), id);
            }
        }
        // XML数据准备
        list.setObj(requestlist);
        String msgStr = MarshallerUtil.buildJaxb(list);

        // // 调用接口生成数据 的到 返回值返回的结果，解析
        InOutBoundResponse resp = PushExpressInfoPortTypeClientNew2.soToWms(msgStr);
        if ("true".equals(resp.getSuccess())) {
            for (long outOrderId : resultIDs) {
                msgOutboundOrderDao.updateStatusById(DefaultStatus.FINISHED.getValue(), outOrderId);
            }
        } else if ("false".equals(resp.getSuccess())) {
            for (long outOrderId : resultIDs) {
                msgOutboundOrderDao.updateStatusById(DefaultStatus.CANCELED.getValue(), outOrderId);
            }
        }
    }

}
