package com.bt.radar.warning;

import org.apache.poi.ss.formula.functions.T;

import com.bt.lmis.base.spring.SpringUtils;
import com.bt.radar.service.EventWarningService;

@SuppressWarnings("unchecked")
public class EventWarning {
	
	/**
	 * 
	 * @Description: TODO(事件预警升级)
	 * @return: void  
	 * @author Ian.Huang 
	 * @date 2016年9月5日下午3:10:13
	 */
	public void EventWarningLevelUp() {
		try {
			((EventWarningService<T>)SpringUtils.getBean("eventWarningServiceImpl")).eventWarningLevelUp();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @Description: TODO(时间自动预警)
	 * @return: void  
	 * @author Ian.Huang 
	 * @date 2016年9月5日下午3:09:19
	 */
	public void EventAutoWarning() {
		try {
			((EventWarningService<T>)SpringUtils.getBean("eventWarningServiceImpl")).eventAutoWarning();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
