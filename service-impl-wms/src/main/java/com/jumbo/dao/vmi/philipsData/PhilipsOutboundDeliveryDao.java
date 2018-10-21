package com.jumbo.dao.vmi.philipsData;

import java.util.List;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.philipsData.PhilipsOutboundDelivery;

@Transactional
public interface PhilipsOutboundDeliveryDao extends GenericEntityDao<PhilipsOutboundDelivery, Long> {

    @NamedQuery
    public PhilipsOutboundDelivery getOutboundByOrderCode(@QueryParam("orderCode") String orderCode);

    @NamedQuery
    public List<PhilipsOutboundDelivery> getOutboundByStatus(@QueryParam("status") Integer status);

    @NativeUpdate
    public void updateOutBoundStatus(@QueryParam("outboundId") Long outboundId, @QueryParam("status") Integer status, @QueryParam("errorMsg") String errorMsg);

    @NativeQuery
    public PhilipsOutboundDelivery getOutboundDeliveryByConfirmId(@QueryParam("confrimId") String confirmId, BeanPropertyRowMapper<PhilipsOutboundDelivery> beanPropertyRowMapper);
}
