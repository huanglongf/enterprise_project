package com.lmis.basis.fixedAssetsAmt.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lmis.common.dataFormat.ObjectUtils;
import com.lmis.common.dynamicSql.model.DynamicSqlParam;
import com.lmis.common.dynamicSql.service.DynamicSqlServiceInterface;
import com.lmis.constant.LBASEConstant;
import com.lmis.framework.baseInfo.LmisConstant;
import com.lmis.framework.baseInfo.LmisPageObject;
import com.lmis.framework.baseInfo.LmisResult;
import com.lmis.basis.fixedAssetsAmt.dao.BasisFixedAssetsAmtMapper;
import com.lmis.basis.fixedAssetsAmt.model.BasisFixedAssetsAmt;
import com.lmis.basis.fixedAssetsAmt.service.BasisFixedAssetsAmtServiceInterface;

/** 
 * @ClassName: BasisFixedAssetsAmtServiceImpl
 * @Description: TODO(固定资产摊销业务层实现类)
 * @author codeGenerator
 * @date 2018-02-26 16:49:11
 * 
 * @param <T>
 */
@Transactional(rollbackFor=Exception.class)
@Service
public class BasisFixedAssetsAmtServiceImpl<T extends BasisFixedAssetsAmt> implements BasisFixedAssetsAmtServiceInterface<T> {
	
	@Resource(name="dynamicSqlServiceImpl")
	DynamicSqlServiceInterface<BasisFixedAssetsAmt> dynamicSqlService;
	@Autowired
	private BasisFixedAssetsAmtMapper<T> basisFixedAssetsAmtMapper;

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
		if(ObjectUtils.isNull(result)) throw new Exception(LBASEConstant.EBASE000007);
		if(result.size() != 1) throw new Exception(LBASEConstant.EBASE000008);
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
	public LmisResult<?> deleteBasisFixedAssetsAmt(T t) throws Exception {
			
		// TODO(业务校验)
		
		// 删除操作
		return new LmisResult<T>(LmisConstant.RESULT_CODE_SUCCESS, basisFixedAssetsAmtMapper.logicalDelete(t));
	}

	@Override
	public LmisResult<?> findAllAmtOrg(String assetsCode) throws Exception {

		return new LmisResult<T>(LmisConstant.RESULT_CODE_SUCCESS, basisFixedAssetsAmtMapper.findAllAmtOrg(assetsCode));
	}

}
