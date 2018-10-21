package com.lmis.setup.constantSql.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.lmis.framework.baseDao.BaseMapper;
import com.lmis.setup.constantSql.model.SetupConstantSql;

/** 
 * @ClassName: SetupConstantSqlMapper
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Ian.Huang
 * @date 2017年12月8日 下午5:38:12 
 * 
 * @param <T>
 */
@Mapper
@Repository
public interface SetupConstantSqlMapper<T extends SetupConstantSql> extends BaseMapper<T> {

}
