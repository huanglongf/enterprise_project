package com.jumbo.web.action.warehouse.salesOutbound;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.Constants;
import com.jumbo.util.JsonUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.outbound.AdCheckManager;
import com.jumbo.wms.manager.outbound.OutboundInfoManager;
import com.jumbo.wms.model.authorization.User;
import com.jumbo.wms.model.warehouse.StaDeliveryInfo;
import com.jumbo.wms.web.commond.OrderCheckCommand;
import com.jumbo.wms.web.commond.PickingListCommand;
import com.jumbo.wms.web.commond.TransWeightOrderCommand;

import cn.baozun.bh.util.JSONUtil;
import loxia.support.json.JSONArray;
import loxia.support.json.JSONException;
import loxia.support.json.JSONObject;

/**
 * 出库
 * 
 * @author sjk
 * 
 */
public class OrderOutboundAction extends BaseJQGridProfileAction {

    private static final long serialVersionUID = -4222038143121837012L;

    @Autowired
    private OutboundInfoManager outboundInfoManager;
    @Autowired
    private AdCheckManager adCheckManager;
    // 单据号
    private String orderCode;
    // 类型
    private Integer typeFlag;
    // 运单号
    private String transNo;
    // staLineId
    private Long uniqueId;
    // SN号
    private String sn;
    // 是否拆单称重，未核对称重
    private Boolean isSpPacking;
    // 封装核对数据
    private OrderCheckCommand checkOrder;
    // 耗材条码
    private String staffCode;
    // 当前重量
    private BigDecimal weight;
    // 明细行ID
    private List<Long> idList;
    
    
    /**
     * 是否跳过称重 1:是   0或null：否
     */
    private String  isSkipWeight;
    /**
     * 批次ID
     */
    private Long plId;

    private String staffCode2;

   

    public String getStaffCode2() {
        return staffCode2;
    }

    public void setStaffCode2(String staffCode2) {
        this.staffCode2 = staffCode2;
    }

    public String getIsSkipWeight() {
        return isSkipWeight;
    }

    public void setIsSkipWeight(String isSkipWeight) {
        this.isSkipWeight = isSkipWeight;
    }

    /**
     * 多件出库
     */
    public String skusOutbound() {
        return SUCCESS;
    }

    /**
     * 初始化当前用户所有未交接单据列表
     * 
     * @return
     */
    public String getNeedHandOverOrderSummary() {
        List<StaDeliveryInfo> hl = outboundInfoManager.getNeedHandOverOrderSummary(userDetails.getCurrentOu().getId(), userDetails.getUser().getId());
        JSONArray result = JsonUtil.collection2json(hl, "id,lpCode");
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 获取核对数据详细信息 需要参数orderCode
     */
    public String getCheckInfo() {
        PickingListCommand p = outboundInfoManager.getOutboundCheckInfo(orderCode, typeFlag);// outboundInfoManager.getOutboundCheckInfo("P00001");
        JSONObject ss =
                JsonUtil.obj2json(p, "plId,plCode,isHaveReportMissing,status,isPostpositionExpressBill,orders,orders.staId," + "orders.staCode,orders.orderCode,orders.slipCode1,orders.tns,"
                                + "orders.status,orders.intStatus,orders.owner,orders.lpcode,"
                        + "orders.transNo,orders.skuQty,orders.pgIndex,orders.idx1,orders.idx2,orders.pickingType,orders.cmCode," + "orders.lines,orders.lines.qty,orders.lines.isGift,orders.lines.cQty,orders.lines.uniqueId,orders.lines.sku,"
                        + "orders.lines.sku.code,orders.lines.sku.name,orders.lines.sku.barcode," + "orders.lines.sku.supCode,orders.lines.sku.keyProp,orders.lines.sku.colorSize," + "orders.lines.sku.barcodes,orders.lines.sku.isSn,orders.lines.sns");
        request.put(JSON, ss);
        return JSON;
    }

    /**
     * 获取商品原产地
     * 
     * @throws JSONException
     * 
     */
    public String findSkuOriginByPlId() throws JSONException {
        JSONObject result = new JSONObject();
        Map<String, Set<String>> originMap = outboundInfoManager.findSkuOriginByPlId(plId);
        String ss = JSONUtil.beanToJson(originMap);
        result.put("originMap", ss);
        request.put(JSON, result);
        return JSON;
    }

    public String recordWeightForTrackingNo() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            result.put("result", ERROR);
            outboundInfoManager.recordWeightForTrackingNo(transNo, weight, staffCode);
            result.put("result", SUCCESS);
        } catch (BusinessException e) {
            String errorMsg = "";
            if (e.getErrorCode() != ErrorCode.ERROR_NOT_SPECIFIED) {
                errorMsg += this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()) + Constants.HTML_LINE_BREAK;
            }
            BusinessException current = e;
            while (current.getLinkedException() != null) {
                current = current.getLinkedException();
                errorMsg += this.getMessage(ErrorCode.BUSINESS_EXCEPTION + current.getErrorCode(), current.getArgs()) + Constants.HTML_LINE_BREAK;
            }
            result.put("msg", errorMsg);
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 校验扫描单号的正确性<br/>
     * 扫描的单号必须是单件的配货清单/周装箱条码或者多件/套装组合/团购的作业单
     * 
     * @return JSON
     * @throws JSONException
     */
    public String checkOrderCode() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            result.put("result", ERROR);
            Integer type = outboundInfoManager.checkOrderCode(orderCode, userDetails.getCurrentOu().getId());
            if (type != null) {
                result.put("typeFlag", type);
                result.put("result", SUCCESS);
            }
        } catch (BusinessException e) {
            String errorMsg = "";
            if (e.getErrorCode() != ErrorCode.ERROR_NOT_SPECIFIED) {
                errorMsg += this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()) + Constants.HTML_LINE_BREAK;
            }
            BusinessException current = e;
            while (current.getLinkedException() != null) {
                current = current.getLinkedException();
                errorMsg += this.getMessage(ErrorCode.BUSINESS_EXCEPTION + current.getErrorCode(), current.getArgs()) + Constants.HTML_LINE_BREAK;
            }
            result.put("msg", errorMsg);
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 校验界面传递的SN是否
     * 
     * @return
     * @throws JSONException
     */
    public String checkSnStatus() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            result.put("result", ERROR);
            outboundInfoManager.checkSnStatus(uniqueId, sn);
            result.put("result", SUCCESS);
        } catch (BusinessException e) {
            String errorMsg = "";
            if (e.getErrorCode() != ErrorCode.ERROR_NOT_SPECIFIED) {
                errorMsg += this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()) + Constants.HTML_LINE_BREAK;
            }
            BusinessException current = e;
            while (current.getLinkedException() != null) {
                current = current.getLinkedException();
                errorMsg += this.getMessage(ErrorCode.BUSINESS_EXCEPTION + current.getErrorCode(), current.getArgs()) + Constants.HTML_LINE_BREAK;
            }
            result.put("msg", errorMsg);
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 获取快递称重数据
     * 
     * @return
     */
    public String getTransWeightInfo() {
        TransWeightOrderCommand cmd = outboundInfoManager.getOrderInfo(transNo, isSpPacking);
        request.put(JSON, JsonUtil.obj2json(cmd));
        return JSON;
    }

    /**
     * 核对作业单
     * 
     * @return
     * @throws JSONException
     */
    public String doCheckBySta() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("result", SUCCESS);
        try {
            // ad预售订单核对时,不需要执行ad的check
            boolean b = adCheckManager.isAdPreSale(checkOrder.getStaId());
            if (b) {
                adCheckManager.storeLogisticsSend(checkOrder.getStaId(), false);
                adCheckManager.ifExistsLineCanncel(checkOrder, userDetails.getUser().getId(), userDetails.getCurrentOu().getId());
            }
            outboundInfoManager.doCheckBySta(checkOrder, userDetails.getUser().getId(), userDetails.getCurrentOu().getId(), isSkipWeight, staffCode2);
        } catch (BusinessException e) {
            String errorMsg = "";
            if (e.getErrorCode() != ErrorCode.ERROR_NOT_SPECIFIED) {
                errorMsg += this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()) + Constants.HTML_LINE_BREAK;
            }
            BusinessException current = e;
            while (current.getLinkedException() != null) {
                current = current.getLinkedException();
                errorMsg += this.getMessage(ErrorCode.BUSINESS_EXCEPTION + current.getErrorCode(), current.getArgs()) + Constants.HTML_LINE_BREAK;
            }
            json.put("result", ERROR);
            json.put("msg", errorMsg);
        }catch (Exception e) {
            log.error("doCheckBySta_1",e);
            json.put("result", ERROR);
            json.put("msg", "系统异常..");
        }
        request.put(JSON, json);
        return JSON;
    }

    public String partlySalesOutbound() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("result", SUCCESS);
        try {
            outboundInfoManager.partlySalesOutbound(idList, userDetails.getUser().getId(), userDetails.getCurrentOu().getId());
        } catch (BusinessException e) {
            String errorMsg = "";
            if (e.getErrorCode() != ErrorCode.ERROR_NOT_SPECIFIED) {
                errorMsg += this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()) + Constants.HTML_LINE_BREAK;
            }
            BusinessException current = e;
            while (current.getLinkedException() != null) {
                current = current.getLinkedException();
                errorMsg += this.getMessage(ErrorCode.BUSINESS_EXCEPTION + current.getErrorCode(), current.getArgs()) + Constants.HTML_LINE_BREAK;
            }
            json.put("result", ERROR);
            json.put("msg", errorMsg);
        }
        request.put(JSON, json);
        return JSON;
    }

    /**
     * 核对时报缺，目前针对AD定制
     * 
     * @return
     * @throws JSONException
     */
    public String reportMissingWhenCheck() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("result", SUCCESS);
        try {
            outboundInfoManager.reportMissingWhenCheck(idList, userDetails.getUser().getId(), userDetails.getCurrentOu().getId());
        } catch (BusinessException e) {
            String errorMsg = "";
            if (e.getErrorCode() != ErrorCode.ERROR_NOT_SPECIFIED) {
                errorMsg += this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()) + Constants.HTML_LINE_BREAK;
            }
            BusinessException current = e;
            while (current.getLinkedException() != null) {
                current = current.getLinkedException();
                errorMsg += this.getMessage(ErrorCode.BUSINESS_EXCEPTION + current.getErrorCode(), current.getArgs()) + Constants.HTML_LINE_BREAK;
            }
            json.put("result", ERROR);
            json.put("msg", errorMsg);
        }
        request.put(JSON, json);
        return JSON;
    }

    public String findUserPrivilegeInfo() throws JSONException {
        User u = outboundInfoManager.findUserPrivilegeInfo(userDetails.getUser().getId());
        request.put(JSON, JsonUtil.obj2json(u));
        return JSON;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getTransNo() {
        return transNo;
    }

    public void setTransNo(String transNo) {
        this.transNo = transNo;
    }

    public Integer getTypeFlag() {
        return typeFlag;
    }

    public void setTypeFlag(Integer typeFlag) {
        this.typeFlag = typeFlag;
    }

    public Long getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(Long uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public Boolean getIsSpPacking() {
        return isSpPacking;
    }

    public void setIsSpPacking(Boolean isSpPacking) {
        this.isSpPacking = isSpPacking;
    }

    public OrderCheckCommand getCheckOrder() {
        return checkOrder;
    }

    public void setCheckOrder(OrderCheckCommand checkOrder) {
        this.checkOrder = checkOrder;
    }

    public String getStaffCode() {
        return staffCode;
    }

    public void setStaffCode(String staffCode) {
        this.staffCode = staffCode;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public List<Long> getIdList() {
        return idList;
    }

    public void setIdList(List<Long> idList) {
        this.idList = idList;
    }

    public Long getPlId() {
        return plId;
    }

    public void setPlId(Long plId) {
        this.plId = plId;
    }



}
