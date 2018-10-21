package com.lmis.basis.store.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.lmis.framework.baseDao.BaseMapper;
import com.lmis.basis.store.model.BasisStore;
import com.lmis.basis.store.model.ViewBasisStore;

/** 
 * @ClassName: BasisStoreMapper
 * @Description: TODO(店铺DAO映射接口)
 * @author codeGenerator
 * @date 2018-01-19 16:10:57
 * 
 * @param <T>
 */
@Mapper
@Repository
public interface BasisStoreMapper<T extends BasisStore> extends BaseMapper<T> {
	
	/**
	 * @Title: retrieveViewBasisStore
	 * @Description: TODO(查询view_basis_store)
	 * @param viewBasisStore
	 * @return: List<ViewBasisStore>
	 * @author: codeGenerator
	 * @date: 2018-01-19 16:10:57
	 */
	List<ViewBasisStore> retrieveViewBasisStore(ViewBasisStore viewBasisStore);
	
}
