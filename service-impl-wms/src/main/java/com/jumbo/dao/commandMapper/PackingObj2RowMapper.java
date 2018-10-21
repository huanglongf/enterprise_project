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

import org.springframework.util.StringUtils;

import loxia.dao.support.BaseRowMapper;

import com.jumbo.wms.model.jasperReport.PackingLineObj;
import com.jumbo.wms.model.jasperReport.PackingObj;

public class PackingObj2RowMapper extends BaseRowMapper<Map<Long, PackingObj>> {

    /**
     * key sta id
     */
    private Map<Long, PackingObj> map = new LinkedHashMap<Long, PackingObj>();

    public Map<Long, PackingObj> mapRow(ResultSet rs, int rowNum) throws SQLException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        Long staId = getResultFromRs(rs, "STAID", Long.class);
        PackingObj po = map.get(staId);
        if (po == null) {
            po = new PackingObj();
            String expName = getResultFromRs(rs, "TRANS_NAME", String.class);
            po.setLpCode(expName);
            String printshopname = getResultFromRs(rs, "PRINTSHOPNAME", String.class);
            po.setPrintshopname(StringUtils.hasLength(printshopname) ? printshopname : "宝尊电商");
            String whName = getResultFromRs(rs, "OUNAME", String.class);
            if (StringUtils.hasLength(printshopname) && StringUtils.hasLength(whName)) {
                if (whName.contains("宝尊")) {
                    whName = whName.replace("宝尊", "");
                }
            }
            po.setDiliveryWhName(whName);
            String receiver = getResultFromRs(rs, "RECEIVER", String.class);
            po.setReceiver(receiver);
            String address = getResultFromRs(rs, "ADDRESS", String.class);
            po.setReceiverAddress(address);
            String rwhAddress = getResultFromRs(rs, "RTN_WAREHOUSE_ADDRESS", String.class);
            // po.setRtnAddress("联系店铺售后旺旺");
            po.setRtnAddress(rwhAddress);
            String rTel = getResultFromRs(rs, "TELEPHONE", String.class);
            po.setShopTelephone(rTel);
            String memo = getResultFromRs(rs, "MEMO", String.class);
            po.setRemark(memo);
            String soCode = getResultFromRs(rs, "SLIP_CODE", String.class);
            po.setSoCode(soCode);
            String soCode2 = getResultFromRs(rs, "SLIP_CODE2", String.class);
            po.setSoCode2(soCode2);
            
            String staCode = getResultFromRs(rs, "code", String.class);
            po.setCode(staCode);

            String zipcode = getResultFromRs(rs, "ZIPCODE", String.class);
            po.setZipcode(zipcode);
            String mobile = getResultFromRs(rs, "MOBILE", String.class);
            po.setTelephone(mobile);
            BigDecimal total = getResultFromRs(rs, "TOTAL_ACTUAL", BigDecimal.class);
            po.setTotalAmount(total);
            BigDecimal transFee = getResultFromRs(rs, "TRANSFER_FEE", BigDecimal.class);
            po.setTransferFee(transFee);
            String plCode = getResultFromRs(rs, "PLCODE", String.class);
            po.setBatchNo(plCode);
            String orderDate = df.format(new Date()).toString();//系统当前时间bin,hu
            po.setOrderDate(orderDate);
            String pgIndex = getResultFromRs(rs, "PG_INDEX", String.class);
            po.setPgIndex("# "+pgIndex);
            /*
             * String trunkTemplateName = getResultFromRs(rs, "TRUNKTEMPLATENAME", String.class);
             * po.setTrunkTemplateName(trunkTemplateName);
             */
            Boolean isNotDisplaySum = getResultFromRs(rs, "ISNOTDISPLAYSUM", Boolean.class);
            po.setIsNotDisplaySum(!(isNotDisplaySum == null ? false : isNotDisplaySum));
            String trunktemplatename = getResultFromRs(rs, "TRUNKTEMPLATENAME", String.class);
            po.setTrunkTemplateName(StringUtils.hasLength(trunktemplatename) ? trunktemplatename : null);
            po.setBarcodeImg("print_img/esprit_barcode.jpg");
            // po.setIndex(map.values().size()+1);
            Integer index = getResultFromRs(rs, "pgindex", Integer.class);
            po.setIndex(index == null ? null : index);

            String soOutCode = getResultFromRs(rs, "soOutCode", String.class);
            if (soOutCode == null || "".equals(soOutCode)) {
                po.setSoOutCode(soCode);
            } else {
                po.setSoOutCode(soOutCode);
            }

            List<PackingLineObj> pickinglineList = new ArrayList<PackingLineObj>();
            pickinglineList.add(getPlObj(rs, pickinglineList.size()));
            po.setLines(pickinglineList);
            po.setDetailSize(pickinglineList.size());

            map.put(staId, po);
        } else {
            po.getLines().add(getPlObj(rs, po.getLines().size()));
            po.setDetailSize(po.getLines().size());
        }
        return map;
    }

    private PackingLineObj getPlObj(ResultSet rs, int size) throws SQLException {
        PackingLineObj plobj = new PackingLineObj();
        String skuKeyP = getResultFromRs(rs, "KEYPROPERTIES", String.class);
        plobj.setKeyProperty(skuKeyP);
        String locCode = getResultFromRs(rs, "LOCATIONCODE", String.class);
        plobj.setLocation(locCode);
        String unitPrice = getResultFromRs(rs, "unitPrice", String.class);
        plobj.setUnitPrice(unitPrice);
        String statPrice = getResultFromRs(rs, "statPrice", String.class);
        plobj.setStatPrice(statPrice);
        String rowNumber = getResultFromRs(rs, "ROWNUMBER", String.class);
        plobj.setRowNumber(rowNumber);

        BigDecimal qty = getResultFromRs(rs, "QUANTITY", BigDecimal.class);
        plobj.setQty(qty.intValue());

        String jmcode = getResultFromRs(rs, "JMCODE", String.class);
        plobj.setSkuCode(jmcode);

        String supcode = getResultFromRs(rs, "SUPPLIERCODE", String.class);
        plobj.setSupplierSkuCode(supcode);
        String skuName = getResultFromRs(rs, "SKUNAME", String.class);
        if (skuName.trim().length() > 17)
            plobj.setSkuName(skuName.substring(0, 17) + "\r\n" + skuName.substring(17));
        else
            plobj.setSkuName(skuName);
        // plobj.setSkuName(skuName);
        plobj.setOrdinal(size + 1);
        return plobj;
    }
}
