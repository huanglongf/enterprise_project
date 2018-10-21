package com.bt.radar.controller;



import java.io.File;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.bt.common.controller.result.Result;
import com.bt.radar.model.*;
import com.bt.radar.service.AreaRadarService;
import com.bt.radar.service.ExpressinfoMasterService;
import com.bt.radar.service.RoutecodeService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.bt.OSinfo;
import com.bt.common.XLSXCovertCSVReader;
import com.bt.lmis.balance.util.CommonUtil;
import com.bt.lmis.basis.service.StoreManagerService;
import com.bt.lmis.model.ExpressbillDetailTemp;
import com.bt.lmis.model.TransportProductType;
import com.bt.lmis.model.TransportVendor;
import com.bt.lmis.model.Warehouse;
import com.bt.lmis.page.PageView;
import com.bt.lmis.page.QueryResult;
import com.bt.lmis.service.TransportProductTypeService;
import com.bt.lmis.service.TransportVendorService;
import com.bt.lmis.service.WarehouseService;
import com.bt.radar.controller.form.AgeingDetailQueryParam;
import com.bt.radar.service.AgeingDetailBackupsService;
import com.bt.radar.service.AgeingDetailService;
import com.bt.radar.service.AgeingDetailUploadService;
import com.bt.radar.service.AgeingDetailUplodaeResultService;
import com.bt.utils.BaseConst;
import com.bt.utils.BigExcelExport;
import com.bt.utils.CommonUtils;
import com.bt.utils.FileUtil;
import com.bt.utils.SessionUtils;


@Controller
@RequestMapping("/control/radar/ageingdetailcontroller")
public class AgeingDetailController {


	private static final Logger logger = Logger.getLogger(AgeingDetailController.class);
	
	@Resource(name = "ageingDetailServiceImpl")
	private AgeingDetailService<AgeingDetail> ageingDetailServiceImpl;
	/**
	 * 快递状态代码服务类
	 */
	@Resource(name = "routecodeServiceImpl")
	private RoutecodeService<Routecode> routecodeServiceImpl;

	/**
	 * 快速信息主表服务类
	 */
	@Resource(name = "expressinfoMasterServiceImpl")
	private ExpressinfoMasterService<ExpressinfoMaster> expressinfoMasterService;

	@Resource(name = "warehouseServiceImpl")
	private WarehouseService<T> warehouseServiceImpl;
	@Resource(name="storeManagerServiceImpl")
	private StoreManagerService<T> storeManagerService;

	@Resource(name="areaRadarServiceImpl")
	private AreaRadarService<Area> areaRadarService;

	@Resource(name= "transportProductTypeServiceImpl")
	private TransportProductTypeService<TransportVendor> transportProductTypeServiceImpl;
	@Resource(name= "transportVendorServiceImpl")
	private TransportVendorService<TransportVendor> transportVendorServiceImpl;
	@Resource(name= "ageingDetailBackupsServiceImpl")
	private AgeingDetailBackupsService<AgeingDetailBackups> ageingDetailBackupsServiceImpl;
	@Resource(name= "ageingDetailUplodaeResultServiceImpl")
	private AgeingDetailUplodaeResultService<AgeingDetailUplodaeResult> ageingDetailUplodaeResultServiceImpl;
	@Resource(name = "ageingDetailUploadServiceImpl")
	private AgeingDetailUploadService<AgeingDetailUpload> ageingDetailUploadServiceImpl;

	
	@RequestMapping("/query")
	public String toForm(AgeingDetailQueryParam queryParam, HttpServletRequest request) throws Exception{
		//物流商
		List list=routecodeServiceImpl.selectTransport_vender();
		request.setAttribute("trans_names", list);
		//产品类型
		List prodeuct_types=expressinfoMasterService.getProduct_type(null);
		request.setAttribute("prodeuct_type", prodeuct_types);
		List physical_warehouses=expressinfoMasterService.getPhysical_Warehouse(null);
		request.setAttribute("physical_warehouses", physical_warehouses);
		//仓库
		List warehouses=expressinfoMasterService.getWarehouse(null);
		request.setAttribute("warehouses", warehouses);
		//店铺
		List stores=expressinfoMasterService.getStore(null);
		request.setAttribute("stores", stores);
		//省
		Area area=new Area();
		area.setPid(1);
		List areas=areaRadarService.findArea(area);
		request.setAttribute("areas", areas);
		if(queryParam.getpCode()!=null&&!"".equals(queryParam.getpCode())){
			//市
			area.setPid(2);
			area.setArea_code(queryParam.getpCode());
			request.setAttribute("city", areaRadarService.findRecordsByP_code(area));
		}
		if(queryParam.getcCode()!=null&&!"".equals(queryParam.getcCode())){
			//区
			area.setPid(3);
			area.setArea_code(queryParam.getcCode());
			request.setAttribute("state", areaRadarService.findRecordsByP_code(area));
		}

		QueryResult<AgeingDetail> qr=null;
		PageView<AgeingDetail> pageView = new PageView<AgeingDetail>(queryParam.getPageSize()==0?BaseConst.pageSize:queryParam.getPageSize(), queryParam.getPage());
		queryParam.setFirstResult(pageView.getFirstResult());
		queryParam.setMaxResult(pageView.getMaxresult());
		qr = ageingDetailServiceImpl.queryAgeingDetail(queryParam);
		pageView.setQueryResult(qr, queryParam.getPage()); 
		request.setAttribute("pageView", pageView);
		request.setAttribute("queryParam", queryParam);
	  return "/radar/ageingmaster/ageing_detail";
	}
	@RequestMapping("/ageingDetailUpload")
	public String ageingDetailUpload(AgeingDetailQueryParam queryParam, HttpServletRequest request) throws Exception{
		request.setAttribute("queryParam", queryParam);
		return "/radar/ageingmaster/ageingDetailUpload";
	}
	
   /* @ResponseBody
	@RequestMapping(value = "/ageingDetailupload")
	public synchronized JSONObject ageingDetailupload(@RequestParam("uploadFile") MultipartFile file, HttpServletRequest req,
													  HttpServletResponse res, AgeingDetailQueryParam queryParam) throws Exception {
		JSONObject result = new JSONObject();
		String username = SessionUtils.getEMP(req).getUsername();
		if (!file.isEmpty()) {
			String filePath = CommonUtil.getAllMessage("config",
					"BALANCE_UPLOAD_RAWDATA_Windows" + OSinfo.getOSname())
					+ new StringBuffer(file.getOriginalFilename()).insert(file.getOriginalFilename().indexOf("."),
							"_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
			// 校验本地文件是否已存在
			// 如果有重名文件存在，就删除文件
			// 这个对象对应的硬盘必须删不能存在，如果已经存在则会抛出IOException异常
			FileUtil.isExistFile(filePath);
			// 转存文件，写入硬盘
			// 这个本质还是一样的打开流传文件， 需要注意 file对应的硬盘中的文件不能存在 需要删除，
			// 否则会抛出文件已经存在且不能删除异常
			file.transferTo(new File(filePath));
			List<String[]> list = XLSXCovertCSVReader.readerExcel(filePath, null, 27);
			List<AgeingDetailBackups> list1 = new ArrayList<AgeingDetailBackups>();
			list.remove(0);
			if (list == null || list.isEmpty() || list.size() == 0) {
				result.put("result_code", "FIE");
				result.put("result_content", "上传文件与所选类型不匹配或上传文件中标题字段有误！");
				return result;
			}
			Integer j =1;
			String uuid = UUID.randomUUID().toString();
			for (int i = 0; i < list.size(); i++) {
				AgeingDetailBackups ageingDetailBackups = new AgeingDetailBackups(list.get(i));
				ageingDetailBackups.setBatId(uuid);
				if(CommonUtils.checkExistOrNot(ageingDetailBackups.getWarehouseName())){
					Warehouse warehouse =warehouseServiceImpl.selectByWarehouseName(ageingDetailBackups.getWarehouseName());
					if(CommonUtils.checkExistOrNot(warehouse)){
						ageingDetailBackups.setWarehouseCode(warehouse.getWarehouse_code());
					}else{
						j=2;
						ageingDetailBackups.setError("仓库名称错误");
					}
				}else{
					j=2;
					ageingDetailBackups.setError("仓库名称不能为空");
				}
				if(CommonUtils.checkExistOrNot(ageingDetailBackups.getStoreName())){
					Store store =storeManagerService.selectByStroeName(ageingDetailBackups.getStoreName());
					if(CommonUtils.checkExistOrNot(store)){
						ageingDetailBackups.setStoreCode(store.getStoreCode());
					}else{
						j=2;
						ageingDetailBackups.setError(ageingDetailBackups.getError()+"店铺名称错误");
					}
				}else{
					j=2;
					ageingDetailBackups.setError(ageingDetailBackups.getError()+"店铺名称不能为空");
				}
				if(CommonUtils.checkExistOrNot(ageingDetailBackups.getpName())){
					String pName =ageingDetailBackups.getpName();
					pName =pName.replace(" ", "");
					if(pName.length()>3){
						if(pName.substring(pName.length()-3,pName.length()).equals("自治州")||pName.substring(pName.length()-3,pName.length()).equals("自治省")||
								pName.substring(pName.length()-3,pName.length()).equals("自治县")){
							pName = pName.substring(0,pName.length()-3);
						}
						if(pName.substring(pName.length()-2,pName.length()).equals("地区")){
							pName = pName.substring(0,pName.length()-2);
						}
					}
					if(pName.substring(pName.length()-1,pName.length()).equals("省")||pName.substring(pName.length()-1,pName.length()).equals("市")||
							pName.substring(pName.length()-1,pName.length()).equals("县")||pName.substring(pName.length()-1,pName.length()).equals("旗")||
							pName.substring(pName.length()-1,pName.length()).equals("区")){
						pName = pName.substring(0,pName.length()-1);
					}
					Area area = new Area();
					area.setArea_name(pName);
					area.setLevel(1);
					List<Area> listarea =areaRadarService.findArea(area);
					if(listarea.size()==1){
						ageingDetailBackups.setpCode(listarea.get(0).getArea_code());
						if(CommonUtils.checkExistOrNot(ageingDetailBackups.getcName())){
							String cName =ageingDetailBackups.getcName();
							cName =cName.replace(" ", "");
							if(cName.length()>3){
								if(cName.substring(cName.length()-3,cName.length()).equals("自治州")||cName.substring(cName.length()-3,cName.length()).equals("自治省")||
										cName.substring(cName.length()-3,cName.length()).equals("自治县")){
									cName = cName.substring(0,cName.length()-3);
								}
								if(cName.substring(cName.length()-2,cName.length()).equals("地区")){
									cName = cName.substring(0,cName.length()-2);
								}
							}
							if(cName.substring(cName.length()-1,cName.length()).equals("省")||cName.substring(cName.length()-1,cName.length()).equals("市")||
									cName.substring(cName.length()-1,cName.length()).equals("县")||cName.substring(cName.length()-1,cName.length()).equals("旗")||
									cName.substring(cName.length()-1,cName.length()).equals("区")){
								cName = cName.substring(0,cName.length()-1);
							}
							area.setArea_name(cName);
							area.setLevel(2);
							area.setPid(listarea.get(0).getId());
							List<Area> listcarea =areaRadarService.findArea(area);
							if(listcarea.size()==1){
								ageingDetailBackups.setcCode(listcarea.get(0).getArea_code());
								if(CommonUtils.checkExistOrNot(ageingDetailBackups.getsName())){
									String sName =ageingDetailBackups.getsName();
									sName =sName.replace(" ", "");
									if(sName.length()>3){
										if(sName.substring(sName.length()-3,sName.length()).equals("自治州")||sName.substring(sName.length()-3,sName.length()).equals("自治省")||
												sName.substring(sName.length()-3,sName.length()).equals("自治县")){
											sName = sName.substring(0,sName.length()-3);
										}
										if(sName.substring(sName.length()-2,sName.length()).equals("地区")){
											sName = sName.substring(0,sName.length()-2);
										}
									}
									if(sName.substring(sName.length()-1,sName.length()).equals("省")||sName.substring(sName.length()-1,sName.length()).equals("市")||
											sName.substring(sName.length()-1,sName.length()).equals("县")||sName.substring(sName.length()-1,sName.length()).equals("旗")||
											sName.substring(sName.length()-1,sName.length()).equals("区")){
										sName = sName.substring(0,sName.length()-1);
									}
									area.setArea_name(sName);
									area.setLevel(3);
									area.setPid(listcarea.get(0).getId());
									List<Area> listsarea =areaRadarService.findLikeArea(area);
									if(listsarea.size()==1){
										ageingDetailBackups.setsCode(listsarea.get(0).getArea_code());
									}else{
										j=2;
										ageingDetailBackups.setError(ageingDetailBackups.getError()+"区信息错误！");
									}
								}
							}else {
								j=2;
								ageingDetailBackups.setError(ageingDetailBackups.getError()+"市信息错误！");
							}
						}else{
							j=2;
							ageingDetailBackups.setError(ageingDetailBackups.getError()+"市不能为空！");
						}
					}else {
						j=2;
						ageingDetailBackups.setError(ageingDetailBackups.getError()+"省信息错误！");
					}
				}else {
					j=2;
					ageingDetailBackups.setError(ageingDetailBackups.getError()+"省不能为空！");
				}
				if(CommonUtils.checkExistOrNot(ageingDetailBackups.getExpressName())){
					TransportVendor transportVendor =transportVendorServiceImpl.selectByName(ageingDetailBackups.getExpressName());
					if(CommonUtils.checkExistOrNot(transportVendor)){
						ageingDetailBackups.setExpressCode(transportVendor.getTransport_code());
						if(CommonUtils.checkExistOrNot(ageingDetailBackups.getProductTypeName())){
							TransportProductType transportProductType =transportProductTypeServiceImpl.selectByNameAndExpressCode(ageingDetailBackups.getProductTypeName(),transportVendor.getTransport_code());
							if(CommonUtils.checkExistOrNot(transportProductType)){
								ageingDetailBackups.setProductTypeCode(transportProductType.getProduct_type_code());
							}else{
								j=2;
								ageingDetailBackups.setError(ageingDetailBackups.getError()+"产品类型名称信息错误！");
							}
						}else {
							j=2;
							ageingDetailBackups.setError(ageingDetailBackups.getError()+"产品类型名称不能为空！");
						}
						
					}else{
						j=2;
						ageingDetailBackups.setError(ageingDetailBackups.getError()+"物流商信息错误！");
					}
				}else{
					j=2;
					ageingDetailBackups.setError(ageingDetailBackups.getError()+"物流商不能为空！");
				}
				if(ageingDetailBackups.getAgeingValue()==null){
					j=2;
					//ageingDetailBackups.setError(ageingDetailBackups.getError()+"时效值不能为空");
				}
				if(ageingDetailBackups.getEmbranceCalTime()==null){
					j=2;
					ageingDetailBackups.setError(ageingDetailBackups.getError()+"揽件截至时间不能为空！");
				}else{
					try {
						SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						java.util.Date date=sdf.parse(ageingDetailBackups.getEmbranceCalTime());
						ageingDetailBackups.setEmbranceCalTime(ageingDetailBackups.getEmbranceCalTime().substring(10, 19));
					} catch (Exception e) {
						j=2;
						ageingDetailBackups.setError(ageingDetailBackups.getError()+"揽件截至时间类型错误！");
					}  
				}
				ageingDetailBackups.setAgeingId(queryParam.getAgeingId());
				ageingDetailBackups.setCreateTime(new Date());
				ageingDetailBackups.setCreateUser(username);
				ageingDetailBackups.setUpdateTime(new Date());
				ageingDetailBackups.setUpdateUser(username);
				list1.add(ageingDetailBackups);
			}
			AgeingDetailUpload ageingDetailUpload = new AgeingDetailUpload();
			ageingDetailUpload.setAgeingId(queryParam.getAgeingId());
			ageingDetailUpload.setBatId(uuid);
			ageingDetailUpload.setCreateTime(new Date());
			ageingDetailUpload.setCreateUser(username);
			ageingDetailUpload.setUpdateTime(new Date());
			ageingDetailUpload.setUpdateUser(username);
			ageingDetailBackupsServiceImpl.insertList(list1);
			AgeingDetailUplodaeResult ageingDetailUplodaeResult = new AgeingDetailUplodaeResult();
			ageingDetailUplodaeResult.setBatId(uuid);
			ageingDetailUplodaeResult.setFlag(j);
			ageingDetailUplodaeResult.setCreateTime(new Date());
			ageingDetailUplodaeResult.setCreateUser(username);
			ageingDetailUplodaeResult.setUpdateTime(new Date());
			ageingDetailUplodaeResult.setUpdateUser(username);
			ageingDetailUplodaeResultServiceImpl.insert(ageingDetailUplodaeResult);
			List<AgeingDetailBackups> listageingDetailBackups = ageingDetailBackupsServiceImpl.selectOrderByBatId(uuid);
			if(listageingDetailBackups.size()!=list1.size()){
				j=2;
				ageingDetailUpload.setFlag(0);
				ageingDetailUploadServiceImpl.insert(ageingDetailUpload);
				result.put("result_code", "FIE");
				result.put("result_content", "上传文件内容有重复，无法通过校验！");
				return result;
			}
			for (AgeingDetailBackups ageingDetailBackups2 : listageingDetailBackups) {
				AgeingDetail ageingDetail =ageingDetailServiceImpl.selectByAgeingDetailBackups(ageingDetailBackups2);
				if(CommonUtils.checkExistOrNot(ageingDetail)){
					ageingDetailBackups2.setError("该条时效下该条时效明细已存在！");
					ageingDetailBackupsServiceImpl.updateByPrimaryKeySelective(ageingDetailBackups2);
					ageingDetailUplodaeResult.setFlag(2);
					ageingDetailUplodaeResultServiceImpl.updateByBatId(ageingDetailUplodaeResult);
					j=2;
				}
			}
			if(j==2){
				ageingDetailUpload.setFlag(0);
				ageingDetailUploadServiceImpl.insert(ageingDetailUpload);
				result.put("result_code", "FIE");
				result.put("result_content", "上传文件内容有误！");
				return result;
			}
			
			ageingDetailUpload.setFlag(1);
			ageingDetailUploadServiceImpl.insert(ageingDetailUpload);
			
			ageingDetailServiceImpl.insertList(listageingDetailBackups);
			result.put("result_code", "SUCCESS");
			result.put("result_content", "上传成功！");
			result.put("success_records", list1.size());
		}
		return result;
	}*/
	
	
	@ResponseBody
	@RequestMapping(value = "/ageingDetailupload")
	public synchronized JSONObject ageingDetailupload(@RequestParam("uploadFile") MultipartFile file, HttpServletRequest req,
													  HttpServletResponse res, AgeingDetailQueryParam queryParam) throws Exception {
		JSONObject result = new JSONObject();
		if (!file.isEmpty()) {
			String filePath = CommonUtil.getAllMessage("config",
					"BALANCE_UPLOAD_RAWDATA_Windows" + OSinfo.getOSname())
					+ new StringBuffer(file.getOriginalFilename()).insert(file.getOriginalFilename().indexOf("."),
							"_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
			// 校验本地文件是否已存在
			// 如果有重名文件存在，就删除文件
			// 这个对象对应的硬盘必须删不能存在，如果已经存在则会抛出IOException异常
			FileUtil.isExistFile(filePath);
			// 转存文件，写入硬盘
			// 这个本质还是一样的打开流传文件， 需要注意 file对应的硬盘中的文件不能存在 需要删除，
			// 否则会抛出文件已经存在且不能删除异常
			file.transferTo(new File(filePath));
			List<String[]> list = XLSXCovertCSVReader.readerExcel(filePath, null, 27);
			String fileName = file.getOriginalFilename();
			String username = SessionUtils.getEMP(req).getUsername();
			queryParam.setCreateUser(username);
			String  batId=UUID.randomUUID().toString();
			ageingDetailServiceImpl.uploadAgeingDetail(list,queryParam,batId);
			Integer i = ageingDetailBackupsServiceImpl.selectCountError(batId);
			AgeingDetailUpload ageingDetailUpload = new AgeingDetailUpload();
			ageingDetailUpload.setAgeingId(queryParam.getAgeingId());
			ageingDetailUpload.setBatId(batId);
			ageingDetailUpload.setCreateTime(new Date());
			ageingDetailUpload.setCreateUser(username);
			ageingDetailUpload.setUpdateTime(new Date());
			ageingDetailUpload.setUpdateUser(username);
			ageingDetailUpload.setFlag(1);
			ageingDetailUpload.setTotalNumber(list.size());
			ageingDetailUpload.setErrorNumber(i);
			ageingDetailUpload.setFileName(fileName);
			ageingDetailUploadServiceImpl.insert(ageingDetailUpload);
		
		}
		
		result.put("result_code", "SUCCESS");
		result.put("result_content", "上传成功！");
		return  result;
	}

	/**
	 * 批量删除店铺时效明细
	 * */
	@RequestMapping("/delAgeingDetail")
	@ResponseBody
	public Result delAgeingDetail(HttpServletRequest request,AgeingDetailQueryParam queryParam){
		Result result;
		try{
			if(StringUtils.isBlank(queryParam.getDetailIdStr()))  return new Result(false,"params error");
			result=ageingDetailServiceImpl.delAgeingDetail(queryParam);
			request.setAttribute("queryParam",queryParam);
		}catch (Exception e){
			result=new Result(false,"AgeingMasterController delAgeingMaster  error");
			logger.error("AgeingMasterController delAgeingMaster  error",e);
		}
		return result;
	}
	
	
	@RequestMapping("/add")
	public String add(AgeingDetailQueryParam queryParam, HttpServletRequest request) throws Exception{
		//物流商
		List list=routecodeServiceImpl.selectTransport_vender();
		request.setAttribute("trans_names", list);
		//产品类型
		request.setAttribute("prodeuct_type", null);
		List physical_warehouses=expressinfoMasterService.getPhysical_Warehouse(null);
		request.setAttribute("physical_warehouses", physical_warehouses);
		request.setAttribute("queryParam", queryParam);
		//仓库
		List warehouses=expressinfoMasterService.getWarehouse(null);
		request.setAttribute("warehouses", warehouses);
		//省
		Area area=new Area();
		area.setPid(1);
		List areas=areaRadarService.findArea(area);
		request.setAttribute("areas", areas);
		if(queryParam.getpCode()!=null&&!"".equals(queryParam.getpCode())){
			//市
			area.setPid(2);
			area.setArea_code(queryParam.getpCode());
			request.setAttribute("city", areaRadarService.findRecordsByP_code(area));
		}
		if(queryParam.getcCode()!=null&&!"".equals(queryParam.getcCode())){
			//区
			area.setPid(3);
			area.setArea_code(queryParam.getcCode());
			request.setAttribute("state", areaRadarService.findRecordsByP_code(area));
		}
	  return "/radar/ageingmaster/addageing_detail";
	}
	
	@RequestMapping("/addAgeingDetail")
	@ResponseBody
	public Result addAgeingDetail(HttpServletRequest request,AgeingDetailQueryParam queryParam){
		Area area=new Area();
		String username = SessionUtils.getEMP(request).getUsername();
		AgeingDetail ageingDetail = ageingDetailServiceImpl.selectByAgeingDetailQueryParam(queryParam);
		if(CommonUtils.checkExistOrNot(ageingDetail)){
			return new Result(false,"该条时效下该条时效明细已存在！");
		}
		area.setArea_code(queryParam.getpCode());
		queryParam.setpName(areaRadarService.findArea(area).get(0).getArea_name());
		area.setArea_code(queryParam.getcCode());
		queryParam.setcName(areaRadarService.findArea(area).get(0).getArea_name());
		if(queryParam.getpCode()!=null){
			if("".equals(queryParam.getpCode())){
				area.setArea_code(queryParam.getsCode());
				queryParam.setsName(areaRadarService.findArea(area).get(0).getArea_name());
			}
		}
		queryParam.setWarehouseName(warehouseServiceImpl.selectByWarehouseCode(queryParam.getWarehouseCode()).getWarehouse_name());
		queryParam.setExpressName(transportVendorServiceImpl.findByCode(queryParam.getExpressCode()).getTransport_name());
		queryParam.setProductTypeName(transportProductTypeServiceImpl.findByCode(queryParam.getProductTypeCode()).getProduct_type_name());
		queryParam.setCreateTime(new Date());
		queryParam.setUpdateTime(new Date());
		queryParam.setCreateUser(username);
		queryParam.setUpdateUser(username);
		SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss");
		try {
			java.util.Date date=sdf.parse(queryParam.getEmbranceCalTime());
			int i =ageingDetailServiceImpl.insert(queryParam);
			return new Result(true,"成功插入"+i+"条");
		} catch (ParseException e) {
			return new Result(false,"揽件截至格式错误，正确格式为HH:mm:ss！");
		}
	}
	
	@RequestMapping("/update")
	public String update(AgeingDetailQueryParam queryParam, HttpServletRequest request) throws Exception{
		//物流商
		List list=routecodeServiceImpl.selectTransport_vender();
		request.setAttribute("trans_names", list);
		//产品类型
		List physical_warehouses=expressinfoMasterService.getPhysical_Warehouse(null);
		request.setAttribute("physical_warehouses", physical_warehouses);
		String ids = request.getParameter("ids");
		String[] split = ids.split(";");
		AgeingDetail ageingDetail = ageingDetailServiceImpl.selectByPrimaryKey(Integer.parseInt(split[0]));
		Map<String, String> map = new HashMap<String,String>();
		map.put("vendor_code", ageingDetail.getExpressCode());
		List prodeuct_types=expressinfoMasterService.getProduct_type(null);
		request.setAttribute("prodeuct_type", prodeuct_types);
		request.setAttribute("queryParam", ageingDetail);
		//仓库
		List warehouses=expressinfoMasterService.getWarehouse(null);
		request.setAttribute("warehouses", warehouses);
		//省
		Area area=new Area();
		area.setPid(1);
		List areas=areaRadarService.findArea(area);
		request.setAttribute("areas", areas);
		if(ageingDetail.getpCode()!=null&&!"".equals(ageingDetail.getpCode())){
			//市
			area.setPid(2);
			area.setArea_code(ageingDetail.getpCode());
			request.setAttribute("city", areaRadarService.findRecordsByP_code(area));
		}
		if(ageingDetail.getcCode()!=null&&!"".equals(ageingDetail.getcCode())){
			//区
			area.setPid(3);
			area.setArea_code(ageingDetail.getcCode());
			request.setAttribute("state", areaRadarService.findRecordsByP_code(area));
		}
	  return "/radar/ageingmaster/updateageing_detail";
	}
	
	@RequestMapping("/updateAgeingDetail")
	@ResponseBody
	public Result updateAgeingDetail(HttpServletRequest request,AgeingDetailQueryParam queryParam){
		Area area=new Area();
		String username = SessionUtils.getEMP(request).getUsername();
		AgeingDetail ageingDetail = ageingDetailServiceImpl.selectByAgeingDetailQueryParam(queryParam);
		if(CommonUtils.checkExistOrNot(ageingDetail)){
			return new Result(false,"更新失败，该条时效下该条时效明细已存在！");
		}
		area.setArea_code(queryParam.getpCode());
		queryParam.setpName(areaRadarService.findArea(area).get(0).getArea_name());
		area.setArea_code(queryParam.getcCode());
		queryParam.setcName(areaRadarService.findArea(area).get(0).getArea_name());
		if(queryParam.getpCode()!=null){
			if("".equals(queryParam.getpCode())){
				area.setArea_code(queryParam.getsCode());
				queryParam.setsName(areaRadarService.findArea(area).get(0).getArea_name());
			}
		}
		queryParam.setWarehouseName(warehouseServiceImpl.selectByWarehouseCode(queryParam.getWarehouseCode()).getWarehouse_name());
		queryParam.setExpressName(transportVendorServiceImpl.findByCode(queryParam.getExpressCode()).getTransport_name());
		queryParam.setProductTypeName(transportProductTypeServiceImpl.findByCode(queryParam.getProductTypeCode()).getProduct_type_name());
		queryParam.setUpdateTime(new Date());
		queryParam.setUpdateUser(username);
		SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss");
		try {
			java.util.Date date=sdf.parse(queryParam.getEmbranceCalTime());
			int i =ageingDetailServiceImpl.update(queryParam);
			return new Result(true,"成功修改"+i+"条");
		} catch (ParseException e) {
			return new Result(false,"揽件截至格式错误，正确格式为HH:mm:ss！");
		}
	}
	

	@RequestMapping("downLoad")
	public void downLoad(HttpServletRequest request, HttpServletResponse res, AgeingDetailQueryParam queryParam) throws Exception {
		Map<String,String> result= new HashMap<String,String>();
		PrintWriter out = null;
		result.put("out_result", "0");
		result.put("out_result_reason","错误");
		List<Map<String, Object>> list = ageingDetailServiceImpl.selectAgeingDetailQueryParam(queryParam);
		try{
			out = res.getWriter();
			HashMap<String, String> cMap = new LinkedHashMap<String, String>();
			cMap.put("ageing_id", "单据号");
			cMap.put("store_name", "店铺名称");
			cMap.put("warehouse_name", "仓库名称");
			cMap.put("express_name", "物流商名称");
			cMap.put("product_type_name", "产品类型名称");
			cMap.put("p_name", "省");
			cMap.put("c_name", "市");
			cMap.put("s_name", "区");
			cMap.put("embrance_cal_time", "揽件截止时间");
			cMap.put("ageing_value", "时效值/天");
			Date time =new Date();
			SimpleDateFormat date = new SimpleDateFormat("yyyyMMddhhmmss");
			String string = date.format(time);
			BigExcelExport.excelDownLoadDatab(list, cMap, string+"时效明细表.xlsx");
			result.put("out_result", "1");
			result.put("out_result_reason", "成功");
			result.put("path", CommonUtils.getAllMessage("config", "COMMON_DOWNLOAD_MAP")+string+"时效明细表.xlsx");
		}catch(Exception e){
			e.printStackTrace();
		}
		out.write(com.alibaba.fastjson.JSONObject.toJSON(result).toString());
		out.flush();
		out.close();
	}
	
	
	
	public static void main(String[] args) {
		
		CommonUtils.checkExistOrNot(null);
		System.out.println(CommonUtils.checkExistOrNot(null));
		System.out.println(CommonUtils.checkExistOrNot(1));
	}
	
}
