package com.jumbo.pms.model.command.cond;


public class ParcelSFResponse extends ParcelResponseCommand {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String SF_MOBILE_HK = "2730 0273";
	public static final String SF_MOBILE = "95338";

    /**
     * 门店区域码
     */
    private String originRegionCode;

    /**
     * 目的地区域码
     */
    private String destinationRegionCode;

    /**
     * 物流商分配给品牌的账号
     */
    private String accountNo;

    /**
     * 付款方式
     */
    private String chargeTo;
    
    private String shippingSignature;
    
    private String sfMobile;

    public String getOriginRegionCode() {
        return originRegionCode;
    }

    public void setOriginRegionCode(String originRegionCode) {
        this.originRegionCode = originRegionCode;
    }

    public String getDestinationRegionCode() {
        return destinationRegionCode;
    }

    public void setDestinationRegionCode(String destinationRegionCode) {
        this.destinationRegionCode = destinationRegionCode;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getChargeTo() {
        return chargeTo;
    }

    public void setChargeTo(String chargeTo) {
        this.chargeTo = chargeTo;
    }

	public String getShippingSignature() {
		return shippingSignature;
	}

	public void setShippingSignature(String shippingSignature) {
		this.shippingSignature = shippingSignature;
	}

	public String getSfMobile() {
		return sfMobile;
	}

	public void setSfMobile(String sfMobile) {
		this.sfMobile = sfMobile;
	}

}
