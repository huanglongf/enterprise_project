package com.jumbo.wms.manager.warehouse;

import java.util.List;

import loxia.dao.Pagination;
import loxia.dao.Sort;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ExpressionException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.jumbo.dao.warehouse.PrePackagedSkuRefDao;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.command.SkuCommand;

/**
 * @author jinlong.ke
 *
 */
@Service("prePackagedSkuRefManager")
public class PrePackagedSkuRefManagerImpl extends BaseManagerImpl implements PrePackagedSkuRefManager {

    /**
     * 
     */
    private static final long serialVersionUID = 1108299860332776471L;
    @Autowired
    private PrePackagedSkuRefDao prePackagedSkuRefDao;


    @Override
    public Pagination<Sku> findAllPrePackagedSkuByOu(int start, int pageSize, String barCode, Long ouId, Sort[] sorts) {
        if (!StringUtils.hasText(barCode)) {
            barCode = null;
        }
        return prePackagedSkuRefDao.getAllPrePackagedSkuByOu(start, pageSize, barCode, ouId, sorts, new BeanPropertyRowMapper<Sku>(Sku.class));
    }

    @Override
    public List<SkuCommand> findSubSkuByIdAndOu(int start, int pageSize, Long skuId, Long ouId, Sort[] sorts) {
        return prePackagedSkuRefDao.finSubSkuByIdAndOu(start, pageSize, skuId, ouId, sorts, new BeanPropertyRowMapper<SkuCommand>(SkuCommand.class));
    }

    @Override
    public Sku findPrePackagedSkuByBarCode(String barCode, Long ouId) {
        return prePackagedSkuRefDao.getSkuByBarCodeAndOu(barCode, ouId, new BeanPropertyRowMapper<Sku>(Sku.class));
    }

    @Override
    public void addPrepackagedSkuByBarCode(String barCode, Long ouId) {
        prePackagedSkuRefDao.addPrepackagedSkuByBarCode(barCode, ouId);
    }

    @Override
    public void deletePrepackagedSkuByMainSkuId(Long mainSkuId, Long ouId) {
        prePackagedSkuRefDao.deletePrepackagedSkuByMainSkuId(mainSkuId, ouId);
    }

    @Override
    public void insertPrepackagedSkuBySkuIdAndSubBarCode(Long mainSkuId, String barCode, Long qty, Long ouId) {
        prePackagedSkuRefDao.insertPrepackagedSkuBySkuIdAndSubBarCode(mainSkuId, barCode, qty, ouId);
    }

    @Override
    public void deletePrepackagedSkuByMainSkuIdAndSubSkuId(Long mainSkuId, Long subSkuId, Long ouId) {
        prePackagedSkuRefDao.deletePrepackagedSkuByMainSkuIdAndSubSkuId(mainSkuId, subSkuId, ouId);
    }

    @Override
    public Sku getSkuByBarCode(String barCode) {
        if (!StringUtils.hasText(barCode)) {
            barCode = null;
          
        }
        return prePackagedSkuRefDao.findSkuByBarCode(barCode, new BeanPropertyRowMapper<Sku>(Sku.class));
    }

    @Override
    public void deleteEmptyPrepackagedSkuByMainSkuId(Long mainSkuId) {
        prePackagedSkuRefDao.deleteEmptyPrepackagedSkuByMainSkuId(mainSkuId);
    }

}
