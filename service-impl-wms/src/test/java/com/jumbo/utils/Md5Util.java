//package com.jumbo.utils;
//
//import java.io.Serializable;
//import java.io.UnsupportedEncodingException;
//import java.security.MessageDigest;
//import java.security.NoSuchAlgorithmException;
//
//public class Md5Util implements Serializable {
//    private static final long serialVersionUID = -1924074101933981032L;
//
//    public static String getMD5Str(String source) {
//        MessageDigest digest = null;
//        try {
//            digest = MessageDigest.getInstance("MD5");
//            digest.reset();
//            digest.update(source.getBytes("UTF-8"));
//        } catch (NoSuchAlgorithmException e) {
//        } catch (UnsupportedEncodingException e) {
//        }
//        if (digest == null) {
//            return null;
//        }
//        byte[] byteArray = digest.digest();
//        StringBuffer md5StrBuff = new StringBuffer();
//        for (int i = 0; i < byteArray.length; i++) {
//            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
//                md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
//            else
//                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
//        }
//        return md5StrBuff.toString();
//    }
//
//    public static byte[] getMD5(String source) {
//        MessageDigest digest = null;
//        try {
//            digest = MessageDigest.getInstance("MD5");
//            digest.reset();
//            digest.update(source.getBytes("UTF-8"));
//        } catch (NoSuchAlgorithmException e) {
//        } catch (UnsupportedEncodingException e) {
//        }
//        if (digest == null) {
//            return null;
//        }
//        byte[] byteArray = digest.digest();
//        return byteArray;
//    }
//
//    public static boolean isEqualsSecret(String source, String secret) {
//        String newSecret = generateSecret(source);
//        return newSecret.equals(secret);
//    }
//
//    public static String generateSecret(String source) {
//        return getMD5Str(source);
//    }
//
//    public static void main(String[] args) {
//        System.out.println(generateSecret("testtest20121018180450"));
//    }
//
//}
