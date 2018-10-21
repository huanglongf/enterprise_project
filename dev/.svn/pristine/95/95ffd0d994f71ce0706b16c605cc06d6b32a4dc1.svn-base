package com.bt.lmis.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.bt.lmis.controller.form.WareParkQueryParam;
import com.bt.lmis.model.WarePark;

public interface WareParkMapper {
    int deleteByPrimaryKey(String id);

    int insert(WarePark record);

    int insertSelective(WarePark record);

    WareParkQueryParam selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(WarePark record);

    int updateByPrimaryKey(WarePark record);

	List<WareParkQueryParam> findAll(WareParkQueryParam queryParam);

	int countAll(WareParkQueryParam queryParam);

	Map<String, BigDecimal> checkWareParkParam(WarePark warePark);

	WareParkQueryParam getBySelective(WareParkQueryParam wareParkQueryParam);

	List<Map<String,Object>> exportList(WareParkQueryParam queryParam);
}