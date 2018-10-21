package com.bt.lmis.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bt.lmis.code.BaseMapper;
import com.bt.lmis.model.PackagePriceLadder;

/**
 * @Title:PackageChargeMapper
 * @Description: TODO(打包费阶梯DAO)
 * @author Ian.Huang 
 * @date 2016年7月4日下午4:32:24
 */
public interface PackageChargeLadderMapper<T> extends BaseMapper<T> {
	
	/**
	 * 
	 * @Description: TODO(删除阶梯)
	 * @param id
	 * @return
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2016年7月5日下午3:17:09
	 */
	public Integer delLadder(@Param("id")int id, @Param("update_by")String update_by);
	
	/**
	 * 
	 * @Description: TODO(通过id查询记录)
	 * @param id
	 * @return
	 * @return: PackagePriceLadder  
	 * @author Ian.Huang 
	 * @date 2016年7月5日下午3:15:39
	 */
	public PackagePriceLadder findById(@Param("id")int id);
	
	/**
	 * 
	 * @Description: TODO(加载记录)
	 * @param param
	 * @return
	 * @return: PackagePriceLadder  
	 * @author Ian.Huang 
	 * @date 2016年7月5日上午9:41:15
	 */
	public List<Map<String, Object>> loadRecords(Map<String, Object> param);
	
	/**
	 * 
	 * @Description: TODO(更新无阶梯)
	 * @param param
	 * @return
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2016年7月5日上午9:39:36
	 */
	public Integer updatePackageChargeLadder(PackagePriceLadder pPL);
	
	/**
	 * 
	 * @Description: TODO(新增阶梯)
	 * @param pPL
	 * @return
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2016年7月5日上午9:38:50
	 */
	public Integer addPackageChargeLadder(PackagePriceLadder pPL);
	
	/**
	 * 
	 * @Description: TODO(查询阶梯记录是否存在)
	 * @param param
	 * @return
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2016年7月4日下午4:35:01
	 */
	public Integer findLadder(Map<String, Object> param);
	
}
