package com.jumbo.wms.manager.inventorySnapshot;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.dao.inventorySnapshot.InventorySnapShotDao;
import com.jumbo.dao.warehouse.InventoryDao;
import com.jumbo.wms.model.inventorySnapShot.InvWhFullInventory;
import com.jumbo.wms.model.inventorySnapShot.InventorySnapShot;

@Transactional
@Service("inventorySnapshotManager")
public class InventorySnapshotManagerImpl implements InventorySnapshotManager {

    /**
     * 
     */
    private static final long serialVersionUID = -1650868165568700764L;

    protected static final Logger logger = LoggerFactory.getLogger(InventorySnapshotManagerImpl.class);
    @Autowired
    private InventoryDao inventoryDao;
    @Autowired
    private InventorySnapShotDao inventorySnapShotDao;

    /**
     * 更改对应状态
     */
    @Override
    public void updateInventorySnapshotStatusById(List<InvWhFullInventory> invWhFullInventory, Integer status) {
        for (InvWhFullInventory i : invWhFullInventory) {
            InventorySnapShot inv = inventorySnapShotDao.getByPrimaryKey(Long.parseLong(i.getUniqueKey()));
            inv.setStatus(status);
        }
    }

}
