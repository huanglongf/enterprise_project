package com.lmis.framework.baseInfo;

import java.io.Serializable;

import com.github.pagehelper.PageInfo;

/**
 * 类的描述
 * 
 * @author Peter Dai
 * @Time 2017-5-3 下午4:23:19
 */
public class LmisResult<T> implements Serializable {

	private static final long serialVersionUID = 3997124446365032582L;

	//状态码
	String code = "";

	//消息
	String message = "";

	//分页信息
	Object meta = "";

	//数据
	Object data = "";

	public LmisResult() {}
	
	public LmisResult(String code, Object data) {
		this.code = code;
		this.data = data;
	}
	
	public LmisResult(String code, String message) {
		this.code = code;
		this.message = message;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getMeta() {
		return meta;
	}

	public void setMeta(Object meta) {
		this.meta = meta;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public void setMetaAndData(PageInfo<T> pageInfo) {
		this.data = pageInfo.getList();
		pageInfo.setList(null);
		this.meta = pageInfo;
	}

}
