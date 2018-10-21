package com.bt.lmis.service.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.OSinfo;
import com.bt.lmis.base.spring.SpringUtils;
import com.bt.lmis.code.ServiceSupport;
import com.bt.lmis.controller.form.DiffBilldeatilsQueryParam;
import com.bt.lmis.controller.form.ExpressbillDetailQueryParam;
import com.bt.lmis.dao.DiffBilldeatilsMapper;
import com.bt.lmis.dao.DifferenceMatchingMapper;
import com.bt.lmis.dao.ExpressbillDetailMapper;
import com.bt.lmis.model.DifferenceObj;
import com.bt.lmis.model.ExpressbillDetail;
import com.bt.lmis.service.ExpressbillDetailService;
import com.bt.lmis.utils.DiffA;
import com.bt.lmis.utils.DiffB;
import com.bt.utils.CommonUtils;
import com.bt.utils.ZipUtils;
@Service
public class ExpressbillDetailServiceImpl<T> extends ServiceSupport<T> implements ExpressbillDetailService<T> {

	@Autowired
    private ExpressbillDetailMapper<T> mapper;
	
	@Autowired
    private DiffBilldeatilsMapper<T> diffBilldeatilsMapper;

	public ExpressbillDetailMapper<T> getMapper() {
		return mapper;
	}

	@Override
	public Long selectCount(Map<String, Object> param) throws Exception {
		return mapper.selectCount(param);
	}

	@Override
	public void deleteByMaster_id(String id) {
		// TODO Auto-generated method stub
		mapper.deleteByMaster_id(id);
	}

	@Override
	public void deleteByBat_id(String batch_id) {
		// TODO Auto-generated method stub
		mapper.deleteByBat_id(batch_id);
	}

	/*@Override
	public void diff(Map<String,String> param) {
		// TODO Auto-generated method stub
		
		String path= CommonUtils.getAllMessage("config", "BALANCE_DIFFERENCE_EXPRESS_" + OSinfo.getOSname());
		String file=  "差异报表";
		
		
		String bat_id=param.get("bat_id").toString();
		DifferenceMatchingMapper<T>  differenceMatchingMapper=(DifferenceMatchingMapper<T>)SpringUtils.getBean("differenceMatchingMapper");
		differenceMatchingMapper
		.deleteResult0(param.get("bat_id").toString());
		ExpressbillDetailQueryParam edq=new ExpressbillDetailQueryParam();
		edq.setBat_id(bat_id);
	    int  total=mapper.Count(edq);
	    edq.setFirstResult(0);
	    edq.setMaxResult(1);
	    List<Map<String,Object>> list=(List<Map<String, Object>>) mapper.findAll(edq);
	    if(list==null||list.size()==0)return;
		//最多五个线程
	    LinkedHashMap<String,String> map=new LinkedHashMap<String, String>();
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
	    map.put("remarks","备注");
	    
	    
	    
		int  threadNo=0;
		int  data_no=0;
		if(total<=200000){threadNo=1;}else{
			threadNo=5;
		}
		data_no=total%threadNo==0?total/threadNo:total/threadNo+1;
        Thread[] threadA=new Thread[threadNo];
		for(int i=0;i<threadNo;i++){
			DifferenceObj obj=new DifferenceObj();
			obj.setBat_id(bat_id);obj.setUrl(path);
			obj.setBegin_index(((ExpressbillDetail)list.get(0)).getId()+i*data_no);
			obj.setTableHeader(map);
			obj.setData_no(data_no);obj.setFileName(file);
			switch(i){
			case 0:obj.setTable_name("tb_diff_billdeatils_temp1");break;
			case 1:obj.setTable_name("tb_diff_billdeatils_temp2");break;
			case 2:obj.setTable_name("tb_diff_billdeatils_temp3");break;
			case 3:obj.setTable_name("tb_diff_billdeatils_temp4");break;
			case 4:obj.setTable_name("tb_diff_billdeatils_temp5");break;
			}
			
			
			DiffA a=new DiffA(obj);
			threadA[i]=a;
			threadA[i].start();
		}
	
		//退货入库
		DifferenceObj DOB=new DifferenceObj();
		DOB.setFileName(file);
		DOB.setBat_id(bat_id);
		DOB.setTableHeader(map);
		DiffB b =new DiffB(DOB);
		b.start();
		
		for(int i=0;i<threadNo;i++){
			try {
				threadA[i].join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
		try {
			ZipUtils.zip(CommonUtils.getAllMessage("config", "COMMON_DOWNLOAD_" + OSinfo.getOSname())+file,CommonUtils.getAllMessage("config", "COMMON_DOWNLOAD_" + OSinfo.getOSname()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}	*/
	
	
	
	@Override
	public void diff(Map<String,String> param) {
		// TODO Auto-generated method stub
		String bat_id=param.get("bat_id").toString();
		DifferenceMatchingMapper<T>  differenceMatchingMapper=(DifferenceMatchingMapper<T>)SpringUtils.getBean("differenceMatchingMapper");
		differenceMatchingMapper
		.deleteResult0(param.get("bat_id").toString());
		ExpressbillDetailQueryParam edq=new ExpressbillDetailQueryParam();
		edq.setBat_id(bat_id);
		edq.setIs_verification("0");
	    edq.setFirstResult(0);
	    edq.setMaxResult(1);
	    edq.setIs_verification(null);
	    List<Map<String,Object>> list=(List<Map<String, Object>>) mapper.findAll(edq);
	    if(list==null||list.size()==0)return;
		//最多五个线程
	   /* LinkedHashMap<String,String> map=new LinkedHashMap<String, String>();
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
	    map.put("remarks","备注");
	    */
	    mapper.clearDiff(bat_id);
	    
	   Map<String,Object> minmax= mapper.getIdByBatId(bat_id);
	   int total=Integer.parseInt(minmax.get("max_id").toString());
	   total=total-((ExpressbillDetail)list.get(0)).getId()+1;
		int  threadNo=0;
		int  data_no=0;
		if(total<=200000){threadNo=1;}else{
			threadNo=5;
		}
		data_no=total%threadNo==0?total/threadNo:total/threadNo+1;
        Thread[] threadA=new Thread[threadNo];
        
        mapper.toClear();
        
		for(int i=0;i<threadNo;i++){
			DifferenceObj obj=new DifferenceObj();
			obj.setBat_id(bat_id);
			obj.setBegin_index(((ExpressbillDetail)list.get(0)).getId()+i*data_no);
			obj.setData_no(((ExpressbillDetail)list.get(0)).getId()+(i+1)*data_no);
			obj.setExpress_code(((ExpressbillDetail)list.get(0)).getExpress_code());
			switch(i){
			case 0:obj.setTable_name("tb_diff_billdeatils_temp1");break;
			case 1:obj.setTable_name("tb_diff_billdeatils_temp2");break;
			case 2:obj.setTable_name("tb_diff_billdeatils_temp3");break;
			case 3:obj.setTable_name("tb_diff_billdeatils_temp4");break;
			case 4:obj.setTable_name("tb_diff_billdeatils_temp5");break;
			}
			
			
			DiffA a=new DiffA(obj);
			threadA[i]=a;
			threadA[i].start();
		}
	
		//退货入库
		DifferenceObj DOB=new DifferenceObj();
		DOB.setBat_id(bat_id);
		DiffB b =new DiffB(DOB);
		b.start();
		try {
			b.join();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for(int i=0;i<threadNo;i++){
			try {
				threadA[i].join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	    //回表
		
		differenceMatchingMapper.insertTable(bat_id);
		//update 未能核销的原因
		
		differenceMatchingMapper.updateReason(bat_id);
	}

	@Override
	public int checkToDiff(ExpressbillDetailQueryParam query) {
		// TODO Auto-generated method stub
		query.setFirstResult(0);
		query.setMaxResult(1);
    List list=mapper.checkToDiff(query);
    if(list==null||list.size()==0)return  0 ;
        return 1;
	}

	@Override
	public void deleteVerification(ExpressbillDetailQueryParam query) {
		// TODO Auto-generated method stub
		mapper.deleteVerification(query);
	}	
	
}
