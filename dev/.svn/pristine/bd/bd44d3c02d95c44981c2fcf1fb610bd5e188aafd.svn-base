package com.bt.radar.dao;

import java.util.List;

import com.bt.radar.controller.form.AgeingMasterQueryParam;
import com.bt.radar.model.AgeingMaster;
import org.apache.ibatis.annotations.Param;

public interface AgeingMasterMapper<T> {
	
    int deleteByPrimaryKey(String id);

    int insert(AgeingMasterQueryParam record);

    int insertSelective(AgeingMasterQueryParam record);

    AgeingMaster selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(AgeingMasterQueryParam record);

    int updateByPrimaryKey(AgeingMasterQueryParam record);

    /**
     * 分页按条件查询总条数
     * */
	int countAllAgeingMaster(AgeingMasterQueryParam queryParam);

	/**
     *  分页按条件查询结果集
     * */
	List<AgeingMaster> queryAgeingMaster(AgeingMasterQueryParam queryParam);

	/**
     * 根据时效名称查询
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
     * 删除
     * */
	int delBatchAgeingMaster(@Param("masterIdList") List<String> masterIdList);

}