package com.bt.lmis.service.impl;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bt.lmis.code.ServiceSupport;
import com.bt.lmis.dao.AreaMapper;
import com.bt.lmis.dao.BuspriceMapper;
import com.bt.lmis.dao.InternalPriceMapper;
import com.bt.lmis.dao.ItemtypePriceMapper;
import com.bt.lmis.dao.OriginDesitinationParamlistMapper;
import com.bt.lmis.model.Busprice;
import com.bt.lmis.model.InternalPrice;
import com.bt.lmis.model.ItemtypePrice;
import com.bt.lmis.model.OriginDesitinationParamlist;
import com.bt.lmis.service.OriginDesitinationParamlistService;
@Service
public  class OriginDesitinationParamlistServiceImpl<T> extends ServiceSupport<T> implements OriginDesitinationParamlistService<T>,Runnable {

	@Autowired
    private OriginDesitinationParamlistMapper<T> mapper;
  
	@Autowired
    private AreaMapper<T> areamapper;
	
	@Autowired
    private BuspriceMapper<T> bus;
	
	@Autowired
    private InternalPriceMapper<T> Internal;
	
	@Autowired
    private ItemtypePriceMapper<T> it_price;

	public OriginDesitinationParamlistMapper<T> getMapper() {
		return mapper;
	}
    private List<innerParam> ListIn=null;
	public List<innerParam> getListIn() {
		return ListIn;
	}
	public void setListIn(List<innerParam> listIn) {
		ListIn = listIn;
	}
	@Override
	public Long selectCount(Map<String, Object> param) throws Exception {
		return mapper.selectCount(param);
	}
	public Map<String,Object> selectCounts(Map<String, Object> param){

		return mapper.selectCounts(param);	
	}

	@Override
	public Map<String, Object> getInfoByNm(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return mapper.getInfoByNm(param);
	}
	
	
	@Override
	public Map<String, Object> findTableIdByTBNm(Map tbn) {
		// TODO Auto-generated method stub	
		return mapper.findTableIdByTBNm(tbn);
	}
	@Override
	public String saveExcels(Workbook workbook,boolean isCheck,String userName,int sheetCount)throws Exception{
	 ListIn=new ArrayList<innerParam>();
	 Date timeStr=new Date();
		String  log="";
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		int i=0;
			//遍历每个Sheet
			String  params[]=null;
			String  titleParams[]=null;
			Map<String ,Integer> title=new HashMap<String,Integer>();
			for (int s = 0; s < sheetCount; s++) {
				Sheet sheet = workbook.getSheetAt(s);  
	            int rowCount = sheet.getPhysicalNumberOfRows(); //获取总行数  
	            //遍历每一行  
	            int cellCount=0;
	            for (int r = 0; r < rowCount; r++) { 
	                Row row = sheet.getRow(r); 
	                if(r==0){
	                	 cellCount = row.getPhysicalNumberOfCells();        //获取总列数  
	                	 titleParams=new String[cellCount];
	                }else{
	                 params=new String[cellCount];
	                }  
	                //遍历每一列
	                String cellValues0 = "";
	                String cellValues = "";
	                for (int c = 0; c < cellCount; c++) {
	                	if(row==null)continue;
	                    Cell cell = row.getCell(c);  
	                    if(cell==null){
	                    	cellValues += "---" + "    ";
	                    	continue;
	                    }
	                    Integer cellType = cell.getCellType(); 
	                   
	                    String cellValue = null;  
	                    switch(cellType) {  
	                        case Cell.CELL_TYPE_STRING: //文本  
	                            cellValue = cell.getStringCellValue();  
	                            break;  
	                        case Cell.CELL_TYPE_NUMERIC: //数字、日期  
	                            if(DateUtil.isCellDateFormatted(cell)) {  
	                                cellValue = fmt.format(cell.getDateCellValue()); //日期型  
	                            }  
	                            else {  
	                                cellValue = String.valueOf(cell.getNumericCellValue()); //数字  
	                            }  
	                            break;  
	                        case Cell.CELL_TYPE_BOOLEAN: //布尔型  
	                            cellValue = String.valueOf(cell.getBooleanCellValue());  
	                            break;  
	                        case Cell.CELL_TYPE_BLANK: //空白  
	                            cellValue = cell.getStringCellValue();  
	                            break;  
	                        case Cell.CELL_TYPE_ERROR: //错误  
	                            cellValue = "错误";  
	                            break;  
	                        case Cell.CELL_TYPE_FORMULA: //公式  
	                            cellValue = "错误";  
	                            break;  
	                        default:  
	                            cellValue = "错误";  
	                    } 
	                    if(r==0){ 
	                    	title.put(cellValue, c);
	                    	titleParams [c]=cellValue;       
	                    	}
	                    else{
	                    	params[c]=cellValue;
	                    }    
	                }  
	                if(r==0)continue;
	                if(isCheck){
	                	ListIn.add(new innerParam(title,params,titleParams));
	                	log+=checkData(title,params,titleParams,r+1,timeStr.toString());
					}
	                
	            }
			}

	    return log;
	    
	}
    
	public void 	saveAndprocessData(String userName) throws Exception{
		java.text.DecimalFormat myformat=new java.text.DecimalFormat("0.00");
		for(int j=0;j<ListIn.size();j++){
			Map<String,Integer> title=ListIn.get(j).getInnerTittle();
			String [] params=ListIn.get(j).getInnerParam();
			String titleParam[]=ListIn.get(j).getTitleParams();
		    
	  	  // saveAndprocessData(title,params,titleParams,userName);  //zyz
	         int i=0;
	 		if(params[0]==null||"".equals(params[0]))return;
	 	    Map<String, Object> info=
	 				mapper.selectCounts(new HashMap()); 
	 			    int szxz_id=0; 
	 			    szxz_id= new Long((long) info.get("table_id")).intValue(); 						 
	                 int szxz_switch=title.get("选择（首重+续重）");
	                 int ld_switch=title.get("选择（首重+续重）");
	                 int splx_switch=title.get("选择（首重+续重）");
	                 int zc_switch=title.get("选择（首重+续重）");
	                 int sxqj_switch=title.get("续重区间（是/否）"); 
	                 OriginDesitinationParamlist odp=new OriginDesitinationParamlist();
	 		        List<InternalPrice> ipList=new ArrayList<InternalPrice>();
	 		
	 		 Map mm=new HashMap();
	 		 Map	InfoNm=new HashMap();
	 		mm.put("contract_name", params[0]);
	 		InfoNm=mapper.findContract(mm);
	 				mm.put("transport_name", params[1]);
	 				if(params[2]!=null&&!"".equals(params[2])){
	 					mm.put("product_type_name", params[2]);
	 					mm=	mapper.getInfoByNm(mm);	
	 				}else{
	 					mm=	mapper.getInfoAndTypeIsNull(mm);
	 				}
	        info.putAll(mm);
	 		Date d=new Date();
	 		odp.setItemtype_code((String)info.get("product_type_code"));
	 		odp.setItemtype_name((String)info.get("product_type_name"));
	 		odp.setCreate_time(d);
	 		odp.setCarrier_name(params[1]);
	 		odp.setCarrier_code((String)info.get("transport_code"));
	 		/*if(params[3]!=null&&!"".equals(params[3]))
	 		odp.setOrigination(params[3].replace("市", "").replace("省", ""));*/
	 		//始发地省市区
			if(params[3]!=null&&!"".equals(params[3]))
				odp.setProvince_origin(params[3].replace("市", "").replace("省", ""));
			if(params[4]!=null&&!"".equals(params[4]))
				odp.setCity_origin(params[4].replace("市", "").replace("省", ""));
			if(params[5]!=null&&!"".equals(params[5])){
				odp.setState_origin(params[5]);
			}
			//目的地省市区
	 		if(params[6]!=null&&!"".equals(params[6]))
	 		odp.setProvince_destination(params[6].replace("市", "").replace("省", ""));
	 		odp.setCon_id((Integer)InfoNm.get("con_id"));
	 		if(params[7]!=null&&!"".equals(params[7]))
	 		odp.setCity_destination(params[7].replace("市", "").replace("省", ""));
	 		if(params[8]!=null&&!"".equals(params[8])){
	 			odp.setDistrict_destination(params[8]);
	 		}
	 		InternalPrice	 ls=null;
	 		Map mp=new HashMap();
	 		mp.put("transport_name", params[1]);
			mp.put("contract_name", params[0]);
            	odp.setFormula(Integer.parseInt(params[9].split("\\.")[0]));
	 		//odp.setFormula((int)Double.parseDouble(params[7]));
	 		if("是".equals(params[title.get("选择（首重+续重）")])){
	 			//编辑首重续重对象
	 			szxz_id++;
	 		odp.setSzxz_id(szxz_id);	
	 	    odp.setSzxz_switch(1);  // 首重续重打开打开状态
	 	    String discont= (params[title.get("选择（首重+续重）")+1]==null||"".equals(params[title.get("选择（首重+续重）")+1]))?"1":params[title.get("选择（首重+续重）")+1];
	 	    odp.setSzxz_discount(new BigDecimal(100).subtract(new BigDecimal(myformat.format(new BigDecimal(discont.trim()).multiply(new BigDecimal(100))))));
	 		odp.setSzxz_sz(new BigDecimal(params[title.get("首重（kg）")]));	
	 		odp.setSzxz_price(new BigDecimal(params[title.get("首重价格（元）")].trim()));
	 		//文件内没有设置单位 默认国际单位kg
	 		odp.setSzxz_price_unit("KG");
	 		odp.setSzxz_sz_unit("KG");
	 		if(params[title.get("计抛基数")]!=null&&!"".equals(params[title.get("计抛基数")]))
	 	    odp.setSzxz_jpnum(new BigDecimal(params[title.get("计抛基数")].trim()));
	 		if(params[title.get("计费重量")]!=null&&!"".equals(params[title.get("计费重量")]))
	 	    odp.setJf_weight(new BigDecimal(params[title.get("计费重量")].trim())); 
	 	     if("否".equals(params[title.get("续重区间（是/否）")])){
	 			   InternalPrice  iPrice=new  InternalPrice();
	 			   iPrice.setPrice(new BigDecimal(params[title.get("续重单价（元/kg）")].trim()));
	 			   iPrice.setPrice_unit("元");
	 			   iPrice.setInternal(">0");
	 		       iPrice.setInternal_unit("KG");
	 			   iPrice.setTable_id(szxz_id);
	 			   ipList.add(iPrice);
	 		     }else{   
	 		                 i=	 title.get("区间起始")+1;
	 		    		    for(;i<title.get("区间结束");i++){
	 		    			 ls=new  InternalPrice(); 
	 		    			 ls.setPrice_unit("元");
	 		    			 ls.setInternal(removePlusInfinity(titleParam[i]));	               		    			
	 		    			 ls.setPrice(new BigDecimal(params[i].trim()));
	 		    			 ls.setInternal_unit("KG");
	 		    			 ls.setTable_id(szxz_id);
	 		    			 ls.setCreate_time(d);
	 		    			 ipList.add(ls);
	 		    		   } 
	 		   }
	 		  }else{
	 			  odp.setSzxz_switch(0);    
	 			
	 		}
	 		//零担表数据	
	 		
	 	
	 		if("是".equals(params[title.get("选择（零担）")])){
	 			odp.setLd_switch(1);
	 			szxz_id++;
	 		//零担折扣
	 		String	discont= (params[title.get("选择（零担）")+1]==null||"".equals(params[title.get("选择（零担）")+1]))?"1":params[title.get("选择（零担）")+1];
	 		//最低一票
	 		String	low_price= (params[title.get("最低一票")]);
	 		//最低起运量
	 		String	low_weight= params[title.get("最低起运量(KG)")];
	 		String	low_weight2= params[title.get("最低起运量(立方米)")];
	 		odp.setLd_discount(new BigDecimal(100).subtract(new BigDecimal(myformat.format(new BigDecimal(discont.trim()).multiply(new BigDecimal(100))))));
	 		odp.setLow_price(low_price);
	 		odp.setLow_weight(low_weight);
	 		odp.setLow_cubic(low_weight2);
	 			  if("否".equals(params[title.get("公斤单价区间（是/否）")])){
	 		         //公斤单价（元/KG）  
	 				   odp.setWeightprice_id(szxz_id);
	 				  ls=new InternalPrice();
	 				  ls.setInternal(">0");
	 				  ls.setPrice(new BigDecimal(params[title.get("公斤单价（元/KG）")].trim()));
	 				  ls.setPrice_unit("元");
	 				  ls.setInternal_unit("KG");
	 				  ls.setTable_id(szxz_id);
	 				  ls.setCreate_time(d);
	 				  ipList.add(ls);
	 			  }else if("是".equals(params[title.get("公斤单价区间（是/否）")])){
	 				 odp.setWeightprice_id(szxz_id);	  
	 				  i=title.get("公斤单价区间起始")+1;
	 				  for(;i<title.get("公斤单价区间结束");i++){
	 					ls=new InternalPrice();
	 				    ls.setInternal(removePlusInfinity(titleParam[i]));	  
	 					ls.setPrice(new BigDecimal(params[i].trim()));  
	 					ls.setPrice_unit("元");
	 					ls.setInternal_unit("KG");
	 					ls.setTable_id(szxz_id);
	 					ls.setCreate_time(d);
	 					ipList.add(ls);
	 				  }
	 			  }
	 			//立方单价区间开关处 
	 			  
	 			++szxz_id;
	 			  if("否".equals(params[title.get("立方单价区间（是/否）")])){
	 				odp.setVolumprice_id(szxz_id);
	 				  ls=new InternalPrice();
	 				 ls.setPrice(new BigDecimal(params[title.get("立方单价（元/立方米）")].trim()));
	 				 ls.setInternal(">0");
	 				 ls.setInternal_unit("立方米");
	 				 ls.setPrice_unit("元");
	 				 ls.setTable_id(szxz_id);
	 				 ls.setCreate_time(d);
	 				 ipList.add(ls); 
	 			  } 
	 			  else if("是".equals(params[title.get("立方单价区间（是/否）")])){
	 				odp.setVolumprice_id(szxz_id);
	 				  ls=new InternalPrice();				  
	 				     i=title.get("立方单价区间起始")+1;
	 				    for(;i<title.get("立方单价区间结束");i++){
	 				    	 ls=new InternalPrice();
	 						 ls.setPrice(new BigDecimal(params[i].trim()));
	 						 ls.setInternal(removePlusInfinity(titleParam[i]));
	 						 ls.setInternal_unit("立方米");
	 						 ls.setPrice_unit("元");
	 						 ls.setTable_id(szxz_id);
	 						 ls.setCreate_time(d);
	 						 ipList.add(ls);	
	 				    }	  
	 			  }		
	 		}else{
	 			odp.setLd_switch(0);
	 		}
	 		//产品类型
	 		List <ItemtypePrice>  itemTPList=new ArrayList<ItemtypePrice>();
	 		ItemtypePrice itemTP=null;
	 		if("是".equals(params[title.get("选择（商品类型）")])){
	 			mm.put("tbnm", "tb_itemtype_price");
	 		int itemtype_price_tb_id=	(Integer) mapper.findTableIdByTBNm(mm).get("table_id");
	 		    itemtype_price_tb_id++;
	 		    odp.setIt_switch(1);
	 			String	discont= (params[title.get("选择（商品类型）")+1]==null||"".equals(params[title.get("选择（商品类型）")+1]))?"1":params[title.get("选择（商品类型）")+1]; 
	 			odp.setGood_type_discount(new BigDecimal(100).subtract(new BigDecimal(myformat.format(new BigDecimal(discont.trim()).multiply(new BigDecimal(100))))));
	 			odp.setIt_id(itemtype_price_tb_id);
	 			odp.setPs_price(new BigDecimal((params[title.get("派送费（元/件）")]==null||"".equals(params[title.get("派送费（元/件）")]))?"1":params[title.get("派送费（元/件）")]));
	 			odp.setPs_unit("件");
	 			 i=title.get("运费起始（元/件）")+1;
	 			for(;i<title.get("运费结束（元/件）");i++){
	 				itemTP=new  ItemtypePrice();
	 				itemTP.setItem_type(titleParam[i]);
	 				itemTP.setPrice(new BigDecimal(params[i].trim()));	
	 				itemTP.setUnit("元");
	 				itemTP.setTable_id(itemtype_price_tb_id);
	 				itemTP.setCreate_time(d);
	 				itemTPList.add(itemTP);	
	 			}
	 			itemtype_price_tb_id++;
	 			odp.setZh_price_tab_id(itemtype_price_tb_id);
	 			i=title.get("中转费起始（元/件）")+1;
	 			//中转信息
	 			for(;i<title.get("中转费结束（元/件）");i++){
	 				itemTP=new  ItemtypePrice();
	 				itemTP.setItem_type(titleParam[i]);
	 				itemTP.setPrice(new BigDecimal(params[i].trim()));	
	 				itemTP.setUnit("元");
	 				itemTP.setTable_id(itemtype_price_tb_id);
	 				itemTP.setCreate_time(d);
	 				itemTPList.add(itemTP);	
	 			}
	 		}else{
	 			odp.setIt_switch(0);
	 		}
	 		List<Busprice> busP=new ArrayList<Busprice>();
	 		Busprice bp=null;
	 		
	       //整车信息表  
	 		if("是".equals(params[title.get("选择（整车）")])){
	 			mm.put("tbnm", "tb_busprice");
	 			int bus_price_id=	(Integer) mapper.findTableIdByTBNm(mm).get("table_id");
	 			bus_price_id++;
	 			odp.setBus_id(bus_price_id);
	 			odp.setZc_switch(1);
	 			 i=title.get("车型起始(元/辆)")+1;
	 			  for(;i<title.get("车型结束(元/辆)");i++){
	 				  bp=new Busprice();
	 				  bp.setUnit("元");
	 				  bp.setPrice(new BigDecimal(params[i].trim()));
	 				  bp.setBus_code(titleParam[i]);
	 				  bp.setBus_name(titleParam[i]);
	 				  bp.setBusvolumn_unit("立方米");
	 				  bp.setTable_id(bus_price_id);
	 				  bp.setCreate_time(d);
	 				  busP.add(bp);
	 			  }	
	 		}else{
	 	      odp.setZc_switch(0);		
	 		}
	 		odp.setJf_unit("KG");
	 		odp.setUpdate_time(d);
	 		odp.setStatus(1);
	 		odp.setCreate_user(userName);
	 		odp.setUpdate_user(userName);
	 		odp.setCountry_destination("中国");     //此处默认
	 			this.save((T) odp);
	 			if(ipList!=null&&ipList.size()!=0){
	 				for(InternalPrice ip:ipList){
	 					ip.setCreate_time(d);
	 					ip.setCreate_user(userName);
	 					ip.setUpdate_user(userName);
	 					ip.setUpdate_time(d);
	 					Internal.insert((T) ip);
	 				}
	 			}
	 			if(itemTPList!=null&&itemTPList.size()!=0){
	 				for(ItemtypePrice item:itemTPList){
	 					item.setCreate_user(userName);
	 					item.setUpdate_user(userName);
	 					item.setUpdate_time(d);
	 					it_price.insert((T) item);
	 				}
	 			}
	 			if(busP!=null&&busP.size()!=0){
	 				for(Busprice item:busP){
	 					item.setCreate_user(userName);
	 					item.setUpdate_user(userName);
	 					item.setUpdate_time(d);
	 					bus.insert((T) item);	
	 				}
	 			}	
		}
  	   //zyz
       	                
		
	}
	
	public  String   checkData(Map<String,Integer> title,String [] param,String titleParam[],int row,String timeStr ){
		String log="";
		if(param[0]==null||"".equals(param[0]))return "";
		if(param[0]==null||"".equals(param[0]))log+="您在第"+row+"行的 ‘合同主体’上有空值;";
		if(param[1]==null||"".equals(param[1]))log+="您在第"+row+"行的 ‘供应商’上有空值;";
		if(param[3]==null||"".equals(param[3]))log+="您在第"+row+"行的 ‘始发地’上有空值;";
		if(param[6]==null||"".equals(param[6]))log+="您在第"+row+"行的 ‘目的地省’上有空值;";
		if(param[9]==null||"".equals(param[9]))log+="您在第"+row+"行的 ‘公式编号’上有空值;";
		if(!isNum(param[9]))log+="您在第"+row+"行的 ‘公式编号’填写了非数值;";
		
			try {
				log=checkArea(log,param,row);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				log+="您在第"+row+"行的 地址检验时发生了错误 请联系管理员！";
				e.printStackTrace();
			}
		if(!"".equals(log))return log;
		if("是".equals(param[title.get("选择（首重+续重）")]))log= checkSZXZ(log,title,param,titleParam,row );
		if("是".equals(param[title.get("选择（零担）")])) log=checkdl(log,title,param,titleParam,row );
		if("是".equals(param[title.get("选择（商品类型）")])) log=checkItemTypePrice(log,title,param,titleParam,row );
		if("是".equals(param[title.get("选择（整车）")])) log=checkZc(log,title,param,titleParam,row );
		if("".equals(log)){
			Map mm=
	   				new HashMap();
	   				mm.put("product_type_name", param[2]);	
	   			    mm.put("carrier_name", param[1]);
	   			    mm.put("contract_name",param[0] );
	   			    if(param[3]!=null)
	   			    mm.put("province_origin",param[3].replace("市", "").replace("省", "") );
					if(param[4]!=null)
						mm.put("city_origin",param[4].replace("市", "").replace("省", "") );
					if(param[5]!=null)
						mm.put("state_origin",param[5].replace("市", "").replace("省", "") );

	   			    if(param[6]!=null)
	   				mm.put("province_destination", param[6].replace("市", "").replace("省", ""));
	   			    if(param[7]!=null)
	   				mm.put("city_destination", param[7].replace("市", "").replace("省", ""));
	   			    mm.put("district_destination", param[8]);
	   			Integer i=    mapper.isExisitRecord(mm);
	   			if(i>0){
	   				log+="第"+row+"行产品已存在，请联系管理员！;";
	   						//+ "sql:  select  count(1)  FROM tb_origin_desitination_paramlist B ,tb_contract_basicinfo A  WHERE    A.validity=1 and A.id=b.con_id and B.status=1 and B.carrier_name=#{carrier_name}and B.origination=#{origination} and B.itemtype_name=#{product_type_name} and A.contract_name=#{contract_name} and B.province_destination=#{province_destination} and B.city_destination=#{city_destination} and B.district_destination=#{district_destination}";
	   			}
		}
		if("".equals(log)){
//		 Map mm=new HashMap();
//   		 Map	InfoNm=new HashMap();
//   		mm.put("contract_name", param[0]);
//   		InfoNm=mapper.findContract(mm);//找合同以及公式编号和验证公式编号
//   		 if(InfoNm==null||InfoNm.size()==0) {
//   			log+="第"+row+"行的合同主体与数据库中的信息不符  sql :select  contract_name,id as con_id,contract_owner FROM tb_contract_basicinfo WHERE validity=1 and   contract_name=#{contract_name}";
//   			log+="参数是";
//				for(Object k:mm.keySet()){
//					log+=k.toString()+":"+mm.get(k)+",";
//				}
//				log+=";"; 
//   			 
//   		 }
//   		       String getInfoSql="";
//   				mm.put("transport_name", param[1]);
//   				if(param[2]!=null&&!"".equals(param[2])){
//   					mm.put("product_type_name", param[2]);
//   					getInfoSql=	"sql: SELECT a.transport_code as transport_code,c.product_type_code product_type_code,c.product_type_name product_type_name FROM tb_transport_vendor  a,tb_transport_product_type c where a.validity= '1' and  c.vendor_code=a.transport_code   and  a.transport_name=#{transport_name}  and  c.product_type_name=#{product_type_name}";
//   					for(Object k:mm.keySet()){
//   						getInfoSql+=k.toString()+":"+mm.get(k)+",";
//   					}
//   					mm=	mapper.getInfoByNm(mm);	
//   					getInfoSql+=";";
//   				}else{
//   					getInfoSql=  "sql: SELECT a.transport_code as transport_code  FROM tb_transport_vendor  a where a.validity='1'  and  a.transport_name=#{transport_name}";
//   					for(Object k:mm.keySet()){
//   						getInfoSql+=k.toString()+":"+mm.get(k)+",";
//   					}
//   					getInfoSql+=";";
//   					mm=	mapper.getInfoAndTypeIsNull(mm);
//   				}
//         if(mm==null||mm.size()==0) {
//        	 log+="第"+row+"行数据的产品类型不符"+getInfoSql;
//         }
			Map mp=new HashMap();
			 List list=null;
			mp.put("transport_name", param[1]);
			mp=mapper.getInfoAndTypeIsNull(mp);
			if(mp==null||mp.get("transport_code")==null){
				log+="第"+row+"行 数据库中没有这个物流商或者快递;";
			}else{
				mp.put("transport_name", param[1]);
				mp.put("contract_name", param[0]);
				
				if("1".equals(mp.get("transport_type").toString())||mp.get("transport_type").toString()=="1"){
					mp.put("pricing_formula", param[9]);
					list=	mapper.checkCTF(mp);
				}
	            if("0".equals(mp.get("transport_type").toString())||mp.get("transport_type").toString()=="0"){
	            	
	            	param[9]=  param[9].contains(".")?
	   	            	 param[9].split("\\.")[0]:param[9];
	            	mp.put("cat_type", param[9]);
	            	list=  mapper.checkWLCTF(mp) ;
				}			
			}		
	   
		if(list==null||list.size()==0)  
			log+="第"+row+"行数据不符,请检查有效合同,合同是否维护算法公式；";
		}
		Map ma=new HashMap();
		ma.put("name", param[0]+param[1]+param[2]+param[3]+param[4]+param[5]+param[6]+param[7]+param[8]);
		ma.put("version",timeStr);
		ma.put("row_no", row);
   		mapper.insertCheck(ma);		
		return log;
	}
	public  boolean isNum(String str) {
		  System.out.println(str);
		  System.out.println(str.getClass());
		  System.out.println("---"+str+"+++++");
        try {
            new BigDecimal(str.trim());
            return true;
        } catch (Exception e) {
            return false;
        }
    }
	public  String isNull(Map<String,Integer> title,String [] param,String str,int row) {
		System.out.println("value: "+param[title.get(str)]+"现在是 第"+row+"行数据 非空检测");
        if(param[title.get(str)]==null||"".equals(param[title.get(str)])){
        	
        	return "您在第"+row+"行的 ‘"+str+"’上有空值;";
        }  
            return "";
        
    }
	 public String   checkSZXZ(String log,Map<String,Integer> title,String [] param,String titleParam[],int row ){
		 log+=isNull( title, param,"首重（kg）",row);
		 if(!isNum(param[title.get("首重（kg）")])) log+="您在第 "+row+"行‘首重（kg）’填写了非数值 ;";
		 if(param[title.get("选择（首重+续重）")+1]!=null&&!"".equals(param[title.get("选择（首重+续重）")+1]))
			 if(!isNum(param[title.get("选择（首重+续重）")+1])){
				 log+="您在第 "+row+"行‘选择（首重+续重） 的 单运单折扣’上填写了非数值 ;"; 
			 }else{
				 System.out.println(new BigDecimal(param[title.get("选择（首重+续重）")+1]).compareTo(new BigDecimal(0))>0);
				 System.out.println(new BigDecimal(param[title.get("选择（首重+续重）")+1]).compareTo(new BigDecimal(0))>0);
			log+=new BigDecimal(param[title.get("选择（首重+续重）")+1]).compareTo(new BigDecimal(0))>0?
					new BigDecimal(param[title.get("选择（首重+续重）")+1]).compareTo(new BigDecimal(1))>0?"您在第 "+row+"行‘选择（首重+续重） 的 单运单折扣’上请填写小于1小数;":""
					:"您在第 "+row+"行‘选择（首重+续重） 的 单运单折扣’上填写了非数值 ;";					
			 }
		 log+=isNull( title, param,"首重价格（元）",row);
		 if(!isNum(param[title.get("首重价格（元）")])) log+="您在第 "+row+"行‘首重价格（元）’填写了非数值 ;";
		 if(!"".equals(param[title.get("计抛基数")])&&param[title.get("计抛基数")]!=null){
			 if(!isNum(param[title.get("计抛基数")])) log+="您在第 "+row+"行‘计抛基数’填写了非数值 ;"; 
		 }
		if(!"".equals(param[title.get("计费重量")])&&param[title.get("计费重量")]!=null){
			if(!isNum(param[title.get("计费重量")])) log+="您在第 "+row+"行‘计费重量’填写了非数值 ;"; 
		}
		 if("".equals(param[title.get("续重区间（是/否）")])||param[title.get("续重区间（是/否）")]==null) return log+="您在第"+row+"行的 ‘续重区间（是/否）’上有空值;";
		 if("是".equals(param[title.get("续重区间（是/否）")])){
			 int i=title.get("区间起始")+1;
		    for(;i<title.get("区间结束");i++){
		    	  if(param[i]==null||"".equals(param[i])) log+="您在第 "+row+"行‘首重续重区间上有空值’填写了空值; ";
			      if(!isNum(param[i]))  log+="您在第 "+row+"行‘首重续重区间上有空值’填写了非数值 ;"; 
		    }
			 
		 }
		 return log;
	 } 
	 

    public String   checkdl(String log,Map<String,Integer> title,String [] param,String titleParam[],int row ){
		 if(param[title.get("选择（零担）")+1]!=null&&!"".equals(param[title.get("选择（零担）")+1]))
			 if(!isNum(param[title.get("选择（零担）")+1])){
				 log+="您在第 "+row+"行‘零担 的 单运单折扣’上填写了非数值 ;"; 
			 }else{
			log+=new BigDecimal(param[title.get("选择（零担）")+1]).compareTo(new BigDecimal(0))>0?
					new BigDecimal(param[title.get("选择（零担）")+1]).compareTo(new BigDecimal(1))>0?"您在第 "+row+"行‘零担 的 单运单折扣’上请填写小数;":""
					:"您在第 "+row+"行‘零担 的 单运单折扣’上填写了非数值 ;";					
			 } 
		if(title.get("最低起运量(KG)")==null) log+="您在第 "+row+"行‘最低起运量(KG)’表头填写错误 ;";
		if(title.get("最低起运量(立方米)")==null) log+="您在第 "+row+"行‘最低起运量(立方米)’表头填写错误 ;";
		if(!"".equals(param[title.get("最低一票")])&&param[title.get("最低一票")]!=null) {
    	if(!isNum(param[title.get("最低一票")])){
			 log+="您在第 "+row+"行‘最低一票’上填写了非数值 ;"; 
		 }}
		 if(param[title.get("公斤单价区间（是/否）")]!=null&&!"".equals(param[title.get("公斤单价区间（是/否）")])){  
			 if("否".equals(param[title.get("公斤单价区间（是/否）")])){
				 log+=isNull( title, param,"公斤单价（元/KG）",row); 
				 if(!isNum(param[title.get("公斤单价（元/KG）")])) log+="您在第 "+row+"行‘公斤单价（元/KG）’填写了非数值; "; 	 
			 }else{
				 int i=title.get("公斤单价区间起始")+1;
				    for(;i<title.get("公斤单价区间结束");i++){
				    	  if(param[i]==null||"".equals(param[i])) log+="您在第 "+row+"行‘公斤单价区间’上有空值; ";
					      if(!isNum(param[i]))  log+="您在第 "+row+"行‘公斤单价区间’上填写了非数值 ;"; 
				    }			 
			 } 
		 }//公斤单价区间
		 //立方区间
		 if(param[title.get("立方单价区间（是/否）")]!=null&&!"".equals(param[title.get("立方单价区间（是/否）")])){  
			 if("否".equals(param[title.get("立方单价区间（是/否）")])){
				 log+=isNull( title, param,"立方单价（元/立方米）",row); 
				 if(!isNum(param[title.get("立方单价（元/立方米）")])) log+="您在第 "+row+"行‘立方单价（元/立方米）’填写了非数值; "; 	 
			 }else{
				 int i=title.get("立方单价区间起始")+1;
				    for(;i<title.get("立方单价区间结束");i++){
				    	  if(param[i]==null||"".equals(param[i])) log+="您在第 "+row+"行‘立方单价区间’上有空值; ";
					      if(!isNum(param[i]))  log+="您在第 "+row+"行‘立方单价区间上’填写了非数值; "; 
				    }			 
			 } 
		 }
		 return log;
		 
	 }
	 public String   checkItemTypePrice(String log,Map<String,Integer> title,String [] param,String titleParam[],int row ){
		 if(param[title.get("选择（商品类型）")]!=null&&!"".equals(param[title.get("选择（商品类型）")])){
			 if(param[title.get("选择（商品类型）")+1]!=null&&!"".equals(param[title.get("选择（商品类型）")+1]))
				 if(!isNum(param[title.get("选择（商品类型）")+1])){
					 log+="您在第 "+row+"行‘选择（商品类型） 的 单运单折扣’上填写了非数值 ;"; 
				 }else{
				log+=new BigDecimal(param[title.get("选择（商品类型）")+1]).compareTo(new BigDecimal(0))>0?
						new BigDecimal(param[title.get("选择（商品类型）")+1]).compareTo(new BigDecimal(1))>0?"您在第 "+row+"行‘选择（商品类型） 的 单运单折扣’上请填写小数;":""
						:"您在第 "+row+"行‘选择（商品类型） 的 单运单折扣’上填写了非数值 ;";					
				 }
			 
			 
			   if("是".equals(param[title.get("立方单价区间（是/否）")])){
				  
				  // log+=isNull( title, param,"派送费（元/件）",row);
				  // if(!isNum(param[title.get("派送费（元/件）")])) log+="您在第 "+row+"行‘ 派送费（元/件）’填写了非数值; ";
				   
				   int i=title.get("运费起始（元/件）")+1;
				    for(;i<title.get("运费结束（元/件）");i++){
				    	  if(param[i]==null||"".equals(param[i])) log+="您在第 "+row+"行‘商品运费价区间’上有空值’;";
					      if(!isNum(param[i]))  log+="您在第 "+row+"行‘商品运费价区间’上填写了非数值 ;"; 
				    }	
				    i=title.get("中转费起始（元/件）")+1;
				    for(;i<title.get("中转费结束（元/件）");i++){
				    	  if(param[i]==null||"".equals(param[i])) log+="您在第 "+row+"行‘商品中转费价区间上有空值; ";
					      if(!isNum(param[i]))  log+="您在第 "+row+"行‘商品中转费区间填写了非数值 ;"; 
				    }   
				   
			   }//选择商品类型
		 }else{
			 log+="您在第 "+row+"行‘选择（商品类型）上有空值; ";
		 }
		 return log;
	 }
	 public String   checkZc(String log,Map<String,Integer> title,String [] param,String titleParam[],int row ){
		 if("是".equals(param[title.get("选择（整车）")])){
			 int i=title.get("车型起始(元/辆)")+1;
			    for(;i<title.get("车型结束(元/辆)");i++){
			    	  if(param[i]==null||"".equals(param[i])) log+="您在第 "+row+"行‘整车车型区间上有空值; ";
				      if(!isNum(param[i]))  log+="您在第 "+row+"行‘整车车型区间上填写了非数值; "; 
			    }	
		 } 
		 return log;
	 }

	@Override
	public void removePamaterList(String id) throws  Exception {
		// TODO Auto-generated method stub
		Map params=new HashMap();
		OriginDesitinationParamlist   address=	(OriginDesitinationParamlist) mapper.selectById(Integer.parseInt(id));
		//删除整车信息tb_busprice
		if(address.getZc_switch()!=null&&address.getZc_switch()!=0&&address.getBus_id()!=null){
			params.put("table_id", address.getBus_id());
			bus.deleteByTbId(params);
		}
		//删除产品信息tb_itemtype_price
		if(address.getIt_switch()!=null&&address.getIt_switch()!=0){
			if(address.getIt_id()!=null){
				params.put("table_id",address.getIt_id());
				it_price.deleteByTbId(params);	
			}
		    if(address.getZh_price_tab_id()!=null){
		    	params.put("table_id", address.getZh_price_tab_id());
		    	it_price.deleteByTbId(params);
					
		    }
		}
		//删除首重续重tb_internal_price
		if(address.getSzxz_switch()!=null&&address.getSzxz_switch()!=0&&address.getSzxz_id()!=null)
			{params.put("table_id",address.getSzxz_id());
			Internal.deleteByTbId(params);};//Internal.delete(address.getSzxz_id());
		    //删除零担信息tb_internal_price
		if(address.getLd_switch()!=null&&address.getLd_switch()!=0){	
			if(address.getWeightprice_id()!=null){
			    params.put("table_id",address.getWeightprice_id());
				Internal.deleteByTbId(params);//删除公斤重量区间
			}
			if(address.getVolumprice_id()!=null){
				params.put("table_id",address.getVolumprice_id());
				Internal.deleteByTbId(params);//删除立方单价区间
			}
			}
		mapper.delete(Integer.parseInt(id));
	}
	
	//处理区间中含有字符串 正无穷 问题
	
	 public String removePlusInfinity(String str){
		 if(!str.contains("+∞"))return str;
		 String newStr="";
		 if(str.contains("("))newStr=">";
		 if(str.contains("["))newStr=">=";
		 String num=str.replace("(", "").replace("[", "").split(",")[0];
		 newStr=newStr+num;
		 return newStr;
	 }

	@Override
	public String insertCheckExelSame(String log) throws Exception {
	String LogRow="";
	String LogMsg="";
	String flag="";
	List list=	mapper.getSame();
	if(list==null||list.size()==0){
		mapper.TRUNCATE(null);
		return "";
	  }
	
	for(Object m:list){
		Map<String,Object> mm=(Map<String,Object>)m;
		if(!flag.equals((Object)mm.get("name").toString())){
			LogRow=LogRow+(Object)mm.get("row_no").toString()+";";	
			flag=mm.get("name").toString();
		}
		LogMsg=LogMsg+"第"+(Object)mm.get("row_no").toString()+"行 "+mm.get("name")+" 数据在excel中出现重复;";
	}
		mapper.TRUNCATE(null);
		return LogRow+"分隔符"+LogMsg;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
	   //需要并行的代码
		
	}

	
	 class innerParam {
		private Map<String ,Integer> innerTittle;
		private String []  innerParam;
		public innerParam(Map<String, Integer> innerTittle, String[] innerParam, String[] titleParams) {
			super();
			this.innerTittle = innerTittle;
			this.innerParam = innerParam;
			this.titleParams = titleParams;
		}
		public Map<String, Integer> getInnerTittle() {
			return innerTittle;
		}
		public void setInnerTittle(Map<String, Integer> innerTittle) {
			this.innerTittle = innerTittle;
		}
		public String[] getInnerParam() {
			return innerParam;
		}
		public void setInnerParam(String[] innerParam) {
			this.innerParam = innerParam;
		}
		public String[] getTitleParams() {
			return titleParams;
		}
		public void setTitleParams(String[] titleParams) {
			this.titleParams = titleParams;
		}
		private String  titleParams[];
		
	}

     //批量删除地址参数信息（主次表）
	@Override
	public void removePamaterListbatch(String parameter) throws Exception {
		// TODO Auto-generated method stub
		String[] IDS=  parameter.split(";");
		for(String id: IDS){
			if(id!=null&&!"".equals(id))
			removePamaterList(id);
		}
	}
	public String checkArea(String log,String[] param,int row) throws Exception{
		//param[3]始发地
		//始发地验证
		Map originMap=new HashMap();
//		map.put("area_name",param[3].replace("市", "").replace("省", ""));
//	    Long  li=	areamapper.count(map);
//	    if(li==0||li==null){
//	    	log+="第"+row+"行 的始发地 不对请核对数据库中的信息！";
//	    	return  log;
//	    }
		originMap.put("privence_code", param[3].replace("市", "").replace("省", ""));
		if(param[4]!=null&&!"".equals(param[4])){
			originMap.put("city_code", param[4].replace("市", "").replace("省", ""));
		}else{
			originMap.put("city_code", "");
		}
		if(param[5]!=null&&!"".equals(param[5])){
			originMap.put("state_code", param[5]);
		}else{
			originMap.put("state_code", "");
		}
		int iflagOrigin=	areamapper.check_area(originMap);
		if(iflagOrigin==0){
			log+="第"+row+"行始发地省市区信息不符,请核对数据库中的信息!";
			return log;
		}
		//检验目的地地址  param[6]
	    //query.setArea_name(param[4].replace("市", "").replace("省", ""));
	   	Map  destMap=new HashMap();
		destMap.put("privence_code", param[6].replace("市", "").replace("省", ""));
	   	if(param[7]!=null&&!"".equals(param[7])){
	   		destMap.put("city_code", param[7].replace("市", "").replace("省", ""));
	   	}else{
			destMap.put("city_code", "");
	   	}
	   	if(param[8]!=null&&!"".equals(param[8])){
	   		destMap.put("state_code", param[8]);
	   	}else{
			destMap.put("state_code", "");
	   	}
	   int iflag=	areamapper.check_area(destMap);
	   if(iflag==0)log+="第"+row+"行目的地省市区信息不符，请核对数据库中的信息！";
		return log;
		
	}
	public  static void main(String[]args){
		String  ss="7.0";
		Integer tt=Integer.parseInt(ss.split("\\.")[0]);
		System.out.println(tt);
		
		
	}
}
