package com.bt.radar.dao;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bt.lmis.code.BaseMapper;
import com.bt.radar.controller.form.ExpressinfoMasterInputlistQueryParam;
import com.bt.radar.model.ExpressinfoMasterInputlist;

/**
* @ClassName: ExpressinfoMasterInputlistMapper
* @Description: TODO(ExpressinfoMasterInputlistMapper)
* @author Yuriy.Jiang
*
* @param <T>
*/ 
public interface ExpressinfoMasterInputlistMapper<T> extends BaseMapper<T> {

	List<ExpressinfoMasterInputlist> findAll(ExpressinfoMasterInputlistQueryParam queryParam);

	int getCount(ExpressinfoMasterInputlistQueryParam queryParam);

	int count(ExpressinfoMasterInputlistQueryParam queryParam);

	void updateMaster(Map param);

	int checkDel(List<String> list);

	void del(List<String> list);


	
}
