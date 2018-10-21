package com.bt.vims.utils;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;


/**
 * 加密过程为 key得到md5的16位字符串 得到AES加密后的数组 将数组转成BASE64字符中输出
 * <pre>
 * 
 * </pre>
 * @Title:
 * @Company:宝尊电子商务有限公司
 * @Description:
 * @Author:xiaozhou.zhou
 * @Since:2015年5月19日
 * @Copyright:Copyright (c) 2015
 * @ModifyDate:
 * @Version:1.1.0
 */
public class AESUtil {

    /**
     * password必须为16位字符串
     * 
     * @param content
     * @param password
     * @return
     * @throws UnsupportedEncodingException
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @Description:
     */
    public static byte[] encrypt(byte[] content, String password) throws UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        SecretKeySpec key = new SecretKeySpec(password.getBytes("UTF-8"), "AES");// password的长度必须为16否则报错
        Cipher cipher = Cipher.getInstance("AES");// 创建密码器
        cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
        byte[] result = cipher.doFinal(content);
        return result;
    }

    /**
     * 
     * @param content
     * @param password
     * @return
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @Description:
     */
    public synchronized static byte[] decrypt(byte[] content, String password) throws UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        SecretKeySpec key = new SecretKeySpec(password.getBytes("UTF-8"), "AES");
        Cipher cipher = Cipher.getInstance("AES");// 创建密码器
        cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
        byte[] result = cipher.doFinal(content);
        return result;
    }

    /**
     * 加密，password为16位md5，这个要在线加密后，双方约定 password
     * 
     * @param content 需要加密的内容
     * @param password 加密密码
     * @return
     */
    public synchronized static String encrypt(String content, String password) {
        try {
            password = MD5UtilStrong.getMD5_16(password);// 密码必须16位长度
            byte[] byteContent = content.getBytes("utf-8");
            return BASE64UtilStrong.encode(encrypt(byteContent, password)); // 加密
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解密
     * 
     * @param content 待解密内容
     * @param password 解密密钥--一定要16位长度
     * @return
     */
    public synchronized static String decrypt(String content, String password) {
        try {
            password = MD5UtilStrong.getMD5_16(password);
            byte[] contentByte = BASE64UtilStrong.decode(content);
            return new String(decrypt(contentByte, password), "UTF-8"); // 加密
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}
