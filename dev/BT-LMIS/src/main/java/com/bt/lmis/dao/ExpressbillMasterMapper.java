package com.bt.lmis.dao;
import java.util.List;

import com.bt.lmis.code.BaseMapper;
import com.bt.lmis.controller.form.ExpressbillMasterQueryParam;
import com.bt.lmis.model.ExpressbillMaster;

/**
* @ClassName: ExpressbillMasterMapper
* @Description: TODO(ExpressbillMasterMapper)
* @author Yuriy.Jiang
*
* @param <T>
*/ 
public interface ExpressbillMasterMapper<T> extends BaseMapper<T> {

	List<ExpressbillMaster> findAll(ExpressbillMasterQueryParam queryParam);

	int CountMaster(ExpressbillMasterQueryParam queryParam);

	List<ExpressbillMaster> pageQuery(ExpressbillMasterQueryParam queryParam);

	void createExpressBill(ExpressbillMaster queryParam);

	void deleteById(String id);
	
	
}
