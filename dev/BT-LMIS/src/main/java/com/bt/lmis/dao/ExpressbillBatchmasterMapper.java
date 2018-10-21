package com.bt.lmis.dao;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bt.lmis.code.BaseMapper;
import com.bt.lmis.controller.form.ExpressbillBatchmasterQueryParam;
import com.bt.lmis.model.ExpressbillBatchmaster;

/**
* @ClassName: ExpressbillBatchmasterMapper
* @Description: TODO(ExpressbillBatchmasterMapper)
* @author Yuriy.Jiang
*
* @param <T>
*/ 
public interface ExpressbillBatchmasterMapper<T> extends BaseMapper<T> {

	void deleteByMaster_id(@Param("master_id")String id);

	List<ExpressbillBatchmaster> selectExpressBillBatch(ExpressbillBatchmasterQueryParam queryParam);

	int Count(ExpressbillBatchmasterQueryParam queryParam);

	void update(ExpressbillBatchmaster expressbillBatchmaster);

	void insertDetailTemp(List<String[]> sflist);

	void checkTemp(ExpressbillBatchmasterQueryParam qureyParam);

	void insertDetail(ExpressbillBatchmasterQueryParam qureyParam);

	int selectSuccess(ExpressbillBatchmasterQueryParam qureyParam);

	int selectFail(ExpressbillBatchmasterQueryParam qureyParam);

	
}
