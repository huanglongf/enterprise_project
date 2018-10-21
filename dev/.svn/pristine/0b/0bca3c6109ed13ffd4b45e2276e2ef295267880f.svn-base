package com.bt.lmis.controller;
import java.io.File;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bt.lmis.code.BaseController;
import com.bt.lmis.controller.form.ErCalMasterParam;
import com.bt.lmis.controller.form.ErExpressinfoAasterParam;
import com.bt.lmis.controller.form.ErExpressinfoDetailParam;
import com.bt.lmis.controller.form.TbInvitationRealuseanmountDataParam;
import com.bt.lmis.controller.form.TbInvitationUseanmountDataParam;
import com.bt.lmis.controller.form.TbOperationfeeDataParam;
import com.bt.lmis.controller.form.TbStorageExpendituresDataParam;
import com.bt.lmis.controller.form.WarehouseExpressDataParam;
import com.bt.lmis.model.ErCalMaster;
import com.bt.lmis.model.ErExpressinfoAaster;
import com.bt.lmis.model.ErExpressinfoDetail;
import com.bt.lmis.model.TbInvitationRealuseanmountData;
import com.bt.lmis.model.TbInvitationUseanmountData;
import com.bt.lmis.model.TbOperationfeeData;
import com.bt.lmis.model.TbStorageExpendituresData;
import com.bt.lmis.model.WarehouseExpressData;
import com.bt.lmis.page.PageView;
import com.bt.lmis.page.QueryResult;
import com.bt.lmis.service.TransPoolService;
import com.bt.utils.BaseConst;
import com.bt.utils.BigExcelExport;
import com.bt.utils.CommonUtils;
/***
 * 查询原始数据(快递运单)
 * @author BZ
 *  hanwei
 */
@Controller
@RequestMapping("/control/rawDataExpressQueryController")
public class RawDataExpressQueryController extends BaseController{
	private static final Logger logger = Logger.getLogger(RawDataExpressQueryController.class);
	
	@Resource(name="transPoolServiceImpl")
	private TransPoolService<T> transPoolServiceImpl;
	
	SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
	
	
	@RequestMapping("findExpress")
	public String findExpress(WarehouseExpressDataParam warehouseExpressDataPar, ModelMap map, HttpServletRequest request) {
		try {
			PageView<WarehouseExpressData> pageView = null;
			String createTime = warehouseExpressDataPar.getCreate_time();
			String storeName = warehouseExpressDataPar.getStore_name();
			String costCenter = warehouseExpressDataPar.getCost_center();
			String ctoreCode = warehouseExpressDataPar.getStore_code();
			if(CommonUtils.checkExistOrNot(createTime)||CommonUtils.checkExistOrNot(storeName)||CommonUtils.checkExistOrNot(costCenter)||CommonUtils.checkExistOrNot(ctoreCode)){
				pageView = new PageView<WarehouseExpressData>(
						warehouseExpressDataPar.getPageSize() == 0 ? BaseConst.pageSize : warehouseExpressDataPar.getPageSize(),
								warehouseExpressDataPar.getPage());
				if(CommonUtils.checkExistOrNot(request.getParameter("create_time"))){
					String time = this.fmtDate(request.getParameter("create_time"));
					warehouseExpressDataPar.setCreate_time(time.split("_")[0]);
					warehouseExpressDataPar.setUpdate_time(fmt.parse(time.split("_")[1]));
				}
				warehouseExpressDataPar.setFirstResult(pageView.getFirstResult());	
				warehouseExpressDataPar.setMaxResult(pageView.getMaxresult());
				warehouseExpressDataPar.setStore_name(request.getParameter("store_name"));
				warehouseExpressDataPar.setCost_center(request.getParameter("cost_center"));
				warehouseExpressDataPar.setStore_code(request.getParameter("store_code"));
				QueryResult<WarehouseExpressData> qrRaw = transPoolServiceImpl.queryRawExpress(warehouseExpressDataPar);
				pageView.setQueryResult(qrRaw, warehouseExpressDataPar.getPage()); 	
			}
			request.setAttribute("pageView", pageView);
			request.setAttribute("warehouseExpressDataPar", warehouseExpressDataPar);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "/lmis/express_list"; 
	}
	/***
	 * 时间处理
	 * @param times
	 * @return
	 */
	public  String fmtDate(String times){
		return times.substring(0, 10)+"_"+times.substring(12, times.length()).trim();
	}
	
	/***
	 * 雷达
	 * @param warehouseExpressDataPar
	 * @param map
	 * @param request
	 * @return
	 */
	@RequestMapping("radarQuery")
	public String  radarQuery(ErCalMasterParam erCalMasterPar, ModelMap map, HttpServletRequest request){
		try {
			PageView<ErCalMaster> pageView = null;
			if(CommonUtils.checkExistOrNot(request.getParameter("waybill"))||CommonUtils.checkExistOrNot(request.getParameter("warehouse_code"))){
				pageView = new PageView<ErCalMaster>(
						erCalMasterPar.getPageSize() == 0 ? BaseConst.pageSize : erCalMasterPar.getPageSize(),
								erCalMasterPar.getPage());
				erCalMasterPar.setFirstResult(pageView.getFirstResult());
				erCalMasterPar.setMaxResult(pageView.getMaxresult());
				erCalMasterPar.setWaybill(request.getParameter("waybill"));
				erCalMasterPar.setWarehouse_code(request.getParameter("warehouse_code"));
				QueryResult<ErCalMaster> qrSetRadar = transPoolServiceImpl.queryRadar(erCalMasterPar);
				pageView.setQueryResult(qrSetRadar, erCalMasterPar.getPage()); 
			}
			request.setAttribute("pageView", pageView);
			request.setAttribute("erCalMasterPar", erCalMasterPar);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "/lmis/radar_list"; 
	}
	/***
	 * 雷达主表
	 * @param warehouseExpressDataPar
	 * @param map
	 * @param request
	 * @return
	 */
	@RequestMapping("radarMainQuery")
	public String  radarMainQuery(ErExpressinfoAasterParam erExpressinfoAasterPar, ModelMap map, HttpServletRequest request){
		try {
			PageView<ErExpressinfoAaster> pageView = null;
			if(CommonUtils.checkExistOrNot(request.getParameter("store_code"))||CommonUtils.checkExistOrNot(request.getParameter("warehouse_code"))||CommonUtils.checkExistOrNot(request.getParameter("waybill"))){
				pageView = new PageView<ErExpressinfoAaster>(
						erExpressinfoAasterPar.getPageSize() == 0 ? BaseConst.pageSize : erExpressinfoAasterPar.getPageSize(),
								erExpressinfoAasterPar.getPage());
				if(CommonUtils.checkExistOrNot(request.getParameter("create_time"))){
					String time = this.fmtDate(request.getParameter("create_time"));
					erExpressinfoAasterPar.setCreate_time(time.split("_")[0]);
					erExpressinfoAasterPar.setUpdate_time(fmt.parse(time.split("_")[1]));
				}
				erExpressinfoAasterPar.setFirstResult(pageView.getFirstResult());
				erExpressinfoAasterPar.setMaxResult(pageView.getMaxresult());
				erExpressinfoAasterPar.setStore_code(request.getParameter("store_code"));
				erExpressinfoAasterPar.setWarehouse_code(request.getParameter("warehouse_code"));
				erExpressinfoAasterPar.setWaybill(request.getParameter("waybill"));
				QueryResult<ErExpressinfoAaster> qrSetRadarMain = transPoolServiceImpl.queryRadarMain(erExpressinfoAasterPar);
				pageView.setQueryResult(qrSetRadarMain, erExpressinfoAasterPar.getPage()); 
			}
			request.setAttribute("pageView", pageView);
			request.setAttribute("erExpressinfoAasterPar", erExpressinfoAasterPar);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "/lmis/radarMain_list"; 
	}
	/***
	 * 快递运单子信息
	 * @param warehouseExpressDataPar
	 * @param map
	 * @param request
	 * @return
	 */
	@RequestMapping("expressInformationQuery")
	public String  expressInformationQuery(ErExpressinfoDetailParam erExpressinfoDetailPar, ModelMap map, HttpServletRequest request){
		try {
			PageView<ErExpressinfoDetail> pageView = null;
			if(CommonUtils.checkExistOrNot(request.getParameter("barcode"))||CommonUtils.checkExistOrNot(request.getParameter("waybill"))){
				pageView = new PageView<ErExpressinfoDetail>(
						erExpressinfoDetailPar.getPageSize() == 0 ? BaseConst.pageSize : erExpressinfoDetailPar.getPageSize(),
								erExpressinfoDetailPar.getPage());
				if(CommonUtils.checkExistOrNot(request.getParameter("create_time"))){
					String time = this.fmtDate(request.getParameter("create_time"));
					erExpressinfoDetailPar.setCreate_time(time.split("_")[0]);
					erExpressinfoDetailPar.setUpdate_time(fmt.parse(time.split("_")[1]));
				}
				erExpressinfoDetailPar.setFirstResult(pageView.getFirstResult());
				erExpressinfoDetailPar.setMaxResult(pageView.getMaxresult());
				erExpressinfoDetailPar.setBarcode(request.getParameter("barcode"));
				erExpressinfoDetailPar.setWaybill(request.getParameter("waybill"));
				erExpressinfoDetailPar.setItem_name(request.getParameter("item_name"));
				QueryResult<ErExpressinfoDetail> qrSetExpressInfo = transPoolServiceImpl.queryExpressInfor(erExpressinfoDetailPar);
				pageView.setQueryResult(qrSetExpressInfo, erExpressinfoDetailPar.getPage()); 
			}
			request.setAttribute("pageView", pageView);
			request.setAttribute("erExpressinfoDetailPar", erExpressinfoDetailPar);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "/lmis/Information_list"; 
	}
	/**
	 * 仓储费
	 * @param warehouseExpressDataPar
	 * @param map
	 * @param request
	 * @return
	 */
	@RequestMapping("warehousingQuery")
	public String  warehousingQuery(TbStorageExpendituresDataParam tbStorageExpendituresDataPar, ModelMap map, HttpServletRequest request){
		try {
			PageView<TbStorageExpendituresData> pageView = null;
			if(CommonUtils.checkExistOrNot(request.getParameter("store_name"))||CommonUtils.checkExistOrNot(request.getParameter("warehouse_name"))){
				pageView = new PageView<TbStorageExpendituresData>(
						tbStorageExpendituresDataPar.getPageSize() == 0 ? BaseConst.pageSize : tbStorageExpendituresDataPar.getPageSize(),
								tbStorageExpendituresDataPar.getPage());
				if(CommonUtils.checkExistOrNot(request.getParameter("create_time"))){
					String time = this.fmtDate(request.getParameter("create_time"));
					tbStorageExpendituresDataPar.setCreate_time(time.split("_")[0]);
					tbStorageExpendituresDataPar.setUpdate_time(fmt.parse(time.split("_")[1]));
				}
				tbStorageExpendituresDataPar.setFirstResult(pageView.getFirstResult());
				tbStorageExpendituresDataPar.setMaxResult(pageView.getMaxresult());
				tbStorageExpendituresDataPar.setStore_name(request.getParameter("store_name"));
				tbStorageExpendituresDataPar.setWarehouse_name(request.getParameter("warehouse_name"));
				tbStorageExpendituresDataPar.setWarehouse_code(request.getParameter("warehouse_code"));
				QueryResult<TbStorageExpendituresData> qrSetWarehousing = transPoolServiceImpl.queryWarehousing(tbStorageExpendituresDataPar);
				pageView.setQueryResult(qrSetWarehousing, tbStorageExpendituresDataPar.getPage()); 
			}
			request.setAttribute("pageView", pageView);
			request.setAttribute("tbStorageExpendituresDataPar", tbStorageExpendituresDataPar);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "/lmis/warehousing_list"; 
	}
	/***
	 * 操作费
	 * @param warehouseExpressDataPar
	 * @param map
	 * @param request
	 * @return
	 */
	@RequestMapping("operatingCostQuery")
	public String  operatingCostQuery(TbOperationfeeDataParam tbOperationfeeDataPar, ModelMap map, HttpServletRequest request){
		try {
			PageView<TbOperationfeeData> pageView = null;
			if(CommonUtils.checkExistOrNot(request.getParameter("store_name"))||CommonUtils.checkExistOrNot(request.getParameter("job_orderno"))||CommonUtils.checkExistOrNot(request.getParameter("art_no"))||CommonUtils.checkExistOrNot(request.getParameter("inventory_status"))){
				pageView = new PageView<TbOperationfeeData>(
						tbOperationfeeDataPar.getPageSize() == 0 ? BaseConst.pageSize : tbOperationfeeDataPar.getPageSize(),
								tbOperationfeeDataPar.getPage());
				tbOperationfeeDataPar.setFirstResult(pageView.getFirstResult());
				tbOperationfeeDataPar.setMaxResult(pageView.getMaxresult());
				tbOperationfeeDataPar.setStore_name(request.getParameter("store_name"));
				tbOperationfeeDataPar.setJob_orderno(request.getParameter("job_orderno"));
				tbOperationfeeDataPar.setArt_no(request.getParameter("art_no"));
				tbOperationfeeDataPar.setInventory_status(request.getParameter("inventory_status"));
				QueryResult<TbOperationfeeData> qrSetCost = transPoolServiceImpl.operatingCostDetail(tbOperationfeeDataPar);
				pageView.setQueryResult(qrSetCost, tbOperationfeeDataPar.getPage()); 
			}
			request.setAttribute("pageView", pageView);
			request.setAttribute("tbOperationfeeDataPar", tbOperationfeeDataPar);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "/lmis/operatingCost_list"; 
	}
	/***
	 * 耗材实际使用量
	 * @param warehouseExpressDataPar
	 * @param map
	 * @param request
	 * @return
	 */
	@RequestMapping("suppliesAmountQuery")
	public String  suppliesAmountQuery(TbInvitationRealuseanmountDataParam tbInvitationRealuseanmountDataPar, ModelMap map, HttpServletRequest request){
		try {
			PageView<TbInvitationRealuseanmountData> pageView = null;
			if(CommonUtils.checkExistOrNot(request.getParameter("store_name"))||CommonUtils.checkExistOrNot(request.getParameter("sku_code"))||CommonUtils.checkExistOrNot(request.getParameter("cost_center"))||CommonUtils.checkExistOrNot(request.getParameter("sku_name"))){
				pageView = new PageView<TbInvitationRealuseanmountData>(
						tbInvitationRealuseanmountDataPar.getPageSize() == 0 ? BaseConst.pageSize : tbInvitationRealuseanmountDataPar.getPageSize(),
								tbInvitationRealuseanmountDataPar.getPage());
				if(CommonUtils.checkExistOrNot(request.getParameter("create_time"))){
					String time = this.fmtDate(request.getParameter("create_time"));
					tbInvitationRealuseanmountDataPar.setCreate_time(time.split("_")[0]);
					tbInvitationRealuseanmountDataPar.setUpdate_time(fmt.parse(time.split("_")[1]));
				}
				tbInvitationRealuseanmountDataPar.setFirstResult(pageView.getFirstResult());
				tbInvitationRealuseanmountDataPar.setMaxResult(pageView.getMaxresult());
				tbInvitationRealuseanmountDataPar.setStore_name(request.getParameter("store_name"));
				tbInvitationRealuseanmountDataPar.setSku_code(request.getParameter("sku_code"));
				tbInvitationRealuseanmountDataPar.setCost_center(request.getParameter("cost_center"));
				tbInvitationRealuseanmountDataPar.setSku_name(request.getParameter("sku_name"));
				QueryResult<TbInvitationRealuseanmountData> qrSetSuppliesAmount = transPoolServiceImpl.querySuppliesAmount(tbInvitationRealuseanmountDataPar);
				pageView.setQueryResult(qrSetSuppliesAmount, tbInvitationRealuseanmountDataPar.getPage()); 
			}
			request.setAttribute("pageView", pageView);
			request.setAttribute("tbInvitationRealuseanmountDataPar", tbInvitationRealuseanmountDataPar);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "/lmis/suppliesAmount_list"; 
	}
	/***
	 * 耗材采购明细
	 * @param warehouseExpressDataPar
	 * @param map
	 * @param request
	 * @return
	 */
	@RequestMapping("suppliesDetailQuery")
	public String  suppliesDetailQuery(TbInvitationUseanmountDataParam tbInvitationUseanmountDataPar, ModelMap map, HttpServletRequest request){
		try {
			PageView<TbInvitationUseanmountData> pageView = null;
			if(CommonUtils.checkExistOrNot(request.getParameter("store_name"))||CommonUtils.checkExistOrNot(request.getParameter("inbound_no"))||CommonUtils.checkExistOrNot(request.getParameter("bz_number"))||CommonUtils.checkExistOrNot(request.getParameter("item_name"))||CommonUtils.checkExistOrNot(request.getParameter("create_time"))){
				pageView = new PageView<TbInvitationUseanmountData>(
						tbInvitationUseanmountDataPar.getPageSize() == 0 ? BaseConst.pageSize : tbInvitationUseanmountDataPar.getPageSize(),
								tbInvitationUseanmountDataPar.getPage());
				if(CommonUtils.checkExistOrNot(request.getParameter("create_time"))){
					String time = this.fmtDate(request.getParameter("create_time"));
					tbInvitationUseanmountDataPar.setCreate_time(time.split("_")[0]);
					tbInvitationUseanmountDataPar.setUpdate_time(fmt.parse(time.split("_")[1]));
				}
				tbInvitationUseanmountDataPar.setFirstResult(pageView.getFirstResult());
				tbInvitationUseanmountDataPar.setMaxResult(pageView.getMaxresult());
				tbInvitationUseanmountDataPar.setStore_name(request.getParameter("store_name"));
				tbInvitationUseanmountDataPar.setInbound_no(request.getParameter("inbound_no"));
				tbInvitationUseanmountDataPar.setBz_number(request.getParameter("bz_number"));
				tbInvitationUseanmountDataPar.setItem_name(request.getParameter("item_name"));
				QueryResult<TbInvitationUseanmountData> qrSet = transPoolServiceImpl.querySuppliesDetail(tbInvitationUseanmountDataPar);
				pageView.setQueryResult(qrSet, tbInvitationUseanmountDataPar.getPage()); 
			}
			request.setAttribute("pageView", pageView);
			request.setAttribute("tbInvitationUseanmountDataPar", tbInvitationUseanmountDataPar);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "/lmis/suppliesDetail_list";
	}
	@RequestMapping("photograph")
	public String photograph(){
		return "/lmis/text";
	}
	
	/***
	 *耗材采购明细(导出) 
	 * @param request
	 */
	@RequestMapping("suppliesDetailExport")
	public void  suppliesDetailExport(TbInvitationUseanmountDataParam tbInvitationUseanmountDataPar,HttpServletRequest request,HttpServletResponse response){
		PrintWriter out= null;
		File res= new File("");
		try {
			String create_time= null;
			String update_time= null;
			out= response.getWriter();
			String store_name= request.getParameter("store_name").trim();
			String inbound_no= request.getParameter("inbound_no").trim();
			String bz_number= request.getParameter("bz_number").trim();
			String item_name= request.getParameter("item_name").trim();
			String createTime= request.getParameter("create_time"); 
			if(CommonUtils.checkExistOrNot(createTime)){
				String time= this.fmtDate(createTime);
				create_time= time.split("_")[0].trim();
				update_time= time.split("_")[1].trim();
				
			}
			List<Map<String, Object>>list= transPoolServiceImpl.findInvitationUseanmount(store_name, inbound_no, bz_number, item_name, create_time, update_time);
			LinkedHashMap<String, String> CMap= new LinkedHashMap<String, String>();
			CMap.put("create_time", "创建时间");
			CMap.put("create_user", "创建人");
			CMap.put("update_time", "更新时间");
			CMap.put("ib_time", "入库时间");
			CMap.put("store_name", "店铺名称");
			CMap.put("vendor", "供应商");
			CMap.put("inbound_no", "PO单号");
			CMap.put("bz_number", "宝尊编码");
			CMap.put("inbound_qty", "实际入库数量");
			CMap.put("purchase_price", "采购单价");
			CMap.put("paymentdays_type", "账期类型");
			res= CommonUtils.excelDownLoadData(list,CMap,"耗材采购明细.xls");
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		out.write(CommonUtils.getAllMessage("config", "COMMON_DOWNLOAD_MAP")+ res.getName());
		out.flush();
		
	}
	/***
	 * 仓储费导出
	 * @param request
	 * @param response
	 */
	@RequestMapping("warehousingExport")
	public void warehousingExport(HttpServletRequest request,HttpServletResponse response){
		PrintWriter out= null;
		File res= new File("");
		try {
			out= response.getWriter();
			Map<String, Object>param= getParamMap(request);
			List<?> list= transPoolServiceImpl.warehousingExport(param);
			LinkedHashMap<String, String> CMapWare=new LinkedHashMap<String, String>();
			CMapWare.put("create_time", "创建时间");
			CMapWare.put("update_time", "更新时间");
			CMapWare.put("start_time", "时间");
			CMapWare.put("store_name", "店铺名称");
			CMapWare.put("warehouse_code", "仓库代码");
			CMapWare.put("warehouse_name", "仓库名称");
			CMapWare.put("system_warehouse", "系统仓");
			CMapWare.put("item_type", "商品类型");
			res= CommonUtils.excelDownLoadData(list, CMapWare, "仓储费采购明细.xls");
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		out.write(CommonUtils.getAllMessage("config", "COMMON_DOWNLOAD_MAP")+ res.getName());
		out.flush();
		
	}
	/***
	 * 操作费导出
	 * @param request
	 * @param response
	 */
	@RequestMapping("operatingCostExport")
	public  void operatingCostExport(HttpServletRequest request,HttpServletResponse response){
		PrintWriter out = null;
		File res=new File("");
		try {
			out= response.getWriter();
			String store_name= request.getParameter("store_name");
			String job_orderno= request.getParameter("job_orderno"); 
			String art_no= request.getParameter("art_no"); 
			String inventory_status= request.getParameter("inventory_status"); 
			List<Map<String, Object>>list= transPoolServiceImpl.findOperatingCostExport(store_name,job_orderno,art_no,inventory_status);
			LinkedHashMap<String, String> CMapOpera=new LinkedHashMap<String, String>();
			CMapOpera.put("create_time", "创建时间");
			CMapOpera.put("update_time", "更新时间");
			CMapOpera.put("order_type", "订单类型");
			CMapOpera.put("store_name", "店铺名称");
			CMapOpera.put("job_orderno", "作业单号");
			CMapOpera.put("job_type", "作业类型名称");
			CMapOpera.put("storaggelocation_code", "库位编码");
			CMapOpera.put("in_num", "入库数量");
			CMapOpera.put("item_number", "商品编码");
			CMapOpera.put("inventory_status", "库存状态");
			res=BigExcelExport.excelDownLoadDatab(list, CMapOpera, "操作费明细.xls");
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		out.write(CommonUtils.getAllMessage("config", "COMMON_DOWNLOAD_MAP") + res.getName());
		out.flush();
		
	}
	/***
	 * 耗材实际使用量导出
	 * @param request
	 * @param response
	 */
	@RequestMapping("suppliesAmountExport")
	public  void suppliesAmountExport(HttpServletRequest request,HttpServletResponse response){
		PrintWriter out = null;
		File res=new File("");
		try {
			String create_time= null;
			String update_time= null;
			out= response.getWriter();
			String store_name= request.getParameter("store_name");
			String sku_code= request.getParameter("sku_code"); 
			String cost_center= request.getParameter("cost_center"); 
			String sku_name= request.getParameter("sku_name"); 
			String createTime= request.getParameter("create_time"); 
			if(CommonUtils.checkExistOrNot(createTime)){
				String time = this.fmtDate(createTime);
				create_time = time.split("_")[0].trim();
				update_time = time.split("_")[1].trim();
			}
			List<Map<String, Object>>list=transPoolServiceImpl.findSuppliesAmountExport(store_name,sku_code,cost_center,sku_name,create_time,update_time);
			LinkedHashMap<String, String> CMapSupplies=new LinkedHashMap<String, String>();
			CMapSupplies.put("create_time", "创建时间");
			CMapSupplies.put("update_time", "更新时间");
			CMapSupplies.put("store_code", "店铺代码");
			CMapSupplies.put("store_name", "店铺名称");
			CMapSupplies.put("cost_center", "成本中心"); 
			CMapSupplies.put("sku_code", "耗材编码");
			CMapSupplies.put("sku_name", "耗材名称");
			res=BigExcelExport.excelDownLoadDatab(list,CMapSupplies,"耗材实际使用量.xls");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		out.write(CommonUtils.getAllMessage("config", "COMMON_DOWNLOAD_MAP") + res.getName());
		out.flush();
	}
	
	/***
	 * 快递运单子信息导出
	 * @param request
	 * @param response
	 */
	@RequestMapping("expressInformationExport")
	public void expressInformationExport(HttpServletRequest request,HttpServletResponse response){
		PrintWriter out = null;
		File res=new File("");
		try {
			String create_time = null;
			String update_time = null;
			out = response.getWriter();
			String  barcode  = request.getParameter("barcode");
			String  waybill  = request.getParameter("waybill"); 
			String  item_name  = request.getParameter("item_name"); 
			String  createTime  = request.getParameter("create_time"); 
			if(CommonUtils.checkExistOrNot(createTime)){
				String time = this.fmtDate(createTime);
				create_time = time.split("_")[0].trim();
				update_time = time.split("_")[1].trim();
			}
			List<Map<String, Object>>list=transPoolServiceImpl.QueryexpressInformationExport(barcode,waybill,item_name,create_time,update_time);
			LinkedHashMap<String, String> CMapExpressInfor=new LinkedHashMap<String, String>();
			CMapExpressInfor.put("create_time", "创建时间");
			CMapExpressInfor.put("update_time", "更新时间");
			CMapExpressInfor.put("sku_number", "SKU条码");
			CMapExpressInfor.put("barcode", "条形码");
			CMapExpressInfor.put("item_name", "商品名称");
			CMapExpressInfor.put("waybill", "运单号");
			res=BigExcelExport.excelDownLoadDatab(list,CMapExpressInfor,"快递运单子信息.xls");
		} catch (Exception e) {
			e.printStackTrace();
		}
		out.write(CommonUtils.getAllMessage("config", "COMMON_DOWNLOAD_MAP") + res.getName());
		out.flush();
	}
	/***
	 * 雷达导出
	 * @param request
	 * @param response
	 */
	@RequestMapping("radarExport")
	public  void radarExport(HttpServletRequest request,HttpServletResponse response){
		PrintWriter out = null;
		File res=new File("");
		try {
			out = response.getWriter();
			String  waybill  = request.getParameter("waybill");
			String  warehouse_code  = request.getParameter("warehouse_code"); 
			List<Map<String, Object>>list=transPoolServiceImpl.queryRadarExport(waybill,warehouse_code);
			LinkedHashMap<String, String> CMapRadar=new LinkedHashMap<String, String>();
			CMapRadar.put("waybill", "运单号");
			CMapRadar.put("express_code", "物流公司");
			CMapRadar.put("warehouse_code", "店铺Code");
			CMapRadar.put("p_code", "省Code");
			CMapRadar.put("c_code", "市Code");
			CMapRadar.put("s_code", "区/县Code");
			res=BigExcelExport.excelDownLoadDatab(list,CMapRadar,"雷达信息.xls");
		} catch (Exception e) {
			e.printStackTrace();
		}
		out.write(CommonUtils.getAllMessage("config", "COMMON_DOWNLOAD_MAP") + res.getName());
		out.flush();
	}
	/***
	 * 雷达主表导出
	 * @param request
	 * @param response
	 */
	@RequestMapping("radarMainExport")
	public void radarMainExport(HttpServletRequest request,HttpServletResponse response){
		PrintWriter out = null;
		File res=new File("");
		try {
			String create_time = null;
			String update_time = null;
			out = response.getWriter();
			String  store_code  = request.getParameter("store_code");
			String  warehouse_code  = request.getParameter("warehouse_code"); 
			String  waybill  = request.getParameter("waybill"); 
			String  createTime  = request.getParameter("create_time");
			if(CommonUtils.checkExistOrNot(createTime)){
				String time = this.fmtDate(createTime);
				create_time = time.split("_")[0].trim();
				update_time = time.split("_")[1].trim();
			}
			List<Map<String, Object>>list=transPoolServiceImpl.findRadarMainExport(store_code,warehouse_code,waybill,create_time,update_time);
			LinkedHashMap<String, String> CMapRadarMain=new LinkedHashMap<String, String>();
			CMapRadarMain.put("create_time", "创建时间");
			CMapRadarMain.put("update_time", "更新时间");
			CMapRadarMain.put("waybill", "快递单号");
			CMapRadarMain.put("express_code", "快递服务商代码");
			CMapRadarMain.put("platform_no", "平台订单号");
			CMapRadarMain.put("store_code", "店铺代码");
			CMapRadarMain.put("warehouse_code", "揽件仓库代码（物理仓）");
			CMapRadarMain.put("shiptoname", "收件人");
			CMapRadarMain.put("phone", "联系电话");
			CMapRadarMain.put("address", "收件地址");
			CMapRadarMain.put("provice_code", "目的省代码");
			CMapRadarMain.put("city_code", "目的市代码");
			CMapRadarMain.put("state_code", "目的区代码");
			CMapRadarMain.put("street_code", "目的街道代码");
			res=BigExcelExport.excelDownLoadDatab(list,CMapRadarMain,"雷达主表信息.xls");
		} catch (Exception e) {
			e.printStackTrace();
		}
		out.write(CommonUtils.getAllMessage("config", "COMMON_DOWNLOAD_MAP") + res.getName());
		out.flush();
	}
	/****
	 * 快递运单导出
	 * @param request
	 * @param response
	 */
	@RequestMapping("queryExpressExport")
	public  void  queryExpressExport(HttpServletRequest request,HttpServletResponse response){
		PrintWriter out = null;
		File res=new File("");
		try {
			String create_time = null;
			String update_time = null;
			out = response.getWriter();
			String  createTime  = request.getParameter("create_time");
			String  store_name  = request.getParameter("store_name"); 
			String  cost_center  = request.getParameter("cost_center"); 
			String  store_code  = request.getParameter("store_code"); 
			if(CommonUtils.checkExistOrNot(createTime)){
				String time = this.fmtDate(createTime);
				create_time = time.split("_")[0].trim();
				update_time = time.split("_")[1].trim();
			}
			List<Map<String, Object>>list=transPoolServiceImpl.queryExpressExport(store_name,cost_center,store_code,create_time,update_time);
			LinkedHashMap<String, String> CMapExpress=new LinkedHashMap<String, String>();
			CMapExpress.put("create_time", "创建时间");
			CMapExpress.put("update_time", "更新时间");
			CMapExpress.put("cost_center", "成本中心编码");
			CMapExpress.put("store_code", "店铺代码");
			CMapExpress.put("store_name", "店铺名称");
			CMapExpress.put("warehouse", "所属仓库");
			CMapExpress.put("transport_code", "运输公司代码");
			CMapExpress.put("transport_name", "快递名称");
			CMapExpress.put("delivery_order", "发货指令");
			CMapExpress.put("order_type", "订单类型");
			CMapExpress.put("express_number", "运单号");
			CMapExpress.put("transport_time", "订单生成时间");
			CMapExpress.put("weight", "实际重量");
			CMapExpress.put("length", "长");
			CMapExpress.put("width", "宽");
			CMapExpress.put("higth", "高");
			CMapExpress.put("volumn", "体积");
			res=BigExcelExport.excelDownLoadDatab(list,CMapExpress,"快递运单.xls");
		} catch (Exception e) {
			e.printStackTrace();
		}
		out.write(CommonUtils.getAllMessage("config", "COMMON_DOWNLOAD_MAP") + res.getName());
		out.flush();
	}
}
