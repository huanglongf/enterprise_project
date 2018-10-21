package com.bt.lmis.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bt.lmis.code.BaseService;
import com.bt.lmis.model.Ain;

public interface AinService<T> extends BaseService<T> {
	public List<Ain> findByCBID(@Param("cbid")String cbid);
}
