package com.bt.lmis.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.lmis.code.ServiceSupport;
import com.bt.lmis.controller.form.ContractBasicinfoQueryParam;
import com.bt.lmis.dao.CarrierFeeFlagMapper;
import com.bt.lmis.dao.CarrierSospMapper;
import com.bt.lmis.dao.ContractBasicinfoMapper;
import com.bt.lmis.dao.ExpressContractConfigMapper;
import com.bt.lmis.dao.InsuranceECMapper;
import com.bt.lmis.dao.JbpcExMapper;
import com.bt.lmis.dao.ManagementECMapper;
import com.bt.lmis.dao.PricingFormulaMapper;
import com.bt.lmis.dao.SpecialServiceExMapper;
import com.bt.lmis.dao.TotalFreightDiscountMapper;
import com.bt.lmis.dao.WaybillDiscountExMapper;
import com.bt.lmis.model.ContractBasicinfo;
import com.bt.lmis.model.SearchBean;
import com.bt.lmis.page.QueryResult;
import com.bt.lmis.service.ContractBasicinfoService;

/** 
* @ClassName: ContractBasicinfoServiceImpl 
* @Description: TODO(合同ServiceImpl) 
* @author Yuriy.Jiang
* @date 2016年6月20日 下午3:56:26 
*  
*/
@Service
public class ContractBasicinfoServiceImpl<T> extends ServiceSupport<T> implements ContractBasicinfoService<T> {
	
	@Autowired
	private CarrierFeeFlagMapper<T> carrierFeeFlagMapper;
	public CarrierFeeFlagMapper<T> getCarrierFeeFlagMapper(){
		return carrierFeeFlagMapper;
	}
	@Autowired
	private ManagementECMapper<T> managementECMapper;
	public ManagementECMapper<T> getManagementECMapper(){
		return managementECMapper;
	}
	@Autowired
	private CarrierSospMapper<T> carrierSospMapper;
	public CarrierSospMapper<T> getCarrierSospMapper(){
		return carrierSospMapper;
	}
	@Autowired
	private WaybillDiscountExMapper<T> waybillDiscountExMapper;
	public WaybillDiscountExMapper<T> getWaybillDiscountExMapper(){
		return waybillDiscountExMapper;
	}
	@Autowired
	private TotalFreightDiscountMapper<T> totalFreightDiscountMapper;
	public TotalFreightDiscountMapper<T> getTotalFreightDiscountMapper(){
		return totalFreightDiscountMapper;
	}
	@Autowired
	private SpecialServiceExMapper<T> specialServiceExMapper;
	public SpecialServiceExMapper<T> getSpecialServiceExMapper(){
		return specialServiceExMapper;
	}
	@Autowired
	private InsuranceECMapper<T> insuranceECMapper;
	public InsuranceECMapper<T> getInsuranceECMapper(){
		return insuranceECMapper;
	}
	@Autowired
	private PricingFormulaMapper<T> pFExMapper;
	public PricingFormulaMapper<T> getPFExMapper(){
		return pFExMapper;
	}
	@Autowired
	private JbpcExMapper<T> jbpcExMapper;
	public JbpcExMapper<T> getJbpcExMapper(){
		return jbpcExMapper;
	}
	@Autowired
	private ExpressContractConfigMapper<T> expressContractConfigMapper;
	public ExpressContractConfigMapper<T> getExpressContractConfigMapper(){
		return expressContractConfigMapper;
	}
	
	@Autowired
	private ContractBasicinfoMapper<T> contractBasicinfoMapper;
	
	@Override
	public QueryResult<ContractBasicinfo> findAll(ContractBasicinfoQueryParam queryParam) {
		QueryResult<ContractBasicinfo> qr = new QueryResult<ContractBasicinfo>();
		qr.setResultlist(contractBasicinfoMapper.findCB(queryParam));
		qr.setTotalrecord(contractBasicinfoMapper.countCBRecords(queryParam));
		return qr;
	}

	@Override
	public void batchDelete(Integer[] ids) throws Exception {
		Integer id = null;
		ContractBasicinfo cB = null;
		for(int i= 0; i< ids.length; i++){
			// 合同关联项删除
			id = ids[0];
			cB = contractBasicinfoMapper.findById(id);
			Integer con_id = Integer.parseInt(cB.getId());
			String belong_to = null;
			switch(Integer.parseInt(cB.getContract_type())){
			case 1:
				belong_to = cB.getContract_owner();
				// 删除其配置
				expressContractConfigMapper.delConfig(con_id, belong_to);
				// 删除计半抛条件
				jbpcExMapper.delJBPC(con_id, belong_to);
				// 删除计费公式
				pFExMapper.deletePF(con_id, belong_to);
				// 删除保价费规则
				insuranceECMapper.deleteIEC(con_id, belong_to);
				// 删除特殊服务费
				specialServiceExMapper.delSSE(con_id, belong_to);
				if(belong_to.equals("SF")){
					// 如果是顺丰
					// 删除单运单折扣
					waybillDiscountExMapper.deleteWD(con_id, belong_to);
				} else {
					// 删除总运费折扣
					totalFreightDiscountMapper.deleteTFD(con_id, belong_to);
				}
			break;
			case 2:;
			break;
			case 3:;
			case 4:
				// 删除店铺/客户下使用快递及其对应规则
				List<Map<String, Object>> expresses = carrierSospMapper.findExpress(con_id);
				Map<String, Object> express = null;
				for(int j= 0; j< expresses.size(); j++){
					express = expresses.get(j);
					belong_to = express.get("carrier_code").toString();
					if(carrierSospMapper.delCarrier(Integer.parseInt(express.get("id").toString())) != 0){
						// 删除其配置
						expressContractConfigMapper.delConfig(con_id, belong_to);
						// 删除计半抛条件
						jbpcExMapper.delJBPC(con_id, belong_to);
						// 删除计费公式
						pFExMapper.deletePF(con_id, belong_to);
						// 删除保价费规则
						insuranceECMapper.deleteIEC(con_id, belong_to);
						// 删除特殊服务费
						specialServiceExMapper.delSSE(con_id, belong_to);
					}
				}
				// 删除使用承运商总运费折扣/管理费配置
				carrierFeeFlagMapper.delCarrierFeeFlag(con_id);
				// 删除总运费折扣规则
				totalFreightDiscountMapper.deleteTFD(con_id, null);
				// 删除管理费规则
				managementECMapper.deleteMan(con_id);
			break;
			default:
				continue;
			}
		}
		// 主表数据批量删除
		contractBasicinfoMapper.batchDelete(ids);
	}

	public int save(T cb) throws Exception {
		return contractBasicinfoMapper.insert(cb);
	}

	public void update(T cb) throws Exception {
		contractBasicinfoMapper.update(cb);
	}

	@Override
	public ContractBasicinfo findById(int id) {
		return contractBasicinfoMapper.findById(id);
	}


	@Override
	public Long selectCount(Map<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public ArrayList getSearchParam(Map<String, Object> param) {
		// TODO Auto-generated method stub
		 ArrayList<HashMap<String, String>> list=contractBasicinfoMapper.getSearchParam(param);
		 if(list==null){
			 list=new ArrayList<HashMap<String, String>>();
			 return list;
		 }
		 if(list.size()!=0){
		 HashMap<String, String> map=new HashMap<String, String>();
		 Set<?> set=((HashMap<?, ?>)list.get(0)).keySet();
		 Iterator<?> iter = set.iterator();
		 for(int i=list.size();i<10;i++){
		 while (iter.hasNext()) {
		 String key = (String) iter.next();
		 map.put(key, "");
		 }
		 list.add(map);
		 }
		return list;
		 }
		 return list;
	}


	@Override
	public ArrayList getShowParam(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return contractBasicinfoMapper.getShowParam(param)==null?new ArrayList():contractBasicinfoMapper.getShowParam(param);
	}


	@Override
	public ArrayList<ContractBasicinfo> getPageInfo(HashMap param) {
		// TODO Auto-generated method stub
		return contractBasicinfoMapper.getPageInfo(param)==null?new ArrayList<ContractBasicinfo>():contractBasicinfoMapper.getPageInfo(param);
	}


	@Override
	public ArrayList<SearchBean> getCurrentParam(HashMap param) {
		// TODO Auto-generated method stub
		return contractBasicinfoMapper.getCurrentParam(param)==null?new ArrayList<SearchBean>():contractBasicinfoMapper.getCurrentParam(param);
	}


	@Override
	public void addParam(Map param) throws RuntimeException {
		// TODO Auto-generated method stub
		Integer count=contractBasicinfoMapper.check_param(param);
		if(count==null || count==0){
		 contractBasicinfoMapper.addParam(param);
		}else{
			throw new RuntimeException("操作失败,不可重复添加");
		}
	}


	@Override
	public void delParam(Map param) throws RuntimeException {
		// TODO Auto-generated method stub
		contractBasicinfoMapper.delParam(param);
	}


	@Override
	public ArrayList<SearchBean> getCurrentParamForSearch(HashMap param) {
		// TODO Auto-generated method stub
		return contractBasicinfoMapper.getCurrentParamForSearch(param);
	}


	@Override
	public void upParam(Map param) throws RuntimeException {
		// TODO Auto-generated method stub
		contractBasicinfoMapper.upParam(param);
//		contractBasicinfoMapper.upParam_common(param);
	}


	@Override
	public List<ContractBasicinfo> find_by_cb() {
		return contractBasicinfoMapper.find_by_cb();
	}


	@Override
	public void update_cb_validity(Map<String, Object> param) throws Exception {
		contractBasicinfoMapper.update_cb_validity(param);
	}


	@Override
	public List<Map<String, Object>> findZZFWFList(String ym, String client_code) {
		return contractBasicinfoMapper.findZZFWFList(ym, client_code);
	}


	@Override
	public List<Map<String, Object>> findCCFList(String ym, String client_code) {
		return contractBasicinfoMapper.findCCFList(ym, client_code);
	}


	@Override
	public List<Map<String, Object>> findHCFList(String ym, String client_code) {
		return contractBasicinfoMapper.findHCFList(ym, client_code);
	}


	@Override
	public List<Map<String, Object>> findWlPoolList(String ym, String client_code) {
		// TODO Auto-generated method stub
		return contractBasicinfoMapper.findWlPoolList(ym, client_code);
	}


	@Override
	public List<Map<String, Object>> findDBFList(String yy, String mm, String client_code) {
		return contractBasicinfoMapper.findDBFList(yy,mm, client_code);
	}
	
	@Override
	public List<Map<String, Object>> findSectionDBF(Map<String, Object> param) {
		return contractBasicinfoMapper.findSectionDBF(param);
	}


	@Override
	public List<ContractBasicinfo> CsToCBID(String contract_owner) {
		return contractBasicinfoMapper.CsToCBID(contract_owner);
	}

	@Override
	public List<Map<String, Object>> set_SQL(String sql) {
		return contractBasicinfoMapper.set_SQL(sql);
	}

	@Override
	public int get_count(String sql) {
		return contractBasicinfoMapper.get_count(sql);
	}


}
