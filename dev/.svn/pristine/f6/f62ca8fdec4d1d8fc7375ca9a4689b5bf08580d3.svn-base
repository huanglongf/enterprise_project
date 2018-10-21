package com.bt.radar.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.radar.dao.AreaRadarMapper;
import com.bt.radar.model.Area;
import com.bt.radar.service.AreaRadarService;
@Service
public class AreaRadarServiceImpl<T> implements AreaRadarService<T> {

	@Autowired
	private AreaRadarMapper<Area> areaRadarMapper;
	
	public AreaRadarMapper<Area> getAreaRadarMapper(){
		return areaRadarMapper;
	}
	
	@Override
	public List<Area> getArea(String area_code) {
		return areaRadarMapper.getRecords(area_code);
	}
	
	@Override
	public List<Area> findArea(Area area) {
		return areaRadarMapper.findRecords(area);
	}
	
	@Override
	public List<Area> findRecordsByP_code(Area area) {
		return areaRadarMapper.findRecordsByP_code(area);
	}

	@Override
	public List<Area> findLikeArea(Area area) {
		// TODO Auto-generated method stub
		return areaRadarMapper.findLikeArea(area);
	}
}
