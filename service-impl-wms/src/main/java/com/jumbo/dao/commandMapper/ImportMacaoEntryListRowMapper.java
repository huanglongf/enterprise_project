package com.jumbo.dao.commandMapper;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import loxia.dao.support.BaseRowMapper;

import com.jumbo.wms.model.jasperReport.ImportEntryListLinesObj;
import com.jumbo.wms.model.jasperReport.ImportEntryListObj;

public class ImportMacaoEntryListRowMapper extends BaseRowMapper<Map<Long, ImportEntryListObj>> {

    /**
     * key sta id
     */
    private Map<Long, ImportEntryListObj> map = new LinkedHashMap<Long, ImportEntryListObj>();


    public Map<Long, ImportEntryListObj> mapRow(ResultSet rs, int rowNum) throws SQLException {
        Long staId = getResultFromRs(rs, "STAID", Long.class);// 作业单ID
        int index = 1;
        ImportEntryListObj ie = map.get(staId);
        if (ie == null) {
            // 获取主模板数据
            ie = new ImportEntryListObj();
            String staCode = getResultFromRs(rs, "STACODE", String.class);
            ie.setStaCode(staCode);
            String receiver = getResultFromRs(rs, "RECEIVER", String.class);
            ie.setReceiver(receiver);

            String address = getResultFromRs(rs, "ADDRESS", String.class);
            ie.setAddress(address);
            String telephone = getResultFromRs(rs, "TELEPHONE", String.class);
            if (null != telephone && !"".equals(telephone)) {
                ie.setTelephone(telephone);
            } else {
                String mobile = getResultFromRs(rs, "MOBILE", String.class);
                ie.setTelephone(mobile);
            }
            String zipCode = getResultFromRs(rs, "ZIPCODE", String.class);
            ie.setZipcode(zipCode);
            String vmiCode = getResultFromRs(rs, "VMICODE", String.class);
            String trackingNo = getResultFromRs(rs, "trackingNo", String.class);
            ie.setTrackingNo(trackingNo);
            String slipCode2 = getResultFromRs(rs, "slipCode2", String.class);
            ie.setSlipCode2(slipCode2);

            BigDecimal totalActual = getResultFromRs(rs, "totalActual", BigDecimal.class);
            ie.setTotalActual(totalActual);
            BigDecimal weight = getResultFromRs(rs, "weight", BigDecimal.class);
            ie.setWeight(weight);
            if (vmiCode.equals("BZ_CB")) {
                // Columbia
                ie.setLogoUrl("print_img/columbia.jpg");
            }
            if (vmiCode.equals("BZ_CA")) {
                // cathkidston
                ie.setLogoUrl("print_img/cathkidston.png");
            }
            ie.setIndex(index++);
            ie.setPrintTime(getPrintTime("yyyy-MM-dd HH:mm:ss"));
            List<ImportEntryListLinesObj> ieList = new ArrayList<ImportEntryListLinesObj>();
            ieList.add(getIeObj(rs, ieList.size()));
            BigDecimal totalPrice = BigDecimal.ZERO;
            Integer num = 0;
            for (ImportEntryListLinesObj list : ieList) {
                totalPrice = totalPrice.add(list.getPrice());
                num = num + Integer.parseInt(list.getQty());
                ie.setUnitName(list.getUnitName());
            }
            ie.setLines(ieList);
            ie.setDetailSize(ieList.size());
            ie.setTotalPrice(totalPrice);
            ie.setNum(num);
            map.put(staId, ie);
        } else {
            ie.getLines().add(getIeObj(rs, ie.getLines().size()));
            ie.setDetailSize(ie.getLines().size());
        }
        return map;
    }


    private ImportEntryListLinesObj getIeObj(ResultSet rs, int size) throws SQLException {
        ImportEntryListLinesObj ieLine = new ImportEntryListLinesObj();
        String supplineCode = getResultFromRs(rs, "supplineCode", String.class);
        ieLine.setSupplineCode(supplineCode);
        String skuName = getResultFromRs(rs, "SKUNAME", String.class);
        ieLine.setSkuName(skuName);
        String color = getResultFromRs(rs, "COLOR", String.class);
        ieLine.setColor(color);
        String skuSize = getResultFromRs(rs, "SKUSIZE", String.class);
        ieLine.setSkuSize(skuSize);
        BigDecimal unitPrice = getResultFromRs(rs, "UNITPRICE", BigDecimal.class);
        ieLine.setUnitPrice(unitPrice);
        String upc = getResultFromRs(rs, "UPC", String.class);
        ieLine.setUpc(upc);
        String qty = getResultFromRs(rs, "QUANTITY", String.class);
        ieLine.setQty(qty);
        BigDecimal price = unitPrice.multiply(new BigDecimal(qty));
        ieLine.setPrice(price);
        String countryOfOrigin = getResultFromRs(rs, "COUNTRYOFORIGIN", String.class);
        ieLine.setCountryOfOrigin(countryOfOrigin);
        BigDecimal orderTransferFree = getResultFromRs(rs, "ORDERTRANSFERFREE", BigDecimal.class);
        ieLine.setOrderTransferFree(orderTransferFree);
        String htsCode = getResultFromRs(rs, "htsCode", String.class);
        ieLine.setHtsCode(htsCode);
        String unitName = getResultFromRs(rs, "unitName", String.class);
        if (null != unitName && !"".equals(unitName)) {
            ieLine.setUnitName(unitName);
        } else {
            ieLine.setUnitName("PCS");
        }
        // 行号
        ieLine.setOrdinal(size + 1);
        return ieLine;
    }

    public static String getPrintTime(String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);// 设置日期格式
        return df.format(new Date());

    }
}
