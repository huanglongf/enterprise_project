package com.bt.lmis.code;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
* @ClassName: BaseService
* @Description: TODO(服务接口的基类)
* @author Yuriy.Jiang
* @date 2016年5月22日 上午9:22:43
*
* @param <T>此服务接口服务的数据模型，即model
*/ 
public interface BaseService<T> {
	/**
	 * 保存实体
	 * @param entity 欲保存的实体
	 * @throws Exception
	 */
	int save(T entity) throws Exception;
	
	/**
	 * 更新实体
	 * @param entity 实体id
	 */
	void update(T entity) throws Exception;
	
	
	/**
	 * 删除实体
	 * @param id
	 * @throws Exception
	 */
	void delete(Serializable id) throws Exception;
	
	/**
	 * 批量删除实体
	 * @param ids
	 * @throws Exception
	 */
	void batchDelete(Integer[]ids) throws Exception;
	

	/**
	 * 依据id查询记录
	 * @param id
	 * @return
	 * @throws Exception
	 */
	T selectById(Integer id) throws Exception;
	
	Long selectCount(Map<String, Object> param) throws Exception;

}
