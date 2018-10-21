package com.jumbo.wms.manager.vmi;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import loxia.dao.support.BeanPropertyRowMapperExt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;

import com.jumbo.wms.Constants;
import com.jumbo.dao.baseinfo.SkuDao;
import com.jumbo.dao.vmi.philipsData.PhilipsCustomerReturnDao;
import com.jumbo.dao.vmi.philipsData.PhilipsCustomerReturnLineDao;
import com.jumbo.dao.vmi.philipsData.PhilipsCustomerReturnReceiptDao;
import com.jumbo.dao.vmi.philipsData.PhilipsCustomerReturnReceiptLineDao;
import com.jumbo.dao.vmi.philipsData.PhilipsGoodsIssueDao;
import com.jumbo.dao.vmi.philipsData.PhilipsGoodsIssueLineDao;
import com.jumbo.dao.vmi.philipsData.PhilipsGoodsMovementDao;
import com.jumbo.dao.vmi.philipsData.PhilipsGoodsMovementLineDao;
import com.jumbo.dao.vmi.philipsData.PhilipsGoodsReceiptDao;
import com.jumbo.dao.vmi.philipsData.PhilipsGoodsReceiptLineDao;
import com.jumbo.dao.vmi.philipsData.PhilipsInboundDeliveryDao;
import com.jumbo.dao.vmi.philipsData.PhilipsOutboundDeliveryDao;
import com.jumbo.dao.vmi.philipsData.PhilipsOutboundDeliveryLineDao;
import com.jumbo.dao.warehouse.StaDeliveryInfoDao;
import com.jumbo.dao.warehouse.StaLineDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.dao.warehouse.StvLineDao;
import com.jumbo.wms.manager.vmi.philipsData.PhilipsParseDataManager;
import com.jumbo.wms.model.DataStatus;
import com.jumbo.wms.model.command.SkuCommand;
import com.jumbo.wms.model.vmi.philipsData.PhilipsCustomerReturn;
import com.jumbo.wms.model.vmi.philipsData.PhilipsCustomerReturnLine;
import com.jumbo.wms.model.vmi.philipsData.PhilipsCustomerReturnReceipt;
import com.jumbo.wms.model.vmi.philipsData.PhilipsCustomerReturnReceiptLine;
import com.jumbo.wms.model.vmi.philipsData.PhilipsGoodsIssue;
import com.jumbo.wms.model.vmi.philipsData.PhilipsGoodsIssueLine;
import com.jumbo.wms.model.vmi.philipsData.PhilipsGoodsMovement;
import com.jumbo.wms.model.vmi.philipsData.PhilipsGoodsMovementLine;
import com.jumbo.wms.model.vmi.philipsData.PhilipsGoodsReceipt;
import com.jumbo.wms.model.vmi.philipsData.PhilipsGoodsReceiptLine;
import com.jumbo.wms.model.vmi.philipsData.PhilipsInboundDelivery;
import com.jumbo.wms.model.vmi.philipsData.PhilipsOutboundDelivery;
import com.jumbo.wms.model.vmi.philipsData.PhilipsOutboundDeliveryLine;
import com.jumbo.wms.model.warehouse.InventoryCheck;
import com.jumbo.wms.model.warehouse.StaDeliveryInfo;
import com.jumbo.wms.model.warehouse.StaLineCommand;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationStatus;
import com.jumbo.wms.model.warehouse.StockTransApplicationType;
import com.jumbo.wms.model.warehouse.StockTransVoucher;
import com.jumbo.wms.model.warehouse.StvLineCommand;
import com.jumbo.wms.model.warehouse.TransactionDirection;



public class VmiPhilips extends VmiBaseBrand {

    private static final long serialVersionUID = 6348626215006563201L;
    @Autowired
    private SkuDao skuDao;
    @Autowired
    private PhilipsGoodsReceiptLineDao grLineDao;
    @Autowired
    private StaDeliveryInfoDao sdiDao;
    @Autowired
    private StaLineDao staLineDao;
    @Autowired
    private PhilipsOutboundDeliveryDao outDao;
    @Autowired
    private PhilipsOutboundDeliveryLineDao odLineDao;
    @Autowired
    private PhilipsGoodsIssueLineDao giLineDao;
    @Autowired
    private PhilipsGoodsIssueDao giDao;
    @Autowired
    private PhilipsCustomerReturnReceiptDao crReceiveDao;
    @Autowired
    private PhilipsCustomerReturnReceiptLineDao crReceiveLineDao;
    @Autowired
    private PhilipsCustomerReturnDao crDao;
    @Autowired
    private PhilipsCustomerReturnLineDao crLineDao;
    @Autowired
    private PhilipsInboundDeliveryDao idDao;
    @Autowired
    private StvLineDao stvLineDao;
    @Autowired
    private PhilipsGoodsMovementDao gmDao;
    @Autowired
    private PhilipsGoodsMovementLineDao gmLineDao;
    @Autowired
    private PhilipsParseDataManager ppdManager;
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private PhilipsGoodsReceiptDao pgrDao;



    @Override
    public SkuCommand findSkuCommond(String skuCode) {
        return skuDao.findSKUCommandFromPhilipsData(skuCode, new BeanPropertyRowMapperExt<SkuCommand>(SkuCommand.class));
    }

    public void generateReceivingWhenFinished(StockTransApplication sta) {
        if (sta == null || sta.getStatus() != StockTransApplicationStatus.FINISHED || !sta.getType().equals(StockTransApplicationType.VMI_INBOUND_CONSIGNMENT)) {
            return;
        }
        Date date = new Date();
        PhilipsGoodsReceipt gr = new PhilipsGoodsReceipt();
        gr.setStatus(DataStatus.CREATED.getValue());
        gr.setCreateTime(date);
        gr.setDeliveryNo(sta.getSlipCode1());
        gr.setInboundCode(sta.getRefSlipCode());
        gr.setReceiveTime(date);
        gr.setReceiptCode(pgrDao.findReceiptCodeSequence(new SingleColumnRowMapper<BigDecimal>()).toString()); // 序列
        pgrDao.save(gr);
        List<PhilipsGoodsReceiptLine> grLines = grLineDao.getGRLineBySlipCode(sta.getRefSlipCode(), sta.getId(), new BeanPropertyRowMapperExt<PhilipsGoodsReceiptLine>(PhilipsGoodsReceiptLine.class));
        String skuCode = null;
        BigDecimal tqty = null;
        for (PhilipsGoodsReceiptLine grLine : grLines) {
            grLine.setPhilipsGoodsReceipt(gr);
            if (skuCode == null || !skuCode.equals(grLine.getSkuCode())) {
                skuCode = grLine.getSkuCode();
                tqty = grLine.getReceivedQty();
            }
            log.debug("SKU Code ： " + skuCode);
            log.debug("tqty : " + tqty);
            if (tqty.intValue() <= 0) {
                grLine.setReceivedQty(BigDecimal.ZERO);
                log.debug("tqty <= 0 continue");
                grLineDao.save(grLine);
                continue;
            }
            if (grLine.getPlanQuantity().longValue() > tqty.longValue()) {
                grLine.setReceivedQty(tqty);
                tqty = BigDecimal.ZERO;
                log.debug("final tqty : {}", tqty);
            } else {
                grLine.setReceivedQty(grLine.getPlanQuantity());
                tqty = tqty.subtract(grLine.getPlanQuantity());
            }
            grLineDao.save(grLine);
        }
        sta.setVmiRCStatus(Boolean.TRUE);
        staDao.save(sta);
    }

    /**
     * 销售出库反馈
     * 
     * @param sta
     */
    public void generateSalesOutboundFeedBack(StockTransApplication sta) {
        if (sta == null || !sta.getType().equals(StockTransApplicationType.OUT_SALES_ORDER_OUTBOUND_SALES)) {
            return;
        }
        String temp[] = sta.getRefSlipCode().split("_");
        String orderCode = "";
        if (temp.length == 2) {
            orderCode = temp[1];
        }
        PhilipsOutboundDelivery outDelivery = outDao.getOutboundByOrderCode(orderCode);
        Date date = new Date();
        PhilipsGoodsIssue gi = new PhilipsGoodsIssue();
        gi.setCreateTime(date);
        gi.setDespatchTime(sta.getOutboundTime());
        gi.setStatus(DataStatus.CREATED.getValue());
        gi.setOrderCode(orderCode);
        StaDeliveryInfo sdi = sdiDao.getByPrimaryKey(sta.getStaDeliveryInfo().getId());
        if (sdi != null) {
            gi.setTrackingNo(sdi.getTrackingNo());
            gi.setLogisticCode(sdi.getLpCode());
        }
        giDao.save(gi);

        List<PhilipsOutboundDeliveryLine> lines = odLineDao.getOutboundLineByOutId(outDelivery.getId());
        PhilipsGoodsIssueLine giLine = null;
        for (PhilipsOutboundDeliveryLine line : lines) {
            giLine = new PhilipsGoodsIssueLine();
            giLine.setCreateTime(date);
            giLine.setLineNumber(line.getLineNumber());
            giLine.setAcceptedQty(line.getQuantity());
            giLine.setReceivedQty(line.getQuantity());
            giLine.setDepatchedQty(line.getQuantity());
            giLine.setSkuCode(line.getSkuCode());
            giLine.setBarcode(line.getBarcode());

            // Sku sku = null;
            // Sku sku = skuDao.findSkuByBarCode(line.getSkuCode());
            // if (sku != null) {
            // Product product = productDao.getByPrimaryKey(sku.getProduct().getId());
            // if (product != null) {
            // giLine.setTotalGrossWeight(product.getGrossWeight().multiply(line.getQuantity())); //
            // 待定
            // giLine.setUnitNetWeight(product.getGrossWeight());
            // giLine.setGrossVolume(product.getLength().multiply(product.getWidth()).multiply(product.getHeight()));
            // }
            // }
            giLine.setpGoodsIssue(gi);
            giLineDao.save(giLine);
        }
    }

    /**
     * 退货收货反馈
     * 
     * @param sta
     */
    public void generateReturnInboundFeedBack(StockTransApplication sta) {
        if (sta == null || !sta.getType().equals(StockTransApplicationType.INBOUND_RETURN_REQUEST)) {
            return;
        }
        String temp[] = sta.getRefSlipCode().split("_");
        String orderCode = "";
        if (temp.length == 2) {
            orderCode = temp[1];
        }
        PhilipsCustomerReturn cr = crDao.getCRByOrderCode(orderCode);
        PhilipsCustomerReturnReceipt crReceipt = new PhilipsCustomerReturnReceipt();
        Date date = new Date();
        crReceipt.setCreateTime(date);
        crReceipt.setOrderCode(orderCode);
        crReceipt.setReceivedTime(sta.getInboundTime());
        crReceipt.setStatus(DataStatus.CREATED.getValue());
        crReceiveDao.save(crReceipt);
        List<PhilipsCustomerReturnLine> crLines = crLineDao.getCRLineByCRID(cr.getId());
        PhilipsCustomerReturnReceiptLine crReceiptLine = null;
        for (PhilipsCustomerReturnLine crLine : crLines) {
            crReceiptLine = new PhilipsCustomerReturnReceiptLine();
            crReceiptLine.setLineNumber(crLine.getLineNumber());
            crReceiptLine.setAcceptedQty(crLine.getQuantity());
            crReceiptLine.setReceivedQty(crLine.getQuantity());
            crReceiptLine.setSkuCode(crLine.getSkuCode());
            crReceiptLine.setBarcode(crLine.getBarcode());
            crReceiptLine.setPhilipsCusReturnReceipt(crReceipt);
            crReceiveLineDao.save(crReceiptLine);
        }
    }

    @Override
    public void generateInboundSta() {
        List<PhilipsInboundDelivery> inbounds = idDao.getInboundDeliveryByStatus(DataStatus.CREATED.getValue());
        for (PhilipsInboundDelivery inbound : inbounds) {
            ppdManager.generateInboundSta(inbound, Constants.PHILIPS_VMI_CODE);
        }
    }

    @Override
    public void generateInvStatusChange(StockTransApplication sta) {
        if (sta == null) {
            return;
        }
        PhilipsGoodsMovement gm = new PhilipsGoodsMovement();
        Date date = new Date();
        gm.setCreateTime(date);
        gm.setStatus(DataStatus.CREATED.getValue());
        gm.setHeaderTime(date);
        gm.setMovementCode(sta.getCode());
        gmDao.save(gm);
        List<StaLineCommand> stalines = staLineDao.findStalineByStaId2(sta.getId(), new BeanPropertyRowMapper<StaLineCommand>(StaLineCommand.class));
        PhilipsGoodsMovementLine gmLine = null;

        for (StaLineCommand staLine : stalines) {
            List<StvLineCommand> stvLines = stvLineDao.findStvLinesByStaLineIdGroupBy(staLine.getId(), new BeanPropertyRowMapperExt<StvLineCommand>(StvLineCommand.class));
            for (StvLineCommand stvLine : stvLines) {
                if (stvLine.getDirectionInt().equals(TransactionDirection.INBOUND.getValue())) {
                    gmLine = new PhilipsGoodsMovementLine();
                    gmLine.setBarcode(staLine.getExtensionCode3());
                    gmLine.setSkuCode(staLine.getBarCode());
                    gmLine.setMovementType("TRANSFER");
                    gmLine.setLineNumber("1");
                    gmLine.setFromLocation(ppdManager.getLocationByInvID(staLine.getInvInvstatusId()));
                    gmLine.setToLocation(ppdManager.getLocationByInvID(stvLine.getIntInvstatus()));
                    gmLine.setQuantity(new BigDecimal(stvLine.getQuantity()));
                    gmLine.setLineTime(date);
                    gmLine.setpGoodsMovement(gm);
                    gmLineDao.save(gmLine);
                }
            }
        }
    }

    @Override
    public void generateReceivingFlitting(StockTransApplication sta) {

    }

    @Override
    public void generateReceivingTransfer(StockTransApplication sta) {

    }

    @Override
    public void generateReceivingWhenInbound(StockTransApplication sta, StockTransVoucher stv) {

    }

    @Override
    public void generateReceivingWhenShelv(StockTransApplication sta, StockTransVoucher stv) {

    }

    @Override
    public String generateRtnStaSlipCode(String vmiCode, StockTransApplicationType type) {
        return null;
    }

    @Override
    public void generateVMIReceiveInfoByInvCk(InventoryCheck inv) {

    }

    @Override
    public void generateVMITranscationWH() {

    }

    @Override
    public void validateReturnManager(StockTransApplication sta) {}

    @Override
    public void generateInboundStaCallBack(String slipCode, Long staId, Map<String, Long> lineDetial) {}

    @Override
    public void generateInboundStaSetHead(String slipCode, StockTransApplication head) {}

    @Override
    public Map<String, Long> generateInboundStaGetDetial(String slipCode) {
        return null;
    }

    @Override
    public List<String> generateInboundStaGetAllOrderCode() {
        return null;
    }
}
