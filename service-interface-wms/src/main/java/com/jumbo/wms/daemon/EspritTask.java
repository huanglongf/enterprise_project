package com.jumbo.wms.daemon;

import java.io.File;

public interface EspritTask {

    public void downloadFile();

    public void uploadFile();

    public void receivedNotUploadEmailInform();

    public void saveReceivingFile(String locationUploadPath);

    public void saveDeliveryFile(String locationUploadPath);

    // 创入库单
    public void generateSta();

    // 创转仓（调拨）单
    public void generateTransferSta();

    public void downloadEspritDispatchInfo();

    // 重新推送mq
    public void sendMqByPac();

    public void sendMqByOms();

    // 创转入单
    public void generateEspritDispatchSta();

    // 转入转出反馈
    public void inOutboundEspritDeliveryRtn();

    public void downloadTestFile();

    public void analyzeInvData(File file, String localPath, String localBackupPath, String fileStart);

}
