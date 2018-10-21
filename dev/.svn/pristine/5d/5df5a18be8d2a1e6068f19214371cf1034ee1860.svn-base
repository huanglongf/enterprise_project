package com.bt.wms.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.wms.dao.RecordMapper;
import com.bt.wms.model.Container;
import com.bt.wms.model.Cut;
import com.bt.wms.model.ErrorLog;
import com.bt.wms.model.LowerRecord;
import com.bt.wms.model.UpperRecord;
import com.bt.wms.service.RecordService;
import com.bt.wms.utils.QueryParameter;
import com.bt.wms.utils.QueryResult;

@Service
public class RecordServiceImpl implements RecordService {

	@Autowired
    private RecordMapper mapper;
	
	@Override
	public void insertContainer(Container container) {
		mapper.insertContainer(container);
	}

	@Override
	public void updateContainer(Container container) {
		mapper.updateContainer(container);
	}

	@Override
	public List<Container> findByContainerCode(Container container) {
		return mapper.findByContainerCode(container);
	}

	@Override
	public void insertLower(LowerRecord lower) {
		mapper.insertLower(lower);
	}

	@Override
	public List<LowerRecord> findByLowerRecord(LowerRecord lower) {
		return mapper.findByLowerRecord(lower);
	}

	@Override
	public void insertUpper(UpperRecord upper) {
		mapper.insertUpper(upper);
	}

	@Override
	public List<UpperRecord> findByUpperRecord(UpperRecord lower) {
		return mapper.findByUpperRecord(lower);
	}

	@Override
	public List<Cut> findLowerRecordNumber(String lower_bat_id, String upper_bat_id, String sku) {
		return mapper.findLowerRecordNumber(lower_bat_id, upper_bat_id, sku);
	}

	@Override
	public QueryResult<Map<String, Object>> queryContainer(QueryParameter queryParam) {
		QueryResult<Map<String,Object>> qr= new QueryResult<Map<String,Object>>();
		qr.setResultlist(mapper.queryContainer(queryParam));
		qr.setTotalrecord(mapper.countContainer(queryParam));
		return qr;
	}

	@Override
	public QueryResult<Map<String, Object>> queryRecord(QueryParameter queryParam) {
		QueryResult<Map<String,Object>> qr= new QueryResult<Map<String,Object>>();
		qr.setResultlist(mapper.queryRecord(queryParam));
		qr.setTotalrecord(mapper.countRecord(queryParam));
		return qr;
	}

	@Override
	public QueryResult<Map<String, Object>> queryRecordCut(QueryParameter queryParam) {
		QueryResult<Map<String,Object>> qr= new QueryResult<Map<String,Object>>();
		qr.setResultlist(mapper.queryRecordCut(queryParam));
		qr.setTotalrecord(mapper.countRecordCut(queryParam));
		return qr;
	}

	@Override
	public List<Map<String, Object>> queryCY(int id) {
		return mapper.queryCY(id);
	}

	@Override
	public List<Map<String, Object>> query_lower_cy(int id) {
		return mapper.query_lower_cy(id);
	}

	@Override
	public List<Map<String, Object>> query_upper_cy(int id) {
		return mapper.query_upper_cy(id);
	}

	@Override
	public List<Map<String, Object>> queryLCY(int id) {
		return mapper.queryLCY(id);
	}

	@Override
	public QueryResult<Map<String, Object>> querylog(QueryParameter queryParam) {
		QueryResult<Map<String,Object>> qr= new QueryResult<Map<String,Object>>();
		qr.setResultlist(mapper.query_log(queryParam));
		qr.setTotalrecord(mapper.countLog(queryParam));
		return qr;
	}

	@Override
	public void insertlog(ErrorLog error) {
		mapper.insertlog(error);
	}

	@Override
	public List<Map<String, Object>> queryC(String code,String type) {
		return mapper.queryC(code,type);
	}

	@Override
	public List<Map<String, Object>> queryB(String container_code, String stime, String etime, String type,
			String create_user, String location, String sku) {
		return mapper.queryB(container_code, stime, etime, type, create_user, location, sku);
	}

	@Override
	public List<Map<String, Object>> queryD(String container_code, String stime, String etime, String type,
			String create_user, String location, String sku) {
		return mapper.queryD(container_code, stime, etime, type, create_user, location, sku);
	}

	@Override
	public List<Map<String, Object>> queryE(String container_code, String stime, String etime, String create_user,
			String sku) {
		return mapper.queryE(container_code, stime, etime, create_user, sku);
	}

	@Override
	public void insertLowers(List<LowerRecord> lower) {
		mapper.insertLowers(lower);
	}

	@Override
	public void insertUppers(List<UpperRecord> upper) {
		mapper.insertUppers(upper);
	}

}
