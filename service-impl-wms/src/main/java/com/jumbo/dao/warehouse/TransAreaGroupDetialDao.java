package com.jumbo.dao.warehouse;

import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.trans.TransAreaGroupDetial;

/**
 * 配送范围明细
 * 
 * @author xiaolong.fei
 * 
 */
@Transactional
public interface TransAreaGroupDetialDao extends GenericEntityDao<TransAreaGroupDetial, Long> {
    @NativeQuery
    List<TransAreaGroupDetial> findTransAreaGDetiaByGId(@QueryParam(value = "groupId") Long groupId, RowMapper<TransAreaGroupDetial> rowMap);

    @NativeUpdate
    void deleteTransADetialByGId(@QueryParam(value = "groupId") Long groupId);

    /**
     * 根据配送范围获取明细
     * 
     * @param trId
     * @param rowMapper
     * @return
     */
    @NativeQuery
    List<TransAreaGroupDetial> findDetialsByTag(@QueryParam(value = "tagId") Long trId, RowMapper<TransAreaGroupDetial> rowMapper);
}
