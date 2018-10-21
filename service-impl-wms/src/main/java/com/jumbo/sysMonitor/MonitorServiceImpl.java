package com.jumbo.sysMonitor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baozun.monitor.bean.MonitorService;
import com.baozun.monitor.command.NodeMonitorCommand;
import com.baozun.monitor.command.ServiceNodeCommand;
import com.baozun.monitor.init.MonitorInitBean;
import com.jumbo.dao.authorization.UserDao;
import com.jumbo.wms.manager.MongoDBInventoryManager;

@Service("monitorService")
public class MonitorServiceImpl implements MonitorService {

    @Autowired
    private MonitorInitBean monitorInitBean;

    @Autowired
    private UserDao userDao;

    @Autowired
    private MongoDBInventoryManager mongoDBInventoryManager;

    /**
     * 检查db
     * 
     * @return
     */
    private NodeMonitorCommand checkDb() {

        NodeMonitorCommand nmc = new NodeMonitorCommand();

        nmc.setServiceName(NodeMonitorCommand.SERVICE_NAME_DB);

        try {
            userDao.findByLoginName("xxxxxx");
            nmc.setStatus(NodeMonitorCommand.STATUS_SUCCESS);
        } catch (Exception e) {
            nmc.setStatus(NodeMonitorCommand.STATUS_FAILURE);
        }

        return nmc;
    }

    /**
     * 检查mongodb
     * 
     * @return
     */
    private NodeMonitorCommand checkMongoDb() {

        NodeMonitorCommand nmc = new NodeMonitorCommand();

        nmc.setServiceName(NodeMonitorCommand.SERVICE_NAME_MONGODB);

        try {

            mongoDBInventoryManager.getInventoryOwner("xxxxx", 1L);

            nmc.setStatus(NodeMonitorCommand.STATUS_SUCCESS);
        } catch (Exception e) {
            nmc.setStatus(NodeMonitorCommand.STATUS_FAILURE);
        }

        return nmc;
    }



    @Override
    public ServiceNodeCommand monitor() {
        ServiceNodeCommand snc = new ServiceNodeCommand();
        // 设置dubbo服务结点的node
        snc.setServiceNode(monitorInitBean.getProtocolName() + "://" + monitorInitBean.getIp() + ":" + monitorInitBean.getPort());

        List<NodeMonitorCommand> nmcList = new ArrayList<NodeMonitorCommand>();

        nmcList.add(checkDb());

        nmcList.add(checkMongoDb());

        snc.setNodeList(nmcList);

        return snc;
    }

}
