package com.bt.lmis.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bt.lmis.code.BaseMapper;
import com.bt.lmis.model.Order;

/**
* @ClassName: OrderMapper
* @Description: TODO(订单接口DAO)
* @author Ian.Huang
* @date 2016年9月19日 下午4:05:28
*
* @param <T>
*/
public interface OrderMapper<T> extends BaseMapper<T> {
	
	public Integer countData(@Param("bat_id")Integer bat_id, @Param("pro_flag")String pro_flag);
	
	public Integer del(@Param("bat_id")Integer bat_id);
	
	/**
	 * 
	 * @Description: TODO(查询批次号)
	 * @param bat_id
	 * @return
	 * @return: List<Integer>  
	 * @author Ian.Huang 
	 * @date 2016年9月21日下午3:22:08
	 */
	public List<Integer> getBatIds(@Param("bat_id")Integer bat_id);
	
	/**
	* @Title: getBatId
	* @Description: TODO(获取批次号)
	* @param @return    设定文件
	* @return Integer    返回类型
	* @throws
	*/ 
	public Integer getBatId();
	
	/**
	* @Title: insertRawData
	* @Description: TODO(插入原始数据)
	* @param @param order
	* @param @return    设定文件
	* @return Integer    返回类型
	* @throws
	*/ 
	public Integer insertRawData(Order order);

}
