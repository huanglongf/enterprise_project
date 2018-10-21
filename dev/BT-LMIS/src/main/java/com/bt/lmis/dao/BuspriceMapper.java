package com.bt.lmis.dao;
import java.util.Map;

import com.bt.lmis.code.BaseMapper;

/**
* @ClassName: BuspriceMapper
* @Description: TODO(BuspriceMapper)
* @author Yuriy.Jiang
*
* @param <T>
*/ 
public interface BuspriceMapper<T> extends BaseMapper<T> {
	
	public void deleteByTbId(Map tableid);
}
