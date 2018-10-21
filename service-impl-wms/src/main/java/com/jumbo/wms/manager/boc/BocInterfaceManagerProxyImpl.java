package com.jumbo.wms.manager.boc;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import loxia.dao.support.BeanPropertyRowMapperExt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.baozun.task.annotation.SingleTaskLock;
import com.jumbo.dao.baseinfo.WarehouseDao;
import com.jumbo.dao.boc.BocStoreReferenceDao;
import com.jumbo.dao.boc.VmiInventoryMovementDataDao;
import com.jumbo.dao.boc.VmiInventorySnapshotDataDao;
import com.jumbo.dao.warehouse.BiChannelDao;
import com.jumbo.dao.warehouse.InventoryCheckDao;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.manager.warehouse.WareHouseManagerProxy;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.baseinfo.Warehouse;
import com.jumbo.wms.model.boc.BocStoreReference;
import com.jumbo.wms.model.boc.VmiInventoryMovementData;
import com.jumbo.wms.model.boc.VmiInventorySnapshotData;
import com.jumbo.wms.model.boc.VmiInventorySnapshotDataCommand;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnInboundOrder;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnOutbound;
import com.jumbo.wms.model.warehouse.InventoryCheck;

@Service("bocInterfaceManager")
public class BocInterfaceManagerProxyImpl extends BaseManagerImpl implements BocInterfaceManagerProxy {

    /**
     * 
     */
    private static final long serialVersionUID = 3245569761837157438L;

    /**
     * MQ消息模板
     */
    private JmsTemplate bhMqJmsTemplate;

    @Resource
    private ApplicationContext applicationContext;

    @Autowired
    private MasterDataManager masterDataManager;

    @Autowired
    private WareHouseManagerProxy wareHouseManagerProxy;

    @Autowired
    private WareHouseManager wareHouseManager;

    @Autowired
    private VmiInventorySnapshotDataDao vmiInventorySnapshotDataDao;

    @Autowired
    private BocStoreReferenceDao bocStoreReferenceDao;

    @Autowired
    private BiChannelDao companyShopDao;

    @Autowired
    private WarehouseDao warehouseDao;

    @Autowired
    private VmiInventoryMovementDataDao vmiInventoryMovementDataDao;

    @Autowired
    private InventoryCheckDao inventoryCheckDao;

    @PostConstruct
    protected void init() {
        try {
            bhMqJmsTemplate = (JmsTemplate) applicationContext.getBean("bhJmsTemplate");
        } catch (Exception e) {
            log.error("no bean named bhMqJmsTemplate Class:BocInterfaceManagerProxyImpl");
        }
    }

    public JmsTemplate getBhMqJmsTemplate() {
        return bhMqJmsTemplate;
    }

    public void setBhMqJmsTemplate(JmsTemplate bhMqJmsTemplate) {
        this.bhMqJmsTemplate = bhMqJmsTemplate;
    }

    public void sendOrderBySource() {
        masterDataManager.mqSendSalesOrder("");
    }

    @Override
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void executeInventoryMovement() {
        List<VmiInventoryMovementData> invMList = new ArrayList<VmiInventoryMovementData>();
        List<String> billList = vmiInventoryMovementDataDao.findBillNoListByExecuteStatus(new SingleColumnRowMapper<String>());
        for (String billno : billList) {
            invMList = vmiInventoryMovementDataDao.findInventoryMovementListByBillNoExecuteStatus(billno, new BeanPropertyRowMapperExt<VmiInventoryMovementData>(VmiInventoryMovementData.class));
            try {
                if (null != invMList && invMList.size() > 0) {
                    VmiInventoryMovementData invM = invMList.get(0);
                    BocStoreReference ref = bocStoreReferenceDao.findBocStoreReferenceBySource(invM.getSource());
                    if (ref != null) {
                        BiChannel companyShop = companyShopDao.getByPrimaryKey(Long.parseLong(ref.getStoreCode()));
                        if (companyShop == null) {
                            log.debug("===============CREATE {} BOCSTOREREFERENCE ERROR================", new Object[] {ref.getStoreCode()});
                            throw new Exception("没有找到店铺信息!storeCode:" + ref.getStoreCode());
                        }
                    }
                    if (StringUtils.hasText(invM.getSource())) {
                        Warehouse warehouse = warehouseDao.findWarehouseByVmiSource(invM.getSource());
                        if (warehouse == null) {
                            log.debug("===============CREATE {} BOCSTOREREFERENCE ERROR================", new Object[] {invM.getSource()});
                            throw new Exception("没有找到仓库信息!VMISOURCE:" + invM.getSource());
                        }
                    }
                    if (invM.getBillNo() == null) {
                        log.debug("===============CREATE {} BOCSTOREREFERENCE ERROR================", new Object[] {invM.getBillNo()});
                        throw new Exception("没有批次号!请确认数据正确!BILLNO:" + invM.getBillNo());
                    }
                    String slipCode = invM.getBillNo();
                    InventoryCheck icExist = inventoryCheckDao.findinvCheckBySlipCode(slipCode);
                    if (icExist != null) {
                        log.debug("===============CREATE {} BOCSTOREREFERENCE ERROR================", new Object[] {ref.getStoreCode()});
                        throw new Exception("该批次的盘点单已存在!BILLNO:" + invM.getBillNo());
                    }

                    InventoryCheck ic = masterDataManager.executeInventoryCheck(invMList);
                    masterDataManager.executionVmiAdjMovement(ic);
                    if (ic != null) {
                        wareHouseManager.confirmVMIInvCKAdjustment(ic);
                    } else {
                        log.debug("===============CREATE {} BOCSTOREREFERENCE ERROR================", new Object[] {ref.getStoreCode()});
                        throw new Exception("InventoryCheck create error" + invM.getFileName());
                    }
                    vmiInventoryMovementDataDao.updateInvMovementListStatusByBillNo(billno);
                }
            } catch (Exception e) {
                log.error("", e);
            }
        }
    }

    @Override
    public void receiveInventorySnapshot(String message) {
        // 接收mq消息
        List<VmiInventorySnapshotData> invSnapshotDatas = masterDataManager.receiveInventorySnapshot(message);

        try {
            if (invSnapshotDatas != null && invSnapshotDatas.size() > 0) {
                VmiInventorySnapshotData data = invSnapshotDatas.get(0);
                String innerShopid = "";
                Long ouid = null;
                String fileName = data.getFileName();
                BocStoreReference ref = bocStoreReferenceDao.findBocStoreReferenceBySource(data.getSource());
                if (ref != null) {
                    BiChannel companyShop = companyShopDao.getByPrimaryKey(Long.parseLong(ref.getStoreCode()));
                    if (companyShop != null) {
                        innerShopid = companyShop.getCode();
                    } else {
                        log.debug("===============CREATE {} BOCSTOREREFERENCE ERROR================", new Object[] {ref.getStoreCode()});
                        throw new Exception("没有找到店铺信息!storeCode:" + ref.getStoreCode());
                    }
                }
                if (StringUtils.hasText(data.getSource())) {
                    Warehouse warehouse = warehouseDao.findWarehouseByVmiSource(data.getSource());
                    if (warehouse != null) {
                        ouid = warehouse.getOu().getId();
                    } else {
                        log.debug("===============CREATE {} BOCSTOREREFERENCE ERROR================", new Object[] {data.getSource()});
                        throw new Exception("没有找到仓库信息!VMISOURCE:" + data.getSource());
                    }
                }
                List<VmiInventorySnapshotDataCommand> invSnapshotDatasInfo =
                        vmiInventorySnapshotDataDao.findVmiInventorySnapshotData(innerShopid, fileName, ouid, new BeanPropertyRowMapper<VmiInventorySnapshotDataCommand>(VmiInventorySnapshotDataCommand.class));
                if (invSnapshotDatasInfo.size() == 0) {
                    return;
                }
                InventoryCheck ic = masterDataManager.executeInventoryChecks(invSnapshotDatasInfo);
                masterDataManager.executionVmiAdjustment(ic);
                if (ic != null) {
                    wareHouseManager.confirmVMIInvCKAdjustment(ic);
                } else {
                    log.debug("===============CREATE {} BOCSTOREREFERENCE ERROR================", new Object[] {ref.getStoreCode()});
                    throw new Exception("InventoryCheck create error" + data.getFileName());
                }
                vmiInventorySnapshotDataDao.updateInventorySnapshotByfileName(fileName, 10);

            }
        } catch (Exception e) {
            log.error("", e);
        }

    }

    @Override
    public void receiveMasterDataInfo(String message) {
        masterDataManager.receiveMasterDateByMq(message);

    }

    public void bocExecuteOrderOutbound(String message) {
        List<MsgRtnOutbound> orderOutboundList = masterDataManager.receiveMsgRtnOutBoundData(message);
        masterDataManager.executeMsgRtnOutbound(orderOutboundList);
    }

    public void bocExecuteReturnOrderInbound(String message) {
        List<MsgRtnInboundOrder> msgRtnOutboundList = masterDataManager.findMsgRtnOutbounds(message);
        if (msgRtnOutboundList != null) {
            wareHouseManagerProxy.inboundToBz(msgRtnOutboundList);
        }
    }
}
