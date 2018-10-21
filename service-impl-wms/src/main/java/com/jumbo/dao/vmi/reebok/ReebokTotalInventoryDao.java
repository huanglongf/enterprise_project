package com.jumbo.dao.vmi.reebok;

import java.util.Date;

import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.hub2wms.WmsReebokTotalInventory;
import com.jumbo.wms.model.vmi.reebokData.ReebokTotalInventory;

@Transactional
public interface ReebokTotalInventoryDao extends GenericEntityDao<ReebokTotalInventory, Long> {

    @NativeQuery(pagable = true)
    Pagination<WmsReebokTotalInventory> getReebokTotalInventoryCondition(int start, int pagesize, @QueryParam("systemKey") String systemKey, @QueryParam("startTime") Date startTime, @QueryParam("endTime") Date endTime,
            BeanPropertyRowMapper<WmsReebokTotalInventory> beanPropertyRowMapper);


}
