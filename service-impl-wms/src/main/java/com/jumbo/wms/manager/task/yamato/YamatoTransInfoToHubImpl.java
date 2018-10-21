package com.jumbo.wms.manager.task.yamato;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.baozun.task.annotation.SingleTaskLock;
import com.jumbo.dao.warehouse.YamatoConfirmOrderQueueDao;
import com.jumbo.wms.daemon.YamatoTransInfoToHub;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.ymt2hub.YamatoTransToHubManager;
import com.jumbo.wms.model.warehouse.YamatoConfirmOrderQueue;


public class YamatoTransInfoToHubImpl extends BaseManagerImpl implements YamatoTransInfoToHub {

    private static final long serialVersionUID = -2624748689650295963L;
    @Autowired
    private YamatoConfirmOrderQueueDao yamatoConfirmOrderQueueDao;
    @Autowired
    private YamatoTransToHubManager yamatoTransToHubManager;

    @Override
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void sendYamatoTransInfoToHub() {
        // 获取yamato待反馈队列
        List<YamatoConfirmOrderQueue> ymtList = yamatoConfirmOrderQueueDao.getYamatoInfoSendHub();
        if (!ymtList.isEmpty()) {
            try {
                yamatoTransToHubManager.yamatoTransInfoToHub(ymtList);
            } catch (Exception e) {
                log.error("", e);
            }
        }

    }
}
