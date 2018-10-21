package com.bt.workOrder.bean;

import java.util.ArrayList;
import java.util.List;

/** 
* @ClassName: Enclosure 
* @Description: TODO(附件) 
* @author Yuriy.Jiang
* @date 2017年8月25日 下午5:02:49 
*  
*/
public class Enclosure {
	
	public String log;
	public String process_remark;
	public boolean flag;
	public List<Accessory> accessory_list;
	
	public String getLog() {
		return log;
	}
	public void setLog(String log) {
		this.log = log;
	}
	public String getProcess_remark() {
		return process_remark;
	}
	public void setProcess_remark(String process_remark) {
		this.process_remark = process_remark;
	}
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	public List<Accessory> getAccessory_list() {
		return accessory_list;
	}
	public void setAccessory_list(List<Accessory> accessory_list) {
		this.accessory_list = accessory_list;
	}
	public Enclosure(String log, String process_remark, String accessory) {
		super();
		this.log = log;
		this.process_remark = process_remark;
		List<Accessory> accessory_list = new ArrayList<>();
		if(null!=accessory && !accessory.equals("")){
			String [] ac = accessory.split("_");
			for (int i = 0; i < ac.length; i++) {
				String filename = ac[i];
				String prefix=ac[i].substring(ac[i].lastIndexOf(".")+1);
				Accessory a = new Accessory(filename, prefix);
				accessory_list.add(a);
			}
			this.accessory_list = accessory_list;
			this.flag = true;
		}else{
			this.flag = false;
		}
	}
	
	
}
