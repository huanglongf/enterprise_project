package com.bt.workOrder.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.bt.lmis.code.ServiceSupport;
import com.bt.lmis.page.QueryParameter;
import com.bt.lmis.page.QueryResult;
import com.bt.utils.SessionUtils;
import com.bt.radar.dao.WarninglevelListMapper;
import com.bt.radar.model.WarninglevelList;
import com.bt.workOrder.controller.form.GenerationRuleQueryParam;
import com.bt.workOrder.dao.GenerationRuleMapper;
import com.bt.workOrder.dao.WkLevelMapper;
import com.bt.workOrder.dao.WkTypeMapper;
import com.bt.workOrder.model.GenerationRule;
import com.bt.workOrder.service.GenerationRuleService;
@Service
public class GenerationRuleServiceImpl<T> extends ServiceSupport<T> implements GenerationRuleService<T> {

	@Autowired
    private GenerationRuleMapper<T> mapper;
	@Autowired
	private WarninglevelListMapper wlMapper;
	@Autowired
    private WkTypeMapper<T> wkTypeMapper;
	@Autowired
    private WkLevelMapper<T> wkLevelMapper;
	
	public GenerationRuleMapper<T> getMapper() {
		return mapper;
	}

	@Override
	public Long selectCount(Map<String, Object> param) throws Exception {
		return mapper.selectCount(param);
	}
		
	
	@Override
	public QueryResult<T> findAllData(QueryParameter qr) {
		// TODO Auto-generated method stub
		QueryResult<T> queryResult = new QueryResult<T>();
		queryResult.setResultlist(mapper.findAllData(qr));
		queryResult.setTotalrecord(mapper.selectCount(qr));
		//queryResult.setTotalrecord(mapper.selectWarnTB(qr));
		return queryResult;
	}
	@Override
	public QueryResult<Map<String, Object>> findAllData2(QueryParameter qr) {
		// TODO Auto-generated method stub
		QueryResult<Map<String, Object>> queryResult = new QueryResult<Map<String, Object>>();
		queryResult.setResultlist(mapper.findAllData2(qr));
		queryResult.setTotalrecord(mapper.selectCount2(qr));
		return queryResult;
	}

	@Override
	public JSONObject addRule(GenerationRuleQueryParam queryParam,HttpServletRequest request) throws Exception {
		// TODO Auto-generated method stub
		JSONObject obj=new JSONObject();
		synchronized(this){
			Map wlp=new HashMap();
			wlp.put("wk_type", queryParam.getWk_type());
			wlp.put("level",queryParam.getWk_level());
			List<Map<String,Object>> wklist=(List<Map<String, Object>>) wkLevelMapper.findAllData(wlp);
			if(!checkType(queryParam)){
			obj.put("code", 0);
			obj.put("msg", "此规则已经存在！");
			return obj;
			}
			if(!checkType_adv(queryParam)){
				obj.put("code", 0);
				obj.put("msg", "一种预警类型只能生成一种工单类型！");	
				return obj;
			}
			if(!checkLevel(queryParam,Integer.parseInt(queryParam.getEw_level()),Integer.parseInt(wklist.get(0).get("sort").toString()))){
				obj.put("code", 0);
				obj.put("msg", "添加的级别不符合要求！！");	
				return obj;
			}
		
			
				GenerationRule r=new GenerationRule();
				r.setCreate_time(new Date());
				r.setUpdate_time(new Date());
				r.setCreate_user(SessionUtils.getEMP(request).getUsername());
				r.setUpdate_user(SessionUtils.getEMP(request).getUsername());
				r.setId(UUID.randomUUID().toString());
				r.setEw_flag(queryParam.getEw_flag());
				r.setEw_level(queryParam.getEw_level());
				r.setEw_type_code(queryParam.getEw_type_code());
				/*r.setWk_type(queryParam.getWk_type());
				r.setWk_level(queryParam.getWk_level());*/
				//查找 warning_level的id
				/*WarninglevelList w=new WarninglevelList();
				w.setWarningtype_code(queryParam.getEw_type());
				w.setLevelup_level(queryParam.getEw_level());
				w.setDl_flag(1);
			List<WarninglevelList> ewList=	wlMapper.selectRecords(w);
			if(ewList==null||ewList.size()==0) return false;
			   r.setEw_type_level_id(ewList.get(0).getId());*/
			 //查找 wk_type_level_id的id
			   Map<String,String> param=new HashMap<String,String>();
			   param.put("wk_type", queryParam.getWk_type());
			   param.put("wk_level", queryParam.getWk_level());
			  List<Map<String,Object>> wkList= wkTypeMapper.findWk_type_level(param);
			  if(wkList==null||wkList.size()==0){
				  obj.put("code", 0);
				  obj.put("msg", "此工单类型或级别不存在！");
				  return obj;  
			  }
			  r.setWk_type_level_id(wkList.get(0).get("id").toString());
				mapper.insert((T) r);
				obj.put("code", 1);
				obj.put("msg", "添加成功！！");
			
			return obj;
		}
		}
	
  public  boolean checkLevel(GenerationRuleQueryParam queryParam,Integer ew_level,Integer wk_level){
	List< Map<String,Object>> list= mapper.CanAddData_se(queryParam);
	 for(Map<String,Object> obj:list){
		 Integer  workorderlevel=Integer.parseInt(obj.get("sort").toString());
		 Integer  radarlevel=Integer.parseInt(obj.get("ew_level").toString());
		  if(ew_level>radarlevel){
			  if(wk_level<workorderlevel)return false;
		  }else if(ew_level<radarlevel){
			  if(wk_level>workorderlevel)return false; 
		  } 
	 }
	  return true;
  }
  
  public boolean  checkType(GenerationRuleQueryParam queryParam){

	  String level_code=queryParam.getEw_level();
	  String wk_type=queryParam.getWk_type();
	  String wk_level=queryParam.getWk_level();
	  String type_code=queryParam.getEw_type_code();
	  GenerationRuleQueryParam q1=new GenerationRuleQueryParam();
	  q1.setEw_type_code(type_code);
	  q1.setEw_level(level_code);
	  List list= mapper.findAllData(q1);
	  if(list==null||list.size()==0)return true;
	  /*for(Object rule:list){
		  Map<String,String> r=(Map<String,String>)rule;
		// if(wk_type.equals(r.get("wk_type_code").toString())&&wk_level.equals(r.get("wk_level_code").toString())) return false;
		 if(r.get("wk_type_code").toString().equals(wk_type)&&r.get("ew_level").toString().equals(level_code)&&(wk_level).equals(r.get("wk_level_code").toString())) return false;
		 if(!wk_type.equals(r.get("wk_type_code").toString()))return false; 
		  
	  }*/
	  return false;
  }
  
  
  public boolean  checkTypeUpdate(GenerationRuleQueryParam queryParam){

	  String level_code=queryParam.getEw_level();
	  String wk_type=queryParam.getWk_type();
	  String wk_level=queryParam.getWk_level();
	  String type_code=queryParam.getEw_type_code();
	  GenerationRuleQueryParam q1=new GenerationRuleQueryParam();
	  q1.setEw_type_code(type_code);
	  q1.setEw_level(level_code);
	  q1.setId(queryParam.getId());
	  List list= mapper.findAllDataUpdate(q1);
	  if(list==null||list.size()==0)return true;
	  /*for(Object rule:list){
		  Map<String,String> r=(Map<String,String>)rule;
		// if(wk_type.equals(r.get("wk_type_code").toString())&&wk_level.equals(r.get("wk_level_code").toString())) return false;
		 if(r.get("wk_type_code").toString().equals(wk_type)&&r.get("ew_level").toString().equals(level_code)&&(wk_level).equals(r.get("wk_level_code").toString())) return false;
		 if(!wk_type.equals(r.get("wk_type_code").toString()))return false; 
		  
	  }*/
	  return false;
  }
  
  
  public boolean  checkType_adv(GenerationRuleQueryParam queryParam){
	  String wk_type=queryParam.getWk_type();
	  GenerationRuleQueryParam q1=new GenerationRuleQueryParam();
	  q1.setEw_type_code(queryParam.getEw_type_code());
	  List list= mapper.findAllData(q1);
	  if(list==null||list.size()==0)return true;
	  for(Object rule:list){
		  Map<String,String> r=(Map<String,String>)rule;
	 if(!wk_type.equals(r.get("wk_type_code").toString()))return false; 
		  
	  }
	  	  return true;
  }
  
  public boolean  checkTypeUpdate_adv(GenerationRuleQueryParam queryParam){
	  String type_code=queryParam.getEw_type_code();
	  String level_code=queryParam.getEw_level();
	  String wk_type=queryParam.getWk_type();
	  String wk_level=queryParam.getWk_level();
	  GenerationRuleQueryParam q1=new GenerationRuleQueryParam();
	  q1.setEw_type_code(queryParam.getEw_type_code());
	  List list= mapper.findAllDataUpdate(q1);
	  if(list==null||list.size()==0)return true;
	  for(Object rule:list){
		  Map<String,String> r=(Map<String,String>)rule;
	 if(!wk_type.equals(r.get("wk_type_code").toString()))return false; 
		  
	  }
	  	  return true;
  }
@Override
public JSONObject deleteRule(GenerationRuleQueryParam queryParam) {
	// TODO Auto-generated method stub
	//是否有有此规则在被使用
//List<Map<String,Object>> list=	mapper.checkRuleIsInUse(queryParam);
	JSONObject obj =new JSONObject();	
	GenerationRuleQueryParam  param=new GenerationRuleQueryParam();
	param.setId(queryParam.getId());
	List<GenerationRule> list=	mapper.findAll(param);
if(list!=null&&list.size()!=0){
	if("1".equals(list.get(0).getEw_flag())){
	   obj.put("code", 0);
	   obj.put("msg", "启用状态无法删除！！！");
	   return obj;
	}
	
}
	mapper.deletRule( queryParam);
	obj.put("code", 1);
	obj.put("msg", "操作成功");
	return obj;
}

@Override
public boolean updateRule(GenerationRuleQueryParam queryParam, HttpServletRequest request) {
	// TODO Auto-generated method stub
	//int flag=mapper.CanAddData(queryParam);
	Map wlp=new HashMap();
	wlp.put("wk_type", queryParam.getWk_type());
	wlp.put("level",queryParam.getWk_level());
	List<Map<String,Object>> wklist=(List<Map<String, Object>>) wkLevelMapper.findAllData(wlp);
	if(checkTypeUpdate(queryParam)
			&&checkTypeUpdate_adv(queryParam)
			&&checkLevel(queryParam,Integer.parseInt(queryParam.getEw_level()),Integer.parseInt(wklist.get(0).get("sort").toString()))
			){
		GenerationRule r=new GenerationRule();
		GenerationRuleQueryParam q=new GenerationRuleQueryParam();
		q.setId(queryParam.getId());
	  List<GenerationRule> grlist=	mapper.findAll(q);
		r=grlist.get(0);
		r.setUpdate_time(new Date());
		r.setUpdate_user(SessionUtils.getEMP(request).getUsername());
		r.setEw_flag(queryParam.getEw_flag());
		r.setEw_level(queryParam.getEw_level());
		r.setEw_type_code(queryParam.getEw_type_code());
		/*r.setWk_type(queryParam.getWk_type());
		r.setWk_level(queryParam.getWk_level());*/
		//查找 warning_level的id
		/*WarninglevelList w=new WarninglevelList();
		w.setWarningtype_code(queryParam.getEw_type());
		w.setLevelup_level(queryParam.getEw_level());
		w.setDl_flag(1);
	List<WarninglevelList> ewList=	wlMapper.selectRecords(w);
	if(ewList==null||ewList.size()==0) return false;
	   r.setEw_type_level_id(ewList.get(0).getId());*/
	 //查找 wk_type_level_id的id
	   Map<String,String> param=new HashMap<String,String>();
	   param.put("wk_type", queryParam.getWk_type());
	   param.put("wk_level", queryParam.getWk_level());
	  List<Map<String,Object>> wkList= wkTypeMapper.findWk_type_level(param);
	  if(wkList==null||wkList.size()==0)return false;
	  r.setWk_type_level_id(wkList.get(0).get("id").toString());
		try {
			mapper.update((T) r);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}finally {}
		return true;
	}else{return false;}

}
	
}
