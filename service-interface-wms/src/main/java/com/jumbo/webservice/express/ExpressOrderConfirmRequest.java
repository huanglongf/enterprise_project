package com.jumbo.webservice.express;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.jumbo.webservice.express.ExpressAddedService;
import com.jumbo.webservice.express.ExpressAppointTime;
import com.jumbo.webservice.express.ExpressContactInformation;
/**
 * 出库信息确认实体
 * @author kai.zhu
 *
 */
public class ExpressOrderConfirmRequest implements Serializable {
	
	private static final long serialVersionUID = 3883973741533714293L;
	
	private String account;			// 快递供应商提供帐号信息
	private String regionCode;  	// 区域编码
	private String checkword;  		// 快递供应商提供帐号对应验证码
	private String mailno;  		// 运单号
	private String type;  			// 下单类型 1：普通订单 5：上面取件单
	private String refMailno;  		// 母运单号
	private String productCode; 	// 产品类型 1：标准 5：当日达 7：次日达 10：次晨达 15：及时达
	private String transportType; 	// 运输方式 1：标准 3：航空 5：陆运 7：汽运 9：铁路
	private Boolean isFragile;		// 是否易碎
	private Boolean isDanger;		// 是否危险品
	private Boolean isLiquid;		// 是否含液体
	private Boolean isBattery;		// 是否含有锂电池
	private Boolean isWorkDay;		// 是否工作日配送
	private Boolean isNight;		// 是否夜间配送
	private Boolean isWeekend;		// 是否周末配送
	private List<ExpressAppointTime> appointTime;	// 指定时间配送对象，可以有多组
	private String orderId;			// 宝尊系统订单唯一标识
	private ExpressContactInformation sender;		// 发送信息对象
	private ExpressContactInformation receiver; 	// 收件信息对象
	private Integer itemQty; 		// 商品数量
	private String special;			// 特殊业务(保留)
	private List<ExpressAddedService> addedService; // 增值服务对象，可以有多组
	private BigDecimal weight;		// 包裹重量
	private BigDecimal volume;		// 包裹体积，单位立方CM
	private BigDecimal width;		// 宽
	private BigDecimal length;		// 长
	private BigDecimal height;		// 高
	private Integer packageQty;		// 包裹数量
	private String lpCode;
	
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getRegionCode() {
		return regionCode;
	}
	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}
	public String getCheckword() {
		return checkword;
	}
	public void setCheckword(String checkword) {
		this.checkword = checkword;
	}
	public String getMailno() {
		return mailno;
	}
	public void setMailno(String mailno) {
		this.mailno = mailno;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getRefMailno() {
		return refMailno;
	}
	public void setRefMailno(String refMailno) {
		this.refMailno = refMailno;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getTransportType() {
		return transportType;
	}
	public void setTransportType(String transportType) {
		this.transportType = transportType;
	}
	public Boolean getIsFragile() {
		return isFragile;
	}
	public void setIsFragile(Boolean isFragile) {
		this.isFragile = isFragile;
	}
	public Boolean getIsDanger() {
		return isDanger;
	}
	public void setIsDanger(Boolean isDanger) {
		this.isDanger = isDanger;
	}
	public Boolean getIsLiquid() {
		return isLiquid;
	}
	public void setIsLiquid(Boolean isLiquid) {
		this.isLiquid = isLiquid;
	}
	public Boolean getIsBattery() {
		return isBattery;
	}
	public void setIsBattery(Boolean isBattery) {
		this.isBattery = isBattery;
	}
	public Boolean getIsWorkDay() {
		return isWorkDay;
	}
	public void setIsWorkDay(Boolean isWorkDay) {
		this.isWorkDay = isWorkDay;
	}
	public Boolean getIsNight() {
		return isNight;
	}
	public void setIsNight(Boolean isNight) {
		this.isNight = isNight;
	}
	public Boolean getIsWeekend() {
		return isWeekend;
	}
	public void setIsWeekend(Boolean isWeekend) {
		this.isWeekend = isWeekend;
	}
	public List<ExpressAppointTime> getAppointTime() {
		return appointTime;
	}
	public void setAppointTime(List<ExpressAppointTime> appointTime) {
		this.appointTime = appointTime;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public ExpressContactInformation getSender() {
		return sender;
	}
	public void setSender(ExpressContactInformation sender) {
		this.sender = sender;
	}
	public ExpressContactInformation getReceiver() {
		return receiver;
	}
	public void setReceiver(ExpressContactInformation receiver) {
		this.receiver = receiver;
	}
	public Integer getItemQty() {
		return itemQty;
	}
	public void setItemQty(Integer itemQty) {
		this.itemQty = itemQty;
	}
	public String getSpecial() {
		return special;
	}
	public void setSpecial(String special) {
		this.special = special;
	}
	public List<ExpressAddedService> getAddedService() {
		return addedService;
	}
	public void setAddedService(List<ExpressAddedService> addedService) {
		this.addedService = addedService;
	}
	public BigDecimal getWeight() {
		return weight;
	}
	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}
	public BigDecimal getVolume() {
		return volume;
	}
	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}
	public BigDecimal getWidth() {
		return width;
	}
	public void setWidth(BigDecimal width) {
		this.width = width;
	}
	public BigDecimal getLength() {
		return length;
	}
	public void setLength(BigDecimal length) {
		this.length = length;
	}
	public BigDecimal getHeight() {
		return height;
	}
	public void setHeight(BigDecimal height) {
		this.height = height;
	}
	public Integer getPackageQty() {
		return packageQty;
	}
	public void setPackageQty(Integer packageQty) {
		this.packageQty = packageQty;
	}
	public String getLpCode() {
		return lpCode;
	}
	public void setLpCode(String lpCode) {
		this.lpCode = lpCode;
	}
	

}
