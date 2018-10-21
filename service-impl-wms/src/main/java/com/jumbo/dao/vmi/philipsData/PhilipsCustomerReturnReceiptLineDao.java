package com.jumbo.dao.vmi.philipsData;

import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;
import com.jumbo.wms.model.vmi.philipsData.PhilipsCustomerReturnReceiptLine;

import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

@Transactional
public interface PhilipsCustomerReturnReceiptLineDao extends GenericEntityDao<PhilipsCustomerReturnReceiptLine, Long> {

    @NativeQuery
    List<PhilipsCustomerReturnReceiptLine> getCusReturnReceiptLineByGoodsrrId(@QueryParam("goodsrrId") Long goodsrrId, RowMapper<PhilipsCustomerReturnReceiptLine> rowMapper);
}
