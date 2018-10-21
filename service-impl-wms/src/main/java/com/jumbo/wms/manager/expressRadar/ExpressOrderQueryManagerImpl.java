package com.jumbo.wms.manager.expressRadar;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import loxia.dao.Pagination;
import loxia.dao.Sort;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.jumbo.dao.expressRadar.RadarAreaDao;
import com.jumbo.dao.expressRadar.RadarTimeRuleDao;
import com.jumbo.dao.expressRadar.RadarTransNoDao;
import com.jumbo.dao.expressRadar.RadarWarningReasonLineDao;
import com.jumbo.dao.expressRadar.TransRouteDao;
import com.jumbo.dao.expressRadar.TransRouteStatusDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.model.command.expressRadar.ExpressDetailCommand;
import com.jumbo.wms.model.command.expressRadar.ExpressOrderQueryCommand;
import com.jumbo.wms.model.command.expressRadar.RadarWarningReasonLineCommand;
import com.jumbo.wms.model.expressRadar.RadarTimeRule;
import com.jumbo.wms.model.expressRadar.RadarTransNo;
import com.jumbo.wms.model.expressRadar.SysRadarArea;
import com.jumbo.wms.model.expressRadar.TransRoute;
import com.jumbo.wms.model.warehouse.StockTransApplication;

@Transactional
@Service("expressOrderQueryManager")
public class ExpressOrderQueryManagerImpl extends BaseManagerImpl implements ExpressOrderQueryManager {

    private static final long serialVersionUID = 6580274824169374917L;

    @Autowired
    private RadarTransNoDao radarTransNoDao;

    @Autowired
    private RadarAreaDao radarAreaDao;

    @Autowired
    private RadarTimeRuleDao radarTimeRuleDao;

    @Autowired
    private TransRouteDao transRouteDao;

    @Autowired
    private TransRouteStatusDao transRouteStatusDao;

    @Autowired
    private StockTransApplicationDao stockTransApplicationDao;

    @Autowired
    private RadarWarningReasonLineDao radarWarningReasonLineDao;

    @Autowired
    private TransRouteStatusManager transRouteStatusManager;

    @Override
    public Pagination<ExpressOrderQueryCommand> findExpressInfoList(int start, int pageSize, ExpressOrderQueryCommand expressOrderQueryCommand, Sort[] sorts) {

        // Long lpId = null;
        String lpCode = null;
        String expressCode = null;
        String pcode = null;
        // Long pwhId = null;
        String pwhName = null;
        String owner = null;
        String province = null;
        String city = null;
        String status = null;
        String warningType = null;
        String warningLv = null;
        String orderCode = null;
        Date takingTime = null;
        Date startDate = null;
        Date endDate = null;

        if (expressOrderQueryCommand != null) {
            if (null != expressOrderQueryCommand.getPwhId()) {
                // pwhId = expressOrderQueryCommand.getPwhId();
                expressOrderQueryCommand.getPwhId();
            }
            if (null != expressOrderQueryCommand.getTakingTime()) {
                takingTime = expressOrderQueryCommand.getTakingTime();
            }
            if (null != expressOrderQueryCommand.getStartDate()) {
                startDate = expressOrderQueryCommand.getStartDate();
            }

            if (null != expressOrderQueryCommand.getEndDate()) {
                endDate = expressOrderQueryCommand.getEndDate();
            }

            if (StringUtils.hasText(expressOrderQueryCommand.getLpCode()) && !("请选择".equals(expressOrderQueryCommand.getLpCode()))) {
                lpCode = expressOrderQueryCommand.getLpCode();
            }
            if (StringUtils.hasText(expressOrderQueryCommand.getPcode())) {
                pcode = expressOrderQueryCommand.getPcode();
            }
            if (StringUtils.hasText(expressOrderQueryCommand.getExpressCode())) {
                expressCode = expressOrderQueryCommand.getExpressCode();
            }
            if (StringUtils.hasText(expressOrderQueryCommand.getOrderCode())) {
                orderCode = expressOrderQueryCommand.getOrderCode();
            }
            if (StringUtils.hasText(expressOrderQueryCommand.getPwhName()) && !("请选择".equals(expressOrderQueryCommand.getPwhName()))) {
                pwhName = expressOrderQueryCommand.getPwhName();
            }
            if (StringUtils.hasText(expressOrderQueryCommand.getOwner()) && !("请选择".equals(expressOrderQueryCommand.getOwner()))) {
                owner = expressOrderQueryCommand.getOwner();
            }
            if (StringUtils.hasText(expressOrderQueryCommand.getProvince()) && !("请选择".equals(expressOrderQueryCommand.getProvince()))) {
                province = expressOrderQueryCommand.getProvince();
            }
            if (StringUtils.hasText(expressOrderQueryCommand.getCity()) && !("请选择".equals(expressOrderQueryCommand.getCity())) && !("null".equals(expressOrderQueryCommand.getCity()))) {
                city = expressOrderQueryCommand.getCity();
            }
            if (StringUtils.hasText(expressOrderQueryCommand.getStatus()) && !("请选择".equals(expressOrderQueryCommand.getStatus()))) {
                status = expressOrderQueryCommand.getStatus();
            }
            if (StringUtils.hasText(expressOrderQueryCommand.getWarningType()) && !("请选择".equals(expressOrderQueryCommand.getWarningType()))) {
                warningType = expressOrderQueryCommand.getWarningType();
            }
            if (StringUtils.hasText(expressOrderQueryCommand.getWarningLv()) && !("请选择".equals(expressOrderQueryCommand.getWarningLv()))) {
                warningLv = expressOrderQueryCommand.getWarningLv();
            }
        }

        Sort[] sortList = new Sort[2];
        sortList[0] = sorts[0];
        Sort sort1 = new Sort();
        sort1.setField("id");
        sort1.setType("desc");
        sortList[1] = sort1;

        Pagination<ExpressOrderQueryCommand> expressOrderQueryList =
                radarTransNoDao.findExpressInfoByParams(start, pageSize, lpCode, expressCode, pwhName, warningLv, orderCode, takingTime, startDate, endDate, pcode, owner, province, city, status, warningType, sortList,
                        new BeanPropertyRowMapper<ExpressOrderQueryCommand>(ExpressOrderQueryCommand.class));

        if (null != expressOrderQueryList && null != expressOrderQueryList.getItems()) {
            for (ExpressOrderQueryCommand expressOrderQuery : expressOrderQueryList.getItems()) {
                // Long expectMs = getWarningDate(expressOrderQuery, 0);
                getWarningDate(expressOrderQuery, 0);
            }
        }
        return expressOrderQueryList;
    }

    @Override
    public Long getWarningDate(ExpressOrderQueryCommand expressOrderQuery, int flag) {

        if (null == expressOrderQuery) {
            return 0L;
        }

        Integer standardDate = null;
        Long actualDate = null;
        Date actualTakingTime = null;
        String overTakingTime = null;
        String city = null;
        String province = null;
        Long expressPwhId = null;
        Date operateTime = null;
        Long sraId = null;
        Long addDate = 0L;
        String expressCode = null;
        Long tsId = null;
        Integer timeType = null;
        SysRadarArea sra = null;
        RadarTimeRule radarTimeRule = null;

        if (null != expressOrderQuery.getLpId()) {
            tsId = expressOrderQuery.getLpId();
        }

        if (null != expressOrderQuery.getTimeType()) {
            timeType = expressOrderQuery.getTimeType();
        }

        if (null != expressOrderQuery.getPwhId()) {
            expressPwhId = expressOrderQuery.getPwhId();
        }
        if (null != expressOrderQuery.getCity()) { // 返回哪一天是超时时间
            city = expressOrderQuery.getCity();
        }
        if (null != expressOrderQuery.getProvince()) {
            province = expressOrderQuery.getProvince();
        }
        if (null == province && null == city) {
            return 0L;
        }
        try {
            if (flag == 2) {
                if (province.endsWith("省") || province.endsWith("市")) {
                    province = province.substring(0, province.length() - 1);
                }
                if (city != null && city.endsWith("市")) {
                    city = city.substring(0, city.length() - 1);
                }

                List<SysRadarArea> sraList = radarAreaDao.findRadarAreaByVagueProvinceCity(province, city, new BeanPropertyRowMapper<SysRadarArea>(SysRadarArea.class));
                if (sraList != null && !sraList.isEmpty()) {
                    sraId = sraList.get(0).getId();
                } else {
                    sraList = radarAreaDao.findRadarAreaByVagueProvinceCity(province, null, new BeanPropertyRowMapper<SysRadarArea>(SysRadarArea.class));
                    if (sraList != null && !sraList.isEmpty()) {
                        sraId = sraList.get(0).getId();
                    }
                }
            } else {

                sra = radarAreaDao.findRadarAreaByProvinceCity(province, city, new BeanPropertyRowMapper<SysRadarArea>(SysRadarArea.class));
                if (sra != null) {
                    sraId = sra.getId();
                } else {
                    sraId = radarAreaDao.findRadarAreaByProvinceCity(province, null, new BeanPropertyRowMapper<SysRadarArea>(SysRadarArea.class)).getId();
                }
            }

            if (null == expressPwhId || null == tsId || null == timeType) {
                return 0L;
            }

            // RadarTimeRule radarTimeRule =
            // radarTimeRuleDao.findRadarTimeRuleByPwhIdAreaId(expressPwhId, sraId, tsId, timeType,
            // new BeanPropertyRowMapper<RadarTimeRule>(RadarTimeRule.class));
            List<RadarTimeRule> radarTimeRules = radarTimeRuleDao.findRadarTimeRuleByPwhIdAreaId(expressPwhId, sraId, tsId, timeType, new BeanPropertyRowMapper<RadarTimeRule>(RadarTimeRule.class));
            if (null == radarTimeRules || radarTimeRules.isEmpty()) {
                if (null == sraId) {
                    return 0L;
                }
                radarTimeRules = radarTimeRuleDao.findRadarTimeRuleByPwhIdAreaId(expressPwhId, null, tsId, timeType, new BeanPropertyRowMapper<RadarTimeRule>(RadarTimeRule.class));
            }
            if (null != radarTimeRules && !radarTimeRules.isEmpty()) {
                radarTimeRule = radarTimeRules.get(0);
                standardDate = radarTimeRule.getStandardDate();
                overTakingTime = radarTimeRule.getOverTakingTime();
                String[] array = overTakingTime.split(":");
                Integer standardHour = Integer.parseInt(array[0]);
                Integer standardMinute = Integer.parseInt(array[1]);
                actualTakingTime = expressOrderQuery.getTakingTime();

                if (null == actualTakingTime) {
                    return 0L;
                }

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(actualTakingTime);
                Integer actualHour = calendar.get(Calendar.HOUR_OF_DAY);
                Integer actualMinute = calendar.get(Calendar.MINUTE);
                Integer actualSecond = calendar.get(Calendar.SECOND);

                Long totalStandard = standardHour * 60L + standardMinute;
                Long totalActual = actualHour * 60L + actualMinute;

                if (totalStandard.compareTo(totalActual) == -1) {
                    // 标准时效加一天
                    standardDate = standardDate + 1;
                    addDate = 1L;
                }

                // 期望收货时间（毫秒）
                Long standardMillisecond = actualTakingTime.getTime() + standardDate.longValue() * 86400000L - (actualHour * 60L + actualMinute) * 60000L - actualSecond * 1000L + 86400000L;
                if (flag == 2) {
                    return standardMillisecond;
                }
                expressCode = expressOrderQuery.getExpressCode();
                List<TransRoute> trList = transRouteDao.findTransRouteByExpressCode(expressCode, new BeanPropertyRowMapper<TransRoute>(TransRoute.class));
                if (null != trList && trList.size() > 0) {
                    if ("签收".equals(trList.get(trList.size() - 1).getOpcode()) || "80".equals(trList.get(trList.size() - 1).getOpcode()) || "已签收".equals(trList.get(trList.size() - 1).getOpcode())) {
                        operateTime = trList.get(trList.size() - 1).getOperateTime();
                    } else {
                        operateTime = new Date();
                    }
                    // 实际收货时间（天）
                    actualDate = (operateTime.getTime() - actualTakingTime.getTime() + (actualHour * 60L + actualMinute) * 60000L + actualSecond * 1000L) / 86400000L - addDate;
                    if (0L > actualDate) {
                        actualDate = 0L;
                    }
                    if (flag == 0) {
                        expressOrderQuery.setStandardDate(radarTimeRule.getStandardDate().longValue()); // 插入标准时效
                        expressOrderQuery.setActualDate(actualDate);
                        return 0L;
                    }
                    return (operateTime.getTime() - standardMillisecond);
                } else
                    return 0L;
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return 0L;
    }

    @Override
    public RadarTransNo findRadarTransNoDetail(Long id) {
        String refCode = null;
        RadarTransNo radar = radarTransNoDao.findRadarTransNoById(id, new BeanPropertyRowMapper<RadarTransNo>(RadarTransNo.class));
        if (null == radar.getMobile()) {
            radar.setMobile(radar.getTelephone());
        }
        radar.setTelephone(refCode);
        try {
            StockTransApplication sta = stockTransApplicationDao.getByCode(radar.getOrderCode());
            if (null != sta) {
                refCode = stockTransApplicationDao.getByCode(radar.getOrderCode()).getRefSlipCode();
                if (null != refCode) {
                    radar.setTelephone(refCode);
                }
            }
        } catch (Exception e) {

        }
        return radar;
    }

    @Override
    public List<ExpressDetailCommand> findExpressDetail(String expressCode) {
        List<ExpressDetailCommand> expressDetailList = transRouteStatusDao.findExpressDetailByExpressCode(expressCode, new BeanPropertyRowMapper<ExpressDetailCommand>(ExpressDetailCommand.class));
        ExpressDetailCommand warningDetail = transRouteStatusDao.findExpressWarningByExpressCode(expressCode, new BeanPropertyRowMapper<ExpressDetailCommand>(ExpressDetailCommand.class));
        if (null != expressDetailList && expressDetailList.size() > 0) {
            if (null != warningDetail) {
                for (ExpressDetailCommand expressDetail : expressDetailList) {
                    long expressDetailId = expressDetail.getId();
                    long warningDetailId = warningDetail.getId();
                    if (expressDetailId == warningDetailId) {
                        expressDetail.setWarningType(warningDetail.getWarningType());
                        expressDetail.setWarningLv(warningDetail.getWarningLv());
                        expressDetail.setModifyUser(warningDetail.getModifyUser());
                    }
                }
            }
        }

        return expressDetailList;
    }

    @Override
    public List<ExpressDetailCommand> findOrderDetail(String orderCode) {
        List<ExpressDetailCommand> expressDetailList = transRouteStatusDao.findOrderDetailByOrderCode(orderCode, new BeanPropertyRowMapper<ExpressDetailCommand>(ExpressDetailCommand.class));
        return expressDetailList;
    }

    @Override
    public List<TransRoute> findLogisticsDetail(String expressCode) {
        List<TransRoute> transRouteList = transRouteDao.findTransRouteByExpressCode(expressCode, new BeanPropertyRowMapper<TransRoute>(TransRoute.class));
        return transRouteList;
    }

    @Override
    public String updateWarningType(String expressCode, Long wrId, String remark, Long userId, Long lvId) {
        try {
            transRouteStatusDao.updateRouteStatusWarningReason(expressCode, wrId, remark, userId);
            radarTransNoDao.updateRdTransNoWarning(expressCode, lvId, wrId);
            transRouteStatusManager.addTransRouteWarningLog(expressCode, userId, wrId, lvId);
            return "SUCCESS";
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return "ERROR";
        }
    }

    @Override
    public List<RadarWarningReasonLineCommand> findOrderWarningLv(String errorCode) {
        return radarWarningReasonLineDao.findWarningReasonLineByErrorCode(errorCode, new BeanPropertyRowMapper<RadarWarningReasonLineCommand>(RadarWarningReasonLineCommand.class));
    }

}
