package com.jumbo.webservice.express;

import java.io.Serializable;

import com.jumbo.webservice.express.ExpressResponse;

/**
 * 出库信息确认反馈实体
 * @author kai.zhu
 *
 */
public class ExpressOrderConfirmResponse extends ExpressResponse implements Serializable {
	
	private static final long serialVersionUID = 3401652726028934638L;
	
	private String mailno;	// 运单号
	private String orderId;	// 宝尊系统订单唯一标识
	
	public String getMailno() {
		return mailno;
	}
	public void setMailno(String mailno) {
		this.mailno = mailno;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

}
