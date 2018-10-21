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
 */
package com.jumbo.wms.manager.warehouse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import loxia.dao.support.BeanPropertyRowMapperExt;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jumbo.dao.baseinfo.SkuDao;
import com.jumbo.dao.system.ChooseOptionDao;
import com.jumbo.dao.warehouse.SkuChildSnDao;
import com.jumbo.dao.warehouse.SkuChildSnLogDao;
import com.jumbo.dao.warehouse.SkuSnCheckCfgDao;
import com.jumbo.dao.warehouse.SkuSnDao;
import com.jumbo.dao.warehouse.StaLineDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.util.StringUtil;
import com.jumbo.wms.Constants;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.sn.SnManager;
import com.jumbo.wms.manager.sn.SnManagerImpl;
import com.jumbo.wms.manager.warehouse.card.CardClientFactory;
import com.jumbo.wms.manager.warehouse.card.CardClientManager;
import com.jumbo.wms.manager.warehouse.card.CardResult;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.baseinfo.SkuInterfaceType;
import com.jumbo.wms.model.baseinfo.SkuSnCardStatus;
import com.jumbo.wms.model.baseinfo.SkuSnCheckCfgType;
import com.jumbo.wms.model.baseinfo.SkuSnCheckMode;
import com.jumbo.wms.model.baseinfo.SkuSnType;
import com.jumbo.wms.model.command.SkuSnCheckCfgCommand;
import com.jumbo.wms.model.system.ChooseOption;
import com.jumbo.wms.model.warehouse.SkuChildSn;
import com.jumbo.wms.model.warehouse.SkuChildSnLog;
import com.jumbo.wms.model.warehouse.SkuSn;
import com.jumbo.wms.model.warehouse.SkuSnLogCommand;
import com.jumbo.wms.model.warehouse.SkuSnStatus;
import com.jumbo.wms.model.warehouse.StaLineCommand;
import com.jumbo.wms.model.warehouse.StockTransApplication;

/**
 * @author lichuan
 * 
 */
@Service("starbucksManagerProxy")
public class StarbucksManagerProxyImpl implements StarbucksManagerProxy {
    private static final Logger log = LoggerFactory.getLogger(StarbucksManagerProxyImpl.class);
    private static final long serialVersionUID = 6926774769305913414L;
    private static final String WAIT_UNFREEZE = "待解冻";
    private static final String UNFREEZE_SUCCESS = "解冻成功";
    private static final String UNFREEZE_FAIL = "解冻失败";
    private static final String CANCEL_SUCCESS = "作废成功";
    private static final String CANCEL_FAIL = "作废失败";
    @Autowired
    private WareHouseManager wareHouseManager;
    @Autowired
    private SkuSnDao skuSnDao;
    @Autowired
    private SnManager snManager;
    @Autowired
    private CardClientFactory clientFactory;
    @Autowired
    private StaLineDao staLineDao;
    @Autowired
    private SkuSnCheckCfgDao skuSnCheckCfgDao;
    @Autowired
    private SkuDao skuDao;
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private SkuChildSnLogDao childSnLogDao;
    @Autowired
    private SkuChildSnDao childSnDao;
    @Autowired
    private ChooseOptionDao chooseOptionDao;

    /**
     * 星巴克作废卡
     * 
     * @throws Exception
     */
    @Override
    public void cancelCard(Long staId, String sn, Long skuId, Integer interfaceType, Integer snType, Integer spType, Long ouId, Long operatorId) throws Exception {
        if (null == staId || null == skuId) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        StockTransApplication sta = staDao.getByPrimaryKey(staId);
        if (null == sta) {
            throw new BusinessException(ErrorCode.STA_NOT_FOUND);
        }
        // 校验是否销售出SN
        List<SkuSnLogCommand> list = wareHouseManager.findOutboundSnBySta(staId);
        boolean isExist = false;
        for (SkuSnLogCommand skuSn : list) {
            String snLog = skuSn.getSn();
            if (StringUtils.isEmpty(snLog)) continue;
            if (snLog.equals(sn)) {
                isExist = true;
            }
        }
        if (false == isExist) {
            throw new BusinessException(ErrorCode.SN_IS_NOT_OUT_SN, new Object[] {"", sn});
        }
        if (SkuSnType.NO_BARCODE_SKU.getValue() != snType) {
            throw new BusinessException(ErrorCode.CANCEL_CARD_NOT_STATBUCKS_ERROR);
        }
        CardClientManager client = clientFactory.getClient(interfaceType);
        Sku sku = skuDao.getByPrimaryKey(skuId);
        // 判断是否已存在
        SkuSn skuSn = skuSnDao.findSkuSnBySnAndStaId(sn, ouId, staId, SkuSnStatus.USING);

        if (SkuInterfaceType.NIKE.getValue() == interfaceType) {
            if (null == skuSn) {
                try {
                    snManager.createSn(sn, skuId, ouId, staId, SkuSnCardStatus.WAIT_UNFREEZE);
                    snManager.saveSnOperateLog(sn, skuId, SkuSnCardStatus.WAIT_UNFREEZE, operatorId, WAIT_UNFREEZE);
                } catch (Exception e) {
                    throw e;
                }
            }
        } else if (SkuInterfaceType.STB_BEN.getValue() == interfaceType) {
            if (null == skuSn) {
                try {
                    snManager.createSn(sn, skuId, ouId, staId, SkuSnCardStatus.WAIT_UNFREEZE);
                    snManager.saveSnOperateLog(sn, skuId, SkuSnCardStatus.WAIT_UNFREEZE, operatorId, WAIT_UNFREEZE);
                } catch (Exception e) {
                    throw e;
                }
            }
        }

        else if (SkuInterfaceType.STB_ZHX.getValue() == interfaceType) {
            if (null == skuSn) {
                // 保存SN
                try {
                    snManager.createSn(sn, skuId, ouId, staId, SkuSnCardStatus.WAIT_UNFREEZE);
                    snManager.saveSnOperateLog(sn, skuId, SkuSnCardStatus.WAIT_UNFREEZE, operatorId, WAIT_UNFREEZE);
                } catch (Exception e) {
                    throw e;
                }
                if (sku.getChildSnQty() != null) {
                    List<SkuChildSnLog> childSnLog = childSnLogDao.getbyChildSn(sn);
                    for (SkuChildSnLog skuChildSnLog : childSnLog) {
                        snManager.createChildSn(sn, skuChildSnLog.getSeedSn(), SkuSnCardStatus.WAIT_UNFREEZE, skuId);
                        // 解冻卡
                        try {
                            CardResult rt = client.unlockCard(skuChildSnLog.getSeedSn());
                            if (null == rt) {
                                throw new BusinessException(ErrorCode.SYSTEM_ERROR);
                            } else if (CardResult.STATUS_SUCCESS != rt.getStatus()) {
                                log.debug("====>starbucks, card unlock invoke hub wrong,sn is[{}], status is[{}], errorCode is[{}]", new Object[] {sn, rt.getStatus(), rt.getErrorCode()});
                                if (CardResult.STATUS_FAIL == rt.getStatus()) {
                                    throw new BusinessException(ErrorCode.STARBUCKS_INVOKE_HUB_UNLOCK_FAIL, new Object[] {rt.getErrorCode(), rt.getMsg()});
                                } else if (CardResult.STATUS_ERROR == rt.getStatus()) {
                                    throw new BusinessException(ErrorCode.STARBUCKS_INVOKE_HUB_UNLOCK_ERROR, new Object[] {rt.getErrorCode(), rt.getMsg()});
                                }
                            }
                            SkuChildSn childSn = childSnDao.getbyStatusSn(skuChildSnLog.getSeedSn(), skuId, SkuSnStatus.USING);
                            childSn.setStatus(SkuSnCardStatus.RELEASE_SUCCESS);
                            childSnDao.save(childSn);
                            childSnDao.flush();
                            snManager.saveSnOperateLog(sn, skuId, SkuSnCardStatus.RELEASE_SUCCESS, operatorId, UNFREEZE_SUCCESS);
                        } catch (Exception e) {
                            SkuChildSn childSn = childSnDao.getbyStatusSn(skuChildSnLog.getSeedSn(), skuId, SkuSnStatus.USING);
                            childSn.setStatus(SkuSnCardStatus.RELEASE_ERROR);
                            childSnDao.save(childSn);
                            childSnDao.flush();
                            snManager.saveSnOperateLog(sn, skuId, SkuSnCardStatus.RELEASE_ERROR, operatorId, UNFREEZE_FAIL);
                            throw e;
                        }
                        // 作废卡
                        try {
                            CardResult rt = client.cancelCard(skuChildSnLog.getSeedSn(), spType, sta);
                            if (null == rt) {
                                throw new BusinessException(ErrorCode.SYSTEM_ERROR);
                            } else if (CardResult.STATUS_SUCCESS != rt.getStatus()) {
                                log.debug("====>starbucks, card cancel invoke hub wrong,sn is[{}], status is[{}], errorCode is[{}]", new Object[] {sn, rt.getStatus(), rt.getErrorCode()});
                                if (CardResult.STATUS_FAIL == rt.getStatus()) {
                                    throw new BusinessException(ErrorCode.STARBUCKS_INVOKE_HUB_CANCEL_FAIL, new Object[] {rt.getErrorCode(), rt.getMsg()});
                                } else if (CardResult.STATUS_ERROR == rt.getStatus()) {
                                    throw new BusinessException(ErrorCode.STARBUCKS_INVOKE_HUB_CANCEL_ERROR, new Object[] {rt.getErrorCode(), rt.getMsg()});
                                }
                            }
                            SkuChildSn childSn = childSnDao.getbyStatusSn(skuChildSnLog.getSeedSn(), skuId, SkuSnStatus.USING);
                            childSn.setStatus(SkuSnCardStatus.CANCEL);
                            childSnDao.save(childSn);
                            childSnDao.flush();
                            snManager.saveSnOperateLog(sn, skuId, SkuSnCardStatus.CANCEL, operatorId, CANCEL_SUCCESS);
                        } catch (Exception e) {
                            SkuChildSn childSn = childSnDao.getbyStatusSn(skuChildSnLog.getSeedSn(), skuId, SkuSnStatus.USING);
                            childSn.setStatus(SkuSnCardStatus.RELEASE_SUCCESS);
                            childSnDao.save(childSn);
                            childSnDao.flush();
                            snManager.saveSnOperateLog(sn, skuId, SkuSnCardStatus.CANCEL_FAIL, operatorId, CANCEL_FAIL);
                            throw e;
                        }
                    }
                } else {
                    // 解冻卡
                    try {
                        CardResult rt = client.unlockCard(sn);
                        if (null == rt) {
                            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
                        } else if (CardResult.STATUS_SUCCESS != rt.getStatus()) {
                            log.debug("====>starbucks, card unlock invoke hub wrong,sn is[{}], status is[{}], errorCode is[{}]", new Object[] {sn, rt.getStatus(), rt.getErrorCode()});
                            if (CardResult.STATUS_FAIL == rt.getStatus()) {
                                throw new BusinessException(ErrorCode.STARBUCKS_INVOKE_HUB_UNLOCK_FAIL, new Object[] {rt.getErrorCode(), rt.getMsg()});
                            } else if (CardResult.STATUS_ERROR == rt.getStatus()) {
                                throw new BusinessException(ErrorCode.STARBUCKS_INVOKE_HUB_UNLOCK_ERROR, new Object[] {rt.getErrorCode(), rt.getMsg()});
                            }
                        }
                        skuSn = skuSnDao.findSkuSnBySn(sn, ouId, SkuSnStatus.USING);
                        skuSn.setCardStatus(SkuSnCardStatus.RELEASE_SUCCESS);
                        skuSnDao.save(skuSn);
                        skuSnDao.flush();
                        snManager.saveSnOperateLog(sn, skuId, SkuSnCardStatus.RELEASE_SUCCESS, operatorId, UNFREEZE_SUCCESS);
                    } catch (Exception e) {
                        skuSn = skuSnDao.findSkuSnBySn(sn, ouId, SkuSnStatus.USING);
                        skuSn.setCardStatus(SkuSnCardStatus.RELEASE_ERROR);
                        skuSnDao.save(skuSn);
                        skuSnDao.flush();
                        snManager.saveSnOperateLog(sn, skuId, SkuSnCardStatus.RELEASE_ERROR, operatorId, UNFREEZE_FAIL);
                        throw e;
                    }
                    // 作废卡
                    try {
                        CardResult rt = client.cancelCard(sn, spType, sta);
                        if (null == rt) {
                            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
                        } else if (CardResult.STATUS_SUCCESS != rt.getStatus()) {
                            log.debug("====>starbucks, card cancel invoke hub wrong,sn is[{}], status is[{}], errorCode is[{}]", new Object[] {sn, rt.getStatus(), rt.getErrorCode()});
                            if (CardResult.STATUS_FAIL == rt.getStatus()) {
                                throw new BusinessException(ErrorCode.STARBUCKS_INVOKE_HUB_CANCEL_FAIL, new Object[] {rt.getErrorCode(), rt.getMsg()});
                            } else if (CardResult.STATUS_ERROR == rt.getStatus()) {
                                throw new BusinessException(ErrorCode.STARBUCKS_INVOKE_HUB_CANCEL_ERROR, new Object[] {rt.getErrorCode(), rt.getMsg()});
                            }
                        }
                        skuSn = skuSnDao.findSkuSnBySn(sn, ouId, SkuSnStatus.USING);
                        skuSn.setCardStatus(SkuSnCardStatus.CANCEL);
                        skuSnDao.save(skuSn);
                        skuSnDao.flush();
                        snManager.saveSnOperateLog(sn, skuId, SkuSnCardStatus.CANCEL, operatorId, CANCEL_SUCCESS);
                    } catch (Exception e) {
                        skuSn = skuSnDao.findSkuSnBySn(sn, ouId, SkuSnStatus.USING);
                        skuSn.setCardStatus(SkuSnCardStatus.RELEASE_SUCCESS);
                        skuSnDao.save(skuSn);
                        skuSnDao.flush();
                        snManager.saveSnOperateLog(sn, skuId, SkuSnCardStatus.CANCEL_FAIL, operatorId, CANCEL_FAIL);
                        throw e;
                    }
                }
            } else {
                // 判断是否绑定正确
                if (null == skuSn.getSku()) throw new BusinessException(ErrorCode.CANCEL_CARD_SN_INFO_ERROR);
                if (null != skuSn.getStv()) throw new BusinessException(ErrorCode.CANCEL_CARD_SN_INFO_ERROR);
                Long snStaId = skuSn.getStaId();
                Long snSkuId = skuSn.getSku().getId();
                if (null == snStaId || snSkuId == null || 0 != snStaId.compareTo(staId) || 0 != snSkuId.compareTo(skuId)) {
                    throw new BusinessException(ErrorCode.CANCEL_CARD_SN_INFO_ERROR);
                }
                // 卡状态
                SkuSnCardStatus cStatus = skuSn.getCardStatus();
                if (SkuSnCardStatus.WAIT_UNFREEZE.equals(cStatus) || SkuSnCardStatus.RELEASE_ERROR.equals(cStatus)) {
                    // 解冻卡
                    try {
                        CardResult rt = client.unlockCard(sn);
                        if (null == rt) {
                            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
                        } else if (CardResult.STATUS_SUCCESS != rt.getStatus()) {
                            log.debug("====>starbucks, card unlock invoke hub wrong,sn is[{}], status is[{}], errorCode is[{}]", new Object[] {sn, rt.getStatus(), rt.getErrorCode()});
                            if (CardResult.STATUS_FAIL == rt.getStatus()) {
                                throw new BusinessException(ErrorCode.STARBUCKS_INVOKE_HUB_UNLOCK_FAIL, new Object[] {rt.getErrorCode(), rt.getMsg()});
                            } else if (CardResult.STATUS_ERROR == rt.getStatus()) {
                                throw new BusinessException(ErrorCode.STARBUCKS_INVOKE_HUB_UNLOCK_ERROR, new Object[] {rt.getErrorCode(), rt.getMsg()});
                            }
                        }
                        skuSn = skuSnDao.findSkuSnBySn(sn, ouId, SkuSnStatus.USING);
                        skuSn.setCardStatus(SkuSnCardStatus.RELEASE_SUCCESS);
                        skuSnDao.save(skuSn);
                        skuSnDao.flush();
                        snManager.saveSnOperateLog(sn, skuId, SkuSnCardStatus.RELEASE_SUCCESS, operatorId, UNFREEZE_SUCCESS);
                    } catch (Exception e) {
                        skuSn = skuSnDao.findSkuSnBySn(sn, ouId, SkuSnStatus.USING);
                        skuSn.setCardStatus(SkuSnCardStatus.RELEASE_ERROR);
                        skuSnDao.save(skuSn);
                        skuSnDao.flush();
                        snManager.saveSnOperateLog(sn, skuId, SkuSnCardStatus.RELEASE_ERROR, operatorId, UNFREEZE_FAIL);
                        throw e;
                    }
                    // 作废卡
                    try {
                        CardResult rt = client.cancelCard(sn, spType, sta);
                        if (null == rt) {
                            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
                        } else if (CardResult.STATUS_SUCCESS != rt.getStatus()) {
                            log.debug("====>starbucks, card cancel invoke hub wrong,sn is[{}], status is[{}], errorCode is[{}]", new Object[] {sn, rt.getStatus(), rt.getErrorCode()});
                            if (CardResult.STATUS_FAIL == rt.getStatus()) {
                                throw new BusinessException(ErrorCode.STARBUCKS_INVOKE_HUB_CANCEL_FAIL, new Object[] {rt.getErrorCode(), rt.getMsg()});
                            } else if (CardResult.STATUS_ERROR == rt.getStatus()) {
                                throw new BusinessException(ErrorCode.STARBUCKS_INVOKE_HUB_CANCEL_ERROR, new Object[] {rt.getErrorCode(), rt.getMsg()});
                            }
                        }
                        skuSn = skuSnDao.findSkuSnBySn(sn, ouId, SkuSnStatus.USING);
                        skuSn.setCardStatus(SkuSnCardStatus.CANCEL);
                        skuSnDao.save(skuSn);
                        skuSnDao.flush();
                        snManager.saveSnOperateLog(sn, skuId, SkuSnCardStatus.CANCEL, operatorId, CANCEL_SUCCESS);
                    } catch (Exception e) {
                        skuSn = skuSnDao.findSkuSnBySn(sn, ouId, SkuSnStatus.USING);
                        skuSn.setCardStatus(SkuSnCardStatus.RELEASE_SUCCESS);
                        skuSnDao.save(skuSn);
                        skuSnDao.flush();
                        snManager.saveSnOperateLog(sn, skuId, SkuSnCardStatus.CANCEL_FAIL, operatorId, CANCEL_FAIL);
                        throw e;
                    }
                } else if (SkuSnCardStatus.RELEASE_SUCCESS.equals(cStatus)) {
                    // 作废卡
                    try {
                        CardResult rt = client.cancelCard(sn, spType, sta);
                        if (null == rt) {
                            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
                        } else if (CardResult.STATUS_SUCCESS != rt.getStatus()) {
                            log.debug("====>starbucks, card cancel invoke hub wrong,sn is[{}], status is[{}], errorCode is[{}]", new Object[] {sn, rt.getStatus(), rt.getErrorCode()});
                            if (CardResult.STATUS_FAIL == rt.getStatus()) {
                                throw new BusinessException(ErrorCode.STARBUCKS_INVOKE_HUB_CANCEL_FAIL, new Object[] {rt.getErrorCode(), rt.getMsg()});
                            } else if (CardResult.STATUS_ERROR == rt.getStatus()) {
                                throw new BusinessException(ErrorCode.STARBUCKS_INVOKE_HUB_CANCEL_ERROR, new Object[] {rt.getErrorCode(), rt.getMsg()});
                            }
                        }
                        skuSn = skuSnDao.findSkuSnBySn(sn, ouId, SkuSnStatus.USING);
                        skuSn.setCardStatus(SkuSnCardStatus.CANCEL);
                        skuSnDao.save(skuSn);
                        skuSnDao.flush();
                        snManager.saveSnOperateLog(sn, skuId, SkuSnCardStatus.CANCEL, operatorId, CANCEL_SUCCESS);
                    } catch (Exception e) {
                        skuSn = skuSnDao.findSkuSnBySn(sn, ouId, SkuSnStatus.USING);
                        skuSn.setCardStatus(SkuSnCardStatus.RELEASE_SUCCESS);
                        skuSnDao.save(skuSn);
                        skuSnDao.flush();
                        snManager.saveSnOperateLog(sn, skuId, SkuSnCardStatus.RELEASE_SUCCESS, operatorId, CANCEL_FAIL);
                        throw e;
                    }
                } else if (SkuSnCardStatus.CANCEL.equals(cStatus)) {
                    // throw new BusinessException(ErrorCode.SYSTEM_ERROR);
                } else {
                    throw new BusinessException(ErrorCode.SYSTEM_ERROR);
                }
            }
        } else if (SkuInterfaceType.STB_HP.getValue() == interfaceType) {
            if (null == skuSn) {
                // 保存SN
                try {
                    snManager.createSn(sn, skuId, ouId, staId, null);
                } catch (Exception e) {
                    throw e;
                }
            } else {
                // 判断是否绑定正确
                if (null == skuSn.getSku()) throw new BusinessException(ErrorCode.CANCEL_CARD_SN_INFO_ERROR);
                if (null != skuSn.getStv()) throw new BusinessException(ErrorCode.CANCEL_CARD_SN_INFO_ERROR);
                Long snStaId = skuSn.getStaId();
                Long snSkuId = skuSn.getSku().getId();
                if (null == snStaId || snSkuId == null || 0 != snStaId.compareTo(staId) || 0 != snSkuId.compareTo(skuId)) {
                    throw new BusinessException(ErrorCode.CANCEL_CARD_SN_INFO_ERROR);
                }
            }
        }

    }

    /**
     * 验证收货卡号
     */
    @Override
    public StaLineCommand validationStarbucksInfo(StaLineCommand staline) {
        List<StaLineCommand> lineList = staLineDao.findStaLineListByStaId(staline.getStaId(), new BeanPropertyRowMapperExt<StaLineCommand>(StaLineCommand.class));
        StaLineCommand s = new StaLineCommand();
        Map<Integer, String> map = new HashMap<Integer, String>();
        String snStart = staline.getStartNum(); // 前端获取起始卡号
        String snStop = staline.getStopNum(); // 获取终止卡号

        boolean alge = false;
        // 验证是否在这一收货批次内
        for (StaLineCommand line : lineList) {
            if (line.getSkuId().equals(staline.getSkuId())) {
                alge = true;
            }
        }
        if (false == alge) {
            throw new BusinessException(ErrorCode.SKU_NOT_PLOT, new Object[] {staline.getSkuName()});
        }
        Sku sku = skuDao.getByPrimaryKey(staline.getSkuId());
        if (sku != null && SkuSnType.NO_BARCODE_SKU.equals(sku.getSnType())) {
            s.setSkuId(sku.getId());
            s.setBarCode(sku.getBarCode());
            s.setSkuCode(sku.getCode());
            s.setSkuName(sku.getName());
            s.setKeyProperties(sku.getKeyProperties());
            Integer snCheckMode = sku.getSnCheckMode() == null ? -1 : sku.getSnCheckMode().getValue();
            List<SkuSnCheckCfgCommand> cfgList = skuSnCheckCfgDao.getSkuSnCheckCfgBySnCheckMode(snCheckMode, new BeanPropertyRowMapperExt<SkuSnCheckCfgCommand>(SkuSnCheckCfgCommand.class));
            if (cfgList.size() > 0) {
                for (SkuSnCheckCfgCommand cfg : cfgList) {
                    map.put(cfg.getTypeInt(), cfg.getMemo());
                }

                // 判断是否有正则表达式验证
                if (map.containsKey(SkuSnCheckCfgType.REGULAR.getValue())) {
                    if (!StringUtil.isEmpty(map.get(SkuSnCheckCfgType.REGULAR.getValue()))) {
                        Pattern p = Pattern.compile(map.get(SkuSnCheckCfgType.REGULAR.getValue()));
                        Matcher m = p.matcher(snStart);
                        if (!m.find()) {
                            // 起始卡号正则规则不正确
                            throw new BusinessException(ErrorCode.SKU_SN_STARTNUM_ERROR, new Object[] {snStart});
                        }
                        Pattern pstop = Pattern.compile(map.get(SkuSnCheckCfgType.REGULAR.getValue()));
                        Matcher mstop = pstop.matcher(snStop);
                        if (!mstop.find()) {
                            // 终止卡号正则规则不正确
                            throw new BusinessException(ErrorCode.SKU_SN_STARTNUM_ERROR, new Object[] {snStop});
                        }
                    }
                }
                // 判断是否有格式化配置
                if (map.containsKey(SkuSnCheckCfgType.CHAR_REPLACE.getValue())) {
                    // 格式化终止卡号
                    if (!StringUtil.isEmpty(map.get(SkuSnCheckCfgType.CHAR_REPLACE.getValue()))) {
                        snStart = SnManagerImpl.formatSn(snStart, map.get(SkuSnCheckCfgType.CHAR_REPLACE.getValue()));
                        s.setStartNum(snStart);
                    }
                    // 格式化终止卡号
                    if (!StringUtil.isEmpty(map.get(SkuSnCheckCfgType.CHAR_REPLACE.getValue()))) {
                        snStop = SnManagerImpl.formatSn(snStop, map.get(SkuSnCheckCfgType.CHAR_REPLACE.getValue()));
                        s.setStopNum(snStop);
                    }
                }
            } else {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR);
            }
            if (sku.getSnCheckMode().getValue() == SkuSnCheckMode.STB_SVCQ.getValue()) {
                String[] a = snStop.split("-");
                String sto = a[1].substring(1);
                String[] b = snStart.split("-");
                String sta = b[1].substring(1);
                int completeQuantity = Integer.parseInt(sto) - Integer.parseInt(sta);
                if (completeQuantity < 20) {
                    throw new BusinessException(ErrorCode.SKU_SN_STARTNUM_AND_STOPNUM, new Object[] {staline.getStartNum(), staline.getStopNum()});

                }
                s.setCompleteQuantity(Long.valueOf(completeQuantity / 20 + 1));

            } else if (sku.getSnCheckMode().getValue() == SkuSnCheckMode.STB_BEN.getValue()) {
                s.setCompleteQuantity(Long.valueOf(1));
            } else {
                if ((Long.valueOf(snStop) - Long.valueOf(snStart)) < 0) {
                    throw new BusinessException(ErrorCode.SKU_SN_STARTNUM_AND_STOPNUM, new Object[] {staline.getStartNum(), staline.getStopNum()});
                }
                s.setCompleteQuantity(Long.valueOf(snStop) - Long.valueOf(snStart) + 1);
            }
        } else {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        return s;
    }

    /**
     * 获取星巴克星享卡aes加密key
     */
    @Override
    public String getStarbucksAESKey() {
        ChooseOption op = chooseOptionDao.findByCategoryCodeAndKey(Constants.STARBUCKS, Constants.STARBUCKS_AES_KEY);
        if (op != null) {
            return op.getOptionValue();
        }
        return null;
    }
}
