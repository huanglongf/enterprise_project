package com.bt.radar.service;

import java.util.List;
import java.util.Map;

import com.bt.lmis.code.BaseService;
import com.bt.radar.controller.form.WarninglevelListQueryParam;
import com.bt.radar.model.WarninglevelList;

public interface WarninglevelListService<T> extends BaseService<T> {
	
	List<T> findByWType(WarninglevelListQueryParam param);
	
	int  updateWll(WarninglevelListQueryParam param);
 
	WarninglevelList findLittlestLevel(WarninglevelListQueryParam param);

	void deleteByCon(Map dparam);
}
