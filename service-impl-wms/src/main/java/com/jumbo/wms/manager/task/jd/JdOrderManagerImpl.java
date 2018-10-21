package com.jumbo.wms.manager.task.jd;

import java.math.BigDecimal;
import java.util.List;

import loxia.dao.support.BeanPropertyRowMapperExt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.baozun.model.jd.EtmsWayBillSend;

import com.jumbo.dao.baseinfo.BiChannelSpecialActionDao;
import com.jumbo.dao.baseinfo.WarehouseDao;
import com.jumbo.dao.warehouse.BiChannelDao;
import com.jumbo.dao.warehouse.JdConfirmOrderQueueDao;
import com.jumbo.dao.warehouse.StaAdditionalLineDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.dao.warehouse.WhTransProvideNoDao;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.expressDelivery.ChannelInsuranceManager;
import com.jumbo.wms.manager.vmi.jdData.JdManager;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.baseinfo.BiChannelSpecialAction;
import com.jumbo.wms.model.baseinfo.Transportator;
import com.jumbo.wms.model.baseinfo.Warehouse;
import com.jumbo.wms.model.warehouse.JdConfirmOrderQueue;
import com.jumbo.wms.model.warehouse.StaAdditionalLine;
import com.jumbo.wms.model.warehouse.StaDeliveryInfo;
import com.jumbo.wms.model.warehouse.StockTransApplication;

@Transactional
@Service("jdOrderManager")
public class JdOrderManagerImpl extends BaseManagerImpl implements JdOrderManager {

    private static final long serialVersionUID = -8804887891270589566L;
    @Autowired
    private StaAdditionalLineDao staAdditionalLineDao;
    @Autowired
    private WarehouseDao warehouseDao;
    @Autowired
    private JdConfirmOrderQueueDao jdConfirmOrderQueueDao;
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private BiChannelDao biChannelDao;
    @Autowired
    private JdManager jdManager;
    @Autowired
    private WhTransProvideNoDao transProvideNoDao;
    @Autowired
    private ChannelInsuranceManager channelInsuranceManager;
    @Autowired
    private BiChannelSpecialActionDao actionDao;

    public void createJdOlTransOrder() {
        List<JdConfirmOrderQueue> jds = jdConfirmOrderQueueDao.findExtOrder(0L);
        for (JdConfirmOrderQueue jd : jds) {
            StockTransApplication sta = staDao.findStaByCode(jd.getStaCode());
            StaDeliveryInfo sd = sta.getStaDeliveryInfo();
            BiChannel biChannel = biChannelDao.getByCode(sta.getOwner());
            Warehouse wh = warehouseDao.getByOuId(sta.getMainWarehouse().getId());
            BiChannel shop = biChannelDao.getByCode(sta.getOwner());
            BiChannelSpecialAction action = actionDao.getChannelActionByCodeAndType(shop.getCode(), 30, new BeanPropertyRowMapperExt<BiChannelSpecialAction>(BiChannelSpecialAction.class));
            EtmsWayBillSend billSend = new EtmsWayBillSend();
            billSend.setDeliveryId(sd.getTrackingNo());
            billSend.setSalePlat("0010001"); // 销售平台编码
            billSend.setCustomerCode(""); // 商家店铺编码
            billSend.setOrderId(sta.getCode());
            billSend.setThrOrderId(sta.getSlipCode2());
            billSend.setSelfPrintWayBill(0); // 是否客户打印运单
            billSend.setPickMethod("1"); // 取件方式
            billSend.setPackageRequired("1"); // 包装要求
            billSend.setSenderName(shop.getName()); // 寄件人姓名
            billSend.setSenderAddress(wh.getAddress()); // 寄件人地址
            billSend.setSenderTel(shop.getTelephone()); // 寄件人电话
            billSend.setSenderPostcode(wh.getZipcode()); // 寄件人邮编
            billSend.setReceiveName(sd.getReceiver()); // 收件人姓名
            billSend.setReceiveAddress(sd.getAddress()); // 手机人地址
            billSend.setProvince(sd.getProvince()); // 收件人省 
            billSend.setCity(sd.getCity()); // 收件人市
            billSend.setCounty(sd.getDistrict()); // 收件人县
            billSend.setTown(""); // 收件人镇

            billSend.setReceiveTel(sd.getTelephone()); // 收件人电话
            billSend.setReceiveMobile(sd.getMobile()); // 收件人手机
            billSend.setPostcode(sd.getZipcode()); // 收件人邮编
            billSend.setPackageCount(1); // 包裹数量
            billSend.setOmsShopId(biChannel.getId());
            // billSend.setWeight(new BigDecimal(0)); //重量
            List<StaAdditionalLine> list = staAdditionalLineDao.findByStaId(sta.getId());
            if (list != null) {
                for (StaAdditionalLine l : list) {
                    if (l.getSku().getLength() != null) {
                        billSend.setVloumLong(l.getSku().getLength().divide(new BigDecimal(10)));
                        billSend.setVloumWidth(l.getSku().getWidth().divide(new BigDecimal(10))); // 包裹宽度
                        billSend.setVloumHeight(l.getSku().getHeight().divide(new BigDecimal(10))); // 包裹高
                        break;
                    }
                }
            } else {
                billSend.setVloumLong(new BigDecimal(0));
                billSend.setVloumWidth(new BigDecimal(0)); // 包裹宽度
                billSend.setVloumHeight(new BigDecimal(0)); // 包裹高
            }
            billSend.setVloumn(new BigDecimal(0)); // 包裹体积
            billSend.setDescription("宝尊商品"); // 商品描述
            if (sd.getIsCod() != null && sd.getIsCod()) {
                billSend.setCollectionValue(1);
            } else {
                billSend.setCollectionValue(0);
            }
            // 是否代收货款
            billSend.setCollectionMoney(sta.getTotalActual().add(sd.getTransferFee())); // 代收货款金额
            // 是否保价
            if (action.getIsInsurance() == 0) {
                billSend.setGuaranteeValue(0);
            } else {
                billSend.setGuaranteeValue(1);
            }

            BigDecimal inSure = channelInsuranceManager.getInsurance(sta.getOwner(), sd.getInsuranceAmount());
            billSend.setGuaranteeValueAmount(inSure == null ? new BigDecimal(0) : inSure);
            billSend.setSignReturn(0); // 是否签单返还
            billSend.setAging(1); // 运单时效
            if (sd.getTransType().getValue() == 6) {
                billSend.setTransType(1);
            } else if (sd.getTransType().getValue() == 4) {
                billSend.setTransType(2);
            }
            try {
                jdManager.senMqJdReceiveOrder(billSend, "wms2bh_jd_way_bill_send_queue");
                jd.setExeCount(1L);
                jdConfirmOrderQueueDao.save(jd);
            } catch (Exception e) {
                log.error("", e);
            }
        }
    }

    public void jdTransOnlineNo() {
        // 如果可用订单号小于1000个，那么调用获取订单号的接口，每次获得2000
        List<BiChannel> biChannels = biChannelDao.getBiChannelListJd(new BeanPropertyRowMapper<BiChannel>(BiChannel.class));
        for (BiChannel biChannel : biChannels) {
            Long transNo = transProvideNoDao.getJdTranNoByLpcodeList(Transportator.JD, biChannel.getCode(), new SingleColumnRowMapper<Long>(Long.class));
            transNo = (transNo == null ? 0L : transNo);
            if (transNo < 5000) {
                for (int j = 0; j < 30; j++) {
                    try {
                        jdManager.sendMqGetJdTransNo(biChannel.getId(), "wms2bh_jd_way_bill_code_queue");
                    } catch (Exception e) {
                        log.error("", e);
                    }
                }
            }
        }
    }

}
