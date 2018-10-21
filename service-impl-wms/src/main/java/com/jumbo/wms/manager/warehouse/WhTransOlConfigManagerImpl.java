package com.jumbo.wms.manager.warehouse;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.dao.baseinfo.TransportatorDao;
import com.jumbo.dao.warehouse.WhTransOlConfigDao;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.model.baseinfo.Transportator;
import com.jumbo.wms.model.warehouse.WhTransOlConfig;

/**
 * @author lihui
 *
 *         2015年6月19日 下午8:42:47
 */
@Transactional
@Service("whTransOlConfigManager")
public class WhTransOlConfigManagerImpl extends BaseManagerImpl implements WhTransOlConfigManager {

    /**
     * 
     */
    private static final long serialVersionUID = 2559204216237561024L;

    @Autowired
    private WhTransOlConfigDao whTransOlConfigDao;
    @Autowired
    private TransportatorDao transportatorDao;

    /**
     * 获取电子运单接口帐号配置信息
     */
    public WhTransOlConfig findTransOlConfig(String lpCode, String departure) {
        if (lpCode != null) {
            // 判断此物流商是否需要分区
            Transportator transportator = transportatorDao.findByCode(lpCode);
            if (transportator != null) {
                Boolean b = transportator.getIsRegion();
                if (b == null || b == false) {
                    departure = null;
                }
                // 获取配置信息
                List<WhTransOlConfig> tocList = whTransOlConfigDao.findTransOlConfig(lpCode, departure, new BeanPropertyRowMapper<WhTransOlConfig>(WhTransOlConfig.class));

                if (tocList != null && !tocList.isEmpty()) {
                    return tocList.get(0);
                }
            }
        }

        return null;
    }

}
