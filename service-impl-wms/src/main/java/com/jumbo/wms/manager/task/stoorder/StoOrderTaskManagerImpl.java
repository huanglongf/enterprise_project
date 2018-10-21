package com.jumbo.wms.manager.task.stoorder;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;

import com.jumbo.dao.baseinfo.WarehouseDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.dao.warehouse.WhTransProvideNoDao;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.expressDelivery.TransOlManager;
import com.jumbo.wms.model.baseinfo.Transportator;
import com.jumbo.wms.model.baseinfo.Warehouse;

/**
 * 
 * @author jinlong.ke
 * 
 */
@Service("stoOrderTaskManager")
public class StoOrderTaskManagerImpl extends BaseManagerImpl implements StoOrderTaskManager {

    /**
     * 
     */
    private static final long serialVersionUID = -1779257941840801046L;
    @Autowired
    private WarehouseDao warehouseDao;
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private TransOlManager transOlManager;
    @Autowired
    private WhTransProvideNoDao transProvideNoDao;

    public void stoOrder(String strWhId) {
        Long whid = Long.valueOf(strWhId);
        stoIntefaceByWarehouse(whid);
    }


    public void stoIntefaceByWarehouse(Long whId) {
        log.debug("==============" + whId + "==================");
        Warehouse wh = warehouseDao.getWarehouseByOuId(whId, new BeanPropertyRowMapper<Warehouse>(Warehouse.class));
        if (wh == null) {
            throw new BusinessException(ErrorCode.WAREHOUSE_NOT_FOUND);
        }
        Boolean flag = true;
        while (flag) {
            List<String> lpList = new ArrayList<String>();
            lpList.add("STO");
            List<Long> staList = staDao.findStaByOuIdAndStatus(whId, lpList, new SingleColumnRowMapper<Long>(Long.class));
            if (staList.size() < 100) {
                flag = false;
            }
            for (Long id : staList) {
                // STO下单
                try {
                    // 设置STO单据号
                    transOlManager.matchingTransNo(id);
                } catch (Exception e) {
                    log.error("", e);
                }
            }
        }
    }

    @Override
    public void stoInterfaceAllWarehouse() {
        Long count = transProvideNoDao.getTranNoNumberByLpCode(Transportator.STO, null, new SingleColumnRowMapper<Long>(Long.class));
        if (count > 0) {
            List<Long> idList = warehouseDao.getAllSTOWarehouse(new SingleColumnRowMapper<Long>(Long.class));
            for (Long id : idList) {
                try {
                    stoIntefaceByWarehouse(id);
                } catch (Exception e) {
                    log.error("", e);
                }
            }
        }
    }

    public Long getStoTranNoNum() {
        return transProvideNoDao.getStoTranNoNum(new SingleColumnRowMapper<Long>(Long.class));

    }

    /**
     * 根据物流商获取随机一条数据
     */
    public Long getTranNoByLpcodeList() {
        return transProvideNoDao.getTranNoByLpcodeList(new SingleColumnRowMapper<Long>(Long.class));

    }

}
