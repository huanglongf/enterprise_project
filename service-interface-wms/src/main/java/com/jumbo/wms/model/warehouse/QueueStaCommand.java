package com.jumbo.wms.model.warehouse;

/**
 * 接口/类说明：
 * @ClassName: QueueStaCommand 
 * @author LuYingMing
 * @date 2016年7月25日 下午12:44:58
 */
public class QueueStaCommand extends QueueSta {

	
	private static final long serialVersionUID = 8640755479137783652L;
	
	/**
     * 数量起
     */
    private Integer numberUp;
    /**
     * 数量至
     */
    private Integer amountTo;
    /**
     * sql 转义字符串
     */
    private String statusStr; 
    
	public Integer getNumberUp() {
		return numberUp;
	}
	public void setNumberUp(Integer numberUp) {
		this.numberUp = numberUp;
	}
	public Integer getAmountTo() {
		return amountTo;
	}
	public void setAmountTo(Integer amountTo) {
		this.amountTo = amountTo;
	}
	public String getStatusStr() {
		return statusStr;
	}
	public void setStatusStr(String statusStr) {
		this.statusStr = statusStr;
	}
	

}
