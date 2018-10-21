package com.lmis.pos.whsRateFillin.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lmis.framework.baseInfo.LmisConstant;
import com.lmis.framework.baseInfo.LmisPageObject;
import com.lmis.framework.baseInfo.LmisResult;
import com.lmis.pos.whsRateFillin.dao.PosWhsRecomRateMapper;
import com.lmis.pos.whsRateFillin.model.PosWhsRecomRate;
import com.lmis.pos.whsRateFillin.service.PosWhsRecomRateServiceInterface;

/**
 * @ClassName: PosWhsServiceImpl
 * @Description: TODO(仓库主表业务层实现类)
 * @author codeGenerator
 * @date 2018-05-29 10:13:18
 * 
 * @param <T>
 */
@Service
public class PosWhsRecomRateServiceImpl<T extends PosWhsRecomRate> implements PosWhsRecomRateServiceInterface<T> {

	@Autowired
	private PosWhsRecomRateMapper<T> recomRateMapper;

	@Override
	public LmisResult<?> executeSelect(T t, LmisPageObject pageObject) throws Exception {
		Page<Map<String, Object>> page = PageHelper.startPage(pageObject.getPageNum(), pageObject.getPageSize());
		recomRateMapper.query(t);
		LmisResult<Map<String, Object>> lmisResult = new LmisResult<Map<String, Object>>();
		lmisResult.setMetaAndData(page.toPageInfo());
		lmisResult.setCode(LmisConstant.RESULT_CODE_SUCCESS);
		return lmisResult;
	}



}
