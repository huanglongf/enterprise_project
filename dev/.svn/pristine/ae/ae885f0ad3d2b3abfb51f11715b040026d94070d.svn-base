package com.bt.radar.service.impl;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.lmis.page.QueryResult;
import com.bt.radar.controller.form.AgeingDetailBackupsQueryParam;
import com.bt.radar.dao.AgeingDetailBackupsMapper;
import com.bt.radar.model.AgeingDetailBackups;
import com.bt.radar.model.AgeingDetailUpload;
import com.bt.radar.service.AgeingDetailBackupsService;

@Service
public class AgeingDetailBackupsServiceImpl<T> implements AgeingDetailBackupsService<T> {

	@Autowired
	private AgeingDetailBackupsMapper<AgeingDetailBackups> ageingDetailBackupsMapper;
	
	public AgeingDetailBackupsMapper<AgeingDetailBackups> getAgeingDetailBackupsMapper(){
		return ageingDetailBackupsMapper;
	}

	@Override
	public void insertList(List<AgeingDetailBackups> list1) {
		// TODO Auto-generated method stub
		for (AgeingDetailBackups ageingDetailBackups : list1) {
			ageingDetailBackupsMapper.insert(ageingDetailBackups);
		}
	}

	@Override
	public List<AgeingDetailBackups> selectOrderByBatId(String batId) {
		// TODO Auto-generated method stub
		return ageingDetailBackupsMapper.selectOrderByBatId(batId);
	}

	@Override
	public void updateByPrimaryKeySelective(AgeingDetailBackups ageingDetailBackups2) {
		// TODO Auto-generated method stub
		ageingDetailBackupsMapper.updateByPrimaryKeySelective(ageingDetailBackups2);
	}

	@Override
	public QueryResult<AgeingDetailBackups> queryAgeingDetailBackups(AgeingDetailBackupsQueryParam queryParam) {
		// TODO Auto-generated method stub
		QueryResult<AgeingDetailBackups> qr = new QueryResult<AgeingDetailBackups>();
		qr.setResultlist((List<AgeingDetailBackups>) ageingDetailBackupsMapper.queryAgeingDetailBackups(queryParam));
		qr.setTotalrecord(ageingDetailBackupsMapper.countAllAgeingDetailBackups(queryParam));
		return qr;
	}

	@Override
	public Integer selectCountError(String batId) {
		// TODO Auto-generated method stub
		return ageingDetailBackupsMapper.selectCountError(batId);
	}

	
}
