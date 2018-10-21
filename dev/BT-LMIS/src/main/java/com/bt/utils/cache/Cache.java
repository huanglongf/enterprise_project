package com.bt.utils.cache;

import java.io.Serializable;

/** 
 * @ClassName: Cache
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Ian.Huang
 * @date 2017年4月26日 下午12:20:32 
 * 
 */
public class Cache implements Serializable {
	/** 
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	 */
	private static final long serialVersionUID = -1744486753444138115L;
	
	// 值
	private Object value;
	// 保存的时间戳 
	private long gmtModify;
	// 过期时间 
	private int expire;
	
	public Cache() {
		super();
	}
	public Cache(Object value, long gmtModify, int expire) {  
		super();
		this.value = value;
		this.gmtModify = gmtModify;
		this.expire = expire;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	public long getGmtModify() {
		return gmtModify;
	}
	public void setGmtModify(long gmtModify) {
		this.gmtModify = gmtModify;
	}
	public int getExpire() {
		return expire;
	}
	public void setExpire(int expire) {
		this.expire = expire;
	}
	
}