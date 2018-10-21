package com.lmis.jbasis.contractBasicinfo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.lmis.framework.baseDao.BaseMapper;
import com.lmis.jbasis.contractBasicinfo.model.JbasisContractBasicinfoDeatil;
import com.lmis.jbasis.contractBasicinfo.model.ViewJbasisContractBasicinfoDeatil;

/** 
 * @ClassName: JbasisContractBasicinfoDeatilMapper
 * @Description: TODO(计费协议明细DAO映射接口)
 * @author codeGenerator
 * @date 2018-05-10 13:45:43
 * 
 * @param <T>
 */
@Mapper
@Repository
public interface JbasisContractBasicinfoDeatilMapper<T extends JbasisContractBasicinfoDeatil> extends BaseMapper<T> {
	
	/**
	 * @Title: retrieveViewJbasisContractBasicinfoDeatil
	 * @Description: TODO(查询view_jbasis_contract_basicinfo_deatil)
	 * @param viewJbasisContractBasicinfoDeatil
	 * @return: List<ViewJbasisContractBasicinfoDeatil>
	 * @author: codeGenerator
	 * @date: 2018-05-10 13:45:43
	 */
	List<ViewJbasisContractBasicinfoDeatil> retrieveViewJbasisContractBasicinfoDeatil(ViewJbasisContractBasicinfoDeatil viewJbasisContractBasicinfoDeatil);
	
}
