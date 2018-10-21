package com.jumbo.wms.manager.warehouse;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.jumbo.dao.warehouse.CreatePickingListSqlDao;
import com.jumbo.dao.warehouse.ShopStoreInfoDao;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.model.warehouse.CreatePickingListSql;
import com.jumbo.wms.model.warehouse.ShopStoreInfo;

/**
 * 
 * @author jinlong.ke
 * 
 */
@Service("shopStoreInfoManager")
public class ShopStoreInfoManagerImpl extends BaseManagerImpl implements ShopStoreInfoManager {

    /**
     * 
     */
    private static final long serialVersionUID = -1896787941528179617L;
    @Autowired
    private ShopStoreInfoDao shopStoreInfoDao;
    @Autowired
    private CreatePickingListSqlDao createPickingListSqlDao;

    @Override
    public List<ShopStoreInfo> getAllShopStore(String code) {
        String cd = null;
        if (code != null && StringUtils.hasText(code)) {
            cd = code;
        }
        return shopStoreInfoDao.findAllShopStore(cd, new BeanPropertyRowMapper<ShopStoreInfo>(ShopStoreInfo.class));
    }

    public List<CreatePickingListSql> getAllRuleName(Long ouId, String modeName) {
        return createPickingListSqlDao.getAllRuleName(ouId, modeName, new BeanPropertyRowMapper<CreatePickingListSql>(CreatePickingListSql.class));
    }

    @Override
    public void deleteShopStoreById(Long id) {
        shopStoreInfoDao.deleteByPrimaryKey(id);
    }

    @Override
    public void editShopStoreByCode(ShopStoreInfo ssi) {
        shopStoreInfoDao.editShopStoreByCode(ssi.getCode(), ssi.getName(), ssi.getReceiver(), ssi.getAddress(), ssi.getTelephone(), ssi.getCountry(), ssi.getProvince(), ssi.getCity(), ssi.getDistrict());
    }

    @Override
    public void addNewShopStore(ShopStoreInfo ssi) {
        ShopStoreInfo s = shopStoreInfoDao.getShopStoreInfoByCode(ssi.getCode());
        if (s != null) {
            // 门店已存在，不能重复
            throw new BusinessException("");
        }
        ShopStoreInfo si = new ShopStoreInfo();
        si.setCode(ssi.getCode());
            si.setName(ssi.getName());
        si.setAddress(ssi.getAddress());
        si.setCreateTime(new Date());
        si.setLastModifyTime(new Date());
        si.setReceiver(ssi.getReceiver());
        si.setTelephone(ssi.getTelephone());
        si.setCountry(ssi.getCountry());
        si.setProvince(ssi.getProvince());
        si.setCity(ssi.getCity());
        si.setDistrict(ssi.getDistrict());
        shopStoreInfoDao.save(si);
    }
    
}
