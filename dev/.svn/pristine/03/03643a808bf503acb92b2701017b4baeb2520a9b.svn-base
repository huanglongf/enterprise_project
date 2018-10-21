package com.bt.lmis.controller.excel;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.bt.lmis.model.StorageExpendituresData;
import com.bt.utils.DateUtil;

/**
 * @ClassName: ReadStorageExpendituresDataExcel
 * @Description: TODO(读取仓库原始数据)
 * @author Yuriy.Jiang
 * @date 2016年9月13日 上午11:13:22
 * 
 */
public class ReadStorageExpendituresDataExcel {
	
	public static void main(String[] args) {
		ReadStorageExpendituresDataExcel test = new ReadStorageExpendituresDataExcel();
		List<StorageExpendituresData> list = test.readExcel("/usr/local/logs/仓储费.xlsx");
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}
	}
	
	/**
	 * @Title: readExcel @Description: TODO(获取Excel转换程仓库原始数据) @param @param
	 * path @param @return 设定文件 @return List<StorageExpendituresData>
	 * 返回类型 @throws
	 */
	public List<StorageExpendituresData> readExcel(String path) {
		try {
			if (path == null || Common.EMPTY.equals(path)) {
				return null;
			} else {
				String postfix = Util.getPostfix(path);
				if (!Common.EMPTY.equals(postfix)) {
					if (Common.OFFICE_EXCEL_2003_POSTFIX.equals(postfix)) {
							return readXls(path);
					} else if (Common.OFFICE_EXCEL_2010_POSTFIX.equals(postfix)) {
						return readXlsx(path);
					}
				} else {
					System.out.println(path + Common.NOT_EXCEL_FILE);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<StorageExpendituresData> readXlsx(String path) throws IOException {
		System.out.println(Common.PROCESSING + path);
		InputStream is = new FileInputStream(path);
		XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
		StorageExpendituresData storageExpendituresData = null;
		List<StorageExpendituresData> list = new ArrayList<StorageExpendituresData>();
		// Read the Sheet
		for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
			XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
			if (xssfSheet == null) {
				continue;
			}
			// Read the Row
			for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
				XSSFRow xssfRow = xssfSheet.getRow(rowNum);
				if (xssfRow != null) {
					storageExpendituresData = new StorageExpendituresData();
					XSSFCell id = xssfRow.getCell(0);
					XSSFCell create_time = xssfRow.getCell(1);
					XSSFCell create_user = xssfRow.getCell(2);
					XSSFCell update_time = xssfRow.getCell(3);
					XSSFCell update_user = xssfRow.getCell(4);
					XSSFCell warehouse_type = xssfRow.getCell(5);
					XSSFCell start_time = xssfRow.getCell(6);
					XSSFCell store_id = xssfRow.getCell(7);
					XSSFCell store_name = xssfRow.getCell(8);
					XSSFCell is_closed = xssfRow.getCell(9);
					XSSFCell trade = xssfRow.getCell(10);
					XSSFCell warehouse_code = xssfRow.getCell(11);
					XSSFCell warehouse_name = xssfRow.getCell(12);
					XSSFCell system_warehouse = xssfRow.getCell(13);
					XSSFCell whcost_center = xssfRow.getCell(14);
					XSSFCell cost_code = xssfRow.getCell(15);
					XSSFCell cost_name = xssfRow.getCell(16);
					XSSFCell area_code = xssfRow.getCell(17);
					XSSFCell area_name = xssfRow.getCell(18);
					XSSFCell item_type = xssfRow.getCell(19);
					XSSFCell storage_type = xssfRow.getCell(20);
					XSSFCell storage_acreage = xssfRow.getCell(21);
					XSSFCell acreage_unit = xssfRow.getCell(22);
					XSSFCell settle_flag = xssfRow.getCell(23);
					XSSFCell end_time = xssfRow.getCell(24);
					storageExpendituresData.setId(Integer.valueOf(getValue(id)));
					storageExpendituresData.setCreate_time(DateUtil.StrToTime(getValue(create_time)));
					storageExpendituresData.setCreate_user(getValue(create_user));
					storageExpendituresData.setUpdate_time(DateUtil.StrToTime(getValue(update_time)));
					storageExpendituresData.setUpdate_user(getValue(update_user));
					storageExpendituresData.setWarehouse_type(Integer.valueOf(getValue(warehouse_type)));
					storageExpendituresData.setStart_time(getValue(start_time));
					storageExpendituresData.setStore_id(Integer.valueOf(getValue(store_id)));
					storageExpendituresData.setStore_name(getValue(store_name));
					storageExpendituresData.setIs_closed(Integer.valueOf(getValue(is_closed)));
					storageExpendituresData.setTrade(getValue(trade));
					storageExpendituresData.setWarehouse_code(getValue(warehouse_code));
					storageExpendituresData.setWarehouse_name(getValue(warehouse_name));
					storageExpendituresData.setSystem_warehouse(getValue(system_warehouse));
					storageExpendituresData.setWhcost_center(getValue(whcost_center));
					storageExpendituresData.setCost_code(getValue(cost_code));
					storageExpendituresData.setCost_name(getValue(cost_name));
					storageExpendituresData.setArea_code(getValue(area_code));
					storageExpendituresData.setArea_name(getValue(area_name));
					storageExpendituresData.setItem_type(getValue(item_type));
					storageExpendituresData.setStorage_type(Integer.valueOf(getValue(storage_type)));
					storageExpendituresData.setStorage_acreage(new BigDecimal(getValue(storage_acreage)));
					storageExpendituresData.setAcreage_unit(getValue(acreage_unit));
					storageExpendituresData.setSettle_flag(Integer.valueOf(getValue(settle_flag)));
					storageExpendituresData.setEnd_time(getValue(end_time));
					list.add(storageExpendituresData);
				}
			}
		}
		return list;
	}

	/**
	 * Read the Excel 2003-2007
	 * 
	 * @param path
	 *            the path of the Excel
	 * @return
	 * @throws IOException
	 */
	public List<StorageExpendituresData> readXls(String path) throws IOException {
		System.out.println(Common.PROCESSING + path);
		InputStream is = new FileInputStream(path);
		HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
		StorageExpendituresData storageExpendituresData = null;
		List<StorageExpendituresData> list = new ArrayList<StorageExpendituresData>();
		// Read the Sheet
		for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
			HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
			if (hssfSheet == null) {
				continue;
			}
			// Read the Row
			for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
				HSSFRow hssfRow = hssfSheet.getRow(rowNum);
				if (hssfRow != null) {
					storageExpendituresData = new StorageExpendituresData();
					HSSFCell id = hssfRow.getCell(0);
					HSSFCell create_time = hssfRow.getCell(1);
					HSSFCell create_user = hssfRow.getCell(2);
					HSSFCell update_time = hssfRow.getCell(3);
					HSSFCell update_user = hssfRow.getCell(4);
					HSSFCell warehouse_type = hssfRow.getCell(5);
					HSSFCell start_time = hssfRow.getCell(6);
					HSSFCell store_id = hssfRow.getCell(7);
					HSSFCell store_name = hssfRow.getCell(8);
					HSSFCell is_closed = hssfRow.getCell(9);
					HSSFCell trade = hssfRow.getCell(10);
					HSSFCell warehouse_code = hssfRow.getCell(11);
					HSSFCell warehouse_name = hssfRow.getCell(12);
					HSSFCell system_warehouse = hssfRow.getCell(13);
					HSSFCell whcost_center = hssfRow.getCell(14);
					HSSFCell cost_code = hssfRow.getCell(15);
					HSSFCell cost_name = hssfRow.getCell(16);
					HSSFCell area_code = hssfRow.getCell(17);
					HSSFCell area_name = hssfRow.getCell(18);
					HSSFCell item_type = hssfRow.getCell(19);
					HSSFCell storage_type = hssfRow.getCell(20);
					HSSFCell storage_acreage = hssfRow.getCell(21);
					HSSFCell acreage_unit = hssfRow.getCell(22);
					HSSFCell settle_flag = hssfRow.getCell(23);
					HSSFCell end_time = hssfRow.getCell(24);
					storageExpendituresData.setId(Integer.valueOf(getValue(id)));
					storageExpendituresData.setCreate_time(DateUtil.StrToTime(getValue(create_time)));
					storageExpendituresData.setCreate_user(getValue(create_user));
					storageExpendituresData.setUpdate_time(DateUtil.StrToTime(getValue(update_time)));
					storageExpendituresData.setUpdate_user(getValue(update_user));
					storageExpendituresData.setWarehouse_type(Integer.valueOf(getValue(warehouse_type)));
					storageExpendituresData.setStart_time(getValue(start_time));
					storageExpendituresData.setStore_id(Integer.valueOf(getValue(store_id)));
					storageExpendituresData.setStore_name(getValue(store_name));
					storageExpendituresData.setIs_closed(Integer.valueOf(getValue(is_closed)));
					storageExpendituresData.setTrade(getValue(trade));
					storageExpendituresData.setWarehouse_code(getValue(warehouse_code));
					storageExpendituresData.setWarehouse_name(getValue(warehouse_name));
					storageExpendituresData.setSystem_warehouse(getValue(system_warehouse));
					storageExpendituresData.setWhcost_center(getValue(whcost_center));
					storageExpendituresData.setCost_code(getValue(cost_code));
					storageExpendituresData.setCost_name(getValue(cost_name));
					storageExpendituresData.setArea_code(getValue(area_code));
					storageExpendituresData.setArea_name(getValue(area_name));
					storageExpendituresData.setItem_type(getValue(item_type));
					storageExpendituresData.setStorage_type(Integer.valueOf(getValue(storage_type)));
					storageExpendituresData.setStorage_acreage(new BigDecimal(getValue(storage_acreage)));
					storageExpendituresData.setAcreage_unit(getValue(acreage_unit));
					storageExpendituresData.setSettle_flag(Integer.valueOf(getValue(settle_flag)));
					storageExpendituresData.setEnd_time(getValue(end_time));
					list.add(storageExpendituresData);
				}
			}
		}
		return list;
	}

	@SuppressWarnings("static-access")
	private String getValue(XSSFCell xssfRow) {
		if (xssfRow.getCellType() == xssfRow.CELL_TYPE_BOOLEAN) {
			return String.valueOf(xssfRow.getBooleanCellValue());
		} else if (xssfRow.getCellType() == xssfRow.CELL_TYPE_NUMERIC) {
			return String.valueOf(xssfRow.getNumericCellValue());
		} else {
			return String.valueOf(xssfRow.getStringCellValue());
		}
	}

	@SuppressWarnings("static-access")
	private String getValue(HSSFCell hssfCell) {
		if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN) {
			return String.valueOf(hssfCell.getBooleanCellValue());
		} else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {
			return String.valueOf(hssfCell.getNumericCellValue());
		} else {
			return String.valueOf(hssfCell.getStringCellValue());
		}
	}
}
