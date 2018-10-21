package com.jumbo.wms.manager.task.vmi;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;

import com.baozun.task.annotation.SingleTaskLock;
import com.jumbo.dao.vmi.cch.CchStockReturnInfoDao;
import com.jumbo.dao.vmi.cch.CchStockTransConfirmDao;
import com.jumbo.dao.warehouse.BiChannelDao;
import com.jumbo.wms.daemon.CCHTask;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.mq.MqManager;
import com.jumbo.wms.manager.vmi.VmiFactory;
import com.jumbo.wms.manager.vmi.VmiInterface;
import com.jumbo.wms.model.DataStatus;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.vmi.cch.CchStockReturnInfo;
import com.jumbo.wms.model.vmi.cch.CchStockTransConfirm;

@Service("cchTask")
public class CCHTaskImpl extends BaseManagerImpl implements CCHTask {

    /**
	 * 
	 */
    private static final long serialVersionUID = -8066976482706382334L;


    @Autowired
    private BiChannelDao shopDao;
    @Autowired
    private CchStockTransConfirmDao confirmDao;
    @Autowired
    private CchStockReturnInfoDao returnInfoDao;
    @Autowired
    private MqManager mqManager;
    @Autowired
    private VmiFactory f;

    public void generateInbountSta() {
        VmiInterface vmiCCH = f.getBrandVmi(BiChannel.CHANNEL_VMICODE_CACHECAHEC_CCH);
        vmiCCH.generateInboundSta();
        VmiInterface vmiBNB = f.getBrandVmi(BiChannel.CHANNEL_VMICODE_CACHECAHEC_BNB);
        vmiBNB.generateInboundSta();
    }

    /**
     * 收货确认
     */
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void mqSendASNReceive() {
        try {
            BiChannel shopCCH = shopDao.getByVmiCode(BiChannel.CHANNEL_VMICODE_CACHECAHEC_CCH);
            BiChannel shopBNB = shopDao.getByVmiCode(BiChannel.CHANNEL_VMICODE_CACHECAHEC_BNB);
            List<CchStockTransConfirm> headerListCCH = confirmDao.findConfirmByStatus(DataStatus.CREATED.getValue(), BiChannel.CHANNEL_VMICODE_CACHECAHEC_CCH);
            List<CchStockTransConfirm> headerListBNB = confirmDao.findConfirmByStatus(DataStatus.CREATED.getValue(), BiChannel.CHANNEL_VMICODE_CACHECAHEC_BNB);
            mqManager.sendMqCCHAsnReceive(headerListCCH, shopCCH.getMqASNReceive(), shopCCH.getId());
            mqManager.sendMqCCHAsnReceive(headerListBNB, shopBNB.getMqASNReceive(), shopBNB.getId());
        } catch (Exception e) {
            log.error("", e);
        }
    }

    /**
     * 退大仓反馈
     */
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void mqSendStockReturn() {
        try {
            BiChannel shopCCH = shopDao.getByVmiCode(BiChannel.CHANNEL_VMICODE_CACHECAHEC_CCH);
            BiChannel shopBNB = shopDao.getByVmiCode(BiChannel.CHANNEL_VMICODE_CACHECAHEC_BNB);
            List<Long> rStaIdCCH = returnInfoDao.findStaIdByStatus(DataStatus.CREATED.getValue(), BiChannel.CHANNEL_VMICODE_CACHECAHEC_CCH, new SingleColumnRowMapper<Long>(Long.class));
            List<CchStockReturnInfo> returnListCCH = null;
            for (Long staId : rStaIdCCH) {
                returnListCCH = returnInfoDao.findReturnInfoByStatusAndStaId(DataStatus.CREATED.getValue(), BiChannel.CHANNEL_VMICODE_CACHECAHEC_CCH, staId);
                mqManager.sendMqCCHRTVList(returnListCCH, shopCCH.getMqRTV(), shopCCH.getId(), staId);
            }
            List<Long> rStaIdBNB = returnInfoDao.findStaIdByStatus(DataStatus.CREATED.getValue(), BiChannel.CHANNEL_VMICODE_CACHECAHEC_BNB, new SingleColumnRowMapper<Long>(Long.class));
            for (Long staId : rStaIdBNB) {
                returnListCCH = returnInfoDao.findReturnInfoByStatusAndStaId(DataStatus.CREATED.getValue(), BiChannel.CHANNEL_VMICODE_CACHECAHEC_BNB, staId);
                mqManager.sendMqCCHRTVList(returnListCCH, shopBNB.getMqRTV(), shopBNB.getId(), staId);
            }
        } catch (Exception e) {
            log.error("", e);
        }
    }

    public void mqSendCCHSales() {
        // Calendar c = Calendar.getInstance();
        // Date today = new Date();
        // c.setTime(today);
        // int day = c.get(Calendar.DATE);
        // c.set(Calendar.DATE, day - 1);
        // Date yesterday = c.getTime();
        // mqManager.sendMqCCHSale(2542L, "912", yesterday, "oms2bh_cch_sales_production");
        // mqManager.sendMqCCHSale(4202L, "912", yesterday, "oms2bh_cch_sales_bnb");
    }

}
