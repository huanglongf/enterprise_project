package com.bt.lmis.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bt.lmis.code.BaseMapper;
import com.bt.lmis.model.WaybillDiscountEx;

/**
 * @Title:WaybillDiscountExMapper
 * @Description: TODO(运单折扣DAO) 
 * @author Ian.Huang 
 * @date 2016年8月18日上午10:55:13
 */
public interface WaybillDiscountExMapper<T> extends BaseMapper<T> {

	/**
	 * 
	 * @Description: TODO(查询阶梯区间)
	 * @param param
	 * @return
	 * @return: List<Map<String,Object>>  
	 * @author Ian.Huang 
	 * @date 2016年7月10日下午10:17:57
	 */
	public List<Map<String, Object>> selectRegion(Map<String, Object> param);
	
	/**
	 * 
	 * @Description: TODO
	 * @param con_id
	 * @param belong_to
	 * @return
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2016年10月10日下午4:50:14
	 */
	public Integer deleteWD(@Param("con_id")Integer con_id, @Param("belong_to")String belong_to);
	
	/**
	 * 
	 * @Description: TODO(删除一条运费折扣启用条件记录)
	 * @param id
	 * @return
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2016年8月18日上午10:59:30
	 */
	public Integer delWD(@Param("id")int id);
	
	/**
	 * 
	 * @Description: TODO(检测是否所有类型已存在)
	 * @param con_id
	 * @param belong_to
	 * @return
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2016年8月18日上午10:58:41
	 */
	public Integer findAllOrNot(@Param("con_id")int con_id, @Param("belong_to")String belong_to);
	
	/**
	 * 
	 * @Description: TODO(查询相应合同下所有的运单折扣启用条件)
	 * @param con_id
	 * @param belong_to
	 * @param product_type
	 * @return
	 * @return: List<Map<String,Object>>  
	 * @author Ian.Huang 
	 * @date 2016年8月18日上午10:57:40
	 */
	public List<Map<String, Object>> selectAllWD(
			@Param("con_id")int con_id, 
			@Param("belong_to")String belong_to,
			@Param("product_type")String product_type
			);
	
	/**
	 * 
	 * @Description: TODO(查询唯一性条件)
	 * @param param
	 * @return
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2016年7月10日下午10:14:40
	 */
	public Integer judgeUnique(Map<String, Object> param);
	
	/**
	 * 
	 * @Description: TODO(新增运单折扣生效条件)
	 * @param waybillDiscountEx
	 * @return
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2016年8月18日上午10:56:23
	 */
	public Integer addWaybillDiscount(WaybillDiscountEx waybillDiscountEx);
}
