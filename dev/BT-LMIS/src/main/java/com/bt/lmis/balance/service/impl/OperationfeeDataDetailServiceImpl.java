package com.bt.lmis.balance.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.lmis.balance.dao.OperationfeeDataDetailMapper;
import com.bt.lmis.balance.model.OperationfeeDataDetail;
import com.bt.lmis.balance.service.OperationfeeDataDetailService;
import com.bt.lmis.code.ServiceSupport;
import com.bt.lmis.controller.form.OperationfeeDataDetailQueryParam;
import com.bt.lmis.controller.form.StorageExpendituresOriginalDataQueryParam;
import com.bt.lmis.page.QueryResult;
@Service
public class OperationfeeDataDetailServiceImpl<T> extends ServiceSupport<T> implements OperationfeeDataDetailService<T> {

	@Autowired
    private OperationfeeDataDetailMapper<T> mapper;


	@Override
	public Long selectCount(Map<String, Object> param) throws Exception {
		return mapper.selectCount(param);
	}

	@Override
	public List<OperationfeeDataDetail>  findByEntityGroupBySKU(OperationfeeDataDetail entity) {
		return mapper.findByEntityGroupBySKU(entity);
	}

	@Override
	public List<OperationfeeDataDetail> findByEntity(OperationfeeDataDetail entity) {
		return mapper.findByEntity(entity);
	}

	@Override
	public List<OperationfeeDataDetail> findByEntityGroupByOutNum(OperationfeeDataDetail entity) {
		return mapper.findByEntityGroupByOutNum(entity);
	}

	@Override
	public void updates(OperationfeeDataDetail OperationfeeDataDetails) {
		mapper.updates(OperationfeeDataDetails);
	}

	@Override
	public List<OperationfeeDataDetail> findByEntityOrderNo(OperationfeeDataDetail entity) {
		return mapper.findByEntityOrderNo(entity);
	}


	@Override
	public List<OperationfeeDataDetail> findByEntityGroupBySKUYM(String job_type, String store_name, String yy, String mm) {
		return mapper.findByEntityGroupBySKUYM(job_type, store_name, yy, mm);
	}

	@Override
	public void update_settleflag(OperationfeeDataDetail OperationfeeDataDetail) {
		mapper.update_settleflag(OperationfeeDataDetail);
	}

	@Override
	public void update_settleflags(OperationfeeDataDetail OperationfeeDataDetail) {
		mapper.update_settleflags(OperationfeeDataDetail);
	}

	@Override
	public List<OperationfeeDataDetail> findByEntityGroupBySKUSE(String job_type, String store_name, String fromDate,
			String toDate,Integer firstResult,Integer maxResult) {
		// TODO Auto-generated method stub
		return mapper.findByEntityGroupBySKUSE(job_type,store_name,fromDate,toDate,firstResult,maxResult);
	}

	@Override
	public List<OperationfeeDataDetail> findByEntityGroupByOutNumSE(OperationfeeDataDetail entity) {
		// TODO Auto-generated method stub
		return mapper.findByEntityGroupByOutNumSE(entity);
	}

	@Override
	public List<OperationfeeDataDetail> findByEntitySE(OperationfeeDataDetail entity) {
		// TODO Auto-generated method stub
		return mapper.findByEntitySE(entity);
	}

	@Override
	public List<OperationfeeDataDetail> findByEntityOrderNoSE(OperationfeeDataDetail entity) {
		// TODO Auto-generated method stub
		return mapper.findByEntityOrderNoSE(entity);
	}

	@Override
	public List<OperationfeeDataDetail> findByEntityGroupBySKUStartEnd(OperationfeeDataDetail entity) {
		// TODO Auto-generated method stub
		return mapper.findByEntityGroupBySKUStartEnd(entity);
	}

	@Override
	public int countByEntitySE(OperationfeeDataDetail entity) {
		// TODO Auto-generated method stub
		return mapper.countByEntitySE(entity);
	}

	@Override
	public int countByEntityOrderNoSE(OperationfeeDataDetail entity) {
		// TODO Auto-generated method stub
		return mapper.countByEntityOrderNoSE(entity);
	}

	@Override
	public int countByEntityGroupBySKUStartEnd(OperationfeeDataDetail entity) {
		// TODO Auto-generated method stub
		return mapper.countByEntityGroupBySKUStartEnd(entity);
	}

	@Override
	public int countByEntityGroupBySKUSE(String job_type, String store_name, String fromDate, String toDate) {
		// TODO Auto-generated method stub
		return mapper.countByEntityGroupBySKUSE(job_type,store_name, fromDate, toDate);
	}

	@Override
	public int countByEntityGroupByOutNumSE(OperationfeeDataDetail entity) {
		// TODO Auto-generated method stub
		return mapper.countByEntityGroupByOutNumSE(entity);
	}


	@Override
	public int save(T entity) throws Exception {
		return mapper.insert(entity);
	}

	@Override
	public QueryResult<Map<String, String>> getPageData(OperationfeeDataDetailQueryParam entity) {
		// TODO Auto-generated method stub
		QueryResult<Map<String, String>> qr=new QueryResult<Map<String, String>>();	
		qr.setResultlist(mapper.getPageData(entity));
		qr.setTotalrecord(mapper.getCount(entity));
		return qr;
	}

	@Override
	public List<Map<String, Object>> getListMap(OperationfeeDataDetailQueryParam queryParam) {
		// TODO Auto-generated method stub
		return mapper.getListMap(queryParam);
	}

	
}
