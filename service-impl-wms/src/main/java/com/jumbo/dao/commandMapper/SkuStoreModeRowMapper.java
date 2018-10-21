package com.jumbo.dao.commandMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.jumbo.wms.model.warehouse.InboundStoreMode;

import loxia.dao.support.BaseRowMapper;

public class SkuStoreModeRowMapper extends BaseRowMapper<Map<Long, InboundStoreMode>> {

    private Map<Long, InboundStoreMode> map = new HashMap<Long, InboundStoreMode>();

    public Map<Long, InboundStoreMode> mapRow(ResultSet rs, int rowNum) throws SQLException {
        Long id = getResultFromRs(rs, "SKU_ID", Long.class);
        Integer mode = getResultFromRs(rs, "STORE_MODE", Integer.class);
        if (mode == null) {
            map.put(id, InboundStoreMode.TOGETHER);
        } else {
            map.put(id, InboundStoreMode.valueOf(mode.intValue()));
        }
        return map;
    }
}
