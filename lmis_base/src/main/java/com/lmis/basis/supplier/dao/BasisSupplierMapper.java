package com.lmis.basis.supplier.dao;

import com.lmis.basis.supplier.model.BasisSupplier;
import com.lmis.basis.supplier.model.ViewBasisSupplier;
import com.lmis.framework.baseDao.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;


/** 
 * @ClassName: BasisSupplierMapper
 * @Description: TODO(供应商DAO映射接口)
 * @author codeGenerator
 * @date 2018-03-12 14:22:46
 * 
 * @param <T>
 */
@Mapper
@Repository
public interface BasisSupplierMapper<T extends BasisSupplier> extends BaseMapper<T> {
	
	/**
	 * @Title: retrieveViewBasisSupplier
	 * @Description: TODO(查询view_basis_supplier)
	 * @param viewBasisSupplier
	 * @return: List<ViewBasisSupplier>
	 * @author: codeGenerator
	 * @date: 2018-03-12 14:22:46
	 */
	List<ViewBasisSupplier> retrieveViewBasisSupplier(ViewBasisSupplier viewBasisSupplier);
	
}
