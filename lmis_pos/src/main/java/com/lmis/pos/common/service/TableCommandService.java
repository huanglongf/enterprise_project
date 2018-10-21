package com.lmis.pos.common.service;

import java.util.List;
import java.util.Map;

import com.lmis.framework.baseInfo.LmisPageObject;
import com.lmis.framework.baseInfo.LmisResult;

public interface TableCommandService {

//	void createTable(String tableName, List<String> columns) throws Exception;
	
	void createTable(String tableName, List<String> columns, String suffix) throws Exception;
	
	public Integer insertBatchData(String tableName, List<String> columns,List<List<String>> dataList) throws Exception;
	
	/**
	 * Title: alterExcelTableColumn
	 * Description: 基于物理表名，列名修改列名、列类型
	 * @param tableName
	 * @param oldColumnName
	 * @param newColumnName
	 * @param columnDataType
	 * @return
	 * @throws Exception
	 * @author lsh8044
	 * @date 2018年4月4日
	 */
	LmisResult<?> alterExcelTableColumn(String tableName, String oldColumnName, 
										String newColumnName, String columnDataType) throws Exception;

	Map<String, Object> execSql(String execSql) throws Exception;

	List<Map<String, Object>> execSqlData(String execSql) throws Exception;

	/**
	 * Title: executeSelect
	 * Description: tableName 物理表名称
	 * @param tableName
	 * @param pageObject
	 * @return
	 * @author lsh8044
	 * @date 2018年4月4日
	 */
	LmisResult<?> executeSelect(String tableName, LmisPageObject pageObject) throws Exception;

	/**
	 * Title: alterTableTrancate
	 * Description: 基于物理表名清空物理表
	 * @param tableName
	 * @return
	 * @throws Exception
	 * @author lsh8044
	 * @date 2018年4月4日
	 */
	LmisResult<?> alterTableTrancate(String tableName) throws Exception;

	/**
	 * Title: alterTableDropTable
	 * Description: 基于物理表名删除物理表
	 * @param tableName
	 * @return
	 * @throws Exception
	 * @author lsh8044
	 * @date 2018年4月4日
	 */
	LmisResult<?> alterTableDropTable(String tableName) throws Exception;

	LmisResult<?> executeSelectBySql(String dataSetCode, LmisPageObject pageObject) throws Exception;

	List<Map<String,Object>> exportSqlData(String execSql) throws Exception;

	int insertBatchData(List<List<String>> list, String tableName, List<String> columns, List<String> columnTypes);

	List<Map<String, Object>> getTables(String dataSourceId, String dbName);

	List<Map<String, Object>> getFields(String dataSourceId, String dbName, String tableId);

}
