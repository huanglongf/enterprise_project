package com.jumbo.dao.hub2wms;

import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.hub2wms.WmsRtnOrderLineQueue;

/**
 * 
 * 
 * @author cheng.su
 * 
 */
@Transactional
public interface WmsRtnOrderLineQueueDao extends GenericEntityDao<WmsRtnOrderLineQueue, Long> {
    @NativeQuery
    public List<WmsRtnOrderLineQueue> queryRtnId(@QueryParam(value = "rtnId") Long rtnId, RowMapper<WmsRtnOrderLineQueue> rowMapper);


}
