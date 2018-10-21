package com.bt.orderPlatform.controller.form;

import java.io.Serializable;

/***
 * SF子母单
 * <b>类名：</b>SFZMDExpressPrint.java<br>
 * @author <font color='blue'>chenkun</font> 
 * @date  2018年3月23日 下午2:25:15
 * @Description
 */
public class SFZMDExpressPrint implements Serializable{

	/** serialVersionUID */
    
    private static final long serialVersionUID = 8600922505594063924L;
    
    private String transTimeTypeB;			//
	private String lpCode;			//
	private Boolean isRailway;			//
	private String amount;			//
	private String trackingNo;			//运单号
	private String transCityCode;			//
	private String receiver;			//
	private String receverTel;			//
	private String address;			//
	private String sender;			//
	private String senderTel;			//
	private String senderAddress;			//
	private String isTransCodPos;			//
	private String isSupportCod;			//
	private String payMethod;			//
	private String insuranceAmount;			//
	private String jcustid;			//
	private String shipmentCode;			//
	private String quantity;			//
	private String refSlipCode;			//
	private String skuList;			//
	private String transmemo;			//
	private String district;			//
	private String telephone;			//
	private String remark;			//
	private String owner;			//
	private String pgindex;			//
	private String strAmount;			//
	private String transTypeB;			//
	private String city;			//
	private String sfTrackingNo;			//
	private String express_code;			//
	private String mark_destination;			//
	private String province;			//
	private String areaNumber;			//
	private String transferFee;			//
	private String transBigWord;			//
	private String weight;			//
	private String logoImg;			//
	//private String district;			//
	private String ordinal;
	private String total;
	private String mainNo;
    
    
    public String getOrdinal(){
        return ordinal;
    }


    
    public void setOrdinal(String ordinal){
        this.ordinal = ordinal;
    }


    
    public String getTotal(){
        return total;
    }


    
    public void setTotal(String total){
        this.total = total;
    }


    public String getMainNo(){
        return mainNo;
    }
    
    public void setMainNo(String mainNo){
        this.mainNo = mainNo;
    }
    public Boolean getIsRailway() {
		return isRailway;
	}
	public String getTransferFee() {
		return transferFee;
	}
	public void setTransferFee(String transferFee) {
		this.transferFee = transferFee;
	}
	public String getTransBigWord() {
		return transBigWord;
	}
	public void setTransBigWord(String transBigWord) {
		this.transBigWord = transBigWord;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getLogoImg() {
		return logoImg;
	}
	public void setLogoImg(String logoImg) {
		this.logoImg = logoImg;
	}
	public String getAreaNumber() {
		return areaNumber;
	}
	public void setAreaNumber(String areaNumber) {
		this.areaNumber = areaNumber;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getExpress_code() {
		return express_code;
	}
	public void setExpress_code(String express_code) {
		this.express_code = express_code;
	}
	public String getMark_destination() {
		return mark_destination;
	}
	public void setMark_destination(String mark_destination) {
		this.mark_destination = mark_destination;
	}
	public void setIsRailway(Boolean isRailway) {
		this.isRailway = isRailway;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getPgindex() {
		return pgindex;
	}
	public void setPgindex(String pgindex) {
		this.pgindex = pgindex;
	}
	public String getStrAmount() {
		return strAmount;
	}
	public void setStrAmount(String strAmount) {
		this.strAmount = strAmount;
	}
	public String getTransTypeB() {
		return transTypeB;
	}
	public void setTransTypeB(String transTypeB) {
		this.transTypeB = transTypeB;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getSfTrackingNo() {
		return sfTrackingNo;
	}
	public void setSfTrackingNo(String sfTrackingNo) {
		this.sfTrackingNo = sfTrackingNo;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getTransTimeTypeB() {
		return transTimeTypeB;
	}
	public void setTransTimeTypeB(String transTimeTypeB) {
		this.transTimeTypeB = transTimeTypeB;
	}
	public String getShipmentCode() {
		return shipmentCode;
	}
	public void setShipmentCode(String shipmentCode) {
		this.shipmentCode = shipmentCode;
	}
	public String getLpCode() {
		return lpCode;
	}
	public void setLpCode(String lpCode) {
		this.lpCode = lpCode;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getTrackingNo() {
		return trackingNo;
	}
	public void setTrackingNo(String trackingNo) {
		this.trackingNo = trackingNo;
	}
	public String getTransCityCode() {
		return transCityCode;
	}
	public void setTransCityCode(String transCityCode) {
		this.transCityCode = transCityCode;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public String getReceverTel() {
		return receverTel;
	}
	public void setReceverTel(String receverTel) {
		this.receverTel = receverTel;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getSenderTel() {
		return senderTel;
	}
	public void setSenderTel(String senderTel) {
		this.senderTel = senderTel;
	}
	public String getSenderAddress() {
		return senderAddress;
	}
	public void setSenderAddress(String senderAddress) {
		this.senderAddress = senderAddress;
	}
	public String getIsTransCodPos() {
		return isTransCodPos;
	}
	public void setIsTransCodPos(String isTransCodPos) {
		this.isTransCodPos = isTransCodPos;
	}
	public String getIsSupportCod() {
		return isSupportCod;
	}
	public void setIsSupportCod(String isSupportCod) {
		this.isSupportCod = isSupportCod;
	}
	public String getPayMethod() {
		return payMethod;
	}
	public void setPayMethod(String payMethod) {
		this.payMethod = payMethod;
	}
	public String getInsuranceAmount() {
		return insuranceAmount;
	}
	public void setInsuranceAmount(String insuranceAmount) {
		this.insuranceAmount = insuranceAmount;
	}
	public String getJcustid() {
		return jcustid;
	}
	public void setJcustid(String jcustid) {
		this.jcustid = jcustid;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getRefSlipCode() {
		return refSlipCode;
	}
	public void setRefSlipCode(String refSlipCode) {
		this.refSlipCode = refSlipCode;
	}
	public String getSkuList() {
		return skuList;
	}
	public void setSkuList(String skuList) {
		this.skuList = skuList;
	}
	public String getTransmemo() {
		return transmemo;
	}
	public void setTransmemo(String transmemo) {
		this.transmemo = transmemo;
	}
	
}
