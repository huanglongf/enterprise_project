package com.jumbo.wms.manager.warehouse;

import java.util.List;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.MongoDBInventoryOcp;


/**
 * 区域占用库存
 * 
 * @author xiaolong.fei
 *
 */
public interface AreaOcpStaManager extends BaseManager {


    /**
     * 指定订单的一轮库存计算
     * 
     * @param staId 订单ID
     */
    void ocpInvByStaId(Long staId);

    /**
     * 初始化MongoDB库存
     * 
     * @param owner
     */
    void initInventoryForOcpAreaByOwner(MongoDBInventoryOcp ocp, Long ouId);

    /**
     * 创建MongBd表
     */
    void createMongDbTableForOcp(Long ouId);

    /**
     * 删库跑路
     */
    void deleteInventoryForAreaOcpInv(Long ouId);

    /**
     * 查询所有的区域列表
     * 
     * @param ouId
     * @return
     */
    List<String> findAllAreaCode(Long ouId);

    /**
     * 按区域查找MongDb库存
     * 
     * @param areaCode
     * @return
     */
    List<MongoDBInventoryOcp> findMongDbInvList(String areaCode, Long ouId);

    /**
     * 按区域查找MongDb库存扣减数据
     * 
     * @param areaCode
     * @return
     */
    List<MongoDBInventoryOcp> findMongDbInvListMinus(String areaCode, Long ouId);
}
