package com.jumbo.wms.manager.task.edw;

import com.jumbo.wms.manager.BaseManager;

public interface EdwTaskManagerProxy extends BaseManager {

    String uploadEdwFile(String locationUploadPathReceiving, String locationUploadPathReceivingOk, String dateTimeString);

    String uploadEdwFileHk(String locationUploadPathReceiving, String locationUploadPathReceivingOk, String dateTimeString);
}
