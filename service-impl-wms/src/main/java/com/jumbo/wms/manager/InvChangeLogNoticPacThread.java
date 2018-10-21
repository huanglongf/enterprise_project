package com.jumbo.wms.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jumbo.wms.manager.util.SpringUtil;
import com.jumbo.wms.manager.warehouse.WareHouseManagerExe;

/**
 * 库存状态修改通知PAC
 * @author jingkai
 *
 */
public class InvChangeLogNoticPacThread implements Runnable {
    protected static final Logger logger = LoggerFactory.getLogger(InvChangeLogNoticPacThread.class);
    private Long staId;
    private Long stvId;

    public InvChangeLogNoticPacThread(Long staId,Long stvId) {
        this.staId = staId;
        this.stvId = stvId;
    }

    @Override
    public void run() {
        //默认等待10秒,等待原任务事务提交
        try {
            Thread.sleep(10 *1000);
        } catch (InterruptedException e) {
        }
        WareHouseManagerExe m = SpringUtil.getBean(WareHouseManagerExe.class);
        m.invChangeLogNoticePac(staId,stvId);


    }

}
