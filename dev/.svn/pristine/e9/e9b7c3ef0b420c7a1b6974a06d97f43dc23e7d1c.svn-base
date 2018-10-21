package com.bt.workOrder.utils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import com.bt.workOrder.model.*;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.bt.common.util.MailUtil;
import com.bt.lmis.base.spring.SpringUtils;
import com.bt.lmis.model.Employee;
import com.bt.utils.CommonUtils;
import com.bt.utils.Constant;
import com.bt.utils.SessionUtils;
import com.bt.utils.UUIDUtils;
import com.bt.utils.observer.Observers;
import com.bt.utils.observer.Visual;
import com.bt.workOrder.bean.WoMutualLog;
import com.bt.workOrder.dao.GroupMapper;
import com.bt.workOrder.dao.WoMutualLogMapper;
import com.bt.workOrder.dao.WorkOrderManagementMapper;

/**
 * @Title:WorkOrderUtils
 * @Description: TODO  
 * @author Ian.Huang 
 * @date 2017年1月12日下午9:37:48
 */
public class WorkOrderUtils {
	
	@SuppressWarnings("unchecked")
	private static WorkOrderManagementMapper<T> workOrderManagementMapper=((WorkOrderManagementMapper<T>)SpringUtils.getBean("workOrderManagementMapper"));
	
	private static WoMutualLogMapper woMutualLogMapper=((WoMutualLogMapper)SpringUtils.getBean("woMutualLogMapper"));
	private static GroupMapper groupMapper=((GroupMapper)SpringUtils.getBean("groupMapper"));
	/**
	 * 
	 * @Description: TODO(工单号生成器)
	 * @return: String  
	 * @author Ian.Huang 
	 * @date 2017年2月23日下午2:27:03
	 */
	private static String workOrderNoGenerator() {
		return Constant.PERFIX_WO + new SimpleDateFormat("yyMMdd").format(new Date()) + UUIDUtils.getUUID8L();
		
	}
	
	/**
	 * 
	 * @Description: TODO(获取工单号)
	 * @return: String  
	 * @author Ian.Huang 
	 * @date 2017年1月26日下午4:27:15
	 */
	public static String generateWorkOrderNo() {
		return workOrderNoGenerator();
		
	}
	
	/**
	 * 
	 * @Description: TODO(索赔编码生成器)
	 * @param userId
	 * @return: String  
	 * @author Ian.Huang 
	 * @date 2017年2月23日下午2:26:39
	 */
	private synchronized static String claimNoGenerator(String userId) {
		// 获取当前年月日
		String now_ymd= new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		//
		int num= 0;
		if(workOrderManagementMapper.judgeDateExist(now_ymd) == 0) {
			num= 1;
			// 下一个从2开始取
			ClaimCount cc= new ClaimCount();
			cc.setCreate_by(userId);
			cc.setDate(now_ymd);
			cc.setClaim_num(2);
			workOrderManagementMapper.initializeNum(cc);
			
		} else {
			ClaimCount cc= workOrderManagementMapper.getCurrentNum(now_ymd);
			num= cc.getClaim_num();
			// 更新
			cc.setUpdate_by(userId);
			cc.setClaim_num(num + 1);
			workOrderManagementMapper.updateNum(cc);
			
		}
		return Constant.PERFIX_WOC + new SimpleDateFormat("yyyyMMdd").format(new Date()) + Constant.SHORT_BAR + num;
		
	}
	
	/**
	 * 
	 * @Description: TODO(获取索赔编码)
	 * @return: String  
	 * @author Ian.Huang 
	 * @date 2017年1月26日下午4:49:00
	 */
	public static String generateClaimNo(String userId) {
		return claimNoGenerator(userId);
		
	}
	
	/**
	 * 
	 * @Description: TODO
	 * @param code
	 * @return: String  
	 * @author Ian.Huang 
	 * @date 2017年3月21日上午11:33:40
	 */
	public static String endueDisplay(String code) {
		switch(code) {
		case Constant.WO_ALLOC_STATUS_WAA:return "待自动分配";
		case Constant.WO_ALLOC_STATUS_WMA:return "待手动分配";
		case Constant.WO_ALLOC_STATUS_ALD:return "已分配";
		case Constant.WO_PROCESS_STATUS_NEW:return "新建";
		case Constant.WO_PROCESS_STATUS_PRO:return "处理中";
		case Constant.WO_PROCESS_STATUS_PAU:return "挂起待确认";
		case Constant.WO_PROCESS_STATUS_CCD:return "已取消";
		case Constant.WO_PROCESS_STATUS_FIN:return "已处理";
		default: return "";
		}
		
	}
	
	/**
	 * 
	 * @Description: TODO(判断a是否为b的下级组)
	 * @param a
	 * @param b
	 * @return: Boolean  
	 * @author Ian.Huang 
	 * @date 2017年2月9日下午9:09:55
	 */
	@SuppressWarnings("unchecked")
	public static Boolean judgeSubordinateGroupOrNot(Integer a, Integer b) {
		GroupMapper<T> groupMapper= (GroupMapper<T>)SpringUtils.getBean("groupMapper");
		if(groupMapper.judgeSubordinateGroupOrNot(a, b) != 0) {
			return true;
			
		} else {
			List<Integer> param= new ArrayList<Integer>();
			param.add(b);
			List<Integer> subordinateGroups= groupMapper.getSubordinateGroups(param);
			while(CommonUtils.checkExistOrNot(subordinateGroups)) {
				for(int i= 0; i< subordinateGroups.size(); i++) {
					if(groupMapper.judgeSubordinateGroupOrNot(a, subordinateGroups.get(i)) != 0) {
						return true;
						
					}
					
				}
				subordinateGroups= groupMapper.getSubordinateGroups(param);
				
			}
			
		}
		return false;
		
	}
	
	/**
	 * 
	 * @Description: TODO
	 * @param woem
	 * @param param
	 * @return: String  
	 * @author Ian.Huang 
	 * @date 2017年2月8日下午12:53:56
	 */
	public static String generateEventRemark(WorkOrderEventMonitor woem, Map<String, Object> param) {
		String result= null;
		if(woem.getEvent_status()) {
			switch(woem.getEvent()) {
			case Constant.WO_EVENT_CREATED: result= "创建";break;
			case Constant.WO_EVENT_ETC: result= "计划完成时间";break;
			case Constant.WO_EVENT_ALLOC_M: result= param.get("event") + "";break;
			case Constant.WO_EVENT_START:
			case Constant.WO_EVENT_UPDATE:result= param.get("info") + "";break;
			case Constant.WO_EVENT_CANCEL_ALLOC:;
			case Constant.WO_EVENT_PAUSE:;
			case Constant.WO_EVENT_RECOVER:;
			case Constant.WO_EVENT_CANCEL:result= "" + param.get("info") + param.get("event");break;
			case Constant.WO_EVENT_FIN: result= param.get("info") + "，" + param.get("event");break;
			default:break;
			}
			
		} else {
			result= param.get("event") + "失败；失败原因：" + param.get("reason");
			
		}
		return result;
		
	}
	
	/**
	 * 
	 * @Description: TODO
	 * @param wo
	 * @param user
	 * @throws Exception
	 * @return: OperationResult  
	 * @author Ian.Huang 
	 * @date 2017年3月20日下午1:43:15
	 */
	public static OperationResult addWorkOrder(WorkOrder wo, Employee user) throws Exception {
		OperationResult or=new OperationResult(false);
		try {
			Map<String, Object> param= new HashMap<String, Object>();
			param.put("event", Constant.WO_EVENT_CREATED);
			VerificationResult vr= verificate(user, null, wo, param);
			if(vr.isFlag()) {
				or.setResult(true);
				// 手动提交事务
				WebApplicationContext contextLoader = ContextLoader.getCurrentWebApplicationContext();
				DataSourceTransactionManager transactionManager = (DataSourceTransactionManager)contextLoader.getBean("transactionManager");
				DefaultTransactionDefinition def = new DefaultTransactionDefinition();
				def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW); // 事物隔离级别，开启新事务，这样会比较安全些。
				TransactionStatus status = transactionManager.getTransaction(def); // 获得事务状态
				try {
					// 新增工单
					wo.setId(UUID.randomUUID().toString());
					Date now = new Date();
					wo.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(now));
					wo.setCreateBy(user.getId().toString());
					wo.setCreateByDisplay(user.getName());
					wo.setWoAllocStatus(Constant.WO_ALLOC_STATUS_WAA);
					wo.setWoAllocStatusDisplay(endueDisplay(Constant.WO_ALLOC_STATUS_WAA));
					wo.setWoProcessStatus(Constant.WO_PROCESS_STATUS_NEW);
					wo.setWoProcessStatusDisplay(endueDisplay(Constant.WO_PROCESS_STATUS_NEW));
					// 工单事件监控
					WorkOrderEventMonitor woem= new WorkOrderEventMonitor();
					woem.setCreate_by(user.getId().toString());
					woem.setWo_id(wo.getId());
					// 创建
					woem.setId(UUID.randomUUID().toString());
					woem.setSort(1);
					woem.setEvent(Constant.WO_EVENT_CREATED);
					woem.setEvent_status(true);
					woem.setEvent_description(WorkOrderUtils.generateEventRemark(woem, null));
					woem.setRemark("初始工单级别：" + workOrderManagementMapper.getLevelName(wo.getWoLevel()));
					workOrderManagementMapper.addWorkOrderEvent(woem);
					// 获取工单类型工单级别对应工时
					Map<String, Object> hours= workOrderManagementMapper.gethours(wo.getWoType(), wo.getWoLevel());
					// 标准工时
					wo.setStandardManhours(new BigDecimal(hours.get("wk_standard").toString()));
					// 计划完成时间
					Calendar cal = Calendar.getInstance();
					cal.setTime(now);
					cal.add(Calendar.MINUTE, Integer.parseInt(hours.get("wk_plan").toString()));
					wo.setEstimatedTimeOfCompletion(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cal.getTime()));
					// 计划完成时间
					woem.setId(UUID.randomUUID().toString());
					woem.setSort(2);
					woem.setEvent(Constant.WO_EVENT_ETC);
					// 事件状态已为成功
					woem.setEvent_description(WorkOrderUtils.generateEventRemark(woem, null));
					woem.setRemark("计划完成时间：" + wo.getEstimatedTimeOfCompletion());
					workOrderManagementMapper.addWorkOrderEvent(woem);
					// 下一级升级时间
					cal.setTime(now);
					cal.add(Calendar.MINUTE, Integer.parseInt(hours.get("wk_timeout").toString()));
					wo.setCal_date(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cal.getTime()));
					if(CommonUtils.checkExistOrNot(wo.getBySelf())) {
						wo.setWoAllocStatus(Constant.WO_ALLOC_STATUS_ALD);
						wo.setWoAllocStatusDisplay(endueDisplay(Constant.WO_ALLOC_STATUS_ALD));
						wo.setAllocatedBy(user.getId() + "");
						wo.setAllocatedByDisplay(user.getName());
						wo.setProcessor(user.getId() + "");
						wo.setProcessorDisplay(user.getName());
						//
						woem.setId(UUID.randomUUID().toString());
						woem.setSort(3);
						woem.setEvent(Constant.WO_EVENT_ALLOC_M);
						woem.setOutManhours(false);
			            woem.setEvent_description("自行处理");
			            woem.setRemark("成功（分配者：" + user.getName() + "；处理者：" + user.getName() + "；计入处理者工时；）");
			            workOrderManagementMapper.addWorkOrderEvent(woem);
			            subtractManhours(
		                		user, user, wo.getId(),
		                		workOrderManagementMapper.getManhours(user.getId(), new SimpleDateFormat("yyyy-MM-dd").format(new Date())),
		                		new BigDecimal(workOrderManagementMapper.gethours(wo.getWoType(), wo.getWoLevel()).get("wk_standard").toString()));
			            // 返回当前工单ID
			            or.getReturnMap().put("woId", wo.getId());
						
					}
					workOrderManagementMapper.add(wo);
					// 附件关联
					if(CommonUtils.checkExistOrNot(wo.getFileName_common())) {
						// 添加附件关联
						Operation op= new Operation();
						op.setId(UUID.randomUUID().toString());
						op.setCreate_by(user.getId().toString());
						op.setWo_id(wo.getId());
						op.setWo_type(wo.getWoType());
						op.setColumn_code("fileName");
						op.setColumn_value(wo.getFileName_common());
						workOrderManagementMapper.addOperation(op);
						
					}
					//增加交互日志
					WoMutualLog record = new WoMutualLog();
					record.setAccessory(wo.getFileName_common());
					record.setAction(Constant.WO_EVENT_CREATED);
					record.setCreateBy(String.valueOf(user.getId()));
					record.setCreateTime(new Date());
					String remark_wl = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"/物流部/"+user.getName()+"/创建工单";
					record.setLog(remark_wl);
					record.setProcessRemark(wo.getSupplementExplain());
					record.setWoNo(wo.getWoNo());
					woMutualLogMapper.insert(record);
					// 非OMS索赔工单
					if(wo.getWoType().equals(Constant.WO_TYPE_NOMSC) || wo.getWoType().equals(Constant.WO_TYPE_OMSC)) {
						// 主数据
						List<Operation> ops = new ArrayList<Operation>();
						// 索赔编码
						Operation op= new Operation();
						op.setId(UUID.randomUUID().toString());
						op.setCreate_by(user.getId().toString());
						op.setWo_id(wo.getId());
						op.setWo_type(wo.getWoType());
						op.setColumn_code("claim_number");
						switch(wo.getWoType()) {
						case Constant.WO_TYPE_NOMSC:
							op.setColumn_value(generateClaimNo(user.getId().toString()));
							ops.add(op);
							break;
						case Constant.WO_TYPE_OMSC:
							// 查询OMS索赔记录
							OMSClaim oc= workOrderManagementMapper.getOMSClaimById(Integer.parseInt(wo.getClaimId()));
							op.setColumn_value(oc.getClaimCode());
							ops.add(op);
							// 索赔状态
							ops.add(new Operation(UUID.randomUUID().toString(), user.getId().toString(), wo.getId(), wo.getWoType(), "claim_status", ""));
							// OMS反馈状态
							ops.add(new Operation(UUID.randomUUID().toString(), user.getId().toString(), wo.getId(), wo.getWoType(), "oms_retrun_flag", ""));
							// 系统对接编码
							ops.add(new Operation(UUID.randomUUID().toString(), user.getId().toString(), wo.getId(), wo.getWoType(), "sys_docking_number", oc.getSystemCode()));
							// 索赔分类与索赔原因
							String claimType= "";
							String claimReason= "";
							switch(oc.getClaimReason()) {
							case "1":
								claimType="遗失";
								claimReason="无路由信息";
								break;
							case "2":
								claimType="遗失";
								claimReason="路由停滞24小时";
								break;
							case "3":
								claimType="遗失";
								claimReason="买家未收到货（路由显示签收）";
								break;
							case "4":
								claimType="遗失";
								claimReason="物流反馈丢件";
								break;
							case "5":
								claimType="遗失";
								claimReason="开箱少/无货";
								break;
							case "6":
								claimType="破损";
								claimReason="物流反馈破损";
								break;
							case "7":
								claimType="破损";
								claimReason="开箱后反馈破损";
								break;
							case "8":
								claimType="错发";
								claimReason="开箱后反馈错发";
								break;
							case "9":
								claimType="补偿";
								claimReason="补偿金";
								break;
							default:break;
							}
							ops.add(new Operation(UUID.randomUUID().toString(), user.getId().toString(), wo.getId(), wo.getWoType(), "claim_type", claimType));
							ops.add(new Operation(UUID.randomUUID().toString(), user.getId().toString(), wo.getId(), wo.getWoType(), "claim_reason", claimReason));
							// 是否外箱破损
							ops.add(new Operation(UUID.randomUUID().toString(), user.getId().toString(), wo.getId(), wo.getWoType(), "outer_damaged_flag", oc.getIsOuterContainerDamaged().equals("1") ? "true" : "false"));
							// 是否产品包装破损
							ops.add(new Operation(UUID.randomUUID().toString(), user.getId().toString(), wo.getId(), wo.getWoType(), "package_damaged_flag", oc.getIsPackageDamaged().equals("1") ? "true" : "false"));
							// 是否二次封箱
							ops.add(new Operation(UUID.randomUUID().toString(), user.getId().toString(), wo.getId(), wo.getWoType(), "repeat_box_flag", oc.getIsTwoSubBox().equals("1") ? "true" : "false"));
							// 是否有产品退回
							ops.add(new Operation(UUID.randomUUID().toString(), user.getId().toString(), wo.getId(), wo.getWoType(), "return_flag", oc.getIsHasProductReturn().equals("1") ? "true" : "false"));
							// 是否箱内填充充分
							ops.add(new Operation(UUID.randomUUID().toString(), user.getId().toString(), wo.getId(), wo.getWoType(), "filling_in_box_fully_flag", oc.getIsFilledWith().equals("1") ? "true" : "false"));
							// 附件下载地址
							ops.add(new Operation(UUID.randomUUID().toString(), user.getId().toString(), wo.getId(), wo.getWoType(), "oms_attachment_url", CommonUtils.checkExistOrNot(oc.getFileUrl()) ? oc.getFileUrl() : "" ));
							// 附加金额
							ops.add(new Operation(UUID.randomUUID().toString(), user.getId().toString(), wo.getId(), wo.getWoType(), "added_value", oc.getExtralAmt().toString()));
							// 附加金额备注
							ops.add(new Operation(UUID.randomUUID().toString(), user.getId().toString(), wo.getId(), wo.getWoType(), "added_value_remark", oc.getExtralRemark()));
							// 申请理赔总金额
							ops.add(new Operation(UUID.randomUUID().toString(), user.getId().toString(), wo.getId(), wo.getWoType(), "total_applied_claim_amount", oc.getTotalClaimAmt().toString()));
							// 店铺备注
							ops.add(new Operation(UUID.randomUUID().toString(), user.getId().toString(), wo.getId(), wo.getWoType(), "store_remark", oc.getRemark()));
							// 明细数据
							List<ClaimDetail> claimDetail= workOrderManagementMapper.selectOMSInterfaceClaimDetail(Integer.parseInt(wo.getClaimId()));
							for(int i = 0; i < claimDetail.size(); i++) {
								claimDetail.get(i).setClaimDetailId(UUID.randomUUID().toString());
								claimDetail.get(i).setCreate_by(user.getId().toString());
								claimDetail.get(i).setWo_id(wo.getId());
								claimDetail.get(i).setRelated_number(wo.getRelatedNumber());
								
							}
							workOrderManagementMapper.addCliamDetail(claimDetail);
							break;
						default:break;
						}
						workOrderManagementMapper.addOperations(ops);
						
					}
					transactionManager.commit(status);
					
				} catch (Exception e) {
					transactionManager.rollback(status);
					throw e;
					
				}
				
			} else {
				or.setResultContent("工单新增失败，失败原因："+vr.getError_message());
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
			
		}
		if(!CommonUtils.checkExistOrNot(wo.getBySelf())) {
			// 非自行处理新建工单
			Visual visual=new Visual();
			visual.addObserver(Observers.getInstance());
			visual.setData(wo.getId());
			
		}
		return or;
		
	}
	
	/**
	 * 
	 * @Description: TODO(校验权限)
	 * @param operator
	 * @param wo
	 * @throws Exception
	 * @return: Boolean  
	 * @author Ian.Huang 
	 * @date 2017年2月22日下午8:57:29
	 */
	public static Boolean judgePower(Integer operator, WorkOrder wo) throws Exception {
		List<Integer> groups= workOrderManagementMapper.selectGroups(operator);
		// 默认无权限
		boolean power= false;
		for(int i= 0; i< groups.size(); i++) {
			int processControl = workOrderManagementMapper.getProcessControl(groups.get(i));
			//所有工单 processControl==1不控制处理权限
			if(processControl==1){
				power= true;
	            break;
			}
		    //原来的  所属组具有处理该物流承运商,工单类型与级别的权限
			if(workOrderManagementMapper.judgeGroupWorkPower(groups.get(i), wo) != 0) {
		        Integer warehosueType= workOrderManagementMapper.getWarehouseType(wo.getWarehouses());
		        Map<String, Object> warehouseInfo= workOrderManagementMapper.judgeGroupStorePower(groups.get(i), wo);
		        if(CommonUtils.checkExistOrNot(warehouseInfo)
		        		&& ((warehosueType == 0 && Boolean.parseBoolean(warehouseInfo.get("selfwarehouse").toString()))
		        				|| (warehosueType == 1 && Boolean.parseBoolean(warehouseInfo.get("outsourcedwarehouse").toString())))) {
		        	// 仓库为自营仓且可处理自营仓/仓库为外包仓且可处理外包仓
		            power= true;
		            break;
		        	
		        }
		        
			}
	        
		}
		return power;
		
	}
	
	/**
	 * 
	 * @Description: TODO(操作验证)
	 * @param event
	 * @param operator
	 * @param actor
	 * @param wo
	 * @throws Exception
	 * @return: VerificationResult  
	 * @author Ian.Huang 
	 * @date 2017年2月22日下午11:52:14
	 */
	private static VerificationResult verificate(Employee operator, Employee actor, WorkOrder wo, Map<String, Object> param) throws Exception {
		VerificationResult vr= new VerificationResult(false);
		switch(param.get("event").toString()) {
		case Constant.WO_EVENT_CREATED:
			if(CommonUtils.checkExistOrNot(wo.getBySelf())) {
				// 自行处理新建工单
				if(!judgePower(operator.getId(), wo)) {
					vr.setError_type(0);
					vr.setError_message("当前用户无操作此工单权限");
					return vr;
					
				}
				// 当前年月日
	            String now_ymd_= new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	            // 员工当日工时情况
	            Manhours manhour_= workOrderManagementMapper.getManhours(operator.getId(), now_ymd_);
	            // 工时情况是否符合条件
	            if(!CommonUtils.checkExistOrNot(manhour_)) {
	            	vr.setError_type(0);
					vr.setError_message(operator.getName() + "无" + now_ymd_ + "工时记录");
					return vr;
	            	
	            }
	            // 当前用户当日工时记录存在
	            BigDecimal subtract_= manhour_.getManhours().subtract(manhour_.getAllocated());
	            // 工单所需标准工时
	            if(!((subtract_.compareTo(new BigDecimal("0")) == 1)
	            		&& (subtract_.compareTo(new BigDecimal(workOrderManagementMapper.gethours(wo.getWoType(), wo.getWoLevel()).get("wk_standard").toString())) >= 0))) {
	            	vr.setError_type(0);
					vr.setError_message(operator.getName() + "于" + now_ymd_ + "当前可用标准工时不满足此工单需求");
					return vr;
					
	            }
				
			}break;
		case Constant.WO_EVENT_ALLOC_M:
			if(!judgePower(operator.getId(), wo)) {
				vr.setError_type(0);
				vr.setError_message("当前用户无操作此工单权限");
				return vr;
				
			}
			if(!judgePower(actor.getId(), wo)) {
				vr.setError_type(0);
				vr.setError_message(actor.getName() + "无处理此工单权限");
				return vr;
		    	
		    }
	    	if(wo.getWoAllocStatus().equals(Constant.WO_ALLOC_STATUS_ALD)) {
	    		vr.setError_type(0);
				vr.setError_message(actor.getName() + "工单已分配，无法进行手动分配");
				return vr;
	            
	        }
	    	if(wo.getWoProcessStatus().equals(Constant.WO_PROCESS_STATUS_CCD) || wo.getWoProcessStatus().equals(Constant.WO_PROCESS_STATUS_FIN)) {
	    		vr.setError_type(0);
				vr.setError_message("已取消或已处理工单无法进行操作");
				return vr;
	            
	        }
	    	if(!CommonUtils.checkExistOrNot(param.get("outManhours"))) {
	    		// 当前年月日
	            String now_ymd= new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	            // 员工当日工时情况
	            Manhours manhour= workOrderManagementMapper.getManhours(actor.getId(), now_ymd);
	            // 工时情况是否符合条件
                if(!CommonUtils.checkExistOrNot(manhour)) {
                	vr.setError_type(0);
    				vr.setError_message(actor.getName() + "无" + now_ymd + "工时记录");
    				return vr;
                	
                }
                // 当前用户当日工时记录存在
                BigDecimal subtract= manhour.getManhours().subtract(manhour.getAllocated());
                // 工单所需标准工时
                if(!((subtract.compareTo(new BigDecimal("0")) == 1)
                		&& (subtract.compareTo(new BigDecimal(workOrderManagementMapper.gethours(wo.getWoType(), wo.getWoLevel()).get("wk_standard").toString())) >= 0))) {
                	vr.setError_type(0);
    				vr.setError_message(actor.getName() + "于" + now_ymd + "当前可用标准工时不满足此工单需求");
    				return vr;
    				
                }
	            
	    	}break;
		case Constant.WO_EVENT_CANCEL_ALLOC:
			if(CommonUtils.checkExistOrNot(param.get("moveType")) && param.get("moveType").equals("process")) {
				if(!operator.getId().toString().equals(wo.getProcessor())) {
					vr.setError_type(0);
    				vr.setError_message("此工单已不属于当前用户处理");
    				return vr;
		            
				}
				if(!judgePower(operator.getId(), wo)) {
					vr.setError_type(2);
    				vr.setError_message("当前用户无操作此工单权限");
    				return vr;
					
				}
				
			} else {
			    if(!operator.getId().equals(0) && !judgePower(operator.getId(), wo)) {
			    	if(CommonUtils.checkExistOrNot(wo.getProcessor()) && operator.getId().toString().equals(wo.getProcessor())) {
			    		vr.setError_type(2);
			    		
			    	} else {
			    		vr.setError_type(0);
			    		
			    	}
    				vr.setError_message("当前用户无操作此工单权限");
    				return vr;
					
				}
				
			}
	    	if(wo.getWoAllocStatus().equals(Constant.WO_ALLOC_STATUS_WAA) || wo.getWoAllocStatus().equals(Constant.WO_ALLOC_STATUS_WMA)) {
	    		vr.setError_type(0);
				vr.setError_message("工单待分配，无法进行取消分配");
				return vr;
	            
	        } 
			if(wo.getWoProcessStatus().equals(Constant.WO_PROCESS_STATUS_CCD) || wo.getWoProcessStatus().equals(Constant.WO_PROCESS_STATUS_FIN)) {
				vr.setError_type(0);
				vr.setError_message("已取消或已处理工单无法进行操作");
				return vr;
				
			}
			// 返还当日工时校验
			WorkOrderEventMonitor old_woem=workOrderManagementMapper.getLastManhoursAlterEvent(wo.getId());
			if(CommonUtils.checkExistOrNot(old_woem)) {
	            // 员工当日工时情况
	            Manhours manhour= workOrderManagementMapper.getManhours(Integer.parseInt(wo.getProcessor()), old_woem.getCreate_time());
	            // 工时情况是否符合条件
                if(!CommonUtils.checkExistOrNot(manhour)) {
                	vr.setError_type(0);
    				vr.setError_message(wo.getProcessorDisplay()+"无"+old_woem.getCreate_time()+"工时记录");
    				return vr;
                	
                }
				
			}break;
		case Constant.WO_EVENT_START:
			if(!operator.getId().toString().equals(wo.getProcessor())) {
				List<Map<String,Object>> qaList = groupMapper.isQaById(operator.getId().toString());
				if(qaList.size()>0&&qaList.get(0).get("qaid").toString().equals(wo.getProcessor())) {
					break;
				}
				vr.setError_type(0);
				vr.setError_message("此工单已不属于当前用户处理");
				return vr;
	            
			}
			if(wo.getWoAllocStatus().equals(Constant.WO_ALLOC_STATUS_WAA) || wo.getWoAllocStatus().equals(Constant.WO_ALLOC_STATUS_WMA)) {
	    		vr.setError_type(0);
				vr.setError_message("工单待分配，无法进入处理");
				return vr;
	            
	        }
			if(!(wo.getWoProcessStatus().equals(Constant.WO_PROCESS_STATUS_CCD) || wo.getWoProcessStatus().equals(Constant.WO_PROCESS_STATUS_FIN))
					&& !judgePower(operator.getId(), wo)) {
				// 当处理状态不为已取消以及已完成时，需要关注权限
				vr.setError_type(2);
				vr.setError_message("当前用户无操作此工单权限");
				return vr;
				
			}break;
		case Constant.WO_EVENT_UPDATE:
			if(!operator.getId().toString().equals(wo.getProcessor())) {
				vr.setError_type(0);
				vr.setError_message("此工单已不属于当前用户处理");
				return vr;
	            
			}
			if(!judgePower(operator.getId(), wo)) {
				vr.setError_type(2);
				vr.setError_message("当前用户无操作此工单权限");
				return vr;
				
			}
			if(CommonUtils.checkExistOrNot(actor)) {
				// 被取消分配人校验
				// 返还当日工时校验
				WorkOrderEventMonitor old_woem2=workOrderManagementMapper.getLastManhoursAlterEvent(wo.getId());
				if(CommonUtils.checkExistOrNot(old_woem2)) {
		            // 员工当日工时情况
		            Manhours manhour=workOrderManagementMapper.getManhours(Integer.parseInt(wo.getProcessor()), old_woem2.getCreate_time());
		            // 工时情况是否符合条件
	                if(!CommonUtils.checkExistOrNot(manhour)) {
	                	vr.setError_type(0);
	    				vr.setError_message(wo.getProcessorDisplay()+"无"+old_woem2.getCreate_time()+"工时记录");
	    				return vr;
	                	
	                }
					
				}
				// 被分配人校验
				WorkOrder temp= new WorkOrder();
				temp.setWoType(wo.getWoType());
				temp.setWoLevel(param.get("wo_level") + "");
				temp.setCarriers(wo.getCarriers());
				temp.setWarehouses(wo.getWarehouses());
				temp.setStores(wo.getStores());
				if(!judgePower(actor.getId(), temp)) {
					// 需要转发工单
					vr.setError_type(0);
					vr.setError_message(actor.getName() + "无处理此工单权限");
					return vr;
					
				} else {
					// 当前年月日
		            String now_ymd= new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		            // 员工当日工时情况
		            Manhours manhour= workOrderManagementMapper.getManhours(actor.getId(), now_ymd);
		            // 工时情况是否符合条件
	                if(!CommonUtils.checkExistOrNot(manhour)) {
	                	vr.setError_type(0);
	    				vr.setError_message(actor.getName() + "无" + now_ymd + "工时记录");
	    				return vr;
	                	
	                } else {
	                	// 当前用户当日工时记录存在
		                BigDecimal subtract= manhour.getManhours().subtract(manhour.getAllocated());
		                // 工单所需标准工时
		                if(!((subtract.compareTo(new BigDecimal("0")) == 1)
		                		&& (subtract.compareTo(new BigDecimal(workOrderManagementMapper.gethours(temp.getWoType(), temp.getWoLevel()).get("wk_standard").toString())) >= 0))) {
		                	vr.setError_type(0);
		    				vr.setError_message(actor.getName() + "于" + now_ymd + "当前可用标准工时不满足此工单需求");
		    				return vr;
		    				
		                }
	                	
	                }
					
				}
				
			}
			if(wo.getWoAllocStatus().equals(Constant.WO_ALLOC_STATUS_WAA) || wo.getWoAllocStatus().equals(Constant.WO_ALLOC_STATUS_WMA)) {
	    		vr.setError_type(0);
				vr.setError_message("工单待分配，无法进行操作");
				return vr;
	            
	        } 
			if(wo.getWoProcessStatus().equals(Constant.WO_PROCESS_STATUS_CCD) || wo.getWoProcessStatus().equals(Constant.WO_PROCESS_STATUS_FIN)) {
				vr.setError_type(0);
				vr.setError_message("已取消或已处理工单无法进行操作");
				return vr;
				
			}
			if(wo.getWoProcessStatus().equals(Constant.WO_PROCESS_STATUS_NEW)) {
				vr.setError_type(0);
				vr.setError_message("处理状态异常，不支持当前操作");
				return vr;
				
			}break;
		case Constant.WO_EVENT_PAUSE:
			if(!operator.getId().toString().equals(wo.getProcessor())) {
				vr.setError_type(0);
				vr.setError_message("此工单已不属于当前用户处理");
				return vr;
	            
			}
			if(!judgePower(operator.getId(), wo)) {
				vr.setError_type(2);
				vr.setError_message("当前用户无操作此工单权限");
				return vr;
				
			}
			if(wo.getWoAllocStatus().equals(Constant.WO_ALLOC_STATUS_WAA) || wo.getWoAllocStatus().equals(Constant.WO_ALLOC_STATUS_WMA)) {
	    		vr.setError_type(0);
				vr.setError_message("工单待分配，无法进行操作");
				return vr;
	            
	        } 
			if(wo.getWoProcessStatus().equals(Constant.WO_PROCESS_STATUS_CCD) || wo.getWoProcessStatus().equals(Constant.WO_PROCESS_STATUS_FIN)) {
				vr.setError_type(0);
				vr.setError_message("已取消或已处理工单无法进行操作");
				return vr;
				
			}
			if(!wo.getWoProcessStatus().equals(Constant.WO_PROCESS_STATUS_PRO)) {
				vr.setError_type(0);
				vr.setError_message("处理状态异常，不支持挂起操作");
				return vr;
				
			}break;
		case Constant.WO_EVENT_RECOVER:
			if(!operator.getId().toString().equals(wo.getProcessor())) {
				vr.setError_type(0);
				vr.setError_message("此工单已不属于当前用户处理");
				return vr;
	            
			}
			if(!judgePower(operator.getId(), wo)) {
				vr.setError_type(2);
				vr.setError_message("当前用户无操作此工单权限");
				return vr;
				
			}
			if(wo.getWoAllocStatus().equals(Constant.WO_ALLOC_STATUS_WAA) || wo.getWoAllocStatus().equals(Constant.WO_ALLOC_STATUS_WMA)) {
	    		vr.setError_type(0);
				vr.setError_message("工单待分配，无法进行操作");
				return vr;
	            
	        } 
			if(wo.getWoProcessStatus().equals(Constant.WO_PROCESS_STATUS_CCD) || wo.getWoProcessStatus().equals(Constant.WO_PROCESS_STATUS_FIN)) {
				vr.setError_type(0);
				vr.setError_message("已取消或已处理工单无法进行操作");
				return vr;
				
			}
			if(!wo.getWoProcessStatus().equals(Constant.WO_PROCESS_STATUS_PAU)) {
				vr.setError_type(0);
				vr.setError_message("处理状态异常，不支持取消挂起操作");
				return vr;
				
			}break;
		case Constant.WO_EVENT_CANCEL:
			if(CommonUtils.checkExistOrNot(param.get("moveType")) && param.get("moveType").equals("process")) {
				if(!operator.getId().toString().equals(wo.getProcessor())) {
					vr.setError_type(0);
    				vr.setError_message("此工单已不属于当前用户处理");
    				return vr;
		            
				}
				if(!judgePower(operator.getId(), wo)) {
					vr.setError_type(2);
    				vr.setError_message("当前用户无操作此工单权限");
    				return vr;
					
				}
				
			} else {
			    if(!operator.getId().equals(0) && !judgePower(operator.getId(), wo)) {
			    	if(CommonUtils.checkExistOrNot(wo.getProcessor()) && operator.getId().toString().equals(wo.getProcessor())) {
			    		vr.setError_type(2);
			    		
			    	} else {
			    		vr.setError_type(0);
			    		
			    	}
    				vr.setError_message("当前用户无操作此工单权限");
    				return vr;
					
				}
				
			}			
			if(wo.getWoProcessStatus().equals(Constant.WO_PROCESS_STATUS_CCD) || wo.getWoProcessStatus().equals(Constant.WO_PROCESS_STATUS_FIN)) {
				vr.setError_type(0);
				vr.setError_message("已取消或已处理工单无法进行操作");
				return vr;
				
			}
			if(wo.getWoAllocStatus().equals(Constant.WO_ALLOC_STATUS_ALD)) {
				// 返还当日工时校验
				WorkOrderEventMonitor old_woem2= workOrderManagementMapper.getLastManhoursAlterEvent(wo.getId());
				if(CommonUtils.checkExistOrNot(old_woem2)) {
		            // 员工当日工时情况
		            Manhours manhour= workOrderManagementMapper.getManhours(Integer.parseInt(wo.getProcessor()), old_woem2.getCreate_time());
		            // 工时情况是否符合条件
	                if(!CommonUtils.checkExistOrNot(manhour)) {
	                	vr.setError_type(0);
	    				vr.setError_message(wo.getProcessorDisplay()+"无"+old_woem2.getCreate_time()+"工时记录");
	    				return vr;
	                	
	                }
					
				}
				
			}
			break;
		case Constant.WO_EVENT_FIN:;
		case "saveOperate":
			if(!operator.getId().equals(0)) {
				if(!operator.getId().toString().equals(wo.getProcessor())) {
					vr.setError_type(0);
					vr.setError_message("此工单已不属于当前用户处理");
					return vr;
        
				}
				if(!judgePower(operator.getId(), wo)) {
					vr.setError_type(2);
					vr.setError_message("当前用户无操作此工单权限");
					return vr;
			
				}
	 
			}
			if(wo.getWoAllocStatus().equals(Constant.WO_ALLOC_STATUS_WAA) || wo.getWoAllocStatus().equals(Constant.WO_ALLOC_STATUS_WMA)) {
	    		vr.setError_type(0);
				vr.setError_message("工单待分配，无法进行操作");
				return vr;
	            
	        }
			String claimStatus= workOrderManagementMapper.getClaimStatus(wo.getId());
			if(!(CommonUtils.checkExistOrNot(claimStatus) && claimStatus.equals("0"))) {
				if(wo.getWoProcessStatus().equals(Constant.WO_PROCESS_STATUS_CCD) || wo.getWoProcessStatus().equals(Constant.WO_PROCESS_STATUS_FIN)) {
					vr.setError_type(0);
					vr.setError_message("已取消或已处理工单无法进行操作");
					return vr;
					
				}
				if(!wo.getWoProcessStatus().equals(Constant.WO_PROCESS_STATUS_PRO)) {
					vr.setError_type(0);
					vr.setError_message("处理状态异常，不支持当前操作");
					return vr;
					
				}
				
			}
			break;
		default:break;
		}
		vr.setFlag(true);
		return vr;
		
	}
	
	/**
	 * 
	 * @Description: TODO(返还员工工时)
	 * @param user
	 * @param wo
	 * @throws Exception
	 * @return: void  
	 * @author Ian.Huang 
	 * @date 2017年2月22日下午9:19:47
	 */
	private static void addManhours(Employee user, WorkOrder wo) throws Exception {
		WorkOrderEventMonitor old_woem=workOrderManagementMapper.getLastManhoursAlterEvent(wo.getId());
		if(CommonUtils.checkExistOrNot(old_woem)) {
			// 此次需要取消的分配操作为计入工时
			// 返还工时
			Manhours manhour=workOrderManagementMapper.getManhours(Integer.parseInt(wo.getProcessor()), old_woem.getCreate_time());
			BigDecimal return_standard_manhours=new BigDecimal(workOrderManagementMapper.getReturnStandardManhours(manhour.getId(), wo.getId()));
			// 分配工时
			Manhours mh=new Manhours();
			mh.setId(manhour.getId());
			mh.setUpdate_by(user.getId().toString());
			mh.setAllocated(manhour.getAllocated().subtract(return_standard_manhours));
			workOrderManagementMapper.allocatedManhours(mh);
			// 工时异动记录
			ManhoursAlter ma=new ManhoursAlter();
			ma.setId(UUID.randomUUID().toString());
			ma.setCreate_by(user.getId().toString());
			ma.setMh_id(mh.getId());
			ma.setWo_id(wo.getId());
			// true-加/false-减
			ma.setAlter_type(true);
			ma.setAlter_amount(return_standard_manhours.toString());
			ma.setRemark(workOrderManagementMapper.getEmployeeById(Integer.parseInt(wo.getProcessor())).getName() + "被返还" + return_standard_manhours + "分钟工时");
			workOrderManagementMapper.addManhoursAlter(ma);
			
		}
		// 不计入工时不需要异动
			
	}

	/**
	 * 
	 * @Description: TODO(扣去员工工时)
	 * @param user
	 * @param emp
	 * @param wo_id
	 * @param manhour
	 * @param standard_manhours
	 * @throws Exception
	 * @return: void  
	 * @author Ian.Huang 
	 * @date 2017年2月22日下午9:20:09
	 */
	private static void subtractManhours(Employee user, Employee emp, String wo_id, Manhours manhour, BigDecimal standard_manhours) throws Exception {
		// 分配工时
		if(manhour==null) {
			throw new Exception("当前工时为空");
		}
        Manhours mh= new Manhours();
        mh.setId(manhour.getId());
        mh.setUpdate_by(user.getId().toString());
        mh.setAllocated(manhour.getAllocated().add(standard_manhours));
        workOrderManagementMapper.allocatedManhours(mh);
        // 工时异动记录
        ManhoursAlter ma= new ManhoursAlter();
        ma.setId(UUID.randomUUID().toString());
        ma.setCreate_by(user.getId().toString());
        ma.setMh_id(mh.getId());
        ma.setWo_id(wo_id);
        // true-加/false-减
        ma.setAlter_type(false);
        ma.setAlter_amount(standard_manhours.toString());
        ma.setRemark(emp.getName() + "被扣去" + standard_manhours + "分钟工时");
        workOrderManagementMapper.addManhoursAlter(ma);
		
	}

	/**
	 * QA组扣除工时方法
	 * @param user
	 * @param wo_id
	 * @param manhour
	 * @param standard_manhours
	 * @throws Exception
	 */
	private static void subtractManhours2(Employee user, String wo_id, Manhours manhour, BigDecimal standard_manhours) throws Exception {
		// 分配工时
		if(manhour==null) {
			throw new Exception("当前工时为空");
		}
		Manhours mh= new Manhours();
		mh.setId(manhour.getId());
		mh.setUpdate_by(user.getId().toString());
		mh.setAllocated(manhour.getAllocated().add(standard_manhours));//已分配工时+需要扣除的工时
		workOrderManagementMapper.allocatedManhours(mh);
		// 工时异动记录
		ManhoursAlter ma= new ManhoursAlter();
		ma.setId(UUID.randomUUID().toString());
		ma.setCreate_by(user.getId().toString());
		ma.setMh_id(mh.getId());
		ma.setWo_id(wo_id);
		// true-加/false-减
		ma.setAlter_type(false);
		ma.setAlter_amount(standard_manhours.toString());
		ma.setRemark(user.getName() + "被扣去" + standard_manhours + "分钟工时");
		workOrderManagementMapper.addManhoursAlter(ma);

	}
	
	/**
	 * 
	 * @Description: TODO
	 * @param woemFlag
	 * @param woem
	 * @param woFlag
	 * @param new_wo
	 * @throws Exception
	 * @return: void  
	 * @author Ian.Huang 
	 * @date 2017年2月22日下午9:34:26
	 */
	private static void submit(Boolean woemFlag, WorkOrderEventMonitor woem, Boolean woFlag, WorkOrder new_wo) throws Exception {
		if(woemFlag || woFlag) {
			// 手动提交事务
		    WebApplicationContext contextLoader = ContextLoader.getCurrentWebApplicationContext();
		    DataSourceTransactionManager transactionManager = (DataSourceTransactionManager)contextLoader.getBean("transactionManager");
		    DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		    // 事物隔离级别，开启新事务，这样会比较安全些。
		    def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
		    // 获得事务状态
		    TransactionStatus status = transactionManager.getTransaction(def);
		    try {
		    	if(woemFlag) {
		    		workOrderManagementMapper.addWorkOrderEvent(woem);
		    		
		    	}
		    	if(woFlag) {
		    		workOrderManagementMapper.updateWorkOrder(new_wo);
		    		
		    	}
		        transactionManager.commit(status);
		        
		    } catch (Exception e) {
		        transactionManager.rollback(status);
		        throw e;
		        
		    }
			
		}
		
	}
	
	/**
	 * 
	 * @Description: TODO
	 * @param request
	 * @param param
	 * @throws Exception
	 * @return: WorkOrderEventMonitor  
	 * @author Ian.Huang 
	 * @date 2017年2月23日下午1:53:45
	 */
	@SuppressWarnings("unchecked")
	public static WorkOrderEventMonitor operateWorkOrder(HttpServletRequest request, Map<String, Object> param) throws Exception {
		// 获取工单授受双方
		Employee operator= null;
		Employee actor= null;
		if(CommonUtils.checkExistOrNot(request)) {
			operator= SessionUtils.getEMP(request);
			if(CommonUtils.checkExistOrNot(request.getParameter("account"))) {
				actor= workOrderManagementMapper.getEmployeeById(Integer.parseInt(request.getParameter("account")));
				
			} else {
				if(CommonUtils.checkExistOrNot(param.get("bySelf"))) {
					actor= operator;
					
				}
				
			}
			
		} else {
			operator= new Employee();
			operator.setId(0);
			operator.setName("系统");
			
		}
		if(param.get("event").toString().equals("zfworkorder")||param.get("event").toString().equals("zfworkorderqazu")){
            param.put("wo_id",request.getAttribute("wo_id"));
        }
		// 获取操作工单
		WorkOrder wo= workOrderManagementMapper.selectWorkOrderById(param.get("wo_id").toString());
		// 工单日志操作标志
		Boolean woemFlag= true;
		String eventDisplay= "";
		String event= param.get("event").toString();
		switch(event) {
		case Constant.WO_EVENT_ALLOC_M: eventDisplay= "手动分配";break;
		case Constant.WO_EVENT_CANCEL_ALLOC:
			if(CommonUtils.checkExistOrNot(param.get("moveType")) && param.get("moveType").equals("process")) {
				eventDisplay= "打回";
			} else {
				eventDisplay= "取消分配";
			}break;
		case Constant.WO_EVENT_START:
			if(wo.getWoProcessStatus().equals(Constant.WO_PROCESS_STATUS_NEW)) {
				eventDisplay= "开始处理";
			} else {
				eventDisplay= "进入";
				
			}break;
		case Constant.WO_EVENT_UPDATE:eventDisplay= "异动";break;
		case Constant.WO_EVENT_PAUSE:eventDisplay= "挂起";break;
		case Constant.WO_EVENT_RECOVER:eventDisplay= "取消挂起";break;
		case Constant.WO_EVENT_CANCEL:eventDisplay= "取消";break;
		case Constant.WO_EVENT_FIN:eventDisplay= "处理完成";break;
		case "saveOperate":
		default:break;
		
		}
		// 工单日志实体类
		WorkOrderEventMonitor woem= new WorkOrderEventMonitor(
				UUID.randomUUID().toString(),operator.getId().toString(), wo.getId(),
				workOrderManagementMapper.getSort(wo.getId()) + 1, event, false, eventDisplay);
		// 工单操作标志
		Boolean woFlag= false;
		// 工单实体定义
		WorkOrder new_wo= null;
		VerificationResult vr= verificate(operator, actor, wo, param);
		if(vr.isFlag()) {
			woem.setEvent_status(true);
			new_wo= new WorkOrder();
			new_wo.setId(wo.getId());
			new_wo.setUpdateBy(operator.getId().toString());
			switch(event) {
			case Constant.WO_EVENT_ALLOC_M:
	            boolean outManhours= false;
	            String manhoursRemark= "计入处理者工时；";
	            if(CommonUtils.checkExistOrNot(param.get("outManhours"))) {
	                outManhours= true;
	                manhoursRemark= "不计入处理者工时；";
	                
	            }
	            new_wo.setWoAllocStatus(Constant.WO_ALLOC_STATUS_ALD);
	            new_wo.setWoAllocStatusDisplay(endueDisplay(Constant.WO_ALLOC_STATUS_ALD));
	            new_wo.setAllocatedBy(operator.getId().toString());
	            new_wo.setAllocatedByDisplay(operator.getName());
	            new_wo.setProcessor(actor.getId().toString());
	            new_wo.setProcessorDisplay(actor.getName());
	            woem.setOutManhours(outManhours);
	            woem.setRemark(woem.getEvent_description() + "成功（分配者：" + operator.getName() + "；处理者：" + actor.getName() + "；" + manhoursRemark + "）");
	            if(!outManhours) {
	                subtractManhours(
	                		operator, actor, new_wo.getId(),
	                		workOrderManagementMapper.getManhours(actor.getId(), new SimpleDateFormat("yyyy-MM-dd").format(new Date())),
	                		new BigDecimal(workOrderManagementMapper.gethours(wo.getWoType(), wo.getWoLevel()).get("wk_standard").toString()));
	                
	            }
		        //----------------------------------添加日志---------------------------------------------------------
		        if("店铺创建".equals(wo.getWoSource()) && "ALLOC_M".equals(woem.getEvent())){
		        	woem.setRemark(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"/物流部/"+ operator.getName()+" /将工单手动分配给"+actor.getName());
		        	woem.setResult_info("");
		        	woem.setFile_path("");
		        	woem.setSort_store(String.valueOf(System.currentTimeMillis()));
		        	woem.setOut_result("1");
		        	woem.setOut_result_reason("成功");
		        	workOrderManagementMapper.add_log_pro(woem);
		        	if(!"1".equals(woem.getOut_result())){
		        		throw new RuntimeException(woem.getOut_result_reason());
		        	}
		        }
			    woFlag= true;
				break;
			case Constant.WO_EVENT_CANCEL_ALLOC:
				if(operator.getId().equals(0)) {
					 new_wo.setWoAllocStatus(Constant.WO_ALLOC_STATUS_WAA);
					 new_wo.setWoAllocStatusDisplay(endueDisplay(Constant.WO_ALLOC_STATUS_WAA));
					 
				} else {
					 new_wo.setWoAllocStatus(Constant.WO_ALLOC_STATUS_WMA);
					 new_wo.setWoAllocStatusDisplay(endueDisplay(Constant.WO_ALLOC_STATUS_WMA));
					
				}
		        new_wo.setWoProcessStatus(Constant.WO_PROCESS_STATUS_NEW);
		        new_wo.setWoProcessStatusDisplay(endueDisplay(Constant.WO_PROCESS_STATUS_NEW));
		        new_wo.setAllocatedBy("");
		        new_wo.setAllocatedByDisplay("");
		        new_wo.setProcessor("");
		        new_wo.setProcessorDisplay("");
		        if(CommonUtils.checkExistOrNot(wo.getProcessStartPoint())) {
		            new_wo.setActualManhours(wo.getActualManhours().add(new BigDecimal((new Date().getTime() - new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(wo.getProcessStartPoint().toString()).getTime())/1000/60 + "")));
		            
		        }
		    	addManhours(operator, wo);
			    woFlag= true;
			    
		        if("店铺创建".equals(wo.getWoSource()) && "CANCEL_ALLOC".equals(woem.getEvent())){
		        	woem.setRemark(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"/物流部/"+operator.getName()+"/将工单取消分配");
		        	woem.setResult_info("");
		        	woem.setFile_path("");
		        	woem.setSort_store(String.valueOf(System.currentTimeMillis()));
		        	woem.setOut_result("1");
		        	woem.setOut_result_reason("成功");
		        	workOrderManagementMapper.add_log_pro(woem);
		        	if(!"1".equals(woem.getOut_result())){
		        		throw new RuntimeException();
		        	}
		        }
				break;
			case Constant.WO_EVENT_START:
		        if(wo.getWoProcessStatus().equals(Constant.WO_PROCESS_STATUS_NEW)) {
		            new_wo.setWoProcessStatus(Constant.WO_PROCESS_STATUS_PRO);
		            new_wo.setWoProcessStatusDisplay(endueDisplay(Constant.WO_PROCESS_STATUS_PRO));
		            new_wo.setProcessStartPoint(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
				    woFlag= true;
				    woem.setRemark("开始处理");

					//判断当前工单处理人是否是Qa组
					List<Map<String,Object>> qaList = groupMapper.isQaById(operator.getId().toString());
					if(qaList.size()>0&&qaList.get(0).get("qaid").toString().equals(wo.getProcessor())){//是Qa组成员且是Qa组的工单
						new_wo.setProcessor(operator.getId().toString());
						new_wo.setProcessorDisplay(operator.getName());//改变处理人为当前操作人
						woem.setRemark("Qa组成员："+operator.getName()+"  领取工单开始处理");
						// 当前操作人扣除工时
						subtractManhours2(operator, new_wo.getId(), workOrderManagementMapper.getManhours(operator.getId(),
								new SimpleDateFormat("yyyy-MM-dd").format(new Date())),
								new BigDecimal(workOrderManagementMapper.gethours(wo.getWoType(),
										wo.getWoLevel()).get("wk_standard").toString()));

					}
		        } else {
		        	woemFlag= false;
		            
		        }

				break;
			case Constant.WO_EVENT_UPDATE:
				String remark= "";
				if(!param.get("wo_level").equals(wo.getWoLevel())) {
					new_wo.setWoLevel(param.get("wo_level") + "");
					new_wo.setWoLevelDisplay(param.get("wo_level_display") + "");
					new_wo.setLevelAlterReason(param.get("level_alter_reason")+ "");
					remark+= "工单级别异动：异动前（"
					+ workOrderManagementMapper.getLevelName(wo.getWoLevel())
					+ "）->异动后（"
					+ workOrderManagementMapper.getLevelName(param.get("wo_level") + "")
					+ "）；升降级原因："
					+ workOrderManagementMapper.getLevelAlterReasonById(Integer.parseInt(param.get("level_alter_reason") + ""))
					+ "；";
					new_wo.setStandardManhours(new BigDecimal(workOrderManagementMapper.gethours(wo.getWoType(), param.get("wo_level").toString()).get("wk_standard").toString()));
					
				}
				
				if(!param.get("wo_type").equals(wo.getWoType())) {
					new_wo.setWoType(param.get("wo_type") + "");
					new_wo.setWoTypeDisplay(param.get("wo_type_display") + "");
					remark+= "工单类型异动：异动前（"
					+ wo.getWoTypeDisplay()
					+ "）->异动后（"
					+param.get("wo_type_display")
					+ "）";
				}
				
				if(!param.get("exception_type").equals(wo.getExceptionType())) {
					if(CommonUtils.checkExistOrNot(param.get("exception_type"))) {
						new_wo.setExceptionType(param.get("exception_type") + "");
						
					} else {
						new_wo.setExceptionType("");
						
					}
					
				}
				// 转发工单
				if(CommonUtils.checkExistOrNot(actor)) {
					// 需转发工单
					new_wo.setWoProcessStatus(Constant.WO_PROCESS_STATUS_NEW);
					new_wo.setWoProcessStatusDisplay(endueDisplay(Constant.WO_PROCESS_STATUS_NEW));
					new_wo.setAllocatedBy(operator.getId().toString());
					new_wo.setAllocatedByDisplay(operator.getName());
		            new_wo.setProcessor(actor.getId().toString());
		            new_wo.setProcessorDisplay(actor.getName());
		            // 转发后工单升级内容初始化
		            new_wo.setWatched("1");
		            new_wo.setCal_date("");
		            new_wo.setLevel_starttime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		            // 结算实际工时
		            if(CommonUtils.checkExistOrNot(wo.getProcessStartPoint())) {
			            new_wo.setActualManhours(wo.getActualManhours().add(new BigDecimal((new Date().getTime() - new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(wo.getProcessStartPoint().toString()).getTime())/1000/60 + "")));
			            
			        }
		            woem.setOutManhours(false);
					// 当前用户还原工时
					addManhours(operator, wo);
					// 分配对象扣除工时
					subtractManhours(
							operator,
							actor,
							new_wo.getId(),
							workOrderManagementMapper.getManhours(actor.getId(),
									new SimpleDateFormat("yyyy-MM-dd").format(new Date())),
							new BigDecimal(workOrderManagementMapper.gethours(wo.getWoType(),
									param.get("wo_level").toString()).get("wk_standard").toString()));
		            //
		            remark+= operator.getName() + "转发工单号[" + wo.getWoNo() + "]予" + actor.getName() + "；";
					
				}
				woem.setRemark(remark);
			    woFlag= true;
				break;

                case "zfworkorder":
                	//拦截、改地址、催件、转寄、在途路由查询 这几个工单类型
				//	if(wo.getWoType().equals(""))
                    // 转发工单
                    if(CommonUtils.checkExistOrNot(actor)) {
                        // 需转发工单
                        new_wo.setWoProcessStatus(Constant.WO_PROCESS_STATUS_NEW);
                        new_wo.setWoProcessStatusDisplay(endueDisplay(Constant.WO_PROCESS_STATUS_NEW));
                        new_wo.setAllocatedBy(operator.getId().toString());
                        new_wo.setAllocatedByDisplay(operator.getName());
                        new_wo.setProcessor(actor.getId().toString());
                        new_wo.setProcessorDisplay(actor.getName());
                        // 转发后工单升级内容初始化
                        new_wo.setWatched("1");
                        new_wo.setCal_date("");
                        new_wo.setLevel_starttime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                        // 结算实际工时
                        if(CommonUtils.checkExistOrNot(wo.getProcessStartPoint())) {
                            new_wo.setActualManhours(wo.getActualManhours().add(new BigDecimal((new Date().getTime() - new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(wo.getProcessStartPoint().toString()).getTime())/1000/60 + "")));

                        }
                        woem.setOutManhours(false);
                        // 当前用户还原工时
                        addManhours(operator, wo);
                        // 分配对象扣除工时
                        subtractManhours(
                                operator,
                                actor,
                                new_wo.getId(),
                                workOrderManagementMapper.getManhours(actor.getId(),
                                        new SimpleDateFormat("yyyy-MM-dd").format(new Date())),
                                new BigDecimal(workOrderManagementMapper.gethours(wo.getWoType(),
                                        wo.getWoLevel()).get("wk_standard").toString()));
                        //
                        woem.setRemark(operator.getName() + "转发工单号[" + wo.getWoNo() + "]予" + actor.getName() + "；");
                    }
                    woFlag= true;
                    break;
				case  "zfworkorderqazu":
					//转发到Qa组
					List<Map<String, Object>> groups = groupMapper.selectGroup(request.getParameter("account"));
					// 需转发工单
					new_wo.setWoProcessStatus(Constant.WO_PROCESS_STATUS_NEW);
					new_wo.setWoProcessStatusDisplay(endueDisplay(Constant.WO_PROCESS_STATUS_NEW));
					new_wo.setAllocatedBy(operator.getId().toString());
					new_wo.setAllocatedByDisplay(operator.getName());
					new_wo.setProcessor(groups.get(0).get("id").toString());
					new_wo.setProcessorDisplay(groups.get(0).get("group_name").toString());
					// 转发后工单升级内容初始化
					new_wo.setWatched("1");
					new_wo.setCal_date("");
					new_wo.setLevel_starttime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
					// 结算实际工时
					if(CommonUtils.checkExistOrNot(wo.getProcessStartPoint())) {
						new_wo.setActualManhours(wo.getActualManhours().add(new BigDecimal((new Date().getTime() - new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(wo.getProcessStartPoint().toString()).getTime())/1000/60 + "")));

					}
					woem.setOutManhours(false);
					// 当前用户还原工时
					addManhours(operator, wo);
					woem.setRemark(operator.getName() + "转发工单号[" + wo.getWoNo() + "]予" + groups.get(0).get("group_name")+ "；");
					woFlag= true;
					break;

				case Constant.WO_EVENT_PAUSE:
		        new_wo.setWoProcessStatus(Constant.WO_PROCESS_STATUS_PAU);
		        new_wo.setWoProcessStatusDisplay(endueDisplay(Constant.WO_PROCESS_STATUS_PAU));
		        new_wo.setPauseReason(param.get("remark").toString());
		        if(CommonUtils.checkExistOrNot(wo.getProcessStartPoint())) {
		            new_wo.setActualManhours(wo.getActualManhours().add(new BigDecimal((new Date().getTime() - new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(wo.getProcessStartPoint().toString()).getTime())/1000/60 + "")));
		            
		        }
		        woem.setRemark("挂起原因：" + param.get("remark").toString());
			    woFlag= true;
				break;
			case Constant.WO_EVENT_RECOVER:
		        new_wo.setWoProcessStatus(Constant.WO_PROCESS_STATUS_PRO);
		        new_wo.setWoProcessStatusDisplay(endueDisplay(Constant.WO_PROCESS_STATUS_PRO));
		        new_wo.setPauseReason("");
		        new_wo.setProcessStartPoint(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			    woFlag= true;
				break;
			case Constant.WO_EVENT_CANCEL:
			    new_wo.setWoProcessStatus(Constant.WO_PROCESS_STATUS_CCD);
			    new_wo.setWoProcessStatusDisplay(endueDisplay(Constant.WO_PROCESS_STATUS_CCD));
			    if(CommonUtils.checkExistOrNot(wo.getProcessStartPoint())) {
			        new_wo.setActualManhours(wo.getActualManhours().add(new BigDecimal((new Date().getTime() - new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(wo.getProcessStartPoint().toString()).getTime())/1000/60 + "")));
			        
			    }
			    woFlag= true;
			    if(wo.getWoAllocStatus().equals(Constant.WO_ALLOC_STATUS_ALD)) {
			    	addManhours(operator, wo);
			    	
			    }
			    workOrderManagementMapper.cancelWaybillWarning(new_wo.getId());
				break;
			case Constant.WO_EVENT_FIN:
				new_wo.setWoProcessStatus(Constant.WO_PROCESS_STATUS_FIN);
				new_wo.setWoProcessStatusDisplay(endueDisplay(Constant.WO_PROCESS_STATUS_FIN));
			    if(CommonUtils.checkExistOrNot(wo.getProcessStartPoint())) {
			        new_wo.setActualManhours(wo.getActualManhours().add(new BigDecimal((new Date().getTime() - new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(wo.getProcessStartPoint().toString()).getTime())/1000/60 + "")));
			        
			    }
			    woFlag= true;
			    break;
			case "saveOperate":
				// 获取内容
			    Map<String, Object> content= null;
			    String userId = null;
			    if(CommonUtils.checkExistOrNot(request)) {
			    	content= JSON.parseObject(request.getParameter("json").toString(), Map.class);
			    	userId = String.valueOf(SessionUtils.getEMP(request).getId());
			    } else {
			    	content= (Map<String, Object>) param.get("content");
			    	userId="0";
			    }
			    if(content.containsKey("operation_type")) {
			    	Integer operation_type=Integer.parseInt(content.get("operation_type").toString());
			    	if(operation_type == 1 || operation_type == 2) {
			    		// 日志变更
			    		woem.setEvent(Constant.WO_EVENT_FIN);
			    		woem.setEvent_description("处理完成");
			    		woem.setRemark("处理完成");
			    		// 处理工单
			    		new_wo.setWoProcessStatus(Constant.WO_PROCESS_STATUS_FIN);
						new_wo.setWoProcessStatusDisplay(endueDisplay(Constant.WO_PROCESS_STATUS_FIN));
					    if(CommonUtils.checkExistOrNot(wo.getProcessStartPoint())) {
					        new_wo.setActualManhours(wo.getActualManhours().add(new BigDecimal((new Date().getTime() - new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(wo.getProcessStartPoint().toString()).getTime())/1000/60 + "")));
					        
					    }
					    woFlag= true;
			    		
			    	}
			    	
			    }
			    // 保存主信息
			    Operation op= new Operation();
			    String remark_wl="";
			    String result_info_wl="";
			    String remark_omsc = "";
			    String file_path_wl="";
			    op.setWo_id(content.get("wo_id").toString());
			    op.setWo_type(content.get("wo_type").toString());
			    List<Map<String, Object>> values= JSONArray.parseObject(content.get("values").toString(), List.class);
			    for(int i= 0; i< values.size(); i++) {
			        Map<String, Object> value= values.get(i);
			        if("sendTime".equals(value.get("code").toString())){
			            continue;
		            }
			        op.setColumn_code(value.get("code").toString());
			        op.setColumn_value(value.get("value").toString());
			        if(workOrderManagementMapper.judgeOperationExist(op) != 0) {
			            // 存在则更新
			            op.setUpdate_by(operator.getId().toString());
			            workOrderManagementMapper.updateOperation(op);
			            
			        } else {
			            // 不存在则新增
			            op.setId(UUID.randomUUID().toString());
			            op.setCreate_by(operator.getId().toString());
			            workOrderManagementMapper.addOperation(op);
			        }
			        remark_wl=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"<br/>物流部/"+operator.getName()+"<br/>将工单提交至店铺";
			        result_info_wl="resultInfo".equals(op.getColumn_code())?op.getColumn_value():result_info_wl;
			        remark_omsc="remark".equals(op.getColumn_code())?op.getColumn_value():remark_omsc;
			        file_path_wl="fileName".equals(op.getColumn_code())?op.getColumn_value():file_path_wl;
			        
			        if(op.getColumn_code().equals("claim_status")) {
			        	switch(op.getColumn_value()) {
			        	case "0":
			        		// 暂作理赔
			        		// 记录索赔开始节点
			        		Map<String, Object> claim_start_point= new HashMap<String, Object>();
			        		claim_start_point.put("code", "claim_start_point");
			        		Calendar c = Calendar.getInstance();
			        		claim_start_point.put("value", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(c.getTime()));
			        		values.add(claim_start_point);
			        		// 新增索赔任务
			        		c.add(Calendar.DATE, workOrderManagementMapper.getWarningDays());
			        		workOrderManagementMapper.addCliamSchedule(
			        				new ClaimSchedule(
			        						UUID.randomUUID().toString(),
			        						operator.getId().toString(),
			        						op.getWo_id(),
			        						op.getWo_type(),
			        						new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(c.getTime())
			        						
			        				));
			        		break;
			        	
			        	case "1":
			        		// 索赔成功
			        		;
			        		
			        	case "2":
			        		// 索赔失败
			        		// 清空索赔开始节点
			        		Map<String, Object> claim_start_point2= new HashMap<String, Object>();
			        		claim_start_point2.put("code", "claim_start_point");
			        		claim_start_point2.put("value", "");
			        		values.add(claim_start_point2);
			        		// 删除工单对应索赔任务
			        		workOrderManagementMapper.delCliamSchedule(op.getWo_id());
			        		//更新索赔失败原因 
			        		workOrderManagementMapper.updateFailureReason(op.getWo_id(),JSON.parseObject(request.getParameter("json").toString(), Map.class).get("failure_reason").toString());
			        		
			        		break;
			        	default:break;
			        	}
			        	
			        }
			    }
		        //----------------------------------添加日志---------------------------------------------------------
			    
			    //增加交互日志
				WoMutualLog record = new WoMutualLog();
				record.setAccessory(file_path_wl);
				record.setAction("saveOperate");
				record.setCreateBy(userId);
				record.setCreateTime(new Date());
				String remark_mu = "";
				if("店铺创建".equals(wo.getWoSource())){
					remark_mu=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"/物流部/"+operator.getName()+"/将工单处理回复至店铺";
					record.setProcessRemark(result_info_wl);
				}else if("人工创建".equals(wo.getWoSource())){
					remark_mu=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"/物流部/"+operator.getName()+"/处理人工创建的工单";
					record.setProcessRemark(result_info_wl);
				}else if("OMS".equals(wo.getWoSource())){
					remark_mu=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"/物流部/"+operator.getName()+"/处理OMS工单";
					record.setProcessRemark(remark_omsc);
				}else if("快递雷达".equals(wo.getWoSource())){
					remark_mu=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"/物流部/"+operator.getName()+"/处理快递雷达工单";
					record.setProcessRemark(result_info_wl);
				}else{
					remark_mu = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"/物流部/"+operator.getName()+"/处理工单";
					record.setProcessRemark(result_info_wl);
				}
				record.setLog(remark_mu);
				record.setWoNo(wo.getWoNo());
				woMutualLogMapper.insert(record);
				
			    if("店铺创建".equals(wo.getWoSource()) && "FIN".equals(woem.getEvent())){
		        	woem.setRemark(remark_wl);
		        	woem.setResult_info(result_info_wl);
		        	woem.setFile_path(file_path_wl);
		        	woem.setSort_store(String.valueOf(System.currentTimeMillis()));
		        	woem.setOut_result("1");
		        	woem.setOut_result_reason("成功");
		        	workOrderManagementMapper.add_log_pro(woem);
		        	
		        	String email = workOrderManagementMapper.getEmailByWoNo(wo.getWoNo());
					if(CommonUtils.checkExistOrNot(email)){
						String content2 = "您收到一个回复的工单，工单号为【"+wo.getWoNo()+"】，请及时处理！";
						MailUtil.sendWorkOrderMail(email,"LMIS工单提醒",content2);
					}
		        	
		        	if(!"1".equals(woem.getOut_result())){
		        		throw new RuntimeException();
		        	}
		        	
		        }
			    // 存在明细数据
			    if(content.containsKey("detail")) {
			    	// 删除从表
					workOrderManagementMapper.delClaimDetailByWid(content.get("wo_id").toString());
					// 新增从表记录
					List<ClaimDetail> claimDetail= fJSONArray2ClaimDetails(JSONArray.parseArray(content.get("detail").toString()), operator.getId().toString());
					if(CommonUtils.checkExistOrNot(claimDetail)) {
						workOrderManagementMapper.addCliamDetail(claimDetail);
						
					}
			    	
			    }
			    if(!woFlag) {
			    	woemFlag= false;
			    	
			    }
			    break;
			default:break;
			
			}
			
		} else {
			if(vr.getError_type().equals(0)) {
				woemFlag= false;
				
			}
			woem.setRemark("工单（号）[" + wo.getWoNo() + "]" + woem.getEvent_description() + "失败，失败原因：" + vr.getError_message());
			
		}
		submit(woemFlag, woem, woFlag, new_wo);
		if(CommonUtils.checkExistOrNot(vr.getError_type()) && vr.getError_type().equals(2)) {
			param= new HashMap<String, Object>();
			param.put("event", Constant.WO_EVENT_CANCEL_ALLOC);
			param.put("wo_id", wo.getId());
			operateWorkOrder(null, param);
			
		}
		return woem;
		
	}
	
	private static List<ClaimDetail> fJSONArray2ClaimDetails(JSONArray detail, String operator) {
		List<ClaimDetail> claimDetails= new ArrayList<ClaimDetail>();
		for(int i= 0; i< detail.size(); i++) {
			ClaimDetail claimDetail= JSON.parseObject(detail.get(i).toString(), ClaimDetail.class);
	        claimDetail.setClaimDetailId(UUID.randomUUID().toString());
	        claimDetail.setCreate_by(operator);
	        claimDetails.add(claimDetail);
	        
		}
		return claimDetails;
		
	}
	
	/**
	 * 
	 * @Description: TODO
	 * @param groups
	 * @param temp
	 * @return: List<Map<String,Object>>  
	 * @author Ian.Huang 
	 * @date 2017年4月21日上午11:41:30
	 */
	public static List<Map<String, Object>> getSameGrouops(List<Map<String, Object>> groups, List<Map<String, Object>> temp) {
		Map<String, Object> tunnel= new LinkedHashMap<String, Object>();
		for(int i=0; i<groups.size(); i++) {
			tunnel.put(groups.get(i).get("id").toString(), groups.get(i).get("group_name"));
			
		}
		groups= new ArrayList<Map<String, Object>>();
		for(int i=0; i<temp.size(); i++) {
			if(tunnel.containsKey(temp.get(i).get("id").toString())) {
				groups.add(temp.get(i));
				
			}
			
		}
		return groups;
		
	}
	
}