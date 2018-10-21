package com.bt.lmis.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSONObject;
import com.bt.OSinfo;
import com.bt.lmis.code.BaseController;
import com.bt.lmis.model.Employee;
import com.bt.lmis.service.RawDataService;
import com.bt.utils.CommonUtils;
import com.bt.utils.FileUtil;
import com.bt.utils.SessionUtils;

/**
 * @Title:RawDataController
 * @Description: TODO(原始数据上传)
 * @author Ian.Huang 
 * @date 2016年9月22日上午9:47:42
 */
@Controller
@RequestMapping("/control/rawDataController")
public class RawDataController  extends BaseController{

	private static final Logger logger = Logger.getLogger(RawDataController.class);
	
	@Resource(name = "rawDataServiceImpl")
	private RawDataService<T> rawDataServiceImpl;
	
	@RequestMapping("invoke")
	public void invoke(HttpServletRequest request, HttpServletResponse res) {
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		JSONObject result = null;
		try {
			result = rawDataServiceImpl.invoke(request, result);
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
	
	@RequestMapping("del")
	public void del(HttpServletRequest request, HttpServletResponse res) {
		res.setContentType("text/xml; charset=utf-8");
		res.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		JSONObject result = null;
		try {
			result = rawDataServiceImpl.del(request, result);
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
	
	@RequestMapping("/tolist")
	public String tolist(ModelMap map, HttpServletRequest request){
		try {
			map = rawDataServiceImpl.searchRawDataByBatId(map, request);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "/lmis/rawDataUpload/rawData_list";
	}
	
	@ResponseBody
	@RequestMapping(value="/upload.do")
	public JSONObject upload(@RequestParam("uploadFile")MultipartFile file, ModelMap map, HttpServletRequest request, HttpServletResponse res, RedirectAttributes ra) throws IOException{
		//　获取当前操作用户
		Employee employee = SessionUtils.getEMP(request);
		// 返回值
		JSONObject result = new JSONObject();
		// 将上传文件写入本地
		//　加同步锁
		synchronized(this){
			// 文件不为空
			if(!file.isEmpty()){
				try {
					// 文件保存路径
					String filePath= CommonUtils.getAllMessage("config", "BALANCE_UPLOAD_RAWDATA_" + OSinfo.getOSname())+ new StringBuffer(file.getOriginalFilename())
					.insert(file.getOriginalFilename().indexOf("."), "_"+ employee.getEmployee_number()+ "_"+ new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
					// 校验本地文件是否已存在
					// 如果有重名文件存在，就删除文件  
					// 这个对象对应的硬盘必须删不能存在，如果已经存在则会抛出IOException异常
					FileUtil.isExistFile(filePath);
					//转存文件，写入硬盘 
					//这个本质还是一样的打开流传文件， 需要注意 file对应的硬盘中的文件不能存在 需要删除， 否则会抛出文件已经存在且不能删除异常
					file.transferTo(new File(filePath));
					// 保存EXCEL数据
					result = rawDataServiceImpl.saveData(result, filePath, request.getParameter("rawDataType"));
					// 分类型插入数据
//					if(type.equals("opration")){
//						
//					}else if(type.equals("valueAddedService")){
//						
//					}else if(type.equals("detailPurchase")){
//						
//					}else if(type.equals("supplies")){
//						
//					}else if(type.equals("storage")){
//						
//					}else if(type.equals("express")){
//						// 封装原始数据对象插入数据库
//						result = rawDataServiceImpl.uploadExpressWaybillExcel(request, result, workbook);
//					}else if(type.equals("logistics")){
//						// 封装原始数据对象插入数据库
//						result = rawDataServiceImpl.uploadLogisticsWaybillExcel(request, result, workbook);
//					}
				} catch (Exception e) {
					 e.printStackTrace();
					 logger.error(e);
					 // upload file failure-文件上传失败
					 result.put("result_code", "ULF");
					 if(e instanceof NoSuchMethodException) {
						 result.put("result_content", "上传文件与所选类型不匹配或上传文件中标题字段有误！");
						 
					 } else {
						 result.put("result_content", CommonUtils.getExceptionStack(e));
						 
					 }
					 
			    }
				
			} else {
				// file is empty-文件为空
				result.put("result_code", "FIE");
				
			}
			
		}
		return result;
		
	}
	
	@RequestMapping("/toForm")
	public String toForm(ModelMap map, HttpServletRequest request){
		try {
//			map = rawDataServiceImpl.searchRawDataByBatId(map, request);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "/lmis/rawDataUpload/rawDataUpload_form";
	}
}
