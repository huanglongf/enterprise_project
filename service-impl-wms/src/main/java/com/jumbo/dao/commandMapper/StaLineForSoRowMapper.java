package com.jumbo.dao.commandMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import com.jumbo.wms.model.warehouse.StaLineCommand;

import loxia.dao.support.BaseRowMapper;

/**
 * 订单过仓使用转换ROWMAP
 * 
 * @author sjk
 * 
 */
public class StaLineForSoRowMapper extends BaseRowMapper<Map<String, List<StaLineCommand>>> {

    private Map<String, List<StaLineCommand>> map = new LinkedHashMap<String, List<StaLineCommand>>();

    public Map<String, List<StaLineCommand>> mapRow(ResultSet rs, int rowNum) throws SQLException {
        String soCode = getResultFromRs(rs, "SLIPCODE", String.class);
        List<StaLineCommand> list = map.get(soCode);
        if (list == null) {
            list = new ArrayList<StaLineCommand>();
        }
        String owner = getResultFromRs(rs, "OWNER", String.class);
        Long quantity = getResultFromRs(rs, "QUANTITY", Long.class);
        Long skuId = getResultFromRs(rs, "SKUID", Long.class);
        StaLineCommand l = new StaLineCommand();
        l.setQuantity(quantity);
        l.setOwner(owner);
        l.setSkuId(skuId);
        list.add(l);
        map.put(soCode, list);
        return map;
    }
}
