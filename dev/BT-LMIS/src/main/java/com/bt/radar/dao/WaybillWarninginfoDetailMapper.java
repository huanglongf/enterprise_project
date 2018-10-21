package com.bt.radar.dao;

import java.util.List;
import java.util.Map;

import com.bt.lmis.code.BaseMapper;
import com.bt.radar.model.WaybillWarninginfoDetail;

/**
 * @Title:WaybillWarninginfoDetailMapper
 * @Description: TODO(运单预警信息明细DAO)
 * @author Ian.Huang 
 * @date 2016年9月2日下午2:43:04
 */
public interface WaybillWarninginfoDetailMapper<T> extends BaseMapper<T> {
	
	/**
	 * 
	 * @Description: TODO(更新记录)
	 * @param waybillWarninginfoDetail
	 * @return
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2016年9月2日上午11:49:05
	 */
	public Integer updateWaybillWarninginfoDetail(WaybillWarninginfoDetail waybillWarninginfoDetail);
	
	/**
	 * 
	 * @Description: TODO(插入记录)
	 * @param waybillWarninginfoDetail
	 * @return
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2016年9月2日上午11:43:32
	 */
	public Integer insertWaybillWarninginfoDetail(WaybillWarninginfoDetail waybillWarninginfoDetail);
	
	/**
	 * 
	 * @Description: TODO(查询记录)
	 * @param waybillWarninginfoDetail
	 * @return
	 * @return: List<WaybillWarninginfoDetail>
	 * @author Ian.Huang 
	 * @date 2016年9月2日上午9:45:25
	 */
	public List<WaybillWarninginfoDetail> findRecords(WaybillWarninginfoDetail waybillWarninginfoDetail);
	
	
	public void  updateDel(Map param);
	
	public void updatWkflag(Map param);

	public int CancelWaybillWarning(String wkId);

	public List<Map<String,Object>> findRecordsForMap(WaybillWarninginfoDetail detail);

	public List<WaybillWarninginfoDetail> findRecordsPageData(Map param);

	public void insertDetailBf(WaybillWarninginfoDetail detail);
	
}
