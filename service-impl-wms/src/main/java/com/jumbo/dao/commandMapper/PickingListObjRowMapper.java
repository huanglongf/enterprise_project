package com.jumbo.dao.commandMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import loxia.dao.support.BaseRowMapper;

import com.jumbo.wms.model.jasperReport.PickingListObj;
import com.jumbo.wms.model.jasperReport.PickingListSubLineObj;

public class PickingListObjRowMapper extends BaseRowMapper<Map<String, PickingListObj>> {

    /**
     * key key_location
     */
    private Map<String, PickingListObj> map = new LinkedHashMap<String, PickingListObj>();

    // PickingListSubLineObj PickingListObj
    public Map<String, PickingListObj> mapRow(ResultSet rs, int rowNum) throws SQLException {
        String loc = getResultFromRs(rs, "location", String.class);
        Long skuid = getResultFromRs(rs, "skuid", Long.class);
        String key = loc + skuid;
        PickingListObj po = map.get(key);
        if (po == null) {
            po = new PickingListObj();
            String warehouseName = getResultFromRs(rs, "warehouseName", String.class);
            String dphCode = getResultFromRs(rs, "dphCode", String.class);
            // String operator = getResultFromRs(rs, "operator", String.class);
            Integer planBillCount = getResultFromRs(rs, "planBillCount", Integer.class);
            Integer planSkuQty = getResultFromRs(rs, "planSkuQty", Integer.class);
            String location = getResultFromRs(rs, "location", String.class);
            String barCode = getResultFromRs(rs, "barCode", String.class);
            // String jmCode = getResultFromRs(rs, "jmCode", String.class);
            String supplierCode = getResultFromRs(rs, "supplierCode", String.class);
            String jmskucode = getResultFromRs(rs, "jmskucode", String.class);
            Integer isCod = getResultFromRs(rs, "isCod", Integer.class);
            String skuName = getResultFromRs(rs, "skuName", String.class);
            String keyProperty = getResultFromRs(rs, "keyProperty", String.class);
            String lpcode = getResultFromRs(rs, "lpcode", String.class);
            String specialPackaging = getResultFromRs(rs, "specialPackaging", String.class);
            Integer quantity = getResultFromRs(rs, "quantity", Integer.class);
            String bigBox = getResultFromRs(rs, "bigBox", String.class);
            String strExpireDate = getResultFromRs(rs, "strExpireDate", String.class);
            String pickZoneCode = getResultFromRs(rs, "pickZoneCode", String.class);
            String whZone = getResultFromRs(rs, "whZone", String.class);
            po.setWhZone(whZone);
            po.setWarehouseName(warehouseName);
            po.setDphCode(dphCode);
            // po.setOperator(operator);
            po.setPlanBillCount(planBillCount);
            po.setPlanSkuQty(planSkuQty);
            po.setLocation(location);
            po.setBarCode(barCode);
            po.setIsCod(isCod);
            po.setBarCode1(barCode.substring(0, barCode.length() - 4));
            po.setBarCode2(barCode.substring(barCode.length() - 4, barCode.length()));
            // po.setJmCode(jmCode);
            po.setJmCode(supplierCode);
            po.setJmskucode(jmskucode);
            po.setSkuName(skuName);
            po.setKeyProperty(keyProperty);
            po.setQuantity(quantity);
            po.setLpcode(lpcode);
            po.setSpecialPackaging(specialPackaging);
            po.setIsBigBox(bigBox == null ? "" : bigBox);
            po.setStrExpireDate(strExpireDate);
            po.setPickZoneCode(pickZoneCode);


            List<PickingListSubLineObj> poline = new ArrayList<PickingListSubLineObj>();
            poline.add(getPickingListLineObj(rs));
            po.setLines(poline);
            map.put(key, po);
        } else {
            po.getLines().add(getPickingListLineObj(rs));
        }
        return map;
    }

    private PickingListSubLineObj getPickingListLineObj(ResultSet rs) throws SQLException {
        PickingListSubLineObj poline = new PickingListSubLineObj();
        Integer tagNumber = getResultFromRs(rs, "tagNumber", Integer.class);
        Integer qty = getResultFromRs(rs, "qty", Integer.class);
        String batchIndex = getResultFromRs(rs, "batchIndex", String.class);
        if (!"-".equals(batchIndex)) {
            poline.setBatchIndex(batchIndex);
        } else {
            poline.setBatchIndex("");
        }
        poline.setTagNumber(tagNumber + "");
        poline.setQty(qty);
        return poline;
    }
}
