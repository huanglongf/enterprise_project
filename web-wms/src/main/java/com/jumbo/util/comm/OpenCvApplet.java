package com.jumbo.util.comm;

import java.applet.Applet;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.imageio.ImageIO;
import javax.swing.Timer;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.googlecode.javacv.CanvasFrame;
import com.googlecode.javacv.OpenCVFrameGrabber;
import com.googlecode.javacv.cpp.opencv_core.IplImage;

/**
 * 
 * 
 * 项目名称：scm-wms 类名称：OpenCvApplet 类描述： USB摄像头拍照功能 创建人：bin.hu
 * 
 * @version
 * 
 */
@SuppressWarnings("deprecation")
public class OpenCvApplet extends Applet {

    private static final long serialVersionUID = 753665376606354575L;

    public static OpenCVFrameGrabber grabber;
    public static CanvasFrame canvasFrame;
    private String camera = "D:/wms/camera/";
    private ResourceBundle bundle;

    public OpenCvApplet() {
        long startTime = new Date().getTime();
        System.out.println("初始化applet start " + startTime);
        try {
            bundle = ResourceBundle.getBundle("com/jumbo/util/comm/messages");
            System.out.println("Debeg============================Init1 end:" + bundle);
            if (!bundle.containsKey("md5")) {
                bundle = ResourceBundle.getBundle("com/jumbo/util/comm/testMessages");
                System.out.println("Debeg============================Init2 end:" + bundle);
            }
        } catch (Exception e) {
            bundle = ResourceBundle.getBundle("com/jumbo/util/comm/testMessages");
            System.out.println("Debeg============================Init3 end:" + bundle);
        }
        System.out.println("Debeg============================URL:" + bundle.getString("zipUrl"));
        System.out.println("初始化applet finish " + (new Date().getTime() - startTime) / 1000);
    }

    static class TimerAction implements ActionListener {
        private Graphics2D g;
        private CanvasFrame canvasFrame;
        private int width, height;

        private int delta = 10;
        private int count = 0;

        private Timer timer;

        public void setTimer(Timer timer) {
            this.timer = timer;
        }

        public TimerAction(CanvasFrame canvasFrame) {
            this.g = (Graphics2D) canvasFrame.getCanvas().getGraphics();
            this.canvasFrame = canvasFrame;
            this.width = canvasFrame.getCanvas().getWidth();
            this.height = canvasFrame.getCanvas().getHeight();
        }

        public void actionPerformed(ActionEvent e) {
            int offset = delta * count;
            if (width - offset >= offset && height - offset >= offset) {
                g.drawRect(offset, offset, width - 2 * offset, height - 2 * offset);
                canvasFrame.repaint();
                count++;
            } else {
                // when animation is done, reset count and stop timer.
                timer.stop();
                count = 0;
            }
        }
    }

    /**
     * 初始化摄像头
     */
    public boolean openCvPhoto() throws Throwable, Exception {
        long startTime = new Date().getTime();
        System.out.println("初始化摄像头 start " + startTime);
        try {
            grabber = new OpenCVFrameGrabber(0);
            grabber.start();
            // create a frame for real-time image display
            canvasFrame = new CanvasFrame("baozun");
            IplImage image = grabber.grab();
            int width = image.width();
            int height = image.height();
            canvasFrame.setCanvasSize(width, height);
            canvasFrame.hide();// 隐藏窗口
            // animation timer
            TimerAction timerAction = new TimerAction(canvasFrame);
            final Timer timer = new Timer(10, timerAction);
            timerAction.setTimer(timer);
        } catch (Throwable e) {
            e.printStackTrace();
            return false;
        }
        System.out.println("初始化摄像头 finish " + (new Date().getTime() - startTime) / 1000);
        return true;
    }

    /**
     * 拍摄图片
     */
    public void cameraPhoto(String fileName, String photoName) throws Throwable, Exception {
        long startTime = new Date().getTime();
        System.out.println("拍摄图片 start " + startTime);
        final BufferedImage bImage = new BufferedImage(640, 480, BufferedImage.TYPE_INT_RGB);
        Graphics2D bGraphics = bImage.createGraphics();
        String savedImageFile = camera + fileName + "/" + photoName + ".jpg";
        // 20140811/P1407280001/N1000688553
        File file = new File(camera + fileName);// 判断文件夹是否存在
        if (!file.exists() && !file.isDirectory()) {
            file.mkdirs();// 不存在创建一个新的
        }
        // grabber = new OpenCVFrameGrabber(0);
        // grabber.start();
        IplImage image = grabber.grab();// 将所获取摄像头数据放入IplImage
        IplImage newImage = grabber.grab();// 将所获取摄像头数据放入IplImage
        canvasFrame.showImage(image);
        // draw the onscreen image simutaneously
        bGraphics.drawImage(newImage.getBufferedImage(), null, 0, 0);
        ImageIO.write(bImage, "jpg", new File(savedImageFile));
        // cvReleaseImage(image);// 释放内存当中的图片
        // grabber.stop();
        System.out.println("拍摄图片 finish " + (new Date().getTime() - startTime) / 1000);
    }

    /**
     * 关闭摄像头
     */
    public void closeGrabber() throws Throwable, Exception {
        long startTime = new Date().getTime();
        System.out.println("关闭摄像头 start " + startTime);
        grabber.stop();
        System.out.println("关闭摄像头 finish " + (new Date().getTime() - startTime) / 1000);
    }

    /**
     * 获取需要上传图片的名称(文件夹内所有文件)
     */
    public List<String> getFileName(String url) {
        List<String> fileNameList = new ArrayList<String>();
        File file = new File(camera + url);// 目标文件夹
        if (!file.isDirectory()) {
            // fileNameList.add(file.getName());
        } else if (file.isDirectory()) {
            String[] list = file.list();
            for (int i = 0; i < list.length; i++) {
                File files = new File(camera + url + "/" + list[i]);// 文件夹下的所有文件
                if (!files.isDirectory()) {
                    // 只读取JPG文件
                    if (files.getName().substring(files.getName().length() - 3, files.getName().length()).equals("jpg")) {
                        fileNameList.add(files.getName());
                    }
                }
            }
        }
        return fileNameList;
    }

    /**
     * 图片压缩成zip
     * 
     */
    public String creatZip(String url, String zipName) throws Throwable, Exception {
        long startTime = new Date().getTime();
        System.out.println("图片压缩成zip start " + startTime);
        List<String> fileNameList = getFileName(url);
        // String zipName = String.valueOf(System.currentTimeMillis());
        File zipFile = new File(camera + url + "/" + zipName + ".zip");
        InputStream input = null;
        ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(zipFile));
        for (String fileName : fileNameList) {
            input = new FileInputStream(camera + url + "/" + fileName);
            zipOut.putNextEntry(new ZipEntry(fileName));
            int temp = 0;
            while ((temp = input.read()) != -1) {
                zipOut.write(temp);
            }
            input.close();
        }
        zipOut.close();
        System.out.println("图片压缩成zip finish " + (new Date().getTime() - startTime) / 1000);
        return zipName;
    }

    /**
     * 把图片ZIP上传到服务器上
     */
    public void updateHttp(String url, String fileName) throws Throwable, Exception {
        long startTime = new Date().getTime();
        System.out.println("把图片ZIP上传到服务器上 start " + startTime);
        try {
            sendPost(url, fileName);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        System.out.println("把图片ZIP上传到服务器上 finish " + (new Date().getTime() - startTime) / 1000);
    }

    // storage/json/sec_upload

    @SuppressWarnings("resource")
	public void sendPost(String url, String fileName) throws Throwable, Exception {
        HttpClient httpclient = new DefaultHttpClient();
        // HttpPost post = new HttpPost("http://10.8.12.151:8084/storage/upload");
        try {
            HttpPost post = new HttpPost(bundle.getString("zipUrl"));
            FileBody fileBody = new FileBody(new File(camera + url + "/" + fileName + ".zip"));
            StringBody systemCode = new StringBody("wms");
            StringBody iszip = new StringBody("1");// 是否zip
            StringBody iscover = new StringBody("1");// 是否覆盖
            StringBody sign = new StringBody(getMD5Str(bundle.getString("md5")));// 对接编码
            StringBody path = new StringBody("/" + url);// 对接编码
            MultipartEntity entity = new MultipartEntity();
            entity.addPart("files", fileBody);
            entity.addPart("systemCode", systemCode);
            entity.addPart("iszip", iszip);
            entity.addPart("iscover", iscover);
            entity.addPart("sign", sign);
            entity.addPart("path", path);
            post.setEntity(entity);
            HttpResponse response = httpclient.execute(post);
            if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
                HttpEntity entitys = response.getEntity();
                if (entity != null) {
                    System.out.println(entity.getContentLength());
                    System.out.println(EntityUtils.toString(entitys));
                }
            }
            httpclient.getConnectionManager().shutdown();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }


    /**
     * MD5加密
     */
    public static String getMD5Str(String source) throws Throwable, Exception {
        MessageDigest digest = null;
        digest = MessageDigest.getInstance("MD5");
        digest.reset();
        digest.update(source.getBytes("UTF-8"));
        byte[] byteArray = digest.digest();
        StringBuffer md5StrBuff = new StringBuffer();
        for (int i = 0; i < byteArray.length; i++) {
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
                md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
            else
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
        }
        return md5StrBuff.toString();
    }

}
