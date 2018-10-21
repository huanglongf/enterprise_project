package com.bt.lmis.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.bt.lmis.code.ServiceSupport;
import com.bt.lmis.dao.PackageChargeLadderMapper;
import com.bt.lmis.dao.PackageChargeMapper;
import com.bt.lmis.model.PackagePrice;
import com.bt.lmis.model.PackagePriceLadder;
import com.bt.lmis.service.PackageChargeService;
import com.bt.utils.CommonUtils;
import com.bt.utils.SessionUtils;

/**
 * @Title:PackageChargeServiceImpl
 * @Description: TODO(打包费ServiceImpl)
 * @author Ian.Huang 
 * @date 2016年7月4日下午4:26:48
 */
@Service
public class PackageChargeServiceImpl<T> extends ServiceSupport<T> implements PackageChargeService<T>{

	@Autowired
	private PackageChargeMapper<T> packageChargeMapper;
	@Autowired
	private PackageChargeLadderMapper<T> packageChargeLadderMapper;
	
	public PackageChargeLadderMapper<T> getPackageChargeLadderMapper(){
		return packageChargeLadderMapper;
	}
	
	@Override
	public PackagePrice getPackagePrice(int con_id) {
		return packageChargeMapper.findByConId(con_id);
	}
	
	@Override
	public JSONObject savePackagePrice(HttpServletRequest request, JSONObject result) throws Exception {
		result = new JSONObject();
		String id = request.getParameter("id");
		PackagePrice pP = new PackagePrice();
		pP.setCarrier_charge(request.getParameter("carrier_charge")!=null?
				Byte.parseByte(request.getParameter("carrier_charge").toString()):null);
		pP.setStorage(request.getParameter("storage")!=null?
				Byte.parseByte(request.getParameter("storage").toString()):null);
		pP.setOperation(request.getParameter("operation")!=null?
				Byte.parseByte(request.getParameter("operation").toString()):null);
		pP.setConsumable(request.getParameter("consumable")!=null?
				Byte.parseByte(request.getParameter("consumable").toString()):null);
		pP.setUnit_price(Double.parseDouble(null!=request.getParameter("unit_price")?request.getParameter("unit_price").toString():"0"));
		pP.setUnit_price_uom(Integer.parseInt(null!=request.getParameter("unit_price_uom")?request.getParameter("unit_price_uom").toString():"0"));
		pP.setInsurance(Integer.parseInt(null!=request.getParameter("insurance")?request.getParameter("insurance").toString():"0"));
		pP.setReturn_unit_price(Double.parseDouble(null!=request.getParameter("return_unit_price")?
				request.getParameter("return_unit_price").toString():"0"));
		pP.setReturn_unit_price_uom(Integer.parseInt(null!=request.getParameter("return_unit_price_uom")?request.getParameter("return_unit_price_uom").toString():"0"));
		pP.setDelegated_pickup_unit_price(Double.parseDouble(null!=request.getParameter("delegated_pickup_unit_price")?request.getParameter("delegated_pickup_unit_price").toString():"0"));
		pP.setDelegated_pickup_unit_price_uom(Integer.parseInt(null!=request.getParameter("delegated_pickup_unit_price_uom")?request.getParameter("delegated_pickup_unit_price_uom").toString():"0"));
		pP.setReturn_insurance(Integer.parseInt(null!=request.getParameter("return_insurance")?request.getParameter("return_insurance").toString():"0"));
		if(CommonUtils.checkExistOrNot(id)){
			// 更新
			pP.setId(Integer.parseInt(id));
			pP.setUpdate_by(SessionUtils.getEMP(request).getUsername());
			packageChargeMapper.updatePackageCharge(pP);
			result.put("result_code", "SUCCESS");
			result.put("result_content", "更新打包费成功！");
		} else {
			// 新增
			pP.setCon_id(Integer.parseInt(request.getParameter("con_id")));
			pP.setCreate_by(SessionUtils.getEMP(request).getUsername());
			packageChargeMapper.addPackageCharge(pP);
			result.put("id", pP.getId());
			result.put("result_code", "SUCCESS");
			result.put("result_content", "新增打包费成功！");
		}
		return result;
	}
	
	@Override
	public JSONObject delLadder(HttpServletRequest request, JSONObject result) throws Exception {
		result = new JSONObject();
		PackagePriceLadder pPL = packageChargeLadderMapper.findById(Integer.parseInt(request.getParameter("id").toString()));
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("con_id", pPL.getCon_id());
		param.put("return_flag", pPL.getReturn_flag());
		param.put("insurance", pPL.getInsurance());
		if(packageChargeLadderMapper.delLadder(Integer.parseInt(request.getParameter("id").toString()), SessionUtils.getEMP(request).getUsername())!=0){
			result.put("result_code", "SUCCESS");
			result.put("result_content", "删除成功！");
			result.put("path", param);
			List<Map<String, Object>> regionList =  packageChargeLadderMapper.loadRecords(param);
			// 判断计费公式是否有记录
			if(regionList.size() != 0) {
				result.put("regionListShow", regionListShow(regionList));
				result.put("result_null_region", "false");
			} else {
				result.put("result_null_region", "true");
			}
			
		} else {
			result.put("result_code", "FAILURE");
			result.put("result_content", "删除失败,失败原因:该条记录不存在！");	
		}
		return result;
	}
	
	@Override
	public JSONObject loadLadder(HttpServletRequest request, JSONObject result) throws Exception {
		result = new JSONObject();
		// 区间展现	
		List<Map<String, Object>> regionList =  packageChargeLadderMapper.loadRecords(CommonUtils.getParamMap(request));
		// 判断是否有记录
		if(regionList.size() != 0) {
			if(Integer.parseInt(request.getParameter("insurance").toString()) == 1){
				result.put("ladder", regionList.get(0));
				result.put("result_null_region", "false");
			} else {
				result.put("regionListShow", regionListShow(regionList));
				result.put("result_null_region", "false");
			}
		} else {
			result.put("result_null_region", "true");
		}
		return result;
	}
	
	@Override
	public List<Map<String, Object>> regionListShow(List<Map<String, Object>> ladderList) {
		List<Map<String, Object>> regionListShow = new ArrayList<Map<String,Object>>();
		Map<String, Object> row = null;
		Map<String, Object> ladder = null;
		for(int i =0;i< ladderList.size();i++){
			ladder = ladderList.get(i);
			row = new HashMap<String, Object>();
			row.put("id", ladder.get("id"));
			StringBuffer region = new StringBuffer();
			if(Integer.parseInt(ladder.get("compare_1").toString())==0){
				region.append("(");
			} else if(Integer.parseInt(ladder.get("compare_1").toString())==1){
				region.append("[");
			}
			region.append(ladder.get("num_1") + ",");
			if(CommonUtils.checkExistOrNot(ladder.get("num_2"))){
				region.append(ladder.get("num_2"));
			} else {
				region.append("+∞");
			}
			if(Integer.parseInt(ladder.get("compare_2").toString())==2){
				region.append(")");
			} else if(Integer.parseInt(ladder.get("compare_2").toString())==3){
				region.append("]");
			}
			row.put("region", region);
			if(CommonUtils.checkExistOrNot(ladder.get("charge_percent"))){
				row.put("charge_content", ladder.get("charge_percent")+"%");
			} else {
				row.put("charge_content", ladder.get("charge"));
			}
			regionListShow.add(row);
		}
		return regionListShow;
	}
	
	@Override
	public JSONObject savePackagePriceLadder(HttpServletRequest request, JSONObject result) throws Exception {
		result = new JSONObject();
		PackagePriceLadder pPL = new PackagePriceLadder();
		// 更新无阶梯
		String id = request.getParameter("id");
		int insurance = Integer.parseInt(request.getParameter("insurance").toString());
		if(CommonUtils.checkExistOrNot(id) && insurance == 1){
			// 更新
			pPL.setUpdate_by(SessionUtils.getEMP(request).getUsername());
			pPL.setId(Integer.parseInt(id.toString()));
			pPL.setCharge_percent(Double.parseDouble(request.getParameter("charge_percent").toString()));
			pPL.setCharge_percent_uom(Integer.parseInt(request.getParameter("charge_percent_uom").toString()));
			Byte charge_min_flag = Byte.parseByte(request.getParameter("charge_min_flag").toString());
			pPL.setCharge_min_flag(charge_min_flag);
			if(charge_min_flag == 0){
				pPL.setCharge_min(Double.parseDouble(request.getParameter("charge_min").toString()));
				pPL.setCharge_min_uom(Integer.parseInt(request.getParameter("charge_min_uom").toString()));
			}
			packageChargeLadderMapper.updatePackageChargeLadder(pPL);
			result.put("result_code", "SUCCESS");
			result.put("result_content", "更新阶梯类型数据成功！");
		} else {
			// 新增
			pPL.setCreate_by(SessionUtils.getEMP(request).getUsername());
			int con_id = Integer.parseInt(request.getParameter("con_id").toString());
			Byte return_flag = Byte.parseByte((request.getParameter("return_flag").toString()));
			pPL.setCon_id(con_id);
			pPL.setReturn_flag(return_flag);
			pPL.setInsurance(insurance);
			if(insurance == 1){
				pPL.setCharge_percent(Double.parseDouble(request.getParameter("charge_percent").toString()));
				pPL.setCharge_percent_uom(Integer.parseInt(request.getParameter("charge_percent_uom").toString()));
				Byte charge_min_flag = Byte.parseByte(request.getParameter("charge_min_flag").toString());
				pPL.setCharge_min_flag(charge_min_flag);
				if(charge_min_flag == 0){
					pPL.setCharge_min(Double.parseDouble(request.getParameter("charge_min").toString()));
					pPL.setCharge_min_uom(Integer.parseInt(request.getParameter("charge_min_uom").toString()));
				}
				packageChargeLadderMapper.addPackageChargeLadder(pPL);
				result.put("result_code", "SUCCESS");
				result.put("result_content", "新增阶梯类型数据成功！");
				result.put("id", pPL.getId());
			} else {
				// 校验区间是否重叠
				Integer compare_1 = Integer.parseInt(request.getParameter("compare_1").toString());
				Double num_1 = Double.parseDouble(request.getParameter("num_1").toString());
				Integer compare_2 = Integer.parseInt(request.getParameter("compare_2").toString());
				Double num_2 = null;
				if(request.getParameterMap().containsKey("num_2")) {
					num_2 = Double.parseDouble(request.getParameter("num_2").toString());
				}
				Map<String, Object> input = new HashMap<String, Object>();
				input.put("compare_1", compare_1);
				input.put("num_1", num_1);
				input.put("compare_2", compare_2);
				input.put("num_2", num_2);
				// 获取阶梯
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("con_id", con_id);
				param.put("return_flag", return_flag);
				param.put("insurance", insurance);
				result = CommonUtils.checkRegion(input, packageChargeLadderMapper.loadRecords(param));
				if(result.containsKey("result_code")){
					return result;
				}
				pPL.setCompare_1(compare_1);
				pPL.setNum_1(num_1);
				pPL.setUom_1(Integer.parseInt(request.getParameter("uom_1").toString()));
				pPL.setRel(Integer.parseInt(request.getParameter("rel").toString()));
				pPL.setCompare_2(compare_2);
				pPL.setNum_2(num_2);
				pPL.setUom_2(Integer.parseInt(request.getParameter("uom_2").toString()));
				if(request.getParameterMap().containsKey("charge_percent")){
					pPL.setCharge_percent(Double.parseDouble(request.getParameter("charge_percent").toString()));
					pPL.setCharge_percent_uom(Integer.parseInt(request.getParameter("charge_percent_uom").toString()));
				} else if(request.getParameterMap().containsKey("charge")){
					pPL.setCharge(Double.parseDouble(request.getParameter("charge").toString()));
					pPL.setCharge_uom(Integer.parseInt(request.getParameter("charge_uom").toString()));
				}
				packageChargeLadderMapper.addPackageChargeLadder(pPL);
				// 区间展现	
				List<Map<String, Object>> regionList =  packageChargeLadderMapper.loadRecords(param);
				// 判断是否有记录
				if(regionList.size() != 0) {
					result.put("regionListShow", regionListShow(regionList));
					result.put("result_code", "SUCCESS");
					result.put("result_content", "新增阶梯类型区间成功！");
				} else {
					result.put("result_code", "FAILURE");
					result.put("result_content", "查询管理费规则失败,未能获取新增阶梯类型及其他！");
				}
			}
		}
		return result;
	}
	
	@Override
	public JSONObject judgeExistRecord(HttpServletRequest request, JSONObject result) throws Exception {
		result = new JSONObject();
		result.put("num", packageChargeLadderMapper.findLadder(CommonUtils.getParamMap(request)));
		return result;
	}

	@Override
	public Long selectCount(Map<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
