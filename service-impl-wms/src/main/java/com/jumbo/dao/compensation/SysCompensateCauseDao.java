package com.jumbo.dao.compensation;

import java.util.List;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.compensation.SysCompensateCause;

@Transactional
public interface SysCompensateCauseDao extends GenericEntityDao<SysCompensateCause, Long> {

    @NamedQuery
    SysCompensateCause getSysCompensateCauseByCode(@QueryParam("code") Integer code);
    
    @NativeQuery(model = SysCompensateCause.class)
    List<SysCompensateCause> findCompensateCauseByTypeId(@QueryParam("claimTypeId") Long claimTypeId);

}
