package com.bt.radar.service;
import java.util.List;
import java.util.Map;

import com.bt.lmis.code.BaseService;
import com.bt.lmis.page.QueryResult;
import com.bt.radar.controller.form.WarninginfoMaintainMasterQueryParam;
import com.bt.radar.model.WarninginfoMaintainMaster;

public interface WarninginfoMaintainMasterService<T> extends BaseService<T> {
	
	List<WarninginfoMaintainMaster> getWarninginfoMaintainMasterService(WarninginfoMaintainMaster warninginfoMaintainMaster);
	
	QueryResult<T> findAllData(WarninginfoMaintainMasterQueryParam queryParam);
	//返回搜索栏中可选的参数
	List<T> selectOption(WarninginfoMaintainMasterQueryParam queryParam);
	
	List<T> selectByParam(WarninginfoMaintainMasterQueryParam queryParam);
	
	List<T> selectWarn_type(Map queryParam);
	
   List<T> checkCode_name(Map param);

     Integer checkDel(String id);
}
