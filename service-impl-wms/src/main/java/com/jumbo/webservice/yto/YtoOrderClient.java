package com.jumbo.webservice.yto;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jumbo.mq.MarshallerUtil;
import com.jumbo.util.HttpClientUtil;
import com.jumbo.util.StringUtils;
import com.jumbo.util.zip.AppSecretUtil;
import com.jumbo.webservice.yto.command.UploadOrdersRequest;
import com.jumbo.webservice.yto.command.UploadOrdersResponse;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;

public class YtoOrderClient {
    /**
	 * 
	 */
    protected static final Logger log = LoggerFactory.getLogger(YtoOrderClient.class);
    public static final String PROVIDER_NO_TRANS_REASON = "Sorry, you don't have enough waybills. Please contact the related site as soon as possible";
    public static final String ILLEGAL_DATA_DIGEST_RESON = "illegal data digest, please check your secret key";

    /**
     * 上传订单
     * 
     * @param request
     * @return
     */
    public static UploadOrdersResponse sendOutboundOrderToYto(UploadOrdersRequest request, String ytoUploadOrdersUrl, String ytoPassword, String ytoClientID) {
        try {
            log.debug(MarshallerUtil.buildJaxb(request));
            String resultXml = sendHttpPost(MarshallerUtil.buildJaxb(request), ytoUploadOrdersUrl, ytoPassword, ytoClientID);
            log.debug(resultXml);
            return (UploadOrdersResponse) MarshallerUtil.buildJaxb(UploadOrdersResponse.class, resultXml);
        } catch (Exception e) {
            log.error("", e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
    }

    /**
     * 发送请求
     * 
     * @param xml 请求数据
     * @param url 请求地址
     * @return 请求结
     * @throws UnsupportedEncodingException
     */
    private static String sendHttpPost(String xml, String url, String ytoPassword, String ytoClientID) throws UnsupportedEncodingException {
        log.debug(xml);
        xml = StringUtils.trimLeadingWhitespace(StringUtils.delete(xml, "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"));
        String encodeStr = new Base64().encodeToString(AppSecretUtil.getMD5(xml + ytoPassword));
        Map<String, String> params = new HashMap<String, String>();
        params.put("logistics_interface", xml);
        params.put("data_digest", encodeStr);
        params.put("clientId", ytoClientID);
        String resultXml = HttpClientUtil.httpPost(url, params);
        log.debug(resultXml);
        return resultXml;
    }

}
