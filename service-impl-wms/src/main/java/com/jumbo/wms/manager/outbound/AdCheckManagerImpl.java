package com.jumbo.wms.manager.outbound;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.baozun.bh.util.JSONUtil;

import com.jumbo.dao.authorization.UserDao;
import com.jumbo.dao.baseinfo.CustomerDao;
import com.jumbo.dao.baseinfo.WarehouseDao;
import com.jumbo.dao.vmi.adidas.AdidasAmiCheckDao;
import com.jumbo.dao.warehouse.AdCheckResponseDao;
import com.jumbo.dao.warehouse.AdCheckResponseLineDao;
import com.jumbo.dao.warehouse.AdConfirmOrderDao;
import com.jumbo.dao.warehouse.BiChannelDao;
import com.jumbo.dao.warehouse.StaDeliveryInfoDao;
import com.jumbo.dao.warehouse.StaLineDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.util.zip.AppSecretUtil;
import com.jumbo.webservice.AdResponse;
import com.jumbo.webservice.AdReturnResponse;
import com.jumbo.webservice.ErrLines;
import com.jumbo.webservice.StoreExchangeConfirmResponse;
import com.jumbo.webservice.StoreLogisticsSendResponse;
import com.jumbo.wms.Constants;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.CommonLogRecordManager;
import com.jumbo.wms.manager.hub2wms.HubWmsManager;
import com.jumbo.wms.manager.task.CommonConfigManager;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.model.CommonLogRecord;
import com.jumbo.wms.model.authorization.User;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.baseinfo.Customer;
import com.jumbo.wms.model.baseinfo.Warehouse;
import com.jumbo.wms.model.mongodb.StaCheckRecord;
import com.jumbo.wms.model.mongodb.TwicePickingBarCode;
import com.jumbo.wms.model.vmi.adidasData.AdidasAmiCheck;
import com.jumbo.wms.model.warehouse.AdCheckResponse;
import com.jumbo.wms.model.warehouse.AdCheckResponseLine;
import com.jumbo.wms.model.warehouse.AdConfirmOrder;
import com.jumbo.wms.model.warehouse.StaDeliveryInfo;
import com.jumbo.wms.model.warehouse.StaLine;
import com.jumbo.wms.model.warehouse.StaLineCommand;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationStatus;
import com.jumbo.wms.web.commond.OrderCheckCommand;

/**
 * @author jinlong.ke
 * @date 2016年7月18日下午3:57:07
 * 
 */
@Service("adCheckManager")
public class AdCheckManagerImpl extends BaseManagerImpl implements AdCheckManager {
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private BiChannelDao biChannelDao;
    @Autowired
    private CommonConfigManager configManager;
    @Autowired
    private OutboundInfoManager outboundInfoManager;
    @Autowired
    private HubWmsManager hubWmsManager;
    @Autowired
    private StaDeliveryInfoDao staDeliveryInfoDao;
    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private StaLineDao staLineDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private WarehouseDao warehouseDao;
    @Autowired
    private WareHouseManager wareHouseManager;
    @Autowired
    private CommonLogRecordManager commonLogRecordManager;
    @Autowired
    private AdConfirmOrderDao adConfirmOrderDao;
    @Autowired
    private AdCheckResponseDao adCheckResponseDao;
    @Autowired
    private AdCheckResponseLineDao adCheckResponseLineDao;
    @Resource(name = "mongoTemplate")
    private MongoOperations mongoOperation;
    @Autowired
    private AdidasAmiCheckDao ad;
    @Value("${is.repeat.invokeAmi}")
    private String isRepeatInvokeAmi;// 是否重复调用AMI取消接口

    /**
     * 
     */
    private static final long serialVersionUID = -4379504062455116457L;


    /**
     * 二次分拣复核一体化-调用AD接口
     * 
     * @param pickingId
     * @param staLineId
     * @param userId
     */
    public void storeLogisticsSendByTwoPicking(Long pickingId, Long staLineId, Long userId) {
        String msg = "";
        TwicePickingBarCode tpbcList = mongoOperation.findOne(new Query(Criteria.where("pickingId").is(pickingId)), TwicePickingBarCode.class);
        List<StaCheckRecord> scrList = tpbcList.getStaCheckRecordList();
        Long staId = 0L;
        for (StaCheckRecord scr : scrList) {
            if (scr.getStaLineId().equals(staLineId)) {
                staId = scr.getStaId();
                break;
            }
        }
        StockTransApplication sta = staDao.getByPrimaryKey(staId);
        try {
            if ((sta.getIsPreSale() == null || "".equals(sta.getIsPreSale()) || "0".equals(sta.getIsPreSale())) && StockTransApplicationStatus.OCCUPIED.equals(sta.getStatus())) {
                Long cId = staDao.findAdCheckSta(staId, new SingleColumnRowMapper<Long>(Long.class));
                if (cId != null && cId != 0) {
                    return;
                }
                BiChannel channel = biChannelDao.getByCode(sta.getOwner());
                Map<String, String> mp = configManager.getCommonFTPConfig(Constants.AD_CONFIG);
                Customer customer = customerDao.getByPrimaryKey(channel.getCustomer().getId());
                if (customer.getCode().equals(mp.get(Constants.AD_CUSTOMER_CODE))) {
                    storeLogisticsSend(staId, false);
                } else {
                    return;
                }

                // OrderCheckCommand occ = new OrderCheckCommand();
                // occ.setStaId(staId);
                // occ.setPickingType(2);
                // ifExistsLineCanncel(occ, userId, sta.getMainWarehouse().getId());
            } else {
                return;
            }
        } catch (BusinessException e) {
            if (e.getArgs() != null && e.getArgs().length > 0) {
                if ("接口调不通".equals(e.getArgs()[0]) || "调用AD接口失败".equals(e.getArgs()[0]) || "数据库异常".equals(e.getArgs()[0])) {
                    throw e;
                }
                msg = e.getArgs()[0].toString();
            }
        }

        List<StaLineCommand> lc = staLineDao.findCancelLineBySta(staId, new BeanPropertyRowMapper<StaLineCommand>(StaLineCommand.class));
        if (lc != null && lc.size() > 0) {
            for (StaLineCommand sc : lc) {
                for (StaCheckRecord scr : scrList) {
                    if (scr.getStaLineId().equals(sc.getId())) {
                        scr.setCancelQty(sc.getQuantity().intValue());
                        break;
                    }
                }
            }
            mongoOperation.save(tpbcList);
        }

        Integer isPrintCancel = 1;
        // 判断打印前取消行还是打印后取消行
        Long qty = staDao.findStaLinePrintBeforeCancel(staId, new SingleColumnRowMapper<Long>(Long.class));
        if (qty != null && qty > 0) {
            isPrintCancel = 0;
        }
        // 保存调接口信息
        staDao.saveAdCheck(staId, msg, isPrintCancel);
    }


    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public String storeLogisticsSend(Long staId, boolean autoSend) {
        AdidasAmiCheck adidasAmiCheck = ad.getByPrimaryKey(staId);
        if (adidasAmiCheck != null) {
            return "";
        }
        adidasAmiCheck = new AdidasAmiCheck();
        adidasAmiCheck.setId(staId);
        adidasAmiCheck.setCreateTime(new Date());
        boolean b = false;
        String msg = "";
        log.info("storeLogisticsSend start id:" + staId);
        try {
            StockTransApplication sta = staDao.getByPrimaryKey(staId);
            if ("1".equals(sta.getIsPreSale())) {
                b = true;
            }
            BiChannel channel = biChannelDao.getByCode(sta.getOwner());
            Map<String, String> mp = configManager.getCommonFTPConfig(Constants.AD_CONFIG);
            Customer customer = customerDao.getByPrimaryKey(channel.getCustomer().getId());
            if (customer.getCode().equals(mp.get(Constants.AD_CUSTOMER_CODE))) {
                // 判断是否非实时接口
                if (mp.get(Constants.AD_INTREFER_EFFECTIVENESS).equals("true") && !autoSend) {
                    AdConfirmOrder aco = new AdConfirmOrder();
                    aco.setCreateTime(new Date());
                    aco.setStaId(staId);
                    adConfirmOrderDao.save(aco);
                } else {
                    List<String> pl = new ArrayList<String>();
                    pl.add("method");
                    pl.add("ver");
                    pl.add("timestamp");
                    pl.add("utc_offset");
                    pl.add("format");
                    pl.add("app_key");
                    pl.add("sign_method");
                    pl.add("session");
                    pl.add("api_type");
                    pl.add("order_type");
                    pl.add("order_no");
                    pl.add("sub_order_no");
                    pl.add("is_split");
                    pl.add("is_check_refund_status");
                    pl.add("express_no");
                    pl.add("express_company");
                    if (null != sta.getOrderType() && !"".equals(sta.getOrderType()) && mp.get(Constants.AD_RETURN_OUTBOUND_KEY).equals(sta.getOrderType())) {
                        pl.add("order_line_no");
                        pl.add("express_type");
                    }
                    Collections.sort(pl);
                    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String requestTime = sf.format(new Date());
                    mp.put("timestamp", requestTime);
                    mp.put("session", mp.get(sta.getOwner()));

                    mp.put("order_no", sta.getSlipCode1());
                    if (null != sta.getOrderType() && !"".equals(sta.getOrderType()) && mp.get(Constants.AD_RETURN_OUTBOUND_KEY).equals(sta.getOrderType())) {
                        String lino = staLineDao.findLineNOStringById(staId, new SingleColumnRowMapper<String>(String.class));
                        mp.put("order_line_no", lino);
                        mp.put("express_type", "express");

                    } else {
                        if (mp.get(Constants.AD_TMALL_OWNER).contains(sta.getOwner())) {
                            String lino = staLineDao.findLineNOStringById(staId, new SingleColumnRowMapper<String>(String.class));
                            mp.put("sub_order_no", lino);
                            mp.put("is_split", "true");
                            mp.put("is_check_refund_status", "true");
                        } else {
                            mp.put("is_check_refund_status", "false");
                        }
                    }

                    StaDeliveryInfo di = staDeliveryInfoDao.getByPrimaryKey(sta.getId());
                    mp.put("express_no", di.getTrackingNo());
                    mp.put("express_company", di.getLpCode());
                    StringBuffer sb = new StringBuffer("");
                    StringBuffer param = new StringBuffer("");
                    sb.append(mp.get("secret"));
                    for (String s : pl) {
                        if (mp.containsKey(s)) {
                            if (s.equals("method")) {
                                if (null != sta.getOrderType() && !"".equals(sta.getOrderType()) && mp.get(Constants.AD_RETURN_OUTBOUND_KEY).equals(sta.getOrderType())) {
                                    sb.append(s);
                                    sb.append(mp.get("return_outbound_method"));
                                    param.append(s + "=" + URLEncoder.encode(mp.get("return_outbound_method")) + "&");
                                } else {
                                    sb.append(s);
                                    sb.append(mp.get(s));
                                    param.append(s + "=" + URLEncoder.encode(mp.get(s)) + "&");
                                }
                            } else {
                                sb.append(s);
                                sb.append(mp.get(s));
                                param.append(s + "=" + URLEncoder.encode(mp.get(s)) + "&");
                            }
                        }
                    }
                    sb.append(mp.get("secret"));
                    String sign = AppSecretUtil.getMD5Str(sb.toString()).toUpperCase();
                    param.append("sign=" + sign);
                    commonLogRecordManager.saveLog(sta.getCode(), CommonLogRecord.AD_PARAM, sb.toString());
                    // log.error(sta.getCode() + "加密前:");
                    // log.error(sta.getCode() + "加密后:");
                    // log.error(sta.getCode() + "url:");
                    commonLogRecordManager.saveLog(sta.getCode(), CommonLogRecord.AD_URL_DATA, param.toString());
                    String result = "";
                    try {
                        URL url = null;
                        if (null != sta.getOrderType() && !"".equals(sta.getOrderType()) && mp.get(Constants.AD_RETURN_OUTBOUND_KEY).equals(sta.getOrderType())) {
                            url = new URL(mp.get("return_outbound_url"));
                        } else {
                            url = new URL(mp.get("url"));
                        }

                        HttpURLConnection con = (HttpURLConnection) url.openConnection();
                        con.setRequestProperty("Connection", "close");
                        con.setDoInput(true);
                        con.setDoOutput(true);
                        con.setUseCaches(false);
                        con.setConnectTimeout(60000);
                        con.setReadTimeout(60000);
                        con.setRequestMethod("POST");
                        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                        con.setRequestProperty("Content-Length", Integer.toString(param.length()));
                        OutputStream outputStream = con.getOutputStream();
                        outputStream.write(param.toString().getBytes("UTF-8"));
                        outputStream.close();
                        int responseCode = con.getResponseCode();
                        InputStream inputStream;
                        if (responseCode == HttpURLConnection.HTTP_OK) {
                            inputStream = con.getInputStream();
                        } else {
                            inputStream = con.getErrorStream();
                        }
                        BufferedReader reader;
                        String line = null;
                        reader = new BufferedReader(new InputStreamReader(inputStream));
                        while ((line = reader.readLine()) != null) {
                            result += line;
                        }
                        ad.save(adidasAmiCheck);
                        inputStream.close();
                        commonLogRecordManager.saveLog(sta.getCode(), CommonLogRecord.AD_RESPONSE, result);
                        if (log.isDebugEnabled()) {
                            log.debug("ad execute", sta.getCode());
                        }
                    } catch (Exception e) {
                        msg = "error";
                        log.error("调用AD接口error,sta id : {}", staId, e);
                        // throw new BusinessException("调用AD接口失败！");
                        throw new BusinessException(ErrorCode.AD_CONFIRM_ERROR, new Object[] {"调用AD接口失败"});
                    }
                    if (log.isInfoEnabled()) {
                        log.info("storeLogisticsSend http id:" + staId);
                    }

                    // 换货出调用接口
                    if (null != sta.getOrderType() && !"".equals(sta.getOrderType()) && mp.get(Constants.AD_RETURN_OUTBOUND_KEY).equals(sta.getOrderType())) {
                        AdReturnResponse s = JSONUtil.jsonToBean(result, AdReturnResponse.class);
                        if (autoSend) {
                            AdCheckResponse ar = new AdCheckResponse();
                            StoreExchangeConfirmResponse sr = s.getStoreExchangeConfirmResponse();
                            ar.setCreateTime(new Date());
                            ar.setErrCode(sr.getErrCode());
                            ar.setErrMsg(sr.getErrMsg());
                            ar.setPlatformMessage(sta.getOrderType());
                            ar.setIsSuccess(sr.getIsSuccess());
                            ar.setStaCode(sta.getCode());
                            adCheckResponseDao.save(ar);
                            adCheckResponseDao.flush();
                        } else {
                            if (log.isDebugEnabled()) {
                                log.debug("storeLogisticsSend JSON id:" + staId);
                            }
                            if (s == null) {
                                if (log.isDebugEnabled()) {
                                    log.debug("接口调不通,sta id :{},msg :{}", staId, result);
                                }
                                msg = "error";
                                throw new BusinessException(ErrorCode.AD_CONFIRM_ERROR, new Object[] {"接口调不通"});
                            } else {
                                if (s.getStoreExchangeConfirmResponse().getIsSuccess() == null || s.getStoreExchangeConfirmResponse().getIsSuccess().equals("false")) {
                                    StoreExchangeConfirmResponse sr = s.getStoreExchangeConfirmResponse();
                                    // 当返回订单取消，订单不存在或者订单号出错，宝尊直接取消订单
                                    if (sr.getErrCode().equals("server.order-not-existed") || sr.getErrCode().equals("server.exchange-not-existed")) {
                                        if (b) {// 预售订单
                                            msg = "error";
                                            if (log.isWarnEnabled()) {
                                                log.warn("ad order cancel ,{}", sta.getSlipCode1());
                                            }
                                            hubWmsManager.cancelSalesSta(mp.get(Constants.AD_SYSTEMKEY), sta.getSlipCode1(), true);
                                            throw new BusinessException(ErrorCode.AD_CONFIRM_ERROR, new Object[] {"AD预售" + sr.getErrMsg()});
                                        }
                                        hubWmsManager.cancelSalesSta(mp.get(Constants.AD_SYSTEMKEY), sta.getSlipCode1(), true);
                                        throw new BusinessException(ErrorCode.AD_CONFIRM_ERROR, new Object[] {sr.getErrMsg()});
                                    } else {
                                        msg = "error";
                                        throw new BusinessException(ErrorCode.AD_CONFIRM_ERROR, new Object[] {sr.getErrMsg()});
                                    }
                                }

                            }
                        }
                        // 销售出调用接口
                    } else {
                        AdResponse s1 = JSONUtil.jsonToBean(result.replace("ErrorResponse", "StoreLogisticsSendResponse"), AdResponse.class);
                        if (autoSend) {
                            AdCheckResponse ar = new AdCheckResponse();
                            StoreLogisticsSendResponse sr = s1.getStoreLogisticsSendResponse();
                            ar.setCreateTime(new Date());
                            ar.setErrCode(sr.getErrCode());
                            ar.setErrMsg(sr.getErrMsg());
                            ar.setPlatformMessage(sr.getPlatformMessage());
                            ar.setIsSuccess(sr.getIsSuccess());
                            ar.setStaCode(sta.getCode());
                            adCheckResponseDao.save(ar);
                            adCheckResponseDao.flush();
                            if (sr.getLines() != null && sr.getLines().size() > 0) {
                                for (ErrLines er : sr.getLines()) {
                                    AdCheckResponseLine line = new AdCheckResponseLine();
                                    line.setHeadId(ar);
                                    line.setCode(er.getCode());
                                    line.setLineNo(er.getLineNo());
                                    line.setStatus(er.getStatus());
                                    adCheckResponseLineDao.save(line);
                                }
                            }
                        }// 如果是定时任务来的，就只做日志记录，拉数据用，否则嵌套在内部流程里面要改单据状态的
                        else {
                            if (log.isDebugEnabled()) {
                                log.debug("storeLogisticsSend JSON id:" + staId);
                            }
                            if (s1 == null) {
                                if (log.isDebugEnabled()) {
                                    log.debug("接口调不通,sta id :{},msg :{}", staId, result);
                                }
                                msg = "error";
                                throw new BusinessException(ErrorCode.AD_CONFIRM_ERROR, new Object[] {"接口调不通"});
                            } else {
                                if (s1.getStoreLogisticsSendResponse().getIsSuccess() == null || s1.getStoreLogisticsSendResponse().getIsSuccess().equals("false")) {
                                    StoreLogisticsSendResponse sr = s1.getStoreLogisticsSendResponse();
                                    // 当返回订单取消，订单不存在或者订单号出错，宝尊直接取消订单
                                    if (sr.getErrCode().equals("server.order-has-refund") || sr.getErrCode().equals("server.order-not-existed") || sr.getErrCode().equals("101") || sr.getErrCode().equals("102")
                                            || sr.getErrCode().equals("server.order-not-existed") || sr.getErrCode().equals("server.exchange-not-existed")) {
                                        if (b) {// 预售订单
                                            msg = "error";
                                            if (log.isWarnEnabled()) {
                                                log.warn("ad order cancel ,{}", sta.getSlipCode1());
                                            }
                                            hubWmsManager.cancelSalesSta(mp.get(Constants.AD_SYSTEMKEY), sta.getSlipCode1(), true);
                                            throw new BusinessException(ErrorCode.AD_CONFIRM_ERROR, new Object[] {"AD预售" + sr.getErrMsg()});
                                        }
                                        hubWmsManager.cancelSalesSta(mp.get(Constants.AD_SYSTEMKEY), sta.getSlipCode1(), true);
                                        throw new BusinessException(ErrorCode.AD_CONFIRM_ERROR, new Object[] {sr.getErrMsg()});
                                    }// 有子订单处于不能发货状态
                                    else if (sr.getErrCode().equals("server.invalid-order-line-status")) {
                                        if (b) {// 预售订单
                                            msg = "error";
                                            throw new BusinessException(ErrorCode.AD_CONFIRM_ERROR, new Object[] {"AD存在订单行不能发货！"});
                                        }
                                        if (sr.getLines() != null) {
                                            List<ErrLines> ls = sr.getLines();
                                            List<String> lines = new ArrayList<String>();
                                            for (ErrLines l : ls) {
                                                if (!(l.getCode().equals("must-send") || l.getCode().equals("has-sent"))) {
                                                    lines.add(l.getLineNo());
                                                }
                                            }
                                            outboundInfoManager.cancelStaLine(lines, sta.getId());
                                            // 重复调用AMI接口
                                            if (isRepeatInvokeAmi != null && isRepeatInvokeAmi.equals("true")) {
                                                List<StaLine> staLine = staLineDao.findByStaId(staId);
                                                for (StaLine line : staLine) {
                                                    if (line.getQuantity() != 0) {
                                                        storeLogisticsSend1(staId, autoSend);
                                                    }
                                                }
                                            }
                                            throw new BusinessException(ErrorCode.AD_CONFIRM_ERROR, new Object[] {"存在订单行不能发货！"});
                                        }
                                    } else if (sr.getErrCode().equals("server.other-invalid-order-status")) {
                                        msg = "server.other-invalid-order-status";
                                        log.error("server.other-invalid-order-status,sta id :{},msg :{}", staId, result);
                                    } else if (sr.getErrCode().equals("server.order-has-sent") || sr.getErrCode().equals("server.order-has-part-sent")) {

                                    } else if (sr.getErrCode().equals("server.invalid-order-status") || sr.getErrCode().equals("server.invalid-exchange-status")) {
                                        msg = "error";
                                        throw new BusinessException(ErrorCode.AD_CONFIRM_ERROR, new Object[] {sr.getErrMsg()});
                                    } else {
                                        msg = "error";
                                        throw new BusinessException(ErrorCode.AD_CONFIRM_ERROR, new Object[] {sr.getErrMsg()});
                                    }
                                }

                            }
                        }
                    }
                }
            }
        } catch (BusinessException e) {
            msg = "error";
            log.error("AD_CHECK1", e);
            throw e;
        } catch (Exception e) {
            msg = "error";
            log.error("AD_CHECK2", e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "数据库异常");
        }
        if (log.isInfoEnabled()) {
            log.info("storeLogisticsSend end id:" + staId);
        }
        return msg;
    }

    @Override
    public void ifExistsLineCanncel(OrderCheckCommand checkOrder, Long userId, Long ouId) {
        Map<String, String> mp = configManager.getCommonFTPConfig(Constants.AD_CONFIG);
        StockTransApplication sta = staDao.getByPrimaryKey(checkOrder.getStaId());
        if (mp.get(Constants.AD_TMALL_OWNER).contains(sta.getOwner()) || true) {
            if (checkOrder.getPickingType() == 1) {} else {
                List<StaLine> sl = staLineDao.findByStaId(checkOrder.getStaId());
                User user = userDao.getByPrimaryKey(userId);
                boolean flag = false;
                for (StaLine l : sl) {
                    if (l.getQuantity().equals(0L)) {
                        flag = true;
                        break;
                    }
                }
                if (flag) {
                    boolean a = sta.getNoHaveReportMissing() == null ? false : sta.getNoHaveReportMissing();
                    boolean b = sta.getIsHaveReportMissing() == null ? false : sta.getIsHaveReportMissing();
                    if (a) {// 强制过滤报缺(AD拣货前部分取消 不用重复复核 true)
                        log.error("AD拣货前部分取消" + sta.getCode());
                    } else {
                        if (!b) {
                            sta.setIsHaveReportMissing(true);
                            staDao.save(sta);
                            // 存在行取消
                            throw new BusinessException(ErrorCode.AD_EXISTS_LINE_CANCEL);
                        } else {
                            boolean ifCanOp = user.getIsShortCheck() == null ? false : user.getIsShortCheck();
                            if (!ifCanOp) {
                                // 你没做操作部分发货的权限
                                throw new BusinessException(ErrorCode.AD_NO_PRIVILEGT_TODO_THIS);
                            }
                        }
                    }
                }
            }
        }
    }


    @Override
    public void reOccupationByIdList(List<Long> staIdList, Long ouId) {
        Warehouse w = warehouseDao.getByOuId(ouId);
        Customer c = customerDao.getByPrimaryKey(w.getCustomer().getId());
        Map<String, String> mp = configManager.getCommonFTPConfig(Constants.AD_CONFIG);
        if (c.getCode().equals(mp.get(Constants.AD_CUSTOMER_CODE))) {
            if (staIdList == null || staIdList.size() == 0) {

            } else {
                for (Long id : staIdList) {
                    StockTransApplication sta = staDao.getByPrimaryKey(id);
                    boolean isDistributeFaile = false;
                    if (sta.getIsDistributeFailed() != null) {
                        isDistributeFaile = sta.getIsDistributeFailed();
                    }
                    try {
                        if (sta.getStatus().equals(StockTransApplicationStatus.FROZEN) || sta.getStatus().equals(StockTransApplicationStatus.FAILED) || (sta.getStatus().equals(StockTransApplicationStatus.CREATED) && isDistributeFaile)) {
                            if (sta.getStatus().equals(StockTransApplicationStatus.FROZEN)) {
                                outboundInfoManager.reOccupiedForAd(id);
                            } else {
                                if (mp.get(Constants.AD_TMALL_OWNER).contains(sta.getOwner())) {
                                    outboundInfoManager.occupiedByIdForAD(id);
                                } else {
                                    wareHouseManager.createStvByStaId(sta.getId(), null, null, false);
                                }
                            }
                        }
                    } catch (Exception e) {
                        staDao.updateStaStatusByid(sta.getId(), StockTransApplicationStatus.FROZEN.getValue());
                    }
                }
            }
        }
    }


    @Override
    public void confirmCheckOrderById(Long id) {
        AdConfirmOrder ac = adConfirmOrderDao.getByPrimaryKey(id);
        try {
            storeLogisticsSend(ac.getStaId(), true);
            adConfirmOrderDao.deleteByPrimaryKey(id);
        } catch (BusinessException e) {
            ac.setErrorMsg(e.getErrorCode() + "");
            adConfirmOrderDao.save(ac);
        } catch (Exception e) {
            log.error("", e);
            ac.setErrorMsg("系统错误!");
            adConfirmOrderDao.save(ac);
        }

    }

    @Override
    public List<Long> getAllNeedConfirmCheckOrder() {
        return adConfirmOrderDao.getAllNeedConfirmCheckOrder(new SingleColumnRowMapper<Long>(Long.class));
    }

    @Override
    public boolean isAdPreSale(Long staId) {
        boolean b = true;
        StockTransApplication sta = staDao.getByPrimaryKey(staId);
        if ("adidas".equals(sta.getSystemKey()) && "1".equals(sta.getIsPreSale())) {
            b = false;
        }
        return b;
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    private String storeLogisticsSend1(Long staId, boolean autoSend) {
        boolean b = false;
        String msg = "";
        log.info("storeLogisticsSend start id:" + staId);

        StockTransApplication sta = staDao.getByPrimaryKey(staId);
        if ("1".equals(sta.getIsPreSale())) {
            b = true;
        }
        BiChannel channel = biChannelDao.getByCode(sta.getOwner());
        Map<String, String> mp = configManager.getCommonFTPConfig(Constants.AD_CONFIG);
        Customer customer = customerDao.getByPrimaryKey(channel.getCustomer().getId());
        if (customer.getCode().equals(mp.get(Constants.AD_CUSTOMER_CODE))) {
            // 判断是否非实时接口
            if (mp.get(Constants.AD_INTREFER_EFFECTIVENESS).equals("true") && !autoSend) {
                AdConfirmOrder aco = new AdConfirmOrder();
                aco.setCreateTime(new Date());
                aco.setStaId(staId);
                adConfirmOrderDao.save(aco);
            } else {
                List<String> pl = new ArrayList<String>();
                pl.add("method");
                pl.add("ver");
                pl.add("timestamp");
                pl.add("utc_offset");
                pl.add("format");
                pl.add("app_key");
                pl.add("sign_method");
                pl.add("session");
                pl.add("api_type");
                pl.add("order_type");
                pl.add("order_no");
                pl.add("sub_order_no");
                pl.add("is_split");
                pl.add("is_check_refund_status");
                pl.add("express_no");
                pl.add("express_company");
                if (null != sta.getOrderType() && !"".equals(sta.getOrderType()) && mp.get(Constants.AD_RETURN_OUTBOUND_KEY).equals(sta.getOrderType())) {
                    pl.add("order_line_no");
                    pl.add("express_type");
                }
                Collections.sort(pl);
                SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String requestTime = sf.format(new Date());
                mp.put("timestamp", requestTime);
                mp.put("session", mp.get(sta.getOwner()));

                mp.put("order_no", sta.getSlipCode1());
                if (null != sta.getOrderType() && !"".equals(sta.getOrderType()) && mp.get(Constants.AD_RETURN_OUTBOUND_KEY).equals(sta.getOrderType())) {
                    String lino = staLineDao.findLineNOStringById(staId, new SingleColumnRowMapper<String>(String.class));
                    mp.put("order_line_no", lino);
                    mp.put("express_type", "express");

                } else {
                    if (mp.get(Constants.AD_TMALL_OWNER).contains(sta.getOwner())) {
                        String lino = staLineDao.findLineNOStringById(staId, new SingleColumnRowMapper<String>(String.class));
                        mp.put("sub_order_no", lino);
                        mp.put("is_split", "true");
                        mp.put("is_check_refund_status", "true");
                    } else {
                        mp.put("is_check_refund_status", "false");
                    }
                }

                StaDeliveryInfo di = staDeliveryInfoDao.getByPrimaryKey(sta.getId());
                mp.put("express_no", di.getTrackingNo());
                mp.put("express_company", di.getLpCode());
                StringBuffer sb = new StringBuffer("");
                StringBuffer param = new StringBuffer("");
                sb.append(mp.get("secret"));
                for (String s : pl) {
                    if (mp.containsKey(s)) {
                        if (s.equals("method")) {
                            if (null != sta.getOrderType() && !"".equals(sta.getOrderType()) && mp.get(Constants.AD_RETURN_OUTBOUND_KEY).equals(sta.getOrderType())) {
                                sb.append(s);
                                sb.append(mp.get("return_outbound_method"));
                                param.append(s + "=" + URLEncoder.encode(mp.get("return_outbound_method")) + "&");
                            } else {
                                sb.append(s);
                                sb.append(mp.get(s));
                                param.append(s + "=" + URLEncoder.encode(mp.get(s)) + "&");
                            }
                        } else {
                            sb.append(s);
                            sb.append(mp.get(s));
                            param.append(s + "=" + URLEncoder.encode(mp.get(s)) + "&");
                        }
                    }
                }
                sb.append(mp.get("secret"));
                String sign = AppSecretUtil.getMD5Str(sb.toString()).toUpperCase();
                param.append("sign=" + sign);
                commonLogRecordManager.saveLog(sta.getCode(), CommonLogRecord.AD_PARAM, sb.toString());
                // log.error(sta.getCode() + "加密前:");
                // log.error(sta.getCode() + "加密后:");
                // log.error(sta.getCode() + "url:");
                commonLogRecordManager.saveLog(sta.getCode(), CommonLogRecord.AD_URL_DATA, param.toString());
                String result = "";
                try {
                    URL url = null;
                    if (null != sta.getOrderType() && !"".equals(sta.getOrderType()) && mp.get(Constants.AD_RETURN_OUTBOUND_KEY).equals(sta.getOrderType())) {
                        url = new URL(mp.get("return_outbound_url"));
                    } else {
                        url = new URL(mp.get("url"));
                    }

                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setRequestProperty("Connection", "close");
                    con.setDoInput(true);
                    con.setDoOutput(true);
                    con.setUseCaches(false);
                    con.setConnectTimeout(60000);
                    con.setReadTimeout(60000);
                    con.setRequestMethod("POST");
                    con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    con.setRequestProperty("Content-Length", Integer.toString(param.length()));
                    OutputStream outputStream = con.getOutputStream();
                    outputStream.write(param.toString().getBytes("UTF-8"));
                    outputStream.close();
                    int responseCode = con.getResponseCode();
                    InputStream inputStream;
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        inputStream = con.getInputStream();
                    } else {
                        inputStream = con.getErrorStream();
                    }
                    BufferedReader reader;
                    String line = null;
                    reader = new BufferedReader(new InputStreamReader(inputStream));
                    while ((line = reader.readLine()) != null) {
                        result += line;
                    }
                    inputStream.close();
                    commonLogRecordManager.saveLog(sta.getCode(), CommonLogRecord.AD_RESPONSE, result);
                    if (log.isDebugEnabled()) {
                        log.debug("ad execute", sta.getCode());
                    }
                } catch (Exception e) {
                    msg = "error";
                    log.error("调用AD接口error,sta id : {}", staId, e);
                    // throw new BusinessException("调用AD接口失败！");
                    throw new BusinessException(ErrorCode.AD_CONFIRM_ERROR, new Object[] {"调用AD接口失败"});
                }
                if (log.isInfoEnabled()) {
                    log.info("storeLogisticsSend http id:" + staId);
                }

                // 换货出调用接口
                if (null != sta.getOrderType() && !"".equals(sta.getOrderType()) && mp.get(Constants.AD_RETURN_OUTBOUND_KEY).equals(sta.getOrderType())) {
                    AdReturnResponse s = JSONUtil.jsonToBean(result, AdReturnResponse.class);
                    if (autoSend) {
                        AdCheckResponse ar = new AdCheckResponse();
                        StoreExchangeConfirmResponse sr = s.getStoreExchangeConfirmResponse();
                        ar.setCreateTime(new Date());
                        ar.setErrCode(sr.getErrCode());
                        ar.setErrMsg(sr.getErrMsg());
                        ar.setPlatformMessage(sta.getOrderType());
                        ar.setIsSuccess(sr.getIsSuccess());
                        ar.setStaCode(sta.getCode());
                        adCheckResponseDao.save(ar);
                        adCheckResponseDao.flush();
                    } else {
                        if (log.isDebugEnabled()) {
                            log.debug("storeLogisticsSend JSON id:" + staId);
                        }
                        if (s == null) {
                            if (log.isDebugEnabled()) {
                                log.debug("接口调不通,sta id :{},msg :{}", staId, result);
                            }
                            msg = "error";
                            throw new BusinessException(ErrorCode.AD_CONFIRM_ERROR, new Object[] {"接口调不通"});
                        } else {
                            if (s.getStoreExchangeConfirmResponse().getIsSuccess() == null || s.getStoreExchangeConfirmResponse().getIsSuccess().equals("false")) {
                                StoreExchangeConfirmResponse sr = s.getStoreExchangeConfirmResponse();
                                // 当返回订单取消，订单不存在或者订单号出错，宝尊直接取消订单
                                if (sr.getErrCode().equals("server.order-not-existed") || sr.getErrCode().equals("server.exchange-not-existed")) {
                                    if (b) {// 预售订单
                                        msg = "error";
                                        if (log.isWarnEnabled()) {
                                            log.warn("ad order cancel ,{}", sta.getSlipCode1());
                                        }
                                        hubWmsManager.cancelSalesSta(mp.get(Constants.AD_SYSTEMKEY), sta.getSlipCode1(), true);
                                        throw new BusinessException(ErrorCode.AD_CONFIRM_ERROR, new Object[] {"AD预售" + sr.getErrMsg()});
                                    }
                                    hubWmsManager.cancelSalesSta(mp.get(Constants.AD_SYSTEMKEY), sta.getSlipCode1(), true);
                                    throw new BusinessException(ErrorCode.AD_CONFIRM_ERROR, new Object[] {sr.getErrMsg()});
                                } else {
                                    msg = "error";
                                    throw new BusinessException(ErrorCode.AD_CONFIRM_ERROR, new Object[] {sr.getErrMsg()});
                                }
                            }

                        }
                    }
                    // 销售出调用接口
                } else {
                    AdResponse s1 = JSONUtil.jsonToBean(result.replace("ErrorResponse", "StoreLogisticsSendResponse"), AdResponse.class);
                    if (autoSend) {
                        AdCheckResponse ar = new AdCheckResponse();
                        StoreLogisticsSendResponse sr = s1.getStoreLogisticsSendResponse();
                        ar.setCreateTime(new Date());
                        ar.setErrCode(sr.getErrCode());
                        ar.setErrMsg(sr.getErrMsg());
                        ar.setPlatformMessage(sr.getPlatformMessage());
                        ar.setIsSuccess(sr.getIsSuccess());
                        ar.setStaCode(sta.getCode());
                        adCheckResponseDao.save(ar);
                        adCheckResponseDao.flush();
                        if (sr.getLines() != null && sr.getLines().size() > 0) {
                            for (ErrLines er : sr.getLines()) {
                                AdCheckResponseLine line = new AdCheckResponseLine();
                                line.setHeadId(ar);
                                line.setCode(er.getCode());
                                line.setLineNo(er.getLineNo());
                                line.setStatus(er.getStatus());
                                adCheckResponseLineDao.save(line);
                            }
                        }
                    }// 如果是定时任务来的，就只做日志记录，拉数据用，否则嵌套在内部流程里面要改单据状态的
                    else {
                        if (log.isDebugEnabled()) {
                            log.debug("storeLogisticsSend JSON id:" + staId);
                        }
                        if (s1 == null) {
                            if (log.isDebugEnabled()) {
                                log.debug("接口调不通,sta id :{},msg :{}", staId, result);
                            }
                            msg = "error";
                            throw new BusinessException(ErrorCode.AD_CONFIRM_ERROR, new Object[] {"接口调不通"});
                        } else {
                            if (s1.getStoreLogisticsSendResponse().getIsSuccess() == null || s1.getStoreLogisticsSendResponse().getIsSuccess().equals("false")) {
                                StoreLogisticsSendResponse sr = s1.getStoreLogisticsSendResponse();
                                // 当返回订单取消，订单不存在或者订单号出错，宝尊直接取消订单
                                if (sr.getErrCode().equals("server.order-has-refund") || sr.getErrCode().equals("server.order-not-existed") || sr.getErrCode().equals("101") || sr.getErrCode().equals("102")
                                        || sr.getErrCode().equals("server.order-not-existed") || sr.getErrCode().equals("server.exchange-not-existed")) {
                                    if (b) {// 预售订单
                                        msg = "error";
                                        if (log.isWarnEnabled()) {
                                            log.warn("ad order cancel ,{}", sta.getSlipCode1());
                                        }
                                        hubWmsManager.cancelSalesSta(mp.get(Constants.AD_SYSTEMKEY), sta.getSlipCode1(), true);
                                        throw new BusinessException(ErrorCode.AD_CONFIRM_ERROR, new Object[] {"AD预售" + sr.getErrMsg()});
                                    }
                                    hubWmsManager.cancelSalesSta(mp.get(Constants.AD_SYSTEMKEY), sta.getSlipCode1(), true);
                                    throw new BusinessException(ErrorCode.AD_CONFIRM_ERROR, new Object[] {sr.getErrMsg()});
                                }// 有子订单处于不能发货状态
                                else if (sr.getErrCode().equals("server.invalid-order-line-status")) {
                                    if (b) {// 预售订单
                                        msg = "error";
                                        throw new BusinessException(ErrorCode.AD_CONFIRM_ERROR, new Object[] {"AD存在订单行不能发货！"});
                                    }
                                    if (sr.getLines() != null) {
                                        List<ErrLines> ls = sr.getLines();
                                        List<String> lines = new ArrayList<String>();
                                        for (ErrLines l : ls) {
                                            if (!(l.getCode().equals("must-send") || l.getCode().equals("has-sent"))) {
                                                lines.add(l.getLineNo());
                                            }
                                        }
                                        outboundInfoManager.cancelStaLine(lines, sta.getId());
                                        throw new BusinessException(ErrorCode.AD_CONFIRM_ERROR, new Object[] {"存在订单行不能发货！"});
                                    }
                                } else if (sr.getErrCode().equals("server.other-invalid-order-status")) {
                                    msg = "server.other-invalid-order-status";
                                    log.error("server.other-invalid-order-status,sta id :{},msg :{}", staId, result);
                                } else if (sr.getErrCode().equals("server.order-has-sent") || sr.getErrCode().equals("server.order-has-part-sent")) {

                                } else if (sr.getErrCode().equals("server.invalid-order-status") || sr.getErrCode().equals("server.invalid-exchange-status")) {
                                    msg = "error";
                                    throw new BusinessException(ErrorCode.AD_CONFIRM_ERROR, new Object[] {sr.getErrMsg()});
                                } else {
                                    msg = "error";
                                    throw new BusinessException(ErrorCode.AD_CONFIRM_ERROR, new Object[] {sr.getErrMsg()});
                                }
                            }

                        }
                    }
                }
            }
        }

        if (log.isInfoEnabled()) {
            log.info("storeLogisticsSend end id:" + staId);
        }
        return msg;

    }
}
