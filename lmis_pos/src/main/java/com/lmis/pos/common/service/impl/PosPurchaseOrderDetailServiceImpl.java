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
import com.lmis.pos.common.dao.PosPurchaseOrderDetailMapper;
import com.lmis.pos.common.model.PosPurchaseOrderDetail;
import com.lmis.pos.common.service.PosPurchaseOrderDetailService;

/** 
 * @ClassName: PosPurchaseOrderDetailServiceImpl
 * @Description: TODO(NIKE-PO单主表业务层实现类)
 * @author codeGenerator
 * @date 2018-05-30 16:28:41
 * 
 * @param <T>
 */
@Transactional(rollbackFor=Exception.class)
@Service
public class PosPurchaseOrderDetailServiceImpl<T extends PosPurchaseOrderDetail> implements PosPurchaseOrderDetailService<T> {
	
	@Resource(name="dynamicSqlServiceImpl")
	DynamicSqlServiceInterface<PosPurchaseOrderDetail> dynamicSqlService;
	@Autowired
	private PosPurchaseOrderDetailMapper<T> posPurchaseOrderDetailMapper;

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
	public LmisResult<?> deletePosPurchaseOrderDetail(T t) throws Exception {
			
		// TODO(业务校验)
		
		// 删除操作
		return new LmisResult<T> (LmisConstant.RESULT_CODE_SUCCESS, posPurchaseOrderDetailMapper.logicalDelete(t));
	}

	@Override
	public LmisResult<?> selectPosPurchaseOrderDetail(T t, LmisPageObject pageObject) {
        LmisResult<T> lmisResult = new LmisResult<>(LmisConstant.RESULT_CODE_ERROR,"");
        Page<T> page = PageHelper.startPage(pageObject.getPageNum(), pageObject.getPageSize());
        if(t == null) {
        	lmisResult.setMessage("未接收到正确的参数，请确认参数是否正确");
        	return lmisResult;
        }
        t.setIsDeleted(false);
        posPurchaseOrderDetailMapper.retrieve(t);
        lmisResult.setMetaAndData(page.toPageInfo());
        lmisResult.setCode(LmisConstant.RESULT_CODE_SUCCESS);
        return lmisResult;
	}

	@Override
	public List<T> retrieve(T t) {
		return posPurchaseOrderDetailMapper.retrieve(t);
	}

}
