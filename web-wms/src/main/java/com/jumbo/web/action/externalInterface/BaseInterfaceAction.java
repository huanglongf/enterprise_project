package com.jumbo.web.action.externalInterface;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.wms.manager.baseinfo.BaseinfoManager;
import com.jumbo.wms.model.baseinfo.InterfaceSecurityInfo;
import com.jumbo.util.zip.AppSecretUtil;
import com.jumbo.web.action.BaseAction;

public class BaseInterfaceAction extends BaseAction implements ServletRequestAware {
    /**
	 * 
	 */
    private static final long serialVersionUID = 7153829626529531762L;

    protected static final String RESULT = "result";
    protected static final String MESSAGE = "message";
    protected static final String METHOD_POST = "POST";
    private HttpServletRequest httpRequest;

    @Autowired
    private BaseinfoManager baseinfoManager;
    /**
     * 请求时间
     */
    private String calldate;
    /**
     * 用户名
     */
    private String username;
    /**
     * 验证码
     */
    private String secretkey;
    /**
     * 来源
     */
    private String source;

    public String getCalldate() {
        return calldate;
    }

    public void setCalldate(String calldate) {
        this.calldate = calldate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSecretkey() {
        return secretkey;
    }

    public void setSecretkey(String secretkey) {
        this.secretkey = secretkey;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public void setServletRequest(HttpServletRequest request) {
        this.httpRequest = request;
    }

    /**
     * 验证访问权限
     * 
     * @return
     */
    protected boolean validateSecretkey() {
        if (!METHOD_POST.equals(httpRequest.getMethod().toUpperCase())) {
            log.debug("method is request.getMethod()");
            return false;
        }
        InterfaceSecurityInfo user = baseinfoManager.findUseringByName(username, source, new Date());
        if (user == null) {
            return false;
        }
        String key = username + user.getPassword() + calldate;
        return AppSecretUtil.isEqualsSecret(key, secretkey);
    }
}
