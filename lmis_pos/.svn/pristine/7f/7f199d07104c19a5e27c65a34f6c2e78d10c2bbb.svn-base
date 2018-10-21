package com.lmis.pos.common.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
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
import com.lmis.pos.common.dao.PosPurchaseOrderMainMapper;
import com.lmis.pos.common.model.PosPurchaseOrderMain;
import com.lmis.pos.common.service.PosPurchaseOrderMainService;

/** 
 * @ClassName: PosPurchaseOrderMainServiceImpl
 * @Description: TODO(业务层实现类)
 * @author codeGenerator
 * @date 2018-05-30 13:41:07
 * 
 * @param <T>
 */
@Transactional(rollbackFor=Exception.class)
@Service
public class PosPurchaseOrderMainServiceImpl<T extends PosPurchaseOrderMain> implements PosPurchaseOrderMainService<T> {
	
	@Resource(name="dynamicSqlServiceImpl")
	DynamicSqlServiceInterface<PosPurchaseOrderMain> dynamicSqlService;
	
	@Autowired
	private PosPurchaseOrderMainMapper<T> posPurchaseOrderMainMapper;

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
	public LmisResult<?> deletePosPurchaseOrderMain(T t) throws Exception {
        LmisResult<T> lmisResult = new LmisResult<>(LmisConstant.RESULT_CODE_ERROR,"");
        
        if(t == null || StringUtils.isBlank(t.getId())) {
			lmisResult.setMessage("所传条件不能为空");
			return lmisResult;
        }
			
		// TODO(业务校验)
        t.setIsDeleted(false);
		List<T> dataList = posPurchaseOrderMainMapper.retrieve(t);
		if(CollectionUtils.isEmpty(dataList)) {
			lmisResult.setMessage("未查找到数据");
			return lmisResult;
		}
		T dataResult = dataList.get(0);
		if("1".equals(dataResult.getPoFlag())) {
			lmisResult.setMessage("已分仓Po不允许删除");
			return lmisResult;
		}
		
		int r = posPurchaseOrderMainMapper.logicalDelete(t);
		// 删除操作
		lmisResult.setCode(LmisConstant.RESULT_CODE_SUCCESS);
		lmisResult.setData(r);
		return lmisResult;
	}

	@Override
	public LmisResult<?> selectPosPurchaseOrderMain(T t, LmisPageObject pageObject) {
        LmisResult<T> lmisResult = new LmisResult<>(LmisConstant.RESULT_CODE_ERROR,"");
        Page<T> page = PageHelper.startPage(pageObject.getPageNum(), pageObject.getPageSize());
        if(t == null) {
        	lmisResult.setMessage("未接收到正确的参数，请确认参数是否正确");
        	return lmisResult;
        }
        t.setIsDeleted(false);
        posPurchaseOrderMainMapper.retrieve(t);
        lmisResult.setMetaAndData(page.toPageInfo());
        lmisResult.setCode(LmisConstant.RESULT_CODE_SUCCESS);
        return lmisResult;
	}

	@Override
	public LmisResult<?> checkPosPurchaseOrderMain(T t) {
        LmisResult<T> lmisResult = new LmisResult<>(LmisConstant.RESULT_CODE_ERROR,"");
        if(!(t != null && StringUtils.isNotBlank(t.getId()))) {
        	lmisResult.setMessage("未接收到正确的主键参数，请确认参数是否正确");
        	return lmisResult;
        }
        t.setIsDeleted(false);
        List<T> resultList = posPurchaseOrderMainMapper.retrieve(t);
        if(CollectionUtils.isEmpty(resultList)) {
        	lmisResult.setMessage("未查找到记录请确认条件是否正确");
        	return lmisResult;
        }
        if(resultList.size()>1) {
        	lmisResult.setMessage("未查找到多条记录，请确认参数是否正确");
        	return lmisResult;
        }
        lmisResult.setCode(LmisConstant.RESULT_CODE_SUCCESS);
        lmisResult.setData(resultList.get(0));
		return lmisResult;
	}

	@Override
	public LmisResult<?> updatePosPurchaseOrderMain(T t) {
		LmisResult<T> lmisResult = new LmisResult<T>();
		posPurchaseOrderMainMapper.update(t);
		lmisResult.setCode(LmisConstant.RESULT_CODE_SUCCESS);
		return lmisResult;
	}

	@Override
	public List<T> retrieve(T t) {
		return posPurchaseOrderMainMapper.retrieve(t);
	}

	@Override
	public int update(T t) {
		return posPurchaseOrderMainMapper.update(t);
	}

}
