package com.jumbo.wms.manager.vmi.guess;

public interface GuessManagerProxy {
    void receiveProductMasterByMq(String message);
    
    void receiveEcomAdj();
    
}
