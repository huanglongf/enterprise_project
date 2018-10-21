package com.bt.vims.utils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.management.ThreadMXBean;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.ServiceUI;
import javax.print.SimpleDoc;
import javax.print.attribute.DocAttributeSet;
import javax.print.attribute.HashDocAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.swing.JFileChooser;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.record.PageBreakRecord.Break;

import com.bt.vims.model.Visitors;

import groovy.ui.SystemOutputInterceptor;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
/**
 * 
 * @author lsh11036+李京功
 *
 */
public final class PrintUtil {
		private static final Logger logger = Logger.getLogger(PrintUtil.class);
		private static final String linuxUrl = "C:/home/file/vims_file/general/";
		private static final String interviewUrl = linuxUrl+"image/interview.png";
		private static final String visitUrl = linuxUrl+"image/visit.png";
		private static final String windxUrl = "C:\\home\\file\\vims_file\\general\\pdf\\";
		/**
		 * 建立打印连接和打印 
		 * @param  printName  打印机名称
		 * @param  list       
		 * @return print_flag 0:代表失败,1:代表成功。
		 */
	    public static int printConnection(String printName,List<Visitors> list){
	    	int print_flag = 0;
	 		try {
	 			if(list.size() >0 && !list.isEmpty()) {
	 				List<Visitors> list1 = new ArrayList<Visitors>();
					List<Visitors> list2 = new ArrayList<Visitors>();
		 			for(Visitors visitors : list) {
		 				if("面试".equals(visitors.getContent()) || "4".equals(visitors.getContent())) {
		 					Visitors visitors1 = new Visitors();
		 					visitors1.setHost(visitors.getHost());
		 					visitors1.setVisitor_name(visitors.getVisitor_name());
		 					visitors1.setVisitor_date(visitors.getVisitor_date());
		 					visitors1.setVisitor_year(visitors.getVisitor_year());
		 					visitors1.setContent(visitors.getContent());
		 				    visitors1.setImage_url(interviewUrl);
		 					list1.add(visitors1);
		 				}else{
		 					Visitors visitors2 = new Visitors();
		 					visitors2.setHost(visitors.getHost());
		 					visitors2.setVisitor_name(visitors.getVisitor_name());
		 					visitors2.setVisitor_date(visitors.getVisitor_date());
		 					visitors2.setVisitor_year(visitors.getVisitor_year());
		 					visitors2.setVisitor_type(visitors.getVisitor_type());
		 					visitors2.setContent(visitors.getContent());
		 				    visitors2.setImage_url(visitUrl);
		 				    visitors2.setVisitorNum(visitors.getVisitorNum());
		 					list2.add(visitors2);
		 				}
		 			}
		 			if(list1.size() > 0 && !list1.isEmpty()) {
		 				jsperChangePdf(printName,list1,"面试");
		 			}
		 			if(list2.size() > 0 && !list2.isEmpty()) {
		 				jsperChangePdf(printName,list2,"访客");
		 			}
		 		    print_flag = 1;
		    	}else {
		    		throw new Exception("list不能为空");
		    	}				
	 		} catch (Exception e) {
	 			e.printStackTrace();
	 			logger.error(e.getMessage());
	 		}
		     return print_flag;
		}
	    
	    /**
	     * 选择不同的.jasper模板
	     * @param printName
	     * @param list
	     * @param content
	     * @throws Exception
	     */
	   private  static void jsperChangePdf(String printName,List<Visitors> list, String content) throws Exception{
			   if("面试".equals(content) ) {
				   String path = linuxUrl + "jasper/interview.jasper";
				   conversion(path,printName,list);
			   }else if("访客".equals(content)){
				   List<Visitors> list1 = new ArrayList<Visitors>();
				   List<Visitors> list2 = new ArrayList<Visitors>();
				   for(Visitors visitor : list) {
					    if("01".equals(visitor.getVisitor_type())) {
					    	Visitors visitors1 = new Visitors();
					    	 visitors1.setHost(visitor.getHost());
			 				 visitors1.setVisitor_name(visitor.getVisitor_name());
			 				 visitors1.setVisitor_date(visitor.getVisitor_date());
			 				 visitors1.setVisitor_year(visitor.getVisitor_year());
			 				 visitors1.setImage_url(visitor.getImage_url());
			 				 visitors1.setVisitorNum(visitor.getVisitorNum());
			 				 list1.add(visitors1);
					    } else if("02".equals(visitor.getVisitor_type())) {
					    	 Visitors visitors2 = new Visitors();
					    	 visitors2.setHost(visitor.getHost());
			 				 visitors2.setVisitor_name(visitor.getVisitor_name());
			 				 visitors2.setVisitor_date(visitor.getVisitor_date());
			 				 visitors2.setVisitor_year(visitor.getVisitor_year());
			 				 visitors2.setImage_url(visitor.getImage_url());
			 				 visitors2.setVisitorNum(visitor.getVisitorNum());
			 				 list2.add(visitors2);
					    }
				   }
				   String path = linuxUrl + "jasper/visit.jasper";
				   if(list1.size()>0 && !list1.isEmpty()) {
					   conversion(path,printName,list1);
				   }
			       if(list2.size()>0 && !list2.isEmpty()) {
			    	   groupVisitors(path,printName,list2);	
			       }
				   	  
			   }		   		           		
		}
	   
	     /**
	      * 把团体jasper文件转换成pdf文
	      * @param path
	      * @param printName
	      * @param list
	      * @throws JRException
	     * @throws IOException 
	     * @throws InterruptedException 
	      */
	     private static void groupVisitors(String path,String printName,List<Visitors> list) throws JRException, InterruptedException, IOException {
	    	   JasperReport jasperReport = null;
	    	   JRDataSource jrDataSource_common = null;
	    	   for(Visitors visitors : list) {
	    		   String uuid = UUID.randomUUID().toString();
	    		   List<Visitors> clist = new ArrayList<Visitors>();
	    		   for(int k=0;k<visitors.getVisitorNum();k++) {
		    		   Visitors visitor = new Visitors();
		    		   visitor.setHost(visitors.getHost());
					   visitor.setVisitor_name(visitors.getVisitor_name());
					   visitor.setVisitor_date(visitors.getVisitor_date());
					   visitor.setVisitor_year(visitors.getVisitor_year());
					   visitor.setContent(visitors.getContent());
					   visitor.setImage_url(visitUrl);
					   clist.add(visitor);
	    		   }
				   jasperReport  = (JasperReport) JRLoader.loadObjectFromFile(path);
				   jrDataSource_common =  new JRBeanCollectionDataSource(clist);
				   //String paths = linuxUrl+"pdf/"+uuid+".pdf";
				   String paths = windxUrl + uuid + ".pdf";
				   Map<String, Object> parameters = new HashMap<String, Object>();
				   JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,jrDataSource_common);   
				   JasperExportManager.exportReportToPdfFile(jasperPrint,paths);   
				   //executeShellLinux(paths,printName);
				   executeShellWindows(paths,printName);
	    	   } 
	    	   
			   
	     }
		  /**
			* 把jasper文件转换成pdf
			* @param path
			* @param printName
			* @param list
			* @throws JRException
		    * @throws IOException 
		    * @throws InterruptedException 
			*/
	    private static void conversion(String path,String printName,List<Visitors> list) throws JRException, InterruptedException, IOException { 	
	    	   Map<String, Object> parameters = new HashMap<String, Object>(); 
			   JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile(path);
			   JRDataSource jrDataSource_common =  new JRBeanCollectionDataSource(list);
			   //String paths = path.split("\\.")[0]+".pdf";
			   String uuid = UUID.randomUUID().toString();
			   //String paths = linuxUrl + "pdf/" + uuid + ".pdf";
			   String paths = windxUrl + uuid + ".pdf";
			   JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,jrDataSource_common);   
		       JasperExportManager.exportReportToPdfFile(jasperPrint,paths);      
		       //executeShellLinux(paths,printName);
		       executeShellWindows(paths,printName);
	    }
	    
	    /**
	     * 打印pdf文件
	     * @param filePath 
	     * @param printName
	     * @throws InterruptedException
	     * @throws IOException
	     */
	    private static void executeShellLinux(String filePath,String printName) throws InterruptedException, IOException {
	    	String shellUrl = linuxUrl + "print/print.sh";
			String command =  shellUrl + " " + filePath + " " + printName;
			new ProcessBuilder("/bin/chmod", "755",shellUrl).start();//shell脚本加上权限
	        int success = Runtime.getRuntime().exec(command).waitFor();
	        if(1 == success) {
	        	 throw new IOException("shell脚步执行错误,请查看传入的相关参数是否有误!!!");
	         }       
	    }
	    /**
	     * 打印pdf文件
	     * @param filePath 
	     * @param printName
	     * @throws InterruptedException
	     * @throws IOException
	     */
	    private static void executeShellWindows(String filePath,String printName) throws InterruptedException, IOException {
	    	if(null != printName && !"".equals(printName)) {
	    		if(printerOfExist(printName)) {
	    			String printRW="cmd.exe /C start acrord32.exe  /t "+filePath+" "+printName;   
				    int success =Runtime.getRuntime().exec(printRW).waitFor();
				    if(1 == success) {
				    	throw new IOException("打印机失败，请查看是否打印机没有开启"); 
				    }
	    		}else {
	    			throw new IOException("打印机不存在或者打印机名称错误");
	    		}
	    	}else{
	    		throw new IOException("打印机名称不能为null");
	    	}
	    }
	    
	    /**
	     * 验证打印机是否存在
	     * @param printName
	     */
	    private static Boolean printerOfExist(String printName) {
	    	boolean flage = false;
	    	PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
	        DocFlavor flavor = DocFlavor.BYTE_ARRAY.PNG;
	        PrintService printService[] = PrintServiceLookup.lookupPrintServices(flavor, pras);
	        if(printService.length>0) {
	        	for(int i=0;i<printService.length;i++) {
	                if(printService[i].getName().equals(printName)){
	                	flage = true;
	                	break;
	                }else {
	                	flage = false;
	                }	               
		        }
	        }else {
	        	flage = false;
	        }
	        return flage; 
	    }
 }
    
