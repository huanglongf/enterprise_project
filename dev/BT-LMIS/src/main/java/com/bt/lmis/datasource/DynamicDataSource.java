package com.bt.lmis.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/** 
* @ClassName: DynamicDataSource 
* @Description: TODO(重写determineCurrentLookupKey()方法，来达到动态切换数据库) 
* @author Yuriy.Jiang
* @date 2016年12月1日 下午5:19:09 
*  
*/
public class DynamicDataSource extends AbstractRoutingDataSource {
	
	@Override
	protected Object determineCurrentLookupKey() {
		return DataSourceContextHolder. getDbType();
	}

}
