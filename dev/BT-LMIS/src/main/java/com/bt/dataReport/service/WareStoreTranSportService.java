
package com.bt.dataReport.service;

import java.util.List;

import com.bt.dataReport.bean.WareStoreTranSportBean;

/**
 *@author jinggong.li
 *@date 2018年7月3日  
 */
public interface WareStoreTranSportService {
	
	public List<WareStoreTranSportBean> selectWare() throws Exception;
	
	
	public List<WareStoreTranSportBean> selectStore()throws Exception;
	
	
	public List<WareStoreTranSportBean> selectTranSport()throws Exception;
}
