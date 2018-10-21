package com.jumbo.wms.model.warehouse;


import com.jumbo.wms.model.baseinfo.BiChannel;

public class BiChannelCommand extends BiChannel {

    /**
	 * 
	 */
    private static final long serialVersionUID = -1460614387690663435L;

    private String cName;
    private String customerId;
    /**
     * EDW需要字段
     */
    private String isNds;// 是否显示金额(保留)
    private String isM;// 是否出库短信通知(保留)
    private String isMa;// 是否允许混店铺创建批次
    private String isRnp;// 是否退仓强制装箱
    private String isJdoo;// 是否使用京东电子面单
    private String statusString;

    // //////////////////////////////////////////////////
    private String asnTypeString;
    private String rsnTypeString;

    private Integer intSpecialType;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }



    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String getIsNds() {
        return isNds;
    }

    public void setIsNds(String isNds) {
        this.isNds = isNds;
    }

    public String getIsM() {
        return isM;
    }

    public void setIsM(String isM) {
        this.isM = isM;
    }

    public String getIsMa() {
        return isMa;
    }

    public void setIsMa(String isMa) {
        this.isMa = isMa;
    }

    public String getIsRnp() {
        return isRnp;
    }

    public void setIsRnp(String isRnp) {
        this.isRnp = isRnp;
    }

    public String getIsJdoo() {
        return isJdoo;
    }

    public void setIsJdoo(String isJdoo) {
        this.isJdoo = isJdoo;
    }

    public String getStatusString() {
        return statusString;
    }

    public void setStatusString(String statusString) {
        this.statusString = statusString;
    }

    public String getAsnTypeString() {
        return asnTypeString;
    }

    public void setAsnTypeString(String asnTypeString) {
        this.asnTypeString = asnTypeString;
    }

    public String getRsnTypeString() {
        return rsnTypeString;
    }

    public void setRsnTypeString(String rsnTypeString) {
        this.rsnTypeString = rsnTypeString;
    }

    public Integer getIntSpecialType() {
        return intSpecialType;
    }

    public void setIntSpecialType(Integer intSpecialType) {
        this.intSpecialType = intSpecialType;
    }

}
