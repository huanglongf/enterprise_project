package com.jumbo.dao.vmi.cj;

import java.util.List;

import loxia.annotation.NamedQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.cj.CjLgOrderCodeUrl;

@Transactional
public interface CjLgOrderCodeUrlDao extends GenericEntityDao<CjLgOrderCodeUrl, Long> {
    @NamedQuery
    CjLgOrderCodeUrl getCjLgOrderCodeUrlByStaId(@QueryParam("staId") Long staId);

    @NamedQuery
    List<CjLgOrderCodeUrl> getCjLgOrderCodeUrlByStatus1(@QueryParam("status1") Integer status1);

    @NamedQuery
    List<CjLgOrderCodeUrl> getCjLgOrderCodeUrlByStatus2(@QueryParam("status2") Integer status2);
}
