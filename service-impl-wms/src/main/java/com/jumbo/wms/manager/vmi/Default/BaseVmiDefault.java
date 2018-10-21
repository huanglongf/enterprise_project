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

import com.jumbo.dao.authorization.OperationUnitDao;
import com.jumbo.dao.baseinfo.SkuDao;
import com.jumbo.dao.vmi.defaultData.VmiAdjDao;
import com.jumbo.dao.vmi.defaultData.VmiAdjLineDao;
import com.jumbo.dao.vmi.defaultData.VmiAsnDao;
import com.jumbo.dao.vmi.defaultData.VmiAsnLineDao;
import com.jumbo.dao.vmi.defaultData.VmiCfgOrderCodeDao;
import com.jumbo.dao.vmi.defaultData.VmiRsnDao;
import com.jumbo.dao.vmi.defaultData.VmiRsnLineDao;
import com.jumbo.dao.vmi.defaultData.VmiRtwDao;
import com.jumbo.dao.vmi.defaultData.VmiRtwLineDao;
import com.jumbo.dao.vmi.defaultData.VmiTfxDao;
import com.jumbo.dao.vmi.defaultData.VmiTfxLineDao;
import com.jumbo.dao.vmi.nikeDate.NikeStockReceiveDao;
import com.jumbo.dao.warehouse.BiChannelDao;
import com.jumbo.dao.warehouse.CartonDao;
import com.jumbo.dao.warehouse.InventoryStatusDao;
import com.jumbo.dao.warehouse.StaLineDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.dao.warehouse.StockTransTxLogDao;
import com.jumbo.dao.warehouse.StockTransVoucherDao;
import com.jumbo.dao.warehouse.StvLineDao;
import com.jumbo.pac.manager.extsys.wms.rmi.model.OperationBillLine;
import com.jumbo.util.StringUtil;
import com.jumbo.wms.Constants;
import com.jumbo.wms.daemon.VmiDefaultFactory;
import com.jumbo.wms.daemon.VmiDefaultInterface;
import com.jumbo.wms.manager.system.SequenceManager;
import com.jumbo.wms.manager.vmi.ext.ExtParam;
import com.jumbo.wms.manager.vmi.ext.VmiExtFactory;
import com.jumbo.wms.manager.vmi.ext.VmiInterfaceExt;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.command.SkuCommand;
import com.jumbo.wms.model.vmi.Default.AsnOrderType;
import com.jumbo.wms.model.vmi.Default.RsnType;
import com.jumbo.wms.model.vmi.Default.VmiGeneralStatus;
import com.jumbo.wms.model.vmi.Default.VmiRsnDefault;
import com.jumbo.wms.model.vmi.Default.VmiRsnLineDefault;
import com.jumbo.wms.model.warehouse.BiChannelCommand;
import com.jumbo.wms.model.warehouse.InventoryCommand;
import com.jumbo.wms.model.warehouse.StaLine;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationStatus;
import com.jumbo.wms.model.warehouse.StockTransTxLogCommand;
import com.jumbo.wms.model.warehouse.StvLine;
import com.jumbo.wms.model.warehouse.StvLineCommand;

public class BaseVmiDefault implements VmiDefaultInterface {
    @Autowired
    private StockTransApplicationDao stockTransApplicationDao;
    @Autowired
    private NikeStockReceiveDao nikeStockReceiveDao;
    @Autowired
    private SkuDao skuDao;
    @Autowired
    private BiChannelDao biCannelDao;
    @Autowired
    private InventoryStatusDao inventoryStatusDao;
    @Autowired
    private CartonDao cartonDao;
    @Autowired
    private StockTransVoucherDao stvDao;
    @Autowired
    private StaLineDao staLineDao;
    @Autowired
    private VmiAsnDao vmiAsnDao;
    @Autowired
    private VmiAsnLineDao vmiAsnLineDao;
    @Autowired
    private VmiRsnDao vmiRsnDao;
    @Autowired
    private VmiRsnLineDao vmiRsnLineDao;
    @Autowired
    private StockTransTxLogDao stvLogDao;
    @Autowired
    private VmiTfxDao vmiTfxDao;
    @Autowired
    private VmiTfxLineDao vmiTfxLineDao;
    @Autowired
    private VmiRtwDao vmiRtwDao;
    @Autowired
    private VmiRtwLineDao vmiRtwLineDao;
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private VmiCfgOrderCodeDao vmiCfgOrderCodeDao;
    @Autowired
    private SequenceManager sequenceManager;
    @Autowired
    private StvLineDao stvLineDao;
    @Autowired
    private OperationUnitDao operationUnitDao;
    @Autowired
    private VmiAdjDao vmiAdjDao;
    @Autowired
    private VmiAdjLineDao vmiAdjLineDao;
    @Autowired
    private VmiExtFactory vmiExtFactory;
    @Autowired
    private VmiDefaultFactory vmiDefaultFactory;

    @Override
    public void generateInboundStaSetHeadDefault(String slipCode, StockTransApplication head) {}

    @Override
    public void generateInboundStaSetSlipCodeOwner(BiChannelCommand shop, StockTransApplication sta) {}

    @Override
    public void generateReceivingWhenShelv(ExtParam ext, VmiRsnLineDefault v) {}

    @Override
    public void generateReceivingWhenClose(Long staid) {
        // System.out.println(staid);
        StockTransApplication sta = staDao.getByPrimaryKey(staid);
        BiChannelCommand bi = biCannelDao.findVmiDefaultTbiChannelByOwen(sta.getOwner(), new BeanPropertyRowMapper<BiChannelCommand>(BiChannelCommand.class));
        if (bi.getIsPda() != null && bi.getIsPda()) {// ispda 为true
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
            vrd.setStaId(sta);
            boolean vmiCheck = false;
            VmiDefaultInterface vv = null;
            if (!StringUtil.isEmpty(bi.getDefaultCode())) {
                // 判断是否有品牌通用定制逻辑
                vv = vmiDefaultFactory.getVmiDefaultInterface(bi.getDefaultCode());
                vmiCheck = true;
                    }
            VmiInterfaceExt vmiBrandExt = null;
            if (null != bi.getIsVmiExt() && true == bi.getIsVmiExt()) {
                vmiBrandExt = vmiExtFactory.getBrandVmi(bi.getVmiCode());// 品牌逻辑定制
                    }
            Map<Long, Long> detials = new HashMap<Long, Long>();
            if (bi.getAsnTypeString().equals(String.valueOf(AsnOrderType.TYPETWO.getValue()))) {
                vrd.setOrderCode(sta.getSlipCode1());
                vrd.setCartonNo(sta.getRefSlipCode());
                vrd.setExtMemo(sta.getCode());
                if (null != vmiBrandExt) {
                    ExtParam extParam = new ExtParam();
                    if (Constants.PUMA_VMI_CODE.equals(bi.getVmiCode())) {// 不同的品牌根据业务可传递不同的参数
                        extParam.setSta(sta);
                        extParam.setAsnOrderType(bi.getAsnTypeString());
                    }
                    vmiBrandExt.generateReceivingWhenShelvRsnAspect(vrd, extParam);
                }
                // 按箱收货////////////////////////////////////////////////////////////////////////////////////
                if (bi.getRsnTypeString().equals(String.valueOf(RsnType.TYPETWO.getValue()))) {
                    // 按实际收货反馈=如果收货数量为100,这次收5件,那反馈5件 t_wh_st_log.quantity
                    if (StockTransApplicationStatus.FINISHED == sta.getStatus()) {// 完成才会一次反馈
                        vmiRsnDao.save(vrd);
                    } else {
                        return;
                    }
                    // List<StockTransTxLogCommand> logList =
                    // stvLogDao.findStaLogByStvId(stv.getId(), new
                    // BeanPropertyRowMapper<StockTransTxLogCommand>(StockTransTxLogCommand.class));
                    List<StockTransTxLogCommand> logList = stvLogDao.findStaLogByStvId2(sta.getId(), new BeanPropertyRowMapper<StockTransTxLogCommand>(StockTransTxLogCommand.class));
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
                        VmiRsnLineDefault v = new VmiRsnLineDefault();
                        v.setQty(line.getValue());
                        v.setUpc(sku.getExtensionCode2());
                        v.setCartonNo(sta.getRefSlipCode());
                        v.setRsnId(vrd);
                        if (null != vmiBrandExt) {
                            ExtParam extParam = new ExtParam();
                            if (Constants.PUMA_VMI_CODE.equals(bi.getVmiCode())) {// 不同的品牌根据业务可传递不同的参数
                                SkuCommand skuCmd = new SkuCommand();
                                skuCmd.setExtensionCode2(sku.getExtensionCode2());
                                extParam.setSkuCmd(skuCmd);
                                extParam.setStaLineList(staLineDao.findByStaId(sta.getId()));
                                extParam.setRsnType(bi.getRsnTypeString());
                                extParam.setSta(sta);// 新增
                                extParam.setAsnOrderType(bi.getAsnTypeString());
                            } else if (Constants.SPEEDO_VMI_CODE.equals(bi.getVmiCode())) {// SPEEDO品牌根据业务可传递不同的参数
                                SkuCommand skuCmd = new SkuCommand();
                                skuCmd.setExtensionCode2(sku.getExtensionCode2());
                                skuCmd.setId(sku.getId());// 设置skuId
                                extParam.setSkuCmd(skuCmd);
                                extParam.setStaLineList(staLineDao.findByStaId(sta.getId()));
                                extParam.setRsnType(bi.getRsnTypeString());
                                extParam.setSta(sta);// 新增
                                extParam.setAsnOrderType(bi.getAsnTypeString());
                            }
                            vmiBrandExt.generateReceivingWhenShelvRsnLineAspect(v, extParam);
                        }
                        if (vmiCheck) {
                            ExtParam ext = new ExtParam();
                            ext.setBi(bi);
                            ext.setSta(sta);
                            ext.setSku(sku);
                            vv.generateReceivingWhenShelv(ext, v);
                        }
                        vmiRsnLineDao.save(v);
                    }
                        }
                    }
        }

    }

    @Override
    public void generateSaveSkuBatch(String batchCode, StockTransApplication sta, List<StvLine> stvlineList) {}

    @Override
    public void transferOmsOutBound(OperationBillLine line, StvLineCommand sc) {}

    @Override
    public void generateVmiInboundStaLine(StockTransApplication sta, StaLine line, String upc) throws Exception {}

    @Override
    public void generateInsertVmiInventory(ExtParam ext) {}

    @Override
    public boolean importForBatchReceiving() {
        return false;
    }

    @Override
    public void importForBatchReceivingSaveStvLine(StvLine stvLine, StockTransApplication sta, Sku sku) {}

    @Override
    public void generateVmiOutBound(ExtParam ext) {}

    @Override
    public void generateVmiInBound(ExtParam ext) {}

    @Override
    public List<InventoryCommand> findVmiInventoryByOwner(List<String> ownerList) {
        return null;
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
