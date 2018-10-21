package com.bt.thread;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import com.bt.lmis.model.ErrorAddress;
import com.bt.lmis.model.OperatorBean;
import com.bt.lmis.model.StoreEmployee;
import com.bt.lmis.service.AddressService;
import com.bt.lmis.service.JkErrorService;
import com.bt.workOrder.service.ShopGroupService;

public class InsertData {
	public static WebApplicationContext context;
	public InsertData(){
		if(context==null){
			 context = ContextLoader.getCurrentWebApplicationContext();
		}
	}
	
	
	/**
	 * 运单导入
	 * @param param
	 * @param taskNum
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String,String> insertData(Map<Object,Object>param,String taskNum){
		Map<String,String> result=new HashMap<String,String>();
		result.put("out_result", "1");
		int task=Integer.valueOf(taskNum);
		AddressService service = (AddressService)context.getBean("addressServiceImpl");
		List<ErrorAddress> list=(List<ErrorAddress>)param.get("orderData");
		//计算所需线程数
		List<ErrorAddress>sub_list=list.subList((task-1)*500,task*500>list.size()?list.size():task*500);
		try {
			service.insertOrder(sub_list);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			result.put("out_result", "0");
		}
		return result;
	}
	
	
	
	
	/**
	 * 操作费导入
	 * @param param
	 * @param taskNum
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String,String> insertOperData(Map<Object,Object>param,String taskNum){
		Map<String,String> result=new HashMap<String,String>();
		result.put("out_result", "1");
		int task=Integer.valueOf(taskNum);
		JkErrorService service = (JkErrorService)context.getBean("jkErrorServiceImpl");
		List<OperatorBean> list=(List<OperatorBean>)param.get("OperatorData");
		//计算所需线程数
		List<OperatorBean>sub_list=list.subList((task-1)*500,task*500>list.size()?list.size():task*500);
		try {
			service.insertOper(sub_list);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			result.put("out_result", "0");
		}
		return result;
	}
	
	
	
	/**
	 * 操作费导入
	 * @param param
	 * @param taskNum
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String,String> insertEmpData(Map<Object,Object>param,String taskNum){
		Map<String,String> result=new HashMap<String,String>();
		result.put("out_result", "1");
		int task=Integer.valueOf(taskNum);
		ShopGroupService service = (ShopGroupService)context.getBean("shopGroupServiceImpl");
		List<StoreEmployee> list=(List<StoreEmployee>)param.get("empData");
		//计算所需线程数
		List<StoreEmployee>sub_list=list.subList((task-1)*500,task*500>list.size()?list.size():task*500);
		try {
			service.insertEmp(sub_list);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			result.put("out_result", "0");
		}
		return result;
	}
	
	
	
	
}
