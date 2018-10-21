package com.bt.lmis.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.formula.functions.T;

import com.bt.lmis.base.spring.SpringUtils;
import com.bt.lmis.dao.DifferenceMatchingMapper;
import com.bt.lmis.model.DifferenceObj;
import com.bt.lmis.model.SimpleTable;
import com.bt.utils.BigExcelExport;

public class DiffB extends Thread {

	
	//退货结算机器人
		private DifferenceObj obj;
		public DifferenceObj getObj() {
			return obj;
		}
		public DiffB(DifferenceObj obj) {
			super();
			this.obj = obj;
		}
		public void setObj(DifferenceObj obj) {
			this.obj = obj;
		}
		public void run(){
			 /*LinkedHashMap<String,String> map=new LinkedHashMap<String, String>();
			    map.put("billingCycle", "核销周期");
			    map.put("month_account", "月结账号");
			    map.put("transport_time", "发货时间");
			    map.put("waybill", "运单号");
			    map.put("transport_weight", "发货重量（kg）");
			    map.put("transport_volumn", "体积(长*宽*高）");
			    map.put("origin_province", "始发地（省）");
			    map.put("origin_city", "始发地（市）");
			    map.put("origin_state", "始发地（区）");
			    map.put("dest_province", "目的地（省）");
			    map.put("dest_city", "目的地（市）");
			    map.put("charged_weight", "计费重量（kg）");
			    map.put("express_code", "供应商名称");
			    map.put("producttype_code", "产品类型");
			    map.put("insurance", "保值");
			    map.put("freight", "运费");
			    map.put("insurance_fee", "保价费");
			    map.put("other_value_added_service_charges", "其他增值服务费");
			    map.put("total_charge", "合计费用");
			    map.put("transport_warehouse", "发货仓");
			    map.put("store", "所属店铺");
			    map.put("cost_center", "成本中心代码");
			    map.put("epistatic_order", "前置单据号");map.put("platform_no", "平台订单号");
			    map.put("sku_number", "耗材sku编码");map.put("length", "长（mm）");
			    map.put("width", "宽（mm)");map.put("height", "高(mm)");
			    map.put("volumn", "体积(mm3）");
			    map.put("origin_province1", "始发地（省）");
			    map.put("origin_city1", "始发地（市）");
			    map.put("transport_time1", "发货时间");
			    map.put("dest_province1", "目的地（省）");
			    map.put("dest_city1", "目的地（市）");
			    map.put("transport_weight1", "发货重量");
			    map.put("express_code1", "物流商名称");
			    map.put("producttype_code1", "产品类型");
			    map.put("insurance1", "保价货值");
			    map.put("volumn_charged_weight", "体积计费重量（mm3）");
			    map.put("charged_weight1", "计费重量（kg)");
			    map.put("firstWeight", "首重");
			    map.put("addedWeight", "续重");
			    map.put("discount", "折扣");
			    map.put("standard_freight", "标准运费");
			    map.put("afterdiscount_freight", "折扣运费");
			    map.put("insurance_fee1", "保价费");
			    map.put("additional_fee", "附加费&服务费");
			    map.put("last_fee", "最终费用");
			    map.put("is_verification", "是否核销");
			    map.put("reason", "未核销原因备注");
			    map.put("remarks","备注");*/
			    
			
			
			DifferenceMatchingMapper<T> differenceMatchingMapper= (DifferenceMatchingMapper<T>)SpringUtils.getBean("differenceMatchingMapper");
			//建退货入库表
			differenceMatchingMapper.deleteReturnTab(obj.getBat_id());
			differenceMatchingMapper.createReturn(obj.getBat_id());
			List<Map<String, Object>> tableContent=differenceMatchingMapper.returnTableContent(obj.getBat_id());
			
			//建表
			differenceMatchingMapper.deleteWarehouseTab(obj.getBat_id());
			for(Map<String, Object> pp:tableContent){
				differenceMatchingMapper.InsertWarehouseTab(pp);
			}
			
			differenceMatchingMapper.createWarehouseTab(obj.getBat_id());
			
			System.out.println("B done");
		}
}
