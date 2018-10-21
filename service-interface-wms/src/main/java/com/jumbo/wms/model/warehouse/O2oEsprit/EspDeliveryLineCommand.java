package com.jumbo.wms.model.warehouse.O2oEsprit;

import java.io.Serializable;
import java.util.List;

/**
 * lzb
 * 
 */
public class EspDeliveryLineCommand implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 每个门店需要有一个独立的自增长的序列，都是以E开头 eg.E000001 */
    private String deliveryNo;
    
    /** 完成配货日期,格式yyyyMMdd. eg.20160101 */
    private String deliveryDate;
    
    /** 完成配货日期,跟deliveryDate一样 */
    private String goodsReceiptDate;
    
    /** R */
    private String deliveryType;
    
    /** D */
    private String deliveryStatus;
    
    /** 门店的locationGLN */
    private String deliveredFromGLN;
    
    /** 宝尊的GLN */
    private String deliveredToGLN;

    /** 商品明细行 */
    private List<EspDeliveryItem> items;
    
    public List<EspDeliveryItem> getItems() {
        return items;
    }

    public void setItems(List<EspDeliveryItem> items) {
        this.items = items;
    }

    public String getDeliveryNo() {
        return deliveryNo;
    }

    public void setDeliveryNo(String deliveryNo) {
        this.deliveryNo = deliveryNo;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getGoodsReceiptDate() {
        return goodsReceiptDate;
    }

    public void setGoodsReceiptDate(String goodsReceiptDate) {
        this.goodsReceiptDate = goodsReceiptDate;
    }

    public String getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public String getDeliveredFromGLN() {
        return deliveredFromGLN;
    }

    public void setDeliveredFromGLN(String deliveredFromGLN) {
        this.deliveredFromGLN = deliveredFromGLN;
    }

    public String getDeliveredToGLN() {
        return deliveredToGLN;
    }

    public void setDeliveredToGLN(String deliveredToGLN) {
        this.deliveredToGLN = deliveredToGLN;
    }
    
}
