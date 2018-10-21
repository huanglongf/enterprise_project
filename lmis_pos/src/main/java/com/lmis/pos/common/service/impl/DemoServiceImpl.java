package com.lmis.pos.common.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lmis.framework.baseInfo.LmisPageObject;
import com.lmis.framework.baseModel.PersistentObject;
import com.lmis.pos.common.dao.DemoMapper;
import com.lmis.pos.common.service.DemoService;

/** 
 * @ClassName: DemoServiceImpl
 * @Description: TODO(DEMO)
 * @author Ian.Huang
 * @date 2017年10月23日 下午12:08:37 
 * 
 */
@Transactional(rollbackFor=Exception.class)
@Service
public class DemoServiceImpl<T extends PersistentObject> implements DemoService<T> {

	//	private final static Logger logger=LoggerFactory.getLogger(DemoServiceImpl.class);
	
	/** 
	 * @Fields demoMapper : TODO(demo) 
	 */
	@Autowired
	private DemoMapper<T> demoMapper;
	
	@Override
	public PageInfo<T> queryPage(T t, int pageNum, int pageSize) {
		Page<T> page = PageHelper.startPage(pageNum, pageSize);
        // PageHelper会自动拦截到下面这查询sql
		demoMapper.retrieve(t);
        return page.toPageInfo();
	}

	@Override
	public PageInfo<T> queryPage1(T t, LmisPageObject lmisPage) {
		Page<T> page = PageHelper.startPage(lmisPage.getPageNum(), lmisPage.getPageSize());
        // PageHelper会自动拦截到下面这查询sql
		demoMapper.retrieve(t);
        return page.toPageInfo();
	}

}
