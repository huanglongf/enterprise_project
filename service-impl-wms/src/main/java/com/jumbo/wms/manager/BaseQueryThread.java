package com.jumbo.wms.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jumbo.wms.manager.warehouse.WareHouseManagerQuery;
import com.jumbo.wms.model.warehouse.InventoryCommand;


public class BaseQueryThread implements Runnable {
    protected static final Logger log = LoggerFactory.getLogger(BaseQueryThread.class);
    private WareHouseManagerQuery wareHouseManagerQuery;

    private InventoryCommand ic;

    private String threadGroup;


    public BaseQueryThread(Object ob, String threadGroup, WareHouseManagerQuery wareHouseManagerQuery) {
        if (ob instanceof InventoryCommand) {
            ic = (InventoryCommand) ob;
        }
        this.threadGroup = threadGroup;
        this.wareHouseManagerQuery = wareHouseManagerQuery;
        // wareHouseManagerQuery = (WareHouseManagerQuery)
        // SpringBeanFactory.getxBean("wareHouseManagerQuery", WareHouseManagerQuery.class);

    }



    public void run() {
        if (ic != null) {
            queryInvQtyPage();
        }
        BaseQueryThreadPoolManagerImpl.threadQtyPlus(threadGroup);
    }

    /**
     * 库存数量查询页面
     */
    public void queryInvQtyPage() {
        InventoryCommand ocInv = wareHouseManagerQuery.findQtyOccupiedInv(ic.getWhOuId(), ic.getSkuId(), ic.getInvOwner());
        InventoryCommand salesqty = wareHouseManagerQuery.findSalesQtyInv(ic.getWhOuId(), ic.getSkuId(), ic.getInvOwner());
        Long availQty = ic.getQuantity();
        Long lockQty = 0L;
        Long salesAvailQty = 0L;
        if (availQty != null) {
            if (ocInv != null && ocInv.getQuantity() != null) {
                availQty = availQty - ocInv.getQuantity();
            }
            if (salesqty != null && salesqty.getLockQty() != null) {
                availQty = availQty - salesqty.getLockQty();
            }
            lockQty = ic.getQuantity() - availQty;
        } else {
            availQty = 0L;
        }
        if (salesqty != null && salesqty.getSalesQty() != null) {
            salesAvailQty = salesqty.getSalesQty();
            if (ocInv != null && ocInv.getQuantity() != null) {
                salesAvailQty = salesAvailQty - ocInv.getQuantity();
            }
            if (salesqty != null && salesqty.getSalesLockQty() != null) {
                salesAvailQty = salesAvailQty - salesqty.getSalesLockQty();
            }
        }

        ic.setAvailQty(availQty);
        ic.setLockQty(lockQty);
        ic.setSalesAvailQty(salesAvailQty);
    }

}
