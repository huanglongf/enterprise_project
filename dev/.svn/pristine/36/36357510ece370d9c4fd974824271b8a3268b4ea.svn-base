package com.bt.radar.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.tools.ant.taskdefs.Zip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.lmis.code.ServiceSupport;
import com.bt.lmis.page.QueryParameter;
import com.bt.lmis.page.QueryResult;
import com.bt.radar.controller.form.ExpressinfoMasterQueryParam;
import com.bt.radar.dao.ExpressinfoMasterInputlistMapper;
import com.bt.radar.dao.ExpressinfoMasterMapper;
import com.bt.radar.model.ExpressinfoDetail;
import com.bt.radar.model.ExpressinfoMaster;
import com.bt.radar.model.ExpressinfoMasterInput;
import com.bt.radar.model.ExpressinfoMasterInputlist;
import com.bt.radar.service.ExpressinfoMasterService;
import com.bt.utils.BigExcelExport;
import com.bt.utils.CmdUtil;
import com.bt.utils.DateUtil;
import com.bt.utils.ZipUtils;
@Service
public class ExpressinfoMasterServiceImpl<T> extends ServiceSupport<T> implements ExpressinfoMasterService<T> {

	@Autowired
	private ExpressinfoMasterInputlistMapper expressinfoMasterInputlistMapper;
	@Autowired
    private ExpressinfoMasterMapper<T> mapper;

	public ExpressinfoMasterMapper<T> getMapper() {
		return mapper;
	}

	@Override
	public Long selectCount(Map<String, Object> param) throws Exception {
		return mapper.selectCount(param);
	}

	@Override
	public List<T> getProduct_type(Map param) {
		// TODO Auto-generated method stub
		return mapper.getProduct_type(param);
	}

	@Override
	public List<T> getWarehouse(Map param) {
		// TODO Auto-generated method stub
		return mapper.getWarehouse(param);
	}

	@Override
	public List<T> getPhysical_Warehouse(Map param) {
		// TODO Auto-generated method stub
		return mapper.getPhysical_Warehouse(param);
	}                
		
	
	@Override
	public List<T> getStore(Map param) {
		// TODO Auto-generated method stub
		return mapper.getStore(param);
	}

	@Override
	public QueryResult<T> findAllData(QueryParameter qr) {
		// TODO Auto-generated method stub
		QueryResult<T> queryResult = new QueryResult<T>();
		queryResult.setResultlist(mapper.findAllData(qr));
		//queryResult.setTotalrecord(100);
		queryResult.setTotalrecord(mapper.selectCount(qr));
		return queryResult;
	}

	@Override
	public List<T> findDetailsByOrderNO(Map param) {
		// TODO Auto-generated method stub
		return mapper.findDetailsByOrderNo(param);
	}

	@Override
	public List<T> findAlarmDetailsByOrderNO(Map param) {
		// TODO Auto-generated method stub
		return mapper.findAlarmDetailsByOrderNo(param);
	}

	@Override
	public QueryResult<T>  findExpressByCondition(QueryParameter qr) {
		// TODO Auto-generated method stub
		QueryResult<T> queryResult = new QueryResult<T>();
		queryResult.setResultlist(mapper.findExpressByCondition(qr));
		//queryResult.setTotalrecord(100);
	    queryResult.setTotalrecord(mapper.findExpressByConditionCount(qr));
		return queryResult;
	}

	@Override
	public QueryResult<T> findAllDataBy_warnCdn(QueryParameter qr) {
		QueryResult<T> queryResult = new QueryResult<T>();
		queryResult.setResultlist(mapper.findAllDataBy_warnCdn(qr));
		queryResult.setTotalrecord(mapper.findAllDataBy_warnCdnCount(qr));
		return queryResult;
	}

	@Override
	public QueryResult<T> findAllWarninData(QueryParameter qr) {
		// TODO Auto-generated method stub
		QueryResult<T> queryResult = new QueryResult<T>();
		queryResult.setResultlist(mapper.findAllData(qr));
		queryResult.setTotalrecord(100);
		//queryResult.setTotalrecord(mapper.selectWarnTB(qr));
		return queryResult;
	}

	@Override
	public List<T> findAlarmDetailsByOrderNO_ADV(Map em) {
		// TODO Auto-generated method stub
		return mapper.findAlarmDetailsByOrderNo_ADV(em);
	}

	@Override
	public QueryResult<T> findAllData_adv(ExpressinfoMasterQueryParam queryParam) {
		// TODO Auto-generated method stub
		QueryResult<T> queryResult = new QueryResult<T>();
		queryResult.setResultlist(mapper.findAllData_adv(queryParam));
		queryResult.setTotalrecord(100);
		return queryResult;
	}

	@Override
	public String  downLoad(ExpressinfoMasterQueryParam queryParam) {
		// TODO Auto-generated method stub
		Map<String,Object> param=new HashMap();
		int num=0;
		boolean doZip=false;
		try {
		 num=mapper.findExpressByWarningUnionCnt_se(queryParam);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int  times=0;
		if(num%2000000!=0){
			times=num/2000000+1;
		}else{
			times=num/2000000;
		}
		File file=new File("C:\\lmis_export\\"+DateUtil.formatSS(new Date()));
		if(!file.exists())file.mkdirs();
		for(int i=0;i<times;i++){
			if(i==1)doZip=true;
			String commond="";
			commond=parseCmd(queryParam,Integer.toString(i*200000),"200000",file.getPath());
			System.out.println(commond);
			CmdUtil.execCommond(commond);
		}
		String  filepath=doZip(file.getPath());
		if(filepath!=null&&!"".equals(filepath)){
			filepath=filepath.split("lmis_export\\")[1];
		}
		return "DownloadFile\\"+filepath;
	}
	
	public String  doZip(String path){
		String  zipPath=path+"zip";
		try {
			ZipUtils.zip(path, zipPath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return zipPath;
	}
	
	
	public  String   parseCmd(ExpressinfoMasterQueryParam queryParam, String first, String max,String file){
		Properties prop = new Properties();
		try{
			prop.load(this.getClass().getClassLoader().getResourceAsStream("/config.properties"));
			
		}catch(Exception e){
			e.printStackTrace();
		}
		String ip = prop.getProperty("service_ip");
		String write_username = prop.getProperty("write_username");
		String write_password = prop.getProperty("write_password");
		String service_database = prop.getProperty("service_database");
		String csvFileName="";
		StringBuffer commond=new StringBuffer("cmd /c mysql  -u"+write_username+" -p"+write_password+" -h"+ip+" --database "+service_database+" -N -e \"SELECT  \\\"运单号\\\",\\\"作业单号\\\",\\\"平台订单号\\\",\\\"收件人\\\", \\\"电话\\\",\\\"地址\\\",\\\"复核时间\\\", \\\"称重时间\\\",\\\"物流商\\\", \\\"产品类型\\\",\\\"店铺名\\\", \\\"仓库名\\\",\\\"目的地省\\\", \\\"目的地市\\\",\\\"目的地区\\\", \\\"预警类型\\\",\\\"预警类别\\\",\\\"预警级别\\\",\\\"时效时间\\\" ");
		commond.append("UNION SELECT a.waybill ,a.work_no ,a.platform_no,a.shiptoname, a.phone,  a.address,a.check_time,a.weight_time,");
		commond.append("a.express_name , a.producttype_name ,a.store_name,a.warehouse_name , a.provice_name ,a.city_name,a.state_name ,c.warningtype_name,");
		commond.append("case  b.warning_category  when 1 then \\\"超时预警\\\" when 0 then \\\"事件预警\\\" end ,b.warning_level ,case b.efficient_time  when null then \\\"--\\\" end ");
		commond.append(" FROM er_expressinfo_master  a left join ");
		commond.append(" er_waybill_warninginfo_detail b ");
		commond.append(" on a.waybill=b.waybill  left join ");
		commond.append(" er_warninginfo_maintain_master c on b.warningtype_code=c.warningtype_code and c.dl_flag=1 where 1=1 ");
		if(queryParam.getWaybill()!=null&&!"".equals(queryParam.getWaybill())){
			commond.append(" and a.waybill="+queryParam.getWaybill()+" ");	
		}
		if(queryParam.getProducttype_code()!=null&&!"".equals(queryParam.getProducttype_code())){
			commond.append(" and a.producttype_code="+queryParam.getProducttype_code()+" ");	
		}
		if(queryParam.getAddress()!=null&&!"".equals(queryParam.getAddress())){
			commond.append(" and a.address="+queryParam.getAddress()+" ");	
		}
		if(queryParam.getPhysical_warehouse_code()!=null&&!"".equals(queryParam.getPhysical_warehouse_code())){
			commond.append(" and a.systemwh_code="+queryParam.getPhysical_warehouse_code()+" ");	
		}
		if(queryParam.getCity_code()!=null&&!"".equals(queryParam.getCity_code())){
			commond.append(" and a.city_code="+queryParam.getCity_code()+" ");	
		}
		if(queryParam.getExpress_code()!=null&&!"".equals(queryParam.getExpress_code())){
			commond.append(" and a.express_code="+queryParam.getExpress_code()+" ");	
		}
		if(queryParam.getPhone()!=null&&!"".equals(queryParam.getPhone())){
			commond.append(" and a.phone="+queryParam.getPhone()+" ");	
		}
		if(queryParam.getProvice_code()!=null&&!"".equals(queryParam.getProvice_code())){
			commond.append(" and a.provice_code="+queryParam.getProvice_code()+" ");	
		}
		if(queryParam.getPlatform_no()!=null&&!"".equals(queryParam.getPlatform_no())){
			commond.append(" and a.platform_no="+queryParam.getPlatform_no()+" ");	
		}
		if(queryParam.getShiptoname()!=null&&!"".equals(queryParam.getShiptoname())){
			commond.append(" and a.shiptoname="+queryParam.getShiptoname()+" ");	
		}
		if(queryParam.getWarningtype_code()!=null&&!"".equals(queryParam.getWarningtype_code())){
			commond.append(" and b.warningtype_code="+queryParam.getWarningtype_code()+" ");	
		}
		if(queryParam.getWarning_level()!=null&&!"".equals(queryParam.getWarning_level())){
			commond.append(" and b.warningtype_level="+queryParam.getWarning_level()+" ");	
		}
		if(queryParam.getWarning_category()!=null&&!"".equals(queryParam.getWarning_category())){
			commond.append(" and b.warningtype_category="+queryParam.getWarning_category()+" ");	
		}
		if(queryParam.getWarehouse_code()!=null&&!"".equals(queryParam.getWarehouse_code())){
			commond.append(" and b.warehouse_code="+queryParam.getWarehouse_code()+" ");	
		}
		commond.append("LIMIT "+first+" , "+max+" ;\"   | sed \"s/\\t/\",\"/g;s/^/\"/g;s/$/\"/g\"");
		csvFileName=first+"_"+max;
		commond.append(">"+file+"\\"+csvFileName+"data.csv");
		
		return commond.toString();
	}
	

	@Override
	public QueryResult<T> findExpressByConditionUnion(QueryParameter qr) {
		// TODO Auto-generated method stub
		QueryResult<T> queryResult = new QueryResult<T>();
		queryResult.setResultlist(mapper.findExpressByConditionUnion(qr));
		if(qr.getPage()==1)
	   queryResult.setTotalrecord(mapper.findExpressByConditionUnionCnt(qr));
		
	   
		return queryResult;
	}

	@Override
	public QueryResult<T> findExpressByConditionUnionNotCount(QueryParameter qr) {
		// TODO Auto-generated method stub
		QueryResult<T> queryResult = new QueryResult<T>();
		queryResult.setResultlist(mapper.findExpressByConditionUnion(qr));		   
		return queryResult;
	}
	
	
	@Override
	public QueryResult<T> findExpressByWarningUnion(QueryParameter qr) {
		// TODO Auto-generated method stub
		QueryResult<T> queryResult = new QueryResult<T>();
		queryResult.setResultlist(mapper.findExpressByWarningUnion(qr));
		if(qr.getPage()==1)
	    queryResult.setTotalrecord(mapper.findExpressByWarningUnionCnt(qr));
		return queryResult;
	}

	@Override
	public String uploadWaybill(Map param) throws IOException {
		// TODO Auto-generated method stub
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		FileInputStream is=null;
		ExpressinfoMasterInput expressinfoMaster=null;
		String bat_id=UUID.randomUUID().toString();
		Map<String,String> insertParam=new HashMap<String,String>();
		try {
			is = new FileInputStream(param.get("filePath").toString());
			Workbook workbook = WorkbookFactory.create(is); 		//这种方式 Excel 2003/2007/2010 都是可以处理的
			int sheetCount = workbook.getNumberOfSheets(); 			//Sheet的数量 	 
			List<ExpressinfoDetail> listDetail=null;
			List<ExpressinfoMasterInput> listMaster=null;
			for (int s = 0; s < sheetCount; s++) {
				listDetail=new ArrayList<ExpressinfoDetail>();
				listMaster=new ArrayList<ExpressinfoMasterInput>();;
				Sheet sheet = workbook.getSheetAt(s);  
	            int rowCount = sheet.getPhysicalNumberOfRows(); //获取总行数  
	            //遍历每一行  
	           int forIndes= (rowCount-18)%3==0?(rowCount-18)/3:(rowCount-18)/3+1;
	           ExpressinfoDetail expressinfoDetail=null;
	           
	            for (int r = 1; r < rowCount; r++) { 
	            	expressinfoMaster=new ExpressinfoMasterInput();
	            	expressinfoMaster.setLine_number(r+1);
	            	expressinfoMaster.setRemark("第"+(r+1)+"行数据中");
	                Row row = sheet.getRow(r); 
	               int cellCount=row.getLastCellNum();
	                //遍历每一列
	                String waybill="";
	                String cellValues0 = "";
	                String cellValues = "";
	                for (int c = 0; c < cellCount; c++) {
	                    Cell cell = row.getCell(c);  
	                    if(cell==null)continue;
	                    Integer cellType = cell.getCellType(); 
	                   
	                    String cellValue = null;  
	                    switch(cellType) {  
	                        case Cell.CELL_TYPE_STRING: //文本  
	                            cellValue = cell.getStringCellValue();  
	                            break;  
	                        case Cell.CELL_TYPE_NUMERIC: //数字、日期  
	                            if(org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {  
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
	                   switch(c)
	                   {
	                   case 0:expressinfoMaster.setWaybill(cellValue);waybill=cellValue;expressinfoMaster.setBat_id(bat_id);break;
	                   case 1:expressinfoMaster.setExpress_code(cellValue);break;
	                   case 2:expressinfoMaster.setExpress_name(cellValue);break;
	                   case 3:expressinfoMaster.setProducttype_code(cellValue);break;
	                   case 4:expressinfoMaster.setProducttype_name(cellValue);break;
	                   case 5:expressinfoMaster.setWeight_time(DateUtil.StrToTime_other(cellValue));break;
	                   case 6:break;
	                   case 7:break;
	                   case 8:break;
	                   //case 5:expressinfoMaster.setWeight_time(DateUtil.StrToTime(cellValue));break;
	                  // case 6:expressinfoMaster.setPlatform_no(cellValue);break;
	                   //case 7:expressinfoMaster.setPlatform_no(cellValue);break;
	                   //case 8:expressinfoMaster.setPlatform_time(DateUtil.StrToTime(cellValue));break;
	                   case 9:expressinfoMaster.setWarehouse_code(cellValue);break;
	                   case 10:expressinfoMaster.setWarehouse_name(cellValue);break;
	                   case 11:expressinfoMaster.setStore_code(cellValue);break;
	                   case 12:expressinfoMaster.setStore_name(cellValue);break;
	                   case 13:break;
	                   case 14:break;
	                   case 15:break;
	                   // case 13:始发地省
	                   // case 14:始发地市
	                   // case 15:始发地区
	                   case 16:expressinfoMaster.setProvice_name(cellValue);break;
	                   case 17:expressinfoMaster.setCity_name(cellValue);break;
	                   case 18:expressinfoMaster.setState_name(cellValue);break;
	                   case 19:expressinfoMaster.setShiptoname(cellValue);break;
	                   case 20:expressinfoMaster.setPhone(cellValue);break;
	                   case 21:expressinfoMaster.setAddress(cellValue);break;
	                   case 22:break;
	                   case 23:break;
	                   case 24:break;
	                   case 25:break;
	                   case 26:break;
	                   //case 22:重量
	                   //case 23:体积
	                   //case 24:保值
	                   //case 25:是否COD
	                   //case 26:是否报价
	                   default:
	                	   if((c-27)%3==1){
	                		   //商品金额
	                		   expressinfoDetail.setQty(cellValue==null||"".equals(cellValue)?0:Integer.parseInt(cellValue.split("\\.")[0]));
	                		  
	                	   }else if((c-27)%3==2){
	                		   listDetail.add(expressinfoDetail);
	                	   }else if((c-27)%3==0){
	                		   expressinfoDetail=new ExpressinfoDetail();
	                		   expressinfoDetail.setSku_number(cellValue);
	                		   expressinfoDetail.setCreate_time(new Date());
	                		   expressinfoDetail.setCreate_user(param.get("createBy").toString());
	                		   expressinfoDetail.setUpdate_time(new Date());
	                		   expressinfoDetail.setUpdate_user(param.get("createBy").toString());
	                		   expressinfoDetail.setWaybill(waybill);
	                		   expressinfoDetail.setBat_id(bat_id);
	                	   }
	                	   break;
	                   }
	                }  
	                expressinfoMaster.setCreate_time(new Date());
	                expressinfoMaster.setCreate_user(param.get("createBy").toString());
	                listMaster.add(expressinfoMaster);
	            } 
			}
			String dateStr =DateUtil.formatSS(new Date());
			mapper.addExpressMasterInput(listMaster);
			mapper.addExpressDetailsInput(listDetail);
			insertParam.put("bat_id", bat_id);
			insertParam.put("create_user", param.get("createBy").toString());
			insertParam.put("create_time",dateStr);
			insertParam.put("update_time",dateStr);
			insertParam.put("update_user", param.get("createBy").toString());
			insertParam.put("update_user", param.get("createBy").toString());              
			insertParam.put("success_num", "0");
			insertParam.put("fail_num", "0");
			insertParam.put("total_num", Integer.toString(listMaster.size()));
			insertParam.put("flag", "0");
			mapper.insertMasterList(insertParam);
			
			/*//check  校验
			mapper.checkIsNUll(bat_id);
			//indert  插入
		  	mapper.insertMaster(bat_id);
			*/
			
			is.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			is.close();
			e.printStackTrace();
		}	//文件流
		
		return bat_id;
	}

	public static void main(String args[]){
		String cellValue="12.0";
		System.out.println(cellValue.split("\\.")[0]);
		
		
	}

	@Override
	public String getTrbFilePath(String uUID) {
		// TODO Auto-generated method stub
		Map param =new HashMap();
		param.put("bat_id", uUID);
		HashMap<String,String> head=new HashMap<String,String>();
		head.put("remark", "日志信息");
		File f=null;
		List <Map<String,Object>> list=mapper.getTrbFilePath(uUID);
		if(list==null||list.size()==0){
			expressinfoMasterInputlistMapper.updateMaster(param);
			return "";	
		}
		try {
			String str ="waybillData"+ (new SimpleDateFormat("yyyyMMdd-HHmmssSSS")).format(new Date()); 
		 f=	BigExcelExport.excelDownLoadDatab(list, head, str+".xlsx");
		 ExpressinfoMasterInputlist obj=new ExpressinfoMasterInputlist();
		 param.put("wrong_path", f.getName());
		 expressinfoMasterInputlistMapper.updateMaster(param);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return f.getName();
	}

	@Override
	public void uploadTransferWaybill(Map param) {
		// TODO Auto-generated method stub
		mapper.checkIsNUll(param.get("bat_id").toString());
		mapper.insertMaster(param.get("bat_id").toString());
		mapper.insertDetails(param.get("bat_id").toString());
	}

	@Override
	public void updateStatus(Map param) {
		// TODO Auto-generated method stub
		mapper.updateStatus(param);
	}
	
}
