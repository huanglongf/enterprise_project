package com.bt.workOrder.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface DistributionService{
	Map<String,String> automatic_distribution(HashMap<String,Object>param);
	List<HashMap<String, Object>> getOrderInfo(Map<String, String>hashMap);
	HashMap<String,Object> getSetParam(HashMap<String,String>param);
	
}
