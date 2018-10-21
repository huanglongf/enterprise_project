package com.jumbo.dao.commandMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import loxia.dao.support.BaseRowMapper;

import com.jumbo.wms.model.baseinfo.SkuCategories;

public class SkuCategoriesRowMapper extends BaseRowMapper<SkuCategories> {

    @Override
    public SkuCategories mapRow(ResultSet rs, int rowNum) throws SQLException {
        SkuCategories sc = new SkuCategories();
        sc.setId(getResultFromRs(rs, "ID", Long.class));
        sc.setIsPickingCategories(getResultFromRs(rs, "IS_PICKING_CATEGORIES", Boolean.class));
        sc.setSedPickingskuQty(getResultFromRs(rs, "SED_PICKINGSKU_QTY", Long.class));
        sc.setSkuCategoriesName(getResultFromRs(rs, "SKU_CATEGORIES_NAME", String.class));
        sc.setSkuMaxLimit(getResultFromRs(rs, "SKU_MAX_LIMIT", Integer.class));
        Long parentId = getResultFromRs(rs, "PARENT_SKU_CATEGORIES_ID", Long.class);
        if (parentId != null) {
            SkuCategories scp = new SkuCategories();
            scp.setId(parentId);
            sc.setSkuCategories(scp);
        }
        return sc;
    }

}
