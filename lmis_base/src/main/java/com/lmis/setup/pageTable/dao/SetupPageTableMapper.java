package com.lmis.setup.pageTable.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.lmis.framework.baseDao.BaseMapper;
import com.lmis.setup.pageTable.model.SetupPageTable;
import com.lmis.setup.pageTable.model.ViewSetupPageTable;

/** 
 * @ClassName: SetupPageTableMapper
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Ian.Huang
 * @date 2017年12月8日 下午5:37:51 
 * 
 * @param <T>
 */
@Mapper
@Repository
public interface SetupPageTableMapper<T extends SetupPageTable> extends BaseMapper<T> {
	
	/**
	 * @Title: listSetupPageElementBySeq
	 * @Description: TODO(按排序查询)
	 * @param viewSetupPageTable
	 * @return: List<ViewSetupPageTable>
	 * @author: Ian.Huang
	 * @date: 2017年12月13日 下午3:15:55
	 */
	List<ViewSetupPageTable> listSetupPageTableBySeq(ViewSetupPageTable viewSetupPageTable);
	
	/**
	 * @Title: listValidateSetupPageTable
	 * @Description: TODO(获取有效查询结果字段)
	 * @param viewSetupPageTable
	 * @return: List<ViewSetupPageTable>
	 * @author: Ian.Huang
	 * @date: 2017年12月27日 下午6:34:43
	 */
	List<ViewSetupPageTable> listValidateSetupPageTable(ViewSetupPageTable viewSetupPageTable);
	
	/**
	 * @Title: listAllValidateLayoutId
	 * @Description: TODO(获取当前表中所有有效布局ID)
	 * @return: List<String>
	 * @author: Ian.Huang
	 * @date: 2018年1月9日 下午2:42:06
	 */
	List<String> listAllValidateLayoutId();
	
	/**
	 * @Title: listSetupPageElementBySeq
	 * @Description: TODO(按排序查询)
	 * @param viewSetupPageTable
	 * @return: List<ViewSetupPageTable>
	 * @author: Ian.Huang
	 * @date: 2017年12月13日 下午3:15:55
	 */
	List<ViewSetupPageTable> listSetupPageTableSimpleBySeq(ViewSetupPageTable viewSetupPageTable);
	/**
	 * 根据 layoutId 集合批量逻辑删除查询列表
	 * @param layoutIdList
	 * @return
	 */
	Integer updateBatch(List<String> layoutIdList);
	/**
	 * 根据 layoutIdList 批量获取查询结果
	 * @param layoutIdList
	 * @return
	 */
	List<ViewSetupPageTable> listValidateSetupPageTableByLayoutIds(List<String> layoutIdList);
}
