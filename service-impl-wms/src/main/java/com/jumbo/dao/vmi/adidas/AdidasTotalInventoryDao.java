package com.jumbo.dao.vmi.adidas;

import java.util.Date;

import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.hub2wms.WmsAdidasTotalInventory;
import com.jumbo.wms.model.vmi.adidasData.AdidasTotalInventory;

@Transactional
public interface AdidasTotalInventoryDao extends GenericEntityDao<AdidasTotalInventory, Long> {

    @NativeQuery(pagable = true)
    Pagination<WmsAdidasTotalInventory> getAdidasTotalInventoryCondition(int start, int pagesize, @QueryParam("systemKey") String systemKey, @QueryParam("startTime") Date startTime, @QueryParam("endTime") Date endTime,
            BeanPropertyRowMapper<WmsAdidasTotalInventory> beanPropertyRowMapper);


}
