package com.bt.radar.dao;
import java.util.List;

import com.bt.lmis.code.BaseMapper;
import com.bt.radar.controller.form.WarningRoutestatusListQueryParam;
import com.bt.radar.model.WarningRoutestatusList;

/**
* @ClassName: WarningRoutestatusListMapper
* @Description: TODO(WarningRoutestatusListMapper)
* @author Yuriy.Jiang
*
* @param <T>
*/ 
public interface WarningRoutestatusListMapper<T> extends BaseMapper<T> {
	
	public List<WarningRoutestatusList> getRecords(WarningRoutestatusList warningRoutestatusList);
	
	List<T>  findAll(WarningRoutestatusListQueryParam query);
	
	int  checkExisit(WarningRoutestatusListQueryParam query);
}
