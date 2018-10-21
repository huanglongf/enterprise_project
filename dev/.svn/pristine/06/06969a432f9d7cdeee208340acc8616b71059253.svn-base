package com.bt.workOrder.dao;
import java.util.List;
import java.util.Map;

import com.bt.lmis.code.BaseMapper;
import com.bt.workOrder.controller.form.WkLevelQueryParam;

/**
* @ClassName: WkLevelMapper
* @Description: TODO(WkLevelMapper)
* @author Yuriy.Jiang
*
* @param <T>
*/ 
public interface WkLevelMapper<T> extends BaseMapper<T> {
	
	public List<T>  findAllData(Map<String,String> param);

	public Map<String,Object> findLevelUpLevel(Map param);

	public Integer hasPowerToOpera(Map obj);
	
	public List<Map<String,Object>> findLevelUp_level_ADV(Map param);
	
	public List<Map<String,Object>> getTimeSlotNow(Map<String,Object> param);

	public List<Map<String, Object>> findSpWorkTime(Map<String, Object> param0);
	
	public List<Map<String, Object>> selectWoWorktimeSpecial(Map<String, Object> param0);
	
	public List<Map<String, Object>> selectWoWorktimeCommon(Map<String, Object> param0);

}
