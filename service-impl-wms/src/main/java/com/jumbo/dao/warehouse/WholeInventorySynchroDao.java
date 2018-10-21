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

import com.jumbo.wms.model.warehouse.WholeInventorySynchro;
import com.jumbo.wms.model.warehouse.WholeInventorySynchroCommand;

@Transactional
public interface WholeInventorySynchroDao extends GenericEntityDao<WholeInventorySynchro, Long> {
    
    @NativeUpdate
    public void totalInventorySyn(@QueryParam(value = "datetime") Date datetime);
    
    @NativeQuery
    public List<WholeInventorySynchro> queryTotalInventory(@QueryParam(value = "datetime") Date datetime ,@QueryParam(value = "ouid") Long ouid, RowMapper<WholeInventorySynchro> r);

    @NativeQuery
    public List<Long> queryTotalInventoryOuId(@QueryParam(value = "datetime") Date datetime ,SingleColumnRowMapper<Long> r);
    
    @NativeUpdate
    public void addTotalInventoryLog();
    
    @NativeUpdate
    public void deleteTotalInventory();
    
    @NativeQuery
    public WholeInventorySynchroCommand findSyncInfo(@QueryParam(value = "datetime") Date datetime, RowMapper<WholeInventorySynchroCommand> r);
}
