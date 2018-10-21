package com.lmis.framework.db;

import java.sql.SQLException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.lmis.common.dataFormat.ObjectUtils;


/** 
 * @ClassName: DataSourcesConfiguration
 * @Description: TODO(数据源配置)
 * @author Ian.Huang
 * @date 2017年10月8日 下午3:24:48 
 * 
 */
@Configuration
@ConfigurationProperties(prefix = DbConstant.DB_PREFIX)
public class DataSourcesConfiguration {

	private static final Logger logger = LoggerFactory.getLogger(DataSourcesConfiguration.class);
	
	// mysql
	private String driverClassName;
	private String url;
	private String username;
	private String password;
	// druid
	private Integer initialSize;
	private Integer minIdle;
	private Integer maxActive;
	private Long maxWait;
	private Long timeBetweenEvictionRunsMillis;
	private Long minEvictableIdleTimeMillis;
	private String validationQuery;
	private boolean testWhileIdle;
	private boolean testOnBorrow;
	private boolean testOnReturn;
	private boolean poolPreparedStatements;
	private Integer maxPoolPreparedStatementPerConnectionSize;
	private String filters;
	private String connectionProperties;
	private boolean useGlobalDataSourceStat;
	private String allowIp;
	private String denyIp;
	private String druidLoginUsername;
	private String druidLoginPassword;
	
	public String getDriverClassName() {
		return driverClassName;
	}

	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getInitialSize() {
		return initialSize;
	}

	public void setInitialSize(Integer initialSize) {
		this.initialSize = initialSize;
	}

	public Integer getMinIdle() {
		return minIdle;
	}

	public void setMinIdle(Integer minIdle) {
		this.minIdle = minIdle;
	}

	public Integer getMaxActive() {
		return maxActive;
	}

	public void setMaxActive(Integer maxActive) {
		this.maxActive = maxActive;
	}

	public Long getMaxWait() {
		return maxWait;
	}

	public void setMaxWait(Long maxWait) {
		this.maxWait = maxWait;
	}

	public Long getTimeBetweenEvictionRunsMillis() {
		return timeBetweenEvictionRunsMillis;
	}

	public void setTimeBetweenEvictionRunsMillis(Long timeBetweenEvictionRunsMillis) {
		this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
	}

	public Long getMinEvictableIdleTimeMillis() {
		return minEvictableIdleTimeMillis;
	}

	public void setMinEvictableIdleTimeMillis(Long minEvictableIdleTimeMillis) {
		this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
	}

	public String getValidationQuery() {
		return validationQuery;
	}

	public void setValidationQuery(String validationQuery) {
		this.validationQuery = validationQuery;
	}

	public boolean isTestWhileIdle() {
		return testWhileIdle;
	}

	public void setTestWhileIdle(boolean testWhileIdle) {
		this.testWhileIdle = testWhileIdle;
	}

	public boolean isTestOnBorrow() {
		return testOnBorrow;
	}

	public void setTestOnBorrow(boolean testOnBorrow) {
		this.testOnBorrow = testOnBorrow;
	}

	public boolean isTestOnReturn() {
		return testOnReturn;
	}

	public void setTestOnReturn(boolean testOnReturn) {
		this.testOnReturn = testOnReturn;
	}

	public boolean isPoolPreparedStatements() {
		return poolPreparedStatements;
	}

	public void setPoolPreparedStatements(boolean poolPreparedStatements) {
		this.poolPreparedStatements = poolPreparedStatements;
	}

	public Integer getMaxPoolPreparedStatementPerConnectionSize() {
		return maxPoolPreparedStatementPerConnectionSize;
	}

	public void setMaxPoolPreparedStatementPerConnectionSize(Integer maxPoolPreparedStatementPerConnectionSize) {
		this.maxPoolPreparedStatementPerConnectionSize = maxPoolPreparedStatementPerConnectionSize;
	}

	public String getFilters() {
		return filters;
	}

	public void setFilters(String filters) {
		this.filters = filters;
	}

	public String getConnectionProperties() {
		return connectionProperties;
	}

	public void setConnectionProperties(String connectionProperties) {
		this.connectionProperties = connectionProperties;
	}

	public boolean isUseGlobalDataSourceStat() {
		return useGlobalDataSourceStat;
	}

	public void setUseGlobalDataSourceStat(boolean useGlobalDataSourceStat) {
		this.useGlobalDataSourceStat = useGlobalDataSourceStat;
	}
	
	public String getAllowIp() {
		return allowIp;
	}

	public void setAllowIp(String allowIp) {
		this.allowIp = allowIp;
	}

	public String getDenyIp() {
		return denyIp;
	}

	public void setDenyIp(String denyIp) {
		this.denyIp = denyIp;
	}

	public String getDruidLoginUsername() {
		return druidLoginUsername;
	}

	public void setDruidLoginUsername(String druidLoginUsername) {
		this.druidLoginUsername = druidLoginUsername;
	}

	public String getDruidLoginPassword() {
		return druidLoginPassword;
	}

	public void setDruidLoginPassword(String druidLoginPassword) {
		this.druidLoginPassword = druidLoginPassword;
	}

	/**
	 * @Title: dataSource
	 * @Description: TODO(druid初始化)
	 * @throws SQLException
	 * @return: DruidDataSource
	 * @author: Ian.Huang
	 * @date: 2017年9月26日 下午1:27:08
	 */
	@Bean(name="dataSource",destroyMethod = "close", initMethod="init")
	@Primary// 默认数据源
	public DruidDataSource dataSource() throws SQLException {
	    DruidDataSource dataSource = new DruidDataSource();
	    // mysql驱动
	    dataSource.setDriverClassName(driverClassName);
	    // url
	    dataSource.setUrl(url);
	    // 用户名
	    dataSource.setUsername(username);
	    // 密码
	    dataSource.setPassword(password);
	    // 配置初始连接
	    dataSource.setInitialSize(initialSize);
	    // 配置最小连接
	    dataSource.setMinIdle(minIdle);
	    // 配置最大连接
	    dataSource.setMaxActive(maxActive);
	    // 连接等待超时时间
	    dataSource.setMaxWait(maxWait);
	    // 间隔多久进行检测,关闭空闲连接
	    dataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
	    // 一个连接最小生存时间
	    dataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
	    // 用来检测是否有效的sql
	    if (!ObjectUtils.isNull(validationQuery)) {dataSource.setValidationQuery(validationQuery);}
	    dataSource.setTestWhileIdle(testWhileIdle);
	    dataSource.setTestOnBorrow(testOnBorrow);
	    dataSource.setTestOnReturn(testOnReturn);
	    // 打开PSCache,并指定每个连接的PSCache大小
	    dataSource.setPoolPreparedStatements(poolPreparedStatements);
	    if(poolPreparedStatements) {
	        dataSource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
	    }
	    // 这是最关键的,否则SQL监控无法生效
	    dataSource.setFilters(filters);
	    if(!ObjectUtils.isNull(connectionProperties)) {
	        Properties connectProperties = new Properties();
	        String[] propertiesList = connectionProperties.split(";");
	        for(String propertiesTmp:propertiesList){
	            String[] obj = propertiesTmp.split("=");
	            String key = obj[0]; String value = obj[1];
	            connectProperties.put(key,value);	
	        }
	        dataSource.setConnectProperties(connectProperties);	
	    }
	    dataSource.setUseGlobalDataSourceStat(useGlobalDataSourceStat);
	    try {
            dataSource.init();
        } catch (SQLException e) {
            throw new RuntimeException("druid datasource init fail");
        }
	    return dataSource;
	}
	
	/**
	 * @Title: druidServlet
	 * @Description: TODO(druid 监控登录界面)
	 * @return: ServletRegistrationBean
	 * @author: Ian.Huang
	 * @date: 2017年9月28日 下午8:25:00
	 */
	@Bean
	public ServletRegistrationBean druidServlet() {
		logger.info("init Druid Servlet Configuration");
		// org.springframework.boot.context.embedded.ServletRegistrationBean提供类的进行注册
		ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
		//添加初始化参数：initParams
		// IP白名单
		servletRegistrationBean.addInitParameter("allow", allowIp);
		// IP黑名单(共同存在时，deny优先于allow)
		servletRegistrationBean.addInitParameter("deny", denyIp);
		// 控制台管理用户
		servletRegistrationBean.addInitParameter("loginUsername", druidLoginUsername);
		servletRegistrationBean.addInitParameter("loginPassword", druidLoginPassword);
		// 是否能够重置数据 禁用HTML页面上的“Reset All”功能
		servletRegistrationBean.addInitParameter("resetEnable", "false");
		return servletRegistrationBean;
	}
	
	/**
	 * @Title: druidFilterRegistrationBean
	 * @Description: TODO(druid url过滤)
	 * @return: FilterRegistrationBean
	 * @author: Ian.Huang
	 * @date: 2017年9月28日 下午8:25:17
	 */
	@Bean(name="druidFilterRegistrationBean")
	public FilterRegistrationBean druidFilterRegistrationBean() {
		FilterRegistrationBean druidFilterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
		druidFilterRegistrationBean.addUrlPatterns("/*");
		druidFilterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
		// druidFilterRegistrationBean.setOrder(Integer.MAX_VALUE);
		druidFilterRegistrationBean.addInitParameter("profileEnable", "true");
		druidFilterRegistrationBean.addInitParameter("principalCookieName", "USER_COOKIE");
		druidFilterRegistrationBean.addInitParameter("principalSessionName", "USER_SESSION");
		return druidFilterRegistrationBean;
	}
	
}
