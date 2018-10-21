package com.jumbo.wms.newInventoryOccupy;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.jumbo.task.ThreadPoolService;
import com.jumbo.webservice.ids.manager.IdsManagerProxy;
import com.jumbo.wms.manager.pickZone.NewInventoryOccupyManager;
import com.jumbo.wms.manager.warehouse.AreaOcpStaManager;
import com.jumbo.wms.manager.warehouse.WareHouseManagerProxy;
import com.jumbo.wms.model.MongoDBInventoryOcp;
import com.jumbo.wms.model.pickZone.OcpInvConstants;
import com.jumbo.wms.task.WmsTaskInterface;


/**
 * 作业单区域占用库存All
 * 
 * @author lzb
 *
 */
public class AreaOcpStaAllThread extends Thread {
    protected static final Logger log = LoggerFactory.getLogger(AreaOcpStaAllThread.class);
  
    private Long ouId;
    @Autowired
    private WmsTaskInterface wmsTaskInterface;


    public AreaOcpStaAllThread(Long ouId) {
        WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
        this.ouId = ouId;
        wmsTaskInterface = (WmsTaskInterface) context.getBean("wmsTaskInterface");
    }


    public void run() {
        try {
            wmsTaskInterface.queryStaToOcpAeraInv(ouId.toString());
        } catch (Exception e) {
          log.error("queryStaToOcpAeraInvAll_dameon:"+ouId);
        }
    }


    public Long getOuId() {
        return ouId;
    }


    public void setOuId(Long ouId) {
        this.ouId = ouId;
    }

}
