package com.lmis.sys.codeRule.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.lmis.framework.baseDao.BaseMapper;
import com.lmis.sys.codeRule.model.SysCoderuleData;
import com.lmis.sys.codeRule.model.ViewSysCoderuleData;

/** 
 * @ClassName: SysCoderuleDataMapper
 * @Description: TODO(DAO映射接口)
 * @author codeGenerator
 * @date 2018-05-22 09:01:05
 * 
 * @param <T>
 */
@Mapper
@Repository
public interface SysCoderuleDataMapper<T extends SysCoderuleData> extends BaseMapper<T> {
	
	/**
	 * @Title: retrieveViewSysCoderuleData
	 * @Description: TODO(查询view_sys_coderule_data)
	 * @param viewSysCoderuleData
	 * @return: List<ViewSysCoderuleData>
	 * @author: codeGenerator
	 * @date: 2018-05-22 09:01:05
	 */
	List<ViewSysCoderuleData> retrieveViewSysCoderuleData(ViewSysCoderuleData viewSysCoderuleData);
	
	
	/**
     * 获取当前configcode已经存储的数据个数
     * @param configcode
     * @return
     */
    int getDataCountByConfigCode(@Param("configcode") String configcode);
    
    
}
