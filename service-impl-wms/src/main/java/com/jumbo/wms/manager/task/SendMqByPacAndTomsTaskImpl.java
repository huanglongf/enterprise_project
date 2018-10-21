package com.jumbo.wms.manager.task;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import com.baozun.task.annotation.SingleTaskLock;
import com.jumbo.dao.hub2wms.WmsSalesOrderQueueDao;
import com.jumbo.dao.system.ChooseOptionDao;
import com.jumbo.dao.warehouse.QueueStaDao;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.hub2wms.CreateStaTaskManager;
import com.jumbo.wms.model.hub2wms.WmsSalesOrderQueue;
import com.jumbo.wms.model.system.ChooseOption;
import com.jumbo.wms.model.warehouse.QueueSta;

public class SendMqByPacAndTomsTaskImpl extends BaseManagerImpl implements SendMqByPacAndTomsTask {

    /**
     * 1
     */
    private static final long serialVersionUID = 1959662420056346093L;

    @Autowired
    private QueueStaDao queueStaDao;

    @Autowired
    private WmsSalesOrderQueueDao wmsSalesOrderQueueDao;

    @Autowired
    private CreateStaTaskManager createStaTaskManager;

    @Autowired
    private ChooseOptionDao chooseOptionDao;



    @Override
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void sendMqByPacAndToms() {
        List<QueueSta> queueStaList = queueStaDao.findQueueStaSendMq();
        if (null != queueStaList && queueStaList.size() > 0) {
            for (QueueSta list : queueStaList) {
                createStaTaskManager.createOrdersendToMq(list.getId());
                list.setIsAgainSendMq(0);
            }
        }
        List<WmsSalesOrderQueue> wmsSalesOrderQueueList = wmsSalesOrderQueueDao.findWmsSalesOrderQueueSendMq();
        if (null != wmsSalesOrderQueueList && wmsSalesOrderQueueList.size() > 0) {
            for (WmsSalesOrderQueue list : wmsSalesOrderQueueList) {
                createStaTaskManager.createTomsOrdersendToMq(list.getId());
                list.setIsAgainSendMq(0);
            }
        }
    }


    @Override
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void sendMqTomsByMqLogTime() {
        BigDecimal num = null;
        ChooseOption ch = chooseOptionDao.findByCategoryCodeAndKey("sendMqTomsByMqLogTime_code", "1");
        if (ch != null && ch.getOptionValue() != null) {
            num = new BigDecimal(ch.getOptionValue());
        } else {
            num = new BigDecimal(2 / 24);
        }
        List<WmsSalesOrderQueue> wmsSalesOrderQueueList = wmsSalesOrderQueueDao.sendMqTomsByMqLogTime(num, new BeanPropertyRowMapper<WmsSalesOrderQueue>(WmsSalesOrderQueue.class));
        if (null != wmsSalesOrderQueueList && wmsSalesOrderQueueList.size() > 0) {
            for (WmsSalesOrderQueue list : wmsSalesOrderQueueList) {
                createStaTaskManager.createTomsOrdersendToMq(list.getId());
                list.setIsAgainSendMq(0);
            }
        }
    }
}
