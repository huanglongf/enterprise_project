package com.jumbo.web.action.warehouse;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import loxia.dao.Pagination;
import loxia.support.json.JSONException;
import loxia.support.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.util.FormatUtil;
import com.jumbo.util.JsonUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.authorization.AuthorizationManager;
import com.jumbo.wms.manager.warehouse.CheckOutBoundManager;
import com.jumbo.wms.manager.warehouse.PickingListPrintManager;
import com.jumbo.wms.manager.warehouse.WareHouseManagerCancel;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.warehouse.PickingList;
import com.jumbo.wms.model.warehouse.PickingListCommand;
import com.jumbo.wms.model.warehouse.StaAdditionalLine;
import com.jumbo.wms.model.warehouse.StockTransApplicationStatus;

/**
 * 核对出库控制逻辑
 * 
 * @author jinlong.ke
 * 
 */
public class CheckOutboundAction extends BaseJQGridProfileAction {

    /**
     * 
     */
    private static final long serialVersionUID = -8191225087004278258L;
    @Autowired
    private CheckOutBoundManager checkOutBoundManager;
    @Autowired
    private PickingListPrintManager pickingListPrintManager;
    @Autowired
    private AuthorizationManager authorizationManager;
    @Autowired
    private WareHouseManagerCancel wareHouseManagerCancel;
    /**
     * 创建时间 下限
     */
    private String fromTime;
    /**
     * 创建时间 上限
     */
    private String endTime;
    /**
     * 封装查询对象
     */
    private PickingList pickingList;
    /**
     * 包裹重量
     */
    private BigDecimal weight;
    /**
     * 包材条码
     */
    private List<StaAdditionalLine> saddlines;
    private OperationUnit ou;
    private Long wid;
    /**
     * 作业单Id
     */
    private Long staId;


    /**
     * 跳转到秒杀订单-核对-出库-交接界面
     * 
     * @return
     */
    public String secKillCheck() {
        return SUCCESS;
    }

    /**
     * 根据查询条件，查询秒杀配货清单，渲染配后清单列表
     * 
     * @return
     */
    public String getAllSecKillPickingListByStatus() {
        setTableConfig();
        Pagination<PickingListCommand> list =
                checkOutBoundManager.getAllSecKillPickingListByStatus(tableConfig.getStart(), tableConfig.getPageSize(), FormatUtil.getDate(fromTime), FormatUtil.getDate(endTime), pickingList, userDetails.getCurrentOu().getId(), tableConfig.getSorts());
        request.put(JSON, toJson(list));
        return JSON;
    }

    /**
     * 查询带核对列表
     * 
     * @return
     */
    public String getStaPickingListPgIndex() {
        setTableConfig();
        request.put(JSON, toJson(checkOutBoundManager.getStaPickingListPgIndex(pickingList.getId(), tableConfig.getSorts())));
        return JSON;
    }

    /**
     * 查询取消作业单列表
     * 
     * @return
     */
    public String getCancelStaListPgIndex() {
        setTableConfig();
        request.put(JSON, toJson(checkOutBoundManager.getCancelStaListPgIndex(pickingList.getId(), tableConfig.getSorts())));
        return JSON;
    }

    /**
     * 根据staId pikinglist id 删除作业单 并返回信息
     * 
     * @return
     */
    public String deleteStaById() {
        JSONObject json = new JSONObject();
        try {
            Boolean b = false;
            json.put("result", "error");
            checkOutBoundManager.deleteStaById(pickingList.getId(), staId);
            json.put("result", "success");
            b = true;
            if (b == true) {
                PickingList pl = pickingListPrintManager.getPickingListById(pickingList.getId());
                if (pl == null) {
                    json.put("flag", "YES");
                } else {
                    json.put("flag", "NO");
                    List<Integer> statusList = new ArrayList<Integer>();
                    statusList.add(StockTransApplicationStatus.CANCEL_UNDO.getValue());
                    statusList.add(StockTransApplicationStatus.CANCELED.getValue());
                    // 判断pickingList是否包含取消作业单
                    Boolean isHaveCancel = wareHouseManagerCancel.findIfExistsCancelSta(pickingList.getId(), statusList);
                    if (isHaveCancel) {
                        json.put("isHaveCancel", "true");
                    } else {
                        json.put("isHaveCancel", "false");
                    }
                }
            }
        } catch (Exception e) {
            // e.printStackTrace();
            if (log.isErrorEnabled()) {
                log.error("deleteStaById error:", e);
            };
        }
        request.put(JSON, json);
        return JSON;
    }

    /**
     * 核对出库
     * 
     * @return
     * @throws JSONException
     */
    public String secKillGenerHandOverList() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            if (ou == null || ou.getId() == null) {
                wid = userDetails.getCurrentOu().getId();
            } else {
                wid = ou.getId();
            }
            checkOutBoundManager.generSecKillHandOverList(weight, pickingList.getId(), saddlines, userDetails.getUser().getId(), wid);
            result.put("rs", "success");
        } catch (BusinessException e) {
            result.put("rs", "error");
            result.put("message", this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 创交接清单
     * 
     * @return
     * @throws JSONException
     */
    public String generHandoverlist() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            if (ou == null || ou.getId() == null) {
                wid = userDetails.getCurrentOu().getId();
            } else {
                wid = ou.getId();
            }
            Long handId = checkOutBoundManager.generHandOverList(pickingList.getId(), wid, userDetails.getCurrentOu().getId(), userDetails.getUser().getId());
            result.put("rs", "success");
            result.put("handId", handId);
        } catch (Exception e) {
            log.error("", e);
            result.put("rs", "error");
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 根据配货清单查询对应的物流交接单
     * 
     * @return
     * @throws JSONException
     */
    public String getHandoverIdByPickinglistId() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            Long handId = pickingListPrintManager.getHandOverIdByPickingListId(pickingList.getId());
            if (handId == null) {
                result.put("rs", "error");
            } else {
                result.put("rs", "success");
                result.put("handId", handId);
            }
        } catch (Exception e) {
            result.put("rs", "error");
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 根据配货批次号查询配货批
     * 
     * @return
     */
    public String findCheckoutPickingByCode() {
        JSONObject result = new JSONObject();
        try {
            PickingListCommand plc = null;
            if (userDetails.getCurrentOu().getOuType().getName().equals("OperationCenter")) {
                List<OperationUnit> ouList = authorizationManager.findOperationUnitList(userDetails.getCurrentOu().getId());
                List<Long> idList = new ArrayList<Long>();
                for (OperationUnit ou : ouList) {
                    idList.add(ou.getId());
                }
                plc = pickingListPrintManager.findCheckoutPickingByCode(pickingList.getCode(), null, idList);
            } else {
                plc = pickingListPrintManager.findCheckoutPickingByCode(pickingList.getCode(), userDetails.getCurrentOu().getId(), null);
            }
            if (plc != null) {
                result.put("result", "success");
                result.put("pickingList", JsonUtil.obj2json(plc));
            } else {
                result.put("result", "error");
            }
        } catch (JSONException e) {
            log.error("", e);
        }
        request.put(JSON, result);
        return JSON;
    }

    public String getFromTime() {
        return fromTime;
    }

    public void setFromTime(String fromTime) {
        this.fromTime = fromTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public PickingList getPickingList() {
        return pickingList;
    }

    public void setPickingList(PickingList pickingList) {
        this.pickingList = pickingList;
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

    public Long getStaId() {
        return staId;
    }

    public void setStaId(Long staId) {
        this.staId = staId;
    }

}
