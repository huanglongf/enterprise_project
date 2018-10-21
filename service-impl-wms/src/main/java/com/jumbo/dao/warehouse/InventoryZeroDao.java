package com.jumbo.dao.warehouse;

import java.util.Date;

import org.springframework.jdbc.core.RowMapper;

import com.jumbo.wms.model.warehouse.InventoryCommand;
import com.jumbo.wms.model.warehouse.InventoryZero;

import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;
import loxia.dao.Sort;

public interface InventoryZeroDao extends GenericEntityDao<InventoryZero, Long> {
    @NativeQuery(pagable = true, withGroupby = true)
    Pagination<InventoryCommand> findCurrentInventoryByPageNew(int start, int pageSize, @QueryParam("barCode") String barCode, @QueryParam("skuCode") String skuCode, @QueryParam("extCode2") String extCode2, @QueryParam("jmCode") String jmCode,
            @QueryParam("skuName") String skuName, @QueryParam("supplierSkuCode") String supplierSkuCode, @QueryParam("brandName") String brandName, @QueryParam("invOwner") String invOwner, @QueryParam("whOuId") Long whOuId,
            @QueryParam("companyid") Long companyid, @QueryParam("isShowZero") Boolean isShowZero, @QueryParam("extCode1") String extCode1, @QueryParam("numberUp") Long numberUp, @QueryParam("amountTo") Long amountTo,
            RowMapper<InventoryCommand> rowMapper, Sort[] sorts);


    @NativeQuery(pagable = true, withGroupby = true)
    Pagination<InventoryCommand> findDetailsInventoryByPageShelfLife(int start, int pageSize, @QueryParam("jmCode") String jmCode, @QueryParam("skuCode") String skuCode, @QueryParam("extCode2") String extCode2, @QueryParam("barCode") String barCode,
            @QueryParam("skuName") String skuName, @QueryParam("supplierSkuCode") String supplierSkuCode, @QueryParam("locationCode") String locationCode, @QueryParam("invOwner") String invOwner, @QueryParam("whOuId") Long whOuId,
            @QueryParam("companyid") Long companyid, @QueryParam("statusId") Long statusId, @QueryParam("warningDate") Integer warningDate, @QueryParam("shelfLife") Integer shelfLife, @QueryParam("startDate") Date startDate,
            @QueryParam("endDate") Date endDate, @QueryParam("isZeroInventory") Integer isZeroInventory, RowMapper<InventoryCommand> rowMapper, Sort[] sorts);
}
