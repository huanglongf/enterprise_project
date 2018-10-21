package com.bt.utils;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Color;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;

import com.bt.lmis.model.ColorInfo;
import com.bt.lmis.model.ExcelPackageObjects;
import com.csvreader.CsvReader;

public class ExcelUtils {
	
	public static ExcelPackageObjects packageCsvToObjects(Integer batId, CsvReader csvReader, String className) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		// 封装Excel对象
		ExcelPackageObjects excelPackageObjects = null;
		// 封装集合
		List<Object> objects = null;
		// 每一行的封装
		Object object = null;
		// 获取某个类的Class对象
		Class<?> clazz = null;
		// 封装表头
		List<String> titles = null;
		// 一行值的数组
		String[] values = null;
		// 行计数器
		int row_num = 0;
		// 列计数器
		int column_num = 0;
		while(csvReader.readRecord()){
			// 获取行
			// 数据由制表符分隔
			values = csvReader.getRawRecord().split("	");
			//　第一行为表头
			if(row_num == 0){
        		// 封装表头
        		titles = new ArrayList<String>();
        		for(String value : values) {
        			// 序号列不需要记录
        			if(column_num != 0){
        				if(!CommonUtils.checkExistOrNot(value)) {
        					value = "";
                        }
        				if(value.equals("#")){
        					break;
        				} else {
        					titles.add(value);
        				}
        			}
    				column_num++;
    			}
        	} else {
        		if(row_num == 1){
    				excelPackageObjects = new ExcelPackageObjects();
    				objects = new ArrayList<Object>();
    				clazz = Class.forName(className);
    			}
    			//利用获取到的类的Class对象新建一个实例（相当于new了个对象）
    			object = clazz.newInstance();
    			clazz.getMethod("setBat_id", Integer.class).invoke(object, batId);
    			for(String value : values) {
    				// 序号列不需要记录
        			if(column_num != 0){
        				if(!CommonUtils.checkExistOrNot(value)) {
        					value = "";
                        }
        				if(value.equals("#")){
        					break;
        				} else {
        					//获取clazz的方法，第一个参数是方法名；第二个参数是参数的类型，注意是参数的类型！
    						clazz
    						.getMethod("set" + CommonUtils.shiftFirstLetter(titles.get(column_num - 1), 0), String.class)
    						.invoke(object, value);
        				}
        			}
        			column_num++;
                }
    			System.out.println("第" + row_num + "行共" + (column_num + 1) + "列！");
    			if(titles.size() != (column_num - 1)){
    				// 如行数据封装字段数量与封装字段不符，则封装错误
    				excelPackageObjects.setResult_flag(false);
    				excelPackageObjects.setReason("第" + row_num +  "行封装字段数量：" + (column_num - 1) + "；类字段实际数量：" + titles.size() + "；数据格式存在异常，或某字段多空格");
    				return excelPackageObjects;
    			} else {
    				objects.add(object);
    				// 第一行为类字段，不是数据封装
    				if(row_num != 0){
    					System.out.println("第" + row_num + "行：封装成功！");
    				}
    			}
        	}
			// 初始化列标
			column_num = 0;
			// 下一行
			row_num++;
//			if(row_num == 12174){
//				System.out.println("开始调试！");
//			}
		}
		if(CommonUtils.checkExistOrNot(excelPackageObjects)){
			if(CommonUtils.checkExistOrNot(objects)){
				excelPackageObjects.setResult_flag(true);
				excelPackageObjects.setResult(objects);
			} else {
				excelPackageObjects.setResult_flag(false);
				excelPackageObjects.setReason("上传CSV为空！");
			}
		} else {
			excelPackageObjects = new ExcelPackageObjects();
			excelPackageObjects.setResult_flag(false);
			excelPackageObjects.setReason("上传CSV格式非法！");
		}
		return excelPackageObjects;
	}
	
    /** 
     * excel97中颜色转化为uof颜色 
     *  
     * @param color 颜色序号 
     * @return 颜色或者null 
     */  
    private static ColorInfo excel97Color2UOF(Workbook book, short color) {  
        if(book instanceof HSSFWorkbook) {  
            HSSFWorkbook hb = (HSSFWorkbook) book;  
            HSSFColor hc = hb.getCustomPalette().getColor(color);  
            ColorInfo ci = excelColor2UOF(hc);  
            return ci;  
        }  
        return null;  
    }
      
    /** 
     * excel(包含97和2007)中颜色转化为uof颜色 
     *  
     * @param color 颜色序号 
     * @return 颜色或者null 
     */  
    private static ColorInfo excelColor2UOF(Color color) {  
        if(color == null) {  
            return null;  
        }  
        ColorInfo ci = null;  
        if(color instanceof XSSFColor) {// .xlsx  
            XSSFColor xc = (XSSFColor) color;  
            byte[] b = xc.getRGB();  
            if (b != null) {// 不一定是argb
            	if(b.length == 3){
            		ci = ColorInfo.fromARGB(b[0], b[1], b[2]);
            	} else if(b.length == 4){
            		ci = ColorInfo.fromARGB(b[0], b[1], b[2], b[3]);
            	}
            }  
        } else if(color instanceof HSSFColor) {// .xls  
            HSSFColor hc = (HSSFColor) color;  
            short[] s = hc.getTriplet();// 一定是rgb  
            if(s != null) {
            	ci = ColorInfo.fromARGB(s[0], s[1], s[2]);  
            }  
        }  
        return ci;  
    }  
	
	/**
	 * 
	 * @Description: TODO(将Excel封装为指定表对应对象)
	 * @param batId
	 * @param workbook
	 * @return
	 * @return: ExcelPackageObjects  
	 * @author Ian.Huang 
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @date 2016年9月18日上午10:26:04
	 */
	public static ExcelPackageObjects packageExcelToObjects(Integer batId, Workbook workbook, String className) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException{
		// 封装Excel对象
		ExcelPackageObjects excelPackageObjects = null;
		// 封装集合
		List<Object> objects = null;
		// 每一行的封装
		Object object = null;
		// 获取某个类的Class对象
		Class<?> clazz = null;
		//
		Font eFont = null;
		// 字体颜色  
		ColorInfo color = null;  
		//
		Row row = null;
		//
		Cell cell = null;
		// 封装表头
		List<Map<String, String>> titles = null;
		// 是否必填-内容
		Map<String, String> title = null;
		for(int i = 0; i < workbook.getNumberOfSheets(); i++){
			// 循环sheet页
			Sheet sheet = workbook.getSheetAt(i);
			// 需要存储数据的sheet页，第一行存在且第一个单元格值为seq的sheet页
			if(CommonUtils.checkExistOrNot(sheet.getRow(0)) 
					&& CommonUtils.checkExistOrNot(sheet.getRow(0).getCell(0)) 
					&& ExcelUtils.getCellValue(sheet.getRow(0).getCell(0)).equals("seq")){
				if(i == 0){
					clazz = Class.forName(className);
					excelPackageObjects = new ExcelPackageObjects();
					objects = new ArrayList<Object>();
				}
				// 封装表头
				row = sheet.getRow(0);
				titles = new ArrayList<Map<String, String>>();
				// 从第二列加载字段标题
				int column_num = 1;
				while(CommonUtils.checkExistOrNot(row.getCell(column_num))
						&& CommonUtils.checkExistOrNot(ExcelUtils.getCellValue(row.getCell(column_num)))){
					cell = row.getCell(column_num);
					title = new HashMap<String, String>();
					title.put("attribute", ExcelUtils.getCellValue(cell));
					// 获取单元格字体样式
					eFont = workbook.getFontAt(cell.getCellStyle().getFontIndex());
					if(eFont instanceof XSSFFont) {  
						XSSFFont f = (XSSFFont) eFont; 
					    color = excelColor2UOF(f.getXSSFColor());
					} else {
					    color = excel97Color2UOF(workbook, eFont.getColor());
					}
					// 判断是否标红
					if(CommonUtils.checkExistOrNot(color) && (-65536 == color.toRGB())){
						// 标红则必填
						title.put("is null", "n");
					} else {
						title.put("is null", "y");
					}
					titles.add(title);
					column_num++;
				}
				// 循环所有sheet页内所有行
				for(int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++){
					// 校验是否有效数据行或是否已过最后一条数据
					if(CommonUtils.checkExistOrNot(sheet.getRow(rowNum))
							&& CommonUtils.checkExistOrNot(sheet.getRow(rowNum).getCell(0))){
						row = sheet.getRow(rowNum);
						//利用获取到的类的Class对象新建一个实例（相当于new了个对象）
						object = clazz.newInstance();
						clazz.getMethod("setBat_id", Integer.class).invoke(object, batId);
						for(column_num = 0; column_num < titles.size(); column_num++){
							// 标题头
							title = titles.get(column_num);
							// 元素内容
							cell = row.getCell(column_num + 1);
							// 如字段必填且单元格内容不存在
							if(title.get("is null").equals("n")
									&& 
									(!CommonUtils.checkExistOrNot(cell) 
											|| !CommonUtils.checkExistOrNot(getCellValue(cell)))){
								excelPackageObjects.setResult_flag(false);
								excelPackageObjects.setReason("第" + rowNum + "行：字段" + title.get("attribute") + "不能为空！");
								return excelPackageObjects;
							}
							if(CommonUtils.checkExistOrNot(cell)){
								//获取clazz的方法，第一个参数是方法名；第二个参数是参数的类型，注意是参数的类型！
								clazz
								.getMethod("set" + CommonUtils.shiftFirstLetter(title.get("attribute"), 0), String.class)
								.invoke(object, getCellValue(cell));
							}
						}
						objects.add(object);
					} else {
						// 如不符合条件则不需要插入数据
						break;
					}
				}
			}
		}
		if(CommonUtils.checkExistOrNot(excelPackageObjects)){
			if(CommonUtils.checkExistOrNot(objects)){
				excelPackageObjects.setResult_flag(true);
				excelPackageObjects.setResult(objects);
			} else {
				excelPackageObjects.setResult_flag(false);
				excelPackageObjects.setReason("上传EXCEL为空！");
			}
		} else {
			excelPackageObjects = new ExcelPackageObjects();
			excelPackageObjects.setResult_flag(false);
			excelPackageObjects.setReason("上传EXCEL格式非法！");
		}
		return excelPackageObjects;
	}
	
	 /**
	 * 
	 * @Description: TODO(获取excel单元格内容)
	 * @param cell
	 * @return
	 * @return: String  
	 * @author Ian.Huang 
	 * @date 2016年9月13日下午4:28:46
	 */
	public static String getCellValue(Cell cell) {  
        String cellValue = "";
        switch (cell.getCellType()) {
        case HSSFCell.CELL_TYPE_STRING: 
            cellValue = cell.getRichStringCellValue().getString().trim();
            break;
        case HSSFCell.CELL_TYPE_NUMERIC:
        	// 区分日期还是纯数字
            if (HSSFDateUtil.isCellDateFormatted(cell)) {
                cellValue = DateFormatUtils.format(cell.getDateCellValue(), "yyyy-MM-dd HH:mm:ss");
            } else {
            	cellValue = new DecimalFormat("#").format(cell.getNumericCellValue()).toString();
            }
            break;  
        case HSSFCell.CELL_TYPE_BOOLEAN:  
            cellValue = String.valueOf(cell.getBooleanCellValue()).trim();
            break;
        case HSSFCell.CELL_TYPE_FORMULA:
            cellValue = cell.getCellFormula();
            break;  
        default:  
            cellValue = "";  
        }  
        return cellValue;
    }  
}
