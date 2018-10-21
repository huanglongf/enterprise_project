package com.jumbo.util.comm;

import java.applet.Applet;

/**
 * 测试jre版本
 * 
 * @author jingkai
 *
 */
public class JreTestApplet extends Applet {

    private static final long serialVersionUID = -9018562351464216316L;


    @Override
    public void init() {
        super.init();
    }

    public static void main(String[] args) {
        String version = System.getProperty("java.version");
        System.out.println("version ： " + version);
        
    }

    public String getJreVersion(String v) {
        System.out.println("get version-" + v);
        String version = System.getProperty("java.version");
        System.out.println("version ： " + version);
        return version;
    }

    @Override
    public void destroy() {
        super.destroy();
    }

}
