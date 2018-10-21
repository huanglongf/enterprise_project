package com.bt.lmis.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.bt.base.redis.RedisUtils;
import com.bt.lmis.balance.controller.form.BalanceErrorLogParam;
import com.bt.lmis.code.ServiceSupport;
import com.bt.lmis.dao.BalanceErrorLogMapper;
import com.bt.lmis.dao.ExpressContractConfigMapper;
import com.bt.lmis.dao.ExpressContractMapper;
import com.bt.lmis.dao.InsuranceECMapper;
import com.bt.lmis.dao.JbpcExMapper;
import com.bt.lmis.dao.JipaoConditionExMapper;
import com.bt.lmis.dao.ManagementECMapper;
import com.bt.lmis.dao.PricingFormulaMapper;
import com.bt.lmis.dao.SpecialServiceExMapper;
import com.bt.lmis.dao.TotalFreightDiscountMapper;
import com.bt.lmis.dao.TransportProductTypeMapper;
import com.bt.lmis.dao.TransportVendorMapper;
import com.bt.lmis.dao.WaybillDiscountExMapper;
import com.bt.lmis.model.BalanceErrorLog;
import com.bt.lmis.model.ExpressContractConfig;
import com.bt.lmis.model.InsuranceEC;
import com.bt.lmis.model.JibanpaoConditionEx;
import com.bt.lmis.model.JipaoConditionEx;
import com.bt.lmis.model.ManagementEC;
import com.bt.lmis.model.SpecialServiceEx;
import com.bt.lmis.model.TotalFreightDiscount;
import com.bt.lmis.model.WaybillDiscountEx;
import com.bt.lmis.model.YFSettlementVo;
import com.bt.lmis.page.QueryResult;
import com.bt.lmis.service.ExpressContractService;
import com.bt.lmis.utils.ExpressBalanceUtils;
import com.bt.utils.CommonUtils;
import com.bt.utils.SessionUtils;

/**
 * @Title:ExpressContractServiceImpl
 * @Description: TODO(快递合同ServiceImpl)  
 * @author Ian.Huang 
 * @date 2016年6月23日上午9:45:12
 */
@Service
public class ExpressContractServiceImpl<T> extends ServiceSupport<T> implements ExpressContractService<T>{
	
	@Autowired
	private BalanceErrorLogMapper<T> balanceErrorLogMapper;
	@Autowired
	private WaybillDiscountExMapper<T> waybillDiscountMapper;
	@Autowired
	private ManagementECMapper<T> managementECMapper;
	@Autowired
	private SpecialServiceExMapper<T> specialServiceExMapper;
	@Autowired
	private InsuranceECMapper<T> insuranceECMapper;
	@Autowired
	private TotalFreightDiscountMapper<T> totalFreightDiscountMapper;
	@Autowired
	private TransportProductTypeMapper<T> transportProductTypeMapper;
	@Autowired
	private PricingFormulaMapper<T> pFExMapper;
	@Autowired
	private JbpcExMapper<T> jbpcExMapper;
	@Autowired
    private TransportVendorMapper<T> transportVendorMapper;
	@Autowired
	private ExpressContractConfigMapper<T> expressContractConfigMapper;
	@Autowired
	private ExpressContractMapper<T> expressContractMapper;
	@Autowired
	private JipaoConditionExMapper jipaoConditionExMapper;
	
	@Override
	public JSONObject deleteWaybillDiscount(HttpServletRequest request, JSONObject result) throws Exception {
		result = new JSONObject();
		if(waybillDiscountMapper.delWD(Integer.parseInt(request.getParameter("id").toString()))!=0){
			result.put("result_code", "SUCCESS");
			result.put("result_content", "删除成功！");
			List<Map<String, Object>> wDList =  waybillDiscountMapper.selectAllWD(
					Integer.parseInt(request.getParameter("con_id").toString()),
					request.getParameter("belong_to").toString(),
					null
					);
			// 判断计费公式是否有记录
			if(wDList.size() != 0) {
				result.put("wDListShow", wDListShow(wDList));
				result.put("result_null_wD", "false");
			} else {
				result.put("result_null_wD", "true");
			}
		} else {
			result.put("result_code", "FAILURE");
			result.put("result_content", "删除失败,失败原因:该条记录不存在！");	
		}
		return result;
	}
	
	@Override
	public JSONObject saveWaybillDiscount(HttpServletRequest request, JSONObject result) throws Exception {
		result = new JSONObject();
		Integer con_id = Integer.parseInt(request.getParameter("con_id").toString());
		String belong_to = null;
		if(CommonUtils.checkExistOrNot(request.getParameter("belong_to"))){
			belong_to = request.getParameter("belong_to");
		}
		String product_type = null;
		if(CommonUtils.checkExistOrNot(request.getParameter("product_type"))){
			product_type = request.getParameter("product_type");
		}
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("con_id", con_id);
		param.put("belong_to", belong_to);
		param.put("product_type", "");
		//　是否存在产品类型
		if(CommonUtils.checkExistOrNot(product_type)){
			// 是否已存在所有类型规则记录
			Integer n = waybillDiscountMapper.findAllOrNot(con_id, belong_to);
			// 当产品类型为所有类型时，检验合同下是否存在除了所有类型以外的其他产品类型
			if(product_type.equals("ALL") && n == 0 && waybillDiscountMapper.judgeUnique(param) != 0 ){
				result.put("result_code", "FAILURE");
				result.put("result_content", "新增运费折扣启用条件失败,失败原因:产品类型重复！");
				return result;
			}
			// 当产品类型不为所有类型时，检验合同下是否存在所有类型
			if(!product_type.equals("ALL") && n != 0){
				result.put("result_code", "FAILURE");
				result.put("result_content", "新增运费折扣启用条件失败,失败原因:产品类型重复！");
				return result;
			}
		}
		param.put("product_type", product_type);
		// 获取条件
		List<Map<String, Object>> regionList = waybillDiscountMapper.selectRegion(param);
		// 条件区间不重叠校验
		Integer compare_1 = Integer.parseInt(request.getParameter("compare_1"));
		Integer compare_2 = Integer.parseInt(request.getParameter("compare_2"));
		BigDecimal num_1 = new BigDecimal(request.getParameter("num_1"));
		BigDecimal num_2 = null;
		if(request.getParameterMap().containsKey("num_2")) {
			num_2 = new BigDecimal(request.getParameter("num_2"));
		}
		int flag = 0;
		BigDecimal num_1_existing = null;
		BigDecimal num_2_existing = null;
		Integer compare_1_existing = null;
		Integer compare_2_existing = null;
		for(int i=0;i < regionList.size();i++){
			param = regionList.get(i);
			num_1_existing = new BigDecimal(param.get("num_1").toString());
			if(CommonUtils.checkExistOrNot(param.get("num_2"))){
				num_2_existing = new BigDecimal(param.get("num_2").toString());
				if(!CommonUtils.checkExistOrNot(num_2)){
					num_2 = CommonUtils.getMax(
							new BigDecimal[]{num_1, num_1_existing, num_2_existing}
							).add(new BigDecimal(1));
					flag = 1;
				}
			} else {
				if(!CommonUtils.checkExistOrNot(num_2)){
					num_2=num_2_existing=CommonUtils.getMax(
							new BigDecimal[]{num_1, num_1_existing}
							).add(new BigDecimal(1));
					flag = 1;
				} else {
					num_2_existing = CommonUtils.getMax(
							new BigDecimal[]{num_1, num_1_existing, num_2}
							).add(new BigDecimal(1));
				}
			}
			compare_1_existing = Integer.parseInt(param.get("compare_1").toString());
			compare_2_existing = Integer.parseInt(param.get("compare_2").toString());
			if(num_2.compareTo(num_1_existing) == -1 || num_1.compareTo(num_2_existing) == 1){
				if(flag==1){
					num_2 = null;
				}
				continue;
			} else if(num_2.compareTo(num_1_existing) == 0){
				if(compare_2==3&&1==compare_1_existing){
					result.put("result_code", "FAILURE");
					result.put("result_content", "新增运费折扣启用条件失败,失败原因:产品类型下阶梯区间重叠！");
					return result;
				}
				if(flag==1){
					num_2 = null;
				}
				continue;
			} else if(num_1.compareTo(num_2_existing) == 0){
				if(compare_1==1&&3==compare_2_existing){
					result.put("result_code", "FAILURE");
					result.put("result_content", "新增运费折扣启用条件失败,失败原因:产品类型下阶梯区间重叠！");
					return result;
				}
				if(flag==1){
					num_2 = null;
				}
				continue;
			} else {
				result.put("result_code", "FAILURE");
				result.put("result_content", "新增运费折扣启用条件失败,失败原因:产品类型下阶梯区间重叠！");
				return result;
			}
		}
		WaybillDiscountEx wD = new WaybillDiscountEx();
		wD.setCompare_1(compare_1);
		wD.setNum_1(num_1);
		wD.setUom_1(Integer.parseInt(request.getParameter("uom_1")));
		wD.setRel(Integer.parseInt(request.getParameter("rel").toString()));
		wD.setCompare_2(compare_2);
		wD.setNum_2(num_2);
		wD.setUom_2(Integer.parseInt(request.getParameter("uom_2")));
		wD.setCon_id(con_id);
		wD.setBelong_to(belong_to);
		wD.setProduct_type(product_type);
		wD.setDiscount(new BigDecimal(request.getParameter("discount")));
		wD.setDiscount_uom(Integer.parseInt(request.getParameter("discount_uom")));
		// 获取当前操作用户
		wD.setCreate_by(SessionUtils.getEMP(request).getUsername());
		waybillDiscountMapper.addWaybillDiscount(wD);
		// 总运费折扣展现	
		List<Map<String, Object>> wDList =  waybillDiscountMapper.selectAllWD(wD.getCon_id(), wD.getBelong_to(), null);
		// 判断计费公式是否有记录
		if(wDList.size() != 0) {
			result.put("wDListShow", wDListShow(wDList));
			result.put("result_code", "SUCCESS");
			result.put("result_content", "新增运费折扣启用条件成功！");
		} else {
			result.put("result_code", "FAILURE");
			result.put("result_content", "查询运费折扣启用条件失败,未能获取新增运费折扣启用条件及其他！");
		}
		return result;
	}
	
	public List<Map<String, Object>> wDListShow(List<Map<String, Object>> wDList){
		List<Map<String, Object>> wDListShow = new ArrayList<Map<String,Object>>();
		Map<String, Object> row = null;
		Map<String, Object> wD = null;
		for(int i =0;i< wDList.size();i++){
			wD = wDList.get(i);
			row = new HashMap<String, Object>();
			row.put("no", i+1);
			row.put("id", wD.get("id"));
			if(!CommonUtils.checkExistOrNot(wD.get("product_type"))){
				row.put("product_type_name", "无");
			} else {
				if(wD.get("product_type").equals("ALL")){
					row.put("product_type_name", "所有类型");
				} else {
					row.put("product_type_name", wD.get("product_type_name"));
				}
			}
			StringBuffer region = new StringBuffer();
			if(Integer.parseInt(wD.get("compare_1").toString())==0){
				region.append("(");
			} else if(Integer.parseInt(wD.get("compare_1").toString())==1){
				region.append("[");
			}
			if(CommonUtils.checkExistOrNot(wD.get("num_1"))){
				region.append(wD.get("num_1"));
			} else {
				region.append("-∞");
			}
			region.append(",");
			if(CommonUtils.checkExistOrNot(wD.get("num_2"))){
				region.append(wD.get("num_2"));
			} else {
				region.append("+∞");
			}
			if(Double.parseDouble(wD.get("compare_2").toString())==2){
				region.append(")");
			} else if(Integer.parseInt(wD.get("compare_2").toString())==3){
				region.append("]");
			}
			row.put("region", region);
			row.put("discount", wD.get("discount") + "%");
			wDListShow.add(row);
		}
		return wDListShow;
	}
	
	@Override
	public JSONObject loadWaybillDiscount(HttpServletRequest request, JSONObject result) throws Exception {
		result = new JSONObject();
		String belong_to = null;
		if(CommonUtils.checkExistOrNot(request.getParameter("belong_to").toString())){
			belong_to = request.getParameter("belong_to").toString();
		}
		// 总运费折扣展现	
		List<Map<String, Object>> wDList =  waybillDiscountMapper.selectAllWD(
				Integer.parseInt(request.getParameter("con_id").toString()),
				belong_to,
				null
				);
		if(wDList.size() != 0) {
			result.put("wDListShow", wDListShow(wDList));
			result.put("result_null_wD", "false");
		} else {
			result.put("result_null_wD", "true");
		}
		return result;
	}
	
	@Override
	public JSONObject checkUniqueValidity(HttpServletRequest request, JSONObject result) throws Exception {
		result= new JSONObject();
		result.put("result_code", "SUCCESS");
		// 封装参数
		Map<String, Object> param= CommonUtils.getParamMap(request);
		// 检测该合同类型/承运商下是否存在有效合同
		List<Map<String, Object>> eCs= expressContractMapper.findValidContract(param);
		if(eCs.size() != 0 && !(Integer.parseInt(eCs.get(0).get("id").toString()) == Integer.parseInt(param.get("con_id").toString()))){
			result= new JSONObject();
			result.put("result_code", "FAILURE");
			if(Integer.parseInt(param.get("contractType").toString()) == 1){
				param.put("contractType", "快递");
			} else if(Integer.parseInt(param.get("contractType").toString()) == 2){
				param.put("contractType", "物流");
			}
			result.put("result_content", "此"+ param.get("contractType").toString() +"下已存在有效合同，故不能生效！");
			return result;
		}
		//　当该快递合同配置不完整
		if(Integer.parseInt(param.get("contractType").toString()) == 1 
				&& 
				ExpressBalanceUtils.checkValidity(
				expressContractConfigMapper.findECC(
				Integer.parseInt(param.get("con_id").toString()), 
				param.get("belong_to").toString()
				)
				)
				){
			result.put("result_code", "FAILURE");
			result.put("result_content", "快递合同配置不完整，故不能生效！");
			return result;
		}
		return result;
	}
	
	@Override
	public JSONObject judgeExist(HttpServletRequest request, JSONObject result) throws Exception {
		result = new JSONObject();
		String type = request.getParameter("type");
		if(type.equals("JBPC")){
			result.put("num", jbpcExMapper.findAllJBPCondition(
					Integer.parseInt(request.getParameter("con_id").toString()),
					request.getParameter("belong_to").toString()).size());
		} else if(type.equals("PF")) {
			result.put("num", pFExMapper.findAllPF(
					Integer.parseInt(request.getParameter("con_id")),
					request.getParameter("belong_to")).size());
		} else if(type.equals("WD")) {
			result.put("num", waybillDiscountMapper.selectAllWD(
					Integer.parseInt(request.getParameter("con_id").toString()),
					request.getParameter("belong_to"),
					null
					).size());
		} else if(type.equals("TFD")) {
			result.put("num", totalFreightDiscountMapper.selectAllTFD(
					Integer.parseInt(request.getParameter("con_id").toString()),
					request.getParameter("belong_to"),
					null
					).size());
		} else if(type.equals("Insurance")) {
			result.put("num", insuranceECMapper.selectAllIEC(
					Integer.parseInt(request.getParameter("con_id")),
					request.getParameter("belong_to").toString()
					).size());
		} else if(type.equals("Man")) {
			result.put("num", managementECMapper.selectAllManEC(
					Integer.parseInt(request.getParameter("con_id")), null
					).size());
		}
		return result;
	}
	
	@Override
	public JSONObject loadManEC(HttpServletRequest request, JSONObject result) throws Exception {
		result = new JSONObject();
		// 管理费规则展现	
		List<Map<String, Object>> manECList =  managementECMapper.selectAllManEC(
				Integer.parseInt(request.getParameter("con_id").toString()), null
				);
		// 判断管理费规则是否有记录
		if(manECList.size() != 0) {
			result.put("manECListShow", manECListShow(manECList));
			result.put("result_null_manEC", "false");
		} else {
			result.put("result_null_manEC", "true");
		}
		return result;
	}

	@Override
	public JSONObject deleteManEC(HttpServletRequest request, JSONObject result) throws Exception {
		result = new JSONObject();
		if(managementECMapper.delManEC(Integer.parseInt(request.getParameter("id").toString()))!=0){
			result.put("result_code", "SUCCESS");
			result.put("result_content", "删除成功！");
			// 管理费规则展现	
			List<Map<String, Object>> manECList =  managementECMapper.selectAllManEC(
					Integer.parseInt(request.getParameter("con_id").toString()), null
					);
			// 判断计费公式是否有记录
			if(manECList.size() != 0) {
				result.put("manECListShow", manECListShow(manECList));
				result.put("result_null_manEC", "false");
			} else {
				result.put("result_null_manEC", "true");
			}
			
		} else {
			result.put("result_code", "FAILURE");
			result.put("result_content", "删除失败,失败原因:该条记录不存在！");	
		}
		return result;
	}
	
	@Override
	public List<Map<String, Object>> manECListShow(List<Map<String, Object>> manECList) {
		List<Map<String, Object>> manECListShow = new ArrayList<Map<String,Object>>();
		Map<String, Object> row = null;
		Map<String, Object> manEC = null;
		for(int i =0;i< manECList.size();i++){
			manEC = manECList.get(i);
			row = new HashMap<String, Object>();
			row.put("no", i + 1);
			row.put("id", manEC.get("id"));
			if(manEC.get("belong_to").toString().equals("ALL")){
				row.put("carrier", "所有已维护承运商");
			} else {
				row.put("carrier", manEC.get("carrier").toString());
			}
			StringBuffer com = new StringBuffer();
			if(Boolean.parseBoolean(manEC.get("freight").toString())){
				com.append("运费+");
			}
			if(Boolean.parseBoolean(manEC.get("insurance").toString())){
				com.append("保价费+");
			}
			if(Boolean.parseBoolean(manEC.get("cod").toString())){
				com.append("特殊服务费（COD）+");
			}
			if(Boolean.parseBoolean(manEC.get("delegated_pickup").toString())){
				com.append("特殊服务费（委托取件费）+");
			}
			com.deleteCharAt(com.length()-1);
			row.put("com", com);
			switch(Integer.parseInt(manEC.get("ladder_type").toString())){
			case 1:
				row.put("ladder_type","无阶梯");
				break;
			case 2:
				row.put("ladder_type","总费用阶梯价");
				break;
			case 3:
				row.put("ladder_type","超出部分阶梯价");
				break;
			}
			if(Integer.parseInt(manEC.get("ladder_type").toString()) != 1){
				StringBuffer region = new StringBuffer();
				if(Integer.parseInt(manEC.get("compare_1").toString())==0){
					region.append("(");
				} else if(Integer.parseInt(manEC.get("compare_1").toString())==1){
					region.append("[");
				}
				if(CommonUtils.checkExistOrNot(manEC.get("num_1"))){
					region.append(manEC.get("num_1"));
				} else {
					region.append("-∞");
				}
				region.append(",");
				if(CommonUtils.checkExistOrNot(manEC.get("num_2"))){
					region.append(manEC.get("num_2"));
				} else {
					region.append("+∞");
				}
				if(Integer.parseInt(manEC.get("compare_2").toString())==2){
					region.append(")");
				} else if(Integer.parseInt(manEC.get("compare_2").toString())==3){
					region.append("]");
				}
				row.put("region", region);
				
			} else {
				row.put("region", "");
			}
			row.put("charge_percent", manEC.get("charge_percent")+"%");
			manECListShow.add(row);
		}
		return manECListShow;
	}
	
	@Override
	public JSONObject saveManEC(HttpServletRequest request, JSONObject result) throws Exception {
		result = new JSONObject();
		Map<String, Object> combination = new HashMap<String, Object>();
		int con_id = Integer.parseInt(request.getParameter("con_id").toString());
		String belong_to = request.getParameter("belong_to");
		combination.put("con_id", con_id);
		if(managementECMapper.checkCombination(combination) != 0){
			// 合同下管理费已存在规则
			combination.put("belong_to", "ALL");
			if(managementECMapper.checkCombination(combination) != 0){
				// 如合同下承运商为所有承运商已存在
				if(!belong_to.equals("ALL")){
					// 新保存的承运商不为ALL
					result.put("result_code", "FAILURE");
					result.put("result_content", "新增管理费规则失败,失败原因:所有承运商已选！");
					return result;
				}
			} else {
				// 不存在所有承运商
				if(belong_to.equals("ALL")){
					// 新保存的承运商为ALL
					result.put("result_code", "FAILURE");
					result.put("result_content", "新增管理费规则失败,失败原因:已存在指定快递规则！");
					return result;
				}
			}
		}
		Boolean freight = Boolean.parseBoolean(request.getParameter("freight").toString());
		Boolean insurance = Boolean.parseBoolean(request.getParameter("insurance").toString());
		Boolean special_service = Boolean.parseBoolean(request.getParameter("special_service").toString());
		Boolean cod = Boolean.parseBoolean(request.getParameter("cod").toString());
		Boolean delegated_pickup = Boolean.parseBoolean(request.getParameter("delegated_pickup").toString());
		Integer ladder_type = Integer.parseInt(request.getParameter("ladder_type").toString());
		Integer compare_1 =null;
		BigDecimal num_1 = null;
		Integer compare_2 = null;
		BigDecimal num_2 = null;
		if(ladder_type != 1){
			compare_1 = Integer.parseInt(request.getParameter("compare_1").toString());
			num_1 = new BigDecimal(request.getParameter("num_1"));
			compare_2 = Integer.parseInt(request.getParameter("compare_2").toString());
			if(request.getParameterMap().containsKey("num_2")) {
				num_2 = new BigDecimal(request.getParameter("num_2"));
			}
		}
		combination.put("belong_to", belong_to);
		combination.put("freight", freight);
		combination.put("insurance", insurance);
		combination.put("special_service", special_service);
		combination.put("cod", cod);
		combination.put("delegated_pickup", delegated_pickup);
		combination.put("ladder_type", null);
		// 阶梯组合是否已存在
		if(managementECMapper.checkCombination(combination) != 0){
			// 已存在-阶梯类型是否已存在
			combination.put("ladder_type", ladder_type);
			if(managementECMapper.checkCombination(combination) != 0){
				// 已存在-是否无阶梯
				if(ladder_type == 1){
					// 是-报错
					result.put("result_code", "FAILURE");
					result.put("result_content", "新增管理费规则失败,失败原因:同一组合下阶梯类型数据重复！");
					return result;
				} else {
					// 否-区间是否重叠校验
					// 获取阶梯
					List<Map<String, Object>> regionList = managementECMapper.selectRegion(combination);
					int flag = 0;
					BigDecimal num_1_existing = null;
					BigDecimal num_2_existing = null;
					Integer compare_1_existing = null;
					Integer compare_2_existing = null;
					for(int i=0; i < regionList.size(); i++){
						combination = regionList.get(i);
						num_1_existing = new BigDecimal(combination.get("num_1").toString());
						if(CommonUtils.checkExistOrNot(combination.get("num_2"))){
							num_2_existing = new BigDecimal(combination.get("num_2").toString());
							if(!CommonUtils.checkExistOrNot(num_2)){
								num_2 = CommonUtils.getMax(
										new BigDecimal[]{num_1, num_1_existing, num_2_existing}
										).add(new BigDecimal(1));
								flag = 1;
							}
						} else {
							if(!CommonUtils.checkExistOrNot(num_2)){
								num_2=num_2_existing=CommonUtils.getMax(
										new BigDecimal[]{num_1, num_1_existing}
										).add(new BigDecimal(1));
								flag = 1;
							} else {
								num_2_existing=CommonUtils.getMax(
										new BigDecimal[]{num_1, num_1_existing, num_2}
										).add(new BigDecimal(1));
							}
						}
						compare_1_existing = Integer.parseInt(combination.get("compare_1").toString());
						compare_2_existing = Integer.parseInt(combination.get("compare_2").toString());
						if(num_2.compareTo(num_1_existing) == -1 || num_1.compareTo(num_2_existing) == 1){
							// 通过-成功
							if(flag==1){
								num_2 = null;
							}
							continue;
						} else if(num_2 .compareTo(num_1_existing) == 0) {
							if(compare_2==3 && 1==compare_1_existing){
								result.put("result_code", "FAILURE");
								result.put("result_content", "新增管理费规则失败,失败原因:组合下阶梯区间重叠！");
								return result;
							} else {
								if(flag==1){
									num_2 = null;
								}
								continue;
							} 
						} else if(num_1.compareTo(num_2_existing) == 0){
							if(compare_1==1 && 3==compare_2_existing){
								result.put("result_code", "FAILURE");
								result.put("result_content", "新增管理费规则失败,失败原因:组合下阶梯区间重叠！");
								return result;
							} else {
								if(flag==1){
									num_2 = null;
								}
								continue;
							} 
						} else {
							// 不通过-报错
							result.put("result_code", "FAILURE");
							result.put("result_content", "新增管理费规则失败,失败原因:组合下阶梯区间重叠！");
							return result;
						}
					}
				}
			} else {
				//　不存在-报错
				result.put("result_code", "FAILURE");
				result.put("result_content", "新增管理费规则失败,失败原因:同一组合下阶梯类型唯一！");
				return result;
			}
		} else {
			// 不存在-是否有收费项已存在
			if(freight){
				combination.put("freight", freight);
				combination.put("insurance", null);
				combination.put("special_service", null);
				combination.put("cod", null);
				combination.put("delegated_pickup", null);
				if(managementECMapper.checkCombination(combination)!=0){
					// 有-报错
					result.put("result_code", "FAILURE");
					result.put("result_content", "新增管理费规则失败,失败原因:运费已存在于管理费规则！");
					return result;
				}
				//　无－成功
			} else if(insurance){
				combination.put("freight", null);
				combination.put("insurance", insurance);
				combination.put("special_service", null);
				combination.put("cod", null);
				combination.put("delegated_pickup", null);
				if(managementECMapper.checkCombination(combination)!=0){
					// 有-报错
					result.put("result_code", "FAILURE");
					result.put("result_content", "新增管理费规则失败,失败原因:保价费已存在于管理费规则！");
					return result;
				}
				//　无－成功
			} else if(cod){
				combination.put("freight", null);
				combination.put("insurance", null);
				combination.put("special_service", special_service);
				combination.put("cod", cod);
				combination.put("delegated_pickup", null);
				if(managementECMapper.checkCombination(combination)!=0){
					// 有-报错
					result.put("result_code", "FAILURE");
					result.put("result_content", "新增管理费规则失败,失败原因:特殊服务费-cod已存在于管理费规则！");
					return result;
				}
				//　无－成功
			} else if(delegated_pickup){
				combination.put("freight", null);
				combination.put("insurance", null);
				combination.put("special_service", special_service);
				combination.put("cod", null);
				combination.put("delegated_pickup", delegated_pickup);
				if(managementECMapper.checkCombination(combination)!=0){
					// 有-报错
					result.put("result_code", "FAILURE");
					result.put("result_content", "新增管理费规则失败,失败原因:特殊服务费-委托取件费已存在于管理费规则！");
					return result;
				}
				//　无－成功
			}
		}
		ManagementEC manEC = new ManagementEC();
		manEC.setCon_id(con_id);
		manEC.setBelong_to(belong_to);
		manEC.setFreight(freight);
		manEC.setInsurance(insurance);
		manEC.setSpecial_service(special_service);
		manEC.setCod(cod);
		manEC.setDelegated_pickup(delegated_pickup);
		manEC.setLadder_type(ladder_type);
		if(ladder_type != 1){
			manEC.setCompare_1(compare_1);
			manEC.setNum_1(num_1);
			manEC.setUom_1(Integer.parseInt(request.getParameter("uom_1").toString()));
			manEC.setRel(Integer.parseInt(request.getParameter("rel").toString()));
			manEC.setCompare_2(compare_2);
			manEC.setNum_2(num_2);
			manEC.setUom_2(Integer.parseInt(request.getParameter("uom_2").toString()));
		}
		manEC.setCharge_percent(new BigDecimal(request.getParameter("charge_percent").toString()));
		manEC.setCharge_percent_uom(Integer.parseInt(request.getParameter("charge_percent_uom").toString()));
		manEC.setCreate_by(SessionUtils.getEMP(request).getEmployee_number());
		managementECMapper.addManagementEC(manEC);
		// 管理费规则展现	
		List<Map<String, Object>> manECList =  managementECMapper.selectAllManEC(manEC.getCon_id(), null);
		// 判断保价费规则是否有记录
		if(manECList.size() != 0) {
			result.put("manECListShow", manECListShow(manECList));
			result.put("result_code", "SUCCESS");
			result.put("result_content", "新增管理费规则成功！");
		} else {
			result.put("result_code", "FAILURE");
			result.put("result_content", "查询管理费规则失败,未能获取新增管理费规则及其他！");
		}
		return result;
	}
	
	@Override
	public JSONObject loadSSE(HttpServletRequest request, JSONObject result) throws Exception {
		result = new JSONObject();
		SpecialServiceEx sSE = specialServiceExMapper.findRecord(
				Integer.parseInt(request.getParameter("con_id").toString()),
				request.getParameter("belong_to")
				);
		if(CommonUtils.checkExistOrNot(sSE)){
			result.put("sSE", sSE);
			result.put("result_null_sSE", "false");
		} else {
			result.put("result_null_sSE", "true");
		}
		return result;
	}
	
	@Override
	public JSONObject deleteInsuranceEC(HttpServletRequest request, JSONObject result) throws Exception {
		result = new JSONObject();
		if(insuranceECMapper.delIEC(Integer.parseInt(request.getParameter("id").toString()))!=0){
			result.put("result_code", "SUCCESS");
			result.put("result_content", "删除成功！");
			List<Map<String, Object>> iECList =  insuranceECMapper.selectAllIEC(
					Integer.parseInt(request.getParameter("con_id").toString()),
					request.getParameter("belong_to").toString());
			// 判断计费公式是否有记录
			if(iECList.size() != 0) {
				result.put("iECListShow", iECListShow(iECList));
				result.put("result_null_iEC", "false");
			} else {
				result.put("result_null_iEC", "true");
			}
			
		} else {
			result.put("result_code", "FAILURE");
			result.put("result_content", "删除失败,失败原因:该条记录不存在！");	
		}
		return result;
	}
	
	@Override
	public JSONObject loadInsuranceEC(HttpServletRequest request, JSONObject result) throws Exception {
		result = new JSONObject();
		// 保价费规则展现	
		List<Map<String, Object>> iECList =  insuranceECMapper.selectAllIEC(
				Integer.parseInt(request.getParameter("con_id").toString()),
				request.getParameter("belong_to")
				);
		// 判断保价费规则是否有记录
		if(iECList.size() != 0) {
			result.put("iECListShow", iECListShow(iECList));
			result.put("result_null_iEC", "false");
		} else {
			result.put("result_null_iEC", "true");
		}
		return result;
	}

	@Override
	public List<Map<String, Object>> iECListShow(List<Map<String, Object>> iECList) {
		List<Map<String, Object>> iECListShow = new ArrayList<Map<String,Object>>();
		Map<String, Object> row = null;
		Map<String, Object> iEC = null;
		for(int i =0;i< iECList.size();i++){
			iEC = iECList.get(i);
			row = new HashMap<String, Object>();
			row.put("no", i+1);
			row.put("id", iEC.get("id"));
			if(!CommonUtils.checkExistOrNot(iEC.get("product_type"))){
				row.put("product_type_name", "无");
			} else {
				if(iEC.get("product_type").equals("ALL")){
					row.put("product_type_name", "所有类型");
				} else {
					row.put("product_type_name", iEC.get("product_type_name"));
				}
			}
			switch(Integer.parseInt(iEC.get("ladder_type").toString())){
			case 1:
				row.put("ladder_type","无阶梯");
				break;
			case 2:
				row.put("ladder_type","总费用阶梯价");
				break;
			case 3:
				row.put("ladder_type","超出部分阶梯价");
				break;
			}
			if(Integer.parseInt(iEC.get("ladder_type").toString()) != 1){
				row.put("charge_min", "");
				StringBuffer region = new StringBuffer();
				if(Integer.parseInt(iEC.get("compare_1").toString())==0){
					region.append("(");
				} else if(Integer.parseInt(iEC.get("compare_1").toString())==1){
					region.append("[");
				}
				if(CommonUtils.checkExistOrNot(iEC.get("num_1"))){
					region.append(iEC.get("num_1"));
				} else {
					region.append("-∞");
				}
				region.append(",");
				if(CommonUtils.checkExistOrNot(iEC.get("num_2"))){
					region.append(iEC.get("num_2"));
				} else {
					region.append("+∞");
				}
				if(Integer.parseInt(iEC.get("compare_2").toString())==2){
					region.append(")");
				} else if(Integer.parseInt(iEC.get("compare_2").toString())==3){
					region.append("]");
				}
				row.put("region", region);
				if(CommonUtils.checkExistOrNot(iEC.get("charge_percent"))){
					row.put("charge_percent", iEC.get("charge_percent")+"%");
					row.put("charge", "");
				} else {
					row.put("charge_percent", "");
					row.put("charge", iEC.get("charge"));
				}
				
			} else {
				if(Integer.parseInt(iEC.get("charge_min_flag").toString())==0){
					row.put("charge_min", iEC.get("charge_min").toString());
				} else {
					row.put("charge_min", "");
				}
				row.put("region", "");
				row.put("charge_percent", iEC.get("charge_percent")+"%");
				row.put("charge", "");
			}
			iECListShow.add(row);
		}
		return iECListShow;
	}

	@Override
	public JSONObject saveiEC(HttpServletRequest request, JSONObject result) throws Exception {
		result = new JSONObject();
		Integer con_id = Integer.parseInt(request.getParameter("con_id").toString());
		String belong_to = request.getParameter("belong_to");
		String product_type = null;
		if(CommonUtils.checkExistOrNot(request.getParameter("product_type"))){
			product_type = request.getParameter("product_type");
		}
		Integer ladder_type = Integer.parseInt(request.getParameter("ladder_type").toString());
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("con_id", con_id);
		param.put("belong_to", belong_to);
		param.put("product_type", "");
		param.put("ladder_type", "");
		//　是否存在产品类型
		if(CommonUtils.checkExistOrNot(product_type)){
			// 当产品类型为所有类型时，检验合同下是否存在其他产品类型
			if(product_type.equals("ALL")
					&& insuranceECMapper.judgeUnique(param) != 0
					&& insuranceECMapper.findAllOrNot(con_id, belong_to) == 0){
				result.put("result_code", "FAILURE");
				result.put("result_content", "新增保价费规则失败,失败原因:产品类型重复！");
				return result;
			}
			//　检验合同下是否存在所有类型
			if(!product_type.equals("ALL")
					&& insuranceECMapper.findAllOrNot(con_id, belong_to) != 0){
				result.put("result_code", "FAILURE");
				result.put("result_content", "新增保价费规则失败,失败原因:产品类型重复！");
				return result;
			}
		}
		// 校验产品类型与阶梯类型组合唯一性
		param.put("product_type", product_type);
		if((insuranceECMapper.judgeUnique(param)!=0)){
			// 无阶梯－同一产品类型下只能维护一条数据
			//　有阶梯区间－同一产品类型下可以维护多个阶梯区间
			param.put("ladder_type", ladder_type);
			if(insuranceECMapper.judgeUnique(param)==0){
				result.put("result_code", "FAILURE");
				result.put("result_content", "新增保价费规则失败,失败原因:同一产品类型下阶梯类型唯一！");
				return result;
			} else {
				if(ladder_type == 1){
					//　无阶梯
					result.put("result_code", "FAILURE");
					result.put("result_content", "新增保价费规则失败,失败原因:同一产品类型下阶梯类型数据重复！");
					return result;
				}
			}
		}
		InsuranceEC iFD = new InsuranceEC();
		if(ladder_type != 1){
			// 阶梯区间不重叠校验
			Integer compare_1 = Integer.parseInt(request.getParameter("compare_1").toString());
			Integer compare_2 = Integer.parseInt(request.getParameter("compare_2").toString());
			BigDecimal num_1 = new BigDecimal(request.getParameter("num_1"));
			BigDecimal num_2 = null;
			if(request.getParameterMap().containsKey("num_2")) {
				num_2 = new BigDecimal(request.getParameter("num_2"));
			}
			// 获取阶梯
			List<Map<String, Object>> regionList = insuranceECMapper.selectRegion(param);
			int flag = 0;
			BigDecimal num_1_existing = null;
			BigDecimal num_2_existing = null;
			Integer compare_1_existing = null;
			Integer compare_2_existing = null;
			for(int i=0; i < regionList.size(); i++){
				param = regionList.get(i);
				num_1_existing = new BigDecimal(param.get("num_1").toString());
				if(CommonUtils.checkExistOrNot(param.get("num_2"))){
					num_2_existing = new BigDecimal(param.get("num_2").toString());
					if(!CommonUtils.checkExistOrNot(num_2)){
						num_2 = CommonUtils.getMax(
								new BigDecimal[]{num_1, num_1_existing, num_2_existing}
								).add(new BigDecimal(1));
						flag = 1;
					}
				} else {
					if(!CommonUtils.checkExistOrNot(num_2)){
						num_2=num_2_existing=CommonUtils.getMax(
								new BigDecimal[]{num_1, num_1_existing}
								).add(new BigDecimal(1));
						flag = 1;
					} else {
						num_2_existing=CommonUtils.getMax(
								new BigDecimal[]{num_1, num_1_existing, num_2}
								).add(new BigDecimal(1));
					}
				}
				compare_1_existing = Integer.parseInt(param.get("compare_1").toString());
				compare_2_existing = Integer.parseInt(param.get("compare_2").toString());
				if(num_2.compareTo(num_1_existing) == -1 || num_1.compareTo(num_2_existing) == 1){
					if(flag==1){
						num_2 = null;
					}
					continue;
				} else if((num_2.compareTo(num_1_existing) == 0)){
					if(compare_2==3&&1==compare_1_existing){
						result.put("result_code", "FAILURE");
						result.put("result_content", "新增保价费规则失败,失败原因:产品类型下阶梯区间重叠！");
						return result;
					}
					if(flag==1){
						num_2 = null;
					}
					continue;
				} else if(num_1.compareTo(num_2_existing) == 0){
					if(compare_1==1&&3==compare_2_existing){
						result.put("result_code", "FAILURE");
						result.put("result_content", "新增保价费规则失败,失败原因:产品类型下阶梯区间重叠！");
						return result;
					}
					if(flag==1){
						num_2 = null;
					}
					continue;
				} else {
					result.put("result_code", "FAILURE");
					result.put("result_content", "新增保价费规则失败,失败原因:产品类型下阶梯区间重叠！");
					return result;
				}
			}
			iFD.setCompare_1(compare_1);
			iFD.setNum_1(num_1);
			iFD.setUom_1(Integer.parseInt(request.getParameter("uom_1").toString()));
			iFD.setRel(Integer.parseInt(request.getParameter("rel").toString()));
			iFD.setCompare_2(compare_2);
			iFD.setNum_2(num_2);
			iFD.setUom_2(Integer.parseInt(request.getParameter("uom_2").toString()));
			if(request.getParameterMap().containsKey("charge_percent")){
				iFD.setCharge_percent(new BigDecimal(request.getParameter("charge_percent")));
				iFD.setCharge_percent_uom(Integer.parseInt(request.getParameter("charge_percent_uom").toString()));
				iFD.setCharge(null);
				iFD.setCharge_uom(null);
			} else if(request.getParameterMap().containsKey("charge")){
				iFD.setCharge_percent(null);
				iFD.setCharge_percent_uom(null);
				iFD.setCharge(new BigDecimal(request.getParameter("charge")));
				iFD.setCharge_uom(Integer.parseInt(request.getParameter("charge_uom").toString()));
			}
			iFD.setCharge_min_flag(null);
			iFD.setCharge_min(null);
			iFD.setCharge_min_uom(null);
		} else {
			iFD.setCompare_1(null);
			iFD.setNum_1(null);
			iFD.setUom_1(null);
			iFD.setRel(null);
			iFD.setCompare_2(null);
			iFD.setNum_2(null);
			iFD.setUom_2(null);
			iFD.setCharge_percent(new BigDecimal(request.getParameter("charge_percent")));
			iFD.setCharge_percent_uom(Integer.parseInt(request.getParameter("charge_percent_uom").toString()));
			if(request.getParameterMap().containsKey("charge_min")){
				iFD.setCharge_min_flag(0);
				iFD.setCharge_min(new BigDecimal(request.getParameter("charge_min")));
				iFD.setCharge_min_uom(Integer.parseInt(request.getParameter("charge_min_uom").toString()));
			} else {
				iFD.setCharge_min_flag(1);
				iFD.setCharge_min(null);
				iFD.setCharge_min_uom(null);
			}
			iFD.setCharge(null);
			iFD.setCharge_uom(null);
		}
		iFD.setCon_id(con_id);
		iFD.setBelong_to(belong_to);
		iFD.setProduct_type(product_type);
		iFD.setLadder_type(ladder_type);
		iFD.setCreate_by(SessionUtils.getEMP(request).getUsername());
		insuranceECMapper.addInsuranceEC(iFD);
		// 保价费规则展现	
		List<Map<String, Object>> iECList =  insuranceECMapper.selectAllIEC(iFD.getCon_id(), iFD.getBelong_to());
		// 判断保价费规则是否有记录
		if(iECList.size() != 0) {
			result.put("iECListShow", iECListShow(iECList));
			result.put("result_code", "SUCCESS");
			result.put("result_content", "新增保价费规则成功！");
		} else {
			result.put("result_code", "FAILURE");
			result.put("result_content", "查询保价费规则失败,未能获取新增保价费规则及其他！");
		}
		return result;
	}
	
	@Override
	public JSONObject deleteTotalFreightDiscount(HttpServletRequest request, JSONObject result) throws Exception {
		result = new JSONObject();
		if(totalFreightDiscountMapper.delTFD(Integer.parseInt(request.getParameter("id").toString()))!=0){
			result.put("result_code", "SUCCESS");
			result.put("result_content", "删除成功！");
			List<Map<String, Object>> tFDList =  totalFreightDiscountMapper.selectAllTFD(
					Integer.parseInt(request.getParameter("con_id").toString()),
					request.getParameter("belong_to").toString(),
					null
					);
			// 判断计费公式是否有记录
			if(tFDList.size() != 0) {
				result.put("tFDListShow", tFDListShow(tFDList));
				result.put("result_null_tFD", "false");
			} else {
				result.put("result_null_tFD", "true");
			}
		} else {
			result.put("result_code", "FAILURE");
			result.put("result_content", "删除失败,失败原因:该条记录不存在！");	
		}
		return result;
	}
	
	@Override
	public JSONObject loadTotalFreightDiscount(HttpServletRequest request, JSONObject result) throws Exception {
		result = new JSONObject();
		String belong_to = null;
		if(CommonUtils.checkExistOrNot(request.getParameter("belong_to").toString())){
			belong_to = request.getParameter("belong_to").toString();
		}
		// 总运费折扣展现	
		List<Map<String, Object>> tFDList =  totalFreightDiscountMapper.selectAllTFD(
				Integer.parseInt(request.getParameter("con_id").toString()),
				belong_to,
				null
				);
		// 判断计费公式是否有记录
		if(tFDList.size() != 0) {
			result.put("tFDListShow", tFDListShow(tFDList));
			result.put("result_null_tFD", "false");
		} else {
			result.put("result_null_tFD", "true");
		}
		return result;
	}
	
	public List<Map<String, Object>> tFDListShow(List<Map<String, Object>> tFDList){
		List<Map<String, Object>> tFDListShow = new ArrayList<Map<String,Object>>();
		Map<String, Object> row = null;
		Map<String, Object> tFD = null;
		for(int i =0;i< tFDList.size();i++){
			tFD = tFDList.get(i);
			row = new HashMap<String, Object>();
			row.put("no", i+1);
			row.put("id", tFD.get("id"));
			if(Byte.parseByte(tFD.get("insurance_contain").toString()) == 0){
				row.put("insurance_contain", "是");
			} else {
				row.put("insurance_contain", "否");
			}
			if(!CommonUtils.checkExistOrNot(tFD.get("product_type"))){
				row.put("product_type_name", "无");
			} else {
				if(tFD.get("product_type").equals("ALL")){
					row.put("product_type_name", "所有类型");
				} else {
					row.put("product_type_name", tFD.get("product_type_name"));
				}
			}
			switch(Integer.parseInt(tFD.get("ladder_type").toString())){
			case 1:
				row.put("ladder_type","无阶梯");
				break;
			case 2:
				row.put("ladder_type","总运费超过部分阶梯");
				break;
			case 3:
				row.put("ladder_type","总运费总折扣阶梯");
				break;
			case 4:
				row.put("ladder_type","单量折扣阶梯");
				break;
			}
			if(Integer.parseInt(tFD.get("ladder_type").toString()) != 1){
				StringBuffer region = new StringBuffer();
				if(Integer.parseInt(tFD.get("compare_1").toString())==0){
					region.append("(");
				} else if(Integer.parseInt(tFD.get("compare_1").toString())==1){
					region.append("[");
				}
				if(CommonUtils.checkExistOrNot(tFD.get("num_1"))){
					region.append(tFD.get("num_1"));
				} else {
					region.append("-∞");
				}
				region.append(",");
				if(CommonUtils.checkExistOrNot(tFD.get("num_2"))){
					region.append(tFD.get("num_2"));
				} else {
					region.append("+∞");
				}
				if(Double.parseDouble(tFD.get("compare_2").toString())==2){
					region.append(")");
				} else if(Integer.parseInt(tFD.get("compare_2").toString())==3){
					region.append("]");
				}
				row.put("region", region);
			} else {
				row.put("region", "");
			}
			row.put("discount", tFD.get("discount")+"%");
			tFDListShow.add(row);
		}
		return tFDListShow;
	}
	
	@Override
	public JSONObject saveTotalFreightDiscount(HttpServletRequest request, JSONObject result) throws Exception {
		result = new JSONObject();
		Integer con_id = Integer.parseInt(request.getParameter("con_id").toString());
		String belong_to = null;
		if(CommonUtils.checkExistOrNot(request.getParameter("belong_to"))){
			belong_to = request.getParameter("belong_to");
		}
		String product_type = null;
		if(CommonUtils.checkExistOrNot(request.getParameter("product_type"))){
			product_type = request.getParameter("product_type");
		}
		Integer ladder_type = Integer.parseInt(request.getParameter("ladder_type").toString());
		Byte insurance_contain = Byte.parseByte(request.getParameter("insurance_contain"));
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("con_id", con_id);
		param.put("belong_to", belong_to);
		param.put("product_type", "");
		param.put("ladder_type", "");
		param.put("insurance_contain", "");
		//　是否存在产品类型
		if(CommonUtils.checkExistOrNot(product_type)){
			// 是否已存在所有类型保价费规则记录
			Integer n = totalFreightDiscountMapper.findAllOrNot(con_id, belong_to);
			// 当产品类型为所有类型时，检验合同下是否存在除了所有类型以外的其他产品类型
			if(product_type.equals("ALL") && n == 0 && totalFreightDiscountMapper.judgeUnique(param) != 0 ){
				result.put("result_code", "FAILURE");
				result.put("result_content", "新增总运费折扣失败,失败原因:产品类型重复！");
				return result;
			}
			// 当产品类型不为所有类型时，检验合同下是否存在所有类型
			if(!product_type.equals("ALL") && n != 0){
				result.put("result_code", "FAILURE");
				result.put("result_content", "新增总运费折扣失败,失败原因:产品类型重复！");
				return result;
			}
		}
		// 校验产品类型与阶梯类型组合唯一性
		param.put("product_type", product_type);
		if((totalFreightDiscountMapper.judgeUnique(param) != 0)){
			// 无阶梯－同一产品类型下只能维护一条数据
			// 有阶梯区间－同一产品类型下可以维护多个阶梯区间
			param.put("ladder_type", ladder_type);
			if(totalFreightDiscountMapper.judgeUnique(param) == 0){
				result.put("result_code", "FAILURE");
				result.put("result_content", "新增总运费折扣失败,失败原因:同一产品类型下阶梯类型唯一！");
				return result;
			} else {
				if(ladder_type == 1){
					// 无阶梯
					result.put("result_code", "FAILURE");
					result.put("result_content", "新增总运费折扣失败,失败原因:同一产品类型下阶梯类型数据重复！");
					return result;
				} 
			}
			// 校验同一产品类型与阶梯类型组合下，保价费包含相同
			param.put("insurance_contain", insurance_contain);
			if(totalFreightDiscountMapper.judgeUnique(param) == 0){
				result.put("result_code", "FAILURE");
				result.put("result_content", "新增总运费折扣失败,失败原因:同一产品类型与阶梯类型组合下保价费包含唯一！");
				return result;
			}
		}
		TotalFreightDiscount tFD = new TotalFreightDiscount();
		if(ladder_type != 1){
			// 阶梯区间不重叠校验
			Integer compare_1 = Integer.parseInt(request.getParameter("compare_1"));
			Integer compare_2 = Integer.parseInt(request.getParameter("compare_2"));
			BigDecimal num_1 = new BigDecimal(request.getParameter("num_1"));
			BigDecimal num_2 = null;
			if(request.getParameterMap().containsKey("num_2")) {
				num_2 = new BigDecimal(request.getParameter("num_2"));
			}
			// 获取阶梯
			List<Map<String, Object>> regionList = totalFreightDiscountMapper.selectRegion(param);
			int flag = 0;
			BigDecimal num_1_existing = null;
			BigDecimal num_2_existing = null;
			Integer compare_1_existing = null;
			Integer compare_2_existing = null;
			for(int i=0;i < regionList.size();i++){
				param = regionList.get(i);
				num_1_existing = new BigDecimal(param.get("num_1").toString());
				if(CommonUtils.checkExistOrNot(param.get("num_2"))){
					num_2_existing = new BigDecimal(param.get("num_2").toString());
					if(!CommonUtils.checkExistOrNot(num_2)){
						num_2 = CommonUtils.getMax(
								new BigDecimal[]{num_1, num_1_existing, num_2_existing}
								).add(new BigDecimal(1));
						flag = 1;
					}
				} else {
					if(!CommonUtils.checkExistOrNot(num_2)){
						num_2=num_2_existing=CommonUtils.getMax(
								new BigDecimal[]{num_1, num_1_existing}
								).add(new BigDecimal(1));
						flag = 1;
					} else {
						num_2_existing = CommonUtils.getMax(
								new BigDecimal[]{num_1, num_1_existing, num_2}
								).add(new BigDecimal(1));
					}
				}
				compare_1_existing = Integer.parseInt(param.get("compare_1").toString());
				compare_2_existing = Integer.parseInt(param.get("compare_2").toString());
				if(num_2.compareTo(num_1_existing) == -1 || num_1.compareTo(num_2_existing) == 1){
					if(flag==1){
						num_2 = null;
					}
					continue;
				} else if(num_2.compareTo(num_1_existing) == 0){
					if(compare_2==3&&1==compare_1_existing){
						result.put("result_code", "FAILURE");
						result.put("result_content", "新增总运费折扣失败,失败原因:产品类型下阶梯区间重叠！");
						return result;
					}
					if(flag==1){
						num_2 = null;
					}
					continue;
				} else if(num_1.compareTo(num_2_existing) == 0){
					if(compare_1==1&&3==compare_2_existing){
						result.put("result_code", "FAILURE");
						result.put("result_content", "新增总运费折扣失败,失败原因:产品类型下阶梯区间重叠！");
						return result;
					}
					if(flag==1){
						num_2 = null;
					}
					continue;
				} else {
					result.put("result_code", "FAILURE");
					result.put("result_content", "新增总运费折扣失败,失败原因:产品类型下阶梯区间重叠！");
					return result;
				}
			}
			tFD.setCompare_1(compare_1);
			tFD.setNum_1(num_1);
			tFD.setUom_1(Integer.parseInt(request.getParameter("uom_1")));
			tFD.setRel(Integer.parseInt(request.getParameter("rel").toString()));
			tFD.setCompare_2(compare_2);
			tFD.setNum_2(num_2);
			tFD.setUom_2(Integer.parseInt(request.getParameter("uom_2")));
		} else {
			tFD.setCompare_1(null);
			tFD.setNum_1(null);
			tFD.setUom_1(null);
			tFD.setRel(null);
			tFD.setCompare_2(null);
			tFD.setNum_2(null);
			tFD.setUom_2(null);
		}
		tFD.setCon_id(con_id);
		tFD.setBelong_to(belong_to);
		tFD.setInsurance_contain(insurance_contain);
		tFD.setProduct_type(product_type);
		tFD.setLadder_type(ladder_type);
		tFD.setDiscount(new BigDecimal(request.getParameter("discount")));
		tFD.setDiscount_uom(Integer.parseInt(request.getParameter("discount_uom")));
		// 获取当前操作用户
		tFD.setCreate_by(SessionUtils.getEMP(request).getUsername());
		totalFreightDiscountMapper.addTotalFreightDiscount(tFD);
		// 总运费折扣展现	
		List<Map<String, Object>> tFDList =  totalFreightDiscountMapper.selectAllTFD(tFD.getCon_id(), tFD.getBelong_to(), null);
		// 判断计费公式是否有记录
		if(tFDList.size() != 0) {
			result.put("tFDListShow", tFDListShow(tFDList));
			result.put("result_code", "SUCCESS");
			result.put("result_content", "新增总运费折扣成功！");
		} else {
			result.put("result_code", "FAILURE");
			result.put("result_content", "查询总运费折扣失败,未能获取新增总运费折扣及其他！");
		}
		return result;
	}
	
	@Override
	public JSONObject deletePricingFormula(HttpServletRequest request, JSONObject result) throws Exception {
		result = new JSONObject();
		if(pFExMapper.delPF(Integer.parseInt(request.getParameter("id").toString()))!=0){
			result.put("result_code", "SUCCESS");
			result.put("result_content", "删除成功！");
			List<Map<String, Object>> pfList =  pFExMapper.findAllPF(
					Integer.parseInt(request.getParameter("con_id").toString()),
					request.getParameter("belong_to").toString()
					);
			if(pfList.size() != 0) {
				result.put("pfListShow", pfListShow(pfList));
				result.put("result_null_pf", "false");
				
			} else {
				result.put("result_null_pf", "true");
			}
		} else {
			result.put("result_code", "FAILURE");
			result.put("result_content", "删除失败,失败原因:该条记录不存在！");	
		}
		return result;
	}
	
	@Override
	public JSONObject getPricingFormula(Map<String, Object> param, JSONObject result) throws Exception {
		result = new JSONObject();
		result.put("pf", pFExMapper.getPF(Integer.parseInt(param.get("id").toString())));
		result.put("result_code", "SUCCESS");
		return result;
	}
	
	@Override
	public List<Map<String, Object>> pfListShow(List<Map<String, Object>> pfList){
		List<Map<String, Object>> pfListShow = new ArrayList<Map<String,Object>>();
		Map<String, Object> row = null;
		for(int i = 0;i < pfList.size();i++) {
			row = new HashMap<String, Object>();
			row.put("no", i + 1 + "");
			row.put("id", pfList.get(i).get("id") + "");
			row.put("pricing_formula_name", "公式" + pfList.get(i).get("pricing_formula") + "：" + pfList.get(i).get("pricing_formula_name"));
			pfListShow.add(row);
		}
		return pfListShow;
	}
	
	@Override
	public JSONObject savePricingFormula(HttpServletRequest request, JSONObject result) throws Exception {
		// 封装参数
		Map<String, Object> param = CommonUtils.getParamMap(request);
		// 按联合主键查询记录
		Integer id = null;
		Map<String, Object> record = pFExMapper.getRecord(param);
		if(CommonUtils.checkExistOrNot(record)){
			id = Integer.parseInt(record.get("id").toString());
		}
		String s = "";
		// 判断计费公式ID是否存在
		if(!CommonUtils.checkExistOrNot(param.get("id"))){
			// 新增
			// 若记录存在则表明计费公式冲突
			if(id != null){
				result = new JSONObject();
				result.put("result_code", "FAILURE");
				result.put("result_content", "新增计费公式失败,失败原因:该计费公式已存在！");
				return result;
			} else {
				switch(Integer.parseInt(param.get("pricing_formula").toString())){
				case 1:
					param.put("pricing_formula_name", "总费用向上取整");
					break;
				case 2:
					param.put("pricing_formula_name", "计费重量向上取整");
					break;
				case 3:
					param.put("pricing_formula_name", "首重价格+续重价格（不向上取整）");
					param.put("exact_decimal", null);
					break;
				case 4:
					param.put("pricing_formula_name", "EMS-首重0.5kg续重0.5kg报价");
					param.put("exact_decimal", null);
					break;
				}
				// 新增
				pFExMapper.addPricingFormula(param);
				s = "新增";
				result = new JSONObject();
				result.put("id", pFExMapper.getRecord(param));
			}
		} else {
			// 更新
			if(id !=null && (!String.valueOf(id).equals(param.get("id")+""))){
				result = new JSONObject();
				result.put("result_code", "FAILURE");
				result.put("result_content", "更新计费公式失败,失败原因:计费公式冲突！");
				return result;
			} else {
				switch(Integer.parseInt(param.get("pricing_formula").toString())){
				case 1: 
					param.put("pricing_formula_name", "总费用向上取整");
					break;
				case 2:
					param.put("pricing_formula_name", "计费重量向上取整");
					break;
				case 3:
					param.put("pricing_formula_name", "首重价格+续重价格（不向上取整）");
					param.put("exact_decimal", null);
					break;
				case 4:
					param.put("pricing_formula_name", "EMS-首重0.5kg续重0.5kg报价");
					param.put("exact_decimal", null);
					break;
				}
				// 更新
				pFExMapper.updatePricingFormula(param);
				s = "更新";
				result = new JSONObject();
			}
		}
		// 计费公式展现	
		List<Map<String, Object>> pfList =  pFExMapper.findAllPF(
				Integer.parseInt(param.get("con_id").toString()),
				param.get("belong_to").toString()
				);
		// 判断计费公式是否有记录
		if(pfList.size() != 0) {
			result.put("pfListShow", pfListShow(pfList));
			result.put("result_code", "SUCCESS");
			result.put("result_content", s + "计费公式成功！");
		} else {
			result.put("result_code", "FAILURE");
			result.put("result_content", "查询计费公式失败,未能获取新增计费公式及其他！");
		}
		return result;
	}
	
	@Override
	public JSONObject loadFreight(HttpServletRequest request, JSONObject result) throws Exception {
		result = new JSONObject();
		List<JibanpaoConditionEx> jbpcList 
			=  jbpcExMapper.findAllJBPCondition(
					Integer.parseInt(request.getParameter("con_id").toString()),
					request.getParameter("belong_to").toString());
		if(jbpcList.size() != 0) {
			result.put("jbpcListShow", jbpcListShow(jbpcList));
			result.put("result_null_jbpc", "false");
		} else {
			result.put("result_null_jbpc", "true");
		}
		List<JipaoConditionEx> jqpcList 
		=  selectAll(
				Integer.parseInt(request.getParameter("con_id").toString()),
				request.getParameter("belong_to").toString());
		if(jqpcList.size() != 0) {
			result.put("jqpcListShow", jqpcListShow(jqpcList));
			result.put("result_null_jqpc", "false");
		} else {
			result.put("result_null_jqpc", "true");
		}
		List<Map<String, Object>> pfList =  pFExMapper.findAllPF(
				Integer.parseInt(request.getParameter("con_id").toString()),
				request.getParameter("belong_to")
				);
		if(pfList.size() != 0) {
			result.put("pfListShow", pfListShow(pfList));
			result.put("result_null_pf", "false");
			
		} else {
			result.put("result_null_pf", "true");
		}
		return result;
	}
	
	@Override
	public JSONObject deleteJBPConditon(HttpServletRequest request, JSONObject result) throws Exception {
		result = new JSONObject();
		if(jbpcExMapper.delJBPConditon(Integer.parseInt(request.getParameter("id").toString()))!=0){
			result.put("result_code", "SUCCESS");
			result.put("result_content", "删除成功！");
			List<JibanpaoConditionEx> jbpcList 
			=  jbpcExMapper.findAllJBPCondition(
					Integer.parseInt(request.getParameter("con_id").toString()),
					request.getParameter("belong_to").toString());
			if(jbpcList.size() != 0) {
				result.put("result_null", "false");
				result.put("jbpcListShow", jbpcListShow(jbpcList));
			} else {
				result.put("result_null", "true");
			}
		} else {
			result.put("result_code", "FAILURE");
			result.put("result_content", "删除失败,失败原因:该条记录不存在！");	
		}
		return result;
	}
	@Override
	public JSONObject deleteJQPConditon(HttpServletRequest request, JSONObject result) throws Exception {
		result = new JSONObject();
		if(jipaoConditionExMapper.delJBPConditon(Integer.parseInt(request.getParameter("id").toString()))!=0){
			result.put("result_code", "SUCCESS");
			result.put("result_content", "删除成功！");
			List<JipaoConditionEx> jbpcList = jipaoConditionExMapper.selectAll(
					Integer.parseInt(request.getParameter("con_id").toString()),
					request.getParameter("belong_to").toString());
			String key = "JQPC"+request.getParameter("con_id").toString()+"_"+request.getParameter("belong_to").toString();
			String value = JSON.toJSONString(jbpcList);
			RedisUtils.set(key, value);
			if(jbpcList.size() != 0) {
				result.put("result_null", "false");
				result.put("jqpcListShow", jqpcListShow(jbpcList));
			} else {
				result.put("result_null", "true");
			}
		} else {
			result.put("result_code", "FAILURE");
			result.put("result_content", "删除失败,失败原因:该条记录不存在！");	
		}
		return result;
	}
	
	@Override
	public List<Map<String, String>> jbpcListShow(List<JibanpaoConditionEx> jbpcList) {
		
		List<Map<String, String>> jbpcListShow = new ArrayList<Map<String,String>>();
		Map<String, String> row = null;
		StringBuffer content = null;
		for(int i = 0; i < jbpcList.size(); i++){
			row = new HashMap<String, String>();
			row.put("no", i + 1 + "");
			row.put("id", jbpcList.get(i).getId() + "");
			content = new StringBuffer("当");
			switch(jbpcList.get(i).getAttr()){
			case 0:
				content.append("长");
				break;
			case 1:
				content.append("宽");
				break;
			case 2:
				content.append("高");
				break;
			}
			switch(jbpcList.get(i).getCompare_mark()){
			case 0:
				content.append("大于");
				break;
			case 1:
				content.append("大等于");
				break;
			case 2:
				content.append("小于");
				break;
			case 3:
				content.append("小等于");
				break;
			}
			content.append(jbpcList.get(i).getCon());
			switch(jbpcList.get(i).getUom()){
			case 0:
				content.append("CM");
				break;
			}
			row.put("content", content.toString());
			jbpcListShow.add(row);
		}
		
		return jbpcListShow;
		
	}
	
	private List<Map<String, String>> jqpcListShow(List<JipaoConditionEx> jbpcList) {
		
		List<Map<String, String>> jbpcListShow = new ArrayList<Map<String,String>>();
		Map<String, String> row = null;
		StringBuffer content = null;
		for(int i = 0; i < jbpcList.size(); i++){
			row = new HashMap<String, String>();
			row.put("no", i + 1 + "");
			row.put("id", jbpcList.get(i).getId() + "");
			content = new StringBuffer("当");
			switch(jbpcList.get(i).getAttr()){
			case 0:
				content.append("长");
				break;
			case 1:
				content.append("宽");
				break;
			case 2:
				content.append("高");
				break;
			}
			switch(jbpcList.get(i).getCompareMark()){
			case 0:
				content.append("大于");
				break;
			case 1:
				content.append("大等于");
				break;
			case 2:
				content.append("小于");
				break;
			case 3:
				content.append("小等于");
				break;
			}
			content.append(jbpcList.get(i).getCon());
			switch(jbpcList.get(i).getUom()){
			case 0:
				content.append("CM");
				break;
			}
			row.put("content", content.toString());
			jbpcListShow.add(row);
		}
		
		return jbpcListShow;
		
	}
	@Override
	public JSONObject addJBPConditon(HttpServletRequest request, JSONObject result) throws Exception {
		JibanpaoConditionEx jbpc  = new JibanpaoConditionEx();
		jbpc.setCon_id(Integer.parseInt(request.getParameter("con_id").toString()));
		jbpc.setBelong_to(request.getParameter("belong_to"));
		jbpc.setAttr(Integer.parseInt(request.getParameter("attr").toString()));
		jbpc.setCompare_mark(Integer.parseInt(request.getParameter("compare_mark").toString()));
		jbpc.setCon(new BigDecimal(request.getParameter("con").toString()));
		jbpc.setUom(Integer.parseInt(request.getParameter("uom").toString()));
		//如果新增条件无重复记录
		if(jbpcExMapper.judgeUnique(jbpc) == 0){
			jbpc.setCreate_by(SessionUtils.getEMP(request).getUsername());
			jbpcExMapper.addJBPConditon(jbpc);
			result = new JSONObject();
			// 计半抛条件展现	
			List<JibanpaoConditionEx> jbpcList =  jbpcExMapper.findAllJBPCondition(jbpc.getCon_id(), jbpc.getBelong_to());
			// 判断计半抛条件是否有记录
			if(jbpcList.size() != 0) {
				result.put("jbpcListShow", jbpcListShow(jbpcList));
				result.put("result_code", "SUCCESS");
				result.put("result_content", "新增计半抛条件成功！");
			} else {
				result.put("result_code", "FAILURE");
				result.put("result_content", "查询计半抛条件失败,未能获取新增计半抛条件及其他！");
			}
		} else {
			result = new JSONObject();
			result.put("result_code", "FAILURE");
			result.put("result_content", "新增计半抛条件失败,失败原因:计半抛条件已存在！");	
		}
		return result;
	}
	@Override
	public JSONObject addJQPConditon(HttpServletRequest request, JSONObject result) throws Exception {
		JipaoConditionEx jbpc  = new JipaoConditionEx();
		jbpc.setConId(Integer.parseInt(request.getParameter("con_id").toString()));
		jbpc.setBelongTo(request.getParameter("belong_to"));
		jbpc.setAttr(Integer.parseInt(request.getParameter("attr").toString()));
		jbpc.setCompareMark(Integer.parseInt(request.getParameter("compare_mark").toString()));
		jbpc.setCon(new BigDecimal(request.getParameter("con").toString()));
		jbpc.setUom(Integer.parseInt(request.getParameter("uom").toString()));
		//如果新增条件无重复记录
		if(jipaoConditionExMapper.judgeUnique(jbpc) == 0){
			jbpc.setCreateBy(SessionUtils.getEMP(request).getUsername());
			jipaoConditionExMapper.insert(jbpc);
			result = new JSONObject();
			List<JipaoConditionEx> jbpcList =  jipaoConditionExMapper.selectAll(jbpc.getConId(), jbpc.getBelongTo());
			String key = "JQPC"+request.getParameter("con_id").toString()+"_"+request.getParameter("belong_to").toString();
			String value = JSON.toJSONString(jbpcList);
			RedisUtils.set(key, value);
			if(jbpcList.size() != 0) {
				result.put("jqpcListShow", jqpcListShow(jbpcList));
				result.put("result_code", "SUCCESS");
				result.put("result_content", "新增计抛条件成功！");
			} else {
				result.put("result_code", "FAILURE");
				result.put("result_content", "查询计抛条件失败,未能获取新增计抛条件及其他！");
			}
		} else {
			result = new JSONObject();
			result.put("result_code", "FAILURE");
			result.put("result_content", "新增计抛条件失败,失败原因:计抛条件已存在！");	
		}
		return result;
	}
	
	@Override
	public JSONObject saveECC(HttpServletRequest request, JSONObject result) throws Exception {
		result = new JSONObject();
		ExpressContractConfig eCC = new ExpressContractConfig();
		int type = Integer.parseInt(request.getParameter("type").toString());
		if(type == 1){
			eCC.setWeight_method(Integer.parseInt(request.getParameter("weight").toString()));
			if(Integer.parseInt(request.getParameter("weight").toString()) == 3){
				eCC.setPercent(new BigDecimal(request.getParameter("percent").toString()));
				eCC.setPercent_uom(Integer.parseInt(request.getParameter("percent_uom").toString()));
			}
			eCC.setWaybill_discount(Boolean.parseBoolean(request.getParameter("waybill_discount").toString()));
			eCC.setTotal_freight_discount(Boolean.parseBoolean(request.getParameter("total_freight_discount").toString()));
		}
		if(type == 2){
			eCC.setInsurance(Boolean.parseBoolean(request.getParameter("insurance").toString()));
			result.put("type", "2");
//			result.put("move", Byte.parseByte(request.getParameter("insurance").toString()));
		}
		if(type == 3){
			String belong_to = request.getParameter("belong_to");
			Boolean cod = Boolean.parseBoolean(request.getParameter("cod").toString());
			Boolean delegated_pickup_flag = Boolean.parseBoolean(request.getParameter("delegated_pickup_flag").toString());
			SpecialServiceEx sSE = null;
			if(cod || delegated_pickup_flag){
				sSE = new SpecialServiceEx();
				sSE.setBelong_to(belong_to);
				if(cod){
					int cod_charge_method = Integer.parseInt(request.getParameter("cod_charge_method").toString());
					sSE.setCod_charge_method(cod_charge_method);
					Byte charge_min_flag = Byte.parseByte(request.getParameter("charge_min_flag").toString());
					sSE.setCharge_min_flag(charge_min_flag);
					if(charge_min_flag == 0){
						sSE.setCharge_min(new BigDecimal(request.getParameter("charge_min").toString()));
						sSE.setCharge_min_uom(Integer.parseInt(request.getParameter("charge_min_uom").toString()));
					} 
					sSE.setPercent(new BigDecimal(request.getParameter("percent").toString()));
					sSE.setPercent_uom(Integer.parseInt(request.getParameter("percent_uom").toString()));
					if(cod_charge_method == 1){
						sSE.setAccurate_decimal_place(Integer.parseInt(request.getParameter("accurate_decimal_place").toString()));
						sSE.setParam_1(new BigDecimal(request.getParameter("param_1").toString()));
						sSE.setParam_1_uom(Integer.parseInt(request.getParameter("param_1_uom").toString()));
						sSE.setCharge_1(new BigDecimal(request.getParameter("charge_1").toString()));
						sSE.setCharge_1_uom(Integer.parseInt(request.getParameter("charge_1_uom").toString()));
					}
				}
				if(delegated_pickup_flag){
					sSE.setDelegated_pickup(new BigDecimal(request.getParameter("delegated_pickup").toString()));
					sSE.setDelegated_pickup_uom(Integer.parseInt(request.getParameter("delegated_pickup_uom").toString()));
				}
				String id= request.getParameter("sSE_id");
				if(CommonUtils.checkExistOrNot(id)){
					// 更新
					sSE.setId(Integer.parseInt(id));
					sSE.setUpdate_by(SessionUtils.getEMP(request).getUsername());
					specialServiceExMapper.updateSSEx(sSE, cod);
				} else {
					//新增
					sSE.setCon_id(Integer.parseInt(request.getParameter("con_id").toString()));
					sSE.setCreate_by(SessionUtils.getEMP(request).getUsername());
					specialServiceExMapper.addSSEx(sSE);
					result.put("sSE_id", sSE.getId());
				}
			}
			eCC.setCod(cod);
			eCC.setDelegated_pickup(delegated_pickup_flag);
			result.put("type", "3");
//			result.put("move1", cod);
//			result.put("move2", delegated_pickup_flag);
		}
		eCC.setId(Integer.parseInt(request.getParameter("id").toString()));
		eCC.setUpdate_by(SessionUtils.getEMP(request).getUsername());
		expressContractConfigMapper.updateExpressContractConfig(eCC);
		result.put("result_code", "SUCCESS");
		result.put("result_content", "快递合同配置成功！");
		return result;
	}
	
	@Override
	public ModelMap getEC(ModelMap map, HttpServletRequest request) throws Exception {
		String con_id = request.getParameter("id");
		if(null==con_id || Integer.valueOf(con_id)==0) {
			// 新增合同
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			map.put("contract_start", sdf.format(new Date()).toString());
			map.addAttribute("expressList", transportVendorMapper.findExpress());
			map.addAttribute("physicaldistributionList", transportVendorMapper.findPhysicaldistribution());
			
		} else if(null!=con_id || Integer.valueOf(con_id)!=0){
			// 编辑合同
			// 合同主信息
			Map<String, Object> expressContract = expressContractMapper.findById(Integer.valueOf(con_id));
			// 合同主体
			Map<String, Object> contractOwner = new HashMap<String, Object>();
			contractOwner.put("contractOwner", expressContract.get("contract_owner"));
			contractOwner.put("contractOwnerName", transportVendorMapper.findByCode(true, expressContract.get("contract_owner").toString()).getTransport_name());
			map.addAttribute("expressContract", expressContract);
			map.addAttribute("contractOwner", contractOwner);
			// 合同配置
			map.addAttribute("eCC", expressContractConfigMapper.findECC(Integer.valueOf(con_id), expressContract.get("contract_owner").toString()));
			// 产品类型
			map.addAttribute("productType", transportProductTypeMapper.findByTransportVendor(true, expressContract.get("contract_owner").toString()));
			
		}
		return map;
		
	}
	
	@Override
	public JSONObject saveECM(HttpServletRequest request, JSONObject result) throws Exception {
		// 封装参数
		Map<String, Object> param = CommonUtils.getParamMap(request);
		if(CommonUtils.checkExistOrNot(param.get("cb_contractValidity"))){
			// 检测该合同类型/承运商下是否存在有效合同
			List<Map<String, Object>> eCs = expressContractMapper.findValidContract(param);
			if(eCs.size() != 0 
					&& !(Integer.parseInt(eCs.get(0).get("id").toString())
					==Integer.parseInt(param.get("id").toString()))){
				result = new JSONObject();
				result.put("result_code", "FAILURE");
				if(Integer.parseInt(param.get("contractType").toString()) == 1){
					param.put("contractType", "快递");
				} else if(Integer.parseInt(param.get("contractType").toString()) == 2){
					param.put("contractType", "物流");
				}
				result.put("result_content", "新增合同主信息失败,失败原因:此"+ param.get("contractType").toString() +"下已存在有效合同！");
				return result;
			}
		}
		// 按联合主键查询记录
		Map<String, Object> record 
			= expressContractMapper.findByCnoAndCvsAndCtp(
					param.get("contractCode").toString(),
					param.get("contractType").toString(),
					param.get("contractVersion").toString());
		// 判断合同ID是否存在
		if(!CommonUtils.checkExistOrNot(param.get("id"))){
			// 新增
			// 若记录存在则表明合同主信息冲突
			if(record != null){
				result = new JSONObject();
				result.put("result_code", "FAILURE");
				result.put("result_content", "新增合同主信息失败,失败原因:合同主信息冲突！");	
			} else {
				// 拆分合同周期
				param.putAll(CommonUtils.spiltDateString((String)param.get("contractCycle")));
				// 按合同类型判断合同所属主体
				if(Integer.parseInt(param.get("contractType").toString()) == 1){
					param.put("belongTo", param.get("express"));
				} else {
					param.put("belongTo", param.get("physicalDistribution"));
				}
				param.put("validity", "0");
				// 新增
				expressContractMapper.addECM(param);
				// 获取已新增信息
				record 
					= expressContractMapper.findByCnoAndCvsAndCtp(
							param.get("contractCode").toString(),
							param.get("contractType").toString(),
							param.get("contractVersion").toString());
				if(Integer.parseInt(record.get("contract_type").toString()) == 1){
					expressContractConfigMapper.initializeExpressContractConfig(
							Integer.parseInt(record.get("id").toString()), 
							record.get("contract_owner").toString(), 
							SessionUtils.getEMP(request).getUsername());
				}
				result = new JSONObject();
				result.put("result_code", "SUCCESS");
				result.put("result_content", "新增合同主信息成功！");
				result.put("id", record.get("id").toString());
			}
		} else {
			// 更新
			if(record!=null && (!(record.get("id")+"").equals(param.get("id")+""))){
				result = new JSONObject();
				result.put("result_code", "FAILURE");
				result.put("result_content", "更新合同主信息失败,失败原因:合同主信息冲突！");	
			} else {
				if(CommonUtils.checkExistOrNot(param.get("cb_contractValidity"))){
					param.put("validity", "1");
				} else {
					param.put("validity", "0");
				}
				// 更新
				expressContractMapper.updateECM(param);
				record 
					= expressContractMapper.findByCnoAndCvsAndCtp(
							param.get("contractCode").toString(),
							param.get("contractType").toString(),
							param.get("contractVersion").toString());
				result = new JSONObject();
				result.put("result_code", "SUCCESS");
				result.put("result_content", "更新合同主信息成功！");
				result.put("updateBy", record.get("update_user").toString());
				result.put("updateTime", record.get("update_time").toString());
			}
		}
		return result;
	}
	
	@Override
	public Long selectCount(Map<String, Object> param) throws Exception {
		return null;
	}

	@Override
	public void addBalanceErrorLog(BalanceErrorLog bEL) throws Exception {
		balanceErrorLogMapper.addBalanceErrorLog(bEL);
	}
	@Override
	public QueryResult<Map<String,Object>> selectByPram(BalanceErrorLogParam map) {
		QueryResult<Map<String,Object>> qr=new QueryResult<Map<String,Object>>();
		List<Map<String,Object>> list = balanceErrorLogMapper.selectByPram(map);
		qr.setResultlist(list);
		qr.setTotalrecord(balanceErrorLogMapper.totalCount(map));
		return qr;
	}
	
	@Override
	public int getCount(YFSettlementVo map) {
		return balanceErrorLogMapper.getCount(map);
	}
	
	@Override
	public Map<String, Object> findById(int con_id) {
		return expressContractMapper.findById(con_id);
	}

	/* (non-Javadoc)
	 * @see com.bt.lmis.service.ExpressContractService#query_export(com.bt.lmis.model.YFSettlementVo)
	 */
	@Override
	public List<Map<String, Object>> queryExport(YFSettlementVo map) {
		// TODO Auto-generated method stub
		return balanceErrorLogMapper.queryExport(map);
	}
	
	@Override
	public List<JipaoConditionEx> selectAll(int conId,String belongTo){
		String key = "JQPC"+conId+"_"+belongTo;
		if(RedisUtils.exist(key)){
			String value = RedisUtils.get(key);
			List<JipaoConditionEx> list = JSON.parseObject(value,new TypeReference<List<JipaoConditionEx>>() { });
			return list;
		}else{
			List<JipaoConditionEx> jqpcList = jipaoConditionExMapper.selectAll(conId,belongTo);
			if(CommonUtils.checkExistOrNot(jqpcList)){
				String value = JSON.toJSONString(jqpcList);
				RedisUtils.set(key, value);
			}
			return jqpcList;
		}
	}
}
