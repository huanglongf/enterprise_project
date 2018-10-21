package com.jumbo.wms.manager.task.seckill;


import java.util.List;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.warehouse.PackageSku;
import com.jumbo.wms.model.warehouse.PackageSkuCounterCommand;
import com.jumbo.wms.model.warehouse.SecKillSkuCounter;
import com.jumbo.wms.model.warehouse.StockTransApplication;

/**
 * 
 * @author dly
 * 
 */
public interface SecKillTaskManager extends BaseManager {

    /**
     * 删除所有过期的秒杀订单商品计数器的记录，默认过期时间定义为48小时
     */
    public void deleteExpireRecord();

    /**
     * 删除所有秒杀商品过期记录，默认过期时间24小时，手工维护按维护时间为准，未维护则按系统默认
     */
    public void deleteExpireRecordSecKill();

    /**
     * 按商品统计求和，找出所有满足阀值（数据库定义）的商品，该商品秒杀订单商品中记录，将对应的该类作业单标识为秒杀订单， 删除对应秒杀订单商品计数器记录
     */
    public List<SecKillSkuCounter> statisticsSeckKillOrder();

    /**
     * 删除过期的套装组合商品和无效的套装组合商品计数器
     */
    public void deleteExpirePackageSkuAndNoUseCounter();

    /**
     * 记录套装组合商品
     * 
     * @param psc
     */
    public PackageSku takePackskuRecord(PackageSkuCounterCommand psc);

    /**
     * 删除套装组合商品计数器
     * 
     * @param psc
     * @param staList
     */
    public void deletePackageSkuCounter(PackageSkuCounterCommand psc, List<StockTransApplication> staList);

    /**
     * 根据套装组合商品查看满足条件的sta
     * 
     * @param psc
     * @return
     */
    public List<StockTransApplication> selectStaByPackageSku(PackageSku ps);

    /**
     * 更新作业单为套装组合商品
     * 
     * @param idList
     */
    public void updateStaToPackageSkuByIdList(List<Long> idList, Long psId);

    /**
     * 将商品记录到秒杀商品表里面
     * 
     * @param ss
     */
    public void addSecKillSku(SecKillSkuCounter ss);
    /**
     * 查询满足对应秒杀的staList
     * @param ss
     * @return
     */
    public List<StockTransApplication> getSecKillStaList(SecKillSkuCounter ss);
    /**
     * 根据idList跟新作业单为秒杀单据
     * @param idList
     */
    public void updateStaToSecKillByIdList(List<Long> idList);
    /**
     * 删除秒杀计数器，扣减套装组合商品计数器
     * @param ss
     * @param staList
     */
    public void deleteSecKillSkuCounter(SecKillSkuCounter ss, List<StockTransApplication> staList);
}
