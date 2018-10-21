package com.bt.lmis.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bt.lmis.code.BaseMapper;
import com.bt.lmis.model.SpecialServiceEx;
import com.bt.lmis.model.SpecialServiceRedisBean;

/**
 * @Title:SpecialServiceExMapper(特殊服务费DAO)
 * @Description: TODO  
 * @author Ian.Huang 
 * @date 2016年6月30日下午4:57:21
 */
public interface SpecialServiceExMapper<T> extends BaseMapper<T> {
	
	/**
	 * 
	 * @Description: TODO
	 * @param key
	 * @return: SpecialServiceEx  
	 * @author Ian.Huang 
	 * @date 2016年11月30日下午8:46:40
	 */
	public SpecialServiceEx get(@Param("key")String key);
	
	/**
	 * 
	 * @Description: TODO(加载特殊服务费规则)
	 * @return: List<SpecialServiceRedisBean>  
	 * @author Ian.Huang 
	 * @date 2016年11月29日下午2:15:25
	 */
	public List<SpecialServiceRedisBean> loadSSE();
	
	/**
	 * 
	 * @Description: TODO(删除记录)
	 * @param con_id
	 * @param belong_to
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2016年10月10日下午3:45:31
	 */
	public Integer delSSE(@Param("con_id")int con_id, @Param("belong_to")String belong_to);
	
	/**
	 * 
	 * @Description: TODO(查询记录)
	 * @param con_id
	 * @param belong_to
	 * @return: SpecialServiceEx  
	 * @author Ian.Huang 
	 * @date 2016年7月11日上午9:25:02
	 */
	public SpecialServiceEx findRecord(@Param("con_id")int con_id, @Param("belong_to")String belong_to);
	
	/**
	 * 
	 * @Description: TODO(更新特殊服务费)
	 * @param sSEX
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2016年6月30日下午5:03:24
	 */
	public Integer updateSSEx(@Param("sSE") SpecialServiceEx sSE, @Param("cod")Boolean cod);
	
	/**
	 * 
	 * @Description: TODO(添加特殊服务费)
	 * @param sSEX
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2016年6月30日下午5:02:23
	 */
	public Integer addSSEx(SpecialServiceEx sSEX);
	
}
