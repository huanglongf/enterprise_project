package com.bt.lmis.controller;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.bt.lmis.code.BaseController;
import com.bt.lmis.controller.form.StorageExpendituresDataSettlementQueryParam;
import com.bt.lmis.controller.form.TransPoolQueryParam;
import com.bt.lmis.model.AddservicefeeBilldataCollect;
import com.bt.lmis.model.AllArea;
import com.bt.lmis.model.AllSingltTray;
import com.bt.lmis.model.AllTray;
import com.bt.lmis.model.AllVolume;
import com.bt.lmis.model.AllVolumeCalculation;
import com.bt.lmis.model.CarrierFeeFlag;
import com.bt.lmis.model.CarrierSosp;
import com.bt.lmis.model.Client;
import com.bt.lmis.model.ContractBasicinfo;
import com.bt.lmis.model.CustomserTransSettleBean;
import com.bt.lmis.model.Employee;
import com.bt.lmis.model.ItemType;
import com.bt.lmis.model.ManagementOtherLadder;
import com.bt.lmis.model.NvitationdataCollect;
import com.bt.lmis.model.OperationFeeDataSettlement;
import com.bt.lmis.model.OperationSettlementRule;
import com.bt.lmis.model.PackagePrice;
import com.bt.lmis.model.SettleDetailBean;
import com.bt.lmis.model.StorageCharge;
import com.bt.lmis.model.StorageDataGroup;
import com.bt.lmis.model.StorageExpendituresDataSettlement;
import com.bt.lmis.model.Store;
import com.bt.lmis.model.TotalArea;
import com.bt.lmis.model.TotalSingltTray;
import com.bt.lmis.model.TotalTray;
import com.bt.lmis.model.TotalVolume;
import com.bt.lmis.model.TotalVolumeCalculation;
import com.bt.lmis.model.Warehouse;
import com.bt.lmis.model.WarehouseArea;
import com.bt.lmis.page.PageView;
import com.bt.lmis.page.QueryResult;
import com.bt.lmis.service.AddservicefeeBilldataCollectService;
import com.bt.lmis.service.AllAreaService;
import com.bt.lmis.service.AllSingltTrayService;
import com.bt.lmis.service.AllTrayService;
import com.bt.lmis.service.AllVolumeCalculationService;
import com.bt.lmis.service.AllVolumeService;
import com.bt.lmis.service.CarrierFeeFlagService;
import com.bt.lmis.service.ClientService;
import com.bt.lmis.service.ContractBasicinfoService;
import com.bt.lmis.service.EmployeeService;
import com.bt.lmis.service.ExpressUsedBalanceService;
import com.bt.lmis.service.FreightSospService;
import com.bt.lmis.service.ItemTypeService;
import com.bt.lmis.service.ManagementOtherLadderService;
import com.bt.lmis.service.NvitationdataCollectService;
import com.bt.lmis.service.OperationFeeDataSettlementService;
import com.bt.lmis.service.OperationSettlementRuleService;
import com.bt.lmis.service.PackageChargeService;
import com.bt.lmis.service.StorageChargeService;
import com.bt.lmis.service.StorageDataGroupService;
import com.bt.lmis.service.StorageExpendituresDataSettlementService;
import com.bt.lmis.service.StoreService;
import com.bt.lmis.service.TotalAreaService;
import com.bt.lmis.service.TotalSingltTrayService;
import com.bt.lmis.service.TotalTrayService;
import com.bt.lmis.service.TotalVolumeCalculationService;
import com.bt.lmis.service.TotalVolumeService;
import com.bt.lmis.service.TransPoolDetailService;
import com.bt.lmis.service.WarehouseAreaService;
import com.bt.lmis.service.WarehouseService;
import com.bt.utils.BaseConst;
import com.bt.utils.CommonUtils;
import com.bt.utils.DateUtil;
import com.bt.utils.IntervalValidationUtil;
import com.bt.utils.SessionUtils;

/** 
* @ClassName: SospController 
* @Description: TODO(仓储操作耗材打包费合同 ［StorageOperationSuppliesPackaging]) 
* @author Yuriy.Jiang
* @date 2016年6月12日 下午3:01:22 
*  
*/
@Controller
@RequestMapping("/control/sospController")
public class SospController extends BaseController {

	private static final Logger logger = Logger.getLogger(SospController.class);
	
	@Resource(name="carrierFeeFlagServiceImpl")
	private CarrierFeeFlagService<CarrierFeeFlag> carrierFeeFlagServiceImpl;
	
	@Resource(name="operationFeeDataSettlementServiceImpl")
	private OperationFeeDataSettlementService<OperationFeeDataSettlement> OperationFeeDataSettlementService;
	
	@Resource(name="expressUsedBalanceServiceImpl")
	private ExpressUsedBalanceService expressUsedBalanceService;
	
	@Resource(name="freightSospServiceImpl")
	private FreightSospService<CarrierSosp> freightSospServiceImpl;
	
	@Resource(name="packageChargeServiceImpl")
	private PackageChargeService<PackagePrice> packageChargeServiceImpl;

	@Resource(name="addservicefeeBilldataCollectServiceImpl")
	private AddservicefeeBilldataCollectService<AddservicefeeBilldataCollect> addservicefeeBilldataCollectServiceImpl;
	
	@Resource(name="managementOtherLadderServiceImpl")
	private ManagementOtherLadderService managementOtherLadderService;
	
	/**
	 * 耗材费明细数据汇总服务类
	 */
	@Resource(name = "nvitationdataCollectServiceImpl")
	private NvitationdataCollectService<NvitationdataCollect> nvitationdataCollectService;
	/**
	 * 店铺服务类
	 */
	@Resource(name="storeServiceImpl")
	private StoreService<Store> storeServiceImpl;
	/**
	 * 客户服务类
	 */
	@Resource(name="clientServiceImpl")
	private ClientService<Client> clientServiceImpl;
	/**
	 * 合同服务类
	 */
	@Resource(name="contractBasicinfoServiceImpl")
	private ContractBasicinfoService<ContractBasicinfo> contractBasicinfoServiceImpl;
	/**
	 *  用户服务类
	 */
	@Resource(name="employeeServiceImpl")
	private EmployeeService<Employee> employeeServiceImpl;
	/**
	 * 仓储费规则表服务类
	 */
	@Resource(name = "storageChargeServiceImpl")
	private StorageChargeService<StorageCharge> storageChargeService;
	/**
	 * 仓库表服务类
	 */
	@Resource(name = "warehouseServiceImpl")
	private WarehouseService<Warehouse> warehouseService;
	/**
	 * 仓库区域表服务类
	 */
	@Resource(name = "warehouseAreaServiceImpl")
	private WarehouseAreaService<WarehouseArea> warehouseAreaService;
	/**
	 * 商品类型表服务类
	 */
	@Resource(name = "itemTypeServiceImpl")
	private ItemTypeService<ItemType> itemTypeService;
	/**
	 * 超过部分占用面积表服务类
	 */
	@Resource(name = "totalAreaServiceImpl")
	private TotalAreaService<TotalArea> totalAreaService;
	/**
	 * 总占用面积表服务类
	 */
	@Resource(name = "allAreaServiceImpl")
	private AllAreaService<AllArea> allAreaServiceImpl;
	/**
	 * 超过部分体积表服务类
	 */
	@Resource(name = "totalVolumeServiceImpl")
	private TotalVolumeService<TotalVolume> totalVolumeService;
	/**
	 * 总占用体积表服务类
	 */
	@Resource(name = "allVolumeServiceImpl")
	private AllVolumeService<AllVolume> allVolumeService;
	/**
	 * 超过部分体积反推表服务类
	 */
	@Resource(name = "totalVolumeCalculationServiceImpl")
	private TotalVolumeCalculationService<TotalVolumeCalculation> totalVolumeCalculationService;
	/**
	 * 总占用部分体积反推表服务类
	 */
	@Resource(name = "allVolumeCalculationServiceImpl")
	private AllVolumeCalculationService<AllVolumeCalculation> allVolumeCalculationService;
	/**
	 * 超过托盘数结算表服务类
	 */
	@Resource(name = "totalTrayServiceImpl")
	private TotalTrayService<TotalTray> totalTrayService;
	@Resource(name = "allTrayServiceImpl")
	private AllTrayService<AllTray> allTrayService;
	/**
	 * 超过托盘数反推表服务类
	 */
	@Resource(name = "totalSingltTrayServiceImpl")
	private TotalSingltTrayService<TotalSingltTray> totalSingltTrayService;
	/**
	 * 总占用托盘数反推表服务类
	 */
	@Resource(name = "allSingltTrayServiceImpl")
	private AllSingltTrayService<AllSingltTray> allSingltTrayService;
	/**
	 * 操作费结算规则表服务类
	 */
	@Resource(name = "operationSettlementRuleServiceImpl")
	private OperationSettlementRuleService<OperationSettlementRule> operationSettlementRuleService;

	/**
	 * 仓储费明细数据表服务类
	 */
	@Resource(name = "storageExpendituresDataSettlementServiceImpl")
	private StorageExpendituresDataSettlementService<StorageExpendituresDataSettlement> storageExpendituresDataSettlementService;

	/**
	 * 客户汇总服务类
	 */
	@Resource(name="transPoolDetailServiceImpl")
	private TransPoolDetailService<SettleDetailBean>transPoolDetailServiceImpl;
	
	
	/**
	 * 仓储费明细汇总表服务类
	 */
	@Resource(name = "storageDataGroupServiceImpl")
	private StorageDataGroupService<StorageDataGroup> storageDataGroupService;
	
	/** 
	* @Title: toForm 
	* @Description: TODO(合同编辑) 
	* @param @param map
	* @param @param request
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws 
	*/
	@RequestMapping("/toForm")
	public String toForm(ModelMap map, HttpServletRequest request){
		//当前登录的用户Session
		Employee employee = SessionUtils.getEMP(request);
		map.addAttribute("employee",employee);
		//合同ID
		String cbid = request.getParameter("id");
		//获取仓库
		List<Map<String, Object>> wareHouseList = warehouseService.findAll();
		map.addAttribute("wareHouseList",wareHouseList);
		//获取商品类型
		List<Map<String, Object>> itemTypeList = itemTypeService.findAll();
		map.addAttribute("itemTypeList",itemTypeList);
		Map<String, Date> datemap = DateUtil.getCurrentWeekMondaySunDay();
		map.addAttribute("sdate",DateUtil.formatDate(datemap.get("start")));
		map.addAttribute("edate",DateUtil.formatDate(datemap.get("end")));
		if(null==cbid || Integer.valueOf(cbid)==0){
			//1.新增基础信息初始化start
			List<Map<String, Object>> storeList = storeServiceImpl.findAll();
			List<Map<String, Object>> clientList = clientServiceImpl.findAll();
			map.addAttribute("storeList",storeList);
			map.addAttribute("clientList",clientList);
			map.addAttribute("contract_version","1.0");
		}else if(null!=cbid && Integer.valueOf(cbid)!=0){
			//1.加载基础信息start
			Map<String, Object> baseinfo = baseInfo(request);
			List<OperationSettlementRule> osrList = operationSettlementRuleService.findByCBID(cbid);
			map.addAttribute("createEMP",baseinfo.get("createEMP"));
			map.addAttribute("updateEMP",baseinfo.get("updateEMP"));
			map.addAttribute("cb",baseinfo.get("cb"));
			map.addAttribute("osr",0!=osrList.size()?osrList.get(0):null);
			map.addAttribute("contract_version",baseinfo.get("contract_version"));
			map.addAttribute("storeList",baseinfo.get("storeList"));
			map.addAttribute("clientList",baseinfo.get("clientList"));
			int con_id = Integer.parseInt(cbid);
			map.addAttribute("pP", packageChargeServiceImpl.getPackagePrice(con_id));
			map.addAttribute("carrierList", freightSospServiceImpl.loadCarrier(con_id));
			map.addAttribute("carrierFeeFlag", carrierFeeFlagServiceImpl.getCarrierFeeFlag(con_id));
			
			List<ManagementOtherLadder> managementOtherLadderList = null;
			ManagementOtherLadder managementOtherLadder = new ManagementOtherLadder();
			managementOtherLadder.setContractId(con_id);
			managementOtherLadder.setType("ccf");
			managementOtherLadderList = managementOtherLadderService.findList(managementOtherLadder);
			map.addAttribute("ccfFeeFlag", managementOtherLadderList != null && managementOtherLadderList.size() > 0);
			managementOtherLadder.setType("czf");
			managementOtherLadderList = managementOtherLadderService.findList(managementOtherLadder);
			map.addAttribute("czfFeeFlag", managementOtherLadderList != null && managementOtherLadderList.size() > 0);
			managementOtherLadder.setType("hcf");
			managementOtherLadderList = managementOtherLadderService.findList(managementOtherLadder);
			map.addAttribute("hcfFeeFlag", managementOtherLadderList != null && managementOtherLadderList.size() > 0);
			managementOtherLadder.setType("dbf");
			managementOtherLadderList = managementOtherLadderService.findList(managementOtherLadder);
			map.addAttribute("dbfFeeFlag", managementOtherLadderList != null && managementOtherLadderList.size() > 0);
			managementOtherLadder.setType("zzfwf");
			managementOtherLadderList = managementOtherLadderService.findList(managementOtherLadder);
			map.addAttribute("zzfwfFeeFlag", managementOtherLadderList != null && managementOtherLadderList.size() > 0);
			
			//仓储费主表格
			List<StorageCharge> storageChargeList = storageChargeService.findByID(cbid);
			if(storageChargeList.size()!=0){
				map.addAttribute("tbMain",storageChargeList);
			}
		}
		return "/lmis/sosp_form";
	}
	
	/** 
	* @Title: saveORupdate 
	* @Description: TODO(新增 or 修改) 
	* @param @param map
	* @param @param req
	* @param @param res    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	@RequestMapping("/saveMain")
	public void saveMain(ContractBasicinfo cb,ModelMap map,HttpServletRequest req,HttpServletResponse res){
		JSONObject json = new JSONObject();
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		try {
			out = res.getWriter();
			String status = req.getParameter("status");
			if(null!=status && status.equals("3")){
				cb.setContract_owner(req.getParameter("store"));
			}else if(null!=status && status.equals("4")){
				cb.setContract_owner(req.getParameter("client"));
			}
			cb.setContract_type(status);
			cb.setValidity("1");
	        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	        String now = format.format(new Date());
	        if(null == cb.getId() || cb.getId().equals("")){
	        	String reservation = req.getParameter("reservation");
				Map<String,String> start_end = spiltDateString(reservation);
				cb.setContract_start(start_end.get("startDate"));
				cb.setContract_end(start_end.get("endDate"));
				cb.setCreate_time(now);
				cb.setUpdate_time(now);
				cb.setUpdate_user(cb.getCreate_user());
				contractBasicinfoServiceImpl.save(cb);
				CarrierFeeFlag carrierFeeFlag = new CarrierFeeFlag();
				carrierFeeFlag.setCon_id(Integer.parseInt(cb.getId()));
				carrierFeeFlag.setCreate_by(SessionUtils.getEMP(req).getEmployee_number());
				carrierFeeFlagServiceImpl.createCarrierFeeFlag(carrierFeeFlag);
				json.put("cFFId", carrierFeeFlag.getId());
				json.put("id", cb.getId());
	        } else {
				cb.setUpdate_time(now);
				Employee employee = SessionUtils.getEMP(req);
				cb.setUpdate_user(String.valueOf(employee.getId()));
				contractBasicinfoServiceImpl.update(cb);
				json.put("id", cb.getId());
	        }
			json.put("status", "y");
			json.put("info", "操作成功!");
		} catch(Exception e){
			logger.error(e);
			json.put("status", "n");
			json.put("info", "操作失败!");
		}
		out.write(json.toString());
		out.flush();
		out.close();
	}
	
	
	/** 
	* @Title: baseInfo 
	* @Description: TODO(获取基础信息)
	* @param @param req
	* @param @return    设定文件 
	* @return Map<String,Object>    返回类型 
	* @throws 
	*/
	public Map<String,Object> baseInfo(HttpServletRequest req){
		Map<String, Object> returnMap = new HashMap<String, Object>();
		List<Map<String, Object>> storeList = storeServiceImpl.findAll();
		List<Map<String, Object>> clientList = clientServiceImpl.findAll();
		returnMap.put("storeList", storeList);
		returnMap.put("clientList", clientList);
		try {
			ContractBasicinfo cb = contractBasicinfoServiceImpl.findById(Integer.valueOf(req.getParameter("id")));
			Map<String, Object> createEMP = employeeServiceImpl.findEmpById(Integer.valueOf(cb.getCreate_user()));
			Map<String, Object> updateEMP = employeeServiceImpl.findEmpById(Integer.valueOf(cb.getUpdate_user()));
			returnMap.put("cb", cb);
			returnMap.put("contract_version", cb.getContract_version());
			returnMap.put("createEMP", createEMP);
			returnMap.put("updateEMP", updateEMP);
		} catch (NumberFormatException e) {
			logger.error(e);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return returnMap;
	}
	
	/** 
	* @Title: getArea 
	* @Description: TODO(根据仓库ID查询区域集合) 
	* @param @param map
	* @param @param req
	* @param @param res    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	@RequestMapping("/getArea")
	public void getArea(ModelMap map,HttpServletRequest req,HttpServletResponse res){
		JSONObject json = new JSONObject();
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		try {
			out = res.getWriter();
			String whid = req.getParameter("id");
			List<Map<String, Object>> whList = warehouseAreaService.findByWhid(Integer.valueOf(whid));
			json.put("whList", whList);
			json.put("status", "y");
			json.put("info", "操作成功!");
		} catch(Exception e){
			logger.error(e);
			json.put("status", "n");
			json.put("info", "操作失败!");
		}
		out.write(json.toString());
		out.flush();
		out.close();
	}
	/** 
	* @Title: saveGDFY 
	* @Description: TODO(1.保存固定费用) 
	* @param @param map
	* @param @param req
	* @param @param res    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	@RequestMapping("/saveGDFY")
	public void saveGDFY(ModelMap map,HttpServletRequest req,HttpServletResponse res){
		JSONObject json = new JSONObject();
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		try {
			out = res.getWriter();
			//固定信息
			String ssc_cb_id = req.getParameter("ssc_cb_id");
			String reservation2 = req.getParameter("reservation2");
			String ssc_whs_id = req.getParameter("ssc_whs_id");
			String ssc_area_id = req.getParameter("ssc_area_id");
			String ssc_item_type = req.getParameter("ssc_item_type");
			String CCF = req.getParameter("CCF");
	    	//固定费用
			String ssc_fixedcost = req.getParameter("ssc_fixedcost");
	    	String ssc_fixedcost_unit = req.getParameter("ssc_fixedcost_unit");
			StorageCharge sc = new StorageCharge();
			//处理日期
			Map<String, String> dateMap = spiltDateString(reservation2);
			sc.setSsc_starttime(dateMap.get("startDate"));
			sc.setSsc_endtime(dateMap.get("endDate"));
			sc.setSsc_whs_id(Integer.valueOf(ssc_whs_id));
			sc.setSsc_area_id(Integer.valueOf(ssc_area_id));
			sc.setSsc_item_type(Integer.valueOf(ssc_item_type));
			sc.setSsc_sc_type(Integer.valueOf(CCF));
			sc.setSsc_fixedcost(ssc_fixedcost);
			sc.setSsc_fixedcost_unit(Integer.valueOf(ssc_fixedcost_unit));
			//查询插入数据是否存在
			List<StorageCharge> list = storageChargeService.queryWAS(sc);
			sc.setSsc_cb_id(Integer.valueOf(ssc_cb_id));
			if(list.size()==0){
				storageChargeService.save(sc);
			}else{
				sc.setSsc_id(list.get(0).getSsc_id());
				storageChargeService.update(sc);
			}
			json.put("status", "y");
			json.put("info", "操作成功!");
		} catch(Exception e){
			logger.error(e);
			json.put("status", "n");
			json.put("info", "操作失败!");
		}
		out.write(json.toString());
		out.flush();
		out.close();
	}
	
	/** 
	* @Title: saveMJJSWJT 
	* @Description: TODO(保存-商品实际占用面积-无阶梯) 
	* @param @param map
	* @param @param req
	* @param @param res    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	@RequestMapping("/saveMJJSWJT")
	public void saveMJJSWJT(ModelMap map,HttpServletRequest req,HttpServletResponse res){
		JSONObject json = new JSONObject();
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		try {
			out = res.getWriter();
			//固定信息
			String ssc_cb_id = req.getParameter("ssc_cb_id");
			String reservation2 = req.getParameter("reservation2");
			String ssc_whs_id = req.getParameter("ssc_whs_id");
			String ssc_area_id = req.getParameter("ssc_area_id");
			String ssc_item_type = req.getParameter("ssc_item_type");
			String CCF = req.getParameter("CCF");
			String MJ = req.getParameter("MJ");
	    	//商品实际占用面积-无阶梯
			String ssc_area_price = req.getParameter("ssc_area_price");
	    	String ssc_area_price_unit = req.getParameter("ssc_area_price_unit");
			StorageCharge sc = new StorageCharge();
			//处理日期
			Map<String, String> dateMap = spiltDateString(reservation2);
			sc.setSsc_cb_id(Integer.valueOf(ssc_cb_id));
			sc.setSsc_starttime(dateMap.get("startDate"));
			sc.setSsc_endtime(dateMap.get("endDate"));
			sc.setSsc_whs_id(Integer.valueOf(ssc_whs_id));
			sc.setSsc_area_id(Integer.valueOf(ssc_area_id));
			sc.setSsc_item_type(Integer.valueOf(ssc_item_type));
			sc.setSsc_sc_type(Integer.valueOf(CCF));
			sc.setSsc_area_price(ssc_area_price);
			sc.setSsc_occupied_area_type(Integer.valueOf(MJ));
			sc.setSsc_area_price_unit(Integer.valueOf(ssc_area_price_unit));
			storageChargeService.save(sc);
			json.put("status", "y");
			json.put("info", "操作成功!");
		} catch(Exception e){
			logger.error(e);
			json.put("status", "n");
			json.put("info", "操作失败!");
		}
		out.write(json.toString());
		out.flush();
		out.close();
	}
	
	/** 
	* @Title: saveMJJSCGBF 
	* @Description: TODO(保存-商品实际占用面积-超过部分阶梯) 
	* @param @param map
	* @param @param req
	* @param @param res    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	@RequestMapping("/saveMJJSCGBF")
	public void saveMJJSCGBF(ModelMap map,HttpServletRequest req,HttpServletResponse res){
		JSONObject json = new JSONObject();
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		try {
			out = res.getWriter();
			//固定信息
			String ssc_cb_id = req.getParameter("ssc_cb_id");
			String reservation2 = req.getParameter("reservation2");
			String ssc_whs_id = req.getParameter("ssc_whs_id");
			String ssc_area_id = req.getParameter("ssc_area_id");
			String ssc_item_type = req.getParameter("ssc_item_type");
			String CCF = req.getParameter("CCF");
			String MJ = req.getParameter("MJ");
	    	//商品实际占用面积-超过部分
			String sta_price = req.getParameter("sta_price");
	    	String sta_price_unit = req.getParameter("sta_price_unit");
	    	String sta_section = req.getParameter("sta_section");
			StorageCharge sc = new StorageCharge();
			TotalArea ta = new TotalArea();
			//处理日期
			Map<String, String> dateMap = spiltDateString(reservation2);
			//table id
			UUID tableid = UUID.randomUUID();
			sc.setSsc_cb_id(Integer.valueOf(ssc_cb_id));
			sc.setSsc_occupied_area_type(Integer.valueOf(MJ));
			sc.setSsc_starttime(dateMap.get("startDate"));
			sc.setSsc_endtime(dateMap.get("endDate"));
			sc.setSsc_whs_id(Integer.valueOf(ssc_whs_id));
			sc.setSsc_area_id(Integer.valueOf(ssc_area_id));
			sc.setSsc_item_type(Integer.valueOf(ssc_item_type));
			sc.setSsc_sc_type(Integer.valueOf(CCF));
			ta.setSta_section(sta_section);
			ta.setSta_section(sta_section);
			ta.setSta_price(sta_price);
			ta.setSta_price_unit(Integer.valueOf(sta_price_unit));
			ta.setSta_status(1);
			//超过部分
			//查询插入数据是否存在
			List<StorageCharge> list = storageChargeService.queryWAS(sc);
			if(list.size()==0){
				sc.setSsc_over_part_area_tableid(tableid.toString());
				ta.setSta_cb_id(tableid.toString());
				storageChargeService.saveAreaCGBF(ta, sc);
			}else{
				sc.setSsc_id(list.get(0).getSsc_id());
				sc.setSsc_over_part_area_tableid(tableid.toString());
				ta.setSta_cb_id(list.get(0).getSsc_over_part_area_tableid());
				if (null!=list.get(0).getSsc_over_part_area_tableid() && !list.get(0).getSsc_over_part_area_tableid().equals("")) {
					List<Map<String, Object>> taList = totalAreaService.findByCBID(list.get(0).getSsc_over_part_area_tableid());
					for (int i = 0; i < taList.size(); i++) {
						IntervalValidationUtil.isExist(sta_section, taList.get(i).get("sta_section").toString());
					}
					sc.setSsc_over_part_area_tableid(list.get(0).getSsc_over_part_area_tableid());
					ta.setSta_cb_id(list.get(0).getSsc_over_part_area_tableid());
				}
				storageChargeService.updateAreaCGBF(ta, sc);
			}
			json.put("status", "y");
			json.put("info", "操作成功!");
		} catch(Exception e){
			logger.error(e);
			json.put("status", "n");
			json.put("info", "操作失败!"+e.getMessage());
		}
		out.write(json.toString());
		out.flush();
		out.close();
	}
	
	/** 
	* @Title: saveMJJSCGBF 
	* @Description: TODO(保存-商品实际占用面积-总占用阶梯) 
	* @param @param map
	* @param @param req
	* @param @param res    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	@RequestMapping("/saveMJJSALL")
	public void saveMJJSALL(ModelMap map,HttpServletRequest req,HttpServletResponse res){
		JSONObject json = new JSONObject();
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		try {
			out = res.getWriter();
			//固定信息
			String ssc_cb_id = req.getParameter("ssc_cb_id");
			String reservation2 = req.getParameter("reservation2");
			String ssc_whs_id = req.getParameter("ssc_whs_id");
			String ssc_area_id = req.getParameter("ssc_area_id");
			String ssc_item_type = req.getParameter("ssc_item_type");
			String CCF = req.getParameter("CCF");
			String MJ = req.getParameter("MJ");
	    	//商品实际占用面积-总占用
			String saa_price = req.getParameter("saa_price");
	    	String saa_price_unit = req.getParameter("saa_price_unit");
	    	String saa_section = req.getParameter("saa_section");
			StorageCharge sc = new StorageCharge();
			AllArea aa = new AllArea();
			//table id
			UUID tableid = UUID.randomUUID();
			//处理日期
			Map<String, String> dateMap = spiltDateString(reservation2);
			sc.setSsc_cb_id(Integer.valueOf(ssc_cb_id));
			sc.setSsc_occupied_area_type(Integer.valueOf(MJ));
			sc.setSsc_starttime(dateMap.get("startDate"));
			sc.setSsc_endtime(dateMap.get("endDate"));
			sc.setSsc_whs_id(Integer.valueOf(ssc_whs_id));
			sc.setSsc_area_id(Integer.valueOf(ssc_area_id));
			sc.setSsc_item_type(Integer.valueOf(ssc_item_type));
			sc.setSsc_sc_type(Integer.valueOf(CCF));
			aa.setSaa_section(saa_section);
			aa.setSaa_price(saa_price);
			aa.setSaa_price_unit(Integer.valueOf(saa_price_unit));
			aa.setSaa_status(1);
			//总占用部分
			//查询插入数据是否存在
			List<StorageCharge> list = storageChargeService.queryWAS(sc);
			if(list.size()==0){
				sc.setSsc_total_area_tableid(tableid.toString());
				aa.setSaa_cb_id(tableid.toString());
				storageChargeService.saveAreaALL(aa, sc);
			}else{
				sc.setSsc_id(list.get(0).getSsc_id());
				aa.setSaa_cb_id(tableid.toString());
				sc.setSsc_total_area_tableid(tableid.toString());
				if(null!=list.get(0).getSsc_total_area_tableid() && !list.get(0).getSsc_total_area_tableid().equals("")){
					List<Map<String,Object>> allAreaList = allAreaServiceImpl.findByCBID(aa.getSaa_cb_id());
					for (int i = 0; i < allAreaList.size(); i++) {
						IntervalValidationUtil.isExist(saa_section, allAreaList.get(i).get("saa_section").toString());
					}
					aa.setSaa_cb_id(list.get(0).getSsc_total_area_tableid());
					sc.setSsc_total_area_tableid(list.get(0).getSsc_total_area_tableid());
				}
				storageChargeService.updateAreaALL(aa, sc);
			}
			json.put("status", "y");
			json.put("info", "操作成功!");
		} catch(Exception e){
			logger.error(e);
			json.put("status", "n");
			json.put("info", "操作失败!"+e.getMessage());
		}
		out.write(json.toString());
		out.flush();
		out.close();
	}
	
	/** 
	* @Title: saveCCF2 
	* @Description: TODO(仓储费按体积 无阶梯) 
	* @param @param map
	* @param @param req
	* @param @param res    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	@RequestMapping("/saveCCF2")
	public void saveCCF2(ModelMap map,HttpServletRequest req,HttpServletResponse res){
		JSONObject json = new JSONObject();
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		try {
			out = res.getWriter();
			//固定信息
			String ssc_cb_id = req.getParameter("ssc_cb_id");
			String reservation2 = req.getParameter("reservation2");
			String ssc_whs_id = req.getParameter("ssc_whs_id");
			String ssc_area_id = req.getParameter("ssc_area_id");
			String ssc_item_type = req.getParameter("ssc_item_type");
			String CCF = req.getParameter("CCF");
			String TJ = req.getParameter("TJ");
	    	//按体积结算 无阶梯
			String ssc_volume_price = req.getParameter("ssc_volume_price");
	    	String ssc_volume_price_unit = req.getParameter("ssc_volume_price_unit");
			StorageCharge sc = new StorageCharge();
			//处理日期
			Map<String, String> dateMap = spiltDateString(reservation2);
			sc.setSsc_cb_id(Integer.valueOf(ssc_cb_id));
			sc.setSsc_starttime(dateMap.get("startDate"));
			sc.setSsc_endtime(dateMap.get("endDate"));
			sc.setSsc_volume_type(Integer.valueOf(TJ));
			sc.setSsc_whs_id(Integer.valueOf(ssc_whs_id));
			sc.setSsc_area_id(Integer.valueOf(ssc_area_id));
			sc.setSsc_item_type(Integer.valueOf(ssc_item_type));
			sc.setSsc_sc_type(Integer.valueOf(CCF));
			sc.setSsc_volume_price(ssc_volume_price);
			sc.setSsc_volume_price_unit(Integer.valueOf(ssc_volume_price_unit));
			//总占用部分
			storageChargeService.save(sc);
			json.put("status", "y");
			json.put("info", "操作成功!");
		} catch(Exception e){
			logger.error(e);
			json.put("status", "n");
			json.put("info", "操作失败!"+e.getMessage());
		}
		out.write(json.toString());
		out.flush();
		out.close();
	}
	
	/** 
	* @Title: saveTjjtCGBF 
	* @Description: TODO(仓储费按体积 超过部分) 
	* @param @param map
	* @param @param req
	* @param @param res    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	@RequestMapping("/saveTjjtCGBF")
	public void saveTjjtCGBF(ModelMap map,HttpServletRequest req,HttpServletResponse res){
		JSONObject json = new JSONObject();
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		try {
			out = res.getWriter();
			//固定信息
			String ssc_cb_id = req.getParameter("ssc_cb_id");
			String reservation2 = req.getParameter("reservation2");
			String ssc_whs_id = req.getParameter("ssc_whs_id");
			String ssc_area_id = req.getParameter("ssc_area_id");
			String ssc_item_type = req.getParameter("ssc_item_type");
			String CCF = req.getParameter("CCF");
			String TJ = req.getParameter("TJ");
	    	//按体积结算 体积超过部分
			String ctv_price = req.getParameter("ctv_price");
	    	String ctv_price_unit = req.getParameter("ctv_price_unit");
	    	String ctv_section = req.getParameter("ctv_section");
			StorageCharge sc = new StorageCharge();
			TotalVolume tv = new TotalVolume();
			//table id
			UUID tableid = UUID.randomUUID();
			//处理日期
			Map<String, String> dateMap = spiltDateString(reservation2);
			sc.setSsc_cb_id(Integer.valueOf(ssc_cb_id));
			sc.setSsc_starttime(dateMap.get("startDate"));
			sc.setSsc_endtime(dateMap.get("endDate"));
			sc.setSsc_whs_id(Integer.valueOf(ssc_whs_id));
			sc.setSsc_area_id(Integer.valueOf(ssc_area_id));
			sc.setSsc_item_type(Integer.valueOf(ssc_item_type));
			sc.setSsc_sc_type(Integer.valueOf(CCF));
			sc.setSsc_volume_type(Integer.valueOf(TJ));
			sc.setSsc_over_part_volume_tableid(tableid.toString());
			tv.setCtv_section(ctv_section);
			tv.setCtv_price(ctv_price);
			tv.setCtv_price_unit(Integer.valueOf(ctv_price_unit));
			tv.setCtv_status(1);
			//超过部分
			//查询插入数据是否存在
			List<StorageCharge> list = storageChargeService.queryWAS(sc);
			if(list.size()==0){
				sc.setSsc_over_part_volume_tableid(tableid.toString());
				tv.setCtv_cb_id(tableid.toString());
				storageChargeService.saveVolumeCGBF(tv, sc);
			}else{
				sc.setSsc_id(list.get(0).getSsc_id());
				sc.setSsc_over_part_volume_tableid(tableid.toString());
				tv.setCtv_cb_id(tableid.toString());
				if(null!=list.get(0).getSsc_over_part_area_tableid() && !list.get(0).getSsc_over_part_area_tableid().equals("")){
					List<Map<String,Object>> totalVolumeList = totalVolumeService.findByCBID(list.get(0).getSsc_over_part_area_tableid());
					for (int i = 0; i < totalVolumeList.size(); i++) {
						IntervalValidationUtil.isExist(ctv_section, totalVolumeList.get(i).get("ctv_section").toString());
					}
					sc.setSsc_over_part_volume_tableid(list.get(0).getSsc_over_part_volume_tableid());
					tv.setCtv_cb_id(list.get(0).getSsc_over_part_area_tableid());
				}
				storageChargeService.updateVolumeCGBF(tv, sc);
			}
			json.put("status", "y");
			json.put("info", "操作成功!");
		} catch(Exception e){
			logger.error(e);
			json.put("status", "n");
			json.put("info", "操作失败!"+e.getMessage());
		}
		out.write(json.toString());
		out.flush();
		out.close();
	}

	/** 
	* @Title: saveTjjtALL 
	* @Description: TODO(按商品的体积推算ALL) 
	* @param @param map
	* @param @param req
	* @param @param res    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	@RequestMapping("/saveTjjtALL")
	public void saveTjjtALL(ModelMap map,HttpServletRequest req,HttpServletResponse res){
		JSONObject json = new JSONObject();
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		try {
			out = res.getWriter();
			//固定信息
			String ssc_cb_id = req.getParameter("ssc_cb_id");
			String reservation2 = req.getParameter("reservation2");
			String ssc_whs_id = req.getParameter("ssc_whs_id");
			String ssc_area_id = req.getParameter("ssc_area_id");
			String ssc_item_type = req.getParameter("ssc_item_type");
			String CCF = req.getParameter("CCF");
			String TJ = req.getParameter("TJ");
	    	//按体积结算 总占用
			String sav_price = req.getParameter("sav_price");
	    	String sav_price_unit = req.getParameter("sav_price_unit");
	    	String sav_section = req.getParameter("sav_section");
			StorageCharge sc = new StorageCharge();
			AllVolume av = new AllVolume();
			//table id
			UUID tableid = UUID.randomUUID();
			//处理日期
			Map<String, String> dateMap = spiltDateString(reservation2);
			sc.setSsc_cb_id(Integer.valueOf(ssc_cb_id));
			sc.setSsc_starttime(dateMap.get("startDate"));
			sc.setSsc_endtime(dateMap.get("endDate"));
			sc.setSsc_whs_id(Integer.valueOf(ssc_whs_id));
			sc.setSsc_area_id(Integer.valueOf(ssc_area_id));
			sc.setSsc_item_type(Integer.valueOf(ssc_item_type));
			sc.setSsc_sc_type(Integer.valueOf(CCF));
			sc.setSsc_volume_type(Integer.valueOf(TJ));
			av.setSav_section(sav_section);
			av.setSav_price(sav_price);
			av.setSav_price_unit(Integer.valueOf(sav_price_unit));
			av.setSav_status(1);
			//超过部分
			//查询插入数据是否存在
			List<StorageCharge> list = storageChargeService.queryWAS(sc);
			if(list.size()==0){
				sc.setSsc_total_volume_tableid(tableid.toString());
				av.setSav_cb_id(tableid.toString());
				storageChargeService.saveVolumeALL(av, sc);
			}else{
				sc.setSsc_id(list.get(0).getSsc_id());
				sc.setSsc_total_volume_tableid(tableid.toString());
				av.setSav_cb_id(tableid.toString());
				if (null!=list.get(0).getSsc_total_volume_tableid() && !list.get(0).getSsc_total_volume_tableid().equals("")) {
					List<Map<String,Object>> allVolumeList = allVolumeService.findByCBID(list.get(0).getSsc_total_volume_tableid());
					for (int i = 0; i < allVolumeList.size(); i++) {
						IntervalValidationUtil.isExist(sav_section, allVolumeList.get(i).get("sav_section").toString());
					}
					sc.setSsc_total_volume_tableid(list.get(0).getSsc_total_volume_tableid());
					av.setSav_cb_id(list.get(0).getSsc_over_part_area_tableid());
				}
				storageChargeService.updateVolumeALL(av, sc);
			}
			json.put("status", "y");
			json.put("info", "操作成功!");
		} catch(Exception e){
			logger.error(e);
			json.put("status", "n");
			json.put("info", "操作失败!"+e.getMessage());
		}
		out.write(json.toString());
		out.flush();
		out.close();
	}

	/** 
	* @Title: saveCCF3 
	* @Description: TODO(仓储费按商品的体积推算 无阶梯) 
	* @param @param map
	* @param @param req
	* @param @param res    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	@RequestMapping("/saveCCF3")
	public void saveCCF3(ModelMap map,HttpServletRequest req,HttpServletResponse res){
		JSONObject json = new JSONObject();
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		try {
			out = res.getWriter();
			//固定信息
			String ssc_cb_id = req.getParameter("ssc_cb_id");
			String reservation2 = req.getParameter("reservation2");
			String ssc_whs_id = req.getParameter("ssc_whs_id");
			String ssc_area_id = req.getParameter("ssc_area_id");
			String ssc_item_type = req.getParameter("ssc_item_type");
			String CCF = req.getParameter("CCF");
			String SPTJ = req.getParameter("SPTJ");
			
			StorageCharge sc = new StorageCharge();
			//
			String ssc_volume_calculation_price = req.getParameter("ssc_volume_calculation_price");
			String ssc_volume_calculation_price_unit = req.getParameter("ssc_volume_calculation_price_unit");
			//处理日期
			Map<String, String> dateMap = spiltDateString(reservation2);
			sc.setSsc_cb_id(Integer.valueOf(ssc_cb_id));
			sc.setSsc_starttime(dateMap.get("startDate"));
			sc.setSsc_endtime(dateMap.get("endDate"));
			sc.setSsc_whs_id(Integer.valueOf(ssc_whs_id));
			sc.setSsc_area_id(Integer.valueOf(ssc_area_id));
			sc.setSsc_item_type(Integer.valueOf(ssc_item_type));
			sc.setSsc_sc_type(Integer.valueOf(CCF));
			sc.setSsc_volume_calculation_type(Integer.valueOf(SPTJ));
			sc.setSsc_volume_calculation_price(ssc_volume_calculation_price);
			sc.setSsc_volume_calculation_price_unit(Integer.valueOf(ssc_volume_calculation_price_unit));
			//体积反推
			storageChargeService.save(sc);
			json.put("status", "y");
			json.put("info", "操作成功!");
		} catch(Exception e){
			logger.error(e);
			json.put("status", "n");
			json.put("info", "操作失败!"+e.getMessage());
		}
		out.write(json.toString());
		out.flush();
		out.close();
	}
	
	/** 
	* @Title: saveSpjtCGBF 
	* @Description: TODO(超过部分体积阶梯) 
	* @param @param map
	* @param @param req
	* @param @param res    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	@RequestMapping("/saveSpjtCGBF")
	public void saveSpjtCGBF(ModelMap map,HttpServletRequest req,HttpServletResponse res){
		JSONObject json = new JSONObject();
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		try {
			out = res.getWriter();
			//固定信息
			String ssc_cb_id = req.getParameter("ssc_cb_id");
			String reservation2 = req.getParameter("reservation2");
			String ssc_whs_id = req.getParameter("ssc_whs_id");
			String ssc_area_id = req.getParameter("ssc_area_id");
			String ssc_item_type = req.getParameter("ssc_item_type");
			String CCF = req.getParameter("CCF");
			String SPTJ = req.getParameter("SPTJ");
			StorageCharge sc = new StorageCharge();
			TotalVolumeCalculation tvc = new TotalVolumeCalculation();
			//
			String stvc_section = req.getParameter("stvc_section");
			String stvc_price = req.getParameter("stvc_price");
			String stvc_price_unit = req.getParameter("stvc_price_unit");
			//table id
			UUID tableid = UUID.randomUUID();
			//处理日期
			Map<String, String> dateMap = spiltDateString(reservation2);
			sc.setSsc_cb_id(Integer.valueOf(ssc_cb_id));
			sc.setSsc_starttime(dateMap.get("startDate"));
			sc.setSsc_endtime(dateMap.get("endDate"));
			sc.setSsc_whs_id(Integer.valueOf(ssc_whs_id));
			sc.setSsc_area_id(Integer.valueOf(ssc_area_id));
			sc.setSsc_item_type(Integer.valueOf(ssc_item_type));
			sc.setSsc_sc_type(Integer.valueOf(CCF));
			sc.setSsc_volume_calculation_type(Integer.valueOf(SPTJ));
			tvc.setStvc_price(stvc_price);
			tvc.setStvc_price_unit(Integer.valueOf(stvc_price_unit));
			tvc.setStvc_section(stvc_section);
			tvc.setStvc_status(1);
			//超过部分
			//查询插入数据是否存在
			List<StorageCharge> list = storageChargeService.queryWAS(sc);
			if(list.size()==0){
				sc.setSsc_over_part_area_tableid(tableid.toString());
				tvc.setStvc_cb_id(tableid.toString());
				storageChargeService.saveTVC(tvc, sc);
			}else{
				sc.setSsc_id(list.get(0).getSsc_id());
				sc.setSsc_over_part_area_tableid(tableid.toString());
				tvc.setStvc_cb_id(tableid.toString());
				if (null!=list.get(0).getSsc_over_part_area_tableid() && !list.get(0).getSsc_over_part_area_tableid().equals("")) {
					List<Map<String,Object>> lists = totalVolumeCalculationService.findByCBID(list.get(0).getSsc_over_part_area_tableid());
					for (int i = 0; i < lists.size(); i++) {
						IntervalValidationUtil.isExist(stvc_section, lists.get(i).get("stvc_section").toString());
					}
					sc.setSsc_over_part_area_tableid(list.get(0).getSsc_over_part_area_tableid());
					tvc.setStvc_cb_id(list.get(0).getSsc_over_part_area_tableid());
				}
				storageChargeService.updateTVC(tvc, sc);
			}
			json.put("status", "y");
			json.put("info", "操作成功!");
		} catch(Exception e){
			logger.error(e);
			json.put("status", "n");
			json.put("info", "操作失败!"+e.getMessage());
		}
		out.write(json.toString());
		out.flush();
		out.close();
	}
	
	/** 
	* @Title: saveSpjtALL 
	* @Description: TODO(总占用体积阶梯) 
	* @param @param map
	* @param @param req
	* @param @param res    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	@RequestMapping("/saveSpjtALL")
	public void saveSpjtALL(ModelMap map,HttpServletRequest req,HttpServletResponse res){
		JSONObject json = new JSONObject();
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		try {
			out = res.getWriter();
			//固定信息
			String ssc_cb_id = req.getParameter("ssc_cb_id");
			String reservation2 = req.getParameter("reservation2");
			String ssc_whs_id = req.getParameter("ssc_whs_id");
			String ssc_area_id = req.getParameter("ssc_area_id");
			String ssc_item_type = req.getParameter("ssc_item_type");
			String CCF = req.getParameter("CCF");
			String SPTJ = req.getParameter("SPTJ");
			StorageCharge sc = new StorageCharge();
			AllVolumeCalculation avc = new AllVolumeCalculation();
			//
			String savc_price = req.getParameter("savc_price");
			String savc_price_unit = req.getParameter("savc_price_unit");
			String savc_section = req.getParameter("savc_section");
			//table id
			UUID tableid = UUID.randomUUID();
			//处理日期
			Map<String, String> dateMap = spiltDateString(reservation2);
			sc.setSsc_cb_id(Integer.valueOf(ssc_cb_id));
			sc.setSsc_volume_calculation_type(Integer.valueOf(SPTJ));
			sc.setSsc_over_part_volume_calculation_tableid(tableid.toString());
			sc.setSsc_starttime(dateMap.get("startDate"));
			sc.setSsc_endtime(dateMap.get("endDate"));
			sc.setSsc_whs_id(Integer.valueOf(ssc_whs_id));
			sc.setSsc_area_id(Integer.valueOf(ssc_area_id));
			sc.setSsc_item_type(Integer.valueOf(ssc_item_type));
			sc.setSsc_sc_type(Integer.valueOf(CCF));
			avc.setSavc_cb_id(tableid.toString());
			avc.setSavc_status(1);
			avc.setSavc_price(savc_price);
			avc.setSavc_price_unit(Integer.valueOf(savc_price_unit));
			avc.setSavc_section(savc_section);
			//超过部分
			//查询插入数据是否存在
			List<StorageCharge> list = storageChargeService.queryWAS(sc);
			if(list.size()==0){
				sc.setSsc_total_volume_tableid(tableid.toString());
				avc.setSavc_cb_id(tableid.toString());
				storageChargeService.saveAVC(avc, sc);
			}else{
				sc.setSsc_id(list.get(0).getSsc_id());
				sc.setSsc_total_volume_tableid(tableid.toString());
				avc.setSavc_cb_id(tableid.toString());
				if(null!=list.get(0).getSsc_total_volume_tableid() && !list.get(0).getSsc_total_volume_tableid().equals("")){
					List<Map<String,Object>> lists = allVolumeCalculationService.findByCBID(list.get(0).getSsc_total_volume_tableid());
					for (int i = 0; i < lists.size(); i++) {
						IntervalValidationUtil.isExist(savc_section, lists.get(i).get("savc_section").toString());
					}
					sc.setSsc_total_volume_tableid(list.get(0).getSsc_total_volume_tableid());
					avc.setSavc_cb_id(list.get(0).getSsc_total_volume_tableid());
				}
				storageChargeService.updateAVC(avc, sc);
			}
			json.put("status", "y");
			json.put("info", "操作成功!");
		} catch(Exception e){
			logger.error(e);
			json.put("status", "n");
			json.put("info", "操作失败!"+e.getMessage());
		}
		out.write(json.toString());
		out.flush();
		out.close();
	}
	
	/** 
	* @Title: saveCCF4 
	* @Description: TODO(仓储费按件数结算) 
	* @param @param map
	* @param @param req
	* @param @param res    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	@RequestMapping("/saveCCF4")
	public void saveCCF4(ModelMap map,HttpServletRequest req,HttpServletResponse res){
		JSONObject json = new JSONObject();
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		try {
			out = res.getWriter();
			//固定信息
			String ssc_cb_id = req.getParameter("ssc_cb_id");
			String reservation2 = req.getParameter("reservation2");
			String ssc_whs_id = req.getParameter("ssc_whs_id");
			String ssc_area_id = req.getParameter("ssc_area_id");
			String ssc_item_type = req.getParameter("ssc_item_type");
			String CCF = req.getParameter("CCF");
			
			StorageCharge sc = new StorageCharge();
			//
			String ssc_number_price = req.getParameter("ssc_number_price");
			String ssc_number_price_unit = req.getParameter("ssc_number_price_unit");
			//处理日期
			Map<String, String> dateMap = spiltDateString(reservation2);
			sc.setSsc_cb_id(Integer.valueOf(ssc_cb_id));
			sc.setSsc_starttime(dateMap.get("startDate"));
			sc.setSsc_endtime(dateMap.get("endDate"));
			sc.setSsc_whs_id(Integer.valueOf(ssc_whs_id));
			sc.setSsc_area_id(Integer.valueOf(ssc_area_id));
			sc.setSsc_item_type(Integer.valueOf(ssc_item_type));
			sc.setSsc_sc_type(Integer.valueOf(CCF));
			sc.setSsc_number_price(ssc_number_price);
			sc.setSsc_number_price_unit(Integer.valueOf(ssc_number_price_unit));
			//体积反推
			storageChargeService.save(sc);
			json.put("status", "y");
			json.put("info", "操作成功!");
		} catch(Exception e){
			logger.error(e);
			json.put("status", "n");
			json.put("info", "操作失败!"+e.getMessage());
		}
		out.write(json.toString());
		out.flush();
		out.close();
	}
	
	/** 
	* @Title: saveCCF5 
	* @Description: TODO(仓储费按件数反推所占面积) 
	* @param @param map
	* @param @param req
	* @param @param res    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	@RequestMapping("/saveCCF5")
	public void saveCCF5(ModelMap map,HttpServletRequest req,HttpServletResponse res){
		JSONObject json = new JSONObject();
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		try {
			out = res.getWriter();
			//固定信息
			String ssc_cb_id = req.getParameter("ssc_cb_id");
			String reservation2 = req.getParameter("reservation2");
			String ssc_whs_id = req.getParameter("ssc_whs_id");
			String ssc_area_id = req.getParameter("ssc_area_id");
			String ssc_item_type = req.getParameter("ssc_item_type");
			String CCF = req.getParameter("CCF");
			
			StorageCharge sc = new StorageCharge();
			//
			String ssc_square_hold_the_number = req.getParameter("ssc_square_hold_the_number");
			String ssc_square_hold_the_number_unit = req.getParameter("ssc_square_hold_the_number_unit");
			String ssc_square_price = req.getParameter("ssc_square_price");
			String ssc_square_price_unit = req.getParameter("ssc_square_price_unit");
			//处理日期
			Map<String, String> dateMap = spiltDateString(reservation2);
			sc.setSsc_cb_id(Integer.valueOf(ssc_cb_id));
			sc.setSsc_starttime(dateMap.get("startDate"));
			sc.setSsc_endtime(dateMap.get("endDate"));
			sc.setSsc_whs_id(Integer.valueOf(ssc_whs_id));
			sc.setSsc_area_id(Integer.valueOf(ssc_area_id));
			sc.setSsc_item_type(Integer.valueOf(ssc_item_type));
			sc.setSsc_sc_type(Integer.valueOf(CCF));
			sc.setSsc_square_hold_the_number(ssc_square_hold_the_number);
			sc.setSsc_square_hold_the_number_unit(Integer.valueOf(ssc_square_hold_the_number_unit));
			sc.setSsc_square_price(ssc_square_price);
			sc.setSsc_square_price_unit(Integer.valueOf(ssc_square_price_unit));
			//体积反推
			storageChargeService.save(sc);
			json.put("status", "y");
			json.put("info", "操作成功!");
		} catch(Exception e){
			logger.error(e);
			json.put("status", "n");
			json.put("info", "操作失败!"+e.getMessage());
		}
		out.write(json.toString());
		out.flush();
		out.close();
	}
	
	/** 
	* @Title: saveTPJSWJT 
	* @Description: TODO(仓储费按托盘结算 无阶梯) 
	* @param @param map
	* @param @param req
	* @param @param res    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	@RequestMapping("/saveTPJSWJT")
	public void saveTPJSWJT(ModelMap map,HttpServletRequest req,HttpServletResponse res){
		JSONObject json = new JSONObject();
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		try {
			out = res.getWriter();
			//固定信息
			String ssc_cb_id = req.getParameter("ssc_cb_id");
			String reservation2 = req.getParameter("reservation2");
			String ssc_whs_id = req.getParameter("ssc_whs_id");
			String ssc_area_id = req.getParameter("ssc_area_id");
			String ssc_item_type = req.getParameter("ssc_item_type");
			String CCF = req.getParameter("CCF");
			String TP = req.getParameter("TP");
			StorageCharge sc = new StorageCharge();
			//
			String ssc_tray_price = req.getParameter("ssc_tray_price");
			String ssc_tray_price_unit = req.getParameter("ssc_tray_price_unit");
			//处理日期
			Map<String, String> dateMap = spiltDateString(reservation2);
			sc.setSsc_cb_id(Integer.valueOf(ssc_cb_id));
			sc.setSsc_starttime(dateMap.get("startDate"));
			sc.setSsc_endtime(dateMap.get("endDate"));
			sc.setSsc_whs_id(Integer.valueOf(ssc_whs_id));
			sc.setSsc_area_id(Integer.valueOf(ssc_area_id));
			sc.setSsc_item_type(Integer.valueOf(ssc_item_type));
			sc.setSsc_sc_type(Integer.valueOf(CCF));
			sc.setSsc_tray_price(ssc_tray_price);
			sc.setSsc_tray_price_unit(Integer.valueOf(ssc_tray_price_unit));
			sc.setSsc_tray_balance_type(Integer.valueOf(TP));
			//体积反推
			storageChargeService.save(sc);
			json.put("status", "y");
			json.put("info", "操作成功!");
		} catch(Exception e){
			logger.error(e);
			json.put("status", "n");
			json.put("info", "操作失败!"+e.getMessage());
		}
		out.write(json.toString());
		out.flush();
		out.close();
	}
	

	/** 
	* @Title: saveTPJS_CGBF 
	* @Description: TODO(超过部分托盘数阶梯) 
	* @param @param map
	* @param @param req
	* @param @param res    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	@RequestMapping("/saveTPJS_CGBF")
	public void saveTPJS_CGBF(ModelMap map,HttpServletRequest req,HttpServletResponse res){
		JSONObject json = new JSONObject();
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		try {
			out = res.getWriter();
			//固定信息
			String ssc_cb_id = req.getParameter("ssc_cb_id");
			String reservation2 = req.getParameter("reservation2");
			String ssc_whs_id = req.getParameter("ssc_whs_id");
			String ssc_area_id = req.getParameter("ssc_area_id");
			String ssc_item_type = req.getParameter("ssc_item_type");
			String CCF = req.getParameter("CCF");
			String TP = req.getParameter("TP");
			TotalTray tt = new TotalTray();
			StorageCharge sc = new StorageCharge();
			//
			String stt_section = req.getParameter("stt_section");
			String stt_price = req.getParameter("stt_price");
			String stt_price_unit = req.getParameter("stt_price_unit");
			//处理日期
			UUID tableid = UUID.randomUUID();
			Map<String, String> dateMap = spiltDateString(reservation2);
			sc.setSsc_cb_id(Integer.valueOf(ssc_cb_id));
			sc.setSsc_starttime(dateMap.get("startDate"));
			sc.setSsc_endtime(dateMap.get("endDate"));
			sc.setSsc_whs_id(Integer.valueOf(ssc_whs_id));
			sc.setSsc_area_id(Integer.valueOf(ssc_area_id));
			sc.setSsc_item_type(Integer.valueOf(ssc_item_type));
			sc.setSsc_sc_type(Integer.valueOf(CCF));
			sc.setSsc_tray_balance_type(Integer.valueOf(TP));
			tt.setStt_price(stt_price);
			tt.setStt_price_unit(Integer.valueOf(stt_price_unit));
			tt.setStt_status(1);
			tt.setStt_section(stt_section);
			//超过部分
			//查询插入数据是否存在
			List<StorageCharge> list = storageChargeService.queryWAS(sc);
			if(list.size()==0){
				sc.setSsc_total_tray_tableid(tableid.toString());
				tt.setStt_cb_id(tableid.toString());
				storageChargeService.saveTT(tt, sc);
			}else{
				sc.setSsc_id(list.get(0).getSsc_id());
				sc.setSsc_total_tray_tableid(tableid.toString());
				tt.setStt_cb_id(tableid.toString());
				if(null!=list.get(0).getSsc_total_tray_tableid() && !list.get(0).getSsc_total_tray_tableid().equals("")){
					List<Map<String,Object>> lists =  totalTrayService.findByCBID(list.get(0).getSsc_total_tray_tableid());
					for (int i = 0; i < lists.size(); i++) {
						IntervalValidationUtil.isExist(stt_section, lists.get(i).get("stt_section").toString());
					}
					sc.setSsc_total_tray_tableid(list.get(0).getSsc_total_tray_tableid());
					tt.setStt_cb_id(list.get(0).getSsc_total_tray_tableid());
				}
				storageChargeService.updateTT(tt, sc);
			}
			json.put("status", "y");
			json.put("info", "操作成功!");
		} catch(Exception e){
			logger.error(e);
			json.put("status", "n");
			json.put("info", "操作失败!"+e.getMessage());
		}
		out.write(json.toString());
		out.flush();
		out.close();
	}

	/** 
	* @Title: saveTPJS_ALL 
	* @Description: TODO(总占用托盘数阶梯) 
	* @param @param map
	* @param @param req
	* @param @param res    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	@RequestMapping("/saveTPJS_ALL")
	public void saveTPJS_ALL(ModelMap map,HttpServletRequest req,HttpServletResponse res){
		JSONObject json = new JSONObject();
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		try {
			out = res.getWriter();
			//固定信息
			String ssc_cb_id = req.getParameter("ssc_cb_id");
			String reservation2 = req.getParameter("reservation2");
			String ssc_whs_id = req.getParameter("ssc_whs_id");
			String ssc_area_id = req.getParameter("ssc_area_id");
			String ssc_item_type = req.getParameter("ssc_item_type");
			String CCF = req.getParameter("CCF");
			String TP = req.getParameter("TP");
			AllTray at = new AllTray();
			StorageCharge sc = new StorageCharge();
			//
			String sat_section = req.getParameter("sat_section");
			String sat_price = req.getParameter("sat_price");
			String sat_price_unit = req.getParameter("sat_price_unit");
			//处理日期
			UUID tableid = UUID.randomUUID();
			Map<String, String> dateMap = spiltDateString(reservation2);
			sc.setSsc_cb_id(Integer.valueOf(ssc_cb_id));
			sc.setSsc_starttime(dateMap.get("startDate"));
			sc.setSsc_endtime(dateMap.get("endDate"));
			sc.setSsc_whs_id(Integer.valueOf(ssc_whs_id));
			sc.setSsc_area_id(Integer.valueOf(ssc_area_id));
			sc.setSsc_item_type(Integer.valueOf(ssc_item_type));
			sc.setSsc_sc_type(Integer.valueOf(CCF));
			sc.setSsc_tray_balance_type(Integer.valueOf(TP));
			at.setSat_price(sat_price);
			at.setSat_price_unit(Integer.valueOf(sat_price_unit));
			at.setSat_status(1);
			at.setSat_section(sat_section);
			//超过部分
			//查询插入数据是否存在
			List<StorageCharge> list = storageChargeService.queryWAS(sc);
			if(list.size()==0){
				sc.setSsc_over_part_tray_tableid(tableid.toString());
				at.setSat_cb_id(tableid.toString());
				storageChargeService.saveAT(at, sc);
			}else{
				sc.setSsc_id(list.get(0).getSsc_id());
				sc.setSsc_over_part_tray_tableid(tableid.toString());
				at.setSat_cb_id(tableid.toString());
				if(null!=list.get(0).getSsc_over_part_tray_tableid() && !list.get(0).getSsc_over_part_tray_tableid().equals("")){
					List<Map<String,Object>> lists = allTrayService.findByCBID(list.get(0).getSsc_over_part_tray_tableid());
					for (int i = 0; i < lists.size(); i++) {
						IntervalValidationUtil.isExist(sat_section, lists.get(i).get("sat_section").toString());
					}
					sc.setSsc_over_part_tray_tableid(list.get(0).getSsc_over_part_tray_tableid());
					at.setSat_cb_id(list.get(0).getSsc_over_part_tray_tableid());
				}
				storageChargeService.updateAT(at, sc);
			}
			json.put("status", "y");
			json.put("info", "操作成功!");
		} catch(Exception e){
			logger.error(e);
			json.put("status", "n");
			json.put("info", "操作失败!"+e.getMessage());
		}
		out.write(json.toString());
		out.flush();
		out.close();
	}
	
	/** 
	* @Title: saveTPFT_WJT 
	* @Description: TODO(仓储费按托盘存放数反推 无阶梯) 
	* @param @param map
	* @param @param req
	* @param @param res    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	@RequestMapping("/saveTPFT_WJT")
	public void saveTPFT_WJT(ModelMap map,HttpServletRequest req,HttpServletResponse res){
		JSONObject json = new JSONObject();
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		try {
			out = res.getWriter();
			//固定信息
			String ssc_cb_id = req.getParameter("ssc_cb_id");
			String reservation2 = req.getParameter("reservation2");
			String ssc_whs_id = req.getParameter("ssc_whs_id");
			String ssc_area_id = req.getParameter("ssc_area_id");
			String ssc_item_type = req.getParameter("ssc_item_type");
			String CCF = req.getParameter("CCF");
			String TPFT = req.getParameter("TPFT");
			StorageCharge sc = new StorageCharge();
			//
	    	String ssc_single_tray_number = req.getParameter("ssc_single_tray_number");
	    	String ssc_single_tray_number_unit = req.getParameter("ssc_single_tray_number_unit");
	    	String ssc_singlt_tray_price = req.getParameter("ssc_singlt_tray_price");
	    	String ssc_singlt_tray_price_unit = req.getParameter("ssc_singlt_tray_price_unit");
			//处理日期
			Map<String, String> dateMap = spiltDateString(reservation2);
			sc.setSsc_cb_id(Integer.valueOf(ssc_cb_id));
			sc.setSsc_starttime(dateMap.get("startDate"));
			sc.setSsc_endtime(dateMap.get("endDate"));
			sc.setSsc_whs_id(Integer.valueOf(ssc_whs_id));
			sc.setSsc_area_id(Integer.valueOf(ssc_area_id));
			sc.setSsc_item_type(Integer.valueOf(ssc_item_type));
			sc.setSsc_sc_type(Integer.valueOf(CCF));
			sc.setSsc_single_tray_balance_type(Integer.valueOf(TPFT));
			sc.setSsc_single_tray_number(ssc_single_tray_number);
			sc.setSsc_single_tray_number_unit(ssc_single_tray_number_unit);
			sc.setSsc_singlt_tray_price(ssc_singlt_tray_price);
			sc.setSsc_singlt_tray_price_unit(Integer.valueOf(ssc_singlt_tray_price_unit));
			storageChargeService.save(sc);
			json.put("status", "y");
			json.put("info", "操作成功!");
		} catch(Exception e){
			logger.error(e);
			json.put("status", "n");
			json.put("info", "操作失败!"+e.getMessage());
		}
		out.write(json.toString());
		out.flush();
		out.close();
	}
	

	/** 
	* @Title: saveTPFT_CGBF 
	* @Description: TODO(超过部分托盘数阶梯) 
	* @param @param map
	* @param @param req
	* @param @param res    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	@RequestMapping("/saveTPFT_CGBF")
	public void saveTPFT_CGBF(ModelMap map,HttpServletRequest req,HttpServletResponse res){
		JSONObject json = new JSONObject();
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		try {
			out = res.getWriter();
			//固定信息
			String ssc_cb_id = req.getParameter("ssc_cb_id");
			String reservation2 = req.getParameter("reservation2");
			String ssc_whs_id = req.getParameter("ssc_whs_id");
			String ssc_area_id = req.getParameter("ssc_area_id");
			String ssc_item_type = req.getParameter("ssc_item_type");
			String CCF = req.getParameter("CCF");
			String TPFT = req.getParameter("TPFT");
			TotalSingltTray tst = new TotalSingltTray();
			StorageCharge sc = new StorageCharge();
			//托盘反推 超过部分
			String stat_section = req.getParameter("stat_section");
			String stat_price = req.getParameter("stat_price");
			String stat_price_unit = req.getParameter("stat_price_unit");
			String ssc_single_tray_number = req.getParameter("ssc_single_tray_number");
			String ssc_single_tray_number_unit = req.getParameter("ssc_single_tray_number_unit");
			//处理日期
			UUID tableid = UUID.randomUUID();
			Map<String, String> dateMap = spiltDateString(reservation2);
			sc.setSsc_cb_id(Integer.valueOf(ssc_cb_id));
			sc.setSsc_starttime(dateMap.get("startDate"));
			sc.setSsc_endtime(dateMap.get("endDate"));
			sc.setSsc_whs_id(Integer.valueOf(ssc_whs_id));
			sc.setSsc_area_id(Integer.valueOf(ssc_area_id));
			sc.setSsc_item_type(Integer.valueOf(ssc_item_type));
			sc.setSsc_sc_type(Integer.valueOf(CCF));
			sc.setSsc_single_tray_balance_type(Integer.valueOf(TPFT));
			sc.setSsc_single_tray_number(ssc_single_tray_number);
			sc.setSsc_single_tray_number_unit(ssc_single_tray_number_unit);
			tst.setStat_price(stat_price);
			tst.setStat_price_unit(Integer.valueOf(stat_price_unit));
			tst.setStat_status(1);
			tst.setStat_section(stat_section);
			//超过部分
			//查询插入数据是否存在
			List<StorageCharge> list = storageChargeService.queryWAS(sc);
			if(list.size()==0){
				sc.setSsc_over_part_single_tray_tableid(tableid.toString());
				tst.setStat_cb_id(tableid.toString());
				storageChargeService.saveTST(tst, sc);
			}else{
				sc.setSsc_id(list.get(0).getSsc_id());
				sc.setSsc_over_part_tray_tableid(tableid.toString());
				tst.setStat_cb_id(tableid.toString());
				if(null!=list.get(0).getSsc_over_part_single_tray_tableid() && !list.get(0).getSsc_over_part_single_tray_tableid().equals("")){
					List<Map<String,Object>> lists = totalSingltTrayService.findByCBID(list.get(0).getSsc_over_part_single_tray_tableid());
					for (int i = 0; i < lists.size(); i++) {
						IntervalValidationUtil.isExist(stat_section, lists.get(i).get("stat_section").toString());
					}
					sc.setSsc_over_part_tray_tableid(list.get(0).getSsc_over_part_single_tray_tableid());
					tst.setStat_cb_id(list.get(0).getSsc_total_tray_tableid());
				}
				storageChargeService.updateTST(tst, sc);
			}
			json.put("status", "y");
			json.put("info", "操作成功!");
		} catch(Exception e){
			logger.error(e);
			json.put("status", "n");
			json.put("info", "操作失败!"+e.getMessage());
		}
		out.write(json.toString());
		out.flush();
		out.close();
	}

	/** 
	* @Title: saveTPFT_ALL 
	* @Description: TODO(总占用托盘数阶梯) 
	* @param @param map
	* @param @param req
	* @param @param res    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	@RequestMapping("/saveTPFT_ALL")
	public void saveTPFT_ALL(ModelMap map,HttpServletRequest req,HttpServletResponse res){
		JSONObject json = new JSONObject();
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		try {
			out = res.getWriter();
			//固定信息
			String ssc_cb_id = req.getParameter("ssc_cb_id");
			String reservation2 = req.getParameter("reservation2");
			String ssc_whs_id = req.getParameter("ssc_whs_id");
			String ssc_area_id = req.getParameter("ssc_area_id");
			String ssc_item_type = req.getParameter("ssc_item_type");
			String CCF = req.getParameter("CCF");
			String TPFT = req.getParameter("TPFT");
			AllSingltTray ast = new AllSingltTray();
			StorageCharge sc = new StorageCharge();
			//托盘反推 总占用部分
			String sast_section = req.getParameter("sast_section");
			String sast_price = req.getParameter("sast_price");
			String sast_price_unit = req.getParameter("sast_price_unit");
			String ssc_single_tray_number = req.getParameter("ssc_single_tray_number");
			String ssc_single_tray_number_unit = req.getParameter("ssc_single_tray_number_unit");
			//处理日期
			UUID tableid = UUID.randomUUID();
			Map<String, String> dateMap = spiltDateString(reservation2);
			sc.setSsc_cb_id(Integer.valueOf(ssc_cb_id));
			sc.setSsc_starttime(dateMap.get("startDate"));
			sc.setSsc_endtime(dateMap.get("endDate"));
			sc.setSsc_whs_id(Integer.valueOf(ssc_whs_id));
			sc.setSsc_area_id(Integer.valueOf(ssc_area_id));
			sc.setSsc_item_type(Integer.valueOf(ssc_item_type));
			sc.setSsc_sc_type(Integer.valueOf(CCF));
			sc.setSsc_single_tray_balance_type(Integer.valueOf(TPFT));
			sc.setSsc_single_tray_number(ssc_single_tray_number);
			sc.setSsc_single_tray_number_unit(ssc_single_tray_number_unit);
			ast.setSast_price(sast_price);
			ast.setSast_price_unit(Integer.valueOf(sast_price_unit));
			ast.setSast_status(1);
			ast.setSast_section(sast_section);
			//超过部分
			//查询插入数据是否存在
			List<StorageCharge> list = storageChargeService.queryWAS(sc);
			if(list.size()==0){
				sc.setSsc_total_single_tray_tableid(tableid.toString());
				ast.setSast_cb_id(tableid.toString());
				storageChargeService.saveAST(ast, sc);
			}else{
				sc.setSsc_id(list.get(0).getSsc_id());
				sc.setSsc_total_single_tray_tableid(tableid.toString());
				ast.setSast_cb_id(tableid.toString());
				if(null!=list.get(0).getSsc_total_single_tray_tableid() && !list.get(0).getSsc_total_single_tray_tableid().equals("")){
					List<Map<String,Object>> lists = allSingltTrayService.findByCBID(list.get(0).getSsc_total_single_tray_tableid());
					for (int i = 0; i < lists.size(); i++) {
						IntervalValidationUtil.isExist(sast_section, lists.get(i).get("sast_section").toString());
					}
					sc.setSsc_total_single_tray_tableid(list.get(0).getSsc_total_single_tray_tableid());
					ast.setSast_cb_id(list.get(0).getSsc_total_single_tray_tableid());
				}
				storageChargeService.updateAST(ast, sc);
			}
			json.put("status", "y");
			json.put("info", "操作成功!");
		} catch(Exception e){
			logger.error(e);
			json.put("status", "n");
			json.put("info", "操作失败!"+e.getMessage());
		}
		out.write(json.toString());
		out.flush();
		out.close();
	}
	
	/** 
	* @Title: getMJJS_CGBF_tab 
	* @Description: TODO(面积结算超过部分表格显示) 
	* @param @param map
	* @param @param req
	* @param @param res    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	@RequestMapping("/getMJJS_CGBF_tab")
	public void getMJJS_CGBF_tab(ModelMap map,HttpServletRequest req,HttpServletResponse res){
		JSONObject json = new JSONObject();
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		try {
			json.put("status", "n");
			json.put("info", "操作失败!");
			out = res.getWriter();
			String cbid = req.getParameter("ssc_cb_id");
			String id2 = req.getParameter("id2");
			String id3 = req.getParameter("id3");
			String id4 = req.getParameter("id4");
			String id5 = req.getParameter("id5");
			if(!cbid.equals("")){
				StorageCharge sc = new StorageCharge();
				sc.setSsc_whs_id(Integer.valueOf(id2));
				sc.setSsc_area_id(Integer.valueOf(id3));
				sc.setSsc_item_type(Integer.valueOf(id4));
				sc.setSsc_sc_type(Integer.valueOf(id5));
				sc.setSsc_occupied_area_type(2);
				sc.setSsc_cb_id(Integer.valueOf(cbid));
				List<StorageCharge> scList = storageChargeService.queryWAS(sc);
				List<Map<String, Object>> taList = totalAreaService.findByCBID(scList.get(0).getSsc_over_part_area_tableid());
				json.put("status", "y");
				json.put("info", "操作成功!");
				json.put("taList", taList);
			}
		} catch(Exception e){
			logger.error(e);
		}
		out.write(json.toString());
		out.flush();
		out.close();
	}
	
	/** 
	* @Title: getCCFMainTB 
	* @Description: TODO(操作费Main表格) 
	* @param @param map
	* @param @param req
	* @param @param res    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	@RequestMapping("/getCCFMainTB")
	public void getCCFMainTB(ModelMap map,HttpServletRequest req,HttpServletResponse res){
		JSONObject json = new JSONObject();
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		json.put("status", "n");
		json.put("info", "操作失败!");
		try {
			out = res.getWriter();
			String cbid = req.getParameter("ssc_cb_id");
			if(!cbid.equals("")){
				List<StorageCharge> storageChargeList = storageChargeService.findByID(cbid);
				map.addAttribute("storageChargeList",storageChargeList);
				json.put("list", storageChargeList);
				json.put("status", "y");
				json.put("info", "操作成功!");
			}
		} catch(Exception e){
			logger.error(e);
		}
		out.write(json.toString());
		out.flush();
		out.close();
	}
	
	/** 
	* @Title: getMJJS_ALL_tab 
	* @Description: TODO(总占用面积阶梯表格) 
	* @param @param map
	* @param @param req
	* @param @param res    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	@RequestMapping("/getMJJS_ALL_tab")
	public void getMJJS_ALL_tab(ModelMap map,HttpServletRequest req,HttpServletResponse res){
		JSONObject json = new JSONObject();
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		json.put("status", "n");
		json.put("info", "操作失败!");
		try {
			out = res.getWriter();
			String cbid = req.getParameter("ssc_cb_id");
			String id2 = req.getParameter("id2");
			String id3 = req.getParameter("id3");
			String id4 = req.getParameter("id4");
			String id5 = req.getParameter("id5");
			if(!cbid.equals("")){
				StorageCharge sc = new StorageCharge();
				sc.setSsc_whs_id(Integer.valueOf(id2));
				sc.setSsc_area_id(Integer.valueOf(id3));
				sc.setSsc_item_type(Integer.valueOf(id4));
				sc.setSsc_sc_type(Integer.valueOf(id5));
				sc.setSsc_cb_id(Integer.valueOf(cbid));
				sc.setSsc_occupied_area_type(3);
				List<StorageCharge> scList = storageChargeService.queryWAS(sc);
				List<Map<String,Object>> allAreaList = allAreaServiceImpl.findByCBID(scList.get(0).getSsc_total_area_tableid());
				json.put("list", allAreaList);
				json.put("status", "y");
				json.put("info", "操作成功!");
			}
		} catch(Exception e){
			logger.error(e);
		}
		out.write(json.toString());
		out.flush();
		out.close();
	}

	/** 
	* @Title: showTjjtCGBF 
	* @Description: TODO(超过部分占用面积阶梯) 
	* @param @param map
	* @param @param req
	* @param @param res    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	@RequestMapping("/showTjjtCGBF")
	public void showTjjtCGBF(ModelMap map,HttpServletRequest req,HttpServletResponse res){
		JSONObject json = new JSONObject();
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		json.put("status", "n");
		json.put("info", "操作失败!");
		try {
			out = res.getWriter();
			String cbid = req.getParameter("ssc_cb_id");
			if(!cbid.equals("")){
				String id2 = req.getParameter("id2");
				String id3 = req.getParameter("id3");
				String id4 = req.getParameter("id4");
				String id5 = req.getParameter("id5");
				StorageCharge sc = new StorageCharge();
				sc.setSsc_whs_id(Integer.valueOf(id2));
				sc.setSsc_area_id(Integer.valueOf(id3));
				sc.setSsc_item_type(Integer.valueOf(id4));
				sc.setSsc_sc_type(Integer.valueOf(id5));
				sc.setSsc_cb_id(Integer.valueOf(cbid));
				sc.setSsc_volume_type(2);
				List<StorageCharge> scList = storageChargeService.queryWAS(sc);
				List<Map<String,Object>> totalVolumeList = totalVolumeService.findByCBID(scList.get(0).getSsc_over_part_area_tableid());
				json.put("list", totalVolumeList);
				json.put("status", "y");
				json.put("info", "操作成功!");
			}

		} catch(Exception e){
			logger.error(e);
		}
		out.write(json.toString());
		out.flush();
		out.close();
	}

	/** 
	* @Title: showTJJTALL 
	* @Description: TODO(总占用体积阶梯) 
	* @param @param map
	* @param @param req
	* @param @param res    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	@RequestMapping("/showTJJTALL")
	public void showTJJTALL(ModelMap map,HttpServletRequest req,HttpServletResponse res){
		JSONObject json = new JSONObject();
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		json.put("status", "n");
		json.put("info", "操作失败!");
		try {
			out = res.getWriter();
			String cbid = req.getParameter("ssc_cb_id");
			String id2 = req.getParameter("id2");
			String id3 = req.getParameter("id3");
			String id4 = req.getParameter("id4");
			String id5 = req.getParameter("id5");
			if(!cbid.equals("")){
				StorageCharge sc = new StorageCharge();
				sc.setSsc_whs_id(Integer.valueOf(id2));
				sc.setSsc_area_id(Integer.valueOf(id3));
				sc.setSsc_item_type(Integer.valueOf(id4));
				sc.setSsc_sc_type(Integer.valueOf(id5));
				sc.setSsc_cb_id(Integer.valueOf(cbid));
				sc.setSsc_volume_type(3);
				List<StorageCharge> scList = storageChargeService.queryWAS(sc);
				List<Map<String,Object>> allVolumeList = allVolumeService.findByCBID(scList.get(0).getSsc_total_volume_tableid());
				json.put("list", allVolumeList);
				json.put("status", "y");
				json.put("info", "操作成功!");
			}
		} catch(Exception e){
			logger.error(e);
		}
		out.write(json.toString());
		out.flush();
		out.close();
	}

	/** 
	* @Title: SpjtCGBF 
	* @Description: TODO(超过部分体积阶梯) 
	* @param @param map
	* @param @param req
	* @param @param res    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	@RequestMapping("/showSpjtCGBF")
	public void showSpjtCGBF(ModelMap map,HttpServletRequest req,HttpServletResponse res){
		JSONObject json = new JSONObject();
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		json.put("status", "n");
		json.put("info", "操作失败!");
		try {
			out = res.getWriter();
			String cbid = req.getParameter("ssc_cb_id");
			String id2 = req.getParameter("id2");
			String id3 = req.getParameter("id3");
			String id4 = req.getParameter("id4");
			String id5 = req.getParameter("id5");
			if(!cbid.equals("")){
				StorageCharge sc = new StorageCharge();
				sc.setSsc_whs_id(Integer.valueOf(id2));
				sc.setSsc_area_id(Integer.valueOf(id3));
				sc.setSsc_item_type(Integer.valueOf(id4));
				sc.setSsc_sc_type(Integer.valueOf(id5));
				sc.setSsc_cb_id(Integer.valueOf(cbid));
				sc.setSsc_volume_calculation_type(2);
				List<StorageCharge> scList = storageChargeService.queryWAS(sc);
				List<Map<String,Object>> list = totalVolumeCalculationService.findByCBID(scList.get(0).getSsc_over_part_area_tableid());
				json.put("list", list);
				json.put("status", "y");
				json.put("info", "操作成功!");
			}
		} catch(Exception e){
			logger.error(e);
		}
		out.write(json.toString());
		out.flush();
		out.close();
	}

	/** 
	* @Title: showSpjtALL 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param map
	* @param @param req
	* @param @param res    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	@RequestMapping("/showSpjtALL")
	public void showSpjtALL(ModelMap map,HttpServletRequest req,HttpServletResponse res){
		JSONObject json = new JSONObject();
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		json.put("status", "n");
		json.put("info", "操作失败!");
		try {
			out = res.getWriter();
			String cbid = req.getParameter("ssc_cb_id");
			String id2 = req.getParameter("id2");
			String id3 = req.getParameter("id3");
			String id4 = req.getParameter("id4");
			String id5 = req.getParameter("id5");
			if(!cbid.equals("")){
				StorageCharge sc = new StorageCharge();
				sc.setSsc_whs_id(Integer.valueOf(id2));
				sc.setSsc_area_id(Integer.valueOf(id3));
				sc.setSsc_item_type(Integer.valueOf(id4));
				sc.setSsc_cb_id(Integer.valueOf(cbid));
				sc.setSsc_sc_type(Integer.valueOf(id5));
				sc.setSsc_volume_calculation_type(3);
				List<StorageCharge> scList = storageChargeService.queryWAS(sc);
				List<Map<String,Object>> list = allVolumeCalculationService.findByCBID(scList.get(0).getSsc_total_volume_tableid());
				json.put("list", list);
				json.put("status", "y");
				json.put("info", "操作成功!");
			}
		} catch(Exception e){
			logger.error(e);
		}
		out.write(json.toString());
		out.flush();
		out.close();
	}

	/** 
	* @Title: showTPJS_ALL 
	* @Description: TODO(总占用托盘数结算) 
	* @param @param map
	* @param @param req
	* @param @param res    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	@RequestMapping("/showTPJS_ALL")
	public void showTPJS_ALL(ModelMap map,HttpServletRequest req,HttpServletResponse res){
		JSONObject json = new JSONObject();
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		json.put("status", "n");
		json.put("info", "操作失败!");
		try {
			out = res.getWriter();
			String id2 = req.getParameter("id2");
			String id3 = req.getParameter("id3");
			String id4 = req.getParameter("id4");
			String id5 = req.getParameter("id5");
			String cbid = req.getParameter("ssc_cb_id");
			if(!cbid.equals("")){
				StorageCharge sc = new StorageCharge();
				sc.setSsc_whs_id(Integer.valueOf(id2));
				sc.setSsc_area_id(Integer.valueOf(id3));
				sc.setSsc_item_type(Integer.valueOf(id4));
				sc.setSsc_sc_type(Integer.valueOf(id5));
				sc.setSsc_cb_id(Integer.valueOf(cbid));
				sc.setSsc_tray_balance_type(3);
				List<StorageCharge> scList = storageChargeService.queryWAS(sc);
				List<Map<String,Object>> list = allTrayService.findByCBID(scList.get(0).getSsc_over_part_tray_tableid());
				json.put("list", list);
				json.put("status", "y");
				json.put("info", "操作成功!");
			}
		} catch(Exception e){
			logger.error(e);
		}
		out.write(json.toString());
		out.flush();
		out.close();
	}

	/** 
	* @Title: showTPJS_CGBF 
	* @Description: TODO(超过部分托盘数结算) 
	* @param @param map
	* @param @param req
	* @param @param res    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	@RequestMapping("/showTPJS_CGBF")
	public void showTPJS_CGBF(ModelMap map,HttpServletRequest req,HttpServletResponse res){
		JSONObject json = new JSONObject();
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		json.put("status", "n");
		json.put("info", "操作失败!");
		try {
			out = res.getWriter();
			String cbid = req.getParameter("ssc_cb_id");
			String id2 = req.getParameter("id2");
			String id3 = req.getParameter("id3");
			String id4 = req.getParameter("id4");
			String id5 = req.getParameter("id5");
			if(!cbid.equals("")){
				StorageCharge sc = new StorageCharge();
				sc.setSsc_whs_id(Integer.valueOf(id2));
				sc.setSsc_area_id(Integer.valueOf(id3));
				sc.setSsc_item_type(Integer.valueOf(id4));
				sc.setSsc_sc_type(Integer.valueOf(id5));
				sc.setSsc_tray_balance_type(2);
				sc.setSsc_cb_id(Integer.valueOf(cbid));
				
				List<StorageCharge> scList = storageChargeService.queryWAS(sc);
				List<Map<String,Object>> list = totalTrayService.findByCBID(scList.get(0).getSsc_total_tray_tableid());
				json.put("list", list);
				json.put("status", "y");
				json.put("info", "操作成功!");
			}
		} catch(Exception e){
			logger.error(e);
		}
		out.write(json.toString());
		out.flush();
		out.close();
	}

	/** 
	* @Title: showTPFT_CGBF 
	* @Description: TODO(超过部分托盘数量反推) 
	* @param @param map
	* @param @param req
	* @param @param res    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	@RequestMapping("/showTPFT_CGBF")
	public void showTPFT_CGBF(ModelMap map,HttpServletRequest req,HttpServletResponse res){
		JSONObject json = new JSONObject();
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		json.put("status", "n");
		json.put("info", "操作失败!");
		try {
			out = res.getWriter();
			String cbid = req.getParameter("ssc_cb_id");
			String id2 = req.getParameter("id2");
			String id3 = req.getParameter("id3");
			String id4 = req.getParameter("id4");
			String id5 = req.getParameter("id5");
			if(!cbid.equals("")){
				StorageCharge sc = new StorageCharge();
				sc.setSsc_cb_id(Integer.valueOf(cbid));
				sc.setSsc_whs_id(Integer.valueOf(id2));
				sc.setSsc_area_id(Integer.valueOf(id3));
				sc.setSsc_item_type(Integer.valueOf(id4));
				sc.setSsc_sc_type(Integer.valueOf(id5));
				sc.setSsc_single_tray_balance_type(2);
				List<StorageCharge> scList = storageChargeService.queryWAS(sc);
				List<Map<String,Object>> list = totalSingltTrayService.findByCBID(scList.get(0).getSsc_over_part_tray_tableid());
				json.put("list", list);
				json.put("status", "y");
				json.put("info", "操作成功!");
			}
		} catch(Exception e){
			logger.error(e);
		}
		out.write(json.toString());
		out.flush();
		out.close();
	}

	/** 
	* @Title: showTPFT_ALL 
	* @Description: TODO(托盘反推 总占用阶梯) 
	* @param @param map
	* @param @param req
	* @param @param res    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	@RequestMapping("/showTPFT_ALL")
	public void showTPFT_ALL(ModelMap map,HttpServletRequest req,HttpServletResponse res){
		JSONObject json = new JSONObject();
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		json.put("status", "n");
		json.put("info", "操作失败!");
		try {
			out = res.getWriter();
			String cbid = req.getParameter("ssc_cb_id");
			String id2 = req.getParameter("id2");
			String id3 = req.getParameter("id3");
			String id4 = req.getParameter("id4");
			String id5 = req.getParameter("id5");
			if(!cbid.equals("")){
				StorageCharge sc = new StorageCharge();
				sc.setSsc_cb_id(Integer.valueOf(cbid));
				sc.setSsc_whs_id(Integer.valueOf(id2));
				sc.setSsc_area_id(Integer.valueOf(id3));
				sc.setSsc_item_type(Integer.valueOf(id4));
				sc.setSsc_sc_type(Integer.valueOf(id5));
				sc.setSsc_single_tray_balance_type(3);
				List<StorageCharge> scList = storageChargeService.queryWAS(sc);
				List<Map<String,Object>> list = allSingltTrayService.findByCBID(scList.get(0).getSsc_total_single_tray_tableid());
				json.put("list", list);
				json.put("status", "y");
				json.put("info", "操作成功!");
				
				
			}
		} catch(Exception e){
			logger.error(e);
		}
		out.write(json.toString());
		out.flush();
		out.close();
	}

	/** 
	* @Title: editStorageCharge 
	* @Description: TODO(显示仓储费明细) 
	* @param @param map
	* @param @param req
	* @param @param res    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	@RequestMapping("/showStorageCharge")
	public void showStorageCharge(ModelMap map,HttpServletRequest req,HttpServletResponse res){
		JSONObject json = new JSONObject();
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		json.put("status", "n");
		json.put("info", "操作失败!");
		try {
			out = res.getWriter();
			String cbid = req.getParameter("ssc_id");
			if(!cbid.equals("")){
				List<StorageCharge> list = storageChargeService.findBySSCID(cbid);
				if(list.size()!=0){
					StorageCharge storageCharge = list.get(0);
					json.put("storageCharge", storageCharge);
					json.put("status", "y");
					json.put("info", "操作成功!");
				}
			}
		} catch(Exception e){
			logger.error(e);
		}
		out.write(json.toString());
		out.flush();
		out.close();
	}
	
	/** 
	* @Title: deleteCCFMain 
	* @Description: TODO(删除合同主标数据) 
	* @param @param map
	* @param @param req
	* @param @param res    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	@RequestMapping("/deleteCCFMain")
	public void deleteCCFMain(ModelMap map,HttpServletRequest req,HttpServletResponse res){
		JSONObject json = new JSONObject();
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		json.put("status", "n");
		json.put("info", "操作失败!");
		try {
			out = res.getWriter();
			String cbid = req.getParameter("ssc_id");
			if(!cbid.equals("")){
				storageChargeService.deleteByCBID(cbid);
				json.put("status", "y");
				json.put("info", "操作成功!");
			}
		} catch(Exception e){
			logger.error(e);
		}
		out.write(json.toString());
		out.flush();
		out.close();
	}
	
	@RequestMapping("/deletea")
	public void delete1(ModelMap map,HttpServletRequest req,HttpServletResponse res){
		JSONObject json = new JSONObject();
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		json.put("status", "n");
		json.put("info", "操作失败!");
		try {
			out = res.getWriter();
			String id = req.getParameter("id");
			storageChargeService.deleteSTAByID(id);
			json.put("status", "y");
			json.put("info", "操作成功!");
		} catch(Exception e){
			logger.error(e);
		}
		out.write(json.toString());
		out.flush();
		out.close();
	}
	

	@RequestMapping("/deleteb")
	public void delete2(ModelMap map,HttpServletRequest req,HttpServletResponse res){
		JSONObject json = new JSONObject();
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		json.put("status", "n");
		json.put("info", "操作失败!");
		try {
			out = res.getWriter();
			String id = req.getParameter("id");
			storageChargeService.deleteSAAByID(id);
			json.put("status", "y");
			json.put("info", "操作成功!");
		} catch(Exception e){
			logger.error(e);
		}
		out.write(json.toString());
		out.flush();
		out.close();
	}

	@RequestMapping("/deletec")
	public void delete3(ModelMap map,HttpServletRequest req,HttpServletResponse res){
		JSONObject json = new JSONObject();
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		json.put("status", "n");
		json.put("info", "操作失败!");
		try {
			out = res.getWriter();
			String id = req.getParameter("id");
			storageChargeService.deleteSTVByID(id);
			json.put("status", "y");
			json.put("info", "操作成功!");
		} catch(Exception e){
			logger.error(e);
		}
		out.write(json.toString());
		out.flush();
		out.close();
	}

	

	@RequestMapping("/deleted")
	public void delete4(ModelMap map,HttpServletRequest req,HttpServletResponse res){
		JSONObject json = new JSONObject();
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		json.put("status", "n");
		json.put("info", "操作失败!");
		try {
			out = res.getWriter();
			String id = req.getParameter("id");
			storageChargeService.deleteSAVByID(id);
			json.put("status", "y");
			json.put("info", "操作成功!");
		} catch(Exception e){
			logger.error(e);
		}
		out.write(json.toString());
		out.flush();
		out.close();
	}

	@RequestMapping("/deletee")
	public void delete5(ModelMap map,HttpServletRequest req,HttpServletResponse res){
		JSONObject json = new JSONObject();
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		json.put("status", "n");
		json.put("info", "操作失败!");
		try {
			out = res.getWriter();
			String id = req.getParameter("id");
			storageChargeService.deleteSTVyID(id);
			json.put("status", "y");
			json.put("info", "操作成功!");
		} catch(Exception e){
			logger.error(e);
		}
		out.write(json.toString());
		out.flush();
		out.close();
	}

	@RequestMapping("/deletef")
	public void delete6(ModelMap map,HttpServletRequest req,HttpServletResponse res){
		JSONObject json = new JSONObject();
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		json.put("status", "n");
		json.put("info", "操作失败!");
		try {
			out = res.getWriter();
			String id = req.getParameter("id");
			storageChargeService.deleteSAVyID(id);
			json.put("status", "y");
			json.put("info", "操作成功!");
		} catch(Exception e){
			logger.error(e);
		}
		out.write(json.toString());
		out.flush();
		out.close();
	}
	
	/**
	 * 
	* @Title: getCus_detail 
	* @Description: TODO(获取客户结算的明细) 
	* @param @param map
	* @param @param req
	* @param @param res    设定文件 
	* @author likun   
	* @return void    返回类型 
	* @throws
	 */	
	@RequestMapping("/getCus_detail.do")
	public String getCus_detail(TransPoolQueryParam queryParam, HttpServletRequest request) throws Exception{
		try{
			//根据条件查询合同集合
			PageView<SettleDetailBean> pageView = new PageView<SettleDetailBean>(queryParam.getPageSize()==0?BaseConst.pageSize:queryParam.getPageSize(), queryParam.getPage());
			queryParam.setFirstResult(pageView.getFirstResult());
			queryParam.setMaxResult(pageView.getMaxresult());
			queryParam.setContractId(request.getParameter("contract_id"));
			queryParam.setCreateDate(request.getParameter("createDate"));
//			queryParam.setTransport_code(request.getParameter("transport_code"));
			QueryResult<SettleDetailBean> qr = transPoolDetailServiceImpl.findAllData(queryParam);
			pageView.setQueryResult(qr, queryParam.getPage()); 
			request.setAttribute("pageView", pageView);
			request.setAttribute("queryParam", queryParam);
		}catch(Exception e){
			logger.error(e);
		}
		return "/lmis/trans_pool_detail_list";
	}
	
	
	
	@RequestMapping("/deletett")
	public void deletett(ModelMap map,HttpServletRequest req,HttpServletResponse res){
		JSONObject json = new JSONObject();
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		json.put("status", "n");
		json.put("info", "操作失败!");
		try {
			out = res.getWriter();
			String id = req.getParameter("id");
			storageChargeService.deleteSTTByID(id);
			json.put("status", "y");
			json.put("info", "操作成功!");
		} catch(Exception e){
			logger.error(e);
		}
		out.write(json.toString());
		out.flush();
		out.close();
	}

	@RequestMapping("/deletej")
	public void delete8(ModelMap map,HttpServletRequest req,HttpServletResponse res){
		JSONObject json = new JSONObject();
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		json.put("status", "n");
		json.put("info", "操作失败!");
		try {
			out = res.getWriter();
			String id = req.getParameter("id");
			storageChargeService.deleteSATByID(id);
			json.put("status", "y");
			json.put("info", "操作成功!");
		} catch(Exception e){
			logger.error(e);
		}
		out.write(json.toString());
		out.flush();
		out.close();
	}

	@RequestMapping("/deleteh")
	public void delete9(ModelMap map,HttpServletRequest req,HttpServletResponse res){
		JSONObject json = new JSONObject();
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		json.put("status", "n");
		json.put("info", "操作失败!");
		try {
			out = res.getWriter();
			String id = req.getParameter("id");
			storageChargeService.deleteSTSTByID(id);
			json.put("status", "y");
			json.put("info", "操作成功!");
		} catch(Exception e){
			logger.error(e);
		}
		out.write(json.toString());
		out.flush();
		out.close();
	}
	

	@RequestMapping("/deletei")
	public void delete10(ModelMap map,HttpServletRequest req,HttpServletResponse res){
		JSONObject json = new JSONObject();
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		json.put("status", "n");
		json.put("info", "操作失败!");
		try {
			out = res.getWriter();
			String id = req.getParameter("id");
			storageChargeService.deleteSASTByID(id);
			json.put("status", "y");
			json.put("info", "操作成功!");
		} catch(Exception e){
			logger.error(e);
		}
		out.write(json.toString());
		out.flush();
		out.close();
	}

	@RequestMapping("/data_settlement")
	public String data_settlement(StorageExpendituresDataSettlementQueryParam queryParam,ModelMap map,HttpServletRequest request,HttpServletResponse res){
		try{
			HashMap<String, Object> param = new HashMap<String,Object>();
			String id = request.getParameter("id");
			StorageDataGroup sdg = storageDataGroupService.selectById(null!=id ? Integer.valueOf(id):null);
			StorageDataGroup sdg1 = new StorageDataGroup();
			sdg1.setBatch(sdg.getBatch());
			int cb_id =sdg.getContract_id();
			ContractBasicinfo cb = contractBasicinfoServiceImpl.findById(cb_id);
			List<StorageDataGroup> list = storageDataGroupService.findBySDG(sdg1);
			// 客户/店铺使用快递结算汇总
			String balance_month = 
					CommonUtils.convertToMonth(
							new StringBuffer(
									CommonUtils.spiltDateStringByTilde(list.get(0).getBilling_cycle()).get("endDate")
									)
							);
					
//			List<Map<String, Object>> bSUEs = expressUsedBalanceService.selectRecordsBySubject(cb_id, balance_month);
			//客户物流运费结算
			param.put("create_date", list.get(0).getCreate_time());
			param.put("contract_id", list.get(0).getContract_id());
			List<CustomserTransSettleBean> cust_price = storageDataGroupService.findByCSP(param);
			
			String batch = list.get(0).getBatch();
			//根据条件查询合同集合
			PageView<StorageExpendituresDataSettlement> pageView = new PageView<StorageExpendituresDataSettlement>(queryParam.getPageSize()==0?BaseConst.pageSize:queryParam.getPageSize(), queryParam.getPage());
			queryParam.setFirstResult(pageView.getFirstResult());
			queryParam.setMaxResult(pageView.getMaxresult());
			queryParam.setBatch(batch);
			
			//Yuriy.Jiang 耗材费汇总数据 start
			NvitationdataCollect data = new NvitationdataCollect();
			data.setContract_id(Integer.valueOf(cb.getId()));
			Map<String, Date> mapss = new HashMap<String,Date>();
			mapss = DateUtil.formatyyyyMM_dd(Integer.valueOf(cb.getSettle_date()));
			data.setBilling_cycle(DateUtil.formatDate(mapss.get("start"))+" ~ "+DateUtil.formatDate(mapss.get("end")));
			List<NvitationdataCollect> nList = nvitationdataCollectService.findByCBIDACYCLE(data);
			// 操作费结算汇总查询
			request.setAttribute(
					"OF", 
					OperationFeeDataSettlementService.selectRecord(
					Integer.valueOf(cb.getId()), 
					DateUtil.formatDate(mapss.get("start"))+" ~ "+DateUtil.formatDate(mapss.get("end"))
					)
					);
			BigDecimal qty = new BigDecimal(0.00);
			BigDecimal totalamount = new BigDecimal(0.00);
			for (int i = 0; i < nList.size(); i++) {
				String skutype = nList.get(i).getSku_type();
				if(skutype.equals("0")){
					request.setAttribute("Z_QTY", nList.get(i).getQty());
					request.setAttribute("Z_UNIT", nList.get(i).getQty_unit());
					request.setAttribute("Z_PRICE", nList.get(i).getTotal_amount());
				}else{
					request.setAttribute("F_QTY", nList.get(i).getQty());
					request.setAttribute("F_UNIT", nList.get(i).getQty_unit());
					request.setAttribute("F_PRICE", nList.get(i).getTotal_amount());
				}
				qty = qty.add(nList.get(i).getQty());
				totalamount = totalamount.add(nList.get(i).getTotal_amount());
			}
			request.setAttribute("qty", qty);
			request.setAttribute("totalamount", totalamount);
			//Yuriy.Jiang 耗材费汇总数据 end

			//Yuriy。Jiang查询增值服务Start
			List<Map<String, String>> groupList = addservicefeeBilldataCollectServiceImpl.findGroupByServiceName();
			for (int i = 0; i < groupList.size(); i++) {
				AddservicefeeBilldataCollect addservicefeeBilldataCollect = new AddservicefeeBilldataCollect();
				addservicefeeBilldataCollect.setAddservice_name(groupList.get(i).get("addservice_name"));
				addservicefeeBilldataCollect.setTime("2016-09");
				List<AddservicefeeBilldataCollect> afbList = addservicefeeBilldataCollectServiceImpl.findByList(addservicefeeBilldataCollect);
			}
			//Yuriy。Jiang查询增值服务End
			QueryResult<StorageExpendituresDataSettlement> qr = storageExpendituresDataSettlementService.findAll(queryParam);
			pageView.setQueryResult(qr, queryParam.getPage());
			request.setAttribute("pageView", pageView);
			request.setAttribute("cb", cb);
			request.setAttribute("StorageExpendituresDataSettlement", qr.getResultlist().get(0));
			request.setAttribute("StorageDataGroup", list.get(0));
			request.setAttribute("CustomerDataGroup", cust_price);
			request.setAttribute("queryParam", queryParam);
//			request.setAttribute("bSUEs", bSUEs);
			request.setAttribute("balance_month", balance_month);
			request.setAttribute("sDGId", id);
		}catch(Exception e){
			logger.error(e);
			e.printStackTrace();
		}
		return "/lmis/ccf_data_settlement";
	}
}
 