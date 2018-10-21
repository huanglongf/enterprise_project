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
package com.jumbo.wms.manager.task.sf.tw;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author lichuan
 * 
 */
public class SfTwDigest {
    protected static final Logger log = LoggerFactory.getLogger(SfTwDigest.class);

    public static String digest(String toVerifyText, String encode) {
        String base64Str = null;
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            md5.update(toVerifyText.getBytes(encode));
            byte[] md = md5.digest();

            base64Str = org.apache.commons.codec.binary.Base64.encodeBase64String(md);
            // base64Str=getURLEncoder(base64Str,encode);
            // base64Str = new String(new BASE64Encoder().encode(md));
        } catch (NoSuchAlgorithmException e) {
            if (log.isErrorEnabled()) {
                log.error("digest NoSuchAlgorithmException", e);
            }
        } catch (UnsupportedEncodingException e) {
            if (log.isErrorEnabled()) {
                log.error("digest UnsupportedEncodingException", e);
            }
        }

        return base64Str;
    }

    // url编码
    public static String getURLEncoder(String urlCode, String encode) {
        try {
            urlCode = URLEncoder.encode(urlCode, encode);
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            if (log.isErrorEnabled()) {
                log.error("getURLEncoder UnsupportedEncodingException", e);
            }
        }
        return urlCode;
    }

    // url解码
    public static String getURLDecoder(String urlCode, String encode) {
        try {
            // String a=new
            // String(org.apache.commons.codec.binary.Base64.decodeBase64(urlCode.getBytes(encode)),encode);
            // System.out.println("a=="+a);
            urlCode = URLDecoder.decode(urlCode, encode);
        } catch (UnsupportedEncodingException e) {
            if (log.isErrorEnabled()) {
                log.error("getURLDecoder UnsupportedEncodingException", e);
            }
        }
        return urlCode;
    }

    public static void main(String[] args) {

        String xxx =
                "%3CRequestOrderStatusInfo%3E%0A++%3CcustomerId%3EGY755DCA%3C%2FcustomerId%3E%0A++%3CorderCode%3EXSCKGY2013042602%3C%2ForderCode%3E%0A++%3CorderStatus%3E%D2%D1%CF%C2%B7%A2%3C%2ForderStatus%3E%0A++%3CorderStatusCode%3E10003%3C%2ForderStatusCode%3E%0A++%3ClogisticProviderId%3ESF%3C%2FlogisticProviderId%3E%0A++%3CmailNo%3E966103131887%3C%2FmailNo%3E%0A++%3CaccessDt%3E2013-05-07+18%3A29%3A09%3C%2FaccessDt%3E%0A++%3CassignedTo%3E236664%3C%2FassignedTo%3E%0A%3C%2FRequestOrderStatusInfo%3E";

        String data_digest = "wmfSXhYhVZAIwV0GaC%2BtYw%3D%3D";

        String xml = getURLDecoder(xxx, "GBK");
        //
        System.out.println(xml);


        String dataDigestSF = "2a588e923c044c4ba03d0d08a4b195b5";
        String idenfifier = xml + dataDigestSF;

        // System.out.println("getURLEncoder : " + getURLEncoder(idenfifier, "GBK"));
        // MD5加密 并且 base64加密
        String word = SfTwDigest.digest(idenfifier, "GBK");

        System.out.println("MD5加密 并且 base64加密 : " + word);

        String url_code = getURLEncoder(word, "GBK");
        System.out.println("URL转码 : " + getURLEncoder(word, "GBK"));


        System.out.println("URL转码 : " + getURLDecoder("maPbQmeK8i5H%2FC0PjIAt3A%3D%3D", "UTF-8"));
        // maPbQmeK8i5H%2FC0PjIAt3A%3D%3D
        // maPbQmeK8i5H%2FC0PjIAt3A%3D%3D

        // 3iI2hIl5C6S/jn72Nes6Gw==
        System.out.println(url_code.equals(data_digest));
        // System.out.println("原MD5 : " + getURLDecoder("%2FLhp3F46vm0rxCzR5JXKmA%3D%3D", "GBK"));
        // 3iI2hIl5C6S%2Fjn72Nes6Gw%3D%3D
        // 3iI2hIl5C6S%2Fjn72Nes6Gw%3D%3D
        //
        //
        // maPbQmeK8i5H%2FC0PjIAt3A%3D%3D
        // maPbQmeK8i5H%2FC0PjIAt3A%3D%3D

        // YNZvGFGHngcNtmh 5TY5Bg== 顺丰:YNZvGFGHngcNtmh+5TY5Bg==
        // String aaaa = DigestUtil.md5(idenfifier);
        // System.out.println("新MD5 : " + aaaa);
        // String zzz1 = getURLEncoder(aaaa, "UTF-8");
        // System.out.println(getURLEncoder(zzz1, "UTF-8"));

        // String dataDigestGY = "fCU5ZU99517LPtXMj9DfTQ%3d%3d";
        // dataDigestGY = DigestUtil.getURLDecoder(dataDigestGY, "UTF-8");
        // System.out.println("管易MD5 : " + dataDigestGY);
        //
        // dataDigestGY = DigestUtil.getURLDecoder(dataDigestGY, "UTF-8");
        // System.out.println("管易MD5 : " + dataDigestGY);
    }

}
