package com.jumbo.webservice.yto.command;


import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "mailNoList")
public class MailNoList {

	@XmlElement(name = "mailNo", required = true)
	private List<String> mailNo;

	public List<String> getMailNo() {
		return mailNo;
	}

	public void setMailNo(List<String> mailNo) {
		this.mailNo = mailNo;
	}
}
