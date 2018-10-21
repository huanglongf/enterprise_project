package com.jumbo.dao.vmi.philipsData;

import java.math.BigDecimal;

import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import org.springframework.jdbc.core.RowMapper;
import com.jumbo.wms.model.vmi.philipsData.PhilipsGoodsReceipt;

import loxia.annotation.NativeQuery;
import loxia.dao.GenericEntityDao;

@Transactional
public interface PhilipsGoodsReceiptDao extends GenericEntityDao<PhilipsGoodsReceipt, Long> {

    @NativeQuery
    BigDecimal findReceiptCodeSequence(SingleColumnRowMapper<BigDecimal> singleColumnRowMapper);

    @NativeQuery
    public List<PhilipsGoodsReceipt> getGoodsReceiptsNoBatchId(RowMapper<PhilipsGoodsReceipt> rowMapper);
}
