package com.bt.lmis.tools.compareData.service.impl;
import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.alibaba.fastjson.JSONObject;
import com.bt.common.CommonUtils;
import com.bt.common.controller.param.Parameter;
import com.bt.common.sequence.dao.SequenceMapper;
import com.bt.lmis.code.ServiceSupport;
import com.bt.lmis.datasource.DataSourceContextHolder;
import com.bt.lmis.model.Employee;
import com.bt.lmis.page.QueryResult;
import com.bt.lmis.tools.compareData.dao.CompareDataMapper;
import com.bt.lmis.tools.compareData.model.WhsCollectData;
import com.bt.lmis.tools.compareData.model.WhsPackCollectData;
import com.bt.lmis.tools.compareData.model.WhsPackData;
import com.bt.lmis.tools.compareData.model.WhsTask;
import com.bt.lmis.tools.compareData.service.CompareDataService;
import com.bt.lmis.tools.compareData.thread.CollectFuture;
import com.bt.lmis.tools.compareData.thread.PackFuture;
import com.bt.lmis.tools.compareData.thread.UseFuture;
import com.bt.utils.SessionUtils;
import com.google.common.collect.Maps;
import com.monitorjbl.xlsx.StreamingReader;


@Service
public class CompareDataServiceImpl<T> extends ServiceSupport<T> implements CompareDataService<T> {
	@Autowired
	private CompareDataMapper<T> compareDataMapper;
	
	@Autowired
	private SequenceMapper sequenceMapper;

	
	@Override
	public Long selectCount(Map<String, Object> param) throws Exception {
		return null;
	}

	@Transactional(rollbackFor=Exception.class)
	@Override
	public List<Map<String, Object>> importPackData(Workbook wk) {
		boolean isSuccess=true;
		boolean isCartonNo = true;
		
		List<Map<String, Object>> errorListMap = new ArrayList<>();
		Pattern _pattern = Pattern.compile("[0-9]+");// 数字
		Pattern pat = Pattern.compile("[\u2E80-\u9FFF]");//汉字
		//Pattern pattern = Pattern.compile("^[A-Za-z0-9]+$");// 数字和字母
		for (Sheet sheet : wk) {
			for (Row r : sheet) {
				if (r.getRowNum() != 0) {
					StringBuffer sb = new StringBuffer();
					Map<String, Object> map = new HashMap<>();
					Cell CartonNo = null;// 箱号
					Cell Article = null;
					Cell Size = null;
					Cell Qty = null;// 数量

					CartonNo = r.getCell(0);
					Article = r.getCell(1);
					Size = r.getCell(2);
					Qty = r.getCell(3);
					
					if((Qty == null||Qty.getStringCellValue().trim().equals(""))
							&&(CartonNo == null||CartonNo.getStringCellValue().trim().equals(""))
							&&(Article == null||Article.getStringCellValue().trim().equals(""))
							&&(Size == null||Size.getStringCellValue().trim().equals(""))
							){
						break;
					}
					
					if(r.getRowNum() == 1 ) {
						if(CartonNo == null) {
							isCartonNo = false;
						}
					}
					
					

					// 箱号校验
					if ((CartonNo == null||CartonNo.getStringCellValue().trim().equals(""))) {
						map.put("CartonNo", "");
						if(isCartonNo) {
							sb.append("请输入CartonNo，");
							isSuccess=false;
						}
					} else {
						String _cartonNo = CartonNo.getStringCellValue().trim();
						map.put("CartonNo", _cartonNo);
						// 校验特殊字符
						Matcher mat = pat.matcher(_cartonNo);
						if (mat.find()) {
							sb.append("CartonNo不可填写汉字，");
							isSuccess=false;
						}
						// 校验长度
						if (_cartonNo.length() > 50) {
							sb.append("CartonNo数据过长，");
							isSuccess=false;
						}
					}
					// 库位校验
					if (Article == null||Article.getStringCellValue().trim().equals("")) {
						map.put("Article", "");
						sb.append("请输入Article，");
						isSuccess=false;
					} else {
						String _article = Article.getStringCellValue().trim();
						map.put("Article", _article);
						// 校验特殊字符
						Matcher mat = pat.matcher(_article);
						if (mat.find()) {
							sb.append("Article含汉字，");
							isSuccess=false;
						}
						// 校验长度
						if (_article.length() > 50) {
							sb.append("Article数据过长，");
							isSuccess=false;
						}
					}
					// sku校验
					if (Size == null||Size.getStringCellValue().trim().equals("")) {
						map.put("Size", "");
						sb.append("请输入Size，");
						isSuccess=false;
					} else {
						String _size = Size.getStringCellValue().trim();
						map.put("Size", _size);
						// 校验特殊字符
						Matcher mat = pat.matcher(_size);
						if (mat.find()) {
							sb.append("Size不可填写汉字，");
							isSuccess=false;
						}
						// 校验长度
						if (_size.length() > 50) {
							sb.append("Size数据过长，");
							isSuccess=false;
						}
					}
					// 数量校验
					if (Qty == null||Qty.getStringCellValue().trim().equals("")) {
						map.put("Qty", "");
						sb.append("请输入Qty，");
						isSuccess=false;
					} else {
						String _qty = Qty.getStringCellValue().trim();
						map.put("Qty", _qty);
						// 校验特殊字符
						Matcher isNum = _pattern.matcher(_qty);
						if (!isNum.find()) {
							sb.append("Qty请输入整形数字，");
							isSuccess=false;
						}
						// 校验长度
						if (_qty.length() > 10) {
							sb.append("Qty数据过长，");
							isSuccess=false;
						}
					}
					
					String error = sb.toString();
					if(error.endsWith("，")) {
						error=error.substring(0,error.length()-1);
					}
					map.put("错误原因",error);
					errorListMap.add(map);
				}
			}
		}
		if(isSuccess) {
			return new ArrayList<Map<String,Object>>();
		}else {
			return errorListMap;
		}
	}
	
	@Transactional(rollbackFor=Exception.class)
	@Override
	public List<Map<String, Object>> importCollectData(Workbook wk) {
		boolean isSuccess=true;
		boolean isCartonNo = true;
		
		List<Map<String, Object>> errorListMap = new ArrayList<>();
		Pattern _pattern = Pattern.compile("[0-9]+");// 数字
		Pattern pat = Pattern.compile("[\u2E80-\u9FFF]");//汉字
		//Pattern pattern = Pattern.compile("^[A-Za-z0-9]+$");// 数字和字母
		for (Sheet sheet : wk) {
			for (Row r : sheet) {
				if (r.getRowNum() != 0) {
					StringBuffer sb = new StringBuffer();
					Map<String, Object> map = new HashMap<>();
					Cell CartonNo = null;// 箱号
					Cell Eancode = null;
					Cell Qty = null;// 数量

					CartonNo = r.getCell(0);
					Eancode = r.getCell(1);
					Qty = r.getCell(2);
					
					if((Qty == null||Qty.getStringCellValue().trim().equals(""))
							&&(CartonNo == null||CartonNo.getStringCellValue().trim().equals(""))
							&&(Eancode == null||Eancode.getStringCellValue().trim().equals(""))){
						break;
					}

					if(r.getRowNum() == 1 ) {
						if(CartonNo == null) {
							isCartonNo = false;
						}
					}

					// 箱号校验
					if ((CartonNo == null||CartonNo.getStringCellValue().trim().equals(""))) {
						map.put("CartonNo", "");
						if(isCartonNo) {
							sb.append("请输入CartonNo，");
							isSuccess=false;
						}
					} else {
						String _cartonNo = CartonNo.getStringCellValue().trim();
						map.put("CartonNo", _cartonNo);
						// 校验特殊字符
						Matcher mat = pat.matcher(_cartonNo);
						if (mat.find()) {
							sb.append("CartonNo不可填写汉字，");
							isSuccess=false;
						}
						// 校验长度
						if (_cartonNo.length() > 50) {
							sb.append("CartonNo数据过长，");
							isSuccess=false;
						}
					}
					// 库位校验
					if (Eancode == null||Eancode.getStringCellValue().trim().equals("")) {
						map.put("Eancode", "");
						sb.append("请输入Eancode，");
						isSuccess=false;
					} else {
						String _eancode = Eancode.getStringCellValue().trim();
						map.put("Eancode", _eancode);
						// 校验特殊字符
						Matcher mat = pat.matcher(_eancode);
						if (mat.find()) {
							sb.append("Eancode不可填写汉字，");
							isSuccess=false;
						}
						// 校验长度
						if (_eancode.length() > 50) {
							sb.append("Eancode数据过长，");
							isSuccess=false;
						}
					}
					// 数量校验
					if (Qty == null||Qty.getStringCellValue().trim().equals("")) {
						map.put("Qty", "");
						sb.append("请输入Qty，");
						isSuccess=false;
					} else {
						String _qty = Qty.getStringCellValue().trim();
						map.put("Qty", _qty);
						// 校验特殊字符
						Matcher isNum = _pattern.matcher(_qty);
						if (!isNum.find()) {
							sb.append("Qty请输入整形数字，");
							isSuccess=false;
						}
						// 校验长度
						if (_qty.length() > 10) {
							sb.append("Qty数据过长，");
							isSuccess=false;
						}
					}

					
					String error = sb.toString();
					if(error.endsWith("，")) {
						error=error.substring(0,error.length()-1);
					}
					map.put("错误原因",error);
					
					errorListMap.add(map);
				}
			}
		}
		if(isSuccess) {
			return new ArrayList<Map<String,Object>>();
		}else {
			return errorListMap;
		}
	}

	@Override
	public QueryResult<Map<String, Object>> queryCompareData(Parameter parameter) {
		// TODO Auto-generated method stub
		return new QueryResult<Map<String, Object>>(compareDataMapper.queryCompareTask(parameter),compareDataMapper.countCompareTask(parameter));
	}

	@Override
	public String getSequence(String tlsWhsTaskCode) {
		Long l = sequenceMapper.nextval(tlsWhsTaskCode);
		return String.format("%01d", l);
	}
	@Override
	public int deleteCompareTaskById(String id,String taskCode) {
		// TODO Auto-generated method stub
		compareDataMapper.deleteCollectDataByTaskCode(taskCode);
		compareDataMapper.deletePackDataByTaskCode(taskCode);
		return compareDataMapper.deleteCompareTaskById(id);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Transactional(rollbackFor=Exception.class)
	@Override
	public JSONObject compared(String taskCode,String empName) {

		JSONObject obj = new JSONObject();

		WhsTask whsTask=compareDataMapper.findTaskByTaskCode(taskCode);
		if(!CommonUtils.checkExistOrNot(whsTask)||whsTask.getStatus().equals("已对比")) {
			obj.put("code", "400");
			obj.put("msg", "当前任务已对比");
			return obj;
		}

		List<WhsPackCollectData> insertList=new ArrayList<>();
		try {
			Map<String,String> paramMap=new HashMap<>();
			paramMap.put("taskCode", taskCode);
			paramMap.put("empName", empName);
	
			List<WhsCollectData> collectList = compareDataMapper.findCollectByTaskCode(taskCode);
			List<WhsPackData> packList = compareDataMapper.findPackByTaskCode(taskCode);
	
			
			List<Future> futureList = new ArrayList<>();
			int count =0;
			if(packList.size() % 3000==0){
				count=packList.size() / 3000;
			}else{
				count = packList.size() / 3000 + 1;
			}
			
			
			ExecutorService executor = Executors.newFixedThreadPool(count);
			for (int k = 1; k <= count; k++) {
				List<WhsPackData> tempList = null;
				if (k == count) {
					tempList = packList.subList((k - 1) * 3000, packList.size());
				} else {
					tempList = packList.subList((k - 1) * 3000, k * 3000);
				}
				FutureTask<List<WhsPackCollectData>> future = new FutureTask<>(new PackFuture<T> (tempList,collectList,empName,taskCode));
				futureList.add(future);
				executor.submit(future);
	
			}
			for (Future future : futureList) {
				insertList.addAll((List<WhsPackCollectData>) future.get());
			}
			executor.shutdown();
			while (true) {
				if (executor.isTerminated()) {
					break;
				}
			}
			
			List<Future> _futureList = new ArrayList<>();
			int _count =0;
			if(collectList.size() % 1000==0){
				_count=collectList.size() / 3000;
			}else{
				_count = collectList.size() / 3000 + 1;
			}
			
			
			ExecutorService _executor = Executors.newFixedThreadPool(_count);
			for (int k = 1; k <= _count; k++) {
				List<WhsCollectData> tempList = null;
				if (k == _count) {
					tempList = collectList.subList((k - 1) * 3000, collectList.size());
				} else {
					tempList = collectList.subList((k - 1) * 3000, k * 3000);
				}
				FutureTask<List<WhsPackCollectData>> future = new FutureTask<>(new CollectFuture<T> (tempList,packList,empName,taskCode));
				_futureList.add(future);
				_executor.submit(future);
	
			}
			for (Future future : _futureList) {
				insertList.addAll((List<WhsPackCollectData>) future.get());
			}
			_executor.shutdown();
			while (true) {
				if (_executor.isTerminated()) {
					break;
				}
			}
			
			//采集数据未匹配到Article和Size
			insertList.addAll(compareDataMapper.findErrorCollectByTaskCode(paramMap));
			
	
		} catch (Exception e) {
			obj.put("code", "400");
			obj.put("msg", e.getMessage());
			e.printStackTrace();
		}
		// 分批添加
		int batch = 100;
		int size = insertList.size();
		int cycle = size / batch;
		if (size % batch != 0) {
			cycle++;
		}
		int fromIndex = 0;
		int toIndex = 0;
		for (int i = 0; i < cycle; i++) {
			fromIndex = i * batch;
			toIndex = fromIndex + batch;
			if (toIndex > size) {
				toIndex = size;
			}
			// 批量插入仓库暂存数据 支出
			compareDataMapper.createBatchPackCollectData(insertList.subList(fromIndex, toIndex));
		}

		
		WhsTask task = new WhsTask();
		task.setTaskCode(taskCode);
		task.setUpdateBy(empName);
		compareDataMapper.updateTaskByTaskCode(task);
		
		

		obj.put("code", "200");
		obj.put("msg", "校验完成");
		return obj;
	}

	

	@Override
	public ArrayList<Map<String, Object>> exportCollectPackData(String taskCode) {
		
		
		return compareDataMapper.exportCollectPackData(taskCode);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Map<String, Object> creatCompareTask(HttpServletRequest req, WhsTask task, String packPath, String collectPath) {
		File packFile = new File(packPath);
		File collectFile = new File(collectPath);
		Map<String, Object> result = Maps.newHashMap();
		List<Map<String, Object>> packMaps = new ArrayList<>();
		List<Map<String, Object>> collectMaps = new ArrayList<>();
		String packFileName = packFile.getName().substring(0, packFile.getName().lastIndexOf("."));//获取装箱数据文件名
		String collectFileName = collectFile.getName().substring(0, collectFile.getName().lastIndexOf("."));//获取采集数据文件名
		try {
			packMaps = readExcelFile(packPath, "pack");
			collectMaps = readExcelFile(collectPath, "collect");
			List<Object> eancodes = new ArrayList<>();
			for (Map<String, Object> collectMap : collectMaps) {
				eancodes.add(collectMap.get("Eancode"));
			}
			List<Map<String, Object>> resultList = new ArrayList<>();
			List<Future> futureList = new ArrayList<>();
			int count =0;
			if(eancodes.size() % 500==0){
				count=eancodes.size() / 500;
			}else{
				count = eancodes.size() / 500 + 1;
			}

			ExecutorService executor = Executors.newFixedThreadPool(count);
			for (int k = 1; k <= count; k++) {
				List<Object> tempList = null;
				if (k == count) {
					tempList = eancodes.subList((k - 1) * 500, eancodes.size());
				} else {
					tempList = eancodes.subList((k - 1) * 500, k * 500);
				}
				FutureTask<List<Map<String, Object>>> future = new FutureTask<>(new UseFuture<T>(tempList));
				futureList.add(future);
				executor.submit(future);

			}
			for (Future future : futureList) {
				resultList.addAll((List<Map<String, Object>>) future.get());
			}
			executor.shutdown();
			while (true) {
				if (executor.isTerminated()) {
					break;
				}
			}
			DataSourceContextHolder.clearDbType();//清空数据源
			result = creatCompareTask1(req, task, packFileName, collectFileName, resultList, packMaps, collectMaps);
		} catch (Exception e) {
			result.put("msg", e.getMessage());
			e.printStackTrace();
		}

		return result;
	}

	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> creatCompareTask1(HttpServletRequest req, WhsTask task, String packFileName, String collectFileName
			, List<Map<String, Object>> listwms, List<Map<String, Object>> packMaps, List<Map<String, Object>> collectMaps) {

		Map<String, Object> result = Maps.newHashMap();
		Employee user = SessionUtils.getEMP(req);

		WebApplicationContext contextLoader = ContextLoader.getCurrentWebApplicationContext();
		DataSourceTransactionManager transactionManager = (DataSourceTransactionManager) contextLoader.getBean("transactionManager");
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		// 事物隔离级别，开启新事务，这样会比较安全些。
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
		// 获得事务状态
		TransactionStatus status = transactionManager.getTransaction(def);
		try {

			compareDataMapper.deletePackDataByTaskCode(task.getTaskCode());
			compareDataMapper.deleteCollectDataByTaskCode(task.getTaskCode());
			compareDataMapper.deleteTaskByTaskCode(task.getTaskCode());

			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			//1.插入对比任务表
			WhsTask whsTask = new WhsTask();

			whsTask.setTaskCode(task.getTaskCode());
			whsTask.setCreateTime(formatter.parse(task.getTaskDate()));
			whsTask.setCreateBy(user.getUsername());
			whsTask.setUpdateTime(formatter.parse(task.getTaskDate()));
			whsTask.setUpdateBy(user.getUsername());
			whsTask.setStatus("未对比");
			whsTask.setPackName(packFileName);
			whsTask.setCollectName(collectFileName);
			whsTask.setPackNum(packMaps.size() + "");
			whsTask.setCollectNum(collectMaps.size() + "");
			int a = compareDataMapper.addCompareTask(whsTask);
			//2.批量插入装箱数据表
			for (Map<String, Object> packMap : packMaps) {
				packMap.put("task_code", task.getTaskCode());
				packMap.put("status", "1");
				packMap.put("create_by", user.getUsername());
				packMap.put("create_time", user.getUsername());
				packMap.put("update_by", user.getUsername());
				packMap.put("update_time", user.getUsername());
				if (packMap.get("Size") != null && !"".equals(packMap.get("Size"))) {
					if (packMap.get("Size").toString().contains("/")) {
						int lastIndexOf = packMap.get("Size").toString().lastIndexOf("/");

						packMap.put("act_size", packMap.get("Size").toString().substring(lastIndexOf+1, packMap.get("Size").toString().length()));
					} else {
						packMap.put("act_size", packMap.get("Size"));
					}
				}

			}

			long count1 =0;
			if(packMaps.size() % 100==0){
				count1=packMaps.size() / 100;
			}else{
				count1 = packMaps.size() / 100 + 1;
			}
			for (int i = 1; i <= count1; i++) {
				List<Map<String, Object>> tempList = null;
				if (i == count1) {
					tempList = packMaps.subList((i - 1) * 100, packMaps.size());
				} else {
					tempList = packMaps.subList((i - 1) * 100, i * 100);
				}
				compareDataMapper.createBatchPackData(tempList);
			}

			Map<String, Map<String, Object>> map1 = list2Map(listwms, "bar_code");
			for (Map<String, Object> temp : collectMaps) {
				temp.put("task_code", task.getTaskCode());
				temp.put("create_by", user.getUsername());
				temp.put("create_time", user.getUsername());
				temp.put("update_by", user.getUsername());
				temp.put("update_time", user.getUsername());
                if(!map1.isEmpty()) {
                	if (map1.get(temp.get("Eancode")) != null) {
    					if (map1.get(temp.get("Eancode")).get("style") != null && !"".equals(map1.get(temp.get("Eancode")).get("style")) && map1.get(temp.get("Eancode")).get("size") != null && !"".equals(map1.get(temp.get("Eancode")).get("size"))) {
    						temp.put("status", "解析成功");
    					} else {
    						temp.put("status", "解析失败");
    					}
    					if (map1.get(temp.get("Eancode")).get("style") != null) {
    						temp.put("article", map1.get(temp.get("Eancode")).get("style"));
    					}
    					if (map1.get(temp.get("Eancode")).get("size") != null) {
    						temp.put("size", map1.get(temp.get("Eancode")).get("size"));
    						if (map1.get(temp.get("Eancode")).get("size").toString().contains("/")) {
								int lastIndexOf = map1.get(temp.get("Eancode")).get("size").toString().lastIndexOf("/");
								temp.put("act_size", map1.get(temp.get("Eancode")).get("size").toString().substring(lastIndexOf+1, map1.get(temp.get("Eancode")).get("size").toString().length()));
    						} else {
    							temp.put("act_size", map1.get(temp.get("Eancode")).get("size"));
    						}
    					}
    				}else{
						temp.put("status", "解析失败");
					}
                }else {
                	temp.put("status", "解析失败");
                }
			
			}
			long count2 =0;
			if(collectMaps.size() % 100==0){
				count2=collectMaps.size() / 100;
			}else{
				count2 = collectMaps.size() / 100 + 1;
			}
			for (int i = 1; i <= count2; i++) {
				List<Map<String, Object>> tempList = null;
				if (i == count2) {
					tempList = collectMaps.subList((i - 1) * 100, collectMaps.size());
				} else {
					tempList = collectMaps.subList((i - 1) * 100, i * 100);
				}
				compareDataMapper.createBatchCollectData(tempList);
			}

			transactionManager.commit(status);
			if (a > 0) {
				result.put("msg", "成功");
				result.put("code", "sucess");
			} else {
				result.put("msg", "失败");
				result.put("code", "false");
			}

		} catch (Exception e) {
			transactionManager.rollback(status);
			result.put("msg", e.getMessage());
			result.put("code", "false");
			e.printStackTrace();
			return result;
		}

		return result;
	}


	private Map<String, Map<String, Object>> list2Map(List<Map<String, Object>> list, String key) {
		Map<String, Map<String, Object>> map = new HashMap<>();
		for (Map<String, Object> m : list) {
			String id = m.get(key) + "";
			map.put(id, m);
		}
		return map;
	}

	/**
	 */
	public static List<Map<String, Object>> readExcelFile(String filePath, String flag) throws Exception {
		List<Map<String, Object>> mapList = new ArrayList<>();
		FileInputStream in = new FileInputStream(filePath);
		Workbook wk = StreamingReader.builder().rowCacheSize(100) // 缓存到内存中的行数，默认是10
				.bufferSize(4096) // 读取资源时，缓存到内存的字节大小，默认是1024
				.open(in); // 打开资源，必须，可以是InputStream或者是File，注意：只能打开XLSX格式的文件
		for (Sheet sheet : wk) {
			for (Row r : sheet) {
				if (r.getRowNum() != 0) {
					Map<String, Object> map = new HashMap<>();
					if (flag.equals("pack")) {
						if(r.getCell(0)!=null){
							map.put("CartonNo", r.getCell(0).getStringCellValue());
							
						}else{
							map.put("CartonNo",null);
						}
						if(r.getCell(1).getStringCellValue()!=null&&!"".equals(r.getCell(1).getStringCellValue())&&
						r.getCell(2).getStringCellValue()!=null&&!"".equals(r.getCell(2).getStringCellValue())&&
						r.getCell(3).getStringCellValue()!=null&&!"".equals(r.getCell(3).getStringCellValue())){
							map.put("Article", r.getCell(1).getStringCellValue());
							map.put("Size", r.getCell(2).getStringCellValue());
							map.put("Qty", r.getCell(3).getStringCellValue());
							mapList.add(map);
						}

					} else {
						if(r.getCell(0)!=null){
							map.put("CartonNo", r.getCell(0).getStringCellValue());
						}else{
							map.put("CartonNo",null);
						}
						if(r.getCell(1).getStringCellValue()!=null&&!"".equals(r.getCell(1).getStringCellValue())&&
								r.getCell(2).getStringCellValue()!=null&&!"".equals(r.getCell(2).getStringCellValue())){
							map.put("Eancode", r.getCell(1).getStringCellValue());
							map.put("Qty", r.getCell(2).getStringCellValue());
							mapList.add(map);
						}

					}
				}
			}
		}
		return mapList;
	}


	public static void main(String[] args) {
		String str = "J/L";
		System.out.println(str.substring(str.lastIndexOf("/")+1,str.length()));
	}
}

