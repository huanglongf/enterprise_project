package com.bt.common.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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
	public static void writeByteArrayToFile(File f, byte[] data) throws IOException {
        BufferedOutputStream bout = new BufferedOutputStream(new FileOutputStream(f));
        bout.write(data);
        bout.flush();
        bout.close();
    }
	
}
