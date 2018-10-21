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
 * 
 */
package com.jumbo.wms.manager.vmi.coachData;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import loxia.dao.support.BeanPropertyRowMapperExt;
import loxia.support.excel.ExcelReader;
import loxia.support.excel.ReadStatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import com.jumbo.dao.authorization.OperationUnitDao;
import com.jumbo.dao.baseinfo.CustomerDao;
import com.jumbo.dao.baseinfo.SkuDao; // import
import com.jumbo.dao.vmi.adidas.BlSkuDao;
import com.jumbo.dao.vmi.adidas.HubSkuDao;
// com.jumbo.dao.vmi.coachData.CoachInboundInstructionDataDao;
import com.jumbo.dao.warehouse.BiChannelDao;
import com.jumbo.dao.warehouse.InventoryStatusDao;
import com.jumbo.dao.warehouse.StaLineDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.dao.warehouse.WhInfoTimeRefDao;
import com.jumbo.event.TransactionalEvent;
import com.jumbo.event.listener.EventObserver;
import com.jumbo.wms.daemon.AdidasTask;
import com.jumbo.wms.daemon.GncTask;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.baseinfo.BaseinfoManager;
import com.jumbo.wms.manager.system.SequenceManager;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.manager.warehouse.WareHouseManagerExe;
import com.jumbo.wms.manager.warehouse.WareHouseManagerQuery;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.baseinfo.Customer;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.vmi.coachData.CoachInboundInstructionData;
import com.jumbo.wms.model.warehouse.BlSkuCommand;
import com.jumbo.wms.model.warehouse.HubSku;
import com.jumbo.wms.model.warehouse.InventoryStatus;
import com.jumbo.wms.model.warehouse.StaLine;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationStatus;
import com.jumbo.wms.model.warehouse.StockTransApplicationType;
import com.jumbo.wms.model.warehouse.WhInfoTimeRefBillType;
import com.jumbo.wms.model.warehouse.WhInfoTimeRefNodeType; // import javax.annotation.Resource;

@Service("coachParseDataManagerProxy")
public class CoachParseDataManagerProxyImpl extends BaseManagerImpl implements CoachParseDataManagerProxy {

    private static final long serialVersionUID = 3591443356576100614L;
    private EventObserver eventObserver;
    @Resource(name = "coachInboundSkuReader")
    private ExcelReader coachInboundSkuReader;
    @Resource(name = "coachInboundInstructionReader")
    private ExcelReader coachInboundInstructionReader;
    @Autowired
    private BiChannelDao shopDao;
    @Autowired
    private WareHouseManager wareHouseManager;
    @Autowired
    private WareHouseManagerQuery wareHouseManagerQuery;
    @Autowired
    private SkuDao skuDao;
    @Autowired
    private BaseinfoManager baseinfoManager;
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private SequenceManager sequenceManager;
    @Autowired
    private OperationUnitDao ouDao;
    @Autowired
    private InventoryStatusDao inventoryStatusDao;
    @Autowired
    private WareHouseManagerExe wmExe;
    @Autowired
    private StaLineDao staLineDao;
    @Autowired
    private WhInfoTimeRefDao whInfoTimeRefDao;
    // @Autowired
    // private CoachInboundInstructionDataDao coachInboundInstructDao;
    // @Resource(name = "vmiAdjustInventoryReader")
    // private ExcelReader vmiAdjustInventoryReader;
    // @Resource(name = "predefinedOutReader")
    // private ExcelReader predefinedOutReader;

    /**
     * MQ JmsTemplate
     */
    private JmsTemplate taxMqJmsTemplate;
    private JmsTemplate bhMqJmsTemplate;

    @Resource
    private ApplicationContext applicationContext;
    @Autowired
    private BlSkuDao blSkuDao;
    @Autowired
    private AdidasTask adidasTask;
    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private HubSkuDao hubSkuDao;
    @Autowired
    private GncTask gncTask;

    @PostConstruct
    protected void init() {
        try {
            eventObserver = applicationContext.getBean(EventObserver.class);
            taxMqJmsTemplate = (JmsTemplate) applicationContext.getBean("jmsTemplate");
            bhMqJmsTemplate = (JmsTemplate) applicationContext.getBean("bhJmsTemplate");
        } catch (Exception e) {
            log.error("no bean named jmsTemplate");
        }
    }

    @SuppressWarnings("unchecked")
    public ReadStatus generateCoachSkuImport(File coachFile, Long shopId) {
        log.debug("===========generateCoachSkuImport start============");
        Map<String, Object> coaches = new HashMap<String, Object>();
        List<CoachInboundInstructionData> stalList = null;
        ReadStatus rs = null;
        try {
            // 读取数据
            rs = coachInboundSkuReader.readSheet(new FileInputStream(coachFile), 0, coaches);
            if (ReadStatus.STATUS_SUCCESS != rs.getStatus()) {
                rs.setStatus(-2);
                rs.addException(new BusinessException(ErrorCode.EXCEL_PARSE_ERROR));
                return rs;
            }
            stalList = (List<CoachInboundInstructionData>) coaches.get("data");
            List<String> barcodes = new ArrayList<String>();
            for (CoachInboundInstructionData tmp : stalList) {
                barcodes.add(tmp.getBarCode().trim());
            }
            // 创建商品
            List<String> errorCodes = new ArrayList<String>();
            errorCodes.addAll(generateInboundSku(barcodes, shopId));
            if (errorCodes.size() == 0) {
                return rs;
            } else {
                rs.setStatus(-2);
                BusinessException error = new BusinessException(ErrorCode.SKU_CREATE_ERROR, new Object[] {errorCodes.toString()});
                rs.addException(error);
                return rs;
            }

        } catch (FileNotFoundException e1) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
    }

    /**
     * 创建商品
     * 
     * @param instructionList
     * @param shopId
     * @return 返回所有失败的SKUCODE
     */
    private List<String> generateInboundSku(List<String> instructionList, Long shopId) {
        BiChannel shop = shopDao.getByPrimaryKey(shopId);
        OperationUnit ou = ouDao.getDefaultInboundWhByShopId(shop.getId());
        if (ou == null) {
            log.debug("=========OPERATION UNIT {} NOT FOUNT===========", new Object[] {shop.getVmiCode()});
            throw new BusinessException(ErrorCode.END_OPERATION_UNIT_NOT_FOUNT);
        }
        List<String> errorCodes = new ArrayList<String>();
        Long customerId = wareHouseManagerQuery.getCustomerByWhouid(ou.getId());
        for (String skuCode : instructionList) {
            Sku sku = skuDao.getByBarcode(skuCode, customerId);
            // 判断skuCode在系统中是否存在
            if (sku == null) {
                try {
                    baseinfoManager.sendMsgToOmsCreateSku(skuCode, shop.getVmiCode());
                } catch (Exception e) {
                    // OMS创商品失败，或调用OMS接口异常
                    log.debug("===============SKU {} NOT FOUND ================", new Object[] {skuCode});
                    errorCodes.add(skuCode);
                }
            }
        }
        return errorCodes;
    }

    @SuppressWarnings("unchecked")
    public ReadStatus generateCoachInventoryImport(File file, Long shopId, String slipCode, Long ouId, Long userId) {
        log.debug("===========generateCoachInventoryImport start============");
        Map<String, Object> beans = new HashMap<String, Object>();
        List<CoachInboundInstructionData> stalList = null;
        ReadStatus rs = null;
        try {
            rs = coachInboundInstructionReader.readSheet(new FileInputStream(file), 0, beans);
            if (ReadStatus.STATUS_SUCCESS != rs.getStatus()) {
                rs.setStatus(-2);
                rs.addException(new BusinessException(ErrorCode.EXCEL_PARSE_ERROR));
                return rs;
            }
            String type = (String) beans.get("type");
            stalList = (List<CoachInboundInstructionData>) beans.get("outStaLine");
            generateInboundSta(stalList, shopId, slipCode, type, ouId, userId);
            return rs;
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            if (log.isErrorEnabled()) {
                log.error("Coach generateCoachInventoryImport IOException:" + shopId, ex);
            }
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
    }

    /**
     * 创入库单
     * 
     * @param instructionList
     * @param shopId
     * @param slipCode
     * @throws Exception
     */
    private void generateInboundSta(List<CoachInboundInstructionData> instructionList, Long shopId, String slipCode, String type, Long ouId, Long userId) throws Exception {
        String innerShopCode = null;
        InventoryStatus is = null;
        int intType = 0;
        if ("SKU条码".equals(type)) { // 判断type
            intType = 1;
        } else if ("外部平台对接编码".equals(type)) {
            intType = 0;
        } else if ("商品编码".equals(type)) {
            intType = 2;
        }
        BiChannel shop = shopDao.getByPrimaryKey(shopId);
        if (shop == null) {
            throw new Exception("没有找到店铺信息!shopId:" + shopId);
        }
        wmExe.validateBiChannelSupport(null, shop.getCode());
        OperationUnit ou = ouDao.getByPrimaryKey(ouId);
        if (ou == null) {
            log.debug("=========OPERATION UNIT {} NOT FOUNT===========", new Object[] {shop.getVmiCode()});
            throw new BusinessException(ErrorCode.END_OPERATION_UNIT_NOT_FOUNT);
        }
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
        Long customerId = wareHouseManagerQuery.getCustomerByWhouid(ouId);
        Map<String, StaLine> map = new HashMap<String, StaLine>(); // 单据号为空，key为skuCode
        Map<String, StockTransApplication> map2 = new HashMap<String, StockTransApplication>(); // 单据号不为空，key为slipCode
        Map<String, Map<String, StaLine>> maps = new HashMap<String, Map<String, StaLine>>();// 单据号重复，嵌套map
        Map<String, StockTransApplication> mapStr = new HashMap<String, StockTransApplication>(); // 单据号、单据号1不为空时校验
        boolean slipCodeIsNull = false;
        int tempCount = 0;
        int tempCount2 = 0;
        int tempCount3 = 0;
        String skuIsNull = "";
        String checkType = "";
        String checkStatus = "";
        innerShopCode = shop.getCode();
        boolean isError = false;
        for (CoachInboundInstructionData ins : instructionList) {
            if (ins.getSta().getRefSlipCode() == null && ins.getSta().getSlipCode1() != null && !shop.getIsSupportNoCancelSta()) { // 判断slipcode，满足条件就报错
                throw new BusinessException(ErrorCode.VMI_INSTRUCTION_SLIPCODE_ERROR);
            }
            if (ins.getSta().getRefSlipCode() != null && !shop.getIsSupportNoCancelSta()) {
                List<StockTransApplication> stas = staDao.findStaStautsBySlipCode(ins.getSta().getRefSlipCode());
                if (stas.size() == 0) {
                    tempCount3++;
                    if (tempCount3 == 1) {
                        checkType = ins.getSta().getRefSlipCode();
                    } else {
                        if (!checkType.contains(ins.getSta().getRefSlipCode())) {
                            checkType += "/" + ins.getSta().getRefSlipCode();
                        }
                    }
                    continue;
                } else {
                    for (StockTransApplication sta : stas) { // 判断status,非取消已处理把相关单据号累加
                        if (!StockTransApplicationStatus.CANCELED.equals(sta.getStatus())) {
                            if (!shop.getIsSupportNoCancelSta()) { // 店铺配置跳过取消单检验
                                tempCount2++;
                                if (tempCount2 == 1) {
                                    checkStatus = ins.getSta().getRefSlipCode();
                                } else {
                                    if (!checkStatus.contains(ins.getSta().getRefSlipCode())) {
                                        checkStatus += "/" + ins.getSta().getRefSlipCode();
                                    }
                                }
                                continue;
                            }
                        }
                    }
                }
            }
            String skuCode = ins.getBarCode().trim();
            Sku sku = null;
            if (intType == 1) {
                sku = skuDao.getByBarcode(skuCode, customerId);
            } else if (intType == 0) {
                sku = wareHouseManager.getByExtCode2AndCustomerAndShopId(skuCode, customerId, shopId);
            } else {
                sku = skuDao.getByCode(skuCode);
            }
            Customer cust = customerDao.getByPrimaryKey(customerId);
            if (sku == null) {
                // adidas 逻辑定制
                if (cust != null && "adidas".equals(cust.getCode())) {
                    List<BlSkuCommand> blSkus = blSkuDao.findNoBlSku(skuCode, new BeanPropertyRowMapperExt<BlSkuCommand>(BlSkuCommand.class));
                    if (blSkus == null || blSkus.size() == 0 || intType == 0) {
                        isError = true;
                    } else {
                        for (BlSkuCommand blSkuCommand : blSkus) {
                            if (blSkuCommand.getBarcode() == null) {
                                blSkuCommand.setSkuCdBarcode(blSkuCommand.getSkuCdBarcode());
                            } else {
                                blSkuCommand.setSkuCdBarcode(blSkuCommand.getBarcode());
                            }
                            try {
                                sku = adidasTask.updateSku(blSkuCommand);
                            } catch (Exception e) {
                                log.error("adidas createSku error..." + blSkuCommand.getId());
                                isError = true;
                            }
                        }
                    }
                    if (isError) {
                        tempCount++;
                        if (tempCount == 1) { // 当商品不存在时，把编码累加
                            skuIsNull = skuCode;
                        } else {
                            if (!skuIsNull.contains(skuCode)) {
                                skuIsNull += "/" + skuCode;
                            }
                        }
                        continue;
                    }
                } else if (cust != null && "GNC".equals(cust.getCode().toUpperCase())) {
                    HubSku blSkus = hubSkuDao.findOneHubSku(cust.getCode(), skuCode, new BeanPropertyRowMapperExt<HubSku>(HubSku.class));
                    if (blSkus == null) {
                        isError = true;
                    } else {
                        try {
                            sku = gncTask.updateSku(blSkus, cust.getCode());
                        } catch (Exception e) {
                            log.error("GNC createSku error..." + skuCode + e.toString());
                            isError = true;
                        }
                    }
                    if (isError) {
                        tempCount++;
                        if (tempCount == 1) { // 当商品不存在时，把编码累加
                            skuIsNull = skuCode;
                        } else {
                            if (!skuIsNull.contains(skuCode)) {
                                skuIsNull += "/" + skuCode;
                            }
                        }
                        continue;
                    }
                } else {
                    try {
                        if (shop.getVmiCode() == null) {
                            throw new BusinessException(ErrorCode.CREATE_SKU_ERROR);
                        } else {
                            baseinfoManager.sendMsgToOmsCreateSku(skuCode, shop.getVmiCode());
                        }
                    } catch (Exception e) {
                        log.debug("===============SKU {} create ERROR ================", new Object[] {skuCode});
                    }
                    tempCount++;
                    if (tempCount == 1) { // 当商品不存在时，把编码累加
                        skuIsNull = skuCode;
                    } else {
                        if (!skuIsNull.contains(skuCode)) {
                            skuIsNull += "/" + skuCode;
                        }
                    }
                    continue;
                }
            }
            log.debug("===============SKU {} CREATE SUCCESS ================", new Object[] {skuCode});
            if (ins.getSta().getRefSlipCode() != null && ins.getSta().getSlipCode1() != null) {// 当单据号、单据号1都不为空
                if (mapStr.containsKey(ins.getSta().getRefSlipCode())) {
                    StockTransApplication temp = mapStr.get(ins.getSta().getRefSlipCode());
                    temp.setSlipCode1(temp.getSlipCode1() + "/" + ins.getSta().getSlipCode1());
                } else {
                    StockTransApplication temp = new StockTransApplication();
                    temp.setSlipCode1(ins.getSta().getSlipCode1());
                    mapStr.put(ins.getSta().getRefSlipCode(), temp);
                }
            }
            if (ins.getSta().getRefSlipCode() == null && ins.getSta().getSlipCode1() == null) { // 单据号为空，则创一单。staLine相同barCode数量叠加
                if (map.containsKey(skuCode)) { // 判断barCode是否相同
                    StaLine temp = map.get(skuCode);
                    temp.setQuantity(temp.getQuantity() + ins.getQuantity().longValue());
                } else {
                    StaLine staLine = new StaLine();
                    staLine.setQuantity(ins.getQuantity().longValue());
                    staLine.setSku(sku);
                    staLine.setCompleteQuantity(0L);// 已执行数量
                    staLine.setOwner(innerShopCode);
                    staLine.setInvStatus(is);
                    map.put(skuCode, staLine);
                }
                slipCodeIsNull = true;
            } else { // 否则按照slipCode来创单，相同barCode、slipCode合并，数量叠加。
                if (maps.containsKey(ins.getSta().getRefSlipCode())) { // 判断slipCode是否重复
                    StockTransApplication sta = map2.get(ins.getSta().getRefSlipCode());
                    sta.setSkuQty(ins.getQuantity().longValue() + map2.get(ins.getSta().getRefSlipCode()).getSkuQty());
                    if (maps.get(ins.getSta().getRefSlipCode()).containsKey(skuCode)) {
                        StaLine temp = maps.get(ins.getSta().getRefSlipCode()).get(skuCode);
                        temp.setQuantity(temp.getQuantity() + ins.getQuantity().longValue());
                    } else {
                        StaLine staLine = new StaLine();
                        staLine.setQuantity(ins.getQuantity().longValue());
                        staLine.setSku(sku);
                        staLine.setCompleteQuantity(0L);// 已执行数量
                        staLine.setOwner(innerShopCode);
                        staLine.setInvStatus(is);
                        staLine.setSta(sta);
                        maps.get(ins.getSta().getRefSlipCode()).put(skuCode, staLine);
                        maps.put(ins.getSta().getRefSlipCode(), maps.get(ins.getSta().getRefSlipCode()));
                    }
                } else {
                    StockTransApplication sta = new StockTransApplication();
                    Map<String, StaLine> tempMap = new HashMap<String, StaLine>();
                    sta.setType(StockTransApplicationType.VMI_INBOUND_CONSIGNMENT);
                    sta.setBusinessSeqNo(staDao.getBusinessSeqNo(new SingleColumnRowMapper<BigDecimal>()).longValue());
                    sta.setCreateTime(new Date());
                    sta.setStatus(StockTransApplicationStatus.CREATED);
                    sta.setRefSlipCode(ins.getSta().getRefSlipCode());
                    sta.setMainWarehouse(ou);
                    // 订单状态与账号关联
                    whInfoTimeRefDao.insertWhInfoTime2(sta.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.CREATE.getValue(), userId, ou.getId());
                    sta.setLastModifyTime(new Date());
                    sta.setCode(sequenceManager.getCode(StockTransApplication.class.getName(), sta));
                    sta.setSlipCode1(ins.getSta().getSlipCode1());
                    sta.setOwner(innerShopCode);
                    sta.setMainStatus(is);
                    sta.setSkuQty(ins.getQuantity().longValue());
                    StaLine staLine = new StaLine();
                    staLine.setQuantity(ins.getQuantity().longValue());
                    staLine.setSku(sku);
                    staLine.setCompleteQuantity(0L);// 已执行数量
                    staLine.setOwner(innerShopCode);
                    staLine.setInvStatus(is);
                    staLine.setSta(sta);
                    map2.put(ins.getSta().getRefSlipCode(), sta);
                    tempMap.put(skuCode, staLine);
                    maps.put(ins.getSta().getRefSlipCode(), tempMap);
                }
            }
        }
        if (tempCount > 0) {// sku不存在，提示反馈
            if (isError) {
                throw new BusinessException(ErrorCode.BRAND_SKU_NOT_EXITS, new Object[] {skuIsNull});
            } else {
                throw new BusinessException(ErrorCode.CREATE_SKU, new Object[] {skuIsNull});
            }
        }
        if (tempCount2 > 0) {// 相关单据号关联的status非17，提示反馈
            throw new BusinessException(ErrorCode.VMI_INSTRUCTION_STATUS_ERROR, new Object[] {checkStatus});
        }
        if (tempCount3 > 0) {// 相关单据号关联的type非81，提示反馈
            throw new BusinessException(ErrorCode.VMI_INSTRUCTION_TYPE_ERROR, new Object[] {checkType});
        }
        if (slipCodeIsNull) { // 单据号为空
            StockTransApplication sta = new StockTransApplication();
            sta.setType(StockTransApplicationType.VMI_INBOUND_CONSIGNMENT);
            sta.setBusinessSeqNo(staDao.getBusinessSeqNo(new SingleColumnRowMapper<BigDecimal>()).longValue());
            sta.setCreateTime(new Date());
            sta.setLastModifyTime(new Date());
            sta.setStatus(StockTransApplicationStatus.CREATED);
            sta.setMainWarehouse(ou);
            sta.setCode(sequenceManager.getCode(StockTransApplication.class.getName(), sta));
            // 订单状态与账号关联
            whInfoTimeRefDao.insertWhInfoTime2(sta.getCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.CREATE.getValue(), userId, ou.getId());
            sta.setOwner(innerShopCode);
            sta.setMainStatus(is);
            staDao.save(sta);
            Long skuQty = 0l;
            for (StaLine staLine : map.values()) {
                staLine.setSta(sta);
                skuQty += staLine.getQuantity();
                staLineDao.save(staLine);
            }
            sta.setSkuQty(skuQty);
            try {
                eventObserver.onEvent(new TransactionalEvent(sta));
            } catch (BusinessException e) {
                throw e;
            }
        } else { // 按照slipCode来创单
            for (StockTransApplication sta : mapStr.values()) { // 判断相同单据号的单据号1是否相同
                if (sta.getSlipCode1().contains("/")) {
                    String[] s = sta.getSlipCode1().split("/");
                    boolean flag = true;
                    for (int i = 0; i < s.length - 1; i++) {
                        for (int j = i + 1; j < s.length; j++) {
                            if (!s[i].equals(s[j])) {
                                flag = false;
                                break;
                            }
                        }
                    }
                    if (!flag) {
                        throw new BusinessException(ErrorCode.VMI_INSTRUCTION_SLIPCODE1_ERROR);
                    }
                }
            }
            for (StockTransApplication sta : map2.values()) {
                staDao.save(sta);
                try {
                    eventObserver.onEvent(new TransactionalEvent(sta));
                } catch (BusinessException e) {
                    throw e;
                }
            }
            for (Iterator<Entry<String, Map<String, StaLine>>> itr = maps.entrySet().iterator(); itr.hasNext();) {
                Entry<String, Map<String, StaLine>> entry = (Entry<String, Map<String, StaLine>>) itr.next();
                Map<String, StaLine> map1 = (Map<String, StaLine>) entry.getValue();
                if (entry.getValue() instanceof Map) {
                    for (Iterator<Entry<String, StaLine>> it = map1.entrySet().iterator(); it.hasNext();) {
                        Entry<String, StaLine> entry2 = (Entry<String, StaLine>) it.next();
                        staLineDao.save(entry2.getValue());
                    }
                }
            }
        }
        staDao.flush();
    }

    public JmsTemplate getTaxMqJmsTemplate() {
        return taxMqJmsTemplate;
    }

    public void setTaxMqJmsTemplate(JmsTemplate taxMqJmsTemplate) {
        this.taxMqJmsTemplate = taxMqJmsTemplate;
    }

    public JmsTemplate getBhMqJmsTemplate() {
        return bhMqJmsTemplate;
    }

    public void setBhMqJmsTemplate(JmsTemplate bhMqJmsTemplate) {
        this.bhMqJmsTemplate = bhMqJmsTemplate;
    }

}
