package com.jumbo.dao.inventorySnapshot;

import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.inventorySnapShot.InvWhFullInventory;
import com.jumbo.wms.model.inventorySnapShot.InventorySnapShot;

@Transactional
public interface InventorySnapShotDao extends GenericEntityDao<InventorySnapShot, Long> {

    /**
     * 获取同步库存给IM数据
     * 
     * @return
     */
    @NativeQuery
    List<InvWhFullInventory> findInvWhFullInventoryToIm(@QueryParam("batch") String batch, RowMapper<InvWhFullInventory> r);

    /**
     * 获取同步IM数据完成汇总信息
     * 
     * @param batch
     * @param r
     * @return
     */
    @NativeQuery
    String getInvWhFullInventorySuccessToIm(@QueryParam("batch") String batch, SingleColumnRowMapper<String> r);
}
