package com.jumbo.rmi.warehouse.vmi;

import java.io.Serializable;

/**
 * 
 * VmiTfxLine转出明细
 */
public class VmiAdjLine implements Serializable {

	private static final long serialVersionUID = -7970961979280110398L;

	/**
	 * 商品唯一编码
	 */
	private String upc;

	/**
	 * 数量
	 */
	private Long qty;

	/**
	 * 入库单箱号
	 */
	private String cartonNo;

	/**
	 * 行唯一标识
	 */
	private String lineSeq;

	/**
	 * 库存状态
	 */
	private String invStatus;

	/**
	 * 定制逻辑使用json格式传递所有定制字段
	 * 
	 */
	private String extMemo;

	/**
	 * 宝尊系统初始库存状态状态
	 */
    private String fromStatus;

	/**
	 * 宝尊系统调整后的库存状态
	 */
    private String toStatus;

	/**
	 * 映射成品牌方给的库存状态名称
	 */
    private String transactionType;

	public String getUpc() {
		return upc;
	}

	public void setUpc(String upc) {
		this.upc = upc;
	}

	public Long getQty() {
		return qty;
	}

	public void setQty(Long qty) {
		this.qty = qty;
	}

	public String getCartonNo() {
		return cartonNo;
	}

	public void setCartonNo(String cartonNo) {
		this.cartonNo = cartonNo;
	}

	public String getLineSeq() {
		return lineSeq;
	}

	public void setLineSeq(String lineSeq) {
		this.lineSeq = lineSeq;
	}

	public String getInvStatus() {
		return invStatus;
	}

	public void setInvStatus(String invStatus) {
		this.invStatus = invStatus;
	}

	public String getExtMemo() {
		return extMemo;
	}

	public void setExtMemo(String extMemo) {
		this.extMemo = extMemo;
	}

    public String getFromStatus() {
        return fromStatus;
    }

    public void setFromStatus(String fromStatus) {
        this.fromStatus = fromStatus;
    }

    public String getToStatus() {
        return toStatus;
    }

    public void setToStatus(String toStatus) {
        this.toStatus = toStatus;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

}
