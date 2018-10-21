package com.bt.lmis.dao;

import java.util.List;

import com.bt.lmis.code.BaseMapper;
import com.bt.lmis.controller.form.WhFeeRDQueryParam;
import com.bt.lmis.model.WarehouseFeeRD;

/**
 * @Title:WhFeeRDMapper
 * @Description: TODO(仓储费原始数据DAO)
 * @author Ian.Huang 
 * @date 2016年6月27日下午3:29:48
 */
public interface WhFeeRDMapper<T> extends BaseMapper<T>{

	public List<WarehouseFeeRD> findCB(WhFeeRDQueryParam queryParam);

	public int countCBRecords(WhFeeRDQueryParam queryParam);
	
	public void insertBatch(List<WarehouseFeeRD> wList);
	
}
