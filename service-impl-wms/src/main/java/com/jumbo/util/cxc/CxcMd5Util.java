package com.jumbo.util.cxc;

import java.security.MessageDigest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 接口/类说明：由CXC物流商 提供
 * 
 * @ClassName: CxcMd5Util
 * @author LuYingMing
 * @date 2016年6月13日 下午1:29:25
 */
public class CxcMd5Util {

    private static Log log = LogFactory.getLog(CxcMd5Util.class);


    public static String doMD5(String parameter) {
        // log.info("要加密的字符串-->"+parameter);
        try {

            MessageDigest md = MessageDigest.getInstance("MD5");

            byte[] digestResult = md.digest(StringUtils.defaultString(parameter).getBytes("UTF-8"));

            StringBuffer hexValue = new StringBuffer();

            for (int i = 0; i < digestResult.length; i++) {
                int val = ((int) digestResult[i]) & 0xff;
                hexValue.append(StringUtils.leftPad(Integer.toHexString(val), 2, '0'));
            }
            // log.info("加密的结果为-->"+hexValue.toString().toUpperCase());
            return hexValue.toString().toUpperCase();
        } catch (Exception ex) {
            log.info(ex.getStackTrace());
            if (log.isErrorEnabled()) {
                log.error("TdoMD5 error:", ex);
            }
        }
        return parameter;
    }
}
