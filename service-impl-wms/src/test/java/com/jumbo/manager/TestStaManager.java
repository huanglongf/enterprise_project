package com.jumbo.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.dao.baseinfo.SkuDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.wms.model.baseinfo.Sku;

@Service("testStaManager")
@Transactional
public class TestStaManager {
    @Autowired
    StockTransApplicationDao staDao;
    @Autowired
    SkuDao skuDao;


    public void getSta() {
        Sku sku = skuDao.getByPrimaryKey(275L);
        System.out.println(sku.getBrand().getName());
    }
}
