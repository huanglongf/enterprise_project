package com.jumbo.wms.model.warehouse;

/**
 * 接口/类说明：出库反馈PACS
 * 
 * @ClassName: WmsIntransitNoticeOmsCommand
 * @author LuYingMing
 * @date 2016年8月15日 下午12:26:08
 */
public class WmsIntransitNoticeOmsCommand extends WmsIntransitNoticeOms{
	
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
     * sql 转义字符串(是否邮件通知)
     */
    private String statusStr; 

    /**
     * 仓库名称
     */
    private String whStr;

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

    public String getWhStr(){
        return whStr;
    }

    public void setWhStr(String whStr){
        this.whStr = whStr;
    }
	

}
