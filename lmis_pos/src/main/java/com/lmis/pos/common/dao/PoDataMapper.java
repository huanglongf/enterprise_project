package com.lmis.pos.common.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.lmis.pos.common.model.CallAbleModel;
import com.lmis.pos.common.model.PoData;


@Mapper
@Repository
public interface PoDataMapper<T extends PoData> extends BaseExcelMapper<T> {

	void callProPodataDeal(CallAbleModel callAbleModel);

}
