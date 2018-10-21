package com.jumbo.zk;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.baozun.zkpro.bean.ZkChildChangeManager;
import com.jumbo.wms.manager.scheduler.ClusterControlManager;

public class ZkChildChangeManagerImpl implements ZkChildChangeManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(ZkChildChangeManagerImpl.class);

    @Autowired
    private ClusterControlManager clusterControlManager;


    private String outputList(List<String> currentChilds) {

        String result = "";
        if (currentChilds != null) {

            for (String str : currentChilds) {
                result += str + ",";
            }
        }
        return result;
    }

    @Override
    public void changeData(String parentPath, List<String> currentChilds) {
        // TODO Auto-generated method stub
        if (currentChilds == null) {
            clusterControlManager.init();
        } else {

            clusterControlManager.changeData(parentPath, currentChilds);
        }
        LOGGER.info("zk state change parentPath:" + parentPath + " curentChilds:" + outputList(currentChilds));
    }

}
