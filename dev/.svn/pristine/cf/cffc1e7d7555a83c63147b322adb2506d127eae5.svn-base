package com.bt.lmis.service.impl;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bt.lmis.controller.form.WareRelationQueryParam;
import com.bt.lmis.dao.WareRelationMapper;
import com.bt.lmis.model.ContractBasicinfo;
import com.bt.lmis.model.WareRelation;
import com.bt.lmis.page.QueryResult;
import com.bt.lmis.service.WareRelationService;

@Service
@Transactional(readOnly=true)
public class WareRelationServiceImpl implements WareRelationService {
	
	@Autowired
	private WareRelationMapper wareRelationMapper;

	@Override
	@Transactional(readOnly=false)
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return wareRelationMapper.deleteByPrimaryKey(id);
	}

	@Override
	@Transactional(readOnly=false)
	public int insert(WareRelation record) {
		// TODO Auto-generated method stub
		return wareRelationMapper.insert(record);
	}

	@Override
	@Transactional(readOnly=false)
	public int insertSelective(WareRelation record) {
		// TODO Auto-generated method stub
		return wareRelationMapper.insertSelective(record);
	}

	@Override
	public WareRelation selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return wareRelationMapper.selectByPrimaryKey(id);
	}

	@Override
	@Transactional(readOnly=false)
	public int updateByPrimaryKeySelective(WareRelation record) {
		// TODO Auto-generated method stub
		return wareRelationMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	@Transactional(readOnly=false)
	public int updateByPrimaryKey(WareRelation record) {
		// TODO Auto-generated method stub
		return wareRelationMapper.updateByPrimaryKey(record);
	}

	@Override
	public QueryResult<WareRelationQueryParam> findAll(WareRelationQueryParam queryParam) {
		QueryResult<WareRelationQueryParam> qr = new QueryResult<WareRelationQueryParam>();
		qr.setResultlist(wareRelationMapper.findAll(queryParam));
		qr.setTotalrecord(wareRelationMapper.countAll(queryParam));
		return qr;
	}

	@Override
	public Map<String, Long> checkWareRelationParam(WareRelation wareRelation) {
		// TODO Auto-generated method stub
		return wareRelationMapper.checkWareRelationParam(wareRelation);
	}

	@Override
	@Transactional(readOnly=false)
	public int delByIds(String ids) {

		if(StringUtils.isNotBlank(ids)){
			
			for (String id : ids.split(",")) {
				wareRelationMapper.deleteByPrimaryKey(Integer.valueOf(id));
			}
			
			return 1;
		} else {
			return -1;
		}
	}

}
