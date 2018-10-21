package com.bt.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;

public class FileUtil {
	
	/** 
	 * 复制单个文件
	 * @param srcFileName 待复制的文件名 
	 * @param descFileName 目标文件名 
	 * @param overlay 如果目标文件存在，是否覆盖 
	 * @return 如果复制成功返回true，否则返回false 
	 */  
	public static boolean copyFile(String srcFileName, String destFileName, boolean overlay) {  
       File srcFile = new File(srcFileName);
       // 判断源文件是否存在  
       if (!srcFile.exists()) {  
    	   System.out.println("源文件：" + srcFileName + "不存在！");
           return false;
           
       } else if (!srcFile.isFile()) {
    	   System.out.println("复制文件失败，源文件：" + srcFileName + "不是一个文件！");
           return false;
           
       }
       // 判断目标文件是否存在  
       File destFile= new File(destFileName);  
       if (destFile.exists()) {  
           // 如果目标文件存在并允许覆盖  
           if (overlay) {  
               // 删除已经存在的目标文件，无论目标文件是目录还是单个文件  
               new File(destFileName).delete();
               
           }
           
       } else {  
           // 如果目标文件所在目录不存在，则创建目录  
           if (!destFile.getParentFile().exists()) {  
               // 目标文件所在目录不存在  
               if (!destFile.getParentFile().mkdirs()) {  
                   // 复制文件失败：创建目标文件所在目录失败  
                   return false;
                   
               }
               
           }
           
       }
       // 复制文件
       // 读取的字节数
       int byteread= 0;
       InputStream in= null;  
       OutputStream out= null;
       try {  
           in= new FileInputStream(srcFile);
           out= new FileOutputStream(destFile);
           byte[] buffer= new byte[1024];
           while ((byteread= in.read(buffer)) != -1) {
               out.write(buffer, 0, byteread);
               
           }  
           return true;
           
       } catch (FileNotFoundException e) {  
           return false;
           
       } catch (IOException e) {
           return false;
           
       } finally {  
           try {  
               if(out != null)out.close();
               if(in != null)in.close();
               
           } catch (IOException e) {  
               e.printStackTrace();
               
           }
           
       }
       
   	}  
	
	/**
	 * 判断路径是否存在，不存在则创建
	 * 
	 * @param path
	 */
	public static void isExistPath(String filePath) {
		// 将路径按"\\"分割为数组
		// 传入的path由"\\"分隔，转义为"\"
		// 分割path是对"\\"进行的，所以分割条件为"\\\\"，转义为"\\"
		String[] paths= {""};
		//切割路径
        try {
        	//File对象转换为标准路径并进行切割，有两种windows和linux
            String tempPath= new File(filePath).getCanonicalPath();
            // windows
            paths= tempPath.split("\\\\");
            //linux
            if(paths.length == 1){paths= tempPath.split("/");}
            
        } catch (IOException e) {
            System.out.println("切割路径错误");
            e.printStackTrace();
            
        }
        // 判断是否有后缀
        boolean hasType= false;
        if(paths.length> 0){
            String tempPath= paths[paths.length-1];
            if(tempPath.length() > 0) {
                if(tempPath.indexOf(".")>0) {
                    hasType=true;
                    
                }
                
            }
            
        }        
        //创建文件夹
        String dir= paths[0];
        // 注意此处循环的长度，有后缀的就是文件路径，没有则文件夹路径
        for (int i= 0; i< paths.length- (hasType? 2: 1); i++) {
            try {
            	// 采用linux下的标准写法进行拼接，由于windows可以识别这样的路径，所以这里采用警容的写法
                dir= dir+ "/"+ paths[i+ 1];
                File dirFile= new File(dir);
                if (!dirFile.exists()) {
                    dirFile.mkdir();
                    System.out.println("成功创建目录：" + dirFile.getCanonicalFile());
                    
                }
                
            } catch (Exception e) {
                System.err.println("文件夹创建发生异常");
                e.printStackTrace();
                
            }
            
        }
        
	}

	/**
	 * 判断路径下文件是否存在，若存在则删除，再创建
	 * 
	 * @param path
	 * @throws IOException 
	 */
	public static void isExistFile(String path) throws IOException {
		File file = new File(path);
		// 如文件对象为文件且该文件已存在，则删除后重建
		if(file.exists() && file.isFile()){
			file.delete();
			
		}
		
	}
	
	/**
	 * 
	 * @Description: TODO
	 * @param path
	 * @throws IOException
	 * @return: Boolean  
	 * @author Ian.Huang 
	 * @date 2017年2月22日下午5:26:47
	 */
	public static Boolean judgeFileExistOrNot(String path) throws IOException {
		File file = new File(path);
		if(file.exists() && file.isFile()){
			return true;
			
		} else {
			return false;
			
		}
		
	}
	
	public static boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			//递归删除目录中的子目录下
	        for (int i=0; i<children.length; i++) {
	            boolean success=deleteDir(new File(dir, children[i]));
	            if (!success) {
	                return false;
	            }
	        }
	    }
	    // 目录此时为空，可以删除
	    return dir.delete();
	    
	}
	
	public static void createImage(String imgurl, String filePath)  {  
		try{
    	    //路径补全
    		isExistPath(filePath);
    	    URL url = new URL(imgurl);  
    	  
    	    HttpURLConnection conn = (HttpURLConnection) url.openConnection();  
    	    InputStream inputStream = conn.getInputStream(); // 通过输入流获得图片数据  
    	    
    	    byte[] buffer = new byte[1024];  
            int len = 0;  
            ByteArrayOutputStream bos = new ByteArrayOutputStream();  
            
            while ((len = inputStream.read(buffer)) != -1) {  
                bos.write(buffer, 0, len);  
            }  
            bos.close();
            
    //        do {
    //            len = inputStream.read(buffer);
    //            bos.write(buffer, 0, len);
    //        } while (inputStream.available() != 0);
            
            
    	    byte[] getData = bos.toByteArray(); // 获得图片的二进制数据  
    	  
    	    File imageFile = new File(filePath);  
    	    FileOutputStream fos = new FileOutputStream(imageFile); 
    	    fos.write(getData);  
    	    fos.close();  
		}catch (Exception e) {
		    String message = e.getMessage();
            if (message.contains("bort")) {
                
            }else if(message.contains("ocketException")){
                
            }else{
                e.printStackTrace();
            }
        }
	    
	}  
	  
	public static void main(String[] args) throws Exception {  
	    String imgurl = "http://10.88.26.50:8084/f65f3e59-fed1-4510-9239-1a622b0d4f1a.jpg";  
	    String suffix = FilenameUtils.getExtension(imgurl);  
	    String uuid = UUID.randomUUID().toString();  
	    String filePath = "e:\\" + uuid + "." + suffix;  
	    createImage(imgurl, filePath);  
	    System.out.println(" read picture success:"+filePath);  
	}  
	  
	public static byte[] readInputStream(InputStream inputStream) throws IOException {  
	    byte[] buffer = new byte[1024];  
	    int len = 0;  
	    ByteArrayOutputStream bos = new ByteArrayOutputStream();  
	    while ((len = inputStream.read(buffer)) != -1) {  
	        bos.write(buffer, 0, len);  
	    }  
	    bos.close();  
	    return bos.toByteArray();  
	}

	/**
	 * @param filepath 源文件路径
	 * @param fileName	下载的文件名
	 * @param resp	响应
	 */
	public static void downloadFile(String filepath,String fileName, HttpServletResponse resp) {
	    OutputStream output = null;
	    try{
			byte[] bytes = null;
			@SuppressWarnings("resource")
            FileInputStream inputStream = new FileInputStream(filepath);
			byte[] buffer = new byte[1024];  
		    int len = 0;  
		    ByteArrayOutputStream bos = new ByteArrayOutputStream();  
		    while ((len = inputStream.read(buffer)) != -1) {  
		        bos.write(buffer, 0, len);  
		    }  
		    bos.close();  
		    bytes = bos.toByteArray();  
			int length=0;
			if(bytes!=null){
				length = bytes.length;
			}
			resp.setContentType("application/msexcel;charset=utf-8");
			resp.setHeader("Content-disposition", "attachment; filename="+ new String((fileName).getBytes("GB2312"),"ISO8859-1")); 
			resp.setContentLength(length);
			output = resp.getOutputStream();
			if(bytes != null){
				output.write(bytes);
			}
			output.flush();
			resp.flushBuffer();
			
		}catch (Exception e) {
		    String message = e.getMessage();
            if (message.contains("bort")) {
                
            } else if(message.contains("ocketException")){
            
            }else{
                e.printStackTrace();
            }
		}finally{
		    try{
		        if (output != null) {
		            output.close();
		        }
            }catch (IOException e){
                e.printStackTrace();
            }
		}
	}
	
}