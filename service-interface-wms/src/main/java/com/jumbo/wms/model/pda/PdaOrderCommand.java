package com.jumbo.wms.model.pda;

public class PdaOrderCommand extends PdaOrder{

    /**
     * 
     */
    private static final long serialVersionUID = -6118271097482352558L;
    private String beginTime;
    private String beginTime1;
    private String endTime;
    private String endTime1;
    /**
     * 执行数量
     */
    private String qty;
    public String getBeginTime() {
        return beginTime;
    }
    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }
    public String getBeginTime1() {
        return beginTime1;
    }
    public void setBeginTime1(String beginTime1) {
        this.beginTime1 = beginTime1;
    }
    public String getEndTime() {
        return endTime;
    }
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
    public String getEndTime1() {
        return endTime1;
    }
    public void setEndTime1(String endTime1) {
        this.endTime1 = endTime1;
    }
	public String getQty() {
		return qty;
	}
	public void setQty(String qty) {
		this.qty = qty;
	}
}
