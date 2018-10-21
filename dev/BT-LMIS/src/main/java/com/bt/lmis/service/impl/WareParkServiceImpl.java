package com.bt.lmis.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bt.lmis.controller.form.WareParkQueryParam;
import com.bt.lmis.dao.WareParkMapper;
import com.bt.lmis.model.WarePark;
import com.bt.lmis.page.QueryResult;
import com.bt.lmis.service.WareParkService;

@Service
@Transactional(readOnly=true)
public class WareParkServiceImpl implements WareParkService {
	
	@Autowired
	private WareParkMapper wareParkMapper;

	@Override
	@Transactional(readOnly=false)
	public int deleteByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return wareParkMapper.deleteByPrimaryKey(id);
	}

	@Override
	@Transactional(readOnly=false)
	public int insert(WarePark record) {
		// TODO Auto-generated method stub
		return wareParkMapper.insert(record);
	}

	@Override
	@Transactional(readOnly=false)
	public int insertSelective(WarePark record) {
		// TODO Auto-generated method stub
		return wareParkMapper.insertSelective(record);
	}

	@Override
	public WareParkQueryParam selectByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return wareParkMapper.selectByPrimaryKey(id);
	}

	@Override
	@Transactional(readOnly=false)
	public int updateByPrimaryKeySelective(WarePark record) {
		// TODO Auto-generated method stub
		return wareParkMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	@Transactional(readOnly=false)
	public int updateByPrimaryKey(WarePark record) {
		// TODO Auto-generated method stub
		return wareParkMapper.updateByPrimaryKey(record);
	}

	@Override
	public QueryResult<WareParkQueryParam> findAll(WareParkQueryParam queryParam) {
		QueryResult<WareParkQueryParam> qr = new QueryResult<WareParkQueryParam>();
		qr.setResultlist(wareParkMapper.findAll(queryParam));
		qr.setTotalrecord(wareParkMapper.countAll(queryParam));
		return qr;
	}

	@Override
	public Map<String, BigDecimal> checkWareParkParam(WarePark warePark) {
		return wareParkMapper.checkWareParkParam(warePark);
	}

	@Override
	@Transactional(readOnly=false)
	public int delByIds(String ids) {
		
		if(StringUtils.isNotBlank(ids)){
			
			for (String id : ids.split(",")) {
				wareParkMapper.deleteByPrimaryKey(id);
			}
			
			return 1;
		} else {
			return -1;
		}
		
	}

	@Override
	public List<Map<String,Object>> exportList(WareParkQueryParam queryParam) {
		// TODO Auto-generated method stub
		return wareParkMapper.exportList(queryParam);
	}


}
