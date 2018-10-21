package com.jumbo.wms.manager.hub4;

import java.util.List;
import java.util.Map;

import com.jumbo.rmi.warehouse.WmsResponse;
import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.lf.StaLfCommand;
import com.jumbo.wms.model.warehouse.StaDeliveryInfo;
import com.jumbo.wms.model.warehouse.StaInvoice;
import com.jumbo.wms.model.warehouse.StaInvoiceLine;
import com.jumbo.wms.model.warehouse.StaLine;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.wmsInterface.WmsOutBound;
import com.jumbo.wms.model.wmsInterface.WmsOutBoundVasLine;

public interface Wms2Hub4AllocationService extends BaseManager {

    public void createAllocationSta(StockTransApplication sta, List<StaLine> staLineList, StaDeliveryInfo sd, Map<StaInvoice, List<StaInvoiceLine>> map, WmsResponse re, StaLfCommand sc, Map<String, List<WmsOutBoundVasLine>> vasMap);

    /**
     * NIKEcrw单据
     * 
     * @param ob
     * @throws Exception
     */
    public String crwOrder(WmsOutBound ob);

    public void createInboundSta(Map<StockTransApplication, Map<String, StaLine>> map);

    public String crwOrderCreate(List<WmsOutBound> obList);

}
