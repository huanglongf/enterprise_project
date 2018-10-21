package com.jumbo.rmiservice;

import java.util.List;

import com.jumbo.rmi.warehouse.BaseResult;
import com.jumbo.rmi.warehouse.Invoice;
import com.jumbo.rmi.warehouse.InvoiceOrder;
import com.jumbo.rmi.warehouse.InvoiceOrderResult;
import com.jumbo.rmi.warehouse.OmsOutInfo;
import com.jumbo.rmi.warehouse.Order;
import com.jumbo.rmi.warehouse.OrderResult;
import com.jumbo.rmi.warehouse.PackageResult;
import com.jumbo.rmi.warehouse.RmiChannelInfo;
import com.jumbo.rmi.warehouse.RmiSku;
import com.jumbo.rmi.warehouse.WarehousePriority;
import com.jumbo.rmi.warehouse.WmsResponse;
import com.jumbo.rmi.warehouse.vmi.VmiAsn;
import com.jumbo.rmi.warehouse.vmi.VmiRto;
import com.jumbo.wms.model.hub2wms.WmsSalesOrder;
import com.jumbo.wms.model.hub2wms.WmsSalesOrderResult;

/**
 * 通用接口方法 EBS整理
 * 
 * @author jinlong.ke
 * 
 */
public interface RmiService {


    /**
     * 更新Sku
     * 
     * @param rmiSku
     * @return
     */
    BaseResult updateSku(RmiSku rmiSku);

    /**
     * 通过客户编码查询渠道信息
     * 
     * @param code
     * @return
     */
    List<RmiChannelInfo> getChannelListByCustomer(String code);

    /**
     * 根据渠道编码查询渠道信息
     * 
     * @param code
     * @return
     */
    RmiChannelInfo getChannelInfoByCode(String code);

    /**
     * 通用创单接口
     * 
     * @param order 接口实体
     * @return
     */
    BaseResult orderCreate(Order order);

    /**
     * 通用创单接口
     * 
     * @param order 接口实体
     * @return
     */
    List<BaseResult> orderCreate(List<Order> order);

    /**
     * 换货出库解锁接口
     * 
     * @param code 单据号
     * @return
     */
    BaseResult unlockOrderOut(String code, List<Invoice> invoices);

    /**
     * 解锁接口
     * 
     * @param code 单据号
     * @return
     */
    BaseResult unlockOrder(String code);

    /**
     * 获取订单商品批次
     * 
     * @param code 单据号
     * @return
     */
    OrderResult orderSkuBatchCode(Order order);

    /**
     * 盘点处理接口
     * 
     * @param code 单据号
     * @return
     */
    BaseResult execInvCheck(String code);

    /**
     * 取消订单接口
     * 
     * @param code 单据号
     * @return
     */
    BaseResult procurementOrder(String code);

    /**
     * 取消订单接口
     * 
     * @param code 单据号
     * @return
     */
    BaseResult cancelOrder(String code);

    /**
     * 费用化取消接口
     * 
     * @param code 单据号
     * @return
     */
    BaseResult cancelOrderBatchCode(String code);

    /**
     * 取消盘点接口
     * 
     * @param code 单据号
     * @return
     */
    BaseResult rejectInvCheck(String code);

    // OMS外包仓销售出库接口
    BaseResult saveoutboundOrder(OmsOutInfo out);

    RmiSku findDateBySkuExtCode1(String owner, String skuExtCode1);

    List<RmiSku> findListBySkuExtCode1(String owner, List<String> skuExtCode1);

    /**
     * 根据省市获取订单发货仓库
     * 
     * @param province 省(必填)
     * @param city 城市(必填)
     * @return
     */
    List<WarehousePriority> getPriorityWHByCity(String province, String city, Long cusId, Long cId);

    /**
     * 生成上传任务
     * 
     * @return
     */
    BaseResult createupLoadTask();

    /**
     * 异常包裹指令状态反馈接口
     * 
     * @param code wms单据编码
     * @param slipCode 反馈相关单据号
     * @return
     */
    BaseResult PackageResult(PackageResult result);

    /**
     * 通用VMI接收收货指令
     */
    WmsResponse VmiAsn(VmiAsn vmiAsn);

    /**
     * 通用扩展VMI接收收货指令
     * 
     * @param vmiAsn
     * @return WmsResponse
     * @throws
     */
    List<WmsResponse> vmiAsnExt(List<VmiAsn> vmiAsnList);

    /**
     * 通用VMI接收退仓指令
     * 
     * @param vmiRto
     * @return WmsResponse
     * @throws
     */
    WmsResponse vmiRto(VmiRto vmiRto);

    /**
     * 通用扩展VMI接收退仓指令
     * 
     * @param vmiRtoList
     * @return List<WmsResponse>
     * @throws
     */
    List<WmsResponse> vmiRtoExt(List<VmiRto> vmiRtoList);

    /**
     * 销售等订单取消接口，替代原始http接口
     * 
     * @param code
     * @return
     */
    BaseResult cancelStaByOrderCode(String code);

    /**
     * 取消采购订单接口
     * 
     * @param code 单据号
     * @return
     */
    BaseResult cancelProcurement(String code);

    /**
     * 关闭采购订单/货返入库/赠品入库订单接口
     * 
     * @param code 单据号
     * @return
     */
    BaseResult closeProcurement(String code);

    /**
     * 补开发票通知
     * 
     * @param systemKey
     * @param order
     * @return
     */
    InvoiceOrderResult invoiceOrderService(String systemKey, InvoiceOrder order);

    /**
     * 直连通用创单接口
     * 
     * @param order 接口实体
     * @return
     */
    WmsSalesOrderResult orderCreatelink(String systemKey, WmsSalesOrder order);

    /**
     * 直连通用创单批量接口
     * 
     * @param order
     * @return
     */
    List<WmsSalesOrderResult> orderCreatelinks(String systemKey, List<WmsSalesOrder> order);

    /**
     * 添加或删除店铺品牌关联
     * 
     * @param shopCode 店铺编码
     * @param brandCode 品牌编码列表
     * @return
     */
    BaseResult updateStoreBrand(String shopCode, List<String> brandCodeList);
}
