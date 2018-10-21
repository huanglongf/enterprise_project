package com.jumbo.zk;

import org.I0Itec.zkclient.ZkClient;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.baozun.scm.baseservice.message.rocketmq.service.governance.GovernanceService;
import com.baozun.zkpro.bean.ZkDateChangeManager;

/**
 * server端增加notice监控
 * 
 * @author
 *
 */
public class ZkDataChangeManagerImpl implements ZkDateChangeManager {

    protected static final Logger log = LoggerFactory.getLogger(ZkDataChangeManagerImpl.class);

    @Value("${zk.notice.log.change}")
    private String logChangePath;

    @Value("${zk.mqservice.root}")
    private String mqservicePath;

    @Value("${zk.customs.declaration.nike.vmicode}")
    private String cdNikeVmiCode;

    @Autowired
    private GovernanceService governanceService;

    @Autowired
    ZkClient zkClient;

    public static String nikeVmiCode = "";

    @Override
    public void changeData(String dataPath, Object data) {
        if (dataPath.equals(logChangePath)) {
            if (data == null) {
                data = getPathData(dataPath);
            }
            if (data != null) {
                changeLogLevel(data);
            }
        }
        if (dataPath.equals(mqservicePath)) {
            governanceService.init(mqservicePath);
        }
        if (dataPath.equals(cdNikeVmiCode)) {
            Object obj = zkClient.readData(dataPath);
            // System.out.println(obj);
            nikeVmiCode = obj.toString();
        }
    }



    // @Override
    // public void changeData(String dataPath, Object data) {
    // if(dataPath.equals(logChangePath)&&data!=null){
    // changeLogLevel(data);
    // }
    // if (dataPath.equals(mqservicePath)) {
    // governanceService.init(mqservicePath);
    // }
    // }

    private Object getPathData(String dataPath) {
        Object obj = zkClient.readData(dataPath);
        return obj;
    }


    /*
     * @Override public void changeData(String dataPath, Object data) {
     * if(dataPath.equals(logChangePath)&&data!=null){ changeLogLevel(data); } if
     * (dataPath.equals(mqservicePath)) { governanceService.init(mqservicePath); } }
     */
    /**
     * 动态调整日志
     * 
     * @param data
     */
    private void changeLogLevel(Object data) {
        String strSource = (String) data;

        String[] strTops = strSource.split(";");

        for (int i = 0; i < strTops.length; i++) {
            String[] strs = strTops[i].split("-");

            if (strs.length > 1) {
                String packagePath = strs[0];
                String strlevel = strs[1];
                Level level = Level.toLevel(strlevel);
                org.apache.log4j.Logger logger = LogManager.getLogger(packagePath);
                logger.setLevel(level);
            } else if (strs.length == 1) {
                Level level = Level.toLevel(strSource);
                LogManager.getRootLogger().setLevel(level);
            }
        }
        log.info(" change log level :" + strSource);
    }

    @Override
    public void deleteData(String dataPath) {
        System.out.println("=================");
    }

}
