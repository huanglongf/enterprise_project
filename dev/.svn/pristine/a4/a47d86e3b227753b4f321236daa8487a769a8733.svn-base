package com.bt.lmis.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.bt.lmis.code.BaseService;
import com.bt.lmis.controller.form.ExpertBillQueryParam;
import com.bt.lmis.model.EmsTemplate;
import com.bt.lmis.model.ExpertBill;
import com.bt.lmis.model.ExpressagingDetail;
import com.bt.lmis.model.ImpModel;
import com.bt.lmis.model.SfTemplate;
import com.bt.lmis.model.StoTemplate;
import com.bt.lmis.model.YtoTemplate;
import com.bt.lmis.page.QueryResult;
import com.bt.radar.model.ReceiveDeadline;

@Service
public interface ExpertBillService<T> extends BaseService<T> {

	public QueryResult<ExpertBill> findAll(ExpertBillQueryParam ebQP);

	List<ExpertBill> finrByID(@Param("id")String id);
	List<Map<String, Object>> findImpByBatId(@Param("id")String id);
	public List<Map<String, Object>> query_main(@Param("bat_id")String id);

	public void ins_imp(ReceiveDeadline receiveDeadline,List<ExpressagingDetail> impList);
	public List<ImpModel> query_imp_del(ImpModel imp);

	public void up_eb(@Param("bat_id")String bat_id);
	
	public void save_ems(EmsTemplate ems);
	public void save_sf(SfTemplate sf);
	public void save_yto(YtoTemplate yto);
	public void save_sto(StoTemplate sto);
	public void save_eb(ExpertBill eb);
	public void save_imp(ImpModel imp);

	public void del_eb(@Param("id")String id);
	public void del_ems(@Param("bat_id")String bat_id);
	public void del_sf(@Param("bat_id")String bat_id);
	public void del_yto(@Param("bat_id")String bat_id);
	public void del_sto(@Param("bat_id")String bat_id);
	public void del_imp(@Param("bat_id")String bat_id);
	

	public void check_wh(@Param("bat_id")String id);
	public void check_exp(@Param("bat_id")String id);
	public void check_area_s(@Param("bat_id")String id);
	public void check_area_q(@Param("bat_id")String id);
	public void check_type(@Param("bat_id")String id);
	public void check_main(@Param("bat_id")String id);

	public void batchInsert(List<String[]> sflist);
	
}
