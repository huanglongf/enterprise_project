package com.bt.common;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 解析Excel表格（只支持2003,2007,2010 xlsx和xls两种扩展名文件）
 *
 */
@SuppressWarnings("resource")
public class ExcelExportUtil {
	public static final String OFFICE_EXCEL_2003_POSTFIX = "xls";
	
	public static final String OFFICE_EXCEL_2010_POSTFIX = "xlsx";
	
	
	/**
	 * 导出excel文件-支持 2003 2007 2010  xls,xlsx扩展名
	 * @param list 需要写的数据
	 * @param headColums 数据的列名（字段名，数据里的key名 不能为null） 
	 * @param headNames 列名说明-一般是字段说明，（不需要时，可以传 null）
	 * @param sheetName excel的第一个sheet的名称
	 * @param fileSuffixName 导出excle类型扩展名有常量变量  OFFICE_EXCEL_2003_POSTFIX，OFFICE_EXCEL_2010_POSTFIX
	 * @throws Exception
	 */
	public static void exportExcelData(List<Map<String,Object>> list,String[] headNames, String sheetName,String fileSuffixName,String fileName,HttpServletResponse resp)  throws Exception {
		OutputStream output = null;
		byte[] bytes = null;
		if(OFFICE_EXCEL_2003_POSTFIX.equals(fileSuffixName)){
			bytes = exportXlsHandle(list,headNames, sheetName);
			
		}else if(OFFICE_EXCEL_2010_POSTFIX.equals(fileSuffixName)){
			bytes = exportXlsxHandle(list,headNames, sheetName);
		}
		int length=0;
		if(bytes!=null){
			length = bytes.length;
		}
		resp.setContentType("application/msexcel;charset=utf-8");
		resp.setHeader("Content-disposition", "attachment; filename="+ new String(fileName.getBytes("GB2312"),"ISO8859-1")); 
		resp.setContentLength(length);
		output = resp.getOutputStream();
		if(bytes != null){
			output.write(bytes);
		}
		output.flush();
		resp.flushBuffer();
		output.close();
	}
	
	
	/**
	 * export文件-支持2003 xls扩展名
	 * @param list
	 * @param headColums 不能为空
	 * @param sheetName
	 * @throws Exception
	 */
	private static byte[] exportXlsHandle(List<Map<String,Object>> list,String[] headNames , String sheetName) throws Exception {
		HSSFWorkbook book = new HSSFWorkbook();
		HSSFSheet sheet = book.createSheet(sheetName);
		//当前行
		int curRow = 0;
		
		//写列的列说明
		if(headNames != null && headNames.length > 0){
			HSSFRow firstRow = sheet.createRow(curRow);
			HSSFCell[] firstCells = new HSSFCell[headNames.length];
			for (int j = 0; j < headNames.length; j++) {
				firstCells[j] = firstRow.createCell(j);
				firstCells[j].setCellValue(new HSSFRichTextString(headNames[j]));
			}
			
			curRow++;
		}
		
		if(list != null){
			int countColumnNum = list.size();
			//写数据
			for (int i = 0; i < countColumnNum; i++) {
				HSSFRow row = sheet.createRow(i + curRow);
				Map<String,Object> rowData = list.get(i);
				
				for (int column = 0; column < headNames.length; column++) {
					Object cellData = rowData.get(headNames[column]);
					HSSFCell cell = row.createCell(column);
					if(cellData == null){
						cell.setCellValue("");
					}else{
						cell.setCellValue(cellData.toString());
					}
				}
			}
		}
		
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		book.write(os);
		//关闭流
		os.close();
		
		return os.toByteArray();
	}
	
	/**
	 * export文件-支持2007,2010 xlsx扩展名
	 * @param list
	 * @param headColums 不能为空
	 * @param sheetName
	 * @throws Exception
	 */
	private static byte[] exportXlsxHandle(List<Map<String,Object>> list,String[] headNames , String sheetName) throws Exception {
		XSSFWorkbook book = new XSSFWorkbook();
		XSSFSheet sheet = book.createSheet(sheetName);
		//当前行
		int curRow = 0;
		
		//写列的列说明
		if(headNames != null && headNames.length > 0){
			XSSFRow firstRow = sheet.createRow(curRow);
			XSSFCell[] firstCells = new XSSFCell[headNames.length];
			for (int j = 0; j < headNames.length; j++) {
				firstCells[j] = firstRow.createCell(j);
				firstCells[j].setCellValue(new XSSFRichTextString(headNames[j]));
			}
			
			curRow++;
		}
		
		if(list != null){
			int countColumnNum = list.size();
			//写数据
			for (int i = 0; i < countColumnNum; i++) {
				XSSFRow row = sheet.createRow(i + curRow);
				Map<String,Object> rowData = list.get(i);
				
				for (int column = 0; column < headNames.length; column++) {
					Object cellData = rowData.get(headNames[column]);
					XSSFCell cell = row.createCell(column);
					if(cellData == null){
						cell.setCellValue("");
					}else{
						cell.setCellValue(cellData.toString());
					}
				}
			}
		}
		
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		book.write(os);
		//关闭流
		os.close();
		
		return os.toByteArray();
	}

	
	public static void exportExcelData(List<Map<String,Object>> list, Map<String, String> cMap, 
			String sheetName,String fileSuffixName,String fileName,HttpServletResponse resp) throws IOException {
		OutputStream output = null;
		byte[] bytes = null;
		if(OFFICE_EXCEL_2003_POSTFIX.equals(fileSuffixName)){
			bytes = exportXlsHandle(list,cMap, sheetName);
			
		}else if(OFFICE_EXCEL_2010_POSTFIX.equals(fileSuffixName)){
			bytes = exportXlsxHandle(list,cMap, sheetName);
		}
		int length=0;
		if(bytes!=null){
			length = bytes.length;
		}
		resp.setContentType("application/msexcel;charset=utf-8");
		resp.setHeader("Content-disposition", "attachment; filename="+ new String(fileName.getBytes("GB2312"),"ISO8859-1")); 
		resp.setContentLength(length);
		output = resp.getOutputStream();
		if(bytes != null){
			output.write(bytes);
		}
		output.flush();
		resp.flushBuffer();
		output.close();
		
	}


	/**
	 * export文件-支持2007,2010 xlsx扩展名
	 * @param list
	 * @param cMap 不能为空
	 * @param sheetName
	 * @throws Exception
	 */
	private static byte[] exportXlsxHandle(List<Map<String, Object>> list, Map<String, String> cMap,
			String sheetName) throws IOException {
		XSSFWorkbook book = new XSSFWorkbook();
		XSSFSheet sheet = book.createSheet(sheetName);
		//当前行
		int curRow = 0;
		
		//写列的列说明
		if(cMap != null && cMap.size() > 0){
			XSSFRow firstRow = sheet.createRow(curRow);
			XSSFCell[] firstCells = new XSSFCell[cMap.size()];
			int j = 0;
			for (Entry<String, String> entry : cMap.entrySet()) {
				firstCells[j] = firstRow.createCell(j);
				firstCells[j].setCellValue(new XSSFRichTextString(entry.getValue()));
				j++;
			}
			
			curRow++;
		}
		
		if(list != null){
			int countColumnNum = list.size();
			//写数据
			for (int i = 0; i < countColumnNum; i++) {
				XSSFRow row = sheet.createRow(i + curRow);
				Map<String,Object> rowData = list.get(i);
				
				int column = 0;
				for (Entry<String, String> entry : cMap.entrySet()) {
					Object cellData = rowData.get(entry.getKey());
					XSSFCell cell = row.createCell(column);
					if(cellData == null){
						cell.setCellValue("");
					}else{
						cell.setCellValue(cellData.toString());
					}
					column++;
				}
				
				
			}
		}
		
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		book.write(os);
		//关闭流
		os.close();
		
		return os.toByteArray();
	}


	/**
	 * export文件-支持2003 xls扩展名
	 * @param list
	 * @param cMap 不能为空
	 * @param sheetName
	 * @throws IOException
	 */
	private static byte[] exportXlsHandle(List<Map<String, Object>> list, Map<String, String> cMap,
			String sheetName) throws IOException {
		HSSFWorkbook book = new HSSFWorkbook();
		HSSFSheet sheet = book.createSheet(sheetName);
		//当前行
		int curRow = 0;
		
		//写列的列说明
		if(cMap != null && cMap.size() > 0){
			HSSFRow firstRow = sheet.createRow(curRow);
			HSSFCell[] firstCells = new HSSFCell[cMap.size()];
			int j = 0;
			for (Entry<String, String> entry : cMap.entrySet()) {
				firstCells[j] = firstRow.createCell(j);
				firstCells[j].setCellValue(new HSSFRichTextString(entry.getValue()));
				j++;
			}
			curRow++;
		}
		
		if(list != null){
			int countColumnNum = list.size();
			//写数据
			for (int i = 0; i < countColumnNum; i++) {
				HSSFRow row = sheet.createRow(i + curRow);
				Map<String,Object> rowData = list.get(i);
				
				int column = 0;
				for (Entry<String, String> entry : cMap.entrySet()) {
					Object cellData = rowData.get(entry.getKey());
					HSSFCell cell = row.createCell(column);
					if(cellData == null){
						cell.setCellValue("");
					}else{
						cell.setCellValue(cellData.toString());
					}
					column++;
				}
			}
		}
		
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		book.write(os);
		//关闭流
		os.close();
		
		return os.toByteArray();
	}
	

		
}
