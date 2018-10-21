package com.jumbo.wms.manager.vmi.Default;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;

import com.jumbo.dao.vmi.defaultData.VmiInventoryDao;
import com.jumbo.dao.warehouse.InventoryDao;
import com.jumbo.wms.manager.vmi.ext.ExtParam;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.vmi.Default.VmiGeneralStatus;
import com.jumbo.wms.model.vmi.Default.VmiInventoryDefault;
import com.jumbo.wms.model.warehouse.BiChannelCommand;
import com.jumbo.wms.model.warehouse.InventoryCommand;

/**
 * MK品牌定制逻辑
 * 
 * @author kai.zhu
 * 
 */
@Service("vmiDefaultMK")
public class VmiDefaultMK extends BaseVmiDefault {

    @Autowired
    private InventoryDao inventoryDao;
    @Autowired
    private VmiInventoryDao vmiInventoryDao;

    /**
     * 查询sku库存中MK品牌所有的skuid
     */
    @Override
    public List<InventoryCommand> findVmiInventoryByOwner(List<String> ownerList) {
        return inventoryDao.findVmiInventoryByOwner(ownerList, new BeanPropertyRowMapper<InventoryCommand>(InventoryCommand.class));
    }

    /**
     * 保存库存快照数据
     */
    @Override
    public void generateInsertVmiInventory(ExtParam ext) {
        Sku sku = ext.getSku();
        if (!sku.getIsGift()) {
            // 非库存商品不做反馈
            InventoryCommand i = ext.getInventoryCommand();
            BiChannelCommand bi = ext.getBi();
            String q = inventoryDao.findVmiInventoryQtyAndBlockQtyAndOnHoldQtySPEEDO(sku.getId(), i.getInvOwner(), new SingleColumnRowMapper<String>(String.class));
            String[] qty = q.split(",");
            // 良品+良品不可销售
            if (Long.parseLong(qty[0]) + Long.parseLong(qty[1]) != 0L) {
                VmiInventoryDefault v1 = new VmiInventoryDefault();
                v1.setCreateTime(getCustomDate(new Date()));// 前一天的23时59分59秒
                v1.setStoreCode(bi.getVmiCode());
                v1.setVersion(1);
                v1.setFinishTime(getCustomDate(new Date()));// 前一天的23时59分59秒
                v1.setStatus(VmiGeneralStatus.NEW);
                v1.setErrorCount(0);
                v1.setVmiSource(bi.getVmiSource());
                v1.setUpc(sku.getExtensionCode2());
                v1.setQty(Long.parseLong(qty[0]) + Long.parseLong(qty[1]));
                v1.setInvStatus("良品");
                v1.setExtMemo(sku.getSupplierCode());
                vmiInventoryDao.save(v1);
            }
            // 除良品之外所有都是残次品
            if (Long.parseLong(qty[2]) != 0L) {
                VmiInventoryDefault v2 = new VmiInventoryDefault();
                v2.setCreateTime(getCustomDate(new Date()));// 前一天的23时59分59秒
                v2.setStoreCode(bi.getVmiCode());
                v2.setVersion(1);
                v2.setFinishTime(getCustomDate(new Date()));// 前一天的23时59分59秒
                v2.setStatus(VmiGeneralStatus.NEW);
                v2.setErrorCount(0);
                v2.setVmiSource(bi.getVmiSource());
                v2.setUpc(sku.getExtensionCode2());
                v2.setQty(Long.parseLong(qty[2]));
                v2.setInvStatus("残次品");
                v2.setExtMemo(sku.getSupplierCode());
                vmiInventoryDao.save(v2);
            }
        }
    }

    private Date getCustomDate(Date d) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.add(Calendar.DAY_OF_MONTH, -1); // 得到前一天
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        Date date = calendar.getTime();
        return date;
    }
}
