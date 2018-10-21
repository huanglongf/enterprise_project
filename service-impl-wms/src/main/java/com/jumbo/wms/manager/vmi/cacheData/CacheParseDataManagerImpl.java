package com.jumbo.wms.manager.vmi.cacheData;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import cn.baozun.bh.connector.cch.model.Product;
import cn.baozun.bh.connector.cch.model.Sales;
import cn.baozun.bh.connector.cch.model.SalesOperation;
import cn.baozun.bh.connector.cch.model.StockTransfers;
import cn.baozun.bh.connector.cch.model.StockTransfers.StockTransfer;
import cn.baozun.bh.connector.model.ConnectorMessage;
import cn.baozun.bh.util.JSONUtil;
import cn.baozun.bh.util.ZipUtil;

import com.jumbo.dao.authorization.OperationUnitDao;
import com.jumbo.dao.baseinfo.SkuDao;
import com.jumbo.dao.vmi.cch.CacheMarkdownDetailDataDao;
import com.jumbo.dao.vmi.cch.CacheProductDataDao;
import com.jumbo.dao.vmi.cch.CacheSinUnnecessaryDao;
import com.jumbo.dao.vmi.cch.CacheStockTransferInDataDao;
import com.jumbo.dao.vmi.cch.CchSalesDao;
import com.jumbo.dao.warehouse.BiChannelDao;
import com.jumbo.dao.warehouse.InventoryStatusDao;
import com.jumbo.dao.warehouse.StaLineDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.dao.warehouse.WhInfoTimeRefDao;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.baseinfo.BaseinfoManager;
import com.jumbo.wms.manager.system.SequenceManager;
import com.jumbo.wms.manager.warehouse.WareHouseManagerExe;
import com.jumbo.wms.manager.warehouse.WareHouseManagerQuery;
import com.jumbo.wms.model.DataStatus;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.vmi.cch.CacheMarkdownDetailData;
import com.jumbo.wms.model.vmi.cch.CacheProductData;
import com.jumbo.wms.model.vmi.cch.CacheSinUnnecessary;
import com.jumbo.wms.model.vmi.cch.CacheStockTransferInData;
import com.jumbo.wms.model.vmi.cch.CchSales;
import com.jumbo.wms.model.warehouse.InventoryStatus;
import com.jumbo.wms.model.warehouse.StaLine;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationStatus;
import com.jumbo.wms.model.warehouse.StockTransApplicationType;
import com.jumbo.wms.model.warehouse.WhInfoTimeRefBillType;
import com.jumbo.wms.model.warehouse.WhInfoTimeRefNodeType;

@Transactional
@Service("cacheParseDataManager")
public class CacheParseDataManagerImpl extends BaseManagerImpl implements CacheParseDataManager {

    /**
     * 
     */
    private static final long serialVersionUID = -7461067659166063724L;
    @Autowired
    private CacheProductDataDao cacheProductDataDao;
    @Autowired
    private CacheMarkdownDetailDataDao cacheMarkdownDetailDataDao;
    @Autowired
    private CacheStockTransferInDataDao stockTransferInDataDao;
    @Autowired
    private CacheStockTransferInDataDao transferInDao;
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private SequenceManager sequenceManager;
    @Autowired
    private WareHouseManagerQuery wareHouseManagerQuery;
    @Autowired
    private SkuDao skuDao;
    @Autowired
    private BiChannelDao shopDao;
    @Autowired
    private OperationUnitDao ouDao;
    @Autowired
    private InventoryStatusDao inventoryStatusDao;
    @Autowired
    private StaLineDao staLineDao;
    @Autowired
    private BaseinfoManager baseinfoManager;
    @Autowired
    private WareHouseManagerExe wmExe;
    @Autowired
    private CchSalesDao cchSaleDao;
    @Autowired
    private WhInfoTimeRefDao whInfoTimeRefDao;
    @Autowired
    private CacheSinUnnecessaryDao sinUnnecessaryDao;

    /**
     * 接收MQ Product数据并插入数据库 param : message
     */
    public void receiveCacheProductByMq(String message) {
        CacheProductData cacheProductData = null;
        try {
            ConnectorMessage connectorMessage = JSONUtil.jsonToBean(message, ConnectorMessage.class);
            // String confirmId = connectorMessage.getConfirmId();
            String messageContent = connectorMessage.getMessageContent();
            if (connectorMessage.getIsCompress()) {
                messageContent = ZipUtil.decompress(messageContent);
            }
            log.debug("******************* cache product start ********************");
            Product cacheProduct = (Product) JSONUtil.jsonToBean(messageContent, Product.class);// json字符串转java对象通用方法
            cacheProductData = new CacheProductData();
            setCacheProductData(cacheProductData, cacheProduct);
            cacheProductDataDao.save(cacheProductData);
            log.debug("******************* cache product end message:" + message + "********************");
        } catch (Exception e) {
            log.error("", e);
        }
    }

    /**
     * 接收MQ Markdown Detail数据并插入数据库 param : message
     */
    public void receiveCacheMarkdownDetailByMq(String message) {
        CacheMarkdownDetailData cacheMarkdownDetailData = null;
        try {
            ConnectorMessage connectorMessage = JSONUtil.jsonToBean(message, ConnectorMessage.class);
            // String confirmId = connectorMessage.getConfirmId();
            String messageContent = connectorMessage.getMessageContent();
            if (connectorMessage.getIsCompress()) {
                messageContent = ZipUtil.decompress(messageContent);
            }
            log.debug("******************* cache MarkdownDetail start ********************");
            SalesOperation salesOperation = (SalesOperation) JSONUtil.jsonToBean(messageContent, SalesOperation.class);// json字符串转java对象通用方法
            cacheMarkdownDetailData = new CacheMarkdownDetailData();
            setCacheMarkdownDetailData(cacheMarkdownDetailData, salesOperation);
            cacheMarkdownDetailDataDao.save(cacheMarkdownDetailData);
            log.debug("******************* cache MarkdownDetail end message:" + message + "********************");
        } catch (Exception e) {
            log.error("", e);
        }
    }

    /**
     * 接收MQ Stock Transfer数据并插入数据库 param : message
     */
    public void receiveCacheStockTransferInByMq(String message) {
        CacheStockTransferInData stockTransferInData = null;
        try {
            ConnectorMessage connectorMessage = JSONUtil.jsonToBean(message, ConnectorMessage.class);
            // String confirmId = connectorMessage.getConfirmId();
            String messageContent = connectorMessage.getMessageContent();
            if (connectorMessage.getIsCompress()) {
                messageContent = ZipUtil.decompress(messageContent);
            }
            log.debug("******************* cache StockTransferIn start ********************");
            // 以下为新添加
            List<StockTransfer> stockTransfers = JSONUtil.jsonToBean(messageContent, StockTransfers.class).getList();// json字符串转java对象通用方法
            for (StockTransfer stockTransfer : stockTransfers) {
                stockTransferInData = new CacheStockTransferInData();
                setCacheStockTransferInData(stockTransferInData, stockTransfer);
                stockTransferInDataDao.save(stockTransferInData);
            }
            log.debug("******************* cache StockTransferIn end message:" + message + "********************");
        } catch (Exception e) {
            log.error("", e);
        }
    }

    /**
     * CacheProductData 赋值
     */
    private void setCacheProductData(CacheProductData cacheProductData, Product cacheProduct) {
        cacheProductData.setItemsBarcode(cacheProduct.getItemBarcode() == null ? "" : cacheProduct.getItemBarcode().trim());
        cacheProductData.setCarelable11(cacheProduct.getCareLabel1() == null ? "" : cacheProduct.getCareLabel1().trim());
        cacheProductData.setCarelable12(cacheProduct.getCareLabel2() == null ? "" : cacheProduct.getCareLabel2().trim());
        cacheProductData.setCarelable13(cacheProduct.getCareLabel3() == null ? "" : cacheProduct.getCareLabel3().trim());
        cacheProductData.setCarelable14(cacheProduct.getCareLabel4() == null ? "" : cacheProduct.getCareLabel4().trim());
        cacheProductData.setCnSize(cacheProduct.getCnSize() == null ? "" : cacheProduct.getCnSize().trim());
        cacheProductData.setColorCode(cacheProduct.getColorCode() == null ? "" : cacheProduct.getColorCode().trim());
        cacheProductData.setColorName(cacheProduct.getColorName() == null ? "" : cacheProduct.getColorName().trim());
        cacheProductData.setDescription(cacheProduct.getDescription() == null ? "" : cacheProduct.getDescription().trim());
        cacheProductData.setFamilyCn(cacheProduct.getFamilyCN() == null ? "" : cacheProduct.getFamilyCN().trim());
        cacheProductData.setGbStandard(cacheProduct.getGBStandardNumber() == null ? "" : cacheProduct.getGBStandardNumber().trim());
        cacheProductData.setIsp(cacheProduct.getISP() == null ? "" : cacheProduct.getISP().toString().trim());
        cacheProductData.setLiningComposition(cacheProduct.getLiningComposition() == null ? "" : cacheProduct.getLiningComposition().trim());
        cacheProductData.setMadeIn(cacheProduct.getMadeIn() == null ? "" : cacheProduct.getMadeIn().trim());
        cacheProductData.setMainfabricComposition(cacheProduct.getMainFabricComposition() == null ? "" : cacheProduct.getMainFabricComposition().trim());
        cacheProductData.setPaddingComposition(cacheProduct.getPaddingComposition() == null ? "" : cacheProduct.getPaddingComposition().trim());
        cacheProductData.setProductCode(cacheProduct.getProductCode() == null ? "" : cacheProduct.getProductCode().trim());
        cacheProductData.setProductName(cacheProduct.getProductName() == null ? "" : cacheProduct.getProductName().trim());
        cacheProductData.setSkuCode(cacheProduct.getSkuCode());
        cacheProductData.setStandardNumber(cacheProduct.getStandardNumber() == null ? "" : cacheProduct.getStandardNumber().trim());
        cacheProductData.setCreateTime(new Date());
        cacheProductData.setStatus("0");
    }

    /**
     * CacheMarkdownDetailData 赋值
     * 
     * @param cacheProductData
     * @param cacheProduct
     */
    private void setCacheMarkdownDetailData(CacheMarkdownDetailData cacheMarkdownDetailData, SalesOperation salesOperation) {
        cacheMarkdownDetailData.setSalesOperationCode(salesOperation.getOpCode());
        cacheMarkdownDetailData.setSalesOperationName(salesOperation.getOpName() == null ? "" : salesOperation.getOpName().trim());
        cacheMarkdownDetailData.setSkuCode(salesOperation.getBarcode() == null ? 0 : Long.parseLong(salesOperation.getBarcode().trim()));
        cacheMarkdownDetailData.setStartingDate(salesOperation.getStartDate());
        cacheMarkdownDetailData.setEndDate(salesOperation.getEndDate());
        cacheMarkdownDetailData.setNewPrice(new BigDecimal(salesOperation.getNewPrice()));
        cacheMarkdownDetailData.setCreateTime(new Date());
        cacheMarkdownDetailData.setStatus("0");
    }

    /**
     * CacheStockTransferInData 赋值
     */
    private void setCacheStockTransferInData(CacheStockTransferInData stockTransferInData, StockTransfer stockTransfer) {
        stockTransferInData.setParcelCode(stockTransfer.getParcelCode() == null ? "" : stockTransfer.getParcelCode().trim());
        stockTransferInData.setQuantityShipped(Long.valueOf(stockTransfer.getQuantity()));
        stockTransferInData.setSkuCode(stockTransfer.getSkuCode());
        stockTransferInData.setStoreCode(stockTransfer.getStoreCode());
        stockTransferInData.setCreateTime(new Date());
        if (StringUtils.hasText(stockTransfer.getBrand())) {
            stockTransferInData.setVmiCode(stockTransfer.getBrand());
        } else {
            stockTransferInData.setVmiCode(BiChannel.CHANNEL_VMICODE_CACHECAHEC_CCH);
        }

        stockTransferInData.setStatus("0");
    }

    public List<String> getParcelCodeWithNoSta(String vmiCode) {
        return stockTransferInDataDao.getParcelCodeWithNoSta(vmiCode, new SingleColumnRowMapper<String>());
    }

    // 0000910009230
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void generateInboundSta(String parcelCode) {
        List<CacheStockTransferInData> ins = transferInDao.getStockInListByParcelCode(parcelCode);
        if (ins.size() < 0) return;

        StockTransApplication sta = new StockTransApplication();
        sta.setType(StockTransApplicationType.VMI_INBOUND_CONSIGNMENT);
        sta.setBusinessSeqNo(staDao.getBusinessSeqNo(new SingleColumnRowMapper<BigDecimal>()).longValue());
        sta.setCreateTime(new Date());
        sta.setLastModifyTime(new Date());
        sta.setStatus(StockTransApplicationStatus.CREATED);
        sta.setCode(sequenceManager.getCode(StockTransApplication.class.getName(), sta));
        sta.setRefSlipCode(parcelCode);
        // 订单状态与账号关联
        staDao.save(sta);

        OperationUnit ou = null;
        String innerShopCode = null;
        // Warehouse wh = null;
        InventoryStatus is = null;
        Long customerId = null;
        CacheStockTransferInData temp = ins.get(0);
        // String vmiCode = temp.getStoreCode().toString();
        String vmiCode = temp.getVmiCode().toString();
        BiChannel shop = shopDao.getByVmiCode(vmiCode);
        if (shop == null) {
            // 抛异常，报错
            throw new BusinessException("没有找到店铺信息!vmiCode:" + vmiCode);
        }
        wmExe.validateBiChannelSupport(null, shop.getCode());
        ou = ouDao.getDefaultInboundWhByShopId(shop.getId());
        if (ou == null) {
            log.info("=========OPERATION UNIT {} NOT FOUNT===========", new Object[] {shop.getVmiCode()});
            throw new BusinessException(ErrorCode.END_OPERATION_UNIT_NOT_FOUNT);
        }
        whInfoTimeRefDao.insertWhInfoTime2(sta.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.CREATE.getValue(), null, ou.getId());
        if (is == null) {
            Long companyId = null;
            if (ou.getParentUnit() != null) {
                OperationUnit ou1 = ouDao.getByPrimaryKey(ou.getParentUnit().getId());
                if (ou1 != null) {
                    companyId = ou1.getParentUnit().getId();
                }
            }
            is = inventoryStatusDao.findInvStatusForSale(companyId);
        }
        Date date = new Date();
        Long skuQty = 0l;
        boolean isNoSkuError = false;
        customerId = wareHouseManagerQuery.getCustomerByWhouid(ou.getId());
        for (CacheStockTransferInData inss : ins) {
            if (inss.getSta() != null || inss.getStaLine() != null) {
                log.info("===============this instruction has create STA================");
                throw new BusinessException(ErrorCode.VMI_INSTRUCTION_TYPE_ERROR);
            }
            String skuCode = inss.getSkuCode().toString();
            CacheProductData cachebarcode = cacheProductDataDao.getCCHCPBySkuCode(skuCode);
            // 判断skuCode在系统中是否存在
            Sku sku = skuDao.getByBarcode(cachebarcode.getItemsBarcode(), customerId);
            if (sku == null) {
                baseinfoManager.sendMsgToOmsCreateSku(cachebarcode.getItemsBarcode(), shop.getVmiCode());
                isNoSkuError = true;
                continue;
            }
            log.info("===============SKU {} CREATE SUCCESS ================", new Object[] {skuCode});
            StaLine staLine = new StaLine();
            staLine.setQuantity(inss.getQuantityShipped().longValue());
            staLine.setSku(sku);
            staLine.setCompleteQuantity(0L);// 已执行数量
            staLine.setSta(sta);
            innerShopCode = shop.getCode();

            staLine.setOwner(innerShopCode);
            staLine.setInvStatus(is);
            staLineDao.save(staLine);
            skuQty += staLine.getQuantity();
            inss.setStaLine(staLine);
            inss.setSta(sta);
            inss.setCreateTime(date);
            inss.setStatus("5");
            transferInDao.save(inss);
        }
        if (isNoSkuError) {
            throw new BusinessException(ErrorCode.SKU_NOT_FOUND);
        }
        sta.setSkuQty(skuQty);
        log.info("===============sta {} create success ================", new Object[] {sta.getCode()});
        if (StringUtils.hasText(innerShopCode) && ou != null && is != null) {
            sta.setOwner(innerShopCode);
            sta.setMainWarehouse(ou);
            sta.setMainStatus(is);
            staDao.save(sta);
            staDao.flush();
            staDao.updateSkuQtyById(sta.getId());
        } else {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
    }

    public void generateInboundStaSql(String parcelCode) {
        List<CacheStockTransferInData> ins = transferInDao.getStockInListByParcelCode(parcelCode);
        if (ins.size() < 0) {
            return;
        }
        List<StockTransApplication> stas = staDao.findBySlipCodeVmi(parcelCode);
        if (stas.size() > 0) {
            for (CacheStockTransferInData inData : ins) {
                CacheSinUnnecessary sinUnnecessary = new CacheSinUnnecessary();
                sinUnnecessary.setCreateTime(new Date());
                sinUnnecessary.setParcelCode(parcelCode);
                sinUnnecessary.setQuantityShipped(inData.getQuantityShipped());
                sinUnnecessary.setSkuCode(inData.getSkuCode());
                sinUnnecessary.setSta(inData.getSta());
                sinUnnecessary.setStaLine(inData.getStaLine());
                sinUnnecessary.setStatus(inData.getStatus());
                sinUnnecessary.setStoreCode(inData.getStoreCode());
                sinUnnecessary.setVersion(inData.getVersion());
                sinUnnecessary.setVmiCode(inData.getVmiCode());
                sinUnnecessaryDao.save(sinUnnecessary);
                transferInDao.delete(inData);
            }
        } else {
            StockTransApplication sta = new StockTransApplication();
            sta.setType(StockTransApplicationType.VMI_INBOUND_CONSIGNMENT);
            sta.setBusinessSeqNo(staDao.getBusinessSeqNo(new SingleColumnRowMapper<BigDecimal>()).longValue());
            sta.setCreateTime(new Date());
            sta.setLastModifyTime(new Date());
            sta.setStatus(StockTransApplicationStatus.CREATED);
            // 订单状态与账号关联
            sta.setCode(sequenceManager.getCode(StockTransApplication.class.getName(), sta));
            sta.setRefSlipCode(parcelCode);
            staDao.save(sta);

            OperationUnit ou = null;
            String innerShopCode = null;
            InventoryStatus is = null;
            Long customerId = null;
            CacheStockTransferInData temp = ins.get(0);
            // String vmiCode = temp.getStoreCode().toString();
            String vmiCode = temp.getVmiCode().toString();
            BiChannel shop = shopDao.getByVmiCode(vmiCode);
            if (shop == null) {
                // 抛异常，报错
                throw new BusinessException("没有找到店铺信息!vmiCode:" + vmiCode);
            }
            wmExe.validateBiChannelSupport(null, shop.getCode());
            ou = ouDao.getDefaultInboundWhByShopId(shop.getId());
            if (ou == null) {
                log.info("=========OPERATION UNIT {} NOT FOUNT===========", new Object[] {shop.getVmiCode()});
                throw new BusinessException(ErrorCode.END_OPERATION_UNIT_NOT_FOUNT);
            }
            whInfoTimeRefDao.insertWhInfoTime2(sta.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.CANCELED.getValue(), null, ou.getId());
            if (is == null) {
                Long companyId = null;
                if (ou.getParentUnit() != null) {
                    OperationUnit ou1 = ouDao.getByPrimaryKey(ou.getParentUnit().getId());
                    if (ou1 != null) {
                        companyId = ou1.getParentUnit().getId();
                    }
                }
                is = inventoryStatusDao.findInvStatusForSale(companyId);
            }
            Date date = new Date();
            Long skuQty = 0l;
            // 同sku_code同parcel_code的在创建staline的时候合并数量
            Map<String, StaLine> tempMap = new HashMap<String, StaLine>();
            customerId = wareHouseManagerQuery.getCustomerByWhouid(ou.getId());
            boolean isNoSkuError = false;
            for (CacheStockTransferInData inss : ins) {
                if (inss.getSta() != null || inss.getStaLine() != null) {
                    log.info("===============this instruction has create STA================");
                    throw new BusinessException(ErrorCode.VMI_INSTRUCTION_TYPE_ERROR);
                }

                String skuCode = inss.getSkuCode().toString();
                // CacheProductData cachebarcode = cacheProductDataDao.getCCHCPBySkuCode(skuCode);
                // 判断skuCode在系统中是否存在
                Sku sku = skuDao.getByBarcode(skuCode, customerId);
                if (sku == null) {
                    isNoSkuError = true;
                    baseinfoManager.sendMsgToOmsCreateSku(skuCode, shop.getVmiCode());
                    continue;
                }
                log.debug("SKU {} CREATE SUCCESS", new Object[] {skuCode});
                String key = inss.getParcelCode() + "_" + inss.getSkuCode();
                if (tempMap.get(key) == null) {
                    StaLine staLine = new StaLine();
                    staLine.setQuantity(inss.getQuantityShipped().longValue());
                    staLine.setSku(sku);
                    staLine.setCompleteQuantity(0L);// 已执行数量
                    staLine.setSta(sta);
                    innerShopCode = shop.getCode();
                    staLine.setOwner(innerShopCode);
                    staLine.setInvStatus(is);
                    staLineDao.save(staLine);
                    skuQty += staLine.getQuantity();
                    inss.setStaLine(staLine);
                    tempMap.put(key, staLine);
                } else {
                    StaLine staLine2 = tempMap.get(key);
                    staLine2.setQuantity(staLine2.getQuantity() + inss.getQuantityShipped());
                    inss.setStaLine(staLine2);
                    skuQty += staLine2.getQuantity();
                    staLineDao.save(staLine2);
                }
                inss.setSta(sta);
                inss.setCreateTime(date);
                inss.setStatus("5");
                transferInDao.save(inss);
            }
            if (isNoSkuError) {
                throw new BusinessException(ErrorCode.SKU_NOT_FOUND);
            }
            sta.setSkuQty(skuQty);
            if (StringUtils.hasText(innerShopCode) && ou != null && is != null) {
                sta.setOwner(innerShopCode);
                sta.setMainWarehouse(ou);
                sta.setMainStatus(is);
                staDao.save(sta);
                staDao.flush();
                staDao.updateSkuQtyById(sta.getId());
            } else {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR);
            }
        }
    }

    public Map<String, Object> transferTOmap(CacheProductData cache) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("itemsBarcode", cache.getItemsBarcode());
        map.put("skuCode", cache.getSkuCode());
        map.put("productCode", cache.getProductCode());
        map.put("colorCode", cache.getColorCode());
        map.put("productName", cache.getProductName());
        map.put("description", cache.getDescription());
        map.put("colorName", cache.getColorName());
        map.put("cnSize", cache.getCnSize());
        map.put("familyCn", cache.getFamilyCn());
        map.put("isp", cache.getIsp());
        map.put("standardNumber", cache.getStandardNumber());
        map.put("gbStandard", cache.getGbStandard());
        map.put("carelable11", cache.getCarelable11());
        map.put("carelable12", cache.getCarelable12());
        map.put("carelable13", cache.getCarelable13());
        map.put("carelable14", cache.getCarelable14());
        map.put("carelable15", cache.getCarelable15());
        map.put("madeIn", cache.getMadeIn());
        map.put("mainfabricComposition", cache.getMainfabricComposition());
        map.put("liningComposition", cache.getLiningComposition());
        map.put("paddingComposition", cache.getPaddingComposition());
        return map;
    }

    public Sales getSSSalesFile(Long shopId, String shopCode, Date date) {

        SimpleDateFormat sf1 = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
        SimpleDateFormat sf2 = new SimpleDateFormat("yyyyMMdd");
        String dateTemp = sf2.format(date) + " 00:00:00";
        Date d = null;
        try {
            d = sf1.parse(dateTemp);
        } catch (ParseException e1) {
            if (log.isErrorEnabled()) {
                log.error("getSSSalesFile ParseException:" + shopCode, e1);
            }
        }
        String batchNum = sf2.format(date);
        cchSaleDao.insertDataBySO(shopId, shopCode, d, batchNum);
        cchSaleDao.updateStatusByBatchNum(DataStatus.CREATED.getValue(), DataStatus.EXECUTING.getValue(), batchNum);
        Sales sales = new Sales();
        List<Sales.Sale> sList = new ArrayList<Sales.Sale>();
        List<CchSales> cchSales = cchSaleDao.findSalesByBatchNum(batchNum, DataStatus.EXECUTING.getValue());
        try {
            for (CchSales cs : cchSales) {
                Sales.Sale sale = new Sales.Sale();
                sale.setSalsDate(cs.getTransactionDate());
                sale.setEANCode(cs.getBarCode());
                sale.setSoldQuantity(cs.getSaleQuantity().toString());
                sale.setReturnedQuantity(cs.getReturnQuantity().toString());
                sale.setNetAmountTax((cs.getAmount().multiply(new BigDecimal(100))).setScale(0, BigDecimal.ROUND_DOWN).toString());
                sale.setDiscountAmount((cs.getDiscount().multiply(new BigDecimal(100))).setScale(0, BigDecimal.ROUND_DOWN).toString());
                sale.setReceiptNumber(cs.getCchCode());
                sList.add(sale);
            }
            sales.setSales(sList);
            Sales.SalesTotal sTotal = new Sales.SalesTotal();
            sTotal.setSalsDate(date);
            sTotal.setVistors("0");
            sales.setTotal(sTotal);
            cchSaleDao.updateStatusByBatchNum(DataStatus.EXECUTING.getValue(), DataStatus.FINISHED.getValue(), batchNum);
        } catch (Exception e) {
            log.error("", e);
        }
        return sales;
    }

}
