package com.jumbo.dao.commandMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import loxia.dao.support.BaseRowMapper;

public class MapRowMapper extends BaseRowMapper<Map<String, Long>> {

    private Map<String, Long> map = new HashMap<String, Long>();

    public Map<String, Long> mapRow(ResultSet rs, int rowNum) throws SQLException {
        String code = getResultFromRs(rs, "CODE", String.class);
        Long id = getResultFromRs(rs, "ID", Long.class);
        if (code != null && id != null) {
            map.put(code, id);
        }
        return map;
    }
}
