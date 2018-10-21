package com.jumbo.dao.warehouse;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.warehouse.DistriButionAreaLoc;

import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import loxia.dao.Page;

import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;

@Transactional
public interface DistriButionAreaLocDao extends GenericEntityDao<DistriButionAreaLoc, Long>{
	
	   /* 库位绑定库位
	    */
	  @NativeQuery(pagable=true)
	  Pagination<DistriButionAreaLoc> getDistriButionAreaLoc(@QueryParam("mainWhid") Long mainWhid,Page page,RowMapper<DistriButionAreaLoc> rowMapper);
	  
	  @NativeQuery(pagable=true)
	  Pagination<DistriButionAreaLoc> findDistriButionAreaLoc(@QueryParam("mainWhid") Long mainWhid,
	                                                                 Page page,
			                     @QueryParam("locCodeName") String locCodeName,
			                             @QueryParam("locCode") String locCode,
	     @QueryParam("locDistriButionAreaCode") String locDistriButionAreaCode,
	     @QueryParam("locDistriButionAreaName") String locDistriButionAreaName, 
			                            RowMapper<DistriButionAreaLoc> rowMapper);
	   //删除文件
	   @NativeUpdate
	   Integer deleteDistriButionAreaLoc(@QueryParam("id") Long id);
	   //查询库区
	   @NativeQuery
	   List<DistriButionAreaLoc> getName(RowMapper<DistriButionAreaLoc> rowMapper);
	   //查询分配区域名称
	   @NativeQuery
	   List<DistriButionAreaLoc> getDistriButionNameList(@QueryParam("mainWhid") Long mainWhid,RowMapper<DistriButionAreaLoc> rowMapper);
	   //查询分配区域编码
       @NativeQuery
       List<DistriButionAreaLoc> getDistriButionCodeList(@QueryParam("mainWhid") Long mainWhid,RowMapper<DistriButionAreaLoc> rowMapper);
}
