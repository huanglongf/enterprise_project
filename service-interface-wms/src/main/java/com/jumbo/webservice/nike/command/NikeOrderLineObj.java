package com.jumbo.webservice.nike.command;

import java.io.Serializable;


/**
 * Created by IntelliJ IDEA. User: hlz Date: 2010-10-12 Time: 9:16:50 To change this template use
 * File | Settings | File Templates.
 */
public class NikeOrderLineObj implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -6006850666103032239L;
    private String upcCode; // 标识sku，同skureference表的extensionCode字段
    private Integer qty; // 数量
    private Long invType; // 货品类型，100为A品，120为B品，101为残次品
    private Boolean isPackage;// 是否特殊包装
    private Boolean isGiftCards;// 是否存在礼品卡
    private String memo;// 礼品卡备注
    private String packageMemo;// 特殊包装备注

    public String getUpcCode() {
        return upcCode;
    }

    public void setUpcCode(String upcCode) {
        this.upcCode = upcCode;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public Long getInvType() {
        return invType;
    }

    public void setInvType(Long invType) {
        this.invType = invType;
    }

    public Boolean getIsPackage() {
        return isPackage;
    }

    public void setIsPackage(Boolean isPackage) {
        this.isPackage = isPackage;
    }

    public Boolean getIsGiftCards() {
        return isGiftCards;
    }

    public void setIsGiftCards(Boolean isGiftCards) {
        this.isGiftCards = isGiftCards;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getPackageMemo() {
        return packageMemo;
    }

    public void setPackageMemo(String packageMemo) {
        this.packageMemo = packageMemo;
    }

}
