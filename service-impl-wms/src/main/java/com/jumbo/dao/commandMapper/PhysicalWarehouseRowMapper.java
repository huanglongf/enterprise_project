package com.jumbo.dao.commandMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.warehouse.PhysicalWarehouse;

import loxia.dao.support.BaseRowMapper;

public class PhysicalWarehouseRowMapper extends BaseRowMapper<Map<String, PhysicalWarehouse>> {
    // private List<PhysicalWarehouse> plist = new ArrayList<PhysicalWarehouse>();
    private Map<String, PhysicalWarehouse> pmap = new LinkedHashMap<String, PhysicalWarehouse>();

    @Override
    public Map<String, PhysicalWarehouse> mapRow(ResultSet rs, int rowNum) throws SQLException {
        String phname = getResultFromRs(rs, "phname", String.class);
        PhysicalWarehouse ph = pmap.get(phname);
        if (ph == null) {
            ph = new PhysicalWarehouse();
            ph.setName(phname);
            List<OperationUnit> oulist = new ArrayList<OperationUnit>();
            oulist.add(getOperationUnit(rs));
            ph.setWhou(oulist);
            pmap.put(phname, ph);
        } else {
            ph.getWhou().add(getOperationUnit(rs));
        }
        return pmap;
    }

    private OperationUnit getOperationUnit(ResultSet rs) throws SQLException {
        OperationUnit ou = new OperationUnit();
        String code = getResultFromRs(rs, "code", String.class);
        String name = getResultFromRs(rs, "oname", String.class);
        ou.setCode(code);
        ou.setName(name);
        ou.setLastModifyTime(new Date());
        return ou;
    }
}
