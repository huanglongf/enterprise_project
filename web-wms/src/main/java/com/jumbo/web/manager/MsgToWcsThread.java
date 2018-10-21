package com.jumbo.web.manager;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.baozun.bizhub.manager.wcs.HubWcsManager;
import com.jumbo.wms.Constants;
import com.jumbo.wms.manager.automaticEquipment.MsgToWcsManager;
import com.jumbo.wms.model.automaticEquipment.MsgToWcs;
import com.jumbo.wms.model.automaticEquipment.WcsInterfaceType;

/**
 * 在每个页面功能触发之后并且事务没有问题之后,单独启动一个线程做同步WCS操作<br/>
 * 预期此处是所有页面操作之后线程的入口。
 * 
 * @author jinlong.ke
 * @date 2016年6月7日下午3:44:03
 * 
 */
public class MsgToWcsThread implements Runnable {
    protected static final Logger log = LoggerFactory.getLogger(MsgToWcsThread.class);

    @Autowired
    private MsgToWcsManager msgToWcsManager;
    /**
     * 接口类型
     */
    private Integer type;

    /**
     * 消息通知表ID
     */
    private Long msgId;

    private HubWcsManager hubWcsManager;

    private List<Long> msgList;


    public MsgToWcsThread() {
        WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
        msgToWcsManager = (MsgToWcsManager) context.getBean("msgToWcsManager");
        hubWcsManager = (HubWcsManager) context.getBean("hubWcsManager");
    }

    /**
     * 初始化值
     * 
     * @param type
     * @param context
     * @param msgId
     */
    public MsgToWcsThread(Integer type, String context, Long msgId, List<Long> msgList) {
        WebApplicationContext c = ContextLoader.getCurrentWebApplicationContext();
        msgToWcsManager = (MsgToWcsManager) c.getBean("msgToWcsManager");
        hubWcsManager = (HubWcsManager) c.getBean("hubWcsManager");

        this.type = type;
        this.msgId = msgId;
        this.msgList = msgList;
    }

    public void run() {
        if (WcsInterfaceType.SShouRongQi.getValue() == type) {
            // 收货入库货箱流向
            sShouRongQi(msgId);
        } else if (WcsInterfaceType.SBoZhong.getValue() == type) {
            // 播种
            SBoZhong(msgId, msgList);
        } else if (WcsInterfaceType.SQuxiaoRongQi.getValue() == type) {
            // 收货入库货箱流向取消
            cancelInboundBox(msgId);
        } else if (WcsInterfaceType.OShouRongQi.getValue() == type) {
            // 拣货完成
            OShouRongQi(msgId);
        } else if (WcsInterfaceType.OQuxiaoBoZhong.getValue() == type) {
            // 播种取消
            OQuxiaoBoZhong(msgId);
        } else if (WcsInterfaceType.OQuxiaoRongQi.getValue() == type) {
            // 货箱流向取消
            OQuxiaoRongQi(msgId, msgList);
        }
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getMsgId() {
        return msgId;
    }

    public void setMsgId(Long msgId) {
        this.msgId = msgId;
    }

    public List<Long> getMsgList() {
        return msgList;
    }

    public void setMsgList(List<Long> msgList) {
        this.msgList = msgList;
    }

    // 收货入库货箱流向
    private void sShouRongQi(Long msgId) {
        MsgToWcs msg = msgToWcsManager.findMsgToWcsById(msgId);
        // 调用HUB接口
        try {
            String reslut = hubWcsManager.receiveGoodsCantainerFlow(Constants.WCS, msg.getContext());
            loxia.support.json.JSONObject obj = new loxia.support.json.JSONObject(reslut);
            String status = obj.get("status").toString(); // 状态
            String msgs = obj.get("msg").toString();
            if (status.equals("1")) {
                msgToWcsManager.deleteMsgToWcsById(msg.getId());// 成功则记录删除消息，记录日志
            } else {
                msg.setStatus(false);
                msg.setErrorMsg(msgs);
                msg.setErrorCount(msg.getErrorCount() + 1);
                msgToWcsManager.saveMsgToWcsById(msg);
                log.error("sShouRongQi  result:" + reslut);// 失败记录日志信息
            }
        } catch (Exception e) {
            msg.setErrorMsg("调用异常");
            msg.setStatus(false);
            msg.setErrorCount(msg.getErrorCount() + 1);
            msgToWcsManager.saveMsgToWcsById(msg);
            log.error(msg.getId() + " sShouRongQi Ecption is:" + e);
        }

    }

    // 播种接口
    private void SBoZhong(Long msgId, List<Long> msgList) {
        if (msgId != null) {
            msgList = new ArrayList<Long>();
            msgList.add(msgId);
        }
        MsgToWcs msg = null;
        // 调用HUB接口
        try {
            for (Long id : msgList) {
                msg = msgToWcsManager.findMsgToWcsById(id);
                String reslut = hubWcsManager.seedingTask(Constants.WCS, msg.getContext());
                log.error(msg.getId() + " SBoZhong Ecption is:" + reslut);
                loxia.support.json.JSONObject obj = new loxia.support.json.JSONObject(reslut);
                String status = obj.get("status").toString(); // 状态
                String msgs = obj.get("msg").toString(); // 异常消息
                if (status.equals("1")) {
                    msgToWcsManager.deleteMsgToWcsById(msg.getId());// 成功则记录删除消息，记录日志
                } else {
                    msg.setStatus(false);
                    msg.setErrorMsg(msgs);
                    msg.setErrorCount(msg.getErrorCount() + 1);
                    msgToWcsManager.saveMsgToWcsById(msg);
                    log.error("SBoZhong  result:" + reslut);// 失败记录日志信息
                }
            }
        } catch (Exception e) {
            msg.setErrorMsg("调用异常");
            msg.setStatus(false);
            msg.setErrorCount(msg.getErrorCount() + 1);
            msgToWcsManager.saveMsgToWcsById(msg);
            log.error(msg.getId() + " SBoZhong Ecption is:" + e);
        }

    }

    /**
     * 取消入库货箱流向
     * 
     * @param msg
     */
    private void cancelInboundBox(Long msgId) {

        MsgToWcs msg = msgToWcsManager.findMsgToWcsById(msgId);
        // 调用HUB接口
        try {
            String reslut = hubWcsManager.receiveGoodsCantainerFlowCancel(Constants.WCS, msg.getContext());
            loxia.support.json.JSONObject obj = new loxia.support.json.JSONObject(reslut);
            String status = obj.get("status").toString(); // 状态
            String msgs = obj.get("msg").toString();
            if (status.equals("1")) {
                msgToWcsManager.deleteMsgToWcsById(msg.getId());// 成功则记录删除消息，记录日志
            } else {
                msg.setStatus(false);
                msg.setErrorMsg(msgs);
                msg.setErrorCount(msg.getErrorCount() + 1);
                msgToWcsManager.saveMsgToWcsById(msg);
                log.error("OQuxiaoBoZhong  result:" + reslut);// 失败记录日志信息
            }
        } catch (Exception e) {
            msg.setErrorMsg("调用异常");
            msg.setStatus(false);
            msg.setErrorCount(msg.getErrorCount() + 1);
            msgToWcsManager.saveMsgToWcsById(msg);
            log.error(msg.getId() + " cancelInboundBox Exception is:" + e);
        }



    }

    // 播种取消
    private void OQuxiaoBoZhong(Long msgId) {
        MsgToWcs msg = msgToWcsManager.findMsgToWcsById(msgId);
        // 调用HUB接口
        try {
            String reslut = hubWcsManager.seedingTaskCancel(Constants.WCS, msg.getContext());
            loxia.support.json.JSONObject obj = new loxia.support.json.JSONObject(reslut);
            String status = obj.get("status").toString(); // 状态
            String msgs = obj.get("msg").toString();
            if (status.equals("1")) {
                msgToWcsManager.deleteMsgToWcsById(msg.getId());// 成功则记录删除消息，记录日志
            } else {
                msg.setStatus(false);
                msg.setErrorMsg(msgs);
                msg.setErrorCount(msg.getErrorCount() + 1);
                msgToWcsManager.saveMsgToWcsById(msg);
                log.error("OQuxiaoBoZhong  result:" + reslut);// 失败记录日志信息
            }
        } catch (Exception e) {
            msg.setErrorMsg("调用异常");
            msg.setStatus(false);
            msg.setErrorCount(msg.getErrorCount() + 1);
            msgToWcsManager.saveMsgToWcsById(msg);
            log.error(msg.getId() + " OQuxiaoBoZhong Ecption is:" + e);
        }


    }

    // 出库货箱流向取消
    private void OQuxiaoRongQi(Long msId, List<Long> msgList) {
        if (msgId != null) {
            msgList = new ArrayList<Long>();
            msgList.add(msgId);
        }
        MsgToWcs msg = null;
        // 调用HUB接口
        try {
            for (Long msgId : msgList) {
                msg = msgToWcsManager.findMsgToWcsById(msgId);
                String reslut = hubWcsManager.finishedCartonFlowCancel(Constants.WCS, msg.getContext());
                loxia.support.json.JSONObject obj = new loxia.support.json.JSONObject(reslut);
                String status = obj.get("status").toString(); // 状态
                String msgs = obj.get("msg").toString();
                if (status.equals("1")) {
                    msgToWcsManager.deleteMsgToWcsById(msg.getId());// 成功则记录删除消息，记录日志
                } else {
                    msg.setStatus(false);
                    msg.setErrorMsg(msgs);
                    msg.setErrorCount(msg.getErrorCount() + 1);
                    msgToWcsManager.saveMsgToWcsById(msg);
                    log.error("OQuxiaoRongQi  result:" + reslut);// 失败记录日志信息
                }
            }
        } catch (Exception e) {
            msg.setErrorMsg("调用异常");
            msg.setStatus(false);
            msg.setErrorCount(msg.getErrorCount() + 1);
            msgToWcsManager.saveMsgToWcsById(msg);
            log.error(msg.getId() + " OQuxiaoRongQi Ecption is:" + e);
        }


    }


    /**
     * 拣货完成
     * 
     * @param msgId
     */
    private void OShouRongQi(Long msgId) {
        MsgToWcs msg = msgToWcsManager.findMsgToWcsById(msgId);
        // 调用HUB接口
        try {
            String reslut = hubWcsManager.finishedCartonFlow(Constants.WCS, msg.getContext());
            loxia.support.json.JSONObject obj = new loxia.support.json.JSONObject(reslut);
            String status = obj.get("status").toString(); // 状态
            String msgs = obj.get("msg").toString();
            if (status.equals("1")) {
                msgToWcsManager.deleteMsgToWcsById(msg.getId());// 成功则记录删除消息，记录日志
            } else {
                msg.setStatus(false);
                msg.setErrorMsg(msgs);
                msg.setErrorCount(msg.getErrorCount() + 1);
                msgToWcsManager.saveMsgToWcsById(msg);
                log.error("OShouRongQi  result:" + reslut);// 失败记录日志信息
            }
        } catch (Exception e) {
            msg.setErrorMsg("调用异常");
            msg.setStatus(false);
            msg.setErrorCount(msg.getErrorCount() + 1);
            msgToWcsManager.saveMsgToWcsById(msg);
            log.error(msg.getId() + " OShouRongQi Exception is:" + e);
        }
    }

}
