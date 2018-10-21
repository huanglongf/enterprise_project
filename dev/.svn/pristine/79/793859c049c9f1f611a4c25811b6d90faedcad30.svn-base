package com.bt.radar.service;

import java.util.List;
import java.util.Map;

import com.bt.lmis.code.BaseService;
import com.bt.radar.model.WaybillWarninginfoDetail;
import com.bt.radar.model.WaybillWarninginfoMaster;

public interface WaybillWarninginfoMasterService<T> extends BaseService<T> {
	
	
	List<WaybillWarninginfoDetail> findAlarmDetails(Map queryParam);
	
    void deleteMaster(Map queryParam);
    
    int CancelWaybillWarning(String wkId);
}
