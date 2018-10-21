package com.jumbo.wms.model.pickZone;


public class OcpInvConstants {
    /**
     * 新建
     */
    public static final int OCP_ORDER_STATUS_CREATED = 1;

    /**
     * 处理中
     */
    public static final int OCP_ORDER_STATUS_PROCESS = 2;

    /**
     * 占用结束
     */
    public static final int OCP_ORDER_STATUS_OCPOVER = 5;

    /**
     * 完成
     */
    public static final int OCP_ORDER_STATUS_OVER = 10;

    /**
     * 异常
     */
    public static final int OCP_ORDER_STATUS_EXCEPTION = 99;

    /**
     * 异常已处理
     */
    public static final int OCP_ORDER_STATUS_EXCEPTION_RESOLVED = 100;

    /**
     * 占用批相关组
     */
    public static final String OCP_CHOOSE_GROUP_SYS_WH = "SYS_WH";

    /**
     * 初始化Mongodb区域线程
     */
    public static final String AREA_OCP_THREAD_MONGDB = "AREA_OCP_THREAD_MONGDB";

    /**
     * 初始化Mongodb 库存行线程
     */
    public static final String AREA_OCP_THREAD_MONGDB_INV = "AREA_OCP_THREAD_MONGDB_INV";

    /**
     * 占用批每批订单限制
     */
    public static final String OCP_CHOOSE_SYS_WH_OCP_BARCH_LIMIT = "OCP_BARCH_LIMIT";

    /**
     * 占用批每批订单限制
     */
    public static final String OCP_MQ_OR_THREAD = "OCP_MQ_OR_THREAD";


    /**
     * 默认占用库存默认商品线程数
     */
    public static final String OCP_CHOOSE_SYS_WH_DEFAULT_OCP_SKU_THREAD_LIMIT = "DEFAULT_OCP_SKU_THREAD_LIMIT";

    /**
     * 默认批次计算单据占用线程数
     */
    public static final String OCP_CHOOSE_SYS_WH_DEFAULT_OCP_BATCH_LIMIT = "DEFAULT_OCP_BATCH_LIMIT";

    /**
     * 默认每次处理订单的数量
     */
    public static final String OCP_CHOOSE_SYS_WH_DEFAULT_STA_BATCH_LIMIT = "DEFAULT_STA_BATCH_LIMIT";

    /**
     * 处理占用批的线程数
     */
    public static final String OCP_THREAD_COUNT = "OCP_THREAD_COUNT";

    /**
     * 区域占用库存作业单生成队列线程数
     */
    public static final String AREA_OCP_THREAD_STA_COUNT = "AREA_OCP_THREAD_STA_COUNT";

    /**
     * 区域占用库存作业单线程数
     */
    public static final String WH_OCP_THREAD_STA_COUNT = "WH_OCP_THREAD_STA_COUNT";


    /**
     * 区域占用库存订单线程数
     */
    public static final String AREA_OCP_THREAD_COUNT = "AREA_OCP_THREAD_COUNT";

    /**
     * 区域占用库存MongDB库存线程数
     */
    public static final String AREA_OCP_MONGDB_COUNT = "AREA_OCP_MONGDB_COUNT";


    /**
     * 异常信息邮件相关组
     */
    public static final String OCP_CHOOSE_GROUP_SYS_MAIL = "SYS_MAIL";

    /**
     * 占用失败异常邮件收件人
     */
    public static final String OCP_CHOOSE_SYS_MAIL_ERROR_OCP_FAILED_RECIVER = "ERROR_OCP_FAILED_RECIVER";

    /**
     * 占用失败异常邮件模版编码
     */
    public static final String OCP_CHOOSE_SYS_MAIL_ERROR_OCP_FAILED_TEMPLETE_CODE = "ERROR_OCP_FAILED_TEMPLETE_CODE";

}
