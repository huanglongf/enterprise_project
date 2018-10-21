package com.lmis.sys.login.controller;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.lmis.common.util.Base64Utils;
import com.lmis.common.util.OauthUtil;
import com.lmis.sys.login.service.LmisLoginServiceInterface;
import com.lmis.sys.user.model.SysUser;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author daikaihua
 * @date 2018年1月9日
 * @todo TODO
 */
@Api(value = "权限-4.5登陆功能")
@RestController
@RequestMapping(value="/sys")
public class LmisLoginController  {
	
	private static Logger logger = Logger.getLogger(LmisLoginController.class);

	@Resource(name="lmisLoginServiceImpl")
	LmisLoginServiceInterface<SysUser> lmisLoginService;
	
	@Autowired
	OauthUtil oauthUtil;    

	@ApiOperation(value="4.5.1登陆")
	@RequestMapping(value = "/lmisLogin", method = RequestMethod.POST)
    public String lmisLogin(String lmisUserId, String lmisPwd) throws Exception {
		SysUser sysUser = new SysUser();
		sysUser.setUserCode(lmisUserId);
		//解密登陆密码
		String pwd = Base64Utils.decoderBase64(lmisPwd);
		logger.info("~~~~~~user pwd~~~~~~"+lmisPwd+"~~~~~~~"+pwd);
		sysUser.setUserPwd(pwd);
		sysUser.setIsDeleted(false);
		return JSON.toJSONString(lmisLoginService.lmisLogin(sysUser));
    }

	@ApiOperation(value="4.5.2退出")
	@RequestMapping(value = "/lmisLogout", method = RequestMethod.POST)
    public String lmisLogout(String lmisUserId, String lmisTokenId) {
		return JSON.toJSONString(lmisLoginService.lmisLogout(lmisUserId, lmisTokenId));
	}
	
	@ApiOperation(value="4.5.3菜单树")
	@RequestMapping(value = "/getFbTree", method = RequestMethod.POST)
    public String getFbTree(String lmisUserId) {
		return JSON.toJSONString(lmisLoginService.getFbTree(lmisUserId));
	}
}