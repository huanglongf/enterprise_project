package com.jumbo.wms.manager.warehouse;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;

import loxia.dao.support.BeanPropertyRowMapperExt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.jumbo.dao.warehouse.QueueStaDao;
import com.jumbo.dao.warehouse.QueueStaDeliveryInfoDao;
import com.jumbo.dao.warehouse.QueueStaInvoiceDao;
import com.jumbo.dao.warehouse.QueueStaInvoiceLineDao;
import com.jumbo.dao.warehouse.QueueStaLineDao;
import com.jumbo.pac.manager.extsys.wms.rmi.model.SoLineResponse;
import com.jumbo.pac.manager.extsys.wms.rmi.model.StaCreatedResponse;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.model.command.CheckInventoryResaultCommand;
import com.jumbo.wms.model.warehouse.InvQstaLineCommand;
import com.jumbo.wms.model.warehouse.QueueSta;
import com.jumbo.wms.model.warehouse.QueueStaDeliveryInfo;
import com.jumbo.wms.model.warehouse.QueueStaInvoice;
import com.jumbo.wms.model.warehouse.QueueStaInvoiceLine;
import com.jumbo.wms.model.warehouse.QueueStaLine;
import com.jumbo.wms.model.warehouse.QueueStaLineStatus;
import com.jumbo.wms.model.warehouse.QueueStaLineType;

@Service("qstaManagerExecute")
public class QstaManagerExecuteImpl implements QstaManagerExecute {
    /**
	 * 
	 */
    private static final long serialVersionUID = 4798278818887881018L;
    protected static final Logger log = LoggerFactory.getLogger(QstaManagerExecuteImpl.class);
    @Resource
    private ApplicationContext applicationContext;
    @Autowired
    private QueueStaDao queueStaDao;
    @Autowired
    private QueueStaLineDao queueStaLineDao;
    @Autowired
    private QueueStaDeliveryInfoDao queueStaDeliveryInfoDao;
    @Autowired
    private QueueStaInvoiceDao queueStaInvoiceDao;
    @Autowired
    private CreateStaManagerExecu createStaManagerExecu;
    @Autowired
    private QueueStaInvoiceLineDao queueStaInvoiceLineDao;

    @Override
    public void createsta(List<CheckInventoryResaultCommand> maps) {
        if (log.isInfoEnabled()) {
            log.info("createsta by checkInventoryResult start");
        }
        for (CheckInventoryResaultCommand cmd : maps) {
            if (log.isInfoEnabled()) {
                log.info("createsta by checkInventoryResult start, qstaId:{}", cmd.getQstaId());
            }
            if (cmd.isResaultStatus()) {
                QueueSta queueSta = queueStaDao.getByPrimaryKey(cmd.getQstaId());
                if (log.isDebugEnabled()) {
                    log.debug("qsta inventory is enought start create sta, qsta order code is:{}", queueSta.getOrdercode());
                }
                QueueStaDeliveryInfo deliveryInfo = queueStaDeliveryInfoDao.getByPrimaryKey(cmd.getQstaId());
                List<QueueStaLine> staLines = queueStaLineDao.queryStaId(cmd.getQstaId(), new BeanPropertyRowMapperExt<QueueStaLine>(QueueStaLine.class));
                List<QueueStaInvoice> invoices = queueStaInvoiceDao.findByQstaId(cmd.getQstaId(), new BeanPropertyRowMapperExt<QueueStaInvoice>(QueueStaInvoice.class));
                if (queueSta.getIsspecialpackaging()) {
                    List<QueueStaLine> l = new ArrayList<QueueStaLine>();
                    // 进入拆单
                    for (QueueStaLine line : staLines) {
                        List<InvQstaLineCommand> commands = cmd.getInvDetial().get(line.getId());
                        if (commands.size() > 1) {
                            // 获取行单价
                            BigDecimal unit_total = line.getTotalactual().divide(new BigDecimal(line.getQty()), BigDecimal.ROUND_HALF_UP);
                            // 获取行单价
                            BigDecimal unit_orderTotal = line.getOrdertotalactual().divide(new BigDecimal(line.getQty()), BigDecimal.ROUND_HALF_UP);
                            for (InvQstaLineCommand command : commands) {
                                QueueStaLine li = new QueueStaLine();
                                BigDecimal total = unit_total.multiply(new BigDecimal(command.getQty()));
                                BigDecimal orderTotal = unit_orderTotal.multiply(new BigDecimal(command.getQty()));
                                li.setTotalactual(total);
                                li.setOrdertotalactual(orderTotal);
                                li.setOrdertotalbfdiscount(line.getOrdertotalbfdiscount());
                                li.setQueueSta(queueSta);
                                li.setId(li.getId());
                                li.setSkucode(line.getSkucode());
                                li.setSkuName(line.getSkuName());
                                li.setOwner(command.getOwner());
                                li.setQty(command.getQty());
                                li.setListprice(line.getListprice());
                                li.setUnitprice(line.getUnitprice());
                                li.setDirection(line.getDirection());
                                li.setInvstatusid(line.getInvstatusid());
                                li.setActivitysource(line.getActivitysource());
                                li.setLineType(line.getLineType());
                                li.setLineStatus(command.getStatus());
                                l.add(li);
                            }
                        } else if (commands.size() == 1) {
                            for (InvQstaLineCommand command : commands) {
                                QueueStaLine li = new QueueStaLine();
                                li.setTotalactual(line.getTotalactual());
                                li.setOrdertotalactual(line.getOrdertotalactual());
                                li.setOrdertotalbfdiscount(line.getOrdertotalbfdiscount());
                                li.setQueueSta(queueSta);
                                li.setId(line.getId());
                                li.setSkucode(line.getSkucode());
                                li.setSkuName(line.getSkuName());
                                li.setOwner(command.getOwner());
                                li.setQty(command.getQty());
                                li.setListprice(line.getListprice());
                                li.setUnitprice(line.getUnitprice());
                                li.setDirection(line.getDirection());
                                li.setInvstatusid(line.getInvstatusid());
                                li.setActivitysource(line.getActivitysource());
                                li.setLineType(line.getLineType());
                                li.setLineStatus(command.getStatus());
                                l.add(li);
                            }
                        } else {
                            if (line.getLineType().equals(QueueStaLineType.LINE_TYPE_TURE)) {
                                throw new BusinessException(ErrorCode.SYSTEM_ERROR);
                            } else {
                                line.setLineStatus(QueueStaLineStatus.LINE_STATUS_FALSE);
                                l.add(line);
                            }
                        }
                    }
                    try {
                        if (l.size() > 0) {
                            // 创单
                            createStaManagerExecu.createSta(queueSta, deliveryInfo, l, invoices);
                        } else {
                            // 创单
                            createStaManagerExecu.createSta(queueSta, deliveryInfo, staLines, invoices);
                        }
                    } catch (Exception e) {
                        log.error("qstaId: {}", cmd.getQstaId(), e);
                        if (e instanceof BusinessException) {
                            BusinessException be = (BusinessException) e;
                            if (be.getErrorCode() == ErrorCode.OMS_ORDER_CANACEL) {
                                // ===========反馈成功,判断OMS是否需要修改仓库================
                                // 删除中间表信息
                                if (log.isDebugEnabled()) {
                                    log.debug("qstaId:{}, pac order cancel, delete queue and log queue", cmd.getQstaId());
                                }
                                QueueSta qsta = queueStaDao.getByPrimaryKey(cmd.getQstaId());
                                createStaManagerExecu.createLogSta(qsta.getId(), null);
                                for (QueueStaInvoice invoice : invoices) {
                                    List<QueueStaInvoiceLine> queueStaInvoiceLines = queueStaInvoiceLineDao.findByInvoiceId(invoice.getId(), new BeanPropertyRowMapperExt<QueueStaInvoiceLine>(QueueStaInvoiceLine.class));
                                    for (QueueStaInvoiceLine queueStaInvoiceLine : queueStaInvoiceLines) {
                                        queueStaInvoiceLineDao.deleteByPrimaryKey(queueStaInvoiceLine.getId());
                                    }
                                    queueStaInvoiceDao.deleteByPrimaryKey(invoice.getId());
                                }

                                List<QueueStaLine> ql = queueStaLineDao.findByStaId(qsta.getId());
                                for (QueueStaLine line : ql) {
                                    queueStaLineDao.delete(line);
                                }
                                queueStaDao.delete(qsta);
                                if (qsta.getQueueStaDeliveryInfo() != null) {
                                    queueStaDeliveryInfoDao.delete(qsta.getQueueStaDeliveryInfo());
                                }
                            } else {
                                // 增加错误次数
                                if (log.isDebugEnabled()) {
                                    log.debug("qstaId:{}, create sta throw exception, add error count 1 for qsta", cmd.getQstaId());
                                }
                                addErrorCountForQsta(cmd.getQstaId(), 1);
                            }
                        } else {
                            // 增加错误次数
                            if (log.isDebugEnabled()) {
                                log.debug("qstaId:{}, create sta throw exception, add error count 1 for qsta", cmd.getQstaId());
                            }
                            addErrorCountForQsta(cmd.getQstaId(), 1);
                        }
                        if (log.isDebugEnabled()) {
                            log.debug("qstaId:{}, create sta throw exception, add error count 1 for qsta", cmd.getQstaId());
                        }
                        addErrorCountForQsta(cmd.getQstaId(), 1);
                    }
                } else {
                    List<QueueStaLine> l = new ArrayList<QueueStaLine>();

                    // 进入拆单
                    for (QueueStaLine line : staLines) {
                        List<InvQstaLineCommand> commands = cmd.getInvDetial().get(line.getId());
                        if (commands.size() > 1) {
                            // 获取行单价
                            BigDecimal unit_total = line.getTotalactual().divide(new BigDecimal(line.getQty()), BigDecimal.ROUND_HALF_UP);
                            // 获取行单价
                            BigDecimal unit_orderTotal = line.getOrdertotalactual().divide(new BigDecimal(line.getQty()), BigDecimal.ROUND_HALF_UP);
                            for (InvQstaLineCommand command : commands) {
                                QueueStaLine li = new QueueStaLine();
                                BigDecimal total = unit_total.multiply(new BigDecimal(command.getQty()));
                                BigDecimal orderTotal = unit_orderTotal.multiply(new BigDecimal(command.getQty()));
                                li.setTotalactual(total);
                                li.setOrdertotalactual(orderTotal);
                                li.setOrdertotalbfdiscount(line.getOrdertotalbfdiscount());
                                li.setQueueSta(queueSta);
                                li.setSkucode(line.getSkucode());
                                li.setOwner(command.getOwner());
                                li.setId(line.getId());
                                li.setSkuName(line.getSkuName());
                                li.setQty(command.getQty());
                                li.setListprice(line.getListprice());
                                li.setUnitprice(line.getUnitprice());
                                li.setDirection(line.getDirection());
                                li.setInvstatusid(line.getInvstatusid());
                                li.setActivitysource(line.getActivitysource());
                                li.setLineType(line.getLineType());
                                li.setLineStatus(command.getStatus());
                                l.add(li);
                            }
                        } else if (commands.size() == 1) {
                            for (InvQstaLineCommand command : commands) {
                                QueueStaLine li = new QueueStaLine();
                                li.setTotalactual(line.getTotalactual());
                                li.setOrdertotalactual(line.getOrdertotalactual());
                                li.setOrdertotalbfdiscount(line.getOrdertotalbfdiscount());
                                li.setQueueSta(queueSta);
                                li.setSkucode(line.getSkucode());
                                li.setOwner(command.getOwner());
                                li.setSkuName(line.getSkuName());
                                li.setId(line.getId());
                                li.setQty(command.getQty());
                                li.setListprice(line.getListprice());
                                li.setUnitprice(line.getUnitprice());
                                li.setDirection(line.getDirection());
                                li.setInvstatusid(line.getInvstatusid());
                                li.setActivitysource(line.getActivitysource());
                                li.setLineType(line.getLineType());
                                li.setLineStatus(command.getStatus());
                                l.add(li);
                            }
                        } else {
                            line.setLineStatus(QueueStaLineStatus.LINE_STATUS_FALSE);
                            l.add(line);
                        }
                    }
                    try {
                        if (l.size() > 0) {
                            // 创单
                            createStaManagerExecu.createSta(queueSta, deliveryInfo, l, invoices);
                        } else {
                            // 创单
                            createStaManagerExecu.createSta(queueSta, deliveryInfo, staLines, invoices);
                        }
                    } catch (Exception e) {
                        if (e instanceof BusinessException) {
                            BusinessException be = (BusinessException) e;
                            if (be.getErrorCode() == ErrorCode.OMS_ORDER_CANACEL) {
                                // ===========反馈成功,判断OMS是否需要修改仓库================
                                // 删除中间表信息
                                if (log.isDebugEnabled()) {
                                    log.debug("qstaId:{}, pac order cancel, delete queue and log queue", cmd.getQstaId());
                                }
                                QueueSta qsta = queueStaDao.getByPrimaryKey(cmd.getQstaId());
                                createStaManagerExecu.createLogSta(qsta.getId(), null);
                                List<QueueStaInvoice> invoices1 = queueStaInvoiceDao.findByQstaId(qsta.getId(), new BeanPropertyRowMapperExt<QueueStaInvoice>(QueueStaInvoice.class));
                                for (QueueStaInvoice invoice : invoices1) {
                                    List<QueueStaInvoiceLine> queueStaInvoiceLines = queueStaInvoiceLineDao.findByInvoiceId(invoice.getId(), new BeanPropertyRowMapperExt<QueueStaInvoiceLine>(QueueStaInvoiceLine.class));
                                    for (QueueStaInvoiceLine queueStaInvoiceLine : queueStaInvoiceLines) {
                                        queueStaInvoiceLineDao.deleteByPrimaryKey(queueStaInvoiceLine.getId());
                                    }
                                    queueStaInvoiceDao.deleteByPrimaryKey(invoice.getId());
                                }

                                List<QueueStaLine> ql = queueStaLineDao.findByStaId(qsta.getId());
                                for (QueueStaLine line : ql) {
                                    queueStaLineDao.delete(line);
                                }
                                queueStaDao.delete(qsta);
                                if (qsta.getQueueStaDeliveryInfo() != null) {
                                    queueStaDeliveryInfoDao.delete(qsta.getQueueStaDeliveryInfo());
                                }
                            } else {
                                // 增加错误次数
                                if (log.isDebugEnabled()) {
                                    log.debug("qstaId:{}, create sta throw exception, add error count 1 for qsta", cmd.getQstaId());
                                }
                                addErrorCountForQsta(cmd.getQstaId(), 1);
                            }
                        } else {
                            // 增加错误次数
                            if (log.isDebugEnabled()) {
                                log.debug("qstaId:{}, create sta throw exception, add error count 1 for qsta", cmd.getQstaId());
                            }
                            addErrorCountForQsta(cmd.getQstaId(), 1);
                        }
                    }
                }

            } else {
                List<SoLineResponse> lineResponses = new ArrayList<SoLineResponse>();
                List<InvQstaLineCommand> commands = cmd.getErrorDetial();
                for (InvQstaLineCommand cm : commands) {
                    SoLineResponse detial = new SoLineResponse();
                    detial.setJmSkuCode(cm.getSkuCode());
                    detial.setQty(cm.getQty());
                    lineResponses.add(detial);
                }
                try {
                    // 反馈OMS库存不足,通知OMS
                    StaCreatedResponse rs = new StaCreatedResponse();
                    QueueSta qsta = queueStaDao.getByPrimaryKey(cmd.getQstaId());
                    if (log.isDebugEnabled()) {
                        log.debug("qsta inventory is not enought send result to pac, qstaId is:{}", cmd.getQstaId());
                    }
                    // 设置反馈OMS类型
                    rs.setType(StaCreatedResponse.BASE_RESULT_TYPE_SO);
                    // 设置类型库存不足
                    rs.setStatus(StaCreatedResponse.BASE_RESULT_STATUS_INV_SHORTAGE);
                    rs.setSlipCode(qsta.getOrdercode());
                    rs.setSoLineResponses(lineResponses);
                    String errorMsg = applicationContext.getMessage((ErrorCode.BUSINESS_EXCEPTION + ErrorCode.EI_SALES_INV_NOT_ENOUGH), new Object[] {}, Locale.SIMPLIFIED_CHINESE);
                    rs.setMsg(errorMsg.toString());
                    // 通知OMS
                    createStaManagerExecu.sendCreateResultToOms(rs, qsta.getId());
                } catch (Exception e) {
                    log.error("qstaId:" + cmd.getQstaId(), e);
                    if (e instanceof BusinessException) {
                        BusinessException be = (BusinessException) e;
                        if (be.getErrorCode() == ErrorCode.OMS_ORDER_CANACEL) {
                            // ===========反馈成功,判断OMS是否需要修改仓库================
                            // 删除中间表信息
                            QueueSta qsta = queueStaDao.getByPrimaryKey(cmd.getQstaId());
                            createStaManagerExecu.createLogSta(qsta.getId(), null);
                            List<QueueStaInvoice> invoices = queueStaInvoiceDao.findByQstaId(qsta.getId(), new BeanPropertyRowMapperExt<QueueStaInvoice>(QueueStaInvoice.class));
                            for (QueueStaInvoice invoice : invoices) {
                                List<QueueStaInvoiceLine> queueStaInvoiceLines = queueStaInvoiceLineDao.findByInvoiceId(invoice.getId(), new BeanPropertyRowMapperExt<QueueStaInvoiceLine>(QueueStaInvoiceLine.class));
                                for (QueueStaInvoiceLine queueStaInvoiceLine : queueStaInvoiceLines) {
                                    queueStaInvoiceLineDao.deleteByPrimaryKey(queueStaInvoiceLine.getId());
                                }
                                queueStaInvoiceDao.deleteByPrimaryKey(invoice.getId());
                            }

                            List<QueueStaLine> ql = queueStaLineDao.findByStaId(qsta.getId());
                            for (QueueStaLine line : ql) {
                                queueStaLineDao.delete(line);
                            }
                            queueStaDao.delete(qsta);
                            if (qsta.getQueueStaDeliveryInfo() != null) {
                                queueStaDeliveryInfoDao.delete(qsta.getQueueStaDeliveryInfo());
                            }
                        } else {
                            // 增加错误次数
                            if (log.isDebugEnabled()) {
                                log.debug("qstaId:{}, create sta throw exception, add error count 1 for qsta", cmd.getQstaId());
                            }
                            addErrorCountForQsta(cmd.getQstaId(), 1);
                        }
                    } else {
                        // 增加错误次数
                        if (log.isDebugEnabled()) {
                            log.debug("qstaId:{}, create sta throw exception, add error count 1 for qsta", cmd.getQstaId());
                        }
                        addErrorCountForQsta(cmd.getQstaId(), 1);
                    }

                }
            }
            if (log.isInfoEnabled()) {
                log.info("createsta by checkInventoryResult end, qstaId:{}", cmd.getQstaId());
            }
        }
        if (log.isInfoEnabled()) {
            log.info("createsta by checkInventoryResult end");
        }
    }

    /**
     * 增加队列错误次数
     * 
     * @param qStaId
     * @param addCount
     */
    public void addErrorCountForQsta(Long qStaId, int addCount) {
        if (log.isInfoEnabled()) {
            log.info("addErrorCountForQsta start, qstaId:{}", qStaId);
        }
        QueueSta queueSta = queueStaDao.getByPrimaryKey(qStaId);
        queueSta.setErrorcount(queueSta.getErrorcount() + addCount);
        queueStaDao.save(queueSta);
        if (log.isInfoEnabled()) {
            log.info("addErrorCountForQsta end, qstaId:{}", qStaId);
        }
    }

}
