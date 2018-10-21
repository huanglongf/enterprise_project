package com.lmis.pos.whs.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lmis.common.dynamicSql.service.DynamicSqlServiceInterface;
import com.lmis.framework.baseInfo.LmisConstant;
import com.lmis.framework.baseInfo.LmisPageObject;
import com.lmis.framework.baseInfo.LmisResult;
import com.lmis.pos.whs.dao.PosWhsScheduleMapper;
import com.lmis.pos.whs.model.PosWhs;
import com.lmis.pos.whs.model.PosWhsSchedule;
import com.lmis.pos.whs.service.PosWhsScheduleServiceInterface;

/**
 * @ClassName: PosWhsServiceImpl
 * @Description: TODO(仓库主表业务层实现类)
 * @author codeGenerator
 * @date 2018-05-29 10:13:18
 * 
 * @param <T>
 */
@Service
public class PosWhsScheduleServiceImpl<T extends PosWhsSchedule> implements PosWhsScheduleServiceInterface<T> {
	private static Log logger = LogFactory.getLog(PosWhsScheduleServiceImpl.class);
	@Resource(name = "dynamicSqlServiceImpl")
	DynamicSqlServiceInterface<PosWhs> dynamicSqlService;

	@Autowired
	private PosWhsScheduleMapper<T> scheduleMapper;

	@Override
	public LmisResult<?> executeSelect(T t, LmisPageObject pageObject) throws Exception {
		List<T> list = scheduleMapper.query(t);
		LmisResult<Map<String, Object>> lmisResult = new LmisResult<Map<String, Object>>();
		lmisResult.setData(list);
		lmisResult.setCode(LmisConstant.RESULT_CODE_SUCCESS);
		return lmisResult;
	}

	@Override
	public LmisResult<?> excelUploadLog(List<Map<String, Object>> list, String batchId) throws Exception {
		if(list == null || list.size() <= 0){
			LmisResult<?> result = new LmisResult<T> (LmisConstant.RESULT_CODE_ERROR, "数据为空，请重新上传");
			return result;
		}
		// 保存到日志表
		int saveNum = scheduleMapper.saveScheduleLog(list);
		if(saveNum > 0){
			int wrongNum = scheduleMapper.selectByBatchId(batchId);
			logger.info("批次号：" + batchId + ", 错误数：" + wrongNum);
			if(wrongNum > 0){
				LmisResult<?> result1 = new LmisResult<T> (LmisConstant.RESULT_CODE_SUCCESS, "");
				result1.setData(batchId);
				return result1;
			}
			// 覆盖重复数据
			for(Map<String, Object> map : list){
				scheduleMapper.updateRepeatSts(map);
			}
			// 保存到正式表
			scheduleMapper.saveSchedule(list);
			LmisResult<?> result2 = new LmisResult<T> (LmisConstant.RESULT_CODE_SUCCESS, "");
			return result2;
		}else{
			LmisResult<?> result3 = new LmisResult<T> (LmisConstant.RESULT_CODE_ERROR, "保存到日志表失败");
			return result3;
		}
		
	}

	@Override
	public Boolean executeDelete(T t) throws Exception {
		int i = scheduleMapper.deleteSchedule(t);
		if (i == 1) {
			return true;
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public LmisResult<?> excelDownLoad(T t) throws Exception {
		System.out.println("开始日期" + t.getBeginDate());
		System.out.println("结束日期" + t.getEndDate());
		Map<String, String> titleMap = new LinkedHashMap<String, String>();
		titleMap.put("whsCode", "仓库代码");
		int disDay = (int) getDisDay(getDate(t.getBeginDate()), getDate(t.getEndDate()));
		for(int i=0; i<=disDay; i++){
			Date beginDate = getDate(t.getBeginDate());
			titleMap.put(getDateStr(addDate(beginDate, i)), getDateStr(addDate(beginDate, i)));
		}
		
		List<PosWhsSchedule> scheduleList = (List<PosWhsSchedule>) scheduleMapper.query(t);
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
//		Map<String, Object> dataMap = new HashMap<String, Object>();
//		dataMap.put("whsCode", "001");
//		dataMap.put("2018-05-01", "100");
//		dataMap.put("2018-05-02", "200");
//		dataMap.put("2018-05-03", "300");
//		dataMap.put("2018-05-04", "400");
//		dataMap.put("2018-05-05", "500");
//		dataMap.put("2018-05-06", "600");
//		dataList.add(dataMap);
		
		
		for(PosWhsSchedule schedule : scheduleList){
			Map<String, Object> dataMap = new HashMap<String, Object>();
			if(dataMap.get("whsCode").equals(schedule.getWhsCode())){
				
			}
		}
		
		SimpleDateFormat tempDate = new SimpleDateFormat("yyyyMMddHHmmss");    
		String datetime = tempDate.format(new java.util.Date());
		excelDownLoadDatab(dataList,(LinkedHashMap<String, String>) titleMap, "仓库排班"+datetime);
//		for (PosWhsSchedule schedule : list) {
//			Map<String, Object> newMap = new HashMap<String, Object>();
//			for(Map<String, Object> map : dataList){
//				if(map.get(schedule.getWhsCode()) == null){
//					
//				}
//			}
////			System.out.println(schedule.getScheduleDate());
////			if(dataMap.get(schedule.getWhsCode()) == null){
////				Map<String, Object> newMap = new HashMap<String, Object>();
////				newMap.put("whsCode", schedule.getWhsCode());
////				newMap.put(schedule.getScheduleDate(), schedule.getScheduleDate());
////				dataList.add(newMap);
////			}else{
////				dataMap.put(key, value);
////			}
//		}
		return new LmisResult<T> (LmisConstant.RESULT_CODE_SUCCESS, "");
	}
	
	/**
	 * @Title: excelDownLoadDatab @Description: TODO(大数据量导出) @param @param result
	 * 内容集合 @param @param cMap 表头 @param @param sheetName
	 * 文件名 @param @return @param @throws IOException 设定文件 @return File 返回类型 @throws
	 */
	@SuppressWarnings("unchecked")
	public static File excelDownLoadDatab(List<?> result, LinkedHashMap<String, String> cMap, String sheetName)
			throws IOException {
		String fileName = "D:\\" + sheetName + ".xlsx";
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

	public static void main(String[] args) throws Exception {
		Date d1 = getDate("2018-05-01");
		Date d2 = getDate("2018-05-10");
		long dd = getDisDay(d1, d2);
		System.out.println("相差" + dd + "天");
		System.out.println("4天后是："+getDateStr(addDate(d1, 4)));
	}

	private static Date addDate(Date date, int d){
		Calendar calendar = new GregorianCalendar(); 
		calendar.setTime(date); 
		calendar.add(Calendar.DATE,d);
		date=calendar.getTime(); 
		return date;
	}
	private static String getDateStr(Date date) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(date);
	}
	
	private static Date getDate(String str) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = sdf.parse(str);
		return date;
	}

	public static long getDisDay(Date startDate, Date endDate) {
		long[] dis = getDisTime(startDate, endDate);
		long day = dis[0];
		if (dis[1] > 0 || dis[2] > 0 || dis[3] > 0) {
			day += 1;
		}
		return day;
	}

	public static long[] getDisTime(Date startDate, Date endDate) {
		long timesDis = Math.abs(startDate.getTime() - endDate.getTime());
		long day = timesDis / (1000 * 60 * 60 * 24);
		long hour = timesDis / (1000 * 60 * 60) - day * 24;
		long min = timesDis / (1000 * 60) - day * 24 * 60 - hour * 60;
		long sec = timesDis / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60;
		return new long[] { day, hour, min, sec };
	}

}
