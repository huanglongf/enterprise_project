package com.jumbo.wms.manager.vmi.CathKidstonData;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.vmi.cathKidstonData.CKReceiveConfrimCommand;
import com.jumbo.wms.model.vmi.cathKidstonData.CKTransferOutCommand;
import com.jumbo.wms.model.vmi.cathKidstonData.CathKidstonStatus;

public interface CathKidstonManager extends BaseManager {

    /**
     * 保存cathkidston收货指令入中间表
     */
    void saveCKreceive(File file, String backupPath) throws Exception;

    List<CKReceiveConfrimCommand> writeReceivingDataToFile(String locationUploadPath) throws Exception;

    Map<String, Long> generateInboundStaGetDetial(String slipCode);

    void createStaForCathKidston(String vmiCode);

    List<CKTransferOutCommand> writeTransferOutDataToFile(String locationUploadPath) throws Exception;

    void updateTransferOutStatus(List<CKTransferOutCommand> cktoList, CathKidstonStatus cs);

    void updateCKReceiveConfrimStatus(List<CKReceiveConfrimCommand> ckrcList, CathKidstonStatus cs);
}
