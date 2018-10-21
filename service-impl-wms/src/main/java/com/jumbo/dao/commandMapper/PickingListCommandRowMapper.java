package com.jumbo.dao.commandMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import loxia.dao.support.BaseRowMapper;

import com.jumbo.wms.web.commond.OrderCheckCommand;
import com.jumbo.wms.web.commond.OrderCheckLineCommand;
import com.jumbo.wms.web.commond.PickingListCommand;
import com.jumbo.wms.web.commond.SkuCommand;

/**
 * @author jinlong.ke
 * @date 2016年6月8日下午3:12:16
 * 
 */
public class PickingListCommandRowMapper extends BaseRowMapper<PickingListCommand> {
    private PickingListCommand pc = new PickingListCommand();
    private Map<String, OrderCheckCommand> ocM = new HashMap<String, OrderCheckCommand>();
    private Map<String, OrderCheckLineCommand> oclM = new HashMap<String, OrderCheckLineCommand>();
    private Map<String, Map<String, String>> skb = new HashMap<String, Map<String, String>>();

    @Override
    public PickingListCommand mapRow(ResultSet rs, int rowNum) throws SQLException {
        // 第一步初始化PickingListCommand主体部分
        if (pc.getPlId() == null) {
            pc.setPlId(getResultFromRs(rs, "plId", Long.class));
            pc.setPlCode(getResultFromRs(rs, "plCode", String.class));
            pc.setStatus(getResultFromRs(rs, "status", Integer.class));
            pc.setOrders(new ArrayList<OrderCheckCommand>());
        }
        String staCode = getResultFromRs(rs, "staCode", String.class);
        if (ocM.get(staCode) == null) {
            OrderCheckCommand oc = new OrderCheckCommand();
            oc.setIdx1(getResultFromRs(rs, "idx1", String.class));
            oc.setSkuQty(getResultFromRs(rs, "skuQty", Long.class));
            oc.setIdx2(getResultFromRs(rs, "idx2", String.class));
            oc.setIntStatus(getResultFromRs(rs, "intStatus", Integer.class));
            oc.setLpcode(getResultFromRs(rs, "lpCode", String.class));
            oc.setOrderCode(getResultFromRs(rs, "orderCode", String.class));
            oc.setOwner(getResultFromRs(rs, "owner", String.class));
            oc.setPgIndex(getResultFromRs(rs, "pgIndex", String.class));
            oc.setSlipCode1(getResultFromRs(rs, "slipCode1", String.class));
            oc.setStaCode(getResultFromRs(rs, "staCode", String.class));
            oc.setStaId(getResultFromRs(rs, "staId", Long.class));
            oc.setStatus(getResultFromRs(rs, "staStatus", String.class));
            oc.setTransNo(getResultFromRs(rs, "transNo", String.class));
            oc.setLines(new ArrayList<OrderCheckLineCommand>());
            ocM.put(staCode, oc);
        }
        Long uniqueId = getResultFromRs(rs, "uniqueId", Long.class);
        String skuCode = getResultFromRs(rs, "skuCode", String.class);
        String skbCode = getResultFromRs(rs, "bcCode", String.class);
        if (oclM.get(staCode + "_" + uniqueId) == null) {
            OrderCheckLineCommand ocl = new OrderCheckLineCommand();
            ocl.setUniqueId(getResultFromRs(rs, "uniqueId", Long.class));
            ocl.setcQty(getResultFromRs(rs, "cQty", Long.class));
            ocl.setQty(getResultFromRs(rs, "qty", Long.class));
            SkuCommand sku = new SkuCommand();
            sku.setBarcode(getResultFromRs(rs, "barCode", String.class));
            sku.setCode(getResultFromRs(rs, "skuCode", String.class));
            sku.setName(getResultFromRs(rs, "skuName", String.class));
            sku.setColorSize(getResultFromRs(rs, "color", String.class));
            sku.setKeyProp(getResultFromRs(rs, "keyProp", String.class));
            sku.setIsSn(getResultFromRs(rs, "isSn", Boolean.class));
            sku.setSupCode(getResultFromRs(rs, "supCode", String.class));
            if (skbCode != null) {
                if (skb.get(skuCode) == null) {
                    Map<String, String> list = new HashMap<String, String>();
                    skb.put(skuCode, list);
                }
                skb.get(skuCode).put(skbCode, skbCode);
                List<String> al = new ArrayList<String>();
                al.addAll(skb.get(skuCode).keySet());
                sku.setBarcodes(al);
            }
            ocl.setSku(sku);
            oclM.put(staCode + "_" + uniqueId, ocl);
        } else {
            OrderCheckLineCommand ocl = oclM.get(staCode + "_" + uniqueId);
            SkuCommand sku = ocl.getSku();
            skb.get(skuCode).put(skbCode, skbCode);
            List<String> al = new ArrayList<String>();
            al.addAll(skb.get(skuCode).keySet());
            sku.setBarcodes(al);
        }

        for (String key : ocM.keySet()) {
            OrderCheckCommand oc = ocM.get(key);
            for (String key1 : oclM.keySet()) {
                if (key1.startsWith(key)) {
                    oc.getLines().add(oclM.get(key1));
                }
            }
            Collections.sort(oc.getLines());
            pc.getOrders().add(oc);
        }
        Collections.sort(pc.getOrders());

        return pc;
    }
}
