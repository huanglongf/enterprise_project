package com.bt.workOrder.service;

import java.util.List;
import java.util.Map;

import com.bt.common.controller.param.Parameter;
import com.bt.lmis.page.QueryResult;
import com.bt.workOrder.bean.WoPersonalReport;

public interface WoPersonalReportService {

    int insert(WoPersonalReport record);

    WoPersonalReport selectByPrimaryKey(Integer id);

    List<WoPersonalReport> selectAll();

	/**
	 * @param date
	 * @return
	 */
	List<Map<String, Object>> getProcessPG(String date);

	/**
	 * @param date
	 * @return
	 */
	List<Map<String, Object>> getRecievePG(String date);

	/**
	 * @param group
	 * @return
	 */
	int getGroupCount(String group);

	/**
	 * @param date
	 * @param person
	 * @param group
	 * @param string
	 * @return
	 */
	List<Map<String, Object>> getWoCount(String date, String person, String group, String string,int type);

	/**
	 * @param date
	 * @param person
	 * @param group
	 * @return
	 */
	int getPersonalMultipleProcessingNum(String date, String person, String group);

	/**
	 * @param person
	 * @param group
	 * @return
	 */
	int getPersonalUnprocessNum(String person, String group);

	/**
	 * @param person
	 * @param group
	 * @param woId
	 * @param createTime
	 * @return
	 */
	Map<String, Object> getNewLog(String woId, String createTime);

	/**
	 * @param parameter
	 * @return
	 */
	QueryResult<Map<String, Object>> search(Parameter parameter);

	/**
	 * @param parameter
	 * @return
	 */
	List<Map<String, Object>> exportReport(Parameter parameter);

    /**
     * @param date
     */
    void deleteByReportTime(String date);
}