package com.jumbo.util.comm;

import java.applet.Applet;
import netscape.javascript.JSObject;

/**
 * POS APPLET
 * 
 * @author jingkai
 *
 */
public class PosApplet extends Applet {

    private static final long serialVersionUID = -7948661620933346298L;

    @Override
    public void init() {
        super.init();
        // 初始化com口
        StartBackPosUtil.initSerialPort();
        // 设置APPLET便于反向推送数据
        StartBackPosUtil.setPosApplet(this);
        StartBackPosUtil.pushCard();
    }

    /**
     * 推送核对SKU
     * 
     * @author jingkai
     * @param data
     */
    public void pushCard(String data) {
        System.out.println("applt : "+ data);
        JSObject win = JSObject.getWindow(this);
        //调用页面js方法
        String jsStr = "checkCard('" + data + "');";
        System.out.println("js:" + jsStr);
        win.eval(jsStr);
    }

    @Override
    public void destroy() {
        StartBackPosUtil.close();
        super.destroy();
    }


}
