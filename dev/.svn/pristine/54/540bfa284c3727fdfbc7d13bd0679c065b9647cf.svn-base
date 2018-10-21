package com.bt.orderPlatform.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bt.orderPlatform.controller.form.ExpressinfoMasterInputlistQueryParam;
import com.bt.orderPlatform.model.ExpressinfoMasterInputlist;

/**
* @ClassName: ExpressinfoMasterInputlistMapper
* @Description: TODO(ExpressinfoMasterInputlistMapper)
* @author Yuriy.Jiang
*
* @param <T>
*/ 
public interface ExpressinfoMasterInputlistMapper<T> {

	List<Map<String, Object>> selectAllExpressinfoMasterInputlist(ExpressinfoMasterInputlistQueryParam queryParam);

	int countAllExpressinfoMasterInputlist(ExpressinfoMasterInputlistQueryParam queryParam);

	void insertExpressinfoMasterInputlist(ExpressinfoMasterInputlist expressinfoMasterInputlist);

	void deletetByBatId(@Param("bat_id")String id);

	List<ExpressinfoMasterInputlist> selectByBatId(@Param("bat_id")String id);

	void updateByBatId(@Param("bat_id")String id, @Param("flag")Integer flag);

	void updateByBatIdAndSuccess(@Param("bat_id")String id, @Param("total_num")Integer total_num);

	
}
