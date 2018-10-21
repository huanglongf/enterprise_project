package com.bt.radar.dao;

import java.util.List;

import com.bt.radar.controller.form.AgeingDetailUplodaeResultQueryParameter;
import com.bt.radar.model.AgeingDetailUplodaeResult;

public interface AgeingDetailUplodaeResultMapper<T> {
    int deleteByPrimaryKey(Integer id);

    int insert(AgeingDetailUplodaeResult record);

    int insertSelective(AgeingDetailUplodaeResult record);

    AgeingDetailUplodaeResult selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AgeingDetailUplodaeResult record);

    int updateByPrimaryKey(AgeingDetailUplodaeResult record);

	void updateByBatId(AgeingDetailUplodaeResult ageingDetailUplodaeResult);

	List<AgeingDetailUplodaeResult> queryAgeingDetailUplodaeResult(AgeingDetailUplodaeResultQueryParameter queryParam);

	int countAllAgeingDetailUplodaeResult(AgeingDetailUplodaeResultQueryParameter queryParam);
}