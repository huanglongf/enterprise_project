package com.jumbo.wms.manager.task.vmi;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.baozun.bizhub.manager.brand.ad.AdAmiIncInventoryManager;
import com.baozun.bizhub.model.ad.AdAmiIncInventory;
import com.baozun.task.annotation.SingleTaskLock;
import com.jumbo.dao.baseinfo.BrandDao;
import com.jumbo.dao.baseinfo.CustomerDao;
import com.jumbo.dao.baseinfo.SkuDao;
import com.jumbo.dao.baseinfo.SkuModifyLogDao;
import com.jumbo.dao.baseinfo.WarehouseDao;
import com.jumbo.dao.system.ChooseOptionDao;
import com.jumbo.dao.vmi.adidas.AdidasSalesInventoryDao;
import com.jumbo.dao.vmi.adidas.AdidasTotalInventoryDao;
import com.jumbo.dao.vmi.adidas.BlSkuDao;
import com.jumbo.dao.vmi.adidas.InventoryBatchDao;
import com.jumbo.dao.warehouse.InventoryDao;
import com.jumbo.util.TimeHashMap;
import com.jumbo.wms.daemon.AdidasTask;
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
import com.jumbo.wms.model.vmi.adidasData.AdidasTotalInventory;
import com.jumbo.wms.model.vmi.adidasData.InventoryBatch;
import com.jumbo.wms.model.warehouse.BlSkuCommand;
import com.jumbo.wms.model.warehouse.InboundStoreMode;
import com.jumbo.wms.model.warehouse.LogQueueHub;
import com.jumbo.wms.model.warehouse.TransDeliveryType;

import loxia.dao.support.BeanPropertyRowMapperExt;


public class AdidasTaskImpl extends BaseManagerImpl implements AdidasTask {

    private static final long serialVersionUID = -6876685706066812933L;
    public static final String AD_CUSTOMER_CODE = "adidas";
    public static final String AD_BRAND_CODE = "adidas";
    public static final String AD_BRAND_NAME = "adidas";

    static TimeHashMap<String, String> adSetParaMap = new TimeHashMap<String, String>();

    @Autowired
    private SkuDao skuDao;
    @Autowired
    private BlSkuDao blSkuDao;
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
    private AdAmiIncInventoryManager adAmiIncInventoryManager;
    @Autowired
    private InventoryBatchDao inventoryBatchDao;

    /**
     * 创建商品
     */
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void createSku() {
        // 查询品牌sku
        List<BlSkuCommand> blSkus = blSkuDao.findNoBlSku(null, new BeanPropertyRowMapperExt<BlSkuCommand>(BlSkuCommand.class));
        for (BlSkuCommand blSkuCommand : blSkus) {
            if (blSkuCommand.getBarcode() == null) {
                blSkuCommand.setSkuCdBarcode(blSkuCommand.getSkuCdBarcode());
            } else {
                blSkuCommand.setSkuCdBarcode(blSkuCommand.getBarcode());
            }
            try {
                updateSku(blSkuCommand);
            } catch (Exception e) {
                if (log.isErrorEnabled()) {
                    log.error("adidas createSku error..." + blSkuCommand.getId(), e);
                }
            }
        }
    }

    public Sku updateSku(BlSkuCommand blSkuCommand) {
        SkuModifyLog smf = null;
        if (blSkuCommand == null) {
            throw new BusinessException(ErrorCode.CUSTOMER_NOT_FOUND, new Object[] {""});
        }
        Customer c = customerDao.getByCode(AD_CUSTOMER_CODE);
        if (c == null) {
            throw new BusinessException(ErrorCode.CUSTOMER_NOT_FOUND, new Object[] {AD_CUSTOMER_CODE});
        }
        Sku sku = skuDao.getByCustomerSkuCode(blSkuCommand.getSkuCdBarcode(), c.getId());
        if (sku == null) {
            sku = new Sku();
        } else {
            log.info("ad updateSku 已存在 CustomerSkuCode=" + blSkuCommand.getSkuCdBarcode() + ",cuId=" + c.getId());
        }
        Brand brand = brandDao.getByCode(AD_BRAND_CODE);
        if (brand == null) {
            brand = new Brand();
            brand.setCode(AD_BRAND_CODE);
            brand.setCreateTime(new Date());
            brand.setName(AD_BRAND_NAME);
            brand.setIsAvailable(true);
            brandDao.save(brand);
        }
        // 创建SKU
        sku.setBarCode(blSkuCommand.getSkuCdBarcode());
        // sku.setExtensionCode2(blSkuCommand.getpCode());// ad 设置 ext_code2
        sku.setBrand(brand);
        sku.setListPrice(blSkuCommand.getUnitPrice());
        sku.setCode(blSkuCommand.getSkuCdBarcode());
        sku.setColor(null);
        sku.setColorName(null);
        sku.setCustomer(c);
        sku.setCustomerSkuCode(blSkuCommand.getSkuCdBarcode());
        sku.setEnName(null);
        // sku.setName(blSkuCommand.getSkuCdBarcode());// 老柯核对need
        sku.setExtensionCode3(blSkuCommand.getSkuCdBarcode());
        sku.setStoremode(InboundStoreMode.TOGETHER);// ad的上架模式
        sku.setExtProp2(blSkuCommand.getGender());
        sku.setExtProp4(blSkuCommand.getTechnicalSize());
        sku.setExtProp1(blSkuCommand.getBusinessSegment());
        sku.setJmCode(blSkuCommand.getSkuCdBarcode());
        if (blSkuCommand.getOrigPrice() != null) {
            sku.setExtProp3(blSkuCommand.getOrigPrice().toString());
        }

        // if (false == isUpdate) {
        // if (rmiSku.getGrossWeight() != null) {
        // sku.setGrossWeight(rmiSku.getGrossWeight());
        // }
        // }
        sku.setIsGift(false);
        if (sku.getDeliveryType() == null || TransDeliveryType.ORDINARY.equals(sku.getDeliveryType())) {
            sku.setDeliveryType(TransDeliveryType.ORDINARY);// 普通
        }
        sku.setCategory(blSkuCommand.getCategory());
        sku.setSalesMode(SkuSalesModel.CONSIGNMENT);// 待销售
        sku.setSkuSize(blSkuCommand.getSizeCd());
        sku.setKeyProperties(blSkuCommand.getSizeCd());// ad扩展属性
        sku.setStoremode(InboundStoreMode.TOGETHER);// ad的上架模式
        sku.setSupplierCode(blSkuCommand.getpCode());
        sku.setWarrantyCardType(SkuWarrantyCardType.NO_NEED);// 无需保修卡
        sku.setLastModifyTime(new Date());
        sku.setInterfaceType(SkuInterfaceType.NUL);
        sku.setSnCheckMode(SkuSnCheckMode.GENERAL);
        sku.setSnType(SkuSnType.GENERAL);
        sku.setSpType(SkuSpType.valueOf(0));// 平台商品类型
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
        blSkuDao.updateBlSkuStatus(blSkuCommand.getId());
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
                log.error("refreshSkuModifyLog error..." + sku.getCode(), e);
            }
        }
        return smf;
    }

    /**
     * 全量库存
     */
    @Override
    @Transactional
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void totalInventoryAdidas() {
        Date date =new Date();
        log.debug("==============================AdidasTask totalInventoryAdidas start");
        // 根据客户code查询出客户下所有店铺code
        List<String> codes = warehouseDao.getAllChannelByCusCode("adidas", new SingleColumnRowMapper<String>(String.class));
        ChooseOption op = chooseOptionDao.findByCategoryCodeAndKey("adidas", "SHWH205");
        if (codes.size() > 0) {
            List<AdidasTotalInventory> totalList = null;
            try {
                // 查询出全量库存
                totalList = inventoryDao.findAdidasTotalInventoryByCodes(Long.valueOf(op.getOptionValue()), codes, new BeanPropertyRowMapper<AdidasTotalInventory>(AdidasTotalInventory.class));
            } catch (Exception e) {
                if (log.isErrorEnabled()) {
                    log.error("totalInventoryAdidas", e);
                }
            }
            for (AdidasTotalInventory adidasTotalInventory : totalList) {
                adidasTotalInventory.setCreateTime(date);// 创建时间
                adidasTotalInventory.setFinishTime(new Date());
                adidasTotalInventory.setStatus(1);// 启用
                adidasTotalInventory.setVersion(1);
                adidasTotalInventoryDao.save(adidasTotalInventory);
            }
        } else {
            log.debug("===AdidasTask totalInventoryAdidas codes size=0");
        }
        log.debug("==============================AdidasTask totalInventoryAdidas end");
    }

    /*
     * 全量可销售库存
     */
    @Override
   @Transactional
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void salesInventoryAdidas() {
        Date date =new Date();
        Long sum = 0L;
        log.debug("==============================AdidasTask salesInventoryAdidas start");
        // 根据客户code查询出客户下所有店铺code
        List<String> codes = warehouseDao.getAllChannelByCusCode("adidas", new SingleColumnRowMapper<String>(String.class));
        ChooseOption op = chooseOptionDao.findByCategoryCodeAndKey("adidas", "SHWH205");
        if (codes.size() > 0) {
            // 查询出销售库存
            List<AdidasSalesInventory> salesList = inventoryDao.findAdidasSalesInventoryByCodes(Long.valueOf(op.getOptionValue()), codes, new BeanPropertyRowMapper<AdidasSalesInventory>(AdidasSalesInventory.class));
            for (AdidasSalesInventory sales : salesList) {
                sales.setCreateTime(date);// 创建时间
                sales.setFinishTime(new Date());
                sales.setInventoryTime(new Date());// 出库时间
                sales.setStatus(1);// 启用
                sales.setVersion(1);
                sales.setBrand("adidas");
                sales.setBatchCode(date.toString());
                adidasSalesInventoryDao.save(sales);
                sum = sum + sales.getQuantity();
            }
            InventoryBatch inventoryBatch = new InventoryBatch();
            inventoryBatch.setBatchCode(date.toString());
            inventoryBatch.setCreateTime(date);
            inventoryBatch.setLineCount(new Long((long) salesList.size()));
            inventoryBatch.setTotalInventory(sum);
            inventoryBatchDao.save(inventoryBatch);
        } else {
            log.debug("===AdidasTask salesInventoryAdidas codes size=0");
        }
        log.debug("==============================AdidasTask salesInventoryAdidas end");
    }

    /**
     * 增量库存
     */
    @Override
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void salesIncrementalInvToAdidas() {
        log.debug("=================AdidasTask salesIncrementalInvToAdidas start=============");
        try {
            // 延迟时间配置(预留)
            String min = adSetParaMap.get("min");
            if (min == null) {
                ChooseOption minutes = chooseOptionDao.findByCategoryCodeAndKey(AD_CUSTOMER_CODE, "min");
                min = minutes == null ? "0" : (minutes.getOptionValue() == null ? "0" : minutes.getOptionValue());
                adSetParaMap.put("min", min, 30 * 60 * 1000);
            }
            // 每次推送hub数量配置
            String num = adSetParaMap.get("num");
            if (num == null) {
                ChooseOption number = chooseOptionDao.findByCategoryCodeAndKey(AD_CUSTOMER_CODE, "num");
                num = number == null ? "1000" : (number.getOptionValue() == null ? "1000" : number.getOptionValue());
                adSetParaMap.put("num", num, 30 * 60 * 1000);
            }
            // 转移date之前数据进中间表（t_wh_st_log_queue_hub）
            Date date = DateUtils.addMinutes(new Date(), Integer.parseInt(min));
            inventoryDao.salesInventoryAdidasAdd(date);
            try {
                inventoryDao.transferInventoryAdidasToLog(date);
            } catch (Exception e) {
                log.error("adAmiIncInventoryManager transferInventoryAdidasToLog error...", e);
            }
            // 删除date之前的数据（t_wh_st_log_queue）
            inventoryDao.delSalesInventoryAdidasAdd(date);
            while (true) {
                List<LogQueueHub> list = inventoryDao.findAdIncrementalInv(Integer.parseInt(num), new BeanPropertyRowMapper<LogQueueHub>(LogQueueHub.class));
                if (list != null && list.size() > 0) {
                    // 推送hub
                    try {
                        List<AdAmiIncInventory> invs = new ArrayList<AdAmiIncInventory>();
                        List<Long> ids = new ArrayList<Long>();
                        for (LogQueueHub logQueueHub : list) {
                            AdAmiIncInventory inv = new AdAmiIncInventory();
                            // inv.setWhCode(logQueueHub.getWhouCode());
                            inv.setWhCode("ECWH");// 固定的
                            inv.setSkuCode(logQueueHub.getCustomerSkuCode());
                            if (logQueueHub.getSalesAvailQty() != null) {
                                inv.setQty(logQueueHub.getSalesAvailQty().intValue());
                            } else {
                                inv.setQty(0);
                            }
                            inv.setOpTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(logQueueHub.getCreateTime()));
                            invs.add(inv);
                            ids.add(logQueueHub.getId());
                        }
                        String result = adAmiIncInventoryManager.creatIncInvFile("AD", invs);
                        if (StringUtils.equals(result, "1")) {
                            // 更新推送状态
                            if (list.size() <= 1000) {
                                inventoryDao.updateAdAmiIncInvSucessStatus(10, ids);
                            } else {
                                // 更新条数>1000时
                                List<Long> temp = new ArrayList<Long>();
                                for (int i = 0; i < ids.size(); i++) {
                                    temp.add(ids.get(i));
                                    if (i != 0 && i % 1000 == 0) {
                                        inventoryDao.updateAdAmiIncInvSucessStatus(10, temp);
                                        temp.clear();
                                    }
                                }
                                if (!temp.isEmpty()) {
                                    inventoryDao.updateAdAmiIncInvSucessStatus(10, temp);
                                }
                            }
                        } else {
                            // 添加错误次数和下次推送时间
                            if (list.size() <= 1000) {
                                inventoryDao.updateAdAmiIncInvFailedStatus(ids);
                            } else {
                                // 更新条数>1000时
                                List<Long> temp = new ArrayList<Long>();
                                for (int i = 0; i < ids.size(); i++) {
                                    temp.add(ids.get(i));
                                    if (i != 0 && i % 1000 == 0) {
                                        inventoryDao.updateAdAmiIncInvFailedStatus(temp);
                                        temp.clear();
                                    }
                                }
                                if (!temp.isEmpty()) {
                                    inventoryDao.updateAdAmiIncInvFailedStatus(temp);
                                }
                            }
                        }
                    } catch (Exception e) {
                        if (log.isErrorEnabled()) {
                            log.error("adAmiIncInventoryManager to hub error...", e);
                        }
                        // 添加错误次数和下次推送时间
                        List<Long> wrongIds = new ArrayList<Long>();
                        for (LogQueueHub logQueueHub : list) {
                            wrongIds.add(logQueueHub.getId());
                        }
                        if (list.size() <= 1000) {
                            inventoryDao.updateAdAmiIncInvFailedStatus(wrongIds);
                        } else {
                            // 更新条数>1000时
                            List<Long> temp = new ArrayList<Long>();
                            for (int i = 0; i < wrongIds.size(); i++) {
                                temp.add(wrongIds.get(i));
                                if (i != 0 && i % 1000 == 0) {
                                    inventoryDao.updateAdAmiIncInvFailedStatus(temp);
                                    temp.clear();
                                }
                            }
                            if (!temp.isEmpty()) {
                                inventoryDao.updateAdAmiIncInvFailedStatus(temp);
                            }
                        }
                    }
                } else {
                    break;
                }
            }
            // 无条件删除中间表前一天的AD增量数据
            try {
                Calendar now = Calendar.getInstance();
                if (now.get(Calendar.HOUR_OF_DAY) < 1) {
                    inventoryDao.deleteLastAdAmiIncInvDate();
                }
            } catch (Exception e) {
                log.error("salesIncrementalInvToAdidas==========deleteLastDateError=======", e);
            }
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("adAmiIncInventoryManager error...", e);
            }
        }
        log.debug("==================AdidasTask salesIncrementalInvToAdidas end============");
    }
}
