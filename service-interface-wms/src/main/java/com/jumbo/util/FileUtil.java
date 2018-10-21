package com.jumbo.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class FileUtil implements Serializable {


    private static final long serialVersionUID = 4170834903565456217L;
    protected static final Logger log = LoggerFactory.getLogger(FileUtil.class);

    public static byte[] readFileToByteArray(File f) throws IOException {
        BufferedInputStream bin = new BufferedInputStream(new FileInputStream(f));
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        while (true) {
            int readCount = bin.read(buffer);
            if (readCount == -1) {
                break;
            }
            bout.write(buffer, 0, readCount);
        }
        bout.flush();
        bout.close();
        bin.close();
        return bout.toByteArray();
    }

    public static byte[] readFileToCompressedByteArray(File f) throws IOException {
        Deflater compresser = new Deflater(Deflater.BEST_COMPRESSION);
        BufferedInputStream bin = new BufferedInputStream(new FileInputStream(f));
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        DeflaterOutputStream dos = new DeflaterOutputStream(bout, compresser);
        byte[] buffer = new byte[1024];
        while (true) {
            int readCount = bin.read(buffer);
            if (readCount == -1) {
                break;
            }
            dos.write(buffer, 0, readCount);
        }
        dos.flush();
        dos.close();
        bin.close();
        return bout.toByteArray();
    }

    public static void writeByteArrayToFile(File f, byte[] data) throws IOException {
        BufferedOutputStream bout = new BufferedOutputStream(new FileOutputStream(f));
        bout.write(data);
        bout.flush();
        bout.close();
    }

    public static void writeCompressedByteArrayToFile(File f, byte[] data) throws IOException {
        Inflater decompresser = new Inflater();
        ByteArrayInputStream bin = new ByteArrayInputStream(data);
        InflaterInputStream iis = new InflaterInputStream(bin, decompresser);
        FileOutputStream fout = new FileOutputStream(f);
        byte[] buffer = new byte[1024];
        while (true) {
            int readCount = iis.read(buffer);
            if (readCount == -1) {
                break;
            }
            fout.write(buffer, 0, readCount);
        }
        fout.flush();
        fout.close();
        iis.close();
    }

    public static File dynamicCreateDir(String path) throws IOException {
        File dir = new File(path);
        if (dir.exists()) {
            if (dir.isDirectory()) {
                return dir;
            } else {
                throw new IOException("File exists of that name");
            }
        } else {
            dir.mkdirs();
            return dir;
        }
    }

    // 判断文件或者文件夹是否存在
    public static boolean isFileExists(String filePath) {

        File file = new File(filePath);

        if (file.exists()) {
            return true;
        } else {
            return false;
        }
    }

    // 写文件
    public static void writeFile(File file, List<String> lines) throws IOException {
        FileWriter fw = new FileWriter(file);
        PrintWriter out = new PrintWriter(fw);
        // BufferedWriter bw=new BufferedWriter(out);
        for (String line : lines) {
            out.write(line);
            out.println();
        }
        fw.close();
        // bw.close();
        out.close();
    }

    public static boolean copyFile(String oldPath, String newPath) {
        boolean flag = true;
        try {
            int byteread = 0;
            File oldfile = new File(oldPath);
            File newFile = new File(newPath);
            if (!newFile.exists()) {
                newFile.mkdirs();
            }
            newPath = newPath + File.separator + oldfile.getName();
            if (oldfile.exists()) {
                // 文件存在时
                // 读入原文件
                InputStream inStream = new FileInputStream(oldPath);
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1024];
                while ((byteread = inStream.read(buffer)) != -1) {
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
                fs.close();
            }
        } catch (Exception e) {
            log.error("", e);
            flag = false;
        }
        return flag;
    }

    public static boolean deleteFile(String filePath) {
        boolean flag = false;
        try {
            File myDelFile = new File(filePath);
            flag = myDelFile.delete();
        } catch (Exception e) {
            // System.out.println("删除文件操作出错 ");
            log.error("", e);

        }
        return flag;
    }

    /**
     * 根据url下载的指定的目录下
     * 
     * @param urlStr
     * @param filePath
     * @throws IOException
     */
    public static void downloadFile(String urlStr, String filePath, String encoding) throws IOException {
        URL url = new URL(urlStr);
        HttpURLConnection httpUrl = (HttpURLConnection) url.openConnection();
        httpUrl.connect();
        InputStream is = null;
        BufferedInputStream bis = null;
        OutputStreamWriter bos = null;
        ZipInputStream zipIn = null;
        try {
            is = httpUrl.getInputStream();
            if (httpUrl.getContentLength() == 0) {
                return;
            } else {
                zipIn = new ZipInputStream(is);
                BufferedReader br = new BufferedReader(new InputStreamReader(zipIn, "GBK"));
                ZipOutputStream zipout = new ZipOutputStream(new FileOutputStream(filePath));
                bos = new OutputStreamWriter(zipout, encoding);
                ZipEntry outEntry = zipIn.getNextEntry();
                String line = null;
                while (outEntry != null) {
                    zipout.putNextEntry(outEntry);
                    while ((line = br.readLine()) != null) {
                        bos.write(line);
                    }
                    outEntry = zipIn.getNextEntry();
                    bos.flush();
                }
                zipout.close();
                br.close();
                bos.close();
            }
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException(filePath + " File Not Found");
        } finally {
            if (is != null) {
                is.close();
                is = null;
            }
            if (bis != null) {
                bis.close();
                bis = null;
            }
            if (zipIn != null) {
                zipIn.close();
                zipIn = null;
            }
        }
    }

    /**
     * 获取zip文件的list文本
     * 
     * @param fileName
     * @return
     */
    public static List<String> getZipFileText(String fileName, String encoding) {
        List<String> list = new ArrayList<String>();
        try {
            // 构造ZipFile
            ZipFile zf = new ZipFile(new File(fileName), ZipFile.OPEN_READ);
            // 返回 ZIP file entries的枚举.
            Enumeration<? extends ZipEntry> entries = zf.entries();
            while (entries.hasMoreElements()) {
                StringBuffer text = new StringBuffer();
                ZipEntry ze = entries.nextElement();
                long size = ze.getSize();
                if (size > 0) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(zf.getInputStream(ze), encoding));
                    String line;
                    while ((line = br.readLine()) != null) {
                        text.append(line);
                    }
                    br.close();
                }
                if (text != null) {
                    list.add(text.toString());
                }
            }
            zf.close();
        } catch (IOException e) {
            log.error("", e);
        }
        return list;
    }

    public static File[] getDirFile(String path) {
        File dirFile = new File(path);
        if (dirFile.exists() && dirFile.isDirectory()) {
            return dirFile.listFiles();
        } else {
            return null;
        }
    }

    public static void writeStringToFile(File f, String data) throws IOException {
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f, false)));
        out.write(data + "\r\n");
        out.flush();
        out.close();
    }

    public static List<String> getDirInFileName(String dirPath, String split) {
        List<String> listFileName = new ArrayList<String>();
        File dirFile = new File(dirPath);
        if (dirFile.exists() && dirFile.isDirectory()) {
            for (File file : dirFile.listFiles()) {
                if (file.getName().indexOf(split) > -1) {
                    listFileName.add(file.getPath());
                }
            }
        }
        return listFileName;
    }

    public static List<File> traveFile(String dirPath) {
        LinkedList<File> fileList = new LinkedList<File>();
        File dir = new File(dirPath);
        if (dir != null) {
            File files[] = dir.listFiles();
            if (files != null) {
                for (File f : files) {
                    if (!f.isDirectory()) {
                        fileList.add(f);
                    }
                }
            }

        }
        return fileList;
    }

    /**
     * 更新XML编码
     * 
     * @param file
     * @param s
     * @param encoding
     */
    public static void updateXmlFile(File file, String s, String encoding) {
        try {
            File file1 = new File(file.getPath() + "_bakj");
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file1)));
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), encoding));
            String line;
            while ((line = br.readLine()) != null) {
                if (line.indexOf("xmlns=") == -1) {
                    String replaceStr = line.replace("xmlns:xsi", s);
                    String subStr = replaceStr.substring(0, 1);
                    if ("<".equals(subStr.trim())) {
                        bw.write(replaceStr);
                    } else {
                        bw.write(replaceStr.substring(1, line.replace("xmlns:xsi", s).length()));
                    }
                } else {
                    br.close();
                    bw.close();
                    return;
                }
            }
            br.close();
            bw.flush();
            bw.close();
            String name = file.getPath();
            file.delete();
            file1.renameTo(new File(name));
        } catch (Exception e) {
            // e.printStackTrace();
            if (log.isErrorEnabled()) {
                log.error("updateXmlFile Exception！", e);
            }
        }
    }

}
