package com.lmis.pos.whs.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.util.StringUtil;
import com.lmis.common.dataFormat.GetUuidForJava;
import com.lmis.framework.baseInfo.LmisPageObject;
import com.lmis.pos.common.util.ZipUtil;
import com.lmis.pos.whs.model.PosWhsSchedule;
import com.lmis.pos.whs.service.impl.PosWhsScheduleServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/** 
 * @ClassName: PosWhsController
 * @Description: TODO(仓库主表控制层类)
 * @author codeGenerator
 * @date 2018-05-29 10:13:18
 * 
 */
@Api(value = "仓库排班表")
@RestController
@RequestMapping(value="/schedule")
public class PosWhsScheduleController {
	
	private static Log logger = LogFactory.getLog(PosWhsScheduleController.class);
	
	public final static ThreadLocal<SimpleDateFormat> format = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    }; 
    
    public final static ThreadLocal<DecimalFormat> decimalFormat = new ThreadLocal<DecimalFormat>() {
        @Override
        protected DecimalFormat initialValue() {
        	DecimalFormat d = new DecimalFormat("##########.##");
        	d.setRoundingMode(RoundingMode.HALF_UP);
            return d;
        }
    }; 

	@Value("${base.page.pageNum}")
    int defPageNum;
	
	@Value("${base.page.pageSize}")
    int defPageSize;
	
	@Value("${lmis_pos.podata.excel_import_file_upload_path}")
	String upZipFilePath;
	
	@Resource
	PosWhsScheduleServiceImpl<PosWhsSchedule> scheduleService;
	
	@ApiOperation(value="查询仓库排班表")
	@RequestMapping(value = "/query", method = RequestMethod.POST)
    public String selectPosWhs(@ModelAttribute PosWhsSchedule schedule,
    		@ModelAttribute LmisPageObject pageObject) throws Exception {
		logger.info("..............查询仓库排班表..............");
		//setDate(schedule);
        return JSON.toJSONString(scheduleService.executeSelect(schedule, pageObject));
    }
	
	@ApiOperation(value="删除仓库排班表")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String deletePosWhs(@ModelAttribute PosWhsSchedule posWhs) throws Exception {
		posWhs.setId("1");
		return JSON.toJSONString(scheduleService.executeDelete(posWhs));
    }
	
	@ApiOperation(value="先导入日志表，再导入正式表")
	@RequestMapping(value = "/uploadLog", method = RequestMethod.POST)
    public String uploadScheduleLog(@RequestParam @ApiParam(value="仓库代码",required=false) String whsCode,
    		@RequestParam @ApiParam(value="上传文件",required=false) MultipartFile file
    		) throws Exception {
		logger.info("解压文件后存放路径：" + upZipFilePath);
		
		unZip(file.getInputStream(), upZipFilePath + file.getName() + "zip");
		String fileName = ZipUtil.unzipStr(upZipFilePath + file.getName() + "zip", upZipFilePath);
		logger.info("解压后文件名称：" + fileName);
		String batchId = GetUuidForJava.getUuidForJava();
		List<Map<String, Object>> excelList = readExcel_(whsCode, upZipFilePath + fileName,batchId);
		return JSON.toJSONString(scheduleService.excelUploadLog(excelList, batchId));
    }

	@ApiOperation(value="导出文件")
	@RequestMapping(value = "/download", method = RequestMethod.POST)
    public String downloadSchedule(@ModelAttribute PosWhsSchedule schedule) throws Exception {
		logger.info("..............导出文件仓库排班表..............");
		schedule.setBeginDate("2018-06-01");
		schedule.setEndDate("2018-06-10");
        return JSON.toJSONString(scheduleService.excelDownLoad(schedule));
    }
	
	private static PosWhsSchedule setDate(PosWhsSchedule schedule){
		if(StringUtil.isEmpty(schedule.getEndDate())){
			schedule.setEndDate(getLastDay());
		}
		return schedule;
	}
	
	/**
	 * 
	 * @param is
	 * @param destFilePath 目标文件夹
	 * @throws IOException
	 */
	private void unZip(InputStream is, String destFilePath) throws IOException {
		BufferedInputStream bis = new BufferedInputStream(is);
		File file = new File(destFilePath);
		File parent = file.getParentFile();
		if(parent != null && (!parent.exists())){
			parent.mkdirs();
		}
		FileOutputStream fos = new FileOutputStream(file);
		BufferedOutputStream bos = new BufferedOutputStream(fos, 1024);
		byte[]	buf = new byte[1024];
		int len = 0;
		while((len = bis.read(buf,0,1024)) != -1){
			fos.write(buf, 0, len);
		}
		bos.flush();
		bos.close();
		bis.close();
	}
	
	private List<Map<String, Object>> readExcel_(String whsCode, String filePath, String batchId) throws Exception{
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();  
		File file  = new File(filePath);
		XSSFWorkbook  workbook = new XSSFWorkbook(new FileInputStream(file));
		if(workbook != null){  
            for(int sheetNum = 0;sheetNum < workbook.getNumberOfSheets();sheetNum++){  
                //获得当前sheet工作表  
                Sheet sheet = workbook.getSheetAt(sheetNum);  
                if(sheet == null){  
                    break;  
                }  
                //获得当前sheet的开始行  
                int firstRowNum  = sheet.getFirstRowNum();  
                //获得当前sheet的结束行  
                int lastRowNum = sheet.getLastRowNum();  
                //获得当前行的开始列  
                int firstCellNum = 0;  
                //获得当前行的列数  
                int lastCellNum = 0;  
                //循环除了第一行的所有行  
                for(int rowNum = firstRowNum;rowNum <= lastRowNum;rowNum++){  

                    //获得当前行  
                    Row row = sheet.getRow(rowNum);  
                    if(row == null){  
                        break;  
                    }  
                    
                	if(rowNum == firstRowNum){
                        //获得当前行的开始列  
                        firstCellNum = row.getFirstCellNum();  
                        //获得当前行的列数  
                        lastCellNum = row.getPhysicalNumberOfCells();  
                	} else {
	                    //循环当前行  
	                    Map<String, Object> map = new HashMap<String, Object>();
	                	map.put("whsCode", whsCode);
	                	map.put("batchId", batchId);
	                    for(int cellNum = firstCellNum; cellNum < lastCellNum;cellNum++){  
	                        Cell cell = row.getCell(cellNum);  
	                        if(cellNum == 0){
	                        	String scheduleDate = getCellValue(cell).trim();
	                        	if(StringUtil.isEmpty(scheduleDate)){
	                        		map.put("wrongDesc", "日期格式不正确，正确格式为：yyyy-MM-dd,");
	                        		map.put("wrongFlag", "1");
	                        		map.put("scheduleDate", "###");
	                        	}else if(!matchDate(scheduleDate)){
	                        		map.put("wrongDesc", "日期格式不正确，正确格式为：yyyy-MM-dd,");
	                        		map.put("wrongFlag", "1");
	                        	}else{
	                        		map.put("wrongDesc", "");
	                        		map.put("wrongFlag", "0");
	                        	}
	                        	map.put("scheduleDate", (map.get("scheduleDate") == null ? "" : map.get("scheduleDate")) +scheduleDate);
	                        	map.put("isValidate", "1");
	                        }else if(cellNum == 1){
	                        	String inJobsMax = getCellValue(cell).trim();
	                        	if(StringUtil.isEmpty(inJobsMax)){
	                        		map.put("wrongDesc", map.get("wrongDesc") + "最大入库作业数(件)仅支持正整数");
	                        		map.put("wrongFlag", "1");
	                        		map.put("inJobsMax", "###");
	                        	}else if(Integer.valueOf(inJobsMax)<=0){
	                        		map.put("wrongDesc", map.get("wrongDesc") + "最大入库作业数(件)仅支持正整数");
	                        		map.put("wrongFlag", "1");
	                        	}else{
	                        		map.put("wrongDesc", map.get("wrongDesc"));
	                        		map.put("wrongFlag", map.get("wrongFlag"));
	                        	}
	                        	map.put("inJobsMax", (map.get("inJobsMax") == null ? "" : map.get("inJobsMax"))+inJobsMax);
	                        	map.put("isValidate", "1");
	                        }
	                    }
	                    list.add(map);
                	}
                }  
            }  
            workbook.close();  
        }
		return list;
	}
	
	@SuppressWarnings("unused")
	private static String getFirstDay(){
		Calendar cale = null;  
        cale = Calendar.getInstance(); 
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");  
        // 获取前月的第一天  
        cale = Calendar.getInstance();  
        cale.add(Calendar.MONTH, 0);  
        cale.set(Calendar.DAY_OF_MONTH, 1);  
        return format.format(cale.getTime());
	}
	
	private static String getLastDay(){
		Calendar cale = null;  
        cale = Calendar.getInstance(); 
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");  
        // 获取前月的第一天  
        cale = Calendar.getInstance();  
        cale.add(Calendar.MONTH, 1);  
        cale.set(Calendar.DAY_OF_MONTH, 0);  
        return format.format(cale.getTime());
	}
	
	private static boolean matchDate(String str){
		if(str.length() < 8 || str.length() > 10)
			return false;
		return Pattern.compile("(\\d){4}-(\\d){1,2}-(\\d){1,2}").matcher(str).find();
	}
	
	@SuppressWarnings("deprecation")
	public static String getCellValue(Cell cell){  
        String cellValue = "";  
        if(cell == null){  
            return cellValue;  
        }  
        //把数字当成String来读，避免出现1读成1.0的情况  
//        if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){  
//            cell.setCellType(Cell.CELL_TYPE_STRING);  
//        }  
        //判断数据的类型  
        switch (cell.getCellType()){  
            case Cell.CELL_TYPE_NUMERIC: //数字  
            	if(HSSFDateUtil.isCellDateFormatted(cell)) {
            		cellValue = format.get().format(cell.getDateCellValue());
            	} else {
                    cellValue = decimalFormat.get().format(cell.getNumericCellValue());
//            		cellValue = String.valueOf(cell.getNumericCellValue());  
            	}
                break;  
            case Cell.CELL_TYPE_STRING: //字符串  
                cellValue = String.valueOf(cell.getStringCellValue());  
                break;  
            case Cell.CELL_TYPE_BOOLEAN: //Boolean  
                cellValue = String.valueOf(cell.getBooleanCellValue());  
                break;  
            case Cell.CELL_TYPE_FORMULA: //公式  
                cellValue = String.valueOf(cell.getCellFormula());  
                break;  
            case Cell.CELL_TYPE_BLANK: //空值   
                cellValue = "";  
                break;  
            case Cell.CELL_TYPE_ERROR: //故障  
                cellValue = "非法字符";  
                break;  
            default:  
                cellValue = "未知类型";  
                break;  
        }  
        return cellValue;  
    }
	
}
