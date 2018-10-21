package com.jumbo.wms.model.command;

import com.jumbo.wms.model.warehouse.GiftLine;

/**
 * 接口反馈信息结构
 * 
 * @author sjk
 * 
 */
public class GiftLineCommand extends GiftLine {

    /**
     * 
     */
    private static final long serialVersionUID = 3360935527310362333L;

    private Integer intType;
    private Long staLineId;
    private String skuCode;
    private String barCode;
    private Integer countMemo;

    /**
     * 是否处理过
     */
    private Boolean isHandle;

    private String name;// 商品名称

    private String typeString;// 特殊类型String



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTypeString() {
        return typeString;
    }

    public void setTypeString(String typeString) {
        this.typeString = typeString;
    }

    public Integer getCountMemo() {
        return countMemo;
    }

    public void setCountMemo(Integer countMemo) {
        this.countMemo = countMemo;
    }

    public Integer getIntType() {
        return intType;
    }

    public void setIntType(Integer intType) {
        this.intType = intType;
    }

    public Long getStaLineId() {
        return staLineId;
    }

    public void setStaLineId(Long staLineId) {
        this.staLineId = staLineId;
    }

    public Boolean getIsHandle() {
        return isHandle;
    }

    public void setIsHandle(Boolean isHandle) {
        this.isHandle = isHandle;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

}
