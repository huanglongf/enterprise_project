package com.jumbo.wms.manager.compensation;

import java.util.Date;
import java.util.List;
import java.util.Map;

import loxia.dao.Pagination;
import loxia.dao.Sort;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.authorization.User;
import com.jumbo.wms.model.command.SkuCommand;
import com.jumbo.wms.model.compensation.ClaimHead;
import com.jumbo.wms.model.compensation.ClaimProcess;
import com.jumbo.wms.model.compensation.ClaimProcessAffirm;
import com.jumbo.wms.model.compensation.ClaimResult;
import com.jumbo.wms.model.compensation.SysCompensateCause;
import com.jumbo.wms.model.compensation.SysCompensateType;
import com.jumbo.wms.model.compensation.WhCompensationCommand;
import com.jumbo.wms.model.compensation.WhCompensationDetailsCommand;
import com.jumbo.wms.model.compensation.WmsClaimResult;

public interface CompensationManager extends BaseManager {


    /**
     * 根据参数获取索赔单
     * 
     * @param start
     * @param pageSize
     * @param sorts
     * @param cc
     * @return
     */
    public Pagination<WhCompensationCommand> findCompensationByParams(int start, int pageSize, Sort[] sorts, WhCompensationCommand cc);



    public Pagination<OperationUnit> getSendWarehouseforPage(int start, int pageSize, String name);

    /**
     * 根据参数获取索赔单明细
     * 
     * @param start
     * @param pageSize
     * @param sorts
     * @param cc
     * @return
     */
    public Pagination<WhCompensationDetailsCommand> findCompensationDetailsByParams(int start, int pageSize, Sort[] sorts, Long compensationId);


    /**
     * 获取索赔类型
     * 
     * @return
     */
    public List<SysCompensateType> findCompensateType();

    /**
     * 获取索赔原因
     * 
     * @return
     */
    public List<SysCompensateCause> findCompensateCause(Long claimTypeId);


    /**
     * 新增索赔单据
     * 
     * @param ch
     * @return
     */
    public WmsClaimResult addCompensationBy(ClaimHead ch);

    /**
     * 根据参数获取处理结果
     * 
     * @param start
     * @param pageSize
     * @param startTime
     * @param endTime
     * @return
     */
    public Pagination<ClaimProcess> findClaimProcessByParams(int start, int pageSize, Date startTime, Date endTime);

    /**
     * 索赔结果
     * 
     * @param start
     * @param pageSize
     * @param startTime
     * @param endTime
     * @return
     */
    public Pagination<ClaimResult> findClaimResultByParams(int start, int pageSize, Date startTime, Date endTime);


    /**
     * 修改索赔单
     * 
     * @param status 状态
     * @param logisticsepartmentAmt 物流部赔偿金
     * @param expressDeliveryAmt 快递商赔偿金
     * @param disposeRemark 赔偿备注
     * @param id
     * @param user
     */
    public void updateCompensationByParams(Integer status, Double logisticsepartmentAmt, Double expressDeliveryAmt, String disposeRemark, Long id, User user);

    /**
     * 索赔金额处理确认反馈
     * 
     * @param ch
     * @return
     */
    public WmsClaimResult updateClaimAffirmStatus(ClaimProcessAffirm ch);

    /**
     * 获取附件下载相关配置
     * 
     * @return
     */
    public Map<String, String> getCOMPENSATIONConfig();

    /**
     * 获取发货仓
     * 
     * @return
     */
    List<OperationUnit> getSendWarehouse();


    public void updateCompensation(Integer status, Double logisticsepartmentAmt, Double expressDeliveryAmt, String disposeRemark, Long id, User user);

    public List<WhCompensationCommand> findStatesIsCheck();

    public void updateStatesIsSucceed(Long whCompensationId);

    public List<WhCompensationCommand> findCompensationByParamsNoPage(WhCompensationCommand cc);

    public List<WhCompensationDetailsCommand> findCompensationDetailsByParamsNoPage(Long compensationId);

    public List<SkuCommand> findskuBarCodeByStaId(Long staId);
}
