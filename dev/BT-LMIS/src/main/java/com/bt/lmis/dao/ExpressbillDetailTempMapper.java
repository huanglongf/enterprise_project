package com.bt.lmis.dao;
import java.util.List;
import java.util.Map;

import com.bt.lmis.code.BaseMapper;

/**
* @ClassName: ExpressbillDetailTempMapper
* @Description: TODO(ExpressbillDetailTempMapper)
* @author Yuriy.Jiang
*
* @param <T>
*/ 
public interface ExpressbillDetailTempMapper<T> extends BaseMapper<T> {

	List<Map<String, Object>> selectByBatId(String bat_id);
	
	
}
