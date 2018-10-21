package com.jumbo.util.comm;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.TooManyListenersException;

import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;

/**
 * 星巴克POS机读卡 COM口处理类
 * 
 * @author sjk
 *
 */
public class StartBackPosUtil implements SerialPortEventListener {

    /**
     * 刷卡字符串
     */
    private static byte[] PUSH_CARD_DATA = new byte[] {2, 0, 70, 48, 54, 51, 48, 48, 48, 48, 32, 32, 32, 32, 32, 32, 50, 48, 49, 55, 49, 49, 50, 57, 49, 52, 51, 53, 52, 54, 32, 32, 32, 32, 32, 32, 32, 32, 32, 54, 53, 32, 32, 32, 32, 32, 32, 32, 32, 32,
            32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 3, 95};

    /**
     * 结束符号
     */
    private static byte ETX = 0x003;
    public static final String PORT = "SCM_POS_PORT";
    /**
     * 读取卡号起始位置,默认101
     */
    public static final String CARD_START = "SCM_POS_CARD_START";
    public static final int CARD_START_IDX = 101;
    public static int cardIdx = CARD_START_IDX;

    private static String port = "COM3";
    /**
     * 通讯端口标识符
     */
    protected static CommPortIdentifier portid = null;
    /**
     * 串行端口
     */
    protected static SerialPort comPort = null;
    /**
     * 输入流
     */
    private static InputStream iStream;
    /**
     * 输出流
     */
    private static OutputStream oStream;
    /**
     * 波特率
     */
    protected static int BAUD = 9600;
    /**
     * 数据位
     */
    protected static int DATABITS = SerialPort.DATABITS_8;
    /**
     * 停止位
     */
    protected static int STOPBITS = SerialPort.STOPBITS_1;
    /**
     * 奇偶检验
     */
    protected static int PARITY = SerialPort.PARITY_NONE;
    private static StartBackPosUtil listener = new StartBackPosUtil();
    private static PosApplet posApplet = null;

    /**
     * COM口读取字符串
     */
    private static String comReadData = "";

    public StartBackPosUtil() {
        super();
    }

    /**
     * COM口写入数据
     * 
     * @author jingkai
     * @param data
     * @param size
     */
    private static void writeData(byte[] data, int size) {
        try {
            oStream = comPort.getOutputStream();
            oStream.write(data, 0, size);
            oStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取COM口数据
     * 
     * @throws Exception
     */
    private static byte[] readData() {
        byte[] buff = new byte[1024];
        try {
            iStream = comPort.getInputStream();
            int size = iStream.read(buff);
            byte[] rs = new byte[size];
            for (int i = 0; i < size; i++) {
                rs[i] = buff[i];
            }
            return rs;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 初始化com口
     */
    public static void initSerialPort() {
        // 设置com口,查看环境变量,如果没有配置则默认com3
        try {
            String tmp = System.getenv(PORT);
            if (tmp == null || "".equals(tmp)) {
                port = "COM3";
            } else {
                port = tmp;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println("POS COM PORT: " + port);
        // 初始化文本读取位置
        try {
            String tmp = System.getenv(CARD_START);
            cardIdx = Integer.valueOf(tmp);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try {
            portid = CommPortIdentifier.getPortIdentifier(port);
            if (portid.isCurrentlyOwned()) {
                System.out.println("com port is used");
            } else {
                comPort = (SerialPort) portid.open(StartBackPosUtil.class.getName(), 1000);
            }
            try {
                // 给当前串口增加一个监听器
                comPort.addEventListener(listener);
                // 当有数据是通知
                comPort.notifyOnDataAvailable(true);
            } catch (TooManyListenersException e) {
                e.printStackTrace();
            }
            try {
                // 设置串口参数依次为(波特率,数据位,停止位,奇偶检验)
                comPort.setSerialPortParams(BAUD, DATABITS, STOPBITS, PARITY);
            } catch (UnsupportedCommOperationException e) {
                e.printStackTrace();
            }
        } catch (PortInUseException e) {
            e.printStackTrace();
        } catch (NoSuchPortException e) {
            e.printStackTrace();
        }
    }

    /**
     * COM口读数监听,当COM口此反向写入数据时出发,当完成后解析卡号，调用applet的核对卡号方法
     */
    @Override
    public void serialEvent(SerialPortEvent event) {
        try {
            byte[] rs = readData();
            comReadData += new String(rs);
            int idxEtx = 2;
            if (rs.length >= idxEtx && rs[rs.length - idxEtx] == ETX) {
                String card = readCard();
                // 去除前后空格
                posApplet.pushCard(card.trim());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 解析读卡数据
     * 
     * @author jingkai
     * @return
     */
    private String readCard() {
        String tmp = "";
        try {
            System.out.println("read card");
            tmp = comReadData.substring(cardIdx, cardIdx + 29);
            comReadData = "";
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {}
            pushCard();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tmp;
    }


    /**
     * 推送刷卡报文
     * 
     * @author jingkai
     */
    public static void pushCard() {
        writeData(PUSH_CARD_DATA, PUSH_CARD_DATA.length);
    }

    public static void main(String[] args) throws Exception {
        StartBackPosUtil.initSerialPort();
        pushCard();
    }

    /**
     * 关闭串口
     */
    public static void close() {
        if (iStream != null) {
            try {
                iStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (comPort != null) {
            comPort.close();
        }
    }

    /**
     * 设置POS applet
     * 
     * @author jingkai
     * @param posApplet
     */
    public static void setPosApplet(PosApplet posApplet) {
        StartBackPosUtil.posApplet = posApplet;
    }


}
