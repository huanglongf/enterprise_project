package com.jumbo.dao.baseinfo;


import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.transaction.annotation.Transactional;

import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;
import loxia.dao.Sort;

import com.jumbo.wms.model.baseinfo.TransportatorWeigth;

@Transactional
public interface TransportatorWeigthDao extends  GenericEntityDao<TransportatorWeigth, Long>{
	
	 @NativeQuery(pagable = true)
	 Pagination<TransportatorWeigth> findTransportatorListByWeight(int start,int pagesize,@QueryParam(value="ouId") Long ouId,@QueryParam(value = "expCode") String expCode,BeanPropertyRowMapper<TransportatorWeigth> beanPropertyRowMapperExt,Sort[] sorts);

	 @NativeQuery
	 TransportatorWeigth findTransportatorWeigth(@QueryParam(value="ouId") Long ouId,@QueryParam(value="expCode")String expCode,BeanPropertyRowMapper<TransportatorWeigth> beanPropertyRowMapperExt);
	 
	 @NativeUpdate
	 void updateTransportatorWeigth(@QueryParam(value="id") Long id,@QueryParam(value = "expCode") String expCode,@QueryParam(value = "name") String name,@QueryParam(value = "maxWeight") String maxWeight,@QueryParam(value = "weightDifferencePercent") String weightDifferencePercent);
	 
	 
}
