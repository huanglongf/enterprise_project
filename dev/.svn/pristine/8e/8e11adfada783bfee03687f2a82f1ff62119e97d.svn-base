package com.bt.lmis.dao;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bt.lmis.code.BaseMapper;
import com.bt.lmis.model.Ain;

/**
* @ClassName: AinMapper
* @Description: TODO(AinMapper)
* @author Yuriy.Jiang
*
* @param <T>
*/ 
public interface AinMapper<T> extends BaseMapper<T> {
	public List<Ain> findByCBID(@Param("cbid")String cbid);

	public List<Ain> findByCBIDSE(Ain ain);
}
