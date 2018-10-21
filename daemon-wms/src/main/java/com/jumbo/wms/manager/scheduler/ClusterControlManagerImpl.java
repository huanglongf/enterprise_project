package com.jumbo.wms.manager.scheduler;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.I0Itec.zkclient.ZkClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.jumbo.wms.bean.TaskControl;


@Service("clusterControlManager")
public class ClusterControlManagerImpl implements ClusterControlManager {

    private static final Logger LOG = LoggerFactory.getLogger(ClusterControlManagerImpl.class);

    @Value("${zk.daemon.cluster.root}")
    private String clusterRoot;

    @Autowired
    private ZkClient zkClient;

    private String curValue;


    @Override
    public void changeData(String parentPath, List<String> currentChilds) {
        // TODO Auto-generated method stub
        Collections.sort(currentChilds);
        // 如果自己创建的结点是第一个，则启动定时任务
        // 保险起见，先暂停10秒钟,再启动所有任务
        if (currentChilds != null && currentChilds.size() > 0 && currentChilds.get(0).equals(curValue)) {
            try {
                // 如果以前是关闭状态,在切换之前，sleep10秒
                if (!TaskControl.isOn()) {
                    Thread.sleep(10000L);
                    TaskControl.on();
                    LOG.info("[taskon]current machine all task is on.");
                }

            } catch (InterruptedException e) {
                LOG.error("sleep error", e);
            }
        }
    }



    @Override
    public void init() {
        long now = System.currentTimeMillis();

        String value = zkClient.createEphemeralSequential(clusterRoot + "/", now);

        int index = value.lastIndexOf("/");

        curValue = value.substring(index + 1);



    }

}
