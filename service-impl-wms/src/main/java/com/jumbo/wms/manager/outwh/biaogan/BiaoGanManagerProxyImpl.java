package com.jumbo.wms.manager.outwh.biaogan;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;

import com.jumbo.dao.baseinfo.BrandDao;
import com.jumbo.dao.system.ChooseOptionDao;
import com.jumbo.dao.vmi.warehouse.IdsInboundSkuDao;
import com.jumbo.dao.vmi.warehouse.MsgOutboundOrderDao;
import com.jumbo.dao.vmi.warehouse.MsgOutboundReturnDao;
import com.jumbo.dao.vmi.warehouse.MsgSKUSyncDao;
import com.jumbo.webservice.biaogan.clientNew.PushExpressInfoPortTypeClientNew;
import com.jumbo.webservice.biaogan.command.RtnOutBoundCommand;
import com.jumbo.webservice.biaogan.command.RtnOutBoundCommandList;
import com.jumbo.wms.Constants;
import com.jumbo.wms.manager.outwh.BaseOutWarehouseManager;
import com.jumbo.wms.model.baseinfo.Warehouse;
import com.jumbo.wms.model.vmi.warehouse.MsgInvoice;
import com.jumbo.wms.model.vmi.warehouse.MsgSKUSync;

@Service("biaoGanManagerProxy")
public class BiaoGanManagerProxyImpl implements BiaoGanManagerProxy {
    protected static final Logger log = LoggerFactory.getLogger(BiaoGanManagerProxyImpl.class);
    /**
	 * 
	 */
    private static final long serialVersionUID = 6911567821851120849L;

    @Autowired
    private MsgOutboundOrderDao msgOutboundOrderDao;
    @Autowired
    private BiaoGanManager biaoGanManager;
    @Autowired
    private BaseOutWarehouseManager baseOutWarehouseManager;
    @Autowired
    private MsgSKUSyncDao msgSKUSyncDao;
    @Autowired
    private MsgOutboundReturnDao msgOutboundReturnDao;
    @Autowired
    private ChooseOptionDao chooseOptionDao;
    @Autowired
    private BrandDao brandDao;
    @Autowired
    private IdsInboundSkuDao idsInboundSkuDao;

    /**
     * 其他出库通知
     */
    public void outBoundReturn() {
        List<Long> list = msgOutboundReturnDao.findMsgOutboundReturnIds(Constants.VIM_WH_SOURCE_BIAOGAN, new SingleColumnRowMapper<Long>(Long.class));
        if (list != null) {
            int limit = 10;
            int start = 1;
            List<Long> tmp = new ArrayList<Long>();
            for (Long id : list) {
                tmp.add(id);
                if (start == limit) {
                    try {
                        biaoGanManager.outBoundReturn(tmp);
                    } catch (Exception e) {
                        log.error("", e);
                    }
                    tmp = new ArrayList<Long>();
                    start = 1;
                }
                start++;
            }
            if (tmp.size() > 0) {
                try {
                    biaoGanManager.outBoundReturn(tmp);
                } catch (Exception e) {
                    log.error("", e);
                }
            }
        }
    }

    /**
     * 处理流程为判断当日出库作业单是否都同步到，发现未同步的订单直接通过查询接口反馈信息同步出库信息
     */
    public void orderDetailQueryToday() {
        RtnOutBoundCommandList orders = PushExpressInfoPortTypeClientNew.shipmentInfoQueryByDate();
        if (orders != null && orders.getOutputBack() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            for (RtnOutBoundCommand order : orders.getOutputBack()) {
                try {
                    biaoGanManager.outBoundToday(order, sdf);
                } catch (Exception e) {
                    log.error("", e);
                }
            }
        }
    }

    /**
     * 同步商品
     */
    public void singleSkuToWmsProxy() {
        List<MsgSKUSync> skus = msgSKUSyncDao.findVmiMsgSKUSync(Constants.VIM_WH_SOURCE_BIAOGAN, new BeanPropertyRowMapper<MsgSKUSync>(MsgSKUSync.class));
        for (MsgSKUSync s : skus) {
            try {
                List<MsgSKUSync> skus2 = new ArrayList<MsgSKUSync>();
                skus2.add(s);
                biaoGanManager.singleSkuToWms(skus2);
            } catch (Exception e) {
                log.error("", e);
            }

        }
    }

    /**
     * 发送销售订单
     */
    public void sendSalesOutOrder() {
        try {
            List<Long> ids = msgOutboundOrderDao.findMsgOutboundOrderId(Constants.VIM_WH_SOURCE_BIAOGAN, new SingleColumnRowMapper<Long>(Long.class));
            List<Long> errorList = new ArrayList<Long>();
            int pc = 10;// 批次长度
            List<Long> ids2 = new ArrayList<Long>();
            for (int i = 0; i < ids.size(); i++) {
                ids2.add(ids.get(i));
                if (i != 0 && i % pc == 0) {
                    try {
                        biaoGanManager.outBoundOrder(ids2);
                    } catch (Exception e) {
                        log.error("", e);
                        errorList.addAll(ids2);
                    } finally {
                        ids2 = new ArrayList<Long>();
                    }
                }
                ids.get(i);
            }
            if (ids2.size() > 0) {
                try {
                    biaoGanManager.outBoundOrder(ids2);
                } catch (Exception e) {
                    log.error("", e);
                    errorList.addAll(ids2);
                }
            }
            for (int i = 0; i < errorList.size(); i++) {
                ids2 = new ArrayList<Long>();
                ids2.add(ids.get(i));
                try {
                    biaoGanManager.outBoundOrder(ids2);
                } catch (Exception e) {
                    log.error("", e);
                }
            }
        } catch (Exception e) {
            log.error("", e);
        }
    }

    public void sendInvoices() {
        // 查询所有标杆仓库
        List<Warehouse> whs = baseOutWarehouseManager.findOutWarehousesBySource(Constants.VIM_WH_SOURCE_BIAOGAN);
        if (whs != null) {
            for (Warehouse wh : whs) {
                try {
                    // 查询仓库所有未同步发票
                    Map<String, List<MsgInvoice>> map = baseOutWarehouseManager.findInvoicesBySourceGroup(wh.getVmiSource(), wh.getVmiSourceWh());
                    // 每次发送15条
                    int limit = 15;
                    int start = 1;
                    Map<String, List<MsgInvoice>> tmpMap = new HashMap<String, List<MsgInvoice>>(17);
                    for (Entry<String, List<MsgInvoice>> ent : map.entrySet()) {
                        tmpMap.put(ent.getKey(), ent.getValue());
                        if (start == limit) {
                            try {
                                biaoGanManager.sendInvoice(tmpMap, wh.getVmiSource(), wh.getVmiSourceWh());
                            } catch (Exception e) {
                                log.error("", e);
                            }
                            tmpMap = new HashMap<String, List<MsgInvoice>>(17);
                            start = 0;
                        }
                        start++;
                    }
                    try {
                        if (tmpMap.size() > 0) {
                            biaoGanManager.sendInvoice(tmpMap, wh.getVmiSource(), wh.getVmiSourceWh());
                        }
                    } catch (Exception e) {
                        log.error("", e);
                    }
                } catch (Exception e) {
                    log.error("", e);
                }

            }
        }
    }



}
