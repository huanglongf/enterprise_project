package com.jumbo.dao.warehouse;

import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.transaction.annotation.Transactional;

import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import com.jumbo.wms.model.warehouse.DeailyInventory;

@Transactional
public interface DeailyInventoryDao extends GenericEntityDao<DeailyInventory, Long> {
    @NativeUpdate
    public void addDeailyInventory(@QueryParam(value = "datetime") Date datetime);
	

    @NativeQuery
    public List<DeailyInventory> queryDeailyInventory(@QueryParam(value = "datetime") Date datetime, @QueryParam(value = "ouid") Long ouid, RowMapper<DeailyInventory> r);

    @NativeQuery
    public List<Long> queryDeailyInventoryOuid(@QueryParam(value = "datetime") Date datetime, SingleColumnRowMapper<Long> r);

    @NativeUpdate
    public void addDeailyInventoryLog();

    @NativeUpdate
    public int updateDeailyInventoryLog(@QueryParam(value = "createTime") Date createTime);

    @NativeUpdate
    public int deleteDeailyInventoryLog();


}
