package com.jumbo.dao.vmi.gucciData;

import java.util.Date;

import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.GucciData.GucciSalesInvHub;
import com.jumbo.wms.model.vmi.GucciData.GucciSalesInventory;

@Transactional
public interface GucciSalesInventoryDao extends GenericEntityDao<GucciSalesInventory, Long> {
    /**
     * 插入可销售库存
     * 
     * @param startTime
     * @return endTime
     */
    @NativeUpdate
    public void insertTotalSalesInv(@QueryParam("ouId") Long ouId, @QueryParam("owner") String owner, @QueryParam("location") String location, @QueryParam("jdaWarehouseCode") String jdaWarehouseCode, @QueryParam("brandCode") String brandCode);

    /**
     * 根据时间段查询可销售库存
     * 
     * @param systemKey
     * @param startTime
     * @return endTime
     */
    @NativeQuery(pagable = true)
    Pagination<GucciSalesInvHub> getGucciSalesInventoryCondition(int start, int pagesize, @QueryParam("systemKey") String systemKey, @QueryParam("startTime") Date startTime, @QueryParam("endTime") Date endTime,
            BeanPropertyRowMapper<GucciSalesInvHub> beanPropertyRowMapper);

}
