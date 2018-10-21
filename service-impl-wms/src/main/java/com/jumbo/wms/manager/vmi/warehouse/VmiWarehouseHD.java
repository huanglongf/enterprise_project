package com.jumbo.wms.manager.vmi.warehouse;

import com.jumbo.wms.model.vmi.warehouse.MsgInboundOrder;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrderCancel;
import com.jumbo.wms.model.warehouse.InventoryStatus;
import com.jumbo.wms.model.warehouse.WarehouseLocation;

public class VmiWarehouseHD extends AbstractVmiWarehouse {



    /**
     * 
     */
    private static final long serialVersionUID = -6293813728193939860L;

    public void inboundNotice(MsgInboundOrder msgInorder) {}

    public void inboundReturnRequestAnsToWms(MsgInboundOrder msg) {

    }

    public boolean cancelSalesOutboundSta(String staCode, MsgOutboundOrderCancel msg) {
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
        return true; // NIKE 不接退货取消，直接 取消成功
        // try {
        // MsgRaCancel raCancel = msgRaCancelDao.getByPrimaryKey(msgLog);
        // StockTransApplication sta = stockTransApplicationDao.getByCode(raCancel.getStaCode());
        // // 插入销售取消表
        // MsgOutboundOrderCancel orderCancel = new MsgOutboundOrderCancel();
        // orderCancel.setStaCode(raCancel.getStaCode());
        // orderCancel.setStaId(sta.getId());
        // orderCancel.setStatus(MsgOutboundOrderCancelStatus.CREATED);
        // orderCancel.setSystemKey(sta.getSystemKey());
        // orderCancel.setVersion(0);
        // orderCancel.setIsKey(0);
        // orderCancel.setSource(raCancel.getSource());
        // orderCancel.setCreateTime(new Date());
        // orderCancel.setMsg("退货取消");
        // Long id = warehouseMsgSkuDao.getThreePlSeq(new SingleColumnRowMapper<Long>(Long.class));
        // orderCancel.setUuid(id);
        // msgOutboundOrderCancelDao.save(orderCancel);
        // // 删除退货取消表数据
        // msgRaCancelDao.deleteByPrimaryKey(msgLog);
        // } catch (Exception e) {
        // log.error("...............returnCancel error................");
        // return false;
        // }
        // return false;
    }
}
