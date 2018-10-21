package com.jumbo.dao.compensation;

import java.util.List;

import loxia.annotation.NamedQuery;
import loxia.dao.GenericEntityDao;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.compensation.SysCompensateType;

@Transactional
public interface SysCompensateTypeDao extends GenericEntityDao<SysCompensateType, Long> {

    @NamedQuery
    List<SysCompensateType> getAllSysCompensateTypes();

}
