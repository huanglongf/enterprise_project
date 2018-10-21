package com.lmis.basis.fixedAssetsAmt.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.lmis.basis.fixedAssetsAmt.model.BasisFixedAssetsAmt;
import com.lmis.basis.fixedAssetsAmt.model.ViewBasisFixedAssetsAmt;
import com.lmis.framework.baseDao.BaseMapper;

/** 
 * @ClassName: BasisFixedAssetsAmtMapper
 * @Description: TODO(固定资产摊销DAO映射接口)
 * @author codeGenerator
 * @date 2018-02-26 16:49:11
 * 
 * @param <T>
 */
@Mapper
@Repository
public interface BasisFixedAssetsAmtMapper<T extends BasisFixedAssetsAmt> extends BaseMapper<T> {
	
	/**
	 * @Title: retrieveViewBasisFixedAssetsAmt
	 * @Description: TODO(查询view_basis_fixed_assets_amt)
	 * @param viewBasisFixedAssetsAmt
	 * @return: List<ViewBasisFixedAssetsAmt>
	 * @author: codeGenerator
	 * @date: 2018-02-26 16:49:11
	 */
	List<ViewBasisFixedAssetsAmt> retrieveViewBasisFixedAssetsAmt(ViewBasisFixedAssetsAmt viewBasisFixedAssetsAmt);

	int updateByAssetsCode(BasisFixedAssetsAmt basisFixedAssetsAmt);

	List<BasisFixedAssetsAmt> selectByTimeValue(BasisFixedAssetsAmt basisFixedAssetsAmt);

	List<Map<String,Object>> findAllAmtOrg(@Param("assetsCode") String assetsCode);
	
}
