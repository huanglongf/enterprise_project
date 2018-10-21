package com.bt.lmis.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.lmis.code.ServiceSupport;
import com.bt.lmis.controller.form.WhFeeRDQueryParam;
import com.bt.lmis.dao.WhFeeRDMapper;
import com.bt.lmis.model.WarehouseFeeRD;
import com.bt.lmis.page.QueryResult;
import com.bt.lmis.service.WarehouseFeeRDService;

@Service
public class WarehouseFeeRDServiceImpl<T> extends ServiceSupport<T> implements WarehouseFeeRDService<T> {

	@Autowired
	private WhFeeRDMapper<WarehouseFeeRD> whFeeRDMapper;
	
	@Override
	public int save(T entity) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void update(T entity) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Serializable id) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void batchDelete(Integer[] ids) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public T selectById(Integer id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long selectCount(Map<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public QueryResult<WarehouseFeeRD> findAll(WhFeeRDQueryParam queryParam) {
		QueryResult<WarehouseFeeRD> qr = new QueryResult<WarehouseFeeRD>();
		qr.setResultlist(whFeeRDMapper.findCB(queryParam));
		qr.setTotalrecord(whFeeRDMapper.countCBRecords(queryParam));
		return qr;
	}

	@Override
	public void insertBatch(List<WarehouseFeeRD> wList) {
		whFeeRDMapper.insertBatch(wList);
	}

}
