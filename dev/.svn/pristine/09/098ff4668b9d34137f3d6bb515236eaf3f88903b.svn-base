package com.bt.lmis.summary;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.poi.ss.formula.functions.T;

import com.bt.lmis.base.spring.SpringUtils;
import com.bt.lmis.service.TransSettleService;
import com.bt.utils.observer.Observers;

/**
 * 
* @ClassName: TransSettleTask 
* @Description: TODO(物流结算) 
* @author likun
* @date 2016年7月21日 上午10:12:11 
*
 */
public class TransSettleTask {
/**
 * 
* @Title: transSettleTask 
* @Description: TODO(承运商明细结算) 
* @param     设定文件 
* @author likun   
* @return void    返回类型 
* @throws
 */
	@SuppressWarnings("unchecked")
	public static void transSettleTask(){
		try {
			HashMap<String,String>param=new HashMap<String,String>();
			param.put("out_result", "");
			param.put("out_result_reason", "");
			param.put("type", "1");
			ArrayList<Object> conList=((TransSettleService<T>)SpringUtils.getBean("transSettleServiceImpl")).carriageContractToSettle(param);
			for(int i=0;i<conList.size();i++){
				HashMap<String,String>separam=(HashMap<String,String>)conList.get(i);
				separam.put("type","1");
				((TransSettleService<T>)SpringUtils.getBean("transSettleServiceImpl")).carriageSettleByContract((HashMap<String,String>)conList.get(i));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	* @Title: transPoolTask 
	* @Description: TODO(承运商汇总结算) 
	* @param     设定文件 
	* @author likun   
	* @return void    返回类型 
	* @throws
	 */
	@SuppressWarnings("unchecked")
	public void transPoolTask(){
		try {
			HashMap<String,String>param=new HashMap<String,String>();
			param.put("out_result ", "");
			param.put("out_result_reason", "");
			((TransSettleService<T>)SpringUtils.getBean("transSettleServiceImpl")).transPool(param);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	/**
	 * 
	* @Title: storeSettle 
	* @Description: TODO(店铺明细结算) 
	* @param     设定文件 
	* @author likun   
	* @return void    返回类型 
	* @throws
	 */
		@SuppressWarnings("unchecked")
		public void storeSettle(){
			try {
				HashMap<String,String>param=new HashMap<String,String>();
				param.put("out_result ", "");
				param.put("out_result_reason", "");
				param.put("type", "2");
				ArrayList<Object> conList=((TransSettleService<T>)SpringUtils.getBean("transSettleServiceImpl")).carriageContractToSettle(param);
				for(int i=0;i<conList.size();i++){
					HashMap<String,String>separam=(HashMap<String,String>)conList.get(i);
					separam.put("type","2");
					((TransSettleService<T>)SpringUtils.getBean("transSettleServiceImpl")).carriageSettleByContract(separam);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		/**
		 * 
		* @Title: storeSettle 
		* @Description: TODO(客户明细结算) 
		* @param     设定文件 
		* @author likun   
		* @return void    返回类型 
		* @throws
		 */
			@SuppressWarnings("unchecked")
			public void customerSettle(){
				try {
					HashMap<String,String>param=new HashMap<String,String>();
					param.put("out_result ", "");
					param.put("out_result_reason", "");
					param.put("type", "3");
					ArrayList<Object> conList=((TransSettleService<T>)SpringUtils.getBean("transSettleServiceImpl")).carriageContractToSettle(param);
					for(int i=0;i<conList.size();i++){
						HashMap<String,String>separam=(HashMap<String,String>)conList.get(i);
						separam.put("type","3");
						((TransSettleService<T>)SpringUtils.getBean("transSettleServiceImpl")).carriageSettleByContract(separam);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		
	/**
	 * 
	* @Title: customerPoolTask 
	* @Description: TODO(客户运费结算汇总) 
	* @param     设定文件 
	* @author likun   
	* @return void    返回类型 
	* @throws
	 */
	@SuppressWarnings("unchecked")
	public void customerPoolTask(){
		try {
			HashMap<String,String>param=new HashMap<String,String>();
			param.put("out_result ", "");
			param.put("out_result_reason", "");
			((TransSettleService<T>)SpringUtils.getBean("transSettleServiceImpl")).customerPool(param);
			System.out.println(param.get("out_result"));
			System.out.println(param.get("out_result_reason"));
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	* @Title: customerPoolTask 
	* @Description: TODO(客户运费结算汇总) 
	* @param     设定文件 
	* @author likun   
	* @return void    返回类型 
	* @throws
	 */
	@SuppressWarnings("unchecked")
	public void storePoolTask(){
		try {
			HashMap<String,String>param=new HashMap<String,String>();
			param.put("out_result ", "");
			param.put("out_result_reason", "");
			((TransSettleService<T>)SpringUtils.getBean("transSettleServiceImpl")).storePool(param);
			System.out.println(param.get("out_result"));
			System.out.println(param.get("out_result_reason"));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}
