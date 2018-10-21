package com.jumbo.dao.hub2wms;

import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.RowMapper;

import com.jumbo.wms.model.hub2wms.WmsInfoLogOms;

public interface WmsInfoLogOmsDao extends GenericEntityDao<WmsInfoLogOms, Long> {
    @NativeQuery
    public List<WmsInfoLogOms> queryInfoLog(@QueryParam(value = "orderId") Long orderId, RowMapper<WmsInfoLogOms> rowMapper);
    
    @NativeUpdate
    void updateInfoLogByOrderStatus(@QueryParam(value = "orderStatus") Long orderStatus, @QueryParam(value = "newOrderStatus") Long newOrderStatus);
    
}
