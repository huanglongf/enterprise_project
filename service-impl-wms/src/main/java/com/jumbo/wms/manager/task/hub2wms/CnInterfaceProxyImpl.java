package com.jumbo.wms.manager.task.hub2wms;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baozun.task.annotation.SingleTaskLock;
import com.jumbo.dao.hub2wms.CnOutOrderItemDao;
import com.jumbo.dao.hub2wms.CnOutOrderNotifyDao;
import com.jumbo.dao.hub2wms.CnOutReceiverInfoDao;
import com.jumbo.dao.warehouse.BiChannelDao;
import com.jumbo.wms.daemon.CnInterfaceProxyTask;
import com.jumbo.wms.daemon.CnInterfaceTask;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.hub2wms.WmsThreePLManager;
import com.jumbo.wms.manager.warehouse.WareHouseManagerExe;
import com.jumbo.wms.model.hub2wms.threepl.CnOutOrderNotify;

@Service("cnInterfaceProxyTask")
public class CnInterfaceProxyImpl extends BaseManagerImpl implements CnInterfaceProxyTask {

    private static final long serialVersionUID = 7340946485053510447L;

    protected static final Logger log = LoggerFactory.getLogger(CnInterfaceProxyImpl.class);

    @Autowired
    private WmsThreePLManager wmsThreePLManager;
    @Autowired
    private CnOutOrderNotifyDao cnOutOrderNotifyDao;
    @Autowired
    private CnOutReceiverInfoDao cnOutReceiverInfoDao;
    @Autowired
    private BiChannelDao biChannelDao;
    @Autowired
    private WareHouseManagerExe wareHouseManagerExe;
    @Autowired
    private CnOutOrderItemDao cnOutOrderItemDao;

    @Autowired
    private CnInterfaceTask cnInterfaceTask;


    /**
     * 生成菜鸟VMI退仓
     */
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void createCaiNiaoVmiRtn() {
        List<CnOutOrderNotify> oonList = cnOutOrderNotifyDao.getNewCnOutOrderNotify();
        if (oonList == null || oonList.size() == 0) {
            return;
        }
        for (CnOutOrderNotify oon : oonList) {
            boolean isNoQty = false;
            try {

                cnInterfaceTask.createVmiRtn(oon);
            } catch (BusinessException e) {
                BusinessException current = e;
                while (current.getLinkedException() != null) {
                    current = current.getLinkedException();
                    if (ErrorCode.PREDEFINED_OUT_CREATE_INV_ERROR == current.getErrorCode()) {
                        isNoQty = true;

                        CnOutOrderNotify n = cnOutOrderNotifyDao.getByPrimaryKey(oon.getId());
                        wmsThreePLManager.createCnInvQtyDeficiency(oon.getOrderCode());
                        n.setStatus(-1);
                        cnOutOrderNotifyDao.save(n);
                    }
                }
            } catch (Exception e) {
                log.error("createCaiNiaoVmiRtn error:", e);
            }
        }
    }

}
