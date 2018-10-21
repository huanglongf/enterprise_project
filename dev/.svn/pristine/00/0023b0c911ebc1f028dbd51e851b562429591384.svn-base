package com.bt.lmis.controller;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bt.lmis.code.BaseController;
import com.bt.lmis.controller.form.TransPoolQueryParam;
import com.bt.lmis.model.SettleDetailBean;
import com.bt.lmis.model.SettleInvitationBean;
import com.bt.lmis.model.SettleOperatorBean;
import com.bt.lmis.model.SettleOrderBean;
import com.bt.lmis.model.SettleOrderDetailBean;
import com.bt.lmis.model.SettleStorageBean;
import com.bt.lmis.model.SettleUseBean;
import com.bt.lmis.model.TransPoolBean;
import com.bt.lmis.page.PageView;
import com.bt.lmis.page.QueryResult;
import com.bt.lmis.service.TransPoolDetailService;
import com.bt.lmis.service.TransPoolService;
import com.bt.utils.BaseConst;
import com.bt.utils.BigExcelExport;
import com.bt.utils.CommonUtils;
/**
 * 
* @ClassName: TransPoolController 
* @Description: TODO(物流运费汇总报表) 
* @author likun
* @date 2016年8月25日 上午9:26:02 
*
 */
@Controller
@RequestMapping("/control/transPoolController")
public class TransPoolController extends BaseController{

	private static final Logger logger = Logger.getLogger(TransPoolController.class);
	
	@Resource(name="transPoolServiceImpl")
	private TransPoolService<TransPoolBean>transPoolServiceImpl;
    
	@Resource(name="transPoolDetailServiceImpl")
	private TransPoolDetailService<SettleDetailBean>transPoolDetailServiceImpl;
	
	
	@RequestMapping("/listMain.do")
	public String getPoolMainInfo(TransPoolQueryParam queryParam, HttpServletRequest request) throws Exception{
		//根据条件查询合同集合
		PageView<TransPoolBean> pageView = new PageView<TransPoolBean>(queryParam.getPageSize()==0?BaseConst.pageSize:queryParam.getPageSize(), queryParam.getPage());
		queryParam.setFirstResult(pageView.getFirstResult());
		queryParam.setMaxResult(pageView.getMaxresult());
		QueryResult<TransPoolBean> qr = transPoolServiceImpl.findMainData(queryParam);
		pageView.setQueryResult(qr, queryParam.getPage()); 
		request.setAttribute("pageView", pageView);
		request.setAttribute("queryParam", queryParam);		
		return "/lmis/trans_pool_main_list";
	}
	
	
	
	@RequestMapping("/list.do")
	public String getPoolInfo(TransPoolQueryParam queryParam, HttpServletRequest request) throws Exception{
		
		try{
			//根据条件查询合同集合
			PageView<TransPoolBean> pageView = new PageView<TransPoolBean>(queryParam.getPageSize()==0?BaseConst.pageSize:queryParam.getPageSize(), queryParam.getPage());
			queryParam.setFirstResult(pageView.getFirstResult());
			queryParam.setMaxResult(pageView.getMaxresult());
			System.out.println(request.getParameter("createDate"));
			queryParam.setContractId(request.getParameter("contractId"));
			queryParam.setStoreName(request.getParameter("storeName"));
			queryParam.setTransportCode(request.getParameter("transportCode"));
			QueryResult<TransPoolBean> qr = transPoolServiceImpl.findAllData(queryParam);
			pageView.setQueryResult(qr, queryParam.getPage()); 
			request.setAttribute("pageView", pageView);
			request.setAttribute("queryParam", queryParam);
		}catch(Exception e){
			logger.error(e);
		}
		return "/lmis/trans_pool_list";
	}
	@RequestMapping("/messageMainList.do")
	public String queryMessage(TransPoolQueryParam queryParam, HttpServletRequest request) throws Exception{
		try{
			String dataType = request.getParameter("dataType");
			String proFlag = request.getParameter("proFlag");
			if("".equals(dataType)||dataType==null){
				dataType = "1";
			}
			if("".equals(proFlag)||proFlag==null){
				proFlag = "0";
			}else{
				queryParam.setProFlag(proFlag);
			}
			if("1".equals(dataType)){
				PageView<SettleInvitationBean> pageView = new PageView<SettleInvitationBean>(queryParam.getPageSize()==0?BaseConst.pageSize:queryParam.getPageSize(), queryParam.getPage());
				QueryResult<SettleInvitationBean> qrSet = transPoolServiceImpl.querySettleInvitation(queryParam);
				pageView.setQueryResult(qrSet, queryParam.getPage()); 
				request.setAttribute("pageView", pageView);
				request.setAttribute("queryParam", queryParam);
				request.setAttribute("dataType", dataType);
				request.setAttribute("proFlag", proFlag);
			}else if("2".equals(dataType)){
				PageView<SettleOperatorBean> pageView = new PageView<SettleOperatorBean>(queryParam.getPageSize()==0?BaseConst.pageSize:queryParam.getPageSize(), queryParam.getPage());
				QueryResult<SettleOperatorBean> qrSetOperator = transPoolServiceImpl.querySettleOperator(queryParam);
				pageView.setQueryResult(qrSetOperator, queryParam.getPage()); 
				request.setAttribute("pageView", pageView);
				request.setAttribute("queryParam", queryParam);
				request.setAttribute("dataType", dataType);
				request.setAttribute("proFlag", proFlag);
			}else if("3".equals(dataType)){
				PageView<SettleOrderBean> pageView = new PageView<SettleOrderBean>(queryParam.getPageSize()==0?BaseConst.pageSize:queryParam.getPageSize(), queryParam.getPage());
				QueryResult<SettleOrderBean> qrSetOrder = transPoolServiceImpl.querySettleOrder(queryParam);
				pageView.setQueryResult(qrSetOrder, queryParam.getPage()); 
				request.setAttribute("pageView", pageView);
				request.setAttribute("queryParam", queryParam); 
				request.setAttribute("dataType", dataType);
				request.setAttribute("proFlag", proFlag);
			}else if("4".equals(dataType)){
				PageView<SettleOrderDetailBean> pageView = new PageView<SettleOrderDetailBean>(queryParam.getPageSize()==0?BaseConst.pageSize:queryParam.getPageSize(), queryParam.getPage());
				QueryResult<SettleOrderDetailBean> qrSetOrderDetail = transPoolServiceImpl.queryOrderDetail(queryParam);
				pageView.setQueryResult(qrSetOrderDetail, queryParam.getPage()); 
				request.setAttribute("pageView", pageView);
				request.setAttribute("queryParam", queryParam);
				request.setAttribute("dataType", dataType);
				request.setAttribute("proFlag", proFlag);
			}else if("5".equals(dataType)){
				PageView<SettleStorageBean> pageView = new PageView<SettleStorageBean>(queryParam.getPageSize()==0?BaseConst.pageSize:queryParam.getPageSize(), queryParam.getPage());
				QueryResult<SettleStorageBean> qrSetStorage = transPoolServiceImpl.queryStorage(queryParam);
				pageView.setQueryResult(qrSetStorage, queryParam.getPage()); 
				request.setAttribute("pageView", pageView);
				request.setAttribute("queryParam", queryParam);
				request.setAttribute("dataType", dataType);
				request.setAttribute("proFlag", proFlag);
			}else{
				PageView<SettleUseBean> pageView = new PageView<SettleUseBean>(queryParam.getPageSize()==0?BaseConst.pageSize:queryParam.getPageSize(), queryParam.getPage());
				QueryResult<SettleUseBean> qrSetUsee = transPoolServiceImpl.queryUse(queryParam);
				pageView.setQueryResult(qrSetUsee, queryParam.getPage()); 
				request.setAttribute("pageView", pageView);
				request.setAttribute("queryParam", queryParam);
				request.setAttribute("dataType", dataType);
				request.setAttribute("proFlag", proFlag);
			}
		}catch(Exception e){
			logger.error(e);
			e.printStackTrace();
		}
		return "/lmis/trans_pool_message";
	}
	@RequestMapping("/detail_list.do")
	public String getDetailInfo(TransPoolQueryParam queryParam, HttpServletRequest request) throws Exception{
		try{
			//根据条件查询合同集合
			PageView<SettleDetailBean> pageView = new PageView<SettleDetailBean>(queryParam.getPageSize()==0?BaseConst.pageSize:queryParam.getPageSize(), queryParam.getPage());
			queryParam.setFirstResult(pageView.getFirstResult());
			queryParam.setMaxResult(pageView.getMaxresult());
			queryParam.setContractId(request.getParameter("contractId"));
			queryParam.setCost_center(request.getParameter("cost_center"));
			queryParam.setCreateDate(request.getParameter("createDate"));
			queryParam.setTransportNum(request.getParameter("transportNum"));
			queryParam.setOrderNum(request.getParameter("orderNum"));
			queryParam.setStartPlace(request.getParameter("startPlace"));
			queryParam.setEndPlace(request.getParameter("endPlace"));
			queryParam.setStartPlace(request.getParameter("start_place"));
			queryParam.setStoreName(request.getParameter("shop_name"));
			queryParam.setTransportCode(request.getParameter("transportCode"));
			queryParam.setStartOrederTime(spiltDateString_jt(request.getParameter("orederTime")).get("startDate"));
			queryParam.setEndOrederTime(spiltDateString_jt(request.getParameter("orederTime")).get("endDate"));
			queryParam.setStartTransportTime(spiltDateString_jt(request.getParameter("transportTime")).get("startDate"));
			queryParam.setEndTransportTime(spiltDateString_jt(request.getParameter("transportTime")).get("endDate"));
			QueryResult<SettleDetailBean> qr = transPoolDetailServiceImpl.findAllData(queryParam);
			pageView.setQueryResult(qr, queryParam.getPage()); 
			request.setAttribute("pageView", pageView);
			queryParam.setStartOrederTime(request.getParameter("orederTime"));
			queryParam.setStartTransportTime(request.getParameter("transportTime"));
			queryParam.setOpId(request.getParameter("opId"));
			queryParam.setClientid(request.getParameter("clientid"));
			queryParam.setYm(request.getParameter("ym"));
			request.setAttribute("queryParam", queryParam);
		}catch(Exception e){
			logger.error(e);
			e.printStackTrace();
		}
		return "/lmis/trans_pool_detail_list";
	}
	

	
	@RequestMapping("/list_gt.do")
	public String getPoolInfo_Gt(TransPoolQueryParam queryParam, HttpServletRequest request) throws Exception{
		
		try{
			//根据条件查询合同集合
			PageView<TransPoolBean> pageView = new PageView<TransPoolBean>(queryParam.getPageSize()==0?BaseConst.pageSize:queryParam.getPageSize(), queryParam.getPage());
			queryParam.setFirstResult(pageView.getFirstResult());
			queryParam.setMaxResult(pageView.getMaxresult());
			QueryResult<TransPoolBean> qr = transPoolServiceImpl.findAllDataForGt(queryParam);
			pageView.setQueryResult(qr, queryParam.getPage()); 
			request.setAttribute("pageView", pageView);
			request.setAttribute("queryParam", queryParam);
		}catch(Exception e){
			logger.error(e);
		}
		return "/lmis/mb_pool_list";
	}
	
	
	
	@RequestMapping("/detail_gt_list.do")
	public String getDetailInfoGt(TransPoolQueryParam queryParam, HttpServletRequest request) throws Exception{
		try{
			//根据条件查询合同集合
			PageView<SettleDetailBean> pageView = new PageView<SettleDetailBean>(queryParam.getPageSize()==0?BaseConst.pageSize:queryParam.getPageSize(), queryParam.getPage());
			queryParam.setFirstResult(pageView.getFirstResult());
			queryParam.setMaxResult(pageView.getMaxresult());
			queryParam.setContractId(request.getParameter("contractId"));
			queryParam.setCost_center(request.getParameter("cost_center"));
			queryParam.setCreateDate(request.getParameter("createDate"));
			queryParam.setTransportNum(request.getParameter("transportNum"));
			queryParam.setOrderNum(request.getParameter("orderNum"));
			queryParam.setStartPlace(request.getParameter("startPlace"));
			queryParam.setEndPlace(request.getParameter("endPlace"));
			queryParam.setStartPlace(request.getParameter("start_place"));
			queryParam.setStoreName(request.getParameter("shop_name"));
			QueryResult<SettleDetailBean> qr = transPoolDetailServiceImpl.findAllData(queryParam);
			pageView.setQueryResult(qr, queryParam.getPage()); 
			request.setAttribute("pageView", pageView);
			
			queryParam.setStartOrederTime(request.getParameter("orederTime"));
			queryParam.setStartTransportTime(request.getParameter("transportTime"));
			
			request.setAttribute("queryParam", queryParam);
		}catch(Exception e){
			logger.error(e);
			e.printStackTrace();
		}
		return "/lmis/trans_pool_detail_list";
	}
	@RequestMapping("/excelError_pool.do")
	public void excelError(TransPoolQueryParam queryParam, HttpServletRequest request,HttpServletResponse response){
		PrintWriter out = null;
		File res=new File("");
		Map<String,Object> main=new HashMap<String,Object>();
		Map<String,Object> detail=new HashMap<String,Object>();
		Map<String,Object> detailThree=new HashMap<String,Object>();
		Map<String,Object> detailFour=new HashMap<String,Object>();
		Map<String,Object> detailFive=new HashMap<String,Object>();
		Map<String,Object> detailSix=new HashMap<String,Object>();
		List<Map<String, Object>> listAll=new ArrayList();
		try{
			out = response.getWriter();
			Map<String, Object>param=getParamMap(request);
			List<?>list=transPoolDetailServiceImpl.getExclInvitation(param);
			List<?>list2=transPoolDetailServiceImpl.getExeclOperator(param);
			List<?>listOrder=transPoolDetailServiceImpl.getExeclOrder(param);
			List<?>listOrderDetail=transPoolDetailServiceImpl.getExeclOrderDetail(param);
			List<?>listStorage=transPoolDetailServiceImpl.getExeclStorage(param);
			List<?> listUse=transPoolDetailServiceImpl.getDetailUse(param);
			
			LinkedHashMap<String, String> CMap=new LinkedHashMap<String, String>();
			LinkedHashMap<String, String> CMap2=new LinkedHashMap<String, String>();
			LinkedHashMap<String, String> CMapOrder=new LinkedHashMap<String, String>();
			LinkedHashMap<String, String> CMapOrderDetail=new LinkedHashMap<String, String>();
			LinkedHashMap<String, String> CMapStorage=new LinkedHashMap<String, String>();
			LinkedHashMap<String, String> CMapUse=new LinkedHashMap<String, String>();

			CMap.put("store_name", "店铺名称");
			CMap.put("inbound_no", "PO单号");
			CMap.put("barcode", "条形码");
			CMap.put("bz_number", "宝尊编码");
			CMap.put("item_no", "商品编码");
			CMap.put("inbound_qty", "实际入库数量");
			CMap.put("purchase_price", "采购单价");
			CMap.put("inbound_money", "实际到货入库金额");
			CMap.put("pro_flag", "过程处理标记");
			CMap.put("pro_remark", "过程处理结果");
			CMap.put("error_count", "错误条数");

			CMap2.put("store_name", "店铺名称");
			CMap2.put("job_orderno", "作业单号");
			CMap2.put("job_type", "作业类型名称");
			CMap2.put("storaggelocation_code", "库位编码");
			CMap2.put("out_num", "出库数量");
			CMap2.put("item_number", "商品编码");
			CMap2.put("art_no", "货号");
			CMap2.put("operator", "操作员");
			CMap2.put("pro_flag", "过程处理标记");
			CMap2.put("pro_remark", "过程处理结果");
			CMap2.put("error_count", "错误条数");
			
			CMapOrder.put("store_code", "店铺代码");
			CMapOrder.put("store_name", "店铺名称");
			CMapOrder.put("warehouse_code", "所属仓库编码 逻辑仓库");
			CMapOrder.put("warehouse_name", "所属仓库名称 逻辑仓库");
			CMapOrder.put("transport_code", "快递代码（如SF YTO 等）");
			CMapOrder.put("transport_name", "快递名称");
			CMapOrder.put("delivery_order", "平台订单号");
			CMapOrder.put("epistatic_order", "上位系统订单号");
			CMapOrder.put("express_number", "运单号");
			CMapOrder.put("weight", "实际重量");
			CMapOrder.put("length", "长");
			CMapOrder.put("higth", "高");
			CMapOrder.put("volumn", "体积");
			CMapOrder.put("delivery_address", "始发地");
			CMapOrder.put("province", "省份");
			CMapOrder.put("city", "城市");
			CMapOrder.put("state", "区县");
			CMapOrder.put("shiptoname", "收件人");
			CMapOrder.put("address", "详细地址");
			CMapOrder.put("pro_remark", "过程处理结果");
			CMapOrder.put("error_count", "错误条数");

			CMapOrderDetail.put("express_number", "运单号");
			CMapOrderDetail.put("sku_number", "SKU条码");
			CMapOrderDetail.put("barcode", "条形码");
			CMapOrderDetail.put("item_name", "商品名称");
			CMapOrderDetail.put("extend_pro", "扩展属性");
			CMapOrderDetail.put("pro_remark", "过程处理结果");
			CMapOrderDetail.put("error_count", "错误条数");
			
			CMapStorage.put("store_code", "店铺编号");
			CMapStorage.put("store_name", "店铺名称");
			CMapStorage.put("warehouse_code", "仓库编号");
			CMapStorage.put("warehouse_name", "仓库名称");
			CMapStorage.put("system_warehouse", "系统仓 /逻辑仓");
			CMapStorage.put("item_type", "商品类型");
			CMapStorage.put("pro_remark", "过程处理结果");
			CMapStorage.put("error_count", "错误条数");
			
			CMapUse.put("store_code", "店铺代码");
			CMapUse.put("store_name", "店铺名称");
			CMapUse.put("sku_code", "耗材编码");
			CMapUse.put("sku_name", "耗材名称");
			CMapUse.put("pro_remark", "过程处理结果");
			CMapUse.put("error_count", "错误条数");
			
			main.put("title", CMap);
			main.put("sheetName", "错误物流运费结算汇总(jk_settle_invitation).xls");
			main.put("data", list);

			detail.put("title", CMap2);
			detail.put("sheetName", "错误物流运费结算明细汇总(jk_settle_operator).xls");
			detail.put("data", list2);

			
			detailThree.put("title", CMapOrder);
			detailThree.put("sheetName", "错误物流运结算明细汇总(jk_settle_order).xls");
			detailThree.put("data", listOrder);
			
			detailFour.put("title", CMapOrderDetail);
			detailFour.put("sheetName", "错误物流运结算明细汇总(jk_settle_order_detail).xls");
			detailFour.put("data", listOrderDetail);
			
			detailFive.put("title", CMapStorage);
			detailFive.put("sheetName", "错误物流运结算明细汇总(jk_settle_storage).xls");
			detailFive.put("data", listStorage);
			
			detailSix.put("title", CMapUse);
			detailSix.put("sheetName", "错误物流运费结算明细汇总(jk_settle_use).xls");
			detailSix.put("data", listUse);
			
			listAll.add(main);
			listAll.add(detail);
			listAll.add(detailThree);
			listAll.add(detailFour);
			listAll.add(detailFive);
			listAll.add(detailSix);
			res=CommonUtils.excelDownLoadData2(listAll,"错误物流运费结算汇总.xls");
		}catch(Exception e){
			logger.error(e);
			e.printStackTrace();
		}
		response.setContentType("text/plain");
		out.write(CommonUtils.getAllMessage("config", "COMMON_DOWNLOAD_MAP") + res.getName());
		out.flush();
	}
	@RequestMapping("/excel_pool.do")
	public void excel(TransPoolQueryParam queryParam, HttpServletRequest request,HttpServletResponse response){
		PrintWriter out = null;
		File res=new File("");
		Map<String,Object>main=new HashMap<String,Object>();
		Map<String,Object>detail=new HashMap<String,Object>();
		List<Map<String, Object>> listAll=new ArrayList();
		try{
			out = response.getWriter();
			Map<String, Object>param=getParamMap(request);
			List<?>list=transPoolDetailServiceImpl.getExclData(param);
			List<?>list2=transPoolDetailServiceImpl.getDetailData(param);
			LinkedHashMap<String, String>CMap=new LinkedHashMap<String, String>();
			LinkedHashMap<String, String>CMap2=new LinkedHashMap<String, String>();
			CMap.put("create_date", "结算周期");
			CMap.put("contract_no", "合同号");
			CMap.put("contract_name", "合同名称");
			CMap.put("warehouse", "仓库");
			CMap.put("cost_center", "成本中心");
			CMap.put("shop_name", "店铺");
			CMap.put("bill_num", "单量");
			CMap.put("carload_price", "整车运费");
			CMap.put("Lingdan_price", "零担运费");
			CMap.put("insurance_price", "保价费");
			CMap.put("upstairs_price", "上楼费");
			CMap.put("unloading_price", "卸货费");
			CMap.put("send_price", "送货费");
			CMap.put("take_price", "提货费");
			CMap.put("manager_price", "管理费");
			CMap.put("dispatch_price", "派送费");
			CMap.put("discount_price", "折扣费用");
			CMap.put("total_price", "合计");
			
			CMap2.put("transport_name", "物流商");
			CMap2.put("transport_type", "运输类型");
			CMap2.put("transport_direction", "运输方向");
			CMap2.put("bookbus_time", "订单时间");
			CMap2.put("transport_time", "运输时间");
			CMap2.put("expressno", "运单号");
			CMap2.put("store_name", "店铺名称");
			CMap2.put("orderno", "订单号");
			CMap2.put("originating_place", "起始地");
			CMap2.put("privince_name", "目的地省");
			CMap2.put("city_name", "目的地市");
			CMap2.put("delivery_address", "送货地址");
			CMap2.put("delivery_number", "件数");
			CMap2.put("box_number", "箱数");
			CMap2.put("real_weight", "实际重量");
			CMap2.put("volumn_cubic", "体积立方");
			CMap2.put("realweight_price", "重货单价");
			CMap2.put("volumn_price", "体积单价");
			CMap2.put("realweight_cost", "重货费用");
			CMap2.put("volumnweight_cost", "体积费用");
			CMap2.put("lower_price", "最低价格");
			CMap2.put("trans_cost", "运费合计");
			CMap2.put("delevery_charges", "送货费");
			CMap2.put("insurance_goods_price", "保价金额");
			CMap2.put("insurance_fee", "保价费");
			CMap2.put("loading_charges", "装卸费");
			CMap2.put("pickup_charges", "提货费");
			CMap2.put("other_charges", "其他费用");
			CMap2.put("total_cost", "合计");
			CMap2.put("remark", "备注");
			
			main.put("title", CMap);
			main.put("sheetName", "物流运费结算汇总.xls");
			main.put("data",list);
			
			detail.put("title",CMap2);
			detail.put("sheetName", "物流运费结算明细汇总.xls");
			detail.put("data", list2);
			
			listAll.add(main);
			listAll.add(detail);
			res=CommonUtils.excelDownLoadData2(listAll,"物流运费结算汇总.xls");
		}catch(Exception e){
			logger.error(e);
			e.printStackTrace();
		}
		out.write(CommonUtils.getAllMessage("config", "COMMON_DOWNLOAD_MAP") + res.getName());
		out.flush();
	}
	
	
	@RequestMapping("/excel_pool_detail.do")
	public void excel_pool_detail(TransPoolQueryParam queryParam, HttpServletRequest request,HttpServletResponse response){
		PrintWriter out = null;
		File res=new File("");
		try{
			out = response.getWriter();
			Map<String,Object>param=getParamMap(request);
			List<?>list=transPoolDetailServiceImpl.getDetailData(param);
			LinkedHashMap<String, String>CMap=new LinkedHashMap<String, String>();
			CMap.put("transport_name", "物流商");
			CMap.put("transport_type", "运输类型");
			CMap.put("transport_direction", "运输方向");
			CMap.put("bookbus_time", "订单时间");
			CMap.put("transport_time", "运输时间");
			CMap.put("expressno", "运单号");
			CMap.put("store_name", "店铺名称");
			CMap.put("orderno", "订单号");
			CMap.put("originating_place", "起始地");
			CMap.put("privince_name", "目的地省");
			CMap.put("city_name", "目的地市");
			CMap.put("delivery_address", "送货地址");
			CMap.put("delivery_number", "件数");
			CMap.put("box_number", "箱数");
			CMap.put("real_weight", "实际重量");
			CMap.put("volumn_cubic", "体积立方");
			CMap.put("realweight_price", "重货单价");
			CMap.put("realweight_cost", "重货费用");
			CMap.put("volumnweight_cost", "体积费用");
			CMap.put("lower_price", "最低价格");
			CMap.put("trans_cost", "运费合计");
			CMap.put("delevery_charges", "送货费");
			CMap.put("insurance_goods_price", "保价金额");
			CMap.put("insurance_fee", "保价费");
			CMap.put("loading_charges", "装卸费");
			CMap.put("pickup_charges", "提货费");
			CMap.put("other_charges", "其他费用");
			CMap.put("total_cost", "合计");
			CMap.put("remark", "备注");
			res=BigExcelExport.excelDownLoadDatab(list,CMap,"物流运费结算明细汇总.xls");
			
		} catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
			
		}
		out.write(CommonUtils.getAllMessage("config", "COMMON_DOWNLOAD_MAP") + res.getName());
		out.flush();
		
	}
	
}
