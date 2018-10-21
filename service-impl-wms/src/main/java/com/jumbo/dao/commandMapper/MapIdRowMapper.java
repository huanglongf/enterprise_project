package com.jumbo.dao.commandMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import loxia.dao.support.BaseRowMapper;

public class MapIdRowMapper extends BaseRowMapper<Map<Long, String>> {

    private Map<Long, String> map = new HashMap<Long, String>();

    public Map<Long, String> mapRow(ResultSet rs, int rowNum) throws SQLException {
        Long id = getResultFromRs(rs, "ID", Long.class);
        String value = getResultFromRs(rs, "CODE", String.class);
        if (id != null) {
            map.put(id, value);
        }
        return map;
    }
}
