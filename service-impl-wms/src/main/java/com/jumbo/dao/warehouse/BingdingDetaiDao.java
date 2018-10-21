package com.jumbo.dao.warehouse;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.warehouse.BingdingDetai;

import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Page;
import loxia.dao.Pagination;
@Transactional
public interface BingdingDetaiDao extends GenericEntityDao<BingdingDetai, Long>{
   //根据id查询绑定作业明细
	@NativeQuery(pagable=true)
	public Pagination<BingdingDetai> findBingdingDetai(@QueryParam("mainWhid") Long mainWhid,Page page,@QueryParam("bingdingDetaiId") Long bingdingDetaiId,RowMapper<BingdingDetai> rowMapper);
}
