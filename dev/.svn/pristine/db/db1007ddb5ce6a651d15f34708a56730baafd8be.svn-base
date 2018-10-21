package com.bt.workOrder.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.bt.common.CommonUtils;
import com.bt.lmis.controller.form.GroupParam;
import com.bt.lmis.dao.EmployeeMapper;
import com.bt.lmis.model.Group;
import com.bt.lmis.model.StoreEmployee;
import com.bt.lmis.model.WoGroupMember;
import com.bt.lmis.page.QueryParameter;
import com.bt.lmis.page.QueryResult;
import com.bt.workOrder.dao.ShopGroupMapper;
import com.bt.workOrder.service.ShopGroupService;
@Service
public class ShopGroupServiceImpl<T> implements ShopGroupService<T> {

	@Autowired
	private ShopGroupMapper<T> groupMapper;
	
	@Autowired
    private EmployeeMapper<T> mapper;
	
	@Override
	public QueryResult<Group> querysGroup(GroupParam groupPar) {
		QueryResult<Group> queryResult = new QueryResult<Group>();
		queryResult.setResultlist(groupMapper.querysGroup(groupPar));
		queryResult.setTotalrecord(groupMapper.countsGroup(groupPar));
		return queryResult;
	}

	@Override
	public ArrayList<?> querySeniorQueryGroupSup(GroupParam groupPar) {
		// TODO Auto-generated method stub
		return groupMapper.querySeniorQueryGroupSup(groupPar);
	}

	@Override
	public ArrayList<?> queryWkGroupSup() {
		// TODO Auto-generated method stub
		return groupMapper.queryWkGroupSup();
	}

	@Override
	public ArrayList<?> findWorkOrderType() {
		// TODO Auto-generated method stub
		return groupMapper.findWorkOrderType();
	}

	@Override
	public ArrayList<?> queryLogisticsCode() {
		// TODO Auto-generated method stub
		return groupMapper.queryLogisticsCode();
	}

	@Override
	public ArrayList<?> findStore() {
		// TODO Auto-generated method stub
		return groupMapper.findStore();
	}

	@Override
	public List<?> workOrderAndLevel(String code) {
		// TODO Auto-generated method stub
		return groupMapper.workOrderAndLevel(code);
	}

	@Override
	public ArrayList<?> queryWkGroupSupAndUpdate(String id) {
		// TODO Auto-generated method stub
		return groupMapper.queryWkGroupSupAndUpdate(id);
	}

	@Override
	public Group querysGroupById(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		return groupMapper.querysGroupById(paramMap);
	}

	@Override
	public QueryResult<T> findAllStoreData(QueryParameter qr) {
		// TODO Auto-generated method stub
		QueryResult<T> queryResult = new QueryResult<T>();
		queryResult.setResultlist(groupMapper.getSrorePage(qr));
		queryResult.setTotalrecord(groupMapper.selectStoreCount(qr));
		return queryResult;
	}

	@Override
	public void updateGroupModel(Map<String, Object> pram) {
		// TODO Auto-generated method stub
		groupMapper.updateGroupModel(pram);
	}

	@Override
	public ArrayList<Map<String, String>> get_team_emp(Map<String, String> param) {
		// TODO Auto-generated method stub
		return groupMapper.get_team_emp(param);
	}

	@Override
	public QueryResult<T> findAllEmpData(QueryParameter qr) {
		// TODO Auto-generated method stub
		QueryResult<T> queryResult = new QueryResult<T>();
		queryResult.setResultlist(groupMapper.getSrorePage(qr));
		queryResult.setTotalrecord(groupMapper.selectStoreCount(qr));
		return queryResult;
	}

	@Override
	public void insertEmp(List<StoreEmployee> param) {
		// TODO Auto-generated method stub
		groupMapper.insertEmp(param);
	}

	@Override
	public Map<String, String> checkImport(Map<String, String> param) {
		// TODO Auto-generated method stub
		groupMapper.checkImport(param);
		return null;
	}

	@Override
	public List<Map<String, Object>> query_export(String bat_id) {
		// TODO Auto-generated method stub
		return groupMapper.query_export(bat_id);
	}

	@Override
	public void addGroupModel(Map<String, Object> param) {
		// TODO Auto-generated method stub
		groupMapper.addGroupModel(param);
		groupMapper.addGroupMember(param);
		
	}

	@Override
	public int checkCountWorkCode(Map<String, Object> pram) {
		// TODO Auto-generated method stub
		return groupMapper.checkCountWorkCode(pram);
	}

	@Override
	public int checkCountWork(Map<String, Object> pram) {
		// TODO Auto-generated method stub
		return groupMapper.checkCountWork(pram);
	}

	@Override
	public void addStore(Map<String, String> param) {
		// TODO Auto-generated method stub
		groupMapper.addStore(param);
	}

	@Override
	public Integer checkCountStore(Map<String, String> param) {
		// TODO Auto-generated method stub
		return groupMapper.checkCountStore(param);
	}

	@Override
	public void delGroup(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delWorkPower(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delStorePower(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int switchShopGroup(String status, String id) {
		return groupMapper.switchShopGroup(status,id);
	}

	@Override
	public void opEmp(Map<String, String> param) {
		// TODO Auto-generated method stub
		if("1".equals(param.get("status"))){
			Map<String,Object> result=groupMapper.checkCountEmp(param);
			if((Long)result.get("ct")>0){
				param.put("out_result", "0");
				param.put("out_result_reason" ,"用户"+result.get("name")+"存在重复账号，不可启用");
			}else{
		groupMapper.opEmp(param);
			}
		}else{
		groupMapper.opEmp(param);
		}
	}

	@Override
	public Map<String, Object> getGroupData(String id) {
		// TODO Auto-generated method stub
		return groupMapper.getGroupData(id);
	}

	@Override
	public ArrayList<?> findWkType() {
		// TODO Auto-generated method stub
		return groupMapper.findWkType();
	}

	@Override
	public Integer checkCountWorkType(Map<String, String> param) {
		// TODO Auto-generated method stub
		return groupMapper.checkCountWorkType(param);
	}

	@Override
	public void addWorkType(Map<String, String> param) {
		groupMapper.addWorkType(param);
	}

	@Override
	public ArrayList<?> getWorkPowerBywkType(Map<String, String> param) {
		// TODO Auto-generated method stub
		return groupMapper.getWorkPowerBywkType(param);
	}

	@Override
	public Integer delBatchWorkPower(Integer[] ids) {
		// TODO Auto-generated method stub
		return groupMapper.delBatchWorkPower(ids);
	}
	
	@Override
	public Integer delBatchStorePower(Integer[] ids) {
		return groupMapper.delBatchStorePower(ids);
	}

	@Override
	public Map<String, Object> getGroupTypeById(String id) {
		// TODO Auto-generated method stub
		return groupMapper.getGroupTypeById(id);
	}

	@Override
	public QueryResult<Group> queryGroup(GroupParam groupPar) {
		// TODO Auto-generated method stub
		QueryResult<Group> queryResult = new QueryResult<Group>();
		queryResult.setResultlist(groupMapper.queryGroup(groupPar));
		queryResult.setTotalrecord(groupMapper.countGroup(groupPar));
		return queryResult;
	}

	@Override
	public ArrayList<Map<String, String>> getSOteam(String groupType) {
		return groupMapper.getSOteam(groupType);
	}

	@Override
	public Map<String,Object> checkImport(List<StoreEmployee> oo_list, String groupId) {
	    
	    Map<String,Object> resultMap =new HashMap<>();
	    List<StoreEmployee> empImpotList=new ArrayList<>();
	    Map<String, Object> groupTypeMap = groupMapper.getGroupTypeById(groupId);
	    String teamType = groupTypeMap.get("group_type").toString();
	    int bindSucess=0;
	    int addSucess =0;
	    int bindNg=0;
	    int addNg=0;
	    
	    for (StoreEmployee se : oo_list) {
	    	
	    	// 手动提交事务
	    	WebApplicationContext contextLoader=ContextLoader.getCurrentWebApplicationContext();
	  	    DataSourceTransactionManager transactionManager=(DataSourceTransactionManager)contextLoader.getBean("transactionManager");
	  	    DefaultTransactionDefinition def=new DefaultTransactionDefinition();
	  	    // 事物隔离级别，开启新事务，这样会比较安全些。
	  	    def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
	  	    // 获得事务状态
	  	    TransactionStatus status=transactionManager.getTransaction(def);
	    	
	    	StringBuilder proRemarkSb=new StringBuilder();
	    	WoGroupMember wgm=new WoGroupMember();
	    	try {
	    		//判断是否有重复登录名，有则直接绑定用户，无则先建用户再绑定
	    		
	    		
	    		String username = se.getUsername();
	    		Integer userId = groupMapper.getIdByUsername(username);
	    		if(CommonUtils.checkExistOrNot(userId)) {
	    			if(!CommonUtils.checkExistOrNot(se.getIsAutoAllct())) {
						proRemarkSb.append("是否自动分配为空,");
					}else {
						if(!se.getIsAutoAllct().equals("0")&&!se.getIsAutoAllct().equals("1")) {
							proRemarkSb.append("是否自动分配数据异常,");
							
						}
					}
	    			wgm.setGroupId(Integer.parseInt(se.getTeam_id()));
	    			wgm.setEmployeeId(userId);
	    			
	    			String groupType=groupMapper.getGroupTypeByUserIduserId(userId);
	    			if(CommonUtils.checkExistOrNot(groupType)) {
	    				if(groupType.equals("1")&&teamType.equals("0")){
	    					
	    					proRemarkSb.append("当前用户已在其他部门,");
	    				}else if(groupType.equals("0")){
    						proRemarkSb.append("当前用户已属于其他店铺客服,");
	    				}
	    			}
	    			Integer count=groupMapper.getCountByUserId(wgm);
	    			if(count!=0) {
	    				proRemarkSb.append("此组内成员已存在,");
	    			}
	    			
	    			String proRemark = proRemarkSb.toString();
	    			if(CommonUtils.checkExistOrNot(proRemark)){
	    				if(proRemark.endsWith(",")) {
	    					proRemark=proRemark.substring(0, proRemark.length()-1);
			    		}
	    				se.setProRemark(proRemark);
		    			int i=groupMapper.addEmpimportError(se);
		    			bindNg++;
		    			transactionManager.commit(status);
		    			continue;
	    			}else {
	    				
	    				wgm.setCreateby(se.getCreateby());
	    				wgm.setUpdateby(se.getCreateby());
	    				wgm.setIsAutoAllct(se.getIsAutoAllct());
	    				groupMapper.addWoGroupMember(wgm);
	    				bindSucess++;
	    			}
	    		}else {
	    			if(!CommonUtils.checkExistOrNot(se.getRow_number())) {
						proRemarkSb.append("行号为空,");
					}
					if(!CommonUtils.checkExistOrNot(se.getEmployee_number())) {
						proRemarkSb.append("工号为空,");
					}else{
						int num = groupMapper.getCountByEmpNum(se.getEmployee_number());
						if(num>0){
							proRemarkSb.append("工号已存在,");
						}
					}
					if(!CommonUtils.checkExistOrNot(se.getEmail())) {
						proRemarkSb.append("邮箱为空,");
					}
					
					int num = mapper.getCountByEmail(se.getEmail());
					if(num>0){
						proRemarkSb.append("邮箱已存在，请重新输入,");
					}
					
					if(!CommonUtils.checkExistOrNot(se.getUsername())) {
						proRemarkSb.append("登录名为空,");
					}
					if(!CommonUtils.checkExistOrNot(se.getName())) {
						proRemarkSb.append("昵称为空,");
					}
					if(!CommonUtils.checkExistOrNot(se.getTeam_id())) {
						proRemarkSb.append("组别为空,");
					}
					if(!CommonUtils.checkExistOrNot(se.getIsAutoAllct())) {
						proRemarkSb.append("是否自动分配为空,");
					}else {
						if(!se.getIsAutoAllct().equals("0")&&!se.getIsAutoAllct().equals("1")) {
							proRemarkSb.append("是否自动分配数据异常,");
						}
					}
					String proRemark = proRemarkSb.toString();
		    		if(proRemark.endsWith(",")) {
		    			proRemark=proRemark.substring(0, proRemark.length()-1);
		    		}
		    		if(proRemark.length()==0) {
		    			
		    			groupMapper.addEmployee(se);
		    			groupMapper.addEmpRole(se.getEmpid());
		    			
		    			wgm.setGroupId(Integer.parseInt(se.getTeam_id()));
		    			wgm.setEmployeeId(se.getEmpid());
		    			wgm.setCreateby(se.getCreateby());
		    			wgm.setUpdateby(se.getCreateby());
		    			wgm.setIsAutoAllct(se.getIsAutoAllct());
		    			groupMapper.addWoGroupMember(wgm);
		    			bindSucess++;
		    			addSucess++;
		    		}else {
		    			se.setProRemark(proRemark);
		    			int i=groupMapper.addEmpimportError(se);
		    			addNg++;
		    		}
	    		}
	    		
	    		
	    	transactionManager.commit(status);
			} catch (Exception e) {
				transactionManager.rollback(status);
				throw e;
				
			}
		}
	    int out_result=0;
	    if(addNg==0&&bindNg==0) {
	    	out_result=99;
	    }
    	resultMap.put("out_result", 0);
    	resultMap.put("empImpotList", empImpotList);
    	resultMap.put("message","添加用户成功:"+addSucess+"条，绑定用户成功"+bindSucess+"条，"+"添加用户失败:"+addNg+"条，绑定用户失败"+bindNg+"条");
    	resultMap.put("errorFlag", "true");
    	resultMap.put("out_result", out_result);
    	
		return resultMap;
	    
	}

	@Override
	public void opAuto(Map<String, String> pram) {
				groupMapper.opAuto(pram);
		
	}

	@Override
	public List<String> queryTeamIdById(Integer id) {
		// TODO Auto-generated method stub
		return groupMapper.queryTeamIdById(id);
	}

	@Override
	public String queryCurrentGroupByWoId(String woId) {
		// TODO Auto-generated method stub
		return groupMapper.queryCurrentGroupByWoId(woId);
	}

	@Override
	public void delEmp(Map<String, String> pram) {
		// TODO Auto-generated method stub
		groupMapper.delEmp(pram);
	}

	@Override
	public String getCodeByName(String storeName) {
		return groupMapper.getCodeByName(storeName);
	}

    @Override
    public void delWkType(String string){
       groupMapper.delWkType(string);
    }


}
