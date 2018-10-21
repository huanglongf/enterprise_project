package com.bt.lmis.code;

import java.io.Serializable;

/**
 * <p>服务支持<p>
* @ClassName: ServiceSupport
* @Description: TODO(为服务组件的基类，必须继承)
* @author Yuriy.Jiang
* @date 2016年5月22日 上午9:30:33
*
* @param <T>  该服务组件服务的数据模型，即model;
*/ 
public abstract class ServiceSupport<T> implements BaseService<T> {

	private BaseMapper<T> mapper;
	
	public BaseMapper<T> getMapper() throws Exception{
		return mapper;
	}

	@Override
	public int save(T entity) throws Exception {
		return getMapper().insert(entity);
	}

	@Override
	public void update(T entity) throws Exception{
		getMapper().update(entity);
	}
	

	@Override
	public void delete(Serializable id) throws Exception {
		getMapper().delete(id);
	}

	@Override
	public void batchDelete(Integer[] ids) throws Exception {
		getMapper().batchDelete(ids);		
	}
	
	@Override
	public T selectById(Integer id) throws Exception {
		return getMapper().selectById(id);		
	}
}
