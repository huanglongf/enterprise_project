package com.jumbo.wms.manager.task.hub2wms;

import java.util.Date;
import java.util.List;

import loxia.dao.support.BeanPropertyRowMapperExt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.dao.hub2wms.MsgOrderCancelDao;
import com.jumbo.dao.vmi.warehouse.MsgOutboundOrderCancelDao;
import com.jumbo.wms.daemon.MsgOrderCancelTask;
import com.jumbo.wms.model.hub2wms.MsgOrderCancel;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrderCancel;

@Transactional
public class MsgOrderCancelImpl implements MsgOrderCancelTask {
    @Autowired
    private MsgOutboundOrderCancelDao cancelDao;
    @Autowired
    private MsgOrderCancelDao msgOrderCancelDao;

    @Override
    public void createMsgOrder() {
        List<MsgOutboundOrderCancel> cancels = cancelDao.findMsgCancelListKey(new BeanPropertyRowMapperExt<MsgOutboundOrderCancel>(MsgOutboundOrderCancel.class));
        for (MsgOutboundOrderCancel cancel : cancels) {
            MsgOrderCancel orderCancel = new MsgOrderCancel();
            orderCancel.setCreateTime(new Date());
            orderCancel.setIsCanceled(cancel.getIsCanceled());
            orderCancel.setMsg(cancel.getMsg());
            orderCancel.setResult(cancel.getResult());
            orderCancel.setSource(cancel.getSource());
            orderCancel.setStaCode(cancel.getStaCode());
            orderCancel.setStatus(cancel.getStatus());
            orderCancel.setUpdateTime(cancel.getUpdateTime());
            orderCancel.setVersion(cancel.getVersion());
            orderCancel.setSystemKey(cancel.getSystemKey());
            msgOrderCancelDao.save(orderCancel);
            cancel.setIsKey(1);
            cancelDao.save(cancel);
        }

    }

}
