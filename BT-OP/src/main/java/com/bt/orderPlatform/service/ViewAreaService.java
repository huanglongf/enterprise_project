package com.bt.orderPlatform.service;

import java.util.List;

import com.bt.orderPlatform.model.ViewArea;

public interface ViewAreaService<T> {

	ViewArea selectByViewAreaAddress(String view_area_address);

	List<ViewArea> selectByViewArea(ViewArea viewArea2);
	
	List<ViewArea> selectByProcessedViewArea(ViewArea viewArea);

}
