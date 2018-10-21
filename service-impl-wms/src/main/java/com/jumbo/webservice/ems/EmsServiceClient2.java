package com.jumbo.webservice.ems;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.baozun.bh.util.JSONUtil;

import com.jumbo.util.HttpClientUtil;
import com.jumbo.webservice.ems.command.New.Address;
import com.jumbo.webservice.ems.command.New.EmsSignUtil;
import com.jumbo.webservice.ems.command.New.EmsSystemParam;
import com.jumbo.webservice.ems.command.New.Item;
import com.jumbo.webservice.ems.command.New.NewEmsOrderBillNo;
import com.jumbo.webservice.ems.command.New.NewEmsOrderBillNoResponse;
import com.jumbo.webservice.ems.command.New.NewEmsOrderPostInfo;
import com.jumbo.webservice.ems.command.New.NewEmsOrderPrivilegeResponse;
import com.jumbo.webservice.ems.command.New.NewEmsOrderUpdateResponse;
import com.jumbo.webservice.ems.command.New.NewWaybill;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;


public class EmsServiceClient2 {
    protected static final Logger log = LoggerFactory.getLogger(EmsServiceClient2.class);
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static final String appKey = "267e77f81179de231b908c6f05450f0a";// app_key
    private static final String appSecret = "2992fd75f70e7558e645ec503b0147e0";// appSecret

    private static final String EMS_GET_ORDER_NEW = "http://60.205.8.187:18001/api/gateway";


    // 业务类型
    public static final String BUSINESS_TYPE_NORMAL = "1"; // 为标准快递
    public static final String BUSINESS_TYPE_COD = "2";
    public static final String BUSINESS_TYPE_ECONOMY = "4";// 为经济快递
    public static final String BUSINESS_TYPE_ISCOD = "8";// 代收到付
    public static final String CARGO_DESC_BZ = "宝尊商品";
    // 打印类型
    public static final String PRINT_KIND_RM = "2"; // 热敏

    // ///NEW.........................
    public static final String EMS_NEW_SWITCH_CODE = "EMS_NEW_SWITCH_CODE";//
    public static final String EMS_NEW_SWITCH_KEY = "ems_new_switch_key";//

    // public static final String EMS_NEW_SWITCH_VALUE = "";// code



    /**
     * 获取运单号 https
     * 
     * @param ems
     * @return
     */
    public static NewEmsOrderBillNoResponse getBillNo(String type, EmsSystemParam sysParam, Map<String, String> params, String url) {
        try {
            String resultXml = sendHttpPostApply(type, sysParam, params, url);
            NewEmsOrderBillNoResponse response = JSONUtil.jsonToBean(resultXml, NewEmsOrderBillNoResponse.class);
            return response;
        } catch (BusinessException e) {
            if (log.isErrorEnabled()) {
                log.error("ems getBillNo fail,error {}", e.getErrorCode());
            }
            throw e;
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("ems getBillNo", e);
            }
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
    }

    /**
     * ems获取验证码等
     */

    public static NewEmsOrderPrivilegeResponse getBillPrivilege(String type, EmsSystemParam sysParam, Map<String, String> params, String url) {
        try {
            String resultXml = sendHttpPostApply(type, sysParam, params, url);
            NewEmsOrderPrivilegeResponse response = JSONUtil.jsonToBean(resultXml, NewEmsOrderPrivilegeResponse.class);
            return response;
        } catch (Exception e) {
            log.error("", e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
    }

    /**
     * ems运单号回传 https
     */

    public static NewEmsOrderUpdateResponse sendEmsOrderDatas(String type, EmsSystemParam sysParam, Map<String, String> params, String url) {
        try {
            String resultXml = sendHttpPostApply(type, sysParam, params, url);
            NewEmsOrderUpdateResponse response = JSONUtil.jsonToBean(resultXml, NewEmsOrderUpdateResponse.class);
            return response;
        } catch (Exception e) {
            log.error("", e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
    }



    /**
     * 3.1.1.2.应用获取用户授权接口(根据授权验证码获取授权码)
     * 
     * @param ems
     * @return
     */
    public static String getAuthorization01(String token, EmsSystemParam sysParam) {
        try {
            String resultJson = HttpClientUtil.httpPost(EMS_GET_ORDER_NEW, getParamsAuthorization01(token, sysParam));
//            String resultXml = new String(Base64.decodeBase64(resultJson.getBytes("UTF-8")), "UTF-8");
            return resultJson;
        } catch (Exception e) {
            log.error("", e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
    }

    // 3.1.1.2.应用获取用户授权接口(根据授权验证码获取授权码)
    private static Map<String, String> getParamsAuthorization01(String token, EmsSystemParam sysParam) throws UnsupportedEncodingException {// 封装ems运单号获取数据
        String dateString = sdf.format(new Date());
        Map<String, String> params = new HashMap<String, String>();
        // //封装系统参数
        params.put("sign", sysParam.getSign());
        params.put("timestamp", dateString);
        params.put("version", sysParam.getVersion());
        params.put("method", sysParam.getMethod());
        params.put("partner_id", sysParam.getPartner_id());
        params.put("format", sysParam.getFormat());
        params.put("app_key", sysParam.getApp_key());
        params.put("charset", sysParam.getCharset());
        params.put("authorization", sysParam.getAuthorization());
        // 封装接口参数
        params.put("token", token);
        String sign = EmsSignUtil.getSign(params, sysParam.getCharset(), sysParam.getApp_secret());// 获取签名
        params.put("sign", sign);
        log.debug("getParamsAuthorization01 send=>" + params);
        return params;
    }



    /**
     * 获取运单号 发送请求
     */
    private static String sendHttpPostApply(String type, EmsSystemParam sysParam, Map<String, String> params, String url) throws UnsupportedEncodingException {
        String resultJson = HttpClientUtil.httpPost(url, getParamsApply(sysParam, params, type));
        // System.out.println("反馈：" + resultJson);
        log.debug("sendHttpPostApply return type=" + type + ":" + resultJson);
        return resultJson;
    }

    /**
     * ems运单回传 发送请求
     */
    // private static String sendHttpEmsOrderDatas(EmsSystemParam sysParam, Map<String, String>
    // params, String url) throws UnsupportedEncodingException {
    // String resultJson = HttpClientUtil.httpPost(url, getParamsApply(sysParam, params));
    // System.out.println("反馈：" + resultJson);
    // log.debug("sendHttpEmsOrderDatas return :" + resultJson);
    // return resultJson;
    // }



    // 获取运单号 获取签名和参数
    private static Map<String, String> getParamsApply(EmsSystemParam sysParam, Map<String, String> params, String type) throws UnsupportedEncodingException {// 封装ems运单号获取数据
        String dateString = sdf.format(new Date());
        // Map<String, String> params = new HashMap<String, String>();
        // //封装系统参数
        params.put("sign", sysParam.getSign());
        params.put("timestamp", dateString);
        params.put("version", sysParam.getVersion());
        params.put("method", sysParam.getMethod());
        params.put("partner_id", sysParam.getPartner_id());
        params.put("format", sysParam.getFormat());
        params.put("app_key", sysParam.getApp_key());
        params.put("charset", sysParam.getCharset());
        params.put("authorization", sysParam.getAuthorization());
        // 封装接口参数
        // params.put("count", ems.getCount());
        // params.put("bizcode", ems.getBizcode());

        String sign = EmsSignUtil.getSign(params, sysParam.getCharset(), sysParam.getApp_secret());// 获取签名
        params.put("sign", sign);
        // System.out.println("请求:" + params);
        log.debug("sendOrderToEMS send type=" + type + ":" + params);
        return params;
    }

    // 封装请求参数
    public static EmsSystemParam initEmsSystemParam(String sign, String timestamp, String version, String method, String partner_id, String format, String app_key, String charset, String authorization, String app_secret) {
        EmsSystemParam en = new EmsSystemParam();
        en.setSign(sign);
        en.setTimestamp(timestamp);
        en.setVersion(version);
        en.setMethod(method);
        en.setPartner_id(partner_id);
        en.setFormat(format);
        en.setApp_key(app_key);
        en.setCharset(charset);
        en.setAuthorization(authorization);
        en.setApp_secret(app_secret);
        return en;
    }

    // ///////////////////////////////////////////////////////测试/////////////////////////////////////////////////////////////////////////////////
    /*****
     * 测试 **********************************************************************************
     *****/

    // 获取有单号test
    private static void getNo() {
        Map<String, String> params = new HashMap<String, String>();
        NewEmsOrderBillNo ems = new NewEmsOrderBillNo();
        ems.setCount("2");
        ems.setBizcode("06");// 08 cod 06 标准
        params.put("count", ems.getCount());
        params.put("bizcode", ems.getBizcode());

        EmsSystemParam en = initEmsSystemParam(null, null, "V3.01", "ems.inland.mms.mailnum.apply", null, "json", "267e77f81179de231b908c6f05450f0a", "UTF-8", "522bc37cc5f558ce445911b0c8fc1bdd", "2992fd75f70e7558e645ec503b0147e0");
        NewEmsOrderBillNoResponse re = EmsServiceClient2.getBillNo("04", en, params, "");
        List<String> mailnums = re.getMailnums();
        for (String str : mailnums) {
            System.out.println(str);
        }
        System.out.println(re.getErrorMsg());
    }

    // 根据token来获取flashToken {3.1.1.2.应用获取用户授权接口(根据授权验证码获取授权码)}
    private static void getAuthorization01() {
        Map<String, String> params = new HashMap<String, String>();
        NewEmsOrderBillNo ems = new NewEmsOrderBillNo();
        // ems.setCount("2");
        // ems.setBizcode("06");
        params.put("token", "ccf6fe65fa737348cfcb9b9818694050");
        EmsSystemParam en = initEmsSystemParam(null, null, "V3.01", "ems.permission.user.permit.get", null, "json", appKey, "UTF-8", null, appSecret);
        NewEmsOrderBillNoResponse re = EmsServiceClient2.getBillNo("01", en, params, "");
        System.out.println(re.getErrorMsg());
    }

    // 根据authorization来刷新flashToken {3.1.1.3.应用刷新用户授权接口(根据authorization刷新flashToken)}
    private static void getAuthorization02() {
        Map<String, String> params = new HashMap<String, String>();
        NewEmsOrderBillNo ems = new NewEmsOrderBillNo();
        // params.put("authorization", "b9599e549193ec9c23be562c10152cee");
        EmsSystemParam en = initEmsSystemParam(null, null, "V3.01", "ems.permission.user.permit.refresh.token", null, "json", appKey, "UTF-8", "522bc37cc5f558ce445911b0c8fc1bdd", appSecret);
        NewEmsOrderBillNoResponse re = EmsServiceClient2.getBillNo("03", en, params, "");
        System.out.println(re.getErrorMsg());
    }

    // 根据应用根据flashToken刷新authorization {3.1.1.4.应用刷新用户授权接口(根据flashToken刷新authorization)}
    private static void getAuthorization03() {
        Map<String, String> params = new HashMap<String, String>();
        NewEmsOrderBillNo ems = new NewEmsOrderBillNo();
        params.put("flashToken", "7d05a851296d9afee0b0bb1f159e6be1");
        EmsSystemParam en = initEmsSystemParam(null, null, "V3.01", "ems.permission.user.permit.refresh.authorization", null, "json", appKey, "UTF-8", null, appSecret);
        NewEmsOrderBillNoResponse re = EmsServiceClient2.getBillNo("02", en, params, EMS_GET_ORDER_NEW);
        System.out.println(re.getErrorMsg());
    }



    // 运单号回传
    private static void sendEmsOrderDatas() {
        List<Item> items = new ArrayList<Item>();
        List<NewWaybill> waybills = new ArrayList<NewWaybill>();
        NewEmsOrderPostInfo info = new NewEmsOrderPostInfo();
        NewWaybill bill = new NewWaybill();
        Item item = new Item();
        Address sender = new Address();// 寄件人信息
        Address receiver = new Address();// 收件人信息
        // 封装 bill
        bill.setTxLogisticID("S600034018228");// 物流订单号(一票多单必填)
        bill.setOrderNo("GOZR1704000072");// 电商订单号
        bill.setMailNum("1100165146700_TEST");// 物流运单号(一票多单主单号)
        bill.setSubMails("");// 一票多单子单号，以“,”(半角逗号)分隔，非一票多单不填
        bill.setYpdjpayment(0);// 一票多件付费方式（1-集中主单计费 2-平均重量计费 3-分单免首重4-主分单单独计费）
        bill.setOrderType("06");// 订单类型(1-普通订单)
        bill.setServiceType(0L);// 产品代码，0-经济快递 1-标准快递
        bill.setRemark(null);// 备注
        bill.setWeight(2000L);// 实际重量，单位：克
        bill.setVolumeWeight(0L);// 体积重量，单位：克
        bill.setFeeWeight(0L);// 计费重量，单位：克
        bill.setInsuredAmount(0L);// 保险金额，单位：分
        // bill.setCustCode("1");// EMS客户代码
        // bill.setDeliveryTime("1");// 投递时间(yyyy-mm-dd hh:mm:ss)
        bill.setReceiverPay(0L);// 收件人付费
        // bill.setCollectionMoney(2L);// 代收货款
        bill.setRevertBill("");// 是否返单，0：返单，1：不返单
        bill.setRevertMailNo("");// 反向运单号
        bill.setPostage(0L);// 邮费
        bill.setSendType("");// 寄递类型，0：单程寄递，1：双程后置去程，2：双程后置返程(公安项目专用)
        // bill.setCommodityMoney(2L);// 商品金额(工本费)
        bill.setState("");// 自定义标识

        // 封装sender
        sender.setName("run1");// 用户姓名
        sender.setPostCode("344534");// 用户邮编
        sender.setPhone("");// 用户电话，包括区号、电话号码及分机号，中间用“-”分隔；
        sender.setMobile("021-2612061sdfsdf3");// 用户电话，包括区号、电话号码及分机号，中间用“-”分隔；
        sender.setProv("上海青浦区徐泾镇明珠路928号B1栋四楼高绮诗物流");// 用户所在省，使用国标全称
        sender.setCity("上海青浦区徐泾镇明珠路928号B1栋四楼高绮诗物流");// 用户所在市，使用国标全称
        sender.setCounty("");// 用户所在县（区），使用国标全称
        sender.setAddress("江苏省  徐州市沛县");//
        // 封装receiver
        receiver.setName("run2");// 用户姓名
        receiver.setPostCode("344534");// 用户邮编
        receiver.setPhone("");// 用户电话，包括区号、电话号码及分机号，中间用“-”分隔；
        receiver.setMobile("021-2612061sdfsdf3");// 用户电话，包括区号、电话号码及分机号，中间用“-”分隔；
        receiver.setProv("江苏省");// 用户所在省，使用国标全称
        receiver.setCity("徐州市");// 用户所在市，使用国标全称
        receiver.setCounty("沛县");// 用户所在县（区），使用国标全称
        receiver.setAddress("江苏省徐州市沛县");//

        bill.setSender(sender);// 寄件人信息
        bill.setReceiver(receiver);// 收件人信息
        // 封装item
        item.setItemName("商品01");// 商品名称
        item.setItemValue(0L);// 数量
        item.setItemValue(0L);
        items.add(item);
        bill.setItems(items);
        waybills.add(bill);
        info.setWaybills(waybills);
        //
        Map<String, String> params = new HashMap<String, String>();
        JSONObject json = JSONObject.fromObject(info);// 将java对象转换为json对象
        String str = json.toString();// 将json对象转换为字符串
        params.put("waybill", str);
        params.put("size", "1");
        EmsSystemParam en = initEmsSystemParam(null, null, "V3.01", "ems.inland.waybill.create.normal", null, "json", appKey, "UTF-8", "522bc37cc5f558ce445911b0c8fc1bdd", appSecret);
        NewEmsOrderUpdateResponse re = sendEmsOrderDatas("05", en, params, "");
        System.out.println(re.getSuccess());

    }



    public static void main(String[] args) throws UnsupportedEncodingException {
        // getNo();// 获取有单号test
        // getAuthorization01();// 根据token来获取 flashToken
        getAuthorization02();// 根据authorization来刷新flashToken
        // getAuthorization03();// 根据应用根据flashToken刷新authorization
        // sendEmsOrderDatas();// 运单号回传
    }
}
