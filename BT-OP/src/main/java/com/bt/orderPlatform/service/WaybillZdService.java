package com.bt.orderPlatform.service;

import java.util.List;

/***
 * 
 * <b>类名：</b>WaybillZdService.java<br>
 * @author <font color='blue'>chenkun</font> 
 * @date  2018年3月23日 上午11:06:31
 * @Description
 * @param <WaybillZd>
 */
public interface WaybillZdService<WaybillZd> {

	public int insert(List<WaybillZd> zds);
	
	public int insert(WaybillZd zd);
	
	public List<WaybillZd> selectByOrderId(String orderId);

}
