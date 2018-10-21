package com.bt.common.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

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
			fileName = CommonUtil.getAllMessage("config", "COMMON_DOWNLOAD_Linux") + sheetName;
		}else{
			fileName = CommonUtil.getAllMessage("config", "COMMON_DOWNLOAD_" + OSinfo.getOSname()) + sheetName;
		}
		FileOutputStream output = new FileOutputStream(new File(new String(fileName.getBytes(),"UTF-8")));
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
					if(o=="status"){
						if(restr.get(o).toString().trim().equals("1")){
							cell.setCellValue("已录单");
						}else if(restr.get(o).toString().trim().equals("2")){
							cell.setCellValue("待揽收");
						}else if(restr.get(o).toString().trim().equals("10")){
							cell.setCellValue("已取消");
						}else if(restr.get(o).toString().trim().equals("4")){
							cell.setCellValue("已下单");
						}else if(restr.get(o).toString().trim().equals("5")){
							cell.setCellValue("已揽收");
						}else if(restr.get(o).toString().trim().equals("6")){
							cell.setCellValue("已发运");
						}else if(restr.get(o).toString().trim().equals("7")){
							cell.setCellValue("已签收");
						}else if(restr.get(o).toString().trim().equals("8")){
							cell.setCellValue("签收失败");
						}else if(restr.get(o).toString().trim().equals("9")){
							cell.setCellValue("已退回");
						}else if(restr.get(o).toString().trim().equals("0")){
							cell.setCellValue("已作废");
						}
					}else if(o=="pay_path"){
						if(restr.get(o).toString().trim().equals("1")){
							cell.setCellValue("寄方付");
						}else if(restr.get(o).toString().trim().equals("2")){
							cell.setCellValue("收方付");
						}else if(restr.get(o).toString().trim().equals("3")){
							cell.setCellValue("第三方付");
						}else if(restr.get(o).toString().trim().equals("4")){
							cell.setCellValue("寄付月结");
						}
					}else if(o=="amount_flag"){
						if(restr.get(o).toString().trim().equals("0")){
							cell.setCellValue("否");
						}else if(restr.get(o).toString().trim().equals("1")){
							cell.setCellValue("是");
						}
					}else{
					cell.setCellValue(restr.get(o).toString().trim());
					}
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
	
	
	
	@SuppressWarnings("unchecked")
	public static File excelDownLoadDatab_Z(List<?> result, HashMap<String, String> cMap,String file_name, String sheetName) throws IOException {
		String fileName=
				CommonUtil.getAllMessage("config", "COMMON_DOWNLOAD_"+OSinfo.getOSname())+file_name+"/"
				//"C:\\lmis_export\\"+file_name+"\\"
				+ sheetName;
		File f=new  File(CommonUtil.getAllMessage("config", "COMMON_DOWNLOAD_"+OSinfo.getOSname())+file_name);
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
				CommonUtil.getAllMessage("config", "COMMON_DOWNLOAD_"+OSinfo.getOSname())+file_name+"/"
				//"C:\\lmis_export\\"+file_name+"\\"
				+ sheetName;
		File f=new  File(CommonUtil.getAllMessage("config", "COMMON_DOWNLOAD_"+OSinfo.getOSname())+file_name);
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
