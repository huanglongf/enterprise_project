package com.bt.common.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;


/** 
	* @Description: 
	* @author  Hanery:
 	* @date 2017年7月21日 上午9:31:44  
*/
public class FTPUtil {

	 /**
	 * 下载文件
	 * @param hostname FTP服务器地址
	 * @param port FTP服务器端口号
	 * @param username FTP登录帐号
	 * @param password FTP登录密码
	 * @param pathname FTP服务器文件目录
	 * @param filename 文件名称
	 * @param localpath 下载后的文件路径
	 * @return
	 */
	public static boolean downloadFile(String hostname, int port, String username, String password, String pathname, String filename, String localpath){
		 boolean flag = false;
		 FTPClient ftpClient = new FTPClient();
		 ftpClient.setControlEncoding("GBK");
		 try {
		 //连接FTP服务器
		 ftpClient.connect(hostname, port);
		 //登录FTP服务器
		 ftpClient.login(username, password);
		 //验证FTP服务器是否登录成功
		 int replyCode = ftpClient.getReplyCode();
		 if(!FTPReply.isPositiveCompletion(replyCode)){
		 return flag;
		 }
		 //切换FTP目录
		 ftpClient.changeWorkingDirectory(pathname);
		 ftpClient.enterLocalPassiveMode();
		 FTPFile[] ftpFiles = ftpClient.listFiles();
		 for(FTPFile file : ftpFiles){
			 if(!file.getName().equals(".")&&!file.getName().equals("..")){
				 if(filename.equalsIgnoreCase(file.getName())){
				 File localFile = new File(localpath + "/" + file.getName());
				 FileOutputStream os = new FileOutputStream(localFile);
				 ftpClient.retrieveFile(file.getName(), os);
				 os.close();
				 }
			 }
		 }
		 ftpClient.logout();
		 flag = true;
		 } catch (Exception e) {
		 e.printStackTrace();
		 } finally{
		 if(ftpClient.isConnected()){
		 try {
		 ftpClient.logout();
		 } catch (IOException e) {
		  
		 }
		 }
		 }
		 return flag;
		}
	
	
	
	/**  
	  * @param path  
	  * @return function:读取指定目录下的文件名  
	  * @throws IOException  
	  */   
	public static List<String> getFileList(String hostname, int port, String username, String password, String pathname) {   
		List<String> fileLists = new ArrayList<String>();   
		  FTPClient ftpClient = new FTPClient();
			 try {
			 ftpClient.setControlEncoding("GBK");
			 //连接FTP服务器
			 ftpClient.connect(hostname, port);
			 //登录FTP服务器
			 ftpClient.login(username, password);
			 //验证FTP服务器是否登录成功
			 int replyCode = ftpClient.getReplyCode();
			 if(!FTPReply.isPositiveCompletion(replyCode)){
				 return fileLists;
			 }
			 //切换FTP目录
			 
			 ftpClient.changeWorkingDirectory(pathname);
			 ftpClient.enterLocalPassiveMode();
			 FTPFile[] ftpFiles = ftpClient.listFiles();
			 for(FTPFile file : ftpFiles){
				 if(!file.getName().equals(".")&&!file.getName().equals("..")){
					 fileLists.add(file.getName());
				 }
			 }
			 }catch (Exception e) {
				 e.printStackTrace();
			 } finally{
			 if(ftpClient.isConnected()){
			 try {
			 ftpClient.logout();
			 } catch (IOException e) {
				 e.printStackTrace();
			 }
			 }
			 }
		  return fileLists;   
		}   
	public static void main(String[] args) {
		String path = null;
		/*String hostname = "10.7.46.46";
		int port = 21;
		String username = "op_user01";
		String password = "baozun123";
		String pathname = "file";*/
		String hostname = "167.64.85.193";
		int port = 21;
		String username = "custprog";
		String password = "Fd26e3jm";
		String pathname = "custshoe_v/china";
		String filename = "1.txt";
		String localpath = "e:\\";
		List<String> fileList = getFileList(hostname, port, username, password, pathname);
		System.out.println(fileList);
		//downloadFile(hostname, port, username, password, pathname, filename, localpath);
		
		
	}
}
