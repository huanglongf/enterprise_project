package com.lmis.pos.common.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lmis.common.dataFormat.ObjectUtils;
import com.lmis.common.dynamicSql.model.DynamicSqlParam;
import com.lmis.common.dynamicSql.service.DynamicSqlServiceInterface;
import com.lmis.framework.baseInfo.LmisConstant;
import com.lmis.framework.baseInfo.LmisPageObject;
import com.lmis.framework.baseInfo.LmisResult;
import com.lmis.pos.common.dao.PosPoImportLogMapper;
import com.lmis.pos.common.model.PosPoImportLog;
import com.lmis.pos.common.service.PosPoImportLogService;

/** 
 * @ClassName: PosPoImportLogServiceImpl
 * @Description: TODO(Po单导入统计日志表业务层实现类)
 * @author codeGenerator
 * @date 2018-06-06 17:08:44
 * 
 * @param <T>
 */
@Transactional(rollbackFor=Exception.class)
@Service
public class PosPoImportLogServiceImpl<T extends PosPoImportLog> implements PosPoImportLogService<T> {
	
	@Resource(name="dynamicSqlServiceImpl")
	DynamicSqlServiceInterface<PosPoImportLog> dynamicSqlService;
	@Autowired
	private PosPoImportLogMapper<T> posPoImportLogMapper;

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

	@Override
	public LmisResult<?> executeInsert(DynamicSqlParam<T> dynamicSqlParam) throws Exception {
			
		// TODO(业务校验)
		
		// 插入操作
		return dynamicSqlService.executeInsert(dynamicSqlParam);
	}

	@Override
	public LmisResult<?> executeUpdate(DynamicSqlParam<T> dynamicSqlParam) throws Exception {
			
		// TODO(业务校验)
		
		// 更新操作
		return dynamicSqlService.executeUpdate(dynamicSqlParam);
	}

	@Override
	public LmisResult<?> deletePosPoImportLog(T t) throws Exception {
			
		// TODO(业务校验)
		
		// 删除操作
		return new LmisResult(LmisConstant.RESULT_CODE_SUCCESS, posPoImportLogMapper.logicalDelete(t));
	}

	@Override
	public LmisResult<?> selectPosPoImportLog(T t, LmisPageObject pageObject) {
        LmisResult<T> lmisResult = new LmisResult<>(LmisConstant.RESULT_CODE_ERROR,"");
        Page<T> page = PageHelper.startPage(pageObject.getPageNum(), pageObject.getPageSize());
        if(t == null) {
        	lmisResult.setMessage("未接收到正确的参数，请确认参数是否正确");
        	return lmisResult;
        }
        t.setIsDeleted(false);
        posPoImportLogMapper.retrieveByGroupFileNo(t);
        lmisResult.setMetaAndData(page.toPageInfo());
        lmisResult.setCode(LmisConstant.RESULT_CODE_SUCCESS);
        return lmisResult;
	}

}
