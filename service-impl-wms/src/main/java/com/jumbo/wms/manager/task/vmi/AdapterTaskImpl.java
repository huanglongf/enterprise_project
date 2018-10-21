package com.jumbo.wms.manager.task.vmi;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baozun.task.annotation.SingleTaskLock;
import com.jumbo.dao.baseinfo.SkuDao;
import com.jumbo.dao.hub2wms.WmsConfirmOrderQueueDao;
import com.jumbo.dao.hub2wms.WmsOrderStatusOmsDao;
import com.jumbo.dao.system.ChooseOptionDao;
import com.jumbo.dao.task.CommonConfigDao;
import com.jumbo.dao.warehouse.AgvSkuDao;
import com.jumbo.dao.warehouse.InboundAgvToHubDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.wms.Constants;
import com.jumbo.wms.daemon.AdapterTask;
import com.jumbo.wms.manager.BaseManagerImpl;

@Transactional
@Service("adapterTask")
public class AdapterTaskImpl extends BaseManagerImpl implements AdapterTask {

    private static final long serialVersionUID = 5782526986244138271L;
    protected static final Logger log = LoggerFactory.getLogger(AdapterTaskImpl.class);

    @Autowired
    private WmsConfirmOrderQueueDao wmsConfirmOrderQueueDao;
    @Autowired
    private WmsOrderStatusOmsDao wmsOrderStatusOmsDao;
    @Autowired
    private CommonConfigDao commonConfigDao;
    @Autowired
    private InboundAgvToHubDao inboundAgvToHubDao;
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private SkuDao skuDao;
    @Autowired
    private AgvSkuDao agvSkuDao;
    @Autowired
    private ChooseOptionDao chooseOptionDao;


    @Override
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void updateCreateOrderAndOutboundFlag() {
        // List<CommonConfig> cfg =
        // commonConfigDao.findCommonConfigListByParaGroup(Constants.WMS_ADAPTER_OWNER_CONFIG);
        List<String> cfg = chooseOptionDao.findByCategoryCodeA(Constants.WMS_ADAPTER_SYSTEM_KEY_CONFIG);
        if (cfg != null && !cfg.isEmpty()) {
            log.info("updateCreateOrderAndOutboundFlag begin!");
            // 创单反馈is_mq打标识
            try {
                while (true) {
                    Long count = wmsConfirmOrderQueueDao.getUnpushedCreateOrderCount(new SingleColumnRowMapper<Long>(Long.class));
                    if (count == null || (count != null && count.intValue() == 0)) {
                        break;
                    }
                    wmsConfirmOrderQueueDao.updateCreateOrderFlag();
                }
            } catch (Exception e) {
                log.error("update createOrder isMq flag error! ", e);
            }
            // 出库反馈is_mq打标识
            try {
                while (true) {
                    Long count = wmsOrderStatusOmsDao.getUnpushedOutboundOrderCount(new SingleColumnRowMapper<Long>(Long.class));
                    if (count == null || (count != null && count.intValue() == 0)) {
                        break;
                    }
                    wmsOrderStatusOmsDao.updateOutboundOrderFlag();
                }
            } catch (Exception e) {
                log.error("update outboundOrder isMq flag error! ", e);
            }
            log.info("updateCreateOrderAndOutboundFlag end!");
        }
    }

}
