package com.bt.lmis.service;

import java.util.Map;

import com.bt.lmis.controller.form.WareRelationQueryParam;
import com.bt.lmis.model.WareRelation;
import com.bt.lmis.page.QueryResult;

public interface WareRelationService {

    int deleteByPrimaryKey(Integer id);

    int insert(WareRelation record);

    int insertSelective(WareRelation record);

    WareRelation selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WareRelation record);

    int updateByPrimaryKey(WareRelation record);

	QueryResult<WareRelationQueryParam> findAll(WareRelationQueryParam queryParam);

	Map<String, Long> checkWareRelationParam(WareRelation wareRelation);

	int delByIds(String ids);
    
}
