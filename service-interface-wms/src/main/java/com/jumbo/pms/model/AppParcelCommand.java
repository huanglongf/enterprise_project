package com.jumbo.pms.model;

import java.io.Serializable;


/**
 * @author ShengGe
 * 
 */
public class AppParcelCommand implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 381501857018369188L;

	private String lpcode;
	
	private String mailNo;
	
	private String slipMailNo;
	
	private String slipLpcode;

    private String o2oShopCode;
	
	private String opType;
	
	private String outerOrderCode; 
	
	private String extend;

	public String getLpcode() {
		return lpcode;
	}

	public void setLpcode(String lpcode) {
		this.lpcode = lpcode;
	}

	public String getMailNo() {
		return mailNo;
	}

	public void setMailNo(String mailNo) {
		this.mailNo = mailNo;
	}
	   
    public String getSlipMailNo() {
        return slipMailNo;
    }

    public void setSlipMailNo(String slipMailNo) {
        this.slipMailNo = slipMailNo;
    }

	public String getSlipLpcode() {
        return slipLpcode;
    }

    public void setSlipLpcode(String slipLpcode) {
        this.slipLpcode = slipLpcode;
    }

    public String getO2oShopCode() {
		return o2oShopCode;
	}

	public void setO2oShopCode(String o2oShopCode) {
		this.o2oShopCode = o2oShopCode;
	}

	public String getOpType() {
		return opType;
	}

	public void setOpType(String opType) {
		this.opType = opType;
	}

	public String getOuterOrderCode() {
		return outerOrderCode;
	}

	public void setOuterOrderCode(String outerOrderCode) {
		this.outerOrderCode = outerOrderCode;
	}

	public String getExtend() {
		return extend;
	}

	public void setExtend(String extend) {
		this.extend = extend;
	}
	
}
