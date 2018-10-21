package com.bt.vims.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import common.Contents;

public class BigExcelExport {

	@SuppressWarnings("unchecked")
	public static File excelDownLoadDatab(List<?> result, HashMap<String, String> cMap, String sheetName)
			throws IOException {
		String fileName = BaseConst.file_path + sheetName;
		FileOutputStream output = new FileOutputStream(new File(fileName));
		SXSSFWorkbook wb = new SXSSFWorkbook(10000);// 内存中保留 1000
													// 条数据，以免内存溢出，其余写入 硬盘
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
					if (Contents.INDIVIDUAL.equals(restr.get(o).toString().trim())) {
						cell.setCellValue("个人");
					} else if (Contents.GROUP.equals(restr.get(o).toString().trim())) {
						cell.setCellValue("团体");
					} else {
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
		System.out.println("*************导出" + fileName + "成功*************");
		return new File(fileName);

	}

}
