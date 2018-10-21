package com.bt.radar.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.lmis.code.ServiceSupport;
import com.bt.lmis.page.QueryResult;
import com.bt.radar.controller.form.WarninginfoMaintainMasterQueryParam;
import com.bt.radar.dao.WarninginfoMaintainMasterMapper;
import com.bt.radar.model.WarninginfoMaintainMaster;
import com.bt.radar.service.WarninginfoMaintainMasterService;
@Service
public class WarninginfoMaintainMasterServiceImpl<T> extends ServiceSupport<T> implements WarninginfoMaintainMasterService<T> {

	@Autowired
    private WarninginfoMaintainMasterMapper<T> mapper;

	public WarninginfoMaintainMasterMapper<T> getMapper() {
		return mapper;
	}

	@Override
	public Long selectCount(Map<String, Object> param) throws Exception {
		return mapper.selectCount(param);
	}

	@Override
	public QueryResult<T> findAllData(WarninginfoMaintainMasterQueryParam queryParam) {
		// TODO Auto-generated method stub
		QueryResult<WarninginfoMaintainMaster> queryResult = new QueryResult<WarninginfoMaintainMaster>();
		queryResult.setResultlist((List<WarninginfoMaintainMaster>) mapper.findAllData(queryParam));
		queryResult.setTotalrecord(mapper.selectCount(queryParam));
		
		return (QueryResult<T>) queryResult  ;
	}

	@Override
	public List<T> selectOption(WarninginfoMaintainMasterQueryParam queryParam) {
		// TODO Auto-generated method stub
		return mapper.selectOption(queryParam);
	}

	@Override
	public List<T> selectByParam(WarninginfoMaintainMasterQueryParam queryParam) {
		// TODO Auto-generated method stub
		
		
		return (List<T>) mapper.findAllData(queryParam);
	}

	@Override
	public List<WarninginfoMaintainMaster> getWarninginfoMaintainMasterService(
			WarninginfoMaintainMaster warninginfoMaintainMaster) {
		return mapper.findRecords(warninginfoMaintainMaster);
	}

	@Override
	public List<T> selectWarn_type(Map queryParam) {
		// TODO Auto-generated method stub
		return mapper.selectWarn_type(queryParam);
	}

	@Override
	public List<T> checkCode_name(Map param) {
		// TODO Auto-generated method stub
		return mapper.checkCode_name(param);
	}

	@Override
	public Integer checkDel(String id) {
		// TODO Auto-generated method stub
		return mapper.checkDel(id);
	}


}
