package com.bt.vims.page;

import java.util.List;

public class PageView<T> {
	/** 分页数据 **/
	private List<T> records;
	/** 页码开始索引和结束索引 **/
	private PageIndex pageindex;
	/** 总页数 **/
	private int totalpage = 1;
	/** 每页显示记录数 **/
	private int maxresult = 10;
	/** 当前页 **/
	private int currentpage = 1;
	/** 总记录数 **/
	private int totalrecord;
	/** 页码数量 **/
	private int pagecode = 10;
	/** 分页标签HTML **/
	private String pageView;
	
	/** 要获取记录的开始索引 **/
	public int getFirstResult() {
		return (this.currentpage - 1) * this.maxresult;
	}

	public int getEndResult() {
		return (this.currentpage) * this.maxresult;
	}

	public int getPagecode() {
		return pagecode;
	}

	public void setPagecode(int pagecode) {
		this.pagecode = pagecode;
	}

	public PageView(int maxresult, int currentpage) {
		this.maxresult = maxresult;
		this.currentpage = currentpage;
	}

	public void setQueryResult(QueryResult<T> qr,int page) {
		setTotalrecord(qr.getTotalrecord());
		setRecords(qr.getResultlist());
		setPageView(page);
	}

	public int getTotalrecord() {
		return totalrecord;
	}

	public void setTotalrecord(int totalrecord) {
		this.totalrecord = totalrecord;
		setTotalpage(this.totalrecord % this.maxresult == 0 ? this.totalrecord / this.maxresult : this.totalrecord / this.maxresult + 1);
	}

	public List<T> getRecords() {
		return records;
	}

	public void setRecords(List<T> records) {
		this.records = records;
	}

	public PageIndex getPageindex() {
		return pageindex;
	}

	public int getTotalpage() {
		return totalpage;
	}

	public void setTotalpage(int totalpage) {
		this.totalpage = totalpage;
		this.pageindex = PageIndex.getPageIndex(pagecode, currentpage, totalpage);
	}

	public int getMaxresult() {
		return maxresult;
	}

	public int getCurrentpage() {
		return currentpage;
	}

	public void setPageView(int page) {
		this.pageView = Pagination.getPageView(maxresult, totalrecord, totalpage, page);
	}
	
	public String getPageView() {
		return pageView;
	}

}
