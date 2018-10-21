package com.jumbo.wms.manager.pda;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.authorization.User;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.mongodb.AsnReceive;
import com.jumbo.wms.model.mongodb.StaCarton;
import com.jumbo.wms.model.mongodb.StaCartonLine;
import com.jumbo.wms.model.mongodb.StaCartonLineSn;
import com.jumbo.wms.model.pda.StaOpDetail;
import com.jumbo.wms.model.warehouse.InventoryCommand;
import com.jumbo.wms.model.warehouse.SkuSn;
import com.jumbo.wms.model.warehouse.StockTransApplication;

public interface PdaReceiveManager {

    String initMongodbWhReceiveInfo(String staCode, OperationUnit op);

    String initMongodbWhcartonBox(String cartonBox, String staCode, Long ouId);

    Boolean verifySku(String staCode, String barCode, Long qty);

    Sku findPDASkuByBarCode(String staCode, String barCode);

    StaOpDetail findOpDetailBySn(String sn);

    String pdaCheckSendlocationCode(String sendlocationCode, Long ouId, String skuCode, String locationCode, String statusName, String owner);

    String pdaFindSku(String skuBarCode, Long ouId, String locationCode, String statusName, String owner);

    AsnReceive findBySkuId(String staCode);

    String isShelfSku(Long ouId, String skuBarCode);

    public String pdaOutBoundHandNum(Long userId, Long ouId);
    
    public String pdaOutBoundHandCurrencyNum(Long userId, Long ouId);

    String pdaExecuteTransitInner(String sendlocationCode, Long ouId, String skuCode, String locationCode, String statusName, String owner, String qty, Long userId, String userName);

    public String findChooseOption();

    String findSkuExpireDate(String skuBarCode, Long ouId, String locationCode);

    String isGroupSta(String staCode, Long ouId);

    String isGroupSta1(String staCode, Long ouId);

    public Boolean checkLocation(String locationCoce, Long ouId);

    StaOpDetail findBySku(String staCode, String code, Long userId, Long ouId);

    StaOpDetail findByNo(String staCode, Long skuId, Long userId, Long ouId);

    void updateMogodbRestQty(String barCode, Long qty, String staCode);

    void updateContainerStatus(String cartonBox);

    String createOutBoundHost(Long userId, Long ouId);
    
    String createOutBoundHostCurrency(Long userId, Long ouId);

    StockTransApplication findSta(String staCode, OperationUnit op);

    Boolean findMongodbByRestQty(String staCode);

    StaCartonLineSn findSnbysn(String sn);

    Boolean isAutoWh(Long ouId);

    String checkTrackingNo(String trackingNo, Long ouId, Long userId);
    
    String checkTrackingNoCurrency(String lpCode, String trackingNo, Long ouId, Long userId);

    Boolean checkAdTrackingNo(String trackingNo);

    String checkTrackingNo2(String trackingNo, Long ouId, Long userId);


    Boolean findSkuByStaLine(String staCode, Long ouId);

    void asnOver(String staCode, String cartonBox, String inventoryStatus, Long ouId, String dataList, String quantitativeModel, User user, OperationUnit op) throws ParseException;

    Long mongodbfindQtyByCode(String staCode, String barCode);

    void mongodbDeleteByStaCode(String staCode);

    public String checkSkuBarCode(String skuBarCode, Long ouId, String locationCode);

    String pdaReceiveByBox(String staCode, User user, OperationUnit op, String tag, String inventoryStatus);

    List<StaCarton> staCartonList(String staCode, OperationUnit op);

    Boolean findMongodbByRestQtyAndBarCode(String staCode, String barCode);



    /**
     * 根据货箱推荐库位并返回没有推荐到库位的数量
     * 
     * @param cartonId
     * @return
     */
    public Map<String, Long> recommendLocationByCarton(Long cartonId);

    /**
     * 库位推荐规则By箱明细
     */
    public Long locationRecommendBySku(Long cartonLineId);

    /**
     * 根据货箱获取弹出口
     * 
     * @param cartonId
     * @return
     */
    public Long sendMsgToWcs(Long cartonId);

    void findAsnReceiveByTime();

    void saveCartonAndLine(String staCode, Long ouId, String inventoryStatus);

    void insertStaOpDetailLog(String staCode, Long ouId);

    BiChannel findDefectType(String staCode);

    void updateCartonLineSn(String dmgCode, String dmgReason, String dmgType);


    void updateStaCartonStatus(List<StaCarton> list);

    /**
     * 根据货箱ID获取未推荐到的明细
     * 
     * @param cartonId
     * @return
     */
    public List<StaCartonLine> findRecommendFaildCartonLine(Long cartonId);

    SkuSn findSnByOuId(String sn, Long ouId);

    Long checkTag(String staCode, String tag, OperationUnit currentOu);
    
    /**
     * 根据条码或库位编码获取库存数量
     * @param barCode
     * @param locationCode
     * @param ouId
     * @return
     */
    List<InventoryCommand> getInventoryByBarCodeOrLocation(String barCode,String locationCode,Long ouId);

    /**
     * 根据作业单校验商品三维信息
     * 
     * @param sta
     * @return
     */
    public String verifySkuThreeDimensional(StockTransApplication sta, String staCode, Long ouId, Boolean isVerifyMdb);

    /**
     * 根据商品校验商品三维信息
     * @param staCode
     * @param ouId
     * @param barCode
     * @return
     */
    String verifySkuThreeDimensionalBySku(String staCode,Long ouId, String barCode);

    String trucking(String licensePlateNumber, String trackingNo);

    String truckingConfirm(String licensePlateNumber, String trackingNo);
}
