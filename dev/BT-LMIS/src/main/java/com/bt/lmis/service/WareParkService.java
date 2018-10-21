package com.bt.lmis.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.bt.lmis.controller.form.WareParkQueryParam;
import com.bt.lmis.model.WarePark;
import com.bt.lmis.page.QueryResult;

public interface WareParkService {
    int deleteByPrimaryKey(String id);

    int insert(WarePark record);

    int insertSelective(WarePark record);

    WareParkQueryParam selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(WarePark record);

    int updateByPrimaryKey(WarePark record);

	QueryResult<WareParkQueryParam> findAll(WareParkQueryParam queryParam);

	Map<String, BigDecimal> checkWareParkParam(WarePark warePark);

	int delByIds(String ids);

	List<Map<String,Object>> exportList(WareParkQueryParam queryParam);
}