package com.jumbo.wms.manager.vmi.gymboreeData;

import java.util.List;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.vmi.gymboreeData.GymboreeReceiveData;
import com.jumbo.wms.model.vmi.gymboreeData.GymboreeReceiveRtnData;
import com.jumbo.wms.model.vmi.gymboreeData.GymboreeRtnOutData;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrderCancel;

/**
 * 金宝贝对接业务逻辑处理
 * 
 * @author jinlong.ke
 * 
 */
public interface GymboreeTaskManager extends BaseManager {
    /**
     * 零售单指示
     */
    void outboundNotice(String flag) throws Exception;

    /**
     * 取消零售单指示
     * 
     * @param cl
     */
    void cancelOutbound(MsgOutboundOrderCancel cl);

    /**
     * 接收零售单实绩
     * 
     * @param msg
     */
    void receiveOutboundMsg(String msg) throws Exception;

    /**
     * 接收零售单取消实绩
     * 
     * @param msg
     */
    void receiveCancelMsg(String msg) throws Exception;

    /**
     * 店存入库指示
     * 
     * @param msg
     */
    void receiveInboundData(String msg) throws Exception;

    /**
     * 店存入库创单执行
     * 
     * @param sta
     * @param rtn
     */
    void createStaForInBound(GymboreeReceiveData rtn);

    /**
     * 退货指示
     */
    void rtnInboundNotice(String flag);

    /**
     * 退货实绩
     * 
     * @param msg
     */
    void receiveRtnInboundMsg(String msg) throws Exception;

    /**
     * 接收店存出库指令
     * 
     * @param msg
     */
    void receiveRtnOutboundMsg(String msg) throws Exception;

    /**
     * 根据店存出库指令创建出库指令单据
     * 
     * @param set
     * 
     * @param list
     */
    void careateRtnOutboundSta(String set, List<GymboreeReceiveRtnData> list);

    /**
     * 店存出库反馈
     * 
     * @param list
     * @param set
     */
    void sendRtnOutboundResult(String set, List<GymboreeRtnOutData> list);

    /**
     * 店存入库反馈
     * 
     * @param set
     * @param list
     */
    void sendRtnInboundResult(String set, List<GymboreeRtnOutData> list);

    /**
     * 其他出入库指示
     * 
     * @param msg
     */
    void receiveOtherInOut(String msg) throws Exception;

    /**
     * 其他出库反馈
     * 
     * @param set
     * @param list
     */
    void otherOutboundRtn(String set, List<GymboreeRtnOutData> list);

    /**
     * 其他入库反馈
     * 
     * @param set
     * @param list
     */
    void otherInboundRtn(String set, List<GymboreeRtnOutData> list);

    /**
     * 金宝贝仓库档案
     * 
     * @param msg
     * @throws Exception
     */
    void receiveGymboreeWarehouseMsg(String msg) throws Exception;

    /**
     * 判断有无错误单据，并进行邮件反馈
     */
    void sendMailForErrorReback();

}
