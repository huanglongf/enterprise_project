package com.lmis.framework.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author daikaihua
 * @date 2017年11月15日
 * @todo 过滤器实现
 */
@WebFilter(filterName="LmisFilter",urlPatterns="/*")
public class LmisFilter implements Filter {
	
	private static Log logger = LogFactory.getLog(LmisFilter.class);
	
	@Override
    public void destroy() {
		logger.info("filter end......");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
    	logger.info("filter begin......");
        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig config) throws ServletException {
    	logger.info("filter init......");
    }
}
