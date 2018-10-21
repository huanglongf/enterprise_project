package com.bt.common;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;

import com.alibaba.fastjson.JSON;
import com.bt.OSinfo;
import com.bt.utils.CommonUtils;

/**
 * 
* @ClassName: UploadServlet 
* @Description: TODO(文件上传) 
* @author likun
* @date 2017年3月9日 下午4:51:03 
*
 */
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 8463846351474716626L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8;");
		request.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		HashMap<String,String> result=new HashMap<String,String>();
		DiskFileItemFactory fac = new DiskFileItemFactory();
		String fileRealPath = "";// 文件存放真实地址
		String formatName="";
		String realName="";
		String firstFileName = "";
		String Uid=UUID.randomUUID().toString();
		try {
			String path= CommonUtils.getAllMessage("config", "WO_UPLOAD_" + OSinfo.getOSname());
			ServletFileUpload upload= new ServletFileUpload(fac);
			upload.setHeaderEncoding("UTF-8");
			// 获取多个上传文件
			List<?> fileList= upload.parseRequest(request);
			Iterator<?>it= fileList.iterator();
			while (it.hasNext()) {
				Object obit= it.next();
				if (obit instanceof DiskFileItem) {
					DiskFileItem item= (DiskFileItem) obit;
					// 如果item是文件上传表单域
					String fileName= item.getName();
					if (fileName != null) {
						realName=fileName.substring(0, fileName.lastIndexOf("."));
						firstFileName = item.getName().substring(item.getName().lastIndexOf("\\") + 1);
						formatName= firstFileName.substring(firstFileName.lastIndexOf("."));// 获取文件后缀名
						fileRealPath= path+ Uid+ formatName;// 文件存放真实地址
						BufferedInputStream in= new BufferedInputStream(item.getInputStream());// 获得文件输入流
						File pat= new File(path);
						if(!pat.exists()) {
							pat.mkdirs();
							
						}
						File files= new File(fileRealPath);
						if(!files.exists()){
							files.createNewFile();
							
						}
						FileOutputStream outs=new FileOutputStream(files);
						BufferedOutputStream outStream = new BufferedOutputStream(outs);// 获得文件输出流
						Streams.copy(in,outStream, true);// 开始把文件写到你指定的上传文件夹
						
					}
					
				}
				
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
			result.put("errInfo",ex.getMessage());
			return;
			
		}
		result.put("name",Uid.concat(formatName+"#").concat(realName).concat("#")+formatName);
		response.getWriter().write(JSON.toJSONString(result));
		out.flush();
		out.close();
		
	}
	
}
