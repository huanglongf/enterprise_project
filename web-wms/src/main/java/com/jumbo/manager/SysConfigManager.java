package com.jumbo.manager;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service("sysConfigManager")
public class SysConfigManager {

	public static final int USER_SIGNLE_SIGN_ON = 1;

	/**
	 * 是否使用单点登录
	 */
	@Value("${signle.sign}")
	private Integer isSignleSign = 0;

    /**
     * web.appkey获取来重定向登录
     * 
     * @return
     */
    @Value("${web.appkey}")
    private String appKey;

    @Value("${web.urlappkey}")
    private String urlAppKey;

    public String getUrlAppKey() {
        return urlAppKey;
    }

    public String getAppKey() {
        return appKey;
    }

    public Integer getIsSignleSign() {
		return isSignleSign;
	}
}
