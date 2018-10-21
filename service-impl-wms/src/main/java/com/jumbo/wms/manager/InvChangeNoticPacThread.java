package com.jumbo.wms.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jumbo.wms.manager.util.SpringUtil;
import com.jumbo.wms.manager.warehouse.excel.ExcelReadManager;

/**
 * 库存状态修改通知PAC
 * @author jingkai
 *
 */
public class InvChangeNoticPacThread implements Runnable {
    protected static final Logger logger = LoggerFactory.getLogger(InvChangeNoticPacThread.class);
    private Long staId;

    public InvChangeNoticPacThread(Long staId) {
        this.staId = staId;
    }

    @Override
    public void run() {
        //默认等待10秒,等待原任务事务提交
        try {
            Thread.sleep(10 *1000);
        } catch (InterruptedException e) {
        }
        ExcelReadManager m = SpringUtil.getBean(ExcelReadManager.class);
        m.invChangeNoticPac(staId);


    }

}
