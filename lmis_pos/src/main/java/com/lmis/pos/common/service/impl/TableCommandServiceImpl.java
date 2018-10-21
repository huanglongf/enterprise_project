package com.lmis.pos.common.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.lmis.framework.baseInfo.LmisConstant;
import com.lmis.framework.baseInfo.LmisPageObject;
import com.lmis.framework.baseInfo.LmisResult;
import com.lmis.pos.common.dao.TableCommandMapper;
import com.lmis.pos.common.service.TableCommandService;

/**
* Title: TableCommandServiceImpl
* Description: 系统数据库表操作服务提供类
* Company: baozun
* @author jindong.lin
* @date 2018年3月23日
*/
@Service
public class TableCommandServiceImpl implements TableCommandService {
	
	@Autowired
	TableCommandMapper tableCommandMapper;

//	@Override
//	@Transactional(readOnly=false,rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
//	public void createTable(String tableName, List<String> columns) throws Exception{
//		tableName = tableName.replaceAll("'", "").replaceAll("\"", "").replaceAll("`", "");
//		tableCommandMapper.createTable(tableName,columns);
//	}

	@Override
	@Transactional(readOnly=false,rollbackFor=Exception.class,propagation=Propagation.REQUIRES_NEW)
	public void createTable(String tableName, List<String> columns, String suffix) throws Exception{
		tableName = tableName.replaceAll("'", "").replaceAll("\"", "").replaceAll("`", "");
		tableCommandMapper.createTable(tableName,columns,suffix);
	}

	@Override
	@Transactional(readOnly=false,rollbackFor=Exception.class,propagation=Propagation.REQUIRES_NEW)
	public Integer insertBatchData(String tableName, List<String> columns,List<List<String>> dataList) throws Exception{
		Integer r = 0;
		List<List<List<String>>> partition = Lists.partition(dataList, 100);
		for (List<List<String>> subList : partition) {
			r += tableCommandMapper.insertBatchData(subList,tableName,columns);
		}
		return r;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public int insertBatchData(List<List<String>> dataList, String tableName, List<String> columns,
			List<String> columnTypes) {
		Integer r = 0;
		List<List<List<String>>> partition = Lists.partition(dataList, 100);
		for (List<List<String>> subList : partition) {
			r += tableCommandMapper.insertBatchDataByType(subList,tableName,columns,columnTypes);
		}
		return r;
	}

	@Override
	@Transactional(readOnly=false,rollbackFor=Exception.class,propagation=Propagation.REQUIRES_NEW)
	public LmisResult<?> alterExcelTableColumn(String tableName, String oldColumnName, 
			String newColumnName, String columnDataType) throws Exception{
		LmisResult<String> lmisResult = new LmisResult<String>(LmisConstant.RESULT_CODE_ERROR,
																"操作失败");
		//数据格式校验
		if(StringUtils.isBlank(tableName) || StringUtils.isBlank(oldColumnName)){
			lmisResult.setCode(LmisConstant.RESULT_CODE_ERROR);
			lmisResult.setMessage("操作失败，tableName,oldColumnName,newColumnName参数不能为空");
		}
		if(tableName.indexOf("'") > 0 || tableName.indexOf("\"") > 0 || tableName.indexOf("`") > 0 
				||oldColumnName.indexOf("'") > 0 || oldColumnName.indexOf("\"") > 0 || oldColumnName.indexOf("`") > 0 
				||newColumnName.indexOf("'") > 0 || newColumnName.indexOf("\"") > 0 || newColumnName.indexOf("`") > 0) {
			lmisResult.setCode(LmisConstant.RESULT_CODE_ERROR);
			lmisResult.setMessage("操作失败，tableName,oldColumnName,newColumnName,columnDataType参数不能含有非法字符',\",`");
		}
		if(StringUtils.isBlank(columnDataType)){
			//查询原来的数据类型
			Map<String,Object> columnInfoMap = tableCommandMapper.getColumnInfoMap(tableName,oldColumnName);
			columnDataType = columnInfoMap.get("COLUMN_TYPE").toString();
		}
		//数据操作
		tableCommandMapper.alterExcelTableColumn(tableName,
												oldColumnName,newColumnName,
												columnDataType);
		lmisResult.setCode(LmisConstant.RESULT_CODE_SUCCESS);
		lmisResult.setMessage("物理字段操作成功");
		return lmisResult;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public Map<String, Object> execSql(String execSql) throws Exception{
		Map<String, Object> result = null;
		try {
			result = tableCommandMapper.execSql(execSql);
		} catch (Exception e) {
			throw new Exception("sql异常，请确认sql格式是否正确");
		}
		return result;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public List<Map<String, Object>> execSqlData(String execSql) throws Exception{
		return tableCommandMapper.execSqlData(execSql);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public LmisResult<?> executeSelect(String tableName, LmisPageObject pageObject) throws Exception{
		LmisResult<Map<String, Object>> lmisResult = new LmisResult<>();
		tableName = tableName.replaceAll("'", "").replaceAll("\"", "").replaceAll("`", "");
		Page<Map<String, Object>> page = PageHelper.startPage(pageObject.getPageNum(), pageObject.getPageSize());
		tableCommandMapper.executeSelectByTableName(tableName);
		lmisResult.setMetaAndData(page.toPageInfo());
		lmisResult.setCode(LmisConstant.RESULT_CODE_SUCCESS);
		return lmisResult;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public LmisResult<?> executeSelectBySql(String execSql, LmisPageObject pageObject) throws Exception{
		LmisResult<Map<String, Object>> lmisResult = new LmisResult<>();
		Page<Map<String, Object>> page = PageHelper.startPage(pageObject.getPageNum(), pageObject.getPageSize());
		tableCommandMapper.executeSelectBySql(execSql);
		lmisResult.setMetaAndData(page.toPageInfo());
		lmisResult.setCode(LmisConstant.RESULT_CODE_SUCCESS);
		return lmisResult;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public List<Map<String,Object>> exportSqlData(String execSql) throws Exception{
		return tableCommandMapper.executeSelectBySql(execSql);
	}

	@Override
	@Transactional(readOnly=false,rollbackFor=Exception.class,propagation=Propagation.REQUIRES_NEW)
	public LmisResult<?> alterTableTrancate(String tableName) throws Exception{
		LmisResult<String> lmisResult = new LmisResult<String>(LmisConstant.RESULT_CODE_ERROR,
																"操作失败");
		if(StringUtils.isBlank(tableName)){
			lmisResult.setCode(LmisConstant.RESULT_CODE_ERROR);
			lmisResult.setMessage("操作失败,tableName参数不能为空");
		}
		tableName = tableName.replaceAll("'", "").replaceAll("\"", "").replaceAll("`", "");
		tableCommandMapper.alterTableTrancate(tableName);
		lmisResult.setCode(LmisConstant.RESULT_CODE_SUCCESS);
		return lmisResult;
	}

	@Override
	@Transactional(readOnly=false,rollbackFor=Exception.class,propagation=Propagation.REQUIRES_NEW)
	public LmisResult<?> alterTableDropTable(String tableName) throws Exception {
		LmisResult<String> lmisResult = new LmisResult<String>(LmisConstant.RESULT_CODE_ERROR,
				"操作失败");
		if(StringUtils.isBlank(tableName)){
			lmisResult.setCode(LmisConstant.RESULT_CODE_ERROR);
			lmisResult.setMessage("操作失败,tableName参数不能为空");
		}
		tableName = tableName.replaceAll("'", "").replaceAll("\"", "").replaceAll("`", "");
		tableCommandMapper.alterTableDropTable(tableName);
		lmisResult.setCode(LmisConstant.RESULT_CODE_SUCCESS);
		return lmisResult;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public List<Map<String, Object>> getTables(String dataSourceId, String dbName) {
		return tableCommandMapper.getTablesInfoMap(dataSourceId,dbName);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public List<Map<String, Object>> getFields(String dataSourceId, String dbName, String tableId) {
		return tableCommandMapper.getColumnListInfoMap(dataSourceId,dbName,tableId);
	}

}
