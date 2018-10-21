package com.bt.lmis.controller;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.bt.lmis.code.BaseController;
import com.bt.lmis.controller.form.DiffBilldeatilsQueryParam;
import com.bt.lmis.controller.form.ExpressbillBatchmasterQueryParam;
import com.bt.lmis.controller.form.ExpressbillDetailQueryParam;
import com.bt.lmis.model.ExpressbillBatchmaster;
import com.bt.lmis.model.ExpressbillDetail;
import com.bt.lmis.model.ExpressbillDetailTemp;
import com.bt.lmis.service.ExpressbillBatchmasterService;
import com.bt.lmis.service.ExpressbillDetailService;
import com.bt.lmis.service.ExpressbillDetailTempService;
import com.bt.utils.BigExcelExport;
import com.bt.utils.CommonUtils;
/**
 * 承运商账单明细表控制器
 *
 */
@Controller
@RequestMapping("/control/lmis/expressbillDetailController")
public class ExpressbillDetailController extends BaseController {

	private static final Logger logger = Logger.getLogger(ExpressbillDetailController.class);
	
	/**
	 * 承运商账单明细表服务类
	 */
	@Resource(name = "expressbillDetailServiceImpl")
	private ExpressbillDetailService<ExpressbillDetail> expressbillDetailService;
	
	@Resource(name = "expressbillBatchmasterServiceImpl")
	private ExpressbillBatchmasterService<ExpressbillBatchmaster> expressbillBatchmasterService;
	
	@Resource(name = "expressbillDetailTempServiceImpl")
	private ExpressbillDetailTempService<ExpressbillDetailTemp> expressbillDetailTempService;
	
	@ResponseBody
	@RequestMapping("toDiff")
	public JSONObject verification(HttpServletRequest req, HttpServletResponse res) throws Exception{
		JSONObject obj=new JSONObject();
		String bat_id="";
		String id=req.getParameter("id").toString();
		ExpressbillBatchmaster eq=null;
		try {
			ExpressbillBatchmasterQueryParam qq=new ExpressbillBatchmasterQueryParam();
			qq.setId(Integer.parseInt(id));
			JSONObject flag=expressbillBatchmasterService.checkFlow(qq, "diff");
			if(flag.get("code").toString().equals("0")){
				return flag;
				}
			
			 eq=	expressbillBatchmasterService.selectById(Integer.parseInt(id));
			bat_id=eq.getBatch_id();
		} catch (NumberFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if("666".equals(eq.getStatus())||"7".equals(eq.getStatus())){
			obj.put("code",0 );
			obj.put("msg", "不能重复匹配！！");
		}
		
		
		Map<String,String> param=new HashMap<String,String>();
		param.put("bat_id", bat_id);
		try{
			ExpressbillDetailQueryParam query=new ExpressbillDetailQueryParam();
			query.setBat_id(bat_id);
			expressbillDetailService.deleteVerification(query);
			
			
			
			
			ExpressbillBatchmasterQueryParam qr=new ExpressbillBatchmasterQueryParam();
			qr.setBatch_id(bat_id);
			expressbillDetailService.diff(param);
			eq.setStatus("666");//转换成功
			expressbillBatchmasterService.update(eq);
			obj.put("code", 1);
		}catch(Exception e){
			eq.setStatus("7");
			expressbillBatchmasterService.update(eq);
			obj.put("code",0);
			e.printStackTrace();
			return obj;
		}
		
		
		return obj;
	}
	
	
	
	
	@RequestMapping("uploade_diff_error")
	public void uploade_diff_error(HttpServletRequest request, HttpServletResponse res, ExpressbillDetailTemp queryParam) throws Exception {
		Map<String,String> result= new HashMap<String,String>();
		PrintWriter out = null;
		result.put("out_result", "0");
		result.put("out_result_reason","错误");
		List<Map<String, Object>> list = expressbillDetailTempService.selectByBatId(queryParam.getBat_id());
		try{
			out = res.getWriter();
			HashMap<String, String> cMap = new LinkedHashMap<String, String>();
			cMap.put("account", "月结账号");
			cMap.put("transport_time", "发货时间");
			cMap.put("waybill", "运单号");
			cMap.put("transport_weight", "发货重量");
			cMap.put("transport_volumn", "体积");
			cMap.put("origin_province", "发货省");
			cMap.put("origin_city", "发货市");
			cMap.put("dest_province", "目的地省");
			cMap.put("dest_city", "目的地市");
			cMap.put("charged_weight", "计费重量");
			cMap.put("express_name", "快递公司");
			cMap.put("producttype_name", "产品类型代码");
			cMap.put("insurance", "保值");
			cMap.put("freight", "运费");
			cMap.put("insurance_fee", "保价费");
			cMap.put("other_value_added_service_charges", "其他增值服务费");
			cMap.put("total_charge", "合计费用");
			cMap.put("reason", "错误原因");
			Date time =new Date();
			SimpleDateFormat date = new SimpleDateFormat("yyyyMMddhhmmss");
			String string = date.format(time);
			BigExcelExport.excelDownLoadDatab(list, cMap, string+"错误信息表.xlsx");
			result.put("out_result", "1");
			result.put("out_result_reason", "成功");
			result.put("path", CommonUtils.getAllMessage("config", "COMMON_DOWNLOAD_MAP")+string+"错误信息表.xlsx");
		}catch(Exception e){
			e.printStackTrace();
		}
		out.write(com.alibaba.fastjson.JSONObject.toJSON(result).toString());
		out.flush();
		out.close();
		
	}
	
}