package com.lmis.pos.common.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageInfo;
import com.lmis.framework.baseInfo.LmisPageObject;
import com.lmis.framework.baseModel.PersistentObject;

/** 
 * @ClassName: DemoService
 * @Description: TODO(DEMO)
 * @author Ian.Huang
 * @date 2017年10月23日 下午12:07:45 
 * 
 * @param <T>
 */
@Transactional(rollbackFor=Exception.class)
@Service
public interface DemoService<T extends PersistentObject> {

	PageInfo<T> queryPage(T t, int pageNum, int pageSize);
	
	PageInfo<T> queryPage1(T t, LmisPageObject page);
}
