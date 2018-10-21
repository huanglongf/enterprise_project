package com.lmis.pos.skuBase.service.impl;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lmis.common.dynamicSql.service.DynamicSqlServiceInterface;
import com.lmis.framework.baseInfo.LmisConstant;
import com.lmis.framework.baseInfo.LmisPageObject;
import com.lmis.framework.baseInfo.LmisResult;
import com.lmis.pos.skuBase.dao.PosSkuBaseMapper;
import com.lmis.pos.skuBase.model.PosSkuBase;
import com.lmis.pos.skuBase.service.PosSkuBaseServiceInterface;

/** 
 * @ClassName: PosSkuBaseServiceImpl
 * @Description: TODO(业务层实现类)
 * @author codeGenerator
 * @date 2018-06-11 16:43:53
 * 
 * @param <T>
 */
@Transactional(rollbackFor=Exception.class)
@Service
public class PosSkuBaseServiceImpl implements PosSkuBaseServiceInterface{
	
	@Resource(name="dynamicSqlServiceImpl")
	DynamicSqlServiceInterface<PosSkuBase> dynamicSqlService;
	@Autowired
	private PosSkuBaseMapper posSkuBaseMapper;

	@SuppressWarnings("rawtypes")
    @Override
	public LmisResult<PosSkuBase> executeSelect(PosSkuBase posSkuBase, LmisPageObject pageObject) throws Exception {
	    Page page = PageHelper.startPage(pageObject.getPageNum(),pageObject.getPageSize());
	    posSkuBaseMapper.executeSelect(posSkuBase);
        return new LmisResult<PosSkuBase>(LmisConstant.RESULT_CODE_SUCCESS,page.toPageInfo());
	}

}
