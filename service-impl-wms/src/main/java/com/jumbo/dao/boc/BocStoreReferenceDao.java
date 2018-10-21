package com.jumbo.dao.boc;

import loxia.annotation.NamedQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.boc.BocStoreReference;

@Transactional
public interface BocStoreReferenceDao extends GenericEntityDao<BocStoreReference, Long> {

    @NamedQuery
    BocStoreReference findBocStoreReferenceBySource(@QueryParam("source") String source);
}
