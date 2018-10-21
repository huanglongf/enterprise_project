package com.lmis.common.dynamicSql.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lmis.common.dataFormat.ObjectUtils;
import com.lmis.common.dynamicSql.service.RedisHelperServiceInterface;
import com.lmis.setup.pageElement.dao.SetupPageElementFrameworkMapper;
import com.lmis.setup.pageElement.model.SetupPageElement;
import com.lmis.setup.pageElement.model.ViewSetupPageElement;
import com.lmis.setup.pageTable.dao.SetupPageTableFrameworkMapper;
import com.lmis.setup.pageTable.model.SetupPageTable;
import com.lmis.setup.pageTable.model.ViewSetupPageTable;

/** 
 * @ClassName: DynamicSqlServiceImpl
 * @Description: TODO(动态SQL业务层实现类)
 * @author Ian.Huang
 * @date 2017年12月19日 下午3:15:53 
 * 
 */
@Transactional(rollbackFor=Exception.class)
@Service
public class RedisHelperServiceImpl<T> implements RedisHelperServiceInterface<T> {

	// redis命名规则，元素部分的"setup:sql:element:"+表setup_page_element中字段element_id
	@Value("${redis.key.element}")
    String redisKeyElement;
	
	// redis命名规则，元素部分的"setup:sql:elements:"+表setup_page_element中字段layout_id
	@Value("${redis.key.elements}")
    String redisKeyElements;
	
	// redis命名规则，元素部分的"setup:sql:column:"+表setup_page_table中字段layout_id
	@Value("${redis.key.column}")
    String redisKeyColumn;
	
	//数据库url
	@Value("${spring.datasource.url}")
    String springDatasourceUrl;
	
	@Autowired
	private SetupPageElementFrameworkMapper<SetupPageElement> setupPageElementFrameworkMapper;
	@Autowired
	private SetupPageTableFrameworkMapper<SetupPageTable> setupPageTableFrameworkMapper;
	
	@Override
	public ViewSetupPageElement getSetupPageElement(ValueOperations<String, ViewSetupPageElement> vo, String elementId)
			throws Exception {
		
		// 获取redis中element对象
		ViewSetupPageElement setupPageElement = vo.get(redisKeyElement + elementId);
		if(ObjectUtils.isNull(setupPageElement)) {
			
			// 如redis不存在此对象，则查询数据库
			ViewSetupPageElement param = new ViewSetupPageElement();
			
			// 根据数据库链接截取数据库名称
			param.setTableSchema(springDatasourceUrl.substring(springDatasourceUrl.indexOf("3306/")+5, springDatasourceUrl.indexOf("?")));
			param.setElementId(elementId);
			List<ViewSetupPageElement> tmp = setupPageElementFrameworkMapper.listValidateSetupPageElement(param);
			
			// 元素ID数据是否存在
			if(ObjectUtils.isNull(tmp)) throw new Exception("元素ID【" + elementId + "】对应有效页面元素不存在");
			
			// 元素ID不能重复
			if(tmp.size() != 1) throw new Exception("元素ID【" + elementId + "】不能重复，数据异常");
			
			// 数据存入redis
			vo.set(redisKeyElement + elementId, tmp.get(0));
			setupPageElement = tmp.get(0);
		}
		return setupPageElement;
	}
	
	@Override
	public List<ViewSetupPageElement> getSetupPageElements(ValueOperations<String, List<ViewSetupPageElement>> vo,
			String layoutId) throws Exception {
		List<ViewSetupPageElement> setupPageElements = vo.get(redisKeyElements + layoutId);
		if(ObjectUtils.isNull(setupPageElements)) {
			ViewSetupPageElement param = new ViewSetupPageElement();
			
			// 根据数据库链接截取数据库名称
			param.setTableSchema(springDatasourceUrl.substring(springDatasourceUrl.indexOf("3306/")+5, springDatasourceUrl.indexOf("?")));
			param.setLayoutId(layoutId);
			setupPageElements = setupPageElementFrameworkMapper.listValidateSetupPageElement(param);
			
			// 元素ID数据是否存在
			if(ObjectUtils.isNull(setupPageElements)) throw new Exception("布局ID【" + layoutId + "】对应有效页面元素集合不存在");
			vo.set(redisKeyElements + layoutId, setupPageElements);
		}
		return setupPageElements;
	}

	@Override
	public List<ViewSetupPageTable> getSetupPageTables(ValueOperations<String, List<ViewSetupPageTable>> vo,
			String layoutId) throws Exception {
		List<ViewSetupPageTable> setupPageTables = vo.get(redisKeyColumn + layoutId);
		if(ObjectUtils.isNull(setupPageTables)) {
			ViewSetupPageTable param = new ViewSetupPageTable();
			param.setLayoutId(layoutId);
			setupPageTables = setupPageTableFrameworkMapper.listValidateSetupPageTable(param);
			if(!ObjectUtils.isNull(setupPageTables)) vo.set(redisKeyColumn + layoutId, setupPageTables);
		}
		return setupPageTables;
	}

}
