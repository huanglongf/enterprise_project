package com.jumbo.web.action.warehouse;

import java.util.List;

import loxia.dao.Pagination;
import loxia.support.json.JSONException;
import loxia.support.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.Constants;
import com.jumbo.util.FormatUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.outbound.AdCheckManager;
import com.jumbo.wms.manager.outbound.OutboundInfoManager;
import com.jumbo.wms.model.warehouse.StockTransApplicationCommand;

/**
 * 
 * @author jinlong.ke
 * @date 2016年9月5日下午7:45:45
 * 
 */
public class OccupiedFailureAction extends BaseJQGridProfileAction {

    /**
     * 
     */
    private static final long serialVersionUID = 22292373835888031L;
    private String createTime;
    private String endCreateTime;
    private List<Long> staIdList;
    private StockTransApplicationCommand sta;
    @Autowired
    private OutboundInfoManager outboundInfoManager;
    @Autowired
    private AdCheckManager adCheckManager;

    /**
     * 页面跳转
     * 
     * @return
     */
    public String redirectIndex() {
        return SUCCESS;
    }

    public String getAllOccupiedStaList() {
        setTableConfig();
        Pagination<StockTransApplicationCommand> tl =
                outboundInfoManager.getAllOccupiedFailureOrder(tableConfig.getStart(), tableConfig.getPageSize(), userDetails.getCurrentOu().getId(), FormatUtil.getDate(createTime), FormatUtil.getDate(endCreateTime), sta, tableConfig.getSorts());
        request.put(JSON, toJson(tl));
        return JSON;
    }

    public String showOccDetail() {
        setTableConfig();
        request.put(JSON, toJson(outboundInfoManager.showOccDetail(sta.getId(), tableConfig.getSorts())));
        return JSON;
    }

    public String reOccupationByIdList() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("result", SUCCESS);
        try {
            adCheckManager.reOccupationByIdList(staIdList, userDetails.getCurrentOu().getId());
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

    public String cancelAdOrderById() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("result", SUCCESS);
        try {
            log.error("AD-Cancle by person==============staId:" + sta.getId());
            outboundInfoManager.cancelAdOrderById(sta.getId());
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

    public String partySendAdOrderById() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("result", SUCCESS);
        try {
            outboundInfoManager.partySendAdOrderById(sta.getId());
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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getEndCreateTime() {
        return endCreateTime;
    }

    public void setEndCreateTime(String endCreateTime) {
        this.endCreateTime = endCreateTime;
    }

    public StockTransApplicationCommand getSta() {
        return sta;
    }

    public void setSta(StockTransApplicationCommand sta) {
        this.sta = sta;
    }

    public List<Long> getStaIdList() {
        return staIdList;
    }

    public void setStaIdList(List<Long> staIdList) {
        this.staIdList = staIdList;
    }

}
