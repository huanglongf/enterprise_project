package com.jumbo.wms.manager.vmi.warehouse;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SingleColumnRowMapper;

import com.jumbo.dao.vmi.warehouse.MsgOutboundOrderCancelDao;
import com.jumbo.dao.warehouse.MsgRaCancelDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.dao.warehouse.WarehouseMsgSkuDao;
import com.jumbo.wms.model.vmi.warehouse.MsgInboundOrder;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrderCancel;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrderCancelStatus;
import com.jumbo.wms.model.vmi.warehouse.MsgRaCancel;
import com.jumbo.wms.model.warehouse.InventoryStatus;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.WarehouseLocation;

public class VmiWarehouseYH extends AbstractVmiWarehouse {

    /**
     * 
     */
    private static final long serialVersionUID = 4867067643681163049L;

    @Autowired
    private MsgOutboundOrderCancelDao msgOutboundOrderCancelDao;
    @Autowired
    private MsgRaCancelDao msgRaCancelDao;
    @Autowired
    private WarehouseMsgSkuDao warehouseMsgSkuDao;
    @Autowired
    private StockTransApplicationDao stockTransApplicationDao;

    public void inboundNotice(MsgInboundOrder msgInorder) {}

    public void inboundReturnRequestAnsToWms(MsgInboundOrder msg) {

    }

    public boolean cancelSalesOutboundSta(String staCode, MsgOutboundOrderCancel msg) {
        // msgOutboundOrderDao.flush();
        // boolean isCancle = false;
        // MsgOutboundOrder order = msgOutboundOrderDao.findOutBoundByStaCode(staCode);
        // // 如果未给外包仓直接标记为取消,取消单据
        // if (order != null && order.getStatus().equals(DefaultStatus.CREATED)) {
        // msgOutboundOrderDao.updateStatusByStaCode(DefaultStatus.FINISHED.getValue(), staCode);
        // isCancle = true;
        // msg.setStatus(MsgOutboundOrderCancelStatus.FINISHED);
        // msg.setUpdateTime(new Date());
        // msg.setMsg("WMS 端取消");
        // msgOutboundOrderCancelDao.save(msg);
        // }
        return false;

    }

    @Override
    public WarehouseLocation findLocByInvStatus(InventoryStatus invStatus) {
        return null;
    }

    /**
     * YH外包仓退货入取消逻辑 1.公共外包仓取消接口是定时的，所以不能实时取消 2.插入销售取消表。3.删除退货取消表数据
     */
    @Override
    public boolean cancelReturnRequest(Long msgLog) {
        // return true;
        // TODO 因目前OMS流程不支持，所以推迟上线
        try {
            MsgRaCancel raCancel = msgRaCancelDao.getByPrimaryKey(msgLog);
            StockTransApplication sta = stockTransApplicationDao.getByCode(raCancel.getStaCode());
            // 插入销售取消表
            MsgOutboundOrderCancel orderCancel = new MsgOutboundOrderCancel();
            orderCancel.setStaCode(raCancel.getStaCode());
            orderCancel.setStaId(sta.getId());
            orderCancel.setStatus(MsgOutboundOrderCancelStatus.CREATED);
            orderCancel.setSystemKey(sta.getSystemKey());
            orderCancel.setVersion(0);
            orderCancel.setIsKey(0);
            orderCancel.setSource(raCancel.getSource());
            orderCancel.setCreateTime(new Date());
            orderCancel.setMsg("退货取消");
            Long id = warehouseMsgSkuDao.getThreePlSeq(new SingleColumnRowMapper<Long>(Long.class));
            orderCancel.setUuid(id);
            msgOutboundOrderCancelDao.save(orderCancel);
            // 删除退货取消表数据
            msgRaCancelDao.deleteByPrimaryKey(msgLog);
        } catch (Exception e) {
            log.error("...............returnCancel error................");
            return false;
        }
        return false;
    }
}
