package com.bt.lmis.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bt.lmis.code.BaseMapper;
import com.bt.lmis.model.JbpcRedisBean;
import com.bt.lmis.model.JibanpaoConditionEx;

/**
 * @Title:JbpcExMapper
 * @Description: TODO(计半抛条件DAO)  
 * @author Ian.Huang 
 * @date 2016年6月27日下午3:28:12
 */
public interface JbpcExMapper<T> extends BaseMapper<T> {
	
	/**
	 * 
	 * @Description: TODO
	 * @param redis_key
	 * @return: List<JibanpaoConditionEx>  
	 * @author Ian.Huang 
	 * @date 2016年11月30日下午3:27:49
	 */
	public List<JibanpaoConditionEx> get(@Param("redis_key")String redis_key);
	
	/**
	 * 
	 * @Description: TODO(加载计半抛条件)
	 * @return: List<JbpcRedisBean> 
	 * @author Ian.Huang 
	 * @date 2016年11月28日下午5:51:42
	 */
	public List<JbpcRedisBean> loadJBPC();
	
	/**
	 * 
	 * @Description: TODO(查询计半抛条件是否存在)
	 * @param param
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2016年6月23日上午9:41:53
	 */
	public Integer judgeUnique(JibanpaoConditionEx jbpc);
	
	/**
	 * 
	 * @Description: TODO(查询相应合同ID下的所有计半抛条件)
	 * @return: JibanpaoConditionEx  
	 * @author Ian.Huang 
	 * @date 2016年6月24日上午9:16:25
	 */
	public List<JibanpaoConditionEx> findAllJBPCondition(@Param("con_id")int con_id, @Param("belong_to")String belong_to);
	
	/**
	 * 
	 * @Description: TODO(新增计半抛条件)
	 * @param jbpc
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2016年6月23日上午10:49:43
	 */
	public Integer addJBPConditon(JibanpaoConditionEx jbpc);
	
	/**
	 * 
	 * @Description: TODO(删除一条记录)
	 * @param id
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2016年6月24日下午1:07:11
	 */
	public Integer delJBPConditon(@Param("id")int id);
	
	/**
	 * 
	 * @Description: TODO
	 * @param con_id
	 * @param belong_to
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2016年10月10日下午3:23:36
	 */
	public Integer delJBPC(@Param("con_id")Integer con_id, @Param("belong_to")String belong_to);
	
}
