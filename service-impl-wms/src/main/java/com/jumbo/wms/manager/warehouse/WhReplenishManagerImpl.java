package com.jumbo.wms.manager.warehouse;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import loxia.dao.Pagination;
import loxia.dao.Sort;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.dao.baseinfo.WarehouseDao;
import com.jumbo.dao.command.ReplenishSkuCommandDao;
import com.jumbo.dao.warehouse.InventoryDao;
import com.jumbo.dao.warehouse.PickingReplenishCfgDao;
import com.jumbo.dao.warehouse.StockTransTxLogDao;
import com.jumbo.dao.warehouse.WhReplenishDao;
import com.jumbo.dao.warehouse.WhReplenishLineDao;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.baseinfo.Warehouse;
import com.jumbo.wms.model.command.ReplenishSkuCommand;
import com.jumbo.wms.model.warehouse.InventoryCommand;
import com.jumbo.wms.model.warehouse.PickingReplenishCfg;
import com.jumbo.wms.model.warehouse.WhReplenish;
import com.jumbo.wms.model.warehouse.WhReplenishCommand;
import com.jumbo.wms.model.warehouse.WhReplenishLine;
import com.jumbo.wms.model.warehouse.WhReplenishLineCommand;
import com.jumbo.wms.model.warehouse.WhReplenishStatus;
import com.jumbo.wms.model.warehouse.WhReplenishType;

/**
 * 
 * @author jinlong.ke
 * 
 */
@Transactional
@Service("whReplenishManager")
public class WhReplenishManagerImpl extends BaseManagerImpl implements WhReplenishManager {

    /**
     * 
     */
    private static final long serialVersionUID = 3231760698893314042L;
    @Autowired
    private WhReplenishDao whReplenishDao;
    @Autowired
    private WarehouseDao warehouseDao;
    @Autowired
    private ReplenishSkuCommandDao replenishSkuCommandDao;
    @Autowired
    private InventoryDao inventoryDao;
    @Autowired
    private PickingReplenishCfgDao pickingReplenishCfgDao;
    @Autowired
    private StockTransTxLogDao stockTransTxLogDao;
    @Autowired
    private WhReplenishLineDao whReplenishLineDao;

    @Override
    public Integer createNewWhReplenish(WhReplenish wr, OperationUnit currentOu) {
        WhReplenish oldwr = whReplenishDao.findOldWhReplenish(currentOu.getId(), WhReplenishStatus.CREATED, WhReplenishStatus.DOING);
        if (oldwr != null) {
            if (new Date().getTime() / 1000 - oldwr.getCreateTime().getTime() / 1000 >= 14400L) {
                if (wr.getWarningPre() != null) {// 如果设置了整型的安全警戒线，转化为带精度的百分比
                    DecimalFormat df = new DecimalFormat("0.00");
                    oldwr.setWarningPre(new BigDecimal((df.format(wr.getWarningPre().floatValue() / 100))));
                }
                whReplenishDao.save(oldwr);
                return 0;
            } else {
                if (oldwr.getStatus().equals(WhReplenishStatus.DOING)) {
                    return 1;
                } else {
                    if (wr.getWarningPre() != null) {// 如果设置了整型的安全警戒线，转化为带精度的百分比
                        DecimalFormat df = new DecimalFormat("0.00");
                        oldwr.setWarningPre(new BigDecimal((df.format(wr.getWarningPre().floatValue() / 100))));
                    }
                    whReplenishDao.save(oldwr);
                    return 2;
                }
            }
        } else {
            SimpleDateFormat sf = new SimpleDateFormat("yyMMddHHmmss");
            wr.setCreateTime(new Date());
            wr.setCode("WR" + sf.format(new Date()));
            wr.setStatus(WhReplenishStatus.CREATED);
            wr.setOu(currentOu);
            if (wr.getWarningPre() != null) {// 如果设置了整型的安全警戒线，转化为带精度的百分比
                DecimalFormat df = new DecimalFormat("0.00");
                wr.setWarningPre(new BigDecimal((df.format(wr.getWarningPre().floatValue() / 100))));
            }
            whReplenishDao.save(wr);
            return 3;
        }
    }

    @Override
    public Pagination<WhReplenishCommand> findAllReplenishOrder(int start, int pageSize, Long ouId, Sort[] sorts) {
        return whReplenishDao.findAllReplenishOrder(start, pageSize, ouId, sorts, new BeanPropertyRowMapper<WhReplenishCommand>(WhReplenishCommand.class));
    }

    @Override
    public List<WhReplenish> findAllNeedDetailOrder() {
        return whReplenishDao.findAllNeedDetailOrder();
    }

    @Override
    public void updateWrStatusAndReplenishTime(WhReplenish wr) {
        wr.setLastReplenishTime(new Date());
        wr.setStatus(WhReplenishStatus.DOING);
        whReplenishDao.save(wr);
    }

    @Override
    public void generateDetail(WhReplenish wr) {
        if (wr.getType().equals(WhReplenishType.NORMAL)) {
            // 库内补货
            wareHouseReplenish(wr);
        } else {
            // 配货失败补货
            staErrorReplenish(wr);
        }
    }

    /**
     * 配货失败补货 商品配置的最大存货量-商品在拣货区的库存=最大补货量 商品在存货区的库存=最大存货量 如最大补货量>=最大存货量则取最大存货量=实际补货量
     * 如最大补货量<最大存货量则取最大补货量=实际补货量 如配货失败商品未维护商品补货信息则实际补货量=配货失败商品总数 如非共享仓库维护商品补货信息未维护店铺属性则按未维护补货信息处理。
     * 
     * @param wr
     */
    private void staErrorReplenish(WhReplenish wr) {
        Long ouId = wr.getOu().getId();
        Warehouse wh = warehouseDao.getByOuId(ouId);
        if (wh.getIsShare() != null && wh.getIsShare()) {// 共享仓
            // 查询配货失败作业单关联的商品和缺货量
            List<ReplenishSkuCommand> rsList = replenishSkuCommandDao.findRePlenishSkuByOuid(ouId, true, new BeanPropertyRowMapper<ReplenishSkuCommand>(ReplenishSkuCommand.class));
            for (ReplenishSkuCommand rs : rsList) {
                // 查询维护的库区最大配货量
                PickingReplenishCfg prc = getPickingReplenishCfg(ouId, rs.getSkuId(), null);
                Long needNum = 0L;
                WhReplenishLine wrl = new WhReplenishLine();
                // 未配置补货处理，实际补货量=缺货总数
                if (prc == null || prc.getMaxQty() == null) {
                    needNum = rs.getQty();
                } else {
                    // 获取维护的拣货区库存
                    Long num1 = getInventoryPicking(ouId, rs.getSkuId(), prc.getDistrict().getId(), null);
                    num1 = num1 == null ? 0L : num1;
                    // 获取存货区库存
                    Long num2 = getInventoryStock(ouId, rs.getSkuId(), null);
                    num2 = num2 == null ? 0L : num2;
                    // 最大补货量 = 设置的最大量-当前拣货区库存
                    Long maxBu = prc.getMaxQty() - num1;
                    // 如果最大补货量>=存货区可用库存 实际补货量=存货区可用库存 否则 实际补货量=最大补货量
                    if (maxBu >= num2) {
                        needNum = num2;
                    } else {
                        needNum = maxBu;
                    }
                    wrl.setInDisCurrentInv(num1);
                    wrl.setInDistrictCode(prc.getDistrict().getCode());
                    wrl.setWarningPre(prc.getWarningPre());
                    // 如果维护要设置入库信息
                    List<String> invList = stockTransTxLogDao.findInInventoryLoc(rs.getSkuId(), prc.getDistrict().getId(), ouId, null, new SingleColumnRowMapper<String>(String.class));
                    if (invList == null || invList.size() < 3) {
                        if (invList != null && invList.size() == 1) {
                            wrl.setInLocationCode(invList.get(0));
                        }
                        if (invList != null && invList.size() == 2) {
                            wrl.setInLocationCode(invList.get(0));
                            wrl.setInLocationCode1(invList.get(1));
                        }
                    } else {
                        wrl.setInLocationCode(invList.get(0));
                        wrl.setInLocationCode1(invList.get(1));
                        wrl.setInLocationCode2(invList.get(2));
                    }

                }
                // 查询该商品存货区可供补货库区库位
                List<InventoryCommand> invList = inventoryDao.findCanReplenishInventory(ouId, rs.getSkuId(), null, new BeanPropertyRowMapper<InventoryCommand>(InventoryCommand.class));
                for (int i = 0; i < invList.size() && needNum > 0; i++) {
                    Long outQty = 0L;
                    if (invList.get(i).getQuantity() <= needNum) {
                        outQty = invList.get(i).getQuantity();
                        needNum -= invList.get(i).getQuantity();
                    } else {
                        outQty = needNum;
                        needNum = 0L;
                    }
                    WhReplenishLine wrline = new WhReplenishLine();
                    wrline.setOutLocCurrentInv(invList.get(i).getQuantity());
                    wrline.setOutQty(outQty);
                    wrline.setOutLocationCode(invList.get(i).getLocationCode());
                    wrline.setOutDistrictCode(invList.get(i).getDistrictCode());
                    wrline.setInDisCurrentInv(wrl.getInDisCurrentInv());
                    wrline.setInDistrictCode(wrl.getInDistrictCode());
                    wrline.setWarningPre(wrl.getWarningPre());
                    wrline.setInQty(outQty);
                    if (prc != null) {
                        wrline.setMaxStore(prc.getMaxQty());
                    }
                    wrline.setInLocationCode(wrl.getInLocationCode());
                    wrline.setInLocationCode1(wrl.getInLocationCode1());
                    wrline.setInLocationCode2(wrl.getInLocationCode2());
                    wrline.setRepl(wr);
                    Sku sku = new Sku();
                    sku.setId(rs.getSkuId());
                    wrline.setSku(sku);
                    whReplenishLineDao.save(wrline);
                }
            }
            wr.setFinishTime(new Date());
            wr.setStatus(WhReplenishStatus.FINISHED);
            whReplenishDao.save(wr);
        } else {// 非共享仓
            // 查询配货失败作业单关联的商品和缺货量
            List<ReplenishSkuCommand> rsList = replenishSkuCommandDao.findRePlenishSkuByOuid(ouId, false, new BeanPropertyRowMapper<ReplenishSkuCommand>(ReplenishSkuCommand.class));
            for (ReplenishSkuCommand rs : rsList) {
                // 查询维护的库区最大配货量
                PickingReplenishCfg prc = getPickingReplenishCfg(ouId, rs.getSkuId(), rs.getInnerShopCode());
                Long needNum = 0L;
                WhReplenishLine wrl = new WhReplenishLine();
                // 未配置补货处理，实际补货量=缺货总数
                if (prc == null || prc.getMaxQty() == null) {
                    needNum = rs.getQty();
                } else {
                    // 获取维护的拣货区库存
                    Long num1 = getInventoryPicking(ouId, rs.getSkuId(), prc.getDistrict().getId(), rs.getInnerShopCode());
                    num1 = num1 == null ? 0L : num1;
                    // 获取存货区库存
                    Long num2 = getInventoryStock(ouId, rs.getSkuId(), rs.getInnerShopCode());
                    num2 = num2 == null ? 0L : num2;
                    // 最大补货量 = 设置的最大量-当前拣货区库存
                    Long maxBu = prc.getMaxQty() - num1;
                    // 如果最大补货量>=存货区可用库存 实际补货量=存货区可用库存 否则 实际补货量=最大补货量
                    if (maxBu >= num2) {
                        needNum = num2;
                    } else {
                        needNum = maxBu;
                    }
                    wrl.setInDisCurrentInv(num1);
                    wrl.setInDistrictCode(prc.getDistrict().getCode());
                    wrl.setWarningPre(prc.getWarningPre());
                    // 如果维护要设置入库信息
                    List<String> invList = stockTransTxLogDao.findInInventoryLoc(rs.getSkuId(), prc.getDistrict().getId(), ouId, rs.getInnerShopCode(), new SingleColumnRowMapper<String>(String.class));
                    if (invList == null || invList.size() < 3) {
                        if (invList != null && invList.size() == 1) {
                            wrl.setInLocationCode(invList.get(0));
                        }
                        if (invList != null && invList.size() == 2) {
                            wrl.setInLocationCode(invList.get(0));
                            wrl.setInLocationCode1(invList.get(1));
                        }
                    } else {
                        wrl.setInLocationCode(invList.get(0));
                        wrl.setInLocationCode1(invList.get(1));
                        wrl.setInLocationCode2(invList.get(2));
                    }

                }
                // 查询该商品存货区可供补货库区库位
                List<InventoryCommand> invList = inventoryDao.findCanReplenishInventory(ouId, rs.getSkuId(), rs.getInnerShopCode(), new BeanPropertyRowMapper<InventoryCommand>(InventoryCommand.class));
                for (int i = 0; i < invList.size() && needNum > 0; i++) {
                    Long outQty = 0L;
                    if (invList.get(i).getQuantity() <= needNum) {
                        outQty = invList.get(i).getQuantity();
                        needNum -= invList.get(i).getQuantity();
                    } else {
                        outQty = needNum;
                        needNum = 0L;
                    }
                    WhReplenishLine wrline = new WhReplenishLine();
                    wrline.setOutLocCurrentInv(invList.get(i).getQuantity());
                    wrline.setOutQty(outQty);
                    wrline.setOutLocationCode(invList.get(i).getLocationCode());
                    wrline.setOutDistrictCode(invList.get(i).getDistrictCode());
                    wrline.setInDisCurrentInv(wrl.getInDisCurrentInv());
                    wrline.setInDistrictCode(wrl.getInDistrictCode());
                    wrline.setWarningPre(wrl.getWarningPre());
                    wrline.setInQty(outQty);
                    if (prc != null) {
                        wrline.setMaxStore(prc.getMaxQty());
                    }
                    wrline.setInLocationCode(wrl.getInLocationCode());
                    wrline.setInLocationCode1(wrl.getInLocationCode1());
                    wrline.setInLocationCode2(wrl.getInLocationCode2());
                    wrline.setRepl(wr);
                    Sku sku = new Sku();
                    sku.setId(rs.getSkuId());
                    wrline.setSku(sku);
                    whReplenishLineDao.save(wrline);
                }
            }
            wr.setFinishTime(new Date());
            wr.setStatus(WhReplenishStatus.FINISHED);
            whReplenishDao.save(wr);
        }
    }

    /**
     * 获取存货区库存
     * 
     * @param ouId
     * @param skuId
     * @param innerShopCode
     * @return
     */
    private Long getInventoryStock(Long ouId, Long skuId, String innerShopCode) {
        return inventoryDao.getStockInventoryBySkuAndOu(ouId, skuId, innerShopCode, new SingleColumnRowMapper<Long>(Long.class));
    }

    /**
     * 获取拣货区库存
     * 
     * @param ouId
     * @param skuId
     * @param disId
     * @param innerShopCode
     * @return
     */
    private Long getInventoryPicking(Long ouId, Long skuId, Long disId, String innerShopCode) {
        return inventoryDao.getPickingInventoryBySkuAndOu(ouId, skuId, disId, innerShopCode, new SingleColumnRowMapper<Long>(Long.class));
    }

    /**
     * 查询是否有拣货区补货配置
     * 
     * @param ouId
     * @param skuId
     * @param innerShopCode
     * @return
     */
    private PickingReplenishCfg getPickingReplenishCfg(Long ouId, Long skuId, String innerShopCode) {
        return pickingReplenishCfgDao.findCfgBySkuAndOu(ouId, skuId, innerShopCode);
    }

    private void wareHouseReplenish(WhReplenish wr) {
        Long ouId = wr.getOu().getId();
        Warehouse wh = warehouseDao.getByOuId(ouId);
        if (wh.getIsShare() != null && wh.getIsShare()) {// 共享仓
            // 查询配货失败作业单关联的商品和缺货量
            List<ReplenishSkuCommand> rsList = replenishSkuCommandDao.findNeedReplenishSku(ouId, true, wr.getWarningPre(), new BeanPropertyRowMapper<ReplenishSkuCommand>(ReplenishSkuCommand.class));
            for (ReplenishSkuCommand rs : rsList) {
                // 查询维护的库区最大配货量
                PickingReplenishCfg prc = getPickingReplenishCfg(ouId, rs.getSkuId(), null);
                Long needNum = 0L;
                WhReplenishLine wrl = new WhReplenishLine();
                // 未配置补货处理，实际补货量=缺货总数
                if (prc == null || prc.getMaxQty() == null) {
                    needNum = rs.getQty();
                } else {
                    // 获取维护的拣货区库存
                    Long num1 = getInventoryPicking(ouId, rs.getSkuId(), prc.getDistrict().getId(), null);
                    num1 = num1 == null ? 0L : num1;
                    // 获取存货区库存
                    Long num2 = getInventoryStock(ouId, rs.getSkuId(), null);
                    num2 = num2 == null ? 0L : num2;
                    // 最大补货量 = 设置的最大量-当前拣货区库存
                    Long maxBu = prc.getMaxQty() - num1;
                    // 如果最大补货量>=存货区可用库存 实际补货量=存货区可用库存 否则 实际补货量=最大补货量
                    if (maxBu >= num2) {
                        needNum = num2;
                    } else {
                        needNum = maxBu;
                    }
                    wrl.setInDisCurrentInv(num1);
                    wrl.setInDistrictCode(prc.getDistrict().getCode());
                    wrl.setWarningPre(prc.getWarningPre());
                    // 如果维护要设置入库信息
                    List<String> invList = stockTransTxLogDao.findInInventoryLoc(rs.getSkuId(), prc.getDistrict().getId(), ouId, null, new SingleColumnRowMapper<String>(String.class));
                    if (invList == null || invList.size() < 3) {
                        if (invList != null && invList.size() == 1) {
                            wrl.setInLocationCode(invList.get(0));
                        }
                        if (invList != null && invList.size() == 2) {
                            wrl.setInLocationCode(invList.get(0));
                            wrl.setInLocationCode1(invList.get(1));
                        }
                    } else {
                        wrl.setInLocationCode(invList.get(0));
                        wrl.setInLocationCode1(invList.get(1));
                        wrl.setInLocationCode2(invList.get(2));
                    }

                }
                // 查询该商品存货区可供补货库区库位
                List<InventoryCommand> invList = inventoryDao.findCanReplenishInventory(ouId, rs.getSkuId(), null, new BeanPropertyRowMapper<InventoryCommand>(InventoryCommand.class));
                for (int i = 0; i < invList.size() && needNum > 0; i++) {
                    Long outQty = 0L;
                    if (invList.get(i).getQuantity() <= needNum) {
                        outQty = invList.get(i).getQuantity();
                        needNum -= invList.get(i).getQuantity();
                    } else {
                        outQty = needNum;
                        needNum = 0L;
                    }
                    WhReplenishLine wrline = new WhReplenishLine();
                    wrline.setOutLocCurrentInv(invList.get(i).getQuantity());
                    wrline.setOutQty(outQty);
                    wrline.setOutLocationCode(invList.get(i).getLocationCode());
                    wrline.setOutDistrictCode(invList.get(i).getDistrictCode());
                    wrline.setInDisCurrentInv(wrl.getInDisCurrentInv());
                    wrline.setInDistrictCode(wrl.getInDistrictCode());
                    wrline.setWarningPre(wr.getWarningPre() == null ? wrl.getWarningPre() : wr.getWarningPre());
                    wrline.setInQty(outQty);
                    if (prc != null) {
                        wrline.setMaxStore(prc.getMaxQty());
                    }
                    wrline.setInLocationCode(wrl.getInLocationCode());
                    wrline.setInLocationCode1(wrl.getInLocationCode1());
                    wrline.setInLocationCode2(wrl.getInLocationCode2());
                    wrline.setRepl(wr);
                    Sku sku = new Sku();
                    sku.setId(rs.getSkuId());
                    wrline.setSku(sku);
                    whReplenishLineDao.save(wrline);
                }
            }
            wr.setFinishTime(new Date());
            wr.setStatus(WhReplenishStatus.FINISHED);
            whReplenishDao.save(wr);
        } else {// 非共享仓
            // 查询配货失败作业单关联的商品和缺货量
            List<ReplenishSkuCommand> rsList = replenishSkuCommandDao.findNeedReplenishSku(ouId, false, wr.getWarningPre(), new BeanPropertyRowMapper<ReplenishSkuCommand>(ReplenishSkuCommand.class));
            for (ReplenishSkuCommand rs : rsList) {
                // 查询维护的库区最大配货量
                PickingReplenishCfg prc = getPickingReplenishCfg(ouId, rs.getSkuId(), rs.getInnerShopCode());
                Long needNum = 0L;
                WhReplenishLine wrl = new WhReplenishLine();
                // 未配置补货处理，实际补货量=缺货总数
                if (prc == null || prc.getMaxQty() == null) {
                    needNum = rs.getQty();
                } else {
                    // 获取维护的拣货区库存
                    Long num1 = getInventoryPicking(ouId, rs.getSkuId(), prc.getDistrict().getId(), rs.getInnerShopCode());
                    num1 = num1 == null ? 0L : num1;
                    // 获取存货区库存
                    Long num2 = getInventoryStock(ouId, rs.getSkuId(), rs.getInnerShopCode());
                    num2 = num2 == null ? 0L : num2;
                    // 最大补货量 = 设置的最大量-当前拣货区库存
                    Long maxBu = prc.getMaxQty() - num1;
                    // 如果最大补货量>=存货区可用库存 实际补货量=存货区可用库存 否则 实际补货量=最大补货量
                    if (maxBu >= num2) {
                        needNum = num2;
                    } else {
                        needNum = maxBu;
                    }
                    wrl.setInDisCurrentInv(num1);
                    wrl.setInDistrictCode(prc.getDistrict().getCode());
                    wrl.setWarningPre(prc.getWarningPre());
                    // 如果维护要设置入库信息
                    List<String> invList = stockTransTxLogDao.findInInventoryLoc(rs.getSkuId(), prc.getDistrict().getId(), ouId, rs.getInnerShopCode(), new SingleColumnRowMapper<String>(String.class));
                    if (invList == null || invList.size() < 3) {
                        if (invList != null && invList.size() == 1) {
                            wrl.setInLocationCode(invList.get(0));
                        }
                        if (invList != null && invList.size() == 2) {
                            wrl.setInLocationCode(invList.get(0));
                            wrl.setInLocationCode1(invList.get(1));
                        }
                    } else {
                        wrl.setInLocationCode(invList.get(0));
                        wrl.setInLocationCode1(invList.get(1));
                        wrl.setInLocationCode2(invList.get(2));
                    }

                }
                // 查询该商品存货区可供补货库区库位
                List<InventoryCommand> invList = inventoryDao.findCanReplenishInventory(ouId, rs.getSkuId(), rs.getInnerShopCode(), new BeanPropertyRowMapper<InventoryCommand>(InventoryCommand.class));
                for (int i = 0; i < invList.size() && needNum > 0; i++) {
                    Long outQty = 0L;
                    if (invList.get(i).getQuantity() <= needNum) {
                        outQty = invList.get(i).getQuantity();
                        needNum -= invList.get(i).getQuantity();
                    } else {
                        outQty = needNum;
                        needNum = 0L;
                    }
                    WhReplenishLine wrline = new WhReplenishLine();
                    wrline.setOutLocCurrentInv(invList.get(i).getQuantity());
                    wrline.setOutQty(outQty);
                    wrline.setOutLocationCode(invList.get(i).getLocationCode());
                    wrline.setOutDistrictCode(invList.get(i).getDistrictCode());
                    wrline.setInDisCurrentInv(wrl.getInDisCurrentInv());
                    wrline.setInDistrictCode(wrl.getInDistrictCode());
                    wrline.setWarningPre(wr.getWarningPre() == null ? wrl.getWarningPre() : wr.getWarningPre());
                    wrline.setInQty(outQty);
                    if (prc != null) {
                        wrline.setMaxStore(prc.getMaxQty());
                    }
                    wrline.setInLocationCode(wrl.getInLocationCode());
                    wrline.setInLocationCode1(wrl.getInLocationCode1());
                    wrline.setInLocationCode2(wrl.getInLocationCode2());
                    wrline.setRepl(wr);
                    Sku sku = new Sku();
                    sku.setId(rs.getSkuId());
                    wrline.setSku(sku);
                    whReplenishLineDao.save(wrline);
                }
            }
            wr.setFinishTime(new Date());
            wr.setStatus(WhReplenishStatus.FINISHED);
            whReplenishDao.save(wr);
        }
    }

    // 合并逻辑
    // private void whReplenish(WhReplenish wr) {
    //
    // }

    @Override
    public void cancelReplenishOrderById(Long wrId) {
        whReplenishDao.updateStatusById(wrId, WhReplenishStatus.CANCEL.getValue());
    }

    @Override
    public List<WhReplenishLineCommand> findWhReplenishLienById(Long wrId) {

        return whReplenishLineDao.findWhReplenishLienById(wrId, new BeanPropertyRowMapper<WhReplenishLineCommand>(WhReplenishLineCommand.class));
    }
}
