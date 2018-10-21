/**
 * Copyright (c) 2010 Jumbomart All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of Jumbomart. You shall not
 * disclose such Confidential Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with Jumbo.
 * 
 * JUMBOMART MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF THE SOFTWARE, EITHER
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE, OR NON-INFRINGEMENT. JUMBOMART SHALL NOT BE LIABLE FOR ANY
 * DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES.
 * 
 */
package com.jumbo.web.action.warehouse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.system.ChooseOptionManager;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.model.command.SkuBarcodeCommand;
import com.jumbo.wms.model.warehouse.PdaPostLogType;
import com.jumbo.wms.model.warehouse.StaLineCommand;
import com.jumbo.util.FormatUtil;
import com.jumbo.web.action.BaseJQGridAction;

public class PdaInterfaceAction extends BaseJQGridAction {

    private static final long serialVersionUID = -3193651537175768024L;

    protected static final String STATUS = "status";
    protected static final String STATUS_SUCCESS = "1";

    @Autowired
    private WareHouseManager warehouseManage;
    @Autowired
    private ChooseOptionManager chooseOptionManager;

    private String pdacode;
    private String code;
    private String skucode;// 商品条码
    private String locationcode;
    private Long qty;
    private String memo;
    private Integer type;
    private String createtime;
    private Boolean isMsg; // 是否显示中文消息

    /**
     * 单号查询
     * 
     * @throws IOException
     */
    public void findByCode() throws IOException {
        log.debug("------------------pda findByCode-----------------------");
        Map<String, String> param = new LinkedHashMap<String, String>();
        try {
            chooseOptionManager.findPdaCode(pdacode);
            warehouseManage.pdaFindByCode(code);
            param.put(STATUS, STATUS_SUCCESS);
            param.put("time", FormatUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
            param.put("type", PdaPostLogType.PO.getValue() + "");
        } catch (Exception e) {
            setErrorMsg(param, e);
        }
        printReturn(param);
    }

    public void findPickingData() {
        log.error("------------------pda findPickingData-----------------------");
        Map<String, List<String>> param = new LinkedHashMap<String, List<String>>();
        try {
            // 验证
            chooseOptionManager.findPdaCode(pdacode);
            // 成功处理
            List<StaLineCommand> data = warehouseManage.findOccupySkuForPda(code);
            if (data == null) {
                throw new BusinessException(ErrorCode.PDA_CODE_NOT_FOUND, new Object[] {code});
            } else {
                StringBuffer bf = new StringBuffer();
                bf.append("<?xml version='1.0' encoding='utf-8'?>");
                bf.append("<root>");
                bf.append("<status>").append(STATUS_SUCCESS).append("</status>");
                // 库存
                bf.append("<pickings>");
                for (StaLineCommand cmd : data) {
                    bf.append("<picking>");
                    bf.append("<location>").append(cmd.getLocation()).append("</location>");
                    bf.append("<barcode>").append(cmd.getBarCode()).append("</barcode>");
                    bf.append("<suppliercode>").append(cmd.getSupplierCode()).append("</suppliercode>");
                    bf.append("<keyproperties>").append(cmd.getKeyProperties() == null ? "" : cmd.getKeyProperties()).append("</keyproperties>");
                    bf.append("<pgindex>").append(cmd.getPgIndex()).append("</pgindex>");
                    bf.append("<qty>").append(cmd.getQuantity()).append("</qty>");
                    bf.append("</picking>");
                }
                bf.append("</pickings>");
                // 商品
                List<SkuBarcodeCommand> skus = warehouseManage.findSkuBarcodeForPda(code);
                bf.append("<skus>");
                if (skus != null) {
                    Map<String, List<SkuBarcodeCommand>> skumap = new LinkedHashMap<String, List<SkuBarcodeCommand>>();
                    for (SkuBarcodeCommand sku : skus) {
                        List<SkuBarcodeCommand> list = skumap.get(sku.getMainBarcode());
                        if (list == null) {
                            list = new ArrayList<SkuBarcodeCommand>();
                        }
                        list.add(sku);
                        skumap.put(sku.getMainBarcode(), list);
                    }
                    for (Entry<String, List<SkuBarcodeCommand>> ent : skumap.entrySet()) {
                        bf.append("<sku>");
                        bf.append("<mainbarcode>").append(ent.getKey()).append("</mainbarcode>");
                        for (SkuBarcodeCommand cmd : ent.getValue()) {
                            bf.append("<barcode>").append(cmd.getBarcode()).append("</barcode>");
                        }
                        bf.append("</sku>");
                    }
                }
                bf.append("</skus>");
                bf.append("</root>");
                log.info(bf.toString());
                try {
                    response.setCharacterEncoding("utf-8");
                    response.setContentType("text/xml; charset=UTF-8");
                    response.getWriter().print(bf.toString());
                } catch (IOException e) {
                    log.error("",e);
                }
            }
        } catch (Exception e) {
            log.error("",e);
            setErrorMsgForSku(param, e);
            printReturnForSku(param);
        }
    }

    /**
     * 商品查询
     * 
     * @throws IOException
     */
    public void findBySku() throws IOException {
        log.debug("------------------pda findBySku-----------------------");
        Map<String, List<String>> param = new LinkedHashMap<String, List<String>>();
        try {
            chooseOptionManager.findPdaCode(pdacode);
            List<String> sts = new ArrayList<String>();
            sts.add(STATUS_SUCCESS);
            param.put(STATUS, sts);
            List<String> locList = new ArrayList<String>();
            try {
                String loc = warehouseManage.pdaFindBySku(code, skucode);
                locList.add(loc);
                List<String> findLocList = warehouseManage.pdaFindLocationListBySku(code, skucode);
                for (String location : findLocList) {
                    locList.add(location);
                }
            } catch (Exception e) {
                // System.out.println("find location error!");
            }
            param.put("locationcode", locList);
        } catch (Exception e) {
            setErrorMsgForSku(param, e);
        }
        printReturnForSku(param);
    }

    protected void setErrorMsgForSku(Map<String, List<String>> param, Exception e) {
        if (e instanceof BusinessException) {
            BusinessException be = (BusinessException) e;
            List<String> sts = new ArrayList<String>();
            sts.add(be.getErrorCode() + "");
            param.put(STATUS, sts);
            if (getIsMsg() != null && getIsMsg()) {
                List<String> msg = new ArrayList<String>();
                msg.add(this.getMessage(ErrorCode.BUSINESS_EXCEPTION + be.getErrorCode(), be.getArgs()));
                param.put("msg", msg);
            }
        } else {
            log.error("",e);
            List<String> sts = new ArrayList<String>();
            sts.add(ErrorCode.PDA_SYS_ERROR + "");
            param.put(STATUS, sts);
        }
    }

    protected void printReturnForSku(Map<String, List<String>> param) {
        if (param == null) {
            param = new LinkedHashMap<String, List<String>>();
            List<String> sts = new ArrayList<String>();
            param.put("status", sts);
        }
        StringBuffer bf = new StringBuffer();
        bf.append("<root>");
        for (Entry<String, List<String>> ent : param.entrySet()) {
            for (String value : ent.getValue()) {
                bf.append("<").append(ent.getKey()).append(">");
                bf.append(value);
                bf.append("</").append(ent.getKey()).append(">");
            }
        }
        bf.append("</root>");
        try {
            response.setCharacterEncoding("gbk");
            response.getWriter().print(bf.toString());
        } catch (IOException e) {
            log.error("",e);
        }
    }

    /**
     * 记录上传日志
     * 
     * @throws IOException
     */
    public void postData() throws IOException {
        log.debug("------------------pda postData-----------------------");
        Map<String, String> param = new LinkedHashMap<String, String>();
        try {
            chooseOptionManager.findPdaCode(pdacode);
            PdaPostLogType t = null;
            if (type != null && type.intValue() == PdaPostLogType.PO.getValue()) {
                t = PdaPostLogType.PO;
            } else if (type != null && type.intValue() == PdaPostLogType.INVENTORY_CHECK.getValue()) {
                t = PdaPostLogType.INVENTORY_CHECK;
            }
            warehouseManage.createPdaPostLog(locationcode, skucode, code, pdacode, FormatUtil.stringToDate(createtime), t, qty);
            param.put(STATUS, STATUS_SUCCESS);
        } catch (Exception e) {
            setErrorMsg(param, e);
        }
        printReturn(param);
    }

    protected void setErrorMsg(Map<String, String> param, Exception e) {
        if (e instanceof BusinessException) {
            BusinessException be = (BusinessException) e;
            param.put(STATUS, be.getErrorCode() + "");
            if (getIsMsg() != null && getIsMsg()) {
                param.put("msg", this.getMessage(ErrorCode.BUSINESS_EXCEPTION + be.getErrorCode(), be.getArgs()));
            }
        } else {
            log.error("",e);
            param.put(STATUS, ErrorCode.PDA_SYS_ERROR + "");
        }
    }

    protected void printReturn(Map<String, String> param) {
        if (param == null) {
            param = new LinkedHashMap<String, String>();
            param.put("status", "1");
        }
        StringBuffer bf = new StringBuffer();
        bf.append("<root>");
        for (Entry<String, String> ent : param.entrySet()) {
            bf.append("<").append(ent.getKey()).append(">");
            bf.append(ent.getValue());
            bf.append("</").append(ent.getKey()).append(">");
        }
        bf.append("</root>");
        try {
            response.setCharacterEncoding("gbk");
            response.getWriter().print(bf.toString());
        } catch (IOException e) {
            log.error("",e);
        }
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSkucode() {
        return skucode;
    }

    public void setSkucode(String skucode) {
        this.skucode = skucode;
    }

    public String getLocationcode() {
        return locationcode;
    }

    public void setLocationcode(String locationcode) {
        this.locationcode = locationcode;
    }

    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getPdacode() {
        return pdacode;
    }

    public void setPdacode(String pdacode) {
        this.pdacode = pdacode;
    }

    public Boolean getIsMsg() {
        return isMsg;
    }

    public void setIsMsg(Boolean isMsg) {
        this.isMsg = isMsg;
    }
}
