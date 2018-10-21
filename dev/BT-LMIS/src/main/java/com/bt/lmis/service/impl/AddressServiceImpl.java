package com.bt.lmis.service.impl;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.bt.baidumap.AddressQuery;
import com.bt.lmis.code.ServiceSupport;
import com.bt.lmis.controller.form.AddressQueryParam;
import com.bt.lmis.controller.form.ErrorAddressQueryParam;
import com.bt.lmis.controller.form.JkErrorQuery;
import com.bt.lmis.dao.AddressMapper;
import com.bt.lmis.dao.CommonDataMapper;
import com.bt.lmis.dao.ContractBasicinfoMapper;
import com.bt.lmis.dao.TransportVendorMapper;
import com.bt.lmis.model.ErrorAddress;
import com.bt.lmis.model.JkErrorAddressBean;
import com.bt.lmis.page.QueryParameter;
import com.bt.lmis.page.QueryResult;
import com.bt.lmis.service.AddressService;
import com.bt.radar.dao.AreaRadarMapper;
import com.bt.radar.model.Area;
import com.bt.utils.BigExcelExport;
import com.bt.utils.CommonUtils;
import com.bt.utils.SessionUtils;

/** 
* @ClassName: EmployeeServiceImpl 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author Yuriy.Jiang
* @date 2016年5月23日 上午10:51:08 
* 
* @param <T> 
*/
@Service
public class AddressServiceImpl<T> extends ServiceSupport<T> implements AddressService<T> {
	
	@Autowired
	private AreaRadarMapper<T> areaRadarMapper;
	public AreaRadarMapper<T> getAreaRadarMapper() {
		return areaRadarMapper;
	}
	
	@Autowired
	private TransportVendorMapper<T> transportVendorMapper;
	public TransportVendorMapper<T> getTransportVendorMapper() {
		return transportVendorMapper;
	}
	
	@Autowired
	private ContractBasicinfoMapper<T> contractBasicinfoMapper;
	public ContractBasicinfoMapper<T> getContractBasicinfoMapper() {
		return contractBasicinfoMapper;
	}
	
	@Autowired
    private AddressMapper<T> mapper;
	public AddressMapper<T> getMapper() {
		return mapper;
	}
	
	@Autowired
	private CommonDataMapper< T> commonDataMapper;
	public CommonDataMapper<T> getCommonDataMapper() {
		return commonDataMapper;
	}
	
	
	@Override
	public Long selectCount(Map<String, Object> param) throws Exception {
		return null;
	}

	@Override
	public ArrayList<?> getTabData(Map param) {
		if(param.get("tabType").equals("1")){
			return mapper.getTabData(param);
			
		}
		if(param.get("tabType").equals("2")){
			return mapper.getGtTab(param);
			
		}
		if(param.get("tabType").equals("3")){
			return mapper.getBusTypeTab(param);
			
		}		
		return new ArrayList<T>();
		
	}

	@Override
	public void updateXzMainData(Map param) {
		mapper.updateXzMainData(param);
		
	}

	@Override
	public void updateTable(Map param) {
		if(param.get("tabType").equals("1")){
			mapper.updateTable(param);
			
		}
		if(param.get("tabType").equals("2")){
			mapper.updateGtTable(param);
			
		}
		if(param.get("tabType").equals("3")){
			mapper.updateZcTable(param);
			
		}
		
	}

	@Override
	public void delTabData(Map param) {
		if(param.get("type").equals("1")){
			mapper.delTabData(param);
		  
		}
		if(param.get("type").equals("2")){
			mapper.delTabData_gt(param);
			 
		}
		if(param.get("type").equals("3")){
            mapper.delTabData_bs(param);
            
		}
		
	}

	@Override
	public void add(Map param) {
		mapper.add(param);
		
	}

	@Override
	public QueryResult<T> findAllErrorData(QueryParameter queryParameter) {
		QueryResult<T> queryResult = new QueryResult<T>();
		queryResult.setResultlist(mapper.findAllErrorData(queryParameter));
		queryResult.setTotalrecord(mapper.selectErrorCount(queryParameter));
		return queryResult;
		
	}

	@Override
	public Map<String,String> updateErrorData(Map<String,Object>param) {
		Map<String,String> pro_param=new HashMap<String,String>();
		pro_param.put("id", String.valueOf(param.get("id")));
		pro_param.put("out_result", "1");
		pro_param.put("out_result_reason", "成功");
		mapper.updateErrorData(param);
	    mapper.transDataPro_add(pro_param);
	    return pro_param;
	}
	
	@Override
	public File exportAddress(AddressQueryParam queryParam, HttpServletRequest request) throws Exception {
		LinkedHashMap<String, String> title = new LinkedHashMap<String, String>();
		title.put("contract_name", "合同名称");
		title.put("contract_version", "版本号");
		title.put("carrier_name", "物流商名称");
		title.put("itemtype_name", "产品类型");
//		title.put("origination", "始发地");
		title.put("province_origin", "始发地省");
		title.put("city_origin", "始发地市");
		title.put("state_origin", "始发地区");
		title.put("province_destination", "目的地省");
		title.put("city_destination", "目的地市");
		title.put("district_destination", "目的地区");
		title.put("pricing_formula_name", "公式");
		title.put("szxz_sz", "首重(KG)	");
		title.put("szxz_price", "首重价格(元)");
		title.put("internal", "续重区间");
		title.put("price", "续重价格(元)");
		title.put("status", "使用状态(启用/停用)");
		return BigExcelExport.excelDownLoadDatab(mapper.exportAddress(queryParam), title, "始发地目的地报价明细_"+ SessionUtils.getEMP(request).getEmployee_number()+ "_"+ new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+ ".xls");
	
	}
	
	@Override
	public Map<String, Object> getAddress(HttpServletRequest request) {
		return mapper.getAddress(Integer.parseInt(request.getParameter("id")));
		
	}
	
	@Override
	public void updateMainData(Map<String, Object> param) {
		mapper.updateMainData(param);
	}
	
	@Override
	public JSONObject delete(HttpServletRequest request, JSONObject result) throws Exception {
		result= new JSONObject();
		mapper.deleteBatch(CommonUtils.strToIntegerArray(request.getParameter("privIds")));
		result.put("result_code", "SUCCESS");
		result.put("result_content", "删除成功！");
		return result;
		
	}
	
	@Override
	public QueryResult<T> query(QueryParameter queryParameter) throws Exception {
		QueryResult<T> queryResult= new QueryResult<T>();
		queryResult.setResultlist(mapper.query(queryParameter));
		queryResult.setTotalrecord(mapper.count(queryParameter));
		return queryResult;
		
	}

	@Override
	public HttpServletRequest queryParam(HttpServletRequest request) throws Exception {
		// 合同名称集合
		request.setAttribute("contract_name", contractBasicinfoMapper.getContractName());
		request.setAttribute("carrier_name", transportVendorMapper.findAllTransportVendor());
		// 所有省
		List<Area> province= areaRadarMapper.findRecords(new Area(1));
		request.setAttribute("origination", province);
		request.setAttribute("province_destination", province);
		return request;
		
	}

	@Override
	public List<Map<String, Object>> query_export(JkErrorQuery queryParam) {
		// TODO Auto-generated method stub
		return mapper.query_export(queryParam);
	}

	@Override
	public QueryResult<T> getImportMainInfo(ErrorAddressQueryParam queryParam) {
		// TODO Auto-generated method stub
		QueryResult<T> queryResult = new QueryResult<T>();
		queryResult.setResultlist(mapper.getImportMainInfo(queryParam));
		queryResult.setTotalrecord(mapper.getImportMainInfoCount(queryParam));
		return queryResult;
	}

	@Override
	public void insertOrder(List<ErrorAddress> sub_list) {
		// TODO Auto-generated method stub
		mapper.insertOrder(sub_list);
	}

	@Override
	public void checkImport(String bat_id) {
		// TODO Auto-generated method stub
		Map<String,String>param=new HashMap<String,String>();
		param.put("bat_id", bat_id);
		mapper.update_iscomplete(param);
	}

	@Override
	public Map<String, String> transDataInfo(Map<String, String> param) {
		// TODO Auto-generated method stub
		param.put("out_result", "1");
		param.put("out_result_reason","成功");
		mapper.transDataPro(param);
		return param;
	}

	@Override
	public void deleteImport(String bat_id) {
		mapper.deleteImport(bat_id);
	}

	@Override
	public List<ErrorAddress> getImportInfo(String bat_id) {
		// TODO Auto-generated method stub
		List<ErrorAddress> list=mapper.getImportInfo(bat_id);
		if(list==null){
			list=new ArrayList<ErrorAddress>();
		}
		return list;
	}

	@Override
	public QueryResult<T> getImportDetailInfo(JkErrorQuery queryParam) {
		// TODO Auto-generated method stub
		QueryResult<T> queryResult = new QueryResult<T>();
		queryResult.setResultlist(mapper.findAllDetailData(queryParam));
		queryResult.setTotalrecord(mapper.selectDetailCount(queryParam));
		return queryResult;
	}

	@Override
	public List<Map<String, Object>> exportData(JkErrorQuery queryParam) {
		// TODO Auto-generated method stub
		return mapper.exportData(queryParam);
	}

	@Override
	public void anysisData(Map<String, String> param) {
		// TODO Auto-generated method stub
		int pageSize=20000;
		Integer count=mapper.getErrorCount();
			  int t_size = count % pageSize != 0 ? (count/ pageSize + 1):count/ pageSize;
				for(int i=0;i<t_size;i++){
					Map<String,Object> pars=new HashMap<String, Object>();
					pars.put("start_size", i*pageSize);
					pars.put("pageSize",pageSize);
				   List<Map<String,String>>list= mapper.getErrorData(pars);
				   for(int k=0;k<list.size();k++){
					   try {
						   Map<String,String>par=new HashMap<String,String>();
						   String address=list.get(k).get("address");
						   //调用api
							System.out.println(address);
							Map<String, Object> map = AddressQuery.getLatitudeAndLongitude(address);
							System.out.println(map);
							Map<String, Object> map2 = AddressQuery.getAddress(map.get("lat").toString(), map.get("lng").toString());
							System.out.println(map2);
							if("200".equals((String)map2.get("code"))){
						   par.put("id", String.valueOf(list.get(k).get("id")));
						   par.put("province", (String)map2.get("province"));//省
						   par.put("city",(String)map2.get("city"));//市
						   par.put("district",(String)map2.get("district"));//区
						   mapper.update_data(par);
						   mapper.transDataPro_add(par);
						 }
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				   }
		 }
	}

	public void add_error_address2(Map<String, Object> param) {
		// TODO Auto-generated method stub
		Map<String,String>pro_param=new HashMap<String,String>();
		String error_province=(String)param.get("error_province");
		String error_city=(String)param.get("error_city");
		String error_state=(String)param.get("error_state");
		String real_province=(String)param.get("real_province");
		String real_city=(String)param.get("real_city");
		String real_state=(String)param.get("real_state");
		pro_param.put("id", String.valueOf(param.get("id")));
		if(!"".equals(real_province.trim())){
			//查询该错误数据和对应数据是否存在，如果存在就不新增，否则，新增
			JkErrorAddressBean bean=new JkErrorAddressBean();
			bean.setError_area_name(error_province.replace("省","").replace("市", ""));
			bean.setReal_area_name(real_province.replace("省","").replace("市", ""));
			bean.setArea_level("1");
			if(mapper.get_if_exist_address(bean)==0){
			 mapper.add_error_address(bean);
			}
		}
		if(!"".equals(real_city.trim())){
			//查询该错误数据和对应数据是否存在，如果存在就不新增，否则，新增
			JkErrorAddressBean bean=new JkErrorAddressBean();
			bean.setError_area_name(error_city.replace("省","").replace("市", ""));
			bean.setReal_area_name(real_city.replace("省","").replace("市", ""));
			bean.setArea_level("2");
			if(mapper.get_if_exist_address(bean)==0){
				 mapper.add_error_address(bean);
			}
		}
		if(!"".equals(real_state.trim())){
			//查询该错误数据和对应数据是否存在，如果存在就不新增，否则，新增
			JkErrorAddressBean bean=new JkErrorAddressBean();
			bean.setError_area_name(error_state);
			bean.setReal_area_name(real_state);
			bean.setArea_level("3");
			if(mapper.get_if_exist_address(bean)==0){
				 mapper.add_error_address(bean);
			}
		}		
		 mapper.transDataPro_add(pro_param);
	}

	@Override
	public void add_error_address(Map<String, Object> param) {
		// TODO Auto-generated method stub
		String error_address=(String)param.get("error_address");
		String real_address=(String)param.get("real_address");
		String level=(String)param.get("level");
		if(!"".equals(error_address.trim())){
			//查询该错误数据和对应数据是否存在，如果存在就不新增，否则，新增
			JkErrorAddressBean bean=new JkErrorAddressBean();
			bean.setError_area_name(error_address.replace("省","").replace("市", ""));
			bean.setReal_area_name(real_address.replace("省","").replace("市", ""));
			bean.setArea_level(level);
			if(mapper.get_if_exist_address(bean)==0){
			 mapper.add_error_address(bean);
			}
		}
	}

	
	@Override
	public ArrayList<Map<String,String>> get_system_address(Map<String, Object> param) {
		// TODO Auto-generated method stub
		Map<String,String>para=new HashMap<String,String>();
		String level=(String)param.get("level");
		String id=(String)param.get("id");
		para.put("level", level);
		para.put("id", id);
		ArrayList<Map<String,String>>add_list=mapper.get_address_exist_system(para);
		return add_list;
	}

	@Override
	public Map<String, String> refresh_tranaction(Map<String, String> param) {
		// TODO Auto-generated method stub
		param.put("out_result", "1");
		param.put("out_result_reason", "成功");
	   mapper.transDataPro_add(param);
	   return param;
	}
	
	@Override
	public String get_business_code(){
		Map<String,String>param=new HashMap<String,String>();
		param.put("business_type", "1");
		param.put("serial_code", "");
		commonDataMapper.get_business_code(param);
		System.out.println(param.get("serial_code"));
		return param.get("serial_code");
	}

}
