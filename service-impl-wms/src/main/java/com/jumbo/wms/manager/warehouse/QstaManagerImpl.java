package com.jumbo.wms.manager.warehouse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import loxia.dao.support.BeanPropertyRowMapperExt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;

import com.jumbo.MsgToWcsThread;
import com.jumbo.dao.authorization.OperationUnitDao;
import com.jumbo.dao.commandMapper.MapInvMapper;
import com.jumbo.dao.system.ChooseOptionDao;
import com.jumbo.dao.warehouse.InventoryDao;
import com.jumbo.dao.warehouse.QueueStaDao;
import com.jumbo.dao.warehouse.QueueStaLineDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.wms.model.automaticEquipment.WcsInterfaceType;
import com.jumbo.wms.model.command.CheckInventoryResaultCommand;
import com.jumbo.wms.model.system.ChooseOption;
import com.jumbo.wms.model.warehouse.InvQstaLineCommand;
import com.jumbo.wms.model.warehouse.QueueSta;
import com.jumbo.wms.model.warehouse.QueueStaLine;
import com.jumbo.wms.model.warehouse.QueueStaLineStatus;
import com.jumbo.wms.model.warehouse.QueueStaLineType;
import com.jumbo.wms.model.warehouse.StockTransApplication;

@Service("qstaManager")
public class QstaManagerImpl implements QstaManager {
    /**
	 * 
	 */
    private static final long serialVersionUID = -1800877127505104806L;
    protected static final Logger log = LoggerFactory.getLogger(QstaManagerImpl.class);
    @Autowired
    private QueueStaDao queueStaDao;
    @Autowired
    private QueueStaLineDao queueStaLineDao;
    @Autowired
    private InventoryDao inventoryDao;
    @Resource
    private StockTransApplicationDao staDao;
    @Autowired
    private QueueStaManagerExecute queueStaManagerExecute;
    @Autowired
    private ChooseOptionDao chooseOptionDao;
    @Autowired
    private QstaManagerExecute qstaManagerExecute;
    @Autowired
    private OperationUnitDao operationUnitDao;

    public void screeningQsta() {
        List<Long> ouids = operationUnitDao.findAlvailableWarehouseOuId(new SingleColumnRowMapper<Long>(Long.class));
        for (Long ouid : ouids) {
            createStaByQueue(ouid);
        }
    }

    public void createStaByQueue(Long ouid) {
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
            log.error("create sta error,ou is null");
        }
    }

    private void noBatchcode(Long whouid) {
        // 获取批订单数量
        ChooseOption chooseOption = chooseOptionDao.findByCategoryCodeAndKey("num", "100");
        // 生成批次号
        List<String> channelList = queueStaDao.findChannelListByOuid(whouid, new SingleColumnRowMapper<String>(String.class));
        int updateQty = 0;
        for (int i = 0; i < channelList.size(); i++) {
            String barchCode = queueStaDao.queryBatchcode(new SingleColumnRowMapper<String>(String.class));
            // 更新销售订单批次
            boolean status = true;
            while (status) {
                updateQty = queueStaDao.updateQstaBatchCodeByOuid(whouid, channelList.get(i), barchCode, Integer.parseInt(chooseOption.getOptionValue()));
                if (log.isDebugEnabled()) {
                    log.debug("no batchcode qsta, update batch by ouId, update qty is:{}", updateQty);
                }
                // --------------------执行创建作业单任务-------------------------------
                if (updateQty <= 0) {
                    updateQty = queueStaDao.updateQstaBatchCodeByOuidOwner(whouid, channelList.get(i), barchCode, Integer.parseInt(chooseOption.getOptionValue()));
                    if (log.isDebugEnabled()) {
                        log.debug("no batchcode qsta, update batch by ouId and owner, update qty is:{}", updateQty);
                    }
                    if (updateQty <= 0) {
                        status = false;
                    } else {
                        createStabyBatchCode(whouid, barchCode);
                    }

                } else {
                    try {
                        createStabyBatchCode(whouid, barchCode);
                    } catch (Exception e) {
                        log.error("", e);
                    }
                }
            }
        }
    }

    public void createStabyBatchCode(Long ouid, String batchCode) {
        if (log.isInfoEnabled()) {
            log.info("check inventory and create sta by batchCode start,ouId is:{}, batchCode is:{}", ouid, batchCode);
        }
        // 查询出该批次所有信息
        List<QueueSta> queueStas = queueStaDao.findIdsByStaBatchcode(ouid, batchCode, new BeanPropertyRowMapperExt<QueueSta>(QueueSta.class));
        if (log.isInfoEnabled()) {
            log.info("get qsta list finish,ouId is:{}, batchCode is:{}", ouid, batchCode);
        }
        if (queueStas.size() == 0) {
            if (log.isInfoEnabled()) {
                log.info("get qsta list finish , size 0 ,ouId is:{}, batchCode is:{}", ouid, batchCode);
            }
        } else {
            if (log.isInfoEnabled()) {
                log.info("get qsta list finish , size is {} ,ouId is:{}, batchCode is:{}", queueStas.size(), ouid, batchCode);
            }
            Map<String, Map<String, Long>> invMap = null;
            String channel = "";
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
                if (log.isInfoEnabled()) {
                    log.info("get qsta find inventory start ,ouId is:{}, batchCode is:{}", ouid, batchCode);
                }
                invMap = inventoryDao.findSalesAvailQtyByBatchShareDetial(ouid, batchCode, channel, new MapInvMapper());
                if (log.isInfoEnabled()) {
                    log.info("get qsta find inventory end ,ouId is:{}, batchCode is:{}", ouid, batchCode);
                }
            }
            if (invMap == null) {
                invMap = new HashMap<String, Map<String, Long>>();
            }
            // ============批次中按单计算库存,执行创建作业单逻辑======================= //
            List<CheckInventoryResaultCommand> maps = new ArrayList<CheckInventoryResaultCommand>();
            for (QueueSta queueSta : queueStas) {
                if (log.isInfoEnabled()) {
                    log.info("create sta start, sta id : {} ,ouId is:{}, batchCode is:{}", queueSta.getId(), ouid, batchCode);
                }
                // 销售出库/换货出库校验库存创建单据
                StockTransApplication sta = staDao.findStaBySlipCodeNotCancel(queueSta.getOrdercode());
                if (sta == null) {
                    // 执行校验库存,创建作业单
                    if (log.isDebugEnabled()) {
                        log.debug("sta is not exist, check sales inventory and cache result, qstaId is:{}", queueSta.getId());
                    }
                    CheckInventoryResaultCommand map = checkSalesInventoryAndCreate(queueSta.getId(), invMap);
                    maps.add(map);
                } else {
                    // 标识单据异常不处理，修改error count + 100
                    if (log.isDebugEnabled()) {
                        log.debug("sta is exist, add error count 100 for qsta,qstaId:{}", queueSta.getId());
                    }
                    queueStaManagerExecute.addErrorCountForQsta(queueSta.getId(), 100);
                }
                if (log.isInfoEnabled()) {
                    log.info("create sta finish, sta id : {} ,ouId is:{}, batchCode is:{}", queueSta.getId(), ouid, batchCode);
                }
            }
            if (maps != null) {
                qstaManagerExecute.createsta(maps);
            }
        }
        if (log.isInfoEnabled()) {
            log.info("check inventory and create sta by batchCode end,ouId is:{}, batchCode is:{}", ouid, batchCode);
        }
    }

    /**
     * 计算库存是否足够,
     * 
     * @param qstaId
     * @param inv
     * @param isShareWh
     */
    private CheckInventoryResaultCommand checkSalesInventoryAndCreate(Long qstaId, Map<String, Map<String, Long>> inv) {
        if (log.isInfoEnabled()) {
            log.info("checkSalesInventoryAndCreate start, qstaId:{}", qstaId);
        }
        CheckInventoryResaultCommand rscmd = new CheckInventoryResaultCommand();
        // 查询某一单所有明细信息
        List<QueueStaLine> lines = queueStaLineDao.findQueueLineDetial(qstaId, new BeanPropertyRowMapperExt<QueueStaLine>(QueueStaLine.class));
        // 库存不足反馈信息
        QueueSta queueSta = queueStaDao.getByPrimaryKey(qstaId);
        boolean isEnoughtQty = true;
        // 校验库存是否足够

        List<InvQstaLineCommand> errorLins = new ArrayList<InvQstaLineCommand>();
        Map<Long, List<InvQstaLineCommand>> map = new HashMap<Long, List<InvQstaLineCommand>>();
        for (QueueStaLine l : lines) {
            List<InvQstaLineCommand> invQstaLines = new ArrayList<InvQstaLineCommand>();
            String[] channelList = queueSta.getChannelList().split(";");
            String key = l.getSkucode();
            Map<String, Long> invmap = inv.get(key);
            Long lqty = l.getQty();
            // 按渠道依次查询
            if (invmap != null) {
                if (l.getOwner() != null) {
                    Long iqty = invmap.get(l.getOwner());
                    iqty = iqty == null ? 0 : iqty;
                    if (iqty - lqty >= 0) {
                        // 库存足够
                        InvQstaLineCommand invQsta = new InvQstaLineCommand();
                        invQsta.setId(l.getId());
                        invQsta.setQty(lqty);
                        invQsta.setOwner(l.getOwner());
                        invQsta.setSkuCode(l.getSkucode());
                        invQsta.setType(l.getLineType());
                        invQsta.setStatus(QueueStaLineStatus.LINE_STATUS_TURE);
                        invQstaLines.add(invQsta);
                        invmap.put(l.getOwner(), iqty - lqty);
                        inv.put(key, invmap);
                        // 计算剩余执行量
                        lqty = lqty - lqty;
                    }
                } else {
                    for (int i = 0; i <= channelList.length - 1; i++) {
                        Long iqty = invmap.get(channelList[i]) == null ? 0 : invmap.get(channelList[i]);
                        if (iqty - lqty >= 0) {
                            // 库存足够
                            InvQstaLineCommand invQsta = new InvQstaLineCommand();
                            invQsta.setId(l.getId());
                            invQsta.setQty(lqty);
                            invQsta.setOwner(channelList[i]);
                            invQsta.setSkuCode(l.getSkucode());
                            invQsta.setType(l.getLineType());
                            invQsta.setStatus(QueueStaLineStatus.LINE_STATUS_TURE);
                            invQstaLines.add(invQsta);
                            invmap.put(channelList[i], iqty - lqty);
                            inv.put(key, invmap);
                            // 计算剩余执行量
                            lqty = lqty - lqty;
                            break;
                        } else {
                            if (iqty > 0) {
                                // 当前店铺库存不足
                                InvQstaLineCommand invQsta = new InvQstaLineCommand();
                                invQsta.setId(l.getId());
                                invQsta.setQty(iqty);
                                invQsta.setOwner(channelList[i]);
                                invQsta.setSkuCode(l.getSkucode());
                                invQsta.setType(l.getLineType());
                                invQsta.setStatus(QueueStaLineStatus.LINE_STATUS_TURE);
                                invQstaLines.add(invQsta);
                                // 计算剩余执行量
                                lqty = lqty - iqty;
                                invmap.put(channelList[i], 0L);
                                inv.put(key, invmap);
                            }
                        }
                    }
                }
                // 判断行库存是否足够
                if (lqty > 0 && QueueStaLineType.LINE_TYPE_FALSE.equals(l.getLineType())) {
                    // 足够,加回扣减数量
                    for (InvQstaLineCommand invQstaLine : invQstaLines) {
                        invQstaLine.setStatus(QueueStaLineStatus.LINE_STATUS_FALSE);
                        invmap.put(invQstaLine.getOwner(), invmap.get(invQstaLine.getOwner()) + invQstaLine.getQty());
                        inv.put(invQstaLine.getSkuCode(), invmap);
                    }

                } else if (lqty > 0) {
                    // 库存不足非主卖品逻辑,加回扣减数量
                    for (InvQstaLineCommand invQstaLine : invQstaLines) {
                        invmap.put(invQstaLine.getOwner(), invmap.get(invQstaLine.getOwner()) + invQstaLine.getQty());
                        inv.put(invQstaLine.getSkuCode(), invmap);
                    }
                    InvQstaLineCommand invQsta = new InvQstaLineCommand();
                    invQsta.setId(l.getId());
                    invQsta.setQty(lqty);
                    invQsta.setSkuCode(l.getSkucode());
                    errorLins.add(invQsta);
                    isEnoughtQty = false;
                } else {
                    l.setLineStatus(QueueStaLineStatus.LINE_STATUS_TURE);
                }
                // ==========足够库存不做处理==================
                map.put(l.getId(), invQstaLines);
            } else if (QueueStaLineType.LINE_TYPE_FALSE.equals(l.getLineType())) {
                // 非主卖品逻辑
                InvQstaLineCommand invQsta = new InvQstaLineCommand();
                invQsta.setId(l.getId());
                invQsta.setQty(lqty);
                invQsta.setSkuCode(l.getSkucode());
                invQsta.setStatus(QueueStaLineStatus.LINE_STATUS_FALSE);
                invQstaLines.add(invQsta);
                map.put(l.getId(), invQstaLines);
            } else {
                InvQstaLineCommand invQsta = new InvQstaLineCommand();
                invQsta.setId(l.getId());
                invQsta.setQty(lqty);
                invQsta.setSkuCode(l.getSkucode());
                errorLins.add(invQsta);
                isEnoughtQty = false;
            }
        }
        if (isEnoughtQty) {
            if (log.isDebugEnabled()) {
                log.debug("check inventory result is enought, qsta order code is:{}", queueSta.getOrdercode());
            }
            rscmd.setQstaId(qstaId);
            rscmd.setResaultStatus(true);
            rscmd.setInvDetial(map);
            if (log.isInfoEnabled()) {
                log.info("checkSalesInventoryAndCreate end, qstaId:{}", qstaId);
            }
            return rscmd;
        } else {
            if (log.isDebugEnabled()) {
                log.debug("check inventory result is not enought, qsta order code is:{}", queueSta.getOrdercode());
            }
            rscmd.setQstaId(qstaId);
            rscmd.setResaultStatus(false);
            rscmd.setErrorDetial(errorLins);
            if (log.isInfoEnabled()) {
                log.info("checkSalesInventoryAndCreate end, qstaId:{}", qstaId);
            }
            return rscmd;
        }
    }

    public void existBatchcode(Long whouid) {
        List<String> barchCodes = queueStaDao.findBatchCodeByOuid(whouid, new SingleColumnRowMapper<String>(String.class));
        for (String barchCode : barchCodes) {
            createStabyBatchCode(whouid, barchCode);
        }
    }

    public void createPickingListToWCS(Long msgId) {
        MsgToWcsThread wcs = new MsgToWcsThread();
        wcs.setMsgId(msgId);
        wcs.setType(WcsInterfaceType.SBoZhong.getValue());
        Thread thread = new Thread(wcs);
        thread.start();
    }

    public void createPickingListToWCS(List<Long> msgId) {
        MsgToWcsThread wcs = new MsgToWcsThread();
        wcs.setMsgList(msgId);
        wcs.setType(WcsInterfaceType.SBoZhong.getValue());
        Thread thread = new Thread(wcs);
        thread.start();
    }
}
