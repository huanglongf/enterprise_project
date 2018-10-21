package com.jumbo.dao.warehouse;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.warehouse.TwhdistriButionAreaType;

import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
@Transactional
public interface TwhdistriButionAreaTypeDao extends GenericEntityDao<TwhdistriButionAreaType, Long> {
   
	//取消绑定
	@NativeUpdate
	public Integer cancelBinding(@QueryParam("distriButionAreaId") Long distriButionAreaId,
			                     @QueryParam("transActionTypeId") Long  transActionTypeId);
	
	@NativeQuery
	public Integer judgeBinding(@QueryParam("distriButionAreaId") Long distriButionAreaId,
                                @QueryParam("transActionTypeId") Long  transActionTypeId,RowMapper<Integer> rowMapper);
}
