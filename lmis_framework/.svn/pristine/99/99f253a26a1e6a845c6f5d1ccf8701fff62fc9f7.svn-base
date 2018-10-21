package com.lmis.framework.baseInfo;

import java.io.Serializable;

/**
 * @author daikaihua
 * @date 2017年11月20日
 * @todo TODO
 */
public class LmisPageObject implements Serializable {

	/** 
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	 */
	private static final long serialVersionUID = 7945410454147111506L;

	int pageNum;
	
	int pageSize;
	
	String lmisOrderByStr;

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	public String getLmisOrderByStr() {
		return lmisOrderByStr;
	}

	public void setLmisOrderByStr(String lmisOrderByStr) {
		this.lmisOrderByStr = lmisOrderByStr;
	}

	public void setDefaultPage(int defPageNum, int defPageSize){
		if(pageNum == 0){
			this.pageNum = defPageNum;
		}
		if(pageSize == 0){
			this.pageSize = defPageSize;
		}
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
