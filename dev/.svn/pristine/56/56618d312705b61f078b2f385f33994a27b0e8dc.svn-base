package com.bt.radar.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.lmis.page.QueryResult;
import com.bt.radar.controller.form.AgeingDetailUplodaeResultQueryParameter;
import com.bt.radar.dao.AgeingDetailUplodaeResultMapper;
import com.bt.radar.model.AgeingDetailUpload;
import com.bt.radar.model.AgeingDetailUplodaeResult;
import com.bt.radar.service.AgeingDetailUplodaeResultService;

/** 
	* @Description: 
	* @author  Hanery:
 	* @date 2018年1月15日 下午6:12:06  
*/
@Service
public class AgeingDetailUplodaeResultServiceImpl<T> implements AgeingDetailUplodaeResultService<T> {

	@Autowired
	private AgeingDetailUplodaeResultMapper<AgeingDetailUplodaeResult> ageingDetailUplodaeResultMapper;
	
	public AgeingDetailUplodaeResultMapper<AgeingDetailUplodaeResult> getAgeingDetailUplodaeResultMapper(){
		return ageingDetailUplodaeResultMapper;
	}
	
	@Override
	public void insert(AgeingDetailUplodaeResult ageingDetailUplodaeResult) {
		// TODO Auto-generated method stub
		ageingDetailUplodaeResultMapper.insert(ageingDetailUplodaeResult);
	}

	@Override
	public void updateByPrimaryKeySelective(AgeingDetailUplodaeResult ageingDetailUplodaeResult) {
		// TODO Auto-generated method stub
		ageingDetailUplodaeResultMapper.updateByPrimaryKeySelective(ageingDetailUplodaeResult);
	}

	@Override
	public void updateByBatId(AgeingDetailUplodaeResult ageingDetailUplodaeResult) {
		// TODO Auto-generated method stub
		ageingDetailUplodaeResultMapper.updateByBatId(ageingDetailUplodaeResult);
	}

	@Override
	public QueryResult<AgeingDetailUplodaeResult> queryAgeingDetailUplodaeResult(
			AgeingDetailUplodaeResultQueryParameter queryParam) {
		QueryResult<AgeingDetailUplodaeResult> qr = new QueryResult<AgeingDetailUplodaeResult>();
		qr.setResultlist((List<AgeingDetailUplodaeResult>) ageingDetailUplodaeResultMapper.queryAgeingDetailUplodaeResult(queryParam));
		qr.setTotalrecord(ageingDetailUplodaeResultMapper.countAllAgeingDetailUplodaeResult(queryParam));
		return qr;
	}

}
