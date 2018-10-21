package com.jumbo.wms.manager.warehouse;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import loxia.dao.support.BeanPropertyRowMapperExt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.jumbo.dao.authorization.OperationUnitDao;
import com.jumbo.dao.baseinfo.SkuDao;
import com.jumbo.dao.commandMapper.MapInvMapper;
import com.jumbo.dao.commandMapper.MapInvOwnerMapper;
import com.jumbo.dao.system.ChooseOptionDao;
import com.jumbo.dao.warehouse.InventoryDao;
import com.jumbo.dao.warehouse.LogQueueDao;
import com.jumbo.dao.warehouse.QueueGiftLineDao;
import com.jumbo.dao.warehouse.QueueStaDao;
import com.jumbo.dao.warehouse.QueueStaLineDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.pac.manager.extsys.wms.rmi.model.SoLineResponse;
import com.jumbo.pac.manager.extsys.wms.rmi.model.StaCreatedResponse;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.task.inv.TaskEbsManager;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.system.ChooseOption;
import com.jumbo.wms.model.warehouse.InventoryCommand;
import com.jumbo.wms.model.warehouse.QueueGiftLine;
import com.jumbo.wms.model.warehouse.QueueSta;
import com.jumbo.wms.model.warehouse.QueueStaLine;
import com.jumbo.wms.model.warehouse.QueueStaLineCommand;
import com.jumbo.wms.model.warehouse.QueueStaLineStatus;
import com.jumbo.wms.model.warehouse.QueueStaLineType;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationType;

/**
 * 获取队列信息生成批次
 * 
 * @author cheng.su
 * 
 */
@Service("queueStaManagerProxy")
public class QueueStaManagerProxyImpl extends BaseManagerImpl implements QueueStaManagerProxy {
    /**
	 * 
	 */
    private static final long serialVersionUID = -2610789424949989203L;
    protected static final Logger log = LoggerFactory.getLogger(QueueStaManagerProxyImpl.class);
    @Autowired
    private QueueStaDao queueStaDao;
    @Autowired
    private QueueStaLineDao queueStaLineDao;
    @Autowired
    private OperationUnitDao operationUnitDao;
    @Autowired
    private InventoryDao inventoryDao;
    @Autowired
    private LogQueueDao logQueueDao;
    @Resource
    private ApplicationContext applicationContext;
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private QueueStaManagerExecute queueStaManagerExecute;
    @Autowired
    private ChooseOptionDao chooseOptionDao;
    @Autowired
    private QueueGiftLineDao queueGiftLineDao;
    @Autowired
    private SkuDao skuDao;
    @Autowired
    private TaskEbsManager taskEbsManager;
    @Autowired
    private QstaManager manager;


    /**
     * 待过仓订单生成批次好
     */
    public void generateQstaBatchCode(Long ouid) {
        // 获取批订单数量
        ChooseOption chooseOption = chooseOptionDao.findByCategoryCodeAndKey("num", "100");
        while (true) {
            // 生成批次号
            List<String> channelList = queueStaDao.findChannelListByOuid(ouid, new SingleColumnRowMapper<String>(String.class));
            for (int i = 0; i < channelList.size(); i++) {
                String barchCode = queueStaDao.queryBatchcode(new SingleColumnRowMapper<String>(String.class));
                // 更新销售订单批次
                int updateQty = 0;
                if (channelList.get(i) != null) {
                    updateQty = queueStaDao.updateQstaBatchCodeByOuid(ouid, channelList.get(i), barchCode, Integer.parseInt(chooseOption.getOptionValue()));
                } else {
                    updateQty = queueStaDao.updateQstaBatchCodeByOuid1(ouid, barchCode, Integer.parseInt(chooseOption.getOptionValue()));
                }
                // 没有更新到的批次暂定任务
                if (updateQty <= 0) {
                    // 更新销售带Add订单批次
                    updateQty = queueStaDao.updateQstaBatchCodeByOuidOwner(ouid, channelList.get(i), barchCode, Integer.parseInt(chooseOption.getOptionValue()));
                    if (updateQty <= 0) {
                        // 更新退换货订单批次
                        updateQty = queueStaDao.updateQstaBatchCodeByOuidOutOwner(ouid, channelList.get(i), barchCode, Integer.parseInt(chooseOption.getOptionValue()));
                        if (updateQty <= 0) {
                            continue;
                        }
                    }
                }
            }
            if (channelList == null || channelList.size() == 0) {
                break;
            }
        }
    }

    /**
     * 查询队列批次信息
     * 
     * @param whouid
     * @param type 0:销售，1：特殊订单，2：退换货，其他：销售
     * @return
     */
    public List<String> findBatchCodeByWhouid(Long whouid, int type) {
        switch (type) {
            case 0:
                // 销售
                return queueStaDao.findBatchCodeByOuidDetial(whouid, new SingleColumnRowMapper<String>(String.class));
            case 1:
                // 特殊
                return queueStaDao.findBatchCodeByOuidDetial1(whouid, new SingleColumnRowMapper<String>(String.class));
            case 2:
                // 退换货
                return queueStaDao.findBatchCodeByOuidDetial2(whouid, new SingleColumnRowMapper<String>(String.class));
            default:
                // 销售
                return queueStaDao.findBatchCodeByOuidDetial(whouid, new SingleColumnRowMapper<String>(String.class));
        }
    }

    public void screeningQsta() {
        List<Long> ouids = operationUnitDao.findAlvailableWarehouseOuIdDetial(new SingleColumnRowMapper<Long>(Long.class));
        for (Long ouid : ouids) {
            createStaByQueue(ouid);
        }
    }

    public void createStaByQueue(Long ouid) {
        if (log.isInfoEnabled()) {
            log.info("createStaByQueue----------start, ouId:{}", ouid);
        }
        if (ouid != null) {
            try {
                // 没有批次号
                noBatchcode(ouid);
            } catch (Exception e) {
                log.error("", e);
            }
            try {
                // 有批次号
                existBatchcode(ouid);
            } catch (Exception e) {
                log.error("", e);
            }

        } else {
            log.debug("create sta error,ou is null");
        }
        if (log.isInfoEnabled()) {
            log.info("createStaByQueue----------end, ouId:{}", ouid);
        }
    }

    public List<QueueStaLineCommand> staLineMerge(QueueSta queueSta, List<QueueStaLineCommand> lines, List<QueueStaLine> staLines) {
        for (QueueStaLine line : staLines) {
            if (lines.size() == 0) {
                QueueStaLineCommand command = new QueueStaLineCommand();
                if (queueSta.getAddOwner() != null) {
                    command.setOwner(queueSta.getAddOwner());
                } else {
                    command.setOwner(queueSta.getOwner());
                }
                command.setOuid(queueSta.getMainwhouid());
                command.setSkucode(line.getSkucode());
                command.setQty(line.getQty());
                lines.add(command);
            } else {
                for (int i = 0; i < lines.size(); i++) {
                    if (lines.get(i).getSkucode().equals(line.getSkucode()) || lines.get(i).getOwner().equals(queueSta.getOwner())) {
                        lines.get(i).setQty(lines.get(i).getQty() + line.getQty());
                    } else {
                        QueueStaLineCommand command = new QueueStaLineCommand();
                        if (queueSta.getAddOwner() != null) {
                            command.setOwner(queueSta.getAddOwner());
                        } else {
                            command.setOwner(queueSta.getOwner());
                        }
                        command.setOuid(queueSta.getMainwhouid());
                        command.setSkucode(line.getSkucode());
                        command.setQty(line.getQty());
                        lines.add(command);
                    }
                }
            }
        }
        return lines;
    }

    public void errorQsta(QueueSta queueSta) {
        queueSta.setErrorcount(queueSta.getErrorcount() + 1);
        queueStaDao.save(queueSta);
    }

    private void checkSalesAfCreate(Long qstaId, Map<String, Map<String, Long>> inv) {
        // 查询某一单所有明细信息
        List<QueueStaLineCommand> lines = queueStaLineDao.findSkuQtyByStaId1(qstaId, new BeanPropertyRowMapperExt<QueueStaLineCommand>(QueueStaLineCommand.class));
        List<QueueStaLineCommand> lines1 = queueStaLineDao.findSkuQtyByStaId1(qstaId, new BeanPropertyRowMapperExt<QueueStaLineCommand>(QueueStaLineCommand.class));
        QueueSta queueSta = queueStaDao.getByPrimaryKey(qstaId);
        List<SoLineResponse> shortageDetails = new ArrayList<SoLineResponse>();
        boolean isEnoughtQty = true;
        boolean isEnoughtQty1 = true;
        for (QueueStaLineCommand line : lines) {
            Sku sku = skuDao.getByCode(line.getSkucode());
            if (sku.getExtensionCode3() != null) {
                line.setExtCode(sku.getExtensionCode3());
                for (QueueStaLineCommand line1 : lines1) {
                    Sku sku1 = skuDao.getByCode(line1.getSkucode());
                    if (!sku.getCode().equals(sku1.getCode())) {
                        if (sku1.getExtensionCode3() != null) {
                            if (sku.getExtensionCode3().equals(sku1.getExtensionCode3())) {
                                line.setSkucode(sku1.getCode());
                                line.setQty(line.getQty() + line1.getQty());
                                line.setTotalactual(line.getTotalactual().add(line1.getTotalactual()));
                                line.setOrdertotalactual(line.getOrdertotalactual().add(line1.getOrdertotalactual()));
                                line.setLineStatus(QueueStaLineStatus.LINE_STATUS_FALSE);
                            }
                        } else {
                            line.setLineStatus(QueueStaLineStatus.LINE_STATUS_FALSE);
                        }
                    } else {
                        line.setLineStatus(QueueStaLineStatus.LINE_STATUS_FALSE);
                    }
                }
            } else {
                line.setLineStatus(QueueStaLineStatus.LINE_STATUS_FALSE);
            }
        }
        // 校验库存是否足够
        List<String> extCode = skuDao.findExtCode3(queueSta.getId(), new SingleColumnRowMapper<String>(String.class));
        for (String code : extCode) {
            for (int i = 0; i < lines.size(); i++) {
                // lines.get(i).setLineType( lines.get(i).getLineType() == null
                // ? 1 :
                // lines.get(i).getLineType());
                String key = lines.get(i).getExtCode();
                if (key == null || key.equals("")) {
                    key = "0";
                }
                Map<String, Long> invmap = inv.get(key);
                Long qty;
                if (invmap == null) {
                    qty = 0l;
                    SoLineResponse detial = new SoLineResponse();
                    detial.setJmSkuCode(lines.get(i).getSkucode());
                    detial.setQty(lines1.get(i).getQty() - (qty < 0 ? 0 : qty));
                    shortageDetails.add(detial);
                    isEnoughtQty = false;
                    isEnoughtQty1 = false;
                } else {
                    String mapKey = lines.get(i).getSkucode();
                    // 获取当前店铺库存
                    qty = invmap.get(mapKey);
                    if (!key.equals("0")) {
                        if (qty == null || qty < lines.get(i).getQty()) {
                            Set<String> keys1 = invmap.keySet();
                            for (String string : keys1) {
                                qty = invmap.get(string);
                                if (qty >= lines.get(i).getQty()) {
                                    lines.get(i).setSkucode(string);
                                    break;
                                }
                            }
                            // 校验当前库存是否足够

                        }
                    }
                    if (lines.get(i).getLineStatus() != QueueStaLineStatus.LINE_STATUS_TURE) {
                        lines.get(i).setExtCode(lines.get(i).getExtCode() == null ? "" : lines.get(i).getExtCode());
                        if (lines.get(i).getExtCode().equals("")) {
                            qty = qty == null ? 0 : qty;
                            if (qty < lines.get(i).getQty()) {
                                // 库存不足
                                SoLineResponse detial = new SoLineResponse();
                                detial.setJmSkuCode(lines.get(i).getSkucode());
                                detial.setQty(lines.get(i).getQty() - (qty < 0 ? 0 : qty));
                                shortageDetails.add(detial);
                                isEnoughtQty = false;
                                isEnoughtQty1 = false;
                            } else {
                                lines.get(i).setLineStatus(QueueStaLineStatus.LINE_STATUS_TURE);
                            }
                        } else {
                            if (code != null) {
                                if (code.equals(lines.get(i).getExtCode())) {
                                    qty = qty == null ? 0 : qty;
                                    if (lines.get(i).getQty() != lines1.get(i).getQty()) {
                                        if (qty < lines.get(i).getQty()) {
                                            // 库存不足
                                            SoLineResponse detial = new SoLineResponse();
                                            detial.setJmSkuCode(lines.get(i).getSkucode());
                                            if (lines1.get(i).getQty() - (qty < 0 ? 0 : qty) == 0) {
                                                detial.setQty(lines.get(i).getQty() - (qty < 0 ? 0 : qty));
                                            } else {
                                                detial.setQty(lines1.get(i).getQty() - (qty < 0 ? 0 : qty));
                                            }
                                            shortageDetails.add(detial);
                                            isEnoughtQty1 = false;
                                        } else {
                                            lines.get(i).setLineStatus(QueueStaLineStatus.LINE_STATUS_TURE);
                                            isEnoughtQty1 = true;
                                            break;
                                        }

                                    } else {
                                        if (qty < lines.get(i).getQty()) {
                                            // 库存不足
                                            SoLineResponse detial = new SoLineResponse();
                                            detial.setJmSkuCode(lines.get(i).getSkucode());
                                            detial.setQty(lines.get(i).getQty() - (qty < 0 ? 0 : qty));
                                            shortageDetails.add(detial);
                                            isEnoughtQty = false;
                                        } else {
                                            lines.get(i).setLineStatus(QueueStaLineStatus.LINE_STATUS_TURE);
                                        }
                                    }
                                }
                            }
                        }
                    }

                }
            }
            if (!isEnoughtQty1) {
                isEnoughtQty = false;
            }
        }
        if (log.isDebugEnabled()) {
            log.debug("qstaId:{}, af check inventory is enought:{}", qstaId, isEnoughtQty);
        }
        if (isEnoughtQty) {
            try {
                // List<QueueStaLine> li =
                // queueStaLineDao.findByStaIddir(qstaId);
                // 获取头上商品数量
                Long sumqty = queueStaLineDao.querycountQty(qstaId, new SingleColumnRowMapper<Long>(Long.class));
                // 判断拆分后商品数量
                if (log.isDebugEnabled()) {
                    log.debug("qstaId:{}, af queuesta skuqty is equals sumqty:{}", qstaId, queueSta.getSkuqty().equals(sumqty));
                }
                if (queueSta.getSkuqty().equals(sumqty)) {
                    queueStaManagerExecute.createsta(qstaId, lines);

                    // 库存足够,扣减库存可用量
                    for (QueueStaLineCommand l : lines) {
                        if (l.getLineStatus() != QueueStaLineStatus.LINE_STATUS_FALSE) {

                            String key = l.getSkucode();
                            String extkey = l.getExtCode();
                            Map<String, Long> invmap = inv.get(extkey);

                            invmap.put(l.getSkucode(), invmap.get(key) - l.getQty());
                            inv.put(extkey, invmap);
                        }
                    }
                } else {
                    if (log.isDebugEnabled()) {
                        log.debug("qstaId:{}, af inventory is not enought, add error count 20 for qsta", qstaId);
                    }
                    queueStaManagerExecute.addErrorCountForQsta(qstaId, 20);
                }

            } catch (Exception e) {
                log.error("qstaId:" + qstaId, e);
                if (e instanceof BusinessException) {
                    BusinessException be = (BusinessException) e;
                    if (be.getErrorCode() == ErrorCode.OMS_ORDER_CANACEL) {
                        // 删除数据，记录日志
                        if (log.isDebugEnabled()) {
                            log.debug("qstaId:{}, af pac order cancel, delete queue and log queue", qstaId);
                        }
                        queueStaManagerExecute.removeQstaAddLog(qstaId);
                    } else {
                        // 增加错误次数
                        if (log.isDebugEnabled()) {
                            log.debug("qstaId:{}, af create sta throw exception, add error count 1 for qsta", qstaId);
                        }
                        queueStaManagerExecute.addErrorCountForQsta(qstaId, 1);
                    }
                } else {
                    // 增加错误次数
                    if (log.isDebugEnabled()) {
                        log.debug("qstaId:{}, af create sta throw exception, add error count 1 for qsta", qstaId);
                    }
                    queueStaManagerExecute.addErrorCountForQsta(qstaId, 1);
                }
            }
        } else {
            // 反馈OMS库存不足,通知OMS
            StaCreatedResponse rs = new StaCreatedResponse();
            QueueSta qsta = queueStaDao.getByPrimaryKey(qstaId);
            // 设置反馈OMS类型
            if (qsta.getType() == StockTransApplicationType.OUTBOUND_SALES.getValue()) {
                rs.setType(StaCreatedResponse.BASE_RESULT_TYPE_SO);
            } else {
                rs.setType(StaCreatedResponse.BASE_RESULT_TYPE_RA);
            }
            // 设置类型库存不足
            rs.setStatus(StaCreatedResponse.BASE_RESULT_STATUS_INV_SHORTAGE);
            rs.setSlipCode(qsta.getOrdercode());
            rs.setSoLineResponses(shortageDetails);
            String errorMsg = applicationContext.getMessage((ErrorCode.BUSINESS_EXCEPTION + ErrorCode.EI_SALES_INV_NOT_ENOUGH), new Object[] {}, Locale.SIMPLIFIED_CHINESE);
            rs.setMsg(errorMsg.toString());
            // 通知OMS
            sendNoEnoughtInvToOms(rs, qstaId);
        }

    }

    /**
     * 计算库存是否足够,
     * 
     * @param qstaId
     * @param inv
     * @param isShareWh
     */
    private void checkSalesInventoryAndCreate(Long qstaId, Map<String, Map<String, Long>> inv) {
        if (log.isInfoEnabled()) {
            log.info("checkSalesInventoryAndCreate start, qstaId:{}", qstaId);
        }
        // 查询某一单所有明细信息
        List<QueueStaLine> lines = queueStaLineDao.findSkuQtyByStaId(qstaId, new BeanPropertyRowMapperExt<QueueStaLine>(QueueStaLine.class));
        // 库存不足反馈信息
        QueueSta queueSta = queueStaDao.getByPrimaryKey(qstaId);
        List<SoLineResponse> shortageDetails = new ArrayList<SoLineResponse>();

        boolean isEnoughtQty = true;
        // 校验库存是否足够
        for (QueueStaLine l : lines) {
            if (l.getOwner() != null) {

                String key = l.getSkucode();
                Map<String, Long> invmap = inv.get(key);
                Long iqty;
                if (invmap == null) {
                    // 库存没有SKU
                    if (l.getLineType() == QueueStaLineType.LINE_TYPE_TURE) {
                        SoLineResponse detial = new SoLineResponse();
                        detial.setJmSkuCode(l.getSkucode());
                        detial.setQty(l.getQty());
                        shortageDetails.add(detial);
                        isEnoughtQty = false;
                    } else {
                        QueueStaLine line = queueStaLineDao.getByPrimaryKey(l.getId());
                        line.setLineStatus(QueueStaLineStatus.LINE_STATUS_FALSE);
                        queueStaLineDao.save(line);
                    }
                } else {
                    // 获取当前店铺库存
                    iqty = invmap.get(l.getOwner());
                    // 校验当前库存是否足够
                    iqty = (invmap.get(l.getOwner()) == null ? 0 : invmap.get(l.getOwner())) - l.getQty();
                    if (iqty < 0) {
                        // 库存没有SKU
                        if (l.getLineType() == QueueStaLineType.LINE_TYPE_TURE) {
                            SoLineResponse detial = new SoLineResponse();
                            detial.setJmSkuCode(l.getSkucode());
                            detial.setQty(l.getQty());
                            shortageDetails.add(detial);
                            isEnoughtQty = false;
                        } else {
                            QueueStaLine line = queueStaLineDao.getByPrimaryKey(l.getId());
                            line.setLineStatus(QueueStaLineStatus.LINE_STATUS_FALSE);
                            queueStaLineDao.save(line);
                        }
                    }
                }
            } else {
                String key = l.getSkucode();
                Map<String, Long> invmap = inv.get(key);
                Long qty;
                if (invmap == null) {
                    qty = null;
                } else {
                    Long iqty;
                    String mapKey = "";
                    // 判断是否带AddOwner
                    if (StringUtils.hasText(queueSta.getAddOwner())) {
                        mapKey = queueSta.getAddOwner();
                    } else {
                        mapKey = queueSta.getOwner();
                    }
                    if (log.isDebugEnabled()) {
                        log.debug("check inv,order:{},sku:{},owner:{}", qstaId, key, mapKey);
                    }
                    // 获取当前店铺库存
                    iqty = invmap.get(mapKey);
                    // 校验当前库存是否足够
                    iqty = (invmap.get(mapKey) == null ? 0 : invmap.get(mapKey)) - l.getQty();
                    if (log.isDebugEnabled()) {
                        log.debug("check inv,order:{},sku:{},owner:{},iqty:{}", qstaId, key, mapKey, iqty);
                    }
                    // 当前库存不足进入
                    if (iqty < 0) {
                        // 获取渠道信息
                        String[] channelList = queueSta.getChannelList().split(";");
                        qty = 0l;
                        // 循环共享店铺获取总库存
                        for (int i = 0; i < channelList.length; i++) {
                            Long invqty = invmap.get(channelList[i]);
                            qty += invqty == null ? 0 : invqty;
                            if (log.isDebugEnabled()) {
                                log.debug("check inv,order:{},sku:{},owner:{},invqty:{},qty:{}", qstaId, key, mapKey, invqty, qty);
                            }
                        }
                    } else {
                        // 当前库存
                        qty = invmap.get(mapKey);
                        if (log.isDebugEnabled()) {
                            log.debug("check inv,order:{},sku:{},owner:{},current qty:{}", qstaId, key, mapKey, qty);
                        }
                    }
                }

                if (qty == null) {
                    // 库存没有SKU
                    if (l.getLineType() == QueueStaLineType.LINE_TYPE_TURE) {
                        SoLineResponse detial = new SoLineResponse();
                        detial.setJmSkuCode(l.getSkucode());
                        detial.setQty(l.getQty());
                        shortageDetails.add(detial);
                        isEnoughtQty = false;
                        if (log.isDebugEnabled()) {
                            log.debug("check inv,order:{},sku:{},current qty:{};sku no inv", qstaId, key, qty);
                        }
                    } else {
                        QueueStaLine line = queueStaLineDao.getByPrimaryKey(l.getId());
                        line.setLineStatus(QueueStaLineStatus.LINE_STATUS_FALSE);
                        queueStaLineDao.save(line);
                        if (log.isDebugEnabled()) {
                            log.debug("check inv,order:{},sku:{},current qty:{};sku egnore", qstaId, key, qty);
                        }
                    }

                } else {
                    if (qty < l.getQty()) {
                        if (log.isDebugEnabled()) {
                            log.debug("check inv,order:{},sku:{},qty:{},lqty:{};sku egnore", qstaId, key, qty, l.getQty());
                        }
                        // 库存不足
                        if (l.getLineType() == QueueStaLineType.LINE_TYPE_TURE) {
                            SoLineResponse detial = new SoLineResponse();
                            detial.setJmSkuCode(l.getSkucode());
                            detial.setQty(l.getQty() - (qty < 0 ? 0 : qty));
                            shortageDetails.add(detial);
                            isEnoughtQty = false;
                            if (log.isDebugEnabled()) {
                                log.debug("check inv,order:{},sku:{},need qty:{};sku egnore", qstaId, key, detial.getQty());
                            }
                        } else {
                            QueueStaLine line = queueStaLineDao.getByPrimaryKey(l.getId());
                            line.setLineStatus(QueueStaLineStatus.LINE_STATUS_FALSE);
                            queueStaLineDao.save(line);
                            if (log.isDebugEnabled()) {
                                log.debug("check inv,order:{},sku:{};check ok", qstaId, key);
                            }
                        }
                    }
                }
            }
        }
        if (log.isDebugEnabled()) {
            log.debug("qstaId:{}, check inventory is enought:{}", qstaId, isEnoughtQty);
        }
        if (isEnoughtQty) {
            // 创建作业单,通知OMS创建完成
            try {
                List<QueueStaLine> lis = queueStaLineDao.findByStaIddir(qstaId);
                if (!StringUtils.hasText(queueSta.getAddOwner())) {
                    // 当前店铺库存不足，拆单
                    for (QueueStaLine l : lis) {
                        if (l.getLineStatus() != QueueStaLineStatus.LINE_STATUS_FALSE) {
                            QueueStaLine qsl = queueStaLineDao.getByPrimaryKey(l.getId());
                            String key = qsl.getSkucode();
                            Map<String, Long> invmap = inv.get(key);
                            Long iqty;
                            iqty = qsl.getQty();
                            // 获取行单价
                            BigDecimal unit_total = qsl.getTotalactual().divide(new BigDecimal(iqty), BigDecimal.ROUND_HALF_UP);
                            // 获取行单价
                            BigDecimal unit_orderTotal = qsl.getOrdertotalactual().divide(new BigDecimal(iqty), BigDecimal.ROUND_HALF_UP);
                            // 判断当前店铺库存不足，进入
                            if (0 < (qsl.getQty() - (invmap.containsKey(qsl.getOwner()) ? invmap.get(qsl.getOwner()) : 0))) {
                                while (iqty > 0) {
                                    if (iqty > 0) {
                                        // 获取渠道
                                        String[] channelList = queueSta.getChannelList().split(";");
                                        for (int i = 0; i < channelList.length; i++) {
                                            Long invqty = invmap.containsKey(channelList[i]) ? invmap.get(channelList[i]) : 0l;
                                            if (invqty != null && invqty != 0) {
                                                // 当前明细商品数量减去某个渠道库存数量
                                                Long numqty = iqty - invqty;
                                                // 拆单
                                                if (numqty >= 0) {
                                                    if (invqty > 0) {
                                                        BigDecimal total = unit_total.multiply(new BigDecimal(invqty));
                                                        BigDecimal orderTotal = unit_orderTotal.multiply(new BigDecimal(invqty));
                                                        QueueStaLine sl = new QueueStaLine();
                                                        sl.setTotalactual(total);
                                                        sl.setOrdertotalactual(orderTotal);
                                                        sl.setOrdertotalbfdiscount(qsl.getOrdertotalbfdiscount());
                                                        sl.setQueueSta(queueSta);
                                                        sl.setSkucode(qsl.getSkucode());
                                                        sl.setOwner(channelList[i]);
                                                        sl.setQty(invqty);
                                                        sl.setListprice(qsl.getListprice());
                                                        sl.setUnitprice(qsl.getUnitprice());
                                                        sl.setDirection(qsl.getDirection());
                                                        sl.setInvstatusid(qsl.getInvstatusid());
                                                        sl.setActivitysource(qsl.getActivitysource());
                                                        sl.setLineType(qsl.getLineType());
                                                        sl.setLineStatus(qsl.getLineStatus());
                                                        queueStaLineDao.save(sl);
                                                        iqty = iqty - invqty;
                                                        // 判断是否特殊商品
                                                        if (queueSta.getIsspecialpackaging()) {
                                                            List<QueueGiftLine> queueGiftLines = queueGiftLineDao.getByfindQstaline(l.getId(), new BeanPropertyRowMapper<QueueGiftLine>(QueueGiftLine.class));
                                                            for (QueueGiftLine giftLine : queueGiftLines) {
                                                                QueueGiftLine line = new QueueGiftLine();
                                                                line.setMemo(giftLine.getMemo());
                                                                line.setQueueStaLine(sl);
                                                                line.setType(giftLine.getType());
                                                                queueGiftLineDao.save(line);

                                                            }
                                                        }
                                                    }
                                                } else if (numqty < 0) {
                                                    if (iqty > 0) {
                                                        BigDecimal total = unit_total.multiply(new BigDecimal(iqty));
                                                        BigDecimal orderTotal = unit_orderTotal.multiply(new BigDecimal(iqty));
                                                        QueueStaLine sl = new QueueStaLine();
                                                        sl.setTotalactual(total);
                                                        sl.setOrdertotalactual(orderTotal);
                                                        sl.setOrdertotalbfdiscount(qsl.getOrdertotalbfdiscount());
                                                        sl.setQueueSta(queueSta);
                                                        sl.setSkucode(qsl.getSkucode());
                                                        sl.setOwner(channelList[i]);
                                                        sl.setQty(iqty);
                                                        sl.setListprice(qsl.getListprice());
                                                        sl.setUnitprice(qsl.getUnitprice());
                                                        sl.setDirection(qsl.getDirection());
                                                        sl.setInvstatusid(qsl.getInvstatusid());
                                                        sl.setActivitysource(qsl.getActivitysource());
                                                        sl.setLineType(qsl.getLineType());
                                                        sl.setLineStatus(qsl.getLineStatus());
                                                        queueStaLineDao.save(sl);
                                                        iqty = iqty - invqty;
                                                        // 判断是否特殊商品
                                                        if (queueSta.getIsspecialpackaging()) {
                                                            List<QueueGiftLine> queueGiftLines = queueGiftLineDao.getByfindQstaline(l.getId(), new BeanPropertyRowMapper<QueueGiftLine>(QueueGiftLine.class));
                                                            for (QueueGiftLine giftLine : queueGiftLines) {
                                                                QueueGiftLine line = new QueueGiftLine();
                                                                line.setMemo(giftLine.getMemo());
                                                                line.setQueueStaLine(sl);
                                                                line.setType(giftLine.getType());
                                                                queueGiftLineDao.save(line);

                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        if (queueSta.getIsspecialpackaging()) {
                                            // 删除被拆单原有特殊信息
                                            queueGiftLineDao.lineDelete(l.getId());
                                        }
                                        // 删除被拆单原有明细信息
                                        queueStaLineDao.delete(qsl);
                                    }

                                }
                            }
                        }
                    }
                }
                List<QueueStaLine> li = queueStaLineDao.findByStaIddir(qstaId);
                // 获取头上商品数量
                Long sumqty = queueStaLineDao.querycountQty(qstaId, new SingleColumnRowMapper<Long>(Long.class));
                // 判断拆分后商品数量
                if (log.isDebugEnabled()) {
                    log.debug("qstaId:{}, queuesta skuqty is equals sumqty:{}", qstaId, queueSta.getSkuqty().equals(sumqty));
                }
                if (queueSta.getSkuqty().equals(sumqty)) {
                    // 库存足够,扣减库存可用量
                    queueStaManagerExecute.createsta(qstaId);
                    for (QueueStaLine l : li) {
                        String key = l.getSkucode();
                        Map<String, Long> invmap = inv.get(key);
                        String owner = "";
                        if (StringUtils.hasText(queueSta.getAddOwner())) {
                            owner = queueSta.getAddOwner();
                        } else {
                            owner = l.getOwner();
                        }
                        invmap.put(l.getOwner(), invmap.get(owner) - l.getQty());
                        inv.put(key, invmap);
                        if (log.isDebugEnabled()) {
                            log.debug("check inv,order:{},sku:{},map data: key : {},qty : {}", qstaId, key, l.getOwner(), invmap.get(l.getOwner()));
                        }
                    }
                } else {
                    if (log.isDebugEnabled()) {
                        log.debug("qstaId:{}, inventory is not enought, add error count 20 for qsta", qstaId);
                    }
                    queueStaManagerExecute.addErrorCountForQsta(qstaId, 20);
                }

            } catch (Exception e) {
                log.error("qstaId:" + qstaId, e);
                if (e instanceof BusinessException) {
                    BusinessException be = (BusinessException) e;
                    if (be.getErrorCode() == ErrorCode.OMS_ORDER_CANACEL) {
                        if (log.isDebugEnabled()) {
                            log.debug("qstaId:{}, pac order cancel, delete queue and log queue", qstaId);
                        }
                        // 删除数据，记录日志
                        queueStaManagerExecute.removeQstaAddLog(qstaId);
                    } else {
                        // 增加错误次数
                        if (log.isDebugEnabled()) {
                            log.debug("qstaId:{}, create sta throw exception, add error count 1 for qsta", qstaId);
                        }
                        queueStaManagerExecute.addErrorCountForQsta(qstaId, 1);
                    }
                } else {
                    // 增加错误次数
                    if (log.isDebugEnabled()) {
                        log.debug("qstaId:{}, create sta throw exception, add error count 1 for qsta", qstaId);
                    }
                    queueStaManagerExecute.addErrorCountForQsta(qstaId, 1);
                }
            }
        } else {
            // 反馈OMS库存不足,通知OMS
            StaCreatedResponse rs = new StaCreatedResponse();
            QueueSta qsta = queueStaDao.getByPrimaryKey(qstaId);
            // 设置反馈OMS类型
            if (qsta.getType() == StockTransApplicationType.OUTBOUND_SALES.getValue()) {
                rs.setType(StaCreatedResponse.BASE_RESULT_TYPE_SO);
            } else {
                rs.setType(StaCreatedResponse.BASE_RESULT_TYPE_RA);
            }
            // 设置类型库存不足
            rs.setStatus(StaCreatedResponse.BASE_RESULT_STATUS_INV_SHORTAGE);
            rs.setSlipCode(qsta.getOrdercode());
            rs.setSoLineResponses(shortageDetails);
            String errorMsg = applicationContext.getMessage((ErrorCode.BUSINESS_EXCEPTION + ErrorCode.EI_SALES_INV_NOT_ENOUGH), new Object[] {}, Locale.SIMPLIFIED_CHINESE);
            rs.setMsg(errorMsg.toString());
            // 通知OMS
            sendNoEnoughtInvToOms(rs, qstaId);
        }
        if (log.isInfoEnabled()) {
            log.info("checkSalesInventoryAndCreate end, qstaId:{}", qstaId);
        }

    }

    /**
     * 调用OMS接口
     * 
     * 1.OMS成功，无需转仓，删除队列数据，记录日志
     * 
     * 2.OMS成功，需转仓，修改队列仓库，记录日志
     * 
     * 3.OMS接口无法连接或系统,回滚事务
     * 
     * 4.OMS反馈失败或单据取消 throw BusinessException ErrorCode=OMS_SYSTEM_ERROR 回滚事务
     */
    private void sendNoEnoughtInvToOms(StaCreatedResponse createdResponse, Long qstaId) {
        try {
            queueStaManagerExecute.sendCreateResultToOms(createdResponse, qstaId);
        } catch (Exception e) {
            // 增加错误次数
            if (e instanceof BusinessException) {
                BusinessException be = (BusinessException) e;
                log.warn("qstaId: {},error : {} ", qstaId, be.getErrorCode());
                if (be.getErrorCode() == ErrorCode.OMS_ORDER_CANACEL) {
                    // 删除数据，记录日志
                    if (log.isDebugEnabled()) {
                        log.debug("qstaId:{}, pac order cancel, delete queue and log queue", qstaId);
                    }
                    queueStaManagerExecute.removeQstaAddLog(qstaId);
                } else {
                    // 增加错误次数
                    if (log.isDebugEnabled()) {
                        log.debug("qstaId:{},create sta throw exception, add error count 1 for qsta,qsta order code is:{}", qstaId);
                    }
                    queueStaManagerExecute.addErrorCountForQsta(qstaId, 1);
                }
            } else {
                log.error("qstaId:" + qstaId, e);
                // 增加错误次数
                if (log.isDebugEnabled()) {
                    log.debug("qstaId:{},create sta throw exception, add error count 1 for qsta,qsta order code is:{}", qstaId);
                }
                queueStaManagerExecute.addErrorCountForQsta(qstaId, 1);
            }
        }
    }

    public void createStabyBatchCode(Long ouid, String batchCode) {
        if (log.isInfoEnabled()) {
            log.info("createStabyBatchCode：{}, ouId:{}, sysTime:{} ----start", new Object[] {batchCode, ouid, System.currentTimeMillis()});
        }
        // 查询出该批次所有信息
        List<QueueSta> queueStas = queueStaDao.findIdsByStaBatchcode(ouid, batchCode, new BeanPropertyRowMapperExt<QueueSta>(QueueSta.class));
        if (queueStas.size() == 0) {

        } else {
            Map<String, Map<String, Long>> invMap = null;
            String channel = "";
            if (log.isDebugEnabled()) {
                log.debug("queueStas.get(0) has addOwner:{}, channelList is not null:{}", StringUtils.hasText(queueStas.get(0).getAddOwner()), null != queueStas.get(0).getChannelList());
            }
            // 判断是否带AddOwner
            if (StringUtils.hasText(queueStas.get(0).getAddOwner())) {
                // 查询带AddOwner的库存信息
                if (log.isDebugEnabled()) {
                    log.debug("Debug -----------------------------1--------------------findSalesAvailQtyByBatchNoShare： Time:" + System.currentTimeMillis());
                }
                invMap = inventoryDao.findSalesAvailQtyByBatchNoShare(ouid, batchCode, new MapInvOwnerMapper());
                if (log.isDebugEnabled()) {
                    log.debug("Debug -----------------------------0--------------------findSalesAvailQtyByBatchNoShare： Time:" + System.currentTimeMillis());
                }
            } else {
                if (queueStas.get(0).getChannelList() != null) {
                    String[] channelList = queueStas.get(0).getChannelList().split(";");
                    for (int i = 0; i < channelList.length; i++) {
                        if (i == 0) {
                            channel = "'" + channelList[i] + "'";
                        } else {
                            channel += ",'" + channelList[i] + "'";
                        }
                    }
                    // 查询库存信息
                    if (log.isDebugEnabled()) {
                        log.debug("Debug -----------------------------1--------------------findSalesAvailQtyByBatchShare： Time:" + System.currentTimeMillis());
                    }
                    invMap = inventoryDao.findSalesAvailQtyByBatchShare(ouid, batchCode, channel, new MapInvMapper());
                    if (log.isDebugEnabled()) {
                        log.debug("Debug -----------------------------0--------------------findSalesAvailQtyByBatchShare： Time:" + System.currentTimeMillis());
                    }
                } else {
                    if (log.isDebugEnabled()) {
                        log.debug("Debug -----------------------------1--------------------findSalesAvailQtyByBatchShare2： Time:" + System.currentTimeMillis());
                    }
                    invMap = inventoryDao.findSalesAvailQtyByBatchShare2(ouid, batchCode, new MapInvMapper());
                    if (log.isDebugEnabled()) {
                        log.debug("Debug -----------------------------1--------------------findSalesAvailQtyByBatchShare2： Time:" + System.currentTimeMillis());
                    }
                }
            }
            if (invMap == null) {
                invMap = new HashMap<String, Map<String, Long>>();
            }
            // ============批次中按单计算库存,执行创建作业单逻辑======================= //
            for (QueueSta queueSta : queueStas) {
                try {
                    // 销售出库/换货出库校验库存创建单据
                    StockTransApplication sta = staDao.findStaBySlipCodeNotCancel(queueSta.getOrdercode());
                    if (sta == null) {
                        // 执行校验库存,创建作业单
                        if (log.isDebugEnabled()) {
                            log.debug("sta is not exist, check sales inventory and create sta, qstaId is:{}", queueSta.getId());
                        }
                        checkSalesInventoryAndCreate(queueSta.getId(), invMap);
                    } else {
                        // 标识单据异常不处理，修改error count + 100
                        if (log.isDebugEnabled()) {
                            log.debug("sta is exist, add error count 100 for qsta,qstaId is:{}", queueSta.getId());
                        }
                        queueStaManagerExecute.addErrorCountForQsta(queueSta.getId(), 100);
                    }
                } catch (BusinessException e) {
                    log.error("qstaId:" + queueSta.getId(), e);
                } catch (Exception e) {
                    log.error("qstaId:" + queueSta.getId(), e);
                }
            }
        }
        List<QueueSta> queueStas1 = queueStaDao.findIdsByStaBatchcode1(ouid, batchCode, new BeanPropertyRowMapperExt<QueueSta>(QueueSta.class));
        if (queueStas1 == null || queueStas1.size() == 0) {
            return;
        } else {
            Map<String, Map<String, Long>> invMap1 = null;
            // 查询库存信息

            invMap1 = inventoryDao.findSalesAvailQtyByBatchShare1(ouid, queueStas1.get(0).getOwner(), new MapInvMapper());
            if (invMap1 == null) {
                invMap1 = new HashMap<String, Map<String, Long>>();
            }
            // ============批次中按单计算库存,执行创建作业单逻辑======================= //
            for (QueueSta queueSta : queueStas1) {
                try {
                    // 销售出库/换货出库校验库存创建单据
                    StockTransApplication sta = staDao.findStaBySlipCodeNotCancel(queueSta.getOrdercode());
                    if (sta == null) {
                        // 执行校验库存,创建作业单
                        if (log.isDebugEnabled()) {
                            log.debug("sta is not exist, check sales af inventory and create sta, qstaId is:{}", queueSta.getId());
                        }
                        checkSalesAfCreate(queueSta.getId(), invMap1);
                    } else {
                        // 标识单据异常不处理，修改error count + 100
                        if (log.isDebugEnabled()) {
                            log.debug("af sta is exist, add error count 100 for qsta,qstaId is:{}", queueSta.getId());
                        }
                        queueStaManagerExecute.addErrorCountForQsta(queueSta.getId(), 100);
                    }
                } catch (BusinessException e) {
                    log.error("qstaId:" + queueSta.getId(), e);
                } catch (Exception e) {
                    log.error("qstaId:" + queueSta.getId(), e);
                }
            }
        }
        if (log.isInfoEnabled()) {
            log.info("createStabyBatchCode：{}, ouId:{}, sysTime:{} ----end", new Object[] {batchCode, ouid, System.currentTimeMillis()});
        }
    }

    private void noBatchcode(Long whouid) {
        if (log.isInfoEnabled()) {
            log.info("noBatchcode, ouId:{}, sysTime:{} ----start", new Object[] {whouid, System.currentTimeMillis()});
        }
        // 获取批订单数量
        ChooseOption chooseOption = chooseOptionDao.findByCategoryCodeAndKey("num", "100");
        while (true) {
            // 生成批次号

            List<String> channelList = queueStaDao.findChannelListByOuid(whouid, new SingleColumnRowMapper<String>(String.class));
            for (int i = 0; i < channelList.size(); i++) {
                String barchCode = queueStaDao.queryBatchcode(new SingleColumnRowMapper<String>(String.class));
                // 更新销售订单批次
                int updateQty = 0;
                if (channelList.get(i) != null) {
                    updateQty = queueStaDao.updateQstaBatchCodeByOuid(whouid, channelList.get(i), barchCode, Integer.parseInt(chooseOption.getOptionValue()));
                    if (log.isDebugEnabled()) {
                        log.debug("no batchcode qsta, update batch by ouId and channelList(no add owner), ouId is:{}, update qty is:{}", whouid, updateQty);
                    }
                    if (updateQty > 0) {
                        // createStabyBatchCode(whouid, barchCode);
                        manager.createStabyBatchCode(whouid, barchCode);
                    }
                } else {
                    updateQty = queueStaDao.updateQstaBatchCodeByOuid1(whouid, barchCode, Integer.parseInt(chooseOption.getOptionValue()));
                    if (log.isDebugEnabled()) {
                        log.debug("no batchcode qsta, update batch by ouId(no add owner), ouId is:{}, update qty is:{}", whouid, updateQty);
                    }
                    if (updateQty > 0) {
                        // createStabyBatchCode(whouid, barchCode);
                        manager.createStabyBatchCode(whouid, barchCode);
                    }
                }
                // 没有更新到的批次暂定任务
                if (updateQty <= 0) {
                    // 更新销售带Add订单批次

                    updateQty = queueStaDao.updateQstaBatchCodeByOuidOwner(whouid, channelList.get(i), barchCode, Integer.parseInt(chooseOption.getOptionValue()));
                    if (log.isDebugEnabled()) {
                        log.debug("no batchcode qsta, update batch by ouId and owner(has add owner), ouId is:{}, update qty is:{}", whouid, updateQty);
                    }
                    if (updateQty > 0) {
                        createStabyBatchCode(whouid, barchCode);
                    }
                    if (updateQty <= 0) {
                        // 更新退换货订单批次
                        updateQty = queueStaDao.updateQstaBatchCodeByOuidOutOwner(whouid, channelList.get(i), barchCode, Integer.parseInt(chooseOption.getOptionValue()));
                        if (updateQty > 0) {
                            createStabyBatchCode(whouid, barchCode);
                        }
                        if (updateQty <= 0) {
                            continue;
                        }

                    }
                }
            }
            if (channelList == null || channelList.size() == 0) {
                break;
            }
        }
        if (log.isInfoEnabled()) {
            log.info("noBatchcode, ouId:{}, sysTime:{} ----end", new Object[] {whouid, System.currentTimeMillis()});
        }
    }

    public void existBatchcode(Long whouid) {
        if (log.isInfoEnabled()) {
            log.info("existBatchcode, ouId:{}, sysTime:{} ----start", new Object[] {whouid, System.currentTimeMillis()});
        }
        // 正常
        List<String> batchCode = queueStaDao.findBatchCodeByOuidDetial(whouid, new SingleColumnRowMapper<String>(String.class));
        for (String barchCode : batchCode) {
            createStabyBatchCode(whouid, barchCode);
        }
        // 特殊
        List<String> batchCode1 = queueStaDao.findBatchCodeByOuidDetial1(whouid, new SingleColumnRowMapper<String>(String.class));
        for (String barchCode : batchCode1) {
            createStabyBatchCode(whouid, barchCode);
        }
        // 退货换
        List<String> barchCodes = queueStaDao.findBatchCodeByOuidDetial2(whouid, new SingleColumnRowMapper<String>(String.class));
        for (String barchCode : barchCodes) {
            createStabyBatchCode(whouid, barchCode);
        }
        // List<String> barchCodes = queueStaDao.findBatchCodeByOuid(whouid, new
        // SingleColumnRowMapper<String>(String.class));
        // for (String barchCode : barchCodes) {
        // createStabyBatchCode(whouid, barchCode);
        // }
        if (log.isInfoEnabled()) {
            log.info("existBatchcode, ouId:{}, sysTime:{} ----end", new Object[] {whouid, System.currentTimeMillis()});
        }
    }

    /**
     * 校验库存
     * 
     * @param invs
     * @param createdResponse
     * @param staLines
     * @param queueSta
     * @return
     */
    public StaCreatedResponse checkInventory(List<InventoryCommand> invs, StaCreatedResponse createdResponse, List<QueueStaLine> staLines, QueueSta queueSta) {
        List<SoLineResponse> shortageDetails = new ArrayList<SoLineResponse>();
        for (QueueStaLine line : staLines) {
            List<String> rs = new ArrayList<String>();
            if (invs.size() > 0) {
                for (InventoryCommand inv : invs) {
                    // 校验库存数量
                    if (line.getOwner().equals(inv.getOwner()) || line.getSkucode().equals(inv.getSkuCode())) {

                        if (inv.getSalesAvailQty() >= line.getQty()) {
                            inv.setSalesAvailQty(inv.getSalesAvailQty() - line.getQty());
                            break;
                        } else {
                            SoLineResponse detail = new SoLineResponse();
                            detail.setQty(inv.getSalesAvailQty() - line.getQty());
                            detail.setJmSkuCode(inv.getSkuCode());
                            String errorMsg = applicationContext.getMessage((ErrorCode.BUSINESS_EXCEPTION + ErrorCode.EI_SALES_INV_NOT_ENOUGH), new Object[] {line.getSkucode(), inv.getSalesAvailQty() - line.getQty()}, Locale.SIMPLIFIED_CHINESE);
                            rs.add(errorMsg);
                            shortageDetails.add(detail);
                            break;
                        }
                    }
                }
            } else {
                SoLineResponse detail = new SoLineResponse();
                detail.setQty(line.getQty());
                detail.setJmSkuCode(line.getSkucode());
                String errorMsg = applicationContext.getMessage((ErrorCode.BUSINESS_EXCEPTION + ErrorCode.EI_SALES_INV_NOT_ENOUGH), new Object[] {line.getSkucode(), line.getQty()}, Locale.SIMPLIFIED_CHINESE);
                rs.add(errorMsg);
                shortageDetails.add(detail);
            }
            if (rs.size() > 0) {
                StringBuffer errorMsg = new StringBuffer();
                for (String s : rs) {
                    errorMsg.append(s);
                }
                // 销售可用量不足:{0}
                createdResponse = new StaCreatedResponse();
                if (queueSta.getType() == 21) {
                    createdResponse.setType(1);
                } else {
                    createdResponse.setType(2);
                }
                createdResponse.setStatus(2);
                createdResponse.setSlipCode(queueSta.getOrdercode());
                createdResponse.setSoLineResponses(shortageDetails);
                createdResponse.setMsg(errorMsg.toString());
            }
        }
        return createdResponse;
    }

    @Override
    public void salesInventoryOms() {
        while (true) {
            log.debug("debug--salesInventoryOms.................start ");
            Date dt = new Date();
            Long time = dt.getTime();// 获取当前时间
            String times = SYS_SEQ_INV_INV_PRE + time;
            log.debug("debug--salesInventoryOms.................start" + times);

            int updateQty = logQueueDao.updateLogQueueBatchId(times);

            if (updateQty > 0) {
                try {
                    taskEbsManager.salesInventoryOms(times);
                } catch (Exception e) {
                    log.error("salesInventoryOms---Error:" + times);
                    log.error(e.getMessage());
                }
            } else {
                List<String> batchIds = logQueueDao.queryLogQueueBatchId(new SingleColumnRowMapper<String>(String.class));
                for (String batchId : batchIds) {
                    try {
                        taskEbsManager.salesInventoryOms(batchId);
                    } catch (Exception e) {
                        log.error("salesInventoryOms---Error:" + batchId);
                        log.error(e.getMessage());
                    }
                }
                break;
            }
        }
    }
}
