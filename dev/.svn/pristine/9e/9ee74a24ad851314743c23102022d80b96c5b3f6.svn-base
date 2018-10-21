package com.bt.lmis.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Callable;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.bt.OSinfo;
import com.bt.lmis.code.BaseController;
import com.bt.lmis.controller.form.FileImportTaskQueryParam;
import com.bt.lmis.controller.form.FileTempleteQueryParam;
import com.bt.lmis.model.Employee;
import com.bt.lmis.model.FileImportTask;
import com.bt.lmis.page.PageView;
import com.bt.lmis.page.QueryResult;
import com.bt.lmis.service.FileImportTaskService;
import com.bt.lmis.service.FileTempleteService;
import com.bt.utils.BaseConst;
import com.bt.utils.CommonUtils;
import com.bt.utils.ExcelExportUtil;
import com.bt.utils.SessionUtils;
import com.bt.utils.XLSXCovertCSVReader;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

/**
* Title: ImportTaskController
* Description: 文件导入导出统一web接口
* Company: baozun
* @author jindong.lin
* @date 2018年1月30日
*/
@Controller
@RequestMapping("/control/importTaskController")
public class ImportTaskController extends BaseController {
	
	private static final Logger logger = Logger.getLogger(ImportTaskController.class);

	@Resource(name = "fileImportTaskServiceImpl")
	private FileImportTaskService fileImportTaskService;
	
	@Resource(name = "fileTempleteServiceImpl")
	private FileTempleteService fileTempleteService;

	@Resource(name = "importTaskFutureCallback")//回调服务类，提供回调方法
	private FutureCallback<String> importTaskFutureCallback;
	

	/**
	 * Title: getList
	 * Description: 
	 * @param queryParam
	 * @param map
	 * @param request
	 * @return
	 * @author jindong.lin
	 * @date 2018年1月25日
	 */
	@RequestMapping("importTaskList")
	public String getList(FileImportTaskQueryParam queryParam,ModelMap map, HttpServletRequest request){
		try{

			if(queryParam == null || (StringUtils.isBlank(queryParam.getBusinessType()) 
					&& StringUtils.isBlank(queryParam.getBusinessGroup()))){
				logger.error("参数异常,businessType、businessGroup参数必须有一个。");
				return null;
			}

			FileTempleteQueryParam fileTemplete = new FileTempleteQueryParam();
			fileTemplete.setBusinessType(queryParam.getBusinessType());
			fileTemplete.setBusinessGroup(queryParam.getBusinessGroup());
			List<FileTempleteQueryParam> templeteList = fileTempleteService.findByQueryParam(fileTemplete);
			if(templeteList == null || templeteList.size() == 0){
				logger.error("未找到" + queryParam.getBusinessType() +"对应模板");
				return null;
			}
			
			//根据条件查询合同集合
			PageView<FileImportTaskQueryParam> pageView = new PageView<FileImportTaskQueryParam>(
					queryParam.getPageSize()==0?
							BaseConst.pageSize:queryParam.getPageSize(), queryParam.getPage());
			queryParam.setFirstResult(pageView.getFirstResult());
			queryParam.setMaxResult(pageView.getMaxresult());
			QueryResult<FileImportTaskQueryParam> qr = fileImportTaskService.findAll(queryParam);
			pageView.setQueryResult(qr, queryParam.getPage());
			request.setAttribute("pageView", pageView);
			request.setAttribute("queryParam", queryParam);
			request.setAttribute("templeteList", templeteList);
			request.setAttribute("fileTemplete", templeteList.get(0));
		}catch(Exception e){
			logger.error(e);
			e.printStackTrace();
		}
		return "/lmis/import_task/import_task_list";
	}
	
	/**
	 * Title: downloadTemplete
	 * Description: 动态生成模版文件统一接口
	 * @param downloadTemplete
	 * @param request
	 * @param resp
	 * @return
	 * @author jindong.lin
	 * @date 2018年1月25日
	 */
	@RequestMapping("downloadTemplete")
	public String downloadTemplete(String businessType,HttpServletRequest request,
								   HttpServletResponse resp){
		try{
			if (StringUtils.isNotBlank(businessType)) {

				FileTempleteQueryParam fileTemplete = new FileTempleteQueryParam();
				fileTemplete.setBusinessType(businessType);
				FileTempleteQueryParam newFileTemplete = fileTempleteService
						.getByQueryParam(fileTemplete);
				if(newFileTemplete == null){
					logger.error("未找到" + businessType +"对应模板");
					return null;
				}
				List<String> titleList = JSONObject.parseArray(newFileTemplete.getTempleteTitle(), 
						String.class);
				String[] headNames = titleList.toArray(new String[titleList.size()]);
				String fileName = newFileTemplete.getTempleteName() + ".xlsx";
				
				ExcelExportUtil.exportExcelData(new ArrayList<Map<String,Object>>(), 
						headNames, "sheet1", ExcelExportUtil.OFFICE_EXCEL_2010_POSTFIX,
						fileName,resp);
			}
		}catch(Exception e){
			logger.error(e);
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Title: getTempleteMode
	 * Description: 获取模板信息
	 * @param businessType
	 * @param request
	 * @param resp
	 * @return
	 * @author jindong.lin
	 * @date 2018年2月6日
	 */
	@RequestMapping("getTempleteMode")
	@ResponseBody
	public String getTempleteMode(String businessType,HttpServletRequest request,
								   HttpServletResponse resp){
		try{
			if (StringUtils.isNotBlank(businessType)) {
				FileTempleteQueryParam fileTemplete = new FileTempleteQueryParam();
				fileTemplete.setBusinessType(businessType);
				FileTempleteQueryParam newFileTemplete = fileTempleteService
						.getByQueryParam(fileTemplete);
				if(newFileTemplete != null){
					return JSONObject.toJSONString(newFileTemplete).toString();
				}
			}
		}catch(Exception e){
			logger.error(e);
			e.printStackTrace();
		}
		return null;
	}
	

	/**
	 * Title: importExcel
	 * Description: 上传处理
	 * @param file
	 * @param fileTemplete
	 * @param request
	 * @return
	 * @author jindong.lin
	 * @date 2018年1月26日
	 */
	@RequestMapping("expressCoverImportExcel")
	@ResponseBody
	public String importExcel(@RequestParam(value = "file", required = false) MultipartFile file, 
			FileTempleteQueryParam fileTemplete,HttpServletRequest request){
		Map<String,String>param=new HashMap<String,String>();
		if(fileTemplete != null && StringUtils.isNotBlank(fileTemplete.getBusinessType())){
			try{
				final FileTempleteQueryParam resultTemplete = fileTempleteService.getByQueryParam(fileTemplete);
				int taskWaitCount = fileImportTaskService
						.checkTaskCount();
				if(taskWaitCount > 10){
		    		param.put("out_result","0");
		    		param.put("out_result_reason","文件上传任务等待数量已达上限,请稍后再试!");
		    		return JSONObject.toJSONString(param).toString();
				}
				if(resultTemplete != null){
					//这里执行文件上传逻辑
					String fileName = file.getOriginalFilename();
					String prefix = fileName.split("\\.")[fileName.split("\\.").length-1];
		    		param.put("out_result","0");
		    		param.put("out_result_reason","系统错误");
					// 判断文件是否为空   
			        if (!file.isEmpty()) {
			        	if(prefix.equals("xlsx")){
			        		// 文件保存路径
			        		String uuid = UUID.randomUUID().toString().replace("-", "");
			    			String filePath= CommonUtils.getAllMessage("config", "BALANCE_UPLOAD_RAWDATA_"+
			    								OSinfo.getOSname()) + uuid + "/";
			    			File baseFile = new File(filePath);
			    			baseFile.mkdirs();
			    			filePath = filePath + fileName;
			    			baseFile = new File(filePath);
			                // 转存文件
			                file.transferTo(baseFile);
			                //这里设置列长
			                final List<String[]> list= XLSXCovertCSVReader.readerExcel(filePath, null, 
			                		resultTemplete.getTitleLength());
			                if(list==null || list.size()==0 || list.size()==1){
					    		param.put("out_result","0");
					    		param.put("out_result_reason","文件内容有误!");
					    		return JSONObject.toJSONString(param).toString();
			                }
			                //校验表头
			                final List<String> titleList = JSONObject.parseArray(
			                		resultTemplete.getTempleteTitle(), String.class);
			                for (int i = 0; i < list.get(0).length; i++) {
			                	if(list.get(0)[i] ==null || 
			                			!titleList.get(i).equals(list.get(0)[i])){
						    		param.put("out_result","0");
						    		param.put("out_result_reason","上传文件表头格式与约定不符,请严格按照约定修改!");
						    		return JSONObject.toJSONString(param).toString();
			                	}
							}
		    				list.remove(0);
		    	    		final Employee user = SessionUtils.getEMP(request);
		    	    		final FileImportTask fileImportTask = new FileImportTask();
		    	    		fileImportTask.setId(uuid);//uuid约定使用用户文件uuid,后面加入应用重启补偿机制
		    	    		fileImportTask.setFileName(fileName);
		    	    		fileImportTask.setTotalCount(list.size());
		    	    		fileImportTask.setBusinessType(fileTemplete.getBusinessType());
		    	    		fileImportTask.setBusinessGroup(fileTemplete.getBusinessGroup());
		    	    		fileImportTask.setCreateTime(new Date());
		    	    		fileImportTask.setCreateUser(user.getId().toString());
		    	    		fileImportTask.setUpdateTime(new Date());
		    	    		fileImportTask.setUpdateUser(user.getId().toString());
		    	    		fileImportTask.setStatus("2");//2等待开始
		    	    		int r = fileImportTaskService.insertSelective(fileImportTask);
		    	    		
		    	    		if(r > 0){
			    	    		//异步处理(使用公共线程池)
			    	    		ListenableFuture<String> listenableFuture = BaseConst
			    	    				.commonTaskExecutorService.submit(new Callable<String>() {
									@Override
									public String call() throws Exception {
										FileImportTask waitImportTask = 
												fileImportTaskService.selectByPrimaryKey(fileImportTask.getId());
										if(waitImportTask != null && waitImportTask.getStatus().equals("2")){
											//打标识正在进行中
											logger.error("导入任务improttask ID:" + fileImportTask.getId() 
												+ ",导入任务正在处理中...");
											fileImportTask.setStatus("-1");
											fileImportTask.setUpdateTime(new Date());
											fileImportTaskService.updateByPrimaryKeySelective(fileImportTask);
											//主要任务改变这里
											fileImportTaskService.expressCoverImportExcel(list,fileImportTask,user,
													titleList,resultTemplete.getTempleteName());
											return "导入任务improttask ID:" + fileImportTask.getId() + ",导入任务处理完毕";
										} else {
											return "导入任务improttask ID:" + fileImportTask.getId() + ",导入任务已取消";
										}
									}
								});
								//应用回调
								Futures.addCallback(listenableFuture, importTaskFutureCallback);	
					    		param.put("out_result","1");
					    		param.put("out_result_reason","导入任务已创建");
		    	    		} else {
					    		param.put("out_result","0");
					    		param.put("out_result_reason","导入任务创建失败");
		    	    		}
			        	}else{
				    		param.put("out_result","0");
				    		param.put("out_result_reason","文件类型必须为[.xlsx],您上传的文件类型为[."+prefix+"]!");
			        	}
			        } else {
			    		param.put("out_result","0");
			    		param.put("out_result_reason","上传文件不能为空");
			        }
				} else {
					param.put("out_result","0");
					param.put("out_result_reason","异常");
				}
				
			}catch(Exception e){
				e.printStackTrace();
				param.put("out_result","0");
				param.put("out_result_reason","异常");
			}
		} else {
			param.put("out_result","0");
			param.put("out_result_reason","参数为空");
		}
    	return JSONObject.toJSONString(param).toString();
	}
	
	
	/**
	 * Title: convertReturnImportExcel
	 * Description: 退换货导入任务处理
	 * @param file
	 * @param fileTemplete
	 * @param request
	 * @return
	 * @author jindong.lin
	 * @date 2018年1月29日
	 */
	@RequestMapping("convertReturnImportExcel")
	@ResponseBody
	public String convertReturnImportExcel(@RequestParam(value = "file", required = false) MultipartFile file, 
			FileTempleteQueryParam fileTemplete,HttpServletRequest request){
		Map<String,String>param=new HashMap<String,String>();
		if(fileTemplete != null && StringUtils.isNotBlank(fileTemplete.getBusinessType())){
			try{
				final FileTempleteQueryParam resultTemplete = fileTempleteService.getByQueryParam(fileTemplete);
				int taskWaitCount = fileImportTaskService
						.checkTaskCount();
				if(taskWaitCount > 10){
		    		param.put("out_result","0");
		    		param.put("out_result_reason","文件上传任务等待数量已达上限,请稍后再试!");
		    		return JSONObject.toJSONString(param).toString();
				}
				if(resultTemplete != null){
					//这里执行文件上传逻辑
					String fileName = file.getOriginalFilename();
					String prefix = fileName.split("\\.")[fileName.split("\\.").length-1];
		    		param.put("out_result","0");
		    		param.put("out_result_reason","系统错误");
					// 判断文件是否为空   
			        if (!file.isEmpty()) {
			        	if(prefix.equals("xlsx")){
			        		// 文件保存路径
			        		String uuid = UUID.randomUUID().toString().replace("-", "");
			    			String filePath= CommonUtils.getAllMessage("config", "BALANCE_UPLOAD_RAWDATA_"+
			    								OSinfo.getOSname()) + uuid + "/";
			    			File baseFile = new File(filePath);
			    			baseFile.mkdirs();
			    			filePath = filePath + fileName;
			    			baseFile = new File(filePath);
			                // 转存文件
			                file.transferTo(baseFile);
			                //这里设置列长
			                final List<String[]> list= XLSXCovertCSVReader.readerExcel(filePath, null, 
			                		resultTemplete.getTitleLength());
			                if(list==null || list.size()==0 || list.size()==1){
					    		param.put("out_result","0");
					    		param.put("out_result_reason","文件内容有误!");
					    		return JSONObject.toJSONString(param).toString();
			                }
			                //校验表头
			                final List<String> titleList = JSONObject.parseArray(
			                		resultTemplete.getTempleteTitle(), String.class);
			                for (int i = 0; i < list.get(0).length; i++) {
			                	if(list.get(0)[i] ==null || 
			                			!titleList.get(i).equals(list.get(0)[i])){
						    		param.put("out_result","0");
						    		param.put("out_result_reason","上传文件表头格式与约定不符,请严格按照约定修改!");
						    		return JSONObject.toJSONString(param).toString();
			                	}
							}
		    				list.remove(0);
		    	    		final Employee user = SessionUtils.getEMP(request);
		    	    		final FileImportTask fileImportTask = new FileImportTask();
		    	    		fileImportTask.setId(uuid);//uuid约定使用用户文件uuid,后面加入应用重启补偿机制
		    	    		fileImportTask.setFileName(fileName);
		    	    		fileImportTask.setTotalCount(list.size());
		    	    		fileImportTask.setBusinessType(fileTemplete.getBusinessType());
		    	    		fileImportTask.setBusinessGroup(fileTemplete.getBusinessGroup());
		    	    		fileImportTask.setCreateTime(new Date());
		    	    		fileImportTask.setCreateUser(user.getId().toString());
		    	    		fileImportTask.setUpdateTime(new Date());
		    	    		fileImportTask.setUpdateUser(user.getId().toString());
		    	    		fileImportTask.setStatus("2");//2等待开始
		    	    		int r = fileImportTaskService.insertSelective(fileImportTask);
		    	    		
		    	    		if(r > 0){
			    	    		//异步处理(使用公共线程池)
			    	    		ListenableFuture<String> listenableFuture = BaseConst
			    	    				.commonTaskExecutorService.submit(new Callable<String>() {
									@Override
									public String call() throws Exception {
										FileImportTask waitImportTask = 
												fileImportTaskService.selectByPrimaryKey(fileImportTask.getId());
										if(waitImportTask != null && waitImportTask.getStatus().equals("2")){
											//打标识正在进行中
											logger.error("导入任务improttask ID:" + fileImportTask.getId() 
												+ ",导入任务正在处理中...");
											fileImportTask.setStatus("-1");
											fileImportTask.setUpdateTime(new Date());
											fileImportTaskService.updateByPrimaryKeySelective(fileImportTask);
											//主要任务改变这里
											fileImportTaskService.convertReturnExcel(list,fileImportTask,user);
											return "导入任务improttask ID:" + fileImportTask.getId() + ",导入任务处理完毕";
										} else {
											return "导入任务improttask ID:" + fileImportTask.getId() + ",导入任务已取消";
										}
									}
								});
								//应用回调
								Futures.addCallback(listenableFuture, importTaskFutureCallback);	
					    		param.put("out_result","1");
					    		param.put("out_result_reason","导入任务已创建");
		    	    		} else {
					    		param.put("out_result","0");
					    		param.put("out_result_reason","导入任务创建失败");
		    	    		}
			        	}else{
				    		param.put("out_result","0");
				    		param.put("out_result_reason","文件类型必须为[.xlsx],您上传的文件类型为[."+prefix+"]!");
			        	}
			        } else {
			    		param.put("out_result","0");
			    		param.put("out_result_reason","上传文件不能为空");
			        }
				} else {
					param.put("out_result","0");
					param.put("out_result_reason","异常");
				}
				
			}catch(Exception e){
				e.printStackTrace();
				param.put("out_result","0");
				param.put("out_result_reason","异常");
			}
		} else {
			param.put("out_result","0");
			param.put("out_result_reason","参数为空");
		}
    	return JSONObject.toJSONString(param).toString();
	}
	
	/**
	 * Title: convertReturnImportExcel
	 * Description: 订单新增导入任务处理
	 * @param file
	 * @param fileTemplete
	 * @param request
	 * @return
	 * @author jindong.lin
	 * @date 2018年1月30日
	 */
	@RequestMapping("convertOrderAddImportExcel")
	@ResponseBody
	public String convertOrderAddImportExcel(@RequestParam(value = "file", required = false) MultipartFile file, 
			FileTempleteQueryParam fileTemplete,HttpServletRequest request){
		Map<String,String>param=new HashMap<String,String>();
		if(fileTemplete != null && StringUtils.isNotBlank(fileTemplete.getBusinessType())){
			try{
				final FileTempleteQueryParam resultTemplete = fileTempleteService.getByQueryParam(fileTemplete);
				int taskWaitCount = fileImportTaskService
						.checkTaskCount();
				if(taskWaitCount > 10){
		    		param.put("out_result","0");
		    		param.put("out_result_reason","文件上传任务等待数量已达上限,请稍后再试!");
		    		return JSONObject.toJSONString(param).toString();
				}
				if(resultTemplete != null){
					//这里执行文件上传逻辑
					String fileName = file.getOriginalFilename();
					String prefix = fileName.split("\\.")[fileName.split("\\.").length-1];
		    		param.put("out_result","0");
		    		param.put("out_result_reason","系统错误");
					// 判断文件是否为空   
			        if (!file.isEmpty()) {
			        	if(prefix.equals("xlsx")){
			        		// 文件保存路径
			        		String uuid = UUID.randomUUID().toString().replace("-", "");
			    			String filePath= CommonUtils.getAllMessage("config", "BALANCE_UPLOAD_RAWDATA_"+
			    								OSinfo.getOSname()) + uuid + "/";
			    			File baseFile = new File(filePath);
			    			baseFile.mkdirs();
			    			filePath = filePath + fileName;
			    			baseFile = new File(filePath);
			                // 转存文件
			                file.transferTo(baseFile);
			                //这里设置列长
			                final List<String[]> list= XLSXCovertCSVReader.readerExcel(filePath, null, 
			                		resultTemplete.getTitleLength());
			                if(list==null || list.size()==0 || list.size()==1){
					    		param.put("out_result","0");
					    		param.put("out_result_reason","文件内容有误!");
					    		return JSONObject.toJSONString(param).toString();
			                }
			                //校验表头
			                final List<String> titleList = JSONObject.parseArray(
			                		resultTemplete.getTempleteTitle(), String.class);
			                for (int i = 0; i < list.get(0).length; i++) {
			                	if(list.get(0)[i] ==null || 
			                			!titleList.get(i).equals(list.get(0)[i])){
						    		param.put("out_result","0");
						    		param.put("out_result_reason","上传文件表头格式与约定不符,请严格按照约定修改!");
						    		return JSONObject.toJSONString(param).toString();
			                	}
							}
		    				list.remove(0);
		    	    		final Employee user = SessionUtils.getEMP(request);
		    	    		final FileImportTask fileImportTask = new FileImportTask();
		    	    		fileImportTask.setId(uuid);//uuid约定使用用户文件uuid,后面加入应用重启补偿机制
		    	    		fileImportTask.setFileName(fileName);
		    	    		fileImportTask.setTotalCount(list.size());
		    	    		fileImportTask.setBusinessType(fileTemplete.getBusinessType());
		    	    		fileImportTask.setBusinessGroup(fileTemplete.getBusinessGroup());
		    	    		fileImportTask.setCreateTime(new Date());
		    	    		fileImportTask.setCreateUser(user.getId().toString());
		    	    		fileImportTask.setUpdateTime(new Date());
		    	    		fileImportTask.setUpdateUser(user.getId().toString());
		    	    		fileImportTask.setStatus("2");//2等待开始
		    	    		int r = fileImportTaskService.insertSelective(fileImportTask);
		    	    		
		    	    		if(r > 0){
			    	    		//异步处理(使用公共线程池)
			    	    		ListenableFuture<String> listenableFuture = BaseConst
			    	    				.commonTaskExecutorService.submit(new Callable<String>() {
									@Override
									public String call() throws Exception {
										FileImportTask waitImportTask = 
												fileImportTaskService.selectByPrimaryKey(fileImportTask.getId());
										if(waitImportTask != null && waitImportTask.getStatus().equals("2")){
											//打标识正在进行中
											logger.error("导入任务improttask ID:" + fileImportTask.getId() 
												+ ",导入任务正在处理中...");
											fileImportTask.setStatus("-1");
											fileImportTask.setUpdateTime(new Date());
											fileImportTaskService.updateByPrimaryKeySelective(fileImportTask);
											//主要任务改变这里
											fileImportTaskService.convertOrderAddExcel(list,fileImportTask,user,
													titleList,resultTemplete.getTempleteName());
											return "导入任务improttask ID:" + fileImportTask.getId() + ",导入任务处理完毕";
										} else {
											return "导入任务improttask ID:" + fileImportTask.getId() + ",导入任务已取消";
										}
									}
								});
								//应用回调
								Futures.addCallback(listenableFuture, importTaskFutureCallback);	
					    		param.put("out_result","1");
					    		param.put("out_result_reason","导入任务已创建");
		    	    		} else {
					    		param.put("out_result","0");
					    		param.put("out_result_reason","导入任务创建失败");
		    	    		}
			        	}else{
				    		param.put("out_result","0");
				    		param.put("out_result_reason","文件类型必须为[.xlsx],您上传的文件类型为[."+prefix+"]!");
			        	}
			        } else {
			    		param.put("out_result","0");
			    		param.put("out_result_reason","上传文件不能为空");
			        }
				} else {
					param.put("out_result","0");
					param.put("out_result_reason","异常");
				}
				
			}catch(Exception e){
				e.printStackTrace();
				param.put("out_result","0");
				param.put("out_result_reason","异常");
			}
		} else {
			param.put("out_result","0");
			param.put("out_result_reason","参数为空");
		}
    	return JSONObject.toJSONString(param).toString();
	}
	
	/**
	 * Title: delImportTaskByIds
	 * Description: 
	 * @param ids
	 * @param fileTemplete
	 * @param request
	 * @return
	 * @author jindong.lin
	 * @date 2018年1月29日
	 */
	@RequestMapping("delImportTaskByIds")
	@ResponseBody
	public String delImportTaskByIds(String ids, 
			FileTempleteQueryParam fileTemplete,HttpServletRequest request){
		Map<String,String>param=new HashMap<String,String>();
		try {
    		Employee user = SessionUtils.getEMP(request);
			int r = fileImportTaskService.delImportTaskByIds(ids,user);
			if(r > 0){
				param.put("out_result","1");
				param.put("out_result_reason","删除成功");
			} else {
				param.put("out_result","1");
				param.put("out_result_reason","删除失败");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			param.put("out_result","0");
			param.put("out_result_reason","删除异常");
			e.printStackTrace();
		}
		
		return JSONObject.toJSONString(param).toString();
	}

	/**
	 * Title: expressinfoImportMatchExcel
	 * Description: 运单导入匹配
	 * @param file
	 * @param fileTemplete
	 * @param request
	 * @return
	 * @author jindong.lin
	 * @date 2018年3月12日
	 */
	@RequestMapping("expressinfoImportMatchExcel")
	@ResponseBody
	public String expressinfoImportMatchExcel(
			@RequestParam(value = "file", required = false) MultipartFile file, 
			FileTempleteQueryParam fileTemplete,HttpServletRequest request){
		Map<String,String>param=new HashMap<String,String>();
		if(fileTemplete != null && StringUtils.isNotBlank(fileTemplete.getBusinessType())){
			try{
				final FileTempleteQueryParam resultTemplete = fileTempleteService
						.getByQueryParam(fileTemplete);
				int taskWaitCount = fileImportTaskService
						.checkTaskCount();
				if(taskWaitCount > 10){
		    		param.put("out_result","0");
		    		param.put("out_result_reason","文件上传任务等待数量已达上限,请稍后再试!");
		    		return JSONObject.toJSONString(param).toString();
				}
				if(resultTemplete != null){
					//这里执行文件上传逻辑
					String fileName = file.getOriginalFilename();
					String prefix = fileName.split("\\.")[fileName.split("\\.").length-1];
		    		param.put("out_result","0");
		    		param.put("out_result_reason","系统错误");
					// 判断文件是否为空   
			        if (!file.isEmpty()) {
			        	if(prefix.equals("xlsx")){
			        		// 文件保存路径
			        		String uuid = UUID.randomUUID().toString().replace("-", "");
			    			String filePath= CommonUtils.getAllMessage("config", 
			    					"BALANCE_UPLOAD_RAWDATA_"+ OSinfo.getOSname()) 
			    					+ uuid + "/";
			    			File baseFile = new File(filePath);
			    			baseFile.mkdirs();
			    			filePath = filePath + fileName;
			    			baseFile = new File(filePath);
			                // 转存文件
			                file.transferTo(baseFile);
			                //这里设置列长
			                final List<String[]> list= XLSXCovertCSVReader
			                		.readerExcel(filePath, null, 
			                				resultTemplete.getTitleLength());
			                if(list==null || list.size()==0 || list.size()==1){
					    		param.put("out_result","0");
					    		param.put("out_result_reason","文件内容有误!");
					    		return JSONObject.toJSONString(param).toString();
			                } else if(list.size()>100001){//限制单次只能导入匹配十万条数据
					    		param.put("out_result","0");
					    		param.put("out_result_reason","单次导入记录过长，并不能超过十万条!");
					    		return JSONObject.toJSONString(param).toString();
			                }
			                //校验表头
			                final List<String> titleList = JSONObject.parseArray(
			                		resultTemplete.getTempleteTitle(), String.class);
			                for (int i = 0; i < list.get(0).length; i++) {
			                	if(list.get(0)[i] ==null || 
			                			!titleList.get(i).equals(list.get(0)[i])){
						    		param.put("out_result","0");
						    		param.put("out_result_reason",
						    				"上传文件表头格式与约定不符,请严格按照约定修改!");
						    		return JSONObject.toJSONString(param).toString();
			                	}
							}
		    				list.remove(0);
		    	    		final Employee user = SessionUtils.getEMP(request);
		    	    		final FileImportTask fileImportTask = new FileImportTask();
		    	    		fileImportTask.setId(uuid);//uuid约定使用用户文件uuid,后面加入应用重启补偿机制
		    	    		fileImportTask.setFileName(fileName);
		    	    		fileImportTask.setTotalCount(list.size());
		    	    		fileImportTask.setBusinessType(fileTemplete.getBusinessType());
		    	    		fileImportTask.setBusinessGroup(fileTemplete.getBusinessGroup());
		    	    		fileImportTask.setCreateTime(new Date());
		    	    		fileImportTask.setCreateUser(user.getId().toString());
		    	    		fileImportTask.setUpdateTime(new Date());
		    	    		fileImportTask.setUpdateUser(user.getId().toString());
		    	    		fileImportTask.setStatus("2");//2等待开始
		    	    		int r = fileImportTaskService.insertSelective(fileImportTask);
		    	    		
		    	    		if(r > 0){
			    	    		//异步处理(使用公共线程池)
			    	    		ListenableFuture<String> listenableFuture = BaseConst
			    	    				.commonTaskExecutorService.submit(new Callable<String>() {
									@Override
									public String call() throws Exception {
										FileImportTask waitImportTask = null;
										try {
											waitImportTask = 
													fileImportTaskService.selectByPrimaryKey(fileImportTask.getId());
											if(waitImportTask != null && waitImportTask.getStatus().equals("2")){
												//打标识正在进行中
												logger.error("导入任务improttask ID:" + fileImportTask.getId() 
													+ ",导入任务正在处理中...");
												fileImportTask.setStatus("-1");
												fileImportTask.setUpdateTime(new Date());
												fileImportTaskService.updateByPrimaryKeySelective(fileImportTask);
												//主要任务改变这里
												fileImportTaskService.expressinfoImportMatchExcel(list,fileImportTask,user,
														titleList,resultTemplete.getTempleteName());
												return "导入任务improttask ID:" + fileImportTask.getId() + ",导入任务处理完毕";
											} else {
												return "导入任务improttask ID:" + fileImportTask.getId() + ",导入任务已取消";
											}
										} catch (Exception e) {
											waitImportTask = new FileImportTask();
											waitImportTask.setId(fileImportTask.getId());
											fileImportTask.setStatus("0");
											fileImportTask.setUpdateTime(new Date());
											fileImportTaskService.updateByPrimaryKeySelective(fileImportTask);
											throw e;
										}
									}
								});
								//应用回调
								Futures.addCallback(listenableFuture, importTaskFutureCallback);	
					    		param.put("out_result","1");
					    		param.put("out_result_reason","导入任务已创建");
		    	    		} else {
					    		param.put("out_result","0");
					    		param.put("out_result_reason","导入任务创建失败");
		    	    		}
			        	}else{
				    		param.put("out_result","0");
				    		param.put("out_result_reason","文件类型必须为[.xlsx],您上传的文件类型为[."+prefix+"]!");
			        	}
			        } else {
			    		param.put("out_result","0");
			    		param.put("out_result_reason","上传文件不能为空");
			        }
				} else {
					param.put("out_result","0");
					param.put("out_result_reason","异常");
				}
				
			}catch(Exception e){
				e.printStackTrace();
				param.put("out_result","0");
				param.put("out_result_reason","异常");
			}
		} else {
			param.put("out_result","0");
			param.put("out_result_reason","参数为空");
		}
    	return JSONObject.toJSONString(param).toString();
	}
	
}
