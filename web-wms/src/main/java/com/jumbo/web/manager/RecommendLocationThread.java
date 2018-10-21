package com.jumbo.web.manager;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.jumbo.wms.manager.pda.PdaReceiveManager;
import com.jumbo.wms.model.automaticEquipment.WcsInterfaceType;
import com.jumbo.wms.model.mongodb.StaCarton;

/**
 * 在每个页面功能触发之后并且事务没有问题之后,单独启动一个线程做同步WCS操作<br/>
 * 预期此处是所有页面操作之后线程的入口。
 * 
 * @author jinlong.ke
 * @date 2016年6月7日下午3:44:03
 * 
 */
public class RecommendLocationThread implements Runnable {
    protected static final Logger log = LoggerFactory.getLogger(RecommendLocationThread.class);

    private PdaReceiveManager pdaReceiveManager;

    private List<StaCarton> staCartonList;

    private Long ouId;

    private String flag;

    private String inventoryStatus;

    private String staCode;

    /**
     * 初始化值
     * 
     * @param type
     * @param context
     * @param msgId
     */
    public RecommendLocationThread() {
        WebApplicationContext c = ContextLoader.getCurrentWebApplicationContext();
        pdaReceiveManager = (PdaReceiveManager) c.getBean("pdaReceiveManager");

    }

    public void run() {
        if ("cartonASNOk".equals(flag)) {
            cartonASNOk();
        } else if ("pdaReceiveByBox".equals(flag)) {
            pdaReceiveByBox();
        } else if ("asnOver".equals(flag)) {
            asnOver();
        }

    }

    void cartonASNOk() {
        for (StaCarton list : staCartonList) {
            if ("良品".equals(inventoryStatus)) {
                log.error("推荐  start--" + staCode + new Date().toString());
                pdaReceiveManager.recommendLocationByCarton(list.getId());
                log.error("推荐  end--" + staCode + new Date().toString());
            }
            Boolean flag = pdaReceiveManager.isAutoWh(ouId);
            if (flag) {
                Long msgId = pdaReceiveManager.sendMsgToWcs(list.getId());
                if (msgId != null) {
                    try {
                        if (msgId != null) {
                            MsgToWcsThread wt = new MsgToWcsThread();
                            wt.setMsgId(msgId);
                            wt.setType(WcsInterfaceType.SShouRongQi.getValue());
                            new Thread(wt).start();
                        }
                    } catch (Exception e) {
                        log.error("auto inbound error:" + e.getMessage());
                    }
                }
            }
        }
    }

    void pdaReceiveByBox() {
        for (StaCarton list : staCartonList) {
            pdaReceiveManager.recommendLocationByCarton(list.getId());
            Boolean flag = pdaReceiveManager.isAutoWh(ouId);
            if (flag) {
                Long msgId = pdaReceiveManager.sendMsgToWcs(list.getId());
                if (msgId != null && flag) {
                    try {
                        if (msgId != null) {
                            MsgToWcsThread wt = new MsgToWcsThread();
                            wt.setMsgId(msgId);
                            wt.setType(WcsInterfaceType.SShouRongQi.getValue());
                            new Thread(wt).start();
                        }
                    } catch (Exception e) {
                        log.error("auto inbound error:" + e.getMessage());
                    }
                }
            }

        }
    }

    void asnOver() {
        for (StaCarton list : staCartonList) {
            if ("良品".equals(inventoryStatus)) {
                pdaReceiveManager.recommendLocationByCarton(list.getId());
            }
            Boolean flag = pdaReceiveManager.isAutoWh(ouId);
            if (flag) {
                Long msgId = pdaReceiveManager.sendMsgToWcs(list.getId());
                if (msgId != null) {
                    try {
                        if (msgId != null) {
                            MsgToWcsThread wt = new MsgToWcsThread();
                            wt.setMsgId(msgId);
                            wt.setType(WcsInterfaceType.SShouRongQi.getValue());
                            new Thread(wt).start();
                        }
                    } catch (Exception e) {
                        log.error("auto inbound error:" + e.getMessage());
                    }
                }
            }
        }
    }

    public List<StaCarton> getStaCartonList() {
        return staCartonList;
    }

    public void setStaCartonList(List<StaCarton> staCartonList) {
        this.staCartonList = staCartonList;
    }

    public Long getOuId() {
        return ouId;
    }

    public void setOuId(Long ouId) {
        this.ouId = ouId;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getInventoryStatus() {
        return inventoryStatus;
    }

    public void setInventoryStatus(String inventoryStatus) {
        this.inventoryStatus = inventoryStatus;
    }

    public String getStaCode() {
        return staCode;
    }

    public void setStaCode(String staCode) {
        this.staCode = staCode;
    }



}
