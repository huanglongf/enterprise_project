package com.jumbo.wms.manager.vmi.guess;

import com.jumbo.wms.model.vmi.guess.GuessEcomAdjData;
import com.jumbo.wms.model.warehouse.InventoryCheck;

public interface GuessManager {

    /**
     * 销售订单发送到仓库
     */
    void sendSalesOrderToHub(String whSource);

    void customerReturnRequestToHub(String whSource);

    void customerDeliveryConfirmation(String message);

    void customerReturnReceiving(String message);

    void GuessEcomAdjData(String message);

    void createInventoryByAll();

    InventoryCheck vmiInvAdjustByWh(GuessEcomAdjData guessEcomAdjData);

    void customerReturnCancelRequestToHub();

    void receiveWhGuessInventoryByMq(String message);

    void receiveWhGuessInventoryRetailByMq(String message);

    void insertGuessInventoryLog();

    void insertGuessInventoryRetailLog();

    void createInventoryRetail();


    void createInventory(InventoryCheck ic);
}
