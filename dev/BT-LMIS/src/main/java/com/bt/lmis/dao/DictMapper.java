package com.bt.lmis.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bt.lmis.model.Dict;

public interface DictMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Dict record);

    int insertSelective(Dict record);

    Dict selectByPrimaryKey(Integer id);
    
    List<Dict> findByType(String dictType);

    int updateByPrimaryKeySelective(Dict record);

    int updateByPrimaryKey(Dict record);

	String findByTypeValue(@Param("dictType")String dictType, @Param("dictValue")String dictValue);
}