package com.bt.radar.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.radar.controller.form.AgeingRecieveQueryParam;
import com.bt.radar.dao.AgeingRecieveMapper;
import com.bt.radar.model.AgeingRecieve;
import com.bt.radar.service.AgeingRecieveService;

@Service
public class AgeingRecieveServiceImpl<T> implements AgeingRecieveService<T> {

	@Autowired
	private AgeingRecieveMapper<AgeingRecieve> ageingRecieveMapper;
	
	public AgeingRecieveMapper<AgeingRecieve> getAgeingRecieveMapper(){
		return ageingRecieveMapper;
	}
	
}
