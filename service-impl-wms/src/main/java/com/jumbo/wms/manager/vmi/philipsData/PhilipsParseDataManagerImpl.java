package com.jumbo.wms.manager.vmi.philipsData;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import loxia.dao.support.BeanPropertyRowMapperExt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.baozun.bh.connector.model.ConnectorMessage;
import cn.baozun.bh.connector.philips.model.CustomerReturn;
import cn.baozun.bh.connector.philips.model.InboundDelivery;
import cn.baozun.bh.connector.philips.model.MaterialMaster;
import cn.baozun.bh.connector.philips.model.OutboundDelivery;
import cn.baozun.bh.util.JSONUtil;
import cn.baozun.bh.util.ZipUtil;

import com.jumbo.dao.authorization.OperationUnitDao;
import com.jumbo.dao.baseinfo.TransportatorDao;
import com.jumbo.dao.vmi.philipsData.PhilipsCustomerReturnDao;
import com.jumbo.dao.vmi.philipsData.PhilipsCustomerReturnLineDao;
import com.jumbo.dao.vmi.philipsData.PhilipsInboundDeliveryDao;
import com.jumbo.dao.vmi.philipsData.PhilipsInboundDeliveryLineDao;
import com.jumbo.dao.vmi.philipsData.PhilipsMasterDao;
import com.jumbo.dao.vmi.philipsData.PhilipsOutboundDeliveryDao;
import com.jumbo.dao.vmi.philipsData.PhilipsOutboundDeliveryLineDao;
import com.jumbo.dao.vmi.philipsData.PhilipsProviceDao;
import com.jumbo.dao.warehouse.BiChannelDao;
import com.jumbo.dao.warehouse.InventoryStatusDao;
import com.jumbo.dao.warehouse.StaDeliveryInfoDao;
import com.jumbo.dao.warehouse.StaLineDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.dao.warehouse.WhInfoTimeRefDao;
import com.jumbo.event.TransactionalEvent;
import com.jumbo.event.listener.EventObserver;
import com.jumbo.wms.Constants;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.manager.warehouse.WareHouseManagerExe; // import
// com.jumbo.wms.manager.baseinfo.BaseinfoManager;
// import com.jumbo.wms.manager.omsTOwms.StaPerfectManager;
// import com.jumbo.wms.manager.vmi.VmiFactory;
// import com.jumbo.wms.manager.vmi.VmiInterface;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.baseinfo.Transportator; // import com.jumbo.wms.model.command.SkuCommand;
import com.jumbo.wms.model.vmi.philipsData.PhilipsCustomerReturn;
import com.jumbo.wms.model.vmi.philipsData.PhilipsCustomerReturnLine;
import com.jumbo.wms.model.vmi.philipsData.PhilipsInboundDelivery;
import com.jumbo.wms.model.vmi.philipsData.PhilipsInboundDeliveryLine;
import com.jumbo.wms.model.vmi.philipsData.PhilipsMaster;
import com.jumbo.wms.model.vmi.philipsData.PhilipsOutboundDelivery;
import com.jumbo.wms.model.vmi.philipsData.PhilipsOutboundDeliveryLine;
import com.jumbo.wms.model.vmi.philipsData.PhilipsProvice;
import com.jumbo.wms.model.warehouse.InventoryStatus;
import com.jumbo.wms.model.warehouse.StaDeliveryInfo;
import com.jumbo.wms.model.warehouse.StaLine;
import com.jumbo.wms.model.warehouse.StaLineCommand;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationStatus;
import com.jumbo.wms.model.warehouse.StockTransApplicationType;
import com.jumbo.wms.model.warehouse.WhInfoTimeRefBillType;
import com.jumbo.wms.model.warehouse.WhInfoTimeRefNodeType;

@Transactional
@Service("philipsParseDataManager")
public class PhilipsParseDataManagerImpl extends BaseManagerImpl implements PhilipsParseDataManager {

    /**
	 * 
	 */
    private static final long serialVersionUID = -7986836457319754128L;
    public static final Map<String, String> PHILIPS_DELIVERY_TYPE;
    static {
        PHILIPS_DELIVERY_TYPE = new HashMap<String, String>();
        PHILIPS_DELIVERY_TYPE.put("ZCNEXP", "商城快递");
        PHILIPS_DELIVERY_TYPE.put("ZCNEMS", "EMS");
    }

    public static final Map<String, String> PHILIPS_DELIVERY_TIME;
    static {
        PHILIPS_DELIVERY_TIME = new HashMap<String, String>();
        PHILIPS_DELIVERY_TIME.put("WD_8TO18", " 仅工作日-8AM到18PM");
        PHILIPS_DELIVERY_TIME.put("WKN_8TO18", " 仅双休日-8AM到18PM");
        PHILIPS_DELIVERY_TIME.put("WD_WKN_8TO18", " 工作日双休日均可-8AM到18PM");
    }

    public static final String PHILIPS_INVENTORY_FOR_SALE = "可销售";
    public static final String PHILIPS_INVENTORY_DEFECTIVE = "不可销售";
    public static final String PHILIPS_INVENTORY_PENDING = "待处理品";

    public static final Map<String, String> PHILIPS_INVENTROY_TYPE;
    static {
        PHILIPS_INVENTROY_TYPE = new HashMap<String, String>();
        PHILIPS_INVENTROY_TYPE.put(PHILIPS_INVENTORY_FOR_SALE, "WHS");
        PHILIPS_INVENTROY_TYPE.put(PHILIPS_INVENTORY_PENDING, "REBL");
        PHILIPS_INVENTROY_TYPE.put(PHILIPS_INVENTORY_DEFECTIVE, "DAM");
    }

    // @Autowired
    // private StaPerfectManager staPerfectManager;
    @Autowired
    private PhilipsInboundDeliveryDao inboundDao;
    @Autowired
    private PhilipsInboundDeliveryLineDao inboundLineDao;
    @Autowired
    private PhilipsOutboundDeliveryLineDao outLineDao;
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private OperationUnitDao ouDao;
    @Autowired
    private InventoryStatusDao inventoryStatusDao;
    @Autowired
    private StaLineDao staLineDao;
    @Autowired
    private WhInfoTimeRefDao whInfoTimeRefDao;
    // @Autowired
    // private VmiFactory vimf;
    // @Autowired
    // private BaseinfoManager baseinfoManager;
    @Autowired
    private BiChannelDao companyShopDao;
    @Autowired
    private StaDeliveryInfoDao staDeliveryInfoDao;
    @Autowired
    private WareHouseManagerExe wmExe;
    @Autowired
    private WareHouseManager wareHouseManager;
    @Autowired
    private PhilipsCustomerReturnDao crDao;
    @Autowired
    private PhilipsCustomerReturnLineDao crLineDao;
    @Autowired
    private PhilipsMasterDao philipsMasterDao;
    @Autowired
    private PhilipsOutboundDeliveryDao pOutboundDeliveryDao;
    private EventObserver eventObserver;
    @Resource
    private ApplicationContext applicationContext;
    @Autowired
    private TransportatorDao transportatorDao;
    @Autowired
    private PhilipsProviceDao proviceDao;

    @PostConstruct
    protected void init() {
        try {
            eventObserver = applicationContext.getBean(EventObserver.class);
        } catch (Exception e) {
            log.error("", e);
        }
    }

    /**
     * PhilipsMaster receive by mq
     */
    @Override
    public void receivePhilipsMasterByMq(String message) {
        log.debug("philipsmaster start =========================== : " + message);
        PhilipsMaster philipsMaster = null;
        try {
            ConnectorMessage connectorMessage = JSONUtil.jsonToBean(message, ConnectorMessage.class);
            String confirmId = connectorMessage.getConfirmId();
            String messageContent = connectorMessage.getMessageContent();
            if (connectorMessage.getIsCompress()) {
                messageContent = ZipUtil.decompress(messageContent);
            }
            MaterialMaster pMaterialMaster = JSONUtil.jsonToBean(messageContent, MaterialMaster.class);
            philipsMaster = philipsMasterDao.getPhilipsMasterByConfirmId(confirmId, new BeanPropertyRowMapper<PhilipsMaster>(PhilipsMaster.class));
            if (philipsMaster == null && pMaterialMaster != null) {
                for (MaterialMaster.Sku sku : pMaterialMaster.getSkus()) {
                    philipsMaster = new PhilipsMaster();
                    philipsMaster.setSkuCode(sku.getSkuCode() == null ? "" : sku.getSkuCode().trim());
                    philipsMaster.setDescName(sku.getSkuDesc() == null ? "" : sku.getSkuDesc().trim());
                    philipsMaster.setBarcode(sku.getSkuBarcode() == null ? "" : sku.getSkuBarcode().trim());
                    philipsMaster.setArticeNumber(sku.getSkuArticleNumber() == null ? "" : sku.getSkuArticleNumber().trim());
                    philipsMaster.setLength("".equals(sku.getSkuLength()) ? new BigDecimal("0") : new BigDecimal(sku.getSkuLength().trim()));
                    philipsMaster.setWidth("".equals(sku.getSkuWidth()) ? new BigDecimal("0") : new BigDecimal(sku.getSkuWidth().trim()));
                    philipsMaster.setHeight("".equals(sku.getSkuHeight()) ? new BigDecimal("0") : new BigDecimal(sku.getSkuHeight().trim()));
                    philipsMaster.setGrossWeight("".equals(sku.getSkuGrossWeight()) ? new BigDecimal("0") : new BigDecimal(sku.getSkuGrossWeight().trim()));
                    philipsMaster.setNetWeight("".equals(sku.getSkuNetWeight()) ? new BigDecimal("0") : new BigDecimal(sku.getSkuNetWeight().trim()));
                    philipsMaster.setConfirmId(confirmId);
                    philipsMaster.setCreateTime(Calendar.getInstance().getTime());
                    philipsMaster.setStatus(0);
                    philipsMasterDao.save(philipsMaster);
                }
            } else {
                log.debug("philipsmaster skulist is null=========================== : " + message);
            }
            log.debug("philipsmaster end=========================== : " + message);
        } catch (Exception e) {
            log.error("", e);
        }
    }

    /**
     * PhilipsOutbound receive by mq
     */
    @Override
    public void receivePhilipsOutboundByMq(String message) {
        log.debug("philipsOutbound start =========================== : " + message);
        PhilipsOutboundDelivery pOutboundDelivery = null;
        PhilipsOutboundDeliveryLine pOutboundDeliveryLine = null;
        try {
            ConnectorMessage connectorMessage = JSONUtil.jsonToBean(message, ConnectorMessage.class);
            String confirmId = connectorMessage.getConfirmId();
            String messageContent = connectorMessage.getMessageContent();
            if (connectorMessage.getIsCompress()) {
                messageContent = ZipUtil.decompress(messageContent);
            }
            OutboundDelivery outboundDelivery = JSONUtil.jsonToBean(messageContent, OutboundDelivery.class);
            pOutboundDelivery = pOutboundDeliveryDao.getOutboundDeliveryByConfirmId(confirmId, new BeanPropertyRowMapper<PhilipsOutboundDelivery>(PhilipsOutboundDelivery.class));
            if (pOutboundDelivery == null && outboundDelivery != null) {
                OutboundDelivery.Header outboundHeader = outboundDelivery.getHeader();
                if (outboundHeader == null) {
                    log.debug("OutboundHeader is null=========================== : " + message);
                    return;
                }
                pOutboundDelivery = new PhilipsOutboundDelivery();
                pOutboundDelivery.setOrderCode(outboundHeader.getOutboundOrderCode() == null ? "" : outboundHeader.getOutboundOrderCode().trim());
                pOutboundDelivery.setSlipCode(outboundHeader.getOutboundSlipCode() == null ? "" : outboundHeader.getOutboundSlipCode().trim());
                pOutboundDelivery.setCountry(outboundHeader.getOutboundCountry() == null ? "" : outboundHeader.getOutboundCountry());
                pOutboundDelivery.setProvince(outboundHeader.getOutboundProvince() == null ? "" : outboundHeader.getOutboundProvince());
                pOutboundDelivery.setCity(outboundHeader.getOutboundCity() == null ? "" : outboundHeader.getOutboundCity());
                pOutboundDelivery.setZipCode(outboundHeader.getOutboundZipCode() == null ? "" : outboundHeader.getOutboundZipCode());
                pOutboundDelivery.setAddress(outboundHeader.getOutboundAddress() == null ? "" : outboundHeader.getOutboundAddress());
                pOutboundDelivery.setReceiver(outboundHeader.getOutboundReceiver() == null ? "" : outboundHeader.getOutboundReceiver().trim());
                pOutboundDelivery.setConfirmId(confirmId);
                pOutboundDelivery.setCreateTime(Calendar.getInstance().getTime());
                pOutboundDelivery.setStatus(0);
                pOutboundDeliveryDao.save(pOutboundDelivery);

                if (outboundDelivery.getLines() != null) {
                    for (OutboundDelivery.Line oLine : outboundDelivery.getLines()) {
                        pOutboundDeliveryLine = new PhilipsOutboundDeliveryLine();
                        pOutboundDeliveryLine.setSkuCode(oLine.getOutboundLineSkuCode() == null ? "" : oLine.getOutboundLineSkuCode().trim());
                        pOutboundDeliveryLine.setBarcode(oLine.getOutboundLineBarcode() == null ? "" : oLine.getOutboundLineBarcode().trim());
                        pOutboundDeliveryLine.setLineNumber(oLine.getOutboundLineNumber() == null ? "" : oLine.getOutboundLineNumber().trim());
                        pOutboundDeliveryLine.setQuantity("".equals(oLine.getOutboundLineQuantity()) ? new BigDecimal("0") : new BigDecimal(oLine.getOutboundLineQuantity().trim()));
                        pOutboundDeliveryLine.setCreateTime(Calendar.getInstance().getTime());
                        pOutboundDeliveryLine.setStatus(0);
                        pOutboundDeliveryLine.setPhilipsOutDelivery(pOutboundDelivery);
                        outLineDao.save(pOutboundDeliveryLine);
                    }
                }
            } else {
                log.debug("OutboundDocument is null=========================== : " + message);
            }
            log.debug("philipsOutbound end =========================== : " + message);
        } catch (Exception e) {
            log.error("", e);
        }
    }

    /**
     * PhilipsInbound receive by mq
     */
    @Override
    public void receivePhilipsInboundByMq(String message) {
        log.debug("philipsInbound start =========================== : " + message);
        PhilipsInboundDelivery pInboundDelivery = null;
        PhilipsInboundDeliveryLine pInboundDeliveryLine = null;
        try {
            ConnectorMessage connectorMessage = JSONUtil.jsonToBean(message, ConnectorMessage.class);
            String confirmId = connectorMessage.getConfirmId();
            String messageContent = connectorMessage.getMessageContent();
            if (connectorMessage.getIsCompress()) {
                messageContent = ZipUtil.decompress(messageContent);
            }
            InboundDelivery inboundDelivery = JSONUtil.jsonToBean(messageContent, InboundDelivery.class);
            pInboundDelivery = inboundDao.getInboundDeliveryByConfirmId(confirmId, new BeanPropertyRowMapper<PhilipsInboundDelivery>(PhilipsInboundDelivery.class));
            if (pInboundDelivery == null && inboundDelivery != null) {
                InboundDelivery.Header inboundHeader = inboundDelivery.getHeader();
                if (inboundHeader == null) {
                    log.debug("inboundHeader is null=========================== : " + message);
                    return;
                }
                pInboundDelivery = new PhilipsInboundDelivery();
                pInboundDelivery.setDeliveryNo(inboundHeader.getInboundDeliveryNo() == null ? "" : inboundHeader.getInboundDeliveryNo().trim());
                pInboundDelivery.setInboundCode(inboundHeader.getInboundDeliveryInboundCode() == null ? "" : inboundHeader.getInboundDeliveryInboundCode().trim());
                pInboundDelivery.setConfirmId(confirmId);
                pInboundDelivery.setCreateTime(Calendar.getInstance().getTime());
                pInboundDelivery.setStatus(0);
                inboundDao.save(pInboundDelivery);

                if (inboundDelivery.getLines() != null) {
                    for (InboundDelivery.Line iLine : inboundDelivery.getLines()) {
                        pInboundDeliveryLine = new PhilipsInboundDeliveryLine();
                        pInboundDeliveryLine.setSkuCode(iLine.getInboundDeliverySkuCode() == null ? "" : iLine.getInboundDeliverySkuCode().trim());
                        pInboundDeliveryLine.setBarcode(iLine.getInboundDeliverySkuBarcode() == null ? "" : iLine.getInboundDeliverySkuBarcode().trim());
                        pInboundDeliveryLine.setLineNumber(iLine.getInboundDeliveryLineNumber() == null ? "" : iLine.getInboundDeliveryLineNumber().trim());
                        pInboundDeliveryLine.setQuantity("".equals(iLine.getInboundDeliveryQuantity()) ? new BigDecimal("0") : new BigDecimal(iLine.getInboundDeliveryQuantity().trim()));
                        pInboundDeliveryLine.setPoCode(iLine.getInboundDelieveryPoNo() == null ? "" : iLine.getInboundDelieveryPoNo().trim());
                        pInboundDeliveryLine.setCreateTime(Calendar.getInstance().getTime());
                        pInboundDeliveryLine.setStatus(0);
                        pInboundDeliveryLine.setPhilipsInDelivery(pInboundDelivery);
                        inboundLineDao.save(pInboundDeliveryLine);
                    }
                }
            } else {
                log.debug("inboundDocument is null=========================== : " + message);
            }
            log.debug("philipsInbound end =========================== : " + message);
        } catch (Exception e) {
            log.error("", e);
        }
    }

    /**
     * PhilipsCusReturn receive by mq
     */
    @Override
    public void receivePhilipsCusReturnByMq(String message) {
        log.debug("philips customer return start=========================== : " + message);
        PhilipsCustomerReturn pCustomerReturn = null;
        PhilipsCustomerReturnLine pCustomerReturnLine = null;
        try {
            ConnectorMessage connectorMessage = JSONUtil.jsonToBean(message, ConnectorMessage.class);
            String confirmId = connectorMessage.getConfirmId();
            String messageContent = connectorMessage.getMessageContent();
            if (connectorMessage.getIsCompress()) {
                messageContent = ZipUtil.decompress(messageContent);
            }
            CustomerReturn customerReturn = JSONUtil.jsonToBean(messageContent, CustomerReturn.class);
            pCustomerReturn = crDao.getCustomerReturnByConfirmId(confirmId, new BeanPropertyRowMapper<PhilipsCustomerReturn>(PhilipsCustomerReturn.class));
            if (pCustomerReturn == null && customerReturn != null) {
                CustomerReturn.Header cHeader = customerReturn.getHeader();
                if (cHeader == null) {
                    log.debug("CustomerReturn.Header is null=========================== : " + message);
                    return;
                }
                pCustomerReturn = new PhilipsCustomerReturn();
                pCustomerReturn.setOrderCode(cHeader.getCustomerReturnOrderCode() == null ? "" : cHeader.getCustomerReturnOrderCode().trim());
                pCustomerReturn.setConfirmId(confirmId);
                pCustomerReturn.setCreateTime(Calendar.getInstance().getTime());
                pCustomerReturn.setStatus(0);
                crDao.save(pCustomerReturn);

                if (customerReturn.getLines() != null) {
                    for (CustomerReturn.Line cLine : customerReturn.getLines()) {
                        pCustomerReturnLine = new PhilipsCustomerReturnLine();
                        pCustomerReturnLine.setLineNumber(cLine.getCustomerReturnLineNumber() == null ? "" : cLine.getCustomerReturnLineNumber().trim());
                        pCustomerReturnLine.setQuantity("".equals(cLine.getCustomerReturnLineQuantity()) ? new BigDecimal("0") : new BigDecimal(cLine.getCustomerReturnLineQuantity().trim()));
                        pCustomerReturnLine.setSkuCode(cLine.getCustomerReturnLineSkuCode() == null ? "" : cLine.getCustomerReturnLineSkuCode().trim());
                        pCustomerReturnLine.setBarcode(cLine.getCustomerReturnLineBarcode() == null ? "" : cLine.getCustomerReturnLineBarcode().trim());
                        pCustomerReturnLine.setSlipCode(cLine.getCustomerReturnSlipCode() == null ? "" : cLine.getCustomerReturnSlipCode().trim());
                        pCustomerReturnLine.setCreateTime(Calendar.getInstance().getTime());
                        pCustomerReturnLine.setStatus(0);
                        pCustomerReturn.setSlipCode(cLine.getCustomerReturnSlipCode() == null ? "" : cLine.getCustomerReturnSlipCode().trim());
                        pCustomerReturnLine.setpCustomerReturn(pCustomerReturn);
                        crLineDao.save(pCustomerReturnLine);
                    }
                }
            } else {
                log.debug("customerReturn is null=========================== : " + message);
            }
            log.debug("philips customer return end=========================== : " + message);
        } catch (Exception e) {
            log.error("", e);
        }
    }

    public void generateInboundSta(PhilipsInboundDelivery inboundDel, String vmiCode) {
        // List<PhilipsInboundDeliveryLine> idList =
        // inboundLineDao.getLineByInboundDelId(inboundDel.getId(), new
        // BeanPropertyRowMapperExt<PhilipsInboundDeliveryLine>(PhilipsInboundDeliveryLine.class));
        // if (idList.size() < 0) return;
        // StockTransApplication sta = new StockTransApplication();
        // sta.setType(StockTransApplicationType.VMI_INBOUND_CONSIGNMENT);
        // sta.setBusinessSeqNo(staDao.getBusinessSeqNo(new
        // SingleColumnRowMapper<BigDecimal>()).longValue());
        // sta.setCreateTime(new Date());
        // sta.setStatus(StockTransApplicationStatus.CREATED);
        // sta.setCode(sequenceManager.getCode(StockTransApplication.class.getName(),
        // sta));
        // sta.setRefSlipCode(inboundDel.getInboundCode());
        // sta.setSlipCode1(inboundDel.getDeliveryNo());
        // staDao.save(sta);
        //
        // OperationUnit ou = null;
        // String innerShopCode = null;
        // Warehouse wh = null;
        // InventoryStatus is = null;
        // Long skuQty = 0l;
        // // String vmiCode = Constants.PHILIPS_VMI_CODE;
        // BiChannel shop = shopDao.getByVmiCode(vmiCode);
        // if (shop == null) {
        // // 抛异常，报错
        // throw new BusinessException("没有找到店铺信息!vmiCode:" + vmiCode);
        // }
        //
        // if (wh == null) {
        // // wh = whManager.getVMIWarehouseByShop(shop);
        // // if (wh == null) {
        // // log.info("=========OPERATION UNIT {} NOT FOUNT===========", new
        // Object[]
        // {shop.getVmiCode()});
        // // throw new
        // BusinessException(ErrorCode.END_OPERATION_UNIT_NOT_FOUNT);
        // // } else {
        // // Long ouId = wh.getOu().getId();
        // // ou = ouDao.getByPrimaryKey(ouId);
        // // if (ou == null) {
        // // log.info("=========OPERATION UNIT {} NOT FOUNT===========", new
        // Object[]
        // {shop.getVmiCode()});
        // // throw new
        // BusinessException(ErrorCode.END_OPERATION_UNIT_NOT_FOUNT);
        // // }
        // // }
        // }
        //
        // if (is == null) {
        // Long companyId = null;
        // if (ou.getParentUnit() != null) {
        // OperationUnit ou1 =
        // ouDao.getByPrimaryKey(ou.getParentUnit().getId());
        // if (ou1 != null) {
        // companyId = ou1.getParentUnit().getId();
        // }
        // }
        // is = inventoryStatusDao.findInvStatusForSale(companyId);
        // }
        // Date date = new Date();
        // for (PhilipsInboundDeliveryLine inss : idList) {
        // if (inboundDel.getSta() != null || inss.getStaLine() != null) {
        // log.info("===============this instruction has create STA================");
        // throw new BusinessException(ErrorCode.VMI_INSTRUCTION_TYPE_ERROR);
        // }
        //
        // String skuCode = inss.getSkuCode().toString();
        // // 判断skuCode在系统中是否存在
        // // Sku sku = skuDao.findSkuByBarCode(skuCode);
        // Sku sku = null;
        // if (sku == null) {
        // // 如果sku在系统中没有，就生成，生成失败返回false
        // boolean isExist = false;
        // try {
        // log.info("===============SKU {} start create ================", new
        // Object[] {skuCode});
        // isExist = generateSKU(skuCode, shop.getId(), shop.getVmiCode());
        // } catch (Exception e) {
        // log.debug("======================Create SKU error :" +
        // e.getMessage());
        // log.error("",e);
        // }
        // if (!isExist) {
        // log.info("===============SKU {} NOT FOUND ================", new
        // Object[] {skuCode});
        // throw new BusinessException(ErrorCode.SKU_NOT_FOUND);
        // }
        // skuDao.flush();
        // // sku = skuDao.findSkuByBarCode(skuCode);
        // }
        // if (sku == null) {
        // throw new BusinessException(ErrorCode.SKU_NOT_FOUND, new Object[]
        // {skuCode});
        // }
        // StaLine staLine = new StaLine();
        // staLine.setQuantity(inss.getQuantity().longValue());
        // staLine.setSku(sku);
        // staLine.setCompleteQuantity(0L);// 已执行数量
        // staLine.setSta(sta);
        // innerShopCode = shop.getCode();
        // skuQty += staLine.getQuantity();
        // staLine.setOwner(innerShopCode);
        // staLine.setInvStatus(is);
        // staLine = staLineDao.save(staLine);
        // inboundLineDao.updatePDLStaLine(staLine.getId(), inboundDel.getId(),
        // inss.getBarcode(),
        // inss.getSkuCode());
        // }
        // inboundDel.setSta(sta);
        // inboundDel.setVersion(date);
        // inboundDel.setStatus(DataStatus.FINISHED.getValue());
        // inboundDao.save(inboundDel);
        // sta.setSkuQty(skuQty);
        // log.info("===============sta {} create success ================", new
        // Object[]
        // {sta.getCode()});
        // if (StringUtils.hasText(innerShopCode) && ou != null && is != null) {
        // sta.setOwner(innerShopCode);
        // sta.setMainWarehouse(ou);
        // sta.setMainStatus(is);
        // staDao.save(sta);
        // staDao.flush();
        // staDao.updateSkuQtyById(sta.getId());
        // } else {
        // throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        // }
    }

    public boolean generateSKU(String barcode, Long shopId, String vmiCode) {
        // boolean flag = false;
        // VmiInterface i = vimf.getBrandVmi(vmiCode);
        // SkuCommand skuCommand = i.findSkuCommond(barcode);
        // if (skuCommand == null) {
        // log.info("=========SKU CODE {} NOT FOUNT IN SKUDATA===========", new
        // Object[] {barcode});
        // return flag;
        // }
        // Long templateId = 0L;
        // if (skuCommand.getSize() == null || skuCommand.getSize().equals(""))
        // {
        // if (skuCommand.getColor() == null ||
        // skuCommand.getColor().equals("")) {
        // templateId = Constants.SKU_TEMPLATE_ID_NOSIZE_NOCOLOR;
        // } else {
        // templateId = Constants.SKU_TEMPLATE_ID_NOSIZE;
        // }
        // } else {
        // if (skuCommand.getColor() == null ||
        // skuCommand.getColor().equals("")) {
        // templateId = Constants.SKU_TEMPLATE_ID_NOCOLOR;
        // } else {
        // templateId = Constants.SKU_TEMPLATE_ID;
        // }
        // }
        // flag = baseinfoManager.createSkuForBrand(skuCommand, templateId,
        // shopId, null);
        // return flag;
        return false;
    }

    /**
     * 查询单据是否已经存在 取消已处理-17， 取消未处理 -15 可以创建， 其他不创建
     * 
     * @param refCode
     * @param staType
     */
    private StockTransApplication findStaByWHAndRefCode(String refCode, int staType) {
        return staDao.findWHAndRefCodeStaType(refCode, staType, new BeanPropertyRowMapperExt<StockTransApplication>(StockTransApplication.class));
    }

    /**
     * 根据店铺id查找 任意为收发仓的仓库
     * 
     * @param shopid
     * @return
     */
    private OperationUnit findWarehouseOuByShopid(Long shopid) {
        return null;
        // Warehouse warehouse = null;
        // // List<Warehouse> warehouses =
        // warehouseDao.findByCompanysShopId(shopid, new
        // BeanPropertyRowMapperExt<Warehouse>(Warehouse.class));
        // List<Warehouse> warehouses = null;
        // for (Warehouse entry : warehouses) {
        // if (entry == null) continue;
        // if (entry.getOpMode().getValue() ==
        // WarehouseOpMode.NORMAL.getValue()) { // 收发仓
        // warehouse = entry;
        // break;
        // }
        // }
        // if (warehouse == null) {
        // log.error("warehosue is not founded for nike ***************************");
        // throw new BusinessException(ErrorCode.NIKE_WRAEHOUSE_NOT_FOUND);
        // }
        // OperationUnit warehouseOU =
        // operationUnitDao.getByPrimaryKey(warehouse.getOu().getId());
        // return warehouseOU;
    }

    @Transactional
    public StockTransApplication createPhilipsSalesSta(StockTransApplication sta, PhilipsOutboundDelivery outbound, String vmiCode) {
        // 查找单据是否已存在
        if (findStaByWHAndRefCode(Constants.PHILIPS_ORDER_PRE + sta.getRefSlipCode(), sta.getType().getValue()) != null) {
            throw new BusinessException("PHILIPS create sales sta exist error.");
        }
        if (outbound == null) {
            throw new BusinessException("PHILIPS create sales sta slip code not exist : " + sta.getRefSlipCode() + ".");
        }
        // 店铺
        // Long shopId = Constants.PHILIPS_OUT_SHOP_ID;
        BiChannel companyShop = companyShopDao.getByVmiCode(vmiCode);
        if (companyShop == null) {
            throw new BusinessException("'Philips shopId: " + vmiCode + "' nike shop not found.");
        }
        wmExe.validateBiChannelSupport(null, companyShop.getCode());
        // 仓库
        OperationUnit warehouseOU = findWarehouseOuByShopid(companyShop.getId());
        if (warehouseOU == null) {
            throw new BusinessException("warehouse not found.");
        }

        PhilipsProvice provide = proviceDao.getProviecByCode(outbound.getProvince());
        if (provide == null) {
            throw new BusinessException("provice Code not found.");
        }

        sta.setRefSlipCode(Constants.PHILIPS_ORDER_PRE + sta.getRefSlipCode());
        sta.setMainWarehouse(warehouseOU);
        sta.setCreateTime(new Date());
        sta.setOwner(companyShop.getCode());
        sta.setLastModifyTime(new Date());
        sta.setStatus(StockTransApplicationStatus.CREATED);
        // 订单状态与账号关联
        whInfoTimeRefDao.insertWhInfoTime2(sta.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.CREATE.getValue(), null, warehouseOU.getId());
        String remark = outbound.getRemark();
        String deliveryType = "";
        String deliveryTime = "";
        try {
            if (remark != null) {
                String remarks[] = remark.split("_", 2);
                deliveryType = PHILIPS_DELIVERY_TYPE.get(remarks[0]);
                deliveryTime = PHILIPS_DELIVERY_TIME.get(remarks[1]);
            }
        } catch (Exception e) {
            log.error("", e);
        }

        sta.setMemo(deliveryType + deliveryTime);
        sta.setIsSn(false);
        sta.setIsNeedOccupied(true);
        staDao.save(sta);

        // SAVE STA DELIVERY INFO
        StaDeliveryInfo staDeliveryInfo = new StaDeliveryInfo();
        staDeliveryInfo.setId(sta.getId());
        staDeliveryInfo.setCountry(outbound.getCountry());
        staDeliveryInfo.setProvince(provide.getName());
        staDeliveryInfo.setCity(outbound.getCity());
        staDeliveryInfo.setAddress(outbound.getAddress());
        staDeliveryInfo.setReceiver(outbound.getReceiver());
        staDeliveryInfo.setZipcode(outbound.getZipCode());
        // staDeliveryInfo.setDistrict(outbound.getDistrict());
        // staDeliveryInfo.setTelephone(qoutbound.getTelephone());
        staDeliveryInfo.setStoreComIsNeedInvoice(false);// 不需要开票
        staDeliveryInfo.setSta(sta);
        if (deliveryType != null && deliveryType.equals("EMS")) {
            Transportator tp = transportatorDao.findByCode(deliveryType);
            if (tp != null) {
                staDeliveryInfo.setLpCode(deliveryType);
            }
        }
        staDeliveryInfoDao.save(staDeliveryInfo);
        // save sta line
        List<PhilipsOutboundDeliveryLine> outLinelist = outLineDao.getOutboundLineByOutId(outbound.getId());
        if (outLinelist == null || outLinelist.size() == 0) {
            throw new BusinessException("PHILIPS sales sta create error : no line.");
        }
        Long companyId = null;
        if (warehouseOU.getParentUnit() != null) {
            OperationUnit ou1 = ouDao.getByPrimaryKey(warehouseOU.getParentUnit().getId());
            if (ou1 != null) {
                companyId = ou1.getParentUnit().getId();
            }
        }

        InventoryStatus is = inventoryStatusDao.findInvStatusForSale(companyId);
        constractStaLine(outLinelist, is, sta);
        // 更新销售出库订单信息 已经秒杀相关信息
        // staPerfectManager.omsTOwmsUpdateSta(sta.getId());
        if (!StockTransApplicationType.INBOUND_RETURN_REQUEST.equals(sta.getType())) {
            staDao.flush();
            // 验证库存可用量
            List<StaLineCommand> occupyLines = staLineDao.findNotEnoughtSalesAvailInvBySta(sta.getId(), new BeanPropertyRowMapper<StaLineCommand>(StaLineCommand.class));
            if (occupyLines == null || occupyLines.size() == 0) {
                try {
                    eventObserver.onEvent(new TransactionalEvent(sta));
                } catch (BusinessException e) {
                    e = new BusinessException("philips webserverce business exception.");
                    log.error("", e);
                    throw e;
                }
            } else {
                BusinessException head = new BusinessException("inventory not enough.");// 11
                BusinessException root = head;
                for (StaLineCommand l : occupyLines) {
                    BusinessException current = new BusinessException("[" + l.getSkuCode() + "]" + l.getQuantity() + "　");
                    root.setLinkedException(current);
                    root = current;
                }
                throw head;
            }
        }
        return sta;
    }

    /**
     * STALINE 的构建
     * 
     * @param lineObjs
     * @param invStatus
     * @param status
     * @param sta
     * @return
     */
    private void constractStaLine(List<PhilipsOutboundDeliveryLine> list, InventoryStatus is, StockTransApplication sta) {
        Long skuQty = 0L;
        BiChannel c = companyShopDao.getByCode(sta.getOwner());
        for (PhilipsOutboundDeliveryLine outLine : list) {
            Sku sku = wareHouseManager.getByExtCode2AndCustomerAndShopId(outLine.getSkuCode(), c.getCustomer().getId(), c.getId());
            if (sku == null) {
                throw new BusinessException("nike webserverce SKU not found.[SKU:" + outLine.getSkuCode() + "]");
            }
            StaLine staLine = new StaLine();
            staLine.setInvStatus(is);
            staLine.setSku(sku);
            staLine.setQuantity(outLine.getQuantity().longValue());
            skuQty += outLine.getQuantity().longValue();
            staLine.setCompleteQuantity(0L);
            staLine.setOwner(sta.getOwner());
            staLine.setSta(sta);
            staLineDao.save(staLine);
            outLine.setStaLine(staLine);
            outLineDao.save(outLine);
        }
        sta.setSkuQty(skuQty);
    }

    private void constractReturnStaLine(List<PhilipsCustomerReturnLine> list, InventoryStatus is, StockTransApplication sta) {
        Long skuQty = 0L;
        BiChannel c = companyShopDao.getByCode(sta.getOwner());
        for (PhilipsCustomerReturnLine outLine : list) {
            Sku sku = wareHouseManager.getByExtCode2AndCustomerAndShopId(outLine.getSkuCode(), c.getCustomer().getId(), c.getId());
            if (sku == null) {
                throw new BusinessException("nike webserverce SKU not found.[SKU:" + outLine.getSkuCode() + "]");
            }
            StaLine staLine = new StaLine();
            staLine.setInvStatus(is);
            staLine.setSku(sku);
            staLine.setQuantity(outLine.getQuantity().longValue());
            skuQty += outLine.getQuantity().longValue();
            staLine.setCompleteQuantity(0L);
            staLine.setOwner(sta.getOwner());
            staLine.setSta(sta);
            staLineDao.save(staLine);
            outLine.setStaLine(staLine);
            crLineDao.save(outLine);
        }
        sta.setSkuQty(skuQty);
    }

    public String getLocationByInvID(Long invId) {
        String location = "";
        InventoryStatus is = inventoryStatusDao.getByPrimaryKey(invId);
        if (is != null) {
            location = PHILIPS_INVENTROY_TYPE.get(is.getName());
        }
        return location;
    }

    public StockTransApplication createPhilipsReturnSta(StockTransApplication sta, PhilipsCustomerReturn cr, String vmiCode) {
        // 查找单据是否已存在
        if (findStaByWHAndRefCode(Constants.PHILIPS_ORDER_PRE + sta.getRefSlipCode(), sta.getType().getValue()) != null) {
            throw new BusinessException("PHILIPS create sales sta exist error.");
        }
        if (cr == null) {
            throw new BusinessException("PHILIPS create return sta slip code not exist : " + sta.getRefSlipCode() + ".");
        }
        // 店铺
        // Long shopId = Constants.PHILIPS_OUT_SHOP_ID;
        BiChannel companyShop = companyShopDao.getByVmiCode(vmiCode);
        if (companyShop == null) {
            throw new BusinessException("'Philips shopId: " + vmiCode + "' nike shop not found.");
        }
        wmExe.validateBiChannelSupport(null, companyShop.getCode());
        // 仓库
        OperationUnit warehouseOU = findWarehouseOuByShopid(companyShop.getId());
        if (warehouseOU == null) {
            throw new BusinessException("warehouse not found.");
        }

        sta.setRefSlipCode(Constants.PHILIPS_ORDER_PRE + sta.getRefSlipCode());
        sta.setMainWarehouse(warehouseOU);
        sta.setCreateTime(new Date());
        sta.setOwner(companyShop.getCode());
        sta.setLastModifyTime(new Date());
        sta.setStatus(StockTransApplicationStatus.CREATED);
        // 订单状态与账号关联
        whInfoTimeRefDao.insertWhInfoTime2(sta.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.CREATE.getValue(), null, warehouseOU.getId());
        // sta.setMemo(cr.getRemark());
        sta.setIsSn(false);
        sta.setIsNeedOccupied(true);
        staDao.save(sta);
        List<PhilipsCustomerReturnLine> crLine = crLineDao.getCRLineByCRID(cr.getId());
        if (crLine == null || crLine.size() == 0) {
            throw new BusinessException("PHILIPS Return sta create error : no line.");
        }
        Long companyId = null;
        if (warehouseOU.getParentUnit() != null) {
            OperationUnit ou1 = ouDao.getByPrimaryKey(warehouseOU.getParentUnit().getId());
            if (ou1 != null) {
                companyId = ou1.getParentUnit().getId();
            }
        }
        InventoryStatus is = inventoryStatusDao.findInvStatusByCompanyANDName(companyId, PHILIPS_INVENTORY_PENDING);
        constractReturnStaLine(crLine, is, sta);
        return sta;
    }
}
