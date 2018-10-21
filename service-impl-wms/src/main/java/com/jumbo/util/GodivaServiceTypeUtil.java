package com.jumbo.util;

import java.io.IOException;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GodivaServiceTypeUtil {


    protected static final Logger log = LoggerFactory.getLogger(GodivaServiceTypeUtil.class);

    @SuppressWarnings("deprecation")
    public static String sendMsgtoIds(String data, String url) {
        String result = "";
        PostMethod method = new PostMethod(url);
        if (data != null) method.setRequestBody(data);
        method.getParams().setContentCharset("UTF-8");
        HttpClient httpClient = new HttpClient();
        try {
            httpClient.executeMethod(method);
            result = method.getResponseBodyAsString();
        } catch (HttpException e) {
            log.error("", e);
        } catch (IOException e) {
            log.error("", e);
        }
        return result;
    }



}
