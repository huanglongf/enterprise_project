package com.bt.lmis.core.business;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.Date;

import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ExcelParse2007 implements IExcelParse {

	// Excel工作区
	private XSSFWorkbook wb = null;

	/*
	 * (non-Javadoc)加载excel文件，获取excel工作区
	 * 
	 * @see com.bt.lmis.core.business.IExcelParse#loadExcel(java.lang.String)
	 */
	@Override
	public void loadExcel(String filePathAndName) throws FileNotFoundException, IOException {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(filePathAndName);
			wb = new XSSFWorkbook(fis);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new FileNotFoundException("加载Excel文件失败：" + e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			throw new IOException("加载Excel文件失败：" + e.getMessage());
		} finally {
			if (fis != null) {
				fis.close();
				fis = null;
			}
		}
	}

	/*
	 * (non-Javadoc)获取sheet页名称
	 * 
	 * @see com.bt.lmis.core.business.IExcelParse#getSheetName(int)
	 */
	@Override
	public String getSheetName(int sheetNo) {
		return wb.getSheetName(sheetNo - 1);
	}

	/*
	 * (non-Javadoc)获取sheet页数
	 * 
	 * @see com.bt.lmis.core.business.IExcelParse#getSheetCount()
	 */
	@Override
	public int getSheetCount() throws Exception {
		int sheetCount = wb.getNumberOfSheets();
		if (sheetCount == 0) {
			throw new Exception("Excel中没有SHEET页");
		}
		return sheetCount;
	}

	/*
	 * (non-Javadoc)获取sheetNo页行数
	 * 
	 * @see com.bt.lmis.core.business.IExcelParse#getRowCount(int)
	 */
	@Override
	public int getRowCount(int sheetNo) {
		int rowCount = 0;
		XSSFSheet sheet = wb.getSheetAt(sheetNo - 1);
		rowCount = sheet.getLastRowNum();
		return rowCount;
	}

	/* (non-Javadoc)获取sheetNo页行数(含有操作或者内容的真实行数)
	 * @see com.bt.lmis.core.business.IExcelParse#getRealRowCount(int)
	 */
	@Override
	public int getRealRowCount(int sheetNo) {
		int rowCount = 0;
		int rowNum = 0;
		XSSFSheet sheet = wb.getSheetAt(sheetNo - 1);
		rowCount = sheet.getLastRowNum();
		if (rowCount == 0) {
			return rowCount;
		}
		XSSFRow row = null;
		XSSFCell cell = null;
		rowNum = rowCount;
		for (int i = 0; i < rowCount; i++) {
			row = sheet.getRow(rowNum);
			rowNum--;
			if (row == null) {
				continue;
			}
			short firstCellNum = row.getFirstCellNum();
			short lastCellNum = row.getLastCellNum();
			for (int j = firstCellNum; j < lastCellNum; j++) {
				cell = row.getCell(j);
				if (cell == null) {
					continue;
				} else if (cell.getCellType() == XSSFCell.CELL_TYPE_BLANK) {
					continue;
				} else if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
					String value = cell.getStringCellValue();
					if (value == null || value.equals("")) {
						continue;
					} else {
						value = value.trim();
						if (value.isEmpty() || value.equals("") || value.length() == 0) {
							continue;
						}
					}
				}
				rowCount = rowNum + 1;
				return rowCount;
			}
		}

		rowCount = rowNum;
		return rowCount;
	}

	/* (non-Javadoc)读取第sheetNo个sheet页中第rowNo行第cellNo列的数据
	 * @see com.bt.lmis.core.business.IExcelParse#readExcelByRowAndCell(int, int, int)
	 */
	@Override
	public String readExcelByRowAndCell(int sheetNo, int rowNo, int cellNo) throws Exception {
		 String rowCellData = "";
	        XSSFSheet sheet = wb.getSheetAt(sheetNo - 1);
	        String sheetName = wb.getSheetName(sheetNo - 1);
	        try {
	            XSSFRow row = sheet.getRow(rowNo - 1);
	            if (row == null) {
	                return "NoData";
	            }
	            XSSFCell cell = row.getCell((short) (cellNo - 1));
	            if (cell == null) {
	                return "NoData";
	            }
	            int cellType = cell.getCellType();
	            String df = cell.getCellStyle().getDataFormatString();
	            if (cellType == XSSFCell.CELL_TYPE_NUMERIC) {// 数值(包括excel中数值、货币、日期、时间、会计专用等单元格格式)
	                double d = cell.getNumericCellValue();
	                // 判断数值是否是日期，该方法只能识别部分日期格式,故加入第二个判断条件对不能识别的日期再次进行识别
	                if (DateUtil.isCellDateFormatted(cell) || df.contains("yyyy\"年\"m\"月\"d\"日\"")) {// 日期、时间单元格格式
	                    Date date = DateUtil.getJavaDate(d);
	                    Timestamp timestamp = new Timestamp(date.getTime());
	                    String temp = timestamp.toString();
	                    if (temp.endsWith("00:00:00.0")) {// yyyy-MM-dd 格式
	                        rowCellData = temp.substring(0,
	                                temp.lastIndexOf("00:00:00.0"));
	                    } else if (temp.endsWith(".0")) {// yyyy-MM-dd hh:mm:ss 格式
	                        rowCellData = temp.substring(0, temp.lastIndexOf(".0"));
	                    } else {
	                        rowCellData = timestamp.toString();
	                    }
	                } else {// 数值、货币、会计专用、百分比、分数、科学记数 单元格式
	                    rowCellData = new DecimalFormat("0.########").format(d);
	                }
	            } else if (cellType == XSSFCell.CELL_TYPE_STRING) {// 文本
	                rowCellData = cell.getStringCellValue();
	            } else if (cellType == XSSFCell.CELL_TYPE_FORMULA) {// 公式
	                double d = cell.getNumericCellValue();
	                rowCellData = String.valueOf(d);
	            } else if (cellType == XSSFCell.CELL_TYPE_BLANK) {// 空
	                rowCellData = "";
	            } else if (cellType == XSSFCell.CELL_TYPE_BOOLEAN) {// 布尔值
	                rowCellData = "";
	            } else if (cellType == XSSFCell.CELL_TYPE_ERROR) {// 异常
	                rowCellData = "";
	            } else {
	                throw new Exception(sheetName + " sheet页中" + "第" + rowNo + "行" + "第" + cellNo + "列,单元格格式无法识别，请检查sheet页");
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            throw new Exception(sheetName + "sheet页中" + "第" + rowNo + "行" + "第"  + cellNo + "列" + "数据不符合要求,请检查sheet页");
	        }
	        return rowCellData;
	}

	/* (non-Javadoc) 读取第sheetNo个sheet页中第rowNo行的数据
	 * @see com.bt.lmis.core.business.IExcelParse#readExcelByRow(int, int)
	 */
	@Override
	public String[] readExcelByRow(int sheetNo, int rowNo) throws Exception {
		String[] rowData = null;
        XSSFSheet sheet = wb.getSheetAt(sheetNo - 1);
        int cellCount = sheet.getRow(rowNo - 1).getLastCellNum();
        if(null!=sheet.getRow(rowNo - 1) && cellCount!=-1){
            rowData = new String[cellCount];
            for (int k = 1; k <= cellCount; k++) {
                rowData[k - 1] = readExcelByRowAndCell(sheetNo, rowNo, k);
            }
        }
        return rowData;
	}

	@Override
	public String[] readExcelByCell(int sheetNo, int cellNo) throws Exception {
		String[] cellData = null;
        XSSFSheet sheet = wb.getSheetAt(sheetNo - 1);
        int rowCount = sheet.getLastRowNum();
        cellData = new String[rowCount + 1];
        for (int i = 0; i <= rowCount; i++) {
            cellData[i] = readExcelByRowAndCell(sheetNo - 1, i, cellNo - 1);
        }
        return cellData;
	}

	@Override
	public void close() {
		if (wb != null) {
            try {
                wb.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                wb = null;
            }
        }
	}

}
