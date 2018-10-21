package com.jumbo.dao.vmi.philipsData;

import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import com.jumbo.wms.model.vmi.philipsData.PhilipsGoodsMovementLine;

@Transactional
public interface PhilipsGoodsMovementLineDao extends GenericEntityDao<PhilipsGoodsMovementLine, Long> {

    @NativeQuery
    List<PhilipsGoodsMovementLine> getGoodsMovementLinesByGoodsmId(@QueryParam("goodSmId") Long goodSmId, RowMapper<PhilipsGoodsMovementLine> rowMapper);
}
