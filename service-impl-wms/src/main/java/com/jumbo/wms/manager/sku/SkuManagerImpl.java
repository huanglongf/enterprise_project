/**
 * \ * EventObserver * Copyright (c) 2010 Jumbomart All Rights Reserved.
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
package com.jumbo.wms.manager.sku;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import loxia.dao.Pagination;
import loxia.dao.Sort;
import loxia.dao.support.BeanPropertyRowMapperExt;
import loxia.utils.PropertyUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.jumbo.dao.baseinfo.BrandDao;
import com.jumbo.dao.baseinfo.CustomerDao;
import com.jumbo.dao.baseinfo.MsgOmsSkuLogDao;
import com.jumbo.dao.baseinfo.SkuBarcodeDao;
import com.jumbo.dao.baseinfo.SkuDao;
import com.jumbo.dao.baseinfo.SkuModifyLogDao;
import com.jumbo.dao.baseinfo.WarehouseDao;
import com.jumbo.dao.hub2wms.CnSkuInfoDao;
import com.jumbo.dao.system.ChooseOptionDao;
import com.jumbo.dao.vmi.warehouse.IdsInboundSkuDao;
import com.jumbo.dao.vmi.warehouse.MsgSKUSyncDao;
import com.jumbo.dao.warehouse.BiChannelDao;
import com.jumbo.dao.warehouse.InventoryDao;
import com.jumbo.dao.warehouse.SkuWarehouseRefDao;
import com.jumbo.dao.warehouse.WarehouseMsgSkuBarcodeDao;
import com.jumbo.dao.warehouse.WarehouseMsgSkuDao;
import com.jumbo.rmi.warehouse.RmiSku;
import com.jumbo.rmi.warehouse.RmiSkuType;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.hub2wms.HubWmsManager;
import com.jumbo.wms.model.DefaultStatus;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.baseinfo.Brand;
import com.jumbo.wms.model.baseinfo.Customer;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.baseinfo.SkuBarcode;
import com.jumbo.wms.model.baseinfo.SkuInterfaceType;
import com.jumbo.wms.model.baseinfo.SkuModifyLog;
import com.jumbo.wms.model.baseinfo.SkuSalesModel;
import com.jumbo.wms.model.baseinfo.SkuSnCheckMode;
import com.jumbo.wms.model.baseinfo.SkuSnType;
import com.jumbo.wms.model.baseinfo.SkuSpType;
import com.jumbo.wms.model.baseinfo.SkuWarrantyCardType;
import com.jumbo.wms.model.baseinfo.Warehouse;
import com.jumbo.wms.model.command.SkuCommand;
import com.jumbo.wms.model.command.SkuWarehouseRefCommand;
import com.jumbo.wms.model.hub2wms.threepl.CnSkuInfo;
import com.jumbo.wms.model.system.ChooseOption;
import com.jumbo.wms.model.vmi.warehouse.MsgSKUSync;
import com.jumbo.wms.model.warehouse.IdsInboundSku;
import com.jumbo.wms.model.warehouse.InboundStoreMode;
import com.jumbo.wms.model.warehouse.InventoryCommand;
import com.jumbo.wms.model.warehouse.TimeTypeMode;
import com.jumbo.wms.model.warehouse.TransDeliveryType;
import com.jumbo.wms.model.warehouse.WarehouseMsgSku;
import com.jumbo.wms.model.warehouse.WarehouseMsgSkuBarcode;

@Transactional
@Service("skuManager")
public class SkuManagerImpl extends BaseManagerImpl implements SkuManager {

    private static final long serialVersionUID = -1643048273661042499L;

    /**
     * 外包仓SKU同步常量表Code
     */
    // private static String categoryCode = "MsgSkuSync";
    @Autowired
    private MsgOmsSkuLogDao msgOmsSkuLogDao;
    @Autowired
    private SkuDao skuDao;
    @Autowired
    private SkuBarcodeDao skuBarcodeDao;
    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private BrandDao brandDao;

    @Autowired
    private SkuModifyLogDao skuModifyLogDao;
    @Autowired
    private WarehouseMsgSkuDao warehouseMsgSkuDao;
    @Autowired
    private WarehouseMsgSkuBarcodeDao warehouseMsgSkuBarcodeDao;
    @Autowired
    private SkuWarehouseRefDao skuWarehouseRef;
    @Autowired
    private WarehouseDao warehouseDao;
    @Autowired
    private InventoryDao inventoryDao;
    @Autowired
    private CnSkuInfoDao cnSkuInfoDao;

    @Autowired
    private ChooseOptionDao chooseOptionDao;
    @Autowired
    private MsgSKUSyncDao msgSKUSyncDao;
    @Autowired
    private IdsInboundSkuDao idsInboundSkuDao;
    @Autowired
    private BiChannelDao bichannelDao;
    @Autowired
    private HubWmsManager hubWmsManager;

    /**
     * RMI接口更新创建SKU
     * 
     * @param rmiSku
     */
    public void rmiUpdateSku(RmiSku rmiSku) {
        SkuModifyLog smf = null;
        if (rmiSku == null) {
            throw new BusinessException(ErrorCode.CUSTOMER_NOT_FOUND, new Object[] {""});
        }
        Customer c = customerDao.getByCode(rmiSku.getCustomerCode());
        if (c == null) {
            throw new BusinessException(ErrorCode.CUSTOMER_NOT_FOUND, new Object[] {rmiSku.getCustomerCode()});
        }
        Sku sku = skuDao.getByCustomerSkuCode(rmiSku.getCustomerSkuCode(), c.getId());
        boolean isUpdate = false;
        boolean isCnDb = false;
        if (sku == null) {
            sku = new Sku();
            isUpdate = false;
        } else {
            isUpdate = true;
            CnSkuInfo csi = cnSkuInfoDao.getCnSkuInfoBySkuCode(sku.getCode());
            if (csi != null) {
                isCnDb = true;
            }
        }
        Brand brand = brandDao.getByCode(rmiSku.getBrandCode());
        if (brand == null) {
            brand = new Brand();
            brand.setCode(rmiSku.getBrandCode());
            brand.setCreateTime(new Date());
            brand.setName(rmiSku.getBrandName());
            brand.setIsAvailable(true);
            brandDao.save(brand);
        }
        // 创建SKU
        // sku.setId(rmiSku.getId());
        sku.setBarCode(rmiSku.getBarCode());
        if (sku.getBoxQty() == null) {
            sku.setBoxQty(rmiSku.getBoxQty());
        }
        sku.setBrand(brand);
        sku.setListPrice(rmiSku.getListPrice());
        sku.setCode(rmiSku.getCustomerSkuCode());
        sku.setColor(rmiSku.getColor());
        sku.setColorName(rmiSku.getColorName());
        sku.setCustomer(c);
        sku.setCustomerSkuCode(rmiSku.getCustomerSkuCode());
        sku.setChildSnQty(rmiSku.getChildSnQty());
        sku.setEnName(rmiSku.getEnName());
        sku.setChildSnQty(rmiSku.getChildSnQty());
        sku.setExtensionCode1(rmiSku.getExtensionCode1());
        sku.setExtensionCode2(rmiSku.getExtensionCode2());
        if (null != sku.getExtensionCode2()) {
            sku.setExtCode2AndCustomer(c.getId() + "_" + sku.getExtensionCode2());
        }
        sku.setExtensionCode3(rmiSku.getExtensionCode3());
        sku.setStoremode(rmiSku.getStroeMode() == null ? InboundStoreMode.TOGETHER : InboundStoreMode.valueOf(rmiSku.getStroeMode()));
        if (rmiSku.getValidDate() != null) {
            sku.setValidDate(rmiSku.getValidDate());
        }
        if (rmiSku.getWarningDate() != null) {
            sku.setWarningDate(rmiSku.getWarningDate());
        }
        if (rmiSku.getWarningDateLv1() != null) {
            sku.setWarningDateLv1(rmiSku.getWarningDateLv1());
        }
        if (rmiSku.getWarningDateLv2() != null) {
            sku.setWarningDateLv2(rmiSku.getWarningDateLv2());
        }
        if ((false == isUpdate) || (true == isCnDb)) {
            if (rmiSku.getGrossWeight() != null) {
                sku.setGrossWeight(rmiSku.getGrossWeight());
            }
        }
        sku.setIsGift(rmiSku.getIsGift());
        if (sku.getDeliveryType() == null || TransDeliveryType.ORDINARY.equals(sku.getDeliveryType())) {
            sku.setDeliveryType(((rmiSku.getIsRailway() != null && rmiSku.getIsRailway()) ? TransDeliveryType.LAND : TransDeliveryType.ORDINARY));
        }
        if (rmiSku.getIsSnSku() != null) {
            sku.setIsSnSku(rmiSku.getIsSnSku());
        }
        sku.setCountryOfOrigin(rmiSku.getCountryOfOrigin());
        sku.setCategory(rmiSku.getCategory());
        sku.setJmCode(rmiSku.getJmCode());
        sku.setKeyProperties(rmiSku.getKeyProperties());
        sku.setName(rmiSku.getName());
        if (rmiSku.getSalesMode() == null) {
            sku.setSalesMode(SkuSalesModel.CONSIGNMENT);
        } else {
            sku.setSalesMode(SkuSalesModel.valueOf(rmiSku.getSalesMode()));
        }
        if (sku.getSkuSize() == null) {
            sku.setSkuSize(rmiSku.getSkuSize());
        }
        if (rmiSku.getStroeMode() == null) {
            if (sku.getStoremode() == null) {
                sku.setStoremode(InboundStoreMode.TOGETHER);
            }
        } else {
            sku.setStoremode(InboundStoreMode.valueOf(rmiSku.getStroeMode()));
        }
        sku.setSupplierCode(rmiSku.getSupplierCode());
        if (rmiSku.getWarrantyCardType() == null) {
            if (sku.getWarrantyCardType() == null) {
                sku.setWarrantyCardType(SkuWarrantyCardType.NO_NEED);
            }
        } else {
            sku.setWarrantyCardType(SkuWarrantyCardType.valueOf(rmiSku.getWarrantyCardType()));
        }
        if ((false == isUpdate) || (true == isCnDb)) {
            if (sku.getHeight() == null || isCnDb == true) {
                sku.setHeight(rmiSku.getHeight());
            }
            if (sku.getWidth() == null || isCnDb == true) {
                sku.setWidth(rmiSku.getWidth());
            }
            if (sku.getLength() == null || isCnDb == true) {
                sku.setLength(rmiSku.getLength());
            }
        }
        Integer i = rmiSku.getTimeType();
        if (i != null) {
            sku.setTimeType(TimeTypeMode.valueOf(i));
        }
        sku.setLastModifyTime(new Date());
        Integer skuType = rmiSku.getSkuType();
        RmiSkuType rmiSkuType = RmiSkuType.valueOf(skuType);
        if (RmiSkuType.COMMON.equals(rmiSkuType) || RmiSkuType.STARBUCKS_SVC_VIRTUAL.equals(rmiSkuType) || RmiSkuType.STARBUCKS_MSR_VIRTUAL.equals(rmiSkuType)) {
            sku.setInterfaceType(SkuInterfaceType.NUL);
            sku.setSnCheckMode(SkuSnCheckMode.GENERAL);
            sku.setSnType(SkuSnType.GENERAL);
        } else if (RmiSkuType.STARBUCKS_SVC.equals(rmiSkuType)) {// 星巴克SVC实体卡
            sku.setInterfaceType(SkuInterfaceType.STB_ZHX);
            sku.setSnCheckMode(SkuSnCheckMode.STB_SVC);
            sku.setSnType(SkuSnType.NO_BARCODE_SKU);
            sku.setIsSnSku(true);// sn商品
        } else if (RmiSkuType.STARBUCKS_SVC_T.equals(rmiSkuType)) {// 星巴克SVC实体券
            sku.setInterfaceType(SkuInterfaceType.STB_ZHX);
            sku.setSnCheckMode(SkuSnCheckMode.STB_SVCQ);
            sku.setSnType(SkuSnType.NO_BARCODE_SKU);
            sku.setIsSnSku(true);// sn商品
        } else if (RmiSkuType.STARBUCKS_MSR.equals(rmiSkuType)) {// 星巴克MSR实体卡
            sku.setInterfaceType(SkuInterfaceType.STB_HP);
            sku.setSnCheckMode(SkuSnCheckMode.STB_MSR);
            sku.setSnType(SkuSnType.NO_BARCODE_SKU);
            sku.setIsSnSku(true);// sn商品
        } else if (RmiSkuType.NIKE_MSG.equals(rmiSkuType)) {// NIKE礼品卡
            sku.setInterfaceType(SkuInterfaceType.NIKE);
            sku.setSnCheckMode(SkuSnCheckMode.NIKE);
            sku.setSnType(SkuSnType.NO_BARCODE_SKU);
            sku.setIsSnSku(true);// sn商品
        } else if (RmiSkuType.STARBUCKS_BEN.equals(rmiSkuType)) {// 星巴克按本 卡
            sku.setInterfaceType(SkuInterfaceType.STB_BEN);
            sku.setSnCheckMode(SkuSnCheckMode.STB_BEN);
            sku.setSnType(SkuSnType.NO_BARCODE_SKU);
            sku.setIsSnSku(true);// sn商品
        } else {// 其他
            sku.setInterfaceType(SkuInterfaceType.NUL);
            sku.setSnCheckMode(SkuSnCheckMode.GENERAL);
            sku.setSnType(SkuSnType.GENERAL);
        }
        sku.setSpType(SkuSpType.valueOf(skuType));// 平台商品类型
        if (rmiSku.getIsConsumable() != null && rmiSku.getIsConsumable()) {
            sku.setSpType(SkuSpType.CONSUMPTIVE_MATERIAL);
        }
        skuDao.save(sku);
        ChooseOption chooseOption = chooseOptionDao.findByCategoryCodeAndKey("isOpenTriggers", "isOpenTriggers");
        if ("0".equals(chooseOption.getOptionValue())) {
            // 触发器
            try {
                insertMsgSKUSync(sku, isUpdate);
            } catch (UnsupportedEncodingException e) {
                if (log.isErrorEnabled()) {
                    log.error("触发器 insertMsgSKUSync UnsupportedEncodingException", e);
                }
            }
        }

        smf = refreshSkuModifyLog(sku);
        if (smf != null) {
            skuModifyLogDao.save(smf);// 将新建或修改后的SKU保存进变更日志表
        }


        if (rmiSku.getAddBarcodes() != null && rmiSku.getAddBarcodes().size() > 0) {
            List<SkuBarcode> list = skuBarcodeDao.findBySkuId(sku.getId());
            // 合并条码
            Set<String> tmpList = new HashSet<String>();
            tmpList.addAll(rmiSku.getAddBarcodes());
            for (String addbarcode : tmpList) {
                boolean isNoNeedAdd = false;
                for (SkuBarcode tmpSku : list) {
                    if (tmpSku.getBarcode().equals(addbarcode)) {
                        isNoNeedAdd = true;
                        break;
                    }
                }
                if (!isNoNeedAdd) {
                    // 保存附加条码
                    SkuBarcode addSku = new SkuBarcode();
                    addSku.setBarcode(addbarcode);
                    addSku.setCustomer(c);
                    addSku.setSku(sku);
                    skuBarcodeDao.save(addSku);
                    if ("0".equals(chooseOption.getOptionValue())) {
                        insertMsgSkuSyncByBarCode(addSku);
                    }
                }
            }

        }
        if (sku != null && StringUtils.hasText(sku.getExtensionCode2())) {
            msgOmsSkuLogDao.deleteByExtCode2(sku.getExtensionCode2());
        }
        // 外包仓商品中间表插入数据
        insertWmsSku(sku, rmiSku.getAddBarcodes());
        List<String> list = chooseOptionDao.findByCategoryCodeA("AGV_SKU_BRAND");
        if (null != list && list.size() > 0) {
            if (list.contains(rmiSku.getBrandCode())) {
                Long createOrUpdate = isUpdate == false ? 1L : 2L;
                hubWmsManager.insertAgvSku(createOrUpdate, sku.getId());
            }
        }
    }

    /**
     * 满足条件插入中间表
     * 
     * @param sku
     */
    private void insertWmsSku(Sku sku, List<String> addBarcodes) {
        Long brandId = sku.getBrand().getId();
        // 1) 根据商品品牌查询是否存在外包仓的商品
        List<SkuWarehouseRefCommand> ref = skuWarehouseRef.findRefByBand(brandId, new BeanPropertyRowMapper<SkuWarehouseRefCommand>(SkuWarehouseRefCommand.class));
        if (ref.size() > 0) {
            for (SkuWarehouseRefCommand skuRef : ref) {
                // 2) 查找中间表是否存在code相同且状态不等于10的数据，如存在，修改原数据状态为0
                WarehouseMsgSku mSku = warehouseMsgSkuDao.getMsgSkuByCode(sku.getCode(), skuRef.getSourceWh());
                if (mSku != null) {
                    mSku.setStatus(0l);
                }
                // 3) 往外包仓商品中间表插数据
                WarehouseMsgSku msgSku = new WarehouseMsgSku();
                // 获取序列ID
                Long id = warehouseMsgSkuDao.getThreePlSeq(new SingleColumnRowMapper<Long>(Long.class));
                msgSku.setUuid(id);
                msgSku.setBarcode(sku.getBarCode());
                msgSku.setBrand(sku.getBrand().getId().toString());
                msgSku.setCode(sku.getCode());
                msgSku.setColor(sku.getColor());
                msgSku.setCrateTime(new Date());
                msgSku.setEnName(sku.getEnName());
                msgSku.setErrorCount(0l);
                msgSku.setExtMemo(sku.getKeyProperties());
                msgSku.setHeight(sku.getHeight());
                msgSku.setLength(sku.getLength());
                msgSku.setWidth(sku.getWidth());
                msgSku.setLastModifyTime(new Date());
                msgSku.setName(sku.getName());
                msgSku.setSkuSize(sku.getSkuSize());
                msgSku.setIsSn(sku.getIsSnSku());
                msgSku.setShopId(skuRef.getChannelId());
                msgSku.setSource(skuRef.getSource());
                msgSku.setWhCode(skuRef.getSourceWh());
                msgSku.setStatus(1l);
                msgSku.setSupplierCode(sku.getSupplierCode());
                warehouseMsgSkuDao.save(msgSku);
                // 4) 往条码明细表插数据
                if (addBarcodes != null && addBarcodes.size() > 0) {
                    Set<String> tmpList = new HashSet<String>();
                    tmpList.addAll(addBarcodes);
                    for (String addbarcode : tmpList) {
                        WarehouseMsgSkuBarcode barcode = new WarehouseMsgSkuBarcode();
                        barcode.setBarcode(addbarcode);
                        barcode.setMsgSku(msgSku);
                        warehouseMsgSkuBarcodeDao.save(barcode);
                    }
                }
                // break; // 因为菜鸟多个仓库同一商品只推送一次，所以循环一次跳出。
            }
        }
    }

    /**
     * 记录sku的变更
     */
    public SkuModifyLog refreshSkuModifyLog(Sku sku) {
        SkuModifyLog smf = new SkuModifyLog();
        try {
            org.springframework.beans.BeanUtils.copyProperties(sku, smf);

            smf.setLastModifyTime(new Date());
            smf.setId(null);
            smf.setSku(sku);
        } catch (Exception e) {
            smf = null;
            if (log.isErrorEnabled()) {
                log.error("refreshSkuModifyLog:" + sku.getCode(), e);
            }
        }
        return smf;
    }

    public SkuCommand getByBarcode1(String barCode) {
        Sku sku = skuDao.getByBarcode1(barCode);
        SkuCommand cmdSku = new SkuCommand();
        if (null == sku) return null;
        try {
            PropertyUtil.copyProperties(sku, cmdSku);
        } catch (Exception e) {
            log.error("Copy Bean properties error for Sku");
            log.error("", e);
            throw new RuntimeException("Copy Bean properties error for Sku");
        }
        return cmdSku;
    }


    public SkuCommand getByBarcode2(String barCode, String bi) {
        BiChannel b = bichannelDao.getByCode(bi);
        Sku sku = skuDao.getByBarcode(barCode, b.getCustomer().getId());
        SkuCommand cmdSku = new SkuCommand();
        if (null == sku) return null;
        try {
            PropertyUtil.copyProperties(sku, cmdSku);
        } catch (Exception e) {
            log.error("Copy Bean properties error for Sku");
            log.error("", e);
            throw new RuntimeException("Copy Bean properties error for Sku");
        }
        return cmdSku;
    }

    @Override
    public List<String> specialCommodityDelete(List<String> skuList, Long ouId, Long userId) {
        Sku sku;
        SkuModifyLog smf = null;
        Warehouse w = warehouseDao.getByOuId(ouId);
        InventoryCommand inv = null;
        List<String> skuBarcode = new ArrayList<String>();
        for (String barcode : skuList) {
            sku = skuDao.getByBarcode(barcode, w.getCustomer().getId());
            // 获取当前仓库下商品库存数量，若数量大于0，不能作废
            try {
                inv = inventoryDao.getInventoryQuantityBySkuId(sku.getId(), new BeanPropertyRowMapperExt<InventoryCommand>(InventoryCommand.class));
            } catch (Exception e) {}
            if (sku == null || inv.getQuantity() > 0) {
                skuBarcode.add(barcode);
            }
        }
        for (String barcode : skuBarcode) {
            skuList.remove(barcode);
        }
        try {
            for (String existBarcode : skuList) {
                sku = skuDao.getByBarcode(existBarcode, w.getCustomer().getId());
                // 商品作废
                if (sku.getBarCode() != null && !("".equals(sku.getBarCode()))) {
                    sku.setBarCode(sku.getBarCode() + "_D");
                }
                if (sku.getCode() != null && !("".equals(sku.getCode()))) {
                    sku.setCode(sku.getCode() + "_D");
                }
                if (sku.getExtensionCode1() != null && !("".equals(sku.getExtensionCode1()))) {
                    sku.setExtensionCode1(sku.getExtensionCode1() + "_D");
                }
                if (sku.getExtensionCode2() != null && !("".equals(sku.getExtensionCode2()))) {
                    sku.setExtensionCode2(sku.getExtensionCode2() + "_D");
                }
                if (sku.getCustomerSkuCode() != null && !("".equals(sku.getCustomerSkuCode()))) {
                    sku.setCustomerSkuCode(sku.getCustomerSkuCode() + "_D");
                }
                smf = refreshSkuModifyLog(sku);
                smf.setOpUserId(userId);
                skuDao.save(sku);
                // 记录商品作废信息到变更日志表
                skuModifyLogDao.save(smf);
            }
        } catch (Exception e) {
            log.error("特殊商品作废失败！", e);
        }
        return skuBarcode;
    }

    public List<SkuCommand> findSkuProvideInfoPickingDistrict(Long ouid, boolean b) {
        return skuDao.findSkuProvideInfoPickingDistrict(ouid, b, new Sort[] {new Sort("sku.code asc")}, new BeanPropertyRowMapperExt<SkuCommand>(SkuCommand.class));
    }

    public List<SkuCommand> findSkuProvideInfoUnMaintain(Long ouid, boolean b) {
        return skuDao.findSkuProvideInfoUnMaintain(ouid, b, new Sort[] {new Sort("sku.code asc")}, new BeanPropertyRowMapperExt<SkuCommand>(SkuCommand.class));
    }

    public List<SkuCommand> unfinishedStaUnMaintainProductExport(Long ouid, Boolean b, List<Integer> staTypes, List<Integer> staStatuses) {
        return skuDao.unfinishedStaUnMaintainProductExport(ouid, b, staTypes, staStatuses, new Sort[] {new Sort("sku.code asc")}, new BeanPropertyRowMapperExt<SkuCommand>(SkuCommand.class));
    }

    @Override
    public List<Sku> findSkuByStvId(Long stvId) {

        return skuDao.findSkuByStvId(stvId, new BeanPropertyRowMapperExt<Sku>(Sku.class));
    }

    @Override
    public Pagination<SkuCommand> returnInboundDirectiveDetail(int start, int pageSize, Long staId, Long ouId) {
        return skuDao.returnInboundDirectiveDetail(start, pageSize, staId, ouId, new BeanPropertyRowMapper<SkuCommand>(SkuCommand.class));
    }


    public void insertMsgSkuSyncByBarCode(SkuBarcode skuBarcode) {
        List<ChooseOption> ChooseOptionList = chooseOptionDao.findListByCategoryCodeAndKey("MsgSkuSync", "BiaoGan");
        Sku sku = skuDao.getByPrimaryKey(skuBarcode.getSku().getId());
        Brand brand = brandDao.getByPrimaryKey(sku.getBrand().getId());
        if (null != brand && !"".equals(brand)) {
            if (null != ChooseOptionList && ChooseOptionList.size() > 0) {
                if (ChooseOptionList.indexOf(brand.getName()) > -1 || brand.getName().startsWith("NBA")) {
                    MsgSKUSync msgSKUSync = new MsgSKUSync();
                    msgSKUSync.setSkuCode(sku.getCode());
                    msgSKUSync.setSkuName(sku.getName());
                    msgSKUSync.setBarCode(sku.getBarCode());
                    msgSKUSync.setBarCode2(skuBarcode.getBarcode());
                    msgSKUSync.setStatus(DefaultStatus.CREATED);
                    msgSKUSync.setSource("BiaoGan");
                    msgSKUSync.setUpdateTime(new Date());
                    msgSKUSync.setBrandName(brand.getName());
                    msgSKUSync.setVersion(0);
                    msgSKUSyncDao.save(msgSKUSync);
                }
            } else if (brand.getName().startsWith("NBA")) {
                MsgSKUSync msgSkuSync = new MsgSKUSync();
                msgSkuSync.setSkuCode(sku.getCode());
                msgSkuSync.setSkuName(sku.getName());
                msgSkuSync.setBarCode(sku.getBarCode());
                msgSkuSync.setBarCode2(skuBarcode.getBarcode());
                msgSkuSync.setStatus(DefaultStatus.CREATED);
                msgSkuSync.setSource("BiaoGan");
                msgSkuSync.setUpdateTime(new Date());
                msgSkuSync.setBrandName(brand.getName());
                msgSkuSync.setVersion(0);
                msgSKUSyncDao.save(msgSkuSync);
            }
        }

    }

    public void insertMsgSKUSync(Sku sku, boolean isUpdate) throws UnsupportedEncodingException {
        List<ChooseOption> ChooseOptionList = chooseOptionDao.findListByCategoryCodeAndKey("MsgSkuSync", "BiaoGan");
        List<ChooseOption> list = chooseOptionDao.findAllOptionListByCategoryCode("SFOMSBRAND");
        List<ChooseOption> gqsscmList = chooseOptionDao.findListByCategoryCodeAndKey("MsgSkuSync", "gqsscm");
        Brand brand = brandDao.getByPrimaryKey(sku.getBrand().getId());
        if (null != brand && !"".equals(brand)) {
            if (null != ChooseOptionList && ChooseOptionList.size() > 0) {
                if (ChooseOptionList.indexOf(brand.getName()) > -1 || brand.getName().startsWith("NBA")) {
                    MsgSKUSync msgSkuSync = new MsgSKUSync();
                    msgSkuSync.setSkuCode(sku.getCode());
                    msgSkuSync.setSkuName(sku.getName());
                    msgSkuSync.setBarCode(sku.getBarCode());
                    msgSkuSync.setStatus(DefaultStatus.CREATED);
                    msgSkuSync.setSource("BiaoGan");
                    msgSkuSync.setUpdateTime(new Date());
                    msgSkuSync.setBrandName(brand.getName());
                    msgSkuSync.setVersion(0);
                    msgSKUSyncDao.save(msgSkuSync);
                }
            } else if (brand.getName().startsWith("NBA")) {
                MsgSKUSync msgSkuSync = new MsgSKUSync();
                msgSkuSync.setSkuCode(sku.getCode());
                msgSkuSync.setSkuName(sku.getName());
                msgSkuSync.setBarCode(sku.getBarCode());
                msgSkuSync.setStatus(DefaultStatus.CREATED);
                msgSkuSync.setSource("BiaoGan");
                msgSkuSync.setUpdateTime(new Date());
                msgSkuSync.setBrandName(brand.getName());
                msgSkuSync.setVersion(0);
                msgSKUSyncDao.save(msgSkuSync);
            }
            if ("哈根达斯".equals(brand.getName())) {
                MsgSKUSync msgSkuSync = new MsgSKUSync();
                msgSkuSync.setSkuCode(sku.getCode());
                msgSkuSync.setSkuName(sku.getName());
                msgSkuSync.setBarCode(sku.getBarCode());
                msgSkuSync.setStatus(DefaultStatus.CREATED);
                msgSkuSync.setSource("SF");
                msgSkuSync.setIsSn(sku.getIsSnSku());
                if (isUpdate) {
                    msgSkuSync.setType(DefaultStatus.SENT);
                    msgSkuSync.setSfFlag("79274559-5");
                } else {
                    msgSkuSync.setType(DefaultStatus.CREATED);
                    msgSkuSync.setSfFlag("79274559-5");
                }
                msgSkuSync.setVersion(0);
                msgSKUSyncDao.save(msgSkuSync);
            }
            if (list.indexOf(brand.getCode()) > -1) {
                MsgSKUSync msgSkuSync = new MsgSKUSync();
                msgSkuSync.setSkuCode(sku.getCode());
                msgSkuSync.setSkuName(sku.getName());
                msgSkuSync.setBarCode(sku.getBarCode());
                msgSkuSync.setStatus(DefaultStatus.CREATED);
                msgSkuSync.setSource("SFTW");
                msgSkuSync.setIsSn(sku.getIsSnSku());
                if (isUpdate) {
                    msgSkuSync.setType(DefaultStatus.SENT);
                    msgSkuSync.setSfFlag("W91320509055203606L");
                } else {
                    msgSkuSync.setType(DefaultStatus.CREATED);
                    msgSkuSync.setSfFlag("W91320509055203606L");
                }
                msgSkuSync.setVersion(0);
                msgSKUSyncDao.save(msgSkuSync);
            }
            if (brand.getName().equals("Levi's")) {
                IdsInboundSku idsInboundSku = new IdsInboundSku();
                idsInboundSku.setCreatDate(new Date());
                idsInboundSku.setType("0");
                idsInboundSku.setColumnType("2");
                idsInboundSku.setStorerkey("18385");
                idsInboundSku.setSku(sku.getExtensionCode2());
                if (null != sku.getEnName() && !"".equals(sku.getEnName())) {
                    if (sku.getEnName().getBytes("GBK").length - 60 > 0) {
                        idsInboundSku.setDescr("1");
                    } else {
                        idsInboundSku.setDescr(sku.getEnName());
                    }
                } else {
                    idsInboundSku.setDescr(null);
                }

                idsInboundSku.setManufacturersku(sku.getBarCode());
                idsInboundSku.setPackkey("LEVISSTD");
                idsInboundSku.setActive("1");
                idsInboundSku.setLottablelabel("RCP_DATE");
                idsInboundSku.setStrategykey("LEVISECOM");
                idsInboundSku.setStyle(sku.getSupplierCode());
                idsInboundSku.setColour(null);
                idsInboundSku.setSizes(sku.getKeyProperties());
                idsInboundSku.setHeaderflag("SKUDT");
                idsInboundSku.setInterfaceactionflag("A");
                idsInboundSkuDao.save(idsInboundSku);

            }
            if (null != gqsscmList && gqsscmList.size() > 0) {
                if (ChooseOptionList.indexOf(brand.getName()) > -1) {
                    MsgSKUSync msgSkuSync = new MsgSKUSync();
                    msgSkuSync.setSkuCode(sku.getCode());
                    msgSkuSync.setSkuName(sku.getName());
                    msgSkuSync.setBarCode(sku.getBarCode());
                    msgSkuSync.setStatus(DefaultStatus.CREATED);
                    msgSkuSync.setSource("gqsscm");
                    msgSkuSync.setUpdateTime(new Date());
                    msgSkuSync.setBrandName(brand.getName());
                    if ("哈根达斯".equals(brand.getName())) {
                        msgSkuSync.setSource("SF");
                        msgSkuSync.setIsSn(sku.getIsSnSku());
                        if (isUpdate) {
                            msgSkuSync.setType(DefaultStatus.SENT);
                            msgSkuSync.setSfFlag("79274559-5");
                        } else {
                            msgSkuSync.setType(DefaultStatus.CREATED);
                            msgSkuSync.setSfFlag("79274559-5");
                        }
                        msgSkuSync.setVersion(0);
                    }
                    if (list.indexOf(brand.getCode()) > -1) {
                        msgSkuSync.setSource("SFTW");
                        msgSkuSync.setIsSn(sku.getIsSnSku());
                        if (isUpdate) {
                            msgSkuSync.setType(DefaultStatus.SENT);
                            msgSkuSync.setSfFlag("W91320509055203606L");
                        } else {
                            msgSkuSync.setType(DefaultStatus.CREATED);
                            msgSkuSync.setSfFlag("W91320509055203606L");
                        }
                        msgSkuSync.setVersion(0);
                    }
                    msgSKUSyncDao.save(msgSkuSync);
                }
            }
        }

    }

    @Override
    public SkuCommand findSkuMaterial(String barCode, Long ouId) {
        Warehouse w = warehouseDao.getByOuId(ouId);
        return skuDao.findSkuMaterialByBarcode(barCode, w.getCustomer().getId(), new BeanPropertyRowMapper<SkuCommand>(SkuCommand.class));
    }

}
