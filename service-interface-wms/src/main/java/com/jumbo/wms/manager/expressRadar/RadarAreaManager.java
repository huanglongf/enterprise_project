package com.jumbo.wms.manager.expressRadar;

import java.util.List;

import loxia.dao.Pagination;
import loxia.dao.Sort;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.expressRadar.SysRadarArea;

/**
 * @author gianni.zhang
 *
 * 2015年5月25日 下午6:11:58
 */
public interface RadarAreaManager extends BaseManager{
	/**
	 * 查询快递省市
	 * @param province
	 * @param city
	 * @return List<SysRadarArea>
	 */
	List<SysRadarArea> radarAreaList(String province, String city);
	
	/**
	 * 移除快递省市
	 * @param province
	 * @param city
	 * @return String
	 */
	String removeArea(List<Long> ids);
	
	/**
	 * 创建快递省市
	 * @param province
	 * @param city
	 * @return String
	 */
	String addRadarArea(String province, String city);
	
	/**
     * 查询快递省市列表
     * 
	 * @param province
	 * @param city
     * @return Pagination<SysRadarArea>
     */
    public Pagination<SysRadarArea> findArea(int start, int pageSize, Sort[] sorts, String province, String city);
	
    
    
}
