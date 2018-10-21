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

package com.jumbo.wms.manager.trans;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import loxia.dao.support.BeanPropertyRowMapperExt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;

import com.jumbo.dao.authorization.OperationUnitDao;
import com.jumbo.dao.baseinfo.SkuCategoriesDao;
import com.jumbo.dao.baseinfo.SkuDao;
import com.jumbo.dao.baseinfo.TransportatorDao;
import com.jumbo.dao.baseinfo.WarehouseDao;
import com.jumbo.dao.hub2wms.WmsSalesOrderQueueDao;
import com.jumbo.dao.system.ChooseOptionDao;
import com.jumbo.dao.warehouse.AdvanceOrderAddInfoDao;
import com.jumbo.dao.warehouse.BiChannelDao;
import com.jumbo.dao.warehouse.OrderRecieverInfoDao;
import com.jumbo.dao.warehouse.SkuTagDao;
import com.jumbo.dao.warehouse.StaLineDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.dao.warehouse.TransAreaGroupDetialDao;
import com.jumbo.dao.warehouse.TransPrioritySelectionDao;
import com.jumbo.dao.warehouse.TransRoleDao;
import com.jumbo.dao.warehouse.TransRoleDetialDao;
import com.jumbo.util.Md5Util;
import com.jumbo.util.StringUtil;
import com.jumbo.util.TimeHashMap;
import com.jumbo.wms.Constants;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.baseinfo.Transportator;
import com.jumbo.wms.model.baseinfo.Warehouse;
import com.jumbo.wms.model.hub2wms.WmsSalesOrderQueue;
import com.jumbo.wms.model.system.ChooseOption;
import com.jumbo.wms.model.trans.SkuTagCommand;
import com.jumbo.wms.model.trans.SkuTagType;
import com.jumbo.wms.model.trans.TransAreaGroupDetial;
import com.jumbo.wms.model.trans.TransPrioritySelection;
import com.jumbo.wms.model.trans.TransRole;
import com.jumbo.wms.model.trans.TransRoleCommand;
import com.jumbo.wms.model.trans.TransRoleDetialCommand;
import com.jumbo.wms.model.warehouse.AdvanceOrderAddInfo;
import com.jumbo.wms.model.warehouse.RecieverInfo;
import com.jumbo.wms.model.warehouse.StaDeliveryInfo;
import com.jumbo.wms.model.warehouse.StaLineCommand;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationStatus;
import com.jumbo.wms.model.warehouse.StockTransApplicationType;
import com.jumbo.wms.model.warehouse.TransTimeType;
import com.jumbo.wms.model.warehouse.TransType;

/**
 * @author lingyun.dai
 * 
 */
@Service("transSuggestManager")
public class TransSuggestManagerImpl extends BaseManagerImpl implements TransSuggestManager {

    /**
     * 
     */
    private static final long serialVersionUID = 260199022227983780L;

    private static final Logger log = LoggerFactory.getLogger(TransSuggestManagerImpl.class);

    /**
     * 渠道物流推荐规则缓存
     */
    TimeHashMap<String, List<TransRoleCommand>> transRoleCache = new TimeHashMap<String, List<TransRoleCommand>>();

    /**
     * EMS关键字缓存
     */
    TimeHashMap<Long, List<TransPrioritySelection>> keyWordCache = new TimeHashMap<Long, List<TransPrioritySelection>>();

    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private TransRoleDao transRoleDao;
    @Autowired
    private OperationUnitDao ouDao;
    @Autowired
    private TransRoleDetialDao transRoleDetialDao;
    @Autowired
    private TransAreaGroupDetialDao tagdDao;
    @Autowired
    private SkuDao skuDao;
    @Autowired
    private StaLineDao staLineDao;
    @Autowired
    private SkuTagDao skuTagDao;
    @Autowired
    private SkuCategoriesDao skuCategoriesDao;
    @Autowired
    private ChooseOptionDao chooseOptionDao;
    @Autowired
    private WarehouseDao warehouseDao;
    @Autowired
    private TransPrioritySelectionDao transPrioritySelectionDao;
    @Autowired
    private WmsSalesOrderQueueDao wmsSalesOrderQueueDao;
    @Autowired
    private TransportatorDao transportatorDao;
    @Autowired
    private BiChannelDao biChannelDao;
    @Autowired
    private OrderRecieverInfoDao orderRecieverInfoDao;
    @Autowired
    AdvanceOrderAddInfoDao addInfoDao;

    /**
     * 居于渠道编码获取规则
     * 
     * @param owner
     * @return
     */
    @Override
    public List<TransRoleCommand> getTransRole(String owner) {
        if (StringUtil.isEmpty(owner)) return null;
        List<TransRoleCommand> result = transRoleCache.get(owner);
        // 缓存中的数据不存在或者已过期
        if (result == null) {
            result = cacheTransRole(owner);
        }
        return result;
    }

    /**
     * 作业单物流推荐
     * 
     * @param staId
     */
    @Override
    public void suggestTransService(Long staId) {
        ChooseOption co = chooseOptionDao.findByCategoryCodeAndKey("SUGGEST_TRANS_SWITCH", "SWITCH");
        if (co != null) {// //////////////////////////////////////////////////////新的推荐逻辑
            StockTransApplication sta = staDao.getByPrimaryKey(staId);
            if (sta == null) {
                return;
            }
            // Warehouse wh = warehouseDao.getByOuId(sta.getMainWarehouse().getId());
            // if (!StringUtil.isEmpty(wh.getVmiSource())) {
            // // 不支持外包仓
            // return;
            // }
            if (log.isInfoEnabled()) {
                log.info("TransSuggestManagerImpl.suggestTransService strat:{},{}", sta.getRefSlipCode(), sta.getSlipCode1());
            }
            if (!StockTransApplicationType.OUTBOUND_SALES.equals(sta.getType()) && !StockTransApplicationType.OUTBOUND_RETURN_REQUEST.equals(sta.getType())) {
                throw new BusinessException(ErrorCode.RETURN_REQUEST_STA_TYPE_ERROR, new Object[] {sta.getCode()});
            }
            if (!StockTransApplicationStatus.CREATED.equals(sta.getStatus())) {
                throw new BusinessException(ErrorCode.ERROR_STA_NOT_STATUS);
            }

            StaDeliveryInfo di = sta.getStaDeliveryInfo();
            if (di.getLpCode() != null) {
                return;
            }
            String province = (di.getProvince() == null ? "" : di.getProvince()), city = (di.getCity() == null ? "" : di.getCity()), district = di.getDistrict() == null ? "" : di.getDistrict();
            BigDecimal channlId = biChannelDao.queryChannlIdByOwner(sta.getOwner(), new SingleColumnRowMapper<BigDecimal>());
            List<StaLineCommand> staLineList = staLineDao.findLineSkuBySta(staId, new BeanPropertyRowMapperExt<StaLineCommand>(StaLineCommand.class));
            if (log.isInfoEnabled()) {
                log.info("TransSuggestManagerImpl.getTransRole strat:{},{}", sta.getRefSlipCode(), sta.getSlipCode1());
            }
            List<TransRoleCommand> roleList = getTransRole(sta.getOwner());
            if (log.isInfoEnabled()) {
                log.info("TransSuggestManagerImpl.getTransRole end:{},{}", sta.getRefSlipCode(), sta.getSlipCode1());
            }
            TransRoleCommand suggestTrans = null;
            TransRoleCommand defaultTrans = null;
            boolean isEmsPriority = isEmsPriority(di.getAddress(), channlId.longValue());
            if (log.isInfoEnabled()) {
                log.info("isEmsPriority : {} , {} ,{}", isEmsPriority, sta.getRefSlipCode(), sta.getSlipCode1());
            }
            if (isEmsPriority) {
                if (log.isInfoEnabled()) {
                    log.info("isEmsPriority true , {} ,{}", sta.getRefSlipCode(), sta.getSlipCode1());
                }
                suggestTrans = new TransRoleCommand();
                suggestTrans.setLpCode(Transportator.EMS);
                if (log.isInfoEnabled()) {
                    log.info("TransSuggestManagerImpl.isEmsPriority....end address:" + di.getAddress());
                }
            } else {
                if (log.isInfoEnabled()) {
                    log.info("isEmsPriority false , {} ,{}", sta.getRefSlipCode(), sta.getSlipCode1());
                }
                // 遍历 物流推荐规则
                for (TransRoleCommand tr : roleList) {
                    /** 1 所有推荐快递均需要当前仓库支持的快递（于WarehouseTransRef查询所有仓库支持的快递信息），仓库不支持的快递的服务规则直接忽略。 **/
                    if (!isWarehouseTransRef(sta.getMainWarehouse().getId(), tr)) {
                        if (log.isInfoEnabled()) {
                            log.info("TransRoleCommand isWarehouseTransRef continue, {} ,{}", sta.getRefSlipCode(), sta.getSlipCode1());
                        }
                        continue;
                    } else {}
                    /** 2 校验作业单如是货到付款作业单则推荐时快递服务所属快递必须支持COD **/
                    if (di.getIsCod() && !tr.getIsSupportCod()) {
                        if (log.isInfoEnabled()) {
                            log.info("TransRoleCommand is cod continue, {} ,{}", sta.getRefSlipCode(), sta.getSlipCode1());
                        }
                        continue;
                    }
                    // /** 3 根据匹配作业单发货明细staD.tranType、transTimeType一致的快递服务，如一致则校验该服务下的所有规则。 **/
                    // if (di.getTransType() != null && di.getTransTimeType() != null &&
                    // di.getTransType().getValue() == tr.getIntTransType() &&
                    // di.getTransTimeType().getValue() == tr.getIntTransTimeType()) {
                    if (log.isInfoEnabled()) {
                        log.info("TransRoleCommand trans time type,{},{}", sta.getRefSlipCode(), sta.getSlipCode1());
                    }
                    /** 4 规则明细计算方式：当规则中该条件为空则默认满足，只有当所有条件成立时，则当前规则匹配成功。 **/
                    List<TransRoleDetialCommand> details = tr.getDetials();
                    if (details != null && details.size() > 0) {
                        // 是否全部匹配
                        boolean isAllMatching = true;
                        // 遍历规则明细
                        if (log.isInfoEnabled()) {
                            log.info("TransSuggestManagerImpl.checkdetails strat: {},{}", sta.getRefSlipCode(), sta.getSlipCode1());
                        }
                        for (TransRoleDetialCommand detial : details) {
                            if (log.isInfoEnabled()) {
                                log.info("detial current start, {} ,{}", sta.getRefSlipCode(), sta.getSlipCode1());
                            }

                            /**
                             * 4.00 根据匹配作业单发货明细staD.tranType（
                             * 运送方式(快递附加服务)）、transTimeType一致的快递服务，如一致则校验该服务下的所有规则。
                             **/
                            if (di.getTransType() != null && di.getTransTimeType() != null && di.getTransType().getValue() == tr.getIntTransType()
                                    && (di.getTransTimeType().getValue() == (detial.getTimeType() == null ? -1 : detial.getTimeType()) || di.getTransTimeType().getValue() == 1)) {
                                /** 4.0计算订单金额，sta.order_total_actual是否>=minAmount、<=maxAmount **/
                                boolean isMaxAmount = detial.getMaxAmount() == null || (sta.getOrderTotalActual().doubleValue() <= detial.getMaxAmount().doubleValue());
                                boolean isMinAmount = detial.getMinAmount() == null || (sta.getOrderTotalActual().doubleValue() >= detial.getMinAmount().doubleValue());
                                if (!isMaxAmount || !isMinAmount) {
                                    isAllMatching = false;
                                    if (log.isInfoEnabled()) {
                                        log.info("isMaxAmount isMinAmount break, {} ,{}", sta.getRefSlipCode(), sta.getSlipCode1());
                                    }
                                    break;
                                }
                                if (log.isInfoEnabled()) {
                                    log.info("isMaxAmount isMinAmount end, {} ,{}", sta.getRefSlipCode(), sta.getSlipCode1());
                                }
                                /** 4.1 计算订单重量，sta.staDeliveryInfo.wieght是否>=minWeight、<=maxWeight **/
                                boolean isMaxWeight = detial.getMaxWeight() == null || (sta.getStaDeliveryInfo().getWeight().doubleValue() <= detial.getMaxWeight().doubleValue());
                                boolean isMinWeight = detial.getMinWeight() == null || (sta.getStaDeliveryInfo().getWeight().doubleValue() >= detial.getMinWeight().doubleValue());
                                if (!isMaxWeight || !isMinWeight) {
                                    isAllMatching = false;
                                    if (log.isInfoEnabled()) {
                                        log.info("isMaxWeight isMinWeight break, {} ,{}", sta.getRefSlipCode(), sta.getSlipCode1());
                                    }
                                    break;
                                }
                                if (log.isInfoEnabled()) {
                                    log.info("isMaxWeight isMinWeight end, {} ,{}", sta.getRefSlipCode(), sta.getSlipCode1());
                                }
                                /** 4.2 校验配送范围，循环配货范围组中明细以开头模糊匹配校验省市区(如范围组中市为空则不计算市、区，如区为空则不计算区) **/
                                if (!isDistributionRange(province, city, district, detial)) {
                                    isAllMatching = false;
                                    if (log.isInfoEnabled()) {
                                        log.info("isMaxWeight isDistributionRange break, {} ,{}", sta.getRefSlipCode(), sta.getSlipCode1());
                                    }
                                    break;
                                }
                                if (log.isInfoEnabled()) {
                                    log.info("TransSuggestManagerImpl.isDistributionRange end:{},{}", sta.getRefSlipCode(), sta.getSlipCode1());
                                }
                                /** 4.3 计算商品，作业单中存在1个商品满足则当前条件成立 **/
                                List<Long> skuIds = detial.getSkuIds();
                                if (skuIds == null) {
                                    // 获取关联商品
                                    skuIds = getTrdRefSku(detial);
                                }
                                if (!isSkusContainStaSkuAll(skuIds, staLineList)) {
                                    isAllMatching = false;
                                    if (log.isInfoEnabled()) {
                                        log.info("TransSuggestManagerImpl.isSkusContainStaSkuAll break:{},{}", sta.getRefSlipCode(), sta.getSlipCode1());
                                    }
                                    break;
                                }
                                if (log.isInfoEnabled()) {
                                    log.info("TransSuggestManagerImpl.isSkusContainStaSkuAll end:{},{}", sta.getRefSlipCode(), sta.getSlipCode1());
                                }
                                /** 4.4 计算商品标签，判断匹配逻辑 **/
                                List<SkuTagCommand> skuTagRefSku = detial.getSkuTagRefSku();
                                if (skuTagRefSku == null) {
                                    // 所有商品标签(非禁用)所关联的商品
                                    skuTagRefSku = getTrdRefSkuTag(detial);
                                }
                                /** 4.4.1 计算商品标签，作业单中商品只要有1个商品标签满足则当前条件成立 **/
                                if (!isSkusContainStaSku(skuTagRefSku, staLineList)) {
                                    isAllMatching = false;
                                    if (log.isInfoEnabled()) {
                                        log.info("TransSuggestManagerImpl.isSkusContainStaSku break:{},{}", sta.getRefSlipCode(), sta.getSlipCode1());
                                    }
                                    break;
                                }
                                if (log.isInfoEnabled()) {
                                    log.info("TransSuggestManagerImpl.isSkusContainStaSku end:{},{}", sta.getRefSlipCode(), sta.getSlipCode1());
                                }
                                /** 4.4.2 计算商品标签，作业单中商品和商品标签完全匹配则当前条件成立 **/
                                if (!isSkusAllContainStaSku(skuTagRefSku, staLineList)) {
                                    isAllMatching = false;
                                    if (log.isInfoEnabled()) {
                                        log.info("TransSuggestManagerImpl.isSkusAllContainStaSku break:{},{}", sta.getRefSlipCode(), sta.getSlipCode1());
                                    }
                                    break;
                                }
                                if (log.isInfoEnabled()) {
                                    log.info("TransSuggestManagerImpl.isSkusAllContainStaSku end:{},{}", sta.getRefSlipCode(), sta.getSlipCode1());
                                }
                                /** 4.4.3 计算商品标签，订单中的所有商品均存在于维护商品列表则当前条件成立 **/
                                if (!isSkusOnlyContainStaSku(skuTagRefSku, staLineList)) {
                                    isAllMatching = false;
                                    if (log.isInfoEnabled()) {
                                        log.info("TransSuggestManagerImpl.isSkusOnlyContainStaSku break:{},{}", sta.getRefSlipCode(), sta.getSlipCode1());
                                    }
                                    break;
                                }
                                if (log.isInfoEnabled()) {
                                    log.info("TransSuggestManagerImpl.isSkusOnlyContainStaSku end:{},{}", sta.getRefSlipCode(), sta.getSlipCode1());
                                }
                                /** 4.5 计算商品分类，作业单中商品的所在分类（包含所有父分类）只要存在1个分类满足则当前条件成立 **/
                                if (!isSkuCategoriesRef(staLineList, detial)) {
                                    isAllMatching = false;
                                    if (log.isInfoEnabled()) {
                                        log.info("TransSuggestManagerImpl.isSkuCategoriesRef break:{},{}", sta.getRefSlipCode(), sta.getSlipCode1());
                                    }
                                    break;
                                }
                                if (log.isInfoEnabled()) {
                                    log.info("TransSuggestManagerImpl.isSkuCategoriesRef end:{},{}", sta.getRefSlipCode(), sta.getSlipCode1());
                                }
                                /** 4.6 判断是否是COD订单,作业单中的物流信息与默认规则相同时则条件成立 **/
                                if (detial.getIsCod() != null && !di.getIsCod().equals(detial.getIsCod())) {
                                    isAllMatching = false;
                                    if (log.isInfoEnabled()) {
                                        log.info("TransSuggestManagerImpl.detial is cod break:{},{}", sta.getRefSlipCode(), sta.getSlipCode1());
                                    }
                                    break;
                                }
                                if (log.isInfoEnabled()) {
                                    log.info("TransSuggestManagerImpl.detial is cod end:{},{}", sta.getRefSlipCode(), sta.getSlipCode1());
                                }
                                /** 4.7 收货详细地址若包含‘排除关键字’内容，则当前快递规则直接跳过 **/
                                if (isRemovekeyword(detial, di.getAddress())) {
                                    isAllMatching = false;
                                    if (log.isInfoEnabled()) {
                                        log.info("TransSuggestManagerImpl.isRemovekeyword break:{},{}", sta.getRefSlipCode(), sta.getSlipCode1());
                                    }
                                    break;
                                }
                                if (log.isInfoEnabled()) {
                                    log.info("TransSuggestManagerImpl.isRemovekeyword end:{},{}", sta.getRefSlipCode(), sta.getSlipCode1());
                                }
                                /** 4.8 是否存在指定仓库 **/
                                List<Long> whIds = detial.getWhouList();
                                if (whIds == null) {
                                    whIds = getRefWarehouse(detial);
                                }
                                if (whIds.size() > 0 && !isRefWarehouse(sta.getMainWarehouse().getId(), whIds)) {
                                    isAllMatching = false;
                                    if (log.isInfoEnabled()) {
                                        log.info("detial current break:{},{}", sta.getRefSlipCode(), sta.getSlipCode1());
                                    }
                                    break;
                                }
                                // 设置时效类型如果sta.transTimeType为普通，物流信息时效类型设置为头时效
                                di.setTransTimeType(TransTimeType.valueOf(tr.getIntTransTimeType()));
                            } else if (tr.getSort() == TransRole.defaultTransSort) {
                                /** 5 选择否默认快递 **/
                                defaultTrans = tr;
                                continue;
                            } else {
                                isAllMatching = false;
                            }
                            if (log.isInfoEnabled()) {
                                log.info("detial current end:{},{}", sta.getRefSlipCode(), sta.getSlipCode1());
                            }
                            // //////////////////////////////////////////////////////////////////////////////////////////////////////////run////////////////////////
                        }
                        if (log.isInfoEnabled()) {
                            log.info("TransRoleDetialCommand detial for all end,{},{}", sta.getRefSlipCode(), sta.getSlipCode1());
                        }
                        if (isAllMatching) {
                            suggestTrans = tr;
                            if (log.isInfoEnabled()) {
                                log.info("TransRoleDetialCommand isAllMatching,{},{}", sta.getRefSlipCode(), sta.getSlipCode1());
                            }
                            break;
                        }
                    } else {
                        if (di.getTransType() != null && di.getTransTimeType() != null && di.getTransType().getValue() == tr.getIntTransType() && di.getTransTimeType().getValue() == tr.getIntTransTimeType()) {
                            // 当规则中条件为空则默认满足
                            suggestTrans = tr;
                            break;
                        }
                    }
                    // } else if (tr.getSort() == TransRole.defaultTransSort) {
                    // /** 5 选择否默认快递 **/
                    // defaultTrans = tr;
                    // continue;
                    // }
                }
            }
            if (log.isInfoEnabled()) {
                log.info("suggest set 1 end,{},{}", sta.getRefSlipCode(), sta.getSlipCode1());
            }
            if (suggestTrans == null) {
                if (log.isInfoEnabled()) {
                    log.info("suggestTrans is null,{},{}", sta.getRefSlipCode(), sta.getSlipCode1());
                }
                if (defaultTrans != null) {
                    di.setLpCode(defaultTrans.getLpCode());
                    // 当默认快递服务为普通，而作业单需求服务非普通时，则作业单服务类型不做调整。当模块快递服务或时效非普通，如作业单服务和时效为普通时则更新作业单时效与服务
                    if (defaultTrans.getIntTransType() != null && TransType.ORDINARY.equals(di.getTransType()) && TransType.ORDINARY.getValue() != defaultTrans.getIntTransType()) {
                        di.setTransType(TransType.valueOf(defaultTrans.getIntTransType()));
                    }
                    if (defaultTrans.getIntTransTimeType() != null && TransTimeType.ORDINARY.equals(di.getTransTimeType()) && TransTimeType.ORDINARY.getValue() != defaultTrans.getIntTransTimeType()) {
                        di.setTransTimeType(TransTimeType.valueOf(defaultTrans.getIntTransTimeType()));
                    }
                } else if (roleList.size() > 0) {
                    if (log.isInfoEnabled()) {
                        log.info("suggest ems,{},{}", sta.getRefSlipCode(), sta.getSlipCode1());
                    }
                    // 在默认快递也不存在的情况下 选择 EMS
                    di.setLpCode(Transportator.EMS);
                }
                if (log.isInfoEnabled()) {
                    log.info("TransSuggestManagerImpl.suggestTrans:{},{}", sta.getRefSlipCode(), sta.getSlipCode1());
                }
            } else {
                if (log.isInfoEnabled()) {
                    log.info("suggestTrans is not null {},{},{}", suggestTrans.getLpCode(), sta.getRefSlipCode(), sta.getSlipCode1());
                }
                // 匹配到对应的物流商
                di.setLpCode(suggestTrans.getLpCode());
            }
            // staDeliveryInfoDao.save(di);
            if (log.isInfoEnabled()) {
                log.info("TransSuggestManagerImpl.suggestTransService end:" + sta.getRefSlipCode() + "," + sta.getSlipCode1());
            }
        } else {// ///////////////////////////////////////////老的物流推荐
            StockTransApplication sta = staDao.getByPrimaryKey(staId);
            if (sta == null) {
                return;
            }
            // Warehouse wh = warehouseDao.getByOuId(sta.getMainWarehouse().getId());
            // if (!StringUtil.isEmpty(wh.getVmiSource())) {
            // // 不支持外包仓
            // return;
            // }
            if (log.isInfoEnabled()) {
                log.info("TransSuggestManagerImpl.suggestTransService strat:{},{}", sta.getRefSlipCode(), sta.getSlipCode1());
            }
            if (!StockTransApplicationType.OUTBOUND_SALES.equals(sta.getType()) && !StockTransApplicationType.OUTBOUND_RETURN_REQUEST.equals(sta.getType())) {
                throw new BusinessException(ErrorCode.RETURN_REQUEST_STA_TYPE_ERROR, new Object[] {sta.getCode()});
            }
            if (!StockTransApplicationStatus.CREATED.equals(sta.getStatus())) {
                throw new BusinessException(ErrorCode.ERROR_STA_NOT_STATUS);
            }

            StaDeliveryInfo di = sta.getStaDeliveryInfo();
            if (di.getLpCode() != null) {
                return;
            }
            String province = (di.getProvince() == null ? "" : di.getProvince()), city = (di.getCity() == null ? "" : di.getCity()), district = di.getDistrict() == null ? "" : di.getDistrict();
            BigDecimal channlId = biChannelDao.queryChannlIdByOwner(sta.getOwner(), new SingleColumnRowMapper<BigDecimal>());
            List<StaLineCommand> staLineList = staLineDao.findLineSkuBySta(staId, new BeanPropertyRowMapperExt<StaLineCommand>(StaLineCommand.class));
            if (log.isInfoEnabled()) {
                log.info("TransSuggestManagerImpl.getTransRole strat:{},{}", sta.getRefSlipCode(), sta.getSlipCode1());
            }
            List<TransRoleCommand> roleList = getTransRole(sta.getOwner());
            if (log.isInfoEnabled()) {
                log.info("TransSuggestManagerImpl.getTransRole end:{},{}", sta.getRefSlipCode(), sta.getSlipCode1());
            }
            TransRoleCommand suggestTrans = null;
            TransRoleCommand defaultTrans = null;
            boolean isEmsPriority = isEmsPriority(di.getAddress(), channlId.longValue());
            if (log.isInfoEnabled()) {
                log.info("isEmsPriority : {} , {} ,{}", isEmsPriority, sta.getRefSlipCode(), sta.getSlipCode1());
            }
            if (isEmsPriority) {
                if (log.isInfoEnabled()) {
                    log.info("isEmsPriority true , {} ,{}", sta.getRefSlipCode(), sta.getSlipCode1());
                }
                suggestTrans = new TransRoleCommand();
                suggestTrans.setLpCode(Transportator.EMS);
                if (log.isInfoEnabled()) {
                    log.info("TransSuggestManagerImpl.isEmsPriority....end address:" + di.getAddress());
                }
            } else {
                if (log.isInfoEnabled()) {
                    log.info("isEmsPriority false , {} ,{}", sta.getRefSlipCode(), sta.getSlipCode1());
                }
                // 遍历 物流推荐规则
                for (TransRoleCommand tr : roleList) {
                    /** 1 所有推荐快递均需要当前仓库支持的快递（于WarehouseTransRef查询所有仓库支持的快递信息），仓库不支持的快递的服务规则直接忽略。 **/
                    if (!isWarehouseTransRef(sta.getMainWarehouse().getId(), tr)) {
                        if (log.isInfoEnabled()) {
                            log.info("TransRoleCommand isWarehouseTransRef continue, {} ,{}", sta.getRefSlipCode(), sta.getSlipCode1());
                        }
                        continue;
                    } else {}
                    /** 2 校验作业单如是货到付款作业单则推荐时快递服务所属快递必须支持COD **/
                    if (di.getIsCod() && !tr.getIsSupportCod()) {
                        if (log.isInfoEnabled()) {
                            log.info("TransRoleCommand is cod continue, {} ,{}", sta.getRefSlipCode(), sta.getSlipCode1());
                        }
                        continue;
                    }
                    /** 3 根据匹配作业单发货明细staD.tranType、transTimeType一致的快递服务，如一致则校验该服务下的所有规则。 **/
                    if (di.getTransType() != null && di.getTransTimeType() != null && di.getTransType().getValue() == tr.getIntTransType() && di.getTransTimeType().getValue() == tr.getIntTransTimeType()) {
                        if (log.isInfoEnabled()) {
                            log.info("TransRoleCommand trans time type,{},{}", sta.getRefSlipCode(), sta.getSlipCode1());
                        }
                        /** 4 规则明细计算方式：当规则中该条件为空则默认满足，只有当所有条件成立时，则当前规则匹配成功。 **/
                        List<TransRoleDetialCommand> details = tr.getDetials();
                        if (details != null && details.size() > 0) {
                            // 是否全部匹配
                            boolean isAllMatching = true;
                            // 遍历规则明细
                            if (log.isInfoEnabled()) {
                                log.info("TransSuggestManagerImpl.checkdetails strat: {},{}", sta.getRefSlipCode(), sta.getSlipCode1());
                            }
                            for (TransRoleDetialCommand detial : details) {
                                if (log.isInfoEnabled()) {
                                    log.info("detial current start, {} ,{}", sta.getRefSlipCode(), sta.getSlipCode1());
                                }
                                /** 4.0计算订单金额，sta.order_total_actual是否>=minAmount、<=maxAmount **/
                                boolean isMaxAmount = detial.getMaxAmount() == null || (sta.getOrderTotalActual().doubleValue() <= detial.getMaxAmount().doubleValue());
                                boolean isMinAmount = detial.getMinAmount() == null || (sta.getOrderTotalActual().doubleValue() >= detial.getMinAmount().doubleValue());
                                if (!isMaxAmount || !isMinAmount) {
                                    isAllMatching = false;
                                    if (log.isInfoEnabled()) {
                                        log.info("isMaxAmount isMinAmount break, {} ,{}", sta.getRefSlipCode(), sta.getSlipCode1());
                                    }
                                    break;
                                }
                                if (log.isInfoEnabled()) {
                                    log.info("isMaxAmount isMinAmount end, {} ,{}", sta.getRefSlipCode(), sta.getSlipCode1());
                                }
                                /** 4.1 计算订单重量，sta.staDeliveryInfo.wieght是否>=minWeight、<=maxWeight **/
                                boolean isMaxWeight = detial.getMaxWeight() == null || (sta.getStaDeliveryInfo().getWeight().doubleValue() <= detial.getMaxWeight().doubleValue());
                                boolean isMinWeight = detial.getMinWeight() == null || (sta.getStaDeliveryInfo().getWeight().doubleValue() >= detial.getMinWeight().doubleValue());
                                if (!isMaxWeight || !isMinWeight) {
                                    isAllMatching = false;
                                    if (log.isInfoEnabled()) {
                                        log.info("isMaxWeight isMinWeight break, {} ,{}", sta.getRefSlipCode(), sta.getSlipCode1());
                                    }
                                    break;
                                }
                                if (log.isInfoEnabled()) {
                                    log.info("isMaxWeight isMinWeight end, {} ,{}", sta.getRefSlipCode(), sta.getSlipCode1());
                                }
                                /** 4.2 校验配送范围，循环配货范围组中明细以开头模糊匹配校验省市区(如范围组中市为空则不计算市、区，如区为空则不计算区) **/
                                if (!isDistributionRange(province, city, district, detial)) {
                                    isAllMatching = false;
                                    if (log.isInfoEnabled()) {
                                        log.info("isMaxWeight isDistributionRange break, {} ,{}", sta.getRefSlipCode(), sta.getSlipCode1());
                                    }
                                    break;
                                }
                                if (log.isInfoEnabled()) {
                                    log.info("TransSuggestManagerImpl.isDistributionRange end:{},{}", sta.getRefSlipCode(), sta.getSlipCode1());
                                }
                                /** 4.3 计算商品，作业单中存在1个商品满足则当前条件成立 **/
                                List<Long> skuIds = detial.getSkuIds();
                                if (skuIds == null) {
                                    // 获取关联商品
                                    skuIds = getTrdRefSku(detial);
                                }
                                if (!isSkusContainStaSkuAll(skuIds, staLineList)) {
                                    isAllMatching = false;
                                    if (log.isInfoEnabled()) {
                                        log.info("TransSuggestManagerImpl.isSkusContainStaSkuAll break:{},{}", sta.getRefSlipCode(), sta.getSlipCode1());
                                    }
                                    break;
                                }
                                if (log.isInfoEnabled()) {
                                    log.info("TransSuggestManagerImpl.isSkusContainStaSkuAll end:{},{}", sta.getRefSlipCode(), sta.getSlipCode1());
                                }
                                /** 4.4 计算商品标签，判断匹配逻辑 **/
                                List<SkuTagCommand> skuTagRefSku = detial.getSkuTagRefSku();
                                if (skuTagRefSku == null) {
                                    // 所有商品标签(非禁用)所关联的商品
                                    skuTagRefSku = getTrdRefSkuTag(detial);
                                }
                                /** 4.4.1 计算商品标签，作业单中商品只要有1个商品标签满足则当前条件成立 **/
                                if (!isSkusContainStaSku(skuTagRefSku, staLineList)) {
                                    isAllMatching = false;
                                    if (log.isInfoEnabled()) {
                                        log.info("TransSuggestManagerImpl.isSkusContainStaSku break:{},{}", sta.getRefSlipCode(), sta.getSlipCode1());
                                    }
                                    break;
                                }
                                if (log.isInfoEnabled()) {
                                    log.info("TransSuggestManagerImpl.isSkusContainStaSku end:{},{}", sta.getRefSlipCode(), sta.getSlipCode1());
                                }
                                /** 4.4.2 计算商品标签，作业单中商品和商品标签完全匹配则当前条件成立 **/
                                if (!isSkusAllContainStaSku(skuTagRefSku, staLineList)) {
                                    isAllMatching = false;
                                    if (log.isInfoEnabled()) {
                                        log.info("TransSuggestManagerImpl.isSkusAllContainStaSku break:{},{}", sta.getRefSlipCode(), sta.getSlipCode1());
                                    }
                                    break;
                                }
                                if (log.isInfoEnabled()) {
                                    log.info("TransSuggestManagerImpl.isSkusAllContainStaSku end:{},{}", sta.getRefSlipCode(), sta.getSlipCode1());
                                }
                                /** 4.4.3 计算商品标签，订单中的所有商品均存在于维护商品列表则当前条件成立 **/
                                if (!isSkusOnlyContainStaSku(skuTagRefSku, staLineList)) {
                                    isAllMatching = false;
                                    if (log.isInfoEnabled()) {
                                        log.info("TransSuggestManagerImpl.isSkusOnlyContainStaSku break:{},{}", sta.getRefSlipCode(), sta.getSlipCode1());
                                    }
                                    break;
                                }
                                if (log.isInfoEnabled()) {
                                    log.info("TransSuggestManagerImpl.isSkusOnlyContainStaSku end:{},{}", sta.getRefSlipCode(), sta.getSlipCode1());
                                }
                                /** 4.5 计算商品分类，作业单中商品的所在分类（包含所有父分类）只要存在1个分类满足则当前条件成立 **/
                                if (!isSkuCategoriesRef(staLineList, detial)) {
                                    isAllMatching = false;
                                    if (log.isInfoEnabled()) {
                                        log.info("TransSuggestManagerImpl.isSkuCategoriesRef break:{},{}", sta.getRefSlipCode(), sta.getSlipCode1());
                                    }
                                    break;
                                }
                                if (log.isInfoEnabled()) {
                                    log.info("TransSuggestManagerImpl.isSkuCategoriesRef end:{},{}", sta.getRefSlipCode(), sta.getSlipCode1());
                                }
                                /** 4.6 判断是否是COD订单,作业单中的物流信息与默认规则相同时则条件成立 **/
                                if (detial.getIsCod() != null && !di.getIsCod().equals(detial.getIsCod())) {
                                    isAllMatching = false;
                                    if (log.isInfoEnabled()) {
                                        log.info("TransSuggestManagerImpl.detial is cod break:{},{}", sta.getRefSlipCode(), sta.getSlipCode1());
                                    }
                                    break;
                                }
                                if (log.isInfoEnabled()) {
                                    log.info("TransSuggestManagerImpl.detial is cod end:{},{}", sta.getRefSlipCode(), sta.getSlipCode1());
                                }
                                /** 4.7 收货详细地址若包含‘排除关键字’内容，则当前快递规则直接跳过 **/
                                if (isRemovekeyword(detial, di.getAddress())) {
                                    isAllMatching = false;
                                    if (log.isInfoEnabled()) {
                                        log.info("TransSuggestManagerImpl.isRemovekeyword break:{},{}", sta.getRefSlipCode(), sta.getSlipCode1());
                                    }
                                    break;
                                }
                                if (log.isInfoEnabled()) {
                                    log.info("TransSuggestManagerImpl.isRemovekeyword end:{},{}", sta.getRefSlipCode(), sta.getSlipCode1());
                                }
                                /** 4.8 是否存在指定仓库 **/
                                List<Long> whIds = detial.getWhouList();
                                if (whIds == null) {
                                    whIds = getRefWarehouse(detial);
                                }
                                if (whIds.size() > 0 && !isRefWarehouse(sta.getMainWarehouse().getId(), whIds)) {
                                    isAllMatching = false;
                                    if (log.isInfoEnabled()) {
                                        log.info("detial current break:{},{}", sta.getRefSlipCode(), sta.getSlipCode1());
                                    }
                                    break;
                                }
                                if (log.isInfoEnabled()) {
                                    log.info("detial current end:{},{}", sta.getRefSlipCode(), sta.getSlipCode1());
                                }
                            }
                            if (log.isInfoEnabled()) {
                                log.info("TransRoleDetialCommand detial for all end,{},{}", sta.getRefSlipCode(), sta.getSlipCode1());
                            }
                            if (isAllMatching) {
                                suggestTrans = tr;
                                if (log.isInfoEnabled()) {
                                    log.info("TransRoleDetialCommand isAllMatching,{},{}", sta.getRefSlipCode(), sta.getSlipCode1());
                                }
                                break;
                            }
                        } else {
                            // 当规则中条件为空则默认满足
                            suggestTrans = tr;
                            break;
                        }
                    } else if (tr.getSort() == TransRole.defaultTransSort) {
                        /** 5 选择否默认快递 **/
                        defaultTrans = tr;
                        continue;
                    }
                }
            }
            if (log.isInfoEnabled()) {
                log.info("suggest set 1 end,{},{}", sta.getRefSlipCode(), sta.getSlipCode1());
            }
            if (suggestTrans == null) {
                if (log.isInfoEnabled()) {
                    log.info("suggestTrans is null,{},{}", sta.getRefSlipCode(), sta.getSlipCode1());
                }
                if (defaultTrans != null) {
                    di.setLpCode(defaultTrans.getLpCode());
                    // 当默认快递服务为普通，而作业单需求服务非普通时，则作业单服务类型不做调整。当模块快递服务或时效非普通，如作业单服务和时效为普通时则更新作业单时效与服务
                    if (defaultTrans.getIntTransType() != null && TransType.ORDINARY.equals(di.getTransType()) && TransType.ORDINARY.getValue() != defaultTrans.getIntTransType()) {
                        di.setTransType(TransType.valueOf(defaultTrans.getIntTransType()));
                    }
                    if (defaultTrans.getIntTransTimeType() != null && TransTimeType.ORDINARY.equals(di.getTransTimeType()) && TransTimeType.ORDINARY.getValue() != defaultTrans.getIntTransTimeType()) {
                        di.setTransTimeType(TransTimeType.valueOf(defaultTrans.getIntTransTimeType()));
                    }
                } else if (roleList.size() > 0) {
                    if (log.isInfoEnabled()) {
                        log.info("suggest ems,{},{}", sta.getRefSlipCode(), sta.getSlipCode1());
                    }
                    // 在默认快递也不存在的情况下 选择 EMS
                    di.setLpCode(Transportator.EMS);
                }
                if (log.isInfoEnabled()) {
                    log.info("TransSuggestManagerImpl.suggestTrans:{},{}", sta.getRefSlipCode(), sta.getSlipCode1());
                }
            } else {
                if (log.isInfoEnabled()) {
                    log.info("suggestTrans is not null {},{},{}", suggestTrans.getLpCode(), sta.getRefSlipCode(), sta.getSlipCode1());
                }
                // 匹配到对应的物流商
                di.setLpCode(suggestTrans.getLpCode());
            }
            // staDeliveryInfoDao.save(di);
            if (log.isInfoEnabled()) {
                log.info("TransSuggestManagerImpl.suggestTransService end:" + sta.getRefSlipCode() + "," + sta.getSlipCode1());
            }
        }
    }

    /**
     * 判断是否存在指定仓库，如果存在，必须只在指定仓库里面才允许推荐
     * 
     * @param whOuId
     * @param address
     * @return
     */
    private synchronized boolean isRefWarehouse(Long whOuId, List<Long> whouList) {
        for (Long wh : whouList) {
            if (whOuId.equals(wh)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 收货详细地址是否包含‘排除关键字’内容
     * 
     * @param address
     * @return
     */
    private synchronized boolean isRemovekeyword(TransRoleDetialCommand trdCom, String address) {
        if (StringUtil.isEmpty(trdCom.getRemoveKeyword()) || StringUtil.isEmpty(address)) return false;
        List<String> removeKeywords = trdCom.getRemoveKeywords();
        if (removeKeywords == null) {
            removeKeywords = new ArrayList<String>();
            String[] rks = trdCom.getRemoveKeyword().split(",");
            for (String s : rks) {
                if (!StringUtil.isEmpty(s)) {
                    removeKeywords.add(s.toLowerCase());
                }
            }
            trdCom.setRemoveKeywords(removeKeywords);
        }
        for (String keyword : removeKeywords) {
            if (address.toLowerCase().contains(keyword)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 根据店铺获取一定时间内的缓存数据
     * 
     * @param owner
     * @return
     */
    private synchronized List<TransRoleCommand> cacheTransRole(String owner) {
        List<TransRoleCommand> result = transRoleDao.findTransRoleByOwner(owner, new BeanPropertyRowMapperExt<TransRoleCommand>(TransRoleCommand.class));
        for (TransRoleCommand tr : result) {
            // 获取相关联的仓库
            tr.setOuList(ouDao.findWHOUByTrans(tr.getTransId(), new BeanPropertyRowMapperExt<OperationUnit>(OperationUnit.class)));
            // 获取规则明细
            tr.setDetials(transRoleDetialDao.findDetialsByTr(tr.getId(), new BeanPropertyRowMapperExt<TransRoleDetialCommand>(TransRoleDetialCommand.class)));
        }
        ChooseOption co = chooseOptionDao.findByCategoryCodeAndKey(Constants.TRANS_SUGGES_TIME_CATEGORY_CODE, Constants.TRANS_SUGGES_TIME_OPTION_KEY);
        if (co == null) {
            transRoleCache.put(owner, result);
        } else {
            transRoleCache.put(owner, result, Long.valueOf(co.getOptionValue()) * 60 * 1000);
        }
        return result;
    }

    /**
     * 获取配送范围组明细
     * 
     * @param trd
     * @return
     */
    private synchronized List<TransAreaGroupDetial> getAreaDetials(TransRoleDetialCommand trd) {
        List<TransAreaGroupDetial> result = null;
        if (trd != null) {
            result = trd.getAreaDetials();
            if (result == null) {
                result = tagdDao.findDetialsByTag(trd.getTagId(), new BeanPropertyRowMapperExt<TransAreaGroupDetial>(TransAreaGroupDetial.class));
                trd.setAreaDetials(result);
            }
        }
        return result;
    }

    /**
     * 获取配送范围组明细
     * 
     * @param trd
     * @return
     */
    private synchronized List<Long> getRefWarehouse(TransRoleDetialCommand trd) {
        List<Long> result = null;
        if (trd != null) {
            result = trd.getWhouList();
            if (result == null) {
                result = transRoleDetialDao.findRefWh(trd.getId(), new SingleColumnRowMapper<Long>(Long.class));
                trd.setWhouList(result);
            }
        }
        return result;
    }


    /**
     * 获取物流推荐规则明细所关联商品
     * 
     * @param trd
     * @return
     */
    private synchronized List<Long> getTrdRefSku(TransRoleDetialCommand trd) {
        List<Long> result = null;
        if (trd != null) {
            result = trd.getSkuIds();
            if (result == null) {
                result = skuDao.findTrdRefSku(trd.getId(), new SingleColumnRowMapper<Long>(Long.class));
                trd.setSkuIds(result);
            }
        }
        return result;
    }

    /**
     * 获取物流推荐规则明细所关联商品标签
     * 
     * @param trd
     * @return
     */
    private synchronized List<SkuTagCommand> getTrdRefSkuTag(TransRoleDetialCommand trd) {
        List<SkuTagCommand> result = null;
        if (trd != null) {
            result = trd.getSkuTagRefSku();
            if (result == null) {
                result = skuTagDao.findSkuTagRefSkuBytrDId(trd.getId(), new BeanPropertyRowMapperExt<SkuTagCommand>(SkuTagCommand.class));
                trd.setSkuTagRefSku(result);
            }
        }
        return result;
    }

    /**
     * 获取物流推荐规则明细所关联商品
     * 
     * @param trd
     * @return
     */
    private synchronized List<Long> getTrdRefSkuCategories(TransRoleDetialCommand trd) {
        List<Long> result = null;
        if (trd != null) {
            result = trd.getCategories();
            if (result == null) {
                result = skuCategoriesDao.findSkuCategoriesBytrDId(trd.getId(), new SingleColumnRowMapper<Long>(Long.class));
                trd.setCategories(result);
            }
        }
        return result;
    }

    /**
     * 仓库是否和物流绑定关系
     * 
     * @param whOuId
     * @param tr
     * @return
     */
    private boolean isWarehouseTransRef(Long whOuId, TransRoleCommand tr) {
        for (OperationUnit ou : tr.getOuList()) {
            if (ou.getId().equals(whOuId)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断送货地点 是否 在配送范围内
     * 
     * @param province 送货地点 省
     * @param city 送货地点 市
     * @param district 送货地点 区
     * @param trDetial 规则明细
     * @return
     */
    private boolean isDistributionRange(String province, String city, String district, TransRoleDetialCommand trDetial) {
        List<TransAreaGroupDetial> areaDetials = trDetial.getAreaDetials();
        if (areaDetials == null) {
            areaDetials = getAreaDetials(trDetial);
        }
        // 如果不存在配送范围组 说明对配送范围没有限制
        if (areaDetials.size() > 0) {
            // 遍历配送范围
            for (TransAreaGroupDetial tagDetial : areaDetials) {
                String tempProvince = tagDetial.getProvince();
                String tempCity = tagDetial.getCity();
                // 派送范围匹配规则模糊匹配
                // (规则：维护数据省.like ‘送货省%’, 维护数据市.like ‘送货市%’, 维护数据区.like送货区%’)
                boolean isProvince = tempProvince != null && tempProvince.toLowerCase().indexOf(province.toLowerCase()) == 0;
                boolean isCity = tempCity != null && tempCity.toLowerCase().indexOf(city.toLowerCase()) == 0;
                boolean isDistrictNull = tagDetial.getDistrict() == null;
                // 省市为空 没有限制省份
                if ((tempProvince == null)
                // 同省 城市没有限制
                        || (isProvince && tempCity == null)
                        // 同省 同市 区没有限制
                        || (isProvince && isCity && isDistrictNull)
                        // 同省 同市 同区
                        || (isProvince && isCity && !isDistrictNull && tagDetial.getDistrict().toLowerCase().indexOf(district.toLowerCase()) == 0)) {
                    return true;
                }
            }
        } else {
            return true;
        }
        return false;
    }


    /**
     * 检验 staLine 里面的商品包含在 skus 中(完全包含所有商品)
     * 
     * @param skus
     * @param lineList
     * @return
     */
    private boolean isSkusContainStaSkuAll(List<Long> skus, List<StaLineCommand> lineList) {
        if (skus != null && skus.size() > 0) {
            boolean isContainAll = true;
            for (StaLineCommand l : lineList) {
                boolean isContain = false;
                for (Long id : skus) {
                    if (l.getSkuId().equals(id)) {
                        isContain = true;
                        break;
                    }
                }
                if (!isContain) {
                    isContainAll = false;
                    break;
                }
            }
            return isContainAll;
        } else {
            // 不存在 说明没有商品限制
            return true;
        }
    }

    /**
     * 检验 staLine 里面的商品包含在 skus 中(任意一件商品)
     * 
     * @param skus
     * @param lineList
     * @return
     */
    private boolean isSkusContainStaSku(List<SkuTagCommand> skus, List<StaLineCommand> lineList) {
        boolean isCheck = false;
        if (skus != null && skus.size() > 0) {
            for (StaLineCommand l : lineList) {
                for (SkuTagCommand ids : skus) {
                    if (ids.getType() == SkuTagType.ANY_MATCHING) {
                        isCheck = true;
                        if (l.getSkuId().equals(ids.getSkuId())) {
                            return true;
                        }
                    }
                }
            }
            if (!isCheck) {// 不存在 说明没有商品限制
                return true;
            }
        } else {
            // 不存在 说明没有商品限制
            return true;
        }
        return false;
    }

    /**
     * 检验 staLine 里面的商品和 skus 中商品（完全匹配）
     * 
     * @param skus
     * @param lineList
     * @return
     */
    private boolean isSkusAllContainStaSku(List<SkuTagCommand> skus, List<StaLineCommand> lineList) {
        Map<Long, Long> map1 = new HashMap<Long, Long>();// 存放商品标签ID
        Map<Long, Long> map2 = new HashMap<Long, Long>();// 存放staLine商品ID
        boolean isCheck = false;
        if (skus != null && skus.size() > 0) {
            for (StaLineCommand l : lineList) {
                map2.put(l.getSkuId(), l.getId());
            }
            for (SkuTagCommand tag : skus) {
                if (tag.getType() == SkuTagType.COMPLETE_MATCHING) {
                    isCheck = true;
                    map1.put(tag.getSkuId(), tag.getSkuId());// 存放商品标签ID
                    if (map2.containsKey(tag.getSkuId())) {// 包含则各自删除
                        map1.remove(tag.getSkuId());
                        map2.remove(tag.getSkuId());
                    }
                }
            }
            if (!isCheck) {// 不存在 说明没有商品限制
                return true;
            }
            if (map1.size() == 0 && map2.size() == 0) {// size都为0则表示staLine 里面的商品和 商品标签中的商品完全匹配
                return true;
            }
        } else {
            // 不存在 说明没有商品限制
            return true;
        }
        return false;
    }

    /**
     * 检验 staLine 里面的商品均存在于商品列表（包含匹配）
     * 
     * @param skus
     * @param lineList
     * @return
     */
    private boolean isSkusOnlyContainStaSku(List<SkuTagCommand> skus, List<StaLineCommand> lineList) {
        Map<Long, Long> map2 = new HashMap<Long, Long>();// 存放staLine商品ID
        boolean isCheck = false;
        if (skus != null && skus.size() > 0) {
            for (StaLineCommand l : lineList) {
                map2.put(l.getSkuId(), l.getId());
            }
            for (SkuTagCommand tag : skus) {
                if (tag.getType() == SkuTagType.CONTAIN_MATCHING) {
                    isCheck = true;
                    if (map2.containsKey(tag.getSkuId())) {// 包含则删除
                        map2.remove(tag.getSkuId());
                    }
                }
            }
            if (!isCheck) {// 不存在 说明没有商品限制
                return true;
            }
            if (map2.size() == 0) {// size为0则是表示staLine 里面的商品均存在于商品列表
                return true;
            }
        } else {
            // 不存在 说明没有商品限制
            return true;
        }
        return false;
    }

    /**
     * 判断 作业单上商品的分类 是否 有规则明细里面限制的商品分类
     * 
     * @param lineList 作业明细数据
     * @param trDetial 规则明细
     * @return
     */
    private boolean isSkuCategoriesRef(List<StaLineCommand> lineList, TransRoleDetialCommand trDetial) {
        List<Long> categoriesList = trDetial.getCategories();
        if (categoriesList == null) {
            categoriesList = getTrdRefSkuCategories(trDetial);
        }
        // 商品分类限制没有数据 说明不做任何限制
        if (categoriesList.size() > 0) {
            for (StaLineCommand l : lineList) {
                // 匹配是否存在相同的商品分类
                for (Long id : categoriesList) {
                    if (l.getCategoriesId() != null && l.getCategoriesId().equals(id)) {
                        return true;
                    }
                }
            }
        } else {
            // 没有分类限制
            return true;
        }
        return false;
    }

    /**
     * 判断详细地址是否包含关键字且不包含排除关键字
     * 
     * @param address
     * @return
     */
    public boolean isEmsPriority(String address, Long channlId) {
        boolean msg = false;
        if (StringUtil.isEmpty(address)) {
            return false;
        }
        String md5Address = "";
        try {
            md5Address = Md5Util.getMD5Str(address);
        } catch (Exception e) {

        }
        if (log.isInfoEnabled()) {
            log.info("TransSuggestManagerImpl.isEmsPriority....start address:{},md5{}", address, md5Address);
        }
        // 关键字集合
        List<TransPrioritySelection> result = keyWordCache.get(channlId);
        // 缓存中的数据不存在或者已过期
        if (result == null) {
            List<TransPrioritySelection> transSel = transPrioritySelectionDao.findTrasPriSel(channlId, new BeanPropertyRowMapperExt<TransPrioritySelection>(TransPrioritySelection.class));
            if (null != transSel && transSel.size() > 0) {
                keyWordCache.put(channlId, transSel, Long.valueOf(10) * 60 * 1000);
                if (log.isInfoEnabled()) {
                    log.info("ems get key word init  ,address :{}", md5Address);
                }
            } else {
                if (log.isInfoEnabled()) {
                    log.info("ems get key word return false  ,address :{}", md5Address);
                }
                return false;
            }
        }
        result = keyWordCache.get(channlId);
        if (log.isInfoEnabled()) {
            log.info("ems get key word end ,address :{}", md5Address);
        }
        for (TransPrioritySelection keyWord : result) {
            if (null != keyWord.getKeyword() && !"".equals(keyWord.getKeyword())) {
                List<String> includeMap = new ArrayList<String>();
                List<String> excludeMap = new ArrayList<String>();
                boolean isInclude = false;
                boolean exInclude = true;
                int position = keyWord.getKeyword().indexOf("|");
                if (0 == position) {
                    String[] keyWordList = keyWord.getKeyword().trim().split("\\|");
                    for (String keylist : keyWordList) {
                        if (null != keylist && !"".equals(keylist)) {
                            if (keylist.indexOf(",") > -1) {
                                String[] key = keylist.split("\\,");
                                for (String keyList : key) {
                                    excludeMap.add(keyList);
                                }
                            } else {
                                excludeMap.add(keylist);
                            }
                        }

                    }
                } else {
                    String[] keyWordList = keyWord.getKeyword().split("\\|");
                    if (keyWordList.length > 1) {
                        if (null != keyWordList[0] && !"".equals(keyWordList[0])) {
                            if (keyWordList[0].indexOf(",") > 0) {
                                String[] keywords = keyWordList[0].split("\\,");
                                for (String keyword : keywords) {
                                    includeMap.add(keyword.trim());
                                }
                            } else {
                                includeMap.add(keyWordList[0]);
                            }
                        }
                        if (null != keyWordList[1] && !"".equals(keyWordList[1])) {
                            if (keyWordList[1].indexOf(",") > 0) {
                                String[] keywords = keyWordList[1].split("\\,");
                                for (String keyword : keywords) {
                                    excludeMap.add(keyword.trim());
                                }
                            } else {
                                excludeMap.add(keyWordList[1]);
                            }
                        }
                    } else {
                        if (keyWordList[0].indexOf(",") > -1) {
                            String[] keyList = keyWordList[0].split("\\,");
                            for (String key : keyList) {
                                includeMap.add(key);
                            }
                        } else {
                            includeMap.add(keyWordList[0]);
                        }
                    }
                }
                for (String include : includeMap) {
                    if (address.toLowerCase().contains(include)) {
                        isInclude = true;
                    }
                }
                for (String exclude : excludeMap) {
                    if (address.toLowerCase().contains(exclude)) {
                        exInclude = false;
                    }
                }
                if (isInclude && exInclude) {
                    msg = true;
                    break;
                }
            }
        }
        if (log.isInfoEnabled()) {
            log.info("ems for end ,address :{}", md5Address);
        }
        return msg;
    }

    /**
     * 指定仓库、渠道、物流逻辑<br/>
     * 首先：物流推荐规则，一条规则一条明细完全匹配，并且对应仓库、物流有绑定关系即完成推荐<br/>
     * 本次校验逻辑：<br/>
     * 1、订单仓库不强制校验过仓必须有物流商(如某些外包仓，具体见仓库标识),直接反馈校验通过<br/>
     * 2、如果订单地址EMS优先，且指定物流商为EMS，直接反馈校验通过<br/>
     * 3、通用校验逻辑，存在一条规则对应的物流商跟订单仓库有绑定关系，并且存在一条明细当前订单完全匹配，则校验通过，反之校验不通过<br/>
     */
    @Override
    public Boolean isCanUseLp(Long id) {
        ChooseOption co = chooseOptionDao.findByCategoryCodeAndKey("SUGGEST_TRANS_SWITCH_ZHI", "SWITCH");
        if (co != null) {// //////////////////////////////////////////////////////新的推荐逻辑
            Boolean flag = false;
            WmsSalesOrderQueue queue = wmsSalesOrderQueueDao.getByPrimaryKey(id);
            OperationUnit unit = ouDao.getByCode(queue.getWarehouseCode());
            Warehouse w = warehouseDao.getByOuId(unit.getId());
            // 如果仓库不强制过仓必须有物流,物流不用做校验
            if (w.getIsTransMust() == null || !w.getIsTransMust()) {
                return true;
            } else {
                BigDecimal channlId = biChannelDao.queryChannlIdByOwner(queue.getOwner(), new SingleColumnRowMapper<BigDecimal>());
                boolean isEmsPriority = isEmsPriority(queue.getAddress(), channlId.longValue());
                // 如果过仓指定EMS,且地址匹配EMS优先，直接反馈验证成功
                if (queue.getTransCode().equals(Transportator.EMS) && isEmsPriority) {
                    return true;
                }

            }
            String province = (queue.getProvince() == null ? "" : queue.getProvince()), city = (queue.getCity() == null ? "" : queue.getCity()), district = queue.getDistrict() == null ? "" : queue.getDistrict();
            List<StaLineCommand> staLineList = staLineDao.findLineSkuBySta(1L, new BeanPropertyRowMapperExt<StaLineCommand>(StaLineCommand.class));
            List<TransRoleCommand> roleList = getTransRole(queue.getOwner());
            // 遍历 物流推荐规则
            for (TransRoleCommand tr : roleList) {
                Transportator t = transportatorDao.getByPrimaryKey(tr.getTransId());
                if (!t.getCode().equals(queue.getTransCode())) {
                    continue;
                }
                /** 1 所有推荐快递均需要当前仓库支持的快递（于WarehouseTransRef查询所有仓库支持的快递信息），仓库不支持的快递的服务规则直接忽略。 **/
                if (!isWarehouseTransRef(unit.getId(), tr)) {
                    continue;
                }
                /** 2 校验作业单如是货到付款作业单则推荐时快递服务所属快递必须支持COD **/
                if (queue.isCod() && !tr.getIsSupportCod()) {
                    continue;
                }
                /** 3 根据匹配作业单发货明细staD.tranType、transTimeType一致的快递服务，如一致则校验该服务下的所有规则。 **/
                // if (queue.getTransType() == tr.getIntTransType() && queue.getTransTimeType() ==
                // tr.getIntTransTimeType()) {
                /** 4 规则明细计算方式：当规则中该条件为空则默认满足，只有当所有条件成立时，则当前规则匹配成功。 **/
                List<TransRoleDetialCommand> details = tr.getDetials();
                if (details != null && details.size() > 0) {
                    // 是否全部匹配
                    boolean isAllMatching = true;
                    // 遍历规则明细
                    for (TransRoleDetialCommand detial : details) {
                        /**
                         * 4.00 根据匹配作业单发货明细staD.tranType（
                         * 运送方式(快递附加服务)）、transTimeType一致的快递服务，如一致则校验该服务下的所有规则。
                         **/
                        if (queue.getTransType() == tr.getIntTransType() && (queue.getTransTimeType() == (detial.getTimeType() == null ? -1 : detial.getTimeType()) || queue.getTransTimeType() == 1)) {

                            /** 4.0计算订单金额，sta.order_total_actual是否>=minAmount、<=maxAmount **/
                            boolean isMaxAmount = detial.getMaxAmount() == null || (queue.getOrderAmt().doubleValue() <= detial.getMaxAmount().doubleValue());
                            boolean isMinAmount = detial.getMinAmount() == null || (queue.getOrderAmt().doubleValue() >= detial.getMinAmount().doubleValue());
                            if (!isMaxAmount || !isMinAmount) {
                                isAllMatching = false;
                                break;
                            }
                            /** 4.2 校验配送范围，循环配货范围组中明细以开头模糊匹配校验省市区(如范围组中市为空则不计算市、区，如区为空则不计算区) **/
                            if (!isDistributionRange(province, city, district, detial)) {
                                isAllMatching = false;
                                break;
                            }
                            /** 4.3 计算商品，作业单中存在1个商品满足则当前条件成立 **/
                            List<Long> skuIds = detial.getSkuIds();
                            if (skuIds == null) {
                                // 获取关联商品
                                skuIds = getTrdRefSku(detial);
                            }
                            if (!isSkusContainStaSkuAll(skuIds, staLineList)) {
                                isAllMatching = false;
                                break;
                            }
                            /** 4.4 计算商品标签，判断匹配逻辑 **/
                            List<SkuTagCommand> skuTagRefSku = detial.getSkuTagRefSku();
                            if (skuTagRefSku == null) {
                                // 所有商品标签(非禁用)所关联的商品
                                skuTagRefSku = getTrdRefSkuTag(detial);
                            }
                            /** 4.4.1 计算商品标签，作业单中商品只要有1个商品标签满足则当前条件成立 **/
                            if (!isSkusContainStaSku(skuTagRefSku, staLineList)) {
                                isAllMatching = false;
                                break;
                            }
                            /** 4.4.2 计算商品标签，作业单中商品和商品标签完全匹配则当前条件成立 **/
                            if (!isSkusAllContainStaSku(skuTagRefSku, staLineList)) {
                                isAllMatching = false;
                                break;
                            }
                            /** 4.4.3 计算商品标签，订单中的所有商品均存在于维护商品列表则当前条件成立 **/
                            if (!isSkusOnlyContainStaSku(skuTagRefSku, staLineList)) {
                                isAllMatching = false;
                                break;
                            }
                            /** 4.5 计算商品分类，作业单中商品的所在分类（包含所有父分类）只要存在1个分类满足则当前条件成立 **/
                            if (!isSkuCategoriesRef(staLineList, detial)) {
                                isAllMatching = false;
                                break;
                            }
                            /** 4.6 判断是否是COD订单,作业单中的物流信息与默认规则相同时则条件成立 **/
                            if (detial.getIsCod() != null && !queue.isCod() == ((detial.getIsCod() == null) ? false : (detial.getIsCod()))) {
                                isAllMatching = false;
                                break;
                            }
                            /** 4.7 收货详细地址若包含‘排除关键字’内容，则当前快递规则直接跳过 **/
                            if (isRemovekeyword(detial, queue.getAddress())) {
                                isAllMatching = false;
                                break;
                            }
                            /** 4.8 是否存在指定仓库 **/
                            List<Long> whIds = detial.getWhouList();
                            if (whIds == null) {
                                whIds = getRefWarehouse(detial);
                            }
                            if (whIds.size() > 0 && !isRefWarehouse(unit.getId(), whIds)) {
                                isAllMatching = false;
                                break;
                            }
                        } else if (tr.getSort() == TransRole.defaultTransSort) {
                            flag = true;
                            return flag;
                        }
                    }
                    if (isAllMatching) {
                        flag = true;
                        return flag;
                    }
                } else {
                    if (queue.getTransType() == tr.getIntTransType() && queue.getTransTimeType() == tr.getIntTransTimeType()) {
                        flag = true;
                        return flag;
                    }
                }
                // } else if (tr.getSort() == TransRole.defaultTransSort) {
                // flag = true;
                // return flag;
                // }
            }
            return flag;
        } else {// ////////老的物流推荐逻辑
            Boolean flag = false;
            WmsSalesOrderQueue queue = wmsSalesOrderQueueDao.getByPrimaryKey(id);
            OperationUnit unit = ouDao.getByCode(queue.getWarehouseCode());
            Warehouse w = warehouseDao.getByOuId(unit.getId());
            // 如果仓库不强制过仓必须有物流,物流不用做校验
            if (w.getIsTransMust() == null || !w.getIsTransMust()) {
                return true;
            } else {
                BigDecimal channlId = biChannelDao.queryChannlIdByOwner(queue.getOwner(), new SingleColumnRowMapper<BigDecimal>());
                boolean isEmsPriority = isEmsPriority(queue.getAddress(), channlId.longValue());
                // 如果过仓指定EMS,且地址匹配EMS优先，直接反馈验证成功
                if (queue.getTransCode().equals(Transportator.EMS) && isEmsPriority) {
                    return true;
                }

            }
            String province = (queue.getProvince() == null ? "" : queue.getProvince()), city = (queue.getCity() == null ? "" : queue.getCity()), district = queue.getDistrict() == null ? "" : queue.getDistrict();
            List<StaLineCommand> staLineList = staLineDao.findLineSkuBySta(1L, new BeanPropertyRowMapperExt<StaLineCommand>(StaLineCommand.class));
            List<TransRoleCommand> roleList = getTransRole(queue.getOwner());
            // 遍历 物流推荐规则
            for (TransRoleCommand tr : roleList) {
                Transportator t = transportatorDao.getByPrimaryKey(tr.getTransId());
                if (!t.getCode().equals(queue.getTransCode())) {
                    continue;
                }
                /** 1 所有推荐快递均需要当前仓库支持的快递（于WarehouseTransRef查询所有仓库支持的快递信息），仓库不支持的快递的服务规则直接忽略。 **/
                if (!isWarehouseTransRef(unit.getId(), tr)) {
                    continue;
                }
                /** 2 校验作业单如是货到付款作业单则推荐时快递服务所属快递必须支持COD **/
                if (queue.isCod() && !tr.getIsSupportCod()) {
                    continue;
                }
                /** 3 根据匹配作业单发货明细staD.tranType、transTimeType一致的快递服务，如一致则校验该服务下的所有规则。 **/
                if (queue.getTransType() == tr.getIntTransType() && queue.getTransTimeType() == tr.getIntTransTimeType()) {
                    /** 4 规则明细计算方式：当规则中该条件为空则默认满足，只有当所有条件成立时，则当前规则匹配成功。 **/
                    List<TransRoleDetialCommand> details = tr.getDetials();
                    if (details != null && details.size() > 0) {
                        // 是否全部匹配
                        boolean isAllMatching = true;
                        // 遍历规则明细
                        for (TransRoleDetialCommand detial : details) {
                            /** 4.0计算订单金额，sta.order_total_actual是否>=minAmount、<=maxAmount **/
                            boolean isMaxAmount = detial.getMaxAmount() == null || (queue.getOrderAmt().doubleValue() <= detial.getMaxAmount().doubleValue());
                            boolean isMinAmount = detial.getMinAmount() == null || (queue.getOrderAmt().doubleValue() >= detial.getMinAmount().doubleValue());
                            if (!isMaxAmount || !isMinAmount) {
                                isAllMatching = false;
                                break;
                            }
                            /** 4.2 校验配送范围，循环配货范围组中明细以开头模糊匹配校验省市区(如范围组中市为空则不计算市、区，如区为空则不计算区) **/
                            if (!isDistributionRange(province, city, district, detial)) {
                                isAllMatching = false;
                                break;
                            }
                            /** 4.3 计算商品，作业单中存在1个商品满足则当前条件成立 **/
                            List<Long> skuIds = detial.getSkuIds();
                            if (skuIds == null) {
                                // 获取关联商品
                                skuIds = getTrdRefSku(detial);
                            }
                            if (!isSkusContainStaSkuAll(skuIds, staLineList)) {
                                isAllMatching = false;
                                break;
                            }
                            /** 4.4 计算商品标签，判断匹配逻辑 **/
                            List<SkuTagCommand> skuTagRefSku = detial.getSkuTagRefSku();
                            if (skuTagRefSku == null) {
                                // 所有商品标签(非禁用)所关联的商品
                                skuTagRefSku = getTrdRefSkuTag(detial);
                            }
                            /** 4.4.1 计算商品标签，作业单中商品只要有1个商品标签满足则当前条件成立 **/
                            if (!isSkusContainStaSku(skuTagRefSku, staLineList)) {
                                isAllMatching = false;
                                break;
                            }
                            /** 4.4.2 计算商品标签，作业单中商品和商品标签完全匹配则当前条件成立 **/
                            if (!isSkusAllContainStaSku(skuTagRefSku, staLineList)) {
                                isAllMatching = false;
                                break;
                            }
                            /** 4.4.3 计算商品标签，订单中的所有商品均存在于维护商品列表则当前条件成立 **/
                            if (!isSkusOnlyContainStaSku(skuTagRefSku, staLineList)) {
                                isAllMatching = false;
                                break;
                            }
                            /** 4.5 计算商品分类，作业单中商品的所在分类（包含所有父分类）只要存在1个分类满足则当前条件成立 **/
                            if (!isSkuCategoriesRef(staLineList, detial)) {
                                isAllMatching = false;
                                break;
                            }
                            /** 4.6 判断是否是COD订单,作业单中的物流信息与默认规则相同时则条件成立 **/
                            if (detial.getIsCod() != null && !queue.isCod() == ((detial.getIsCod() == null) ? false : (detial.getIsCod()))) {
                                isAllMatching = false;
                                break;
                            }
                            /** 4.7 收货详细地址若包含‘排除关键字’内容，则当前快递规则直接跳过 **/
                            if (isRemovekeyword(detial, queue.getAddress())) {
                                isAllMatching = false;
                                break;
                            }
                            /** 4.8 是否存在指定仓库 **/
                            List<Long> whIds = detial.getWhouList();
                            if (whIds == null) {
                                whIds = getRefWarehouse(detial);
                            }
                            if (whIds.size() > 0 && !isRefWarehouse(unit.getId(), whIds)) {
                                isAllMatching = false;
                                break;
                            }
                        }
                        if (isAllMatching) {
                            flag = true;
                            return flag;
                        }
                    } else {
                        flag = true;
                        return flag;
                    }
                } else if (tr.getSort() == TransRole.defaultTransSort) {
                    flag = true;
                    return flag;
                }
            }
            return flag;
        }
    }

    /**
     * 如果订单指定物流商，则反馈该物流商绑定的仓库，如果指定仓库，则为当前仓，如果未指定，为绑定的所有仓<br/>
     * 根据订单获取推荐物流商 1、如果地址匹配EMS优先，添加EMS至推荐列表 2、继续匹配列表 <br/>
     * 根据订单匹配推荐物流，同时匹配物流对应仓库
     */
    @Override
    public Map<Long, String> suggestTransServiceForOrder(Long id) {
        ChooseOption co = chooseOptionDao.findByCategoryCodeAndKey("SUGGEST_TRANS_SWITCH_ZHI", "SWITCH");
        if (co != null) {// //////////////////////////////////////////////////////新的推荐逻辑
            // 存放仓库和匹配的物流商，按照优先级顺序来
            Map<Long, String> ouLp = new HashMap<Long, String>();
            WmsSalesOrderQueue queue = wmsSalesOrderQueueDao.getByPrimaryKey(id);
            String infoMsg = "suggestTransServiceForOrder-Order:" + queue.getOrderCode();
            Long ouId = null;
            String wcode = queue.getWarehouseCode();
            if (wcode != null) {
                OperationUnit unit = ouDao.getByCode(wcode);
                ouId = unit.getId();
            }
            String lpCode = queue.getTransCode();
            if (null != lpCode) {// 指定物流，则只用根据物流匹配仓库
                setOuLpByLpCode(ouLp, lpCode, ouId);
                return ouLp;
            }
            String province = (queue.getProvince() == null ? "" : queue.getProvince()), city = (queue.getCity() == null ? "" : queue.getCity()), district = queue.getDistrict() == null ? "" : queue.getDistrict();
            List<StaLineCommand> staLineList = staLineDao.findLineSkuByOrderId(id, new BeanPropertyRowMapperExt<StaLineCommand>(StaLineCommand.class));
            List<TransRoleCommand> roleList = getTransRole(queue.getOwner());
            log.debug(infoMsg + "-roleList:" + roleList.size());
            BigDecimal channlId = biChannelDao.queryChannlIdByOwner(queue.getOwner(), new SingleColumnRowMapper<BigDecimal>());
            boolean isEmsPriority = isEmsPriority(queue.getAddress(), channlId.longValue());
            log.debug(infoMsg + "-isEmsPriority:" + isEmsPriority);
            if (isEmsPriority) {
                /**
                 * 如果地址默认EMS优先，且单据没有指定快递，则EMS优先，所有仓库都可以发
                 */
                setOuLpByLpCode(ouLp, Transportator.EMS, ouId);
            }
            /**
             * 如果快递非EMS优先，则后续推荐快递逻辑，
             */
            // 遍历 物流推荐规则
            for (TransRoleCommand tr : roleList) {
                /** 1 所有推荐快递均需要当前仓库支持的快递（于WarehouseTransRef查询所有仓库支持的快递信息），仓库不支持的快递的服务规则直接忽略。 **/
                // 如果订单指定仓库，但是当前规则不支持对应仓库，跳过该规则
                if (ouId != null) {
                    if (!isWarehouseTransRef(ouId, tr)) {
                        continue;
                    }
                }
                /** 2 校验作业单如是货到付款作业单则推荐时快递服务所属快递必须支持COD **/
                // 如果订单为cod订单，但是当前规则不支持cod业务，跳过该规则
                if (queue.isCod() && (tr.getIsSupportCod() == null || !tr.getIsSupportCod())) {
                    continue;
                }
                log.debug(infoMsg + "-TR:" + tr.getId() + "-TransTypeInfo:" + queue.getTransType() + "|" + queue.getTransTimeType() + "|" + tr.getIntTransType() + "|" + tr.getIntTransTimeType());
                /** 3 根据匹配作业单发货明细staD.tranType、transTimeType一致的快递服务，如一致则校验该服务下的所有规则。 **/
                // if (queue.getTransType() == tr.getIntTransType() && queue.getTransTimeType() ==
                // tr.getIntTransTimeType()) {
                log.debug(infoMsg + "-TR is IN:" + tr.getId());
                /** 4 规则明细计算方式：当规则中该条件为空则默认满足，只有当所有条件成立时，则当前规则匹配成功。 **/
                List<TransRoleDetialCommand> details = tr.getDetials();
                if (details != null && details.size() > 0) {
                    // 是否全部匹配
                    boolean isAllMatching = true;
                    // 遍历规则明细
                    for (TransRoleDetialCommand detial : details) {
                        /**
                         * 4.00 根据匹配作业单发货明细staD.tranType（
                         * 运送方式(快递附加服务)）、transTimeType一致的快递服务，如一致则校验该服务下的所有规则。
                         **/
                        if (queue.getTransType() == tr.getIntTransType() && (queue.getTransTimeType() == (detial.getTimeType() == null ? -1 : detial.getTimeType()) || queue.getTransTimeType() == 1)) {
                            /** 4.0计算订单金额，sta.order_total_actual是否>=minAmount、<=maxAmount **/
                            boolean isMaxAmount = detial.getMaxAmount() == null || (queue.getOrderAmt().doubleValue() <= detial.getMaxAmount().doubleValue());
                            boolean isMinAmount = detial.getMinAmount() == null || (queue.getOrderAmt().doubleValue() >= detial.getMinAmount().doubleValue());
                            if (!isMaxAmount || !isMinAmount) {
                                isAllMatching = false;
                                break;
                            }
                            /** 4.2 校验配送范围，循环配货范围组中明细以开头模糊匹配校验省市区(如范围组中市为空则不计算市、区，如区为空则不计算区) **/
                            if (!isDistributionRange(province, city, district, detial)) {
                                isAllMatching = false;
                                break;
                            }
                            /** 4.3 计算商品，作业单中存在1个商品满足则当前条件成立 **/
                            List<Long> skuIds = detial.getSkuIds();
                            if (skuIds == null) {
                                // 获取关联商品
                                skuIds = getTrdRefSku(detial);
                            }
                            if (!isSkusContainStaSkuAll(skuIds, staLineList)) {
                                isAllMatching = false;
                                break;
                            }
                            /** 4.4 计算商品标签，判断匹配逻辑 **/
                            List<SkuTagCommand> skuTagRefSku = detial.getSkuTagRefSku();
                            if (skuTagRefSku == null) {
                                // 所有商品标签(非禁用)所关联的商品
                                skuTagRefSku = getTrdRefSkuTag(detial);
                            }
                            /** 4.4.1 计算商品标签，作业单中商品只要有1个商品标签满足则当前条件成立 **/
                            if (!isSkusContainStaSku(skuTagRefSku, staLineList)) {
                                isAllMatching = false;
                                break;
                            }
                            /** 4.4.2 计算商品标签，作业单中商品和商品标签完全匹配则当前条件成立 **/
                            if (!isSkusAllContainStaSku(skuTagRefSku, staLineList)) {
                                isAllMatching = false;
                                break;
                            }
                            /** 4.4.3 计算商品标签，订单中的所有商品均存在于维护商品列表则当前条件成立 **/
                            if (!isSkusOnlyContainStaSku(skuTagRefSku, staLineList)) {
                                isAllMatching = false;
                                break;
                            }
                            /** 4.5 计算商品分类，作业单中商品的所在分类（包含所有父分类）只要存在1个分类满足则当前条件成立 **/
                            if (!isSkuCategoriesRef(staLineList, detial)) {
                                isAllMatching = false;
                                break;
                            }
                            /** 4.6 判断是否是COD订单,作业单中的物流信息与默认规则相同时则条件成立 **/
                            if (detial.getIsCod() != null && !queue.isCod() == detial.getIsCod().booleanValue()) {
                                isAllMatching = false;
                                break;
                            }
                            /** 4.7 收货详细地址若包含‘排除关键字’内容，则当前快递规则直接跳过 **/
                            if (isRemovekeyword(detial, queue.getAddress())) {
                                isAllMatching = false;
                                break;
                            }
                            // 如果订单指定仓库，判断明细是否支持该仓库
                            List<Long> whIds = detial.getWhouList();
                            if (whIds == null) {
                                whIds = getRefWarehouse(detial);
                            }
                            if (ouId != null) {
                                /** 4.8 是否存在指定仓库 **/
                                if ((whIds.size() > 0 && !isRefWarehouse(ouId, whIds)) || !isWarehouseTransRef(ouId, tr)) {
                                    isAllMatching = false;
                                    break;
                                }
                                if (isAllMatching) {
                                    /**
                                     * 如果订单指定仓库，当逻辑执行到这里的时候<br/>
                                     * 1、如果指定了快递，则该快递符合规则<br/>
                                     * 2、如果没有指定快递，则推荐该快递
                                     */
                                    ouLp.put(ouId, tr.getLpCode());
                                    return ouLp;

                                }
                            } else {
                                if (isAllMatching) {
                                    if (whIds.size() > 0) {
                                        for (Long whId : whIds) {
                                            if (isWarehouseTransRef(whId, tr)) {// 加一层仓库跟物流商的校验
                                                if (!ouLp.containsKey(whId)) {// 此处可以保证，一个仓库只会存在一次，且物流商为优先推荐的物流
                                                    ouLp.put(whId, tr.getLpCode());
                                                }
                                            }
                                        }
                                    } else {// 规则没有明细，则所有的都会匹配
                                        for (OperationUnit ou : tr.getOuList()) {
                                            if (!ouLp.containsKey(ou.getId())) {
                                                ouLp.put(ou.getId(), tr.getLpCode());
                                            }
                                        }
                                    }
                                }
                            }
                            // 设置时效类型如果sta.transTimeType为普通，物流信息时效类型设置为头时效
                            queue.setTransTimeType(tr.getIntTransTimeType());// TransTimeType.valueOf(tr.getIntTransTimeType()));
                        } else if (tr.getSort() == TransRole.defaultTransSort) {
                            /**
                             * 如果规则为默认物流商，那么推荐当前的物流，可用仓库为当前物流绑定的所有仓库
                             */
                            for (OperationUnit unit : tr.getOuList()) {
                                if (!ouLp.containsKey(unit.getId())) {// 此处可以保证，一个仓库只会存在一次，且物流商为优先推荐的物流
                                    ouLp.put(unit.getId(), tr.getLpCode());
                                }
                            }
                        }
                    }
                } else {
                    if (queue.getTransType() == tr.getIntTransType() && queue.getTransTimeType() == tr.getIntTransTimeType()) {
                        /**
                         * 如果规则没有明细，且通过之前的校验，那么推荐当前的物流，可用仓库为当前物流绑定的所有仓库
                         */
                        for (OperationUnit unit : tr.getOuList()) {
                            if (!ouLp.containsKey(unit.getId())) {// 此处可以保证，一个仓库只会存在一次，且物流商为优先推荐的物流
                                ouLp.put(unit.getId(), tr.getLpCode());
                            }
                        }
                    }
                }
                // } else if (tr.getSort() == TransRole.defaultTransSort) {
                // /**
                // * 如果规则为默认物流商，那么推荐当前的物流，可用仓库为当前物流绑定的所有仓库
                // */
                // for (OperationUnit unit : tr.getOuList()) {
                // if (!ouLp.containsKey(unit.getId())) {// 此处可以保证，一个仓库只会存在一次，且物流商为优先推荐的物流
                // ouLp.put(unit.getId(), tr.getLpCode());
                // }
                // }
                // }
            }
            wmsSalesOrderQueueDao.save(queue);
            return ouLp;
        } else {// //////////////////////////////////////////////////推荐老的逻辑
            // 存放仓库和匹配的物流商，按照优先级顺序来
            Map<Long, String> ouLp = new HashMap<Long, String>();
            WmsSalesOrderQueue queue = wmsSalesOrderQueueDao.getByPrimaryKey(id);
            String infoMsg = "suggestTransServiceForOrder-Order:" + queue.getOrderCode();
            Long ouId = null;
            String wcode = queue.getWarehouseCode();
            if (wcode != null) {
                OperationUnit unit = ouDao.getByCode(wcode);
                ouId = unit.getId();
            }
            String lpCode = queue.getTransCode();
            if (null != lpCode) {// 指定物流，则只用根据物流匹配仓库
                setOuLpByLpCode(ouLp, lpCode, ouId);
                return ouLp;
            }
            String province = (queue.getProvince() == null ? "" : queue.getProvince()), city = (queue.getCity() == null ? "" : queue.getCity()), district = queue.getDistrict() == null ? "" : queue.getDistrict();
            List<StaLineCommand> staLineList = staLineDao.findLineSkuByOrderId(id, new BeanPropertyRowMapperExt<StaLineCommand>(StaLineCommand.class));
            List<TransRoleCommand> roleList = getTransRole(queue.getOwner());
            if (log.isDebugEnabled()) {
                log.debug(infoMsg + "-roleList:" + roleList.size());
            }
            BigDecimal channlId = biChannelDao.queryChannlIdByOwner(queue.getOwner(), new SingleColumnRowMapper<BigDecimal>());
            boolean isEmsPriority = isEmsPriority(queue.getAddress(), channlId.longValue());
            if (log.isDebugEnabled()) {
                log.debug(infoMsg + "-isEmsPriority:" + isEmsPriority);
            }
            if (isEmsPriority) {
                /**
                 * 如果地址默认EMS优先，且单据没有指定快递，则EMS优先，所有仓库都可以发
                 */
                setOuLpByLpCode(ouLp, Transportator.EMS, ouId);
            }
            /**
             * 如果快递非EMS优先，则后续推荐快递逻辑，
             */
            // 遍历 物流推荐规则
            for (TransRoleCommand tr : roleList) {
                /** 1 所有推荐快递均需要当前仓库支持的快递（于WarehouseTransRef查询所有仓库支持的快递信息），仓库不支持的快递的服务规则直接忽略。 **/
                // 如果订单指定仓库，但是当前规则不支持对应仓库，跳过该规则
                if (ouId != null) {
                    if (!isWarehouseTransRef(ouId, tr)) {
                        continue;
                    }
                }
                /** 2 校验作业单如是货到付款作业单则推荐时快递服务所属快递必须支持COD **/
                // 如果订单为cod订单，但是当前规则不支持cod业务，跳过该规则
                if (queue.isCod() && (tr.getIsSupportCod() == null || !tr.getIsSupportCod())) {
                    continue;
                }
                log.debug(infoMsg + "-TR:" + tr.getId() + "-TransTypeInfo:" + queue.getTransType() + "|" + queue.getTransTimeType() + "|" + tr.getIntTransType() + "|" + tr.getIntTransTimeType());
                /** 3 根据匹配作业单发货明细staD.tranType、transTimeType一致的快递服务，如一致则校验该服务下的所有规则。 **/
                if (queue.getTransType() == tr.getIntTransType() && queue.getTransTimeType() == tr.getIntTransTimeType()) {
                    log.debug(infoMsg + "-TR is IN:" + tr.getId());
                    /** 4 规则明细计算方式：当规则中该条件为空则默认满足，只有当所有条件成立时，则当前规则匹配成功。 **/
                    List<TransRoleDetialCommand> details = tr.getDetials();
                    if (details != null && details.size() > 0) {
                        // 是否全部匹配
                        boolean isAllMatching = true;
                        // 遍历规则明细
                        for (TransRoleDetialCommand detial : details) {
                            /** 4.0计算订单金额，sta.order_total_actual是否>=minAmount、<=maxAmount **/
                            boolean isMaxAmount = detial.getMaxAmount() == null || (queue.getOrderAmt().doubleValue() <= detial.getMaxAmount().doubleValue());
                            boolean isMinAmount = detial.getMinAmount() == null || (queue.getOrderAmt().doubleValue() >= detial.getMinAmount().doubleValue());
                            if (!isMaxAmount || !isMinAmount) {
                                isAllMatching = false;
                                break;
                            }
                            /** 4.2 校验配送范围，循环配货范围组中明细以开头模糊匹配校验省市区(如范围组中市为空则不计算市、区，如区为空则不计算区) **/
                            if (!isDistributionRange(province, city, district, detial)) {
                                isAllMatching = false;
                                break;
                            }
                            /** 4.3 计算商品，作业单中存在1个商品满足则当前条件成立 **/
                            List<Long> skuIds = detial.getSkuIds();
                            if (skuIds == null) {
                                // 获取关联商品
                                skuIds = getTrdRefSku(detial);
                            }
                            if (!isSkusContainStaSkuAll(skuIds, staLineList)) {
                                isAllMatching = false;
                                break;
                            }
                            /** 4.4 计算商品标签，判断匹配逻辑 **/
                            List<SkuTagCommand> skuTagRefSku = detial.getSkuTagRefSku();
                            if (skuTagRefSku == null) {
                                // 所有商品标签(非禁用)所关联的商品
                                skuTagRefSku = getTrdRefSkuTag(detial);
                            }
                            /** 4.4.1 计算商品标签，作业单中商品只要有1个商品标签满足则当前条件成立 **/
                            if (!isSkusContainStaSku(skuTagRefSku, staLineList)) {
                                isAllMatching = false;
                                break;
                            }
                            /** 4.4.2 计算商品标签，作业单中商品和商品标签完全匹配则当前条件成立 **/
                            if (!isSkusAllContainStaSku(skuTagRefSku, staLineList)) {
                                isAllMatching = false;
                                break;
                            }
                            /** 4.4.3 计算商品标签，订单中的所有商品均存在于维护商品列表则当前条件成立 **/
                            if (!isSkusOnlyContainStaSku(skuTagRefSku, staLineList)) {
                                isAllMatching = false;
                                break;
                            }
                            /** 4.5 计算商品分类，作业单中商品的所在分类（包含所有父分类）只要存在1个分类满足则当前条件成立 **/
                            if (!isSkuCategoriesRef(staLineList, detial)) {
                                isAllMatching = false;
                                break;
                            }
                            /** 4.6 判断是否是COD订单,作业单中的物流信息与默认规则相同时则条件成立 **/
                            if (detial.getIsCod() != null && !queue.isCod() == detial.getIsCod().booleanValue()) {
                                isAllMatching = false;
                                break;
                            }
                            /** 4.7 收货详细地址若包含‘排除关键字’内容，则当前快递规则直接跳过 **/
                            if (isRemovekeyword(detial, queue.getAddress())) {
                                isAllMatching = false;
                                break;
                            }
                            // 如果订单指定仓库，判断明细是否支持该仓库
                            List<Long> whIds = detial.getWhouList();
                            if (whIds == null) {
                                whIds = getRefWarehouse(detial);
                            }
                            if (ouId != null) {
                                /** 4.8 是否存在指定仓库 **/
                                if ((whIds.size() > 0 && !isRefWarehouse(ouId, whIds)) || !isWarehouseTransRef(ouId, tr)) {
                                    isAllMatching = false;
                                    break;
                                }
                                if (isAllMatching) {
                                    /**
                                     * 如果订单指定仓库，当逻辑执行到这里的时候<br/>
                                     * 1、如果指定了快递，则该快递符合规则<br/>
                                     * 2、如果没有指定快递，则推荐该快递
                                     */
                                    ouLp.put(ouId, tr.getLpCode());
                                    return ouLp;

                                }
                            } else {
                                if (isAllMatching) {
                                    if (whIds.size() > 0) {
                                        for (Long whId : whIds) {
                                            if (isWarehouseTransRef(whId, tr)) {// 加一层仓库跟物流商的校验
                                                if (!ouLp.containsKey(whId)) {// 此处可以保证，一个仓库只会存在一次，且物流商为优先推荐的物流
                                                    ouLp.put(whId, tr.getLpCode());
                                                }
                                            }
                                        }
                                    } else {// 规则没有明细，则所有的都会匹配
                                        for (OperationUnit ou : tr.getOuList()) {
                                            if (!ouLp.containsKey(ou.getId())) {
                                                ouLp.put(ou.getId(), tr.getLpCode());
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        /**
                         * 如果规则没有明细，且通过之前的校验，那么推荐当前的物流，可用仓库为当前物流绑定的所有仓库
                         */
                        for (OperationUnit unit : tr.getOuList()) {
                            if (!ouLp.containsKey(unit.getId())) {// 此处可以保证，一个仓库只会存在一次，且物流商为优先推荐的物流
                                ouLp.put(unit.getId(), tr.getLpCode());
                            }
                        }
                    }
                } else if (tr.getSort() == TransRole.defaultTransSort) {
                    /**
                     * 如果规则为默认物流商，那么推荐当前的物流，可用仓库为当前物流绑定的所有仓库
                     */
                    for (OperationUnit unit : tr.getOuList()) {
                        if (!ouLp.containsKey(unit.getId())) {// 此处可以保证，一个仓库只会存在一次，且物流商为优先推荐的物流
                            ouLp.put(unit.getId(), tr.getLpCode());
                        }
                    }
                }
            }
            return ouLp;
        }
    }

    private void setOuLpByLpCode(Map<Long, String> ouLp, String lpCode, Long ouId) {
        List<Long> idList = ouDao.getOuIdByLpCode(lpCode, new SingleColumnRowMapper<Long>(Long.class));
        boolean ob = false;
        for (Long wId : idList) {
            if (null != ouId) {
                if (wId.equals(ouId)) {
                    ob = true;
                }
            }
            ouLp.put(wId, lpCode);
        }
        if (null != ouId) {
            ouLp.clear();
            if (ob) {
                ouLp.put(ouId, lpCode);
            }
        }
    }

    @Override
    public void suggestTransServicePreSale(Long id) {

        ChooseOption co = chooseOptionDao.findByCategoryCodeAndKey("SUGGEST_TRANS_SWITCH", "SWITCH");
        if (co != null) {// //////////////////////////////////////////////////////新的推荐逻辑
            RecieverInfo recieverInfo = orderRecieverInfoDao.getByPrimaryKey(id);
            if (recieverInfo == null) {
                return;
            }

            AdvanceOrderAddInfo addInfo = addInfoDao.getByPrimaryKey(recieverInfo.getAddInfo().getId());
            if (addInfo == null) {
                return;
            }
            StockTransApplication sta = staDao.findStaByReSlipCode(addInfo.getOrderCode());
            if (sta == null) {
                return;
            }
            if (log.isInfoEnabled()) {
                log.info("TransSuggestManagerImpl.suggestTransService strat:{}", sta.getRefSlipCode());
            }


            String province = (recieverInfo.getRecieverProvince() == null ? "" : recieverInfo.getRecieverProvince()), city = (recieverInfo.getRecieverCity() == null ? "" : recieverInfo.getRecieverCity()), district =
                    recieverInfo.getRecieverDistrict() == null ? "" : recieverInfo.getRecieverDistrict();
            BigDecimal channlId = biChannelDao.queryChannlIdByOwner(sta.getOwner(), new SingleColumnRowMapper<BigDecimal>());
            List<StaLineCommand> staLineList = staLineDao.findLineSkuBySta(sta.getId(), new BeanPropertyRowMapperExt<StaLineCommand>(StaLineCommand.class));
            if (log.isInfoEnabled()) {
                log.info("TransSuggestManagerImpl.getTransRole strat:{},{}", sta.getRefSlipCode(), sta.getSlipCode1());
            }
            List<TransRoleCommand> roleList = getTransRole(sta.getOwner());
            if (log.isInfoEnabled()) {
                log.info("TransSuggestManagerImpl.getTransRole end:{},{}", sta.getRefSlipCode(), sta.getSlipCode1());
            }
            TransRoleCommand suggestTrans = null;
            TransRoleCommand defaultTrans = null;
            boolean isEmsPriority = isEmsPriority(recieverInfo.getRecieverAddress(), channlId.longValue());
            if (log.isInfoEnabled()) {
                log.info("isEmsPriority : {} , {} ,{}", isEmsPriority, sta.getRefSlipCode(), sta.getSlipCode1());
            }
            if (isEmsPriority) {
                if (log.isInfoEnabled()) {
                    log.info("isEmsPriority true , {} ,{}", sta.getRefSlipCode(), sta.getSlipCode1());
                }
                suggestTrans = new TransRoleCommand();
                suggestTrans.setLpCode(Transportator.EMS);
                if (log.isInfoEnabled()) {
                    log.info("TransSuggestManagerImpl.isEmsPriority....end address:" + recieverInfo.getRecieverAddress());
                }
            } else {
                if (log.isInfoEnabled()) {
                    log.info("isEmsPriority false , {} ,{}", sta.getRefSlipCode(), sta.getSlipCode1());
                }
                // 遍历 物流推荐规则
                for (TransRoleCommand tr : roleList) {
                    /** 1 所有推荐快递均需要当前仓库支持的快递（于WarehouseTransRef查询所有仓库支持的快递信息），仓库不支持的快递的服务规则直接忽略。 **/
                    if (!isWarehouseTransRef(sta.getMainWarehouse().getId(), tr)) {
                        if (log.isInfoEnabled()) {
                            log.info("TransRoleCommand isWarehouseTransRef continue, {} ,{}", sta.getRefSlipCode(), sta.getSlipCode1());
                        }
                        continue;
                    } else {}
                    /** 2 校验作业单如是货到付款作业单则推荐时快递服务所属快递必须支持COD **/
                    if (sta.getStaDeliveryInfo().getIsCod() && !tr.getIsSupportCod()) {
                        if (log.isInfoEnabled()) {
                            log.info("TransRoleCommand is cod continue, {} ,{}", sta.getRefSlipCode(), sta.getSlipCode1());
                        }
                        continue;
                    }
                    // /** 3 根据匹配作业单发货明细staD.tranType、transTimeType一致的快递服务，如一致则校验该服务下的所有规则。 **/
                    // if (di.getTransType() != null && di.getTransTimeType() != null &&
                    // di.getTransType().getValue() == tr.getIntTransType() &&
                    // di.getTransTimeType().getValue() == tr.getIntTransTimeType()) {
                    if (log.isInfoEnabled()) {
                        log.info("TransRoleCommand trans time type,{},{}", sta.getRefSlipCode(), sta.getSlipCode1());
                    }
                    /** 4 规则明细计算方式：当规则中该条件为空则默认满足，只有当所有条件成立时，则当前规则匹配成功。 **/
                    List<TransRoleDetialCommand> details = tr.getDetials();
                    if (details != null && details.size() > 0) {
                        // 是否全部匹配
                        boolean isAllMatching = true;
                        // 遍历规则明细
                        if (log.isInfoEnabled()) {
                            log.info("TransSuggestManagerImpl.checkdetails strat: {},{}", sta.getRefSlipCode(), sta.getSlipCode1());
                        }
                        for (TransRoleDetialCommand detial : details) {
                            if (log.isInfoEnabled()) {
                                log.info("detial current start, {} ,{}", sta.getRefSlipCode(), sta.getSlipCode1());
                            }

                            /**
                             * 4.00 根据匹配作业单发货明细staD.tranType（
                             * 运送方式(快递附加服务)）、transTimeType一致的快递服务，如一致则校验该服务下的所有规则。
                             **/
                            if (sta.getStaDeliveryInfo().getTransType() != null && sta.getStaDeliveryInfo().getTransTimeType() != null && sta.getStaDeliveryInfo().getTransType().getValue() == tr.getIntTransType()
                                    && (sta.getStaDeliveryInfo().getTransTimeType().getValue() == (detial.getTimeType() == null ? -1 : detial.getTimeType()) || sta.getStaDeliveryInfo().getTransTimeType().getValue() == 1)) {
                                /** 4.0计算订单金额，sta.order_total_actual是否>=minAmount、<=maxAmount **/
                                boolean isMaxAmount = detial.getMaxAmount() == null || (sta.getOrderTotalActual().doubleValue() <= detial.getMaxAmount().doubleValue());
                                boolean isMinAmount = detial.getMinAmount() == null || (sta.getOrderTotalActual().doubleValue() >= detial.getMinAmount().doubleValue());
                                if (!isMaxAmount || !isMinAmount) {
                                    isAllMatching = false;
                                    if (log.isInfoEnabled()) {
                                        log.info("isMaxAmount isMinAmount break, {} ,{}", sta.getRefSlipCode(), sta.getSlipCode1());
                                    }
                                    break;
                                }
                                if (log.isInfoEnabled()) {
                                    log.info("isMaxAmount isMinAmount end, {} ,{}", sta.getRefSlipCode(), sta.getSlipCode1());
                                }
                                /** 4.1 计算订单重量，sta.staDeliveryInfo.wieght是否>=minWeight、<=maxWeight **/
                                boolean isMaxWeight = detial.getMaxWeight() == null || (sta.getStaDeliveryInfo().getWeight().doubleValue() <= detial.getMaxWeight().doubleValue());
                                boolean isMinWeight = detial.getMinWeight() == null || (sta.getStaDeliveryInfo().getWeight().doubleValue() >= detial.getMinWeight().doubleValue());
                                if (!isMaxWeight || !isMinWeight) {
                                    isAllMatching = false;
                                    if (log.isInfoEnabled()) {
                                        log.info("isMaxWeight isMinWeight break, {} ,{}", sta.getRefSlipCode(), sta.getSlipCode1());
                                    }
                                    break;
                                }
                                if (log.isInfoEnabled()) {
                                    log.info("isMaxWeight isMinWeight end, {} ,{}", sta.getRefSlipCode(), sta.getSlipCode1());
                                }
                                /** 4.2 校验配送范围，循环配货范围组中明细以开头模糊匹配校验省市区(如范围组中市为空则不计算市、区，如区为空则不计算区) **/
                                if (!isDistributionRange(province, city, district, detial)) {
                                    isAllMatching = false;
                                    if (log.isInfoEnabled()) {
                                        log.info("isMaxWeight isDistributionRange break, {} ,{}", sta.getRefSlipCode(), sta.getSlipCode1());
                                    }
                                    break;
                                }
                                if (log.isInfoEnabled()) {
                                    log.info("TransSuggestManagerImpl.isDistributionRange end:{},{}", sta.getRefSlipCode(), sta.getSlipCode1());
                                }
                                /** 4.3 计算商品，作业单中存在1个商品满足则当前条件成立 **/
                                List<Long> skuIds = detial.getSkuIds();
                                if (skuIds == null) {
                                    // 获取关联商品
                                    skuIds = getTrdRefSku(detial);
                                }
                                if (!isSkusContainStaSkuAll(skuIds, staLineList)) {
                                    isAllMatching = false;
                                    if (log.isInfoEnabled()) {
                                        log.info("TransSuggestManagerImpl.isSkusContainStaSkuAll break:{},{}", sta.getRefSlipCode(), sta.getSlipCode1());
                                    }
                                    break;
                                }
                                if (log.isInfoEnabled()) {
                                    log.info("TransSuggestManagerImpl.isSkusContainStaSkuAll end:{},{}", sta.getRefSlipCode(), sta.getSlipCode1());
                                }
                                /** 4.4 计算商品标签，判断匹配逻辑 **/
                                List<SkuTagCommand> skuTagRefSku = detial.getSkuTagRefSku();
                                if (skuTagRefSku == null) {
                                    // 所有商品标签(非禁用)所关联的商品
                                    skuTagRefSku = getTrdRefSkuTag(detial);
                                }
                                /** 4.4.1 计算商品标签，作业单中商品只要有1个商品标签满足则当前条件成立 **/
                                if (!isSkusContainStaSku(skuTagRefSku, staLineList)) {
                                    isAllMatching = false;
                                    if (log.isInfoEnabled()) {
                                        log.info("TransSuggestManagerImpl.isSkusContainStaSku break:{},{}", sta.getRefSlipCode(), sta.getSlipCode1());
                                    }
                                    break;
                                }
                                if (log.isInfoEnabled()) {
                                    log.info("TransSuggestManagerImpl.isSkusContainStaSku end:{},{}", sta.getRefSlipCode(), sta.getSlipCode1());
                                }
                                /** 4.4.2 计算商品标签，作业单中商品和商品标签完全匹配则当前条件成立 **/
                                if (!isSkusAllContainStaSku(skuTagRefSku, staLineList)) {
                                    isAllMatching = false;
                                    if (log.isInfoEnabled()) {
                                        log.info("TransSuggestManagerImpl.isSkusAllContainStaSku break:{},{}", sta.getRefSlipCode(), sta.getSlipCode1());
                                    }
                                    break;
                                }
                                if (log.isInfoEnabled()) {
                                    log.info("TransSuggestManagerImpl.isSkusAllContainStaSku end:{},{}", sta.getRefSlipCode(), sta.getSlipCode1());
                                }
                                /** 4.4.3 计算商品标签，订单中的所有商品均存在于维护商品列表则当前条件成立 **/
                                if (!isSkusOnlyContainStaSku(skuTagRefSku, staLineList)) {
                                    isAllMatching = false;
                                    if (log.isInfoEnabled()) {
                                        log.info("TransSuggestManagerImpl.isSkusOnlyContainStaSku break:{},{}", sta.getRefSlipCode(), sta.getSlipCode1());
                                    }
                                    break;
                                }
                                if (log.isInfoEnabled()) {
                                    log.info("TransSuggestManagerImpl.isSkusOnlyContainStaSku end:{},{}", sta.getRefSlipCode(), sta.getSlipCode1());
                                }
                                /** 4.5 计算商品分类，作业单中商品的所在分类（包含所有父分类）只要存在1个分类满足则当前条件成立 **/
                                if (!isSkuCategoriesRef(staLineList, detial)) {
                                    isAllMatching = false;
                                    if (log.isInfoEnabled()) {
                                        log.info("TransSuggestManagerImpl.isSkuCategoriesRef break:{},{}", sta.getRefSlipCode(), sta.getSlipCode1());
                                    }
                                    break;
                                }
                                if (log.isInfoEnabled()) {
                                    log.info("TransSuggestManagerImpl.isSkuCategoriesRef end:{},{}", sta.getRefSlipCode(), sta.getSlipCode1());
                                }
                                /** 4.6 判断是否是COD订单,作业单中的物流信息与默认规则相同时则条件成立 **/
                                if (detial.getIsCod() != null && !sta.getStaDeliveryInfo().getIsCod().equals(detial.getIsCod())) {
                                    isAllMatching = false;
                                    if (log.isInfoEnabled()) {
                                        log.info("TransSuggestManagerImpl.detial is cod break:{},{}", sta.getRefSlipCode(), sta.getSlipCode1());
                                    }
                                    break;
                                }
                                if (log.isInfoEnabled()) {
                                    log.info("TransSuggestManagerImpl.detial is cod end:{},{}", sta.getRefSlipCode(), sta.getSlipCode1());
                                }
                                /** 4.7 收货详细地址若包含‘排除关键字’内容，则当前快递规则直接跳过 **/
                                if (isRemovekeyword(detial, recieverInfo.getRecieverAddress())) {
                                    isAllMatching = false;
                                    if (log.isInfoEnabled()) {
                                        log.info("TransSuggestManagerImpl.isRemovekeyword break:{},{}", sta.getRefSlipCode(), sta.getSlipCode1());
                                    }
                                    break;
                                }
                                if (log.isInfoEnabled()) {
                                    log.info("TransSuggestManagerImpl.isRemovekeyword end:{},{}", sta.getRefSlipCode(), sta.getSlipCode1());
                                }
                                /** 4.8 是否存在指定仓库 **/
                                List<Long> whIds = detial.getWhouList();
                                if (whIds == null) {
                                    whIds = getRefWarehouse(detial);
                                }
                                if (whIds.size() > 0 && !isRefWarehouse(sta.getMainWarehouse().getId(), whIds)) {
                                    isAllMatching = false;
                                    if (log.isInfoEnabled()) {
                                        log.info("detial current break:{},{}", sta.getRefSlipCode(), sta.getSlipCode1());
                                    }
                                    break;
                                }
                                // 设置时效类型如果sta.transTimeType为普通，物流信息时效类型设置为头时效
                                sta.getStaDeliveryInfo().setTransTimeType(TransTimeType.valueOf(tr.getIntTransTimeType()));
                            } else if (tr.getSort() == TransRole.defaultTransSort) {
                                /** 5 选择否默认快递 **/
                                defaultTrans = tr;
                                continue;
                            }
                            if (log.isInfoEnabled()) {
                                log.info("detial current end:{},{}", sta.getRefSlipCode(), sta.getSlipCode1());
                            }
                            // //////////////////////////////////////////////////////////////////////////////////////////////////////////run////////////////////////
                        }
                        if (log.isInfoEnabled()) {
                            log.info("TransRoleDetialCommand detial for all end,{},{}", sta.getRefSlipCode(), sta.getSlipCode1());
                        }
                        if (isAllMatching) {
                            suggestTrans = tr;
                            if (log.isInfoEnabled()) {
                                log.info("TransRoleDetialCommand isAllMatching,{},{}", sta.getRefSlipCode(), sta.getSlipCode1());
                            }
                            break;
                        }
                    } else {
                        if (sta.getStaDeliveryInfo().getTransType() != null && sta.getStaDeliveryInfo().getTransTimeType() != null && sta.getStaDeliveryInfo().getTransType().getValue() == tr.getIntTransType()
                                && sta.getStaDeliveryInfo().getTransTimeType().getValue() == tr.getIntTransTimeType()) {
                            // 当规则中条件为空则默认满足
                            suggestTrans = tr;
                            break;
                        }
                    }
                }
            }
            if (log.isInfoEnabled()) {
                log.info("suggest set 1 end,{},{}", sta.getRefSlipCode(), sta.getSlipCode1());
            }
            if (suggestTrans == null) {
                if (log.isInfoEnabled()) {
                    log.info("suggestTrans is null,{},{}", sta.getRefSlipCode(), sta.getSlipCode1());
                }
                if (defaultTrans != null) {
                    recieverInfo.setLpcode(defaultTrans.getLpCode());
                    // 当默认快递服务为普通，而作业单需求服务非普通时，则作业单服务类型不做调整。当模块快递服务或时效非普通，如作业单服务和时效为普通时则更新作业单时效与服务
                    if (defaultTrans.getIntTransType() != null && TransType.ORDINARY.equals(sta.getStaDeliveryInfo().getTransType()) && TransType.ORDINARY.getValue() != defaultTrans.getIntTransType()) {
                        sta.getStaDeliveryInfo().setTransType(TransType.valueOf(defaultTrans.getIntTransType()));
                    }
                    if (defaultTrans.getIntTransTimeType() != null && TransTimeType.ORDINARY.equals(sta.getStaDeliveryInfo().getTransTimeType()) && TransTimeType.ORDINARY.getValue() != defaultTrans.getIntTransTimeType()) {
                        sta.getStaDeliveryInfo().setTransTimeType(TransTimeType.valueOf(defaultTrans.getIntTransTimeType()));
                    }
                } else if (roleList.size() > 0) {
                    if (log.isInfoEnabled()) {
                        log.info("suggest ems,{},{}", sta.getRefSlipCode(), sta.getSlipCode1());
                    }
                    // 在默认快递也不存在的情况下 选择 EMS
                    recieverInfo.setLpcode(Transportator.EMS);
                }
                if (log.isInfoEnabled()) {
                    log.info("TransSuggestManagerImpl.suggestTrans:{},{}", sta.getRefSlipCode(), sta.getSlipCode1());
                }
            } else {
                if (log.isInfoEnabled()) {
                    log.info("suggestTrans is not null {},{},{}", suggestTrans.getLpCode(), sta.getRefSlipCode(), sta.getSlipCode1());
                }
                // 匹配到对应的物流商
                recieverInfo.setLpcode(suggestTrans.getLpCode());
            }
            orderRecieverInfoDao.save(recieverInfo);
            if (log.isInfoEnabled()) {
                log.info("TransSuggestManagerImpl.suggestTransService end:" + sta.getRefSlipCode() + "," + sta.getSlipCode1());
            }
        } else {// ///////////////////////////////////////////老的物流推荐
            StockTransApplication sta = staDao.getByPrimaryKey(1l);
            if (sta == null) {
                return;
            }
            // Warehouse wh = warehouseDao.getByOuId(sta.getMainWarehouse().getId());
            // if (!StringUtil.isEmpty(wh.getVmiSource())) {
            // // 不支持外包仓
            // return;
            // }
            if (log.isInfoEnabled()) {
                log.info("TransSuggestManagerImpl.suggestTransService strat:{},{}", sta.getRefSlipCode(), sta.getSlipCode1());
            }
            if (!StockTransApplicationType.OUTBOUND_SALES.equals(sta.getType()) && !StockTransApplicationType.OUTBOUND_RETURN_REQUEST.equals(sta.getType())) {
                throw new BusinessException(ErrorCode.RETURN_REQUEST_STA_TYPE_ERROR, new Object[] {sta.getCode()});
            }
            if (!StockTransApplicationStatus.CREATED.equals(sta.getStatus())) {
                throw new BusinessException(ErrorCode.ERROR_STA_NOT_STATUS);
            }

            StaDeliveryInfo di = sta.getStaDeliveryInfo();
            if (di.getLpCode() != null) {
                return;
            }
            String province = (di.getProvince() == null ? "" : di.getProvince()), city = (di.getCity() == null ? "" : di.getCity()), district = di.getDistrict() == null ? "" : di.getDistrict();
            BigDecimal channlId = biChannelDao.queryChannlIdByOwner(sta.getOwner(), new SingleColumnRowMapper<BigDecimal>());
            List<StaLineCommand> staLineList = staLineDao.findLineSkuBySta(sta.getId(), new BeanPropertyRowMapperExt<StaLineCommand>(StaLineCommand.class));
            if (log.isInfoEnabled()) {
                log.info("TransSuggestManagerImpl.getTransRole strat:{},{}", sta.getRefSlipCode(), sta.getSlipCode1());
            }
            List<TransRoleCommand> roleList = getTransRole(sta.getOwner());
            if (log.isInfoEnabled()) {
                log.info("TransSuggestManagerImpl.getTransRole end:{},{}", sta.getRefSlipCode(), sta.getSlipCode1());
            }
            TransRoleCommand suggestTrans = null;
            TransRoleCommand defaultTrans = null;
            boolean isEmsPriority = isEmsPriority(di.getAddress(), channlId.longValue());
            if (log.isInfoEnabled()) {
                log.info("isEmsPriority : {} , {} ,{}", isEmsPriority, sta.getRefSlipCode(), sta.getSlipCode1());
            }
            if (isEmsPriority) {
                if (log.isInfoEnabled()) {
                    log.info("isEmsPriority true , {} ,{}", sta.getRefSlipCode(), sta.getSlipCode1());
                }
                suggestTrans = new TransRoleCommand();
                suggestTrans.setLpCode(Transportator.EMS);
                if (log.isInfoEnabled()) {
                    log.info("TransSuggestManagerImpl.isEmsPriority....end address:" + di.getAddress());
                }
            } else {
                if (log.isInfoEnabled()) {
                    log.info("isEmsPriority false , {} ,{}", sta.getRefSlipCode(), sta.getSlipCode1());
                }
                // 遍历 物流推荐规则
                for (TransRoleCommand tr : roleList) {
                    /** 1 所有推荐快递均需要当前仓库支持的快递（于WarehouseTransRef查询所有仓库支持的快递信息），仓库不支持的快递的服务规则直接忽略。 **/
                    if (!isWarehouseTransRef(sta.getMainWarehouse().getId(), tr)) {
                        if (log.isInfoEnabled()) {
                            log.info("TransRoleCommand isWarehouseTransRef continue, {} ,{}", sta.getRefSlipCode(), sta.getSlipCode1());
                        }
                        continue;
                    } else {}
                    /** 2 校验作业单如是货到付款作业单则推荐时快递服务所属快递必须支持COD **/
                    if (di.getIsCod() && !tr.getIsSupportCod()) {
                        if (log.isInfoEnabled()) {
                            log.info("TransRoleCommand is cod continue, {} ,{}", sta.getRefSlipCode(), sta.getSlipCode1());
                        }
                        continue;
                    }
                    /** 3 根据匹配作业单发货明细staD.tranType、transTimeType一致的快递服务，如一致则校验该服务下的所有规则。 **/
                    if (di.getTransType() != null && di.getTransTimeType() != null && di.getTransType().getValue() == tr.getIntTransType() && di.getTransTimeType().getValue() == tr.getIntTransTimeType()) {
                        if (log.isInfoEnabled()) {
                            log.info("TransRoleCommand trans time type,{},{}", sta.getRefSlipCode(), sta.getSlipCode1());
                        }
                        /** 4 规则明细计算方式：当规则中该条件为空则默认满足，只有当所有条件成立时，则当前规则匹配成功。 **/
                        List<TransRoleDetialCommand> details = tr.getDetials();
                        if (details != null && details.size() > 0) {
                            // 是否全部匹配
                            boolean isAllMatching = true;
                            // 遍历规则明细
                            if (log.isInfoEnabled()) {
                                log.info("TransSuggestManagerImpl.checkdetails strat: {},{}", sta.getRefSlipCode(), sta.getSlipCode1());
                            }
                            for (TransRoleDetialCommand detial : details) {
                                if (log.isInfoEnabled()) {
                                    log.info("detial current start, {} ,{}", sta.getRefSlipCode(), sta.getSlipCode1());
                                }
                                /** 4.0计算订单金额，sta.order_total_actual是否>=minAmount、<=maxAmount **/
                                boolean isMaxAmount = detial.getMaxAmount() == null || (sta.getOrderTotalActual().doubleValue() <= detial.getMaxAmount().doubleValue());
                                boolean isMinAmount = detial.getMinAmount() == null || (sta.getOrderTotalActual().doubleValue() >= detial.getMinAmount().doubleValue());
                                if (!isMaxAmount || !isMinAmount) {
                                    isAllMatching = false;
                                    if (log.isInfoEnabled()) {
                                        log.info("isMaxAmount isMinAmount break, {} ,{}", sta.getRefSlipCode(), sta.getSlipCode1());
                                    }
                                    break;
                                }
                                if (log.isInfoEnabled()) {
                                    log.info("isMaxAmount isMinAmount end, {} ,{}", sta.getRefSlipCode(), sta.getSlipCode1());
                                }
                                /** 4.1 计算订单重量，sta.staDeliveryInfo.wieght是否>=minWeight、<=maxWeight **/
                                boolean isMaxWeight = detial.getMaxWeight() == null || (sta.getStaDeliveryInfo().getWeight().doubleValue() <= detial.getMaxWeight().doubleValue());
                                boolean isMinWeight = detial.getMinWeight() == null || (sta.getStaDeliveryInfo().getWeight().doubleValue() >= detial.getMinWeight().doubleValue());
                                if (!isMaxWeight || !isMinWeight) {
                                    isAllMatching = false;
                                    if (log.isInfoEnabled()) {
                                        log.info("isMaxWeight isMinWeight break, {} ,{}", sta.getRefSlipCode(), sta.getSlipCode1());
                                    }
                                    break;
                                }
                                if (log.isInfoEnabled()) {
                                    log.info("isMaxWeight isMinWeight end, {} ,{}", sta.getRefSlipCode(), sta.getSlipCode1());
                                }
                                /** 4.2 校验配送范围，循环配货范围组中明细以开头模糊匹配校验省市区(如范围组中市为空则不计算市、区，如区为空则不计算区) **/
                                if (!isDistributionRange(province, city, district, detial)) {
                                    isAllMatching = false;
                                    if (log.isInfoEnabled()) {
                                        log.info("isMaxWeight isDistributionRange break, {} ,{}", sta.getRefSlipCode(), sta.getSlipCode1());
                                    }
                                    break;
                                }
                                if (log.isInfoEnabled()) {
                                    log.info("TransSuggestManagerImpl.isDistributionRange end:{},{}", sta.getRefSlipCode(), sta.getSlipCode1());
                                }
                                /** 4.3 计算商品，作业单中存在1个商品满足则当前条件成立 **/
                                List<Long> skuIds = detial.getSkuIds();
                                if (skuIds == null) {
                                    // 获取关联商品
                                    skuIds = getTrdRefSku(detial);
                                }
                                if (!isSkusContainStaSkuAll(skuIds, staLineList)) {
                                    isAllMatching = false;
                                    if (log.isInfoEnabled()) {
                                        log.info("TransSuggestManagerImpl.isSkusContainStaSkuAll break:{},{}", sta.getRefSlipCode(), sta.getSlipCode1());
                                    }
                                    break;
                                }
                                if (log.isInfoEnabled()) {
                                    log.info("TransSuggestManagerImpl.isSkusContainStaSkuAll end:{},{}", sta.getRefSlipCode(), sta.getSlipCode1());
                                }
                                /** 4.4 计算商品标签，判断匹配逻辑 **/
                                List<SkuTagCommand> skuTagRefSku = detial.getSkuTagRefSku();
                                if (skuTagRefSku == null) {
                                    // 所有商品标签(非禁用)所关联的商品
                                    skuTagRefSku = getTrdRefSkuTag(detial);
                                }
                                /** 4.4.1 计算商品标签，作业单中商品只要有1个商品标签满足则当前条件成立 **/
                                if (!isSkusContainStaSku(skuTagRefSku, staLineList)) {
                                    isAllMatching = false;
                                    if (log.isInfoEnabled()) {
                                        log.info("TransSuggestManagerImpl.isSkusContainStaSku break:{},{}", sta.getRefSlipCode(), sta.getSlipCode1());
                                    }
                                    break;
                                }
                                if (log.isInfoEnabled()) {
                                    log.info("TransSuggestManagerImpl.isSkusContainStaSku end:{},{}", sta.getRefSlipCode(), sta.getSlipCode1());
                                }
                                /** 4.4.2 计算商品标签，作业单中商品和商品标签完全匹配则当前条件成立 **/
                                if (!isSkusAllContainStaSku(skuTagRefSku, staLineList)) {
                                    isAllMatching = false;
                                    if (log.isInfoEnabled()) {
                                        log.info("TransSuggestManagerImpl.isSkusAllContainStaSku break:{},{}", sta.getRefSlipCode(), sta.getSlipCode1());
                                    }
                                    break;
                                }
                                if (log.isInfoEnabled()) {
                                    log.info("TransSuggestManagerImpl.isSkusAllContainStaSku end:{},{}", sta.getRefSlipCode(), sta.getSlipCode1());
                                }
                                /** 4.4.3 计算商品标签，订单中的所有商品均存在于维护商品列表则当前条件成立 **/
                                if (!isSkusOnlyContainStaSku(skuTagRefSku, staLineList)) {
                                    isAllMatching = false;
                                    if (log.isInfoEnabled()) {
                                        log.info("TransSuggestManagerImpl.isSkusOnlyContainStaSku break:{},{}", sta.getRefSlipCode(), sta.getSlipCode1());
                                    }
                                    break;
                                }
                                if (log.isInfoEnabled()) {
                                    log.info("TransSuggestManagerImpl.isSkusOnlyContainStaSku end:{},{}", sta.getRefSlipCode(), sta.getSlipCode1());
                                }
                                /** 4.5 计算商品分类，作业单中商品的所在分类（包含所有父分类）只要存在1个分类满足则当前条件成立 **/
                                if (!isSkuCategoriesRef(staLineList, detial)) {
                                    isAllMatching = false;
                                    if (log.isInfoEnabled()) {
                                        log.info("TransSuggestManagerImpl.isSkuCategoriesRef break:{},{}", sta.getRefSlipCode(), sta.getSlipCode1());
                                    }
                                    break;
                                }
                                if (log.isInfoEnabled()) {
                                    log.info("TransSuggestManagerImpl.isSkuCategoriesRef end:{},{}", sta.getRefSlipCode(), sta.getSlipCode1());
                                }
                                /** 4.6 判断是否是COD订单,作业单中的物流信息与默认规则相同时则条件成立 **/
                                if (detial.getIsCod() != null && !di.getIsCod().equals(detial.getIsCod())) {
                                    isAllMatching = false;
                                    if (log.isInfoEnabled()) {
                                        log.info("TransSuggestManagerImpl.detial is cod break:{},{}", sta.getRefSlipCode(), sta.getSlipCode1());
                                    }
                                    break;
                                }
                                if (log.isInfoEnabled()) {
                                    log.info("TransSuggestManagerImpl.detial is cod end:{},{}", sta.getRefSlipCode(), sta.getSlipCode1());
                                }
                                /** 4.7 收货详细地址若包含‘排除关键字’内容，则当前快递规则直接跳过 **/
                                if (isRemovekeyword(detial, di.getAddress())) {
                                    isAllMatching = false;
                                    if (log.isInfoEnabled()) {
                                        log.info("TransSuggestManagerImpl.isRemovekeyword break:{},{}", sta.getRefSlipCode(), sta.getSlipCode1());
                                    }
                                    break;
                                }
                                if (log.isInfoEnabled()) {
                                    log.info("TransSuggestManagerImpl.isRemovekeyword end:{},{}", sta.getRefSlipCode(), sta.getSlipCode1());
                                }
                                /** 4.8 是否存在指定仓库 **/
                                List<Long> whIds = detial.getWhouList();
                                if (whIds == null) {
                                    whIds = getRefWarehouse(detial);
                                }
                                if (whIds.size() > 0 && !isRefWarehouse(sta.getMainWarehouse().getId(), whIds)) {
                                    isAllMatching = false;
                                    if (log.isInfoEnabled()) {
                                        log.info("detial current break:{},{}", sta.getRefSlipCode(), sta.getSlipCode1());
                                    }
                                    break;
                                }
                                if (log.isInfoEnabled()) {
                                    log.info("detial current end:{},{}", sta.getRefSlipCode(), sta.getSlipCode1());
                                }
                            }
                            if (log.isInfoEnabled()) {
                                log.info("TransRoleDetialCommand detial for all end,{},{}", sta.getRefSlipCode(), sta.getSlipCode1());
                            }
                            if (isAllMatching) {
                                suggestTrans = tr;
                                if (log.isInfoEnabled()) {
                                    log.info("TransRoleDetialCommand isAllMatching,{},{}", sta.getRefSlipCode(), sta.getSlipCode1());
                                }
                                break;
                            }
                        } else {
                            // 当规则中条件为空则默认满足
                            suggestTrans = tr;
                            break;
                        }
                    } else if (tr.getSort() == TransRole.defaultTransSort) {
                        /** 5 选择否默认快递 **/
                        defaultTrans = tr;
                        continue;
                    }
                }
            }
            if (log.isInfoEnabled()) {
                log.info("suggest set 1 end,{},{}", sta.getRefSlipCode(), sta.getSlipCode1());
            }
            if (suggestTrans == null) {
                if (log.isInfoEnabled()) {
                    log.info("suggestTrans is null,{},{}", sta.getRefSlipCode(), sta.getSlipCode1());
                }
                if (defaultTrans != null) {
                    di.setLpCode(defaultTrans.getLpCode());
                    // 当默认快递服务为普通，而作业单需求服务非普通时，则作业单服务类型不做调整。当模块快递服务或时效非普通，如作业单服务和时效为普通时则更新作业单时效与服务
                    if (defaultTrans.getIntTransType() != null && TransType.ORDINARY.equals(di.getTransType()) && TransType.ORDINARY.getValue() != defaultTrans.getIntTransType()) {
                        di.setTransType(TransType.valueOf(defaultTrans.getIntTransType()));
                    }
                    if (defaultTrans.getIntTransTimeType() != null && TransTimeType.ORDINARY.equals(di.getTransTimeType()) && TransTimeType.ORDINARY.getValue() != defaultTrans.getIntTransTimeType()) {
                        di.setTransTimeType(TransTimeType.valueOf(defaultTrans.getIntTransTimeType()));
                    }
                } else if (roleList.size() > 0) {
                    if (log.isInfoEnabled()) {
                        log.info("suggest ems,{},{}", sta.getRefSlipCode(), sta.getSlipCode1());
                    }
                    // 在默认快递也不存在的情况下 选择 EMS
                    di.setLpCode(Transportator.EMS);
                }
                if (log.isInfoEnabled()) {
                    log.info("TransSuggestManagerImpl.suggestTrans:{},{}", sta.getRefSlipCode(), sta.getSlipCode1());
                }
            } else {
                if (log.isInfoEnabled()) {
                    log.info("suggestTrans is not null {},{},{}", suggestTrans.getLpCode(), sta.getRefSlipCode(), sta.getSlipCode1());
                }
                // 匹配到对应的物流商
                di.setLpCode(suggestTrans.getLpCode());
            }
            // staDeliveryInfoDao.save(di);
            if (log.isInfoEnabled()) {
                log.info("TransSuggestManagerImpl.suggestTransService end:" + sta.getRefSlipCode() + "," + sta.getSlipCode1());
            }
        }


    }


}
