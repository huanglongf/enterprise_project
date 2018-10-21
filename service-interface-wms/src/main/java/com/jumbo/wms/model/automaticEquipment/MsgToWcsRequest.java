package com.jumbo.wms.model.automaticEquipment;

import java.io.Serializable;

/**
 * WCS消息请求实体
 * 
 * @author xiaolong.fei
 * 
 */
public class MsgToWcsRequest implements Serializable {

    private static final long serialVersionUID = -4653771412715358796L;
    /**
     * 收货入库货箱流向
     */
    private SShouRongQi sShouRongQi;


    public SShouRongQi getsShouRongQi() {
        return sShouRongQi;
    }

    public void setsShouRongQi(SShouRongQi sShouRongQi) {
        this.sShouRongQi = sShouRongQi;
    }



    /**
     * 收货入库货箱流向
     * 
     * @author xiaolong.fei
     * 
     */
    public static class SShouRongQi implements Serializable {
        private static final long serialVersionUID = 2600857514178771657L;
        private String containerNO; // 容器号 Y
        private String destinationNO;// 目的地位置Y
        private String serialNumber;// 序号 N

        public String getContainerNO() {
            return containerNO;
        }

        public void setContainerNO(String containerNO) {
            this.containerNO = containerNO;
        }

        public String getDestinationNO() {
            return destinationNO;
        }

        public void setDestinationNO(String destinationNO) {
            this.destinationNO = destinationNO;
        }

        public String getSerialNumber() {
            return serialNumber;
        }

        public void setSerialNumber(String serialNumber) {
            this.serialNumber = serialNumber;
        }

    }

    /**
     * 快递集货
     * 
     * @author xiaolong.fei
     * 
     */
    public static class SjiHuo implements Serializable {
        private static final long serialVersionUID = 3723737389470540862L;
        private String waybillNumber; // 运单号（出库箱号） Y
        private String shippingArea;// 集货口 Y

        public String getWaybillNumber() {
            return waybillNumber;
        }

        public void setWaybillNumber(String waybillNumber) {
            this.waybillNumber = waybillNumber;
        }

        public String getShippingArea() {
            return shippingArea;
        }

        public void setShippingArea(String shippingArea) {
            this.shippingArea = shippingArea;
        }

    }

    /**
     * 播种取消
     * 
     * @author jumbo
     * 
     */
    public static class OQuxiaoBoZhong implements Serializable {
        private static final long serialVersionUID = 2600857514178771657L;
        private String waveOrder; // 波次号
        private String orderID; // 订单号
        private String operation; // 操作

        public String getWaveOrder() {
            return waveOrder;
        }

        public void setWaveOrder(String waveOrder) {
            this.waveOrder = waveOrder;
        }

        public String getOrderID() {
            return orderID;
        }

        public void setOrderID(String orderID) {
            this.orderID = orderID;
        }

        public String getOperation() {
            return operation;
        }

        public void setOperation(String operation) {
            this.operation = operation;
        }

    }

    /**
     * 货箱流向取消
     * 
     * @author jumbo
     * 
     */
    public static class OQuxiaoRongQi implements Serializable {
        private static final long serialVersionUID = 8752342719705348961L;
        private String containerNO; // 箱号

        public String getContainerNO() {
            return containerNO;
        }

        public void setContainerNO(String containerNO) {
            this.containerNO = containerNO;
        }

    }
}
