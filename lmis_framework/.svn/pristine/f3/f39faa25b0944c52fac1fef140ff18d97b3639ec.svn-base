package com.lmis.framework.db;

import java.io.IOException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import com.github.pagehelper.PageHelper;
import com.lmis.framework.interceptor.SqlCombineInterceptor;
import com.lmis.framework.interceptor.SqlPrintInterceptor;

/** 
 * @ClassName: MybatisConfiguration
 * @Description: TODO(mybatis配置)
 * @author Ian.Huang
 * @date 2017年10月8日 下午4:08:22 
 * 
 */
@MapperScan(basePackages = "com.lmis.*.dao", sqlSessionFactoryRef = "sqlSessionFactory")
@EnableTransactionManagement
@ConfigurationProperties(prefix = DbConstant.MYBATIS_PREFIX)
@AutoConfigureAfter(DataSourcesConfiguration.class)
@Configuration
public class MybatisConfiguration implements TransactionManagementConfigurer {

private static Log logger = LogFactory.getLog(MybatisConfiguration.class);
	
	// 配置类型别名
	private String typeAliasesPackage;
	
	// 配置mapper的扫描，找到所有的mapper.xml映射文件  
	private String mapperLocations;
	
	// 加载全局的配置文件
	private String configLocation; 
	
	public String getTypeAliasesPackage() {
		return typeAliasesPackage;
	}

	public void setTypeAliasesPackage(String typeAliasesPackage) {
		this.typeAliasesPackage = typeAliasesPackage;
	}

	public String getMapperLocations() {
		return mapperLocations;
	}

	public void setMapperLocations(String mapperLocations) {
		this.mapperLocations = mapperLocations;
	}

	public String getConfigLocation() {
		return configLocation;
	}

	public void setConfigLocation(String configLocation) {
		this.configLocation = configLocation;
	}

	@Autowired
	private DataSource dataSource;
	
	/* (非 Javadoc) 
	 * <p>Title: annotationDrivenTransactionManager</p> 
	 * <p>Description: 事务管理</p> 
	 * @return 
	 * @see org.springframework.transaction.annotation.TransactionManagementConfigurer#annotationDrivenTransactionManager() 
	 */
	@Bean
	@Override
	public PlatformTransactionManager annotationDrivenTransactionManager() {
		return new DataSourceTransactionManager(dataSource);
	}
	
	
	/**
     * @Title: sqlCombineInterceptor
     * @Description: TODO(将要执行的sql进行自定义拼装)
     * @return: SqlCombineInterceptor
     * @author: xuyisu
     * @date: 2018年05月07日 下午14:28:08
     */
    @Bean  
    public SqlCombineInterceptor sqlCombineInterceptor(){  
        return new SqlCombineInterceptor();  
    }
	
	/**
	 * @Title: pageHelper
	 * @Description: TODO()
	 * @return: PageHelper
	 * @author: Ian.Huang
	 * @date: 2017年10月6日 下午8:35:25
	 */
	@Bean
	public PageHelper pageHelper() {  
		PageHelper pageHelper = new PageHelper();  
		Properties p = new Properties();  
		p.setProperty("offsetAsPageNum", "true");  
		p.setProperty("rowBoundsWithCount", "true");  
		p.setProperty("reasonable", "true");  
		p.setProperty("returnPageInfo", "check");  
		p.setProperty("params", "count=countSql");  
		pageHelper.setProperties(p);  
		return pageHelper;  
	}
	
    /**
     * @Title: sqlPrintInterceptor
     * @Description: TODO(将要执行的sql进行日志打印(不想拦截，就把这方法注释掉))
     * @return: SqlPrintInterceptor
     * @author: Ian.Huang
     * @date: 2017年10月6日 下午8:35:08
     */
    @Bean  
    public SqlPrintInterceptor sqlPrintInterceptor(){  
        return new SqlPrintInterceptor();  
    }
	
	// 提供SqlSeesion  
	@Bean(name = "sqlSessionFactory")  
	@Primary  
    public SqlSessionFactory sqlSessionFactory() {  
        try {  
			SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();  
			sessionFactoryBean.setDataSource(dataSource);  
			// 读取配置
			sessionFactoryBean.setTypeAliasesPackage(typeAliasesPackage);
			//设置mapper.xml文件所在位置
			Resource[] resources = new PathMatchingResourcePatternResolver().getResources(mapperLocations);  
			sessionFactoryBean.setMapperLocations(resources);
			//设置mybatis-config.xml配置文件位置
            sessionFactoryBean.setConfigLocation(new DefaultResourceLoader().getResource(configLocation));
            //添加分页插件、打印sql插件
            sessionFactoryBean.setPlugins(new Interceptor[]{pageHelper(),sqlPrintInterceptor(),sqlCombineInterceptor()});
            return sessionFactoryBean.getObject();
        } catch (IOException e) {  
            logger.error("mybatis resolver mapper*xml is error",e);  
            return null;  
        } catch (Exception e) {  
            logger.error("mybatis sqlSessionFactoryBean create error",e);  
            return null;  
        }  
    }  

    @Bean  
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {  
        return new SqlSessionTemplate(sqlSessionFactory);  
    }

}
