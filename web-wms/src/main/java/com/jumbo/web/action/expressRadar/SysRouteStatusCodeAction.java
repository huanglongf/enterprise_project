package com.jumbo.web.action.expressRadar;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.util.JsonUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.wms.manager.expressRadar.SysRouteStatusCodeManager;
import com.jumbo.wms.model.expressRadar.SysRouteStatusCode;

/**
 * 系统状态编码
 * 
 * @author hui.li
 * 
 */
public class SysRouteStatusCodeAction extends BaseJQGridProfileAction {

    

	/**
	 * 
	 */
	private static final long serialVersionUID = -4410764159620952588L;
	@Autowired
	private SysRouteStatusCodeManager sysRouteStatusCodeManager;
	
    

    /**
     * 获取系统路由状态编码
     * 
     * @return
     */
    public String findSysRouteStatusCodeList(){
    	Map<String, Object> params=new HashMap<String, Object>();
    	List<SysRouteStatusCode> p=sysRouteStatusCodeManager.findRouteStatusCodeByParam(params);
    	request.put(JSON, JsonUtil.collection2json(p));
         
        return JSON;
    }
    
   
    
   
}
