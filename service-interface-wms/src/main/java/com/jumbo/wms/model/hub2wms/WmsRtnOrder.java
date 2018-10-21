package com.jumbo.wms.model.hub2wms;

import com.jumbo.wms.model.BaseModel;

/**
 * 退换货订单
 * 
 * @author cheng.su
 * 
 */
public class WmsRtnOrder extends BaseModel {
    /**
	 * 
	 */
    private static final long serialVersionUID = -3238845248706906566L;
    /**
     * 订单号(唯一对接标识)
     */
    private String orderCode;

    /**
     * 仓库编码
     */
    private String refWarehouseCode;
    /**
     * 退货入口单据
     */
    private WmsRtnInOrder rtnInOrder;
    /**
     * 换货出库单据(见1销售接口接口参数)
     */
    private WmsSalesOrder rntOutOrder;


    public String getRefWarehouseCode() {
        return refWarehouseCode;
    }

    public void setRefWarehouseCode(String refWarehouseCode) {
        this.refWarehouseCode = refWarehouseCode;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public WmsRtnInOrder getRtnInOrder() {
        return rtnInOrder;
    }

    public void setRtnInOrder(WmsRtnInOrder rtnInOrder) {
        this.rtnInOrder = rtnInOrder;
    }

    public WmsSalesOrder getRntOutOrder() {
        return rntOutOrder;
    }

    public void setRntOutOrder(WmsSalesOrder rntOutOrder) {
        this.rntOutOrder = rntOutOrder;
    }



}
