package com.jumbo.wms.manager.warehouse;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import loxia.dao.Pagination;
import loxia.dao.Sort;
import loxia.dao.support.BeanPropertyRowMapperExt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.jumbo.dao.pickZone.WhPickZoneDao;
import com.jumbo.dao.warehouse.GiftLineDao;
import com.jumbo.dao.warehouse.InventoryDao;
import com.jumbo.dao.warehouse.RtwDieKingDao;
import com.jumbo.dao.warehouse.RtwDieKingLineDao;
import com.jumbo.dao.warehouse.RtwDieKingLineLogDao;
import com.jumbo.dao.warehouse.StaLineDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.util.StringUtil;
import com.jumbo.wms.model.command.GiftLineCommand;
import com.jumbo.wms.model.command.RtwDiekingCommend;
import com.jumbo.wms.model.pickZone.WhPickZoon;
import com.jumbo.wms.model.warehouse.GiftType;
import com.jumbo.wms.model.warehouse.Inventory;
import com.jumbo.wms.model.warehouse.RtwDieking;
import com.jumbo.wms.model.warehouse.RtwDiekingLine;
import com.jumbo.wms.model.warehouse.RtwDiekingLineLineLogCommand;
import com.jumbo.wms.model.warehouse.StaLine;
import com.jumbo.wms.model.warehouse.StockTransApplication;

@Transactional
@Service("rtwDieKingLineManager")
public class RtwDieKingLineManagerImpl implements RtwDieKingLineManager {

    private static final long serialVersionUID = 9031203153833883942L;
    protected static final Logger logger = LoggerFactory.getLogger(RtwDieKingLineManagerImpl.class);
    @Autowired
    private RtwDieKingDao rtwDieKingDao;
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private RtwDieKingLineDao rtwDieKingLineDao;
    @Autowired
    private RtwDieKingLineLogDao rtwDieKingLineLogDao;
    @Autowired
    private InventoryDao inventoryDao;
    @Autowired
    private WhPickZoneDao whPickZoneDao;
    @Autowired
    private StaLineDao staLineDao;
    @Autowired
    private GiftLineDao giftLineDao;



    @Transactional(readOnly = true)
    public Pagination<RtwDiekingCommend> getRtwDiekingList(int start, int pageSize, RtwDiekingCommend rtwDieking, String shopLikeQuery, Long ouid, Sort[] sorts) {
        Date startCreateTime = null;
        Date endCreateTime = null;
        String batchCode = null;
        Integer staType = null;
        List<String> shopInnerCodes = null;
        String staCode = null;
        String staRefSlipCode = null;
        String shortPickStatus = "1";
        String skuCode = null;
        if (rtwDieking != null) {
            if (rtwDieking.getStartCreateTime1() != null) {
                startCreateTime = rtwDieking.getStartCreateTime1();
            }
            if (rtwDieking.getEndCreateTime1() != null) {
                endCreateTime = rtwDieking.getEndCreateTime1();
            }
            if (!StringUtil.isEmpty(rtwDieking.getBatchCode())) {
                batchCode = rtwDieking.getBatchCode();
            }
            if (rtwDieking.getStaType() != null) {
                staType = rtwDieking.getStaType();
            }
            if (!StringUtil.isEmpty(rtwDieking.getStaCode())) {
                staCode = rtwDieking.getStaCode();
            }
            if (!StringUtil.isEmpty(rtwDieking.getStaRefSlipCode())) {
                staRefSlipCode = rtwDieking.getStaRefSlipCode();
            }
            if (rtwDieking.getStatus() != null && rtwDieking.getStatus() != 1) {
                shortPickStatus = rtwDieking.getStatus().toString();
            }
            if (!StringUtil.isEmpty(rtwDieking.getSkuCode())) {
                skuCode = rtwDieking.getSkuCode();
            }
            if (StringUtils.hasText(shopLikeQuery)) {
                if (StringUtils.hasLength(shopLikeQuery)) {
                    shopInnerCodes = new ArrayList<String>();
                    String[] shopArrays = shopLikeQuery.split("\\|");
                    for (String s : shopArrays) {
                        shopInnerCodes.add(s);
                    }
                }
            }
        }
        return rtwDieKingDao.getRtwDiekingList(start, pageSize, startCreateTime, endCreateTime, batchCode, staType, shopInnerCodes, staCode, staRefSlipCode, sorts, ouid, shortPickStatus, skuCode, new BeanPropertyRowMapperExt<RtwDiekingCommend>(
                RtwDiekingCommend.class));
    }


    /**
     * 生成拣货任务
     */
    @Override
    public String saveRtwDickingTask(Long staid, String rule, Long maxQty, Long mainWhID, Long userId, String userName) {
        // 获取出库单信息
        String result = null;
        StockTransApplication sta = staDao.getByPrimaryKey(staid);
        List<Inventory> inv = inventoryDao.findByOccupiedCode(sta.getCode());
        // 判断占用库存对应库位是否绑定拣货区域 拣货区域是否绑定仓库区域
        for (Inventory i : inv) {
            if (i.getLocation().getWhPickZoon() == null) {
                result = "库位编码【" + i.getLocation().getCode() + "】 没有绑定拣货区域，请先绑定拣货区域！";
                // System.out.println("库位编码【" + i.getLocation().getCode() + "】 没有绑定拣货区域，请先绑定拣货区域！");
                return result;
            }
            Long pickZoonId = i.getLocation().getWhPickZoon().getId();
            WhPickZoon zoon = whPickZoneDao.getByPrimaryKey(pickZoonId);
            if (zoon.getZoon() == null) {
                result = "拣货区域编码【" + zoon.getCode() + "】 没有绑定仓库区域，请先绑定仓库区域！";
                // System.out.println("拣货区域编码【" + zoon.getCode() + "】 没有绑定仓库区域，请先绑定仓库区域！");
                return result;
            }
        }
        // 进来判断拆分规则
        if (StringUtil.isEmpty(rule)) {
            // 如果是空 没有规则 splitRtwDicking
            splitRtwDicking(sta, maxQty, mainWhID, userId, userName, null, null, null);
        } else {
            // 有规则
            dickingRuleIsNotNull(sta, maxQty, mainWhID, userId, userName, rule);
        }
        // 验证是否有重复行 合并重复行
        mergeRtwDickingLine(sta.getCode(), mainWhID);
        // 更新拣货单头计划数量
        updateRtwDiekingPlanQty(sta.getCode(), mainWhID);
        return result;
    }

    /**
     * 验证是否有重复行 合并重复行
     */
    private void mergeRtwDickingLine(String staCode, Long mainWhID) {
        // 查作业单对应所有的拣货任务
        List<Long> rtwDickingIds = rtwDieKingDao.getRtwDiekingIdsByStaCode(staCode, mainWhID, new SingleColumnRowMapper<Long>(Long.class));
        for (Long id : rtwDickingIds) {
            List<String> rtwDickingLine = rtwDieKingDao.getRepeatRtwDiekingLineByRtwDiekingId(id, new SingleColumnRowMapper<String>(String.class));
            for (String s : rtwDickingLine) {
                List<Long> lineIds = new ArrayList<Long>();
                String ids = s.split("⊥")[0];
                String[] idss = ids.split("-");
                if (idss.length > 1) {
                    // 存在多条相同明细
                    for (String string : idss) {
                        lineIds.add(Long.parseLong(string));
                    }
                    String qty = s.split("⊥")[1];
                    // 获取明细信息
                    RtwDiekingLine line = rtwDieKingLineDao.getByPrimaryKey(Long.parseLong(ids.split("-")[0]));
                    // 删除重复行
                    rtwDieKingLineDao.deleteAllByPrimaryKey(lineIds);
                    // 插入新行
                    RtwDiekingLine l = new RtwDiekingLine();
                    BeanUtils.copyProperties(line, l);
                    l.setId(null);
                    l.setPlanQuantity(Long.parseLong(qty));
                    rtwDieKingLineDao.save(l);
                    rtwDieKingLineDao.flush();
                }
            }
        }
    }

    /**
     * 处理没有拆分规则和没有多余拣货/仓库区域数据
     */
    private void splitRtwDicking(StockTransApplication sta, Long maxQty, Long mainWhID, Long userId, String userName, Long zoonId, String supplierCode, Long pZoonId) {
        // 通过STA.CODE获取所有的库存信息
        // for循环数据 生成拣获货批次 区别是否有商品数量上限 通过商品数量上限拆分
        // VAS明细行
        List<Long> staLineListVas = new ArrayList<Long>();
        // 非VAS明细行
        List<Long> staLineList = new ArrayList<Long>();
        List<StaLine> stalines = staLineDao.findByStaId(sta.getId());
        // 验证STALINE是否有VAS增值服务
        List<GiftLineCommand> giftLineCommands = giftLineDao.findGiftBySta(sta.getId(), GiftType.HUB4_VAS_GIFT.getValue(), new BeanPropertyRowMapperExt<GiftLineCommand>(GiftLineCommand.class));
        for (StaLine staLine : stalines) {
            if (null != giftLineCommands && giftLineCommands.size() > 0) {
                for (GiftLineCommand gift : giftLineCommands) {
                    if (staLine.getId().equals(gift.getStaLineId())) {
                        // 是VAS
                        staLineListVas.add(staLine.getId());
                    } else {
                        staLineList.add(staLine.getId());
                    }
                }
            } else {
                staLineList.add(staLine.getId());
            }
        }
        staLineList.removeAll(staLineListVas);
        /**
         * 非VAS明细行
         */
        if (null != staLineList && staLineList.size() > 0) {
            List<RtwDiekingLine> rtwDiekings = rtwDieKingDao.getRtwDiekingLineByStaCodeAndLoctionIds(sta.getCode(), zoonId, mainWhID, supplierCode, pZoonId, staLineList, new BeanPropertyRowMapperExt<RtwDiekingLine>(RtwDiekingLine.class));
            if (null != rtwDiekings && rtwDiekings.size() > 0) {
                Long qty = 0L;
                if (null == maxQty) {
                    // 没有子任务商品总数上线 只生成一个拣货单
                    saveRtwDickingAndLineData(sta, maxQty, mainWhID, userId, userName, rtwDiekings, false);
                } else {
                    // 需要按照子任务商品总数上线拆分
                    // 先获取本次计划商品总件数
                    for (RtwDiekingLine rtwDiekingLine : rtwDiekings) {
                        qty += rtwDiekingLine.getPlanQuantity();
                    }
                    // 判断商品总件数是不是<=商品上限数量
                    if (maxQty >= qty) {
                        // 只生成一个拣货单
                        saveRtwDickingAndLineData(sta, maxQty, mainWhID, userId, userName, rtwDiekings, false);
                    } else {
                        saveRtwDickingPlanAboveMaxQty(rtwDiekings, sta, maxQty, mainWhID, userId, userName, false);
                    }
                }
            }
        }
        /**
         * VAS明细行
         */
        if (null != staLineListVas && staLineListVas.size() > 0) {
            List<RtwDiekingLine> rtwDiekingsVas = rtwDieKingDao.getRtwDiekingLineByStaCodeAndLoctionIds(sta.getCode(), zoonId, mainWhID, supplierCode, pZoonId, staLineListVas, new BeanPropertyRowMapperExt<RtwDiekingLine>(RtwDiekingLine.class));
            if (null != rtwDiekingsVas && rtwDiekingsVas.size() > 0) {
                Long qty = 0L;
                if (null == maxQty) {
                    // 没有子任务商品总数上线 只生成一个拣货单
                    saveRtwDickingAndLineData(sta, maxQty, mainWhID, userId, userName, rtwDiekingsVas, true);
                } else {
                    // 需要按照子任务商品总数上线拆分
                    // 先获取本次计划商品总件数
                    for (RtwDiekingLine rtwDiekingLine : rtwDiekingsVas) {
                        qty += rtwDiekingLine.getPlanQuantity();
                    }
                    // 判断商品总件数是不是<=商品上限数量
                    if (maxQty >= qty) {
                        // 只生成一个拣货单
                        saveRtwDickingAndLineData(sta, maxQty, mainWhID, userId, userName, rtwDiekingsVas, true);
                    } else {
                        saveRtwDickingPlanAboveMaxQty(rtwDiekingsVas, sta, maxQty, mainWhID, userId, userName, true);
                    }
                }
            }
        }
    }

    /**
     * 更新拣货单头计划数量
     * 
     * @param staCode
     */
    private void updateRtwDiekingPlanQty(String staCode, Long ouid) {
        List<Long> ids = rtwDieKingDao.getRtwDiekingIdsByStaCode(staCode, ouid, new SingleColumnRowMapper<Long>(Long.class));
        for (Long id : ids) {
            rtwDieKingDao.updateRtwDiekingPlanQtyById(id);
        }
    }

    /**
     * 处理有拆分规则
     * 
     * @param sta
     * @param maxQty
     * @param mainWhID
     * @param userId
     * @param userName
     */
    private void dickingRuleIsNotNull(StockTransApplication sta, Long maxQty, Long mainWhID, Long userId, String userName, String rule) {
        // List<String> locationIds = null;
        // 判断拆分规则类型
        if (rule.equals("1")) {
            // 按拣货区域拆分
            List<Long> zoonId = rtwDieKingDao.getLocationIdByStaCodePickZoon(sta.getCode(), mainWhID, new SingleColumnRowMapper<Long>(Long.class));
            if (null == zoonId) {
                splitRtwDicking(sta, maxQty, mainWhID, userId, userName, null, null, null);
            } else {
                if (zoonId.size() == 1) {
                    // 没多个拣货区域
                    splitRtwDicking(sta, maxQty, mainWhID, userId, userName, null, null, null);
                } else {
                    // 有多个拣货区域
                    for (Long zoon : zoonId) {
                        // 每个拣货区域计算一次拣货单
                        splitRtwDicking(sta, maxQty, mainWhID, userId, userName, zoon, null, null);
                    }
                }
            }
        }
        if (rule.equals("2")) {
            // 按仓库区域拆分
            List<Long> pzoonId = rtwDieKingDao.getLocationIdByStaCodeZoon(sta.getCode(), mainWhID, new SingleColumnRowMapper<Long>(Long.class));
            if (null == pzoonId) {
                splitRtwDicking(sta, maxQty, mainWhID, userId, userName, null, null, null);
            } else {
                if (pzoonId.size() == 1) {
                    // 没多个仓库区域
                    splitRtwDicking(sta, maxQty, mainWhID, userId, userName, null, null, null);
                } else {
                    // 有多个仓库区域
                    for (Long pzoon : pzoonId) {
                        // 每个仓库区域计算一次拣货单
                        splitRtwDicking(sta, maxQty, mainWhID, userId, userName, null, null, pzoon);
                    }
                }
            }
        }
        if (rule.equals("3")) {
            // 按商品货号拆分
            List<String> supplierCodesList = rtwDieKingDao.getSkuIdByStaCodeSkuSupplierCode(sta.getCode(), mainWhID, new SingleColumnRowMapper<String>(String.class));
            if (null == supplierCodesList) {
                splitRtwDicking(sta, maxQty, mainWhID, userId, userName, null, null, null);
            } else {
                if (supplierCodesList.size() == 1) {
                    // 没多个商品货号
                    splitRtwDicking(sta, maxQty, mainWhID, userId, userName, null, null, null);
                } else {
                    for (String supplierCode : supplierCodesList) {
                        splitRtwDicking(sta, maxQty, mainWhID, userId, userName, null, supplierCode, null);
                    }
                }
            }
        }
    }

    /**
     * 计划数量>商品上限数量 需要拆分拣货单
     * 
     * @param sta
     * @param maxQty
     * @param mainWhID
     * @param userId
     * @param userName
     */
    private void saveRtwDickingPlanAboveMaxQty(List<RtwDiekingLine> rtwDiekings, StockTransApplication sta, Long maxQty, Long mainWhID, Long userId, String userName, boolean isVas) {
        int sort = 1;
        // 需要按照商品上限数量拆分
        boolean isNew = true;
        Long q = maxQty;
        Long rtwid = 0L;
        Map<Long, Long> check = new HashMap<Long, Long>();
        for (RtwDiekingLine rtwDiekingLine : rtwDiekings) {
            Long lplanQty = rtwDiekingLine.getPlanQuantity();// 当前条目计划数量
            while (true) {
                if (lplanQty > q) {
                    // 当前商品计划数量>商品上限数量
                    if (q >= maxQty) {
                        rtwid = saveRtwDicking(sta, mainWhID, userId, userName, isVas);
                        sort = 1;
                    }
                    RtwDiekingLine line = new RtwDiekingLine();
                    BeanUtils.copyProperties(rtwDiekingLine, line);
                    line.setMainWhId(mainWhID);
                    line.setPlanQuantity(q);
                    line.setRealityQuantity(0L);
                    line.setSort(sort);
                    line.setVersion(0);
                    line.setRtwDiekingId(rtwid);
                    rtwDieKingLineDao.save(line);
                    rtwDieKingLineDao.flush();
                    lplanQty -= q;
                    if (check.containsKey(rtwid)) {
                        check.put(rtwid, check.get(rtwid) + q);
                    } else {
                        check.put(rtwid, q);
                    }
                    if (check.get(rtwid) == maxQty) {
                        q = maxQty;
                        isNew = true;
                    }
                    sort++;
                } else {
                    if (lplanQty > 0) {
                        if (isNew) {
                            rtwid = saveRtwDicking(sta, mainWhID, userId, userName, isVas);
                            sort = 1;
                        }
                        RtwDiekingLine line = new RtwDiekingLine();
                        BeanUtils.copyProperties(rtwDiekingLine, line);
                        line.setMainWhId(mainWhID);
                        line.setPlanQuantity(lplanQty);
                        line.setRealityQuantity(0L);
                        line.setSort(sort);
                        line.setVersion(0);
                        line.setRtwDiekingId(rtwid);
                        rtwDieKingLineDao.save(line);
                        rtwDieKingLineDao.flush();
                        q -= lplanQty;
                        if (q > 0) {
                            isNew = false;
                        } else {
                            q = maxQty;
                            isNew = true;
                        }
                        if (check.containsKey(rtwid)) {
                            check.put(rtwid, check.get(rtwid) + lplanQty);
                        } else {
                            check.put(rtwid, lplanQty);
                        }
                        if (check.get(rtwid) == maxQty) {
                            q = maxQty;
                            isNew = true;
                        }
                        sort++;
                        break;
                    } else {
                        break;
                    }
                }
            }
        }
    }


    private Long saveRtwDicking(StockTransApplication sta, Long mainWhID, Long userId, String userName, boolean isVas) {
        RtwDieking rtw = new RtwDieking();
        rtw.setBatchCode(getRtwDiekingBatchCode());
        rtw.setOwner(sta.getOwner());
        rtw.setStaCode(sta.getCode());
        rtw.setStaRefSlipCode(sta.getRefSlipCode());
        rtw.setStaType(sta.getType().getValue());
        rtw.setStatus(sta.getStatus().getValue());
        rtw.setVersion(0);
        rtw.setRealityQuantity(0L);
        rtw.setCreateTime(new Date());
        rtw.setCreateId(userId);
        rtw.setCreateUser(userName);
        rtw.setMainWhId(mainWhID);
        rtw.setIsVas(isVas);
        rtw.setStatus(1);
        rtwDieKingDao.save(rtw);
        rtwDieKingDao.flush();
        return rtw.getId();
    }


    private void saveRtwDickingAndLineData(StockTransApplication sta, Long maxQty, Long mainWhID, Long userId, String userName, List<RtwDiekingLine> rtwDiekings, boolean isVas) {
        RtwDieking rtw = new RtwDieking();
        rtw.setBatchCode(getRtwDiekingBatchCode());
        rtw.setOwner(sta.getOwner());
        rtw.setStaCode(sta.getCode());
        rtw.setStaRefSlipCode(sta.getRefSlipCode());
        rtw.setStaType(sta.getType().getValue());
        rtw.setStatus(sta.getStatus().getValue());
        rtw.setVersion(0);
        rtw.setRealityQuantity(0L);
        rtw.setCreateTime(new Date());
        rtw.setCreateId(userId);
        rtw.setCreateUser(userName);
        rtw.setMainWhId(mainWhID);
        rtw.setIsVas(isVas);
        rtw.setStatus(1);
        rtwDieKingDao.save(rtw);
        rtwDieKingDao.flush();
        int sort = 1;
        for (RtwDiekingLine rtwDiekingLine : rtwDiekings) {
            rtwDiekingLine.setMainWhId(mainWhID);
            rtwDiekingLine.setRealityQuantity(0L);
            rtwDiekingLine.setSort(sort);
            rtwDiekingLine.setVersion(0);
            rtwDiekingLine.setRtwDiekingId(rtw.getId());
            rtwDieKingLineDao.save(rtwDiekingLine);
            rtwDieKingLineDao.flush();
            sort++;
        }
    }

    /**
     * 获取退仓拣货批次编码
     */
    @Override
    public String getRtwDiekingBatchCode() {
        String batchCode = "RD" + rtwDieKingDao.getRtwDiekingBatchCode(new SingleColumnRowMapper<Long>(Long.class));
        return batchCode;
    }



    @Override
    public Pagination<RtwDiekingLine> getOutboundDetailListCollection(int start, int pageSize, Long ouid, Long staid) {
        return rtwDieKingLineDao.getOutboundDetailListCollection(start, pageSize, ouid, staid, new BeanPropertyRowMapperExt<RtwDiekingLine>(RtwDiekingLine.class));
    }


    @Override
    public Pagination<RtwDiekingCommend> getOutboundDickingTaskDetailList(int start, int pageSize, Sort[] sorts, Long ouid, Long staid) {
        return rtwDieKingDao.getOutboundDickingTaskDetailList(start, pageSize, sorts, ouid, staid, new BeanPropertyRowMapperExt<RtwDiekingCommend>(RtwDiekingCommend.class));
    }


    @Override
    public Pagination<RtwDiekingLineLineLogCommand> getOutboundDickingZzxDetailList(int start, int pageSize, Long ouid, String staCode) {
        return rtwDieKingLineLogDao.getOutboundDickingZzxDetailList(start, pageSize, ouid, staCode, new BeanPropertyRowMapperExt<RtwDiekingLineLineLogCommand>(RtwDiekingLineLineLogCommand.class));
    }


    @Override
    public RtwDieking getRtwDiekingById(Long id) {
        return rtwDieKingDao.getByPrimaryKey(id);
    }
}
