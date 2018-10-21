package com.jumbo.dao.commandMapper;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

import loxia.dao.support.BaseRowMapper;

import com.jumbo.wms.model.jasperReport.InvoiceNumObj;

public class InvoiceNumRowMapper extends BaseRowMapper<Map<String, InvoiceNumObj>> {

    /**
     * key stacode
     */
    private Map<String, InvoiceNumObj> map = new LinkedHashMap<String, InvoiceNumObj>();

    public Map<String, InvoiceNumObj> mapRow(ResultSet rs, int rowNum) throws SQLException {
        InvoiceNumObj invoiceNumObj = new InvoiceNumObj();

        String staCode = getResultFromRs(rs, "code", String.class);
        invoiceNumObj.setCode(staCode);

        Integer needInvoice = getResultFromRs(rs, "needInvoice", Integer.class);
        invoiceNumObj.setNeedInvoice(needInvoice);

        Integer invoiceType = getResultFromRs(rs, "invoiceType", Integer.class);
        invoiceNumObj.setInvoiceType(invoiceType);

        BigDecimal billingManual = getResultFromRs(rs, "billingManual", BigDecimal.class);
        invoiceNumObj.setBillingManual(billingManual);

        Integer staType = getResultFromRs(rs, "staType", Integer.class);
        invoiceNumObj.setStaType(staType);

        Integer num = getResultFromRs(rs, "num", Integer.class);
        invoiceNumObj.setNum(num);

        map.put(staCode, invoiceNumObj);
        return map;
    }
}
