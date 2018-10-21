package com.bt.radar.dao;
import java.util.List;
import java.util.Map;

import com.bt.lmis.code.BaseMapper;
import com.bt.radar.controller.form.WarninginfoMaintainMasterQueryParam;
import com.bt.radar.model.WarninginfoMaintainMaster;

/**
* @ClassName: WarninginfoMaintainMasterMapper
* @Description: TODO(WarninginfoMaintainMasterMapper)
* @author Yuriy.Jiang
*
* @param <T>
*/ 
public interface WarninginfoMaintainMasterMapper<T> extends BaseMapper<T> {
	
	public List<WarninginfoMaintainMaster> findRecords(WarninginfoMaintainMaster warninginfoMaintainMaster);
	
	public List<T> findAllData(WarninginfoMaintainMasterQueryParam  QueryParam);

	public int selectCount(WarninginfoMaintainMasterQueryParam queryParam);

	public List<T> selectOption(WarninginfoMaintainMasterQueryParam queryParam);
	
	public T selectByStringId(String id);
	
	public List<T> selectWarn_type(Map param);
	
	public  List<T> checkCode_name(Map param);

	public Integer checkDel(String id);
}
