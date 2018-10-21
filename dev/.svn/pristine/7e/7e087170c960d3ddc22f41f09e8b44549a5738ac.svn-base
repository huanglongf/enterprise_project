package com.bt.lmis.dao;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bt.lmis.code.BaseMapper;
import com.bt.lmis.controller.form.DiffBilldeatilsQueryParam;
import com.bt.lmis.controller.form.ExpressbillDetailQueryParam;

/**
* @ClassName: ExpressbillDetailMapper
* @Description: TODO(ExpressbillDetailMapper)
* @author Yuriy.Jiang
*
* @param <T>
*/ 
public interface ExpressbillDetailMapper<T> extends BaseMapper<T> {

	void deleteByMaster_id(@Param("master_id")String id);

	void deleteByBat_id(@Param("bat_id")String batch_id);

	int  Count(ExpressbillDetailQueryParam param);

	 List<T> findAll (ExpressbillDetailQueryParam param);

	Map<String,Object> getIdByBatId(@Param("bat_id")String batch_id);

	void toClear();

	List checkToDiff(ExpressbillDetailQueryParam query);

	void clearDiff(@Param("bat_id")String bat_id);

	void deleteVerification(ExpressbillDetailQueryParam query);
	
}
