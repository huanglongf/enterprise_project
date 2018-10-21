package com.bt.common.controller.result;

import java.io.Serializable;

/** 
 * @ClassName: Result
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Ian.Huang
 * @date 2017年8月18日 上午9:53:27 
 * 
 */
public class Result implements Serializable {

	/** 
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	 */
	private static final long serialVersionUID = 1L;
	
	private boolean flag;
	private String msg;

	private Object data;
	
	public Result() {}
	public Result(boolean flag, String msg) {
		this.flag = flag;
		this.msg = msg;
		
	}
	public Result(boolean flag, String msg,Object data) {
		this.flag = flag;
		this.msg = msg;
		this.data=data;

	}
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
