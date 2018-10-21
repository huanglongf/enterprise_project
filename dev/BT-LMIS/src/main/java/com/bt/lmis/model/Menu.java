package com.bt.lmis.model;

/** 
* @ClassName: Menu 
* @Description: TODO(菜单) 
* @author Yuriy.Jiang
* @date 2016年5月24日 下午3:18:46 
*  
*/

public class Menu {
	
	public int id;
	public String name;
	public String url;
	public String icon;
	public int sort;
	public String remarks;
	public int node;
	public int pid;
	public int displaypid;
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String flag;
	
	
	public int getDisplaypid() {
		return displaypid;
	}
	public void setDisplaypid(int displaypid) {
		this.displaypid = displaypid;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public int getNode() {
		return node;
	}
	public void setNode(int node) {
		this.node = node;
	}
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	
	
}
