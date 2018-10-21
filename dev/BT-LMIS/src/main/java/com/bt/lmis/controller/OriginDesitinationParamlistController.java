package com.bt.lmis.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSONObject;
import com.bt.lmis.code.BaseController;
import com.bt.lmis.model.Area;
import com.bt.lmis.model.Employee;
import com.bt.lmis.model.OriginDesitinationParamlist;
import com.bt.lmis.service.AreaService;
import com.bt.lmis.service.OriginDesitinationParamlistService;
import com.bt.lmis.summary.StorageChargeSettlement;
import com.bt.utils.SessionUtils;
/**
 * 始发目的地参数表控制器
 *
 */
@Controller
@RequestMapping("/control/lmis/originDesitinationParamlistController")
public class OriginDesitinationParamlistController extends BaseController {
	@Resource(name = "areaServiceImpl")
	private AreaService<Area> areaService;
	private static final Logger logger = Logger.getLogger(OriginDesitinationParamlistController.class);
	@Autowired
	StorageChargeSettlement  storageservice;
	/**
	 * 始发目的地参数表服务类
	 */
	@Resource(name = "originDesitinationParamlistServiceImpl")
	private OriginDesitinationParamlistService<OriginDesitinationParamlist> originDesitinationParamlistService;
	
	@Resource(name = "originDesitinationParamlistServiceImpl")
	private OriginDesitinationParamlistService<OriginDesitinationParamlist> OriginDesitinationParamlistServiceImpl;
	
	@RequestMapping("/upload_page.do")
	public String upload_page(ModelMap map, HttpServletRequest request,HttpServletResponse res){
		map.put("url", request.getParameter("url"));
		map.put("msg", request.getParameter("msg"));
		map.put("pathDone", request.getParameter("pathDone"));
		if(request.getParameter("path1")!=null)
		map.put("path1",request.getParameter("path1"));
		if(request.getParameter("path2")!=null)
		map.put("path2",request.getParameter("path2"));
		return "/lmis/address_upload";
	}
	@ResponseBody
	@RequestMapping(value="/upload.do")
	public JSONObject upload(@RequestParam("file") MultipartFile file,ModelMap map, HttpServletRequest req,HttpServletResponse res,RedirectAttributes ra) throws IOException{
		 Employee user= SessionUtils.getEMP(req);
		 String log= "";
		 JSONObject obj=new JSONObject();
		 String str = (new SimpleDateFormat("yyyyMMdd-HHmmssSSS")).format(new Date()); 
		 synchronized(this){
		if (!file.isEmpty()) {
	            try {
	                // 文件保存路径
	                File  ff=new File(req.getSession().getServletContext().getRealPath("/") + "upload");
	                if(!ff.exists()){
	                	ff.mkdir();
	                }
	                String filePath = req.getSession().getServletContext().getRealPath("/") + "upload/" + file.getOriginalFilename();  
	                // 转存文件
	                    file.transferTo(new File(filePath));						
	         			FileInputStream is = new FileInputStream(filePath);	//文件流
	         			Workbook workbook = WorkbookFactory.create(is); 		//这种方式 Excel 2003/2007/2010 都是可以处理的
	         			int sheetCount = workbook.getNumberOfSheets(); 			//Sheet的数量
                         log=OriginDesitinationParamlistServiceImpl.saveExcels(workbook,true,user.getUsername(), sheetCount);
                        if(log==null||"".equals(log)){
                        	String logCheckExelSame="";
                        	logCheckExelSame= OriginDesitinationParamlistServiceImpl.insertCheckExelSame("");
                        	if(logCheckExelSame!=null&&!"".equals(logCheckExelSame)) {
                        		log=log+logCheckExelSame.split("分隔符")[1];
                                Sheet sheet = workbook.getSheetAt(0);  
                                List <Integer>al = new ArrayList<Integer>();
                                al=listIntToString(Arrays.asList(logCheckExelSame.split("分隔符")[0].split(";")));
                                Collections.sort(al);
                                for(int ii=0;ii<al.size();ii++){
                                	if(al.get(ii)==null||"".equals(al.get(ii)))continue;
                                	removeRow(sheet, al.get(ii)-1-ii*1);
                                }
                                FileOutputStream os = new FileOutputStream(req.getSession().getServletContext().getRealPath("/") +"upload/"+str+"logDone.xlsx");  
                                workbook.write(os); 
                                os.close();
                        	}//打印新的excel
                        }
                     if(log==null||"".equals(log)){
                    	 OriginDesitinationParamlistServiceImpl. saveAndprocessData(Integer.toString(user.getId()));  	 
                     } 
                     if(log==null||"".equals(log)){
                    	 obj.put("msg", "恭喜上传成功");
                         obj.put("code", 1);
                     }else{
                    	 String downPath=req.getSession().getServletContext().getRealPath("/") +"upload/"+str+"log.xls";
                    	 createCell(downPath,log.split(";")); 
                    	 obj.put("msg", "上传过程有点小问题，请查看日志信息");
                    	 obj.put("path",str+"log.xls");
                    	 obj.put("pathDone", str+"logDone.xlsx");
                    	 obj.put("code", 0);              
                     }	 	 
                    	 is.close();
                    	 
	            } catch (Exception e) {  
	              e.printStackTrace();  
	              obj.put("msg", "上传失败");
	              obj.put("code", 3);
	              logger.error(e);
	            }
	        }else{
	        	obj.put("code", 4);
	        }
		 }
		return  obj;
	}
	

	public String download1(ModelMap map, HttpServletRequest req,HttpServletResponse res,RedirectAttributes ra){

	System.out.println(req.getParameter("name"));
		 return "/lmis/address_upload"; 
	}
	@RequestMapping("/download.do")
	public ResponseEntity<byte[]> download(ModelMap map, HttpServletRequest req,HttpServletResponse res,RedirectAttributes ra) throws IOException {  
		HttpHeaders headers = new HttpHeaders();  
	    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);  
	    headers.setContentDispositionFormData("attachment", "log.xlsx");  
	    return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(new File(req.getParameter("filePath"))),  
	                                      headers, HttpStatus.CREATED);  
	}

	private  String createCell(String url,String[] a) {
		FileOutputStream fileOut= null;
		//创建行 & 创建列 start
		Workbook wb=new HSSFWorkbook(); 											// 定义一个新的工作簿
		Sheet sheet=wb.createSheet("第一个Sheet页");  	
		for (int i = 0; i < a.length; i++) {
										// 创建第一个Sheet页
			Row row = sheet.createRow(i); 												// 创建一个行
			row.setHeightInPoints(20);
			Cell cell=row.createCell((short)0);  										// 创建单元格(short)0 (short)1 (short)2 (short)3
			//创建行 & 创建列 end
			CellStyle cellStyle=wb.createCellStyle(); 									// 创建单元格样式
			cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);  						// 设置单元格水平方向对其方式 ALIGN_CENTER ALIGN_FILL ALIGN_LEFT ALIGN_RIGHT
			cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_BOTTOM); 				// 设置单元格垂直方向对其方式 HSSFCellStyle.VERTICAL_BOTTOM VERTICAL_CENTER VERTICAL_TOP VERTICAL_TOP
			cell.setCellStyle(cellStyle); 												// 设置单元格样式
			cell.setCellValue(new HSSFRichTextString(a[i]));  							// 设置值
			try {
				fileOut = new FileOutputStream(url);
				wb.write(fileOut);
				fileOut.close();
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					wb.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
		return  "ok";
	}
	
	//删除地址参数
		@ResponseBody
		@RequestMapping("/deleteParamters.do")
		public JSONObject deleteParamters(ModelMap map, HttpServletRequest request) throws Exception{
			JSONObject  json=new JSONObject();
			 synchronized(this){
			try{
				if(request.getParameter("id")!=null&&!"".equals(request.getParameter("id")))
					 originDesitinationParamlistService.removePamaterList(request.getParameter("id"));
				if(request.getParameter("ids")!=null&&!"".equals(request.getParameter("ids")))
					 originDesitinationParamlistService.removePamaterListbatch(request.getParameter("ids"));
				 json.put("code", "1");
			}catch(Exception e){
				json.put("code", "0");
				logger.error(e);
			   
			}
			
			 }
			
		  return json;
		}
		
	
	
	public static void main(String[] args) {

	}
	@RequestMapping(value="/test.do")
	public JSONObject test(ModelMap map, HttpServletRequest req,HttpServletResponse res,RedirectAttributes ra) throws IOException, InterruptedException{
		JSONObject ob=new JSONObject();
		synchronized(this){
		System.out.println("test0000000");
		Thread.sleep(2000);
		System.out.println("test0000000");
		Thread.sleep(2000);
		System.out.println("test0000000");
		Thread.sleep(2000);
		System.out.println("test0000000");
		Thread.sleep(2000);
		System.out.println("test0000000");
		Thread.sleep(2000);
		System.out.println("test0000000");
		System.out.println("test0000000");
		System.out.println("test0000000");}
	return ob;
	}
	
	@RequestMapping(value="/test1.do")
	public JSONObject test1(ModelMap map, HttpServletRequest req,HttpServletResponse res,RedirectAttributes ra) throws IOException{
		JSONObject ob=new JSONObject();
		synchronized(this){
		System.out.println("test11111");
		System.out.println("test11111");
		System.out.println("test11111");
		System.out.println("test11111");
		System.out.println("test11111");
		System.out.println("test11111");}
		return ob;
	}
	
	
	@RequestMapping(value="/test2.do")
	public JSONObject test2(ModelMap map, HttpServletRequest req,HttpServletResponse res,RedirectAttributes ra) throws IOException{
		JSONObject ob=new JSONObject();
		//areaService.test();
		
		
		return ob;
	}
	
	public  void removeRow(Sheet sheet, int rowIndex) {  
	    int lastRowNum=sheet.getLastRowNum();  
	    if(rowIndex>=0&&rowIndex<lastRowNum)  
	        sheet.shiftRows(rowIndex+1,lastRowNum,-1);//将行号为rowIndex+1一直到行号为lastRowNum的单元格全部上移一行，以便删除rowIndex行  
	    if(rowIndex==lastRowNum){  
	        Row removingRow=sheet.getRow(rowIndex);  
	        if(removingRow!=null)  
	            sheet.removeRow(removingRow);  
	    }  
	}
	
	
	public List<Integer> listIntToString(List<String>arr){
        List<Integer> list=new ArrayList<Integer>();
		for(String str:arr){
        	if(str==null||"".equals(str))continue;
        	list.add(Integer.parseInt(str));
        }
        return list;
        }
	@RequestMapping(value="/test110.do")
	public  String  tesdddte(){
		System.out.print("2131312");
		storageservice.ReckonStorageCharge_by_con_id("","");
		return null;
	}	
	
}
