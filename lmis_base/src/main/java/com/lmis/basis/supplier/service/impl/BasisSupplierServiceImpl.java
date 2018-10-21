package com.lmis.basis.supplier.service.impl;

import com.lmis.basis.supplier.dao.BasisSupplierMapper;
import com.lmis.basis.supplier.model.BasisSupplier;
import com.lmis.basis.supplier.service.BasisSupplierServiceInterface;
import com.lmis.common.dataFormat.ObjectUtils;
import com.lmis.common.dynamicSql.model.DynamicSqlParam;
import com.lmis.common.dynamicSql.service.DynamicSqlServiceInterface;
import com.lmis.constant.LBASEConstant;
import com.lmis.framework.baseInfo.LmisConstant;
import com.lmis.framework.baseInfo.LmisPageObject;
import com.lmis.framework.baseInfo.LmisResult;
import com.lmis.setup.constant.dao.SetupConstantMapper;
import com.lmis.setup.constant.model.SetupConstant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;


/** 
 * @ClassName: BasisSupplierServiceImpl
 * @Description: TODO(供应商业务层实现类)
 * @author codeGenerator
 * @date 2018-03-12 14:22:46
 * 
 * @param <T>
 */
@Transactional(rollbackFor=Exception.class)
@Service
public class BasisSupplierServiceImpl<T extends BasisSupplier> implements BasisSupplierServiceInterface<T> {
	
	@Resource(name="dynamicSqlServiceImpl")
	DynamicSqlServiceInterface<BasisSupplier> dynamicSqlService;
	@Autowired
	private BasisSupplierMapper<T> basisSupplierMapper;
	
	@Autowired
	private SetupConstantMapper<SetupConstant> setupConstantMapper;

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

	@Override
	@SuppressWarnings("unchecked")
	public LmisResult<?> executeInsert(DynamicSqlParam<T> dynamicSqlParam) throws Exception {
			
		// TODO(业务校验)
		
        BasisSupplier basisSupplier = dynamicSqlService.generateTableModel((DynamicSqlParam<BasisSupplier>) dynamicSqlParam,new BasisSupplier()).getTableModel();
		
	    //非空校验
	    if(ObjectUtils.isNull(basisSupplier.getSupplierCode())) throw new Exception(LBASEConstant.EBASE000092);
        
        BasisSupplier supplier = new BasisSupplier();
		supplier.setIsDeleted(false);
		supplier.setSupplierCode(basisSupplier.getSupplierCode());
		if(basisSupplierMapper.retrieve((T) supplier).size()>0) throw  new Exception(LBASEConstant.EBASE000093);
		
		if(!ObjectUtils.isNull(basisSupplier.getSupplierType())) {
			SetupConstant param= new SetupConstant();
			param.setConstantCode(basisSupplier.getSupplierType());
			param.setIsDeleted(false);
			if(setupConstantMapper.retrieve(param).size()==0) throw new Exception(LBASEConstant.EBASE000094);
		}
		
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
	@SuppressWarnings("unchecked")
	public LmisResult<?> deleteBasisSupplier(T t) throws Exception {
			
		// TODO(业务校验)
		BasisSupplier basisSupplier = new BasisSupplier();
		basisSupplier.setIsDeleted(false);
		basisSupplier.setSupplierCode(t.getSupplierCode());
		if (basisSupplierMapper.retrieve((T) basisSupplier).size()==0) throw  new Exception(LBASEConstant.EBASE000095);
		//更新人
		t.setUpdateBy(session.getAttribute("lmisUserId").toString());
		// 删除操作
		return new LmisResult<T>(LmisConstant.RESULT_CODE_SUCCESS, basisSupplierMapper.logicalDelete(t));
	}

	@Override
	@SuppressWarnings("unchecked")
	public LmisResult<?> switchBasisSupplier(T t) throws Exception {
		BasisSupplier basisSupplier = new BasisSupplier();
		basisSupplier.setSupplierCode(t.getSupplierCode());
		if (basisSupplierMapper.retrieve((T) basisSupplier).size()==0) throw  new Exception(LBASEConstant.EBASE000095);
		//更新人
		t.setUpdateBy(session.getAttribute("lmisUserId").toString());
		return new LmisResult<T>(LmisConstant.RESULT_CODE_SUCCESS,basisSupplierMapper.shiftValidity(t));
	}

}
