package com.jumbo.wms.manager.lf;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.baozun.bh.connector.lf.model.LfInventorys;
import cn.baozun.bh.connector.lf.model.LfInventorys.LfInventory;
import cn.baozun.bh.util.JSONUtil;

import com.jumbo.dao.warehouse.WhUaInventoryDao;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.model.vmi.warehouse.WhUaInventory;

@Service("lfManager")
public class LFManagerImpl extends BaseManagerImpl implements LFManager {
    /**
     * 
     */
    private static final long serialVersionUID = -2686412192078251799L;
    @Autowired
    private WhUaInventoryDao whUaInventoryDao;

    @Override
    public void receiveWhLFInventoryByMq(String message) {
        WhUaInventory whUaInventory = null;
        try {
            // 以下为新添加
            LfInventorys uaInventorysList = (LfInventorys) JSONUtil.jsonToBean(message, LfInventorys.class);
            if (uaInventorysList == null) {
                return;
            }

            List<LfInventory> lfInventorys = uaInventorysList.getLfInventory();
            for (LfInventory lfInventory : lfInventorys) {
                whUaInventory = new WhUaInventory();
                setWhUaInventory(whUaInventory, lfInventory);
                whUaInventoryDao.save(whUaInventory);
            }
            whUaInventoryDao.flush();
        } catch (Exception e) {
            throw new BusinessException();
        }

    }

    private void setWhUaInventory(WhUaInventory whUaInventory, LfInventory lfInventory) {
        whUaInventory.setSku(lfInventory.getSku());
        whUaInventory.setStorerKey(lfInventory.getStorerKey());
        whUaInventory.setTotalQty(lfInventory.getTotalQty());
        whUaInventory.setAvaiableQty(lfInventory.getAvaiableQty());
        whUaInventory.setShorts(lfInventory.getStatus());
        whUaInventory.setCreateTime(new Date());
    }


}
