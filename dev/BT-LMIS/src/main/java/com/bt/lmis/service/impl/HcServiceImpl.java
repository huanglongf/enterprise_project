package com.bt.lmis.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.lmis.code.ServiceSupport;
import com.bt.lmis.dao.HcMapper;
import com.bt.lmis.model.Store;
import com.bt.lmis.service.HcService;

@Service
public class HcServiceImpl<T> extends ServiceSupport<T> implements HcService<T> {

	@Autowired
    private HcMapper<T> mapper;

	public HcMapper<T> getMapper() {
		return mapper;
	}


	@Override
	public Long selectCount(Map<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void saveData(Map<String, Object> param) throws RuntimeException {
		// TODO Auto-generated method stub
		Integer result=mapper.checkData(param);
		if(result==null){
			mapper.saveData(param);
		}else{
			throw new RuntimeException("该耗材的计费方式已近存在，无法重复添加！");
		}		
		
	}




	@Override
	public ArrayList getTable(Map<String, Object> param) throws RuntimeException {
		// TODO Auto-generated method stub
		return mapper.getTable(param)==null?new ArrayList():mapper.getTable(param);
	}


	@Override
	public void delTable(Map<String, Object> param) throws RuntimeException {
		// TODO Auto-generated method stub
		mapper.delTable(param);
	}



}
