package com.bt.orderPlatform.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bt.orderPlatform.model.ViewArea;

/**
* @ClassName: ViewAreaMapper
* @Description: TODO(ViewAreaMapper)
* @author Yuriy.Jiang
*
* @param <T>
*/ 
public interface ViewAreaMapper<T> {

	ViewArea selectByViewAreaAddress(@Param("address_name")String view_area_address);

	List<ViewArea> selectByViewArea(ViewArea viewArea);
	
	List<ViewArea> selectByProcessedViewArea(ViewArea viewArea);

	
}
