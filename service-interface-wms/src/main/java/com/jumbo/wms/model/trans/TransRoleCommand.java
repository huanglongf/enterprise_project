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

package com.jumbo.wms.model.trans;

import java.math.BigDecimal;
import java.util.List;

import com.jumbo.wms.model.authorization.OperationUnit;

/**
 * 物流推荐规则
 * 
 * @author lingyun.dai
 * 
 */

public class TransRoleCommand extends TransRole {
    /**
     * 
     */
    private static final long serialVersionUID = -1261893908452049363L;

    private Long roleStatus;// 状态
    private String roleStatu;
    private Long roleChannelId;// 渠道ID
    private Long roleServiceId;// 物流服务ID
    private Long areaGroupId;// 配送范围组ID
    private String owner; // 店铺
    private Long transId; // 物流商
    private String lpCode;// 物流编号
    private Boolean isSupportCod;// 物流是否支持COD
    private Integer intTransType; // 物流类型
    private Integer intTransTimeType; // 失效类型
    private List<OperationUnit> ouList;// 相关联的仓库
    private List<TransRoleDetialCommand> detials;// 规则明细

    private BigDecimal minCount;// 订单金额最小值
    private BigDecimal maxCount;// 订单金额最大值
    private BigDecimal minWCount;// 订单重量最小值
    private BigDecimal maxWCount;// 订单重量最大值
    private Integer timeType;// 时效类型
    private Integer isCod;// 是否是COD订单
    private String areaCode;// 配送范围编码
    private String areaName;// 配送范围名称
    private String areaStatus;// 配送范围名称
    private Long roleDtalId;// 规则明细ID

    private String removeKeyword;// 排除关键字

    public Integer getTimeType() {
        return timeType;
    }

    public void setTimeType(Integer timeType) {
        this.timeType = timeType;
    }

    public Long getRoleStatus() {
        return roleStatus;
    }

    public void setRoleStatus(Long roleStatus) {
        this.roleStatus = roleStatus;
    }

    public String getRoleStatu() {
        return roleStatu;
    }

    public void setRoleStatu(String roleStatu) {
        this.roleStatu = roleStatu;
    }

    public Long getRoleChannelId() {
        return roleChannelId;
    }

    public void setRoleChannelId(Long roleChannelId) {
        this.roleChannelId = roleChannelId;
    }

    public Long getRoleServiceId() {
        return roleServiceId;
    }

    public void setRoleServiceId(Long roleServiceId) {
        this.roleServiceId = roleServiceId;
    }

    public Long getAreaGroupId() {
        return areaGroupId;
    }

    public void setAreaGroupId(Long areaGroupId) {
        this.areaGroupId = areaGroupId;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Long getTransId() {
        return transId;
    }

    public void setTransId(Long transId) {
        this.transId = transId;
    }

    public Integer getIntTransType() {
        return intTransType;
    }

    public void setIntTransType(Integer intTransType) {
        this.intTransType = intTransType;
    }

    public Integer getIntTransTimeType() {
        return intTransTimeType;
    }

    public void setIntTransTimeType(Integer intTransTimeType) {
        this.intTransTimeType = intTransTimeType;
    }

    public List<OperationUnit> getOuList() {
        return ouList;
    }

    public void setOuList(List<OperationUnit> ouList) {
        this.ouList = ouList;
    }

    public BigDecimal getMinCount() {
        return minCount;
    }

    public Boolean getIsSupportCod() {
        return isSupportCod;
    }

    public void setIsSupportCod(Boolean isSupportCod) {
        this.isSupportCod = isSupportCod;
    }

    public void setMinCount(BigDecimal minCount) {
        this.minCount = minCount;
    }

    public BigDecimal getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(BigDecimal maxCount) {
        this.maxCount = maxCount;
    }

    public BigDecimal getMinWCount() {
        return minWCount;
    }

    public void setMinWCount(BigDecimal minWCount) {
        this.minWCount = minWCount;
    }

    public BigDecimal getMaxWCount() {
        return maxWCount;
    }

    public void setMaxWCount(BigDecimal maxWCount) {
        this.maxWCount = maxWCount;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public Long getRoleDtalId() {
        return roleDtalId;
    }

    public void setRoleDtalId(Long roleDtalId) {
        this.roleDtalId = roleDtalId;
    }

    public List<TransRoleDetialCommand> getDetials() {
        return detials;
    }

    public void setDetials(List<TransRoleDetialCommand> detials) {
        this.detials = detials;
    }

    public String getLpCode() {
        return lpCode;
    }

    public void setLpCode(String lpCode) {
        this.lpCode = lpCode;
    }

    public String getAreaStatus() {
        return areaStatus;
    }

    public void setAreaStatus(String areaStatus) {
        this.areaStatus = areaStatus;
    }

    public Integer getIsCod() {
        return isCod;
    }

    public void setIsCod(Integer isCod) {
        this.isCod = isCod;
    }

    public String getRemoveKeyword() {
        return removeKeyword;
    }

    public void setRemoveKeyword(String removeKeyword) {
        this.removeKeyword = removeKeyword;
    }


}
