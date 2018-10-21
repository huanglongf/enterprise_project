/**
 * 
 */
package com.bt.dataReport.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *@author jinggong.li
 *@date 2018年7月4日  
 */
public class ExportExcelUtils {
	/**
	 * 
	 * @param result 数据信息
	 * @param sheetName 文件名称
	 * @param headers 标题
	 * @param workbook
	 * @return
	 * @throws Exception 
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")  
	public static File produceExcel(String filePath,String fileName,String[] headers,List<Map<String,Object>> result) throws Exception{
	 XSSFWorkbook workbook = new XSSFWorkbook();
	 String filePaths = filePath + "/" + fileName + ".xlsx";
	  OutputStream out = null;
      // 生成一个表格  
      XSSFSheet sheet = workbook.createSheet();  
      /*workbook.setSheetName(sheetNum,sheetTitle);*/  
      // 设置表格默认列宽度为20个字节  
      sheet.setDefaultColumnWidth((short)20);  
      // 生成一个样式  
      XSSFCellStyle style = workbook.createCellStyle();  
      // 设置这些样式  
      style.setFillForegroundColor(HSSFColor.PALE_BLUE.index);  
      style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);  
      style.setBorderBottom(HSSFCellStyle.BORDER_THIN);  
      style.setBorderLeft(HSSFCellStyle.BORDER_THIN);  
      style.setBorderRight(HSSFCellStyle.BORDER_THIN);  
      style.setBorderTop(HSSFCellStyle.BORDER_THIN);  
      style.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
      // 生成一个字体  
      XSSFFont font = workbook.createFont();  
      font.setColor(HSSFColor.BLACK.index);  
      font.setFontHeightInPoints((short) 12);  
      font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);  
      // 把字体应用到当前的样式  
      style.setFont(font);  
      // 指定当单元格内容显示不下时自动换行  
      style.setWrapText(true);  
      // 产生表格标题行  
      XSSFRow row = sheet.createRow(0);  
      for (int i = 0; i < headers.length; i++) {  
          XSSFCell cell = row.createCell((short) i);      
          cell.setCellStyle(style);  
          HSSFRichTextString text = new HSSFRichTextString(headers[i]);  
          cell.setCellValue(text.toString());  
      }
      // 遍历集合数据，产生数据行  
      if (result != null) {  
          int index = 1;  
          for (Map<String,Object> m : result) {  
              row = sheet.createRow(index);  
              int cellIndex = 0;
              for(Object str:m.values()){//遍历map的值
            	  XSSFCell cell = row.createCell((short) cellIndex);  
                  cell.setCellValue(str.toString());  
                  cellIndex++; 
            	 }
              index++;  
          }  
      } 
        File file = new File(filePaths);
        if(!file.exists()) {
        	file.mkdirs();
        }
		try {
			out = new FileOutputStream(file);
			workbook.write(out);
		} catch (Exception e) {
			throw new Exception("下载文件失败");
		}finally {
			 try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return file;
  }
}
