package com.jumbo.dao.vmi.philipsData;

import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.philipsData.PhilipsGoodsReceiptLine;

import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

@Transactional
public interface PhilipsGoodsReceiptLineDao extends GenericEntityDao<PhilipsGoodsReceiptLine, Long> {

    @NativeQuery
    List<PhilipsGoodsReceiptLine> getGRLineBySlipCode(@QueryParam("slipCode") String slipCode, @QueryParam("staId") Long staId, RowMapper<PhilipsGoodsReceiptLine> rowMapper);

    @NativeQuery
    List<PhilipsGoodsReceiptLine> getGoodsReceiptLinesByGoodsrId(@QueryParam("goodsrId") Long goodsrId, RowMapper<PhilipsGoodsReceiptLine> rowMapper);
}
