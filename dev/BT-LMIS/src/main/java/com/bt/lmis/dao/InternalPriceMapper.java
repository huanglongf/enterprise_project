package com.bt.lmis.dao;
import java.util.Map;

import com.bt.lmis.code.BaseMapper;

/**
* @ClassName: InternalPriceMapper
* @Description: TODO(InternalPriceMapper)
* @author Yuriy.Jiang
*
* @param <T>
*/ 
public interface InternalPriceMapper<T> extends BaseMapper<T> {
	
	public void deleteByTbId(Map table_id);
	
	
	
}
