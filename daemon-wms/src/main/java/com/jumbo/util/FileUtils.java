package com.jumbo.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUtils {
    protected static final Logger log = LoggerFactory.getLogger(FileUtils.class);
    // @Value("${error.file.path}")
    private static String FILE_PATH = "/home/appuser/wms/daemon/error/";// smtp服务器
    // @Value("${error.file.name}")
    private static String FILE_NAME = "msg.txt";// smtp服务器
    // @Value("${mail.mailBody}")
    private static String MAIL_BODY = "异常定时任务：";// 邮件内容副本

    private static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public final static int TYPE_WRITE = 1; // 写入文件
    public final static int TYPE_READ = 2;// 读取文件
    public final static int TYPE_DELETE = 3;// 删除文件


    public final synchronized static String operateFile(int type, Date date, String method_, String class_) {
        switch (type) {
            case TYPE_WRITE:
                writeFile(method_, class_, date);
                return null;
            case TYPE_READ:
                return readFile();
            case TYPE_DELETE:
                deleteFile();
                return null;
            default:
                throw new IllegalArgumentException();
        }
    }

    /**
     * 写入文件
     * 
     * @param method_
     * @param class_
     * @param date
     */
    private final synchronized static void writeFile(String method_, String class_, Date date) {
        try {
            FileWriter fw = null;
            File pathFile = new File(FILE_PATH);
            if (!pathFile.exists()) {
                pathFile.mkdirs();
            }
            File file = new File(FILE_PATH + FILE_NAME);
            if (!file.exists()) {
                file.createNewFile();
            }
            // 如果文件存在，则追加内容；如果文件不存在，则创建文件
            fw = new FileWriter(file, true);
            PrintWriter pw = new PrintWriter(fw);
            pw.println(df.format(date) + MAIL_BODY + class_ + "." + method_);
            pw.flush();
            fw.flush();
            pw.close();
            fw.close();
        } catch (IOException e) {
            // e.printStackTrace();
            if (log.isErrorEnabled()) {
                log.error("FileUtils writeFile error", e);
            }
        }
    }

    /**
     * 文件读取
     * 
     * @return 文件内容
     * @throws IOException
     */
    private final synchronized static String readFile() {
        StringBuffer sb = new StringBuffer();
        File file = new File(FILE_PATH + FILE_NAME);
        if (!file.exists()) return null;
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            // e.printStackTrace();
            if (log.isErrorEnabled()) {
                log.error("FileUtils readFile IOException", e);
            }
        }
        try {
            if (br != null) {
                br.close();
            }
        } catch (Exception e) {
            log.error("", e);
        }
        return sb.toString();
    }

    /**
     * 文件删除
     */
    private final synchronized static void deleteFile() {
        try {
            File file = new File(FILE_PATH + FILE_NAME);
            if (file.isFile()) {
                file.delete();
            }
        } catch (Exception e) {}
    }
}
