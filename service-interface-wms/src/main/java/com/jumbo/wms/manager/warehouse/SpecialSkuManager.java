package com.jumbo.wms.manager.warehouse;

import java.util.List;

import loxia.dao.Pagination;
import loxia.dao.Sort;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.command.SkuCommand;

/**
 * 特殊处理商品业务逻辑
 * 
 * @author jinlong.ke
 * 
 */
public interface SpecialSkuManager extends BaseManager {

    /**
     * 根据supplierCode和当前仓库组织节点id进行特殊商品维护
     * 
     * @param id
     * @param supplierCode
     */
    void addSpecialSkuBySupplierCode(int start, int pageSize,Long id, String supplierCode);

    /**
     * 根据当前仓库查询所有的特殊处理商品
     * 
     * @param id
     * @param sorts
     * @return
     */
    Pagination<SkuCommand> selectAllSpecialSkuByOu(int start, int pageSize,Long id, Sort[] sorts,String supplierCode);

    /**
     * 根据id删除特殊处理商品
     * 
     * @param ssId
     */
    void deleteSpecialSkuById(List<Long> ssId);
    
    /**
     * 批量导入supplierCode和当前仓库组织节点id进行特殊商品维护
     * 
     * @param id
     * @param list
     */
    void addSpecialSkuBySupplierCodeList(int start, int pageSize,Long id, List<String> supplierCodeList);

}
