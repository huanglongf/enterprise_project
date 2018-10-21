package com.bt.radar.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSONObject;
import com.bt.lmis.code.BaseController;
import com.bt.lmis.model.Employee;
import com.bt.radar.dao.ExpressinfoMasterInputlistMapper;
import com.bt.radar.model.ExpressinfoMasterInputlist;
import com.bt.radar.service.ExpressinfoMasterInputlistService;
import com.bt.radar.service.ExpressinfoMasterService;
import com.bt.utils.SessionUtils;
/**
 * 运单导入明细表控制器
 *
 */
@Controller
@RequestMapping("/control/radar/expressinfoMasterInputlistController")
public class ExpressinfoMasterInputlistController extends BaseController {

	private static final Logger logger = Logger.getLogger(ExpressinfoMasterInputlistController.class);
	
	/**
	 * 运单导入明细表服务类
	 */
	@Resource(name = "expressinfoMasterInputlistServiceImpl")
	private ExpressinfoMasterInputlistService<ExpressinfoMasterInputlist> expressinfoMasterInputlistService;
	@Resource(name = "expressinfoMasterServiceImpl")
	private ExpressinfoMasterService<ExpressinfoMasterInputlist> expressinfoMasterService;
	@Autowired
	private ExpressinfoMasterInputlistMapper expressinfoMasterInputlistMapper;
	
	@ResponseBody
	@RequestMapping(value="/uploadWaybill.do")
	public JSONObject upload(@RequestParam("file") MultipartFile file,ModelMap map, HttpServletRequest req,HttpServletResponse res,RedirectAttributes ra) throws IOException{
		 Employee user= SessionUtils.getEMP(req);
		 String UUID="";
		 String log= "";
		 String trbFilePath="";
		 JSONObject obj=new JSONObject();
		 String str = (new SimpleDateFormat("yyyyMMdd-HHmmssSSS")).format(new Date()); 
		 Map param =new HashMap();
		 synchronized(this){
		if (!file.isEmpty()) {
	            try {
	                // 文件保存路径
	                File  ff=new File(req.getSession().getServletContext().getRealPath("/") + "upload");
	                if(!ff.exists()){
	                	ff.mkdir();
	                }
	                String filePath = req.getSession().getServletContext().getRealPath("/") + "upload/" +str+ file.getOriginalFilename();  
	                // 转存文件
	                file.transferTo(new File(filePath));
	                param.put("filePath", filePath);
	                param.put("createBy", SessionUtils.getEMP(req).getEmployee_number());
	                UUID          =   expressinfoMasterService.uploadWaybill(param);  
	              //trbFilePath   =   expressinfoMasterService.getTrbFilePath(UUID);
	                	/*if("".equals(trbFilePath)){
	                		obj.put("msg", "上传成功");
	                		obj.put("code", 1);
	                	}else{
	                		obj.put("code", 0);
	                		obj.put("path",trbFilePath );
	                	}	*/				
	                /*FileInputStream is = new FileInputStream(filePath);	//文件流
	         			Workbook workbook = WorkbookFactory.create(is); 		//这种方式 Excel 2003/2007/2010 都是可以处理的
	         			int sheetCount = workbook.getNumberOfSheets(); 			//Sheet的数量 	 
                    	 is.close();*/
	                obj.put("code", 1);	 
	            } catch (Exception e) {  
	              e.printStackTrace();  
	              obj.put("msg", "上传失败");
	              obj.put("code", 0);
	              logger.error(e);
	            }
	        }else{
	        	obj.put("code", 0);
	        }
		 }
		return  obj;
	}
	
	
	@ResponseBody
	@RequestMapping(value="/uploadInWaybill.do")
	public JSONObject uploadInWaybill(ModelMap map, HttpServletRequest req,HttpServletResponse res,RedirectAttributes ra) throws IOException{
		//校验批次好，大于等于30个的时候。不允许用户转换。
		 JSONObject obj=new JSONObject();
		int num=expressinfoMasterInputlistMapper.count(null);
		if(num>=30){
			obj.put("code", 2);
			return obj;
		}
		 Employee user= SessionUtils.getEMP(req);
		 String UUID=req.getParameter("bat_id").toString();
		 String logPath= "";
		 String str ="uploadExpress"+ (new SimpleDateFormat("yyyyMMdd-HHmmssSSS")).format(new Date()); 
		 Map param =new HashMap();
		 param.put("bat_id", UUID);
		 synchronized(this){
			 try{
				 param.put("flag", 2);
				 expressinfoMasterService.updateStatus(param);
				 expressinfoMasterService.uploadTransferWaybill(param); 
				 logPath   =   expressinfoMasterService.getTrbFilePath(UUID);
				 param.put("flag", 1);
				 expressinfoMasterService.updateStatus(param);
				 if("".equals(logPath)){ 
					 obj.put("code", 1);	 
				 }else{
					 obj.put("code", 0);
				 }
				 
			 }catch(Exception e){
				 obj.put("code", 0);	 
			 }
		 }
		return  obj;
	}
	
	@ResponseBody
	@RequestMapping(value="/delete.do")
	public JSONObject delete(ModelMap map, HttpServletRequest req,HttpServletResponse res,RedirectAttributes ra) throws IOException{
		 Employee user= SessionUtils.getEMP(req);
		 
		 String UUID=req.getParameter("bat_id").toString();
		List<String> list= Arrays.asList(UUID.split(";"));
		 String logPath= "";
		 JSONObject obj=new JSONObject();
		 String str ="uploadExpress"+ (new SimpleDateFormat("yyyyMMdd-HHmmssSSS")).format(new Date()); 
		 Map param =new HashMap();
		 param.put("bat_id", UUID);
		 synchronized(this){
			 try{
			 expressinfoMasterInputlistService.del(list);
			 obj.put("code", 1);
			 }catch(Exception e){
				e.printStackTrace();
				 obj.put("code", 0);	 
			 }
		 }
		return  obj;
	}
	
	
}
