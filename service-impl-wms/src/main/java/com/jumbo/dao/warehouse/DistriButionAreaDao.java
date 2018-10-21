package com.jumbo.dao.warehouse;



import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.warehouse.DistriButionArea;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Page;
import loxia.dao.Pagination;

/**
 * 
 * @author lijinggong+2018年7月26日
 *
 *
 */

@Transactional
public interface DistriButionAreaDao extends GenericEntityDao<DistriButionArea, Long> {
	//查询分配区域新建
   @NamedQuery(pagable=true)
   public Pagination<DistriButionArea> getDistriButionArea(@QueryParam("mainWhid") Long mainWhid,Page page);
	
   /**
	 * 根据分配区域编码和分配区域名称进行模糊查询
	 * 
	 */
   @NativeQuery(pagable=true)
   Pagination<DistriButionArea> findDistriButionArea(@QueryParam("mainWhid") Long mainWhid,Page page,@QueryParam("distriButionAreaCode") String distriButionAreaCode, @QueryParam("distriButionAreaName") String distriButionAreaName, RowMapper<DistriButionArea> rowMapper);
   
   
   @NativeUpdate
   Integer updateDistriButionArea(@QueryParam("id") Long id,@QueryParam("distriButionAreaCode") String distriButionAreaCode,@QueryParam("distriButionAreaName") String distriButionAreaName);
   
   @NativeUpdate
   Integer deleteDistriButionArea(@QueryParam("id") Long id);
   
   @NativeUpdate
   Integer deleteLocByDistriButionAreaId(@QueryParam("distriButionAreaId") Long distriButionAreaId);
   
   @NativeUpdate
   Integer deleteTypeByDistriButionAreaId(@QueryParam("distriButionAreaId") Long distriButionAreaId);
   
   @NamedQuery
   String judgeDistriButionArea(@QueryParam("distriButionAreaCode") String distriButionAreaCode);
   //进行重复性校验
   @NativeQuery
   int repeatCheck(@QueryParam("mainWhid") Long mainWhid,@QueryParam("distriButionAreaCode") String distriButionAreaCode,@QueryParam("distriButionAreaName") String distriButionAreaName,RowMapper<Integer> rowMapper);
   
   //进行区域编码重复性校验
   @NativeQuery
   Integer repeatCheckDistriButionAreaCode(@QueryParam("mainWhid") Long mainWhid,@QueryParam("distriButionAreaCode") String distriButionAreaCode,RowMapper<Integer> rowMapper);
   
   //进行区域名称重复性校验
   @NativeQuery
   Integer repeatCheckDistriButionAreaName(@QueryParam("mainWhid") Long mainWhid,@QueryParam("distriButionAreaName") String distriButionAreaName,RowMapper<Integer> rowMapper);
}
