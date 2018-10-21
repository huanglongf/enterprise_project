package com.lmis.setup.pageElement.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.lmis.framework.baseDao.BaseMapper;
import com.lmis.setup.pageElement.model.SetupPageElement;
import com.lmis.setup.pageElement.model.ViewSetupPageElement;

/** 
 * @ClassName: SetupPageElementFrameworkMapper
 * @Description: TODO(页面元素DAO映射接口-framework版)
 * @author Ian.Huang
 * @date 2018年1月8日 下午1:12:20 
 * 
 * @param <T>
 */
@Mapper
@Repository
public interface SetupPageElementFrameworkMapper<T extends SetupPageElement> extends BaseMapper<T> {

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
	 * @Title: listValidateSetupPageElement
	 * @Description: TODO(查询有效的页面元素及其对应字段信息)
	 * @param viewSetupPageElement
	 * @return: List<ViewSetupPageElement>
	 * @author: Ian.Huang
	 * @date: 2017年12月27日 下午6:41:40
	 */
	List<ViewSetupPageElement> listValidateSetupPageElement(ViewSetupPageElement viewSetupPageElement);
	/**
	 * 功能同 listValidateSetupPageElement 一样，通过简单视图加快查询速度
	 * @param viewSetupPageElement
	 * @return
	 */
	List<ViewSetupPageElement> listValidateSetupPageElementForSimple(ViewSetupPageElement viewSetupPageElement);
}
