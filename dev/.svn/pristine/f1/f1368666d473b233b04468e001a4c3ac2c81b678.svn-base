package com.bt.lmis.dao;
import java.util.List;
import java.util.Map;

import com.bt.lmis.code.BaseMapper;

/**
* @ClassName: CollectionMasterMapper
* @Description: TODO(CollectionMasterMapper)
* @author Yuriy.Jiang
*
* @param <T>
*/ 
public interface CollectionMasterMapper<T> extends BaseMapper<T> {

	/**
	 * @param contract_owner
	 * @param account_id
	 * @param table_name
	 * @return
	 */
	List<Map<String,Object>> getMaster(Map<String, Object> map);

	/**
	 * @param detailParam
	 * @return
	 */
	List<Map<String, Object>> getDetail(Map<String, Object> detailParam);

	/**
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> getDetailSF(Map<String, Object> map);
	
	
}
