package com.bt.lmis.dao;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bt.lmis.code.BaseMapper;
import com.bt.lmis.controller.form.ExpressbillMasterhxQueryParam;
import com.bt.lmis.model.ExpressbillMasterhx;

/**
* @ClassName: ExpressbillMasterhxMapper
* @Description: TODO(ExpressbillMasterhxMapper)
* @author Yuriy.Jiang
*
* @param <T>
*/ 
public interface ExpressbillMasterhxMapper<T> extends BaseMapper<T> {

	void updateById(@Param("id")String id, @Param("file_path")String file_path);
	
	void insetObj(ExpressbillMasterhx ex);

	List<ExpressbillMasterhx> findAll(ExpressbillMasterhxQueryParam queryParam);

	int countNum(ExpressbillMasterhxQueryParam queryParam);

	void updateByIdClose(@Param("id")String id,@Param("user")String user);

	void deleteID(@Param("id")String id);

	ExpressbillMasterhx selectByExpressbillMasterhxId(@Param("id")String id);

}
