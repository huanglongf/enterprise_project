package com.jumbo.wms.model.command;


import com.jumbo.wms.model.baseinfo.Transportator;


/**
 * 
 * 物流商
 * 
 * @author sjk
 * 
 */
public class TransportatorCommand extends Transportator {
    /**
	 * 
	 */
    private static final long serialVersionUID = -7812676025062855078L;

    private Integer isRef;

    private String isSupportCodStr;

    private Integer isSupportCodInt;


    // /**
    // * 编码
    // */
    // private String code;
    // /**
    // * 名称
    // */
    // private String name;
    //
    // /**
    // * 平台对接编码
    // */
    // private String platformCode;


    private String statusId;

    /**
     * 状态名称
     */
    private String statusName;

    /**
     * 是否后置
     */
    private String isAfter;

    /**
     * 是否分区
     */
    private String isReg;

    /**
     * 用户名
     */
    private String userName;

    // private Boolean isSupportCod;

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getStatusId() {
        return statusId;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }



    public String getIsSupportCodStr() {
        return isSupportCodStr;
    }

    public void setIsSupportCodStr(String isSupportCodStr) {
        this.isSupportCodStr = isSupportCodStr;
    }

    public Integer getIsSupportCodInt() {
        return isSupportCodInt;
    }

    public void setIsSupportCodInt(Integer isSupportCodInt) {
        this.isSupportCodInt = isSupportCodInt;
    }

    public Integer getIsRef() {
        return isRef;
    }

    public void setIsRef(Integer isRef) {
        this.isRef = isRef;
    }

    public String getIsAfter() {
        return isAfter;
    }

    public void setIsAfter(String isAfter) {
        this.isAfter = isAfter;
    }

    public String getIsReg() {
        return isReg;
    }

    public void setIsReg(String isReg) {
        this.isReg = isReg;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
