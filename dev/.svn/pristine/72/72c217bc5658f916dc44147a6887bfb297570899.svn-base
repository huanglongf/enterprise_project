package com.bt.lmis.dao;

import org.apache.ibatis.annotations.Param;

import com.bt.lmis.code.BaseMapper;
import com.bt.lmis.model.PackagePrice;

/**
 * @Title:PackageChargeMapper
 * @Description: TODO(打包费DAO)  
 * @author Ian.Huang 
 * @date 2016年7月5日下午5:58:06
 */
public interface PackageChargeMapper<T> extends BaseMapper<T> {

	/**
	 * 
	 * @Description: TODO(查询合同对应记录)
	 * @param con_id
	 * @return
	 * @return: PackagePrice  
	 * @author Ian.Huang 
	 * @date 2016年7月5日下午6:01:42
	 */
	public PackagePrice findByConId(@Param("con_id")int con_id);
	
	/**
	 * 
	 * @Description: TODO(更新打包费)
	 * @param pP
	 * @return
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2016年7月5日下午6:00:34
	 */
	public Integer updatePackageCharge(PackagePrice pP);
	
	/**
	 * 
	 * @Description: TODO(新增打包费)
	 * @param pP
	 * @return
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2016年7月5日下午5:59:47
	 */
	public Integer addPackageCharge(PackagePrice pP);
	
}
