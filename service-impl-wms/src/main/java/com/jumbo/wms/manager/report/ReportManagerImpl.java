/**
 * 
 * Copyright (c) 2010 Jumbomart All Rights Reserved.
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
package com.jumbo.wms.manager.report;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;

import loxia.dao.support.BeanPropertyRowMapperExt;
import loxia.support.excel.ExcelWriter;
import loxia.support.excel.WriteStatus;
import loxia.utils.DateUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.jumbo.dao.authorization.OperationUnitDao;
import com.jumbo.dao.baseinfo.SkuDao;
import com.jumbo.dao.report.OrderStatusCountCommandDao;
import com.jumbo.dao.report.SalesDataCommandDao;
import com.jumbo.dao.report.SalesDataDetailCommandDao;
import com.jumbo.dao.report.SalesRaDataCommandDao;
import com.jumbo.dao.warehouse.InventoryDao;
import com.jumbo.dao.warehouse.InventoryStatusDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.dao.warehouse.StockTransTxLogDao;
import com.jumbo.dao.warehouse.TransactionTypeDao;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.command.report.OrderStatusCountCommand;
import com.jumbo.wms.model.report.SalesDataCommand;
import com.jumbo.wms.model.report.SalesDataDetailCommand;
import com.jumbo.wms.model.report.SalesRaDataCommand;
import com.jumbo.wms.model.report.SalesReportCommand;
import com.jumbo.wms.model.warehouse.InventoryCommand;
import com.jumbo.wms.model.warehouse.InventoryStatus;
import com.jumbo.wms.model.warehouse.StockTransTxLogCommand;

/**
 * 销售报表导出
 * 
 * @author jumbo
 * 
 */
@Transactional
@Service("reportManager")
public class ReportManagerImpl extends BaseManagerImpl implements ReportManager {

    private static final long serialVersionUID = -8339867628198475693L;
    /**
	 * 
	 */
    @Autowired
    private SalesDataCommandDao salesDataCommandDao;
    @Autowired
    private SalesDataDetailCommandDao salesDataDetailCommandDao;
    @Autowired
    private OperationUnitDao operationUnitDao;
    @Autowired
    private InventoryDao inventoryDao;
    @Autowired
    private SkuDao skuDao;
    @Autowired
    private StockTransTxLogDao stockTransTxLogDao;
    @Autowired
    private InventoryStatusDao inventoryStatusDao;
    @Autowired
    private TransactionTypeDao transactionTypeDao;
    @Autowired
    private SalesRaDataCommandDao salesRaDataCommandDao;
    @Autowired
    private OrderStatusCountCommandDao orderStatusCountCommandDao;
    @Autowired
    private StockTransApplicationDao staDao;

    /**
     * excel writer
     */
    @Resource(name = "reportExportForSalesSumWriter")
    private ExcelWriter reportExportForSalesSumWriter;
    @Resource(name = "reportExportForSalesDetailWriter")
    private ExcelWriter reportExportForSalesDetailWriter;
    @Resource(name = "shopTimeInventoryWriter")
    private ExcelWriter shopTimeInventoryWriter;
    @Resource(name = "lvsInvnetoryLogWriter")
    private ExcelWriter lvsInvnetoryLogWriter;
    @Resource(name = "lvsRaWriter")
    private ExcelWriter lvsRaWriter;
    @Resource(name = "reportExportForShopCurrentInventoryWiter")
    private ExcelWriter reportExportForShopCurrentInventoryWiter;

    /**
     * 店铺 下 的导出 不用改
     */
    public WriteStatus reportExportForSalesSum(ServletOutputStream os, Date fromDate, Date toDate, Long ouid) {
        Map<String, Object> beans = new HashMap<String, Object>();
        beans.put("reportName", REPORT_EXPORT_EXCEL_NAME + DateUtil.format(fromDate, "yyyyMMdd") + "-" + DateUtil.format(toDate, "yyyyMMdd") + ".xsl");
        // beans.put("store",REPORT_EXPORT_SHOP_NAME);
        OperationUnit shop = operationUnitDao.getByPrimaryKey(ouid);
        beans.put("store", shop.getName());
        beans.put("transStartTime", DateUtil.format(fromDate, "yyyy-MM-dd"));
        beans.put("transEndTime", DateUtil.format(toDate, "yyyy-MM-dd"));
        List<SalesDataCommand> sumlist1 = salesDataCommandDao.findSalesDataByDate(fromDate, toDate, ouid, new BeanPropertyRowMapperExt<SalesDataCommand>(SalesDataCommand.class));
        beans.put("salesdatas", sumlist1);
        return reportExportForSalesSumWriter.write(REPORT_EXPORT_FOR_SALES, os, beans);
    }

    /**
     * 店铺 下 的导出 不用改
     */
    public WriteStatus reportExportForLvsRa(ServletOutputStream os, SalesRaDataCommand cmd, Date fromDate, Date toDate, Long ouid) {
        Map<String, Object> beans = new HashMap<String, Object>();
        OperationUnit shop = operationUnitDao.getByPrimaryKey(ouid);


        if (cmd == null) {
            cmd = new SalesRaDataCommand();
        } else {
            if (!StringUtils.hasText(cmd.getProductCode())) {
                cmd.setProductCode(null);
            }
            if (!StringUtils.hasText(cmd.getProductCate())) {
                cmd.setProductCate(null);
            }
            if (!StringUtils.hasText(cmd.getProductLine())) {
                cmd.setProductLine(null);
            }
            if (!StringUtils.hasText(cmd.getConsumerGroup())) {
                cmd.setConsumerGroup(null);
            }
        }
        List<SalesRaDataCommand> list =
                salesRaDataCommandDao.findRaData(fromDate, toDate, cmd.getProductCode(), cmd.getProductCate(), cmd.getProductLine(), cmd.getConsumerGroup(), ouid, new BeanPropertyRowMapperExt<SalesRaDataCommand>(SalesRaDataCommand.class));
        beans.put("inv", list);
        beans.put("shopName", shop.getName());
        beans.put("exportStartTime", DateUtil.format(fromDate, "yyyy-MM-dd"));
        beans.put("exportEndTime", DateUtil.format(DateUtil.addDays(toDate, -1), "yyyy-MM-dd"));
        return lvsRaWriter.write(REPORT_EXPORT_FOR_LEVIS_RA, os, beans);
    }

    public void checkLineForReportExportForLvsInventoryLog(StockTransTxLogCommand cmd, Long ouid) {
        Map<String, Object> beans = new HashMap<String, Object>();
        OperationUnit shop = operationUnitDao.getByPrimaryKey(ouid);
        beans.put("shopName", shop.getName());
        beans.put("exportStartTime", DateUtil.format(cmd.getStockStartTime(), "yyyy-MM-dd"));
        beans.put("exportEndTime", DateUtil.format(DateUtil.addDays(cmd.getStockEndTime(), -1), "yyyy-MM-dd"));
        if (!StringUtils.hasText(cmd.getSupplierSkuCode())) {
            cmd.setSupplierSkuCode(null);
        }
        if (!StringUtils.hasText(cmd.getProductCategory())) {
            cmd.setProductCategory(null);
        }
        if (!StringUtils.hasText(cmd.getProductLine())) {
            cmd.setProductLine(null);
        }
        if (!StringUtils.hasText(cmd.getConsumerGroup())) {
            cmd.setConsumerGroup(null);
        }
        if (!StringUtils.hasText(cmd.getTransactionTypeName())) {
            cmd.setTransactionTypeName(null);
        }
        BigDecimal qty =
                stockTransTxLogDao.findCountLvsStockTransTxLogList(ouid, cmd.getStockStartTime(), cmd.getStockEndTime(), cmd.getTransactionTypeName(), cmd.getInvStatusId(), cmd.getSupplierSkuCode(), cmd.getProductCategory(), cmd.getProductLine(),
                        cmd.getConsumerGroup(), new SingleColumnRowMapper<BigDecimal>());
        Long limitLine = 30001L;
        if (qty.longValue() >= limitLine) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR_EXPORT_LINE_OUTOF_LIMIE, new Object[] {qty});
        }
    }

    @Override
    public List<SalesReportCommand> queryByouId(OperationUnit operationUnit) {
        List<SalesReportCommand> list = null;
        list = salesRaDataCommandDao.findByOuId(operationUnit.getId(), new BeanPropertyRowMapperExt<SalesReportCommand>(SalesReportCommand.class));
        return list;
    }

    /**
     * 店铺 下 的导出 不用改
     */
    public WriteStatus reportExportForLvsInventoryLog(ServletOutputStream os, StockTransTxLogCommand cmd, Long ouid) {
        Map<String, Object> beans = new HashMap<String, Object>();
        OperationUnit shop = operationUnitDao.getByPrimaryKey(ouid);
        beans.put("shopName", shop.getName());
        beans.put("exportStartTime", DateUtil.format(cmd.getStockStartTime(), "yyyy-MM-dd"));
        beans.put("exportEndTime", DateUtil.format(DateUtil.addDays(cmd.getStockEndTime(), -1), "yyyy-MM-dd"));
        if (!StringUtils.hasText(cmd.getSupplierSkuCode())) {
            cmd.setSupplierSkuCode(null);
        }
        if (!StringUtils.hasText(cmd.getProductCategory())) {
            cmd.setProductCategory(null);
        }
        if (!StringUtils.hasText(cmd.getProductLine())) {
            cmd.setProductLine(null);
        }
        if (!StringUtils.hasText(cmd.getConsumerGroup())) {
            cmd.setConsumerGroup(null);
        }
        if (!StringUtils.hasText(cmd.getTransactionTypeName())) {
            cmd.setTransactionTypeName(null);
        }
        List<StockTransTxLogCommand> list =
                stockTransTxLogDao.findLvsStockTransTxLogList(ouid, cmd.getStockStartTime(), cmd.getStockEndTime(), cmd.getTransactionTypeName(), cmd.getInvStatusId(), cmd.getSupplierSkuCode(), cmd.getProductCategory(), cmd.getProductLine(),
                        cmd.getConsumerGroup(), new BeanPropertyRowMapperExt<StockTransTxLogCommand>(StockTransTxLogCommand.class));
        beans.put("inv", list);
        return lvsInvnetoryLogWriter.write(REPORT_EXPORT_FOR_LEVIS_INVENTORY_LOG, os, beans);
    }

    /**
     * 店铺 下 的导出 不用改
     */
    public WriteStatus exportShopCurrentInventory(ServletOutputStream os, Long ouid, InventoryCommand cmd) {
        Map<String, Object> beans = new HashMap<String, Object>();
        OperationUnit shop = operationUnitDao.getByPrimaryKey(ouid);
        beans.put("shopName", shop.getName());
        beans.put("reportName", shop.getName());
        beans.put("exportTime", shop.getName() + DateUtil.format(new Date(), "yyyyMMdd") + ".xls");
        if (cmd == null) {
            cmd = new InventoryCommand();
        } else {
            if (!StringUtils.hasText(cmd.getSupplierSkuCode())) {
                cmd.setSupplierSkuCode(null);
            }
            if (!StringUtils.hasText(cmd.getProductCategory())) {
                cmd.setProductCategory(null);
            }
            if (!StringUtils.hasText(cmd.getProductLine())) {
                cmd.setProductLine(null);
            }
            if (!StringUtils.hasText(cmd.getConsumerGroup())) {
                cmd.setConsumerGroup(null);
            }
        }
        List<InventoryCommand> list = inventoryDao.findLeivsCurrentInv(shop.getId(), cmd.getSupplierSkuCode(), cmd.getProductCategory(), cmd.getProductLine(), cmd.getConsumerGroup(), new BeanPropertyRowMapperExt<InventoryCommand>(InventoryCommand.class));
        beans.put("invs", list);
        return reportExportForShopCurrentInventoryWiter.write(REPORT_EXPORT_FOR_LEVIS_CURRENT_IVN, os, beans);
    }

    /**
     * 店铺 下 的导出 不用改
     */
    public WriteStatus exportShopTimeInventory(ServletOutputStream os, Date date, Long ouid, InventoryCommand cmd) {
        Map<String, Object> beans = new HashMap<String, Object>();
        OperationUnit shop = operationUnitDao.getByPrimaryKey(ouid);
        beans.put("shopName", shop.getName());
        beans.put("exportTime", DateUtil.format(date, "yyyy-MM-dd"));
        beans.put("reportName", shop.getName() + DateUtil.format(date, "yyyyMMdd") + ".xls");
        if (cmd == null) {
            cmd = new InventoryCommand();
        } else {
            if (!StringUtils.hasText(cmd.getSupplierSkuCode())) {
                cmd.setSupplierSkuCode(null);
            }
            if (!StringUtils.hasText(cmd.getProductCategory())) {
                cmd.setProductCategory(null);
            }
            if (!StringUtils.hasText(cmd.getProductLine())) {
                cmd.setProductLine(null);
            }
            if (!StringUtils.hasText(cmd.getConsumerGroup())) {
                cmd.setConsumerGroup(null);
            }
        }

        List<InventoryCommand> list =
                inventoryDao.findSalesReportInvnentory(ouid, date, cmd.getSupplierSkuCode(), cmd.getProductCategory(), cmd.getProductLine(), cmd.getConsumerGroup(), new BeanPropertyRowMapperExt<InventoryCommand>(InventoryCommand.class));
        beans.put("inv", list);
        return shopTimeInventoryWriter.write(REPORT_EXPORT_FOR_TIME_INVENTORY, os, beans);
    }

    /**
     * 店铺 下 的导出 不用改
     */
    public WriteStatus reportExportForSalesDetial(SalesDataDetailCommand cmd, ServletOutputStream os, Date fromDate, Date toDate, String supplierSkuCode, String pomotionCode, Long ouid) {
        Map<String, Object> beans = new HashMap<String, Object>();
        OperationUnit shop = operationUnitDao.getByPrimaryKey(ouid);
        beans.put("store", shop.getName());
        beans.put("transStartTime", DateUtil.format(fromDate, "yyyy-MM-dd"));
        beans.put("transEndTime", DateUtil.format(toDate, "yyyy-MM-dd"));
        if (!StringUtils.hasText(supplierSkuCode)) {
            supplierSkuCode = null;
        }
        if (!StringUtils.hasText(pomotionCode)) {
            pomotionCode = null;
        }
        if (cmd == null) {
            cmd = new SalesDataDetailCommand();
        } else {
            if (!StringUtils.hasText(cmd.getProductCate())) {
                cmd.setProductCate(null);
            }
            if (!StringUtils.hasText(cmd.getProductLine())) {
                cmd.setProductLine(null);
            }
            if (!StringUtils.hasText(cmd.getConsumerGroup())) {
                cmd.setConsumerGroup(null);
            }
        }

        List<SalesDataDetailCommand> list = getSalesDataDetail(cmd, fromDate, toDate, supplierSkuCode, pomotionCode, ouid);
        beans.put("salesdatas", list);
        return reportExportForSalesDetailWriter.write(REPORT_EXPORT_FOR_SALES_DETIAL, os, beans);
    }

    private List<SalesDataDetailCommand> getSalesDataDetail(SalesDataDetailCommand cmd, Date fromDate, Date toDate, String productCode, String promotion, Long ouid) {
        // 原始数据
        List<SalesDataDetailCommand> dataList =
                salesDataDetailCommandDao.findSalesDataDetail(fromDate, toDate, productCode, promotion, cmd.getProductCate(), cmd.getProductLine(), cmd.getConsumerGroup(), ouid, new BeanPropertyRowMapperExt<SalesDataDetailCommand>(
                        SalesDataDetailCommand.class));
        // 整理后最终结果数据
        List<SalesDataDetailCommand> resultList = new ArrayList<SalesDataDetailCommand>();
        // 特殊商品inseam必须是-
        Map<String, String> spcPrdMap = new HashMap<String, String>();
        spcPrdMap.put("04824-0001", "04824-0001");
        spcPrdMap.put("04824-0002", "04824-0002");
        spcPrdMap.put("04826-0001", "04826-0001");
        spcPrdMap.put("04826-0002", "04826-0002");
        spcPrdMap.put("04842-0001", "04842-0001");
        spcPrdMap.put("04842-0002", "04842-0002");
        spcPrdMap.put("04999-0001", "04999-0001");
        spcPrdMap.put("04999-0002", "04999-0002");
        spcPrdMap.put("02373-0003", "02373-0003");
        spcPrdMap.put("02373-0001", "02373-0001");
        spcPrdMap.put("04426-0002", "04426-0002");
        // spcPrdMap.put("TA115-0001", "TA115-0001");
        // spcPrdMap.put("TA115-0002", "TA115-0002");
        spcPrdMap.put("04413-0003", "04413-0003");
        spcPrdMap.put("04410-0002", "04410-0002");
        spcPrdMap.put("04426-0001", "04426-0001");
        // 存放单号map
        Map<String, String> codeMap = new HashMap<String, String>();
        String transFeeCode1 = "GWPTB00002M";
        String transFeeCode2 = "GWPTB00004M";
        String pointCode = "VDA00064";
        if (dataList != null) {
            for (SalesDataDetailCommand detailCommand : dataList) {
                String oriTransCode = detailCommand.getTransCode();
                Date payTime = detailCommand.getPayTime();
                String transCode = oriTransCode;
                String outerCode = detailCommand.getPfTransNum();
                Date transDate = detailCommand.getTransDate();
                String prdCode = detailCommand.getProductCode();
                String kp = detailCommand.getKeyProperties();
                String productDesc = detailCommand.getProductDesc();
                String productCate = detailCommand.getProductCate();
                String productLine = detailCommand.getProductLine();
                String consumerGroup = detailCommand.getConsumerGroup();
                String productName = detailCommand.getProductName();
                String warehouseName = detailCommand.getWarehouseName();
                String size = null;
                String inseam = null;

                if (kp != null && kp.contains(" ")) {
                    size = kp.split(" ")[0];
                    inseam = kp.split(" ")[1];
                } else {
                    size = kp;
                    if (spcPrdMap.containsKey(prdCode)) {
                        inseam = "-";
                    }
                }

                // 运费行，如果未计算过则加入
                if (!codeMap.containsKey(oriTransCode)) {
                    SalesDataDetailCommand transFeeCommand = new SalesDataDetailCommand();
                    transFeeCommand.setTransDate(transDate);
                    transFeeCommand.setTransCode(transCode);
                    transFeeCommand.setPfTransNum(outerCode);
                    if (detailCommand.getTransferFee().compareTo(new BigDecimal(0)) > 0) {
                        transFeeCommand.setProductCode(transFeeCode1);
                        transFeeCommand.setTransferFee(detailCommand.getTransferFee());
                    } else {
                        transFeeCommand.setProductCode(transFeeCode2);
                        transFeeCommand.setTransferFee(new BigDecimal(12));
                    }
                    transFeeCommand.setQuantity(1);
                    transFeeCommand.setTransType("运费");
                    resultList.add(transFeeCommand);
                    codeMap.put(oriTransCode, oriTransCode);
                }
                // 商品行
                SalesDataDetailCommand productCommand = new SalesDataDetailCommand();
                productCommand.setTransDate(transDate);
                productCommand.setTransCode(transCode);
                productCommand.setPfTransNum(outerCode);
                productCommand.setProductCode(prdCode);
                productCommand.setPayTime(payTime);
                productCommand.setSize(size);
                productCommand.setInseam(inseam);
                productCommand.setTransType("正常销售");
                productCommand.setProductName(productName);
                productCommand.setProductCate(productCate);
                productCommand.setProductDesc(productDesc);
                productCommand.setProductLine(productLine);
                productCommand.setWarehouseName(warehouseName);
                productCommand.setConsumerGroup(consumerGroup);
                productCommand.setQuantity(detailCommand.getQuantity());
                productCommand.setMdPrice(detailCommand.getMdPrice());

                double point = 0.0;
                double point2 = 1.0;
                if (detailCommand.getShopId() != null && detailCommand.getShopId().equals(601L)) {
                    point = 0.005;
                    point2 = 0.995;
                }

                BigDecimal actualPrice = detailCommand.getUnitPrice().subtract(detailCommand.getDiscountFee().divide(new BigDecimal(detailCommand.getQuantity()), 3, BigDecimal.ROUND_HALF_UP)).setScale(2, BigDecimal.ROUND_HALF_UP);
                BigDecimal pointValue = actualPrice.multiply(new BigDecimal(detailCommand.getQuantity())).multiply(new BigDecimal(point)).setScale(2, BigDecimal.ROUND_DOWN);
                BigDecimal posPrice = actualPrice.subtract(pointValue.divide(new BigDecimal(detailCommand.getQuantity()), 2, BigDecimal.ROUND_HALF_UP));
                BigDecimal posAmt = posPrice.multiply(new BigDecimal(detailCommand.getQuantity()));
                productCommand.setPosPrice(posPrice);
                productCommand.setPosAmt(posAmt);
                productCommand.setActualPrice(detailCommand.getActualPrice());
                productCommand.setActualAmt(detailCommand.getActualPrice().multiply(new BigDecimal(detailCommand.getQuantity())).multiply(new BigDecimal(point2)).setScale(2, BigDecimal.ROUND_UP));

                resultList.add(productCommand);


                if (detailCommand.getUnitPrice().compareTo(new BigDecimal(0)) > 0) {

                    if (detailCommand.getShopId() != null && detailCommand.getShopId().equals(601L)) {
                        // 返点行
                        SalesDataDetailCommand pointCommand = new SalesDataDetailCommand();
                        pointCommand.setTransDate(transDate);
                        pointCommand.setTransCode(transCode);
                        pointCommand.setPfTransNum(outerCode);
                        pointCommand.setProductCode(prdCode);
                        pointCommand.setSize(size);
                        pointCommand.setInseam(inseam);
                        pointCommand.setTransType("积分返点");
                        pointCommand.setProductName(productName);
                        pointCommand.setProductCate(productCate);
                        pointCommand.setProductDesc(productDesc);
                        pointCommand.setProductLine(productLine);
                        pointCommand.setPayTime(payTime);
                        pointCommand.setConsumerGroup(consumerGroup);
                        pointCommand.setPromotionCode(pointCode);
                        pointCommand.setWarehouseName(warehouseName);
                        pointCommand.setDiscount(pointValue);
                        resultList.add(pointCommand);

                    }


                    // 行促销折扣行
                    if (detailCommand.getLinePromotionCode() != null && !detailCommand.getLinePromotionCode().equals("")) {
                        SalesDataDetailCommand lineDisCommand = new SalesDataDetailCommand();
                        lineDisCommand.setTransDate(transDate);
                        lineDisCommand.setTransCode(transCode);
                        lineDisCommand.setPfTransNum(outerCode);
                        lineDisCommand.setProductCode(prdCode);
                        lineDisCommand.setSize(size);
                        lineDisCommand.setInseam(inseam);
                        lineDisCommand.setTransType("商品折扣");
                        lineDisCommand.setProductName(productName);
                        lineDisCommand.setProductCate(productCate);
                        lineDisCommand.setPayTime(payTime);
                        lineDisCommand.setProductDesc(productDesc);
                        lineDisCommand.setProductLine(productLine);
                        lineDisCommand.setWarehouseName(warehouseName);
                        lineDisCommand.setConsumerGroup(consumerGroup);
                        lineDisCommand.setPromotionCode(detailCommand.getLinePromotionCode());
                        lineDisCommand.setDiscount(detailCommand.getMdPrice().multiply(new BigDecimal(detailCommand.getQuantity())).subtract(detailCommand.getUnitPrice().multiply(new BigDecimal(detailCommand.getQuantity()))));
                        resultList.add(lineDisCommand);
                    }
                    // 整单促销折扣行
                    if (detailCommand.getPromotionCode() != null && !detailCommand.getPromotionCode().equals("")) {
                        SalesDataDetailCommand disCommand = new SalesDataDetailCommand();
                        disCommand.setTransDate(transDate);
                        disCommand.setTransCode(transCode);
                        disCommand.setPfTransNum(outerCode);
                        disCommand.setProductCode(prdCode);
                        disCommand.setSize(size);
                        disCommand.setInseam(inseam);
                        disCommand.setTransType("整单折扣");
                        disCommand.setProductName(productName);
                        disCommand.setProductCate(productCate);
                        disCommand.setProductDesc(productDesc);
                        disCommand.setPayTime(payTime);
                        disCommand.setWarehouseName(warehouseName);
                        disCommand.setProductLine(productLine);
                        disCommand.setConsumerGroup(consumerGroup);
                        disCommand.setPromotionCode(detailCommand.getPromotionCode());
                        disCommand.setDiscount(detailCommand.getUnitPrice().multiply(new BigDecimal(detailCommand.getQuantity())).subtract(detailCommand.getActualPrice().multiply(new BigDecimal(detailCommand.getQuantity()))));
                        resultList.add(disCommand);
                    }
                }
            }

        }
        return resultList;
    }

    public List<OrderStatusCountCommand> findOrderStatusByOuId(Long ouid, String startdate, String enddate) {
        return orderStatusCountCommandDao.findOrderStatusByOuId(ouid, startdate, enddate);
    }

    public List<OrderStatusCountCommand> findTransOrderStatusByOuId(Long ouid, String startdate, String enddate) {
        return orderStatusCountCommandDao.findTransOrderStatusByOuId(ouid, startdate, enddate);
    }

    public List<String> findTransTypeByShop(Long ouid) {
        return transactionTypeDao.findNameByShop(ouid, new SingleColumnRowMapper<String>(String.class));
    }

    public List<InventoryStatus> findInventoryStatusByShop(Long cmpouid) {
        return inventoryStatusDao.findInvStatusListByCompany(cmpouid, null);
    }

    public List<String> findProductCategoryList() {
        return skuDao.findLvsSkuInfoByProductCategory(new SingleColumnRowMapper<String>(String.class));
    }

    public List<String> findProductLineList() {
        return skuDao.findLvsSkuInfoByProductLine(new SingleColumnRowMapper<String>(String.class));
    }

    public List<String> findConsumerGroupList() {
        return skuDao.findLvsSkuInfoByConsumerGroup(new SingleColumnRowMapper<String>(String.class));
    }

    public ExcelWriter getShopTimeInventoryWriter() {
        return shopTimeInventoryWriter;
    }

    public void setShopTimeInventoryWriter(ExcelWriter shopTimeInventoryWriter) {
        this.shopTimeInventoryWriter = shopTimeInventoryWriter;
    }

    public ExcelWriter getLvsInvnetoryLogWriter() {
        return lvsInvnetoryLogWriter;
    }

    public void setLvsInvnetoryLogWriter(ExcelWriter lvsInvnetoryLogWriter) {
        this.lvsInvnetoryLogWriter = lvsInvnetoryLogWriter;
    }

    public ExcelWriter getReportExportForSalesSumWriter() {
        return reportExportForSalesSumWriter;
    }

    public void setReportExportForSalesSumWriter(ExcelWriter reportExportForSalesSumWriter) {
        this.reportExportForSalesSumWriter = reportExportForSalesSumWriter;
    }

    public ExcelWriter getReportExportForSalesDetailWriter() {
        return reportExportForSalesDetailWriter;
    }

    public void setReportExportForSalesDetailWriter(ExcelWriter reportExportForSalesDetailWriter) {
        this.reportExportForSalesDetailWriter = reportExportForSalesDetailWriter;
    }

    public ExcelWriter getLvsRaWriter() {
        return lvsRaWriter;
    }

    public void setLvsRaWriter(ExcelWriter lvsRaWriter) {
        this.lvsRaWriter = lvsRaWriter;
    }

    public ExcelWriter getReportExportForShopCurrentInventoryWiter() {
        return reportExportForShopCurrentInventoryWiter;
    }

    public void setReportExportForShopCurrentInventoryWiter(ExcelWriter reportExportForShopCurrentInventoryWiter) {
        this.reportExportForShopCurrentInventoryWiter = reportExportForShopCurrentInventoryWiter;
    }
}
