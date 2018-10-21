package com.jumbo.dao.vmi.levis;

import java.math.BigDecimal;
import java.util.List;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;



import com.jumbo.wms.model.vmi.levisData.LevisDeliveryOrder;

@Transactional
public interface LevisDeliveryOrderDao extends GenericEntityDao<LevisDeliveryOrder, Long> {

    @NativeQuery
    List<String> findUnDoPoCode(RowMapper<String> r);

    @NativeQuery
    String findShopVmiCodeByPoCode(@QueryParam("poCode") String poCode, RowMapper<String> r);

    @NamedQuery
    List<LevisDeliveryOrder> findByPoCode(@QueryParam("poCode") String poCode);

    @NativeQuery
    String findSingelPoCode(@QueryParam("poCode") String poCode, RowMapper<String> r);

    @NativeUpdate
    void createDeliveryReceive(@QueryParam("staId") Long staId);

    @NativeQuery
    BigDecimal findAdjReceviceSeq(RowMapper<BigDecimal> r);
    
    @NativeUpdate
    void createAdjReceive(@QueryParam("staId") Long staId,@QueryParam("seq") String seq);

    @NativeUpdate
    void createRtnReceive(@QueryParam("staid") Long staid);

    @NativeUpdate
    void createInvCkReceive(@QueryParam("invckid") Long invckid);

    @NativeUpdate
    void createTransOut(@QueryParam("staId") Long staId);

    @NativeUpdate
    void createTransIn(@QueryParam("staId") Long staId);

}
