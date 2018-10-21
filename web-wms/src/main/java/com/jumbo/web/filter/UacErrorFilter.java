package com.jumbo.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;

import com.jumbo.manager.SysConfigManager;

public class UacErrorFilter implements Filter {

    public static final Logger log = LoggerFactory.getLogger(UacErrorFilter.class);
    public static final String GET_CACHE_TOKEN_ERROR = "get_cache_token_error";


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("UacErrorFilter1");
        String urlAppKey = null;
        try {
            chain.doFilter(request, response);
        } catch (RuntimeException e) {
            if (e.getMessage() != null) {
                if (GET_CACHE_TOKEN_ERROR.equals(e.getMessage())) {
                    SysConfigManager cfg = ContextLoader.getCurrentWebApplicationContext().getBean(SysConfigManager.class);
                    urlAppKey = cfg.getUrlAppKey();
                    log.error("get_cache_token_error");
                    // request.getRequestDispatcher("/oauth/back/{appKey}").forward(request,
                    // response);
                    ((HttpServletResponse) response).sendRedirect(urlAppKey);
                }
            }
                
        } catch (Exception e) {
            log.error("get_cache_token_error2", e.getMessage());
        }
        log.info("UacErrorFilter2");
    }

    @Override
    public void destroy() {}

}
