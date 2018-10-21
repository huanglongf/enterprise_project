package com.jumbo.wms.model.warehouse;

public class ImperfectStvCommand extends ImperfectStv {

    /**
     * 
     */
    private static final long serialVersionUID = -344700116505305973L;
    private String skuName;
    private String jmskuCode;
    private String intInvstatusName;
    private String isSnSku;
    private Long intInvstatusId;
    private String owner;
    private String staCode;
    private String skuCode;
    private String keyProperties;
    private String defectType;
    private String defectWhy;
    private String size;
    
    


    public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getStaCode() {
		return staCode;
	}

	public void setStaCode(String staCode) {
		this.staCode = staCode;
	}

	public String getSkuCode() {
		return skuCode;
	}

	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}
	@Override
	public String getKeyProperties() {
		return keyProperties;
	}
	@Override
	public void setKeyProperties(String keyProperties) {
		this.keyProperties = keyProperties;
	}

	public String getDefectType() {
		return defectType;
	}

	public void setDefectType(String defectType) {
		this.defectType = defectType;
	}

	public String getDefectWhy() {
		return defectWhy;
	}

	public void setDefectWhy(String defectWhy) {
		this.defectWhy = defectWhy;
	}

	public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Long getIntInvstatusId() {
        return intInvstatusId;
    }

    public void setIntInvstatusId(Long intInvstatusId) {
        this.intInvstatusId = intInvstatusId;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public String getJmskuCode() {
        return jmskuCode;
    }

    public void setJmskuCode(String jmskuCode) {
        this.jmskuCode = jmskuCode;
    }

    public String getIntInvstatusName() {
        return intInvstatusName;
    }

    public void setIntInvstatusName(String intInvstatusName) {
        this.intInvstatusName = intInvstatusName;
    }

    public String getIsSnSku() {
        return isSnSku;
    }

    public void setIsSnSku(String isSnSku) {
        this.isSnSku = isSnSku;
    }


}
