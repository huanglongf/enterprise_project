package com.jumbo.wms.manager.task.expressRadar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import loxia.dao.Pagination;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import com.baozun.task.annotation.SingleTaskLock;
import com.jumbo.dao.expressRadar.RadarTransNoDao;
import com.jumbo.dao.warehouse.BiChannelDao;
import com.jumbo.wms.Constants;
import com.jumbo.wms.daemon.ExpressRadarTask;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.expressRadar.ExpressRadarManager;
import com.jumbo.wms.manager.expressRadar.TransRouteStatusManager;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.command.expressRadar.RadarTransNoCommand;

public class ExpressRadarTaskImpl extends BaseManagerImpl implements ExpressRadarTask {

    /**
	 * 
	 */
    private static final long serialVersionUID = 2955600644914337382L;

    protected static final Logger log = LoggerFactory.getLogger(ExpressRadarTaskImpl.class);

    @Autowired
    private RadarTransNoDao radarTransNoDao;
    @Autowired
    private ExpressRadarManager expressRadarManager;
    @Autowired
    private BiChannelDao biChannelDao;
    @Autowired
    private TransRouteStatusManager transRouteStatusManager;

    public String EXPRESS_RADAR_LOGISTICS = "SF,STO,EMS";

    public int EXPRESS_RADAR_NO_AMOUNT = 2000;

    // 特殊店铺
    @SingleTaskLock(timeout = Constants.TASK_LOCK_TIMEOUT, value = Constants.TASK_LOCK_VALUE)
    public void specialStore(String args) {

        if (EXPRESS_RADAR_LOGISTICS != null) {

            String[] erls = EXPRESS_RADAR_LOGISTICS.split(",");

            Map<String, Object> params = new HashMap<String, Object>();
            params.put("amount", 2000);
            params.put("store", args);

            List<String> logistics = new ArrayList<String>();// 物流商编码
            for (String erl : erls) {
                logistics.add(erl);
            }
            params.put("logistics", logistics);

            updateExpressInfo(params);
        }


    }

    // 普通店铺
    @SingleTaskLock(timeout = Constants.TASK_LOCK_TIMEOUT, value = Constants.TASK_LOCK_VALUE)
    public void commonStore() {

        if (EXPRESS_RADAR_LOGISTICS != null) {
            String[] erls = EXPRESS_RADAR_LOGISTICS.split(",");

            Map<String, Object> params = new HashMap<String, Object>();
            List<BiChannel> bicList = biChannelDao.getBySpecial(true);// 获取特殊店铺
            List<String> channelCode = new ArrayList<String>();// 特殊店铺编码
            for (BiChannel bc : bicList) {
                channelCode.add(bc.getCode());
            }

            List<String> logistics = new ArrayList<String>();// 物流商编码
            for (String erl : erls) {
                logistics.add(erl);
            }

            params.put("logistics", logistics);
            params.put("amount", 2000);
            params.put("channelCode", channelCode);

            updateExpressInfo(params);
        }

    }

    @SingleTaskLock(timeout = Constants.TASK_LOCK_TIMEOUT, value = Constants.TASK_LOCK_VALUE)
    public void updateExpressWarnningInfo() {
        updateRouteWarning();
    }

    /**
     * 快递信息的更新
     * 
     * @param params
     */
    public void updateExpressInfo(Map<String, Object> params) {
        try {
            if (params != null) {
                Integer amount = (Integer) params.get("amount");

                // 第一次查询，得到数据的数量，数据的页数
                Pagination<RadarTransNoCommand> p = radarTransNoDao.findRadarTransNoByParams(0, amount, params, new BeanPropertyRowMapper<RadarTransNoCommand>(RadarTransNoCommand.class));

                if (p.getCount() > 0) {
                    eachExpress(p.getItems());
                    if (p.getCount() > amount) {
                        for (int i = 2; i <= p.getTotalPages(); i++) {
                            long start = System.currentTimeMillis();
                            Pagination<RadarTransNoCommand> pagination = radarTransNoDao.findRadarTransNoByParams((i - 1) * amount, amount, params, new BeanPropertyRowMapper<RadarTransNoCommand>(RadarTransNoCommand.class));
                            eachExpress(pagination.getItems());
                            long end = System.currentTimeMillis();
                            log.info("快递更新 time:" + (end - start));
                        }
                    }
                }
            }
        } catch (BusinessException e) {
            log.error("快递雷达error：快递信息更新失败！error code :{}", e.getErrorCode());
        } catch (Exception e) {
            log.error("快递雷达error：快递信息更新失败！", e);
        }


    }

    /**
     * 根据订单号更新快递
     * 
     * @param erList
     */
    public void eachExpress(List<RadarTransNoCommand> erList) {

        for (RadarTransNoCommand expressRadar : erList) {
            try {
                expressRadarManager.expressDispose(expressRadar);

            } catch (Exception e) {
                if (log.isDebugEnabled()) {
                    log.debug("快递单号为" + expressRadar.getExpressCode() + "的" + expressRadar.getLpcode() + "物流信息获取失败");
                }
                log.error("", e);

            }
        }
    }

    /**
     * 更新快递的预警等级及预警信息
     */
    public void updateRouteWarning() {
        // 揽件超时
        takesTimeout();

        // 配送超时
        deliveryTimeout();
        // 其它原因
        // warningList.addAll(otherWarning());


    }

    /**
     * 揽件超时
     */
    public void takesTimeout() {
        Pagination<RadarTransNoCommand> p = expressRadarManager.findTakesTimeoutExpress(0, EXPRESS_RADAR_NO_AMOUNT);
        if (p.getCount() > 0) {
            List<RadarTransNoCommand> rtn = transRouteStatusManager.takesTimeout(p.getItems());
            updateWarningStatus(rtn);
            if (p.getCount() > EXPRESS_RADAR_NO_AMOUNT) {
                for (int i = 2; i <= p.getTotalPages(); i++) {
                    Pagination<RadarTransNoCommand> pagination = expressRadarManager.findTakesTimeoutExpress((i - 1) * EXPRESS_RADAR_NO_AMOUNT, EXPRESS_RADAR_NO_AMOUNT);
                    rtn.clear();
                    rtn = transRouteStatusManager.takesTimeout(pagination.getItems());
                    updateWarningStatus(rtn);
                }
            }
        }
    }

    /**
     * 配送超时
     */
    public void deliveryTimeout() {
        Pagination<RadarTransNoCommand> p = expressRadarManager.findDeliveryTimeoutExpress(0, EXPRESS_RADAR_NO_AMOUNT);
        if (p.getCount() > 0) {
            List<RadarTransNoCommand> rtn = transRouteStatusManager.deliveryTimeout(p.getItems());
            updateWarningStatus(rtn);
            if (p.getCount() > EXPRESS_RADAR_NO_AMOUNT) {
                for (int i = 2; i <= p.getTotalPages(); i++) {
                    Pagination<RadarTransNoCommand> pagination = expressRadarManager.findDeliveryTimeoutExpress((i - 1) * EXPRESS_RADAR_NO_AMOUNT, EXPRESS_RADAR_NO_AMOUNT);
                    rtn.clear();
                    rtn = transRouteStatusManager.deliveryTimeout(pagination.getItems());
                    updateWarningStatus(rtn);
                }
            }
        }
    }


    public void updateWarningStatus(List<RadarTransNoCommand> rtn) {
        if (rtn != null) {
            for (RadarTransNoCommand r : rtn) {
                try {

                    transRouteStatusManager.updateWarningStatus(r);
                } catch (Exception e) {
                    log.error("给单号为" + r.getExpressCode() + "的快递添加预警信息失败", e);
                }
            }
        }
    }
}
