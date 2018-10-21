package com.lmis.common.dynamicSql.service;

import java.util.List;

import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lmis.setup.pageElement.model.ViewSetupPageElement;
import com.lmis.setup.pageTable.model.ViewSetupPageTable;

/** 
 * @ClassName: RedisHelperServiceInterface
 * @Description: TODO(redis工具业务层接口)
 * @author Ian.Huang
 * @date 2018年1月12日 上午10:51:31 
 * 
 * @param <T>
 */
@Transactional(rollbackFor=Exception.class)
@Service
public interface RedisHelperServiceInterface<T> {

	/**
	 * @Title: getSetupPageElement
	 * @Description: TODO(获取element_id对应数据，判断redis中是否存在element_id对应数据，不存在则从数据库查出存入缓存)
	 * @param vo
	 * @param elementId
	 * @throws Exception
	 * @return: ViewSetupPageElement
	 * @author: Ian.Huang
	 * @date: 2018年1月12日 上午10:54:53
	 */
	ViewSetupPageElement getSetupPageElement(ValueOperations<String, ViewSetupPageElement> vo, String elementId) throws Exception;
	
	/**
	 * @Title: getSetupPageElements
	 * @Description: TODO(根据layoutId获取redis缓存中对应elements集合，如不存在则从数据库中查出并加入缓存)
	 * @param vo
	 * @param layoutId
	 * @throws Exception
	 * @return: List<ViewSetupPageElement>
	 * @author: Ian.Huang
	 * @date: 2018年1月12日 上午10:52:36
	 */
	List<ViewSetupPageElement> getSetupPageElements(ValueOperations<String, List<ViewSetupPageElement>> vo, String layoutId) throws Exception;
	
	/**
	 * @Title: getSetupPageTables
	 * @Description: TODO(根据layoutId获取redis缓存中对应columns集合，如不存在则从数据库中查出并加入缓存)
	 * @param vo
	 * @param layoutId
	 * @throws Exception
	 * @return: List<ViewSetupPageTable>
	 * @author: Ian.Huang
	 * @date: 2018年1月12日 上午11:03:21
	 */
	List<ViewSetupPageTable> getSetupPageTables(ValueOperations<String, List<ViewSetupPageTable>> vo, String layoutId) throws Exception;

}
