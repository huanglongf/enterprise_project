package com.bt.lmis.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bt.lmis.code.BaseMapper;
import com.bt.lmis.model.PricingFormula;
import com.bt.lmis.model.PricingFormulaRedisBean;

/**
 * @Title:PricingFormulaMapper
 * @Description: TODO(计费公式DAO) 
 * @author Ian.Huang 
 * @date 2016年11月30日下午4:56:09
 */
public interface PricingFormulaMapper<T> extends BaseMapper<T> {
	
	/**
	 * 
	 * @Description: TODO
	 * @param key
	 * @return: PricingFormula  
	 * @author Ian.Huang 
	 * @date 2016年11月30日下午3:37:32
	 */
	public PricingFormula get(@Param("key")String key);
	
	/**
	 * 
	 * @Description: TODO(加载计费公式)
	 * @return
	 * @return: List<PricingFomulaRedisBean>  
	 * @author Ian.Huang 
	 * @date 2016年11月29日上午10:30:41
	 */
	public List<PricingFormulaRedisBean> loadPF();
	
	/**
	 * 
	 * @Description: TODO
	 * @param con_id
	 * @param belong_to
	 * @return
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2016年10月10日下午3:31:45
	 */
	public Integer deletePF(@Param("con_id")Integer con_id, @Param("belong_to")String belong_to);
	
	/**
	 * 
	 * @Description: TODO(删除一条计费公式)
	 * @param id
	 * @return
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2016年6月26日下午11:04:01
	 */
	public Integer delPF(@Param("id")int id);
	
	/**
	 * 
	 * @Description: TODO(根据ID获取计费公式)
	 * @param id
	 * @return
	 * @return: Map<String,Object>  
	 * @author Ian.Huang 
	 * @date 2016年6月26日下午11:01:52
	 */
	public Map<String, Object> getPF(@Param("id")int id);
	
	/**
	 * 
	 * @Description: TODO(获取相应合同下所有计费公式)
	 * @param con_id
	 * @return
	 * @return: Map<String,Object>  
	 * @author Ian.Huang 
	 * @date 2016年6月26日下午1:44:19
	 */
	public List<Map<String, Object>> findAllPF(@Param("con_id")int con_id, @Param("belong_to")String belong_to);
	
	/**
	 * 
	 * @Description: TODO(更新计费公式)
	 * @param param
	 * @return
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2016年6月26日下午12:33:58
	 */
	public Integer updatePricingFormula(Map<String, Object> param);
	
	/**
	 * 
	 * @Description: TODO(新增计费公式)
	 * @param param
	 * @return
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2016年6月26日下午12:23:18
	 */
	public Integer addPricingFormula(Map<String, Object> param);
	
	/**
	 * 
	 * @Description: TODO(获取公式记录)
	 * @param param
	 * @return
	 * @return: Map<String,Object>  
	 * @author Ian.Huang 
	 * @date 2016年7月18日下午1:26:51
	 */
	public Map<String, Object> getRecord(Map<String, Object> param);
	
}
