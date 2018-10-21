package com.jumbo.util.comm;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.util.Date;

/**
 * 
 * 
 * 项目名称：scm-wms 类名称：OpenCvImgApplet 类描述：获取本地图片 jsp页面显示 创建人：bin.hu 创建时间：2014-8-14 下午05:57:42
 * 
 * @version
 * 
 */
public class OpenCvImgApplet extends Applet {

    Image img;
    private String camera = "D:/wms/camera/";
    private static final long serialVersionUID = 8142908536883008639L;

    public void cameraImg(String fileUrl) {
        long startTime = new Date().getTime();
        System.out.println("显示图片 start " + startTime);
        File file = new File(camera + fileUrl + ".jpg");
        try {
            System.out.println(file.toURI().toURL());
            setBackground(Color.white);
            System.out.println("Get Image at:" + (new Date().getTime() - startTime) / 1000);
            img = Toolkit.getDefaultToolkit().getImage(file.toURI().toURL());
            System.out.println("开始重绘");
            paint(getGraphics());
            System.out.println("结束重绘");
        } catch (Throwable e) {
            e.printStackTrace();
        }
        System.out.println("显示图片 finish " + (new Date().getTime() - startTime) / 1000);
    }

    public void paint(Graphics g) {
        g.drawImage(img, 0, 0, 320, 240, this);
    }

    public void destory() {
        super.destroy();
    }
}
