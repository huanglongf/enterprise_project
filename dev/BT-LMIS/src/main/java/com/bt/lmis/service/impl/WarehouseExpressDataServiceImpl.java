package com.bt.lmis.service.impl;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bt.lmis.code.ServiceSupport;
import com.bt.lmis.controller.form.WarehouseExpressDataParam;
import com.bt.lmis.controller.form.WarehouseExpressDataQueryParam;
import com.bt.lmis.dao.WarehouseExpressDataMapper;
import com.bt.lmis.model.WarehouseExpressData;
import com.bt.lmis.page.QueryResult;
import com.bt.lmis.service.WarehouseExpressDataService;
import com.bt.radar.model.ExpressinfoMaster;
@Service
public class WarehouseExpressDataServiceImpl<T> extends ServiceSupport<T> implements WarehouseExpressDataService<T> {

	@Autowired
    private WarehouseExpressDataMapper<T> mapper;

	public WarehouseExpressDataMapper<T> getMapper() {
		return mapper;
	}

	@Override
	public Long selectCount(Map<String, Object> param) throws Exception {
		return mapper.selectCount(param);
	}

	@Override
	public List<WarehouseExpressData> queryAll(WarehouseExpressData data) {
		return mapper.queryAll(data);
	}

	@Override
	public List<WarehouseExpressData> queryExpressAll(WarehouseExpressData data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public QueryResult<WarehouseExpressData> findAll(WarehouseExpressDataQueryParam queryParam,boolean hasCount) {
		QueryResult<WarehouseExpressData> qr = new QueryResult<WarehouseExpressData>();
		qr.setResultlist(mapper.findAll(queryParam));
		if(!hasCount){
			qr.setTotalrecord(mapper.getCount(queryParam));	
		}else{
			qr.setTotalrecord(queryParam.getLastTotalCount());
		}
		
		return qr;
	}
	@Override
	public List<WarehouseExpressData> querySection(Map<String,Object> data) {
		// TODO Auto-generated method stub
		return mapper.findSection(data);
	}
	@Override
	public List<WarehouseExpressData> queryAllSE(WarehouseExpressData data) {
		// TODO Auto-generated method stub
		return mapper.queryAllSE(data);
	}

	@Override
	public List<WarehouseExpressData> queryExpressAllSE(WarehouseExpressData data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int countByEntitySE(WarehouseExpressData data) {
		// TODO Auto-generated method stub
		return mapper.countByEntitySE(data);
	}

	@Override
	public int countAllSE(WarehouseExpressData data) {
		// TODO Auto-generated method stub
		return mapper.countAllSE(data);
	}

	@Override
	public int countExpressAllSE(WarehouseExpressData data) {
		// TODO Auto-generated method stub
//		return mapper.countExpressAllSE(data);
		return 0;
	}

	@Override
	public int countSection(WarehouseExpressDataQueryParam data) {
		return mapper.getCount(data);
	}

	
	
}
