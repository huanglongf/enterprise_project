package com.jumbo.rmi.warehouse;

import java.io.Serializable;

public class PmsBaseResult  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7118841808001147946L;
	
    public static final int STATUS_SUCCESS = 1;//成功
    public static final int STATUS_ERROR = 0;//失败、
    
    /**
     * result Code
     */
    public static final int ERROR_CODE_50001 = 50001;//HUB端系統異常
    public static final int ERROR_CODE_50002 = 50002;//簽名不匹配
    //不允許重複調用
    public static final int ERROR_CODE_60001 = 60001;//請求數據為空
    public static final int ERROR_CODE_60002 = 60002;//數據非法, 請求失敗
    public static final int ERROR_CODE_60003 = 60003;//PMS系統異常
    //數據異常
    public static final int ERROR_CODE_70001 = 70001;//根據運單號[{}]和物流商[{}]查詢不到包裹
    public static final int ERROR_CODE_70002 = 70002;//根據運單號[{}]和物流商[{}]查詢到包裹不屬於當前自提點,簽收失敗
    public static final int ERROR_CODE_70003 = 70003;//運單號[{}]和物流商[{}]包裹簽收異常
    public static final int ERROR_CODE_70004 = 70004;//調用物流商接口失敗
    public static final int ERROR_CODE_70005 = 70005;//門店編碼[{}]不存在
    public static final int ERROR_CODE_70006 = 70006;//根据運單號[{0}]和物流商[{1}]查询到包裹當前狀態是[{2}]，簽收失敗
    public static final int ERROR_CODE_70007 = 70007;//倉庫[{0}]不存在
    public static final int ERROR_CODE_70008 = 70008;//当前運單號[{0}]已存在，更新失敗
    public static final int ERROR_CODE_70009 = 70009;//數據更新失敗,請聯繫客服
    //success
//    public static final int SUCCESS_CODE_30001 = 30001;//門店允許簽收
//    public static final int SUCCESS_CODE_30002 = 30002;//運單號[{}]和物流商[{}]包裹簽收成功
//    public static final int SUCCESS_CODE_30003 = 30003;//運單號[{}]和物流商[{}]包裹重复签收
    
    
    /**
     * 状态 1：成功 0：失败
     */
    private int status;
    
    /**
     * 原因
     */
    private String msg;
    
    /**
     * 物流服务商
     */
    private String lpCode;
    
    /**
     * 运单号
     */
    private String mailNo;
    
    /**
     * 订单号
     */
    private String outerOrderCode;

    /**
     * 收件人
     */
    private String receiver;

    /**
     * 收件人电话
     */
    private String receiverMobile;

    /**
     * 收件人地址
     */
    private String receiverAddress;

    /**
     * 物流商分配给品牌的账号
     */
    private String accountNo;

    /**
     * 付款方式
     */
    private String chargeTo;
    
    /**
     * 门店区域码
     */
    private String originRegionCode;

    /**
     * 目的地区域码
     */
    private String destinationRegionCode;
    
    /**
     * 门店地址
     */
    private String o2oShopAddress;
    
    /**
     * 門店名稱
     */
    private String shipperSignature;
    
    /**
     * 错误代码
     */
    private Integer errorCode;

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

	public String getMailNo() {
		return mailNo;
	}

	public void setMailNo(String mailNo) {
		this.mailNo = mailNo;
	}

	public String getOuterOrderCode() {
		return outerOrderCode;
	}

	public void setOuterOrderCode(String outerOrderCode) {
		this.outerOrderCode = outerOrderCode;
	}

	public String getLpCode() {
		return lpCode;
	}

	public void setLpCode(String lpCode) {
		this.lpCode = lpCode;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getReceiverMobile() {
		return receiverMobile;
	}

	public void setReceiverMobile(String receiverMobile) {
		this.receiverMobile = receiverMobile;
	}

	public String getReceiverAddress() {
		return receiverAddress;
	}

	public void setReceiverAddress(String receiverAddress) {
		this.receiverAddress = receiverAddress;
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

	public String getO2oShopAddress() {
		return o2oShopAddress;
	}

	public void setO2oShopAddress(String o2oShopAddress) {
		this.o2oShopAddress = o2oShopAddress;
	}

	public String getShipperSignature() {
		return shipperSignature;
	}

	public void setShipperSignature(String shipperSignature) {
		this.shipperSignature = shipperSignature;
	}

	public Integer getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}

}
