package com.bt.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import com.bt.EPlatform;
import com.bt.OSinfo;
import com.bt.lmis.base.spring.SpringUtils;
import com.bt.lmis.controller.form.DiffBilldeatilsQueryParam;
import com.bt.lmis.dao.DiffBilldeatilsMapper;
public class BigExcelExport {

	/** 
	* @Title: excelDownLoadDatab 
	* @Description: TODO(大数据量导出) 
	* @param @param result 		内容集合
	* @param @param cMap		表头
	* @param @param sheetName	文件名
	* @param @return
	* @param @throws IOException    设定文件 
	* @return File    返回类型 
	* @throws 
	*/
	@SuppressWarnings("unchecked")
	public static File excelDownLoadDatab(List<?> result, HashMap<String, String> cMap, String sheetName) throws IOException {
		String fileName = "";
		if(OSinfo.getOSname().equals(EPlatform.Linux) || OSinfo.getOSname().equals(EPlatform.Mac_OS_X)) {
			fileName = CommonUtils.getAllMessage("config", "COMMON_DOWNLOAD_Linux") + sheetName;
		}else{
			fileName = CommonUtils.getAllMessage("config", "COMMON_DOWNLOAD_" + OSinfo.getOSname()) + sheetName;
		}
		FileOutputStream output = new FileOutputStream(new File(fileName));
		SXSSFWorkbook wb = new SXSSFWorkbook(10000);//内存中保留 1000 条数据，以免内存溢出，其余写入 硬盘  
		Sheet sheet1 = wb.createSheet(sheetName); 
		int excelRow = 0; 
		int titlelRow = 0; 
		int contentlRow = 1;
		Row titleRow = (Row) sheet1.createRow(excelRow++);
		Collection<?> col = cMap.keySet();
		for(Object o : col) {
			Cell cell = titleRow.createCell(titlelRow++); 
			cell.setCellValue(cMap.get(o));
			
		}
		for(int k = 0; k < result.size(); k++){
			excelRow = 0;
			Row contentRow = (Row) sheet1.createRow(contentlRow++);  
			HashMap<String,Object> restr = (HashMap<String,Object>) result.get(k);
			for(Object o : col){
				Cell cell = contentRow.createCell(excelRow++);
				if(restr.get(o) != null) {
					if (restr.get(o) instanceof BigDecimal) {
						double val = ((BigDecimal) restr.get(o)).doubleValue();
						cell.setCellValue(val);
					}else if(restr.get(o) instanceof Double){
						cell.setCellValue((double)restr.get(o));
					}else{
						cell.setCellValue(restr.get(o).toString().trim());
					}
				} else {
					cell.setCellValue(0);
				}
			}
		}
		wb.write(output);
		output.close();
		wb.close();
		System.out.println("*************导出"+fileName+"成功*************");
		return new File(fileName);
		
	}
	
	
	
	@SuppressWarnings("unchecked")
	public static File excelDownLoadDatab_Z(List<?> result, HashMap<String, String> cMap,String file_name, String sheetName) throws IOException {
		String fileName=
				CommonUtils.getAllMessage("config", "COMMON_DOWNLOAD_"+OSinfo.getOSname())+file_name+"/"
				//"C:\\lmis_export\\"+file_name+"\\"
				+ sheetName;
		File f=new  File(CommonUtils.getAllMessage("config", "COMMON_DOWNLOAD_"+OSinfo.getOSname())+file_name);
		if(!f.exists())f.mkdirs();
		FileOutputStream output = new FileOutputStream(new File(fileName));
		SXSSFWorkbook wb = new SXSSFWorkbook(10000);//内存中保留 1000 条数据，以免内存溢出，其余写入 硬盘  
		Sheet sheet1 = wb.createSheet(sheetName); 
		int excelRow = 0; 
		int titlelRow = 0; 
		int contentlRow = 1;
		Row titleRow = (Row) sheet1.createRow(excelRow++);
		Collection<?> col = cMap.keySet();
		for(Object o : col) {
			Cell cell = titleRow.createCell(titlelRow++); 
			cell.setCellValue(cMap.get(o));
			
		}
	
		//
		for(int k = 0; k < result.size(); k++){
			excelRow = 0;
			Row contentRow = (Row) sheet1.createRow(contentlRow++);  
			HashMap<String,Object> restr = (HashMap<String,Object>) result.get(k);
			for(Object o : col){
				Cell cell = contentRow.createCell(excelRow++);
				if(restr.get(o) != null) {
					cell.setCellValue(restr.get(o).toString().trim());
					
				} else {
					cell.setCellValue("");	
				}
			}
		}
		wb.write(output);
		//
	
		output.close();
		wb.close();
		System.out.println("*************导出"+fileName+"成功*************");
		return new File(fileName);
		
	}
	
	@SuppressWarnings("unchecked")
	public static File excelDownLoadDatab_Zddhx(List<?> resultnouse, HashMap<String, String> cMap,String file_name, String sheetName,DiffBilldeatilsQueryParam queryParam,Map<String,Object> idMap) throws IOException {
		String fileName=
				CommonUtils.getAllMessage("config", "COMMON_DOWNLOAD_"+OSinfo.getOSname())+file_name+"/"
				//"C:\\lmis_export\\"+file_name+"\\"
				+ sheetName;
		File f=new  File(CommonUtils.getAllMessage("config", "COMMON_DOWNLOAD_"+OSinfo.getOSname())+file_name);
		if(!f.exists())f.mkdirs();
		FileOutputStream output = new FileOutputStream(new File(fileName));
		SXSSFWorkbook wb = new SXSSFWorkbook(10000);//内存中保留 1000 条数据，以免内存溢出，其余写入 硬盘  
		Sheet sheet1 = wb.createSheet(sheetName); 
		int excelRow = 0; 
		int titlelRow = 0; 
		int contentlRow = 1;
		Row titleRow = (Row) sheet1.createRow(excelRow++);
		Collection<?> col = cMap.keySet();
		for(Object o : col) {
			Cell cell = titleRow.createCell(titlelRow++); 
			cell.setCellValue(cMap.get(o));
			
		}
		DiffBilldeatilsMapper mapper=	(DiffBilldeatilsMapper<T>)SpringUtils.getBean("diffBilldeatilsMapper");
		Integer min=Integer.parseInt(idMap.get("min").toString());
		Integer max=Integer.parseInt(idMap.get("max").toString());
		Integer btw=max-min;
		int times=btw%30000==0?btw/30000:btw/30000+1;
		
		for(int i=0;i<times;i++){
			queryParam.setMinId(min+i*30000);
			queryParam.setMaxId(min+(i+1)*30000);
			List<Map<String,Object>> content=mapper.uploadDetails(queryParam);
		//
		for(int k = 0; k < content.size(); k++){
			excelRow = 0;
			Row contentRow = (Row) sheet1.createRow(contentlRow++);  
			HashMap<String,Object> restr = (HashMap<String,Object>) content.get(k);
			for(Object o : col){
				Cell cell = contentRow.createCell(excelRow++);
				if(restr.get(o) != null) {
					cell.setCellValue(restr.get(o).toString().trim());
					
				} else {
					cell.setCellValue("");	
				}
			}
		}
		
		//
		}
		wb.write(output);
		output.close();
		wb.close();
		System.out.println("*************导出"+fileName+"成功*************");
		return new File(fileName);
		
	}
	
	
	
	public static void main(String args[]) throws IOException{
	Map<String, String>  map=new	HashMap<String, String>();
		map.put("name", "名字");
		map.put("age", "年龄");
		List result=new ArrayList();
		Map mm=new HashMap();
		mm.put("name", "王麻子");
		mm.put("age", "123");
		result.add(mm);
		BigExcelExport.excelDownLoadDatab(result,(HashMap<String, String>) map,"teset");
		
		
		
	}



	@SuppressWarnings("unchecked")
	public static File excelDownLoadDatab_Zone(List<Map<String,String>> result, HashMap<String, String> cMap,String file_name, String sheetName) throws IOException {
		String fileName=
				CommonUtils.getAllMessage("config", "COMMON_DOWNLOAD_"+OSinfo.getOSname())+file_name+"/"
				//"C:\\lmis_export\\"+file_name+"\\"
				+ sheetName;
		File f=new  File(CommonUtils.getAllMessage("config", "COMMON_DOWNLOAD_"+OSinfo.getOSname())+file_name);
		if(!f.exists())f.mkdirs();
		FileOutputStream output = new FileOutputStream(new File(fileName));
		SXSSFWorkbook wb = new SXSSFWorkbook(10000);//内存中保留 1000 条数据，以免内存溢出，其余写入 硬盘  
		Sheet sheet1 = wb.createSheet(sheetName); 
		int excelRow = 0; 
		int titlelRow = 0; 
		int contentlRow = 1;
		Row titleRow = (Row) sheet1.createRow(excelRow++);
		Collection<?> col = cMap.keySet();
		for(Object o : col) {
			Cell cell = titleRow.createCell(titlelRow++); 
			cell.setCellValue(cMap.get(o));
			
		}
		for(int k = 0; k < result.size(); k++){
			excelRow = 0;
			Row contentRow = (Row) sheet1.createRow(contentlRow++);  
			HashMap<String,String> restr = (HashMap<String,String>) result.get(k);
			for(Object o : col){
				Cell cell = contentRow.createCell(excelRow++);
				if(restr.get(o) != null) {
					cell.setCellValue(restr.get(o).toString().trim());
					
				} else {
					cell.setCellValue("");
					
				}
				
			}
			
		}
		wb.write(output);
		output.close();
		wb.close();
		System.out.println("*************导出"+fileName+"成功*************");
		return new File(fileName);
		
	}
}
