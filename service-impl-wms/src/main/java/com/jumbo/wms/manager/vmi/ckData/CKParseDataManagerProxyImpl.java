package com.jumbo.wms.manager.vmi.ckData;

import java.util.List;

import loxia.dao.support.BeanPropertyRowMapperExt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;

import com.jumbo.dao.vmi.ckData.CKOutBoundFeedBackDao;
import com.jumbo.dao.vmi.warehouse.MsgRtnInboundOrderDao;
import com.jumbo.dao.vmi.warehouse.MsgRtnOutboundDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.wms.Constants;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.manager.warehouse.WareHouseManagerProxy;
import com.jumbo.wms.model.DefaultStatus;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnInboundOrder;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnOutbound;
import com.jumbo.wms.model.warehouse.StockTransApplication;

@Service("cKParseDataManagerProxy")
public class CKParseDataManagerProxyImpl implements CKParseDataManagerProxy {
    protected static final Logger log = LoggerFactory.getLogger(CKParseDataManagerProxyImpl.class);

    /**
	 * 
	 */
    private static final long serialVersionUID = 4156889607130303154L;
    @Autowired
    private CKOutBoundFeedBackDao obFeedBackDao;
    @Autowired
    private MsgRtnOutboundDao msgRtnOutboundDao;
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private WareHouseManager wareHouseManager;
    @Autowired
    private WareHouseManagerProxy wareHouseManagerProxy;
    @Autowired
    private MsgRtnInboundOrderDao msgRtnInboundDao;
    @Autowired
    private CKParseDataManager ckParseDataManager;

    /**
     * 处理出库反馈，并执行出库
     */
    public void executeOBFeedBack() {
        // 将中间表中未处理过的数据状态变成处理中
        obFeedBackDao.updateOutFeedbackStatus(DefaultStatus.CREATED.getValue(), DefaultStatus.EXECUTING.getValue());
        // 将中间表中状态为“处理中”的数据插入到反馈表中。
        msgRtnOutboundDao.saveRtnOutBoundFromTemplate(Constants.CK_WH_SOURCE, DefaultStatus.CREATED.getValue(), DefaultStatus.EXECUTING.getValue());
        // 将中间表中“处理中”的数据变成“完成”
        obFeedBackDao.updateOutFeedbackStatus(DefaultStatus.EXECUTING.getValue(), DefaultStatus.FINISHED.getValue());

        // 1.读反馈表
        List<MsgRtnOutbound> rtns = msgRtnOutboundDao.findAllVmiMsgOutbound(Constants.CK_WH_SOURCE, new BeanPropertyRowMapper<MsgRtnOutbound>(MsgRtnOutbound.class));
        // 2.执行出库流程
        for (MsgRtnOutbound rtn : rtns) {
            StockTransApplication sta = staDao.findStaByCode(rtn.getStaCode());
            if (sta == null) {
                wareHouseManager.updateMsgRtnOutbound(rtn.getId(), DefaultStatus.ERROR.getValue());
                continue;
            }
            int staStatus = sta.getStatus().getValue();
            if (staStatus == 10 || staStatus == 4) {
                wareHouseManager.updateMsgRtnOutbound(rtn.getId(), DefaultStatus.FINISHED.getValue());
                continue;
            }
            try {
                wareHouseManagerProxy.callVmiSalesStaOutBound(rtn.getId());
            } catch (BusinessException e) {
                log.error("executeOBFeedBack error ! Out STA :" + e.getErrorCode());
            } catch (Exception e) {
                log.error("", e);
            }

        }
    }

    /**
     * 执行退货入库流程
     */
    public void executeIBFeedBack() {
        ckParseDataManager.executeIBFeedBackForData();
        // 执行入库
        List<MsgRtnInboundOrder> rtns = msgRtnInboundDao.findInboundByStatus(Constants.CK_WH_SOURCE, new BeanPropertyRowMapperExt<MsgRtnInboundOrder>(MsgRtnInboundOrder.class));
        if (rtns != null && rtns.size() > 0) {
            wareHouseManagerProxy.inboundToBz(rtns);
        }

    }

}
