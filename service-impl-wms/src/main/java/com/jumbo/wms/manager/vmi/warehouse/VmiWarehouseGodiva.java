package com.jumbo.wms.manager.vmi.warehouse;



import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import loxia.dao.support.BeanPropertyRowMapperExt;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import cn.baozun.bh.connector.gdv.model.order.DSO;
import cn.baozun.bh.connector.gdv.model.order.DSO.DSOInformation;
import cn.baozun.bh.connector.gdv.model.order.DSO.DSOInformation.DSOHeader;
import cn.baozun.bh.connector.gdv.model.order.DSO.DSOInformation.DSOHeader.DSODetail;
import cn.baozun.bh.connector.gdv.model.order.DSO.DSOInformation.DSOHeader.DSODetail.FreightInformation;
import cn.baozun.bh.connector.gdv.model.order.DSO.DSOInformation.DSOHeader.DSODetail.PickingCriteria;
import cn.baozun.bh.connector.gdv.model.order.DSO.DSOInformation.DSOHeader.DeliveryInformation;
import cn.baozun.bh.connector.gdv.model.order.DSO.DSOInformation.DSOHeader.GeneralInformation;
import cn.baozun.bh.connector.gdv.model.order.DSO.DSOInformation.DSOHeader.ReferenceInformation;
import cn.baozun.bh.connector.gdv.model.order.DSO.DSOInformation.DSOHeader.ReferenceInformation.ReferenceOrder;
import cn.baozun.bh.connector.gdv.model.order.DSO.DSOInformation.DSOHeader.ReferenceInformation.UserDefinedLongItem;
import cn.baozun.bh.connector.gdv.model.order.DSO.DSOInformation.DSOHeader.Remarks;

import com.jumbo.dao.vmi.warehouse.MsgOutboundOrderCancelDao;
import com.jumbo.dao.vmi.warehouse.MsgOutboundOrderDao;
import com.jumbo.dao.vmi.warehouse.MsgOutboundOrderLineDao;
import com.jumbo.mq.MarshallerUtil;
import com.jumbo.util.GodivaServiceTypeUtil;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.model.DefaultStatus;
import com.jumbo.wms.model.vmi.godivaData.WSMsgInfo;
import com.jumbo.wms.model.vmi.warehouse.MsgInboundOrder;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrder;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrderCancel;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrderCommand;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrderLineCommand;
import com.jumbo.wms.model.warehouse.InventoryStatus;
import com.jumbo.wms.model.warehouse.WarehouseLocation;

public class VmiWarehouseGodiva extends AbstractVmiWarehouse {

    /**
	 * 
	 */
    private static final long serialVersionUID = 8650669634369994178L;
    @Autowired
    private MsgOutboundOrderDao msgOutboundOrderDao;
    @Autowired
    private MsgOutboundOrderLineDao msgOutboundOrderLineDao;
    @Autowired
    private MsgOutboundOrderCancelDao cancelDao;

    @Override
    public boolean cancelSalesOutboundSta(String staCode, MsgOutboundOrderCancel msg) {
        msgOutboundOrderDao.flush();
        boolean isCancle = false;
        MsgOutboundOrderCommand order = msgOutboundOrderDao.findGDVSalesOrderStacode(staCode, new BeanPropertyRowMapperExt<MsgOutboundOrderCommand>(MsgOutboundOrderCommand.class));
        MsgOutboundOrder msgOrder = msgOutboundOrderDao.findOutBoundByStaCode(staCode);
        List<MsgOutboundOrderLineCommand> lineList = msgOutboundOrderLineDao.findMsgoutLineByMsgId(order.getId(), new BeanPropertyRowMapperExt<MsgOutboundOrderLineCommand>(MsgOutboundOrderLineCommand.class));
        // 如果未给外包仓直接标记为取消,取消单据
        if (order != null && order.getStatus().equals(DefaultStatus.CREATED)) {
            msg.setMsg("WMS取消");
            cancelDao.save(msg);
            isCancle = true;
            msgOrder.setStatus(DefaultStatus.FINISHED);
            msgOutboundOrderDao.save(msgOrder);


        } else if (order.getStatus().equals(DefaultStatus.FINISHED)) {
            DSO dso = constructMqGodivaSalesOrder(order, lineList);
            String xml = MarshallerUtil.buildJaxb(dso);
            WSMsgInfo wsMsgInfo = new WSMsgInfo();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            wsMsgInfo.setTimeStamp(sdf.format(new Date()));
            Date date = new Date();
            Long evenID = date.getTime();
            wsMsgInfo.setEventID(evenID.toString());
            String baseXml = new String(Base64.encodeBase64(xml.getBytes()));
            wsMsgInfo.setBizData(baseXml);
            String msgXml = MarshallerUtil.buildJaxb(wsMsgInfo);
            String result = "";
            try {
                log.debug("START_GOD_URL");
                result = GodivaServiceTypeUtil.sendMsgtoIds(msgXml, GOD_URL);
                log.debug(result);
            } catch (Exception e) {
                log.error("", e);
            }
            if (result.equals("")) {
                log.error("arry is null");
                throw new BusinessException(ErrorCode.STA_CANCELED_ERROR_VMI_WH);
            }
            int flagbeginIdx = result.indexOf("<Flag>") + "<Flag>".length();
            int flagendIdx = result.indexOf("</Flag>");
            String flag = result.substring(flagbeginIdx, flagendIdx);
            if (flag.equals("Success")) {
                isCancle = true;
                msg.setMsg("提醒GODIVA取消");
                cancelDao.save(msg);
            } else {
                log.error("arry is null");
                throw new BusinessException(ErrorCode.STA_CANCELED_ERROR_VMI_WH);
            }
        }


        return isCancle;
    }



    @Override
    public WarehouseLocation findLocByInvStatus(InventoryStatus invStatus) {
        return null;
    }

    @SuppressWarnings("static-access")
    public static DSO constructMqGodivaSalesOrder(MsgOutboundOrderCommand order, List<MsgOutboundOrderLineCommand> lines) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        DSO dso = new DSO();
        DSOInformation dsoinfo = new DSOInformation();
        dsoinfo.setStorerIdentifier(order.getSource());
        dsoinfo.setDate(sdf.format(new Date()));
        dsoinfo.setFileType("DSO");

        DSOHeader header = new DSOHeader();
        header.setActionType("D");
        String period = "";
        JSONObject json = null;
        GeneralInformation general = new GeneralInformation();
        if (StringUtils.hasText(order.getReleaseDate())) {
            json = JSONObject.fromObject(order.getReleaseDate());
            String psDate = json.getString("psDate");
            period = json.getString("period");
            general.setReleaseDate(psDate);
        } else {
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(order.getCreateTime());
            calendar.add(calendar.DATE, 1);// 把日期往后增加一天
            general.setReleaseDate(sdf.format(calendar.getTime()));
        }

        general.setUrgentOrder("N");
        if (!("1").equals(order.getPaymentType())) {
            general.setCashOnDelivery("N");
            general.setCashOnDeliveryAmount("0");
        } else {
            general.setCashOnDelivery("Y");
            BigDecimal totalActual = order.getTotalActual().add(order.getTransferFee());// 含运费的总金额
            general.setCashOnDeliveryAmount(totalActual.toString());
        }


        header.setGeneralInformation(general);


        ReferenceInformation ref = new ReferenceInformation();

        ReferenceOrder reforder = new ReferenceOrder();
        reforder.setReferenceOrderNumber(order.getSlipCode());
        reforder.setReferenceOrderType("DeliveryReference");
        ref.getReferenceOrder().add(reforder);


        ReferenceOrder reforder3 = new ReferenceOrder();
        reforder3.setReferenceOrderType("InvoiceNumber");
        if (!("1").equals(order.getPaymentType())) {
            reforder3.setReferenceOrderNumber("0");
        } else {
            if (order.getTransferFee() != null) {
                reforder3.setReferenceOrderNumber(order.getTransferFee().toString());
            } else {
                reforder3.setReferenceOrderNumber("0");
            }

        }
        ref.getReferenceOrder().add(reforder3);

        UserDefinedLongItem item1 = new UserDefinedLongItem();
        item1.setUserDefinedLongItemValue(period);
        item1.setUserDefinedLongItemIndex(7);
        ref.getUserDefinedLongItem().add(item1);

        if (("1").equals(order.getPaymentType())) {
            UserDefinedLongItem item2 = new UserDefinedLongItem();
            item2.setUserDefinedLongItemValue("Cash Payment");
            item2.setUserDefinedLongItemIndex(8);

            ref.getUserDefinedLongItem().add(item2);
        }


        header.setReferenceInformation(ref);

        DeliveryInformation delivery = new DeliveryInformation();
        delivery.setConsigneeOrShipToType("ShipTo");
        delivery.setCompany("");// 收货公司
        delivery.setAddressLine1(order.getReserve1() == null ? "" : order.getReserve1());
        delivery.setAddressLine2(order.getReserve2() == null ? "" : order.getReserve2());
        delivery.setAddressLine3(order.getReserve3() == null ? "" : order.getReserve3());
        delivery.setAddressLine4(order.getCity() == null ? "" : order.getCity());
        delivery.setAddressLine5(order.getDistrict() == null ? "" : order.getDistrict());
        delivery.setZip(order.getZipcode());
        delivery.setContactName(order.getReceiver());
        if (order.getMobile() == null) {
            delivery.setContactPhone(order.getTelePhone());
        } else {
            delivery.setContactPhone(order.getMobile());
        }

        delivery.setRouting("KEAS");

        header.setDeliveryInformation(delivery);



        List<DSODetail> detailLine = new ArrayList<DSODetail>();
        for (MsgOutboundOrderLineCommand line : lines) {
            // String lineReserve1="",lineReserve2="",lineReserve3="",wishComment="";

            // 解析丝带，配饰信息
            if (StringUtils.hasText(line.getLineReserve1())) {
                if (line.getLineReserve1().length() > 0) {
                    if (line.getLineReserve1().indexOf("\r") != -1) {
                        line.setLineReserve1(line.getLineReserve1().replaceAll("\r", "\n"));
                    }
                }
                // json处理
                json = JSONObject.fromObject(line.getLineReserve1().replace("\n", " "));
                JSONArray arry = null;
                try {
                    arry = json.getJSONArray("skus");
                } catch (Exception e) {
                    log.error("", e);
                }
                if (arry == null) {
                    log.error("arry is null");
                    throw new BusinessException(ErrorCode.SYSTEM_ERROR);
                }
                Object[] skus = arry.toArray();
                int qty = skus.length;
                // 如果qty=2 说明同样该商品应该有两件，此行要拆开分别对应不同的配饰信息
                for (int i = 1; i <= qty; i++) {

                    JSONObject js = (JSONObject) skus[i - 1];
                    if (js == null) {
                        continue;
                    }
                    String wishCard = (String) js.get("wishCard");
                    String lineReserve3 = wishCard;

                    if (line.getBarCode().equals(wishCard)) {
                        continue;
                    }
                    String lineReserve1 = (String) js.get("sidaiCode");
                    String lineReserve2 = (String) js.get("peishiCode");
                    String wishComment = (String) js.get("wishComment");
                    if (wishComment != null) {
                        if (wishComment.length() > 100) {
                            wishComment = wishComment.substring(0, 100);
                        }
                    } else {
                        wishComment = "";
                    }

                    DSODetail detail = new DSODetail();
                    FreightInformation information = new FreightInformation();
                    information.setSKU(line.getBarCode());
                    if (qty == 2 && 2 == line.getQty()) {
                        information.setReleaseQuantity(String.valueOf(line.getQty() - 1));
                    } else {
                        information.setReleaseQuantity(String.valueOf(line.getQty()));
                    }

                    PickingCriteria pick = new PickingCriteria();
                    pick.setPickBy1("F");
                    /*
                     * if(order.getShopId().equals(Constants.GDV_BS_SHOP_ID)&&line.getLineReserve1()==
                     * null||StringUtils.hasText(line.getLineReserve1())&&line.getBarCode().equals(
                     * lineReserve3)){ continue; }
                     */

                    Remarks remarks = new Remarks();
                    if (order.getRemark() != null && !order.getRemark().equals("")) {
                        if (order.getRemark().length() > 20) {
                            remarks.setShortRemark1(order.getRemark().substring(0, 20));
                            remarks.setShortRemark2(order.getRemark().substring(20, order.getRemark().length()));
                        } else {
                            remarks.setShortRemark1(order.getRemark());
                        }
                    } else {
                        remarks.setShortRemark1(order.getRemark());
                    }
                    remarks.setLongRemark(wishComment);
                    header.setRemarks(remarks);

                    cn.baozun.bh.connector.gdv.model.order.DSO.DSOInformation.DSOHeader.DSODetail.ReferenceInformation refdetail = new cn.baozun.bh.connector.gdv.model.order.DSO.DSOInformation.DSOHeader.DSODetail.ReferenceInformation();
                    if (StringUtils.hasText(lineReserve1)) {

                        cn.baozun.bh.connector.gdv.model.order.DSO.DSOInformation.DSOHeader.DSODetail.ReferenceInformation.UserDefinedLongItem detailitem1 =
                                new cn.baozun.bh.connector.gdv.model.order.DSO.DSOInformation.DSOHeader.DSODetail.ReferenceInformation.UserDefinedLongItem();
                        detailitem1.setUserDefinedLongItemIndex(20);
                        detailitem1.setUserDefinedLongItemValue(lineReserve1);
                        refdetail.getUserDefinedLongItem().add(detailitem1);
                    }
                    if (StringUtils.hasText(lineReserve2)) {
                        cn.baozun.bh.connector.gdv.model.order.DSO.DSOInformation.DSOHeader.DSODetail.ReferenceInformation.UserDefinedLongItem detailitem2 =
                                new cn.baozun.bh.connector.gdv.model.order.DSO.DSOInformation.DSOHeader.DSODetail.ReferenceInformation.UserDefinedLongItem();
                        detailitem2.setUserDefinedLongItemIndex(21);
                        detailitem2.setUserDefinedLongItemValue(lineReserve2);
                        refdetail.getUserDefinedLongItem().add(detailitem2);

                    }
                    if (StringUtils.hasText(lineReserve3)) {
                        cn.baozun.bh.connector.gdv.model.order.DSO.DSOInformation.DSOHeader.DSODetail.ReferenceInformation.UserDefinedLongItem detailitem3 =
                                new cn.baozun.bh.connector.gdv.model.order.DSO.DSOInformation.DSOHeader.DSODetail.ReferenceInformation.UserDefinedLongItem();
                        detailitem3.setUserDefinedLongItemIndex(22);
                        detailitem3.setUserDefinedLongItemValue(lineReserve3);
                        refdetail.getUserDefinedLongItem().add(detailitem3);
                    }

                    cn.baozun.bh.connector.gdv.model.order.DSO.DSOInformation.DSOHeader.DSODetail.ReferenceInformation.UserDefinedLongItem detailitem4 =
                            new cn.baozun.bh.connector.gdv.model.order.DSO.DSOInformation.DSOHeader.DSODetail.ReferenceInformation.UserDefinedLongItem();
                    detailitem4.setUserDefinedLongItemIndex(23);
                    if (("1").equals(order.getPaymentType())) {
                        if (qty == 2 && 2 == line.getQty()) {
                            detailitem4.setUserDefinedLongItemValue((line.getTotalActual().divide(new BigDecimal("2")).toString()));
                        } else {
                            detailitem4.setUserDefinedLongItemValue(line.getTotalActual().toString());
                        }
                    } else {
                        detailitem4.setUserDefinedLongItemValue("0");
                    }

                    refdetail.getUserDefinedLongItem().add(detailitem4);
                    detail.setReferenceInformation(refdetail);

                    detail.setFreightInformation(information);
                    detail.setPickingCriteria(pick);

                    detailLine.add(detail);


                }
            } else {

                DSODetail detail = new DSODetail();
                FreightInformation information = new FreightInformation();
                information.setSKU(line.getBarCode());
                information.setReleaseQuantity(line.getQty().toString());

                PickingCriteria pick = new PickingCriteria();
                pick.setPickBy1("F");
                /*
                 * if(order.getShopId().equals(Constants.GDV_BS_SHOP_ID)&&line.getLineReserve1()==null
                 * ){ continue; }
                 */

                if (header.getRemarks() == null) {
                    Remarks remarks = new Remarks();
                    if (order.getRemark() != null && !order.getRemark().equals("")) {
                        if (order.getRemark().length() > 20) {
                            remarks.setShortRemark1(order.getRemark().substring(0, 20));
                            remarks.setShortRemark2(order.getRemark().substring(20, order.getRemark().length()));
                        } else {
                            remarks.setShortRemark1(order.getRemark());
                        }
                    } else {
                        remarks.setShortRemark1(order.getRemark());
                    }
                    remarks.setLongRemark("");
                    header.setRemarks(remarks);
                }
                cn.baozun.bh.connector.gdv.model.order.DSO.DSOInformation.DSOHeader.DSODetail.ReferenceInformation refdetail = new cn.baozun.bh.connector.gdv.model.order.DSO.DSOInformation.DSOHeader.DSODetail.ReferenceInformation();
                cn.baozun.bh.connector.gdv.model.order.DSO.DSOInformation.DSOHeader.DSODetail.ReferenceInformation.UserDefinedLongItem detailitem4 =
                        new cn.baozun.bh.connector.gdv.model.order.DSO.DSOInformation.DSOHeader.DSODetail.ReferenceInformation.UserDefinedLongItem();
                detailitem4.setUserDefinedLongItemIndex(23);
                if (("1").equals(order.getPaymentType())) {
                    detailitem4.setUserDefinedLongItemValue(line.getTotalActual().toString());
                } else {
                    detailitem4.setUserDefinedLongItemValue("0");
                }

                refdetail.getUserDefinedLongItem().add(detailitem4);
                detail.setReferenceInformation(refdetail);

                detail.setFreightInformation(information);
                detail.setPickingCriteria(pick);
                detailLine.add(detail);
            }
        }
        header.getDSODetail().addAll(detailLine);
        dsoinfo.setDSOHeader(header);
        dso.setDSOInformation(dsoinfo);
        return dso;
    }



    @Override
    public void inboundNotice(MsgInboundOrder msgInorder) {

    }



    @Override
    public void inboundReturnRequestAnsToWms(MsgInboundOrder msg) {}



    @Override
    public boolean cancelReturnRequest(Long msgLog) {
        return false;
    }

}
