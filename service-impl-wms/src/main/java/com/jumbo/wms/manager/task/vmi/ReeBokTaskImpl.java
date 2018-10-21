package com.jumbo.wms.manager.task.vmi;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import com.baozun.task.annotation.SingleTaskLock;
import com.jumbo.dao.baseinfo.BrandDao;
import com.jumbo.dao.baseinfo.CustomerDao;
import com.jumbo.dao.baseinfo.WarehouseDao;
import com.jumbo.dao.system.ChooseOptionDao;
import com.jumbo.dao.vmi.reebok.ReebokSalesInventoryDao;
import com.jumbo.dao.vmi.reebok.ReebokTotalInventoryDao;
import com.jumbo.dao.warehouse.InventoryDao;
import com.jumbo.wms.daemon.ReebokTask;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.model.system.ChooseOption;
import com.jumbo.wms.model.vmi.reebokData.ReebokSalesInventory;
import com.jumbo.wms.model.vmi.reebokData.ReebokTotalInventory;


public class ReeBokTaskImpl extends BaseManagerImpl implements ReebokTask {
    private static final long serialVersionUID = -3850532728995107682L;

    public static final String REEBOK_OWN = "reebok_own";// 店铺code
    public static final String REEBOK_WCODE = "reebok_wcode";// 仓库id
    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private BrandDao brandDao;
    @Autowired
    private WarehouseDao warehouseDao;
    @Autowired
    private InventoryDao inventoryDao;
    @Autowired
    private ReebokTotalInventoryDao reebokTotalInventoryDao;
    @Autowired
    private ReebokSalesInventoryDao reebokSalesInventoryDao;
    @Autowired
    private ChooseOptionDao chooseOptionDao;


    /**
     * 全量库存
     */
    @Override
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void totalInventoryReebok() {
        log.debug("==============================ReebokTask totalInventoryReebok start");
        List<String> ownList = new ArrayList<String>();
        List<String> wcodeList = new ArrayList<String>();
        List<ChooseOption> chooselist = chooseOptionDao.findOptionListByCategoryCode(REEBOK_OWN);
        for (ChooseOption chooseOption : chooselist) {
            ownList.add(chooseOption.getOptionKey());// 店铺code
        }
        List<ChooseOption> chooselist2 = chooseOptionDao.findOptionListByCategoryCode(REEBOK_WCODE);
        for (ChooseOption chooseOption : chooselist2) {
            wcodeList.add(chooseOption.getOptionKey());// 仓库id
        }
        if (ownList.size() > 0) {
            List<ReebokTotalInventory> totalList = null;
            try {
                // 查询出全量库存
                totalList = inventoryDao.findReebokTotalInventoryByCodes(wcodeList, ownList, new BeanPropertyRowMapper<ReebokTotalInventory>(ReebokTotalInventory.class));
            } catch (Exception e) {
                if (log.isErrorEnabled()) {
                    log.error("ReeBokTask totalInventoryReebok error...", e);
                }
            }
            for (ReebokTotalInventory reebokTotalInventory : totalList) {
                reebokTotalInventory.setCreateTime(new Date());// 创建时间
                reebokTotalInventory.setFinishTime(new Date());
                reebokTotalInventory.setStatus(1);// 启用
                reebokTotalInventory.setVersion(1);
                reebokTotalInventoryDao.save(reebokTotalInventory);
            }
        } else {
            log.debug("===ReebokTask totalInventoryReebok codes size=0");
        }
        log.debug("==============================ReebokTask totalInventoryReebok end");
    }

    /*
     * 销售库存
     */
    @Override
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void salesInventoryReebok() {
        log.debug("==============================ReebokTask salesInventoryReebok start");
        List<String> ownList = new ArrayList<String>();
        List<String> wcodeList = new ArrayList<String>();
        List<ChooseOption> chooselist = chooseOptionDao.findOptionListByCategoryCode(REEBOK_OWN);
        for (ChooseOption chooseOption : chooselist) {
            ownList.add(chooseOption.getOptionKey());// 店铺code
        }
        List<ChooseOption> chooselist2 = chooseOptionDao.findOptionListByCategoryCode(REEBOK_WCODE);
        for (ChooseOption chooseOption : chooselist2) {
            wcodeList.add(chooseOption.getOptionKey());// 仓库id
        }
        if (ownList.size() > 0) {
            // 查询出销售库存
            List<ReebokSalesInventory> salesList = inventoryDao.findReebokSalesInventoryByCodes(wcodeList, ownList, new BeanPropertyRowMapper<ReebokSalesInventory>(ReebokSalesInventory.class));
            for (ReebokSalesInventory sales : salesList) {
                sales.setCreateTime(new Date());// 创建时间
                sales.setFinishTime(new Date());
                sales.setInventoryTime(new Date());// 出库时间
                sales.setStatus(1);// 启用
                sales.setVersion(1);
                reebokSalesInventoryDao.save(sales);
            }
        } else {
            log.debug("===ReebokTask salesInventoryAdidas codes size=0");
        }
        log.debug("==============================ReebokTask salesInventoryReebok end");
    }

}
