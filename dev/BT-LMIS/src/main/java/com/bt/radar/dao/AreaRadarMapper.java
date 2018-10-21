package com.bt.radar.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bt.lmis.code.BaseMapper;
import com.bt.radar.model.Area;

/**
 * @Title:AreaMapper
 * @Description: TODO(区域DAO)  
 * @author Ian.Huang 
 * @date 2016年8月25日下午4:40:04
 */
public interface AreaRadarMapper<T> extends BaseMapper<T> {
	
	/**
	 * 
	 * @Description: TODO(下级找下级区域)
	 * @param area_code
	 * @return
	 * @return: List<Area>  
	 * @author Ian.Huang 
	 * @date 2016年8月26日上午10:34:10
	 */
	public List<Area> getRecords(@Param("area_code")String area_code);
	
	/**
	 * 
	 * @Description: TODO(查询地区)
	 * @param area
	 * @return
	 * @return: List<Area>
	 * @author Ian.Huang 
	 * @date 2016年8月25日下午4:51:55
	 */
	public List<Area> findRecords(Area area);
	/**
	 * 
	 * @Description: TODO(查询地区)
	 * @param area
	 * @return
	 * @return: List<Area>
	 * @author Ian.Huang 
	 * @date 2016年8月25日下午4:51:55
	 */
	
	public List<Area> findRecordsByP_code(Area area);

	public List<Area> findLikeArea(Area area);
	
	
}
