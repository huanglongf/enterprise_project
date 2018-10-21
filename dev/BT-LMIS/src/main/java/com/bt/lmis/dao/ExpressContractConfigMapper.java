package com.bt.lmis.dao;

import org.apache.ibatis.annotations.Param;

import com.bt.lmis.code.BaseMapper;
import com.bt.lmis.model.ExpressContractConfig;

/**
 * @Title:ExpressContractConfigMapper
 * @Description: TODO(快递合同配置DAO)
 * @author Ian.Huang 
 * @date 2016年6月29日下午7:31:29
 */
public interface ExpressContractConfigMapper<T> extends BaseMapper<T> {
	
	/**
	 * 
	 * @Description: TODO(删除配置)
	 * @param con_id
	 * @param belong_to
	 * @return
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2016年10月10日下午3:21:23
	 */
	public Integer delConfig(@Param("con_id")Integer con_id, @Param("belong_to")String belong_to);
	
	/**
	 * 
	 * @Description: TODO(查询快递合同配置)
	 * @param con_id
	 * @param belong_to
	 * @return
	 * @return: ExpressContractConfig  
	 * @author Ian.Huang 
	 * @date 2016年7月15日上午11:03:13
	 */
	public ExpressContractConfig findECC(@Param("con_id")int con_id, @Param("belong_to")String belong_to);

	/**
	 * 
	 * @Description: TODO(更新快递合同配置)
	 * @param eCC
	 * @return
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2016年6月29日下午7:33:20
	 */
	public Integer updateExpressContractConfig(ExpressContractConfig eCC);
	
	/**
	 * 
	 * @Description: TODO(初始化快递合同配置)
	 * @param eCC
	 * @return
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2016年6月29日下午7:32:28
	 */
	public Integer initializeExpressContractConfig(@Param("con_id") int con_id, @Param("belong_to")String belong_to, @Param("create_by") String create_by);

}
