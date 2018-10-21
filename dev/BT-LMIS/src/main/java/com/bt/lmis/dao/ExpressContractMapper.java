package com.bt.lmis.dao;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import com.bt.lmis.code.BaseMapper;

/**
 *
 * @Title:ExpressContractMapper
 * @Description: TODO(快递合同DAO)  
 * @author Ian.Huang 
 * @date 2016年6月22日下午12:58:30
 */
public interface ExpressContractMapper<T> extends BaseMapper<T> {

	/**
	 * 
	 * @Description: TODO(查询该合同主体下是否已存在有效合同)
	 * @param contract_owner
	 * @return
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2016年8月5日下午4:26:27
	 */
	public Integer selectValidContractByOwner(@Param("contract_owner")String contract_owner);
	
	/**
	 * 
	 * @Description: TODO(更新有效状态)
	 * @param currentUser
	 * @param validity
	 * @return
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2016年7月28日上午9:59:42
	 */
	public Integer updateValidity(@Param("id")int id, @Param("currentUser")String currentUser, @Param("validity")int validity);
	
	/**
	 * 
	 * @Description: TODO(查询有效合同)
	 * @param param
	 * @return
	 * @return: List<Map<String,Object>>  
	 * @author Ian.Huang 
	 * @date 2016年7月13日上午9:49:55
	 */
	public List<Map<String, Object>> findValidContract(Map<String, Object> param);
	
	/**
	 * 
	 * @Description: TODO(新增合同主信息)
	 * @param param
	 * @return
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2016年6月23日上午9:28:15
	 */
	public Integer addECM(Map<String, Object> param);
	
	/**
	 * 
	 * @Description: TODO(按联合主键查询合同主信息)
	 * @param contract_no
	 * @param contract_version
	 * @return
	 * @return: Map<String,Object>  
	 * @author Ian.Huang 
	 * @date 2016年6月23日上午9:40:44
	 */
	public Map<String, Object> findByCnoAndCvsAndCtp(
			@Param("contract_no")String contract_no,
			@Param("contract_type")String contract_type,
			@Param("contract_version")String contract_version);
	
	/**
	 * 
	 * @Description: TODO(按主键查询合同主信息)
	 * @param id
	 * @return
	 * @return: Map<String,Object>  
	 * @author Ian.Huang 
	 * @date 2016年6月23日下午12:00:18
	 */
	public Map<String, Object> findById(@Param("id")int id);
	
	/**
	 * 
	 * @Description: TODO(更新合同主信息)
	 * @param param
	 * @return
	 * @return: Integer  
	 * @author Ian.Huang 
	 * @date 2016年6月23日上午9:41:39
	 */
	public Integer updateECM(Map<String, Object> param);

	public void updateWarehouseExpressData();
	
}
