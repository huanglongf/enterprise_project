package com.jumbo.wms.manager.expressRadar;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.dao.expressRadar.SysRouteStatusCodeDao;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.model.expressRadar.SysRouteStatusCode;

/**
 * @author lihui 系统路由状态编码 2015年5月26日 下午5:44:00
 */
@Transactional
@Service("sysRouteStatusCodeManager")
public class SysRouteStatusCodeManagerImpl extends BaseManagerImpl implements SysRouteStatusCodeManager {



    /**
	 * 
	 */
    private static final long serialVersionUID = -792084360932942170L;
    @Autowired
    private SysRouteStatusCodeDao sysRouteStatusCodeDao;

    /**
     * 根据参数获取状态编码列表
     * 
     * @param params
     * @return
     */
    public List<SysRouteStatusCode> findRouteStatusCodeByParam(Map<String, Object> params) {
        return sysRouteStatusCodeDao.findRouteStatusCodeByParam(params, new BeanPropertyRowMapper<SysRouteStatusCode>(SysRouteStatusCode.class));
    }


}
