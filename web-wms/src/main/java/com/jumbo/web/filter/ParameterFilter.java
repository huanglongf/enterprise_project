package com.jumbo.web.filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.util.Map;

public class ParameterFilter implements Filter {

    public static final Logger log = LoggerFactory.getLogger(ParameterFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            HttpServletRequest rq = (HttpServletRequest)request;
            log.error("url:{}", rq.getRequestURI().toString());
            log.error("{}", request.getParameterMap());
            
            @SuppressWarnings("rawtypes")
            Map paramterMap = request.getParameterMap();
            for (Object obj : paramterMap.keySet()) {
                log.error("{}={}", obj.toString(), paramterMap.get(obj).toString());
            }
        } catch (Exception e) {
            log.error("", e);
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {}

}
