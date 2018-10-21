package com.jumbo.wms.manager.task;

import com.jumbo.wms.manager.BaseManager;

public interface SendMqByPacAndTomsTask extends BaseManager {

    public void sendMqByPacAndToms();

    public void sendMqTomsByMqLogTime();
}
