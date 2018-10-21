package com.jumbo.web.action.checkPing;



import java.net.InetAddress;

import loxia.support.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.SystemVersion;
import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.wms.manager.checklist.CheckListManager;

public class CheckPingAction extends BaseJQGridProfileAction {

    @Autowired
    private CheckListManager checkListManager;
    /**
     * 
     */
    private static final long serialVersionUID = -3688144284802489816L;

    @Override
    public String execute() {
        return SUCCESS;
    }



    public String checkWebPing() throws Exception {
        JSONObject result = new JSONObject();
        InetAddress address = InetAddress.getLocalHost();
        result.put("result", SUCCESS);
        result.put("memo", "web主机名:" + address.getHostName() + " web主机别名: " + address.getCanonicalHostName() + " web ip地址: " + address.getHostAddress());
        result.put("version", SystemVersion.SystemVersion);
        // System.out.println(address.getHostName());// 主机名
        // System.out.println(address.getCanonicalHostName());// 主机别名
        // System.out.println(address.getHostAddress());// 获取IP地址
        request.put(JSON, result);
        return JSON;
    }

    public String checkServicePing() throws Exception {
        JSONObject result = new JSONObject();
        InetAddress address = InetAddress.getLocalHost();
        String memoString = checkListManager.checkServicePing();
        result.put("result", SUCCESS);
        result.put("memo", "web主机别名: " + address.getCanonicalHostName() + " " + memoString);
        result.put("version", SystemVersion.SystemVersion);
        request.put(JSON, result);
        return JSON;
    }

    public String webCheckVersion() throws Exception {
        JSONObject result = new JSONObject();
        InetAddress address = InetAddress.getLocalHost();
        result.put("result", SUCCESS);
        result.put("memo", "web主机名:" + address.getHostName() + " web主机别名: " + address.getCanonicalHostName() + " web ip地址: " + address.getHostAddress());
        result.put("version", SystemVersion.SystemVersion);
        // System.out.println(address.getHostName());// 主机名
        // System.out.println(address.getCanonicalHostName());// 主机别名
        // System.out.println(address.getHostAddress());// 获取IP地址
        request.put(JSON, result);
        return JSON;
    }

}
