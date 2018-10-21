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
package com.jumbo.web.action.warehouse;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.jumbo.util.JsonUtil;
import com.jumbo.util.StringUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.authorization.AuthorizationManager;
import com.jumbo.wms.manager.sku.SkuManager;
import com.jumbo.wms.manager.warehouse.CustomsDeclarationManager;
import com.jumbo.wms.manager.warehouse.PickingListManager;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.manager.warehouse.WareHouseManagerExe;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.command.OperationCenterCommand;
import com.jumbo.wms.model.command.SkuCommand;
import com.jumbo.wms.model.command.WarehouseCommand;
import com.jumbo.wms.model.warehouse.OutBoundPack;
import com.jumbo.wms.model.warehouse.PickingList;
import com.jumbo.wms.model.warehouse.PickingListPackageCommand;
import com.jumbo.wms.model.warehouse.StaAdditionalLine;
import com.jumbo.wms.model.warehouse.StockTransApplicationCommand;

import loxia.dao.Pagination;
import loxia.support.json.JSONException;
import loxia.support.json.JSONObject;

/**
 * 
 * @author sjk
 * @author fanht update 2013-05-03
 * 
 */
public class SalesOutboundAction extends BaseJQGridProfileAction {

    /**
	 * 
	 */
    private static final long serialVersionUID = 8574644256454671572L;

    @Autowired
    private WareHouseManager wareHouseManager;
    @Autowired
    private WareHouseManagerExe wareHouseManagerExe;
    @Autowired
    private AuthorizationManager authorizationManager;
    @Autowired
    private SkuManager skuManager;
    @Autowired
    private PickingListManager pickingListManager;
    @Autowired
    private CustomsDeclarationManager customsDeclarationManager;

    /**
     * 包材条码
     */
    private List<StaAdditionalLine> saddlines;

    /**
     * 快递单号
     */
    private String trackingNo;

    private Long staId;
    private Boolean isOtwoO;
    private String staIds;

    private Long plpId;
    /**
     * 物流code
     */
    private String lpCode;

    /**
     * 重量
     */
    private BigDecimal weight;

    private String barCode;
    /**
     * 是否全部出库（自动化多件称重最后将所有已称重的包裹全部出库)
     */
    private Boolean allOutBound = false;

    private String pickListCode;

    public String getPickListCode() {
        return pickListCode;
    }

    public void setPickListCode(String pickListCode) {
        this.pickListCode = pickListCode;
    }

    /**
     * 耗材
     */
    private String consumables;

    /**
     * 批次号
     */
    private String batchCode;

    /**
     * 出库进入
     * 
     * @return
     */
    public String outboundEntry() {
        return SUCCESS;
    }

    public String getLpCode() {
        return lpCode;
    }

    public void setLpCode(String lpCode) {
        this.lpCode = lpCode;
    }

    /**
     * 根据快递单查询sta line
     * 
     * @return
     */
    public String getStaLineByTrackingNo() {
        setTableConfig();
        request.put(JSON, toJson(wareHouseManager.findStaLineCommandListByStaId(tableConfig.getStart(), tableConfig.getPageSize(), staId, tableConfig.getSorts())));
        return JSON;
    }

    /**
     * 空请求，更新Session时间，重置定时
     */
    public String getNull() {
        return JSON;
    }

    /**
     * find sta
     * 
     * @return
     */
    public String findStaByTrackingNo1() {
        if (StringUtils.hasText(trackingNo)) {
            StockTransApplicationCommand staCmd = null;
            if (userDetails.getCurrentOu().getOuType().getName().equals("OperationCenter")) {
                List<OperationUnit> ouList = authorizationManager.findOperationUnitList(userDetails.getCurrentOu().getId());
                List<Long> idList = new ArrayList<Long>();
                for (OperationUnit ou : ouList) {
                    idList.add(ou.getId());
                }
                staCmd = wareHouseManager.findSalesStaByTrackingNo1(trackingNo, null, idList);
            } else {
                staCmd = wareHouseManager.findSalesStaByTrackingNo1(trackingNo, userDetails.getCurrentOu().getId(), null);
            }

            if (staCmd != null) {
                JSONObject result = new JSONObject();
                try {
                    result.put("result", SUCCESS);
                    result.put("sta", JsonUtil.obj2json(staCmd));
                } catch (JSONException e) {
                    log.error("", e);
                }
                request.put(JSON, result);

            }
        }
        return JSON;
    }

    /**
     * find sta
     * 
     * @return
     */
    public String findStaByTrackingNos() {
        if (StringUtils.hasText(trackingNo)) {
            List<StockTransApplicationCommand> staCmd = null;
            if (userDetails.getCurrentOu().getOuType().getName().equals("OperationCenter")) {
                List<OperationUnit> ouList = authorizationManager.findOperationUnitList(userDetails.getCurrentOu().getId());
                List<Long> idList = new ArrayList<Long>();
                for (OperationUnit ou : ouList) {
                    idList.add(ou.getId());
                }
                staCmd = wareHouseManager.findSalesStaByTrackingNos(trackingNo, null, idList);
            } else {
                staCmd = wareHouseManager.findSalesStaByTrackingNos(trackingNo, userDetails.getCurrentOu().getId(), null);
            }

            if (staCmd != null) {
                JSONObject result = new JSONObject();
                try {
                    result.put("result", SUCCESS);
                    result.put("sta", JsonUtil.collection2json(staCmd));
                } catch (JSONException e) {
                    log.error("", e);
                }
                request.put(JSON, result);

            }
        }
        return JSON;
    }

    /**
     * find sta
     * 
     * @return
     */
    public String findStaByTrackingNo() {
        if (StringUtils.hasText(trackingNo)) {
            StockTransApplicationCommand staCmd = wareHouseManager.findSalesStaByTrackingNo(null, trackingNo, userDetails.getCurrentOu().getId(), null);
            if (staCmd != null) {
                JSONObject result = new JSONObject();
                try {
                    result.put("result", SUCCESS);
                    result.put("sta", JsonUtil.obj2json(staCmd));
                } catch (JSONException e) {
                    // e.printStackTrace();
                    if (log.isErrorEnabled()) {
                        log.error("findStaByTrackingNo error:" + trackingNo, e);
                    };
                }
                request.put(JSON, result);

            }
        }
        return JSON;
    }

    public String findStaId() {
        if (staId != null) {
            StockTransApplicationCommand staCmd = wareHouseManager.findStaById(staId);
            if (staCmd != null) {
                JSONObject result = new JSONObject();
                try {
                    result.put("result", SUCCESS);
                    result.put("sta", JsonUtil.obj2json(staCmd));
                } catch (JSONException e) {
                    // e.printStackTrace();
                    if (log.isErrorEnabled()) {
                        log.error("findStaId JSONException:" + staId, e);
                    };
                }
                request.put(JSON, result);

            }
        }
        return JSON;
    }


    /**
     * find PICKINGLIST
     * 
     * @return
     */
    public String findPickingListPackageByTrackingNo() {
        if (StringUtils.hasText(trackingNo)) {
            PickingListPackageCommand plpCmd = wareHouseManager.findPickingListPackageByTrackingNo(trackingNo, userDetails.getCurrentOu().getId());
            if (plpCmd != null) {
                JSONObject result = new JSONObject();
                try {
                    result.put("result", SUCCESS);
                    result.put("plp", JsonUtil.obj2json(plpCmd));
                } catch (JSONException e) {
                    // e.printStackTrace();
                    if (log.isErrorEnabled()) {
                        log.error("findPickingListPackageByTrackingNo JSONException:" + trackingNo, e);
                    };
                }
                request.put(JSON, result);

            }
        }
        return JSON;
    }

    /**
     * 出库
     * 
     * @return
     * @throws JSONException
     */
    public String outBound() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            boolean rs = wareHouseManager.salesStaOutBound(staId, userDetails.getUser().getId(), userDetails.getCurrentOu().getId(), trackingNo, weight, saddlines, false,null);

            if (rs) {
                result.put("result", SUCCESS);
            } else {
                result.put("result", ERROR);
                wareHouseManager.findUnCheckedPackageBySta(staId);
            }
        } catch (BusinessException e) {
            result.put("result", ERROR);
            result.put("message", this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 出库&交接 sta转出，扣减库存，插入中间表，返回物流主键 fanht
     * 
     * @return
     * @throws JSONException
     */
    public String outBoundHand() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            Map<String, Object> rs = wareHouseManager.salesStaOutBoundHand(staId, userDetails.getUser().getId(), isOtwoO, userDetails.getCurrentOu().getId(), trackingNo, weight, saddlines, allOutBound,null);
            if (rs != null) {
                result.put("packageId", (Long) rs.get("pgId"));
                if ((Boolean) rs.get("bool")) {
                    result.put("result", SUCCESS);
                    // 保税仓报关车辆推荐
                    String lpn = customsDeclarationManager.findLicensePlateNumber(staId);
                    if (!StringUtil.isEmpty(lpn)) {
                        if ("failed".equals(lpn)) {
                            result.put("licensePlateNumber", "没有推荐到车辆！");
                        } else {
                            result.put("licensePlateNumber", "推荐到车辆，车牌号：" + lpn);
                        }
                    }
                } else {
                    result.put("result", ERROR);
                    wareHouseManager.findUnCheckedPackageBySta(staId);
                }
            }
        } catch (BusinessException e) {
            result.put("result", ERROR);
            result.put("message", this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
        }
        // }
        request.put(JSON, result);
        return JSON;
    }


    /**
     * o2oQs出库 sta转出(此处转出设置为10)，扣减库存
     * 
     * @return
     * @throws JSONException
     */
    @SuppressWarnings("unchecked")
    public String o2oQsoutBound() throws JSONException {
        JSONObject result = new JSONObject();
        Map<String, Object> rs = null;
        try {
            rs = wareHouseManagerExe.o2oQsSalesStaOutBound(plpId, userDetails.getUser().getId(), userDetails.getCurrentOu().getId(), trackingNo, weight, saddlines);
            if (rs != null) {
                result.put("staCodeList", (List<String>) rs.get("staCodeList"));
                result.put("result", SUCCESS);
            }
        } catch (BusinessException e) {
            result.put("result", ERROR);
            result.put("message", this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 出库&交接，初始化未交接单 fanht
     * 
     * @return
     * @throws JSONException
     */
    public String initOutBoundPack() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            List<OutBoundPack> bean = wareHouseManager.initOutBoundPack(userDetails.getUser().getId(), userDetails.getCurrentOu().getId(), lpCode);
            result.put("result", SUCCESS);
            result.put("initBean", JsonUtil.collection2json(bean));
        } catch (BusinessException e) {
            result.put("result", ERROR);
            result.put("message", this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 初始化未交接单，check
     * 
     * @return
     * @throws JSONException
     */
    public String initOutBoundPackCheck() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            Object bean = wareHouseManager.initOutBoundPackCheck(userDetails.getUser().getId(), userDetails.getCurrentOu().getId());
            if (bean != null) {
                if (bean instanceof WarehouseCommand) {
                    WarehouseCommand ware = (WarehouseCommand) bean;
                    result.put("result", ERROR);
                    result.put("message", "你在" + ware.getOu().getName() + "下，还有订单未交接，请及时处理");
                } else {
                    OperationCenterCommand oc = (OperationCenterCommand) bean;
                    result.put("result", ERROR);
                    result.put("message", "你在" + oc.getOu().getName() + "下，还有订单未交接，请及时处理");
                }
            } else {
                result.put("result", SUCCESS);
            }
        } catch (BusinessException e) {
            result.put("result", ERROR);
            result.put("message", this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 阿迪整批出库校验是否耗材
     */
    public String isSkuMaterial() {
        JSONObject ob = new JSONObject();
        if (consumables != null && consumables != "") {
            SkuCommand sku = skuManager.findSkuMaterial(consumables, userDetails.getCurrentUserRole().getOu().getId());
            try {
                if (sku == null) {
                    ob.put("msg", "no");
                } else {
                    ob.put("msg", "yes");
                }
            } catch (JSONException e) {}
            request.put(JSON, ob);
        }

        return JSON;
    }

    public String outBoundByPistList() {
        JSONObject result = new JSONObject();
        String flag = null;
        if (null != batchCode && !"".equals(batchCode)) {
            flag = wareHouseManager.outBoundByPistList(batchCode, userDetails.getCurrentOu().getId(), userDetails.getUser().getId(), consumables, weight);
        }
        try {
            result.put("result", flag);
        } catch (JSONException e) {
            log.error("outBoundByPistList:" + e.toString());
        }
        request.put(JSON, result);
        return JSON;
    }


    public String findPistListStatus() {
        JSONObject result = new JSONObject();
        try {
            String flag = wareHouseManager.findPistListStatus(userDetails.getCurrentOu().getId(), batchCode);
            result.put("result", flag);
        } catch (Exception e) {
            log.error("findPistListStatus:" + e.toString());
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 是否是单件-一键创批的批次号
     * 
     * @return
     * @throws JSONException
     */
    public String isPistListByOneKey() throws JSONException {
        JSONObject ob = new JSONObject();
        if (batchCode != null && !"".equals(batchCode)) {
            PickingList p = pickingListManager.findPickListByPickCode(batchCode, userDetails.getCurrentOu().getId());
            if (p != null) {
                if (p.getLocId() == null) {
                    ob.put("msg", "error1");
                } else {
                    ob.put("msg", "yes");
                }
            } else {
                ob.put("msg", "error2");
            }
        }
        request.put(JSON, ob);
        return JSON;
    }

    /**
     * 根据批次号查询取消单据
     * 
     * @return
     */
    public String getCancelStaByBatCheCode() {
        setTableConfig();
        if (null != batchCode && !"".equals(batchCode)) {
            Pagination<StockTransApplicationCommand> list = new Pagination<StockTransApplicationCommand>();
            List<StockTransApplicationCommand> staList = wareHouseManager.findStaByPickingListCode(batchCode, userDetails.getCurrentOu().getId());
            list.setItems(staList);
            request.put(JSON, toJson(list));
        }
        return JSON;
    }

    public String getTrackingNo() {
        return trackingNo;
    }

    public void setTrackingNo(String trackingNo) {
        this.trackingNo = trackingNo;
    }

    public Long getStaId() {
        return staId;
    }

    public void setStaId(Long staId) {
        this.staId = staId;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public List<StaAdditionalLine> getSaddlines() {
        return saddlines;
    }

    public void setSaddlines(List<StaAdditionalLine> saddlines) {
        this.saddlines = saddlines;
    }

    public Long getPlpId() {
        return plpId;
    }

    public void setPlpId(Long plpId) {
        this.plpId = plpId;
    }

    public String getStaIds() {
        return staIds;
    }

    public void setStaIds(String staIds) {
        this.staIds = staIds;
    }

    public Boolean getIsOtwoO() {
        return isOtwoO;
    }

    public void setIsOtwoO(Boolean isOtwoO) {
        this.isOtwoO = isOtwoO;
    }

    public Boolean getAllOutBound() {
        return allOutBound;
    }

    public void setAllOutBound(Boolean allOutBound) {
        this.allOutBound = allOutBound;
    }

    public String getConsumables() {
        return consumables;
    }

    public void setConsumables(String consumables) {
        this.consumables = consumables;
    }

    public String getBatchCode() {
        return batchCode;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }



}
