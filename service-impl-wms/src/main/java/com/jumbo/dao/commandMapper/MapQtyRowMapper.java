package com.jumbo.dao.commandMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import loxia.dao.support.BaseRowMapper;

public class MapQtyRowMapper extends BaseRowMapper<Map<Long, Long>> {

    private Map<Long, Long> map = new HashMap<Long, Long>();

    public Map<Long, Long> mapRow(ResultSet rs, int rowNum) throws SQLException {
        Long qty = getResultFromRs(rs, "QTY", Long.class);
        Long id = getResultFromRs(rs, "ID", Long.class);
        if (qty != null && id != null) {
            map.put(id, qty);
        }
        return map;
    }
}
