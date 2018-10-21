package com.jumbo.wms.manager.warehouse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.dao.automaticEquipment.ZoonDao;
import com.jumbo.dao.automaticEquipment.ZoonOcpOrderDao;
import com.jumbo.dao.automaticEquipment.ZoonOcpSortDao;
import com.jumbo.dao.warehouse.MongoDBInventoryDao;
import com.jumbo.dao.warehouse.StaErrorLineDao;
import com.jumbo.dao.warehouse.StaLineDao;
import com.jumbo.dao.warehouse.StaOcpLineDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.wms.Constants;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.MongoDBInventoryManager;
import com.jumbo.wms.model.MongoDBInventoryOcp;
import com.jumbo.wms.model.automaticEquipment.ZoonOcpOrderLine;
import com.jumbo.wms.model.automaticEquipment.ZoonOcpSort;
import com.jumbo.wms.model.warehouse.StaErrorLine;
import com.jumbo.wms.model.warehouse.StaLineCommand;
import com.jumbo.wms.model.warehouse.StaOcpLine;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationStatus;

@Transactional
@Service("areaOcpStaManager")
public class AreaOcpStaManagerImpl extends BaseManagerImpl implements AreaOcpStaManager {
    protected static final Logger log = LoggerFactory.getLogger(AreaOcpStaManagerImpl.class);
    private static final long serialVersionUID = 1L;
    @Autowired
    private ZoonOcpOrderDao ZoonOcpOrderDao;
    @Autowired
    private ZoonOcpSortDao zoonOcpSortDao;
    @Autowired
    private StaLineDao staLineDao;
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private MongoDBInventoryManager mongoDBInventoryManager;
    @Autowired
    private MongoDBInventoryDao mongoDBInventoryDao;
    @Autowired
    private ZoonDao zoonDao;
    @Autowired
    private StaOcpLineDao staOcpLineDao;
    @Autowired
    private StaErrorLineDao staErrorLineDao;


    /**
     * 
     * 根据作业单进行库存计算<br/>
     * 先计算整单在区域中是否满足<br/>
     * 判断是单区域还是多区域，计算完成后更新订单行状态和作业单状态，计算成功扣减MongDb库存,staLine上记录区域<br/>
     */
    public void ocpInvByStaId(Long staId) {
        log.info("ocpInvByStaId is start: " + staId);
        StockTransApplication sta = staDao.getByPrimaryKey(staId);
        staOcpLineDao.deleteStaOcpLineByStaId(staId);// 删除staOcpLine数据
        staErrorLineDao.deleteByStaErrorLineByStaId(staId);// 删除staErrorLine数据
        Long ouId = sta.getMainWarehouse().getId();
        String saleModle = "";
        Integer pickingType = null;
        if (sta.getIsPreSale() != null) {
            // 判断是否预售
            if (sta.getIsPreSale().equals("1")) {
                // 预售
                saleModle = "预售";
            } else {
                // 非预售
                pickingType = sta.getPickingType().getValue();
                saleModle = pickingType.toString();
            }
        } else {
            pickingType = sta.getPickingType().getValue();
            saleModle = pickingType.toString();
        }
        List<ZoonOcpSort> ocpList = zoonOcpSortDao.findZoonOcpSortList(ouId, saleModle, new BeanPropertyRowMapper<ZoonOcpSort>(ZoonOcpSort.class));
        // 读取区域优先级缓存
        // if (Constants.zoonOcpMap.size() == 0) {
        // ocpList = zoonOcpSortDao.findZoonOcpSortList(ouId, new
        // BeanPropertyRowMapper<ZoonOcpSort>(ZoonOcpSort.class));
        // Constants.zoonOcpMap.put(ouId, ocpList);
        // } else {
        // ocpList = Constants.zoonOcpMap.get(ouId);
        // }
        List<ZoonOcpOrderLine> lineList = new ArrayList<ZoonOcpOrderLine>();
        for (ZoonOcpSort zoonSort : ocpList) {// 区域待占用列表
            // String pickType = sta.getPickingType().getValue() + "";
            // 属于当前仓库且配货模式相同的区域 进入计算队列
            // if (sta.getMainWarehouse().getId().equals(zoonSort.getOuId()) &&
            // (zoonSort.getSaleModle().equals(pickType) || (zoonSort.getSaleModle().equals("预售") &&
            // sta.getIsPreSale() != null && sta.getIsPreSale().equals("1")))) {
            List<StaLineCommand> staLine = staLineDao.findStaLineGroupByOcp(staId, new BeanPropertyRowMapper<StaLineCommand>(StaLineCommand.class));
            for (StaLineCommand line : staLine) { // 配货明细
                ZoonOcpOrderLine orderLine = new ZoonOcpOrderLine();
                orderLine.setOrderId(staId);
                orderLine.setOuId(zoonSort.getOuId());
                orderLine.setSaleModle(zoonSort.getSaleModle());
                orderLine.setZoonCode(zoonSort.getZoonCode());
                orderLine.setOwner(line.getOwner());
                orderLine.setQuantity(line.getSumQty());
                orderLine.setSkuId(line.getSkuId());
                orderLine.setSort(zoonSort.getSort());
                lineList.add(orderLine);
            }
            // }
        }
        String zoonCode = ""; // 区域编码
        String errorMsg = ""; // 缺库存信息
        Boolean isFull = true; // 是否计算成功
        Boolean isMongDbInv = false; // 是否是mongDB里的库存
        String owner = "";
        List<StaErrorLine> staErrorLines = new ArrayList<StaErrorLine>();
        Map<String, String> areaNuFullMap = new HashMap<String, String>(); // 整单不满足的区域集合
        Map<String, String> areaAllMap = new HashMap<String, String>(); // 所有的区域集合
        Map<Long, Integer> areaSkuMap = new HashMap<Long, Integer>(); // 存放跨区域 未占用的商品集合
        List<Long> skuList = new ArrayList<Long>(); // 商品列表
        List<String> areaList = new ArrayList<String>(); // 区域列表
        // 获取本次计算的skuList等数据。
        for (ZoonOcpOrderLine line : lineList) {
            if (zoonCode.equals("")) {
                zoonCode = line.getZoonCode();
            }
            if (zoonCode.equals(line.getZoonCode())) {// SKU只需计算一个区域即可
                skuList.add(line.getSkuId());
                owner = line.getOwner();
                ouId = line.getOuId();
                areaSkuMap.put(line.getSkuId(), line.getQuantity());// 所有需占用的商品和数量
            }
            areaList.add(line.getZoonCode());
        }
        List<MongoDBInventoryOcp> mongDbInv = null;
        try {
            // 查询mongDb获取本次计算的SKU库存列表
            mongDbInv = mongoDBInventoryManager.getOcpInventoryOwner(owner, ouId, skuList, areaList);
        } catch (BusinessException e) {
            log.error("ocpInvByStaId mongoDBInventoryManager.getOcpInventoryOwner error:" + staId, e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR); // 直接报错
        }
        if (mongDbInv == null || mongDbInv.size() == 0) {
            /**
             * 库存不满足，直接失败
             */
            log.error("mongDbInv is no data:" + ouId + "," + owner + "," + skuList + "," + areaList);
            staDao.updateStaOcpResult(staId, Constants.OCP_AREA_STATUS_ERROR, "MongDB库存不足");// 标记作业单占用状态为占用失败，更新错误信息
            /**
             * 区域占用失败更新为配货失败RUN
             */
             sta.setStatus(StockTransApplicationStatus.FAILED); 
             staDao.save(sta);
            
            List<StaLineCommand> staLine = staLineDao.findStaLineGroupByOcp(staId, new BeanPropertyRowMapper<StaLineCommand>(StaLineCommand.class));
            // 插入staErrorLine数据
            for (StaLineCommand sl : staLine) {
                StaErrorLine sel = new StaErrorLine();
                sel.setQuantity(sl.getSumQty().longValue());
                sel.setSkuId(sl.getSkuId());
                sel.setStaId(staId);
                staErrorLineDao.save(sel);
            }
            return;
        }
        /**
         * 区域筛选<br/>
         * 先计算整单在区域中是否满足<br/>
         * 整单满足的放入Map中，供后续计算库存<br/>
         * 如所有区域库存不满足，可用区域=1，直接失败<br/>
         * 如所有区域库存不满足，可用区域>1，则需要分区域计算
         */
        List<String> areaCodeList = new ArrayList<String>();// 保存成功的区域编码
        Map<String, String> areaOkMap = new HashMap<String, String>();
        for (ZoonOcpOrderLine line : lineList) {
            areaAllMap.put(line.getZoonCode(), "");// 存放所有的区域
            if (areaNuFullMap.containsKey(line.getZoonCode())) {
                continue; // 当前区域库存不满足，直接跳过该区域
            }
            isMongDbInv = false;
            for (MongoDBInventoryOcp ocpInv : mongDbInv) {
                if (line.getSkuId().equals(ocpInv.getSkuId()) && line.getZoonCode().equals(ocpInv.getZoonCode())) {
                    if (ocpInv.getAvailQty() < line.getQuantity()) {
                        errorMsg += line.getSkuId() + "[" + line.getZoonCode() + "]共" + ocpInv.getAvailQty() + "件," + "需占" + line.getQuantity() + "件,缺" + (line.getQuantity() - ocpInv.getAvailQty()) + "件;";
                        areaNuFullMap.put(line.getZoonCode(), ""); // 存放库存不满足的区域
                        StaErrorLine sel = new StaErrorLine();
                        Integer errorQty = line.getQuantity() - ocpInv.getAvailQty();
                        sel.setQuantity(errorQty.longValue());
                        sel.setSkuId(line.getSkuId());
                        sel.setStaId(staId);
                        staErrorLines.add(sel);
                        break;
                    } else {
                        if (areaOkMap.get(line.getZoonCode()) == null) {
                            areaOkMap.put(line.getZoonCode(), "");
                            areaCodeList.add(line.getZoonCode());
                        }
                        isMongDbInv = true; // 为了判断当前orderLine的code不在mongDb库存内， 也属于库存不满足的区域
                    }
                }
            }
            if (!isMongDbInv) {
                areaNuFullMap.put(line.getZoonCode(), ""); // 存放库存不满足的区域
            }
        }
        /**
         * 区域计算库存
         */
        if (areaAllMap.size() == areaNuFullMap.size() && areaAllMap.size() == 1) {
            // 若订单所有的区域 和 库存不满足的区域相等，且 区域size =1 ，那么是不需要分仓的，直接失败
            staDao.updateStaOcpResult(staId, Constants.OCP_AREA_STATUS_ERROR, errorMsg);// 标记作业单占用状态为占用失败，更新错误信息
            
            /**
             * 区域占用失败更新为配货失败RUN
             */
             sta.setStatus(StockTransApplicationStatus.FAILED); 
             staDao.save(sta);
            
            // 插入库存ERROR表
            for (StaErrorLine s : staErrorLines) {
                staErrorLineDao.save(s);
            }
            return;
        }
        if (areaAllMap.size() == areaNuFullMap.size() && areaAllMap.size() > 1) {
            /**
             * 分区域<br/>
             * areaSkuMap 为计划占用量<br/>
             * 逐行遍历区域列表数据， 占用mongDB库存，有多少占用多少。 直到areaSkuMap的计划占用量扣减完
             */
            for (ZoonOcpOrderLine line : lineList) {
                // 1,更新mongdb库存，2，扣减map数量
                try {
                    Integer totalQty = areaSkuMap.get(line.getSkuId()); // 计划占用量
                    if (totalQty == null) {
                        continue; // 如计划占用量为空，则遍历下一条
                    }
                    line.setQuantity(totalQty); // 更新计划占用量
                    // 查询mongdb库存，是否足够
                    MongoDBInventoryOcp invOcp = mongoDBInventoryManager.findOneOcpInv(owner, ouId, line.getSkuId(), line.getZoonCode());
                    if (invOcp == null) {
                        continue;
                    }
                    if ((invOcp.getAvailQty() - line.getQuantity()) >= 0) {
                        // 库存满足，直接扣减
                        mongoDBInventoryManager.updateOcpMongoDBAvailQty(ouId, owner, line.getZoonCode(), line.getSkuId(), -line.getQuantity());
                        staLineDao.updateStaLineOcpArea(line.getSkuId(), line.getZoonCode() + ",", line.getZoonCode() + "," + line.getQuantity() + ";", staId);
                        // 插入staOcpLine数据
                        StaOcpLine sol = new StaOcpLine();
                        sol.setStaId(staId);
                        sol.setStaLineId(line.getStaLineId());
                        sol.setSkuId(line.getSkuId());
                        sol.setQuantity(line.getQuantity());
                        sol.setOwner(line.getOwner());
                        sol.setOcpCode(line.getZoonCode());
                        sol.setOuId(sta.getMainWarehouse().getId());
                        staOcpLineDao.save(sol);
                        totalQty = 0;
                    } else {
                        // 库存不满足，有多少占用多少
                        mongoDBInventoryManager.updateOcpMongoDBAvailQty(ouId, owner, line.getZoonCode(), line.getSkuId(), -invOcp.getAvailQty());
                        staLineDao.updateStaLineOcpArea(line.getSkuId(), line.getZoonCode() + ",", line.getZoonCode() + "," + invOcp.getAvailQty() + ";", staId);
                        totalQty = line.getQuantity() - invOcp.getAvailQty(); // 剩余占用量
                        // 插入staOcpLine数据
                        StaOcpLine sol = new StaOcpLine();
                        sol.setStaId(staId);
                        sol.setStaLineId(line.getStaLineId());
                        sol.setSkuId(line.getSkuId());
                        sol.setQuantity(invOcp.getAvailQty());
                        sol.setOwner(line.getOwner());
                        sol.setOcpCode(line.getZoonCode());
                        sol.setOuId(sta.getMainWarehouse().getId());
                        staOcpLineDao.save(sol);
                    }
                    if (totalQty == 0) {
                        areaSkuMap.remove(line.getSkuId());
                    } else {
                        areaSkuMap.remove(line.getSkuId());
                        areaSkuMap.put(line.getSkuId(), totalQty);
                    }
                } catch (Exception e) {
                    // 如遇到任何异常（更新数据库或MongDB出错，则整单计算失败，还原MongDB库存和订单状态）
                    isFull = false; // 库存不足
                    List<StaLineCommand> ocpLine = staLineDao.findStaLineByOcp(staId, new BeanPropertyRowMapper<StaLineCommand>(StaLineCommand.class));
                    for (StaLineCommand staline : ocpLine) {
                        // 查询已占用区域的SKU，还原mongDB库存
                        String ocpArray[] = staline.getOcpAreaMemo().split(";");
                        for (int i = 0; i < ocpArray.length; i++) {
                            String memo = ocpArray[i];
                            String ocpCode = memo.substring(0, memo.indexOf(",") + 1);
                            String qty = memo.substring(memo.indexOf(","), memo.length());
                            mongoDBInventoryManager.updateOcpMongoDBAvailQty(ouId, owner, ocpCode, staline.getSkuId(), Integer.parseInt(qty));
                        }
                    }
                    break; // 结束当前区域
                }
                if (areaSkuMap.size() == 0 || areaSkuMap == null) {
                    isFull = true; // 跨区域占用完毕
                    break;
                } else {
                    isFull = false; // areaSkuMap 不为空，则代表没有占用完毕
                }
            }
            if (errorMsg.length() > 200) { // 错误信息截取
                errorMsg = errorMsg.substring(0, 200);
            }
            if (isFull) {
                staDao.updateStaOcpResultSec(staId, Constants.OCP_AREA_STATUS_ROW_FINISH, "跨区域");// 跨区域占用完毕
            } else {
                staDao.updateStaOcpResult(staId, Constants.OCP_AREA_STATUS_ERROR, "跨区域库存不满足" + errorMsg);
                
                /**
                 * 区域占用失败更新为配货失败RUN
                 */
                 sta.setStatus(StockTransApplicationStatus.FAILED); 
                 staDao.save(sta);
                
                // 插入库存ERROR表
                for (StaErrorLine s : staErrorLines) {
                    staErrorLineDao.save(s);
                }
                staLineDao.updateStaLineOcpAreaById(staId); // 还原明细行标记
                List<StaLineCommand> ocpLine = staLineDao.findStaLineByOcp(staId, new BeanPropertyRowMapper<StaLineCommand>(StaLineCommand.class));
                for (StaLineCommand staline : ocpLine) {
                    // 查询已占用区域的SKU，还原mongDB库存
                    String ocpArray[] = staline.getOcpAreaMemo().split(";");
                    for (int i = 0; i < ocpArray.length; i++) {
                        String memo = ocpArray[i];
                        String ocpCode = memo.substring(0, memo.indexOf(","));
                        String qty = memo.substring(memo.indexOf(",") + 1, memo.length());
                        mongoDBInventoryManager.updateOcpMongoDBAvailQty(ouId, owner, ocpCode, staline.getSkuId(), Integer.parseInt(qty));
                    }
                }
            }
        } else {
            /**
             * 单区域计算<br/>
             * 除不满足的区域外，其他的都是需要依次遍历的区域。 如一个区域满足，则后续区域不进行计算<br/>
             * 计算成功，扣减mongdb库存，更新作业单和行状态。删除队列数据<br/>
             * 计算失败，则还原mongDb库存，更新作业单状态。删除队列数据<br/>
             */
            for (String code : areaCodeList) {
                //
                // }
                // for (String code : areaAllMap.keySet()) {
                isFull = true; // 默认当前区域库存足够
                if (!areaNuFullMap.containsKey(code)) {
                    for (ZoonOcpOrderLine line : lineList) {
                        if (code.equals(line.getZoonCode())) {
                            try {
                                // 扣减mongDb库存
                                int result = mongoDBInventoryManager.updateOcpMongoDBAvailQty(ouId, owner, line.getZoonCode(), line.getSkuId(), -line.getQuantity());
                                if (result == 0) {
                                    // 库存不足，直接报错
                                    log.info("updateOcpMongoDBAvailQty is error:" + staId);
                                    throw new BusinessException(ErrorCode.SO_TO_WH_INVENTORY_IS_NULL);
                                }
                                // 更新staLine上的区域
                                staLineDao.updateStaLineOcpAreaNull(line.getSkuId(), line.getZoonCode(), line.getZoonCode() + "," + line.getQuantity() + ";", staId);
                                // 插入staOcpLine数据
                                StaOcpLine sol = new StaOcpLine();
                                sol.setStaId(staId);
                                sol.setStaLineId(line.getStaLineId());
                                sol.setSkuId(line.getSkuId());
                                sol.setQuantity(line.getQuantity());
                                sol.setOwner(line.getOwner());
                                sol.setOcpCode(line.getZoonCode());
                                sol.setOuId(sta.getMainWarehouse().getId());
                                staOcpLineDao.save(sol);
                            } catch (Exception e) {
                                isFull = false; // 库存不足
                                errorMsg += line.getSkuId() + "," + line.getZoonCode() + "," + line.getQuantity();
                                StaErrorLine sel = new StaErrorLine();
                                sel.setQuantity(line.getQuantity().longValue());
                                sel.setSkuId(line.getSkuId());
                                sel.setStaId(staId);
                                staErrorLines.add(sel);
                                log.error("not full mongoDBInventory" + staId + "," + line.getZoonCode() + "," + line.getSkuId() + "," + line.getQuantity());
                                List<StaLineCommand> ocpLine = staLineDao.findStaLineByOcp(staId, new BeanPropertyRowMapper<StaLineCommand>(StaLineCommand.class));
                                for (StaLineCommand staline : ocpLine) {
                                    // 查询已占用区域的SKU，还原mongDB库存
                                    mongoDBInventoryManager.updateOcpMongoDBAvailQty(ouId, owner, staline.getOcpAreaCode(), staline.getSkuId(), staline.getQuantity().intValue());
                                }
                                break; // 结束当前区域
                            }
                        }
                    }
                    if (isFull) {
                        // 结束计算，更新作业单状态
                        staDao.updateStaOcpResultSec(staId, Constants.OCP_AREA_STATUS_ROW_FINISH, code);
                        break;
                    }
                }

            }
            if (!isFull) {
                // 单区域都计算完后，还未计算成功。也需标记作业单区域状态为计算失败
                staDao.updateStaOcpResult(staId, Constants.OCP_AREA_STATUS_ERROR, errorMsg);
                staLineDao.updateStaLineOcpAreaById(staId); // 还原明细行标记
                // 插入库存ERROR表
                for (StaErrorLine s : staErrorLines) {
                    staErrorLineDao.save(s);
                }
            }
        }
        log.info("ocpInvByStaId is end: " + staId);
    }

    /**
     * 初始化MongDb库存
     */
    public void initInventoryForOcpAreaByOwner(MongoDBInventoryOcp ocp, Long ouId) {
        // 查询逻辑修改。 计算库位的绑定销售的作业类型
        mongoDBInventoryManager.initInventoryForOcpAreaByOwner(ocp, ouId);
        // 扣除未非分配或者分配失败占用
        // try {
        // Long needMinusQty = staLineDao.findUnHandledOrFailedAreaSkuOcpQty(ocp.getOuId(),
        // ocp.getZoonCode(), ocp.getOwner(), ocp.getSkuId(), new
        // SingleColumnRowMapper<Long>(Long.class));
        // if (needMinusQty != null && needMinusQty.longValue() > 0) {
        // mongoDBInventoryManager.updateOcpMongoDBAvailQty(ocp.getOuId(), ocp.getOwner(),
        // ocp.getZoonCode(), ocp.getSkuId(), -needMinusQty.intValue());
        // }
        // } catch (Exception e) {
        // log.error("findUnHandledOrFailedAreaSkuOcpQty error" + ocp.toString(), e);
        // }
    }

    /**
     * MongDb建表
     */
    public void createMongDbTableForOcp(Long ouId) {
        mongoDBInventoryManager.createTableForMongoAreaOcpInv(ouId);
    }

    /**
     * 删库，跑路
     */
    public void deleteInventoryForAreaOcpInv(Long ouId) {
        mongoDBInventoryManager.deleteInventoryForAreaOcpInv(ouId);
    }

    /**
     * 查询所有区域
     */
    public List<String> findAllAreaCode(Long ouId) {
        // return zoonOcpOrderLineDao.findAllAreaCode(ouId, new
        // SingleColumnRowMapper<String>(String.class));
        return zoonDao.findAllZoonCode(ouId, new SingleColumnRowMapper<String>(String.class));
    }


    /**
     * 按区域查询MongDb库存
     * 
     * @param areaCode
     * @return
     */
    public List<MongoDBInventoryOcp> findMongDbInvList(String areaCode, Long ouId) {
        return mongoDBInventoryDao.findInventoryForOcpAreaByAreaCode(areaCode, ouId, new BeanPropertyRowMapper<MongoDBInventoryOcp>(MongoDBInventoryOcp.class));
    }

    @Override
    public List<MongoDBInventoryOcp> findMongDbInvListMinus(String areaCode, Long ouId) {
        List<MongoDBInventoryOcp> needMinusQty = staLineDao.findMongDbInvListMinus(areaCode, ouId, new BeanPropertyRowMapper<MongoDBInventoryOcp>(MongoDBInventoryOcp.class));
        return needMinusQty;
    }

}
