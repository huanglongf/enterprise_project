package com.bt.utils;

import java.beans.IntrospectionException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import com.bt.lmis.model.SimpleMasterSlaveReport;
import com.bt.lmis.model.SimpleTable;

/**
 * @Title:ReportUtils(报表工具类)
 * @Description: TODO  
 * @author Ian.Huang 
 * @date 2017年3月17日下午5:43:20
 */
public class ReportUtils {
	
	/**
	 * 
	 * @Description: TODO(生成简单主从报表)
	 * @param smsr
	 * @return: void  
	 * @author Ian.Huang 
	 * @throws IOException 
	 * @throws Exception 
	 * @date 2017年3月17日下午7:57:46
	 */
	public static void generateSimpleMasterSlaveReport(SimpleMasterSlaveReport smsr) throws Exception {
		// 输出文件流
		FileOutputStream output = null;
		// 内存中保留 10000 条数据，以免内存溢出，其余写入硬盘  
		SXSSFWorkbook wb = new SXSSFWorkbook(10000);
		//
		try {
			// 路径生成
			FileUtil.isExistPath(smsr.getFilePath());
			//
			output = new FileOutputStream(new File(smsr.getFilePath()));
			// 生成表
			for(int i= 0; i< smsr.getTables().size(); i++) {
				wb= addSheet(wb, smsr.getTables().get(i));
				
			}
			wb.write(output);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
			
		} finally {
			try {
				wb.close();
				output.close();
				
			} catch (IOException e) {
				e.printStackTrace();
				throw e;
				
			}
			
		}
		System.out.println("*************" + smsr.getFilePath() + "已生成*************");
		
	}
	
	/**
	 * 
	 * @Description: TODO
	 * @param wb
	 * @param table
	 * @return: SXSSFWorkbook  
	 * @author Ian.Huang 
	 * @throws IntrospectionException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 * @date 2017年3月17日下午9:00:16
	 */
	public static SXSSFWorkbook addSheet(SXSSFWorkbook wb, SimpleTable table) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, IntrospectionException {
		// 创建新sheet页
		SXSSFSheet sheet=wb.createSheet(table.getTableName());
		// 表头
		LinkedHashMap<String, String> tableHeader=table.getTableHeader();
		// 表内容
		List<Map<String, Object>> tableContent=table.getTableContent();
		// 字段编码
		Collection<?> columnCode=tableHeader.keySet();
		int rowCount=0; 
		Row row = (Row) sheet.createRow(rowCount++);
		int cellCount=0;
		for(Object cc:columnCode) {
			row.createCell(cellCount++).setCellValue(tableHeader.get(cc));
			
		}
		// 表内容
		for(int i=0; i<tableContent.size(); i++){
			row=(Row) sheet.createRow(rowCount++);
			cellCount=0;
			for(Object cc:columnCode){
				row.createCell(cellCount++).setCellValue(CommonUtils.checkExistOrNot(tableContent.get(i).get(cc))?tableContent.get(i).get(cc).toString().trim():"");
				
			}
			
		}
		return wb;
		
	}

}