package com.jumbo.wms.manager.task.vmi;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import loxia.dao.support.BeanPropertyRowMapperExt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import com.baozun.task.annotation.SingleTaskLock;
import com.jumbo.dao.baseinfo.BrandDao;
import com.jumbo.dao.baseinfo.CustomerDao;
import com.jumbo.dao.baseinfo.SkuDao;
import com.jumbo.dao.baseinfo.SkuModifyLogDao;
import com.jumbo.dao.baseinfo.WarehouseDao;
import com.jumbo.dao.system.ChooseOptionDao;
import com.jumbo.dao.vmi.adidas.AdidasSalesInventoryDao;
import com.jumbo.dao.vmi.adidas.AdidasTotalInventoryDao;
import com.jumbo.dao.vmi.adidas.HubSkuDao;
import com.jumbo.dao.warehouse.InventoryDao;
import com.jumbo.wms.daemon.GncTask;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.model.baseinfo.Brand;
import com.jumbo.wms.model.baseinfo.Customer;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.baseinfo.SkuInterfaceType;
import com.jumbo.wms.model.baseinfo.SkuModifyLog;
import com.jumbo.wms.model.baseinfo.SkuSalesModel;
import com.jumbo.wms.model.baseinfo.SkuSnCheckMode;
import com.jumbo.wms.model.baseinfo.SkuSnType;
import com.jumbo.wms.model.baseinfo.SkuSpType;
import com.jumbo.wms.model.baseinfo.SkuWarrantyCardType;
import com.jumbo.wms.model.system.ChooseOption;
import com.jumbo.wms.model.vmi.adidasData.AdidasSalesInventory;
import com.jumbo.wms.model.warehouse.HubSku;
import com.jumbo.wms.model.warehouse.InboundStoreMode;
import com.jumbo.wms.model.warehouse.TransDeliveryType;


public class GncTaskImpl extends BaseManagerImpl implements GncTask {

    private static final long serialVersionUID = -6876685706066812933L;
    public static final String GNC_CUSTOMER_CODE = "gnc";
    public static final String GNC_OWN = "gnc_own";
    public static final String GNC_WCODE = "gnc_wcode";
    @Autowired
    private SkuDao skuDao;
    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private BrandDao brandDao;
    @Autowired
    private SkuModifyLogDao skuModifyLogDao;
    @Autowired
    private WarehouseDao warehouseDao;
    @Autowired
    private InventoryDao inventoryDao;
    @Autowired
    private AdidasTotalInventoryDao adidasTotalInventoryDao;
    @Autowired
    private AdidasSalesInventoryDao adidasSalesInventoryDao;
    @Autowired
    private ChooseOptionDao chooseOptionDao;
    @Autowired
    private HubSkuDao hubSkuDao;

    /**
     * 通用创建商品
     */
    @Override
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void createHubSku(String brand) {
        log.info("==============================GncTask createHubSku start:" + brand);
        // 查询品牌sku
        List<HubSku> blSkus = hubSkuDao.findNoHubSku(brand, new BeanPropertyRowMapperExt<HubSku>(HubSku.class));
        for (HubSku hubSku : blSkus) {
            try {
                updateSku(hubSku, brand);
            } catch (Exception e) {
                // log.error("createHubSku error..." + hubSku.getId());
                if (log.isErrorEnabled()) {
                    log.error("createHubSku error..." + hubSku.getId(), e);
                }
            }
        }
        log.info("==============================GncTask createHubSku end:" + brand);
    }



    /**
     * 全量可销售库存
     */
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void salesInventoryGnc(String brand) {
        log.info("==============================GncTask salesInventoryGnc start:" + brand);
        // System.out.println("==============================GncTask salesInventoryGnc start:" +
        // brand);
        // 根据客户code查询出客户下所有店铺code
        List<String> ownList = new ArrayList<String>();
        List<String> wcodeList = new ArrayList<String>();
        List<ChooseOption> chooselist = chooseOptionDao.findOptionListByCategoryCode(brand + "_own");
        for (ChooseOption chooseOption : chooselist) {
            ownList.add(chooseOption.getOptionKey());// 店铺code
        }
        List<ChooseOption> chooselist2 = chooseOptionDao.findOptionListByCategoryCode(brand + "_wcode");
        for (ChooseOption chooseOption : chooselist2) {
            wcodeList.add(chooseOption.getOptionKey());// 仓库id
        }

        if (ownList.size() > 0) {
            List<AdidasSalesInventory> salesList = null;
            try {
                // 查询出全量可销售库存
                salesList = inventoryDao.findAdidasSalesInventoryByCodes2(wcodeList, ownList, new BeanPropertyRowMapper<AdidasSalesInventory>(AdidasSalesInventory.class));
            } catch (Exception e) {
                if (log.isErrorEnabled()) {
                    log.error("salesInventoryGnc error...", e);
                }
            }
            for (AdidasSalesInventory sales : salesList) {
                sales.setCreateTime(new Date());// 创建时间
                sales.setFinishTime(new Date());
                sales.setInventoryTime(new Date());// 出库时间
                sales.setStatus(1);// 启用
                sales.setVersion(1);
                sales.setBrand(brand);
                adidasSalesInventoryDao.save(sales);
            }
        } else {
            log.info("===GncTask salesInventoryGnc codes size=0:" + brand);
        }
        log.info("==============================GncTask salesInventoryGnc end:" + brand);
    }

    /**
     * 增量库存
     */

    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void salesInventoryGncAdd() {
        try {
            Date date = new Date();
            inventoryDao.salesInventoryGncAdd(date);// 插入date 之前的数据
            inventoryDao.delSalesInventoryGncAdd(date);// 删除date之前的数据
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("salesInventoryGncAdd error...", e);
            }
        }

    }

    public Sku updateSku(HubSku hubSku, String brand2) {
        SkuModifyLog smf = null;
        if (hubSku == null) {
            throw new BusinessException(ErrorCode.CUSTOMER_NOT_FOUND, new Object[] {""});
        }
        Customer c = customerDao.getByCode(brand2);
        if (c == null) {
            throw new BusinessException(ErrorCode.CUSTOMER_NOT_FOUND, new Object[] {brand2});
        }
        Sku sku = skuDao.getByCustomerSkuCode(hubSku.getSkuCode(), c.getId());
        if (sku == null) {
            sku = new Sku();
        } else {
            log.error("updateSku 已存在 CustomerSkuCode=" + hubSku.getSkuCode() + ",cuId=" + c.getId());
        }
        Brand brand = brandDao.getByCode(brand2);
        if (brand == null) {
            brand = new Brand();
            brand.setCode(brand2);
            brand.setCreateTime(new Date());
            brand.setName(brand2);
            brand.setIsAvailable(true);
            brandDao.save(brand);
        }
        // 创建SKU
        sku.setBarCode(hubSku.getBarCode());
        // sku.setExtensionCode2(blSkuCommand.getpCode());// ad 设置 ext_code2
        sku.setBrand(brand);
        sku.setListPrice(hubSku.getListPrice());
        sku.setCode(hubSku.getSkuCode());
        sku.setColor(null);
        sku.setColorName(null);
        sku.setCustomer(c);
        sku.setCustomerSkuCode(hubSku.getSkuCode());
        sku.setEnName(hubSku.getEnName());
        sku.setName(hubSku.getName());
        sku.setExtensionCode3(hubSku.getSkuCode());
        if ("1".equals(hubSku.getIsValid())) {// 1 是效期商品
            sku.setStoremode(InboundStoreMode.SHELF_MANAGEMENT);// 效期商品 上架模式
            sku.setWarningDate(0);// 预警
        } else {
            sku.setStoremode(InboundStoreMode.TOGETHER);// 上架模式
        }
        // sku.setExtProp2(blSkuCommand.getGender());
        // sku.setExtProp4(blSkuCommand.getTechnicalSize());
        // sku.setExtProp1(blSkuCommand.getBusinessSegment());
        sku.setJmCode(hubSku.getSkuCode());


        // if (false == isUpdate) {
        // if (rmiSku.getGrossWeight() != null) {
        // sku.setGrossWeight(rmiSku.getGrossWeight());
        // }
        // }
        sku.setIsGift(false);
        if (sku.getDeliveryType() == null || TransDeliveryType.ORDINARY.equals(sku.getDeliveryType())) {
            sku.setDeliveryType(TransDeliveryType.ORDINARY);// 普通
        }
        sku.setCategory(hubSku.getCategory());
        sku.setSalesMode(SkuSalesModel.CONSIGNMENT);// 待销售
        // sku.setSkuSize(blSkuCommand.getSizeCd());
        // sku.setKeyProperties(blSkuCommand.getSizeCd());// ad扩展属性
        sku.setSupplierCode(hubSku.getSupplierCode());// 货号
        sku.setWarrantyCardType(SkuWarrantyCardType.NO_NEED);// 无需保修卡
        sku.setLastModifyTime(new Date());
        sku.setInterfaceType(SkuInterfaceType.NUL);
        sku.setSnCheckMode(SkuSnCheckMode.GENERAL);
        sku.setSnType(SkuSnType.GENERAL);
        if ("1".equals(hubSku.getSpType())) {
            sku.setSpType(SkuSpType.valueOf(1));// 1 耗材
        } else {
            sku.setSpType(SkuSpType.valueOf(0));// 0 普通商品
        }
        // 长宽高
        sku.setLength(hubSku.getLength());
        sku.setWidth(hubSku.getWidth());
        sku.setHeight(hubSku.getHeight());
        // 重量
        sku.setGrossWeight(hubSku.getWeight());
        // 保质期天数
        sku.setValidDate(hubSku.getProductDate());
        skuDao.save(sku);
        smf = refreshSkuModifyLog(sku);
        if (smf != null) {
            skuModifyLogDao.save(smf);// 将新建或修改后的SKU保存进变更日志表
        }
        skuDao.flush();
        // if (sku != null && StringUtils.hasText(sku.getExtensionCode2())) {
        // msgOmsSkuLogDao.deleteByExtCode2(sku.getExtensionCode2());
        // }
        // 更新 ad品牌SKU 状态为0 已创建完
        hubSkuDao.updateHubSkuStatus(hubSku.getId());
        return sku;
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
                log.error("GncTask refreshSkuModifyLog error...", e);
            }
        }
        return smf;
    }


}
