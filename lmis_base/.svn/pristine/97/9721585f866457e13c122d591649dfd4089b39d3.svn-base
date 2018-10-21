package com.lmis.setup.pageTable.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections4.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lmis.common.dataFormat.ObjectUtils;
import com.lmis.common.dynamicSql.dao.DynamicSqlMapper;
import com.lmis.common.dynamicSql.model.DynamicSqlParam;
import com.lmis.common.dynamicSql.model.Element;
import com.lmis.common.dynamicSql.service.DynamicSqlServiceInterface;
import com.lmis.common.dynamicSql.service.RedisHelperServiceInterface;
import com.lmis.common.getData.model.ConstantData;
import com.lmis.common.getData.service.GetConstantDataServiceInterface;
import com.lmis.common.util.BaseUtils;
import com.lmis.framework.baseInfo.LmisConstant;
import com.lmis.framework.baseInfo.LmisPageObject;
import com.lmis.framework.baseInfo.LmisResult;
import com.lmis.setup.pageElement.dao.SetupPageElementMapper;
import com.lmis.setup.pageElement.model.SetupPageElement;
import com.lmis.setup.pageElement.model.ViewSetupPageElement;
import com.lmis.setup.pageLayout.dao.SetupPageLayoutMapper;
import com.lmis.setup.pageLayout.model.SetupPageLayout;
import com.lmis.setup.pageTable.dao.SetupPageTableMapper;
import com.lmis.setup.pageTable.model.SetupPageTable;
import com.lmis.setup.pageTable.model.ViewSetupPageTable;
import com.lmis.setup.pageTable.service.SetupPageTableServiceInterface;

/** 
 * @ClassName: SetupPageTableServiceImpl
 * @Description: TODO(页面列表业务层实现)
 * @author Ian.Huang
 * @date 2017年12月11日 下午4:44:32 
 * 
 * @param <T>
 */
@Transactional(rollbackFor=Exception.class)
@Service
public class SetupPageTableServiceImpl<T extends SetupPageTable> implements SetupPageTableServiceInterface<T> {
	
	@Resource(name="dynamicSqlServiceImpl")
	DynamicSqlServiceInterface<SetupPageTable> dynamicSqlService;
	@Autowired
	private SetupPageTableMapper<T> setupPageTableMapper;
	@Autowired
	private SetupPageLayoutMapper<SetupPageLayout> setupPageLayoutMapper;
	@Autowired
	private SetupPageElementMapper<SetupPageElement> setupPageElementMapper;
	@Autowired
    private RedisTemplate<?, ?> redisTemplate;
	@Autowired
	private BaseUtils baseUtils;
	@Autowired
    private DynamicSqlMapper<T> dynamicSqlMapper;
	@Resource(name="getConstantDataServiceImpl")
	GetConstantDataServiceInterface<ConstantData> getConstantDataService;
	@Resource(name="redisHelperServiceImpl")
	RedisHelperServiceInterface<SetupPageElement> redisHelperService;
	
	// redis命名规则，元素部分的"setup:sql:column:"+表setup_page_table中字段layout_id
	@Value("${redis.key.column}")
    String redisKeyColumn;
	
	@Override
	public LmisResult<?> queryPage(ViewSetupPageTable viewSetupPageTable, LmisPageObject pageObject) throws Exception {
		Page<ViewSetupPageTable> page = PageHelper.startPage(pageObject.getPageNum(), pageObject.getPageSize());
	
        //PageHelper会自动拦截到下面这查询sql
		setupPageTableMapper.listSetupPageTableBySeq(viewSetupPageTable);
	
		//封装返回页面参数对象
		LmisResult<ViewSetupPageTable> lmisResult = new LmisResult<ViewSetupPageTable>();
		lmisResult.setMetaAndData(page.toPageInfo());
		lmisResult.setCode(LmisConstant.RESULT_CODE_SUCCESS);
        return lmisResult;
	}
	
	@Override
	public LmisResult<?> executeSelect(DynamicSqlParam<T> dynamicSqlParam, LmisPageObject pageObject) throws Exception {
		return dynamicSqlService.executeSelect(dynamicSqlParam, pageObject);
	}

	@SuppressWarnings("unchecked")
	@Override
	public LmisResult<?> executeSelect(DynamicSqlParam<T> dynamicSqlParam) throws Exception {
		LmisResult<?> _lmisResult = dynamicSqlService.executeSelect(dynamicSqlParam);
		if(LmisConstant.RESULT_CODE_ERROR.equals(_lmisResult.getCode())) return _lmisResult;
		List<Map<String, Object>> result =  (List<Map<String, Object>>) _lmisResult.getData();
		if(ObjectUtils.isNull(result)) throw new Exception("查无记录，数据异常");
		if(result.size() != 1) throw new Exception("记录存在多条，数据异常");
		return new LmisResult<T>(LmisConstant.RESULT_CODE_SUCCESS, result.get(0));
	}
	
	/**
	 * @Title: checkSetupPageTable
	 * @Description: TODO(校验查询结果字段数据)
	 * @param t
	 * @throws Exception
	 * @return: void
	 * @author: Ian.Huang
	 * @date: 2018年1月2日 下午8:46:11
	 */
	private void checkSetupPageTable(T t) throws Exception {
		
		// 页面元素需存在页面布局中
		if(ObjectUtils.isNull(t.getLayoutId())) throw new Exception("查询结果字段需存在页面布局中");
		
		// 查询提交了的页面布局
		SetupPageLayout param = new SetupPageLayout();
		param.setIsDeleted(false);
		param.setLayoutId(t.getLayoutId());
		List<SetupPageLayout> currentLayout = setupPageLayoutMapper.retrieve(param);
		
		// 绑定页面元素的页面布局需存在
		if(ObjectUtils.isNull(currentLayout)) throw new Exception("绑定查询结果字段的页面布局需存在");
		
		// 绑定页面元素的页面布局只可能存在一条记录
		if(currentLayout.size() != 1) throw new Exception("绑定查询结果字段的页面布局只可能存在一条记录");
		
		// 绑定页面元素的页面布局需为元素布局
		if(!"layout_a".equals(currentLayout.get(0).getLayoutType())) throw new Exception("绑定查询结果字段的页面布局需为元素布局");
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public LmisResult<T> addSetupPageTable(T t) throws Exception {
		
		// 唯一校验
		if(!ObjectUtils.isNull(setupPageTableMapper.retrieve((T) new SetupPageTable(null, false, t.getColumnId())))) throw new Exception("列ID已经存在，不能新增");
		
		// 数据业务校验
		checkSetupPageTable(t);
		
		// 新增操作
		return new LmisResult<T>(LmisConstant.RESULT_CODE_SUCCESS, setupPageTableMapper.create(t));
	}

	@SuppressWarnings("unchecked")
	@Override
	public LmisResult<T> updateSetupPageTable(T t) throws Exception {
			
		// 存在校验
		if(ObjectUtils.isNull(setupPageTableMapper.retrieve((T) new SetupPageTable(null, false, t.getColumnId())))) throw new Exception("列ID不存在，不能更新");
	
		// 数据业务校验
		checkSetupPageTable(t);
	
		// 修改操作
		return new LmisResult<T>(LmisConstant.RESULT_CODE_SUCCESS, setupPageTableMapper.updateRecord(t));
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public LmisResult<?> executeUpdate(DynamicSqlParam<T> dynamicSqlParam) throws Exception {
		SetupPageElement param = new SetupPageElement();
		param.setIsDeleted(false);
		param.setLayoutId(dynamicSqlParam.getLayoutId());
		param.setTableId("setup_page_table");
		param.setColumnId("column_id");
		List<SetupPageElement> tmp = setupPageElementMapper.retrieve(param);
		if(ObjectUtils.isNull(tmp)) throw new Exception("无字段column_id对应记录，数据异常");
		if(tmp.size() != 1) throw new Exception("字段column_id存在多条记录，数据异常");
		
		// 存在校验
		if(ObjectUtils.isNull(setupPageTableMapper.retrieve((T) new SetupPageTable(null, false, dynamicSqlParam.getElementValue(tmp.get(0).getElementId()))))) throw new Exception("查询结果字段ID不存在，不能更新");
		return dynamicSqlService.executeUpdate(dynamicSqlParam);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public LmisResult<T> deleteSetupPageTable(T t) throws Exception {
			
		// 存在校验
		if(ObjectUtils.isNull(setupPageTableMapper.retrieve((T) new SetupPageTable(null, false, t.getColumnId())))) throw new Exception("列ID不存在，不能删除");
		
		// 删除操作
		return new LmisResult<T>(LmisConstant.RESULT_CODE_SUCCESS, setupPageTableMapper.logicalDelete(t));
	}

	@SuppressWarnings("unchecked")
	@Override
	public LmisResult<?> redisForPageTables() throws Exception {
		List<String> allValidateLayoutId = setupPageTableMapper.listAllValidateLayoutId();
		for(int i = 0; i < allValidateLayoutId.size(); i++) {
			ViewSetupPageTable param = new ViewSetupPageTable();
			param.setLayoutId(allValidateLayoutId.get(i));
			List<ViewSetupPageTable> setupPageTables = setupPageTableMapper.listValidateSetupPageTable(param);
			if(!ObjectUtils.isNull(setupPageTables)) {
				((ValueOperations<String, List<ViewSetupPageTable>>)redisTemplate.opsForValue()).set(redisKeyColumn + allValidateLayoutId.get(i), setupPageTables);
			}
		}
		LmisResult<T> lmisResult = new LmisResult<T>();
		lmisResult.setCode(LmisConstant.RESULT_CODE_SUCCESS);
		return lmisResult;
	}
	
	@Override
	public LmisResult<?> redisForPageTablesPipeline() throws Exception {
		List<String> allValidateLayoutIds = setupPageTableMapper.listAllValidateLayoutId();
		if (!ObjectUtils.isNull(allValidateLayoutIds)) {
			// 设置批量获取的最大条目
			int maxSize  = 1000;
	        List<List<String>> subSets = ListUtils.partition(allValidateLayoutIds, maxSize);
	        for (List<String> list : subSets) {
	        	pipelineSetupPageTables(list);
			}
		}
		LmisResult<T> lmisResult = new LmisResult<T>();
		lmisResult.setCode(LmisConstant.RESULT_CODE_SUCCESS);
		return lmisResult;
	}

	@SuppressWarnings("unchecked")
	private void pipelineSetupPageTables(List<String> allValidateLayoutIds) {
		List<ViewSetupPageTable> setupPageTables = setupPageTableMapper.listValidateSetupPageTableByLayoutIds(allValidateLayoutIds);
		Map<String, List<ViewSetupPageTable>> setupPageTablesMap = new HashMap<>();
		for(ViewSetupPageTable viewSetupPageTable : setupPageTables) {
			String redisKeyColumnId = redisKeyColumn + viewSetupPageTable.getLayoutId();
			if (!setupPageTablesMap.containsKey(redisKeyColumnId)) {
				setupPageTablesMap.put(redisKeyColumnId, new ArrayList<ViewSetupPageTable>());
			}
			setupPageTablesMap.get(redisKeyColumnId).add(viewSetupPageTable);
		}
		((ValueOperations<String, List<ViewSetupPageTable>>)redisTemplate.opsForValue()).multiSet(setupPageTablesMap);
	}

	@Override
	public LmisResult<T> getSetupPageTable(T t) throws Exception {
		List<T> result = setupPageTableMapper.retrieve(t);
		if(ObjectUtils.isNull(result)) throw new Exception("查无记录，数据异常");
		if(result.size() != 1) throw new Exception("记录存在多条，数据异常");
		return new LmisResult<T>(LmisConstant.RESULT_CODE_SUCCESS, result.get(0));
	}

	@SuppressWarnings("unchecked")
	@Override
	public LmisResult<?> executeInsert(DynamicSqlParam<T> dynamicSqlParam) throws Exception {
		// 是否配置检查
		SetupPageElement param = new SetupPageElement();
		param.setIsDeleted(false);
		param.setLayoutId(dynamicSqlParam.getLayoutId());
		param.setTableId("setup_page_table");
		param.setColumnId("column_id");
		List<SetupPageElement> tmp = setupPageElementMapper.retrieve(param);
		if(ObjectUtils.isNull(tmp)) throw new Exception("无字段column_id对应记录，数据异常");
		if(tmp.size() != 1) throw new Exception("字段column_id存在多条记录，数据异常");
		
		SetupPageTable setupPageTable = dynamicSqlService.generateTableModel((DynamicSqlParam<SetupPageTable>) dynamicSqlParam, new SetupPageTable()).getTableModel();

		// 数据业务校验
		checkSetupPageTable((T) setupPageTable);
		
		// 不允许前端传递列表 ID
		notAllowElementId(dynamicSqlParam,"P_YMBJ2_ADDTABLE_P1_E01");
		
		// 自动赋值 ID
		String generateId = baseUtils.GetCodeRule("PAGE_NUM");
		Element element = new Element();
		element.setElementId("P_YMBJ2_ADDTABLE_P1_E01");
		element.setValue(generateId);
		dynamicSqlParam.getElements().add(element);
		
		// 唯一校验
		if(!ObjectUtils.isNull(setupPageTableMapper.retrieve((T) new SetupPageTable(null, false, dynamicSqlParam.getElementValue(tmp.get(0).getElementId()))))) throw new Exception("列ID已经存在，不能新增");

		return dynamicSqlService.executeInsert(dynamicSqlParam);
	}

	/**
	 * 不允许前端传递元素 ID 值，由后端统一赋值
	 * @param dynamicSqlParam
	 * @throws Exception 
	 */
	private void notAllowElementId(DynamicSqlParam<T> dynamicSqlParam, String elementId) throws Exception {
		List<Element> elements = dynamicSqlParam.getElements();
		if (ObjectUtils.isNull(elements)) {
			throw new Exception("elements 参数不能为空!");
		}
		for (Element element:elements) {
			if (element.getElementId().equals(elementId)) {
				throw new Exception("查询列表ID由后端统一生成，不允许手动指定!");
			}
		}
	}

	/**
	 * 统计查询列表下需要统计的列
	 */
	@SuppressWarnings("unchecked")
	@Override
	public LmisResult<?> getSetupPageTablesCount(DynamicSqlParam<T> dynamicSqlParam) throws Exception {		
        List<ViewSetupPageTable> data = redisHelperService.getSetupPageTables((ValueOperations<String, List<ViewSetupPageTable>>) redisTemplate.opsForValue(), dynamicSqlParam.getLayoutId());
		if(ObjectUtils.isNull(data)) throw new Exception("查无记录，数据异常");
		List<Map<String, String>> countList = new ArrayList<>();
		// 组装需要进行统计的列
		getNeedCountList(data, countList);
		List<Map<String, Object>> countResultList = new ArrayList<>();
		if (!ObjectUtils.isNull(countList)) {
			ViewSetupPageElement viewSetupPageElement = new ViewSetupPageElement();
			viewSetupPageElement.setLayoutId(dynamicSqlParam.getLayoutId());
			
			// 用于重新组装的查询条件
			List<Element> newElements = new ArrayList<>();
			
			// 构建新的
			buildNewParamElements(dynamicSqlParam, countList, newElements);
			
			dynamicSqlParam.setElements(JSON.toJSONString(newElements));
			LmisResult<?> result2 = dynamicSqlService.getWhereSql(dynamicSqlParam);
			if (LmisConstant.RESULT_CODE_ERROR.equals(result2.getCode()))
	            return result2;
			// 构建统计结果
			buildCountSQL(countList, countResultList, result2);
		}
		LmisResult<T> lmisResult = new LmisResult<T>();
		if (!ObjectUtils.isNull(countResultList)) {
			lmisResult.setData(countResultList);
		}
		lmisResult.setCode(LmisConstant.RESULT_CODE_SUCCESS);
		return lmisResult;
	}

	private void buildNewParamElements(DynamicSqlParam<T> dynamicSqlParam, List<Map<String, String>> countList,
			List<Element> newElements) {
		// 查找出对应的表或者视图对应的所有页面元素
		ConstantData constantData = new ConstantData();
		constantData.setCode("column_name");
		constantData.setValue1(countList.get(0).get("tableId"));
		LmisResult<ConstantData> constantDataResult = getConstantDataService.getElementValue(constantData);
		List<ConstantData> valueList = (List<ConstantData>) constantDataResult.getData();
		
		// 传递上来的参数值
		List<Element> elements = dynamicSqlParam.getElements();
		java.util.Set<String> constantSet = new HashSet<>();
		
		for (ConstantData constantDataItem : valueList) {
			constantSet.add(constantDataItem.getCode());
		}
		
		if (!ObjectUtils.isNull(elements)) {
			for (Element element : elements) {
				if (!ObjectUtils.isNull(element.getElementId()) && !ObjectUtils.isNull(element.getValue())) {
					SetupPageElement setupPageElement = new SetupPageElement();
					setupPageElement.setElementId(element.getElementId());
					List<SetupPageElement> setupPageElements =  setupPageElementMapper.retrieve(setupPageElement);
					if (!ObjectUtils.isNull(setupPageElements) && 
							constantSet.contains(setupPageElements.get(0).getColumnId())) {
						newElements.add(element);
					}
				}
			}
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void buildCountSQL(List<Map<String, String>> countList, List<Map<String, Object>> countResultList,
			LmisResult<?> result2) {
		for (int i = 0;i < countList.size(); i++) {
			Map<String, String> map = countList.get(i);
			StringBuffer query = new StringBuffer();
			query.append(" SELECT ");
			query.append(" " + map.get("countType") + "("+ map.get("tableColumnId") +") as value");
			query.append(" FROM ");
			query.append(" " + map.get("tableId") +  " ");
		    if (!ObjectUtils.isNull(result2.getData()))
		    	query.append(result2.getData().toString());
		    List<?> countResult = dynamicSqlMapper.executeSelect(query.toString()); 
			if (!ObjectUtils.isNull(countResult) && !ObjectUtils.isNull(countResult.get(0))) {
				Map<String, Object> item = new HashMap<>();
				item.put("name", map.get("countName"));
				Map<String,Object> value = (Map<String,Object>) countResult.get(0);
				item.put("value", value.get("value"));
				countResultList.add(item);
			}
		}
	}

	private void getNeedCountList(List<ViewSetupPageTable> data, List<Map<String, String>> countList) {
		Map<String, String> countMap = null;
		for(ViewSetupPageTable setupPageTable : data) {
			if (!ObjectUtils.isNull(setupPageTable.getCountType()) 
					&& !ObjectUtils.isNull(setupPageTable.getTableId())
					&& !ObjectUtils.isNull(setupPageTable.getTableColumnId())) {
				countMap  = new HashMap<>();
				// 需要统计的表
				countMap.put("tableId",setupPageTable.getTableId());
				// 需要统计的列
				countMap.put("tableColumnId",setupPageTable.getTableColumnId());
				// 需要展示的统计名称
				countMap.put("countName",setupPageTable.getCountName());
				// 需要统计的类型
				countMap.put("countType",setupPageTable.getCountType());
				countList.add(countMap);
			}
		}
	}

}
