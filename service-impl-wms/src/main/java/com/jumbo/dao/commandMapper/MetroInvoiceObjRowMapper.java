package com.jumbo.dao.commandMapper;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

import loxia.dao.support.BaseRowMapper;

import com.jumbo.wms.model.jasperReport.MetroInvoiceLineObj;
import com.jumbo.wms.model.jasperReport.MetroInvoiceObj;

public class MetroInvoiceObjRowMapper extends BaseRowMapper<Map<Long, MetroInvoiceObj>> {

    /**
     * key sta id
     */
    private Map<Long, MetroInvoiceObj> map = new LinkedHashMap<Long, MetroInvoiceObj>();

    public Map<Long, MetroInvoiceObj> mapRow(ResultSet rs, int rowNum) throws SQLException {
        Long staId = getResultFromRs(rs, "staid", Long.class);
        MetroInvoiceObj io = map.get(staId);
        if (io == null) {
            io = new MetroInvoiceObj();
            // 订单金额
            BigDecimal sumTotalAmount = getResultFromRs(rs, "sumTotalAmount", BigDecimal.class);
            io.setSumTotalAmount(sumTotalAmount);
            // io.setOrderAmount(transferFee.add(totalActual));
            // 积分抵扣金额
            BigDecimal totalPointUsed = getResultFromRs(rs, "totalPointUsed", BigDecimal.class);
            io.setTotalPointUsed(totalPointUsed);
            // 折扣
            BigDecimal discount = getResultFromRs(rs, "discount", BigDecimal.class);
            io.setDiscount(discount);
            // 实际支付金额
            BigDecimal totalActualAmount = getResultFromRs(rs, "totalActualAmount", BigDecimal.class);
            io.setTotalActualAmount(totalActualAmount);
            // 运费
            BigDecimal transferFee = getResultFromRs(rs, "transferFee", BigDecimal.class);
            io.setTransferFee(transferFee);

            String soCode = getResultFromRs(rs, "soCode", String.class);
            io.setSoCode(soCode);
            String taxCode = getResultFromRs(rs, "taxCode", String.class);
            io.setTaxCode(taxCode);
            String registeredAddress = getResultFromRs(rs, "registeredAddress", String.class);
            if (StringUtils.hasLength(registeredAddress)) {
                int length = 9;
                if (registeredAddress.trim().length() > length) {
                    io.setRegisteredAddress(registeredAddress.substring(0, length) + "\r\n" + registeredAddress.substring(length));
                } else {
                    io.setRegisteredAddress(registeredAddress);
                }
            }
            String tellPhone = getResultFromRs(rs, "tellPhone", String.class);
            io.setTellPhone(tellPhone);
            String bankName = getResultFromRs(rs, "bankName", String.class);
            io.setBankName(bankName);
            String bankcode = getResultFromRs(rs, "bankcode", String.class);
            io.setBankCode(bankcode);
            String companyName = getResultFromRs(rs, "companyName", String.class);
            io.setCompanyName(companyName);

            String receiver = getResultFromRs(rs, "receiver", String.class);
            io.setReceiver(receiver);
            String receiverTel = getResultFromRs(rs, "receiverTel", String.class);
            io.setReceiverTel(receiverTel);
            // 收获地址
            String province = hasLength(getResultFromRs(rs, "province", String.class));
            String city = hasLength(getResultFromRs(rs, "city", String.class));
            String district = hasLength(getResultFromRs(rs, "district", String.class));
            String address = hasLength(getResultFromRs(rs, "address", String.class));
            StringBuffer temp = new StringBuffer();
            temp = temp.append(province).append(city).append(district);
            address = address.replace(" ", "");
            if (!address.contains(temp.toString())) {
                String add = temp.toString() + address;
                io.setReceiverAddress(add);
            } else {
                io.setReceiverAddress(address);
            }
            Integer invoiceType = getResultFromRs(rs, "invoiceType", Integer.class);
            if (invoiceType == 1 || invoiceType.equals(1)) {
                io.setStrInvoiceType("普通商业零售发票");
                String invoiceTitle = getResultFromRs(rs, "invoiceTitle", String.class);
                if (StringUtils.hasLength(invoiceTitle)) {
                    int length = 12;
                    if (invoiceTitle.trim().length() > length) {
                        io.setInvoiceTitle(invoiceTitle.substring(0, length) + "\r\n" + invoiceTitle.substring(length));
                    } else {
                        io.setInvoiceTitle(invoiceTitle);
                    }
                } else
                    io.setInvoiceTitle(receiver);
                // io.setInvoiceTitle(StringUtils.hasLength(invoiceTitle)?invoiceTitle:receiver);
            } else {
                io.setStrInvoiceType("增值税专用发票");
                io.setInvoiceTitle(companyName);
            }
            List<MetroInvoiceLineObj> invoiceLineList = new ArrayList<MetroInvoiceLineObj>();
            invoiceLineList.add(getInvoiceLineObj(rs, invoiceLineList.size()));
            io.setLines(invoiceLineList);
            map.put(staId, io);
        } else {
            io.getLines().add(getInvoiceLineObj(rs, io.getLines().size()));
        }
        return map;
    }

    private MetroInvoiceLineObj getInvoiceLineObj(ResultSet rs, int size) throws SQLException {
        MetroInvoiceLineObj invoiceLineObj = new MetroInvoiceLineObj();
        String supplierCode = getResultFromRs(rs, "supplierCode", String.class);
        invoiceLineObj.setSupplierCode(supplierCode);
        String barcode = getResultFromRs(rs, "barcode", String.class);
        invoiceLineObj.setBarcode(barcode);
        String skuName = getResultFromRs(rs, "skuName", String.class);
        if (skuName.trim().length() > 18)
            invoiceLineObj.setSkuName(skuName.substring(0, 18) + "\r\n" + skuName.substring(18));
        else
            invoiceLineObj.setSkuName(skuName);
        // invoiceLineObj.setSkuName(skuName);
        Integer qty = getResultFromRs(rs, "qty", Integer.class);
        invoiceLineObj.setQty(qty);
        // 原单价
        BigDecimal unitPrice = getResultFromRs(rs, "unitPrice", BigDecimal.class);
        invoiceLineObj.setUnitPrice(unitPrice);
        // 行实际金额
        BigDecimal lineActualAmount = getResultFromRs(rs, "lineActualAmount", BigDecimal.class);
        invoiceLineObj.setLineActualAmount(lineActualAmount);

        /*
         * // 折扣 BigDecimal discount = getResultFromRs(rs, "discount", BigDecimal.class);
         * invoiceLineObj.setDiscount(discount); // 实际单价 BigDecimal userPayPrice =
         * getResultFromRs(rs, "userPayPrice", BigDecimal.class);
         * invoiceLineObj.setUserPayPrice(userPayPrice); // 积分抵扣 BigDecimal subDiscount =
         * getResultFromRs(rs, "subDiscount", BigDecimal.class);
         * invoiceLineObj.setSubDiscount(subDiscount);
         */
        invoiceLineObj.setOrdinal(size + 1);

        return invoiceLineObj;
    }

    private String hasLength(String str) {
        return StringUtils.hasLength(str) ? str : "";
    }
}
