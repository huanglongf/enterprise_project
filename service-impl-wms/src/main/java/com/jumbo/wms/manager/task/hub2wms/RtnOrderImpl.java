package com.jumbo.wms.manager.task.hub2wms;

import java.util.List;

import loxia.dao.support.BeanPropertyRowMapperExt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;

import com.jumbo.dao.hub2wms.WmsRtnInOrderQueueDao;
import com.jumbo.dao.hub2wms.WmsSalesOrderQueueDao;
import com.jumbo.dao.system.ChooseOptionDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.wms.daemon.RtnOrderTask;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.hub2wms.RtnOrderManager;
import com.jumbo.wms.model.hub2wms.WmsRtnInOrderQueue;
import com.jumbo.wms.model.hub2wms.WmsSalesOrderQueue;
import com.jumbo.wms.model.system.ChooseOption;
import com.jumbo.wms.model.warehouse.StockTransApplication;

@Service("rtnOrderTask")
public class RtnOrderImpl extends BaseManagerImpl implements RtnOrderTask {
    /**
     * 
     */
    private static final long serialVersionUID = 2193682965729237287L;
    @Autowired
    private WmsRtnInOrderQueueDao inOrderQueueDao;
    @Autowired
    private ChooseOptionDao chooseOptionDao;
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private WmsSalesOrderQueueDao orderQueueDao;
    @Autowired
    private RtnOrderManager orderManager;

    @Override
    public void createRtnOrder() {
        if (log.isDebugEnabled()) {
            log.debug("createRtnOrder is");
        }
        // 获取批订单数量
        ChooseOption chooseOption = chooseOptionDao.findByCategoryCodeAndKey("num", "100");
        // 获取批次号
        String barchCode = inOrderQueueDao.queryBatchcode(new SingleColumnRowMapper<String>(String.class));
        // 更新批次
        try {
            int updateQty = inOrderQueueDao.updateQstaBatchCodeByOuid(barchCode, Integer.parseInt(chooseOption.getOptionValue()));
            if (updateQty > 0) {
                createRtnOrderBatchCode(barchCode);
            }

            List<String> batchCodes = inOrderQueueDao.findBatchCodeByDetial(new SingleColumnRowMapper<String>(String.class));
            for (String string : batchCodes) {
                createRtnOrderBatchCode(string);
            }

        } catch (Exception e) {
            log.error("", e);
        }

    }

    public void createRtnOrderBatchCode(String batchCode) {
        // 查询出该批次所有信息
        List<WmsRtnInOrderQueue> inOrderQueues = inOrderQueueDao.findByRtnInorderBatchcode(batchCode, new BeanPropertyRowMapperExt<WmsRtnInOrderQueue>(WmsRtnInOrderQueue.class));

        for (WmsRtnInOrderQueue wmsRtnInOrderQueue : inOrderQueues) {

            try {
                WmsSalesOrderQueue orderQueue = orderQueueDao.getOrderToSetFlagByOrderCode(wmsRtnInOrderQueue.getOrderCode());
                if (orderQueue != null) {
                    try {
                        StockTransApplication sta = staDao.findStaBySlipCodeNotCancel(orderQueue.getOrderCode());
                        if (sta == null) {
                            // 执行校验库存,创建作业单
                            orderManager.createRtnOrder(orderQueue, wmsRtnInOrderQueue);
                        } else {
                            // 标识单据异常不处理，修改error count + 100
                            wmsRtnInOrderQueue.setErrorCount(wmsRtnInOrderQueue.getErrorCount() == null ? 0 : 100);
                            inOrderQueueDao.save(wmsRtnInOrderQueue);
                        }

                    } catch (Exception e) {
                        log.error("createRtnOrderBatchCode is " + wmsRtnInOrderQueue.getOrderCode(), e);
                        wmsRtnInOrderQueue.setErrorCount(wmsRtnInOrderQueue.getErrorCount() == null ? 0 : wmsRtnInOrderQueue.getErrorCount() + 1);
                        inOrderQueueDao.save(wmsRtnInOrderQueue);
                    }

                } else {
                    try {
                        StockTransApplication sta = staDao.findStaBySlipCodeNotCancel(wmsRtnInOrderQueue.getOrderCode());
                        if (sta == null) {
                            orderManager.createRtnOrder(wmsRtnInOrderQueue);
                        } else {
                            // 标识单据异常不处理，修改error count + 100
                            wmsRtnInOrderQueue.setErrorCount(wmsRtnInOrderQueue.getErrorCount() == null ? 0 : 100);
                            inOrderQueueDao.save(wmsRtnInOrderQueue);
                        }
                    } catch (Exception e) {
                        log.error("createRtnOrderBatchCode is" + wmsRtnInOrderQueue.getOrderCode(), e);
                        wmsRtnInOrderQueue.setErrorCount(wmsRtnInOrderQueue.getErrorCount() == null ? 0 : wmsRtnInOrderQueue.getErrorCount() + 1);
                        inOrderQueueDao.save(wmsRtnInOrderQueue);
                    }
                }


            } catch (Exception e) {
                log.error("", e);
            }
        }

    }
}
