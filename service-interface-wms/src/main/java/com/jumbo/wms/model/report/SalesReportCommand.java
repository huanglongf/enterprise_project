package com.jumbo.wms.model.report;

import com.jumbo.wms.model.BaseModel;


public class SalesReportCommand extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = -2660882014527696838L;
    /**
     * 时间段
     */
    private String owner;

    /**
     * 数量
     */
    private Integer qty1 = 0;
    /**
     * 数量
     */
    private Integer qty2 = 0;
    /**
     * 数量
     */
    private Integer qty3 = 0;
    /**
     * 数量
     */
    private Integer qty4 = 0;
    /**
     * 数量
     */
    private Integer qty5 = 0;
    /**
     * 数量
     */
    private Integer qty6 = 0;
    /**
     * 数量
     */
    private Integer qty7 = 0;
    /**
     * 合计
     */
    private Integer qty8 = 0;

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Integer getQty1() {
        return qty1;
    }

    public void setQty1(Integer qty1) {
        this.qty1 = qty1;
    }

    public Integer getQty2() {
        return qty2;
    }

    public void setQty2(Integer qty2) {
        this.qty2 = qty2;
    }

    public Integer getQty3() {
        return qty3;
    }

    public void setQty3(Integer qty3) {
        this.qty3 = qty3;
    }

    public Integer getQty4() {
        return qty4;
    }

    public void setQty4(Integer qty4) {
        this.qty4 = qty4;
    }

    public Integer getQty5() {
        return qty5;
    }

    public void setQty5(Integer qty5) {
        this.qty5 = qty5;
    }

    public Integer getQty6() {
        return qty6;
    }

    public void setQty6(Integer qty6) {
        this.qty6 = qty6;
    }

    public Integer getQty7() {
        return qty7;
    }

    public void setQty7(Integer qty7) {
        this.qty7 = qty7;
    }

    public Integer getQty8() {
        return qty8;
    }

    public void setQty8(Integer qty8) {
        this.qty8 = qty8;
    }



}
