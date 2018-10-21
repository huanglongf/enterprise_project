package com.jumbo.wms.model.automaticEquipment;

import java.io.Serializable;
import java.util.List;

/**
 * WCS消息请求--播种实体
 * 
 * @author xiaolong.fei
 * 
 */
public class MsgToWcsBoZRequest implements Serializable {

    private static final long serialVersionUID = -4653771412715358796L;
    private String waveOrder; // 波次号
    private List<WcsOrder> order; // 订单

    public String getWaveOrder() {
        return waveOrder;
    }

    public void setWaveOrder(String waveOrder) {
        this.waveOrder = waveOrder;
    }

    public List<WcsOrder> getOrder() {
        return order;
    }

    public void setOrder(List<WcsOrder> order) {
        this.order = order;
    }



    /**
     * 收货入库货箱流向
     * 
     * @author xiaolong.fei
     * 
     */
    public static class WcsOrder implements Serializable {
        private static final long serialVersionUID = 2600857514178771657L;
        private String orderID; // 订单号
        private String index; // 序号
        private List<WcsSku> sku; // 商品行

        public String getOrderID() {
            return orderID;
        }

        public void setOrderID(String orderID) {
            this.orderID = orderID;
        }

        public List<WcsSku> getSku() {
            return sku;
        }

        public void setSku(List<WcsSku> sku) {
            this.sku = sku;
        }


        public String getIndex() {
            return index;
        }

        public void setIndex(String index) {
            this.index = index;
        }



        /**
         * 商品行对象
         * 
         * @author xiaolong.fei
         * 
         */
        public static class WcsSku implements Serializable {
            private static final long serialVersionUID = 4603904017139808634L;
            private Long OrderNumber;// 订单行号
            private String skuCode;// 商品编码
            private String barcode;// 商品条码
            private String skuname;// 商品名称
            private Integer isSn;// 是否管理SN商品 1：是 0：不是
            private Integer isSl;// 是否管理效期 1：是 0：不是
            private Integer isBatchNo;// 是否管理批次 1：是 0：不是
            private Integer isOg; // 是否管理产地 1：是 0：不是
            private List<BarCodes> addBarcodes; // 多条码对象
            private Integer count; // 数量
            private Integer priority; // 优先级

            public Long getOrderNumber() {
                return OrderNumber;
            }

            public void setOrderNumber(Long orderNumber) {
                OrderNumber = orderNumber;
            }

            public String getSkuCode() {
                return skuCode;
            }

            public void setSkuCode(String skuCode) {
                this.skuCode = skuCode;
            }

            public String getBarcode() {
                return barcode;
            }

            public void setBarcode(String barcode) {
                this.barcode = barcode;
            }

            public String getSkuname() {
                return skuname;
            }

            public void setSkuname(String skuname) {
                this.skuname = skuname;
            }

            public Integer getIsSn() {
                return isSn;
            }

            public void setIsSn(Integer isSn) {
                this.isSn = isSn;
            }

            public Integer getIsSl() {
                return isSl;
            }

            public void setIsSl(Integer isSl) {
                this.isSl = isSl;
            }

            public Integer getIsBatchNo() {
                return isBatchNo;
            }

            public void setIsBatchNo(Integer isBatchNo) {
                this.isBatchNo = isBatchNo;
            }

            public Integer getIsOg() {
                return isOg;
            }

            public void setIsOg(Integer isOg) {
                this.isOg = isOg;
            }

            public List<BarCodes> getAddBarcodes() {
                return addBarcodes;
            }

            public void setAddBarcodes(List<BarCodes> addBarcodes) {
                this.addBarcodes = addBarcodes;
            }

            public Integer getCount() {
                return count;
            }

            public void setCount(Integer count) {
                this.count = count;
            }

            public Integer getPriority() {
                return priority;
            }

            public void setPriority(Integer priority) {
                this.priority = priority;
            }

            /**
             * 多条码对象
             * 
             * @author xiaolong.fei
             * 
             */
            public static class BarCodes implements Serializable {
                private static final long serialVersionUID = -2781394166157400798L;
                private String barcode; // 条形码

                public String getBarcode() {
                    return barcode;
                }

                public void setBarcode(String barcode) {
                    this.barcode = barcode;
                }
            }
        }
    }


}
