package com.bt.lmis.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.lmis.code.ServiceSupport;
import com.bt.lmis.dao.PerationfeeDataMapper;
import com.bt.lmis.model.PerationfeeData;
import com.bt.lmis.model.PerationfeeDatas;
import com.bt.lmis.service.PerationfeeDataService;
@Service
public class PerationfeeDataServiceImpl<T> extends ServiceSupport<T> implements PerationfeeDataService<T> {

	@Autowired
    private PerationfeeDataMapper<T> mapper;

	public PerationfeeDataMapper<T> getMapper() {
		return mapper;
	}

	@Override
	public Long selectCount(Map<String, Object> param) throws Exception {
		return mapper.selectCount(param);
	}

	@Override
	public List<PerationfeeData>  findByEntityGroupBySKU(PerationfeeData entity) {
		return mapper.findByEntityGroupBySKU(entity);
	}

	@Override
	public List<PerationfeeData> findByEntity(PerationfeeData entity) {
		return mapper.findByEntity(entity);
	}

	@Override
	public List<PerationfeeDatas> findByEntityGroupByOutNum(PerationfeeData entity) {
		return mapper.findByEntityGroupByOutNum(entity);
	}

	@Override
	public void updates(PerationfeeDatas perationfeeDatas) {
		mapper.updates(perationfeeDatas);
	}

	@Override
	public List<PerationfeeData> findByEntityOrderNo(PerationfeeData entity) {
		return mapper.findByEntityOrderNo(entity);
	}


	@Override
	public List<PerationfeeData> findByEntityGroupBySKUYM(String job_type, String store_name, String yy, String mm) {
		return mapper.findByEntityGroupBySKUYM(job_type, store_name, yy, mm);
	}

	@Override
	synchronized public void update_settleflag(PerationfeeData perationfeeData) {
		mapper.update_settleflag(perationfeeData);
	}

	@Override
	public void update_settleflags(PerationfeeDatas perationfeeData) {
		mapper.update_settleflags(perationfeeData);
	}

	@Override
	public List<PerationfeeData> findByEntityGroupBySKUSE(String job_type, String store_name, String fromDate,
			String toDate,Integer firstResult,Integer maxResult) {
		// TODO Auto-generated method stub
		return mapper.findByEntityGroupBySKUSE(job_type,store_name,fromDate,toDate,firstResult,maxResult);
	}

	@Override
	public List<PerationfeeDatas> findByEntityGroupByOutNumSE(PerationfeeData entity) {
		// TODO Auto-generated method stub
		return mapper.findByEntityGroupByOutNumSE(entity);
	}

	@Override
	public List<PerationfeeData> findByEntitySE(PerationfeeData entity) {
		// TODO Auto-generated method stub
		return mapper.findByEntitySE(entity);
	}

	@Override
	public List<PerationfeeData> findByEntityOrderNoSE(PerationfeeData entity) {
		// TODO Auto-generated method stub
		return mapper.findByEntityOrderNoSE(entity);
	}

	@Override
	public List<PerationfeeData> findByEntityGroupBySKUStartEnd(PerationfeeData entity) {
		// TODO Auto-generated method stub
		return mapper.findByEntityGroupBySKUStartEnd(entity);
	}

	@Override
	public int countByEntitySE(PerationfeeData entity) {
		// TODO Auto-generated method stub
		return mapper.countByEntitySE(entity);
	}

	@Override
	public int countByEntityOrderNoSE(PerationfeeData entity) {
		// TODO Auto-generated method stub
		return mapper.countByEntityOrderNoSE(entity);
	}

	@Override
	public int countByEntityGroupBySKUStartEnd(PerationfeeData entity) {
		// TODO Auto-generated method stub
		return mapper.countByEntityGroupBySKUStartEnd(entity);
	}

	@Override
	public int countByEntityGroupBySKUSE(String job_type, String store_name, String fromDate, String toDate) {
		// TODO Auto-generated method stub
		return mapper.countByEntityGroupBySKUSE(job_type,store_name, fromDate, toDate);
	}

	@Override
	public int countByEntityGroupByOutNumSE(PerationfeeData entity) {
		// TODO Auto-generated method stub
		return mapper.countByEntityGroupByOutNumSE(entity);
	}

	@Override
	public BigDecimal sumB2COutboundNum(PerationfeeData entity) {
		// TODO Auto-generated method stub
		return mapper.sumB2COutboundNum(entity);
	}
		
	
}
