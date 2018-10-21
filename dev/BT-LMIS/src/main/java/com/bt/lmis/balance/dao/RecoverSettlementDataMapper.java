package com.bt.lmis.balance.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bt.lmis.balance.model.RecoverProcess;

/** 
 * @ClassName: RecoverSettlementDataMapper
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Ian.Huang
 * @date 2017年7月11日 上午10:42:12 
 * 
 * @param <T>
 */
public interface RecoverSettlementDataMapper<T> {
	
	/**
	 * @Title: ensureRecoverProcess
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @return: List<Map<String,Object>>
	 * @author: Ian.Huang
	 * @date: 2017年7月13日 下午7:55:15
	 */
	List<Map<String, Object>> ensureRecoverProcess();
	
	/**
	 * @Title: addRecoverTask
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param recoverProcess
	 * @return: void
	 * @author: Ian.Huang
	 * @date: 2017年7月14日 下午12:48:05
	 */
	void addRecoverTask(List<RecoverProcess> recoverProcess);
	
	/**
	 * @Title: existProcessing
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @return: int
	 * @author: Ian.Huang
	 * @date: 2017年7月14日 下午1:47:34
	 */
	int existProcessing();
	
	/**
	 * @Title: ensureNextProcessing
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @return: RecoverProcess
	 * @author: Ian.Huang
	 * @date: 2017年7月14日 下午2:14:51
	 */
	RecoverProcess ensureNextProcessing();
	
	/**
	 * @Title: updateProcess
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param status
	 * @param log
	 * @param id
	 * @return: void
	 * @author: Ian.Huang
	 * @date: 2017年7月14日 下午2:48:14
	 */
	void updateProcess(@Param("status")int status, @Param("log") String log, @Param("id")int id);
	
	/**
	 * @Title: ensureContractType
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param conId
	 * @return: int
	 * @author: Ian.Huang
	 * @date: 2017年7月12日 下午5:02:38
	 */
	int ensureContractType(@Param("conId")int conId);
	
	/**
	 * @Title: deleteErrorDetail
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param conId
	 * @param recoverStartDate
	 * @param recoverEndDate
	 * @param errorType
	 * @return: int
	 * @author: Ian.Huang
	 * @date: 2017年7月11日 下午8:42:46
	 */
	int deleteErrorDetail(
			@Param("conId")int conId,
			@Param("recoverStartDate")String recoverStartDate,
			@Param("recoverEndDate")String recoverEndDate,
			@Param("errorType")int errorType);
	
	/**
	 * @Title: deleteSettlementDetail
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param settlementDetailTable
	 * @param conId
	 * @param recoverStartDate
	 * @param recoverEndDate
	 * @return: int
	 * @author: Ian.Huang
	 * @date: 2017年7月11日 下午8:39:30
	 */
	int deleteSettlementDetail(
			@Param("settlementDetailTable")String settlementDetailTable,
			@Param("conId")int conId,
			@Param("recoverStartDate")String recoverStartDate,
			@Param("recoverEndDate")String recoverEndDate);
	
	/**
	 * @Title: recoverSettleFlag
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param recoverProcess
	 * @return: int
	 * @author: Ian.Huang
	 * @date: 2017年7月14日 下午2:03:21
	 */
	int recoverSettleFlag(RecoverProcess recoverProcess);
	
	/**
	 * @Title: recoverSettleClientFlag
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param recoverProcess
	 * @return: int
	 * @author: Ian.Huang
	 * @date: 2017年7月14日 下午2:03:11
	 */
	int recoverSettleClientFlag(RecoverProcess recoverProcess);
	
	/**
	 * @Title: cleanRecover
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @return: void
	 * @author: Ian.Huang
	 * @date: 2017年7月14日 下午2:29:46
	 */
	void cleanRecover();
	
}
