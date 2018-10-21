package com.bt.lmis.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bt.lmis.code.BaseMapper;
import com.bt.lmis.model.ManagementEC;

/**
 * @Title:ManagementECMapper
 * @Description: TODO(管理费DAO) 
 * @author Ian.Huang 
 * @date 2016年7月3日上午10:22:09
 */
public interface ManagementECMapper<T> extends BaseMapper<T> {
	
	/**
	 * 
	 * @Description: TODO(获取存在管理规则的快递)
	 * @param con_id
	 * @return
	 * @return: List<String>  
	 * @author Ian.Huang 
	 * @date 2016年10月25日下午4:57:47
	 */
	public List<String> getExpressWithRule(@Param("con_id")Integer con_id);
	
	/**
	 * 
	 * @Description: TODO
	 * @param con_id
	 * @return
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2016年10月20日下午10:01:53
	 */
	public Integer deleteMan(@Param("con_id")Integer con_id);
	
	/**
	 * 
	 * @Description: TODO(删除一条管理费规则)
	 * @param id
	 * @return
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2016年7月4日上午1:38:45
	 */
	public Integer delManEC(@Param("id")int id);
	
	/**
	 * 
	 * @Description: TODO(查询所有管理费规则记录)
	 * @param con_id
	 * @param belong_to
	 * @return
	 * @return: List<Map<String,Object>>  
	 * @author Ian.Huang 
	 * @date 2016年7月11日上午9:55:54
	 */
	public List<Map<String, Object>> selectAllManEC(@Param("con_id")int con_id, @Param("belong_to")String belong_to);
	
	/**
	 * 
	 * @Description: TODO(查询区间)
	 * @param combination
	 * @return
	 * @return: List<Map<String,Object>>  
	 * @author Ian.Huang 
	 * @date 2016年7月4日上午12:10:13
	 */
	public List<Map<String, Object>> selectRegion(Map<String, Object> combination);
	
	/**
	 * 
	 * @Description: TODO(校验组合)
	 * @param combination
	 * @return
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2016年7月3日下午11:09:38
	 */
	public Integer checkCombination(Map<String, Object> combination);
	
	/**
	 * 
	 * @Description: TODO(新增管理费)
	 * @param iEC
	 * @return
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2016年7月3日上午10:22:39
	 */
	public Integer addManagementEC(ManagementEC manEC);

}
