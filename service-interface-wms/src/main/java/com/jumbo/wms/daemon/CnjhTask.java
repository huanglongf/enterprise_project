package com.jumbo.wms.daemon;

/**
 * CJ项目：菜鸟集货物流方式
 * 
 */
public interface CnjhTask {

    /**
     * 获取lgorderCode和快递面单url定时任务
     */
    public void goToGetLgorderCodeAndUrl();

    /**
     * step1通过一般进口发货获取物流订单编号lgorder_code
     * 
     * @param systemKey hub请求key
     * @param staId 作业单id
     * @param tradeOrderId 交易订单id
     * @param resourceId 物流资源ID
     * @param storeCode 仓库编码
     * @param firstLogistics 第1段物流承运商
     * @param firstWaybillNo 第1段物流运单号
     * @param i 请求次数
     * @return 物流订单编号lgorder_code
     */
    public String getLgorderCode(String systemKey, Long staId, Long tradeOrderId, Long resourceId, String storeCode, String firstLogistics, String firstWaybillNo, Integer i);

    /**
     * step2通过获取运单信息接口和lgorder_code查询面单url
     * 
     * @param systemKey hub请求key
     * @param staId 作业单id
     * @param lgorder_code 物流订单号
     * @param i 请求次数
     * @return 面单url
     */
    public String getExpressWayBillUrl(String systemKey, Long staId, String lgorder_code, Integer i);
}
