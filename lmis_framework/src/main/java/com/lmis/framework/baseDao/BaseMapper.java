package com.lmis.framework.baseDao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.lmis.framework.baseModel.PersistentObject;

/** 
 * @ClassName: BaseMapper
 * @Description: TODO(基础映射接口crud)
 * @author Ian.Huang
 * @date 2017年10月8日 下午4:54:11 
 * 
 * @param <T>
 */
@Mapper
public interface BaseMapper<T extends PersistentObject> {
	
	/**
	 * @Title: create
	 * @Description: TODO(create new record)
	 * @param t
	 * @return: int
	 * @author: Ian.Huang
	 * @date: 2017年10月7日 下午3:59:31
	 */
	int create(T t);
	
	/**
	 * @Title: createBatch
	 * @Description: TODO(create new records)
	 * @param ts
	 * @return: int
	 * @author: Ian.Huang
	 * @date: 2017年10月7日 下午5:31:29
	 */
	int createBatch(List<T> ts);
	
	/**
	 * @Title: retrieve
	 * @Description: TODO(retrieve existing record)
	 * @param t
	 * @return: List<T>
	 * @author: Ian.Huang
	 * @date: 2017年10月7日 下午3:59:53
	 */
	List<T> retrieve(T t);
	
	/**
	 * @Title: update
	 * @Description: TODO(update existing record)
	 * @param t
	 * @return: int
	 * @author: Ian.Huang
	 * @date: 2017年10月7日 下午4:00:10
	 */
	int update(T t);
	
	/**
	 * @Title: updateRecord
	 * @Description: TODO(页面更新记录)
	 * @param t
	 * @return: int
	 * @author: Ian.Huang
	 * @date: 2017年12月21日 下午6:53:31
	 */
	int updateRecord(T t);
	
	/**
	 * @Title: logicalDelete
	 * @Description: TODO(逻辑删除)
	 * @param t
	 * @return: int
	 * @author: Ian.Huang
	 * @date: 2017年12月21日 下午6:55:29
	 */
	int logicalDelete(T t);
	
	/**
	 * @Title: shiftValidity
	 * @Description: TODO(切换有效性)
	 * @param t
	 * @return: int
	 * @author: Ian.Huang
	 * @date: 2017年12月21日 下午6:59:01
	 */
	int shiftValidity(T t);
	
	/**
	 * @Title: delete
	 * @Description: TODO(delete existing record)
	 * @param object
	 * @return: int
	 * @author: Ian.Huang
	 * @date: 2017年10月7日 下午4:00:24
	 */
	int delete(T object);
	
}
