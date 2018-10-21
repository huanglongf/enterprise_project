package com.jumbo.webservice.yto.command;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "MailNoResponse")
public class YtoTransNoResponse {
	/**
	 * 批次号
	 */
	@XmlElement(required = true, name = "sequence")
	private String sequence;

	/**
	 * 数量
	 */
	@XmlElement(required = true, name = "quantity")
	private String quantity;

	/**
	 * 电子面单号列表
	 */
	@XmlElement(required = true, name = "mailNoList")
	private MailNoList mailNoList;

	/**
	 * 成功与否
	 */
	@XmlElement(required = true, name = "success")
	private Boolean success;

	/**
	 * 代收/非代收
	 */
	@XmlElement(required = true, name = "materialCode")
	private String materialCode;

	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public MailNoList getMailNoList() {
		return mailNoList;
	}

	public void setMailNoList(MailNoList mailNoList) {
		this.mailNoList = mailNoList;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public String getMaterialCode() {
		return materialCode;
	}

	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}
}
