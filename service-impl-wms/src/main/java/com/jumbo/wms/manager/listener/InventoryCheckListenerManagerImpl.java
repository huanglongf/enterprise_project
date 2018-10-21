package com.jumbo.wms.manager.listener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.dao.warehouse.InventoryCheckDao;
import com.jumbo.dao.warehouse.StockTransTxLogDao;
import com.jumbo.dao.warehouse.WmsInvChangeToOmsDao;
import com.jumbo.pac.manager.extsys.wms.rmi.Rmi4Wms;
import com.jumbo.pac.manager.extsys.wms.rmi.model.BaseResult;
import com.jumbo.pac.manager.extsys.wms.rmi.model.OperationBill;
import com.jumbo.pac.manager.extsys.wms.rmi.model.OperationBillLine;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.model.DefaultStatus;
import com.jumbo.wms.model.warehouse.InventoryCheck;
import com.jumbo.wms.model.warehouse.InventoryCheckCommand;
import com.jumbo.wms.model.warehouse.InventoryCheckType;
import com.jumbo.wms.model.warehouse.StockTransApplicationStatus;
import com.jumbo.wms.model.warehouse.StockTransTxLogCommand;
import com.jumbo.wms.model.warehouse.WmsInvChangeToOms;

/**
 * 库存盘点批状态监听处理方法
 * 
 * @author jinlong.ke
 * 
 */
@Service("inventoryCheckListenerManager")
public class InventoryCheckListenerManagerImpl extends BaseManagerImpl implements InventoryCheckListenerManager {

    /**
     * 
     */
    private static final long serialVersionUID = 5281758611134211027L;
    @Autowired
    private InventoryCheckDao inventoryCheckDao;
    @Autowired
    private StockTransTxLogDao stockTransTxLogDao;
    @Autowired
    private Rmi4Wms rmi4Wms;
    @Autowired
    private WmsInvChangeToOmsDao wmsInvChangeToOmsDao;

    @Override
    @Transactional(readOnly = true)
    public void inventoryCheckFinished(InventoryCheck ic) {
        // 取本次反馈日志最大的执行时间
        List<StockTransTxLogCommand> list = stockTransTxLogDao.getDetailByInventoryCheckId(ic.getId(), true, new BeanPropertyRowMapper<StockTransTxLogCommand>(StockTransTxLogCommand.class));
        boolean b = true;
        if (list.size() == 0) {
            List<StockTransTxLogCommand> list1 = stockTransTxLogDao.getDetailByInventoryCheckId(ic.getId(), false, new BeanPropertyRowMapper<StockTransTxLogCommand>(StockTransTxLogCommand.class));
            if (list1.size() > 0) {
                b = false;
            }
        }
        if (b) {
            InventoryCheck ic1 = inventoryCheckDao.getByPrimaryKey(ic.getId());
            inventoryCheckDao.flush();
            InventoryCheckCommand inv = inventoryCheckDao.findIMaxTransactionTime(ic1.getId(), new BeanPropertyRowMapper<InventoryCheckCommand>(InventoryCheckCommand.class));
            OperationBill ob = new OperationBill();
            ob.setCode(ic1.getCode());
            ob.setMaxTransactionTime(inv.getManagerTime());
            ob.setDirection(OperationBill.IN_AND_OUT);
            ob.setSlipCode(ic1.getSlipCode());
            if (ic1.getType().equals(InventoryCheckType.VMI)) {
                ob.setType(222);
            } else {
                ob.setType(ic1.getType().getValue() + 10000);
            }
            ob.setMemo(ic1.getRemork());
            // ob.setWhOuId(ic1.getOu().getId());
            ob.setWhCode(ic1.getOu().getCode());

            List<OperationBillLine> outLine = new ArrayList<OperationBillLine>();
            List<OperationBillLine> inLine = new ArrayList<OperationBillLine>();
            for (StockTransTxLogCommand sl : list) {
                OperationBillLine bl = new OperationBillLine();
                bl.setSkuCode(sl.getSkuCode());
                // 新增是否可销售
                bl.setMarketability(sl.getMarketAbility() == 1 ? true : false);
                bl.setInvBatchCode(sl.getBatchCode());
                // bl.setInvStatusId(sl.getInvStatusId());
                bl.setInvStatusCode(sl.getInvStatus());
                bl.setQty(sl.getQuantity());
                // bl.setShopOuId(sl.getShopOuId());
                bl.setShopCode(sl.getOwner());
                // bl.setWhOuId(sl.getWarehouseOuId());
                bl.setWhCode(sl.getWhouCode());
                if (sl.getIntDirection() == 1) {
                    inLine.add(bl);
                } else {
                    outLine.add(bl);
                }
            }
            ob.setInboundLines(inLine);
            ob.setOutboundLines(outLine);
            try {
                log.debug("Call oms inventory confirm interface------------------->BEGIN");
                BaseResult br = rmi4Wms.wmsOperationsFeedback(ob);
                if (br.getStatus() == 0) {
                    throw new BusinessException(ErrorCode.OMS_SYSTEM_ERROR, br.getMsg());
                }
                log.debug("Call oms inventory confirm interface------------------->END");
            } catch (BusinessException e) {
                throw e;
            } catch (Exception e) {
                log.debug("");
                throw new BusinessException(ErrorCode.RMI_OMS_FAILURE, e.getClass().getName());
            }
        }
    }

    @Override
    public void recordFinishDataToOms() {
        List<StockTransTxLogCommand> logList = stockTransTxLogDao.findStockTransTxLogList2(new BeanPropertyRowMapper<StockTransTxLogCommand>(StockTransTxLogCommand.class));
        if (logList.size() > 0) {
            for (StockTransTxLogCommand sc : logList) {
                WmsInvChangeToOms wio = new WmsInvChangeToOms();
                wio.setStaId(sc.getId());
                wio.setStaCode(sc.getStaCode());
                wio.setCreateTime(new Date());
                wio.setDataStatus(StockTransApplicationStatus.FINISHED);
                wio.setErrorCount(0);
                wio.setExeStatus(DefaultStatus.CREATED);
                wio.setPriority(3);// 此处默认为3
                wio.setOrderType(0);
                wmsInvChangeToOmsDao.save(wio);
                stockTransTxLogDao.updateOcpById(sc.getId());
            }
        }
    }
}
