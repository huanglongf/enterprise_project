package com.jumbo.wms.manager.warehouse;

import java.util.Date;
import java.util.List;

import loxia.dao.Sort;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.command.SecKillSkuCommand;

/**
 * 秒杀商品业务逻辑
 * 
 * @author jinlong.ke
 * 
 */
public interface SecKillSkuManager extends BaseManager {
    /**
     * 添加新的团购商品
     * 
     * @param currentOu
     * @param skus
     * @param expireDate
     */
    void addNewSecKillSku(OperationUnit currentOu, Integer skuQty,List<Long> idList,List<String> titleList, Date expireDate);

    /**
     * 返回所有的秒杀商品
     * 
     * @param id
     * @param sorts 
     * @return
     */
    List<SecKillSkuCommand> selectAllSecKillSkuByOu(Long id, Sort[] sorts);
    
    /**
     * 删除秒杀商品
     * @param skus
     * @param secKillId
     * @param id
     */
    void deleteSecKillSkuByIdAndSkus(String skus, Long secKillId, Long id);

}
