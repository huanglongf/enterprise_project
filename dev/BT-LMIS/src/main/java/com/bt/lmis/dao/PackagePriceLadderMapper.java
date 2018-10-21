package com.bt.lmis.dao;
import java.util.List;

import com.bt.lmis.code.BaseMapper;
import com.bt.lmis.model.PackagePriceLadder;

/**
* @ClassName: PackagePriceLadderMapper
* @Description: TODO(PackagePriceLadderMapper)
* @author Yuriy.Jiang
*
* @param <T>
*/ 
public interface PackagePriceLadderMapper<T> extends BaseMapper<T> {
	public List<PackagePriceLadder> findByCBID(PackagePriceLadder packagePriceLadder);	
}
