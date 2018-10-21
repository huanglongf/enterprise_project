package com.jumbo.wms.manager.util;

import com.baozun.scm.baseservice.message.common.MessageCommond;


public class MqStaticEntity {

    // 生产者发送队列
    public static final String WMS3_SALE_OUTBOUNT = "wms3_sale_outbount";// 出库 ok
    public static final String WMS3_SALE_OUTBOUNT_CALLBACK = "wms3_sale_outbount_CallBack";// 出库 回传
    public static final String WMS3_OMS = "oms"; // ok
    public static final String WMS3_PAC = "pac";

    public static final String WMS3_IM_INV_SKU_FLOW = "wms32im_im_inv_sku_flow";// 推送IM库存流水
    // IM库存占用和释放
    // public static final String WMS3_IM_OCCUPIED_RELEASE = "wms32im_im_occupied_release";
    // public static final String WMS3_IM_OCCUPIED_TAGS = "occupied";// IM库存占用(标签)
    // public static final String WMS3_IM_RELEASE_TAGS = "release";// IM库存释放(标签)

    public static final String WMS3_IM_OCCUPIED = "wms32im_im_occupied";// 发送IM内部库存占用
    public static final String WMS3_IM_RELEASE = "wms32im_im_release"; // 发送IM内部库存取消释放

    // 销售出、退换货出 反馈OMS 非直连
    public static final String WMS3_SALE_OUTBOUNT_FEEDBACK = "wms32pac_sale_outbount_feedback";
    public static final String WMS3_INCREMENTAL_INVENTORY = "wms32pac_incremental_inventory";// 增量库存同步

    public static final String WMS3_RTN_ORDER_SERVICE = "wms3_rtn_order_service";// 退换货入库反馈 直连
    public static final String WMS3_RTN_ORDER_SERVICE_PAC = "wms3_rtn_order_service_pac";// 非退换货入库反馈
    public static final String WMS3_SALES_ORDER_SERVICE = "wms3_sales_order_service";// 订单接受 直连
    public static final String WMS3_SALES_ORDER_CREATE = "wms3_sales_order_create";// 订单接受 非直连
    public static final String WMS3_SALES_ORDER_SERVICE_RETURN = "wms3_sales_order_service_return";// 直连创单反馈
    public static final String WMS3_MQ_RTN_OUTBOUNT_YH = "wms3_mq_rtn_outbount_yh";// 出库反馈YHWLB主题 OK
    public static final String WMS3_MQ_RTN_OUTBOUNT_LF = "wms3_mq_rtn_outbount_lf";// 出库反馈LF 主题 OK
    public static final String WMS3_MQ_RTN_ORDER_CREATE = "wms32pac_mq_rtn_order_create";// 非直连创单反馈pac


    public static final String WMS3_MQ_CREATE_STA_PAC = "wms3_create_sta_pac"; // 非直连创单

    public static final String WMS3_MQ_CREATE_STA_OMS = "wms3_create_sta_oms"; // 非直连创单

    public static final String WMS3_MQ_OUTBOUND_BY_PICKINGLIST = "wms3_outbound_pickinglist";

    public static final String WMS3_MQ_CREATE_PICKINGLIST = "wms3_create_pickinglist"; // 创建配货批

    public static final String WMS3_MQ_TEST_ORDER = "wms3_test_order";//

    public static final String WMS3_MQ_CREATE_SING_ORDER = "wms3_create_singOrder_pickinglist"; // 创建配货批

    public static MessageCommond initMessageCommond() {
        return null;
    }

}
