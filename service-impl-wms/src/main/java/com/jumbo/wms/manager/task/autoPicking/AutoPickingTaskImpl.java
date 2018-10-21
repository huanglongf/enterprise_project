package com.jumbo.wms.manager.task.autoPicking;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SingleColumnRowMapper;

import com.baozun.task.annotation.SingleTaskLock;
import com.jumbo.dao.authorization.OperationUnitDao;
import com.jumbo.wms.daemon.AutoPickingTask;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.task.mergeSta.MergeStaTaskImpl;

/**
 * 
 * 项目名称：scm-wms 类名称：AutoPickingTask 类描述：自动创建配货清单定时任务 创建人：bin.hu 创建时间：2014-9-18 上午09:27:35
 * 
 * @version
 * 
 */
public class AutoPickingTaskImpl extends BaseManagerImpl implements AutoPickingTask {

    private static final long serialVersionUID = 2805825352806822054L;

    @Autowired
    private AutoPickingManagerProxy autoPickingManagerProxy;
    @Autowired
    OperationUnitDao operationUnitDao;
    @Autowired
    MergeStaTaskImpl mergeStaTask;

    /**
     * 通用创建
     */
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void autoPicking() {
        log.debug("=================================AutoPickingTask.autoPicking begin！");
        List<Long> ouids = operationUnitDao.findWarehouseByIsVMIWh(true, new SingleColumnRowMapper<Long>(Long.class));
        for (Long ouid : ouids) {
            autoPickingManagerProxy.createCreateByWh(ouid, false);
        }
        log.debug("=================================AutoPickingTask.autoPicking end！");
    }

    /**
     * 单独定制任务
     * 
     * @param strWhOu
     */
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void autoPickingByWh(String strWhOu) {
        log.debug("=================================autoPickingByWh begin：" + strWhOu);
        Long whid = Long.valueOf(strWhOu);
        autoPickingManagerProxy.createCreateByWh(whid, true);
        log.debug("=================================autoPickingByWh end！");
    }

    /**
     * 单独合并订单 对于没有配置过自动创建配货清单仓库
     * 
     * @param strWhOu
     */
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void createMergeOrder() {
        log.debug("=================================createMergeOrder begin");
        List<Long> ouids = operationUnitDao.findWarehouseByIsVMIWh(false, new SingleColumnRowMapper<Long>(Long.class));
        for (Long ouid : ouids) {
            mergeStaTask.mergeSta(ouid);
        }
        log.debug("=================================createMergeOrder end！");
    }
}
