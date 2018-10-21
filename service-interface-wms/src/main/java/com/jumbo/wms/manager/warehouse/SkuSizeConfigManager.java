package com.jumbo.wms.manager.warehouse;

import java.util.List;

import org.springframework.jdbc.core.SingleColumnRowMapper;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.warehouse.SkuReplenishmentCommand;
import com.jumbo.wms.model.warehouse.SkuSizeConfig;

/**
 * 商品大小件分类
 * 
 * @author lihui
 * 
 */
public interface SkuSizeConfigManager extends BaseManager {
    @Deprecated
    public String findBypicklistId(Long ouId, SingleColumnRowMapper<String> pname);

    String findBypicklistId(Long ouId);

    /**
     * 得到所有商品大小件分类
     * 
     * @return
     */
    public List<SkuSizeConfig> selectAllConfig();

    /**
     * 根据特定的skuMaxLength查询对应的大中小
     * 
     * @param skuMaxLength
     * @param singleColumnRowMapper
     * @return
     */
    String findSizeBySkuQty(Long skuMaxLength);

    /**
     * 根据 配货失败作业单 ID查找库存数量
     * 
     * @param skuId
     * @return
     */
    SkuReplenishmentCommand findCodeBySkuId(Long skuId);

    /**
     * 根据 配货失败作业单 ID查找库存数量
     * 
     * @param ouId
     * @param skuId
     * @return
     */
    SkuReplenishmentCommand findstockBySkuId(Long skuId, String locationCode);
}
