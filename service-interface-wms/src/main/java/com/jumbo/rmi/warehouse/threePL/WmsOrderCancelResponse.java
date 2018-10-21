package com.jumbo.rmi.warehouse.threePL;

import java.io.Serializable;

/**
 * 外包仓取消反馈
 * 
 * @author xiaolong.fei
 * 
 */
public class WmsOrderCancelResponse implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 775801364228091956L;
    /**
     * 数据唯一标识
     */
    private String uuid;
    /**
     * 单据编码
     */
    private String orderCode;
    /**
     * 单据类型 1：入库 10：转出 41：退货入库 21：销售出库
     */
    private String type;
    /**
     * 取消结果 1：取消成功(所有已经取消订单也以此状态反馈) 0：取消失败( 如单据未找到以此状态反馈)
     */
    private String status;
    /**
     * 扩展字段json格式
     */
    private String extMemo;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getExtMemo() {
        return extMemo;
    }

    public void setExtMemo(String extMemo) {
        this.extMemo = extMemo;
    }

}
