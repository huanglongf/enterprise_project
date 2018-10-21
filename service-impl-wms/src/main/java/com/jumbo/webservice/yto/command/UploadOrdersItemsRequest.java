package com.jumbo.webservice.yto.command;


import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class UploadOrdersItemsRequest {

	@XmlElement(name = "item", required = true)
	private List<UploadOrdersItemRequest> item;

	public List<UploadOrdersItemRequest> getItem() {
		return item;
	}

	public void setItem(List<UploadOrdersItemRequest> item) {
		this.item = item;
	}

}
