package com.bt.lmis.dao;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bt.lmis.code.BaseMapper;
import com.bt.lmis.controller.form.EmsTemplateQueryParam;
import com.bt.lmis.controller.form.ExpertBillQueryParam;
import com.bt.lmis.controller.form.SfTemplateQueryParam;
import com.bt.lmis.controller.form.StoTemplateQueryParam;
import com.bt.lmis.controller.form.YtoTemplateQueryParam;
import com.bt.lmis.model.EmsTemplate;
import com.bt.lmis.model.ExpertBill;
import com.bt.lmis.model.ExpressagingDetail;
import com.bt.lmis.model.ImpModel;
import com.bt.lmis.model.SfTemplate;
import com.bt.lmis.model.StoTemplate;
import com.bt.lmis.model.YtoTemplate;
import com.bt.radar.model.ReceiveDeadline;

/**
* @ClassName: ExpertBillMapper
* @Description: TODO(ExpertBillMapper)
* @author Yuriy.Jiang
*
* @param <T>
*/ 
public interface ExpertBillMapper<T> extends BaseMapper<T>{
	/** 
	* @Title: countAllGroup 
	* @Description: TODO(查询记录条数) 
	* @param @param gQP
	* @param @return    设定文件 
	* @return Integer    返回类型 
	* @throws 
	*/
	public Integer countAlls(ExpertBillQueryParam ebQP);
	
	/**
	 * 
	 * @Description: TODO(查询所有组别记录)
	 * @return
	 * @return: List<Map<String, Object>>  
	 * @author Ian.Huang 
	 * @date 2016年8月8日下午7:23:36
	 */
	public List<ExpertBill> findAlls(ExpertBillQueryParam ebQP);
	public List<ExpertBill> finrByID(@Param("id")String id);
	public List<Map<String, Object>> findImpByBatId(@Param("id")String id);
	public List<Map<String, Object>> query_main(@Param("bat_id")String id);

	public void up_eb(@Param("bat_id")String bat_id);
	
	public Integer ins_imp(ReceiveDeadline receiveDeadline);
	public Integer ins_imp_detail(ExpressagingDetail expressagingDetail);
	
	public List<ImpModel> query_imp_del(ImpModel imp);

	public void check_wh(@Param("bat_id")String id);
	public void check_exp(@Param("bat_id")String id);
	public void check_area_s(@Param("bat_id")String id);
	public void check_area_q(@Param("bat_id")String id);
	public void check_type(@Param("bat_id")String id);
	public void check_main(@Param("bat_id")String id);
	
	public void save_eb(ExpertBill eb);
	public void save_ems(EmsTemplate ems);
	public void save_sf(SfTemplate sf);
	public void save_yto(YtoTemplate yto);
	public void save_sto(StoTemplate sto);
	public void save_imp(ImpModel imp);

	public void del_eb(@Param("id")String id);
	public void del_ems(@Param("bat_id")String bat_id);
	public void del_sf(@Param("bat_id")String bat_id);
	public void del_yto(@Param("bat_id")String bat_id);
	public void del_sto(@Param("bat_id")String bat_id);
	public void del_imp(@Param("bat_id")String bat_id);

	

	public Integer countSF(SfTemplateQueryParam ep);
	public List<SfTemplate> findSF(SfTemplateQueryParam ep);

	public Integer countEMS(EmsTemplateQueryParam ep);
	public List<EmsTemplate> findEMS(EmsTemplateQueryParam ep);

	public Integer countYTO(YtoTemplateQueryParam ep);
	public List<YtoTemplate> findYTO(YtoTemplateQueryParam ep);

	public Integer countSTO(StoTemplateQueryParam ep);
	public List<StoTemplate> findSTO(StoTemplateQueryParam ep);

	public void batchInsert(List<String[]> sf_list);
	
}
