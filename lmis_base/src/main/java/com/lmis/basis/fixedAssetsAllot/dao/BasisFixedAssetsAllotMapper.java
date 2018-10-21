package com.lmis.basis.fixedAssetsAllot.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.lmis.basis.fixedAssetsAllot.model.BasisFixedAssetsAllot;
import com.lmis.basis.fixedAssetsAllot.model.ViewBasisFixedAssetsAllot;
import com.lmis.framework.baseDao.BaseMapper;

/** 
 * @ClassName: BasisFixedAssetsAllotMapper
 * @Description: TODO(固定资产调拨日志DAO映射接口)
 * @author codeGenerator
 * @date 2018-04-02 10:47:58
 * 
 * @param <T>
 */
@Mapper
@Repository
public interface BasisFixedAssetsAllotMapper<T extends BasisFixedAssetsAllot> extends BaseMapper<T> {
	
	/**
	 * @Title: retrieveViewBasisFixedAssetsAllot
	 * @Description: TODO(查询view_basis_fixed_assets_allot)
	 * @param viewBasisFixedAssetsAllot
	 * @return: List<ViewBasisFixedAssetsAllot>
	 * @author: codeGenerator
	 * @date: 2018-04-02 10:47:58
	 */
	List<ViewBasisFixedAssetsAllot> retrieveViewBasisFixedAssetsAllot(ViewBasisFixedAssetsAllot viewBasisFixedAssetsAllot);

	List<Map<String,Object>>  selectBasisFixedAssetsAllot(ViewBasisFixedAssetsAllot allot);
	
}
