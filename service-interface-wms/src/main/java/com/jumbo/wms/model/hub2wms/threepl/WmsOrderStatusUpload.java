package com.jumbo.wms.model.hub2wms.threepl;

import java.io.Serializable;

public class WmsOrderStatusUpload implements Serializable {

    private static final long serialVersionUID = -1089829535927394818L;
    /**
     * 仓库订单类型：201 交易出库单 501 销退入库单 502 换货出库单 301 调拨出库单 302 调拨入库单 601 采购入库单 901 普通出库单 904 普通入库单 604
     * B2B干线退货入库 306 B2B入库单 305 B2B出库单 703 库存状态调整出库 704 库存状态调整入库
     */
    private String orderType;
    /**
     * 仓储中心订单编码
     */
    private String orderCode;
    /**
     * 订单状态: WMS_REJECT-拒单 WMS_RECEIVED-收货完成 WMS_PRINT-打印 WMS_PICK-拣货 WMS_PACKAGE-打包 WMS_ONSALE-上架完成
     * WMS_LACK-缺货 WMS_ERROR WMS_CLEARANCE-清关 WMS_CHECK-复核 WMS_ARRIVE-货到仓库 WMS_ARRIVALREGISTER-到货登记
     * WMS_ACCEPT-接单 WMS_ORDER_CANCELED-订单已取消 WMS_ TALLING -理货中 WMS_ TALLIED -理货完成 注意：
     * 保税行业必须回传打包，否则不允许出库 入库回传: WMS_ACCEPT-接单 WMS_REJECT-拒单(一般入库单不会拒单) WMS_ARRIVE-货到仓库 WMS_ TALLING
     * -理货中 WMS_ TALLIED -理货完成 出库回传: WMS_ACCEPT-接单 WMS_REJECT-拒单 发货回传: WMS_ACCEPT-接单 WMS_REJECT-拒单
     * WMS_PICK-拣货 WMS_CHECK-复核 WMS_PACKAGE-打包 WMS_PRINT-打印
     */
    private String status;
    /**
     * 操作人
     */
    private String operator;
    /**
     * 操作人联系方式
     */
    private String operatorContact;
    /**
     * 操作时间：格式为YYYY-MM-DD hh:mm:ss
     */
    private String operateDate;
    /**
     * 操作内容：物流详情显示
     */
    private String content;
    /**
     * 备注
     */
    private String remark;
    /**
     * 扩展属性 保税回传接单，要回传重量和包材,wms回传的订单的估重(用于海关报关), key:WEIGHT,单位g,例如WEIGHT:50；
     * wms回传的包材,key:PACKAGE_MATERIAL,例如:PACKAGE_MATERIAL:1。 保税回传打包，要回传重量,wms回传的订单包裹的重量(用于结算)
     * ,key:WEIGHT,单位g,例如WEIGHT:50；
     */
    private String features;

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getOperatorContact() {
        return operatorContact;
    }

    public void setOperatorContact(String operatorContact) {
        this.operatorContact = operatorContact;
    }

    public String getOperateDate() {
        return operateDate;
    }

    public void setOperateDate(String operateDate) {
        this.operateDate = operateDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

}
