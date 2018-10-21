package com.jumbo.dao.automaticEquipment;

import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.automaticEquipment.GoodsCollectionLog;

import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;



/**
 * @author hui.li
 * 
 */
@Transactional
public interface GoodsCollectionLogDao extends GenericEntityDao<GoodsCollectionLog, Long> {

    @NativeQuery(pagable = true)
    Pagination<GoodsCollectionLog> findGoodsCollectionLog(int start, int pageSize, @QueryParam Map<String, Object> m, RowMapper<GoodsCollectionLog> rowMapper);

    @NativeUpdate
    void resetGoodsCollectionLogById(@QueryParam(value = "gcId") Long gcId, @QueryParam(value = "userId") Long userId);

    @NativeUpdate
    void resetGoodsCollectionLogByPlId(@QueryParam(value = "plId") Long plId, @QueryParam(value = "userId") Long userId);
	
}
