package com.jumbo.wms.manager.task.shelfLife;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.baozun.task.annotation.SingleTaskLock;
import com.jumbo.dao.warehouse.BiChannelDao;
import com.jumbo.wms.daemon.ShelfLifeTask;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.model.baseinfo.BiChannel;

/**
 * 项目名称：Wms 类名称：ShelfLifeTask 类描述：对保质期商品操作定时任务 创建人：bin.hu 创建时间：2014-6-12 下午02:23:18
 * 
 * @version
 * 
 */
public class ShelfLifeTaskImpl extends BaseManagerImpl implements ShelfLifeTask {

    private static final long serialVersionUID = -6697803805752431404L;

    @Autowired
    private ShelfLifeManager shelfLifeManager;

    @Autowired
    private BiChannelDao biDao;
    protected static final Logger logger = LoggerFactory.getLogger(ShelfLifeTaskImpl.class);

    /**
     * 定时任务-每日12:30点执行，库存状态修改，自动将所有可销售库存且需要预警库存修改为库存状态“临近保质期”
     */
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void updateShelfLifeStatus() {
        logger.debug("=================================ShelfLifeTask begin！");
        try {
            List<BiChannel> biList = biDao.getAllBiChannel();// 获取所有渠道信息
            for (BiChannel b : biList) {
                shelfLifeManager.updateShelfLifeStatus(b);
            }
        } catch (Exception e) {
            logger.error("updateShelfLifeStatus", e);
        }
        logger.debug("=================================ShelfLifeTask end！");
    }

    /**
     * 定时任务-每10分钟一次，同步给OMS保质期商品修改信息
     */
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void updateSkuShelfLifeTime() {
        log.debug("=================================updateSkuShelfLifeTime begin！");
        try {
            shelfLifeManager.updateSkuShelfLifeTime();
        } catch (Exception e) {
            log.error("", e);
        }
        log.debug("=================================updateSkuShelfLifeTime end！");
    }
}
