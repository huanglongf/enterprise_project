package com.bt.lmis.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.bt.OSinfo;
import com.bt.common.controller.result.Result;
import com.bt.lmis.api.model.Order_test;
import com.bt.lmis.controller.form.ExpressReturnStorageQueryParam;
import com.bt.lmis.controller.form.FileImportTaskQueryParam;
import com.bt.lmis.controller.form.FileTempleteQueryParam;
import com.bt.lmis.dao.ExpressReturnStorageMapper;
import com.bt.lmis.dao.FileImportTaskMapper;
import com.bt.lmis.dao.FileTempleteMapper;
import com.bt.lmis.model.Employee;
import com.bt.lmis.model.ExpressRawData;
import com.bt.lmis.model.ExpressReturnStorage;
import com.bt.lmis.model.FileImportTask;
import com.bt.lmis.page.QueryResult;
import com.bt.lmis.service.FileImportTaskService;
import com.bt.radar.dao.ExpressinfoMasterMapper;
import com.bt.radar.model.ExpressinfoMaster;
import com.bt.utils.CommonUtils;
import com.bt.utils.DateUtil;
import com.bt.utils.ExcelExportUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
* Title: FileImportTaskServiceImpl
* Description: 文件导入导出统一服务类
* Company: baozun
* @author jindong.lin
* @date 2018年1月30日
*/
@Service
@Transactional(readOnly=true)
public class FileImportTaskServiceImpl implements FileImportTaskService {
	
	@Autowired
	private FileImportTaskMapper fileImportTaskMapper;
	
	@Autowired
	private FileTempleteMapper fileTempleteMapper;

	@Autowired
	private ExpressReturnStorageMapper<?> expressReturnStorageMapper;

	@Autowired
	private ExpressinfoMasterMapper<HashMap<String,Object>> expressinfoMasterMapper;
	
	private final SimpleDateFormat baseJKSettleOrderDateFormat = new SimpleDateFormat("yyyy-MM-dd HH/mm/ss");

	private Map<String,Map<String,Object>> warehouseMap;//仓库map
	private Map<String,Map<String,Object>> storeMap;//店铺map

	@Override
	@Transactional(readOnly=false,rollbackFor = Exception.class)
	public int deleteByPrimaryKey(FileImportTask fileImportTask) {
		// TODO Auto-generated method stub
		return fileImportTaskMapper.deleteByPrimaryKey(fileImportTask);
	}

	@Override
	@Transactional(readOnly=false,rollbackFor = Exception.class)
	public int insert(FileImportTask record) {
		// TODO Auto-generated method stub
		return fileImportTaskMapper.insert(record);
	}

	@Override
	@Transactional(readOnly=false,rollbackFor = Exception.class)
	public int insertSelective(FileImportTask record) {
		// TODO Auto-generated method stub
		return fileImportTaskMapper.insertSelective(record);
	}

	@Override
	public FileImportTask selectByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return fileImportTaskMapper.selectByPrimaryKey(id);
	}

	@Override
	@Transactional(readOnly=false,rollbackFor = Exception.class)
	public int updateByPrimaryKeySelective(FileImportTask record) {
		// TODO Auto-generated method stub
		return fileImportTaskMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	@Transactional(readOnly=false,rollbackFor = Exception.class)
	public int updateByPrimaryKey(FileImportTask record) {
		// TODO Auto-generated method stub
		return fileImportTaskMapper.updateByPrimaryKey(record);
	}

	@Override
	public FileImportTaskQueryParam getByQueryParam(FileImportTaskQueryParam queryParam) {
		// TODO Auto-generated method stub
		return fileImportTaskMapper.getByQueryParam(queryParam);
	}

	@Override
	public QueryResult<FileImportTaskQueryParam> findAll(FileImportTaskQueryParam queryParam) {
		QueryResult<FileImportTaskQueryParam> queryResult = new QueryResult<FileImportTaskQueryParam>();
		queryResult.setResultlist(fileImportTaskMapper.getList(queryParam));
		queryResult.setTotalrecord(fileImportTaskMapper.getListCount(queryParam));
		return queryResult;
	}

	@Override
	public int checkTaskCount() {
		return fileImportTaskMapper.checkTaskCount();
	}

	@Override
	public int updateJkSettleOrder(ExpressRawData expressRawData) {
		return fileImportTaskMapper.updateJkSettleOrder(expressRawData);
	}

	@Override
	@Transactional(readOnly=false,rollbackFor = Exception.class)
	public void expressCoverImportExcel(List<String[]> list, FileImportTask fileImportTask,
			Employee user, List<String> titleList, String fileName) {
		
		//单sheet最大记录1048576

		String length = null;
		String width = null;
		String height = null;
		String volumn = null;
		String weight = null;
		String expressNumber = null;
		String[] row = null;
		
		StringBuffer message = new StringBuffer();
		Boolean isSuccess = false;
		Integer successNum = 0;
		ExpressRawData expressRawData = null;

		SXSSFWorkbook book = new SXSSFWorkbook(100000);//设置内存允许存在记录数，超过则写入磁盘
		SXSSFSheet sheet = book.createSheet("sheet1");
		fileName = fileName + "失败记录" + DateUtil.formatSS(new Date()) + ".xlsx";
		//当前行
		int curRow = 0;
		//写列的列说明
		SXSSFCell firstCell = null;
		SXSSFRow firstRow = null;
		if(titleList != null && titleList.size() > 0){
			firstRow = sheet.createRow(curRow);
			for (int j = 0; j < titleList.size(); j++) {
				firstCell = firstRow.createCell(j);
				firstCell.setCellValue(titleList.get(j));
				if(j == (titleList.size() - 1)){
					firstCell = firstRow.createCell(j+1);
					firstCell.setCellValue("异常信息");
				}
			}
			curRow++;
		}
		
		Map<String,Integer> titleIndexMap = new HashMap<String,Integer>();
		switch (fileImportTask.getBusinessType()) {
		    case "express_cover_sf"://顺丰模板
		    	titleIndexMap.put("length", 57);
		    	titleIndexMap.put("width", 58);
		    	titleIndexMap.put("height", 59);
		    	titleIndexMap.put("volumn", 32);
		    	titleIndexMap.put("weight", 37);
		    	titleIndexMap.put("expressNumber", 2);
		    	break;
		    case "express_cover_ems"://EMS模板
		    	titleIndexMap.put("length", 30);
		    	titleIndexMap.put("width", 31);
		    	titleIndexMap.put("height", 32);
		    	titleIndexMap.put("volumn", 33);
		    	titleIndexMap.put("weight", 4);
		    	titleIndexMap.put("expressNumber", 1);
		    	break;
		    case "express_cover_sto"://申通模板
		    	titleIndexMap.put("length", 12);
		    	titleIndexMap.put("width", 13);
		    	titleIndexMap.put("height", 14);
		    	titleIndexMap.put("volumn", 15);
		    	titleIndexMap.put("weight", 10);
		    	titleIndexMap.put("expressNumber", 4);
		    	break;
		    case "express_cover_yto"://圆通模板
		    	titleIndexMap.put("length", null);
		    	titleIndexMap.put("width", null);
		    	titleIndexMap.put("height", null);
		    	titleIndexMap.put("volumn", null);
		    	titleIndexMap.put("weight", 5);
		    	titleIndexMap.put("expressNumber", 1);
		    	break;
		    default:
		    	break;
		}
		
		//处理记录
		for (int i = 0; i < list.size(); i++) {
			
			row = list.get(i);
			message.setLength(0);
			isSuccess = true;
			
			length = titleIndexMap.get("length") == null ? null : row[titleIndexMap.get("length")];
			width = titleIndexMap.get("width") == null ? null : row[titleIndexMap.get("width")];
			height = titleIndexMap.get("height") == null ? null : row[titleIndexMap.get("height")];
			volumn = titleIndexMap.get("volumn") == null ? null : row[titleIndexMap.get("volumn")];//row[77];
			weight = titleIndexMap.get("weight") == null ? null : row[titleIndexMap.get("weight")];//row[64];
			expressNumber = titleIndexMap.get("expressNumber") == null ? 
					null : row[titleIndexMap.get("expressNumber")];

			if(StringUtils.isBlank(expressNumber)){
				isSuccess = false;
				message.append("|该记录运单号码字段未填值,请重新输入");
			}
			if(StringUtils.isBlank(length)){
				isSuccess = false;
				message.append("|该记录长度字段未填值,请重新输入");
			}
			if(StringUtils.isBlank(width)){
				isSuccess = false;
				message.append("|该记录宽度字段未填值,请重新输入");
			}
			if(StringUtils.isBlank(height)){
				isSuccess = false;
				message.append("|该记录高度字段未填值,请重新输入");
			}
			if(StringUtils.isBlank(volumn)){
				isSuccess = false;
				message.append("|该记录尺寸字段未填值,请重新输入");
			}
			if(StringUtils.isBlank(weight)){
				isSuccess = false;
				message.append("|该记录重量字段未填值,请重新输入");
			}
			
			if(isSuccess){
				//执行成功逻辑
				expressRawData = new ExpressRawData();
				expressRawData.setExpress_number(expressNumber);
				expressRawData.setLength(new BigDecimal(length));
				expressRawData.setWidth(new BigDecimal(width));
				expressRawData.setHigth(new BigDecimal(height));
				expressRawData.setVolumn(new BigDecimal(volumn));
				expressRawData.setWeight(new BigDecimal(weight));
				int r = updateJkSettleOrder(expressRawData);//刷新jk_settle_order表
				if(r > 0){
					successNum++;
				} else {
					isSuccess = false;
					message.append("|该运单更新失败,未匹配到运单");
				}
			}
			
			if(!isSuccess){
				//执行失败逻辑
				//插入结果表格
				firstRow = sheet.createRow(curRow);
				for (int j = 0; j < titleList.size(); j++) {
					firstCell = firstRow.createCell(j);
					firstCell.setCellValue(list.get(i)[j]);
					if(j == (titleList.size() - 1)){
						firstCell = firstRow.createCell(j+1);
						firstCell.setCellValue(message.toString());
					}
				}
				curRow++;
			}
		}
		
		//打标识已完成
		if(successNum.compareTo(list.size()) < 0){
			String filePath= CommonUtils.getAllMessage("config", "COMMON_DOWNLOAD_EXPRESS_RAW_DATA_"+
					OSinfo.getOSname()) + fileImportTask.getId();
			File baseFile = new File(filePath);
			if(!baseFile.exists()){
				baseFile.mkdirs();
			}
			filePath = filePath + "/" + fileName;
			baseFile = new File(filePath);
			FileOutputStream fo = null;
			try {
				fo = new FileOutputStream(baseFile);
				book.write(fo);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					if(fo != null){
						fo.flush();
						fo.close();
					}
					book.dispose();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			fileImportTask.setResultFilePath(fileImportTask.getId() + "/" + fileName);
			fileImportTask.setStatus("0");
		} else {
			fileImportTask.setStatus("1");
		}
		fileImportTask.setSuccessSum(successNum);
		fileImportTask.setUpdateTime(new Date());
		updateByPrimaryKeySelective(fileImportTask);
	}

	@Override
	@Transactional(readOnly=false,rollbackFor = Exception.class)
	public int delImportTaskByIds(String ids, Employee user) {
		if(ids != null){
			String[] idArr = ids.split(",");
			if(idArr != null && idArr.length > 0){
				FileImportTask fileImportTask = null;
				for (int i = 0; i < idArr.length; i++) {
					fileImportTask = new FileImportTask();
					fileImportTask.setId(idArr[i]);
					fileImportTask.setUpdateTime(new Date());
					fileImportTask.setUpdateUser(user.getId().toString());
					try {
						deleteByPrimaryKey(fileImportTask);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return -1;
					}
				}
			}
		}
		return 1;
	}


	/**
	 * 导入的excel转换成标准运单接口导入模板
	 * */
	@Override
	@Transactional(readOnly=false,rollbackFor = Exception.class)
	public Map<String, Object> convertReturnExcel(List<String[]> list, FileImportTask fileImportTask,
			Employee user) {
		Result result = null;
		String[] items = null;
		List<String[]> arrays= Lists.newArrayList();
		int successNum=0;
		for(int i=0;i<list.size();i++){
			items=list.get(i);
			result=convertImportData(items);
			if(result.isFlag()) {
				successNum++;
				arrays.add((String[]) result.getData());
			}
		}
		list = null;//置空中转list
		FileTempleteQueryParam fileTemplete = new FileTempleteQueryParam();
		fileTemplete.setBusinessType("express_import_templete");
		FileTempleteQueryParam resultTemplete = fileTempleteMapper.getByQueryParam(fileTemplete);
		List<String> titleList = JSONObject.parseArray(resultTemplete.getTempleteTitle(),String.class);
		String [] title = titleList.toArray(new String [titleList.size() + 1]);
		title[titleList.size()] = "是否匹配成功";
		arrays.add(0,title);
		// 文件保存路径
//		String fileName = resultTemplete.getTempleteName() + "_" + DateUtil.formatSS(new Date());
		String fileName ="退换货匹配结果_" + DateUtil.formatSS(new Date());
		String filePath= CommonUtils.getAllMessage("config", "COMMON_DOWNLOAD_EXPRESS_RAW_DATA_"+ OSinfo.getOSname()) 
				+ fileImportTask.getId() + "/";
		try {
			ExcelExportUtil.exportNewShell(arrays,filePath,fileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		fileImportTask.setUpdateTime(new Date());
		fileImportTask.setSuccessSum(successNum);
		fileImportTask.setResultFilePath(fileImportTask.getId() + "/" + fileName + ".xlsx");
		fileImportTask.setStatus("1");
		fileImportTaskMapper.updateByPrimaryKeySelective(fileImportTask);
		return null;
	}

	//全国报价订单新增导入
	@Override
	@Transactional(readOnly=false,rollbackFor = Exception.class)
	public Map<String, Object> convertOrderAddExcel(List<String[]> list, FileImportTask fileImportTask,
			Employee user, List<String> titleList, String fileName) {
		//两阶段判断
		//1.jk_settle_order插入阶段,单记录不合格直接全部返回,生成错误excel
		//2.jk_settle_order转换阶段,查询所有转换错误记录内容,生成错误excel
		initWareStoreMap();
		Result result = null;
		Boolean firstStepFlag = true;
		StringBuffer baseMessageBuffer = new StringBuffer();
		String [] title = titleList.toArray(new String [titleList.size()+1]);
		title[titleList.size()] = "异常信息";
		//第一阶段处理信息
		List<String[]> resultList= Lists.newArrayList();
		for(int i=0;i<list.size();i++){
			result = convertImportData(list.get(i),
					fileImportTask.getId(),
					i+1,
					baseMessageBuffer);
			if(!result.isFlag()) {
				firstStepFlag = false;
			}
			resultList.add((String[])result.getData());
		}
		Integer successNum=0;
		Integer totalCount = list.size();
		list = null;//置空中转list
		if(!firstStepFlag){
			//上传错误日志excel
			successNum = totalCount - resultList.size();
			resultList.add(0,title);
			fileName= fileName + "导入错误列表_"+DateUtil.formatSS(new Date());
			String filePath= CommonUtils.getAllMessage("config", "COMMON_DOWNLOAD_EXPRESS_RAW_DATA_"
					+ OSinfo.getOSname()) + fileImportTask.getId() + "/";
			try {
				ExcelExportUtil.exportNewShell(resultList,filePath,fileName);
			} catch (Exception e) {
				e.printStackTrace();
			}
			fileName= fileName + ".xlsx";
		} else {
			Order_test order_test = null;
			String[] items = null;
			if(resultList != null && !resultList.isEmpty()){
				for (int i = 0; i < resultList.size(); i++) {
					items = resultList.get(i);
					order_test = new Order_test();
					order_test.setBat_id(fileImportTask.getId());//批次号
					order_test.setUpper_id("3");
					order_test.setPlatform_mark(3);//调用方
					order_test.setIs_complete("1");
					order_test.setK_flag("0");
					order_test.setExpress_number(items[0]);//运单号
					order_test.setWarehouse_code(items[1]);//仓库编码
					order_test.setWarehouse_name(items[2]);//仓库名称
					order_test.setStore_code(items[3]);//店铺编码
					order_test.setStore_name(items[4]);//店铺名称
					order_test.setSku_number(items[5]);//耗材编码
					order_test.setLength(items[6]);//高
					order_test.setWidth(items[7]); //宽
					order_test.setHigth(items[8]); //高=体积
					order_test.setVolumn(items[9]);//体积
					order_test.setOrder_type(items[10]);//出库单类型
					order_test.setWeight(items[11]);//包裹重量
					order_test.setTransport_code(items[12]);//*运输服务商编码 快递代码
					order_test.setTransport_name(items[13]);//*运输服务商  快递名称
					order_test.setItemtype_code(items[14]);//服务类型编码
					order_test.setItemtype_name(checkItemTypeName(items[15]) == null ? 
							items[15] : checkItemTypeName(items[15]));//服务类型名称
					order_test.setDelivery_order(items[16]); //平台订单号
					order_test.setEpistatic_order(items[17]); //*相关单号  ，没有
					//*出库单金额 待定，不知道哪个字段
					order_test.setCollection_on_delivery(StringUtils.isBlank(items[19])?"0":items[19]);//代收货款
					order_test.setCod_flag(new BigDecimal(
							StringUtils.isBlank(items[19])?"0":items[19]
							)
							.compareTo(new BigDecimal("0")) > 0? "1":"0");
					order_test.setOrder_amount(StringUtils.isBlank(items[20])?"0":items[20]);// 订单金额 保价金额
					order_test.setInsurance_flag(new BigDecimal(
							StringUtils.isBlank(items[20])?"0":items[20]
							)
							.compareTo(new BigDecimal("0")) > 0? "1":"0");
					order_test.setTransport_time(items[21]);//出库时间
					order_test.setDelivery_address(items[22]);
					order_test.setProvince_origin(items[22]);
					order_test.setCity_origin(items[23]);
					order_test.setState_origin(items[24]);
					//目的地省 接口新增只传目的地,始发地省市区根据仓库编码获取
					order_test.setProvince(items[25]);
					order_test.setCity(items[26]);
					order_test.setState(items[27]);
					order_test.setShiptoname(items[28]);
					order_test.setPhone(items[29]);
					order_test.setAddress(items[30]);
					order_test.setQty(Integer.valueOf(items[31]));//包裹装箱数量
					//物流方向不填默认为0正向,1逆向
					order_test.setTransport_direction(StringUtils.isBlank(items[32])?"0":items[32]);
					//这里执行插入
					fileImportTaskMapper.convertOrderAddExcel(order_test);
					successNum++;
				}
				resultList = null;//置空中间表
				
				//这里进行第二步,调用存储过程
				HashMap<String,Object> paramMap = Maps.newHashMapWithExpectedSize(2);
				paramMap.put("bat_id", fileImportTask.getId());
				
				while (true) {
					fileImportTaskMapper.callConvertOrder(paramMap);
					
					if(paramMap != null 
							&& paramMap.get("out_result_reason") != null 
							&& paramMap.get("out_result_reason").equals("成功:0条,失败:0条")){
						break;
					} else if (paramMap == null || paramMap.get("out_result_reason") == null) {
						break;
					}
					
				}
				
				//执行完毕查询生成excel
				//fileName+转换详情
				List<Order_test> secoundList = fileImportTaskMapper.findOrderConvertResultList(paramMap);
				SXSSFWorkbook book = new SXSSFWorkbook(100000);//设置内存允许存在记录数，超过则写入磁盘
				SXSSFSheet sheet = book.createSheet("sheet1");
				//当前行
				int curRow = 0;
				//写列的列说明
				SXSSFCell firstCell = null;
				SXSSFRow firstRow = null;
				if(title != null){
					firstRow = sheet.createRow(curRow);
					for (int j = 0; j < title.length; j++) {
						firstCell = firstRow.createCell(j);
						firstCell.setCellValue(title[j]);
					}
					curRow++;
				}
				successNum = totalCount - secoundList.size();
				for (int i = 0; i < secoundList.size(); i++) {
					firstRow = sheet.createRow(curRow);
					order_test = secoundList.get(i);
					firstCell = firstRow.createCell(0);
					firstCell.setCellValue(order_test.getExpress_number());
					firstCell = firstRow.createCell(1);
					firstCell.setCellValue(order_test.getWarehouse_code());
					firstCell = firstRow.createCell(2);
					firstCell.setCellValue(order_test.getWarehouse_name());
					firstCell = firstRow.createCell(3);
					firstCell.setCellValue(order_test.getStore_code());
					firstCell = firstRow.createCell(4);
					firstCell.setCellValue(order_test.getStore_name());
					firstCell = firstRow.createCell(5);
					firstCell.setCellValue(order_test.getSku_number());
					firstCell = firstRow.createCell(6);
					firstCell.setCellValue(order_test.getLength());
					firstCell = firstRow.createCell(7);
					firstCell.setCellValue(order_test.getWidth());
					firstCell = firstRow.createCell(8);
					firstCell.setCellValue(order_test.getHigth());
					firstCell = firstRow.createCell(9);
					firstCell.setCellValue(order_test.getVolumn());
					firstCell = firstRow.createCell(10);
					firstCell.setCellValue(order_test.getOrder_type());
					firstCell = firstRow.createCell(11);
					firstCell.setCellValue(order_test.getWeight());
					firstCell = firstRow.createCell(12);
					firstCell.setCellValue(order_test.getTransport_code());
					firstCell = firstRow.createCell(13);
					firstCell.setCellValue(order_test.getTransport_name());
					firstCell = firstRow.createCell(14);
					firstCell.setCellValue(order_test.getItemtype_code());
					firstCell = firstRow.createCell(15);
					firstCell.setCellValue(checkItemTypeNameReverse(order_test.getItemtype_name()) == null ?
							order_test.getItemtype_name() : 
								checkItemTypeNameReverse(order_test.getItemtype_name()));
					firstCell = firstRow.createCell(16);
					firstCell.setCellValue(order_test.getDelivery_order());
					firstCell = firstRow.createCell(17);
					firstCell.setCellValue(order_test.getEpistatic_order());
					firstCell = firstRow.createCell(18);
					firstCell.setCellValue("");
					firstCell = firstRow.createCell(19);
					firstCell.setCellValue(order_test.getCollection_on_delivery());
					firstCell = firstRow.createCell(20);
					firstCell.setCellValue(order_test.getOrder_amount());
					firstCell = firstRow.createCell(21);
					firstCell.setCellValue(order_test.getTransport_time());
					firstCell = firstRow.createCell(22);
					firstCell.setCellValue(order_test.getProvince_origin());
					firstCell = firstRow.createCell(23);
					firstCell.setCellValue(order_test.getCity_origin());
					firstCell = firstRow.createCell(24);
					firstCell.setCellValue(order_test.getState_origin());
					firstCell = firstRow.createCell(25);
					firstCell.setCellValue(order_test.getProvince());
					firstCell = firstRow.createCell(26);
					firstCell.setCellValue(order_test.getCity());
					firstCell = firstRow.createCell(27);
					firstCell.setCellValue(order_test.getState());
					firstCell = firstRow.createCell(28);
					firstCell.setCellValue(order_test.getShiptoname());
					firstCell = firstRow.createCell(29);
					firstCell.setCellValue(order_test.getPhone());
					firstCell = firstRow.createCell(30);
					firstCell.setCellValue(order_test.getAddress());
					firstCell = firstRow.createCell(31);
					firstCell.setCellValue(order_test.getQty());
					firstCell = firstRow.createCell(32);
					firstCell.setCellValue(order_test.getTransport_direction());
					firstCell = firstRow.createCell(33);
					firstCell.setCellValue(order_test.getPro_remark());
					curRow++;
				}
				if(successNum.compareTo(totalCount) < 0){
					fileName = fileName + "转换失败详情列表_" + DateUtil.formatSS(new Date()) + ".xlsx";
					String filePath= CommonUtils.getAllMessage("config", "COMMON_DOWNLOAD_EXPRESS_RAW_DATA_"
							+ OSinfo.getOSname()) + fileImportTask.getId() + "/";
					File baseFile = new File(filePath);
					if(!baseFile.exists()){
						baseFile.mkdirs();
					}
					filePath = filePath + "/" + fileName;
					baseFile = new File(filePath);
					FileOutputStream fo = null;
					try {
						fo = new FileOutputStream(baseFile);
						book.write(fo);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} finally {
						try {
							if(fo != null){
								fo.flush();
								fo.close();
							}
							book.dispose();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		}
		
		fileImportTask.setUpdateTime(new Date());
		fileImportTask.setSuccessSum(successNum);
		if(successNum.compareTo(totalCount) < 0){
			fileImportTask.setResultFilePath(fileImportTask.getId() + "/" + fileName);
			fileImportTask.setStatus("0");
		} else {
			fileImportTask.setStatus("1");
		}
		fileImportTaskMapper.updateByPrimaryKeySelective(fileImportTask);
		return null;
	}

	//初始化仓库和店铺
	private void initWareStoreMap(){
		//仓库map
		if(warehouseMap == null || (warehouseMap != null && warehouseMap.isEmpty())){
			List<HashMap<String,Object>> warehouses= expressinfoMasterMapper
					.getWarehouse(null);
			warehouseMap = Maps.newHashMapWithExpectedSize(500);
			for(Map<String,Object> war:warehouses){
				warehouseMap.put(war.get("warehouse_code").toString(),war);
			}
		}
		//店铺
		if(storeMap == null || (storeMap != null && storeMap.isEmpty())){
			List<HashMap<String,Object>> stores= expressinfoMasterMapper
					.getStore(null);
			storeMap = Maps.newHashMapWithExpectedSize(500);
			if(stores != null && !stores.isEmpty()){
				for(Map<String,Object> s:stores){
					storeMap.put(s.get("store_code").toString(),s);
				}
			}
		}
	}
	
	/**
	 * Title: convertImportData
	 * Description: 导入数据校验（全国报价订单新增导入）
	 * @param items
	 * @param batchId
	 * @param rowCount
	 * @return
	 * @author duoduo.gao
	 * @date 2018年1月31日
	 */
	private Result convertImportData(String[] items,String batchId,int rowCount,StringBuffer sb){
		sb.setLength(0);
		Boolean resultFlag = true;
		if(StringUtils.isBlank(items[0])) {
			resultFlag = false;
			sb.append("|运单号不能为空");
		} 
//		else {
//			//查询运单号在退货表中是否存在
//			ExpressReturnStorageQueryParam expressReturnStorage=new ExpressReturnStorageQueryParam();
//			expressReturnStorage.setWaybill(items[0]);
//			List<ExpressReturnStorage> storagesList= expressReturnStorageMapper.queryList(expressReturnStorage);
//			if(storagesList == null || storagesList.isEmpty()){
//				resultFlag = false;
//				sb.append("|运单号不存在");
//			}
//		}
		if(StringUtils.isBlank(items[1])){
			resultFlag = false;
			sb.append("|仓库编码不能为空");
		} else {
			if(warehouseMap.get(items[1]) == null){
				resultFlag = false;
				sb.append("|仓库编码不存在");
			}
		}
		if(StringUtils.isBlank(items[3])){
			resultFlag = false;
			sb.append("|店铺编码不能为空");
		} else {
			if(storeMap.get(items[3]) == null){
				resultFlag = false;
				sb.append("|店铺编码不存在");
			}
		}
		BigDecimal baseDecimal = new BigDecimal("0");
		if(StringUtils.isBlank(items[6])){
			resultFlag = false;
			sb.append("|长CM不能为空");
		} else {
			try {
				if(new BigDecimal(items[6]).compareTo(baseDecimal) < 1){
					resultFlag = false;
					sb.append("|长CM必须大于0");
				}
			} catch (NumberFormatException e) {
				resultFlag = false;
				sb.append("|长CM必须为数字");
			}
		}
		if(StringUtils.isBlank(items[7])){
			resultFlag = false;
			sb.append("|宽CM不能为空");
		} else {
			try {
				if(new BigDecimal(items[7]).compareTo(baseDecimal) < 1){
					resultFlag = false;
					sb.append("|宽CM必须大于0");
				}
			} catch (NumberFormatException e) {
				resultFlag = false;
				sb.append("|宽CM必须为数字");
			}
		}
		if(StringUtils.isBlank(items[8])){
			resultFlag = false;
			sb.append("|高CM不能为空");
		} else {
			try {
				if(new BigDecimal(items[8]).compareTo(baseDecimal) < 1){
					resultFlag = false;
					sb.append("|高CM必须大于0");
				}
			} catch (NumberFormatException e) {
				resultFlag = false;
				sb.append("|高CM必须为数字");
			}
		}
		if(StringUtils.isBlank(items[9])){
			resultFlag = false;
			sb.append("|体积不能为空");
		} else {
			try {
				if(new BigDecimal(items[9]).compareTo(baseDecimal) < 1){
					resultFlag = false;
					sb.append("|体积必须大于0");
				}
			} catch (NumberFormatException e) {
				resultFlag = false;
				sb.append("|体积必须为数字");
			}
		}
		if(StringUtils.isBlank(items[11])){
			resultFlag = false;
			sb.append("|包裹重量不能为空");
		} else {
			try {
				if(new BigDecimal(items[11]).compareTo(baseDecimal) < 1){
					resultFlag = false;
					sb.append("|包裹重量必须大于0");
				}
			} catch (NumberFormatException e) {
				resultFlag = false;
				sb.append("|包裹重量必须为数字");
			}
		}
		if(StringUtils.isBlank(items[12])){
			resultFlag = false;
			sb.append("|运输服务商编码不能为空");
		}
		if(StringUtils.isBlank(items[14])){
			resultFlag = false;
			sb.append("|服务类型编码不能为空");
		}
		if(StringUtils.isBlank(items[15])){
			resultFlag = false;
			sb.append("|服务类型名称不能为空");
		} else if(checkItemTypeName(items[15]) == null) {
			resultFlag = false;
			sb.append("|服务类型名称未找到");
		}
		if(StringUtils.isNotBlank(items[19])){
			try {
				new BigDecimal(items[19]);
			} catch (NumberFormatException e) {
				resultFlag = false;
				sb.append("|代收货款金额必须为数字");
			}
		}
		if(StringUtils.isNotBlank(items[20])){
			try {
				new BigDecimal(items[20]);
			} catch (NumberFormatException e) {
				resultFlag = false;
				sb.append("|保价金额必须为数字");
			}
		}
		if(StringUtils.isBlank(items[21])){
			resultFlag = false;
			sb.append("|出库时间不能为空");
		} else {
			try {
				// 设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
				baseJKSettleOrderDateFormat.setLenient(false);
				baseJKSettleOrderDateFormat.parse(items[21]);
			} catch (ParseException | NullPointerException e) {
				resultFlag = false;
				sb.append("|出库时间格式不正确,例:yyyy-MM-dd HH/mm/ss");
			} 
		}
		if(StringUtils.isNotBlank(items[32]) 
				&& items[32].equals("1") 
				&& StringUtils.isBlank(items[22])){
			resultFlag = false;
			sb.append("|物流方向逆向时始发省不能为空");
		}
		if(StringUtils.isBlank(items[25])){
			resultFlag = false;
			sb.append("|派送省不能为空");
		}
		if(StringUtils.isBlank(items[26])){
			resultFlag = false;
			sb.append("|派送市不能为空");
		}
//		if(StringUtils.isBlank(items[27])){
//			resultFlag = false;
//			sb.append("|派送区/县不能为空");
//		}
		if(StringUtils.isBlank(items[31])){
			resultFlag = false;
			sb.append("|包裹装箱数量不能为空");
		} else {
			try {
				if(Integer.valueOf(items[31]).compareTo(baseDecimal.intValue()) < 1){
					resultFlag = false;
					sb.append("|包裹装箱数量必须大于0");
				}
			} catch (NumberFormatException e) {
				resultFlag = false;
				sb.append("|包裹装箱数量必须为整数数字");
			}
		}
		if(StringUtils.isNotBlank(items[32]) 
				&& !(items[32].equals("1") || items[32].equals("0"))){
			resultFlag = false;
			sb.append("|物流方向只能为0或1,不填时默认为0");
		}
		return  new Result(resultFlag,"",convertStringErrArray(items,sb.toString()));
	}

	//导入数据校验,转换成excel导出（退货入库匹配导出）
	private Result convertImportData(String[] items){
		StringBuffer errRemark=new StringBuffer(); //错误备注
		String[] strings=new String[34];
		if(StringUtils.isBlank(items[2])) return  new Result(false,"运单号不能为空");
		if(StringUtils.isBlank(items[3]))  errRemark.append("目的地市不能为空,");
		if(StringUtils.isBlank(items[6]))  errRemark.append("服务类型不能为空,");
		if(StringUtils.isBlank(items[8]))  errRemark.append("保价金额不能为空,");
		if(StringUtils.isBlank(items[24])) errRemark.append("出库时间不能为空,");
		if(StringUtils.isBlank(items[32])) errRemark.append("体积不能为空,");
		if(StringUtils.isBlank(items[37])) errRemark.append("包裹重量不能为空,");
		if(StringUtils.isBlank(items[33])) errRemark.append("目的地省不能为空,");
		//查询运单号在退货表中是否存在
		ExpressReturnStorageQueryParam expressReturnStorage=new ExpressReturnStorageQueryParam();
		expressReturnStorage.setWaybill(items[2]);
		List<ExpressReturnStorage> storagesList= expressReturnStorageMapper.queryList(expressReturnStorage);
		if(storagesList == null || storagesList.isEmpty()) return  new Result(false,"运单号不存在");

		int i=0;//有坑，首位必须i++否则会被第二列覆盖
		strings[i++]=storagesList.get(0).getWaybill();			//运单号
		strings[i++]=storagesList.get(0).getWarehouse_code();	//仓库编码
		strings[i++]=storagesList.get(0).getWarehouse_name();	//仓库名称
		strings[i++]=storagesList.get(0).getStore_code();		//店铺编码
		strings[i++]=storagesList.get(0).getStore_name();		//店铺名称
		strings[i++]="无";										//耗材编码
		strings[i++]="1";										//长
		strings[i++]="1";										//宽
		strings[i++]=items[32];									//高
		strings[i++]=items[32];									//体积
		strings[i++]="退货运单";									//出库单类型
		strings[i++]=items[37];									//包裹重量
		strings[i++]="SF";										//运输服务商编码
		strings[i++]="顺丰";										//运输服务商
		strings[i++]="";										//服务类型编码
//		String productTypeName="";
		strings[i++]=items[6];									//服务类型名称
		strings[i++]=storagesList.get(0).getPlatform_no();		//平台订单号
		strings[i++]=storagesList.get(0).getRelated_no();		//相关单号
		strings[i++]="0";										//出库单金额
		strings[i++]="0";										//代收货款
		strings[i++]=items[8];									//保价金额
		strings[i++]=items[24];									//出库时间
		strings[i++]=items[25];									//始发省
		strings[i++]=items[17];									//始发市
		strings[i++]="";										//始发区
		strings[i++]=items[33];									//派送省
		strings[i++]=items[3];									//派送市
		strings[i++]="";										//派送区
		strings[i++]="";										//收货人
		strings[i++]="";										//收货人联系电话
		strings[i++]="";										//收货人详细地址
		strings[i++]="";										//包裹装箱数量
		strings[i++]="1";										//物流方向1逆向0正向
		if(errRemark == null || String.valueOf(errRemark)=="")
			strings[i++]= "是";									//是否匹配成功
		else
			strings[i++]= "否,"+String.valueOf(errRemark);
		
		return  new Result(true,"",strings);
	}

	//封装订单导入错误excel
	private String[] convertStringErrArray(String[] items, String errRemark){
		String[] strings=new String[items.length+1];
		for (int i = 0; i < items.length; i++) {
			strings[i] = items[i];
		}
		strings[items.length]=errRemark;
		return  strings;
	}
	
	
	/**
	 * Title: checkItemTypeName
	 * Description: 检验是否存在承运商产品类型(服务类型)名称
	 * @param productTypeName
	 * @return
	 * @author jindong.lin
	 * @date 2018年2月7日
	 */
	private String checkItemTypeName(String productTypeName){
		switch (productTypeName){
			case "顺丰次日":
				return "1";//服务类型名称
			case "顺丰隔日":
				return "2";
			case "顺丰次晨":
				return "5";
			case "顺丰即日":
				return "6";
			case "云仓次日":
				return "37";
			case "云仓隔日":
				return "38";
			default:
				return null;
		}
	}
	
	/**
	 * Title: checkItemTypeNameReverse
	 * Description: 通过产品类型代码(仅限顺丰)反推产品类型名称
	 * @param productTypeCode
	 * @return
	 * @author jindong.lin
	 * @date 2018年2月8日
	 */
	private String checkItemTypeNameReverse(String productTypeCode){
		switch (productTypeCode){
			case "1":
				return "顺丰次日";//服务类型名称
			case "2":
				return "顺丰隔日";
			case "3":
				return "顺丰隔日";
			case "5":
				return "顺丰次晨";
			case "6":
				return "顺丰即日";
			case "37":
				return "云仓次日";
			case "38":
				return "云仓隔日";
			default:
				return null;
		}
	}

	@Override
	@Transactional(readOnly=false,rollbackFor = Exception.class)
	public void expressinfoImportMatchExcel(List<String[]> list, FileImportTask fileImportTask, Employee user,
			List<String> titleList, String templeteName) {
		
		Integer successNum = list.size();
		Integer totalCount = 0;
		
		//插入导入数据
		Map<String,Object> insertParam = new HashMap<String,Object>();
		insertParam.put("bat_id", fileImportTask.getId());
		insertParam.put("create_user", user.getId());
		insertParam.put("update_user", user.getId());
		for (int i = 0; i < list.size(); i++) {
			insertParam.put("waybill", list.get(i)[0]);
			expressinfoMasterMapper.insertImportMatchDetail(insertParam);
			totalCount++;
		}
		
		//执行sql带出导出结果
		List<Map<String,Object>> resultList = expressinfoMasterMapper.getExpressinfoImportMatchResult(insertParam);

		//生成结果文件
		SXSSFWorkbook book = new SXSSFWorkbook(100000);//设置内存允许存在记录数，超过则写入磁盘
		SXSSFSheet sheet = book.createSheet("sheet1");
		//当前行
		int curRow = 0;
		//写列的列说明
		SXSSFCell firstCell = null;
		SXSSFRow firstRow = null;
		Object cellValue = null;
		if(titleList != null && titleList.size() > 0){
			firstRow = sheet.createRow(curRow);
			for (int j = 0; j < titleList.size(); j++) {
				firstCell = firstRow.createCell(j);
				firstCell.setCellValue(titleList.get(j));
			}
			curRow++;
		}
		for (int i = 0; i < resultList.size(); i++) {
			//插入结果表格
			firstRow = sheet.createRow(curRow);
			for (int j = 0; j < titleList.size(); j++) {
				firstCell = firstRow.createCell(j);
				cellValue = resultList.get(i).get(titleList.get(j));
				firstCell.setCellValue(cellValue == null ? "" : cellValue.toString());
			}
			curRow++;
		}

		//收尾
		fileImportTask.setUpdateTime(new Date());
		fileImportTask.setSuccessSum(successNum);
		if(successNum.compareTo(totalCount) == 0){
			String filePath= CommonUtils.getAllMessage("config", "COMMON_DOWNLOAD_EXPRESS_RAW_DATA_"+
					OSinfo.getOSname()) + fileImportTask.getId();
			File baseFile = new File(filePath);
			if(!baseFile.exists()){
				baseFile.mkdirs();
			}
			filePath = filePath + "/" + templeteName + ".xlsx";
			baseFile = new File(filePath);
			FileOutputStream fo = null;
			try {
				fo = new FileOutputStream(baseFile);
				book.write(fo);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					if(fo != null){
						fo.close();
					}
					book.dispose();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			fileImportTask.setResultFilePath(fileImportTask.getId() + "/" + templeteName
					+ ".xlsx");
			fileImportTask.setStatus("1");
		} else {
			fileImportTask.setStatus("0");
		}
		fileImportTaskMapper.updateByPrimaryKeySelective(fileImportTask);
		expressinfoMasterMapper.clearMatchedData(fileImportTask.getId());
	}
	
}
