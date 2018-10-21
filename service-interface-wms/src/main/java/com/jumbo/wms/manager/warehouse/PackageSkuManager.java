package com.jumbo.wms.manager.warehouse;

import java.util.Date;
import java.util.List;


import loxia.dao.Sort;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.warehouse.PackageSkuCommand;

/**
 * 套装组合商品业务逻辑
 * @author jinlong.ke
 *
 */
public interface PackageSkuManager extends BaseManager{
    /**
     * 添加套装组合商品
     * @param currentOu
     * @param staTotalSkuQty
     * @param idList 
     * @param titleList
     * @param date
     */
    void addNewPackageSku(OperationUnit currentOu, Long staTotalSkuQty,Long skuQty, List<Long> idList, List<String> titleList, Date date);
    /**
     * 根据仓库id查询所有的套装组合商品
     * @param id
     * @param sorts
     * @return
     */
    List<PackageSkuCommand> selectAllPackageSkuByOu(Long id, Sort[] sorts);
    /**
     * 根据id删除套装组合商品
     * @param packageSkuId
     * @param ouId 
     */
    void deletePackageSkuById(Long packageSkuId, Long ouId);
    
    public String findSkus1ByPackingListId(Long pid);
}
