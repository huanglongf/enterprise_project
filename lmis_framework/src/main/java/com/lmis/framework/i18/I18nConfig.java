package com.lmis.framework.i18;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.lmis.framework.db.DataSourcesConfiguration;

/**
 * @author daikaihua
 * @date 2017年11月17日
 * @todo 国际化配置文件
 */
@Configuration
public class I18nConfig {
	private static final Logger logger = LoggerFactory.getLogger(DataSourcesConfiguration.class);

    @Bean(name = "localeResolver")
    public LmisLocaleResolver lmisLocaleResolver(){
        logger.info("cookieLocaleResolver---create");

        LmisLocaleResolver lmisLocaleResolver = new LmisLocaleResolver();
        lmisLocaleResolver.setDefaultLocale(Locale.ENGLISH);
        logger.info("cookieLocaleResolver:");
        return lmisLocaleResolver;
    }
}