package com.jumbo.wms.manager.vmi.converseYxData;

/**
 * Converse永兴业务接口
 * 
 * @author jinlong.ke
 * 
 */
public interface ConverseYxManager {

    void inBoundTransferInBoundWriteToFile(String localDir);

    void vmiReturnReceiveWriteToFile(String localDir);


}
