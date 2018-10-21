package com.bt.radar.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bt.lmis.code.BaseMapper;
import com.bt.radar.controller.form.ReceiveDeadlineQueryParam;
import com.bt.radar.model.ReceiveDeadline;

/**
 * @Title:ReceiveDeadlineMapper
 * @Description: TODO(揽件截止时间DAO) 
 * @author Ian.Huang 
 * @date 2016年8月25日下午3:10:24
 */
public interface ReceiveDeadlineMapper<T> extends BaseMapper<T> {
	
	/**
	 * 
	 * @Description: TODO(根据ID取记录)
	 * @param id
	 * @return
	 * @return: ReceiveDeadline  
	 * @author Ian.Huang 
	 * @date 2016年8月29日下午3:30:43
	 */
	public ReceiveDeadline getById(@Param("id")String id);
	
	/**
	 * 
	 * @Description: TODO(计算所有存在记录条数)
	 * @param receiveDeadlineQueryParam
	 * @return
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2016年8月26日下午12:54:22
	 */
	public Integer countAllExist(ReceiveDeadlineQueryParam receiveDeadlineQueryParam);
	
	/**
	 * 
	 * @Description: TODO(查询所有存在揽件截止时间记录列表)
	 * @param receiveDeadlineQueryParam
	 * @return
	 * @return: List<Map<String,Object>>
	 * @author Ian.Huang 
	 * @date 2016年8月25日下午3:18:56
	 */
	public List<Map<String, Object>> toList(ReceiveDeadlineQueryParam receiveDeadlineQueryParam);
	
	/**
	 * 
	 * @Description: TODO(查询所有存在揽件截止时间记录)
	 * @param receiveDeadline
	 * @return
	 * @return: ReceiveDeadline  
	 * @author Ian.Huang 
	 * @date 2016年8月25日下午3:17:48
	 */
	public ReceiveDeadline findAllExist(ReceiveDeadline receiveDeadline);
	
	/**
	 * 
	 * @Description: TODO(更新一条揽件截止时间)
	 * @param receiveDeadline
	 * @return
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2016年8月25日下午3:12:56
	 */
	public Integer updateReceiveDeadline(ReceiveDeadline receiveDeadline);
	
	/**
	 * 
	 * @Description: TODO(插入一条揽件截止时间)
	 * @param receiveDeadline
	 * @return
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2016年8月25日下午3:11:48
	 */
	public Integer insertReceiveDeadline(ReceiveDeadline receiveDeadline);

	public void pro_radar_refresh_receive_temp();
	
}
