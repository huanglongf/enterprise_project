package com.jumbo.wms.model.command;

import java.util.HashMap;
import java.util.Map;

import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.baseinfo.Sku;



public class NikeVMIInboundCommand extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = -8376293740129865132L;

    private Integer no;

    private String externOrderKey;

    private String upc;

    private String qtyShipped;

    private String labelNo;

    private Sku sku;

    private Map<Integer, Object[]> errors = new HashMap<Integer, Object[]>();

    private String errorMsg;

    public String getExternOrderKey() {
        return externOrderKey;
    }

    public void setExternOrderKey(String externOrderKey) {
        this.externOrderKey = externOrderKey;
    }

    public String getUpc() {
        return upc;
    }

    public void setUpc(String upc) {
        this.upc = upc;
    }

    public String getQtyShipped() {
        return qtyShipped;
    }

    public void setQtyShipped(String qtyShipped) {
        this.qtyShipped = qtyShipped;
    }

    public String getLabelNo() {
        return labelNo;
    }

    public void setLabelNo(String labelNo) {
        this.labelNo = labelNo;
    }

    public Map<Integer, Object[]> getErrors() {
        return errors;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Sku getSku() {
        return sku;
    }

    public void setSku(Sku sku) {
        this.sku = sku;
    }

    public Integer getNo() {
        return no;
    }

    public void setNo(Integer no) {
        this.no = no;
    }

}
