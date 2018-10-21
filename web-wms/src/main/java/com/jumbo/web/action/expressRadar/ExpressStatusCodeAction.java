package com.jumbo.web.action.expressRadar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import loxia.dao.Pagination;
import loxia.support.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.wms.manager.expressRadar.ExpressStatusCodeManager;
import com.jumbo.wms.model.command.expressRadar.RadarRouteStatusRefCommand;

/**
 * 快递状态代码查询
 * 
 * @author hui.li
 * 
 */
public class ExpressStatusCodeAction extends BaseJQGridProfileAction {

    /**
	 * 
	 */
	private static final long serialVersionUID = -515993503504989310L;

	@Autowired
	private ExpressStatusCodeManager expressStatusCodeManager;
	
	
		
	/**
	 * 物流商编码
	 */
	private String logisticsCode;
	
	/**
	 * 系统路由状态编码ID
	 */
	private String sysRscId;
	
	/**
	 * 代码
	 */
	private String code;
	
	/**
	 * 描述
	 */
	private String describe;
	
	
	private String idStr;
	
	private String remark;
    
	private RadarRouteStatusRefCommand rrsrCommand;

    /**
     * 初始跳转页面，只进行页面跳转，不进行其他 操作。
     * 
     * @return
     */
    public String initStatusCodePage() {
    	
        return SUCCESS;
    }

    /**
     * 快递状态代码查询
     * 
     * @return
     */
    public String findStatusCodeList(){
    	Map<String, Object> params=new HashMap<String, Object>();
    	if(logisticsCode!=null&&!"".equals(logisticsCode)){
    		params.put("logisticsCode", logisticsCode);
    	}
    	if(sysRscId!=null&&!"".equals(sysRscId)){
    		params.put("sysRscId", sysRscId);
    	}
    	if(describe!=null&&!"".equals(describe)){
    		params.put("describe",describe );
    	}
    	setTableConfig();
    	Pagination<RadarRouteStatusRefCommand> p=expressStatusCodeManager.findExpressStatusCodeByParams(tableConfig.getStart(), tableConfig.getPageSize(), params);
    	JSONObject j=toJson(p);
         
         request.put(JSON, j);
        return JSON;
    }
    
    public String updateStatusCode(){
    	if(idStr!=null&&!"".equals(idStr)&&remark!=null&&!"".equals(remark)){
    		JSONObject result = new JSONObject();
    		try{
    			
    			Map<String, Object> params=new HashMap<String, Object>();
    			List<Long> idList=new ArrayList<Long>();
    			
    			String[] ids=idStr.split(",");
    			for(int i=0;i<ids.length;i++){
    				idList.add(Long.parseLong(ids[i]));
    			}
    			
    			params.put("remark", remark);
    			expressStatusCodeManager.updateRRSR(params, idList);
    			result.put("flag", "success");
    			 request.put(JSON, result);
    		}catch(Exception e){
    		}
    	}
    	
    	return JSON;
    }
    
    public String deleteStatusCode(){
    	if(idStr!=null&&!"".equals(idStr)){
    		try{
    			JSONObject result = new JSONObject();
    			
    			Map<String, Object> params=new HashMap<String, Object>();
    			List<Long> idList=new ArrayList<Long>();
    			
    			String[] ids=idStr.split(",");
    			for(int i=0;i<ids.length;i++){
    				idList.add(Long.parseLong(ids[i]));
    			}
    			
    			params.put("status", 10);
    			expressStatusCodeManager.updateRRSR(params, idList);
    			
    			result.put("flag", "success");
    			request.put(JSON, result);
    		}catch(Exception e){
    		}
    	}
    	
    	return JSON;
    }
    
    public String saveStatusCode(){
    	if(rrsrCommand!=null){
    		try{
    			JSONObject result = new JSONObject();
    			rrsrCommand.setUser(this.userDetails.getUser());
    			String flag=expressStatusCodeManager.saveRRSR(rrsrCommand);
    			result.put("flag", flag);
    			request.put(JSON, result);
    		}catch(Exception e){
    			
    		}
    		
    	}
    	return JSON;
    }
	

	public ExpressStatusCodeManager getExpressStatusCodeManager() {
		return expressStatusCodeManager;
	}

	public void setExpressStatusCodeManager(
			ExpressStatusCodeManager expressStatusCodeManager) {
		this.expressStatusCodeManager = expressStatusCodeManager;
	}

	

	public String getLogisticsCode() {
		return logisticsCode;
	}

	public void setLogisticsCode(String logisticsCode) {
		this.logisticsCode = logisticsCode;
	}

	public String getSysRscId() {
		return sysRscId;
	}

	public void setSysRscId(String sysRscId) {
		this.sysRscId = sysRscId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	

	public RadarRouteStatusRefCommand getRrsrCommand() {
		return rrsrCommand;
	}

	public void setRrsrCommand(RadarRouteStatusRefCommand rrsrCommand) {
		this.rrsrCommand = rrsrCommand;
	}

	public String getIdStr() {
		return idStr;
	}

	public void setIdStr(String idStr) {
		this.idStr = idStr;
	}

    
   
}
