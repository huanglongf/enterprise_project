package com.jumbo.dao.warehouse;

import java.util.List;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.warehouse.PriorityChannelOms;

@Transactional
public interface PriorityChannelOmsDao extends GenericEntityDao<PriorityChannelOms, Long> {

    @NamedQuery
    List<PriorityChannelOms> findAllPriority();

    @NativeQuery
    Long findCountAllPriority(SingleColumnRowMapper<Long> r);

    @NamedQuery
    List<PriorityChannelOms> findAllPriorityByQty();
}
