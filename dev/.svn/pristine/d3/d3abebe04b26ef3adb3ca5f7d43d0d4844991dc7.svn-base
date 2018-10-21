package com.bt.radar.service.impl;



import com.bt.lmis.page.QueryResult;
import com.bt.radar.controller.form.AgeingDetailUploadQueryParam;
import com.bt.radar.dao.AgeingDetailUploadMapper;
import com.bt.radar.model.AgeingDetail;
import com.bt.radar.model.AgeingDetailUpload;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.radar.service.AgeingDetailUploadService;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AgeingDetailUploadServiceImpl<T> implements AgeingDetailUploadService<T> {

	@Autowired
	private AgeingDetailUploadMapper<AgeingDetailUpload> ageingDetailUploadMapper;

	
	public AgeingDetailUploadMapper<AgeingDetailUpload> getAgeingDetailUploadMapper(){
		return ageingDetailUploadMapper;
	}


	@Override
	public void insert(AgeingDetailUpload ageingDetailUpload) {
		// TODO Auto-generated method stub
		ageingDetailUploadMapper.insert(ageingDetailUpload);
	}


	@Override
	public QueryResult<AgeingDetailUpload> queryAgeingDetailUpload(AgeingDetailUploadQueryParam queryParam) {
		// TODO Auto-generated method stub
		QueryResult<AgeingDetailUpload> qr = new QueryResult<AgeingDetailUpload>();
		qr.setResultlist((List<AgeingDetailUpload>) ageingDetailUploadMapper.queryAgeingDetailUpload(queryParam));
		qr.setTotalrecord(ageingDetailUploadMapper.countAllAgeingDetailUpload(queryParam));
		return qr;
	}





}
