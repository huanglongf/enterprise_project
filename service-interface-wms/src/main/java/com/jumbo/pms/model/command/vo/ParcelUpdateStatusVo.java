package com.jumbo.pms.model.command.vo;

import java.io.Serializable;


/**
 * @author ge.sheng
 * 
 */
public class ParcelUpdateStatusVo implements Serializable {

	/**
     * 
     */
    private static final long serialVersionUID = 4472093389705104871L;
	public static final String OPTYPE_STORE = "002";
    public static final String OPTYPE_CUSTOMER = "003";

    private String code;

	private String opType;
	
	private String remark;
	
	private String extend;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getOpType() {
        return opType;
    }

    public void setOpType(String opType) {
        this.opType = opType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getExtend() {
        return extend;
    }

    public void setExtend(String extend) {
        this.extend = extend;
    }

	
}
