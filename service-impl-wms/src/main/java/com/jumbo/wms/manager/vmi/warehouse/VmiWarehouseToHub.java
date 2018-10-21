package com.jumbo.wms.manager.vmi.warehouse;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SingleColumnRowMapper;

import com.baozun.bizhub.manager.warehouse.WarehouseRealtimeManager;
import com.baozun.bizhub.model.warehouse.WmsOrderCancel;
import com.baozun.bizhub.model.warehouse.WmsOrderCancelResponse;
import com.jumbo.dao.baseinfo.WarehouseDao;
import com.jumbo.dao.vmi.warehouse.MsgOutboundOrderCancelDao;
import com.jumbo.dao.warehouse.MsgRaCancelDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.dao.warehouse.WarehouseMsgSkuDao;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.warehouse.StaCancelManagerImpl;
import com.jumbo.wms.model.DefaultStatus;
import com.jumbo.wms.model.baseinfo.Warehouse;
import com.jumbo.wms.model.vmi.warehouse.MsgInboundOrder;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrderCancel;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrderCancelStatus;
import com.jumbo.wms.model.vmi.warehouse.MsgRaCancel;
import com.jumbo.wms.model.warehouse.InventoryStatus;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.WarehouseLocation;

public class VmiWarehouseToHub extends AbstractVmiWarehouse {

    /**
     * 
     */
    private static final long serialVersionUID = 4867067643681163049L;
    
    protected static final Logger logger = LoggerFactory.getLogger(VmiWarehouseToHub.class);

    @Autowired
    private MsgOutboundOrderCancelDao msgOutboundOrderCancelDao;
    @Autowired
    private MsgRaCancelDao msgRaCancelDao;
    @Autowired
    private WarehouseMsgSkuDao warehouseMsgSkuDao;
    @Autowired
    private StockTransApplicationDao stockTransApplicationDao;
    @Autowired
    private WarehouseDao warehouseDao;
    
    @Autowired
    private WarehouseRealtimeManager warehouseRealtimeManager;

    public void inboundNotice(MsgInboundOrder msgInorder) {}

    public void inboundReturnRequestAnsToWms(MsgInboundOrder msg) {

    }

    public boolean cancelSalesOutboundSta(String staCode, MsgOutboundOrderCancel msg) {
        StockTransApplication sta=stockTransApplicationDao.getByCode(staCode);
        Warehouse warehouse=warehouseDao.getByOuId(sta.getMainWarehouse().getId());
        WmsOrderCancel wmsOrderCancel=new WmsOrderCancel();
        wmsOrderCancel.setUuid(msg.getUuid().toString());
        wmsOrderCancel.setType(String.valueOf(sta.getType().getValue()));
        wmsOrderCancel.setOrderCode(staCode);
        WmsOrderCancelResponse wmsOrderCancelResponse=warehouseRealtimeManager.orderCancel(warehouse.getVmiSource(), wmsOrderCancel);
        if(null!=wmsOrderCancelResponse&& null!=wmsOrderCancelResponse.getStatus()&&!"".equals(wmsOrderCancelResponse.getStatus())){
            logger.error("nikeToHub staCode {} status{}", staCode,wmsOrderCancelResponse.getStatus());
        }else{
            logger.error("nikeToHub staCode {}", staCode);
        }
        if(null!=wmsOrderCancelResponse&& null!=wmsOrderCancelResponse.getStatus()&&!"".equals(wmsOrderCancelResponse.getStatus())
                &&"1".equals(wmsOrderCancelResponse.getStatus())){
            msg.setStatus(MsgOutboundOrderCancelStatus.FINISHED);
            msg.setIsCanceled(true);
            msg.setUpdateTime(new Date());
            msg.setMsg("WMS推送LF实时取消");
            msgOutboundOrderCancelDao.save(msg);
            return true;
        }else {
        	 msg.setStatus(MsgOutboundOrderCancelStatus.FINISHED);
             msg.setIsCanceled(false);
             msg.setUpdateTime(new Date());
             msg.setMsg(wmsOrderCancelResponse.getExtMemo());
             msgOutboundOrderCancelDao.save(msg);
             return false;
        }

    }

    @Override
    public WarehouseLocation findLocByInvStatus(InventoryStatus invStatus) {
        return null;
    }

    /**
     * 外包仓退货入库取消
     */
	@Override
	public boolean cancelReturnRequest(Long msgLog) {
		MsgRaCancel raCancel = msgRaCancelDao.getByPrimaryKey(msgLog);
		StockTransApplication sta = stockTransApplicationDao.getByCode(raCancel.getStaCode());
		Warehouse warehouse = warehouseDao.getByOuId(sta.getMainWarehouse().getId());
		Long id = warehouseMsgSkuDao.getThreePlSeq(new SingleColumnRowMapper<Long>(Long.class));
		WmsOrderCancel wmsOrderCancel = new WmsOrderCancel();
		wmsOrderCancel.setUuid(id.toString());
		wmsOrderCancel.setType(String.valueOf(sta.getType().getValue()));
		wmsOrderCancel.setOrderCode(sta.getCode());
		WmsOrderCancelResponse wmsOrderCancelResponse = warehouseRealtimeManager.orderCancel(warehouse.getVmiSource(),
				wmsOrderCancel);
		if (null != wmsOrderCancelResponse && null != wmsOrderCancelResponse.getStatus() && "1".equals(wmsOrderCancelResponse.getStatus())) {
			raCancel.setVersion(1);
			raCancel.setUpdateTime(new Date());
			raCancel.setStatus(DefaultStatus.FINISHED);
			msgRaCancelDao.save(raCancel);
			return true;
		}
		return false;
	}
}
