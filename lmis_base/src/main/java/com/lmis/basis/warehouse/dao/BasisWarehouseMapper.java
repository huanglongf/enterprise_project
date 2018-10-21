package com.lmis.basis.warehouse.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.lmis.framework.baseDao.BaseMapper;
import com.lmis.basis.warehouse.model.BasisWarehouse;
import com.lmis.basis.warehouse.model.ViewBasisWarehouse;

/** 
 * @ClassName: BasisWarehouseMapper
 * @Description: TODO(仓库DAO映射接口)
 * @author codeGenerator
 * @date 2018-01-19 15:05:00
 * 
 * @param <T>
 */
@Mapper
@Repository
public interface BasisWarehouseMapper<T extends BasisWarehouse> extends BaseMapper<T> {
	
	/**
	 * @Title: retrieveViewBasisWarehouse
	 * @Description: TODO(查询view_basis_warehouse)
	 * @param viewBasisWarehouse
	 * @return: List<ViewBasisWarehouse>
	 * @author: codeGenerator
	 * @date: 2018-01-19 15:05:00
	 */
	List<ViewBasisWarehouse> retrieveViewBasisWarehouse(ViewBasisWarehouse viewBasisWarehouse);
	
}
