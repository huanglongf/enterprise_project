package com.jumbo.wms.manager.mq;

import java.util.List;

import loxia.dao.support.BeanPropertyRowMapperExt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baozun.task.annotation.SingleTaskLock;
import com.jumbo.dao.vmi.warehouse.MsgOutboundOrderDao;
import com.jumbo.dao.warehouse.BiChannelDao;
import com.jumbo.mq.FlowInvoiceMsgBatch;
import com.jumbo.mq.FlowInvoicePrintMsg;
import com.jumbo.mq.MarshallerUtil;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrderCommand;

@Service("mqManagerProxy")
public class MqManagerProxyImpl extends BaseManagerImpl implements MqManagerProxy {

    /**
	 * 
	 */
    private static final long serialVersionUID = -7898143453619504591L;
    @Autowired
    private MsgOutboundOrderDao msgOutboundOrderDao;
    @Autowired
    private MqManager mqManager;
    @Autowired
    private BiChannelDao biChannelDao;

    @Override
    public void receiveMqInvoicePrintMsgWj(String message) {
        String msgStr = MarshallerUtil.decodeBase64StringWithUTF8(message);
        log.debug(msgStr);
        FlowInvoiceMsgBatch msgBatch = (FlowInvoiceMsgBatch) MarshallerUtil.buildJaxb(FlowInvoiceMsgBatch.class, msgStr);
        for (Object obj : msgBatch.getMsgs()) {
            FlowInvoicePrintMsg printMsg = (FlowInvoicePrintMsg) obj;
            mqManager.constructMqInvoicePrintMsgLog(printMsg);
        }
    }

    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void mqSendGDVSalesOrder() {
        BiChannel shop = biChannelDao.getByVmiCode(BiChannel.CHANNEL_VMICODE_GDV_STORE);
        if (shop != null) {
            List<MsgOutboundOrderCommand> orderList = msgOutboundOrderDao.findGDVSalesOrder(new BeanPropertyRowMapperExt<MsgOutboundOrderCommand>(MsgOutboundOrderCommand.class));
            for (MsgOutboundOrderCommand order : orderList) {
                mqManager.sendMqGDVSalesOrder(order, shop.getMqOrder());
            }
        }
    }

}
