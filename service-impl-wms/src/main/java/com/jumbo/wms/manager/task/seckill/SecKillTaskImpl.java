/**
 * Copyright (c) 2010 Jumbomart All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of Jumbomart. You shall not
 * disclose such Confidential Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with Jumbo.
 * 
 * JUMBOMART MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF THE SOFTWARE, EITHER
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE, OR NON-INFRINGEMENT. JUMBOMART SHALL NOT BE LIABLE FOR ANY
 * DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES.
 * 
 */

package com.jumbo.wms.manager.task.seckill;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import com.baozun.task.annotation.SingleTaskLock;
import com.jumbo.dao.warehouse.PackageSkuCounterDao;
import com.jumbo.wms.daemon.SecKillTask;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.model.warehouse.PackageSku;
import com.jumbo.wms.model.warehouse.PackageSkuCounterCommand;
import com.jumbo.wms.model.warehouse.SecKillSkuCounter;
import com.jumbo.wms.model.warehouse.StockTransApplication;



/**
 * @author dly
 * 
 */

public class SecKillTaskImpl extends BaseManagerImpl implements SecKillTask {

    private static final long serialVersionUID = 2411683624366043149L;
    @Autowired
    private SecKillTaskManager secKillTaskManager;
    @Autowired
    private PackageSkuCounterDao packageSkuCounterDao;

    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void taskTrigger() {
        try {
            log.debug("=========================Delete expired counter records !");
            // 删除过期的计数器的记录
            secKillTaskManager.deleteExpireRecord();
        } catch (Exception e) {
            log.debug("secKillTaskManager.deleteExpireRecord() error!");
            log.debug(e.getMessage());
        }
        try {
            log.debug("=========================Delete expired seckill commodity !");
            // 删除过期秒杀商品
            secKillTaskManager.deleteExpireRecordSecKill();
        } catch (Exception e) {
            log.debug("secKillTaskManager.deleteExpireRecordSecKill() error!");
            log.debug(e.getMessage());
        }
        try {
            log.debug("=========================Seckill seckill counter records according to the calculation of goods !");
            // 根据秒杀计数器记录计算秒杀商品
            List<SecKillSkuCounter> list = secKillTaskManager.statisticsSeckKillOrder();
            for (SecKillSkuCounter ss : list) {
                try{
                    // 将商品记录到秒杀商品表里面
                    secKillTaskManager.addSecKillSku(ss);
                    // 将所有对应的秒杀订单
                    List<StockTransApplication> staList = secKillTaskManager.getSecKillStaList(ss);
                    List<Long> idList = new ArrayList<Long>();
                    for(StockTransApplication sta:staList){
                        idList.add(sta.getId());
                    }
                    secKillTaskManager.updateStaToSecKillByIdList(idList);
                    secKillTaskManager.deleteSecKillSkuCounter(ss,staList);
                }catch(Exception e){
                    log.error("",e);
                }
            }
        } catch (Exception e) {
            log.debug("secKillTaskManager.statisticsSeckKillOrder() error!");
            log.debug(e.getMessage());
        }
        try {
            log.debug("==========================Delete expired packageSku and nouse packageSkuCounter");
            secKillTaskManager.deleteExpirePackageSkuAndNoUseCounter();
        } catch (Exception e) {
            log.debug("SecKillTask.taskTrigger.secKillTaskManager.deleteExpirePackageSkuAndNoUseCounter()-------------------->Exception");
            log.error("", e);
        }
//        try{
//            log.debug("===========================Discount packageSku counter when this sku in SecKillSku counter");
//            secKillTaskManager.discountPackageSkuCounter();
//        }catch(Exception e){
//            log.debug("SecKillTask.taskTrigger.secKillTaskManager.discountPackageSkuCounter()--------------------->Exception");
//            log.error("",e);
//        }
        try{
            log.debug("===========================Take a record for packageSku");
            List<PackageSkuCounterCommand> pscList = packageSkuCounterDao.findEnoughCountCounter(new BeanPropertyRowMapper<PackageSkuCounterCommand>(PackageSkuCounterCommand.class));
            for(PackageSkuCounterCommand psc:pscList){
                try{
                    //记录套装组合商品
                    PackageSku  ps = secKillTaskManager.takePackskuRecord(psc);
                    //查找满足条件的套装组合商品作业单
                    List<StockTransApplication> staList = secKillTaskManager.selectStaByPackageSku(ps);
                    List<Long> idList = new ArrayList<Long>();
                    //标记作业单为套装组合商品，记录id
                    for(StockTransApplication sta:staList){
                        idList.add(sta.getId());
                    }
                    secKillTaskManager.updateStaToPackageSkuByIdList(idList,ps.getId());
                    secKillTaskManager.deletePackageSkuCounter(psc,staList);
                }catch(Exception e){
                    log.error("",e);
                }
            }
        }catch(Exception e){
            log.debug("SecKillTask.taskTrigger.secKillTaskManager.takePackskuRecord()---------------->Exception");
            log.error("",e);
        }

    }
}
