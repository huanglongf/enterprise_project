package com.bt.orderPlatform.service.impl;


import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.orderPlatform.controller.form.AreaQueryParam;
import com.bt.orderPlatform.dao.AreaMapper;
import com.bt.orderPlatform.model.Area;
import com.bt.orderPlatform.service.AreaService;
@Service
public class AreaServiceImpl<T> implements AreaService<T> {

	@Autowired
    private AreaMapper<T> mapper;

	public AreaMapper<T> getMapper() {
		return mapper;
	}

	@Override
	public List<Map<String, Object>> findChildren(Map<String, String> param) {
		// TODO Auto-generated method stub
		return mapper.findChildren(param);
	}

	@Override
	public Object selectById(int id) {
		// TODO Auto-generated method stub
		return mapper.selectById(id);
	}

	@Override
	public void deleteTree(int id) {
		// TODO Auto-generated method stub
		List<String> pid = mapper.selectByIdPid(id);
		if(pid.size()!=0){
			for (String ids : pid) {
				deleteTree(Integer.parseInt(ids));
			}
		}
		deleteById(id);
		
	}

	@Override
	public void addTree(Area area) {
		// TODO Auto-generated method stub
		area.setCreate_time(new Date());
		//zyz	area.setHaschild(0);
		area.setUpdate_time(new Date());
		area.setCreate_user("1");
		area.setUpdate_user("1");
		mapper.insert((T) area);
	}

	@Override
	public void updateTree(AreaQueryParam parma) {
		// TODO Auto-generated method stub
		mapper.updateTree(parma);
	}

	/**
	 * 根据id删除
	 * 只供本类调用
	 * @param id
	 */
	private void deleteById(Integer id) {
		mapper.deleteById(id);
	}

	@Override
	public List findArea(Area area) {
		// TODO Auto-generated method stub
		return mapper.findArea(area);
	}
	
	
	@Override
	public List findRecordsByP_code(Area area) {
		// TODO Auto-generated method stub
		return mapper.findRecordsByP_code(area);
	}

	@Override
	public List<Area> getArea(String area_code) {
		// TODO Auto-generated method stub
		return mapper.getArea(area_code);
	}
	



	@Override
	public String queryByAreaCode(String area_code) {
		// TODO Auto-generated method stub
		return mapper.queryByAreaCode(area_code);
	}

	

}
