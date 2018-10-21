package com.jumbo.dao.commandMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import loxia.dao.support.BaseRowMapper;

public class MapInvOwnerMapper extends BaseRowMapper<Map<String, Map<String, Long>>> {
	  private Map<String, Map<String, Long>> result = new HashMap<String, Map<String, Long>>();

      public Map<String, Map<String, Long>> mapRow(ResultSet rs, int rowNum) throws SQLException {
          Map<String, Long> codeMap = result.get("CODE");
          Long id = getResultFromRs(rs, "ID", Long.class);
          if (result.get("CODE") == null) {
              codeMap = new HashMap<String, Long>();
          }
          codeMap.put(getResultFromRs(rs, "OWNER", String.class), id);
          String barCode = getResultFromRs(rs, "CODE", String.class);
          result.put(barCode, codeMap);
          return result;
      }
  }
