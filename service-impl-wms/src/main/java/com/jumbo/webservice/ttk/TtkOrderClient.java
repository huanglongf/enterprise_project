/**
 * Copyright (c) 2010 Jumbomart All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of Jumbomart. You shall not
 * disclose such Confidential Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with Jumbo.
 * 
 * JUMBOMART MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF THE SOFTWARE, EITHER
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE, OR NON-INFRINGEMENT. JUMBOMART SHALL NOT BE LIABLE FOR ANY
 * DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES.
 */
package com.jumbo.webservice.ttk;

import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.baozun.bh.util.JSONUtil;

import com.jumbo.util.HttpClientUtil;
import com.jumbo.util.JsonUtil;
import com.jumbo.util.StringUtil;
import com.jumbo.util.UrlUtil;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.model.warehouse.StaDeliveryInfoCommand;
import com.taobao.api.internal.util.codec.Base64;

/**
 * @author lichuan
 *
 */
public class TtkOrderClient {
    private static final Logger log = LoggerFactory.getLogger(TtkOrderClient.class);
    public static final String MSG_TYPE_ORDER_CREATE = "ORDERCREATE";
    public static final String TP = "TTKDEX";
    public static final String STATUS_SUCCESS = "0";
    public static final String GET_ARG_PI = "手机接口";
    public static final String GET_ARG_TRANS_NO = "通用_提取空白单号";
    public static final String GET_ARG_TRANS_BIG_WORD = "通用_取大头笔";
    public static final String POST_ARG_TRANS_BIG_WORD = "取大头笔";
    
    /**
     * 获取运单号段
     *@param getCount 获取数量
     *@param ttkSite 网点
     *@param ttkCus  客户
     *@param ttkPsw 密码
     *@param ttkUrl 资源地址
     *@return
     *@throws UnsupportedEncodingException TransNoSegmentsResponse 
     *@throws
     */
    public static TransNoSegmentsResponse getTransNoSegments(Integer getCount,String ttkSite,String ttkCus,String ttkPsw,String ttkUrl) throws UnsupportedEncodingException {
        if(log.isDebugEnabled()){
            log.debug("====>TTK,call getTransNoSegments interface begain");
        }
        if (0 >= getCount) {
            return null;
        }
        StringBuilder requestUrl = new StringBuilder();
        StringBuilder params = new StringBuilder();
        String site = ttkSite;
        String username = ttkCus;
        String password = ttkPsw;
        String url = ttkUrl;
        String count = getCount.toString();
        if (StringUtil.isEmpty(site) || StringUtil.isEmpty(username) || StringUtil.isEmpty(password) || StringUtil.isEmpty(url)) {
            if(log.isDebugEnabled()){
                log.debug("====>TTK,call getTransNoSegments interface request params has null error");
            }
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        String arg1 = encode(TtkOrderClient.GET_ARG_PI);
        String arg2 = encode(TtkOrderClient.GET_ARG_TRANS_NO);
        site = encode(site);
        username = encode(username);
        password = encode(password);
        count = encode(count);
        params.append("?arg1=").append(arg1)
              .append("&arg2=").append(arg2)
              .append("&arg3=").append("{site:").append(site).append(",")
              .append("cus:").append(username).append(",")
              .append("password:").append(password).append(",")
              .append("getcount:").append(count).append("}");
        requestUrl.append(url).append(params.toString().replace("\"", "%22").replace("{", "%7b").replace("}", "%7d"));
        // System.out.println(requestUrl.toString());
        String response = HttpClientUtil.httpGet(requestUrl.toString());
        if(log.isDebugEnabled()){
            log.debug("====>TTK,call getTransNoSegments interface end, response is:[{}]", response);
        }
        return JSONUtil.jsonToBean(response, TransNoSegmentsResponse.class);
    }
    
    /**
     * 获取大头笔信息（get方式）
     *@param deliveryInfos 运单信息
     *@param ttkSite 网点
     *@param ttkCus  客户
     *@param ttkPsw 密码
     *@param ttkUrl 资源地址
     *@return
     *@throws UnsupportedEncodingException TransBigWordResponse 
     *@throws
     */
    public static TransBigWordResponse getTransBigWordByDeliveryInfo(List<StaDeliveryInfoCommand> deliveryInfos,String ttkSite,String ttkCus,String ttkPsw,String ttkUrl) throws UnsupportedEncodingException {
        if (0 == deliveryInfos.size()) {
            return null;
        }
        StringBuilder requestUrl = new StringBuilder();
        StringBuilder params = new StringBuilder();
        TransBigWordRequest req = new TransBigWordRequest();
        StringBuilder sb = new StringBuilder();
        String site = ttkSite;
        String username = ttkCus;
        String password = ttkPsw;
        String url = ttkUrl;
        if (StringUtil.isEmpty(site) || StringUtil.isEmpty(username) || StringUtil.isEmpty(password) || StringUtil.isEmpty(url)) {
            if(log.isDebugEnabled()){
                log.debug("====>TTK,call getTransBigWordByDeliveryInfo interface request params has null error");
            }
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        String arg1 = encode(TtkOrderClient.GET_ARG_PI);
        String arg2 = encode(TtkOrderClient.GET_ARG_TRANS_BIG_WORD);
        req.setStie(encode(site));
        req.setCus(encode(username));
        req.setPassword(encode(password));
        for (StaDeliveryInfoCommand di : deliveryInfos) {
            String trackNo = di.getTrackingNo();
            String prov = di.getProvince();
            String city = di.getCity();
            String area = di.getDistrict();
            if (!StringUtil.isEmpty(trackNo)) {
                TransBigWordSender sent = new TransBigWordSender();
                sent.setKey(encode(trackNo));
                sent.setProv(encode(prov));
                sent.setCity(encode(city));
                sent.setArea(encode(area));
                sb.append(trackNo);
                req.getData().add(sent);
            }
        }
        String arg3 = JsonUtil.obj2jsonStrIncludeAll(req);
        arg3 = arg3.replace("\"", "%22").replace("{", "%7b").replace("}", "%7d");
        params.append("?arg1=").append(arg1)
              .append("&arg2=").append(arg2)
              .append("&arg3=").append(arg3);
        requestUrl.append(url).append(params);
        if(log.isDebugEnabled()){
            log.debug("====>TTK,call getTransBigWordByDeliveryInfo interface begain,request param key is:[{}]", sb.toString());
        }
        String response = HttpClientUtil.httpGet(requestUrl.toString());
        if(log.isDebugEnabled()){
            log.debug("====>TTK,call getTransBigWordByDeliveryInfo interface end, response is:[{}]", response);
        }
        response = response.replaceAll("package", "pack");
        return JSONUtil.jsonToBean(response, TransBigWordResponse.class);
    }
    
    /**
     * 获取大头笔信息（post方式）
     *@param deliveryInfos 运单信息
     *@param ttkSite 网点
     *@param ttkCus  客户
     *@param ttkPsw 密码
     *@param ttkUrl 资源地址
     *@return
     *@throws UnsupportedEncodingException TransBigWordResponse 
     *@throws
     */
    public static TransBigWordResponse postTransBigWordByDeliveryInfo(List<StaDeliveryInfoCommand> deliveryInfos,String ttkSite,String ttkCus,String ttkPsw,String ttkUrl) throws UnsupportedEncodingException {
        if (0 == deliveryInfos.size()) {
            return null;
        }
        TransBigWordRequest req = new TransBigWordRequest();
        StringBuilder sb = new StringBuilder();
        String site = ttkSite;
        String username = ttkCus;
        String password = ttkPsw;
        String url = ttkUrl;
        if (StringUtil.isEmpty(site) || StringUtil.isEmpty(username) || StringUtil.isEmpty(password) || StringUtil.isEmpty(url)) {
            if(log.isDebugEnabled()){
                log.debug("====>TTK,call getTransBigWordByDeliveryInfo interface request params has null error");
            }
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        String serviceCode = TtkOrderClient.POST_ARG_TRANS_BIG_WORD;
        req.setStie(site);
        req.setCus(username);
        req.setPassword(password);
        for (StaDeliveryInfoCommand di : deliveryInfos) {
            String trackNo = di.getTrackingNo();
            String prov = di.getProvince();
            String city = di.getCity();
            String area = di.getDistrict();
            if (!StringUtil.isEmpty(trackNo)) {
                TransBigWordSender sent = new TransBigWordSender();
                sent.setKey(trackNo);
                sent.setProv(prov);
                sent.setCity(city);
                sent.setArea(area);
                sb.append(trackNo);
                req.getData().add(sent);
            }
        }
        String params = JsonUtil.obj2jsonStrIncludeAll(req);
        Map<String, String> reqParam = new HashMap<String, String>();
        reqParam.put("serviceCode", serviceCode);
        reqParam.put("params", params);
        if(log.isDebugEnabled()){
            log.debug("====>TTK,call postTransBigWordByDeliveryInfo interface begain,request param key is:[{}]", sb.toString());
        }
        String response = HttpClientUtil.httpPost(url, reqParam);
        if(log.isDebugEnabled()){
            log.debug("====>TTK,call postTransBigWordByDeliveryInfo interface end, response is:[{}]", response);
        }
        response = response.replaceAll("package", "pack");
        return JSONUtil.jsonToBean(response, TransBigWordResponse.class);
    }

    private static String encode(String uriComponent) throws UnsupportedEncodingException {
        return UrlUtil.encode(uriComponent);
    }
    
    /**
     * 下单接口
     *@param orderInfo 订单信息
     *@param ttkUrl 资源地址
     *@param parternId 签名附加序列
     *@return boolean 
     *@throws
     */
    public static boolean comfirmOrder(RequestOrder orderInfo, String ttkUrl, String parternId) {
        boolean ret = false;
        String req = marshallerRequestOrderInfo(orderInfo);
        if (null == orderInfo) {            
            if(log.isDebugEnabled()){
                log.debug("====>TTK,call comfirmOrder interface request param null error");
            }
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        if (StringUtil.isEmpty(orderInfo.getTxLogisticID()) || StringUtil.isEmpty(orderInfo.getMailNo()) || StringUtil.isEmpty(ttkUrl)) {
            if(log.isDebugEnabled()){
                log.debug("====>TTK,call comfirmOrder interface request param [txLogisticID] or [mailNo] or [ttkUrl] has null error");
            }
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        String digest = req + parternId;
        String dataDigest = new String(Base64.encodeBase64(DigestUtils.md5(digest.getBytes(Charset.forName("GBK")))), Charset.forName("GBK"));;
        Map<String, String> reqParam = new HashMap<String, String>();
        reqParam.put("logistics_interface", req);
        reqParam.put("logistic_provider_id", TtkOrderClient.TP);
        reqParam.put("msg_type", TtkOrderClient.MSG_TYPE_ORDER_CREATE);
        reqParam.put("data_digest", dataDigest);
        if(log.isDebugEnabled()){
            log.debug("====>TTK,call comfirmOrder interface begain,staCode is:[{}]", orderInfo.getTxLogisticID());
        }
        String charset = "GBK";
        String response = HttpClientUtil.httpPost(ttkUrl, reqParam, charset);
        if(log.isDebugEnabled()){
            log.debug("====>TTK,call comfirmOrder interface end, staCode is:[{}], response is:[{}]", orderInfo.getTxLogisticID(), response);
        }
        Responses resp = unmarshallerResponseInfo(response);
        if (null != resp) {
            ResponseItems items = resp.getResponseItems();
            if (null != items) {
                Response res = items.getResponse();
                if (null != res) {
                    if (res.isSuccess() && (res.getTxLogisticID()).equals(orderInfo.getTxLogisticID())) {
                        ret = true;
                    }
                }
            }
        }
        return ret;
    }

    private static String marshallerRequestOrderInfo(RequestOrder order) {
        try {
            // 生成xml
            JAXBContext jaxbContext = JAXBContext.newInstance(RequestOrder.class);
            StringWriter sw = new StringWriter();
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
            marshaller.marshal(order, sw);
            return sw.toString();
        } catch (Exception e) {
            return null;
        }
    }

    private static Responses unmarshallerResponseInfo(String response) {
        try {
            if (null == response) {
                return null;
            }
            JAXBContext cxt = JAXBContext.newInstance(Responses.class);
            Unmarshaller unmarshaller = cxt.createUnmarshaller();
            return (Responses) unmarshaller.unmarshal(new ByteArrayInputStream(response.getBytes()));
        } catch (Exception e) {
            return null;
        }
    }
    
}