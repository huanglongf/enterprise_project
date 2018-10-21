package com.bt.lmis.service.impl;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import javax.annotation.Resource;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import com.bt.OSinfo;
import com.bt.lmis.balance.service.BalanceService;
import com.bt.lmis.code.ServiceSupport;
import com.bt.lmis.controller.excel.XLSXCovertCSVReader;
import com.bt.lmis.controller.form.DiffBilldeatilsQueryParam;
import com.bt.lmis.dao.DiffBilldeatilsMapper;
import com.bt.lmis.dao.ExpressbillMasterhxMapper;
import com.bt.lmis.model.CollectionDetail;
import com.bt.lmis.model.CollectionMaster;
import com.bt.lmis.model.DiffBilldeatils;
import com.bt.lmis.model.ExpressbillMaster;
import com.bt.lmis.model.ExpressbillMasterhx;
import com.bt.lmis.page.QueryResult;
import com.bt.lmis.service.DiffBilldeatilsService;
import com.bt.lmis.service.ExpressbillMasterService;
import com.bt.lmis.service.ExpressbillMasterhxService;
import com.bt.utils.BigExcelExport;
import com.bt.utils.CommonUtils;
import com.bt.utils.DateUtil;
import com.bt.utils.ZipUtils;
import java.util.Arrays;
@Service
public class DiffBilldeatilsServiceImpl<T> extends ServiceSupport<T> implements DiffBilldeatilsService<T> {

	@Autowired
    private DiffBilldeatilsMapper<T> mapper;
	@Resource(name = "balanceServiceImpl")
	private BalanceService balanceServiceImpl;
	@Resource(name = "expressbillMasterhxServiceImpl")
	private ExpressbillMasterhxService<ExpressbillMasterhx> expressbillMasterhxService;
	@Resource(name = "expressbillMasterServiceImpl")
	private ExpressbillMasterService expressbillMasterService;
	@Autowired
	ExpressbillMasterhxMapper expressbillMasterhxMapper;
	
	public DiffBilldeatilsMapper<T> getMapper() {
		return mapper;
	}

	@Override
	public Long selectCount(Map<String, Object> param) throws Exception {
		return mapper.selectCount(param);
	}

	@Override
	public QueryResult<DiffBilldeatils> selectMasterId(DiffBilldeatilsQueryParam queryParam) {
		// TODO Auto-generated method stub
		QueryResult<DiffBilldeatils> qr=new QueryResult<DiffBilldeatils>();
		qr.setResultlist(mapper.selectMasterId(queryParam));
		qr.setTotalrecord(mapper.Count(queryParam));
		return qr;
	}
	
	@Override
	public void insertDiff(DiffBilldeatilsQueryParam queryParam) {
		// TODO Auto-generated method stub
		
	}
	
	public  DiffBilldeatils getReason(DiffBilldeatils detail){
		String  reason="";
		if(!detail.getProducttype_code().equals(detail.getProducttype_code1()))reason+=";产品类型不符";
		if(detail.getCharged_weight().compareTo(detail.getCharged_weight1())!=0)reason+=";计费重量不符";
		if(detail.getInsurance_fee().compareTo(detail.getInsurance_fee1())!=0)reason+=";保价费不符";
		//if(detail.getAdditional_fee().compareTo(detail.getOther_value_added_service_charges())!=0)reason+=";增值服务费不符";
		detail.setReason(reason);
		return  detail;
	}
	
	
	@Override
	public void insertDiffResult(Map<String, String> param0) throws IOException, OpenXML4JException, ParserConfigurationException, SAXException {
		// TODO Auto-generated method stub
		String filePath=param0.get("file");
		String master_id=param0.get("master_id");
		String user_id=param0.get("user");
		 File   f =new File(filePath);
 		List<String[]> list= XLSXCovertCSVReader.readerExcel(filePath, null, 100);  
 		list.remove(list.get(0));
 		int  flag_no=4000;
 		int  size_no=list.size();
 	    int  for_no=size_no%flag_no==0?size_no/flag_no:size_no/flag_no+1;
 	    String bat_id=UUID.randomUUID().toString();
 	   for(int i=0;i<for_no;i++){
  		  List<String []> sflist=null;
  		  sflist =new ArrayList<String []>();
  		  if(i==for_no-1){
  			  for(int j=i*flag_no;j<list.size();j++){
  				  list.get(j)[52]=bat_id;
  				  list.get(j)[53]=user_id;
  				  sflist.add(list.get(j));
  			  } 
  		  }else{
  			  for(int j=i*flag_no;j<i*flag_no+flag_no;j++){
 				  list.get(j)[53]=user_id;
 				 list.get(j)[52]=bat_id;
 				  sflist.add(list.get(j));
  				  
  			  } 
  		  }
  		  mapper.updateDiff(sflist);
  	  }	
 	    //调用重新结算
 	   DiffBilldeatilsQueryParam dq=new DiffBilldeatilsQueryParam();
 	   dq.setCal_batid(bat_id);
 	   dq.setRemarks("以供应商为准");
 	List<DiffBilldeatils> recalList=	mapper.selectMasterId(dq);
 	if(recalList!=null&&recalList.size()!=0){
 		ExpressbillMaster expressbillMaster=null;
 		try {
 			expressbillMaster = (ExpressbillMaster) expressbillMasterService.selectById(Integer.parseInt(recalList.get(0).getMaster_id()));
 		} catch (Exception e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}
 		List<DiffBilldeatils> result=balanceServiceImpl.rebalance(recalList, expressbillMaster.getCon_id());
 	 		for(DiffBilldeatils detail:result){
 	 			try {
 	 				if(detail.getSettleFlag()==1){
 	 					if("SF".equals(detail.getExpress_code())){
 	 						if((detail.getFreight().add(detail.getInsurance_fee())).compareTo((detail.getStandard_freight().add(detail.getInsurance_fee1())))==0){
	 	 						 detail.setIs_verification("1");
	 	 						 detail.setRemarks("以供应商为准，重新结算完成");
	 	 					 }else{
	 	 						getReason(detail);
	 	 					 }
 	 					}else{
 	 						if(detail.getTotal_charge().compareTo(detail.getLast_fee())==0){
 	 	 						 detail.setIs_verification("1");
 	 	 						 detail.setRemarks("以供应商为准，重新结算完成");
 	 	 					 }else{
 	 	 						 getReason(detail);
 	 	 					 }	
 	 					}
 	 				}else{
 	 					detail.setRemarks("重新结算失败");
 	 				}
 					mapper.update((T) detail);
 				} catch (Exception e) {
 					// TODO Auto-generated catch block
 					e.printStackTrace();
 				}
 	 		}return;
 	};
 	
 		//以宝尊为准的直接更新核销状态
 		DiffBilldeatils bd=new DiffBilldeatils();
 		bd.setRemarks("以宝尊为准");
 		bd.setCal_batid(bat_id);
 		bd.setIs_verification("1");
 		try {
			mapper.update((T) bd);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Override
	public void insertDiff(Map<String, String> param0) {
		// TODO Auto-generated method stub
		String filePath=param0.get("file");
		String master_id=param0.get("master_id");
		String user_id=param0.get("user");
		 File   f =new File(filePath);
 		try{
 		List<String[]> list= XLSXCovertCSVReader.readerExcel(filePath, null, 100);  
 		list.remove(list.get(0));
 		int  flag_no=4000;
 		int  size_no=list.size();
 	    int  for_no=size_no%flag_no==0?size_no/flag_no:size_no/flag_no+1;
 	    int size=0;
 	    for(int i=0;i<for_no;i++){
 		  List<String []> sflist=null;
 		  sflist =new ArrayList<String []>();
 		  if(i==for_no-1){
 			  for(int j=i*flag_no;j<list.size();j++){
 				  list.get(j)[52]=user_id;
 				  list.get(j)[53]=master_id;
 				  sflist.add(list.get(j));
 				  size= size + 1;
 			  } 
 		  }else{
 			  for(int j=i*flag_no;j<i*flag_no+flag_no;j++){
 				  list.get(j)[52]=user_id;
				  list.get(j)[53]=master_id;
				  sflist.add(list.get(j));
 				  size= size + 1;
 			  } 
 		  }
 		  mapper.deleteDiff(sflist);
 		  mapper.insertDiff(sflist);
 	  }	
 	    //更批次表状态
 	
 		}catch(IOException io){
 			io.printStackTrace();
 			}
 		 catch(OpenXML4JException ox){
 			ox.printStackTrace(); 
 			}
 		 catch(ParserConfigurationException pf){
 			 pf.printStackTrace();
 			}
 		 catch(SAXException se){
 			 se.printStackTrace();
 			}
	}
	
	@Override
	public void verification(DiffBilldeatilsQueryParam param) {
		// TODO Auto-generated method stub
	List<DiffBilldeatils>  list=	mapper.selectMasterId(param);
	String express_code=   list.get(0).getExpress_code();
	
	   if("SF".equals(express_code)){

		  mapper.sFverification(param);
		  
		  mapper.verification1(param); 
	   }else{
		   mapper.verification(param);//常规的
		   
		   mapper.verification1(param);   //以宝尊为准
	   }
		   
	}

	@Override
	public void verification(List<String> ids) {
		// TODO Auto-generated method stub
		mapper.verificationIds(ids);
	}

	@Override
	public void Cancelverification(DiffBilldeatilsQueryParam param) {
		// TODO Auto-generated method stub
		mapper.Cancelverification(param);
	}

	@Override
	public void Cancelverification(List<String> ids) {
		// TODO Auto-generated method stub
		mapper.CancelverificationIds(ids);
	}

	@Override
	public int saveAccount(DiffBilldeatilsQueryParam param) throws Exception {
		// TODO Auto-generated method stub
		//检查是否已经生成账单
		param.setAccountparam("1");
	String user=	param.getCreate_user();
	param.setCreate_user("");
	    int flag=	mapper.Count(param);
		if(flag>0)return 2;
		String account_id=UUID.randomUUID().toString();
		param.setAccount_id(account_id);
		param.setAccountparam(null);
		mapper.gerAccount(param);
		
		ExpressbillMasterhx entity=new ExpressbillMasterhx();
	       entity.setBill_name(DateUtil.format(new Date()));
	       entity.setCreate_time(new Date());
	       entity.setCreate_user(param.getCreate_user());
	       entity.setId(account_id);
	       entity.setStatus("0");
	       entity.setMaster_id(param.getMaster_id());
	       expressbillMasterhxMapper.insert(entity);
		return 1;
	}

	@Override
	public int saveAccount(Map<String,Object> param) throws Exception {
		// TODO Auto-generated method stub
		String idsString=param.get("ids").toString();
		if(idsString==null||"".equals(idsString))return 0;
		String idslist[]=idsString.split(";");
         List ids=Arrays.asList(idslist);
	  int flag=	mapper.checkAccountById(ids);
       if(flag>0)return 2;
       String idsv="";
       for(String id:idslist){
    	   idsv+=id+",";
       }
       if(!"".equals(idsv))idsv=idsv.substring(0, idsv.length()-1);
    	   String account_id=UUID.randomUUID().toString();
       mapper.genAccountIds(idsv,account_id,param.get("user").toString(),param.get("master_id").toString());
       ExpressbillMasterhx entity=new ExpressbillMasterhx();
       entity.setBill_name(DateUtil.format(new Date()));
       entity.setCreate_time(new Date());
       entity.setCreate_user(param.get("user").toString());
       entity.setUpdate_time(new Date());
       entity.setUpdate_user(param.get("user").toString());
       entity.setId(account_id);
       entity.setStatus("0");
       entity.setMaster_id(param.get("master_id").toString());
       expressbillMasterhxMapper.insert(entity);
		return 1;
	}

	@Override
	public int reDiff(DiffBilldeatilsQueryParam param) {
		// TODO Auto-generated method stub
		//检查是否已经关账
		String close_account=param.getClose_account();
		param.setClose_account("1"); //0未关账 1已关账
		 int flag=	mapper.Count(param);
		if(flag>0)return 0;
		//删除tb_expressbill_detail
		param.setClose_account(close_account);
		mapper.deleteExpressbillDetail(param);
		//插回tb_expressbill_detail
		String bat_id=UUID.randomUUID().toString();
		param.setBat_id(bat_id);
		mapper.insertReDiffParam(param);
		
		
		
		
		//删除原有的数据
		
		//进行新的匹配
		
		return 1;
	}

	@Override
	public int reDiff(List<String> ids) {
		// TODO Auto-generated method stub
		        //检查是否已经关账
		
		
				//导入到新的表中
				
				
				//删除原有的数据
				
				//进行新的匹配
				
		
		
		return 1;
	}	
	
	/**
	 * 
	 * @param ids id集合
	 * @param contract_id 合同id
	 */
	public List<CollectionMaster> uploadByIds(String[] ids,String contract_id,String uuid) {
		// TODO Auto-generated method stub
		for (String id : ids) {
			mapper.insertDiffBilldeatilsTemp(uuid, id);
		}
		Map<String ,String> map = new HashMap<String ,String>();
		map.put("table_name", "tb_diff_billdeatils_temp");
		map.put("master_id", uuid.toString());
		map.put("account_id", uuid.toString());
		map.put("contract_id", contract_id);
		Map<String, Object> discount = balanceServiceImpl.getDiscount(map);
		String str = (String) discount.get("ALL");
		if(str!=null){
			mapper.updateDiffBilldeatilsTemp(uuid,str);
		}else{
			for(Entry<String, Object> vo : discount.entrySet()){ 
	            String procude_code = vo.getKey(); 
	            BigDecimal discount1 = (BigDecimal) discount.get(procude_code);
	            mapper.updateDiffBilldeatilsTempSF(uuid,procude_code,discount1);
	        }
		}
		List<CollectionMaster> summary = balanceServiceImpl.getSummary(map);
		return summary;
	}
	
	
	/**
	 * 
	 * @param param
	 * @param contract_id 合同id
	 */
	public List<CollectionMaster> uploadByQueryParam(DiffBilldeatilsQueryParam param,String contract_id,String uuid) {
		// TODO Auto-generated method stub
		mapper.insertDiffBilldeatilsTempByQueryParam(uuid, param);
		Map<String ,String> map = new HashMap<String ,String>();
		map.put("table_name", "tb_diff_billdeatils_temp");
		map.put("master_id", uuid.toString());
		map.put("account_id", uuid.toString());
		map.put("contract_id", contract_id);
		Map<String, Object> discount = balanceServiceImpl.getDiscount(map);
		String str = (String) discount.get("ALL");
		if(str!=null){
			mapper.updateDiffBilldeatilsTemp(uuid,str);
		}else{
			for(Entry<String, Object> vo : discount.entrySet()){ 
	            String procude_code = vo.getKey(); 
	            BigDecimal discount1 = (BigDecimal) vo.getValue(); 
	            mapper.updateDiffBilldeatilsTempSF(uuid,procude_code,discount1);
	        }
		}
		List<CollectionMaster> summary = balanceServiceImpl.getSummary(map);
		return summary;
	}

	@Override
	public void deleteDiffBilldeatilsTempByAccountId(String uuid) {
		// TODO Auto-generated method stub
		mapper.deleteDiffBilldeatilsTempByAccountId(uuid);
	}
	
	
	/**
	 * 
	 * @param account_id 合同id
	 * @param contract_id 
	 * @param file_name 文件名（不可重复）
	 */
	@Override
	public void getUploadeExcel(String account_id,String contract_id,String file_name) {
		// TODO Auto-generated method stub
		Date time =new Date();
		SimpleDateFormat date = new SimpleDateFormat("yyyyMMdd");
		String string1 = date.format(time);
		Map<String ,String> map1 = new HashMap<String ,String>();
		map1.put("table_name", "tb_diff_billdeatils");
		map1.put("account_id", account_id);
		map1.put("contract_id", contract_id);
		/*Map<String, Object> discount = balanceServiceImpl.getDiscount(map);
		String str = (String) discount.get("ALL");
		if(str!=null){
			mapper.updateDiffBilldeatils(account_id,str);
		}else{
			for(Entry<String, Object> vo : discount.entrySet()){ 
	            String procude_code = vo.getKey(); 
	            BigDecimal discount1 = (BigDecimal) vo.getValue(); 
	            mapper.updateDiffBilldeatilsSF(account_id,procude_code,discount1);
	        }
		}*/
		List<CollectionMaster> list = balanceServiceImpl.getSummary(map1);
		
		
		Map<String, String> map = new HashMap<>();
		List<String> listsf = new ArrayList<String>();
		List<Map<String, Object>> data = new ArrayList<>();
		if(list.size()>0){
			if(list.get(0).getDetails().get(0).getProducttype_code()!=null||list.get(0).getDetails().get(0).getProducttype_code()!=""){
				for (CollectionMaster collectionMaster : list) {
					List<CollectionDetail> list2 = collectionMaster.getDetails();
					for (CollectionDetail collectionDetail : list2) {
						if(map.get(collectionDetail.getProducttype_code())==null||map.get(collectionDetail.getProducttype_code())==""){
							map.put(collectionDetail.getProducttype_code(), collectionDetail.getProducttype_code());
						}
					}
				}
				for (Map.Entry<String, String> entry : map.entrySet()) {
				   /* System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());*/
				    listsf.add(entry.getKey());
				}
			}
			for (int j = 0; j < list.size(); j++) {
				CollectionMaster collectionMaster = list.get(j);
				Map<String, Object> keyMap = new HashMap<>();		
				keyMap.put("cost_center", collectionMaster.getCost_center());
				keyMap.put("store_code", collectionMaster.getStore_code());
				keyMap.put("warehouse_code", collectionMaster.getWarehouse_code());
				keyMap.put("insurance_fee", collectionMaster.getInsurance_fee());
				keyMap.put("service_fee", collectionMaster.getService_fee());
				if(collectionMaster.getDetails().get(0).getProducttype_code()==null||list.get(0).getDetails().get(0).getProducttype_code()==""){
							keyMap.put("sum", collectionMaster.getDetails().get(0).getSum());
							keyMap.put("freight", collectionMaster.getDetails().get(0).getFreight());
							keyMap.put("favourable_price", "");
							keyMap.put("zfavourable_price", collectionMaster.getDetails().get(0).getFavourable_price());
				}else{
					List<CollectionDetail> details = collectionMaster.getDetails();
					for (CollectionDetail collectionDetail : details) {
							keyMap.put(collectionDetail.getProducttype_code()+"sum", collectionDetail.getSum());
							keyMap.put(collectionDetail.getProducttype_code()+"freight", collectionDetail.getFreight());
							keyMap.put(collectionDetail.getProducttype_code()+"favourable_price", collectionDetail.getFavourable_price());
							keyMap.put(collectionDetail.getProducttype_code()+"zfavourable_price", "");
					}
				}
				data.add(keyMap);
			}
			try{
				HashMap<String, String> cMap = new LinkedHashMap<String, String>();
				cMap.put("cost_center", "成本中心");
				cMap.put("store_code", "店铺");
				cMap.put("warehouse_code", "仓库");
				if(list.size()==0){
					cMap.put("sum", "单量");
					cMap.put("freight", "标准运费");
					cMap.put("insurance_fee", "保价费");
					cMap.put("service_fee", "服务费");
					cMap.put("favourable_price", "单运单优惠金额");
					cMap.put("zfavourable_price", "总运单优惠金额");
/*				}else if(list.get(0).getDetails().get(0).getProducttype_code()==null||list.get(0).getDetails().get(0).getProducttype_code()==""){
					cMap.put("sum", "单量");
					cMap.put("freight", "标准运费");
					cMap.put("insurance_fee", "保价费");
					cMap.put("service_fee", "服务费");
					cMap.put("favourable_price", "单运单优惠金额");
					cMap.put("zfavourable_price", "总运单优惠金额");
					
*/				}else{
					for (String string : listsf) {
						if(string!=null&&string!=""){
						    if(string.equals("SFCR")||string=="SFCR"){
						    	cMap.put(string+"sum", "顺丰次日单量");
						    }else if(string.equals("SFJR")||string=="SFJR"){
						    	cMap.put(string+"sum", "顺丰即日单量");
						    }else if(string.equals("SFCC")||string=="SFCC"){
						    	cMap.put(string+"sum", "顺丰次晨单量");
						    }else if(string.equals("SFGR")||string=="SFGR"){
						    	cMap.put(string+"sum", "顺丰隔日单量");
						    }else if(string.equals("YCCR")||string=="YCCR"){
								cMap.put(string+"sum", "云仓次日单量");
							}else if(string.equals("YCGR")||string=="YCGR"){
								cMap.put(string+"sum", "云仓隔日单量");
							}
						}else{
							cMap.put("sum", "单量");
						}
					}
					for (String string : listsf) {
						if(string!=null&&string!=""){
							if(string.equals("SFCR")||string=="SFCR"){
								cMap.put(string+"freight", "顺丰次日标准运费");
							}else if(string.equals("SFJR")||string=="SFJR"){
								cMap.put(string+"freight", "顺丰即日标准运费");
							}else if(string.equals("SFCC")||string=="SFCC"){
								cMap.put(string+"freight", "顺丰次晨标准运费");
							}else if(string.equals("SFGR")||string=="SFGR"){
								cMap.put(string+"freight", "顺丰隔日标准运费");
							}else if(string.equals("YCCR")||string=="YCCR"){
								cMap.put(string+"freight", "云仓次日标准运费");
							}else if(string.equals("YCGR")||string=="YCGR"){
								cMap.put(string+"freight", "云仓隔日标准运费");
							}
						}else{
							cMap.put("freight", "标准运费");
						}
					}
					cMap.put("insurance_fee", "保价费");
					cMap.put("service_fee", "服务费");
					for (String string : listsf) {
						if(string!=null&&string!=""){
							if(string.equals("SFCR")||string=="SFCR"){
								cMap.put(string+"favourable_price", "顺丰次日单运单优惠金额");
							}else if(string.equals("SFJR")||string=="SFJR"){
								cMap.put(string+"favourable_price", "顺丰即日单运单优惠金额");
							}else if(string.equals("SFCC")||string=="SFCC"){
								cMap.put(string+"favourable_price", "顺丰次晨单运单优惠金额");
							}else if(string.equals("SFGR")||string=="SFGR"){
								cMap.put(string+"favourable_price", "顺丰隔日单运单优惠金额");
							}else if(string.equals("YCCR")||string=="YCCR"){
								cMap.put(string+"favourable_price", "云仓次日单运单优惠金额");
							}else if(string.equals("YCGR")||string=="YCGR"){
								cMap.put(string+"favourable_price", "云仓隔日单运单优惠金额");
							}
						}else{
							cMap.put("favourable_price", "单运单优惠金额");
						}
					}
					for (String string : listsf) {
						if(string!=null&&string!=""){
							if(string.equals("SFCR")||string=="SFCR"){
								cMap.put(string+"zfavourable_price", "顺丰次日总运单优惠金额");
							}else if(string.equals("SFJR")||string=="SFJR"){
								cMap.put(string+"zfavourable_price", "顺丰即日总运单优惠金额");
							}else if(string.equals("SFCC")||string=="SFCC"){
								cMap.put(string+"zfavourable_price", "顺丰次晨总运单优惠金额");
							}else if(string.equals("SFGR")||string=="SFGR"){
								cMap.put(string+"zfavourable_price", "顺丰隔日总运单优惠金额");
							}else if(string.equals("YCCR")||string=="YCCR"){
								cMap.put(string+"zfavourable_price", "云仓次日总运单优惠金额");
							}else if(string.equals("YCGR")||string=="YCGR"){
								cMap.put(string+"zfavourable_price", "云仓隔日总运单优惠金额");
							}
						}else{
							cMap.put("zfavourable_price", "总运单优惠金额");
						}
					}
			}
			/*for (CollectionMaster collectionMaster : list) {
					Map<String, Object> keyMap = new HashMap<>();		
					keyMap.put("cost_center", collectionMaster.getCost_center());
					keyMap.put("store_code", collectionMaster.getStore_code());
					keyMap.put("warehouse_code", collectionMaster.getWarehouse_code());
					keyMap.put("insurance_fee", collectionMaster.getInsurance_fee());
					keyMap.put("service_fee", collectionMaster.getService_fee());
					if(collectionMaster.getDetails().get(0).getProducttype_code()==null||list.get(0).getDetails().get(0).getProducttype_code()==""){
								keyMap.put("sum", collectionMaster.getDetails().get(0).getSum());
								keyMap.put("freight", collectionMaster.getDetails().get(0).getFreight());
								keyMap.put("favourable_price", collectionMaster.getDetails().get(0).getFavourable_price());
								keyMap.put("zfavourable_price", "");
					}else{
						List<CollectionDetail> details = collectionMaster.getDetails();
						for (CollectionDetail collectionDetail : details) {
								keyMap.put(collectionDetail.getProducttype_code()+"sum", collectionDetail.getSum());
								keyMap.put(collectionDetail.getProducttype_code()+"freight", collectionDetail.getFreight());
								keyMap.put(collectionDetail.getProducttype_code()+"favourable_price", "");
								keyMap.put(collectionDetail.getProducttype_code()+"zfavourable_price", collectionDetail.getFavourable_price());
						}
					}
					data.add(keyMap);
				}	
			}
		try{
			HashMap<String, String> cMap = new LinkedHashMap<String, String>();
			cMap.put("cost_center", "成本中心");
			cMap.put("store_code", "店铺");
			cMap.put("warehouse_code", "仓库");
			if(list.size()==0){
				cMap.put("sum", "单量");
				cMap.put("freight", "标准运费");
				cMap.put("insurance_fee", "保价费");
				cMap.put("service_fee", "服务费");
				cMap.put("favourable_price", "单运单优惠金额");
				cMap.put("zfavourable_price", "总运单优惠金额");
			}else if(list.get(0).getDetails().get(0).getProducttype_code()==null||list.get(0).getDetails().get(0).getProducttype_code()==""){
				cMap.put("sum", "单量");
				cMap.put("freight", "标准运费");
				cMap.put("insurance_fee", "保价费");
				cMap.put("service_fee", "服务费");
				cMap.put("favourable_price", "单运单优惠金额");
				cMap.put("zfavourable_price", "总运单优惠金额");
				
			}else{
				for (String string : listsf) {
				    if(string.equals("SFCR")||string=="SFCR"){
				    	cMap.put(string+"sum", "顺丰次日单量");
				    }else if(string.equals("SFJR")||string=="SFJR"){
				    	cMap.put(string+"sum", "顺丰即日单量");
				    }else if(string.equals("SFCC")||string=="SFCC"){
				    	cMap.put(string+"sum", "顺丰次晨单量");
				    }else if(string.equals("SFGR")||string=="SFGR"){
				    	cMap.put(string+"sum", "顺丰隔日单量");
				    }else if(string.equals("YCCR")||string=="YCCR"){
						cMap.put(string+"sum", "云仓次日单量");
					}else if(string.equals("YCGR")||string=="YCGR"){
						cMap.put(string+"sum", "云仓隔日单量");
					}
				}
				for (String string : listsf) {
					if(string.equals("SFCR")||string=="SFCR"){
						cMap.put(string+"freight", "顺丰次日标准运费");
					}else if(string.equals("SFJR")||string=="SFJR"){
						cMap.put(string+"freight", "顺丰即日标准运费");
					}else if(string.equals("SFCC")||string=="SFCC"){
						cMap.put(string+"freight", "顺丰次晨标准运费");
					}else if(string.equals("SFGR")||string=="SFGR"){
						cMap.put(string+"freight", "顺丰隔日标准运费");
					}else if(string.equals("YCCR")||string=="YCCR"){
						cMap.put(string+"freight", "云仓次日标准运费");
					}else if(string.equals("YCGR")||string=="YCGR"){
						cMap.put(string+"freight", "云仓隔日标准运费");
					}
				}
				cMap.put("insurance_fee", "保价费");
				cMap.put("service_fee", "服务费");
				for (String string : listsf) {
					if(string.equals("SFCR")||string=="SFCR"){
						cMap.put(string+"favourable_price", "顺丰次日单运单优惠金额");
					}else if(string.equals("SFJR")||string=="SFJR"){
						cMap.put(string+"favourable_price", "顺丰即日单运单优惠金额");
					}else if(string.equals("SFCC")||string=="SFCC"){
						cMap.put(string+"favourable_price", "顺丰次晨单运单优惠金额");
					}else if(string.equals("SFGR")||string=="SFGR"){
						cMap.put(string+"favourable_price", "顺丰隔日单运单优惠金额");
					}else if(string.equals("YCCR")||string=="YCCR"){
						cMap.put(string+"favourable_price", "云仓次日单运单优惠金额");
					}else if(string.equals("YCGR")||string=="YCGR"){
						cMap.put(string+"favourable_price", "云仓隔日单运单优惠金额");
					}
				}
				for (String string : listsf) {
					if(string.equals("SFCR")||string=="SFCR"){
						cMap.put(string+"zfavourable_price", "顺丰次日总运单优惠金额");
					}else if(string.equals("SFJR")||string=="SFJR"){
						cMap.put(string+"zfavourable_price", "顺丰即日总运单优惠金额");
					}else if(string.equals("SFCC")||string=="SFCC"){
						cMap.put(string+"zfavourable_price", "顺丰次晨总运单优惠金额");
					}else if(string.equals("SFGR")||string=="SFGR"){
						cMap.put(string+"zfavourable_price", "顺丰隔日总运单优惠金额");
					}else if(string.equals("YCCR")||string=="YCCR"){
						cMap.put(string+"zfavourable_price", "云仓次日总运单优惠金额");
					}else if(string.equals("YCGR")||string=="YCGR"){
						cMap.put(string+"zfavourable_price", "云仓隔日总运单优惠金额");
					}
				}
			}*/
		
		
		
		/*int i = 0;
		for (CollectionMaster collectionMaster : list) {
			int j = collectionMaster.getDetails().size();
			if(j>i){
				i=j;
			}
		}
		List<Map<String, Object>> data = new ArrayList<>();
		for (CollectionMaster collectionMaster : list) {
				Map<String, Object> keyMap = new HashMap<>();		
				keyMap.put("cost_center", collectionMaster.getCost_center());
				keyMap.put("store_code", collectionMaster.getStore_code());
				keyMap.put("warehouse_code", collectionMaster.getWarehouse_code());
				keyMap.put("insurance_fee", collectionMaster.getInsurance_fee());
				keyMap.put("service_fee", collectionMaster.getService_fee());
				if(collectionMaster.getDetails().get(0).getProducttype_code()==null){
					for(int k=0; k<i; k++){
						if(collectionMaster.getDetails().size()>k){
							keyMap.put("sum"+(k+1), collectionMaster.getDetails().get(k).getSum());
							keyMap.put("freight"+(k+1), collectionMaster.getDetails().get(k).getFreight());
							keyMap.put("favourable_price"+(k+1), "");
							keyMap.put("zfavourable_price"+(k+1), collectionMaster.getDetails().get(k).getFavourable_price());
						}else{
							keyMap.put("sum"+(k+1), "");
							keyMap.put("freight"+(k+1), "");
							keyMap.put("favourable_price"+(k+1), "");
							keyMap.put("zfavourable_price"+(k+1), "");
						}
					}
				}else{
					for(int k=0; k<i; k++){
						if(collectionMaster.getDetails().size()>k){
							keyMap.put("sum"+(k+1), collectionMaster.getDetails().get(k).getSum());
							keyMap.put("freight"+(k+1), collectionMaster.getDetails().get(k).getFreight());
							keyMap.put("favourable_price"+(k+1), collectionMaster.getDetails().get(k).getFavourable_price());
							keyMap.put("zfavourable_price"+(k+1), "");
						}else{
							keyMap.put("sum"+(k+1), "");
							keyMap.put("freight"+(k+1), "");
							keyMap.put("favourable_price"+(k+1), "");
							keyMap.put("zfavourable_price"+(k+1), "");
						}
					}
				}
				data.add(keyMap);
			}	
		try{
			HashMap<String, String> cMap = new LinkedHashMap<String, String>();
			cMap.put("cost_center", "成本中心");
			cMap.put("store_code", "店铺");
			cMap.put("warehouse_code", "仓库");
			for(int k=0; k<i; k++){
				cMap.put("sum"+(k+1), "产品类型"+(k+1)+"单量");
			}
			for(int k=0; k<i; k++){
				cMap.put("freight"+(k+1), "产品类型"+(k+1)+"标准运费");
			}
			cMap.put("insurance_fee", "保价费");
			cMap.put("service_fee", "服务费");
			for(int k=0; k<i; k++){
				cMap.put("favourable_price"+(k+1), "产品类型"+(k+1)+"单运单优惠金额");
			}
			for(int k=0; k<i; k++){
				cMap.put("zfavourable_price"+(k+1), "产品类型"+(k+1)+"总运单优惠金额");
			}*/
				BigExcelExport.excelDownLoadDatab_Z(data, cMap,file_name, string1+"汇总表.xlsx");
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		List<Map<String, Object>> list1 = mapper.selectExpressbillMasterhxByAccountId(account_id);
		try {
			HashMap<String, String> cMap = new LinkedHashMap<String, String>();
			cMap.put("billingCycle", "核销周期");
			cMap.put("month_account", "月结账号");
			cMap.put("transport_time", "发货时间");
			cMap.put("waybill", "运单号");
			cMap.put("transport_weight", "发货重量（kg）");
			cMap.put("transport_volumn", "体积(长*宽*高）");
			cMap.put("origin_province", "始发地（省）");
			cMap.put("origin_city", "始发地（市）");
			cMap.put("origin_state", "始发地（区）");
			cMap.put("dest_province", "目的地（省）");
			cMap.put("dest_city", "目的地（市）");
			map.put("dest_state", "目的地（区）");
			cMap.put("charged_weight", "计费重量（kg）");
			cMap.put("express_code", "供应商名称");
			cMap.put("producttype_code", "产品类型");
			cMap.put("insurance", "保值");
			cMap.put("freight", "运费");
			cMap.put("insurance_fee", "保价费");
			cMap.put("other_value_added_service_charges", "其他增值服务费");
			cMap.put("total_charge", "合计费用");
			cMap.put("transport_warehouse", "发货仓");
			cMap.put("store", "所属店铺");
			cMap.put("cost_center", "成本中心代码");
			cMap.put("epistatic_order", "前置单据号");cMap.put("platform_no", "平台订单号");
			cMap.put("sku_number", "耗材sku编码");cMap.put("length", "长（mm）");
			cMap.put("width", "宽（mm)");cMap.put("height", "高(mm)");
			cMap.put("volumn", "体积(mm3）");
			cMap.put("origin_province1", "始发地（省）");
			cMap.put("origin_city1", "始发地（市）");
			cMap.put("transport_time1", "发货时间");
			cMap.put("dest_province1", "目的地（省）");
			cMap.put("dest_city1", "目的地（市）");
			cMap.put("transport_weight1", "发货重量");
			cMap.put("express_code1", "物流商名称");
			cMap.put("producttype_code1", "产品类型");
			cMap.put("insurance1", "保价货值");
			cMap.put("volumn_charged_weight", "体积计费重量（mm3）");
			cMap.put("charged_weight1", "计费重量（kg)");
			cMap.put("firstWeight", "首重");
			cMap.put("addedWeight", "续重");
			cMap.put("discount", "折扣");
			cMap.put("standard_freight", "标准运费");
			cMap.put("afterdiscount_freight", "折扣运费");
			cMap.put("insurance_fee1", "保价费");
			cMap.put("additional_fee", "附加费&服务费");
			cMap.put("last_fee", "最终费用");
			cMap.put("is_verification", "是否核销");
			cMap.put("reason", "未核销原因备注");
			cMap.put("remarks","备注");
			BigExcelExport.excelDownLoadDatab_Z(list1, cMap,file_name, string1+"汇总明细表.xlsx");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			ZipUtils.zip(CommonUtils.getAllMessage("config", "COMMON_DOWNLOAD_"+OSinfo.getOSname())+file_name, CommonUtils.getAllMessage("config", "COMMON_DOWNLOAD_"+OSinfo.getOSname()));
			expressbillMasterhxService.updateById(account_id,file_name);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public String upload(DiffBilldeatilsQueryParam queryParam) throws IOException {
		// TODO Auto-generated method stub
		
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
	    map.put("dest_state", "目的地（区）");
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
	    
		//List<Map<String,Object>> content=mapper.uploadDetails(queryParam);
	    Map<String,Object> mparam=  mapper. getMinMaxId(queryParam);
		File f=BigExcelExport.excelDownLoadDatab_Zddhx(null, map,"hxupload" , "hxupload"+DateUtil.formatSS(new Date())+".xlsx",queryParam,mparam);	
		return f.getName();
	}

	@Override
	public String uploadIds(String ids) throws IOException {
		// TODO Auto-generated method stub

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
	    map.put("dest_state", "目的地（区）");
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
	    String[] ids0=ids.split(";");
	    String id1="";
	    for(String id:ids0){
	    	id1+=id+",";
	       }
	    if(!"".equals(id1))id1=id1.substring(0, id1.length()-1);
		List<Map<String,Object>> content=mapper.uploadDetailsIds(id1);
		
		File f=BigExcelExport.excelDownLoadDatab_Z(content, map,"hxupload" , "hxupload"+DateUtil.formatSS(new Date())+".xlsx");	
		return f.getName();
	}

	@Override
	public void deleteByQuery(DiffBilldeatilsQueryParam dq) {
		// TODO Auto-generated method stub
		mapper.deleteByQuery(dq);
	}

	@Override
	public void closeBfData(String master_id) {
		// TODO Auto-generated method stub
		DiffBilldeatilsQueryParam queryParam=new DiffBilldeatilsQueryParam();
		List<DiffBilldeatils> list=mapper.selectMasterId(queryParam);
		if(list==null||list.size()==0)return;
		
		
		
	}

	@Override
	public List<Map<String, Object>> getMonthAccount() {
		// TODO Auto-generated method stub
		return mapper.getMonthAccount();
	}


	
}
