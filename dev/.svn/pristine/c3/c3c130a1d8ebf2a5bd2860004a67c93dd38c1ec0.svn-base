package com.bt.lmis.basis.service;

import java.util.List;
import java.util.Map;

import com.bt.common.controller.param.Parameter;
import com.bt.common.model.ResultMessage;
import com.bt.lmis.basis.model.Store;
import com.bt.lmis.controller.form.StoreQueryParam;
import com.bt.lmis.page.QueryResult;

import javax.servlet.http.HttpServletRequest;

/** 
 * @ClassName: StoreManagerService
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Ian.Huang
 * @date 2017年7月31日 下午1:14:46 
 * 
 * @param <T>
 */
public interface StoreManagerService<T> {
	
	/**
	 * @Title: listStoreView
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param paremeter
	 * @return: QueryResult<Map<String,Object>>
	 * @author: Ian.Huang
	 * @date: 2017年7月31日 下午4:19:02
	 */
	QueryResult<Map<String,Object>> listStoreView(Parameter paremeter);
	
	/**
	 * @Title: getStore
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param id
	 * @return: Store
	 * @author: Ian.Huang
	 * @date: 2017年8月1日 下午1:26:55
	 */
	Store getStore(int id);
	
	/**
	 * @Title: save
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param parameter
	 * @return: ResultMessage
	 * @author: Ian.Huang
	 * @date: 2017年8月1日 下午3:35:57
	 */
	ResultMessage save(Parameter parameter);
	
	/**
	 * @Title: del
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param parameter
	 * @return: ResultMessage
	 * @author: Ian.Huang
	 * @date: 2017年8月1日 下午6:45:24
	 */
	ResultMessage del(Parameter parameter);
	
	/**
	 * 根据name查询store
	 * @param storeName
	 * @return
	 */
	Store selectByStroeName(String storeName);

	List<Map<String, Object>> findAll();

	Store selectByStoreCode(String storeCode);

	List<Map<String, Object>> download(Parameter paremeter);

	public  void getSomeParams(HttpServletRequest request);

	int addStoreTransport(HttpServletRequest request,String transportCodes,String storeCode,String storebj);
	
}
