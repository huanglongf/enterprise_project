package com.bt.common.model;

import java.io.Serializable;
import java.util.Date;

/** 
 * @ClassName: ShardTest
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Ian.Huang
 * @date 2017年6月28日 下午3:53:20 
 * 
 */
public class ShardTest implements Serializable {

	/** 
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	 */
	private static final long serialVersionUID = -5538552621036594795L;
	private Date time;
	public ShardTest() {}
	public ShardTest(Date time) {
		this.time = time;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
