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

import org.jfree.util.Log;

import com.jumbo.util.StringUtil;
import com.jumbo.wms.Constants;
import com.jumbo.wms.model.jasperReport.PackingLineObj;
import com.jumbo.wms.model.jasperReport.PackingObj;

public class PackingObjRowMapper extends BaseRowMapper<Map<Long, PackingObj>> {

    /**
     * key sta id
     */
    private Map<Long, PackingObj> map = new LinkedHashMap<Long, PackingObj>();


    private int totalQty = 0;

    public Map<Long, PackingObj> mapRow(ResultSet rs, int rowNum) throws SQLException {
        Long staId = getResultFromRs(rs, "STAID", Long.class);// 作业单ID

        PackingObj po = map.get(staId);
        if (po == null) {
            // 获取主模板数据
            po = new PackingObj();
            // 物流商名称
            String expName = getResultFromRs(rs, "TRANS_NAME", String.class);

            // StaID
            // po.setStaId(staId);

            String isCodString = getResultFromRs(rs, "ISCODSTRING", String.class);
            if (isCodString.equals("是")) {
                po.setLpCode(expName + "     货到付款");
                po.setLpCodeHkMain(expName + "     貨到付款");
            } else {
                po.setLpCode(expName + "");
                po.setLpCodeHkMain(expName + "");
            }
            // 出库小批次号
            String batchIndex = getResultFromRs(rs, "batchIndex", String.class);
            if (!"-".equals(batchIndex)) {
                po.setBatchIndex(batchIndex);
            } else {
                po.setBatchIndex("");
            }
            // 店铺名称
            String printshopname = getResultFromRs(rs, "PRINTSHOPNAME", String.class);
            po.setPrintshopname(printshopname + "送货单");
            po.setPrintshopname2(printshopname);
            // 发货仓地址
            String whName = getResultFromRs(rs, "OUNAME", String.class);
            po.setDiliveryWhName(whName);
            // 收件人
            String receiver = getResultFromRs(rs, "RECEIVER", String.class);
            po.setReceiver(receiver);
            // 收货人地址
            String address = getResultFromRs(rs, "ADDRESS", String.class);
            po.setReceiverAddress(address);
            // 退货地址
            String returnTransno = getResultFromRs(rs, "RETURNTRANSNO", String.class);
            po.setReturnTransno(returnTransno);
            // 退货地址
            String cppAddress = getResultFromRs(rs, "cppAddress", String.class);
            po.setCppAddress(cppAddress);
            // 退货过期时间
            String returnTime = getResultFromRs(rs, "RETURNTIME", String.class);
            po.setReturnTime(returnTime);
            // 收货省
            String province = getResultFromRs(rs, "PROVINCE", String.class);
            po.setProvince(province);
            // 收货市
            String city = getResultFromRs(rs, "CITY", String.class);
            po.setCity(city);
            // 收货区
            String district = getResultFromRs(rs, "DISTRICT", String.class);
            po.setDistrict(district);
            // 拼接替换地址
            String realAddress = null;
            realAddress = StringUtil.getRealAddress(address, po.getProvince(), po.getCity(), po.getDistrict(), true);
            realAddress += "                                                                                                                #";
            po.setAddress(realAddress);
            // 退货仓地址
            String rwhAddress = getResultFromRs(rs, "RTN_WAREHOUSE_ADDRESS", String.class);
            po.setRtnAddress(rwhAddress);
            String rtnContacts = getResultFromRs(rs, "RTN_CONTACTS", String.class);
            po.setRtnContacts(rtnContacts);
            // 是否需要发票
            Boolean isSeedInvoice = getResultFromRs(rs, "ISSEEDINVOICE", Boolean.class);
            po.setIsSeedInvoice(isSeedInvoice);
            // 用户联系方式
            String rTel = getResultFromRs(rs, "TELEPHONE", String.class);
            po.setShopTelephone(rTel);
            String mobile = getResultFromRs(rs, "MOBILE", String.class);
            po.setTelephone(mobile);
            // 订单备注
            String memo = getResultFromRs(rs, "MEMO", String.class);
            po.setRemark(memo);
            // 订单创建时间
            String orderDate = getResultFromRs(rs, "ORDERDATE", String.class);
            po.setOrderDate(orderDate);

            try {
                Date orderDateNew = getResultFromRs(rs, "ORDERDATE_NEW", Date.class);
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
                po.setOrderDateNew(df.format(orderDateNew));
            } catch (Exception e) {}

            // 订单创建时间(斜杠分隔符)
            try {
                String orderDate1 = getResultFromRs(rs, "ORDERDATE1", String.class);
                po.setOrderDate1(orderDate1);
            } catch (Exception e) {}
            // 相关单据号
            String soCode = getResultFromRs(rs, "SLIP_CODE", String.class);
            po.setSoCode(soCode);
            String slipCode1 = getResultFromRs(rs, "SLIPCODE1", String.class);
            po.setSlipCode1(slipCode1);
            String slipCode2 = getResultFromRs(rs, "SLIPCODE2", String.class);
            slipCode2 = StringUtil.isEmpty(slipCode2) ? slipCode1 : slipCode2;
            po.setSlipCode2(slipCode2);
            po.setSoOutCode(slipCode2);
            // 作业单号
            String staCode = getResultFromRs(rs, "CODE", String.class);
            po.setCode(staCode);
            // 作业单备注
            String staMemo = getResultFromRs(rs, "STA_MEMO", String.class);
            if (!StringUtil.isEmpty(memo)) {
                po.setStaMemo(staMemo + Constants.PRINT_PLACEHOLDER);
            }
            // 用户邮编
            String zipcode = getResultFromRs(rs, "ZIPCODE", String.class);
            po.setZipcode(zipcode);
            // 订单商品总金额
            BigDecimal total = getResultFromRs(rs, "TOTAL_ACTUAL", BigDecimal.class);
            po.setTotalAmount(total);
            // 运费
            BigDecimal transFee = getResultFromRs(rs, "TRANSFER_FEE", BigDecimal.class);
            po.setTransferFee(transFee);
            // 包装费
            try {
                BigDecimal amt = getResultFromRs(rs, "AMT", BigDecimal.class);
                po.setAmt(amt);
            } catch (Exception e) {
                Log.error("amt mapRow" + po.getCode());
            }

            // 配货清单号
            String plCode = getResultFromRs(rs, "PLCODE", String.class);
            po.setBatchNo(plCode);
            po.setBatchNo1(plCode != null ? plCode.substring(0, plCode.length() - 4) : "");
            po.setBatchNo2(plCode != null ? plCode.substring(plCode.length() - 4, plCode.length()) : "");
            // 是否显示订单总金额
            Boolean isNotDisplaySum = getResultFromRs(rs, "ISNOTDISPLAYSUM", Boolean.class);
            po.setIsNotDisplaySum(!(isNotDisplaySum == null ? false : isNotDisplaySum));
            String imgSemacode = getResultFromRs(rs, "IMG_SEMACODE", String.class);// 二维码图片路径
            po.setImgSemacode(imgSemacode);
            String imgLogoTmall = getResultFromRs(rs, "IMG_LOGOTMALL", String.class);// 天猫LOGO图片路径
            po.setImgLogoTmall(imgLogoTmall);

            // 发票数
            Integer invoiceNum = getResultFromRs(rs, "INVOICENUMBER", Integer.class);
            po.setInvoiceNum(invoiceNum);
            po.setInvoiceNumber(invoiceNum + "");
            // 箱号
            Integer index = getResultFromRs(rs, "pgindex", Integer.class);
            po.setIndex(index == null ? null : index);
            po.setPgIndex(index + "");
            try {
                String ruleCode = getResultFromRs(rs, "ruleCode", String.class);
                if (!StringUtil.isEmpty(ruleCode)) {
                    po.setPgIndex(po.getPgIndex() + "-" + ruleCode);
                }
            } catch (Exception e) {
                // TODO: handle exception
            }
            // nike负向url
            try {
                String returnTrackingNo = getResultFromRs(rs, "returnUrl", String.class);
                po.setReturnTranckingNo(returnTrackingNo);
            } catch (Exception e) {}
            // 设置明细
            totalQty = 0;
            List<PackingLineObj> pickinglineList = new ArrayList<PackingLineObj>();
            pickinglineList.add(getPlObj(rs, pickinglineList.size()));

            po.setLines(pickinglineList);
            po.setDetailSize(pickinglineList.size());
            po.setPrintTime1(getPrintTime("yyyy-MM-dd HH:mm:ss"));
            po.setPrintTimeHk(getPrintTime("yyyy/MM/dd"));
            String orderUser = getResultFromRs(rs, "ORDERUSERCODE", String.class);
            po.setOrderUserCode(orderUser);

            // String trunktemplatename = getResultFromRs(rs, "TRUNKTEMPLATENAME", String.class);
            // po.setTrunkTemplateName(StringUtils.hasLength(trunktemplatename) ? trunktemplatename
            // : null);

            // po.setIndex(map.values().size()+1);
            map.put(staId, po);
        } else {
            po.getLines().add(getPlObj(rs, po.getLines().size()));
            po.setDetailSize(po.getLines().size());
        }
        try {
            po.setTotalQty2(po.getLines().get(po.getLines().size() - 1).getTotalQty());
        } catch (Exception e) {
            Log.error("PackingObjRowMapper mapRow" + po.getCode());
        }
        // 合计吊牌价
        try {
            BigDecimal sumskuListprice = new BigDecimal(0);
            for (PackingLineObj packingLineObj : po.getLines()) {
                sumskuListprice = sumskuListprice.add(packingLineObj.getTotalskulistprice());
            }
            po.setTotalSkuListPrice(sumskuListprice);
        } catch (Exception e) {}
        return map;
    }

    private PackingLineObj getPlObj(ResultSet rs, int size) throws SQLException {
        PackingLineObj plobj = new PackingLineObj();
        // 物流商名称
        String expName = getResultFromRs(rs, "TRANS_NAME", String.class);

        String isCodString = getResultFromRs(rs, "ISCODSTRING", String.class);
        if (isCodString.equals("是")) {
            plobj.setLpCode(expName + "     货到付款");
            plobj.setLpCodeHk(expName + "     貨到付款");
        } else {
            plobj.setLpCode(expName + "");
            plobj.setLpCodeHk(expName + "");
        }
        // 收件人
        String receiver = getResultFromRs(rs, "RECEIVER", String.class);
        plobj.setReceiver(receiver);
        // 发货仓地址
        String whName = getResultFromRs(rs, "OUNAME", String.class);
        plobj.setDiliveryWhName(whName);
        // 退货仓地址
        String rwhAddress = getResultFromRs(rs, "RTN_WAREHOUSE_ADDRESS", String.class);
        plobj.setRtnAddress(rwhAddress);
        // 退货收件人
        String rtnContacts = getResultFromRs(rs, "RTN_CONTACTS", String.class);
        plobj.setRtnContacts(rtnContacts);

        String imgSemacode = getResultFromRs(rs, "IMG_SEMACODE", String.class);// 二维码图片路径
        plobj.setImgSemacode(imgSemacode);
        String imgLogoTmall = getResultFromRs(rs, "IMG_LOGOTMALL", String.class);// 天猫LOGO路径
        plobj.setImgLogoTmall(imgLogoTmall);
        // 用户联系方式
        String rTel = getResultFromRs(rs, "TELEPHONE", String.class);
        plobj.setShopTelephone(rTel);
        // 配货清单号
        String plCode = getResultFromRs(rs, "PLCODE", String.class);
        plobj.setBatchNo(plCode);
        // 商品关键属性
        String skuKeyP = getResultFromRs(rs, "KEYPROPERTIES", String.class);
        plobj.setKeyProperty(skuKeyP);

        // 商品upc码
        try {
            String upcCode = getResultFromRs(rs, "UPCCODE", String.class);
            plobj.setUpcCode(upcCode);
        } catch (Exception e) {}
        // 库位
        try {
            String locCode = getResultFromRs(rs, "LOCATIONCODE", String.class);
            plobj.setLocation(locCode);
        } catch (Exception e) {}
        // 单价
        try {
            String unitPrice = getResultFromRs(rs, "UNITPRICE", String.class);
            plobj.setUnitPrice(unitPrice);
        } catch (Exception e) {}
        // 单价=行总价/数量
        try {
            BigDecimal statPrice = getResultFromRs(rs, "STATPRICE", BigDecimal.class);
            BigDecimal qty = getResultFromRs(rs, "QUANTITY", BigDecimal.class);
            String univalence = statPrice.divide(qty, 0).toString();
            plobj.setUnivalent(univalence);
        } catch (Exception e) {}

        try {
            // 总计
            String statPrice = getResultFromRs(rs, "STATPRICE", String.class);
            plobj.setStatPrice(statPrice);
        } catch (Exception e) {}
        try {
            // 总计
            String orderTotal = getResultFromRs(rs, "ORDERTOTAL", String.class);
            plobj.setOrderTotal(orderTotal);
        } catch (Exception e) {}
        try {
            // 总计
            String orderunitprice = getResultFromRs(rs, "ORDERUNITPRICE", String.class);
            plobj.setOrderUnitPrice(orderunitprice);
        } catch (Exception e) {}

        // 数量
        BigDecimal qty = getResultFromRs(rs, "QUANTITY", BigDecimal.class);
        plobj.setQty(qty.intValue());

        String qty1 = getResultFromRs(rs, "QUANTITY", String.class);
        plobj.setQty1(qty1);
        // 订单商品总金额
        BigDecimal total = getResultFromRs(rs, "TOTAL_ACTUAL", BigDecimal.class);
        plobj.setTotalAmount(total);
        // 采购(采购行总金额)/销售(销售单行实际总金额)
        /*
         * BigDecimal total2 = getResultFromRs(rs, "TOTAL_ACTUAL2", BigDecimal.class);
         * plobj.setTotalAmount2(total2);
         */
        // 运费
        BigDecimal transFee = getResultFromRs(rs, "TRANSFER_FEE", BigDecimal.class);
        plobj.setTransferFee(transFee);
        // 宝尊编码
        String jmcode = getResultFromRs(rs, "JMCODE", String.class);
        plobj.setSkuCode(jmcode);
        // 货号
        String supcode = getResultFromRs(rs, "SUPPLIERCODE", String.class);
        plobj.setSupplierSkuCode(supcode);
        // 商品名称
        String skuName = getResultFromRs(rs, "SKUNAME", String.class);
        plobj.setSkuName(skuName);
        // 商品条码
        String barCode = getResultFromRs(rs, "BARCODE", String.class);
        plobj.setBarCode(barCode);
        try {
            // 商品编码
            String skuCodeStr = getResultFromRs(rs, "skuCodeStr", String.class);
            plobj.setSkuCodeStr(skuCodeStr);
        } catch (Exception e) {}
        String sizeS = getResultFromRs(rs, "SKUSIZE", String.class);
        String colorS = getResultFromRs(rs, "COLOR", String.class);
        try {
            plobj.setColor(colorS);
        } catch (Exception e) {}
        plobj.setKeyPro(sizeS + "," + colorS);// ad定制属性
        plobj.setKsm((supcode == null ? "" : supcode) + "-" + (colorS == null ? "" : colorS) + "-" + (sizeS == null ? "" : sizeS));
        String slipCode1 = getResultFromRs(rs, "SLIPCODE1", String.class);
        String slipCode2 = getResultFromRs(rs, "SLIPCODE2", String.class);
        // 相关单据号
        String soCode = getResultFromRs(rs, "SLIP_CODE", String.class);
        plobj.setSoCode(soCode);
        slipCode2 = StringUtil.isEmpty(slipCode2) ? slipCode1 : slipCode2;
        plobj.setSlipCode2(slipCode2);
        plobj.setSlipCode1(slipCode1);
        plobj.setSize(sizeS);
        // 备注
        String staMemo = getResultFromRs(rs, "STA_MEMO", String.class);
        plobj.setStaMemo(staMemo);

        // SKUID
        // Long skuId = getResultFromRs(rs, "SKUID", Long.class);// SKUID
        // plobj.setSkuId(skuId);

        // 行号
        plobj.setOrdinal(size + 1);
        plobj.setPrintTime1(getPrintTime("yyyy-MM-dd HH:mm:ss"));
        plobj.setPrintTimeHk(getPrintTime("yyyy/MM/dd"));
        plobj.setPrintTime2(getPrintTime("yyyy-MM-dd"));
        totalQty = totalQty + qty.intValue();
        plobj.setTotalQty(totalQty);
        try {
            String memo = getResultFromRs(rs, "GIFTMEMO", String.class);
            if (!StringUtil.isEmpty(memo)) {
                plobj.setMemo(memo + Constants.PRINT_PLACEHOLDER);
            }
        } catch (Exception e) {}
        try {
            Long skuId = getResultFromRs(rs, "SKU_ID", Long.class);
            plobj.setSkuId(skuId);
        } catch (Exception e) {}
        try {
            // reebok detail联接字段(商品名称 颜色 尺码)
            String concatField = getResultFromRs(rs, "CONCATFIELD", String.class);
            plobj.setConcatField(concatField);
        } catch (Exception e) {}



        try {
            // Gucci的full style code
            String skuStyleCode = getResultFromRs(rs, "SKUSTYLECODE", String.class);
            plobj.setSkuStyleCode(skuStyleCode);
        } catch (Exception e) {}

        try {
            // Gucci的中文名称
            String gucciSkuName = getResultFromRs(rs, "GUCCISKUNAME", String.class);
            plobj.setGucciSkuName(gucciSkuName);
        } catch (Exception e) {}
        try {
            // Gucci是否打印价格
            String isPrintPrice = getResultFromRs(rs, "ISPRINTPRICE", String.class);
            plobj.setIsPrintPrice(isPrintPrice);
        } catch (Exception e) {}
        // 商品吊牌价
        try {
            BigDecimal skulistprice = getResultFromRs(rs, "SKULISTPRICE", BigDecimal.class);
            plobj.setSkuListPrice(skulistprice);
        } catch (Exception e) {}
        try {
            BigDecimal totalskulistprice = getResultFromRs(rs, "TOTALSKULISTPRICE", BigDecimal.class);
            plobj.setTotalskulistprice(totalskulistprice);
        } catch (Exception e) {}

        try {
            // puma detail联接字段(商品名称 颜色 尺码)
            String concatField2 = skuName + " " + colorS + " " + sizeS;
            plobj.setConcatField2(concatField2);
        } catch (Exception e) {}
        return plobj;
    }

    public static String getPrintTime(String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);// 设置日期格式
        return df.format(new Date());

    }
}
