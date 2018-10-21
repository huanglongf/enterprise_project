package com.jumbo.rmi.warehouse;

import java.io.Serializable;

/**
 * WmsResponse 反馈消息
 */
public class WmsResponse implements Serializable {


    private static final long serialVersionUID = 4661231400418974844L;

    /**
     * 成功
     */
    public static final int STATUS_SUCCESS = 1;

    /**
     * 失败
     */
    public static final int STATUS_ERROR = 0;

    public static final int SYS_EXCEPTION = 1001;// 系统异常

    public static final String PARAMETER_INCOMPLETE = "1002";// 参数不完整

    public static final int DATA_TRANSMISSION = 1003;// 单据重复/已存在

    public static final String CUSTOMER_NOT_FOUND = "1004";// 无对应客户信息

    public static final String SHOP_NOT_FOUND = "1005";// 无对应店铺信息

    public static final String CUSTOMER_SHOP_NOT_BINDING = "1006";// 客户店铺无对应绑定关系

    public static final String WAREHOUSE_NOT_FOUND = "1007";// 无队形仓库信息

    public static final String SKU_NOT_EXIST = "1008";// upc商品不存在

    public static final String CUSTOMER_SKU_EXIST = "1009";// 同客户条码商品已存在

    public static final String KEY_SERVICE_GETCODE_ERROE = "1010";// 主键服务获取编码错误

    public static final String UPPER_SYSTEM_CODE_NOT_FOUND = "3001";// 上位系统单据号找不到wms对应单据

    public static final String UPPER_SYSTEM_CODE_EXIST = "3002";// 上位系统单号已存在

    public static final String PARAMETER_ANOMALY = "3003";// 传入参数异常

    public static final String OUTBOUND_CODE_LOCK_OR_UNLOCK_FILED = "3004";// 出库单锁定/解锁失败

    public static final String CANCEL_THE_FAILURE = "3005";// 取消失败

    public static final String PARAMETER_QUERY_EXCEPTION = "3006";// 参数查询结果异常

    public static final String DOCUMENTS_ARE_NOT_ALLOWED_TO_BE_CANCELLED = "3007";// 单据不允许取消

    public static final String DOCUMENT_TYPE_DOES_NOT_EXIST = "3008";// 单据类型不存在
    
    public static final String STATUS_NOT_FOUND="3009";//库存状态不存在
    
    public static final int OCCUPATION_FAILURE = 3010;// 库存占用失败
    


    /**
     * 数据唯一标识
     */
    private String uuid;

    /**
     * 状态 1：成功 0：失败
     */
    private int status;

    /**
     * 异常编码
     */
    private String errorCode;

    /**
     * 订单号
     */
    private String orderCode;

    /**
     * 备注
     */
    private String msg;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

}
