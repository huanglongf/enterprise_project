package com.lmis.basis.costCenter.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.lmis.framework.baseDao.BaseMapper;
import com.lmis.basis.costCenter.model.BasisCostCenter;
import com.lmis.basis.costCenter.model.ViewBasisCostCenter;

/** 
 * @ClassName: BasisCostCenterMapper
 * @Description: TODO(成本中心DAO映射接口)
 * @author codeGenerator
 * @date 2018-01-31 15:43:21
 * 
 * @param <T>
 */
@Mapper
@Repository
public interface BasisCostCenterMapper<T extends BasisCostCenter> extends BaseMapper<T> {
	
	/**
	 * @Title: retrieveViewBasisCostCenter
	 * @Description: TODO(查询view_basis_cost_center)
	 * @param viewBasisCostCenter
	 * @return: List<ViewBasisCostCenter>
	 * @author: codeGenerator
	 * @date: 2018-01-31 15:43:21
	 */
	List<ViewBasisCostCenter> retrieveViewBasisCostCenter(ViewBasisCostCenter viewBasisCostCenter);
	
}
