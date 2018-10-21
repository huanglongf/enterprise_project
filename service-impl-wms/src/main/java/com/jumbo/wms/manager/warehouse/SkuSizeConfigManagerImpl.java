package com.jumbo.wms.manager.warehouse;

import java.util.List;

import loxia.dao.support.BeanPropertyRowMapperExt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.dao.warehouse.SkuSizeConfigDao;
import com.jumbo.dao.warehouse.StaErrorLineDao;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.model.warehouse.SkuReplenishmentCommand;
import com.jumbo.wms.model.warehouse.SkuSizeConfig;

/**
 * 商品大小件分类
 * 
 * @author jinlong.ke
 * 
 */
@Transactional
@Service("skuSizeConfigManager")
public class SkuSizeConfigManagerImpl extends BaseManagerImpl implements SkuSizeConfigManager {

    /**
     * 
     */
    private static final long serialVersionUID = 7771338821284399555L;

    @Autowired
    private SkuSizeConfigDao skuSizeConfigDao;
    @Autowired
    private StaErrorLineDao staErrorLineDao;

    public String findBypicklistId(Long ouId, SingleColumnRowMapper<String> pname) {
        return skuSizeConfigDao.findBypicklistId(ouId, pname);
    }

    /** 
     *
     */
    @Override
    public String findBypicklistId(Long ouId) {
        return skuSizeConfigDao.findBypicklistId(ouId, new SingleColumnRowMapper<String>(String.class));
    }

    /**
     * 得到所有商品大小件分类
     * 
     * @param beanPropertyRowMapper
     * @return
     */
    public List<SkuSizeConfig> selectAllConfig() {
        return skuSizeConfigDao.selectAllConfig(new BeanPropertyRowMapper<SkuSizeConfig>(SkuSizeConfig.class));
    }

    public String findSizeBySkuQty(Long skuMaxLength) {
        return skuSizeConfigDao.findSizeBySkuQty(skuMaxLength, new SingleColumnRowMapper<String>(String.class));
    }

    public SkuReplenishmentCommand findCodeBySkuId(Long skuId) {
        return staErrorLineDao.findCodeBySkuId(skuId, new BeanPropertyRowMapperExt<SkuReplenishmentCommand>(SkuReplenishmentCommand.class));
    }

    public SkuReplenishmentCommand findstockBySkuId(Long skuId, String locationCode) {
        return staErrorLineDao.findstockBySkuId(skuId, locationCode, new BeanPropertyRowMapperExt<SkuReplenishmentCommand>(SkuReplenishmentCommand.class));
    }
}
