package com.jumbo.wms.manager.warehouse;

import java.util.List;

import loxia.dao.Pagination;
import loxia.dao.Sort;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.command.SkuCommand;

/**
 * 
 * @author jinlong.ke
 * 
 */
public interface PrePackagedSkuRefManager extends BaseManager {
    /**
     * 分页查询当前仓库已有的预包装商品列表
     * 
     * @param start
     * @param pageSize
     * @param barCode
     * @param long1 
     * @param sorts
     * @return
     */
    Pagination<Sku> findAllPrePackagedSkuByOu(int start, int pageSize, String barCode, Long long1, Sort[] sorts);
    /**
     * 
     * @param start
     * @param pageSize
     * @param skuId
     * @param id
     * @param sorts
     * @return
     */
    List<SkuCommand> findSubSkuByIdAndOu(int start, int pageSize, Long skuId, Long id, Sort[] sorts);
    /**
     * 根据条码查询组合商品
     * @param barCode
     * @param ouId 
     * @return
     */
    Sku findPrePackagedSkuByBarCode(String barCode, Long ouId);
    
    /**
     * 根据商品条码和组织ID添加预包装商品列表
     * @param barCode
     * @param ouId
     */
    void addPrepackagedSkuByBarCode(String barCode, Long ouId);

    /**
     * 根据主商品条码和组织ID删除预包装商品列表
     * @param mainSkuId
     * @param ouId
     */
    void deletePrepackagedSkuByMainSkuId(Long mainSkuId, Long ouId);
    
   /**
    * 根据主商品SkuId和子商品条形码插入预包装商品
    * @param mainSkuId
    * @param barCode
    * @param qty
    * @param ouId
    */
    void insertPrepackagedSkuBySkuIdAndSubBarCode(Long mainSkuId, String barCode, Long qty, Long ouId);
    
    /**
     * 根据主商品SkuId和子商品SkuId删除预包装商品
     * @param mainSkuId
     * @param subSkuId
     * @param ouId
     */
    void deletePrepackagedSkuByMainSkuIdAndSubSkuId(Long mainSkuId, Long subSkuId, Long ouId);
    
    /**
     * 通过BarCode获得Sku是否存在
     * @param barCode
     */
    Sku getSkuByBarCode(String barCode);
    
    /**
     * 根据MainSkuId删除空的预包装商品
     * @param mainSkuId
     */
    void deleteEmptyPrepackagedSkuByMainSkuId(Long mainSkuId);
    
    
}
