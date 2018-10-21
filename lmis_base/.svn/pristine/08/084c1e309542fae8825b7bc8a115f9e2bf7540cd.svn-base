package com.lmis.basis.exchangeRate.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.lmis.framework.baseDao.BaseMapper;
import com.lmis.basis.exchangeRate.model.BasisExchangeRate;
import com.lmis.basis.exchangeRate.model.ViewBasisExchangeRate;

/** 
 * @ClassName: BasisExchangeRateMapper
 * @Description: TODO(汇率DAO映射接口)
 * @author codeGenerator
 * @date 2018-01-18 15:00:45
 * 
 * @param <T>
 */
@Mapper
@Repository
public interface BasisExchangeRateMapper<T extends BasisExchangeRate> extends BaseMapper<T> {
	
	/**
	 * @Title: retrieveViewBasisExchangeRate
	 * @Description: TODO(查询view_basis_exchange_rate)
	 * @param viewBasisExchangeRate
	 * @return: List<ViewBasisExchangeRate>
	 * @author: codeGenerator
	 * @date: 2018-01-18 15:00:45
	 */
	List<ViewBasisExchangeRate> retrieveViewBasisExchangeRate(ViewBasisExchangeRate viewBasisExchangeRate);
	
}
