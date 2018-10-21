package com.bt.lmis.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bt.lmis.code.BaseMapper;
import com.bt.lmis.model.CarrierSosp;

/**
 * @Title:CarrierSospMapper
 * @Description: TODO(sosp运费DAO)  
 * @author Ian.Huang 
 * @date 2016年7月6日下午5:05:46
 */
public interface CarrierSospMapper<T> extends BaseMapper<T> {

	/**
	 * 
	 * @Description: TODO
	 * @param id
	 * @return
	 * @return: CarrierSosp  
	 * @author Ian.Huang 
	 * @date 2016年10月10日下午3:07:44
	 */
	public CarrierSosp getById(@Param("id")Integer id);
	
	/**
	 * 
	 * @Description: TODO(查询合同下使用物流)
	 * @param con_id
	 * @return
	 * @return: List<Map<String,Object>>  
	 * @author Ian.Huang 
	 * @date 2016年8月22日下午3:21:20
	 */
	public List<Map<String, Object>> findLogistics(@Param("con_id")Integer con_id);
	
	/**
	 * 
	 * @Description: TODO(查询合同下使用快递)
	 * @param con_id
	 * @return
	 * @return: List<Map<String, Object>> 
	 * @author Ian.Huang 
	 * @date 2016年7月28日下午2:00:55
	 */
	public List<Map<String, Object>> findExpress(@Param("con_id")Integer con_id);
	
	/**
	 * 
	 * @Description: TODO(校验是否存在该承运商)
	 * @param con_id
	 * @param carrier
	 * @return
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2016年7月15日上午10:31:33
	 */
	public Integer checkExist(@Param("con_id")int con_id, @Param("carrier")String carrier);
	
	/**
	 * 
	 * @Description: TODO(查询承运商)
	 * @param con_id
	 * @return
	 * @return: List<Map<String,Object>>  
	 * @author Ian.Huang 
	 * @date 2016年7月6日下午5:11:42
	 */
	public List<Map<String, Object>> findCarriers(@Param("con_id")int con_id);
	
	/**
	 * 
	 * @Description: TODO(删除承运商)
	 * @param id
	 * @return
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2016年7月6日下午5:10:13
	 */
	public Integer delCarrier(@Param("id")int id);
	
	/**
	 * 
	 * @Description: TODO(新增承运商)
	 * @param cS
	 * @return
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2016年7月6日下午5:09:04
	 */
	public Integer addCarrier(CarrierSosp cS);
	
}
