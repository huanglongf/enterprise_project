package com.bt.lmis.dao;

import org.apache.ibatis.annotations.Param;

import com.bt.lmis.code.BaseMapper;
import com.bt.lmis.model.CarrierFeeFlag;

/**
 * @Title:CarrierFeeFlagMapper
 * @Description: TODO(SOSP运费总运费折扣/管理费开关DAO)
 * @author Ian.Huang 
 * @date 2016年8月12日下午4:22:00
 */
public interface CarrierFeeFlagMapper<T> extends BaseMapper<T> {

	/**
	 * 
	 * @Description: TODO
	 * @param con_id
	 * @return
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2016年10月20日下午10:16:50
	 */
	public Integer delCarrierFeeFlag(@Param("con_id")int con_id);
	
	/**
	 * 
	 * @Description: TODO(按合同ID查询记录)
	 * @param con_id
	 * @return
	 * @return: CarrierFeeFlag  
	 * @author Ian.Huang 
	 * @date 2016年8月12日下午4:23:23
	 */
	public CarrierFeeFlag selectByConId(@Param("con_id")int con_id);
	
	/**
	 * 
	 * @Description: TODO(更新承运商费用开关)
	 * @param carrierFeeFlag
	 * @return
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2016年8月12日下午4:24:38
	 */
	public Integer updateCarrierFeeFlag(CarrierFeeFlag carrierFeeFlag);
	
	/**
	 * 
	 * @Description: TODO(新增承运商费用开关记录)
	 * @param carrierFeeFlag
	 * @return
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2016年8月12日下午4:25:32
	 */
	public Integer insertCarrierFeeFlag(CarrierFeeFlag carrierFeeFlag);
	
}
