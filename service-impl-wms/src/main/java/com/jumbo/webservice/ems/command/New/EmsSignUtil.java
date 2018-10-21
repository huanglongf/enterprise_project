package com.jumbo.webservice.ems.command.New;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jumbo.util.StringUtil;


public class EmsSignUtil {
    protected static final Logger log = LoggerFactory.getLogger(EmsSignUtil.class);

    /**
     * 计算签名
     * 
     * @param content
     * @return
     */
    public static String sign(String content, String charset) {
        if (charset == null || "".equals(charset)) {
            charset = "utf-8";
        }
        String sign = "";
        try {
            byte[] str = EMSAppSecretUtil.getMD5(content);
            sign = new String(Base64.encodeBase64(str), charset);
        } catch (UnsupportedEncodingException e) {
            if (log.isErrorEnabled()) {
                log.error("EmsSifnUtil UnsupportedEncodingException", e);
            }
        }
        return sign;
    }

    /**
     * 根据参数名排序并拼装字符串
     * 
     * @param params
     * @return
     */
    public static String getSortParams(Map<String, String> params) {
        System.out.println(params);
        if (params == null || params.isEmpty()) {
            return "";
        }
        // 删掉sign参数
        params.remove("sign");
        String contnt = "";
        Set<String> keySet = params.keySet();
        List<String> keyList = new ArrayList<String>();
        for (String key : keySet) {
            String value = params.get(key);
            // 将值为空的参数排除
            if (!StringUtil.isEmpty(value)) {
                keyList.add(key);
            }
        }
        Collections.sort(keyList, new Comparator<String>() {

            @Override
            public int compare(String o1, String o2) {
                int length = Math.min(o1.length(), o2.length());
                for (int i = 0; i < length; i++) {
                    char c1 = o1.charAt(i);
                    char c2 = o2.charAt(i);
                    int r = c1 - c2;
                    if (r != 0) {
                        // char值小的排前边
                        return r;
                    }
                }
                // 2个字符串关系是str1.startsWith(str2)==true
                // 取str2排前边
                return o1.length() - o2.length();
            }
        });
        for (int i = 0; i < keyList.size(); i++) {
            String key = keyList.get(i);
            contnt += key + params.get(key);
        }
        return contnt;
    }

    /**
     * 计算签名
     * 
     * @param params
     * @param charset
     * @return
     */
    public static String getSign(Map<String, String> params, String charset, String appSecret) {
        String content = getSortParams(params) + appSecret;
        // System.out.println("排序后=" + content);
        String result = sign(content, charset);
        // System.out.println("加密sign=" + result);
        return result;
    }

    public static void main(String[] args) {// 测试代码
        Map<String, String> params = new HashMap<String, String>();
        params.put("sign", "");
        params.put("timestamp", "2014-09-03 17:05:05");
        params.put("version", "V0.1");
        params.put("method", "ems.permission.user.permit.get");
        params.put("format", "json");
        params.put("token", "13jk21s87921j3kl12l");
        params.put("partner_id", "API_SDK_TEST_V0.1");
        params.put("authorization", "");
        params.put("app_key", "TEST1");
        String result = getSign(params, null, "TEST1_SECRET");
        System.out.println(result);
    }

}
