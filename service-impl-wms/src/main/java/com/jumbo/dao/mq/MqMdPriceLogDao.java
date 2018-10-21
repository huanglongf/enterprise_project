package com.jumbo.dao.mq;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.mq.MqMdPriceLog;

import loxia.annotation.NamedQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

@Transactional
public interface MqMdPriceLogDao extends GenericEntityDao<MqMdPriceLog, Long> {
    /**
     * 查询未发送markdown价格信息
     * 
     * @param shopId
     * @return
     */
    @NamedQuery
    List<MqMdPriceLog> findSendMqMdPriceLog(@QueryParam("shopId") Long shopId);
}
