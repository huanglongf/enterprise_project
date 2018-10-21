package com.jumbo.wms.manager.warehouse;

import java.util.Date;
import java.util.List;

import loxia.dao.Pagination;
import loxia.dao.Sort;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.dao.baseinfo.SkuDao;
import com.jumbo.dao.warehouse.SpecialSkuDao;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.command.SkuCommand;
import com.jumbo.wms.model.warehouse.SpecialSku;
import com.jumbo.wms.model.warehouse.SpecialSkuType;

/**
 * 特殊处理商品业务逻辑实现
 * 
 * @author jinlong.ke
 * 
 */
@Transactional
@Service("specialSkuManager")
public class SpecialSkuManagerImpl extends BaseManagerImpl implements SpecialSkuManager {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    @Autowired
    private SpecialSkuDao specialSkuDao;
    @Autowired
    private SkuDao skuDao;

    @Override
    public void addSpecialSkuBySupplierCode(int start, int pageSize,Long id, String supplierCode) {
        List<SkuCommand> list = skuDao.getAllSkuBySupplierCode(supplierCode, new BeanPropertyRowMapper<SkuCommand>(SkuCommand.class));
        if (list == null || list.size() == 0) {
            throw new BusinessException(ErrorCode.NO_SKU_WITH_THIS_SUPPLIERCODE);
        }
        Pagination<SkuCommand> list1 = skuDao.getAllSepecialSku(start,pageSize,id, supplierCode, new Sort[] {}, new BeanPropertyRowMapper<SkuCommand>(SkuCommand.class));
        if (list1 != null && list1.getItems().size() > 0) {
            throw new BusinessException(ErrorCode.CURRENT_WAREHOUSE_HAVE_THIS_SKU);
        }
        for (SkuCommand sku : list) {
            SpecialSku ss = new SpecialSku();
            OperationUnit ou = new OperationUnit();
            ou.setId(id);
            ou.setLastModifyTime(new Date());
            ss.setOu(ou);
            Sku sku1 = new Sku();
            sku1.setId(sku.getId());
            ss.setSku(sku);
            ss.setCreateTime(new Date());
            ss.setSpecialType(SpecialSkuType.FILL_PACKAGE);
            specialSkuDao.save(ss);
        }

    }

    @Override
    public Pagination<SkuCommand> selectAllSpecialSkuByOu(int start, int pageSize,Long id, Sort[] sorts,String supplierCode) {
        return skuDao.getAllSepecialSku(start,pageSize,id, supplierCode, sorts, new BeanPropertyRowMapper<SkuCommand>(SkuCommand.class));
    }

    @Override
    public void deleteSpecialSkuById(List<Long> ids) {
        specialSkuDao.deleteAllByPrimaryKey(ids);
    }

    @Override
    public void addSpecialSkuBySupplierCodeList(int start, int pageSize, Long id, List<String> supplierCodeList) {
        if (supplierCodeList != null && supplierCodeList.size() > 0) {
            for (String supplierCode : supplierCodeList) {
                addSpecialSkuBySupplierCode(start, pageSize, id, supplierCode);
            }
        }
    }
}
