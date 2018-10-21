package com.bt.lmis.service.impl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bt.lmis.code.ServiceSupport;
import com.bt.lmis.dao.TransSettleMapper;
import com.bt.lmis.service.TransSettleService;



/**
 * 
* @ClassName: TransSettleServiceImpl 
* @Description: TODO(物流结算) 
* @author likun
* @date 2016年7月21日 上午10:15:56 
* 
* @param <T>
 */
@Service
public class TransSettleServiceImpl<T> extends ServiceSupport<T> implements TransSettleService<T>{
	@Autowired
	private TransSettleMapper<T>transSettleMapper;
	
	
	/**
	 * 获取需要结算的合同信息
	 * type(1:承运商,2:店铺,3:客户)
	 *
	 */
	@Override
	public ArrayList<Object> carriageContractToSettle(HashMap<String, String> param) throws RuntimeException {
		// TODO Auto-generated method stub
		ArrayList<Object>contractList=new 	ArrayList<Object>();
		if("1".equals(param.get("type"))){
			contractList=transSettleMapper.carriageContract(param);	
		}		
		if("2".equals(param.get("type"))){
			contractList=transSettleMapper.storeContract(param);
		}
		if("3".equals(param.get("type"))){
			contractList=transSettleMapper.customerContract(param);
		}		
		if(contractList==null ||contractList.size()==0 ){
			throw new RuntimeException("未找到结算合同数据");
		}
		return contractList;
	}
	
	
	
/**
 * 结算主体方法
 */
	@Override	
    public HashMap<String, String> carriageSettleByContract(HashMap<String, String> param) throws RuntimeException {
		ArrayList<Object>settleDetail_one=new ArrayList<>();//整车，零担
	    ArrayList<Object>settleDetail_two=new ArrayList<>();//商品类型
		//承运商明细结算原始数据
		if("1".equals(param.get("type"))){
			settleDetail_one=transSettleMapper.carriageSettleEveryDay(param);
		}
		//店铺明细结算原始数据
		if("2".equals(param.get("type"))){
			settleDetail_one=transSettleMapper.storeSettleEveryDay(param);
		}
		//客户明细结算原始数据
		if("3".equals(param.get("type"))){
			settleDetail_one=transSettleMapper.customerSettleEveryDay(param);
		}
		//获取对应合同需要结算的商品类型结算原始数据
			//settleDetail_two=transSettleMapper.getSettleDataForGt(param);	
		//整车零担明细结算
		if(settleDetail_one.size()>0){
		     settle_zc_ld(param,settleDetail_one);
		}
		//商品类型明细结算
        if(settleDetail_two.size()>0){
        	settle_gt(param, settleDetail_two);
        }
    		return null;
     }

	
  /**
   * 承运商费用汇总
   */
	@Override
	public HashMap<String, String> transPool(HashMap<String, String> param) {
		// TODO Auto-generated method stub
		 transSettleMapper.transPool(param);
		 return null;
	}	
	
	/**
	 * 店铺承运商费用汇总(包含商品类型)
	 */
	@Override
	public HashMap<String, String> storePool(HashMap<String, String> param) {
		// TODO Auto-generated method stub
	    transSettleMapper.storePool(param);
		return null;
	}
	
	/**
	 * 客户承运商费用汇总(包含商品类型)
	 */
	@Override
	public HashMap<String, String> customerPool(HashMap<String, String> param) throws RuntimeException {
		// TODO Auto-generated method stub
	     transSettleMapper.customerPool(param);
		 return null;
	}

	/**
	 * 
	* @Title: settle_zc_ld 
	* @Description: TODO(整车。零担的结算主体方法) 
	* @param @param param
	* @param @param settleDetail_one
	* @param @return    设定文件 
	* @author likun   
	* @return HashMap    返回类型 
	* @throws
	 */
	public HashMap<String,String>settle_zc_ld(HashMap<String, String> param,ArrayList<Object>settleDetail_one){
		for(int k=0;k<settleDetail_one.size();k++){
			String result_str="";
			HashMap<Object,Object>result_Info=new HashMap<Object,Object>();
			//找到对应的计费公式
			HashMap<Object,Object>yf_param=(HashMap<Object,Object>)settleDetail_one.get(k);
			
			param.put("carrierCode", (String)yf_param.get("transport_code"));
			HashMap<Object,Object>formulaInfo=transSettleMapper.getFormula(param);
			
			yf_param.put("out_result", "");
			yf_param.put("out_result_reason", "");
			yf_param.put("low_weight", formulaInfo.get("low_weight"));
			//结算过程:计抛计重取大值
			if(formulaInfo.get("cat_type").equals("1")){
				transSettleMapper.carriageSettlePz(yf_param);
			}
			//结算过程:按首重+续重结算方式
			if(formulaInfo.get("cat_type").equals("2")){
				transSettleMapper.carriageSettleXz(yf_param);
			}    
			//结算过程:按公斤结算方式
			if(formulaInfo.get("cat_type").equals("3")){
				transSettleMapper.carriageSettleGj(yf_param);
			}
			result_str=result_str+yf_param.get("out_result_reason");
			//保价费结算过程
			HashMap<Object,Object>bj_param=(HashMap<Object,Object>)settleDetail_one.get(k);
			bj_param.put("out_result", "");
			bj_param.put("out_result_reason", "");
			transSettleMapper.carriageSettleBj(bj_param);
			result_str=result_str+bj_param.get("out_result_reason");
			//管理费结算过程
			HashMap<Object,Object>gl_param=(HashMap<Object,Object>)settleDetail_one.get(k);
			gl_param.put("out_result", "");
			gl_param.put("out_result_reason", "");
			transSettleMapper.carriageSettleGl(gl_param);
			result_str=result_str+gl_param.get("out_result_reason");
			//特殊服务费结算过程
			HashMap<String,String>tf_param=(HashMap<String,String>)settleDetail_one.get(k);
			tf_param.put("out_result", "");
			tf_param.put("out_result_reason", "");
			transSettleMapper.carriageSettleTf(tf_param);
			result_str=result_str+tf_param.get("out_result_reason");
			//更新结算标记
			if("1".equals(yf_param.get("out_result"))
				&&"1".equals(bj_param.get("out_result"))
				&& "1".equals(gl_param.get("out_result"))
				&&"1".equals(tf_param.get("out_result"))){
				yf_param.put("settle_flag", "1");
			}else{
				yf_param.put("settle_flag", "2");
			}
			transSettleMapper.updateSettleInfo(yf_param);
		    transSettleMapper.updateLogisInfo(yf_param);
			yf_param.put("resultInfo", result_str);
			transSettleMapper.insertResultInfo(yf_param);
		}
		return param;
	}
	
	
	/**
	 * 
	* @Title: settle_gt 
	* @Description: TODO(商品类型明细结算过程) 
	* @param @param param
	* @param @param settleDetail_two
	* @param @return    设定文件 
	* @author likun   
	* @return HashMap    返回类型 
	* @throws
	 */
	public HashMap<String,String>settle_gt(HashMap<String, String> param,ArrayList<Object>settleDetail_two){
		for(int k=0;k<settleDetail_two.size();k++){
			HashMap<Object,Object>yf_param=(HashMap<Object,Object>)settleDetail_two.get(k);
			HashMap<Object,Object>bj_param=(HashMap<Object,Object>)settleDetail_two.get(k);
			HashMap<Object,Object>gl_param=(HashMap<Object,Object>)settleDetail_two.get(k);
			HashMap<String,String>tf_param=(HashMap<String,String>)settleDetail_two.get(k);
			
			//运费结算过程
			yf_param.put("out_result", "");
			yf_param.put("out_result_reason", "");
			transSettleMapper.carriageSettleGt(yf_param);
//			System.out.println("管理费:"+gl_param.get("out_result"));
			
			//保价费结算过程
			bj_param.put("out_result", "");
			bj_param.put("out_result_reason", "");
			transSettleMapper.carriageSettleBjGt(bj_param);
//			System.out.println("保价费:"+bj_param.get("out_result"));
			
			//管理费结算过程
			gl_param.put("out_result", "");
			gl_param.put("out_result_reason", "");
			transSettleMapper.carriageSettleGlGt(gl_param);
//			System.out.println("管理费:"+gl_param.get("out_result"));
			
			//特殊服务费结算过程
			tf_param.put("out_result", "");
			tf_param.put("out_result_reason", "");
			transSettleMapper.carriageSettleTfGt(tf_param);
//			System.out.println("特殊服务费:"+tf_param.get("out_result"));
			
		}
		return param;
	}

	

	public Long selectCount(Map<String, Object> param) throws Exception {
		return null;
	}
}
