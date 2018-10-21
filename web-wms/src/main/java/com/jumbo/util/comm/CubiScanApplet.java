package com.jumbo.util.comm;

import java.applet.Applet;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class CubiScanApplet extends Applet {

    private static final long serialVersionUID = 7771261004212233280L;

    // private String ip = "10.7.31.220";
    // private int port = 1050;

    public static final int DEFAULT_TIME_OUT = 8000;
    public static final String SCAN_ASK = "0243030D0A";

    static {
        System.setSecurityManager(null);
    }

    @Override
    public void init() {
        super.init();
    }

    public static void main(String[] args) {
        CubiScanApplet app = new CubiScanApplet();
        app.scan("10.7.13.240", 1050);
    }

    /**
     * 获取测量仪读书
     * 
     * @param ip
     * @param port
     * @return String[] 0 ,长;1,宽;2,高;3,重量
     */
    public String scan(String ip, int port) {
        try {
            Socket socket = new Socket(ip, port);
            socket.setSoTimeout(DEFAULT_TIME_OUT);
            // 客户端的输出流
            OutputStream os = socket.getOutputStream();
            // 将信息写入流,把这个信息传递给服务器
            String s = asciiStringToString(SCAN_ASK);
            os.write(s.getBytes());
            // 从服务器端接收信息
            InputStream is = socket.getInputStream();
            byte[] buffer = new byte[200];
            int length = is.read(buffer);
            String str = new String(buffer, 0, length);
            System.out.println(str);
            String[] rs = new String[5];
            rs[0] = str.substring(12, 17).trim();
            System.out.println("length : " + rs[0]);
            rs[1] = str.substring(19, 24).trim();
            System.out.println("width : " + rs[1]);
            rs[2] = str.substring(27, 31).trim();
            System.out.println("height : " + rs[2]);
            rs[3] = str.substring(36, 41).trim();
            System.out.println("weight : " + rs[3]);
            rs[4] = str.substring(44, 49).trim();
            System.out.println("dweight : " + rs[4]);
            // 关闭资源
            is.close();
            os.close();
            socket.close();
            System.out.println("data: " + rs[0] + rs[1] + rs[2] + rs[3]);
            return rs[0] + "," + rs[1] + "," + rs[2] + "," + rs[3];
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void destroy() {
        super.destroy();
    }

    /**
     * ASCII码字符串转数字字符串
     * 
     * @param String ASCII字符串
     * @return 字符串
     */
    public static String asciiStringToString(String content) {
        String result = "";
        int length = content.length() / 2;
        for (int i = 0; i < length; i++) {
            String c = content.substring(i * 2, i * 2 + 2);
            int a = hexStringToAlgorism(c);
            char b = (char) a;
            String d = String.valueOf(b);
            result += d;
        }
        return result;
    }

    /**
     * 十六进制字符串装十进制
     * 
     * @param hex 十六进制字符串
     * @return 十进制数值
     */
    private static int hexStringToAlgorism(String hex) {
        hex = hex.toUpperCase();
        int max = hex.length();
        int result = 0;
        for (int i = max; i > 0; i--) {
            char c = hex.charAt(i - 1);
            int algorism = 0;
            if (c >= '0' && c <= '9') {
                algorism = c - '0';
            } else {
                algorism = c - 55;
            }
            result += Math.pow(16, max - i) * algorism;
        }
        return result;
    }


}
