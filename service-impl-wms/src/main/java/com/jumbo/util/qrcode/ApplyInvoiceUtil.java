package com.jumbo.util.qrcode;

import java.net.URLEncoder;
import java.text.MessageFormat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jumbo.wms.model.warehouse.ApplyInvoiceRequest;

public class ApplyInvoiceUtil {

    private static final String CHARSET_TYPE_UTF8 = "UTF-8";
    private static final String SYMBOL_AND = "&";
    private static final String APPLY_INVOICE_URL = "/apply/get_invoice?shopCode={0}&data={1}&sign={2}";

    private static String DOMAIN;

    private static String SECRET;


    static {
        DOMAIN = ConfigurationUtil.getInstance().getNebulaUtilityConfiguration("einvoice.domain");
        SECRET = ConfigurationUtil.getInstance().getNebulaUtilityConfiguration("einvoice.secret");
    }

    /**
     * fapiao.baozun.com/apply/get_invoice?shopCode&data&sign
     * 
     * @param applyInvoiceRequest
     * @return
     */
    public static String genApplyInvoiceUrl(ApplyInvoiceRequest applyInvoiceRequest) {
        String shopCode = applyInvoiceRequest.getShopCode();
        String data = genDataParam(applyInvoiceRequest);
        String sign = genSignParam(shopCode, data, SECRET);
        String getInvoiceUrl = MessageFormat.format(getApplyInvoiceUrl(), shopCode, data, sign);
        return getInvoiceUrl;
    }

    public static byte[] genApplyInvoiceQRCode(ApplyInvoiceRequest applyInvoiceRequest) {
        String applyInvoiceUrl = genApplyInvoiceUrl(applyInvoiceRequest);
        byte[] genQRCode = null;
        try {
            genQRCode = ZXingUtil.genQRCode(applyInvoiceUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return genQRCode;
    }

    /**
     * Base64{AES[param, secret]}
     * 
     * @param applyInvoiceRequest
     * @return
     */
    private static String genDataParam(ApplyInvoiceRequest applyInvoiceRequest) {
        ObjectMapper om = new ObjectMapper();
        String data = null;
        AESEncryptor aes = new AESEncryptor();
        try {
            String json = om.writeValueAsString(applyInvoiceRequest);
            String encryptedUrlParam = aes.encrypt(json, SECRET);
            String formattedUrlParam = Base64Util.format(encryptedUrlParam.getBytes());
            data = URLEncoder.encode(formattedUrlParam, CHARSET_TYPE_UTF8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * uppercase(md5(shopCode & data & secret))
     * 
     * @param shopCode
     * @param data
     * @param secret
     * @return
     */
    private static String genSignParam(String shopCode, String data, String shopSecret) {
        StringBuilder sb = new StringBuilder();
        sb.append(shopCode).append(SYMBOL_AND).append(data).append(SYMBOL_AND).append(shopSecret);
        String sign = Md5Encrypt.md5(sb.toString()).toUpperCase();
        return sign;
    }

    private static String getApplyInvoiceUrl() {
        return DOMAIN + APPLY_INVOICE_URL;
    }

}
