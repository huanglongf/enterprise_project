package com.jumbo.wms.manager.task;

import java.util.List;

import loxia.dao.support.BeanPropertyRowMapperExt;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.dao.warehouse.WmsIntransitNoticeOmsDao;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.listener.StaListenerManager;
import com.jumbo.wms.model.warehouse.WmsIntransitNoticeOms;

/**
 * 
 * @author xiaolong.fei WMS出库双十一优化
 */
public class TaskOmsOutThread extends BaseManagerImpl implements Runnable {

    /**
     * 
     */
    private static final long serialVersionUID = 6657073643890859462L;

    @Autowired
    private WmsIntransitNoticeOmsDao wmsIntransitNoticeOmsDao;
    @Autowired
    private StaListenerManager staListenerManager;
    /**
     * 全局变量 用于传 批次号
     */
    private String batchCode;

    @Override
    public void run() {
        log.debug("-- 调用oms出库接口定时任务 --  start");
        // 根据批次号来查询数据
        List<WmsIntransitNoticeOms> list = wmsIntransitNoticeOmsDao.findPartIntransitByCode(batchCode, new BeanPropertyRowMapperExt<WmsIntransitNoticeOms>(WmsIntransitNoticeOms.class));
        for (WmsIntransitNoticeOms item : list) {
            try {
                staListenerManager.transferOmsOutBound(item.getStaId(), item.getId());
            } catch (Exception e) {
                log.error("", e);
            }
        }
    }

    public String getBatchCode() {
        return batchCode;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
    }

}
