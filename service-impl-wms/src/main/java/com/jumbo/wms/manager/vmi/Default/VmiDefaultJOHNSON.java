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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;

import com.jumbo.dao.authorization.OperationUnitDao;
import com.jumbo.dao.baseinfo.SkuDao;
import com.jumbo.dao.vmi.defaultData.VmiAsnLineDao;
import com.jumbo.dao.vmi.defaultData.VmiInventoryDao;
import com.jumbo.dao.vmi.defaultData.VmiRsnDao;
import com.jumbo.dao.vmi.defaultData.VmiRsnLineDao;
import com.jumbo.dao.warehouse.BiChannelDao;
import com.jumbo.dao.warehouse.InventoryDao;
import com.jumbo.dao.warehouse.InventoryStatusDao;
import com.jumbo.dao.warehouse.SkuBatchDao;
import com.jumbo.dao.warehouse.StaLineDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.pac.manager.extsys.wms.rmi.model.OperationBillLine;
import com.jumbo.util.StringUtil;
import com.jumbo.wms.manager.vmi.ext.ExtParam;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.vmi.Default.AsnOrderType;
import com.jumbo.wms.model.vmi.Default.RsnType;
import com.jumbo.wms.model.vmi.Default.VmiAsnLineCommand;
import com.jumbo.wms.model.vmi.Default.VmiGeneralStatus;
import com.jumbo.wms.model.vmi.Default.VmiInventoryDefault;
import com.jumbo.wms.model.vmi.Default.VmiRsnDefault;
import com.jumbo.wms.model.vmi.Default.VmiRsnLineDefault;
import com.jumbo.wms.model.warehouse.BiChannelCommand;
import com.jumbo.wms.model.warehouse.InventoryCommand;
import com.jumbo.wms.model.warehouse.InventoryStatus;
import com.jumbo.wms.model.warehouse.SkuBatch;
import com.jumbo.wms.model.warehouse.StaLine;
import com.jumbo.wms.model.warehouse.StaLineCommand;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StvLine;
import com.jumbo.wms.model.warehouse.StvLineCommand;

/**
 * 强生品牌定制逻辑
 * 
 * @author jumbo
 * 
 */
@Service("vmiDefaultJohnson")
public class VmiDefaultJOHNSON extends BaseVmiDefault {

    @Autowired
    private BiChannelDao companyShopDao;
    @Autowired
    private StaLineDao staLineDao;
    @Autowired
    private StockTransApplicationDao stockTransApplicationDao;
    @Autowired
    private BiChannelDao biCannelDao;
    @Autowired
    private SkuDao skuDao;
    @Autowired
    private VmiRsnDao vmiRsnDao;
    @Autowired
    private VmiRsnLineDao vmiRsnLineDao;
    @Autowired
    private SkuBatchDao skuBatchDao;
    @Autowired
    private VmiAsnLineDao vmiAsnLineDao;
    @Autowired
    private InventoryDao inventoryDao;
    @Autowired
    private OperationUnitDao operationUnitDao;
    @Autowired
    private InventoryStatusDao inventoryStatusDao;
    @Autowired
    private VmiInventoryDao vmiInventoryDao;

    /**
     * 收货创STA格式化头信息
     */
    @Override
    public void generateInboundStaSetHeadDefault(String slipCode, StockTransApplication head) {
        String[] code = slipCode.split(",");
        String cartonNo = code[0];
        String orderCode = code[1];
        head.setSlipCode1(orderCode);
        head.setSlipCode2(cartonNo);
    }

    /**
     * 品牌定制slipCode 库存指定渠道
     */
    @Override
    public void generateInboundStaSetSlipCodeOwner(BiChannelCommand shop, StockTransApplication sta) {
        BiChannelCommand s = companyShopDao.findVmiDefaultTbiChannel(shop.getVmiCode(), shop.getVmiSource(), 1L, new BeanPropertyRowMapper<BiChannelCommand>(BiChannelCommand.class));
        sta.setRefSlipCode(sta.getSlipCode2() + "_" + sta.getSlipCode1());
        sta.setOwner(s.getCode());
    }

    /**
     * 收货反馈判断商品是否全部收货完成
     */
    @Override
    public void generateReceivingWhenShelv(ExtParam ext, VmiRsnLineDefault v) {
        StockTransApplication sta = ext.getSta();
        Sku sku = ext.getSku();
        BiChannelCommand bi = ext.getBi();
        // 强生品牌收货反馈是按箱按实际收货反馈
        if (bi.getAsnTypeString().equals(String.valueOf(AsnOrderType.TYPETWO.getValue())) && bi.getRsnTypeString().equals(String.valueOf(RsnType.TYPETWO.getValue()))) {
            StaLineCommand q = staLineDao.findTotalQtyByStaIdAndSkuId(sta.getId(), sku.getId(), new BeanPropertyRowMapper<StaLineCommand>(StaLineCommand.class));
            v.setCartonNo(sta.getSlipCode2());
            if (q.getQuantity().longValue() == q.getCompleteQuantity().longValue()) {
                v.setExtMemo("Y");
            } else {
                v.setExtMemo("N");
            }
        }
    }

    /**
     * 收货单关闭反馈收货信息
     */
    @Override
    public void generateReceivingWhenClose(Long staid) {
        StockTransApplication sta = stockTransApplicationDao.getByPrimaryKey(staid);
        BiChannelCommand bi = biCannelDao.findVmiDefaultTbiChannelByOwen(sta.getOwner(), new BeanPropertyRowMapper<BiChannelCommand>(BiChannelCommand.class));
        VmiRsnDefault vrd = new VmiRsnDefault();
        vrd.setStoreCode(bi.getVmiCode());
        vrd.setCreateTime(new Date());
        vrd.setFinishTime(new Date());
        vrd.setFromLocation(sta.getFromLocation());
        vrd.setToLoaction(sta.getToLocation());
        vrd.setReceiveDate(getFormateDateToData("yyyyMMddHHmmss", new Date()));
        vrd.setStatus(VmiGeneralStatus.NEW);
        vrd.setVersion(1);
        vrd.setVmiSource(bi.getVmiSource());
        vrd.setWhCode(sta.getMainWarehouse().getCode());
        vrd.setOrderCode(sta.getSlipCode1());
        vrd.setCartonNo(sta.getRefSlipCode());
        vrd.setExtMemo(sta.getCode());
        vrd.setStaId(sta);
        vmiRsnDao.save(vrd);
        List<StaLineCommand> staList = staLineDao.findLineListByStaId(sta.getId(), null, new BeanPropertyRowMapper<StaLineCommand>(StaLineCommand.class));
        for (StaLineCommand s : staList) {
            Sku sku = skuDao.getByPrimaryKey(s.getSkuId());
            StaLineCommand q = staLineDao.findTotalQtyByStaIdAndSkuId(staid, sku.getId(), new BeanPropertyRowMapper<StaLineCommand>(StaLineCommand.class));
            // 如果上架数量不等于收货指令数量反馈信息
            if (q.getQuantity().longValue() != q.getCompleteQuantity().longValue()) {
                VmiRsnLineDefault v = new VmiRsnLineDefault();
                v.setQty(0L);
                v.setUpc(sku.getExtensionCode2());
                v.setCartonNo(sta.getSlipCode2());
                v.setRsnId(vrd);
                v.setExtMemo("Y");// 此SKU此批次是否收货完成
                vmiRsnLineDao.save(v);
            }
        }
    }

    /**
     * 商品上架后通过库存批次插入t_wh_inv_sku_batch表
     */
    @Override
    public void generateSaveSkuBatch(String batchCode, StockTransApplication sta, List<StvLine> stvlineList) {
        Map<String, String> detials = new HashMap<String, String>();
        // 合并条目
        for (StvLine line : stvlineList) {
            Long skuid = line.getSku().getId();
            String val = detials.get(line.getBatchCode() + "_" + skuid);
            if (val == null) {
                detials.put(line.getBatchCode() + "_" + skuid, sta.getSlipCode2());
            }
        }
        for (Entry<String, String> line : detials.entrySet()) {
            String batch = line.getKey().split("_")[0];// batch
            Long skuid = Long.parseLong(line.getKey().split("_")[1]);// skuid
            // 验证库存批次+SKU+产品生产批次是否存在
            SkuBatch skuBatch = skuBatchDao.findSkuBatchByInvBatchCodeAndSkuIdAndBatchNo(batch, skuid, sta.getSlipCode2(), new BeanPropertyRowMapper<SkuBatch>(SkuBatch.class));
            if (skuBatch == null) {
                SkuBatch s = new SkuBatch();
                s.setCreateTime(new Date());
                s.setInvBatchCode(batch);
                s.setVersion(0);
                s.setSkuBatchNo(sta.getSlipCode2());
                s.setSkuid(skuid);
                skuBatchDao.save(s);
            }
        }
    }

    /**
     * 销售出库同步数据给OMS
     */
    @Override
    public void transferOmsOutBound(OperationBillLine line, StvLineCommand sc) {
        SkuBatch b = skuBatchDao.findSkuBatchByInvBatchCodeAndSkuId(sc.getBatchCode(), sc.getSkuId(), new BeanPropertyRowMapper<SkuBatch>(SkuBatch.class));
        if (b != null) {
            line.setProductLot(b.getSkuBatchNo());
        }
    }

    /**
     * 获取过期时间插入staline.ExpireDate
     */
    @Override
    public void generateVmiInboundStaLine(StockTransApplication sta, StaLine line, String upc) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        VmiAsnLineCommand v = vmiAsnLineDao.findVmiAsnLineByOrdercodeCartonnoUpc(sta.getSlipCode1(), sta.getSlipCode2(), upc, new BeanPropertyRowMapper<VmiAsnLineCommand>(VmiAsnLineCommand.class));
        if (v != null) {
            if (!StringUtil.isEmpty(v.getExtMemo())) {
                line.setExpireDate(sdf.parse(v.getExtMemo()));
            }
        }
    }


    /**
     * 强生品牌定制库存快照对应数据
     */
    @Override
    public void generateInsertVmiInventory(ExtParam ext) {
        Sku sku = ext.getSku();
        InventoryCommand i = ext.getInventoryCommand();
        BiChannelCommand bi = ext.getBi();
        OperationUnit ou = operationUnitDao.getByPrimaryKey(Long.parseLong(i.getWhOuId()));
        InventoryStatus is = inventoryStatusDao.getByPrimaryKey(i.getInventoryStatusId());
        String q = inventoryDao.findVmiInventoryQtyAndBlockQty(i.getBatchCode(), sku.getId(), i.getInvOwner(), i.getInventoryStatusId(), new SingleColumnRowMapper<String>(String.class));
        String[] qty = q.split(",");
        VmiInventoryDefault v = new VmiInventoryDefault();
        v.setCreateTime(new Date());
        v.setStoreCode(bi.getVmiCode());
        v.setVersion(1);
        v.setFinishTime(new Date());
        v.setStatus(VmiGeneralStatus.NEW);
        v.setErrorCount(0);
        v.setVmiSource(bi.getVmiSource());
        v.setUpc(sku.getExtensionCode2());
        v.setWhCode(ou.getCode());
        v.setQty((Long.parseLong(qty[0]) + Long.parseLong(qty[1])));
        v.setBlockQty(Long.parseLong(qty[1]));
        v.setInvStatus(is.getName());
        v.setBatchNo(i.getBatchCode());
        SkuBatch b = skuBatchDao.findSkuBatchByInvBatchCodeAndSkuId(i.getBatchCode(), sku.getId(), new BeanPropertyRowMapper<SkuBatch>(SkuBatch.class));
        if (b != null) {
            v.setBatchNo(b.getSkuBatchNo());
        }
        vmiInventoryDao.save(v);
    }

    @Override
    public boolean importForBatchReceiving() {
        return true;
    }

    /**
     * 模板批量收货品牌定制 设置过期时间到stvline importForBatchReceiving()方法返回为true时需要配置
     */
    @Override
    public void importForBatchReceivingSaveStvLine(StvLine stvLine, StockTransApplication sta, Sku sku) {
        StaLine staLine = staLineDao.findStaLineBySkuId(sku.getId(), sta.getId());
        stvLine.setValidDate(sku.getValidDate());
        stvLine.setExpireDate(staLine.getExpireDate());
    }

    /**
     * VMI出库反馈信息
     */
    @Override
    public void generateVmiOutBound(ExtParam ext) {}

    /**
     * VMI退换货入反馈信息
     */
    @Override
    public void generateVmiInBound(ExtParam ext) {}

    /**
     * 品牌定制库存快照数据
     */
    @Override
    public List<InventoryCommand> findVmiInventoryByOwner(List<String> ownerList) {
        return inventoryDao.findVmiInventoryByOwnerToJNJ(ownerList, new BeanPropertyRowMapper<InventoryCommand>(InventoryCommand.class));
    }

    /**
     * 格式化时间
     */
    private String getFormateDateToData(String s, Date d) {
        SimpleDateFormat sdf = new SimpleDateFormat(s);
        String date = sdf.format(d);
        return date;
    }
}
