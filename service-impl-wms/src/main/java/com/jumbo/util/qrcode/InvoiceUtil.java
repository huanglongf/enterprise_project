package com.jumbo.util.qrcode;

import java.net.URLEncoder;
import java.text.MessageFormat;

import com.jumbo.wms.model.warehouse.ApplyInvoiceRequest;



public class InvoiceUtil {

    private static final String CHARSET_TYPE_UTF8 = "UTF-8";
    private static final String SYMBOL_AND = "&";
    private static final String SYMBOL_COMMA = ",";
    private static final String APPLY_INVOICE_URL = "/t/g?data={0}&sign={1}";

    private static String EINVOICE_DOMAIN;

    private static String EINVOICE_SECRET;


    static {
        EINVOICE_DOMAIN = ConfigurationUtil.getInstance().getNebulaUtilityConfiguration("einvoice.domain");
        EINVOICE_SECRET = ConfigurationUtil.getInstance().getNebulaUtilityConfiguration("einvoice.secret");
    }

    /**
     * 生成领票链接 fapiao.baozun.com/t/g?data=xxx&sign=xxx
     * 
     * @param applyInvoiceRequest
     * @return
     */
    public static String genApplyInvoiceUrl(ApplyInvoiceRequest applyInvoiceRequest) {
        String encryptParam = encryptParam(applyInvoiceRequest);
        String sign = genSignParam(applyInvoiceRequest.getShopCode(), encryptParam, EINVOICE_SECRET);
        String getInvoiceUrl = MessageFormat.format(getApplyInvoiceUrl(), encryptParam, sign);
        return getInvoiceUrl;
    }

    /**
     * 生成领票链接对应的二维码
     * 
     * @param applyInvoiceRequest
     * @return
     */
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


    public static byte[] genApplyInvoiceQRCode(int size, int margin, String format, ApplyInvoiceRequest applyInvoiceRequest) {
        String applyInvoiceUrl = genApplyInvoiceUrl(applyInvoiceRequest);
        byte[] genQRCode = null;
        try {
            genQRCode = ZXingUtil.genQRCode(size, margin, format, applyInvoiceUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return genQRCode;
    }

    /**
     * 加密参数 URLEncoder(AES['param1,param2,param'])
     * param:shopCode,orderCode,amount,time,isCheckedInvoice
     * 
     * @param applyInvoiceRequest
     * @return
     */
    private static String encryptParam(ApplyInvoiceRequest applyInvoiceRequest) {
        String data = null;
        StringBuilder sb = new StringBuilder();
        try {
            String paramStr =
                    sb.append(applyInvoiceRequest.getShopCode()).append(SYMBOL_COMMA).append(applyInvoiceRequest.getOrderCode()).append(SYMBOL_COMMA).append(applyInvoiceRequest.getAmount()).append(SYMBOL_COMMA).append(applyInvoiceRequest.getTime())
                            .append(SYMBOL_COMMA).append(applyInvoiceRequest.isCheckedInvoice()).toString();
            data = URLEncoder.encode(paramStr, CHARSET_TYPE_UTF8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * uppercase(md5(shopCode & encryptParam & secret))
     * 
     * @param shopCode
     * @param encryptParam
     * @param secret
     * @return
     */
    private static String genSignParam(String shopCode, String encryptParam, String shopSecret) {
        StringBuilder sb = new StringBuilder();
        sb.append(shopCode).append(SYMBOL_AND).append(encryptParam).append(SYMBOL_AND).append(shopSecret);
        String sign = Md5Encrypt.md5(sb.toString()).toUpperCase();
        return sign;
    }

    private static String getApplyInvoiceUrl() {
        return EINVOICE_DOMAIN + APPLY_INVOICE_URL;
    }

}
