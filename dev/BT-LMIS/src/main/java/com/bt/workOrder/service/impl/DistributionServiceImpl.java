package com.bt.workOrder.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.UUID;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Service;

import com.bt.lmis.base.spring.SpringUtils;
import com.bt.utils.observer.Observers;
import com.bt.utils.observer.Visual;
import com.bt.workOrder.dao.DistributionMapper;
import com.bt.workOrder.service.DistributionService;


@Service
public class DistributionServiceImpl implements DistributionService{
	
	@SuppressWarnings("unchecked")
	DistributionMapper<T> mapper = (DistributionMapper)SpringUtils.getBean("distributionMapper");
	
	public DistributionMapper<T> getMapper() {
		return mapper;
	}
		
	public Map<String, String> automatic_distribution() {
		HashMap<String, Object> param = new HashMap<>();
		System.out.println("工单自动分配定时开启..>");
		List<Map<String,Object>> person=null;
		Map<String,Object> result = null;
				if(Observers.queue.peek()==null){
					try {
						System.out.println("自动查询数据");
						Visual visual=new Visual();
						visual.addObserver(Observers.getInstance());
						visual.setData(null);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else{
					try {
						result=Observers.queue.peek();
						System.out.println("---------开始查找组别信息----------");
						List<HashMap<String,String>> team_list=findTeam();   //查找组别
						if(team_list==null || team_list.size()==0){
							result.put("remark",result.get("wo_type_name")+","+result.get("wo_level_name")+",未找到可处理的组别");
							addLogInfo(result);
							upStatus(result);
						}
						System.out.println("---------开始查找客服信息----------");
						if("1".equals(param.get("if_beforehand"))){
						  System.out.println("---------预分配模式----------");
						  person=findStaff_beforehand(team_list);//查找客服
						}else{
							System.out.println("---------非预分配模式----------");
						    person=findStaff(team_list);//查找客服
						}
						if(person==null || person.size()==0){
							result.put("remark",result.get("wo_type_name")+","+result.get("wo_level_name")+",未找到可处理的客服");
							addLogInfo(result);
							upStatus(result);
						}else{
							System.out.println("---------开始分配工单 信息----------");
							distribution(person);                               // 分配工单 
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
//			}
		return null;
	}
	
	
	public void addLogInfo(Map<String,Object> result){
		result.put("id",UUID.randomUUID().toString());
		Integer maxId=mapper.getMaxId(result);
		result.put("sort", maxId);
		mapper.insertLogInfo(result);
	}
	
	public void upStatus(Map<String,Object> result){
		mapper.updateInfo(result);
	}
	
	public void distribution(List<Map<String,Object>>list){
		for(int i=0;i<list.size();i++){
			Map<String,Object> param=list.get(i);
			param.put("out_result","1");
			param.put("out_result_reason","成功");
			mapper.wk_distribution_pro(param);
			if(!param.get("out_result").equals("1")){
			  System.out.println("新分配流程.............");
			  param.put("remark",param.get("out_result_reason"));
			  addLogInfo(param);
			  if(list.size()==i){
				  upStatus(param);
			  }
			  continue;
			}else{
				System.out.println(param);
				break;
			}
//			if(!param.get("out_result").equals("1")){
//			param.put("remark",param.get("out_result_reason"));
//			addLogInfo(param);
//			}else{
//			break;
//			}
			
		}
	}
	
	/**
	 * 
	* @Title: findTeam 
	* @param @return    设定文件 
	* @author likun   
	* @return List<HashMap<String,String>>    返回类型 
	* @throws
	 */
	public List<HashMap<String,String>> findTeam(){
		Queue<HashMap<String,Object>> map_queue=Observers.queue;
		List<HashMap<String,String>>group_list=new ArrayList<HashMap<String,String>>();
		HashMap<String,Object> result=map_queue.poll();
		List<HashMap<String,String>>first_list=mapper.getFirstResult(result);
		for(int k=0;k<first_list.size();k++){
			HashMap<String,String> obj=first_list.get(k);
			if(obj!=null && !(Boolean)result.get("warehouse_type")){//自营仓
				result.put("selfwarehouseFlag","1");
			}
			if(obj!=null && (Boolean)result.get("warehouse_type")){//外包仓
				result.put("outsourcedwarehouseFlag","1");
			}
			result.put("group", obj.get("group"));
			List<HashMap<String,String>>second_list=mapper.getSecondResult(result);
			group_list.addAll(second_list);
		 }
		return group_list;
	}
	
	public List<Map<String,Object>> findStaff(List<HashMap<String,String>> param){
		List<Map<String,Object>>obj_list=new ArrayList<Map<String,Object>>();
		for(int i=0;i<param.size();i++){
			//查询组别下，可用工时最大的客服
			Map<String,String>group=param.get(i);
			Map<String,Object>obj=mapper.getMaxUsable(group);
			if(obj==null)
			continue;
			obj.put("emp_id",obj.get("employee"));
			obj_list.add(obj);
		}

		if(obj_list==null || obj_list.size()==0){
			return null;
		}
		Collections.sort(obj_list,new Comparator<Map<String,Object>>(){
			@Override
			public int compare(Map<String, Object> map1, Map<String, Object> map2) {
				BigDecimal a1=(BigDecimal)map1.get("usable_time");
				BigDecimal a2=(BigDecimal)map2.get("usable_time");
	            return a2.compareTo(a1);
			}
		});
		return obj_list;
	}

	
	
	public List<Map<String,Object>> findStaff_beforehand (List<HashMap<String,String>> param){
		List<Map<String,Object>>obj_list=new ArrayList<Map<String,Object>>();
		HashMap<String, Object>obj=null;
		for(int i=0;i<param.size();i++){
			//查询组别下，可用工时最大的客服
			Map<String,String>group=param.get(i);
			List<HashMap<String, Object>>list_obj=mapper.getMaxUsable_beforehand(group);
			if(list_obj==null)
			  continue;
			
			for(int k=0;k<list_obj.size();k++){
				obj=list_obj.get(k);
				obj.put("emp_id",obj.get("employee"));
				obj_list.add(obj);
			}
		}

		if(obj_list==null || obj_list.size()==0){
			return null;
		}
		
//		Collections.sort(obj_list,new Comparator<Map<String,Object>>(){
//			@Override
//			public int compare(Map<String, Object> map1, Map<String, Object> map2) {
//				int a1=Integer.valueOf((String)map1.get("usable_time"));
//				int a2=Integer.valueOf((String)map2.get("usable_time"));
//				int t1=Integer.valueOf((String)map1.get("date_time"));
//				int t2=Integer.valueOf((String)map2.get("date_time"));
//				if(a1==a2 && t1==t2){
//					return 0;
//				}
//				if(a1>a2 && t1<t2){
//					return -1;
//				}
//				return 1;
//			}
//			
//		});
		return obj_list;
	}
	
	@Override
	public List<HashMap<String,Object>>getOrderInfo(Map<String,String> param){
		List<HashMap<String,Object>>list=mapper.getOrderInfo(param);
		if(list ==null){
			list=new ArrayList<HashMap<String,Object>>();
		}
		return list;
	}

	@Override
	public HashMap<String, Object> getSetParam(HashMap<String, String> param) {
		return mapper.getSetParam(param);
	}

	/* (non-Javadoc)
	 * @see com.bt.workOrder.service.DistributionService#automatic_distribution(java.util.HashMap)
	 */
	@Override
	public Map<String, String> automatic_distribution(HashMap<String, Object> param) {
		return null;
	}
}
