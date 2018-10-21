package com.jumbo.wms.web.commond;

import com.jumbo.wms.model.BaseModel;

/**
 * 称重快递数据
 * 
 * @author sjk
 * 
 */
public class TransWeightOrderCommand extends BaseModel {

    /**
	 * 
	 */
    private static final long serialVersionUID = 4447753242892777070L;

    // 订单号
    private String orderCode;
    // 运单号
    private String transNo;
    // 物流商
    private String lpcode;
    // 包裹ID(packageInfo ID)
    private Long pgId;
    // 作业单ID
    private Long staId;
    // 推荐耗材
    private String barCode;
    //是否预售订单
    private String isPreSale;

    private String isHaoCai;

    public String getIsHaoCai() {
        return isHaoCai;
    }

    public void setIsHaoCai(String isHaoCai) {
        this.isHaoCai = isHaoCai;
    }

    public String getIsPreSale() {
        return isPreSale;
    }

    public void setIsPreSale(String isPreSale) {
        this.isPreSale = isPreSale;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getTransNo() {
        return transNo;
    }

    public void setTransNo(String transNo) {
        this.transNo = transNo;
    }

    public String getLpcode() {
        return lpcode;
    }

    public void setLpcode(String lpcode) {
        this.lpcode = lpcode;
    }

    public Long getPgId() {
        return pgId;
    }

    public void setPgId(Long pgId) {
        this.pgId = pgId;
    }

    public Long getStaId() {
        return staId;
    }

    public void setStaId(Long staId) {
        this.staId = staId;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

}
