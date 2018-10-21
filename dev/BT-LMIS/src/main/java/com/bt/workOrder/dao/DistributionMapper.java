package com.bt.workOrder.dao;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bt.lmis.code.BaseMapper;
/**
 * 
* @ClassName: DistributionMapper 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author likun
* @date 2017年1月4日 下午2:06:29 
* 
* @param <T>
 */
public interface DistributionMapper<T> extends BaseMapper<T>{
	public List<HashMap<String,Object>> getOrderInfo(Map<String,String>param);
	public List<HashMap<String,String>> getFirstResult(HashMap<String,Object>param);
	public List<HashMap<String,String>> getSecondResult(HashMap<String,Object>param);
	public HashMap<String,Object> getMaxUsable(Map<String,String>param);
	public List<HashMap<String,Object>> getMaxUsable_beforehand(Map<String,String>param);
	public HashMap<String,String> wk_distribution_pro(Map<String,Object>param);
	public void insertLogInfo(Map<String,Object>param);
	public Integer getMaxId(Map<String,Object>param);
	public void updateInfo(Map<String,Object>param);
	public HashMap<String,Object> getSetParam(HashMap<String, String> param);
}
