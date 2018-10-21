package com.jumbo.zk;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.baozun.zkpro.bean.ZkStateChangeManager;
import com.jumbo.wms.bean.TaskControl;
import com.jumbo.wms.manager.scheduler.ClusterControlManager;

public class ZkStateChangeManagerImpl implements ZkStateChangeManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(ZkStateChangeManagerImpl.class);
    @Autowired
    private ClusterControlManager clusterControlManager;
    // 上次断线时间
    private static Date lastDisconnected = new Date();
    // 是否已断线
    private static boolean isDisconnected = false;

    @Value("${zk.session.timeout}")
    private Integer zkSessionTimeout;

    @Override
    public void handleNewSession() throws Exception {
        isDisconnected = false;
        clusterControlManager.init();
        LOGGER.info("zk new Session create");
    }

    @Override
    public void handleStateChanged(KeeperState arg0) throws Exception {
        LOGGER.info("zk stateChange:" + arg0);
    }

    @Override
    public void handlerDisconnected() throws Exception {
        LOGGER.info("zk Disconnected");
        lastDisconnected = new Date();
        isDisconnected = true;
        // 如果任务是打开状态，才需要考虑是否要关闭任务的可能性
        if (TaskControl.isOn()) {
            ScheduledExecutorService scheduledThreadPool = new ScheduledThreadPoolExecutor(1);
            scheduledThreadPool.schedule(new Runnable() {
                @Override
                public void run() {
                    Date now = new Date();
                    // 已经断线，并超过sessionTimeout的时间
                    // 则关闭所有定时任务
                    if (isDisconnected && now.getTime() - lastDisconnected.getTime() >= zkSessionTimeout) {
                        TaskControl.off();
                        LOGGER.info("[taskoff]current machine all task is off.");
                    }
                }
            }, zkSessionTimeout / 1000, TimeUnit.SECONDS);

        }
    }

    @Override
    public void handlerExpired() throws Exception {
        TaskControl.off();

        LOGGER.info("[taskoff]zk Expired.all task off");
    }

    @Override
    public void handlerSyncConnected() throws Exception {
        LOGGER.info("zk SyncConnected");
        isDisconnected = false;
    }

}
