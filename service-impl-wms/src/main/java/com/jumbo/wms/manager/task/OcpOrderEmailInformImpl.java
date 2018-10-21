package com.jumbo.wms.manager.task;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.wms.daemon.OcpOrderEmailInform;
import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.manager.pickZone.NewInventoryOccupyManager;

/**
 * @author lihui
 *
 * @createDate 2015年7月28日 下午6:24:55
 */
public class OcpOrderEmailInformImpl implements BaseManager, OcpOrderEmailInform {

    /**
     * 
     */
    private static final long serialVersionUID = 2554843866466952735L;

    @Autowired
    private NewInventoryOccupyManager newInventoryOccupyManager;

    @Override
    public void exceptionOcpOrderInformEmail() {
        
        newInventoryOccupyManager.exceptionOcpOrderInformEmail();
    }

}
