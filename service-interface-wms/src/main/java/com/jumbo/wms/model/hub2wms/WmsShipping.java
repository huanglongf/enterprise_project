package com.jumbo.wms.model.hub2wms;

import java.util.List;

import com.jumbo.wms.model.BaseModel;

public class WmsShipping extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = -7660978360041185672L;
    private Long id;
    /*
     * 单据号，Wms单据唯一标识
     */
    private String shippingCode;
    /*
     * 仓库编码
     */
    private String whCode;
    /*
     * 物流商简称
     */
    private String transCode;
    /*
     * 运单号
     */
    private String transNo;
    /*
     * 明细行
     */
    private List<WmsShippingLine> lines;

    public String getShippingCode() {
        return shippingCode;
    }

    public void setShippingCode(String shippingCode) {
        this.shippingCode = shippingCode;
    }

    public String getTransCode() {
        return transCode;
    }

    public void setTransCode(String transCode) {
        this.transCode = transCode;
    }

    public String getTransNo() {
        return transNo;
    }

    public void setTransNo(String transNo) {
        this.transNo = transNo;
    }

    public List<WmsShippingLine> getLines() {
        return lines;
    }

    public void setLines(List<WmsShippingLine> lines) {
        this.lines = lines;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWhCode() {
        return whCode;
    }

    public void setWhCode(String whCode) {
        this.whCode = whCode;
    }


}
