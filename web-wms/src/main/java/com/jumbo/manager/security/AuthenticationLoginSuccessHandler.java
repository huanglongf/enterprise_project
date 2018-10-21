package com.jumbo.manager.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.web.context.ContextLoader;

import com.jumbo.web.security.UserDetails;

import net.spy.memcached.MemcachedClient;

/**
 * 登录成功回调处理
 * 
 * @author sjk
 *
 */
public class AuthenticationLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	public static final Logger log = LoggerFactory.getLogger(AuthenticationLoginSuccessHandler.class);

	public AuthenticationLoginSuccessHandler() {
		super();
	}

	public AuthenticationLoginSuccessHandler(String defaultTargetUrl) {
		super(defaultTargetUrl);
	}

	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		handle(request, response, authentication);
		clearAuthenticationAttributes(request);
		try {
			Object obj = authentication.getPrincipal();
			if (obj instanceof UserDetails) {
				UserDetails user = (UserDetails) obj;
				if (user.getUser() != null) {
					log.info("user : {}, session : {}", user.getUser().getLoginName(), request.getSession().getId());
					MemcachedClient client = ContextLoader.getCurrentWebApplicationContext()
							.getBean(MemcachedClient.class);
					client.set(user.getUser().getLoginName(), 3600 * 24, request.getSession().getId());
				}
			}
		} catch (Exception e) {
			log.error("", e);
		}

	}

}
