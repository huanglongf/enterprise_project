package com.lmis.pos.whsRateLoadin.service.impl;

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
import com.lmis.pos.whsRateLoadin.dao.PosWhsRateLoadinMapper;
import com.lmis.pos.whsRateLoadin.model.PosWhsRateLoadin;
import com.lmis.pos.whsRateLoadin.model.PosWhsRateLoadinProportion;
import com.lmis.pos.whsRateLoadin.service.PosWhsRateLoadinServiceInterface;

/** 
 * @ClassName: PosWhsRateLoadinServiceImpl
 * @Description: TODO(新货商品分配仓库比例分析业务层实现类)
 * @author codeGenerator
 * @date 2018-05-30 17:12:37
 * 
 * @param <T>
 */
@Transactional(rollbackFor=Exception.class)
@Service
public class PosWhsRateLoadinServiceImpl<T extends PosWhsRateLoadin> implements PosWhsRateLoadinServiceInterface<T> {
	
	@Resource(name="dynamicSqlServiceImpl")
	DynamicSqlServiceInterface<PosWhsRateLoadin> dynamicSqlService;
	@Autowired
	private PosWhsRateLoadinMapper<T> posWhsRateLoadinMapper;

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
	public LmisResult<?> deletePosWhsRateLoadin(T t) throws Exception {
			
		// TODO(业务校验)
		
		// 删除操作
		return new LmisResult<T>(LmisConstant.RESULT_CODE_SUCCESS, posWhsRateLoadinMapper.logicalDelete(t));
	}
	

    @SuppressWarnings("rawtypes")
    @Override
    public LmisResult<PosWhsRateLoadinProportion> selectPosWhsRateLoadinProportion(PosWhsRateLoadinProportion posWhsRateLoadinProportion, LmisPageObject pageObject){
        Page page = PageHelper.startPage(pageObject.getPageNum(),pageObject.getPageSize());
        posWhsRateLoadinMapper.selectPosWhsRateLoadinProportion(posWhsRateLoadinProportion);
        return new LmisResult<PosWhsRateLoadinProportion>(LmisConstant.RESULT_CODE_SUCCESS,page.toPageInfo());
    }

}
