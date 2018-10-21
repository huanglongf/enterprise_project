package com.lmis.common.util;

import java.io.File;

/** 
 * @ClassName: FileUtils
 * @Description: TODO(文件工具类)
 * @author Ian.Huang
 * @date 2017年12月26日 下午5:48:43 
 * 
 */
public class FileUtils {

	/**
	 * @Title: isExistence
	 * @Description: TODO(判断文件名对应文件是否存在【路径需全】)
	 * @param fileName
	 * @return: Boolean
	 * @author: Ian.Huang
	 * @date: 2017年12月26日 下午5:55:19
	 */
	public static Boolean isExistence(String fileName) {
		return isExistence(new File(fileName));
	}
	
	/**
	 * @Title: isExistence
	 * @Description: TODO(判断文件是否存在)
	 * @param file
	 * @return: Boolean
	 * @author: Ian.Huang
	 * @date: 2017年12月26日 下午5:55:42
	 */
	public static Boolean isExistence(File file) {
		if(file.exists() && file.isFile()) {
			return true;
		}
		return false;
	}
	
	/**
	 * @Title: deleteDir
	 * @Description: TODO(删除文件夹)
	 * @param dir
	 * @return: Boolean
	 * @author: Ian.Huang
	 * @date: 2017年12月26日 下午5:56:57
	 */
	public static Boolean deleteDir(File dir) {
		if (dir.exists() && dir.isDirectory()) {
			String[] children = dir.list();
			
			// 递归删除目录中的子目录下
	        for (int i = 0; i < children.length; i++) {
	        	boolean success = deleteDir(new File(dir, children[i]));
	            if (!success) {
	                return false;
	            }
	        }
	    }
		
	    // 目录此时为空，可以删除
	    return dir.delete();
	}
	
}
