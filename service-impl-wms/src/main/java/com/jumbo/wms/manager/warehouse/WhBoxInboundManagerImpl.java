package com.jumbo.wms.manager.warehouse;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.dao.baseinfo.SkuDao;
import com.jumbo.dao.warehouse.BiChannelDao;
import com.jumbo.dao.warehouse.InventoryStatusDao;
import com.jumbo.dao.warehouse.StaLineDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.dao.warehouse.WhInfoTimeRefDao;
import com.jumbo.wms.Constants;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.system.SequenceManager;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.warehouse.StaLine;
import com.jumbo.wms.model.warehouse.StaLineCommand;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationStatus;
import com.jumbo.wms.model.warehouse.WhInfoTimeRefBillType;
import com.jumbo.wms.model.warehouse.WhInfoTimeRefNodeType;

import loxia.dao.Sort;
import loxia.support.excel.ExcelReader;
import loxia.support.excel.ReadStatus;

/**
 * 按箱收货
 * 
 * @author PUCK SHEN
 * 
 */
@Transactional
@Service("whBoxInboundManager")
public class WhBoxInboundManagerImpl extends BaseManagerImpl implements WhBoxInboundManager {

    /**
	 * 
	 */
    private static final long serialVersionUID = -7736486749731671093L;
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private WhInfoTimeRefDao whInfoTimeRefDao;
    @Autowired
    private SequenceManager sequenceManager;
    @Autowired
    private BiChannelDao shopDao;
    @Autowired
    private WareHouseManagerExe wareHouseManagerExe;
    @Autowired
    private SkuDao skuDao;
    @Autowired
    private InventoryStatusDao inventoryStatusDao;
    @Autowired
    private StaLineDao staLineDao;

    @Resource(name = "whBoxInboundReader")
    private ExcelReader whBoxInboundReader;

    @SuppressWarnings("unchecked")
    @Override
    public ReadStatus whBoxReceiveImport(File file, Long userId, Long staId) {
        log.debug("===========whBoxInboundImport start============");
        Map<String, Object> beans = new HashMap<String, Object>();
        List<StaLineCommand> trac = null;
        ReadStatus rs = null;
        try {
            rs = whBoxInboundReader.readSheet(new FileInputStream(file), 0, beans);
            if (ReadStatus.STATUS_SUCCESS != rs.getStatus()) {
                rs.setStatus(-2);
                rs.addException(new BusinessException(ErrorCode.WH_BOX_INBOUND_EXCEL_PARSE_ERROR));
                return rs;
            }
            trac = (List<StaLineCommand>) beans.get("staLines");
            createSonSta(trac, userId, staId);
            return rs;
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            if (log.isErrorEnabled()) {
                log.error("whBoxReceiveImport Exception:" + staId, ex);
            }
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
    }

    private void createSonSta(List<StaLineCommand> staLine, Long userId, Long staId) {
        log.debug("=============== createCartonSta ================");
        StockTransApplication rootSta = staDao.getByPrimaryKey(staId);
        if (rootSta == null) {
            throw new BusinessException(ErrorCode.STA_IS_NULL);
        }
        // 校验导入的Excel文件是否合法
        validateImportData(staLine, rootSta);
        List<List<StaLineCommand>> list = createGroupData(staLine);
        createSonStaByGroupData(list, userId, staId);
    }

    private List<List<StaLineCommand>> createGroupData(List<StaLineCommand> staLine) {
        Map<String, List<StaLineCommand>> ml = new HashMap<String, List<StaLineCommand>>();
        for (StaLineCommand sc : staLine) {
            if (!ml.containsKey(sc.getSlipCode())) {
                List<StaLineCommand> nl = new ArrayList<StaLineCommand>();
                nl.add(sc);
                ml.put(sc.getSlipCode(), nl);
            } else {
                ml.get(sc.getSlipCode()).add(sc);
            }
        }
        List<List<StaLineCommand>> list = new ArrayList<List<StaLineCommand>>();
        list.addAll(ml.values());
        return list;
    }

    @Override
    public void createSonStaByGroupData(List<List<StaLineCommand>> list, Long userId, Long staId) {
        StockTransApplication rootSta = staDao.getByPrimaryKey(staId);
        Long cId = shopDao.getByCode(rootSta.getOwner()).getCustomer().getId();
        for (List<StaLineCommand> staLine : list) {
            StockTransApplication sta = new StockTransApplication();
            String slipCode = staLine.get(0).getSlipCode();
            // 创建cartonSta
            // OperationUnit ou = null;
            sta.setType(rootSta.getType());
            sta.setBusinessSeqNo(staDao.getBusinessSeqNo(new SingleColumnRowMapper<BigDecimal>()).longValue());
            sta.setCreateTime(new Date());
            sta.setStatus(StockTransApplicationStatus.CREATED);
            sta.setRefSlipCode(slipCode == null ? "-----------------------" : slipCode);
            // 订单状态与账号关联
            whInfoTimeRefDao.insertWhInfoTime2(sta.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.CREATE.getValue(), userId, rootSta.getMainWarehouse() == null ? null : rootSta.getMainWarehouse().getId());
            sta.setLastModifyTime(new Date());
            sta.setSlipCode1(rootSta.getRefSlipCode());// 按箱收货主订单箱号记入子订单slipcode1
            sta.setSlipCode2(rootSta.getSlipCode1());// 按箱收货主订单单号记入子订单slipcode2
            sta.setCode(sequenceManager.getCode(StockTransApplication.class.getName(), sta));
            sta.setGroupSta(rootSta);// 新创建作业单通过sta中group_sta_id关联
            staDao.save(sta);
            staDao.flush();
            Long skuQty = 0l;
            wareHouseManagerExe.validateBiChannelSupport(null, rootSta.getOwner());
            for (StaLineCommand staLineCommand : staLine) {
                Sku sku = skuDao.getByBarCodeAndCustomer(staLineCommand.getBarCode(), cId);
                StaLine sl = new StaLine();
                sl.setQuantity(staLineCommand.getQuantity());
                sl.setSku(sku);
                sl.setCompleteQuantity(0l);
                sl.setSta(sta);
                sl.setOwner(rootSta.getOwner());
                sl.setInvStatus(inventoryStatusDao.findByNameAndOu(Constants.CK_GOOD_INV_STATUS_NAME, rootSta.getMainWarehouse().getId()));
                staLineDao.save(sl);
                skuQty += sl.getQuantity();
            }
            sta.setSkuQty(skuQty);
            log.debug("===============cartonsSta create success ================", new Object[] {sta.getCode()});
            sta.setOwner(rootSta.getOwner());
            sta.setMainWarehouse(rootSta.getMainWarehouse());
        }
        // 每次创出新单主单要标示为冻结
        rootSta.setStatus(StockTransApplicationStatus.FROZEN);
        rootSta.setLastModifyTime(new Date());
    }

    private void validateImportData(List<StaLineCommand> staLine, StockTransApplication sta) {
        Long cId = shopDao.getByCode(sta.getOwner()).getCustomer().getId();
        BusinessException e = new BusinessException();
        BusinessException e1 = e;
        Map<String, String> sm = new HashMap<String, String>();// 有问题商品列表
        Map<String, Boolean> sam = new HashMap<String, Boolean>();// 单号列表
        Map<String, Long> skuSum = new HashMap<String, Long>();
        Map<String, String> scb = new HashMap<String, String>();// 箱号-商品
        for (StaLineCommand c : staLine) {
            // 校验导入的单号是否存在已经创建的作业单，如果存在，则添加一条错误信息，不存在记录并且标示后续该单号不需要校验
            if (!sam.containsKey(c.getSlipCode())) {
                sam.put(c.getSlipCode(), true);
                StockTransApplication sa = staDao.findStaBySlipCodeNotCancel(c.getSlipCode());
                if (sa != null) {
                    // 导入的箱号已存在对应的作业单
                    BusinessException newe = new BusinessException(ErrorCode.AXSH_BOX_EXISTS, new Object[] {c.getSlipCode()});
                    e1.setLinkedException(newe);
                    e1 = newe;
                    sam.put(c.getSlipCode(), false);
                }
            }
            // 校验商品是否存在在有问题列表中，如果在有问题列表中，之前已经跑出了相关异常，改行数据无意义<br/>
            // 如果不存在在问题列表中，校验是否有问题，有问题抛出异常，没问题计算数量逻辑
            if (!sm.containsKey(c.getBarCode())) {
                Sku sku = skuDao.getByBarCodeAndCustomer(c.getBarCode(), cId);
                if (sku == null) {
                    // 导入的商品系统不存在
                    BusinessException newe = new BusinessException(ErrorCode.AXSH_SKU_NOT_EXISTS, new Object[] {c.getBarCode()});
                    e1.setLinkedException(newe);
                    e1 = newe;
                    sm.put(c.getBarCode(), c.getBarCode());
                } else {
                    if (sam.get(c.getSlipCode())) {// 单号校验通过，sku校验通过，要求只能存在一行数据
                        String key = c.getSlipCode() + "_" + c.getBarCode();
                        if (scb.containsKey(key)) {
                            BusinessException newe = new BusinessException(ErrorCode.AXSH_SLIPCODESKU_EXISTS, new Object[] {c.getSlipCode(), c.getBarCode()});
                            e1.setLinkedException(newe);
                            e1 = newe;
                        } else {
                            if (!skuSum.containsKey(c.getBarCode())) {
                                skuSum.put(c.getBarCode(), c.getQuantity());
                            } else {
                                skuSum.put(c.getBarCode(), skuSum.get(c.getBarCode()) + c.getQuantity());
                            }
                            scb.put(key, key);
                        }
                    }
                }
            }
        }
        List<StaLineCommand> cl = findBoxReceiveStaLine(sta.getId(), new Sort[] {new Sort("id")});
        for (String barCode : skuSum.keySet()) {
            boolean isExists = false;
            for (StaLineCommand cc : cl) {
                if (cc.getBarCode().equals(barCode)) {
                    if (cc.getReceiptQty() < skuSum.get(barCode)) {
                        // 商品导入量大于剩余计划量
                        BusinessException newe = new BusinessException(ErrorCode.AXSH_QUANTITY_HAS_PROBLEM, new Object[] {barCode, skuSum.get(barCode) - cc.getReceiptQty(), skuSum.get(barCode), cc.getReceiptQty()});
                        e1.setLinkedException(newe);
                        e1 = newe;
                    }
                    isExists = true;
                    break;
                }
            }
            if (!isExists) {
                // 商品导入量大于计划执行量
                BusinessException newe = new BusinessException(ErrorCode.AXSH_QUANTITY_HAS_PROBLEM, new Object[] {barCode, skuSum.get(barCode), skuSum.get(barCode), 0});
                e1.setLinkedException(newe);
                e1 = newe;
            }
        }
        if (e.getLinkedException() != null) {
            throw e;
        }
    }

    @Override
    public List<StaLineCommand> findBoxReceiveStaLine(Long id, Sort[] sorts) {
        return staLineDao.findBoxReceiveStaLine(id, sorts, new BeanPropertyRowMapper<StaLineCommand>(StaLineCommand.class));
    }

}
