package com.jumbo.wms.daemon;

import java.io.UnsupportedEncodingException;

public interface ZtoOrderTask {

	/**
	 * ZTO 申请电子运单号
	 * 
	 * @param number
	 *            ,lastno
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	void ztoTransNo() throws UnsupportedEncodingException;

	// 设置ZTO单据号
	void ztoInterfaceByWarehouse();

	// 重置取消订单占用的transno
	void reUsedTransNo();
}
