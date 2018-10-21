package com.bt.lmis.balance.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bt.lmis.balance.model.OperationfeeDataDetail;
import com.bt.lmis.code.BaseService;
import com.bt.lmis.controller.form.OperationfeeDataDetailQueryParam;
import com.bt.lmis.page.QueryResult;

public interface OperationfeeDataDetailService<T> extends BaseService<T> {
	List<OperationfeeDataDetail>  findByEntityGroupBySKU(OperationfeeDataDetail entity);
	List<OperationfeeDataDetail>  findByEntityGroupBySKUYM(@Param("job_type")String job_type,
			@Param("store_name")String store_name,@Param("yy")String yy,@Param("mm")String mm);
	List<OperationfeeDataDetail>  findByEntityGroupByOutNum(OperationfeeDataDetail entity);
	List<OperationfeeDataDetail> findByEntity(OperationfeeDataDetail entity);
	List<OperationfeeDataDetail> findByEntityOrderNo(OperationfeeDataDetail entity);
	public void updates(OperationfeeDataDetail OperationfeeDataDetails);
	public void update_settleflag(OperationfeeDataDetail OperationfeeDataDetail);
	public void update_settleflags(OperationfeeDataDetail OperationfeeDataDetail);
	List<OperationfeeDataDetail> findByEntityGroupBySKUSE(@Param("job_type")String job_type,
			@Param("store_name")String store_name, @Param("fromDate")String fromDate, 
			@Param("toDate")String toDate, @Param("firstResult")Integer firstResult, @Param("maxResult")Integer maxResult);
	List<OperationfeeDataDetail> findByEntityGroupByOutNumSE(OperationfeeDataDetail entity);
	List<OperationfeeDataDetail> findByEntitySE(OperationfeeDataDetail entity);
	List<OperationfeeDataDetail> findByEntityOrderNoSE(OperationfeeDataDetail entity);
	List<OperationfeeDataDetail> findByEntityGroupBySKUStartEnd(OperationfeeDataDetail entity);
	int countByEntitySE(OperationfeeDataDetail entity);
	int countByEntityOrderNoSE(OperationfeeDataDetail entity);
	int countByEntityGroupBySKUStartEnd(OperationfeeDataDetail entity);
	int countByEntityGroupBySKUSE(String string, String store_name, String fromDate, String toDate);
	int countByEntityGroupByOutNumSE(OperationfeeDataDetail entity);
	
	QueryResult<Map<String, String>>  getPageData(OperationfeeDataDetailQueryParam entity);
	List<Map<String, Object>> getListMap(OperationfeeDataDetailQueryParam queryParam);
}
