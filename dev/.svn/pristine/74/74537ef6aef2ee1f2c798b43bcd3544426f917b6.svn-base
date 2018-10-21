package com.bt.lmis.dao;

import java.util.List;
import java.util.Map;

import com.bt.lmis.controller.form.WareRelationQueryParam;
import com.bt.lmis.model.WareRelation;

public interface WareRelationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WareRelation record);

    int insertSelective(WareRelation record);

    WareRelation selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WareRelation record);

    int updateByPrimaryKey(WareRelation record);

	List<WareRelationQueryParam> findAll(WareRelationQueryParam queryParam);

	int countAll(WareRelationQueryParam queryParam);

	Map<String, Long> checkWareRelationParam(WareRelation wareRelation);
}