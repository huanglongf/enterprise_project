package com.jumbo.wms.manager.boc;


public interface BocInterfaceManagerProxy {
	
	/**
	 * 收完消息，定时执行增量出入库
	 */
	public void executeInventoryMovement();
	
	

    /**
     * 接收 InventorySnapshot mq 执行入库
     * 
     * @param message
     */
    public void receiveInventorySnapshot(String message);

    /**
     * 接收MasterData
     * 
     * @param message
     */
    void receiveMasterDataInfo(String message);
    
    /**
     * 接收并执行订单出库
     * @param message
     */
    void  bocExecuteOrderOutbound(String message);
    
    /**
     * 退货入库
     * @param message
     */
    void bocExecuteReturnOrderInbound(String message);
}
