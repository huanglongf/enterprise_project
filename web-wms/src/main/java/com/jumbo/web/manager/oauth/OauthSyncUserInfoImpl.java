package com.jumbo.web.manager.oauth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.baozun.scm.baseservice.account.CommonUserInfo;
import com.baozun.scm.baseservice.account.oauth.OauthSyncUserInfo;
import com.jumbo.wms.manager.authorization.AuthorizationManager;
import com.jumbo.wms.model.authorization.User;

public class OauthSyncUserInfoImpl implements OauthSyncUserInfo {

    private static final Logger logger = LoggerFactory.getLogger(OauthSyncUserInfoImpl.class);

    @Autowired
    private AuthorizationManager authorizationManager;

    @Override
    public boolean syncUserInfo(CommonUserInfo commonUser) {
        User user = authorizationManager.findUserByUsernameForUac(commonUser.getLoginName());

        if (null == user) {
            user = new User();
        }

        user.setLoginName(commonUser.getLoginName());
        user.setUserName(commonUser.getUserName());
        user.setJobNumber(commonUser.getJobNumber());
        user.setEmail(commonUser.getEmail());
        user.setCreateTime(commonUser.getCreateTime());
        user.setIsAccNonLocked(true);
        if (1 == commonUser.getLifeCycle()) {
            user.setIsEnabled(true);
        } else {
            user.setIsEnabled(false);
        }
        try {
            authorizationManager.createOrUpdateUserForUAC(user);
        } catch (Exception e) {
            // e.printStackTrace();
            if (logger.isErrorEnabled()) {
                logger.error("同步用户失败 ,LoginName={}", new Object[] {commonUser.getLoginName()}, e);
            };
            // logger.error("同步用户失败 ,LoginName={}", new Object[] {commonUser.getLoginName()});
            return false;
        }
        return true;
    }
}
