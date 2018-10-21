package com.bt.lmis.balance.model;

import java.io.Serializable;
import java.util.Map;

/** 
 * @ClassName: SettleResult
 * @Description: TODO(结算结果)
 * @author Ian.Huang
 * @date 2017年5月4日 下午2:17:56 
 * 
 */
public class EstimateResult implements Serializable {
	/** 
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	 */
	private static final long serialVersionUID = 5734500122293359920L;
	// 本次结算标志0-返回异常1-返回成功
	private boolean flag;
	//
	private Map<String, Object> msg;
	public EstimateResult() {};
	public EstimateResult(boolean flag) {
		super();
		this.flag = flag;
	}
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	public Map<String, Object> getMsg() {
		return msg;
	}
	public void setMsg(Map<String, Object> msg) {
		this.msg = msg;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
