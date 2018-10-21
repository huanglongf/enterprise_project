package com.jumbo.mq;

public class MqConstants {

    /**
     * 退货申请
     */
    public static int RETURN_APPLICATION_TYPE_RETURN = 1;

    /**
     * 换货申请
     */
    public static int RETURN_APPLICATION_TYPE_EXCHANGE = 2;

    /**
     * 7天无理由
     */
    public static String RETURN_APPLICATION_REASON_7DAYS = "R001A";

    /**
     * 商品质量问题
     */
    public static String RETURN_APPLICATION_REASON_QUALITY_PROBLEMS = "R002A";

    /**
     * 店铺支付宝
     */
    public static String REFUND_WAY_ALIPAY = "1";

    /**
     * 网银转账
     */
    public static String REFUND_WAY_BANK = "3";

    /**
     * 订单创建成功
     */
    public static Integer MQ_SO_RESULT_OPTYPE_SO_CREATE = new Integer(1);

    /**
     * 订单取消
     */
    public static Integer MQ_SO_RESULT_OPTYPE_SO_CANCEL = new Integer(2);

    /**
     * 过单到仓库
     */
    public static Integer MQ_SO_RESULT_OPTYPE_SO_CONFIRM = new Integer(3);

    /**
     * 销售出库
     */
    public static Integer MQ_SO_RESULT_OPTYPE_SO_DELIVERY = new Integer(5);

    /**
     * 退换货申请创建
     */
    public static Integer MQ_SO_RESULT_OPTYPE_RA_CREATE = new Integer(6);

    /**
     * 退货已入库
     */
    public static Integer MQ_SO_RESULT_OPTYPE_RETURN_INBOUND = new Integer(7);

    /**
     * 换货已入库
     */
    public static Integer MQ_SO_RESULT_OPTYPE_EXCHANGE_INBOUND = new Integer(8);

    /**
     * 换货已出库
     */
    public static Integer MQ_SO_RESULT_OPTYPE_EXCHANGE_OUTBOUND = new Integer(9);

    /**
     * 交易完成(订单)
     */
    public static Integer MQ_SO_RESULT_OPTYPE_SO_FINISHED = new Integer(10);

    /**
     * COD全额收款
     */
    public static Integer MQ_SO_RESULT_OPTYPE_SO_FULL_PAYMENT = new Integer(11);

    /**
     * 转物流
     */
    public static Integer MQ_SO_RESULT_OPTYPE_SO_CHANGE_TRANS = new Integer(12);

    /**
     * 退货申请创建成功
     */
    public static Integer MQ_SO_RESULT_OPTYPE_RETUAN_CREATE_SUCCESS = new Integer(17);

    /**
     * 退货申请创建失败
     */
    public static Integer MQ_SO_RESULT_OPTYPE_RETURN_CREATE_FAILED = new Integer(18);

    /**
     * 换货申请创建成功
     */
    public static Integer MQ_SO_RESULT_OPTYPE_EXCHANGE_CREATE_SUCCESS = new Integer(19);

    /**
     * 换货申请创建失败
     */
    public static Integer MQ_SO_RESULT_OPTYPE_EXCHANGE_CREATE_FAILED = new Integer(20);
    
    /**
     * 退换货申请取消
     */
    public static Integer MQ_SO_RESULT_OPTYPE_RA_CANCEL = new Integer(21);
    
    //mq 消息类型
    /**
     * 订单消息
     */
    public static String MQ_MSG_TYPE_SO = "SO";
    /**
     * 订单处理状态消息
     */
    public static String MQ_MSG_TYPE_SO_RESULT = "SORESULT";
    /**
     * SKU消息
     */
    public static String MQ_MSG_TYPE_SKU = "SKU";
    /**
     * 商品价格消息
     */
    public static String MQ_MSG_TYPE_SKU_PRICE = "SKUPRICE";
    /**
     * 库存消息
     */
    public static String MQ_MSG_TYPE_INVENTORY = "INV";
    
    //反馈状态
    public static String FLAG_ERROR = "error";
    public static String FLAG_SUCCESS = "success";
    
    
}
