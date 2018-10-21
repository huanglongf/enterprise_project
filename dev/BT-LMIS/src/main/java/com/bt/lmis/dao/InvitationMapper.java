package com.bt.lmis.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bt.lmis.code.BaseMapper;
import com.bt.lmis.controller.form.InvitationQueryParam;
import com.bt.lmis.model.Invitation;
import com.bt.lmis.model.InvitationBean;

/** 
* @ClassName: ContractBasicinfoMapper 
* @Description: TODO(合同Dao) 
* @author Yuriy.Jiang
* @date 2016年6月20日 下午2:50:48 
* 
* @param <T> 
*/
public interface InvitationMapper<T> extends BaseMapper<T>{
	
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
	
	public Integer getBatId();
	
	public Integer insertRawData(Invitation invitation);
	
	/** 
	* @Title: findAll 
	* @Description: TODO(根据条件查询[分页]内容) 
	* @param @param contractBasicinfoQueryParam
	* @param @return    设定文件 
	* @return List<Map<String,Object>>    返回类型 
	* @throws 
	*/
	public List<InvitationBean> findCB(InvitationQueryParam queryParam);
	/** 
	* @Title: countPowerRecords 
	* @Description: TODO(根据条件查询[分页]条数) 
	* @param @param queryParam
	* @param @return    设定文件 
	* @return int    返回类型 
	* @throws 
	*/
	/** 
	* @Title: countPowerRecords 
	* @Description: TODO(根据条件查询[分页]条数) 
	* @param @param queryParam
	* @param @return    设定文件 
	* @return int    返回类型 
	* @throws 
	*/
	public int countCBRecords(InvitationQueryParam queryParam);

	
}
