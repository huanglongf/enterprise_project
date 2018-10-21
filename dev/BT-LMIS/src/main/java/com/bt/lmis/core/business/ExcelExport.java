package com.bt.lmis.core.business;

import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.lmis.dao.ContractBasicinfoMapper;
import com.bt.lmis.model.ContractBasicinfo;
import com.bt.utils.BaseConst;
import com.bt.utils.CommonUtils;
import com.bt.utils.FileUtil;
import com.bt.utils.POIUtil;

/**
 * @Title:ExcelExport
 * @Description: TODO(生成EXCEL账单)
 * @author Ian.Huang 
 * @date 2016年12月22日下午3:13:50
 */
@Service
public class ExcelExport {

	@Autowired
	private ContractBasicinfoMapper<T> contractBasicinfoMapper;
	/**
	 * 
	 * @Description: TODO(导出结算报表)
	 * @param cb
	 * @param path
	 * @param year
	 * @param month
	 * @throws Exception
	 * @return: Boolean  
	 * @author Ian.Huang 
	 * @date 2016年12月22日下午3:39:13
	 */
	public Boolean exportSettlementForm(ContractBasicinfo cb, String path, String year, String month) throws Exception {
		// 合同ID
		String contract_id= cb.getId();
		// 客户编号
		String contract_owner= cb.getContract_owner();
		// 客户名称
		String client_name= "";
		// 查询店铺SQL
		StringBuffer sb_get_storename = new StringBuffer();
		// 根据contract_owner查询客户下所有店铺
		sb_get_storename.append("select c.id as cid,c.client_name as client_name,s.id as sid,s.store_name as store_name from tb_client c ");
		sb_get_storename.append("left join tb_store s on c.id=s.client_id ");
		sb_get_storename.append("where c.client_code='"+ contract_owner+ "' ");
		List<Map<String, Object>> store_list= contractBasicinfoMapper.set_SQL(sb_get_storename.toString());
		if(CommonUtils.checkExistOrNot(store_list)){
			client_name= store_list.get(0).get("client_name").toString();
			
		} else {
			return false;
			
		}
		// 路径/文件校验
		String fileName= path+ year+ "年"+ month+ "月"+ client_name+ "结算报表.xlsx";
		FileUtil.isExistFile(fileName);
		// 创建文件
		SXSSFWorkbook workbook= new SXSSFWorkbook(1000);
		// 店铺名称集合
		StringBuffer str_storename= new StringBuffer();
		// 循环客户下所有店铺
		for (int j= 0; j< store_list.size(); j++) {
			str_storename.append("'"+ store_list.get(j).get("store_name")+ "'");
			if(j+ 1!= store_list.size()){
				str_storename.append(", ");
				
			}
			
		}
		//仓储费Start---------------------------------------------------------------------------------------------------------------------------------------------------------------------------
		StringBuffer sb_get_storageCharge_lists= new StringBuffer();
		//仓储费明细查询 聚合
		sb_get_storageCharge_lists.append("select billing_cycle as 结算周期,area_qty as 面积数量,area_cost as 按面积结算,piece_qty as 件数,piece_cost as 件数结算 from tb_storage_data_group where contract_id="+contract_id+" ");
		sb_get_storageCharge_lists.append("and billing_cycle='"+ (year+"-"+month)+ "' ");
		List<Map<String, Object>> storageCharge_lists= contractBasicinfoMapper.set_SQL(sb_get_storageCharge_lists.toString());
		List<Map<String, Object>> max_list= new ArrayList<>();
		Map<String, Object> map_1= new LinkedHashMap<>();
		map_1.put("text1", "仓储费");
		if(storageCharge_lists.size()!= 0){
			if(null!= storageCharge_lists.get(0).get("按面积结算")){
				map_1.put("text2", storageCharge_lists.get(0).get("面积数量"));
				map_1.put("text4",storageCharge_lists.get(0).get("按面积结算"));
				
			} else {
				map_1.put("text2", storageCharge_lists.get(0).get("件数"));
				map_1.put("text4", storageCharge_lists.get(0).get("件数结算"));
				
			}
			
		}else{
			map_1.put("text2", "暂无结算数据");
			map_1.put("text3", "");
			map_1.put("text4", "");
			
		}
		map_1.put("text3", "");
		map_1.put("text5", "");
		//将操作费明细放入text6 -仓储费没有明细
		map_1.put("text6", null);
		max_list.add(map_1);
		//仓储费Sheet页
		Map<String, Object> ccfMap= new LinkedHashMap<>();
		if(CommonUtils.checkExistOrNot(storageCharge_lists)){
			ccfMap.put("sheet_name", client_name+ "-仓储费");
			Map<String, String> ccf_title= new LinkedHashMap<>();
			ccf_title.put("结算周期", "结算周期");
			ccf_title.put("面积数量", "面积数量");
			ccf_title.put("按面积结算", "按面积结算");
			ccf_title.put("件数", "件数");
			ccf_title.put("件数结算", "件数结算");
			ccfMap.put("sheet_title", ccf_title);
			ccfMap.put("sheet_list", storageCharge_lists);
			workbook= POIUtil.create_sheet(workbook, ccfMap, false);
			
		}
		//仓储费End---------------------------------------------------------------------------------------------------------------------------------------------------------------------------
		
		//操作费Start---------------------------------------------------------------------------------------------------------------------------------------------------------------------------
		//查询操作费汇总SQL
		StringBuffer sb_get_czf_lists= new StringBuffer();
		sb_get_czf_lists.append("select cb.contract_name as 合同名称,cb.id, hzData.settle_period as 结算周期, hzData.ib_fee as B2B入库,hzData.btb_qty as btb_qty, ");
		sb_get_czf_lists.append("hzData.btc_fee as B2C出库, hzData.ib_qty as ib_qty, ");
		sb_get_czf_lists.append("hzData.btb_fee as B2B出库,hzData.btc_qty as btc_qty, ");
		sb_get_czf_lists.append("hzData.return_fee 销售退货,hzData.return_qty as return_qty, ");
		sb_get_czf_lists.append("(hzData.ib_fee+hzData.btc_fee+hzData.btb_fee+hzData.return_fee) as 按实际使用量结算 ");
		sb_get_czf_lists.append(",xse_fee 按销售额百分比结算 ");
		sb_get_czf_lists.append(",gd_fee 按固定费用结算 ");
		sb_get_czf_lists.append("from tb_operationfee_data_settlement hzData ");
		sb_get_czf_lists.append("left join tb_contract_basicinfo cb on hzData.contract_id=cb.id ");
		sb_get_czf_lists.append("where 1=1 ");
		sb_get_czf_lists.append("and cb.id="+ contract_id+ " ");
		sb_get_czf_lists.append("and hzData.settle_period='"+ year+ "-"+ month+ "' ");
		List<Map<String, Object>> czf_lists= contractBasicinfoMapper.set_SQL(sb_get_czf_lists.toString());
		Map<String, Object> map_2= new LinkedHashMap<>();
		List<Map<String, Object>> small_list= new ArrayList<>();
		if(CommonUtils.checkExistOrNot(czf_lists)){
			if(new BigDecimal(czf_lists.get(0).get("按固定费用结算").toString()).compareTo(new BigDecimal(0))!=0){
				map_2.put("text1", "操作费");
				map_2.put("text2", "按固定费用结算");
				map_2.put("text3", "");
				map_2.put("text4", czf_lists.get(0).get("按固定费用结算"));
				map_2.put("text5", "");
				
			}else if(new BigDecimal(czf_lists.get(0).get("按销售额百分比结算").toString()).compareTo(new BigDecimal(0))!=0){
				map_2.put("text1", "操作费");
				map_2.put("text2", "按销售额百分比结算");
				map_2.put("text3", "");
				map_2.put("text4", czf_lists.get(0).get("按销售额百分比结算"));
				map_2.put("text5", "");
				
			}else if(new BigDecimal(czf_lists.get(0).get("按实际使用量结算").toString()).compareTo(new BigDecimal(0))!=0){
				map_2.put("text1", "操作费");
				map_2.put("text2", "");
				map_2.put("text3", "按实际使用量结算");
				map_2.put("text4", czf_lists.get(0).get("按实际使用量结算"));
				map_2.put("text5", "");
				Map<String, Object> map_gourp_4 = new LinkedHashMap<>();
				map_gourp_4.put("text1", "B2B出库");
				map_gourp_4.put("text2", czf_lists.get(0).get("btb_qty"));
				map_gourp_4.put("text3", "");
				map_gourp_4.put("text4", czf_lists.get(0).get("B2B出库"));
				map_gourp_4.put("text5", "");
				small_list.add(map_gourp_4);
				Map<String, Object> map_gourp_3 = new LinkedHashMap<>();
				map_gourp_3.put("text1", "B2B入库");
				map_gourp_3.put("text2", czf_lists.get(0).get("ib_qty"));
				map_gourp_3.put("text3", "");
				map_gourp_3.put("text4", czf_lists.get(0).get("B2B入库"));
				map_gourp_3.put("text5", "");
				small_list.add(map_gourp_3);
				Map<String, Object> map_gourp_2 = new LinkedHashMap<>();
				map_gourp_2.put("text1", "B2C出库");
				map_gourp_2.put("text2", czf_lists.get(0).get("btc_qty"));
				map_gourp_2.put("text3", "");
				map_gourp_2.put("text4", czf_lists.get(0).get("B2C出库"));
				map_gourp_2.put("text5", "");
				small_list.add(map_gourp_2);
				Map<String, Object> map_gourp_1 = new LinkedHashMap<>();
				map_gourp_1.put("text1", "销售退货");
				map_gourp_1.put("text2", czf_lists.get(0).get("return_qty"));
				map_gourp_1.put("text3", "");
				map_gourp_1.put("text4", czf_lists.get(0).get("销售退货"));
				map_gourp_1.put("text5", "");
				small_list.add(map_gourp_1);
				map_2.put("text6", small_list);
				
			}
			
		}else{
			map_2.put("text1", "操作费");
			map_2.put("text2", "暂无结算数据");
			map_2.put("text3", "");
			map_2.put("text4", "");
			map_2.put("text5", "");
			
		}
		max_list.add(map_2);
		//操作费Sheet页
		if(CommonUtils.checkExistOrNot(czf_lists)){
			//B2C出库操作费
			int count1= contractBasicinfoMapper.get_count("select count(1) from tb_operationfee_data where store_name in ("+str_storename.toString()+") and job_type in ('退换货出库','销售出库') and year(operation_time)="+year+" and month(operation_time)="+month);
			int a= 0;
			for(int j= 1; j< forCount(count1, BaseConst.excel_pageSize)+ 1; j++) {
				workbook= POIUtil.create_sheet(workbook, add_czf_sheet(str_storename, "'退换货出库','销售出库'", client_name+ "-B2C出库操作费-"+ j, year, month, " limit "+ a+ ","+BaseConst.excel_pageSize), false);
				a= j*BaseConst.excel_pageSize;
				
			}
			//B2B入库操作费
			int count2= contractBasicinfoMapper.get_count("select count(1) from tb_operationfee_data where store_name in ("+ str_storename.toString()+ ") and job_type in ('VMI移库入库','代销入库','结算经销入库') and year(operation_time)="+ year+ " and month(operation_time)="+ month);
			a= 0;
			for (int j= 1; j< forCount(count2, BaseConst.excel_pageSize)+ 1; j++) {
				workbook= POIUtil.create_sheet(workbook, add_czf_sheet(str_storename, "'VMI移库入库','代销入库','结算经销入库'", client_name+ "-B2B入库操作费-"+j, year, month," limit "+ a+ ","+ BaseConst.excel_pageSize), false);
				a= j*BaseConst.excel_pageSize;
				
			}
			//B2B出库操作费 AD特殊判断
			if(contract_id.equals("264")){
				int count3 = contractBasicinfoMapper.get_count("select count(1) from tb_operationfee_data where store_name in ("+str_storename.toString()+") and job_type in ('VMI退仓出库','VMI转店退仓出库','代销出库','结算经销出库','样品领用出库','库间移动-出库') and year(operation_time)="+year+" and month(operation_time)="+month);
				a= 0;
				for (int j = 1; j < forCount(count3, BaseConst.excel_pageSize)+1; j++) {
					workbook= POIUtil.create_sheet(workbook, add_czf_sheet(str_storename, "'VMI退仓出库','VMI转店退仓出库','代销出库','结算经销出库','样品领用出库','库间移动-出库'", client_name+"-B2B出库操作费-"+j, year, month," limit "+a+","+BaseConst.excel_pageSize), false);
					a= j*BaseConst.excel_pageSize;
					
				}
				
			} else {
				int count3= contractBasicinfoMapper.get_count("select count(1) from tb_operationfee_data where store_name in ("+str_storename.toString()+") and job_type in ('VMI退仓出库','VMI转店退仓出库','代销出库','结算经销出库','样品领用出库') and year(operation_time)="+year+" and month(operation_time)="+month);
				a= 0;
				for (int j= 1; j< forCount(count3, BaseConst.excel_pageSize)+ 1; j++) {
					workbook= POIUtil.create_sheet(workbook, add_czf_sheet(str_storename, "'VMI退仓出库','VMI转店退仓出库','代销出库','结算经销出库','样品领用出库'", client_name+ "-B2B出库操作费-"+ j, year, month," limit "+ a+ ","+ BaseConst.excel_pageSize), false);
					a= j* BaseConst.excel_pageSize;
					
				}
				
			}
			//B2C退换货入库费
			int count4= contractBasicinfoMapper.get_count("select count(1) from tb_operationfee_data where store_name in ("+str_storename.toString()+") and job_type in ('退换货入库') and year(operation_time)="+year+" and month(operation_time)="+month);
			a= 0;
			for (int j= 1; j< forCount(count4, BaseConst.excel_pageSize)+1; j++) {
				workbook= POIUtil.create_sheet(workbook, add_czf_sheet(str_storename, "'退换货入库'", client_name+ "-B2C退换货入库费-"+ j, year, month," limit "+ a+ ","+BaseConst.excel_pageSize), false);
				a= j* BaseConst.excel_pageSize;
				
			}
			
		}
		//操作费End---------------------------------------------------------------------------------------------------------------------------------------------------------------------------
		
		//耗材费Start---------------------------------------------------------------------------------------------------------------------------------------------------------------------------
		//查询耗材费汇总SQL
		StringBuffer sb_get_hcf_lists= new StringBuffer();
		sb_get_hcf_lists.append("select b.contract_name,a.contract_id,a.billing_cycle,a.qty,a.total_amount,sku_type from tb_invitationdata_collect a  ");
		sb_get_hcf_lists.append("left join tb_contract_basicinfo b on a.contract_id=b.id ");
		sb_get_hcf_lists.append("where a.contract_id="+ contract_id+ " ");
		sb_get_hcf_lists.append("and billing_cycle= '"+ (year+"-"+month)+ "' ");
		List<Map<String, Object>> hcf_lists= contractBasicinfoMapper.set_SQL(sb_get_hcf_lists.toString());
		
		List<Map<String, Object>> small_list_3 = new ArrayList<>();
		BigDecimal sum_qty = new BigDecimal(0.00);
		BigDecimal sum_amount = new BigDecimal(0.00);
		for (int j = 0; j < hcf_lists.size(); j++) {
			if (hcf_lists.get(j).get("sku_type").equals("1")) {
				Map<String, Object> map_gourp_1 = new LinkedHashMap<>();
				map_gourp_1.put("text1", "主材");
				map_gourp_1.put("text2", hcf_lists.get(j).get("qty"));
				sum_qty=sum_qty.add(new BigDecimal(hcf_lists.get(j).get("qty").toString()));
				map_gourp_1.put("text3", "");
				map_gourp_1.put("text4", hcf_lists.get(j).get("total_amount"));
				sum_amount=sum_amount.add(new BigDecimal(hcf_lists.get(j).get("total_amount").toString()));
				map_gourp_1.put("text5", "");
				small_list_3.add(map_gourp_1);
			}else{
				Map<String, Object> map_gourp_2= new LinkedHashMap<>();
				map_gourp_2.put("text1", "辅材");
				map_gourp_2.put("text2", hcf_lists.get(j).get("qty"));
				sum_qty=sum_qty.add(new BigDecimal(hcf_lists.get(j).get("qty").toString()));
				map_gourp_2.put("text3", "");
				map_gourp_2.put("text4", hcf_lists.get(j).get("total_amount"));
				sum_amount=sum_amount.add(new BigDecimal(hcf_lists.get(j).get("total_amount").toString()));
				map_gourp_2.put("text5", "");
				small_list_3.add(map_gourp_2);
				
			}
			
		}
		Map<String, Object> map_3 = new LinkedHashMap<>();
		map_3.put("text1", "耗材费");
		map_3.put("text2", sum_qty);
		map_3.put("text3", "");
		map_3.put("text4", sum_amount);
		map_3.put("text5", "");
		map_3.put("text6", small_list_3);
		max_list.add(map_3);
		//耗材费明细数据
		workbook= POIUtil.create_sheet(workbook, add_hcf_sheet(contract_id, client_name+ "-耗材费明细", year, month), false);
		//耗材费End---------------------------------------------------------------------------------------------------------------------------------------------------------------------------
		
		//增值服务费Start---------------------------------------------------------------------------------------------------------------------------------------------------------------------------
		//查询增值服务费汇总SQL
		StringBuffer sb_get_zzfwf_lists = new StringBuffer();
		sb_get_zzfwf_lists.append("select addservice_name,qty,amount from tb_addservicefee_billdata_collect ");
		sb_get_zzfwf_lists.append("where contract_id= "+contract_id+" ");
		sb_get_zzfwf_lists.append("and time= '"+(year+"-"+month)+"' ");
		List<Map<String, Object>> zzfwf_lists = contractBasicinfoMapper.set_SQL(sb_get_zzfwf_lists.toString());
		List<Map<String, Object>> small_list_4 = new ArrayList<>();
		BigDecimal sum_zzfwf_qty = new BigDecimal(0.00);
		BigDecimal sum_zzfwf_amount = new BigDecimal(0.00);
		for (int j = 0; j < zzfwf_lists.size(); j++) {
			Map<String, Object> map_gourp = new LinkedHashMap<>();
			map_gourp.put("text1", zzfwf_lists.get(j).get("addservice_name"));
			map_gourp.put("text2", zzfwf_lists.get(j).get("qty"));
			sum_zzfwf_qty=sum_zzfwf_qty.add(new BigDecimal(zzfwf_lists.get(j).get("qty").toString()));
			map_gourp.put("text3", "");
			map_gourp.put("text4", zzfwf_lists.get(j).get("amount"));
			sum_zzfwf_amount=sum_zzfwf_amount.add(new BigDecimal(zzfwf_lists.get(j).get("amount").toString()));
			map_gourp.put("text5", "单价："+ new BigDecimal(zzfwf_lists.get(j).get("amount").toString()).divide(new BigDecimal(zzfwf_lists.get(j).get("qty").toString())));
			small_list_4.add(map_gourp);
			
		}
		Map<String, Object> map_4 = new LinkedHashMap<>();
		map_4.put("text1", "增值费");
		map_4.put("text2", sum_zzfwf_qty);
		map_4.put("text3", "");
		map_4.put("text4", sum_zzfwf_amount);
		map_4.put("text5", "");
		map_4.put("text6", small_list_4);
		max_list.add(map_4);
		//耗材费明细数据
		workbook= POIUtil.create_sheet(workbook, add_zzfwf_sheet(str_storename, client_name+ "-增值服务费明细", year, month), false);
		//增值服务费End---------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	
		// 运费Start---------------------------------------------------------------------------------------------------------------------------------------------------------------------------
		// 查询运费汇总
		List<Map<String, Object>> summary = contractBasicinfoMapper.set_SQL("select id, con_id, balance_subject, subject_type, balance_month, express, b.transport_name, product_type_code, product_type_name, order_num, total_freight, total_insurance, total_cod, extend_column_1, extend_column_2, extend_column_3, extend_column_4, extend_column_5, total_fee, create_by, create_time, update_by, update_time from ex_used_balance_summary a left join(select transport_code, transport_name from tb_transport_vendor where transport_type = 1) b on a.express = b.transport_code where con_id= "+ contract_id+ " and balance_month= '" + year + "-" + month+ "'");
		// 总运费折扣
		List<Map<String, Object>> tFD= contractBasicinfoMapper.set_SQL("select id, create_by, create_time, update_by, update_time, con_id, balance_subject, subject_type, carrier_used, CASE WHEN b.transport_name IS NULL THEN '' ELSE transport_name END AS transport_name, balance_month, fee_type, fee from tb_carrier_used_summary a left join(select transport_code,transport_name from tb_transport_vendor where validity= 1) b on a.carrier_used= b.transport_code where con_id= " + contract_id + " and balance_month= '" + year + "-" + month + "' and fee_type= 0");
		// 管理费
		List<Map<String, Object>> man= contractBasicinfoMapper.set_SQL("select id, create_by, create_time, update_by, update_time, con_id, balance_subject, subject_type, carrier_used, CASE WHEN b.transport_name IS NULL THEN '' ELSE transport_name END AS transport_name, balance_month, fee_type, fee from tb_carrier_used_summary a left join(select transport_code,transport_name from tb_transport_vendor where validity= 1) b on a.carrier_used= b.transport_code where con_id= " + contract_id + " and balance_month= '" + year + "-" + month + "' and fee_type= 1"); 
		// 运单量
		BigDecimal order_num= new BigDecimal(0);
		// 运费小计
		BigDecimal freight= new BigDecimal(0);
		// cod小计
		BigDecimal cod= new BigDecimal("0.00");
		// 管理费小计
		BigDecimal manFee= new BigDecimal("0.00");
		// 运费内容
		List<Map<String, Object>> small_list_5= new ArrayList<>();
		Map<String, Object> map_group= null;
		// cod内容
		List<Map<String, Object>> small_list_6= new ArrayList<>();
		Map<String, Object> map_group2= new LinkedHashMap<>();
		map_group2.put("text1", "COD项-合计");
		map_group2.put("text2", "");
		map_group2.put("text3", "");
		map_group2.put("text4", cod + "元");
		map_group2.put("text5", "");
		small_list_6.add(map_group2);
		String name= "";
		// 运单明细查询条件
		StringBuffer condition= new StringBuffer("");
		String temp1= "WHERE dFlag= 0 AND contract_id= "+ contract_id+ " AND transport_name= '";
		String temp2= " AND CONCAT(YEAR(transport_time), MONTH(transport_time))= '"+ year+ month + "' ORDER BY create_time DESC";
		for(int p= 0; p< summary.size(); p++) {
			order_num= order_num.add(new BigDecimal(summary.get(p).get("order_num").toString()));
			freight= freight.add(new BigDecimal(summary.get(p).get("total_fee").toString()));
			cod= cod.add(new BigDecimal(summary.get(p).get("total_cod").toString()));
			map_group= new LinkedHashMap<>();
			map_group2= new LinkedHashMap<>();
			name= CommonUtils.checkExistOrNot(summary.get(p).get("product_type_name"))? summary.get(p).get("product_type_name").toString(): summary.get(p).get("transport_name").toString();
			map_group.put("text1", name);
			map_group2.put("text1", name);
			map_group.put("text2", summary.get(p).get("order_num"));
			map_group2.put("text2", "");
			map_group.put("text3", "单");
			map_group2.put("text3", "");
			map_group.put("text4", summary.get(p).get("total_fee"));
			map_group2.put("text4", summary.get(p).get("total_cod"));
			map_group.put("text5", "");
			map_group2.put("text5", "");
			small_list_5.add(map_group);
			small_list_6.add(map_group2);
			//运费明细数据
			condition.append(new StringBuffer(temp1+ summary.get(p).get("transport_name")+ "'"));
			if(CommonUtils.checkExistOrNot(summary.get(p).get("product_type_name"))) {
				condition.append(" AND itemtype_code= '"+ summary.get(p).get("product_type_code")+ "' AND itemtype_name= '"+ summary.get(p).get("product_type_name")+ "'");
				
			}
			condition.append(temp2);
			int count= contractBasicinfoMapper.get_count(new StringBuffer("select count(1) from tb_warehouse_express_data_store_settlement ").append(condition).toString());
			int head= 0;
			for (int j= 1; j< forCount(count, BaseConst.excel_pageSize)+ 1; j++) {
				workbook= POIUtil.create_sheet(workbook, add_yf_sheet(name+ "运费-"+ j, condition, " LIMIT "+ head+ ", "+ BaseConst.excel_pageSize), false);
				head= j* BaseConst.excel_pageSize;
				
			}
			condition= new StringBuffer("");
			
		}
		// 若存在总运费折扣
		if(CommonUtils.checkExistOrNot(tFD)) {
			freight.subtract(new BigDecimal(tFD.get(0).get("fee").toString()));
			
		}
		// 表头
		// 运费汇总
		Map<String, Object> map_5 = new LinkedHashMap<String, Object>();
		// 运费
		map_5.put("text1", "运费-合计(折后运费+保价费-总运费折扣)");
		map_5.put("text2", order_num);
		map_5.put("text3", "单");
		map_5.put("text4", freight + "元");
		map_5.put("text5", "");
		// 总运费折扣
		map_group= new LinkedHashMap<>();
		map_group.put("text1", "总运费折扣");
		map_group.put("text2", "");
		map_group.put("text3", "");
		if(CommonUtils.checkExistOrNot(tFD)) {
			map_group.put("text4", tFD.get(0).get("fee").toString() + "元");
			
		} else {
			map_group.put("text4", "0.00元");
			
		}
		map_group.put("text5", "");
		small_list_5.add(map_group);
		map_5.put("text6", small_list_5);
		max_list.add(map_5);
		// cod费用单独列出
		Map<String, Object> map_6 = new LinkedHashMap<String, Object>();
		// 特殊服务费
		map_6.put("text1", "特殊服务费(COD+委托取件费)-合计");
		map_6.put("text2", "");
		map_6.put("text3", "");
		map_6.put("text4", cod + "元");
		map_6.put("text5", "");
		//
		map_group2= new LinkedHashMap<>();
		map_group2.put("text1", "委托取件费-合计");
		map_group2.put("text2", "");
		map_group2.put("text3", "");
		map_group2.put("text4", "0.00元");
		map_group2.put("text5", "");
		small_list_6.add(map_group2);
		map_6.put("text6", small_list_6);
		max_list.add(map_6);
		// 管理费
		List<Map<String, Object>> small_list_7= new ArrayList<>();
		for(int p= 0; p< man.size(); p++) {
			manFee= manFee.add(new BigDecimal(man.get(p).get("fee").toString()));
			if(CommonUtils.checkExistOrNot(man.get(p).get("transport_name").toString())) {
				map_group= new LinkedHashMap<>();
				map_group.put("text1", man.get(p).get("transport_name").toString());
				map_group.put("text2", "");
				map_group.put("text3", "");
				map_group.put("text4", man.get(p).get("fee").toString());
				map_group.put("text5", "");
				small_list_7.add(map_group);
				
			}
			
		}
		Map<String, Object> map_7= new LinkedHashMap<>();
		// 表头
		map_7.put("text1", "管理费-合计");
		map_7.put("text2", "");
		map_7.put("text3", "");
		map_7.put("text4", manFee + "元");
		map_7.put("text5", "");
		map_7.put("text6", small_list_7);
		max_list.add(map_7);
		//运费End---------------------------------------------------------------------------------------------------------------------------------------------------------------------------
		
		//表头Start---------------------------------------------------------------------------------------------------------------------------------------------------------------------------
		//汇总
		Map<String, Object> map= new LinkedHashMap<String,Object>();
		map.put("company", "上海宝尊电子商务有限公司");
		map.put("settlement_period", year+ "年"+ month+ "月");
		map.put("distribution_unit", "上海宝尊电子商务有限公司");
		map.put("client_name", client_name);
		map.put("store_name", str_storename.toString());
		map.put("unit", "元");
		map.put("max_lists", max_list);
		workbook= POIUtil.create_sheet(workbook, map, true);
		//表头End---------------------------------------------------------------------------------------------------------------------------------------------------------------------------
		FileOutputStream fos= new FileOutputStream(fileName);
		workbook.write(fos);
		fos.close();
		workbook.close();
		return true;
		
	}
	
	public Map<String, Object> add_yf_sheet(String sheet_name, Object condition, String limit){
		Map<String, Object> yfMap= new LinkedHashMap<>();
		yfMap.put("sheet_name", sheet_name);
		Map<String, String> yf_title = new LinkedHashMap<>();
		yf_title.put("所属仓库", "所属仓库");
		yf_title.put("所属店铺", "所属店铺");
		yf_title.put("订单号", "订单号");
		yf_title.put("备注", "备注");
		yf_title.put("商品编码", "商品编码");
		yf_title.put("长(CM)", "长(CM)");
		yf_title.put("宽(CM)", "宽(CM)");
		yf_title.put("高(CM)", "高(CM)");
		yf_title.put("体积(CM³)", "体积(CM³)");
		yf_title.put("发货时间", "发货时间");
//		yf_title.put("平台订单时间", "平台订单时间");
		yf_title.put("平台支付时间", "平台支付时间");
		yf_title.put("计费省", "计费省");
		yf_title.put("计费市", "计费市");
		yf_title.put("计费区", "计费区");
		yf_title.put("订单销售件数", "订单销售件数");
		yf_title.put("发货重量(KG)", "发货重量(KG)");
		yf_title.put("物流商名称", "物流商名称");
		yf_title.put("产品类型", "产品类型");
		yf_title.put("是否COD", "是否COD");
		yf_title.put("订单金额(元)", "订单金额(元)");
		yf_title.put("运单号", "运单号");
		yf_title.put("类型", "类型");
		yf_title.put("计费重量(KG)", "计费重量(KG)");
		yf_title.put("保价费(元)", "保价费(元)");
		yf_title.put("首重报价(元)", "首重报价(元)");
		yf_title.put("续重报价(元)", "续重报价(元)");
		yf_title.put("折扣", "折扣");
		yf_title.put("COD手续费(元)", "COD手续费(元)");
		yf_title.put("运费(元)", "运费(元)");
		yf_title.put("最终费用(元)（不含COD）", "最终费用(元)（不含COD）");
		yfMap.put("sheet_title", yf_title);
		yfMap.put("sheet_list",
				contractBasicinfoMapper.set_SQL(
						new StringBuffer("SELECT "
								+ "warehouse AS '所属仓库',"
								+ "store_name AS '所属店铺',"
								+ "epistatic_order AS '订单号',"
								+ "delivery_order AS '备注',"
								+ "sku_number AS '商品编码',"
								+ "length AS '长(CM)',"
								+ "width AS '宽(CM)',"
								+ "higth AS '高(CM)',"
								+ "volumn AS '体积(CM³)',"
								+ "DATE_FORMAT(transport_time, '%Y-%m-%d-%T') AS '发货时间',"
//								+ "DATE_FORMAT(platform_order_time, '%Y-%m-%d-%T') AS '平台订单时间',"
								+ "DATE_FORMAT(platform_pay_time, '%Y-%m-%d-%T') AS '平台支付时间',"
								+ "province AS '计费省',"
								+ "city AS '计费市',"
								+ "state AS '计费区',"
								+ "qty AS '订单销售件数',"
								+ "weight AS '发货重量(KG)',"
								+ "transport_name AS '物流商名称',"
								+ "itemtype_name AS '产品类型',"
								+ "CASE cod_flag WHEN true THEN '是' ELSE '否' END as '是否COD',"
								+ "order_amount AS '订单金额(元)',"
								+ "express_number AS '运单号',"
								+ "order_type AS '类型',"
								+ "charge_weight AS '计费重量(KG)',"
								+ "insurance_fee AS '保价费(元)',"
								+ "first_weight_price AS '首重报价(元)',"
								+ "added_weight_price AS '续重报价(元)',"
								+ "(100- discount)/100 AS '折扣',"
								+ "cod AS 'COD手续费(元)',"
								+ "afterdiscount_freight AS '运费(元)',"
								+ "total_fee AS '最终费用(元)（不含COD）' FROM tb_warehouse_express_data_store_settlement ")
						.append(condition)
						.append(limit)
						.toString()));
		return yfMap;
		
	}

	public Map<String, Object> add_zzfwf_sheet(StringBuffer str_storename,String sheet_name,String yy,String mm){
		StringBuffer czf_zzfwf_sql= new StringBuffer();
		czf_zzfwf_sql.append("select store_name as 店铺名,addservice_code as 增值服务费编码,addservice_name as 增值服务费名称,qty as 数量,amount as 总计 from tb_addservicefee_data_settlement ");
		czf_zzfwf_sql.append("where store_name in ("+str_storename.toString()+") and year(date)="+yy+" and month(date)="+mm);
		List<Map<String, Object>> czf_b2cob_sheet_list = contractBasicinfoMapper.set_SQL(czf_zzfwf_sql.toString());
		Map<String, Object> czfMap = new LinkedHashMap<>();
		czfMap.put("sheet_name", sheet_name);
		Map<String, String> czf_title = new LinkedHashMap<>();
		czf_title.put("店铺名", "店铺名");
		czf_title.put("增值服务费编码", "增值服务费编码");
		czf_title.put("增值服务费名称", "增值服务费名称");
		czf_title.put("数量", "数量");
		czf_title.put("总计", "总计");
		czfMap.put("sheet_title", czf_title);
		czfMap.put("sheet_list", czf_b2cob_sheet_list);
		return czfMap;
	}
	
	public Map<String, Object> add_hcf_sheet(String cb_id,String sheet_name,String yy,String mm){
		StringBuffer czf_b2cob_sql= new StringBuffer();
		czf_b2cob_sql.append("select billing_cycle as 结算周期,sku_code as 商品编码,sku_name as 商品名称,qty as 数量,price as 单价,total_amount as 总价, CASE sku_type when '1' then '主材' when '2' then '辅材' end as 类型 from  tb_invitation_useanmount_data_groupdetail ");
		czf_b2cob_sql.append("where contract_id="+cb_id+" and billing_cycle='"+(yy+"-"+mm)+"' ");
		List<Map<String, Object>> czf_b2cob_sheet_list = contractBasicinfoMapper.set_SQL(czf_b2cob_sql.toString());
		Map<String, Object> czfMap = new LinkedHashMap<>();
		czfMap.put("sheet_name", sheet_name);
		Map<String, String> czf_title = new LinkedHashMap<>();
		czf_title.put("结算周期", "结算周期");
		czf_title.put("商品编码", "商品编码");
		czf_title.put("商品名称", "商品名称");
		czf_title.put("数量", "数量");
		czf_title.put("单价", "单价");
		czf_title.put("总价", "总价");
		czf_title.put("类型", "类型");
		czfMap.put("sheet_title", czf_title);
		czfMap.put("sheet_list", czf_b2cob_sheet_list);
		return czfMap;
	}
	
	public Map<String, Object> add_czf_sheet(StringBuffer str_storename,String job_type,String sheet_name,String yy,String mm,String limit){
		StringBuffer czf_b2cob_sql= new StringBuffer();
		czf_b2cob_sql.append("select operation_time as 操作时间,job_orderno as 作业单号,related_orderno as 相关单据号,platform_order as 平台订单号,job_type as 操作类型,item_name as 商品名称,out_num as 出库数量,in_num as 入库数量 from tb_operationfee_data ");
		czf_b2cob_sql.append("where store_name in ("+str_storename.toString()+") and job_type in ("+job_type+") and year(operation_time)="+yy+" and month(operation_time)="+mm+" "+limit);
		List<Map<String, Object>> czf_b2cob_sheet_list = contractBasicinfoMapper.set_SQL(czf_b2cob_sql.toString());
		Map<String, Object> czfMap = new LinkedHashMap<>();
		czfMap.put("sheet_name", sheet_name);
		Map<String, String> czf_title = new LinkedHashMap<>();
		czf_title.put("操作时间", "操作时间");
		czf_title.put("作业单号", "作业单号");
		czf_title.put("相关单据号", "相关单据号");
		czf_title.put("平台订单号", "平台订单号");
		czf_title.put("操作类型", "操作类型");
		czf_title.put("商品名称", "商品名称");
		czf_title.put("出库数量", "出库数量");
		czf_title.put("入库数量", "入库数量");
		czfMap.put("sheet_title", czf_title);
		czfMap.put("sheet_list", czf_b2cob_sheet_list);
		return czfMap;
		
	}

	public int forCount(int count, int max) {
		int i= 1;
		if(count> max) {
			if((count% max)!= 0) {
				i= (count/ max)+ 1;
				
			}else{
				i= (count/ max);
				
			}
			
		}
		return i;
		
	}
	
}
