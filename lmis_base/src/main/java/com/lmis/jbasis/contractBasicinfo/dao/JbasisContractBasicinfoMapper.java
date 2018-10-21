package com.lmis.jbasis.contractBasicinfo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.lmis.framework.baseDao.BaseMapper;
import com.lmis.jbasis.contractBasicinfo.model.JbasisContractBasicinfo;
import com.lmis.jbasis.contractBasicinfo.model.ViewJbasisContractBasicinfo;

/** 
 * @ClassName: JbasisContractBasicinfoMapper
 * @Description: TODO(合同DAO映射接口)
 * @author codeGenerator
 * @date 2018-05-04 15:06:45
 * 
 * @param <T>
 */
@Mapper
@Repository
public interface JbasisContractBasicinfoMapper<T extends JbasisContractBasicinfo> extends BaseMapper<T> {
	
	/**
	 * @Title: retrieveViewJbasisContractBasicinfo
	 * @Description: TODO(查询view_jbasis_contract_basicinfo)
	 * @param viewJbasisContractBasicinfo
	 * @return: List<ViewJbasisContractBasicinfo>
	 * @author: codeGenerator
	 * @date: 2018-05-04 15:06:45
	 */
	List<ViewJbasisContractBasicinfo> retrieveViewJbasisContractBasicinfo(ViewJbasisContractBasicinfo viewJbasisContractBasicinfo);
	
}
