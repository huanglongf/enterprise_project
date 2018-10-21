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
 * Ralph 定制
 * 
 * @author yang.wang
 * 
 * @date 2017年6月12日 下午3:24:28
 */
@Service("vmiDefaultRalph")
public class VmiDefaultRalph extends BaseVmiDefault {

    @Autowired
    private InventoryDao inventoryDao;
    @Autowired
    private VmiInventoryDao vmiInventoryDao;

    /**
     * 查询sku库存中vmiDefaultRalph品牌所有的skuid
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
            InventoryCommand i = ext.getInventoryCommand();
            BiChannelCommand bi = ext.getBi();
            String q = inventoryDao.findVmiInventoryQtyAndBlockQtyAndOnHoldQtyPaulFrank(sku.getId(), i.getInvOwner(), new SingleColumnRowMapper<String>(String.class));
            // InventoryStatus is = inventoryStatusDao.getByPrimaryKey(i.getInventoryStatusId());
            boolean a = true;
            boolean b = true;
            String[] qty = q.split(",");
            for (int j = 0; j < qty.length; j++) {
                if (a == true) {
                    if (Long.parseLong(qty[0]) != 0L) {
                        VmiInventoryDefault v = new VmiInventoryDefault();
                        v.setCreateTime(getCustomDate(new Date()));// 前一天的23时59分59秒
                        v.setStoreCode(bi.getVmiCode());
                        v.setVersion(1);
                        v.setFinishTime(getCustomDate(new Date()));// 前一天的23时59分59秒
                        v.setStatus(VmiGeneralStatus.NEW);
                        v.setErrorCount(0);
                        v.setVmiSource(bi.getVmiSource());
                        v.setUpc(sku.getExtensionCode2());
                        v.setQty(Long.parseLong(qty[0]));// 良品 占用库存总和
                        v.setInvStatus("良品");
                        vmiInventoryDao.save(v);
                        a = false;
                    }
                }

                if (b == true) {
                    if (Long.parseLong(qty[1]) != 0L) {
                        VmiInventoryDefault v = new VmiInventoryDefault();
                        v.setCreateTime(getCustomDate(new Date()));// 前一天的23时59分59秒
                        v.setStoreCode(bi.getVmiCode());
                        v.setVersion(1);
                        v.setFinishTime(getCustomDate(new Date()));// 前一天的23时59分59秒
                        v.setStatus(VmiGeneralStatus.NEW);
                        v.setErrorCount(0);
                        v.setVmiSource(bi.getVmiSource());
                        v.setUpc(sku.getExtensionCode2());
                        v.setQty(Long.parseLong(qty[1]));// 良品 占用库存总和
                        v.setInvStatus("残次品");
                        vmiInventoryDao.save(v);
                        b = false;
                    }
                }
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
