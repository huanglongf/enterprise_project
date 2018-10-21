package com.jumbo.wms.manager.vmi.itochuData;

import java.util.List;

import loxia.dao.support.BeanPropertyRowMapperExt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;

import com.jumbo.dao.vmi.warehouse.MsgRtnInboundOrderDao;
import com.jumbo.dao.vmi.warehouse.MsgRtnOutboundDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.wms.Constants;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.manager.BaseManagerImpl;
/*
 * import com.jumbo.manager.warehouse.WareHouseManager; import
 * com.jumbo.manager.warehouse.WareHouseManagerProxy; import com.jumbo.model.DefaultStatus; import
 * com.jumbo.model.vmi.warehouse.MsgRtnInboundOrder; import
 * com.jumbo.model.vmi.warehouse.MsgRtnOutbound; import
 * com.jumbo.model.warehouse.StockTransApplication;
 */
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.manager.warehouse.WareHouseManagerProxy;
import com.jumbo.wms.model.DefaultStatus;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnInboundOrder;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnOutbound;
import com.jumbo.wms.model.warehouse.StockTransApplication;


/**
 * UA
 * 
 * @author oypj
 * 
 */

@Service("itochuUAManagerProxy")
public class ItochuUAManagerImplProxy extends BaseManagerImpl implements ItochuUAManagerProxy {

    @Autowired
    private MsgRtnOutboundDao msgRtnOutboundDao;
    @Autowired
    private WareHouseManager wareHouseManager;
    @Autowired
    private WareHouseManagerProxy wareHouseManagerProxy;
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private MsgRtnInboundOrderDao msgRtnInboundOrderDao;
    /**
	 * 
	 */
    private static final long serialVersionUID = 3967028537053611855L;

    @Override
    public void uaOutBoundRtnExecute() {
        // 1.读反馈表
        List<MsgRtnOutbound> rtns = msgRtnOutboundDao.findAllVmiMsgOutbound(Constants.VIM_WH_SOURCE_ITOCHU_UA, new BeanPropertyRowMapper<MsgRtnOutbound>(MsgRtnOutbound.class));
        // 2.执行出库流程
        for (MsgRtnOutbound rtn : rtns) {
            StockTransApplication sta = staDao.findStaByCode(rtn.getStaCode());
            if (sta == null) {
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
                log.error("uaOutBoundRtnExecute error ! Out STA :" + e.getErrorCode());
            } catch (Exception e) {
                log.error("" + e);
            }
        }

    }

    @Override
    public void uaInBoundRtnExecute() {
        List<MsgRtnInboundOrder> rtns = msgRtnInboundOrderDao.findInboundByStatus(Constants.VIM_WH_SOURCE_ITOCHU_UA, new BeanPropertyRowMapperExt<MsgRtnInboundOrder>(MsgRtnInboundOrder.class));
        if (rtns != null && rtns.size() > 0) {
            wareHouseManagerProxy.inboundToBz(rtns);
        }
    }



}
