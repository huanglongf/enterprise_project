package com.jumbo.wms.manager.vmi.jdData;


import cn.baozun.model.jd.EtmsWayBillSend;

import com.jumbo.wms.manager.BaseManager;



public interface JdManager extends BaseManager {
	public void sendMqGetJdTransNo(Long omsShopId,String mqName);
	public void senMqJdReceiveOrder(EtmsWayBillSend billSend,String mqName);
}
