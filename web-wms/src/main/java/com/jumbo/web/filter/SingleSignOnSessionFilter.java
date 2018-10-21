package com.jumbo.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.spy.memcached.MemcachedClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.web.context.ContextLoader;

import com.jumbo.manager.SysConfigManager;
import com.jumbo.web.security.UserDetails;

public class SingleSignOnSessionFilter implements Filter {

    public static final Logger log = LoggerFactory.getLogger(SingleSignOnSessionFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            String url = httpRequest.getRequestURI();
            if (url.contains("/pda/")) {
                // 如果是pda请求
                if (!url.contains("/pda/login.do") && !url.contains("/pda/checkSingleogin.do") && !url.contains("/pda/userlogin.do") && !url.contains("/pda/pdaExit.do")) {
                    // 过滤非PDA登入操作URL
                    HttpSession session = httpRequest.getSession();
                    UserDetails userDetails = (UserDetails) session.getAttribute("userDetials");
                    if (null != userDetails) {
                        boolean b = checkSingleSign(session.getId(), userDetails.getUsername());
                        // 别的设备登入 弹出现有设备
                        if (!b) {
                            log.info("pda current user is login,user : {} , session : {}", userDetails.getUsername(), session.getId());
                            if (response instanceof HttpServletResponse && request instanceof HttpServletRequest) {
                                // 如果是ajax请求响应头会有x-requested-with
                                // if (httpRequest.getHeader("x-requested-with") != null &&
                                // httpRequest.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest"))
                                // {
                                // HttpServletResponse res = (HttpServletResponse) response;
                                // res.setHeader("sessionstatus", "timeout");
                                // res.setStatus(403);
                                // // 向http头添加登录的url
                                // res.addHeader("loginPath", "/pad/login.do");
                                // chain.doFilter(request, response);
                                // return;
                                // } else {
                                // 非ajax请求时，session失效的处理
                                HttpServletResponse res = (HttpServletResponse) response;
                                HttpServletRequest req = (HttpServletRequest) request;
                                res.sendRedirect(req.getContextPath() + "/pda/pdaExit.do");
                                return;
                                // }
                            }
                        }
                    }
                }
            } else {
                SysConfigManager cfg = ContextLoader.getCurrentWebApplicationContext().getBean(SysConfigManager.class);
                if (cfg.getIsSignleSign() == SysConfigManager.USER_SIGNLE_SIGN_ON) {
                    HttpSession session = httpRequest.getSession();
                    Object obj = session.getAttribute("SPRING_SECURITY_CONTEXT");
                    if (obj instanceof SecurityContextImpl) {
                        SecurityContextImpl sc = (SecurityContextImpl) obj;
                        Object pl = sc.getAuthentication().getPrincipal();
                        if (pl instanceof UserDetails) {
                            UserDetails user = (UserDetails) pl;
                            boolean rs = checkSingleSign(session.getId(), user.getUsername());
                            if (!rs) {
                                log.info("current user is login,user : {} , session : {}", user.getUsername(), session.getId());
                                // 如果校验失败登出系统
                                session.setAttribute("SPRING_SECURITY_CONTEXT", null);
                                if (response instanceof HttpServletResponse && request instanceof HttpServletRequest) {
                                    HttpServletResponse res = (HttpServletResponse) response;
                                    HttpServletRequest req = (HttpServletRequest) request;
                                    res.sendRedirect(req.getContextPath() + "/lout");
                                    return;
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.warn("", e);
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {}

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
                if (currentSessionId.equals(sid)) {
                    return true;
                }
            }
        }
        return false;
    }
}
