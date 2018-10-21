package com.jumbo.dao.mq;

import java.util.List;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.mq.MqSkuPriceLog;

@Transactional
public interface MqPriceLogDao extends GenericEntityDao<MqSkuPriceLog, Long> {

    /**
     * 查询未发送商品价格信息
     * 
     * @param shopId
     * @return
     */
    @NamedQuery
    List<MqSkuPriceLog> findSendMqSkuPriceLog(@QueryParam("shopId") Long shopId);

    /**
     * 将IT价格中间表中状态为0的记录更新状态为1
     */
    @NativeUpdate
    void updatePriceDataFlagForITBS();

}
