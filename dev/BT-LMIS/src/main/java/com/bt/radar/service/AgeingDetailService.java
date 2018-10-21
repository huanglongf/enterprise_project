package com.bt.radar.service;

import java.util.List;
import java.util.Map;

import com.bt.common.controller.result.Result;
import com.bt.lmis.page.QueryResult;
import com.bt.radar.controller.form.AgeingDetailQueryParam;
import com.bt.radar.model.AgeingDetail;
import com.bt.radar.model.AgeingDetailBackups;

public interface AgeingDetailService<T> {

	QueryResult<AgeingDetail> queryAgeingDetail(AgeingDetailQueryParam queryParam);

	/**
	 * 批量删除
	 * */
	Result delAgeingDetail(AgeingDetailQueryParam queryParam);

	void insertList(List<AgeingDetailBackups> listageingDetailBackups);

	AgeingDetail selectByAgeingDetailBackups(AgeingDetailBackups ageingDetailBackups2);

	int insert(AgeingDetailQueryParam queryParam);

	/**
	 * 当id不为空时根据id查询非当前id下明细信息相符的明细信息
	 * 当id为空时直接查询相符的明细信息
	 */
	AgeingDetail selectByAgeingDetailQueryParam(AgeingDetailQueryParam queryParam);

	AgeingDetail selectByPrimaryKey(int id);

	int update(AgeingDetailQueryParam queryParam);

	List<Map<String, Object>> selectAgeingDetailQueryParam(AgeingDetailQueryParam queryParam);

	void uploadAgeingDetail(List<String[]> list, AgeingDetailQueryParam queryParam,String batId);



}
