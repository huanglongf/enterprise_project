package com.bt.radar.dao;

import java.util.List;

import com.bt.radar.controller.form.AgeingDetailUploadQueryParam;
import com.bt.radar.model.AgeingDetailUpload;

public interface AgeingDetailUploadMapper<T> {
    int deleteByPrimaryKey(Integer id);

    int insert(AgeingDetailUpload record);

    int insertSelective(AgeingDetailUpload record);

    AgeingDetailUpload selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AgeingDetailUpload record);

    int updateByPrimaryKey(AgeingDetailUpload record);

	List<AgeingDetailUpload> queryAgeingDetailUpload(AgeingDetailUploadQueryParam queryParam);

	int countAllAgeingDetailUpload(AgeingDetailUploadQueryParam queryParam);

	Integer selectCountError();
}