package com.jumbo.dao.vmi.philipsData;

import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.philipsData.PhilipsCustomerReturnReceipt;

import loxia.annotation.NativeQuery;
import loxia.dao.GenericEntityDao;

@Transactional
public interface PhilipsCustomerReturnReceiptDao extends GenericEntityDao<PhilipsCustomerReturnReceipt, Long> {

    @NativeQuery
    List<PhilipsCustomerReturnReceipt> getpCustomerReturnReceiptNoBatchId(RowMapper<PhilipsCustomerReturnReceipt> rowMapper);

}
