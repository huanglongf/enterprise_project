package com.jumbo.webservice.ids.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jumbo.util.zip.AppSecretUtil;


public class ServiceType implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 4814865969139435322L;

    protected static final Logger log = LoggerFactory.getLogger(ServiceType.class);
    // 发货单同步接口
    public static final String LOGISTIC_ORDER_NOTIFY = "logistic_order_notify";
    // 发货单取消接口
    public static final String LOGISTIC_ORDER_CANCEL = "logistic_order_cancel";
    // 订单状态更新接口 1
    public static final String LOGISTIC_ORDER_STATUS = "logistic_order_status";
    // 发货单确认接口 1
    public static final String LOGISTIC_ORDER_DELIVERY = "logistic_order_delivery";
    // 退货通知单接口
    public static final String LOGISTIC_RETURN_NOTIFY = "logistic_return_notify";
    // 退货单确认接口 1
    public static final String LOGISTIC_RETURN_ARRIVAL = "logistic_return_arrival";
    // 退货单取消接口
    public static final String LOGISTIC_RETURN_CANCEL = "logistic_return_cancel";
    // 出入库同步接口 1
    public static final String LOGISTIC_INVENTORY_SYNCHRONOUS = "logistic_inventory_synchronous";
    // 发货单释放接口
    public static final String LOGISTIC_ORDER_RELEASE = "logistic_order_release";


    public static final String ERROR_S01 = "S01";// XML格式错误或有非法字符
    public static final String ERROR_S02 = "S02";// 签名错误
    public static final String ERROR_S03 = "S03";// 非法的商户编号
    public static final String ERROR_S04 = "S04";// 非法的仓库编码
    public static final String ERROR_S05 = "S05";// 系统错误
    public static final String ERROR_S06 = "S06";// 其他错误
    public static final String ERROR_B10 = "B10";// 不能进行操作，SKU不存在
    public static final String ERROR_B11 = "B11";// 不能进行操作，当前状态：订单取消或者关闭
    public static final String ERROR_B12 = "B12";// 不能进行操作，订单已发货
    public static final String ERROR_B13 = "B13";// 不能进行操作，订单不存在
    public static final String ERROR_B14 = "B14";// 不能进行操作，物流公司为空
    public static final String ERROR_B15 = "B15";// 不能进行操作，运单号为空
    public static final String ERROR_B16 = "B16";// 不能进行操作，非法的运单号
    public static final String ERROR_B17 = "B17";// 不能进行操作，订单已存在

    public static final String LF_STATUS_CREATE = "0";
    public static final String LF_STATUS_PICKING = "5";
    public static final String LF_STATUS_CONFIRMATION = "9";
    public static final String LF_GODIVA_KEY = "QT_BaoZun";

    // public static final String Sign = "f132050c38301dff2a299d0a1f554c50";
    // public static final String Key = "AEO_ERP_WMS_API";
    // // public static final String URL="http://wcs.lfuat.net:20185/wms-aeo";
    //
    // public static final String bzSign = "32410abbb4cc9de495549c8a9b8a81f4";
    // public static final String bzKey = "IDS_AEO_TEST";
    //
    // // public static final String LevisbzSign="P3s62OaZQKgeSvC5I91VJxqKwkSU0HIN";
    // // public static final String LevisbzKey="IDS_LEVIS_TEST";
    // public static final String LevisbzSign = "7CmCiAPcjj3ctAiZX7ftU4iHls6NKI2i";
    // public static final String LevisbzKey = "IDS_LEVIS_PRO";
    //
    // public static final String AFSign = "e00d67066fb782603b39a95f50da5b93";
    // public static final String AFKey = "ANF_ERP_WMS_API";
    //
    // public static final String AFbzSign = "BGWnl12WtqdeGPANY1MYmDVAAWC37gx";
    // public static final String AFbzKey = "IDS_ANF_PRO";
    //
    // public static final String NIKEbzSign = "ac183548fec3a4fe843387fe44978m65748";
    // public static final String NIKEbzKey = "IDS_NIKE_API";
    //
    // public static final Map<String, String> IDS_PRO;
    // public static final Map<String, String> PRO_SOURCE;
    // static {
    // PRO_SOURCE = new HashMap<String, String>();
    // IDS_PRO = new HashMap<String, String>();
    // // IDS_PRO.put("Key","Sign");
    // IDS_PRO.put(NIKEbzKey, NIKEbzSign);
    // PRO_SOURCE.put(NIKEbzKey, Constants.VIM_WH_SOURCE_IDS_NIKE);
    // IDS_PRO.put(AFbzKey, AFbzSign);
    // PRO_SOURCE.put(AFbzKey, Constants.VIM_WH_SOURCE_IDS);
    // IDS_PRO.put(AFKey, AFSign);
    // PRO_SOURCE.put(AFKey, Constants.VIM_WH_SOURCE_AEO_IDS);
    // IDS_PRO.put(LevisbzKey, LevisbzSign);
    // PRO_SOURCE.put(LevisbzKey, Constants.VIM_WH_SOURCE_IDS);
    // IDS_PRO.put(bzKey, bzSign);
    // PRO_SOURCE.put(bzKey, Constants.VIM_WH_SOURCE_AEO_IDS);
    // }

    // public static final String AFbzSign="0Mk4Li4Mu7Mn4Ca7Vn2Mx0Et7Sm4Sk8H";
    // public static final String AFbzKey="IDS_ANF_TEST";



    public static String sendMsgtoIds(String type, String data, String url, String key, String sign) {
        // String formatData = data.replace("\\&", " ");
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            SimpleDateFormat sf = new SimpleDateFormat("yyyyMMDDHHMMss");
            String requestTime = sf.format(new Date());
            String param = null;
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("Accept-Charset", "UTF-8");
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            if (key.equals(LF_GODIVA_KEY)) {
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            } else {
                conn.setRequestProperty("Content-Type", "application/xml");
            }

            conn.setConnectTimeout(15000);
            conn.setReadTimeout(60000);
            // conn.setRequestProperty("user-agent",
            // "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            sf = new SimpleDateFormat("yyyyMMddHHmmss");
            requestTime = sf.format(new Date());

            String param1 = "Key=" + key + "&RequestTime=" + requestTime + "&Sign=" + sign + "&Version=4.0&ServiceType=" + type;
            log.debug("DebugIDS-secretKey-NIKE->:" + param1);
            String secretKey = AppSecretUtil.generateSecret(param1);
            param = "Key=" + key + "&RequestTime=" + requestTime + "&Sign=" + secretKey + "&Version=4.0&ServiceType=" + type + "&Data=" + data;


            log.debug("DebugIDS->:" + param);
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            realUrl = null;
        } catch (Exception e) {
            log.error("LF sendMsgtoIds:发送 POST 请求出现异常！:" + key + e.toString());
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                log.error("LF sendMsgtoIds:流关闭异常：" + ex.toString());
            }
        }
        log.debug("LF sendMsgtoIds:" + result);
        return result;
    }


    public static String sendMsgtoAeo(String filePath, String xml, String url, String pKeyPassword) {
        String result = "";
        try {
            // Open a secure connection.
            // "https://servicesapi-test.ae.com/osb/enterprise/apac/customerorder/v1/create"
            URL aeoUrl = new URL(url);

            HttpsURLConnection con = (HttpsURLConnection) aeoUrl.openConnection();

            // Set up the connection properties
            con.setRequestProperty("Connection", "close");
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setUseCaches(false);
            con.setConnectTimeout(60000);
            con.setReadTimeout(60000);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/xml");
            con.setRequestProperty("Content-Length", Integer.toString(xml.length()));
            if (log.isDebugEnabled()) {
                log.debug("File");
            }
            File pKeyFile = new File(filePath);
            if (log.isDebugEnabled()) {
                log.debug("File2");
            }
            // Password for the certificate
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            InputStream keyInput = new FileInputStream(pKeyFile);
            if (log.isDebugEnabled()) {
                log.debug("find filePath");
            }
            keyStore.load(keyInput, pKeyPassword.toCharArray());
            keyInput.close();
            keyManagerFactory.init(keyStore, pKeyPassword.toCharArray());
            SSLContext context = SSLContext.getInstance("TLS");
            context.init(keyManagerFactory.getKeyManagers(), null, new SecureRandom());
            SSLSocketFactory sockFact = context.getSocketFactory();
            con.setSSLSocketFactory(sockFact);
            if (log.isDebugEnabled()) {
                log.debug("Send To AEO sendMsgtoAeo:");
            }
            OutputStream outputStream = con.getOutputStream();
            outputStream.write(xml.getBytes("UTF-8"));
            outputStream.close();
            int responseCode = con.getResponseCode();
            InputStream inputStream;
            if (responseCode == HttpURLConnection.HTTP_OK) {
                inputStream = con.getInputStream();
            } else {
                inputStream = con.getErrorStream();
            }
            if (log.isDebugEnabled()) {
                log.debug("AEO sendMsgtoAeo:");
            }
            // Process the response
            BufferedReader reader;
            String line = null;
            reader = new BufferedReader(new InputStreamReader(inputStream));
            while ((line = reader.readLine()) != null) {
                result += line;
            }
            aeoUrl = null;
            try {
                inputStream.close();
            } catch (Exception e1) {
                inputStream = null;
            }
            try {
                reader.close();
            } catch (Exception e2) {
                reader = null;
            }
        } catch (Exception e) {
            log.error("AEO sendMsgtoAEO:发送 POST 请求出现异常！:", e);
        }
        return result;
    }

}
