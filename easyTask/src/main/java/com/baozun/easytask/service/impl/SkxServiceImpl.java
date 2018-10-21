package com.baozun.easytask.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baozun.easytask.mapper.SkxMapper;
import com.baozun.easytask.service.SkxService;

@Service
public class SkxServiceImpl implements SkxService{

	@Autowired
    private SkxMapper mapper;
	
	@Override
	public List<Map<String, Object>> job1(String varStra, String varStrb) {
		return mapper.job1(varStra, varStrb);
	}

	@Override
	public List<Map<String, Object>> job2(String varStra, String varStrb) {
		return mapper.job2(varStra, varStrb);
	}

	@Override
	public List<Map<String, Object>> job3(String varStra, String varStrb) {
		return mapper.job3(varStra, varStrb);
	}

	@Override
	public List<Map<String, Object>> job4(String varStra, String varStrb) {
		return mapper.job4(varStra, varStrb);
	}

	@Override
	public List<Map<String, Object>> job5(String varStra, String varStrb) {
		return mapper.job5(varStra, varStrb);
	}

	@Override
	public List<Map<String, Object>> job6(String varStra, String varStrb) {
		return mapper.job6(varStra, varStrb);
	}

	@Override
	public List<Map<String, Object>> job7() {
		return mapper.job7();
	}

	@Override
	public List<Map<String, Object>> job8() {
		return mapper.job8();
	}

	@Override
	public List<Map<String, Object>> job9() {
		return mapper.job9();
	}

	
	@Override
	public List<Map<String, Object>> job11() {
		
		return mapper.job11();
	}

}
