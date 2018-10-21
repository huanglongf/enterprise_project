package com.bt.lmis.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bt.lmis.code.BaseMapper;
import com.bt.lmis.model.TotalFreightDiscount;

/**
 * @Title:ProductTypeMapper
 * @Description: TODO(总运费折扣DAO)
 * @author Ian.Huang 
 * @date 2016年6月27日下午3:26:45
 */
public interface TotalFreightDiscountMapper<T> extends BaseMapper<T> {

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
	 * @date 2016年10月10日下午4:47:16
	 */
	public Integer deleteTFD(@Param("con_id")Integer con_id, @Param("belong_to")String belong_to);
	
	/**
	 * 
	 * @Description: TODO(删除一条总运费折扣记录)
	 * @param id
	 * @return
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2016年6月28日上午2:29:06
	 */
	public Integer delTFD(@Param("id")int id);
	
	/**
	 * 
	 * @Description: TODO(检测是否所有类型已存在)
	 * @param con_id
	 * @return
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2016年6月28日上午2:08:23
	 */
	public Integer findAllOrNot(@Param("con_id")int con_id, @Param("belong_to")String belong_to);
	
	/**
	 * 
	 * @Description: TODO(查询相应合同下所有的总运费折扣规则)
	 * @param con_id
	 * @return
	 * @return: List<TotalFreightDiscount>  
	 * @author Ian.Huang 
	 * @date 2016年6月28日上午12:28:23
	 */
	public List<Map<String, Object>> selectAllTFD(
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
	 * @Description: TODO(新增总运费折扣)
	 * @param totalFreightDiscount
	 * @return
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2016年6月27日下午10:01:59
	 */
	public Integer addTotalFreightDiscount(TotalFreightDiscount totalFreightDiscount);
}
