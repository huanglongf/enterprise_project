package com.jumbo.util.comm;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;


@SuppressWarnings("deprecation")
public class HttpClientDownUtil {

    public static final int cache = 100 * 1024;
    public static final boolean isWindows;
    public static final String splash;
    static {
        if (System.getProperty("os.name") != null && System.getProperty("os.name").toLowerCase().contains("windows")) {
            isWindows = true;
            splash = "\\";
        } else {
            isWindows = false;
            splash = "/";
        }
    }

    /**
     * 根据url下载文件，文件名从response header头中获取
     * 
     * @param url
     * @return
     * @throws IOException
     */
    public static String download(String url) throws IOException {
        return download(url, null, null);
    }

    /**
     * 根据url下载文件，保存到filepath中
     * 
     * @param url
     * @param filepath
     * @return
     * @throws IOException
     * @throws ClientProtocolException
     */
    @SuppressWarnings("resource")
	public static String download(String url, String rootPath, String fileName) throws IOException {
        HttpClient client = new DefaultHttpClient();
        HttpGet httpget = new HttpGet(url);
        HttpResponse response = client.execute(httpget);

        HttpEntity entity = response.getEntity();
        InputStream is = entity.getContent();
        String fliePath = getFilePath(response, rootPath, fileName);


        File file = new File(fliePath);
        file.getParentFile().mkdirs();
        FileOutputStream fileout = new FileOutputStream(file);
        /**
         * 根据实际运行效果 设置缓冲区大小
         */
        byte[] buffer = new byte[cache];
        int ch = 0;
        while ((ch = is.read(buffer)) != -1) {
            fileout.write(buffer, 0, ch);
        }
        is.close();
        fileout.flush();
        fileout.close();

        return null;
    }

    /**
     * 获取response要下载的文件的默认路径
     * 
     * @param response
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String getFilePath(HttpResponse response, String rootPath, String fileName) throws UnsupportedEncodingException {
        String filepath = rootPath + splash;
        String filename = getFileName(response);

        if (filename != null && !"".equals(filename)) {
            if (fileName != null && !"".equals(fileName)) {
                // 更改文件名
                String suffix = filename.substring(filename.lastIndexOf("."));
                filename = fileName + suffix;
            }
            
            filepath += filename;

        } else {
            filepath += getRandomFileName();
        }
        return filepath;
    }

    /**
     * 获取response header中Content-Disposition中的filename值
     * 
     * @param response
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String getFileName(HttpResponse response) throws UnsupportedEncodingException {
        Header contentHeader = response.getFirstHeader("Content-Disposition");
        String filename = null;
        if (contentHeader != null) {
            HeaderElement[] values = contentHeader.getElements();
            if (values.length == 1) {
                NameValuePair param = values[0].getParameterByName("filename");
                if (param != null) {
                    filename = param.getValue();

                }
            }

        }
        return filename;
    }

    /**
     * 获取随机文件名
     * 
     * @return
     */
    public static String getRandomFileName() {
        return String.valueOf(System.currentTimeMillis());
    }

}
