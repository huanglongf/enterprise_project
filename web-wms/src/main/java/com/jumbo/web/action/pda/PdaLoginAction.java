/**
 * Copyright (c) 2010 Jumbomart All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of Jumbomart. You shall not
 * disclose such Confidential Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with Jumbo.
 * 
 * JUMBOMART MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF THE SOFTWARE, EITHER
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE, OR NON-INFRINGEMENT. JUMBOMART SHALL NOT BE LIABLE FOR ANY
 * DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES.
 * 
 */
package com.jumbo.web.action.pda;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import loxia.support.json.JSONObject;
import net.spy.memcached.MemcachedClient;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.ContextLoader;

import com.jumbo.web.action.BaseJQGridAction;
import com.jumbo.web.security.UserDetails;
import com.jumbo.wms.manager.authorization.AuthorizationManager;
import com.jumbo.wms.manager.warehouse.OperationUnitManager;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.command.authorization.UserDetailsCmd;

/**
 * PDA登录
 * 
 * @author sjk
 *
 */
public class PdaLoginAction extends BaseJQGridAction {

    private static final long serialVersionUID = -1749970559606837506L;

    protected static final Logger logger = LoggerFactory.getLogger(PdaLoginAction.class);

    @Autowired
    private AuthorizationManager authorizationManager;
    @Autowired
    private OperationUnitManager operationUnitManager;
    private String loginname;
    private String password;
    private String oucode;

    public String login() {
        return SUCCESS;
    }

    public String menu() {
        return SUCCESS;
    }

    public String userLogin() throws Exception {
        String r = authorizationManager.toCasPwdLogin(loginname, password, "PDA");
        JSONObject jsonObject = new JSONObject(r);
        if ((Boolean) jsonObject.get("success")) {
            // if (true) {
            // 认证成功
            try {
                boolean rs = pdaLogin(loginname, oucode);
                if (rs) {
                    return SUCCESS;
                } else {
                    return ERROR;
                }
            } catch (Exception e) {
                logger.error("login failed  : {}", loginname);
                logger.error("", e);
                this.addFieldError("msg", "认证失败!");
                // 认证失败
                return ERROR;
            }
        } else {
            logger.error("login failed  : {}", loginname);
            this.addFieldError("msg", "认证失败!");
            // 认证失败
            return ERROR;
        }
    }

    /**
     * 验证PDA单点登入
     * 
     * @return
     * @throws Exception
     */
    public String checkSingleogin() throws Exception {
        JSONObject result = new JSONObject();
        HttpServletRequest r = ServletActionContext.getRequest();
        HttpSession session = r.getSession();
        // System.out.println(session.getId());
        boolean b = checkSingleSign(session.getId(), loginname);
        if (!b) {
            // Memcached没有登入 or 相同设备
            result.put("check", "0");
        } else {
            // Memcached不同设备已登入
            result.put("check", "1");
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 检查用户登录是否一致,如果没有登录过则保存用户session id，如果登录过则校验session id是否一致
     * 
     * @param currentSessionId
     * @param loginName
     * @return
     */
    private boolean checkSingleSign(String currentSessionId, String loginName) {
        MemcachedClient client = ContextLoader.getCurrentWebApplicationContext().getBean(MemcachedClient.class);
        if (client != null && loginName != null && currentSessionId != null) {
            Object rs = client.get(loginName);
            if (rs instanceof String) {
                String sid = (String) rs;
                if (!currentSessionId.equals(sid)) {
                    // 不同客户端
                    return true;
                }
            }
        }
        return false;
    }


    private boolean pdaLogin(String username, String oucode) {
        UserDetailsCmd cmd = authorizationManager.loadUserByUsername(username);
        OperationUnit ou = null;
        if (cmd != null) {
            ou = operationUnitManager.getByCode(oucode);
            if (ou == null) {
                this.addFieldError("msg", "仓库未找到!");
                return false;
            }
        } else {
            this.addFieldError("msg", "用户未找到!");
            return false;
        }
        cmd.getUser().setOu(ou);
        UserDetails userDetails = new com.jumbo.web.security.UserDetails(cmd.getUser());
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = request.getSession();
        session.setAttribute("userDetials", userDetails);
        // System.out.println(request.getSession().getId());
        // 放入Memcached
        MemcachedClient client = ContextLoader.getCurrentWebApplicationContext().getBean(MemcachedClient.class);
        client.set(username, 3600 * 24, request.getSession().getId());
        return true;
    }

    public String pdaExit() {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = request.getSession();
        UserDetails userDetails = (UserDetails) session.getAttribute("userDetials");
        MemcachedClient client = ContextLoader.getCurrentWebApplicationContext().getBean(MemcachedClient.class);
        client.delete(userDetails.getUser().getUserName());
        session.removeAttribute("userDetials");
        return SUCCESS;
    }

    public String getLoginname() {
        return loginname;
    }

    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOucode() {
        return oucode;
    }

    public void setOucode(String oucode) {
        this.oucode = oucode;
    }

}
