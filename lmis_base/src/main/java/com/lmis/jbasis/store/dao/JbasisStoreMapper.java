package com.lmis.jbasis.store.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.lmis.framework.baseDao.BaseMapper;
import com.lmis.jbasis.store.model.JbasisStore;
import com.lmis.jbasis.store.model.ViewJbasisStore;

/** 
 * @ClassName: JbasisStoreMapper
 * @Description: TODO(店铺信息（基础数据）DAO映射接口)
 * @author codeGenerator
 * @date 2018-04-13 10:22:24
 * 
 * @param <T>
 */
@Mapper
@Repository
public interface JbasisStoreMapper<T extends JbasisStore> extends BaseMapper<T> {
	
	/**
	 * @Title: retrieveViewJbasisStore
	 * @Description: TODO(查询view_jbasis_store)
	 * @param viewJbasisStore
	 * @return: List<ViewJbasisStore>
	 * @author: codeGenerator
	 * @date: 2018-04-13 10:22:24
	 */
	List<ViewJbasisStore> retrieveViewJbasisStore(ViewJbasisStore viewJbasisStore);
	
}
