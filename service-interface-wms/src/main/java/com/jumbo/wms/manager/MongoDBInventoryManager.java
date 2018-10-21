package com.jumbo.wms.manager;

import java.util.List;

import com.jumbo.wms.model.MongoDBInventory;
import com.jumbo.wms.model.MongoDBInventoryOcp;
import com.jumbo.wms.model.MongoDBInventoryPac;

/**
 * MongoDB 库存接口
 * 
 * @author jinlong.ke
 * 
 */
public interface MongoDBInventoryManager extends BaseManager {
    /**
     * 更新MongoDB对应库存
     * 
     * @param ml
     */
    void updateMongoDBInventory(List<MongoDBInventory> ml);

    /**
     * 更新MongoDB对应库存
     * 
     * @param ml
     */
    boolean updateMongoDBInventoryNew(List<MongoDBInventory> ml);

    /**
     * 更新MongoDB对应库存
     * 
     * @param ml
     */
    void updateMongoDBInventoryPac(List<MongoDBInventoryPac> ml);

    /**
     * 查询某一商品，特定仓库、渠道的库存
     * 
     * @param warehouseCode
     * @param owner
     * @param skuId
     * @param flag 是否过仓方法调用
     * @return
     */
    MongoDBInventory getInventory(String warehouseCode, String owner, Long skuId);

    /**
     * 查询某一商品，特定仓库、渠道的库存
     * 
     * @param warehouseCode
     * @param owner
     * @param skuId
     * @param flag 是否过仓方法调用
     * @return
     */
    MongoDBInventoryPac getInventoryPacOwner(String warehouseCode, String owner, String skuCode);

    /**
     * 查询某一商品,渠道的库存
     * 
     * @param warehouseCode
     * @param owner
     * @param skuId
     * @return
     */
    List<MongoDBInventory> getInventoryOwner(String owner, Long skuId);

    /**
     * 更新可用库存
     * 
     * @param warehouseCode
     * @param owner
     * @param skuId
     * @param variation
     */
    void updateMongoDBAvailQty(String warehouseCode, String owner, Long skuId, Long variation);

    /**
     * 更新可用库存
     * 
     * @param warehouseCode
     * @param owner
     * @param skuId
     * @param variation
     */
    boolean updateMongoDBAvailQtyNew(String warehouseCode, String owner, Long skuId, Long variation);

    List<Long> getAllNeedWarehouseId();

    Integer getSystemThreadValueByKey(String string);

    void initInventoryByOuId(String warehouseCode);

    void deleteInventoryForOnceUse();

    void deleteInventoryForOuId();

    /**
     * 
     */
    void createTableForMongoInv();

    /**
     * @param owner
     */
    void initInventoryForOnceUseByOwner(String owner);

    /**
     * 区域占用库存 初始化库存
     * 
     * @param owner
     */
    void initInventoryForOcpAreaByOwner(MongoDBInventoryOcp ocp, Long ouId);

    /**
     * 区域占用库存建表
     */
    void createTableForMongoAreaOcpInv(Long ouId);

    /**
     * 删库，跑路
     */
    void deleteInventoryForAreaOcpInv(Long ouId);


    /**
     * 查询某一商品，特定仓库、渠道的库存
     * 
     * @param warehouseCode
     * @param owner
     * @param skuCode
     * @param flag 是否过仓方法调用
     * @return
     */
    List<MongoDBInventoryPac> getInventoryPac(String warehouseCode, String owner, String skuCode);

    /**
     * 查询某一系列，特定仓库、渠道的库存
     * 
     * @param warehouseCode
     * @param owner
     * @param skuCode
     * @param flag 是否过仓方法调用
     * @return
     */
    List<MongoDBInventoryPac> getInventoryExtCode3Pac(String warehouseCode, String owner, String extCode);

    /**
     * 根据条件查询OCPMongDB库存
     * 
     * @param owner
     * @param ouId
     * @param skuId
     * @return
     */
    List<MongoDBInventoryOcp> getOcpInventoryOwner(String owner, Long ouId, List<Long> skuId, List<String> areaList);

    /**
     * 修改mongDb库存
     * 
     * @param ouId
     * @param owner
     * @param areaCode
     * @param skuId
     * @param quantity
     */
    int updateOcpMongoDBAvailQty(Long ouId, String owner, String areaCode, Long skuId, Integer quantity);

    /**
     * 查询MongDb库存
     * 
     * @param owner
     * @param ouId
     * @param skuId
     * @param areaList
     * @return
     */
    MongoDBInventoryOcp findOneOcpInv(String owner, Long ouId, Long skuId, String areaList);

}
