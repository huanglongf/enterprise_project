package com.jumbo.wms.model.vmi.Default;

public class TransferOwnerTargetCommand extends TransferOwnerTarget {



    /**
     * 
     */
    private static final long serialVersionUID = -1500210297875579623L;


    
    /**
     * 商品数量
     */
    private Long qty;

	private Integer sourceRatio;

	private String skuBarCode;

	private String skuName;

    private String sourceOwnerName;

    private String targetOwnerName;


    /**
     * 商品编码
     */
    private String skuCode;

    public Long getQty() {
        return qty;
    }



    public void setQty(Long qty) {
        this.qty = qty;
    }



    public String getSkuCode() {
        return skuCode;
    }



    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }



	public Integer getSourceRatio() {
		return sourceRatio;
	}

	public void setSourceRatio(Integer sourceRatio) {
		this.sourceRatio = sourceRatio;
	}

	public String getSkuBarCode() {
		return skuBarCode;
	}

	public void setSkuBarCode(String skuBarCode) {
		this.skuBarCode = skuBarCode;
	}

	public String getSkuName() {
		return skuName;
	}

	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}



    public String getSourceOwnerName() {
        return sourceOwnerName;
    }



    public void setSourceOwnerName(String sourceOwnerName) {
        this.sourceOwnerName = sourceOwnerName;
    }


    public String getTargetOwnerName() {
        return targetOwnerName;
    }


    public void setTargetOwnerName(String targetOwnerName) {
        this.targetOwnerName = targetOwnerName;
    }


}
