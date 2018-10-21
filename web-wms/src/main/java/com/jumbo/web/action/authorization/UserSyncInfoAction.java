package com.jumbo.web.action.authorization;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.baozun.scm.baseservice.account.CommonUserInfo;
import com.baozun.scm.baseservice.account.oauth.OauthSyncUserInfo;
import com.baozun.scm.baseservice.account.util.OauthUserUtils;
import com.jumbo.util.comm.HttpBodyUtil;
import com.jumbo.web.action.BaseAction;

public class UserSyncInfoAction extends BaseAction {

    /**
     * 
     */
    private static final long serialVersionUID = -8697370006262025991L;

    @Autowired
    private OauthSyncUserInfo oauthSyncUserInfo;

    private String sign;

    private String contentType = "text/html;charset=utf-8";

    /**
     * 同步用户信息
     * 
     * @author wenjin.gao
     * @param sign
     * @param param
     * @param request
     * @return 0:失败; 1:成功
     * @throws IOException
     */
    public String syncUserInfo() throws IOException {

        ServletActionContext.getResponse().setContentType(contentType);
        PrintWriter out = ServletActionContext.getResponse().getWriter();
        HttpServletRequest httpRequest = ServletActionContext.getRequest();
        InputStream is = httpRequest.getInputStream();
        httpRequest.setCharacterEncoding("UTF-8");
        int size = httpRequest.getContentLength();

        String param = HttpBodyUtil.processRequest(is, size);
        log.error("syncUserInfo info sign:" + sign + "param:" + param);
        CommonUserInfo commonUserInfo = OauthUserUtils.getCommonUser(sign, param);
        if (null == commonUserInfo) {
            log.error("syncUserInfo info is error");
            out.print("0");
            out.flush();
            out.close();
            return SUCCESS;
        }
        boolean flag = oauthSyncUserInfo.syncUserInfo(commonUserInfo);

        if (flag) {
            // 成功
            out.print("1");
            out.flush();
            out.close();
            return SUCCESS;
        }
        out.print("0");
        out.flush();
        out.close();
        return SUCCESS;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

}
