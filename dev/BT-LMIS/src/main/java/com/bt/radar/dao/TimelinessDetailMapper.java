package com.bt.radar.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bt.lmis.code.BaseMapper;
import com.bt.radar.controller.form.TimelinessDetailQueryParam;
import com.bt.radar.model.TimelinessDetail;

/**
 * @Title:TimelinessDetailMapper
 * @Description: TODO(时效性明细DAO)  
 * @author Ian.Huang 
 * @date 2016年8月30日上午10:42:06
 */
public interface TimelinessDetailMapper<T> extends BaseMapper<T> {
	
	/**
	 * 
	 * @Description: TODO(获取删除节点后的节点集合)
	 * @param pid
	 * @param number
	 * @return
	 * @return: List<TimelinessDetail>  
	 * @author Ian.Huang 
	 * @date 2016年8月31日下午1:36:53
	 */
	public List<TimelinessDetail> getAfterNodes(@Param("pid")String pid, @Param("number")Integer number);
	
	/**
	* @Title: getMaxNumber
	* @Description: TODO(获取最大节点序号)
	* @param @param pid
	* @param @return    设定文件
	* @return Integer    返回类型
	* @throws
	*/ 
	public Integer getMaxNumber(@Param("pid")String pid);
	
	/**
	 * 
	 * @Description: TODO(根据ID取记录)
	 * @param id
	 * @return
	 * @return: TimelinessDetail  
	 * @author Ian.Huang 
	 * @date 2016年8月29日下午3:30:43
	 */
	public TimelinessDetail getById(@Param("id")String id);
	
	/**
	 * 
	 * @Description: TODO(计算所有存在记录条数)
	 * @param timelinessDetailQueryParam
	 * @return
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2016年8月26日下午12:54:22
	 */
	public Integer countAllExist(TimelinessDetailQueryParam timelinessDetailQueryParam);
	
	/**
	 * 
	 * @Description: TODO(查询所有存在时效明细记录列表)
	 * @param timelinessDetailQueryParam
	 * @return
	 * @return: List<Map<String,Object>>
	 * @author Ian.Huang 
	 * @date 2016年8月25日下午3:18:56
	 */
	public List<Map<String, Object>> toList(TimelinessDetailQueryParam timelinessDetailQueryParam);
	
	/**
	 * 
	 * @Description: TODO()
	 * @param timelinessDetail
	 * @return
	 * @return: TimelinessDetail  
	 * @author Ian.Huang 
	 * @date 2016年8月25日下午3:17:48
	 */
	public TimelinessDetail findAllExist(TimelinessDetail timelinessDetail);
	
	/**
	 * 
	 * @Description: TODO(更新一条时效明细)
	 * @param timelinessDetail
	 * @return
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2016年8月25日下午3:12:56
	 */
	public Integer updateTimelinessDetail(TimelinessDetail timelinessDetail);
	
	/**
	 * 
	 * @Description: TODO(插入一条时效明细)
	 * @param timelinessDetail
	 * @return
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2016年8月25日下午3:11:48
	 */
	public Integer insertTimelinessDetail(TimelinessDetail timelinessDetail);
	
}
