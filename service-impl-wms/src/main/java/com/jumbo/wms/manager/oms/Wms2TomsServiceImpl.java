package com.jumbo.wms.manager.oms;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;

import com.jumbo.dao.warehouse.InventoryDao;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.model.warehouse.InventoryCommand;

@Service("wms2TomsService")
public class Wms2TomsServiceImpl extends BaseManagerImpl implements Wms2TomsService{
    private static final Logger log = LoggerFactory.getLogger(Wms2TomsServiceImpl.class);
    private static final long serialVersionUID = 3694839989456124541L;
    @Autowired
    private InventoryDao inventoryDao;

    @Override
    public List<InventoryCommand> getskuInventory(String skuCode, String shop) {
        log.info("getskuInventory start:"+skuCode+","+shop);
        if(skuCode==null||shop==null){
            return null;
        }
        return inventoryDao.findInventoryByCodeAndShop(skuCode, shop, new BeanPropertyRowMapper<InventoryCommand>(InventoryCommand.class));
    }

}
