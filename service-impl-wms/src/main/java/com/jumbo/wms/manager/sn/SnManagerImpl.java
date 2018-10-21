package com.jumbo.wms.manager.sn;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import loxia.dao.Pagination;
import loxia.dao.Sort;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.dao.authorization.OperationUnitDao;
import com.jumbo.dao.authorization.UserDao;
import com.jumbo.dao.baseinfo.SkuDao;
import com.jumbo.dao.baseinfo.WarehouseDao;
import com.jumbo.dao.commandMapper.ImportEntryListRowMapper;
import com.jumbo.dao.commandMapper.ImportMacaoEntryListRowMapper;
import com.jumbo.dao.warehouse.BiChannelDao;
import com.jumbo.dao.warehouse.InventoryDao;
import com.jumbo.dao.warehouse.InventoryStatusDao;
import com.jumbo.dao.warehouse.PackageInfoDao;
import com.jumbo.dao.warehouse.PickingListDao;
import com.jumbo.dao.warehouse.SkuChildSnDao;
import com.jumbo.dao.warehouse.SkuSnCheckCfgDao;
import com.jumbo.dao.warehouse.SkuSnDao;
import com.jumbo.dao.warehouse.SkuSnLogDao;
import com.jumbo.dao.warehouse.SkuSnMappingDao;
import com.jumbo.dao.warehouse.SkuSnOpLogDao;
import com.jumbo.dao.warehouse.StaLineDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.dao.warehouse.StockTransVoucherDao;
import com.jumbo.dao.warehouse.WarehouseLocationDao;
import com.jumbo.util.FormatUtil;
import com.jumbo.util.StringUtil;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.warehouse.card.CardClientFactory;
import com.jumbo.wms.manager.warehouse.card.CardResult;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.authorization.User;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.baseinfo.SkuSnCardStatus;
import com.jumbo.wms.model.baseinfo.SkuSnCheckCfgType;
import com.jumbo.wms.model.baseinfo.SkuSnMapping;
import com.jumbo.wms.model.baseinfo.Warehouse;
import com.jumbo.wms.model.command.SkuCommand;
import com.jumbo.wms.model.command.SkuSnCheckCfgCommand;
import com.jumbo.wms.model.jasperReport.ImportEntryListObj;
import com.jumbo.wms.model.jasperReport.SnCardErrorLinesObj;
import com.jumbo.wms.model.jasperReport.SnCardErrorObj;
import com.jumbo.wms.model.warehouse.InventoryCommand;
import com.jumbo.wms.model.warehouse.InventoryStatus;
import com.jumbo.wms.model.warehouse.PackageInfoCommand;
import com.jumbo.wms.model.warehouse.PickingListCommand;
import com.jumbo.wms.model.warehouse.SkuChildSn;
import com.jumbo.wms.model.warehouse.SkuSn;
import com.jumbo.wms.model.warehouse.SkuSnCommand;
import com.jumbo.wms.model.warehouse.SkuSnLog;
import com.jumbo.wms.model.warehouse.SkuSnOpLog;
import com.jumbo.wms.model.warehouse.SkuSnStatus;
import com.jumbo.wms.model.warehouse.StaLine;
import com.jumbo.wms.model.warehouse.StaLineCommand;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationCommand;
import com.jumbo.wms.model.warehouse.StockTransVoucher;
import com.jumbo.wms.model.warehouse.StockTransVoucherCommand;
import com.jumbo.wms.model.warehouse.TransactionDirection;
import com.jumbo.wms.model.warehouse.WarehouseLocation;

@Transactional
@Service("snManager")
public class SnManagerImpl extends BaseManagerImpl implements SnManager {

    private static final long serialVersionUID = 8644493273171422556L;

    @Autowired
    private SkuSnDao snDao;
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private StockTransVoucherDao stvDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private SkuSnOpLogDao skuSnOpLogDao;
    @Autowired
    private SkuDao skuDao;
    @Autowired
    private SkuSnCheckCfgDao skuSnCheckCfgDao;
    @Autowired
    private SkuSnMappingDao skuSnMappingDao;
    @Autowired
    private OperationUnitDao operationUnitDao;
    @Autowired
    private SkuSnDao skuSnDao;
    @Autowired
    private SkuSnLogDao skuSnLogDao;
    @Autowired
    private PickingListDao pickingListDao;
    @Autowired
    private StaLineDao staLineDao;
    @Autowired
    private PackageInfoDao packageInfoDao;
    @Autowired
    private BiChannelDao biChannelDao;
    @Autowired
    private CardClientFactory cardClientFactory;
    @Autowired
    private InventoryStatusDao inventoryStatusDao;
    @Autowired
    private InventoryDao inventoryDao;
    @Autowired
    private WarehouseLocationDao warehouseLocationDao;
    @Autowired
    private WarehouseDao warehouseDao;
    @Autowired
    private SkuChildSnDao childSnDao;


    @Override
    public void checkSnStatus(Long ouid, String sn) {
        SkuSnCommand skusn = snDao.findBySn(sn, ouid, SkuSnStatus.USING.getValue(), new BeanPropertyRowMapper<SkuSnCommand>(SkuSnCommand.class));
        if (!StringUtil.isEmpty(skusn.getcStatus())) {
            if (!SkuSnCardStatus.valueOf(Integer.parseInt(skusn.getcStatus())).equals(SkuSnCardStatus.NONACTIVATED) && !SkuSnCardStatus.valueOf(Integer.parseInt(skusn.getcStatus())).equals(SkuSnCardStatus.ACTIVATE_SUCCESS)) {
                // 如果不是未激活状态和激活状态,此卡就是有问题返回错误信息
                String result = getSkuSnCardStatusName(Integer.parseInt(skusn.getcStatus()));
                throw new BusinessException(ErrorCode.SKU_SN_STATUS_ERROR, new Object[] {sn, result});
            }
        }
    }

    @Override
    public StockTransApplication getStaIdBySnStv(Long ouid, String sn) {
        return snDao.getStaIdBySnStv(ouid, sn, new BeanPropertyRowMapper<StockTransApplication>(StockTransApplication.class));
    }

    @Override
    public void cardBindingStv(Long ouid, String sn, Long staid) {
        StockTransApplication sta = staDao.getByPrimaryKey(staid);
        // 合并订单SN卡绑定子订单。 最终要实现： sn表里绑定的sta 是 sn所在的商品的所在的订单id
        if (sta.getIsMerge() != null && sta.getIsMerge()) {
            List<StockTransApplication> childStaList = staDao.getChildStaByGroupId(staid);
            for (StockTransApplication stas : childStaList) {
                // 获取当前sn的sku剩余核对量。 已核对sn数量 - 订单sku总量。
                Long isCheckCount = snDao.findisCheckSnQtyBySn(stas.getId(), ouid, sn, new SingleColumnRowMapper<Long>(Long.class));
                // 找到未核对的作业单。 如果为空，则找下一单。
                Long countSta = snDao.findSnQtyByStaIdAndSn(stas.getId(), sn, new SingleColumnRowMapper<Long>(Long.class));
                // 如果该作业单下，没有需要绑定的作业单，则跳过校验下一单
                if (countSta == null || countSta == 0) {
                    continue;
                }
                // 子订单有多少商品数量就绑定几个SN号
                if (isCheckCount >= 0) {
                    continue;
                }
                StockTransVoucher stv = stvDao.findStvCreatedByStaId(stas.getId());
                SkuSn skusn = snDao.findSkuSnBySn(sn, sta.getMainWarehouse().getId(), SkuSnStatus.USING);
                skusn.setStv(stv);
                skusn.setLastModifyTime(new Date());
                skusn.setStaId(stas.getId());
                snDao.save(skusn);
                break;
            }
        } else {
            // 更新sn号状态
            StockTransVoucher stv = stvDao.findStvCreatedByStaId(staid);
            SkuSn skusn = snDao.findSkuSnBySn(sn, sta.getMainWarehouse().getId(), SkuSnStatus.USING);
            skusn.setStv(stv);
            skusn.setLastModifyTime(new Date());
            skusn.setStaId(sta.getId());
            snDao.save(skusn);
        }

    }

    @Override
    public SkuSnCommand checkStvBinding(Long staid) {
        return snDao.checkStvBinding(staid, new BeanPropertyRowMapper<SkuSnCommand>(SkuSnCommand.class));
    }

    @Override
    public String activateCardStatus(Long staid, Long uid, String snCode) {
        String result = "ok";//
        StockTransApplication sta = staDao.getByPrimaryKey(staid);
        SkuSnCommand skusn = null;
        // 星巴克合并订单逻辑， 根据子订单去激活SN
        if (sta.getIsMerge() != null && sta.getIsMerge()) {
            List<StockTransApplication> childStaList = staDao.getChildStaByGroupId(staid);
            for (StockTransApplication stas : childStaList) {
                skusn = snDao.activateCardStatus(stas.getId(), snCode, new BeanPropertyRowMapper<SkuSnCommand>(SkuSnCommand.class));
                sta = stas;
                if (skusn == null) {
                    continue;
                }
                // 如果子订单绑定的SN已激活，继续循环
                if (skusn.getcStatus().equals("1")) {
                    continue;
                }
                break;
            }
        } else {
            skusn = snDao.activateCardStatus(staid, snCode, new BeanPropertyRowMapper<SkuSnCommand>(SkuSnCommand.class));
        }
        User u = userDao.getByPrimaryKey(uid);
        Sku sku = skuDao.getByPrimaryKey(skusn.getSkuid());
        SkuSn sn = snDao.getByPrimaryKey(skusn.getId());
        if (sku.getChildSnQty() != null) {
            List<SkuChildSn> childSn = childSnDao.getbySkuId(sku.getId(), snCode);
            if (childSn != null && childSn.size() > 0) {
                SkuSnCardStatus status = SkuSnCardStatus.ACTIVATE_SUCCESS;
                for (SkuChildSn skuChildSn : childSn) {
                    if (SkuSnCardStatus.ACTIVATE_SUCCESS.equals(SkuSnCardStatus.valueOf(skuChildSn.getStatus().getValue()))) {
                        // 如果已经激活就显示激活重复操作
                        SkuSnOpLog sso = new SkuSnOpLog();
                        sso.setCreateTime(new Date());
                        sso.setSn(skuChildSn.getSeedSn());
                        sso.setUser(u);
                        sso.setCardStatus(SkuSnCardStatus.ACTIVATE_REPETITIVE_OPERATION);
                        sso.setMemo("激活重复操作");
                        sso.setSku(sku);
                        skuSnOpLogDao.save(sso);
                    } else {
                        CardResult cr = cardClientFactory.getClient(skusn.getInterfaceType()).activeCard(snCode, skuChildSn.getSeedSn(), skusn.getSpType(), sta, sku);
                        SkuSnCardStatus childstatus = null;
                        String memo = "";
                        if (cr.getStatus() == CardResult.STATUS_SUCCESS) {
                            // 激活成功
                            childstatus = SkuSnCardStatus.ACTIVATE_SUCCESS;
                            memo = "激活成功";
                        }
                        if (cr.getStatus() == CardResult.STATUS_FAIL) {
                            // 激活失败
                            childstatus = SkuSnCardStatus.ACTIVATE_ERROR;
                            status = SkuSnCardStatus.ACTIVATE_ERROR;
                            memo = cr.getMsg();
                            result = "error";

                        }
                        if (cr.getStatus() == CardResult.STATUS_ERROR) {
                            // 系统异常 直接抛出错误
                            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
                        }
                        skuChildSn.setStatus(childstatus);
                        childSnDao.save(skuChildSn);
                        // 保存卡号操作日志
                        SkuSnOpLog sso = new SkuSnOpLog();
                        sso.setCreateTime(new Date());
                        sso.setSn(skuChildSn.getSeedSn());
                        sso.setUser(u);
                        sso.setMemo(memo);
                        sso.setCardStatus(childstatus);
                        sso.setSku(sku);
                        skuSnOpLogDao.save(sso);

                    }
                }
                sn.setCardStatus(status);
                sn.setLastModifyTime(new Date());
                snDao.save(sn);
            } else {
                result = "ERRORNOTSN";
                throw new BusinessException(ErrorCode.NOT_SKU_CHILD_SN);
            }
        } else {
            if (SkuSnCardStatus.ACTIVATE_SUCCESS.equals(SkuSnCardStatus.valueOf(Integer.parseInt(skusn.getcStatus())))) {
                // 如果已经激活就显示激活重复操作
                SkuSnOpLog sso = new SkuSnOpLog();
                sso.setCreateTime(new Date());
                sso.setSn(sn.getSn());
                sso.setUser(u);
                sso.setCardStatus(SkuSnCardStatus.ACTIVATE_REPETITIVE_OPERATION);
                sso.setMemo("激活重复操作");
                sso.setSku(sku);
                skuSnOpLogDao.save(sso);
            } else {
                // 激活接口
                CardResult cr = cardClientFactory.getClient(skusn.getInterfaceType()).activeCard(snCode, null, skusn.getSpType(), sta, sku);
                SkuSnCardStatus status = null;
                String memo = "";
                if (cr.getStatus() == CardResult.STATUS_SUCCESS) {
                    // 激活成功
                    status = SkuSnCardStatus.ACTIVATE_SUCCESS;
                    memo = "激活成功";
                }
                if (cr.getStatus() == CardResult.STATUS_FAIL) {
                    // 激活失败
                    status = SkuSnCardStatus.ACTIVATE_ERROR;
                    memo = cr.getMsg();
                    result = "error";
                }
                if (cr.getStatus() == CardResult.STATUS_ERROR) {
                    // 系统异常 直接抛出错误
                    throw new BusinessException(ErrorCode.SYSTEM_ERROR);
                }
                sn.setCardStatus(status);
                sn.setLastModifyTime(new Date());
                snDao.save(sn);
                // 保存卡号操作日志
                SkuSnOpLog sso = new SkuSnOpLog();
                sso.setCreateTime(new Date());
                sso.setSn(sn.getSn());
                sso.setUser(u);
                sso.setMemo(memo);
                sso.setCardStatus(status);
                sso.setSku(sku);
                skuSnOpLogDao.save(sso);
            }
        }
        return result;
    }

    private static String getSkuSnCardStatusName(int status) {
        String result = "";
        if (SkuSnCardStatus.valueOf(status).equals(SkuSnCardStatus.ACTIVATE_ERROR)) {
            result = "激活失败";
        }
        if (SkuSnCardStatus.valueOf(status).equals(SkuSnCardStatus.FROZEN)) {
            result = "已冻结";
        }
        if (SkuSnCardStatus.valueOf(status).equals(SkuSnCardStatus.RELEASE_SUCCESS)) {
            result = "解冻成功";
        }
        if (SkuSnCardStatus.valueOf(status).equals(SkuSnCardStatus.CANCEL)) {
            result = "作废";
        }
        return result;
    }

    @Override
    public Long getSnCountForStvSku(Long staid, String barcode, Integer cardStatus) {
        StockTransApplication sta = staDao.getByPrimaryKey(staid);
        Long count = 0l;
        // 星巴克合并订单逻辑， 查询子订单绑定的SN号的数量去更新作业单的执行数量
        if (sta.getIsMerge() != null && sta.getIsMerge()) {
            List<StockTransApplication> childStaList = staDao.getChildStaByGroupId(staid);
            for (StockTransApplication stas : childStaList) {
                count = snDao.getSnCountForStvSku(stas.getId(), barcode, cardStatus, new SingleColumnRowMapper<Long>(Long.class));
                if (count == null) {
                    count = 0l;
                }
                if (stas.getSkuQty() < count) {
                    continue;
                }
                if (count > 0) {
                    break;
                }
            }
            return count;
        } else {
            return snDao.getSnCountForStvSku(staid, barcode, cardStatus, new SingleColumnRowMapper<Long>(Long.class));
        }
    }

    /**
     * 创sn
     */
    @Override
    public void createSn(String sn, Long skuId, Long ouId, Long staId, SkuSnCardStatus cStatus) {
        StockTransApplication sta = staDao.getByPrimaryKey(staId);
        Sku sku = skuDao.getByPrimaryKey(skuId);
        if (null == sta || null == sku || null == ouId) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        OperationUnit ou = new OperationUnit();
        ou.setId(ouId);
        SkuSn skuSn = new SkuSn();
        skuSn.setOu(ou);
        skuSn.setSku(sku);
        skuSn.setSn(sn);
        skuSn.setCardStatus(cStatus);
        skuSn.setStatus(SkuSnStatus.USING);
        skuSn.setStaId(staId);
        skuSn.setVersion(0);
        skuSn.setLastModifyTime(new Date());
        try {
            snDao.save(skuSn);
        } catch (Exception e) {
            log.error("", e);
            throw new BusinessException(ErrorCode.ERROR_SN_IS_NOT_UNIQUE, new Object[] {2, sn});
        }
    }

    /**
     * sn操作日志
     */
    @Override
    public void saveSnOperateLog(SkuSn skuSn, Long operatorId, String memo) {
        if (StringUtils.isEmpty(skuSn.getSn())) throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        if (null == skuSn.getSku()) throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        User user = userDao.getByPrimaryKey(operatorId);
        if (null == user) throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        SkuSnOpLog opLog = new SkuSnOpLog();
        opLog.setUser(user);
        opLog.setCreateTime(new Date());
        opLog.setSn(skuSn.getSn());
        opLog.setSku(skuSn.getSku());
        opLog.setMemo(memo);
        skuSnOpLogDao.save(opLog);
    }

    /**
     * sn操作日志
     */
    @Override
    public void saveSnOperateLog(String sn, Long skuId, SkuSnCardStatus cStatus, Long operatorId, String memo) {
        if (StringUtils.isEmpty(sn)) throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        Sku sku = skuDao.getByPrimaryKey(skuId);
        if (null == sku) throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        User user = userDao.getByPrimaryKey(operatorId);
        if (null == user) throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        SkuSnOpLog opLog = new SkuSnOpLog();
        opLog.setUser(user);
        opLog.setCreateTime(new Date());
        opLog.setSn(sn);
        opLog.setSku(sku);
        opLog.setMemo(memo);
        opLog.setCardStatus(cStatus);
        skuSnOpLogDao.save(opLog);
    }

    @Override
    public Map<String, String> formatCardToSn(List<String> snList, Long ouid, String snCode, Long uid) {
        Map<String, String> resultMap = new HashMap<String, String>();
        Map<String, String> map = new HashMap<String, String>();
        for (String sn : snList) {
            // 合并相同barcode商品
            if (!StringUtil.isEmpty(sn)) {
                if (map.get(sn) == null) {
                    map.put(sn, sn);
                }
            }
        }
        snList.clear();
        snList.addAll(map.values());
        for (int i = 0; i < snList.size(); i++) {
            String barcode = snList.get(i).toString().split(",")[0];
            Long cid = Long.parseLong(snList.get(i).toString().split(",")[1]);
            // 获取卡号mapping规则
            SkuCommand sku = skuDao.getSkuByBarCodeAndCostomer(barcode, cid, new BeanPropertyRowMapper<SkuCommand>(SkuCommand.class));
            if (sku.getSnCheckModeInteger() != null) {
                // 有规则 查找规则明细
                Map<String, String> result = checkSnCheckMode(sku.getSnCheckModeInteger(), snCode, sku.getId(), ouid, uid);
                resultMap = result;
                if (result.containsKey("snok")) {
                    break;
                } else {
                    continue;
                }
            } else {
                resultMap.put("snerror", "【" + snCode + "】不在计划核对量中，请确认录入！");
            }
        }
        return resultMap;
    }


    public Map<String, String> checkSnCheckMode(Integer snCheckMode, String sn, Long skuid, Long ouid, Long uid) {
        List<SkuSnCheckCfgCommand> cfgList = skuSnCheckCfgDao.getSkuSnCheckCfgBySnCheckMode(snCheckMode, new BeanPropertyRowMapper<SkuSnCheckCfgCommand>(SkuSnCheckCfgCommand.class));
        Map<Integer, String> map = new HashMap<Integer, String>();
        Map<String, String> result = new HashMap<String, String>();
        String snCode = sn;
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
                        result.put("snerror", "【" + sn + "】不在计划核对量中，请确认录入！");
                        return result;
                    }
                }
            }
            // 判断是否有格式化配置
            if (map.containsKey(SkuSnCheckCfgType.CHAR_REPLACE.getValue())) {
                if (!StringUtil.isEmpty(map.get(SkuSnCheckCfgType.CHAR_REPLACE.getValue()))) {
                    snCode = formatSn(sn, map.get(SkuSnCheckCfgType.CHAR_REPLACE.getValue()));
                }
            }
            // 判断是否有mapping设置
            if (map.containsKey(SkuSnCheckCfgType.MAPPING.getValue())) {
                if (!StringUtil.isEmpty(map.get(SkuSnCheckCfgType.MAPPING.getValue()))) {
                    // 把处理过的SN号查询mapping表
                    SkuSnMapping ssm = skuSnMappingDao.getSkuSnMappingBySn(snCode, ouid, skuid, new BeanPropertyRowMapper<SkuSnMapping>(SkuSnMapping.class));
                    if (ssm != null) {
                        // 如果有记录 保存记录进sn表,并且删除mapping表数据 记录sn_log
                        addSkuSn(ssm, uid, sn);
                        skuSnMappingDao.deleteByPrimaryKey(ssm.getId());
                    } else {
                        // 没有数据
                        result.put("snerror", "卡券号：【" + sn + "】无收货记录，请确认录入！");
                        return result;
                    }
                }
            }
            result.put("snok", "");
        } else {
            result.put("snerror", "【" + sn + "】不在计划核对量中，请确认录入！");
            return result;
        }
        return result;
    }

    /**
     * 添加skusn skusnlog skusnoplog
     */
    private void addSkuSn(SkuSnMapping ssm, Long uid, String sn) {
        OperationUnit ou = operationUnitDao.getByPrimaryKey(ssm.getOuId());
        Sku sku = skuDao.getByPrimaryKey(ssm.getSkuId());
        User user = userDao.getByPrimaryKey(uid);
        StockTransVoucherCommand stv = stvDao.findTwhStvById(ssm.getStvId(), new BeanPropertyRowMapper<StockTransVoucherCommand>(StockTransVoucherCommand.class));
        SkuSn skusn = new SkuSn();
        skusn.setSn(sn);
        skusn.setStatus(SkuSnStatus.USING);
        skusn.setVersion(0);
        skusn.setOu(ou);
        skusn.setSku(sku);
        skusn.setLastModifyTime(new Date());
        skusn.setCardStatus(SkuSnCardStatus.NONACTIVATED);
        skuSnDao.save(skusn);
        SkuSnLog ssl = new SkuSnLog();
        // 星巴克新MSR卡定制
        if (sku.getSpType() != null && sku.getSpType().getValue() == 7 && stv.getStaId() != null) {
            StockTransApplication sta = staDao.getByPrimaryKey(stv.getStaId().longValue());
            List<StaLine> lines = staLineDao.findByStaId(sta.getId());
            if (sta != null && sta.getStatus().getValue() == 2) {
                ssl.setDirection(TransactionDirection.valueOf(1));
            } else {
                ssl.setDirection(TransactionDirection.valueOf(stv.getDirectionInt()));
            }
            // 绑定作业单
            skusn.setLastModifyTime(new Date());
            if (lines != null && !lines.isEmpty() && lines.get(0).getQuantity() == 1) {
                // 单件核对绑定作业单
                skusn.setStaId(sta.getId());
                skusn.setStv(stv);
            } else {
                // 多件核对这边不绑定，激活sn的时候校验
            }
            snDao.save(skusn);
        } else {
            ssl.setDirection(TransactionDirection.valueOf(stv.getDirectionInt()));
        }
        ssl.setOuId(ou.getId());
        ssl.setSkuId(sku.getId());
        ssl.setSn(sn);
        ssl.setStvId(stv.getId());
        ssl.setTransactionTime(new Date());
        ssl.setCardStatus(SkuSnCardStatus.NONACTIVATED);
        ssl.setLastModifyTime(new Date());
        skuSnLogDao.save(ssl);
        SkuSnOpLog sso = new SkuSnOpLog();
        sso.setSn(sn);
        sso.setSku(sku);
        sso.setCreateTime(new Date());
        sso.setUser(user);
        sso.setCardStatus(SkuSnCardStatus.NONACTIVATED);
        sso.setMemo("未激活");
        skuSnOpLogDao.save(sso);
    }

    /**
     * 格式化卡号
     * 
     * @param sn
     * @param formatValue
     * @return 16|6, ;7, ;15, @19|4, ;5, ;10, ;18, @27|4, ;5, ;6, ;7, ;8, ;9, ;10, ;11,
     */
    public static String formatSn(String sn, String formatValue) {
        String[] slipValue = formatValue.split("@");
        String snCode = sn;
        for (int j = 0; j < slipValue.length; j++) {
            int row = 0;
            String[] formatSn = slipValue[j].split("\\|");
            if (sn.length() == Integer.parseInt(formatSn[0])) {
                // 验证SN号长度是否需要格式化
                String[] format = formatSn[1].split(";");
                for (int i = 0; i < format.length; i++) {
                    int xb = Integer.parseInt(format[i].split(",")[0]) + row;
                    // 如果替换为空就直接删除对应位置字符
                    StringBuffer strb = new StringBuffer(snCode);
                    if (StringUtil.isEmpty(format[i].split(",")[1])) {
                        // 删除
                        if (snCode.length() == xb) {
                            snCode = strb.substring(0, xb - 1);
                        } else {
                            snCode = strb.substring(0, xb) + strb.substring(xb + 1, snCode.length());
                        }
                        row = row - 1;
                    } else {
                        // 替换
                        if (snCode.length() == xb) {
                            snCode = strb.substring(0, xb - 1) + format[i].split(",")[1].trim();
                        } else {
                            snCode = strb.substring(0, xb) + format[i].split(",")[1].trim() + strb.substring(xb + 1, snCode.length());
                        }
                        row = row + format[i].split(",")[1].length() - 1;
                    }
                }
            } else {
                continue;
            }
        }
        return snCode;
    }

    @Override
    public Pagination<PickingListCommand> searchSnCardCheckList(int start, int pageSize, PickingListCommand cmd, Sort[] sorts, Long ouid) {
        Date pickingTime = null;
        Date executedTime = null;
        String slipCode = null;
        String staCode = null;
        Integer status = null;
        String code = null;
        if (!StringUtils.isEmpty(cmd.getPickingTime1())) {
            pickingTime = FormatUtil.getDate(cmd.getPickingTime1());
        }
        if (!StringUtils.isEmpty(cmd.getExecutedTime1())) {
            executedTime = FormatUtil.getDate(cmd.getExecutedTime1());
        }
        if (!StringUtils.isEmpty(cmd.getSlipCode())) {
            slipCode = cmd.getSlipCode();
        }
        if (!StringUtils.isEmpty(cmd.getStaCode())) {
            staCode = cmd.getStaCode();
        }
        if (cmd.getIntStatus().intValue() > 0) {
            status = cmd.getIntStatus();
        }
        if (!StringUtils.isEmpty(cmd.getCode())) {
            code = cmd.getCode();
        }
        return pickingListDao.searchSnCardCheckList(start, pageSize, pickingTime, executedTime, slipCode, staCode, status, code, ouid, sorts, new BeanPropertyRowMapper<PickingListCommand>(PickingListCommand.class));
    }

    @Override
    public List<StockTransApplicationCommand> findSnCardErrorList(String plCode, String staCode) {
        String pl = null;
        String sta = null;
        if (!StringUtil.isEmpty(plCode)) {
            pl = plCode;
        }
        if (!StringUtil.isEmpty(staCode)) {
            sta = staCode;
        }
        return staDao.findSnCardErrorList(pl, sta, new BeanPropertyRowMapper<StockTransApplicationCommand>(StockTransApplicationCommand.class));
    }

    @Override
    public StockTransApplicationCommand findSnCardErrorByStaCode(String code, Long ouid) {
        return staDao.findSnCardErrorByStaCode(ouid, code, new BeanPropertyRowMapper<StockTransApplicationCommand>(StockTransApplicationCommand.class));
    }

    @Override
    public PickingListCommand getSnCardErrorPl(Long plid) {
        return pickingListDao.getSnCardErrorPl(plid, new BeanPropertyRowMapper<PickingListCommand>(PickingListCommand.class));
    }

    @Override
    public void shiftCardBindingStv(Long snid, Long staid, String sn) {
        StockTransApplication sta = staDao.getByPrimaryKey(staid);
        SkuSn oldsn = skuSnDao.getByPrimaryKey(snid);
        SkuSn skusn = snDao.findSkuSnBySn(sn, sta.getMainWarehouse().getId(), SkuSnStatus.USING);
        StockTransVoucher stv = stvDao.findStvCreatedByStaId(sta.getId());
        // 激活失败卡去除STA和STV绑定关系
        oldsn.setStv(null);
        oldsn.setStaId(null);
        oldsn.setLastModifyTime(new Date());
        skuSnDao.save(oldsn);
        // 新卡绑定STA和STV
        skusn.setStaId(sta.getId());
        skusn.setStv(stv);
        skusn.setLastModifyTime(new Date());
        skuSnDao.save(skusn);
    }

    @Override
    public List<StaLineCommand> findSnCardCheckSta(Long id) {
        return staLineDao.findSnCardCheckSta(id, new BeanPropertyRowMapper<StaLineCommand>(StaLineCommand.class));
    }

    @Override
    public List<PackageInfoCommand> findPackAgeInfoForSta(Long id) {
        return packageInfoDao.getPackageInfoByStaId(id, new BeanPropertyRowMapper<PackageInfoCommand>(PackageInfoCommand.class));
    }

    @Override
    public BigDecimal getBiChannelLimitAmount(Long id) {
        StockTransApplication sta = staDao.getByPrimaryKey(id);
        BiChannel bi = biChannelDao.getByCode(sta.getOwner());
        return bi.getLimitAmount();
    }

    @Override
    public SnCardErrorObj printSnCardError(StockTransApplicationCommand sta, Long cmpOuid) {
        int ordinal = 1;
        InventoryStatus inventoryStatus = inventoryStatusDao.findByName("良品", cmpOuid);
        SnCardErrorObj sce = new SnCardErrorObj();
        List<SnCardErrorLinesObj> scLineList = new ArrayList<SnCardErrorLinesObj>();
        sce.setCode(sta.getCode().substring(0, sta.getCode().length() - 4));
        sce.setCode1(sta.getCode().substring(sta.getCode().length() - 4, sta.getCode().length()));
        sce.setCode2(sta.getCode());
        sce.setOuName(sta.getOuName());
        sce.setSlipCode(sta.getRefSlipCode());
        sce.setPlCode(sta.getPlCode());
        sce.setPrintTime(getPrintTime("yyyy-MM-dd HH:mm:ss"));
        List<SkuSnCommand> skusnList = skuSnDao.getSnCardErrorListSkuCount(sta.getId(), new BeanPropertyRowMapper<SkuSnCommand>(SkuSnCommand.class));
        for (SkuSnCommand ss : skusnList) {
            Integer qty = ss.getCountNumber();
            Sku sku = skuDao.getByPrimaryKey(ss.getSkuid());
            StockTransApplication s = staDao.getByPrimaryKey(ss.getStaId());
            // 获取STA肚饿应
            List<SkuSnCommand> skusn = skuSnDao.getSnCardListByStaIdAndCardStatus(ss.getStaId(), SkuSnCardStatus.ACTIVATE_ERROR.getValue(), ss.getSkuid(), new BeanPropertyRowMapper<SkuSnCommand>(SkuSnCommand.class));
            int qty1 = 1;
            int qty2 = 1;
            for (SkuSnCommand sn : skusn) {
                boolean isCheck = true;
                SnCardErrorLinesObj scLine = new SnCardErrorLinesObj();
                WarehouseLocation l = null;
                scLine.setOrdinal(ordinal++);
                scLine.setBarCode(sku.getCode());
                scLine.setSkuName(sku.getName());
                scLine.setSn(sn.getSn().substring(0, sn.getSn().length() - 4));
                scLine.setSn1(sn.getSn().substring(sn.getSn().length() - 4, sn.getSn().length()));
                // 查看STA暂用对应商品同库位是否有足够库存
                InventoryCommand inv = inventoryDao.findSnCardSkuQtyLocation(s.getOwner(), ss.getSkuid(), s.getMainWarehouse().getId(), inventoryStatus.getId(), ss.getLoctionid(), new BeanPropertyRowMapper<InventoryCommand>(InventoryCommand.class));
                if (inv != null) {
                    if (inv.getQuantity() >= qty) {
                        // 有足够库存占用
                        l = warehouseLocationDao.getByPrimaryKey(inv.getLocationId());
                        scLine.setQty(inv.getQuantity().toString());
                        scLine.setLocation(l.getCode());
                        isCheck = false;
                    } else {
                        // 如果没有足够库存判断是否大于1张
                        if (inv.getQuantity() >= qty1) {
                            scLine.setQty(inv.getQuantity().toString());
                            l = warehouseLocationDao.getByPrimaryKey(inv.getLocationId());
                            scLine.setLocation(l.getCode());
                            isCheck = false;
                            qty1++;
                        }
                    }
                }
                // 判断是否已经被同库位占用了
                if (isCheck) {
                    // 同库位没有足够库存拆分别的库位
                    List<InventoryCommand> iList =
                            inventoryDao.findSnCardSkuQtyLocation1(s.getOwner(), ss.getSkuid(), s.getMainWarehouse().getId(), inventoryStatus.getId(), ss.getLoctionid(), new BeanPropertyRowMapper<InventoryCommand>(InventoryCommand.class));
                    for (InventoryCommand i : iList) {
                        if (i.getQuantity() >= qty) {
                            // 有足够库存占用
                            l = warehouseLocationDao.getByPrimaryKey(inv.getLocationId());
                            scLine.setQty(inv.getQuantity().toString());
                            scLine.setLocation(l.getCode());
                            isCheck = false;
                            break;
                        } else {
                            if (i.getQuantity() >= qty2) {
                                scLine.setQty(i.getQuantity().toString());
                                l = warehouseLocationDao.getByPrimaryKey(inv.getLocationId());
                                scLine.setLocation(l.getCode());
                                isCheck = false;
                                qty2++;
                                break;
                            } else {
                                continue;
                            }
                        }
                    }
                }
                if (isCheck) {
                    // 没有足够库存 库位显示空 数量显示0
                    scLine.setQty("0");
                    scLine.setLocation("");
                }
                scLineList.add(scLine);
            }
        }
        sce.setLines(scLineList);
        return sce;
    }

    public static String getPrintTime(String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);// 设置日期格式
        return df.format(new Date());
    }

    @Override
    public List<StockTransApplicationCommand> printSnCardStaList(String staCode, String plCode) {
        String pl = null;
        String code = null;
        if (!StringUtil.isEmpty(plCode)) {
            pl = plCode;
        }
        if (!StringUtil.isEmpty(staCode)) {
            code = staCode;
        }
        List<StockTransApplicationCommand> staList = skuSnDao.printSnCardErrorStaList(code, pl, new BeanPropertyRowMapper<StockTransApplicationCommand>(StockTransApplicationCommand.class));
        return staList;
    }

    @Override
    public Map<Long, ImportEntryListObj> printImportEntryList(Long staid) {
        return staDao.printImportEntryList(staid, new ImportEntryListRowMapper());
    }

    @Override
    public Map<Long, ImportEntryListObj> printImportMacaoEntryList(Long staid) {

        Map<Long, ImportEntryListObj> map = staDao.printImportMacaoEntryList(staid, new ImportMacaoEntryListRowMapper());

        return map;
    }

    @Override
    public StockTransApplicationCommand findPackageCount(Long id) {
        return staDao.findPackageCount(id, new BeanPropertyRowMapper<StockTransApplicationCommand>(StockTransApplicationCommand.class));
    }

    @Override
    public Boolean findIsWrapStuff(Long id, String wrapStuff, Long ouId) {
        Sku sku = skuDao.getByPrimaryKey(id);
        Warehouse w = warehouseDao.getByOuId(ouId);
        Boolean msg = true;
        if (null != sku && !"".equals(sku.getPaperSku()) && null != sku.getPaperSku()) {
            Sku paperSku = skuDao.getByPrimaryKey(sku.getPaperSku().getId());
            if (null != paperSku && null != wrapStuff && null != paperSku.getBarCode()) {
                if (w.getIsNeedWrapStuff()) {
                    if (paperSku.getBarCode().equals(wrapStuff)) {
                        msg = true;
                    } else {
                        msg = false;
                    }
                } else {
                    msg = true;
                }
            } else {
                msg = false;
            }
            return msg;
        } else {
            return msg;
        }
    }

    @Override
    public void createChildSn(String sn, String seedSn, SkuSnCardStatus cStatus, Long skuId) {
        // TODO Auto-generated method stub

    }
}
