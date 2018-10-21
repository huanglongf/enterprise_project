package com.jumbo.wms.manager.expressRadar;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import loxia.dao.Pagination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.dao.baseinfo.TransportatorDao;
import com.jumbo.dao.expressRadar.RadarRouteStatusRefDao;
import com.jumbo.dao.expressRadar.SysRouteStatusCodeDao;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.model.baseinfo.Transportator;
import com.jumbo.wms.model.command.expressRadar.RadarRouteStatusRefCommand;
import com.jumbo.wms.model.expressRadar.RadarRouteStatusRef;
import com.jumbo.wms.model.expressRadar.SysRouteStatusCode;

/**
 * @author lihui 快递状态代码查询 2015年5月26日 下午5:44:00
 */
@Transactional
@Service("expressStatusCodeManager")
public class ExpressStatusCodeManagerImpl extends BaseManagerImpl implements ExpressStatusCodeManager {

    /**
	 * 
	 */
    private static final long serialVersionUID = -7827734799762661443L;

    @Autowired
    private RadarRouteStatusRefDao radarRouteStatusRefDao;
    @Autowired
    private TransportatorDao transportatorDao;
    @Autowired
    private SysRouteStatusCodeDao sysRouteStatusCodeDao;

    /**
     * 根据参数查询快递状态代码，并分页
     * 
     * @param start
     * @param pageSize
     * @param params
     * @return
     */
    public Pagination<RadarRouteStatusRefCommand> findExpressStatusCodeByParams(int start, int pageSize, Map<String, Object> params) {
        return radarRouteStatusRefDao.findRadarStatusRefByParams(start, pageSize, params, new BeanPropertyRowMapper<RadarRouteStatusRefCommand>(RadarRouteStatusRefCommand.class));
    }

    /**
     * 保存快递运单路由状态关联
     * 
     * @param radarRouteStatusRef
     * @return
     */
    public String saveRRSR(RadarRouteStatusRefCommand radarRouteStatusRefCommand) {
        String tCode = radarRouteStatusRefCommand.getExpCode();
        Long cId = radarRouteStatusRefCommand.getSysRscId();


        Transportator tran = transportatorDao.findByCode(tCode);
        SysRouteStatusCode srsc = sysRouteStatusCodeDao.getByPrimaryKey(cId);
        if (tran == null || srsc == null) {
            return "error";
        }

        RadarRouteStatusRef rrsr = new RadarRouteStatusRef();
        rrsr.setSysRouteStatusCode(srsc);
        rrsr.setTransportator(tran);
        rrsr.setRemark(radarRouteStatusRefCommand.getRemark());
        rrsr.setUser(radarRouteStatusRefCommand.getUser());
        rrsr.setCreareTime(new Date());
        rrsr.setLastModifyTime(new Date());
        rrsr.setStatus(1);


        Map<String, Object> params = new HashMap<String, Object>();
        params.put("logisticsCode", tCode);
        params.put("sysRscId", cId);
        Pagination<RadarRouteStatusRefCommand> pagination = radarRouteStatusRefDao.findRadarStatusRefByParams(-1, -1, params, new BeanPropertyRowMapper<RadarRouteStatusRefCommand>(RadarRouteStatusRefCommand.class));

        // 判断是否已经存在此数据
        if (pagination != null && pagination.getItems() != null && pagination.getItems().size() > 0) {
            RadarRouteStatusRefCommand rrsrc = pagination.getItems().get(0);
            Integer status = rrsrc.getStatus();
            // 判断状态是否为禁用
            if (status == null || 1 == status) {
                // 返回数据已存在提示
                return "exist";
            } else {
                // 修改状态为使用，并返回添加成功提示
                radarRouteStatusRefDao.save(rrsr);
                return "success";
            }
        } else {
            // 保存数据
            radarRouteStatusRefDao.save(rrsr);
            return "success";
        }

    }

    /**
     * 修改快递运单路由状态关联
     * 
     * @param radarRouteStatusRef
     */
    public void updateRRSR(Map<String, Object> params, List<Long> ids) {
        Integer status = (Integer) params.get("status");
        String remark = (String) params.get("remark");
        // 修改状态
        if (status != null && status != 0) {
            radarRouteStatusRefDao.updateRadarStatusRefStatus(status, ids);
        }
        // 修改备注
        if (remark != null && !"".equals(remark)) {
            radarRouteStatusRefDao.updateRadarStatusRefRemark(remark, ids);
        }
    }

}
