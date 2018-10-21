package com.bt.lmis.service;

import java.util.List;

import com.bt.lmis.code.BaseService;
import com.bt.lmis.model.PackagePriceLadder;

public interface PackagePriceLadderService<T> extends BaseService<T> {
	public List<PackagePriceLadder> findByCBID(PackagePriceLadder packagePriceLadder);	
}
