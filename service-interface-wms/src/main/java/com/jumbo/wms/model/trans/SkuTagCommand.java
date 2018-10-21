package com.jumbo.wms.model.trans;

public class SkuTagCommand extends SkuTag {

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;
    private String tagType;// 类型
    private String tagStatus;// 状态
    private Integer intType;// 类型
    private Integer intStatus;// 状态
    private Long skuId;
    public String getTagType() {
        return tagType;
    }

    public void setTagType(String tagType) {
        this.tagType = tagType;
    }

    public String getTagStatus() {
        return tagStatus;
    }

    public void setTagStatus(String tagStatus) {
        this.tagStatus = tagStatus;
    }

    public Integer getIntType() {
        return intType;
    }

    public void setIntType(Integer intType) {
        this.intType = intType;
    }

    public Integer getIntStatus() {
        return intStatus;
    }

    public void setIntStatus(Integer intStatus) {
        this.intStatus = intStatus;
    }

	public Long getSkuId() {
		return skuId;
	}

	public void setSkuId(Long skuId) {
		this.skuId = skuId;
	}

}
