package com.jumbo.wms.manager.expressDelivery.logistics.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import cn.baozun.model.top.TopLogisticsService;
import cn.baozun.model.top.TopPackageItem;
import cn.baozun.model.top.TopWayBillApplyCancelRequest;
import cn.baozun.model.top.TopWayBillApplyCancelResponse;
import cn.baozun.model.top.TopWaybillAddress;
import cn.baozun.model.top.TopWlbWaybillIGetRequest;
import cn.baozun.model.top.TopWlbWaybillIGetRequest.TopTradeOrderInfoCols;
import cn.baozun.model.top.TopWlbWaybillIGetResponse;
import cn.baozun.model.top.TopWlbWaybillIGetResponse.TopWaybillApplyNewInfo;
import cn.baozun.rmi.top.TopRmiService;

import com.jumbo.dao.baseinfo.SkuDao;
import com.jumbo.dao.warehouse.BiChannelDao;
import com.jumbo.dao.warehouse.PackageInfoDao;
import com.jumbo.dao.warehouse.StaDeliveryInfoDao;
import com.jumbo.dao.warehouse.StaLineDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.util.StringUtil;
import com.jumbo.wms.Constants;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.expressDelivery.logistics.TransAliWaybill;
import com.jumbo.wms.manager.mongodb.MongoDBManager;
import com.jumbo.wms.manager.system.ChooseOptionManager;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.baseinfo.Transportator;
import com.jumbo.wms.model.mongodb.MongoDBYtoBigWord;
import com.jumbo.wms.model.system.ChooseOption;
import com.jumbo.wms.model.warehouse.PackageInfo;
import com.jumbo.wms.model.warehouse.StaDeliveryInfo;
import com.jumbo.wms.model.warehouse.StaLine;
import com.jumbo.wms.model.warehouse.StockTransApplication;

@Transactional
@Service("transAliWaybill")
public class TransAliWaybillImpl extends BaseManagerImpl implements TransAliWaybill {

    private static final long serialVersionUID = 3285467892508602075L;
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private StaDeliveryInfoDao staDeliveryInfoDao;
    @Autowired
    private TopRmiService topRmiService;
    @Autowired
    private StaLineDao staLineDao;
    @Autowired
    private SkuDao skuDao;
    @Autowired
    private BiChannelDao biChannelDao;
    @Autowired
    private MongoDBManager mongoDBManager;
    @Autowired
    private PackageInfoDao packageInfoDao;
    @Autowired
    private ChooseOptionManager chooseOptionManager;

    private String PROVINCE = "_PROVINCE";
    private String CITY = "_CITY";
    private String DISTRICT = "_DISTRICT";
    private String ADDRESS = "_ADDRESS";

    @Override
    public TopWlbWaybillIGetResponse waybillGetByStaId(Long staId, String packageNo) {
        StockTransApplication sta = staDao.getByPrimaryKey(staId);
        List<StockTransApplication> groupStaList = staDao.findGroupStaList(staId);
        OperationUnit ou = sta.getMainWarehouse();
        BiChannel bc = biChannelDao.getByCode(sta.getOwner());
        StaDeliveryInfo sd = sta.getStaDeliveryInfo();
        if (sd == null) {
            sd = staDeliveryInfoDao.getByPrimaryKey(staId);
        }
        /*
         * if (!Transportator.YTO.equals(sd.getLpCode())) {// 临时写成这样，后续可以改为可配置的 return null; }
         */
        // 各物流云栈开关
        Map<String, String> option = chooseOptionManager.getOptionByCategoryCode(Constants.ALI_WAYBILL);
        if (option != null) {
            ChooseOption co = chooseOptionManager.findChooseOptionByCategoryCodeAndKey(Constants.ALI_WAYBILL + "_" + sd.getLpCode(), bc.getCode());
            String onOff = option.get(sd.getLpCode());
            String source = option.get(sta.getOrderSourcePlatform());
            if (co == null || !Constants.ALI_WAYBILL_SWITCH_ON.equals(co.getOptionValue()) || onOff == null || !Constants.ALI_WAYBILL_SWITCH_ON.equals(onOff) || source == null || !Constants.ALI_WAYBILL_SWITCH_ON.equals(source)) {
                return null;
            }


        } else {
            return null;
        }
        List<StaLine> slList = staLineDao.findByStaId(sta.getId());

        String p = option.get(ou.getCode() + PROVINCE);
        String c = option.get(ou.getCode() + CITY);
        String ar = option.get(ou.getCode() + DISTRICT);
        String ad = option.get(ou.getCode() + ADDRESS);
        if (p == null || c == null || ar == null || ad == null) {
            return null;
        }
        TopWlbWaybillIGetRequest tr = new TopWlbWaybillIGetRequest();
        tr.setCpCode(sd.getLpCode());// 物流商编码
        // 发货地址
        TopWaybillAddress f = new TopWaybillAddress();
        f.setProvince(p);
        f.setCity(c);
        f.setArea(ar);
        f.setAddressDetail(ad);
        tr.setShippingAddress(f);


        List<TopTradeOrderInfoCols> tradeOrderInfoCols = new ArrayList<TopTradeOrderInfoCols>();
        TopTradeOrderInfoCols tt = new TopTradeOrderInfoCols();
        TopWaybillAddress s = new TopWaybillAddress();
        s.setProvince(sd.getProvince());
        s.setCity(sd.getCity());
        s.setArea(sd.getDistrict());
        s.setAddressDetail(sd.getAddress());
        tt.setConsigneeAddress(s);// 收件地址
        tt.setConsigneeName(sd.getReceiver());// 收件人
        if (!StringUtil.isEmpty(sd.getTelephone())) {

            tt.setConsigneePhone(sd.getTelephone());
        } else {
            tt.setConsigneePhone(sd.getMobile());
        }
        tt.setOrderChannelsType(sta.getOrderSourcePlatform());// 订单来源

        // 商品列表
        List<TopPackageItem> packageItems = new ArrayList<TopPackageItem>();
        for (StaLine sl : slList) {
            TopPackageItem tpi = new TopPackageItem();
            tpi.setCount(sl.getQuantity());
            Sku sku = skuDao.getByPrimaryKey(sl.getSku().getId());
            tpi.setItemName(sku.getName());
            packageItems.add(tpi);
        }
        tt.setPackageItems(packageItems);
        // 订单号
        List<String> tradeOrderList = new ArrayList<String>();
        if (groupStaList != null && !groupStaList.isEmpty()) {// 如果是
            for (StockTransApplication stac : groupStaList) {
                tradeOrderList.add(stac.getSlipCode1());
            }
        } else {
            tradeOrderList.add(sta.getSlipCode1());
        }
        tt.setTradeOrderList(tradeOrderList);

        tt.setProductType("STANDARD_EXPRESS");
        List<TopLogisticsService> logisticsServiceList = new ArrayList<TopLogisticsService>();
        if (sd.getIsCod() != null && sd.getIsCod()) {
            TopLogisticsService ts = new TopLogisticsService();
            ts.setServiceCode("SVC-COD");
            logisticsServiceList.add(ts);
            tt.setLogisticsServiceList(logisticsServiceList);
        }
        tt.setPackageId(packageNo);
        tradeOrderInfoCols.add(tt);
        tr.setTradeOrderInfoCols(tradeOrderInfoCols);



        TopWlbWaybillIGetResponse twgr = transNoGet(tr, bc.getId());
        for (int i = 0; i < 2; i++) {
            if (twgr == null) {
                twgr = transNoGet(tr, bc.getId());
            } else {
                break;
            }
        }

        return twgr;
    }

    @Override
    public StaDeliveryInfo waybillGetByPackage(Long packId) {
        StaDeliveryInfo sd = null;
        try {

            // 匹配时基于订单是否COD判断获取不同类型的运单号(EMS COD与非COD 帐号不同)
            PackageInfo info = packageInfoDao.getByPrimaryKey(packId);

            if (info != null) {
                sd = info.getStaDeliveryInfo();// 根据包裹获取物流信息
                if (sd != null) {} else {
                    throw new BusinessException(ErrorCode.ERROR_DELIINFO_ISNULL);
                }
            }
            TopWlbWaybillIGetResponse tr = waybillGetByStaId(sd.getId(), packId.toString());
            if (tr != null) {
                if (tr.getErrorCode() == null || "".equals(tr.getErrorCode())) {
                    if (!tr.getResult()) {
                        log.error("get ali waybill fail ! packId:" + packId + " errorMsg:" + tr.getMsg());
                        return null;
                    }
                    TopWaybillApplyNewInfo tw = tr.getWaybillApplyNewCols().get(0);
                    if (tw.getWaybillCode() == null || "".equals(tw.getWaybillCode())) {
                        return null;
                    }
                    info.setTrackingNo(tw.getWaybillCode());// 快递单号
                    info.setAliPackageNo(packId.toString());

                } else {
                    return null;
                }
            } else {
                return null;
            }

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
        return sd;
    }

    /**
     * 调用get接口
     * 
     * @param tr
     * @param biChannelId
     * @return
     */
    private TopWlbWaybillIGetResponse transNoGet(TopWlbWaybillIGetRequest tr, Long biChannelId) {
        TopWlbWaybillIGetResponse twgr = null;
        try {

            twgr = topRmiService.wlbWayBilliGet(tr, biChannelId);
        } catch (Exception e) {
            log.error(e.getMessage(), e);

        }
        return twgr;
    }

    /**
     * 跟staId获取运单号
     * 
     * @param staId
     * @return
     */
    public StaDeliveryInfo getTransNoByStaId(Long staId) {
        StaDeliveryInfo sd = null;
        try {
            StockTransApplication sta = staDao.getByPrimaryKey(staId);

            sd = sta.getStaDeliveryInfo();
            if (sd == null) {
                sd = staDeliveryInfoDao.getByPrimaryKey(staId);
            }
            // 如果之前调用成功，记录快递单号则直接返回
            if (StringUtils.hasText(sd.getTrackingNo())) {
                return sd;
            }
            TopWlbWaybillIGetResponse tr = waybillGetByStaId(staId, sta.getCode());
            if (tr != null) {
                if (tr.getErrorCode() == null || "".equals(tr.getErrorCode())) {
                    // sd = staDeliveryInfoDao.getByPrimaryKey(staId);
                    if (!tr.getResult()) {
                        log.error("get ali waybill fail ! staId:" + staId + " errorMsg:" + tr.getMsg());
                        return null;
                    }
                    TopWaybillApplyNewInfo tw = tr.getWaybillApplyNewCols().get(0);

                    sd.setTransBigWord(tw.getShortAddress());// 大头笔
                    if (tw.getWaybillCode() == null || "".equals(tw.getWaybillCode())) {
                        return null;
                    }
                    sd.setTrackingNo(tw.getWaybillCode());// 快递单号
                    sd.setAliPackageNo(sta.getCode());// 设置云栈包裹ID
                    if (sd.getLpCode().equals(Transportator.YTO)) {

                        try {
                            MongoDBYtoBigWord ytoBigWord = mongoDBManager.matchingPackNo(sd);
                            if (null != ytoBigWord) {
                                sd.setTransCityCode(ytoBigWord.getPackNo());// 打包编码
                            }
                        } catch (Exception e) {}
                    }
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }

        return sd;
    }

    @Override
    public void cancelTransNoByStaId(Long staId) {
        try {

            StockTransApplication sta = staDao.getByPrimaryKey(staId);
            StaDeliveryInfo sd = staDeliveryInfoDao.getByPrimaryKey(staId);
            BiChannel bc = biChannelDao.getByCode(sta.getOwner());
            List<PackageInfo> pacList = packageInfoDao.getByStaId(staId);

            Map<String, String> transNoMap = new HashMap<String, String>();// 存储需要取消的云栈物流单号

            if (sd.getAliPackageNo() != null && !"".equals(sd.getAliPackageNo())) {
                transNoMap.put(sd.getAliPackageNo(), sd.getTrackingNo());
            }
            if (pacList != null && !pacList.isEmpty()) {
                for (PackageInfo pi : pacList) {
                    if (pi.getAliPackageNo() != null && !"".equals(pi.getAliPackageNo())) {
                        transNoMap.put(pi.getAliPackageNo(), pi.getTrackingNo());
                    }
                }
            }

            // 取消单号
            for (String aliNo : transNoMap.keySet()) {

                TopWayBillApplyCancelRequest request = new TopWayBillApplyCancelRequest();
                request.setCpCode(sd.getLpCode());
                request.setWaybillCode(transNoMap.get(aliNo));
                request.setPackageId(aliNo);
                List<String> tradeOrderList = new ArrayList<String>();
                tradeOrderList.add(sta.getSlipCode1());
                request.setTradeOrderList(tradeOrderList);
                TopWayBillApplyCancelResponse tr = cancelTransNo(request, bc.getId());
                for (int i = 0; i < 3; i++) {
                    if (tr == null) {
                        tr = cancelTransNo(request, bc.getId());
                    } else {
                        break;
                    }
                }
                // 更新云栈电子面单表的状态
                if (tr != null && tr.getResult()) {
                    if (aliNo.equals(sd.getAliPackageNo())) {
                        sd.setAliPackageNo(aliNo + "_D");
                    }
                    if (pacList != null && !pacList.isEmpty()) {
                        for (PackageInfo pi : pacList) {
                            if (aliNo.equals(pi.getAliPackageNo())) {
                                pi.setAliPackageNo(aliNo + "_D");
                                break;
                            }
                        }
                    }


                } else if (tr != null) {
                    log.error("cancel ali waybill fail ! aliNo:" + aliNo + " errorMsg:" + tr.getMsg());
                }

            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 调用接口取消电子面单
     * 
     * @param request
     * @return
     */
    private TopWayBillApplyCancelResponse cancelTransNo(TopWayBillApplyCancelRequest request, Long biChannelId) {
        TopWayBillApplyCancelResponse tcr = null;
        try {
            tcr = topRmiService.wlbWayBillICancel(request, biChannelId);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
        return tcr;
    }

}
