package com.bt.lmis.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.bt.lmis.code.BaseController;
import com.bt.lmis.service.ExpressContractService;
import com.bt.lmis.service.ManagementOtherLadderService;

/**
 * @Title:ExpressContractController
 * @Description: TODO
 * @author Ian.Huang
 * @date 2016年6月22日下午5:47:47
 */
@Controller
@RequestMapping("/control/expressContractController")
public class ExpressContractController extends BaseController {

	private static final Logger logger = Logger.getLogger(EmployeeController.class);
	@Resource(name = "expressContractServiceImpl")
	private ExpressContractService<?> expressContractServiceImpl;
	@Resource(name="managementOtherLadderServiceImpl")
	private ManagementOtherLadderService managementOtherLadderService;
	
	/**
	 * 
	 * @Description: TODO(删除一条运单折扣规则)
	 * @param request
	 * @param res
	 * @return: void  
	 * @author Ian.Huang 
	 * @date 2016年6月28日下午3:18:31
	 */
	@RequestMapping("delWD")
	public void delWaybillDiscount(HttpServletRequest request, HttpServletResponse res) {
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out= null;
		JSONObject result= null;
		try {
			result= expressContractServiceImpl.deleteWaybillDiscount(request, result);
			
		}  catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			result= new JSONObject();
			result.put("result_code", "FAILURE");
			result.put("result_content", "操作失败,失败原因:"+ e.getMessage());
			
		}
		try {
			out= res.getWriter();
			out.write(result.toString());
			out.flush();
			
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e);
			
		}
		out.close();
		
	}
	
	/**
	 * 
	 * @Description: TODO(新增运单折扣启用条件)
	 * @param request
	 * @param res
	 * @return: void  
	 * @author Ian.Huang 
	 * @date 2016年8月18日上午11:48:38
	 */
	@RequestMapping("saveWaybillDiscount")
	public void saveWaybillDiscount(HttpServletRequest request, HttpServletResponse res) {
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out= null;
		JSONObject result= null;
		try {
			result = expressContractServiceImpl.saveWaybillDiscount(request, result);
		}  catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			result= new JSONObject();
			result.put("result_code", "FAILURE");
			result.put("result_content", "操作失败,失败原因:" + e.getMessage());	
		}
		try {
			out = res.getWriter();
			out.write(result.toString());
			out.flush();
			
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e);
		}
		out.close();
	}
	
	/**
	 * 
	 * @Description: TODO(加载运单折扣启用条件)
	 * @param request
	 * @param res
	 * @return: void  
	 * @author Ian.Huang 
	 * @date 2016年8月18日上午11:09:31
	 */
	@RequestMapping("loadWaybillDiscount")
	public void loadWaybillDiscount(HttpServletRequest request, HttpServletResponse res) {
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out= null;
		JSONObject result = null;
		try {
			result = expressContractServiceImpl.loadWaybillDiscount(request, result);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			result = new JSONObject();
			result.put("result_code", "FAILURE");
			result.put("result_content", "操作失败,失败原因:"+e.getMessage());	
		}
		try {
			out = res.getWriter();
			out.write(result.toString());
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e);
		}
		out.close();	
	}
	
	/**
	 * 
	 * @Description: TODO(检验快递合同配置有效性)
	 * @param request
	 * @param res
	 * @return: void  
	 * @author Ian.Huang 
	 * @date 2016年7月15日上午9:59:30
	 */
	@RequestMapping("checkUniqueValidity")
	public void checkUniqueValidity(HttpServletRequest request, HttpServletResponse res) {
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		JSONObject result = null;
		try {
			result = expressContractServiceImpl.checkUniqueValidity(request, result);
		}  catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			result = new JSONObject();
			result.put("result_code", "FAILURE");
			result.put("result_content", "操作失败,失败原因:"+e.getMessage());	
		}
		try {
			out = res.getWriter();
			out.write(result.toString());
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e);
		}
		out.close();	
	}
	
	/**
	 * 
	 * @Description: TODO(判断是否存在记录)
	 * @param request
	 * @param res
	 * @return: void  
	 * @author Ian.Huang 
	 * @date 2016年7月6日上午10:34:25
	 */
	@RequestMapping("judgeExist")
	public void judgeExist(HttpServletRequest request, HttpServletResponse res) {
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		JSONObject result = null;
		try {
			result = expressContractServiceImpl.judgeExist(request, result);
		}  catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			result = new JSONObject();
			result.put("result_code", "FAILURE");
			result.put("result_content", "操作失败,失败原因:"+e.getMessage());	
		}
		try {
			out = res.getWriter();
			out.write(result.toString());
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e);
		}
		out.close();	
	}
	
	/**
	 * 
	 * @Description: TODO(加载管理费规则)
	 * @param request
	 * @param res
	 * @return: void  
	 * @author Ian.Huang 
	 * @date 2016年7月4日上午1:49:09
	 */
	@RequestMapping("loadManEC")
	public void loadManEC(HttpServletRequest request, HttpServletResponse res) {
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		JSONObject result = null;
		try {
			result = expressContractServiceImpl.loadManEC(request, result);
		}  catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			result = new JSONObject();
			result.put("result_code", "FAILURE");
			result.put("result_content", "操作失败,失败原因:"+e.getMessage());	
		}
		try {
			out = res.getWriter();
			out.write(result.toString());
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e);
		}
		out.close();	
	}
	
	/**
	 * Title: loadManECOther
	 * Description: 加载管理费列表(区别于运费管理费)
	 * @param request
	 * @param res
	 * @author jindong.lin
	 * @date 2017年9月4日
	 */
	@RequestMapping("loadManECOther")
	public void loadManECOther(HttpServletRequest request, HttpServletResponse res) {
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		JSONObject result = null;
		try {
			result = managementOtherLadderService.loadManECOther(request, result);
		}  catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			result = new JSONObject();
			result.put("result_code", "FAILURE");
			result.put("result_content", "操作失败,失败原因:"+e.getMessage());	
		}
		try {
			out = res.getWriter();
			out.write(result.toString());
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e);
		}
		out.close();	
	}
	
	/**
	 * 
	 * @Description: TODO(删除管理费规则)
	 * @param request
	 * @param res
	 * @return: void  
	 * @author Ian.Huang 
	 * @date 2016年7月4日上午1:30:57
	 */
	@RequestMapping("delManEC")
	public void delManEC(HttpServletRequest request, HttpServletResponse res) {
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		JSONObject result = null;
		try {
			result = expressContractServiceImpl.deleteManEC(request, result);
		}  catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			result = new JSONObject();
			result.put("result_code", "FAILURE");
			result.put("result_content", "操作失败,失败原因:"+e.getMessage());	
		}
		try {
			out = res.getWriter();
			out.write(result.toString());
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e);
		}
		out.close();	
	}
	
	/**
	 * Title: delManECOther
	 * Description: 删除管理费规则(区别于运费管理费)
	 * @param request
	 * @param res
	 * @author jindong.lin
	 * @date 2017年9月4日
	 */
	@RequestMapping("delManECOther")
	public void delManECOther(HttpServletRequest request, HttpServletResponse res) {
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		JSONObject result = null;
		try {
			result = managementOtherLadderService.deleteManECOther(request, result);
		}  catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			result = new JSONObject();
			result.put("result_code", "FAILURE");
			result.put("result_content", "操作失败,失败原因:"+e.getMessage());	
		}
		try {
			out = res.getWriter();
			out.write(result.toString());
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e);
		}
		out.close();	
	}
	
	/**
	 * 
	 * @Description: TODO(保存管理费规则)
	 * @param request
	 * @param res
	 * @return: void  
	 * @author Ian.Huang 
	 * @date 2016年7月4日上午1:30:23
	 */
	@RequestMapping("saveManEC")
	public void saveManEC(HttpServletRequest request, HttpServletResponse res) {
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		JSONObject result = null;
		try {
			result = expressContractServiceImpl.saveManEC(request, result);
		}  catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			result = new JSONObject();
			result.put("result_code", "FAILURE");
			result.put("result_content", "操作失败,失败原因:"+e.getMessage());	
		}
		try {
			out = res.getWriter();
			out.write(result.toString());
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e);
		}
		out.close();	
	}
	
	/**
	 * Title: saveManECOther
	 * Description: 保存管理费规则(区别于 运费管理费)
	 * @param request
	 * @param res
	 * @author jindong.lin
	 * @date 2017年9月4日
	 */
	@RequestMapping("saveManECOther")
	public void saveManECOther(HttpServletRequest request, HttpServletResponse res) {
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		JSONObject result = null;
		try {
			result = managementOtherLadderService.saveManECOther(request, result);
		}  catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			result = new JSONObject();
			result.put("result_code", "FAILURE");
			result.put("result_content", "操作失败,失败原因:"+e.getMessage());	
		}
		try {
			out = res.getWriter();
			out.write(result.toString());
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e);
		}
		out.close();	
	}
	
	/**
	 * 
	 * @Description: TODO(加载特殊服务费)
	 * @param request
	 * @param res
	 * @return: void  
	 * @author Ian.Huang 
	 * @date 2016年7月1日上午10:25:28
	 */
	@RequestMapping("loadSSE")
	public void loadSSE(HttpServletRequest request, HttpServletResponse res) {
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		JSONObject result = null;
		try {
			result = expressContractServiceImpl.loadSSE(request, result);
		}  catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			result = new JSONObject();
			result.put("result_code", "FAILURE");
			result.put("result_content", "操作失败,失败原因:"+e.getMessage());	
		}
		try {
			out = res.getWriter();
			out.write(result.toString());
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e);
		}
		out.close();	
	}
	
	/**
	 * 
	 * @Description: TODO(删除保价费)
	 * @param request
	 * @param res
	 * @return: void  
	 * @author Ian.Huang 
	 * @date 2016年7月1日上午10:20:12
	 */
	@RequestMapping("delIEC")
	public void delInsuranceFeeRule(HttpServletRequest request, HttpServletResponse res) {
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		JSONObject result = null;
		try {
			result = expressContractServiceImpl.deleteInsuranceEC(request, result);
		}  catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			result = new JSONObject();
			result.put("result_code", "FAILURE");
			result.put("result_content", "操作失败,失败原因:"+e.getMessage());	
		}
		try {
			out = res.getWriter();
			out.write(result.toString());
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e);
		}
		out.close();	
	}
	
	/**
	 * 
	 * @Description: TODO(加载保价费规则)
	 * @param request
	 * @param res
	 * @return: void  
	 * @author Ian.Huang 
	 * @date 2016年6月29日上午10:30:33
	 */
	@RequestMapping("loadInsuranceEC")
	public void loadInsuranceFeeRule(HttpServletRequest request, HttpServletResponse res) {
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out= null;
		JSONObject result = null;
		try {
			result = expressContractServiceImpl.loadInsuranceEC(request, result);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			result = new JSONObject();
			result.put("result_code", "FAILURE");
			result.put("result_content", "操作失败,失败原因:"+e.getMessage());	
		}
		try {
			out = res.getWriter();
			out.write(result.toString());
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e);
		}
		out.close();	
	}
	
	/**
	 * 
	 * @Description: TODO(保存保价费)
	 * @param request
	 * @param res
	 * @return: void  
	 * @author Ian.Huang 
	 * @date 2016年6月28日下午3:20:44
	 */
	@RequestMapping("saveIEC")
	public void saveIFRule(HttpServletRequest request, HttpServletResponse res) {
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		JSONObject result = null;
		try {
			result = expressContractServiceImpl.saveiEC(request, result);
		}  catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			result = new JSONObject();
			result.put("result_code", "FAILURE");
			result.put("result_content", "操作失败,失败原因:"+e.getMessage());	
		}
		try {
			out = res.getWriter();
			out.write(result.toString());
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e);
		}
		out.close();	
	}
	
	/**
	 * 
	 * @Description: TODO(删除一条总运费折扣)
	 * @param request
	 * @param res
	 * @return: void  
	 * @author Ian.Huang 
	 * @date 2016年6月28日下午3:18:31
	 */
	@RequestMapping("delTFD")
	public void delTotalFreightDiscount(HttpServletRequest request, HttpServletResponse res) {
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		JSONObject result = null;
		try {
			result = expressContractServiceImpl.deleteTotalFreightDiscount(request, result);
		}  catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			result = new JSONObject();
			result.put("result_code", "FAILURE");
			result.put("result_content", "操作失败,失败原因:"+e.getMessage());	
		}
		try {
			out = res.getWriter();
			out.write(result.toString());
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e);
		}
		out.close();	
	}
	
	/**
	 * 
	 * @Description: TODO(加载总运费折扣)
	 * @param request
	 * @param res
	 * @return: void  
	 * @author Ian.Huang 
	 * @date 2016年6月28日上午1:29:30
	 */
	@RequestMapping("loadTotalFreightDiscount")
	public void loadTotalFreightDiscount(HttpServletRequest request, HttpServletResponse res) {
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out= null;
		JSONObject result = null;
		try {
			result = expressContractServiceImpl.loadTotalFreightDiscount(request, result);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			result = new JSONObject();
			result.put("result_code", "FAILURE");
			result.put("result_content", "操作失败,失败原因:"+e.getMessage());	
		}
		try {
			out = res.getWriter();
			out.write(result.toString());
			out.flush();
			
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e);
		}
		out.close();	
	}
	
	/**
	 * 
	 * @Description: TODO(新增总费用折扣)
	 * @param request
	 * @param res
	 * @return: void  
	 * @author Ian.Huang 
	 * @date 2016年6月27日下午10:28:35
	 */
	@RequestMapping("saveTotalFreightDiscount")
	public void saveTotalFreightDiscount(HttpServletRequest request, HttpServletResponse res) {
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		JSONObject result = null;
		try {
			result = expressContractServiceImpl.saveTotalFreightDiscount(request, result);
		}  catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			result = new JSONObject();
			result.put("result_code", "FAILURE");
			result.put("result_content", "操作失败,失败原因:"+e.getMessage());	
		}
		try {
			out = res.getWriter();
			out.write(result.toString());
			out.flush();
			
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e);
		}
		out.close();
	}
	
	/**
	 * 
	 * @Description: TODO(删除一条计费公式)
	 * @param request
	 * @param res
	 * @return: void  
	 * @author Ian.Huang 
	 * @date 2016年6月26日下午11:24:14
	 */
	@RequestMapping("/delPricingFormula")
	public void delPricingFormula(HttpServletRequest request, HttpServletResponse res) {
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		JSONObject result = null;
		try {
			result = expressContractServiceImpl.deletePricingFormula(request, result);
		}  catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			result = new JSONObject();
			result.put("result_code", "FAILURE");
			result.put("result_content", "操作失败,失败原因:"+e.getMessage());	
		}
		try {
			out = res.getWriter();
			out.write(result.toString());
			out.flush();
			
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e);
		}
		out.close();	
	}
	
	/**
	 * 
	 * @Description: TODO(根据ID获取一条计费公式)
	 * @param request
	 * @param res
	 * @return: void  
	 * @author Ian.Huang 
	 * @date 2016年6月26日下午10:16:33
	 */
	@RequestMapping("/getPricingFormula")
	public void getPricingFormula(HttpServletRequest request, HttpServletResponse res) {
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		JSONObject result = null;
		// 封装参数
		Map<String, Object> param = getParamMap(request);
		try {
			result = expressContractServiceImpl.getPricingFormula(param, result);
		}  catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			result = new JSONObject();
			result.put("result_code", "FAILURE");
			result.put("result_content", "操作失败,失败原因:"+e.getMessage());	
		}
		
		try {
			out = res.getWriter();
			out.write(result.toString());
			out.flush();
			
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e);
		}
		
		out.close();	
	}
	
	/**
	 * 
	 * @Description: TODO(新增/更新一条计费公式)
	 * @param request
	 * @param res
	 * @return: void  
	 * @author Ian.Huang 
	 * @date 2016年6月26日上午9:55:22
	 */
	@RequestMapping("/savePricingFormula")
	public void savePricingFormula(HttpServletRequest request, HttpServletResponse res) {
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		JSONObject result = null;
		try {
			result = expressContractServiceImpl.savePricingFormula(request, result);
		}  catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			result = new JSONObject();
			result.put("result_code", "FAILURE");
			result.put("result_content", "操作失败,失败原因:"+e.getMessage());	
		}
		
		try {
			out = res.getWriter();
			out.write(result.toString());
			out.flush();
			
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e);
		}
		
		out.close();	
		
	}
	
	/**
	 * 
	 * @Description: TODO(删除一条计半抛条件记录后刷新数据)
	 * @param request
	 * @param res
	 * @return: void  
	 * @author Ian.Huang 
	 * @date 2016年6月24日下午1:10:05
	 */
	@RequestMapping("/delJBPCondition")
	public void delJBPCondition(HttpServletRequest request, HttpServletResponse res){
		
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out= null;
		JSONObject result = null;
		try {
			result = expressContractServiceImpl.deleteJBPConditon(request, result);
		}  catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			result = new JSONObject();
			result.put("result_code", "FAILURE");
			result.put("result_content", "操作失败,失败原因:"+e.getMessage());	
		}
		try {
			out = res.getWriter();
			out.write(result.toString());
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e);
		}
		out.close();	
	}
	@RequestMapping("/delJQPCondition")
	public void delJQPCondition(HttpServletRequest request, HttpServletResponse res){
		
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out= null;
		JSONObject result = null;
		try {
			result = expressContractServiceImpl.deleteJQPConditon(request, result);
		}  catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			result = new JSONObject();
			result.put("result_code", "FAILURE");
			result.put("result_content", "操作失败,失败原因:"+e.getMessage());	
		}
		try {
			out = res.getWriter();
			out.write(result.toString());
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e);
		}
		out.close();	
	}
	
	/**
	 * 
	 * @Description: TODO(查询相应合同ID下的所有计半抛条件)
	 * @param request
	 * @param res
	 * @return: void  
	 * @author Ian.Huang 
	 * @date 2016年6月24日上午9:23:14
	 */
	@RequestMapping("/loadFreight")
	public void loadFreight(HttpServletRequest request, HttpServletResponse res){
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out= null;
		JSONObject result = null;
		try {
			result = expressContractServiceImpl.loadFreight(request, result);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			result = new JSONObject();
			result.put("result_code", "FAILURE");
			result.put("result_content", "操作失败,失败原因:"+e.getMessage());	
		}
		try {
			out = res.getWriter();
			out.write(result.toString());
			out.flush();
			
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e);
		}
		out.close();	
	}
	
	/**
	 * 
	 * @Description: TODO(新增计半抛条件)
	 * @param request
	 * @param res
	 * @return: void  
	 * @author Ian.Huang 
	 * @date 2016年6月22日下午5:53:47
	 */
	@RequestMapping("/addJBPCondition")
	public void addJBPCondition(HttpServletRequest request, HttpServletResponse res){
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out= null;
		JSONObject result = null;
		try {
			result = expressContractServiceImpl.addJBPConditon(request, result);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			result = new JSONObject();
			result.put("result_code", "FAILURE");
			result.put("result_content", "操作失败,失败原因:"+e.getMessage());	
		}
		try {
			out = res.getWriter();
			out.write(result.toString());
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e);
		}
		out.close();	
	}
	
	@RequestMapping("/addJQPCondition")
	public void addJQPCondition(HttpServletRequest request, HttpServletResponse res){
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out= null;
		JSONObject result = null;
		try {
			result = expressContractServiceImpl.addJQPConditon(request, result);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			result = new JSONObject();
			result.put("result_code", "FAILURE");
			result.put("result_content", "操作失败,失败原因:"+e.getMessage());	
		}
		try {
			out = res.getWriter();
			out.write(result.toString());
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e);
		}
		out.close();	
	}
	
	/**
	 * 
	 * @Description: TODO(加载合同信息)
	 * @param map
	 * @param request
	 * @return
	 * @return: String  
	 * @author Ian.Huang 
	 * @date 2016年6月25日下午10:41:24
	 */
	@RequestMapping("/form")
	public String getEC(ModelMap map, HttpServletRequest request){
		try {
			map = expressContractServiceImpl.getEC(map, request);
		} catch (NumberFormatException e) {
			logger.error(e);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return "/lmis/express_contract_form";
	}
	
	/**
	 * 
	 * @Description: TODO(配置快递合同)
	 * @param request
	 * @param res
	 * @return: void  
	 * @author Ian.Huang 
	 * @date 2016年6月29日下午7:56:51
	 */
	@RequestMapping("/configureEC")
	public void configureEC(HttpServletRequest request, HttpServletResponse res){
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out= null;
		JSONObject result = null;
		try {
			result = expressContractServiceImpl.saveECC(request, result);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			result = new JSONObject();
			result.put("result_code", "FAILURE");
			result.put("result_content", "操作失败,失败原因:"+e.getMessage());	
		}
		try {
			out = res.getWriter();
			out.write(result.toString());
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e);
		}
		out.close();	
	}
	
	/**
	 * 
	 * @Description: TODO(保存快递合同主表数据)
	 * @param request
	 * @param res
	 * @return: void  
	 * @author Ian.Huang 
	 * @date 2016年6月22日下午5:52:56
	 */
	@RequestMapping("/saveECM")
	public void saveECM(HttpServletRequest request, HttpServletResponse res){
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out= null;
		JSONObject result = null;
		try{
			result = expressContractServiceImpl.saveECM(request, result);
		} catch(Exception e) {
			e.printStackTrace();
			logger.error(e);
			result = new JSONObject();
			result.put("result_code", "FAILURE");
			result.put("result_content", "操作失败,失败原因:"+e.getMessage());	
		}
		try {
			out = res.getWriter();
			out.write(result.toString());
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e);
		}
		out.close();	
	}
	
	@RequestMapping("toManagementFeeOther")
	public String toManagementFeeOther(String cb_id,String management_fee_type,
			HttpServletRequest request, HttpServletResponse response){
		request.setAttribute("cb_id", cb_id);
		request.setAttribute("management_fee_type", management_fee_type);
		return "lmis/express_contract/managementFeeOther";
	}
}
