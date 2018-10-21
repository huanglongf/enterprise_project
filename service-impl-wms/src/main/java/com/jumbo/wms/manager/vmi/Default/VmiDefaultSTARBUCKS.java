/**
 * EventObserver * Copyright (c) 2010 Jumbomart All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of Jumbomart. You shall not
 * disclose such Confidential Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with Jumbo.
 * 
 * JUMBOMART MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF THE SOFTWARE, EITHER
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE, OR NON-INFRINGEMENT. JUMBOMART SHALL NOT BE LIABLE FOR ANY
 * DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES.
 * 
 */
package com.jumbo.wms.manager.vmi.Default;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;

import com.jumbo.dao.baseinfo.SkuDao;
import com.jumbo.dao.vmi.defaultData.VmiInBoundDao;
import com.jumbo.dao.vmi.defaultData.VmiInBoundLineDao;
import com.jumbo.dao.vmi.defaultData.VmiInventoryDao;
import com.jumbo.dao.vmi.defaultData.VmiOutBoundDao;
import com.jumbo.dao.vmi.defaultData.VmiOutBoundLineDao;
import com.jumbo.dao.warehouse.InventoryDao;
import com.jumbo.dao.warehouse.StockTransTxLogDao;
import com.jumbo.pac.manager.extsys.wms.rmi.model.OperationBillLine;
import com.jumbo.wms.manager.vmi.ext.ExtParam;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.vmi.Default.VmiGeneralStatus;
import com.jumbo.wms.model.vmi.Default.VmiInBoundDefault;
import com.jumbo.wms.model.vmi.Default.VmiInBoundLineDefault;
import com.jumbo.wms.model.vmi.Default.VmiInventoryDefault;
import com.jumbo.wms.model.vmi.Default.VmiOutBoundDefault;
import com.jumbo.wms.model.vmi.Default.VmiOutBoundLineDefault;
import com.jumbo.wms.model.vmi.Default.VmiRsnLineDefault;
import com.jumbo.wms.model.warehouse.BiChannelCommand;
import com.jumbo.wms.model.warehouse.InventoryCommand;
import com.jumbo.wms.model.warehouse.StaLine;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransTxLogCommand;
import com.jumbo.wms.model.warehouse.StockTransVoucher;
import com.jumbo.wms.model.warehouse.StvLine;
import com.jumbo.wms.model.warehouse.StvLineCommand;

/**
 * 星巴克品牌定制逻辑
 * 
 * @author jumbo
 * 
 */
@Service("vmiDefaultStarbucks")
public class VmiDefaultSTARBUCKS extends BaseVmiDefault {

    @Autowired
    private VmiOutBoundDao vmiOutBoundDao;
    @Autowired
    private VmiOutBoundLineDao vmiOutBoundLineDao;
    @Autowired
    private SkuDao skuDao;
    @Autowired
    private VmiInBoundDao vmiInBoundDao;
    @Autowired
    private VmiInBoundLineDao vmiInBoundLineDao;
    @Autowired
    private InventoryDao inventoryDao;
    @Autowired
    private VmiInventoryDao vmiInventoryDao;
    @Autowired
    private StockTransTxLogDao stvLogDao;


    @Override
    public void generateInboundStaSetHeadDefault(String slipCode, StockTransApplication head) {}

    @Override
    public void generateInboundStaSetSlipCodeOwner(BiChannelCommand shop, StockTransApplication sta) {}

    @Override
    public void generateReceivingWhenShelv(ExtParam ext, VmiRsnLineDefault v) {}

    // @Override
    // public void generateReceivingWhenClose(Long staid) {}

    @Override
    public void generateSaveSkuBatch(String batchCode, StockTransApplication sta, List<StvLine> stvlineList) {}

    @Override
    public void transferOmsOutBound(OperationBillLine line, StvLineCommand sc) {}

    @Override
    public void generateVmiInboundStaLine(StockTransApplication sta, StaLine line, String upc) throws Exception {}

    /**
     * 星巴克品牌定制对应库存快照数据
     */
    @Override
    public void generateInsertVmiInventory(ExtParam ext) {
        Sku sku = ext.getSku();
        if (!sku.getIsGift()) {
            // 非库存商品不做反馈
            InventoryCommand i = ext.getInventoryCommand();
            BiChannelCommand bi = ext.getBi();
            String q = inventoryDao.findVmiInventoryQtyAndBlockQtyAndOnHoldQty(sku.getId(), i.getInvOwner(), new SingleColumnRowMapper<String>(String.class));
            String[] qty = q.split(",");
            VmiInventoryDefault v = new VmiInventoryDefault();
            v.setCreateTime(getCustomDate(new Date()));// 前一天的23时59分59秒
            v.setStoreCode(bi.getVmiCode());
            v.setVersion(1);
            v.setFinishTime(getCustomDate(new Date()));// 前一天的23时59分59秒
            v.setStatus(VmiGeneralStatus.NEW);
            v.setErrorCount(0);
            v.setVmiSource(bi.getVmiSource());
            v.setUpc(sku.getExtensionCode2());
            v.setQty((Long.parseLong(qty[0])));
            v.setBlockQty(Long.parseLong(qty[1]));
            v.setOnHoldQty(Long.parseLong(qty[2]));
            vmiInventoryDao.save(v);
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

    @Override
    public boolean importForBatchReceiving() {
        return false;
    }

    @Override
    public void importForBatchReceivingSaveStvLine(StvLine stvLine, StockTransApplication sta, Sku sku) {}

    /**
     * VMI出库反馈信息
     */
    @Override
    public void generateVmiOutBound(ExtParam ext) {
        BiChannel biChannel = ext.getBiChannel();
        StockTransApplication sta = ext.getSta();
        List<StvLineCommand> stvLine = ext.getStvLineList();
        List<VmiOutBoundDefault> list = vmiOutBoundDao.findVmiOutBoundByStaId(sta.getId(), new BeanPropertyRowMapper<VmiOutBoundDefault>(VmiOutBoundDefault.class));
        if (null != list && list.size() > 0) {

        } else {
            VmiOutBoundDefault ob = new VmiOutBoundDefault();
            ob.setCreateTime(new Date());
            ob.setOrderCode(sta.getRefSlipCode());
            ob.setVersion(1);
            ob.setFinishTime(new Date());
            ob.setStatus(VmiGeneralStatus.NEW);
            ob.setOrderTime(sta.getOutboundTime());
            ob.setOutBoundTime(sta.getOutboundTime());
            ob.setStoreCode(biChannel.getVmiCode());
            ob.setStaId(sta);
            vmiOutBoundDao.save(ob);
            for (StvLineCommand stv : stvLine) {
                Sku sku = skuDao.getByPrimaryKey(stv.getSkuId());
                if (!sku.getIsGift()) {
                    // 是库存商品才需要反馈
                    VmiOutBoundLineDefault obLine = new VmiOutBoundLineDefault();
                    obLine.setObid(ob);
                    obLine.setVersion(1);
                    obLine.setQty(stv.getQuantity());
                    obLine.setUpc(sku.getExtensionCode2());
                    vmiOutBoundLineDao.save(obLine);
                }
            }
        }
    }

    /**
     * VMI退换货入反馈信息
     */
    @Override
    public void generateVmiInBound(ExtParam ext) {
        StockTransApplication sta = ext.getSta();
        StockTransVoucher stv = ext.getStv();
        BiChannel biChannel = ext.getBiChannel();
        Map<Long, Long> detials = new HashMap<Long, Long>();
        VmiInBoundDefault ib = new VmiInBoundDefault();
        ib.setCreateTime(new Date());
        ib.setOrderCode(sta.getRefSlipCode());
        ib.setVersion(1);
        ib.setFinishTime(new Date());
        ib.setStatus(VmiGeneralStatus.NEW);
        ib.setOrderTime(sta.getInboundTime());
        ib.setInBoundTime(sta.getInboundTime());
        ib.setStoreCode(biChannel.getVmiCode());
        ib.setStaId(sta);
        vmiInBoundDao.save(ib);
        List<StockTransTxLogCommand> logList = stvLogDao.findStaLogByStvId(stv.getId(), new BeanPropertyRowMapper<StockTransTxLogCommand>(StockTransTxLogCommand.class));
        for (StockTransTxLogCommand log : logList) {
            // 合并相同商品数量
            Long val = detials.get(Long.parseLong(log.getSkuCode()));
            if (val == null) {
                detials.put(Long.parseLong(log.getSkuCode()), log.getInQty());
            } else {
                detials.put(Long.parseLong(log.getSkuCode()), val + log.getInQty());
            }
        }
        for (Entry<Long, Long> line : detials.entrySet()) {
            Sku sku = skuDao.getByPrimaryKey(line.getKey());
            if (!sku.getIsGift()) {
                // 是库存商品才需要反馈
                VmiInBoundLineDefault ibLine = new VmiInBoundLineDefault();
                ibLine.setIbid(ib);
                ibLine.setVersion(1);
                ibLine.setQty(line.getValue());
                ibLine.setUpc(sku.getExtensionCode2());
                vmiInBoundLineDao.save(ibLine);
            }
        }
    }

    @Override
    public List<InventoryCommand> findVmiInventoryByOwner(List<String> ownerList) {
        return inventoryDao.findVmiInventoryByOwner(ownerList, new BeanPropertyRowMapper<InventoryCommand>(InventoryCommand.class));
    }
}
