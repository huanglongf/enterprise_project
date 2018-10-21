package com.jumbo.dao.warehouse;

import java.util.List;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;
import loxia.dao.Sort;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.warehouse.RelationNike;

@Transactional
public interface RelationNikeDao extends GenericEntityDao<RelationNike, Long> {


    /**
     * 根据系统箱号查找RelationNike
     * 
     * @param sysPid
     * @return
     */
    @NamedQuery
    RelationNike getBySysPid(@QueryParam(value = "sysPid") String sysPid, @QueryParam(value = "whOuId") Long whOuId);


    /**
     * 根据系统箱号查找RelationNike
     * 
     * @param sysPid
     * @return
     */
    @NamedQuery
    RelationNike findSysByPid(@QueryParam(value = "sysPid") String sysPid);

    /**
     * 根据实物箱号查找RelationNike
     * 
     * @param enPid
     * @return
     */
    @NamedQuery
    RelationNike getByEnPid(@QueryParam(value = "enPid") String enPid, @QueryParam(value = "whOuId") Long whOuId);

    /**
     * 根据库查找RelationNike
     * 
     * @param whOuId
     * @return
     */
    @NativeQuery(pagable = true)
    Pagination<RelationNike> findRelationNikeByOuid(int start, int pageSize, Sort[] sorts, @QueryParam(value = "whOuId") Long whOuId, @QueryParam(value = "sysPid") String sysPid, @QueryParam(value = "enPid") String enPid,
            RowMapper<RelationNike> rowMapper);
    
    /**
     * 根据实物箱号查找RelationNike
     * 
     * @param enPid
     * @return
     */
    @NamedQuery
    List<RelationNike> getByEnPidNoOu(@QueryParam(value = "enPid") String enPid);


}
