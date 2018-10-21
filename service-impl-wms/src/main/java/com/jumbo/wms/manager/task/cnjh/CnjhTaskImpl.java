package com.jumbo.wms.manager.task.cnjh;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.baozun.bizhub.manager.platform.taobao.TaobaoManager;
import com.baozun.bizhub.model.taobao.GeneralImportsOutBoundRequest;
import com.baozun.bizhub.model.taobao.GeneralImportsOutBoundResponse;
import com.baozun.bizhub.model.taobao.WaybillRequest;
import com.baozun.bizhub.model.taobao.WaybillResponse;
import com.baozun.task.annotation.SingleTaskLock;
import com.jumbo.dao.vmi.cj.CjLgOrderCodeUrlDao;
import com.jumbo.dao.vmi.warehouse.MsgOutboundOrderDao;
import com.jumbo.dao.warehouse.StaDeliveryInfoDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.wms.Constants;
import com.jumbo.wms.daemon.CnjhTask;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.model.vmi.cj.CjLgOrderCodeUrl;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrder;
import com.jumbo.wms.model.warehouse.StaDeliveryInfo;
import com.jumbo.wms.model.warehouse.StockTransApplication;

@Service("cnjhTask")
public class CnjhTaskImpl extends BaseManagerImpl implements CnjhTask {
    private static final long serialVersionUID = -6142933299116262237L;
    protected static final Logger logger = LoggerFactory.getLogger(CnjhTaskImpl.class);
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private StaDeliveryInfoDao staDeliveryInfoDao;
    @Autowired
    private MsgOutboundOrderDao msgOutboundOrderDao;
    @Autowired
    private TaobaoManager taobaoManager;
    @Autowired
    private CjLgOrderCodeUrlDao cjLgOrderCodeUrlDao;

    /**
     * （此定时任务暂未启用）
     */
    @Override
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void goToGetLgorderCodeAndUrl() {
        List<CjLgOrderCodeUrl> lcuList = cjLgOrderCodeUrlDao.getCjLgOrderCodeUrlByStatus1(0);
        if (lcuList != null && !lcuList.isEmpty()) {
            for (CjLgOrderCodeUrl lcu : lcuList) {
                if (lcu.getLgorderCode() == null) {
                    String lgorderCode = getLgorderCode(Constants.CJ_SYSTEM_KEY, lcu.getStaId(), Long.parseLong(lcu.getTradeOrderId()), Long.parseLong(lcu.getResourceId()), lcu.getStoreCode(), lcu.getFirstLogistics(), lcu.getFirstWaybillNo(), 2);
                    if (lgorderCode != null) {
                        lcu.setLgorderCode(lgorderCode);
                        lcu.setStatus1(1);
                        // 错误次数字段公用
                        lcu.setErrorCount(null);
                        cjLgOrderCodeUrlDao.save(lcu);
                    } else {
                        Integer count = lcu.getErrorCount();
                        if (count == null) {
                            count = 0;
                        }
                        count++;
                        lcu.setErrorCount(count);
                        if (count >= 3) {
                            lcu.setStatus1(-1);
                            lcu.setErrorCount(null);
                        }
                        cjLgOrderCodeUrlDao.save(lcu);
                    }
                }
            }
        }
        List<CjLgOrderCodeUrl> urlList = cjLgOrderCodeUrlDao.getCjLgOrderCodeUrlByStatus2(0);
        if (urlList != null && !urlList.isEmpty()) {
            for (CjLgOrderCodeUrl lcu : urlList) {
                // 无lgOrderCode执行下一个
                if (lcu.getLgorderCode() == null) continue;
                String url = getExpressWayBillUrl(Constants.CJ_SYSTEM_KEY, lcu.getStaId(), lcu.getLgorderCode(), 2);
                if (url != null) {
                    lcu.setWaybillurl(url);
                    lcu.setStatus2(1);
                    cjLgOrderCodeUrlDao.save(lcu);
                    // 更新CJ外包仓物流面单url数据
                    try {
                        StockTransApplication sta = staDao.getByPrimaryKey(lcu.getStaId());
                        StaDeliveryInfo sd = sta.getStaDeliveryInfo();
                        if (sd == null) {
                            sd = staDeliveryInfoDao.getByPrimaryKey(lcu.getStaId());
                        }
                        if (sd.getTrackingNo() == null) {
                            sd.setTrackingNo(lcu.getLgorderCode());
                            staDeliveryInfoDao.save(sd);
                        }
                        MsgOutboundOrder mo = msgOutboundOrderDao.getByStaCode(sta.getCode());
                        if (mo != null && mo.getTransNo() == null) {
                            mo.setTransNo(lcu.getLgorderCode());
                            // 更新CJ外包仓订单创建时间
                            mo.setCreateTime(new Date());
                            mo.setSfCityCode(sd.getTransCityCode());
                            // 解锁
                            if (mo.getIsLocked() != null && mo.getIsLocked()) {
                                mo.setIsLocked(false);
                            }
                            msgOutboundOrderDao.save(mo);
                        }
                    } catch (Exception e) {
                        logger.error("定时任务补充CJ销售单-物流面单url数据失败!staId=" + lcu.getStaId() + ",tradeOrderId=" + lcu.getTradeOrderId());
                    }
                } else {
                    Integer count = lcu.getErrorCount();
                    if (count == null) {
                        count = 0;
                    }
                    count++;
                    lcu.setErrorCount(count);
                    if (count >= 3) {
                        lcu.setStatus2(-1);
                    }
                    cjLgOrderCodeUrlDao.save(lcu);
                }
            }
        }
    }

    /**
     * 通过一般进口发货获取lgorder_code
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public String getLgorderCode(String systemKey, Long staId, Long tradeOrderId, Long resourceId, String storeCode, String firstLogistics, String firstWaybillNo, Integer i) {
        CjLgOrderCodeUrl lcu = cjLgOrderCodeUrlDao.getCjLgOrderCodeUrlByStaId(staId);
        // 数据保存中间表
        if (lcu == null) {
            lcu = new CjLgOrderCodeUrl();
            lcu.setStaId(staId);
            lcu.setTradeOrderId(tradeOrderId + "");
            lcu.setResourceId(resourceId + "");
            lcu.setStoreCode(storeCode);
            lcu.setFirstLogistics(firstLogistics);
            lcu.setFirstWaybillNo(firstWaybillNo);
            lcu.setStatus1(0);
            lcu.setStatus2(0);
            cjLgOrderCodeUrlDao.save(lcu);
        }
        if (lcu != null && lcu.getLgorderCode() != null) {
            return lcu.getLgorderCode();
        }
        try {
            if (StringUtils.isAnyBlank(tradeOrderId + "", resourceId + "", storeCode)) {
                logger.error("获取lgorder_code请求参数缺失必要参数!staId=" + staId + ",tradeOrderId=" + tradeOrderId);
                throw new BusinessException(ErrorCode.MUST_PARAM_LOST);
            }
            GeneralImportsOutBoundRequest req = new GeneralImportsOutBoundRequest();
            req.setTradeOrderId(tradeOrderId);// 交易订单id
            req.setResourceId(resourceId);// 物流资源id
            req.setStoreCode(storeCode);// 仓库编码
            req.setFirstLogistics(firstLogistics);// 第1段物流承运商
            req.setFirstWaybillno(firstWaybillNo);// 第1段物流运单号
            GeneralImportsOutBoundResponse rep = taobaoManager.generalImportsOutBound(systemKey, req);
            if (rep.getIsSuccess()) {
                lcu.setStatus1(1);
                lcu.setLgorderCode(rep.getLgorderCode());
                cjLgOrderCodeUrlDao.save(lcu);
                return rep.getLgorderCode();
            } else {
                logger.error("菜鸟集货获取lgorder_code失败！" + "ErrorCode:" + rep.getResultErrorCode() + "ErrorMsg:" + rep.getResultErrorMsg());
                lcu.setErrorMsg("ErrorCode:" + rep.getResultErrorCode() + "ErrorMsg:" + rep.getResultErrorMsg());
                cjLgOrderCodeUrlDao.save(lcu);
                i++;
                if (i < 3) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e1) {
                        if (log.isErrorEnabled()) {
                            log.error("getLgorderCode Thread InterruptedException", e1);
                        }
                    }
                    return getLgorderCode(systemKey, staId, tradeOrderId, resourceId, storeCode, firstLogistics, firstWaybillNo, i);
                } else {
                    throw new BusinessException(ErrorCode.RETRY_FAILd);
                }
            }
        } catch (Exception e) {
            logger.error("菜鸟集货获取lgorder_code异常！staId=" + staId + ",tradeOrderId=" + tradeOrderId, e);
            return null;
        }
    }

    /**
     * 获取运单信息接口和lgorder_code查询面单url
     */
    @Override
    public String getExpressWayBillUrl(String systemKey, Long staId, String lgorder_code, Integer i) {
        try {
            if (lgorder_code == null) {
                logger.error("获取面单url请求参数缺失必要参数!staId=" + staId + ",lgorder_code=" + lgorder_code);
                throw new BusinessException(ErrorCode.MUST_PARAM_LOST);
            }
            CjLgOrderCodeUrl lgorderCodeUrl = cjLgOrderCodeUrlDao.getCjLgOrderCodeUrlByStaId(staId);
            if (lgorderCodeUrl != null && lgorderCodeUrl.getWaybillurl() != null) {
                return lgorderCodeUrl.getWaybillurl();
            }
            WaybillRequest req = new WaybillRequest();
            req.setOrderCode(lgorder_code);// 物流订单号
            WaybillResponse rep = taobaoManager.waybill(systemKey, req);
            if (rep.getWaybillurl() != null) {
                if (lgorderCodeUrl != null) {
                    lgorderCodeUrl.setWaybillurl(rep.getWaybillurl());
                    lgorderCodeUrl.setStatus2(1);
                    cjLgOrderCodeUrlDao.save(lgorderCodeUrl);
                }
                return rep.getWaybillurl();
            } else {
                logger.error("菜鸟集货获取物流面单url失败！staId=" + staId + ",lgorder_code=" + lgorder_code);
                i++;
                if (i < 3) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e1) {
                        if (log.isErrorEnabled()) {
                            log.error("getExpressWayBillUrl Thread InterruptedException", e1);
                        }
                    }
                    return getExpressWayBillUrl(systemKey, staId, lgorder_code, i);
                } else {
                    throw new BusinessException(ErrorCode.RETRY_FAILd);
                }
            }
        } catch (Exception e) {
            logger.error("菜鸟集货获取物流面单url异常！staId=" + staId + ",lgorder_code=" + lgorder_code, e);
            return null;
        }
    }
}
