package com.bt.lmis.dao;
import java.util.Map;

import com.bt.lmis.code.BaseMapper;

/**
* @ClassName: ItemtypePriceMapper
* @Description: TODO(ItemtypePriceMapper)
* @author Yuriy.Jiang
*
* @param <T>
*/ 
public interface ItemtypePriceMapper<T> extends BaseMapper<T> {
	
	public void deleteByTbId(Map table_id);
	
}
