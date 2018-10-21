package com.bt.radar.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bt.radar.controller.form.AgeingDetailBackupsQueryParam;
import com.bt.radar.model.AgeingDetailBackups;

public interface AgeingDetailBackupsMapper<T> {
    int deleteByPrimaryKey(Integer id);

    int insert(AgeingDetailBackups record);

    int insertSelective(AgeingDetailBackups record);

    AgeingDetailBackups selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AgeingDetailBackups record);

    int updateByPrimaryKey(AgeingDetailBackups record);

	List<AgeingDetailBackups> selectOrderByBatId(String batId);

	List<AgeingDetailBackups> queryAgeingDetailBackups(AgeingDetailBackupsQueryParam queryParam);

	int countAllAgeingDetailBackups(AgeingDetailBackupsQueryParam queryParam);

	Integer selectCountError(@Param("batId")String batId);


}