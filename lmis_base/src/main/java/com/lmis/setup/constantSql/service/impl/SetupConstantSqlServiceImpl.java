package com.lmis.setup.constantSql.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.lmis.common.dataFormat.ObjectUtils;
import com.lmis.common.dynamicSql.dao.DynamicSqlMapper;
import com.lmis.common.dynamicSql.model.DynamicSqlParam;
import com.lmis.common.dynamicSql.service.DynamicSqlServiceInterface;
import com.lmis.common.getData.dao.GetConstantDataMapper;
import com.lmis.common.getData.model.ConstantData;
import com.lmis.common.util.LimsLogUtil;
import com.lmis.common.util.SqlUtils;
import com.lmis.framework.baseInfo.LmisConstant;
import com.lmis.framework.baseInfo.LmisPageObject;
import com.lmis.framework.baseInfo.LmisResult;
import com.lmis.setup.constantSql.dao.SetupConstantSqlMapper;
import com.lmis.setup.constantSql.model.SetupConstantSql;
import com.lmis.setup.constantSql.service.SetupConstantSqlServiceInterface;
import com.lmis.setup.pageElement.dao.SetupPageElementMapper;
import com.lmis.setup.pageElement.model.SetupPageElement;

/** 
 * @ClassName: SetupConstantSqlServiceImpl
 * @Description: TODO(配置选项业务层实现类)
 * @author Ian.Huang
 * @date 2017年12月11日 下午2:57:54 
 * 
 * @param <T>
 */
@Transactional(rollbackFor=Exception.class)
@Service
public class SetupConstantSqlServiceImpl<T extends SetupConstantSql> implements SetupConstantSqlServiceInterface<T> {

	@Resource(name="dynamicSqlServiceImpl")
	DynamicSqlServiceInterface<Map<String, Object>> dynamicSqlService;
	@Autowired
	private SetupPageElementMapper<SetupPageElement> setupPageElementMapper;
	@Autowired
	private SetupConstantSqlMapper<T> setupConstantSqlMapper;
	@Autowired
	private GetConstantDataMapper<ConstantData> getConstantDataMapper;
	@Autowired
	private HttpSession session;
	@Autowired
	private DynamicSqlMapper<?> dynamicSqlMapper;
	@Autowired
	private LimsLogUtil limsLogUtil;

	
	@Override
	public LmisResult<?> executeSelect(DynamicSqlParam<T> dynamicSqlParam, LmisPageObject pageObject) throws Exception {
		return dynamicSqlService.executeSelect(dynamicSqlParam, pageObject);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public LmisResult<?> executeSelect(DynamicSqlParam<T> dynamicSqlParam) throws Exception {
		LmisResult<?> _lmisResult = dynamicSqlService.executeSelect(dynamicSqlParam);
		if(LmisConstant.RESULT_CODE_ERROR.equals(_lmisResult.getCode())) return _lmisResult;
		List<Map<String, Object>> result =  (List<Map<String, Object>>) _lmisResult.getData();
		if(ObjectUtils.isNull(result)) throw new Exception("查无记录，数据异常");
		if(result.size() != 1) throw new Exception("记录存在多条，数据异常");
		return new LmisResult<T>(LmisConstant.RESULT_CODE_SUCCESS, result.get(0));
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public LmisResult<?> executeInsert(DynamicSqlParam<T> dynamicSqlParam) throws Exception {
		SetupPageElement param = new SetupPageElement();
		param.setIsDeleted(false);
		param.setLayoutId(dynamicSqlParam.getLayoutId());
		param.setTableId("setup_constant_sql");
		param.setColumnId("sql_code");
		List<SetupPageElement> tmp = setupPageElementMapper.retrieve(param);
		if(ObjectUtils.isNull(tmp)) throw new Exception("无字段sql_code对应记录，数据异常");
		if(tmp.size() != 1) throw new Exception("字段sql_code存在多条记录，数据异常");
		// SQL 查询语句校验
		LmisResult<?> checkResult =  checkValidSQL(dynamicSqlParam.getElementValue("P_YMXS_N_P1_E03"));
		if (checkResult.getCode() != LmisConstant.RESULT_CODE_SUCCESS) {
			return checkResult;
		}
		// 唯一校验
		if(!ObjectUtils.isNull(setupConstantSqlMapper.retrieve((T) new SetupConstantSql(null, false, dynamicSqlParam.getElementValue(tmp.get(0).getElementId()))))) throw new Exception("sql编号已经存在，不能新增");
		return dynamicSqlService.executeInsert(dynamicSqlParam);
	}

	@SuppressWarnings("unchecked")
	@Override
	public LmisResult<?> executeUpdate(DynamicSqlParam<T> dynamicSqlParam) throws Exception {
		SetupPageElement param = new SetupPageElement();
		param.setIsDeleted(false);
		param.setLayoutId(dynamicSqlParam.getLayoutId());
		param.setTableId("setup_constant_sql");
		param.setColumnId("sql_code");
		List<SetupPageElement> tmp = setupPageElementMapper.retrieve(param);
		if(ObjectUtils.isNull(tmp)) throw new Exception("无字段sql_code对应记录，数据异常");
		if(tmp.size() != 1) throw new Exception("字段sql_code存在多条记录，数据异常");
		
		// SQL 查询语句校验
		LmisResult<?> checkResult =  checkValidSQL(dynamicSqlParam.getElementValue("P_YMXS_N_P1_E03"));
		if (checkResult.getCode() != LmisConstant.RESULT_CODE_SUCCESS) {
			return checkResult;
		}
		// 存在校验
		if(ObjectUtils.isNull(setupConstantSqlMapper.retrieve((T) new SetupConstantSql(null, false, dynamicSqlParam.getElementValue(tmp.get(0).getElementId()))))) throw new Exception("sql编号不存在，不能更新");
		return dynamicSqlService.executeUpdate(dynamicSqlParam);
	}
	
	@Override
	public LmisResult<T> executeSetupConstantSql(T t) throws Exception {
		return new LmisResult<T>(LmisConstant.RESULT_CODE_SUCCESS, JSONArray.toJSON(getConstantDataMapper.getConstantSqlData(t.getSqlRemark())));
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public LmisResult<?> deleteSetupConstantSql(T t) throws Exception {
			
		// 存在校验
		if(ObjectUtils.isNull(setupConstantSqlMapper.retrieve((T) new SetupConstantSql(t.getId(), false, t.getSqlCode())))) throw new Exception("sql编号不存在，不能删除");
	
		// 删除操作
		t.setUpdateBy(session.getAttribute("lmisUserId").toString());
		return new LmisResult<T>(LmisConstant.RESULT_CODE_SUCCESS, setupConstantSqlMapper.logicalDelete(t));
	}

	/**
	 * 根据传入的 SQL 字符串检测该字符串是否是 Select 语句，并且检查不存在 SQL 注入问题
	 */
	@Override
	public LmisResult<?> checkValidSQL(String sql) throws Exception {
        LmisResult<Map<String, Object>> lmisResult = new LmisResult<Map<String, Object>>();
		
		if (ObjectUtils.isNull(sql)) {
			lmisResult.setCode(LmisConstant.RESULT_CODE_SQL_ERROR);
			lmisResult.setMessage("SQL 不能为空!");
			return lmisResult;
		}
		// 去除空格和去除掉 SQL 单引号和双引号中的内容为空
		sql = sql.trim().toUpperCase().replaceAll("'(.*)'", "''").replaceAll("\"(.*)\"", "''");
		if (!sql.startsWith("SELECT")) {
			lmisResult.setCode(LmisConstant.RESULT_CODE_SQL_ERROR);
			lmisResult.setMessage("SQL 语句只能为 SELECT 语句");
			return lmisResult;
		}
		if (!SqlUtils.checkSqlSecurity(sql)) {
			lmisResult.setCode(LmisConstant.RESULT_CODE_SQL_ERROR);
			lmisResult.setMessage("SQL 查询语句包含操作关键字");
			return lmisResult;
		}
		lmisResult.setCode(LmisConstant.RESULT_CODE_SUCCESS);
		return lmisResult;
	}
}
