package com.bt.utils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;


public class ExcelStyle {
	public static void main(String[] args) {
		String[] a = new String[]{"仓库","区域","类型","结算金额"};
		createCell("/usr/local/apache-tomcat-7.0.69/poi/text.xls",a);
	}
	
	/** 
	* @Title: createCell 
	* @Description: TODO(创建xls) 
	* @param @param url    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	private static void createCell(String url,String[] a) {
		FileOutputStream fileOut;
		//创建行 & 创建列 start
		for (int i = 0; i < a.length; i++) {
			Workbook wb=new HSSFWorkbook(); 											// 定义一个新的工作簿
			Sheet sheet=wb.createSheet("第一个Sheet页");  								// 创建第一个Sheet页
			Row row = sheet.createRow(0); 												// 创建一个行
			row.setHeightInPoints(20);
			Cell cell=row.createCell((short)i);  										// 创建单元格(short)0 (short)1 (short)2 (short)3
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
			}
		}
	}
	
}
