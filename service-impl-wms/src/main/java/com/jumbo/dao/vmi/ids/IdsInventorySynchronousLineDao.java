package com.jumbo.dao.vmi.ids;

import java.util.List;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.ids.IdsInventorySynchronousLine;
@Transactional
public interface IdsInventorySynchronousLineDao extends GenericEntityDao<IdsInventorySynchronousLine, Long>{

    @NamedQuery
    List<IdsInventorySynchronousLine> findidsinvSynLine( @QueryParam(value = "invid") Long invid);
    
    @NativeQuery
    List<IdsInventorySynchronousLine> findInvSynLineById(@QueryParam(value = "invId") Long invId, RowMapper<IdsInventorySynchronousLine> orders);
    
    @NativeQuery
    List<IdsInventorySynchronousLine> findOutInvSynLineById(@QueryParam(value = "invSynId") Long invSynId, RowMapper<IdsInventorySynchronousLine> orders);
    
    @NativeQuery
    List<IdsInventorySynchronousLine> findinInvSynLineById(@QueryParam(value = "invSynId") Long invSynId, RowMapper<IdsInventorySynchronousLine> orders);
}
