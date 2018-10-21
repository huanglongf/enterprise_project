package com.jumbo.wms.manager.expressRadar;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import loxia.dao.Pagination;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import cn.baozun.bh.connector.es.model.LogisticsQueryResponse;
import cn.baozun.bh.connector.sf.rmi.RmiService;

import com.jumbo.dao.expressRadar.RadarRouteStatusRefDao;
import com.jumbo.dao.expressRadar.RadarTransNoDao;
import com.jumbo.dao.expressRadar.TransRouteDao;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.model.baseinfo.Transportator;
import com.jumbo.wms.model.command.expressRadar.RadarRouteStatusRefCommand;
import com.jumbo.wms.model.command.expressRadar.RadarTransNoCommand;
import com.jumbo.wms.model.expressRadar.RadarRouteStatusRef;
import com.jumbo.wms.model.expressRadar.RadarTransNo;
import com.jumbo.wms.model.expressRadar.TransRoute;
import com.jumbo.wms.model.expressRadar.TransRouteStatus;

@Transactional
@Service("expressRadarManager")
public class ExpressRadarManagerImpl extends BaseManagerImpl implements ExpressRadarManager {

    /**
	 * 
	 */
    private static final long serialVersionUID = -6157301206734115045L;
    Logger log = LoggerFactory.getLogger(ExpressRadarManagerImpl.class);

    @Autowired
    private RadarTransNoDao radarTransNoDao;
    @Autowired
    private TransRouteDao transRouteDao;
    @Autowired
    private TransRouteManager transRouteManager;
    @Autowired
    private RadarRouteStatusRefDao radarRouteStatusRefDao;
    @Autowired
    private TransRouteStatusManager transRouteStatusManager;
    @Autowired
    private RmiService sfRmiService;
    private String lang = "zh-CN";// en英文，zh-CN中文简体，zh-HK中文繁体



    /**
     * 根据订单号更新快递
     * 
     * @param erList
     */
    public void expressDispose(RadarTransNoCommand expressRadar) {


        List<TransRoute> otherList = expressInfoAcquire(expressRadar.getExpressCode(), expressRadar.getLpcode(), expressRadar.getId());
        List<TransRoute> okList = new ArrayList<TransRoute>();// 不重复可插入的物流信息
        if (otherList != null) {
            for (TransRoute erd : otherList) {
                String expressCode = erd.getExpressCode();
                Date date = erd.getOperateTime();
                if (StringUtils.hasText(expressCode) && date != null) {

                    // 判断是否重复
                    Long eList = transRouteDao.getTransRouteByParam(expressCode, date);
                    if (eList < 1) {
                        okList.add(erd);// 可插入的信息
                    }
                    // 判断快递是否需要改状态
                    Integer status = expressStatus(erd.getOpcode(), expressRadar.getLpcode());
                    if (status != null) {
                        updateExpressRadarBatch(expressCode, status);// 修改快递状态
                    }
                    transRouteManager.saveTransRouteList(okList);// 批量添加快递明细
                    transRouteDao.flush();
                    saveTransRouteStatus(okList);
                }
            }
        }
    }



    /**
     * 其他物流信息获取
     * 
     * @param expressCode
     * @return
     */
    public List<TransRoute> expressInfoAcquire(String expressCode, String lpcode, Long noId) {
        if (expressCode != null && !"".equals(expressCode) && lpcode != null && !"".equals(lpcode)) {
            RadarTransNo rtn = radarTransNoDao.getByPrimaryKey(noId);
            if (Transportator.EMS.equals(lpcode)) {

                String expressInfo = sfRmiService.logisticsRouteQuery(lang, expressCode, LogisticsQueryResponse.LOGISTICS_NAME_EMS);

                return ExpressInfoParseUtils.getTransRouteByEMS(expressInfo, rtn);
            } else if (Transportator.SF.equals(lpcode)) {
                String expressInfo = sfRmiService.logisticsRouteQuery(lang, expressCode, LogisticsQueryResponse.LOGISTICS_NAME_SF);
                return ExpressInfoParseUtils.getTransRouteBySF(expressInfo, rtn);
            } else if (Transportator.STO.equals(lpcode)) {
                String expressInfo = sfRmiService.logisticsRouteQuery(lang, expressCode, LogisticsQueryResponse.LOGISTICS_NAME_STO);
                return ExpressInfoParseUtils.getTransRouteBySTO(expressInfo, rtn);
            } else {
                log.info("快递雷达：系统暂时不支持" + lpcode + "物流商！");
            }


        }
        return null;
    }

    /**
     * 根据不同的物流商的快递明细状态判断是否需要修改快递的状态 未完成则返回null 完成则返回状态值
     * 
     * @param status
     * @return
     */
    public Integer expressStatus(String status, String lpcode) {
        Integer ret = null;
        RadarRouteStatusRefCommand rsr = radarRouteStatusRefDao.findRouteStatusRoleByExpressStatus(status, lpcode, new BeanPropertyRowMapper<RadarRouteStatusRefCommand>(RadarRouteStatusRefCommand.class));
        if (rsr != null && rsr.getCode() != null) {
            if ("100".equals(rsr.getCode())) {
                ret = 10;
            }
        }
        return ret;

    }

    /**
     * 批量修改快递状态
     * 
     * @param:快递单号
     * @param:状态值
     */
    public void updateExpressRadarBatch(String expressCode, Integer status) {
        radarTransNoDao.updateRadarTransNoStatus(expressCode, status);
    }

    /**
     * 添加快递运单路由状态
     * 
     * @param transRouteList
     */
    public void saveTransRouteStatus(List<TransRoute> transRouteList) {
        if (transRouteList != null) {
            List<TransRouteStatus> trsList = new ArrayList<TransRouteStatus>();
            Set<String> expressCodeSet = new HashSet<String>();
            for (TransRoute transRoute : transRouteList) {
                expressCodeSet.add(transRoute.getExpressCode());
                String opcode = transRoute.getOpcode();
                RadarTransNo rtn = transRoute.getRadarTransNo();
                if (opcode != null && !"".equals(opcode) && rtn != null && transRoute.getId() != null) {
                    // 获取路由状态
                    RadarRouteStatusRefCommand rrsrc = radarRouteStatusRefDao.findRadarRouteStatusRefByExpressStatus(opcode, rtn.getLpcode(), new BeanPropertyRowMapper<RadarRouteStatusRefCommand>(RadarRouteStatusRefCommand.class));

                    RadarRouteStatusRefCommand rsr = radarRouteStatusRefDao.findRouteStatusRoleByExpressStatus(opcode, rtn.getLpcode(), new BeanPropertyRowMapper<RadarRouteStatusRefCommand>(RadarRouteStatusRefCommand.class));
                    if (rsr != null && rsr.getCode() != null) {
                        String code = rsr.getCode();
                        // 判断是否是揽件状态
                        if ("010".equals(code)) {
                            if (rtn.getTakingTime() == null) {
                                rtn.setTakingTime(transRoute.getOperateTime());
                                radarTransNoDao.save(rtn);
                            } else {
                                continue;
                            }
                        }
                    }
                    if (rrsrc != null) {
                        RadarRouteStatusRef r = radarRouteStatusRefDao.getByPrimaryKey(rrsrc.getId());
                        TransRouteStatus trs = new TransRouteStatus();
                        trs.setCreareTime(new Date());
                        trs.setLastModifyTime(new Date());
                        trs.setRadarRouteStatusRef(r);
                        trs.setTransRoute(transRoute);
                        trsList.add(trs);
                    }

                }
            }
            transRouteStatusManager.saveTransRouteStatusList(trsList);
            for (String expressCode : expressCodeSet) {
                Long routeStatusId = radarTransNoDao.findRouteStatusByTimeAndExpressCode(expressCode, new SingleColumnRowMapper<Long>(Long.class));
                radarTransNoDao.updateRdTransNoRouteStatus(expressCode, routeStatusId);
            }
        }

    }

    /**
     * 修改快递为出库状态
     * 
     * @param transRouteList
     */
    public void updateOutBound(List<TransRoute> transRouteList) {
        try {
            if (transRouteList != null) {
                List<TransRouteStatus> trsList = new ArrayList<TransRouteStatus>();
                Set<String> expressCodeSet = new HashSet<String>();
                for (TransRoute transRoute : transRouteList) {
                    try {

                        expressCodeSet.add(transRoute.getExpressCode());
                        String opcode = transRoute.getOpcode();
                        RadarTransNo rtn = transRoute.getRadarTransNo();
                        if (opcode != null && !"".equals(opcode) && rtn != null) {
                            RadarRouteStatusRefCommand rrsrc = radarRouteStatusRefDao.findRadarRouteStatusRefByCode("001", rtn.getLpcode(), new BeanPropertyRowMapper<RadarRouteStatusRefCommand>(RadarRouteStatusRefCommand.class));
                            if (rrsrc != null) {
                                RadarRouteStatusRef r = radarRouteStatusRefDao.getByPrimaryKey(rrsrc.getId());

                                TransRouteStatus trs = new TransRouteStatus();
                                trs.setCreareTime(new Date());
                                trs.setLastModifyTime(new Date());
                                trs.setRadarRouteStatusRef(r);
                                trs.setTransRoute(transRoute);
                                trsList.add(trs);
                            }

                        }
                    } catch (Exception e) {
                        log.error(e.getMessage(), e);
                    }
                }
                transRouteStatusManager.saveTransRouteStatusList(trsList);
                for (String expressCode : expressCodeSet) {
                    Long routeStatusId = radarTransNoDao.findRouteStatusByTimeAndExpressCode(expressCode, new SingleColumnRowMapper<Long>(Long.class));
                    radarTransNoDao.updateRdTransNoRouteStatus(expressCode, routeStatusId);
                }
            }

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

    }

    /**
     * 分页获取可能揽件超时的快递
     * 
     * @param start
     * @param pageSize
     * @return
     */
    public Pagination<RadarTransNoCommand> findTakesTimeoutExpress(int start, int pageSize) {
        return radarTransNoDao.findTakesTimeoutExpress(start, pageSize, new BeanPropertyRowMapper<RadarTransNoCommand>(RadarTransNoCommand.class));
    }

    /**
     * 分页获取可能配送超时的快递
     * 
     * @param start
     * @param pageSize
     * @return
     */
    public Pagination<RadarTransNoCommand> findDeliveryTimeoutExpress(int start, int pageSize) {
        return radarTransNoDao.findDeliveryTimeoutExpress(start, pageSize, new BeanPropertyRowMapper<RadarTransNoCommand>(RadarTransNoCommand.class));
    }

}
