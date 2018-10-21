package com.bt.radar.dao;

import java.util.List;
import java.util.Map;

import com.bt.lmis.code.BaseMapper;
import com.bt.radar.model.WaybillWarninginfoDetail;
import com.bt.radar.model.WaybillWarninginfoMaster;

/**
 * @Title:WaybillWarninginfoMasterMapper
 * @Description: TODO(运单预警信息主表DAO) 
 * @author Ian.Huang 
 * @date 2016年9月2日上午11:28:01
 */
public interface WaybillWarninginfoMasterMapper<T> extends BaseMapper<T> {
	
	/**
	 * 
	 * @Description: TODO(更新记录)
	 * @param waybillWarninginfoMaster
	 * @return
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2016年9月2日上午11:49:05
	 */
	public Integer updateWaybillWarninginfoMaster(WaybillWarninginfoMaster waybillWarninginfoMaster);
	
	/**
	 * 
	 * @Description: TODO(插入记录)
	 * @param waybillWarninginfoMaster
	 * @return
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2016年9月2日上午11:43:32
	 */
	public Integer insertWaybillWarninginfoMaster(WaybillWarninginfoMaster waybillWarninginfoMaster);
	
	/**
	 * 
	 * @Description: TODO(查询记录)
	 * @param waybillWarninginfoMaster
	 * @return
	 * @return: List<WaybillWarninginfoMaster>
	 * @author Ian.Huang 
	 * @date 2016年9月2日上午9:45:25
	 */
	public List<WaybillWarninginfoMaster> findRecords(WaybillWarninginfoMaster waybillWarninginfoMaster);
	
	/**
	 * 
	 * @Description: TODO(查询记录)
	 * @param waybillWarninginfoMaster
	 * @return
	 * @return: List<T>
	 * @author Ian.Huang 
	 * @date 2016年9月8日上午9:45:25
	 */
	
	
	public List<WaybillWarninginfoDetail> findAlarmDetails(Map param);
	
	
	void  updateMaster(Map param);
}
