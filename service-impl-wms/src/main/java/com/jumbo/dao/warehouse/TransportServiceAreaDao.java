package com.jumbo.dao.warehouse;

import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Sort;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.trans.TransportServiceArea;

/**
 * 配送范围明细
 * 
 * @author xiaolong.fei
 * 
 */
@Transactional
public interface TransportServiceAreaDao extends GenericEntityDao<TransportServiceArea, Long> {
    @NativeQuery
    List<TransportServiceArea> findTransServiceAreaById(@QueryParam(value = "groupId") String groupId, RowMapper<TransportServiceArea> rowMap, Sort[] sorts);

    @NativeUpdate
    void deleteTransAareByGId(@QueryParam(value = "groupId") Long groupId);

}
