package com.jumbo.dao.warehouse;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;
import loxia.dao.Sort;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.trans.TransAreaGroup;
import com.jumbo.wms.model.trans.TransAreaGroupCommand;

/**
 * 配送范围
 * 
 * @author xiaolong.fei
 * 
 */

@Transactional
public interface TransAreaGroupDao extends GenericEntityDao<TransAreaGroup, Long> {
    /**
     * 根据编码查找配送范围
     * 
     * @param code
     */
    @NamedQuery
    TransAreaGroup findTransAreaByCode(@QueryParam(value = "code") String code);

    @NativeQuery(pagable = true)
    Pagination<TransAreaGroupCommand> findTransAreaList(int start, int pageSize, @QueryParam(value = "cstmId") Long csId, BeanPropertyRowMapper<TransAreaGroupCommand> r, Sort[] sorts);

    @NativeQuery(pagable = true)
    Pagination<TransAreaGroupCommand> findUpdateTransAreaList(int start, int pageSize, @QueryParam(value = "cstmId") Long csId, BeanPropertyRowMapper<TransAreaGroupCommand> r, Sort[] sorts);
    
    @NativeUpdate
    void updateTransArea(@QueryParam(value = "id") Long id, @QueryParam(value = "name") String name, @QueryParam(value = "status") Long status);
}
