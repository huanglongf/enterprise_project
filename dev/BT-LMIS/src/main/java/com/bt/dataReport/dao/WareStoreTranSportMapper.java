package com.bt.dataReport.dao;

import java.util.List;

import com.bt.dataReport.bean.WareStoreTranSportBean;
import com.bt.lmis.code.BaseMapper;

/**
 *@author jinggong.li
 *@date 2018年7月3日  
 */
public interface WareStoreTranSportMapper extends BaseMapper {
   
	public List<WareStoreTranSportBean> selectWare() throws Exception;
	
	public List<WareStoreTranSportBean> selectStore() throws Exception;
	
	public List<WareStoreTranSportBean> selectTranSport() throws Exception;
	
}
