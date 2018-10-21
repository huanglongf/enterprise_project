package com.lmis.pos.crdOut.service.impl;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lmis.framework.baseInfo.LmisConstant;
import com.lmis.framework.baseInfo.LmisResult;
import com.lmis.pos.common.util.StringUtil;
import com.lmis.pos.crdOut.dao.PosCrdOutMapper;
import com.lmis.pos.crdOut.model.PosCrdOut;
import com.lmis.pos.crdOut.service.PosCrdOutServiceInterface;

/** 
 * @ClassName: PosCrdOutServiceImpl
 * @Description: TODO(不拆分CRD设置业务层实现类)
 * @author codeGenerator
 * @date 2018-06-01 17:09:27
 * 
 * @param <T>
 */
@Transactional(rollbackFor=Exception.class)
@Service
public class PosCrdOutServiceImpl<T extends PosCrdOut> implements PosCrdOutServiceInterface<T> {
	
	@Autowired
	private PosCrdOutMapper<T> posCrdOutMapper;
	@Autowired
    private HttpSession session;
	
	
	@Override
	public LmisResult<?> executeSelect(T t) throws Exception {
		List<T> result = posCrdOutMapper.retrieve(t);
        return new LmisResult<T>(LmisConstant.RESULT_CODE_SUCCESS, result);
	}

	@Override
	public LmisResult<?> executeInsert(T t) throws Exception {
		//类型校验
	    if(!StringUtil.isNotEmpty(t.getCrdOut())){
            throw new Exception("日期不为空");
        }
	    List<T> oldResult = posCrdOutMapper.retrieveRepeatDate(t);
	    if(oldResult != null && oldResult.size() >0){
	    	if(t.getCrdOut().equals(oldResult.get(0).getCrdOut()))throw new Exception("不能添加相同日期！");
	    }
	    t.setCreateBy(session.getAttribute("lmisUserId").toString());
	    t.setPwrOrg(session.getAttribute("lmisUserOrg").toString());
	    LmisResult<T> result =  new LmisResult<>();
	    if(posCrdOutMapper.create(t)>0){
	        result.setCode(LmisConstant.RESULT_CODE_SUCCESS);
	    }else{
	        result.setCode(LmisConstant.RESULT_CODE_ERROR);
	    }
		return result;
	}

	@Override
	public LmisResult<?> deletePosCrdData(T t) throws Exception {
		// 删除操作
		 //更新人
        t.setUpdateBy(session.getAttribute("lmisUserId").toString());
		return new LmisResult<T> (LmisConstant.RESULT_CODE_SUCCESS, posCrdOutMapper.logicalDelete(t));
	}
	
}
