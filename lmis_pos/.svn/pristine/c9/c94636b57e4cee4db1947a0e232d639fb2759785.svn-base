package com.lmis.pos.soldtoRule.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lmis.framework.baseInfo.LmisConstant;
import com.lmis.framework.baseInfo.LmisResult;
import com.lmis.pos.soldtoRule.dao.PosSoldtoRuleMapper;
import com.lmis.pos.soldtoRule.model.PosSoldtoRule;
import com.lmis.pos.soldtoRule.service.PosSoldtoRuleServiceInterface;

/** 
 * @ClassName: PosCrdOutServiceImpl
 * @Description: TODO(PO-soldto业务层实现类)
 * @author codeGenerator
 * @date 2018-06-22 17:09:27
 * 
 * @param <T>
 */
@Transactional(rollbackFor=Exception.class)
@Service
public class PosSoldtoRuleServiceImpl<T extends PosSoldtoRule> implements PosSoldtoRuleServiceInterface<T> {
	
	@Autowired
	private PosSoldtoRuleMapper<T> posSoldtoRuleMapper;
	
	
	@Override
	public LmisResult<?> executeSelect() throws Exception {
		List<T> result = posSoldtoRuleMapper.findSpecifyData();
        return new LmisResult<T>(LmisConstant.RESULT_CODE_SUCCESS, result);
	}



	
}
