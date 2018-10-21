package com.bt.lmis.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bt.lmis.code.BaseMapper;
import com.bt.lmis.model.InsuranceEC;
import com.bt.lmis.model.InsuranceRedisBean;

/**
 * @Title:InsuranceFeeRuleMapper(保价费规则DAO)
 * @Description: TODO  
 * @author Ian.Huang 
 * @date 2016年6月29日下午5:39:11
 */
public interface InsuranceECMapper<T> extends BaseMapper<T> {
	
	/**
	 * 
	 * @Description: TODO
	 * @param redis_key
	 * @return: List<InsuranceEC>  
	 * @author Ian.Huang 
	 * @date 2016年11月30日下午7:55:35
	 */
	public List<InsuranceEC> get(@Param("redis_key")String redis_key);
	
	/**
	 * 
	 * @Description: TODO(加载保价费规则)
	 * @return
	 * @return: List<InsuranceRedisBean>  
	 * @author Ian.Huang 
	 * @date 2016年11月29日下午1:31:27
	 */
	public List<InsuranceRedisBean> loadInsurance();
	
	/**
	 * 
	 * @Description: TODO(查询对应产品类型下规则)
	 * @param param
	 * @return
	 * @return: InsuranceEC  
	 * @author Ian.Huang 
	 * @date 2016年7月18日下午3:24:22
	 */
	public List<InsuranceEC> findInsuranceByProType(Map<String, Object> param);
	
	/**
	 * 
	 * @Description: TODO
	 * @param con_id
	 * @param belong_to
	 * @return
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2016年10月10日下午3:35:13
	 */
	public Integer deleteIEC(@Param("con_id")int con_id, @Param("belong_to")String belong_to);
	
	/**
	 * 
	 * @Description: TODO(删除一条保价费规则)
	 * @param id
	 * @return
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2016年6月28日下午10:46:10
	 */
	public Integer delIEC(@Param("id")int id);
	
	/**
	 * 
	 * @Description: TODO((查询相应合同下所有的保价费规则))
	 * @param con_id
	 * @return
	 * @return: List<Map<String,Object>>  
	 * @author Ian.Huang 
	 * @date 2016年6月28日下午10:43:29
	 */
	public List<Map<String, Object>> selectAllIEC(@Param("con_id")int con_id, @Param("belong_to")String belong_to);
	
	/**
	 * 
	 * @Description: TODO(查询阶梯区间)
	 * @param param
	 * @return
	 * @return: List<Map<String,Object>>  
	 * @author Ian.Huang 
	 * @date 2016年7月10日下午10:54:03
	 */
	public List<Map<String, Object>> selectRegion(Map<String, Object> param);
	
	/**
	 * 
	 * @Description: TODO(检测是否所有类型已存在)
	 * @param con_id
	 * @return
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2016年6月28日下午5:30:19
	 */
	public Integer findAllOrNot(@Param("con_id")int con_id, @Param("belong_to")String belong_to);
	
	/**
	 * 
	 * @Description: TODO(校验唯一性)
	 * @param param
	 * @return
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2016年7月10日下午10:51:56
	 */
	public Integer judgeUnique(Map<String, Object> param);
	
	/**
	 * 
	 * @Description: TODO(新增保价费规则)
	 * @param iFR
	 * @return
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2016年6月28日下午3:16:49
	 */
	public Integer addInsuranceEC(InsuranceEC iEC);

}
