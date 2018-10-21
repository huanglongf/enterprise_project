package com.lmis.pos.addrLib.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lmis.framework.baseInfo.LmisConstant;
import com.lmis.framework.baseInfo.LmisPageObject;
import com.lmis.framework.baseInfo.LmisResult;
import com.lmis.pos.addrLib.dao.PosAddrLibMapper;
import com.lmis.pos.addrLib.model.PosAddrLib;
import com.lmis.pos.addrLib.service.PosAddrLibServiceInterface;

/** 
 * @ClassName: PosAddrLibServiceImpl
 * @Description: TODO(地址库表（基础数据）业务层实现类)
 * @author codeGenerator
 * @date 2018-05-29 10:46:34
 * 
 * @param <T>
 */
@Transactional(rollbackFor=Exception.class)
@Service
public class PosAddrLibServiceImpl<T extends PosAddrLib> implements PosAddrLibServiceInterface<T> {
	
	@Autowired
	private PosAddrLibMapper<T> posAddrLibMapper;

	@Override
	public LmisResult<?> executeSelect(T t, LmisPageObject pageObject) throws Exception {
		return null;
	}

	@Override
	public LmisResult<?> executeSelect(T t) throws Exception {
		return null;
	}

	@Override
	public LmisResult<?> executeInsert(T t) throws Exception {
			
		// TODO(业务校验)
		
		// 插入操作
		return null;
	}

	@Override
	public LmisResult<?> executeUpdate(T t) throws Exception {
			
		// TODO(业务校验)
		
		// 更新操作
		return null;
	}

	@Override
	public LmisResult<?> deletePosAddrLib(T t) throws Exception {
			
		// TODO(业务校验)
		
		// 删除操作
		return new LmisResult<T> (LmisConstant.RESULT_CODE_SUCCESS, posAddrLibMapper.logicalDelete(t));
	}

}
