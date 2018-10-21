package com.bt.workOrder.service;

import java.util.Map;

import com.bt.radar.model.WaybillWarninginfoDetail;

public interface WorkOrderLevelUpService<T>{

	public void  LevelUp  ()  throws Exception;

	public void parseCaldate();
	
	public void updateLevel(Map param) throws Exception;
	
	public void  RadarGenWk(WaybillWarninginfoDetail detail ) throws Exception;
	
	public void RadarCancelWk() throws Exception;
}
