package com.jumbo.wms.model.warehouse;

import java.io.Serializable;

public class InvQstaLineCommand implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8742032057361732382L;
	private Long id;
	private String owner;
	private String skuCode;
	private Long qty;
	private QueueStaLineStatus status;
	private QueueStaLineType type;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getSkuCode() {
		return skuCode;
	}

	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}

	public Long getQty() {
		return qty;
	}

	public void setQty(Long qty) {
		this.qty = qty;
	}

	public QueueStaLineStatus getStatus() {
		return status;
	}

	public void setStatus(QueueStaLineStatus status) {
		this.status = status;
	}

	public QueueStaLineType getType() {
		return type;
	}

	public void setType(QueueStaLineType type) {
		this.type = type;
	}

}
