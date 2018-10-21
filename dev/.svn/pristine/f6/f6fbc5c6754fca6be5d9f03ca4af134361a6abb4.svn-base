package com.bt.lmis.balance.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bt.lmis.balance.model.ExBillTask;

/** 
 * @ClassName: ExBillTaskMapper
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Ian.Huang
 * @date 2017年11月25日 下午2:46:38 
 * 
 * @param <T>
 */
public interface ExBillTaskMapper<T> {
	
	/**
	 * @Title: getEnabledTaskByContractId
	 * @Description: TODO(获取指定合同有效账单任务)
	 * @param contractId
	 * @return: List<ExBillTask>
	 * @author: Ian.Huang
	 * @date: 2017年11月25日 下午3:33:24
	 */
	List<ExBillTask> getEnabledTaskByContractId(@Param("contractId")Integer contractId);
	
	/**
	 * @Title: updateTaskDisabled
	 * @Description: TODO(任务完成后自动失效)
	 * @param id
	 * @return: int
	 * @author: Ian.Huang
	 * @date: 2017年11月25日 下午3:59:54
	 */
	int updateTaskDisabled(@Param("id")String id);
	
}
