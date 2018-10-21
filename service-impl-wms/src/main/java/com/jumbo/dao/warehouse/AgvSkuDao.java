package com.jumbo.dao.warehouse;

import java.util.List;

import loxia.annotation.NamedQuery;
import loxia.dao.GenericEntityDao;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.warehouse.AgvSku;

@Transactional
public interface AgvSkuDao extends GenericEntityDao<AgvSku, Long> {

    @NamedQuery
    List<AgvSku> findAgvSkuByErrorCount();
}
