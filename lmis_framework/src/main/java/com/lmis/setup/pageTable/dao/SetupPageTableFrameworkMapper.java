package com.lmis.setup.pageTable.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.lmis.framework.baseDao.BaseMapper;
import com.lmis.setup.pageTable.model.SetupPageTable;
import com.lmis.setup.pageTable.model.ViewSetupPageTable;

/** 
 * @ClassName: SetupPageTableFrameworkMapper
 * @Description: TODO(页面查询结果字段DAO映射接口-framework版)
 * @author Ian.Huang
 * @date 2018年1月8日 下午1:11:32 
 * 
 * @param <T>
 */
@Mapper
@Repository
public interface SetupPageTableFrameworkMapper<T extends SetupPageTable> extends BaseMapper<T> {
	
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
}
