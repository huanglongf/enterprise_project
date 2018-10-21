package com.bt.lmis.datasource;

/** 
* @ClassName: DataSourceContextHolder 
* @Description: TODO(切换数据库工具类) 
* @author Yuriy.Jiang
* @date 2016年12月1日 下午5:17:10 
*  
*/
public class DataSourceContextHolder {
	//线程安全的ThreadLocal
	private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();

	/** 
	* @Title: setDbType 
	* @Description: TODO(设置当前数据库) 
	* @param @param dbType    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	public static void setDbType(String dbType) {
		contextHolder.set(dbType);
	}

	/** 
	* @Title: getDbType 
	* @Description: TODO(取得当前数据源) 
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws 
	*/
	public static String getDbType() {
		return ((String) contextHolder.get());
	}

	/** 
	* @Title: clearDbType 
	* @Description: TODO(清除上下文数据) 
	* @param     设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	public static void clearDbType() {
		contextHolder.remove();
	}
}
