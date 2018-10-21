package com.bt.workOrder.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.common.CommonUtils;
import com.bt.common.controller.param.Parameter;
import com.bt.lmis.code.ServiceSupport;
import com.bt.lmis.page.QueryResult;
import com.bt.workOrder.controller.form.CaimantWKParam;
import com.bt.workOrder.controller.form.CheckWKParam;
import com.bt.workOrder.dao.WkTypeMapper;
import com.bt.workOrder.service.WkTypeService;
@Service
public class WkTypeServiceImpl<T> extends ServiceSupport<T> implements WkTypeService<T> {

	@Autowired
    private WkTypeMapper<T> mapper;

	public WkTypeMapper<T> getMapper() {
		return mapper;
	}

	@Override
	public Long selectCount(Map<String, Object> param) throws Exception {
		return mapper.selectCount(param);
	}

	@Override
	public List<Map<String, Object>> findByWKTYPE() {
		return mapper.findByWKTYPE();
	}

	@Override
	public List<Map<String, Object>> findByWKERROR(@Param("code")String code) {
		return mapper.findByWKERROR(code);
	}

	@Override
	public List<Map<String, Object>> exportWK(Map<String, String> param) {
		return mapper.exportWK(param);
	}

	@Override
	public QueryResult<Map<String, Object>> query(CheckWKParam queryParam) {
		QueryResult<Map<String, Object>> qr = new QueryResult<Map<String, Object>>();
		qr.setResultlist(mapper.queryCHECKWK(queryParam));
		qr.setTotalrecord(mapper.countCHECKWK(queryParam));
		return qr;
	}

	@Override
	public QueryResult<Map<String, Object>> query(CaimantWKParam queryParam) {
		QueryResult<Map<String, Object>> qr = new QueryResult<Map<String, Object>>();
		qr.setResultlist(mapper.queryCAIMANTWK(queryParam));
		qr.setTotalrecord(mapper.countCAIMANTWK(queryParam));
		return qr;
	}

	@Override
	public List<Map<String, Object>> exportWKSP(Map<String, String> param) {
		return mapper.exportWKSP(param);
	}

	@Override
	public String getWkTypeByCode(String wkTypeCode) {
		// TODO Auto-generated method stub
		return mapper.getWkTypeByCode(wkTypeCode);
	}


    @Override
    public QueryResult<Map<String, Object>> queryCaimantWK(Parameter parameter){
        String[] temp = null;
        if(CommonUtils.checkExistOrNot(parameter.getParam().get("create_time"))) {
            temp = parameter.getParam().get("create_time").toString().split(" - ");
            parameter.getParam().put("s_time", temp[0]);
            parameter.getParam().put("e_time", temp[1]);
        }
        return new QueryResult<Map<String,Object>>(mapper.queryCAIMANTWK2(parameter), mapper.countCAIMANTWK2(parameter));
    }

    @Override
    public List<Map<String, Object>> exportCaimantWKExcel(Parameter parameter){
        String[] temp = null;
        if(CommonUtils.checkExistOrNot(parameter.getParam().get("create_time"))) {
            temp = parameter.getParam().get("create_time").toString().split(" - ");
            parameter.getParam().put("s_time", temp[0]);
            parameter.getParam().put("e_time", temp[1]);
        }
        return mapper.exportCaimantWKExcel(parameter);
    }

    @Override
    public QueryResult<Map<String, Object>> queryCheckWK(Parameter parameter){
        String[] temp = null;
        if(CommonUtils.checkExistOrNot(parameter.getParam().get("create_time"))) {
            temp = parameter.getParam().get("create_time").toString().split(" - ");
            parameter.getParam().put("s_time", temp[0]);
            parameter.getParam().put("e_time", temp[1]);
        }
        return new QueryResult<Map<String,Object>>(mapper.queryCHECKWK2(parameter), mapper.countCHECKWK2(parameter));
    }

    @Override
    public List<Map<String, Object>> exportCheckWKExcel(Parameter parameter){
        String[] temp = null;
        if(CommonUtils.checkExistOrNot(parameter.getParam().get("create_time"))) {
            temp = parameter.getParam().get("create_time").toString().split(" - ");
            parameter.getParam().put("s_time", temp[0]);
            parameter.getParam().put("e_time", temp[1]);
        }
        return mapper.exportCheckWKExcel(parameter);
    }
		
	
}
