package com.bt.lmis.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bt.lmis.controller.form.WareImportErrorQueryParam;
import com.bt.lmis.controller.form.WareImportTaskQueryParam;
import com.bt.lmis.dao.WareImportErrorMapper;
import com.bt.lmis.model.WareImportError;
import com.bt.lmis.page.QueryResult;
import com.bt.lmis.service.WareImportErrorService;

@Service
@Transactional(readOnly=true)
public class WareImportErrorServiceImpl implements WareImportErrorService {
	
	@Autowired
	private WareImportErrorMapper wareImportErrorMapper;

	@Override
	@Transactional(readOnly=false)
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return wareImportErrorMapper.deleteByPrimaryKey(id);
	}

	@Override
	@Transactional(readOnly=false)
	public int insert(WareImportError record) {
		// TODO Auto-generated method stub
		return wareImportErrorMapper.insert(record);
	}

	@Override
	@Transactional(readOnly=false)
	public int insertSelective(WareImportError record) {
		// TODO Auto-generated method stub
		return wareImportErrorMapper.insertSelective(record);
	}

	@Override
	public WareImportError selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return wareImportErrorMapper.selectByPrimaryKey(id);
	}

	@Override
	@Transactional(readOnly=false)
	public int updateByPrimaryKeySelective(WareImportError record) {
		// TODO Auto-generated method stub
		return wareImportErrorMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	@Transactional(readOnly=false)
	public int updateByPrimaryKey(WareImportError record) {
		// TODO Auto-generated method stub
		return wareImportErrorMapper.updateByPrimaryKey(record);
	}

	@Override
	public QueryResult<WareImportErrorQueryParam> getList(WareImportErrorQueryParam wareImportErrorQueryParam) {
		QueryResult<WareImportErrorQueryParam> queryResult = new QueryResult<WareImportErrorQueryParam>();
		queryResult.setResultlist(wareImportErrorMapper.getList(wareImportErrorQueryParam));
		queryResult.setTotalrecord(wareImportErrorMapper.getListCount(wareImportErrorQueryParam));
		return queryResult;
	}

}
