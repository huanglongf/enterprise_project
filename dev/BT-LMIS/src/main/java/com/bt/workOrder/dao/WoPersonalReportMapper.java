package com.bt.workOrder.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bt.common.controller.param.Parameter;
import com.bt.workOrder.bean.WoPersonalReport;

public interface WoPersonalReportMapper {

    int insert(WoPersonalReport record);

    WoPersonalReport selectByPrimaryKey(Integer id);

    List<WoPersonalReport> selectAll();

	/**
	 * @Param("woNo")
	 */
	List<Map<String, Object>> getProcessPG(String date);

	List<Map<String, Object>> getRecievePG(String date);
	
	int getGroupCount(String group);

	List<Map<String,Object>> getWoCount(@Param("date")String date, @Param("person")String person,@Param("group") String group,@Param("action") String action,@Param("type")int type);

	int getPersonalMultipleProcessingNum(@Param("date")String date, @Param("person")String person, @Param("group") String group);

	int getPersonalUnprocessNum(@Param("person")String person,@Param("group")String group);

	Map<String, Object> getNewLog(@Param("woId")String woId, @Param("createTime")String createTime);

	List<Map<String, Object>> search(Parameter parameter);

	int countSearch(Parameter parameter);

	List<Map<String, Object>> exportReport(Parameter parameter);

    void deleteByReportTime(@Param("date")String date);
}