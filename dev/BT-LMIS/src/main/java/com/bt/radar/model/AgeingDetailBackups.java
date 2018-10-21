package com.bt.radar.model;

import java.util.Date;

public class AgeingDetailBackups {
    private Integer id;

    private String ageingId;

    private String warehouseCode;

    private String warehouseName;

    private String storeCode;

    private String storeName;

    private String pName;

    private String pCode;

    private String cName;

    private String cCode;

    private String sName;

    private String sCode;

    private String productTypeCode;

    private String productTypeName;

    private String expressCode;

    private String expressName;

    private String embranceCalTime;

    private Integer ageingValue;

    private String batId;
    
    private String error;
    
private Date createTime;
    
    private String createUser;
    
    private Date updateTime;
    
    private String updateUser;

    

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}


	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAgeingId() {
        return ageingId;
    }

    public void setAgeingId(String ageingId) {
        this.ageingId = ageingId == null ? null : ageingId.trim();
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode == null ? null : warehouseCode.trim();
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName == null ? null : warehouseName.trim();
    }

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode == null ? null : storeCode.trim();
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName == null ? null : storeName.trim();
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName == null ? null : pName.trim();
    }

    public String getpCode() {
        return pCode;
    }

    public void setpCode(String pCode) {
        this.pCode = pCode == null ? null : pCode.trim();
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName == null ? null : cName.trim();
    }

    public String getcCode() {
        return cCode;
    }

    public void setcCode(String cCode) {
        this.cCode = cCode == null ? null : cCode.trim();
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName == null ? null : sName.trim();
    }

    public String getsCode() {
        return sCode;
    }

    public void setsCode(String sCode) {
        this.sCode = sCode == null ? null : sCode.trim();
    }

    public String getProductTypeCode() {
        return productTypeCode;
    }

    public void setProductTypeCode(String productTypeCode) {
        this.productTypeCode = productTypeCode == null ? null : productTypeCode.trim();
    }

    public String getProductTypeName() {
        return productTypeName;
    }

    public void setProductTypeName(String productTypeName) {
        this.productTypeName = productTypeName == null ? null : productTypeName.trim();
    }

    public String getExpressCode() {
        return expressCode;
    }

    public void setExpressCode(String expressCode) {
        this.expressCode = expressCode == null ? null : expressCode.trim();
    }

    public String getExpressName() {
        return expressName;
    }

    public void setExpressName(String expressName) {
        this.expressName = expressName == null ? null : expressName.trim();
    }

    public String getEmbranceCalTime() {
        return embranceCalTime;
    }

    public void setEmbranceCalTime(String embranceCalTime) {
        this.embranceCalTime = embranceCalTime == null ? null : embranceCalTime.trim();
    }

    public Integer getAgeingValue() {
        return ageingValue;
    }

    public void setAgeingValue(Integer ageingValue) {
        this.ageingValue = ageingValue;
    }

    public String getBatId() {
        return batId;
    }

    public void setBatId(String batId) {
        this.batId = batId == null ? null : batId.trim();
    }
    
    public  AgeingDetailBackups(String[] row) {
		this.warehouseName = row[0];
		this.pName = row[1];
		this.cName = row[2];
		this.sName = row[3];
		this.expressName = row[4];
		this.productTypeName = row[5];
		this.embranceCalTime = row[6];
		if(row[7]!=null){
			if(row[7].matches("[0-9]*")){
				this.ageingValue = Integer.parseInt(row[7]);
			}else{
				this.error ="时效值类型错误！";
			}
		}else{
			this.error ="时效值不能为空！";
		}
		
    }
    public  AgeingDetailBackups(){
    	
    }
}