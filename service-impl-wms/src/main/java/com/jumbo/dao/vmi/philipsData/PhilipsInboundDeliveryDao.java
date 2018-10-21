package com.jumbo.dao.vmi.philipsData;

import java.util.List;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.philipsData.PhilipsInboundDelivery;

@Transactional
public interface PhilipsInboundDeliveryDao extends GenericEntityDao<PhilipsInboundDelivery, Long> {

    @NamedQuery
    public List<PhilipsInboundDelivery> getInboundDeliveryByCode(@QueryParam("inboundCode") String inboundCode);

    @NamedQuery
    public List<PhilipsInboundDelivery> getInboundDeliveryByStatus(@QueryParam("status") Integer status);

    @NativeUpdate
    public void updateInboundDelStatus(@QueryParam("fromStatus") Integer fromStatus, @QueryParam("toStatus") Integer toStatus);

    @NativeQuery
    public PhilipsInboundDelivery getInboundDeliveryByConfirmId(@QueryParam("confrimId") String confirmId, BeanPropertyRowMapper<PhilipsInboundDelivery> beanPropertyRowMapper);
}
