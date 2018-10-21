package com.lmis.setup.constant.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.lmis.framework.baseDao.BaseMapper;
import com.lmis.setup.constant.model.SetupConstant;

/**
 * @author daikaihua
 * @date 2017年11月28日
 * @todo TODO
 */
@Mapper
@Repository
public interface SetupConstantMapper<T extends SetupConstant> extends BaseMapper<T> {

}
