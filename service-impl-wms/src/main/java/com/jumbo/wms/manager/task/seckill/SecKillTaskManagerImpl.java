package com.jumbo.wms.manager.task.seckill;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import loxia.dao.support.BeanPropertyRowMapperExt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.dao.warehouse.PackageSkuCounterDao;
import com.jumbo.dao.warehouse.PackageSkuDao;
import com.jumbo.dao.warehouse.SecKillSkuCounterDao;
import com.jumbo.dao.warehouse.SecKillSkuDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.warehouse.PackageSkuManager;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.warehouse.PackageSku;
import com.jumbo.wms.model.warehouse.PackageSkuCounterCommand;
import com.jumbo.wms.model.warehouse.SecKillSkuCounter;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationPickingType;
import com.jumbo.wms.model.warehouse.StockTransApplicationStatus;


/**
 * 
 * @author dly
 * 
 */
@Transactional
@Service("secKillTaskManager")
public class SecKillTaskManagerImpl extends BaseManagerImpl implements SecKillTaskManager {

    private static final long serialVersionUID = -8739477317641611746L;

    @Autowired
    private SecKillSkuCounterDao secKillSkuCounterDao;
    @Autowired
    private SecKillSkuDao secKillSkuDao;
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private PackageSkuDao packageSkuDao;
    @Autowired
    private PackageSkuCounterDao packageSkuCounterDao;
    @Autowired
    private PackageSkuManager packageSkuManager;

    /**
     * 按商品统计求和，找出所有满足阀值（数据库定义）的商品， 该商品秒杀订单商品中记录，将对应的该类作业单标识为秒杀订单， 删除对应秒杀订单商品计数器记录
     */
    @Override
    public List<SecKillSkuCounter> statisticsSeckKillOrder() {
        // 找出达到秒杀订单商品的SKUS
        return secKillSkuCounterDao.findSecKillSku(new BeanPropertyRowMapperExt<SecKillSkuCounter>(SecKillSkuCounter.class));
    }

    /**
     * 删除所有过期的秒杀订单商品计数器的记录，默认过期时间定义为48小时
     */
    @Override
    public void deleteExpireRecord() {
        secKillSkuCounterDao.deleteExpireRecordSecKillSku();
    }

    /**
     * 删除所有秒杀商品过期记录，默认过期时间24小时，手工维护按维护时间为准，未维护则按系统默认 存在新建与配货失败的秒杀订单不能删除
     */
    @Override
    public void deleteExpireRecordSecKill() {
        secKillSkuDao.deleteExpireRecordSecKill();
    }

    @Override
    public void deleteExpirePackageSkuAndNoUseCounter() {
        List<PackageSku> skus = packageSkuDao.findExirePackageSku(new BeanPropertyRowMapper<PackageSku>(PackageSku.class));
        for (PackageSku sku : skus) {
            packageSkuManager.deletePackageSkuById(sku.getId(), sku.getSkuQty());
        }
        packageSkuCounterDao.deleteExpirePackageSkuCounter();
    }

    @Override
    public PackageSku takePackskuRecord(PackageSkuCounterCommand psc) {
        String skus1 = psc.getSkuId() + ";" + 1;
        PackageSku ps = new PackageSku();
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE, 1);
        Date d1 = c.getTime();
        ps.setCreateTime(new Date());
        ps.setExpTime(d1);
        OperationUnit ou = new OperationUnit();
        ou.setId(psc.getOuId());
        ps.setOu(ou);
        ps.setSkuQty(psc.getSkuQty());
        ps.setStaTotalSkuQty(psc.getStaTotalSkuQty());
        ps.setIsSystem(true);
        ps.setSkus1(skus1);
        packageSkuDao.save(ps);
        packageSkuDao.flush();
        return ps;
    }

    @Override
    public void deletePackageSkuCounter(PackageSkuCounterCommand psc, List<StockTransApplication> staList) {
        packageSkuCounterDao.deletePackageSkuCounterByPackageSku(psc.getOuId(), psc.getSkuId(), psc.getSkuQty(), psc.getStaTotalSkuQty());
        Map<Long, Long> skuAndQty = new HashMap<Long, Long>();
        for (int i = 0; i < staList.size(); i++) {
            Long skus1 = Long.parseLong(staList.get(i).getSkus().split(":")[1].split(",")[0].split(";")[0]);
            Long skus2 = Long.parseLong(staList.get(i).getSkus().split(":")[1].split(",")[1].split(";")[0]);
            Long skuId = skus1;
            if (skus1.equals(psc.getSkuId())) {
                skuId = skus2;
            }
            if (skuAndQty.get(skuId) == null) {
                skuAndQty.put(skuId, 1L);
            } else {
                skuAndQty.put(skuId, skuAndQty.get(skuId) + 1);
            }
        }
        for (Entry<Long, Long> entry : skuAndQty.entrySet()) {
            packageSkuCounterDao.updateCounterWhenPackage(psc.getOuId(), entry.getKey(), entry.getValue());
        }

    }

    @Override
    public List<StockTransApplication> selectStaByPackageSku(PackageSku ps) {
        List<Integer> statusList = new ArrayList<Integer>();
        statusList.add(StockTransApplicationStatus.CREATED.getValue());
        statusList.add(StockTransApplicationStatus.FAILED.getValue());
        return staDao.selectStaByPackageSku("%" + ps.getSkus1() + "%", ps.getStaTotalSkuQty(), ps.getOu().getId(), ps.getSkuQty(), statusList, ":", new BeanPropertyRowMapper<StockTransApplication>(StockTransApplication.class));
    }

    @Override
    public void updateStaToPackageSkuByIdList(List<Long> idList, Long psId) {
        staDao.updateStaToPackageSkuByIdList(idList, psId, StockTransApplicationPickingType.SKU_PACKAGE.getValue());
    }

    @Override
    public void addSecKillSku(SecKillSkuCounter ss) {
        secKillSkuDao.addSecKillSku(ss.getId(), ss.getSkus(), true);
    }

    @Override
    public List<StockTransApplication> getSecKillStaList(SecKillSkuCounter ss) {
        return staDao.getSecKillStaList(ss.getId(), ss.getSkus(), new BeanPropertyRowMapper<StockTransApplication>(StockTransApplication.class));
    }

    @Override
    public void updateStaToSecKillByIdList(List<Long> idList) {
        if (idList != null && idList.size() > 0) {
            for (Long id : idList) {
                List<Long> idL = new ArrayList<Long>();
                idL.add(id);
                staDao.updateStaToSecKillByIdList(idL, 1, StockTransApplicationPickingType.SECKILL.getValue());
            }
            // staDao.updateStaToSecKillByIdList(idList,1,StockTransApplicationPickingType.SECKILL.getValue());
        }
    }

    @Override
    public void deleteSecKillSkuCounter(SecKillSkuCounter ss, List<StockTransApplication> staList) {
        secKillSkuCounterDao.deleteSecKillSkuCounter(ss.getId(), ss.getSkus());
        if (staList != null && staList.size() > 0) {
            Long qty = Long.valueOf(staList.size());
            Long staTotalSkuQty = staList.get(0).getSkuQty();
            Long skuQty = Long.parseLong(staList.get(0).getSkus().split(":")[0]);
            Long ouId = ss.getId();
            List<Long> skuIdList = new ArrayList<Long>();
            String[] sList = ss.getSkus().split(":")[1].split(",");
            for (int i = 0; i < sList.length; i++) {
                skuIdList.add(Long.parseLong(sList[i].split(";")[0]));
            }
            packageSkuCounterDao.discountPackageSkuCounterWhenSecKill(skuIdList, ouId, staTotalSkuQty, skuQty, qty);
        }
    }
}
