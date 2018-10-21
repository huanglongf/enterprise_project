package com.jumbo.webservice.base;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;

import com.jumbo.wms.manager.baseinfo.BaseinfoManager;
import com.jumbo.wms.model.baseinfo.InterfaceSecurityInfo;
import com.jumbo.util.ToStringUtil;
import com.jumbo.util.zip.AppSecretUtil;

@Aspect
public class AuthenticationInteceptor implements Ordered {
	protected static final Logger log = LoggerFactory.getLogger(AuthenticationInteceptor.class);

	@Autowired
	private BaseinfoManager baseinfoManager;

	@Around("execution(public * com.jumbo.webservice..*.*Service.*(..)) && args(authRequest,..)")
	public Object around(ProceedingJoinPoint pjp, AuthRequest authRequest) throws AuthenticationFailedException {

		AuthRequestHeader authRequestHeader = authRequest.getAuthRequestHeader();
		// check sign
		if (!checkSign(authRequestHeader)) {
			throw new AuthenticationFailedException("checkSign failed" + authRequestHeader.getSign());
		}
		Signature signature = pjp.getSignature();
		// com.jumbo.webservice.pda.PdaService:login
		String requestPath = signature.getDeclaringTypeName() + ":" + signature.getName();

		if (!canCallNow(authRequestHeader.getOrganizationCode(), requestPath)) {
			throw new AuthenticationFailedException("can't call xxxxxxxxxx now,yyyyyy");
		}

		Object res = null;
		try {
			res = pjp.proceed();
		} catch (Throwable e) {
			log.error("", e);
		}

		if (res instanceof AuthResponse) {
			AuthResponse resp = (AuthResponse) res;
			signAuthResponse(authRequestHeader, resp.getAuthResponseHeader());
		}
		log.info(ToStringUtil.toStr(res, true));

		return res;
	}

	private void signAuthResponse(AuthRequestHeader authRequestHeader, AuthResponseHeader authResponseHeader) {
		String signSalt = "123";
		String serverSign = sign(authRequestHeader.getOrganizationCode(), "", signSalt, authResponseHeader.getCallbackTime());

		authResponseHeader.setSignSalt(signSalt);
		authResponseHeader.setSign(serverSign);
		authResponseHeader.setSequenceCode(authRequestHeader.getSequenceCode());
	}

	private boolean canCallNow(String organizationCode, String requestPath) {
		return true;
	}

	private boolean checkSign(AuthRequestHeader authRequestHeader) {
		String organizationCode = authRequestHeader.getOrganizationCode();
		String callTime = authRequestHeader.getCallTime();
		SimpleDateFormat sd = new SimpleDateFormat("yyyyMMddHHmmss");
		try {
			Long d = sd.parse(callTime).getTime() / 1000;
			Long d1 = new Date().getTime() / 1000;
			if (d1 - d > 300) {
				return false;
			}
		} catch (ParseException e) {
			log.error(e.getMessage());
			return false;
		}
		InterfaceSecurityInfo info = baseinfoManager.findInterfaceSecurityInfo(organizationCode);
		String signSalt = authRequestHeader.getSignSalt();
		String sign = authRequestHeader.getSign();
		if (info == null) {
			return false;
		}
		String serverSign = sign(organizationCode, info.getPassword(), signSalt, callTime);
		return serverSign.equalsIgnoreCase(sign);
	}

	private String sign(String organizationCode, String key, String signSalt, String callTime) {
		return AppSecretUtil.getMD5Str(organizationCode + callTime + signSalt + key);
	}

	public int getOrder() {
		return 1;
	}
}
