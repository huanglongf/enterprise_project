package com.lmis.pos.soldtoRule.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.lmis.framework.baseDao.BaseMapper;
import com.lmis.pos.soldtoRule.model.PosSoldtoRule;

/** 
 * @ClassName: PosSoldtoRuleMapper
 * @Description: TODO(销售平台拆单规则DAO映射接口)
 * @author codeGenerator
 * @date 2018-06-06 15:08:30
 * 
 * @param <T>
 */
@Mapper
@Repository
public interface PosSoldtoRuleMapper<T extends PosSoldtoRule> extends BaseMapper<T> {
	
	/**
	 * 不根据条件查询指定字段值
	 * @return
	 */
	public List<T> findSpecifyData();
}
