package com.lmis.basis.currency.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.lmis.framework.baseDao.BaseMapper;
import com.lmis.basis.currency.model.BasisCurrency;
import com.lmis.basis.currency.model.ViewBasisCurrency;

/** 
 * @ClassName: BasisCurrencyMapper
 * @Description: TODO(货币DAO映射接口)
 * @author codeGenerator
 * @date 2018-01-18 14:46:24
 * 
 * @param <T>
 */
@Mapper
@Repository
public interface BasisCurrencyMapper<T extends BasisCurrency> extends BaseMapper<T> {
	
	/**
	 * @Title: retrieveViewBasisCurrency
	 * @Description: TODO(查询view_basis_currency)
	 * @param viewBasisCurrency
	 * @return: List<ViewBasisCurrency>
	 * @author: codeGenerator
	 * @date: 2018-01-18 14:46:24
	 */
	List<ViewBasisCurrency> retrieveViewBasisCurrency(ViewBasisCurrency viewBasisCurrency);
	
}
