package com.bt.utils;

import java.awt.Color;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;

import com.bt.EPlatform;
import com.bt.OSinfo;

/**
 * @Title:POIUtil
 * @Description: TODO  
 * @author Ian.Huang 
 * @date 2016年12月2日上午10:16:17
 */
public class POIUtil {
	
	public static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * 
	 * @Description: TODO(外调接口)
	 * @param workbook
	 * @param map
	 * @param type
	 * @throws Exception
	 * @return: SXSSFWorkbook  
	 * @author Ian.Huang 
	 * @date 2016年12月1日下午4:24:33
	 */
	public static SXSSFWorkbook create_sheet(SXSSFWorkbook workbook, Map<String, Object> map, boolean type) throws Exception {
		if(type) {
			// 汇总sheet
			return create_summary_sheet(workbook, "费用汇总", map);
			
		} else {
			// 明细sheet
			return create_detail_sheet(workbook, map);
			
		}
		
	}
	/**
	 * 
	 * @Description: TODO(创建明细页面)
	 * @param workbook
	 * @param detail
	 * @throws Exception
	 * @return: SXSSFWorkbook  
	 * @author Ian.Huang 
	 * @date 2016年12月1日下午4:12:18
	 */
	@SuppressWarnings("unchecked")
	private static SXSSFWorkbook create_detail_sheet(SXSSFWorkbook workbook, Map<String, Object> detail) throws Exception {
		SXSSFSheet sheet= workbook.createSheet(detail.get("sheet_name").toString());
    	//标题
    	Map<String, Object> sheet_title= (Map<String, Object>)detail.get("sheet_title");
    	
    	//设置标题字体样式
		Font fs= workbook.createFont();
		fs.setFontHeightInPoints((short) 9);
		fs.setFontName("Microsoft YaHei");
		fs.setBoldweight(Font.BOLDWEIGHT_BOLD); 				// 字体加粗  
		
		//统一边框颜色
	    XSSFColor xssfColor_border= new XSSFColor(new Color(202,211,225));
    	
    	//设置标题单元格样式
	    XSSFCellStyle style_title= (XSSFCellStyle)workbook.createCellStyle();
	    style_title.setBorderTop(BorderStyle.THIN);
	    style_title.setBorderBottom(BorderStyle.THIN);
	    style_title.setBorderLeft(BorderStyle.THIN);
	    style_title.setBorderRight(BorderStyle.THIN);
	    style_title.setTopBorderColor(xssfColor_border);
	    style_title.setBottomBorderColor(xssfColor_border);
	    style_title.setLeftBorderColor(xssfColor_border);
	    style_title.setRightBorderColor(xssfColor_border);
		style_title.setFont(fs);								//应用标题字体样式
		style_title.setAlignment(CellStyle.ALIGN_CENTER);		// 设置居中   
    	
    	//填充标题数据
    	List<Map<String, Object>> sheet_list= (List<Map<String, Object>>)detail.get("sheet_list");
    	Row row_title= sheet.createRow((short)0);  
    	int row_hs= 0;
    	Cell titleCell = null;
        sheet.trackAllColumnsForAutoSizing();					//设置允许自适应列宽
        Map<String,Integer> columnTypeMap = new HashMap<String,Integer>();
	    for (Entry<String, Object> entry: sheet_title.entrySet()) {
	    	titleCell = row_title.createCell((short)row_hs);
	    	titleCell.setCellStyle(style_title);
	    	titleCell.setCellValue(entry.getValue().toString());
    		columnTypeMap.put(entry.getKey(), getCellType(entry.getKey()));
	    	row_hs= row_hs+ 1;
	    	
	    	if(entry.getValue().toString().equals("操作时间")){
	    		
		    	titleCell = row_title.createCell((short)row_hs);
		    	titleCell.setCellStyle(style_title);
		    	titleCell.setCellValue("月旬");
	    		columnTypeMap.put(entry.getKey(), 0);
		    	row_hs= row_hs+ 1;
	    		
	    	}
	    	
		}
	    
	    //设置数据行字体样式
		Font fs_list= workbook.createFont();
		fs_list.setFontHeightInPoints((short) 9);
		fs_list.setFontName("Microsoft YaHei");
	    
	    //设置奇数数据行样式
		XSSFCellStyle style_list_odd= (XSSFCellStyle)workbook.createCellStyle();
	    style_list_odd.setFont(fs_list);							//应用数据行字体样式
	    style_list_odd.setFillForegroundColor(new XSSFColor(new Color(247,249,251)));//设置背景色
	    style_list_odd.setFillPattern(CellStyle.SOLID_FOREGROUND);  //背景色填充方式
	    style_list_odd.setBorderTop(BorderStyle.THIN);
	    style_list_odd.setBorderBottom(BorderStyle.THIN);
	    style_list_odd.setBorderLeft(BorderStyle.THIN);
	    style_list_odd.setBorderRight(BorderStyle.THIN);
	    style_list_odd.setTopBorderColor(xssfColor_border);
	    style_list_odd.setBottomBorderColor(xssfColor_border);
	    style_list_odd.setLeftBorderColor(xssfColor_border);
	    style_list_odd.setRightBorderColor(xssfColor_border);
	    style_list_odd.setAlignment(CellStyle.ALIGN_RIGHT);			// 设置居右  
		

	    //设置偶数数据行样式
	    XSSFCellStyle style_list_even= (XSSFCellStyle)workbook.createCellStyle();	
	    style_list_even.setFont(fs_list);							//应用数据行字体样式
	    style_list_even.setBorderTop(BorderStyle.THIN);
	    style_list_even.setBorderBottom(BorderStyle.THIN);
	    style_list_even.setBorderLeft(BorderStyle.THIN);
	    style_list_even.setBorderRight(BorderStyle.THIN);
	    style_list_even.setTopBorderColor(xssfColor_border);
	    style_list_even.setBottomBorderColor(xssfColor_border);
	    style_list_even.setLeftBorderColor(xssfColor_border);
	    style_list_even.setRightBorderColor(xssfColor_border);
	    style_list_even.setAlignment(CellStyle.ALIGN_RIGHT);		// 设置居右  
		
		
		Row row_data = null;
		Cell row_data_cell = null;
		int row_h = 0;
		XSSFCellStyle style_list_base = null;
		
		StringBuffer sb = new StringBuffer();
		Date operationDate = null;
		for (int k= 0; k< sheet_list.size(); k++) {
			row_data= sheet.createRow(k + 1);
			style_list_base = k % 2 == 0 ? style_list_even : style_list_odd;
			row_h= 0;
			for (String key: sheet_title.keySet()) {
				row_data_cell= row_data.createCell((short)row_h);
				row_data_cell.setCellStyle(style_list_base);
				if (columnTypeMap.get(key) == 1 || columnTypeMap.get(key) == 2) {
		    		try {
		    			row_data_cell.setCellValue(Double.parseDouble(sheet_list.get(k).get(key).toString()));
		    			row_data_cell.setCellType(Cell.CELL_TYPE_NUMERIC);
		    		} catch (NumberFormatException | NullPointerException e){
		    			row_data_cell.setCellValue(sheet_list.get(k).get(key) + "");
		    		}
				} else {
					row_data_cell.setCellType(Cell.CELL_TYPE_STRING);
					row_data_cell.setCellValue(sheet_list.get(k).get(key)+"");
				}
				row_h= row_h+ 1;
				
		    	if(key.equals("操作时间")){
		    		sb.setLength(0);
		    		operationDate = sdf.parse(sheet_list.get(k).get(key).toString());
		    		//sheet_list.get(k).get(key);
		    		
		    		row_data_cell= row_data.createCell((short)row_h);
					row_data_cell.setCellStyle(style_list_base);
					
					row_data_cell.setCellType(Cell.CELL_TYPE_STRING);
					
					sb.append(operationDate.getYear()+1900);
					sb.append("年");
					sb.append(operationDate.getMonth()+1);
					sb.append("月");
					if((operationDate.getDate()-1)/10 < 1){
						sb.append("上旬");
					} else if((operationDate.getDate()-1)/10 == 1){
						sb.append("中旬");
					} else {
						sb.append("下旬");
					}
					
					row_data_cell.setCellValue(sb.toString());
					row_h= row_h+ 1;
		    		
		    	}
			}
		}

		
//		for (int i = 0; i < row_hs; i++) {
//			sheet.autoSizeColumn(i, true);
//		}
		return workbook;
		
	}
	
	/**
	 * 0.String，1.int，2.double
	 * @param key
	 * @return
	 */
	private static int getCellType(String key) {
		
		if (key != null) {
			if (key.indexOf("量") > -1) {
				return 1;
			} else if (key.indexOf("费用") > -1 
					|| key.indexOf("计") > -1 || key.indexOf("元") > -1 
					|| key.indexOf("CM") > -1 || key.indexOf("价") > -1
					|| key.indexOf("KG") > -1 || key.indexOf("件") > -1) {
				return 2;
			}
		}
		
		return 0;
	}
	/**
	 * 
	 * @Description: TODO(创建客户/店铺账单汇总页面)
	 * @param workbook
	 * @param sheetName
	 * @param summary
	 * @throws Exception
	 * @return: SXSSFWorkbook  
	 * @author Ian.Huang 
	 * @date 2016年12月1日下午4:12:37
	 */
	@SuppressWarnings("unchecked")
	private static SXSSFWorkbook create_summary_sheet(SXSSFWorkbook workbook, String sheetName, Map<String, Object> summary) throws Exception{
		// Yuriy 2016-12-11--------------------------------------------
		// 1.Drawing绘图工具插入图片
		// 创建汇总sheet页
		SXSSFSheet cost_aggregation= workbook.createSheet(sheetName);
		// 将sheet页移至第一页
		workbook.setSheetOrder(sheetName, 0);
		cost_aggregation.setDefaultRowHeightInPoints(20); 
		Drawing patriarch= cost_aggregation.createDrawingPatriarch();
		//anchor主要用于设置图片的属性  
        ClientAnchor anchor= workbook.getCreationHelper().createClientAnchor();
        anchor.setRow1(0);  
        anchor.setCol1(0);  
        anchor.setDx1(0);  
        anchor.setDy1(0);  
        anchor.setRow2(2);  
        anchor.setCol2(1);  
        anchor.setDx2(0);  
        anchor.setDy2(0);
        //插入BaoZun Logo

		if(OSinfo.getOSname().equals(EPlatform.Linux) || OSinfo.getOSname().equals(EPlatform.Mac_OS_X)) {
			patriarch.createPicture(anchor, workbook.addPicture(IOUtils.toByteArray(new FileInputStream(CommonUtils.getAllMessage("config", "BALANCE_BILL_CUSTOMER_BAOZUN_LOGO_Linux"))), Workbook.PICTURE_TYPE_PNG));
		}else{
			patriarch.createPicture(anchor, workbook.addPicture(IOUtils.toByteArray(new FileInputStream(CommonUtils.getAllMessage("config", "BALANCE_BILL_CUSTOMER_BAOZUN_LOGO_" + OSinfo.getOSname()))), Workbook.PICTURE_TYPE_PNG));
		}
        // 2.HSSFSheet获取图片方式
//		ByteArrayOutputStream byteArrayOut= new ByteArrayOutputStream();
        // 获取BaoZun Logo
//		bufferImg = ImageIO.read(new File(CommonUtils.getAllMessage("config", "excel_logo")));
//		ImageIO.write(bufferImg, "png", byteArrayOut); 
//		HSSFWorkbook work_book = new HSSFWorkbook();
//		HSSFSheet cost_aggregation = work_book.createSheet("费用汇总");
//		cost_aggregation.setDefaultRowHeightInPoints(20); 
        // 画图的顶级管理器，一个sheet只能获取一个（一定要注意这点）
//		HSSFPatriarch patriarch = cost_aggregation.createDrawingPatriarch();
        // anchor主要用于设置图片的属性  
//		HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 0, 0,(short) 0, 0, (short) 1, 2);
//		anchor.setAnchorType(3);
        // 插入图片    
//		patriarch.createPicture(anchor, work_book.addPicture(byteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG));
        //--------------------------------------------
        //Style----start
        //标题样式
		cost_aggregation.setColumnWidth(0, 5000);
		cost_aggregation.setColumnWidth(1, 4000);
		cost_aggregation.setColumnWidth(2, 5000);
		cost_aggregation.setColumnWidth(3, 4000);
		cost_aggregation.setColumnWidth(4, 7000);
		cost_aggregation.setDefaultRowHeightInPoints(20); 
		CellStyle style1= workbook.createCellStyle();
//		style1= workbook.createCellStyle();
		style1.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		style1.getFillPatternEnum();
		style1.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		style1.setBorderTop(BorderStyle.THIN);
		style1.setBorderBottom(BorderStyle.THIN);
		style1.setBorderLeft(BorderStyle.THIN);
		style1.setBorderRight(BorderStyle.THIN);
		style1.setAlignment(HorizontalAlignment.CENTER);
		
		Font fs = workbook.createFont(); //创建字体样式 
		fs.setFontName("Calibri");  //设置字体类型
		fs.setFontHeightInPoints((short)11);    //设置字体大小  
		style1.setFont(fs);//应用字体样式
		style1.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		// 内容无多余样式，只有黑色边框
		CellStyle style2= workbook.createCellStyle();
		style2= workbook.createCellStyle();
		style2.setBorderTop(BorderStyle.THIN);
		style2.setBorderBottom(BorderStyle.THIN);
		style2.setBorderLeft(BorderStyle.THIN);
		style2.setBorderRight(BorderStyle.THIN);
		// 左对齐
		CellStyle style3= workbook.createCellStyle();
		style3.setAlignment(HorizontalAlignment.RIGHT);
		// 居中对齐标题
		CellStyle style4= workbook.createCellStyle();
		style4.setAlignment(HorizontalAlignment.CENTER);
		Font fs_1= workbook.createFont();
		fs_1.setFontHeightInPoints((short) 11);
		style4.setBorderTop(BorderStyle.THIN);
		style4.setBorderBottom(BorderStyle.THIN);
		style4.setBorderLeft(BorderStyle.THIN);
		style4.setBorderRight(BorderStyle.THIN);
		style4.setFont(fs_1);
		// 居中对齐正文
		CellStyle style5= workbook.createCellStyle();
		style5.setAlignment(HorizontalAlignment.CENTER);
		Font fs_2= workbook.createFont();
		fs_2.setFontHeightInPoints((short) 9);
		fs_2.setColor(HSSFColor.BLUE.index);
		style5.setBorderBottom(BorderStyle.THIN);
		style5.setBorderLeft(BorderStyle.THIN);
		style5.setBorderRight(BorderStyle.THIN);
		style5.setBorderTop(BorderStyle.THIN);
		style5.setFont(fs_2);
		
		CellStyle style6= workbook.createCellStyle();
		Font fs_3= workbook.createFont();
		fs_3.setFontHeightInPoints((short) 9);
		fs_3.setFontName("微软雅黑");
		style6.setFont(fs_3);
        //Style----end
		Row row0= cost_aggregation.createRow((short) 0);
		Cell cell= row0.createCell((short) 0);
		cell.setCellValue("");
		cell= row0.createCell((short) 1);
		cell.setCellValue("");
		cell= row0.createCell((short) 2);
		cell.setCellValue("");
		cell= row0.createCell((short) 3);
		cell.setCellValue("");
		cell= row0.createCell((short) 4);
		String company= CommonUtils.checkExistOrNot(summary.get("company"))? summary.get("company").toString(): "_________________";
		cell.setCellValue(company);
		cell.setCellStyle(style3);
		Row row1 = cost_aggregation.createRow((short) 1);
		cell= row1.createCell((short) 0);
		cell.setCellValue("");
		cell= row1.createCell((short) 1);
		cell.setCellValue("结 算 单");
		CellStyle style7= workbook.createCellStyle();
		Font f= workbook.createFont();
		f.setFontHeightInPoints((short) 16);
		style7.setFont(f);
		cell.setCellStyle(style7);
		cell= row1.createCell((short) 2);
		cell.setCellValue("");
		cell= row1.createCell((short) 3);
		cell.setCellValue("");
		cell= row1.createCell((short) 4);
		cell.setCellValue("");
		Row row2= cost_aggregation.createRow((short) 2);
		cell= row2.createCell((short) 0);
		String settlement_period= CommonUtils.checkExistOrNot(summary.get("settlement_period"))? summary.get("settlement_period").toString(): "____________";
		cell.setCellValue("结算周期：");
		cell= row2.createCell((short) 1);
		cell.setCellValue(settlement_period);
		cell= row2.createCell((short) 2);
		cell.setCellValue("");
		cell= row2.createCell((short) 3);
		cell.setCellValue("");
		cell= row2.createCell((short) 4);
		cell.setCellValue("");
		Row row3= cost_aggregation.createRow((short) 3);
		cell= row3.createCell((short) 0);
		cell.setCellValue("经销单位：");
		cell= row3.createCell((short) 1);
		String distribution_unit= CommonUtils.checkExistOrNot(summary.get("distribution_unit"))? summary.get("distribution_unit").toString(): "____________";
		cell.setCellValue(distribution_unit);
		cell= row3.createCell((short) 2);
		cell.setCellValue("");
		cell= row3.createCell((short) 3);
		cell.setCellValue("");
		cell= row3.createCell((short) 4);
		String client_name= CommonUtils.checkExistOrNot(summary.get("client_name"))? summary.get("client_name").toString(): "____________";
		cell.setCellValue("品牌："+client_name);
		cell.setCellStyle(style3);
		Row row4= cost_aggregation.createRow((short) 4);
		cell= row4.createCell((short) 0);
		cell.setCellValue("店铺：");
		cell= row4.createCell((short) 1);
		String store_name= CommonUtils.checkExistOrNot(summary.get("store_name"))? summary.get("store_name").toString(): "____________";
		cell.setCellValue(store_name);
		cell= row4.createCell((short) 2);
		cell.setCellValue("");
		cell= row4.createCell((short) 3);
		cell.setCellValue("");
		cell= row4.createCell((short) 4);
		String unit= CommonUtils.checkExistOrNot(summary.get("unit"))? summary.get("unit").toString(): "";
		cell.setCellValue("单位：" + unit);
		cell.setCellStyle(style3);
		Row row5= cost_aggregation.createRow((short) 5);
		row5.setHeight((short) (19 * 20));
		cell= row5.createCell((short) 0);
		cell.setCellValue("项目");
		cell.setCellStyle(style1);
		cell= row5.createCell((short) 1);
		cell.setCellValue("数量");
		cell.setCellStyle(style1);
		cell= row5.createCell((short) 2);
		cell.setCellValue("");
		cell.setCellStyle(style1);
		cell= row5.createCell((short) 3);
		cell.setCellValue("费用");
		cell.setCellStyle(style1);
		cell= row5.createCell((short) 4);
		cell.setCellValue("备注");
		cell.setCellStyle(style1);
		int row_num= 6;
		List<Map<String, Object>> max_lists= (List<Map<String, Object>>)summary.get("max_lists");
		for(int j= 0; j< max_lists.size(); j++) {
			Row row6= cost_aggregation.createRow((short) row_num);
			row_num= row_num+1;
			row6.setHeight((short) (20 * 20));
			cell= row6.createCell((short) 0);
			cell.setCellValue("");
			cell.setCellStyle(style1);
			cell= row6.createCell((short) 1);
			cell.setCellValue("");
			cell.setCellStyle(style1);
			cell= row6.createCell((short) 2);
			cell.setCellValue("");
			cell.setCellStyle(style1);
			cell= row6.createCell((short) 3);
			cell.setCellValue("");
			cell.setCellStyle(style1);
			cell= row6.createCell((short) 4);
			cell.setCellValue("");
			cell.setCellStyle(style1);
			Map<String, Object> max_list= max_lists.get(j);
    		Row max_row= cost_aggregation.createRow((short) row_num);
    		row_num= row_num+ 1;
    		max_row.setHeight((short) (19 * 20));
    		cell= max_row.createCell((short) 0);
    		cell.setCellValue(max_list.get("text1")+ "小计");
    		cell.setCellStyle(style4);
    		cell= max_row.createCell((short) 1);
    		try {
				cell.setCellValue(Double.parseDouble(max_list.get("text2").toString()));
				cell.setCellType(Cell.CELL_TYPE_NUMERIC);
    		} catch (NumberFormatException | NullPointerException e){
				cell.setCellValue(max_list.get("text2") + "");
    		}
    		cell.setCellStyle(style4);
    		cell= max_row.createCell((short) 2);
    		cell.setCellValue(max_list.get("text3")+ "");
    		cell.setCellStyle(style4);
    		cell= max_row.createCell((short) 3);
    		try {
				cell.setCellValue(Double.parseDouble(max_list.get("text4").toString()));
				cell.setCellType(Cell.CELL_TYPE_NUMERIC);
    		} catch (NumberFormatException | NullPointerException e){
				cell.setCellValue(max_list.get("text4") + "");
    		}
    		cell.setCellStyle(style4);
    		cell= max_row.createCell((short) 4);
    		cell.setCellValue(max_list.get("text5")+ "");
    		cell.setCellStyle(style4);
    		if(CommonUtils.checkExistOrNot(max_list.get("text6"))) {
				List<Map<String, Object>> small_list= (List<Map<String, Object>>) max_list.get("text6");
				for (int i = 0; i < small_list.size(); i++) {
					Row small_row = cost_aggregation.createRow((short) row_num);
					row_num = row_num+1;
					small_row.setHeight((short) (19 * 20));
					cell= small_row.createCell((short) 0);
					cell.setCellValue(small_list.get(i).get("text1")+ "");
					cell.setCellStyle(style5);
					cell= small_row.createCell((short) 1);
		    		try {
						cell.setCellValue(Double.parseDouble(small_list.get(i).get("text2").toString()));
						cell.setCellType(Cell.CELL_TYPE_NUMERIC);
		    		} catch (NumberFormatException | NullPointerException e){
						cell.setCellValue(small_list.get(i).get("text2") + "");
		    		}
					cell.setCellStyle(style5);
					cell= small_row.createCell((short) 2);
					cell.setCellValue(small_list.get(i).get("text3")+ "");
					cell.setCellStyle(style5);
					cell= small_row.createCell((short) 3);
		    		try {
						cell.setCellValue(Double.parseDouble(small_list.get(i).get("text4").toString()));
						cell.setCellType(Cell.CELL_TYPE_NUMERIC);
		    		} catch (NumberFormatException | NullPointerException e){
						cell.setCellValue(small_list.get(i).get("text4") + "");
		    		}
					cell.setCellStyle(style5);
					cell= small_row.createCell((short) 4);
					cell.setCellValue(small_list.get(i).get("text5")+ "");
					cell.setCellStyle(style5);
					
				}
			
    		}
    		
		}
		return workbook;
		
	}
	
}
