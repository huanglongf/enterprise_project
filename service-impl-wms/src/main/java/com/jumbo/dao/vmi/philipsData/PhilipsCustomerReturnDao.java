package com.jumbo.dao.vmi.philipsData;

import java.util.List;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.philipsData.PhilipsCustomerReturn;

@Transactional
public interface PhilipsCustomerReturnDao extends GenericEntityDao<PhilipsCustomerReturn, Long> {

    @NamedQuery
    public List<PhilipsCustomerReturn> getCRByStatus(@QueryParam("status") Integer status);

    @NativeUpdate
    public void updateCRStatusByID(@QueryParam("crId") Long crId, @QueryParam("status") Integer status, @QueryParam("errorMsg") String errorMsg);

    @NamedQuery
    public PhilipsCustomerReturn getCRByOrderCode(@QueryParam("orderCode") String orderCode);

    @NativeQuery
    public PhilipsCustomerReturn getCustomerReturnByConfirmId(@QueryParam("confrimId") String confirmId, BeanPropertyRowMapper<PhilipsCustomerReturn> beanPropertyRowMapper);
}
