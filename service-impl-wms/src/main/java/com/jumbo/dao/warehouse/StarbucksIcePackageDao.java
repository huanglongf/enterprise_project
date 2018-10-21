package com.jumbo.dao.warehouse;

import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.jumbo.wms.model.warehouse.StarbucksIcePackage;

import loxia.annotation.NativeQuery;
import loxia.dao.GenericEntityDao;

public interface StarbucksIcePackageDao extends GenericEntityDao<StarbucksIcePackage, Long> {

    @NativeQuery
    List<StarbucksIcePackage> findStarbucksIcePackage(RowMapper<StarbucksIcePackage> rowMapper);
}
