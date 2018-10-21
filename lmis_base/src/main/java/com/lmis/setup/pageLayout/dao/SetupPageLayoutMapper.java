package com.lmis.setup.pageLayout.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.lmis.framework.baseDao.BaseMapper;
import com.lmis.setup.pageLayout.model.SetupPageLayout;
import com.lmis.setup.pageLayout.model.ViewSetupPageLayout;

/** 
 * @ClassName: SetupPageLayoutMapper
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Ian.Huang
 * @date 2017年12月5日 下午8:08:47 
 * 
 * @param <T>
 */
@Mapper
@Repository
public interface SetupPageLayoutMapper<T extends SetupPageLayout> extends BaseMapper<T> {

	/**
	 * @Title: listSetupPageLayoutBySeq
	 * @Description: TODO(按排序查询)
	 * @param viewSetupPageLayout
	 * @return: List<ViewSetupPageLayout>
	 * @author: Ian.Huang
	 * @date: 2017年12月13日 下午3:13:28
	 */
	List<ViewSetupPageLayout> listSetupPageLayoutBySeq(ViewSetupPageLayout viewSetupPageLayout);
	
	/**
	 * @Title: listSetupPageLayoutBySeqSimple
	 * @Description: TODO(按排序查询)
	 * @param viewSetupPageLayout
	 * @return: List<ViewSetupPageLayout>
	 * @author: Ian.Huang
	 * @date: 2017年12月13日 下午3:13:28
	 */
	List<ViewSetupPageLayout> listSetupPageLayoutSimpleBySeq(ViewSetupPageLayout viewSetupPageLayout);
	
	/**
	 * @Title: updateBatch
	 * @Description: TODO(根据 layoutId 集合批量更新 SetupPageLayout)
	 * @param layoutIdList
	 * @return: Integer
	 * @author: wenhui.bai
	 * @date: 2018年6月4日 下午17:37:00
	 */
	Integer updateBatch(List<String> layoutIdList);
}
