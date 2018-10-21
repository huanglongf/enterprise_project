package com.bt.lmis.controller;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.bt.lmis.code.BaseController;
import com.bt.lmis.model.BtcobopbillTable;
import com.bt.lmis.model.Employee;
import com.bt.lmis.model.OperationSettlementRule;
import com.bt.lmis.service.BtcobopbillTableService;
import com.bt.lmis.service.EmployeeService;
import com.bt.lmis.service.OperationSettlementRuleService;
import com.bt.utils.IntervalValidationUtil;
import com.bt.utils.SessionUtils;

/** 
* @ClassName: OperatingCostController 
* @Description: TODO(操作费控制器) 
* @author Yuriy.Jiang
* @date 2016年7月4日 下午3:00:22 
*  
*/
@Controller
@RequestMapping("/control/operatingCostController")
public class OperatingCostController extends BaseController{

	private static final Logger logger = Logger.getLogger(OperatingCostController.class);

	/**
	 * 操作费结算规则表服务类
	 */
	@Resource(name = "operationSettlementRuleServiceImpl")
	private OperationSettlementRuleService<OperationSettlementRule> operationSettlementRuleService;
	
	/**
	 *登录服务类
	 */
	@Resource(name = "employeeServiceImpl")
	private EmployeeService<Employee> employeeServiceImpl;

	/**
	 * B2C出库操作费按单出库服务类
	 */
	@Resource(name = "btcobopbillTableServiceImpl")
	private BtcobopbillTableService<BtcobopbillTable> btcobopbillTableService;
	
	/** 
	* @Title: saveMain 
	* @Description: TODO(操作费页面保存操作) 
	* @param @param req
	* @param @param res    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	@RequestMapping("/saveMain")
	public void saveMain(HttpServletRequest req,HttpServletResponse res){
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		Integer msg = 401;
		boolean flag = true;
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Employee iflogin = SessionUtils.getEMP(req);
			out = res.getWriter();
			//合同ID
			String contract_id = req.getParameter("id");
			String allzk = req.getParameter("allzk");
			if(null!=allzk && !allzk.equals("")){
				map.put("allzk", allzk);
			}else{
				map.put("allzk", "1");
			}
			map.put("contract_id", contract_id);
			//操作费结算方式 ID＝CZF[0固定费用结算1按销售额百分比结算2.按实际发生费用结算]
			String osr_setttle_method = req.getParameter("CZF");
			//结算方式
			map.put("osr_setttle_method", osr_setttle_method);
			map.put("create_user", iflogin.getName());
			map.put("update_user", iflogin.getName());
			if(osr_setttle_method.equals("0")){
				//固定费用结算
				String osr_fixed_type = req.getParameter("GD");							//固定费用类型 1无阶梯 2超过部分
				map.put("osr_fixed_type", osr_fixed_type);
				if(osr_fixed_type.equals("1")){
					//无阶梯
					String osr_fixed_price = req.getParameter("osr_fixed_price");			//无阶梯固定费用单价
					String osr_fixed_price_unit = req.getParameter("osr_fixed_price_unit");	//无阶梯固定费用单价单位
					
					map.put("osr_fixed_price", osr_fixed_price);
					map.put("osr_fixed_price_unit", osr_fixed_price_unit);
					msg = 401;
					if((null!=map.get("osr_fixed_type") && !map.get("osr_fixed_type").equals("")) &&
							(null!=map.get("osr_fixed_price") && !map.get("osr_fixed_price").equals("")) &&
							(null!=map.get("osr_fixed_price_unit") && !map.get("osr_fixed_price_unit").equals(""))){
						msg=360;
					}else{
						msg=201;
						flag=false;
					}
				}else{
					//超过部分
					String osr_fixed_price = req.getParameter("osr_fixed_price1");				//超过部分阶梯固定费用单价
					String osr_fixed_price_unit = req.getParameter("osr_fixed_price_unit1");	//超过部分阶梯固定费用单价单位
					String osr_fixed_exceed_tableid = null;
					List<OperationSettlementRule> list = operationSettlementRuleService.findByCBID(contract_id);
					if (list.size()!=0 && null!=list.get(0).getOsr_fixed_exceed_tableid() && !list.get(0).getOsr_fixed_exceed_tableid().equals("")) {
						osr_fixed_exceed_tableid = list.get(0).getOsr_fixed_exceed_tableid();	//超过部分阶梯表ID Update
					}else{
						osr_fixed_exceed_tableid = UUID.randomUUID().toString();				//超过部分阶梯表ID Insert
					}
					map.put("osr_fixed_price", osr_fixed_price);								//固定费用
					map.put("osr_fixed_price_unit", osr_fixed_price_unit);						//固定费用单位
					map.put("osr_fixed_exceed_tableid", osr_fixed_exceed_tableid);				//区间表集合ID
					//未组装区间参数
					String osr_mark1 = req.getParameter("osr_mark1");					//条件1
					String osr_mark2 = req.getParameter("osr_mark2");					//条件2
					String osr_mark3 = req.getParameter("osr_mark3");					//条件3
					String osr_mark4 = req.getParameter("osr_mark4");					//参数1
					String osr_mark5 = req.getParameter("osr_mark5");					//参数2
					//区间
					String tosf_section = getSection(osr_mark1, osr_mark2, osr_mark3, osr_mark4, osr_mark5);
					if(null!=tosf_section){
						String tosf_tab_id = osr_fixed_exceed_tableid;
						String tosf_price = req.getParameter("tosf_price");
						String tosf_price_unit = req.getParameter("tosf_price_unit");
						Map<String, Object> map2 = new HashMap<String, Object>();
						map2.put("tosf_section", tosf_section);
						map2.put("tosf_tab_id", tosf_tab_id);
						map2.put("tosf_price", tosf_price);
						map2.put("tosf_price_unit", tosf_price_unit);
						msg = 401;
						if((null!=map.get("osr_fixed_type") && !map.get("osr_fixed_type").equals("")) &&
								(null!=map2.get("tosf_section") && !map2.get("tosf_section").equals("")) &&
								(null!=map2.get("tosf_tab_id") && !map2.get("tosf_tab_id").equals("")) &&
								(null!=map2.get("tosf_price") && !map2.get("tosf_price").equals(""))){
							operationSettlementRuleService.saveAupdateOSF(map, map2);
							msg=360;
						}else{
							msg=201;
						}
					}
				}
			}else if(osr_setttle_method.equals("1")){
				//按销售额百分比结算
				String osr_sales_percent = req.getParameter("osr_sales_percent");
				map.put("osr_sales_percent", osr_sales_percent);
				String osr_tax_point = req.getParameter("osr_tax_point");
				map.put("osr_tax_point", osr_tax_point);
				msg = 401;
				if((null!=map.get("osr_sales_percent") && !map.get("osr_sales_percent").equals(""))){
					msg=360;
				}else{
					msg=201;
					flag=false;
				}
				msg=360;
			}else if(osr_setttle_method.equals("2")){
				//按实际发生费用结算
				//1.入库操作费
				String osr_ibop_fee = req.getParameter("RKCZF");
				map.put("osr_ibop_fee", osr_ibop_fee);
				if(null!=osr_ibop_fee && !osr_ibop_fee.equals("")){
					if(osr_ibop_fee.equals("1")){
						//按SKU类型收取
						String osr_ibop_skuprice = req.getParameter("osr_ibop_skuprice");				//SKU单价
						String osr_ibop_skuprice_unit = req.getParameter("osr_ibop_skuprice_unit");		//SKU单价单位
						String osr_ibop_itemprice = req.getParameter("osr_ibop_itemprice");				//商品单价
						String osr_ibop_itemprice_unit = req.getParameter("osr_ibop_itemprice_unit");	//商品单价单位
						map.put("osr_ibop_skuprice", osr_ibop_skuprice);
						map.put("osr_ibop_skuprice_unit", osr_ibop_skuprice_unit);
						map.put("osr_ibop_itemprice", osr_ibop_itemprice);
						map.put("osr_ibop_itemprice_unit", osr_ibop_itemprice_unit);
						msg = 401;
						if((null!=map.get("osr_ibop_skuprice") && !map.get("osr_ibop_skuprice").equals("")) &&
								(null!=map.get("osr_ibop_skuprice_unit") && !map.get("osr_ibop_skuprice_unit").equals("")) &&
								(null!=map.get("osr_ibop_itemprice") && !map.get("osr_ibop_itemprice").equals("")) &&
								(null!=map.get("osr_ibop_itemprice_unit") && !map.get("osr_ibop_itemprice_unit").equals(""))){
							msg=360;
						}else{
							msg=201;
							flag=false;
						}
					}else if(osr_ibop_fee.equals("2")){
						//按入库数量收取
						String osr_ibop_qtyprice = req.getParameter("osr_ibop_qtyprice1");			//收货上架数量单价
						String osr_ibop_qtyprice_unit = req.getParameter("osr_ibop_qtyprice_unit");	//收货上架数量单价单位
						map.put("osr_ibop_qtyprice", osr_ibop_qtyprice);
						map.put("osr_ibop_qtyprice_unit", osr_ibop_qtyprice_unit);
						msg = 401;
						if((null!=map.get("osr_ibop_qtyprice") && !map.get("osr_ibop_qtyprice").equals("")) &&
								(null!=map.get("osr_ibop_qtyprice_unit") && !map.get("osr_ibop_qtyprice_unit").equals(""))){
							msg=360;
						}else{
							msg=201;
							flag=false;
						}
					}
				}
				//2.B2C出库操作费
				String osr_btcobop_fee = req.getParameter("B2CCKCZF");
				map.put("osr_btcobop_fee", osr_btcobop_fee);
				if (null!=osr_btcobop_fee && !osr_btcobop_fee.equals("")) {
					if (osr_btcobop_fee.equals("1")) {
						//2.1 按件出库
						String osr_btcobop_numfee = req.getParameter("B2CCKCZFJT");
						map.put("osr_btcobop_numfee", osr_btcobop_numfee);
						if (null!=osr_btcobop_numfee && !osr_btcobop_numfee.equals("")) {
							if (osr_btcobop_numfee.equals("1")) {
								//无阶梯
								String osr_btcobop_numprice = req.getParameter("osr_btcobop_numprice");
								String osr_btcobop_numdc = req.getParameter("osr_btcobop_numdc");
								map.put("osr_btcobop_numprice", osr_btcobop_numprice);
								map.put("osr_btcobop_numdc", osr_btcobop_numdc);
								msg = 401;
								if((null!=map.get("osr_btcobop_numprice") && !map.get("osr_btcobop_numprice").equals("")) &&
										(null!=map.get("osr_btcobop_numdc") && !map.get("osr_btcobop_numdc").equals(""))){
									msg=360;
								}else{
									msg=201;
									flag=false;
								}
							}else if (osr_btcobop_numfee.equals("2")) {
								//超过部分阶梯价
							}else if (osr_btcobop_numfee.equals("3")) {
								//总件数阶梯价
							}
						}
					}else if (osr_btcobop_fee.equals("2")) {
						//2.2 按单出库
						String osr_tobtb = req.getParameter("osr_tobtb");
						map.put("osr_tobtb", 0);
						if (null!=osr_tobtb && osr_tobtb.equals("on")) {
							map.put("osr_tobtb", 1);
							String osr_btcobopbill_tobtb_js = req.getParameter("osr_btcobopbill_tobtb_js");
							map.put("osr_btcobopbill_tobtb_js", osr_btcobopbill_tobtb_js);
							msg = 401;
							if((null!=map.get("osr_btcobopbill_tobtb_js") && !map.get("osr_btcobopbill_tobtb_js").equals(""))){
								msg=360;
							}else{
								msg=201;
								flag=false;
							}
						}
					}else if (osr_btcobop_fee.equals("3")) {
						//2.3 按单出库
						String osr_btcobop_numfees = req.getParameter("B2CCKCZFJTs");
						map.put("osr_btcobop_numfees", osr_btcobop_numfees);
						if (null!=osr_btcobop_numfees && !osr_btcobop_numfees.equals("")) {
							if (osr_btcobop_numfees.equals("1")) {
								//无阶梯
								String osr_btcobop_numprices = req.getParameter("osr_btcobop_numprices");
								String osr_btcobop_numdcs = req.getParameter("osr_btcobop_numdcs");
								map.put("osr_btcobop_numprices", osr_btcobop_numprices);
								map.put("osr_btcobop_numdcs", osr_btcobop_numdcs);
								msg = 401;
								if((null!=map.get("osr_btcobop_numprices") && !map.get("osr_btcobop_numprices").equals("")) &&
										(null!=map.get("osr_btcobop_numdcs") && !map.get("osr_btcobop_numdcs").equals(""))){
									msg=360;
								}else{
									msg=201;
									flag=false;
								}
							}else if (osr_btcobop_numfees.equals("2")) {
								//超过部分阶梯价
							}else if (osr_btcobop_numfees.equals("3")) {
								//总件数阶梯价
							}
						}
					}
				}
				//3.B2B出库操作费
				String osr_btbobop_fee = req.getParameter("B2BCKCZF");
				map.put("osr_btbobop_fee", osr_btbobop_fee);
				if (null!=osr_btbobop_fee && !osr_btbobop_fee.equals("")) {
					if (osr_btbobop_fee.equals("1")) {
						//3.1按件出库
						String osr_btbobop_billprice = req.getParameter("osr_btbobop_billprice");
						String osr_btbobop_billprice_unit = req.getParameter("osr_btbobop_billprice_unit");
						map.put("osr_btbobop_billprice", osr_btbobop_billprice);
						map.put("osr_btbobop_billprice_unit", osr_btbobop_billprice_unit);
						msg = 401;
						if((null!=map.get("osr_btbobop_billprice") && !map.get("osr_btbobop_billprice").equals("")) &&
								(null!=map.get("osr_btbobop_billprice_unit") && !map.get("osr_btbobop_billprice_unit").equals(""))){
							
							msg=360;
						}else{
							msg=201;
							flag=false;
						}
					}else if (osr_btbobop_fee.equals("2")) {
						//3.2按SKU类型收取
						String osr_btbobop_skuprice = req.getParameter("osr_btbobop_skuprice");
						String osr_btbobop_skuprice_unit = req.getParameter("osr_btbobop_skuprice_unit");
						String osr_btbobop_itemprice = req.getParameter("osr_btbobop_itemprice");
						String osr_btbobop_itemprice_unit = req.getParameter("osr_btbobop_itemprice_unit");
						map.put("osr_btbobop_skuprice", osr_btbobop_skuprice);
						map.put("osr_btbobop_skuprice_unit", osr_btbobop_skuprice_unit);
						map.put("osr_btbobop_itemprice", osr_btbobop_itemprice);
						map.put("osr_btbobop_itemprice_unit", osr_btbobop_itemprice_unit);
						msg = 401;
						if((null!=map.get("osr_btbobop_skuprice") && !map.get("osr_btbobop_skuprice").equals("")) &&
								(null!=map.get("osr_btbobop_skuprice_unit") && !map.get("osr_btbobop_skuprice_unit").equals(""))&&
								(null!=map.get("osr_btbobop_itemprice") && !map.get("osr_btbobop_itemprice").equals(""))&&
								(null!=map.get("osr_btbobop_itemprice_unit") && !map.get("osr_btbobop_itemprice_unit").equals(""))){
							
							msg=360;
						}else{
							msg=201;
							flag=false;
						}
					}
				}
				//4.B2B退仓操作费
				String osr_btbrtop_fee = req.getParameter("B2BTCCZF");
				map.put("osr_btbrtop_fee", osr_btbrtop_fee);
				if (null!=osr_btbrtop_fee && !osr_btbrtop_fee.equals("")) {
					if(osr_btbrtop_fee.equals("1")){
						//4.1按SKU计算
						String osr_btbrtop_sku_skuprice = req.getParameter("osr_btbrtop_sku_skuprice");
						String osr_btbrtop_sku_skuprice_unit = req.getParameter("osr_btbrtop_sku_skuprice_unit");
						String osr_btbrtop_sku_billprice = req.getParameter("osr_btbrtop_sku_billprice");
						String osr_btbrtop_sku_billprice_unit = req.getParameter("osr_btbrtop_sku_billprice_unit");
						map.put("osr_btbrtop_sku_skuprice", osr_btbrtop_sku_skuprice);
						map.put("osr_btbrtop_sku_skuprice_unit", osr_btbrtop_sku_skuprice_unit);
						map.put("osr_btbrtop_sku_billprice", osr_btbrtop_sku_billprice);
						map.put("osr_btbrtop_sku_billprice_unit", osr_btbrtop_sku_billprice_unit);
						msg = 401;
						if((null!=map.get("osr_btbrtop_sku_skuprice") && !map.get("osr_btbrtop_sku_skuprice").equals("")) &&
								(null!=map.get("osr_btbrtop_sku_skuprice_unit") && !map.get("osr_btbrtop_sku_skuprice_unit").equals(""))&&
								(null!=map.get("osr_btbrtop_sku_billprice") && !map.get("osr_btbrtop_sku_billprice").equals(""))&&
								(null!=map.get("osr_btbrtop_sku_billprice_unit") && !map.get("osr_btbrtop_sku_billprice_unit").equals(""))){
							
							msg=360;
						}else{
							msg=201;
							flag=false;
						}
					}else if(osr_btbrtop_fee.equals("2")){
						//4.2按件数计算
						String osr_btbrtop_bill_billprice = req.getParameter("osr_btbrtop_bill_billprice");
						String osr_btbrtop_bill_billprice_unit = req.getParameter("osr_btbrtop_bill_billprice_unit");
						map.put("osr_btbrtop_bill_billprice", osr_btbrtop_bill_billprice);
						map.put("osr_btbrtop_bill_billprice_unit", osr_btbrtop_bill_billprice_unit);
						msg = 401;
						if((null!=map.get("osr_btbrtop_bill_billprice") && !map.get("osr_btbrtop_bill_billprice").equals("")) &&
								(null!=map.get("osr_btbrtop_bill_billprice_unit") && !map.get("osr_btbrtop_bill_billprice_unit").equals(""))){
							
							msg=360;
						}else{
							msg=201;
							flag=false;
						}
					}
				}
				//5.B2C退换货入库费
				String osr_btcrtib_fee = req.getParameter("B2CTHHRKF");
				map.put("osr_btcrtib_fee", osr_btcrtib_fee);
				if (null!=osr_btcrtib_fee && !osr_btcrtib_fee.equals("")) {
					if(osr_btcrtib_fee.equals("1")){
						//5.1按单计算
						String osr_btcrtib_bill_billprice = req.getParameter("osr_btcrtib_bill_billprice");
						String osr_btcrtib_bill_billprice_unit = req.getParameter("osr_btcrtib_bill_billprice_unit");
						map.put("osr_btcrtib_bill_billprice", osr_btcrtib_bill_billprice);
						map.put("osr_btcrtib_bill_billprice_unit", osr_btcrtib_bill_billprice_unit);
						msg = 401;
						if((null!=map.get("osr_btcrtib_bill_billprice") && !map.get("osr_btcrtib_bill_billprice").equals("")) &&
								(null!=map.get("osr_btcrtib_bill_billprice_unit") && !map.get("osr_btcrtib_bill_billprice_unit").equals(""))){
							
							msg=360;
						}else{
							msg=201;
							flag=false;
						}
					}else if(osr_btcrtib_fee.equals("2")){
						//5.2按件计算
						String osr_btcrtib_piece = req.getParameter("B2CTHHRKFJT");
						map.put("osr_btcrtib_piece", osr_btcrtib_piece);
						//B2C退换货入库费按件计算阶梯模式
						if(null!=osr_btcrtib_piece && !osr_btcrtib_piece.equals("")){
							if (osr_btcrtib_piece.equals("1")) {
								//5.2.1 无阶梯
								String osr_btcrtib_piece_pieceprice = req.getParameter("osr_btcrtib_piece_pieceprice");
								String osr_btcrtib_piece_pieceprice_unit = req.getParameter("osr_btcrtib_piece_pieceprice_unit");
								map.put("osr_btcrtib_piece_pieceprice", osr_btcrtib_piece_pieceprice);
								map.put("osr_btcrtib_piece_pieceprice_unit", osr_btcrtib_piece_pieceprice_unit);
								msg = 401;
								if((null!=map.get("osr_btcrtib_piece_pieceprice") && !map.get("osr_btcrtib_piece_pieceprice").equals("")) &&
										(null!=map.get("osr_btcrtib_piece_pieceprice_unit") && !map.get("osr_btcrtib_piece_pieceprice_unit").equals(""))){
									
									msg=360;
								}else{
									msg=201;
									flag=false;
								}
							}else if (osr_btcrtib_piece.equals("2")) {
//								String osr_btcrti_tableid = null;
//								List<OperationSettlementRule> list = operationSettlementRuleService.findByCBID(contract_id);
//								if (list.size()!=0 && null!=list.get(0).getOsr_btcrti_tableid()) {
//									osr_btcrti_tableid = list.get(0).getOsr_fixed_exceed_tableid();	//B2C退换货按件阶梯表格id(tb_btcrti_table) Update
//								}else{
//									osr_btcrti_tableid = UUID.randomUUID().toString();				//B2C退换货按件阶梯表格id(tb_btcrti_table) Insert
//								}
//								map.put("osr_btcrti_tableid", osr_btcrti_tableid);
//								//5.2.2 有阶梯
//								String btcrti_mark1 = req.getParameter("btcrti_mark1");					//条件1
//								String btcrti_mark2 = req.getParameter("btcrti_mark2");					//条件2
//								String btcrti_mark3 = req.getParameter("btcrti_mark3");					//条件3
//								String btcrti_mark4 = req.getParameter("btcrti_mark4");					//参数1
//								String btcrti_mark5 = req.getParameter("btcrti_mark5");					//参数2
//								//区间
//								String btcrti_interval = getSection(btcrti_mark1, btcrti_mark2, btcrti_mark3, btcrti_mark4, btcrti_mark5);
//								if(null!=btcrti_interval){
//									//区间参数 合法
//									String btcrt_price = req.getParameter("btcrt_price");
//									String btcrt_price_unit = req.getParameter("btcrt_price_unit");
//									Map<String, Object> map2 = new HashMap<String, Object>();
//									map2.put("btcrti_interval", btcrti_interval);
//									map2.put("btcrt_price", btcrt_price);
//									map2.put("btcrt_price_unit", btcrt_price_unit);
//									map2.put("btcrt_id", osr_btcrti_tableid);
//									msg = 401;
//									if((null!=map2.get("btcrti_interval") && !map2.get("btcrti_interval").equals("")) &&
//											(null!=map2.get("btcrt_price") && !map2.get("btcrt_price").equals("")) &&
//											(null!=map2.get("btcrt_price_unit") && !map2.get("btcrt_price_unit").equals(""))){
//										 //operationSettlementRuleService.saveAupdateTBT(map, map2);
//										msg=360;
//									}else{
//										msg=201;
//									}
//								}
							}
						}
					}
				}
				//6.增值服务费
			}
			if(flag){
				operationSettlementRuleService.saveAupdateMain(map);
			}
		} catch(Exception e){
			logger.error(e);
			msg=400;
		}
		out.write(returnFunction(msg));
		out.flush();
		out.close();
	}

	/** 
	* @Title: showZZFWFJT 
	* @Description: TODO(保存) 
	* @param @param req
	* @param @param res    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	@RequestMapping("/showZZFWFJT")
	public void showZZFWFJT(HttpServletRequest req,HttpServletResponse res){
		JSONObject json = new JSONObject();
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		json.put("status", "n");
		json.put("info", "操作失败!");
		try {
			out = res.getWriter();
			String tbid = req.getParameter("tbid");
			if(null!=tbid && !tbid.equals("")){
				List<OperationSettlementRule> osrlist = operationSettlementRuleService.findByCBID(tbid);
				List<Map<String, Object>>  list = operationSettlementRuleService.queryZZFWFList(osrlist.size()>=1?osrlist.get(0).getOsr_zzfwf_detail():null);
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
	
	@RequestMapping("/saveZZFWF")
	public void saveZZFWF(HttpServletRequest req,HttpServletResponse res){
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		Integer msg = 360;
		boolean flag = true;
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Employee iflogin = SessionUtils.getEMP(req);
			out = res.getWriter();
			//合同ID
			String contract_id = req.getParameter("id");
			map.put("contract_id", contract_id);
			//操作费结算方式 ID＝CZF[0固定费用结算1按销售额百分比结算2.按实际发生费用结算]
			String osr_setttle_method = req.getParameter("CZF");
			//结算方式
			map.put("osr_setttle_method", osr_setttle_method);
			map.put("create_user", iflogin.getName());
			map.put("update_user", iflogin.getName());
			
			String osr_addservice_fee = req.getParameter("osr_addservice_fee");
			map.put("osr_addservice_fee", 0);
			if(null!=osr_addservice_fee && osr_addservice_fee.equals("on")){
				map.put("osr_addservice_fee", 1);
				//QC费用
				String osr_qc_fee = req.getParameter("osr_qc_fee");
				map.put("osr_qc_fee", 0);
				if (null!=osr_qc_fee && osr_qc_fee.equals("on")) {
					map.put("osr_qc_fee", 1);
					String osr_qc_pieceprice = req.getParameter("osr_qc_pieceprice");
					map.put("osr_qc_pieceprice", osr_qc_pieceprice);
					String osr_qc_pieceprice_unit = req.getParameter("osr_qc_pieceprice_unit");
					map.put("osr_qc_pieceprice_unit", osr_qc_pieceprice_unit);
				}
				//放赠品费用
				String osr_gift_fee = req.getParameter("osr_gift_fee");
				map.put("osr_gift_fee", 0);
				if (null!=osr_gift_fee && osr_gift_fee.equals("on")) {
					map.put("osr_gift_fee", 1);
					String osr_gift_billprice = req.getParameter("osr_gift_billprice");
					map.put("osr_gift_billprice", osr_gift_billprice);
					String osr_gift_billprice_unit = req.getParameter("osr_gift_billprice_unit");
					map.put("osr_gift_billprice_unit", osr_gift_billprice_unit);
				}
				//测量尺寸
				String osr_mesurement = req.getParameter("osr_mesurement");
				map.put("osr_mesurement", 0);
				if (null!=osr_mesurement && osr_mesurement.equals("on")) {
					map.put("osr_mesurement", 1);
					String osr_mesurement_price = req.getParameter("osr_mesurement_price");
					map.put("osr_mesurement_price", osr_mesurement_price);
					String osr_mesurement_price_unit = req.getParameter("osr_mesurement_price_unit");
					map.put("osr_mesurement_price_unit", osr_mesurement_price_unit);
				}
				//代贴防伪码
				String osr_security_code = req.getParameter("osr_security_code");
				map.put("osr_security_code", 0);
				if(null!=osr_security_code && osr_security_code.equals("on")){
					map.put("osr_security_code", 1);
					String osr_security_code_price = req.getParameter("osr_security_code_price");
					map.put("osr_security_code_price", osr_security_code_price);
					String osr_security_code_price_unit = req.getParameter("osr_security_code_price_unit");
					map.put("osr_security_code_price_unit", osr_security_code_price_unit);
				}
				//代贴条码
				String osr_itemnum = req.getParameter("osr_itemnum");
				map.put("osr_itemnum", 0);
				if(null!=osr_itemnum && osr_itemnum.equals("on")){
					map.put("osr_itemnum", 1);
					String osr_itemnum_price = req.getParameter("osr_itemnum_price");
					String osr_itemnum_price_unit = req.getParameter("osr_itemnum_price_unit");
					map.put("osr_itemnum_price", osr_itemnum_price);
					map.put("osr_itemnum_price_unit", osr_itemnum_price_unit);
				}
				//吊牌拍照
				String osr_drop_photo = req.getParameter("osr_drop_photo");
				map.put("osr_drop_photo", 0);
				if (null!=osr_drop_photo && osr_drop_photo.equals("on")) {
					map.put("osr_drop_photo", 1);
					String osr_drop_photo_price = req.getParameter("osr_drop_photo_price");
					String osr_drop_photo_price_unit = req.getParameter("osr_drop_photo_price_unit");
					map.put("osr_drop_photo_price", osr_drop_photo_price);
					map.put("osr_drop_photo_price_unit", osr_drop_photo_price_unit);
				}
				//短信服务费
				String osr_message  = req.getParameter("osr_message");
				map.put("osr_message", 0);
				if (null!=osr_message && osr_message.equals("on")) {
					map.put("osr_message", 1);
					String osr_message_price = req.getParameter("osr_message_price");
					map.put("osr_message_price", osr_message_price);
					String osr_message_price_unit = req.getParameter("osr_message_price_unit");
					map.put("osr_message_price_unit", osr_message_price_unit);
				}
				//额外税费及管理费税
				String osr_extra_fee = req.getParameter("osr_extra_fee");
				map.put("osr_extra_fee", 0);
				if(null!=osr_extra_fee && osr_extra_fee.equals("on")){
					map.put("osr_extra_fee", 1);
					String osr_extra_fee_price = req.getParameter("osr_extra_fee_price");
					map.put("osr_extra_fee_price", osr_extra_fee_price);
					String osr_extra_fee_price_unit = req.getParameter("osr_extra_fee_price_unit");
					map.put("osr_extra_fee_price_unit", osr_extra_fee_price_unit);
				}
				//更改包装
				String osr_change_package = req.getParameter("osr_change_package");
				map.put("osr_change_package", 0);
				if (null!=osr_change_package && osr_change_package.equals("on")) {
					map.put("osr_change_package", 1);
					String osr_change_package_price = req.getParameter("osr_change_package_price");
					String osr_change_package_price_unit = req.getParameter("osr_change_package_price_unit");
					map.put("osr_change_package_price", osr_change_package_price);
					map.put("osr_change_package_price_unit", osr_change_package_price_unit);
				}
				//非工作时间作业
				String osr_notworkingtime = req.getParameter("osr_notworkingtime");
				map.put("osr_notworkingtime", 0);
				if(null!=osr_notworkingtime && osr_notworkingtime.equals("on")){
					map.put("osr_notworkingtime", 1);
					String osr_notworkingtime_price = req.getParameter("osr_notworkingtime_price");
					map.put("osr_notworkingtime_price", osr_notworkingtime_price);
					String osr_notworkingtime_price_unit = req.getParameter("osr_notworkingtime_price_unit");
					map.put("osr_notworkingtime_price_unit", osr_notworkingtime_price_unit);
				}
				//货票同行
				String osr_waybillpeer = req.getParameter("osr_waybillpeer");
				map.put("osr_waybillpeer", 0);
				if (null!=osr_waybillpeer && osr_waybillpeer .equals("on")) {
					map.put("osr_waybillpeer", 1);
					String osr_waybillpeer_price = req.getParameter("osr_waybillpeer_price");
					map.put("osr_waybillpeer_price", osr_waybillpeer_price);
					String osr_waybillpeer_price_unit = req.getParameter("osr_waybillpeer_price_unit");
					map.put("osr_waybillpeer_price_unit", osr_waybillpeer_price_unit);
				}
				//紧急收货
				String osr_emergency_receipt = req.getParameter("osr_emergency_receipt");
				map.put("osr_emergency_receipt", 0);
				if (null!=osr_emergency_receipt && osr_emergency_receipt.equals("on")) {
					map.put("osr_emergency_receipt", 1);
					String osr_emergency_receipt_price = req.getParameter("osr_emergency_receipt_price");
					map.put("osr_emergency_receipt_price", osr_emergency_receipt_price);
					String osr_emergency_receipt_price_unit = req.getParameter("osr_emergency_receipt_price_unit");
					map.put("osr_emergency_receipt_price_unit", osr_emergency_receipt_price_unit);
				}
				//取消订单拦截
				String osr_cancelorder = req.getParameter("osr_cancelorder");
				map.put("osr_cancelorder", 0);
				if(null!=osr_cancelorder && osr_cancelorder .equals("on")){
					map.put("osr_cancelorder", 1);
					String osr_cancelorder_price = req.getParameter("osr_cancelorder_price");
					map.put("osr_cancelorder_price", osr_cancelorder_price);
					String osr_cancelorder_price_unit = req.getParameter("osr_cancelorder_price_unit");
					map.put("osr_cancelorder_price_unit", osr_cancelorder_price_unit);
				}
				//条码制作人工费
				String osr_makebarfee = req.getParameter("osr_makebarfee");
				map.put("osr_makebarfee", 0);
				if(null!=osr_makebarfee && osr_makebarfee .equals("on")){
					map.put("osr_makebarfee", 1);
					String osr_makebarfee_price = req.getParameter("osr_makebarfee_price");
					map.put("osr_makebarfee_price", osr_makebarfee_price);
					String osr_makebarfee_price_unit = req.getParameter("osr_makebarfee_price_unit");
					map.put("osr_makebarfee_price_unit", osr_makebarfee_price_unit);
				}
				//卸货费
				String osr_landfee = req.getParameter("osr_landfee");
				map.put("osr_landfee", 0);
				if(null!=osr_landfee && osr_landfee.equals("on")){
					map.put("osr_landfee", 1);
					String osr_landfee_price = req.getParameter("osr_landfee_price");
					map.put("osr_landfee_price", osr_landfee_price);
					String osr_landfee_price_util = req.getParameter("osr_landfee_price_util");
					map.put("osr_landfee_price_util", osr_landfee_price_util);
				}
			}
			if(flag){
				operationSettlementRuleService.saveAupdateMain(map);
			}
		} catch(Exception e){
			logger.error(e);
			msg=400;
		}
		out.write(returnFunction(msg));
		out.flush();
		out.close();
	}
	
	/** 
	* @Title: get_show_tab 
	* @Description: TODO(显示Tab1) 
	* @param @param req
	* @param @param res    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	@RequestMapping("/get_show_tab")
	public void get_show_tab(HttpServletRequest req,HttpServletResponse res){
		JSONObject json = new JSONObject();
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		json.put("status", "n");
		json.put("info", "操作失败!");
		try {
			out = res.getWriter();
			String tbid = req.getParameter("tbid");
			if(null!=tbid && !tbid.equals("")){
				List<OperationSettlementRule> osrlist = operationSettlementRuleService.findByCBID(tbid);
				List<Map<String, Object>>  list = operationSettlementRuleService.queryOSFList(osrlist.size()>=1?osrlist.get(0).getOsr_fixed_exceed_tableid():null);
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

	@RequestMapping("/delete1")
	public void delete1(HttpServletRequest req,HttpServletResponse res){
		JSONObject json = new JSONObject();
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		json.put("status", "n");
		json.put("info", "操作失败!");
		try {
			out = res.getWriter();
			String id = req.getParameter("id");
			if(null!=id && !id.equals("")){
				operationSettlementRuleService.deleteOSF(id);
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
	
	@RequestMapping("/get_show_tab2")
	public void get_show_tab2(HttpServletRequest req,HttpServletResponse res){
		JSONObject json = new JSONObject();
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		json.put("status", "n");
		json.put("info", "操作失败!");
		try {
			out = res.getWriter();
			String tbid = req.getParameter("tbid");
			if(null!=tbid && !tbid.equals("")){
				List<OperationSettlementRule> osrlist = operationSettlementRuleService.findByCBID(tbid);
				if (osrlist.size()!=0) {
					List<Map<String, Object>>  list = operationSettlementRuleService.queryTBTList(String.valueOf(osrlist.get(0).getOsr_btcrti_tableid()));
					json.put("list", list);
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
	
	@RequestMapping("/delete2")
	public void delete2(HttpServletRequest req,HttpServletResponse res){
		JSONObject json = new JSONObject();
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		json.put("status", "n");
		json.put("info", "操作失败!");
		try {
			out = res.getWriter();
			String id = req.getParameter("id");
			if(null!=id && !id.equals("")){
				operationSettlementRuleService.deleteTBT(id);
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
	
	@RequestMapping("/saveB2CTHHRKFJT")
	public void saveB2CTHHRKFJT(HttpServletRequest req,HttpServletResponse res){
		JSONObject json = new JSONObject();
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		json.put("status", "n");
		json.put("info", "操作失败!");
		try {
			out = res.getWriter();
			String contract_id = req.getParameter("cbid");
			String osr_btcrti_tableid = null;
			List<OperationSettlementRule> list = operationSettlementRuleService.findByCBID(contract_id);
			Map<String, Object> map = new HashMap<String, Object>();
			if (list.size()!=0 && null!=list.get(0).getOsr_btcrti_tableid() && !list.get(0).getOsr_btcrti_tableid().equals("")) {
				osr_btcrti_tableid = list.get(0).getOsr_btcrti_tableid();		// Update
			}else{
				osr_btcrti_tableid = UUID.randomUUID().toString();				// Insert
			}
			map.put("contract_id", contract_id);
			String osr_btcrtib_fee = req.getParameter("B2CTHHRKF");
			String osr_btcrtib_piece = req.getParameter("B2CTHHRKFJT");
			//5.2.2 有阶梯
			String btcrti_mark1 = req.getParameter("btcrti_mark1");					//条件1
			String btcrti_mark2 = req.getParameter("btcrti_mark2");					//条件2
			String btcrti_mark3 = req.getParameter("btcrti_mark3");					//条件3
			String btcrti_mark4 = req.getParameter("btcrti_mark4");					//参数1
			String btcrti_mark5 = req.getParameter("btcrti_mark5");					//参数2
			//区间
			String btcrti_interval = getSection(btcrti_mark1, btcrti_mark2, btcrti_mark3, btcrti_mark4, btcrti_mark5);
			if(null!=btcrti_interval){
				List<Map<String, Object>> taList = operationSettlementRuleService.queryTBTList(list.get(0).getOsr_btcrti_tableid());
				for (int i = 0; i < taList.size(); i++) {
					IntervalValidationUtil.isExist(btcrti_interval, taList.get(i).get("btcrti_interval").toString());
				}
				//区间参数 合法
				String btcrt_price = req.getParameter("btcrt_price");
				String btcrt_price_unit = req.getParameter("btcrt_price_unit");
				Map<String, Object> map2 = new HashMap<String, Object>();
				map2.put("btcrti_interval", btcrti_interval);
				map2.put("btcrt_price", btcrt_price);
				map2.put("btcrt_price_unit", btcrt_price_unit);
				map2.put("btcrt_id", osr_btcrti_tableid);
				map.put("osr_btcrti_tableid", osr_btcrti_tableid);
				map.put("osr_btcrtib_fee", osr_btcrtib_fee);
				map.put("osr_btcrtib_piece", osr_btcrtib_piece);
				if((null!=map2.get("btcrti_interval") && !map2.get("btcrti_interval").equals("")) &&
						(null!=map2.get("btcrt_price") && !map2.get("btcrt_price").equals("")) &&
						(null!=map2.get("btcrt_price_unit") && !map2.get("btcrt_price_unit").equals(""))&&
						(null!=map.get("osr_btcrti_tableid") && !map.get("osr_btcrti_tableid").equals(""))&&
						(null!=map.get("osr_btcrtib_fee") && !map.get("osr_btcrtib_fee").equals(""))&&
						(null!=map.get("osr_btcrtib_piece") && !map.get("osr_btcrtib_piece").equals(""))){
					operationSettlementRuleService.saveAupdateTBT(map, map2);
					json.put("status", "y");
					json.put("info", "操作成功!");
				}else{
					json.put("status", "y");
					json.put("info", "参数错误!");
				}
			}
		} catch(Exception e){
			logger.error(e);
		}
		out.write(json.toString());
		out.flush();
		out.close();
	}

	@RequestMapping("/saveB2CCKCZF2")
	public void saveB2CCKCZF2(HttpServletRequest req,HttpServletResponse res){
		JSONObject json = new JSONObject();
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		json.put("status", "n");
		json.put("info", "操作失败!");
		try {
			out = res.getWriter();
			String contract_id = req.getParameter("cbid");
			String osr_btcobopnum_tableid = null;
			List<OperationSettlementRule> list = operationSettlementRuleService.findByCBID(contract_id);
			Map<String, Object> map = new HashMap<String, Object>();
			if (list.size()!=0 && null!=list.get(0).getOsr_btcobopnum_tableid() && !list.get(0).getOsr_btcobopnum_tableid().equals("")) {
				osr_btcobopnum_tableid = list.get(0).getOsr_btcobopnum_tableid();		// Update
			}else{
				osr_btcobopnum_tableid = UUID.randomUUID().toString();				// Insert
			}
			map.put("contract_id", contract_id);
			String osr_btcobop_fee = req.getParameter("B2CCKCZF");
			String osr_btcobop_numfee = req.getParameter("B2CCKCZFJT");
			//5.2.2 有阶梯
			String bnt_mark1 = req.getParameter("bnt_mark1");					//条件1
			String bnt_mark2 = req.getParameter("bnt_mark2");					//条件2
			String bnt_mark3 = req.getParameter("bnt_mark3");					//条件3
			String bnt_mark4 = req.getParameter("bnt_mark4");					//参数1
			String bnt_mark5 = req.getParameter("bnt_mark5");					//参数2
			//区间
			String bnt_interval = getSection(bnt_mark1, bnt_mark2, bnt_mark3, bnt_mark4, bnt_mark5);
			if(null!=bnt_interval){
				List<Map<String, Object>> taList = operationSettlementRuleService.queryTBNTList(list.get(0).getOsr_btcobopnum_tableid());
				for (int i = 0; i < taList.size(); i++) {
					IntervalValidationUtil.isExist(bnt_interval, taList.get(i).get("bnt_interval").toString());
				}
				//区间参数 合法
				String bnt_price = req.getParameter("bnt_price");
				String bnt_discount = req.getParameter("bnt_discount");
				Map<String, Object> map2 = new HashMap<String, Object>();
				map2.put("bnt_interval", bnt_interval);
				map2.put("bnt_price", bnt_price);
				map2.put("bnt_discount", bnt_discount);
				map2.put("bnt_id", osr_btcobopnum_tableid);
				map.put("osr_btcobopnum_tableid", osr_btcobopnum_tableid);
				map.put("osr_btcobop_fee", osr_btcobop_fee);
				map.put("osr_btcobop_numfee", osr_btcobop_numfee);
				
				if((null!=map2.get("bnt_interval") && !map2.get("bnt_interval").equals("")) &&
						(null!=map2.get("bnt_discount") && !map2.get("bnt_discount").equals(""))&&
						(null!=map2.get("bnt_id") && !map2.get("bnt_id").equals(""))&&
						(null!=map.get("osr_btcobopnum_tableid") && !map.get("osr_btcobopnum_tableid").equals(""))&&
						(null!=map.get("osr_btcobop_fee") && !map.get("osr_btcobop_fee").equals(""))){
					operationSettlementRuleService.saveAupdateTBNT(map, map2);
					json.put("status", "y");
					json.put("info", "操作成功!");
				}else{
					json.put("status", "n");
					json.put("info", "参数错误!");
				}
			}
		} catch(Exception e){
			logger.error(e);
		}
		out.write(json.toString());
		out.flush();
		out.close();
	}
	
	@RequestMapping("/get_show_tab3")
	public void get_show_tab3(HttpServletRequest req,HttpServletResponse res){
		JSONObject json = new JSONObject();
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		json.put("status", "n");
		json.put("info", "操作失败!");
		try {
			out = res.getWriter();
			String tbid = req.getParameter("tbid");
			if(null!=tbid && !tbid.equals("")){
				List<OperationSettlementRule> osrlist = operationSettlementRuleService.findByCBID(tbid);
				if (osrlist.size()!=0) {
					List<Map<String, Object>>  list = operationSettlementRuleService.queryTBNTList(String.valueOf(osrlist.get(0).getOsr_btcobopnum_tableid()));
					json.put("list", list);
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
	
	@RequestMapping("/delete3")
	public void delete3(HttpServletRequest req,HttpServletResponse res){
		JSONObject json = new JSONObject();
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		json.put("status", "n");
		json.put("info", "操作失败!");
		try {
			out = res.getWriter();
			String id = req.getParameter("id");
			if(null!=id && !id.equals("")){
				operationSettlementRuleService.deleteTBNT(id);
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

	@RequestMapping("/delete36")
	public void delete36(HttpServletRequest req,HttpServletResponse res){
		JSONObject json = new JSONObject();
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		json.put("status", "n");
		json.put("info", "操作失败!");
		try {
			out = res.getWriter();
			String id = req.getParameter("id");
			if(null!=id && !id.equals("")){
				operationSettlementRuleService.deleteTBBTs(id);
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
	

	@RequestMapping("/delete37")
	public void delete37(HttpServletRequest req,HttpServletResponse res){
		JSONObject json = new JSONObject();
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		json.put("status", "n");
		json.put("info", "操作失败!");
		try {
			out = res.getWriter();
			String id = req.getParameter("id");
			if(null!=id && !id.equals("")){
				operationSettlementRuleService.deleteTBBTss(id);
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

	@RequestMapping("/saveB2CCKCZF2s")
	public void saveB2CCKCZF2s(HttpServletRequest req,HttpServletResponse res){
		JSONObject json = new JSONObject();
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		json.put("status", "n");
		json.put("info", "操作失败!");
		try {
			out = res.getWriter();
			String contract_id = req.getParameter("cbid");
			String osr_btcobopnum_tableids = null;
			List<OperationSettlementRule> list = operationSettlementRuleService.findByCBID(contract_id);
			Map<String, Object> map = new HashMap<String, Object>();
			if (list.size()!=0 && null!=list.get(0).getOsr_btcobopnum_tableids() && !list.get(0).getOsr_btcobopnum_tableids().equals("")) {
				osr_btcobopnum_tableids = list.get(0).getOsr_btcobopnum_tableids();		// Update
			}else{
				osr_btcobopnum_tableids = UUID.randomUUID().toString();				// Insert
			}
			map.put("contract_id", contract_id);
			String osr_btcobop_fee = req.getParameter("B2CCKCZF");
			String osr_btcobop_numfees = req.getParameter("B2CCKCZFJTs");
			//5.2.2 有阶梯
			String bnts_mark1 = req.getParameter("bnts_mark1");					//条件1
			String bnts_mark2 = req.getParameter("bnts_mark2");					//条件2
			String bnts_mark3 = req.getParameter("bnts_mark3");					//条件3
			String bnts_mark4 = req.getParameter("bnts_mark4");					//参数1
			String bnts_mark5 = req.getParameter("bnts_mark5");					//参数2
			//区间
			String bnts_interval = getSection(bnts_mark1, bnts_mark2, bnts_mark3, bnts_mark4, bnts_mark5);
			if(null!=bnts_interval){
				List<Map<String, Object>> taList = operationSettlementRuleService.queryTBBTLists(list.get(0).getOsr_btcobopnum_tableid());
				for (int i = 0; i < taList.size(); i++) {
					IntervalValidationUtil.isExist(bnts_interval, taList.get(i).get("bnts_interval").toString());
				}
				//区间参数 合法
				String bnts_price = req.getParameter("bnts_price");
				String bnts_discount = req.getParameter("bnts_discount");
				Map<String, Object> map2 = new HashMap<String, Object>();
				map2.put("bnts_interval", bnts_interval);
				map2.put("bnts_price", bnts_price);
				map2.put("bnts_discount", bnts_discount);
				map2.put("bnts_id", osr_btcobopnum_tableids);
				map.put("osr_btcobopnum_tableids", osr_btcobopnum_tableids);
				map.put("osr_btcobop_fee", osr_btcobop_fee);
				map.put("osr_btcobop_numfees", osr_btcobop_numfees);
				
				if((null!=map2.get("bnts_interval") && !map2.get("bnts_interval").equals("")) &&
						(null!=map2.get("bnts_discount") && !map2.get("bnts_discount").equals(""))&&
						(null!=map2.get("bnts_id") && !map2.get("bnts_id").equals(""))&&
						(null!=map.get("osr_btcobopnum_tableids") && !map.get("osr_btcobopnum_tableids").equals(""))&&
						(null!=map.get("osr_btcobop_fee") && !map.get("osr_btcobop_fee").equals(""))){
					operationSettlementRuleService.saveAupdateTBBTs(map, map2);
					json.put("status", "y");
					json.put("info", "操作成功!");
				}else{
					json.put("status", "n");
					json.put("info", "参数错误!");
				}
			}
		} catch(Exception e){
			logger.error(e);
		}
		out.write(json.toString());
		out.flush();
		out.close();
	}
	
	@RequestMapping("/get_show_tab36")
	public void get_show_tab36(HttpServletRequest req,HttpServletResponse res){
		JSONObject json = new JSONObject();
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		json.put("status", "n");
		json.put("info", "操作失败!");
		try {
			out = res.getWriter();
			String tbid = req.getParameter("tbid");
			if(null!=tbid && !tbid.equals("")){
				List<OperationSettlementRule> osrlist = operationSettlementRuleService.findByCBID(tbid);
				if (osrlist.size()!=0) {
					List<Map<String, Object>>  list = operationSettlementRuleService.queryTBBTLists(String.valueOf(osrlist.get(0).getOsr_btcobopnum_tableids()));
					json.put("list", list);
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
	

	@RequestMapping("/get_show_tab37")
	public void get_show_tab37(HttpServletRequest req,HttpServletResponse res){
		JSONObject json = new JSONObject();
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		json.put("status", "n");
		json.put("info", "操作失败!");
		try {
			out = res.getWriter();
			String tbid = req.getParameter("tbid");
			if(null!=tbid && !tbid.equals("")){
				List<OperationSettlementRule> osrlist = operationSettlementRuleService.findByCBID(tbid);
				if (osrlist.size()!=0) {
					List<Map<String, Object>>  list = operationSettlementRuleService.queryTBBTListss(String.valueOf(osrlist.get(0).getOsr_btcobopnum_tableidss()));
					json.put("list", list);
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

	@RequestMapping("/saveB2CCKCZFJT3")
	public void saveB2CCKCZFJT3(HttpServletRequest req,HttpServletResponse res){
		JSONObject json = new JSONObject();
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		json.put("status", "n");
		json.put("info", "操作失败!");
		try {
			out = res.getWriter();
			String contract_id = req.getParameter("cbid");
			String obt_table_id = null;
			List<OperationSettlementRule> list = operationSettlementRuleService.findByCBID(contract_id);
			Map<String, Object> map = new HashMap<String, Object>();
			if (list.size()!=0 && null!=list.get(0).getOsr_btcobopbill_tobtb_tableid() && !list.get(0).getOsr_btcobopbill_tobtb_tableid().equals("")) {
				obt_table_id = list.get(0).getOsr_btcobopbill_tobtb_tableid();		// Update
			}else{
				obt_table_id = UUID.randomUUID().toString();				// Insert
			}
			map.put("contract_id", contract_id);
			String osr_btcobop_fee = req.getParameter("B2CCKCZF");
			String osr_btcobop_numfee = req.getParameter("B2CCKCZFJT");
			//5.2.2 有阶梯
			String obt_mark1 = req.getParameter("obt_mark1");					//条件1
			String obt_mark2 = req.getParameter("obt_mark2");					//条件2
			String obt_mark3 = req.getParameter("obt_mark3");					//条件3
			String obt_mark4 = req.getParameter("obt_mark4");					//参数1
			String obt_mark5 = req.getParameter("obt_mark5");					//参数2
			//区间
			String obt_interval = getSection(obt_mark1, obt_mark2, obt_mark3, obt_mark4, obt_mark5);
			if(null!=obt_interval){
				List<Map<String, Object>> taList = operationSettlementRuleService.queryTOTList(list.get(0).getOsr_btcobopbill_tobtb_tableid());
				for (int i = 0; i < taList.size(); i++) {
					IntervalValidationUtil.isExist(obt_interval, taList.get(i).get("obt_interval").toString());
				}
				//区间参数 合法
				String obt_price = req.getParameter("obt_price");
				String obt_discount = req.getParameter("obt_discount");
				Map<String, Object> map2 = new HashMap<String, Object>();
				map2.put("obt_interval", obt_interval);
				map2.put("obt_price", obt_price);
				map2.put("obt_discount", obt_discount);
				map2.put("obt_table_id", obt_table_id);
				map.put("osr_btcobopbill_tobtb_tableid", obt_table_id);
				map.put("osr_btcobop_fee", osr_btcobop_fee);
				map.put("osr_btcobop_numfee", osr_btcobop_numfee);
				
				if((null!=map2.get("obt_interval") && !map2.get("obt_interval").equals("")) &&
						(null!=map2.get("obt_price") && !map2.get("obt_price").equals("")) &&
						(null!=map2.get("obt_discount") && !map2.get("obt_discount").equals(""))&&
						(null!=map2.get("obt_table_id") && !map2.get("obt_table_id").equals(""))&&
						(null!=map.get("osr_btcobopbill_tobtb_tableid") && !map.get("osr_btcobopbill_tobtb_tableid").equals(""))&&
						(null!=map.get("osr_btcobop_fee") && !map.get("osr_btcobop_fee").equals(""))){
					operationSettlementRuleService.saveAupdateTOT(map, map2);
					json.put("status", "y");
					json.put("info", "操作成功!");
				}else{
					json.put("status", "n");
					json.put("info", "参数错误!");
				}
			}
		} catch(Exception e){
			logger.error(e);
		}
		out.write(json.toString());
		out.flush();
		out.close();
	}
	
	@RequestMapping("/saveB2CCKCZFJT3s")
	public void saveB2CCKCZFJT3s(HttpServletRequest req,HttpServletResponse res){
		JSONObject json = new JSONObject();
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		json.put("status", "n");
		json.put("info", "操作失败!");
		try {
			out = res.getWriter();
			String contract_id = req.getParameter("cbid");
			String obt_table_id = null;
			List<OperationSettlementRule> list = operationSettlementRuleService.findByCBID(contract_id);
			Map<String, Object> map = new HashMap<String, Object>();
			if (list.size()!=0 && null!=list.get(0).getOsr_btcobopnum_tableidss() && !list.get(0).getOsr_btcobopnum_tableidss().equals("")) {
				obt_table_id = list.get(0).getOsr_btcobopnum_tableidss();		// Update
			}else{
				obt_table_id = UUID.randomUUID().toString();				// Insert
			}
			map.put("contract_id", contract_id);
			String osr_btcobop_fee = req.getParameter("B2CCKCZF");
			String osr_btcobop_numfees = req.getParameter("B2CCKCZFJTs");
			//5.2.2 有阶梯
			String obts_mark1 = req.getParameter("obts_mark1");					//条件1
			String obts_mark2 = req.getParameter("obts_mark2");					//条件2
			String obts_mark3 = req.getParameter("obts_mark3");					//条件3
			String obts_mark4 = req.getParameter("obts_mark4");					//参数1
			String obts_mark5 = req.getParameter("obts_mark5");					//参数2
			//区间
			String obts_interval = getSection(obts_mark1, obts_mark2, obts_mark3, obts_mark4, obts_mark5);
			if(null!=obts_interval){
				List<Map<String, Object>> taList = operationSettlementRuleService.queryTBBTListss(list.get(0).getOsr_btcobopnum_tableidss());
				for (int i = 0; i < taList.size(); i++) {
					IntervalValidationUtil.isExist(obts_interval, taList.get(i).get("obts_interval").toString());
				}
				//区间参数 合法
				String obt_price = req.getParameter("obts_price");
				String obt_discount = req.getParameter("obts_discount");
				Map<String, Object> map2 = new HashMap<String, Object>();
				map2.put("obts_interval", obts_interval);
				map2.put("obts_price", obt_price);
				map2.put("obts_discount", obt_discount);
				map2.put("obts_table_id", obt_table_id);
				map.put("osr_btcobopnum_tableidss", obt_table_id);
				map.put("osr_btcobop_fee", osr_btcobop_fee);
				map.put("osr_btcobop_numfees", osr_btcobop_numfees);
				
				if((null!=map2.get("obts_interval") && !map2.get("obts_interval").equals("")) &&
						(null!=map2.get("obts_price") && !map2.get("obts_price").equals("")) &&
						(null!=map2.get("obts_discount") && !map2.get("obts_discount").equals(""))&&
						(null!=map2.get("obts_table_id") && !map2.get("obts_table_id").equals(""))&&
						(null!=map.get("osr_btcobopnum_tableidss") && !map.get("osr_btcobopnum_tableidss").equals(""))&&
						(null!=map.get("osr_btcobop_fee") && !map.get("osr_btcobop_fee").equals(""))){
					operationSettlementRuleService.saveAupdateTBBTss(map, map2);
					json.put("status", "y");
					json.put("info", "操作成功!");
				}else{
					json.put("status", "n");
					json.put("info", "参数错误!");
				}
			}
		} catch(Exception e){
			logger.error(e);
		}
		out.write(json.toString());
		out.flush();
		out.close();
	}
	
	@RequestMapping("/get_show_tab4")
	public void get_show_tab4(HttpServletRequest req,HttpServletResponse res){
		JSONObject json = new JSONObject();
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		json.put("status", "n");
		json.put("info", "操作失败!");
		try {
			out = res.getWriter();
			String tbid = req.getParameter("tbid");
			if(null!=tbid && !tbid.equals("")){
				List<OperationSettlementRule> osrlist = operationSettlementRuleService.findByCBID(tbid);
				if (osrlist.size()!=0) {
					List<Map<String, Object>>  list = operationSettlementRuleService.queryTOTList(String.valueOf(osrlist.get(0).getOsr_btcobopbill_tobtb_tableid()));
					json.put("list", list);
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
	
	@RequestMapping("/delete4")
	public void delete4(HttpServletRequest req,HttpServletResponse res){
		JSONObject json = new JSONObject();
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		json.put("status", "n");
		json.put("info", "操作失败!");
		try {
			out = res.getWriter();
			String id = req.getParameter("id");
			if(null!=id && !id.equals("")){
				operationSettlementRuleService.deleteTOT(id);
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
	
	

	@RequestMapping("/saveTBBT")
	public void saveTBBT(HttpServletRequest req,HttpServletResponse res){
		JSONObject json = new JSONObject();
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		json.put("status", "n");
		json.put("info", "操作失败!");
		try {
			out = res.getWriter();
			String contract_id = req.getParameter("cbid");
			String osr_btcobopbill_tableid = null;
			List<OperationSettlementRule> list = operationSettlementRuleService.findByCBID(contract_id);
			Map<String, Object> map = new HashMap<String, Object>();
			if (list.size()!=0 && null!=list.get(0).getOsr_btcobopbill_tableid() && !list.get(0).getOsr_btcobopbill_tableid().equals("")) {
				osr_btcobopbill_tableid = list.get(0).getOsr_btcobopbill_tableid();		// Update
			}else{
				osr_btcobopbill_tableid = UUID.randomUUID().toString();				// Insert
			}
			map.put("contract_id", contract_id);
			String osr_btcobop_fee = req.getParameter("B2CCKCZF");
			//5.2.2 有阶梯
			String tbbt_mark1 = req.getParameter("tbbt_mark1");					//条件1
			String tbbt_mark2 = req.getParameter("tbbt_mark2");					//条件2
			String tbbt_mark3 = req.getParameter("tbbt_mark3");					//条件3
			String tbbt_mark4 = req.getParameter("tbbt_mark4");					//参数1
			String tbbt_mark5 = req.getParameter("tbbt_mark5");					//参数2
			//区间
			String bbt_interval = getSection(tbbt_mark1, tbbt_mark2, tbbt_mark3, tbbt_mark4, tbbt_mark5);
			if(null!=bbt_interval){
				List<Map<String, Object>> taList = operationSettlementRuleService.queryTBBTList(list.get(0).getOsr_btcobopbill_tableid());
				for (int i = 0; i < taList.size(); i++) {
					IntervalValidationUtil.isExist(bbt_interval, taList.get(i).get("bbt_interval").toString());
				}
				//区间参数 合法
				String bbt_bill_price = req.getParameter("bbt_bill_price");
				String bbt_piece_price = req.getParameter("bbt_piece_price");
				Map<String, Object> map2 = new HashMap<String, Object>();
				map2.put("bbt_interval", bbt_interval);
				map2.put("bbt_bill_price", bbt_bill_price);
				map2.put("bbt_piece_price", bbt_piece_price);
				map2.put("bbt_table_id", osr_btcobopbill_tableid);
				map.put("osr_btcobopbill_tableid", osr_btcobopbill_tableid);
				map.put("osr_btcobop_fee", osr_btcobop_fee);
				
				if((null!=map2.get("bbt_interval") && !map2.get("bbt_interval").equals("")) &&
						(null!=map2.get("bbt_bill_price") && !map2.get("bbt_bill_price").equals("")) &&
						(null!=map2.get("bbt_piece_price") && !map2.get("bbt_piece_price").equals(""))&&
						(null!=map2.get("bbt_table_id") && !map2.get("bbt_table_id").equals(""))&&
						(null!=map.get("osr_btcobopbill_tableid") && !map.get("osr_btcobopbill_tableid").equals(""))&&
						(null!=map.get("osr_btcobop_fee") && !map.get("osr_btcobop_fee").equals(""))){
					operationSettlementRuleService.saveAupdateTBBT(map, map2);
					json.put("status", "y");
					json.put("info", "操作成功!");
				}else{
					json.put("status", "n");
					json.put("info", "参数错误!");
				}
			}
		} catch(Exception e){
			logger.error(e);
		}
		out.write(json.toString());
		out.flush();
		out.close();
	}
	
	@RequestMapping("/get_show_tab5")
	public void get_show_tab5(HttpServletRequest req,HttpServletResponse res){
		JSONObject json = new JSONObject();
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		json.put("status", "n");
		json.put("info", "操作失败!");
		try {
			out = res.getWriter();
			String tbid = req.getParameter("tbid");
			if(null!=tbid && !tbid.equals("")){
				List<OperationSettlementRule> osrlist = operationSettlementRuleService.findByCBID(tbid);
				if (osrlist.size()!=0) {
					List<Map<String, Object>>  list = operationSettlementRuleService.queryTBBTList(String.valueOf(osrlist.get(0).getOsr_btcobopbill_tableid()));
					json.put("list", list);
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
	
	@RequestMapping("/delete5")
	public void delete5(HttpServletRequest req,HttpServletResponse res){
		JSONObject json = new JSONObject();
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		json.put("status", "n");
		json.put("info", "操作失败!");
		try {
			out = res.getWriter();
			String id = req.getParameter("id");
			if(null!=id && !id.equals("")){
				operationSettlementRuleService.deleteTBBT(id);
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
	
	@RequestMapping("/save6")
	public void save6(HttpServletRequest req,HttpServletResponse res){
		JSONObject json = new JSONObject();
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		json.put("status", "n");
		json.put("info", "操作失败!");
		try {
			out = res.getWriter();
			String contract_id = req.getParameter("cbid");
			String osr_btcobopbill_discount_tableid = null;
			List<OperationSettlementRule> list = operationSettlementRuleService.findByCBID(contract_id);
			Map<String, Object> map = new HashMap<String, Object>();
			if (list.size()!=0 && null!=list.get(0).getOsr_btcobopbill_discount_tableid() && !list.get(0).getOsr_btcobopbill_discount_tableid().equals("")) {
				osr_btcobopbill_discount_tableid = list.get(0).getOsr_btcobopbill_discount_tableid();		// Update
			}else{
				osr_btcobopbill_discount_tableid = UUID.randomUUID().toString();				// Insert
			}
			map.put("contract_id", contract_id);
			String osr_btcobopbill_discount_type = req.getParameter("B2CADJTCK");
			//5.2.2 有阶梯
			String btcbd_mark1 = req.getParameter("btcbd_mark1");					//条件1
			String btcbd_mark2 = req.getParameter("btcbd_mark2");					//条件2
			String btcbd_mark3 = req.getParameter("btcbd_mark3");					//条件3
			String btcbd_mark4 = req.getParameter("btcbd_mark4");					//参数1
			String btcbd_mark5 = req.getParameter("btcbd_mark5");					//参数2
			//区间
			String btcbd_interval = getSection(btcbd_mark1, btcbd_mark2, btcbd_mark3, btcbd_mark4, btcbd_mark5);
			if(null!=btcbd_interval){
				List<Map<String, Object>> taList = operationSettlementRuleService.queryBTBDList(list.get(0).getOsr_btcobopbill_discount_tableid());
				for (int i = 0; i < taList.size(); i++) {
					IntervalValidationUtil.isExist(btcbd_interval, taList.get(i).get("btcbd_interval").toString());
				}
				//区间参数 合法
				String btcbd_price = req.getParameter("btcbd_price");
				Map<String, Object> map2 = new HashMap<String, Object>();
				map2.put("btcbd_interval", btcbd_interval);
				map2.put("btcbd_price", btcbd_price);
				map2.put("btcbd_id", osr_btcobopbill_discount_tableid);
				map.put("osr_btcobopbill_discount_tableid", osr_btcobopbill_discount_tableid);
				map.put("osr_btcobopbill_discount_type", osr_btcobopbill_discount_type);
				
				if((null!=map2.get("btcbd_interval") && !map2.get("btcbd_interval").equals("")) &&
						(null!=map2.get("btcbd_price") && !map2.get("btcbd_price").equals("")) &&
						(null!=map2.get("btcbd_id") && !map2.get("btcbd_id").equals(""))&&
						(null!=map.get("osr_btcobopbill_discount_tableid") && !map.get("osr_btcobopbill_discount_tableid").equals(""))&&
						(null!=map.get("osr_btcobopbill_discount_type") && !map.get("osr_btcobopbill_discount_type").equals(""))){
					operationSettlementRuleService.saveAupdateBTBD(map, map2);
					json.put("status", "y");
					json.put("info", "操作成功!");
				}else{
					json.put("status", "n");
					json.put("info", "参数错误!");
				}
			}
		} catch(Exception e){
			logger.error(e);
		}
		out.write(json.toString());
		out.flush();
		out.close();
	}
	
	@RequestMapping("/show6")
	public void show6(HttpServletRequest req,HttpServletResponse res){
		JSONObject json = new JSONObject();
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		json.put("status", "n");
		json.put("info", "操作失败!");
		try {
			out = res.getWriter();
			String tbid = req.getParameter("tbid");
			if(null!=tbid && !tbid.equals("")){
				List<OperationSettlementRule> osrlist = operationSettlementRuleService.findByCBID(tbid);
				if (osrlist.size()!=0) {
					List<Map<String, Object>>  list = operationSettlementRuleService.queryBTBDList(String.valueOf(osrlist.get(0).getOsr_btcobopbill_discount_tableid()));
					json.put("list", list);
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
	
	@RequestMapping("/delete6")
	public void delete6(HttpServletRequest req,HttpServletResponse res){
		JSONObject json = new JSONObject();
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		json.put("status", "n");
		json.put("info", "操作失败!");
		try {
			out = res.getWriter();
			String id = req.getParameter("id");
			if(null!=id && !id.equals("")){
				operationSettlementRuleService.deleteBTBD(id);
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
	
	@RequestMapping("/save7")
	public void save7(HttpServletRequest req,HttpServletResponse res){
		JSONObject json = new JSONObject();
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		json.put("status", "n");
		json.put("info", "操作失败!");
		try {
			out = res.getWriter();
			String contract_id = req.getParameter("cbid");
			String osr_btcobopbill_discount_tableid2 = "";
			List<OperationSettlementRule> list = operationSettlementRuleService.findByCBID(contract_id);
			Map<String, Object> map = new HashMap<String, Object>();
			if (list.size()!=0 && null!=list.get(0).getOsr_btcobopbill_discount_tableid2() && !list.get(0).getOsr_btcobopbill_discount_tableid2().equals("")) {
				osr_btcobopbill_discount_tableid2 = list.get(0).getOsr_btcobopbill_discount_tableid2();		// Update
			}else{
				osr_btcobopbill_discount_tableid2 = UUID.randomUUID().toString();				// Insert
			}
			map.put("contract_id", contract_id);
			String osr_btcobopbill_discount_type = req.getParameter("B2CADJTCK");
			//5.2.2 有阶梯
			String btcbd2_mark1 = req.getParameter("btcbd2_mark1");					//条件1
			String btcbd2_mark2 = req.getParameter("btcbd2_mark2");					//条件2
			String btcbd2_mark3 = req.getParameter("btcbd2_mark3");					//条件3
			String btcbd2_mark4 = req.getParameter("btcbd2_mark4");					//参数1
			String btcbd2_mark5 = req.getParameter("btcbd2_mark5");					//参数2
			//区间
			String btcbd2_interval2 = getSection(btcbd2_mark1, btcbd2_mark2, btcbd2_mark3, btcbd2_mark4, btcbd2_mark5);
			if(null!=btcbd2_interval2){

				List<Map<String, Object>> taList = operationSettlementRuleService.queryBTBD2List(list.get(0).getOsr_btcobopbill_discount_tableid2());
				for (int i = 0; i < taList.size(); i++) {
					IntervalValidationUtil.isExist(btcbd2_interval2, taList.get(i).get("btcbd2_interval2").toString());
				}
				//区间参数 合法
				String btcbd2_price = req.getParameter("btcbd2_price");
				Map<String, Object> map2 = new HashMap<String, Object>();
				map2.put("btcbd2_interval", btcbd2_interval2);
				map2.put("btcbd2_price", btcbd2_price);
				map2.put("btcbd2_id", osr_btcobopbill_discount_tableid2);
				map.put("osr_btcobopbill_discount_tableid2", osr_btcobopbill_discount_tableid2);
				map.put("osr_btcobopbill_discount_type", osr_btcobopbill_discount_type);
				
				if((null!=map2.get("btcbd2_interval") && !map2.get("btcbd2_interval").equals("")) &&
						(null!=map2.get("btcbd2_price") && !map2.get("btcbd2_price").equals("")) &&
						(null!=map2.get("btcbd2_id") && !map2.get("btcbd2_id").equals(""))&&
						(null!=map.get("osr_btcobopbill_discount_tableid2") && !map.get("osr_btcobopbill_discount_tableid2").equals(""))&&
						(null!=map.get("osr_btcobopbill_discount_type") && !map.get("osr_btcobopbill_discount_type").equals(""))){
					operationSettlementRuleService.saveAupdateBTBD2(map, map2);
					json.put("status", "y");
					json.put("info", "操作成功!");
				}else{
					json.put("status", "n");
					json.put("info", "参数错误!");
				}
			}
		} catch(Exception e){
			logger.error(e);
		}
		out.write(json.toString());
		out.flush();
		out.close();
	}
	
	@RequestMapping("/show7")
	public void show7(HttpServletRequest req,HttpServletResponse res){
		JSONObject json = new JSONObject();
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		json.put("status", "n");
		json.put("info", "操作失败!");
		try {
			out = res.getWriter();
			String tbid = req.getParameter("tbid");
			if(null!=tbid && !tbid.equals("")){
				List<OperationSettlementRule> osrlist = operationSettlementRuleService.findByCBID(tbid);
				if (osrlist.size()!=0) {
					List<Map<String, Object>>  list = operationSettlementRuleService.queryBTBD2List(String.valueOf(osrlist.get(0).getOsr_btcobopbill_discount_tableid2()));
					json.put("list", list);
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
	
	@RequestMapping("/delete7")
	public void delete7(HttpServletRequest req,HttpServletResponse res){
		JSONObject json = new JSONObject();
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		json.put("status", "n");
		json.put("info", "操作失败!");
		try {
			out = res.getWriter();
			String id = req.getParameter("id");
			if(null!=id && !id.equals("")){
				operationSettlementRuleService.deleteBTBD2(id);
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
	

	@RequestMapping("/save8")
	public void save8(HttpServletRequest req,HttpServletResponse res){
		JSONObject json = new JSONObject();
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		json.put("status", "n");
		json.put("info", "操作失败!");
		try {
			out = res.getWriter();
			String contract_id = req.getParameter("cbid");
			String osr_zzfwf_detail = "";
			List<OperationSettlementRule> list = operationSettlementRuleService.findByCBID(contract_id);
			Map<String, Object> map = new HashMap<String, Object>();
			if (list.size()!=0 && null!=list.get(0).getOsr_zzfwf_detail() && !list.get(0).getOsr_zzfwf_detail().equals("")) {
				osr_zzfwf_detail = list.get(0).getOsr_zzfwf_detail();		// Update
			}else{
				osr_zzfwf_detail = UUID.randomUUID().toString();				// Insert
			}
			map.put("contract_id", contract_id);
			String osr_zzfwf_status = req.getParameter("ZZFWFJT");
			String zzfwf_alls_mark1 = req.getParameter("zzfwf_alls_mark1");					//条件1
			String zzfwf_alls_mark2 = req.getParameter("zzfwf_alls_mark2");					//条件2
			String zzfwf_alls_mark3 = req.getParameter("zzfwf_alls_mark3");					//条件3
			String zzfwf_alls_mark4 = req.getParameter("zzfwf_alls_mark4");					//参数1
			String zzfwf_alls_mark5 = req.getParameter("zzfwf_alls_mark5");					//参数2
			//区间
			String vasd_section = getSection(zzfwf_alls_mark1, zzfwf_alls_mark2, zzfwf_alls_mark3, zzfwf_alls_mark4, zzfwf_alls_mark5);
			if(null!=vasd_section){
				List<Map<String, Object>> taList = operationSettlementRuleService.queryZZFWFList(list.get(0).getOsr_zzfwf_detail());
				for (int i = 0; i < taList.size(); i++) {
					IntervalValidationUtil.isExist(vasd_section, taList.get(i).get("vasd_section").toString());
				}
				//区间参数 合法
				String vasd_value = req.getParameter("vasd_value");
				Map<String, Object> map2 = new HashMap<String, Object>();
				map2.put("vasd_section", vasd_section);
				map2.put("vasd_value", vasd_value);
				map2.put("vasd_tab_id", osr_zzfwf_detail);
				map.put("osr_zzfwf_detail", osr_zzfwf_detail);
				map.put("osr_zzfwf_status", osr_zzfwf_status);
				
				if((null!=map2.get("vasd_section") && !map2.get("vasd_section").equals("")) &&
						(null!=map2.get("vasd_value") && !map2.get("vasd_value").equals("")) &&
						(null!=map2.get("vasd_tab_id") && !map2.get("vasd_tab_id").equals(""))&&
						(null!=map.get("osr_zzfwf_detail") && !map.get("osr_zzfwf_detail").equals(""))&&
						(null!=map.get("osr_zzfwf_status") && !map.get("osr_zzfwf_status").equals(""))){
					operationSettlementRuleService.saveAupdateZZFWF(map, map2);
					json.put("status", "y");
					json.put("info", "操作成功!");
				}else{
					json.put("status", "n");
					json.put("info", "参数错误!");
				}
			}
		} catch(Exception e){
			logger.error(e);
		}
		out.write(json.toString());
		out.flush();
		out.close();
	}
	

	@RequestMapping("/show8")
	public void show8(HttpServletRequest req,HttpServletResponse res){
		JSONObject json = new JSONObject();
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		json.put("status", "n");
		json.put("info", "操作失败!");
		try {
			out = res.getWriter();
			String tbid = req.getParameter("tbid");
			if(null!=tbid && !tbid.equals("")){
				List<OperationSettlementRule> osrlist = operationSettlementRuleService.findByCBID(tbid);
				if (osrlist.size()!=0) {
					List<Map<String, Object>>  list = operationSettlementRuleService.queryZZFWFList(String.valueOf(osrlist.get(0).getOsr_zzfwf_detail()));
					json.put("list", list);
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

	@RequestMapping("/delete8")
	public void delete8(HttpServletRequest req,HttpServletResponse res){
		JSONObject json = new JSONObject();
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		json.put("status", "n");
		json.put("info", "操作失败!");
		try {
			out = res.getWriter();
			String id = req.getParameter("id");
			if(null!=id && !id.equals("")){
				operationSettlementRuleService.deleteZZFWF(id);
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
}
