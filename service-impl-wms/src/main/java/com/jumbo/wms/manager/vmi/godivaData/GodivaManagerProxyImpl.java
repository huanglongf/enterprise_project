package com.jumbo.wms.manager.vmi.godivaData;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import loxia.dao.support.BeanPropertyRowMapperExt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;

import com.baozun.task.annotation.SingleTaskLock;
import com.jumbo.dao.boc.VmiInventorySnapshotDataDao;
import com.jumbo.dao.vmi.warehouse.CompanyShopShareDao;
import com.jumbo.dao.vmi.warehouse.MsgRtnOutboundDao;
import com.jumbo.wms.Constants;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.boc.MasterDataManager;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.manager.warehouse.WareHouseManagerProxy;
import com.jumbo.wms.model.boc.VmiInventorySnapshotData;
import com.jumbo.wms.model.vmi.warehouse.CompanyShopShare;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnOutbound;
import com.jumbo.wms.model.warehouse.InventoryCheck;

@Service("godivaManagerProxy")
public class GodivaManagerProxyImpl extends BaseManagerImpl implements GodivaManagerProxy {


    private static final long serialVersionUID = 556412771606654632L;

    @Autowired
    private GodivaManager godivaManager;

    @Autowired
    private MasterDataManager masterDataManager;

    @Autowired
    private WareHouseManager wareHouseManager;

    @Autowired
    private VmiInventorySnapshotDataDao vmiInventorySnapshotDataDao;

    @Autowired
    private CompanyShopShareDao companyShopShareDao;

    @Autowired
    private MsgRtnOutboundDao msgRtnOutboundDao;

    @Autowired
    private WareHouseManagerProxy wareHouseManagerProxy;


    public Map<String, String> warehouseMap() {
        Map<String, String> mapwh = new HashMap<String, String>();
        mapwh.put("SH", Constants.VIM_WH_SOURCE_KERRYEAS_SH);
        mapwh.put("BJ", Constants.VIM_WH_SOURCE_KERRYEAS_BJ);
        return mapwh;
    }

    public void godivaExecuteInventoryChecks(String message) {

        List<VmiInventorySnapshotData> invSnapshotDatas = godivaManager.receiveGodivaInventory(message);
        if (invSnapshotDatas != null && invSnapshotDatas.size() > 0) {
            List<CompanyShopShare> sharesList = companyShopShareDao.findShopShares("GDV", new BeanPropertyRowMapperExt<CompanyShopShare>(CompanyShopShare.class));

            if (sharesList == null || sharesList.size() != 3) {
                log.info("========= {Godiva} NOT FOUNT COMPANYSHOPSHARE===========godivaExecuteInventoryChecks");
                throw new BusinessException(ErrorCode.END_OPERATION_UNIT_NOT_FOUNT);
            }
            VmiInventorySnapshotData data = invSnapshotDatas.get(0);
            String fileName = data.getFileName();
            @SuppressWarnings("rawtypes")
            Iterator it = this.warehouseMap().keySet().iterator();
            while (it.hasNext()) {
                try {
                    String keyString = (String) it.next();
                    String vimSource = this.warehouseMap().get(keyString);
                    List<InventoryCheck> icList = godivaManager.gdvCreateInventoryCheck(fileName, vimSource, sharesList);
                    if (icList != null) {
                        for (InventoryCheck ic : icList) {
                            if (ic != null) {
                                masterDataManager.executionVmiAdjustment(ic);
                                wareHouseManager.confirmVMIInvCKAdjustment(ic);
                            } else {
                                log.debug("===============CREATE {} BOCSTOREREFERENCE ERROR================");
                                throw new Exception("InventoryCheck create error" + fileName);
                            }

                        }
                    }
                } catch (Exception e) {
                    log.error("", e);
                }
            }
            vmiInventorySnapshotDataDao.updateInventorySnapshotByfileName(fileName, 10);
        }

    }


    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void msgOutBoundTask() {
        List<MsgRtnOutbound> msgoutListSH = msgRtnOutboundDao.findAllVmiMsgOutbound(Constants.VIM_WH_SOURCE_KERRYEAS_SH, new BeanPropertyRowMapper<MsgRtnOutbound>(MsgRtnOutbound.class));
        for (MsgRtnOutbound rtnOutbound : msgoutListSH) {
            wareHouseManagerProxy.gdvinBound(rtnOutbound);
        }

        List<MsgRtnOutbound> msgoutListBJ = msgRtnOutboundDao.findAllVmiMsgOutbound(Constants.VIM_WH_SOURCE_KERRYEAS_BJ, new BeanPropertyRowMapper<MsgRtnOutbound>(MsgRtnOutbound.class));
        for (MsgRtnOutbound rtnOutbound : msgoutListBJ) {
            wareHouseManagerProxy.gdvinBound(rtnOutbound);
        }
    }
}
