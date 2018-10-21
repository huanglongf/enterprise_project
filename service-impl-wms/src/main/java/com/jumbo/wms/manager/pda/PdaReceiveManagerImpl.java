package com.jumbo.wms.manager.pda;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.jumbo.dao.authorization.OperationUnitDao;
import com.jumbo.dao.automaticEquipment.MsgToWcsDao;
import com.jumbo.dao.automaticEquipment.WhContainerDao;
import com.jumbo.dao.baoshui.CustomsDeclarationDao;
import com.jumbo.dao.baseinfo.ChannelWhRefRefDao;
import com.jumbo.dao.baseinfo.SkuBarcodeDao;
import com.jumbo.dao.baseinfo.SkuDao;
import com.jumbo.dao.baseinfo.WarehouseDao;
import com.jumbo.dao.hub2wms.WmsOrderStatusOmsDao;
import com.jumbo.dao.hub2wms.WmsTransInfoOmsDao;
import com.jumbo.dao.pda.PdaStaShelvesPlanDao;
import com.jumbo.dao.pda.StaCartonDao;
import com.jumbo.dao.pda.StaCartonLineDao;
import com.jumbo.dao.pda.StaCartonLineSnDao;
import com.jumbo.dao.pda.StaOpDetailDao;
import com.jumbo.dao.pda.StaOpLogDao;
import com.jumbo.dao.system.ChooseOptionDao;
import com.jumbo.dao.warehouse.AdvanceOrderAddInfoDao;
import com.jumbo.dao.warehouse.BiChannelDao;
import com.jumbo.dao.warehouse.BiChannelImperfectDao;
import com.jumbo.dao.warehouse.BiChannelImperfectLineDao;
import com.jumbo.dao.warehouse.InventoryDao;
import com.jumbo.dao.warehouse.InventoryStatusDao;
import com.jumbo.dao.warehouse.PackageInfoDao;
import com.jumbo.dao.warehouse.PdaHandOverCurrencyDao;
import com.jumbo.dao.warehouse.PdaHandOverDao;
import com.jumbo.dao.warehouse.PdaHandOverLogDao;
import com.jumbo.dao.warehouse.PdaTransitInnerLogDao;
import com.jumbo.dao.warehouse.RecieverInfoDao;
import com.jumbo.dao.warehouse.SkuImperfectDao;
import com.jumbo.dao.warehouse.SkuSnDao;
import com.jumbo.dao.warehouse.StaDeliveryInfoDao;
import com.jumbo.dao.warehouse.StaLineDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.dao.warehouse.StockTransTxLogDao;
import com.jumbo.dao.warehouse.TransactionTypeDao;
import com.jumbo.dao.warehouse.WarehouseLocationDao;
import com.jumbo.dao.warehouse.WmsIntransitNoticeOmsDao;
import com.jumbo.util.JsonUtil;
import com.jumbo.util.StringUtil;
import com.jumbo.wms.Constants;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.outbound.AdCheckManager;
import com.jumbo.wms.manager.system.SequenceManager;
import com.jumbo.wms.manager.warehouse.BaseQueryThreadPoolManager;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.manager.warehouse.WareHouseManagerExecute;
import com.jumbo.wms.model.DefaultStatus;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.authorization.User;
import com.jumbo.wms.model.automaticEquipment.MsgToWcs;
import com.jumbo.wms.model.automaticEquipment.MsgToWcsRequest.SShouRongQi;
import com.jumbo.wms.model.automaticEquipment.PopUpArea;
import com.jumbo.wms.model.automaticEquipment.WcsInterfaceType;
import com.jumbo.wms.model.automaticEquipment.WhContainer;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.baseinfo.ChannelWhRef;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.baseinfo.SkuBarcode;
import com.jumbo.wms.model.baseinfo.Warehouse;
import com.jumbo.wms.model.command.SkuBarcodeCommand;
import com.jumbo.wms.model.command.SkuCommand;
import com.jumbo.wms.model.mongodb.AsnReceive;
import com.jumbo.wms.model.mongodb.InboundSkuWeightCheck;
import com.jumbo.wms.model.mongodb.OrderLine;
import com.jumbo.wms.model.mongodb.StaCarton;
import com.jumbo.wms.model.mongodb.StaCartonLine;
import com.jumbo.wms.model.mongodb.StaCartonLineSn;
import com.jumbo.wms.model.pda.PdaStaShelvesPlan;
import com.jumbo.wms.model.pda.PdaStaShelvesPlanCommand;
import com.jumbo.wms.model.pda.StaOpDetail;
import com.jumbo.wms.model.pda.StaOpDetailCommand;
import com.jumbo.wms.model.vmi.Default.AsnOrderType;
import com.jumbo.wms.model.warehouse.AdvanceOrderAddInfo;
import com.jumbo.wms.model.warehouse.BiChannelImperfect;
import com.jumbo.wms.model.warehouse.BiChannelImperfectLine;
import com.jumbo.wms.model.warehouse.HandOverList;
import com.jumbo.wms.model.warehouse.InboundStoreMode;
import com.jumbo.wms.model.warehouse.InventoryCommand;
import com.jumbo.wms.model.warehouse.InventoryStatus;
import com.jumbo.wms.model.warehouse.PackageInfo;
import com.jumbo.wms.model.warehouse.PackageInfoStatus;
import com.jumbo.wms.model.warehouse.PdaHandOver;
import com.jumbo.wms.model.warehouse.PdaHandOverCommand;
import com.jumbo.wms.model.warehouse.PdaHandOverCurrency;
import com.jumbo.wms.model.warehouse.PdaHandOverLog;
import com.jumbo.wms.model.warehouse.PdaTransitInnerLog;
import com.jumbo.wms.model.warehouse.SkuImperfect;
import com.jumbo.wms.model.warehouse.SkuSn;
import com.jumbo.wms.model.warehouse.SkuSnCommand;
import com.jumbo.wms.model.warehouse.StaLine;
import com.jumbo.wms.model.warehouse.StaLineCommand;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationStatus;
import com.jumbo.wms.model.warehouse.StockTransTxLog;
import com.jumbo.wms.model.warehouse.TransactionDirection;
import com.jumbo.wms.model.warehouse.TransactionType;
import com.jumbo.wms.model.warehouse.WarehouseLocation;
import com.jumbo.wms.model.warehouse.baoShui.CustomsDeclaration;

import loxia.dao.support.BeanPropertyRowMapperExt;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Transactional
@Service("pdaReceiveManager")
public class PdaReceiveManagerImpl extends BaseManagerImpl implements PdaReceiveManager {

    /**
     * 
     */
    private static final long serialVersionUID = -5426064748783352202L;
    @Resource(name = "mongoTemplate")
    private MongoOperations mongoOperation;
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private StaLineDao staLineDao;
    @Autowired
    private WarehouseDao warehouseDao;
    @Autowired
    private WhContainerDao whContainerDao;
    @Autowired
    private SkuBarcodeDao skuBarcodeDao;
    @Autowired
    private SkuDao skuDao;
    @Autowired
    private InventoryStatusDao inventoryStatusDao;
    @Autowired
    private StaCartonDao staCartonDao;
    @Autowired
    private StaCartonLineDao staCartonLineDao;
    @Autowired
    private StaCartonLineSnDao staCartonLineSnDao;
    @Autowired
    private PdaStaShelvesPlanDao pdaStaShelvesPlanDao;
    @Autowired
    private WarehouseLocationDao warehouseLocationDao;
    @Autowired
    private MsgToWcsDao msgToWcsDao;
    @Autowired
    private StaOpDetailDao staOpDetailDao;
    @Autowired
    private StaOpLogDao staOpLogDao;
    @Autowired
    private BiChannelDao biChannelDao;
    @Autowired
    private SkuImperfectDao skuImperfectDao;
    @Autowired
    private BiChannelImperfectDao biChannelImperfectDao;
    @Autowired
    private BiChannelImperfectLineDao biChannelImperfectLineDao;
    @Autowired
    private SequenceManager sequenceManager;
    @Autowired
    private SkuSnDao skuSnDao;
    @Autowired
    private ChannelWhRefRefDao channelWhRefRefDao;
    @Autowired
    private PackageInfoDao packageInfoDao;
    @Autowired
    private AdvanceOrderAddInfoDao advanceOrderAddInfoDao;
    @Autowired
    private RecieverInfoDao recieverInfoDao;
    @Autowired
    private PdaHandOverDao pdaHandOverDao;
    @Autowired
    private PdaHandOverCurrencyDao pdaHandOverCurrencyDao;
    @Autowired
    private PdaHandOverLogDao pdaHandOverLogDao;
    @Autowired
    private WareHouseManagerExecute wareHouseManagerExecute;
    @Autowired
    private WareHouseManager wareHouseManager;
    @Autowired
    private WmsOrderStatusOmsDao wmsOrderStatusOmsDao;
    @Autowired
    private WmsIntransitNoticeOmsDao wmsIntransitNoticeOmsDao;
    @Autowired
    private StaDeliveryInfoDao staDeliveryInfoDao;
    @Autowired
    private ChooseOptionDao chooseOptionDao;
    @Autowired
    private AdCheckManager adCheckManager;
    @Autowired
    private InventoryDao inventoryDao;
    @Autowired
    private OperationUnitDao ouDao;
    @Autowired
    private StockTransTxLogDao stockTransTxLogDao;
    @Autowired
    private TransactionTypeDao transactionTypeDao;
    @Autowired
    private PdaTransitInnerLogDao pdaTransitInnerLogDao;
    @Autowired
    private WmsTransInfoOmsDao wmsTransInfoOmsDao;
    @Autowired
    private BaseQueryThreadPoolManager th;
    @Autowired
    private CustomsDeclarationDao customsDeclarationDao;



    @Override
    public String pdaReceiveByBox(String staCode, User user, OperationUnit op, String tag, String inventoryStatus) {
        StockTransApplication sta = staDao.findStaBySlipCodeOrCode(staCode, op.getId(), new BeanPropertyRowMapperExt<StockTransApplication>(StockTransApplication.class));
        if (null == sta) {
            List<ChannelWhRef> cwrList = channelWhRefRefDao.getChannelRefByOuid(op.getId());
            if (cwrList != null) {
                for (ChannelWhRef cwr : cwrList) {
                    if (cwr.getReceivePrefix() != null && !"".equals(cwr.getReceivePrefix())) {
                        sta = staDao.findStaBySlipCodeOrCode(cwr.getReceivePrefix() + staCode, op.getId(), new BeanPropertyRowMapperExt<StockTransApplication>(StockTransApplication.class));
                        if (sta != null) {
                            staCode = cwr.getReceivePrefix() + staCode;
                            break;
                        }
                    }
                }
            }
        }
        if (null != sta) {
            List<StaCarton> staCartonList = staCartonDao.findStaCartonByStaId(sta.getId(), op.getId(), new BeanPropertyRowMapperExt<StaCarton>(StaCarton.class));
            if (null != staCartonList && staCartonList.size() > 0) {
                List<Long> cidList = new ArrayList<Long>();
                for (StaCarton staCartonlist : staCartonList) {
                    cidList.add(staCartonlist.getId());

                }
                StaCartonLine lineList = staCartonLineDao.findStaCartonLineByCid(cidList, new BeanPropertyRowMapper<StaCartonLine>(StaCartonLine.class));
                StaLineCommand staLine = staLineDao.findLineQtyListByStaId(sta.getId(), new BeanPropertyRowMapper<StaLineCommand>(StaLineCommand.class));
                if (null != staLine.getQuantity() && null != lineList.getQty() && staLine.getQuantity() <= lineList.getQty()) {
                    return "此作业单已收货完毕";
                }
            }

            // 检验是否缺失体积或重量
            String msg = verifySkuThreeDimensional(sta, null, op.getId(), true);
            if (!StringUtil.isEmpty(msg)) {
                return "threeDimensional";
            }

            BiChannel channel = biChannelDao.getByCode(sta.getOwner());
            if (AsnOrderType.TYPETWO.equals(channel.getAsnOrderType())) { // 按箱收货

                pdaReceiveByBoxToDetail(sta.getId(), user, op, inventoryStatus);
                saveCartonAndLine(staCode, op.getId(), inventoryStatus);

                if (tag == null || "".equals(tag)) {} else {
                    checkTag(staCode, tag, op);
                }
                return "success";

            } else {
                // zi作业单不验证
                Warehouse warehouse = warehouseDao.getByOuId(op.getId());
                StockTransApplication sta2 = staDao.getByPrimaryKey(sta.getId());
                if (sta2.getGroupSta() == null) {
                    // 看仓库上的配置SKU数量是不是满足
                    // warehouse = warehouseDao.getByOuId(op.getId());
                    if (null == warehouse.getSkuNum() || null == warehouse.getSkuTotal()) {
                        return "none";
                    }
                }
                List<StaLineCommand> staLineList = staLineDao.findStaLineByStaId(sta.getId(), new BeanPropertyRowMapper<StaLineCommand>(StaLineCommand.class));
                StaLineCommand staLineCommand = staLineDao.findStaLineSkuByStaId(sta.getId(), new BeanPropertyRowMapper<StaLineCommand>(StaLineCommand.class));
                if (null != staLineCommand & null != staLineCommand.getReceiptQty()) {
                    if (sta2.getGroupSta() == null) {// 主作业单
                        if (staLineCommand.getReceiptQty() <= warehouse.getSkuTotal() && staLineList.size() <= warehouse.getSkuNum()) {
                            pdaReceiveByBoxToDetail(sta.getId(), user, op, inventoryStatus);
                            saveCartonAndLine(staCode, op.getId(), inventoryStatus);
                            if (tag == null || "".equals(tag)) {} else {
                                checkTag(staCode, tag, op);
                            }
                            return "success";
                        } else {
                            return "none2";
                        }
                    } else {// 子作业单
                        pdaReceiveByBoxToDetail(sta.getId(), user, op, inventoryStatus);
                        saveCartonAndLine(staCode, op.getId(), inventoryStatus);
                        if (tag == null || "".equals(tag)) {} else {
                            checkTag(staCode, tag, op);
                        }
                        return "success";
                    }
                }
            }
        } else {
            return "false";
        }
        return "error2";
    }

    public void pdaReceiveByBoxToDetail(Long staId, User user, OperationUnit op, String inventoryStatus) {
        List<StaCarton> staCartonList = staCartonDao.findStaCartonByStaId(staId, op.getId(), new BeanPropertyRowMapperExt<StaCarton>(StaCarton.class));
        List<StaLineCommand> StaLineList = staLineDao.findStaLineByStaId(staId, new BeanPropertyRowMapper<StaLineCommand>(StaLineCommand.class));
        StockTransApplication sta = staDao.getByPrimaryKey(staId);
        // System.out.println(sta.getId());
        for (StaLineCommand list : StaLineList) {
            StaOpDetail staOpDetail = new StaOpDetail();
            staOpDetail.setCreateTime(new Date());
            staOpDetail.setUserId(user.getId());
            staOpDetail.setStaId(staId);
            if (null != sta.getRefSlipCode() && !"".equals(sta.getRefSlipCode())) {
                staOpDetail.setCartonCode(sta.getRefSlipCode());
                sta.setContainerCode(sta.getRefSlipCode());
                staDao.save(sta);
            } else if (null != sta.getContainerCode() && !"".equals(sta.getContainerCode())) {
                staOpDetail.setCartonCode(sta.getContainerCode());
            } else {
                String que = sequenceManager.getAutoInboundBoxCode();
                staOpDetail.setCartonCode(que);
                sta.setContainerCode(que);
                staDao.save(sta);
                staDao.flush();
            }
            if (null != staCartonList && staCartonList.size() > 0) {
                List<Long> cidList = new ArrayList<Long>();
                for (StaCarton staCartonlist : staCartonList) {
                    cidList.add(staCartonlist.getId());

                }
                staOpDetail.setSkuId(list.getSkuId());
                StaCartonLine lineList = staCartonLineDao.findStaCartonLineBySkuId(cidList, list.getSkuId(), new BeanPropertyRowMapper<StaCartonLine>(StaCartonLine.class));
                staOpDetail.setQty(list.getQuantity() - lineList.getQty());
            } else {
                staOpDetail.setSkuId(list.getSkuId());
                staOpDetail.setQty(list.getQuantity());
            }

            // List<StockTransApplication> staList =
            // staDao.getChildStaByGroupId(staId);
            // Long qty = 0L;
            /*
             * // 主作业单 if (null != staList && staList.size() > 0) { for (StockTransApplication sta1
             * : staList) { StaCartonLine staCartonLine =
             * staCartonLineDao.findQtyByStaId(sta1.getId(), list.getSkuId(), new
             * BeanPropertyRowMapperExt<StaCartonLine>(StaCartonLine.class)); if (null !=
             * staCartonLine && null != staCartonLine.getQty() &&
             * !"".equals(staCartonLine.getQty())) { qty = qty + staCartonLine.getQty(); }
             * 
             * } staOpDetail.setQty(list.getQuantity() - qty); }
             */
            staOpDetail.setType(1);
            staOpDetail.setWhOuId(op.getId());
            InventoryStatus status = inventoryStatusDao.findByNameAndOu(inventoryStatus, op.getId());
            staOpDetail.setInvStatusId(status.getId());
            staOpDetailDao.save(staOpDetail);
            staOpDetailDao.flush();
        }
    }

    @Override
    public String initMongodbWhReceiveInfo(String staCode, OperationUnit op) {
        StockTransApplication sta = staDao.findStaBySlipCodeOrCode(staCode, op.getId(), new BeanPropertyRowMapperExt<StockTransApplication>(StockTransApplication.class));
        Long qty = 0L;
        if (null == sta) {
            List<ChannelWhRef> cwrList = channelWhRefRefDao.getChannelRefByOuid(op.getId());
            if (cwrList != null) {
                for (ChannelWhRef cwr : cwrList) {
                    if (cwr.getReceivePrefix() != null && !"".equals(cwr.getReceivePrefix())) {
                        sta = staDao.findStaBySlipCodeOrCode(cwr.getReceivePrefix() + staCode, op.getId(), new BeanPropertyRowMapperExt<StockTransApplication>(StockTransApplication.class));
                        if (sta != null) {
                            staCode = cwr.getReceivePrefix() + staCode;
                            break;
                        }
                    }
                }
            }
        }
        if (sta == null) {
            throw new BusinessException(ErrorCode.PDA_CODE_NOT_FOUND);
        }
        // 校验此单商品是否已维护体积和重量
        verifySkuThreeDimensionalBySta(sta, sta.getCode(), op.getId());
        
        List<AsnReceive> list = mongoOperation.find(new Query(Criteria.where("code").is(staCode)).with(new Sort(Direction.ASC, "createTime")), AsnReceive.class);
        if (null == list || list.size() == 0) {
            List<StockTransApplication> stalist = staDao.getChildStaByGroupId(sta.getId());
            // 主作业单
            if (null != stalist && stalist.size() > 0) {
                Long q = 0L;
                StaLineCommand staLine = staLineDao.findLineQtyListByStaId(sta.getId(), new BeanPropertyRowMapper<StaLineCommand>(StaLineCommand.class));

                for (StockTransApplication sta1 : stalist) {
                    StaCartonLine staCartonLine = staCartonLineDao.findQtyByStaId(sta1.getId(), null, new BeanPropertyRowMapperExt<StaCartonLine>(StaCartonLine.class));
                    // StaLineCommand staLine =
                    // staLineDao.findLineQtyListByStaId(sta1.getId(), new
                    // BeanPropertyRowMapper<StaLineCommand>(StaLineCommand.class));
                    if (null != staCartonLine && null != staCartonLine.getQty()) {
                        qty = qty + staCartonLine.getQty();
                    }
                }
                List<StaCarton> staCartonList = staCartonDao.findStaCartonByStaId(sta.getId(), op.getId(), new BeanPropertyRowMapperExt<StaCarton>(StaCarton.class));
                if (null != staCartonList && staCartonList.size() > 0) {
                    List<Long> cidList = new ArrayList<Long>();
                    for (StaCarton staCartonlist : staCartonList) {
                        cidList.add(staCartonlist.getId());
                    }
                    StaCartonLine lineList = staCartonLineDao.findStaCartonLineByCid(cidList, new BeanPropertyRowMapper<StaCartonLine>(StaCartonLine.class));
                    q = staLine.getQuantity() - qty - lineList.getQty();
                } else {
                    q = staLine.getQuantity() - qty;
                }

                if (q > 0) {} else {
                    return "此作业单已收货完毕";
                }
            } else {
                StaCartonLine lineList = null;
                List<StaCarton> staCartonList = staCartonDao.findStaCartonByStaId(sta.getId(), op.getId(), new BeanPropertyRowMapperExt<StaCarton>(StaCarton.class));
                if (null != staCartonList && staCartonList.size() > 0) {
                    List<Long> cidList = new ArrayList<Long>();
                    for (StaCarton staCartonlist : staCartonList) {
                        cidList.add(staCartonlist.getId());
                    }
                    lineList = staCartonLineDao.findStaCartonLineByCid(cidList, new BeanPropertyRowMapper<StaCartonLine>(StaCartonLine.class));
                    StaLineCommand staLine = staLineDao.findLineQtyListByStaId(sta.getId(), new BeanPropertyRowMapper<StaLineCommand>(StaLineCommand.class));
                    if (null != staLine.getQuantity() && null != lineList.getQty() && staLine.getQuantity() <= lineList.getQty()) {
                        return "此作业单已收货完毕";
                    }
                }

            }
            List<StaLine> staLine = staLineDao.findByStaId(sta.getId());
            AsnReceive asnReceive = new AsnReceive();
            asnReceive.setId(sta.getId());
            asnReceive.setCode(sta.getCode());
            asnReceive.setSlipCode(sta.getRefSlipCode());
            asnReceive.setCreateTime(new Date());
            List<OrderLine> OrderLineList = new ArrayList<OrderLine>();
            for (StaLine line : staLine) {
                StaCartonLine staCartonLine = staCartonLineDao.findStaQtyByCid(sta.getId(), line.getSku().getId(), new BeanPropertyRowMapper<StaCartonLine>(StaCartonLine.class));
                if (null != staCartonLine && null != staCartonLine.getQty() && (line.getQuantity() - staCartonLine.getQty() > 0)) {

                    OrderLine orderLine = new OrderLine();
                    Set<String> skubarcode = new HashSet<String>();
                    orderLine.setSkuId(line.getSku().getId());
                    skubarcode.add(line.getSku().getBarCode());
                    List<SkuBarcode> sbs = skuBarcodeDao.findBySkuId(line.getSku().getId());
                    orderLine.setFalg(0L);
                    if (sbs != null) {
                        for (SkuBarcode sb : sbs) {
                            skubarcode.add(sb.getBarcode());
                        }
                    }
                    orderLine.setSkubarcode(skubarcode);
                    orderLine.setPlanQty(line.getQuantity());
                    orderLine.setRestQty(line.getQuantity() - staCartonLine.getQty());
                    // 主作业单加个逻辑
                    List<StockTransApplication> staList = staDao.getChildStaByGroupId(sta.getId());
                    // 主作业单
                    if (null != staList && staList.size() > 0) {
                        /*
                         * Long qty = 0L; for (StockTransApplication staList1 : staList) {
                         * StaCartonLine staCartonLine1 =
                         * staCartonLineDao.findStaQtyByCid(staList1.getId(), line.getSku().getId(),
                         * new BeanPropertyRowMapper<StaCartonLine >(StaCartonLine.class)); if (null
                         * != staCartonLine1 && null != staCartonLine1.getQty() &&
                         * !"".equals(staCartonLine1.getQty())) { qty = qty +
                         * staCartonLine1.getQty(); } }
                         */
                        orderLine.setRestQty(line.getQuantity() - qty);
                    }
                    OrderLineList.add(orderLine);
                    asnReceive.setOrderLine(OrderLineList);
                    mongoOperation.save(asnReceive);

                } else {
                    OrderLine orderLine = new OrderLine();
                    Set<String> skubarcode = new HashSet<String>();
                    orderLine.setSkuId(line.getSku().getId());
                    skubarcode.add(line.getSku().getBarCode());
                    List<SkuBarcode> sbs = skuBarcodeDao.findBySkuId(line.getSku().getId());
                    orderLine.setFalg(0L);
                    if (sbs != null) {
                        for (SkuBarcode sb : sbs) {
                            skubarcode.add(sb.getBarcode());
                        }
                    }
                    orderLine.setSkubarcode(skubarcode);
                    orderLine.setPlanQty(line.getQuantity());
                    orderLine.setRestQty(line.getQuantity() - line.getCompleteQuantity());
                    // 主作业单加个逻辑
                    List<StockTransApplication> staList = staDao.getChildStaByGroupId(sta.getId());
                    // 主作业单
                    if (null != staList && staList.size() > 0) {

                        for (StockTransApplication staList1 : staList) {
                            StaCartonLine staCartonLine1 = staCartonLineDao.findStaQtyByCid(staList1.getId(), line.getSku().getId(), new BeanPropertyRowMapper<StaCartonLine>(StaCartonLine.class));
                            if (null != staCartonLine1 && null != staCartonLine1.getQty() && !"".equals(staCartonLine1.getQty())) {
                                qty = qty + staCartonLine1.getQty();
                            }
                        }
                        orderLine.setRestQty(line.getQuantity() - qty);
                    }
                    OrderLineList.add(orderLine);
                    asnReceive.setOrderLine(OrderLineList);
                    mongoOperation.save(asnReceive);
                }

                /*
                 * if (null != lineList) { Long qty = staCartonLineDao.findStaQtyByCid(sta.getId(),
                 * line.getSku().getId(), new
                 * BeanPropertyRowMapper<StaCartonLine>(StaCartonLine.class)); if (null != qty) {
                 * orderLine.setRestQty(line.getQuantity() - line.getCompleteQuantity() - qty); } }
                 * else { orderLine.setRestQty(line.getQuantity() - line.getCompleteQuantity()); }
                 */

            }

        }
        return "success";
    }

    @Override
    public String initMongodbWhcartonBox(String cartonBox, String staCode, Long ouId) {
        Warehouse warehouse = warehouseDao.getByOuId(ouId);
        StockTransApplication sta = staDao.getByCode(staCode);
        WhContainer whContainer = whContainerDao.getWhContainerByCode(cartonBox);
        StaCarton st = staCartonDao.getStaCartonByCode1(cartonBox, ouId, new BeanPropertyRowMapperExt<StaCarton>(StaCarton.class));
        if (null == st) {
            List<ChannelWhRef> cwrList = channelWhRefRefDao.getChannelRefByOuid(ouId);
            if (cwrList != null) {
                for (ChannelWhRef cwr : cwrList) {
                    if (cwr.getReceivePrefix() != null && !"".equals(cwr.getReceivePrefix())) {
                        st = staCartonDao.getStaCartonByCode1(cwr.getReceivePrefix() + cartonBox, ouId, new BeanPropertyRowMapperExt<StaCarton>(StaCarton.class));
                        if (st != null) {
                            cartonBox = cwr.getReceivePrefix() + cartonBox;
                            break;
                        }
                    }
                }
            }
        }
        if (null == warehouse.getIsAutoWh() || !warehouse.getIsAutoWh()) { // 非自动化仓
            if (null == whContainer) { // 校验容器是否可用
                return "容器不存在";
            } else if (DefaultStatus.EXECUTING.equals(whContainer.getStatus()) && null != st) {
                return "容器已被占用";
            } else if (!DefaultStatus.CREATED.equals(whContainer.getStatus()) && !whContainer.getStaId().equals(sta.getId())) {
                return "容器已被占用";
            } else {
                WhContainer wh = whContainerDao.getByPrimaryKey(whContainer.getId());
                wh.setStatus(DefaultStatus.EXECUTING);
                wh.setStaId(sta.getId());
                whContainerDao.save(wh);
                return "success";
            }
        } else if (null != warehouse.getIsAutoWh() && warehouse.getIsAutoWh()) { // 自动化仓
            if (null != whContainer && null != sta.getContainerCode()) {
                if (DefaultStatus.EXECUTING.equals(whContainer.getStatus()) && null != st) {
                    return "容器已被占用";
                } else if (DefaultStatus.CREATED.equals(whContainer.getStatus())) {
                    WhContainer wh = whContainerDao.getByPrimaryKey(whContainer.getId());
                    wh.setStatus(DefaultStatus.EXECUTING);
                    wh.setStaId(sta.getId());
                    whContainerDao.save(wh);
                    return "success";
                } else if (!DefaultStatus.CREATED.equals(whContainer.getStatus()) && sta.getContainerCode().equals(cartonBox)) {
                    return "success";
                } else if (!DefaultStatus.CREATED.equals(whContainer.getStatus()) && !sta.getContainerCode().equals(cartonBox)) {
                    if (whContainer.getStaId().equals(sta.getId())) {
                        return "success";
                    } else {
                        return "容器已被占用";
                    }
                }
            } else if (null == whContainer && null != sta.getContainerCode()) {
                if (sta.getContainerCode().equals(cartonBox) || sta.getRefSlipCode().equals(cartonBox)) {
                    return "success";
                } else {
                    return "容器不是系统自动生成";
                }
            } else if (null == whContainer && null == sta.getContainerCode()) {
                return "容器不是系统自动生成";
            } else if (null != whContainer && null == sta.getContainerCode()) {
                if (DefaultStatus.EXECUTING.equals(whContainer.getStatus()) && null != st) {
                    return "容器已被占用";
                } else if (!DefaultStatus.CREATED.equals(whContainer.getStatus())) {
                    if (whContainer.getStaId().equals(sta.getId())) {
                        return "success";
                    } else {
                        return "容器已被占用";
                    }
                } else {
                    WhContainer wh = whContainerDao.getByPrimaryKey(whContainer.getId());
                    wh.setStatus(DefaultStatus.EXECUTING);
                    wh.setStaId(sta.getId());
                    whContainerDao.save(wh);
                    return "success";
                }
            }
        }
        return "";
    }

    @Override
    public Boolean verifySku(String staCode, String barCode, Long qty) {
        Boolean msg = false;
        List<AsnReceive> asnReceiveList = mongoOperation.find(new Query(Criteria.where("code").is(staCode)).with(new Sort(Direction.ASC, "createTime")), AsnReceive.class);
        if (null == asnReceiveList || asnReceiveList.size() == 0) {
            throw new BusinessException(ErrorCode.PDA_CODE_NOT_FOUND);
        }
        if (null == barCode || "".equals(barCode)) {
            throw new BusinessException(ErrorCode.PDA_CODE_NOT_FOUND);
        }
        List<OrderLine> orderLineList = new ArrayList<OrderLine>();
        Set<String> barCodeset = new HashSet<String>();
        for (AsnReceive asnReceive : asnReceiveList) {
            orderLineList = asnReceive.getOrderLine();
            for (OrderLine orderLine : orderLineList) {
                barCodeset = orderLine.getSkubarcode();
                for (String s : barCodeset) {
                    if (s.equals(barCode) && null != orderLine.getRestQty() && orderLine.getRestQty() - qty >= 0L) {
                        msg = true;
                        break;
                    }
                }
            }
        }
        return msg;
    }

    @Override
    public Sku findPDASkuByBarCode(String staCode, String barCode) {
        Sku sku = new Sku();
        List<AsnReceive> asnReceiveList = mongoOperation.find(new Query(Criteria.where("code").is(staCode)).with(new Sort(Direction.ASC, "createTime")), AsnReceive.class);
        if (null == asnReceiveList || asnReceiveList.size() == 0) {
            throw new BusinessException(ErrorCode.PDA_CODE_NOT_FOUND);
        }
        if (null == barCode || "".equals(barCode)) {
            throw new BusinessException(ErrorCode.PDA_CODE_NOT_FOUND);
        }
        List<OrderLine> orderLineList = new ArrayList<OrderLine>();
        Set<String> barCodeset = new HashSet<String>();
        for (AsnReceive asnReceive : asnReceiveList) {
            orderLineList = asnReceive.getOrderLine();
            for (OrderLine orderLine : orderLineList) {
                barCodeset = orderLine.getSkubarcode();
                for (String s : barCodeset) {
                    if (s.equals(barCode)) {
                        sku = skuDao.getByPrimaryKey(orderLine.getSkuId());
                        break;
                    }
                }

            }
        }
        return sku;
    }

    @Override
    public void updateMogodbRestQty(String barCode, Long qty, String staCode) {
        AsnReceive asnReceive = mongoOperation.findOne(new Query(Criteria.where("code").is(staCode)).with(new Sort(Direction.ASC, "createTime")), AsnReceive.class);
        List<OrderLine> OrderLineList = asnReceive.getOrderLine();
        for (OrderLine list : OrderLineList) {
            Set<String> set = list.getSkubarcode();
            for (String s : set) {
                if (s.equals(barCode)) {
                    long num = list.getRestQty() - qty;
                    if (num < 0) {
                        throw new BusinessException();
                    }
                    list.setRestQty(num);
                    list.setFalg(qty + list.getFalg());
                    break;
                }
            }
        }
        mongoOperation.save(asnReceive);
    }

    @Override
    public Boolean findMongodbByRestQty(String staCode) {
        Boolean flag = false;
        AsnReceive asnReceive = mongoOperation.findOne(new Query(Criteria.where("code").is(staCode)), AsnReceive.class);
        if (null != asnReceive) {
            List<OrderLine> OrderLineList = asnReceive.getOrderLine();
            for (OrderLine list : OrderLineList) {
                if (list.getRestQty() != 0) {
                    flag = true;
                    break;
                }
            }
        }
        return flag;
    }

    @Override
    public Boolean findMongodbByRestQtyAndBarCode(String staCode, String barCode) {
        Boolean flag = false;
        AsnReceive asnReceive = mongoOperation.findOne(new Query(Criteria.where("code").is(staCode)), AsnReceive.class);
        if (null != asnReceive) {
            List<OrderLine> OrderLineList = asnReceive.getOrderLine();
            for (OrderLine list : OrderLineList) {
                Set<String> set = list.getSkubarcode();
                for (String s : set) {
                    if (s.contains(barCode) && list.getRestQty() != 0) {
                        flag = true;
                        break;
                    }
                }
            }
        }
        return flag;
    }

    @Override
    public void asnOver(String staCode, String cartonBox, String inventoryStatus, Long ouId, String dataList, String quantitativeModel, User user, OperationUnit op) throws ParseException {
        if (null != dataList && !"".equals(dataList) && "null" != dataList && !"null".equals(dataList) && "undefined" != dataList && !"undefined".equals(dataList)) {
            JSONArray jsonArray = JSONArray.fromObject(dataList); // 生成残次品标签
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                Long skuId = Long.parseLong(jsonObject.getString("skuId"));
                // String skubarcode = jsonObject.getString("skubarcode");
                if (jsonObject.containsKey("typeAndsnList")) {
                    JSONArray jsonSnArray = (JSONArray) jsonObject.get("typeAndsnList");
                    for (int j = 0; j < jsonSnArray.size(); j++) {
                        JSONObject jsonsn = (JSONObject) jsonSnArray.get(j);
                        if (jsonsn.containsKey("sn")) {
                            JSONArray jsonArraySn = (JSONArray) jsonsn.get("sn");
                            for (int k = 0; k < jsonArraySn.size(); k++) {
                                StaOpDetail staOpDetail = new StaOpDetail();
                                String json = (String) jsonArraySn.get(k);
                                staOpDetail.setSn(json);
                                if (jsonsn.containsKey("unType") && null != jsonsn.getString("unType") && !"".equals(jsonsn.getString("unType"))) {
                                    String type = jsonsn.getString("unType");
                                    String reason = jsonsn.getString("unReasonList");
                                    staOpDetail.setDmgType(type);
                                    staOpDetail.setDmgReason(reason);
                                    String dmgCode = insertSkuImperfect(staCode, type, reason, skuId, 1, op, json);
                                    staOpDetail.setDmgCode(dmgCode);
                                }
                                staOpDetail.setCreateTime(new Date());
                                staOpDetail.setUserId(user.getId());
                                StockTransApplication sta = staDao.getByCode(staCode);
                                staOpDetail.setStaId(sta.getId());
                                staOpDetail.setCartonCode(cartonBox);
                                staOpDetail.setSkuId(skuId);
                                staOpDetail.setType(1);
                                staOpDetail.setWhOuId(ouId);
                                staOpDetail.setQty(1L);
                                if ("良品".equals(inventoryStatus)) {
                                    InventoryStatus status = inventoryStatusDao.findByNameAndOu("良品", ouId);
                                    staOpDetail.setInvStatusId(status.getId());
                                } else {
                                    InventoryStatus invStatus = inventoryStatusDao.findByNameAndOu("残次品", ouId);
                                    staOpDetail.setInvStatusId(invStatus.getId());
                                }
                                if (jsonObject.containsKey("expirationDate") && null != jsonObject.getString("expirationDate") && !"".equals(jsonObject.getString("expirationDate"))) {
                                    String expirationDate = (String) jsonObject.get("expirationDate");
                                    staOpDetail.setExpDate(sdf.parse(expirationDate));
                                } else {
                                    if (jsonObject.containsKey("productionDate") && null != jsonObject.getString("productionDate") && !"".equals(jsonObject.getString("productionDate"))) {
                                        Sku sku = skuDao.getByPrimaryKey(skuId);
                                        Date productionDate = sdf.parse((String) jsonObject.get("productionDate"));
                                        if (null != sku && null != sku.getValidDate()) {
                                            Integer date = sku.getValidDate();
                                            staOpDetail.setExpDate(getNDay(productionDate, date));
                                        }
                                    }
                                }
                                staOpDetailDao.save(staOpDetail);
                            }
                        } else if (jsonsn.containsKey("unType") && !jsonsn.containsKey("sn")) {
                            Long num = jsonsn.getLong("damageNum");
                            for (int k = 0; k < num; k++) {
                                StaOpDetail staOpDetail = new StaOpDetail();
                                String type = jsonsn.getString("unType");
                                String reason = jsonsn.getString("unReasonList");
                                staOpDetail.setDmgType(type);
                                staOpDetail.setDmgReason(reason);
                                String dmgCode = insertSkuImperfect(staCode, type, reason, skuId, 1, op, null);
                                staOpDetail.setDmgCode(dmgCode);
                                staOpDetail.setCreateTime(new Date());
                                staOpDetail.setUserId(user.getId());
                                StockTransApplication sta = staDao.getByCode(staCode);
                                staOpDetail.setStaId(sta.getId());
                                staOpDetail.setCartonCode(cartonBox);
                                staOpDetail.setSkuId(skuId);
                                staOpDetail.setType(1);
                                staOpDetail.setQty(1L);
                                staOpDetail.setWhOuId(ouId);
                                if ("良品".equals(inventoryStatus)) {
                                    InventoryStatus status = inventoryStatusDao.findByNameAndOu("良品", ouId);
                                    staOpDetail.setInvStatusId(status.getId());
                                } else {
                                    InventoryStatus invStatus = inventoryStatusDao.findByNameAndOu("残次品", ouId);
                                    staOpDetail.setInvStatusId(invStatus.getId());
                                }
                                if (jsonObject.containsKey("expirationDate") && null != jsonObject.getString("expirationDate") && !"".equals(jsonObject.getString("expirationDate"))) {
                                    String expirationDate = (String) jsonObject.get("expirationDate");
                                    staOpDetail.setExpDate(sdf.parse(expirationDate));
                                } else {
                                    if (jsonObject.containsKey("productionDate") && null != jsonObject.getString("productionDate") && !"".equals(jsonObject.getString("productionDate"))) {
                                        Sku sku = skuDao.getByPrimaryKey(skuId);
                                        Date productionDate = sdf.parse((String) jsonObject.get("productionDate"));
                                        if (null != sku && null != sku.getValidDate()) {
                                            Integer date = sku.getValidDate();
                                            staOpDetail.setExpDate(getNDay(productionDate, date));
                                        }
                                    }
                                }
                                staOpDetailDao.save(staOpDetail);
                            }

                        }

                    }
                } else {
                    StaOpDetail staOpDetail = new StaOpDetail();
                    staOpDetail.setCreateTime(new Date());
                    staOpDetail.setUserId(user.getId());
                    StockTransApplication sta = staDao.getByCode(staCode);
                    staOpDetail.setStaId(sta.getId());
                    staOpDetail.setCartonCode(cartonBox);
                    staOpDetail.setSkuId(skuId);
                    staOpDetail.setType(1);
                    staOpDetail.setQty(jsonObject.getLong("skuNum"));
                    staOpDetail.setWhOuId(ouId);
                    if ("良品".equals(inventoryStatus)) {
                        InventoryStatus status = inventoryStatusDao.findByNameAndOu("良品", ouId);
                        staOpDetail.setInvStatusId(status.getId());
                    } else {
                        InventoryStatus invStatus = inventoryStatusDao.findByNameAndOu("残次品", ouId);
                        staOpDetail.setInvStatusId(invStatus.getId());
                    }
                    if (jsonObject.containsKey("expirationDate") && null != jsonObject.getString("expirationDate") && !"".equals(jsonObject.getString("expirationDate"))) {
                        String expirationDate = (String) jsonObject.get("expirationDate");
                        staOpDetail.setExpDate(sdf.parse(expirationDate));
                    } else {
                        if (jsonObject.containsKey("productionDate") && null != jsonObject.getString("productionDate") && !"".equals(jsonObject.getString("productionDate"))) {
                            Sku sku = skuDao.getByPrimaryKey(skuId);
                            Date productionDate = sdf.parse((String) jsonObject.get("productionDate"));
                            if (null != sku && null != sku.getValidDate()) {
                                Integer date = sku.getValidDate();
                                staOpDetail.setExpDate(getNDay(productionDate, date));
                            }
                        }
                    }
                    staOpDetailDao.save(staOpDetail);
                }
            }
        }

    }

    @Override
    public Long mongodbfindQtyByCode(String staCode, String barCode) {
        Long msg = 0L;
        AsnReceive asnReceive = mongoOperation.findOne(new Query(Criteria.where("code").is(staCode)), AsnReceive.class);
        if (null != asnReceive && null != asnReceive.getOrderLine() && asnReceive.getOrderLine().size() > 0) {
            List<OrderLine> OrderLineList = asnReceive.getOrderLine();
            for (OrderLine list : OrderLineList) {
                if (list.getSkubarcode().contains(barCode)) {
                    msg = list.getFalg();
                    break;
                }
            }
        }
        return msg;
    }

    @Override
    public void mongodbDeleteByStaCode(String staCode) {
        Boolean flag = true;
        AsnReceive asnReceive = mongoOperation.findOne(new Query(Criteria.where("code").is(staCode)), AsnReceive.class);
        if (null != asnReceive) {
            List<OrderLine> orderLineList = asnReceive.getOrderLine();
            for (int i = 0; i < orderLineList.size(); i++) {
                if (0 != orderLineList.get(i).getRestQty()) {
                    flag = false;
                    break;
                }
            }
        }
        if (flag) {
            mongoOperation.remove(new Query(Criteria.where("code").is(staCode)), AsnReceive.class);
        } /*
           * else { List<OrderLine> orderLineList = asnReceive.getOrderLine(); for (OrderLine list :
           * orderLineList) { list.setFalg(0L); } mongoOperation.save(asnReceive); }
           */
    }

    public Date getNDay(Date date, Integer factor) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, factor);
        return calendar.getTime();
    }

    /**
     * 库位推荐规则By箱明细
     */
    public Long locationRecommendBySku(Long cartonLineId) {
        StaCartonLine c = staCartonLineDao.getByPrimaryKey(cartonLineId);
        Sku sku = skuDao.getByPrimaryKey(c.getSkuId());
        // List<StaCartonLineSn> sclList =
        // staCartonLineSnDao.getStaCartonLineSnByClId(cartonLineId);
        StaCarton box = staCartonDao.getByPrimaryKey(c.getStaCartonId().getId());
        StockTransApplication sta = staDao.getByPrimaryKey(box.getSta().getId());
        List<PdaStaShelvesPlan> sspList = new ArrayList<PdaStaShelvesPlan>();
        Long planQty = c.getQty();
        Long realityQty = 0L;
        boolean chaos = false;
        // 1.获取推荐的库位
        if (InboundStoreMode.TOGETHER.equals(sku.getStoremode())) {
            chaos = true;
        }

        Long lineMin = 0L;
        Long lineMax = 10L;
        while ((c.getQty() - realityQty) != 0L) {
            List<PdaStaShelvesPlanCommand> locationList =
                    pdaStaShelvesPlanDao.recommendLocation(sku.getCode(), sku.getSupplierCode(), sku.getId(), sku.getSkuType() == null ? null : sku.getSkuType().getId(), sta.getOwner(), box.getWhOuId(), chaos, lineMin, lineMax,
                            new BeanPropertyRowMapper<PdaStaShelvesPlanCommand>(PdaStaShelvesPlanCommand.class));
            if (locationList == null || locationList.size() == 0) {
                break;
            }
            realityQty = recommendLocation(c, sku, planQty, realityQty, sta, box, sspList, cartonLineId, locationList);
            lineMin = lineMax;
            lineMax += 10L;
        }

        // 3.保存推荐的信息
        if ((c.getQty() - realityQty) == 0L) {
            for (PdaStaShelvesPlan sp : sspList) {
                pdaStaShelvesPlanDao.save(sp);
            }
            pdaStaShelvesPlanDao.flush();
        }

        return c.getQty() - realityQty;
    }

    public Long recommendLocation(StaCartonLine c, Sku sku, Long planQty, Long realityQty, StockTransApplication sta, StaCarton box, List<PdaStaShelvesPlan> sspList, Long cartonLineId, List<PdaStaShelvesPlanCommand> locationList) {

        // 排序
        // List<PdaStaShelvesPlanCommand> sortList = new
        // ArrayList<PdaStaShelvesPlanCommand>();
        // List<PdaStaShelvesPlanCommand> sortList2 = new
        // ArrayList<PdaStaShelvesPlanCommand>();
        // List<PdaStaShelvesPlanCommand> sortList3 = new
        // ArrayList<PdaStaShelvesPlanCommand>();
        // for (PdaStaShelvesPlanCommand loc : locationList) {
        // if (loc.getOccupy() > 0) {
        // sortList.add(loc);
        // } else {
        // if (loc.getSort() == null) {
        // sortList3.add(loc);
        // } else {
        // sortList2.add(loc);
        // }
        // }
        // }
        //
        // if (sortList2 != null && sortList2.size() > 1) {
        //
        // Collections.sort(sortList2, new
        // Comparator<PdaStaShelvesPlanCommand>() {
        // public int compare(PdaStaShelvesPlanCommand arg0,
        // PdaStaShelvesPlanCommand arg1) {
        // return arg0.getSort().compareTo(arg1.getSort());
        // }
        // });
        // }
        // if (sortList3 != null && sortList3.size() > 1) {
        //
        // Collections.sort(sortList3, new
        // Comparator<PdaStaShelvesPlanCommand>() {
        // public int compare(PdaStaShelvesPlanCommand arg0,
        // PdaStaShelvesPlanCommand arg1) {
        // return arg0.getLocCode().compareTo(arg1.getLocCode());
        // }
        // });
        // }
        // sortList.addAll(sortList2);
        // sortList.addAll(sortList3);
        //
        // Collections.sort(sortList, new Comparator<PdaStaShelvesPlanCommand>()
        // {
        // public int compare(PdaStaShelvesPlanCommand arg0,
        // PdaStaShelvesPlanCommand arg1) {
        // return arg0.getLv().compareTo(arg1.getLv());
        // }
        // });

        // 2.给每个库位分配库存数量
        for (PdaStaShelvesPlanCommand loc : locationList) {
            PdaStaShelvesPlan ssp = new PdaStaShelvesPlan();
            sspList.add(ssp);
            WarehouseLocation location = warehouseLocationDao.getByPrimaryKey(loc.getLocId());
            ssp.setExpDate(c.getExpDate());
            ssp.setSku(sku);
            ssp.setStaCarton(box);
            ssp.setSta(sta);
            ssp.setLocation(location);
            ssp.setStatus(0L);
            ssp.setStaCartonLineId(cartonLineId);
            planQty = loc.getQty() - planQty;
            if (planQty >= 0) {
                ssp.setQty(loc.getQty() - planQty);
                realityQty = realityQty + (loc.getQty() - planQty);
                break;
            } else {
                planQty = 0 - planQty;
                realityQty = realityQty + loc.getQty();
                ssp.setQty(loc.getQty());
            }
        }

        return realityQty;
    }

    /**
     * 根据货箱推荐库位并返回没有推荐到库位的数量
     * 
     * @param cartonId
     * @return
     */
    public Map<String, Long> recommendLocationByCarton(Long cartonId) {
        Map<String, Long> map = new HashMap<String, Long>();
        List<StaCartonLine> cList = staCartonLineDao.getCartonLineByCartonId(cartonId);
        if (cList != null && cList.size() > 0) {
            for (StaCartonLine cl : cList) {
                Sku sku = skuDao.getByPrimaryKey(cl.getSkuId());
                Long qty = locationRecommendBySku(cl.getId());
                if (qty != null && qty != 0L) {
                    map.put(sku.getCode(), qty);
                }
            }
        }
        return map;
    }

    /**
     * 根据货箱获取弹出口
     * 
     * @param cartonId
     * @return
     */
    public Long sendMsgToWcs(Long cartonId) {
        StaCarton sc = staCartonDao.getByPrimaryKey(cartonId);
        StockTransApplication sta = staDao.getByPrimaryKey(sc.getSta().getId());
        BiChannel bc = biChannelDao.getByCode(sta.getOwner());
        ChannelWhRef cwr = channelWhRefRefDao.getChannelRef(sta.getMainWarehouse().getId(), bc.getId());

        String containerCode = sc.getCode();
        if (cwr.getReceivePrefix() != null && !"".equals(cwr.getReceivePrefix()) && containerCode.startsWith(cwr.getReceivePrefix())) {

            containerCode = containerCode.substring(cwr.getReceivePrefix().length());
        }

        MsgToWcs msg = new MsgToWcs();
        List<PopUpArea> puaList = pdaStaShelvesPlanDao.findPopUpAreaByCarton(cartonId, new BeanPropertyRowMapper<PopUpArea>(PopUpArea.class));
        if (puaList == null || puaList.size() == 0) {
            return null;
        }
        List<SShouRongQi> ssList = new ArrayList<SShouRongQi>();
        for (PopUpArea pop : puaList) {
            // 封装数据成json格式
            SShouRongQi ss = new SShouRongQi();
            ss.setContainerNO(containerCode); // 容器号
            ss.setDestinationNO(pop.getCode()); // 目的地位置
            ss.setSerialNumber(pop.getSort().toString());
            ssList.add(ss);
        }
        String context = JsonUtil.collection2jsonStr(ssList);
        // 保存数据到中间表
        msg.setContext(context);
        msg.setContainerCode(containerCode);
        msg.setCreateTime(new Date());
        msg.setErrorCount(0);
        msg.setStatus(true);
        msg.setType(WcsInterfaceType.SShouRongQi);
        msgToWcsDao.save(msg);
        msgToWcsDao.flush();

        return msg.getId();
    }

    @Override
    public void saveCartonAndLine(String staCode, Long ouId, String inventoryStatus) {
        StockTransApplication sta = staDao.findStaBySlipCodeOrCode(staCode, ouId, new BeanPropertyRowMapperExt<StockTransApplication>(StockTransApplication.class));
        if (null == sta) {
            List<ChannelWhRef> cwrList = channelWhRefRefDao.getChannelRefByOuid(ouId);
            if (cwrList != null) {
                for (ChannelWhRef cwr : cwrList) {
                    if (cwr.getReceivePrefix() != null && !"".equals(cwr.getReceivePrefix())) {
                        sta = staDao.findStaBySlipCodeOrCode(cwr.getReceivePrefix() + staCode, ouId, new BeanPropertyRowMapperExt<StockTransApplication>(StockTransApplication.class));
                        if (sta != null) {
                            staCode = cwr.getReceivePrefix() + staCode;
                            break;
                        }
                    }
                }
            }
        }
        if (null != sta) {
            List<StaOpDetailCommand> staOpDetailList = staOpDetailDao.findGoodsOpDetailByStaId(sta.getId(), ouId, new BeanPropertyRowMapperExt<StaOpDetailCommand>(StaOpDetailCommand.class));
            if (null != staOpDetailList && staOpDetailList.size() > 0) {
                for (StaOpDetailCommand list : staOpDetailList) {
                    StaCarton staCarton = new StaCarton();
                    staCarton.setSta(sta);
                    staCarton.setUserId(list.getUserId());
                    staCarton.setCode(list.getCartonCode());
                    staCarton.setCreateTime(new Date());
                    staCarton.setStatus(DefaultStatus.CREATED);
                    staCarton.setWhOuId(ouId);
                    if (null != inventoryStatus && !"".equals(inventoryStatus)) {
                        if ("良品".equals(inventoryStatus)) {
                            InventoryStatus status = inventoryStatusDao.findByNameAndOu("良品", ouId);
                            staCarton.setInvStatusId(status.getId());
                        } else {
                            InventoryStatus invStatus = inventoryStatusDao.findByNameAndOu("残次品", ouId);
                            staCarton.setInvStatusId(invStatus.getId());
                        }
                    } else {
                        InventoryStatus status = inventoryStatusDao.findByNameAndOu("良品", ouId);
                        staCarton.setInvStatusId(status.getId());
                    }
                    staCartonDao.save(staCarton);
                    staCartonDao.flush();
                    List<StaOpDetailCommand> staCartonLineList = staOpDetailDao.findGoodsOpDetailByStaIdAnd(sta.getId(), ouId, list.getCartonCode(), new BeanPropertyRowMapperExt<StaOpDetailCommand>(StaOpDetailCommand.class));
                    if (null != staCartonLineList && staCartonLineList.size() > 0) {
                        for (StaOpDetailCommand staCartonLinelist : staCartonLineList) {
                            StaCartonLine staCartonLine = new StaCartonLine();
                            staCartonLine.setStaCartonId(staCarton);
                            staCartonLine.setSkuId(staCartonLinelist.getSkuId());
                            staCartonLine.setQty(staCartonLinelist.getTotalNum());
                            staCartonLine.setExpDate(staCartonLinelist.getExpDate());
                            staCartonLineDao.save(staCartonLine);
                            staCartonLineDao.flush();
                            List<StaOpDetailCommand> staCartonLineSnList =
                                    staOpDetailDao
                                            .findSnListByStaIdAndSkuId(sta.getId(), ouId, list.getCartonCode(), staCartonLinelist.getSkuId(), staCartonLinelist.getExpDate(), new BeanPropertyRowMapperExt<StaOpDetailCommand>(StaOpDetailCommand.class));
                            if (null != staCartonLineSnList && staCartonLineSnList.size() > 0) {
                                for (StaOpDetailCommand staCartonLineSnlist : staCartonLineSnList) {
                                    if (null != staCartonLineSnlist.getSn() || null != staCartonLineSnlist.getDmgType() || null != staCartonLineSnlist.getDmgCode() || null != staCartonLineSnlist.getDmgReason()) {
                                        StaCartonLineSn staCartonLineSn = new StaCartonLineSn();
                                        staCartonLineSn.setSn(staCartonLineSnlist.getSn());
                                        staCartonLineSn.setDmgCode(staCartonLineSnlist.getDmgCode());
                                        staCartonLineSn.setDmgReason(staCartonLineSnlist.getDmgReason());
                                        staCartonLineSn.setDmgType(staCartonLineSnlist.getDmgType());
                                        staCartonLineSn.setStaCartonLine(staCartonLine);
                                        staCartonLineSnDao.save(staCartonLineSn);
                                        staCartonLineSnDao.flush();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void insertStaOpDetailLog(String staCode, Long ouId) {
        StockTransApplication sta = staDao.findStaBySlipCodeOrCode(staCode, ouId, new BeanPropertyRowMapperExt<StockTransApplication>(StockTransApplication.class));
        if (null == sta) {
            List<ChannelWhRef> cwrList = channelWhRefRefDao.getChannelRefByOuid(ouId);
            if (cwrList != null) {
                for (ChannelWhRef cwr : cwrList) {
                    if (cwr.getReceivePrefix() != null && !"".equals(cwr.getReceivePrefix())) {
                        sta = staDao.findStaBySlipCodeOrCode(cwr.getReceivePrefix() + staCode, ouId, new BeanPropertyRowMapperExt<StockTransApplication>(StockTransApplication.class));
                        if (sta != null) {
                            staCode = cwr.getReceivePrefix() + staCode;
                            break;
                        }
                    }
                }
            }
        }
        staOpLogDao.insertStaOpDetailLog(sta.getId());
        staOpDetailDao.deleteStaOpDetailLog(sta.getId());
    }

    @Override
    public BiChannel findDefectType(String staCode) {
        StockTransApplication sta = staDao.getByCode(staCode);
        BiChannel channel = biChannelDao.getByCode(sta.getOwner());
        return channel;
    }

    public String insertSkuImperfect(String staCode, String type, String reason, Long skuId, int qty, OperationUnit operationUnit, String sn) {
        SkuImperfect skuImperfect = new SkuImperfect();
        skuImperfect.setCreateTime(new Date());
        StockTransApplication sta = staDao.getByCode(staCode);
        skuImperfect.setOwner(sta.getOwner());
        String code = getTimeMillisSequence();
        skuImperfect.setDefectCode(code);
        if (!"其他".equals(type)) {
            BiChannelImperfect name = biChannelImperfectDao.getByPrimaryKey(Long.parseLong(type));
            skuImperfect.setDefectType(name.getName());
        } else {
            skuImperfect.setDefectType("其他");
        }
        if (!"其他".equals(reason)) {
            BiChannelImperfectLine line = biChannelImperfectLineDao.getByPrimaryKey(Long.parseLong(reason));
            skuImperfect.setDefectWhy(line.getName());
        } else {
            skuImperfect.setDefectWhy("其他");
        }
        skuImperfect.setStatus(1);
        Sku sku2 = skuDao.getByPrimaryKey(skuId);
        skuImperfect.setSku(sku2);
        skuImperfect.setSta(sta);
        skuImperfect.setQty(1);
        skuImperfect.setOuId(operationUnit);

        /*
         * ImperfectStv imperfectStv = new ImperfectStv(); Sku sku2 = skuDao.getByPrimaryKey(skuId);
         * imperfectStv.setBarCode(sku2.getBarCode()); imperfectStv.setJmCode(sku2.getJmCode());
         * imperfectStv.setName(sku2.getName());
         * imperfectStv.setSupplierCode(sku2.getSupplierCode()); InventoryStatus invStatus =
         * inventoryStatusDao.findByNameAndOu("残次品", operationUnit.getId());
         * imperfectStv.setStatusId(invStatus); imperfectStv.setStatusName(invStatus.getName());
         * imperfectStv.setCompleteQuantity(0); imperfectStv.setAddQuantity(1); if
         * (!"其他".equals(type)) { BiChannelImperfect name =
         * biChannelImperfectDao.getByPrimaryKey(Long.parseLong(type));
         * imperfectStv.setImperfectName(name.getName()); } else {
         * imperfectStv.setImperfectName("其他"); }
         * 
         * if (!"其他".equals(reason)) { BiChannelImperfectLine line =
         * biChannelImperfectLineDao.getByPrimaryKey(Long.parseLong(reason));
         * imperfectStv.setImperfectLineName(line.getName()); } else {
         * imperfectStv.setImperfectLineName("其他"); } StockTransApplication sta =
         * staDao.getByCode(staCode); imperfectStv.setSta(sta); imperfectStv.setCreateTime(new
         * Date());
         */
        /*
         * String code = getTimeMillisSequence(); imperfectStv.setDefectCode(code); if (null != sn
         * && !"".equals(sn)) { imperfectStv.setSkuSn(sn); imperfectStv.setSn(sn); }
         * imperfectStvDao.save(imperfectStv); imperfectStvDao.flush();
         */
        skuImperfectDao.save(skuImperfect);
        skuImperfectDao.flush();
        return code;
    }

    public void updateCartonLineSn(String dmgCode, String dmgReason, String dmgType) {
        StaCartonLineSn staCartonLineSn = staCartonLineSnDao.getStaCartonLineSnByCode(dmgCode);
        if (null != staCartonLineSn) {
            staCartonLineSn.setDmgReason(dmgReason);
            staCartonLineSn.setDmgType(dmgType);
            staCartonLineSnDao.save(staCartonLineSn);
            staCartonLineSnDao.flush();
        }
    }

    public List<StaCarton> staCartonList(String staCode, OperationUnit op) {
        // StockTransApplication sta = staDao.getByCode(staCode);
        StockTransApplication sta = staDao.findStaBySlipCodeOrCode(staCode, op.getId(), new BeanPropertyRowMapperExt<StockTransApplication>(StockTransApplication.class));
        if (null == sta) {
            List<ChannelWhRef> cwrList = channelWhRefRefDao.getChannelRefByOuid(op.getId());
            if (cwrList != null) {
                for (ChannelWhRef cwr : cwrList) {
                    if (cwr.getReceivePrefix() != null && !"".equals(cwr.getReceivePrefix())) {
                        sta = staDao.findStaBySlipCodeOrCode(cwr.getReceivePrefix() + staCode, op.getId(), new BeanPropertyRowMapperExt<StockTransApplication>(StockTransApplication.class));
                        if (sta != null) {
                            staCode = cwr.getReceivePrefix() + staCode;
                            break;
                        }
                    }
                }
            }
        }
        List<StaCarton> staCartonList = staCartonDao.findStaCartonByStaId1(sta.getId(), op.getId(), new BeanPropertyRowMapperExt<StaCarton>(StaCarton.class));
        return staCartonList;
    }

    public static String getTimeMillisSequence() {
        long nanoTime = System.nanoTime();
        String preFix = "";
        if (nanoTime < 0) {
            preFix = "CC";// 负数补位A保证负数排在正数Z前面,解决正负临界值(如A9223372036854775807至Z0000000000000000000)问题。
            nanoTime = nanoTime + Long.MAX_VALUE + 1;
        } else {
            preFix = "C";
        }
        String nanoTimeStr = String.valueOf(nanoTime);

        int difBit = String.valueOf(Long.MAX_VALUE).length() - nanoTimeStr.length();
        for (int i = 0; i < difBit; i++) {
            preFix = preFix + "0";
        }
        nanoTimeStr = preFix + nanoTimeStr;

        return nanoTimeStr;
    }

    /**
     * 根据货箱ID获取未推荐到的明细
     * 
     * @param cartonId
     * @return
     */
    public List<StaCartonLine> findRecommendFaildCartonLine(Long cartonId) {
        List<StaCartonLine> sclList = staCartonLineDao.findRecommendFaildCartonLine(cartonId, new BeanPropertyRowMapper<StaCartonLine>(StaCartonLine.class));
        return sclList;
    }

    public void findAsnReceiveByTime() {
        Date date = getNDay(new Date(), -7);
        List<AsnReceive> AsnReceiveList = mongoOperation.find(new Query(Criteria.where("createTime").lte(date)), AsnReceive.class);
        for (AsnReceive list : AsnReceiveList) {
            mongoOperation.remove(new Query(Criteria.where("code").in(list.getCode())), AsnReceive.class);
        }
    }

    @Override
    public Boolean isAutoWh(Long ouId) {
        Boolean msg = false;
        Warehouse warehouse = warehouseDao.getByOuId(ouId);
        if (null != warehouse && null != warehouse.getIsAutoWh() && warehouse.getIsAutoWh()) {
            msg = true;
        } else {
            msg = false;
        }
        return msg;
    }

    public void updateContainerStatus(String cartonBox) {
        WhContainer whContainer = whContainerDao.getWhContainerByCode(cartonBox);
        if (null != whContainer) {
            WhContainer wh = whContainerDao.getByPrimaryKey(whContainer.getId());
            wh.setStatus(DefaultStatus.SENT);
            whContainerDao.save(wh);
        }
    }

    public void updateStaCartonStatus(List<StaCarton> list) {
        List<Long> listIds = new ArrayList<Long>();
        for (StaCarton listId : list) {
            listIds.add(listId.getId());
        }
        staCartonDao.updateStatusById(listIds);
    }

    public SkuSn findSnByOuId(String sn, Long ouId) {
        return skuSnDao.findSnByOuId(sn, ouId, new BeanPropertyRowMapper<SkuSnCommand>(SkuSnCommand.class));
    }

    public StaCartonLineSn findSnbysn(String sn) {
        return staCartonLineSnDao.findSnbysn(sn);
    }

    public StaOpDetail findOpDetailBySn(String sn) {
        return staOpDetailDao.findOpDetailBySn(sn, new BeanPropertyRowMapper<StaOpDetail>(StaOpDetail.class));
    }

    public StaOpDetail findByNo(String staCode, Long skuId, Long userId, Long ouId) {
        StockTransApplication sta = staDao.findStaBySlipCodeOrCode(staCode, ouId, new BeanPropertyRowMapperExt<StockTransApplication>(StockTransApplication.class));
        if (null == sta) {
            List<ChannelWhRef> cwrList = channelWhRefRefDao.getChannelRefByOuid(ouId);
            if (cwrList != null) {
                for (ChannelWhRef cwr : cwrList) {
                    if (cwr.getReceivePrefix() != null && !"".equals(cwr.getReceivePrefix())) {
                        sta = staDao.findStaBySlipCodeOrCode(cwr.getReceivePrefix() + staCode, ouId, new BeanPropertyRowMapperExt<StockTransApplication>(StockTransApplication.class));
                        if (sta != null) {
                            staCode = cwr.getReceivePrefix() + staCode;
                            break;
                        }
                    }
                }
            }
        }
        return staOpDetailDao.findByNo(sta.getId(), skuId, userId, ouId, new BeanPropertyRowMapper<StaOpDetail>(StaOpDetail.class));
    }

    public StaOpDetail findBySku(String staCode, String code, Long userId, Long ouId) {
        StockTransApplication sta = staDao.findStaBySlipCodeOrCode(staCode, ouId, new BeanPropertyRowMapperExt<StockTransApplication>(StockTransApplication.class));
        if (null == sta) {
            List<ChannelWhRef> cwrList = channelWhRefRefDao.getChannelRefByOuid(ouId);
            if (cwrList != null) {
                for (ChannelWhRef cwr : cwrList) {
                    if (cwr.getReceivePrefix() != null && !"".equals(cwr.getReceivePrefix())) {
                        sta = staDao.findStaBySlipCodeOrCode(cwr.getReceivePrefix() + staCode, ouId, new BeanPropertyRowMapperExt<StockTransApplication>(StockTransApplication.class));
                        if (sta != null) {
                            staCode = cwr.getReceivePrefix() + staCode;
                            break;
                        }
                    }
                }
            }
        }
        return staOpDetailDao.findBySku(sta.getId(), code, userId, ouId, new BeanPropertyRowMapper<StaOpDetail>(StaOpDetail.class));
    }

    public AsnReceive findBySkuId(String staCode) {
        AsnReceive asnReceive = mongoOperation.findOne(new Query(Criteria.where("code").is(staCode)), AsnReceive.class);
        return asnReceive;
    }

    public Boolean findSkuByStaLine(String staCode, Long ouId) {
        Boolean flag = true;
        StockTransApplication sta = staDao.findStaBySlipCodeOrCode(staCode, ouId, new BeanPropertyRowMapperExt<StockTransApplication>(StockTransApplication.class));
        if (null == sta) {
            List<ChannelWhRef> cwrList = channelWhRefRefDao.getChannelRefByOuid(ouId);
            if (cwrList != null) {
                for (ChannelWhRef cwr : cwrList) {
                    if (cwr.getReceivePrefix() != null && !"".equals(cwr.getReceivePrefix())) {
                        sta = staDao.findStaBySlipCodeOrCode(cwr.getReceivePrefix() + staCode, ouId, new BeanPropertyRowMapperExt<StockTransApplication>(StockTransApplication.class));
                        if (sta != null) {
                            staCode = cwr.getReceivePrefix() + staCode;
                            break;
                        }
                    }
                }
            }
        }
        if (sta == null) {
            throw new BusinessException("没有该作业单或箱号");
        } else if (null != sta && (!StockTransApplicationStatus.CANCELED.equals(sta.getStatus()) && !StockTransApplicationStatus.CANCEL_UNDO.equals(sta.getStatus()))) {
            List<StaLine> staLine = staLineDao.findByStaId(sta.getId());
            for (StaLine list : staLine) {
                Sku sku = skuDao.getByPrimaryKey(list.getSku().getId());
                if (null != sku) {
                    if (InboundStoreMode.SHELF_MANAGEMENT.equals(sku.getStoremode()) || (sku.getIsSnSku() == null ? false : sku.getIsSnSku())) {
                        flag = false;
                        return flag;
                    }
                }
            }

        } else {
            flag = false;
        }
        return flag;
    }

    public String isGroupSta(String staCode, Long ouId) {
        String flag = "success";
        StockTransApplication sta1 = staDao.findStaBySlipCodeOrCode(staCode, ouId, new BeanPropertyRowMapperExt<StockTransApplication>(StockTransApplication.class));
        if (null == sta1) {
            List<ChannelWhRef> cwrList = channelWhRefRefDao.getChannelRefByOuid(ouId);
            if (cwrList != null) {
                for (ChannelWhRef cwr : cwrList) {
                    if (cwr.getReceivePrefix() != null && !"".equals(cwr.getReceivePrefix())) {
                        sta1 = staDao.findStaBySlipCodeOrCode(cwr.getReceivePrefix() + staCode, ouId, new BeanPropertyRowMapperExt<StockTransApplication>(StockTransApplication.class));
                        if (sta1 != null) {
                            staCode = cwr.getReceivePrefix() + staCode;
                            break;
                        }
                    }
                }
            }
        }
        List<StockTransApplication> staList = staDao.getChildStaByGroupId(sta1.getId());
        // 主作业单
        if (null != staList && staList.size() > 0) {
            flag = "false";
        }
        return flag;
    }

    public String isGroupSta1(String staCode, Long ouId) {
        String flag = "success";
        StockTransApplication sta1 = staDao.getByCode1(staCode, ouId);
        if (null == sta1) {
            List<ChannelWhRef> cwrList = channelWhRefRefDao.getChannelRefByOuid(ouId);
            if (cwrList != null) {
                for (ChannelWhRef cwr : cwrList) {
                    if (cwr.getReceivePrefix() != null && !"".equals(cwr.getReceivePrefix())) {
                        sta1 = staDao.getByCode1(cwr.getReceivePrefix() + staCode, ouId);
                        if (sta1 != null) {
                            staCode = cwr.getReceivePrefix() + staCode;
                            break;
                        }
                    }
                }
            }
        }
        List<StockTransApplication> staList = staDao.getChildStaByGroupId(sta1.getId());
        // 主作业单
        if (null != staList && staList.size() > 0) {
            for (StockTransApplication sta : staList) {
                StaCartonLine staCartonLine = staCartonLineDao.findQtyByStaId(sta.getId(), null, new BeanPropertyRowMapperExt<StaCartonLine>(StaCartonLine.class));
                StaLineCommand staLine = staLineDao.findLineQtyListByStaId(sta.getId(), new BeanPropertyRowMapper<StaLineCommand>(StaLineCommand.class));
                if (null != staCartonLine && null != staCartonLine.getQty()) {
                    if (staCartonLine.getQty().equals(0L)) {
                        flag = "false";
                        break;
                    } else {
                        if (!staLine.getQuantity().equals(staCartonLine.getQty())) {
                            flag = "false";
                            break;
                        }
                    }
                } else {
                    flag = "false";
                    break;
                }
            }
        }
        return flag;
    }

    public StockTransApplication findSta(String staCode, OperationUnit op) {
        StockTransApplication sta = staDao.findStaBySlipCodeOrCode(staCode, op.getId(), new BeanPropertyRowMapperExt<StockTransApplication>(StockTransApplication.class));
        if (null == sta) {
            List<ChannelWhRef> cwrList = channelWhRefRefDao.getChannelRefByOuid(op.getId());
            if (cwrList != null) {
                for (ChannelWhRef cwr : cwrList) {
                    if (cwr.getReceivePrefix() != null && !"".equals(cwr.getReceivePrefix())) {
                        sta = staDao.findStaBySlipCodeOrCode(cwr.getReceivePrefix() + staCode, op.getId(), new BeanPropertyRowMapperExt<StockTransApplication>(StockTransApplication.class));
                        if (sta != null) {
                            staCode = cwr.getReceivePrefix() + staCode;
                            break;
                        }
                    }
                }
            }
        }
        return sta;

    }

    /**
     * 按箱收货（扫标签）1
     */
    @Override
    public Long checkTag(String staCode, String tag, OperationUnit op) {
        StockTransApplication s = staDao.findStaBySlipCodeOrCode(staCode, op.getId(), new BeanPropertyRowMapperExt<StockTransApplication>(StockTransApplication.class));
        if (null == s) {
            List<ChannelWhRef> cwrList = channelWhRefRefDao.getChannelRefByOuid(op.getId());
            if (cwrList != null) {
                for (ChannelWhRef cwr : cwrList) {
                    if (cwr.getReceivePrefix() != null && !"".equals(cwr.getReceivePrefix())) {
                        s = staDao.findStaBySlipCodeOrCode(cwr.getReceivePrefix() + staCode, op.getId(), new BeanPropertyRowMapperExt<StockTransApplication>(StockTransApplication.class));
                        if (s != null) {
                            staCode = cwr.getReceivePrefix() + staCode;
                            break;
                        }
                    }
                }
            }
        }
        if (s == null) {
            throw new BusinessException("没有该作业单");
        }
        StockTransApplication sta1 = staDao.getByPrimaryKey(s.getId());
        // System.out.println(sta1.getContainerCode());
        // System.out.println(sta1.getId());
        List<StockTransApplication> staList = staDao.getChildStaByGroupId(sta1.getId());
        if (null != staList && staList.size() > 0) {// 主作业单
            throw new BusinessException("该作业单为主作业单");
        }
        if (StockTransApplicationStatus.FINISHED.equals(sta1.getStatus())) {
            throw new BusinessException("该作业单已经完成");
        }
        String carCode = (sta1.getRefSlipCode() == null ? sta1.getContainerCode() : sta1.getRefSlipCode());
        if (carCode == null) {
            throw new BusinessException("作业单：" + sta1.getCode() + ",没有箱号");
        }
        sta1.setContainerCode(tag);
        StaCarton c = staCartonDao.getStaCartonByCode1(tag, op.getId(), new BeanPropertyRowMapperExt<StaCarton>(StaCarton.class));
        StaCarton c2 = staCartonDao.getStaCartonByCode1(carCode, op.getId(), new BeanPropertyRowMapperExt<StaCarton>(StaCarton.class));
        if (c2 == null) {
            throw new BusinessException("容器号为空");
        }
        if (c == null || c.getStatus() == null || c.getStatus().getValue() == 1 || c.getStatus().getValue() == 10) {} else {
            throw new BusinessException("此标签正在被使用");
        }
        // 验证标签 是否正使用
        WhContainer whc = whContainerDao.getWhContainerByCode(tag);
        if (whc != null) {
            if (DefaultStatus.CREATED.equals(whc.getStatus())) {
                WhContainer wh = whContainerDao.getByPrimaryKey(whc.getId());
                wh.setStatus(DefaultStatus.EXECUTING);
                wh.setStaId(s.getId());
                sta1.setContainerCode(null);
                whContainerDao.save(wh);
            } else {
                throw new BusinessException("此标签正在被使用");
            }
        }
        // StaCarton c2 = staCartonDao.getStaCartonByCode(tag, op.getId(), new
        // BeanPropertyRowMapperExt<StaCarton>(StaCarton.class));
        // if (c2 != null) {
        // throw new BusinessException("此标签正在被使用");
        // }
        StaCarton cc = staCartonDao.getByPrimaryKey(c2.getId());
        cc.setCode(tag);
        staCartonDao.save(cc);
        // sta1.setContainerCode(tag);
        staDao.save(sta1);
        // int i = staCartonDao.updateTagByCode(tag, staCode, c.getId());
        log.info("checkTag:" + tag + "," + staCode + "," + c2.getId());
        return c2.getId();
    }

    public String checkTrackingNoCurrency(String lpCode, String trackingNo, Long ouId, Long userId) {
        String msg = "";
        PackageInfo packageInfo = packageInfoDao.findByTrackingNo(trackingNo);
        if (null != packageInfo) {
            if (packageInfo.getHandOverLine() == null) {
                if (lpCode.equals(packageInfo.getLpCode())) {
                    if (null != packageInfo.getStaDeliveryInfo() && null != packageInfo.getStaDeliveryInfo().getId()) {
                        StockTransApplication sto = staDao.getByPrimaryKey(packageInfo.getStaDeliveryInfo().getId());
                        if (null != sto.getIsPreSale() && "1".equals(sto.getIsPreSale())) {
                            // 预售订单
                            msg = "isPreSale";
                        } else {
                            // 非预售
                            if (packageInfo.getStatus() != null && PackageInfoStatus.OUTBOUND.equals(packageInfo.getStatus())) {
                                msg = "success";
                            } else {
                                msg = "packageStatusOperation";
                            }
                        }
                    } else {
                        // 非预售
                        if (packageInfo.getStatus() != null && PackageInfoStatus.OUTBOUND.equals(packageInfo.getStatus())) {
                            msg = "success";
                        } else {
                            msg = "packageStatusOperation";
                        }
                    }
                } else {
                    msg = "notLogisticsPackage";
                }
            } else {
                msg = "yes";
            }
        } else {
            msg = "staIsNotFind";
        }
        if (null != msg && "success".equals(msg)) {
            PdaHandOverCurrency pdaHandOverCurrency = pdaHandOverCurrencyDao.findPdaHandOverCurrencyByTransNo(trackingNo, new BeanPropertyRowMapperExt<PdaHandOverCurrency>(PdaHandOverCurrency.class));
            // 看有没有 没有在插入
            if (null != pdaHandOverCurrency) {} else {
                pdaHandOverCurrency = new PdaHandOverCurrency();
                pdaHandOverCurrency.setCreateTime(new Date());
                pdaHandOverCurrency.setLpcode(packageInfo.getLpCode());
                pdaHandOverCurrency.setTrackingNo(packageInfo.getTrackingNo());
                pdaHandOverCurrency.setOuId(ouId);
                pdaHandOverCurrency.setUserId(userId);
                pdaHandOverCurrency.setPackageInfoId(packageInfo.getId());
                pdaHandOverCurrency.setStatus(1);
                pdaHandOverCurrencyDao.save(pdaHandOverCurrency);
            }
        }
        return msg;
    }

    public String pdaExecuteTransitInner(String sendlocationCode, Long ouId, String skuCode, String locationCode, String statusName, String owner, String qty, Long userId, String userName) {
        String msg = pdaCheckSendlocationCode(sendlocationCode, ouId, skuCode, locationCode, statusName, owner);
        if (null != msg && msg.equals("success")) {
            Warehouse warehouse = warehouseDao.getByOuId(ouId);
            BiChannel channel = biChannelDao.getByName(owner);
            Sku sku = skuDao.getByBarcode(skuCode, warehouse.getCustomer().getId());
            if (sku == null) {
                List<SkuBarcodeCommand> list = skuBarcodeDao.findByBarcode1(skuCode, warehouse.getCustomer().getId(), new BeanPropertyRowMapper<SkuBarcodeCommand>(SkuBarcodeCommand.class));
                if (null != list && list.size() > 0) {
                    sku = skuDao.getByPrimaryKey(list.get(0).getSkuId());
                }
            }
            WarehouseLocation warehouseLocationCode = warehouseLocationDao.findLocationByCode(locationCode, ouId);
            OperationUnit ou1 = ouDao.getByPrimaryKey(ouId);
            ou1 = ouDao.getByPrimaryKey(ou1.getParentUnit().getId());
            InventoryStatus invetoryStatus = inventoryStatusDao.findInvStatusisByOuId(ou1.getParentUnit().getId(), statusName);
            Long inventoryQty = inventoryDao.find1InventoryBySkuIdLocationIdOwner(sku.getId(), warehouseLocationCode.getId(), channel.getCode(), ouId, invetoryStatus.getId(), new SingleColumnRowMapper<Long>(Long.class));
            if (null != inventoryQty && inventoryQty.longValue() >= Long.parseLong(qty)) {
                List<InventoryCommand> inventoryCommandList =
                        inventoryDao.findInventoryBySkuIdLocationIdOwner2(sku.getId(), warehouseLocationCode.getId(), channel.getCode(), ouId, invetoryStatus.getId(), new BeanPropertyRowMapper<InventoryCommand>(InventoryCommand.class));
                Long countInv = Long.parseLong(qty);
                WarehouseLocation sendWarehouseLocationCode = warehouseLocationDao.findLocationByCode(sendlocationCode, ouId);
                for (InventoryCommand inventoryCommand : inventoryCommandList) {
                    countInv = countInv - inventoryCommand.getQuantity();
                    StockTransTxLog log = new StockTransTxLog();
                    StockTransTxLog inLog = new StockTransTxLog();
                    TransactionType type = transactionTypeDao.findByCode(Constants.TRANSACTION_TYPE_TRANSIT_INNER_OUT);
                    TransactionType inType = transactionTypeDao.findByCode(Constants.TRANSACTION_TYPE_TRANSIT_INNER_IN);
                    inLog.setDirection(TransactionDirection.INBOUND);
                    inLog.setDistrictId(sendWarehouseLocationCode.getDistrict().getId());
                    inLog.setInvStatusId(invetoryStatus.getId());
                    inLog.setOwner(channel.getCode());
                    inLog.setLocationId(sendWarehouseLocationCode.getId());
                    inLog.setSkuId(sku.getId());
                    inLog.setTransactionTime(new Date());
                    inLog.setTransactionType(inType);
                    inLog.setWarehouseOuId(ouId);
                    inLog.setOpUserName(userName);
                    inLog.setSlipCode("PDA_TRANSIT_INNER");

                    log.setDirection(TransactionDirection.OUTBOUND);
                    log.setDistrictId(inventoryCommand.getDistrictId());
                    log.setInvStatusId(invetoryStatus.getId());
                    log.setOwner(channel.getCode());
                    log.setLocationId(inventoryCommand.getLocationId());
                    log.setSkuId(sku.getId());
                    log.setTransactionTime(new Date());
                    log.setTransactionType(type);
                    log.setWarehouseOuId(ouId);
                    log.setOpUserName(userName);
                    // 设置slipcode=PDA_TRANSIT_INNER来不写入库存流水表
                    log.setSlipCode("PDA_TRANSIT_INNER");
                    if (countInv < 0) {
                        inventoryDao.addNewInventory(inventoryCommand.getId(), -countInv, null);
                        inventoryDao.modifyInventoryByOwnerQty(inventoryCommand.getId(), sendWarehouseLocationCode.getId(), sendWarehouseLocationCode.getDistrict().getId(), inventoryCommand.getQuantity() + countInv);
                        log.setQuantity(inventoryCommand.getQuantity() + countInv);
                        log.setBatchCode(inventoryCommand.getBatchCode());
                        log.setValidDate(inventoryCommand.getValidDate());
                        log.setExpireDate(inventoryCommand.getExpireDate());
                        log.setProductionDate(inventoryCommand.getProductionDate());

                        inLog.setQuantity(inventoryCommand.getQuantity() + countInv);
                        inLog.setBatchCode(inventoryCommand.getBatchCode());
                        inLog.setValidDate(inventoryCommand.getValidDate());
                        inLog.setExpireDate(inventoryCommand.getExpireDate());
                        inLog.setProductionDate(inventoryCommand.getProductionDate());

                        stockTransTxLogDao.save(log);
                        stockTransTxLogDao.save(inLog);
                        break;
                    } else if (countInv == 0) {
                        inventoryDao.modifyInventoryByOwner(inventoryCommand.getId(), sendWarehouseLocationCode.getId(), sendWarehouseLocationCode.getDistrict().getId());
                        log.setQuantity(inventoryCommand.getQuantity());
                        log.setBatchCode(inventoryCommand.getBatchCode());
                        log.setValidDate(inventoryCommand.getValidDate());
                        log.setExpireDate(inventoryCommand.getExpireDate());
                        log.setProductionDate(inventoryCommand.getProductionDate());

                        inLog.setQuantity(inventoryCommand.getQuantity());
                        inLog.setBatchCode(inventoryCommand.getBatchCode());
                        inLog.setValidDate(inventoryCommand.getValidDate());
                        inLog.setExpireDate(inventoryCommand.getExpireDate());
                        inLog.setProductionDate(inventoryCommand.getProductionDate());
                        stockTransTxLogDao.save(log);
                        stockTransTxLogDao.save(inLog);
                        break;
                    } else {
                        inventoryDao.modifyInventoryByOwner(inventoryCommand.getId(), sendWarehouseLocationCode.getId(), sendWarehouseLocationCode.getDistrict().getId());
                        log.setQuantity(inventoryCommand.getQuantity());
                        log.setBatchCode(inventoryCommand.getBatchCode());
                        log.setValidDate(inventoryCommand.getValidDate());
                        log.setExpireDate(inventoryCommand.getExpireDate());
                        log.setProductionDate(inventoryCommand.getProductionDate());

                        inLog.setQuantity(inventoryCommand.getQuantity());
                        inLog.setBatchCode(inventoryCommand.getBatchCode());
                        inLog.setValidDate(inventoryCommand.getValidDate());
                        inLog.setExpireDate(inventoryCommand.getExpireDate());
                        inLog.setProductionDate(inventoryCommand.getProductionDate());
                        stockTransTxLogDao.save(log);
                        stockTransTxLogDao.save(inLog);
                    }

                }

                // 加操作人
                PdaTransitInnerLog pdaTransitInnerLog = new PdaTransitInnerLog();
                pdaTransitInnerLog.setCreateTime(new Date());
                pdaTransitInnerLog.setDisId(warehouseLocationCode.getDistrict().getId());
                pdaTransitInnerLog.setLocId(warehouseLocationCode.getId());
                pdaTransitInnerLog.setOuId(ouId);
                pdaTransitInnerLog.setOwner(channel.getCode());
                pdaTransitInnerLog.setQty(Long.parseLong(qty));
                pdaTransitInnerLog.setSkuId(sku.getId());
                pdaTransitInnerLog.setTargetDisId(sendWarehouseLocationCode.getDistrict().getId());
                pdaTransitInnerLog.setTargetLocId(sendWarehouseLocationCode.getId());
                pdaTransitInnerLog.setUserId(userId);
                pdaTransitInnerLogDao.save(pdaTransitInnerLog);
            } else {
                msg = "NoInventory";
            }
        }
        return msg;
    }

    public String pdaCheckSendlocationCode(String sendlocationCode, Long ouId, String skuCode, String locationCode, String statusName, String owner) {
        String msg = null;
        String isMixTime = null;
        WarehouseLocation warehouseLocation = warehouseLocationDao.findLocationByCode(sendlocationCode, ouId);
        if (null != warehouseLocation && !"".equals(warehouseLocation)) {
            isMixTime = warehouseLocation.getIsMixTime();
            Warehouse warehouse = warehouseDao.getByOuId(ouId);
            Sku sku = skuDao.getByBarcode(skuCode, warehouse.getCustomer().getId());
            if (sku == null) {
                List<SkuBarcodeCommand> list = skuBarcodeDao.findByBarcode1(skuCode, warehouse.getCustomer().getId(), new BeanPropertyRowMapper<SkuBarcodeCommand>(SkuBarcodeCommand.class));
                if (null != list && list.size() > 0) {
                    sku = skuDao.getByPrimaryKey(list.get(0).getSkuId());
                }
            }
            if (InboundStoreMode.RESPECTIVE.equals(sku.getStoremode())) { // 单批隔离
                Long invCount = inventoryDao.findInventoryBySkuIdLocationId(sku.getId(), warehouse.getId(), null, new SingleColumnRowMapper<Long>(Long.class));
                if (invCount != null && invCount > 0) {// 单批隔离，有商品，不能存放
                    msg = "respective";
                } else { // 可以入库
                    msg = "success";
                }
            } else if (InboundStoreMode.SHELF_MANAGEMENT.equals(sku.getStoremode())) {
                List<InventoryCommand> inventoryCommandList =
                        inventoryDao.findInventoryBySkuIdLocationIdOwner(sku.getId(), warehouseLocation.getId(), biChannelDao.getByName(owner).getCode(), ouId, new BeanPropertyRowMapper<InventoryCommand>(InventoryCommand.class));
                if (null != inventoryCommandList && inventoryCommandList.size() > 0) {
                    if (inventoryCommandList.size() > 1 && !"1".equals(isMixTime)) {
                        msg = "isTogether";// 效期商品已经混放
                    } else {
                        WarehouseLocation locationCodeLocation = warehouseLocationDao.findLocationByCode(locationCode, ouId);
                        OperationUnit ou1 = ouDao.getByPrimaryKey(ouId);
                        ou1 = ouDao.getByPrimaryKey(ou1.getParentUnit().getId());
                        InventoryStatus invetoryStatus = inventoryStatusDao.findInvStatusisByOuId(ou1.getParentUnit().getId(), statusName);
                        Date locationCodeDate =
                                inventoryDao.findInvQtyBySkuIdOwnerAndLocationId2(sku.getId(), locationCodeLocation.getId(), ouId, biChannelDao.getByName(owner).getCode(), invetoryStatus.getId(), new SingleColumnRowMapper<Date>(Date.class));
                        if (null != locationCodeDate && locationCodeDate.getTime() != inventoryCommandList.get(0).getExpireDate().getTime() && !"1".equals(isMixTime)) {
                            msg = "expireDateIsEquals";// 效期商品跟准备移入的商品效期不一致
                        } else { // 可以入库
                            msg = "success";
                        }
                    }
                } else { // 可以入库
                    msg = "success";
                }
            } else {// 可以入库
                msg = "success";
            }
        } else {
            msg = "noFind";
        }
        return msg;
    }

    /**
     * AD预售业务处理
     */
    @Override
    public String checkTrackingNo2(String trackingNo, Long ouId, Long userId) {
        boolean b = false;
        String msg = "";
        PackageInfo packageInfo = packageInfoDao.findByTrackingNo(trackingNo);
        if (packageInfo != null) {
            if (packageInfo.getStatus() == PackageInfoStatus.CREATED) {
                return "此包裹未称重！";
            }
            if (null != packageInfo.getStaDeliveryInfo() && null != packageInfo.getStaDeliveryInfo().getId()) {
                StockTransApplication sto = staDao.getByPrimaryKey(packageInfo.getStaDeliveryInfo().getId());
                if (null != sto && (StockTransApplicationStatus.CANCEL_UNDO.equals(sto.getStatus()) || StockTransApplicationStatus.CANCELED.equals(sto.getStatus()))) {
                    msg = "ad_cancel";// 已经取消
                } else if (StockTransApplicationStatus.INTRANSIT.equals(sto.getStatus())) {// 正确的业务处理逻辑
                    String str = adCheckManager.storeLogisticsSend(sto.getId(), false);
                    if ("".equals(str)) {// ok
                        PdaHandOverCommand pdaHandOver = pdaHandOverDao.findPdaHandOverByTransNo(trackingNo, new BeanPropertyRowMapperExt<PdaHandOverCommand>(PdaHandOverCommand.class));
                        PdaHandOverLog pdaHandOverLog = pdaHandOverLogDao.findPdaHandOverLogByTransNo(trackingNo, new BeanPropertyRowMapperExt<PdaHandOverLog>(PdaHandOverLog.class));
                        // 看有没有 没有在插入
                        if (null != pdaHandOver || null != pdaHandOverLog) {
                            msg = "scanning==+" + pdaHandOver.getLoginName();
                        } else {
                            PdaHandOver pdaHandOver1 = new PdaHandOver();
                            pdaHandOver1.setCreateTime(new Date());
                            pdaHandOver1.setLpcode(packageInfo.getLpCode());
                            pdaHandOver1.setTrackingNo(packageInfo.getTrackingNo());
                            pdaHandOver1.setOuId(ouId);
                            pdaHandOver1.setUserId(userId);
                            pdaHandOver1.setPackageInfoId(packageInfo.getId());
                            pdaHandOver1.setStatus(1);
                            pdaHandOver1.setStaId(sto.getId());
                            pdaHandOverDao.save(pdaHandOver1);
                            pdaHandOverDao.flush();
                        }
                        StockTransApplication sta = staDao.getByPrimaryKey(sto.getId());// 如果是多包裹怎么办???????
                        List<PackageInfo> ps = packageInfoDao.getByStaId(sto.getId());
                        List<PdaHandOver> pl = pdaHandOverDao.findPdaHandOverByStaId(sto.getId(), ouId, new BeanPropertyRowMapperExt<PdaHandOver>(PdaHandOver.class));
                        if (ps.size() == pl.size()) {
                            b = true;
                        }
                        // 回传 adidas出库信息
                        if ("adidas".equals(sta.getSystemKey()) && b) {
                            wmsOrderStatusOmsDao.updateWmsOrderByStaCode(sta.getCode());
                        } else {
                            log.error("ad_出库信息.trackingNo" + trackingNo + sta.getSystemKey());
                        }
                        msg = "success";
                    } else if ("server.other-invalid-order-status".equals(str)) {
                        log.error("ad_不能发货状态.trackingNo" + trackingNo);
                        msg = "ad_NotOutBount";
                    } else {
                        msg = "ad_sysError";// 系统异常
                    }

                } else if (StockTransApplicationStatus.FINISHED.equals(sto.getStatus())) {
                    msg = "ad_over";// 作业单完成
                } else {
                    msg = "ad_statusError";// 作业单状态不对
                }

            } else {
                msg = "ad_sysError";// 系统异常
                log.error("ad_sysError.trackingNo" + trackingNo);
            }
        } else {
            msg = "ad_notTrackingNo";// 没有改运单号
        }
        return msg;
    }

    public String checkTrackingNo(String trackingNo, Long ouId, Long userId) {
        String msg = "";
        // 是否是旧单号
        PackageInfo packageInfo = packageInfoDao.findPackageByOldTrackingNo(trackingNo);
        PackageInfo packageInfo1 = new PackageInfo();
        AdvanceOrderAddInfo advanceOrderAddInfo = null;
        if (null != packageInfo) {
            StockTransApplication sto = staDao.getByPrimaryKey(packageInfo.getStaDeliveryInfo().getId());
            if (null != sto && StockTransApplicationStatus.FINISHED.equals(sto.getStatus())) {
                msg = "alreadyOutBound";
                return msg;
            }
            if (null != sto && (StockTransApplicationStatus.CANCEL_UNDO.equals(sto.getStatus()) || StockTransApplicationStatus.CANCELED.equals(sto.getStatus()))) {
                msg = "cancel";
            } else {
                if (null != sto && (!StockTransApplicationStatus.CANCEL_UNDO.equals(sto.getStatus()) || !StockTransApplicationStatus.CANCELED.equals(sto.getStatus()))) {
                    msg = "changeTransNo";
                } else if (null != sto && (StockTransApplicationStatus.CANCEL_UNDO.equals(sto.getStatus()) || StockTransApplicationStatus.CANCELED.equals(sto.getStatus()))) {
                    msg = "cancel";
                } else {
                    msg = "isNotPreSale";
                }
            }
        } else {
            packageInfo1 = packageInfoDao.findByTrackingNo(trackingNo);
            if (null != packageInfo1) {
                if (null != packageInfo1.getStaDeliveryInfo() && null != packageInfo1.getStaDeliveryInfo().getId()) {
                    StockTransApplication sto = staDao.getByPrimaryKey(packageInfo1.getStaDeliveryInfo().getId());
                    if (null != sto && StockTransApplicationStatus.FINISHED.equals(sto.getStatus())) {
                        msg = "alreadyOutBound";
                        return msg;
                    }
                    if (null != sto && !StockTransApplicationStatus.CANCEL_UNDO.equals(sto.getStatus()) && !StockTransApplicationStatus.CANCELED.equals(sto.getStatus())) {
                        advanceOrderAddInfo = advanceOrderAddInfoDao.findOrderInfoByOrderCode(sto.getRefSlipCode(), new BeanPropertyRowMapperExt<AdvanceOrderAddInfo>(AdvanceOrderAddInfo.class));
                        if (null != advanceOrderAddInfo) {
                            if (null != advanceOrderAddInfo.getIsAllowDeliver() && advanceOrderAddInfo.getIsAllowDeliver()) {
                                if (null != advanceOrderAddInfo.getIsChangeRecieverInfo() && advanceOrderAddInfo.getIsChangeRecieverInfo()) {
                                    if (null != advanceOrderAddInfo.getStatus() && "1".equals(advanceOrderAddInfo.getStatus().toString())) {
                                        msg = "changeTransNo";
                                    } else if (null != advanceOrderAddInfo.getStatus() && "0".equals(advanceOrderAddInfo.getStatus().toString())) {
                                        msg = "error";
                                    } else if (null != advanceOrderAddInfo.getStatus() && "5".equals(advanceOrderAddInfo.getStatus().toString())) {
                                        msg = "out";
                                    } else {
                                        msg = "success";
                                    }
                                } else {
                                    if ("5".equals(advanceOrderAddInfo.getStatus())) {
                                        msg = "out";
                                    } else {
                                        msg = "success";
                                    }
                                }
                            } else {
                                msg = "NotOutBount";
                            }
                        } else {
                            if (null != sto.getIsPreSale() && "1".equals(sto.getIsPreSale())) {
                                msg = "NotOutBount";
                            } else {
                                msg = "isNotPreSale";
                            }

                        }
                    } else if (null != sto && (StockTransApplicationStatus.CANCEL_UNDO.equals(sto.getStatus()) || StockTransApplicationStatus.CANCELED.equals(sto.getStatus()))) {
                        msg = "cancel";
                    } else {
                        msg = "isNotPreSale";
                    }

                } else {
                    msg = "staIsNotFind";
                }
            } else {
                msg = "staIsNotFind";
            }
        }

        if (null != msg && "success".equals(msg)) {
            Long id = 0L;
            if (null != packageInfo) {
                id = packageInfo.getStaDeliveryInfo().getId();
            } else {
                id = packageInfo1.getStaDeliveryInfo().getId();
            }

            if (packageInfo1.getStatus() == PackageInfoStatus.CREATED) {
                return "此包裹未称重！";
            }

            PdaHandOverCommand pdaHandOver = pdaHandOverDao.findPdaHandOverByTransNo(trackingNo, new BeanPropertyRowMapperExt<PdaHandOverCommand>(PdaHandOverCommand.class));
            // 看有没有 没有在插入
            String lastTrackingNo = "";
            if (null != pdaHandOver) {
                msg = "scanning==+" + pdaHandOver.getLoginName();
            } else {
                PdaHandOver pda = new PdaHandOver();
                pda.setCreateTime(new Date());
                pda.setLpcode(null != packageInfo ? packageInfo.getLpCode() : packageInfo1.getLpCode());
                pda.setTrackingNo(null != packageInfo ? packageInfo.getTrackingNo() : packageInfo1.getTrackingNo());
                // 最终扫描出库运单号
                lastTrackingNo = pda.getTrackingNo();
                pda.setOuId(ouId);
                pda.setUserId(userId);
                pda.setPackageInfoId(null != packageInfo ? packageInfo.getId() : packageInfo1.getId());
                pda.setStatus(1);
                pdaHandOverDao.save(pda);
            }

            StockTransApplication sta = staDao.getByPrimaryKey(id);
            // 回传pac oms出库信息
            if (StringUtils.hasText(sta.getSystemKey())) {
                if (advanceOrderAddInfo != null && advanceOrderAddInfo.getIsChangeRecieverInfo() != null && advanceOrderAddInfo.getIsChangeRecieverInfo().booleanValue()) {
                    // 更新出库表运单号
                    wmsTransInfoOmsDao.updatePreOrderTransInfoByOrderCode(advanceOrderAddInfo.getOrderCode(), lastTrackingNo);
                }
                wmsOrderStatusOmsDao.updateWmsOrderByStaCode(sta.getCode());
            } else {
                wmsIntransitNoticeOmsDao.updateOrderByStaCode(sta.getCode());
            }
            advanceOrderAddInfoDao.updateAdvanceOrderAddInfoById(sta.getRefSlipCode());
        }
        return msg;
    }

    public String createOutBoundHost(Long userId, Long ouId) {
        String msg = "";
        List<PdaHandOver> list = pdaHandOverDao.findPdaHandOverByUserId(userId, ouId, new BeanPropertyRowMapperExt<PdaHandOver>(PdaHandOver.class));
        if (null != list && list.size() > 0) {
            for (PdaHandOver l : list) {
                List<Long> lists = pdaHandOverDao.findPdaHandOverByLpcode(userId, l.getLpcode(), ouId, new SingleColumnRowMapper<Long>(Long.class));
                HandOverList handOverList = wareHouseManagerExecute.createHandOverListDelete(l.getLpcode(), lists, ouId, userId, true);
                wareHouseManager.handoverListhandOver(handOverList, userId);
            }
            msg = "success";
        } else {
            msg = "NoHost";
        }
        if (null != msg && "success".equals(msg)) {
            pdaHandOverDao.updatePdaHandByUserId(userId, ouId);
            /*
             * List<PdaHandOver> pdaHandOverList =
             * pdaHandOverDao.findPdaHandOverByUserIdAndOuId(userId, ouId, new
             * BeanPropertyRowMapper<PdaHandOver>(PdaHandOver.class)); if (null != pdaHandOverList
             * && pdaHandOverList.size() > 0) { for (PdaHandOver lists : pdaHandOverList) {
             * PdaHandOverLog phol = new PdaHandOverLog(); phol.setIsPreSale(1);
             * phol.setLpcode(lists.getLpcode()); phol.setPackageInfoId(lists.getPackageInfoId());
             * phol.setTrackingNo(lists.getTrackingNo()); phol.setOuId(lists.getOuId());
             * phol.setUserId(lists.getUserId()); phol.setStatus(5); phol.setCreateTime(new Date());
             * pdaHandOverLogDao.save(phol); } // delete
             * pdaHandOverDao.updatePdaHandOverByUserId(userId, ouId); }
             */
        }
        return msg;
    }

    public String createOutBoundHostCurrency(Long userId, Long ouId) {
        String msg = "";
        List<PdaHandOverCurrency> list = pdaHandOverCurrencyDao.findPdaHandOverCurrencyByUserId(userId, ouId, new BeanPropertyRowMapperExt<PdaHandOverCurrency>(PdaHandOverCurrency.class));
        if (null != list && list.size() > 0) {
            for (PdaHandOverCurrency l : list) {
                List<Long> lists = pdaHandOverCurrencyDao.findPdaHandOverCurrencyByLpcode(userId, l.getLpcode(), new SingleColumnRowMapper<Long>(Long.class));
                HandOverList handOverList = wareHouseManagerExecute.createHandOverListDelete(l.getLpcode(), lists, ouId, userId, true);
                wareHouseManager.handoverListhandOver(handOverList, userId);
            }
            msg = "success";
        } else {
            msg = "NoHost";
        }
        if (null != msg && "success".equals(msg)) {
            List<PdaHandOverCurrency> handList = pdaHandOverCurrencyDao.findAllPdaHandOverCurrencyByUserId(userId, ouId, new BeanPropertyRowMapperExt<PdaHandOverCurrency>(PdaHandOverCurrency.class));
            for (PdaHandOverCurrency p : handList) {
                PdaHandOverLog phol = new PdaHandOverLog();
                phol.setIsPreSale(0);
                phol.setLpcode(p.getLpcode());
                phol.setPackageInfoId(p.getPackageInfoId());
                phol.setTrackingNo(p.getTrackingNo());
                phol.setOuId(p.getOuId());
                phol.setUserId(p.getUserId());
                phol.setStatus(5);
                phol.setCreateTime(new Date());
                pdaHandOverLogDao.save(phol);
            }
            pdaHandOverCurrencyDao.deletePdaHandOverCurrency(userId, ouId);
        }
        return msg;
    }

    public String isShelfSku(Long ouId, String skuBarCode) {
        String msg = null;
        Warehouse warehouse = warehouseDao.getByOuId(ouId);
        if (null != warehouse) {
            Sku sku = skuDao.getByBarcode(skuBarCode, warehouse.getCustomer().getId());
            if (null != sku && InboundStoreMode.SHELF_MANAGEMENT.equals(sku.getStoremode())) {
                msg = "ture";
            }
        } else {
            msg = "error";
        }
        return msg;
    }

    public String findSkuExpireDate(String skuBarCode, Long ouId, String locationCode) {
        String msg = null;
        Warehouse warehouse = warehouseDao.getByOuId(ouId);
        if (null != warehouse) {
            Sku sku = skuDao.getByBarcode(skuBarCode, warehouse.getCustomer().getId());
            if (null != sku) {
                WarehouseLocation warehouseLocation = warehouseLocationDao.findLocationByCode(locationCode, ouId);
                List<InventoryCommand> list = inventoryDao.findInvExpireDateQtyBySkuIdAndLocationId(sku.getId(), warehouseLocation.getId(), ouId, new BeanPropertyRowMapper<InventoryCommand>(InventoryCommand.class));
                if (null != list && list.size() > 0) {
                    StringBuffer owner = new StringBuffer();
                    for (InventoryCommand l : list) {
                        owner.append(l.getExpireDate() + ",");
                    }
                    msg = owner.substring(0, owner.length() - 1);
                }
            }
        }
        return msg;
    }

    public String pdaFindSku(String skuBarCode, Long ouId, String locationCode, String statusName, String owner) {
        String msg = null;
        Warehouse warehouse = warehouseDao.getByOuId(ouId);
        if (null != warehouse) {
            Sku sku = skuDao.getByBarcode(skuBarCode, warehouse.getCustomer().getId());
            if (sku == null) {
                List<SkuBarcodeCommand> list = skuBarcodeDao.findByBarcode1(skuBarCode, warehouse.getCustomer().getId(), new BeanPropertyRowMapper<SkuBarcodeCommand>(SkuBarcodeCommand.class));
                if (null != list && list.size() > 0) {
                    sku = skuDao.getByPrimaryKey(list.get(0).getSkuId());
                }
            }
            if (null != sku) {
                OperationUnit ou1 = ouDao.getByPrimaryKey(ouId);
                ou1 = ouDao.getByPrimaryKey(ou1.getParentUnit().getId());
                InventoryStatus invetoryStatus = inventoryStatusDao.findInvStatusisByOuId(ou1.getParentUnit().getId(), statusName);
                WarehouseLocation warehouseLocation = warehouseLocationDao.findLocationByCode(locationCode, ouId);
                BiChannel c = biChannelDao.getByName(owner);
                Long skuQty = inventoryDao.findInvQtyBySkuIdOwnerAndLocationId(sku.getId(), warehouseLocation.getId(), ouId, c.getCode(), invetoryStatus.getId(), new SingleColumnRowMapper<Long>(Long.class));
                Long totalQty = inventoryDao.findInvQtyBySkuIdOwnerAndLocationIdAll(sku.getId(), warehouseLocation.getId(), ouId, c.getCode(), invetoryStatus.getId(), new SingleColumnRowMapper<Long>(Long.class));
                msg = sku.getSupplierCode() + "    " + sku.getSkuSize() + "-:" + warehouseLocation.getCode() + "-:" + skuQty.toString() + "-:" + totalQty;
            }
        }
        return msg;
    }

    public String checkSkuBarCode(String skuBarCode, Long ouId, String locationCode) {
        String msg = null;
        Warehouse warehouse = warehouseDao.getByOuId(ouId);
        if (null != warehouse) {
            Sku sku = skuDao.getByBarcode(skuBarCode, warehouse.getCustomer().getId());
            if (sku == null) {
                List<SkuBarcodeCommand> list = skuBarcodeDao.findByBarcode1(skuBarCode, warehouse.getCustomer().getId(), new BeanPropertyRowMapper<SkuBarcodeCommand>(SkuBarcodeCommand.class));
                if (null != list && list.size() > 0) {
                    sku = skuDao.getByPrimaryKey(list.get(0).getSkuId());
                }
            }
            if (null != sku) {
                WarehouseLocation warehouseLocation = warehouseLocationDao.findLocationByCode(locationCode, ouId);
                List<InventoryCommand> list = inventoryDao.findInvQtyBySkuIdAndLocationId(sku.getId(), warehouseLocation.getId(), ouId, new BeanPropertyRowMapper<InventoryCommand>(InventoryCommand.class));
                if (null != list && list.size() > 0) {
                    StringBuffer owner = new StringBuffer();
                    for (InventoryCommand inventoryList : list) {
                        owner.append(inventoryList.getOwner() + ":" + inventoryList.getInventoryStatusName() + ",");
                    }
                    msg = "success" + "-:" + owner.substring(0, owner.length() - 1).toString();
                } else {
                    msg = "NoQty";
                }
            } else {
                msg = "NoSku";
            }
        } else {
            msg = "false";
        }
        return msg;
    }

    public Boolean checkLocation(String locationCoce, Long ouId) {
        WarehouseLocation warehouseLocation = warehouseLocationDao.findLocationByCode(locationCoce, ouId);
        if (null != warehouseLocation) {
            return true;
        } else {
            return false;
        }
    }

    public String pdaOutBoundHandNum(Long userId, Long ouId) {
        String msg = "0";
        Long msg1 = pdaHandOverDao.pdaOutBoundHandNum(userId, ouId, new SingleColumnRowMapper<Long>(Long.class));
        if (null != msg1) {
            msg = msg1.toString();
        }
        return msg;
    }

    public String pdaOutBoundHandCurrencyNum(Long userId, Long ouId) {
        String msg = "0";
        Long msg1 = pdaHandOverCurrencyDao.pdaOutBoundHandCurrencyNum(userId, ouId, new SingleColumnRowMapper<Long>(Long.class));
        if (null != msg1) {
            msg = msg1.toString();
        }
        return msg;
    }

    public String findChooseOption() {
        return chooseOptionDao.findByCategoryCodeAndKey("pdaHandOver", "num").getOptionValue();
    }

    /*
     * public String pdaExecuteTransitInner(String skuBarCode, Long ouId, String locationCode,
     * String statusName, String owner, String skuNum) { String msg = null; Warehouse warehouse =
     * warehouseDao.getByOuId(ouId); if (null != warehouse) { Sku sku =
     * skuDao.getByBarcode(skuBarCode, warehouse.getCustomer().getId()); if (null != sku) {
     * WarehouseLocation warehouseLocation = warehouseLocationDao.findLocationByCode(locationCode,
     * ouId); List<InventoryCommand> list = inventoryDao.findInvQtyBySkuIdAndLocationId(sku.getId(),
     * warehouseLocation.getId(), ouId, new
     * BeanPropertyRowMapper<InventoryCommand>(InventoryCommand.class)); if (null != list &&
     * list.size() > 0) {} } }
     * 
     * }
     */

    /**
     * 按箱收货（扫标签）2
     */
    // @Override
    // public Long checkTag(String staCode, String tag, OperationUnit op) {
    // StockTransApplication sta1 = staDao.findStaBySlipCodeOrCode(staCode,
    // op.getId(), new
    // BeanPropertyRowMapperExt<StockTransApplication>(StockTransApplication.class));
    // if (sta1 == null) {
    // throw new BusinessException("没有该作业单");
    // }
    // List<StockTransApplication> staList =
    // staDao.getChildStaByGroupId(sta1.getId());
    // if (null != staList && staList.size() > 0) {// 主作业单
    // throw new BusinessException("该作业单为主作业单");
    // }
    // if (StockTransApplicationStatus.FINISHED.equals(sta1.getStatus())) {
    // throw new BusinessException("该作业单已经完成");
    // }
    // boolean flag = isAutoWh(op.getId());
    // if (!flag) {
    // throw new BusinessException("此单不是自动化仓的");
    // }
    // StaCarton c = staCartonDao.getStaCartonByCode(sta1.getRefSlipCode(),
    // op.getId(), new
    // BeanPropertyRowMapperExt<StaCarton>(StaCarton.class));
    // if (c == null) {
    // throw new BusinessException("此箱还没有收货,请先收货");
    // }
    // AsnShelves asnShelves = mongoOperation.findOne(new
    // Query(Criteria.where("cartonCode").is(sta1.getRefSlipCode()).andOperator(Criteria.where("ouId").is(op.getId()))).with(new
    // Sort(Direction.ASC, "createTime")), AsnShelves.class);
    // if (asnShelves != null) {
    // throw new BusinessException("此箱已经加入上架不允许操作");
    // }
    // // 验证该作业单是否已经加入上架 如果加入上架不许修改 查询日志表
    // List<StaOpDetailCommand> ls =
    // staOpDetailDao.StaOpDetailByCarId(sta1.getId(), c.getId(),
    // op.getId(), new
    // BeanPropertyRowMapperExt<StaOpDetailCommand>(StaOpDetailCommand.class));
    // if (ls.size() > 0) {
    // throw new BusinessException("此箱已经加入上架不允许操作");
    // }
    // List<StaOpLogCommand2> ls2 = staOpLogDao.queryStaOpLog(sta1.getId(),
    // c.getId(), op.getId(),
    // new BeanPropertyRowMapperExt<StaOpLogCommand2>(StaOpLogCommand2.class));
    // if (ls2.size() > 0) {
    // throw new BusinessException("此箱已经加入上架不允许操作");
    // }
    // // 验证标签 是否正使用
    // WhContainer whc = whContainerDao.getWhContainerByCode(tag);
    // if (whc != null) {
    // if (DefaultStatus.CREATED.equals(whc.getStatus())) {} else {
    // throw new BusinessException("此标签正在被使用");
    // }
    // }
    // StaCarton c2 = staCartonDao.getStaCartonByCode(tag, op.getId(), new
    // BeanPropertyRowMapperExt<StaCarton>(StaCarton.class));
    // if (c2 != null) {
    // throw new BusinessException("此标签正在被使用");
    // }
    // int i = staCartonDao.updateTagByCode(tag, staCode, c.getId());
    // log.info("checkTag:" + tag + "," + staCode + "," + c.getId() + "," + i);
    // return c.getId();
    // }

    @Override
    public Boolean checkAdTrackingNo(String trackingNo) {
        Boolean b = false;
        PackageInfo packageInfo = packageInfoDao.checkAdTrackingNo(trackingNo, new BeanPropertyRowMapperExt<PackageInfo>(PackageInfo.class));
        if (packageInfo != null) {
            b = true;
        }
        return b;
    }

    @Override
    public List<InventoryCommand> getInventoryByBarCodeOrLocation(String barCode, String locationCode, Long ouId) {
        List<InventoryCommand> list = new ArrayList<InventoryCommand>();
        if ("".equals(barCode)) {
            barCode = null;
        }
        if ("".equals(locationCode)) {
            locationCode = null;
        }
        Sku sku = null;
        if (null != barCode && !"".equals(barCode)) {
            Warehouse warehouse = warehouseDao.getByOuId(ouId);
            sku = skuDao.getByBarcode(barCode, warehouse.getCustomer().getId());
            if (sku == null) {
                List<SkuBarcodeCommand> skuList = skuBarcodeDao.findByBarcode1(barCode, warehouse.getCustomer().getId(), new BeanPropertyRowMapper<SkuBarcodeCommand>(SkuBarcodeCommand.class));
                if (skuList != null && skuList.size() > 0) {
                    sku = skuDao.getByPrimaryKey(skuList.get(0).getSkuId());
                }
            }
        }
        if (sku == null) {
            list = inventoryDao.getInventoryForPdaMorebarCode(locationCode, null, ouId + "", new BeanPropertyRowMapper<InventoryCommand>(InventoryCommand.class));
        } else {
            list = inventoryDao.getInventoryForPdaMorebarCode(locationCode, sku.getId(), ouId + "", new BeanPropertyRowMapper<InventoryCommand>(InventoryCommand.class));
        }
        return list;
    }

    /**
     * 根据作业单校验商品三维信息
     * 
     * @param sta
     * @return
     */
    public String verifySkuThreeDimensional(StockTransApplication sta, String staCode, Long ouId, Boolean isVerifyMdb) {
        if (sta == null) {
            sta = staDao.findStaBySlipCodeOrCode(staCode, ouId, new BeanPropertyRowMapperExt<StockTransApplication>(StockTransApplication.class));
            if (null == sta) {
                List<ChannelWhRef> cwrList = channelWhRefRefDao.getChannelRefByOuid(ouId);
                if (cwrList != null) {
                    for (ChannelWhRef cwr : cwrList) {
                        if (cwr.getReceivePrefix() != null && !"".equals(cwr.getReceivePrefix())) {
                            sta = staDao.findStaBySlipCodeOrCode(cwr.getReceivePrefix() + staCode, ouId, new BeanPropertyRowMapperExt<StockTransApplication>(StockTransApplication.class));
                            if (sta != null) {
                                staCode = cwr.getReceivePrefix() + staCode;
                                break;
                            }
                        }
                    }
                }
            }
            if (sta == null) {
                throw new BusinessException("没有该作业单或箱号");
            }
        }

        Warehouse wh = warehouseDao.getByOuId(ouId == null ? sta.getMainWarehouse().getId() : ouId);
        if (wh == null || wh.getIsSkipWeight() == null || !wh.getIsSkipWeight()) {
            return null;
        }

        InboundSkuWeightCheck iswc = mongoOperation.findOne(new Query(Criteria.where("id").is(sta.getId())), InboundSkuWeightCheck.class);
        if (iswc == null) {
            List<Sku> skuList = skuDao.findSkuThreeDimensionalIsNullByStaId(sta.getId(), new BeanPropertyRowMapper<Sku>(Sku.class));
            if (skuList != null && skuList.size() > 0) {
                iswc = new InboundSkuWeightCheck();
                iswc.setId(sta.getId());
                // iswc.setSkuCode(sku.getCode());
                iswc.setStaId(sta.getId());
                iswc.setOuId(ouId == null ? sta.getMainWarehouse().getId() : ouId);
                iswc.setCreateTime(new Date());
                List<SkuCommand> skuListS = new ArrayList<SkuCommand>();

                boolean b = false;

                for (Sku sku : skuList) {
                    SkuCommand sku1 = new SkuCommand();
                    sku1.setId(sku.getId());
                    sku1.setCode(sku.getCode());
                    sku1.setBarCode(sku.getBarCode());
                    sku1.setIsSendMsg(0);
                    skuListS.add(sku1);

                    InboundSkuWeightCheck iswcSku = mongoOperation.findOne(new Query(Criteria.where("skuList.id").is(sku.getId())), InboundSkuWeightCheck.class);
                    if (iswcSku == null) {
                        b = true;
                    }

                }
                iswc.setSkuList(skuListS);
                mongoOperation.save(iswc);

                if (b) {
                    return "该作业单缺失三维信息";
                }
            }
        }
        return null;
    }


    /**
     * 根据作业单校验商品三维信息
     * 
     * @param sta
     * @return
     */
    public String verifySkuThreeDimensionalBySta(StockTransApplication sta, String staCode, Long ouId) {
        if (sta == null) {
            sta = staDao.findStaBySlipCodeOrCode(staCode, ouId, new BeanPropertyRowMapperExt<StockTransApplication>(StockTransApplication.class));
            if (null == sta) {
                List<ChannelWhRef> cwrList = channelWhRefRefDao.getChannelRefByOuid(ouId);
                if (cwrList != null) {
                    for (ChannelWhRef cwr : cwrList) {
                        if (cwr.getReceivePrefix() != null && !"".equals(cwr.getReceivePrefix())) {
                            sta = staDao.findStaBySlipCodeOrCode(cwr.getReceivePrefix() + staCode, ouId, new BeanPropertyRowMapperExt<StockTransApplication>(StockTransApplication.class));
                            if (sta != null) {
                                staCode = cwr.getReceivePrefix() + staCode;
                                break;
                            }
                        }
                    }
                }
            }
            if (sta == null) {
                throw new BusinessException("没有该作业单或箱号");
            }
        }

        Warehouse wh = warehouseDao.getByOuId(ouId == null ? sta.getMainWarehouse().getId() : ouId);
        if (wh == null || wh.getIsSkipWeight() == null || !wh.getIsSkipWeight()) {
            return null;
        }

        InboundSkuWeightCheck iswc = mongoOperation.findOne(new Query(Criteria.where("id").is(sta.getId())), InboundSkuWeightCheck.class);
        if (iswc == null) {
            List<Sku> skuList = skuDao.findSkuThreeDimensionalIsNullByStaId(sta.getId(), new BeanPropertyRowMapper<Sku>(Sku.class));
            if (skuList != null && skuList.size() > 0) {
                iswc = new InboundSkuWeightCheck();
                iswc.setId(sta.getId());
                // iswc.setSkuCode(sku.getCode());
                iswc.setStaId(sta.getId());
                iswc.setOuId(ouId == null ? sta.getMainWarehouse().getId() : ouId);
                iswc.setCreateTime(new Date());
                List<SkuCommand> skuListS = new ArrayList<SkuCommand>();
                for (Sku sku : skuList) {
                    SkuCommand sku1 = new SkuCommand();
                    sku1.setId(sku.getId());
                    sku1.setCode(sku.getCode());
                    sku1.setBarCode(sku.getBarCode());
                    sku1.setIsSendMsg(0);
                    skuListS.add(sku1);
                }
                iswc.setSkuList(skuListS);
                mongoOperation.save(iswc);
                return "该作业单缺失三维信息";
            }
        }
        return null;
    }
    /**
     * 根据商品校验商品三维信息
     * @param staCode
     * @param ouId
     * @param barCode
     * @return
     */
    public String verifySkuThreeDimensionalBySku(String staCode,Long ouId, String barCode) {

        Warehouse wh = warehouseDao.getByOuId(ouId);
        if (wh == null || wh.getIsSkipWeight() == null || !wh.getIsSkipWeight()) {
            return null;
        }

        List<AsnReceive> asnReceiveList = mongoOperation.find(new Query(Criteria.where("code").is(staCode)).with(new Sort(Direction.ASC, "createTime")), AsnReceive.class);
        if (null == asnReceiveList || asnReceiveList.size() == 0) {
            throw new BusinessException(ErrorCode.PDA_CODE_NOT_FOUND);
        }
        if (null == barCode || "".equals(barCode)) {
            throw new BusinessException(ErrorCode.PDA_CODE_NOT_FOUND);
        }
        List<OrderLine> orderLineList = null;
        Set<String> barCodeset = null;
        Long skuId = null;
        for (AsnReceive asnReceive : asnReceiveList) {
            orderLineList = asnReceive.getOrderLine();
            for (OrderLine orderLine : orderLineList) {
                barCodeset = orderLine.getSkubarcode();
                for (String s : barCodeset) {
                    if (s.equals(barCode)) {
                        skuId = orderLine.getSkuId();
                        break;
                    }
                }
                if (skuId != null) {
                    break;
                }
            }
            if (skuId != null) {
                break;
            }
        }

        InboundSkuWeightCheck isw = mongoOperation.findOne(new Query(Criteria.where("skuList.id").is(skuId)), InboundSkuWeightCheck.class);
        if (isw != null) {

            InboundSkuWeightCheck iswc = mongoOperation.findOne(new Query(Criteria.where("skuList.id").is(skuId).and("skuList.isSendMsg").is(1)), InboundSkuWeightCheck.class);
            if (iswc == null) {
                // mongoOperation.updateMulti(new
                // Query(Criteria.where("skuList.id").is(skuId).and("skuList.isSendMsg").is(0)), new
                // Update().inc("skuList.isSendMsg", 1), InboundSkuWeightCheck.class);
                for (SkuCommand sc : isw.getSkuList()) {
                    if (sc.getId().intValue() == skuId.intValue()) {
                        sc.setIsSendMsg(1);
                        mongoOperation.save(isw);
                        break;
                    }
                }

                return "SkuThreeDimensional";
            }
        }

        return null;
    }

    @Override
    public String trucking(String licensePlateNumber, String trackingNo) {
        String msg = "";
        CustomsDeclaration customsDeclaration = customsDeclarationDao.queryCustomsDeclaration(trackingNo, new BeanPropertyRowMapper<CustomsDeclaration>(CustomsDeclaration.class));
        customsDeclaration = customsDeclarationDao.getByPrimaryKey(customsDeclaration.getId());
        if (licensePlateNumber.equals(customsDeclaration.getLicensePlateNumber())) {
            customsDeclaration.setIsLoading(1);
            customsDeclarationDao.save(customsDeclaration);
            msg = "success";
        } else {
            msg = customsDeclaration.getLicensePlateNumber();

        }

        return msg;
    }

    @Override
    public String truckingConfirm(String licensePlateNumber, String trackingNo) {
        String msg = "";
        CustomsDeclaration customsDeclaration = customsDeclarationDao.queryCustomsDeclaration(trackingNo, new BeanPropertyRowMapper<CustomsDeclaration>(CustomsDeclaration.class));
        customsDeclaration = customsDeclarationDao.getByPrimaryKey(customsDeclaration.getId());
        if (customsDeclaration.getIsLoading() == 1) {
            msg = "车牌号:" + customsDeclaration.getLicensePlateNumber() + "已装车 请重新选择车牌号";
        } else {
            customsDeclaration.setLicensePlateNumber(licensePlateNumber);
            customsDeclaration.setStatus(1);
            customsDeclaration.setIsLoading(1);
            customsDeclarationDao.save(customsDeclaration);
            msg = "success";
        }

        return msg;
    }
}
