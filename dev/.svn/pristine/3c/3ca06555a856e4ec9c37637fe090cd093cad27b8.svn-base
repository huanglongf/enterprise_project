package com.bt.lmis.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bt.lmis.code.BaseService;
import com.bt.lmis.controller.form.WhFeeRDQueryParam;
import com.bt.lmis.model.WarehouseFeeRD;
import com.bt.lmis.page.QueryResult;

@Service
public interface WarehouseFeeRDService<T> extends BaseService<T> {

	public QueryResult<WarehouseFeeRD> findAll(WhFeeRDQueryParam queryParam);
	public void insertBatch(List<WarehouseFeeRD> wList);
}
