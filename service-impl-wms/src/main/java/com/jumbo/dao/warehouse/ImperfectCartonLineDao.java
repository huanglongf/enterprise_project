package com.jumbo.dao.warehouse;

import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import com.jumbo.wms.model.warehouse.ImperfectCartonLine;
import com.jumbo.wms.model.warehouse.ImperfectCartonLineCommand;

public interface ImperfectCartonLineDao extends GenericEntityDao<ImperfectCartonLine, Long> {
	
	  @NativeQuery
	  ImperfectCartonLineCommand findImperfectCartonLineCode(@QueryParam(value = "defectCode") String defectCode, RowMapper<ImperfectCartonLineCommand> r);
	  @NativeQuery
	  List<ImperfectCartonLineCommand> findImperfectCartonLineStaId(@QueryParam(value = "staId") Long staId, RowMapper<ImperfectCartonLineCommand> r);
	  
	
	

}
