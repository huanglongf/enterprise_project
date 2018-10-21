package com.bt.common.util;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import com.bt.utils.CommonUtils;

public class ExcelExportUtil {

	public static final String OFFICE_EXCEL_2010_POSTFIX = "xlsx";
	public static final Integer SINGLE_SHEET_MAX_NUM= 300000;
	
	/**
	 * @Title: exportExcelByStream
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param fileName
	 * @param fileSuffixName
	 * @param sheetName
	 * @param title
	 * @param data
	 * @param response
	 * @throws Exception
	 * @return: void
	 * @author: Ian.Huang
	 * @date: 2017年8月27日 下午10:06:48
	 */
	public static void exportExcelByStream(String fileName, String fileSuffixName, String sheetName, LinkedHashMap<String, String> title, List<Map<String,Object>> data, HttpServletResponse response) throws Exception {
		OutputStream output = null;
		byte[] bytes = null;
		SXSSFWorkbook wb=new SXSSFWorkbook(10000);
		if(OFFICE_EXCEL_2010_POSTFIX.equals(fileSuffixName)){
			bytes = exportXlsxHandle(wb, sheetName, title, data);
		}
		int length=0;
		if(bytes != null){
			length = bytes.length;
		}
		response.setContentType("application/msexcel;charset=utf-8");
		response.setHeader("Content-disposition", "attachment; filename=" + new String((fileName.concat("." + fileSuffixName)).getBytes("GB2312"),"ISO8859-1")); 
		response.setContentLength(length);
		output = response.getOutputStream();
		if(bytes != null){
			output.write(bytes);
		}
		output.flush();
		response.flushBuffer();
		output.close();
	}
	
	/**
	 * @Title: exportXlsxHandle
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param wb
	 * @param sheetName
	 * @param title
	 * @param data
	 * @throws Exception
	 * @return: byte[]
	 * @author: Ian.Huang
	 * @date: 2017年8月27日 下午10:11:08
	 */
	private static byte[] exportXlsxHandle(SXSSFWorkbook wb, String sheetName, LinkedHashMap<String, String> title, List<Map<String,Object>> data) throws Exception {
		int cycleNum = paginationCount(data.size(), SINGLE_SHEET_MAX_NUM);
		for(int i = 1; i <= cycleNum; i++) {
			if((i - 1) * SINGLE_SHEET_MAX_NUM == cycleNum) {
				break;
			}
			int end = (i - 1) * SINGLE_SHEET_MAX_NUM;
			if(i * SINGLE_SHEET_MAX_NUM > data.size()) {
				end = data.size();
			} else {
				end = i * SINGLE_SHEET_MAX_NUM;
			}
			wb = addSheet(wb, sheetName + "-" + i, title, data.subList((i - 1) * SINGLE_SHEET_MAX_NUM, end));
		}
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		wb.write(os);
		//关闭流
		os.close();
		return os.toByteArray();
	}
	
	/**
	 * @Title: addSheet
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param wb
	 * @param sheetName
	 * @param title
	 * @param data
	 * @throws Exception
	 * @return: SXSSFWorkbook
	 * @author: Ian.Huang
	 * @date: 2017年8月27日 下午10:11:16
	 */
	private static SXSSFWorkbook addSheet(SXSSFWorkbook wb, String sheetName, LinkedHashMap<String, String> title, List<Map<String,Object>> data) throws Exception {
		// 创建新sheet页
		SXSSFSheet sheet=wb.createSheet(sheetName);
		// 字段编码
		Collection<?> columnCode=title.keySet();
		int rowCount=0; 
		Row row = (Row) sheet.createRow(rowCount++);
		int cellCount=0;
		for(Object cc:columnCode) {
			row.createCell(cellCount++).setCellValue(title.get(cc));
			
		}
		// 表内容
		for(int i=0; i<data.size(); i++){
			row=(Row) sheet.createRow(rowCount++);
			cellCount=0;
			for(Object cc:columnCode){
				row.createCell(cellCount++).setCellValue(CommonUtils.checkExistOrNot(data.get(i).get(cc)) ? data.get(i).get(cc).toString().trim() : "");
			}
		}
		return wb;
	}
	
	private static int paginationCount(int count, int max) {
		int i= 1;
		if(count> max) {
			if((count% max)!= 0) {
				i= (count/ max)+ 1;
			}else{
				i= (count/ max);
			}
		}
		return i;
	}
	
}
