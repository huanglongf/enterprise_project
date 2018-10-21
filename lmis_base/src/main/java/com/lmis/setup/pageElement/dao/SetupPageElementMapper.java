package com.lmis.setup.pageElement.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.lmis.framework.baseDao.BaseMapper;
import com.lmis.setup.pageElement.model.SetupPageElement;
import com.lmis.setup.pageElement.model.ViewSetupPageElement;

/** 
 * @ClassName: SetupPageElementMapper
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Ian.Huang
 * @date 2017年12月6日 下午10:15:44 
 * 
 * @param <T>
 */
@Mapper
@Repository
public interface SetupPageElementMapper<T extends SetupPageElement> extends BaseMapper<T> {

	/**
	 * @Title: listSetupPageElementBySeq
	 * @Description: TODO(按排序查询)
	 * @param viewSetupPageElement
	 * @return: List<ViewSetupPageElement>
	 * @author: Ian.Huang
	 * @date: 2017年12月13日 下午3:15:55
	 */
	List<ViewSetupPageElement> listSetupPageElementBySeq(ViewSetupPageElement viewSetupPageElement);
	
	/**
	 * @Title: listAllValidateLayoutId
	 * @Description: TODO(获取当前表中所有有效布局ID)
	 * @return: List<String>
	 * @author: Ian.Huang
	 * @date: 2018年1月9日 下午2:42:06
	 */
	List<String> listAllValidateLayoutId();
	
	List<SetupPageElement> findPageElementByPageId(@Param("pageId") String pageId);
	
	/**
	 * @Title: listSetupPageElementSimpleBySeq
	 * @Description: TODO(按排序查询)
	 * @param viewSetupPageElement
	 * @return: List<ViewSetupPageElement>
	 * @author: wenhui.bai
	 * @date: 2018年5月31日 早上 11:07:00
	 */
	List<ViewSetupPageElement> listSetupPageElementSimpleBySeq(ViewSetupPageElement viewSetupPageElement);
	/**
	 * 根据 layoutId 集合批量逻辑删除元素
	 * @param layoutIdList
	 * @return
	 */
	Integer updateBatch(List<String> layoutIdList);
}
