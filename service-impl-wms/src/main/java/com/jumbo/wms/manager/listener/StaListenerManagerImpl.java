/**
 * Copyright (c) 2010 Jumbomart All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of Jumbomart. You shall not
 * disclose such Confidential Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with Jumbo.
 * 
 * JUMBOMART MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF THE SOFTWARE, EITHER
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE, OR NON-INFRINGEMENT. JUMBOMART SHALL NOT BE LIABLE FOR ANY
 * DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES.
 * 
 */

package com.jumbo.wms.manager.listener;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.annotation.Resource;

import com.baozun.ecp.ip.command.response.Response;
import com.baozun.ecp.ip.manager.wms3.Wms3AdapterInteractManager;
import com.baozun.scm.baseservice.message.common.MessageCommond;
import com.baozun.scm.baseservice.message.rocketmq.service.server.RocketMQProducerServer;
import com.baozun.utilities.json.JsonUtil;
import com.jumbo.dao.authorization.OperationUnitDao;
import com.jumbo.dao.authorization.PhysicalWarehouseDao;
import com.jumbo.dao.automaticEquipment.WhContainerDao;
import com.jumbo.dao.baseinfo.*;
import com.jumbo.dao.baseinfo.ChannelWhRefRefDao;
import com.jumbo.dao.baoshui.CustomsDeclarationDao;
import com.jumbo.dao.baoshui.CustomsDeclarationLineDao;
import com.jumbo.dao.baseinfo.SkuCategoriesDao;
import com.jumbo.dao.baseinfo.SkuDao;
import com.jumbo.dao.baseinfo.TransportatorDao;
import com.jumbo.dao.baseinfo.WarehouseDao;
import com.jumbo.dao.expressRadar.RadarTransNoDao;
import com.jumbo.dao.expressRadar.TransRouteDao;
import com.jumbo.dao.hub2wms.*;
import com.jumbo.dao.msg.MessageConfigDao;
import com.jumbo.dao.msg.MessageProducerErrorDao;
import com.jumbo.dao.nikeLogistics.NikeCartonNoDao;
import com.jumbo.dao.nikeLogistics.NikeCartonNoLineDao;
import com.jumbo.dao.pda.StaCartonInfoDao;
import com.jumbo.dao.system.ChooseOptionDao;
import com.jumbo.dao.vmi.defaultData.VmiStatusAdjDao;
import com.jumbo.dao.vmi.defaultData.VmiStatusAdjLineDao;
import com.jumbo.dao.vmi.warehouse.MsgOutboundOrderCancelDao;
import com.jumbo.dao.vmi.warehouse.MsgOutboundOrderDao;
import com.jumbo.dao.vmi.warehouse.MsgOutboundOrderLineDao;
import com.jumbo.dao.vmi.warehouse.MsgRtnOutboundDao;
import com.jumbo.dao.warehouse.*;
import com.jumbo.pac.manager.extsys.wms.rmi.Rmi4Wms;
import com.jumbo.pac.manager.extsys.wms.rmi.model.*;
import com.jumbo.pms.manager.SysInterfaceQueueManager;
import com.jumbo.pms.manager.api.ApiShipmentManager;
import com.jumbo.pms.manager.app.AppParcelManager;
import com.jumbo.pms.model.ParcelBill;
import com.jumbo.pms.model.ParcelLineBill;
import com.jumbo.pms.model.*;
import com.jumbo.pms.model.command.ParcelInfoCommand;
import com.jumbo.pms.model.command.ParcelInfoLineCommand;
import com.jumbo.pms.model.command.vo.ShipmentInTransitVo;
import com.jumbo.util.StringUtil;
import com.jumbo.util.UUIDUtil;
import com.jumbo.wms.Constants;
import com.jumbo.wms.daemon.VmiDefaultFactory;
import com.jumbo.wms.daemon.VmiDefaultInterface;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.baseinfo.BaseinfoManager;
import com.jumbo.wms.manager.expressDelivery.TransOlManager;
import com.jumbo.wms.manager.expressDelivery.logistics.TransOlInterface;
import com.jumbo.wms.manager.expressDelivery.logistics.TransOnLineFactory;
import com.jumbo.wms.manager.expressRadar.ExpressOrderQueryManager;
import com.jumbo.wms.manager.expressRadar.ExpressRadarManager;
import com.jumbo.wms.manager.hub2wms.HubWmsService;
import com.jumbo.wms.manager.hub2wms.WmsThreePLManager;
import com.jumbo.wms.manager.hub4.WmsOrderServiceToHub4Manager;
import com.jumbo.wms.manager.pingan.WhPingAnCoverManager;
import com.jumbo.wms.manager.system.ChooseOptionManager;
import com.jumbo.wms.manager.trans.TransSuggestManager;
import com.jumbo.wms.manager.util.MqStaticEntity;
import com.jumbo.wms.manager.vmi.Default.VmiDefaultManager;
import com.jumbo.wms.manager.vmi.VmiFactory;
import com.jumbo.wms.manager.vmi.VmiInterface;
import com.jumbo.wms.manager.vmi.ext.ExtParam;
import com.jumbo.wms.manager.vmi.gucciData.GucciManager;
import com.jumbo.wms.manager.vmi.warehouse.VmiWarehouseFactory;
import com.jumbo.wms.manager.vmi.warehouse.VmiWarehouseInterface;
import com.jumbo.wms.manager.warehouse.CustomsDeclarationManager;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.manager.warehouse.WareHouseManagerExe;
import com.jumbo.wms.manager.warehouse.WareHouseOutBoundManager;
import com.jumbo.wms.manager.warehouse.vmi.VmiStaCreateManager;
import com.jumbo.wms.model.DefaultStatus;
import com.jumbo.wms.model.SlipType;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.baseinfo.*;
import com.jumbo.wms.model.command.GiftLineCommand;
import com.jumbo.wms.model.command.SkuCategoriesCommand;
import com.jumbo.wms.model.command.SkuCommand;
import com.jumbo.wms.model.command.expressRadar.ExpressOrderQueryCommand;
import com.jumbo.wms.model.expressRadar.RadarTransNo;
import com.jumbo.wms.model.expressRadar.TransRoute;
import com.jumbo.wms.model.hub2wms.*;
import com.jumbo.wms.model.hub2wms.threepl.CnCancelOrderLog;
import com.jumbo.wms.model.hub2wms.threepl.CnMsgConfirmReply;
import com.jumbo.wms.model.mongodb.StaCartonInfo;
import com.jumbo.wms.model.msg.MessageConfig;
import com.jumbo.wms.model.msg.MessageProducerError;
import com.jumbo.wms.model.msg.MongoDBMessage;
import com.jumbo.wms.model.msg.MongoDBMessageTest;
import com.jumbo.wms.model.nikeLogistics.NikeCartonNo;
import com.jumbo.wms.model.nikeLogistics.NikeCartonNoLine;
import com.jumbo.wms.model.system.ChooseOption;
import com.jumbo.wms.model.vmi.Default.VmiGeneralStatus;
import com.jumbo.wms.model.vmi.Default.VmiStatusAdjDefault;
import com.jumbo.wms.model.vmi.Default.VmiStatusAdjLineDefault;
import com.jumbo.wms.model.vmi.warehouse.*;
import com.jumbo.wms.model.warehouse.*;
import loxia.dao.support.BeanPropertyRowMapperExt;
import loxia.utils.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

// import com.jumbo.dao.warehouse.WXConfirmOrderQueueDao;

// import com.jumbo.wms.model.warehouse.WxConfirmOrderQueue;

/**
 * @author author
 */
@Transactional
@Service("staListenerManager")
// @MsgClassAnnotation
public class StaListenerManagerImpl extends BaseManagerImpl implements StaListenerManager {

    protected static final Logger log = LoggerFactory.getLogger(StaListenerManagerImpl.class);

    private static final long serialVersionUID = -257712517932945752L;
    @Autowired
    private OperationUnitDao operationUnitDao;
    @Autowired
    private VmiWarehouseFactory vmiWarehouseFactory;
    @Autowired
    private MsgOutboundOrderDao msgOutboundOrderDao;
    @Autowired
    private StaLineDao staLineDao;
    @Autowired
    private SkuCategoriesDao skuCategoriesDao;
    @Autowired
    private WareHouseManagerExe wmExe;
    @Autowired
    private StockTransVoucherDao stvDao;
    @Autowired
    private BiChannelDao companyShopDao;
    @Autowired
    private SfOrderCancelQueueDao sfOrderCancelQueueDao;
    @Autowired
    private MsgOutboundOrderLineDao msgOutboundOrderLineDao;
    @Autowired
    private WarehouseDao warehouseDao;
    @Autowired
    private SkuSnDao snDao;
    @Autowired
    private SkuSizeConfigDao skuSizeConfigDao;
    @Autowired
    private StaDeliveryInfoDao staDeliveryInfoDao;
    @Autowired
    private WareHouseManager wareHouseManager;
    @Autowired
    private MsgOutboundOrderCancelDao msgOutboundOrderCancelDao;
    @Autowired
    private MsgInvoiceDao msgInvoiceDao;
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private StaInvoiceDao staInvoiceDao;
    @Autowired
    private BaseinfoManager baseManger;
    @Autowired
    private VmiFactory vmiFactory;
    @Autowired
    private StaAdditionalLineDao staAdditionalLineDao;
    @Autowired
    private SecKillSkuCounterDao secKillSkuCounterDao;
    @Autowired
    private SecKillSkuDao secKillSkuDao;
    @Autowired
    private PackageSkuDao packageSkuDao;
    @Autowired
    private PackageSkuCounterDao packageSkuCounterDao;
    @Autowired
    private WareHouseOutBoundManager wareHouseOutBoundManager;
    @Autowired
    private PackageSkuLogDao packageSkuLogDao;
    @Autowired
    private Rmi4Wms rmi4Wms;
    @Autowired
    private PackageInfoDao packageInfoDao;
    @Autowired
    private StvLineDao stvLineDao;
    @Autowired
    private GiftLineDao giftLineDao;
    @Autowired
    private SkuSnLogDao skuSnLogDao;
    @Autowired
    private TransOnLineFactory transOnLineFactory;
    @Autowired
    private TransSuggestManager transSuggestManager;
    @Autowired
    private WmsIntransitNoticeOmsDao wmsIntransitNoticeOmsDao;
    @Autowired
    private WmsIntransitNoticeOmsLogDao wmsIntransitNoticeOmsLogDao;
    @Autowired
    private ChooseOptionDao chooseOptionDao;
    // @Autowired
    // private WXConfirmOrderQueueDao wsConfirmOrderQueueDao;
    @Autowired
    private RadarTransNoDao radarTransNoDao;
    @Autowired
    private TransRouteDao transRouteDao;
    @Autowired
    private PhysicalWarehouseDao physicalWarehouseDao;
    @Autowired
    private WarehouseMsgSkuDao warehouseMsgSkuDao;
    @Autowired
    private ExpressRadarManager expressRadarManager;
    @Autowired
    private TransportatorDao transportatorDao;
    @Autowired
    private ExpressOrderQueryManager expressOrderQueryManager;
    @Autowired
    private ChooseOptionManager chooseOptionManager;
    @Autowired
    private VmiDefaultFactory vmiDefaultFactory;
    @Autowired
    private WmsOrderStatusOmsDao orderStatusOmsDao;
    @Autowired
    private WmsTransInfoOmsDao infoOmsDao;
    @Autowired
    private WmsInfoLogOmsDao infoLogOmsDao;
    @Autowired
    private StaInvoiceLineDao invoiceLineDao;
    @Autowired
    private WmsOrderInvoiceOmsDao invoiceOmsDao;
    @Autowired
    private WmsOrderInvoiceLineOmsDao invoiceLineOmsDao;
    @Autowired
    private InventoryStatusDao inventoryStatusDao;
    @Autowired
    private WhInfoTimeRefDao whInfoTimeRefDao;
    @Autowired
    private StockTransTxLogDao stockTransTxLogDao;
    @Autowired
    private SkuDao skuDao;
    @Autowired
    private PackageInfoDao infoDao;
    @Autowired
    private AppParcelManager appParcelManager;
    @Autowired
    private WmsInvChangeToOmsDao wmsInvChangeToOmsDao;
    @Autowired
    private ApiShipmentManager apiShipmentManager;
    @Autowired
    private BiChannelDao biChannelDao;
    @Autowired
    private NikeTurnCreateDao createDao;
    @Autowired
    private TransOlManager transOlManager;
    @Autowired
    private SfNextMorningConfigDao sfNextMorningConfigDao;
    @Autowired
    private SkuChildSnLogDao childSnLogDao;
    @Autowired
    private SkuDao skudao;
    @Autowired
    private VmiDefaultManager vmiDefaultManager;
    @Autowired
    private VmiStatusAdjDao vmiStatusAdjDao;
    @Autowired
    private VmiStatusAdjLineDao vmiStatusLineAdjDao;
    @Autowired
    private AdCancelDao adCancelDao;

    @Autowired
    private StaCheckLogDao staCheckLogDao;

    @Autowired
    private SysInterfaceQueueManager sysInterfaceQueueManager;
    @Autowired
    private WmsThreePLManager wmsThreePLManager;
    @Autowired
    private CnMsgConfirmReplyDao cnMsgConfirmReplyDao;
    @Autowired
    private CnCancelOrderLogDao cnCancelOrderLogDao;
    @Autowired
    private YamatoConfirmOrderQueueDao yamatoConfirmOrderQueueDao;
    @Autowired
    private NikeCartonNoDao cartonNoDao;
    @Autowired
    private NikeCartonNoLineDao cartonNoLineDao;
    @Autowired
    private GucciManager gucciManager;
    @Autowired
    private HubWmsService hubWmsService;
    @Autowired
    private WmsOrderStatusOmsDao wmsOrderStatusOmsDao;

    @Autowired
    private RocketMQProducerServer producerServer;

    @Resource(name = "mongoTemplate")
    private MongoOperations mongoOperation;
    @Autowired
    private MessageConfigDao messageConfigDao;

    @Autowired
    private WmsConfirmOrderQueueDao wmsConfirmOrderQueueDao;
    @Autowired
    private StaCheckDetialDao staCheckDetialDao;

    @Autowired
    private WmsShippingQueueDao wmsShippingQueueDao;
    @Autowired
    private WmsShippingLineQueueDao wmsShippingLineQueueDao;

    @Autowired
    private MsgRtnOutboundDao msgRtnOutboundDao;
    
    @Autowired
    private CustomsDeclarationDao customsDeclarationDao;
    @Autowired
    private CustomsDeclarationLineDao customsDeclarationLineDao;

    @Autowired
    private MessageProducerErrorDao messageProducerErrorDao;

    @Autowired
    private VmiStaCreateManager vmiStaCreateManager;

    @Autowired
    private StvListenerManager stvListenerManager;

    @Autowired
    private WhContainerDao whContainerDao;

    @Autowired
    private CustomsDeclarationManager customsDeclarationManager;

    @Autowired
    private Wms3AdapterInteractManager wms3AdapterInteractManager;

    @Autowired
    private WmsOrderServiceToHub4Manager wmsOrderServiceToHub4Manager;
    
    @Autowired
    private ChannelWhRefRefDao refDao;
    
    @Autowired
    private WhPingAnCoverManager pinanCoverManager;

    @Value("${levis.owner}")
    public String owner;

    @Autowired
    private StaCancelDao staCancelDao;

    @Autowired
    private StaCartonInfoDao staCartonInfoDao;


    @Override
    public void staOccupied(StockTransApplication sta, Warehouse wh) {
        // IM
        hubWmsService.insertOccupiedAndRelease(sta.getId());

        if (wh.getVmiSource() != null && (Constants.VIM_WH_SOURCE_HD.equals(wh.getVmiSource()) || Constants.VIM_WH_SOURCE_BIAOGAN.equals(wh.getVmiSource()) || Constants.VIM_WH_SOURCE_SF.equals(wh.getVmiSource()))) {
            // 标杆仓库处理
            switch (sta.getType()) {
                case OUTBOUND_OTHERS:
                    generateMsgForVmiOut(sta, wh);
                    break;
                // case OUTBOUND_PURCHASE:
                // generateMsgForVmiOut(sta, wh);
                // break;
                case VMI_RETURN:
                    generateMsgForVmiOut(sta, wh);
                    break;
                case VMI_TRANSFER_RETURN:
                    generateMsgForVmiOut(sta, wh);
                    break;
                default:
                    break;
            }
        } else if (wh.getVmiSource() != null && Constants.VIM_WH_SOURCE_GQS.equals(wh.getVmiSource())) {
            // 高启诗
            switch (sta.getType()) {
                case OUTBOUND_OTHERS:
                    generateMsgForVmiOut(sta, wh);
                    break;
                case VMI_RETURN:
                    generateMsgForVmiOut(sta, wh);
                    break;
                // case OUTBOUND_PURCHASE:
                // generateMsgForVmiOut(sta, wh);
                // break;
                default:
                    break;
            }
        } else if (wh.getVmiSource() != null && Constants.VIM_WH_SOURCE_IDS.equals(wh.getVmiSource())) {
            // IDS 外包仓处理
            if ((StockTransApplicationType.VMI_RETURN.equals(sta.getType()) || StockTransApplicationType.VMI_TRANSFER_RETURN.equals(sta.getType())) && StockTransApplicationStatus.OCCUPIED.equals(sta.getStatus())) {
                wareHouseManager.vmiReturnFeedback(sta);
            }
            if (StockTransApplicationType.OUTBOUND_PURCHASE.equals(sta.getType()) && "1".equals(sta.getIsPf())) {// 唯品会采购出库
                wareHouseManager.vmiReturnFeedbackPf(sta);
            }
        } else if (wh.getVmiSource() != null && Constants.VIM_WH_SOURCE_CONVERSE_IDS.equals(wh.getVmiSource())) {
            if ((StockTransApplicationType.VMI_RETURN.equals(sta.getType()) || StockTransApplicationType.VMI_TRANSFER_RETURN.equals(sta.getType())) && StockTransApplicationStatus.OCCUPIED.equals(sta.getStatus())) {
                wareHouseManager.vmiReturnFeedbackConverse(sta, wh.getVmiSource());
            }
        } else if (wh.getVmiSource() != null) {
            // IDS-NIKE 外包仓处理
            // nike退大仓推送利丰外包仓配置
            ChooseOption op = chooseOptionDao.findByCategoryCodeAndKey(Constants.VIM_WH_SOURCE_IDS, Constants.IDS_VMI_RTO_LF_SOURCES);
            if (op != null && op.getOptionValue() != null) {
                String[] sources = org.apache.commons.lang3.StringUtils.split(op.getOptionValue(), ",");
                List<String> list = Arrays.asList(sources);
                if (list != null && list.size() > 0 && list.contains(wh.getVmiSource())) {
                    if ((StockTransApplicationType.VMI_RETURN.equals(sta.getType()) || StockTransApplicationType.VMI_TRANSFER_RETURN.equals(sta.getType())) && StockTransApplicationStatus.OCCUPIED.equals(sta.getStatus())) {
                        wareHouseManager.vmiReturnFeedbackNike(sta, wh.getVmiSource());
                    }
                }
            }
        }
        switch (sta.getType()) {
            case OUTBOUND_SETTLEMENT:// 结算经销出库
            case OUTBOUND_CONSIGNMENT:// 代销出库
            case OUTBOUND_OTHERS:// 其他出库
            case VMI_RETURN:// VMI退仓
            case VMI_TRANSFER_RETURN:// VMI_TRANSFER_RETURN
            case TRANSIT_CROSS:// 库间移动
            case INVENTORY_LOCK:// 库存锁定
                insertInvChangeData(sta.getId(), StockTransApplicationStatus.OCCUPIED, false);
                break;
            case VMI_OWNER_TRANSFER:// 转店
                if (sta.getSlipCode1() == null) {
                    insertInvChangeData(sta.getId(), StockTransApplicationStatus.OCCUPIED, false);
                }
                break;
            default:
                break;
        }
    }

    private void insertInvChangeData(Long id, StockTransApplicationStatus status, boolean c) {
        // 1:查询是否占用可销售库存
        Boolean b = staLineDao.findIsOccupationInvForSales(id, new SingleColumnRowMapper<Boolean>(Boolean.class));
        if (c) {
            b = c;
        }
        if (b) {
            StockTransApplication sta = staDao.getByPrimaryKey(id);
            WmsInvChangeToOms wio = new WmsInvChangeToOms();
            wio.setStaCode(sta.getCode());
            wio.setStaId(sta.getId());
            wio.setErrorCount(0);
            wio.setCreateTime(new Date());
            wio.setPriority(3);// 默认该类型为3
            wio.setDataStatus(status);// 配货中/取消
            wio.setExeStatus(DefaultStatus.CREATED);// 初始化新建状态
            wio.setOrderType(1);
            wmsInvChangeToOmsDao.save(wio);
        }
    }

    /**
     * 
     * VMI外包仓-过单时记录中间表
     * 
     * @param sta
     */
    private void generateMsgForVmi(StockTransApplication sta, Warehouse wh) {
        boolean b = false;
        if ("1".equals(MSG_OUTBOUNT_CREATETIMELOG)) {
            b = true;
        }
        if (StringUtils.hasText(wh.getVmiSource()) && !owner.equals(sta.getOwner())) {
            // 确认外包仓库，记录通知数据
            BiChannel shop = companyShopDao.getByCode(sta.getOwner());
            StaDeliveryInfo stainfo = sta.getStaDeliveryInfo();
            MsgOutboundOrder msg = new MsgOutboundOrder();
            Long id = warehouseMsgSkuDao.getThreePlSeq(new SingleColumnRowMapper<Long>(Long.class));
            msg.setUuid(id);
            msg.setSource(wh.getVmiSource());
            msg.setSourceWh(wh.getVmiSourceWh());
            msg.setStaCode(sta.getCode());
            msg.setOuterOrderCode(sta.getSlipCode2());
            // 触发器WMS.TRI_T_WH_MSG_OUTBOUND移除
            ChooseOption op = chooseOptionDao.findByCategoryCodeAndKey(Constants.UPDATE_MSG_OUTBOUND_ORDERCODE, Constants.MSG_OUTBOUND_ORDERCODE_IS_OPEN);
            String flag = op == null ? "false" : (op.getOptionValue() == null ? "false" : op.getOptionValue());
            if ("true".equals(flag)) {
                if (sta.getCode() != null) {
                    if (msg.getOuterOrderCode() == null
                            && StockTransApplicationType.OUTBOUND_SALES.equals(sta.getType()) //
                            && (Constants.VIM_WH_SOURCE_NIKE_IDS_005.equals(wh.getVmiSource()) || Constants.VIM_WH_SOURCE_NIKE_IDS_004.equals(wh.getVmiSource()) || Constants.VIM_WH_SOURCE_NIKE_IDS_003.equals(wh.getVmiSource())
                                    || Constants.VIM_WH_SOURCE_NIKE_IDS_002.equals(wh.getVmiSource()) || Constants.VIM_WH_SOURCE_NIKE_IDS.equals(wh.getVmiSource()) || Constants.VIM_WH_SOURCE_NIKE_IDS_TM.equals(wh.getVmiSource()))) {
                        msg.setOuterOrderCode(sta.getSlipCode1());
                    }
                }
            }
            if (sta.getStaDeliveryInfo() != null && StringUtils.hasText(sta.getStaDeliveryInfo().getLpCode())) {
                msg.setLpCode(sta.getStaDeliveryInfo().getLpCode());
            }
            // 如果使用顺丰电子面单的外包仓，则锁定单据号
            if (wh.getIsSfOlOrder() != null && wh.getIsSfOlOrder() && (Transportator.SF.equals(stainfo.getLpCode()) || Transportator.SFCOD.equals(stainfo.getLpCode()) || Transportator.SFDSTH.equals(stainfo.getLpCode()))) {
                msg.setIsLocked(true);
            }
            if (wh.getIsEmsOlOrder() != null && wh.getIsEmsOlOrder() && (Transportator.EMS.equals(stainfo.getLpCode()) || Transportator.EMS_COD.equals(stainfo.getLpCode()))) {
                msg.setIsLocked(true);
            }
            // 如果使用JD电子面单的外包仓，则锁定单据号
            if ((Transportator.JD.equals(stainfo.getLpCode()) || Transportator.JDCOD.equals(stainfo.getLpCode()))) {
                msg.setIsLocked(true);
            }
            // 如果使用Kerry物流的外包仓，则锁定单据号
            if (Transportator.KERRY_A.equals(stainfo.getLpCode()) && wh.getIsKerryaOlOrder()) {
                msg.setIsLocked(true);
            }
            // 如果使用菜鸟集货的外包仓，则锁定单据号
            if (Transportator.CNJH.equals(stainfo.getLpCode())) {
                msg.setIsLocked(true);
            }

            if (stainfo.getIsCod() == null ? false : stainfo.getIsCod()) {
                msg.setPaymentType("1");
            }

            msg.setIsCodOrder(stainfo.getIsCod());
            // 设置收款金额为运费加上商品金额
            stainfo.setTransferFee(stainfo.getTransferFee() == null ? new BigDecimal(0) : stainfo.getTransferFee());
            BigDecimal cost = stainfo.getTransferFee().add(sta.getTotalActual() == null ? new BigDecimal(0) : sta.getTotalActual());
            msg.setTotalActual(cost);

            msg.setTransferFee(stainfo.getTransferFee());
            msg.setMemberEmail(sta.getStaDeliveryInfo().getOrderUserMail());
            msg.setReceiver(stainfo.getReceiver());
            msg.setZipcode(stainfo.getZipcode());
            msg.setStaType(sta.getType().getValue());
            msg.setTelePhone(stainfo.getTelephone());
            msg.setMobile(stainfo.getMobile());
            // 判断外包仓设置是否需要发票信息
            if (wh.getIsOutInvoice() != null && wh.getIsOutInvoice()) {
                msg.setIsNeedInvoice(stainfo.getStoreComIsNeedInvoice() == null ? false : stainfo.getStoreComIsNeedInvoice());
            } else {
                msg.setIsNeedInvoice(false);
            }
            msg.setCountry(stainfo.getCountry());
            msg.setProvince(stainfo.getProvince());
            msg.setCity(stainfo.getCity());
            msg.setDistrict(stainfo.getDistrict());
            msg.setAddress(stainfo.getAddress());
            msg.setTotalActual(sta.getTotalActual());
            msg.setRemark(stainfo.getRemark());
            msg.setStatus(DefaultStatus.CREATED);

            if (b && msg.getIsLocked() != null && msg.getIsLocked()) {
                msg.setCreateTimeLog(new Date());
                msg.setCreateTime(null);
            } else {
                msg.setCreateTime(new Date());
                msg.setCreateTimeLog(new Date());
            }
            msg.setReleaseDate(stainfo.getTransMemo());
            msg.setPaymentType(null);
            msg.setShopId(shop.getId());
            msg.setSlipCode(sta.getRefSlipCode());
            msg.setUserId(stainfo.getOrderUserCode());
            msg.setAddressEnglish(stainfo.getAddressEn());
            msg.setCityEnglish(stainfo.getCityEn());
            msg.setRemarkEnglish(stainfo.getRemarkEn());
            msg.setReceiverEnglish(stainfo.getReceiverEn());
            msg.setProvinceEnglish(stainfo.getProvinceEn());
            msg.setDistrictEnglish(stainfo.getDistrictEn());
            msgOutboundOrderDao.save(msg);
            // 获取作业单行信息
            List<StaLine> stalines = staLineDao.findByStaId(sta.getId());
            for (StaLine line : stalines) {
                MsgOutboundOrderLine msgline = new MsgOutboundOrderLine();
                msgline.setMsgOrder(msg);
                msgline.setQty(line.getQuantity());
                msgline.setSku(line.getSku());
                msgline.setSkuName(line.getSkuName());
                msgline.setTotalActual(line.getTotalActual());
                msgline.setActivitySource(line.getActivitySource());
                BigDecimal unitPrice = line.getTotalActual() == null ? new BigDecimal(0) : line.getTotalActual().divide(new BigDecimal(line.getQuantity()), 2, BigDecimal.ROUND_UP);
                msgline.setUnitPrice(unitPrice);
                List<GiftLine> gls = giftLineDao.getLinesByStalineId(line.getId());
                // 设置特殊包装处理
                if (gls != null && gls.size() > 0) {
                    for (GiftLine gl : gls) {
                        if (GiftType.GIFT_PACKAGE.equals(gl.getType())) {
                            msgline.setLineReserve1(gl.getMemo());
                        }
                    }
                }
                msgOutboundOrderLineDao.save(msgline);
            }

            // 发票外包仓记录逻辑迁移
            // 记录外包仓发票信息 标杆的仓库是除了标杆建材仓库其他的都是不传送发票信息
            if (wh.getIsOutInvoice() != null && wh.getIsOutInvoice() && stainfo.getStoreComIsNeedInvoice() != null && stainfo.getStoreComIsNeedInvoice()) {
                // 查票
                List<StaInvoice> ivList = staInvoiceDao.getBySta(sta.getId());
                for (StaInvoice iv : ivList) {
                    MsgInvoice mic = new MsgInvoice();
                    mic.setAmt1(iv.getAmt());
                    mic.setCreateTime(new Date());
                    mic.setDrawer(iv.getDrawer());
                    mic.setInvoiceTime(iv.getInvoiceDate());
                    mic.setUnitPrice1(iv.getUnitPrice());
                    mic.setItem1(iv.getItem());
                    mic.setPayee(iv.getPayee());
                    mic.setPayer(iv.getPayer());
                    mic.setQty1(iv.getQty() == null ? 0L : iv.getQty().longValue());
                    mic.setRemark(iv.getMemo());
                    mic.setSource(wh.getVmiSource());
                    mic.setSourceWh(wh.getVmiSourceWh());
                    mic.setStaCode(sta.getCode());
                    mic.setUpdateTime(new Date());
                    mic.setStatus(DefaultStatus.CREATED);
                    msgInvoiceDao.save(mic);
                }
            }
            // 根据标识是否需要传递运单号给外包仓
            // TODO:设置货仓配置是否发送快递到外包仓

            if (b) {} else {
                if (wh.getIsOgistics() != null && wh.getIsOgistics()) {
                    try {
                        // 根据物流商设置运单号
                        transOlManager.matchingTransNo(sta.getId(), sta.getStaDeliveryInfo().getLpCode(), sta.getMainWarehouse().getId());
                    } catch (Exception e) {
                        if (e.getMessage() != null) {
                            if (e.getMessage().equals(ErrorCode.EMS_ERROR)) {
                                sta.getStaDeliveryInfo().setLpCode("EMS");
                                msg.setLpCode("EMS");
                                staDao.save(sta);
                                try {
                                    transOlManager.matchingTransNo(sta.getId(), sta.getStaDeliveryInfo().getLpCode(), sta.getMainWarehouse().getId());
                                } catch (Exception e2) {
                                    log.error("", e);
                                    throw new BusinessException(ErrorCode.BUSINESS_EXCEPTION);
                                }
                            } else {
                                log.error("", e);
                                throw new BusinessException(ErrorCode.BUSINESS_EXCEPTION);
                            }

                        } else {
                            log.error("", e);
                            throw new BusinessException(ErrorCode.BUSINESS_EXCEPTION);
                        }

                    }
                }
            }
        }
    }

    /**
     * 销售出库单据修改前置单据状态XS
     * 
     * @param sta
     */
    private void staCreateSendMsgToStore(StockTransApplication sta) {
        // MQ订单状态通知前端商城
        // mqManager.createMqSoResultLog(sta.getId(),
        // MqSoResultLog.MQ_SR_LOG_OP_TYPE_TO_WH);
        // 更新前置单据状态
        // wareHouseManager.salesStaCreateUpdateSlipCode(sta.getRefSlipCode(),
        // sta.getMainWarehouse().getId());
    }

    @Override
    public void staCreaded(StockTransApplication sta, Warehouse wh) {
        switch (sta.getType()) {
            case OUTBOUND_SALES:
                log.info("staCreaded start, LISTERNER OUTBOUND_SALES,  sta is : {},{}", sta.getRefSlipCode(), sta.getSlipCode1());
                staCreateSendMsgToStore(sta);
                updateStaSkuInfo(sta);
                log.info("staCreaded start, LISTERNER OUTBOUND_SALES,updateStaSkuInfo end,  sta is : {},{}", sta.getRefSlipCode(), sta.getSlipCode1());
                checkStaPickingType(sta);
                log.info("staCreaded end, LISTERNER OUTBOUND_SALES,  sta is : {},{}", sta.getRefSlipCode(), sta.getSlipCode1());
                wmsThreePLManager.createCnWmsOrderStatusUpload(sta.getId());
                break;
            case OUT_SALES_ORDER_OUTBOUND_SALES:
                updateStaSkuInfo(sta);
                checkStaPickingType(sta);
                break;
            case INBOUND_PURCHASE:
                break;
            case OUTBOUND_RETURN_REQUEST:
                // 换货出库
                if (SlipType.RETURN_REQUEST.equals(sta.getRefSlipType())) {
                    sta.setIsLocked(true);
                }
                updateStaSkuInfo(sta);
                break;
           case INBOUND_RETURN_REQUEST:
                if (!StringUtils.hasLength(wh.getVmiSource())) {
                    log.error("staCreaded start" + sta.getRefSlipCode() + sta.getSlipCode1());
                    Boolean isSn = staDao.getIsSnOrExpByStaId(sta.getId(), new SingleColumnRowMapper<Boolean>(Boolean.class));
                    Boolean expDate = staDao.getIsSnOrExpByStaId1(sta.getId(), new SingleColumnRowMapper<Boolean>(Boolean.class));
                    Boolean IsRFID = staDao.getIsRFIDBySkuId(sta.getId(), new SingleColumnRowMapper<Boolean>(Boolean.class));
                    log.error("staCreaded start" + sta.getRefSlipCode() + sta.getSlipCode1() + "isSn" + isSn);
                    if ((isSn != null && isSn) || (expDate != null && expDate) || (IsRFID != null && IsRFID)) {
                        // sta.setIsSn(true);
                        // 保存T_WH_STA_CHECK_DETIAL
                        log.error("staCreaded start" + sta.getRefSlipCode() + sta.getSlipCode1() + " start");
                        insertStaCheckDetial(sta);
                    }
                }
                break;
            default:
                break;
        }
        log.info("staCreaded end, out warhouse start,  sta is : {},{}", sta.getRefSlipCode(), sta.getSlipCode1());
        if (StringUtils.hasLength(wh.getVmiSource())) {
            // 外包仓处理通知
            switch (sta.getType()) {
                case OUTBOUND_SALES:// 销售出库
                    generateMsgForVmi(sta, wh);
                    break;
                case OUT_SALES_ORDER_OUTBOUND_SALES:// 外部订单销售出库
                    generateMsgForVmi(sta, wh);
                    break;
                case TRANSIT_CROSS:// 库间移动
                    generateMsgForVmiOut(sta, wh);
                    break;
                case INBOUND_RETURN_REQUEST:// 退换货申请-退货入库
                    wareHouseManager.msgInorder(sta, wh);
                    break;
                case INBOUND_PURCHASE:// 采购入库
                    wareHouseManager.msgInorder(sta, wh);
                    break;
                case INBOUND_SETTLEMENT:// 结算经销入库
                    wareHouseManager.msgInorder(sta, wh);
                    break;
                case INBOUND_OTHERS:// 其他入库
                    wareHouseManager.msgInorder(sta, wh);
                    break;
                case INBOUND_CONSIGNMENT:// 代销入库
                    wareHouseManager.msgInorder(sta, wh);
                    break;
                case INBOUND_GIFT:// 赠品入库
                    wareHouseManager.msgInorder(sta, wh);
                    break;
                case INBOUND_MOBILE:// 移库入库
                    wareHouseManager.msgInorder(sta, wh);
                    break;
                case VMI_INBOUND_CONSIGNMENT:// VMI移库入库
                    wareHouseManager.msgInorder(sta, wh);
                    break;
                case SAMPLE_OUTBOUND:// 样品领用出库
                    generateMsgForVmiOut(sta, wh);
                    break;
                case SERIAL_NUMBER_OUTBOUND:// 串号拆分出库
                    generateMsgForVmiOut(sta, wh);
                    break;
                case SERIAL_NUMBER_GROUP_OUTBOUND:// 串号组合出库
                    generateMsgForVmiOut(sta, wh);
                    break;
                case WELFARE_USE: // 福利领用
                    generateMsgForVmiOut(sta, wh);
                    break;
                case FIXED_ASSETS_USE: // 固定资产领用
                    generateMsgForVmiOut(sta, wh);
                    break;
                case SCARP_OUTBOUND:// 报废出库
                    generateMsgForVmiOut(sta, wh);
                    break;
                case SALES_PROMOTION_USE:// 促销领用
                    generateMsgForVmiOut(sta, wh);
                    break;
                case LOW_VALUE_CONSUMABLE_USE:// 低值易耗品
                    generateMsgForVmiOut(sta, wh);
                    break;
                case SKU_EXCHANGE_OUTBOUND:// 置换出库
                    generateMsgForVmiOut(sta, wh);
                    break;
                case SAME_COMPANY_TRANSFER:// 同公司调拨
                    generateMsgForVmiOut(sta, wh);
                    break;
                case DIFF_COMPANY_TRANSFER:// 不同公司调拨
                    generateMsgForVmiOut(sta, wh);
                    break;
                case SAMPLE_INBOUND:// 样品领用入库
                    wareHouseManager.msgInorder(sta, wh);
                    break;
                case SERIAL_NUMBER_INBOUND:// 串号拆分入库
                    if (sta.getIsLocked() == null || !sta.getIsLocked()) {
                        wareHouseManager.msgInorder(sta, wh);
                    }
                    break;
                case SERIAL_NUMBER_GROUP_INBOUND:// 串号组合入库
                    if (sta.getIsLocked() == null || !sta.getIsLocked()) {
                        wareHouseManager.msgInorder(sta, wh);
                    }
                    break;
                case SKU_RETURN_INBOUND:// 货返入库
                    wareHouseManager.msgInorder(sta, wh);
                    break;
                case SKU_EXCHANGE_INBOUND:// 赠品入库
                    wareHouseManager.msgInorder(sta, wh);
                    break;
                case OUTBOUND_PURCHASE:// 采购出库
                    if (sta.getIsLocked() == null || !sta.getIsLocked()) {
                        if ("1".equals(sta.getIsPf())) {} else {
                            generateMsgForVmiOut(sta, wh);
                        }
                    }
                    break;
                case REAPAIR_OUTBOUND:// 送修出库
                    generateMsgForVmiOut(sta, wh);
                    break;
                case OUTBOUND_SETTLEMENT:// 结算经销出库
                    generateMsgForVmiOut(sta, wh);
                    break;
                case REAPAIR_INBOUND:// 送修入库
                    wareHouseManager.msgInorder(sta, wh);
                    break;
                case VMI_RETURN://VMI退大仓
                	generateMsgForVmiOut(sta, wh);
                	break;
                case VMI_TRANSFER_RETURN: // VMI转店退仓
                	generateMsgForVmiOut(sta, wh);
                	break;
                default:
                    break;
            }
        }
        log.info("staCreaded end, out warhouse end,  sta is : {},{}", sta.getRefSlipCode(), sta.getSlipCode1());
    }

    // /**
    // * 外部退换货处理
    // *
    // * @param sta
    // * @param wh
    // */
    // private void staFinishedForOutReturnRequest(StockTransApplication sta) {
    // 外部订单退货入库反馈(philips)
    // String innerShopCode = sta.getOwner();
    // if (innerShopCode != null) {
    // CompanyShop sh = companyShopDao.findByInnerShopCode(innerShopCode);
    // if (sh != null && sh.getVmiCode() != null) {
    // VmiInterface vf = vmiFactory.getBrandVmi(sh.getVmiCode());
    // vf.generateReturnInboundFeedBack(sta);
    // }
    // }
    // }

    /**
     * 有前置单据退换货入库完成处理
     * 
     * @param sta
     * @param wh
     */
    private void staFinishedForReturnRequest(StockTransApplication sta, Warehouse wh) {
        // 有前置单据退换货执行更新状态
        // wareHouseManager.updateRaInbound(sta);
        // 查询换货出库单据
        StockTransApplication stainfo = staDao.findOutboundReturnRequest(sta.getRefSlipCode());
        if (stainfo != null) {
            // 判断换货出库单据仓库是否是外包仓
            Warehouse outWh = baseManger.findWarehouseByOuId(stainfo.getMainWarehouse().getId());
            if (outWh != null && StringUtils.hasText(outWh.getVmiSource())) {
                // 生成出库通知
                generateRaOutboundMsgForVmi(stainfo, outWh);
            }
        }
        // VMI 品牌 退货入库后调整处理
        String innerShopCode = sta.getOwner();
        if (innerShopCode != null) {
            BiChannel sh = companyShopDao.getByCode(innerShopCode);
            if (sh != null && sh.getVmiCode() != null) {
                VmiInterface vf = vmiFactory.getBrandVmi(sh.getVmiCode());
                if (vf != null) {
                    vf.generateInvStatusChangeByInboundSta(sta);
                }
            }
        }
        // 若店铺上设置为不需要同步so处理反馈信息， 则返回不处理。
        // mqManager.createMqSoResultLog(sta.getId(),
        // MqSoResultLog.MQ_SR_LOG_OP_TYPE_RA_IN);
        // 退换货入库虚拟发票
        // invoiceUtil.registXnTaxTaskAlongWithRaInTask(sta);
    }

    @Override
    public void staFinished(StockTransApplication sta, Warehouse wh) {
        StaCartonInfo staCartonInfo = new StaCartonInfo(sta.getId(), 1, 0);

        switch (sta.getType()) {
            // 采购出库（采购退货出库）
            case OUTBOUND_PURCHASE:
                // 采购出库
                // wareHouseManager.poOutSlipPoStatus(sta);
                // 释放周转箱
                whContainerDao.updateContainerByStaId(sta.getId());
                break;
            // 状态调整的作业单
            case INVENTORY_STATUS_CHANGE:
                // 是speedo品牌的作业单
                if ("SPEEDO".equals(sta.getOwner()) || "speedoSecondTest".equals(sta.getOwner()) || "speedo".equals(sta.getOwner()) || "Speedo官方商城".equals(sta.getOwner()) || "Speedo天猫旗舰店".equals(sta.getOwner())) {
                    vmiStatusSpeedo(sta, wh);
                }
                break;
            // 退换货申请-退货入库
            case INBOUND_RETURN_REQUEST:
                wmExe.deblockingOutboundReturnRequest(sta.getRefSlipCode());
                wmExe.updateReturnPackage(sta, ReturnPackageStatus.ALREADY_IN_STORAGE);
                switch (sta.getRefSlipType()) {
                    case OUT_RETURN_REQUEST:
                        // staFinishedForOutReturnRequest(sta);
                        break;
                    case RETURN_REQUEST:
                        staFinishedForReturnRequest(sta, wh);
                        break;
                    default:
                        break;
                }
                // 退换货入库 ,解冻 换货出库
                if (sta.getAddiOwner() != null && !sta.getAddiOwner().equals("")) {
                    BiChannel bichannel = biChannelDao.getByCode(sta.getOwner());
                    VmiInterface vf = vmiFactory.getBrandVmi(bichannel.getVmiCode());
                    String turnCode = vf.generateRtnStaSlipCode(bichannel.getVmiCode(), StockTransApplicationType.VMI_OWNER_TRANSFER);
                    List<StvLineCommand> lines = stvLineDao.findStvLineQtyByStaId(sta.getId(), new BeanPropertyRowMapperExt<StvLineCommand>(StvLineCommand.class));
                    for (StvLineCommand stvLineCommand : lines) {
                        BiChannel biChannelLine = biChannelDao.getByCode(stvLineCommand.getOwner());
                        Sku sku = skuDao.getByPrimaryKey(stvLineCommand.getSkuId());
                        if (sku.getIsGift() != null && !sku.getIsGift()) {
                            NikeTurnCreate nikeTurnCreate = new NikeTurnCreate();
                            nikeTurnCreate.setCreateTime(new Date());
                            nikeTurnCreate.setFromLocation(bichannel.getVmiCode());
                            nikeTurnCreate.setSku(sku);
                            nikeTurnCreate.setQuantity(stvLineCommand.getQuantity());
                            nikeTurnCreate.setStaCode(sta.getCode());
                            nikeTurnCreate.setToLocation(biChannelLine.getVmiCode());
                            nikeTurnCreate.setStatus(1);
                            nikeTurnCreate.setReferenceNo(turnCode);
                            createDao.save(nikeTurnCreate);
                        }
                    }
                }

                // 保存纸箱数据到数据库，待同步给LMIS
                staCartonInfoDao.save(staCartonInfo);
                break;
            case VMI_OWNER_TRANSFER:
                if (sta.getSlipCode1() != null) {
                    staFinishNoticeOms(sta,wh);
                } else {
                    insertInvChangeData(sta.getId(), StockTransApplicationStatus.FINISHED, true);
                }
                break;
            // 结算经销入库
            case INBOUND_SETTLEMENT:
                // 保存纸箱数据到数据库，待同步给LMIS
                staCartonInfoDao.save(staCartonInfo);
                break;
            // 代销入库
            case INBOUND_CONSIGNMENT:
                // 保存纸箱数据到数据库，待同步给LMIS
                staCartonInfoDao.save(staCartonInfo);
                break;
            // 采购入库
            case INBOUND_PURCHASE:
            // VMI移库入库
            case VMI_INBOUND_CONSIGNMENT:
                Long rootStaId = sta.getGroupSta() == null ? null : sta.getGroupSta().getId();
                if (null != rootStaId) {
                    StockTransApplication rootSta = staDao.getByPrimaryKey(rootStaId);
                    List<StvLineCommand> stvLine = stvLineDao.findOnShelvesByStaId(sta.getId(), new BeanPropertyRowMapperExt<StvLineCommand>(StvLineCommand.class));
                    for (StvLineCommand sl : stvLine) {
                        String ctempKey = sl.getBarCode() + "_" + sl.getIntInvstatusName();
                        List<StaLine> staline = staLineDao.findByStaId(rootStaId);
                        for (StaLine staLine2 : staline) {
                            Sku sku = skuDao.getByPrimaryKey(staLine2.getSku().getId());
                            InventoryStatus status = inventoryStatusDao.getByPrimaryKey(staLine2.getInvStatus().getId());
                            String rtempKey = sku.getBarCode() + "_" + status.getName();
                            if (rtempKey.equals(ctempKey)) {
                                staLine2.setCompleteQuantity(sl.getQuantity() + staLine2.getCompleteQuantity());
                                staLineDao.save(staLine2);
                            }
                        }
                    }
                    staLineDao.flush();
                    // 获得rootSta剩余计划量
                    Long rootSum = staDao.findSurplusPlanByStaId(rootStaId, new SingleColumnRowMapper<Long>(Long.class));
                    StockTransApplicationStatus status = null;
                    Integer type = null;
                    if (rootSum == 0) {
                        type = WhInfoTimeRefNodeType.FINISHED.getValue();
                        status = StockTransApplicationStatus.FINISHED;
                    } else {
                        List<StockTransApplicationCommand> cartonStaList = staDao.findCartonStaListByStaIdSql(rootStaId, new BeanPropertyRowMapper<StockTransApplicationCommand>(StockTransApplicationCommand.class));
                        boolean isForzen = false;
                        for (StockTransApplicationCommand c : cartonStaList) {
                            if (c.getIntStaStatus() == StockTransApplicationStatus.CREATED.getValue() || c.getIntStaStatus() == StockTransApplicationStatus.PARTLY_RETURNED.getValue()) {
                                isForzen = true;
                                break;
                            }
                        }
                        if (isForzen) {
                            status = StockTransApplicationStatus.FROZEN;
                        } else {
                            type = WhInfoTimeRefNodeType.PARTLY_RETURNED.getValue();
                            status = StockTransApplicationStatus.PARTLY_RETURNED;
                        }
                    }
                    if (null != type) {
                        whInfoTimeRefDao.insertWhInfoTime2(rootSta.getRefSlipCode() == null ? rootSta.getCode() : rootSta.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), type, null, rootSta.getMainWarehouse() == null ? null : rootSta
                                .getMainWarehouse().getId());
                    }
                    staDao.updateStaStatusByStaIdAndStatus(rootStaId, status.getValue());
                }
                if (org.apache.commons.lang3.StringUtils.equals(sta.getSystemKey(), Constants.GUCCI_SYSTEM_KEY)) {
                    gucciManager.generateInBoundFeedbackDate(sta);
                }
                if (StringUtils.hasText(sta.getDataSource())) {
                    wmsOrderServiceToHub4Manager.createResponseInfo(sta.getId(), 0);
                } else {
                    // 自动转店
                    vmiStaCreateManager.autoTransferByVmiInbound(sta);
                }

                // 保存纸箱数据到数据库，待同步给LMIS
                staCartonInfoDao.save(staCartonInfo);
                break;
            case INVENTORY_LOCK:
                insertInvChangeData(sta.getId(), StockTransApplicationStatus.CANCELED, false);
                break;
            // 结算经销出库
            case OUTBOUND_SETTLEMENT:
                // 释放周转箱
                whContainerDao.updateContainerByStaId(sta.getId());
                break;
            // 代销出库
            case OUTBOUND_CONSIGNMENT:
                // 释放周转箱
                whContainerDao.updateContainerByStaId(sta.getId());
                break;
            // 其他出库
            case OUTBOUND_OTHERS:
                // VMI退仓
            case VMI_RETURN:
                if (StringUtils.hasText(sta.getDataSource())) {
                    wmsOrderServiceToHub4Manager.createResponseInfo(sta.getId(), 1);
                }
                // 释放周转箱
                whContainerDao.updateContainerByStaId(sta.getId());
                Boolean isBondedWarehouse = wh.getIsBondedWarehouse();
                if (isBondedWarehouse != null && isBondedWarehouse) {// 如果是保税仓插入推送中间表
                    customsDeclarationManager.newCustomsDeclaration(sta, true);
                }
                break;
            // VMI_TRANSFER_RETURN
            case VMI_TRANSFER_RETURN:
                if (StringUtils.hasText(sta.getDataSource())) {
                    wmsOrderServiceToHub4Manager.createResponseInfo(sta.getId(), 1);
                } else {
                    insertInvChangeData(sta.getId(), StockTransApplicationStatus.FINISHED, true);
                }
                // 通知菜鸟大宝
                notifyCaiNiaoDB(sta);
                // 释放周转箱
                whContainerDao.updateContainerByStaId(sta.getId());
                break;
            default:
                break;
        }
    }

    @Override
    public void staCanceled(StockTransApplication sta, Warehouse wh) {
        // IM
        hubWmsService.insertOccupiedAndRelease(sta.getId());
        switch (sta.getType()) {
            case OUTBOUND_SALES:
                // 取消订单
                cancelSo(sta, wh);
                // 更新配货批次状态状态
                if (null != sta.getPickingList()) {
                    wareHouseManager.updatePickinglistToFinish2(sta.getId(), sta.getPickingList().getId(), sta.getMainWarehouse().getId());
                } else {
                    // 扣减计数器
                    if (sta.getPickingType().equals(StockTransApplicationPickingType.SECKILL)) {
                        secKillSkuDao.deleteSecSkillSkuIsSystemByOu(sta.getMainWarehouse().getId(), sta.getSkus());
                    } else if (sta.getPickingType().equals(StockTransApplicationPickingType.SKU_PACKAGE)) {
                        List<Long> idList = staDao.findIfExistsStaNotPick(sta.getPackageSku(), new SingleColumnRowMapper<Long>(Long.class));
                        if (idList.size() == 0) {
                            PackageSku ps = packageSkuDao.getByPrimaryKey(sta.getPackageSku());
                            if (ps.getIsSystem() != null && ps.getIsSystem()) {
                                // 插入新的packageSkuLog
                                packageSkuLogDao.newPackageSkuLog(sta.getPackageSku());
                                // 根据Id删除对应的套装组合商品
                                packageSkuDao.deleteByPrimaryKey(sta.getPackageSku());
                            }
                        }
                    } else if (!sta.getPickingType().equals(StockTransApplicationPickingType.GROUP)) {
                        wareHouseOutBoundManager.sedKillSkuCounterByPickingList(sta);
                    }
                }
                BiChannel bichannel = biChannelDao.getByCode(sta.getOwner());
                if (StringUtils.hasText(bichannel.getVmiCode())) {
                    if (bichannel.getVmiCode().equals("3251")) {
                        String turnCode = "";
                        VmiInterface vf = vmiFactory.getBrandVmi(bichannel.getVmiCode());
                        if (vf != null) {
                            turnCode = vf.generateRtnStaSlipCode(bichannel.getVmiCode(), StockTransApplicationType.VMI_OWNER_TRANSFER);
                        }
                        List<StaLine> staLines = staLineDao.findByStaId(sta.getId());
                        for (StaLine staLine : staLines) {
                            NikeTurnCreate nikeTurnCreate = new NikeTurnCreate();
                            nikeTurnCreate.setCreateTime(new Date());
                            nikeTurnCreate.setFromLocation(bichannel.getVmiCode());
                            nikeTurnCreate.setSku(staLine.getSku());
                            nikeTurnCreate.setQuantity(staLine.getQuantity());
                            nikeTurnCreate.setStaCode(sta.getCode());
                            nikeTurnCreate.setToLocation("3250");
                            nikeTurnCreate.setStatus(1);
                            nikeTurnCreate.setReferenceNo(turnCode);
                            createDao.save(nikeTurnCreate);
                        }
                    }
                }
                break;
            case OUT_SALES_ORDER_OUTBOUND_SALES:
                if (null != sta.getPickingList()) {
                    wareHouseManager.updatePickinglistToFinish2(sta.getId(), sta.getPickingList().getId(), sta.getMainWarehouse().getId());
                } else {
                    // 扣减计数器
                    if (sta.getPickingType().equals(StockTransApplicationPickingType.SECKILL)) {
                        secKillSkuDao.deleteSecSkillSkuIsSystemByOu(sta.getMainWarehouse().getId(), sta.getSkus());
                    } else if (sta.getPickingType().equals(StockTransApplicationPickingType.SKU_PACKAGE)) {
                        List<Long> idList = staDao.findIfExistsStaNotPick(sta.getPackageSku(), new SingleColumnRowMapper<Long>(Long.class));
                        if (idList.size() == 0) {
                            PackageSku ps = packageSkuDao.getByPrimaryKey(sta.getPackageSku());
                            if (ps.getIsSystem() != null && ps.getIsSystem()) {
                                // 插入新的packageSkuLog
                                packageSkuLogDao.newPackageSkuLog(sta.getPackageSku());
                                // 根据Id删除对应的套装组合商品
                                packageSkuDao.deleteByPrimaryKey(sta.getPackageSku());
                            }
                        }
                    } else if (!sta.getPickingType().equals(StockTransApplicationPickingType.GROUP)) {
                        wareHouseOutBoundManager.sedKillSkuCounterByPickingList(sta);
                    }
                }
                break;
            case OUTBOUND_SETTLEMENT:// 结算经销出库
                whContainerDao.updateContainerByStaId(sta.getId());
                break;
            case OUTBOUND_CONSIGNMENT:// 代销出库
                whContainerDao.updateContainerByStaId(sta.getId());
                break;
            case OUTBOUND_OTHERS:// 其他出库
            case VMI_RETURN:// VMI退仓
                whContainerDao.updateContainerByStaId(sta.getId());
                break;
            case VMI_TRANSFER_RETURN:// VMI_TRANSFER_RETURN
                whContainerDao.updateContainerByStaId(sta.getId());
                break;
            case OUTBOUND_PURCHASE:// 采购出库
                whContainerDao.updateContainerByStaId(sta.getId());
                break;
            case TRANSIT_CROSS:// 库间移动
                insertInvChangeData(sta.getId(), StockTransApplicationStatus.CANCELED, false);
                break;
            case VMI_OWNER_TRANSFER:// 转店
                if (sta.getSlipCode1() == null) {
                    insertInvChangeData(sta.getId(), StockTransApplicationStatus.CANCELED, false);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void staCancelUndo(StockTransApplication sta, Warehouse wh) {
        switch (sta.getType()) {
            case OUTBOUND_SALES:
                // 取消订单
                cancelSo(sta, wh);
                // 取消顺风单据
                cancelSfOrder(sta, wh);
                break;
            case OUT_SALES_ORDER_OUTBOUND_SALES:
                // 取消顺风单据
                cancelSfOrder(sta, wh);
                break;
            case OUTBOUND_RETURN_REQUEST:
                // 取消顺风单据
                cancelSfOrder(sta, wh);
                break;
            default:
                break;
        }
    }

    @Override
    public void staChecked(StockTransApplication sta, Warehouse wh) {
        // 设置发票SN号回填
        snDao.flush();
        // 合并订单无包裹信息
        if (null != sta.getIsMerge() && true == sta.getIsMerge()) {
            List<PackageInfo> pilist = packageInfoDao.getByStaId(sta.getId());
            if (pilist == null || pilist.size() == 0) {
                throw new BusinessException(ErrorCode.MEARGE_PACKAGE_IS_NULL);
            }
        }
        // 查询 StaLine 中是否存在SN商品
        Integer snCount = staLineDao.findSkuSN(sta.getId(), new SingleColumnRowMapper<Integer>(Integer.class));
        if (snCount != null && snCount > 0) {
            // 获取发票信息
            List<StaInvoice> ivlsit = staInvoiceDao.getBySta(sta.getId());
            // 存在发票填写SN号
            if (ivlsit != null && ivlsit.size() > 0) {
                // 当SN商品作业单
                // 获取所有已核对Sn号
                StockTransVoucher stv = stvDao.findStvCreatedByStaId(sta.getId());
                List<SkuSnCommand> snList = snDao.findAllSnListByStvWithStatus(stv.getId(), SkuSnStatus.CHECKING.getValue(), new BeanPropertyRowMapper<SkuSnCommand>(SkuSnCommand.class));
                // 填写发票SN号
                if (snList != null && snList.size() > 0) {
                    for (StaInvoice iv : ivlsit) {
                        List<StaInvoiceLine> sl = invoiceLineDao.findSkuAndQtyByInvoice(iv.getId(), new BeanPropertyRowMapper<StaInvoiceLine>(StaInvoiceLine.class));
                        for (StaInvoiceLine l : sl) {
                            int i = 0;
                            for (SkuSnCommand skuSn : snList) {
                                if (i < l.getQty() && l.getId().equals(skuSn.getSkuid())) {
                                    if (skuSn.getIsSeted()) {
                                        continue;
                                    }
                                    if (iv.getMemo() == null) {
                                        i++;
                                        iv.setMemo(skuSn.getSn());
                                        skuSn.setIsSeted(true);
                                    } else if (iv.getMemo() != null && iv.getMemo().length() <= 250) {
                                        i++;
                                        String memo = iv.getMemo() + "," + skuSn.getSn();
                                        if (memo.length() <= 250) {
                                            iv.setMemo(memo);
                                            skuSn.setIsSeted(true);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        wmsThreePLManager.createCnWmsOrderStatusUpload(sta.getId());
    }

    /**
     * 出库注册在线快递订单回传信息
     * 
     * @param sta
     */
    private void staIntransitForOnLineTransOrder(StockTransApplication sta) {
        String lpcode = sta.getStaDeliveryInfo().getLpCode();
        if (log.isDebugEnabled()) {
            log.debug("staIntransitForOnLineTransOrder staCode:{}, lpcode:{}", sta.getCode(), lpcode);
        }
        TransOlInterface transol = transOnLineFactory.getTransOnLine(lpcode, sta.getMainWarehouse().getId());
        if (transol != null) {
            transol.setRegistConfirmOrder(sta, null);
        }
    }

    @Override
    public void staIntransit(StockTransApplication sta, Warehouse wh) {
        switch (sta.getType()) {
            case OUTBOUND_RETURN_REQUEST:
                // 处理退换货
                if (SlipType.RETURN_REQUEST.equals(sta.getRefSlipType())) {
                    staFinishNoticeOms(sta,wh);
                    // 换出记录包裹信息
                    ChooseOption joinSDChannelCh = chooseOptionManager.findChooseOptionByCategoryCodeAndKey("joinSDChannel", sta.getOwner());
                    if (null != joinSDChannelCh) {
                        staFinishNotifyPMS(sta);
                    }
                    // o2o订单需推送包裹信息至SD
                    getO2oSta(sta);
                }
                // 出库注册在线快递订单回传信息
                staIntransitForOnLineTransOrder(sta);
                // 通知菜鸟大宝
                notifyCaiNiaoDB(sta);
                break;
            case TRANSIT_CROSS:// 库间移动
            case SAME_COMPANY_TRANSFER:// 同公司调拨
            case DIFF_COMPANY_TRANSFER:// 不同公司调拨
                staIntransitTransitCrossForOutWh(sta);
                break;
            case OUTBOUND_SALES:
                staFinishNoticeOms(sta,wh);
                staIntransitSalesOrder(sta);
                // 将快递同步到快递雷达
                // extractFromStaInfo(sta);
                // 万象发货数据插入中间表
                // insertWxOrderQueue(sta);
                // 计算快递预计到达时间
                expressPlanTime(sta);
                // 通知菜鸟大宝
                notifyCaiNiaoDB(sta);
                // 保存NIKE定制信息
                BiChannel biChannel = biChannelDao.getByCode(sta.getOwner());
                if (biChannel.getIsPackingList() != null && biChannel.getIsPackingList()) {
                    saveCartonNo(sta);
                }
                /**
                 * TODO 包裹出库RMI通知PMS PMS独立项目之后再改成RMI接口
                 */
                ChooseOption joinSDChannelCh = chooseOptionManager.findChooseOptionByCategoryCodeAndKey("joinSDChannel", sta.getOwner());
                if (null != joinSDChannelCh) {
                    staFinishNotifyPMS(sta);
                }
                // o2o订单需推送包裹信息至SD
                getO2oSta(sta);
                // 包裹已转出生成yamato反馈hub对列
                if (staDeliveryInfoDao.getByPrimaryKey(sta.getId()).getLpCode().equals("ZJB")) {
                    YamatoConfirmOrderQueue ymt = new YamatoConfirmOrderQueue();
                    StaDeliveryInfo sd = staDeliveryInfoDao.getByPrimaryKey(sta.getId());
                    ymt.setMailno(sd.getTrackingNo());
                    ymt.setStaCode(sta.getCode());
                    ymt.setExeCount(0L);
                    ymt.setCreateTime(new Date());
                    ymt.setStaId(sta.getId());
                    // 在yamato反馈队列表中吧获取到的运单号和作业单code绑定
                    yamatoConfirmOrderQueueDao.save(ymt);
                }
                createPingAnCoverInfo(sta);//生成平安投保数据
                break;
            case OUT_SALES_ORDER_OUTBOUND_SALES:
                staIntransitForBrandOutSalesOrder(sta);
                break;
            default:
                break;
        }
        // ********************
        // 更新tracking_and_sku
        this.updateTrackingAndSku(sta);
    }

    /**
     * 通知菜鸟出库
     * 
     * @param sta
     */
    private void notifyCaiNiaoDB(StockTransApplication sta) {
        if (Constants.CAINIAO_DB_SYSTEM_KEY.equals(sta.getSystemKey())) {
            // 判断单据是否被取消掉
            List<CnCancelOrderLog> olList = cnCancelOrderLogDao.getCnCancelOrderLogByOrderCode(sta.getRefSlipCode());
            if (olList != null && olList.size() > 0) {
                throw new BusinessException(ErrorCode.STA_IS_CANCEL);
            }

            CnMsgConfirmReply cr = new CnMsgConfirmReply();
            cr.setStaId(sta.getId());
            cr.setStaCode(sta.getCode());
            cr.setStatus(0);
            cnMsgConfirmReplyDao.save(cr);
        }
    }

    /**
     * 查询O2O订单, sta.to_location not null
     * 
     * @param sta
     */
    @Override
    public void getO2oSta(StockTransApplication sta) {
        if (StringUtils.hasText(sta.getStorecode())) {
            try {
                StaDeliveryInfo sd = staDeliveryInfoDao.getByPrimaryKey(sta.getId());
                if (sd != null) {
                    List<PackageInfo> infos = sd.getPackageInfos();
                    if (infos != null && infos.size() > 0) {
                        // 子母单的情况会有多个运单号
                        for (PackageInfo packageInfo : infos) {
                            sysInterfaceQueueManager.saveSysInterfaceQueue(packageInfo.getTrackingNo(), packageInfo.getLpCode(), sta.getRefSlipCode(), sta.getSlipCode2(), SysInterfaceQueueType.PARCEL_DELIVERED_NOTIFY_SD,
                                    SysInterfaceQueueSysType.G_SHOPDOG, SysInterfaceQueueStatus.STATUS_NEW);
                        }
                    }
                }
            } catch (Exception e) {
                log.error("com.jumbo.wms.manager.listener.StaListenerManagerImpl.getO2oSta(StockTransApplication) Execution error , omsOrderCode [" + sta.getSlipCode1() + "], please check~");
            }
        }
    }

    /**
     * 通用出入库完成调用oms接口方法
     * 
     * @param sta
     * @throws Exception
     */
    @Transactional(readOnly = true)
    private void staFinishNoticeOms(StockTransApplication sta,Warehouse wh) {
        StockTransApplication sta1 = staDao.getByPrimaryKey(sta.getId());
        if (StringUtils.hasText(sta.getDataSource())) {
            // wmsOrderServiceToHub4Manager.createResponseInfo(sta.getId(), null);
            return;
        }
        Boolean b = false;
        Boolean isMq = false;
        List<MessageConfig> configs = messageConfigDao.findMessageConfigByParameter(null, MqStaticEntity.WMS3_SALE_OUTBOUNT, null, new BeanPropertyRowMapper<MessageConfig>(MessageConfig.class));
        if (configs != null && !configs.isEmpty() && configs.get(0).getIsOpen() == 1) {// 开
            isMq = true;
        }
        if (log.isDebugEnabled()) {
            log.debug("sta intransit or finished notify pac start, staCode is:{}, staType is:{}", sta1.getCode(), sta1.getType());
        }
        // 取本次反馈日志最大的执行时间
        staDao.flush();
        if ("1".equals(sta.getIsPreSale())) {
            b = true;
        }
        OperationBill ob = new OperationBill();
        // 优化：销售出和换货出不执行查询
        if (!sta.getType().equals(StockTransApplicationType.OUTBOUND_SALES) && !sta.getType().equals(StockTransApplicationType.OUTBOUND_RETURN_REQUEST)) {
            Date maxTransactionTime = staDao.getMaxTransactionTime(sta1.getId(), new SingleColumnRowMapper<Date>(Date.class));
            ob.setMaxTransactionTime(maxTransactionTime);
        }
        ob.setCode(sta1.getCode());
        ob.setSlipCode(sta1.getRefSlipCode());
        ob.setType(sta1.getType().getValue());
        ob.setMemo(sta1.getMemo());
        ob.setWhCode(sta1.getMainWarehouse().getCode());
        StockTransApplicationType staType = sta.getType();
        if (staType.equals(StockTransApplicationType.INBOUND_RETURN_REQUEST)) {
            if (sta.getStaDeliveryInfo().getReturnReasonType() != null) {
                List<ChooseOption> statusList = chooseOptionDao.findOptionListByCategoryCode("returnReasonType");
                for (ChooseOption chooseOption : statusList) {
                    if (ReturnReasonType.valueOf(sta.getStaDeliveryInfo().getReturnReasonType().getValue()).toString().equals(chooseOption.getOptionKey())) {
                        ob.setInBoundRemark(chooseOption.getOptionValue() + ":" + (sta.getStaDeliveryInfo().getReturnReasonMemo() == null ? "" : sta.getStaDeliveryInfo().getReturnReasonMemo()));
                        ob.setInboundRemarkCode(String.valueOf(sta.getStaDeliveryInfo().getReturnReasonType().getValue()));
                    }
                }
            }
        }
        ob.setAddWhCode(sta1.getAddiWarehouse() == null ? null : sta1.getAddiWarehouse().getCode());
        switch (sta.getType()) {
            case OUTBOUND_SALES:
            case OUTBOUND_RETURN_REQUEST:
                if (log.isDebugEnabled()) {
                    log.debug("staFinishNoticeOms staCode:{}, sta has systemKey:{}", sta1.getCode(), StringUtils.hasText(sta.getSystemKey()));
                }
                if (StringUtils.hasText(sta.getSystemKey())) {
                    if (log.isDebugEnabled()) {
                        log.debug("WmsOrderStatusOms is" + sta.getCode());
                    }
                    WmsOrderStatusOms statusOms = new WmsOrderStatusOms();
                    statusOms.setLogTime(new Date());
                    statusOms.setSystemKey(sta1.getSystemKey());
                    statusOms.setOrderCode(sta1.getRefSlipCode());
                    statusOms.setShippingCode(sta1.getCode());
                    statusOms.setType(sta1.getType().getValue());
                    statusOms.setOwner(sta1.getOwner());
                    statusOms.setStatus(1);
                    if (b) {
                        statusOms.setIsLock(true);// 预售订单 锁定
                    }
                    if (isMq && null != sta.getSystemKey() && !"adidas".equals(sta.getSystemKey())) {
                        statusOms.setIsMq("1");// mq 发送
                        statusOms.setCreatetime(new Date());
                    }
                    orderStatusOmsDao.save(statusOms);
                    // 记录出入库物流信息
                    if (sta.getGroupSta() != null) {
                        List<PackageInfo> infos = infoDao.findByStaId(sta1.getGroupSta().getId());
                        for (PackageInfo info : infos) {
                            WmsTransInfoOms infoOms = new WmsTransInfoOms();
                            infoOms.setOrderStatusOms(statusOms);
                            infoOms.setTransCode(info.getLpCode());
                            infoOms.setTransNo(info.getTrackingNo());
                            if (sta.getStaDeliveryInfo().getTransTimeType() != null) {
                                infoOms.setTransTimeType(sta.getStaDeliveryInfo().getTransTimeType().getValue());
                            }
                            infoOmsDao.save(infoOms);
                        }
                    } else {
                        List<PackageInfo> infos = infoDao.findByStaId(sta.getId());
                        for (PackageInfo info : infos) {
                            WmsTransInfoOms infoOms = new WmsTransInfoOms();
                            infoOms.setOrderStatusOms(statusOms);
                            infoOms.setTransCode(info.getLpCode());
                            infoOms.setTransNo(info.getTrackingNo());
                            if (sta.getStaDeliveryInfo().getTransTimeType() != null) {
                                infoOms.setTransTimeType(sta.getStaDeliveryInfo().getTransTimeType().getValue());
                            }
                            infoOmsDao.save(infoOms);
                        }
                    }

                    Map<String,String> skuLineNoMap=new HashMap<String,String>();
                    List<StaLine> staLineList=staLineDao.findByStaId(sta.getId());
                    if(null!=staLineList&&staLineList.size()>0){
                        for(StaLine staLine:staLineList){
                            skuLineNoMap.put(staLine.getSku().getId()+"-"+staLine.getQuantity(),staLine.getLineNo());
                        }
                    }
                    // 记录出入库流水
                    StockTransVoucher stv = stvDao.findStvBySta(sta1.getId(), new BeanPropertyRowMapper<StockTransVoucher>(StockTransVoucher.class));
                    List<StockTransTxLogCommand> stockTransTxLogs = stockTransTxLogDao.findLogByStvId(stv.getId(), new BeanPropertyRowMapper<StockTransTxLogCommand>(StockTransTxLogCommand.class));
                    Map<String, List<SkuSnLogCommand>> map = new HashMap<String, List<SkuSnLogCommand>>();
                    for (StockTransTxLogCommand logCommand : stockTransTxLogs) {
                        WmsInfoLogOms infoLogOms = new WmsInfoLogOms();
                        InventoryStatus status = inventoryStatusDao.getByPrimaryKey(logCommand.getStatusId());
                        Sku sku = skuDao.getByPrimaryKey(logCommand.getSkuId());
                        infoLogOms.setSku(sku.getCustomerSkuCode());
                        infoLogOms.setBtachCode(logCommand.getBatchCode());
                        infoLogOms.setTranTime(logCommand.getTranTime());
                        infoLogOms.setOwner(logCommand.getOwner());
                        if(skuLineNoMap.containsKey(logCommand.getSkuId()+"-"+logCommand.getQuantity())){
                            infoLogOms.setLineNo(skuLineNoMap.get(logCommand.getSkuId()+"-"+logCommand.getQuantity()));
                        }
                        // 新增是否可售By FXL
                        if (logCommand.getMarketAbility() == 1) {
                            infoLogOms.setIsSales(true);
                        } else {
                            infoLogOms.setIsSales(false);
                        }
                        if (status.getName().equals("良品")) {
                            infoLogOms.setInvStatus("normal");
                        } else if (status.getName().equals("残次品")) {
                            infoLogOms.setInvStatus("damage");
                        } else {
                            infoLogOms.setInvStatus("pending");
                        }
                        OperationUnit operationUnit = operationUnitDao.getByPrimaryKey(logCommand.getWhId());
                        infoLogOms.setQty(logCommand.getQuantity());
                        infoLogOms.setWarehouceCode(operationUnit.getCode());
                        sku.setIsSnSku(sku.getIsSnSku() == null ? false : sku.getIsSnSku());
                        if (sku.getIsSnSku()) {
                            List<SkuSnLogCommand> mapstvcmd = map.get(sku.getId().toString());
                            if (mapstvcmd != null) {
                                Long qty = logCommand.getQuantity();
                                for (int i = 0; i < mapstvcmd.size(); i++) {
                                    if (qty == mapstvcmd.size()) {
                                        if (infoLogOms.getSns() == null) {
                                            infoLogOms.setSns(mapstvcmd.get(i).getSn());
                                        } else {
                                            infoLogOms.setSns(infoLogOms.getSns() + "," + mapstvcmd.get(i).getSn());
                                        }
                                    } else {

                                        if (infoLogOms.getSns() == null) {
                                            infoLogOms.setSns(mapstvcmd.get(i).getSn());
                                            --qty;
                                        } else {
                                            infoLogOms.setSns(infoLogOms.getSns() + "," + mapstvcmd.get(i).getSn());
                                            --qty;
                                        }
                                        if (qty == 0) {
                                            String[] sn = infoLogOms.getSns().split(",");
                                            for (int j = 0; j < sn.length; j++) {
                                                for (int h = 0; h < mapstvcmd.size(); h++) {
                                                    if (sn[j].equals(mapstvcmd.get(h).getSn())) {
                                                        mapstvcmd.remove(h);
                                                    }
                                                }
                                            }
                                            map.put(sku.getId().toString(), mapstvcmd);
                                            break;
                                        }
                                    }
                                }

                            } else {
                                List<SkuSnLogCommand> stvcmd = skuSnLogDao.findOutboundSnBySkuId(stv.getId(), sku.getId(), new BeanPropertyRowMapper<SkuSnLogCommand>(SkuSnLogCommand.class));
                                Long qty = logCommand.getQuantity();
                                for (int i = 0; i < stvcmd.size(); i++) {
                                    if (qty == stvcmd.size()) {
                                        if (infoLogOms.getSns() == null) {
                                            infoLogOms.setSns(stvcmd.get(i).getSn());
                                        } else {
                                            infoLogOms.setSns(infoLogOms.getSns() + "," + stvcmd.get(i).getSn());
                                        }
                                    } else {
                                        if (infoLogOms.getSns() == null) {
                                            infoLogOms.setSns(stvcmd.get(i).getSn());
                                            --qty;
                                        } else {
                                            infoLogOms.setSns(infoLogOms.getSns() + "," + stvcmd.get(i).getSn());
                                            --qty;
                                        }
                                    }
                                    if (qty == 0) {
                                        String[] sn = infoLogOms.getSns().split(",");
                                        for (int j = 0; j < sn.length; j++) {
                                            for (int h = 0; h < stvcmd.size(); h++) {
                                                if (sn[j].equals(stvcmd.get(h).getSn())) {
                                                    stvcmd.remove(h);
                                                }
                                            }
                                        }
                                        map.put(sku.getId().toString(), stvcmd);
                                        break;
                                    }

                                }
                            }
                        }

                        infoLogOms.setWmsOrderStatusOms(statusOms);
                        infoLogOmsDao.save(infoLogOms);
                    }
                    map.clear();
                    // 记录发票信息
                    List<StaInvoice> invoices = staInvoiceDao.getBySta(sta1.getId());
                    for (StaInvoice invoice : invoices) {
                        WmsOrderInvoiceOms invoiceOms = new WmsOrderInvoiceOms();
                        invoiceOms.setAmt(invoice.getAmt());
                        invoiceOms.setDrawer(invoice.getDrawer());
                        invoiceOms.setInvoiceDate(invoice.getInvoiceDate());
                        invoiceOms.setItem(invoice.getItem());
                        invoiceOms.setInvoiceCode(invoice.getInvoiceCode());
                        invoiceOms.setMemo(invoice.getMemo());
                        invoiceOms.setCompany(invoice.getCompany());
                        invoiceOms.setPayee(invoice.getPayee());
                        invoiceOms.setPayer(invoice.getPayer());
                        invoiceOms.setQty(invoice.getQty().intValue());
                        invoiceOms.setOrderStatusOms(statusOms);
                        invoiceOms.setUnitPrice(invoice.getUnitPrice());
                        invoiceOmsDao.save(invoiceOms);
                        List<StaInvoiceLine> invoiceLines = invoiceLineDao.getByStaInvoiceId(invoice.getId());
                        for (StaInvoiceLine staInvoiceLine : invoiceLines) {
                            WmsOrderInvoiceLineOms invoiceLineOms = new WmsOrderInvoiceLineOms();
                            invoiceLineOms.setAmt(staInvoiceLine.getAmt());
                            invoiceLineOms.setInvoiceId(invoiceOms);
                            invoiceLineOms.setLineNo(staInvoiceLine.getLineNO());
                            invoiceLineOms.setItem(staInvoiceLine.getItem());
                            invoiceLineOms.setQty(staInvoiceLine.getQty());
                            invoiceLineOms.setUnitPrice(staInvoiceLine.getUnitPrice());
                            invoiceLineOmsDao.save(invoiceLineOms);

                        }
                    }
                } else {
                    WmsIntransitNoticeOms notice = new WmsIntransitNoticeOms();
                    notice.setStaCode(sta1.getCode());
                    notice.setStaId(sta1.getId());
                    notice.setCreateTime(new Date());
                    notice.setLastModifyTime(new Date());
                    notice.setWhOuId(sta1.getMainWarehouse());
                    notice.setOwner(sta1.getOwner());
                    notice.setErrorCount(0l);
                    notice.setIsSend(0l);
                    if (b) {
                        notice.setIsLock(true);// 预售订单
                    }
                    wmsIntransitNoticeOmsDao.save(notice);
                    wmsIntransitNoticeOmsDao.flush();
                }
                
                Boolean isBondedWarehouse= wh.getIsBondedWarehouse();
                if(isBondedWarehouse !=null && isBondedWarehouse){//如果是保税仓插入推送中间表
                    customsDeclarationManager.newCustomsDeclaration(sta, true);
                }
                return;
            case VMI_OWNER_TRANSFER:
                ob.setSlipCode(sta.getSlipCode1());
                ob.setDirection(OperationBill.IN_AND_OUT);
                List<StvLineCommand> allList = stvLineDao.getFinishedStvLineByStaId(sta1.getId(), new BeanPropertyRowMapper<StvLineCommand>(StvLineCommand.class));
                List<OperationBillLine> outList = new ArrayList<OperationBillLine>();
                List<OperationBillLine> inList = new ArrayList<OperationBillLine>();
                for (StvLineCommand sc : allList) {
                    OperationBillLine line = new OperationBillLine();
                    line.setSkuCode(sc.getSkuCode());
                    // 新增是否可销售
                    line.setMarketability(sc.getMarketAbility() == 1 ? true : false);
                    line.setInvBatchCode(sc.getBatchCode());
                    line.setInvStatusCode(sc.getIntInvstatusName());

                    line.setQty(sc.getQuantity());
                    line.setShopCode(sc.getOwner());
                    line.setWhCode(sc.getOuCode());
                    if (sc.getDirectionInt() == 2) {
                        outList.add(line);
                    } else {
                        inList.add(line);
                    }
                }
                ob.setOutboundLines(outList);
                ob.setInboundLines(inList);
                break;
            default:
                break;
        }
        try {
            if (log.isInfoEnabled()) {
                log.info("rmi call pac wmsOperationsFeedback start, staCode:{}, slipCode:{}, staType:{}", new Object[] {sta1.getCode(), sta1.getRefSlipCode(), sta1.getType()});
            }
            BaseResult rs = rmi4Wms.wmsOperationsFeedback(ob);
            if (log.isInfoEnabled()) {
                log.info("rmi call pac wmsOperationsFeedback end, staCode:{}, slipCode:{}, staType:{}", new Object[] {sta1.getCode(), sta1.getRefSlipCode(), sta1.getType()});
            }
            if (rs.getStatus() == 0) {
                throw new BusinessException(ErrorCode.OMS_SYSTEM_ERROR, new Object[] {rs.getMsg()});
            }
        } catch (BusinessException e) {
            log.debug("Call oms outbound response interface error:");
            log.error("staCode:" + sta1.getCode(), e);
            throw e;
        } catch (Exception e) {
            log.debug("Call oms outbound response interface error:");
            log.error("staCode:" + sta1.getCode(), e);
            throw new BusinessException(ErrorCode.RMI_OMS_FAILURE);
        }
        if (log.isDebugEnabled()) {
            log.debug("sta intransit or finished notify pac end, staCode is:{}, staType is:{}", sta1.getCode(), sta1.getType());
        }
    }

    @Transactional
    private void updateTrackingAndSku(StockTransApplication sta) {
        long staId = sta.getId();
        staAdditionalLineDao.flush();
        try {
            StockTransApplication stock = staDao.findGroupIdByStuId(staId, new BeanPropertyRowMapperExt<StockTransApplication>(StockTransApplication.class));
            if (null != stock && null != stock.getGroupSta()) {
                staId = stock.getGroupSta().getId();
            }
            List<StaAdditionalLineCommand> staList = staAdditionalLineDao.findTrackingAndSkuByStuId(staId, new BeanPropertyRowMapperExt<StaAdditionalLineCommand>(StaAdditionalLineCommand.class));
            if (staList != null && staList.size() > 0) {
                StringBuffer sb = new StringBuffer();
                for (StaAdditionalLineCommand staAdditionalLineCommand : staList) {
                    sb.append(staAdditionalLineCommand.getTrackingValue()).append(":").append(staAdditionalLineCommand.getSkuCode()).append(";");
                }
                String trackingAndSku = sb.toString().substring(0, sb.toString().length() - 1);
                if (!StringUtil.isEmpty(trackingAndSku) && trackingAndSku.length() > 3000) {
                    trackingAndSku = trackingAndSku.substring(0, 3000);
                }
                staAdditionalLineDao.updateTrackingAndSkuByStuId(trackingAndSku, staId);
            }
        } catch (Exception e) {
            log.error("", e);
        }

    }

    /**
     * 销售订单作业单出库更新前置订单状态
     * 
     * @param sta
     */
    private void staIntransitSalesOrder(StockTransApplication sta) {
        if (log.isDebugEnabled()) {
            log.debug("----------------------staIntransitSalesOrder-----------------start------ staCode:{}", sta.getCode());
        }
        wareHouseManager.sendSms(sta);
        // MQ 销售出库
        // mqManager.createMqSoResultLog(sta.getId(),
        // MqSoResultLog.MQ_SR_LOG_OP_TYPE_DELIVERY);
        // 出库注册在线快递订单回传信息
        staIntransitForOnLineTransOrder(sta);
        if (log.isDebugEnabled()) {
            log.debug("----------------------staIntransitSalesOrder-----------------end------ staCode:{}", sta.getCode());
        }
    }

    /**
     * 品牌外部销售出库单据
     * 
     * @param sta
     */
    private void staIntransitForBrandOutSalesOrder(StockTransApplication sta) {
        staIntransitForOnLineTransOrder(sta);
        // CompanyShop shop = companyShopDao.getByinnerShopCode(sta.getOwner());
        // if (shop != null) {
        // String vmiCode = shop.getVmiCode();
        // if (vmiCode != null) {
        // VmiInterface vmi = vmiFactory.getBrandVmi(vmiCode);
        // if (vmi != null) {
        // vmi.generateSalesOutboundFeedBack(sta);
        // }
        // }
        // }
    }

    /**
     * 外包仓库间移动出库处理
     * 
     * @param sta
     */
    private void staIntransitTransitCrossForOutWh(StockTransApplication sta) {
        // 处理外包仓库间移动
        if (sta.getAddiWarehouse() != null) {
            // 查询目标仓库是否是VMI外包仓库
            Warehouse addWh = baseManger.findWarehouseByOuId(sta.getAddiWarehouse().getId());
            if (addWh != null && StringUtils.hasText(addWh.getVmiSource())) {
                MsgInboundOrder msgInorder = wareHouseManager.msgInorder(sta, addWh);
                VmiWarehouseInterface vw = vmiWarehouseFactory.getVmiWarehouse(addWh.getVmiSource());
                if (vw != null) {
                    if (log.isDebugEnabled()) {
                        log.debug("staIntransitTransitCrossForOutWh staCode:{}, sta addiWarehouse is not null:{}, addWh vmiSource:{}", new Object[] {sta.getCode(), true, addWh.getVmiSource()});
                    }
                    // 入库通知
                    vw.inboundNotice(msgInorder);
                }
            }
        }
    }

    /**
     * 取消销售订单相关信息
     * 
     * @param sta
     * @param wh
     */
    private void cancelSo(StockTransApplication sta, Warehouse wh) {
        // wareHouseManager.updateSoCancel(sta);
        if (wh != null && StringUtils.hasText(wh.getVmiSource())) {
            msgOutboundOrderCancelDao.updateStaById(sta.getCode(), DefaultStatus.FINISHED.getValue());
        }
    }

    /**
     * 取消顺丰下单记录
     * 
     * @param sta
     * @param wh
     */
    private void cancelSfOrder(StockTransApplication sta, Warehouse wh) {
        if (wh.getIsSfOlOrder() != null && wh.getIsSfOlOrder()) {
            StaDeliveryInfo d = sta.getStaDeliveryInfo();
            if (Transportator.SF.equals(d.getLpCode()) || Transportator.SFCOD.equals(d.getLpCode()) || Transportator.SFDSTH.equals(d.getLpCode())) {
                SfOrderCancelQueue q = new SfOrderCancelQueue();
                q.setCount(0L);
                q.setCreateTime(new Date());
                q.setOrderId(d.getExtTransOrderId());
                q.setCmpOu(sta.getMainWarehouse().getParentUnit().getParentUnit());
                sfOrderCancelQueueDao.save(q);
            }
        }
    }

    /**
     * VMI外包仓-过出库单时记录中间表
     * 
     * @param sta
     */
    @Override
    public void generateMsgForVmiOut(StockTransApplication sta, Warehouse wh) {
        // VMI 判断是否是vmi ,
        if (sta.getMainWarehouse() != null) {
            if (StringUtils.hasText(wh.getVmiSource())) {
                MsgOutboundOrder msg = new MsgOutboundOrder();
                Long id = warehouseMsgSkuDao.getThreePlSeq(new SingleColumnRowMapper<Long>(Long.class));
                msg.setUuid(id);
                msg.setSource(wh.getVmiSource());
                msg.setSourceWh(wh.getVmiSourceWh());
                msg.setStaCode(sta.getCode());
                StaDeliveryInfo stainfo = staDeliveryInfoDao.getByPrimaryKey(sta.getId());
                if (stainfo != null) {
                    if (StringUtils.hasText(stainfo.getLpCode())) {
                        msg.setLpCode(stainfo.getLpCode());
                    }if (StringUtils.hasText(stainfo.getTrackingNo())) {
                        msg.setTransNo(stainfo.getTrackingNo());
                    }
                    msg.setTransferFee(stainfo.getTransferFee());
                    msg.setReceiver(stainfo.getReceiver());
                    msg.setZipcode(stainfo.getZipcode());
                    msg.setStaType(sta.getType().getValue());
                    msg.setTelePhone(stainfo.getTelephone());
                    msg.setMobile(stainfo.getMobile());
                    msg.setCountry(stainfo.getCountry());
                    msg.setProvince(stainfo.getProvince());
                    msg.setCity(stainfo.getCity());
                    msg.setDistrict(stainfo.getDistrict());
                    msg.setAddress(stainfo.getAddress());
                    msg.setRemark(sta.getMemo() == null ? "" : sta.getMemo() + " " + stainfo.getRemark() == null ? "" : stainfo.getRemark());
                }
                // 外包仓 - 库间移动
                if (sta.getType().equals(StockTransApplicationType.TRANSIT_CROSS) || sta.getType().equals(StockTransApplicationType.SAME_COMPANY_TRANSFER) || sta.getType().equals(StockTransApplicationType.DIFF_COMPANY_TRANSFER)) {
                    Warehouse addWh = warehouseDao.getByOuId(sta.getAddiWarehouse().getId());
                    msg.setAddress(addWh.getAddress());
                    msg.setProvince(addWh.getProvince());
                    msg.setCity(addWh.getCity());
                    msg.setDistrict(addWh.getDistrict());
                    msg.setCountry("中国");
                    msg.setReceiver(addWh.getPic());
                    msg.setTelePhone(addWh.getPhone());
                    msg.setMobile(addWh.getPhone());
                    msg.setStaType(sta.getType().getValue());
                }
                msg.setTotalActual(sta.getTotalActual());
                msg.setStatus(DefaultStatus.CREATED);
                if (msg.getShopId() == null && sta.getOwner() != null) {
                    BiChannel shop = companyShopDao.getByCode(sta.getOwner());
                    if (shop != null) {
                        msg.setShopId(shop.getId());
                    }
                }
                msg = msgOutboundOrderDao.save(msg);
                List<StaLine> stalines = staLineDao.findByStaId(sta.getId());
                for (StaLine line : stalines) {
                    MsgOutboundOrderLine msgline = new MsgOutboundOrderLine();
                    msgline.setMsgOrder(msg);
                    msgline.setQty(line.getQuantity());
                    msgline.setSku(line.getSku());
                    msgline.setSkuName(line.getSkuName());
                    msgline.setUnitPrice(line.getUnitPrice());
                    try {
                        msgline.setInvStatus(line.getInvStatus());
                    } catch (Exception e) {}
                    msgline = msgOutboundOrderLineDao.save(msgline);
                }
            }
        }
    }

    /**
     * 生成换货出库通知消息2
     * 
     * @param sta
     * @param wh
     */
    private void generateRaOutboundMsgForVmi(StockTransApplication sta, Warehouse wh) {
        // VMI 判断是否是vmi
        StaDeliveryInfo stainfo = sta.getStaDeliveryInfo();
        MsgOutboundOrder msg = new MsgOutboundOrder();
        Long id = warehouseMsgSkuDao.getThreePlSeq(new SingleColumnRowMapper<Long>(Long.class));
        msg.setUuid(id);
        msg.setSource(wh.getVmiSource());
        msg.setSourceWh(wh.getVmiSourceWh());
        msg.setStaCode(sta.getCode());
        if (sta.getStaDeliveryInfo() != null && StringUtils.hasText(sta.getStaDeliveryInfo().getLpCode())) {
            msg.setLpCode(sta.getStaDeliveryInfo().getLpCode());
        }
        msg.setTransferFee(stainfo.getTransferFee());
        msg.setReceiver(stainfo.getReceiver());
        msg.setZipcode(stainfo.getZipcode());
        msg.setStaType(sta.getType().getValue());
        msg.setTelePhone(stainfo.getTelephone());
        msg.setMobile(stainfo.getMobile());
        msg.setCountry(stainfo.getCountry());
        msg.setProvince(stainfo.getProvince());
        msg.setCity(stainfo.getCity());
        msg.setDistrict(stainfo.getDistrict());
        msg.setAddress(stainfo.getAddress());
        msg.setTotalActual(sta.getTotalActual());
        msg.setRemark(stainfo.getRemark());
        msg.setStatus(DefaultStatus.CREATED);
        // 如果wh的isSFolorder值为true，并且VMI_SOURCE有值，则对MsgOutboundOrder进行加锁
        if (wh.getVmiSource() != null && wh.getIsSfOlOrder() != null && wh.getIsSfOlOrder() == true && stainfo.getLpCode() != null
                && (Transportator.SF.equals(stainfo.getLpCode()) || Transportator.SFCOD.equals(stainfo.getLpCode()) || Transportator.SFDSTH.equals(stainfo.getLpCode()))) {
            msg.setIsLocked(true);
        }
        if (wh.getIsEmsOlOrder() != null && wh.getIsEmsOlOrder() && (Transportator.EMS.equals(stainfo.getLpCode()) || Transportator.EMS_COD.equals(stainfo.getLpCode()))) {
            msg.setIsLocked(true);
        }
        if (Transportator.KERRY_A.equals(stainfo.getLpCode())) {
            msg.setIsLocked(true);
        }

        if ("1".equals(MSG_OUTBOUNT_CREATETIMELOG) && msg.getIsLocked() != null && msg.getIsLocked()) {
            msg.setCreateTimeLog(new Date());
            msg.setCreateTime(null);
        } else {
            msg.setCreateTimeLog(new Date());
            msg.setCreateTime(new Date());
        }
        // 是否货到付款与付款金额
        msg.setIsCodOrder(stainfo.getIsCod());
        if (stainfo.getTransferFee() != null) {
            BigDecimal cost = stainfo.getTransferFee().add(sta.getTotalActual());
            msg.setTotalActual(cost);
        }

        if (msg.getShopId() == null && sta.getOwner() != null) {
            BiChannel shop = companyShopDao.getByCode(sta.getOwner());
            if (shop != null) {
                msg.setShopId(shop.getId());
            }
        }
        MsgOutboundOrder m = msgOutboundOrderDao.save(msg);
        List<StaLine> stalines = staLineDao.findByStaId(sta.getId());
        for (StaLine line : stalines) {
            MsgOutboundOrderLine msgline = new MsgOutboundOrderLine();
            msgline.setMsgOrder(m);
            msgline.setQty(line.getQuantity());
            msgline.setSku(line.getSku());
            msgline.setSkuName(line.getSkuName());
            msgline.setUnitPrice(line.getUnitPrice());
            msgline.setTotalActual(line.getTotalActual());
            msgline = msgOutboundOrderLineDao.save(msgline);
        }
        if (wh.getIsOutInvoice() != null && wh.getIsOutInvoice() && stainfo.getStoreComIsNeedInvoice() != null && stainfo.getStoreComIsNeedInvoice()) {
            // 查票
            List<StaInvoice> ivList = staInvoiceDao.getBySta(sta.getId());
            for (StaInvoice iv : ivList) {
                MsgInvoice mic = new MsgInvoice();
                mic.setAmt1(iv.getAmt());
                mic.setCreateTime(new Date());
                mic.setDrawer(iv.getDrawer());
                mic.setInvoiceTime(iv.getInvoiceDate());
                mic.setItem1(iv.getItem());
                mic.setPayee(iv.getPayee());
                mic.setPayer(iv.getPayer());
                mic.setQty1(iv.getQty() == null ? 0L : iv.getQty().longValue());
                mic.setRemark(iv.getMemo());
                mic.setSource(wh.getVmiSource());
                mic.setSourceWh(wh.getVmiSourceWh());
                mic.setStaCode(sta.getCode());
                mic.setStatus(DefaultStatus.CREATED);
                msgInvoiceDao.save(mic);
            }
        }
    }

    // }

    /**
     * 更新作业单过仓后的基本信息
     * 
     * @param sta
     */
    private void updateStaSkuInfo(StockTransApplication sta) {
        log.info("updateStaSkuInfo start, sta is : {},{}", sta.getRefSlipCode(), sta.getSlipCode1());
        staDao.flush();
        // 更新 作业单过后所需基本信息 //获取作业单商品公共分类
        Long scId = findStaSkuCategories(sta.getId());
        if (scId != null) {
            SkuCategories sc = skuCategoriesDao.getByPrimaryKey(scId);
            sta.setSkuCategoriesId(sc);
        }
        String skus = staDao.getSkusByStaId(sta.getId(), ":", new SingleColumnRowMapper<String>(String.class));
        Long skuQty = staDao.getSkuQtyByStaId(sta.getId(), new SingleColumnRowMapper<Long>(Long.class));
        Boolean isSn = staDao.getIsSnByStaId(sta.getId(), new SingleColumnRowMapper<Boolean>(Boolean.class));
        BigDecimal skuMaxLength = staDao.getSkuMaxLengthByStaId(sta.getId(), new SingleColumnRowMapper<BigDecimal>(BigDecimal.class));
        Integer deliveryType = staDao.getIsRailWayByStaId(sta.getId(), new SingleColumnRowMapper<Integer>(Integer.class));
        if (skus != null && !skus.equals("")) {
            if (skus.length() > 1000) {
                sta.setSkus(null);
            } else {
                sta.setSkus(skus);
            }

        } else {
            sta.setSkus(skus);
        }
        /**
         * 星巴克特殊订单标记
         */
        Integer i = skuDao.findMarSkuCardType(sta.getId(), 7, new SingleColumnRowMapper<Integer>(Integer.class));
        if (i > 0) {
            sta.setOrderType2(1);
        }
        sta.setSkuQty(skuQty);
        sta.setIsSn(isSn);
        sta.setSkuMaxLength(skuMaxLength);
        if (deliveryType != null) {
            sta.setDeliveryType(TransDeliveryType.valueOf(deliveryType));
        }
        
        /**
         * QS商品
         */
        if (sta.getIsSpecialPackaging() == null || !sta.getIsSpecialPackaging()) {
            Boolean b = staLineDao.checkIsSpecailSku(sta.getId(), new SingleColumnRowMapper<Boolean>(Boolean.class));
            if (b) {
                sta.setIsSpecialPackaging(true);
            }
        }
        

        // 作业单重量
        Integer isNullCount = staDao.getSkuWeightIsNull(sta.getId(), new SingleColumnRowMapper<Integer>(Integer.class));
        if (isNullCount == 0) {
            StaDeliveryInfo di = sta.getStaDeliveryInfo();
            if (di != null) {
                BigDecimal weight = staDao.getStaAllSkuWeight(sta.getId(), new SingleColumnRowMapper<BigDecimal>(BigDecimal.class));
                di.setWeight(weight);
                staDeliveryInfoDao.save(di);
            }
        }
        // 设置作业单配货类型单件多件团购
        // 所有特殊包装订单不计算秒杀、组合套装等特殊逻辑，特殊包装订单固定属于多件订单
        if (sta.getSkuQty().equals(1L)) {
            sta.setPickingType(StockTransApplicationPickingType.SKU_SINGLE);
        } else {
            sta.setPickingType(StockTransApplicationPickingType.SKU_MANY);
        }
        if (sta.getSpecialType() != null) {
            if (sta.getSpecialType().equals(SpecialSkuType.NORMAL)) {
                sta.setPickingType(StockTransApplicationPickingType.SKU_MANY);
            }
        } else {
            Long groupConfig = skuSizeConfigDao.findConfigBySkuQty(sta.getSkuMaxLength(), new SingleColumnRowMapper<Long>(Long.class));
            if (sta.getSkuQty().compareTo(groupConfig) >= 0 && (sta.getIsSpecialPackaging() == null || !sta.getIsSpecialPackaging())) {
                sta.setPickingType(StockTransApplicationPickingType.GROUP);
                // OTO订单不允许有团购，设为多件
                if (StringUtils.hasText(sta.getToLocation())) {
                    sta.setPickingType(StockTransApplicationPickingType.SKU_MANY);
                }
                // 礼盒包装类型 不允许有团购，设置为多件
                if (sta.getPackingType() != null && sta.getPackingType().equals(PackingType.GIFT_BOX)) {
                    sta.setPickingType(StockTransApplicationPickingType.SKU_MANY);
                }
            }
        }
        // 多件根据商品数量区分 多件模式1/多件模式2
        if (sta.getPickingType().equals(StockTransApplicationPickingType.SKU_MANY)) {
            Warehouse house = warehouseDao.getByOuId(sta.getMainWarehouse().getId());
            Long tempValue = null; // 区分拣货模式的值
            if (house != null && house.getSkuQty() != null) {
                tempValue = house.getSkuQty();
            } else {
                tempValue = 3l;
            }
            // 作业单商品数量小等于该值 为多件子模式1，大于该值 为模式2
            if (sta.getSkuQty() <= tempValue) {
                sta.setPickSubType(1l);
            } else {
                sta.setPickSubType(2l);
            }
        }
        log.info("updateStaSkuInfo start,suggest trans start,  sta is : {},{}", sta.getRefSlipCode(), sta.getSlipCode1());
        // 物流推荐
        BiChannel bc = biChannelDao.getByCode(sta.getOwner());
        boolean flag = bc.getIsNotCheckInventory() == null ? false : bc.getIsNotCheckInventory();
        if (sta != null && (null == sta.getSystemKey() || flag)) {
            try {
                Warehouse w = warehouseDao.getByOuId(sta.getMainWarehouse().getId());
                if (w.getIsTransMust() != null && w.getIsTransMust() == true) {
                    transSuggestManager.suggestTransService(sta.getId());
                }
            } catch (Exception e) {
                log.error("", e);
            }
        }
        
        /**
         * QS商品
         */
    /*    if (sta.getIsSpecialPackaging() == null || !sta.getIsSpecialPackaging()) {
            Boolean b = staLineDao.checkIsSpecailSku(sta.getId(), new SingleColumnRowMapper<Boolean>(Boolean.class));
            if (b) {
                sta.setIsSpecialPackaging(true);
            }
        }*/
        
        ChooseOption choose = chooseOptionDao.findByCategoryCodeAndKey("isOpenSFYunGeDay", "key");
        if (null != choose && "1".equals(choose.getOptionValue())) {
            StaDeliveryInfo di = sta.getStaDeliveryInfo();
            if (null != di.getLpCode() && "SF".equals(di.getLpCode()) && null != di.getTransTimeType() && TransTimeType.ORDINARY.equals(di.getTransTimeType())) {
                log.info("StaDeliveryInfoChoose: {},{}", sta.getCode(), di.getTransTimeType());
                List<StaLineCommand> list = staLineDao.findStaLineAndSkuByStaId(sta.getId(), new BeanPropertyRowMapperExt<StaLineCommand>(StaLineCommand.class));
                if (null != list && list.size() > 0) {
                    for (StaLineCommand staLine : list) {
                        if (null != staLine.getDeliveryType() && TransDeliveryType.LAND.getValue() == (staLine.getDeliveryType())) {
                            sta.setDeliveryType(TransDeliveryType.LAND);
                            break;
                        }
                    }
                }
            }

        }
        staDao.save(sta);
        /**
         * ad取消检验 
         */
        if("adidas".equals(sta.getSystemKey())){
            Warehouse w2 = warehouseDao.getByOuId(sta.getMainWarehouse().getId());
            if("1".equals(w2.getIsAdidas())){
            ////////////////////////////////////查询中间表看是否是整单取消
            //整单取消
         List<AdCancel>  adList= adCancelDao.getAdCancelBySlipCode(sta.getRefSlipCode(), new BeanPropertyRowMapper<AdCancel>(AdCancel.class));
         List<AdCancel>  adList2= adCancelDao.getAdCancelBySlipCode2(sta.getSlipCode1(), new BeanPropertyRowMapper<AdCancel>(AdCancel.class));
             if(adList.size()>0 || adList2.size()>0 ){
               //  Long id=null;
                 Map<String, Long> map=new  HashMap<String, Long>();
                 for (AdCancel adCancel : adList2) {
                     if(adCancel.getLineNo() != null){
                        if(map.get(adCancel.getLineNo())==null){
                            map.put(adCancel.getLineNo(), adCancel.getId());
                        }
                     }
                }
                 if(adList.size()>0){//整单取消
                     sta.setStatus(StockTransApplicationStatus.CANCELED);
                     staDao.save(sta);
                     adCancelDao.updateAdCancelIsCancel(sta.getRefSlipCode());
                     //插入中间表
                     MsgOutboundOrderCancel msg = new MsgOutboundOrderCancel();
                     msg.setCreateTime(new Date());
                     msg.setErrorCount(0L);
                     msg.setIsCanceled(true);
                     msg.setSystemKey("adidas");
                     msg.setSource("adidas");
                     msg.setStatus(MsgOutboundOrderCancelStatus.CREATED);
                     msg.setMsg("adidas");
                     msg.setUpdateTime(new Date());
                     msg.setStaCode(sta.getCode());
                     msg.setStaId(sta.getId());
                     msg.setSilpCode1(sta.getSlipCode1());
                     msg.setWarehouseCode("SHWH205");
                     StaDeliveryInfo stainfo = staDeliveryInfoDao.getByPrimaryKey(sta.getId());
                     msg.setTransCode(stainfo.getLpCode());
                     msg.setTransNo(stainfo.getTrackingNo());
                     msgOutboundOrderCancelDao.save(msg);
                     List<StaLineCommand> lists = staLineDao.findStaLineByOrderCode(sta.getId(), new BeanPropertyRowMapperExt<StaLineCommand>(StaLineCommand.class));
                     for (StaLineCommand staLine : lists) {
                         StaCancel cancel = new StaCancel();
                         cancel.setCreateDate(new Date());
                         cancel.setQuantity(0L);
                         cancel.setOrderLineNo(staLine.getLineNo());
                         cancel.setOwner("adidas");
                         Sku sku = skuDao.getSkuByBarcode(staLine.getBarCode());
                         cancel.setSku(sku);
                         cancel.setOrderCancel(msg);
                         staCancelDao.save(cancel);
                     }
                 }  else if (adList2.size()>0){
                     for (String lineNo : map.keySet()) {//行取消 
                         sta.setNoHaveReportMissing(true);// 强制过滤报缺(AD 拣货前部分取消 不用重复复核 true)
                         staLineDao.updateStaLineQtyIsCancel(0L, lineNo, sta.getId());
                         adCancelDao.updateAdCancelIsCancel2(sta.getSlipCode1(), lineNo);
                     } 
                     List<StaLineCommand> staLineList=staLineDao.findByStaIdByBatis(sta.getId(),new BeanPropertyRowMapperExt<StaLineCommand>(StaLineCommand.class));
                     Boolean a=true;
                     for (StaLineCommand staLine : staLineList) {
                         if(staLine.getQuantity() ==0){
                             sta.setSkuQty(sta.getSkuQty() - staLine.getOrderQty());
                             sta.setTotalActual(sta.getTotalActual().subtract(staLine.getTotalActual()));
                             sta.setOrderTotalActual(sta.getTotalActual());
                         }else{
                             a=false;
                         }
                    }
                     if(a){
                         sta.setStatus(StockTransApplicationStatus.CANCELED);
                         //插入中间表
                         MsgOutboundOrderCancel msg = new MsgOutboundOrderCancel();
                         msg.setCreateTime(new Date());
                         msg.setErrorCount(0L);
                         msg.setIsCanceled(true);
                         msg.setSystemKey("adidas");
                         msg.setSource("adidas");
                         msg.setMsg("adidas");
                         msg.setStatus(MsgOutboundOrderCancelStatus.CREATED);
                         msg.setUpdateTime(new Date());
                         msg.setStaCode(sta.getCode());
                         msg.setStaId(sta.getId());
                         msg.setSilpCode1(sta.getSlipCode1());
                         msg.setWarehouseCode("SHWH205");
                         StaDeliveryInfo stainfo = staDeliveryInfoDao.getByPrimaryKey(sta.getId());
                         msg.setTransCode(stainfo.getLpCode());
                         msg.setTransNo(stainfo.getTrackingNo());
                         msgOutboundOrderCancelDao.save(msg);
                         List<StaLineCommand> lists = staLineDao.findStaLineByOrderCode(sta.getId(), new BeanPropertyRowMapperExt<StaLineCommand>(StaLineCommand.class));
                         for (StaLineCommand staLine : lists) {
                             StaCancel cancel = new StaCancel();
                             cancel.setCreateDate(new Date());
                             cancel.setQuantity(0L);
                             cancel.setOrderLineNo(staLine.getLineNo());
                             cancel.setOwner("adidas");
                             Sku sku = skuDao.getSkuByBarcode(staLine.getBarCode());
                             cancel.setSku(sku);
                             cancel.setOrderCancel(msg);
                             staCancelDao.save(cancel);
                         }
                     }
                     staDao.save(sta);
                 }
                 
             }
           }
            //////////////////////////////
        }
        log.info("updateStaSkuInfo end,sta is : {},{}", sta.getRefSlipCode(), sta.getSlipCode1());
    }

    /**
     * 获取作业单商品的公共分类
     * 
     * @param staId
     * @return 公共分类节点ID
     */
    private Long findStaSkuCategories(Long staId) {
        // 返回结果
        Long result = null;
        // 查出所有作业单商品的所属分类信息（包括所属分类所有父节点）
        List<SkuCategoriesCommand> list = skuCategoriesDao.findSkuCategoryBySta(staId, new BeanPropertyRowMapperExt<SkuCategoriesCommand>(SkuCategoriesCommand.class));
        // 保存 每个商品里面的分类信息（包括所属分类所有父节点）
        List<List<SkuCategoriesCommand>> skuList = new ArrayList<List<SkuCategoriesCommand>>();
        // 是否是头信息
        boolean isHand = true;
        // 零时保存单个商品的所属分类已经所有父节点信息
        List<SkuCategoriesCommand> temp = null;
        // 将 商品的所属分类以及分类的所有父节点 按照每个商品 所分开来
        for (SkuCategoriesCommand com : list) {
            if (isHand) {
                temp = new ArrayList<SkuCategoriesCommand>();
                skuList.add(temp);
                isHand = false;
            }
            temp.add(com);
            if (com.getParentId() == null || com.getParentId() < 0) {
                isHand = true;
            }
        }
        // 如果 =1 证明作业单只存在一种商品 如果是一种商品 取商品分类 就取商品所属分类
        if (skuList.size() == 1) {
            result = skuList.get(0).get(0).getId();
        } else {
            // 从2开始 是因为 排除掉商品总类的遍历
            int index = 2;
            // 零时公共分类 默认赋值 商品总类
            Long tempSkuC = skuList.get(0).get(skuList.get(0).size() - 1).getId();
            do {
                long skucId = -1;
                for (List<SkuCategoriesCommand> tempList : skuList) {
                    // 判断是否前一次的遍历就是 当前SKU的所属分类节点
                    if (tempList.size() < index) {
                        result = tempSkuC;
                        break;
                    }
                    // 获取当前商品的当前分类节点信息
                    SkuCategoriesCommand bean = tempList.get(tempList.size() - index);
                    if (skucId == -1) {// 判断是否是遍历的第一个商品的分类信息
                        skucId = bean.getId();
                    } else if (skucId != bean.getId()) {// 当铺商品 同一级别的商品所属分类
                        // 与别的商品分类信息不同
                        // 所以取当前所属分类级别的上一级做公共
                        // 取上一级的商品分类节点
                        result = tempSkuC;
                        break;
                    }
                }
                // 在没有确定出公共分类时 确保下一轮遍历
                if (result == null) {
                    // ++是为了下一轮遍历下一级的分类节点信息
                    index++;
                    // 保存此次遍历中所遍历过的 分类节点信息
                    tempSkuC = skucId;
                }
            } while (result == null);
        }
        return result;
    }

    /**
     * 销售过单成功后，完善订单信息
     * 
     * @param sta
     */
    private void checkStaPickingType(StockTransApplication sta) {
        staDao.save(sta);
        staDao.flush();
        if ((sta.getIsSpecialPackaging() != null && sta.getIsSpecialPackaging()) || sta.getSpecialType() != null) {
            // 所有特殊包装订单不计算秒杀、组合套装等特殊逻辑，特殊包装订单固定属于多件订单
            // sta.setPickingType(StockTransApplicationPickingType.SKU_MANY);
        } else if (!sta.getPickingType().equals(StockTransApplicationPickingType.GROUP)) {
            Warehouse wh = warehouseDao.getByOuId(sta.getMainWarehouse().getId());
            if (!StringUtils.hasText(wh.getVmiSource())) {
                if (wh.getIsSupportSecKill() != null && wh.getIsSupportPackageSku() != null && wh.getIsSupportPackageSku() && wh.getIsSupportSecKill()) {
                    Integer snCount = staLineDao.findSkuSN(sta.getId(), new SingleColumnRowMapper<Integer>(Integer.class));
                    if (snCount == 0) {
                        // 查询秒杀订单商品是否有该订单商品
                        SecKillSku bian = secKillSkuDao.getSecKillSkuBySta(sta.getId(), new BeanPropertyRowMapperExt<SecKillSku>(SecKillSku.class));
                        if (bian == null) {
                            // 如果订单非秒杀 要判断是否为套装组合商品
                            // 如果是套装组合商品则标记为套装组合商品
                            // 否则同时记录套装日志和秒杀日志
                            Long psId = packageSkuDao.findPackageSkuListBySkus1System(sta.getMainWarehouse().getId(), sta.getSkus(), Long.parseLong(sta.getSkus().split(":")[0]), sta.getSkuQty(), new SingleColumnRowMapper<Long>(Long.class));
                            if (psId != null) {
                                sta.setPackageSku(psId);
                                sta.setPickingType(StockTransApplicationPickingType.SKU_PACKAGE);
                                staDao.save(sta);
                                staDao.flush();
                            } else {
                                if (sta.getSkuQty().equals(2L) && sta.getSkus().split(":")[0].equals("2")) {
                                    Long sku1Id = Long.parseLong(sta.getSkus().split(":")[1].split(",")[0].split(";")[0]);
                                    PackageSkuCounter psc = packageSkuCounterDao.getPackageSkuCounter(sku1Id, 2L, 2L, sta.getMainWarehouse().getId(), new BeanPropertyRowMapper<PackageSkuCounter>(PackageSkuCounter.class));
                                    if (psc == null) {
                                        packageSkuCounterDao.addNewCounter(sta.getMainWarehouse().getId(), sku1Id);
                                    } else {
                                        packageSkuCounterDao.addCounterById(psc.getId());
                                    }
                                    Long sku2Id = Long.parseLong(sta.getSkus().split(":")[1].split(",")[1].split(";")[0]);
                                    PackageSkuCounter psc1 = packageSkuCounterDao.getPackageSkuCounter(sku2Id, 2L, 2L, sta.getMainWarehouse().getId(), new BeanPropertyRowMapper<PackageSkuCounter>(PackageSkuCounter.class));
                                    if (psc1 == null) {
                                        packageSkuCounterDao.addNewCounter(sta.getMainWarehouse().getId(), sku2Id);
                                    } else {
                                        packageSkuCounterDao.addCounterById(psc1.getId());
                                    }
                                }
                                // staDao.updateStaSedKill(staId, false);
                                // 订单商品不存在秒杀订单商品里面 须要在秒杀订单商品计数器表里面记录
                                secKillSkuCounterDao.addSecKillSkuCounterBySta(sta.getId(), "1:%");
                            }
                        } else {
                            // 订单商品包括在秒杀订单商品里面 将订单设置为秒杀订单
                            staDao.updateStaSedKill(sta.getId(), true);
                        }
                    }
                } else if (wh.getIsSupportSecKill() != null && wh.getIsSupportSecKill() && (wh.getIsSupportPackageSku() == null || (wh.getIsSupportPackageSku() != null && !wh.getIsSupportPackageSku()))) {
                    // 只支持秒杀，不支持套装组合
                    Integer snCount = staLineDao.findSkuSN(sta.getId(), new SingleColumnRowMapper<Integer>(Integer.class));
                    if (snCount == 0) {
                        SecKillSku bian = secKillSkuDao.getSecKillSkuBySta(sta.getId(), new BeanPropertyRowMapperExt<SecKillSku>(SecKillSku.class));
                        if (bian == null) {
                            secKillSkuCounterDao.addSecKillSkuCounterBySta(sta.getId(), "1:%");
                        } else {
                            staDao.updateStaSedKill(sta.getId(), true);
                        }
                    }
                } else if (wh.getIsSupportPackageSku() != null && wh.getIsSupportPackageSku() && (wh.getIsSupportSecKill() == null || (wh.getIsSupportSecKill() != null && !wh.getIsSupportSecKill()))) {
                    // 只支持套装组合，不支持秒杀
                    Integer snCount = staLineDao.findSkuSN(sta.getId(), new SingleColumnRowMapper<Integer>(Integer.class));
                    if (snCount == 0) {
                        Long psId = packageSkuDao.findPackageSkuListBySkus1System(sta.getMainWarehouse().getId(), sta.getSkus(), Long.parseLong(sta.getSkus().split(":")[0]), sta.getSkuQty(), new SingleColumnRowMapper<Long>(Long.class));
                        if (psId != null) {
                            sta.setPackageSku(psId);
                            sta.setPickingType(StockTransApplicationPickingType.SKU_PACKAGE);
                            staDao.save(sta);
                            staDao.flush();
                        } else {
                            if (sta.getSkuQty().equals(2L) && sta.getSkus().split(":")[0].equals("2")) {
                                Long sku1Id = Long.parseLong(sta.getSkus().split(":")[1].split(",")[0].split(";")[0]);
                                PackageSkuCounter psc = packageSkuCounterDao.getPackageSkuCounter(sku1Id, 2L, 2L, sta.getMainWarehouse().getId(), new BeanPropertyRowMapper<PackageSkuCounter>(PackageSkuCounter.class));
                                if (psc == null) {
                                    packageSkuCounterDao.addNewCounter(sta.getMainWarehouse().getId(), sku1Id);
                                } else {
                                    packageSkuCounterDao.addCounterById(psc.getId());
                                }
                                Long sku2Id = Long.parseLong(sta.getSkus().split(":")[1].split(",")[1].split(";")[0]);
                                PackageSkuCounter psc1 = packageSkuCounterDao.getPackageSkuCounter(sku2Id, 2L, 2L, sta.getMainWarehouse().getId(), new BeanPropertyRowMapper<PackageSkuCounter>(PackageSkuCounter.class));
                                if (psc1 == null) {
                                    packageSkuCounterDao.addNewCounter(sta.getMainWarehouse().getId(), sku2Id);
                                } else {
                                    packageSkuCounterDao.addCounterById(psc1.getId());
                                }
                            }
                        }
                    }
                }
            }
        }
        if (sta.getPickingType().equals(StockTransApplicationPickingType.SKU_SINGLE)) {
            Long pid = staDao.getPackageSkuIdBySta(sta.getId(), new SingleColumnRowMapper<Long>(Long.class));
            if (pid != null) {
                sta.setPackageSku(pid);
                staDao.save(sta);
                staDao.flush();
            }
        }
        sfNextMorningConfig(sta);
    }

    /**
     * 1、作业单店铺配置支持SF次晨达业务<br/>
     * 2、物流商是SF时效是次日达<br/>
     * 3、地址在次晨达配送区域内
     * 
     * @param sta
     */
    private void sfNextMorningConfig(StockTransApplication sta) {
        BiChannel c = biChannelDao.getByCode(sta.getOwner());
        // 1、作业单店铺配置支持SF次晨达业务
        if (null != c.getIsSupportNextMorning() && c.getIsSupportNextMorning()) {
            StaDeliveryInfo di = sta.getStaDeliveryInfo();
            // 2、物流商是SF时效是次日达
            if (null != di.getLpCode() && null != di.getTransTimeType() && di.getTransTimeType().equals(TransTimeType.THE_NEXT_DAY) && (di.getLpCode().equals(Transportator.SF))) {
                List<SfNextMorningConfig> list = sfNextMorningConfigDao.findSfNextMorningConfigListByOuId(sta.getMainWarehouse().getId(), new BeanPropertyRowMapper<SfNextMorningConfig>(SfNextMorningConfig.class));
                if (null != list && list.size() > 0) {
                    // 3、地址在次晨达配送区域内
                    boolean b = false;
                    for (SfNextMorningConfig sf : list) {
                        if (di.getProvince().contains(sf.getProvince()) && di.getCity().contains(sf.getCity())) {
                            b = true;
                            break;
                        }
                    }
                    if (b) {
                        di.setTransTimeType(TransTimeType.THE_NEXT_MORNING);
                    }
                }
            }
        }
    }

    @Override
    public void staPartlyReturned(StockTransApplication sta, Warehouse wh) {

    }

    /**
     * 销售出库、退换货出库 类型 oms出库接口 oms
     */
    // @MsgMethodTopicAnnotation(topic = "wms3_sale_outbount", isSave = true)
    @Override
    public void statusOmsOutBound(Long id) {
        WmsOrderStatusOms oms = wmsOrderStatusOmsDao.getByPrimaryKey(id);
        StockTransApplication sta = staDao.getByCode(oms.getShippingCode());
        List<WmsInvLog> infoLogs = new ArrayList<WmsInvLog>();
        List<WmsOrderStatus> statusList = new ArrayList<WmsOrderStatus>();
        WmsOrderStatus status = new WmsOrderStatus();
        status.setOrderCode(oms.getOrderCode());
        status.setSystemKey(oms.getSystemKey());
        status.setRefWarehouseCode(sta.getMainWarehouse().getCode());
        status.setShippingCode(oms.getShippingCode());
        status.setStatus(oms.getStatus());
        status.setOwner(oms.getOwner());
        status.setType(oms.getType());
        status.setOutboundTime(sta.getOutboundTime());// 出库时间
        status.setOtherOutboundTime(oms.getCreatetime());// 报表出库时间
        status.setInboundTime(sta.getInboundTime());
        List<WmsInfoLogOms> infoLogOms = infoLogOmsDao.queryInfoLog(oms.getId(), new BeanPropertyRowMapperExt<WmsInfoLogOms>(WmsInfoLogOms.class));
        for (WmsInfoLogOms wmsInfoLogOms : infoLogOms) {
            WmsInvLog infoLog = new WmsInvLog();
            infoLog.setBarchNo(wmsInfoLogOms.getBarchNo());
            infoLog.setBtachCode(wmsInfoLogOms.getBtachCode());
            infoLog.setInvStatus(wmsInfoLogOms.getInvStatus());
            infoLog.setOwner(wmsInfoLogOms.getOwner());
            infoLog.setTranTime(wmsInfoLogOms.getTranTime());
            infoLog.setSku(wmsInfoLogOms.getSku());
            infoLog.setQty(wmsInfoLogOms.getQty());
            infoLog.setLineNo(wmsInfoLogOms.getLineNo());
            infoLog.setMarketability(wmsInfoLogOms.getIsSales()); // 新增是否可售By FXL
            if (StringUtils.hasText(wmsInfoLogOms.getSns())) {
                String sn[] = wmsInfoLogOms.getSns().split(",");
                List<String> sns = new ArrayList<String>();
                for (int i = 0; i < sn.length; i++) {
                    sns.add(sn[i]);
                }
                infoLog.setSns(sns);
            }
            infoLog.setWarehouceCode(wmsInfoLogOms.getWarehouceCode());
            infoLogs.add(infoLog);
        }
        status.setInvLogs(infoLogs);
        List<WmsTransInfo> transInfos = new ArrayList<WmsTransInfo>();
        if (sta.getType().getValue() == 41 || sta.getType().getValue() == 42) {
            StaDeliveryInfo d = staDeliveryInfoDao.getByPrimaryKey(sta.getId());
            WmsTransInfo info = new WmsTransInfo();
            info.setTransCode(d.getLpCode());
            info.setTransNo(d.getTrackingNo());
            transInfos.add(info);
        } else {
            List<WmsTransInfoOms> transinfoLogOms = infoOmsDao.findOrderId(oms.getId(), new BeanPropertyRowMapperExt<WmsTransInfoOms>(WmsTransInfoOms.class));
            for (WmsTransInfoOms wmsTransInfoOms : transinfoLogOms) {
                WmsTransInfo info = new WmsTransInfo();
                info.setTransCode(wmsTransInfoOms.getTransCode());
                info.setTransNo(wmsTransInfoOms.getTransNo());
                info.setTransTimeType(wmsTransInfoOms.getTransTimeType());
                transInfos.add(info);
            }

        }
        status.setTransInfos(transInfos);
        List<WmsOrderInvoice> invoices = new ArrayList<WmsOrderInvoice>();
        List<WmsOrderInvoiceOms> invoiceOms = invoiceOmsDao.findOrderId(oms.getId(), new BeanPropertyRowMapperExt<WmsOrderInvoiceOms>(WmsOrderInvoiceOms.class));
        for (WmsOrderInvoiceOms wmsOrderInvoiceOms : invoiceOms) {
            WmsOrderInvoice invoice = new WmsOrderInvoice();
            invoice.setAmt(wmsOrderInvoiceOms.getAmt());
            invoice.setCompany(wmsOrderInvoiceOms.getCompany());
            invoice.setDrawer(wmsOrderInvoiceOms.getDrawer());
            invoice.setInvoiceCode(wmsOrderInvoiceOms.getInvoiceCode());
            invoice.setInvoiceDate(wmsOrderInvoiceOms.getInvoiceDate());
            invoice.setInvoiceNo(wmsOrderInvoiceOms.getInvoiceNo());
            invoice.setInvoiceCode(wmsOrderInvoiceOms.getInvoiceCode());
            invoice.setItem(wmsOrderInvoiceOms.getItem());
            invoice.setMemo(wmsOrderInvoiceOms.getMemo());
            invoice.setPayee(wmsOrderInvoiceOms.getPayee());
            invoice.setPayer(wmsOrderInvoiceOms.getPayer());
            invoice.setQty(wmsOrderInvoiceOms.getQty().longValue());
            invoice.setUnitPrice(wmsOrderInvoiceOms.getUnitPrice());
            invoice.setWmsInvoiceCode(wmsOrderInvoiceOms.getWmsInvoiceCode());
            invoices.add(invoice);
            List<WmsOrderInvoiceLineOms> invoiceLineOms = invoiceLineOmsDao.findInvoiceId(wmsOrderInvoiceOms.getId(), new BeanPropertyRowMapperExt<WmsOrderInvoiceLineOms>(WmsOrderInvoiceLineOms.class));
            List<WmsOrderInvoiceLine> detials = new ArrayList<WmsOrderInvoiceLine>();
            for (WmsOrderInvoiceLineOms wmsOrderInvoiceLineOms : invoiceLineOms) {
                WmsOrderInvoiceLine invoiceLine = new WmsOrderInvoiceLine();
                invoiceLine.setAmt(wmsOrderInvoiceLineOms.getAmt());
                invoiceLine.setItem(wmsOrderInvoiceLineOms.getItem());
                invoiceLine.setLineNo(wmsOrderInvoiceLineOms.getLineNo());
                invoiceLine.setQty(wmsOrderInvoiceLineOms.getQty().longValue());
                invoiceLine.setUnitPrice(wmsOrderInvoiceLineOms.getUnitPrice());
                detials.add(invoiceLine);
            }
            invoice.setDetials(detials);

        }
        status.setInvoices(invoices);
        // 1、斯凯奇等走适配器-->hub4.0 销售出库、退换货出库反馈
        //List<WmsOrderStatus> cListHub4 = new ArrayList<WmsOrderStatus>();
        Boolean flag = false;
        // List<CommonConfig> cfg =
        // commonConfigDao.findCommonConfigListByParaGroup(Constants.WMS_ADAPTER_OWNER_CONFIG);
        List<String> chooseOptionList = chooseOptionManager.findByCategoryCode(Constants.WMS_ADAPTER_SYSTEM_KEY_CONFIG);
        if (chooseOptionList != null && chooseOptionList.size() > 0) {
            if (!chooseOptionList.contains(oms.getSystemKey())) {
                flag = true;
                // 添加重量信息
                List<WmsTransInfo> listTransInfos = status.getTransInfos();
                if (listTransInfos != null && !listTransInfos.isEmpty()) {
                    for (WmsTransInfo wmsTransInfo : listTransInfos) {
                        if (wmsTransInfo.getTransNo() != null) {
                            PackageInfo p = packageInfoDao.findByTrackingNo(wmsTransInfo.getTransNo());
                            if (p != null && p.getWeight() != null) {
                                wmsTransInfo.setWeight(p.getWeight());
                            }
                        }
                    }
                }
               // cListHub4.add(status);
            }
        }
        if (flag) {
            try {
                log.info("rmi Call hub4.0 statusOmsOutBound response begin!");
                Response response = null;
                if (status.getType() == 21 || status.getType() == 42) {
                    status.setRefWarehouseCode(sta.getMainWarehouse().getCode());
                    response = wms3AdapterInteractManager.salesOrderShippingConfirm(status);
                } else {
                    response = wms3AdapterInteractManager.returnOrderShippingConfirm(status);
                }
                log.info("rmi Call hub4.0 statusOmsOutBound response end!");
                if (response != null) {
                    log.info("rmi Call hub4.0 statusOmsOutBound response!response=" + JsonUtil.writeValue(response));
                }
                MongoDBMessage mdbm = new MongoDBMessage();
                mdbm.setStaCode(null);
                mdbm.setOtherUniqueKey(status.getOrderCode());
                mdbm.setMsgBody(JsonUtil.writeValue(status));
                mdbm.setSendTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                if (response != null) {
                    mdbm.setMemo("推送wms3.0->hub4.0接口适配器(销售出库、退换货出库反馈)->response:" + JsonUtil.writeValue(response));
                } else {
                    mdbm.setMemo("推送wms3.0->hub4.0接口适配器(销售出库、退换货出库反馈)->response is null!");
                }
                mongoOperation.save(mdbm);
                if (response != null && response.getOrderResponse() != null && !response.getOrderResponse().isEmpty() && response.getOrderResponse().get(0) != null && response.getOrderResponse().get(0).getStatus() == 1) {
                    oms.setMqStatus("10");// 发送OK
                } else {
                    // 接收异常
                    if (oms.getErrorCount() == null) {
                        oms.setErrorCount(1);
                    } else {
                        oms.setErrorCount(oms.getErrorCount() + 1);
                    }
                }
            } catch (Exception e) {
                log.error("rmi Call hub4.0 statusOmsOutBound response error :" + status.getOrderCode(), e);
                if (oms.getErrorCount() == null) {
                    oms.setErrorCount(1);
                } else {
                    oms.setErrorCount(oms.getErrorCount() + 1);
                }
            }
            wmsOrderStatusOmsDao.save(oms);
            return;
        }
        // 2、其他直连订单走MQ
        statusList.add(status);
        String reqJson = JsonUtil.writeValue(statusList);
        try {
            MessageCommond mes = new MessageCommond();
            mes.setMsgId(oms.getId().toString() + "," + System.currentTimeMillis() + UUIDUtil.getUUID());
            mes.setIsMsgBodySend(false);
            mes.setMsgType("com.jumbo.wms.manager.listener.StaListenerManagerImpl.statusOmsOutBound");
            mes.setMsgBody(reqJson);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date();
            mes.setSendTime(sdf.format(date));
            // mes.setFeedBackTime(feedBackTime);
            // producerServer.sendDataMsgConcurrently(MqStaticEntity.WMS3_SALE_OUTBOUNT,
            // MqStaticEntity.WMS3_OMS, mes);
            producerServer.sendDataMsgConcurrently(MQ_WMS3_SALE_OUTBOUNT, oms.getOwner(), mes);

            // 保存进mongodb
            MongoDBMessage mdbm = new MongoDBMessage();
            BeanUtils.copyProperties(mes, mdbm);
            mdbm.setStaCode(oms.getShippingCode());
            mdbm.setOtherUniqueKey(oms.getOrderCode());
            mdbm.setMsgBody(reqJson);
            mdbm.setMemo(MQ_WMS3_SALE_OUTBOUNT);
            if(oms.getOwner() !=null && oms.getOwner().contains("IT后端测试")){
                MongoDBMessageTest mdbmTest = new MongoDBMessageTest();
                BeanUtils.copyProperties(mdbm, mdbmTest);
                mongoOperation.save(mdbmTest);
            }else{
                mongoOperation.save(mdbm);
            }
            log.debug("rmi Call oms outbound response interface by MQ end:" + oms.getId());
            oms.setMqStatus("10");// 发送OK
        } catch (Exception e) {
            log.error("rmi Call oms outbound response error :" + oms.getId());
            if (oms.getErrorCount() == null) {
                oms.setErrorCount(1);
            } else {
                oms.setErrorCount(oms.getErrorCount() + 1);
            }
        }
        wmsOrderStatusOmsDao.save(oms);
    }

    /**
     * 销售出库、退换货出库 类型 oms出库接口
     */
    @Override
    public void transferOmsOutBound(Long staId, Long id) {
        StockTransApplication sta1 = staDao.getByPrimaryKey(staId);
        // 取本次反馈日志最大的执行时间
        if (sta1 != null) {
            // Date maxTransactionTime = staDao.getMaxTransactionTime(sta1.getId(), new
            // SingleColumnRowMapper<Date>(Date.class));
            boolean check = false;
            BiChannel shop = companyShopDao.getByCode(sta1.getOwner());
            VmiDefaultInterface vv = null;
            if (!StringUtil.isEmpty(shop.getDefaultCode())) {
                vv = vmiDefaultFactory.getVmiDefaultInterface(shop.getDefaultCode());
                check = true;
            }
            OperationBill ob = new OperationBill();
            ob.setCreateTime(sta1.getOutboundTime() == null ? new Date() : sta1.getOutboundTime());// BI
            ob.setCode(sta1.getCode());
            ob.setSlipCode(sta1.getRefSlipCode());
            ob.setType(sta1.getType().getValue());
            // ob.setMaxTransactionTime(maxTransactionTime);
            ob.setMaxTransactionTime(sta1.getOutboundTime());
            ob.setMemo(sta1.getMemo());
            ob.setWhCode(sta1.getMainWarehouse().getCode());
            ob.setAddWhCode(sta1.getAddiWarehouse() == null ? null : sta1.getAddiWarehouse().getCode());
            switch (sta1.getType()) {
                case OUTBOUND_SALES:
                case OUTBOUND_RETURN_REQUEST:
                    ob.setDirection(OperationBill.ONLY_OUTBOUND);
                    List<SkuSnLogCommand> skus = skuSnLogDao.findOutboundSnBySta(sta1.getId(), new BeanPropertyRowMapper<SkuSnLogCommand>(SkuSnLogCommand.class));
                    List<SnFeedbackInfo> snFeedbackInfos = new ArrayList<SnFeedbackInfo>();
                    for (SkuSnLogCommand sku : skus) {
                        SnFeedbackInfo feedbackInfo = new SnFeedbackInfo();
                        feedbackInfo.setSnNo(sku.getSn());
                        feedbackInfo.setJmskuCode(sku.getSkuCode());
                        List<SkuChildSnLog> childSnNos = childSnLogDao.getbyStaIdSn(sku.getSn(), sta1.getId());
                        List<String> snNos = new ArrayList<String>();
                        for (SkuChildSnLog skuChildSnLog : childSnNos) {
                            snNos.add(skuChildSnLog.getSeedSn());
                        }
                        feedbackInfo.setChildSnNo(snNos);
                        snFeedbackInfos.add(feedbackInfo);
                    }
                    ob.setOutboundSnInfo(snFeedbackInfos);
                    List<StvLineCommand> list = stvLineDao.getFinishedStvLineByStaId(sta1.getId(), new BeanPropertyRowMapper<StvLineCommand>(StvLineCommand.class));
                    List<OperationBillLine> list1 = new ArrayList<OperationBillLine>();
                    for (StvLineCommand sc : list) {
                        OperationBillLine line = new OperationBillLine();
                        line.setSkuCode(sc.getSkuCode());
                        // 新增是否可销售
                        line.setMarketability(sc.getMarketAbility() == 1 ? true : false);
                        line.setInvBatchCode(sc.getBatchCode());
                        line.setInvStatusCode(sc.getIntInvstatusName());
                        line.setQty(sc.getQuantity());
                        line.setShopCode(sc.getOwner());
                        line.setWhCode(sc.getOuCode());
                        if (check) {
                            vv.transferOmsOutBound(line, sc);
                        }
                        list1.add(line);
                    }
                    ob.setOutboundLines(list1);
                    // VMI出库通知HUB 品牌定制
                    if (check) {
                        ExtParam ext = new ExtParam();
                        ext.setSta(sta1);
                        ext.setBiChannel(shop);
                        ext.setStvLineList(list);
                        vv.generateVmiOutBound(ext);
                    }
                    List<OperationBillTransInfo> list2 = new ArrayList<OperationBillTransInfo>();
                    List<PackageInfoCommand> pcList = null;
                    StockTransApplication groupSta = sta1.getGroupSta();
                    if (null != groupSta && true == groupSta.getIsMerge()) {// 合并单
                        pcList = packageInfoDao.getPackageInfoByStaId(sta1.getId(), new BeanPropertyRowMapper<PackageInfoCommand>(PackageInfoCommand.class));
                        if (pcList == null || pcList.size() == 0) {
                            pcList = packageInfoDao.getPackageInfoByStaId(groupSta.getId(), new BeanPropertyRowMapper<PackageInfoCommand>(PackageInfoCommand.class));
                        }
                    } else {// 非合并单
                        pcList = packageInfoDao.getPackageInfoByStaId(sta1.getId(), new BeanPropertyRowMapper<PackageInfoCommand>(PackageInfoCommand.class));
                    }

                    for (PackageInfoCommand pc : pcList) {
                        OperationBillTransInfo obti = new OperationBillTransInfo();
                        obti.setGoodsWeight(pc.getWeight());
                        obti.setTrackingNo(pc.getTrackingNo());
                        StaDeliveryInfo info = staDeliveryInfoDao.getByPrimaryKey(sta1.getId());
                        if (info.getTransTimeType() != null) {
                            obti.setActualTransTimeType(info.getTransTimeType().getValue());
                        }
                        if (pc != null && pc.getLpCode() != null && pc.getLpCode().contains("-自提")) {
                            obti.setTransCode(pc.getLpCode().substring(0, pc.getLpCode().length() - 3));
                        } else {
                            obti.setTransCode(pc.getLpCode());
                        }
                        if (pc != null && pc.getLpCode() != null && pc.getLpCode().contains("-送货上门")) {
                            obti.setTransCode(pc.getLpCode().substring(0, pc.getLpCode().length() - 5));
                        } else {
                            obti.setTransCode(pc.getLpCode());
                        }
                        list2.add(obti);
                    }
                    ob.setBillTransInfoList(list2);

                    List<WarrantyCardResp> warrantyCardList = new ArrayList<WarrantyCardResp>();
                    List<GiftLineCommand> glList = giftLineDao.findGiftByStaAndType(sta1.getId(), GiftType.COACH_CARD.getValue(), new BeanPropertyRowMapperExt<GiftLineCommand>(GiftLineCommand.class));
                    SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    for (GiftLineCommand gl : glList) {
                        WarrantyCardResp resp = new WarrantyCardResp();
                        resp.setCardNo(gl.getSanCardCode());
                        resp.setDirection(2);
                        Date date;
                        try {
                            date = formatDate.parse(gl.getMemo());
                        } catch (Exception e) {
                            log.error("String to Date format error! StringDate:" + gl.getMemo());
                            throw new BusinessException(ErrorCode.STRING_TO_DATE_FORMAT_ERROR);
                        }
                        resp.setExpirationDate(date);
                        resp.setJmskuCode(gl.getSkuCode());
                        warrantyCardList.add(resp);
                    }
                    ob.setWarrantyCardList(warrantyCardList);

                    break;
                default:
                    break;
            }
            try {
                if (log.isInfoEnabled()) {
                    log.info("rmi Call pac outbound response interface------------------->BEGIN, staCode:{}, staSlipCode:{}, staType:{}", new Object[] {sta1.getCode(), sta1.getRefSlipCode(), sta1.getType()});
                }
                WmsIntransitNoticeOms obj = wmsIntransitNoticeOmsDao.getByPrimaryKey(id);
                // 添加切换RocketMQ开关
                final String topic = MqStaticEntity.WMS3_SALE_OUTBOUNT_FEEDBACK;
                // ChooseOption op =
                // chooseOptionDao.findByCategoryCodeAndKey(Constants.ROCKET_MQ_OPEN_GROUP,
                // Constants.OUTBOUND_OPEN_INTERFACE);
                // 查询Message消息配置表
                List<MessageConfig> configs = messageConfigDao.findMessageConfigByParameter(null, topic, null, new BeanPropertyRowMapper<MessageConfig>(MessageConfig.class));
                if (configs != null && !configs.isEmpty() && configs.get(0).getIsOpen() == 1) {// 开
                    // 发送MQ
                    MessageCommond mc = new MessageCommond();
                    // mc.setMsgId(configs.get(0).getMsgId());
                    mc.setMsgId(ob.getCode().toString() + "," + System.currentTimeMillis() + UUIDUtil.getUUID());
                    mc.setMsgType(configs.get(0).getMsgType());
                    mc.setSendTime(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
                    List<OperationBill> lists = new ArrayList<OperationBill>();
                    lists.add(ob);
                    String msgBody = JsonUtil.writeValue(lists);
                    mc.setMsgBody(msgBody);
                    mc.setIsMsgBodySend(false);
                    log.debug("rmi Call pac outbound response interface by MQ begin:" + ob.getCode());
                    try {
                        producerServer.sendDataMsgConcurrently(MQ_WMS3_SALE_OUTBOUNT_FEEDBACK, sta1.getOwner(), mc);
                    } catch (Exception e) {
                        log.error("rmi Call pac outbound response interface by MQ error:" + ob.getCode(), e);
                        // throw new BusinessException(ErrorCode.MESSAGE_SEND_ERROR, ob.getCode());
                        /** --调用失败 --更新中间表错误次数- **/
                        int errorCount = obj.getErrorCount().intValue() + 1;
                        Long newCount = Long.parseLong(String.valueOf(errorCount));
                        wmsIntransitNoticeOmsDao.updateNoticeById(obj.getId(), newCount, "发送MQ消息失败！");
                    }
                    // 保存进mongodb
                    MongoDBMessage mdbm = new MongoDBMessage();
                    BeanUtils.copyProperties(mc, mdbm);
                    mdbm.setStaCode(ob.getCode());
                    mdbm.setOtherUniqueKey(ob.getSlipCode());
                    mdbm.setMsgBody(msgBody);
                    mdbm.setMemo(MQ_WMS3_SALE_OUTBOUNT_FEEDBACK);
                    // MQ优化 不再插入到日志表 为了补偿机制
                    if(sta1.getOwner() !=null && sta1.getOwner().contains("IT后端测试")){
                        MongoDBMessageTest mdbmTest = new MongoDBMessageTest();
                        BeanUtils.copyProperties(mdbm, mdbmTest);
                        mongoOperation.save(mdbmTest);
                    }else{
                        mongoOperation.save(mdbm);
                    }
                    obj.setStatus(10);
                    wmsIntransitNoticeOmsDao.save(obj);
                    /** --调用成功 --删除中间表信息，记录成功日志-- **/
                    // wmsIntransitNoticeOmsDao.delete(obj);
                    // WmsIntransitNoticeOmsLog log = new WmsIntransitNoticeOmsLog();
                    // log.setStaCode(obj.getStaCode());
                    // log.setStaId(obj.getStaId());
                    // log.setCreateTime(obj.getCreateTime());
                    // log.setFinishTime(new Date());
                    // log.setWhOuId(obj.getWhOuId());
                    // log.setOwner(obj.getOwner());
                    // wmsIntransitNoticeOmsLogDao.save(log);
                    log.debug("rmi Call pac outbound response interface by MQ end:" + ob.getCode());
                } else {
                    BaseResult rs = rmi4Wms.wmsOperationsFeedback(ob);
                    if (rs.getStatus() == 0) {
                        /** --调用失败 --更新中间表错误次数、反馈信息-- **/
                        int errorCount = obj.getErrorCount().intValue() + 1;
                        Long newCount = Long.parseLong(String.valueOf(errorCount));
                        wmsIntransitNoticeOmsDao.updateNoticeById(obj.getId(), newCount, rs.getMsg());
                    } else {
                        /** --调用成功 --删除中间表信息，记录成功日志-- **/
                        wmsIntransitNoticeOmsDao.delete(obj);
                        WmsIntransitNoticeOmsLog log = new WmsIntransitNoticeOmsLog();
                        log.setStaCode(obj.getStaCode());
                        log.setStaId(obj.getStaId());
                        log.setCreateTime(obj.getCreateTime());
                        log.setFinishTime(new Date());
                        log.setWhOuId(obj.getWhOuId());
                        log.setOwner(obj.getOwner());
                        wmsIntransitNoticeOmsLogDao.save(log);
                    }
                }
                wmsIntransitNoticeOmsLogDao.flush();
                wmsIntransitNoticeOmsDao.flush();
                if (log.isInfoEnabled()) {
                    log.info("rmi Call pac outbound response interface------------------->END, staCode:{}, staSlipCode:{}, staType:{}", new Object[] {sta1.getCode(), sta1.getRefSlipCode(), sta1.getType()});
                }
            } catch (BusinessException e) {
                log.error("rmi Call oms outbound response interface error:, staCode:{}, staSlipCode:{}, staType:{}", new Object[] {sta1.getCode(), sta1.getRefSlipCode(), sta1.getType()});
                throw e;
            } catch (Exception e) {
                log.error("rmi Call oms outbound response interface error:, staCode:{}, staSlipCode:{}, staType:{}", new Object[] {sta1.getCode(), sta1.getRefSlipCode(), sta1.getType()});
                throw new BusinessException(ErrorCode.RMI_OMS_FAILURE);
            }
        } else {
            log.debug("Sta is null");
        }
    }

    /**
     * 将已出库的作业单的物流信息同步到快递雷达中
     * 
     * @param sta
     */
    @Override
    public void extractFromStaInfo(StockTransApplication sta) {
        try {
            List<PackageInfo> packageInfoList = packageInfoDao.findByStaId(sta.getId());
            if (packageInfoList != null) {
                if (log.isDebugEnabled()) {
                    log.debug("extractFromStaInfo, staCode:{}, packageInfoList is not null:{}", sta.getCode(), packageInfoList != null);
                }
                for (PackageInfo packageInfo : packageInfoList) {
                    String lpCode = packageInfo.getLpCode();
                    if (!("STO".equals(lpCode)) && !("EMS".equals(lpCode)) && !("SF".equals(lpCode))) {
                        break;
                    }

                    RadarTransNo radarTransNo = new RadarTransNo();
                    TransRoute transRoute = new TransRoute();
                    Long ouId = sta.getMainWarehouse().getId();
                    PhysicalWarehouse physicalWarehouse = physicalWarehouseDao.findPhysicalWarehouseByWhOuId(ouId, new BeanPropertyRowMapper<PhysicalWarehouse>(PhysicalWarehouse.class));
                    radarTransNo.setPhysicalWarehouse(physicalWarehouse);


                    radarTransNo.setOrderCode(sta.getCode()); // order_code
                    radarTransNo.setPCode(sta.getSlipCode2()); // p_code
                    radarTransNo.setOwner(sta.getOwner()); // owner
                    radarTransNo.setCreateTime(new Date()); // create_time
                    radarTransNo.setStatus(1); // finished or not
                    radarTransNo.setLastModifyTime(new Date()); // temporary
                    radarTransNo.setOutboundTime(new Date()); // temporary

                    String address = packageInfo.getStaDeliveryInfo().getAddress();
                    String expressCode = packageInfo.getTrackingNo();
                    radarTransNo.setLpcode(lpCode); // lpcode
                    radarTransNo.setExpressCode(expressCode);
                    radarTransNo.setAddress(address);
                    radarTransNo.setDestinationCity(packageInfo.getStaDeliveryInfo().getCity());
                    radarTransNo.setDestinationProvince(packageInfo.getStaDeliveryInfo().getProvince());
                    radarTransNo.setTransType(packageInfo.getStaDeliveryInfo().getTransType().getValue());
                    radarTransNo.setTelephone(packageInfo.getStaDeliveryInfo().getTelephone());
                    radarTransNo.setTransTimeType(packageInfo.getStaDeliveryInfo().getTransTimeType().getValue());
                    radarTransNo.setMobile(packageInfo.getStaDeliveryInfo().getMobile());
                    radarTransNo.setReceiver(packageInfo.getStaDeliveryInfo().getReceiver());

                    transRoute.setMessage("出库");
                    transRoute.setOperateTime(new Date());
                    transRoute.setLastModifyTime(new Date());
                    transRoute.setExpressCode(expressCode);
                    transRoute.setOpcode("已出库");
                    radarTransNoDao.save(radarTransNo);
                    transRoute.setRadarTransNo(radarTransNo);
                    transRouteDao.save(transRoute);
                    List<TransRoute> transRouteList = new ArrayList<TransRoute>();
                    transRouteList.add(transRoute);
                    expressRadarManager.updateOutBound(transRouteList);
                }
                radarTransNoDao.flush();
                transRouteDao.flush();
            }
        } catch (Exception e) {
            log.error("单号为： " + sta.getCode() + "的作业单未能同步到快递雷达中！", e.getMessage());
        }
    }

    /**
     * 设置快递的预期到达时间
     * 
     * @param sta
     */
    @Override
    public void expressPlanTime(StockTransApplication sta) {
        try {
            List<PackageInfo> packageInfoList = packageInfoDao.findByStaId(sta.getId());
            if (packageInfoList != null && !packageInfoList.isEmpty()) {
                if (log.isDebugEnabled()) {
                    log.debug("expressPlanTime staCode:{}, packageInfoList is not null:{}", sta.getCode(), null != packageInfoList);
                }
                PackageInfo packageInfo = packageInfoList.get(0);
                String lpCode = packageInfo.getLpCode();
                Long ouId = sta.getMainWarehouse().getId();
                PhysicalWarehouse physicalWarehouse = physicalWarehouseDao.findPhysicalWarehouseByWhOuId(ouId, new BeanPropertyRowMapper<PhysicalWarehouse>(PhysicalWarehouse.class));
                String expressCode = packageInfo.getTrackingNo();
                Transportator t = transportatorDao.findByCode(lpCode);
                Long lpId = null;
                if (t != null) {
                    lpId = t.getId();
                }
                ExpressOrderQueryCommand eoqc = new ExpressOrderQueryCommand();
                eoqc.setCity(packageInfo.getStaDeliveryInfo().getCity());
                eoqc.setProvince(packageInfo.getStaDeliveryInfo().getProvince());
                eoqc.setExpressCode(expressCode);
                if (physicalWarehouse != null) {
                    eoqc.setPwhId(physicalWarehouse.getId());
                } else {
                    log.warn("expressPlanTime physicalWarehouse is null, sta : {}", sta.getCode());
                }
                eoqc.setTakingTime(new Date());
                eoqc.setLpCode(lpCode);
                eoqc.setLpId(lpId);
                eoqc.setTimeType(packageInfo.getStaDeliveryInfo().getTransTimeType().getValue());
                // 获取揽件后配送的时间
                Long msec = expressOrderQueryManager.getWarningDate(eoqc, 2);
                if (msec > 0) {
                    Date planTime = new Date(msec);
                    sta.setWmsPlanArriveTime(planTime);
                    staDao.save(sta);
                }
            }
        } catch (Exception e) {
            log.error("单号为： " + sta.getCode() + "的作业单计算快递预期到达时间失败！", e);
        }
    }

    /**
     * 延迟到货短信通知
     * 
     * @param sta
     */
    public void delayArriveSmsSend(StockTransApplication sta) {
        try {
            if (sta.getStorePlanArriveTime() != null && sta.getWmsPlanArriveTime() != null) {
                Date omsDate = sta.getStorePlanArriveTime();
                Date wmsDate = sta.getWmsPlanArriveTime();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String omsDateString = sdf.format(omsDate);
                String wmsDateString = sdf.format(wmsDate);

                Long omsTime = sdf.parse(omsDateString).getTime();
                Long wmsTime = sdf.parse(wmsDateString).getTime();

                if (omsTime < wmsTime) {
                    // 生成待发送短信
                    BiChannel bc = companyShopDao.getByCode(sta.getOwner());
                    String stad = bc.getSmsTempArriveDelay();
                    if (stad == null || "".equals(stad.trim())) {
                        Map<String, String> option = chooseOptionManager.getOptionByCategoryCode(Constants.WMS_DELIVER_SMS);
                        stad = option.get(Constants.DEFAULT_SMS_TEMPLATE);
                    }
                    String receiver = null;
                    String shopName = bc.getName();
                    String orderCode = sta.getSlipCode2();
                    String express = null;
                    String transNo = null;
                    String planArriveTime = wmsDateString;
                    stad = stad.replaceAll(Constants.RECEIVER, receiver);
                    stad = stad.replaceAll(Constants.SHOP_NAME, shopName);
                    stad = stad.replaceAll(Constants.ORDER_CODE, orderCode);
                    stad = stad.replaceAll(Constants.EXPRESS, express);
                    stad = stad.replaceAll(Constants.TRANS_NO, transNo);
                    stad = stad.replaceAll(Constants.PLAN_ARRIVE_TIME, planArriveTime);


                }
            }

        } catch (Exception e) {
            log.error("延迟到货短信通知发送失败！订单编码为：" + sta.getCode(), e);
        }

    }

    // private void insertWxOrderQueue(StockTransApplication stas) {
    // try {
    // // 获取wx中间表所需要的数据
    // StockTransApplicationCommand sta = staDao.getWxconfirmOrderQueue(stas.getId(), new
    // BeanPropertyRowMapper<StockTransApplicationCommand>(StockTransApplicationCommand.class));
    // // 有几个包裹插几条记录
    // if (sta.getLpcode() != null && sta.getLpcode().equals(Transportator.WX)) {
    // if (log.isDebugEnabled()) {
    // log.debug("insertWxOrderQueue staCode:{}, lpcode:{}, lpcode is equals WX:{}", new Object[]
    // {sta.getCode(), sta.getLpcode(), Transportator.WX.equals(sta.getLpcode())});
    // }
    // List<PackageInfo> arrayList = packageInfoDao.findByStaId(stas.getId());
    // for (PackageInfo array : arrayList) {
    // WxConfirmOrderQueue wx = new WxConfirmOrderQueue();
    // wx.setCreateTime(new Date());
    // wx.setExeCount(0L);
    // wx.setMailno(array.getTrackingNo());
    // wx.setOrderId(sta.getExtTransOrderId());
    // wx.setStaCode(sta.getCode());
    // wx.setFilter4(array.getId().toString());
    // // DecimalFormat df = new DecimalFormat("0.00");
    // wx.setWeight(array.getWeight() + "");
    // // 插入中间表数据
    // wsConfirmOrderQueueDao.save(wx);
    // }
    // }
    // } catch (Exception e) {
    // log.error("insertWxOrderQueue error : " + e.toString());
    // }
    // }

    @Override
    public void staFinishNotifyPMS(StockTransApplication sta) {
        /**
         * 1.封装数据 2.调用PMS接口
         */
        // log.info("com.jumbo.wms.manager.listener.StaListenerManagerImpl.staFinishNotifyPMS(StockTransApplication) start.");
        StockTransApplication sta1 = staDao.getByPrimaryKey(sta.getId());
        List<ParcelBill> bills = convertParcelBill(sta1);
        if (bills != null && bills.size() > 0) {
            try {
                for (ParcelBill parcelBill : bills) {
                    appParcelManager.staFinishNotifyPMS(parcelBill);
                }
            } catch (Exception e) {
                log.error("staFinishNotifyPMS Execution error , orderCode [" + sta1.getSlipCode1() + "], please check~");
            }
        }
        // log.info("com.jumbo.wms.manager.listener.StaListenerManagerImpl.staFinishNotifyPMS(StockTransApplication) end.");
    }

    public List<ParcelBill> convertParcelBill(StockTransApplication sta1) {
        List<ParcelBill> bills = new ArrayList<ParcelBill>();
        ParcelBill parcel = null;
        try {
            StaDeliveryInfo sd = sta1.getStaDeliveryInfo();
            if (sd != null) {
                List<PackageInfo> infos = sd.getPackageInfos();
                if (infos != null && infos.size() > 0) {
                    // 子母单的情况会有多个运单号
                    for (PackageInfo packageInfo : infos) {
                        parcel = new ParcelBill();
                        parcel.setAddress(sd.getAddress());
                        parcel.setMailNo(packageInfo.getTrackingNo());
                        parcel.setLpcode(packageInfo.getLpCode());
                        parcel.setReceiver(sd.getReceiver());
                        parcel.setReceiverMobile(sd.getMobile());
                        parcel.setRemark(sd.getRemark());
                        parcel.setIsCod(sd.getIsCod());
                        parcel.setExtTransOrderId(sd.getExtTransOrderId());
                        parcel.setOmsOrderCode(sta1.getRefSlipCode());
                        parcel.setOuterOrderCode(sta1.getSlipCode2());
                        parcel.setType(new Integer(2)); // parcel_delivered
                        parcel.setOriginCode(sta1.getMainWarehouse().getCode());
                        parcel.setDestinationCode(sta1.getToLocation());
                        List<ParcelLineBill> lineBills = new ArrayList<ParcelLineBill>();
                        ParcelLineBill parcelLine = null;
                        // 获取作业单行信息
                        List<StaLine> stalines = staLineDao.findByStaId(sta1.getId());
                        for (StaLine staLine2 : stalines) {
                            parcelLine = new ParcelLineBill();
                            Sku sku = skuDao.getByPrimaryKey(staLine2.getSku().getId());
                            if (sku != null) {
                                parcelLine.setBarCode(sku.getBarCode());
                                parcelLine.setSkuCode(sku.getExtensionCode1());
                                parcelLine.setSkuName(sku.getName());
                            }
                            parcelLine.setQuantity(staLine2.getQuantity());
                            lineBills.add(parcelLine);
                        }
                        parcel.setLineBills(lineBills);
                        bills.add(parcel);
                    }
                }
            }
        } catch (Exception e) {
            log.error("convertParcelBill Execution error , orderCode [" + sta1.getSlipCode1() + "], please check~");
        }
        return bills;
    }

    @Override
    public void staInTransitNotifyPMS(StockTransApplication sta1) {
        /**
         * 1.封装数据 2.调用PMS接口
         */
        StockTransApplication sta = staDao.getByPrimaryKey(sta1.getId());
        ShipmentInTransitVo shipment = convertShipmentCommand(staDao.getByPrimaryKey(sta.getId()));
        try {
            apiShipmentManager.createShipment(ShipmentInTransitVo.OPTYPE_HAS_MAILNO, shipment);
        } catch (Exception e) {
            log.error("staInTransitNotifyPMS Execution error , orderCode [" + sta.getSlipCode1() + "], please check~");
        }
    }

    public ShipmentInTransitVo convertShipmentCommand(StockTransApplication sta) {
        ShipmentInTransitVo shipment = new ShipmentInTransitVo();
        try {
            StaDeliveryInfo sd = sta.getStaDeliveryInfo();
            if (sd != null) {
                /**
                 * ShipmentCommand
                 */
                // shipment.setTargetSys(targetSys);//TODO SG 值域待定
                shipment.setTransTimeType(sd.getTransTimeType() == null ? TransTimeType.ORDINARY.getValue() : sd.getTransTimeType().getValue());// 默认普通
                shipment.setOriginCode(sta.getMainWarehouse().getCode());
                shipment.setDestinationCode(sta.getToLocation());
                shipment.setChannelCode(sta.getOwner());
                shipment.setOmsOrderCode(sta.getSlipCode1());
                shipment.setPlatformOrderCode(sta.getSlipCode2());
                shipment.setReceiver(sd.getReceiver());
                shipment.setReceiverMobile(sd.getMobile());
                shipment.setAddress(sd.getAddress());
                shipment.setCountry(sd.getCountry());
                shipment.setProvince(sd.getProvince());
                shipment.setCity(sd.getCity());
                shipment.setDistrict(sd.getDistrict());
                shipment.setZipcode(sd.getZipcode());
                shipment.setTotalCharges(sta.getTotalActual());// 取订单运费
                shipment.setRemark(sd.getTransMemo());// 不取发货备注 待定
                shipment.setIsCod(sd.getIsCod());
                shipment.setType(ShipmentInTransitVo.ENTRANCE_SO);
                List<PackageInfo> infos = sd.getPackageInfos();
                List<ParcelInfoCommand> parcelCommands = new ArrayList<ParcelInfoCommand>();
                ParcelInfoCommand parcel = null;
                if (infos != null && infos.size() > 0) {
                    // 子母单的情况会有多个运单号
                    for (PackageInfo packageInfo : infos) {
                        /**
                         * ParcelCommand
                         */
                        parcel = new ParcelInfoCommand();
                        // parcel.setShipmentContents(shipmentContents);//TODO SG
                        parcel.setExtTransOrderId(sd.getExtTransOrderId());
                        parcel.setMailNo(packageInfo.getTrackingNo());
                        parcel.setLpcode(packageInfo.getLpCode());
                        parcel.setRemark(sd.getRemark());
                        parcel.setDeliveryTime(new Date());

                        /**
                         * ParcelLineCommand
                         */
                        List<ParcelInfoLineCommand> lineCommands = new ArrayList<ParcelInfoLineCommand>();
                        ParcelInfoLineCommand parcelLine = null;
                        // 获取作业单行信息
                        List<StaLine> stalines = staLineDao.findByStaId(sta.getId());
                        int parcelTotalQty = 0;
                        for (StaLine staLine2 : stalines) {
                            parcelLine = new ParcelInfoLineCommand();
                            Sku sku = skuDao.getByPrimaryKey(staLine2.getSku().getId());
                            if (sku != null) {
                                parcelLine.setBarCode(sku.getBarCode());
                                parcelLine.setSkuCode(sku.getCode());
                                parcelLine.setSkuName(sku.getName());
                            }
                            parcelLine.setQuantity(staLine2.getQuantity());
                            lineCommands.add(parcelLine);
                            parcelTotalQty += staLine2.getQuantity();
                        }
                        parcel.setParcelInfoLineCommands(lineCommands);
                        parcel.setTotalQty(new Long(parcelTotalQty));
                        parcelCommands.add(parcel);
                    }
                    shipment.setParcelCount(parcelCommands.size());
                    shipment.setParcelInfoCommands(parcelCommands);
                }
            }
        } catch (Exception e) {
            log.error("convertShipmentCommand Execution error , orderCode [" + sta.getSlipCode1() + "], please check~");
        }
        return shipment;
    }

    /**
     * 生成speedo库存状态调整信息
     * 
     * @param sta
     * @param wh
     */
    @Override
    public void vmiStatusSpeedo(StockTransApplication sta, Warehouse wh) {
        // 从库存日志表中查询speedo库存状态调整数据插入t_sta_adj,t_sta_adj_line表
        // 查询入库状态
        List<StockTransTxLogCommand> LogList1 = vmiDefaultManager.findAdjLog(sta.getCode(), 1);
        // 查询出库状态
        List<StockTransTxLogCommand> LogList2 = vmiDefaultManager.findAdjLog(sta.getCode(), 2);
        VmiStatusAdjDefault adjDefault = null;
        VmiStatusAdjLineDefault adjLineDefault = null;
        List<VmiStatusAdjLineDefault> staLineList = new ArrayList<VmiStatusAdjLineDefault>();
        List<VmiStatusAdjDefault> staAdjList = new ArrayList<VmiStatusAdjDefault>();
        // StockTransApplication stockTran = null;
        for (StockTransTxLogCommand stock1 : LogList1) {
            adjLineDefault = new VmiStatusAdjLineDefault();
            adjDefault = new VmiStatusAdjDefault();
            for (StockTransTxLogCommand stock2 : LogList2) {
                if (stock2.getSkuId().equals(stock1.getSkuId())) {
                    // stockTran = stockTransApplicationDao.findStaTypeBycode(stock1.getStaCode(),
                    // new
                    // BeanPropertyRowMapperExt<StockTransApplicationCommand>(StockTransApplicationCommand.class));
                    adjLineDefault.setFromStatus(stock2.getStatusId().toString());
                    adjLineDefault.setToStatus(stock1.getStatusId().toString());
                    adjDefault.setCreateTime(stock2.getTranTime());
                    adjDefault.setFinishTime(stock1.getTranTime());
                    // 根据staid获取库存数量
                    // StaLine staline = stalineDao.findStaLineBySkuId(stock1.getSkuId(),
                    // stockTran.getId());
                    adjLineDefault.setQty(stock1.getOutQty());
                }
            }
            // 获取skuid
            SkuCommand sku = skudao.findSkuBySkuId(stock1.getSkuId(), new BeanPropertyRowMapper<SkuCommand>(SkuCommand.class));
            // 根据wh_id获取仓库编码 OperationUnit
            OperationUnit op = operationUnitDao.findOperationUnitWarehouseopc(stock1.getWhId());
            adjDefault.setWhCode(op.getCode());
            adjDefault.setStoreCode("SPEEDO");
            adjDefault.setStatus(VmiGeneralStatus.NEW);
            adjDefault.setOrderCode(sta.getCode());
            staAdjList.add(adjDefault);
            try {
                adjLineDefault.setUpc(sku.getDpProp2());
                if (!adjLineDefault.getFromStatus().isEmpty()) {
                    adjLineDefault.setAdjId(vmiStatusAdjDao.save(adjDefault));
                    staLineList.add(adjLineDefault);
                }
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
        if (!staLineList.isEmpty()) {
            for (VmiStatusAdjLineDefault vmiStatuLine : staLineList) {
                vmiStatusLineAdjDao.save(vmiStatuLine);
            }
        }
    }

    public void saveCartonNo(StockTransApplication sta) {
        List<PackageInfoCommand> list = packageInfoDao.getPackageInfoByStaId(sta.getId(), new BeanPropertyRowMapper<PackageInfoCommand>(PackageInfoCommand.class));
        for (PackageInfoCommand packageInfoCommand : list) {
            if (packageInfoCommand.getTrackingNo().equals(sta.getStaDeliveryInfo().getTrackingNo())) {
                NikeCartonNo cartonNo = new NikeCartonNo();
                cartonNo.setAoId(sta.getSlipCode1());
                cartonNo.setCartonNo(null);
                cartonNo.setCity(sta.getStaDeliveryInfo().getCity());
                cartonNo.setConsumerAddress(sta.getStaDeliveryInfo().getAddress());
                cartonNo.setCNID(sta.getSlipCode2());
                cartonNo.setCreateTime(new Date());
                cartonNo.setConsumerTel(sta.getStaDeliveryInfo().getTelephone());
                cartonNo.setCPPcode(sta.getNikePickUpCode());
                cartonNo.setCPPtype(sta.getNikePickUpType());
                cartonNo.setDistrict(sta.getStaDeliveryInfo().getDistrict());
                cartonNo.setIsCPP(sta.getIsNikePick());
                cartonNo.setIsCod(sta.getStaDeliveryInfo().getIsCod());
                cartonNo.setProvince(sta.getStaDeliveryInfo().getProvince());
                cartonNo.setConsumer(sta.getStaDeliveryInfo().getReceiver());
                // cartonNo.setServiceLevel(cartonNoConfirm.getServiceLevel());
                cartonNo.setTrackingNo(packageInfoCommand.getTrackingNo());
                cartonNo.setStatus(1);
                cartonNo.setTrackingType("1");
                cartonNoDao.save(cartonNo);
                List<StaLine> lines = staLineDao.findByStaId(sta.getId());
                for (StaLine staLine : lines) {
                    NikeCartonNoLine cartonNoLine = new NikeCartonNoLine();
                    cartonNoLine.setCartonNo(cartonNo);
                    cartonNoLine.setQty(staLine.getQuantity());
                    cartonNoLine.setUPC(staLine.getSku().getExtensionCode2());
                    // cartonNoLine.setStyle(staLine.getSku().gets);
                    cartonNoLine.setSize(staLine.getSku().getSkuSize());
                    cartonNoLine.setColor(staLine.getSku().getColor());
                    cartonNoLineDao.save(cartonNoLine);
                }
            } else {
                NikeCartonNo cartonNo = new NikeCartonNo();
                cartonNo.setAoId(sta.getSlipCode1());
                cartonNo.setCartonNo(null);
                cartonNo.setCreateTime(new Date());
                cartonNo.setCity(sta.getStaDeliveryInfo().getCity());
                cartonNo.setConsumerAddress(sta.getStaDeliveryInfo().getAddress());
                cartonNo.setCNID(sta.getSlipCode2());
                cartonNo.setConsumerTel(sta.getStaDeliveryInfo().getTelephone());
                cartonNo.setIsCod(sta.getStaDeliveryInfo().getIsCod());
                cartonNo.setCPPcode(sta.getNikePickUpCode());
                cartonNo.setCPPtype(sta.getNikePickUpType());
                cartonNo.setDistrict(sta.getStaDeliveryInfo().getDistrict());
                cartonNo.setIsCPP(sta.getIsNikePick());
                cartonNo.setStatus(1);
                cartonNo.setProvince(sta.getStaDeliveryInfo().getProvince());
                cartonNo.setConsumer(sta.getStaDeliveryInfo().getReceiver());
                // cartonNo.setServiceLevel(cartonNoConfirm.getServiceLevel());
                cartonNo.setTrackingNo(packageInfoCommand.getTrackingNo());
                cartonNo.setTrackingType("1");
                cartonNoDao.save(cartonNo);
            }
        }
        NikeCartonNo returncartonNo = new NikeCartonNo();
        returncartonNo.setAoId(sta.getSlipCode1());
        returncartonNo.setCartonNo(null);
        returncartonNo.setCreateTime(new Date());
        returncartonNo.setCity(sta.getStaDeliveryInfo().getCity());
        returncartonNo.setConsumerAddress(sta.getStaDeliveryInfo().getAddress());
        returncartonNo.setCNID(sta.getSlipCode2());
        returncartonNo.setConsumerTel(sta.getStaDeliveryInfo().getTelephone());
        returncartonNo.setCPPcode(sta.getNikePickUpCode());
        returncartonNo.setCPPtype(sta.getNikePickUpType());
        returncartonNo.setIsCod(sta.getStaDeliveryInfo().getIsCod());
        returncartonNo.setStatus(1);
        returncartonNo.setDistrict(sta.getStaDeliveryInfo().getDistrict());
        returncartonNo.setIsCPP(sta.getIsNikePick());
        returncartonNo.setProvince(sta.getStaDeliveryInfo().getProvince());
        returncartonNo.setConsumer(sta.getStaDeliveryInfo().getReceiver());
        // cartonNo.setServiceLevel(cartonNoConfirm.getServiceLevel());
        returncartonNo.setTrackingNo(sta.getStaDeliveryInfo().getReturnTransNo());
        returncartonNo.setTrackingType("2");
        cartonNoDao.save(returncartonNo);


    }

    @Override
    public void wmsCommonMessageProducerErrorMq(Long id) {
        MessageProducerError error = messageProducerErrorDao.getByPrimaryKey(id);

        try {
            if ("com.jumbo.wms.manager.listener.StvListenerManagerImpl.inventoryTransactionToOms".equals(error.getMsgType()) && error.getStvId() != null) {
                StockTransVoucher stv = stvDao.getByPrimaryKey(error.getStvId());
                stvListenerManager.inventoryTransactionToOms(stv, true);
                error.setStatus(10);
            } else {
                MessageCommond mes = new MessageCommond();
                mes.setMsgId(error.getId().toString() + "," + System.currentTimeMillis() + UUIDUtil.getUUID());
                mes.setIsMsgBodySend(false);
                mes.setMsgType(error.getMsgType());
                mes.setMsgBody(error.getMsgBody());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date();
                mes.setSendTime(sdf.format(date));
                producerServer.sendDataMsgConcurrently(mes.getTopic(), mes);
                // 保存进mongodb
                MongoDBMessage mdbm = new MongoDBMessage();
                BeanUtils.copyProperties(mdbm, mes);
                mdbm.setStaCode(error.getStaCode());
                mdbm.setOtherUniqueKey(error.getSlipCode());
                mdbm.setMsgBody(error.getMsgBody());
                mdbm.setMemo(mes.getTopic());
                mongoOperation.save(mdbm);
                log.debug("rmi Call oms wmsCommonMessageProducerErrorMq response interface by MQ end:" + error.getId());
                error.setStatus(10);
            }
        } catch (Exception e) {
            log.error("wmsCommonMessageProducerErrorMq isMQ  error---:" + error.getId());
            if (error.getNum() == null) {
                error.setNum(1);
            } else {
                error.setNum(error.getNum() + 1);
            }
            long currentTime = System.currentTimeMillis() + 10 * 60 * 1000;
            Date date = new Date(currentTime);
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String nowTime = df.format(date);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date dates;
            try {
                dates = sdf.parse(nowTime);
                error.setNextTime(dates);
            } catch (ParseException e1) {
                if (log.isErrorEnabled()) {
                    log.error("wmsCommonMessageProducerErrorMq-ParseException", e1);
                }
            }
            if (log.isErrorEnabled()) {
                log.error("wmsCommonMessageProducerErrorMq-exception", e);
            }
        }
        messageProducerErrorDao.save(error);
    }

    @Override
    public void wmsRtnOutBountMq(Long id) {
        System.out.println("==============");
        MsgRtnOutbound msgRtnOut = msgRtnOutboundDao.getByPrimaryKey(id);// 发mq
        List<String> sourceList = new ArrayList<String>();
        List<ChooseOption> chooselist = chooseOptionManager.findOptionListByCategoryCode(Constants.THREE_PL_USER_CONFIG);
        for (ChooseOption chooseOption : chooselist) {
            sourceList.add(chooseOption.getOptionKey());
        }
        sourceList.add("WLB");
        sourceList.add("OMSTmall");
        sourceList.add("CJ");
        Boolean b = sourceList.contains(msgRtnOut.getSource());
        try {
            String reqJson = JsonUtil.writeValue(msgRtnOut);
            MessageCommond mes = new MessageCommond();
            mes.setIsMsgBodySend(false);
            mes.setMsgId(msgRtnOut.getId().toString() + "," + System.currentTimeMillis() + UUIDUtil.getUUID());
            mes.setMsgType("com.jumbo.wms.manager.listener.StaListenerManagerImpl.wmsOrderFinishOms.wmsRtnOutBountMq");
            mes.setMsgBody(reqJson);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date();
            mes.setSendTime(sdf.format(date));
            if (b) {
                producerServer.sendDataMsgConcurrently(MQ_WMS3_MQ_RTN_OUTBOUNT_YH, mes);
            } else {
                producerServer.sendDataMsgConcurrently(MQ_WMS3_MQ_RTN_OUTBOUNT_LF, mes);
            }
            // 保存进mongodb
            MongoDBMessage mdbm = new MongoDBMessage();
            BeanUtils.copyProperties(mes, mdbm);
            mdbm.setStaCode(msgRtnOut.getStaCode());
            mdbm.setOtherUniqueKey(null);
            mdbm.setMsgBody(reqJson);
            if (b) {
                mdbm.setMemo(MQ_WMS3_MQ_RTN_OUTBOUNT_YH);
            } else {
                mdbm.setMemo(MQ_WMS3_MQ_RTN_OUTBOUNT_LF);
            }
            mongoOperation.save(mdbm);
            log.debug("rmi Call oms wmsRtnOutBountMq response interface by MQ end:" + msgRtnOut.getId());
            msgRtnOut.setMqStatus("10");
        } catch (Exception e) {
            log.error("uploadWmsSalesOrder isMQ  error---:" + msgRtnOut.getStaCode());
            if (msgRtnOut.getErrorCount() == null) {
                msgRtnOut.setErrorCount(1l);
            } else {
                msgRtnOut.setErrorCount(msgRtnOut.getErrorCount() + 1);
            }
            long currentTime = System.currentTimeMillis() + 10 * 60 * 1000;
            Date date = new Date(currentTime);
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String nowTime = df.format(date);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date dates;
            try {
                dates = sdf.parse(nowTime);
                msgRtnOut.setNextTime(dates);
            } catch (ParseException e1) {
                if (log.isErrorEnabled()) {
                    log.error("wmsRtnOutBountMq-ParseException", e);
                }
            }
            if (log.isErrorEnabled()) {
                log.error("wmsRtnOutBountMq-exception", e);
            }
        }
        msgRtnOutboundDao.save(msgRtnOut);
    }

    public void insertStaCheckDetial(StockTransApplication sta) {
        List<StaLineCommand> staLineList = staLineDao.findSnOrExpDateSkuByStaId(sta.getId(), new BeanPropertyRowMapper<StaLineCommand>(StaLineCommand.class));
        if (staLineList != null && staLineList.size() > 0) {
            for (StaLineCommand staLine : staLineList) {
                if (staLine.getEffectSku() != null && staLine.getEffectSku() == 33) {
                    List<StaCheckLog> staCheckLogList = staCheckLogDao.findExpDateBySkuIdAndSlipCode(staLine.getSkuId(), sta.getSlipCode1(), new BeanPropertyRowMapper<StaCheckLog>(StaCheckLog.class));
                    for (StaCheckLog staCheckLog : staCheckLogList) {
                        StaCheckDetial staCheckDatial = new StaCheckDetial();
                        staCheckDatial.setLogDate(new Date());
                        staCheckDatial.setQty(staCheckLog.getQty());
                        staCheckDatial.setSkuId(staLine.getSkuId());
                        staCheckDatial.setExpDate(staCheckLog.getExpDate());
                        staCheckDatial.setStaId(sta.getId());
                        staCheckDetialDao.save(staCheckDatial);
                    }
                }
                if (staLine.getIsSn() != null && staLine.getIsSn()) {
                    List<String> snList = staCheckLogDao.findSnBySkuIdAndSlipCode(staLine.getSkuId(), sta.getSlipCode1(), new SingleColumnRowMapper<String>(String.class));
                    for (String sn : snList) {
                        StaCheckDetial staCheckDatial = new StaCheckDetial();
                        staCheckDatial.setLogDate(new Date());
                        staCheckDatial.setQty(1L);
                        staCheckDatial.setSkuId(staLine.getSkuId());
                        staCheckDatial.setSn(sn);
                        staCheckDatial.setStaId(sta.getId());
                        staCheckDetialDao.save(staCheckDatial);
                    }
                }
                if (staLine.getSkuRfid() != null && staLine.getSkuRfid()) {
                    List<String> skuRfidList = staCheckLogDao.findRfidBySkuIdAndSlipCode(staLine.getSkuId(), sta.getSlipCode1(), new SingleColumnRowMapper<String>(String.class));
                    for (String rfid : skuRfidList) {
                        StaCheckDetial staCheckDatial = new StaCheckDetial();
                        staCheckDatial.setLogDate(new Date());
                        staCheckDatial.setQty(1L);
                        staCheckDatial.setSkuId(staLine.getSkuId());
                        staCheckDatial.setRfid(rfid);
                        staCheckDatial.setStaId(sta.getId());
                        staCheckDetialDao.save(staCheckDatial);
                    }
                }
            }
        }
    }

    /**
     * 直连 创单 反馈
     */

    @Override
    public void wmsOrderFinishOms(Long id) {
        List<WmsConfirmOrder> cList = new ArrayList<WmsConfirmOrder>();
        WmsConfirmOrderQueue com = wmsConfirmOrderQueueDao.getByPrimaryKey(id);
        WmsConfirmOrder c = wmsConfirmOrderQueueDao.getWmsConfirmOrderById(id, new BeanPropertyRowMapper<WmsConfirmOrder>(WmsConfirmOrder.class));
        c.setOtherOutboundTime(com.getCreateTime());
        c.setStatusType("ORDER_NOTIFY");
        List<WmsShipping> sl = wmsShippingQueueDao.findAllShippingById(c.getId(), new BeanPropertyRowMapper<WmsShipping>(WmsShipping.class));
        for (WmsShipping ws : sl) {
            List<WmsShippingLine> sll = wmsShippingLineQueueDao.findAllShippingLineById(ws.getId(), new BeanPropertyRowMapper<WmsShippingLine>(WmsShippingLine.class));
            ws.setLines(sll);
        }
        c.setShippings(sl);
        cList.add(c);
        // 1、斯凯奇等走适配器-->hub4.0 创单反馈
        //List<WmsConfirmOrder> cListHub4 = new ArrayList<WmsConfirmOrder>();
        Boolean flag = false;
        List<String> chooseOptionList = chooseOptionManager.findByCategoryCode(Constants.WMS_ADAPTER_SYSTEM_KEY_CONFIG);
        if (null != chooseOptionList && chooseOptionList.size() > 0) {
            if (!chooseOptionList.contains(com.getSystemKey())) {
                flag = true;
                //cListHub4.add(c);
            }
        }

        /*
         * List<CommonConfig> cfg =
         * commonConfigDao.findCommonConfigListByParaGroup(Constants.WMS_ADAPTER_OWNER_CONFIG); if
         * (cfg != null && !cfg.isEmpty()) { for (CommonConfig commonConfig : cfg) { if
         * (org.apache.commons.lang3.StringUtils.equals(c.getOwner(), commonConfig.getParaValue()))
         * { flag = true; cListHub4.add(c); } } }
         */

        if (flag) {
            try {
                log.info("rmi Call hub4.0 wmsOrderFinishOms response begin!");
                Response response = wms3AdapterInteractManager.wmsOrderFinishOms(c);
                log.info("rmi Call hub4.0 wmsOrderFinishOms response end!");
                if (response != null) {
                    log.info("rmi Call hub4.0 wmsOrderFinishOms response!response=" + JsonUtil.writeValue(response));
                }
                MongoDBMessage mdbm = new MongoDBMessage();
                mdbm.setStaCode(null);
                mdbm.setOtherUniqueKey(c.getOrderCode());
                mdbm.setMsgBody(JsonUtil.writeValue(c));
                mdbm.setSendTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                if (response != null) {
                    mdbm.setMemo("推送wms3.0->hub4.0接口适配器(创单反馈)->response:" + JsonUtil.writeValue(response));
                } else {
                    mdbm.setMemo("推送wms3.0->hub4.0接口适配器(创单反馈)->response is null!");
                }
                mongoOperation.save(mdbm);

                if (response != null && response.getOrderResponse() != null && !response.getOrderResponse().isEmpty() && response.getOrderResponse().get(0) != null && response.getOrderResponse().get(0).getStatus() == 1) {
                    com.setMqStatus("10");// 发送OK
                } else {
                    // 接收异常
                    if (com.getErrorCount() == null) {
                        com.setErrorCount(1);
                    } else {
                        com.setErrorCount(com.getErrorCount() + 1);
                    }
                }
            } catch (Exception e) {
                log.error("rmi Call hub4.0 wmsOrderFinishOms response error :" + c.getOrderCode(), e);
                if (com.getErrorCount() == null) {
                    com.setErrorCount(1);
                } else {
                    com.setErrorCount(com.getErrorCount() + 1);
                }
            }
            wmsConfirmOrderQueueDao.save(com);
            return;
        }
        // 2、其他直连订单走MQ
        String reqJson = JsonUtil.writeValue(cList);
        try {
            MessageCommond mes = new MessageCommond();
            mes.setIsMsgBodySend(false);
            mes.setMsgId(c.getId().toString() + "," + System.currentTimeMillis() + UUIDUtil.getUUID());
            mes.setMsgType("com.jumbo.wms.manager.listener.StaListenerManagerImpl.wmsOrderFinishOms");
            mes.setMsgBody(reqJson);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date();
            mes.setSendTime(sdf.format(date));
            // producerServer.sendDataMsgConcurrently(MQ_WMS3_SALES_ORDER_SERVICE_RETURN, mes);
            producerServer.sendDataMsgConcurrently(MQ_WMS3_SALES_ORDER_SERVICE_RETURN, c.getOwner(), mes);
            // 保存进mongodb
            MongoDBMessage mdbm = new MongoDBMessage();
            BeanUtils.copyProperties(mes, mdbm);
            mdbm.setStaCode(null);
            mdbm.setOtherUniqueKey(c.getOrderCode());
            mdbm.setMsgBody(reqJson);
            mdbm.setMemo(MQ_WMS3_SALES_ORDER_SERVICE_RETURN);
            if(c.getOwner() !=null && c.getOwner().contains("IT后端测试")){
                MongoDBMessageTest mdbmTest = new MongoDBMessageTest();
                BeanUtils.copyProperties(mdbm, mdbmTest);
                mongoOperation.save(mdbmTest);
            }else{
                mongoOperation.save(mdbm);
            }
            log.debug("rmi Call oms wmsOrderFinishOms response interface by MQ end:" + c.getId());
            com.setMqStatus("10");// 发送OK
        } catch (Exception e) {
            log.error("rmi Call oms wmsOrderFinishOms response error :" + c.getId());
            if (com.getErrorCount() == null) {
                com.setErrorCount(1);
            } else {
                com.setErrorCount(com.getErrorCount() + 1);
            }
        }
        wmsConfirmOrderQueueDao.save(com);
    }
    
    private void createPingAnCoverInfo(StockTransApplication sta1){
        // 订单金额小于1000，走平安投保
        if (sta1.getTotalActual().compareTo(new BigDecimal(1000)) < 0 && sta1.getType().getValue() == 21) {
            BiChannel shop = biChannelDao.getByCode(sta1.getOwner());
            ChannelWhRef whRef = refDao.getChannelRef(sta1.getMainWarehouse().getId(), shop.getId());
            if (whRef != null && whRef.getIsPinganCover()) {
                try {
                    pinanCoverManager.createPingAnCover(sta1.getId(), sta1.getMainWarehouse().getId(), staDeliveryInfoDao.getByPrimaryKey(sta1.getId()).getTrackingNo(),staDeliveryInfoDao.getByPrimaryKey(sta1.getId()).getLpCode());
                } catch (Exception e) {
                    log.error("平安投保失败:", e);
                    throw new BusinessException("平安投保失败");
                }
            }
        }
    }
    



}
