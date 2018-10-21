package com.bt.radar.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.lmis.code.ServiceSupport;
import com.bt.radar.controller.form.WarninglevelListQueryParam;
import com.bt.radar.dao.WarninglevelListMapper;
import com.bt.radar.model.WarninglevelList;
import com.bt.radar.service.WarninglevelListService;
@Service
public class WarninglevelListServiceImpl<T> extends ServiceSupport<T> implements WarninglevelListService<T> {

	@Autowired
    private WarninglevelListMapper<T> mapper;

	public WarninglevelListMapper<T> getMapper() {
		return mapper;
	}

	@Override
	public Long selectCount(Map<String, Object> param) throws Exception {
		return mapper.selectCount(param);
	}

	@Override
	public List<T> findByWType(WarninglevelListQueryParam param) {
		// TODO Auto-generated method stub
		return  mapper.findByWType(param);
	}

	@Override
	public int updateWll(WarninglevelListQueryParam param) {
		// TODO Auto-generated method stub
		
		Map map =new HashMap();
        map.put("id", param.getId());
        map.put("levelup_level", param.getLevelup_level());
        map.put("levelup_time", param.getLevelup_time());
	List<Map<String,Object>> list=mapper.ExistsLevel(map);
      if(list!=null&&list.size()!=0)return 0;
      List<Map<String,Object>> list0=mapper.checkLevel(map);
      Map<String,Object> paramMap=list0.get(0);
      if(paramMap.get("time")==paramMap.get("level")||paramMap.get("level").toString().equals(paramMap.get("time").toString())){
    	  mapper.updateWll(param);
      }else{
    	  return 0;
      }
//	   if(list.get(0).get("big_value")==null&&list.get(0).get("little_value")==null){
//		   mapper.updateWll(param); //没有最大值也没有最小值  随便改   
//	   }else if(list.get(0).get("big_value")==null){
//		   //没有最大值
//		   if(param.getLevelup_time()<=(Integer)list.get(0).get("little_value"))return 0;
//		   mapper.updateWll(param);
//	   }else if(list.get(0).get("little_value")==null){
//		   if(param.getLevelup_time()<=(Integer)list.get(0).get("little_value"))return 0;
//		  if(param.getLevelup_time()>=(Integer)list.get(0).get("big_value")) return 0; 
//		  mapper.updateWll(param);
//	   }else{
//		   if(param.getLevelup_time()<=(Integer)list.get(0).get("little_value")||param.getLevelup_time()>=(Integer)list.get(0).get("big_value"))return 0;
//		   mapper.updateWll(param);  
//	   }
 	  return 1; 
	}

	@Override
	public WarninglevelList findLittlestLevel(WarninglevelListQueryParam param) {
		// TODO Auto-generated method stub
		
		return mapper.findLittlestLevel(param);
	}

	@Override
	public void deleteByCon(Map dparam) {
		// TODO Auto-generated method stub
		mapper.deleteByCon(dparam);
	}	

}
