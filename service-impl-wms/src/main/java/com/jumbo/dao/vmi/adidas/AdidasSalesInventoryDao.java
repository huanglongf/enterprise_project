package com.jumbo.dao.vmi.adidas;

import java.util.Date;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.hub2wms.WmsAdidasSalesInventory;
import com.jumbo.wms.model.vmi.adidasData.AdidasSalesInventory;

import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;

@Transactional
public interface AdidasSalesInventoryDao extends GenericEntityDao<AdidasSalesInventory, Long> {
    @NativeQuery(pagable = true)
    Pagination<WmsAdidasSalesInventory> getAdidasSalesInventoryCondition(int start, int pagesize, @QueryParam("systemKey") String systemKey, @QueryParam("startTime") Date startTime, @QueryParam("endTime") Date endTime,
            BeanPropertyRowMapper<WmsAdidasSalesInventory> beanPropertyRowMapper);

    @NativeQuery(pagable = true)
    Pagination<WmsAdidasSalesInventory> getWmsSalesInventoryHubServiceCondition(int start, int pagesize, @QueryParam("systemKey") String systemKey, @QueryParam("startTime") Date startTime, @QueryParam("endTime") Date endTime,
            BeanPropertyRowMapper<WmsAdidasSalesInventory> beanPropertyRowMapper);

    @NativeQuery(pagable = true)
    Pagination<WmsAdidasSalesInventory> getSalesInventoryDetailByBatchCode(int start, int pagesize, @QueryParam("batchCode") String batchCode,
            BeanPropertyRowMapper<WmsAdidasSalesInventory> beanPropertyRowMapper);
}
