package com.jumbo.wms.manager.pda;

import java.util.List;
import java.util.Map;

import com.jumbo.wms.model.automaticEquipment.GoodsCollectionLog;
import com.jumbo.wms.model.mongodb.MongoDBWhPicking;
import com.jumbo.wms.model.pda.PdaSortPickingCommand;
import com.jumbo.wms.model.warehouse.RtwDieking;
import com.jumbo.wms.web.commond.GoodsCollectionCommand;

import loxia.dao.Pagination;

public interface PdaPickingManager {
    /**
     * 根据拣货编码初始化mongodb数据
     * 
     * @param code
     * @return
     */
    public String initMongodbWhPickingInfo(String code, Long userId, Long ouId);


    /**
     * 校验拣货编码是否可用
     * 
     * @param code
     * @return
     */
    public String checkPickingCode(String code);


    /**
     * 根据拣货编码获取
     * 
     * @param code
     */
    public MongoDBWhPicking findLocationByPickingCode(String code, int status);

    /**
     * PDA扫描商品拣货
     * 
     * @param skuBarCode
     * @param pickingId
     * @param cCode
     * @param userId
     */
    public String pickingSku(Long pickingId, String cCode, Long userId);

    /**
     * 周转箱补绑
     * 
     * @param pickingId
     * @param cCode
     * @return
     */
    public String bindBoxByBoxCode(String pickingCode, String cCode);

    /**
     * 校验条码是否正确
     * 
     * @param skuBarCode
     * @param pickingId
     * @return
     */
    public MongoDBWhPicking checkSkuBarCode(String skuBarCode, Long pickingId);

    /**
     * 更新剩余数量并返回
     * 
     * @param pickingId
     * @param userId
     * @param cCode
     * @return
     */
    public Long updateMongoDBWhPickingByPicking(Long pickingId, String cCode, Long userId,Boolean isReturnWareHouse);

    /**
     * 根据拣货编码获取短拣信息
     * 
     * @param code
     */
    public List<MongoDBWhPicking> findShortByPickingCode(String code);

    /**
     * 更新wp数据状态
     * 
     * @param pickingId
     * @param code
     */
    public void updateWhPickingStatusByPicking(Long pickingId, Integer status, String code, String pickingStatus);

    /**
     * 二级批次拣货完成
     * 
     * @param code
     */
    public List<Long> pickingBatchOver(String code, Long userId);

    /**
     * 查询短拣信息
     * 
     * @param start
     * @param pageSize
     * @param params
     * @return
     */
    public Pagination<PdaSortPickingCommand> findSortPicking(int start, int pageSize, Map<String, Object> params);

    /**
     * 导出短拣信息
     * 
     * @param start
     * @param pageSize
     * @param params
     * @return
     */
    public List<PdaSortPickingCommand> findSortPickingList(Map<String, Object> params);

    /**
     * 根据配货批ID获取打印编码
     * 
     * @param plId
     * @return
     */
    public List<String> findPickingBatchBarCode(Long plId);

    /**
     * 根据多个配货批ID获取打印编码
     * 
     * @param plId
     * @return
     */
    public List<String> findPickingBatchBarCodeS(String plIdS);

    /**
     * PDA拣货 校验周转箱是否可用
     * 
     * @param pickingBarCode
     * @param boxCode
     * @return
     */
    public String checkBox(String pickingBarCode, String boxCode);

    /**
     * 获取已拣货的数量
     * 
     * @param code
     * @param cCode
     * @return
     */
    public Integer getPickingNum(String code, String cCode);

    /**
     * 如有取消的作业单则重置拣货数量
     * 
     * @param pickingId
     */
    public void resetPickingQtyByCancelSta(Long pickingId);

    /**
     * 根据拣货条码重置数量
     * 
     * @param barCode
     */
    public void resetPickingQtyByBarcode(String barCode);


    /**
     * 校验此周转箱是否可以人工集货
     * 
     * @param code
     * @return
     */
    public String checkCollectionBox(String code);


    /**
     * 集周转箱
     * 
     * @param code
     * @return
     */
    public String collectionBox(String code, Long ouId, Long userId);


    /**
     * 移走集货周转箱
     * 
     * @param barCode
     * @param collectionCode
     * @param ouId
     * @return
     */
    public String moveCollectionBox(String barCode, String collectionCode, boolean b, Long ouId, Long userId);

    /**
     * 移走集货周转箱2
     * 
     * @param barCode
     * @param collectionCode
     * @param ouId
     * @return
     */
    public String moveCollectionBox2(String barCode, String collectionCode, boolean b, Long ouId, Long userId);


    /**
     * 查询集货区域状态
     * 
     * @param pickingCode
     * @param collectionCode
     * @param ouId
     * @return
     */
    public Map<String, Object> queryCollectionBoxStatus(String pickingCode, String collectionCode, Long ouId);

    /**
     * 查询集货的数量
     * 
     * @return
     */
    public Map<String, String> findCollectionQty(Long ouId);


    /**
     * 重新推荐集货区域
     */
    public List<Long> anewRecommendCollection(String code);

    /**
     * 校验作业单是否是当前小批次下
     * 
     * @param slipCode
     * @param pickingCode
     * @param ouId
     * @return
     */
    public Boolean checkStaAndBatch(String slipCode, String pickingCode, Long ouId);



    boolean initMongodbWhPickingInfo2(String pickingbarCode, Long ouId);

    /**
     * 是否是自动化仓单件
     * 
     * @param pickingCode
     * @return
     */
    public Boolean checkAutoSingle(String pickingCode);

    /**
     * 获取已绑定的周转箱
     * 
     * @param pickingCode
     * @return
     */
    public List<String> findBindBoxByPickingCode(String pickingCode);

    /**
     * 查询已经二次分拣完成的集货库位
     * 
     * @param ouId
     * @return
     */
    public List<GoodsCollectionCommand> findTwoPickingOver(Long ouId);

    /**
     * 二次分拣完成，释放集货库位
     * 
     * @param pickingCode
     * @param collectionCode
     * @param ouId
     * @return
     */
    public String moveCollectionBoxByPickingOver(String pickingCode, String collectionCode, Long ouId, Long userId);
    
    /**
     * 根据拣货编码初始化mongodb数据-退仓拣货逻辑
     * 
     * @param code
     * @return
     */
    public String initMongodbWhPickingInfoRTWareHouse(String code, Long userId, Long ouId);
    
    /**
     * 查看此拣货批是否是VAS
     * 
     * @param pickingbarCode
     * @return
     */
    public Boolean orderIsVas(String pickingbarCode);

    /**
     * 根据拣货条码更新数量-退仓拣货
     * 
     * @param barCode
     */
    public void updatePickingQtyByBarcode(String barCode,Long ouId);
    
    /**
     * 退仓-PDA拣货 校验周转箱是否可用
     * 
     * @param pickingBarCode
     * @param boxCode
     * @return
     */
    public String returnWhCheckBox(String pickingBarCode, String boxCode);
    
    /**
     * 退仓二级批次拣货完成
     * @param code
     * @param userId
     * @return
     */
    public List<Long> returnWhousePickingBatchOver(String code, Long userId,Long ouId);
    
    /**
     * 根据拣货批次条码获取退仓拣货数据
     */
    public RtwDieking getRtwDiekingByBatchCode(String btCode);
    
    /**
     * PDA扫描商品拣货-退仓
     * 
     * @param skuBarCode
     * @param pickingId
     * @param cCode
     * @param userId
     */
    public String returnWhpickingSku(Long pickingId, String cCode, Long userId);
    /**
     * 人工集货日志查询
     * 
     * @param start
     * @param pageSize
     * @param params
     * @return
     */
    public Pagination<GoodsCollectionLog> findGoodsCollectionLog(int start, int pageSize, Map<String, Object> params);

}
