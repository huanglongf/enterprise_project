package com.jumbo.wms.manager.compensation;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import loxia.dao.Pagination;
import loxia.dao.Sort;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.dao.authorization.OperationUnitDao;
import com.jumbo.dao.baseinfo.SkuDao;
import com.jumbo.dao.compensation.SysCompensateCauseDao;
import com.jumbo.dao.compensation.SysCompensateTypeDao;
import com.jumbo.dao.compensation.WhCompensationDao;
import com.jumbo.dao.compensation.WhCompensationDetailsDao;
import com.jumbo.dao.task.CommonConfigDao;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.authorization.User;
import com.jumbo.wms.model.command.SkuCommand;
import com.jumbo.wms.model.compensation.ClaimHead;
import com.jumbo.wms.model.compensation.ClaimLine;
import com.jumbo.wms.model.compensation.ClaimProcess;
import com.jumbo.wms.model.compensation.ClaimProcessAffirm;
import com.jumbo.wms.model.compensation.ClaimResult;
import com.jumbo.wms.model.compensation.CompensateStatus;
import com.jumbo.wms.model.compensation.SysCompensateCause;
import com.jumbo.wms.model.compensation.SysCompensateType;
import com.jumbo.wms.model.compensation.WhCompensation;
import com.jumbo.wms.model.compensation.WhCompensationCommand;
import com.jumbo.wms.model.compensation.WhCompensationDetails;
import com.jumbo.wms.model.compensation.WhCompensationDetailsCommand;
import com.jumbo.wms.model.compensation.WmsClaimResult;
import com.jumbo.wms.model.task.CommonConfig;

/**
 * @author lihui
 *
 * @createDate 2015年9月24日 下午4:03:37
 */
@Transactional
@Service("compensationManager")
public class CompensationManagerImpl extends BaseManagerImpl implements CompensationManager {

    private static final long serialVersionUID = 5581839884433901789L;

    @Autowired
    private WhCompensationDao whCompensationDao;

    @Autowired
    private SysCompensateCauseDao sysCompensateCauseDao;

    @Autowired
    private SysCompensateTypeDao sysCompensateTypeDao;

    @Autowired
    private CommonConfigDao commonConfigDao;

    @Autowired
    private WhCompensationDetailsDao whCompensationDetailsDao;

    @Autowired
    private OperationUnitDao operationUnitDao;

    @Autowired
    private SkuDao skuDao;

    /**
     * 根据参数获取索赔单
     * 
     * @param start
     * @param pageSize
     * @param sorts
     * @param cc
     * @return
     */
    public Pagination<WhCompensationCommand> findCompensationByParams(int start, int pageSize, Sort[] sorts, WhCompensationCommand cc) {
        Map<String, Object> comMap = new HashMap<String, Object>();
        if (cc != null) {
            if (cc.getClaimCode() != null && !"".equals(cc.getClaimCode())) {

                comMap.put("claimCode", cc.getClaimCode());
            }
            if (cc.getClaimTypeId() != null && !"".equals(cc.getClaimTypeId())) {
                comMap.put("claimTypeId", cc.getClaimTypeId());

            }
            if (cc.getClaimReasonId() != null && !"".equals(cc.getClaimReasonId())) {

                comMap.put("claimReasonId", cc.getClaimReasonId());
            }
            if (cc.getClaimStatus() != null) {
                comMap.put("claimStatus", cc.getClaimStatus());
            }
            if (cc.getShopOwner() != null && !"".equals(cc.getShopOwner())) {
                comMap.put("shopOwner", cc.getShopOwner());
            }
            if (cc.getOmsOrderCode() != null && !"".equals(cc.getOmsOrderCode())) {
                comMap.put("omsOrderCode", cc.getOmsOrderCode());
            }
            if (cc.getTransNumber() != null && !"".equals(cc.getTransNumber())) {
                comMap.put("transNumber", cc.getTransNumber());
            }
            if (cc.getTransCode() != null && !"".equals(cc.getTransCode())) {
                comMap.put("transCode", cc.getTransCode());
            }
            if (cc.getCreateUserName() != null && !"".equals(cc.getCreateUserName())) {
                comMap.put("createUserName", cc.getCreateUserName());
            }
            if (null != cc.getWarehouseId() && !"".equals(cc.getWarehouseId())) {
                comMap.put("warehouseId", cc.getWarehouseId());
            }
            comMap.put("startDate", cc.getStartDate());
            comMap.put("endDate", cc.getEndDate());

            comMap.put("staFinishDateStart", cc.getStaFinishDateStart());
            comMap.put("staFinishDateEnd", cc.getStaFinishDateEnd());
        }

        return whCompensationDao.findCompensationByParams(start, pageSize, sorts, comMap, new BeanPropertyRowMapper<WhCompensationCommand>(WhCompensationCommand.class));
    }

    /**
     * 根据参数获取索赔单明细
     * 
     * @param start
     * @param pageSize
     * @param sorts
     * @param cc
     * @return
     */
    public Pagination<WhCompensationDetailsCommand> findCompensationDetailsByParams(int start, int pageSize, Sort[] sorts, Long compensationId) {
        return whCompensationDetailsDao.findCompensationDetailsByParams(start, pageSize, sorts, compensationId, new BeanPropertyRowMapper<WhCompensationDetailsCommand>(WhCompensationDetailsCommand.class));
    }


    /**
     * 获取索赔类型
     * 
     * @return
     */
    public List<SysCompensateType> findCompensateType() {
        List<SysCompensateType> sct = sysCompensateTypeDao.getAllSysCompensateTypes();
        return sct;
    }

    /**
     * 获取索赔原因
     * 
     * @return
     */
    public List<SysCompensateCause> findCompensateCause(Long claimTypeId) {
        List<SysCompensateCause> sct = sysCompensateCauseDao.findCompensateCauseByTypeId(claimTypeId);
        return sct;
    }

    /**
     * 新增索赔单据
     * 
     * @param ch
     * @return
     */
    public WmsClaimResult addCompensationBy(ClaimHead ch) {
        WmsClaimResult wcr = new WmsClaimResult();
        wcr.setSource("toms");
        wcr.setSystemCode(ch.getSystemCode());

        // 验证此单在WMS中是否存在
        String sysCode = ch.getSystemCode();
        if (sysCode != null && !"".equals(sysCode)) {
            WhCompensation wcList = whCompensationDao.getWhCompensationBySysCode(sysCode);
            if (wcList != null) {
                wcr.setStatus(WmsClaimResult.DATA_REPETITION);
                wcr.setMemo("此索赔单据WMS已存在！");
                return wcr;
            }
            Integer cause = ch.getClaimReason();

            WhCompensation wc = new WhCompensation();
            BeanUtils.copyProperties(ch, wc);

            // 获取索赔原因
            if (cause != null) {
                SysCompensateCause scc = sysCompensateCauseDao.getSysCompensateCauseByCode(cause);
                if (scc == null) {
                    wcr.setStatus(WmsClaimResult.ACCEPT_FAIL);
                    wcr.setMemo("此索赔单据没有索赔原因！");
                    return wcr;
                }
                wc.setSysCompensateCause(scc);
            }
            wc.setStatus(CompensateStatus.CREATE);
            wc.setLastModifyTime(new Date());

            List<ClaimLine> clList = ch.getLines();
            List<WhCompensationDetails> wcdList = new ArrayList<WhCompensationDetails>();
            // 判断是否有明细行
            if (clList != null && !clList.isEmpty()) {

                claimLine2compensationDetails(clList, wcdList);

            } else {
                wcr.setStatus(WmsClaimResult.ACCEPT_FAIL);
                wcr.setMemo("此索赔单据没有明细行！");
                return wcr;
            }
            // 作废之前的订单
            List<WhCompensation> cList = whCompensationDao.getWhCompensationByClaimCode(ch.getClaimCode());
            if (cList != null && !cList.isEmpty()) {
                for (WhCompensation compensation : cList) {

                    compensation.setStatus(CompensateStatus.DISCARD);
                }
            }
            // 保存数据
            whCompensationDao.save(wc);
            for (WhCompensationDetails wcd : wcdList) {
                wcd.setWhCompensation(wc);
                whCompensationDetailsDao.save(wcd);
            }

            wcr.setStatus(WmsClaimResult.ACCEPT_SUCCESS);
            wcr.setMemo("WMS已受理此索赔单据！");

        } else {
            wcr.setStatus(WmsClaimResult.ACCEPT_FAIL);
            wcr.setMemo("系统对接码不许为空！");
        }

        return wcr;
    }

    /**
     * 索赔金额处理确认反馈
     * 
     * @param ch
     * @return
     */
    public WmsClaimResult updateClaimAffirmStatus(ClaimProcessAffirm ch) {
        WmsClaimResult wcr = new WmsClaimResult();
        wcr.setSource("toms");
        wcr.setSystemCode(ch.getSystemCode());

        // 验证此单在WMS中是否存在
        String sysCode = ch.getSystemCode();
        if (sysCode != null && !"".equals(sysCode)) {
            WhCompensation wc = whCompensationDao.getWhCompensationBySysCode(sysCode);
            if (wc != null) {

                if (wc.getClaimAffirmStatus() != null) {
                    wcr.setStatus(WmsClaimResult.DATA_REPETITION);
                    wcr.setMemo("此索赔单据已经反馈，无需再次操作！");
                    return wcr;
                }
                int status = ch.getStatus();
                if (status == 0) {
                    wc.setStatus(CompensateStatus.DISCARD);
                }

                wc.setClaimAffirmStatus(status);

                wcr.setStatus(WmsClaimResult.ACCEPT_SUCCESS);
                wcr.setMemo("WMS已受理此指令！");
            } else {
                wcr.setStatus(WmsClaimResult.ACCEPT_FAIL);
                wcr.setMemo("此索赔单据不存在！");
                return wcr;
            }


        } else {
            wcr.setStatus(WmsClaimResult.ACCEPT_FAIL);
            wcr.setMemo("系统对接码不许为空！");
        }


        return wcr;
    }


    public void claimLine2compensationDetails(List<ClaimLine> source, List<WhCompensationDetails> target) {
        if (source != null && target != null) {
            for (ClaimLine claimLine : source) {
                WhCompensationDetails wcd = new WhCompensationDetails();
                wcd.setSkuCode(claimLine.getSkuCode());
                wcd.setSkuName(claimLine.getSkuName());
                wcd.setClaimQty(claimLine.getClaimQty());
                wcd.setQuantity(claimLine.getQuantity());

                if (claimLine.getClaimAmt() != null) {
                    wcd.setClaimAmt(claimLine.getClaimAmt().doubleValue());
                }
                if (claimLine.getClaimUnitPrice() != null) {
                    wcd.setClaimUnitPrice(claimLine.getClaimUnitPrice().doubleValue());
                }
                if (claimLine.getTotalPrice() != null) {
                    wcd.setTotalPrice(claimLine.getTotalPrice().doubleValue());
                }
                if (claimLine.getUnitPrice() != null) {
                    wcd.setUnitPrice(claimLine.getUnitPrice().doubleValue());
                }
                target.add(wcd);
            }
        } else {
            throw new RuntimeException();
        }
    }

    /**
     * 根据参数获取处理结果
     * 
     * @param start
     * @param pageSize
     * @param startTime
     * @param endTime
     * @return
     */
    public Pagination<ClaimProcess> findClaimProcessByParams(int start, int pageSize, Date startTime, Date endTime) {

        return whCompensationDao.findClaimProcessByParams(start, pageSize, startTime, endTime, new BeanPropertyRowMapper<ClaimProcess>(ClaimProcess.class));
    }

    /**
     * 索赔结果
     * 
     * @param start
     * @param pageSize
     * @param startTime
     * @param endTime
     * @return
     */
    public Pagination<ClaimResult> findClaimResultByParams(int start, int pageSize, Date startTime, Date endTime) {

        return whCompensationDao.findClaimResultByParams(start, pageSize, startTime, endTime, new BeanPropertyRowMapper<ClaimResult>(ClaimResult.class));
    }


    /**
     * 修改索赔单
     * 
     * @param status 状态
     * @param logisticsepartmentAmt 物流部赔偿金
     * @param expressDeliveryAmt 快递商赔偿金
     * @param disposeRemark 赔偿备注
     * @param id
     */
    public void updateCompensationByParams(Integer status, Double logisticsepartmentAmt, Double expressDeliveryAmt, String disposeRemark, Long id, User user) {
        if (logisticsepartmentAmt == null) {
            logisticsepartmentAmt = 0d;
        }
        if (expressDeliveryAmt == null) {
            expressDeliveryAmt = 0d;
        }
        WhCompensation wc = whCompensationDao.getByPrimaryKey(id);
        if (status != null) {

            if (status == CompensateStatus.DISPOSE.getValue()) {
                wc.setLogisticsepartmentAmt(logisticsepartmentAmt);
                wc.setExpressDeliveryAmt(expressDeliveryAmt);
                wc.setDisposeRemark(disposeRemark);
                // wc.setDisposeTime(new Date());

                if ((logisticsepartmentAmt + expressDeliveryAmt) > 0) {
                    wc.setStatus(CompensateStatus.valueOf(status));
                } else {
                    wc.setStatus(CompensateStatus.FAIL);
                    wc.setAffirmTime(new Date());
                    wc.setDisposeTime(new Date());
                }

            } else {
                wc.setAffirmTime(new Date());
                wc.setStatus(CompensateStatus.valueOf(status));
            }
            wc.setLastModifyTime(new Date());
            wc.setLastUpdateUser(user);
        } else {
            throw new RuntimeException();
        }
    }


    /**
     * 修改索赔单
     * 
     * @param status 状态
     * @param logisticsepartmentAmt 物流部赔偿金
     * @param expressDeliveryAmt 快递商赔偿金
     * @param disposeRemark 赔偿备注
     * @param id
     */
    public void updateCompensation(Integer status, Double logisticsepartmentAmt, Double expressDeliveryAmt, String disposeRemark, Long id, User user) {
        if (logisticsepartmentAmt == null) {
            logisticsepartmentAmt = 0d;
        }
        if (expressDeliveryAmt == null) {
            expressDeliveryAmt = 0d;
        }
        WhCompensation wc = whCompensationDao.getByPrimaryKey(id);
        if (status != null) {

            if (status == CompensateStatus.CHECK.getValue()) {
                wc.setLogisticsepartmentAmt(logisticsepartmentAmt);
                wc.setExpressDeliveryAmt(expressDeliveryAmt);
                wc.setDisposeRemark(disposeRemark);
                wc.setDisposeTime(new Date());

                if ((logisticsepartmentAmt + expressDeliveryAmt) > 0) {
                    wc.setStatus(CompensateStatus.valueOf(status));
                } else {
                    wc.setStatus(CompensateStatus.FAIL);
                    wc.setAffirmTime(new Date());
                }

            } else {
                wc.setAffirmTime(new Date());
                wc.setStatus(CompensateStatus.valueOf(status));
            }
            wc.setLastModifyTime(new Date());
            wc.setLastUpdateUser(user);
        } else {
            throw new RuntimeException();
        }
    }

    /**
     * 获取附件下载相关配置
     * 
     * @return
     */
    public Map<String, String> getCOMPENSATIONConfig() {
        Map<String, String> ftpconfigCache = new HashMap<String, String>();
        List<CommonConfig> configs = commonConfigDao.findCommonConfigListByParaGroup("COMPENSATION");
        if (configs != null && configs.size() > 0) {
            for (CommonConfig config : configs) {
                ftpconfigCache.put(config.getParaName(), config.getParaValue());
            }
        }
        log.debug("CommonConfigManagerImpl.refreshK3Config.assountSet={}", new Object[] {"default"});
        return ftpconfigCache;
    }

    @Override
    public List<OperationUnit> getSendWarehouse() {
        return operationUnitDao.getSendWarehouse(new BeanPropertyRowMapper<OperationUnit>(OperationUnit.class));
    }

    @Override
    public List<WhCompensationCommand> findStatesIsCheck() {

        return whCompensationDao.findStatesIsCheck(new BeanPropertyRowMapper<WhCompensationCommand>(WhCompensationCommand.class));
    }

    @Override
    public void updateStatesIsSucceed(Long whCompensationId) {
        whCompensationDao.updateStatesIsSucceed(whCompensationId);
    }

    @Override
    public Pagination<OperationUnit> getSendWarehouseforPage(int start, int pageSize, String name) {
        return operationUnitDao.getSendWarehouseforPage(start, pageSize, name, new BeanPropertyRowMapper<OperationUnit>(OperationUnit.class));
    }

    @Override
    public List<WhCompensationCommand> findCompensationByParamsNoPage(WhCompensationCommand cc) {
        Map<String, Object> comMap = new HashMap<String, Object>();
        if (cc != null) {
            if (cc.getClaimCode() != null && !"".equals(cc.getClaimCode())) {

                comMap.put("claimCode", cc.getClaimCode());
            }
            if (cc.getClaimTypeId() != null && !"".equals(cc.getClaimTypeId())) {
                comMap.put("claimTypeId", cc.getClaimTypeId());

            }
            if (cc.getClaimReasonId() != null && !"".equals(cc.getClaimReasonId())) {

                comMap.put("claimReasonId", cc.getClaimReasonId());
            }
            if (cc.getClaimStatus() != null) {
                comMap.put("claimStatus", cc.getClaimStatus());
            }
            if (cc.getShopOwner() != null && !"".equals(cc.getShopOwner())) {
                comMap.put("shopOwner", cc.getShopOwner());
            }
            if (cc.getOmsOrderCode() != null && !"".equals(cc.getOmsOrderCode())) {
                comMap.put("omsOrderCode", cc.getOmsOrderCode());
            }
            if (cc.getTransNumber() != null && !"".equals(cc.getTransNumber())) {
                comMap.put("transNumber", cc.getTransNumber());
            }
            if (cc.getTransCode() != null && !"".equals(cc.getTransCode())) {
                comMap.put("transCode", cc.getTransCode());
            }
            if (cc.getCreateUserName() != null && !"".equals(cc.getCreateUserName())) {
                comMap.put("createUserName", cc.getCreateUserName());
            }
            if (null != cc.getWarehouseId() && !"".equals(cc.getWarehouseId())) {
                comMap.put("warehouseId", cc.getWarehouseId());
            }
            comMap.put("startDate", cc.getStartDate());
            comMap.put("endDate", cc.getEndDate());

            comMap.put("staFinishDateStart", cc.getStaFinishDateStart());
            comMap.put("staFinishDateEnd", cc.getStaFinishDateEnd());
        }

        return whCompensationDao.findCompensationByParamsNoPage(comMap, new BeanPropertyRowMapper<WhCompensationCommand>(WhCompensationCommand.class));
    }

    @Override
    public List<WhCompensationDetailsCommand> findCompensationDetailsByParamsNoPage(Long compensationId) {
        return whCompensationDetailsDao.findCompensationDetailsByParamsNoPage(compensationId, new BeanPropertyRowMapper<WhCompensationDetailsCommand>(WhCompensationDetailsCommand.class));
    }

    @Override
    public List<SkuCommand> findskuBarCodeByStaId(Long staId) {
        return skuDao.findSkuMaterialByStaId(staId, new BeanPropertyRowMapper<SkuCommand>(SkuCommand.class));
    }

}
