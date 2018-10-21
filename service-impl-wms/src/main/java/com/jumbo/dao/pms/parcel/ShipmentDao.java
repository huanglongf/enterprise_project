package com.jumbo.dao.pms.parcel;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.pms.model.command.cond.ParcelSFResponse;
import com.jumbo.pms.model.parcel.Shipment;

@Transactional
public interface ShipmentDao extends GenericEntityDao<Shipment, Long> {
    
    @NativeQuery(model = ParcelSFResponse.class)
    ParcelSFResponse findByMultiCodeForResponse(@QueryParam("platformOrderCode") String platformOrderCode, @QueryParam("omsOrderCode") String omsOrderCode, @QueryParam("platformRaCode") String platformRaCode, @QueryParam("omsRaCode") String omsRaCode, @QueryParam("mailNo") String mailNo);
    
    @NativeQuery(model = Shipment.class)
    Shipment findByMultiCode(@QueryParam("platformOrderCode") String platformOrderCode, @QueryParam("omsOrderCode") String omsOrderCode, @QueryParam("platformRaCode") String platformRaCode, @QueryParam("omsRaCode") String omsRaCode);
    
    @NativeUpdate
    int updateParcelCountById(@QueryParam("id") Long id);
    
    @NamedQuery
    Shipment findByOmsOrderCode(@QueryParam("omsOrderCode") String omsOrderCode, @QueryParam("type") Integer type);
    
    @NamedQuery
    Shipment findByOmsRaCode(@QueryParam("omsRaCode") String omsRaCode, @QueryParam("type") Integer type);
    
}