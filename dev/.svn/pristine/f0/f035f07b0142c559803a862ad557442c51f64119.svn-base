package com.bt.lmis.dao;
import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bt.lmis.code.BaseMapper;
import com.bt.lmis.model.PerationfeeData;
import com.bt.lmis.model.PerationfeeDatas;

/**
* @ClassName: PerationfeeDataMapper
* @Description: TODO(PerationfeeDataMapper)
* @author Yuriy.Jiang
*
* @param <T>
*/ 
public interface PerationfeeDataMapper<T> extends BaseMapper<T> {

	List<PerationfeeData> findByEntityGroupBySKU(PerationfeeData entity);

	List<PerationfeeData> findByEntity(PerationfeeData entity);
	
	List<PerationfeeDatas>  findByEntityGroupByOutNum(PerationfeeData entity);
	
	List<PerationfeeData> findByEntityOrderNo(PerationfeeData entity);
	
	public void updates(PerationfeeDatas perationfeeDatas);

	List<PerationfeeData>  findByEntityGroupBySKUYM(@Param("job_type")String job_type,
			@Param("store_name")String store_name,@Param("yy")String yy,@Param("mm")String mm);

	public void update_settleflag(PerationfeeData perationfeeData);
	
	public void update_settleflag_zero(PerationfeeData perationfeeData);

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

	int countByEntityGroupBySKUSE(@Param("job_type")String job_type,
			@Param("store_name")String store_name, @Param("fromDate")String fromDate,
			@Param("toDate")String toDate);

	int countByEntityGroupByOutNumSE(PerationfeeData entity);

	BigDecimal sumB2COutboundNum(PerationfeeData entity);
}
