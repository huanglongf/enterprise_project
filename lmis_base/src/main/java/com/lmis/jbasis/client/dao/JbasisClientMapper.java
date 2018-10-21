package com.lmis.jbasis.client.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.lmis.framework.baseDao.BaseMapper;
import com.lmis.jbasis.client.model.JbasisClient;
import com.lmis.jbasis.client.model.ViewJbasisClient;

/** 
 * @ClassName: JbasisClientMapper
 * @Description: TODO(客户信息(基础数据)DAO映射接口)
 * @author codeGenerator
 * @date 2018-05-16 14:41:27
 * 
 * @param <T>
 */
@Mapper
@Repository
public interface JbasisClientMapper<T extends JbasisClient> extends BaseMapper<T> {
	
	/**
	 * @Title: retrieveViewJbasisClient
	 * @Description: TODO(查询view_jbasis_client)
	 * @param viewJbasisClient
	 * @return: List<ViewJbasisClient>
	 * @author: codeGenerator
	 * @date: 2018-05-16 14:41:27
	 */
	List<ViewJbasisClient> retrieveViewJbasisClient(ViewJbasisClient viewJbasisClient);
	
}
