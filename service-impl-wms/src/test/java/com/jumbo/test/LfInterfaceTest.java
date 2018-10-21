package com.jumbo.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.jumbo.util.zip.AppSecretUtil;

public class LfInterfaceTest {

    public static void main() {
        System.out.println("LF Interface post data");
        System.out.println(new Date().getTime());
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            String type = "logistic_return_arrival";
            String data =
                    "<?xml version='1.0' encoding='UTF-8'?><WMSREC><BatchID>0000284305</BatchID><RECHD><StorerKey>UA</StorerKey><Facility>CN1</Facility><ExternReceiptKey>E600006675982</ExternReceiptKey><WarehouseReference>R2176711</WarehouseReference><UserDefines><UserDefine><UserDefine_No>1</UserDefine_No><UserDefine_Value>UA1507000005</UserDefine_Value></UserDefine></UserDefines>  <ASNStatus>9</ASNStatus>    <EffectiveDate>20150728163137</EffectiveDate>   <RECDT> <ExternLineNo>0001</ExternLineNo>   <SKU>1257471-410-SM</SKU>   <QtyReceived>1</QtyReceived>    <HostWHCode>BL</HostWHCode> </RECDT>    <RECDT> <ExternLineNo>0002</ExternLineNo>   <SKU>1257471-410-MD</SKU>   <QtyReceived>1</QtyReceived>    <HostWHCode>UN</HostWHCode> </RECDT>    </RECHD>    </WMSREC>";
            // SimpleDateFormat sf = new SimpleDateFormat("yyyyMMDDHHMMss");
            SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");

            String requestTime = sf.format(new Date());
            // URL realUrl = new URL("http://scm.baozun.cn/scm/rest/ids.json");

            URL realUrl = new URL("http://scm.baozun.cn/scm/rest/ids.json");
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("Content-Type", "application/xml");
            // conn.setRequestProperty("user-agent",
            // "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            String param1 = "Key=LFL_UA_PRO&RequestTime=" + requestTime + "&Sign=eynQWZStsjFd0oXhScMo8byy2DEWbTSN&Version=4.0&ServiceType=" + type;
            String secretKey = AppSecretUtil.generateSecret(param1);
            String param = "Key=LFL_UA_PRO&RequestTime=" + requestTime + "&Sign=" + secretKey + "&Version=4.0&ServiceType=" + type + "&Data=" + data;
            out.print(param);

            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            System.out.println("result" + result);
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
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
                ex.printStackTrace();
            }
        }
        System.out.println(result);
    }
}
