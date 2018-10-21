package com.jumbo.wms.manager.expressRadar;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.dao.expressRadar.RadarTransNoDao;
import com.jumbo.dao.expressRadar.RadarWarningReasonDao;
import com.jumbo.dao.expressRadar.RadarWarningReasonLineDao;
import com.jumbo.dao.expressRadar.TransRouteStatusDao;
import com.jumbo.dao.expressRadar.TransRouteWarningLogDao;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.model.command.expressRadar.ExpressOrderQueryCommand;
import com.jumbo.wms.model.command.expressRadar.RadarTransNoCommand;
import com.jumbo.wms.model.command.expressRadar.RadarWarningReasonLineCommand;
import com.jumbo.wms.model.expressRadar.RadarRouteStatusRef;
import com.jumbo.wms.model.expressRadar.RadarWarningReason;
import com.jumbo.wms.model.expressRadar.TransRouteStatus;
import com.jumbo.wms.model.expressRadar.TransRouteWarningLog;

/**
 * @author lihui
 *
 *         2015年6月3日 上午11:14:22
 */
@Transactional
@Service("transRouteStatusManager")
public class TransRouteStatusManagerImpl extends BaseManagerImpl implements TransRouteStatusManager {

    /**
     * 
     */
    private static final long serialVersionUID = 8073044740018027318L;


    Logger log = LoggerFactory.getLogger(TransRouteStatusManagerImpl.class);


    @Autowired
    private TransRouteStatusDao transRouteStatusDao;
    @Autowired
    private RadarTransNoDao radarTransNoDao;
    @Autowired
    private RadarWarningReasonLineDao radarWarningReasonLineDao;
    @Autowired
    private ExpressOrderQueryManager expressOrderQueryManager;
    @Autowired
    private RadarWarningReasonDao radarWarningReasonDao;
    @Autowired
    private TransRouteWarningLogDao transRouteWarningLogDao;

    /**
     * 批量保存快递运单路由状态
     * 
     * @param erdList
     */
    public void saveTransRouteStatusList(List<TransRouteStatus> trsList) {
        if (trsList != null && !trsList.isEmpty()) {
            for (TransRouteStatus transRouteStatus : trsList) {
                transRouteStatusDao.save(transRouteStatus);
            }
            transRouteStatusDao.flush();
        }

    }



    /**
     * 更新快递预警信息
     * 
     * @param warningList
     */
    public void updateWarningStatus(RadarTransNoCommand rtnc) {
        if (rtnc != null) {
            // 更新快递的预警等级及预警信息
            transRouteStatusDao.updateRouteStatusWarningReason(rtnc.getExpressCode(), rtnc.getRadarWarningReasonId(), null, null);
            radarTransNoDao.updateRdTransNoWarning(rtnc.getExpressCode(), rtnc.getSysRadarWarningLvId(), rtnc.getRadarWarningReasonId());
            addTransRouteWarningLog(rtnc.getExpressCode(), null, rtnc.getRadarWarningReasonId(), rtnc.getSysRadarWarningLvId());
        }
    }

    /**
     * 揽件超时
     */
    public List<RadarTransNoCommand> takesTimeout(List<RadarTransNoCommand> rtncCommands) {
        try {

            // 获取揽件预警超时时间
            List<RadarWarningReasonLineCommand> rwrlc = radarWarningReasonLineDao.findWarningReasonLineByErrorCode("E00001", new BeanPropertyRowMapper<RadarWarningReasonLineCommand>(RadarWarningReasonLineCommand.class));
            // 需要预警的快递
            List<RadarTransNoCommand> warningList = new ArrayList<RadarTransNoCommand>();
            if (rtncCommands != null && !rtncCommands.isEmpty() && rwrlc != null && !rwrlc.isEmpty()) {
                for (RadarTransNoCommand radarTransNoCommand : rtncCommands) {
                    long hour = getDatePoor(radarTransNoCommand.getCreateTime());
                    if (rwrlc.size() != 1) {

                        for (int i = 1; i < rwrlc.size(); i++) {
                            RadarWarningReasonLineCommand rwCommand = rwrlc.get(i);
                            RadarWarningReasonLineCommand rwrl = rwrlc.get(rwrlc.size() - 1);
                            if ((int) hour < rwCommand.getWarningHour() && (int) hour >= rwrlc.get(0).getWarningHour()) {
                                radarTransNoCommand.setRadarWarningReasonId(rwrlc.get(i - 1).getWrId());
                                radarTransNoCommand.setSysRadarWarningLvId(rwrlc.get(i - 1).getLvId());
                                warningList.add(radarTransNoCommand);
                                break;
                            } else if ((int) hour >= rwrl.getWarningHour()) {
                                radarTransNoCommand.setRadarWarningReasonId(rwrl.getWrId());
                                radarTransNoCommand.setSysRadarWarningLvId(rwrl.getLvId());
                                warningList.add(radarTransNoCommand);
                                break;
                            }

                        }
                    } else {
                        if ((int) hour >= rwrlc.get(0).getWarningHour()) {
                            RadarWarningReasonLineCommand rwCommand = rwrlc.get(0);
                            radarTransNoCommand.setRadarWarningReasonId(rwCommand.getWrId());
                            radarTransNoCommand.setSysRadarWarningLvId(rwCommand.getLvId());
                            warningList.add(radarTransNoCommand);
                        }
                    }
                }


            }
            return warningList;
        } catch (Exception e) {
            log.error("揽件超时预警更新失败！", e);
            return null;
        }

    }

    /**
     * 配送超时
     */
    public List<RadarTransNoCommand> deliveryTimeout(List<RadarTransNoCommand> rtncCommands) {
        try {

            // 获取配送预警超时时间
            List<RadarWarningReasonLineCommand> rwrlc = radarWarningReasonLineDao.findWarningReasonLineByErrorCode("E00002", new BeanPropertyRowMapper<RadarWarningReasonLineCommand>(RadarWarningReasonLineCommand.class));
            // 需要预警的快递
            List<RadarTransNoCommand> warningList = new ArrayList<RadarTransNoCommand>();
            if (rtncCommands != null && !rtncCommands.isEmpty() && rwrlc != null) {
                for (RadarTransNoCommand radarTransNoCommand : rtncCommands) {
                    ExpressOrderQueryCommand eoqc = new ExpressOrderQueryCommand();
                    eoqc.setCity(radarTransNoCommand.getDestinationCity());
                    eoqc.setProvince(radarTransNoCommand.getDestinationProvince());
                    eoqc.setExpressCode(radarTransNoCommand.getExpressCode());
                    eoqc.setPwhId(radarTransNoCommand.getPhysicalWarehouseId());
                    eoqc.setTakingTime(radarTransNoCommand.getTakingTime());
                    eoqc.setLpCode(radarTransNoCommand.getLpcode());
                    eoqc.setLpId(radarTransNoCommand.getTpId());
                    eoqc.setTimeType(radarTransNoCommand.getTransTimeType());
                    // 获取揽件后配送的时间
                    Long msec = expressOrderQueryManager.getWarningDate(eoqc, 1);
                    if (msec != null && msec > 0) {

                        Long hour = msec / 3600000L;

                        if (rwrlc.size() != 1) {

                            for (int i = 1; i < rwrlc.size(); i++) {
                                RadarWarningReasonLineCommand rwCommand = rwrlc.get(i);
                                RadarWarningReasonLineCommand rwrl = rwrlc.get(rwrlc.size() - 1);
                                if (hour < rwCommand.getWarningHour() && hour >= rwrlc.get(0).getWarningHour()) {
                                    radarTransNoCommand.setRadarWarningReasonId(rwrlc.get(i - 1).getWrId());
                                    radarTransNoCommand.setSysRadarWarningLvId(rwrlc.get(i - 1).getLvId());
                                    warningList.add(radarTransNoCommand);
                                    break;
                                } else if (hour >= rwrl.getWarningHour()) {
                                    radarTransNoCommand.setRadarWarningReasonId(rwrl.getWrId());
                                    radarTransNoCommand.setSysRadarWarningLvId(rwrl.getLvId());
                                    warningList.add(radarTransNoCommand);
                                    break;
                                }
                            }
                        } else {
                            if (hour >= rwrlc.get(0).getWarningHour()) {
                                RadarWarningReasonLineCommand rwCommand = rwrlc.get(0);
                                radarTransNoCommand.setRadarWarningReasonId(rwCommand.getWrId());
                                radarTransNoCommand.setSysRadarWarningLvId(rwCommand.getLvId());
                                warningList.add(radarTransNoCommand);
                                break;
                            }
                        }
                    }
                }


            }
            return warningList;
        } catch (Exception e) {
            log.error("配送超时预警状态更新失败！");
            return null;
        }
    }

    /**
     * 其它预警原因
     */
    public List<RadarTransNoCommand> otherWarning() {
        try {

            String[][] statusCode = { {"", "E01001"}, {"", "E02001"}, {"", "E03001"}, {"", "E03002"}, {"", "E03003"}};
            // 需要预警的快递
            List<RadarTransNoCommand> warningList = new ArrayList<RadarTransNoCommand>();
            for (int i = 0; i < statusCode.length; i++) {
                List<RadarTransNoCommand> rtncCommands = radarTransNoDao.findOtherWarningExpress(statusCode[i][0], new BeanPropertyRowMapper<RadarTransNoCommand>(RadarTransNoCommand.class));
                // 获取揽件预警超时时间
                List<RadarWarningReasonLineCommand> rwrlc = radarWarningReasonLineDao.findWarningReasonLineByErrorCode(statusCode[i][1], new BeanPropertyRowMapper<RadarWarningReasonLineCommand>(RadarWarningReasonLineCommand.class));
                if (rtncCommands != null && !rtncCommands.isEmpty() && rwrlc != null) {

                    for (RadarTransNoCommand radarTransNoCommand : rtncCommands) {
                        long hour = getDatePoor(radarTransNoCommand.getCreateTime());
                        if (rwrlc.size() != 1) {

                            for (int y = 1; y < rwrlc.size(); y++) {
                                RadarWarningReasonLineCommand rwCommand = rwrlc.get(y);
                                RadarWarningReasonLineCommand rwrl = rwrlc.get(rwrlc.size() - 1);
                                if ((int) hour < rwCommand.getWarningHour() && (int) hour >= rwrlc.get(0).getWarningHour()) {
                                    radarTransNoCommand.setRadarWarningReasonId(rwrlc.get(i - 1).getWrId());
                                    radarTransNoCommand.setSysRadarWarningLvId(rwrlc.get(i - 1).getLvId());
                                    warningList.add(radarTransNoCommand);
                                    break;
                                } else if ((int) hour >= rwrl.getWarningHour()) {
                                    radarTransNoCommand.setRadarWarningReasonId(rwrl.getWrId());
                                    radarTransNoCommand.setSysRadarWarningLvId(rwrl.getLvId());
                                    warningList.add(radarTransNoCommand);
                                    break;
                                }
                            }
                        } else {
                            if ((int) hour >= rwrlc.get(0).getWarningHour()) {
                                RadarWarningReasonLineCommand rwCommand = rwrlc.get(0);
                                radarTransNoCommand.setRadarWarningReasonId(rwCommand.getWrId());
                                radarTransNoCommand.setSysRadarWarningLvId(rwCommand.getLvId());
                                warningList.add(radarTransNoCommand);
                                break;
                            }
                        }
                    }


                }
            }
            return warningList;
        } catch (Exception e) {
            log.error("其它预警状态更新失败！");
            return null;
        }
    }

    /** 获取两个时间的时间查 如1天2小时30分钟 */
    public long getDatePoor(Date startDate) {

        // long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        // long nm = 1000 * 60;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = new Date().getTime() - startDate.getTime();
        // 计算差多少天
        // long day = diff / nd;
        // 计算差多少小时
        long hour = diff / nh;
        // 计算差多少分钟
        // long min = diff % nh;
        // 计算差多少秒//输出结果
        // long sec = diff % nd % nh % nm / ns;

        return hour;
    }

    /**
     * 
     * @param expressCode
     * @param userId
     * @param wrId
     * @param lvId
     */
    public void addTransRouteWarningLog(String expressCode, Long userId, Long wrId, Long lvId) {
        try {
            TransRouteWarningLog trwl = new TransRouteWarningLog();
            List<TransRouteStatus> trsList = transRouteStatusDao.getTransRouteStatusByExpressCode(expressCode);
            TransRouteStatus trs = trsList.get(trsList.size() - 1);
            RadarWarningReason rwr = radarWarningReasonDao.getByPrimaryKey(wrId);
            RadarRouteStatusRef rrsr = trs.getRadarRouteStatusRef();

            trwl.setCreareTime(trs.getCreareTime());
            trwl.setLastModifyTime(new Date());
            trwl.setTrId(trs.getTransRoute().getId());
            trwl.setRscId(rrsr.getSysRouteStatusCode().getId());
            trwl.setModifyUserId(userId);
            trwl.setWarningCreaterId(userId);
            trwl.setRemark(trs.getRemark());
            trwl.setWarningLv(lvId);
            trwl.setErrorCode(rwr.getRec().getCode());
            trwl.setErrorName(rwr.getRec().getName());
            trwl.setWarningName(rwr.getName());

            transRouteWarningLogDao.save(trwl);
            transRouteWarningLogDao.flush();
        } catch (Exception e) {
            log.debug("快递雷达路由状态日志添加失败！");
        }
    }
}
