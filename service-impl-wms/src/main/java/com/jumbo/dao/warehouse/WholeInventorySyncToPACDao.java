package com.jumbo.dao.warehouse;

import java.util.Date;
import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.warehouse.WholeInventorySyncToPAC;
import com.jumbo.wms.model.warehouse.WholeInventorySyncToPACCommand;

@Transactional
public interface WholeInventorySyncToPACDao extends GenericEntityDao<WholeInventorySyncToPAC, Long> {
    
    @NativeUpdate
    public void totalInventorySynToPAC(@QueryParam(value = "datetime") Date datetime);
    
    @NativeQuery
	public List<WholeInventorySyncToPAC> queryTotalInventory(@QueryParam(value = "datetime") Date datetime, @QueryParam(value = "channelCode") String channelCode, RowMapper<WholeInventorySyncToPAC> r);

    @NativeQuery
    public List<String> queryTotalInventoryChannelCode(@QueryParam(value = "datetime") Date datetime ,SingleColumnRowMapper<String> r);
    
    @NativeUpdate
    public void addTotalInventoryLog();
    
    @NativeUpdate
    public void deleteTotalInventory();
    
    @NativeQuery
    public WholeInventorySyncToPACCommand findSyncInfo(@QueryParam(value = "datetime") Date datetime, RowMapper<WholeInventorySyncToPACCommand> r);
}
