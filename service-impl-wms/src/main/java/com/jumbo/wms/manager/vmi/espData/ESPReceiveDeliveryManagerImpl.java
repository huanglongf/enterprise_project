/**
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
 */
package com.jumbo.wms.manager.vmi.espData;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import loxia.dao.support.BeanPropertyRowMapperExt;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.dao.authorization.OperationUnitDao;
import com.jumbo.dao.vmi.espData.ESPDeliveryDao;
import com.jumbo.dao.vmi.espData.ESPDeliveryReceiveDao;
import com.jumbo.dao.warehouse.BiChannelDao;
import com.jumbo.dao.warehouse.InventoryStatusDao;
import com.jumbo.dao.warehouse.StaLineDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.dao.warehouse.WhInfoTimeRefDao;
import com.jumbo.pac.manager.extsys.wms.rmi.Rmi4Wms;
import com.jumbo.pac.manager.extsys.wms.rmi.model.BaseResult;
import com.jumbo.util.FileUtil;
import com.jumbo.util.FormatUtil;
import com.jumbo.util.StringUtil;
import com.jumbo.wms.Constants;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.baseinfo.BaseinfoManager;
import com.jumbo.wms.manager.system.SequenceManager;
import com.jumbo.wms.manager.vmi.VmiEsprit;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.manager.warehouse.WareHouseManagerExe;
import com.jumbo.wms.manager.warehouse.WareHouseManagerQuery;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.command.vmi.esprit.xml.espDelilvery.Deliveries;
import com.jumbo.wms.model.command.vmi.esprit.xml.espDelilvery.Delivery;
import com.jumbo.wms.model.command.vmi.esprit.xml.espDelilvery.EspDelivery;
import com.jumbo.wms.model.command.vmi.esprit.xml.espDelilvery.Header;
import com.jumbo.wms.model.command.vmi.esprit.xml.espDelilvery.Items;
import com.jumbo.wms.model.command.vmi.esprit.xml.espDispatch.EspDispatch;
import com.jumbo.wms.model.command.vmi.esprit.xml.espDispatch.Item;
import com.jumbo.wms.model.command.vmi.esprit.xml.espDispatch.Pick;
import com.jumbo.wms.model.vmi.espData.ESPDelivery;
import com.jumbo.wms.model.vmi.espData.ESPDeliveryCommand;
import com.jumbo.wms.model.vmi.espData.ESPDeliveryReceive;
import com.jumbo.wms.model.vmi.espData.ESPDeliveryReceiveCommand;
import com.jumbo.wms.model.warehouse.InventoryStatus;
import com.jumbo.wms.model.warehouse.StaLine;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationStatus;
import com.jumbo.wms.model.warehouse.StockTransApplicationType;
import com.jumbo.wms.model.warehouse.StvLineCommand;
import com.jumbo.wms.model.warehouse.WhInfoTimeRefBillType;
import com.jumbo.wms.model.warehouse.WhInfoTimeRefNodeType;

/**
 * @author lichuan
 * 
 */
@Transactional
@Service("espReceiveDeliveryManager")
public class ESPReceiveDeliveryManagerImpl extends BaseManagerImpl implements ESPReceiveDeliveryManager {

    private static final long serialVersionUID = -4465885059770099517L;

    @Autowired
    private ESPDeliveryReceiveDao espDeliveryReceiveDao;
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private OperationUnitDao ouDao;
    @Autowired
    private BiChannelDao shopDao;
    @Autowired
    private InventoryStatusDao inventoryStatusDao;
    @Autowired
    private StaLineDao staLineDao;
    @Autowired
    private SequenceManager sequenceManager;
    @Autowired
    private WareHouseManagerExe wmExe;
    @Autowired
    private WhInfoTimeRefDao whInfoTimeRefDao;
    @Autowired
    private WareHouseManager wareHouseManager;
    @Autowired
    private WareHouseManagerQuery wareHouseManagerQuery;
    @Autowired
    private BaseinfoManager baseinfoManager;
    @Autowired
    private ESPDeliveryDao espDeliveryDao;
    @Autowired
    private Rmi4Wms rmi4Wms;

    /**
     * 日志记录日常信息，解决BusinessException无法获取信息
     * 
     * @param e
     */
    public void loggerErrorMsg(Exception e) {
        if (e instanceof BusinessException) {
            BusinessException be = (BusinessException) e;
            log.error("throw BusinessException, ErrorCode : {}", be.getErrorCode());
        } else {
            log.error(e.getMessage());
        }
    }

    /**
     * 将Esprit Dispatch信息保存入库
     */
    @Override
    public void saveEspritDispatchDate(EspDispatch root, File sourceFile, String backupPath) throws IOException {
        if (sourceFile.exists()) {
            try {
                for (Pick pick : root.getList().getPick()) {
                    for (Item item : pick.getItems().getItem()) {
                        ESPDeliveryReceive receive = new ESPDeliveryReceive();
                        receive.sethFromNode(root.getHeader().getFromNode());
                        receive.sethToNode(root.getHeader().getToNode());
                        receive.sethFromGln(root.getHeader().getFromGLN());
                        receive.sethToGln(root.getHeader().getToGLN());
                        receive.sethBatchNo(root.getHeader().getBatchNo());
                        receive.sethNoOfRecord(root.getHeader().getNoOfRecord());
                        receive.sethGenerationDate(root.getHeader().getGenerationDate());
                        receive.sethGenerationTime(root.getHeader().getGenerationTime());
                        receive.setpCompany(pick.getCompany());
                        receive.setpLocation(pick.getLocation());
                        receive.setpPickNo(pick.getPickNo());
                        receive.setpFromLocation(pick.getFromLocation());
                        receive.setpOrderFlag(pick.getOrderFlag());
                        receive.setpReqDeliveryDate(pick.getReqDeliveryDate());
                        receive.setpLatestReqDeliveryDate(pick.getLatestReqDeliveryDate());
                        receive.setpPromotionStartDate(pick.getPromotionStartDate());
                        receive.setpEdiStatus(pick.getEdiStatus());
                        receive.setpRouteCode(pick.getRouteCode());
                        receive.setpRemark1(pick.getRemark1());
                        receive.setpRemark2(pick.getRemark2());
                        receive.setpRemark3(pick.getRemark3());
                        receive.setpRemark4(pick.getRemark4());
                        receive.setpPickType(pick.getPickType());
                        receive.setpFromLocGln(pick.getFromLocGLN());
                        receive.setpToLocGln(pick.getToLocGLN());
                        receive.setpCheckDigit(pick.getCheckDigit());
                        receive.setItemSku(item.getSku());
                        receive.setItemQty(item.getQty());
                        receive.setItemSize(item.getSize());
                        receive.setItemStyle(item.getStyle());
                        receive.setItemStyleShortDesc(item.getStyleShortDesc());
                        receive.setItemSeason(item.getSeason());
                        receive.setItemYear(item.getYear());
                        receive.setItemColor(item.getColor());
                        receive.setItemColorDesc(item.getColorDesc());
                        receive.setItemDivision(item.getDivision());
                        receive.setCreateTime(new Date());
                        receive.setLastModifyTime(new Date());
                        espDeliveryReceiveDao.save(receive);
                        espDeliveryReceiveDao.flush();
                    }
                }
                log.debug("====>Esprit dispatch file [{}] save success", (null != sourceFile ? sourceFile.getName() : ""));
                FileUtils.moveToDirectory(sourceFile, new File(backupPath), true);
                log.debug("====>Esprit dispatch file [{}] move to backup path [{}]", (null != sourceFile ? sourceFile.getName() : ""), backupPath);
            } catch (Exception e) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR);
            }
        }
    }

    /**
     * 查接收数据
     */
    @Override
    public List<ESPDeliveryReceiveCommand> getReceiveDatasGroupByBatchNoAndPN(String status) {
        return espDeliveryReceiveDao.findReceiveDatasGroupByBatchNoAndPN(status, new BeanPropertyRowMapperExt<ESPDeliveryReceiveCommand>(ESPDeliveryReceiveCommand.class));
    }

    /**
     * 获取一批接收数据
     */
    @Override
    public List<ESPDeliveryReceive> findReceiveOrdersByBatchNoAndPN(String batchNo, String pickNo, String status) {
        return espDeliveryReceiveDao.findReceiveOrdersByBatchNoAndPN(batchNo, pickNo);
    }

    /**
     * 创入库单
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void generateNewInboundSta(ESPDeliveryReceiveCommand espComd, String vmiCode) {
        String batchNo = espComd.gethBatchNo();
        if (StringUtil.isEmpty(batchNo)) {
            log.error("====>Esprit dispatch data batchNo is null can not generate sta");
            throw new BusinessException(ErrorCode.VMI_ESPRIT_BATCH_NO_ISNULL);
        }
        String pickNo = espComd.getpPickNo();
        if (StringUtil.isEmpty(pickNo)) {
            log.error("====>Esprit dispatch data pickNo is null can not generate sta");
            throw new BusinessException(ErrorCode.VMI_ESPRIT_PICK_NO_ISNULL);
        }
        StockTransApplication sta = staDao.findStaBySlipCode(pickNo);
        if (null == sta) {
            if (log.isInfoEnabled()) {
                log.info("====>Esprit dispatch data batchNo [{}] and pickNo [{}] start generate sta", espComd.gethBatchNo(), espComd.getpPickNo());
            }
            generateSTAFromESPDispatch(espComd, vmiCode);
            if (log.isInfoEnabled()) {
                log.info("====>Esprit dispatch data batchNo [{}] and pickNo [{}] generate sta success", espComd.gethBatchNo(), espComd.getpPickNo());
            }
        } else {// 已创建
            Long staId = sta.getId();
            if (null == staId) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR);
            }
            log.error("====>Esprit dispatch data batchNo [{}] and pickNo [{}] has exist,can not generate sta", espComd.gethBatchNo(), espComd.getpPickNo());
            updateReceiveOrdersStatus(null, espComd.gethBatchNo(), espComd.getpPickNo(), null);
        }
    }

    /**
     * 创入库单
     */
    @Override
    public void generateInboundSta() {
        // 转入数据，创入库单
        List<ESPDeliveryReceiveCommand> esList = getReceiveDatasGroupByBatchNoAndPN(null);
        for (ESPDeliveryReceiveCommand espComd : esList) {
            try {
                generateNewInboundSta(espComd, Constants.ESPRIT_VIM_CODE);
            } catch (Exception e) {
                updateReceiveOrdersStatus(null, espComd.gethBatchNo(), espComd.getpPickNo(), null);
                /*
                 * List<ESPDeliveryReceive> orderList =
                 * findReceiveOrdersByBatchNoAndPN(espComd.gethBatchNo(), espComd.getpPickNo(),
                 * espComd.getpEdiStatus()); for(ESPDeliveryReceive dr : orderList){
                 * dr.setStaId(null); dr.setLastModifyTime(new Date()); dr.setVersion(new Date());
                 * espDeliveryReceiveDao.save(dr); }
                 */
                log.error("====>Esprit dispatch data generate sta error");
                loggerErrorMsg(e);
            }
        }

    }

    /**
     * 更新状态为已创入库单
     */
    @Override
    public void updateReceiveOrdersStatus(Long staId, String batchNo, String pickNo, String status) {
        espDeliveryReceiveDao.updateReceiveOrdersStatus(staId, batchNo, status, pickNo);
    }

    /**
     * 创入库单
     */
    public void generateSTAFromESPDispatch(ESPDeliveryReceiveCommand espComd, String vmiCode) {
        List<ESPDeliveryReceive> orderList = findReceiveOrdersByBatchNoAndPN(espComd.gethBatchNo(), espComd.getpPickNo(), espComd.getpEdiStatus());
        if (0 == orderList.size()) {
            log.error("====>Esprit dispatch data batchNo [{}] and pickNo [{}] generate sta error, receive data not exist", espComd.gethBatchNo(), espComd.getpPickNo());
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        StockTransApplication sta = new StockTransApplication();
        sta.setType(StockTransApplicationType.VMI_INBOUND_CONSIGNMENT);
        sta.setBusinessSeqNo(staDao.getBusinessSeqNo(new SingleColumnRowMapper<BigDecimal>()).longValue());
        sta.setCreateTime(new Date());
        sta.setLastModifyTime(new Date());
        sta.setStatus(StockTransApplicationStatus.CREATED);
        sta.setCode(sequenceManager.getCode(StockTransApplication.class.getName(), sta));

        /**
         * pickType如果为R 则设置为sta。isEsprit 为1
         */
        if (espComd.getpPickType() != null && "R".equals(espComd.getpPickType())) {
            sta.setIsEsprit(1);
        }
        // /////

        BiChannel shop = shopDao.getByVmiCode(vmiCode);
        if (shop == null) {
            log.debug("=========VMICODE {} NOT FOUNT SHOP===========", new Object[] {vmiCode});
            throw new BusinessException(ErrorCode.END_OPERATION_UNIT_NOT_FOUNT);
        }
        wmExe.validateBiChannelSupport(null, shop.getCode());
        Long companyId = null;
        OperationUnit ou = ouDao.getDefaultInboundWhByShopId(shop.getId());

        if (ou == null) {
            log.debug("=========OPERATION UNIT {} NOT FOUNT===========", new Object[] {vmiCode});
            throw new BusinessException(ErrorCode.END_OPERATION_UNIT_NOT_FOUNT);
        }
        if (ou.getParentUnit() != null) {
            OperationUnit ou1 = ouDao.getByPrimaryKey(ou.getParentUnit().getId());
            if (ou1 != null) {
                companyId = ou1.getParentUnit().getId();
            }
        }
        // 订单状态与账号关联
        if (null != sta && !StringUtil.isEmpty(sta.getRefSlipCode())) {
            whInfoTimeRefDao.insertWhInfoTime2(sta.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.CREATE.getValue(), null, ou.getId());
        } else if (null != sta && !StringUtil.isEmpty(sta.getCode())) {
            whInfoTimeRefDao.insertWhInfoTime2(sta.getCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.CREATE.getValue(), null, ou.getId());
        }
        InventoryStatus is = inventoryStatusDao.findInvStatusForSale(companyId);
        sta.setMainStatus(is);
        sta.setOwner(shop.getCode());
        sta.setMainWarehouse(ou);
        String po = "";
        sta = staDao.save(sta);
        Long customerId = wareHouseManagerQuery.getCustomerByWhouid(ou.getId());
        boolean isNoSkuError = false;
        List<String> skuCodes = new ArrayList<String>();
        String fromLocGLN = null;
        String toLocGLN = null;
        for (ESPDeliveryReceive order : orderList) {
            if (order.getItemSku() == null) {
                throw new BusinessException(ErrorCode.SKU_NOT_FOUND, new Object[] {order.getItemSku()});
            }
            Sku sku = wareHouseManager.getByExtCode2AndCustomerAndShopId(order.getItemSku(), customerId, shop.getId());
            if (sku == null) {
                baseinfoManager.sendMsgToOmsCreateSku(order.getItemSku(), shop.getVmiCode());
                isNoSkuError = true;
                skuCodes.add(order.getItemSku());
                break;
            }
            StaLine staLine = new StaLine();
            // Long qty = Long.parseLong(order.getItemQty());
            Long qty = order.getItemQty();
            staLine.setQuantity(qty);
            staLine.setSku(sku);
            staLine.setCompleteQuantity(0L);
            staLine.setSta(sta);
            staLine.setInvStatus(is);
            staLine.setOwner(sta.getOwner());
            staLine = staLineDao.save(staLine);
            order.setStaId(sta.getId());
            order.setLastModifyTime(new Date());
            espDeliveryReceiveDao.save(order);
            po = order.getpPickNo();
            if (null == fromLocGLN) {
                fromLocGLN = order.getpFromLocGln();
            }
            if (null == toLocGLN) {
                toLocGLN = order.getpToLocGln();
            }
        }
        if (isNoSkuError) {
            log.error("====>Esprit dispatch data pickNo [{}] generate sta error, sku not exist and ext_code2 is :[{}]", po, skuCodes.toString());
            throw new BusinessException(ErrorCode.SKU_NOT_FOUND, new Object[] {skuCodes.toString()});
        }
        sta.setInterfaceType(VmiEsprit.INTERFACETYPE);
        sta.setRefSlipCode(po);
        sta.setFromLocation(fromLocGLN);
        sta.setToLocation(toLocGLN);
        staDao.save(sta);
        staDao.flush();
        staDao.updateSkuQtyById(sta.getId());
        updateReceiveOrdersStatus(sta.getId(), espComd.gethBatchNo(), espComd.getpPickNo(), null);
    }

    /** 
     *
     */
    @Override
    public List<ESPDeliveryReceiveCommand> findReceiveOrdersByStaId(Long staId) {
        return espDeliveryReceiveDao.findReceiveOrdersByStaId(staId, new BeanPropertyRowMapperExt<ESPDeliveryReceiveCommand>(ESPDeliveryReceiveCommand.class));
    }

    /**
     * 获取当前上架的明细
     */
    @Override
    public List<ESPDeliveryReceiveCommand> findReceiveOrdersByShelveStvId(Long stvId) {
        return espDeliveryReceiveDao.findReceiveOrdersByShelveStvId(stvId, new BeanPropertyRowMapperExt<ESPDeliveryReceiveCommand>(ESPDeliveryReceiveCommand.class));
    }

    /**
     * 获取所有上架的明细
     */
    @Override
    public List<ESPDeliveryReceiveCommand> findShelveReceiveOrdersByStaId(Long staId) {
        return espDeliveryReceiveDao.findShelveReceiveOrdersByStaId(staId, new BeanPropertyRowMapperExt<ESPDeliveryReceiveCommand>(ESPDeliveryReceiveCommand.class));
    }

    /**
     * 根据关单staId查找所有未收货数据
     */
    @Override
    public List<ESPDeliveryReceiveCommand> findReceiveOrdersByCloseStaId(Long staId) {
        return espDeliveryReceiveDao.findReceiveOrdersByCloseStaId(staId, new BeanPropertyRowMapperExt<ESPDeliveryReceiveCommand>(ESPDeliveryReceiveCommand.class));
    }

    /**
     * 生成全量入库xml反馈数据
     * 
     * @throws Exception
     */
    @Override
    public String marshallerInboundDeliveryData(List<ESPDeliveryReceiveCommand> list, StockTransApplication sta, Date transDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        EspDelivery espD = new EspDelivery();
        Header head = new Header();
        head.setFromGLN(ESPDelivery.DELIVERED_FROM_GLN);
        head.setToGLN(ESPDelivery.DELIVERED_TO_GLN);
        head.setFromNode(ESPDelivery.DELIVERED_FROM_NODE);
        head.setToNode(ESPDelivery.DELIVERED_TO_NODE);
        // head.setSequenceNumber("");
        head.setNumberOfRecords(String.valueOf(sta.getSkuQty()));
        head.setGenerationDate(sdf.format(transDate));
        sdf = new SimpleDateFormat("HHmmss");
        head.setGenerationTime(sdf.format(transDate));
        Deliveries ds = new Deliveries();
        Delivery delivery = new Delivery();
        delivery.setDeliveryNo(sta.getRefSlipCode());
        sdf = new SimpleDateFormat("yyyyMMdd");
        delivery.setDeliveryDate(sdf.format(transDate));
        delivery.setGoodsReceiptDate(sdf.format(sta.getCreateTime()));
        delivery.setDeliveryType(ESPDelivery.DELIVERED_TYPE);
        delivery.setDeliveryStatus(ESPDelivery.DELIVERED_STATUS);
        delivery.setDeliveredFromGLN(sta.getFromLocation());
        delivery.setDeliveredToGLN(sta.getToLocation());
        Items items = new Items();
        com.jumbo.wms.model.command.vmi.esprit.xml.espDelilvery.Item item = null;
        for (ESPDeliveryReceiveCommand r : list) {
            ESPDeliveryReceiveCommand cmd = r;
            item = new com.jumbo.wms.model.command.vmi.esprit.xml.espDelilvery.Item();
            String skuCode = cmd.getExtCode2();
            if (StringUtil.isEmpty(skuCode)) {
                throw new BusinessException(ErrorCode.VMI_SKU_CODE_ERROR);
            }
            item.setSku(skuCode);
            item.setReceivedQty(Long.valueOf(cmd.getSkuQty()));
            items.getItem().add(item);

        }
        delivery.setItems(items);
        ds.getDelivery().add(delivery);
        espD.setDeliveries(ds);
        espD.setHeader(head);
        try {
            // 生成xml
            JAXBContext jaxbContext = JAXBContext.newInstance(EspDelivery.class);
            StringWriter sw = new StringWriter();
            Marshaller marshaller = jaxbContext.createMarshaller();
            // marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(espD, sw);
            return sw.toString();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 生成执行出库xml反馈数据
     */
    @Override
    public String marshallerOutboundDeliveryData(List<StvLineCommand> list, StockTransApplication sta, Date transDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        EspDelivery espD = new EspDelivery();
        Header head = new Header();
        head.setFromGLN(ESPDelivery.DELIVERED_FROM_GLN);
        head.setToGLN(ESPDelivery.DELIVERED_TO_GLN);
        head.setFromNode(ESPDelivery.DELIVERED_FROM_NODE);
        head.setToNode(ESPDelivery.DELIVERED_TO_NODE);
        // head.setSequenceNumber("");
        head.setNumberOfRecords(String.valueOf(sta.getSkuQty()));
        head.setGenerationDate(sdf.format(transDate));
        sdf = new SimpleDateFormat("HHmmss");
        head.setGenerationTime(sdf.format(transDate));
        Deliveries ds = new Deliveries();
        Delivery delivery = new Delivery();
        delivery.setDeliveryNo(sta.getRefSlipCode());
        sdf = new SimpleDateFormat("yyyyMMdd");
        delivery.setDeliveryDate(sdf.format(transDate));
        delivery.setGoodsReceiptDate(sdf.format(sta.getCreateTime()));
        delivery.setDeliveryType(ESPDelivery.DELIVERED_TYPE);
        delivery.setDeliveryStatus(ESPDelivery.DELIVERED_STATUS);
        delivery.setDeliveredFromGLN(sta.getFromLocation());
        delivery.setDeliveredToGLN(sta.getToLocation());
        Items items = new Items();
        com.jumbo.wms.model.command.vmi.esprit.xml.espDelilvery.Item item = null;
        for (StvLineCommand r : list) {
            StvLineCommand cmd = r;
            item = new com.jumbo.wms.model.command.vmi.esprit.xml.espDelilvery.Item();
            String skuCode = cmd.getExtCode2();
            if (StringUtil.isEmpty(skuCode)) {
                throw new BusinessException(ErrorCode.VMI_SKU_CODE_ERROR);
            }
            item.setSku(skuCode);
            item.setReceivedQty(Long.valueOf(cmd.getQuantity()));
            items.getItem().add(item);

        }
        delivery.setItems(items);
        ds.getDelivery().add(delivery);
        espD.setDeliveries(ds);
        espD.setHeader(head);
        try {
            // 生成xml
            JAXBContext jaxbContext = JAXBContext.newInstance(EspDelivery.class);
            StringWriter sw = new StringWriter();
            Marshaller marshaller = jaxbContext.createMarshaller();
            // marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(espD, sw);
            return sw.toString();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取一批待反馈数据
     */
    @Override
    public List<ESPDeliveryCommand> findDeliveryDatasGroupByStaCode() {
        return espDeliveryDao.findDeliveryDatasGroupByStaCode(new BeanPropertyRowMapperExt<ESPDeliveryCommand>(ESPDeliveryCommand.class));
    }

    /**
     * 获取一批待反馈数据2
     */
    @Override
    public List<ESPDeliveryCommand> findDeliveryDatasGroupByStaCode2() {
        return espDeliveryDao.findDeliveryDatasGroupByStaCode2(new BeanPropertyRowMapperExt<ESPDeliveryCommand>(ESPDeliveryCommand.class));
    }

    private List<ESPDeliveryCommand> findDeliveryDatasGroupByStaCode(int staType) {
        return espDeliveryDao.findAllDeliveryDatasGroupByStaCode(staType, new BeanPropertyRowMapperExt<ESPDeliveryCommand>(ESPDeliveryCommand.class));
    }

    private List<ESPDeliveryCommand> findDeliveryDatasGroupByStaCode2(int staType) {
        return espDeliveryDao.findAllDeliveryDatasGroupByStaCode2(staType, new BeanPropertyRowMapperExt<ESPDeliveryCommand>(ESPDeliveryCommand.class));
    }

    /**
     * 获取一批关单反馈数据
     */
    @Override
    public List<ESPDeliveryCommand> findCloseDeliveryDatasGroupByStaCode() {
        return espDeliveryDao.findCloseDeliveryDatasGroupByStaCode(new BeanPropertyRowMapperExt<ESPDeliveryCommand>(ESPDeliveryCommand.class));
    }

    /**
     * 获取一批关单反馈数据2
     */
    @Override
    public List<ESPDeliveryCommand> findCloseDeliveryDatasGroupByStaCode2() {
        return espDeliveryDao.findCloseDeliveryDatasGroupByStaCode2(new BeanPropertyRowMapperExt<ESPDeliveryCommand>(ESPDeliveryCommand.class));
    }

    /**
     * 转入转出反馈
     */
    @Override
    public void inOutBoundRtn() {
        List<ESPDeliveryCommand> esList = findDeliveryDatasGroupByStaCode();
        for (ESPDeliveryCommand espCmd : esList) {
            try {
                inOutBoundDeliveryRtn(espCmd.getStaCode());
            } catch (Exception e) {
                log.error("====>Esprit delivery data feedback error, staCode:[{}]", espCmd.getStaCode());
                // espDeliveryDao.updateStatusByStaCode(ESPDelivery.STATUS_0, espCmd.getStaCode());
                loggerErrorMsg(e);
            }
        }
    }

    /**
     * 转入转出反馈2
     */
    @Override
    public void inOutBoundRtn2() {
        List<ESPDeliveryCommand> esList = findDeliveryDatasGroupByStaCode2();
        for (ESPDeliveryCommand espCmd : esList) {
            try {
                inOutBoundDeliveryRtn(espCmd.getStaCode());
            } catch (Exception e) {
                log.error("====>Esprit2 delivery data feedback error, staCode:[{}]", espCmd.getStaCode());
                // espDeliveryDao.updateStatusByStaCode(ESPDelivery.STATUS_0, espCmd.getStaCode());
                loggerErrorMsg(e);
            }
        }
    }



    /**
     * 关单反馈
     */
    @Override
    public void inBoundCloseRtn() {
        List<ESPDeliveryCommand> esList = findCloseDeliveryDatasGroupByStaCode();
        for (ESPDeliveryCommand espCmd : esList) {
            try {
                inBoundDeliveryCloseRtn(espCmd.getStaCode());
            } catch (Exception e) {
                log.error("====>Esprit delivery data feedback error, staCode:[{}]", espCmd.getStaCode());
                // espDeliveryDao.updateStatusByStaCode(ESPDelivery.STATUS_1, espCmd.getStaCode());
                loggerErrorMsg(e);
            }
        }
    }

    /**
     * 关单反馈2
     */
    @Override
    public void inBoundCloseRtn2() {
        List<ESPDeliveryCommand> esList = findCloseDeliveryDatasGroupByStaCode2();
        for (ESPDeliveryCommand espCmd : esList) {
            try {
                inBoundDeliveryCloseRtn(espCmd.getStaCode());
            } catch (Exception e) {
                log.error("====>Esprit2 delivery data feedback error, staCode:[{}]", espCmd.getStaCode());
                // espDeliveryDao.updateStatusByStaCode(ESPDelivery.STATUS_1, espCmd.getStaCode());
                loggerErrorMsg(e);
            }
        }
    }

    /**
     * 转入转出反馈
     */
    @Override
    public void inOutBoundDeliveryRtn(String staCode) {
        StockTransApplication sta = staDao.getByCode(staCode);
        List<ESPDelivery> epList = espDeliveryDao.getDeliveryDatasByStaCode(staCode);
        boolean hasBatchNo = true;
        String batchCode = "";
        String batchNo = "";
        for (ESPDelivery ep : epList) {
            String bNo = ep.getBatchCodes();
            if (StringUtil.isEmpty(bNo)) {
                hasBatchNo = false;
            } else {
                if (!StringUtil.isEmpty(bNo)) {
                    batchNo = bNo;
                }
            }
        }
        if (false == hasBatchNo) {
            BigDecimal batchId = espDeliveryDao.findBatchId(new SingleColumnRowMapper<BigDecimal>(BigDecimal.class));
            batchCode = "wms_" + batchId.toString();
            for (ESPDelivery ep : epList) {
                Long id = ep.getId();
                if (null != id) {
                    ESPDelivery epd = espDeliveryDao.getByPrimaryKey(id);
                    if (StringUtil.isEmpty(epd.getBatchCodes())) {
                        epd.setBatchCodes(batchCode);
                        epd.setVersion(new Date());
                        espDeliveryDao.save(epd);
                    }
                }
            }
            espDeliveryDao.flush();
        } else {
            batchCode = batchNo;
        }
        batchCode = (StringUtil.isEmpty(batchCode) ? null : batchCode);
        if (null == batchCode) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        Date transDate = new Date();
        List<ESPDeliveryCommand> list = findDeliveryDatasByStaCode(staCode, batchCode);
        Long skuQtys = 0L;
        String deliveryNo = "";
        for (ESPDeliveryCommand esp : list) {
            String qty = esp.getItemReceivedQTY();
            skuQtys += (StringUtils.isEmpty(qty) ? 0L : Long.valueOf(qty));
            if (!StringUtils.isEmpty(esp.getDeliveryDeliveryNO()) && "".equals(deliveryNo)) {
                deliveryNo = esp.getDeliveryDeliveryNO();
            }
        }
        if (0 == skuQtys) {
            return;
        }
        // 合并重复明细
        List<ESPDeliveryCommand> espList = new ArrayList<ESPDeliveryCommand>();
        for (ESPDeliveryCommand esp : list) {
            ESPDeliveryCommand cmd = esp;
            cmd.setHeaderNumberofRecords(String.valueOf(skuQtys));
            cmd.setDeliveryDeliveryNO(deliveryNo);
            if (espList.size() > 0) {
                boolean isExist = false;
                String extCode2 = cmd.getItemSku();
                if (StringUtil.isEmpty(extCode2)) {
                    throw new BusinessException(ErrorCode.VMI_SKU_CODE_ERROR);
                }
                ListIterator<ESPDeliveryCommand> iter = espList.listIterator();
                while (iter.hasNext()) {
                    ESPDeliveryCommand c = iter.next();
                    if (!StringUtils.isEmpty(extCode2)) {
                        if (extCode2.equals(c.getItemSku())) {
                            isExist = true;
                            Long oldQty = (StringUtils.isEmpty(c.getItemReceivedQTY()) ? 0L : Long.valueOf(c.getItemReceivedQTY()));
                            Long newQty = (StringUtils.isEmpty(cmd.getItemReceivedQTY()) ? 0L : Long.valueOf(cmd.getItemReceivedQTY()));
                            Long reQty = oldQty + newQty;
                            c.setItemReceivedQTY(String.valueOf(reQty));
                            iter.set(c);
                            break;
                        }
                    }
                }
                if (false == isExist) {
                    espList.add(cmd);
                }
            } else {
                espList.add(cmd);
            }
        }
        String xml = marshallerInOutBoundDeliveryData(espList, sta, transDate);
        if (null == xml) {
            throw new BusinessException(ErrorCode.VMI_GENERATE_RESPONSE_DATA_ERROR);
        }
        // SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        try {
            // 调Pacs接口
            if (log.isInfoEnabled()) {
                log.error("====>Esprit call pacs feedback interface begain, batchCode:[{}], staCode:[{}], slipCode:[{}]", new Object[] {batchCode, sta.getCode(), sta.getRefSlipCode()});
            }
            BaseResult rs = rmi4Wms.espritDeliveryDataSync(batchCode, transDate, xml);
            if (rs.getStatus() == 0) {
                espDeliveryDao.updateStatusByStaCode(ESPDelivery.STATUS_0, sta.getCode(), batchCode);
                throw new BusinessException(ErrorCode.OMS_SYSTEM_ERROR, new Object[] {rs.getMsg()});
            }
            if (log.isInfoEnabled()) {
                log.error("====>Esprit call pacs feedback interface end, batchCode:[{}], staCode:[{}], slipCode:[{}]", new Object[] {batchCode, sta.getCode(), sta.getRefSlipCode()});
            }
            espDeliveryDao.updateStatusByStaCode(ESPDelivery.STATUS_END, sta.getCode(), batchCode);
        } catch (BusinessException e) {
            espDeliveryDao.updateStatusByStaCode(ESPDelivery.STATUS_0, sta.getCode(), batchCode);
            log.error("====>Esprit Call pacs feedback interface error, batchCode:[{}], staCode:[{}], slipCode:[{}]", new Object[] {batchCode, sta.getCode(), sta.getRefSlipCode()});
            log.error("====>Esprit Call pacs feedback interface error, batchCode:[" + batchCode + "]", e);
            throw e;
        } catch (Exception e) {
            espDeliveryDao.updateStatusByStaCode(ESPDelivery.STATUS_0, sta.getCode(), batchCode);
            log.error("====>Esprit call pacs feedback error, batchCode:[{}], staCode:[{}], slipCode:[{}]", new Object[] {batchCode, sta.getCode(), sta.getRefSlipCode()});
            log.error("====>Esprit call pacs feedback error, batchCode:[" + batchCode + "]", e);
            throw new BusinessException(ErrorCode.VMI_ESPRIT_TO_PACS_ERROR);
        }

    }

    /**
     * 转出反馈
     */
    private void outBoundDeliveryRtn(String staCode) {
        StockTransApplication sta = staDao.getByCode(staCode);
        List<ESPDelivery> epList = espDeliveryDao.getDeliveryDatasByStaCode(staCode);
        boolean hasBatchNo = true;
        String batchCode = "";
        String batchNo = "";
        for (ESPDelivery ep : epList) {
            String bNo = ep.getBatchCodes();
            if (StringUtil.isEmpty(bNo)) {
                hasBatchNo = false;
            } else {
                if (!StringUtil.isEmpty(bNo)) {
                    batchNo = bNo;
                }
            }
        }
        if (false == hasBatchNo) {
            BigDecimal batchId = espDeliveryDao.findBatchId(new SingleColumnRowMapper<BigDecimal>(BigDecimal.class));
            batchCode = "wms_" + batchId.toString();
            for (ESPDelivery ep : epList) {
                Long id = ep.getId();
                if (null != id) {
                    ESPDelivery epd = espDeliveryDao.getByPrimaryKey(id);
                    if (StringUtil.isEmpty(epd.getBatchCodes())) {
                        epd.setBatchCodes(batchCode);
                        epd.setVersion(new Date());
                        espDeliveryDao.save(epd);
                    }
                }
            }
            espDeliveryDao.flush();
        } else {
            batchCode = batchNo;
        }
        batchCode = (StringUtil.isEmpty(batchCode) ? null : batchCode);
        if (null == batchCode) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        Date transDate = new Date();
        List<ESPDeliveryCommand> list = findDeliveryDatasByStaCode(staCode, batchCode);
        Long skuQtys = 0L;
        String deliveryNo = "";
        for (ESPDeliveryCommand esp : list) {
            String qty = esp.getItemReceivedQTY();
            skuQtys += (StringUtils.isEmpty(qty) ? 0L : Long.valueOf(qty));
            if (!StringUtils.isEmpty(esp.getDeliveryDeliveryNO()) && "".equals(deliveryNo)) {
                deliveryNo = esp.getDeliveryDeliveryNO();
            }
        }
        if (0 == skuQtys) {
            return;
        }
        // 合并重复明细
        List<ESPDeliveryCommand> espList = new ArrayList<ESPDeliveryCommand>();
        for (ESPDeliveryCommand esp : list) {
            ESPDeliveryCommand cmd = esp;
            cmd.setHeaderNumberofRecords(String.valueOf(skuQtys));
            cmd.setDeliveryDeliveryNO(deliveryNo);
            if (espList.size() > 0) {
                boolean isExist = false;
                String extCode2 = cmd.getItemSku();
                if (StringUtil.isEmpty(extCode2)) {
                    throw new BusinessException(ErrorCode.VMI_SKU_CODE_ERROR);
                }
                ListIterator<ESPDeliveryCommand> iter = espList.listIterator();
                while (iter.hasNext()) {
                    ESPDeliveryCommand c = iter.next();
                    if (!StringUtils.isEmpty(extCode2)) {
                        if (extCode2.equals(c.getItemSku())) {
                            isExist = true;
                            Long oldQty = (StringUtils.isEmpty(c.getItemReceivedQTY()) ? 0L : Long.valueOf(c.getItemReceivedQTY()));
                            Long newQty = (StringUtils.isEmpty(cmd.getItemReceivedQTY()) ? 0L : Long.valueOf(cmd.getItemReceivedQTY()));
                            Long reQty = oldQty + newQty;
                            c.setItemReceivedQTY(String.valueOf(reQty));
                            iter.set(c);
                            break;
                        }
                    }
                }
                if (false == isExist) {
                    espList.add(cmd);
                }
            } else {
                espList.add(cmd);
            }
        }
        String xml = marshallerInOutBoundDeliveryData(espList, sta, transDate);
        if (null == xml) {
            throw new BusinessException(ErrorCode.VMI_GENERATE_RESPONSE_DATA_ERROR);
        }
        // SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        try {
            // 调Pacs接口
            if (log.isInfoEnabled()) {
                log.error("====>Esprit call pacs feedback interface begain, batchCode:[{}], staCode:[{}], slipCode:[{}]", new Object[] {batchCode, sta.getCode(), sta.getRefSlipCode()});
            }
            BaseResult rs = rmi4Wms.espritDeliveryDataSync(batchCode, transDate, xml);
            if (rs.getStatus() == 0) {
                espDeliveryDao.updateStatusByStaCode(ESPDelivery.STATUS_0, sta.getCode(), batchCode);
                throw new BusinessException(ErrorCode.OMS_SYSTEM_ERROR, new Object[] {rs.getMsg()});
            }
            if (log.isInfoEnabled()) {
                log.error("====>Esprit call pacs feedback interface end, batchCode:[{}], staCode:[{}], slipCode:[{}]", new Object[] {batchCode, sta.getCode(), sta.getRefSlipCode()});
            }
            espDeliveryDao.updateStatusByStaCode(ESPDelivery.STATUS_END, sta.getCode(), batchCode);
        } catch (BusinessException e) {
            espDeliveryDao.updateStatusByStaCode(ESPDelivery.STATUS_0, sta.getCode(), batchCode);
            log.error("====>Esprit Call pacs feedback interface error, batchCode:[{}], staCode:[{}], slipCode:[{}]", new Object[] {batchCode, sta.getCode(), sta.getRefSlipCode()});
            log.error("====>Esprit Call pacs feedback interface error, batchCode:[" + batchCode + "]", e);
            throw e;
        } catch (Exception e) {
            espDeliveryDao.updateStatusByStaCode(ESPDelivery.STATUS_0, sta.getCode(), batchCode);
            log.error("====>Esprit call pacs feedback error, batchCode:[{}], staCode:[{}], slipCode:[{}]", new Object[] {batchCode, sta.getCode(), sta.getRefSlipCode()});
            log.error("====>Esprit call pacs feedback error, batchCode:[" + batchCode + "]", e);
            throw new BusinessException(ErrorCode.VMI_ESPRIT_TO_PACS_ERROR);
        }

    }

    /**
     * 转入反馈
     * 
     * @param staCode void
     * @throws
     */
    private void inBoundDeliverySaveFile(String staCode, String localUpPath) {
        StockTransApplication sta = staDao.getByCode(staCode);
        List<ESPDelivery> epList = espDeliveryDao.getDeliveryDatasByStaCode(staCode);
        boolean hasBatchNo = true;
        String batchCode = "";
        String batchNo = "";
        if (null == epList || 0 == epList.size()) return;
        for (ESPDelivery ep : epList) {
            String bNo = ep.getBatchCodes();
            if (StringUtil.isEmpty(bNo)) {
                hasBatchNo = false;
            } else {
                if (!StringUtil.isEmpty(bNo)) {
                    batchNo = bNo;
                }
            }
        }
        if (false == hasBatchNo) {
            batchCode = espDeliveryDao.findDispatchHeaderSeq(new SingleColumnRowMapper<String>(String.class));
            log.error("====>Esprit inbound feedback generate batchCode, batchCode:[{}], staCode:[{}], slipCode:[{}]", new Object[] {batchCode, sta.getCode(), sta.getRefSlipCode()});
            for (ESPDelivery ep : epList) {
                Long id = ep.getId();
                if (null != id) {
                    ESPDelivery epd = espDeliveryDao.getByPrimaryKey(id);
                    if (StringUtil.isEmpty(epd.getBatchCodes())) {
                        epd.setBatchCodes(batchCode);
                        epd.setVersion(new Date());
                        espDeliveryDao.save(epd);
                    }
                }
            }
            espDeliveryDao.flush();
        } else {
            batchCode = batchNo;
        }
        batchCode = (StringUtil.isEmpty(batchCode) ? null : batchCode);
        if (null == batchCode) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        Date transDate = new Date();
        List<ESPDeliveryCommand> list = findDeliveryDatasByStaCode(staCode, batchCode);
        Long skuQtys = 0L;
        String deliveryNo = "";
        for (ESPDeliveryCommand esp : list) {
            String qty = esp.getItemReceivedQTY();
            skuQtys += (StringUtils.isEmpty(qty) ? 0L : Long.valueOf(qty));
            if (!StringUtils.isEmpty(esp.getDeliveryDeliveryNO()) && "".equals(deliveryNo)) {
                deliveryNo = esp.getDeliveryDeliveryNO();
            }
        }
        if (0 == skuQtys) {
            return;
        }
        // 合并重复明细
        List<ESPDeliveryCommand> espList = new ArrayList<ESPDeliveryCommand>();
        for (ESPDeliveryCommand esp : list) {
            ESPDeliveryCommand cmd = esp;
            cmd.setHeaderNumberofRecords(String.valueOf(skuQtys));
            cmd.setDeliveryDeliveryNO(deliveryNo);
            if (espList.size() > 0) {
                boolean isExist = false;
                String extCode2 = cmd.getItemSku();
                if (StringUtil.isEmpty(extCode2)) {
                    throw new BusinessException(ErrorCode.VMI_SKU_CODE_ERROR);
                }
                ListIterator<ESPDeliveryCommand> iter = espList.listIterator();
                while (iter.hasNext()) {
                    ESPDeliveryCommand c = iter.next();
                    if (!StringUtils.isEmpty(extCode2)) {
                        if (extCode2.equals(c.getItemSku())) {
                            isExist = true;
                            Long oldQty = (StringUtils.isEmpty(c.getItemReceivedQTY()) ? 0L : Long.valueOf(c.getItemReceivedQTY()));
                            Long newQty = (StringUtils.isEmpty(cmd.getItemReceivedQTY()) ? 0L : Long.valueOf(cmd.getItemReceivedQTY()));
                            Long reQty = oldQty + newQty;
                            c.setItemReceivedQTY(String.valueOf(reQty));
                            iter.set(c);
                            break;
                        }
                    }
                }
                if (false == isExist) {
                    espList.add(cmd);
                }
            } else {
                espList.add(cmd);
            }
        }
        String xml = marshallerInBoundDeliveryData(espList, sta, transDate, batchCode);
        if (null == xml) {
            throw new BusinessException(ErrorCode.VMI_GENERATE_RESPONSE_DATA_ERROR);
        }
        String fromGLN = espList.get(0).getHeaderFromgln();
        String toGLN = espList.get(0).getHeaderTogln();
        // 生成文件
        String fileName = fromGLN + "_" + toGLN + "_espDelivery_" + FormatUtil.addCharForString(batchCode, '0', 8, 1) + Constants.FILE_EXTENSION_XML;
        try {
            FileUtil.writeStringToFile(new File(localUpPath + "/" + fileName), xml);
        } catch (IOException e) {
            espDeliveryDao.updateStatusByStaCode(ESPDelivery.STATUS_0, sta.getCode(), batchCode);
            log.error("====>Esprit save inbound feedback file error, batchCode:[{}], staCode:[{}], slipCode:[{}]", new Object[] {batchCode, sta.getCode(), sta.getRefSlipCode()});
            log.error("====>Esprit save inbound feedback file error, batchCode:[" + batchCode + "]", e);
        }
        espDeliveryDao.updateStatusByStaCode(ESPDelivery.STATUS_END, sta.getCode(), batchCode);
    }

    private void inBoundDeliveryCloseSaveFile(String staCode, String localUpPath) {
        StockTransApplication sta = staDao.getByCode(staCode);
        List<ESPDelivery> epList = espDeliveryDao.getCloseDeliveryDatasByStaCode(staCode);
        boolean hasBatchNo = true;
        String batchCode = "";
        String batchNo = "";
        if (null == epList || 0 == epList.size()) return;
        for (ESPDelivery ep : epList) {
            String bNo = ep.getBatchCodes();
            if (StringUtil.isEmpty(bNo)) {
                hasBatchNo = false;
            } else {
                if (!StringUtil.isEmpty(bNo)) {
                    batchNo = bNo;
                }
            }
        }
        if (false == hasBatchNo) {
            batchCode = espDeliveryDao.findDispatchHeaderSeq(new SingleColumnRowMapper<String>(String.class));
            log.error("====>Esprit inbound close feedback generate batchCode, batchCode:[{}], staCode:[{}], slipCode:[{}]", new Object[] {batchCode, sta.getCode(), sta.getRefSlipCode()});
            for (ESPDelivery ep : epList) {
                Long id = ep.getId();
                if (null != id) {
                    ESPDelivery epd = espDeliveryDao.getByPrimaryKey(id);
                    if (StringUtil.isEmpty(epd.getBatchCodes())) {
                        epd.setBatchCodes(batchCode);
                        epd.setVersion(new Date());
                        espDeliveryDao.save(epd);
                    }
                }
            }
            espDeliveryDao.flush();
        } else {
            batchCode = batchNo;
        }
        batchCode = (StringUtil.isEmpty(batchCode) ? null : batchCode);
        if (null == batchCode) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        Date transDate = new Date();
        List<ESPDeliveryCommand> list = findCloseDeliveryDatasByStaCode(staCode, batchCode);
        Long skuQtys = 0L;
        String deliveryNo = "";
        for (ESPDeliveryCommand esp : list) {
            String qty = esp.getItemReceivedQTY();
            skuQtys += (StringUtils.isEmpty(qty) ? 0L : Long.valueOf(qty));
            if (!StringUtils.isEmpty(esp.getDeliveryDeliveryNO()) && "".equals(deliveryNo)) {
                deliveryNo = esp.getDeliveryDeliveryNO();
            }
        }
        // 合并重复明细
        List<ESPDeliveryCommand> espList = new ArrayList<ESPDeliveryCommand>();
        for (ESPDeliveryCommand esp : list) {
            ESPDeliveryCommand cmd = esp;
            cmd.setHeaderNumberofRecords(String.valueOf(skuQtys));
            cmd.setDeliveryDeliveryNO(deliveryNo);
            if (espList.size() > 0) {
                boolean isExist = false;
                String extCode2 = cmd.getItemSku();
                if (StringUtil.isEmpty(extCode2)) {
                    throw new BusinessException(ErrorCode.VMI_SKU_CODE_ERROR);
                }
                ListIterator<ESPDeliveryCommand> iter = espList.listIterator();
                while (iter.hasNext()) {
                    ESPDeliveryCommand c = iter.next();
                    if (!StringUtils.isEmpty(extCode2)) {
                        if (extCode2.equals(c.getItemSku())) {
                            isExist = true;
                            Long oldQty = (StringUtils.isEmpty(c.getItemReceivedQTY()) ? 0L : Long.valueOf(c.getItemReceivedQTY()));
                            Long newQty = (StringUtils.isEmpty(cmd.getItemReceivedQTY()) ? 0L : Long.valueOf(cmd.getItemReceivedQTY()));
                            Long reQty = oldQty + newQty;
                            c.setItemReceivedQTY(String.valueOf(reQty));
                            iter.set(c);
                            break;
                        }
                    }
                }
                if (false == isExist) {
                    espList.add(cmd);
                }
            } else {
                espList.add(cmd);
            }
        }
        String xml = marshallerInBoundDeliveryData(espList, sta, transDate, batchCode);
        if (null == xml) {
            throw new BusinessException(ErrorCode.VMI_GENERATE_RESPONSE_DATA_ERROR);
        }
        String fromGLN = espList.get(0).getHeaderFromgln();
        String toGLN = espList.get(0).getHeaderTogln();
        // 生成文件
        String fileName = fromGLN + "_" + toGLN + "_espDelivery_" + FormatUtil.addCharForString(batchCode, '0', 8, 1) + Constants.FILE_EXTENSION_XML;
        try {
            FileUtil.writeStringToFile(new File(localUpPath + "/" + fileName), xml);
        } catch (IOException e) {
            espDeliveryDao.updateStatusByStaCode(ESPDelivery.STATUS_0, sta.getCode(), batchCode);
            log.error("====>Esprit save inbound close feedback file error, batchCode:[{}], staCode:[{}], slipCode:[{}]", new Object[] {batchCode, sta.getCode(), sta.getRefSlipCode()});
            log.error("====>Esprit save inbound close feedback file error, batchCode:[" + batchCode + "]", e);
        }
        espDeliveryDao.updateStatusByClosedStaCode(ESPDelivery.STATUS_END, sta.getCode(), batchCode);

    }

    /**
     * 转入关单反馈
     */
    @Override
    public void inBoundDeliveryCloseRtn(String staCode) {
        StockTransApplication sta = staDao.getByCode(staCode);
        List<ESPDelivery> epList = espDeliveryDao.getCloseDeliveryDatasByStaCode(staCode);
        boolean hasBatchNo = true;
        String batchCode = "";
        String batchNo = "";
        for (ESPDelivery ep : epList) {
            String bNo = ep.getBatchCodes();
            if (StringUtil.isEmpty(bNo)) {
                hasBatchNo = false;
            } else {
                if (!StringUtil.isEmpty(bNo)) {
                    batchNo = bNo;
                }
            }
        }
        if (false == hasBatchNo) {
            BigDecimal batchId = espDeliveryDao.findBatchId(new SingleColumnRowMapper<BigDecimal>(BigDecimal.class));
            batchCode = "wms_" + batchId.toString();
            for (ESPDelivery ep : epList) {
                Long id = ep.getId();
                if (null != id) {
                    ESPDelivery epd = espDeliveryDao.getByPrimaryKey(id);
                    if (StringUtil.isEmpty(epd.getBatchCodes())) {
                        epd.setBatchCodes(batchCode);
                        epd.setVersion(new Date());
                        espDeliveryDao.save(epd);
                    }
                }
            }
            espDeliveryDao.flush();
        } else {
            batchCode = batchNo;
        }
        batchCode = (StringUtil.isEmpty(batchCode) ? null : batchCode);
        if (null == batchCode) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        Date transDate = new Date();
        List<ESPDeliveryCommand> list = findCloseDeliveryDatasByStaCode(staCode, batchCode);
        Long skuQtys = 0L;
        String deliveryNo = "";
        for (ESPDeliveryCommand esp : list) {
            String qty = esp.getItemReceivedQTY();
            skuQtys += (StringUtils.isEmpty(qty) ? 0L : Long.valueOf(qty));
            if (!StringUtils.isEmpty(esp.getDeliveryDeliveryNO()) && "".equals(deliveryNo)) {
                deliveryNo = esp.getDeliveryDeliveryNO();
            }
        }
        // 合并重复明细
        List<ESPDeliveryCommand> espList = new ArrayList<ESPDeliveryCommand>();
        for (ESPDeliveryCommand esp : list) {
            ESPDeliveryCommand cmd = esp;
            cmd.setHeaderNumberofRecords(String.valueOf(skuQtys));
            cmd.setDeliveryDeliveryNO(deliveryNo);
            if (espList.size() > 0) {
                boolean isExist = false;
                String extCode2 = cmd.getItemSku();
                if (StringUtil.isEmpty(extCode2)) {
                    throw new BusinessException(ErrorCode.VMI_SKU_CODE_ERROR);
                }
                ListIterator<ESPDeliveryCommand> iter = espList.listIterator();
                while (iter.hasNext()) {
                    ESPDeliveryCommand c = iter.next();
                    if (!StringUtils.isEmpty(extCode2)) {
                        if (extCode2.equals(c.getItemSku())) {
                            isExist = true;
                            Long oldQty = (StringUtils.isEmpty(c.getItemReceivedQTY()) ? 0L : Long.valueOf(c.getItemReceivedQTY()));
                            Long newQty = (StringUtils.isEmpty(cmd.getItemReceivedQTY()) ? 0L : Long.valueOf(cmd.getItemReceivedQTY()));
                            Long reQty = oldQty + newQty;
                            c.setItemReceivedQTY(String.valueOf(reQty));
                            iter.set(c);
                            break;
                        }
                    }
                }
                if (false == isExist) {
                    espList.add(cmd);
                }
            } else {
                espList.add(cmd);
            }
        }
        String xml = marshallerInOutBoundDeliveryData(espList, sta, transDate);
        if (null == xml) {
            throw new BusinessException(ErrorCode.VMI_GENERATE_RESPONSE_DATA_ERROR);
        }
        // SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        try {
            // 调Pacs接口
            if (log.isInfoEnabled()) {
                log.info("====>Esprit call pacs feedback interface begain, batchCode:[{}], staCode:[{}], slipCode:[{}]", new Object[] {batchCode, sta.getCode(), sta.getRefSlipCode()});
            }
            BaseResult rs = rmi4Wms.espritDeliveryDataSync(batchCode, transDate, xml);
            if (rs.getStatus() == 0) {
                espDeliveryDao.updateStatusByClosedStaCode(ESPDelivery.STATUS_1, sta.getCode(), batchCode);
                throw new BusinessException(ErrorCode.OMS_SYSTEM_ERROR, new Object[] {rs.getMsg()});
            }
            if (log.isInfoEnabled()) {
                log.error("====>Esprit call pacs feedback interface end, batchCode:[{}], staCode:[{}], slipCode:[{}]", new Object[] {batchCode, sta.getCode(), sta.getRefSlipCode()});
            }
            espDeliveryDao.updateStatusByClosedStaCode(ESPDelivery.STATUS_END, sta.getCode(), batchCode);
        } catch (BusinessException e) {
            espDeliveryDao.updateStatusByClosedStaCode(ESPDelivery.STATUS_1, sta.getCode(), batchCode);
            log.error("====>Esprit Call pacs feedback interface error, batchCode:[{}], staCode:[{}], slipCode:[{}]", new Object[] {batchCode, sta.getCode(), sta.getRefSlipCode()});
            log.error("====>Esprit Call pacs feedback interface error, batchCode:[" + batchCode + "]", e);
            throw e;
        } catch (Exception e) {
            espDeliveryDao.updateStatusByClosedStaCode(ESPDelivery.STATUS_1, sta.getCode(), batchCode);
            log.error("====>Esprit call pacs feedback error, batchCode:[{}], staCode:[{}], slipCode:[{}]", new Object[] {batchCode, sta.getCode(), sta.getRefSlipCode()});
            log.error("====>Esprit call pacs feedback error, batchCode:[" + batchCode + "]", e);
            throw new BusinessException(ErrorCode.VMI_ESPRIT_TO_PACS_ERROR);
        }

    }

    /**
     * 查
     */
    @Override
    public List<ESPDeliveryCommand> findDeliveryDatasByStaCode(String staCode, String batchCode) {
        return espDeliveryDao.findDeliveryDatasByStaCode(staCode, batchCode, new BeanPropertyRowMapperExt<ESPDeliveryCommand>(ESPDeliveryCommand.class));
    }

    /**
     * 收货单关闭反馈数据
     */
    @Override
    public List<ESPDeliveryCommand> findCloseDeliveryDatasByStaCode(String staCode, String batchCode) {
        return espDeliveryDao.findCloseDeliveryDatasByStaCode(staCode, batchCode, new BeanPropertyRowMapperExt<ESPDeliveryCommand>(ESPDeliveryCommand.class));
    }

    /**
     * 生成入库出库xml反馈数据
     */
    @Override
    public String marshallerInOutBoundDeliveryData(List<ESPDeliveryCommand> list, StockTransApplication sta, Date transDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        EspDelivery espD = new EspDelivery();
        Header head = new Header();
        String fromGLN = list.get(0).getHeaderFromgln();
        String toGLN = list.get(0).getHeaderTogln();
        head.setFromGLN(fromGLN);
        head.setToGLN(toGLN);
        String fromNode = list.get(0).getHeaderFromnode();
        String toNode = list.get(0).getHeaderTonode();
        head.setFromNode(fromNode);
        head.setToNode(toNode);
        // head.setSequenceNumber("");
        head.setNumberOfRecords("1");
        head.setGenerationDate(sdf.format(transDate));
        sdf = new SimpleDateFormat("HHmmss");
        head.setGenerationTime(sdf.format(transDate));
        Deliveries ds = new Deliveries();
        Delivery delivery = new Delivery();
        delivery.setDeliveryNo(list.get(0).getDeliveryDeliveryNO());
        sdf = new SimpleDateFormat("yyyyMMdd");
        delivery.setDeliveryDate(sdf.format(transDate));
        delivery.setGoodsReceiptDate(sdf.format(sta.getCreateTime()));
        // 再次判断反馈给pac的数据类型
        if (sta.getIsEsprit() == null) {
            if (null != sta.getToLocation() && "4046655078762".equals(sta.getToLocation())) {
                delivery.setDeliveryType("P");
            } else {
                delivery.setDeliveryType(ESPDelivery.DELIVERED_TYPE);
            }

            delivery.setDeliveryStatus(ESPDelivery.DELIVERED_STATUS);
        }
        // else if (sta.getType().getValue() ==
        // StockTransApplicationType.VMI_TRANSFER_RETURN.getValue()) {// 转店
        // delivery.setDeliveryType("P");
        // delivery.setDeliveryStatus("D");
        // } else if (sta.getType().getValue() ==
        // StockTransApplicationType.VMI_INBOUND_CONSIGNMENT.getValue()) {// 入库
        // // 反馈
        // head.setFromNode(toNode);
        // head.setToNode(fromNode);
        // head.setFromGLN(toGLN);
        // head.setToGLN(fromGLN);
        //
        // delivery.setDeliveryType("R");
        // delivery.setDeliveryStatus("A");
        // }
        String dFromGLN = (StringUtils.isEmpty(list.get(0).getDeliveryDeliveredfromGLN()) ? sta.getFromLocation() : list.get(0).getDeliveryDeliveredfromGLN());
        String dToGLN = (StringUtils.isEmpty(list.get(0).getDeliveryDeliveredtoGLN()) ? sta.getToLocation() : list.get(0).getDeliveryDeliveredtoGLN());
        delivery.setDeliveredFromGLN(dFromGLN);
        delivery.setDeliveredToGLN(dToGLN);
        Items items = new Items();
        com.jumbo.wms.model.command.vmi.esprit.xml.espDelilvery.Item item = null;
        for (ESPDeliveryCommand r : list) {
            ESPDeliveryCommand cmd = r;
            item = new com.jumbo.wms.model.command.vmi.esprit.xml.espDelilvery.Item();
            String skuCode = cmd.getItemSku();
            if (StringUtil.isEmpty(skuCode)) {
                throw new BusinessException(ErrorCode.VMI_SKU_CODE_ERROR);
            }
            item.setSku(skuCode);
            item.setReceivedQty(Long.valueOf(cmd.getItemReceivedQTY()));
            items.getItem().add(item);

        }
        delivery.setItems(items);
        ds.getDelivery().add(delivery);
        espD.setDeliveries(ds);
        espD.setHeader(head);
        try {
            // 生成xml
            JAXBContext jaxbContext = JAXBContext.newInstance(EspDelivery.class);
            StringWriter sw = new StringWriter();
            Marshaller marshaller = jaxbContext.createMarshaller();
            // marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(espD, sw);
            return sw.toString();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 生成入库出库xml反馈数据
     */
    private String marshallerInBoundDeliveryData(List<ESPDeliveryCommand> list, StockTransApplication sta, Date transDate, String seq) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        EspDelivery espD = new EspDelivery();
        Header head = new Header();
        String fromGLN = list.get(0).getHeaderFromgln();
        String toGLN = list.get(0).getHeaderTogln();
        head.setFromGLN(fromGLN);
        head.setToGLN(toGLN);
        String fromNode = list.get(0).getHeaderFromnode();
        String toNode = list.get(0).getHeaderTonode();
        head.setFromNode(fromNode);
        head.setToNode(toNode);
        head.setSequenceNumber(seq);
        head.setNumberOfRecords("1");
        head.setGenerationDate(sdf.format(transDate));
        sdf = new SimpleDateFormat("HHmmss");
        head.setGenerationTime(sdf.format(transDate));
        Deliveries ds = new Deliveries();
        Delivery delivery = new Delivery();
        delivery.setDeliveryNo(list.get(0).getDeliveryDeliveryNO());
        sdf = new SimpleDateFormat("yyyyMMdd");
        delivery.setDeliveryDate(sdf.format(transDate));
        delivery.setGoodsReceiptDate(sdf.format(sta.getCreateTime()));
        delivery.setDeliveryType(ESPDelivery.DELIVERED_TYPE);
        delivery.setDeliveryStatus(ESPDelivery.DELIVERED_STATUS);
        String dFromGLN = (StringUtils.isEmpty(list.get(0).getDeliveryDeliveredfromGLN()) ? sta.getFromLocation() : list.get(0).getDeliveryDeliveredfromGLN());
        String dToGLN = (StringUtils.isEmpty(list.get(0).getDeliveryDeliveredtoGLN()) ? sta.getToLocation() : list.get(0).getDeliveryDeliveredtoGLN());
        delivery.setDeliveredFromGLN(dFromGLN);
        delivery.setDeliveredToGLN(dToGLN);
        Items items = new Items();
        com.jumbo.wms.model.command.vmi.esprit.xml.espDelilvery.Item item = null;
        for (ESPDeliveryCommand r : list) {
            ESPDeliveryCommand cmd = r;
            item = new com.jumbo.wms.model.command.vmi.esprit.xml.espDelilvery.Item();
            String skuCode = cmd.getItemSku();
            if (StringUtil.isEmpty(skuCode)) {
                throw new BusinessException(ErrorCode.VMI_SKU_CODE_ERROR);
            }
            item.setSku(skuCode);
            item.setReceivedQty(Long.valueOf(cmd.getItemReceivedQTY()));
            items.getItem().add(item);

        }
        delivery.setItems(items);
        ds.getDelivery().add(delivery);
        espD.setDeliveries(ds);
        espD.setHeader(head);
        try {
            // 生成xml
            JAXBContext jaxbContext = JAXBContext.newInstance(EspDelivery.class);
            StringWriter sw = new StringWriter();
            Marshaller marshaller = jaxbContext.createMarshaller();
            // marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(espD, sw);
            return sw.toString();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 收货单关闭反馈
     */
    @Override
    public void inBoundCloseFeedback(String locationUploadPath) {
        List<ESPDeliveryCommand> esList = findCloseDeliveryDatasGroupByStaCode();
        for (ESPDeliveryCommand espCmd : esList) {
            try {
                inBoundDeliveryCloseSaveFile(espCmd.getStaCode(), locationUploadPath);
            } catch (Exception e) {
                log.error("====>Esprit inbound close file feedback error, staCode:[{}]", espCmd.getStaCode());
                loggerErrorMsg(e);
            }
        }
    }

    /**
     * 转入反馈
     */
    @Override
    public void inBoundFeedback(String locationUploadPath) {
        List<ESPDeliveryCommand> esList = findDeliveryDatasGroupByStaCode2(StockTransApplicationType.VMI_INBOUND_CONSIGNMENT.getValue());
        for (ESPDeliveryCommand espCmd : esList) {
            try {
                inBoundDeliverySaveFile(espCmd.getStaCode(), locationUploadPath);
            } catch (Exception e) {
                log.error("====>Esprit inbound file feedback error, staCode:[{}]", espCmd.getStaCode());
                loggerErrorMsg(e);
            }
        }

    }

    /**
     * 转出反馈Pacs
     */
    @Override
    public void outBoundRtn() {
        List<ESPDeliveryCommand> esList = findDeliveryDatasGroupByStaCode(StockTransApplicationType.VMI_TRANSFER_RETURN.getValue());
        for (ESPDeliveryCommand espCmd : esList) {
            try {
                outBoundDeliveryRtn(espCmd.getStaCode());
            } catch (Exception e) {
                log.error("====>Esprit delivery data feedback pacs error, staCode:[{}]", espCmd.getStaCode());
                loggerErrorMsg(e);
            }
        }
    }
}
