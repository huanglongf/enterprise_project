package com.lmis.setup.pageElement.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lmis.common.dataFormat.ObjectUtils;
import com.lmis.common.dynamicSql.model.DynamicSqlParam;
import com.lmis.common.dynamicSql.model.Element;
import com.lmis.common.dynamicSql.service.DynamicSqlServiceInterface;
import com.lmis.common.util.BaseUtils;
import com.lmis.framework.baseInfo.LmisConstant;
import com.lmis.framework.baseInfo.LmisPageObject;
import com.lmis.framework.baseInfo.LmisResult;
import com.lmis.setup.pageElement.dao.SetupPageElementFrameworkMapper;
import com.lmis.setup.pageElement.dao.SetupPageElementMapper;
import com.lmis.setup.pageElement.model.SetupPageElement;
import com.lmis.setup.pageElement.model.ViewSetupPageElement;
import com.lmis.setup.pageElement.service.SetupPageElementServiceInterface;
import com.lmis.setup.pageLayout.dao.SetupPageLayoutMapper;
import com.lmis.setup.pageLayout.model.SetupPageLayout;
import com.lmis.setup.pageTable.model.ViewSetupPageTable;

/** 
 * @ClassName: SetupPageElementServiceImpl
 * @Description: TODO(页面元素业务层实现)
 * @author Ian.Huang
 * @date 2017年12月11日 上午11:33:09 
 * 
 * @param <T>
 */
@Transactional(rollbackFor=Exception.class)
@Service
public class SetupPageElementServiceImpl<T extends SetupPageElement> implements SetupPageElementServiceInterface<T> {

	// redis命名规则，元素部分的"setup:sql:element:"+表setup_page_element中字段element_id
	@Value("${redis.key.element}")
	String redisKeyElement;
		
	// redis命名规则，元素部分的"setup:sql:elements:"+表setup_page_element中字段layout_id
	@Value("${redis.key.elements}")
    String redisKeyElements;
	
	//数据库url
	@Value("${spring.datasource.url}")
    String springDatasourceUrl;
	
	@Autowired
    private RedisTemplate<?, ?> redisTemplate;
	@Autowired
	private SetupPageElementMapper<T> setupPageElementMapper;
	@Autowired
	private SetupPageElementFrameworkMapper<T> setupPageElementFrameworkMapper;
	@Autowired
	private SetupPageLayoutMapper<SetupPageLayout> setupPageLayoutMapper;
	@Resource(name="dynamicSqlServiceImpl")
	DynamicSqlServiceInterface<Map<String, Object>> dynamicSqlService;
	
	@Autowired
	private BaseUtils baseUtils;
	
	@Override
	public LmisResult<?> queryPage(ViewSetupPageElement viewSetupPageElement, LmisPageObject pageObject) throws Exception {
		Page<ViewSetupPageElement> page = PageHelper.startPage(pageObject.getPageNum(), pageObject.getPageSize());
		
		// PageHelper会自动拦截到下面这查询sql
		setupPageElementMapper.listSetupPageElementBySeq(viewSetupPageElement);
	
		// 封装返回页面参数对象
		LmisResult<ViewSetupPageElement> lmisResult = new LmisResult<ViewSetupPageElement>();
		lmisResult.setCode(LmisConstant.RESULT_CODE_SUCCESS);
		lmisResult.setMetaAndData(page.toPageInfo());
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
	 * @Title: checkSetupPageElement
	 * @Description: TODO(校验页面元素数据)
	 * @param t
	 * @throws Exception
	 * @return: void
	 * @author: Ian.Huang
	 * @date: 2018年1月2日 下午8:46:25
	 */
	private void checkSetupPageElement(T t) throws Exception {
		
		// 页面元素需存在页面布局中
		if(ObjectUtils.isNull(t.getLayoutId())) throw new Exception("页面元素需存在页面布局中");
		
		// 查询提交了的页面布局
		SetupPageLayout param = new SetupPageLayout();
		param.setIsDeleted(false);
		param.setLayoutId(t.getLayoutId());
		List<SetupPageLayout> currentLayout = setupPageLayoutMapper.retrieve(param);
		
		// 绑定页面元素的页面布局需存在
		if(ObjectUtils.isNull(currentLayout)) throw new Exception("绑定页面元素的页面布局需存在");
		
		// 绑定页面元素的页面布局只可能存在一条记录
		if(currentLayout.size() != 1) throw new Exception("绑定页面元素的页面布局只可能存在一条记录");
		
		// 绑定页面元素的页面布局需为元素布局
		if(!"layout_a".equals(currentLayout.get(0).getLayoutType())) throw new Exception("绑定页面元素的页面布局需为元素布局");
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public LmisResult<T> addSetupPageElement(T t) throws Exception {
			
		// 唯一校验
		if(!ObjectUtils.isNull(setupPageElementMapper.retrieve((T) new SetupPageElement(null, false, t.getElementId())))) throw new Exception("元素ID已经存在，不能新增");
	
		// 数据业务校验
		checkSetupPageElement(t);
	
		// 新增操作
		return new LmisResult<T>(LmisConstant.RESULT_CODE_SUCCESS, setupPageElementMapper.create(t));
	}

	@SuppressWarnings("unchecked")
	@Override
	public LmisResult<T> updateSetupPageElement(T t) throws Exception {
			
		// 存在校验
		if(ObjectUtils.isNull(setupPageElementMapper.retrieve((T) new SetupPageElement(null, false, t.getElementId())))) throw new Exception("元素ID不存在，不能更新");
	
		// 数据业务校验
		checkSetupPageElement(t);
	
		// 修改操作
		return new LmisResult<T>(LmisConstant.RESULT_CODE_SUCCESS, setupPageElementMapper.updateRecord(t));
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public LmisResult<?> executeUpdate(DynamicSqlParam<T> dynamicSqlParam) throws Exception {
		SetupPageElement param = new SetupPageElement();
		param.setIsDeleted(false);
		param.setLayoutId(dynamicSqlParam.getLayoutId());
		param.setTableId("setup_page_element");
		param.setColumnId("element_id");
		List<T> tmp = setupPageElementMapper.retrieve((T) param);
		if(ObjectUtils.isNull(tmp)) throw new Exception("无字段element_id对应记录，数据异常");
		if(tmp.size() != 1) throw new Exception("字段element_id存在多条记录，数据异常");
		
		// 存在校验
		if(ObjectUtils.isNull(setupPageElementMapper.retrieve((T) new SetupPageElement(null, false, dynamicSqlParam.getElementValue(tmp.get(0).getElementId()))))) throw new Exception("元素ID不存在，不能更新");
		return dynamicSqlService.executeUpdate(dynamicSqlParam);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public LmisResult<T> deleteSetupPageElement(T t) throws Exception {

		// 存在校验
		if(ObjectUtils.isNull(setupPageElementMapper.retrieve((T) new SetupPageElement(null, false, t.getElementId())))) throw new Exception("元素ID不存在，不能删除");
		
		// 删除操作
		return new LmisResult<T>(LmisConstant.RESULT_CODE_SUCCESS, setupPageElementMapper.logicalDelete(t));
	}

	@SuppressWarnings("unchecked")
	@Override
	public LmisResult<?> redisForPageElements() throws Exception {
		ViewSetupPageElement param = new ViewSetupPageElement();
		// 根据数据库链接截取数据库名称
		param.setTableSchema(springDatasourceUrl.substring(springDatasourceUrl.indexOf("3306/")+5, springDatasourceUrl.indexOf("?")));
		
		List<String> allValidateLayoutId = setupPageElementMapper.listAllValidateLayoutId();
		for(int i = 0; i < allValidateLayoutId.size(); i++) {
			param.setLayoutId(allValidateLayoutId.get(i));
			List<ViewSetupPageElement> setupPageElements = setupPageElementFrameworkMapper.listValidateSetupPageElement(param);
			if(!ObjectUtils.isNull(setupPageElements)) {
				((ValueOperations<String, List<ViewSetupPageElement>>)redisTemplate.opsForValue()).set(redisKeyElements + allValidateLayoutId.get(i), setupPageElements);
			}
		}
		param.setLayoutId(null);
		List<ViewSetupPageElement> setupPageElements = setupPageElementFrameworkMapper.listValidateSetupPageElement(param);
		for(int i = 0; i < setupPageElements.size(); i++) {
			((ValueOperations<String, ViewSetupPageElement>)redisTemplate.opsForValue()).set(redisKeyElement + setupPageElements.get(i).getElementId(), setupPageElements.get(i));
		}
		LmisResult<T> lmisResult = new LmisResult<T>();
		lmisResult.setCode(LmisConstant.RESULT_CODE_SUCCESS);
		return lmisResult;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public LmisResult<?> redisForPageElementsMset() throws Exception {
		ViewSetupPageElement param = new ViewSetupPageElement();
		// 根据数据库链接截取数据库名称
		param.setTableSchema(springDatasourceUrl.substring(springDatasourceUrl.indexOf("3306/")+5, springDatasourceUrl.indexOf("?")));
		
		List<String> allValidateLayoutId = setupPageElementMapper.listAllValidateLayoutId();
		param.setLayoutId(null);
		List<ViewSetupPageElement> setupPageElements = setupPageElementFrameworkMapper.listValidateSetupPageElementForSimple(param);
	    
		Map<String, List<ViewSetupPageElement>> elementsOfLayoutMap = new HashMap<>();
	    Map<String, ViewSetupPageElement> elementsOfNullLayoutIdMap = new HashMap<>();

	    // 解决循环内查询耗时太长问题
	    for(int i = 0; i < setupPageElements.size(); i++) {
	      ViewSetupPageElement viewSetupPageElement = setupPageElements.get(i);
	      String currentLayoutId = viewSetupPageElement.getLayoutId();
	      // 用于缓存有效布局元素
	      if (allValidateLayoutId.contains(currentLayoutId)) {
	    	String currentLayoutKey = redisKeyElements + currentLayoutId;
	        if (!elementsOfLayoutMap.containsKey(currentLayoutKey)) {
	          elementsOfLayoutMap.put(currentLayoutKey, new ArrayList<ViewSetupPageElement>());
	        }
	        elementsOfLayoutMap.get(currentLayoutKey).add(viewSetupPageElement);
	      }
	      elementsOfNullLayoutIdMap.put(redisKeyElement + viewSetupPageElement.getElementId(), viewSetupPageElement);
	    }
	    // 批量同步有效布局下的页面元素
	    ((ValueOperations<String, List<ViewSetupPageElement>>)redisTemplate.opsForValue()).multiSet(elementsOfLayoutMap);
	    // 批量同步元素
	    ((ValueOperations<String, ViewSetupPageElement>)redisTemplate.opsForValue()).multiSet(elementsOfNullLayoutIdMap);
		LmisResult<T> lmisResult = new LmisResult<T>();
		lmisResult.setCode(LmisConstant.RESULT_CODE_SUCCESS);
		return lmisResult;
	}

	@Override
	public LmisResult<T> getSetupPageElement(T t) throws Exception {
		List<T> result = setupPageElementMapper.retrieve(t);
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
		param.setTableId("setup_page_element");
		param.setColumnId("element_id");
		List<T> tmp = setupPageElementMapper.retrieve((T) param);
		if(ObjectUtils.isNull(tmp)) throw new Exception("无字段element_id对应记录，数据异常");
		if(tmp.size() != 1) throw new Exception("字段element_id存在多条记录，数据异常");
		
		// 数据业务校验
		checkSetupPageElement((T) tmp.get(0));
		
		// 不允许前端传递元素 ID
		notAllowElementId(dynamicSqlParam,"P_YMBJ2_ADDELE_P01_E01");
		
		String generateId = baseUtils.GetCodeRule("PAGE_NUM");
		Element element = new Element();
		element.setElementId("P_YMBJ2_ADDELE_P01_E01");
		element.setValue(generateId);
		dynamicSqlParam.getElements().add(element);
		
		// 唯一校验
		if(!ObjectUtils.isNull(setupPageElementMapper.retrieve((T) new SetupPageElement(null, false, dynamicSqlParam.getElementValue(tmp.get(0).getElementId()))))) throw new Exception("页面元素ID已经存在，不能新增");

		return dynamicSqlService.executeInsert(dynamicSqlParam);
	}

	/**
	 * 不允许前端传递元素 ID 值，由后端统一赋值
	 * @param dynamicSqlParam
	 * @throws Exception 
	 */
	private void notAllowElementId(DynamicSqlParam<T> dynamicSqlParam, String elementId) throws Exception {
		List<Element> elements = dynamicSqlParam.getElements();
		if (ObjectUtils.isNull(elements)) throw new Exception("参数 elements 不能为空，数据异常");
		for (Element element : elements) {
			if (element.getElementId().equals(elementId)) {
				throw new Exception("页面元素ID由后端统一生成，不允许手动指定!");
			}
		}
	}

}
