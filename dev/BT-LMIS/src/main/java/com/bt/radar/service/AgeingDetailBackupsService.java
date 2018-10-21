package com.bt.radar.service;

import java.util.List;

import com.bt.lmis.page.QueryResult;
import com.bt.radar.controller.form.AgeingDetailBackupsQueryParam;
import com.bt.radar.model.AgeingDetailBackups;

public interface AgeingDetailBackupsService<T> {

	void insertList(List<AgeingDetailBackups> list1);

	List<AgeingDetailBackups> selectOrderByBatId(String batId);

	void updateByPrimaryKeySelective(AgeingDetailBackups ageingDetailBackups2);

	QueryResult<AgeingDetailBackups> queryAgeingDetailBackups(AgeingDetailBackupsQueryParam queryParam);

	Integer selectCountError(String batId);

	

}
