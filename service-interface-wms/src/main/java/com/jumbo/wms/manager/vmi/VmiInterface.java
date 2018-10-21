package com.jumbo.wms.manager.vmi;

import java.util.List;
import java.util.Map;

import com.jumbo.wms.model.command.SkuCommand;
import com.jumbo.wms.model.warehouse.BiChannelCommand;
import com.jumbo.wms.model.warehouse.InventoryCheck;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransVoucher;
import com.jumbo.wms.model.warehouse.StockTransApplicationType;

public interface VmiInterface {

    List<String> getVmiCode();

    /**
     * 根据中间表创建入库单据
     */
    void generateInboundSta();

    /**
     * 入库作业当收货时创建反馈/VMI为收货上架时
     * 
     * @param sta
     */
    void generateReceivingWhenInbound(StockTransApplication sta, StockTransVoucher stv);


    /**
     * 入库作业当上架时反馈
     * 
     * @param sta
     * @param stv
     */
    void generateReceivingWhenShelv(StockTransApplication sta, StockTransVoucher stv);

    /**
     * Vmi调正单据反馈
     * 
     * @param inv
     */
    void generateVMIReceiveInfoByInvCk(InventoryCheck inv);

    /**
     * Vmi调拨（转仓）单据创建
     */
    void generateVMITranscationWH();

    /**
     * VMI转店反馈数据
     * 
     * @param sta
     */
    void generateReceivingTransfer(StockTransApplication sta);

    /**
     * VMI转仓（调拨）反馈数据
     * 
     * @param sta
     */
    void generateReceivingFlitting(StockTransApplication sta);

    /***
     * 创建大仓反馈
     * 
     * @param sta
     */
    void generateRtnWh(StockTransApplication sta);

    /**
     * 根据指令创商品
     * 
     * @param po
     */
    void generateSkuByOrder(String orderName, String vmiCode);

    /**
     * 生成退仓外部单据号
     * 
     * @return
     */
    String generateRtnStaSlipCode(String vmiCode, StockTransApplicationType type);

    /**
     * 生成库存状态调整单反馈
     * 
     * @param sta
     */
    void generateInvStatusChange(StockTransApplication sta);

    /**
     * 验证 退仓 信息
     * 
     * @param sta
     */
    void validateReturnManager(StockTransApplication sta);

    /**
     * 根据入库单创库存状态调整反馈
     * 
     * @param sta
     */
    void generateInvStatusChangeByInboundSta(StockTransApplication sta);

    /**
     * 通过SKUCode取商品基础信息（不同品牌取商品基本信息的表不同）
     * 
     * @param skuCode
     * @return
     * @deprecated
     */
    SkuCommand findSkuCommond(String skuCode);

    /**
     * VMI入库收货反馈（作业单完成时反馈）
     * 
     * @param sta
     */
    void generateReceivingWhenFinished(StockTransApplication sta);
    
    /**
     * VMI入库单关闭时反馈数据
     *@param sta void 
     *@throws
     */
    void generateReceivingWhenClose(StockTransApplication sta);

    /**
     * 创建入库作业单-成功后回调方法,用于各个品牌特殊处理
     * 
     * @param slipCode
     * @param staId
     * @param lineDetial 明细行 ,key:extcode2,value:staline id
     */
    void generateInboundStaCallBack(String slipCode, Long staId, Map<String, Long> lineDetial);

    /**
     * 创建入库作业单-设置作业单头特殊信息
     * 
     * @param slipCode
     * @param head
     */
    void generateInboundStaSetHead(String slipCode, StockTransApplication head);

    /**
     * 创建入库作业单-根据唯一编码获取一单的入库单数据
     * 
     * @param slipCode
     * @return key:sku extcode2,value:qty
     */
    Map<String, Long> generateInboundStaGetDetial(String slipCode);

    /**
     * 创建入库作业单-获取所有未创建单号
     * 
     * @return
     */
    List<String> generateInboundStaGetAllOrderCode();

    /**
     * 创建入库作业单-获取所有未创建单号(通用)
     * 
     * @return
     */
    List<String> generateInboundStaGetAllOrderCodeDefault(String vmiCode, String vmiSource, String asnType);

    /**
     * 将文件读取结果记录到数据库
     * 
     * @param results
     * @return
     */
    boolean inBoundInsertIntoDB(List<String> results);

    /**
     * 创建入库作业单-设置作业单头特殊信息(通用)
     * 
     * @param slipCode
     * @param head
     */
    void generateInboundStaSetHeadDefault(String slipCode, String vmisource, StockTransApplication head);

    /**
     * 创建入库作业单-根据唯一编码获取一单的入库单数据(通用)
     * 
     * @param slipCode
     * @return key:sku extcode2,value:qty
     */
    Map<String, Long> generateInboundStaGetDetialDefault(String slipCode, String vmisource, String asnType);


    /**
     * 创建入库作业单-成功后回调方法,用于各个品牌特殊处理(通用)
     * 
     * @param slipCode
     * @param staId
     * @param lineDetial 明细行 ,key:extcode2,value:staline id
     */
    void generateInboundStaCallBackDefault(String slipCode, String orderCode, Long staId, BiChannelCommand b);
}
