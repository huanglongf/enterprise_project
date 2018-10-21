package com.baozun.easytask.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

public class BigExcelExport {

	/**
	 * @Title: excelDownLoadDatab @Description: TODO(大数据量导出) @param @param result
	 * 内容集合 @param @param cMap 表头 @param @param sheetName
	 * 文件名 @param @return @param @throws IOException 设定文件 @return File 返回类型 @throws
	 */
	@SuppressWarnings("unchecked")
	public static File excelDownLoadDatab(List<?> result, LinkedHashMap<String, String> cMap, String sheetName)
			throws IOException {
		String fileName = MailUtils.path+ sheetName + ".xlsx";
		FileOutputStream output = new FileOutputStream(new File(fileName));
		SXSSFWorkbook wb = new SXSSFWorkbook(10000);// 内存中保留 1000 条数据，以免内存溢出，其余写入 硬盘
		Sheet sheet1 = wb.createSheet(sheetName);
		int excelRow = 0;
		int titlelRow = 0;
		int contentlRow = 1;
		Row titleRow = (Row) sheet1.createRow(excelRow++);
		Collection<?> col = cMap.keySet();
		for (Object o : col) {
			Cell cell = titleRow.createCell(titlelRow++);
			cell.setCellValue(cMap.get(o));

		}
		for (int k = 0; k < result.size(); k++) {
			excelRow = 0;
			Row contentRow = (Row) sheet1.createRow(contentlRow++);
			HashMap<String, Object> restr = (HashMap<String, Object>) result.get(k);
			for (Object o : col) {
				Cell cell = contentRow.createCell(excelRow++);
				if (restr.get(o) != null) {
					if (restr.get(o) instanceof BigDecimal) {
						double val = ((BigDecimal) restr.get(o)).doubleValue();
						cell.setCellValue(val);
					} else if (restr.get(o) instanceof Double) {
						cell.setCellValue((double) restr.get(o));
					} else {
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
		System.out.println("*************导出" + fileName + "成功*************");
		return new File(fileName);

	}

}
