package com.lmis.framework.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.lmis.framework.interceptor.LmisInterceptor;

/**
 * @author daikaihua
 * @date 2017年11月15日
 * @todo 配置拦截器
 */
@Configuration
public class LmisWebAppConfigurer extends WebMvcConfigurerAdapter {

	@Bean
	LmisInterceptor lmisInterceptor() {
        return new LmisInterceptor();
    }
	
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(lmisInterceptor()).addPathPatterns("/**").excludePathPatterns("/sys/lmisLogin").excludePathPatterns("/swagger*").excludePathPatterns("/v2/api-docs");
        super.addInterceptors(registry);
    }

}
