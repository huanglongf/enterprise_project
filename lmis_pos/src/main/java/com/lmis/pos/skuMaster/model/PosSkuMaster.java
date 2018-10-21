package com.lmis.pos.skuMaster.model;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

/** 
 * @ClassName: PosSkuMaster
 * @Description: TODO()
 * @author codeGenerator
 * @date 2018-06-01 17:59:04
 * 
 */
public class PosSkuMaster implements Serializable  {

    /** 
	 * @Fields serialVersionUID : TODO(序列号) 
	 */
	private static final long serialVersionUID = 1L;
	
    public static long getSerialversionuid() {
    	return serialVersionUID;	
    }
    
    @ApiModelProperty(value = "")
	private String ccdDtBusSeasnYrCd;
	public String getCcdDtBusSeasnYrCd() {
		return ccdDtBusSeasnYrCd;
	}
	public void setCcdDtBusSeasnYrCd(String ccdDtBusSeasnYrCd) {
		this.ccdDtBusSeasnYrCd = ccdDtBusSeasnYrCd;
	}
	
    @ApiModelProperty(value = "")
	private String prodCd;
	public String getProdCd() {
		return prodCd;
	}
	public void setProdCd(String prodCd) {
		this.prodCd = prodCd;
	}
	
    @ApiModelProperty(value = "")
	private String ppk;
	public String getPpk() {
		return ppk;
	}
	public void setPpk(String ppk) {
		this.ppk = ppk;
	}
	
    @ApiModelProperty(value = "")
	private String divCd;
	public String getDivCd() {
		return divCd;
	}
	public void setDivCd(String divCd) {
		this.divCd = divCd;
	}
	
    @ApiModelProperty(value = "")
	private String gndrAgeDesc;
	public String getGndrAgeDesc() {
		return gndrAgeDesc;
	}
	public void setGndrAgeDesc(String gndrAgeDesc) {
		this.gndrAgeDesc = gndrAgeDesc;
	}
	
    @ApiModelProperty(value = "")
	private String gblCatSumDesc;
	public String getGblCatSumDesc() {
		return gblCatSumDesc;
	}
	public void setGblCatSumDesc(String gblCatSumDesc) {
		this.gblCatSumDesc = gblCatSumDesc;
	}
	
    @ApiModelProperty(value = "")
	private String gblCatCoreFocsDesc;
	public String getGblCatCoreFocsDesc() {
		return gblCatCoreFocsDesc;
	}
	public void setGblCatCoreFocsDesc(String gblCatCoreFocsDesc) {
		this.gblCatCoreFocsDesc = gblCatCoreFocsDesc;
	}
	
    @ApiModelProperty(value = "")
	private String vNumber;
	public String getVNumber() {
		return vNumber;
	}
	public void setVNumber(String vNumber) {
		this.vNumber = vNumber;
	}
	
}
