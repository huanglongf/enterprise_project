package com.jumbo.rmi.warehouse.vmi;

import java.io.Serializable;

/**
 * 
 * VmiRtwLine 退仓明细
 */
public class VmiRtwLine implements Serializable {

    private static final long serialVersionUID = -1037689003111511334L;

    /**
     * 商品唯一编码
     */
    private String upc;

    /**
     * 数量
     */
    private Long qty;

    /**
     * 入库单箱号
     */
    private String cartonNo;

    /**
     * 行唯一标识
     */
    private String lineSeq;

    /**
     * 库存状态
     */
    private String invStatus;

    /**
     * 定制逻辑使用json格式传递所有定制字段
     * 
     */
    private String extMemo;

    /**
     * 行号
     */
    private String lineNo;

    /**
     * 收货方
     */
    private String consigneeKey;

    /**
     * 原始数量
     */
    private Long originalQty;

    /**
     * 单位
     */
    private String uom;

    public String getUpc() {
        return upc;
    }

    public void setUpc(String upc) {
        this.upc = upc;
    }

    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }

    public String getCartonNo() {
        return cartonNo;
    }

    public void setCartonNo(String cartonNo) {
        this.cartonNo = cartonNo;
    }

    public String getLineSeq() {
        return lineSeq;
    }

    public void setLineSeq(String lineSeq) {
        this.lineSeq = lineSeq;
    }

    public String getInvStatus() {
        return invStatus;
    }

    public void setInvStatus(String invStatus) {
        this.invStatus = invStatus;
    }

    public String getExtMemo() {
        return extMemo;
    }

    public void setExtMemo(String extMemo) {
        this.extMemo = extMemo;
    }

    public String getLineNo() {
        return lineNo;
    }

    public void setLineNo(String lineNo) {
        this.lineNo = lineNo;
    }

    public String getConsigneeKey() {
        return consigneeKey;
    }

    public void setConsigneeKey(String consigneeKey) {
        this.consigneeKey = consigneeKey;
    }

    public Long getOriginalQty() {
        return originalQty;
    }

    public void setOriginalQty(Long originalQty) {
        this.originalQty = originalQty;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }


}
