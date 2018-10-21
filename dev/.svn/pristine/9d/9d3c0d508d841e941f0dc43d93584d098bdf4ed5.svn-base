package com.bt.lmis.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.lmis.dao.DictMapper;
import com.bt.lmis.model.Dict;
import com.bt.lmis.service.DictService;

@Service
public class DictServiceImpl implements DictService {
	
	@Autowired
	private DictMapper dictMapper;

	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return dictMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(Dict record) {
		// TODO Auto-generated method stub
		return dictMapper.insert(record);
	}

	@Override
	public int insertSelective(Dict record) {
		// TODO Auto-generated method stub
		return dictMapper.insertSelective(record);
	}

	@Override
	public Dict selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return dictMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<Dict> findByType(String dictType) {
		// TODO Auto-generated method stub
		return dictMapper.findByType(dictType);
	}

	@Override
	public int updateByPrimaryKeySelective(Dict record) {
		// TODO Auto-generated method stub
		return dictMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(Dict record) {
		// TODO Auto-generated method stub
		return dictMapper.updateByPrimaryKey(record);
	}

	@Override
	public String findByTypeValue(String dictType, String dictValue) {
		// TODO Auto-generated method stub
		return dictMapper.findByTypeValue(dictType,dictValue);
	}

}
