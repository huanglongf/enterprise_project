package com.jumbo.dao.warehouse;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;


import com.jumbo.wms.model.warehouse.DistriButionAreaType;

import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
@Transactional
public interface DistriButionAreaTypeDao extends GenericEntityDao<DistriButionAreaType, Long>{
	
	  /* 统计区域绑定类型数目
	   */
	  @NativeQuery
	  int getDistriButionAreaType(@QueryParam("distriButionAreaCode") String distriButionAreaCode,@QueryParam("distriButionAreaName") String distriButionAreaName,RowMapper<Integer> rowMapper);
	  
	  
	  
}
