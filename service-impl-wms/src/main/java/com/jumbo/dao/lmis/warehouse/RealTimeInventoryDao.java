package com.jumbo.dao.lmis.warehouse;

import loxia.annotation.NativeQuery;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;
import loxia.dao.Sort;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.lmis.RealTimeInventory;

@Transactional
public interface RealTimeInventoryDao extends GenericEntityDao<RealTimeInventory, Long> {
    @NativeQuery(pagable = true)
    Pagination<RealTimeInventory> findRealTimeInventoryLists(int start, int pageSize, RowMapper<RealTimeInventory> rowMapper, Sort[] sorts);
}
