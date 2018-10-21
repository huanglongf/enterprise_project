package com.bt.lmis.dao;
import java.util.List;
import java.util.Map;

import com.bt.lmis.code.BaseMapper;
import com.bt.lmis.controller.form.AreaQueryParam;

/**
* @ClassName: AreaMapper
* @Description: TODO(AreaMapper)
* @author Yuriy.Jiang
*
* @param <T>
*/ 
public interface AreaMapper<T> extends BaseMapper<T> {
	
	public List<Map<String, Object>> findChildren(Map param);
	
	public void updateTree  (AreaQueryParam param);
	
	public Map<String,String> getChildLst(int id);
	
	public List<Map<String, Object>> findAll(AreaQueryParam param);
	
	public int check_area(Map param); 
	
	
	public Long count(Map param);
	
	
}
