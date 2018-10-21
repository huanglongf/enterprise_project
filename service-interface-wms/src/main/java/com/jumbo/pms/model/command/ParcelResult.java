package com.jumbo.pms.model.command;

import java.io.Serializable;

public class ParcelResult  implements Serializable {

	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final int STATUS_SUCCESS = 1;//成功
    public static final int STATUS_ERROR = 0;//失败
    
    /**
     * result Code
     */
    public static final int ERROR_CODE_50001 = 50001;//HUB端系统异常
    public static final int ERROR_CODE_50002 = 50002;//签名不匹配
    //不允许重複调用
    public static final int ERROR_CODE_60001 = 60001;//请求数据为空
    public static final int ERROR_CODE_60002 = 60002;//数据非法, 请求失败
    public static final int ERROR_CODE_60003 = 60003;//PMS系统异常
    //数据异常
    public static final int ERROR_CODE_70001 = 70001;//查无此件
    public static final int ERROR_CODE_70002 = 70002;//包裹不属于当前自提点
    public static final int ERROR_CODE_70003 = 70003;//包裹签收异常
    public static final int ERROR_CODE_70004 = 70004;//调用物流商接口失败
    public static final int ERROR_CODE_70005 = 70005;//门店编码[{}]不存在
    public static final int ERROR_CODE_70006 = 70006;//包裹当前状态是[{2}]，签收失败
    public static final int ERROR_CODE_70007 = 70007;//仓库[{0}]不存在
    public static final int ERROR_CODE_70008 = 70008;//当前运单号[{0}]已存在，更新失败
    public static final int ERROR_CODE_70009 = 70009;//数据更新失败,请联系客服
    public static final int ERROR_CODE_70010 = 70010;//门店没有配置物流商
    public static final int ERROR_CODE_70011 = 70011;//快递单号已存在
    public static final int ERROR_CODE_70012 = 70012;//查询到多个订单信息
    public static final int ERROR_CODE_70013 = 70013;//物流商异常反馈

    //success
//    public static final int SUCCESS_CODE_30001 = 30001;//门店允许签收
//    public static final int SUCCESS_CODE_30002 = 30002;//运单号[{}]和物流商[{}]包裹签收成功
//    public static final int SUCCESS_CODE_30003 = 30003;//运单号[{}]和物流商[{}]包裹重复签收
    
    /**
     * 渠道
     */
    private String channelCode;
    
    /**
     * 包裹对接唯一编码
     */
    private String parcelCode;
    
    /**
     * 相关编码
     */
    private String slipCode;
    
    /**
     * 相关编码2
     */
    private String addSlipCode;
    
    /**
     * 状态 1：成功 0：失败
     */
    private int status;
    
    /**
     * 原因
     */
    private String msg;
    
    /**
     * 错误代码
     */
    private Integer errorCode;
    
    /**
     * 扩展字段
     */
    private String extend;
    /**
     * 扩展字段1
     */
    private String ext_str1;
    
    /**
     * 扩展字段2
     */
    private String ext_str2;
    
    /**
     * 扩展字段3
     */
    private String ext_str3;

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public String getParcelCode() {
        return parcelCode;
    }

    public void setParcelCode(String parcelCode) {
        this.parcelCode = parcelCode;
    }

    public String getSlipCode() {
        return slipCode;
    }

    public void setSlipCode(String slipCode) {
        this.slipCode = slipCode;
    }

    public String getAddSlipCode() {
        return addSlipCode;
    }

    public void setAddSlipCode(String addSlipCode) {
        this.addSlipCode = addSlipCode;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getExtend() {
        return extend;
    }

    public void setExtend(String extend) {
        this.extend = extend;
    }

    public String getExt_str1() {
        return ext_str1;
    }

    public void setExt_str1(String ext_str1) {
        this.ext_str1 = ext_str1;
    }

    public String getExt_str2() {
        return ext_str2;
    }

    public void setExt_str2(String ext_str2) {
        this.ext_str2 = ext_str2;
    }

    public String getExt_str3() {
        return ext_str3;
    }

    public void setExt_str3(String ext_str3) {
        this.ext_str3 = ext_str3;
    }

}
