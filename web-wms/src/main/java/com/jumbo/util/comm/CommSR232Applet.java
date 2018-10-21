package com.jumbo.util.comm;

import static javax.comm.SerialPortEvent.DATA_AVAILABLE;

import java.applet.Applet;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.TooManyListenersException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.comm.CommPortIdentifier;
import javax.comm.NoSuchPortException;
import javax.comm.PortInUseException;
import javax.comm.SerialPort;
import javax.comm.SerialPortEvent;
import javax.comm.SerialPortEventListener;
import javax.comm.UnsupportedCommOperationException;

import netscape.javascript.JSObject;

public class CommSR232Applet extends Applet implements Runnable, SerialPortEventListener {

    private static final long serialVersionUID = 1914178110749008304L;

    public static final String BAUD_RATE = "SCM_WEIGHT";
    public static final String WEIGHT_TYPE = "SCM_WEIGHT_TYPE";
    public static final String WEIGHT_TYPE_SMALL = "1";
    public static final String WEIGHT_TYPE_SMALLNEW = "2";
    public static final String PORT = "SCM_WEIGHT_PORT";

    private CommPortIdentifier portId;
    private int delayRead;
    private byte readBuffer[];
    private InputStream inputStream;

    public static SerialPort serialPort;
    private Thread readThread;

    static {
        System.setSecurityManager(null);
    }

    public CommSR232Applet() {
        delayRead = 20;
        readBuffer = new byte[1024];
    }

    public void initSerialPort() throws NoSuchPortException, PortInUseException, IOException, TooManyListenersException, UnsupportedCommOperationException {
        System.out.println("-------start init serial port---------- : " + serialPort);
        try {
            serialPort = null;
            int timeout = 1000;
            String port = "COM1";
            try {
                String tmp = System.getenv(PORT);
                if (tmp == null || "".equals(tmp)) {
                    port = "COM1";
                } else {
                    port = tmp;
                }
            } catch (Exception ex) {}

            portId = CommPortIdentifier.getPortIdentifier(port);
            serialPort = (SerialPort) portId.open("SerialReader", timeout);
            int rate = 2400;
            try {
                String sysrate = System.getenv(BAUD_RATE);
                rate = Integer.parseInt(sysrate);
            } catch (Exception e) {
                rate = 2400;
            }
            int dataBits = 8;
            int stopBits = 1;
            int parity = 0;
            inputStream = serialPort.getInputStream();
            serialPort.addEventListener(this);
            serialPort.notifyOnDataAvailable(true);
            serialPort.setSerialPortParams(rate, dataBits, stopBits, parity);
            System.out.println("-------end init serial port---------- : " + serialPort);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void closeSerialPort() {
        System.out.println("-------close serial port----------");
        if (serialPort != null) {
            serialPort.close();
        }
    }

    @Override
    public void init() {
        super.init();
        boolean start = true;
        if (serialPort == null) {
            try {
                initSerialPort();
            } catch (PortInUseException e) {
                System.out.println("Port is in use!");
                start = false;
            } catch (TooManyListenersException e) {
                System.out.println("Too many listeners!");
                start = false;
            } catch (UnsupportedCommOperationException e) {
                System.out.println("Unsupported Comm Operation!");
                start = false;
            } catch (NoSuchPortException e) {
                System.out.println("Port is not exist!");
                start = false;
            } catch (Throwable e) {
                e.printStackTrace();
                start = false;
            }
        }
        if (start) {
            readThread = new Thread(this);
            readThread.start();
        }

    }

    public void serialEvent(SerialPortEvent event) {
        try {
            Thread.sleep(delayRead);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        switch (event.getEventType()) {
            case DATA_AVAILABLE: // '\001'
                try {
                    try {
                        String type = null;
                        try {
                            type = System.getenv(WEIGHT_TYPE);
                            if ("".equals(type)) {
                                type = null;
                            }
                        } catch (Exception e) {}
                        System.out.println("type is : " + type);
                        if (type == null) {
                            System.out.println("----normal------");
                            inputStream.read(readBuffer);
                            String str = new String(readBuffer);
                            setRS232Value(calcValue1(str));
                        } else if (WEIGHT_TYPE_SMALL.equals(type)) {
                            System.out.println("----small------");
                            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
                            String str = br.readLine();
                            System.out.println("====source value : ==-|" + str + "|-");
                            setRS232Value(smallVal(str));
                        } else if (WEIGHT_TYPE_SMALLNEW.equals(type)) {
                            System.out.println("----SMALL NEW------");
                            inputStream.read(readBuffer);
                            String str = new String(readBuffer);
                            setRS232Value(calcValueNew(str));
                        } else {
                            System.out.println("----normal------");
                            inputStream.read(readBuffer);
                            String str = new String(readBuffer);
                            setRS232Value(calcValue1(str));
                        }
                    } catch (Exception e) {
                        System.out.println("error");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }

    }

    public static void main(String[] args) throws Exception {
        CommSR232Applet c = new CommSR232Applet();
        float f = c.smallVal("ST NT 0000.015kg");
        System.out.println(f);
    }

    public float calcValueNew(String value) throws Exception {
        System.out.println("KJL==" + value);
        if (value == null || value.equals("")) {
            System.out.println("Blank weight value");
            return -500F;
        }
        value = value.replaceAll("(^=)", "").trim().replace("\r", " ").replace("\n", " ").replace(",", "");
        value = value.substring(value.indexOf("ST", 0), value.indexOf("kg", 0) + 2);
        System.out.println("calcValueNew===" + value);
        String vArray[] = value.split("=");
        boolean isNormal = false;
        // try to find one valid number
        Float f = -100f;
        for (int i = 0; i < vArray.length; i++) {
            String v = vArray[i].trim();
            System.out.println(v);
            if (!isNormal) {
                StringBuilder sb = new StringBuilder(v);
                v = sb.reverse().toString();
            }
            try {
                f = new Float(v);
                break;
            } catch (NumberFormatException e) {
                System.out.println("[value=" + value + "][v=" + v + "]");
                value = value.replace(" ", "").replace(",", "");
            }
        }
        System.out.println("===Final value:" + f + "===");
        if (f == -100f) {
            try {
                String tmp = value.toLowerCase().substring(4, value.indexOf("kg"));
                f = new Float(tmp);
            } catch (NumberFormatException e1) {
                f = -500f;
                System.out.println("[value=" + value + "]");
            }
        }
        if (f == -500f) {
            throw new Exception();
        }
        return f;
    }

    // XK3190-a6
    public float calcValue1(String value) throws Exception {
        System.out.println("====normal====");
        if (value == null || value.equals("")) {
            System.out.println("Blank weight value");
            return -500F;
        }
        value = value.replaceAll("(^=)", "").trim();
        String vArray[] = value.split("=");
        boolean isNormal = false;
        // try to find one valid number
        Float f = -100f;
        for (int i = 0; i < vArray.length; i++) {
            String v = vArray[i].trim();
            System.out.println(v);
            if (!isNormal) {
                StringBuilder sb = new StringBuilder(v);
                v = sb.reverse().toString();
            }
            try {
                f = new Float(v);
                break;
            } catch (NumberFormatException e) {
                System.out.println("[value=" + value + "][v=" + v + "]");
            }
        }
        System.out.println("===Final value:" + f + "===");
        if (f == -100f) {
            try {
                String tmp = value.toLowerCase().substring(value.indexOf('0'), value.indexOf("kg"));
                f = new Float(tmp);
            } catch (NumberFormatException e1) {
                f = -500f;
                System.out.println("[value=" + value + "]");
            }
        }
        if (f == -500f) {
            throw new Exception();
        }
        return f;
    }

    public void setRS232Value(float value) {
        JSObject win = JSObject.getWindow(this);
        String evalStr = (new StringBuilder("setRS232Value('")).append(value == -500 ? "" : value).append("');").toString();
        System.out.println(evalStr);
        win.eval(evalStr);
    }

    public void run() {
        try {
            Thread.sleep(delayRead);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void destroy() {
        if (serialPort != null) serialPort.close();
        System.out.println("RS232 Already Closed!");
        super.destroy();
    }



    public float smallVal(String value) throws Exception {
        System.out.println("====smallVal==========");
        if (value == null || value.equals("")) {
            System.out.println("Blank weight value");
            return -500F;
        }
        value = value.replaceAll("(^=)", "").trim().replace("\r", " ").replace("\n", " ");
        Float f = -100f;
        try {
            System.out.println(value);
            value = value.replace(" ", "");
            Pattern pattern = Pattern.compile("^[a-zA-Z]\\S+$");
            Matcher matcher = pattern.matcher(value);
            boolean b = matcher.matches();
            if (!b) {
                throw new Exception();
            }

            String tmp = value.toLowerCase().substring(value.indexOf('0'), value.indexOf("kg"));
            f = new Float(tmp);
        } catch (NumberFormatException e1) {
            f = -500f;
            System.out.println("[value=" + value + "]");
        }
        if (f == -500f) {
            throw new Exception();
        }
        return f;
    }
}
