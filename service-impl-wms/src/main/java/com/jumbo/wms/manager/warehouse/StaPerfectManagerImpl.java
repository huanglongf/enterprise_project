package com.jumbo.wms.manager.warehouse;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import loxia.dao.support.BeanPropertyRowMapperExt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.util.StringUtils;

import com.jumbo.dao.baseinfo.SkuCategoriesDao;
import com.jumbo.dao.baseinfo.WarehouseDao;
import com.jumbo.dao.warehouse.PackageSkuCounterDao;
import com.jumbo.dao.warehouse.PackageSkuDao;
import com.jumbo.dao.warehouse.SecKillSkuCounterDao;
import com.jumbo.dao.warehouse.SecKillSkuDao;
import com.jumbo.dao.warehouse.SkuSizeConfigDao;
import com.jumbo.dao.warehouse.StaLineDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.wms.model.baseinfo.SkuCategories;
import com.jumbo.wms.model.baseinfo.Warehouse;
import com.jumbo.wms.model.command.SkuCategoriesCommand;
import com.jumbo.wms.model.warehouse.PackageSkuCounter;
import com.jumbo.wms.model.warehouse.SecKillSku;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationCommand;
import com.jumbo.wms.model.warehouse.StockTransApplicationPickingType;
import com.jumbo.wms.model.warehouse.StockTransApplicationStatus;
import com.jumbo.wms.model.warehouse.StockTransApplicationType;
import com.jumbo.wms.model.warehouse.TransDeliveryType;

public class StaPerfectManagerImpl implements StaPerfectManager {
    /**
	 * 
	 */
    private static final long serialVersionUID = 7655608624111099186L;

    private static final Logger logger = LoggerFactory.getLogger(StaPerfectManager.class);

    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private SkuCategoriesDao skuCategoriesDao;
    @Autowired
    private SecKillSkuCounterDao secKillSkuCounterDao;
    @Autowired
    private StaLineDao staLineDao;
    @Autowired
    private SecKillSkuDao secKillSkuDao;
    @Autowired
    private SkuSizeConfigDao skuSizeConfigDao;
    @Autowired
    private WarehouseDao warehouseDao;
    @Autowired
    private PackageSkuDao packageSkuDao;
    @Autowired
    private PackageSkuCounterDao packageSkuCounterDao;



    @Override
    public void omsTOwmsUpdateSta(Long staId) {
        StockTransApplicationCommand sta = staDao.findStaById(staId, new BeanPropertyRowMapperExt<StockTransApplicationCommand>(StockTransApplicationCommand.class));
        if (sta.getIntStaStatus() == StockTransApplicationStatus.CREATED.getValue()) {
            if (sta.getIntStaType() == StockTransApplicationType.OUTBOUND_SALES.getValue() || sta.getIntStaType() == StockTransApplicationType.OUT_SALES_ORDER_OUTBOUND_SALES.getValue()) {
                updateSOSta(staId);
            } else if (sta.getIntStaType() == StockTransApplicationType.OUTBOUND_RETURN_REQUEST.getValue()) {
                updateReturnSta(staId);
            }
        }
    }

    /**
     * 销售过单成功后，完善订单信息
     * 
     * @param staId
     */
    private void updateSOSta(Long staId) {
        logger.debug("updateSOSta -->" + staId);
        // 更新基本信息
        updateBasicInfo(staId);
        StockTransApplication sta = staDao.getByPrimaryKey(staId);
        if (!sta.getPickingType().equals(StockTransApplicationPickingType.GROUP)) {
            Warehouse wh = warehouseDao.getByOuId(sta.getMainWarehouse().getId());
            if (!StringUtils.hasText(wh.getVmiSource())) {
                if (wh.getIsSupportSecKill() != null && wh.getIsSupportPackageSku() != null && wh.getIsSupportPackageSku() && wh.getIsSupportSecKill()) {
                    Integer snCount = staLineDao.findSkuSN(staId, new SingleColumnRowMapper<Integer>(Integer.class));
                    if (snCount == 0) {
                        // 查询秒杀订单商品是否有该订单商品
                        SecKillSku bian = secKillSkuDao.getSecKillSkuBySta(staId, new BeanPropertyRowMapperExt<SecKillSku>(SecKillSku.class));
                        if (bian == null) {// 如果订单非秒杀 要判断是否为套装组合商品
                            // 如果是套装组合商品则标记为套装组合商品
                            // 否则同时记录套装日志和秒杀日志
                            Long psId = packageSkuDao.findPackageSkuListBySkus1System(sta.getMainWarehouse().getId(), sta.getSkus(), Long.parseLong(sta.getSkus().split(":")[0]), sta.getSkuQty(), new SingleColumnRowMapper<Long>(Long.class));
                            if (psId != null) {
                                sta.setPackageSku(psId);
                                sta.setPickingType(StockTransApplicationPickingType.SKU_PACKAGE);
                                staDao.save(sta);
                                staDao.flush();
                            } else {
                                if (sta.getSkuQty().equals(2L) && sta.getSkus().split(":")[0].equals("2")) {
                                    Long sku1Id = Long.parseLong(sta.getSkus().split(":")[1].split(",")[0].split(";")[0]);
                                    PackageSkuCounter psc = packageSkuCounterDao.getPackageSkuCounter(sku1Id, 2L, 2L, sta.getMainWarehouse().getId(), new BeanPropertyRowMapper<PackageSkuCounter>(PackageSkuCounter.class));
                                    if (psc == null) {
                                        packageSkuCounterDao.addNewCounter(sta.getMainWarehouse().getId(), sku1Id);
                                    } else {
                                        packageSkuCounterDao.addCounterById(psc.getId());
                                    }
                                    Long sku2Id = Long.parseLong(sta.getSkus().split(":")[1].split(",")[1].split(";")[0]);
                                    PackageSkuCounter psc1 = packageSkuCounterDao.getPackageSkuCounter(sku2Id, 2L, 2L, sta.getMainWarehouse().getId(), new BeanPropertyRowMapper<PackageSkuCounter>(PackageSkuCounter.class));
                                    if (psc1 == null) {
                                        packageSkuCounterDao.addNewCounter(sta.getMainWarehouse().getId(), sku2Id);
                                    } else {
                                        packageSkuCounterDao.addCounterById(psc1.getId());
                                    }
                                }
                                // staDao.updateStaSedKill(staId, false);
                                // 订单商品不存在秒杀订单商品里面 须要在秒杀订单商品计数器表里面记录
                                secKillSkuCounterDao.addSecKillSkuCounterBySta(staId, "1:%");
                            }
                        } else {
                            // 订单商品包括在秒杀订单商品里面 将订单设置为秒杀订单
                            staDao.updateStaSedKill(staId, true);
                        }
                    }
                } else if (wh.getIsSupportSecKill() != null && wh.getIsSupportSecKill() && (wh.getIsSupportPackageSku() == null || (wh.getIsSupportPackageSku() != null && !wh.getIsSupportPackageSku()))) {
                    // 只支持秒杀，不支持套装组合
                    Integer snCount = staLineDao.findSkuSN(sta.getId(), new SingleColumnRowMapper<Integer>(Integer.class));
                    if (snCount == 0) {
                        SecKillSku bian = secKillSkuDao.getSecKillSkuBySta(sta.getId(), new BeanPropertyRowMapperExt<SecKillSku>(SecKillSku.class));
                        if (bian == null) {
                            secKillSkuCounterDao.addSecKillSkuCounterBySta(sta.getId(), "1:%");
                        } else {
                            staDao.updateStaSedKill(sta.getId(), true);
                        }
                    }
                } else if (wh.getIsSupportPackageSku() != null && wh.getIsSupportPackageSku() && (wh.getIsSupportSecKill() == null || (wh.getIsSupportSecKill() != null && !wh.getIsSupportSecKill()))) {
                    // 只支持套装组合，不支持秒杀
                    Integer snCount = staLineDao.findSkuSN(sta.getId(), new SingleColumnRowMapper<Integer>(Integer.class));
                    if (snCount == 0) {
                        Long psId = packageSkuDao.findPackageSkuListBySkus1System(sta.getMainWarehouse().getId(), sta.getSkus(), Long.parseLong(sta.getSkus().split(":")[0]), sta.getSkuQty(), new SingleColumnRowMapper<Long>(Long.class));
                        if (psId != null) {
                            sta.setPackageSku(psId);
                            sta.setPickingType(StockTransApplicationPickingType.SKU_PACKAGE);
                            staDao.save(sta);
                            staDao.flush();
                        } else {
                            if (sta.getSkuQty().equals(2L) && sta.getSkus().split(":")[0].equals("2")) {
                                Long sku1Id = Long.parseLong(sta.getSkus().split(":")[1].split(",")[0].split(";")[0]);
                                PackageSkuCounter psc = packageSkuCounterDao.getPackageSkuCounter(sku1Id, 2L, 2L, sta.getMainWarehouse().getId(), new BeanPropertyRowMapper<PackageSkuCounter>(PackageSkuCounter.class));
                                if (psc == null) {
                                    packageSkuCounterDao.addNewCounter(sta.getMainWarehouse().getId(), sku1Id);
                                } else {
                                    packageSkuCounterDao.addCounterById(psc.getId());
                                }
                                Long sku2Id = Long.parseLong(sta.getSkus().split(":")[1].split(",")[1].split(";")[0]);
                                PackageSkuCounter psc1 = packageSkuCounterDao.getPackageSkuCounter(sku2Id, 2L, 2L, sta.getMainWarehouse().getId(), new BeanPropertyRowMapper<PackageSkuCounter>(PackageSkuCounter.class));
                                if (psc1 == null) {
                                    packageSkuCounterDao.addNewCounter(sta.getMainWarehouse().getId(), sku2Id);
                                } else {
                                    packageSkuCounterDao.addCounterById(psc1.getId());
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 退换货过仓成功后，作业单信息完善
     * 
     * @param staId
     */
    private void updateReturnSta(Long staId) {
        logger.debug("returnSta -->" + staId);
        // 更新基本信息
        updateBasicInfo(staId);
        // 更新锁定换货出库
        staDao.updateStaLocked(staId);
    }

    /**
     * 更新作业单郭仓后的基本信息
     * 
     * @param staId
     */
    private void updateBasicInfo(Long staId) {
        // // 更新 作业单过后所需基本信息 //获取作业单商品公共分类
        // staDao.updateStaBasicInfo(staId, findStaSkuCategories(staId), ":");
        // staDao.flush();
        // // 设置作业单配货类型单件多件团购
        // StockTransApplication sta = staDao.getByPrimaryKey(staId);
        StockTransApplication sta = staDao.getByPrimaryKey(staId);
        Long scId = findStaSkuCategories(sta.getId());
        if (scId != null) {
            SkuCategories sc = skuCategoriesDao.getByPrimaryKey(scId);
            sta.setSkuCategoriesId(sc);
        }
        String skus = staDao.getSkusByStaId(sta.getId(), ":", new SingleColumnRowMapper<String>(String.class));
        Long skuQty = staDao.getSkuQtyByStaId(sta.getId(), new SingleColumnRowMapper<Long>(Long.class));
        Boolean isSn = staDao.getIsSnByStaId(sta.getId(), new SingleColumnRowMapper<Boolean>(Boolean.class));
        BigDecimal skuMaxLength = staDao.getSkuMaxLengthByStaId(sta.getId(), new SingleColumnRowMapper<BigDecimal>(BigDecimal.class));
        Integer isRailWay = staDao.getIsRailWayByStaId(sta.getId(), new SingleColumnRowMapper<Integer>(Integer.class));
        sta.setSkus(skus);
        sta.setSkuQty(skuQty);
        sta.setIsSn(isSn);
        sta.setSkuMaxLength(skuMaxLength);
        sta.setDeliveryType(TransDeliveryType.valueOf(isRailWay));
        if (sta.getSkuQty().equals(1L)) {
            sta.setPickingType(StockTransApplicationPickingType.SKU_SINGLE);
        } else {
            sta.setPickingType(StockTransApplicationPickingType.SKU_MANY);
        }
        Long groupConfig = skuSizeConfigDao.findConfigBySkuQty(sta.getSkuMaxLength(), new SingleColumnRowMapper<Long>(Long.class));
        if (sta.getSkuQty().compareTo(groupConfig) >= 0) {
            sta.setPickingType(StockTransApplicationPickingType.GROUP);
        }
        sta.setIsSedKill(false);
        staDao.save(sta);
        staDao.flush();
    }

    /**
     * 获取作业单商品的公共分类
     * 
     * @param staId
     * @return 公共分类节点ID
     */
    private Long findStaSkuCategories(Long staId) {
        // 返回结果
        Long result = null;

        // 查出所有作业单商品的所属分类信息（包括所属分类所有父节点）
        List<SkuCategoriesCommand> list = skuCategoriesDao.findSkuCategoryBySta(staId, new BeanPropertyRowMapperExt<SkuCategoriesCommand>(SkuCategoriesCommand.class));
        // 保存 每个商品里面的分类信息（包括所属分类所有父节点）
        List<List<SkuCategoriesCommand>> skuList = new ArrayList<List<SkuCategoriesCommand>>();
        // 是否是头信息
        boolean isHand = true;
        // 零时保存单个商品的所属分类已经所有父节点信息
        List<SkuCategoriesCommand> temp = null;
        // 将 商品的所属分类以及分类的所有父节点 按照每个商品 所分开来
        for (SkuCategoriesCommand com : list) {
            if (isHand) {
                temp = new ArrayList<SkuCategoriesCommand>();
                skuList.add(temp);
                isHand = false;
            }
            temp.add(com);
            if (com.getParentId() == null || com.getParentId() < 0) {
                isHand = true;
            }
        }
        // 如果 =1 证明作业单只存在一种商品 如果是一种商品 取商品分类 就取商品所属分类
        if (skuList.size() == 1) {
            result = skuList.get(0).get(0).getId();
        } else {
            // 从2开始 是因为 排除掉商品总类的遍历
            int index = 2;
            // 零时公共分类 默认赋值 商品总类
            Long tempSkuC = skuList.get(0).get(skuList.get(0).size() - 1).getId();
            do {
                long skucId = -1;
                for (List<SkuCategoriesCommand> tempList : skuList) {
                    // 判断是否前一次的遍历就是 当前SKU的所属分类节点
                    if (tempList.size() < index) {
                        result = tempSkuC;
                        break;
                    }
                    // 获取当前商品的当前分类节点信息
                    SkuCategoriesCommand bean = tempList.get(tempList.size() - index);
                    if (skucId == -1) {// 判断是否是遍历的第一个商品的分类信息
                        skucId = bean.getId();
                    } else if (skucId != bean.getId()) {// 当铺商品 同一级别的商品所属分类 与别的商品分类信息不同
                        // 所以取当前所属分类级别的上一级做公共
                        // 取上一级的商品分类节点
                        result = tempSkuC;
                        break;
                    }
                }
                // 在没有确定出公共分类时 确保下一轮遍历
                if (result == null) {
                    // ++是为了下一轮遍历下一级的分类节点信息
                    index++;
                    // 保存此次遍历中所遍历过的 分类节点信息
                    tempSkuC = skucId;
                }

            } while (result == null);
        }
        return result;
    }

}
