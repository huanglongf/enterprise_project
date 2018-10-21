package com.bt.lmis.service;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bt.lmis.code.BaseService;
import com.bt.lmis.model.PerationfeeData;
import com.bt.lmis.model.PerationfeeDatas;

public interface PerationfeeDataService<T> extends BaseService<T> {
	List<PerationfeeData>  findByEntityGroupBySKU(PerationfeeData entity);
	List<PerationfeeData>  findByEntityGroupBySKUYM(@Param("job_type")String job_type,
			@Param("store_name")String store_name,@Param("yy")String yy,@Param("mm")String mm);
	List<PerationfeeDatas>  findByEntityGroupByOutNum(PerationfeeData entity);
	List<PerationfeeData> findByEntity(PerationfeeData entity);
	List<PerationfeeData> findByEntityOrderNo(PerationfeeData entity);
	public void updates(PerationfeeDatas perationfeeDatas);
	public void update_settleflag(PerationfeeData perationfeeData);
	public void update_settleflags(PerationfeeDatas perationfeeData);
	List<PerationfeeData> findByEntityGroupBySKUSE(@Param("job_type")String job_type,
			@Param("store_name")String store_name, @Param("fromDate")String fromDate, 
			@Param("toDate")String toDate, @Param("firstResult")Integer firstResult, @Param("maxResult")Integer maxResult);
	List<PerationfeeDatas> findByEntityGroupByOutNumSE(PerationfeeData entity);
	List<PerationfeeData> findByEntitySE(PerationfeeData entity);
	List<PerationfeeData> findByEntityOrderNoSE(PerationfeeData entity);
	List<PerationfeeData> findByEntityGroupBySKUStartEnd(PerationfeeData entity);
	int countByEntitySE(PerationfeeData entity);
	int countByEntityOrderNoSE(PerationfeeData entity);
	int countByEntityGroupBySKUStartEnd(PerationfeeData entity);
	int countByEntityGroupBySKUSE(String string, String store_name, String fromDate, String toDate);
	int countByEntityGroupByOutNumSE(PerationfeeData entity);
	BigDecimal sumB2COutboundNum(PerationfeeData entity);
}
