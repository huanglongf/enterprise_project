package com.jumbo.dao.commandMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import loxia.dao.support.BaseRowMapper;

public class MapCodeRowMapper extends BaseRowMapper<Map<String, String>> {

    private Map<String, String> map = new HashMap<String, String>();

    public Map<String, String> mapRow(ResultSet rs, int rowNum) throws SQLException {
        String key = getResultFromRs(rs, "KEY", String.class);
        String value = getResultFromRs(rs, "VALUE", String.class);
        if (key != null) {
            map.put(key, value);
        }
        return map;
    }
}
