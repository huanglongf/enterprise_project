package com.bt.lmis.service.impl;

import java.io.FileInputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.alibaba.fastjson.JSONObject;
import com.bt.lmis.base.spring.SpringUtils;
import com.bt.lmis.code.ServiceSupport;
import com.bt.lmis.dao.ExpressRawDataMapper;
import com.bt.lmis.dao.JkSettleMapper;
import com.bt.lmis.dao.LogisticsStandardDataMapper;
import com.bt.lmis.model.ExcelPackageObjects;
import com.bt.lmis.model.ExpressRawData;
import com.bt.lmis.model.LogisticsStandardData;
import com.bt.lmis.model.RawDataResult;
import com.bt.lmis.service.RawDataService;
import com.bt.utils.CommonUtils;
import com.bt.utils.ExcelUtils;
import com.bt.utils.SessionUtils;
import com.csvreader.CsvReader;
@Service
public class RawDataServiceImpl<T> extends ServiceSupport<T> implements RawDataService<T> {

	@Autowired
	private JkSettleMapper<T> jkSettleMapper;
	@Autowired
	private LogisticsStandardDataMapper<T> logisticsStandardDataMapper;
	@Autowired
	private ExpressRawDataMapper<T> expressRawDataMapper;
	
	public JkSettleMapper<T> getJkSettleMapper(){
		return jkSettleMapper;
	}
	public LogisticsStandardDataMapper<T> getLogisticsStandardDataMapper(){
		return logisticsStandardDataMapper;
	}
	public ExpressRawDataMapper<T> getExpressRawDataMapper(){
		return expressRawDataMapper;
	}
	
	@Override
	public JSONObject invoke(HttpServletRequest request, JSONObject result) throws Exception {
		result = new JSONObject();
		// 调用存储过程参数
		Map<String, String> param = new HashMap<String, String>();
		// 获取模板
		List<Map<String, String>> list = getTemplet();
		Map<String, String> obj = null;
		for(int i = 0; i < list.size(); i++) {
			obj = list.get(i);
			if(obj.get("name").equals(request.getParameter("type"))){
				param.put("class_name", obj.get("code"));
			}
		}
		// 调用存储过程插入正式表
		param.put("bat_id", request.getParameter("batId"));
		param.put("out_result", "");
		param.put("out_result_reason", "");
		jkSettleMapper.jk_settle_pro(param);
		result.put("result_content", param.get("out_result_reason"));
		return result;
	}
	
	@Override
	public JSONObject del(HttpServletRequest request, JSONObject result) throws Exception {
		result = new JSONObject();
		// 获取模板
		List<Map<String, String>> list = getTemplet();
		Map<String, String> obj = null;
		Object mapper = null;
		boolean flag = false;
		for(int i = 0; i < list.size(); i++) {
			obj = list.get(i);
			if(obj.get("name").equals(request.getParameter("type"))){
				mapper = SpringUtils.getBean(obj.get("code") + "Mapper");
				if((Integer)mapper.getClass().getMethod("del", Integer.class).invoke(mapper, Integer.parseInt(request.getParameter("batId"))) > 0){
					// 删除成功
					flag = true;
				}
				break;
			}
		}
		if(flag){
			result.put("result_code", "SUCCESS");
			result.put("result_content", "删除成功！");
		} else {
			result.put("result_code", "FAILURE");
			result.put("result_content", "删除失败或记录不存在！");
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ModelMap searchRawDataByBatId(ModelMap map, HttpServletRequest request) throws Exception {
		// 获取模板
		List<Map<String, String>> list = getTemplet();
		//　查询条件
		Integer batId = null;
		if(CommonUtils.checkExistOrNot(request.getParameter("batId"))){
			batId = Integer.parseInt(request.getParameter("batId"));
		}
		String type = null;
		Object mapper = null;
		List<Integer> batIds = null;
		List<RawDataResult> rDRs = null;
		RawDataResult rDR = null;
		Map<String, String> obj = null;
		List<Map<String, String>> batIdData = null;
		Map<String, String> data = null;
		Integer batIdParam = null;
		String dataMsg = "";
		Method method = null;
		if(CommonUtils.checkExistOrNot(request.getParameter("type"))){
			type = request.getParameter("type");
			for(int i = 0; i < list.size(); i++) {
				obj = list.get(i);
				if(obj.get("name").indexOf(type) >= 0){
					mapper = SpringUtils.getBean(obj.get("code") + "Mapper");
					batIds = (List<Integer>)mapper.getClass().getMethod("getBatIds", Integer.class).invoke(mapper, batId);
					if(CommonUtils.checkExistOrNot(batIds)){
						if(!CommonUtils.checkExistOrNot(rDRs)){
							rDRs = new ArrayList<RawDataResult>();
						}
						rDR = new RawDataResult();
						rDR.setType(obj.get("name"));
						for(int j = 0; j < batIds.size(); j++){
							batIdParam = batIds.get(j);
							if(j == 0){
								batIdData = new ArrayList<Map<String, String>>();
							}
							data = new HashMap<String, String>();
							data.put("batId", batIdParam + "");
							method = mapper.getClass().getMethod("countData", new Class[]{Integer.class, String.class});
							//　所有数据
							dataMsg = "数据量：共" + (Integer)method.invoke(mapper, batId, null) + "条记录；";
							// 未处理
							dataMsg += "未处理：" + (Integer)method.invoke(mapper, batId, "0") + "；";
							// 处理成功
							dataMsg += "处理成功：" + (Integer)method.invoke(mapper, batId, "1") + "；";
							// 处理失败
							dataMsg += "处理失败：" + (Integer)method.invoke(mapper, batId, "2") + "；";
							data.put("msg", dataMsg);
							batIdData.add(data);
						}
						rDR.setBatIdData(batIdData);
						rDRs.add(rDR);
					}
				}
			}
		} else {
			for(int i = 0; i < list.size(); i++) {
				obj = list.get(i);
				mapper = SpringUtils.getBean(obj.get("code") + "Mapper");
				batIds = (List<Integer>)mapper.getClass().getMethod("getBatIds", Integer.class).invoke(mapper, batId);
				if(CommonUtils.checkExistOrNot(batIds)){
					if(!CommonUtils.checkExistOrNot(rDRs)){
						rDRs = new ArrayList<RawDataResult>();
					}
					rDR = new RawDataResult();
					rDR.setType(obj.get("name"));
					for(int j = 0; j < batIds.size(); j++){
						batIdParam = batIds.get(j);
						if(j == 0){
							batIdData = new ArrayList<Map<String, String>>();
						}
						data = new HashMap<String, String>();
						data.put("batId", batIdParam + "");
						method = mapper.getClass().getMethod("countData", new Class[]{Integer.class, String.class});
						//　所有数据
						dataMsg = "数据量：共" + (Integer)method.invoke(mapper, batIdParam, null) + "条记录；";
						// 未处理
						dataMsg += "未处理：" + (Integer)method.invoke(mapper, batIdParam, "0") + "；";
						// 处理成功
						dataMsg += "处理成功：" + (Integer)method.invoke(mapper, batIdParam, "1") + "；";
						// 处理失败
						dataMsg += "处理失败：" + (Integer)method.invoke(mapper, batIdParam, "2") + "；";
						data.put("msg", dataMsg);
						batIdData.add(data);
					}
					rDR.setBatIdData(batIdData);
					rDRs.add(rDR);
				}
			}
		}
		map.addAttribute("result", rDRs);
		if(CommonUtils.checkExistOrNot(type)){
			map.addAttribute("type", type);
		}
		if(CommonUtils.checkExistOrNot(batId)){
			map.addAttribute("batId", batId);
		}
		return map;
	}
	
	@Override
	public List<Map<String, String>> getTemplet() {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Map<String, String> obj = null;
		for(int i = 0; i < 6; i++){
			obj = new HashMap<String, String>();
			switch (i) {
			case 0:
				obj.put("code", "order");
				obj.put("name", "运单");
				break;
			case 1:
				obj.put("code", "orderDetail");
				obj.put("name", "运单明细");
				break;
			case 2:
				obj.put("code", "storage");
				obj.put("name", "仓储费");
				break;
			case 3:
				obj.put("code", "operator");
				obj.put("name", "操作费");
				break;
			case 4:
				obj.put("code", "use");
				obj.put("name", "耗材实际使用量");
				break;
			case 5:
				obj.put("code", "invitation");
				obj.put("name", "耗材采购量");
				break;
			}
			list.add(obj);
		}
		return list;
	}
	
	@Override
	public JSONObject saveRawData(JSONObject result, Object mapper, List<Object> results, String className) throws Exception {
		// 生成当前类
		Class<?> clazz = Class.forName(className);
		for(int i = 0; i < results.size(); i++) {
			try{
				// class.cast(object)强转类型
				mapper.getClass().getMethod("insertRawData", clazz).invoke(mapper, clazz.cast(results.get(i)));
			} catch(Exception e) {
				e.printStackTrace();
				result.put("result_code", "ULF");
				if(e instanceof InvocationTargetException) {
					String errorStr1 = "第" + (i + 1) + "行数据出现字段";
					String errorStr2 = "类型异常！";
					if(e instanceof DataIntegrityViolationException) {
						errorStr1 = errorStr1 + "日期" + errorStr2;
					} else {
						errorStr1 = errorStr1 + errorStr2;
					}
					result.put("result_content", errorStr1);
				}
				return result;
			}
		}
		result.put("result_code", "SUCCESS");
		result.put("success_records", results.size());
		return result;
	}
	
	@Override
	public JSONObject saveData(JSONObject result, String filePath, String className) throws Exception {
		// 获取批次号
		Object mapper = SpringUtils.getBean(CommonUtils.shiftFirstLetter(className, 1) + "Mapper");
		Integer batId = (Integer)mapper.getClass().getMethod("getBatId").invoke(mapper);
		if(CommonUtils.checkExistOrNot(batId)){
			batId++;
		} else {
			batId = 1;
		}
		// 不一样的表封装不一样的类
		className = "com.bt.lmis.model." + className;
		// 判断上传文件类型
		ExcelPackageObjects objects = null;
		switch(filePath.substring(filePath.lastIndexOf(".") + 1)){
		case "csv":
			// 文件上载后转为CsvReader对象
			CsvReader csvReader = new CsvReader(filePath, '	', Charset.forName("GBK"));
			// 设置SafetySwitch为false
			csvReader.setSafetySwitch(false);
			// 封装数据
			objects = ExcelUtils.packageCsvToObjects(batId, csvReader, className);
			csvReader.close();
		break;
		case "xls":;
		case "xlsx":
			// 文件上载后转为Workbook对象
			Workbook workbook= WorkbookFactory.create(new FileInputStream(filePath));
			// 封装数据
			objects = ExcelUtils.packageExcelToObjects(batId, workbook, className);
		break;
		}
		// 
		if(CommonUtils.checkExistOrNot(objects) && objects.getResult_flag()){
			// 封装成功
			// 保存
			result = saveRawData(result, mapper, objects.getResult(), className);
		} else {
			// 封装失败
			result.put("result_code", "ULF");
			result.put("result_content", objects.getReason());
		}
		return result;
	}
	
	@Override
	public void saveLogisticsRawData(List<LogisticsStandardData> logisticsStandardDatas) throws Exception {
		// 循环插入表
		for(int i = 0; i < logisticsStandardDatas.size(); i++) {
			logisticsStandardDataMapper.insertRawData(logisticsStandardDatas.get(i));
		}
	}
	
	@Override
	public JSONObject uploadLogisticsWaybillExcel(HttpServletRequest request, JSONObject result, Workbook workbook)
			throws Exception {
		List<LogisticsStandardData> logisticsStandardDatas = null;
		LogisticsStandardData logisticsStandardData = null;
		Row row = null;
		Cell cell = null;
		for(int i = 0; i < workbook.getNumberOfSheets(); i++){
			// 循环sheet页
			Sheet sheet = workbook.getSheetAt(i);
			// 需要存储数据的sheet页，第一行存在且第一个单元格值为cost_center的sheet页
			if(CommonUtils.checkExistOrNot(sheet.getRow(0)) 
					&& CommonUtils.checkExistOrNot(sheet.getRow(0).getCell(0)) 
					&& ExcelUtils.getCellValue(sheet.getRow(0).getCell(0)).equals("transport_code")){
				// 创建数据集合对象
				logisticsStandardDatas = new ArrayList<LogisticsStandardData>();
				// 循环所有sheet页内所有行
				for(int rowNum = 1; rowNum < sheet.getLastRowNum(); rowNum++){
					// 校验是否运单记录
					if(CommonUtils.checkExistOrNot(sheet.getRow(rowNum).getCell(1))
							|| CommonUtils.checkExistOrNot(sheet.getRow(rowNum).getCell(2))
							|| CommonUtils.checkExistOrNot(sheet.getRow(rowNum).getCell(3))
							|| CommonUtils.checkExistOrNot(sheet.getRow(rowNum).getCell(5))
							|| CommonUtils.checkExistOrNot(sheet.getRow(rowNum).getCell(6))
							|| CommonUtils.checkExistOrNot(sheet.getRow(rowNum).getCell(8))
							|| CommonUtils.checkExistOrNot(sheet.getRow(rowNum).getCell(26))){
						// 创建单行数据对象
						logisticsStandardData = new LogisticsStandardData();
						logisticsStandardData.setCreate_user(SessionUtils.getEMP(request).getId()+"");
						row = sheet.getRow(rowNum);
						if(CommonUtils.checkExistOrNot(row)){
							for(int j = 0; j < 29; j++){
								cell = row.getCell(j);
								// 当指定单元格存在且不为空时
								if(CommonUtils.checkExistOrNot(cell) && !ExcelUtils.getCellValue(cell).equals("")){
									switch(j){
									case 0:
										logisticsStandardData.setTransport_code(ExcelUtils.getCellValue(cell));
									break;
									case 1:
										logisticsStandardData.setTransport_name(ExcelUtils.getCellValue(cell));
									break;
									case 2:
										logisticsStandardData.setTransport_type(ExcelUtils.getCellValue(cell));
									break;
									case 3:
										logisticsStandardData.setTransport_direction(ExcelUtils.getCellValue(cell));
									break;
									case 4:
										logisticsStandardData.setBookbus_time(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(ExcelUtils.getCellValue(cell)));
									break;
									case 5:
										logisticsStandardData.setTransport_time(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(ExcelUtils.getCellValue(cell)));
									break;
									case 6:
										logisticsStandardData.setExpressno(ExcelUtils.getCellValue(cell));
									break;
									case 7:
										logisticsStandardData.setStore_code(ExcelUtils.getCellValue(cell));
									break;
									case 8:
										logisticsStandardData.setStore_name(ExcelUtils.getCellValue(cell));
									break;
									case 9:
										logisticsStandardData.setOrderno(ExcelUtils.getCellValue(cell));
									break;
									case 10:
										logisticsStandardData.setOriginating_place(ExcelUtils.getCellValue(cell));
									break;
									case 11:
										logisticsStandardData.setWarehouse(ExcelUtils.getCellValue(cell));
									break;
									case 12:
										logisticsStandardData.setPrivince_name(ExcelUtils.getCellValue(cell));
									break;
									case 13:
										logisticsStandardData.setCity_name(ExcelUtils.getCellValue(cell));
									break;
									case 14:
										logisticsStandardData.setState_name(ExcelUtils.getCellValue(cell));
									break;
									case 15:
										logisticsStandardData.setStreet_name(ExcelUtils.getCellValue(cell));
									break;
									case 16:
										logisticsStandardData.setDelivery_address(ExcelUtils.getCellValue(cell));
									break;
									case 17:
										logisticsStandardData.setDelivery_number(ExcelUtils.getCellValue(cell));
									break;
									case 18:
										logisticsStandardData.setBox_number(ExcelUtils.getCellValue(cell));
									break;
									case 19:
										logisticsStandardData.setIf_insurance(ExcelUtils.getCellValue(cell));
									break;
									case 20:
										logisticsStandardData.setReal_weight(ExcelUtils.getCellValue(cell));
									break;
									case 21:
										logisticsStandardData.setVolumn_cubic(ExcelUtils.getCellValue(cell));
									break;
									case 22:
										logisticsStandardData.setRemark(ExcelUtils.getCellValue(cell));
									break;
									case 23:
										logisticsStandardData.setSettle_target(ExcelUtils.getCellValue(cell));
									break;
									case 24:
										logisticsStandardData.setOrder_price(ExcelUtils.getCellValue(cell));
									break;
									case 25:
										logisticsStandardData.setService_type(ExcelUtils.getCellValue(cell));
									break;
									case 26:
										logisticsStandardData.setCost_center(ExcelUtils.getCellValue(cell));
									break;
									}
								} else {
									// 当指定单元格不存在或为空串时
									switch(j){
									case 1:;
									case 2:;
									case 3:;
									case 5:;
									case 6:;
									case 8:;
									case 9:;
									case 10:;
									case 11:;
									case 12:;
									case 18:;
									case 19:;
									case 20:;
									case 21:;
									case 23:;
									case 26:
										result.put("result_code", "ULF");
										result.put("result_content", "第" + rowNum + "行：字段" + ExcelUtils.getCellValue(sheet.getRow(0).getCell(j)) + "不能为空！");
										return result;
									}
								}
							}
							logisticsStandardDatas.add(logisticsStandardData);
						}
					}
				}
				saveLogisticsRawData(logisticsStandardDatas);
				result.put("result_code", "SUCCESS");
				result.put("success_records", logisticsStandardDatas.size());
				return result;
			}
		}
		return result;
	}
	
	@Override
	public void saveExpressRawData(List<ExpressRawData> expressRawDatas) throws Exception {
		// 循环插入表
		for(int i = 0; i < expressRawDatas.size(); i++) {
			expressRawDataMapper.insertRawData(expressRawDatas.get(i));
		}
	}
	
	@Override
	public JSONObject uploadExpressWaybillExcel(HttpServletRequest request, JSONObject result, Workbook workbook) throws Exception {
		List<ExpressRawData> expressRawDatas = null;
		ExpressRawData expressRawData = null;
		Row row = null;
		Cell cell = null;
		for(int i = 0; i < workbook.getNumberOfSheets(); i++){
			// 循环sheet页
			Sheet sheet = workbook.getSheetAt(i);
			// 需要存储数据的sheet页，第一行存在且第一个单元格值为cost_center的sheet页
			if(CommonUtils.checkExistOrNot(sheet.getRow(0)) 
					&& CommonUtils.checkExistOrNot(sheet.getRow(0).getCell(0)) 
					&& ExcelUtils.getCellValue(sheet.getRow(0).getCell(0)).equals("cost_center")){
				// 创建数据集合对象
				expressRawDatas = new ArrayList<ExpressRawData>();
				// 循环所有sheet页内所有行
				for(int rowNum = 1; rowNum < sheet.getLastRowNum(); rowNum++){
					// 校验是否运单记录
					if(CommonUtils.checkExistOrNot(sheet.getRow(rowNum).getCell(0))
							|| CommonUtils.checkExistOrNot(sheet.getRow(rowNum).getCell(2))
							|| CommonUtils.checkExistOrNot(sheet.getRow(rowNum).getCell(3))
							|| CommonUtils.checkExistOrNot(sheet.getRow(rowNum).getCell(5))
							|| CommonUtils.checkExistOrNot(sheet.getRow(rowNum).getCell(8))){
						// 创建单行数据对象
						expressRawData = new ExpressRawData();
						expressRawData.setCreate_user(SessionUtils.getEMP(request).getId()+"");
						row = sheet.getRow(rowNum);
						if(CommonUtils.checkExistOrNot(row)){
							for(int j = 0; j < 29; j++){
								cell = row.getCell(j);
								// 当指定单元格存在且不为空时
								if(CommonUtils.checkExistOrNot(cell) && !ExcelUtils.getCellValue(cell).equals("")){
									switch(j){
									case 0:
										expressRawData.setCost_center(ExcelUtils.getCellValue(cell));
									break;
									case 1:
										expressRawData.setStore_code(ExcelUtils.getCellValue(cell));
									break;
									case 2:
										expressRawData.setStore_name(ExcelUtils.getCellValue(cell));
									break;
									case 3:
										expressRawData.setWarehouse(ExcelUtils.getCellValue(cell));
									break;
									case 4:
										expressRawData.setTransport_code(ExcelUtils.getCellValue(cell));
									break;
									case 5:
										expressRawData.setTransport_name(ExcelUtils.getCellValue(cell));
									break;
									case 6:
										expressRawData.setDelivery_order(ExcelUtils.getCellValue(cell));
									break;
									case 7:
										expressRawData.setOrder_type(ExcelUtils.getCellValue(cell));
									break;
									case 8:
										expressRawData.setExpress_number(ExcelUtils.getCellValue(cell));
									break;
									case 9:
										expressRawData.setTransport_direction(ExcelUtils.getCellValue(cell));
									break;
									case 10:
										expressRawData.setItemtype_code(ExcelUtils.getCellValue(cell));
									break;
									case 11:
										expressRawData.setItemtype_name(ExcelUtils.getCellValue(cell));
									break;
									case 12:
										expressRawData.setTransport_time(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(ExcelUtils.getCellValue(cell)));
									break;
									case 13:
										expressRawData.setCollection_on_delivery(new BigDecimal(ExcelUtils.getCellValue(cell)));
									break;
									case 14:
										expressRawData.setOrder_amount(new BigDecimal(ExcelUtils.getCellValue(cell)));
									break;
									case 15:
										expressRawData.setSku_number(ExcelUtils.getCellValue(cell));
									break;
									case 16:
										expressRawData.setWeight(new BigDecimal(ExcelUtils.getCellValue(cell)));
									break;
									case 17:
										expressRawData.setLength(new BigDecimal(ExcelUtils.getCellValue(cell)));
									break;
									case 18:
										expressRawData.setWidth(new BigDecimal(ExcelUtils.getCellValue(cell)));
									break;
									case 19:
										expressRawData.setHigth(new BigDecimal(ExcelUtils.getCellValue(cell)));
									break;
									case 20:
										expressRawData.setVolumn(new BigDecimal(ExcelUtils.getCellValue(cell)));
									break;
									case 21:
										expressRawData.setDelivery_address(ExcelUtils.getCellValue(cell));
									break;
									case 22:
										expressRawData.setProvince(ExcelUtils.getCellValue(cell));
									break;
									case 23:
										expressRawData.setCity(ExcelUtils.getCellValue(cell));
									break;
									case 24:
										expressRawData.setState(ExcelUtils.getCellValue(cell));
									break;
									case 25:
										expressRawData.setStreet(ExcelUtils.getCellValue(cell));
									break;
									case 26:
										expressRawData.setInsurance_flag(new Boolean(ExcelUtils.getCellValue(cell)));
									break;
									case 27:
										expressRawData.setCod_flag(new Boolean(ExcelUtils.getCellValue(cell)));
									break;
									case 28:
										expressRawData.setBalance_subject(ExcelUtils.getCellValue(cell));
									break;
									}
								} else {
									// 当指定单元格不存在或为空串时
									switch(j){
									case 0:;
									case 2:;
									case 3:;
									case 5:;
									case 6:;
									case 8:;
									case 9:;
									case 12:;
									case 13:;
									case 14:;
									case 16:;
									case 21:;
									case 22:;
									case 26:;
									case 27:
										result.put("result_code", "ULF");
										result.put("result_content", "第" + rowNum + "行：字段" + ExcelUtils.getCellValue(sheet.getRow(0).getCell(j)) + "不能为空！");
										return result;
									}
								}
							}
							expressRawDatas.add(expressRawData);
						}
					}
				}
				saveExpressRawData(expressRawDatas);
				result.put("result_code", "SUCCESS");
				result.put("success_records", expressRawDatas.size());
				return result;
			}
		}
		return result;
	}
	
	@Override
	public Long selectCount(Map<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
}
