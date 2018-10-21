package com.bt.utils.observer;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Queue;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;

import com.bt.lmis.base.spring.SpringUtils;
import com.bt.workOrder.dao.DistributionMapper;
import com.bt.workOrder.service.DistributionService;


/**
 * 
* @ClassName: Observers 
* @Description: TODO(观察者) 
* @author likun
* @date 2017年1月18日 下午1:13:46 
*
 */
public class Observers implements Observer {
	public static Queue<HashMap<String,Object>> queue=new LinkedList<HashMap<String,Object>>();
	private static Observers observers;
	@Autowired
	private DistributionMapper<T> mapper;
	public DistributionMapper<T> getMapper() {
		return mapper;
	}
	public static Observers getInstance(){
		if(observers==null){
			observers=new Observers();
		}
		return observers;
	}
	
		
	@Override
	public void update(Observable object, Object argument) {
		// TODO Auto-generated method stub
		getOrderInfo(((Visual) object).getData());
	}

	
	
	
	synchronized public   void getOrderInfo(String woId){
		//查询新增的工单信息并加入到队列中
		DistributionService service=(DistributionService)SpringUtils.getBean("distributionServiceImpl");
		//查询自动分配配置信息
		HashMap<String,Object>param=service.getSetParam(new HashMap<String,String>());
		
		if("1".equals(param.get("status"))){
            Map<String,String>pa=new HashMap<String,String>();
			pa.put("woId",woId);
			List<HashMap<String,Object>>list=service.getOrderInfo(pa);
			for(int i=0;i<list.size();i++){
				queue.offer(list.get(i));
			}
		}
	}
}
