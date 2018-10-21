package com.bt.lmis.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSONObject;
import com.bt.OSinfo;
import com.bt.lmis.controller.form.StorageLocationQueryParam;
import com.bt.lmis.model.StorageLocation;
import com.bt.lmis.page.PageView;
import com.bt.lmis.page.QueryResult;
import com.bt.lmis.service.StorageLocationService;
import com.bt.lmis.service.WarehouseService;
import com.bt.utils.BaseConst;
import com.bt.utils.CommonUtils;
import com.bt.utils.FileUtil;
import com.bt.utils.SessionUtils;

@Controller
@RequestMapping("/control/storageLocationController")
public class StorageLocationController {
 
	@Resource(name = "storageLocationServiceImpl")
	private StorageLocationService<T> storageLocationService;
	@Resource(name = "warehouseServiceImpl")
	private WarehouseService<T> warehouseService;
	
	@RequestMapping(value="/initial.do")
	public String initial(HttpServletRequest request){
		List list=warehouseService.findAll();
		if(list!=null&&list.size()!=0)
			request.setAttribute("warehouses",list);
		
		return "/lmis/storage_location";
	}
	@ResponseBody
	@RequestMapping(value="/downloadModel.do")
	public JSONObject downloadModel(){
		JSONObject obj  = new JSONObject();
		String path="storage_location_model.xls";
		File tempFile=null;
		String filePath = CommonUtils.getAllMessage("config", "COMMON_DOWNLOAD_"+OSinfo.getOSname())+path;
		File f=new File(filePath);
		try{
		if(f.exists()){
			obj.put("path", "storage_location_model.xls");
		}else{
			tempFile=new File(CommonUtils.getAllMessage("config", "COMMON_TEMPLET_"+OSinfo.getOSname())+path);
			FileUtil.copyFile(CommonUtils.getAllMessage("config", "COMMON_TEMPLET_"+OSinfo.getOSname())+path, CommonUtils.getAllMessage("config", "COMMON_DOWNLOAD_"+OSinfo.getOSname())+path,true);	
		}
		obj.put("code", 1);
		obj.put("path", "storage_location_model.xlsx");
		}catch(Exception e){
			obj.put("code", 0);
		}
		return obj;
	}
	
	
	
	@RequestMapping(value="/pageQuery.do")
	public String pageQuery(StorageLocationQueryParam queryParam, HttpServletRequest request){
		QueryResult<StorageLocation> qr=null;
		PageView<StorageLocation> pageView = new PageView<StorageLocation>(queryParam.getPageSize()==0?BaseConst.pageSize:queryParam.getPageSize(), queryParam.getPage());
		queryParam.setFirstResult(pageView.getFirstResult());
		queryParam.setMaxResult(pageView.getMaxresult());
		qr=storageLocationService.findRecord(queryParam);
		/*if(request.getParameter("flag")==null||"1".equals(request.getParameter("flag").toString())){
			qr=storageLocationService.findRecord(queryParam);
		}else{
			qr=storageLocationService.findRecord(queryParam);
			qr.setTotalrecord(Integer.parseInt(request.getParameter("pageCountNo").toString()));
		}*/
		pageView.setQueryResult(qr, queryParam.getPage()); 
		request.setAttribute("pageView", pageView);
		request.setAttribute("queryParam", queryParam);
		request.setAttribute("pageCountNo", qr.getTotalrecord());
		return "lmis/storage_location_page";
	}
	
	
	@ResponseBody
	@RequestMapping(value="/delete.do")
	public JSONObject delete(StorageLocationQueryParam queryParam, HttpServletRequest request){
		String ids=request.getParameter("ids").toString();
		String[] idslist=ids.split(";");
		List <String> idList=Arrays.asList(idslist);
		JSONObject obj =new JSONObject();
		try{
			
			storageLocationService.deleteListId(idList);
			obj.put("code", 1);
			
		}catch(Exception e){
			obj.put("code", 0);
			e.printStackTrace();
		}

		return obj;
	}
	
	/*
	 * 批量上传方法
	 * 
	 */
	@ResponseBody
	@RequestMapping(value="/upload.do")
	public JSONObject upload(@RequestParam("file") MultipartFile file,ModelMap map, HttpServletRequest req,HttpServletResponse res,RedirectAttributes ra) throws IOException, EncryptedDocumentException, InvalidFormatException{
		JSONObject obj =new JSONObject();
		List <StorageLocation> storageLocationList=new ArrayList<StorageLocation>();
		StorageLocation storageLocation=null;
		boolean flag=true;
		String logs="";
		if (!file.isEmpty()) {
			  file.transferTo(new File(CommonUtils.getAllMessage("config", "WO_UPLOAD_"+OSinfo.getOSname())+file.getOriginalFilename()));
			  FileInputStream is = new FileInputStream(CommonUtils.getAllMessage("config", "WO_UPLOAD_"+OSinfo.getOSname())+file.getOriginalFilename());	//文件流
   			   Workbook workbook = WorkbookFactory.create(is);
   			   String str = (new SimpleDateFormat("yyyyMMdd-HHmmssSSS")).format(new Date()); 
   			   SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
				Map<String ,Integer> title=new HashMap<String,Integer>();
				for (int s = 0; s < 1; s++) {
					Sheet sheet = workbook.getSheetAt(s);  
		            int rowCount = sheet.getPhysicalNumberOfRows(); //获取总行数  
		            //遍历每一行  
		            int cellCount=0;
		            for (int r = 1; r < rowCount; r++) { 
		            	storageLocation=new StorageLocation();
		            	storageLocation.setCreate_time(new Date());
		            	storageLocation.setUpdate_time(new Date());
		            	storageLocation.setRow_no(r+1);
		            	storageLocation.setCreate_user(SessionUtils.getEMP(req).getEmployee_number());
		            	storageLocation.setUpdate_user(SessionUtils.getEMP(req).getEmployee_number());
		            	storageLocation.setDel_flag("1");
		                Row row = sheet.getRow(r); 
		                //遍历每一列
		                String cellValues = "";
		                cellCount = row.getPhysicalNumberOfCells();  
		             if(r>11){
		            	 System.out.println(123);
		            	 System.out.println(row.getCell(0).getStringCellValue());
		             }
		                if("".equals(row.getCell(0).getStringCellValue()))break;
		                for (int c = 0; c < cellCount; c++) {
		                	if(row==null)continue;
		                    Cell cell = row.getCell(c);  
		                    Integer cellType = cell.getCellType(); 
		                    String cellValue = null;  
		                    switch(cellType) {  
		                        case Cell.CELL_TYPE_STRING: //文本  
		                            cellValue = cell.getStringCellValue();  
		                            break;  
		                        case Cell.CELL_TYPE_NUMERIC: //数字、日期  
		                            if(DateUtil.isCellDateFormatted(cell)) {  
		                                cellValue = fmt.format(cell.getDateCellValue()); //日期型  
		                            }  
		                            else {  
		                                cellValue = String.valueOf(cell.getNumericCellValue()); //数字  
		                            }  
		                            break;  
		                        case Cell.CELL_TYPE_BOOLEAN: //布尔型  
		                            cellValue = String.valueOf(cell.getBooleanCellValue());  
		                            break;  
		                        case Cell.CELL_TYPE_BLANK: //空白  
		                            cellValue = cell.getStringCellValue();  
		                            break;  
		                        case Cell.CELL_TYPE_ERROR: //错误  
		                            cellValue = "错误";  
		                            break;  
		                        case Cell.CELL_TYPE_FORMULA: //公式  
		                            cellValue = "错误";  
		                            break;  
		                        default:  
		                            cellValue = "错误";  
		                    } 
		                  /*if(c==0){
		                	  //仓库名称校验校验
		                	if(storageLocationService.checkWarehouse(cellValue.trim(),storageLocation)) {
		                		logs+="仓库名称输入有误";
		                		flag=false;
		                		continue;
		                		}
		                	storageLocation.setWarehouse_name(cellValue.trim());
		                  }
		                  if(c==1){storageLocation.setLocation_type(cellValue.trim());continue;}  
		                  if(c==2){storageLocation.setReservoir_code(cellValue.trim());continue;}  
		                  if(c==3){storageLocation.setPassageway_code(cellValue.trim());continue;}  
		                  if(c==4){storageLocation.setGroup_code(cellValue.trim());continue;}  
		                  if(c==5){storageLocation.setLine_code(cellValue.trim());continue;}  
		                  if(c==6){storageLocation.setCell_code(cellValue.trim());continue;} 
		                  if(c==7){storageLocation.setSingle_square(new BigDecimal(cellValue.trim()==""||"".equals(cellValue.trim())?"0":cellValue.trim()));continue;} 
		                  if(c==8){storageLocation.setSingle_volumn(new BigDecimal(cellValue.trim()==""||"".equals(cellValue.trim())?"0":cellValue.trim()));continue;} 
		                  if(c==9){storageLocation.setAll_square(new BigDecimal(cellValue.trim()==""||"".equals(cellValue.trim())?"0":cellValue.trim()));continue;} 
		                  if(c==10){storageLocation.setAll_volumn(new BigDecimal(cellValue.trim()==""||"".equals(cellValue.trim())?"0":cellValue.trim()));continue;} */
		                switch (c){
		                case 0 :
		                	if(storageLocationService.checkWarehouse(cellValue.trim(),storageLocation)) {
		                		logs+="第"+(r+1)+"行第"+(c+1)+"列仓库名称输入有误;";
		                		flag=false;
		                		}
		                	storageLocation.setWarehouse_name(cellValue.trim());break;
		                case 1:storageLocation.setLocation_type(cellValue.trim());break;
		                case 2:storageLocation.setReservoir_code(cellValue.trim().split("\\.")[0]);break;
		                case 3:storageLocation.setPassageway_code(cellValue.trim().split("\\.")[0]);break;
		                case 4:storageLocation.setGroup_code(cellValue.trim().split("\\.")[0]);break;
		                case 5:storageLocation.setLine_code(cellValue.trim().split("\\.")[0]);break;
		                case 6:storageLocation.setCell_code(cellValue.trim().split("\\.")[0]);break;
		                case 7:storageLocation.setSingle_square(new BigDecimal(cellValue.trim()==""||"".equals(cellValue.trim())?"0":cellValue.trim()));break;
		                case 8:storageLocation.setSingle_volumn(new BigDecimal(cellValue.trim()==""||"".equals(cellValue.trim())?"0":cellValue.trim()));break;
		                case 9:storageLocation.setAll_square(new BigDecimal(cellValue.trim()==""||"".equals(cellValue.trim())?"0":cellValue.trim()));break;
		                //case 10:storageLocation.setAll_volumn(new BigDecimal(cellValue.trim()==""||"".equals(cellValue.trim())?"0":cellValue.trim()));break;
		                case 10:storageLocation.setStorage_number(Integer.parseInt((cellValue.trim()==""||"".equals(cellValue.trim())?"0":cellValue.trim().split("\\.")[0])));break;
		                }
		                }  
		                if(!storageLocationService.checkBtachinsertBf(storageLocation)){
		                	logs+="第"+(r+1)+"行 数据库已存在;";
		                	flag=false;
		                }else{
				             storageLocationList.add(storageLocation);
		                }
		            }
		            
		           logs+=storageLocationService.checkRepeat(storageLocationList);
		            if(!"".equals(logs)) flag=false;
		            if(flag){
		            	 //批量插入
		            	storageLocationService.addBatch(storageLocationList);
		            	obj.put("code", 1);
		             }else{ 
		            	 createCell(CommonUtils.getAllMessage("config", "COMMON_DOWNLOAD_"+OSinfo.getOSname())+str+"storageLocationbatchinputlog.xls",logs.split(";"));
							obj.put("code", 3);
							obj.put("path", str+"storageLocationbatchinputlog.xls");
							return obj;
		             }  
				}	
		}else{
			obj.put("code", 0);
			return obj;
		}
		obj.put("code", 1);
		return obj;
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
	
	
	@ResponseBody
	@RequestMapping(value="/toUpdate.do")
	public JSONObject toUpdate(StorageLocationQueryParam queryParam, HttpServletRequest request){
		JSONObject obj =new JSONObject();
		QueryResult<StorageLocation> qr=null;
		try{
			PageView<StorageLocation> pageView = new PageView<StorageLocation>(queryParam.getPageSize()==0?BaseConst.pageSize:queryParam.getPageSize(), queryParam.getPage());
			queryParam.setFirstResult(pageView.getFirstResult());
			queryParam.setMaxResult(pageView.getMaxresult());
			qr=storageLocationService.findRecord(queryParam);
		}catch(Exception e){
			obj.put("code", 0);
			e.printStackTrace();
		}
       if(qr!=null&&qr.getResultlist().size()!=0)
		//request.setAttribute("result", qr.getResultlist().get(0));
		obj.put("result", qr.getResultlist().get(0));
		return obj;
	}
	
	
	@ResponseBody
	@RequestMapping(value="/update.do")
	public JSONObject update(StorageLocationQueryParam queryParam, HttpServletRequest request,StorageLocation Entity){
		JSONObject obj =new JSONObject();
		QueryResult<StorageLocation> qr=null;
		try{
			if(storageLocationService.checkUpdateBf(queryParam)){
				Entity.setUpdate_time(new Date());
				Entity.setUpdate_user(SessionUtils.getEMP(request).getEmployee_number());
				storageLocationService.update(Entity);
				obj.put("code", 1);
			}else{
				obj.put("code", 0);
				obj.put("msg", "库区编码+通道+组+行+格 已经存在。修改失败！");
			}
			qr=storageLocationService.findRecord(queryParam);
		}catch(Exception e){
			obj.put("code", 0);
			obj.put("msg", "系统错误！");
			e.printStackTrace();
		}
       if(qr!=null&&qr.getResultlist().size()!=0)
		//request.setAttribute("result", qr.getResultlist().get(0));
		obj.put("result", qr.getResultlist().get(0));
		return obj;
	}
	
	
	public static void main(String args[]){
		System.out.println("wq");
		System.out.println("".split("\\.")[0]);
	}
	
}
