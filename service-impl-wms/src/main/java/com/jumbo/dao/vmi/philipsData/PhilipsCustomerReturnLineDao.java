package com.jumbo.dao.vmi.philipsData;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import loxia.annotation.NamedQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import com.jumbo.wms.model.vmi.philipsData.PhilipsCustomerReturnLine;

@Transactional
public interface PhilipsCustomerReturnLineDao extends GenericEntityDao<PhilipsCustomerReturnLine, Long> {
    @NamedQuery
    public List<PhilipsCustomerReturnLine> getCRLineByCRID(@QueryParam("crid") Long crid);

}
