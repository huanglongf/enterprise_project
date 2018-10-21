package com.lmis.setup.page.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.lmis.framework.baseDao.BaseMapper;
import com.lmis.setup.page.model.SetupPage;
import com.lmis.setup.page.model.ViewSetupPage;

/** 
 * @ClassName: SetupPageMapper
 * @Description: TODO(配置页面DAO)
 * @author Ian.Huang
 * @date 2017年12月28日 下午3:49:48 
 * 
 * @param <T>
 */
@Mapper
@Repository
public interface SetupPageMapper<T extends SetupPage> extends BaseMapper<T> {

	/**
	 * @Title: retrieveViewSetupPage
	 * @Description: TODO(视图view_setup_page查询方法)
	 * @param viewSetupPage
	 * @return: List<ViewSetupPage>
	 * @author: Ian.Huang
	 * @date: 2018年1月3日 下午5:57:55
	 */
	List<ViewSetupPage> retrieveViewSetupPage(ViewSetupPage viewSetupPage);
	
}
