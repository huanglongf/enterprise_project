package com.bt.lmis.dao;
import java.util.List;

import com.bt.lmis.code.BaseMapper;
import com.bt.lmis.controller.form.ExpressReturnStorageQueryParam;
import com.bt.lmis.model.ExpressReturnStorage;

/**
* @ClassName: ExpressReturnStorageMapper
* @Description: TODO(ExpressReturnStorageMapper)
* @author Yuriy.Jiang
*
* @param <T>
*/ 
public interface ExpressReturnStorageMapper<T> extends BaseMapper<T> {

	List<T> getPageData(ExpressReturnStorageQueryParam expressReturnStorage);

	int getPageDataCount(ExpressReturnStorageQueryParam expressReturnStorage);

	/**
	 * 查询退货入库列表
	 * */
	List<ExpressReturnStorage> queryList(ExpressReturnStorageQueryParam expressReturnStorage);
	
}
