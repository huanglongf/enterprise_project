package com.jumbo.util;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Md5Util implements Serializable {
    private static final long serialVersionUID = -1924074101933981032L;
    protected static final Logger log = LoggerFactory.getLogger(Md5Util.class);

    public static String getMD5Str(String source) {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("MD5");
            digest.reset();
            digest.update(source.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            if (log.isErrorEnabled()) {
                log.error("httpPost", e);
            }
        } catch (UnsupportedEncodingException e) {
            if (log.isErrorEnabled()) {
                log.error("httpPost", e);
            }
        }
        if (digest == null) {
            return null;
        }
        byte[] byteArray = digest.digest();
        StringBuffer md5StrBuff = new StringBuffer();
        for (int i = 0; i < byteArray.length; i++) {
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
                md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
            else
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
        }
        return md5StrBuff.toString();
    }

    public static byte[] getMD5(String source) {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("MD5");
            digest.reset();
            digest.update(source.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            if (log.isErrorEnabled()) {
                log.error("Md5Util-NoSuchAlgorithmException" + source, e);
            }
        } catch (UnsupportedEncodingException e) {
            if (log.isErrorEnabled()) {
                log.error("Md5Util-UnsupportedEncodingException" + source, e);
            }
        }
        if (digest == null) {
            return null;
        }
        byte[] byteArray = digest.digest();
        return byteArray;
    }

    public static boolean isEqualsSecret(String source, String secret) {
        String newSecret = generateSecret(source);
        return newSecret.equals(secret);
    }

    public static String generateSecret(String source) {
        return getMD5Str(source);
    }

    public static void main(String[] args) {
        System.out.println(generateSecret("testtest20121018180450"));
    }

}
