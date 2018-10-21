package com.lmis.basis.currency.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

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
import com.lmis.basis.currency.dao.BasisCurrencyMapper;
import com.lmis.basis.currency.model.BasisCurrency;
import com.lmis.basis.currency.service.BasisCurrencyServiceInterface;

/** 
 * @ClassName: BasisCurrencyServiceImpl
 * @Description: TODO(货币业务层实现类)
 * @author codeGenerator
 * @date 2018-01-18 14:46:24
 * 
 * @param <T>
 */
@Transactional(rollbackFor=Exception.class)
@Service
public class BasisCurrencyServiceImpl<T extends BasisCurrency> implements BasisCurrencyServiceInterface<T> {
	
	@Resource(name="dynamicSqlServiceImpl")
	DynamicSqlServiceInterface<BasisCurrency> dynamicSqlService;
	@Autowired
	private BasisCurrencyMapper<T> basisCurrencyMapper;
	
	@Autowired
	private HttpSession session;
	
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

	@SuppressWarnings("unchecked")
	@Override
	public LmisResult<?> executeInsert(DynamicSqlParam<T> dynamicSqlParam) throws Exception {
		
		BasisCurrency currency = dynamicSqlService.generateTableModel((DynamicSqlParam<BasisCurrency>) dynamicSqlParam, new BasisCurrency()).getTableModel();
		//非空校验
		if(ObjectUtils.isNull(currency.getCurrencyCode())) throw new Exception(LBASEConstant.EBASE000022);
		//唯一校验
		BasisCurrency checkCurr=new BasisCurrency();
		checkCurr.setIsDeleted(false);
		checkCurr.setCurrencyCode(currency.getCurrencyCode());
		if(basisCurrencyMapper.retrieve((T) checkCurr).size()>0) throw new Exception(LBASEConstant.EBASE000013);
		
		//主要货币唯一校验
		if(currency.getIsMajor()) {
			BasisCurrency checkCurrency=new BasisCurrency();
			checkCurrency.setIsDeleted(false);
			checkCurrency.setIsMajor(true);
			List<T> currList = basisCurrencyMapper.retrieve((T) checkCurrency);
			if(currList.size()>0) throw new Exception(LBASEConstant.EBASE000034);
		}
		
		// 插入操作
		
		//创建人
		dynamicSqlParam.setCreateBy(session.getAttribute("lmisUserId").toString());
		//更新人
		dynamicSqlParam.setUpdateBy(session.getAttribute("lmisUserId").toString());
		//所属机构
		dynamicSqlParam.setPwrOrg(session.getAttribute("lmisUserOrg").toString());	
		
		return dynamicSqlService.executeInsert(dynamicSqlParam);
	}

	@SuppressWarnings("unchecked")
	@Override
	public LmisResult<?> executeUpdate(DynamicSqlParam<T> dynamicSqlParam) throws Exception {
			
		// TODO(业务校验)
		//存在校验
		BasisCurrency currency = dynamicSqlService.generateTableModel((DynamicSqlParam<BasisCurrency>) dynamicSqlParam, new BasisCurrency()).getTableModel();
		BasisCurrency checkCurr=new BasisCurrency();
		checkCurr.setIsDeleted(false);
		checkCurr.setCurrencyCode(currency.getCurrencyCode());
		List<T> currencyList = basisCurrencyMapper.retrieve((T) checkCurr);
		if(currencyList.size()==0) throw new Exception(LBASEConstant.EBASE000001);
		
		if(!currencyList.get(0).getIsMajor()) {
			//主要货币唯一校验
			BasisCurrency checkCurrency=new BasisCurrency();
			checkCurrency.setIsDeleted(false);
			checkCurrency.setIsMajor(true);
			List<T> currList = basisCurrencyMapper.retrieve((T) checkCurrency);
			if(currList.size()>0&&currency.getIsMajor()) throw new Exception(LBASEConstant.EBASE000035);
		}
		//更新人
		dynamicSqlParam.setUpdateBy(session.getAttribute("lmisUserId").toString());
		// 更新操作
		return dynamicSqlService.executeUpdate(dynamicSqlParam);
	}

	@SuppressWarnings("unchecked")
	@Override
	public LmisResult<?> deleteBasisCurrency(T t) throws Exception {
			
		// TODO(业务校验)
		//存在校验
		BasisCurrency checkCurr=new BasisCurrency();
		checkCurr.setIsDeleted(false);
		checkCurr.setCurrencyCode(t.getCurrencyCode());
		
		List<T> currList = basisCurrencyMapper.retrieve((T) checkCurr);
		if(currList.size()==0) throw new Exception(LBASEConstant.EBASE000001);
		if(currList.get(0).getIsMajor()) throw new Exception(LBASEConstant.EBASE000036);
		
		//更新人
		t.setUpdateBy(session.getAttribute("lmisUserId").toString());
		// 删除操作
		return new LmisResult<T>(LmisConstant.RESULT_CODE_SUCCESS, basisCurrencyMapper.logicalDelete(t));
	}

	@SuppressWarnings("unchecked")
	@Override
	public LmisResult<?> switchBasisCurrency(T t) throws Exception {
		//存在校验
		BasisCurrency checkCurr=new BasisCurrency();
		checkCurr.setIsDeleted(false);
		checkCurr.setCurrencyCode(t.getCurrencyCode());
		
		List<T> currList = basisCurrencyMapper.retrieve((T) checkCurr);
		
		if(currList.size()==0) throw new Exception(LBASEConstant.EBASE000001);
		if(t.getIsDisabled()&&currList.get(0).getIsMajor()) throw new Exception(LBASEConstant.EBASE000037);
		//更新人
		t.setUpdateBy(session.getAttribute("lmisUserId").toString());
		
		return new LmisResult<T>(LmisConstant.RESULT_CODE_SUCCESS, basisCurrencyMapper.shiftValidity(t));
	}

}
