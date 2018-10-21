package com.bt.radar.service;

import com.bt.common.controller.result.Result;
import com.bt.lmis.page.QueryResult;
import com.bt.radar.controller.form.AgeingMasterQueryParam;
import com.bt.radar.model.AgeingMaster;

public interface AgeingMasterService<T> {

	/**
	 * 分页查询
	 * */
	QueryResult<AgeingMaster> queryAgeingMaster(AgeingMasterQueryParam queryParam);

	/**
	 * 根据name查询
	 * */
	AgeingMaster selectByAgeingName(String ageingName);

	/**
	 * 插入
	 * */
	void insertAgeingMaster(AgeingMasterQueryParam queryParam);

	/**
	 * 根据主键查询
	 * */
	AgeingMaster queryAgeingMasterById(String id);

	/**
	 * 更新信息
	 * */
	void updateAgeingMaster(AgeingMasterQueryParam queryParam);

	/**
	 * 批量删除时效信息
	 * */
	Result delAgeingMaster(AgeingMasterQueryParam queryParam) throws Exception;

	/**
	 * 更新启用禁用信息
	 * */
	Result upStatus(AgeingMasterQueryParam queryParam);
	

}
