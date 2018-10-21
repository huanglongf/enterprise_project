package com.lmis.basis.fixedAssetsDetail.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.lmis.framework.baseDao.BaseMapper;
import com.lmis.basis.fixedAssetsDetail.model.BasisFixedAssetsDetail;
import com.lmis.basis.fixedAssetsDetail.model.ViewBasisFixedAssetsDetail;

/** 
 * @ClassName: BasisFixedAssetsDetailMapper
 * @Description: TODO(固定资产明细DAO映射接口)
 * @author codeGenerator
 * @date 2018-03-23 17:12:34
 * 
 * @param <T>
 */
@Mapper
@Repository
public interface BasisFixedAssetsDetailMapper<T extends BasisFixedAssetsDetail> extends BaseMapper<T> {
	
	/**
	 * @Title: retrieveViewBasisFixedAssetsDetail
	 * @Description: TODO(查询view_basis_fixed_assets_detail)
	 * @param viewBasisFixedAssetsDetail
	 * @return: List<ViewBasisFixedAssetsDetail>
	 * @author: codeGenerator
	 * @date: 2018-03-23 17:12:34
	 */
	List<ViewBasisFixedAssetsDetail> retrieveViewBasisFixedAssetsDetail(ViewBasisFixedAssetsDetail viewBasisFixedAssetsDetail);

	List<Map<String,Object>> selectBasisFixedAssetsDetail(ViewBasisFixedAssetsDetail detail);
	
	Integer getCountByAssetsCodeLike(@Param("assetsCode")String assetsCode);
	
}
