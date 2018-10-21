package com.jumbo.wms.manager.task.boc;

import java.util.ArrayList;
import java.util.List;

import loxia.dao.support.BeanPropertyRowMapperExt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.transaction.UnexpectedRollbackException;

import com.jumbo.dao.vmi.warehouse.MsgRtnInboundOrderDao;
import com.jumbo.dao.vmi.warehouse.MsgRtnOutboundDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.wms.Constants;
import com.jumbo.wms.daemon.BocTask;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.af.AFManager;
import com.jumbo.wms.manager.boc.MasterDataManager;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.manager.warehouse.WareHouseManagerProxy;
import com.jumbo.wms.model.DefaultStatus;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnInboundOrder;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnOutbound;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationStatus;

/**
 * Boc Task
 * 
 * @author wudan
 * 
 */
public class BocTaskImpl extends BaseManagerImpl implements BocTask {


    /**
     * 
     */
    private static final long serialVersionUID = -211784178437571319L;
    protected static final Logger log = LoggerFactory.getLogger(BocTaskImpl.class);
    @Autowired
    private MasterDataManager masterDataManager;

    @Autowired
    private MsgRtnOutboundDao msgRtnOutbounddao;

    @Autowired
    private StockTransApplicationDao staDao;

    @Autowired
    private WareHouseManagerProxy wareHouseManagerProxy;

    @Autowired
    private WareHouseManager wareHouseManager;

    @Autowired
    private MsgRtnInboundOrderDao msgRtnInboundOrderDao;

    @Autowired
    private AFManager aFManager;



    /**
     * 15分钟
     */
    public void bocfollieOrderTask() {
        masterDataManager.mqSendSalesOrder(Constants.VIM_WH_SOURCE_FOLLIE);
        masterDataManager.mqSendSalesOrder(Constants.VIM_WH_SOURCE_ARVATO_WMF);
        masterDataManager.mqSendSalesOrder(Constants.VIM_WH_SOURCE_NATURE_CARE);
        masterDataManager.mqSendSalesOrder(Constants.VIM_WH_SOURCE_NOVO);
    }

    /**
     * 15分钟
     */
    public void bocfollieReturnTask() {
        masterDataManager.receiveMsgInboundOrder(Constants.VIM_WH_SOURCE_FOLLIE);
        masterDataManager.receiveMsgInboundOrder(Constants.VIM_WH_SOURCE_ARVATO_WMF);
        masterDataManager.receiveMsgInboundOrder(Constants.VIM_WH_SOURCE_NOVO);
        masterDataManager.mqSendSalesOrder(Constants.VIM_WH_SOURCE_NATURE_CARE);
    }

    /**
     * 每天晚上2.30
     */
    public void updateBocPriceTask() {
        masterDataManager.updateBocPrice();

    }

    /**
     * 取消
     */
    public void sendBocCancelOrder() {
        masterDataManager.bocExecuteCreateCancelOrder(Constants.VIM_WH_SOURCE_ARVATO_WMF);
        masterDataManager.bocExecuteCreateCancelOrder(Constants.VIM_WH_SOURCE_FOLLIE);
        masterDataManager.bocExecuteCreateCancelOrder(Constants.VIM_WH_SOURCE_NOVO);
    }

    /**
     * 销售报表接口
     */
    public void sendsaleOrder() {
        // masterDataManager.receiveSalesData(Constants.VIM_WH_SOURCE_ARVATO_WMF);
    }

    /**
     * aeo销售报表接口
     */
    public void sendsaleaeoOrder() {
        // masterDataManager.receiveaeoSalesData("aeo");
    }


    public void executeMsgRtnOutbound() {
        List<String> sourceList = new ArrayList<String>();
        sourceList.add(Constants.VIM_WH_SOURCE_FOLLIE);
        sourceList.add(Constants.VIM_WH_SOURCE_ARVATO_WMF);
        sourceList.add(Constants.VIM_WH_SOURCE_NATURE_CARE);
        sourceList.add(Constants.VIM_WH_SOURCE_NOVO);
        sourceList.add(Constants.VIM_WH_SOURCE_GUESS);
        sourceList.add(Constants.VIM_WH_SOURCE_GUESS_RETAIL);
        sourceList.add(Constants.VIM_WH_SOURCE_NEWLOOK_IDS);
        sourceList.add(Constants.VIM_WH_SOURCE_NBAUA_IDS);
        sourceList.add(Constants.VIM_WH_SOURCE_NEWLOOKJD_IDS);
        sourceList.add(Constants.VIM_WH_SOURCE_IDS);
        sourceList.add(Constants.VIM_WH_SOURCE_IDS_VS);
        for (String source : sourceList) {
            List<MsgRtnOutbound> msgoutList = msgRtnOutbounddao.findAllVmiMsgOutbound(source, new BeanPropertyRowMapper<MsgRtnOutbound>(MsgRtnOutbound.class));
            try {
                executeMsgRtnOutbound(msgoutList);
            } catch (Exception e) {
                log.error("", e);
            }
        }
    }

    public void executeRtnInboundOrderTask() {
        List<String> sourceList = new ArrayList<String>();
        sourceList.add(Constants.VIM_WH_SOURCE_FOLLIE);
        sourceList.add(Constants.VIM_WH_SOURCE_ARVATO_WMF);
        sourceList.add(Constants.VIM_WH_SOURCE_AEO_IDS);
        sourceList.add(Constants.VIM_WH_SOURCE_AF_IDS);
        sourceList.add(Constants.VIM_WH_SOURCE_NATURE_CARE);
        sourceList.add(Constants.VIM_WH_SOURCE_NOVO);
        sourceList.add(Constants.VIM_WH_SOURCE_UA_IDS);
        sourceList.add(Constants.VIM_WH_SOURCE_GUESS);
        sourceList.add(Constants.VIM_WH_SOURCE_GUESS_RETAIL);
        sourceList.add(Constants.VIM_WH_SOURCE_NEWLOOK_IDS);
        sourceList.add(Constants.VIM_WH_SOURCE_NBAUA_IDS);
        sourceList.add(Constants.VIM_WH_SOURCE_GODIVA_HAVI);
        sourceList.add(Constants.VIM_WH_SOURCE_NEWLOOKJD_IDS);
        sourceList.add(Constants.VIM_WH_SOURCE_IDS);
        sourceList.add(Constants.VIM_WH_SOURCE_IDS_VS);
        for (String source : sourceList) {
            log.debug("===========================task msg inbound " + source + "===================== ");
            List<MsgRtnInboundOrder> msgOrderNewInfo = msgRtnInboundOrderDao.findInboundByStatus(source, new BeanPropertyRowMapperExt<MsgRtnInboundOrder>(MsgRtnInboundOrder.class));
            if (msgOrderNewInfo.size() > 0) {
                log.debug("===========================msgOrderNewInfo size : {}  ===================== ", msgOrderNewInfo.size());
                for (MsgRtnInboundOrder msgNewInorder : msgOrderNewInfo) {
                    try {
                        wareHouseManagerProxy.msgInBoundWareHouse(msgNewInorder);
                    } catch (BusinessException be) {
                        log.warn("executeRtnInboundOrderTask error rtnInbound,error code is : {}" + msgNewInorder.getStaCode(), be.getErrorCode());
                    } catch (UnexpectedRollbackException e) {
                        log.error("Transaction rolled back because it has been marked as rollback-only" + msgNewInorder.getStaCode());
                        log.debug("Transaction rolled back because it has been marked as rollback-only" + msgNewInorder.getStaCode(), e);
                    } catch (Exception e) {
                        log.error("" + msgNewInorder.getStaCode(), e);
                    }
                }
            }
        }
    }

    public void executeMsgRtnOutbound(List<MsgRtnOutbound> msgoutList) {
        for (MsgRtnOutbound msgRtnbound : msgoutList) {
            StockTransApplication sta = staDao.findStaByCode(msgRtnbound.getStaCode());
            try {
                if (sta != null) {
                    if (sta.getStatus().equals(StockTransApplicationStatus.CREATED)) {
                        wareHouseManagerProxy.callVmiSalesStaOutBound(msgRtnbound.getId());
                    } else if (sta.getStatus().equals(StockTransApplicationStatus.FINISHED)) {
                        wareHouseManager.updateMsgRtnOutbound(msgRtnbound.getId(), DefaultStatus.FINISHED.getValue());
                    }
                } else {
                    wareHouseManager.updateMsgRtnOutbound(msgRtnbound.getId(), DefaultStatus.CANCELED.getValue());
                }

            } catch (BusinessException e) {
                wareHouseManager.updateMsgRtnOutBoundErrorCount(msgRtnbound.getId());
                log.error("executeMsgRtnOutbound error ! OUT STA :" +msgRtnbound.getStaCode()+"_" + e.getErrorCode());
            } catch (Exception ex) {
                wareHouseManager.updateMsgRtnOutbound(msgRtnbound.getId(), DefaultStatus.CANCELED.getValue());
                wareHouseManager.updateMsgRtnOutBoundErrorCount(msgRtnbound.getId());
                log.error(ex+ "executeMsgRtnOutbound error ! OUT STA" + msgRtnbound.getStaCode());
            }
        }
    }

    /**
     * AF库存比对定时任务，比对利丰仓库和宝尊仓库的库存量，发送邮件
     */
    public void afInvCompareMethod() {
        log.debug("=========afInvCompareMethod start===========");
        aFManager.sendAfInvComReportMail(Constants.AF_VIM_CODE, Constants.AF_VIM_SOURCE, 20080l);
    }
}
