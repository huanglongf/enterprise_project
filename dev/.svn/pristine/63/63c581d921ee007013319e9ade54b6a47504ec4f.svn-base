package com.bt.lmis.service;

import java.util.List;

import com.bt.lmis.model.Dict;

public interface DictService {
	int deleteByPrimaryKey(Integer id);

    int insert(Dict record);

    int insertSelective(Dict record);

    Dict selectByPrimaryKey(Integer id);
    
    List<Dict> findByType(String dictType);

    int updateByPrimaryKeySelective(Dict record);

    int updateByPrimaryKey(Dict record);

	String findByTypeValue(String dictType, String dictValue);
}
