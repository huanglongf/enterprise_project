package com.jumbo.pms.model.command.vo;

import java.io.Serializable;


/**
 * 包裹更新运单号
 * @author ge.sheng
 * 
 */
public class ParcelUpdateMailNoVo implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 2752463110663314521L;


    private String code;
    /** 新物流商 允许空 */
    private String newLpcode;
    /** 新物流商 */
    private String newMailNo;
    /** 扩展信息 */
    private String extend;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNewLpcode() {
        return newLpcode;
    }

    public void setNewLpcode(String newLpcode) {
        this.newLpcode = newLpcode;
    }

    public String getNewMailNo() {
        return newMailNo;
    }

    public void setNewMailNo(String newMailNo) {
        this.newMailNo = newMailNo;
    }

    public String getExtend() {
        return extend;
    }

    public void setExtend(String extend) {
        this.extend = extend;
    }

}
