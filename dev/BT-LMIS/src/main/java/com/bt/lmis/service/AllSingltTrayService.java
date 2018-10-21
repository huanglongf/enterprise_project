package com.bt.lmis.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bt.lmis.code.BaseService;

public interface AllSingltTrayService<T> extends BaseService<T> {

	public List<Map<String, Object>> findByCBID(@Param("cbid")String id);
}
