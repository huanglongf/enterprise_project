package com.jumbo.wms.manager.pda;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.dao.authorization.PhysicalWarehouseDao;
import com.jumbo.dao.authorization.UserDao;
import com.jumbo.dao.automaticEquipment.GoodsCollectionDao;
import com.jumbo.dao.automaticEquipment.GoodsCollectionLogDao;
import com.jumbo.dao.automaticEquipment.MsgToWcsDao;
import com.jumbo.dao.automaticEquipment.WhContainerDao;
import com.jumbo.dao.automaticEquipment.WhPickingBatchDao;
import com.jumbo.dao.automaticEquipment.ZoonDao;
import com.jumbo.dao.baseinfo.SkuBarcodeDao;
import com.jumbo.dao.baseinfo.SkuDao;
import com.jumbo.dao.baseinfo.WarehouseDao;
import com.jumbo.dao.pda.PickingBatchBoxDao;
import com.jumbo.dao.pda.PickingLineDao;
import com.jumbo.dao.pda.PickingOpLineDao;
import com.jumbo.dao.pda.PickingOpLineLogDao;
import com.jumbo.dao.warehouse.PickingListDao;
import com.jumbo.dao.warehouse.RtwDieKingDao;
import com.jumbo.dao.warehouse.RtwDieKingLineDao;
import com.jumbo.dao.warehouse.RtwDieKingLineLogDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.dao.warehouse.WarehouseLocationDao;
import com.jumbo.dao.warehouse.WhInfoTimeRefDao;
import com.jumbo.util.JsonUtil;
import com.jumbo.util.StringUtil;
import com.jumbo.util.StringUtils;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.warehouse.AutoOutboundTurnboxManager;
import com.jumbo.wms.model.DefaultStatus;
import com.jumbo.wms.model.authorization.User;
import com.jumbo.wms.model.automaticEquipment.GoodsCollection;
import com.jumbo.wms.model.automaticEquipment.GoodsCollectionLog;
import com.jumbo.wms.model.automaticEquipment.MsgToWcs;
import com.jumbo.wms.model.automaticEquipment.MsgToWcsPickingOverRequest;
import com.jumbo.wms.model.automaticEquipment.WcsInterfaceType;
import com.jumbo.wms.model.automaticEquipment.WhContainer;
import com.jumbo.wms.model.automaticEquipment.WhPickingBatch;
import com.jumbo.wms.model.automaticEquipment.Zoon;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.baseinfo.SkuBarcode;
import com.jumbo.wms.model.baseinfo.Warehouse;
import com.jumbo.wms.model.mongodb.MongoDBWhPicking;
import com.jumbo.wms.model.pda.PdaSortPickingCommand;
import com.jumbo.wms.model.pda.PickingBatchBox;
import com.jumbo.wms.model.pda.PickingOpLine;
import com.jumbo.wms.model.pda.PickingOpLineLog;
import com.jumbo.wms.model.warehouse.PhysicalWarehouse;
import com.jumbo.wms.model.warehouse.PickingList;
import com.jumbo.wms.model.warehouse.PickingListCheckMode;
import com.jumbo.wms.model.warehouse.PickingListStatus;
import com.jumbo.wms.model.warehouse.RtwDieking;
import com.jumbo.wms.model.warehouse.RtwDiekingLineLog;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationStatus;
import com.jumbo.wms.model.warehouse.WhInfoTimeRef;
import com.jumbo.wms.model.warehouse.WhInfoTimeRefBillType;
import com.jumbo.wms.web.commond.GoodsCollectionCommand;

import loxia.dao.Pagination;

@Transactional
@Service("pdaPickingManager")
public class PdaPickingManagerImpl extends BaseManagerImpl implements PdaPickingManager {

    private static final long serialVersionUID = -7697640588371433642L;

    @Resource(name = "mongoTemplate")
    private MongoOperations mongoOperation;
    @Autowired
    private WhPickingBatchDao whPickingBatchDao;
    @Autowired
    private PickingLineDao pickingLineDao;
    @Autowired
    private SkuDao skuDao;
    @Autowired
    private SkuBarcodeDao skuBarcodeDao;

    @Autowired
    private WhInfoTimeRefDao whInfoTimeRefDao;
    @Autowired
    private PickingListDao pickingListDao;
    @Autowired
    private PickingOpLineDao pickingOpLineDao;
    @Autowired
    private WarehouseDao warehouseDao;
    @Autowired
    private AutoOutboundTurnboxManager autoOutboundTurnboxManager;
    @Autowired
    private ZoonDao zoonDao;
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private PickingOpLineLogDao opLogDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private WhContainerDao whContainerDao;

    @Autowired
    private GoodsCollectionDao goodsCollectionDao;
    @Autowired
    private PhysicalWarehouseDao physicalWarehouseDao;

    @Autowired
    private MsgToWcsDao msgToWcsDao;
    @Autowired
    private PickingBatchBoxDao pickingBatchBoxDao;
    @Autowired
    private GoodsCollectionLogDao goodsCollectionLogDao;
    @Autowired
    private RtwDieKingLineDao rtwDieKingLineDao;
    @Autowired
    private RtwDieKingDao rtwDieKingDao;
    @Autowired
    private RtwDieKingLineLogDao rtwDieKingLineLogDao;
    @Autowired
    private WarehouseLocationDao locDao;


    /**
     * 根据拣货编码初始化mongodb数据
     * 
     * @param code
     * @return
     */
    public String initMongodbWhPickingInfo(String pickingbarCode, Long userId, Long ouId) {
        // 清除没有明细的小批次
        pickingLineDao.flush();
        List<Long> pbIdList = whPickingBatchDao.findWhPickingBatchByNotLine(pickingbarCode, new SingleColumnRowMapper<Long>(Long.class));
        whPickingBatchDao.modifyStaPbId(pbIdList);
        whPickingBatchDao.deleteAllByPrimaryKey(pbIdList);
        whPickingBatchDao.flush();


        List<WhPickingBatch> pbList = whPickingBatchDao.getPlzListByBarCode(pickingbarCode);
        if (pbList != null && pbList.size() > 0) {
            PickingList pl = pickingListDao.getByPrimaryKey(pbList.get(0).getPickingList().getId());
            Warehouse w = warehouseDao.getByOuId(pl.getWarehouse().getId());

            if (!ouId.equals(pl.getWarehouse().getId())) {
                return "此配货批不在此仓库！";
            }
            //
            if (!PickingListStatus.PACKING.equals(pl.getStatus()) && pl.getCheckMode() != PickingListCheckMode.PICKING_CHECK) {
                return "此配货批已配货完成或是已被取消！";
            }

            GoodsCollection cc = goodsCollectionDao.getGoodsCollectionByPickingCode(pl.getCode());

            if (cc == null && pl.getCheckMode() != PickingListCheckMode.PICKING_CHECK) {
                Long collectionQty = goodsCollectionDao.countCollectionByStatus(2, ouId, new SingleColumnRowMapper<Long>(Long.class));
                Long collectionOverQty = goodsCollectionDao.countCollectionByStatus(3, ouId, new SingleColumnRowMapper<Long>(Long.class));
                Long qty = collectionQty + collectionOverQty;
                List<Long> pickIdList = whContainerDao.findContainerPickId(w.getAutoSeedGroup(), new SingleColumnRowMapper<Long>(Long.class));
                if (w.getIsAutoWh() && pickIdList != null) {
                    qty += pickIdList.size();
                }
                if (w.getTotalPickinglistLimit() != null && qty.intValue() >= w.getTotalPickinglistLimit()) {
                    return "仓库拣货批次已达到上限，无法拣货！";
                }

            }
            /*
             * if (w.getIsAutoWh() != null && w.getIsAutoWh() == true && (pl.getCheckMode() ==
             * PickingListCheckMode.DEFAULE)) {
             * 
             * // 校验批次是否已经达到上限 List<Long> pickIdList =
             * whContainerDao.findContainerPickId(w.getAutoSeedGroup(), new
             * SingleColumnRowMapper<Long>(Long.class)); if (pickIdList != null && pickIdList.size()
             * >= w.getAutoPickinglistLimit()) {// 当批次的数量超过限制的时候，如果此批次已经在集货口，那么就继续往下走，否则就抛异常 boolean
             * b = true; for (Long pickId : pickIdList) { if (pl.getId().equals(pickId)) { b =
             * false; } } if (b) { // 仓库中配货批次数量已经达到上限，请稍后再为该批次绑定周转箱！ return "仓库中配货批次数量已经达到上限！"; } }
             * }
             */

            // 验证是否被PDA操作过
            /*
             * List<MongoDBWhPicking> mdwpList = getMongoDBWhPickingByCode(pickingbarCode); if
             * (mdwpList != null && mdwpList.size() > 0) { // 初始化mgdb状态
             * mongoOperation.updateMulti(new Query(Criteria.where("barCode").is(pickingbarCode)),
             * new Update().set("status", 0), MongoDBWhPicking.class); return ""; }
             */
            // 拣货记录开始
            whPickingBatchDao.updateWhPickingBatchStartTime(pickingbarCode, userId);
            // //end
            // 清除mgdb缓存
            mongoOperation.remove(new Query(Criteria.where("barCode").in(pickingbarCode)), MongoDBWhPicking.class);
            boolean b = false;
            for (WhPickingBatch pb : pbList) {

                // 更新拣货数量
                // pickingLineDao.updatePickingLineQtyByPbId(pb.getId());
                updatePickingLineQtyByPbId(pb.getId());
                // 备份日志
                backupsPickingOpLing(pb.getId());

                List<MongoDBWhPicking> wp = pickingLineDao.findMongodbInfoByPbCode(pb.getCode(), new BeanPropertyRowMapper<MongoDBWhPicking>(MongoDBWhPicking.class));
                if (wp == null || wp.size() < 1) {
                    if (pb.getStatus().getValue() == 1) {
                        pickingBatchOver(pb.getCode(), userId);
                    }
                    b = true;
                    continue;
                }
                b = false;
                // 获取商品的所有条码
                for (MongoDBWhPicking p : wp) {
                    Set<String> skuBarcodes = new HashSet<String>();

                    Sku sku = skuDao.getByPrimaryKey(p.getSkuId());
                    skuBarcodes.add(sku.getBarCode());
                    List<SkuBarcode> sbs = skuBarcodeDao.findBySkuId(p.getSkuId());
                    if (sbs != null) {
                        for (SkuBarcode sb : sbs) {
                            skuBarcodes.add(sb.getBarcode());
                        }
                    }
                    p.setBarcodes(skuBarcodes);
                    // 保存数据到mongodb
                    mongoOperation.save(p);
                }
            }
            List<MongoDBWhPicking> mdpList=getMongoDBWhPickingByCode(pickingbarCode);
            if(mdpList==null||mdpList.size()<1) {
            	return "此编码已拣货完成！";
            }
            /*if (b) {
                return "此编码已拣货完成！";
            }*/
            if (pl.getPickingStatus() == null || pl.getPickingStatus() == 0) {
                pl.setPickingStatus(13);
                pl.setPickingStartTime(new Date());
                User u = userDao.getByPrimaryKey(userId);
                pl.setPickingUser(u);
            }
            if (w.getIsCheckPickingStatus() != null && w.getIsCheckPickingStatus()) {

                // 配货批开始
                WhInfoTimeRef ref2 = whInfoTimeRefDao.getInfoBySlipCode(pl.getCode(), 13, new BeanPropertyRowMapper<WhInfoTimeRef>(WhInfoTimeRef.class));
                if (ref2 == null) {
                    whInfoTimeRefDao.insertWhInfoTime2(pl.getCode(), WhInfoTimeRefBillType.STA_PICKING.getValue(), 13, userId, ouId);
                }
            }
            pl.setIsHavePrint(true);
        }

        else {
            return "无此拣货编码！";
        }
        return "";
    }


    /**
     * 获取已绑定的周转箱
     * 
     * @param pickingCode
     * @return
     */
    public List<String> findBindBoxByPickingCode(String pickingCode) {
        WhPickingBatch pb = whPickingBatchDao.getPlzListByCode(pickingCode);
        List<String> boxList = pickingOpLineDao.findBoxCodeByPbId(pb.getId(), new SingleColumnRowMapper<String>(String.class));
        return boxList;
    }

    public boolean initMongodbWhPickingInfo2(String pickingbarCode, Long ouId) {
        boolean msg = false;
        try {
            List<WhPickingBatch> pbList = whPickingBatchDao.getPlzListByBarCode(pickingbarCode);
            if (pbList != null && pbList.size() > 0) {
                PickingList pl = pickingListDao.getByPrimaryKey(pbList.get(0).getPickingList().getId());
                List<StockTransApplication> sto = staDao.findStaByPickingList(pl.getId(), ouId);
                if (null != sto && sto.size() > 0) {
                    if (null != sto.get(0).getIsPreSale() && "1".equals(sto.get(0).getIsPreSale())) {
                        msg = true;
                    }
                }
            }
        } catch (Exception e) {
            msg = false;
        }
        return msg;
    }

    /**
     * 根据拣货编码获取
     * 
     * @param code
     * @return
     */
    public List<MongoDBWhPicking> getMongoDBWhPickingByCode(String code) {
        return (List<MongoDBWhPicking>) mongoOperation.find(new Query(Criteria.where("barCode").is(code)), MongoDBWhPicking.class);
    }

    /**
     * 校验拣货编码是否可用
     * 
     * @param code
     * @return
     */
    public String checkPickingCode(String code) {
        WhPickingBatch pb = whPickingBatchDao.getPlzListByCode(code);
        if (pb != null) {
            PickingList pl = pickingListDao.getByPrimaryKey(pb.getPickingList().getId());
            // 判断是否拣货完成
            if (pl.getPickingStatus() != null && pl.getPickingStatus() == 14) {
                return "此配货批已经拣货完成！";
            }
        } else {
            return "无此拣货编码！";
        }
        return "";
    }

    /**
     * 根据拣货编码获取
     * 
     * @param code
     */
    public MongoDBWhPicking findLocationByPickingCode(String code, int status) {
        MongoDBWhPicking mdwp = mongoOperation.findOne(new Query(Criteria.where("barCode").is(code).and("status").is(status)).with(new Sort(Direction.ASC, "sort", "id")), MongoDBWhPicking.class);
        return mdwp;
    }

    /**
     * 校验条码是否正确
     * 
     * @param skuBarCode
     * @param pickingId
     * @return
     */
    public MongoDBWhPicking checkSkuBarCode(String skuBarCode, Long pickingId) {
        MongoDBWhPicking mdwp = mongoOperation.findOne(new Query(Criteria.where("id").is(pickingId)), MongoDBWhPicking.class);
        if (mdwp != null) {
            for (String bc : mdwp.getBarcodes()) {
                if (skuBarCode.equals(bc)) {
                    return mdwp;
                }
            }
            return null;
        }
        return null;
    }

    /**
     * PDA扫描商品拣货,更新拣货数量
     * 
     * @param skuBarCode
     * @param pickingId
     * @param cCode
     * @param userId
     */
    public String pickingSku(Long pickingId, String cCode, Long userId) {


        MongoDBWhPicking mdwp = mongoOperation.findOne(new Query(Criteria.where("id").is(pickingId)), MongoDBWhPicking.class);
        // 插入操作明细
        int i = pickingOpLineDao.insertPickingOpLine(mdwp.getPickingLineId(), cCode, 1L, userId);
        if (i != 1) {
            return "数据异常！";
        } else {
            pickingOpLineDao.flush();
            int u = pickingBatchBoxDao.updatePickingBatchBoxByLineId(mdwp.getPickingLineId(), cCode);
            if (u == 0) {
                pickingBatchBoxDao.insertPickingBatchBoxByLineId(mdwp.getPickingLineId(), cCode, 1);
            }
        }
        return "";
    }

    /**
     * 周转箱补绑
     * 
     * @param pickingId
     * @param cCode
     * @return
     */
    public String bindBoxByBoxCode(String pickingCode, String cCode) {
        String msg = "";

        msg = checkBox(pickingCode, cCode);
        if (!StringUtil.isEmpty(msg)) {
            return msg;
        }
        WhPickingBatch pb = whPickingBatchDao.getPlzListByCode(pickingCode);
        List<String> boxList = pickingOpLineDao.findBoxCodeByPbId(pb.getId(), new SingleColumnRowMapper<String>(String.class));
        boolean b = true;
        if (boxList != null) {
            for (String box : boxList) {
                if (cCode.equals(box)) {
                    b = false;
                }
            }
        }
        if (b) {
            PickingBatchBox pbb = new PickingBatchBox();
            pbb.setBoxCode(cCode);
            pbb.setPickingBatchId(pb.getId());
            pbb.setQty(0L);
            pickingBatchBoxDao.save(pbb);
            // pickingBatchBoxDao.insertPickingBatchBoxByLineId(mdwp.getPickingLineId(), cCode, 0);
        }

        return "";
    }

    /**
     * 更新剩余数量并返回
     * 
     * @param pickingId
     * @return
     */
    public Long updateMongoDBWhPickingByPicking(Long pickingId, String cCode, Long userId, Boolean isReturnWareHouse) {
        MongoDBWhPicking mdwp = mongoOperation.findOne(new Query(Criteria.where("id").is(pickingId)), MongoDBWhPicking.class);
        Long qty = mdwp.getQty() - 1;
        String msg = null;
        if (isReturnWareHouse) {
            msg = returnWhpickingSku(pickingId, cCode, userId);
        } else {
            msg = pickingSku(pickingId, cCode, userId);
        }
        if (qty < 1L) {
            mongoOperation.remove(new Query(Criteria.where("id").is(pickingId)), MongoDBWhPicking.class);
        } else {
            mongoOperation.updateFirst(new Query(Criteria.where("id").is(pickingId)), new Update().set("qty", qty), MongoDBWhPicking.class);
        }
        if (msg != null && !"".equals(msg)) {
            return -2L;
        }
        return qty;
    }

    /**
     * 根据拣货编码获取短拣信息
     * 
     * @param code
     */
    public List<MongoDBWhPicking> findShortByPickingCode(String code) {
        List<MongoDBWhPicking> mdwp = null;
        mdwp = mongoOperation.find(new Query(Criteria.where("code").is(code)).with(new Sort(Direction.ASC, "locSort")), MongoDBWhPicking.class);
        return mdwp;
    }

    /**
     * 更新wp数据状态
     * 
     * @param pickingId
     */
    public void updateWhPickingStatusByPicking(Long pickingId, Integer status, String code, String picingStatus) {
        if (code != null && !"".equals(code)) {
            if ("1".equals(picingStatus)) {
                pickingLineDao.updatePickingStatus(code);
            }
            mongoOperation.updateFirst(new Query(Criteria.where("code").is(code)), new Update().set("status", status), MongoDBWhPicking.class);
        } else {
            mongoOperation.updateFirst(new Query(Criteria.where("id").is(pickingId)), new Update().set("status", status), MongoDBWhPicking.class);
        }
    }

    /**
     * 二级批次拣货完成
     * 
     * @param code
     */
    public List<Long> pickingBatchOver(String code, Long userId) {
        // String msg = "";
        List<Long> msgIds = new ArrayList<Long>();
        WhPickingBatch pb = whPickingBatchDao.getPlzListByCode(code);
        PickingList pl = pickingListDao.getByPrimaryKey(pb.getPickingList().getId());
        Warehouse w = warehouseDao.getByOuId(pl.getWarehouse().getId());
        // 获取已绑定的周转箱
        List<String> boxList = pickingOpLineDao.findBoxCodeByPbId(pb.getId(), new SingleColumnRowMapper<String>(String.class));
        if (w.getIsAutoWh() != null && w.getIsAutoWh() == true) {
            if (pl.getPickingStatus() == null || pl.getPickingStatus() != 14) {
                if (DefaultStatus.CREATED.equals(pb.getStatus()) && (boxList == null || boxList.size() == 0)) {
                    msgIds.add(-1L);
                    return msgIds;
                }
            }
        }
        // 更新拣货数量
        // pickingLineDao.updatePickingLineQtyByPbId(pb.getId());
        updatePickingLineQtyByPbId(pb.getId());
        // 备份日志
        backupsPickingOpLing(pb.getId());

        List<WhPickingBatch> ls = whPickingBatchDao.getPlzListByPickingListIdAndStatus(pl.getId(), DefaultStatus.CREATED);

        // pda拣货记录结束
        // int i = whPickingBatchDao.updateWhPickingBatchEndTime(code, new Date());
        // System.out.println(i);
        // pb.setEndTime(new Date());
        // end

        WhInfoTimeRef ref2 = whInfoTimeRefDao.getInfoBySlipCode(pl.getCode(), 14, new BeanPropertyRowMapper<WhInfoTimeRef>(WhInfoTimeRef.class));
        // 绑定周转箱
        if (w.getIsAutoWh() != null && w.getIsAutoWh() == true) {
            if (pl.getPickingStatus() == null || pl.getPickingStatus() != 14) {

                if (DefaultStatus.CREATED.equals(pb.getStatus()) && boxList != null && boxList.size() > 0) {
                    if (pl.getCheckMode() == PickingListCheckMode.DEFAULE || pl.getCheckMode() == PickingListCheckMode.PICKING_PACKAGE || pl.getCheckMode() == PickingListCheckMode.PICKING_SPECIAL
                            || pl.getCheckMode() == PickingListCheckMode.PICKING_SECKILL || pl.getCheckMode() == PickingListCheckMode.PICKING_GROUP) {// 多件
                        Zoon z = zoonDao.getByPrimaryKey(pb.getZoon().getId());
                        // 多件绑定
                        msgIds = autoOutboundTurnboxManager.pickingListAndZoneOver(pl.getId(), z.getCode(), boxList, pl.getWarehouse().getId(), userId);
                        // msg = msgId.toString();
                        return msgIds;
                    } else if (pl.getCheckMode() == PickingListCheckMode.PICKING_CHECK) {// 单件
                        List<StockTransApplication> staList = staDao.getStaListByPickingListAndBatchIndex(pl.getId(), pb.getSort().toString());
                        String slipCode = null;
                        for (StockTransApplication sta : staList) {
                            if (sta.getStatus().equals(StockTransApplicationStatus.OCCUPIED)) {
                                slipCode = sta.getRefSlipCode();
                                break;
                            }
                        }
                        if (staList != null && staList.size() > 0 && slipCode == null) {
                            slipCode = staList.get(0).getRefSlipCode();
                        }
                        // 单件绑定
                        String ms = autoOutboundTurnboxManager.bindBatchAndTurnbox(slipCode, boxList.get(0), pl.getId(), pl.getWarehouse().getId(), userId);
                        pb.setStatus(DefaultStatus.SENT);
                        whPickingBatchDao.save(pb);
                        whPickingBatchDao.flush();
                        if (ls.size() == 1) {
                            // 设置结束时间
                            whPickingBatchDao.updateWhPickingBatchEndTime(pb.getBarCode(), new Date());
                        }
                        if (Long.valueOf(ms.split(",")[0]).compareTo(0L) != 0) {
                            // msg = ms.split(",")[0];
                            msgIds.add(4L);
                            msgIds.add(Long.valueOf(ms.split(",")[0]));
                        }
                    }
                }

            }

        } else if (pl.getCheckMode() == PickingListCheckMode.DEFAULE || pl.getCheckMode() == PickingListCheckMode.PICKING_PACKAGE || pl.getCheckMode() == PickingListCheckMode.PICKING_SPECIAL || pl.getCheckMode() == PickingListCheckMode.PICKING_SECKILL
                || pl.getCheckMode() == PickingListCheckMode.PICKING_GROUP) {// 多件
            if (pl.getPickingStatus() == null || pl.getPickingStatus() != 14) {
                if (DefaultStatus.CREATED.equals(pb.getStatus()) && boxList != null && boxList.size() > 0) {
                    Zoon z = zoonDao.getByPrimaryKey(pb.getZoon().getId());
                    // 多件绑定
                    msgIds = autoOutboundTurnboxManager.notAutoPickingListAndZoneOver(pl.getId(), z.getCode(), boxList, pl.getWarehouse().getId(), userId);
                    // msg = msgId.toString();
                    return msgIds;
                }
            }
        } else {
            // 绑定周转箱
            for (String boxCode : boxList) {
                WhContainer tb = whContainerDao.getWhContainerByCode(boxCode);
                if (tb != null) {
                    if (tb.getStatus().equals(DefaultStatus.CREATED) || tb.getWhPickingBatch() == null) {
                        tb.setPickingList(pl);
                        tb.setWhPickingBatch(pb);
                        tb.setStatus(DefaultStatus.EXECUTING);
                        whContainerDao.save(tb);
                    }
                }
            }

            pb.setStatus(DefaultStatus.FINISHED);
            whPickingBatchDao.save(pb);
            whPickingBatchDao.flush();
            if (ls.size() == 1) {
                // 设置结束时间
                whPickingBatchDao.updateWhPickingBatchEndTime(pb.getBarCode(), new Date());
            }

            // 清除mgdb缓存
            // mongoOperation.remove(new Query(Criteria.where("barCode").in(pb.getBarCode())),
            // MongoDBWhPicking.class);
            if (pl.getPickingStartTime() == null) {
                pl.setPickingStartTime(new Date());
            }
            if (pl.getPickingStatus() == null || pl.getPickingStatus() != 14) {
                pl.setPickingStatus(14);
                pl.setPickingEndTime(new Date());
            }
            if (w.getIsCheckPickingStatus() != null && w.getIsCheckPickingStatus()) {
                // 设置批次拣货完成
                ref2 = whInfoTimeRefDao.getInfoBySlipCode(pl.getCode(), 13, new BeanPropertyRowMapper<WhInfoTimeRef>(WhInfoTimeRef.class));
                if (ref2 == null) {
                    whInfoTimeRefDao.insertWhInfoTime2(pl.getCode(), WhInfoTimeRefBillType.STA_PICKING.getValue(), 13, userId, pl.getWarehouse().getId());
                }
                ref2 = whInfoTimeRefDao.getInfoBySlipCode(pl.getCode(), 14, new BeanPropertyRowMapper<WhInfoTimeRef>(WhInfoTimeRef.class));
                if (ref2 == null) {
                    whInfoTimeRefDao.insertWhInfoTime2(pl.getCode(), WhInfoTimeRefBillType.STA_PICKING.getValue(), 14, userId, pl.getWarehouse().getId());
                }
            }
        }
        mongoOperation.remove(new Query(Criteria.where("code").in(pb.getCode())), MongoDBWhPicking.class);
        return msgIds;
    }

    /**
     * 查询短拣信息
     * 
     * @param start
     * @param pageSize
     * @param params
     * @return
     */
    public Pagination<PdaSortPickingCommand> findSortPicking(int start, int pageSize, Map<String, Object> params) {
        return pickingLineDao.findSortPicking(start, pageSize, params, new BeanPropertyRowMapper<PdaSortPickingCommand>(PdaSortPickingCommand.class));
    }

    /**
     * 导出短拣信息
     * 
     * @param start
     * @param pageSize
     * @param params
     * @return
     */
    public List<PdaSortPickingCommand> findSortPickingList(Map<String, Object> params) {
        return pickingLineDao.findSortPickingList(params, new BeanPropertyRowMapper<PdaSortPickingCommand>(PdaSortPickingCommand.class));
    }

    /**
     * 备份操作日志
     * 
     * @param opLineList
     */
    public void backupsPickingOpLing(Long pbId) {
        List<Long> plList = pickingLineDao.getPickingLineListByPbId(pbId);
        for (Long p : plList) {
            List<PickingOpLine> pol = pickingOpLineDao.getPolListByPickingLineId(p);
            if (pol != null) {
                for (PickingOpLine op : pol) {
                    PickingOpLineLog l = new PickingOpLineLog();
                    BeanUtils.copyProperties(op, l);
                    l.setLocationId(op.getLocation().getId());
                    l.setSkuId(op.getSku().getId());
                    l.setPickingLineId(op.getPickingLine().getId());
                    l.setUserId(op.getUser().getId());
                    opLogDao.save(l);
                }
                pickingOpLineDao.deleteAll(pol);
            }
        }
        pickingLineDao.flush();
    }

    /**
     * 备份操作日志-退仓拣货
     * 
     * @param opLineList
     */
    public void backupsPickingOpLing(String batchCode) {
        List<PickingOpLine> pol = pickingOpLineDao.getPickingOpLineByRDCode(batchCode);
        if (pol != null) {
            for (PickingOpLine op : pol) {
                PickingOpLineLog l = new PickingOpLineLog();
                BeanUtils.copyProperties(op, l);
                l.setLocationId(op.getLocation().getId());
                l.setSkuId(op.getSku().getId());
                l.setUserId(op.getUser().getId());
                opLogDao.save(l);
            }
            pickingOpLineDao.deleteAll(pol);
        }

        pickingLineDao.flush();
    }

    /**
     * 根据配货批ID获取打印编码
     * 
     * @param plId
     * @return
     */
    public List<String> findPickingBatchBarCode(Long plId) {
        List<Long> pbIdList = whPickingBatchDao.findWhPickingBatchByNotLine2(plId.toString(), new SingleColumnRowMapper<Long>(Long.class));
        whPickingBatchDao.modifyStaPbId(pbIdList);
        whPickingBatchDao.deleteAllByPrimaryKey(pbIdList);
        whPickingBatchDao.flush();

        List<String> barCodes = new ArrayList<String>();
        Set<String> bs = new HashSet<String>();
        List<WhPickingBatch> pbList = whPickingBatchDao.getPlzListByPickingListId(plId);
        if (pbList != null) {
            for (WhPickingBatch pb : pbList) {
                bs.add(pb.getBarCode());
            }
        }
        barCodes.addAll(bs);
        return barCodes;
    }

    /**
     * 根据多个配货批ID获取打印编码
     * 
     * @param plId
     * @return
     */
    public List<String> findPickingBatchBarCodeS(String plIdS) {
        List<Long> pbIdList = whPickingBatchDao.findWhPickingBatchByNotLine2(plIdS, new SingleColumnRowMapper<Long>(Long.class));
        whPickingBatchDao.modifyStaPbId(pbIdList);
        whPickingBatchDao.deleteAllByPrimaryKey(pbIdList);
        whPickingBatchDao.flush();

        List<String> barCodes = new ArrayList<String>();
        barCodes = whPickingBatchDao.findPlzListByPickingListIdS(plIdS, new SingleColumnRowMapper<String>(String.class));
        return barCodes;
    }

    public void updatePickingLineQtyByPbId(Long pbId) {
        List<Long> plList = pickingLineDao.getPickingLineListByPbId(pbId);
        for (Long p : plList) {
            Long plqty = pickingLineDao.findPlQtyByPickingLineId(p, new SingleColumnRowMapper<Long>(Long.class));
            Long qty = pickingLineDao.findQtyByPickingLineId(p, new SingleColumnRowMapper<Long>(Long.class));
            if (plqty == null) {
                plqty = 0L;
            }
            if (qty == null) {
                qty = 0L;
            }
            pickingLineDao.updatePickingLineQtyByPlIdAndQty(p, plqty + qty);
        }
    }

    /**
     * PDA拣货 校验周转箱是否可用
     * 
     * @param pickingBarCode
     * @param boxCode
     * @return
     */
    public String checkBox(String code, String boxCode) {
        WhContainer wcn = whContainerDao.getWhContainerByCode(boxCode);
        WhPickingBatch pbList = whPickingBatchDao.getPlzListByCode(code);
        List<WhContainer> wcList = whContainerDao.getWcByPbId(pbList.getId());
        List<String> boxList = pickingOpLineDao.findBoxCodeByPbId(pbList.getId(), new SingleColumnRowMapper<String>(String.class));
        PickingList p = pickingListDao.getByPrimaryKey(pbList.getPickingList().getId());
        Warehouse w = warehouseDao.getByOuId(p.getWarehouse().getId());
        if (wcn != null) {
            if (wcn.getStaId() != null) {
                StockTransApplication sta = staDao.getByPrimaryKey(wcn.getStaId());
                return "此周转箱已被作业单" + sta.getCode() + "占用！";
            }
            if (wcn.getPickingList() != null && wcn.getPickingList().getId() != null && wcn.getWhPickingBatch() != null) {
                PickingList pl = pickingListDao.getByPrimaryKey(wcn.getPickingList().getId());
                if (!wcn.getPickingList().getId().equals(pbList.getPickingList().getId())) {
                    return "此周转箱已被" + pl.getCode() + "占用！";
                } else {
                    // if (w.getIsAutoWh() != null && w.getIsAutoWh() == true && p.getCheckMode() ==
                    // PickingListCheckMode.PICKING_CHECK) {
                    if (!wcn.getWhPickingBatch().getId().equals(pbList.getId())) {
                        return "此周转箱已被其他小批次占用！";
                    }
                    // }
                }
            } else if (wcList != null && wcList.size() > 0) {
                if (w.getIsAutoWh() != null && w.getIsAutoWh() == true && p.getCheckMode() == PickingListCheckMode.PICKING_CHECK) {
                    if (!boxCode.equals(wcList.get(0).getCode())) {
                        return "此单件配货小批次已绑定周转箱" + wcList.get(0).getCode();
                    }
                }
            } else if (boxList != null && boxList.size() > 0) {
                if (w.getIsAutoWh() != null && w.getIsAutoWh() == true && p.getCheckMode() == PickingListCheckMode.PICKING_CHECK) {
                    if (!boxList.contains(boxCode)) {
                        return "此单件配货小批次已绑定周转箱" + boxList.get(0);
                    }
                }
            }
        } else {
            return "无此周转箱！";
        }
        if (wcn != null) {
            if (wcn.getStatus().equals(DefaultStatus.CREATED)) {
                wcn.setPickingList(p);
                // wcn.setWhPickingBatch(pbList);
                wcn.setStatus(DefaultStatus.EXECUTING);
                whContainerDao.save(wcn);
            }
        }
        return "";
    }

    /**
     * 获取已拣货的数量
     * 
     * @param code
     * @param cCode
     * @return
     */
    public Integer getPickingNum(String code, String cCode) {
        return pickingLineDao.getPickingNum(code, cCode, new SingleColumnRowMapper<Integer>(Integer.class));
    }

    /**
     * 如有取消的作业单则重置拣货数量
     * 
     * @param pickingId
     */
    public void resetPickingQtyByCancelSta(Long pickingId) {
        PickingList pl = pickingListDao.getByPrimaryKey(pickingId);
        if (pl == null || (pl.getPickingStatus() != null && pl.getPickingStatus() >= 13)) {
            return;
        }
        pickingLineDao.deletePickingLineByPickingId(pickingId);

        Warehouse wh = warehouseDao.getByOuId(pl.getWarehouse().getId());
        boolean isAutoWh = false; // 是否自动化仓
        if (wh != null && wh.getIsAutoWh() != null && wh.getIsAutoWh() == true) {
            isAutoWh = true;
        }

        if (pl.getCheckMode() == PickingListCheckMode.DEFAULE || pl.getCheckMode() == PickingListCheckMode.PICKING_SPECIAL || pl.getCheckMode() == PickingListCheckMode.PICKING_SECKILL || pl.getCheckMode() == PickingListCheckMode.PICKING_GROUP) {
            pickingLineDao.savePickingLineByPickingList(pl.getId());
        } else if (isAutoWh && pl.getCheckMode() == PickingListCheckMode.PICKING_CHECK) {
            pickingLineDao.savePickingLineBySingle(pl.getId());
        } else if (!isAutoWh) {
            pickingLineDao.savePickingLineByGeneral(pl.getId());
        }

        // 删除多余的小批次
        if (isAutoWh && pl.getCheckMode() == PickingListCheckMode.PICKING_CHECK) {
            List<Long> pb = pickingLineDao.findInvalidPickingBatchByPickingId(pickingId, new SingleColumnRowMapper<Long>(Long.class));
            List<WhPickingBatch> deleteId = new ArrayList<WhPickingBatch>();
            if (pb != null && pb.size() > 0) {
                for (Long wpbcId : pb) {
                    boolean b = true;
                    List<StockTransApplication> staList = staDao.getStaListByPickingBatch(wpbcId);
                    if (staList != null && staList.size() > 0) {
                        for (StockTransApplication st : staList) {
                            if (st.getStatus() == StockTransApplicationStatus.CANCELED) {
                                st.setWhPickingBatch(null);
                            } else {
                                b = false;
                            }
                        }
                        if (b) {
                            deleteId.add(whPickingBatchDao.getByPrimaryKey(wpbcId));
                        }

                    }
                }
                if (deleteId != null && deleteId.size() > 0) {
                    whPickingBatchDao.deleteAll(deleteId);
                }
            }

        } else {

            // 删除无效的小批次
            pickingLineDao.deleteInvalidPickingBatchByPickingId(pickingId);
        }

    }

    /**
     * 根据拣货条码重置数量
     * 
     * @param barCode
     */
    public void resetPickingQtyByBarcode(String barCode) {
        if (barCode != null && !"".equals(barCode)) {
            PickingList pl = whPickingBatchDao.getPkListByBarCode(barCode);
            if (pl != null) {
                resetPickingQtyByCancelSta(pl.getId());
            }
        }
    }

    /**
     * 校验此周转箱是否可以人工集货
     * 
     * @param code
     * @return
     */
    @Override
    public String checkCollectionBox(String code) {
        return goodsCollectionDao.checkCollectionBox(code, new SingleColumnRowMapper<String>(String.class));
    }


    @Override
    public String collectionBox(String code, Long ouId, Long userId) {
        // 将周转箱设为已集货状态
        WhContainer wc = whContainerDao.getWhContainerByCode(code);
        if (wc == null) {
            throw new BusinessException(ErrorCode.COLLECTION_CODE_NOT_EXISTS);
        }
        wc.setIsReceive(true);
        whContainerDao.save(wc);
        whContainerDao.flush();
        // 查询此批次是否还有周转箱没有集齐
        List<Long> box = goodsCollectionDao.findWaitBox(code, new SingleColumnRowMapper<Long>(Long.class));
        PickingList p = pickingListDao.getByPrimaryKey(wc.getPickingList().getId());
        GoodsCollection gc = goodsCollectionDao.getGoodsCollectionByPickingCode(p.getCode());
        if (gc.getStartTime() == null) {
            gc.setStartTime(new Date());
        }

        // 记录日志
        GoodsCollectionLog gcl = new GoodsCollectionLog();
        gcl.setContainerCode(wc.getCode());
        gcl.setCollectionCode(gc.getCollectionCode());
        gcl.setOpTime(new Date());
        gcl.setOpUser(userId);
        gcl.setPickingCode(p.getCode());
        gcl.setStatus("集货");
        goodsCollectionLogDao.save(gcl);

        if (box == null || box.size() == 0) {
            gc.setStatus(3);

            return p.getCode();
        }
        return null;
    }


    @Override
    public String moveCollectionBox(String pickingCode, String collectionCode, boolean b, Long ouId, Long userId) {
        // 移走周转箱
        GoodsCollection gc = null;
        if (pickingCode != null) {
            // gc = goodsCollectionDao.getGoodsCollectionByPickingCode(pickingCode);
            gc = goodsCollectionDao.findGoodsCollectionByPlCodeSql(pickingCode, new BeanPropertyRowMapper<GoodsCollection>(GoodsCollection.class));
            if (gc == null) {
                return "此配货批次不存在，或已被移走！";
            }
        } else {
            PhysicalWarehouse pw = physicalWarehouseDao.findPhysicalWarehouseByWhOuId(ouId, new BeanPropertyRowMapper<PhysicalWarehouse>(PhysicalWarehouse.class));
            // gc = goodsCollectionDao.getGoodsCollectionByCode(collectionCode, pw.getId());
            gc = goodsCollectionDao.findGoodsCollectionByCodeSql(collectionCode, pw.getId(), new BeanPropertyRowMapper<GoodsCollection>(GoodsCollection.class));
            if (gc == null) {
                return "此集货区域不存在！";
            }
        }

        if (gc.getStatus() == 3 || b) {
            goodsCollectionLogDao.resetGoodsCollectionLogById(gc.getId(), userId);
            goodsCollectionDao.resetGoodsCollectionById(gc.getId());
            /*
             * gc.setStatus(1); gc.setPickinglist(null); gc.setStartTime(null);
             * goodsCollectionDao.save(gc);
             */
        } else if (gc.getStatus() == 1) {
            return "该集货库位未被占用！";
        } else {
            return "1";
            // return "该集货库位未被占用，无法移动!";
        }
        return null;
    }

    /**
     * 二次分拣完成，释放集货库位
     * 
     * @param pickingCode
     * @param collectionCode
     * @param ouId
     * @return
     */
    public String moveCollectionBoxByPickingOver(String pickingCode, String collectionCode, Long ouId, Long userId) {
        // 移走周转箱
        GoodsCollection gc = null;
        if (pickingCode != null) {
            // gc = goodsCollectionDao.getGoodsCollectionByPickingCode(pickingCode);
            gc = goodsCollectionDao.findGoodsCollectionByPlCodeSql(pickingCode, new BeanPropertyRowMapper<GoodsCollection>(GoodsCollection.class));
            if (gc == null) {
                return "此配货批次不存在，或已被移走！";
            }
        } else {
            PhysicalWarehouse pw = physicalWarehouseDao.findPhysicalWarehouseByWhOuId(ouId, new BeanPropertyRowMapper<PhysicalWarehouse>(PhysicalWarehouse.class));
            // gc = goodsCollectionDao.getGoodsCollectionByCode(collectionCode, pw.getId());
            gc = goodsCollectionDao.findGoodsCollectionByCodeSql(collectionCode, pw.getId(), new BeanPropertyRowMapper<GoodsCollection>(GoodsCollection.class));
            if (gc == null) {
                return "此集货区域不存在！";
            }
        }

        GoodsCollection g = goodsCollectionDao.findTwoPickingOverIsOver(gc.getId(), new BeanPropertyRowMapper<GoodsCollection>(GoodsCollection.class));
        if (g == null) {
            return "此集货区域的批次二次分拣未完成！";
        }

        goodsCollectionLogDao.resetGoodsCollectionLogById(gc.getId(), userId);
        goodsCollectionDao.resetGoodsCollectionById(gc.getId());
        return null;
    }

    /**
     * 查询集货区域状态
     * 
     * @param pickingCode
     * @param collectionCode
     * @param ouId
     * @return
     */
    public Map<String, Object> queryCollectionBoxStatus(String pickingCode, String collectionCode, Long ouId) {
        Map<String, Object> msgMap = new HashMap<String, Object>();
        String msg = null;


        GoodsCollection gc = null;
        if (pickingCode != null) {
            gc = goodsCollectionDao.getGoodsCollectionByPickingCode(pickingCode);
            if (gc == null) {
                msg = "此配货批次不存在，或已被移走！";
            }
        } else {
            PhysicalWarehouse pw = physicalWarehouseDao.findPhysicalWarehouseByWhOuId(ouId, new BeanPropertyRowMapper<PhysicalWarehouse>(PhysicalWarehouse.class));
            gc = goodsCollectionDao.getGoodsCollectionByCode(collectionCode, pw.getId());
            if (gc == null) {
                msg = "该集货库位不存在！";
            }
        }
        if (msg == null || "".equals(msg)) {

            if (gc.getStatus() == 3) {
                List<WhContainer> wcList = whContainerDao.getWcByPickId(gc.getPickinglist().getId());
                if (wcList != null) {
                    String wcStr = "";
                    for (WhContainer wc : wcList) {
                        wcStr += wc.getCode() + ",";
                    }
                    msgMap.put("wcList", wcStr);
                }
            } else if (gc.getStatus() == 1) {
                msg = "该集货库位未被占用！";
            } else {
                msg = "该集货库位配货批次周转箱未集满！";
            }
        }
        msgMap.put("msg", msg);
        return msgMap;
    }


    /**
     * 重新推荐集货区域
     */
    public List<Long> anewRecommendCollection(String code) {
        List<Long> msgIds = new ArrayList<Long>();
        // 1.根据编码找批次
        PickingList pl = pickingListDao.getByCode(code);
        if (pl == null) {
            pl = whPickingBatchDao.getPkListByBarCode(code);
            if (pl == null) {
                WhContainer wc = whContainerDao.getWhContainerByCode(code);
                if (wc == null) {
                    // 此编码不存在
                    throw new BusinessException(ErrorCode.TURNOVERBOX_CODE_NOTFOUND);
                }
                if (wc.getPickingList() == null) {
                    // 此周转箱未被使用
                    throw new BusinessException(ErrorCode.TURNOVERBOX_UNUSED);
                }
                pl = pickingListDao.getByPrimaryKey(wc.getPickingList().getId());
            }
        }
        if (pl.getPickModleType() != null && pl.getPickModleType() == 2) {
            // 此批次已经推荐到自动化集货区域，无需再次推荐
            throw new BusinessException(ErrorCode.TURNOVERBOX_PICKING_AUTO_EXIST);
        }
        GoodsCollection gc = goodsCollectionDao.getGoodsCollectionByPickingCode(pl.getCode());
        if (gc != null) {
            // 此批次已经推荐到人工集货区域，集货区域编码{}
            throw new BusinessException(ErrorCode.TURNOVERBOX_PICKING_NOT_AUTO_EXIST, new Object[] {gc.getCollectionCode()});
        }

        // 2.判断集货的上限
        Warehouse w = warehouseDao.getByOuId(pl.getWarehouse().getId());
        boolean noBind = false;
        boolean b = true;
        List<Long> pickIdList = whContainerDao.findContainerPickId(w.getAutoSeedGroup(), new SingleColumnRowMapper<Long>(Long.class));
        List<WhPickingBatch> pbList = whPickingBatchDao.getPlzListByPickingListId(pl.getId());

        if (pbList == null || pbList.size() == 0) {
            // 此批次没有拣货区域拣货完成
            throw new BusinessException(ErrorCode.TURNOVERBOX_PICKING_BATCH_NOT_OVER);
        }

        if (pickIdList != null && pickIdList.size() >= w.getAutoPickinglistLimit()) {// 当批次的数量超过限制的时候，如果此批次已经在集货口，那么就继续往下走，否则就抛异常
            b = false;
            for (Long pickId : pickIdList) {
                if (pl.getId().equals(pickId)) {
                    b = true;
                }
            }
        }
        // 人工集货是否到达上限
        Long qty = goodsCollectionDao.countCollectionByStatus(1, pl.getWarehouse().getId(), new SingleColumnRowMapper<Long>(Long.class));
        Long collectionQty = goodsCollectionDao.countCollectionByStatus(2, pl.getWarehouse().getId(), new SingleColumnRowMapper<Long>(Long.class));
        Long collectionOverQty = goodsCollectionDao.countCollectionByStatus(3, pl.getWarehouse().getId(), new SingleColumnRowMapper<Long>(Long.class));
        Integer limit = w.getManpowerPickinglistLimit();

        // 是否支持跨区人工集货

        if (qty == 0 || (limit != null && limit <= (collectionQty.intValue() + collectionOverQty.intValue())) || (w.getIsManpowerConsolidation() == true && pbList.size() <= 1)) {

            // 仓库中配货批次数量已经达到上限，请稍后再为该批次绑定周转箱！
            noBind = true;
        }

        if ((!b && noBind) || (!w.getIsAutoWh() && noBind)) {
            // 集货区域已达到上限，无法推荐
            throw new BusinessException(ErrorCode.TURNOVERBOX_COLLECTION_PICKING_LIMIT);
        }

        // 3.判断是否有未完成的集货区域,并统计需要推送的周转箱
        boolean over = true;
        List<WhContainer> overWcList = new ArrayList<WhContainer>();
        for (WhPickingBatch wpb : pbList) {
            if (DefaultStatus.CREATED.equals(wpb.getStatus())) {
                over = false;
            } else {
                List<WhContainer> wcList = whContainerDao.getWcByPbId(wpb.getId());
                overWcList.addAll(wcList);
                if (w.getIsAutoWh()) {
                    wpb.setStatus(DefaultStatus.SENT);
                    // whPickingBatchDao.save(wpb);
                }
            }
        }

        if (overWcList.size() == 0) {
            // 此批次没有拣货区域拣货完成
            throw new BusinessException(ErrorCode.TURNOVERBOX_PICKING_BATCH_NOT_OVER);
        }

        if (w.getIsAutoWh() && b) {
            if (pl.getGoodsCollectionType() == null || pl.getGoodsCollectionType() != 2) {
                pl.setGoodsCollectionType(2);
            }
            List<MsgToWcsPickingOverRequest> porList = new ArrayList<MsgToWcsPickingOverRequest>();
            for (WhContainer wc : overWcList) {

                MsgToWcsPickingOverRequest po = new MsgToWcsPickingOverRequest();
                po.setCategoryMode("1");
                po.setContainerNO(wc.getCode());
                po.setWaveOrderr(pl.getCode());
                String boCode = autoOutboundTurnboxManager.findCheckingCode(pl);
                if (!StringUtil.isEmpty(boCode)) {
                    po.setDestinationNO(boCode);
                } else if (PickingListCheckMode.DEFAULE.equals(pl.getCheckMode()) || PickingListCheckMode.PICKING_SPECIAL.equals(pl.getCheckMode())) {// 判断是否是多件
                    // 多件匹配播种墙逻辑
                    boCode = autoOutboundTurnboxManager.checkBozRole(pl.getStaList(), pl.getWarehouse().getId(), pl.getId());
                    if (StringUtils.hasLength(boCode)) {
                        po.setDestinationNO(boCode);
                    } else {
                        po.setDestinationNO(w.getSeedingAreaCode());
                    }
                }

                if (over) {// 设置完结标识
                    if (pl.getBoxCount() == null) {
                        pl.setBoxCount(1);
                    }
                    po.setEndingTag(pl.getBoxCount().toString());
                    po.setCount(pl.getBoxCount().toString());
                    over = false;
                }
                porList.add(po);
            }

            String context = JsonUtil.collection2jsonStr(porList);
            // 保存数据到中间表
            MsgToWcs msg = new MsgToWcs();
            msg.setContext(context);
            msg.setPickingListCode(pl.getCode());
            msg.setCreateTime(new Date());
            msg.setErrorCount(0);
            msg.setStatus(true);
            msg.setType(WcsInterfaceType.OShouRongQi);
            msgToWcsDao.save(msg);
            msgToWcsDao.flush();
            msgIds.add(4L);
            msgIds.add(msg.getId());
        } else if (w.getIsAutoWh()) {
            List<String> boxCode = new ArrayList<String>();
            for (WhContainer wc : overWcList) {
                boxCode.add(wc.getCode());
            }
            msgIds = autoOutboundTurnboxManager.generateToWcsMsg(boxCode, pl, pl.getWarehouse().getId());
            if (pl.getGoodsCollectionType() == null || pl.getGoodsCollectionType() != 3) {
                pl.setGoodsCollectionType(3);
            }
        } else {
            if (pl.getGoodsCollectionType() == null || pl.getGoodsCollectionType() != 3) {
                pl.setGoodsCollectionType(3);
            }

            Long gcId = goodsCollectionDao.recommendCollectionCode(null, pl.getWarehouse().getId(), new SingleColumnRowMapper<Long>(Long.class));
            if (gcId == null) {
                // 集货区域已达到上限，无法推荐
                throw new BusinessException(ErrorCode.TURNOVERBOX_COLLECTION_PICKING_LIMIT);
            }
            gc = goodsCollectionDao.getByPrimaryKey(gcId);
            gc.setPickinglist(pl);
            gc.setStartTime(null);
            gc.setStatus(2);
            goodsCollectionDao.save(gc);
        }
        return msgIds;


    }


    @Override
    public String moveCollectionBox2(String pickingCode, String collectionCode, boolean b, Long ouId, Long userId) {
        // 移走周转箱
        GoodsCollection gc = null;
        if (pickingCode != null) {
            gc = goodsCollectionDao.getGoodsCollectionByPickingCode(pickingCode);
            if (gc == null) {
                return "此配货批次不存在，或已被移走！";
            }
        } else {
            PhysicalWarehouse pw = physicalWarehouseDao.findPhysicalWarehouseByWhOuId(ouId, new BeanPropertyRowMapper<PhysicalWarehouse>(PhysicalWarehouse.class));
            gc = goodsCollectionDao.getGoodsCollectionByCode(collectionCode, pw.getId());
            if (gc == null) {
                return "此集货区域不存在！";
            }
        }

        goodsCollectionLogDao.resetGoodsCollectionLogById(gc.getId(), userId);

        gc.setStatus(1);
        gc.setPickinglist(null);
        gc.setStartTime(null);
        goodsCollectionDao.save(gc);
        return null;
    }

    @Override
    public Map<String, String> findCollectionQty(Long ouId) {
        Map<String, String> map = new HashMap<String, String>();
        Long collectionQty = goodsCollectionDao.countCollectionByStatus(2, ouId, new SingleColumnRowMapper<Long>(Long.class));
        Long collectionOverQty = goodsCollectionDao.countCollectionByStatus(3, ouId, new SingleColumnRowMapper<Long>(Long.class));
        String collectionOverCode = goodsCollectionDao.recommendMoveCollectionCode(ouId, new SingleColumnRowMapper<String>(String.class));

        map.put("collectionQty", String.valueOf(collectionQty));
        map.put("collectionOverQty", String.valueOf(collectionOverQty));
        map.put("collectionOverCode", collectionOverCode);

        return map;
    }


    /**
     * 校验作业单是否是当前小批次下
     * 
     * @param slipCode
     * @param pickingCode
     * @param ouId
     * @return
     */
    public Boolean checkStaAndBatch(String slipCode, String pickingCode, Long ouId) {
        List<StockTransApplication> staList = staDao.findBySlipCodeOuId(slipCode, ouId);
        WhPickingBatch wpb = whPickingBatchDao.getPlzListByCode(pickingCode);
        if (staList != null && staList.size() > 0 && wpb != null) {
            for (StockTransApplication sta : staList) {
                Integer sort = wpb.getSort();
                if (sort != null) {
                    if (sort.toString().equals(sta.getIdx1())) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * 是否是自动化仓单件
     * 
     * @param pickingCode
     * @return
     */
    public Boolean checkAutoSingle(String pickingCode) {
        WhPickingBatch pb = whPickingBatchDao.getPlzListByCode(pickingCode);
        PickingList pl = pickingListDao.getByPrimaryKey(pb.getPickingList().getId());
        Warehouse w = warehouseDao.getByOuId(pl.getWarehouse().getId());

        if (w.getIsAutoWh() && pl.getCheckMode() == PickingListCheckMode.PICKING_CHECK) {// 单件
            return true;
        }
        return false;
    }

    /**
     * 查询已经二次分拣完成的集货库位
     * 
     * @param ouId
     * @return
     */
    public List<GoodsCollectionCommand> findTwoPickingOver(Long ouId) {
        return goodsCollectionDao.findTwoPickingOver(ouId, new BeanPropertyRowMapper<GoodsCollectionCommand>(GoodsCollectionCommand.class));
    }


    @Override
    public Pagination<GoodsCollectionLog> findGoodsCollectionLog(int start, int pageSize, Map<String, Object> params) {

        return goodsCollectionLogDao.findGoodsCollectionLog(start, pageSize, params, new BeanPropertyRowMapper<GoodsCollectionLog>(GoodsCollectionLog.class));
    }


    @Override
    public String initMongodbWhPickingInfoRTWareHouse(String pickingbarCode, Long userId, Long ouId) {
        RtwDieking rt = rtwDieKingDao.findRtwDiekingListByCode(pickingbarCode);
        if (rt != null) {
            Warehouse w = warehouseDao.getByOuId(rt.getMainWhId());
            if (!ouId.equals(rt.getMainWhId())) {
                return "此配货批不在此仓库！";
            }
            if (rt.getStatus() == 15 || rt.getStatus() == 17) {
                return "此配货批次已取消！";
            }
            if (rt.getStatus() == 10) {
                return "拣货批已完成";
            }
            // 拣货记录开始
            User u = userDao.getByPrimaryKey(userId);
            // rtwDieKingDao.updateStartTimeAndUser(pickingbarCode, userId, u.getLoginName());
            // RtwDieking rt1 = rtwDieKingDao.findRtwDiekingListByCode(pickingbarCode);
            //rt.setCreateId(userId);// 拣货人ID
            rt.setBeginDiekingTime(new Date());// 开始拣货时间
            if (!rt.getStatus().equals(3)) {
                rt.setStatus(2);// 拣货状态
            }
            rt.setOperatingUser(u.getLoginName());// 拣货人
            rtwDieKingDao.save(rt);
            // 清除mgdb缓存
            mongoOperation.remove(new Query(Criteria.where("barCode").in(pickingbarCode)), MongoDBWhPicking.class);
            // 备份日志
            backupsPickingOpLing(pickingbarCode);
            List<MongoDBWhPicking> wp = rtwDieKingDao.findMongodbInfoByBatchCode(pickingbarCode, new BeanPropertyRowMapper<MongoDBWhPicking>(MongoDBWhPicking.class));
            // 获取商品的所有条码
            for (MongoDBWhPicking p : wp) {
                Set<String> skuBarcodes = new HashSet<String>();

                Sku sku = skuDao.getByPrimaryKey(p.getSkuId());
                skuBarcodes.add(sku.getBarCode());
                List<SkuBarcode> sbs = skuBarcodeDao.findBySkuId(p.getSkuId());
                if (sbs != null) {
                    for (SkuBarcode sb : sbs) {
                        skuBarcodes.add(sb.getBarcode());
                    }
                }
                p.setBarcodes(skuBarcodes);
                // 保存数据到mongodb
                mongoOperation.save(p);
            }
            if (w.getIsCheckPickingStatus() != null && w.getIsCheckPickingStatus()) {
                // 配货批开始
                WhInfoTimeRef ref2 = whInfoTimeRefDao.getInfoBySlipCode(pickingbarCode, 13, new BeanPropertyRowMapper<WhInfoTimeRef>(WhInfoTimeRef.class));
                if (ref2 == null) {
                    whInfoTimeRefDao.insertWhInfoTime2(pickingbarCode, WhInfoTimeRefBillType.STA_PICKING.getValue(), 13, userId, ouId);
                }
            }
        } else {
            return "无此拣货编码！";
        }
        return "";
    }

    /**
     * 查看此拣货批是否是VAS
     * 
     * @param pickingbarCode
     * @return
     */
    public Boolean orderIsVas(String pickingbarCode) {
        RtwDieking rt = rtwDieKingDao.findRtwDiekingListByCode(pickingbarCode);
        return rt.getIsVas();
    }

    @Override
    public void updatePickingQtyByBarcode(String barCode, Long ouId) {
        List<PickingOpLine> poLine = pickingOpLineDao.getPickingOpLineByDiekingCode(barCode, new BeanPropertyRowMapper<PickingOpLine>(PickingOpLine.class));
        List<PickingOpLine> poLine1 = pickingOpLineDao.getPickingOpLineByDiekingCode1(barCode, new BeanPropertyRowMapper<PickingOpLine>(PickingOpLine.class));
        // List<PickingOpLine> poLine2 = pickingOpLineDao.getPickingOpLineByDiekingCode2(barCode,
        // new BeanPropertyRowMapper<PickingOpLine>(PickingOpLine.class));
        // 根据拣货批次号查询拣货批次id
        Long id = rtwDieKingDao.findIdByBatchCode(barCode, new SingleColumnRowMapper<Long>(Long.class));
        // 更新退仓拣货明细信息
        RtwDieking rd = new RtwDieking();
        for (PickingOpLine pickingOpLine : poLine) {
            rtwDieKingLineDao.updateRtwDieKingLine(pickingOpLine.getId(), pickingOpLine.getLocationCode(), id, pickingOpLine.getQty());
            if (rd.getRealityQuantity() != null) {
                rd.setRealityQuantity(rd.getRealityQuantity() + pickingOpLine.getQty());
            } else {
                rd.setRealityQuantity(pickingOpLine.getQty());
            }
        }
        // 更新退仓明细头
        if (rd.getRealityQuantity() != null && !"".equals(rd.getRealityQuantity())) {
            rtwDieKingDao.updateRealityQuantityById(rd.getRealityQuantity(), id);
        }
        // 绑定周转箱
        String code = rtwDieKingDao.getByPrimaryKey(id).getStaCode();
        StockTransApplication sta = staDao.getByCode(code);
        Long pId = staDao.getPickingListIdByCode(code, new SingleColumnRowMapper<Long>(Long.class));
        // 插入退仓拣货明细日志
        for (PickingOpLine pickingOpLine : poLine1) {
            RtwDiekingLineLog rdLog = new RtwDiekingLineLog();
            Sku sku = skuDao.getByPrimaryKey(pickingOpLine.getId());
            rdLog.setBoxCode(pickingOpLine.getContainerCode());
            rdLog.setDiekingQuantity(pickingOpLine.getQty());
            rdLog.setMainWhId(ouId);
            rdLog.setSkuBarCode(sku.getBarCode());
            rdLog.setSkuCode(sku.getCode());
            rdLog.setSkuKeyProperties(sku.getKeyProperties());
            rdLog.setSkuName(sku.getName());
            rdLog.setSkuSupplierCode(sku.getSupplierCode());
            rdLog.setSkuInvStatus(pickingOpLine.getSkuInvStatus());
            rdLog.setRtwDiekingId(id);
            rtwDieKingLineLogDao.save(rdLog);
            whContainerDao.bindingContainer(pId, sta.getId(), id, pickingOpLine.getContainerCode());
        }
        rtwDieKingDao.flush();
    }

    @Override
    public String returnWhCheckBox(String code, String boxCode) {
        WhContainer wcn = whContainerDao.getWhContainerByCode(boxCode);
        // WhPickingBatch pbList = whPickingBatchDao.getPlzListByCode(code);
        RtwDieking rt = rtwDieKingDao.findRtwDiekingListByCode(code);
        // List<WhContainer> wcList = whContainerDao.getWcByDekingId(rt.getId());
        Long pId = staDao.getPickingListIdByCode(rt.getStaCode(), new SingleColumnRowMapper<Long>(Long.class));
        // Warehouse w = warehouseDao.getByOuId(rt.getMainWhId());
        if (wcn != null) {
            if (wcn.getStaId() != null) {
                StockTransApplication sta = staDao.getByPrimaryKey(wcn.getStaId());
                // 验证周装箱是不是被此拣货批次对应作业单占用
                if (!sta.getCode().equals(rt.getStaCode())) {
                    return "此周转箱已被作业单" + sta.getCode() + "占用！";
                }
            }
            if (wcn.getPickingList() != null && wcn.getPickingList().getId() != null) {
                PickingList pl = pickingListDao.getByPrimaryKey(wcn.getPickingList().getId());
                if (!wcn.getPickingList().getId().equals(pId)) {
                    return "此周转箱已被" + pl.getCode() + "占用！";
                }
                // else {
                // if (!wcn.getWhPickingBatch().getId().equals(rt.getId())) {
                // return "此周转箱已被其他小批次占用！";
                // }
                // }
            }
            // 判断周转箱是不是对应拣货批次绑定的周转箱
            if (null != wcn.getDiekingId()) {
                if (!wcn.getDiekingId().equals(rt.getId())) {
                    return "此周转箱已被其他拣货批次占用！";
                }
            }
            // else if (wcList != null && wcList.size() > 0) {
            // if (w.getIsAutoWh() != null && w.getIsAutoWh() == true) {
            // if (!boxCode.equals(wcList.get(0).getCode())) {
            // }
            // }
            // }
        } else {
            return "无此周转箱！";
        }
        if (wcn != null) {
            if (wcn.getStatus().equals(DefaultStatus.CREATED)) {
                StockTransApplication sta = staDao.getByCode(rt.getStaCode());
                wcn.setStaId(sta.getId());
                wcn.setPickingList(sta.getPickingList());
                wcn.setDiekingId(rt.getId());
                wcn.setStatus(DefaultStatus.EXECUTING);
                whContainerDao.save(wcn);
            }
        }
        return "";
    }


    @Override
    public List<Long> returnWhousePickingBatchOver(String pickingbarCode, Long userId, Long ouId) {
        // 更新拣货数量
        updatePickingQtyByBarcode(pickingbarCode, ouId);
        RtwDieking rt = rtwDieKingDao.findRtwDiekingListByCode(pickingbarCode);
        if (rt != null) {
            Warehouse w = warehouseDao.getByOuId(rt.getMainWhId());
            // 拣货结束
            User u = userDao.getByPrimaryKey(userId);
            RtwDieking rt1 = rtwDieKingDao.findRtwDiekingListByCode(pickingbarCode);
            if (rt.getPlanQuantity() > (rt.getRealityQuantity() == null ? 0 : rt.getRealityQuantity())) {
                // 短拣状态
                rt1.setStatus(3);
                rt1.setEndDiekingTime(new Date());
            } else if (rt.getPlanQuantity() == (rt.getRealityQuantity() == null ? 0 : rt.getRealityQuantity())) {
                // 拣货完成
                rt1.setStatus(10);
                rt1.setEndDiekingTime(new Date());
            }
            rtwDieKingDao.save(rt1);
            rtwDieKingDao.updateStartTimeAndUser(pickingbarCode, userId, u.getLoginName());
            // 清除mgdb缓存
            mongoOperation.remove(new Query(Criteria.where("barCode").in(pickingbarCode)), MongoDBWhPicking.class);
            // 备份日志
            backupsPickingOpLing(pickingbarCode);
            List<MongoDBWhPicking> wp = rtwDieKingDao.findMongodbInfoByBatchCode(pickingbarCode, new BeanPropertyRowMapper<MongoDBWhPicking>(MongoDBWhPicking.class));
            // 获取商品的所有条码
            for (MongoDBWhPicking p : wp) {
                Set<String> skuBarcodes = new HashSet<String>();
                Sku sku = skuDao.getByPrimaryKey(p.getSkuId());
                skuBarcodes.add(sku.getBarCode());
                List<SkuBarcode> sbs = skuBarcodeDao.findBySkuId(p.getSkuId());
                if (sbs != null) {
                    for (SkuBarcode sb : sbs) {
                        skuBarcodes.add(sb.getBarcode());
                    }
                }
                p.setBarcodes(skuBarcodes);
                // 保存数据到mongodb
                mongoOperation.save(p);
            }
            if (w.getIsCheckPickingStatus() != null && w.getIsCheckPickingStatus()) {
                // 配货批开始
                WhInfoTimeRef ref2 = whInfoTimeRefDao.getInfoBySlipCode(pickingbarCode, 13, new BeanPropertyRowMapper<WhInfoTimeRef>(WhInfoTimeRef.class));
                if (ref2 == null) {
                    whInfoTimeRefDao.insertWhInfoTime2(pickingbarCode, WhInfoTimeRefBillType.STA_PICKING.getValue(), 13, userId, ouId);
                }
            }
        }
        return null;
    }


    @Override
    public RtwDieking getRtwDiekingByBatchCode(String btCode) {
        return rtwDieKingDao.findRtwDiekingListByCode(btCode);
    }


    @Override
    public String returnWhpickingSku(Long pickingId, String cCode, Long userId) {
        MongoDBWhPicking mdwp = mongoOperation.findOne(new Query(Criteria.where("id").is(pickingId)), MongoDBWhPicking.class);
        PickingOpLine po = new PickingOpLine();
        po.setContainerCode(cCode);
        po.setLocation(locDao.getByPrimaryKey(mdwp.getLocationId()));
        po.setLocationCode(mdwp.getLocationCode());
        po.setQty(1L);
        po.setRtwDiekingCode(mdwp.getCode());
        po.setSku(skuDao.getByPrimaryKey(mdwp.getSkuId()));
        po.setSkuBarcode(skuDao.getByPrimaryKey(mdwp.getSkuId()).getBarCode());
        po.setUser(userDao.getByPrimaryKey(userId));
        po.setSkuInvStatus(mdwp.getSkuStatus());
        Long i = pickingOpLineDao.save(po).getId();
        // 插入操作明细
        if (i == null) {
            return "数据异常！";
        } else {
            pickingOpLineDao.flush();
        }
        return "";
    }
}
