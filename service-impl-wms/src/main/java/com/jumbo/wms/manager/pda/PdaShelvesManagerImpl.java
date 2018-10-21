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

import loxia.dao.support.BeanPropertyRowMapperExt;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.dao.authorization.UserDao;
import com.jumbo.dao.automaticEquipment.WhContainerDao;
import com.jumbo.dao.baseinfo.ChannelWhRefRefDao;
import com.jumbo.dao.baseinfo.SkuBarcodeDao;
import com.jumbo.dao.baseinfo.SkuDao;
import com.jumbo.dao.pda.PdaStaShelvesPlanDao;
import com.jumbo.dao.pda.StaCartonDao;
import com.jumbo.dao.pda.StaCartonLineDao;
import com.jumbo.dao.pda.StaCartonLineSnDao;
import com.jumbo.dao.pda.StaOpDetailDao;
import com.jumbo.dao.pda.StaOpLogDao;
import com.jumbo.dao.warehouse.BiChannelDao;
import com.jumbo.dao.warehouse.InventoryDao;
import com.jumbo.dao.warehouse.InventoryStatusDao;
import com.jumbo.dao.warehouse.QsSkuDao;
import com.jumbo.dao.warehouse.SkuSnDao;
import com.jumbo.dao.warehouse.SkuSnLogDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.dao.warehouse.WarehouseLocationDao;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.warehouse.AutoOutboundTurnboxManager;
import com.jumbo.wms.model.DefaultStatus;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.authorization.User;
import com.jumbo.wms.model.automaticEquipment.WhContainer;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.baseinfo.ChannelWhRef;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.baseinfo.SkuBarcode;
import com.jumbo.wms.model.mongodb.AsnShelves;
import com.jumbo.wms.model.mongodb.ShelvesCartonLine;
import com.jumbo.wms.model.mongodb.StaCarton;
import com.jumbo.wms.model.mongodb.StaCartonLine;
import com.jumbo.wms.model.mongodb.StaCartonLineCommand;
import com.jumbo.wms.model.mongodb.StaCartonLineSn;
import com.jumbo.wms.model.mongodb.StaCartonLineSnCommand;
import com.jumbo.wms.model.pda.PdaStaShelvesPlan;
import com.jumbo.wms.model.pda.StaOpDetail;
import com.jumbo.wms.model.pda.StaOpLog;
import com.jumbo.wms.model.pda.StaOpLogCommand2;
import com.jumbo.wms.model.warehouse.InventoryCommand;
import com.jumbo.wms.model.warehouse.InventoryStatus;
import com.jumbo.wms.model.warehouse.QsSkuCommand;
import com.jumbo.wms.model.warehouse.SkuSnCommand;
import com.jumbo.wms.model.warehouse.SkuSnLog;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StvLineCommand;
import com.jumbo.wms.model.warehouse.TransactionDirection;
import com.jumbo.wms.model.warehouse.WarehouseLocation;

@Transactional
@Service("pdaShelvesManager")
public class PdaShelvesManagerImpl extends BaseManagerImpl implements PdaShelvesManager {

    /**
     * 
     */
    private static final long serialVersionUID = -772296421955900233L;
    /**
     * 
     */

    @Resource(name = "mongoTemplate")
    private MongoOperations mongoOperation;
    @Autowired
    private StaCartonLineDao staCartonLineDao;
    @Autowired
    private StaCartonDao staCartonDao;
    @Autowired
    private PdaStaShelvesPlanDao pdaStaShelvesPlanDao;
    @Autowired
    private SkuBarcodeDao skuBarcodeDao;
    @Autowired
    private StaOpDetailDao staOpDetailDao;
    @Autowired
    private com.jumbo.wms.manager.warehouse.WareHouseManagerExecute WareHouseManagerExecute;
    @Autowired
    private StaOpLogDao staOpLogDao;
    @Autowired
    private StaCartonLineSnDao staCartonLineSnDao;
    @Autowired
    private WarehouseLocationDao warehouseLocationDao;
    @Autowired
    private PdaReceiveManager pdaReceiveManager;
    @Autowired
    private SkuSnDao skuSnDao;
    @Autowired
    private SkuSnLogDao skuSnLogDao;
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private WhContainerDao whContainerDao;
    @Autowired
    private AutoOutboundTurnboxManager autoOutboundTurnboxManager;
    @Autowired
    private SkuDao skuDao;
    @Autowired
    private InventoryStatusDao inventoryStatusDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private ChannelWhRefRefDao channelWhRefRefDao;
    @Autowired
    private QsSkuDao qsSkuDao;
    @Autowired
    private BiChannelDao biChannelDao;
    @Autowired
    private InventoryDao inventoryDao;

    /**
     * 上架初始化缓存（上架计划容器等相关信息）残次品
     */
    @Override
    public void initMongodbWhShelvesInfoIncomplete(String code, OperationUnit op, User user) {
        // AsnShelves asnShelve = mongoOperation.findOne(new
        // Query(Criteria.where("cartonCode").is(code).andOperator(Criteria.where("ouId").is(op.getId()))).with(new
        // Sort(Direction.ASC, "createTime")), AsnShelves.class);
        AsnShelves asnShelve = mongoOperation.findOne(new Query(Criteria.where("cartonCode").is(code).and("ouId").is(op.getId())).with(new Sort(Direction.ASC, "createTime")), AsnShelves.class);

        if (asnShelve == null) {// 缓存没有 初始化缓存
            StaCarton carton = staCartonDao.getStaCartonByCode(code, op.getId(), new BeanPropertyRowMapperExt<StaCarton>(StaCarton.class));
            if (null == carton) {
                throw new BusinessException("没有此箱号");
            }
            InventoryStatus s = inventoryStatusDao.getByPrimaryKey(carton.getInvStatusId());
            if (s == null) {
                throw new BusinessException("此仓库下库存状态设置有误");
            } else if (!"残次品".equals(s.getName())) {
                throw new BusinessException("此箱不是残次品");
            }
            // 根据箱号来获取箱明细list
            List<StaCartonLine> carTonLineList = staCartonLineDao.getCartonLineByCartonId(carton.getId());
            if (carTonLineList == null || carTonLineList.size() == 0) {
                throw new BusinessException("该箱号下没有明细,id:" + carton.getId());
            }
            AsnShelves asnShelves = new AsnShelves();
            List<ShelvesCartonLine> shelvesCartonLines = new ArrayList<ShelvesCartonLine>();
            for (StaCartonLine staCartonLine : carTonLineList) {
                asnShelves.setId(carton.getId());
                asnShelves.setCartonId(carton.getId());// 箱id
                asnShelves.setCartonCode(carton.getCode());// 箱号
                asnShelves.setOrderCode(carton.getSta().getCode());// 单据号
                asnShelves.setSlipCode(carton.getSta().getRefSlipCode());// 相关单据号
                asnShelves.setCreateTime(new Date());// 创建时间
                asnShelves.setOuId(carton.getWhOuId());// 仓库id
                asnShelves.setStaId(carton.getSta().getId());// 作业单id
                asnShelves.setUserId(user.getId());// 操作人id
                asnShelves.setInvStatusId(carton.getInvStatusId());// 库存状态
                asnShelves.setInvStatus(0L);// 残次品


                Set<String> skubarcode = new HashSet<String>();// 商品条码
                Set<String> sns = new HashSet<String>();// 商品sns
                Set<String> dmgBarcode = new HashSet<String>();// 残次条码列表

                ShelvesCartonLine shelvesCartonLine = new ShelvesCartonLine();// 明细
                // skubarcode.add(pdaStaShelvesPlan.getSku().getBarCode());
                List<SkuBarcode> sbs = skuBarcodeDao.findBySkuId(staCartonLine.getSkuId());
                if (sbs != null) {
                    for (SkuBarcode sb : sbs) {
                        skubarcode.add(sb.getBarcode());
                    }
                }
                List<StaCartonLineSn> snList = staCartonLineSnDao.getStaCartonLineSnByClId(staCartonLine.getId());
                if (snList != null) {
                    for (StaCartonLineSn staCartonLineSn : snList) {
                        dmgBarcode.add(staCartonLineSn.getDmgCode());
                    }
                }

                shelvesCartonLine.setSkubarcode(skubarcode);// 明细-barCodes
                shelvesCartonLine.setSns(sns);// 明细-sns
                shelvesCartonLine.setDmgBarcode(dmgBarcode);// 明细-dmgBarcodes
                shelvesCartonLine.setDmgBarcode2(dmgBarcode);// 明细-dmgBarcodes2原始
                shelvesCartonLine.setSkuId(staCartonLine.getSkuId());// 明细-商品id
                shelvesCartonLine.setQty(staCartonLine.getQty());// 明细-剩余执行量
                shelvesCartonLine.setRemainQty(staCartonLine.getQty());// 明细-总执行量
                if (staCartonLine.getExpDate() != null) {
                    Long l = staCartonLine.getExpDate().getTime();
                    shelvesCartonLine.setExpDate(new Date(l));// 明细-过期时间
                }

                // 明细-行是否完成
                shelvesCartonLine.setIsOver("0");
                shelvesCartonLines.add(shelvesCartonLine);
            }
            asnShelves.setCartonLines(shelvesCartonLines);
            mongoOperation.save(asnShelves);

        } else {
            /*
             * if (!(user.getId().toString()).equals(asnShelve.getUserId().toString())) { User u =
             * userDao.getByPrimaryKey(asnShelve.getUserId()); throw new
             * BusinessException("该容器被用户id:" + asnShelve.getUserId() + ",用户名:" + u.getUserName() +
             * ",正在使用"); } else
             */
            if (0L != asnShelve.getInvStatus()) {
                throw new BusinessException("此箱不是残次品");
            }
        }
    }


    public String checkLocIsInv(Long ouId, String locationCode) {
        String msg = "error";
        InventoryCommand c = inventoryDao.checkLocIsInv(ouId, locationCode, new BeanPropertyRowMapperExt<InventoryCommand>(InventoryCommand.class));
        if (null != c && null != c.getIsShowZero() && c.getIsShowZero()) {
            msg = "succuess";
        }
        return msg;
    }

    /**
     * 上架初始化缓存（上架计划容器等相关信息）良品
     */

    public Map<String, Object> initMongodbWhShelvesInfo(String code, OperationUnit op, User user) {
        Map<String, Object> map = new HashMap<String, Object>();
        String msgs = null;
        String tip = null;
        StaCarton carton = staCartonDao.getStaCartonByCode(code, op.getId(), new BeanPropertyRowMapperExt<StaCarton>(StaCarton.class));
        if (null == carton) {
            throw new BusinessException("没有此箱号");
        }

        List<StaOpLogCommand2> cs = staOpLogDao.queryStaOpLog(carton.getSta().getId(), carton.getId(), carton.getWhOuId(), new BeanPropertyRowMapperExt<StaOpLogCommand2>(StaOpLogCommand2.class));
        if ((5 == carton.getStatus().getValue()) && ((carton.getType() == null ? -1 : carton.getType()) == 1)) {
            if (cs.size() > 0) {
                throw new BusinessException("此箱号已经手工上架，不允许使用系统上架");
            } else {
                mongoOperation.remove(new Query(Criteria.where("cartonCode").is(carton.getCode()).andOperator(Criteria.where("ouId").is(op.getId()))), AsnShelves.class);
            }
        }
        // AsnShelves asnShelve = mongoOperation.findOne(new
        // Query(Criteria.where("cartonCode").is(code).andOperator(Criteria.where("ouId").is(op.getId()))).with(new
        // Sort(Direction.ASC, "createTime")), AsnShelves.class);
        AsnShelves asnShelve = mongoOperation.findOne(new Query(Criteria.where("cartonCode").is(code).and("ouId").is(op.getId())).with(new Sort(Direction.ASC, "createTime")), AsnShelves.class);

        if (asnShelve == null) {// 缓存没有 初始化缓存
            // StaCarton carton = staCartonDao.getStaCartonByCode(code, op.getId(), new
            // BeanPropertyRowMapperExt<StaCarton>(StaCarton.class));
            // if (null == carton) {
            // throw new BusinessException("没有此箱号");
            // }
            InventoryStatus s = inventoryStatusDao.getByPrimaryKey(carton.getInvStatusId());
            if (s == null) {
                throw new BusinessException("此仓库下库存状态设置有误");
            } else if (!"良品".equals(s.getName())) {
                throw new BusinessException("此箱不是良品");
            }
            msgs = locationRecommendShelves(carton.getId(), op);// 推荐
            List<PdaStaShelvesPlan> planList = pdaStaShelvesPlanDao.getByCidByName2(carton.getId());
            if (planList == null || planList.size() == 0) {
                throw new BusinessException("上架推荐都没有成功,请重新维护参数!");
            }
            tip = getBarCodeShelvesCartonLineByCarId(carton.getId());// 未推荐成功明细
            List<ShelvesCartonLine> shelvesCartonLines = new ArrayList<ShelvesCartonLine>();
            AsnShelves asnShelves = null;
            for (PdaStaShelvesPlan pdaStaShelvesPlan : planList) {
                asnShelves = initAsnShelves(pdaStaShelvesPlan, op, user, carton);// 封装AsnShelves
                ShelvesCartonLine shelvesCartonLine = initShelvesCartonLine(pdaStaShelvesPlan);// 封装initShelvesCartonLine
                shelvesCartonLines.add(shelvesCartonLine);
                // 设置推荐计划状态为1(上架但未完成)
                pdaStaShelvesPlan.setStatus(1L);
                pdaStaShelvesPlanDao.save(pdaStaShelvesPlan);

            }
            asnShelves.setCartonLines(shelvesCartonLines);
            mongoOperation.save(asnShelves);
            map.put("tip", tip);
            map.put("msgs", msgs);
            return map;
        } else {
            /*
             * if (!(user.getId().toString()).equals(asnShelve.getUserId().toString())) { User u =
             * userDao.getByPrimaryKey(asnShelve.getUserId()); throw new
             * BusinessException("该容器被用户id:" + asnShelve.getUserId() + ",用户名:" + u.getUserName() +
             * ",正在使用"); } else
             */
            if (1L != asnShelve.getInvStatus()) {
                throw new BusinessException("此箱不是良品");
            } else {// 再次把推荐的加载到缓存里
                // StaCarton carton = staCartonDao.getStaCartonByCode(code, op.getId(), new
                // BeanPropertyRowMapperExt<StaCarton>(StaCarton.class));
                // if (null == carton) {
                // throw new BusinessException("没有此箱号");
                // }
                msgs = locationRecommendShelves(carton.getId(), op);// 推荐
                tip = getBarCodeShelvesCartonLineByCarId(carton.getId());// 未推荐成功明细
                List<PdaStaShelvesPlan> planList = pdaStaShelvesPlanDao.getByCidByName(carton.getId());
                List<ShelvesCartonLine> shelvesCartonLines = new ArrayList<ShelvesCartonLine>();
                for (PdaStaShelvesPlan pdaStaShelvesPlan : planList) {
                    ShelvesCartonLine shelvesCartonLine = initShelvesCartonLine(pdaStaShelvesPlan);// 封装initShelvesCartonLine
                    shelvesCartonLines.add(shelvesCartonLine);
                    pdaStaShelvesPlan.setStatus(1L); // 设置推荐计划状态为1(上架但未完成)
                    pdaStaShelvesPlanDao.save(pdaStaShelvesPlan);
                    asnShelve.getCartonLines().add(shelvesCartonLine);
                    // asnShelve.setCartonLines(shelvesCartonLines);
                    mongoOperation.save(asnShelve);
                }
            }
            map.put("tip", tip);
            map.put("msgs", msgs);
            return map;
        }
    }



    @Override
    public Map<String, Object> verifyAndRecommend(String barCode, String code, OperationUnit op, User user) {
        Map<String, Object> map = new HashMap<String, Object>();
        Boolean flag = false;
        // AsnShelves asnShelves = mongoOperation.findOne(new
        // Query(Criteria.where("cartonCode").is(code).andOperator(Criteria.where("ouId").is(op.getId()))).with(new
        // Sort(Direction.ASC, "createTime")), AsnShelves.class);
        AsnShelves asnShelves = mongoOperation.findOne(new Query(Criteria.where("cartonCode").is(code).and("ouId").is(op.getId())).with(new Sort(Direction.ASC, "createTime")), AsnShelves.class);

        if (asnShelves == null) {
            throw new BusinessException("该容器号不存在");
        }
        List<ShelvesCartonLine> CartonLines = asnShelves.getCartonLines();

        for (ShelvesCartonLine shelvesCartonLine : CartonLines) {
            if (shelvesCartonLine.getSkubarcode().contains(barCode)) {// 判断该容器下是否有该skuBarcode
                if ("1".equals(shelvesCartonLine.getIsOver())) {
                    log.error("该容器：" + code + ",skuId,locationId:" + shelvesCartonLine.getSkuId() + "," + shelvesCartonLine.getLocationId() + ",已经上架");
                    continue;
                } else {
                    map.put("line", shelvesCartonLine);// 推荐库位
                    flag = true;
                    break;
                }
            }
        }
        if (!flag) {// 该容器下没有该商品
            throw new BusinessException("该容器下没有该商品或已经推荐上架完成");
        }
        map.put("barCode", barCode);// 商品编码
        return map;
    }

    /**
     * 良品逐渐
     */
    @Override
    public Map<String, Object> scanSku(String barCode, String code, String locationCode, OperationUnit op, User user) throws Exception {// 扫描商品
        Map<String, Object> map = new HashMap<String, Object>();
        Boolean flag = false;
        // AsnShelves asnShelves = mongoOperation.findOne(new
        // Query(Criteria.where("cartonCode").is(code).andOperator(Criteria.where("ouId").is(op.getId()))).with(new
        // Sort(Direction.ASC, "createTime")), AsnShelves.class);
        AsnShelves asnShelves = mongoOperation.findOne(new Query(Criteria.where("cartonCode").is(code).and("ouId").is(op.getId())).with(new Sort(Direction.ASC, "createTime")), AsnShelves.class);

        if (asnShelves == null) {
            throw new BusinessException("该容器号不存在");
        }
        List<ShelvesCartonLine> CartonLines = asnShelves.getCartonLines();
        int i = 0;// 判断sku和locationCode是否唯一
        int j = 0;
        map.put("isOver", 1);
        for (ShelvesCartonLine shelvesCartonLine : CartonLines) {
            if (shelvesCartonLine.getSkubarcode().contains(barCode) && locationCode.equals(shelvesCartonLine.getLocationCode())) {// 判断该容器下是否有该skuBarcode和locationCode
                flag = true;
                if ("1".equals(shelvesCartonLine.getIsOver())) {
                    throw new BusinessException("已经扫描完,不可以在扫描");
                } else {
                    i++;
                    shelvesCartonLine.setRemainQty(shelvesCartonLine.getRemainQty() - 1);// 扣减
                    map.put("line", shelvesCartonLine);// 推荐库位
                    if (shelvesCartonLine.getQty() == (shelvesCartonLine.getQty() - shelvesCartonLine.getRemainQty())) {// 自动该line上架完成
                        shelvesCartonLine.setIsOver("1");
                        pdaStaShelvesPlanDao.updatePlanOver(shelvesCartonLine.getPlanId(), null, 2L);// 上架完成
                        j++;
                    }
                    // 插入数据库记录操作明细
                    StaOpDetail opDetail = new StaOpDetail();
                    opDetail.setCartonCode(asnShelves.getCartonCode());// 箱号
                    opDetail.setCreateTime(new Date());// 操作时间
                    opDetail.setDmgCode("");// 残次条码
                    opDetail.setDmgReason("");// 残次原因
                    opDetail.setDmgType("");// 残次类型
                    opDetail.setExpDate(shelvesCartonLine.getExpDate());// 过期时间
                    opDetail.setInvStatusId(asnShelves.getInvStatusId());// 库存状态
                    opDetail.setLocationCode(shelvesCartonLine.getLocationCode());// 库位编码
                    opDetail.setQty(1L);// 数量
                    opDetail.setSkuId(shelvesCartonLine.getSkuId());// 商品id
                    opDetail.setSn(null);// 设置sn
                    opDetail.setStaId(asnShelves.getStaId());// 作业单id
                    opDetail.setType(2);// 2:上架
                    opDetail.setUserId(user.getId());
                    opDetail.setWhOuId(op.getId());
                    opDetail.setCarId(asnShelves.getCartonId());// 箱id
                    staOpDetailDao.save(opDetail);
                    staOpDetailDao.flush();
                }
            }

            if ("0".equals(shelvesCartonLine.getIsOver())) {// 判断line是否全部上架完成
                map.put("isOver", 0);
            }
        }
        if (i > 1) {
            throw new BusinessException("sku和locationCode不唯一");
        }
        if (!flag) {// 该容器下没有该商品
            throw new BusinessException("该容器推荐的库位下没有该商品");
        }
        // 判断整箱是否已经已经上架完成
        if ("1".equals(map.get("isOver").toString())) {// 上架完成
            shouHuoAndshelves(asnShelves, op, user, true);// 收货和上架
        } else if (j > 0) {
            shouHuoAndshelves(asnShelves, op, user, false);// 收货和上架
        } else {
            mongoOperation.save(asnShelves);
        }
        return map;
    }

    private void shouHuoAndshelves(AsnShelves asnShelves, OperationUnit op, User user, Boolean flag) throws Exception {// 收货and上架
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyyMMdd");
        List<StvLineCommand> list = new ArrayList<StvLineCommand>();// 收货明细list
        List<StvLineCommand> list2 = new ArrayList<StvLineCommand>();// 上架明细list
        // 残次品
        // List<StaOpDetail> staOpDtailList =
        // staOpDetailDao.findByNoGoodsOpDetail(asnShelves.getStaId(), asnShelves.getCartonCode(),
        // op.getId(), new BeanPropertyRowMapperExt<StaOpDetail>(StaOpDetail.class));
        List<StaOpDetail> staOpDtailList2 = staOpDetailDao.findByGoodsOpDetail2(asnShelves.getStaId(), asnShelves.getCartonCode(), op.getId(), new BeanPropertyRowMapperExt<StaOpDetail>(StaOpDetail.class));
        StockTransApplication sta = staDao.getByPrimaryKey(asnShelves.getStaId());
        BiChannel bc = biChannelDao.getByCode(sta.getOwner());
        ChannelWhRef cwr = channelWhRefRefDao.getChannelRef(op.getId(), bc.getId());
        InventoryStatus is = inventoryStatusDao.getByPrimaryKey(asnShelves.getInvStatusId());

        for (StaOpDetail staOpDetail : staOpDtailList2) {// 封装收货明细
            QsSkuCommand qsc = qsSkuDao.getQsSku(op.getId(), staOpDetail.getSkuId(), new BeanPropertyRowMapper<QsSkuCommand>(QsSkuCommand.class));
            Long invStatusId = null;
            if (qsc != null && cwr.getIsPostQs() != null && cwr.getIsPostQs() && "良品".equals(is.getName())) {
                InventoryStatus status = inventoryStatusDao.findByNameAndOu("良品不可销售", op.getId());
                invStatusId = status.getId();
            }
            if (invStatusId == null) {
                invStatusId = asnShelves.getInvStatusId();
            }
            StvLineCommand stvLine = new StvLineCommand();
            stvLine.setSkuId(staOpDetail.getSkuId());
            stvLine.setReceiptQty(staOpDetail.getQty());
            stvLine.setIntInvstatus(invStatusId);// 良品,残次品
            stvLine.setInvStatusId(invStatusId);
            stvLine.setExpireDate(staOpDetail.getExpDate());// 过期时间
            if (staOpDetail.getExpDate() != null) {
                stvLine.setStrExpireDate(formatDate.format(staOpDetail.getExpDate()));// 过期时间
            }
            list.add(stvLine);
        }
        for (StaOpDetail staOpDetail : staOpDtailList2) {// 封装上架明细
            QsSkuCommand qsc = qsSkuDao.getQsSku(op.getId(), staOpDetail.getSkuId(), new BeanPropertyRowMapper<QsSkuCommand>(QsSkuCommand.class));
            Long invStatusId = null;
            if (qsc != null && cwr.getIsPostQs() != null && cwr.getIsPostQs() && "良品".equals(is.getName())) {
                InventoryStatus status = inventoryStatusDao.findByNameAndOu("良品不可销售", op.getId());
                invStatusId = status.getId();
            }
            if (invStatusId == null) {
                invStatusId = asnShelves.getInvStatusId();
            }
            StvLineCommand stvLine = new StvLineCommand();
            stvLine.setSkuId(staOpDetail.getSkuId());
            stvLine.setAddedQty(staOpDetail.getQty());//
            stvLine.setIntInvstatus(invStatusId);// 良品,残次品
            stvLine.setInvStatusId(invStatusId);
            stvLine.setLocationCode(staOpDetail.getLocationCode());// 库位编码code
            stvLine.setExpireDate(staOpDetail.getExpDate());// 过期时间
            if (staOpDetail.getExpDate() != null) {
                stvLine.setStrExpireDate(formatDate.format(staOpDetail.getExpDate()));// 过期时间
            }
            list2.add(stvLine);

        }
        // 收货逻辑
        WareHouseManagerExecute.inBoundAffirmHand(asnShelves.getStaId(), list, null, user, op.getId(), false);
        // 上架逻辑
        WareHouseManagerExecute.inboundShelvesHand(asnShelves.getStaId(), list2, user);

        for (StaOpDetail staOpDetail : staOpDtailList2) {// 生成sn
            List<StaCartonLineSnCommand> list3 = staCartonLineSnDao.getSns(asnShelves.getCartonId(), staOpDetail.getSkuId(), staOpDetail.getExpDate(), new BeanPropertyRowMapperExt<StaCartonLineSnCommand>(StaCartonLineSnCommand.class));
            for (StaCartonLineSnCommand staCartonLineSnCommand : list3) {
                List<SkuSnCommand> s = skuSnDao.getSkuSnBySku2(staCartonLineSnCommand.getSn(), op.getId(), new BeanPropertyRowMapper<SkuSnCommand>(SkuSnCommand.class));
                if (s.size() == 0) {
                    skuSnDao.createSnImport(staCartonLineSnCommand.getSn(), 5, op.getId(), staCartonLineSnCommand.getSkuId());
                    SkuSnLog log = new SkuSnLog();
                    log.setLastModifyTime(new Date());
                    log.setSkuId(staCartonLineSnCommand.getSkuId());
                    log.setSn(staCartonLineSnCommand.getSn());
                    log.setOuId(op.getId());
                    log.setTransactionTime(new Date());
                    log.setDirection(TransactionDirection.INBOUND);
                    skuSnLogDao.save(log);
                }
            }
        }

        // for (StaOpDetail staOpDetail : staOpDtailList) {// 残次品
        // ImperfectStv stv = imperfectStvDao.findImperfectStv(staOpDetail.getDmgCode());
        // Sku s = new Sku();
        // s.setId(staOpDetail.getSkuId());
        // SkuImperfect imperfect = new SkuImperfect();
        // imperfect.setCreateTime(new Date());
        // imperfect.setOwner(stv.getSta().getOwner());
        // imperfect.setDefectCode(stv.getDefectCode());
        // imperfect.setDefectType(stv.getImperfectName());
        // imperfect.setDefectWhy(stv.getImperfectLineName());
        // imperfect.setStatus(1);
        // imperfect.setSku(s);
        // imperfect.setSta(stv.getSta());
        // imperfect.setQty(1);
        // imperfect.setOuId(op);
        // skuImperfectDao.save(imperfect);
        // // imperfectStvLogDao
        // ImperfectStvLog log = new ImperfectStvLog();
        // BeanUtils.copyProperties(log, stv);
        // imperfectStvLogDao.save(log);
        // imperfectStvDao.deleteByPrimaryKey(stv.getId());
        //
        // }
        // 删除操作明细记录操作日志
        List<StaOpDetail> opDetails = staOpDetailDao.findAllByStaIdCarCode(asnShelves.getStaId(), asnShelves.getCartonCode(), op.getId(), new BeanPropertyRowMapperExt<StaOpDetail>(StaOpDetail.class));
        for (StaOpDetail staOpDetail : opDetails) {
            StaOpLog log = new StaOpLog();
            BeanUtils.copyProperties(log, staOpDetail);
            staOpLogDao.save(log);
            staOpDetailDao.deleteByPrimaryKey(staOpDetail.getId());
        }
        mongoOperation.save(asnShelves);
        if (flag) {
            // 首先判断还有没有推荐的
            if (1L == asnShelves.getInvStatus()) {// 良品
                if (getErrorShelvesCartonLineByCarIdResult(asnShelves.getCartonId())) {// 完成
                    StaCarton carton = staCartonDao.getStaCartonByCode(asnShelves.getCartonCode(), op.getId(), new BeanPropertyRowMapperExt<StaCarton>(StaCarton.class));
                    carton.setStatus(DefaultStatus.FINISHED);// 此箱子上架完成
                    staCartonDao.save(carton);
                    StockTransApplication s = staDao.getByPrimaryKey(asnShelves.getStaId());
                    if (s != null && null == s.getContainerCode()) {
                        WhContainer whc = whContainerDao.getWhContainerByCode(asnShelves.getCartonCode());
                        if (whc != null) {
                            autoOutboundTurnboxManager.resetTurnoverBoxStatusPdaShelves(whc.getId(), user.getId());
                        }
                    }
                    mongoOperation.remove(new Query(Criteria.where("cartonCode").is(asnShelves.getCartonCode()).andOperator(Criteria.where("ouId").is(op.getId()))), AsnShelves.class);
                }
            } else {
                StaCarton carton = staCartonDao.getStaCartonByCode(asnShelves.getCartonCode(), op.getId(), new BeanPropertyRowMapperExt<StaCarton>(StaCarton.class));
                carton.setStatus(DefaultStatus.FINISHED);// 此箱子上架完成
                staCartonDao.save(carton);
                StockTransApplication s = staDao.getByPrimaryKey(asnShelves.getStaId());
                if (s != null && null == s.getContainerCode()) {
                    WhContainer whc = whContainerDao.getWhContainerByCode(asnShelves.getCartonCode());
                    if (whc != null) {
                        autoOutboundTurnboxManager.resetTurnoverBoxStatusPdaShelves(whc.getId(), user.getId());
                    }
                }
                mongoOperation.remove(new Query(Criteria.where("cartonCode").is(asnShelves.getCartonCode()).andOperator(Criteria.where("ouId").is(op.getId()))), AsnShelves.class);
            }
        }
    }

    @Override
    public Map<String, Object> cancelSku(String barCode, String code, String locationCode, OperationUnit op, User user) {
        Long skuId = null;
        Map<String, Object> map = new HashMap<String, Object>();
        // AsnShelves asnShelves = mongoOperation.findOne(new
        // Query(Criteria.where("cartonCode").is(code).andOperator(Criteria.where("ouId").is(op.getId()))).with(new
        // Sort(Direction.ASC, "createTime")), AsnShelves.class);
        AsnShelves asnShelves = mongoOperation.findOne(new Query(Criteria.where("cartonCode").is(code).and("ouId").is(op.getId())).with(new Sort(Direction.ASC, "createTime")), AsnShelves.class);

        if (asnShelves == null) {
            throw new BusinessException("该容器号不存在");
        }

        List<ShelvesCartonLine> CartonLines = asnShelves.getCartonLines();
        for (ShelvesCartonLine shelvesCartonLine : CartonLines) {
            if (shelvesCartonLine.getSkubarcode().contains(barCode) && locationCode.equals(shelvesCartonLine.getLocationCode())) {
                skuId = shelvesCartonLine.getSkuId();
            }
        }
        // 删除该商品的记录日志
        List<StaOpDetail> opDetails = staOpDetailDao.findAllByStaIdCarCode2(asnShelves.getStaId(), asnShelves.getCartonCode(), op.getId(), skuId, locationCode, new BeanPropertyRowMapperExt<StaOpDetail>(StaOpDetail.class));
        for (StaOpDetail staOpDetail : opDetails) {
            for (ShelvesCartonLine shelvesCartonLine : CartonLines) {
                if (shelvesCartonLine.getSkubarcode().contains(barCode) && locationCode.equals(shelvesCartonLine.getLocationCode())) {// 判断该容器下是否有该skuBarcode和locationCode
                    shelvesCartonLine.setIsOver("0");
                    shelvesCartonLine.setRemainQty(shelvesCartonLine.getRemainQty() + staOpDetail.getQty());// 还原数量
                }
            }
        }
        for (StaOpDetail staOpDetail : opDetails) {
            staOpDetailDao.deleteByPrimaryKey(staOpDetail.getId());
        }
        mongoOperation.save(asnShelves);
        // map.put("flag", "1");
        return map;
    }

    /**
     * 库位上架(良品)
     * 
     * @throws Exception
     */
    @Override
    public Map<String, Object> locationShelves(String barCode, String code, String locationCode, OperationUnit op, User user, String type) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        // AsnShelves asnShelves = mongoOperation.findOne(new
        // Query(Criteria.where("cartonCode").is(code).andOperator(Criteria.where("ouId").is(op.getId()))).with(new
        // Sort(Direction.ASC, "createTime")), AsnShelves.class);
        AsnShelves asnShelves = mongoOperation.findOne(new Query(Criteria.where("cartonCode").is(code).and("ouId").is(op.getId())).with(new Sort(Direction.ASC, "createTime")), AsnShelves.class);
        if (asnShelves == null) {
            throw new BusinessException("该容器号不存在");
        }
        map.put("isOver", 1);
        List<ShelvesCartonLine> CartonLines = asnShelves.getCartonLines();
        for (ShelvesCartonLine shelvesCartonLine : CartonLines) {
            if (shelvesCartonLine.getSkubarcode().contains(barCode) && locationCode.equals(shelvesCartonLine.getLocationCode())) {// 判断该容器下是否有该skuBarcode和locationCode
                if (shelvesCartonLine.getQty() == (shelvesCartonLine.getQty() - shelvesCartonLine.getRemainQty())) {// 自动该line上架完成
                    shelvesCartonLine.setIsOver("1");
                    pdaStaShelvesPlanDao.updatePlanOver(shelvesCartonLine.getPlanId(), null, 2L);// 上架完成
                }
            }
            if ("0".equals(shelvesCartonLine.getIsOver())) {// 判断line是否全部上架完成
                map.put("isOver", 0);
            }
        }
        // 判断整箱是否已经已经上架完成
        if ("1".equals(map.get("isOver").toString())) {// 上架完成
            shouHuoAndshelves(asnShelves, op, user, true);// 收货和上架
        } else {
            shouHuoAndshelves(asnShelves, op, user, false);// 收货和上架
        }
        return map;
    }

    /**
     * 良品批量
     */

    @Override
    public Map<String, Object> scanSku2(String barCode, String code, String locationCode, OperationUnit op, User user, Long num) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        // AsnShelves asnShelves = mongoOperation.findOne(new
        // Query(Criteria.where("cartonCode").is(code).andOperator(Criteria.where("ouId").is(op.getId()))).with(new
        // Sort(Direction.ASC, "createTime")), AsnShelves.class);
        AsnShelves asnShelves = mongoOperation.findOne(new Query(Criteria.where("cartonCode").is(code).and("ouId").is(op.getId())).with(new Sort(Direction.ASC, "createTime")), AsnShelves.class);

        if (asnShelves == null) {
            throw new BusinessException("该容器号不存在");
        }
        List<ShelvesCartonLine> CartonLines = asnShelves.getCartonLines();
        map.put("isOver", 1);
        for (ShelvesCartonLine shelvesCartonLine : CartonLines) {
            if (shelvesCartonLine.getSkubarcode().contains(barCode) && locationCode.equals(shelvesCartonLine.getLocationCode())) {// 判断该容器下是否有该skuBarcode和locationCode
                if ("1".equals(shelvesCartonLine.getIsOver())) {
                    throw new BusinessException("已经扫描完,不可以在扫描");
                } else {
                    map.put("line", shelvesCartonLine);

                    if (shelvesCartonLine.getRemainQty() - num < 0) {
                        throw new BusinessException("剩余数量不可以为负数");
                    }
                    shelvesCartonLine.setRemainQty(shelvesCartonLine.getRemainQty() - num);

                    if (shelvesCartonLine.getQty() == (shelvesCartonLine.getQty() - shelvesCartonLine.getRemainQty())) {// 自动该line上架完成
                        shelvesCartonLine.setIsOver("1");
                        pdaStaShelvesPlanDao.updatePlanOver(shelvesCartonLine.getPlanId(), null, 2L);// 上架完成
                    }

                    // 插入数据库记录操作明细
                    StaOpDetail opDetail = new StaOpDetail();
                    opDetail.setCartonCode(asnShelves.getCartonCode());// 箱号
                    opDetail.setCreateTime(new Date());// 操作时间
                    opDetail.setDmgCode("");// 残次条码
                    opDetail.setDmgReason("");// 残次原因
                    opDetail.setDmgType("");// 残次类型
                    opDetail.setExpDate(shelvesCartonLine.getExpDate());// 过期时间
                    opDetail.setInvStatusId(asnShelves.getInvStatusId());// 库存状态
                    opDetail.setLocationCode(shelvesCartonLine.getLocationCode());// 库位编码
                    opDetail.setQty(num);// 数量
                    opDetail.setSkuId(shelvesCartonLine.getSkuId());// 商品id
                    opDetail.setSn(null);// 设置sn
                    opDetail.setStaId(asnShelves.getStaId());// 作业单id
                    opDetail.setType(2);// 2:上架
                    opDetail.setUserId(user.getId());
                    opDetail.setWhOuId(op.getId());
                    staOpDetailDao.save(opDetail);
                    staOpDetailDao.flush();
                }
            }
            if ("0".equals(shelvesCartonLine.getIsOver())) {// 判断line是否全部上架完成
                map.put("isOver", 0);
            }
        }
        if ("0".equals(map.get("isOver").toString())) {// 未完全上架
            shouHuoAndshelves(asnShelves, op, user, false);
        } else {
            shouHuoAndshelves(asnShelves, op, user, true);
        }
        return map;
    }

    // 验证库位
    @Override
    public Map<String, Object> verifyLocation(String locationCode, OperationUnit op, User user) {
        Map<String, Object> map = new HashMap<String, Object>();
        WarehouseLocation location = warehouseLocationDao.findLocationByCode(locationCode, op.getId());
        if (location == null) {
            map.put("location", 0);
        } else {
            map.put("location", 1);
        }
        return map;
    }

    /**
     * 扫描残次品
     */
    @Override
    public Map<String, Object> scanSkuIncomplete(String barCode, String code, String locationCode, OperationUnit op, User user) throws Exception {
        Boolean flag = false;
        Map<String, Object> map = new HashMap<String, Object>();
        // AsnShelves asnShelves = mongoOperation.findOne(new
        // Query(Criteria.where("cartonCode").is(code).andOperator(Criteria.where("ouId").is(op.getId()))).with(new
        // Sort(Direction.ASC, "createTime")), AsnShelves.class);
        AsnShelves asnShelves = mongoOperation.findOne(new Query(Criteria.where("cartonCode").is(code).and("ouId").is(op.getId())).with(new Sort(Direction.ASC, "createTime")), AsnShelves.class);

        if (asnShelves == null) {
            throw new BusinessException("该容器号不存在");
        }
        List<ShelvesCartonLine> CartonLines = asnShelves.getCartonLines();
        map.put("isOver", 1);
        for (ShelvesCartonLine shelvesCartonLine : CartonLines) {
            if (shelvesCartonLine.getDmgBarcode().contains(barCode)) {// 判断残次品barcode
                flag = true;
                if ("1".equals(shelvesCartonLine.getIsOver())) {
                    throw new BusinessException("已经扫描完,不可以在扫描");
                } else {
                    shelvesCartonLine.setRemainQty(shelvesCartonLine.getRemainQty() - 1);// 扣减
                    map.put("line", shelvesCartonLine);
                    if (shelvesCartonLine.getQty() == (shelvesCartonLine.getQty() - shelvesCartonLine.getRemainQty())) {// 自动该line上架完成
                        shelvesCartonLine.setIsOver("1");
                    }
                    shelvesCartonLine.getDmgBarcode().remove(barCode);// 删除残次码
                    // 插入数据库记录操作明细
                    StaOpDetail opDetail = new StaOpDetail();
                    opDetail.setCartonCode(asnShelves.getCartonCode());// 箱号
                    opDetail.setCreateTime(new Date());// 操作时间
                    opDetail.setDmgCode(barCode);// 残次条码
                    opDetail.setDmgReason("");// 残次原因
                    opDetail.setDmgType("");// 残次类型
                    opDetail.setExpDate(shelvesCartonLine.getExpDate());// 过期时间
                    opDetail.setInvStatusId(asnShelves.getInvStatusId());// 库存状态
                    opDetail.setLocationCode(locationCode);// 库位编码
                    opDetail.setQty(1L);// 数量
                    opDetail.setSkuId(shelvesCartonLine.getSkuId());// 商品id
                    opDetail.setSn(null);// 设置sn
                    opDetail.setStaId(asnShelves.getStaId());// 作业单id
                    opDetail.setType(2);// 2:上架
                    opDetail.setUserId(user.getId());
                    opDetail.setWhOuId(op.getId());
                    staOpDetailDao.save(opDetail);
                    staOpDetailDao.flush();
                }
            }
            if ("0".equals(shelvesCartonLine.getIsOver())) {// 判断line是否全部上架完成
                map.put("isOver", 0);
            }
        }
        if (!flag) {// 该容器没有该残次品条码
            throw new BusinessException("该容器没有该残次品条码或已经被扫描");
        }
        // 判断整箱是否已经上架完成
        if ("1".equals(map.get("isOver").toString())) {// 上架完成
            shouHuoAndshelves(asnShelves, op, user, true);// 收货和上架
        } else {
            mongoOperation.save(asnShelves);
        }
        return map;
    }

    /**
     * 残次品取消
     */
    @Override
    public Map<String, Object> cancelSkuIncomplete(String barCode, String code, String locationCode, OperationUnit op, User user) {
        Map<String, Object> map = new HashMap<String, Object>();
        // AsnShelves asnShelves = mongoOperation.findOne(new
        // Query(Criteria.where("cartonCode").is(code).andOperator(Criteria.where("ouId").is(op.getId()))).with(new
        // Sort(Direction.ASC, "createTime")), AsnShelves.class);
        AsnShelves asnShelves = mongoOperation.findOne(new Query(Criteria.where("cartonCode").is(code).and("ouId").is(op.getId())).with(new Sort(Direction.ASC, "createTime")), AsnShelves.class);

        if (asnShelves == null) {
            throw new BusinessException("该容器号不存在");
        }
        // 删除该商品的记录日志(残次)
        List<StaOpDetail> opDetails = staOpDetailDao.findAllDmgCodeByStaIdCarCode(asnShelves.getStaId(), asnShelves.getCartonCode(), op.getId(), locationCode, new BeanPropertyRowMapperExt<StaOpDetail>(StaOpDetail.class));
        List<ShelvesCartonLine> CartonLines = asnShelves.getCartonLines();
        for (StaOpDetail opDetail : opDetails) {
            for (ShelvesCartonLine shelvesCartonLine : CartonLines) {
                Set<String> dmgBarcodes = shelvesCartonLine.getDmgBarcode();
                if (shelvesCartonLine.getDmgBarcode2().contains(opDetail.getDmgCode()) && !shelvesCartonLine.getDmgBarcode().contains(opDetail.getDmgCode())) {// 判断残次品barcode
                    shelvesCartonLine.setIsOver("0");
                    shelvesCartonLine.setRemainQty(shelvesCartonLine.getRemainQty() + 1);
                    dmgBarcodes.add(opDetail.getDmgCode());
                    shelvesCartonLine.setDmgBarcode(dmgBarcodes);
                }
            }
        }
        for (StaOpDetail staOpDetail : opDetails) {
            staOpDetailDao.deleteByPrimaryKey(staOpDetail.getId());
        }
        mongoOperation.save(asnShelves);
        return map;
    }

    /**
     * 库位上架(残次)
     */
    @Override
    public Map<String, Object> locationShelvesDmgCode(String barCode, String code, String locationCode, OperationUnit op, User user, String type) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        // AsnShelves asnShelves = mongoOperation.findOne(new
        // Query(Criteria.where("cartonCode").is(code).andOperator(Criteria.where("ouId").is(op.getId()))).with(new
        // Sort(Direction.ASC, "createTime")), AsnShelves.class);
        AsnShelves asnShelves = mongoOperation.findOne(new Query(Criteria.where("cartonCode").is(code).and("ouId").is(op.getId())).with(new Sort(Direction.ASC, "createTime")), AsnShelves.class);

        if (asnShelves == null) {
            throw new BusinessException("该容器号不存在");
        }
        map.put("isOver", 1);
        List<ShelvesCartonLine> CartonLines = asnShelves.getCartonLines();
        for (ShelvesCartonLine shelvesCartonLine : CartonLines) {
            if ("0".equals(shelvesCartonLine.getIsOver())) {// 判断line是否全部上架完成
                map.put("isOver", 0);
            }
        }
        // 判断整箱是否已经已经上架完成
        if ("1".equals(map.get("isOver").toString())) {// 上架完成
            shouHuoAndshelves(asnShelves, op, user, true);// 收货和上架
        } else {
            shouHuoAndshelves(asnShelves, op, user, false);// 收货和上架
        }
        return map;
    }

    /**
     * 补推荐库位逻辑()
     */
    public String locationRecommendShelves(Long carId, OperationUnit op) {
        Boolean flag = pdaReceiveManager.isAutoWh(op.getId());
        StringBuffer sf = new StringBuffer("");
        List<StaCartonLine> lines = getErrorShelvesCartonLineByCarId(carId);
        for (StaCartonLine staCartonLine : lines) {
            Long num = pdaReceiveManager.locationRecommendBySku(staCartonLine.getId());
            if (num == 0) {
                if (flag) {
                    Long msgId = pdaReceiveManager.sendMsgToWcs(carId);
                    if (msgId != null) {
                        sf.append(String.valueOf(msgId) + ",");
                    }
                }
            }
        }
        return sf.toString();
    }



    public Boolean getErrorShelvesCartonLineByCarIdResult(Long carId) {
        List<StaCartonLine> lines = getErrorShelvesCartonLineByCarId(carId);
        if (lines == null || lines.size() == 0) {
            return true;
        }
        return false;
    }



    /**
     * 传入箱号id返回未未推荐成功的箱明细实体
     */
    public List<StaCartonLine> getErrorShelvesCartonLineByCarId(Long carId) {
        List<StaCartonLine> sclList = pdaReceiveManager.findRecommendFaildCartonLine(carId);
        return sclList;
    }


    /**
     * 传入箱号id返回未未推荐成功的箱明细实体(sku barCode)
     */
    public String getBarCodeShelvesCartonLineByCarId(Long carId) {
        StringBuilder sb = new StringBuilder();
        List<StaCartonLine> sclList = pdaReceiveManager.findRecommendFaildCartonLine(carId);
        for (StaCartonLine staCartonLine : sclList) {
            Sku sku = skuDao.getByPrimaryKey(staCartonLine.getSkuId());
            if (staCartonLine.getExpDate() == null) {
                sb.append("barCode:" + sku.getBarCode() + ",未推荐成功;");
            } else {
                sb.append("barCode:" + sku.getBarCode() + ",expireTime:" + staCartonLine.getExpDate() + ",未推荐成功;");
            }

        }
        return sb.toString();
    }

    public AsnShelves initAsnShelves(PdaStaShelvesPlan pdaStaShelvesPlan, OperationUnit op, User user, StaCarton carton) {// 封装AsnShelves
        AsnShelves asnShelves = new AsnShelves();
        asnShelves.setId(carton.getId());
        asnShelves.setCartonId(carton.getId());// 箱id
        asnShelves.setCartonCode(pdaStaShelvesPlan.getStaCarton().getCode());// 箱号
        asnShelves.setOrderCode(pdaStaShelvesPlan.getSta().getCode());// 单据号
        asnShelves.setSlipCode(pdaStaShelvesPlan.getSta().getRefSlipCode());// 相关单据号
        asnShelves.setCreateTime(new Date());// 创建时间
        asnShelves.setOuId(carton.getWhOuId());// 仓库id
        asnShelves.setStaId(pdaStaShelvesPlan.getSta().getId());// 作业单id
        asnShelves.setUserId(user.getId());// 操作人id
        asnShelves.setInvStatusId(carton.getInvStatusId());// 库存状态
        asnShelves.setInvStatus(1L);// 良品
        return asnShelves;
    }

    public ShelvesCartonLine initShelvesCartonLine(PdaStaShelvesPlan pdaStaShelvesPlan) {// 封装initShelvesCartonLine
        Set<String> skubarcode = new HashSet<String>();// 商品条码
        Set<String> sns = new HashSet<String>();// 商品sns
        Set<String> dmgBarcode = new HashSet<String>();// 残次条码列表
        ShelvesCartonLine shelvesCartonLine = new ShelvesCartonLine();// 明细
        skubarcode.add(pdaStaShelvesPlan.getSku().getBarCode());
        List<SkuBarcode> sbs = skuBarcodeDao.findBySkuId(pdaStaShelvesPlan.getSku().getId());
        if (sbs != null) {
            for (SkuBarcode sb : sbs) {
                skubarcode.add(sb.getBarcode());
            }
        }
        shelvesCartonLine.setPlanId(pdaStaShelvesPlan.getId());
        shelvesCartonLine.setSkubarcode(skubarcode);// 明细-barCodes
        shelvesCartonLine.setSns(sns);// 明细-sns
        shelvesCartonLine.setDmgBarcode(dmgBarcode);// 明细-dmgBarcodes
        shelvesCartonLine.setSkuId(pdaStaShelvesPlan.getSku().getId());// 明细-商品id
        shelvesCartonLine.setQty(pdaStaShelvesPlan.getQty());// 明细-剩余执行量
        shelvesCartonLine.setRemainQty(pdaStaShelvesPlan.getQty());// 明细-总执行量
        shelvesCartonLine.setSort(pdaStaShelvesPlan.getLocation().getSort());// 明细-拣货顺序
        shelvesCartonLine.setLocationId(pdaStaShelvesPlan.getLocation().getId());// 明细-库位id
        shelvesCartonLine.setLocationCode(pdaStaShelvesPlan.getLocation().getCode());// 明细-库位code
        if (pdaStaShelvesPlan.getExpDate() != null) {
            Long l = pdaStaShelvesPlan.getExpDate().getTime();
            shelvesCartonLine.setExpDate(new Date(l));// 明细-过期时间
        }
        // 明细-行是否完成
        shelvesCartonLine.setIsOver("0");
        return shelvesCartonLine;
    }

    // /////////////////////////////////////////手动上架//////////////////////////////

    public AsnShelves initAsnShelvesShou(OperationUnit op, User user, StaCarton carton) {// 封装AsnShelves（良品手动）
        AsnShelves asnShelves = new AsnShelves();
        asnShelves.setId(carton.getId());
        asnShelves.setCartonId(carton.getId());// 箱id
        asnShelves.setCartonCode(carton.getCode());// 箱号
        // asnShelves.setOrderCode(pdaStaShelvesPlan.getSta().getCode());// 单据号
        // asnShelves.setSlipCode(pdaStaShelvesPlan.getSta().getRefSlipCode());// 相关单据号
        asnShelves.setCreateTime(new Date());// 创建时间
        asnShelves.setOuId(carton.getWhOuId());// 仓库id
        asnShelves.setStaId(carton.getSta().getId());// 作业单id
        asnShelves.setUserId(user.getId());// 操作人id
        asnShelves.setInvStatusId(carton.getInvStatusId());// 库存状态
        asnShelves.setInvStatus(1L);// 良品
        return asnShelves;
    }

    public ShelvesCartonLine initShelvesCartonLineShou(StaCartonLineCommand line) {// 封装initShelvesCartonLine（良品手动）
        Sku sku = skuDao.getByPrimaryKey(line.getSkuId());
        int b = sku.getStoremode().getValue();
        Set<String> skubarcode = new HashSet<String>();// 商品条码
        Set<String> sns = new HashSet<String>();// 商品sns
        Set<String> dmgBarcode = new HashSet<String>();// 残次条码列表
        ShelvesCartonLine shelvesCartonLine = new ShelvesCartonLine();// 明细
        skubarcode.add(sku.getBarCode());
        List<SkuBarcode> sbs = skuBarcodeDao.findBySkuId(sku.getId());
        if (sbs != null) {
            for (SkuBarcode sb : sbs) {
                skubarcode.add(sb.getBarcode());
            }
        }
        // shelvesCartonLine.setPlanId(pdaStaShelvesPlan.getId());
        shelvesCartonLine.setSkubarcode(skubarcode);// 明细-barCodes
        shelvesCartonLine.setSns(sns);// 明细-sns
        shelvesCartonLine.setDmgBarcode(dmgBarcode);// 明细-dmgBarcodes
        shelvesCartonLine.setSkuId(sku.getId());// 明细-商品id
        shelvesCartonLine.setIsXq(b == 33 ? "1" : "0");// 是否是效期商品
        shelvesCartonLine.setQty(line.getQty());// 明细-总执行量
        shelvesCartonLine.setRemainQty(line.getQty2()); // 明细-剩余执行量
        // shelvesCartonLine.setSort(pdaStaShelvesPlan.getLocation().getSort());// 明细-拣货顺序
        // shelvesCartonLine.setLocationId(pdaStaShelvesPlan.getLocation().getId());// 明细-库位id
        // shelvesCartonLine.setLocationCode(pdaStaShelvesPlan.getLocation().getCode());// 明细-库位code
        shelvesCartonLine.setLineId(line.getId());// 行id
        if (line.getExpDate() != null) {
            Long l = line.getExpDate().getTime();
            shelvesCartonLine.setExpDate(new Date(l));// 明细-过期时间
        }
        // 明细-行是否完成
        // shelvesCartonLine.setIsOver("0");
        return shelvesCartonLine;
    }

    public void initWhShelvesInfoShou(AsnShelves asnShelve, OperationUnit op, User user) {
        // 把该箱子标示为 type=1（手动上架）
        StaCarton c = staCartonDao.getByPrimaryKey(asnShelve.getCartonId());
        c.setType(1);
        staCartonDao.save(c);
        // 删除缓存
        mongoOperation.remove(new Query(Criteria.where("cartonCode").is(asnShelve.getCartonCode()).andOperator(Criteria.where("ouId").is(op.getId()))), AsnShelves.class);
        // 删除t_wh_sta_op_detial
        staOpDetailDao.deleteStaOpDetail(asnShelve.getStaId(), asnShelve.getCartonId(), asnShelve.getOuId());
        // 先查询t_wh_sta_op_log
        List<StaOpLogCommand2> cs = staOpLogDao.queryStaOpLog(asnShelve.getStaId(), asnShelve.getCartonId(), asnShelve.getOuId(), new BeanPropertyRowMapperExt<StaOpLogCommand2>(StaOpLogCommand2.class));
        // 查询t_wh_sta_carton_line
        List<StaCartonLineCommand> lines = staCartonLineDao.getCartonLineByCartonId2(asnShelve.getCartonId(), new BeanPropertyRowMapperExt<StaCartonLineCommand>(StaCartonLineCommand.class));
        // 还原t_wh_sta_carton_line已经上架数量
        for (StaCartonLineCommand staCartonLine : lines) {
            for (StaOpLogCommand2 c2 : cs) {
                // 商品与失效日期为一维度 进行数量更改
                if (staCartonLine.getSkuId().equals(c2.getSkuId2()) && (staCartonLine.getExpDate() == null ? "-1" : staCartonLine.getExpDate()).equals((c2.getExpDate2() == null ? "-1" : c2.getExpDate2()))) {
                    Long num = staCartonLine.getQty() - c2.getQty2();
                    if (num < 0) {
                        throw new BusinessException("商品与失效日期为一维度 进行数量更改,<0");
                    } else if (num == 0) {
                        // 设置t_wh_sta_carton_line 状态为1 完成上架
                        StaCartonLine l = staCartonLineDao.getByPrimaryKey(staCartonLine.getId());
                        l.setStatus(1);
                        staCartonLineDao.save(l);
                    }
                    staCartonLine.setQty2(num);
                    break;
                }
            }
        }
        // 放入新的缓存里
        List<ShelvesCartonLine> shelvesCartonLines = new ArrayList<ShelvesCartonLine>();
        AsnShelves a = initAsnShelvesShou(op, user, c);// 封装AsnShelves
        for (StaCartonLineCommand line : lines) {
            ShelvesCartonLine shelvesCartonLine = initShelvesCartonLineShou(line);// 封装initShelvesCartonLine
            shelvesCartonLines.add(shelvesCartonLine);
        }
        a.setCartonLines(shelvesCartonLines);
        mongoOperation.save(a);
    }

    /**
     * 缓存良品 手动
     */
    @Override
    public String initMongodbWhShelvesInfoShou(String code, OperationUnit op, User user) {
        String tip = null;
        // 1：首先判断缓存里是否含有该箱子 2： 有的话 删除 基于推荐表生成的缓存 并且删除t_wh_sta_op_detial并且把这箱子表示type=1 （手动上架） 3： 再
        // 看t_wh_sta_op_log表该箱子已经上架数据 并与carton表明细 生成 新的手动上架缓存数据
        // AsnShelves asnShelve = mongoOperation.findOne(new
        // Query(Criteria.where("cartonCode").is(code).andOperator(Criteria.where("ouId").is(op.getId()))).with(new
        // Sort(Direction.ASC, "createTime")), AsnShelves.class);
        AsnShelves asnShelve = mongoOperation.findOne(new Query(Criteria.where("cartonCode").is(code).and("ouId").is(op.getId())).with(new Sort(Direction.ASC, "createTime")), AsnShelves.class);
        if (asnShelve == null) {
            List<ChannelWhRef> cwrList = channelWhRefRefDao.getChannelRefByOuid(op.getId());
            if (cwrList != null) {
                for (ChannelWhRef cwr : cwrList) {
                    if (cwr.getReceivePrefix() != null && !"".equals(cwr.getReceivePrefix())) {
                        asnShelve = mongoOperation.findOne(new Query(Criteria.where("cartonCode").is(cwr.getReceivePrefix() + code).and("ouId").is(op.getId())).with(new Sort(Direction.ASC, "createTime")), AsnShelves.class);
                        if (asnShelve != null) {
                            code = cwr.getReceivePrefix() + code;
                            break;
                        }
                    }
                }
            }
        }

        if (asnShelve == null) {// 封装数据
            // 放入新的缓存里
            StaCarton carton = staCartonDao.getStaCartonByCode(code, op.getId(), new BeanPropertyRowMapperExt<StaCarton>(StaCarton.class));
            if (carton == null) {
                List<ChannelWhRef> cwrList = channelWhRefRefDao.getChannelRefByOuid(op.getId());
                if (cwrList != null) {
                    for (ChannelWhRef cwr : cwrList) {
                        if (cwr.getReceivePrefix() != null && !"".equals(cwr.getReceivePrefix())) {
                            carton = staCartonDao.getStaCartonByCode(cwr.getReceivePrefix() + code, op.getId(), new BeanPropertyRowMapperExt<StaCarton>(StaCarton.class));
                            if (carton != null) {
                                break;
                            }
                        }
                    }
                }
            }
            if (null == carton) {
                throw new BusinessException("没有此箱号");
            }
            InventoryStatus s = inventoryStatusDao.getByPrimaryKey(carton.getInvStatusId());
            if (s == null) {
                throw new BusinessException("此仓库下库存状态设置有误");
            } else if (!"良品".equals(s.getName())) {
                throw new BusinessException("此箱不是良品");
            }
            AsnShelves z = new AsnShelves();
            z.setCartonId(carton.getId());
            z.setOuId(carton.getWhOuId());
            z.setStaId(carton.getSta().getId());
            initWhShelvesInfoShou(z, op, user);
        } else {
            /*
             * if (!(user.getId().toString()).equals(asnShelve.getUserId().toString())) { User u =
             * userDao.getByPrimaryKey(asnShelve.getUserId()); throw new
             * BusinessException("该容器被用户id:" + asnShelve.getUserId() + ",用户名:" + u.getUserName() +
             * ",正在使用"); } else
             */
            if (1L != asnShelve.getInvStatus()) {
                throw new BusinessException("此箱不是良品");
            } else {
                initWhShelvesInfoShou(asnShelve, op, user);
            }
        }
        return tip;
    }

    /**
     * 验证手工上架
     * 
     * @throws ParseException
     */
    @Override
    public String checkSkuShou(String code, String skuBarcode, Long num, String eDate, String sDate, OperationUnit op, User user) throws ParseException {
        String tip = null;
        Long skuId = null;
        // AsnShelves asnShelve = mongoOperation.findOne(new
        // Query(Criteria.where("cartonCode").is(code).andOperator(Criteria.where("ouId").is(op.getId()))).with(new
        // Sort(Direction.ASC, "createTime")), AsnShelves.class);
        AsnShelves asnShelve = mongoOperation.findOne(new Query(Criteria.where("cartonCode").is(code).and("ouId").is(op.getId())).with(new Sort(Direction.ASC, "createTime")), AsnShelves.class);
        if (asnShelve == null) {
            List<ChannelWhRef> cwrList = channelWhRefRefDao.getChannelRefByOuid(op.getId());
            if (cwrList != null) {
                for (ChannelWhRef cwr : cwrList) {
                    if (cwr.getReceivePrefix() != null && !"".equals(cwr.getReceivePrefix())) {
                        asnShelve = mongoOperation.findOne(new Query(Criteria.where("cartonCode").is(cwr.getReceivePrefix() + code).and("ouId").is(op.getId())).with(new Sort(Direction.ASC, "createTime")), AsnShelves.class);
                        if (asnShelve != null) {
                            code = cwr.getReceivePrefix() + code;
                            break;
                        }
                    }
                }
            }
        }
        List<ShelvesCartonLine> CartonLines = asnShelve.getCartonLines();
        for (ShelvesCartonLine line : CartonLines) {
            if (line.getSkubarcode().contains(skuBarcode)) {// 判断该容器下 是否有该条码
                skuId = line.getSkuId();
                break;
            }
        }
        if (skuId == null) {
            throw new BusinessException("该容器没有：" + skuBarcode + "商品");
        }

        Date ss = null;//
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date d1 = new Date();
        String d2 = sdf.format(d1);
        Date d3 = sdf.parse(d2);// 当前时间
        Date e = null;// 失效时间
        Date s = null;// 生产时间
        if (!"".equals(eDate)) {// 失效日期
            e = sdf.parse(eDate);
            if (d3.after(e)) {
                throw new BusinessException("失效日期应大于当前时间");
            }
        }
        if (!"".equals(sDate)) {// 生产日期
            s = sdf.parse(sDate);
            if (s.after(d3)) {
                throw new BusinessException("生产日期应小于当前时间");
            }
        }
        if (!"".equals(sDate) && !"".equals(eDate)) {
            s = sdf.parse(sDate);
            e = sdf.parse(eDate);
            if (s.after(e)) {
                throw new BusinessException("生产日期应小于失效日期");
            }
        }
        // 确定商品与失效时间
        if (!"".equals(eDate)) {
            ss = e;
        } else if (!"".equals(sDate)) {// 根据生产日期推断出失效日期
            Sku sku = skuDao.getByPrimaryKey(skuId);
            if (null != sku && null != sku.getValidDate()) {
                Integer date = sku.getValidDate();
                ss = getNDay(s, date);
            }
        }
        boolean flag = false;
        for (ShelvesCartonLine line : CartonLines) {
            if (line.getSkubarcode().contains(skuBarcode)) {// 判断该容器下
                // 是否有该条码
                if (ss == null && line.getExpDate() == null) {
                    Long num2 = line.getRemainQty() - num;
                    if (num2 < 0) {
                        throw new BusinessException("数量超出,此商品,总数量:" + line.getRemainQty() + ";已上架:" + (line.getQty() - line.getRemainQty()));
                    }
                    flag = true;
                    break;
                } else if (ss != null && line.getExpDate() != null) {
                    if (ss.equals(line.getExpDate())) {
                        Long num2 = line.getRemainQty() - num;
                        if (num2 < 0) {
                            throw new BusinessException("数量超出,此商品,总数量:" + line.getQty() + ";已上架:" + (line.getQty() - line.getRemainQty()));
                        }
                        flag = true;
                        break;
                    } /*
                       * else { throw new BusinessException("该商品失效日期不正确,应为:" + line.getExpDate()); }
                       */

                }
                /*
                 * else { if(ss.equals(line.getExpDate())){ Long num2 = line.getQty() - num; if
                 * (num2 < 0) { throw new BusinessException("数量超出,此商品,总数量:" + line.getRemainQty() +
                 * ";已上架:" + (line.getRemainQty() - line.getQty())); } } }
                 */
            }
        }

        if (!flag) {
            throw new BusinessException("该容器下没有该失效日期的商品");
        }
        if (ss != null) {
            tip = sdf.format(ss);
        }

        return tip;
    }

    public Date getNDay(Date date, Integer factor) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, factor);
        return calendar.getTime();
    }

    public Date getStringDate(String eDate) throws ParseException {
        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        if (eDate == null || "".equals(eDate)) {
            return date;
        } else {
            date = sdf.parse(eDate);
        }
        return date;
    }


    public String getDateString(Date eDate) throws ParseException {
        String date = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        if (eDate == null || "".equals(eDate)) {
            return date;
        } else {
            date = sdf.format(eDate);
        }
        return date;
    }

    /**
     * 手动上架
     * 
     * @throws ParseException
     */
    @Override
    public Map<String, Object> locationShelvesShou(Long num, String skuBarcode, String code, String locationCode, String eDate, OperationUnit op, User user, Object object) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        // 封装手动上架数据
        Long skuId = null;
        List<StvLineCommand> list = new ArrayList<StvLineCommand>();// 收货明细list
        List<StvLineCommand> list2 = new ArrayList<StvLineCommand>();// 上架明细list
        // AsnShelves asnShelve = mongoOperation.findOne(new
        // Query(Criteria.where("cartonCode").is(code).andOperator(Criteria.where("ouId").is(op.getId()))).with(new
        // Sort(Direction.ASC, "createTime")), AsnShelves.class);
        AsnShelves asnShelve = mongoOperation.findOne(new Query(Criteria.where("cartonCode").is(code).and("ouId").is(op.getId())).with(new Sort(Direction.ASC, "createTime")), AsnShelves.class);
        if (asnShelve == null) {
            List<ChannelWhRef> cwrList = channelWhRefRefDao.getChannelRefByOuid(op.getId());
            if (cwrList != null) {
                for (ChannelWhRef cwr : cwrList) {
                    if (cwr.getReceivePrefix() != null && !"".equals(cwr.getReceivePrefix())) {
                        asnShelve = mongoOperation.findOne(new Query(Criteria.where("cartonCode").is(cwr.getReceivePrefix() + code).and("ouId").is(op.getId())).with(new Sort(Direction.ASC, "createTime")), AsnShelves.class);
                        if (asnShelve != null) {
                            code = cwr.getReceivePrefix() + code;
                            break;
                        }
                    }
                }
            }
        }
        if (asnShelve == null) {
            throw new BusinessException("没有该容器");
        }
        List<ShelvesCartonLine> CartonLines = asnShelve.getCartonLines();
        for (ShelvesCartonLine line : CartonLines) {
            if (line.getSkubarcode().contains(skuBarcode)) {// 判断该容器下 是否有该条码
                skuId = line.getSkuId();
                break;
            }
        }
        if (skuId == null) {
            throw new BusinessException("该容器没有：" + skuBarcode + "商品");
        }
        // 验证库位（良品人为上架的目标库位不能是被系统推荐逻辑占用的库位）
        WarehouseLocation w = warehouseLocationDao.findLocationByCode(locationCode, op.getId());
        if (w == null) {
            throw new BusinessException("该库位不存在");
        }
        List<PdaStaShelvesPlan> ls = pdaStaShelvesPlanDao.ckeckLocation(w.getId(), asnShelve.getOuId(), new BeanPropertyRowMapper<PdaStaShelvesPlan>(PdaStaShelvesPlan.class));
        if (ls.size() > 0) {
            throw new BusinessException("该库位是已被系统推荐逻辑占用的库位");
        }
        QsSkuCommand qsc = qsSkuDao.getQsSku(op.getId(), skuId, new BeanPropertyRowMapper<QsSkuCommand>(QsSkuCommand.class));
        Long invStatusId = null;
        StockTransApplication sta = staDao.getByPrimaryKey(asnShelve.getStaId());
        BiChannel bc = biChannelDao.getByCode(sta.getOwner());
        ChannelWhRef cwr = channelWhRefRefDao.getChannelRef(op.getId(), bc.getId());
        InventoryStatus is = inventoryStatusDao.getByPrimaryKey(asnShelve.getInvStatusId());
        if (qsc != null && cwr.getIsPostQs() != null && cwr.getIsPostQs() && "良品".equals(is.getName())) {
            InventoryStatus status = inventoryStatusDao.findByNameAndOu("良品不可销售", op.getId());
            invStatusId = status.getId();
        }
        if (invStatusId == null) {
            invStatusId = asnShelve.getInvStatusId();
        }

        StvLineCommand stvLine1 = new StvLineCommand();
        stvLine1.setSkuId(skuId);
        stvLine1.setReceiptQty(num);
        stvLine1.setIntInvstatus(invStatusId);// 良品,残次品
        stvLine1.setInvStatusId(invStatusId);
        stvLine1.setExpireDate(getStringDate(eDate));// 过期时间
        if (eDate != null) {
            stvLine1.setStrExpireDate(eDate);// 过期时间
        }
        list.add(stvLine1);
        StvLineCommand stvLine2 = new StvLineCommand();// 封装上架明细
        stvLine2.setSkuId(skuId);
        stvLine2.setAddedQty(num);//
        stvLine2.setIntInvstatus(invStatusId);// 良品,残次品
        stvLine2.setInvStatusId(invStatusId);
        stvLine2.setLocationCode(locationCode);// 库位编码code
        stvLine2.setExpireDate(getStringDate(eDate));// 过期时间
        if (eDate != null) {
            stvLine2.setStrExpireDate(eDate);// 过期时间
        }
        list2.add(stvLine2);

        // 收货逻辑
        WareHouseManagerExecute.inBoundAffirmHand(asnShelve.getStaId(), list, null, user, op.getId(), false);
        // 上架逻辑
        WareHouseManagerExecute.inboundShelvesHand(asnShelve.getStaId(), list2, user);
        // 生成sn
        List<StaCartonLineSnCommand> list3 = staCartonLineSnDao.getSns(asnShelve.getCartonId(), skuId, getStringDate(eDate), new BeanPropertyRowMapperExt<StaCartonLineSnCommand>(StaCartonLineSnCommand.class));
        for (StaCartonLineSnCommand staCartonLineSnCommand : list3) {
            List<SkuSnCommand> s = skuSnDao.getSkuSnBySku2(staCartonLineSnCommand.getSn(), op.getId(), new BeanPropertyRowMapper<SkuSnCommand>(SkuSnCommand.class));
            if (s.size() == 0) {
                skuSnDao.createSnImport(staCartonLineSnCommand.getSn(), 5, op.getId(), staCartonLineSnCommand.getSkuId());
                SkuSnLog log = new SkuSnLog();
                log.setLastModifyTime(new Date());
                log.setSkuId(staCartonLineSnCommand.getSkuId());
                log.setSn(staCartonLineSnCommand.getSn());
                log.setOuId(op.getId());
                log.setTransactionTime(new Date());
                log.setDirection(TransactionDirection.INBOUND);
                skuSnLogDao.save(log);
            }
        }
        // 操作明细记录操作日志
        StaOpLog log = new StaOpLog();
        log.setCarId(asnShelve.getCartonId());
        log.setCartonCode(code);
        log.setCreateTime(new Date());
        log.setExpDate(getStringDate(eDate));
        log.setInvStatusId(asnShelve.getInvStatusId());
        log.setLocationCode(locationCode);
        log.setQty(num);
        log.setSkuId(skuId);
        log.setStaId(asnShelve.getStaId());
        log.setUserId(user.getId());
        log.setWhOuId(op.getId());
        log.setType(2);// 上架
        staOpLogDao.save(log);

        // 查询t_wh_sta_carton_line
        // List<StaCartonLineCommand> lines =
        // staCartonLineDao.getCartonLineByCartonId2(asnShelve.getCartonId(), new
        // BeanPropertyRowMapperExt<StaCartonLineCommand>(StaCartonLineCommand.class));
        List<ShelvesCartonLine> lines = asnShelve.getCartonLines();
        // 还原t_wh_sta_carton_line已经上架数量
        int i = 0;
        for (ShelvesCartonLine staCartonLine : lines) {
            // 商品与失效日期为一维度 进行数量更改
            if (staCartonLine.getSkuId().equals(skuId) && (getDateString(staCartonLine.getExpDate()) == null ? "-1" : getDateString(staCartonLine.getExpDate())).equals(((eDate == null || "".equals(eDate)) ? "-1" : eDate))) {
                Long num2 = staCartonLine.getRemainQty() - num;
                if (num2 < 0) {
                    throw new BusinessException("商品与失效日期为一维度 进行数量更改,<0");
                } else if (num2 == 0) {
                    // 设置t_wh_sta_carton_line 状态为1 完成上架
                    StaCartonLine l = staCartonLineDao.getByPrimaryKey(staCartonLine.getLineId());
                    l.setStatus(1);
                    staCartonLineDao.save(l);
                    staCartonLineDao.flush();
                }
                staCartonLine.setRemainQty(num2);
                i++;
                break;
            }
        }
        if (i == 0) {
            throw new BusinessException("无商品与失效日期为一维度 ");
        }
        mongoOperation.save(asnShelve);
        // 查看箱明细时候都上架完成
        List<StaCartonLineCommand> ls2 = staCartonLineDao.getCartonLineByCartonId3(asnShelve.getCartonId(), new BeanPropertyRowMapperExt<StaCartonLineCommand>(StaCartonLineCommand.class));
        if (ls2.size() > 0) {
            map.put("brand", "1");// 该箱子 还没有上架完
        } else {
            StaCarton carton = staCartonDao.getStaCartonByCode(asnShelve.getCartonCode(), op.getId(), new BeanPropertyRowMapperExt<StaCarton>(StaCarton.class));
            carton.setStatus(DefaultStatus.FINISHED);// 此箱子上架完成
            staCartonDao.save(carton);
            StockTransApplication s = staDao.getByPrimaryKey(asnShelve.getStaId());
            if (s != null && null == s.getContainerCode()) {
                WhContainer whc = whContainerDao.getWhContainerByCode(asnShelve.getCartonCode());
                if (whc != null) {
                    autoOutboundTurnboxManager.resetTurnoverBoxStatusPdaShelves(whc.getId(), user.getId());
                }
            }
            mongoOperation.remove(new Query(Criteria.where("cartonCode").is(asnShelve.getCartonCode()).andOperator(Criteria.where("ouId").is(op.getId()))), AsnShelves.class);
            map.put("brand", "2");// 该箱子 上架完
        }
        return map;
    }

    @Override
    public String checkSkuShouPro(Long num, String code, String skuBarcode, OperationUnit op, User user) {
        String isXq = null;
        Long skuId = null;
        // AsnShelves asnShelve = mongoOperation.findOne(new
        // Query(Criteria.where("cartonCode").is(code).andOperator(Criteria.where("ouId").is(op.getId()))).with(new
        // Sort(Direction.ASC, "createTime")), AsnShelves.class);
        AsnShelves asnShelve = mongoOperation.findOne(new Query(Criteria.where("cartonCode").is(code).and("ouId").is(op.getId())).with(new Sort(Direction.ASC, "createTime")), AsnShelves.class);
        if (asnShelve == null) {
            List<ChannelWhRef> cwrList = channelWhRefRefDao.getChannelRefByOuid(op.getId());
            if (cwrList != null) {
                for (ChannelWhRef cwr : cwrList) {
                    if (cwr.getReceivePrefix() != null && !"".equals(cwr.getReceivePrefix())) {
                        asnShelve = mongoOperation.findOne(new Query(Criteria.where("cartonCode").is(cwr.getReceivePrefix() + code).and("ouId").is(op.getId())).with(new Sort(Direction.ASC, "createTime")), AsnShelves.class);
                        if (asnShelve != null) {
                            code = cwr.getReceivePrefix() + code;
                            break;
                        }
                    }
                }
            }
        }
        List<ShelvesCartonLine> CartonLines = asnShelve.getCartonLines();
        for (ShelvesCartonLine line : CartonLines) {
            if (line.getSkubarcode().contains(skuBarcode)) {// 判断该容器下 是否有该条码
                skuId = line.getSkuId();
                break;
            }
        }
        if (skuId == null) {
            throw new BusinessException("该容器没有：" + skuBarcode + "商品");
        }

        for (ShelvesCartonLine line : CartonLines) {
            if (line.getSkubarcode().contains(skuBarcode)) {// 判断该容器下 是否有该条码
                isXq = line.getIsXq();
                break;
            }
        }
        if ("0".equals(isXq)) {

            for (ShelvesCartonLine line : CartonLines) {
                if (line.getSkubarcode().contains(skuBarcode)) {// 判断该容器下 是否有该条码
                    Long num2 = line.getRemainQty() - num;
                    if (num2 < 0) {
                        throw new BusinessException("数量超出,此商品,总数量:" + line.getRemainQty() + ";已上架:" + (line.getQty() - line.getRemainQty()));
                    }
                }
            }
        }


        return isXq;
    }


    @Override
    public String checkStaStatus(String slipCode, Long ouId) {
        String re = "";
        AsnShelves asnShelve = mongoOperation.findOne(new Query(Criteria.where("cartonCode").is(slipCode).and("ouId").is(ouId)).with(new Sort(Direction.ASC, "createTime")), AsnShelves.class);
        if (asnShelve == null) {
            re = "error";
            return re;
        }
        StockTransApplication sta = staDao.getByPrimaryKey(asnShelve.getStaId());
        if (sta == null) {
            re = "error";
        } else if (sta.getStatus().getValue() == 10) {
            re = "yes";
        } else {
            re = "no";
        }
        return re;
    }
}
