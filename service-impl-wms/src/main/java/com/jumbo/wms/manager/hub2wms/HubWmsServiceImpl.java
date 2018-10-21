package com.jumbo.wms.manager.hub2wms;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.baozun.scm.baseservice.sac.manager.CodeManager;
import com.baozun.scm.baseservice.sac.model.seq.CodeSequence;
import com.baozun.scm.baseservice.sac.service.CodeService;
import com.baozun.utilities.json.JsonUtil;
import com.jumbo.dao.authorization.OperationUnitDao;
import com.jumbo.dao.baseinfo.CustomerDao;
import com.jumbo.dao.baseinfo.SkuDao;
import com.jumbo.dao.baseinfo.WarehouseDao;
import com.jumbo.dao.hub2wms.CnCancelOrderLogDao;
import com.jumbo.dao.hub2wms.CnOutOrderNotifyDao;
import com.jumbo.dao.hub2wms.CnWmsStockInOrderNotifyDao;
import com.jumbo.dao.hub2wms.WmsConfirmOrderQueueDao;
import com.jumbo.dao.hub2wms.WmsRtnInOrderQueueDao;
import com.jumbo.dao.hub2wms.WmsRtnOrderLineQueueDao;
import com.jumbo.dao.hub2wms.WmsSalesOrderLogDao;
import com.jumbo.dao.hub2wms.WmsSalesOrderQueueDao;
import com.jumbo.dao.hub2wms.WmsShippingLineQueueDao;
import com.jumbo.dao.hub2wms.WmsShippingQueueDao;
import com.jumbo.dao.hub2wms.WmsTransInfoOmsDao;
import com.jumbo.dao.invflow.WmsIMOccupiedAndReleaseDao;
import com.jumbo.dao.invflow.WmsSkuInventoryFlowDao;
import com.jumbo.dao.vmi.adidas.AdidasSalesInventoryDao;
import com.jumbo.dao.vmi.adidas.AdidasTotalInventoryDao;
import com.jumbo.dao.vmi.adidas.BlProductDao;
import com.jumbo.dao.vmi.adidas.BlSkuDao;
import com.jumbo.dao.vmi.adidas.HubSkuDao;
import com.jumbo.dao.vmi.adidas.InventoryBatchDao;
import com.jumbo.dao.vmi.espData.ESPDeliveryDao;
import com.jumbo.dao.vmi.espData.ESPOrderDao;
import com.jumbo.dao.vmi.gucciData.GucciSalesInventoryDao;
import com.jumbo.dao.vmi.reebok.ReebokSalesInventoryDao;
import com.jumbo.dao.vmi.reebok.ReebokTotalInventoryDao;
import com.jumbo.dao.vmi.warehouse.MsgOmsTmallOrderDao;
import com.jumbo.dao.vmi.warehouse.MsgRtnOutboundDao;
import com.jumbo.dao.warehouse.AdCancelDao;
import com.jumbo.dao.warehouse.AdvanceOrderAddInfoDao;
import com.jumbo.dao.warehouse.BiChannelDao;
import com.jumbo.dao.warehouse.EspritStoreDao;
import com.jumbo.dao.warehouse.InboundAgvToHubDao;
import com.jumbo.dao.warehouse.InboundAgvToHubLineDao;
import com.jumbo.dao.warehouse.InventoryCheckDao;
import com.jumbo.dao.warehouse.InventoryCheckDifferenceLineDao;
import com.jumbo.dao.warehouse.InventoryDao;
import com.jumbo.dao.warehouse.InventoryStatusDao;
import com.jumbo.dao.warehouse.PickingListDao;
import com.jumbo.dao.warehouse.RecieverInfoDao;
import com.jumbo.dao.warehouse.SkuSnCheckCfgDao;
import com.jumbo.dao.warehouse.SkuSnDao;
import com.jumbo.dao.warehouse.SkuSnMappingDao;
import com.jumbo.dao.warehouse.StaDeliveryInfoDao;
import com.jumbo.dao.warehouse.StaLineDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.dao.warehouse.StockTransVoucherDao;
import com.jumbo.dao.warehouse.StvLineDao;
import com.jumbo.rmi.warehouse.BaseResult;
import com.jumbo.util.StringUtil;
import com.jumbo.wms.Constants;
import com.jumbo.wms.daemon.AdidasTask;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.compensation.CompensationManager;
import com.jumbo.wms.manager.sn.SnManagerImpl;
import com.jumbo.wms.manager.warehouse.StaCancelManager;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.manager.warehouse.WareHouseManagerCancel;
import com.jumbo.wms.manager.warehouse.WhBoxInboundManager;
import com.jumbo.wms.model.DefaultStatus;
import com.jumbo.wms.model.MongoDBOrderAddInfo;
import com.jumbo.wms.model.MongoDBRecieverInfo;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.baseinfo.Customer;
import com.jumbo.wms.model.baseinfo.MSRCustomCardRtnInfo;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.baseinfo.SkuSnCheckCfgType;
import com.jumbo.wms.model.baseinfo.SkuSnMapping;
import com.jumbo.wms.model.baseinfo.Warehouse;
import com.jumbo.wms.model.command.EspritStoreCommand;
import com.jumbo.wms.model.command.SkuSnCheckCfgCommand;
import com.jumbo.wms.model.compensation.ClaimHead;
import com.jumbo.wms.model.compensation.ClaimProcess;
import com.jumbo.wms.model.compensation.ClaimProcessAffirm;
import com.jumbo.wms.model.compensation.ClaimResult;
import com.jumbo.wms.model.compensation.WmsClaimResult;
import com.jumbo.wms.model.hub2wms.OmsTmallOrder;
import com.jumbo.wms.model.hub2wms.OrderAddInfo;
import com.jumbo.wms.model.hub2wms.ReturnResult;
import com.jumbo.wms.model.hub2wms.RtnOrderByAD;
import com.jumbo.wms.model.hub2wms.WmsAdidasSalesInventory;
import com.jumbo.wms.model.hub2wms.WmsAdidasTotalInventory;
import com.jumbo.wms.model.hub2wms.WmsAdvanceOrderResult;
import com.jumbo.wms.model.hub2wms.WmsConfirmOrder;
import com.jumbo.wms.model.hub2wms.WmsOrderCancel;
import com.jumbo.wms.model.hub2wms.WmsOrderCancelLine;
import com.jumbo.wms.model.hub2wms.WmsOrderCancelResult;
import com.jumbo.wms.model.hub2wms.WmsOrderLine;
import com.jumbo.wms.model.hub2wms.WmsOrderStatus;
import com.jumbo.wms.model.hub2wms.WmsReebokSalesInventory;
import com.jumbo.wms.model.hub2wms.WmsReebokTotalInventory;
import com.jumbo.wms.model.hub2wms.WmsRtnInOrder;
import com.jumbo.wms.model.hub2wms.WmsRtnInOrderQueue;
import com.jumbo.wms.model.hub2wms.WmsRtnOrder;
import com.jumbo.wms.model.hub2wms.WmsRtnOrderLine;
import com.jumbo.wms.model.hub2wms.WmsRtnOrderResult;
import com.jumbo.wms.model.hub2wms.WmsSalesOrder;
import com.jumbo.wms.model.hub2wms.WmsSalesOrderLine;
import com.jumbo.wms.model.hub2wms.WmsSalesOrderLog;
import com.jumbo.wms.model.hub2wms.WmsSalesOrderQueue;
import com.jumbo.wms.model.hub2wms.WmsSalesOrderResult;
import com.jumbo.wms.model.hub2wms.WmsShipping;
import com.jumbo.wms.model.hub2wms.WmsShippingLine;
import com.jumbo.wms.model.hub2wms.WmsShippingResult;
import com.jumbo.wms.model.hub2wms.WmsSku;
import com.jumbo.wms.model.hub2wms.threepl.CnCancelOrderLog;
import com.jumbo.wms.model.hub2wms.threepl.CnOutOrderNotify;
import com.jumbo.wms.model.hub2wms.threepl.CnWmsStockInOrderNotify;
import com.jumbo.wms.model.invflow.WmsCustomer;
import com.jumbo.wms.model.invflow.WmsIMOccupiedAndRelease;
import com.jumbo.wms.model.invflow.WmsOccupiedAndRelease;
import com.jumbo.wms.model.invflow.WmsSkuInventoryFlow;
import com.jumbo.wms.model.mongodb.MSRCustomCardFeedBack;
import com.jumbo.wms.model.msg.MongoAGVMessage;
import com.jumbo.wms.model.vmi.GucciData.GucciSalesInvHub;
import com.jumbo.wms.model.vmi.adidasData.InventoryBatch;
import com.jumbo.wms.model.vmi.espData.ESPDelivery;
import com.jumbo.wms.model.vmi.espData.ESPDeliveryCommand;
import com.jumbo.wms.model.vmi.espData.ESPOrder;
import com.jumbo.wms.model.vmi.warehouse.MsgOmstmallOrder;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnOutbound;
import com.jumbo.wms.model.warehouse.AdCancel;
import com.jumbo.wms.model.warehouse.AdvanceOrderAddInfo;
import com.jumbo.wms.model.warehouse.BlProduct;
import com.jumbo.wms.model.warehouse.BlSku;
import com.jumbo.wms.model.warehouse.BlSkuCommand;
import com.jumbo.wms.model.warehouse.HubSku;
import com.jumbo.wms.model.warehouse.InboundAgvToHub;
import com.jumbo.wms.model.warehouse.InboundAgvToHubLine;
import com.jumbo.wms.model.warehouse.InboundStoreMode;
import com.jumbo.wms.model.warehouse.InventoryCheck;
import com.jumbo.wms.model.warehouse.InventoryCheckDifferenceLine;
import com.jumbo.wms.model.warehouse.InventoryCheckStatus;
import com.jumbo.wms.model.warehouse.InventoryStatus;
import com.jumbo.wms.model.warehouse.PickingList;
import com.jumbo.wms.model.warehouse.RecieverInfo;
import com.jumbo.wms.model.warehouse.StaDeliveryInfo;
import com.jumbo.wms.model.warehouse.StaLine;
import com.jumbo.wms.model.warehouse.StaLineCommand;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationCommand;
import com.jumbo.wms.model.warehouse.StockTransApplicationStatus;
import com.jumbo.wms.model.warehouse.StockTransApplicationType;
import com.jumbo.wms.model.warehouse.StvLineCommand;
import com.jumbo.wms.model.warehouse.O2oEsprit.EspDeliveryCommand;
import com.jumbo.wms.model.warehouse.O2oEsprit.EspDeliveryItem;
import com.jumbo.wms.model.warehouse.O2oEsprit.EspDeliveryLineCommand;
import com.jumbo.wms.model.warehouse.O2oEsprit.EspDeliveryResponse;
import com.jumbo.wms.model.warehouse.O2oEsprit.EspritConstants;
import com.jumbo.wms.model.warehouse.O2oEsprit.NotifyConstants;
import com.jumbo.wms.model.warehouse.agv.AgvOutBoundDto;
import com.jumbo.wms.model.warehouse.agv.AgvOutBoundLineDto;

import loxia.dao.Pagination;
import loxia.dao.Sort;
import loxia.dao.support.BeanPropertyRowMapperExt;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service("hubWmsService")
public class HubWmsServiceImpl extends BaseManagerImpl implements HubWmsService {
    // 取消成功
    private static final String COLSE_SUCCESS = "S0001";
    // 取消成功-订单已取消
    private static final String COLSE_SUCCESS1 = "S0002";
    // 等待确认
    private static final String COLSE_WAIT = "W0001";
    // 取消失败-订单未找到
    private static final String FAILURE = "E0001";
    // 取消失败-订单无法取消
    private static final String COLSE_FAILURE = "E0002";
    // 取消失败-订单已发货
    public static final String FAILURE1 = "E0010";
    public static final String RECEIVE_SUCCESS = "接收成功";
    public static final String RECEIVE_FAILURE = "接收失敗";


    @Autowired
    private WhBoxInboundManager whBoxInboundManager;
    @Autowired
    private InboundAgvToHubLineDao inboundAgvToHubLineDao;
    @Autowired
    private ESPOrderDao espOrderDao;
    @Autowired
    BlProductDao blProductDao;
    @Autowired
    BlSkuDao blSkuDao;
    @Autowired
    WmsRtnInOrderQueueDao inOrderQueueDao;
    @Autowired
    WmsRtnOrderLineQueueDao lineQueueDao;
    @Autowired
    HubWmsManager wmsManager;
    @Resource
    private ApplicationContext applicationContext;
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private HubWmsManager hubWmsManager;
    @Autowired
    private SkuDao skuDao;
    @Autowired
    private StaCancelManager staCancelManager;
    @Autowired
    private WmsConfirmOrderQueueDao wmsConfirmOrderQueueDao;
    @Autowired
    private WmsShippingQueueDao wmsShippingQueueDao;
    @Autowired
    private WmsShippingLineQueueDao wmsShippingLineQueueDao;
    @Autowired
    private WmsSalesOrderLogDao logDao;
    Sort[] sorts;

    @Autowired
    private CompensationManager compensationManager;
    @Autowired
    private MsgRtnOutboundDao msgRtnDao;
    @Autowired
    private MsgOmsTmallOrderDao msgOmsDao;
    @Autowired
    private AdidasTotalInventoryDao adidasTotalInventoryDao;
    @Autowired
    private AdidasSalesInventoryDao adidasSalesInventoryDao;
    @Autowired
    WmsTransInfoOmsDao infoOmsDao;
    @Autowired
    private StaLineDao staLineDao;
    @Autowired
    private PickingListDao pickingListDao;
    @Autowired
    private AdidasTask adidasTask;
    @Autowired
    private WareHouseManager wareHouseManager;
    @Autowired
    private CnWmsStockInOrderNotifyDao cnWmsStockInOrderNotifyDao;
    @Autowired
    private CnOutOrderNotifyDao cnOutOrderNotifyDao;
    @Autowired
    private WareHouseManagerCancel wareHouseManagerCancel;
    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private CnCancelOrderLogDao cnCancelOrderLogDao;
    @Autowired
    private ReebokSalesInventoryDao reebokSalesInventoryDao;
    @Autowired
    private ReebokTotalInventoryDao reebokTotalInventoryDao;
    @Autowired
    private GucciSalesInventoryDao gucciSalesInventoryDao;
    @Autowired
    private EspritStoreDao espritStoreDao;
    @Autowired
    private ESPDeliveryDao espDeliveryDao;

    @Autowired
    private StaDeliveryInfoDao staDeliveryInfoDao;

    @Autowired
    private CodeService codeService;

    @Autowired(required = false)
    private CodeManager codeManager;

    @Autowired
    private WmsSkuInventoryFlowDao wmsSkuInventoryFlowDao;

    @Autowired
    private WmsIMOccupiedAndReleaseDao wmsIMOccupiedAndReleaseDao;

    @Autowired
    private BiChannelDao biChannelDao;

    @Autowired
    private InventoryStatusDao inventoryStatusDao;
    @Autowired
    private StvLineDao stvLineDao;
    @Autowired
    private OperationUnitDao operationUnitDao;
    @Autowired
    private InventoryDao inventoryDao;
    @Autowired
    private AdvanceOrderAddInfoDao addInfoDao;
    @Autowired
    private RecieverInfoDao recieverInfoDao;
    @Resource(name = "mongoTemplate")
    private MongoOperations mongoOperation;
    @Autowired
    AdvanceOrderAddInfoDao advanceOrderAddInfoDao;
    @Autowired
    private HubSkuDao hubSkuDao;
    @Autowired
    private SkuSnDao skuSnDao;
    @Autowired
    private SkuSnMappingDao skuSnMappingDao;
    @Autowired
    private SkuSnCheckCfgDao skuSnCheckCfgDao;
    @Autowired
    private StockTransVoucherDao stvDao;
    @Autowired
    private WarehouseDao warehouseDao;
    @Autowired
    private WmsSalesOrderQueueDao wmsSalesOrderQueueDao;
    @Autowired
    private CreateStaTaskManager createStaTaskManager;
    @Autowired
    private InventoryCheckDao inventoryCheckDao;
    @Autowired
    private InventoryCheckDifferenceLineDao inventoryCheckDifferenceLineDao;
    @Autowired
    private InboundAgvToHubDao inboundAgvToHubDao;

    @Autowired
    private AdCancelDao adCancelDao;
    @Autowired
    private InventoryBatchDao inventoryBatchDao;

    /**
     * 
     */
    private static final long serialVersionUID = 4135171480729216111L;

    protected static final Logger log = LoggerFactory.getLogger(HubWmsServiceImpl.class);



    @Override
    public ReturnResult EspritAsn(String po, List<com.jumbo.wms.model.hub2wms.EspritAsn> espritAsn) {
        ReturnResult returnResult = new ReturnResult();
        Map<String, List<StaLineCommand>> ml = new HashMap<String, List<StaLineCommand>>();
        if (null != espritAsn && espritAsn.size() == 0) {
            returnResult.setStatus(0);
            returnResult.setMemo("SKU不能为空");
        }
        if (null != po && !"".equals(po)) {
            List<ESPOrder> eSPOrderList = espOrderDao.findOrderAsnByPo(po, new BeanPropertyRowMapper<ESPOrder>(ESPOrder.class));
            if (null != eSPOrderList && eSPOrderList.size() > 0) {
                for (com.jumbo.wms.model.hub2wms.EspritAsn list : espritAsn) {
                    if (ml.containsKey(list.getCartonNo())) {
                        List<StaLineCommand> nl = ml.get(list.getCartonNo());
                        StaLineCommand staLineCommand = new StaLineCommand();
                        staLineCommand.setSlipCode(list.getCartonNo());
                        staLineCommand.setBarCode(list.getSku());
                        staLineCommand.setQuantity(list.getQty());
                        nl.add(staLineCommand);
                    } else {
                        List<StaLineCommand> nl = new ArrayList<StaLineCommand>();
                        StaLineCommand staLineCommand = new StaLineCommand();
                        staLineCommand.setSlipCode(list.getCartonNo());
                        staLineCommand.setBarCode(list.getSku());
                        staLineCommand.setQuantity(list.getQty());
                        nl.add(staLineCommand);
                        ml.put(list.getCartonNo(), nl);
                    }
                }
                Map<String, Long> qtyMap = new HashMap<String, Long>();
                List<StockTransApplication> isStaExist = staDao.findBySlipCodeCancel(po);
                if (null != isStaExist && isStaExist.size() > 0) {
                    List<List<StaLineCommand>> list = new ArrayList<List<StaLineCommand>>();
                    List<StaLineCommand> staLineCommandList = staLineDao.findStalineByStaId2(isStaExist.get(0).getId(), new BeanPropertyRowMapper<StaLineCommand>(StaLineCommand.class));
                    for (StaLineCommand staLine : staLineCommandList) {
                        if (qtyMap.containsKey(staLine.getBarCode())) {
                            BigDecimal qty = new BigDecimal(qtyMap.get(staLine.getBarCode()));
                            BigDecimal qty1 = new BigDecimal(staLine.getQuantity());
                            staLine.setQuantity(qty.add(qty1).longValue());
                        } else {
                            qtyMap.put(staLine.getBarCode(), staLine.getQuantity());
                        }
                    }
                    // 减去已经存在的单据计划量
                    List<StaLineCommand> staLineCommandList1 = staLineDao.findStalineByStaId3(isStaExist.get(0).getId(), new BeanPropertyRowMapper<StaLineCommand>(StaLineCommand.class));
                    if (staLineCommandList1 != null && staLineCommandList1.size() > 0) {
                        for (StaLineCommand staLineCommand1 : staLineCommandList1) {
                            if (qtyMap.containsKey(staLineCommand1.getBarCode())) {
                                BigDecimal qty = new BigDecimal(qtyMap.get(staLineCommand1.getBarCode()));
                                BigDecimal qty1 = new BigDecimal(staLineCommand1.getQuantity());
                                BigDecimal qty2 = qty1.subtract(qty);
                                qtyMap.put(staLineCommand1.getBarCode(), qty2.longValue());
                            }
                        }
                    }
                    for (Map.Entry<String, List<StaLineCommand>> map : ml.entrySet()) {
                        List<StaLineCommand> staLineCommand = map.getValue();
                        List<StaLineCommand> qtyList = new ArrayList<StaLineCommand>();
                        for (StaLineCommand staLine : staLineCommand) {
                            if (qtyMap.containsKey(staLine.getBarCode())) {
                                BigDecimal qty1 = new BigDecimal(qtyMap.get(staLine.getBarCode()));
                                BigDecimal qty = new BigDecimal(staLine.getQuantity());
                                BigDecimal qty2 = qty1.subtract(qty);
                                if (qty2.longValue() >= 0) {
                                    qtyList.add(staLine);
                                    qtyMap.put(staLine.getBarCode(), qty2.longValue());
                                }
                            }
                        }
                        if (qtyList != null && qtyList.size() > 0) {
                            list.add(qtyList);
                        }
                    }
                    // list.addAll(ml.values());
                    if (list != null && list.size() > 0) {
                        whBoxInboundManager.createSonStaByGroupData(list, 0L, isStaExist.get(0).getId());
                    }
                    returnResult.setStatus(1);
                    returnResult.setMemo("成功");
                } else {
                    returnResult.setStatus(0);
                    returnResult.setMemo("未收到品牌收货大指令");
                }

            } else {
                returnResult.setStatus(0);
                returnResult.setMemo("未收到品牌收货大指令");
            }

        } else {
            returnResult.setStatus(0);
            returnResult.setMemo("PO不能为空");
        }
        return returnResult;
    }

    @Override
    public WmsOrderCancelResult wmsCancelOrderExchange(String systemKey, String orderCode) {
        List<WmsShippingResult> results = new ArrayList<WmsShippingResult>();
        WmsOrderCancelResult rs = new WmsOrderCancelResult();
        try {

            boolean status = staCancelManager.cancelSalesStaBySlipCode(orderCode, StockTransApplicationType.OUTBOUND_RETURN_REQUEST);
            if (status) {
                rs.setMemo("取消成功");
                rs.setOrderCode(orderCode);
                WmsShippingResult result = new WmsShippingResult();
                result.setMemo("取消成功-订单取消成功");
                result.setStatusCode(COLSE_SUCCESS1);
                results.add(result);
                rs.setShippings(results);

                return rs;
            } else {
                rs.setMemo("等待确认");
                rs.setOrderCode(orderCode);
                WmsShippingResult result = new WmsShippingResult();
                result.setMemo("等待确认");
                result.setStatusCode(COLSE_WAIT);
                results.add(result);
                rs.setShippings(results);
                return rs;
            }
        } catch (BusinessException e) {
            log.error("wmsCancelOrderExchange sta error : error code is : [{}]", e.getErrorCode());
            BusinessException le = e;
            if (le != null) {
                log.error("wmsCancelOrderExchange sta error : error code is : [{}]", le.getErrorCode());
                rs.setMemo(ErrorCode.BUSINESS_EXCEPTION + le.getErrorCode());
                rs.setStatus(0);
                rs.setOrderCode(orderCode);
                rs.setOrderType(42);
                WmsShippingResult result = new WmsShippingResult();
                result.setStatusCode(COLSE_FAILURE);
                result.setMemo("取消失败");
                result.setShippingCode(orderCode);
                results.add(result);
                rs.setShippings(results);
            }
        } catch (Exception e) {
            WmsShippingResult result = new WmsShippingResult();
            result.setStatusCode(COLSE_FAILURE);
            result.setMemo("取消失败");
            result.setShippingCode(orderCode);
            results.add(result);
            rs.setShippings(results);
        }
        return rs;
    }

    @Override
    public WmsOrderCancelResult wmsCancelOrder(String systemKey, String shippingCode, String orderCode) {
        log.error("wmsCancelOrder_systemKey:"+systemKey+"_shippingCode:"+shippingCode+"_orderCode:"+orderCode);
        WmsOrderCancelResult rs = new WmsOrderCancelResult();
        try {
            if (Constants.CAINIAO_DB_SYSTEM_KEY.equals(systemKey)) {
                CnCancelOrderLog ol = new CnCancelOrderLog();
                ol.setOrderCode(orderCode);
                ol.setSystemKey(systemKey);
                try {
                    rs = cancelCaiNiaoOrderBySlipCode(systemKey, orderCode);
                    if (1 == rs.getStatus()) {
                        ol.setStatus(1);
                    } else {
                        ol.setStatus(-1);
                    }
                } catch (Exception e) {
                    rs.setStatus(0);
                    ol.setStatus(-1);
                }
                cnCancelOrderLogDao.save(ol);
                return rs;
            }
            if (log.isDebugEnabled()) {
                log.debug("so shippingCode is :[{}]", shippingCode);
            }
            //ad整单取消记录到中间表
            if("adidas".equals(systemKey)){
                List<StockTransApplication> isStaExist = staDao.findBySlipCode(orderCode);
                if(isStaExist.size()==0){
                    AdCancel adCancel=new AdCancel();
                    adCancel.setCreateTime(new Date());
                    adCancel.setLastUpdateTime(new Date());
                    adCancel.setSlipCode(orderCode);
                    adCancelDao.save(adCancel);
                }
            }
            // wms订单取消
            if (shippingCode != null) {
                rs = cancelSalesStaByshippingCode(shippingCode);
                return rs;
            }
            if (log.isDebugEnabled()) {
                log.debug("so orderCode is :[{}]", orderCode);
            }
            // OMS订单取消
            if (orderCode != null) {
                rs = cancelSalesStaBySlipCode(orderCode);
                return rs;
            }
        } catch (BusinessException e) {
            log.error("cancel sta error : error code is : [{}]", e.getErrorCode());
            log.error("wmsCancelOrder_2"+shippingCode+","+orderCode,e);
            BusinessException le = e;
            if (le != null) {
                log.error("cancel sta error : error code is : [{}]", le.getErrorCode());
                rs.setMemo(ErrorCode.BUSINESS_EXCEPTION + le.getErrorCode());
                rs.setStatus(0);
                rs.setOrderCode(orderCode);
                if (shippingCode != null) {
                    rs.setOrderType(21);
                } else {
                    rs.setOrderType(41);
                }
                List<WmsShippingResult> results = new ArrayList<WmsShippingResult>();
                WmsShippingResult result = new WmsShippingResult();
                result.setStatusCode(COLSE_FAILURE);
                result.setMemo("取消失败.");
                result.setShippingCode(shippingCode);
                results.add(result);
                rs.setShippings(results);
            }
        } catch (Exception e) {
            log.error("wmsCancelOrder_3"+shippingCode+","+orderCode,e);
            List<WmsShippingResult> results = new ArrayList<WmsShippingResult>();
            WmsShippingResult result = new WmsShippingResult();
            result.setStatusCode(COLSE_FAILURE);
            result.setMemo("取消失败..");
            result.setShippingCode(shippingCode);
            results.add(result);
            rs.setShippings(results);
        }
        return rs;
    }

    /**
     * 非销售类订单取消
     * 
     * @param orderCode
     * @return
     */
    public WmsOrderCancelResult wmsCancelOrderNotSales(String orderCode, String type) {
        WmsOrderCancelResult rs = new WmsOrderCancelResult();
        List<StockTransApplication> isStaExist = staDao.findBySlipCode(orderCode);
        List<WmsShippingResult> results = new ArrayList<WmsShippingResult>();
        // StockTransApplicationType type = null;
        if (isStaExist == null || isStaExist.size() == 0) {
            // 判断单据是否存在
            // throw new BusinessException(ErrorCode.STA_NOT_FOUND);
            rs.setMemo("取消失败");
            rs.setOrderCode(orderCode);
            WmsShippingResult result = new WmsShippingResult();
            result.setMemo("取消失败-单据未找到");
            result.setStatusCode(COLSE_FAILURE);
            results.add(result);
            rs.setShippings(results);
            return rs;
        } else {

            for (StockTransApplication sta : isStaExist) {
                if (!sta.getStatus().equals(StockTransApplicationStatus.CANCELED)) {
                    rs.setMemo("取消失败");
                    rs.setOrderCode(orderCode);
                    WmsShippingResult result = new WmsShippingResult();
                    result.setMemo("取消失败");
                    result.setStatusCode(COLSE_FAILURE);
                    results.add(result);
                    rs.setShippings(results);
                    return rs;
                }
            }
        }
        rs.setMemo("取消成功");
        rs.setOrderCode(orderCode);
        // for (StockTransApplication stockTransApplication : isStaExist) {
        WmsShippingResult result = new WmsShippingResult();
        result.setMemo("取消成功");
        result.setShippingCode(isStaExist.get(0).getCode());
        result.setStatusCode(COLSE_SUCCESS);
        results.add(result);
        rs.setShippings(results);

        return rs;
    }

    /**
     * 取消菜鸟的单据
     * 
     * @return
     * @throws Exception
     */
    public WmsOrderCancelResult cancelCaiNiaoOrderBySlipCode(String systemKey, String orderCode) throws Exception {
        WmsOrderCancelResult rs = new WmsOrderCancelResult();
        List<StockTransApplication> isStaExist = staDao.findBySlipCode(orderCode);

        if (isStaExist == null || isStaExist.size() == 0) {
            CnWmsStockInOrderNotify son = cnWmsStockInOrderNotifyDao.getByOrderCode(orderCode);
            CnOutOrderNotify on = cnOutOrderNotifyDao.getCnOutOrderNotifyByOrderCode(orderCode);
            if (son != null) {
                son.setStatus(-2);
                cnWmsStockInOrderNotifyDao.save(son);
                rs.setMemo("取消成功！");
                rs.setOrderCode(orderCode);
                rs.setStatus(1);
            } else if (on != null) {
                on.setStatus(-2);
                cnOutOrderNotifyDao.save(on);
                rs.setMemo("取消成功！");
                rs.setOrderCode(orderCode);
                rs.setStatus(1);
            } else {
                rs.setMemo("取消失败！");
                rs.setOrderCode(orderCode);
                rs.setStatus(0);
            }
        } else {
            StockTransApplication sta = isStaExist.get(0);
            if (sta.getType() == StockTransApplicationType.VMI_RETURN) {
                if (sta.getStatus() != StockTransApplicationStatus.CANCELED) {
                    wareHouseManagerCancel.cancelVmiTransferOutBound(sta.getId(), null);
                }
                rs.setMemo("取消成功！");
                rs.setOrderCode(orderCode);
                rs.setStatus(1);
            } else if (sta.getType() == StockTransApplicationType.VMI_INBOUND_CONSIGNMENT) {
                if (sta.getStatus() == StockTransApplicationStatus.CREATED) {
                    sta.setStatus(StockTransApplicationStatus.CANCELED);
                    staDao.save(sta);
                    rs.setMemo("取消成功！");
                    rs.setOrderCode(orderCode);
                    rs.setStatus(1);
                } else {
                    rs.setMemo("取消失败！");
                    rs.setOrderCode(orderCode);
                    rs.setStatus(0);
                }
            } else {
                rs = cancelSalesStaBySlipCode(orderCode);
                List<WmsShippingResult> srList = rs.getShippings();
                if (srList != null) {
                    WmsShippingResult sr = srList.get(0);
                    if (COLSE_SUCCESS.equals(sr.getStatusCode()) || COLSE_SUCCESS1.equals(sr.getStatusCode()) || COLSE_WAIT.equals(sr.getStatusCode())) {
                        rs.setStatus(1);
                    } else {
                        rs.setStatus(0);
                    }
                }
            }
        }
        return rs;
    }

    /**
     * 取消销售作业单 when cancel failed then throw exception
     * 
     * @param slipCode 相关单据号
     * @param type 单据类型 * @return true 允许取消，false 不允许取消, throw BusinessException 取消异常不能取消
     */
    public WmsOrderCancelResult cancelSalesStaBySlipCode(String slipCode) {
        AdvanceOrderAddInfo ad = null;
        WmsOrderCancelResult rs = new WmsOrderCancelResult();
        // 首先判断OMS系统订单取消，slipcode在WMS系统数据库中是否存在
        List<StockTransApplication> isStaExist = staDao.findBySlipCode(slipCode);
        List<WmsShippingResult> results = new ArrayList<WmsShippingResult>();
        StockTransApplicationType type = null;
        if (isStaExist == null || isStaExist.size() == 0) {
            // 判断单据是否存在
            //throw new BusinessException(ErrorCode.STA_NOT_FOUND);
            rs.setMemo("取消失败");
            rs.setOrderCode(slipCode);
            WmsShippingResult result = new WmsShippingResult();
            result.setMemo("取消失败-订单未找到");
            result.setStatusCode(COLSE_FAILURE);
            results.add(result);
            rs.setShippings(results);
            return rs;
        } else {
            for (StockTransApplication sta : isStaExist) {
                Boolean b = false;
                if ("1".equals(sta.getIsPreSale())) {// 是预售
                    b = true;
                    ad = advanceOrderAddInfoDao.findOrderInfoByOrderCode(slipCode, new BeanPropertyRowMapperExt<AdvanceOrderAddInfo>(AdvanceOrderAddInfo.class));
                }
                if (b && ((ad != null && ad.getStatus() == 5) || StockTransApplicationStatus.FINISHED.equals(sta.getStatus()))) {
                    rs.setMemo("取消失败");
                    rs.setOrderCode(slipCode);
                    WmsShippingResult result = new WmsShippingResult();
                    result.setMemo("取消失败-预定订单已发货或已完成");
                    result.setStatusCode(COLSE_FAILURE);
                    results.add(result);
                    rs.setShippings(results);
                    return rs;
                } else if (b && StockTransApplicationStatus.CHECKED.equals(sta.getStatus())) {
                    log.info(sta.getCode() + ",预售核对取消");
                }
                // 作业单配货中、已核对、已转出、已完成状态不能取消
                else if (!b && (StockTransApplicationStatus.CHECKED.equals(sta.getStatus()) || // 作业单已核对
                        StockTransApplicationStatus.INTRANSIT.equals(sta.getStatus()) || // 作业单已转出
                        StockTransApplicationStatus.FINISHED.equals(sta.getStatus()))) { // 作业单已完成
                    String brand=null;
                    if(StockTransApplicationStatus.CHECKED.equals(sta.getStatus())){
                        brand="作业单已核对";
                    }else if(StockTransApplicationStatus.INTRANSIT.equals(sta.getStatus())){
                        brand="作业单已转出";
                    }else if(StockTransApplicationStatus.FINISHED.equals(sta.getStatus())){
                        brand="作业单已完成";
                    }
                    rs.setMemo("取消失败");
                    rs.setOrderCode(slipCode);
                    WmsShippingResult result = new WmsShippingResult();
                    result.setMemo("取消失败-订单无法取消;"+brand);
                    result.setStatusCode(COLSE_FAILURE);
                    results.add(result);
                    rs.setShippings(results);
                    return rs;
                }
                // }
            }
            // 判断单据是否都取消成功
            boolean isAllCancel = isAllStaCancel(isStaExist);
            if (isAllCancel) {
                rs.setMemo("取消成功");
                rs.setOrderCode(slipCode);
                // for (StockTransApplication stockTransApplication : isStaExist) {
                WmsShippingResult result = new WmsShippingResult();
                result.setMemo("取消成功");
                result.setShippingCode(isStaExist.get(0).getCode());
                result.setStatusCode(COLSE_SUCCESS);
                results.add(result);
                rs.setShippings(results);
                // }

                return rs;
            } else {
                if (StockTransApplicationType.OUTBOUND_SALES.equals(isStaExist.get(0).getType())) {
                    type = StockTransApplicationType.OUTBOUND_SALES;
                } else if (StockTransApplicationType.INBOUND_RETURN_REQUEST.equals(isStaExist.get(0).getType()) || StockTransApplicationType.OUTBOUND_RETURN_REQUEST.equals(isStaExist.get(0).getType())) {
                    type = StockTransApplicationType.INBOUND_RETURN_REQUEST;
                } else {
                    throw new BusinessException(ErrorCode.STA_NOT_FOUND);
                }
            }
        }
        boolean status = staCancelManager.cancelSalesStaBySlipCode(slipCode, type);
        if (status) {
            rs.setMemo("取消成功");
            rs.setOrderCode(slipCode);
            WmsShippingResult result = new WmsShippingResult();
            result.setMemo("取消成功-订单取消成功");
            result.setStatusCode(COLSE_SUCCESS1);
            results.add(result);
            rs.setShippings(results);

            return rs;
        } else {
            rs.setMemo("等待确认");
            rs.setOrderCode(slipCode);
            WmsShippingResult result = new WmsShippingResult();
            result.setMemo("等待确认");
            result.setStatusCode(COLSE_WAIT);
            results.add(result);
            rs.setShippings(results);

            return rs;
        }
    }



    /**
     * 通过WMSCODE取消
     * 
     * @param shipping
     * @return
     */
    @Override
    public WmsOrderCancelResult cancelSalesStaByshippingCode(String shipping) {
        AdvanceOrderAddInfo ad = null;
        Boolean b = false;
        WmsOrderCancelResult rs = new WmsOrderCancelResult();
        // 首先判断OMS系统订单取消，slipcode在WMS系统数据库中是否存在
        StockTransApplication isStaExist = staDao.getByCode(shipping);
        List<WmsShippingResult> results = new ArrayList<WmsShippingResult>();
        if (isStaExist == null) {
            rs.setMemo("取消失败");
            WmsShippingResult result = new WmsShippingResult();
            result.setMemo("取消失败-订单未找到");
            result.setShippingCode(shipping);
            result.setStatusCode(FAILURE);
            results.add(result);
            rs.setShippings(results);
            return rs;
        } else {
            // 已核对、已完成状态不能取消
            if ("1".equals(isStaExist.getIsPreSale())) {// 是预售
                b = true;
                ad = advanceOrderAddInfoDao.findOrderInfoByOrderCode(isStaExist.getRefSlipCode(), new BeanPropertyRowMapperExt<AdvanceOrderAddInfo>(AdvanceOrderAddInfo.class));
            }
            if (b && ((ad != null && ad.getStatus() == 5) || StockTransApplicationStatus.FINISHED.equals(isStaExist.getStatus()))) {
                rs.setMemo("取消失败");
                rs.setOrderCode(isStaExist.getRefSlipCode());
                rs.setOrderType(isStaExist.getType().getValue());
                rs.setStatus(isStaExist.getStatus().getValue());
                WmsShippingResult result = new WmsShippingResult();
                result.setMemo("取消失败-预定订单已发货或已完成");
                result.setShippingCode(isStaExist.getCode());
                result.setStatusCode(COLSE_FAILURE);
                results.add(result);
                rs.setShippings(results);
                return rs;
            } else if (b && StockTransApplicationStatus.CHECKED.equals(isStaExist.getStatus())) {
                log.info(isStaExist.getCode() + ",预售核对取消");
            } else if (!b && (StockTransApplicationStatus.CHECKED.equals(isStaExist.getStatus()) || // 作业单已核对
                    StockTransApplicationStatus.FINISHED.equals(isStaExist.getStatus()) || StockTransApplicationStatus.INTRANSIT.equals(isStaExist.getStatus()) )) { // 作业单已完成
                String brand=null;
                if(StockTransApplicationStatus.CHECKED.equals(isStaExist.getStatus())){
                    brand="作业单已核对";
                }else if(StockTransApplicationStatus.INTRANSIT.equals(isStaExist.getStatus())){
                    brand="作业单已转出";
                }else if(StockTransApplicationStatus.FINISHED.equals(isStaExist.getStatus())){
                    brand="作业单已完成";
                }
                rs.setMemo("取消失败");
                rs.setOrderCode(isStaExist.getRefSlipCode());
                rs.setOrderType(isStaExist.getType().getValue());
                rs.setStatus(isStaExist.getStatus().getValue());
                WmsShippingResult result = new WmsShippingResult();
                result.setMemo("取消失败-订单无法取消;"+brand);
                result.setShippingCode(isStaExist.getCode());
                result.setStatusCode(COLSE_FAILURE);
                results.add(result);
                rs.setShippings(results);
                return rs;
            } 
           /* else if (!b && StockTransApplicationStatus.INTRANSIT.equals(isStaExist.getStatus())) { // 订单已转出
                rs.setMemo("取消失败");
                rs.setOrderCode(isStaExist.getRefSlipCode());
                rs.setOrderType(isStaExist.getType().getValue());
                rs.setStatus(isStaExist.getStatus().getValue());
                WmsShippingResult result = new WmsShippingResult();
                result.setMemo("取消失败-订单已发货");
                result.setShippingCode(isStaExist.getCode());
                result.setStatusCode(FAILURE1);
                results.add(result);
                rs.setShippings(results);
                return rs;
            }*/
            // 判断单据是否都取消成功
            boolean isAllCancel = isStaCancel(isStaExist);
            if (isAllCancel) {
                rs.setMemo("取消成功");
                rs.setOrderCode(isStaExist.getRefSlipCode());
                rs.setOrderType(isStaExist.getType().getValue());
                rs.setStatus(isStaExist.getStatus().getValue());
                WmsShippingResult result = new WmsShippingResult();
                result.setMemo("取消成功");
                result.setShippingCode(isStaExist.getCode());
                result.setStatusCode(COLSE_SUCCESS);
                results.add(result);
                rs.setShippings(results);
                return rs;
            } else {
                if (StockTransApplicationType.OUTBOUND_SALES.equals(isStaExist.getType())) {} else if (StockTransApplicationType.INBOUND_RETURN_REQUEST.equals(isStaExist.getType())
                        || StockTransApplicationType.OUTBOUND_RETURN_REQUEST.equals(isStaExist.getType())) {} else {
                    throw new BusinessException(ErrorCode.STA_NOT_FOUND);
                }
            }
        }
        boolean result = hubWmsManager.cancelSalesStaByshippingCode1(shipping);
        if (result) {
            rs.setMemo("取消成功");
            rs.setOrderType(21);
            rs.setOrderCode(isStaExist.getRefSlipCode());
            WmsShippingResult shippingResult = new WmsShippingResult();
            shippingResult.setMemo("取消成功");
            shippingResult.setShippingCode(shipping);
            shippingResult.setStatusCode(COLSE_SUCCESS1);
            results.add(shippingResult);
            rs.setShippings(results);
            return rs;
        } else {
            rs.setMemo("取消等待");
            rs.setOrderType(21);
            rs.setOrderCode(isStaExist.getRefSlipCode());
            WmsShippingResult shippingResult = new WmsShippingResult();
            shippingResult.setMemo("取消等待");
            shippingResult.setShippingCode(shipping);
            shippingResult.setStatusCode(COLSE_WAIT);
            results.add(shippingResult);
            rs.setShippings(results);
            return rs;
        }
    }


    private boolean isAllStaCancel(List<StockTransApplication> stas) {
        boolean isAllCancel = false;
        for (StockTransApplication sta : stas) {
            if (StockTransApplicationStatus.CANCEL_UNDO.equals(sta.getStatus()) || StockTransApplicationStatus.CANCELED.equals(sta.getStatus())) {
                isAllCancel = true;
            } else {
                isAllCancel = false;
                return isAllCancel;
            }
        }
        return isAllCancel;
    }

    private boolean isStaCancel(StockTransApplication sta) {
        boolean isAllCancel = false;
        if (StockTransApplicationStatus.CANCEL_UNDO.equals(sta.getStatus()) || StockTransApplicationStatus.CANCELED.equals(sta.getStatus())) {
            isAllCancel = true;
        } else {
            isAllCancel = false;
            return isAllCancel;
        }
        return isAllCancel;
    }

    @Override
    public Pagination<WmsOrderCancelResult> wmsCancelOrderConfirm(int start, int pagesize, String systemKey, Date starteTime, Date endTime) {
        if (log.isDebugEnabled()) {
            log.debug("wmsCancelOrderConfirm is start:" + starteTime + "----end" + endTime);
        }
        Pagination<WmsOrderCancelResult> orderCancelResult = hubWmsManager.wmsCancelOrderConfirm(start, pagesize, systemKey, starteTime, endTime);
        return orderCancelResult;
    }

    @Override
    public List<WmsRtnOrderResult> wmsRtnOrderService(String systemKey, List<WmsRtnOrder> rntOrder) {
        if (log.isDebugEnabled()) {
            log.debug("wmsRtnOrderService is");
        }
        List<WmsRtnOrderResult> baseResults = new ArrayList<WmsRtnOrderResult>();
        // 判断数据是否为空按
        if (rntOrder.size() == 0) {
            WmsRtnOrderResult result = new WmsRtnOrderResult();
            result.setStatus(0);
            result.setOrderCode(null);
            baseResults.add(result);
        }
        for (int i = 0; i < rntOrder.size(); i++) {
            try {
                if (rntOrder.get(i).getRntOutOrder() != null) {
                    WmsRtnOrderResult result = new WmsRtnOrderResult();
                    JSONArray jsonArray = JSONArray.fromObject(rntOrder.get(i));
                    WmsSalesOrderLog log = new WmsSalesOrderLog();
                    log.setCreateTime(new Date());
                    log.setJsonInfo(jsonArray.toString());
                    log.setOrderCode(rntOrder.get(i).getOrderCode());
                    logDao.save(log);
                    rntOrder.get(i).getRntOutOrder().setInvoices(rntOrder.get(i).getRtnInOrder().getNewinvoices());
                    WmsSalesOrderResult orderResult = wmsSalesOrderService(systemKey, rntOrder.get(i).getRntOutOrder());;

                    if (orderResult.getStatus() == 1) {
                        if (rntOrder.get(i).getRtnInOrder() != null) {
                            result = wmsRtnOrderCreate(systemKey, rntOrder.get(i));
                            baseResults.add(result);
                        }
                    } else {
                        result = new WmsRtnOrderResult();
                        result.setStatus(0);
                        result.setOrderCode(rntOrder.get(i).getOrderCode());
                        result.setMemo(orderResult.getMemo());
                        baseResults.add(result);
                    }
                } else {
                    WmsRtnOrderResult result = new WmsRtnOrderResult();
                    result = wmsRtnOrderCreate(systemKey, rntOrder.get(i));
                    baseResults.add(result);
                }
            } catch (Exception e) {
                log.error("", e);
            }
        }
        return baseResults;
    }

    @Override
    public Pagination<WmsOrderStatus> wmsOrderFinish(int start, int pagesize, String systemKey, Date starteTime, Date endTime) {
        Pagination<WmsOrderStatus> orderCancelResult = null;
        try {
            if (log.isDebugEnabled()) {
                log.debug("wmsOrderFinish is starte" + starteTime + "-----end" + endTime);
            }
            orderCancelResult = hubWmsManager.wmsOrderFinish(start, pagesize, systemKey, starteTime, endTime);
        } catch (Exception e) {
            log.error("", e);
        }

        return orderCancelResult;
    }

    @Override
    public WmsSalesOrderResult findTelephoneAndNameBySlipCode1(String slipCode1) {
        WmsSalesOrderResult result = new WmsSalesOrderResult();
        if (null != slipCode1 && !"".equals(slipCode1)) {
            List<StockTransApplication> list = staDao.findADBySlipCode1(slipCode1);
            result.setStatus(1);
            result.setMemo("成功");
            if (null != list && list.size() > 0) {
                StaDeliveryInfo staInfo = staDeliveryInfoDao.getByPrimaryKey(list.get(0).getId());
                if (null != staInfo) {
                    result.setOrderCode(staInfo.getMobile() == null ? staInfo.getTelephone() : staInfo.getMobile());
                    result.setOrderSource(staInfo.getReceiver());
                }
            } else {
                result.setStatus(0);
                result.setMemo("订单号不存在");
            }
        } else {
            result.setStatus(0);
            result.setMemo("订单号不能为空");
        }
        return result;
    }

    @Override
    public WmsRtnOrderResult wmsRtnOrderService(String systemKey, WmsRtnOrder rntOrder) {
        WmsRtnOrderResult result = new WmsRtnOrderResult();
        if (rntOrder == null) {
            result.setStatus(0);
            result.setOrderCode(null);
            String errorMsg = applicationContext.getMessage((ErrorCode.BUSINESS_EXCEPTION + ErrorCode.EI_ALLREADY_SUCCESS), new Object[] {}, Locale.SIMPLIFIED_CHINESE);
            result.setMemo(errorMsg);

        }
        if (rntOrder.getRntOutOrder() != null) {
            if (log.isDebugEnabled()) {
                log.debug("RntOutOrder is " + rntOrder.getRntOutOrder().getOrderCode());
            }
            rntOrder.getRntOutOrder().setWarehouseCode(rntOrder.getRefWarehouseCode());
            JSONArray jsonArray = JSONArray.fromObject(rntOrder);
            WmsSalesOrderLog log = new WmsSalesOrderLog();
            log.setCreateTime(new Date());
            log.setJsonInfo(jsonArray.toString());
            log.setOrderCode(rntOrder.getOrderCode());
            logDao.save(log);
            WmsSalesOrderResult orderResult = wmsSalesOrderService(systemKey, rntOrder.getRntOutOrder());;
            if (orderResult.getStatus() == 1) {
                if (rntOrder.getRtnInOrder() != null) {
                    result = wmsRtnOrderCreate(systemKey, rntOrder);
                    return result;
                }
            } else {
                result.setStatus(0);
                result.setOrderCode(rntOrder.getOrderCode());
                String errorMsg = applicationContext.getMessage((ErrorCode.BUSINESS_EXCEPTION + rntOrder.getOrderCode()), new Object[] {}, Locale.SIMPLIFIED_CHINESE);
                result.setMemo(errorMsg);
                return result;
            }

        } else {
            if (rntOrder.getRtnInOrder() != null) {
                if (log.isDebugEnabled()) {
                    log.debug("getRtnInOrder is " + rntOrder.getRtnInOrder().getOrderCode());
                }
                rntOrder.getRtnInOrder().setWarehouseCode(rntOrder.getRefWarehouseCode());
                JSONArray jsonArray = JSONArray.fromObject(rntOrder);
                WmsSalesOrderLog log = new WmsSalesOrderLog();
                log.setCreateTime(new Date());
                log.setJsonInfo(jsonArray.toString());
                log.setOrderCode(rntOrder.getOrderCode());
                logDao.save(log);
                // 菜鸟退库入库字段映射修改
                if (org.apache.commons.lang3.StringUtils.equals(systemKey, Constants.CAINIAO_DB_SYSTEM_KEY)) {
                    WmsRtnInOrder inOrder = rntOrder.getRtnInOrder();
                    inOrder.setOrderType(41);
                    inOrder.setOrderCode(rntOrder.getOrderCode());
                    List<WmsRtnOrderLine> rtnLines = inOrder.getRtnLines();
                    if (rtnLines != null && !rtnLines.isEmpty()) {
                        for (WmsRtnOrderLine rtnLine : rtnLines) {
                            Customer c = customerDao.getByCode(Constants.CAINIAO_DB_CUSTOMER_CODE);
                            Sku sku = skuDao.getByExtCode2AndCustomerId(rtnLine.getSku(), c.getId());
                            if (sku != null) {
                                rtnLine.setSku(sku.getCode());
                                rtnLine.setSkuName(sku.getName());
                            }
                            rtnLine.setOwner(inOrder.getOwner());
                            rtnLine.setLineNo(rtnLine.getLineNo());
                        }
                    }
                }
                result = wmsRtnOrderCreate(systemKey, rntOrder);
            }
        }


        return result;
    }

    public WmsRtnOrderResult wmsRtnOrderCreate(String systemKey, WmsRtnOrder order) {
        WmsRtnOrderResult bs = new WmsRtnOrderResult();
        try {
            // 校验基本信息
            wmsManager.validateOrderInfo(systemKey, order);
            // 查询中间表是否存在该单据信息
            WmsRtnInOrderQueue inOrderQueue = inOrderQueueDao.findStaByOrderCodeNotCancel(order.getOrderCode());
            if (inOrderQueue != null) {
                bs.setStatus(1);
                bs.setOrderCode(inOrderQueue.getOrderCode());
                String errorMsg = applicationContext.getMessage((ErrorCode.BUSINESS_EXCEPTION + ErrorCode.EI_ALLREADY_SUCCESS), new Object[] {}, Locale.SIMPLIFIED_CHINESE);
                bs.setMemo(errorMsg);
                return bs;
            }
            // Step1:校验商品是否存在
            validateSkuByCode(order.getRtnInOrder().getRtnLines());

            wmsManager.createStaByOrder(systemKey, order.getRtnInOrder());
            bs.setStatus(1);
            bs.setMemo("成功");
            bs.setOrderCode(order.getRtnInOrder().getOrderCode());
        } catch (BusinessException e) {
            log.error("error code : " + order.getOrderCode(), e);
            String errorMsg = "";
            bs.setOrderCode(order.getOrderCode());
            errorMsg += applicationContext.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs(), Locale.SIMPLIFIED_CHINESE);
            BusinessException current = e;
            while (current.getLinkedException() != null) {
                current = current.getLinkedException();
                errorMsg += applicationContext.getMessage(ErrorCode.BUSINESS_EXCEPTION + current.getErrorCode(), current.getArgs(), Locale.SIMPLIFIED_CHINESE);
            }
            bs.setStatus(0);
            bs.setMemo(errorMsg);
        } catch (Exception e) {
            log.error("wmsRtnOrderCreate is " + order.getOrderCode(), e);
            bs.setStatus(0);
            bs.setOrderCode(order.getOrderCode());
            String errorMsg = applicationContext.getMessage(ErrorCode.BUSINESS_EXCEPTION + ErrorCode.SYSTEM_ERROR, new Object[] {}, Locale.SIMPLIFIED_CHINESE);
            bs.setMemo(errorMsg);
        }
        return bs;

    }

    public void validateSkuByCode(List<WmsRtnOrderLine> lines) {
        List<String> codeList = new ArrayList<String>();
        Map<String, String> map = new HashMap<String, String>();
        for (WmsRtnOrderLine line : lines) {
            if (!StringUtils.hasText(line.getSku())) {
                // 存在商品为空的明细行！
                throw new BusinessException(ErrorCode.EI_EXISTS_NULL_SKU);
            }
            if (line.getQty() == null || line.getQty().compareTo(0L) <= 0) {
                throw new BusinessException(ErrorCode.EI_EXISTS_NULL_QTY);
            }
            map.put(line.getSku(), line.getSku());
        }
        codeList.addAll(map.values());
        List<String> errorList = new ArrayList<String>();
        for (String s : codeList) {
            Sku sku = skuDao.getByCode(s);
            if (sku == null) {
                errorList.add(s);
            }
        }
        if (errorList.size() > 0) {
            // 编码{0}对应的商品不存在！
            throw new BusinessException(ErrorCode.EI_SKU_NOTEXISTS, new Object[] {errorList});
        }
    }


    /**
     * 校验：<br/>
     * 1、基础信息验证，仓库，渠道，商品存在性校验<br/>
     * 2、头上有仓库，行上没有仓库/全部跟头一致，不允许分仓<br/>
     * 3、
     */
    @Override
    public WmsSalesOrderResult wmsSalesOrderService(String systemKey, WmsSalesOrder order) {
        WmsSalesOrderResult result = new WmsSalesOrderResult();
        try {
            if (log.isDebugEnabled()) {
                log.debug("wmsSalesOrderService is" + order.getOrderCode());
            }
            hubWmsManager.wmsSalesOrder(systemKey, order);
            result.setStatus(1);
            result.setOrderCode(order.getOrderCode());
            result.setOrderSource(order.getOrderSource());
            result.setMemo(RECEIVE_SUCCESS);
            WmsSalesOrderQueue wmsSalesOrderQueue = wmsSalesOrderQueueDao.getOrderToSetFlagByOrderCode(order.getOrderCode());
            if (null != wmsSalesOrderQueue && null != wmsSalesOrderQueue.getBeginFlag() && null != wmsSalesOrderQueue.getCanFlag() && "1".equals(wmsSalesOrderQueue.getBeginFlag()) && wmsSalesOrderQueue.getCanFlag()) {
                createStaTaskManager.createTomsOrdersendToMq(wmsSalesOrderQueue.getId());
            }
        } catch (BusinessException e) {
            log.error("wmsSalesOrderService BusinessException:" + order.getOrderCode() + " " + e.getErrorCode());
            log.error("", e);
            String errorMsg = "";
            if (e.getErrorCode() != ErrorCode.ERROR_NOT_SPECIFIED) {
                errorMsg += applicationContext.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs(), Locale.SIMPLIFIED_CHINESE);
            }
            BusinessException current = e;
            while (current.getLinkedException() != null) {
                current = current.getLinkedException();
                errorMsg += applicationContext.getMessage(ErrorCode.BUSINESS_EXCEPTION + current.getErrorCode(), current.getArgs(), Locale.SIMPLIFIED_CHINESE);
            }
            result.setStatus(0);
            if (e.getErrorCode() == ErrorCode.HW_ORDER_EXISTS) {
                result.setStatus(1);
            }
            result.setOrderCode(order.getOrderCode());
            result.setOrderSource(order.getOrderSource());
            result.setMemo(errorMsg);
        } catch (Exception e) {
            log.error("wmsSalesOrderService Exception:" + order.getOrderCode(), e);
            result.setStatus(0);
            result.setOrderCode(order.getOrderCode());
            result.setOrderSource(order.getOrderSource());
            result.setMemo(applicationContext.getMessage(ErrorCode.BUSINESS_EXCEPTION + ErrorCode.PDA_SYS_ERROR, null, Locale.SIMPLIFIED_CHINESE));
        }

        return result;
    }

    @Override
    public List<WmsSalesOrderResult> wmsSalesOrderService(String systemKey, List<WmsSalesOrder> orders) {
        if (log.isDebugEnabled()) {
            log.debug("wmsSalesOrderService Begin-----------");
        }
        List<WmsSalesOrderResult> wrl = new ArrayList<WmsSalesOrderResult>();
        for (WmsSalesOrder wo : orders) {
            WmsSalesOrderResult result = wmsSalesOrderService(systemKey, wo);
            wrl.add(result);
        }
        return wrl;
    }

    @Override
    public Pagination<WmsConfirmOrder> wmsConfirmOrderService(int start, int pagesize, String systemKey, Date startTime, Date endTime) {
        if (log.isDebugEnabled()) {
            log.debug("wmsConfirmOrderService is start" + startTime + "-----------end" + endTime);
        }
        Pagination<WmsConfirmOrder> pl = wmsConfirmOrderQueueDao.getListByQueryCondition(start, pagesize, systemKey, startTime, endTime, new Sort[] {new Sort("id asc")}, new BeanPropertyRowMapper<WmsConfirmOrder>(WmsConfirmOrder.class));
        for (WmsConfirmOrder c : pl.getItems()) {
            List<WmsShipping> sl = wmsShippingQueueDao.findAllShippingById(c.getId(), new BeanPropertyRowMapper<WmsShipping>(WmsShipping.class));
            for (WmsShipping ws : sl) {
                List<WmsShippingLine> sll = wmsShippingLineQueueDao.findAllShippingLineById(ws.getId(), new BeanPropertyRowMapper<WmsShippingLine>(WmsShippingLine.class));
                ws.setLines(sll);
            }
            c.setShippings(sl);
        }
        return pl;
    }

    @Override
    public Pagination<WmsConfirmOrder> wmsConfirmOrderService(int start, int pagesize, String systemKey, Date startTime, Date endTime, Integer type) {
        if (log.isDebugEnabled()) {
            log.debug("wmsConfirmOrderService type is start" + startTime + "-----------end" + endTime);
        }
        Pagination<WmsConfirmOrder> pl = wmsConfirmOrderQueueDao.getListByQueryTypeCondition(start, pagesize, systemKey, startTime, endTime, type, new Sort[] {new Sort("id asc")}, new BeanPropertyRowMapper<WmsConfirmOrder>(WmsConfirmOrder.class));
        for (WmsConfirmOrder c : pl.getItems()) {
            List<WmsShipping> sl = wmsShippingQueueDao.findAllShippingById(c.getId(), new BeanPropertyRowMapper<WmsShipping>(WmsShipping.class));
            for (WmsShipping ws : sl) {
                List<WmsShippingLine> sll = wmsShippingLineQueueDao.findAllShippingLineById(ws.getId(), new BeanPropertyRowMapper<WmsShippingLine>(WmsShippingLine.class));
                ws.setLines(sll);
            }
            c.setShippings(sl);
        }
        return pl;
    }

    @Override
    public Pagination<WmsConfirmOrder> wmsConfirmRtnOrderService(int start, int pagesize, String systemKey, Date startTime, Date endTime) {
        if (log.isDebugEnabled()) {
            log.debug("wmsConfirmOrderService is start" + startTime + "-----------end" + endTime);
        }
        List<Integer> typeList = new ArrayList<Integer>();
        typeList.add(41);
        Pagination<WmsConfirmOrder> pl = wmsConfirmOrderQueueDao.getListByTypeCondition(start, pagesize, systemKey, startTime, endTime, typeList, new Sort[] {new Sort("id asc")}, new BeanPropertyRowMapper<WmsConfirmOrder>(WmsConfirmOrder.class));
        for (WmsConfirmOrder c : pl.getItems()) {
            StockTransApplication sta = staDao.findSta1ByOrderCode(c.getOrderCode(), typeList, new BeanPropertyRowMapperExt<StockTransApplication>(StockTransApplication.class));
            List<WmsOrderLine> lines = new ArrayList<WmsOrderLine>();
            if (null != sta) {
                c.setSourceOrderCode(sta.getSlipCode1());
                List<StaLineCommand> staLineCommandLists = staLineDao.findStaLineByOrderCode(sta.getId(), new BeanPropertyRowMapperExt<StaLineCommand>(StaLineCommand.class));
                for (StaLineCommand staLineCommandList : staLineCommandLists) {
                    WmsOrderLine wmsOrderLine = new WmsOrderLine();
                    wmsOrderLine.setLineNo(staLineCommandList.getLineNo());
                    lines.add(wmsOrderLine);
                }
            }
            c.setLines(lines);
        }
        return pl;
    }

    /**
     * 索赔数据同步接口
     * 
     * @param contactCode
     * @param systemKey
     * @param chList
     * @return
     */
    public List<WmsClaimResult> tomsClaimSync(String contactCode, String systemKey, List<ClaimHead> chList) {
        List<WmsClaimResult> wcrList = new ArrayList<WmsClaimResult>();
        if (chList != null && !chList.isEmpty()) {
            for (ClaimHead ch : chList) {
                try {
                    WmsClaimResult wcr = compensationManager.addCompensationBy(ch);
                    wcrList.add(wcr);

                } catch (Exception e) {
                    WmsClaimResult wcr = new WmsClaimResult();
                    wcr.setSource("toms");
                    wcr.setSystemCode(ch.getSystemCode());
                    wcr.setStatus(WmsClaimResult.ACCEPT_FAIL);
                    wcr.setMemo("系统异常，处理失败！");
                    wcrList.add(wcr);
                    log.error("claim system exception !!", e);
                }
            }
            return wcrList;
        } else {
            return null;
        }

    }

    /**
     * 理赔处理反馈接口
     * 
     * @param start
     * @param pagesize
     * @param systemKey
     * @param startTime
     * @param endTime
     * @return
     */
    public Pagination<ClaimProcess> receiveWmsClaimProcess(int start, int pagesize, String systemKey, Date startTime, Date endTime) {

        return compensationManager.findClaimProcessByParams(start, pagesize, startTime, endTime);
    }

    /**
     * 索赔结果反馈接口
     * 
     * @param start
     * @param pagesize
     * @param systemKey
     * @param startTime
     * @param endTime
     * @return
     */
    public Pagination<ClaimResult> receiveWmsClaimResult(int start, int pagesize, String systemKey, Date startTime, Date endTime) {

        return compensationManager.findClaimResultByParams(start, pagesize, startTime, endTime);
    }


    /**
     * 索赔处理确认接口
     * 
     * @param contactCode
     * @param systemKey
     * @param cpList
     * @return
     */
    public List<WmsClaimResult> tomsClaimProcessAffirm(String contactCode, String systemKey, List<ClaimProcessAffirm> cpList) {
        List<WmsClaimResult> wcrList = new ArrayList<WmsClaimResult>();
        if (cpList != null && !cpList.isEmpty()) {
            for (ClaimProcessAffirm ch : cpList) {
                try {
                    WmsClaimResult wcr = compensationManager.updateClaimAffirmStatus(ch);
                    wcrList.add(wcr);

                } catch (Exception e) {
                    WmsClaimResult wcr = new WmsClaimResult();
                    wcr.setSource("toms");
                    wcr.setSystemCode(ch.getSystemCode());
                    wcr.setStatus(WmsClaimResult.ACCEPT_FAIL);
                    wcr.setMemo("系统异常，处理失败！");
                    wcrList.add(wcr);
                    log.error("claim system exception !!", e);
                }
            }
            return wcrList;
        } else {
            return null;
        }
    }

    /**
     * 小家电3C商家强制入菜鸟仓接口
     * 
     * @param OmsTmallOrder
     * @return BaseResult
     */

    public BaseResult saveOMSTmalloutboundOrder(OmsTmallOrder order) {
        BaseResult ba = new BaseResult();
        if (null != order) {
            MsgOmstmallOrder omsOrder = new MsgOmstmallOrder();
            omsOrder.setSource("OMSTmall");
            omsOrder.setLpCode(order.getLogistic());
            omsOrder.setSlipCode(order.getSlipCode());
            omsOrder.setTrackingNo(order.getTrackingCode());
            omsOrder.setWeight(new BigDecimal(order.getWeight()));
            StockTransApplicationCommand sta = staDao.findOmsTamllStaCodeBySlipCode(order.getSlipCode(), new BeanPropertyRowMapperExt<StockTransApplicationCommand>(StockTransApplicationCommand.class));
            if (null != sta && null != sta.getCode() && !"".equals(sta.getCode())) {
                if (1 == sta.getIntStaStatus().intValue() || 2 == sta.getIntStaStatus().intValue()) {
                    MsgRtnOutbound out = new MsgRtnOutbound();
                    out.setStatus(DefaultStatus.CREATED);
                    out.setSource("OMSTmall");
                    out.setStaCode(sta.getCode());
                    out.setSlipCode(order.getSlipCode());
                    out.setLpCode(order.getLogistic());
                    out.setTrackingNo(order.getTrackingCode());
                    out.setWeight(new BigDecimal(order.getWeight()));
                    out.setCreateTime(new Date());
                    omsOrder.setStaCode(sta.getCode());
                    msgRtnDao.save(out);
                    msgRtnDao.flush();
                    ba.setStatus(BaseResult.STATUS_SUCCESS);
                    omsOrder.setIsOk(true);
                } else {
                    ba.setStatus(BaseResult.STATUS_SUCCESS);
                    omsOrder.setIsOk(true);
                }
            } else {
                ba.setStatus(BaseResult.STATUS_ERROR);
                omsOrder.setIsOk(false);
            }
            msgOmsDao.save(omsOrder);
            msgOmsDao.flush();
        }
        return ba;
    }

    /**
     * 商品接口
     * 
     * @param systemKey
     * @param Sku
     * @return BaseResult
     */
    @Override
    public BaseResult updateSku(String systemKey, WmsSku sku) {
        BaseResult ba = new BaseResult();
        try {
            if ("adidas".equals(systemKey)) {

                if ("1".equals(sku.getType())) {// 头(产品product)
                    if (null != sku) {
                        BlProduct p = new BlProduct();
                        p.setCode(sku.getSupplierCode());
                        p.setGender(sku.getExtProp2());
                        p.setCategory(sku.getCategory());
                        p.setBusinessSegment(sku.getExtProp1());
                        p.setUnitPrice(sku.getListPrice());
                        p.setOrigPrice(sku.getExtProp3());
                        p.setCreateTime(new Date());
                        String code = blProductDao.findSingelCode(sku.getSupplierCode(), new SingleColumnRowMapper<String>(String.class));
                        if (null == code) {
                            blProductDao.save(p);
                            ba.setStatus(BaseResult.STATUS_SUCCESS);
                        } else {
                            log.info("BlProduct code =" + sku.getSupplierCode() + ",已存在!");
                            ba.setStatus(BaseResult.STATUS_SUCCESS);
                            ba.setMsg("BlProduct code =" + sku.getSupplierCode() + ",已存在!");
                        }
                        blProductDao.flush();
                    }
                } else if ("5".equals(sku.getType())) {// 行(sku)
                    if (null != sku) {
                        BlSku s = new BlSku();
                        s.setpCode(sku.getSupplierCode());
                        s.setSkuCdBarcode(sku.getCode());
                        s.setTechnicalSize(sku.getExtProp4());
                        s.setSizeCd(sku.getSkuSize());
                        s.setBarcode(sku.getBarcode());
                        s.setCreateTime(new Date());
                        s.setStatus(1);
                        if (sku.getBarcode() != null) {// 根据barcode
                            BlSku blSku1 = blSkuDao.findSingelSkuCdBarcodeOrBarcode(null, sku.getBarcode(), new BeanPropertyRowMapper<BlSku>(BlSku.class));// 根据barcode
                            if (null == blSku1) {
                                blSkuDao.save(s);
                                ba.setStatus(BaseResult.STATUS_SUCCESS);
                            } else {
                                log.info("BlSku SkuCdBarcode =" + sku.getCode() + ",已存在!");
                                blSku1.setpCode(s.getpCode());
                                blSku1.setSkuCdBarcode(s.getSkuCdBarcode());
                                blSku1.setTechnicalSize(s.getTechnicalSize());
                                blSku1.setSizeCd(s.getSizeCd());
                                blSku1.setCreateTime(new Date());
                                blSku1.setStatus(1);
                                blSkuDao.save(blSku1);
                                createSku(blSku1.getBarcode());
                                ba.setStatus(BaseResult.STATUS_SUCCESS);
                            }
                        } else if (sku.getCode() != null) {
                            BlSku blSku2 = blSkuDao.findSingelSkuCdBarcodeOrBarcode(sku.getCode(), null, new BeanPropertyRowMapper<BlSku>(BlSku.class));// 根据barcode
                            if (null == blSku2) {
                                blSkuDao.save(s);
                                ba.setStatus(BaseResult.STATUS_SUCCESS);
                            } else {
                                log.info("BlSku SkuCdBarcode =" + blSku2.getSkuCdBarcode() + ",已存在!");
                                blSku2.setpCode(s.getpCode());
                                blSku2.setSkuCdBarcode(s.getSkuCdBarcode());
                                blSku2.setTechnicalSize(s.getTechnicalSize());
                                blSku2.setSizeCd(s.getSizeCd());
                                blSku2.setCreateTime(new Date());
                                blSku2.setStatus(1);
                                blSkuDao.save(blSku2);
                                createSku(blSku2.getSkuCdBarcode());
                                ba.setStatus(BaseResult.STATUS_SUCCESS);
                            }
                        } else {
                            log.info("SkuCdBarcode Barcode all null");
                            ba.setStatus(BaseResult.STATUS_ERROR);
                            ba.setMsg("SkuCdBarcode Barcode all null");
                        }
                        blSkuDao.flush();
                    }

                }
            } else {// 其他通用品牌
                HubSku hubSku = null;
                HubSku s = hubSkuDao.findOneHubSku(systemKey, sku.getCode(), new BeanPropertyRowMapper<HubSku>(HubSku.class));
                if (s == null) {
                    hubSku = new HubSku();
                } else {
                    hubSku = s;
                }
                hubSku.setBarCode(sku.getBarcode());
                hubSku.setBrand(systemKey);
                hubSku.setCategory(sku.getCategory());
                hubSku.setCreateTime(new Date());
                hubSku.setEnName(sku.getEnName());
                hubSku.setHeight(sku.getHeight());
                hubSku.setIsValid(sku.getIsValid());
                hubSku.setLength(sku.getLength());
                hubSku.setListPrice(sku.getListPrice());
                hubSku.setName(sku.getName());
                hubSku.setProductDate(sku.getProductDate());
                hubSku.setSkuCode(sku.getCode());
                hubSku.setSpType(sku.getSpType());
                hubSku.setStatus(1);// 没有创建
                hubSku.setStatusCode(sku.getStatusCode());
                hubSku.setSupplierCode(sku.getSupplierCode());
                hubSku.setWeight(sku.getWeight());
                hubSku.setWidth(sku.getWidth());
                hubSkuDao.save(hubSku);
                ba.setStatus(BaseResult.STATUS_SUCCESS);
            }
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.info("sku.getSupplierCode():" + sku.getSupplierCode() + "sku.getBarcode():" + sku.getBarcode() + "sku.getCode()" + sku.getCode(), e);
            }
            ba.setStatus(BaseResult.STATUS_ERROR);
            ba.setMsg("系统异常");

        }
        return ba;
    }

    public void createSku(String code) {
        List<BlSkuCommand> blSkus = blSkuDao.findNoBlSku(code, new BeanPropertyRowMapperExt<BlSkuCommand>(BlSkuCommand.class));
        for (BlSkuCommand blSkuCommand : blSkus) {
            if (blSkuCommand.getBarcode() == null) {
                blSkuCommand.setSkuCdBarcode(blSkuCommand.getSkuCdBarcode());
            } else {
                blSkuCommand.setSkuCdBarcode(blSkuCommand.getBarcode());
            }
            try {
                adidasTask.updateSku(blSkuCommand);
            } catch (Exception e) {
                if (log.isErrorEnabled()) {
                    log.error("adidas createSku error2..." + blSkuCommand.getId(), e);
                }
            }
        }
    }

    @Override
    public Pagination<WmsOrderStatus> wmsOrderFinishStatus(int start, int pagesize, String systemKey, Date starteTime, Date endTime, int type) {
        Pagination<WmsOrderStatus> orderCancelResult = new Pagination<WmsOrderStatus>();
        try {
            if (log.isDebugEnabled()) {
                log.debug("wmsOrderFinish is starte" + starteTime + "-----end" + endTime + "-----type" + type);
            }
            orderCancelResult = hubWmsManager.wmsOrderFinishStatus(start, pagesize, systemKey, starteTime, endTime, type);
        } catch (Exception e) {
            log.error("", e);
        }

        return orderCancelResult;
    }

    /**
     * adidas全量库存查询接口
     */
    @Override
    public Pagination<WmsAdidasTotalInventory> wmsTotalInventoryAdidasService(int start, int pagesize, String systemKey, Date starteTime, Date endTime) {
        Pagination<WmsAdidasTotalInventory> pl = null;
        try {
            if (log.isDebugEnabled()) {
                log.debug("wmsTotalInventoryAdidasService is start" + starteTime + "-----end" + endTime);
            }
            pl = adidasTotalInventoryDao.getAdidasTotalInventoryCondition(start, pagesize, systemKey, starteTime, endTime, new BeanPropertyRowMapper<WmsAdidasTotalInventory>(WmsAdidasTotalInventory.class));
        } catch (Exception e) {
            log.error("", e);
        }
        return pl;
    }

    /**
     * adidas 全量销售库存查询接口
     */
    @Override
    public Pagination<WmsAdidasSalesInventory> wmsSalesInventoryAdidasService(int start, int pagesize, String systemKey, Date starteTime, Date endTime) {
        Pagination<WmsAdidasSalesInventory> pl = null;
        try {
            if (log.isDebugEnabled()) {
                log.debug("wmsSalesInventoryAdidasService is start" + starteTime + "-----end" + endTime);
            }
            pl = adidasSalesInventoryDao.getAdidasSalesInventoryCondition(start, pagesize, systemKey, starteTime, endTime, new BeanPropertyRowMapper<WmsAdidasSalesInventory>(WmsAdidasSalesInventory.class));
        } catch (Exception e) {
            log.error("", e);
        }
        return pl;
    }


    @Override
    public Pagination<WmsAdidasSalesInventory> wmsSalesInventoryHubServiceAdd(int start, int pagesize, String systemKey, Date starteTime, Date endTime) {
        Pagination<WmsAdidasSalesInventory> pl = null;
        try {
            if (log.isDebugEnabled()) {
                log.debug("wmsSalesInventoryHubService is start" + starteTime + "-----end" + endTime);
            }
            pl = adidasSalesInventoryDao.getWmsSalesInventoryHubServiceCondition(start, pagesize, systemKey, starteTime, endTime, new BeanPropertyRowMapper<WmsAdidasSalesInventory>(WmsAdidasSalesInventory.class));
        } catch (Exception e) {
            log.error("", e);
        }
        return pl;
    }


    /**
     * reebok全量库存查询接口
     */
    @Override
    public Pagination<WmsReebokTotalInventory> wmsTotalInventoryReebokService(int start, int pagesize, String systemKey, Date starteTime, Date endTime) {
        Pagination<WmsReebokTotalInventory> pl = null;
        try {
            if (log.isDebugEnabled()) {
                log.debug("wmsTotalInventoryReebokService is start" + starteTime + "-----end" + endTime);
            }
            pl = reebokTotalInventoryDao.getReebokTotalInventoryCondition(start, pagesize, systemKey, starteTime, endTime, new BeanPropertyRowMapper<WmsReebokTotalInventory>(WmsReebokTotalInventory.class));
        } catch (Exception e) {
            log.error("", e);
        }
        return pl;
    }

    /**
     * reebok销售库存查询接口
     */
    @Override
    public Pagination<WmsReebokSalesInventory> wmsSalesInventoryReebokService(int start, int pagesize, String systemKey, Date starteTime, Date endTime) {
        Pagination<WmsReebokSalesInventory> pl = null;
        try {
            if (log.isDebugEnabled()) {
                log.debug("wmsSalesInventoryReebokService is start" + starteTime + "-----end" + endTime);
            }
            pl = reebokSalesInventoryDao.getReebokSalesInventoryCondition(start, pagesize, systemKey, starteTime, endTime, new BeanPropertyRowMapper<WmsReebokSalesInventory>(WmsReebokSalesInventory.class));
        } catch (Exception e) {
            log.error("", e);
        }
        return pl;
    }

    /**
     * gucci销售库存查询接口
     */
    @Override
    public Pagination<GucciSalesInvHub> wmsSalesInventoryGucciService(int start, int pagesize, String systemKey, Date startTime, Date endTime) {
        Pagination<GucciSalesInvHub> pl = null;
        try {
            if (log.isDebugEnabled()) {
                log.debug("wmsSalesInventoryGucciService is start" + startTime + "-----end" + endTime);
            }
            pl = gucciSalesInventoryDao.getGucciSalesInventoryCondition(start, pagesize, systemKey, startTime, endTime, new BeanPropertyRowMapper<GucciSalesInvHub>(GucciSalesInvHub.class));
        } catch (Exception e) {
            log.error("", e);
        }
        return pl;
    }

    /**
     * WMS3.0通知上位系统库存流水信息
     */
    @Override
    public Pagination<WmsSkuInventoryFlow> wmsSkuInventoryFlow(int start, int pagesize, String systemKey, String customerCode, Date startTime, Date endTime) {
        Pagination<WmsSkuInventoryFlow> p = null;
        try {
            if (log.isDebugEnabled()) {
                log.debug("wmsSkuInventoryFlow is start" + startTime + "-----end" + endTime + "-----customerCode:" + customerCode);
            }
            p = wmsSkuInventoryFlowDao.getWmsSkuInventoryFlow(start, pagesize, systemKey, customerCode, startTime, endTime, new BeanPropertyRowMapper<WmsSkuInventoryFlow>(WmsSkuInventoryFlow.class));
        } catch (Exception e) {
            log.error("WMS3.0通知上位系统库存流水信息异常！" + startTime.toString() + ":" + endTime.toString() + ":" + new Date().toString() + "-----customerCode:" + customerCode, e);
        }
        return p;
    }

    /**
     * WMS3.0可用客户信息推送hub
     */
    @Override
    public List<WmsCustomer> wmsCustomerList(String systemKey) {
        List<WmsCustomer> p = null;
        try {
            if (log.isDebugEnabled()) {
                log.debug("wmsAllAvailableCustomerInfo is start");
            }
            p = customerDao.getwmsAllAvailableCustomerInfo(new BeanPropertyRowMapper<WmsCustomer>(WmsCustomer.class));
            if (p != null) {
                log.info("wmsAllAvailableCustomerInfo size:" + p.size() + "");
            }
        } catch (Exception e) {
            log.error("WMS3.0可用客户信息推送hub异常！" + new Date().toString(), e);
        }
        return p;
    }


    @Override
    public Pagination<WmsOrderStatus> clientReturnCancel(int start, int pagesize, String systemKey, Date starteTime, Date endTime) {
        Pagination<WmsOrderStatus> orderCancelResult = new Pagination<WmsOrderStatus>();
        try {
            if (log.isDebugEnabled()) {
                log.debug("wmsOrderFinish is starte" + starteTime + "-----end" + endTime);
            }
            orderCancelResult = hubWmsManager.wmsOrderFinishStatus(start, pagesize, systemKey, starteTime, endTime, null);
        } catch (Exception e) {
            log.error("", e);
        }

        return orderCancelResult;
    }

    @Override
    public Pagination<WmsOrderStatus> wmsOrderCancel(int start, int pagesize, String systemKey, Date starteTime, Date endTime, List<Integer> type) {

        Pagination<WmsOrderStatus> wmsOrderStatus = new Pagination<WmsOrderStatus>();
        try {
            if (log.isDebugEnabled()) {
                log.debug("wmsOrderCancel is starte" + starteTime + "-----end" + endTime);
            }
            wmsOrderStatus = hubWmsManager.wmsOrderCancel(start, pagesize, systemKey, starteTime, endTime);
        } catch (Exception e) {
            log.error("wmsOrderCancel", e);
        }
        return wmsOrderStatus;
    }


    @Override
    public Pagination<WmsOrderStatus> wmsOrderFinish(int start, int pagesize, String systemKey, Date starteTime, Date endTime, List<Integer> type) {
        Pagination<WmsOrderStatus> orderCancelResult = new Pagination<WmsOrderStatus>();
        try {
            if (log.isDebugEnabled()) {
                log.debug("wmsOrderFinish is starte" + starteTime + "-----end" + endTime);
            }
            orderCancelResult = hubWmsManager.wmsOrderFinishByType(start, pagesize, systemKey, starteTime, endTime, type);
        } catch (Exception e) {
            log.error("", e);
        }

        return orderCancelResult;
    }



    @Override
    public String wmsCancelOrder(String systemKey, String json) {
        boolean b = false;
        String jsonCancel = "";
        log.error("wmsCancelOrder-->AD" + json);
        if (null != json && !"".equals(json)) {
            WmsOrderCancel wmsOrderCancel = new WmsOrderCancel();
            JSONObject jSONObject = JSONObject.fromObject(json);
            String orderCode = (String) jSONObject.get("orderCode");
            String channel = (String) jSONObject.get("channel");
            JSONArray jsonArray = jSONObject.getJSONArray("orderLineNos");
            List<WmsSalesOrderLine> lines = new ArrayList<WmsSalesOrderLine>();
            for (int i = 0; i < jsonArray.size(); i++) {
                WmsSalesOrderLine wmsSalesOrderLine = new WmsSalesOrderLine();
                String lineNo = jsonArray.getJSONObject(i).getString("lineNo");
                wmsSalesOrderLine.setLineNo(lineNo);
                lines.add(wmsSalesOrderLine);
            }
            if (null != lines && lines.size() > 0) { // 行取消
                if (null != orderCode && !"".equals(orderCode)) {
                    List<StockTransApplication> isStaExist = staDao.findBySlipCode1(orderCode);
                    // 预售订单不支持行取消
                    for (StockTransApplication stockTransApplication : isStaExist) {
                        if ("1".equals(stockTransApplication.getIsPreSale())) {
                            b = true;
                            break;
                        }
                    }
                    if (b) {
                        log.error("wmsCancelOrder-->YU_AD" + orderCode);
                        wmsOrderCancel.setOrderCode(orderCode);
                        wmsOrderCancel.setStatus(0);
                        wmsOrderCancel.setChannel(channel);
                    } else {
                        if (null != isStaExist && isStaExist.size() > 0) {
                            StockTransApplication sta = isStaExist.get(0);
                            PickingList pl = sta.getPickingList() == null ? null : pickingListDao.getByPrimaryKey(sta.getPickingList().getId());
                            // 修改为打印开始之前不允许取消
                            if (null != pl && pl.getStartPrint() != null && pl.getStartPrint()) {
                                wmsOrderCancel.setStatus(0);
                                wmsOrderCancel.setOrderCode(orderCode);
                                wmsOrderCancel.setChannel(channel);
                            } else {
                                if (null != pl && (pl.getIsHavePrint() == null ? false : pl.getIsHavePrint())) { // 配货中不让取消（拣货之后取消）
                                    String str = sta.getCtCode() == null ? "" : sta.getCtCode();
                                    for (WmsSalesOrderLine line : lines) {
                                        str = str + "," + line.getLineNo();
                                    }
                                    if (str.length() > 200) {
                                        str = str.substring(0, 199);
                                    }
                                    sta.setCtCode(str);
                                    wmsOrderCancel.setOrderCode(orderCode);
                                    wmsOrderCancel.setStatus(0);
                                } else { // 行取消 （拣货之前取消）
                                    wmsOrderCancel = hubWmsManager.cancelSalesStaByLineNo(lines, orderCode);
                                }
                            }
                        } else {
                            //ad行取消记录中间表
                            AdCancel adCancel = null;
                            for (WmsSalesOrderLine wmsSalesOrderLine : lines) {
                                adCancel = new AdCancel();
                                adCancel.setCreateTime(new Date());
                                adCancel.setLastUpdateTime(new Date());
                                adCancel.setSlipCode(orderCode);
                                adCancel.setLineNo(wmsSalesOrderLine.getLineNo());
                                adCancelDao.save(adCancel);
                            }
                            List<WmsOrderCancelLine> orderLineNos = new ArrayList<WmsOrderCancelLine>();
                            wmsOrderCancel.setChannel(channel);
                            wmsOrderCancel.setOrderCode(orderCode);
                            wmsOrderCancel.setStatus(0);
                            for (WmsSalesOrderLine line : lines) {
                                WmsOrderCancelLine wmsSalesOrderLine = new WmsOrderCancelLine();
                                wmsSalesOrderLine.setLineNo(line.getLineNo());
                                wmsSalesOrderLine.setStatus(0);
                                orderLineNos.add(wmsSalesOrderLine);
                            }
                            wmsOrderCancel.setOrderLineNos(orderLineNos);
                        }
                        if (null != wmsOrderCancel && !"".equals(wmsOrderCancel)) {
                            List<WmsOrderCancelLine> list = wmsOrderCancel.getOrderLineNos();
                            if (null != list && list.size() > 0) {
                                boolean flag = true;
                                for (WmsOrderCancelLine wmsOrderCancelLine : list) {
                                    if (wmsOrderCancelLine.getStatus() == 0) {
                                        flag = false;
                                        break;
                                    }
                                }
                                if (null == wmsOrderCancel.getStatus()) {
                                    if (flag) {
                                        wmsOrderCancel.setStatus(1);
                                    } else {
                                        wmsOrderCancel.setStatus(0);
                                    }
                                }
                            }
                            wmsOrderCancel.setChannel(channel);
                        }
                    }
                }
                JSONArray jsonArr = JSONArray.fromObject(wmsOrderCancel);
                jsonCancel = jsonArr.toString();
                return jsonCancel;
            } else { // 整单取消
                List<StockTransApplication> isStaExist = staDao.findBySlipCode1(orderCode);
                if (isStaExist != null && isStaExist.size() > 0) {
                    StockTransApplication sta = isStaExist.get(0);
                    PickingList pl = sta.getPickingList() == null ? null : pickingListDao.getByPrimaryKey(sta.getPickingList().getId());
                    // 修改为打印开始之前不允许取消
                    if (null != pl && pl.getStartPrint() != null && pl.getStartPrint()) {
                        wmsOrderCancel.setStatus(0);
                        wmsOrderCancel.setOrderCode(orderCode);
                        wmsOrderCancel.setChannel(channel);
                    } else {
                        WmsOrderCancelResult rs = hubWmsManager.cancelSalesSta(systemKey, orderCode, true);
                        List<WmsShippingResult> list = rs.getShippings();
                        if (null != list && list.size() > 0) {
                            boolean flag = false;
                            for (WmsShippingResult wmsShippingResult : list) {
                                if (wmsShippingResult.getStatusCode().equals(COLSE_SUCCESS) || wmsShippingResult.getStatusCode().equals(COLSE_SUCCESS1)) {
                                    flag = true;
                                }
                            }
                            if (flag) {
                                wmsOrderCancel.setStatus(1);
                            } else {
                                wmsOrderCancel.setStatus(0);
                            }
                        } else {
                            wmsOrderCancel.setStatus(0);
                        }
                        wmsOrderCancel.setOrderCode(orderCode);
                        wmsOrderCancel.setChannel(channel);
                    }
                } else {
                    wmsOrderCancel.setStatus(0);
                    wmsOrderCancel.setOrderCode(orderCode);
                    wmsOrderCancel.setChannel(channel);
                }
                JSONArray jsonArr = JSONArray.fromObject(wmsOrderCancel);
                jsonCancel = jsonArr.toString();
                return jsonCancel;
            }

        }
        return jsonCancel;
    }

    /**
     * esprit o2o 提供给hub来获取数据生成oto给品牌反馈文件
     */
    // staType:102 转店退仓 81：vmi入库 type（收货）：0 收货 1：收货关闭
    @Override
    public EspDeliveryResponse findDeliveries(int staType, String type, Date startTime) {
        List<EspDeliveryCommand> data = new ArrayList<EspDeliveryCommand>();
        EspDeliveryResponse response = new EspDeliveryResponse();
        try {
            // ///////////////////////////////////////start
            // 转店退仓,vmi入库 收货反馈
            if ((staType == 102 && "0".equals(type)) || (staType == 81 && "0".equals(type))) {
                List<ESPDeliveryCommand> esList = espDeliveryDao.findAllDeliveryDatasGroupByStaCode3(staType, new BeanPropertyRowMapperExt<ESPDeliveryCommand>(ESPDeliveryCommand.class));
                EspDeliveryCommand en = null;
                for (ESPDeliveryCommand espCmd : esList) {
                    try {
                        en = outBoundDeliveryRtn2(espCmd.getStaCode());
                        if (en != null) {
                            data.add(en);
                            espDeliveryDao.updateStatusByStaCode(ESPDelivery.STATUS_END, espCmd.getStaCode(), en.getBatchCode());
                            String a = null;
                            try {
                                a = en.getDeliveries().get(0).getDeliveryNo();
                                espDeliveryDao.updateStatusByStaCode3(null, espCmd.getStaCode(), en.getBatchCode(), a, String.valueOf(en.getSequenceNumber()));
                            } catch (Exception e) {
                                if (log.isErrorEnabled()) {
                                    log.error("esprit findDeliveries error...", e);
                                }
                            }

                            log.info("findDeliveries_01:" + espCmd.getStaCode() + "," + en.getBatchCode() + "," + en.getSequenceNumber() + "," + a);
                        }
                    } catch (Exception e) {
                        log.error("findDeliveries,staType,type" + staType + "," + type + ";" + espCmd.getStaCode());
                    }

                }
                response.setData(data);
            } else if (staType == 81 && "1".equals(type)) {// vmi入库 收货关闭 反馈
                // List<ESPDeliveryCommand> esList =
                // espDeliveryDao.findCloseDeliveryDatasGroupByStaCode2(new
                // BeanPropertyRowMapperExt<ESPDeliveryCommand>(ESPDeliveryCommand.class));
                // EspDeliveryCommand en = null;
                // for (ESPDeliveryCommand espCmd : esList) {
                // try {
                // en = inBoundDeliveryCloseRtn02(espCmd.getStaCode());
                // if (en != null) {
                // data.add(en);
                // espDeliveryDao.updateStatusByClosedStaCode(ESPDelivery.STATUS_END,
                // espCmd.getStaCode(), en.getBatchCode());
                // String a = null;
                // try {
                // a = en.getDeliveries().get(0).getDeliveryNo();
                // espDeliveryDao.updateStatusByStaCode3(null, espCmd.getStaCode(),
                // en.getBatchCode(),
                // a, String.valueOf(en.getSequenceNumber()));
                // } catch (Exception e) {}
                // log.info("findDeliveries_02:" + espCmd.getStaCode() + "," + en.getBatchCode() +
                // "," +
                // en.getSequenceNumber() + "," + a);
                // }
                // } catch (Exception e) {
                // log.error("findDeliveries2,staType,type" + staType + "," + type + ";" +
                // espCmd.getStaCode());
                // }
                // }
                response.setData(data);
            } else {
                response.setData(data);
            }
            // ///////////////////////////////////////end
        } catch (BusinessException e) {
            if (log.isErrorEnabled()) {
                log.error("findDeliveries 入参startTime = [{}],startTime = [{}],错误信息：[{}]", startTime.toString(), "", e);
            }
            response.setResult("");
            response.setErrorCode(String.valueOf(e.getErrorCode()));
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            log.error("findDeliveries2 入参startTime = [{}],startTime = [{}],错误信息：[{}]", startTime.toString(), "");
            log.error(e.getMessage(), e);
            response.setResult("系统异常");
            response.setErrorCode("");
            response.setMessage(e.getMessage());
        }
        return response;
    }

    private String saveOrQueryCodeByCompanyGLN(String pre, String companyGLN, int seqInitLength, String startWith) {// 获取sequenceNumber、deliveryNo生成
        String code;
        String entityMark = null;
        try {
            entityMark = pre + companyGLN;
            // code = codeGeneratorService.generateCode(entityMark);
            code = this.codeManager.generateCode(NotifyConstants.CUSTOM_CODE, entityMark, null, null, null);
        } catch (com.baozun.scm.baseservice.sac.exception.BusinessException e) {
            if (e.getErrorCode() == 60001) {
                CodeSequence codeSequence = new CodeSequence();
                codeSequence.setCustomer(NotifyConstants.CUSTOM_CODE);
                codeSequence.setEntityMark(entityMark);
                codeSequence.setIsContinuous(true);
                codeSequence.setStartWith(startWith);
                codeSequence.setVal(0);
                if (startWith != null) {
                    codeSequence.setSeqInitLength(seqInitLength);
                }
                codeSequence.setResetType(0);
                this.codeService.saveOrUpdate(codeSequence);

                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e1) {
                    if (log.isErrorEnabled()) {
                        log.error("saveOrQueryCodeByCompanyGLN:" + entityMark, e1);
                    }
                }
                // code = codeGeneratorService.generateCode(entityMark);
                code = this.codeManager.generateCode(NotifyConstants.CUSTOM_CODE, entityMark, null, null, null);
            } else {
                throw new BusinessException("");
            }
        } catch (Exception e) {
            throw new BusinessException("");
        }
        return code;
    }


    public EspDeliveryCommand inBoundDeliveryCloseRtn02(String staCode) {
        StockTransApplication sta = staDao.getByCode(staCode);
        List<ESPDelivery> epList = espDeliveryDao.getCloseDeliveryDatasByStaCode(staCode);
        boolean hasBatchNo = true;
        String batchCode = "";
        String batchNo = "";
        for (ESPDelivery ep : epList) {
            String bNo = ep.getBatchCodes();
            if (StringUtil.isEmpty(bNo)) {
                hasBatchNo = false;
            } else {
                if (!StringUtil.isEmpty(bNo)) {
                    batchNo = bNo;
                }
            }
        }
        if (false == hasBatchNo) {
            BigDecimal batchId = espDeliveryDao.findBatchId(new SingleColumnRowMapper<BigDecimal>(BigDecimal.class));
            batchCode = "wms_" + batchId.toString();
            for (ESPDelivery ep : epList) {
                Long id = ep.getId();
                if (null != id) {
                    ESPDelivery epd = espDeliveryDao.getByPrimaryKey(id);
                    if (StringUtil.isEmpty(epd.getBatchCodes())) {
                        epd.setBatchCodes(batchCode);
                        epd.setVersion(new Date());
                        espDeliveryDao.save(epd);
                    }
                }
            }
            espDeliveryDao.flush();
        } else {
            batchCode = batchNo;
        }
        batchCode = (StringUtil.isEmpty(batchCode) ? null : batchCode);
        if (null == batchCode) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        Date transDate = new Date();
        List<ESPDeliveryCommand> list = espDeliveryDao.findCloseDeliveryDatasByStaCode(staCode, batchCode, new BeanPropertyRowMapperExt<ESPDeliveryCommand>(ESPDeliveryCommand.class));
        List<ESPDeliveryCommand> list2 = espDeliveryDao.findDeliveryDatasByStaCode2(staCode, batchCode, new BeanPropertyRowMapperExt<ESPDeliveryCommand>(ESPDeliveryCommand.class));
        Long skuQtys = 0L;
        String deliveryNo = "";
        for (ESPDeliveryCommand esp : list) {
            String qty = esp.getItemReceivedQTY();
            skuQtys += (StringUtils.isEmpty(qty) ? 0L : Long.valueOf(qty));
            if (!StringUtils.isEmpty(esp.getDeliveryDeliveryNO()) && "".equals(deliveryNo)) {
                deliveryNo = esp.getDeliveryDeliveryNO();
            }
        }
        // 合并重复明细
        List<ESPDeliveryCommand> espList = new ArrayList<ESPDeliveryCommand>();
        for (ESPDeliveryCommand esp : list) {
            ESPDeliveryCommand cmd = esp;
            cmd.setHeaderNumberofRecords(String.valueOf(skuQtys));
            cmd.setDeliveryDeliveryNO(deliveryNo);
            if (espList.size() > 0) {
                boolean isExist = false;
                String extCode2 = cmd.getItemSku();
                if (StringUtil.isEmpty(extCode2)) {
                    throw new BusinessException(ErrorCode.VMI_SKU_CODE_ERROR);
                }
                ListIterator<ESPDeliveryCommand> iter = espList.listIterator();
                while (iter.hasNext()) {
                    ESPDeliveryCommand c = iter.next();
                    if (!StringUtils.isEmpty(extCode2)) {
                        if (extCode2.equals(c.getItemSku())) {
                            isExist = true;
                            Long oldQty = (StringUtils.isEmpty(c.getItemReceivedQTY()) ? 0L : Long.valueOf(c.getItemReceivedQTY()));
                            Long newQty = (StringUtils.isEmpty(cmd.getItemReceivedQTY()) ? 0L : Long.valueOf(cmd.getItemReceivedQTY()));
                            Long reQty = oldQty + newQty;
                            c.setItemReceivedQTY(String.valueOf(reQty));
                            iter.set(c);
                            break;
                        }
                    }
                }
                if (false == isExist) {
                    espList.add(cmd);
                }
            } else {
                espList.add(cmd);
            }
        }
        EspDeliveryCommand en = outBoundDeliveryData(espList, sta, transDate, "0", list2);
        en.setBatchCode(batchCode);
        return en;
    }

    private EspDeliveryCommand outBoundDeliveryRtn2(String staCode) {// 封装hub所需要的数据 01
        StockTransApplication sta = staDao.getByCode(staCode);
        List<ESPDelivery> epList = espDeliveryDao.getDeliveryDatasByStaCode(staCode);
        boolean hasBatchNo = true;
        String batchCode = "";
        String batchNo = "";
        for (ESPDelivery ep : epList) {
            String bNo = ep.getBatchCodes();
            if (StringUtil.isEmpty(bNo)) {
                hasBatchNo = false;
            } else {
                if (!StringUtil.isEmpty(bNo)) {
                    batchNo = bNo;
                }
            }
        }
        if (false == hasBatchNo) {
            BigDecimal batchId = espDeliveryDao.findBatchId(new SingleColumnRowMapper<BigDecimal>(BigDecimal.class));
            batchCode = "wms_" + batchId.toString();
            for (ESPDelivery ep : epList) {
                Long id = ep.getId();
                if (null != id) {
                    ESPDelivery epd = espDeliveryDao.getByPrimaryKey(id);
                    if (StringUtil.isEmpty(epd.getBatchCodes())) {
                        epd.setBatchCodes(batchCode);
                        epd.setVersion(new Date());
                        espDeliveryDao.save(epd);
                    }
                }
            }
            espDeliveryDao.flush();
        } else {
            batchCode = batchNo;
        }
        batchCode = (StringUtil.isEmpty(batchCode) ? null : batchCode);
        if (null == batchCode) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        Date transDate = new Date();
        // espDeliveryDao.findDeliveryDatasByStaCode(staCode, batchCode, new
        // BeanPropertyRowMapperExt<ESPDeliveryCommand>(ESPDeliveryCommand.class));
        List<ESPDeliveryCommand> list = espDeliveryDao.findDeliveryDatasByStaCode(staCode, batchCode, new BeanPropertyRowMapperExt<ESPDeliveryCommand>(ESPDeliveryCommand.class));
        List<ESPDeliveryCommand> list2 = espDeliveryDao.findDeliveryDatasByStaCode2(staCode, batchCode, new BeanPropertyRowMapperExt<ESPDeliveryCommand>(ESPDeliveryCommand.class));
        Long skuQtys = 0L;
        String deliveryNo = "";
        for (ESPDeliveryCommand esp : list) {
            String qty = esp.getItemReceivedQTY();
            skuQtys += (StringUtils.isEmpty(qty) ? 0L : Long.valueOf(qty));
            if (!StringUtils.isEmpty(esp.getDeliveryDeliveryNO()) && "".equals(deliveryNo)) {
                deliveryNo = esp.getDeliveryDeliveryNO();
            }
        }
        if (0 == skuQtys) {
            return null;
        }
        // 合并重复明细
        List<ESPDeliveryCommand> espList = new ArrayList<ESPDeliveryCommand>();
        for (ESPDeliveryCommand esp : list) {
            ESPDeliveryCommand cmd = esp;
            cmd.setHeaderNumberofRecords(String.valueOf(skuQtys));
            cmd.setDeliveryDeliveryNO(deliveryNo);
            if (espList.size() > 0) {
                boolean isExist = false;
                String extCode2 = cmd.getItemSku();
                if (StringUtil.isEmpty(extCode2)) {
                    throw new BusinessException(ErrorCode.VMI_SKU_CODE_ERROR);
                }
                ListIterator<ESPDeliveryCommand> iter = espList.listIterator();
                while (iter.hasNext()) {
                    ESPDeliveryCommand c = iter.next();
                    if (!StringUtils.isEmpty(extCode2)) {
                        if (extCode2.equals(c.getItemSku())) {
                            isExist = true;
                            Long oldQty = (StringUtils.isEmpty(c.getItemReceivedQTY()) ? 0L : Long.valueOf(c.getItemReceivedQTY()));
                            Long newQty = (StringUtils.isEmpty(cmd.getItemReceivedQTY()) ? 0L : Long.valueOf(cmd.getItemReceivedQTY()));
                            Long reQty = oldQty + newQty;
                            c.setItemReceivedQTY(String.valueOf(reQty));
                            iter.set(c);
                            break;
                        }
                    }
                }
                if (false == isExist) {
                    espList.add(cmd);
                }
            } else {
                espList.add(cmd);
            }
        }
        EspDeliveryCommand en = outBoundDeliveryData(espList, sta, transDate, "1", list2);
        en.setBatchCode(batchCode);
        return en;
    }


    private EspDeliveryCommand outBoundDeliveryData(List<ESPDeliveryCommand> list, StockTransApplication sta, Date transDate, String type, List<ESPDeliveryCommand> list2) {// 封装hub所需要的数据02

        EspritStoreCommand es = espritStoreDao.findEspritEn(null, sta.getCtCode(), null, null, null, new BeanPropertyRowMapperExt<EspritStoreCommand>(EspritStoreCommand.class));

        if (sta.getType().getValue() == 81) {// 81
        } else {// 102
            if (es == null) {
                log.error("findEspritEn en 为空" + sta.getCtCode());
                throw new BusinessException("findEspritEn en 为空:" + sta.getCtCode());
            }
        }

        // 封装数据 S
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        EspDeliveryCommand en = new EspDeliveryCommand();
        String fromGLN = list.get(0).getHeaderFromgln();
        String toGLN = list.get(0).getHeaderTogln();
        String fromNode = list.get(0).getHeaderFromnode();
        String toNode = list.get(0).getHeaderTonode();
        String sequenceNumber = list2.get(0).getRemark2();// 看原来的序列是否为空 如果不为空则还是用这个序列 为空 重新获取
        en.setFromGLN(fromGLN);
        en.setToGLN(toGLN);
        en.setFromNode(fromNode);
        en.setToNode(toNode);
        en.setNumberOfRecords(Integer.valueOf(type));
        en.setGenerationDateTime(transDate);
        List<EspDeliveryLineCommand> espDeliveryLineCommands = new ArrayList<EspDeliveryLineCommand>();
        EspDeliveryLineCommand espDeliveryLineCommand = new EspDeliveryLineCommand();

        if (sta.getType().getValue() == StockTransApplicationType.VMI_TRANSFER_RETURN.getValue()) {// 转店
            espDeliveryLineCommand.setDeliveryType("P");
            espDeliveryLineCommand.setDeliveryStatus("D");
        } else if (sta.getType().getValue() == StockTransApplicationType.VMI_INBOUND_CONSIGNMENT.getValue()) {// 入库
            // 反馈
            en.setFromNode(toNode);
            en.setToNode(fromNode);
            en.setFromGLN(toGLN);
            en.setToGLN(fromGLN);
            espDeliveryLineCommand.setDeliveryType("R");
            espDeliveryLineCommand.setDeliveryStatus("A");
        }
        // espDeliveryLineCommand.setDeliveryType("P");// oto转店
        // espDeliveryLineCommand.setDeliveryStatus("D");// oto转店
        espDeliveryLineCommand.setDeliveryDate(sdf.format(transDate));
        espDeliveryLineCommand.setGoodsReceiptDate(espDeliveryLineCommand.getDeliveryDate());
        if (sta.getType().getValue() == 81) {// 81
            espDeliveryLineCommand.setDeliveryNo(list.get(0).getDeliveryDeliveryNO());// 收货
        } else {// 102
            espDeliveryLineCommand.setDeliveryNo(this.saveOrQueryCodeByCompanyGLN(EspritConstants.ESP_STORE, es.getGln(), 6, EspritConstants.START_WITH));// 收货
        }
        String dFromGLN = (StringUtils.isEmpty(list.get(0).getDeliveryDeliveredfromGLN()) ? sta.getFromLocation() : list.get(0).getDeliveryDeliveredfromGLN());
        String dToGLN = (StringUtils.isEmpty(list.get(0).getDeliveryDeliveredtoGLN()) ? sta.getToLocation() : list.get(0).getDeliveryDeliveredtoGLN());
        espDeliveryLineCommand.setDeliveredFromGLN(dFromGLN);
        espDeliveryLineCommand.setDeliveredToGLN(dToGLN);
        List<EspDeliveryItem> items = new ArrayList<EspDeliveryItem>();
        EspDeliveryItem item = null;
        for (ESPDeliveryCommand r : list) {
            ESPDeliveryCommand cmd = r;
            item = new EspDeliveryItem();
            String skuCode = cmd.getItemSku();
            if (StringUtil.isEmpty(skuCode)) {
                throw new BusinessException(ErrorCode.VMI_SKU_CODE_ERROR);
            }
            item.setSku(skuCode);
            item.setReceivedQty(Integer.parseInt(cmd.getItemReceivedQTY()));
            items.add(item);
        }
        espDeliveryLineCommand.setItems(items);
        espDeliveryLineCommands.add(espDeliveryLineCommand);
        en.setDeliveries(espDeliveryLineCommands);
        if (sta.getType().getValue() == 81) {// 81
            if (StringUtil.isEmpty(sequenceNumber)) {
                en.setSequenceNumber(Integer.valueOf(this.saveOrQueryCodeByCompanyGLN(EspritConstants.ESP_COMPANY, fromGLN, 0, null)));
            } else {
                en.setSequenceNumber(Integer.valueOf(sequenceNumber));
            }
        } else {// 102
            if (StringUtil.isEmpty(sequenceNumber)) {
                en.setSequenceNumber(Integer.valueOf(this.saveOrQueryCodeByCompanyGLN(EspritConstants.ESP_COMPANY, es.getCityGln(), 0, null)));
            } else {
                en.setSequenceNumber(Integer.valueOf(sequenceNumber));
            }
        }

        // 封装数据 E
        return en;
    }

    /**
     * WMS3.0无前置单号的占用数据推送hub
     */
    @Override
    public Pagination<WmsOccupiedAndRelease> wmsNoPreOrderOccupiedData(int start, int pagesize, String systemKey, String customerCode, String owner, Date startTime, Date endTime) {
        Pagination<WmsOccupiedAndRelease> p = null;
        try {
            if (log.isDebugEnabled()) {
                log.debug("WmsOccupied is start:" + startTime + "-----end:" + endTime + "-----customerCode:" + customerCode + "-----owner:" + owner);
            }
            p = wmsIMOccupiedAndReleaseDao.getWmsIMOccupiedAndRelease(start, pagesize, systemKey, customerCode, owner, 0, startTime, endTime, new BeanPropertyRowMapper<WmsOccupiedAndRelease>(WmsOccupiedAndRelease.class));
            // if (p != null && p.getItems() != null && !p.getItems().isEmpty()) {
            // for (WmsOccupiedAndRelease r : p.getItems()) {
            // WmsIMOccupiedAndRelease w = wmsIMOccupiedAndReleaseDao.getByPrimaryKey(r.getId());
            // if (w != null) {
            // w.setStatus(1);
            // wmsIMOccupiedAndReleaseDao.save(w);
            // }
            // }
            // }
        } catch (Exception e) {
            log.error("WMS3.0通知上位系统占用数据信息异常！" + startTime.toString() + ":" + endTime.toString() + ":" + new Date().toString() + "-----customerCode:" + customerCode + "-----owner:" + owner, e);
        }
        return p;
    }

    /**
     * WMS3.0无前置单号的取消释放数据推送hub
     */
    @Override
    public Pagination<WmsOccupiedAndRelease> wmsNoPreOrderReleasedData(int start, int pagesize, String systemKey, String customerCode, String owner, Date startTime, Date endTime) {
        Pagination<WmsOccupiedAndRelease> p = null;
        try {
            if (log.isDebugEnabled()) {
                log.debug("WmsRelease is start:" + startTime + "-----end:" + endTime + "-----customerCode:" + customerCode + "-----owner:" + owner);
            }
            p = wmsIMOccupiedAndReleaseDao.getWmsIMOccupiedAndRelease(start, pagesize, systemKey, customerCode, owner, 1, startTime, endTime, new BeanPropertyRowMapper<WmsOccupiedAndRelease>(WmsOccupiedAndRelease.class));
            // if (p != null && p.getItems() != null && !p.getItems().isEmpty()) {
            // for (WmsOccupiedAndRelease r : p.getItems()) {
            // WmsIMOccupiedAndRelease w = wmsIMOccupiedAndReleaseDao.getByPrimaryKey(r.getId());
            // if (w != null) {
            // w.setStatus(1);
            // wmsIMOccupiedAndReleaseDao.save(w);
            // }
            // }
            // }
        } catch (Exception e) {
            log.error("WMS3.0通知上位系统取消释放数据信息异常！" + startTime.toString() + ":" + endTime.toString() + ":" + new Date().toString() + "-----customerCode:" + customerCode + "-----owner:" + owner, e);
        }
        return p;
    }

    /**
     * IM的无前置单号的占用数据
     * 
     * @param staId
     */
    public void insertOccupiedAndRelease(Long staId) {
        log.info("insertOccupiedAndRelease----begin-----" + staId);
        try {
            StockTransApplication sta = staDao.getByPrimaryKey(staId);
            if (sta != null && sta.getType().equals(StockTransApplicationType.INVENTORY_STATUS_CHANGE)) {
                log.error("INVENTORY_STATUS_CHANGE----end-----" + staId);
                return;
            }
            if (sta != null && (sta.getIsNotPacsomsOrder() || (sta.getDataSource() != null && "WMS3".equals(sta.getDataSource())))) {
                Integer type = 0;
                if (StockTransApplicationStatus.CANCELED.equals(sta.getStatus()) || (StockTransApplicationType.INVENTORY_ADJUSTMENT_UPDATE.equals(sta.getType()) && StockTransApplicationStatus.FINISHED.equals(sta.getStatus()))
                        || (StockTransApplicationType.INVENTORY_LOCK.equals(sta.getType()) && StockTransApplicationStatus.FINISHED.equals(sta.getStatus()))) {
                    type = 1;
                }
                // 是否有重复数据
                List<WmsIMOccupiedAndRelease> oarList = wmsIMOccupiedAndReleaseDao.getWmsIMOccupiedAndReleaseByStaCodeAndType(sta.getCode(), type);
                if (oarList != null && oarList.size() > 0) {
                    return;
                }
                // 是否占用过库存
                List<StvLineCommand> outStvLine = stvLineDao.findStvLineByStaIdAndDirection(staId, 2, new BeanPropertyRowMapperExt<StvLineCommand>(StvLineCommand.class));
                if (type != 1 && (outStvLine == null || outStvLine.size() == 0)) {
                    return;
                }
                log.info("insertOccupiedAndRelease----passCheck-----" + staId);
                com.jumbo.wms.model.authorization.OperationUnit ou = operationUnitDao.getByPrimaryKey(sta.getMainWarehouse().getId());
                List<StaLine> slList = staLineDao.findByStaId(staId);
                if (sta.getOwner() != null) {
                    for (StaLine sl : slList) {
                        BiChannel bi = null;
                        bi = biChannelDao.getByCode(sta.getOwner());
                        Customer c = null;
                        if (bi != null && bi.getCustomer() != null) {
                            c = customerDao.getByPrimaryKey(bi.getCustomer().getId());
                        }
                        String statusStr = ImWmsInvStratusMapping(sl.getInvStatus().getId());
                        if (statusStr == null) {
                            continue;
                        }
                        Sku sku = skuDao.getByPrimaryKey(sl.getSku().getId());
                        if (c == null) {
                            c = customerDao.getByPrimaryKey(sku.getCustomer().getId());
                        }
                        WmsIMOccupiedAndRelease or = new WmsIMOccupiedAndRelease();
                        or.setBinCode(ou.getCode());
                        or.setCreateTime(new Date());
                        or.setCustomerCode(c.getCode());
                        or.setInvStatusCode(statusStr);
                        or.setInvTransactionTime(new Date());
                        or.setOwnerCode(sta.getOwner());
                        or.setSkuCode(sku.getCode());
                        or.setStaCode(sta.getCode());
                        or.setStatus(0);
                        or.setStaType(sta.getType().getValue() + "");
                        if (type == 1) {
                            or.setQty(sl.getQuantity());
                            or.setType(1);
                        } else {
                            or.setQty(0 - sl.getQuantity());
                            or.setType(0);
                        }
                        // 判断库间移动是否推送im
                        if (org.apache.commons.lang3.StringUtils.equals("0", IS_PUSH_IM_TRANSIT_INNER) && sta.getType().getValue() == 31) {
                            or.setStatus(20);
                        } else {
                            wmsIMOccupiedAndReleaseDao.save(or);
                        }
                    }
                } else {
                    for (StvLineCommand sc : outStvLine) {
                        String owner = stvLineDao.findOwnerByStvLineId(sc.getId(), new SingleColumnRowMapper<String>(String.class));
                        if (owner == null) {
                            owner = sc.getOwner();
                        }
                        BiChannel bi = null;
                        bi = biChannelDao.getByCode(owner);
                        Customer c = null;
                        if (bi != null && bi.getCustomer() != null) {
                            c = customerDao.getByPrimaryKey(bi.getCustomer().getId());
                        }
                        String statusStr = ImWmsInvStratusMapping(sc.getIntInvstatus());
                        if (statusStr == null) {
                            continue;
                        }
                        Sku sku = skuDao.getByPrimaryKey(sc.getSkuId());
                        if (c == null) {
                            c = customerDao.getByPrimaryKey(sku.getCustomer().getId());
                        }
                        WmsIMOccupiedAndRelease or = new WmsIMOccupiedAndRelease();
                        or.setBinCode(ou.getCode());
                        or.setCreateTime(new Date());
                        or.setCustomerCode(c.getCode());
                        or.setInvStatusCode(statusStr);
                        or.setInvTransactionTime(new Date());
                        or.setOwnerCode(owner);
                        or.setSkuCode(sku.getCode());
                        or.setStaCode(sta.getCode());
                        or.setStatus(0);
                        or.setStaType(sta.getType().getValue() + "");
                        if (type == 1) {
                            or.setQty(sc.getQuantity());
                            or.setType(1);
                        } else {
                            or.setQty(0 - sc.getQuantity());
                            or.setType(0);
                        }
                        // 判断库间移动是否推送im
                        if (org.apache.commons.lang3.StringUtils.equals("0", IS_PUSH_IM_TRANSIT_INNER) && sta.getType().getValue() == 31) {
                            or.setStatus(20);
                        } else {
                            wmsIMOccupiedAndReleaseDao.save(or);
                        }
                    }
                }
            }
            log.info("insertOccupiedAndRelease----end-----" + staId);
        } catch (Exception e) {
            log.error("insertOccupiedAndRelease staId:" + staId, e);
        }
    }

    /**
     * IM盘点的占用数据
     * 
     * @param staId
     */
    public void insertOccupiedAndReleaseByCheck(Long checkId) {
        log.info("insertOccupiedAndRelease----begin-----" + checkId);
        try {
            InventoryCheck ic = inventoryCheckDao.getByPrimaryKey(checkId);
            if (ic != null) {
                Integer type = 0;
                if (ic.getStatus().equals(InventoryCheckStatus.CANCELED)) {
                    type = 1;
                }
                // 是否有重复数据
                List<WmsIMOccupiedAndRelease> oarList = wmsIMOccupiedAndReleaseDao.getWmsIMOccupiedAndReleaseByStaCodeAndType(ic.getCode(), type);
                if (oarList != null && oarList.size() > 0) {
                    return;
                }
                // 是否占用过库存
                List<InventoryCheckDifferenceLine> dlist = inventoryCheckDifferenceLineDao.findByInventoryCheck(ic.getId());
                if (dlist == null || dlist.size() == 0) {
                    return;
                }
                log.info("insertOccupiedAndReleaseByCheck----passCheck-----" + checkId);
                com.jumbo.wms.model.authorization.OperationUnit ou = operationUnitDao.getByPrimaryKey(ic.getOu().getId());

                for (InventoryCheckDifferenceLine icd : dlist) {

                    if (icd.getQuantity() < 0) {// 盘亏

                        BiChannel bi = null;
                        bi = biChannelDao.getByPrimaryKey(ic.getShop().getId());
                        Customer c = null;
                        if (bi != null && bi.getCustomer() != null) {
                            c = customerDao.getByPrimaryKey(bi.getCustomer().getId());
                        }
                        String statusStr = ImWmsInvStratusMapping(icd.getStatus().getId());
                        if (statusStr == null) {
                            continue;
                        }
                        Sku sku = skuDao.getByPrimaryKey(icd.getSku().getId());
                        if (c == null) {
                            c = customerDao.getByPrimaryKey(sku.getCustomer().getId());
                        }
                        WmsIMOccupiedAndRelease or = new WmsIMOccupiedAndRelease();
                        or.setBinCode(ou.getCode());
                        or.setCreateTime(new Date());
                        or.setCustomerCode(c.getCode());
                        or.setInvStatusCode(statusStr);
                        or.setInvTransactionTime(new Date());
                        or.setOwnerCode(bi.getCode());
                        or.setSkuCode(sku.getCode());
                        or.setStaCode(ic.getCode());
                        or.setStatus(0);
                        or.setStaType(ic.getType().getValue() + "");
                        if (type == 1) {
                            or.setQty(-icd.getQuantity());
                            or.setType(1);
                        } else {
                            or.setQty(icd.getQuantity());
                            or.setType(0);
                        }
                        wmsIMOccupiedAndReleaseDao.save(or);
                    }
                }
            }
            log.info("insertOccupiedAndReleaseByCheck----end-----" + checkId);
        } catch (Exception e) {
            log.error("insertOccupiedAndReleaseByCheck checkId:" + checkId, e);
        }
    }

    public String ImWmsInvStratusMapping(Long statusId) {
        InventoryStatus is = inventoryStatusDao.getByPrimaryKey(statusId);
        if (is == null) {
            return null;
        } else if ("良品".equals(is.getName()) && is.getIsForSale()) {
            return "10";
        } else if ("残次品".equals(is.getName()) || "待报废".equals(is.getName()) || "良品不可售".equals(is.getName()) || "良品不可销售".equals(is.getName()) || "临近保质期".equals(is.getName())) {
            return "20";
        } else if ("待处理品".equals(is.getName())) {
            return "40";
        }
        return null;
    }

    /**
     * IM的部分出库的释放占用数据
     * 
     * @param staId
     */
    public void insertOccupiedAndReleaseUnDeal(Long staId) {
        log.info("insertOccupiedAndReleaseUnDeal----begin-----" + staId);
        try {
            StockTransApplication sta = staDao.getByPrimaryKey(staId);
            if (sta != null && sta.getType().equals(StockTransApplicationType.INVENTORY_STATUS_CHANGE)) {
                log.error("INVENTORY_STATUS_CHANGE----end-----" + staId);
                return;
            }
            if (sta != null && (sta.getIsNotPacsomsOrder() || (sta.getDataSource() != null && "WMS3".equals(sta.getDataSource())))) {
                Integer type = 1;
                // 退大仓|转店退仓|结算经销出库|包材领用出库|代销 出库
                if (!((StockTransApplicationType.VMI_RETURN.equals(sta.getType()) && StockTransApplicationStatus.FINISHED.equals(sta.getStatus()))
                        || (StockTransApplicationType.VMI_TRANSFER_RETURN.equals(sta.getType()) && StockTransApplicationStatus.FINISHED.equals(sta.getStatus()))
                        || (StockTransApplicationType.OUTBOUND_SETTLEMENT.equals(sta.getType()) && StockTransApplicationStatus.FINISHED.equals(sta.getStatus()))
                        || (StockTransApplicationType.OUTBOUND_PACKAGING_MATERIALS.equals(sta.getType()) && StockTransApplicationStatus.FINISHED.equals(sta.getStatus())) || (StockTransApplicationType.OUTBOUND_CONSIGNMENT.equals(sta.getType()) && StockTransApplicationStatus.FINISHED
                        .equals(sta.getStatus())))) {
                    return;
                }
                log.info("insertOccupiedAndReleaseUndeal----passCheck-----" + staId);
                com.jumbo.wms.model.authorization.OperationUnit ou = operationUnitDao.getByPrimaryKey(sta.getMainWarehouse().getId());
                List<StaLineCommand> slList = staLineDao.findStaLineByStaId5(staId, new BeanPropertyRowMapper<StaLineCommand>(StaLineCommand.class));
                if (sta.getOwner() != null) {
                    for (StaLineCommand sl : slList) {
                        if (sl.getCompleteQuantity().longValue() == sl.getQuantity().longValue()) {
                            continue;
                        }
                        BiChannel bi = null;
                        bi = biChannelDao.getByCode(sta.getOwner());
                        Customer c = null;
                        if (bi != null && bi.getCustomer() != null) {
                            c = customerDao.getByPrimaryKey(bi.getCustomer().getId());
                        }
                        String statusStr = ImWmsInvStratusMapping(sl.getInvStatusId());
                        Sku sku = skuDao.getByPrimaryKey(sl.getSkuId());
                        if (c == null) {
                            c = customerDao.getByPrimaryKey(sku.getCustomer().getId());
                        }
                        WmsIMOccupiedAndRelease or = new WmsIMOccupiedAndRelease();
                        or.setBinCode(ou.getCode());
                        or.setCreateTime(new Date());
                        or.setCustomerCode(c.getCode());
                        or.setInvStatusCode(statusStr);
                        or.setInvTransactionTime(new Date());
                        or.setOwnerCode(sta.getOwner());
                        or.setSkuCode(sku.getCode());
                        or.setStaCode(sta.getCode());
                        or.setStatus(0);
                        or.setStaType(sta.getType().getValue() + "");
                        or.setQty(sl.getQuantity() - sl.getCompleteQuantity());
                        or.setType(1);
                        // 判断库间移动是否推送im
                        if (org.apache.commons.lang3.StringUtils.equals("0", IS_PUSH_IM_TRANSIT_INNER) && sta.getType().getValue() == 31) {
                            or.setStatus(20);
                        } else {
                            wmsIMOccupiedAndReleaseDao.save(or);
                        }
                    }
                }
            }
            log.info("insertOccupiedAndReleaseUnDeal----end-----" + staId);
        } catch (Exception e) {
            log.error("insertOccupiedAndReleaseUnDeal staId:" + staId, e);
        }
    }

    /**
     * 导入Excel覆盖原始计划量插入取消占用表给IM
     */
    public void insertOccupiedAndReleaseCover(Long staId, List<StaLine> listSource, List<StaLine> listLast) {
        log.info("insertOccupiedAndReleaseCover----begin-----" + staId);
        try {
            StockTransApplication sta = staDao.getByPrimaryKey(staId);
            if (sta != null && (sta.getIsNotPacsomsOrder() || (sta.getDataSource() != null && "WMS3".equals(sta.getDataSource())))) {
                Integer type = 1;
                // 退大仓和转店退仓
                if (!((StockTransApplicationType.VMI_RETURN.equals(sta.getType()) && StockTransApplicationStatus.OCCUPIED.equals(sta.getStatus()))
                        || (StockTransApplicationType.VMI_TRANSFER_RETURN.equals(sta.getType()) && StockTransApplicationStatus.OCCUPIED.equals(sta.getStatus()))
                        || (StockTransApplicationType.OUTBOUND_SETTLEMENT.equals(sta.getType()) && StockTransApplicationStatus.OCCUPIED.equals(sta.getStatus()))
                        || (StockTransApplicationType.OUTBOUND_PACKAGING_MATERIALS.equals(sta.getType()) && StockTransApplicationStatus.OCCUPIED.equals(sta.getStatus())) || (StockTransApplicationType.OUTBOUND_CONSIGNMENT.equals(sta.getType()) && StockTransApplicationStatus.OCCUPIED
                        .equals(sta.getStatus())))) {
                    return;
                }
                log.info("insertOccupiedAndReleaseCover----passCheck-----" + staId);
                com.jumbo.wms.model.authorization.OperationUnit ou = operationUnitDao.getByPrimaryKey(sta.getMainWarehouse().getId());
                if (sta.getOwner() != null) {
                    for (StaLine sl : listSource) {
                        for (StaLine l : listLast) {
                            if ((sl.getSku().getId().longValue() == l.getSku().getId().longValue()) && (sl.getInvStatus().getId().longValue() == sl.getInvStatus().getId().longValue())) {
                                if (sl.getQuantity().longValue() == l.getQuantity().longValue()) {
                                    break;
                                }
                                BiChannel bi = null;
                                bi = biChannelDao.getByCode(sta.getOwner());
                                Customer c = null;
                                if (bi != null && bi.getCustomer() != null) {
                                    c = customerDao.getByPrimaryKey(bi.getCustomer().getId());
                                }
                                String statusStr = ImWmsInvStratusMapping(sl.getInvStatus().getId());
                                Sku sku = skuDao.getByPrimaryKey(sl.getSku().getId());
                                if (c == null) {
                                    c = customerDao.getByPrimaryKey(sku.getCustomer().getId());
                                }
                                WmsIMOccupiedAndRelease or = new WmsIMOccupiedAndRelease();
                                or.setBinCode(ou.getCode());
                                or.setCreateTime(new Date());
                                or.setCustomerCode(c.getCode());
                                or.setInvStatusCode(statusStr);
                                or.setInvTransactionTime(new Date());
                                or.setOwnerCode(sta.getOwner());
                                or.setSkuCode(sku.getCode());
                                or.setStaCode(sta.getCode());
                                or.setStatus(0);
                                or.setStaType(sta.getType().getValue() + "");
                                if (l.getQuantity().longValue() < sl.getQuantity().longValue()) {
                                    // 执行量小于计划量
                                    or.setQty(sl.getQuantity() - l.getQuantity());
                                    or.setType(1);
                                } else {
                                    or.setQty(sl.getQuantity() - l.getQuantity());
                                    or.setType(0);
                                }
                                // 判断库间移动是否推送im
                                if (org.apache.commons.lang3.StringUtils.equals("0", IS_PUSH_IM_TRANSIT_INNER) && sta.getType().getValue() == 31) {
                                    or.setStatus(20);
                                } else {
                                    wmsIMOccupiedAndReleaseDao.save(or);
                                }
                            }
                        }
                    }
                }
            }
            log.info("insertOccupiedAndReleaseCover----end-----" + staId);
        } catch (Exception e) {
            log.error("insertOccupiedAndReleaseCover staId:" + staId, e);
        }
    }

    @Override
    public WmsAdvanceOrderResult wmsAdvanceOrderDeliverNotice(String systemKey, OrderAddInfo addInfo) {
        log.info("wmsAdvanceOrderDeliverNotice----start");
        WmsAdvanceOrderResult result = new WmsAdvanceOrderResult();
        if (addInfo != null) {
            // 查询作业单
            StockTransApplication sta = staDao.findStaByReSlipCode(addInfo.getOrderCode());
            if (sta == null) {
                result.setStatus(0);
                result.setMemo("作业单不存在:" + addInfo.getOrderCode());
                return result;
            }
            Warehouse wareHouse = warehouseDao.getByOuId(sta.getMainWarehouse().getId());
            AdvanceOrderAddInfo ad = advanceOrderAddInfoDao.findOrderInfoByOrderCode(addInfo.getOrderCode(), new BeanPropertyRowMapperExt<AdvanceOrderAddInfo>(AdvanceOrderAddInfo.class));
            if (ad == null) {
                AdvanceOrderAddInfo info = new AdvanceOrderAddInfo();
                info.setIsAllowDeliver(addInfo.getIsAllowDeliver());
                info.setIsChangeRecieverInfo(addInfo.getIsChangeRecieverInfo());
                info.setMemo(addInfo.getMemo());
                info.setOrderCode(addInfo.getOrderCode());
                info.setOwner(addInfo.getOwner());
                info.setPaymentStatus(addInfo.getPaymentStatus());
                info.setPaymentTime(addInfo.getPaymentTime());
                info.setStatus(1);
                info.setLfMemo(sta.getCode());// 作业单
                if (wareHouse.getVmiSource() != null) {// 外包仓
                    info.setLfStatus(1);
                    info.setVmiSource(wareHouse.getVmiSource());
                }
                addInfoDao.save(info);
                if (addInfo.getRecieverInfo() != null) {
                    RecieverInfo recieverInfo = new RecieverInfo();
                    recieverInfo.setLpcode(addInfo.getRecieverInfo().getLpcode());
                    recieverInfo.setRecieverAddress(addInfo.getRecieverInfo().getRecieverAddress());
                    recieverInfo.setRecieverCity(addInfo.getRecieverInfo().getRecieverCity());
                    recieverInfo.setRecieverCountry(addInfo.getRecieverInfo().getRecieverCountry());
                    recieverInfo.setRecieverDistrict(addInfo.getRecieverInfo().getRecieverDistrict());
                    recieverInfo.setRecieverEmail(addInfo.getRecieverInfo().getRecieverEmail());
                    recieverInfo.setRecieverMobilePhone(addInfo.getRecieverInfo().getRecieverMobilePhone());
                    recieverInfo.setRecieverName(addInfo.getRecieverInfo().getRecieverName());
                    recieverInfo.setRecieverProvince(addInfo.getRecieverInfo().getRecieverProvince());
                    recieverInfo.setRecieverTelephone(addInfo.getRecieverInfo().getRecieverTelephone());
                    recieverInfo.setRecieverVillagesTowns(addInfo.getRecieverInfo().getRecieverVillagesTowns());
                    recieverInfo.setRecieverZipCode(addInfo.getRecieverInfo().getRecieverZipCode());
                    recieverInfo.setTrackingNumber(addInfo.getRecieverInfo().getTrackingNumber());
                    recieverInfo.setAddInfo(info);
                    recieverInfoDao.save(recieverInfo);
                }
                MongoDBOrderAddInfo orderAddInfo = new MongoDBOrderAddInfo();
                orderAddInfo.setOwner(addInfo.getOwner());
                orderAddInfo.setIsAllowDeliver(addInfo.getIsAllowDeliver());
                orderAddInfo.setIsChangeRecieverInfo(addInfo.getIsChangeRecieverInfo());
                orderAddInfo.setOrderCode(addInfo.getOrderCode());
                orderAddInfo.setMemo(addInfo.getMemo());
                orderAddInfo.setOrderSource(addInfo.getOrderSource());
                orderAddInfo.setPaymentStatus(addInfo.getPaymentStatus());
                orderAddInfo.setPaymentTime(addInfo.getPaymentTime());
                mongoOperation.save(orderAddInfo);
                MongoDBRecieverInfo dbRecieverInfo = new MongoDBRecieverInfo();
                if (addInfo.getRecieverInfo() != null) {
                    dbRecieverInfo.setLpcode(addInfo.getRecieverInfo().getLpcode());
                    dbRecieverInfo.setRecieverAddress(addInfo.getRecieverInfo().getRecieverAddress());
                    dbRecieverInfo.setRecieverCity(addInfo.getRecieverInfo().getRecieverCity());
                    dbRecieverInfo.setRecieverCountry(addInfo.getRecieverInfo().getRecieverCountry());
                    dbRecieverInfo.setRecieverDistrict(addInfo.getRecieverInfo().getRecieverDistrict());
                    dbRecieverInfo.setRecieverEmail(addInfo.getRecieverInfo().getRecieverEmail());
                    dbRecieverInfo.setRecieverMobilePhone(addInfo.getRecieverInfo().getRecieverMobilePhone());
                    dbRecieverInfo.setRecieverName(addInfo.getRecieverInfo().getRecieverName());
                    dbRecieverInfo.setRecieverProvince(addInfo.getRecieverInfo().getRecieverProvince());
                    dbRecieverInfo.setRecieverTelephone(addInfo.getRecieverInfo().getRecieverTelephone());
                    dbRecieverInfo.setRecieverVillagesTowns(addInfo.getRecieverInfo().getRecieverVillagesTowns());
                    dbRecieverInfo.setRecieverZipCode(addInfo.getRecieverInfo().getRecieverZipCode());
                    dbRecieverInfo.setTrackingNumber(addInfo.getRecieverInfo().getTrackingNumber());
                    dbRecieverInfo.setAddinfoId(info.getId());
                    mongoOperation.save(dbRecieverInfo);
                }
                result.setOrderCode(addInfo.getOrderCode());
                result.setOrderSource(addInfo.getOrderSource());
                result.setStatus(1);
                return result;
            } else {
                result.setStatus(0);
                result.setMemo("数据重复");
                log.info("wmsAdvanceOrderDeliverNotice-----double");
                return result;
            }

        } else {
            result.setStatus(0);
            result.setMemo("数据为空");
            return result;
        }


    }

    /**
     * 星巴克MSR定制卡入库通知接口
     */
    @Override
    public ReturnResult recieveMSRCustomCardRtnInfo(String systemKey, List<MSRCustomCardRtnInfo> list) {
        ReturnResult rs = new ReturnResult();
        rs.setStatus(0);
        // String batchCode = Long.valueOf(new Date().getTime()).toString();
        try {
            if (list == null || (list != null && list.isEmpty())) {
                rs.setErrorMsg("星巴克MSR定制卡sn入库数据为空！");
                return rs;
            }
            for (MSRCustomCardRtnInfo cardRtnInfo : list) {
                // 数据校验
                if (cardRtnInfo.getStaCode() == null || cardRtnInfo.getSn() == null) {
                    throw new BusinessException(ErrorCode.LOST_STACODE_OR_SN);
                }
                if (cardRtnInfo.getStaCode() == null || cardRtnInfo.getSn() == null || cardRtnInfo.getSku() == null) {
                    throw new BusinessException(ErrorCode.LOST_STACODE_OR_SN);
                }
                StockTransApplication sta = staDao.getByCode(cardRtnInfo.getStaCode());
                if (sta == null) {
                    throw new BusinessException(ErrorCode.STA_NOT_EXIST, new Object[] {cardRtnInfo.getStaCode()});
                }
                // 对sn号进行格式化处理
                Long skuId = skuDao.findSkuIdByStaCode(sta.getCode(), new SingleColumnRowMapper<Long>(Long.class));
                Sku sku = skuDao.getByPrimaryKey(skuId);
                if (sku == null) {
                    throw new BusinessException(ErrorCode.MSR_SKU_NOT_EXIST, new Object[] {sku.getCode()});
                }
                if (sku.getSnCheckMode() == null) {
                    throw new BusinessException(ErrorCode.SKU_CHECK_MODE_NOT_EXIST, new Object[] {sku.getCode()});
                }
                List<SkuSnCheckCfgCommand> cfgList = skuSnCheckCfgDao.getSkuSnCheckCfgBySnCheckMode(sku.getSnCheckMode().getValue(), new BeanPropertyRowMapper<SkuSnCheckCfgCommand>(SkuSnCheckCfgCommand.class));
                Map<Integer, String> map = new HashMap<Integer, String>();
                String snCode = cardRtnInfo.getSn();
                if (cfgList.size() > 0) {
                    // 验证卡号是否符合对应规则
                    for (SkuSnCheckCfgCommand cfg : cfgList) {
                        map.put(cfg.getTypeInt(), cfg.getMemo());
                    }
                    // 判断是否有正则表达式验证
                    if (map.containsKey(SkuSnCheckCfgType.REGULAR.getValue())) {
                        if (!StringUtil.isEmpty(map.get(SkuSnCheckCfgType.REGULAR.getValue()))) {
                            Pattern p = Pattern.compile(map.get(SkuSnCheckCfgType.REGULAR.getValue()));
                            Matcher m = p.matcher(snCode);
                            if (!m.find()) {
                                // 正则规则不正确
                                throw new BusinessException(ErrorCode.SN_REGULAR_CHECK_NOT_PASS, new Object[] {snCode, map.get(SkuSnCheckCfgType.REGULAR.getValue())});
                            }
                        }
                    }
                    // 判断是否有格式化配置
                    if (map.containsKey(SkuSnCheckCfgType.CHAR_REPLACE.getValue())) {
                        if (!StringUtil.isEmpty(map.get(SkuSnCheckCfgType.CHAR_REPLACE.getValue()))) {
                            snCode = SnManagerImpl.formatSn(cardRtnInfo.getSn(), map.get(SkuSnCheckCfgType.CHAR_REPLACE.getValue()));
                        }
                    }
                }
                // 检验sn重复性
                SkuSnMapping skuSnExist = skuSnMappingDao.findByStaIdAndSn(null, snCode, new BeanPropertyRowMapper<SkuSnMapping>(SkuSnMapping.class));
                SkuSnMapping skuSnSta = skuSnMappingDao.findByStaIdAndSn(sta.getId(), snCode, new BeanPropertyRowMapper<SkuSnMapping>(SkuSnMapping.class));
                if (skuSnExist != null && skuSnExist.getStaId() != null && skuSnSta == null) {
                    throw new BusinessException(ErrorCode.EXIST_SN_HAS_STA, new Object[] {cardRtnInfo.getSn(), skuSnExist.getStaId(), sta.getId()});
                }
                // 保存monggodb
                MSRCustomCardFeedBack mb = new MSRCustomCardFeedBack();
                mb.setStaCode(sta.getCode());
                mb.setSn(cardRtnInfo.getSn());
                mb.setOrderCode(cardRtnInfo.getOrderCode());
                mb.setPickingListCode(cardRtnInfo.getPickingListCode());
                mb.setCreateDate(new Date());
                mongoOperation.save(mb);
                // 重复推送的数据
                if (skuSnExist != null && skuSnExist != null) {
                    rs.setMemo(rs.getMemo() + " | " + cardRtnInfo.getSn() + "重复推送！");
                    continue;
                }
                // 保存 mapping过的sn号
                // sn入mapping表
                SkuSnMapping sn = new SkuSnMapping();
                sn.setOuId(sta.getMainWarehouse().getId());
                sn.setSn(snCode);
                sn.setSkuId(skuId);
                sn.setStaId(sta.getId());
                sn.setCreateTime(new Date());
                Long stvId = stvDao.findStvIdByStaIdUnique(sta.getCode(), new SingleColumnRowMapper<Long>(Long.class));
                sn.setStvId(stvId);
                skuSnMappingDao.save(sn);
            }
            rs.setStatus(1);
            rs.setMemo("接收成功！");
            return rs;
        } catch (BusinessException e) {
            String errorMsg = "";
            if (e.getErrorCode() != ErrorCode.ERROR_NOT_SPECIFIED) {
                errorMsg += applicationContext.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs(), Locale.SIMPLIFIED_CHINESE);
            }
            rs.setErrorMsg(errorMsg);
            log.error("MSR sn inboud BusinessException:" + list.toString() + " " + e.getErrorCode());
            log.error(errorMsg, e);
        } catch (Exception e) {
            log.error("MSR sn inboud error! " + list.toString(), e);
            rs.setErrorMsg("WMS系统异常！");
        }
        return rs;
    }

    @Override
    public String agvRtnOutBound(AgvOutBoundDto agvOutBoundDto) {
        Boolean b = true;
        if (agvOutBoundDto == null) {
            throw new BusinessException("AGV出库反馈为空");
        }
        StockTransApplication sta = staDao.getByCode(agvOutBoundDto.getStaCode());
        List<InboundAgvToHub> hubList = inboundAgvToHubDao.inboundAgvToHubByApiName(sta.getId(), "agvRtnOutBound", new BeanPropertyRowMapper<InboundAgvToHub>(InboundAgvToHub.class));
        if (hubList.size() > 0) {
            log.error("agvRtnOutBound.hubList.size=" + sta.getCode());
            MongoAGVMessage mdbm = new MongoAGVMessage();
            mdbm.setStaCode(agvOutBoundDto.getStaCode());
            mdbm.setMsgType("agvRtnOutBound");
            mdbm.setOtherUniqueKey(agvOutBoundDto.getPlCode());
            mdbm.setMsgBody(JsonUtil.writeValue(agvOutBoundDto));
            mdbm.setSendTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            mongoOperation.save(mdbm);
            return "1";// 成功
        }
        MongoAGVMessage mdbm = new MongoAGVMessage();
        mdbm.setStaCode(agvOutBoundDto.getStaCode());
        mdbm.setMsgType("agvRtnOutBound");
        mdbm.setOtherUniqueKey(agvOutBoundDto.getPlCode());
        mdbm.setMsgBody(JsonUtil.writeValue(agvOutBoundDto));
        mdbm.setSendTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        mongoOperation.save(mdbm);
        List<AgvOutBoundLineDto> list = agvOutBoundDto.getList();
            InboundAgvToHub inboundAgvToHub = new InboundAgvToHub();
            inboundAgvToHub.setApiName("agvRtnOutBound");
            inboundAgvToHub.setStaId(sta.getId());
            inboundAgvToHub.setCreateTime(new Date());
            inboundAgvToHub.setErrorCount(0L);
            inboundAgvToHub.setStatus(1L);
            inboundAgvToHub.setType("采购入库");
            boolean a = false;
            Long qtys = 0L;
            Long fqtys = 0L;
            for (AgvOutBoundLineDto agvOutBoundLineDto : list) {
            qtys = agvOutBoundLineDto.getQuantity() + qtys;
            fqtys = agvOutBoundLineDto.getFulfillQuantity() + fqtys;
            }
        if (qtys != fqtys) {
            a = true;
        }
        for (AgvOutBoundLineDto agvOutBoundLineDto : list) {
            if (a) {
                if (agvOutBoundLineDto.getFulfillQuantity() != 0) {
                    if (b) {
                        inboundAgvToHub = inboundAgvToHubDao.save(inboundAgvToHub);
                        b = false;
                    }
                    InboundAgvToHubLine inboundAgvToHubLine = new InboundAgvToHubLine();
                    inboundAgvToHubLine.setInAgvId(inboundAgvToHub.getId());
                    inboundAgvToHubLine.setQty(agvOutBoundLineDto.getFulfillQuantity());
                    Sku sku = skuDao.getByCode(agvOutBoundLineDto.getSkuCode());
                    inboundAgvToHubLine.setSkuId(sku.getId());
                    if (sku.getStoremode().equals(InboundStoreMode.SHELF_MANAGEMENT)) {
                        if (agvOutBoundLineDto.getExpireDateStr() != null) {
                            inboundAgvToHubLine.setExpireDate(agvOutBoundLineDto.getExpireDateStr());
                        }
                        }
                    inboundAgvToHubLineDao.save(inboundAgvToHubLine);
                    }
            }
        }


        return "1";// 成功
    }

    @Override
    public ReturnResult agvRtnOutBoundList(List<AgvOutBoundDto> ls) {
        ReturnResult rs = new ReturnResult();
        rs.setStatus(1);
        if (ls.size() == 0) {
            log.error("agvRtnOutBoundList==>AGV出库反馈为空 ls为空");
            rs.setStatus(0);
            rs.setErrorMsg("AGV出库反馈为空 ls为空");
            return rs;
        }
        String staCode = null;
        try {
            for (AgvOutBoundDto agvOutBoundDto : ls) {
                staCode = agvOutBoundDto.getStaCode();
                agvRtnOutBound(agvOutBoundDto);
            }
        } catch (Exception e) {
            log.error("agvRtnOutBoundList_staCode:" + staCode, e);
            rs.setStatus(0);
            rs.setErrorMsg("AGV出库反馈异常单号" + e.getMessage());
            return rs;
        }

        return rs;
    }

    @Override
    public List<WmsRtnOrderResult> rtnOrderModifyMessage(String systemKey, List<RtnOrderByAD> rtnOrderByADList) {
        List<WmsRtnOrderResult> wmsRtnOrderResultList = new ArrayList<WmsRtnOrderResult>();
        if (systemKey != null && systemKey.equals("adidas")) {
            if (rtnOrderByADList != null && rtnOrderByADList.size() > 0) {
                for (RtnOrderByAD rtnOrderByAD : rtnOrderByADList) {
                    StockTransApplication sta = staDao.findStaBySlipCodeStatus(rtnOrderByAD.getOrderCode());
                    WmsRtnOrderResult wmsRtnOrderResult = new WmsRtnOrderResult();
                    wmsRtnOrderResult.setOrderCode(rtnOrderByAD.getOrderCode());
                    wmsRtnOrderResult.setOrderSource("adidas");
                    if (sta != null) {
                        staDao.updateStaDeliveryInfoBySlipCode(sta.getId(), rtnOrderByAD.getLpCode(), rtnOrderByAD.getTrackingNo());
                        wmsRtnOrderResult.setStatus(1);
                        staDao.save(sta);
                    } else {
                        wmsRtnOrderResult.setStatus(0);
                        wmsRtnOrderResult.setMemo("订单不存在");
                    }
                    wmsRtnOrderResultList.add(wmsRtnOrderResult);
                }
            }

        } else {
            WmsRtnOrderResult wmsRtnOrderResult = new WmsRtnOrderResult();
            wmsRtnOrderResult.setStatus(0);
            wmsRtnOrderResult.setOrderSource(systemKey);
            wmsRtnOrderResult.setMemo("systemKey不正确");
            wmsRtnOrderResultList.add(wmsRtnOrderResult);
        }
        return wmsRtnOrderResultList;
    }

    @Override
    public InventoryBatch getWmsSalesInventoryBatch(Date startTime, Date endTime) {
        return inventoryBatchDao.getWmsSalesInventoryBatch(startTime, endTime, new BeanPropertyRowMapper<InventoryBatch>(InventoryBatch.class));
    }

    @Override
    public Pagination<WmsAdidasSalesInventory> getSalesInventoryDetailByBatchCode(int start, int pagesize, String batchCode) {
        Pagination<WmsAdidasSalesInventory> pl = null;
        try {
            if (log.isDebugEnabled()) {
                log.debug("getSalesInventoryDetailByBatchCode is start batchCode=" + batchCode);
            }
            pl = adidasSalesInventoryDao.getSalesInventoryDetailByBatchCode(start, pagesize, batchCode, new BeanPropertyRowMapper<WmsAdidasSalesInventory>(WmsAdidasSalesInventory.class));
        } catch (Exception e) {
            log.error("", e);
        }
        return pl;
    }

}
