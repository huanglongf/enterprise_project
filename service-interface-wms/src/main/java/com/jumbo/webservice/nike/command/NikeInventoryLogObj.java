package com.jumbo.webservice.nike.command;

import java.io.Serializable;

public class NikeInventoryLogObj implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -233938529782449581L;
    private long id; // 日志Id
    private String extensionCode; // upc编号
    private String fullTime; // 操作时间
    private Integer qty; // 数量
    private String opeType;// 事物类型
    private Integer typeId; // 备注

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getExtensionCode() {
        return extensionCode;
    }

    public void setExtensionCode(String extensionCode) {
        this.extensionCode = extensionCode;
    }

    public String getFullTime() {
        return fullTime;
    }

    public void setFullTime(String fullTime) {
        this.fullTime = fullTime;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public String getOpeType() {
        return opeType;
    }

    public void setOpeType(String opeType) {
        this.opeType = opeType;
    }

}
