package com.bt.radar.service.impl;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.lmis.code.ServiceSupport;
import com.bt.lmis.page.QueryResult;
import com.bt.radar.controller.form.RoutecodeQueryParam;
import com.bt.radar.dao.RoutecodeMapper;
import com.bt.radar.model.Routecode;
import com.bt.radar.model.RoutecodeBean;
import com.bt.radar.service.RoutecodeService;
@Service
public class RoutecodeServiceImpl<T> extends ServiceSupport<T> implements RoutecodeService<T> {

	@Autowired
    private RoutecodeMapper<T> mapper;

	public RoutecodeMapper<T> getMapper() {
		return mapper;
	}

	@Override
	public Long selectCount(Map<String, Object> param) throws Exception {
		return mapper.selectCount(param);
	}

	@Override
	public QueryResult<RoutecodeBean> findAllData(RoutecodeQueryParam queryParam) {
		// TODO Auto-generated method stub
		QueryResult<RoutecodeBean> queryResult = new QueryResult<RoutecodeBean>();
		queryResult.setResultlist((List<RoutecodeBean>) mapper.findAllData(queryParam));
		queryResult.setTotalrecord(mapper.selectCount(queryParam));
		return queryResult;
		
	}
		@Override
		public List<T> selectTransport_vender() {
			// TODO Auto-generated method stub
			
			return mapper.selectTransport_vender(null);
		}

		@Override
		public List<T> getTransport_vender() {
			// TODO Auto-generated method stub
			return mapper.getTransport_vender(null);
		}

		@Override
		public List<T> findAllRecord(RoutecodeQueryParam queryParam) {
			// TODO Auto-generated method stub
			return mapper.findAllRecord(queryParam);
		}

		@Override
		public List<T> selectRecordsByWID(Map param) {
			// TODO Auto-generated method stub
			return mapper.selectRecordsByWID(param);
		}

		@Override
		public List<T> getStatus(Map param) {
			// TODO Auto-generated method stub
			return mapper.getStatus(param);
		}
	
}
