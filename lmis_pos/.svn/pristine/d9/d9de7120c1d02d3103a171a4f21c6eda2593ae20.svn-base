package com.lmis.pos.common.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
* Title: TableCommandMapper
* Description: 系统数据库表操作服务DAO映射接口
* Company: baozun
* @author jindong.lin
* @date 2018年3月23日
*/
@Mapper
@Repository
public interface TableCommandMapper {

	void createTable(@Param("tableName")String tableName, @Param("columns")List<String> columns);
	
	void createTable(@Param("tableName")String tableName, 
			@Param("columns")List<String> columns,
			@Param("suffix")String suffix);

	Integer insertBatchData(@Param("dataList")List<List<String>> dataList, 
							@Param("tableName")String tableName,
							@Param("columns")List<String> columns);

	Integer insertBatchDataByType(@Param("dataList")List<List<String>> dataList, 
							@Param("tableName")String tableName, 
							@Param("columns")List<String> columns,
							@Param("columnTypes")List<String> columnTypes);

	void alterExcelTableColumn(@Param("tableName")String tableName, 
			@Param("oldColumnName")String oldColumnName,
			@Param("newColumnName")String newColumnName,
			@Param("columnDataType")String columnDataType);

	Map<String, Object> getColumnInfoMap(@Param("tableName")String tableName, 
										@Param("oldColumnName")String oldColumnName);

	Map<String, Object> execSql(@Param("execSql")String execSql);

	List<Map<String, Object>> execSqlData(@Param("execSql")String execSql);

	List<Map<String, Object>> executeSelectByTableName(@Param("tableName")String tableName);

	List<Map<String, Object>> executeSelectBySql(@Param("execSql")String execSql);

	void alterTableRename(@Param("oldTableName")String oldTableName, @Param("newTableName")String newTableName);

	void alterTableTrancate(@Param("tableName")String tableName);

	void alterTableDropTable(@Param("tableName")String tableName);

	Map<String,Object> getExcelTableColumnName(@Param("tableName")String tableName);

	List<Map<String, Object>> getTablesInfoMap(@Param("dataSourceId")String dataSourceId, 
											   @Param("dbName")String dbName);

	List<Map<String, Object>> getColumnListInfoMap(@Param("dataSourceId")String dataSourceId, 
												@Param("dbName")String dbName, 
												@Param("tableName")String tableName);

}
