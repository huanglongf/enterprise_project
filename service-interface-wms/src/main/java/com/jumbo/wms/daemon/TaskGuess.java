package com.jumbo.wms.daemon;

public interface TaskGuess {

    public void createGuessSales();

    public void sendSalesOrderToHubTask();

    public void customerReturnRequestToHubTask();

    public void receiveEcomAdjTask();


    public void customerReturnCancelRequestTask();

    public void insertGuessInventoryLog();

    public void insertGuessInventoryRetailLog();

    public void createInventoryRetail();

    public void createInventoryByAll();

    public void createInventory();
}
