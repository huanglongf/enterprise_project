package com.bt.lmis.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bt.lmis.controller.form.FileTempleteQueryParam;
import com.bt.lmis.dao.FileTempleteMapper;
import com.bt.lmis.model.FileTemplete;
import com.bt.lmis.service.FileTempleteService;

@Service
@Transactional(readOnly=true)
public class FileTempleteServiceImpl implements FileTempleteService {
	
	@Autowired
	private FileTempleteMapper fileTempleteMapper; 

	@Override
	@Transactional(readOnly=false,rollbackFor=Exception.class)
	public int deleteByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return fileTempleteMapper.deleteByPrimaryKey(id);
	}

	@Override
	@Transactional(readOnly=false,rollbackFor=Exception.class)
	public int insert(FileTemplete record) {
		// TODO Auto-generated method stub
		return fileTempleteMapper.insert(record);
	}

	@Override
	@Transactional(readOnly=false,rollbackFor=Exception.class)
	public int insertSelective(FileTemplete record) {
		// TODO Auto-generated method stub
		return fileTempleteMapper.insertSelective(record);
	}

	@Override
	public FileTemplete selectByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return fileTempleteMapper.selectByPrimaryKey(id);
	}

	@Override
	@Transactional(readOnly=false,rollbackFor=Exception.class)
	public int updateByPrimaryKeySelective(FileTemplete record) {
		// TODO Auto-generated method stub
		return fileTempleteMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	@Transactional(readOnly=false,rollbackFor=Exception.class)
	public int updateByPrimaryKey(FileTemplete record) {
		// TODO Auto-generated method stub
		return fileTempleteMapper.updateByPrimaryKey(record);
	}

	@Override
	public FileTempleteQueryParam getByQueryParam(FileTempleteQueryParam fileTemplete) {
		// TODO Auto-generated method stub
		return fileTempleteMapper.getByQueryParam(fileTemplete);
	}

	@Override
	public List<FileTempleteQueryParam> findByQueryParam(FileTempleteQueryParam fileTemplete) {
		// TODO Auto-generated method stub
		return fileTempleteMapper.findByQueryParam(fileTemplete);
	}

}
