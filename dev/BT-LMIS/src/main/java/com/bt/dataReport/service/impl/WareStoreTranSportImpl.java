
package com.bt.dataReport.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.bt.dataReport.bean.WareStoreTranSportBean;
import com.bt.dataReport.dao.WareStoreTranSportMapper;
import com.bt.dataReport.service.WareStoreTranSportService;
import com.bt.lmis.dao.WarehouseAreaMapper;

/**
 *@author jinggong.li
 *@date 2018年7月3日  
 */
@Service
public class WareStoreTranSportImpl implements WareStoreTranSportService{
   
	 @Resource
	 private WareStoreTranSportMapper wareStoreTranSportMapper;
   
   
   
	@Override
	public List<WareStoreTranSportBean> selectWare() throws Exception {
		
		return wareStoreTranSportMapper.selectWare();
	}

	@Override
	public List<WareStoreTranSportBean> selectStore() throws Exception {
		
		return wareStoreTranSportMapper.selectStore();
	}

	
	@Override
	public List<WareStoreTranSportBean> selectTranSport() throws Exception {
		
		return wareStoreTranSportMapper.selectTranSport();
	}
	
	
}
