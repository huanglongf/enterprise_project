package com.jumbo.dao.priorityChannelOms;

import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.config.ThreadConfig;
import com.jumbo.wms.model.warehouse.PriorityChannelOms;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;
import loxia.dao.Sort;


@Transactional
public interface PriorityChannelOmsConfigDao extends GenericEntityDao<PriorityChannelOms, Long> {


    /**
     * 获取店铺列表
     * 
     * @author 
     * @param 
     * @return
     */
	@NativeQuery(pagable = true)
	Pagination<PriorityChannelOms> getAllPriorityChannelOms(int start, int pageSize, Sort[] sorts,RowMapper<PriorityChannelOms> rowMapper);
    /**
     * 根据code获取店铺
     * 
     * @author 
     * @param code
     * @return
     */
    @NamedQuery
    PriorityChannelOms getPriorityChannelOmsByCode(@QueryParam(value = "code") String code);
    /**
     * 根据id获取店铺
     * 
     * @author 
     * @param id
     * @return
     */
    @NamedQuery
	PriorityChannelOms getPriorityChannelOmsById(@QueryParam(value = "id") Long id);
	
	

}
