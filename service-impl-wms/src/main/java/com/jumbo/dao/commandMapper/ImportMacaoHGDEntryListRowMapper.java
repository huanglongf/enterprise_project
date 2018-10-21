package com.jumbo.dao.commandMapper;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import loxia.dao.support.BaseRowMapper;

import com.jumbo.wms.model.jasperReport.ImportHGDEntryListLinesObj;
import com.jumbo.wms.model.jasperReport.ImportHGDEntryListObj;

public class ImportMacaoHGDEntryListRowMapper extends BaseRowMapper<Map<Long, ImportHGDEntryListObj>> {

    /**
     * key sta id
     */
    private Map<Long, ImportHGDEntryListObj> map = new LinkedHashMap<Long, ImportHGDEntryListObj>();

    public Map<Long, ImportHGDEntryListObj> mapRow(ResultSet rs, int rowNum) throws SQLException {
        Long staId = getResultFromRs(rs, "STAID", Long.class);// 作业单ID
        ImportHGDEntryListObj ie = map.get(staId);
        if (ie == null) {
            // 获取主模板数据
            ie = new ImportHGDEntryListObj();
            String staCode = getResultFromRs(rs, "STACODE", String.class);
            ie.setStaCode(staCode);
            String receiver = getResultFromRs(rs, "RECEIVER", String.class);
            ie.setReceiver(receiver);
            String address = getResultFromRs(rs, "ADDRESS", String.class);
            ie.setAddress(address);
            BigDecimal totalQty = getResultFromRs(rs, "TOTALQTY", BigDecimal.class);
            ie.setTotalQty(totalQty);
            try {
                BigDecimal totalWeight = getResultFromRs(rs, "TOTALWEIGHT", BigDecimal.class);
                ie.setTotalWeight(totalWeight);
            } catch (Exception e) {}
            try {
                BigDecimal totalActual = getResultFromRs(rs, "TOTALACTUAL", BigDecimal.class);
                ie.setTotalActual(totalActual);
            } catch (Exception e) {}
            String year = getResultFromRs(rs, "YEAR", String.class);
            ie.setYear(year);
            String month = getResultFromRs(rs, "MONTH", String.class);
            ie.setMonth(month);
            String day = getResultFromRs(rs, "DAY", String.class);
            ie.setDay(day);

            List<ImportHGDEntryListLinesObj> ieList = new ArrayList<ImportHGDEntryListLinesObj>();
            ieList.add(getIeObj(rs, ieList.size()));
            ie.setLines(ieList);
            map.put(staId, ie);
        } else {
            ie.getLines().add(getIeObj(rs, ie.getLines().size()));
        }
        return map;
    }

    private ImportHGDEntryListLinesObj getIeObj(ResultSet rs, int size) throws SQLException {
        ImportHGDEntryListLinesObj ieLine = new ImportHGDEntryListLinesObj();
        String skuName = getResultFromRs(rs, "SKUNAME", String.class);
        ieLine.setSkuName(skuName);
        String supplineCode = getResultFromRs(rs, "SUPPLINECODE", String.class);
        ieLine.setSupplineCode(supplineCode);
        try {
            BigDecimal qty = getResultFromRs(rs, "QTY", BigDecimal.class);
            ieLine.setQty(qty);
        } catch (Exception e) {}
        String countryOfOrigin = getResultFromRs(rs, "COUNTRYOFORIGIN", String.class);
        ieLine.setCountryOfOrigin(countryOfOrigin);
        try {
            BigDecimal suttleWeight = getResultFromRs(rs, "SUTTLEWEIGHT", BigDecimal.class);
            ieLine.setSuttleWeight(suttleWeight);
        } catch (Exception e) {}
        try {
            BigDecimal unitPrice = getResultFromRs(rs, "UNITPRICE", BigDecimal.class);
            ieLine.setUnitPrice(unitPrice);
        } catch (Exception e) {}
        return ieLine;
    }
}
