package com.bt.radar.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.lmis.code.ServiceSupport;
import com.bt.radar.dao.WaybillWarninginfoDetailMapper;
import com.bt.radar.dao.WaybillWarninginfoMasterMapper;
import com.bt.radar.model.WaybillWarninginfoDetail;
import com.bt.radar.model.WaybillWarninginfoMaster;
import com.bt.radar.service.WaybillWarninginfoMasterService;
@Service
public class WaybillWarninginfoMasterServiceImpl<T> extends ServiceSupport<T> implements WaybillWarninginfoMasterService<T> {

	@Autowired
    private WaybillWarninginfoMasterMapper<T> mapper;
	
	@Autowired
    private WaybillWarninginfoDetailMapper<T> Dmapper;

	public WaybillWarninginfoMasterMapper<T> getMapper() {
		return mapper;
	}

	@Override
	public Long selectCount(Map<String, Object> param) throws Exception {
		return mapper.selectCount(param);
	}

	@Override
	public List<WaybillWarninginfoDetail> findAlarmDetails(Map queryParam) {
		// TODO Auto-generated method stub
		return mapper.findAlarmDetails(queryParam);
	}

	@Override
	public void deleteMaster(Map queryParam) {
		Dmapper.updateDel(queryParam);
		Map pp=new HashMap();
		pp.put("del_flag", 0);
		pp.put("pid",queryParam.get("pid") );
	List list=	mapper.findAlarmDetails(pp);
	if(list==null||list.size()==0){
		pp.put("id", queryParam.get("pid"));
		mapper.updateMaster(pp);
	};
	}

	@Override
	public int CancelWaybillWarning(String wkId) {
		// TODO Auto-generated method stub
		return Dmapper.CancelWaybillWarning( wkId);
	}
		
	
}
