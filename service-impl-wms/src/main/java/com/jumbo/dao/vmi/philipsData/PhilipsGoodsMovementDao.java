package com.jumbo.dao.vmi.philipsData;

import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.philipsData.PhilipsGoodsMovement;

import loxia.annotation.NativeQuery;
import loxia.dao.GenericEntityDao;

@Transactional
public interface PhilipsGoodsMovementDao extends GenericEntityDao<PhilipsGoodsMovement, Long> {

    @NativeQuery
    List<PhilipsGoodsMovement> getpGoodsMovementsNoBatchId(RowMapper<PhilipsGoodsMovement> rowMapper);
}
