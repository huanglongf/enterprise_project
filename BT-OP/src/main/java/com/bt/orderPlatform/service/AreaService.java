package com.bt.orderPlatform.service;

import java.util.List;
import java.util.Map;

import com.bt.orderPlatform.controller.form.AreaQueryParam;
import com.bt.orderPlatform.model.Area;


public interface AreaService<T> {

	public  List<Map<String, Object>>  findChildren(Map<String,String> param);

	public Object selectById(int parseInt);

	public void deleteTree(int id);

	public void addTree(Area area);

	public void updateTree(AreaQueryParam parma);

	public List findArea(Area area);

	public List findRecordsByP_code(Area area);

	public List<Area> getArea(String area_code);

	public String queryByAreaCode(String from_city);


}
