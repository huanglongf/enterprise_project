package com.jumbo.dao.warehouse;

import java.util.List;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.warehouse.CxcConfirmOrderQueue;
import com.jumbo.wms.model.warehouse.CxcConfirmOrderQueueCommand;

@Transactional
public interface CxcConfirmOrderQueueDao extends GenericEntityDao<CxcConfirmOrderQueue, Long> {

	/**
	 * 方法说明：查询所有新建和失败状态的CXC中间表数据
	 * @author LuYingMing
	 * @date 2016年5月30日 下午4:12:19 
	 * @return
	 */
    @NamedQuery
    public List<CxcConfirmOrderQueue> getAllCxcOrderQueue();

    /**
     * 方法说明：根据仓库作业申请单号查询CxcConfirmOrderQueueCommand
     * @author LuYingMing
     * @param staId
     * @param beanPropertyRowMapper
     * @return CxcConfirmOrderQueueCommand
     */
    @NativeQuery
    List<CxcConfirmOrderQueueCommand> findCxcConfirmOrderQueueByStaId(@QueryParam(value = "staId") Long staId, RowMapper<CxcConfirmOrderQueueCommand> beanPropertyRowMapper);
    
    /**
     * 方法说明：根据运单编号查询CXC订单确认队列
     * @author LuYingMing
     * @param transNo
     * @return CxcConfirmOrderQueue
     */
    @NamedQuery
    public CxcConfirmOrderQueue getCxcConfirmOrderQueueByTransCode(@QueryParam(value = "transNo") String transNo);
    
    
    
    
    
}
