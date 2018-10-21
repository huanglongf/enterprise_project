package com.jumbo.wms.manager.expressRadar;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.dao.expressRadar.TransRouteDao;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.model.expressRadar.TransRoute;

@Transactional
@Service("transRouteManager")
public class TransRouteManagerImpl extends BaseManagerImpl implements TransRouteManager {

    /**
	 * 
	 */
    private static final long serialVersionUID = -175113619481864263L;
    Logger log = LoggerFactory.getLogger(TransRouteManagerImpl.class);


    @Autowired
    private TransRouteDao transRouteDao;

    /**
     * 批量保存快递明细
     * 
     * @param erdList
     */
    public void saveTransRouteList(List<TransRoute> erdList) {
        if (erdList != null && !erdList.isEmpty()) {
            for (TransRoute expressRadarDetail : erdList) {
                transRouteDao.save(expressRadarDetail);
            }
        }
    }

}
