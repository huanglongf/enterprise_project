package com.jumbo.wms.manager.task.vmi;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;

import com.jumbo.wms.Constants;
import com.jumbo.dao.vmi.philipsData.PhilipsCustomerReturnDao;
import com.jumbo.dao.vmi.philipsData.PhilipsCustomerReturnReceiptDao;
import com.jumbo.dao.vmi.philipsData.PhilipsGoodsIssueDao;
import com.jumbo.dao.vmi.philipsData.PhilipsGoodsMovementDao;
import com.jumbo.dao.vmi.philipsData.PhilipsGoodsReceiptDao;
import com.jumbo.dao.vmi.philipsData.PhilipsOutboundDeliveryDao;
import com.jumbo.dao.vmi.philipsData.PhilipsStockComparisonDao;
import com.jumbo.dao.vmi.philipsData.PhilipsStockComparisonLineDao;
import com.jumbo.dao.warehouse.BiChannelDao;
import com.jumbo.dao.warehouse.InventoryDao;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.mq.MqManager;
import com.jumbo.wms.manager.vmi.VmiFactory;
import com.jumbo.wms.manager.vmi.VmiInterface;
import com.jumbo.wms.manager.vmi.philipsData.PhilipsParseDataManager;
import com.jumbo.wms.model.DataStatus;
import com.jumbo.wms.model.SlipType;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.vmi.philipsData.PhilipsCustomerReturn;
import com.jumbo.wms.model.vmi.philipsData.PhilipsCustomerReturnReceipt;
import com.jumbo.wms.model.vmi.philipsData.PhilipsGoodsIssue;
import com.jumbo.wms.model.vmi.philipsData.PhilipsGoodsMovement;
import com.jumbo.wms.model.vmi.philipsData.PhilipsGoodsReceipt;
import com.jumbo.wms.model.vmi.philipsData.PhilipsOutboundDelivery;
import com.jumbo.wms.model.vmi.philipsData.PhilipsStockComparison;
import com.jumbo.wms.model.vmi.philipsData.PhilipsStockComparisonLine;
import com.jumbo.wms.model.warehouse.InventoryCommand;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationType;

@Service("philipsTask")
public class PhilipsTask extends BaseManagerImpl {
    /**
	 * 
	 */
    private static final long serialVersionUID = -3850211807303074150L;
    @Autowired
    private PhilipsOutboundDeliveryDao outDao;
    @Autowired
    private PhilipsParseDataManager phManager;
    @Autowired
    private PhilipsCustomerReturnDao crDao;
    @Autowired
    private InventoryDao inventoryDao;
    @Autowired
    private PhilipsStockComparisonLineDao pscLineDao;
    @Autowired
    private BiChannelDao shopDao;
    @Autowired
    private PhilipsStockComparisonDao pscDao;
    @Autowired
    private PhilipsGoodsIssueDao pgiDao;
    @Autowired
    private PhilipsGoodsReceiptDao pgrDao;
    @Autowired
    private PhilipsCustomerReturnReceiptDao pcrrDao;
    @Autowired
    private PhilipsGoodsMovementDao pgmDao;
    @Autowired
    private MqManager mmManager;
    @Autowired
    private VmiFactory f;
    
    public static final Long PHILIPS_OUT_SHOP_ID = 0L;

    /**
     * 创VMI入库单
     */
    public void generateInbountSta() {
        VmiInterface vmiPhilips = f.getBrandVmi("philips");
        vmiPhilips.generateInboundSta();
    }

    /**
     * 创建Philips销售出库作业单
     */
    public void generateSaleOutboundSta() {
        log.debug("===================PHILIPS Create Sales outbound sta start===========");
        List<PhilipsOutboundDelivery> outList = outDao.getOutboundByStatus(DataStatus.CREATED.getValue());
        for (PhilipsOutboundDelivery outDel : outList) {
            try {
                StockTransApplication sta = new StockTransApplication();
                sta.setType(StockTransApplicationType.OUT_SALES_ORDER_OUTBOUND_SALES);
                sta.setRefSlipCode(outDel.getOrderCode());
                sta.setRefSlipType(SlipType.SALES_ORDER);
                sta = phManager.createPhilipsSalesSta(sta, outDel, Constants.PHILIPS_VMI_CODE);
                outDel.setSta(sta);
                outDao.save(outDel);
                outDao.updateOutBoundStatus(outDel.getId(), DataStatus.FINISHED.getValue(), null);
            } catch (Exception e) {
                log.error("",e);
                String errorInfo = "";
                if (e instanceof BusinessException) {
                    BusinessException error = (BusinessException) e;
                    StringBuffer sb = new StringBuffer();
                    if (error.getLinkedException() == null) {
                        sb.append(error.getMessage());
                    } else {
                        BusinessException be = error;
                        while ((be = be.getLinkedException()) != null) {
                            sb.append(be.getMessage());
                        }
                    }
                    if (sb.length() > 500) {
                        errorInfo = sb.substring(0, 490) + "......";
                    } else {
                        errorInfo = sb.toString();
                    }
                } else {
                    errorInfo = "System error!";
                }
                outDao.updateOutBoundStatus(outDel.getId(), DataStatus.ERROR.getValue(), errorInfo);
                log.error("Create Philips so(CODE:" + outDel.getOrderCode() + " ) error!");
            }
        }
    }

    /**
     * 创建Philips退换货入库
     */
    public void generateReturnInboundSta() {
        log.debug("===================PHILIPS Create Sales inbound sta start===========");
        List<PhilipsCustomerReturn> crList = crDao.getCRByStatus(DataStatus.CREATED.getValue());
        for (PhilipsCustomerReturn cr : crList) {
            StockTransApplication sta = new StockTransApplication();
            sta.setType(StockTransApplicationType.INBOUND_RETURN_REQUEST);
            sta.setRefSlipCode(cr.getOrderCode());
            sta.setRefSlipType(SlipType.OUT_RETURN_REQUEST);
            sta = phManager.createPhilipsReturnSta(sta, cr, Constants.PHILIPS_VMI_CODE);
            cr.setSta(sta);
            crDao.save(cr);
            crDao.updateCRStatusByID(cr.getId(), DataStatus.FINISHED.getValue(), null);
        }
    }

    /**
     * 将库存数据插入到中间表
     * 
     * @param vmiCode
     */
    public void generateStockSnapshot(String vmiCode) {
        if (vmiCode == null) {
            return;
        }
        BiChannel shop = shopDao.getByVmiCode(vmiCode);
        if (shop != null) {
            PhilipsStockComparison psc = new PhilipsStockComparison();
            Date date = new Date();
            psc.setCreateTime(date);
            psc.setStatus(DataStatus.CREATED.getValue());
            psc.setTransTime(date);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            psc.setTransCode(sdf.format(date));
            psc = pscDao.save(psc);
            PhilipsStockComparisonLine pscLine = null;
            List<InventoryCommand> inventotyList = inventoryDao.findCurrentPhilipsInventory(shop.getId(), new BeanPropertyRowMapper<InventoryCommand>(InventoryCommand.class));
            Integer i = 0;
            for (InventoryCommand ic : inventotyList) {
                i++;
                pscLine = new PhilipsStockComparisonLine();
                pscLine.setSkuCode(ic.getBarCode());
                pscLine.setBarcode(ic.getExtCode3());
                pscLine.setLineNumber(i.toString());
                pscLine.setLocation(ic.getDistrictCode());
                pscLine.setStatusType(""); // 待定
                pscLine.setQuantity(new BigDecimal(ic.getQuantity()));
                pscLine.setPhilipsStockComparison(psc);
                pscLineDao.save(pscLine);
            }
        }
    }

    /**
     * 销售出库反馈
     */
    public void mqSendGoodsIssue() {
        List<PhilipsGoodsIssue> pGoodsIssues = pgiDao.getPhilipsGoodsIssuesNoBatchId(new BeanPropertyRowMapper<PhilipsGoodsIssue>(PhilipsGoodsIssue.class));
        for (PhilipsGoodsIssue pGoodsIssue : pGoodsIssues) {
            mmManager.sendMqPhilipsGoodsIssue(pGoodsIssue, "oms2bh_philips_goods_issue_test", PHILIPS_OUT_SHOP_ID);
        }
    }

    /**
     * 大仓发货的收货确认
     */
    public void mqSendGoodsReceipt() {
        List<PhilipsGoodsReceipt> pGoodsReceipts = pgrDao.getGoodsReceiptsNoBatchId(new BeanPropertyRowMapper<PhilipsGoodsReceipt>(PhilipsGoodsReceipt.class));
        for (PhilipsGoodsReceipt pGoodsReceipt : pGoodsReceipts) {
            mmManager.sendMqPhilipsGoodsReceipt(pGoodsReceipt, "oms2bh_philips_goods_receipt_test", PHILIPS_OUT_SHOP_ID);
        }
    }

    /**
     * 客户退货的收货确认
     */
    public void mqSendGoodsCustomerReceiptReturn() {
        List<PhilipsCustomerReturnReceipt> pCustomerReturnReceipts = pcrrDao.getpCustomerReturnReceiptNoBatchId(new BeanPropertyRowMapper<PhilipsCustomerReturnReceipt>(PhilipsCustomerReturnReceipt.class));
        for (PhilipsCustomerReturnReceipt pCustomerReturnReceipt : pCustomerReturnReceipts) {
            mmManager.sendMqPhilipsGoodsReceiptReturn(pCustomerReturnReceipt, "oms2bh_philips_goods_receipt_customer_return_test", PHILIPS_OUT_SHOP_ID);
        }
    }

    /**
     * 库存状态变更
     */
    public void mqSendGoodsMovement() {
        List<PhilipsGoodsMovement> pGoodsMovements = pgmDao.getpGoodsMovementsNoBatchId(new BeanPropertyRowMapper<PhilipsGoodsMovement>(PhilipsGoodsMovement.class));
        for (PhilipsGoodsMovement pGoodsMovement : pGoodsMovements) {
            mmManager.sendMqPhilipsGoodsMovement(pGoodsMovement, "oms2bh_philips_goods_movement_test", PHILIPS_OUT_SHOP_ID);
        }
    }

    /**
     * 在库库存数据
     */
    public void mqSendStockComparison() {
        List<PhilipsStockComparison> pStockComparisons = pscDao.getStockComparisonsNoBatchId(new BeanPropertyRowMapper<PhilipsStockComparison>(PhilipsStockComparison.class));
        for (PhilipsStockComparison pStockComparison : pStockComparisons) {
            mmManager.sendMqPhilipsGoodsStockComparison(pStockComparison, "oms2bh_philips_stock_comparison_test", PHILIPS_OUT_SHOP_ID);
        }
    }
}
