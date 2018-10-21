package com.jumbo.wms.manager.vmi.vmiInventory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.dao.baseinfo.SkuDao;
import com.jumbo.dao.vmi.defaultData.VmiInventoryDao;
import com.jumbo.dao.warehouse.BiChannelDao;
import com.jumbo.rmi.warehouse.WmsResponse;
import com.jumbo.rmi.warehouse.vmi.VmiInventory;
import com.jumbo.service.HubService;
import com.jumbo.wms.daemon.VmiDefaultFactory;
import com.jumbo.wms.daemon.VmiDefaultInterface;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.vmi.ext.ExtParam;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.vmi.Default.VmiGeneralStatus;
import com.jumbo.wms.model.vmi.Default.VmiInventoryCommand;
import com.jumbo.wms.model.vmi.Default.VmiInventoryDefault;
import com.jumbo.wms.model.warehouse.BiChannelCommand;
import com.jumbo.wms.model.warehouse.InventoryCommand;

@Transactional
@Service("vmiInventoryManager")
public class VmiInventoryManagerImpl extends BaseManagerImpl implements VmiInventoryManager {

    private static final long serialVersionUID = -230333698474341952L;

    @Autowired
    private BiChannelDao biChannelDao;
    @Autowired
    private SkuDao skuDao;
    @Autowired
    private VmiDefaultFactory vmiDefaultFactory;
    @Autowired
    private VmiInventoryDao vmiInventoryDao;
    @Autowired
    private HubService hubService;

    /**
     * 通过VMICODE获取对应渠道CODE
     */
    @Override
    public List<String> getBiChannelByVmiCode(String vmicode) {
        return biChannelDao.getBiChannelByVmiCode(vmicode, new SingleColumnRowMapper<String>(String.class));
    }

    /**
     * 根据品牌定制获取相关数据插入vmi库存快照表
     */
    @Override
    public void insertVmiInventory(BiChannelCommand bi, List<String> ownerList) {
        // 品牌定制数据
        VmiDefaultInterface vv = vmiDefaultFactory.getVmiDefaultInterface(bi.getDefaultCode());
        List<InventoryCommand> iList = vv.findVmiInventoryByOwner(ownerList);
        for (InventoryCommand i : iList) {
            ExtParam ext = new ExtParam();
            Sku sku = skuDao.getByPrimaryKey(i.getSkuId());
            ext.setSku(sku);
            ext.setBi(bi);
            ext.setInventoryCommand(i);
            vv.generateInsertVmiInventory(ext);
        }
    }

    /**
     * ck 根据品牌定制获取相关数据插入vmi库存快照表
     */
    @Override
    public void insertCKVmiInventory(String defaultCode, List<String> vmiCodeList) {
        // 品牌定制数据
        VmiDefaultInterface vv = vmiDefaultFactory.getVmiDefaultInterface(defaultCode);
        for (String vmiCode : vmiCodeList) {
            List<String> ownerList = getBiChannelByVmiCode(vmiCode);
            List<InventoryCommand> iList = vv.findVmiInventoryByOwner(ownerList);

            for (InventoryCommand i : iList) {
                BiChannelCommand b = new BiChannelCommand();
                b.setDefaultCode(defaultCode);
                b.setVmiCode(vmiCode);
                ExtParam ext = new ExtParam();
                Sku sku = skuDao.getByPrimaryKey(i.getSkuId());
                ext.setSku(sku);
                ext.setBi(b);
                ext.setInventoryCommand(i);
                vv.generateInsertVmiInventory(ext);
            }

        }
    }

    @Override
    public void uploadVmiInventoryToHub(String vmiCode) {
        List<VmiInventoryCommand> vList = new ArrayList<VmiInventoryCommand>();
        try {
            vList = vmiInventoryDao.vmiInventoryToHubList(vmiCode, new BeanPropertyRowMapper<VmiInventoryCommand>(VmiInventoryCommand.class));
            List<VmiInventory> list = new ArrayList<VmiInventory>();
            for (VmiInventoryCommand v : vList) {
                VmiInventory vv = new VmiInventory();
                vv.setUuid(uuidString());
                vv.setBatchNo(v.getBatchNo());
                vv.setBlockQty(v.getBlockQty());
                vv.setOnHoldQty(v.getOnHoldQty());
                vv.setCreateTime(v.getCreateTime());
                vv.setExtMemo(v.getExtMemo());
                vv.setInvStatus(v.getInvStatus());
                vv.setQty(v.getQty());
                vv.setStoreCode(v.getStoreCode());
                vv.setUpc(v.getUpc());
                vv.setVmiSource(v.getVmiSource());
                vv.setWhCode(v.getWhCode());

                list.add(vv);
            }
            if (list.size() > 0) {
                boolean check = uploadVmiInventoryToHub(list);
                if (check) {
                    // 成功
                    updateVmiInventoryStatus(vList, VmiGeneralStatus.FINISHED);
                } else {
                    // 失败
                    updateVmiInventoryStatus(vList, VmiGeneralStatus.FAILED);
                }
            }
        } catch (Exception e) {
            log.error("", e);
            updateVmiInventoryStatus(vList, VmiGeneralStatus.FAILED);
        }
    }

    public boolean uploadVmiInventoryToHub(List<VmiInventory> list) {
        boolean success = false;
        for (int i = 0; i <= 6; i++) {
            if (success) {
                break;
            }
            try {
                WmsResponse w = hubService.inventorySnapshot(list);
                if (w.getStatus() == WmsResponse.STATUS_SUCCESS) {
                    success = true;
                } else {
                    success = false;
                }
            } catch (Exception e) {
                if (log.isErrorEnabled()) {
                    log.error("uploadVmiInventoryToHub Exception:", e);
                }
            }
        }
        return success;
    }

    public void updateVmiInventoryStatus(List<VmiInventoryCommand> list, VmiGeneralStatus status) {
        for (VmiInventoryCommand v : list) {
            VmiInventoryDefault vv = vmiInventoryDao.getByPrimaryKey(v.getId());
            vv.setFinishTime(new Date());
            vv.setStatus(status);
            vmiInventoryDao.save(vv);
        }
    };

    /**
     * 生成UUID
     */
    private String uuidString() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

}
