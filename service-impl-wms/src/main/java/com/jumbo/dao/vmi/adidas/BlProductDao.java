package com.jumbo.dao.vmi.adidas;

import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.warehouse.BlProduct;

@Transactional
public interface BlProductDao extends GenericEntityDao<BlProduct, Long> {



    // @NamedQuery
    // List<LevisDeliveryOrder> findByPoCode(@QueryParam("poCode") String poCode);

    @NativeQuery
    String findSingelCode(@QueryParam("code") String code, RowMapper<String> r);
    //
    // @NativeUpdate
    // void createDeliveryReceive(@QueryParam("staId") Long staId);
    //
    // @NativeQuery
    // BigDecimal findAdjReceviceSeq(RowMapper<BigDecimal> r);
    //
    // @NativeUpdate
    // void createAdjReceive(@QueryParam("staId") Long staId, @QueryParam("seq") String seq);
    //
    // @NativeUpdate
    // void createRtnReceive(@QueryParam("staid") Long staid);
    //
    // @NativeUpdate
    // void createInvCkReceive(@QueryParam("invckid") Long invckid);
    //
    // @NativeUpdate
    // void createTransOut(@QueryParam("staId") Long staId);
    //
    // @NativeUpdate
    // void createTransIn(@QueryParam("staId") Long staId);

}
