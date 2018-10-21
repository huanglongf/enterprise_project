package com.lmis.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/** 
 * @ClassName: ZipUtils
 * @Description: TODO(zip工具类)
 * @author Ian.Huang
 * @date 2017年12月21日 下午7:07:07 
 * 
 */
public class ZipUtils {

	private static final int BUFFER = 1024;
	
	/**
	 * @Title: zip
	 * @Description: TODO(生成zip文件)
	 * @param input
	 * @param outputDir
	 * @throws Exception
	 * @return: void
	 * @author: Ian.Huang
	 * @date: 2017年12月18日 下午6:04:03
	 */
	public static void zip(String input, String outputDir) throws Exception {
		
		// 输入（文件/文件夹）
		File inputFile= new File(input);
		
        // 创建压缩文件
        File destDir = new File(outputDir);
        if (!destDir.exists()) destDir.mkdir();
        
        // 递归压缩方法
        // 设置压缩编码，默认为UTF-8
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(new File(destDir + File.separator + inputFile.getName()+ ".zip")));
        zip(out, inputFile, "");
        System.out.println("zip done");
        out.close();
	}
	
	/**
	 * @Title: zip
	 * @Description: TODO(递归压缩方法)
	 * @param out
	 * @param f
	 * @param base
	 * @throws Exception
	 * @return: void
	 * @author: Ian.Huang
	 * @date: 2017年12月18日 下午6:04:18
	 */
	public static void zip(ZipOutputStream out, File f, String base) throws Exception {
		int BUFFER = 1024;
		
		// 记录日志，开始压缩
		System.out.println("Zipping " + f.getName());
        if (f.isDirectory()) {
        	
        	// 如果是文件夹，则获取下面的所有文件
        	File[] fl = f.listFiles();
            if (base.length() > 0) {
            	
            	// 此处要将文件写到文件夹中只用在文件名前加"/"再加文件夹名
            	out.putNextEntry(new ZipEntry(base + "/"));
            }
            base = base.length() == 0 ? "" : base + "/";
            for (int i = 0; i < fl.length; i++) {
            	zip(out, fl[i], base + fl[i].getName());
            }
        } else {
        	
        	// 如果是文件，则压缩
        	// 生成下一个压缩节点
	        out.putNextEntry(new ZipEntry(base));
	        
	        // 读取文件内容
	        FileInputStream in = new FileInputStream(f);
	        int len;
	        byte[] buf = new byte[BUFFER];
	        while ((len = in.read(buf, 0, BUFFER)) != -1) {
	        	// 写入到压缩包
	        	out.write(buf, 0, len);
	        }
	        in.close();
	        out.closeEntry();
        }
	}
	
    /**
     * @Title: unZip
     * @Description: TODO(解压缩zip文件)
     * @param fileName 要解压的文件名，包含路径，如："c:\\test.zip"
     * @param destFilePath 解压后存放文件的路径 如："c:\\temp"
     * @throws Exception
     * @return: void
     * @author: Ian.Huang
     * @date: 2017年12月21日 下午7:09:27
     */
    public static void unZip(String fileName, String destFilePath) throws Exception {
    	
    	// 以UTF-8编码创建zip文件，用来处理winRAR压缩的文件。
    	ZipFile zipFile = new ZipFile(fileName, StandardCharsets.UTF_8);
        Enumeration<?> emu = zipFile.entries();
        while (emu.hasMoreElements()) {
        	ZipEntry entry = (ZipEntry) emu.nextElement();
            if (entry.isDirectory()) {
                File dir = new File(destFilePath + entry.getName());
                if (!dir.exists()) dir.mkdirs();
                continue;
            }
            BufferedInputStream bis = new BufferedInputStream(zipFile.getInputStream(entry));
            File file = new File(destFilePath + entry.getName());
            File parent = file.getParentFile();
            if (parent != null && (!parent.exists())) parent.mkdirs();
            FileOutputStream fos = new FileOutputStream(file);
            BufferedOutputStream bos = new BufferedOutputStream(fos, BUFFER);
            byte[] buf = new byte[BUFFER];
            int len = 0;
            while ((len = bis.read(buf, 0, BUFFER)) != -1) fos.write(buf, 0, len);
            bos.flush();
            bos.close();
            bis.close();
            System.out.println("解压文件：" + file.getName());
        }
        zipFile.close();
    }
	
}
