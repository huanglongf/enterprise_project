package com.jumbo.pms.manager.api;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.jumbo.dao.system.ChooseOptionDao;
import com.jumbo.dao.warehouse.PackageInfoDao;
import com.jumbo.pms.manager.SysInterfaceQueueManager;
import com.jumbo.pms.model.SysInterfaceQueue;
import com.jumbo.pms.model.SysInterfaceQueueStatus;
import com.jumbo.pms.model.SysInterfaceQueueSysType;
import com.jumbo.pms.model.SysInterfaceQueueType;
import com.jumbo.pms.model.command.ParcelResult;
import com.jumbo.pms.model.command.cond.PgPackageCreateCommand;
import com.jumbo.pms.model.command.cond.PgPackageCreateCond;
import com.jumbo.pms.model.command.vo.CreateSfOrderVo;
import com.jumbo.pms.model.command.vo.GetParcelInfoVo;
import com.jumbo.util.FormatUtil;
import com.jumbo.webservice.sfNew.SfOrderWebserviceClientInter;
import com.jumbo.webservice.sfNew.model.SfOrder;
import com.jumbo.webservice.sfNew.model.SfOrderOption;
import com.jumbo.webservice.sfNew.model.SfOrderResponse;
import com.jumbo.webservice.sfNew.model.SfResponse;
import com.jumbo.wms.Constants;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.system.SequenceManager;
import com.jumbo.wms.model.system.ChooseOption;

@Transactional
@Service("apiPackageManager")
public class ApiPackageManagerImpl extends BaseManagerImpl implements ApiPackageManager {

    /**
     * 
     */
    private static final long serialVersionUID = -5403826315958082018L;
    @Autowired
    private SequenceManager sequenceManager;
    @Autowired
    private ChooseOptionDao chooseOptionDao;
    @Autowired
    private PackageInfoDao packageInfoDao;
    @Autowired
    private SysInterfaceQueueManager sysInterfaceQueueManager;
    @Autowired
    private SfOrderWebserviceClientInter sfOrderWebserviceClient;

    @Override
    public ParcelResult createSFOrder(String channelCode, CreateSfOrderVo createSfOrderVo) {
        ParcelResult result = new ParcelResult();
        SfResponse rs = createSFOrder(createSfOrderVo);
        if (rs == null || rs.getHead() == null) {// 调用接口失败
            result.setStatus(ParcelResult.STATUS_ERROR);
            result.setErrorCode(ParcelResult.ERROR_CODE_70004);// 如果运单号为空，认为是调用接口失败
            result.setMsg("调用物流商接口失败");
        } else if (rs.getHead().equals(SfResponse.STATUS_ERROR)) { // 顺丰反馈的异常信息
            result.setStatus(ParcelResult.STATUS_ERROR);
            result.setErrorCode(ParcelResult.ERROR_CODE_70013);// 物流商异常反馈
            result.setMsg(rs.getError());
        } else if (rs.getHead().equals(SfResponse.STATUS_OK)) {// 调用接口成功
            // 解析反馈body
            SfOrderResponse response = (SfOrderResponse) rs.getBodyObj();
            result.setStatus(ParcelResult.STATUS_SUCCESS);
            result.setSlipCode(response.getMailno());// 运单号
            result.setExt_str1(response.getOriginCode());// 发件地编码
            result.setExt_str2(response.getDestCode());// 目的地编码
        }
        return result;
    }

    @Override
    public PgPackageCreateCond getParcelInfo(GetParcelInfoVo getParcelInfoVo) {
        /**
         * 1.校验运单号+物流商是否存在 1.1 存在就封装数据 1.2 不存在 status = 0 errorCode = 70001
         */
        if (null == getParcelInfoVo) {
            return constructRequest(ParcelResult.STATUS_ERROR, "请求数据为空", ParcelResult.ERROR_CODE_60001);
        }

        if (!StringUtils.hasText(getParcelInfoVo.getMailNo()) && (null == getParcelInfoVo.getChannelCodes() || getParcelInfoVo.getChannelCodes().size() == 0)) {
            return constructRequest(ParcelResult.STATUS_ERROR, "数据非法, 请求失败", ParcelResult.ERROR_CODE_60002);
        }

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("mailNo", getParcelInfoVo.getMailNo());
        params.put("lpCode", getParcelInfoVo.getLpCode());
        params.put("channelCodes", getParcelInfoVo.getChannelCodes());

        List<PgPackageCreateCommand> packageInfos = packageInfoDao.findByParams(getParcelInfoVo.getLpCode(), getParcelInfoVo.getMailNo(), null);
        if (null == packageInfos || packageInfos.size() == 0) {
            return constructRequest(ParcelResult.STATUS_ERROR, "查无此件", ParcelResult.ERROR_CODE_70001);
        }
        // 如果查询到1个以上包裹信息，maybe是个合并包裹，SD暂时不做此处理
        if (packageInfos.size() > 1) {
            return constructRequest(ParcelResult.STATUS_ERROR, "查询到多个订单信息", ParcelResult.ERROR_CODE_70012);
        }
        // 返回对象
        PgPackageCreateCond cond = new PgPackageCreateCond();
        for (PgPackageCreateCommand pgPackageCommand : packageInfos) {
            Date deliveryTime = (pgPackageCommand.getShipTime());
            /** 出库时间 */
            pgPackageCommand.setPlatCreateTime(deliveryTime);
            pgPackageCommand.setCreateTime(deliveryTime);
            pgPackageCommand.setRoCode(null);
            pgPackageCommand.setExtCode(null);// 之前传的是PMS的唯一编码
            pgPackageCommand.setSourceSys(null);
        }
        cond.setStatus(PgPackageCreateCond.STATUS_SUCCESS);
        cond.setPgPackageCreateCommand(packageInfos.get(0));
        return cond;
    }

    // @Override
    // public PgPackageCreateCond getParcelInfo(GetParcelInfoVo getParcelInfoVo) {
    // /**
    // * 1.校验运单号+物流商是否存在
    // * 1.1 存在就封装数据
    // * 1.2 不存在 status = 0 errorCode = 70001
    // */
    // if(null == getParcelInfoVo){
    // return constructRequest(ParcelResult.STATUS_ERROR, "请求数据为空", ParcelResult.ERROR_CODE_60001);
    // }
    //
    // if(!StringUtils.hasText(getParcelInfoVo.getMailNo()) && (null ==
    // getParcelInfoVo.getChannelCodes() || getParcelInfoVo.getChannelCodes().size() == 0)){
    // return constructRequest(ParcelResult.STATUS_ERROR, "数据非法, 请求失败",
    // ParcelResult.ERROR_CODE_60002);
    // }
    //
    // Map<String, Object> params = new HashMap<String, Object>();
    // params.put("mailNo", getParcelInfoVo.getMailNo());
    // params.put("lpCode", getParcelInfoVo.getLpCode());
    // params.put("channelCodes", getParcelInfoVo.getChannelCodes());
    //
    // List<PackageInfo> packageInfos =
    // packageInfoDao.findByTrackingNoAndLpCode(getParcelInfoVo.getLpCode(),
    // getParcelInfoVo.getMailNo());
    // if(null == packageInfos || packageInfos.size() == 0){
    // return constructRequest(ParcelResult.STATUS_ERROR, "查无此件", ParcelResult.ERROR_CODE_70001);
    // }
    // // 如果查询到1个以上包裹信息，maybe是个合并包裹，SD暂时不做此处理
    // if(packageInfos.size() > 1){
    // return constructRequest(ParcelResult.STATUS_ERROR, "查询到多个订单信息",
    // ParcelResult.ERROR_CODE_70012);
    // }
    // // 返回对象
    // PgPackageCreateCond cond = new PgPackageCreateCond();
    // PgPackageCreateCommand pgPackageCommand = new PgPackageCreateCommand();;
    // for (PackageInfo packageInfo : packageInfos) {
    // StaDeliveryInfo sd =
    // staDeliveryInfoDao.getByPrimaryKey(packageInfo.getStaDeliveryInfo().getId());
    // StockTransApplication sta = staDao.getByPrimaryKey(sd.getId());
    // Date deliveryTime = (null == sta.getOutboundTime() ? new Date() : sta.getOutboundTime());/**
    // 出库时间 */
    // pgPackageCommand.setPlatCreateTime(deliveryTime);
    // pgPackageCommand.setCreateTime(deliveryTime);
    // pgPackageCommand.setIsCod(sd.getIsCod());
    // pgPackageCommand.setTrackNo(packageInfo.getTrackingNo());
    // String lpCode = packageInfo.getLpCode();
    // Transportator transportator = transportatorDao.findByCode(lpCode);
    // pgPackageCommand.setLpCode(lpCode);
    // // String lpName = findByCategoryCodeAndKey("lpName", lpCode);
    // pgPackageCommand.setLpName(null == transportator ? null : transportator.getName());
    // pgPackageCommand.setTranstimeType(null != sd.getTransTimeType() ?
    // sd.getTransTimeType().getValue() : null);
    // pgPackageCommand.setTotalCharges(sta.getTotalActual());
    // pgPackageCommand.setStoreCode(sta.getToLocation());
    // pgPackageCommand.setOmsCode(sta.getRefSlipCode());
    // pgPackageCommand.setOrderCode(sta.getSlipCode2());
    // pgPackageCommand.setReceiver(sd.getReceiver());
    // pgPackageCommand.setReceiverPhone(sd.getMobile());
    // pgPackageCommand.setRoCode(null);
    // pgPackageCommand.setShopCode(sta.getOwner());
    // pgPackageCommand.setSourceCode(sta.getMainWarehouse().getCode());
    // pgPackageCommand.setExtCode(null);// 之前传的是PMS的唯一编码
    // pgPackageCommand.setSourceSys(null);
    // pgPackageCommand.setShipTime(deliveryTime);
    // pgPackageCommand.setTotalQty(null != sta.getSkuQty() ? sta.getSkuQty().intValue() : 0);
    // }
    // cond.setStatus(PgPackageCreateCond.STATUS_SUCCESS);
    // cond.setPgPackageCreateCommand(pgPackageCommand);
    // return cond;
    // }


    @Override
    public List<PgPackageCreateCommand> getPackageInfo() {
        log.debug("--------------- [{}] parcelInfoQuery start -------------------", new Object[] {FormatUtil.formatDate(new Date(), "yyyyMMdd HH:mm:ss")});
        /**
         * 1.获取未推送的包裹 2.封装数据
         */
        List<SysInterfaceQueue> queueList = sysInterfaceQueueManager.getSysInterfaceQueueList(SysInterfaceQueueStatus.STATUS_NEW, SysInterfaceQueueType.PARCEL_DELIVERED_NOTIFY_SD, SysInterfaceQueueSysType.G_SHOPDOG);
        List<PgPackageCreateCommand> parcelInfoQueryCommands = new ArrayList<PgPackageCreateCommand>();
        for (SysInterfaceQueue queue : queueList) {
            String code = queue.getCode();
            List<PgPackageCreateCommand> packageInfos = packageInfoDao.findByParams(queue.getLpcode(), queue.getMailNo(), queue.getOmsOrderCode());
            if (null == packageInfos || packageInfos.size() == 0) {
                int updateResult = sysInterfaceQueueManager.updateByCode(queue.getMailNo(), queue.getLpcode(), queue.getOmsOrderCode(), "查无此件", null, SysInterfaceQueueStatus.STATUS_FAILED.getValue());
                if (updateResult < 0) {
                    log.debug("sendParcelToSD update error , code [" + code + "]");
                }
                continue;
            }
            parcelInfoQueryCommands.addAll(packageInfos);
        }
        log.debug("--------------- [{}] parcelInfoQuery end -------------------", new Object[] {FormatUtil.formatDate(new Date(), "yyyyMMdd HH:mm:ss")});
        return parcelInfoQueryCommands;
    }

    private SfResponse createSFOrder(CreateSfOrderVo createSfOrderVo) {
        SfOrder order = new SfOrder();
        // 申请运单对应的宝尊单号
        String orderId = sequenceManager.getCode(SfOrder.class.getName(), order);
        order.setOrderId(orderId);
        order.setExpressType(createSfOrderVo.getExpressType());
        order.setPayMethod(SfOrder.SF_PAYMETHOD_TYPE_THIRD_PARTY_PAYMETHOD);
        SfOrderOption option = new SfOrderOption();

        // 收件方
        order.setdContact(createSfOrderVo.getdContact());
        order.setdTel(createSfOrderVo.getdTel());
        order.setdProvince(createSfOrderVo.getdProvince());
        order.setdCity(createSfOrderVo.getdCity());
        order.setdAddress(createSfOrderVo.getdAddress());
        option.setdCounty(createSfOrderVo.getdCountry());
        option.setdMobile(createSfOrderVo.getdMobile());

        // 寄件方
        order.setjAddress(createSfOrderVo.getjAddress());
        order.setjProvince(createSfOrderVo.getjProvince());
        order.setjCity(createSfOrderVo.getjCity());
        order.setjContact(createSfOrderVo.getjContact());
        order.setjTel(createSfOrderVo.getjTel());
        order.setjCompany(createSfOrderVo.getjCompany());
        option.setjMobile(createSfOrderVo.getjMobile());

        // SF模板
        String template = findByCategoryCodeAndKey(Constants.SF_WEB_SERVICE_INFO, "TEMPLATE_ZDB");
        option.setTemplate(template);

        option.setCustid(createSfOrderVo.getExt_str1());// 月结账号
        option.setCargoCount(createSfOrderVo.getCargoCount());
        // 获取 SF顾客编码
        String sfClientCode = findByCategoryCodeAndKey(Constants.SF_WEB_SERVICE_INFO, "CLIENT_CODE_ZDB");
        // 获取 SF checkWord
        String checkword = findByCategoryCodeAndKey(Constants.SF_WEB_SERVICE_INFO, "CHECKWORD_ZDB");
        // // 获取 SF顾客编码
        // String sfClientCode = findByCategoryCodeAndKey(Constants.SF_WEB_SERVICE_INFO,
        // Constants.SF_WEB_SERVICE_INFO_CODE);
        // // 获取 SF checkWord
        // String checkword = findByCategoryCodeAndKey(Constants.SF_WEB_SERVICE_INFO,
        // Constants.SF_WEB_SERVICE_INFO_CHECKWORD);
        order.setOrderOption(option);
        SfResponse rs = null;
        try {
            // 调用SF接口
            rs = sfOrderWebserviceClient.creSfOrder(order, sfClientCode, checkword, 0);
        } catch (Exception e) {}
        return rs;
    }

    public String findByCategoryCodeAndKey(String categoryCode, String key) {
        ChooseOption option = chooseOptionDao.findByCategoryCodeAndKey(categoryCode, key);
        return option.getOptionValue();
    }

    private PgPackageCreateCond constructRequest(int status, String msg, Integer errorCode) {
        PgPackageCreateCond parcelResult = new PgPackageCreateCond();
        parcelResult.setStatus(status);
        parcelResult.setMsg(msg);
        // 异常信息
        parcelResult.setErrorCode(errorCode);
        return parcelResult;
    }

}
