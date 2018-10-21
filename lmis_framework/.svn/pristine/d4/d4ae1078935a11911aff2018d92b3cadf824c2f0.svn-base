package com.lmis.common.dynamicSql.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/** 
 * @ClassName: DynamicSqlMapper
 * @Description: TODO(动态SQL映射接口)
 * @author Ian.Huang
 * @date 2018年1月3日 上午10:15:29 
 * 
 * @param <T>
 */
@Mapper
@Repository
public interface DynamicSqlMapper<T> {

	/**
	 * @Title: executeSelect
	 * @Description: TODO(执行查询SQL)
	 * @param sql
	 * @return: List<T>
	 * @author: Ian.Huang
	 * @date: 2018年1月3日 上午10:16:48
	 */
	List<T> executeSelect(@Param("sql") String sql);
	
	/**
	 * @Title: executeInsert
	 * @Description: TODO(执行插入SQL)
	 * @param sql
	 * @return: Integer
	 * @author: Ian.Huang
	 * @date: 2018年1月3日 上午10:17:25
	 */
	Integer executeInsert(@Param("sql") String sql);
	
	/**
	 * @Title: executeUpdate
	 * @Description: TODO(执行更新SQL)
	 * @param sql
	 * @return: Integer
	 * @author: Ian.Huang
	 * @date: 2018年1月3日 上午10:17:40
	 */
	Integer executeUpdate(@Param("sql") String sql);
	
	/**
	 * @Title: executeDelete
	 * @Description: TODO(执行删除SQL)
	 * @param sql
	 * @return: Integer
	 * @author: Ian.Huang
	 * @date: 2018年1月3日 上午10:17:59
	 */
	Integer executeDelete(@Param("sql") String sql);
	
	Map<String,Object> findColumns(Map<String,String> map);
	
}
