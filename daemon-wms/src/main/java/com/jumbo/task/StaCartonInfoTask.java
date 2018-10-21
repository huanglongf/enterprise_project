package com.jumbo.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.wms.manager.lmis.LmisManager;

/**
 * 通过mq同步纸箱数据给LMIS
 * @author hsh10697
 */
public class StaCartonInfoTask {

    private static final Logger log = LoggerFactory.getLogger(StaCartonInfoTask.class);

    @Autowired
    private LmisManager lmisManager;

    public void sendToLMIS() {
        log.error("发送数据给mq");
        System.out.println("sendToLMIS-发送数据给mq");
        lmisManager.sendStaCartonInfo();
    }

}
