package com.bt.orderPlatform.dao;

import java.util.List;

/***
 * 
 * <b>类名：</b>WaybillZdMapper.java<br>
 * @author <font color='blue'>chenkun</font> 
 * @date  2018年3月23日 上午11:04:38
 * @Description
 * @param <WaybillZd>
 */
public interface WaybillZdMapper<WaybillZd> {

	public int insert(WaybillZd waybillZd);
	
	public List<WaybillZd> selectByOrderId(String orderId);

}
