package com.jumbo.dao.vmi.cathKidstonData;

import java.util.List;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.cathKidstonData.CKReceive;

@Transactional
public interface CKReceiveDao extends GenericEntityDao<CKReceive, Long> {

    @NativeQuery
    CKReceive findCkReceiveByDeliveryNo(@QueryParam(value = "deliveryNo") String deliveryNo, RowMapper<CKReceive> rowMapper);

    @NativeQuery
    List<String> findNikeVmiStockIn(SingleColumnRowMapper<String> singleColumnRowMapper);

    @NamedQuery
    List<CKReceive> getByReferenceNo(@QueryParam(value = "deliveryNo") String deliveryNo);
}
