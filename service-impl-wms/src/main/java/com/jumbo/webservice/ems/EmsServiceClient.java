package com.jumbo.webservice.ems;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jumbo.mq.MarshallerUtil;
import com.jumbo.util.HttpClientUtil;
import com.jumbo.webservice.ems.command.EmsOrderBillNo;
import com.jumbo.webservice.ems.command.EmsOrderBillNoResponse;
import com.jumbo.webservice.ems.command.EmsOrderPostInfo;
import com.jumbo.webservice.ems.command.EmsOrderUpdate;
import com.jumbo.webservice.ems.command.EmsOrderUpdateResponse;
import com.jumbo.webservice.ems.command.EmsTestHtp;
import com.jumbo.webservice.ems.command.EmsTestHtpResponse;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;

public class EmsServiceClient {
    protected static final Logger log = LoggerFactory.getLogger(EmsServiceClient.class);
    // public static final String EMS_CREARE_ORDER =
    // "http://211.156.219.132:8081/zkweb/bigaccount/getBigAccountDataAction.do?method=getPrintDatas&xml=";

    private static final String EMS_UPDATE_ORDER = "http://211.156.219.132:8081/zkweb/bigaccount/getBigAccountDataAction.do?method=updatePrintWeight";

    private static final String EMS_GET_ORDER = "http://211.156.219.132:8081/zkweb/bigaccount/getBigAccountDataAction.do?method=getBillNumBySys";

    private static final String EMS_UPDATE_PRINT_DATAS = "http://211.156.219.132:8081/zkweb/bigaccount/getBigAccountDataAction.do?method=updatePrintDatas";

    private static final String EMS_TEST_HTP_OK = "http://211.156.219.132:8081/zkweb/bigaccount/getBigAccountDataAction.do?method=testHtpOk";


    // 业务类型
    public static final String BUSINESS_TYPE_NORMAL = "1"; // 为标准快递
    public static final String BUSINESS_TYPE_COD = "2";
    public static final String BUSINESS_TYPE_ECONOMY = "4";// 为经济快递

    public static final String BUSINESS_TYPE_ISCOD = "8";// 代收到付

    public static final String CARGO_DESC_BZ = "宝尊商品";
    // 大客户号
    // public static final String SYS_ACCOUNT_BZ = "A1234567890Z";
    // public static final String PASSWORD_MD5_BZ = "e10adc3949ba59abbe56e057f20f883e";
    // 打印类型
    public static final String PRINT_KIND_RM = "2"; // 热敏



    /**
     * 跟新订单接口
     * 
     * @param info
     * @return
     */
    public static EmsOrderUpdateResponse updateEmsOrder(EmsOrderUpdate info) {
        try {
            String resultXml = sendHttpPost(MarshallerUtil.buildJaxb(info), EMS_UPDATE_ORDER);
            return (EmsOrderUpdateResponse) MarshallerUtil.buildJaxb(EmsOrderUpdateResponse.class, resultXml);
        } catch (UnsupportedEncodingException e) {
            log.error("", e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
    }

    /**
     * 获取运单号
     * 
     * @param ems
     * @return
     */
    public static EmsOrderBillNoResponse getBillNo(EmsOrderBillNo ems) {
        try {
            String resultXml = sendHttpPost(MarshallerUtil.buildJaxb(ems), EMS_GET_ORDER);
            return (EmsOrderBillNoResponse) MarshallerUtil.buildJaxb(EmsOrderBillNoResponse.class, resultXml);
        } catch (Exception e) {
            log.error("", e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
    }


    /**
     * 详情单打印信息更新到EMS自助服务系统
     * 
     * @param info
     * @return
     */
    public static EmsOrderUpdateResponse sendEmsOrderDatas(EmsOrderPostInfo ems) {
        try {
            String postXml = MarshallerUtil.buildJaxb(ems, "GBK");
            log.debug(postXml);
            String resultXml = sendHttpPost(postXml, EMS_UPDATE_PRINT_DATAS);
            log.debug(resultXml);
            return (EmsOrderUpdateResponse) MarshallerUtil.buildJaxb(EmsOrderUpdateResponse.class, resultXml);
        } catch (UnsupportedEncodingException e) {
            log.error("", e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
    }

    /**
     * 发送请求
     * 
     * @param xml 请求数据
     * @param url 请求地址
     * @return 请求结果
     * @throws UnsupportedEncodingException
     */
    private static String sendHttpPost(String xml, String url) throws UnsupportedEncodingException {
        log.debug(xml);
        String base64Str = new String(Base64.encodeBase64(xml.getBytes("UTF-8")), "UTF-8");
        Map<String, String> params = new HashMap<String, String>();
        params.put("xml", base64Str);
        String rtn = HttpClientUtil.httpPost(url, params);
        String resultXml = new String(Base64.decodeBase64(rtn.getBytes("UTF-8")), "UTF-8");
        log.debug(resultXml);
        return resultXml;
    }


    /**************************************************** 测试 **************************************************************************/
    /**
     * 调试测试接口
     * 
     * @param ems
     * @return
     */
    public static EmsTestHtpResponse testHtpOK(EmsTestHtp ems) {
        try {
            String resultXml = sendHttpPost(MarshallerUtil.buildJaxb(ems), EMS_TEST_HTP_OK);
            return (EmsTestHtpResponse) MarshallerUtil.buildJaxb(EmsTestHtpResponse.class, resultXml);
        } catch (UnsupportedEncodingException e) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
    }

    public static void main(String[] args) {
        // EmsTestHtp test = new EmsTestHtp();
        // test.setSysAccount(SYS_ACCOUNT_BZ);
        // //test.setBigAccountDataId("ABCDEFG");
        // EmsTestHtpResponse response = testHtpOK(test);
        // System.out.println(response.getBigAccountDataId());
    }
}
