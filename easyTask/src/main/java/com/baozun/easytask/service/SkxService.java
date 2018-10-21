package com.baozun.easytask.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface SkxService {

	public List<Map<String, Object>> job1(@Param("varStra")String varStra,@Param("varStrb")String varStrb);
	public List<Map<String, Object>> job2(@Param("varStra")String varStra,@Param("varStrb")String varStrb);
	public List<Map<String, Object>> job3(@Param("varStra")String varStra,@Param("varStrb")String varStrb);
	public List<Map<String, Object>> job4(@Param("varStra")String varStra,@Param("varStrb")String varStrb);
	public List<Map<String, Object>> job5(@Param("varStra")String varStra,@Param("varStrb")String varStrb);
	public List<Map<String, Object>> job6(@Param("varStra")String varStra,@Param("varStrb")String varStrb);
	public List<Map<String, Object>>        job7();
	public List<Map<String, Object>>        job8();
	public List<Map<String, Object>>        job9();
	public List<Map<String, Object>>        job11();
	
}
